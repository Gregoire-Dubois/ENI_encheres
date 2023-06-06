package fr.eni.enienchere.bll;

import fr.eni.enienchere.bo.Utilisateur;
import fr.eni.enienchere.dal.DAOFactory;
import fr.eni.enienchere.dal.UtilisateurDAO;

import java.util.List;

import org.eclipse.jdt.internal.compiler.ast.ThisReference;

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
	
	public Utilisateur selectionnerPseudo(String pseudo) throws BusinessException {
		Utilisateur utilisateur = this.utilisateurDAO.selectByPseudo(pseudo);
		return utilisateur;
	}
	//Utiliser dans la servlet de modification du profil
	public Utilisateur selectionnerParIdMdp(int id, String mdp) throws BusinessException {
		BusinessException businessException = new BusinessException();	
		Utilisateur utilisateur = null;
		System.out.println(this.utilisateurDAO.selectByIdMdp(id, mdp));
		if(this.utilisateurDAO.selectByIdMdp(id, mdp)!=null) {
		utilisateur = this.utilisateurDAO.selectByIdMdp(id, mdp);
		}else {
			System.out.println("Je suis là");
			businessException.ajouterErreur(CodesResultatBLL.MDP_ERREUR);
			for(int erreur : businessException.getListeCodesErreur()) {
				System.out.println(erreur);
			}
			throw businessException;
		}
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
	
	public void modifierUtilisateur(Utilisateur utilisateur, Utilisateur userConnected) throws BusinessException {
		BusinessException businessException = new BusinessException();
		//On teste seulement si le pseudo a été modifié
		if(!utilisateur.getPseudo().equals(userConnected.getPseudo())) {
		this.checkPseudo(utilisateur.getPseudo(),businessException);
		}
		this.checkNom(utilisateur.getNom(), businessException);
		this.checkPrenom(utilisateur.getPrenom(), businessException);
		System.out.println(utilisateur.getEmail());
		
		//Ajout: on teste seulement si l'email a été modifié
		if(!utilisateur.getEmail().equals(userConnected.getEmail())){
		this.checkEmail(utilisateur.getEmail(), businessException);
		}
		if (!utilisateur.getTelephone().trim().isEmpty()) {
			this.checkTelephone(utilisateur.getTelephone(), businessException);
		}else {
			utilisateur.setTelephone(null);
		}
		this.checkRue(utilisateur.getRue(), businessException);
		this.checkCodePostal(utilisateur.getCodePostal(), businessException);
		this.checkVille(utilisateur.getVille(), businessException);
		this.checkMdp(utilisateur.getMotDePasse(), businessException);

		// Pour les tests
		List<Integer> listeErreurs = businessException.getListeCodesErreur();
		if (listeErreurs != null) {
			for (int erreurs : listeErreurs) {
				System.out.println(erreurs);
			}
		} else {
			System.out.println("Pas d'erreurs");
		}

		// Si il n'y a pas d'erreurs, on fait la modification
		if (!businessException.hasErreur()) {
			this.utilisateurDAO.updateUtilisateur(utilisateur);
		} else {
			utilisateur = null;
			throw businessException;
		}

	}

	public void deleteById(int id) {
		try {
			this.utilisateurDAO.deleteById(id);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

	public Utilisateur insert(Utilisateur utilisateur) throws BusinessException {
		BusinessException businessException = new BusinessException();
		this.checkPseudo(utilisateur.getPseudo(), businessException);
		this.checkNom(utilisateur.getNom(), businessException);
		this.checkPrenom(utilisateur.getPrenom(), businessException);
		System.out.println(utilisateur.getEmail());
		this.checkEmail(utilisateur.getEmail(), businessException);
		if (!utilisateur.getTelephone().trim().isEmpty()) {
			this.checkTelephone(utilisateur.getTelephone(), businessException);
		}else {
			utilisateur.setTelephone(null);
		}
		this.checkRue(utilisateur.getRue(), businessException);
		this.checkCodePostal(utilisateur.getCodePostal(), businessException);
		this.checkVille(utilisateur.getVille(), businessException);
		this.checkMdp(utilisateur.getMotDePasse(), businessException);

		// Pour les tests
		List<Integer> listeErreurs = businessException.getListeCodesErreur();
		if (listeErreurs != null) {
			for (int erreurs : listeErreurs) {
				System.out.println(erreurs);
			}
		} else {
			System.out.println("Pas d'erreurs");
		}

		// Si il n'y a pas d'erreurs, on fait l'insertion, l'inscription dans la base de
		// données
		if (!businessException.hasErreur()) {
			utilisateur = this.utilisateurDAO.insert(utilisateur);
		} else {
			utilisateur = null;
			throw businessException;
		}

		return utilisateur;

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
	
	public void checkPseudo(String pseudo, BusinessException businessException) throws BusinessException {

		if (pseudo.trim().isEmpty()) {
			System.out.println("Erreur sur le pseudo vide");
			businessException.ajouterErreur(CodesResultatBLL.PSEUDO_VIDE_ERREUR);
		}
		if (!pseudo.trim().matches("^[a-zA-ZÀ-ÿ0-9]+$")) {
			System.out.println("Erreur sur le pseudo qui n'est pas alphanumérique");
			businessException.ajouterErreur(CodesResultatBLL.PSEUDO_ALPHA_NUM_ERREUR);
		}
		if (this.utilisateurDAO.selectByPseudo(pseudo) != null) {
			businessException.ajouterErreur(CodesResultatBLL.PSEUDO_DOUBLON_ERREUR);
		}
	}
//	public void checkPseudoModif(String pseudo, Utilisateur userConnected, BusinessException businessException) throws BusinessException {
//
//		if (pseudo.trim().isEmpty()) {
//			System.out.println("Erreur sur le pseudo vide");
//			businessException.ajouterErreur(CodesResultatBLL.PSEUDO_VIDE_ERREUR);
//		}
//		if (!pseudo.trim().matches("^[a-zA-ZÀ-ÿ0-9]+$")) {
//			System.out.println("Erreur sur le pseudo qui n'est pas alphanumérique");
//			businessException.ajouterErreur(CodesResultatBLL.PSEUDO_ALPHA_NUM_ERREUR);
//		}
//		//Si la sélection par pseudo != null, on vérifie si elle est égal à userConnected 
//		if (this.utilisateurDAO.selectByPseudo(pseudo) != null && this.utilisateurDAO.selectByPseudo(pseudo) != userConnected) {
//			businessException.ajouterErreur(CodesResultatBLL.PSEUDO_DOUBLON_ERREUR);
//		}
//	}

	public void checkNom(String nom, BusinessException businessException) {

		if (nom.trim().isEmpty()) {
			System.out.println("Erreur sur le nom vide");
			businessException.ajouterErreur(CodesResultatBLL.NOM_VIDE_ERREUR);
		}
		if (!nom.trim().matches("^[a-zA-ZÀ-ÿ\\-]+$")) {
			System.out.println("Erreur sur le nom qui n'est pas alphanumérique");
			businessException.ajouterErreur(CodesResultatBLL.NOM_ALPHA_ERREUR);
		}
	}

	public void checkPrenom(String prenom, BusinessException businessException) {

		if (prenom.trim().isEmpty()) {
			System.out.println("Erreur sur le prenom vide");
			businessException.ajouterErreur(CodesResultatBLL.PRENOM_VIDE_ERREUR);
		}
		if (!prenom.trim().matches("^[a-zA-ZÀ-ÿ\\s\\-]+$")) {
			System.out.println("Erreur sur le prenom qui n'est pas alphanumérique");
			businessException.ajouterErreur(CodesResultatBLL.PRENOM_ALPHA_ERREUR);
		}
	}

	public void checkRue(String rue, BusinessException businessException) {

		if (rue.trim().isEmpty()) {
			System.out.println("Erreur sur la rue vide");
			businessException.ajouterErreur(CodesResultatBLL.RUE_VIDE_ERREUR);
		}
		if (!rue.trim().matches("^[A-Za-zÀ-ÿ0-9\\s'-]+$")) {
			System.out.println("Erreur sur la rue qui n'est pas alphanumérique + espace");
			businessException.ajouterErreur(CodesResultatBLL.RUE_ALPHA_NUM_ESP_ERREUR);
		}
	}

	public void checkCodePostal(String codePostal, BusinessException businessException) {

		if (codePostal.trim().isEmpty()) {
			System.out.println("Erreur sur le code postal vide");
			businessException.ajouterErreur(CodesResultatBLL.CODEPOSTAL_VIDE_ERREUR);
		}
		if (!codePostal.trim().matches("\\d+")) {
			System.out.println("Erreur sur le code postal qui n'est pas numérique");
			businessException.ajouterErreur(CodesResultatBLL.CODEPOSTAL_NUM_ERREUR);
		}
	}

	public void checkVille(String ville, BusinessException businessException) {

		if (ville.trim().isEmpty()) {
			System.out.println("Erreur sur la ville vide");
			businessException.ajouterErreur(CodesResultatBLL.VILLE_VIDE_ERREUR);
		}
		if (!ville.trim().matches("^[a-zA-ZÀ-ÿ\\-]+$")) {
			System.out.println("Erreur sur la ville qui ne contient pas uniquement des caractères alpha et -");
			businessException.ajouterErreur(CodesResultatBLL.VILLE_ALPHA_ERREUR);
		}
	}

	public void checkMdp(String motDePasse, BusinessException businessException) {
		if (motDePasse.trim().isEmpty()) {
			System.out.println("Erreur sur le mot de passe vide");
			businessException.ajouterErreur(CodesResultatBLL.MDP_VIDE_ERREUR);
		}
	}

	public void checkEmail(String email, BusinessException businessException) throws BusinessException {
		System.out.println(this.utilisateurDAO.selectByEmail(email));
		if (this.utilisateurDAO.selectByEmail(email) != null) {
			businessException.ajouterErreur(CodesResultatBLL.EMAIL_DOUBLON_ERREUR);
		}
	}
//	//Si la sélection par email != null, on vérifie si elle est égal à userConnected 
//	public void checkEmailModif(String email, Utilisateur userConnected,BusinessException businessException) throws BusinessException {
//		System.out.println(this.utilisateurDAO.selectByEmail(email));
//		if (this.utilisateurDAO.selectByEmail(email) != null && this.utilisateurDAO.selectByEmail(email) != userConnected) {
//			businessException.ajouterErreur(CodesResultatBLL.EMAIL_DOUBLON_ERREUR);
//		}
//	}

	public void checkTelephone(String telephone, BusinessException businessException) {
		if (!telephone.trim().matches("^[0-9\\s\\/\\-]+$")) {
			System.out.println("Erreur sur le telephone qui ne contient pas uniquement des chiffres");
			businessException.ajouterErreur(CodesResultatBLL.TELEPHONE_NUM_ERREUR);
		}
	}

}
