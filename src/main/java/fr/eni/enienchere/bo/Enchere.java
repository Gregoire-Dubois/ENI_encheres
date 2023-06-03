/**
* <p>Titre : eni_encheres</p>
* <p>Description : BO Enchere</p>
* <p>Groupe : les Vioques</p>
* @author Cédric CREPIN
* @version 1.0
*/
package fr.eni.enienchere.bo;

import java.sql.Timestamp;

public class Enchere {

	//private static final long serialVersionUID = 1L;
	
	private int id; //??? Déjà présent dans ArticleVendu non?
	private ArticleVendu article;
	private Utilisateur utilisateur;
	private Timestamp dateEnchere; //LocalDateTime ou lieu de Timestamp
	private int montantEnchere;
	
	public Enchere() {
	}

	public Enchere(int id, ArticleVendu article, Utilisateur utilisateur, Timestamp dateEnchere, int montantEnchere) {
		super();
		this.id = id;
		this.article = article;
		this.utilisateur = utilisateur;
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
	}

	public Enchere(ArticleVendu article, Utilisateur utilisateur, Timestamp dateEnchere, int montantEnchere) {
		super();
		this.article = article;
		this.utilisateur = utilisateur;
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Enchere [" +
				"id= " + id +
				", article = " + article +
				", utilisateur = " + utilisateur +
				", dateEnchere = " + dateEnchere +
				", montantEnchere = " + montantEnchere +
				']';
	}

	public int getId() {
		return id; 
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArticleVendu getArticle() {
		return article;
	}

	public void setArticle(ArticleVendu article) {
		this.article = article;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Timestamp getDateEnchere() {
		return dateEnchere;
	}

	public void setDateEnchere(Timestamp dateEnchere) {
		this.dateEnchere = dateEnchere;
	}

	public int getMontantEnchere() {
		return montantEnchere;
	}

	public void setMontantEnchere(int montantEnchere) {
		this.montantEnchere = montantEnchere;
	}	
}
