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

	public Utilisateur selectionnerEmailMdp(String email, String mdp) throws BusinessException {

		Utilisateur utilisateur = this.utilisateurDAO.selectByEmailMdp(email, mdp);

		return utilisateur;
	}
	
	public Utilisateur selectionnerPseudoMdp(String pseudo, String mdp) throws BusinessException {

		Utilisateur utilisateur = this.utilisateurDAO.selectByPseudoMdp(pseudo, mdp);

		return utilisateur;
	}
	
	public Utilisateur inscrire(Utilisateur utilisateur) throws BusinessException {
		

		utilisateur = this.utilisateurDAO.insert(utilisateur);

		return utilisateur;
	}
}
