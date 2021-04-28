package servicesoap.models;

import servicesoap.models.personne.Vendeur;

import javax.persistence.*;

@Entity(name = "ArticleB")
public class Produit {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int idProduit;
	private String nom, categorie;
	private double prix;
	private int stock;

	@ManyToOne
	private Vendeur vendeur;

	public Produit(){

	}
	public Produit(String nom, String categorie, double prix) {
		this.nom = nom;
		this.categorie = categorie;
		this.prix = prix;
		this.stock = 0;
	}

	public int getIdProduit() {
		return idProduit;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public Vendeur getVendeur() {
		return vendeur;
	}

	public void setVendeur(Vendeur vendeur) {
		this.vendeur = vendeur;
	}

	public int getStock() {
		return stock;
	}

	public void addStock(int stock) {
		this.stock += stock;
	}

	public boolean removeStock(int stock) {
		if((this.stock - stock) < 0)return false;
		this.stock -= stock;
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("{");
		sb.append("idProduit:").append(idProduit);
		sb.append(", nom:'").append(nom).append('\'');
		sb.append(", categorie:'").append(categorie).append('\'');
		sb.append(", prix:").append(prix);
		sb.append(", stock:").append(stock);
		sb.append(", vendeur:").append(vendeur);
		sb.append('}');
		return sb.toString();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
