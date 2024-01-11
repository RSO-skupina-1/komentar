package si.fri.rso.komentar.api.v1.resources;


import com.kumuluz.ee.cors.annotations.CrossOrigin;
import com.kumuluz.ee.logs.cdi.Log;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.json.JSONObject;
import si.fri.rso.komentar.lib.Komentar;
import si.fri.rso.komentar.services.beans.KomentarBean;
import si.fri.rso.komentar.services.config.RestProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.logging.Logger;


@Log
@ApplicationScoped
@Path("/komentar")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@CrossOrigin(name = "komentar", allowOrigin = "*")
public class KomentarResource {

    private Logger log = Logger.getLogger(KomentarResource.class.getName());

    @Inject
    private KomentarBean komentarBean;

    @Inject
    private RestProperties restProperties;


    @Context
    protected UriInfo uriInfo;

    private int retryCounter = 0;

    @Counted(name = "get_all_komentar_count")
    @Operation(description = "Get all comments.", summary = "Returns all comments present in the database.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Array of comments",
                    content = @Content(schema = @Schema(implementation = Komentar.class, type = SchemaType.ARRAY, example = """
                            [
                                {
                                    "id": 1,
                                    "user_id": 1,
                                    "lokacija_id": 1,
                                    "komentar": "Priljubljeno mesto za potovanje, toplo priporočam!",
                                    "ocena": 5,
                                    "ustvarjen": "2021-01-01T00:00:00.000+00:00"
                                },
                                {
                                    "id": 2,
                                    "user_id": 2,
                                    "lokacija_id": 2,
                                    "komentar": "Priljubljeno mesto za potovanje, toplo priporočam!",
                                    "ocena": 5,
                                    "ustvarjen": "2021-01-01T00:00:00.000+00:00"
                                }]"""))
            )})
    @GET
    public Response getKomentar() {
        log.info("Get all comments.") ;
        List<Komentar> komentar = komentarBean.getKomentarById();

        return Response.status(Response.Status.OK).entity(komentar).build();
    }

    @Counted(name = "get_komentar_by_komentarId_count")
    @Operation(description = "Get comment by ID.", summary = "Returns comment with corresponding ID.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Successfully returns chosen comment.",
                    content = @Content(
                            schema = @Schema(implementation = Komentar.class, example = """
                            {
                                "id": 1,
                                "user_id": 1,
                                "lokacija_id": 1,
                                "komentar": "Priljubljeno mesto za potovanje, toplo priporočam!",
                                "ocena": 5,
                                "ustvarjen": "2021-01-01T00:00:00.000+00:00"
                            }"""))
            ),
            @APIResponse(responseCode = "404",
                    description = "Comment with given ID doesn't exist.")
    })
    @GET
    @Path("/{komentarId}")
    public Response getKomentarById(@Parameter(description = "Metadata ID.", required = true)
                                     @PathParam("komentarId") Integer komentarId) {

        log.info("Get comment with id: " + komentarId);

        Komentar komentar = komentarBean.getKomentarById(komentarId);

        if (komentar == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(komentar).build();
    }

    @Counted(name = "get_komentar_by_userId_count")
    @Operation(description = "Get comments by user ID.", summary = "Returns all comments posted by user with coresponding user ID.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Successfully returns chosen users comments.",
                    content = @Content(
                            schema = @Schema(implementation = Komentar.class, type = SchemaType.ARRAY, example = """
                            [
                                {
                                    "id": 1,
                                    "user_id": 1,
                                    "lokacija_id": 1,
                                    "komentar": "Priljubljeno mesto za potovanje, toplo priporočam!",
                                    "ocena": 5,
                                    "ustvarjen": "2021-01-01T00:00:00.000+00:00"
                                },
                                {
                                    "id": 2,
                                    "user_id": 2,
                                    "lokacija_id": 2,
                                    "komentar": "Priljubljeno mesto za potovanje, toplo priporočam!",
                                    "ocena": 5,
                                    "ustvarjen": "2021-01-01T00:00:00.000+00:00"
                                }]"""))
            ),
            @APIResponse(responseCode = "404",
                    description = "User with given ID doesn't exist.")
    })
    @GET
    @Path("user/{userId}")
    public Response getKomentarByUser(@Parameter(description = "User ID.", required = true)
                                 @PathParam("userId") Integer userId) {

        log.info("Get all comments posted by user with id: " + userId);

        List<Komentar> komentar = komentarBean.getKomentarByUser(userId);

        if (komentar == null || komentar.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(komentar).build();
    }

    @Counted(name = "get_komentar_by_destinacijaId_count")
    @Operation(description = "Get comments by destinacija ID.", summary = "Returns all comments posted under destinacija with coresponding destinacija ID.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Successfully returns chosen destinations comments.",
                    content = @Content(
                            schema = @Schema(implementation = Komentar.class, type = SchemaType.ARRAY, example = """
                            [
                                {
                                    "id": 1,
                                    "user_id": 1,
                                    "lokacija_id": 1,
                                    "komentar": "Priljubljeno mesto za potovanje, toplo priporočam!",
                                    "ocena": 5,
                                    "ustvarjen": "2021-01-01T00:00:00.000+00:00"
                                },
                                {
                                    "id": 2,
                                    "user_id": 2,
                                    "lokacija_id": 2,
                                    "komentar": "Priljubljeno mesto za potovanje, toplo priporočam!",
                                    "ocena": 5,
                                    "ustvarjen": "2021-01-01T00:00:00.000+00:00"
                                }]"""))
            ),
            @APIResponse(responseCode = "404",
                    description = "Destinacija with given ID doesn't exist.")
    })
    @GET
    @Path("destinacija/{destinacijaId}")
    public Response getKomentarByDestinacija(@Parameter(description = "Destinacija ID.", required = true)
                                 @PathParam("destinacijaId") Integer destinacijaId) {

        log.info("Get all comments posted under destination with id: " + destinacijaId);

        List<Komentar> komentar = komentarBean.getKomentarByDestinacija(destinacijaId);

        if (komentar == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(komentar).build();
    }


    @Operation(description = "Add new comment from given user to a destination.", summary = "Add comment")
    @RequestBody(
            description = "DTO object with comment metadata and text",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = Komentar.class, example = """
                                                                                    {
                                                                                        "id": 1,
                                                                                        "user_id": 1,
                                                                                        "lokacija_id": 1,
                                                                                        "komentar": "Priljubljeno mesto za potovanje, toplo priporočam!",
                                                                                        "ocena": 5,
                                                                                        "ustvarjen": "2021-01-01T00:00:00.000+00:00"
                                                                                    }""")
            )
    )
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Comment successfully added.",
                    content = @Content(
                            schema = @Schema(implementation = Komentar.class, example = """
                            {
                                "id": 1,
                                "user_id": 1,
                                "lokacija_id": 1,
                                "komentar": "Priljubljeno mesto za potovanje, toplo priporočam!",
                                "ocena": 5,
                                "ustvarjen": "2021-01-01T00:00:00.000+00:00"
                            }""")
                    )
            ),
            @APIResponse(responseCode = "405",
                        description = "Either user ID or destinacija ID was not given")
    })
    @Counted(name = "num_of_posted_comments")
    @Timed(name = "Comment_post_time")
    @POST
    public Response postKomentarByDestinacija(@RequestBody(description = "DTO object with comment metadata and text",
                                                           required = true,
                                                           content = @Content(
                                                                   schema = @Schema(implementation = Komentar.class)
                                                           )) Komentar komentar) throws IOException {


        if (komentar.getLokacija_id() == null || komentar.getUser_id() == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if(komentar.getUstvarjen() == null){
            komentar.setUstvarjen(Instant.now());
        }

        return Response.status(Response.Status.CREATED).entity(komentarBean.createKomentar(komentar)).build();
    }

    @Operation(description = "Update comment from user on destinacija.", summary = "Update comment with corresponding komentar ID.")
    @RequestBody(
            description = "DTO object with comment metadata and text",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = Komentar.class, example = """
                                                                                    {
                                                                                        "id": 1,
                                                                                        "user_id": 1,
                                                                                        "lokacija_id": 1,
                                                                                        "komentar": "Priljubljeno mesto za potovanje, toplo priporočam!",
                                                                                        "ocena": 5,
                                                                                        "ustvarjen": "2021-01-01T00:00:00.000+00:00"
                                                                                    }""")
            )
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "201",
                    description = "Comment successfully updated."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Comment with given komentar ID was not found, hence cannot be updated."
            )
            })
    @PUT
    @Counted(name = "number_of_updated_comments")
    @Timed(name = "Comment_update_time")
    @Path("{komentarId}")
    public Response putImageMetadata(@Parameter(description = "Metadata ID.", required = true)
                                     @PathParam("komentarId") Integer komentarId,
                                     @RequestBody(
                                             description = "DTO object with comment.",
                                             required = true, content = @Content(
                                             schema = @Schema(implementation = Komentar.class)))
                                     Komentar komentar) throws IOException{

        log.info("Update comment.");

        if(komentar.getUstvarjen() == null){
            komentar.setUstvarjen(Instant.now());
        }

        komentar = komentarBean.putKomentar(komentarId, komentar);

        if (komentar == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        String text = komentar.getKomentar();
        okhttp3.RequestBody body = okhttp3.RequestBody
                .create(okhttp3.MediaType.get("application/x-www-form-urlencoded"), text);

        Request request = new Request.Builder()
                .url("https://api.apilayer.com/bad_words?censor_character=*")
                .addHeader("apiKey", "VC7y8FdT1gEcdGuoOTZBWSBPN05mq4ds")
                .post(body)
                .build();

        OkHttpClient client = new OkHttpClient().newBuilder().build();

        Call call = client.newCall(request);
        okhttp3.Response response1 = call.execute();

        JSONObject jo = new JSONObject(response1.body().string());

        komentar.setKomentar(jo.get("censored_content").toString());

        return Response.status(Response.Status.CREATED).build();

    }

    @Operation(description = "Delete comment with given id.", summary = "Delete comment with corresponding komentar ID.")
    @APIResponses({
            @APIResponse(
                    responseCode = "204",
                    description = "Comment successfully deleted."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Comment with given comment ID was not found."
            )
    })
    @DELETE
    @Counted(name = "number_of_deleted_comments")
    @Path("{komentarId}")
    public Response deleteKomentar(@Parameter(description = "Comment ID.", required = true)
                                        @PathParam("komentarId") Integer komentarId){

        log.info("Delete comment with id: " + komentarId);

        boolean deleted = komentarBean.deleteKomentar(komentarId);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}