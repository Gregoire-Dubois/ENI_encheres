/*BO Utilisateur
 *v1 Gregoire Dubois 
 *v2 Ced : correction sur les associations  
 */


package fr.eni.enienchere.bo;

import java.time.LocalDate;
import java.util.List;
import fr.eni.enienchere.BusinessException;
import fr.eni.enienchere.dal.DAOFactory;

public class ArticleVendu {
	
	//private static final long serialVersionUID = 1L;

	private int noArticle;
	private String nomArticle;
	private String description;
	private LocalDate dateDebutEncheres;
	private LocalDate dateFinEncheres;
	private int prixInitial;
	private int prixVente;
	private String etatVente;
	//private Utilisateur utilisateur; //Différencier acquéreur et vendeur? 
	//CCN : pour moi non, l'article appartient à un uilisateur à un instant T. Par contre il faudra faire le changement de propriétaire à la fin de la vente. 
	//et comment on stockerait ces valeurs ? 
	//private Utilisateur acquereur;
	//private Utilisateur acquereur;
	private Categorie categorie;
	private Retrait retrait;
	private Utilisateur vendeur;//OK
	
	
	private List<Enchere> listeEncheres; //CCN : quel intérêt ? 
	
	
	public ArticleVendu() {
		super();
		//listeEncheres =new ArrayList<>();
	}
	
	
	public ArticleVendu(int noArticle, String nomArticle, String description, LocalDate dateDebutEncheres,
			LocalDate dateFinEncheres, int prixInitial, int prixVente, String etatVente) {
		super();
		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.prixInitial = prixInitial;
		this.prixVente = prixVente;
		this.etatVente = etatVente;
		
	}

	
	public ArticleVendu(int noArticle, String nomArticle, String description, LocalDate dateDebutEncheres,
			LocalDate dateFinEncheres, int prixInitial, int prixVente, Utilisateur acquereur, Categorie categorie,
			Retrait retrait, String etatVente, Utilisateur vendeur) {
		super();
		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.prixInitial = prixInitial;
		this.prixVente = prixVente;
		this.etatVente = etatVente;
		//this.setAcquereur(acquereur);
		this.setCategorie(categorie);
		this.setRetrait(retrait);
		this.setVendeur(vendeur);
	}
	
	public ArticleVendu(String nomArticle, String description, LocalDate dateDebutEncheres, LocalDate dateFinEncheres, int prixInitial,
			Categorie categorie, Retrait retrait, Utilisateur vendeur) {
		super();
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.prixInitial = prixInitial;
		this.setCategorie(categorie);
		this.setRetrait(retrait);
		this.setVendeur(vendeur);
	}
	

	public ArticleVendu(int noArticle, String nomArticle, String description, LocalDate dateDebutEncheres,
			LocalDate dateFinEncheres, int prixInitial, int prixVente, String etatVente, Categorie categorie) {
		super();
		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.prixInitial = prixInitial;
		this.prixVente = prixVente;
		this.etatVente = etatVente;
		this.setCategorie(categorie);
	}
	
	//TJ : Constructeur pour Détail Vente
	public ArticleVendu(String nomArticle, String description, Categorie categorie, int prixVente,
			Utilisateur acquereur, int prixInitial, LocalDate dateFinEncheres, Retrait retrait,
			Utilisateur vendeur) {
		super();
		this.nomArticle = nomArticle;
		this.description = description;
		this.setCategorie(categorie);
		this.prixVente = prixVente;
		this.setVendeur(acquereur);
		this.prixInitial = prixInitial;
		this.dateFinEncheres = dateFinEncheres;
		this.setRetrait(retrait);
		this.setVendeur(vendeur);	
	}
	
