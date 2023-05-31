package fr.eni.enienchere.tanguy;

import fr.eni.enienchere.BusinessException;
import fr.eni.enienchere.bo.Utilisateur;
import fr.eni.enienchere.dal.UtilisateurDAOJdbcImpl;

public class classTestaSupprimer {

	public static void main(String[] args) {
		UtilisateurDAOJdbcImpl u = new UtilisateurDAOJdbcImpl();
		try {
			u.updateUtilisateur(new Utilisateur("TJ", "Josse", "Tanguy", "tanguy@josse.fr", null, "rue de Nantes", "44000", "Nantes", "tjosse", 500, false));
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
