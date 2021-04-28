package servicesoap.access;

import javax.persistence.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import servicesoap.models.personne.*;

import java.util.Collection;
/**
 * Contient l'ensemble des requêtes SOAP pour la gestion des comptes d'utilisateurs
 * @author bverhul
 *
 */
public class Comptes {
	/**
	 * Permet de vérifier si le mot de passe d'un compte donné est bon
	 * @param nom
	 * @param prenom
	 * @param motdepasse
	 * @param isClient : si c'est un compte client ou un compte vendeur
	 * @return
	 */
	public boolean isCorrect(String nom, String prenom, String motdepasse, boolean isClient) {
		// TODO
		Personne p = null;
		StringBuilder sb = new StringBuilder("SELECT p FROM ");
		sb.append(isClient?"Client p ":"Vendeur p ");
		sb.append("WHERE p.nom = :nom AND p.prenom = :prenom");

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("user");
		EntityManager em = emf.createEntityManager();
		try {
		p = (Personne) em.createQuery(sb.toString())
				.setParameter("nom", nom.trim()).setParameter("prenom", prenom.trim())
				.getSingleResult();
		}catch(Exception e) {
			
		}
		
		em.close();
		emf.close();
		if(p==null)return false;
		return p.getMotdepasse().equals(motdepasse.trim());
	}

	/**
	 * Permet de créer un compte
	 * @param nom
	 * @param prenom
	 * @param motdepasse
	 * @param isClient : si il faut créer un compte client ou un compte vendeur
	 * @return si le compte a été crée
	 */
	public boolean creerCompte(String nom, String prenom, String motdepasse, boolean isClient) {
		/* vérification des paramètres */
		if((nom.equals(""))||(prenom.equals(""))||(motdepasse.equals("")))return false;
		/* normalisation des données */
		String nomF = nom.trim();
		String prenomF = prenom.trim();
		String motdepasseF = motdepasse.trim();
		/* envoi en base de données */
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("user");
	    EntityManager em = emf.createEntityManager();

	    em.getTransaction().begin();
	    em.persist(isClient?new Client(nomF,prenomF,motdepasseF):new Vendeur(nomF,prenomF,motdepasseF));
	    em.getTransaction().commit();

	    em.close();
	    emf.close();
		return true;
	}
	/**
	 * Permet d'obtenir la liste des comptes client sans les mots de passe
	 * @return liste en JSON
	 */
	public String listeComptesClient() {
		JSONArray list = new JSONArray();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("user");
		EntityManager em = emf.createEntityManager();

		Query query = em.createQuery("SELECT c FROM Client c");
		Collection<Client> ce = (Collection<Client>) query.getResultList();
		
		ce.forEach(personne -> {
			JSONObject obj = new JSONObject();
			obj.put("id", personne.getIdPersonne());
			obj.put("nom", personne.getNom());
			obj.put("prenom", personne.getPrenom());
			list.add(obj);
		});
		em.close();
		emf.close();
		return list.toJSONString();/* convertit la liste en JSON */
	}
	/**
	 * Permet d'obtenir la liste des comptes vendeurs sans les mots de passe
	 * @return liste en JSON
	 */
	public String listeComptesVendeur() {
		JSONArray list = new JSONArray();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("user");
		EntityManager em = emf.createEntityManager();

		Query query = em.createQuery("SELECT v FROM Vendeur v");
		Collection<Vendeur> ce = (Collection<Vendeur>) query.getResultList();
		
		ce.forEach(personne -> {
			JSONObject obj = new JSONObject();
			obj.put("id", personne.getIdPersonne());
			obj.put("nom", personne.getNom());
			obj.put("prenom", personne.getPrenom());
			list.add(obj);
		});
		em.close();
		emf.close();
		return list.toJSONString();//* convertit la liste en JSON */
	}
}
