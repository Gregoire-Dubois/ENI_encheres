package fr.eni.enienchere.bll;

import fr.eni.enienchere.bo.Utilisateur;
import fr.eni.enienchere.dal.DAOFactory;
import fr.eni.enienchere.dal.UtilisateurDAO;
import fr.eni.enienchere.BusinessException;

public class UtilisateurManager {
	
	private static UtilisateurManager instance = null;
	private UtilisateurDAO utilisateurDAO ;

	//Constructeur privé
	public UtilisateurManager() {
		this.utilisateurDAO = DAOFactory.getUtilisateurDAO();
	}
	
	//SINGLETON
	public static UtilisateurManager getInstante() {
			if (instance == null) {
				instance = new UtilisateurManager();
			}
	 return instance;
	}

	public Utilisateur selectionner(int id) throws BusinessException {

		Utilisateur utilisateur = this.utilisateurDAO.selectById(id);

		return utilisateur;
	}

	public Utilisateur selectionnerEmailMdp(String email, String mdp) throws BusinessException {

		Utilisateur utilisateur = utilisateurDAO.selectByEmailMdp(email, mdp);

		return utilisateur;
	}
	
	public Utilisateur selectionnerPseudoMdp(String pseudo, String mdp) throws BusinessException {

		Utilisateur utilisateur = this.utilisateurDAO.selectByPseudoMdp(pseudo, mdp);

		return utilisateur;
	}
	
	public Utilisateur inscrire(String pseudo, String nom, String prenom, String email, String telephone, String rue,String codePostal,String ville,String mdp) throws BusinessException {
		BusinessException businessException = new BusinessException();
		Utilisateur utilisateur=null;
		//Tests de non nullité //séparer dans différentes méthodes? validerPseudo(pseudo, businessException) ...
		if(pseudo.trim() == null || !pseudo.trim().matches("\\p{Alpha}")) {
			businessException.ajouterErreur(CodesResultatBLL.PSEUDO_NULL_ERREUR);
		}
		
		if(nom.trim()==null) {
			businessException.ajouterErreur(CodesResultatBLL.NOM_NULL_ERREUR);
		}
		
		if(prenom.trim()==null) {
			businessException.ajouterErreur(CodesResultatBLL.PRENOM_NULL_ERREUR);
		}
		
		if(email.trim()==null) {
			businessException.ajouterErreur(CodesResultatBLL.EMAIL_NULL_ERREUR);
		}
		
		if(rue.trim()==null) {
			businessException.ajouterErreur(CodesResultatBLL.RUE_NULL_ERREUR);
		}
		
		if(codePostal.trim()==null) {
			businessException.ajouterErreur(CodesResultatBLL.CODEPOSTAL_NULL_ERREUR);
		}
		
		if(ville.trim()==null) {
			businessException.ajouterErreur(CodesResultatBLL.VILLE_NULL_ERREUR);
		}
		
		if(mdp.trim()==null) {
			businessException.ajouterErreur(CodesResultatBLL.MDP_NULL_ERREUR);
		}
		
		//Si il n'y a pas d'erreurs, on fait l'insertion, l'inscription dans la base de données
		if(!businessException.hasErreur()) {
			utilisateur = new Utilisateur(0, pseudo, nom, prenom, email, telephone, rue, codePostal, ville, mdp, null, null);
			utilisateur = this.utilisateurDAO.insert(utilisateur);
		}else {
			throw businessException;
		}

		return utilisateur;
	}
	
	//Permet de se connecter en vérifiant si la combinaison email/mdp OU pseudo/mdp correspond à la combinaison identifiant/mdp
		public Utilisateur login(String identifiant, String mdp) throws BusinessException {
			Utilisateur utilisateur = null;
			
			if(this.selectionnerEmailMdp(identifiant, mdp)!=null) {
				utilisateur=this.selectionnerEmailMdp(identifiant, mdp);
			}else {
				if(this.selectionnerPseudoMdp(identifiant, mdp)!=null) {
					utilisateur = this.selectionnerPseudoMdp(identifiant, mdp);
				}
			}
			
			
			return utilisateur;
		}
}
