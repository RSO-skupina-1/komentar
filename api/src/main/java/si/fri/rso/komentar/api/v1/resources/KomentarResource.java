package si.fri.rso.komentar.api.v1.resources;

<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> parent of 410ac78 (init)

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
<<<<<<< HEAD
>>>>>>> parent of e9a4a0d (rollback)
=======
>>>>>>> parent of 410ac78 (init)
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
<<<<<<< HEAD
<<<<<<< HEAD
=======
import org.json.JSONObject;
>>>>>>> parent of e9a4a0d (rollback)
=======
import org.json.JSONObject;
>>>>>>> parent of 410ac78 (init)
import si.fri.rso.komentar.lib.Komentar;
import si.fri.rso.komentar.services.beans.KomentarBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
import java.time.Instant;
>>>>>>> parent of 8446cdb (Revert "Revert "rollback"")
=======
import java.io.IOException;
import java.time.Instant;
>>>>>>> parent of e9a4a0d (rollback)
=======
import java.io.IOException;
import java.time.Instant;
>>>>>>> parent of 410ac78 (init)
import java.util.List;
import java.util.logging.Logger;



@ApplicationScoped
@Path("/komentar")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class KomentarResource {

    private Logger log = Logger.getLogger(KomentarResource.class.getName());

    @Inject
    private KomentarBean komentarBean;


    @Context
    protected UriInfo uriInfo;

    @Counted(name = "get_all_komentar_count")
<<<<<<< HEAD
<<<<<<< HEAD
    @Operation(description = "Get all comments.", summary = "Get all metadata")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of comments",
                    content = @Content(schema = @Schema(implementation = Komentar.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of objects in list")}
=======
=======
>>>>>>> parent of 410ac78 (init)
    @Operation(description = "Get all comments.", summary = "Returns all comments present in the database.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Array of comments",
<<<<<<< HEAD
<<<<<<<< HEAD:api/src/main/java/si/fri/rso/priporocilni/api/v1/resources/PriporocilniResource.java
<<<<<<< HEAD
                    content = @Content(schema = @Schema(implementation = Komentar.class, type = SchemaType.ARRAY))
=======
                    content = @Content(schema = @Schema(implementation = Priporocilni.class, type = SchemaType.ARRAY))
<<<<<<< HEAD
=======
                    description = "List of comments",
                    content = @Content(schema = @Schema(implementation = Komentar.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of objects in list")}
>>>>>>> parent of aca6107 (popravljen openAPI):api/src/main/java/si/fri/rso/komentar/api/v1/resources/KomentarResource.java
>>>>>>> parent of 8446cdb (Revert "Revert "rollback"")
=======
>>>>>>> parent of 0fff523 (Revert "rollback")
========
                    content = @Content(schema = @Schema(implementation = Komentar.class, type = SchemaType.ARRAY))
>>>>>>>> parent of e9a4a0d (rollback):api/src/main/java/si/fri/rso/komentar/api/v1/resources/KomentarResource.java
>>>>>>> parent of e9a4a0d (rollback)
=======
                    content = @Content(schema = @Schema(implementation = Komentar.class, type = SchemaType.ARRAY))
>>>>>>> parent of 410ac78 (init)
            )})
    @GET
    public Response getKomentar() {
        log.info("Get all comments.") ;
        List<Komentar> komentar = komentarBean.getKomentarFilter(uriInfo);

        return Response.status(Response.Status.OK).entity(komentar).build();
    }


<<<<<<< HEAD
<<<<<<< HEAD
    @Operation(description = "Get metadata for a comment.", summary = "Get metadata for a comment")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Comments",
                    content = @Content(
                            schema = @Schema(implementation = Komentar.class))
            )})

    @GET
    @Path("/{komentarId}")
    public Response getKomentar(@Parameter(description = "Metadata ID.", required = true)
                                     @PathParam("komentarId") Integer imageMetadataId) {

        Komentar komentar = komentarBean.getKomentar(imageMetadataId);

=======
=======
>>>>>>> parent of 410ac78 (init)
    @Operation(description = "Get comment by ID.", summary = "Returns comment with corresponding ID.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Successfully returns chosen comment.",
                    content = @Content(
<<<<<<< HEAD
<<<<<<<< HEAD:api/src/main/java/si/fri/rso/priporocilni/api/v1/resources/PriporocilniResource.java
<<<<<<< HEAD
<<<<<<< HEAD
                            schema = @Schema(implementation = Komentar.class))
=======
<<<<<<< HEAD:api/src/main/java/si/fri/rso/priporocilni/api/v1/resources/PriporocilniResource.java
=======
>>>>>>> parent of 0fff523 (Revert "rollback")
                            schema = @Schema(implementation = Priporocilni.class))
>>>>>>> parent of 8446cdb (Revert "Revert "rollback"")
========
                            schema = @Schema(implementation = Komentar.class))
