package fr.eni.enienchere.bll;

import fr.eni.enienchere.bo.Utilisateur;
import fr.eni.enienchere.dal.DAOFactory;
import fr.eni.enienchere.dal.UtilisateurDAO;
import fr.eni.enienchere.BusinessException;

public class UtilisateurManager {
	private UtilisateurDAO utilisateurDAO;

	public UtilisateurManager() {
		this.utilisateurDAO = DAOFactory.getUtilisateurDAO();
	}

	public Utilisateur selectionner(int id) throws BusinessException {

		Utilisateur utilisateur = this.utilisateurDAO.selectById(id);

		return utilisateur;
	}

	public Utilisateur selectionner_email_mdp(String email, String mdp) throws BusinessException {

		Utilisateur utilisateur = this.utilisateurDAO.selectByEmailMdp(email, mdp);

		return utilisateur;
	}
}
