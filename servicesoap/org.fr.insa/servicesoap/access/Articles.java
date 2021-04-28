package servicesoap.access;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import servicesoap.models.Produit;
import servicesoap.models.personne.Personne;
import servicesoap.models.personne.Vendeur;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.Collection;

/**
 * Contient l'ensemble des requêtes SOAP pour la gestion des articles et leurs stocks
 * @author bverhul
 *
 */
public class Articles {
	/**
	 * Permet de créer un article en base
	 * @param nom
	 * @param categorie
	 * @param prix
	 * @param idVendeur : identifiant unique du vendeur
	 * @return true si l'opération s'est bien passée
	 */
	public boolean ajoutArticle(String nom, String categorie, double prix,int idVendeur) {
		/* vérification des paramètres */
		if((nom.equals(""))||(categorie.equals("")))return false;
		/* recherche du vendeur */
		Personne p = null;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("user");
		EntityManager em = emf.createEntityManager();
		try {
			p = (Personne) em.createQuery("SELECT p FROM Vendeur p WHERE p.idPersonne = :id")
					.setParameter("id", idVendeur)
					.getSingleResult();
		}catch(Exception e) {

		}
		em.close();
		emf.close();
		/* normalisation des données */
		String nomF = nom.trim();
		String categorieF = categorie.trim();
		/* envoi en base de données */
		emf = Persistence.createEntityManagerFactory("article");
		em = emf.createEntityManager();

		em.getTransaction().begin();
		Produit produit = new Produit(nomF,categorieF,prix);
		produit.setVendeur((Vendeur) p);
		em.persist(produit);
		em.getTransaction().commit();

		em.close();
		emf.close();
		return true;
	}
	/***
	 * Permet d'ajouter du stock à un article
	 * @param idArticle
	 * @param quantité qui doit être strictement positive
	 * @return true si l'opération s'est bien passée
	 */
	public boolean ajoutStock(int idArticle, int quantité) {
		/* vérification du paramètre quantité */
		if(quantité <= 0)return false;
		/* connexion à la base de données */
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("article");
		EntityManager em = emf.createEntityManager();

		Produit produit = em.find(Produit.class, idArticle);
		em.getTransaction().begin();
		if(produit != null)produit.addStock(quantité);
		em.getTransaction().commit();
		em.close();
		emf.close();
		return produit!=null;
	}

	/***
	 * Permet de retirer du stock à un article
	 * @param idArticle
	 * @param quantité qui doit être positive
	 * @return true si l'opération s'est bien passée
	 */
	public boolean retirerStock(int idArticle, int quantité) {
		boolean result = false;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("article");
		EntityManager em = emf.createEntityManager();

		Produit produit = em.find(Produit.class, idArticle);
		em.getTransaction().begin();
		if(produit != null)result = produit.removeStock(quantité);
		em.getTransaction().commit();
		em.close();
		emf.close();
		return result;
	}

	/**
	 * Permet de changer le prix d'un article
	 * @param idArticle : identifiant unique de l'article
	 * @param prix : prix qui doit être supérieur ou égal à 0
	 * @return true si l'opération a pu se faire
	 */
	public boolean changerPrix(int idArticle, double prix) {
		/* vérification du paramètre prix */
		if(prix < 0)return false;
		/* appel base de données */
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("article");
		EntityManager em = emf.createEntityManager();

		Produit produit = em.find(Produit.class, idArticle);
		em.getTransaction().begin();
		if(produit != null)produit.setPrix(prix);
		em.getTransaction().commit();
		em.close();
		emf.close();
		return produit != null;
	}

	/**
	 * Permet de changer la catégorie
	 * @param idArticle : identifiant unique de l'article
	 * @param categorie
	 * @return true si l'opération a pu se faire
	 */
	public boolean changerCatégorie(int idArticle, String categorie) {
		/* vérification du paramètre catégorie */
		if(categorie.equals(""))return false;
		/* appel base de données */
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("article");
		EntityManager em = emf.createEntityManager();

		Produit produit = em.find(Produit.class, idArticle);
		em.getTransaction().begin();
		if(produit != null)produit.setCategorie(categorie);
		em.getTransaction().commit();
		em.close();
		emf.close();
		return produit != null;
	}
	/**
	 * Liste l'ensemble des articles présents
	 * @return la liste en JSON
	 */
	public String getAllArticles(){
		JSONArray list = new JSONArray();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("article");
		EntityManager em = emf.createEntityManager();

		Query query = em.createQuery("SELECT a FROM ArticleB a");
		Collection<Produit> ce = (Collection<Produit>) query.getResultList();

		ce.forEach(produit -> {
			JSONObject obj = new JSONObject();
			obj.put("id", produit.getIdProduit());
			obj.put("nom", produit.getNom());
			obj.put("prix", produit.getPrix());
			obj.put("categorie", produit.getCategorie());
			obj.put("stock", produit.getStock());
			obj.put("vendeur", produit.getVendeur());
			list.add(obj);
		});
		em.close();
		emf.close();
		return list.toJSONString();/* convertit la liste en JSON */
	}

	/**
	 * Permet d'obtenir l'article d'identifiant id
	 * @param id
	 * @return l'article en json ou {} si il n'existe pas
	 */
	public String getArticle(int id){
		JSONObject obj = new JSONObject();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("article");
		EntityManager em = emf.createEntityManager();

		Produit produit = em.find(Produit.class, id);
		if(produit != null) {
			obj.put("id", produit.getIdProduit());
			obj.put("nom", produit.getNom());
			obj.put("prix", produit.getPrix());
			obj.put("categorie", produit.getCategorie());
			obj.put("stock", produit.getStock());
			obj.put("vendeur", produit.getVendeur());
		}
		em.close();
		emf.close();
		return obj.toJSONString();/* convertit la liste en JSON */
	}
}
