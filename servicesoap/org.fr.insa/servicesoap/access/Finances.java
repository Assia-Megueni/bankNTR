package servicesoap.access;

import servicesoap.calc.CommandesF;
import servicesoap.models.Commande;
import servicesoap.models.Items;
import servicesoap.models.Produit;
import servicesoap.models.personne.Vendeur;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.logging.Logger;

/**
 * Contient l'ensemble des requêtes SOAP pour la gestion des comptes d'utilisateur
 * @author bverhul
 *
 */
public class Finances {
	public boolean rembourserCommande(int idCommande) {
		// TODO
		/* calcul du prix de la commande */
		double prix = CommandesF.prixTotalCommande(idCommande);
		if(prix <= 0)return false;
		/* récupérer la commande */
		Commande c = CommandesF.getCommande(idCommande);
		if(c==null)return false;
		if(c.getClient()==null)return false;
		if(!c.isEstPaye())return false;
		if(c.isEstRembourse())return false;
		/* crédit du compte client */
		boolean commande = CommandesF.crediter(c.getClient().getNom(),c.getClient().getPrenom(),prix);
		Logger.getGlobal().warning("Crédit client = " + commande);
		/* crédit des comptes vendeur */
		EntityManagerFactory emf2 = Persistence.createEntityManagerFactory("commande");
		EntityManager em2 = emf2.createEntityManager();
		List<Items> liste = em2.createQuery("SELECT i FROM ItemB i where i.commande.idCommande = :id").setParameter("id",idCommande)
				.getResultList();
		liste.forEach(items -> {
			/* retirer le stock */
			Produit p = items.getArticle();
			if((p != null)){
				Vendeur v = p.getVendeur();
				if(v != null) {
					/* le vendeur existe */
					if (p.removeStock(items.getNb())) {
						/* payer le compte vendeur */
						double nb = items.getNb() * p.getPrix();
						boolean crediter = CommandesF.debiter(v.getNom(),v.getPrenom(),nb);
						if(!crediter)Logger.getGlobal().warning("Compte "+v+" non débité de "+nb);
					} else Logger.getGlobal().warning("Ne peut retirer du stock du produit : "+p);
				}else Logger.getGlobal().warning("Ce produit n'a pas de vendeur : "+p);
			}
		});
		em2.close();
		emf2.close();
		/* modification de l'état de la commande */
		CommandesF.setCommandeRembouse(idCommande);
		return true;
	}
	
	public boolean payerCommande(int idCommande) {
		// TODO
		/* calcul du prix de la commande */
		double prix = CommandesF.prixTotalCommande(idCommande);
		if(prix <= 0)return false;
		/* récupérer la commande */
		Commande c = CommandesF.getCommande(idCommande);
		if(c==null)return false;
		if(c.getClient()==null)return false;
		if(c.isEstPaye())return false;
		/* débit du compte client */
		boolean commande = CommandesF.debiter(c.getClient().getNom(),c.getClient().getPrenom(),prix);
		Logger.getGlobal().warning("Débit client = " + commande);
		/* crédit des comptes vendeur */
		EntityManagerFactory emf2 = Persistence.createEntityManagerFactory("commande");
		EntityManager em2 = emf2.createEntityManager();
		List<Items> liste = em2.createQuery("SELECT i FROM ItemB i where i.commande.idCommande = :id").setParameter("id",idCommande)
				.getResultList();
		liste.forEach(items -> {
			/* retirer le stock */
			Produit p = items.getArticle();
			if((p != null)){
				Vendeur v = p.getVendeur();
				if(v != null) {
					/* le vendeur existe */
					if (p.removeStock(items.getNb())) {
						/* payer le compte vendeur */
						double nb = items.getNb() * p.getPrix();
						boolean crediter = CommandesF.crediter(v.getNom(),v.getPrenom(),nb);
						if(!crediter)Logger.getGlobal().warning("Compte "+v+" non crédité de "+nb);
					} else Logger.getGlobal().warning("Ne peut retirer du stock du produit : "+p);
				}else Logger.getGlobal().warning("Ce produit n'a pas de vendeur : "+p);
			}
		});
		em2.close();
		emf2.close();
		/* modification de l'état de la commande */
		CommandesF.setCommandePayee(idCommande);
		return true;
	}
}
