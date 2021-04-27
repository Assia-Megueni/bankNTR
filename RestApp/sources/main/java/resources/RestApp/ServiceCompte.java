package resources.RestApp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
public class ServiceCompte {
	List<Compte> lesComptes=new ArrayList<Compte>();
	
	public ServiceCompte() {
		super();
	}
	public List<Compte> getCompteList()
	{
		return lesComptes;
	}
	
	public void setLesComptes(List<Compte> lesComptes) {
		this.lesComptes = lesComptes;
	}
	public List<Compte> ajouterCompte(String nom,String prenom,Double montant)
	{
		Compte compte=new Compte(nom,prenom);
		compte.crediterCompte(montant);
		if(lesComptes.equals(null)) {
			
			lesComptes.add(compte);
				return lesComptes;
			}
		else
		{
			for(Compte c: lesComptes)
			{
				if(c.getNom().equalsIgnoreCase(nom)) {
						if(c.getPrenom().equalsIgnoreCase(prenom));
					}									
			}
			lesComptes.add(compte);
		}
		return lesComptes;
		
	}
	/*
	 *methode qui permet de créer un compte
	 *elle prend comme parametre nom n , prenom p et le sold  s
	 */
	public Compte createCompte(String n, String p,double s)
	{
		Compte compte = null;
		if(lesComptes==null)
		{
			compte=new Compte(n,p);
			lesComptes.add(compte);
			return compte;
		}
		else
		{
			for(Compte c: lesComptes)
			{
				if((n).equals(c.nom))
				{
					if((p).equals(c.prenom))
						compte=new Compte(n,p);
						lesComptes.add(compte);
				}
			}
			return compte;
		}
	}
	
	/*
	 *methode qui permet de faire une recherche compte 
	 * en fonction de nom et prenom donnés dans le parametre
	 */
	public Compte  rechercheCompte(final String nom, String prenom)
	{
		List<Compte> result = lesComptes.stream()             // convert list to stream
              	.filter(compte -> (nom.equals(compte.nom) && nom.equals(compte.nom)))   // we dont like mkyong
		        .collect(Collectors.toList());
				
				if(result.equals(null)) return null;
				if(result.isEmpty())
					return null;
				else return result.get(0);
		
		//boolean resultat=true;
	
	}
	public double consulterSolde(Compte compte)
	{
		return compte.getSold();
	}
	/*
	 * methode permet de crediter un compte c d'un montant donné dans le parametre
	 */
	public double CrediterSonCompte(Compte c,double montant)
	{
		return c.crediterCompte(montant);
	}
	public double debiterSonCompte(Compte c, double montant)
	{
		return c.debiterCompte(montant);
	}
	/*
	 * methode permet de debiter un compte c d'un montant comme parametre
	 */
	public Compte afficherCompte(Compte compte)
	{
		return compte;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
