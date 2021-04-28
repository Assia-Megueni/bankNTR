package servicesoap.models.personne;

import javax.persistence.*;

@Entity(name = "Personne")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)/* crée une table pour chaque classe fille */
public abstract class Personne {
	protected String nom,prenom,motdepasse;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected int idPersonne;
	
	public Personne() {
		this("","","");
	}
	
	public Personne(String nom, String prenom, String motdepasse) {
		this.nom = nom;
		this.prenom = prenom;
		this.motdepasse = motdepasse;
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

	public String getMotdepasse() {
		return motdepasse;
	}

	public void setMotdepasse(String motdepasse) {
		this.motdepasse = motdepasse;
	}

	public int getIdPersonne() {
		return idPersonne;
	}
	
	
}
