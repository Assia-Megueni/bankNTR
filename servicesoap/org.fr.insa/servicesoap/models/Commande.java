package servicesoap.models;

import servicesoap.models.personne.Client;
import servicesoap.models.personne.Vendeur;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Commande")
public class Commande {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int idCommande;

	@ManyToOne
	private Client client;

	private boolean estPaye;
	private boolean estRembourse;

	public Commande(){
		// TODO
	}

	public Commande(Client client) {
		this.client = client;this.estPaye = false;this.estRembourse = false;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public boolean isEstPaye() {
		return estPaye;
	}

	public void setEstPaye(boolean estPaye) {
		this.estPaye = estPaye;
	}

	public boolean isEstRembourse() {
		return estRembourse;
	}

	public void setEstRembourse(boolean estRembourse) {
		this.estRembourse = estRembourse;
	}

	public int getIdCommande() {
		return idCommande;
	}

}
