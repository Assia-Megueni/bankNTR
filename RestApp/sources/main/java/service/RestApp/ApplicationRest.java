package service.RestApp;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
@ApplicationPath("/resources")
public class ApplicationRest extends Application{
	public ApplicationRest() {
		XMLServices.service.ajouterCompte("RANDRIANANDRASANA", "Harivelo",300.0);
		XMLServices.service.ajouterCompte("LELEUX", "Celia",450.5);
	}
}
