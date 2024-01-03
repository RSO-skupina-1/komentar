package si.fri.rso.komentar.services.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;

import si.fri.rso.komentar.lib.Komentar;
import si.fri.rso.komentar.models.converters.KomentarConverter;
import si.fri.rso.komentar.models.entities.KomentarEntity;


@RequestScoped
public class KomentarBean {

    private Logger log = Logger.getLogger(si.fri.rso.komentar.services.beans.KomentarBean.class.getName());

    @Inject
    private EntityManager em;

    public List<Komentar> getKomentar() {

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

    public Komentar getKomentar(Integer id) {

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

        return KomentarConverter.toDto(komentarEntity);
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
