package servicesoap.access;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import servicesoap.models.Commande;
import servicesoap.models.Items;
import servicesoap.models.Produit;
import servicesoap.models.personne.Client;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

public class Commandes {
	/**
	 * Retourne la liste complète des commandes
	 * @return : liste en JSON
	 */
	public String listeCommandes() {
		JSONArray list = new JSONArray();
		/* recherche des infos concernant la commande */
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("commande");
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("SELECT c FROM Commande c");
		Collection<Commande> ce = (Collection<Commande>) query.getResultList();
		em.close();
		emf.close();
		
		ce.forEach(commande -> {
			JSONObject obj = new JSONObject();
			obj.put("id", commande.getIdCommande());
			obj.put("client", commande.getClient());
			obj.put("estPaye",commande.isEstPaye());
			obj.put("estRembourse",commande.isEstRembourse());
			JSONArray elements = new JSONArray();
			/* va chercher tous les articles de la commande */
			EntityManagerFactory emf2 = Persistence.createEntityManagerFactory("commande");
			EntityManager em2 = emf2.createEntityManager();
			List<Items> liste = em2.createQuery("SELECT i FROM ItemB i where i.commande.idCommande = :id").setParameter("id",commande.getIdCommande())
					.getResultList();
			liste.forEach(e->{
				JSONObject el = new JSONObject();
				el.put("id", e.getId());
				el.put("nb", e.getNb());
				if(e.getArticle()!= null)el.put("article", e.getArticle().toString());
				else el.put("article", null);
				elements.add(el);
			});
			em2.close();
			emf2.close();
			obj.put("items", elements);
			list.add(obj);
		});
		return list.toJSONString();/* convertit la liste en JSON */
	}
	/**
	 * Retourne la liste complète des commandes d'un client à partir de son nom et prénom
	 * @param nom
	 * @param prenom
	 * @return : liste en JSON
	 */
	public String commandesClient(String nom, String prenom) {
		/* vérification des paramètres */
		if(nom.equals("")||prenom.equals(""))return "";
		JSONArray list = new JSONArray();
		/* recherche des infos concernant la commande */
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("commande");
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("SELECT c FROM Commande c where c.client.nom = :nom and c.client.prenom = :prenom")
				.setParameter("nom",nom.trim()).setParameter("prenom",prenom.trim());
		Collection<Commande> ce = (Collection<Commande>) query.getResultList();
		em.close();
		emf.close();
		ce.forEach(commande -> {
			JSONObject obj = new JSONObject();
			obj.put("id", commande.getIdCommande());
			obj.put("client", commande.getClient());
			obj.put("estPaye",commande.isEstPaye());
			obj.put("estRembourse",commande.isEstRembourse());
			JSONArray elements = new JSONArray();
			/* va chercher tous les articles de la commande */
			EntityManagerFactory emf2 = Persistence.createEntityManagerFactory("commande");
			EntityManager em2 = emf2.createEntityManager();
			List<Items> liste = em2.createQuery("SELECT i FROM ItemB i where i.commande.idCommande = :id").setParameter("id",commande.getIdCommande())
					.getResultList();
			liste.forEach(e->{
				JSONObject el = new JSONObject();
				el.put("id", e.getId());
				el.put("nb", e.getNb());
				if(e.getArticle()!= null)el.put("article", e.getArticle().toString());
				else el.put("article", null);
				elements.add(el);
			});
			em2.close();
			emf2.close();
			obj.put("items", elements);
			list.add(obj);
		});
		return list.toJSONString();/* convertit la liste en JSON */
	}
	/**
	 * Permet d'ajouter une quantité d'un article sur une commande
	 * @param idcommande : identifiant de la commande
	 * @param idarticle : identifiant de l'article
	 * @param quantité : nombre à retirer(strictement positif)
	 * @return true si l'opération s'est bien passée
	 */
	public boolean ajoutArticle(int idcommande, int idarticle, int quantité) {
		boolean result = false;
		Items item = null;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("commande");
		EntityManager em = emf.createEntityManager();

		Commande c = em.find(Commande.class, idcommande);
		if(c != null){
			em.getTransaction().begin();
			/* recherche de l'item si il existe */
			EntityManagerFactory emf5 = Persistence.createEntityManagerFactory("items");
			EntityManager em5 = emf5.createEntityManager();
			em5.getTransaction().begin();
			try {
				item = (Items) em5.createQuery("select v from ItemB v where v.article.idProduit = :idp and v.commande.idCommande = :idc")
						.setParameter("idp", idarticle).setParameter("idc", idcommande).getSingleResult();
			}catch (Exception e){}
			em5.getTransaction().commit();
			em5.close();emf5.close();
			if(item != null){
				/* l'item existe */
				Logger.getGlobal().info("Item is inside");
				int nb = item.getNb();
				item.setNb(nb + quantité);
				/* insertion de l'item en base */
				emf5 = Persistence.createEntityManagerFactory("items");
				em5 = emf5.createEntityManager();
				em5.getTransaction().begin();
				em5.merge(item);
				em5.createQuery("select v from ItemB v").getResultList().forEach(System.out::println);
				em5.getTransaction().commit();
				em5.close();emf5.close();
				result = true;
			}else{
				/* récupère l'article en base */
				EntityManagerFactory emf2 = Persistence.createEntityManagerFactory("article");
				EntityManager em2 = emf2.createEntityManager();
				em2.getTransaction().begin();
				Produit produit = em2.find(Produit.class, idarticle);
				if(produit != null) {
					/* créer un item */
					item = new Items(produit,quantité,c);
					Logger.getGlobal().info("Item to send : "+item);
					/* insertion de l'item en base */
					emf5 = Persistence.createEntityManagerFactory("items");
					em5 = emf5.createEntityManager();
					em5.getTransaction().begin();
					em5.persist(item);
					em5.getTransaction().commit();
					em5.close();emf5.close();

					result = true;
				}else Logger.getGlobal().warning("Items est null");

				em2.getTransaction().commit();

				em2.createQuery("select v from ItemB v").getResultList().forEach(System.out::println);
				em2.close();
				emf2.close();
			}
			em.getTransaction().commit();
		}else Logger.getGlobal().warning("Commande est null");
		em.close();
		emf.close();
		return result;
	}

