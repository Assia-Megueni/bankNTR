package servicesoap.models.personne;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("vendeur")
public class Vendeur extends Personne{

	public Vendeur() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Vendeur(String nom, String prenom, String motdepasse) {
		super(nom, prenom, motdepasse);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "{" +
				"nom:'" + nom + '\'' +
				", prenom:'" + prenom + '\'' +
				", idPersonne:" + idPersonne +
				'}';
	}
}
