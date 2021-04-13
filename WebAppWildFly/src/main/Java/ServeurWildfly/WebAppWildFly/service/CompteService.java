package ServeurWildfly.WebAppWildFly.service;




import java.util.ArrayList;
import java.util.List;

import ServeurWildfly.WebAppWildFly.CompteBancaire;

public class CompteService {
	List<CompteBancaire>  list = new ArrayList<CompteBancaire>();
	public List<CompteBancaire> getCompte(){
		CompteBancaire c1= new CompteBancaire("RANDRIANANDRASASANA","Harivelo",200.0);
		CompteBancaire c2= new CompteBancaire("LELEUX","Célia",300.0);
		
		list.add(c1);
		list.add(c2);
		return list;
	}
	public String getSold(CompteBancaire c1)
	{
		for(CompteBancaire c :list) {
			if(c.equals(c1.getIdCompte()))
			   return c1.getNom()+c1.getIdCompte()+c1.getSold();
		}
		return "Compte n'existe";
	}
}
