package ServeurWildfly.WebAppWildFly;


import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ServeurWildfly.WebAppWildFly.service.CompteService;
@Path("/src")
public class RequestClass {
	CompteService compteService =new CompteService();
	@GET
	@Produces("text/plain")
	
	public String getIt() {
        return "Resulta! Ok !";
	}
	@POST
	@Path("/donnee_post")
	@Consumes("application/json")
	public String addIt() {
        return "Resultat Post! Ok !";
	}
	/*@GET
	@Path("/donnee")
	@Produces("Application/json")
	
	public String getJson() {
       JsonObject value =Json.createObjectBuilder()
    		   .add("info","JsonMessage")
    		   .add("message","WildfyRest").build();
       return value.toString();
    		   
	}
	@GET
	@Path("/compte")
	@Produces("text/plain")
	public String getCompte() {
		return ("voila");
		
	}*/
	@GET
	@Path("/comptexml")
	@Produces(MediaType.APPLICATION_XML)
	public List<CompteBancaire> getCompteBancaire(){
		
		return compteService.getCompte();
	}
	@GET
	@Path("/compteJson")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CompteBancaire> getCompteBancaireJSON(){
		
		return compteService.getCompte();
	}
	@POST
	@Path("/compteJsonP")
	@Produces(MediaType.APPLICATION_JSON)
	public double envoyerSoldeCompte() {
		return compteService.hashCode();
	}
}