>>>>>>>> parent of e9a4a0d (rollback):api/src/main/java/si/fri/rso/komentar/api/v1/resources/KomentarResource.java
=======
                            schema = @Schema(implementation = Komentar.class))
>>>>>>> parent of 410ac78 (init)
            ),
            @APIResponse(responseCode = "404",
                    description = "Comment with given ID doesn't exist.")
    })
    @GET
    @Path("/{komentarId}")
    public Response getKomentar(@Parameter(description = "Metadata ID.", required = true)
<<<<<<< HEAD
<<<<<<<< HEAD:api/src/main/java/si/fri/rso/priporocilni/api/v1/resources/PriporocilniResource.java
<<<<<<< HEAD
<<<<<<< HEAD
                                     @PathParam("komentarId") Integer komentarId) {
=======
<<<<<<< HEAD:api/src/main/java/si/fri/rso/priporocilni/api/v1/resources/PriporocilniResource.java
=======
>>>>>>> parent of 0fff523 (Revert "rollback")
                                     @PathParam("priporocilniId") Integer priporocilniId) {
>>>>>>> parent of 8446cdb (Revert "Revert "rollback"")

        log.info("Get comment with id: " + komentarId);

<<<<<<< HEAD
        Komentar komentar = komentarBean.getKomentar(komentarId);
=======
        Priporocilni priporocilni = priporocilniBean.getKomentar(priporocilniId);
<<<<<<< HEAD
=======
                                     @PathParam("komentarId") Integer imageMetadataId) {

        Komentar komentar = komentarBean.getKomentar(imageMetadataId);
>>>>>>> parent of aca6107 (popravljen openAPI):api/src/main/java/si/fri/rso/komentar/api/v1/resources/KomentarResource.java
>>>>>>> parent of 8446cdb (Revert "Revert "rollback"")
=======
>>>>>>> parent of 0fff523 (Revert "rollback")

========
=======
>>>>>>> parent of 410ac78 (init)
                                     @PathParam("komentarId") Integer komentarId) {

        log.info("Get comment with id: " + komentarId);

        Komentar komentar = komentarBean.getKomentar(komentarId);

<<<<<<< HEAD
>>>>>>>> parent of e9a4a0d (rollback):api/src/main/java/si/fri/rso/komentar/api/v1/resources/KomentarResource.java
>>>>>>> parent of e9a4a0d (rollback)
=======
>>>>>>> parent of 410ac78 (init)
        if (komentar == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(komentar).build();
    }

<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> parent of 410ac78 (init)
    @Operation(description = "Get comments by user ID.", summary = "Returns all comments posted by user with coresponding user ID.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Successfully returns chosen users comments.",
                    content = @Content(
                            schema = @Schema(implementation = Komentar.class, type = SchemaType.ARRAY))
            ),
            @APIResponse(responseCode = "404",
                    description = "User with given ID doesn't exist.")
    })
