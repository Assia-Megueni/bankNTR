package service.RestApp;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
@ApplicationPath("/resources")
public class ApplicationRestJson extends Application{
	public ApplicationRestJson() {
		JSONServices.service.ajouterCompte("RANDRIANANDRASANA", "Harivelo",300.0);
		JSONServices.service.ajouterCompte("LELEUX", "Celia",450.5);
	}
}
