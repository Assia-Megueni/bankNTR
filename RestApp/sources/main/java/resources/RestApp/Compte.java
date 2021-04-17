package resources.RestApp;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Compte")
//@XmlAccessorType(XmlAccessType.FIELD)
public class Compte implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2100375569114687579L;
	static Integer idCompte=0;
	String nom;
	String prenom;
	Double sold;
	public Compte() {
		super();
		idCompte++;
		this.sold=0.0;
		// TODO Auto-generated constructor stub
	}
	public Compte(String nom,String prenom)
	{
		idCompte++;
		this.nom=nom;
		this.prenom=prenom;
		this.sold=0.0;
	}
	//@XmlAttribute
	public static int getIdCompte() {
		return idCompte;
	}
	public static void setIdCompte(int idCompte) {
		Compte.idCompte = idCompte;
	}
	//@XmlAttribute
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	//@XmlElement
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	@XmlElement(required = true)
	public Double getSold() {
		return sold;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	//@XmlAttribute
	public double crediterCompte(double montant) {
		this.sold =this.getSold()+montant;
		return this.sold;
	}
	//@XmlAttribute
	public double debiterCompte(double montant) {
		if(this.getSold()-montant>=0) {
			this.sold =this.getSold()-montant;
		}
		return this.sold ;
	}

}