<<<<<<< HEAD
>>>>>>> parent of e9a4a0d (rollback)
=======
>>>>>>> parent of 410ac78 (init)
    @GET
    @Path("user/{userId}")
    public Response getKomentarByUser(@Parameter(description = "User ID.", required = true)
                                 @PathParam("userId") Integer userId) {

<<<<<<< HEAD
<<<<<<< HEAD
        List<Komentar> komentar = komentarBean.getKomentarByUser(userId);

        if (komentar == null) {
=======
=======
>>>>>>> parent of 410ac78 (init)
        log.info("Get all comments posted by user with id: " + userId);

        List<Komentar> komentar = komentarBean.getKomentarByUser(userId);

<<<<<<< HEAD
<<<<<<<< HEAD:api/src/main/java/si/fri/rso/priporocilni/api/v1/resources/PriporocilniResource.java
<<<<<<< HEAD
        if (komentar == null || komentar.isEmpty()) {
=======
        if (priporocilni == null || priporocilni.isEmpty()) {
<<<<<<< HEAD
=======
        List<Komentar> komentar = komentarBean.getKomentarByUser(userId);

        if (komentar == null) {
>>>>>>> parent of aca6107 (popravljen openAPI):api/src/main/java/si/fri/rso/komentar/api/v1/resources/KomentarResource.java
>>>>>>> parent of 8446cdb (Revert "Revert "rollback"")
=======
>>>>>>> parent of 0fff523 (Revert "rollback")
========
        if (komentar == null || komentar.isEmpty()) {
>>>>>>>> parent of e9a4a0d (rollback):api/src/main/java/si/fri/rso/komentar/api/v1/resources/KomentarResource.java
>>>>>>> parent of e9a4a0d (rollback)
=======
        if (komentar == null || komentar.isEmpty()) {
>>>>>>> parent of 410ac78 (init)
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(komentar).build();
    }
<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> parent of 410ac78 (init)
    @Operation(description = "Get comments by destinacija ID.", summary = "Returns all comments posted under destinacija with coresponding destinacija ID.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Successfully returns chosen destinations comments.",
                    content = @Content(
                            schema = @Schema(implementation = Komentar.class, type = SchemaType.ARRAY))
            ),
            @APIResponse(responseCode = "404",
                    description = "Destinacija with given ID doesn't exist.")
    })
<<<<<<< HEAD
>>>>>>> parent of e9a4a0d (rollback)
=======
>>>>>>> parent of 410ac78 (init)
    @GET
    @Path("destinacija/{destinacijaId}")
    public Response getKomentarByDestinacija(@Parameter(description = "Destinacija ID.", required = true)
                                 @PathParam("destinacijaId") Integer destinacijaId) {

<<<<<<< HEAD
<<<<<<< HEAD
        List<Komentar> komentar = komentarBean.getKomentarByDestinacija(destinacijaId);
=======
        log.info("Get all comments posted under destination with id: " + destinacijaId);

<<<<<<<< HEAD:api/src/main/java/si/fri/rso/priporocilni/api/v1/resources/PriporocilniResource.java
<<<<<<< HEAD
        List<Komentar> komentar = komentarBean.getKomentarByDestinacija(destinacijaId);
=======
        List<Priporocilni> priporocilni = priporocilniBean.getKomentarByDestinacija(destinacijaId);
<<<<<<< HEAD
=======
        List<Komentar> komentar = komentarBean.getKomentarByDestinacija(destinacijaId);
>>>>>>> parent of aca6107 (popravljen openAPI):api/src/main/java/si/fri/rso/komentar/api/v1/resources/KomentarResource.java
>>>>>>> parent of 8446cdb (Revert "Revert "rollback"")
=======
>>>>>>> parent of 0fff523 (Revert "rollback")
========
        List<Komentar> komentar = komentarBean.getKomentarByDestinacija(destinacijaId);
>>>>>>>> parent of e9a4a0d (rollback):api/src/main/java/si/fri/rso/komentar/api/v1/resources/KomentarResource.java
>>>>>>> parent of e9a4a0d (rollback)
=======
        log.info("Get all comments posted under destination with id: " + destinacijaId);

        List<Komentar> komentar = komentarBean.getKomentarByDestinacija(destinacijaId);
>>>>>>> parent of 410ac78 (init)

        if (komentar == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(komentar).build();
    }

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
    /*
    @Operation(description = "Add image metadata.", summary = "Add metadata")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Metadata successfully added."
            ),
            @APIResponse(responseCode = "405", description = "Validation error .")
    })
    @Counted(name = "number_of_created_katalog_destinacij")
    @POST
    public Response createKomentar(@RequestBody(
            description = "DTO object with destinacija metadata.",
            required = true, content = @Content(
            schema = @Schema(implementation = Komentar.class))) Komentar komentar) {

        if ((komentar.getTitle() == null || komentar.getDescription() == null)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else {
            komentar = komentarBean.createKomentar(komentar);
        }

        return Response.status(Response.Status.CONFLICT).entity(komentar).build();

    }
    */

    @Operation(description = "Update metadata for an destinacija.", summary = "Update metadata")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Metadata successfully updated."
            )
    })
    @PUT
    @Counted(name = "number_of_updated_katalog_destinacij")
    @Path("{komentarId}")
    public Response putImageMetadata(@Parameter(description = "Metadata ID.", required = true)
                                     @PathParam("komentarId") Integer imageMetadataId,
                                     @RequestBody(
                                             description = "DTO object with image metadata.",
=======
    @Operation(description = "Add new comment from given user to a destination.", summary = "Add comment")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Comment successfully added."
            ),
            @APIResponse(responseCode = "405", description = "Validation error .")
=======
=======
>>>>>>> parent of 410ac78 (init)

    @Operation(description = "Add new comment from given user to a destination.", summary = "Add comment")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Comment successfully added.",
                    content = @Content(
                            schema = @Schema(implementation = Komentar.class)
                    )
            ),
            @APIResponse(responseCode = "405",
                        description = "Either user ID or destinacija ID was not given")
<<<<<<< HEAD
>>>>>>> parent of e9a4a0d (rollback)
=======
>>>>>>> parent of 410ac78 (init)
    })
    @Counted(name = "num_of_posted_comments")
    @POST
    public Response postKomentarByDestinacija(@RequestBody(description = "DTO object with comment metadata and text",
                                                           required = true,
                                                           content = @Content(
                                                                   schema = @Schema(implementation = Komentar.class)
<<<<<<< HEAD
<<<<<<< HEAD
                                                           )) Komentar komentar) {

        System.out.println(komentar.getUstvarjen());
=======
                                                           )) Komentar komentar) throws IOException {

        log.info("Post new comment.");
>>>>>>> parent of e9a4a0d (rollback)
=======
                                                           )) Komentar komentar) throws IOException {

        log.info("Post new comment.");
>>>>>>> parent of 410ac78 (init)

        if (komentar.getLokacija_id() == null || komentar.getUser_id() == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if(komentar.getUstvarjen() == null){
            komentar.setUstvarjen(Instant.now());
        }

<<<<<<< HEAD
<<<<<<< HEAD
        System.out.println(komentar.getUstvarjen());

        return Response.status(Response.Status.CONFLICT).entity(komentarBean.createKomentar(komentar)).build();
    }

    @Operation(description = "Update comment from user on destinacija.", summary = "Update comment")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Comment successfully updated."
            )
    })
