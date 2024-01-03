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
                                             required = true, content = @Content(
                                             schema = @Schema(implementation = Komentar.class)))
                                     Komentar komentar){

        komentar = komentarBean.putKomentar(imageMetadataId, komentar);

        if (komentar == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.NOT_MODIFIED).build();

    }

    @Operation(description = "Delete metadata for an destinacija.", summary = "Delete metadata")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Metadata successfully deleted."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @DELETE
    @Counted(name = "number_of_deleted_comments")
    @Path("{komentarId}")
    public Response deleteKomentar(@Parameter(description = "Metadata ID.", required = true)
                                        @PathParam("komentarId") Integer komentarId){

        boolean deleted = komentarBean.deleteKomentar(komentarId);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }





}
