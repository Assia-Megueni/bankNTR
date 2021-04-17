package service.RestApp;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
	@GET
    @Path("/{nom}/{prenom}/{sold}")
    @Produces(MediaType.APPLICATION_XML)
    public Compte getCompteInXML(@PathParam("nom") String nom,@PathParam("prenom") String prenom,@PathParam("sold") double sold)
	{
    	System.out.println("getCompteInXML");
        Compte compte = new Compte();
        compte.setNom(nom);
        compte.setPrenom(prenom);
        compte.crediterCompte(sold);
        lesComptes.add(compte);
        return compte;
    }
	@GET
    @Path("/listeCompte")
    @Produces(MediaType.APPLICATION_XML)
	//afficher les comptes
	public static List<Compte> listCompte()
	{
		//List<Compte> xmlListCompte= new ArrayList<Compte>();
		//lesComptes=service.getCompteList();
		service.ajouterCompte("RANDRIANANDRASANA", "Harivelo",300.0);
		service.ajouterCompte("LELEUX", "Celia",450.5);
		//System.out.println(service.getCompteList());
		System.out.println(service.getCompteList());
		return service.getCompteList();
	}
	@GET
    @Path("/recherche/{nom}/{prenom}")
    @Produces(MediaType.APPLICATION_XML)
	public Compte trouverCompte(@PathParam("nom") String nom,@PathParam("prenom") String prenom)
	{
		
		lesComptes=service.getCompteList();
		System.out.println(lesComptes);
		return service.rechercheCompte(nom, prenom);
		
	}
	
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
	/*
	@GET
	@Path("/listCompteInxml/{nom}/{prenom}/{sold}")
	@Produces(MediaType.APPLICATION_XML)
	 
	public List<Compte> listCompteXML(@PathParam("nom") String nom,@PathParam("prenom") String prenom,@PathParam("sold") double sold){
		//List<Compte>lesComptes=new ArrayList<Compte>();
		
		Compte c1=new Compte(nom, prenom);
		c1.crediterCompte(sold);
		if(lesComptes.equals(null)) 
		{
				lesComptes.add(new Compte(nom, prenom));
				//System.out.println(lesComptes);
		}
		else
			{for (Compte c :lesComptes)
				{
					if((c1.getNom()).equals(c.getNom()) && (c1.getPrenom()).equals(c.getPrenom()))
						return lesComptes;
				}
			}
		lesComptes.add(new Compte(nom, prenom));
		System.out.println(lesComptes);
		return lesComptes;
	}
	@GET
	 @Produces("application/xml")
	 @Path("/listxml")
	 public List<Compte> xmlliste() {
	 //List<Compte> xmlList = new ArrayList<Compte>();
	 lesComptes.add(new Compte("LELEUX","Célia"));
	 
	 lesComptes.add(new Compte("RANDRIANANDRASANA","Harivelo"));
	 System.out.println(lesComptes);
	 return lesComptes;
	 }
	@GET
	 @Produces("application/xml")
	 @Path("/getSol")
	 public String soldCompte() {
	
	 Compte C1=new Compte("LELEUX","Célia");
	 C1.crediterCompte(400);
	 System.out.println(C1.getSold());
	 return "Votre solde est :"+ service.consulterSolde(C1);
	 }
	*/
}