=======
=======
>>>>>>> parent of 410ac78 (init)
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

        // kdaj dobim exception Internal Exception: org.postgresql.util.PSQLException: ERROR: prepared statement "S_2" already exists
        // bi bilo idealno za error prevention.
<<<<<<< HEAD
<<<<<<<< HEAD:api/src/main/java/si/fri/rso/priporocilni/api/v1/resources/PriporocilniResource.java
<<<<<<< HEAD
<<<<<<< HEAD
        return Response.status(Response.Status.CREATED).entity(komentarBean.createKomentar(komentar)).build();
=======
<<<<<<< HEAD:api/src/main/java/si/fri/rso/priporocilni/api/v1/resources/PriporocilniResource.java
        return Response.status(Response.Status.CREATED).entity(priporocilniBean.createKomentar(priporocilni)).build();
=======
        return Response.status(Response.Status.CONFLICT).entity(komentarBean.createKomentar(komentar)).build();
>>>>>>> parent of aca6107 (popravljen openAPI):api/src/main/java/si/fri/rso/komentar/api/v1/resources/KomentarResource.java
>>>>>>> parent of 8446cdb (Revert "Revert "rollback"")
=======
        return Response.status(Response.Status.CREATED).entity(priporocilniBean.createKomentar(priporocilni)).build();
>>>>>>> parent of 0fff523 (Revert "rollback")
========
        return Response.status(Response.Status.CREATED).entity(komentarBean.createKomentar(komentar)).build();
>>>>>>>> parent of e9a4a0d (rollback):api/src/main/java/si/fri/rso/komentar/api/v1/resources/KomentarResource.java
=======
        return Response.status(Response.Status.CREATED).entity(komentarBean.createKomentar(komentar)).build();
>>>>>>> parent of 410ac78 (init)
    }

    @Operation(description = "Update comment from user on destinacija.", summary = "Update comment with corresponding komentar ID.")
    @APIResponses({
            @APIResponse(
                    responseCode = "201",
                    description = "Comment successfully updated.",
                    content = @Content(
                            schema = @Schema(implementation = Komentar.class)
                    )
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Comment with given komentar ID was not found, hence cannot be updated."
            )
            })
