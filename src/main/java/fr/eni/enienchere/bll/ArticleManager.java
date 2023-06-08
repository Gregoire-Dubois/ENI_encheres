package fr.eni.enienchere.bll;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import fr.eni.enienchere.BusinessException;
import fr.eni.enienchere.bo.ArticleVendu;
import fr.eni.enienchere.bo.Categorie;
import fr.eni.enienchere.bo.Retrait;
import fr.eni.enienchere.bo.Utilisateur;
import fr.eni.enienchere.dal.ArticleDAO;
import fr.eni.enienchere.dal.DAOFactory;

public class ArticleManager {

	private static ArticleManager instance = null;
	private ArticleDAO articleDAO;

    //Constructeur privé
	public ArticleManager() {
		this.articleDAO=DAOFactory.getArticleDAO();
	}
	
	//SINGLETON
	public static ArticleManager getInstance() {
		if (instance == null) {
			instance = new ArticleManager();
		}
		return instance;
	}
	
	public ArticleVendu selectArticleById(int id) throws BusinessException {
		ArticleVendu article = this.articleDAO.selectArticleById(id);
		return 	article;
	}
	
	// Methode pour afficher article dans page accueil non connecté
	
	public ArrayList<ArticleVendu> selectionnerArticles() throws BusinessException {
		
		//BusinessException businessException = new BusinessException();

		ArrayList<ArticleVendu> articles = new ArrayList<>();
			
		articles = (ArrayList<ArticleVendu>) articleDAO.selectAllArticles();
	
		return articles;
		
	}
	
	public List<ArticleVendu> selectionnerArticlesFiltres(String etatVente, String mot, String categorie) throws BusinessException{
		List<ArticleVendu> articles = null;
		BusinessException businessException = new BusinessException();
		if(!(etatVente.equals("EC")||etatVente.equals("VE")||etatVente.equals("NC"))) {
			businessException.ajouterErreur(CodesResultatBLL.ETAT_VENTE_ERREUR);
		}
		
		if (!businessException.hasErreur()) {
			
				
					articles=this.articleDAO.selectionnerArticlesFiltres(categorie, mot, etatVente);
//					if(articles==null) {
//						businessException.ajouterErreur(CodesResultatServlet.PAS_D_ARTICLES_ERREUR);
//						throw businessException;
//					}
		} else {
			
			
			throw businessException;
		}
		
		
		return articles;
	}
	
