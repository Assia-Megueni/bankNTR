package ServeurWildfly.WebAppWildFly;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CompteBancaire {
	
	static int idCompte=0;
	String nom;
	String prenom;
	double sold;
	public CompteBancaire() {super();}
	public CompteBancaire(String n,String p,Double s){
		idCompte++;
		nom=n;
		prenom= p;
		sold =s;
		
	}
	public int getIdCompte() {
		return idCompte;
	}
	
	public void setIdCompte(int idCompte) {
		this.idCompte = idCompte;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public double getSold() {
		return sold;
	}
	public void setSold(double sold) {
		this.sold = sold;
	}
	@Override
	public String toString() {
		return "CompteBancaire [getIdCompte()=" + getIdCompte() + ", getNom()=" + getNom() + ", getPrenom()="
				+ getPrenom() + ", getSold()=" + getSold() + "]";
	}
	
	
}
