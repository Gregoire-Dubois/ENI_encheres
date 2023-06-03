/*BO Utilisateur
 *v1 Gregoire Dubois 
 *v2 Ced : correction sur les associations  
 */


package fr.eni.enienchere.bo;

import java.util.Date;
import java.util.List;

public class ArticleVendu {
	
	//private static final long serialVersionUID = 1L;

	private int noArticle;
	private String nomArticle;
	private String description;
	private Date dateDebutEncheres;//LocalDate
	private Date dateFinEncheres;//LocalDate
	private int prixInitial;
	private int prixVente;
	private Utilisateur utilisateur; //Différencier acquéreur et vendeur?
	//private Utilisateur acquereur;
	private Categorie categorie;
	private Retrait retrait;
	
	
	//private Utilisateur vendeur;
	//private List<Enchere> listeEncheres;

	public ArticleVendu() {
		super();
	}

	
	
	/**
	 * @param noArticle
	 * @param nomArticle
	 * @param description
	 * @param dateDebutEncheres
	 * @param dateFinEncheres
	 * @param prixInitial
	 * @param prixVente
	 * @param utilisateur
	 * @param categorie
	 * @param retrait
	 */
	public ArticleVendu(int noArticle, String nomArticle, String description, Date dateDebutEncheres,
			Date dateFinEncheres, int prixInitial, int prixVente, Utilisateur utilisateur, Categorie categorie,
			Retrait retrait) {
		super();
		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.prixInitial = prixInitial;
		this.prixVente = prixVente;
		this.utilisateur = utilisateur;
		this.categorie = categorie;
		this.retrait = retrait;
	}

	

	/**
	 * @param noArticle
	 * @param nomArticle
	 * @param description
	 * @param dateDebutEncheres
	 * @param dateFinEncheres
	 * @param prixInitial
	 * @param prixVente
	 * @param utilisateur
	 * @param categorie
	 */
	public ArticleVendu(int noArticle, String nomArticle, String description, Date dateDebutEncheres,
			Date dateFinEncheres, int prixInitial, int prixVente, Utilisateur utilisateur, Categorie categorie) {
		super();
		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.prixInitial = prixInitial;
		this.prixVente = prixVente;
		this.utilisateur = utilisateur;
		this.categorie = categorie;
	}


/*
 * Comment CCN 02/06/2023
 * 
	public ArticleVendu(int noArticle, String nomArticle, String description, Date dateDebutEncheres,
			Date dateFinEncheres, int prixInitial, int prixVente, int noUtilisateur, int noCategorie) {
		super();
		
		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.prixInitial = prixInitial;
		this.prixVente = prixVente;
		this.noUtilisateur = noUtilisateur;
		this.noCategorie = noCategorie;
	}
*/
	public int getNoArticle() {
		return noArticle;
	}

	public void setNoArticle(int noArticle) {
		this.noArticle = noArticle;
	}

	public String getNomArticle() {
		return nomArticle;
	}

	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateDebutEncheres() {
		return dateDebutEncheres;
	}

	public void setDateDebutEncheres(Date dateDebutEncheres) {
		this.dateDebutEncheres = dateDebutEncheres;
	}

	public Date getDateFinEncheres() {
		return dateFinEncheres;
	}

	public void setDateFinEncheres(Date dateFinEncheres) {
		this.dateFinEncheres = dateFinEncheres;
	}

	public int getPrixInitial() {
		return prixInitial;
	}

	public void setPrixInitial(int prixInitial) {
		this.prixInitial = prixInitial;
	}

	public int getPrixVente() {
		return prixVente;
	}

	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}
	
	

	@Override
	public String toString() {
		return "ArticleVendu [" +
				"noArticle=" + noArticle + 
				", nomArticle=" + nomArticle + 
				", description=" + description +
				", dateDebutEncheres=" + dateDebutEncheres + 
				", dateFinEncheres=" + dateFinEncheres + 
				", prixInitial=" + prixInitial + 
				", prixVente=" + prixVente + 
				", utilisateur=" + utilisateur + 
				", categorie=" + categorie + 
				"]";
	}

}