	/**
	 * Permet de retirer une quantité d'un article sur une commande
	 * @param idcommande : identifiant de la commande
	 * @param idarticle : identifiant de l'article
	 * @param quantité : nombre à retirer(strictement positif)
	 * @return true si l'opération s'est bien passée
	 */
	public boolean retraitArticle(int idcommande, int idarticle, int quantité) {
		boolean result = false;
		Items item = null;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("commande");
		EntityManager em = emf.createEntityManager();

		Commande c = em.find(Commande.class, idcommande);
		if(c != null){
			em.getTransaction().begin();
			/* recherche de l'item si il existe */
			EntityManagerFactory emf5 = Persistence.createEntityManagerFactory("items");
			EntityManager em5 = emf5.createEntityManager();
			em5.getTransaction().begin();
			try {
				item = (Items) em5.createQuery("select v from ItemB v where v.article.idProduit = :idp and v.commande.idCommande = :idc")
						.setParameter("idp", idarticle).setParameter("idc", idcommande).getSingleResult();
			}catch (Exception e){}
			em5.getTransaction().commit();
			em5.close();emf5.close();
			if(item != null){
				/* l'item existe */
				Logger.getGlobal().info("Item is inside");
				int nb = item.getNb();
				int newNb = nb - quantité;
				if(newNb < 0)newNb = 0;
				item.setNb(newNb);
				/* insertion de l'item en base */
				emf5 = Persistence.createEntityManagerFactory("items");
				em5 = emf5.createEntityManager();
				em5.getTransaction().begin();
				em5.merge(item);
				em5.createQuery("select v from ItemB v").getResultList().forEach(System.out::println);
				em5.getTransaction().commit();
				em5.close();emf5.close();
				result = true;
			}
			em.getTransaction().commit();
		}else Logger.getGlobal().warning("Commande est null");
		em.close();
		emf.close();
		return result;
	}

	/**
	 * Permet de créer une commande vide avec un client
	 * @param idClient
	 * @return true si l'opération s'est bien passée
	 */
	public boolean creerCommande(int idClient) {
		/* recherche du client */
		Client p = null;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("user");
		EntityManager em = emf.createEntityManager();
		try {
			p = (Client) em.createQuery("SELECT c from Client c where c.idPersonne = :id")
					.setParameter("id", idClient)
					.getSingleResult();
		}catch(Exception e) {

		}
		em.close();
		emf.close();
		if(p==null)return false;

		/* envoi en base de données */
		emf = Persistence.createEntityManagerFactory("commande");
		em = emf.createEntityManager();

		em.getTransaction().begin();
		Commande c = new Commande();
		c.setClient(p);
		em.persist(c);
		em.getTransaction().commit();

		em.close();
		emf.close();
		return true;
	}

}