<<<<<<< HEAD
>>>>>>> parent of e9a4a0d (rollback)
=======
>>>>>>> parent of 410ac78 (init)
    @PUT
    @Counted(name = "number_of_updated_comments")
    @Path("{komentarId}")
    public Response putImageMetadata(@Parameter(description = "Metadata ID.", required = true)
                                     @PathParam("komentarId") Integer komentarId,
                                     @RequestBody(
                                             description = "DTO object with comment.",
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> parent of 8446cdb (Revert "Revert "rollback"")
                                             required = true, content = @Content(
                                             schema = @Schema(implementation = Komentar.class)))
                                     Komentar komentar){

<<<<<<< HEAD
        komentar = komentarBean.putKomentar(imageMetadataId, komentar);
=======
        System.out.println(komentar.getKomentar());
=======
=======
>>>>>>> parent of 410ac78 (init)
                                             required = true, content = @Content(
                                             schema = @Schema(implementation = Komentar.class)))
                                     Komentar komentar) throws IOException{

        log.info("Update comment.");
<<<<<<< HEAD
>>>>>>> parent of e9a4a0d (rollback)
=======
>>>>>>> parent of 410ac78 (init)

        if(komentar.getUstvarjen() == null){
            komentar.setUstvarjen(Instant.now());
        }

        komentar = komentarBean.putKomentar(komentarId, komentar);
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> parent of 8446cdb (Revert "Revert "rollback"")
=======
>>>>>>> parent of e9a4a0d (rollback)
=======
>>>>>>> parent of 410ac78 (init)

        if (komentar == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

<<<<<<< HEAD
<<<<<<< HEAD
        return Response.status(Response.Status.NOT_MODIFIED).build();

    }

<<<<<<< HEAD
    @Operation(description = "Delete metadata for an destinacija.", summary = "Delete metadata")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Metadata successfully deleted."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
=======
    @Operation(description = "Delete comment with given id.", summary = "Delete comment")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
=======
=======
>>>>>>> parent of 410ac78 (init)
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
<<<<<<< HEAD
>>>>>>> parent of e9a4a0d (rollback)
=======
>>>>>>> parent of 410ac78 (init)
                    description = "Comment successfully deleted."
            ),
            @APIResponse(
                    responseCode = "404",
<<<<<<< HEAD
<<<<<<< HEAD
                    description = "Comment not found."
>>>>>>> parent of 8446cdb (Revert "Revert "rollback"")
=======
                    description = "Comment with given comment ID was not found."
>>>>>>> parent of e9a4a0d (rollback)
=======
                    description = "Comment with given comment ID was not found."
>>>>>>> parent of 410ac78 (init)
            )
    })
    @DELETE
    @Counted(name = "number_of_deleted_comments")
    @Path("{komentarId}")
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
    public Response deleteKomentar(@Parameter(description = "Metadata ID.", required = true)
=======
    public Response deleteKomentar(@Parameter(description = "Comment ID.", required = true)
>>>>>>> parent of 8446cdb (Revert "Revert "rollback"")
                                        @PathParam("komentarId") Integer komentarId){

        boolean deleted = komentarBean.deleteKomentar(komentarId);

<<<<<<< HEAD
=======
        System.out.println("Delete Comment with id " + komentarId + ".");

>>>>>>> parent of 8446cdb (Revert "Revert "rollback"")
=======
    public Response deleteKomentar(@Parameter(description = "Comment ID.", required = true)
                                        @PathParam("komentarId") Integer komentarId){

<<<<<<<< HEAD:api/src/main/java/si/fri/rso/priporocilni/api/v1/resources/PriporocilniResource.java
<<<<<<< HEAD
<<<<<<< HEAD
        log.info("Delete comment with id: " + komentarId);

        boolean deleted = komentarBean.deleteKomentar(komentarId);
=======
<<<<<<< HEAD:api/src/main/java/si/fri/rso/priporocilni/api/v1/resources/PriporocilniResource.java
        log.info("Delete comment with id: " + priporocilniId);

        boolean deleted = priporocilniBean.deleteKomentar(priporocilniId);
=======
        boolean deleted = komentarBean.deleteKomentar(komentarId);
>>>>>>> parent of aca6107 (popravljen openAPI):api/src/main/java/si/fri/rso/komentar/api/v1/resources/KomentarResource.java

        System.out.println("Delete Comment with id " + komentarId + ".");
>>>>>>> parent of 8446cdb (Revert "Revert "rollback"")
=======
        log.info("Delete comment with id: " + priporocilniId);

        boolean deleted = priporocilniBean.deleteKomentar(priporocilniId);
>>>>>>> parent of 0fff523 (Revert "rollback")
========
        log.info("Delete comment with id: " + komentarId);

        boolean deleted = komentarBean.deleteKomentar(komentarId);
>>>>>>>> parent of e9a4a0d (rollback):api/src/main/java/si/fri/rso/komentar/api/v1/resources/KomentarResource.java

>>>>>>> parent of e9a4a0d (rollback)
=======
    public Response deleteKomentar(@Parameter(description = "Comment ID.", required = true)
                                        @PathParam("komentarId") Integer komentarId){

        log.info("Delete comment with id: " + komentarId);

        boolean deleted = komentarBean.deleteKomentar(komentarId);

>>>>>>> parent of 410ac78 (init)
        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }





}
