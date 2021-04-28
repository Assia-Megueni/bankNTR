package servicesoap.calc;

import org.json.simple.JSONObject;
import servicesoap.models.Commande;
import servicesoap.models.Items;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

/**
 * Contient l'ensemble des fonctions utiles pour les commandes
 */
public class CommandesF {
    public static final String URL = "http://localhost:8080/RestApp/resources/xml";
    public static double prixTotalCommande(int idCommande){
        double total = 0d;
        try {
        EntityManagerFactory emf2 = Persistence.createEntityManagerFactory("items");
        EntityManager em2 = emf2.createEntityManager();
        List<Items> liste = em2.createQuery("SELECT i FROM ItemB i where i.commande.idCommande = :id").setParameter("id",idCommande)
                .getResultList();
        if(!liste.isEmpty()) {
            total = liste.stream().mapToDouble(items -> items.getNb() * items.getArticle().getPrix()).sum();
        }
        em2.close();
        emf2.close();
        }catch(Exception e) {}
        return total;
    }
    /**
     * Permet d'ajouter une quantité d'argent sur un compte en appellant le serveur 1
     * @param nom
     * @param prenom
     * @param v
     * @return true si l'opération s'est bien passée
     */
    public static boolean crediter(String nom, String prenom, double v){
        // TODO
        boolean exitS = false;
        Logger.getGlobal().info("crediter à "+nom+" "+prenom+" : "+v);
        HttpURLConnection con;
        try {
            URL url = new URL(URL + "/compte/crediter/"+nom+"/"+prenom+"/"+v);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
            con.setDoOutput(true);
            Logger.getGlobal().info("URL = "+url.toString());
            /* timeout prog */
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            /* lecture de toute la requête */
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            Logger.getGlobal().info("Buffer = "+content.toString());
            exitS = content.toString().contains("xml");/* permet de différentier une erreur d'un succès */
        }catch (Exception e){
            Logger.getGlobal().warning(e.toString());
            return false;
        }
        return exitS;
    }

    /**
     * Permet de retirer une quantité d'argent sur un compte en appellant le serveur 1
     * @param nom
     * @param prenom
     * @param v
     * @return true si l'opération s'est bien passée
     */
    public static boolean debiter(String nom, String prenom, double v){
        boolean exitS = false;
        Logger.getGlobal().info("débiter à "+nom+" "+prenom+" : "+v);
        HttpURLConnection con;
        try {
            URL url = new URL(URL + "/compte/debiter/"+nom+"/"+prenom+"/"+v);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
            con.setDoOutput(true);
            Logger.getGlobal().info("URL = "+url.toString());
            /* timeout prog */
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            /* lecture de toute la requête */
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            Logger.getGlobal().info("Buffer = "+content.toString());
            exitS = content.toString().contains("xml");/* permet de différentier une erreur d'un succès */
        }catch (Exception e){
            Logger.getGlobal().warning(e.toString());
            return false;
        }
        return exitS;
    }

    public static Commande getCommande(int idCommande){
        Commande commande = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("commande");
        EntityManager em = emf.createEntityManager();
        try {
            commande = (Commande) em.createQuery("SELECT c FROM Commande c where c.idCommande = :id")
                    .setParameter("id", idCommande).getSingleResult();
        }catch (Exception e){Logger.getGlobal().warning(e.toString());}
        em.close();
        emf.close();
        return commande;
    }

    public static boolean setCommandePayee(int idCommande){
        Commande commande = null;
        boolean state = false;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("commande");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            commande = (Commande) em.createQuery("SELECT c FROM Commande c where c.idCommande = :id")
                    .setParameter("id", idCommande).getSingleResult();
            commande.setEstPaye(true);
            state = true;
        }catch (Exception e){Logger.getGlobal().warning(e.toString());}
        em.getTransaction().commit();
        em.close();
        emf.close();
        return state;
    }
    public static boolean setCommandeRembouse(int idCommande){
        Commande commande = null;
        boolean state = false;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("commande");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            commande = (Commande) em.createQuery("SELECT c FROM Commande c where c.idCommande = :id")
                    .setParameter("id", idCommande).getSingleResult();
            commande.setEstRembourse(true);
            state = true;
        }catch (Exception e){Logger.getGlobal().warning(e.toString());}
        em.getTransaction().commit();
        em.close();
        emf.close();
        return state;
    }

}
