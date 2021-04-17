package service.RestApp;


import java.util.List;

import javax.ws.rs.GET;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import resources.RestApp.Compte;
@Path("")
public class ServicesClass {
	@GET
	@Path("/donnee")
	@Produces("text/plain")
	public String addIt() {
        return "Resultat GET!";
	}
	@GET
	@Path("/comptexml")
	@Produces(MediaType.APPLICATION_XML)
	public double getSold(){
		
		Compte c= new Compte("RANDRIANANDRASASANA","Harivelo");
		return c.getSold();
	}

}
