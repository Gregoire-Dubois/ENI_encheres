package fr.eni.enienchere.dal;

import fr.eni.enienchere.BusinessException;
import fr.eni.enienchere.bo.Utilisateur;

public interface UtilisateurDAO {
	
	public Utilisateur select_by_id(int id) throws BusinessException;
	public Utilisateur select_by_email_mdp(String email, String mdp) throws BusinessException;
	

}

