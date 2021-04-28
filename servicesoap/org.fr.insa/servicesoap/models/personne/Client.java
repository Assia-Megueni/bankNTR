package servicesoap.models.personne;

import javax.persistence.*;

@Entity
@DiscriminatorValue("client")
public class Client extends Personne{

	public Client() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Client(String nom, String prenom, String motdepasse) {
		super(nom, prenom, motdepasse);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("{");
		sb.append("nom:'").append(nom).append('\'');
		sb.append(", prenom:'").append(prenom).append('\'');
		sb.append(", idPersonne:").append(idPersonne);
		sb.append('}');
		return sb.toString();
	}
}