	//Avant d'ajouter, on doit vérifier les champs
	//nomArticle, description, dateDebutEncheres, dateFinEncheres,  prixInitial, (categorie, retrait, vendeur pas nécéssaire)
	public ArticleVendu ajouter(String nomArticle, String description, LocalDate dateDebutEncheres, LocalDate dateFinEncheres, int prixInitial, Categorie categorie, Retrait retrait, Utilisateur vendeur) throws BusinessException {
		BusinessException businessException = new BusinessException();
		ArticleVendu article;
		ArticleVendu articleVendu;
		//Méthodes check à faire avec gestion des erreurs
		this.checkNom(nomArticle, businessException);
		this.checkDescription(description, businessException);
		this.checkDateDebut(dateDebutEncheres, businessException);
		this.checkDateFin(dateFinEncheres, dateDebutEncheres, businessException);
		this.checkPrixInitial(prixInitial, businessException);
		//this.checkCategorie(categorie, businessException); 
		//this.checkRetrait(retrait, businessException); est-ce utile car le check se fait quand on va essayer d'insérer dans la table retrait
		//this.checkVendeur(vendeur, businessException); est-ce utile?
				
		
		
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
	
	
	public List<ArticleVendu> getAllVentesEnCoursByNoCategorie(int id) throws BusinessException {
        return articleDAO.selectAllVentesEnCoursByNoCategorie(id);
    }
	
	public List<ArticleVendu> getAllVentesNonCommenceesByNoUtilisateur(int no_utilisateur) throws BusinessException {
        return articleDAO.selectAllVentesNonCommenceesByNoUtilisateur(no_utilisateur);
    }
	
	public List<ArticleVendu> getAllVentesEnCoursByNoUtilisateur(int no_utilisateur) throws BusinessException {
        return articleDAO.selectAllVentesEnCoursByNoUtilisateur(no_utilisateur);
    }
	
	public List<ArticleVendu> getAllVentesTermineesByNoUtilisateur(int no_utilisateur) throws BusinessException {
        return articleDAO.selectAllVentesTermineesByNoUtilisateur(no_utilisateur);
    }

	public List<ArticleVendu> getAllEncheresEnCoursByNoUtilisateur(int no_utilisateur) throws BusinessException {
        return articleDAO.selectAllEncheresEnCoursByNoUtilisateur(no_utilisateur);
    }
	
	public List<ArticleVendu> getAllEncheresGagneesByNoUtilisateur(int no_utilisateur) throws BusinessException {
        return articleDAO.selectAllEncheresGagneesByNoUtilisateur(no_utilisateur);
    }
	
	private void checkDateFin(LocalDate dateFinEncheres, LocalDate dateDebutEncheres, BusinessException businessException) {
		
		if (dateFinEncheres.isBefore(dateDebutEncheres) || dateFinEncheres.isEqual(dateDebutEncheres)) {
			businessException.ajouterErreur(CodesResultatBLL.DATE_FIN_ENCHERE_INFERIEURE_EGALE_DATE_DEBUT_ERREUR);
		}
	}

//	private void checkVendeur(Utilisateur vendeur, BusinessException businessException) {
//
//		List<Utilisateur> listeVendeur = new ArrayList<>();
//		
//		if (!listeVendeur.contains(vendeur)) {
//			businessException.ajouterErreur(CodesResultatBLL.PSEUDO_VENDEUR_INTROUVABLE_ERREUR);
//		}
//		
//		if (String.valueOf(vendeur).isEmpty()) {
//	        businessException.ajouterErreur(CodesResultatBLL.PSEUDO_VENDEUR_VIDE_ERREUR);
//		}
//		
//	}
//
//	private void checkRetrait(Retrait retrait, BusinessException businessException) {
//
//	        if (retrait.getRue().isEmpty()) {
//	            businessException.ajouterErreur(CodesResultatBLL.RUE_VIDE_ERREUR);
//	        }
//	        
//	        if (retrait.getCodePostal().isEmpty()) {
//	            businessException.ajouterErreur(CodesResultatBLL.CODEPOSTAL_VIDE_ERREUR);
//	        }
//	        
//	        if (retrait.getVille().isEmpty()) {
//	            businessException.ajouterErreur(CodesResultatBLL.VILLE_VIDE_ERREUR);
//	        }
//	        		
//	}
//
//	/*
//	 * Pas sûr que ce contrôle soit utile car l'utilisateur n'a accès qu'à une liste déroulante prédéfinie dans la JSP (JSPPageVendreArticle.jsp).
//	 * Il est selon moi impossible de saisir une autre catégorie ou de laisser le champ vide.
//	 */
//	private void checkCategorie(Categorie categorie, BusinessException businessException) {
//		
//		List<Categorie> listeCategorie = new ArrayList<>();
//		
//		if (!listeCategorie.contains(categorie)) {
//			businessException.ajouterErreur(CodesResultatBLL.CATEGORIE_ARTICLE_NON_AUTORISEE_ERREUR);
//		}
//		
//		if (String.valueOf(categorie).isEmpty()) {
//	        businessException.ajouterErreur(CodesResultatBLL.CATEGORIE_ARTICLE_VIDE_ERREUR);
//	    }
//		
//	}

	private void checkPrixInitial(int prixInitial, BusinessException businessException) {

		if (prixInitial <= 0) {
	        businessException.ajouterErreur(CodesResultatBLL.PRIX_VENTE_INFERIEUR_EGAL_A_0_ERREUR);
	    }
	    
	    if (String.valueOf(prixInitial).isEmpty()) {
	        businessException.ajouterErreur(CodesResultatBLL.PRIX_DE_VENTE_VIDE_ERREUR);
	    }
		
	}

	private void checkDateDebut(LocalDate dateDebutEncheres, BusinessException businessException) {
		
		LocalDate currentDate = LocalDate.now();
	    
	    if (dateDebutEncheres.isBefore(currentDate)) {
	        businessException.ajouterErreur(CodesResultatBLL.DATE_DEBUT_ENCHERE_INFERIEURE_DATE_JOUR_ERREUR);
	    }
	}

	private void checkDescription(String description, BusinessException businessException) {

		if (description.trim().isEmpty()) {
			System.out.println("Erreur sur le nom de l'article vide");
			businessException.ajouterErreur(CodesResultatBLL.DESCRIPTION_ARTICLE_VIDE_ERREUR);
		}
		//On peut mettre ce qu'on veut dans la description
//		if (!description.trim().matches("^[a-zA-ZÀ-ÿ\\-]+$")) {
//			System.out.println("Erreur sur le nom de la'rticle qui n'est pas alphanumérique");
//			businessException.ajouterErreur(CodesResultatBLL.DESCRIPTION_ARTICLE_ALPHA_ERREUR);
//		}
		
	}

	private void checkNom(String nomArticle, BusinessException businessException) {
		
		if (nomArticle.trim().isEmpty()) {
			System.out.println("Erreur sur le nom de l'article vide");
			businessException.ajouterErreur(CodesResultatBLL.NOM_ARTICLE_VIDE_ERREUR);
		}
		//Utile? on peut mettre ce qu'on veut comme nom d'article
//		if (!nomArticle.trim().matches("^[a-zA-ZÀ-ÿ\\-]+$")) {
//			System.out.println("Erreur sur le nom de la'rticle qui n'est pas alphanumérique");
//			businessException.ajouterErreur(CodesResultatBLL.NOM_ARTICLE_ALPHA_ERREUR);
//		}
		
	}
	
	public List<ArticleVendu> selectionnerArticlesFiltresAchats(String[] filtresAchats, int nombreParametres, Utilisateur utilisateur) throws BusinessException {
		List<ArticleVendu> listeArticles = new ArrayList<>();
			switch (nombreParametres) {
			case 1: {
				String filtre = filtresAchats[0];
					switch (filtre) {
					case "enchereOuverte": 
						listeArticles= this.articleDAO.selectArticlesECSansUtilisateur(utilisateur.getNoUtilisateur());
					
						break;
					case "mesEncheresEnCours": 
						listeArticles= this.articleDAO.selectMesEncheresEnCours(utilisateur.getNoUtilisateur());
						break;
					case "mesEncheresRemportees": 
						listeArticles= this.articleDAO.selectEncheresRemportees(utilisateur.getNoUtilisateur());
						
					default: System.out.println("A faire");
						break;
					}break;
				
			}
			case 2: {
				String filtre1=filtresAchats[0];
				String filtre2=filtresAchats[1];
				switch (filtre1) {
				case "enchereOuverte": 
						if(filtre2.equals("mesEncheresEnCours")) {
							listeArticles= this.articleDAO.selectArticlesECSansUtilisateur(utilisateur.getNoUtilisateur()); //Enchere ouverte + Mes encheres en cours = Enchere ouverte car mes encheres en cours sont comprises dedans.
						}else { //Encheres remportees
							listeArticles= this.articleDAO.selectArticlesECSansUtilisateurPlusEncheresRemportees(utilisateur.getNoUtilisateur());
						}
					break;
				case "mesEncheresEnCours": 
						if(filtre2.equals("enchereOuverte")) {
							listeArticles= this.articleDAO.selectArticlesECSansUtilisateur(utilisateur.getNoUtilisateur());
						}else {//Encheres remportees
							listeArticles= this.articleDAO.selectArticlesEncheresRemporteesPlusMesEncheresEnCours(utilisateur.getNoUtilisateur());
						}
					break;
				case "mesEncheresRemportees": 
					if(filtre2.equals("enchereOuverte")) {
						listeArticles= this.articleDAO.selectArticlesECSansUtilisateurPlusEncheresRemportees(utilisateur.getNoUtilisateur());
					}else {//mesEncheresEnCours
						listeArticles= this.articleDAO.selectArticlesEncheresRemporteesPlusMesEncheresEnCours(utilisateur.getNoUtilisateur());
					}
					
					break;
				default: System.out.println("A faire");
					break;
				}break;
			}
			case 3: {
				listeArticles= this.articleDAO.selectArticlesEncheresRemporteesPlusMesEncheresEnCours(utilisateur.getNoUtilisateur());
			}break;
			default:
				System.out.println("A faire");
				break;
			}
		
		return listeArticles;
	}

	public List<ArticleVendu> selectionnerArticlesFiltresVentes(String[] filtresAchats, int nombreParametres,	Utilisateur utilisateur) throws BusinessException {
		List<ArticleVendu> listeArticles = new ArrayList<>();
		switch (nombreParametres) {
		case 1: {
			String filtre = filtresAchats[0];
				switch (filtre) {
				case "mesVentesEnCours": 
					listeArticles= this.articleDAO.selectMesVentesEnCours(utilisateur.getNoUtilisateur());
				
					break;
				case "ventesNonDebutees": 
					listeArticles= this.articleDAO.selectMesVentesNonDebutees(utilisateur.getNoUtilisateur());
					break;
				case "ventesTerminees": 
					listeArticles= this.articleDAO.selectMesVentesTerminees(utilisateur.getNoUtilisateur());
					
				default: System.out.println("A faire");
					break;
				}break;
			
		}
		case 2: {
			String filtre1=filtresAchats[0];
			String filtre2=filtresAchats[1];
			switch (filtre1) {
			case "mesVentesEnCours": 
					if(filtre2.equals("ventesNonDebutees")) {
						listeArticles= this.articleDAO.selectMesVentesEnCoursPlusVentesNonDebutees(utilisateur.getNoUtilisateur()); 
					}else { //Ventes terminees
						listeArticles= this.articleDAO.selectMesVentesEnCoursPlusVentesTerminees(utilisateur.getNoUtilisateur());
					}
				break;
			case "ventesNonDebutees": 
					if(filtre2.equals("mesVentesEnCours")) {
						listeArticles= this.articleDAO.selectMesVentesEnCoursPlusVentesNonDebutees(utilisateur.getNoUtilisateur()); 
					}else {//Ventes terminees
						listeArticles= this.articleDAO.selectVentesNonDebuteesVentesTerminees(utilisateur.getNoUtilisateur());
					}
				break;
			case "ventesTerminees": 
				if(filtre2.equals("mesVentesEnCours")) {
					listeArticles= this.articleDAO.selectMesVentesEnCoursPlusVentesTerminees(utilisateur.getNoUtilisateur());
				}else {//ventes non debutees
					listeArticles= this.articleDAO.selectVentesNonDebuteesVentesTerminees(utilisateur.getNoUtilisateur());
				}
				
				break;
			default: System.out.println("A faire");
				break;
			}break;
		}
		case 3: {
			listeArticles= this.articleDAO.selectMesVentesEnCoursNonDebuteesEtTerminees(utilisateur.getNoUtilisateur());
		}break;
		default:
			System.out.println("A faire");
			break;
		}
	
	return listeArticles;
	}
}
