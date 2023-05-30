package fr.eni.enienchere.dal;

import fr.eni.enienchere.BusinessException;
import fr.eni.enienchere.bo.Utilisateur;

public interface UtilisateurDAO {
	
	public Utilisateur selectById(int id) throws BusinessException;
	public Utilisateur selectByEmailMdp(String email, String mdp) throws BusinessException;
	public Utilisateur selectByPseudoMdp(String pseudo, String mdp) throws BusinessException;
	public Utilisateur insert(Utilisateur utilisateur) throws BusinessException;

}

