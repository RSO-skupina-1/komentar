package si.fri.rso.komentar.api.v1.resources;

import si.fri.rso.komentar.services.config.RestProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

@ApplicationScoped
@Path("komentar/config")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DemoResource {

    private Logger log = Logger.getLogger(DemoResource.class.getName());

    @Inject
    private RestProperties restProperties;

    @GET
    public Response isHealthy() {

        if (restProperties.getBroken()) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        }

        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("break")
    public Response makeUnhealthy() {

        log.warning("Setting service to unavailable!");
        restProperties.setBroken(true);

        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("fix")
    public Response makeHealthy() {

        log.info("Setting service to available!");

        restProperties.setBroken(false);

        return Response.status(Response.Status.OK).build();
    }
}
