package service.RestApp;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import resources.RestApp.Compte;
import resources.RestApp.ServiceCompte;

@Path("/xml/compte")
public class XMLServices {
	List<Compte>lesComptes=new ArrayList<Compte>();
	//Compte c2= new Compte("LELEUX","Célia");
	static ServiceCompte service =new ServiceCompte();
	/*
	 * methode qui fait appel à la methode @POST pour creer u compte
	 */
	@POST
    @Path("/{nom}/{prenom}/{sold}")
    @Produces(MediaType.APPLICATION_XML)
    public Compte getCompteInXML(@PathParam("nom") String nom,@PathParam("prenom") String prenom,@PathParam("sold") double sold)
	{
    	System.out.println("getCompteInXML");
    	Compte compte = new Compte();
        compte.setNom(nom);
        compte.setPrenom(prenom);
        compte.crediterCompte(sold);
        service.ajouterCompte(nom, prenom, sold);
        return compte;
    }
	/*
	 * methode qui permet d'afficher la liste de tous les compte existant 
	 */
	@GET
    @Path("/listeCompte")
    @Produces(MediaType.APPLICATION_XML)
	//afficher les comptes
	public static List<Compte> listCompte()
	{
		
		System.out.println(service.getCompteList());
		return service.getCompteList();
	}
	/*
	 * methode permet d'afficher un compte existant
	 * elle prend comme parametre nom,prenom
	 */
	@GET
    @Path("/recherche/{nom}/{prenom}")
    @Produces(MediaType.APPLICATION_XML)
	public Compte trouverCompte(@PathParam("nom") String nom,@PathParam("prenom") String prenom)
	{
		
		lesComptes=service.getCompteList();
		System.out.println(lesComptes);
		return service.rechercheCompte(nom, prenom);
		
	}
	/*
	 * methode permet d'affiche le sold d'un compte et 
	 * prend comme parametre nom, prénom
	 */
	@GET
    @Path("/sold/{nom}/{prenom}")
    @Produces(MediaType.APPLICATION_XML)
	public String getSoldCompte(@PathParam("nom") String nom,@PathParam("prenom") String prenom)
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
	 * methode permet de crediter compte
	 */
	@PUT
	@Path("/crediter/{nom}/{prenom}/{sold}")
	@Consumes(MediaType.APPLICATION_XML)
	public Compte CrediterSonCompte(@PathParam("nom") String nom,
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
	 * methode permet de debiter compte avec @PUT
	 */
	@PUT
	@Path("/debiter/{nom}/{prenom}/{sold}")
	@Consumes(MediaType.APPLICATION_XML)
	public Compte debiteterSonCompte(@PathParam("nom") String nom,
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
