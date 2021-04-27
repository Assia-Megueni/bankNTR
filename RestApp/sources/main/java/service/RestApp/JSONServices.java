package service.RestApp;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import resources.RestApp.Compte;
import resources.RestApp.ServiceCompte;
@Path("/json/compte")
public class JSONServices {
	static ServiceCompte service =new ServiceCompte();
	
	@GET
    @Path("/sold/{nom}/{prenom}")
    @Produces(MediaType.APPLICATION_JSON)
	public String getSoldCompteJson(@PathParam("nom") String nom,@PathParam("prenom") String prenom)
	{
		Compte compte=new Compte(nom,prenom);
		System.out.println(service.getCompteList());
		service.rechercheCompte(nom, prenom);
		compte=service.rechercheCompte(nom, prenom);
		compte.getSold();
		System.out.println(compte.getSold());
		return "<solde>"+compte.getSold()+"</solde>";
		
	}
	/*
	 * Foction permet d'afficher tous les compte
	 */
	@GET
    @Path("/listeCompte")
    @Produces(MediaType.APPLICATION_JSON)
	//afficher les comptes
	public static List<Compte> listCompteJson()
	{
		
		System.out.println(service.getCompteList());
		return service.getCompteList();
	}
	/*
	 * Fonction pour crediter compte
	 */
	@PUT
	@Path("/crediter/{nom}/{prenom}/{sold}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Compte CrediterSonCompteJson(@PathParam("nom") String nom,
			@PathParam("prenom") String prenom,@PathParam("sold") double sold)
	{
		Compte compte=null;
		compte=service.rechercheCompte(nom, prenom);
		if(compte!=null)
		service.rechercheCompte(nom, prenom).crediterCompte(sold);
		System.out.print(service.rechercheCompte(nom, prenom).getNom()+"\n");
		System.out.print(service.rechercheCompte(nom, prenom).getPrenom()+"\n");
		System.out.print(service.rechercheCompte(nom, prenom).getSold()+"\n");
		return compte;
	}
	/*
	 * Fonction pour debiter compte
	 */
	@PUT
	@Path("/debiter/{nom}/{prenom}/{sold}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Compte debiteterSonCompteJson(@PathParam("nom") String nom,
			@PathParam("prenom") String prenom,@PathParam("sold") double sold)
	{
		Compte compte=null;
		compte=service.rechercheCompte(nom, prenom);
		if(compte!=null)
		service.rechercheCompte(nom, prenom).debiterCompte(sold);
		System.out.print(service.rechercheCompte(nom, prenom).getNom()+"\n");
		System.out.print(service.rechercheCompte(nom, prenom).getPrenom()+"\n");
		System.out.print(service.rechercheCompte(nom, prenom).getSold()+"\n");
		return compte;
	}
	

}
