package servicesoap.models;

import servicesoap.models.personne.Client;

import javax.persistence.*;

@Entity(name = "ItemB")
public class Items {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int id;

	@ManyToOne
	private Produit article;

	@ManyToOne
	private Commande commande;
	private int nb;

	public Items() {
		this(null,0,null);
	}

	public Items(Produit article, int nb, Commande commande) {
		this.article = article;
		this.nb = nb;
		this.commande = commande;
	}

	public void setNb(int nb) {
		this.nb = nb;
	}

	public int getId() {
		return id;
	}

	public Produit getArticle() {
		return article;
	}

	public int getNb() {
		return nb;
	}

	public void setArticle(Produit article) {
		this.article = article;
	}

	public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("{");
		sb.append("id:").append(id);
		sb.append(", article:").append(article);
		sb.append(", nb:").append(nb);
		sb.append(", commande:").append(commande);
		sb.append('}');
		return sb.toString();
	}
}
