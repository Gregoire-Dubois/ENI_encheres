package fr.eni.enienchere.bll;

import fr.eni.enienchere.bo.Utilisateur;
import fr.eni.enienchere.dal.DAOFactory;
import fr.eni.enienchere.dal.UtilisateurDAO;

import java.util.List;

import fr.eni.enienchere.BusinessException;

public class UtilisateurManager {
	
	private static UtilisateurManager instance = null;
	private UtilisateurDAO utilisateurDAO ;

	//Constructeur privé
	public UtilisateurManager() {
		this.utilisateurDAO = DAOFactory.getUtilisateurDAO();
	}
	
	//SINGLETON
	public static UtilisateurManager getInstance() {
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
	

	public List<Utilisateur> findAll(String field,String sens) {
		try {
			return utilisateurDAO.selectAll();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return null;

	}
	
	

	
	//Permet de se connecter en vérifiant si la combinaison email/mdp OU pseudo/mdp correspond à la combinaison identifiant/mdp
	public Utilisateur login(String identifiant, String mdp) throws BusinessException {
		BusinessException businessException = new BusinessException();
		Utilisateur utilisateur = null;
		
		if(this.selectionnerEmailMdp(identifiant, mdp)!=null || this.selectionnerPseudoMdp(identifiant, mdp)!=null) { //ça veut dire qu'il y a une correspondance
			if(this.selectionnerEmailMdp(identifiant, mdp)!=null) { 
				utilisateur=this.selectionnerEmailMdp(identifiant, mdp);
			}else {
				if(this.selectionnerPseudoMdp(identifiant, mdp)!=null) {
					utilisateur = this.selectionnerPseudoMdp(identifiant, mdp);
				}
			}
		}else { //Pas de correspondance. Exception ici car si on le fait dans selectionnerEmailMdp ou selectionnerPseudoMdp il y aura forcement une exception.
			businessException.ajouterErreur(CodesResultatBLL.PAS_DE_CORRESPONDANCE_ERREUR);
//			List<Integer> listeErreur = businessException.getListeCodesErreur();
//			for(int erreur : listeErreur) {
//				System.out.println(erreur);
//			}
//			utilisateur=null;
			throw businessException;//Test
		}
		
		
		
		return utilisateur;
	}
}
