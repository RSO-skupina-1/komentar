package si.fri.rso.komentar.api.v1;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(title = "Komentar API", version = "v1",
        contact = @Contact(email = "dg6781@fri.uni-lj.si"),
        license = @License(name = "dev"), description = "API for managing comments."),
        servers = @Server(url = "http://localhost:8081/"))
@ApplicationPath("/v1")
public class KomentarApplication extends Application {

}
