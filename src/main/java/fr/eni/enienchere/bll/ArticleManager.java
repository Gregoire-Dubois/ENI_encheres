package fr.eni.enienchere.bll;

import java.time.LocalDate;

import fr.eni.enienchere.BusinessException;
import fr.eni.enienchere.bo.ArticleVendu;
import fr.eni.enienchere.bo.Categorie;
import fr.eni.enienchere.bo.Retrait;
import fr.eni.enienchere.bo.Utilisateur;
import fr.eni.enienchere.dal.ArticleDAO;
import fr.eni.enienchere.dal.DAOFactory;

public class ArticleManager {
private ArticleDAO articleDAO;
	
	public ArticleManager() {
		this.articleDAO=DAOFactory.getArticleDAO();
	}
	
	//Avant d'ajouter, on doit vérifier les champs
	//nomArticle, description, dateDebutEncheres, dateFinEncheres,  prixInitial, categorie, retrait, vendeur
	public ArticleVendu ajouter(String nomArticle, String description, LocalDate dateDebutEncheres, LocalDate dateFinEncheres, int prixInitial, Categorie categorie, Retrait retrait, Utilisateur vendeur) throws BusinessException {
		BusinessException businessException = new BusinessException();
		ArticleVendu article;
		ArticleVendu articleVendu;
		//Méthodes check à faire avec gestion des erreurs
		this.checkNom(nomArticle);
		this.checkDescription(description);
		this.checkDateDebut(dateDebutEncheres);
		this.checkDateFin(dateFinEncheres);
		this.checkPrixInitial(prixInitial);
		this.checkCategorie(categorie);
		this.checkRetrait(retrait);
		this.checkVendeur(vendeur);
		
		
		// Si il n'y a pas d'erreurs, on fait l'insertion, l'inscription dans la base de
				// données
		if (!businessException.hasErreur()) {
			article = new ArticleVendu(nomArticle, description, dateDebutEncheres,dateFinEncheres, prixInitial, categorie, retrait, vendeur);
			System.out.println(article);
			articleVendu = this.articleDAO.insertArticle(article);
			
		} else {
			articleVendu = null;
			throw businessException;
		}

		return articleVendu;
	}

	private void checkDateFin(LocalDate dateFinEncheres) {
		// TODO Auto-generated method stub
		
	}

	private void checkVendeur(Utilisateur vendeur) {
		// TODO Auto-generated method stub
		
	}

	private void checkRetrait(Retrait retrait) {
		// TODO Auto-generated method stub
		
	}

	private void checkCategorie(Categorie categorie) {
		// TODO Auto-generated method stub
		
	}

	private void checkPrixInitial(int prixInitial) {
		// TODO Auto-generated method stub
		
	}

	private void checkDateDebut(LocalDate dateDebutEncheres) {
		// TODO Auto-generated method stub
		
	}

	private void checkDescription(String description) {
		// TODO Auto-generated method stub
		
	}

	private void checkNom(String nomArticle) {
		// TODO Auto-generated method stub
		
	}
}
