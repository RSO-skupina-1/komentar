package si.fri.rso.komentar.api.v1.resources;

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
import si.fri.rso.komentar.lib.Komentar;
import si.fri.rso.komentar.services.beans.KomentarBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.time.Instant;
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
    @Operation(description = "Get all comments.", summary = "Get all metadata")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of comments",
                    content = @Content(schema = @Schema(implementation = Komentar.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of objects in list")}
            )})
    @GET
    public Response getKomentar() {
        log.info("Get all comments.") ;
        List<Komentar> komentar = komentarBean.getKomentarFilter(uriInfo);

        return Response.status(Response.Status.OK).entity(komentar).build();
    }


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

        if (komentar == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(komentar).build();
    }

    @GET
    @Path("user/{userId}")
    public Response getKomentarByUser(@Parameter(description = "User ID.", required = true)
                                 @PathParam("userId") Integer userId) {

        List<Komentar> komentar = komentarBean.getKomentarByUser(userId);

        if (komentar == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(komentar).build();
    }
    @GET
    @Path("destinacija/{destinacijaId}")
    public Response getKomentarByDestinacija(@Parameter(description = "Destinacija ID.", required = true)
                                 @PathParam("destinacijaId") Integer destinacijaId) {

        List<Komentar> komentar = komentarBean.getKomentarByDestinacija(destinacijaId);

        if (komentar == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(komentar).build();
    }

    @Operation(description = "Add new comment from given user to a destination.", summary = "Add comment")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Comment successfully added."
            ),
            @APIResponse(responseCode = "405", description = "Validation error .")
    })
    @Counted(name = "num_of_posted_comments")
    @POST
    public Response postKomentarByDestinacija(@RequestBody(description = "DTO object with comment metadata and text",
                                                           required = true,
                                                           content = @Content(
                                                                   schema = @Schema(implementation = Komentar.class)
                                                           )) Komentar komentar) {

        System.out.println(komentar.getUstvarjen());

        if (komentar.getLokacija_id() == null || komentar.getUser_id() == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if(komentar.getUstvarjen() == null){
            komentar.setUstvarjen(Instant.now());
        }

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
    @PUT
    @Counted(name = "number_of_updated_comments")
    @Path("{komentarId}")
    public Response putImageMetadata(@Parameter(description = "Metadata ID.", required = true)
                                     @PathParam("komentarId") Integer komentarId,
                                     @RequestBody(
                                             description = "DTO object with comment.",
                                             required = true, content = @Content(
                                             schema = @Schema(implementation = Komentar.class)))
                                     Komentar komentar){

        System.out.println(komentar.getKomentar());

        if(komentar.getUstvarjen() == null){
            komentar.setUstvarjen(Instant.now());
        }

        komentar = komentarBean.putKomentar(komentarId, komentar);

        if (komentar == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.NOT_MODIFIED).build();

    }

    @Operation(description = "Delete comment with given id.", summary = "Delete comment")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Comment successfully deleted."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Comment not found."
            )
    })
    @DELETE
    @Counted(name = "number_of_deleted_comments")
    @Path("{komentarId}")
    public Response deleteKomentar(@Parameter(description = "Comment ID.", required = true)
                                        @PathParam("komentarId") Integer komentarId){

        boolean deleted = komentarBean.deleteKomentar(komentarId);

        System.out.println("Delete Comment with id " + komentarId + ".");

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }





}
