package fr.eni.enienchere.dal;

import fr.eni.enienchere.BusinessException;
import fr.eni.enienchere.bo.Utilisateur;

public interface UtilisateurDAO {
	
	public static Utilisateur selectById(int id) throws BusinessException {
		return null;}
	public Utilisateur selectByEmailMdp(String email, String mdp) throws BusinessException;
	public void updateUtilisateur(Utilisateur utilisateurConnecte) throws BusinessException;
	public void deleteById(int id) throws BusinessException;
	public void deleteByEmail(String email) throws BusinessException;
	public Utilisateur selectByPseudoMdp(String pseudo, String mdp) throws BusinessException;
	public Utilisateur insert(Utilisateur utilisateur) throws BusinessException;
	public Utilisateur selectByEmail(String email) throws BusinessException;
<<<<<<< HEAD
	public static Utilisateur selectByPseudo(String pseudo) throws BusinessException {
		return null;}
=======
	public Utilisateur selectByPseudo(String pseudo) throws BusinessException;

>>>>>>> branch 'master' of git@bitbucket.org:ccrepin/eni_encheres.git
	public Utilisateur selectByIdMdp(int id, String mdp) throws BusinessException;
	public void updateUtilisateurApresEnchere(Utilisateur utilisateur) throws  BusinessException;
	
}

