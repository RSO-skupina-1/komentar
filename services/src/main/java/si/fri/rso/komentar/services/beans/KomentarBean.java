package si.fri.rso.komentar.services.beans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import si.fri.rso.komentar.lib.Komentar;
import si.fri.rso.komentar.models.converters.KomentarConverter;
import si.fri.rso.komentar.models.entities.KomentarEntity;
import si.fri.rso.komentar.services.config.RestProperties;


@RequestScoped
public class KomentarBean {

    private Logger log = Logger.getLogger(si.fri.rso.komentar.services.beans.KomentarBean.class.getName());

    @Inject
    private EntityManager em;

    @Inject
    private KomentarBean komentarBeanProxy;

    @Inject
    private RestProperties restProperties;

    private int retryCounter = 0;

    private OkHttpClient client;

    @PostConstruct
    private void init(){
        client = new OkHttpClient().newBuilder().build();
    }

    public List<Komentar> getKomentarById() {

        TypedQuery<KomentarEntity> query = em.createNamedQuery(
                "KomentarEntity.getAll", KomentarEntity.class);

        List<KomentarEntity> resultList = query.getResultList();

        return resultList.stream().map(KomentarConverter::toDto).collect(Collectors.toList());

    }

    public List<Komentar> getKomentarFilter(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, KomentarEntity.class, queryParameters).stream()
                .map(KomentarConverter::toDto).collect(Collectors.toList());
    }

    public Komentar getKomentarById(Integer id) {

        KomentarEntity komentarEntity = em.find(KomentarEntity.class, id);

        if (komentarEntity == null) {
            throw new NotFoundException();
        }

        Komentar komentar = KomentarConverter.toDto(komentarEntity);

        return komentar;
    }
    public List<Komentar> getKomentarByDestinacija(Integer id) {

        TypedQuery<KomentarEntity> query = em.createNamedQuery(
                "KomentarEntity.getByLokacijaId", KomentarEntity.class).setParameter("lokacija_id", id);

        List<KomentarEntity> resultList = query.getResultList();

        return resultList.stream().map(KomentarConverter::toDto).collect(Collectors.toList());

    }
    public List<Komentar> getKomentarByUser(Integer id) {

        TypedQuery<KomentarEntity> query = em.createNamedQuery(
                "KomentarEntity.getByUserId", KomentarEntity.class).setParameter("user_id", id);

        List<KomentarEntity> resultList = query.getResultList();

        return resultList.stream().map(KomentarConverter::toDto).collect(Collectors.toList());

    }

    public Komentar createKomentar(Komentar komentar) {

        komentar.setKomentar(komentarBeanProxy.checkForBadWords(komentar.getKomentar()));

        KomentarEntity komentarEntity = KomentarConverter.toEntity(komentar);

        try {
            beginTx();
            em.persist(komentarEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        if (komentarEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        retryCounter = 0;
        return KomentarConverter.toDto(komentarEntity);
    }

    //@Retry(maxRetries = 3)
    //@Timeout(value = 4, unit = ChronoUnit.SECONDS)
    //@Fallback(fallbackMethod = "checkForBadWordsFallback")
    public String checkForBadWords(String text){
        retryCounter++;
        String url = "https://api.apilayer.com/bad_words?censor_character=*";
        String apiKey = "qYFK5eu2TTx2T3qIa5P7x3TIRklhOUKj";
        if(restProperties.getBroken()){
            apiKey = "wrong_api_key";
        }
        log.info("Checking for bad words for the " + retryCounter + ". time. With apiKey: " + apiKey + ".");

        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("text/plain");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, text);

        Request request = new Request.Builder()
                .url(url)
                .addHeader("apiKey", apiKey)
                .method("POST", body)
                .build();

        try{
            okhttp3.Response response = client.newCall(request).execute();
            if (response.body() != null){
                String s = response.body().string();
                response.body().close();
                String[] split = s.split("\"");
                int i = 0;
                Boolean found = false;
                for (i = 0; i < split.length; i++) {
                    if (split[i].contains("censored_content")) {
                        found = true;
                        break;
                    }
                }
                if (!found){
                    log.severe("Bad words service not working.");
                    throw new InternalServerErrorException("Bad words service not working.");
                };
                return(split[(i + 2)]);

            }
            return null;

        } catch (IOException e) {
            log.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }

    public String checkForBadWordsFallback(String text){
        return text;
    }

    public Komentar putKomentar(Integer id, Komentar komentar) {

        KomentarEntity c = em.find(KomentarEntity.class, id);

        if (c == null) {
            return null;
        }

        KomentarEntity updatedKomentarEntity = KomentarConverter.toEntity(komentar);

        try {
            beginTx();
            updatedKomentarEntity.setId(c.getId());
            updatedKomentarEntity = em.merge(updatedKomentarEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        return KomentarConverter.toDto(updatedKomentarEntity);
    }

    public boolean deleteKomentar(Integer id) {

        KomentarEntity imageMetadata = em.find(KomentarEntity.class, id);

        if (imageMetadata != null) {
            try {
                beginTx();
                em.remove(imageMetadata);
                commitTx();
            }
            catch (Exception e) {
                rollbackTx();
            }
        }
        else {
            return false;
        }

        return true;
    }

    private void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}
