package fr.eni.enienchere.bll;

import fr.eni.enienchere.BusinessException;
import fr.eni.enienchere.bo.ArticleVendu;
import fr.eni.enienchere.bo.Retrait;
import fr.eni.enienchere.bo.Utilisateur;
import fr.eni.enienchere.dal.DAOFactory;
import fr.eni.enienchere.dal.RetraitDAO;

public class RetraitManager {
private RetraitDAO retraitDAO;
	
	public RetraitManager() {
		this.retraitDAO = DAOFactory.getRetraitDAO();
	}
	
	//pas d'interet d'avoir un retour? on peut mettre void
	public Retrait ajouter(ArticleVendu article, Utilisateur userConnected) throws BusinessException {
		BusinessException businessException = new BusinessException();
		Retrait retrait;
		if(!article.getRetrait().getRue().equals(userConnected.getRue())) {
		this.checkRue(article.getRetrait().getRue(),businessException);
		}
		if(!article.getRetrait().getCodePostal().equals(userConnected.getCodePostal())){
		this.checkCodePostal(article.getRetrait().getCodePostal(),businessException);
		}
		if(!article.getRetrait().getVille().equals(userConnected.getVille())) {
			this.checkVille(article.getRetrait().getVille(),businessException);
		}
		// Si il n'y a pas d'erreurs, on fait l'insertion, l'inscription dans la base de
				// données
		if (!businessException.hasErreur()) {
			retrait = this.retraitDAO.insert(article);
		} else {
			//retrait = null;
			throw businessException;
		}

		return retrait;
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
}
