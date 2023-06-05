/**
* <p>Titre : eni_encheres</p>
* <p>Description : BO Enchere</p>
* <p>Groupe : les Vioques</p>
* @author Cédric CREPIN
* @version 1.0
*/
package fr.eni.enienchere.bo;

import java.time.LocalDateTime;

public class Enchere {

	//private static final long serialVersionUID = 1L;
	
	//private int id; //??? Déjà présent dans ArticleVendu non? 
	//CCN : en fait ici on revient sur un point non tranché. En base enchere n'a pas d'ID ce que je trouve très très chiant. 
	private ArticleVendu article;
	private Utilisateur utilisateur;
	private LocalDateTime dateEnchere;
	private int montantEnchere;
	
	public Enchere() {
	}
	
	public Enchere(ArticleVendu article, Utilisateur utilisateur, LocalDateTime dateEnchere, int montantEnchere) {
		super();
		this.article = article;
		this.setUtilisateur(utilisateur);
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
	}
//Modification des constructeurs LocalDate et ajout des relations?
//	public Enchere(int id, ArticleVendu article, Utilisateur utilisateur, Timestamp dateEnchere, int montantEnchere) {
//		super();
//		this.id = id;
//		this.article = article;
//		this.utilisateur = utilisateur;
//		this.dateEnchere = dateEnchere;
//		this.montantEnchere = montantEnchere;
//	}
//
//	public Enchere(ArticleVendu article, Utilisateur utilisateur, Timestamp dateEnchere, int montantEnchere) {
//		super();
//		this.article = article;
//		this.utilisateur = utilisateur;
//		this.dateEnchere = dateEnchere;
//		this.montantEnchere = montantEnchere;
//	}

	

//	public int getId() {
//		return id; 
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}

	public ArticleVendu getArticle() {
		return article;
	}

	public void setArticle(ArticleVendu article) {
		this.article = article;
	}

//	public Utilisateur getUtilisateur() {
//		return utilisateur;
//	}
//
//	public void setUtilisateur(Utilisateur utilisateur) {
//		this.utilisateur = utilisateur;
//	}

	public LocalDateTime getDateEnchere() {
		return dateEnchere;
	}

	public void setDateEnchere(LocalDateTime dateEnchere) {
		this.dateEnchere = dateEnchere;
	}

	public int getMontantEnchere() {
		return montantEnchere;
	}

	public void setMontantEnchere(int montantEnchere) {
		this.montantEnchere = montantEnchere;
	}	
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
		if(utilisateur!=null) {
			utilisateur.ajouterEnchere(this);
		}
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Enchere [" +
				", article = " + article +
				", utilisateur = " + utilisateur +
				", dateEnchere = " + dateEnchere +
				", montantEnchere = " + montantEnchere +
				']';
	}
}