	//TJ : Constructeur pour la liste des articles sur la page d'acceuil
	public ArticleVendu(int no_article, String nomArticle, int prixVente, LocalDate dateFinEncheres,Utilisateur vendeur) {
		super();
		this.noArticle = no_article;
		this.nomArticle = nomArticle;
		this.dateFinEncheres = dateFinEncheres;
		this.prixVente = prixVente;
		this.setCategorie(categorie);
		this.setVendeur(vendeur);
	}
	
//	public ArticleVendu() {
//		super();
//	}

	
	//Modification des constructeurs car ajout d'attributs et changement Date en LocalDate
//	/**
//	 * @param noArticle
//	 * @param nomArticle
//	 * @param description
//	 * @param dateDebutEncheres
//	 * @param dateFinEncheres
//	 * @param prixInitial
//	 * @param prixVente
//	 * @param utilisateur
//	 * @param categorie
//	 * @param retrait
//	 */
//	public ArticleVendu(int noArticle, String nomArticle, String description, LocalDate dateDebutEncheres,
//			LocalDate dateFinEncheres, int prixInitial, int prixVente, Utilisateur utilisateur, Categorie categorie,
//			Retrait retrait) {
//		super();
//		this.noArticle = noArticle;
//		this.nomArticle = nomArticle;
//		this.description = description;
//		this.dateDebutEncheres = dateDebutEncheres;
//		this.dateFinEncheres = dateFinEncheres;
//		this.prixInitial = prixInitial;
//		this.prixVente = prixVente;
//		this.utilisateur = utilisateur;
//		this.categorie = categorie;
//		this.retrait = retrait;
//	}

	

//	/**
//	 * @param noArticle
//	 * @param nomArticle
//	 * @param description
//	 * @param dateDebutEncheres
//	 * @param dateFinEncheres
//	 * @param prixInitial
//	 * @param prixVente
//	 * @param utilisateur
//	 * @param categorie
//	 */
//	public ArticleVendu(int noArticle, String nomArticle, String description, LocalDate dateDebutEncheres,
//			LocalDate dateFinEncheres, int prixInitial, int prixVente, Utilisateur utilisateur, Categorie categorie) {
//		super();
//		this.noArticle = noArticle;
//		this.nomArticle = nomArticle;
//		this.description = description;
//		this.dateDebutEncheres = dateDebutEncheres;
//		this.dateFinEncheres = dateFinEncheres;
//		this.prixInitial = prixInitial;
//		this.prixVente = prixVente;
//		this.utilisateur = utilisateur;
//		this.categorie = categorie;
//	}








	


	


	/*
 * Comment CCN 02/06/2023
 * 
	public ArticleVendu(int noArticle, String nomArticle, String description, LocalDatedateDebutEncheres,
			LocalDatedateFinEncheres, int prixInitial, int prixVente, int noUtilisateur, int noCategorie) {
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

	public LocalDate getDateDebutEncheres() {
		return dateDebutEncheres;
	}

	public void setDateDebutEncheres(LocalDate dateDebutEncheres) {
		this.dateDebutEncheres = dateDebutEncheres;
	}

	public LocalDate getDateFinEncheres() {
		return dateFinEncheres;
	}

	public void setDateFinEncheres(LocalDate dateFinEncheres) {
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
	
	
//	public Utilisateur getAcquereur() {
//		return acquereur;
//	}
//
//	public void setAcquereur(Utilisateur acquereur) {
//
//		this.acquereur=acquereur;
//		
//		if(acquereur!=null) {
//			acquereur.ajouterArticleAchat(this);
//		}
//	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;

//		if(categorie!=null) {
//			categorie.ajouterArticle(this);
//		}
	}

	public Retrait getRetrait() {
		return retrait;
	}

	public void setRetrait(Retrait retrait) {
		this.retrait = retrait;

	}

	public Utilisateur getVendeur() {
		return vendeur;
	}

	public void setVendeur(Utilisateur vendeur) {
		this.vendeur = vendeur;
		
//		if(vendeur!=null) {
//			vendeur.ajouterArticleVente(this);
//		}
	}

	public List<Enchere> getListeEncheres() throws BusinessException {
		if(listeEncheres==null) {
			
			listeEncheres=DAOFactory.getEnchereDAO().selectAllEnchereByNoArticle(noArticle);
		}
		return listeEncheres;
	}

//	public void ajouterEnchere(Enchere enchere) {
//		if(enchere.getArticle().equals(this) && !listeEncheres.contains(enchere))
//		{
//			this.listeEncheres.add(enchere);
//		}
//		else
//		{
//			System.out.println("ajout impossible");
//		}
//	}
	public String getEtatVente() {
		return etatVente;
	}

	public void setEtatVente(String etatVente) {
		this.etatVente = etatVente;
	}

	@Override
	public String toString() {
		return "ArticleVendu [noArticle=" + noArticle + ", nomArticle=" + nomArticle + ", description=" + description
				+ ", dateDebutEncheres=" + dateDebutEncheres + ", dateFinEncheres=" + dateFinEncheres + ", prixInitial="
				+ prixInitial + ", prixVente=" + prixVente + ", etatVente=" + etatVente + ", categorie=" + categorie + ", retrait=" + retrait + ", vendeur=" + vendeur +"]";
	}

}


