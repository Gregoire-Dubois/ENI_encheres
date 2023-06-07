package fr.eni.enienchere.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import fr.eni.enienchere.BusinessException;
import fr.eni.enienchere.bll.UtilisateurManager;
import fr.eni.enienchere.bo.ArticleVendu;
import fr.eni.enienchere.bo.Categorie;
import fr.eni.enienchere.bo.Retrait;
import fr.eni.enienchere.bo.Utilisateur;


public class ArticleDAOJdbcImpl implements ArticleDAO {
	
	private static final String INSERT_ARTICLE="INSERT INTO ARTICLES_VENDUS(nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,no_utilisateur,no_categorie) VALUES(?,?,?,?,?,?,?)";
	
	private static final String SELECT_ALL_ARTICLE = "SELECT no_article, a.no_utilisateur, nom_article, date_fin_encheres, prix_vente, pseudo\n"
			+ "FROM ARTICLES_VENDUS as a\n"
			+ "INNER JOIN UTILISATEURS as u ON a.no_utilisateur = u.no_utilisateur\n"
			+ "WHERE etat_vente = 'EC';";
	
private static final String SELECT_ARTICLE_BY_ID = "SELECT"
			+ "a.nomArticle, a.description, c.libelle AS categorie, e.montant_enchere, ua.pseudo AS acquereur, a.prix_initial, "
			+ "a.date_fin_encheres, r.rue AS rue_retrait, r.code_postal AS code_postal_retrait, r.ville AS ville_retrait, "
			+ "UV.pseudo AS pseudo_vendeur, "
			+ "ROW_NUMBER() OVER (ORDER BY e.montant_enchere DESC) AS row_number"
			+ "FROM"
			+ "ARTICLES_VENDUS a INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie"
			+ "LEFT JOIN ENCHERES e ON a.no_article = e.no_article"
			+ "LEFT JOIN UTILISATEURS ua ON e.no_utilisateur = ua.no_utilisateur"
			+ "LEFT JOIN UTILISATEURS uv ON a.no_utilisateur = uv.no_utilisateur"
			+ "LEFT JOIN RETRAITS r ON a.no_article = r.no_article"
			+ "WHERE"
			+ "AV.no_article = ?) AS subquery WHERE row_number = 1;";
	
	private static final String SELECT_ALL_VENTE_EC = "SELECT"
			+ "a.no_article as id_article, a.no_utilisateur as id_utilisateur, vendeur.pseudo as vendeur, a.no_categorie as id_categorie, "
			+ "c.libelle as categorie_libelle, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, "
			+ "e.no_enchere as id_enchere_max, e.no_article as ench_idArticle, e.no_utilisateur as id_meilleur_encherisseur, "
			+ "encherisseur.pseudo as pseudo_encherisseur, date_enchere, montant_enchere, etat_vente"
			+ "FROM "
			+ "ARTICLES_VENDUS a LEFT JOIN ENCHERES e ON  a.no_article =  e.no_article AND "
			+ "e.no_enchere = ( SELECT max(e.no_enchere) FROM ENCHERES e WHERE a.no_article = e.no_article)"
			+ "LEFT JOIN CATEGORIES c ON a.no_categorie = c.no_categorie"
			+ "LEFT JOIN UTILISATEURS vendeur ON a.no_utilisateur = vendeur.no_utilisateur"
			+ "LEFT JOIN UTILISATEURS encherisseur ON e.no_utilisateur = encherisseur.no_utilisateur"
			+ "WHERE "
			+ " a.etat_vente = 'EC';";
	
	private static final String SELECT_ALL_VENTE_EC_BY_CATEGORIE_ID = "SELECT"
			+ "a.no_article as id_article, a.no_utilisateur as id_utilisateur, vendeur.pseudo as vendeur,"
			+ "a.no_categorie as id_categorie, c.libelle as categorie_libelle, nom_article, description,"
			+ "date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, e.no_enchere as id_enchere_max,"
			+ "e.no_article as ench_idArticle, e.no_utilisateur as id_meilleur_encherisseur, encherisseur.pseudo as pseudo_encherisseur,"
			+ "date_enchere, montant_enchere, etat_vente"
			+ "FROM"
			+ "ARTICLES_VENDUS a LEFT JOIN ENCHERES e ON a.no_article = e.no_article AND "
			+ "e.no_enchere = ( SELECT max(e.no_enchere) FROM ENCHERES e WHERE a.no_article =  e.no_article)"
			+ "LEFT JOIN CATEGORIES c ON a.no_categorie = c.no_categorie"
			+ "LEFT JOIN UTILISATEURS vendeur ON a.no_utilisateur = vendeur.no_utilisateur"
			+ "LEFT JOIN UTILISATEURS encherisseur ON e.no_utilisateur = encherisseur.no_utilisateur"
			+ "WHERE "
			+ "a.etat_vente = 'EC' AND a.no_categorie  = ?;";
	
	private static final String SELECT_ALL_VENTE_EC_BY_NOM_ART = "SELECT"
			+ "a.no_article as id_article, a.no_utilisateur as id_utilisateur, vendeur.pseudo as vendeur, a.no_categorie as id_categorie, "
			+ "c.libelle as categorie_libelle, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, "
			+ "e.no_enchere as id_enchere_max, e.no_article as ench_idArticle, e.no_utilisateur as id_meilleur_encherisseur,"
			+ "encherisseur.pseudo as pseudo_encherisseur, date_enchere, montant_enchere, etat_vente "
			+ "FROM "
			+ "ARTICLES_VENDUS a LEFT JOIN ENCHERES e ON  a.no_article =  e.no_article AND "
			+ "e.no_enchere = ( SELECT max(e.no_enchere) FROM ENCHERES e WHERE a.no_article =  e.no_article)"
			+ "LEFT JOIN CATEGORIES c ON a.no_categorie = c.no_categorie"
			+ "LEFT JOIN UTILISATEURS vendeur ON a.no_utilisateur = vendeur.no_utilisateur"
			+ "LEFT JOIN UTILISATEURS encherisseur ON e.no_utilisateur = encherisseur.no_utilisateur"
			+ "WHERE "
			+ "a.etat_vente = 'EC' AND lower(a.nom_article) LIKE '%?%';";

	private static final String SELECT_ALL_VENTE_NC_BY_UTILISATEUR_ID = "SELECT"
			+ "a.no_article as id_article, a.no_utilisateur as id_utilisateur,vendeur.pseudo as vendeur,a.no_categorie as id_categorie, "
			+ "c.libelle as categorie_libelle, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, "
			+ "prix_vente, e.no_enchere as id_enchere_max, e.no_article as ench_idArticle,e.no_utilisateur as id_meilleur_encherisseur, "
			+ "encherisseur.pseudo as pseudo_encherisseur, date_enchere, montant_enchere, etat_vente"
			+ "FROM "
			+ "ARTICLES_VENDUS a LEFT JOIN ENCHERES e ON  a.no_article =  e.no_article AND "
			+ "e.no_enchere = ( SELECT max(e.no_enchere) FROM ENCHERES e WHERE a.no_article =  e.no_article)"
			+ "LEFT JOIN CATEGORIES c ON a.no_categorie = c.no_categorie"
			+ "LEFT JOIN UTILISATEURS vendeur ON a.no_utilisateur = vendeur.no_utilisateur"
			+ "LEFT JOIN UTILISATEURS encherisseur ON e.no_utilisateur = encherisseur.no_utilisateur"
			+ "WHERE "
			+ "a.etat_vente = 'NC' AND vendeur.no_utilisateur = ?;";
	
	private static final String SELECT_ALL_VENTE_EC_BY_UTILISATEUR_ID = "SELECT"
			+ "a.no_article as id_article, a.no_utilisateur as id_utilisateur,vendeur.pseudo as vendeur,a.no_categorie as id_categorie, "
			+ "c.libelle as categorie_libelle, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, "
			+ "prix_vente, e.no_enchere as id_enchere_max, e.no_article as ench_idArticle,e.no_utilisateur as id_meilleur_encherisseur, "
			+ "encherisseur.pseudo as pseudo_encherisseur, date_enchere, montant_enchere, etat_vente"
			+ "FROM "
			+ "ARTICLES_VENDUS a LEFT JOIN ENCHERES e ON  a.no_article =  e.no_article AND "
			+ "e.no_enchere = ( SELECT max(e.no_enchere) FROM ENCHERES e WHERE a.no_article =  e.no_article)"
			+ "LEFT JOIN CATEGORIES c ON a.no_categorie = c.no_categorie"
			+ "LEFT JOIN UTILISATEURS vendeur ON a.no_utilisateur = vendeur.no_utilisateur"
			+ "LEFT JOIN UTILISATEURS encherisseur ON e.no_utilisateur = encherisseur.no_utilisateur"
			+ "WHERE "
			+ "a.etat_vente = 'EC' AND vendeur.no_utilisateur = ?;";
	
	private static final String SELECT_ALL_VENTE_VE_BY_UTILISATEUR_ID = "SELECT"
			+ "a.no_article as id_article, a.no_utilisateur as id_utilisateur,vendeur.pseudo as vendeur,a.no_categorie as id_categorie, "
			+ "c.libelle as categorie_libelle, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, "
			+ "prix_vente, e.no_enchere as id_enchere_max, e.no_article as ench_idArticle,e.no_utilisateur as id_meilleur_encherisseur, "
			+ "encherisseur.pseudo as pseudo_encherisseur, date_enchere, montant_enchere, etat_vente"
			+ "FROM "
			+ "ARTICLES_VENDUS a LEFT JOIN ENCHERES e ON  a.no_article =  e.no_article AND "
			+ "e.no_enchere = ( SELECT max(e.no_enchere) FROM ENCHERES e WHERE a.no_article =  e.no_article)"
			+ "LEFT JOIN CATEGORIES c ON a.no_categorie = c.no_categorie"
			+ "LEFT JOIN UTILISATEURS vendeur ON a.no_utilisateur = vendeur.no_utilisateur"
			+ "LEFT JOIN UTILISATEURS encherisseur ON e.no_utilisateur = encherisseur.no_utilisateur"
			+ "WHERE "
			+ "a.etat_vente = 'VE' AND vendeur.no_utilisateur = ?;";
	
	private static final String SELECT_ALL_ENCHERES_EC_BY_UTILISATEUR_ID = "SELECT"
            + "a.no_article, a.nom_article, a.date_fin_encheres, a.prix_vente, u.no_utilisateur, u.pseudo"
            + "FROM "
            + "ARTICLES_VENDUS a "
            + "INNER JOIN ENCHERES e ON a.no_article = e.no_article "
            + "INNER JOIN UTILISATEURS u ON a.no_utilisateur = u.no_utilisateur "
            + "WHERE "
            + "a.etat_vente = 'EC' AND e.no_utilisateur = ?;";

	
	private static final String SELECT_ALL_ARTICLES_ETAT_MOT_CATEGORIE = "SELECT no_article, nom_article, prix_vente, date_fin_encheres, a.no_utilisateur, pseudo \r\n"
			+ "	FROM ARTICLES_VENDUS AS a INNER JOIN UTILISATEURS as u on a.no_utilisateur=u.no_utilisateur \r\n"
			+ "							  INNER JOIN CATEGORIES as c on c.no_categorie=a.no_categorie\r\n"
			+ "			where etat_vente=? and LOWER(nom_article) like ? and ((c.libelle = ?) or (? IS NULL))";
	
	
	private static final String ID_ARTICLE = "SELECT etat_vente "
			+ "FROM ARTICLES_VENDUS "
			+ "WHERE no_article = ?;"; 
	
	
	
	private static final String DETAIL_VENTE = "SELECT\r\n"

        + "a.nom_article, \r\n"

        + "a.description, \r\n"

        + "c.libelle as categorie_libelle, \r\n"

        + "e.montant_enchere, \r\n"

        + "e.no_utilisateur AS no_acquereur, \r\n"

        + "encherisseur.pseudo as encherisseur,\r\n"

        + "a.prix_initial, \r\n"

        + "a.date_fin_encheres, \r\n"

        + "r.rue , \r\n"

        + "r.code_postal , \r\n"

        + "r.ville , \r\n"

        + "vendeur.no_utilisateur as no_vendeur,\r\n"

        + "vendeur.pseudo as pseudo_vendeur\r\n"

        + "FROM\r\n"

        + "ARTICLES_VENDUS a INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie  \r\n"

        + "LEFT JOIN ENCHERES e ON a.no_article = e.no_article and  \r\n"

        + "e.no_enchere = ( select max(e.no_enchere) from ENCHERES e where a.no_article =  e.no_article)\r\n"

        + "LEFT JOIN UTILISATEURS vendeur on a.no_utilisateur = vendeur.no_utilisateur\r\n"

        + "LEFT JOIN UTILISATEURS encherisseur on e.no_utilisateur = encherisseur.no_utilisateur\r\n"

        + "LEFT JOIN RETRAITS r ON a.no_article = r.no_article\r\n"

        + "WHERE\r\n"

        + "a.no_article = ?;";
	
	
	public String idArticleInList(int id) {
		String etatDeVente = ""; 
		Connection cnx = null;
		PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    
	    
	    
		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(ID_ARTICLE);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				etatDeVente = rs.getString("etat_vente");
				
			}			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
	    	closeResources(cnx, pstmt, rs);

		}
		
		return etatDeVente;
		
	}
	
	// méthode en cours de réécriture
	
	@Override

    public ArticleVendu selectArticleById(int articleId) throws BusinessException {

        ArticleVendu article = null;
        Connection cnx = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            cnx = ConnectionProvider.getConnection();
            pstmt = cnx.prepareStatement(DETAIL_VENTE);
            pstmt.setInt(1, articleId);
            rs = pstmt.executeQuery();

            while (rs.next()) {

                String nomArticle = rs.getString("nom_article");
                String description = rs.getString("description");
                String categorie = rs.getString("categorie_libelle");
                int prixVente = rs.getInt("montant_enchere");
                int noAcquereur = rs.getInt("no_acquereur");
                String pseudoAcquereur = rs.getString("encherisseur");
                int prixInitial = rs.getInt("prix_initial");
                LocalDate dateFinEncheres = rs.getDate("date_fin_encheres").toLocalDate();
                String rueRetrait = rs.getString("rue");
                String codePostalRetrait = rs.getString("code_postal");
                String villeRetrait = rs.getString("ville");
                int noVendeur = rs.getInt("no_vendeur");
                String pseudoVendeur = rs.getString("pseudo_vendeur");

                // Récupérer les objets Utilisateur correspondants aux pseudos

                UtilisateurManager utilisateurManager = new UtilisateurManager();

                Utilisateur acquereur = utilisateurManager.selectionner(noAcquereur);

                Utilisateur vendeur = utilisateurManager.selectionner(noVendeur);

//                Utilisateur acquereur = UtilisateurDAO.selectByPseudo(pseudoAcquereur);

//                Utilisateur vendeur = UtilisateurDAO.selectByPseudo(pseudoVendeur);

                // Créer l'objet Retrait

                Retrait retrait = new Retrait(rueRetrait, codePostalRetrait, villeRetrait);

                // Créer l'objet Categorie

                Categorie categorieArt = new Categorie();

                categorieArt.setLibelle(categorie);

 

                // Créer l'objet ArticleVendu

                article = new ArticleVendu(nomArticle, description, categorieArt, prixVente, acquereur,

                            prixInitial, dateFinEncheres, retrait, vendeur);
                

                }

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            closeResources(cnx, pstmt, rs);

        }
        System.out.println("---->" + article);

        return article;

        }
	
	
	
	
	
/*
	@Override
	public ArticleVendu selectArticleById(int articleId) throws BusinessException {
		ArticleVendu article = null;
		Connection cnx = null;
		PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
    		cnx = ConnectionProvider.getConnection();
    		pstmt = cnx.prepareStatement(DETAIL_VENTE);// SELECT_ARTICLE_BY_ID

    		pstmt.setInt(1, articleId);
    		
    		rs = pstmt.executeQuery();
    		

	        if (rs.next()) {
	        	String nomArticle = rs.getString("nomArticle");
                String description = rs.getString("description");
                String categorie = rs.getString("categorie");
                int prixVente = rs.getInt("montant_enchere");
                String pseudoAcquereur = rs.getString("pseudo_acquereur");
                int prixInitial = rs.getInt("prix_initial");
                LocalDate dateFinEncheres = rs.getDate("date_fin_encheres").toLocalDate();
                String rueRetrait = rs.getString("rue_retrait");
                String codePostalRetrait = rs.getString("code_postal_retrait");
                String villeRetrait = rs.getString("ville_retrait");
                String pseudoVendeur = rs.getString("pseudo_vendeur");

                // Récupérer les objets Utilisateur correspondants aux pseudos
                UtilisateurManager utilisateurManager = new UtilisateurManager();
                Utilisateur acquereur = utilisateurManager.selectionnerPseudo(pseudoAcquereur); 
                Utilisateur vendeur = utilisateurManager.selectionnerPseudo(pseudoVendeur);
//                Utilisateur acquereur = UtilisateurDAO.selectByPseudo(pseudoAcquereur);
//                Utilisateur vendeur = UtilisateurDAO.selectByPseudo(pseudoVendeur);

                // Créer l'objet Retrait
                Retrait retrait = new Retrait(rueRetrait, codePostalRetrait, villeRetrait);

                // Créer l'objet Categorie
                Categorie categorieArt = new Categorie();
                categorieArt.setLibelle(categorie);

                // Créer l'objet ArticleVendu
                article = new ArticleVendu(nomArticle, description, categorieArt, prixVente, acquereur,
	                        prixInitial, dateFinEncheres, retrait, vendeur);
	            }
	      
	        	System.out.println(" ---->" + article);
	        
	    } catch (SQLException e) {
			e.printStackTrace();
	    } finally {
	    	closeResources(cnx, pstmt, rs);
	    }

	    return article;
	    }
*/

	@Override
	public List<ArticleVendu> selectAllArticles() throws BusinessException {

		// code Greg 
		
		ArrayList<ArticleVendu> listeArticlesEnVente = new ArrayList<>(); 
			
		ArticleVendu articlesVendus= null; 
		Connection cnx = null;
		
		try {
			cnx = ConnectionProvider.getConnection();
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL_ARTICLE);
			ResultSet rst = pstmt.executeQuery();
			
			while (rst.next()) {
				
				String pseudo = rst.getString("pseudo"); 
				int noUtilisateur = rst.getInt("no_utilisateur");
				Utilisateur utilisateur = new Utilisateur(noUtilisateur, pseudo); 
				
				articlesVendus = new ArticleVendu(
						
						rst.getInt("no_article"),
						rst.getString("nom_article"),
						rst.getDate("date_fin_encheres").toLocalDate(), 
						rst.getInt("prix_vente"),
						utilisateur								
						);
												
				listeArticlesEnVente.add(articlesVendus);
			}	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ECHEC);
		}finally {
			if(cnx !=null) {
				try {
					cnx.close();
				} catch (SQLException e) {
					e.printStackTrace();
					BusinessException businessException = new BusinessException();
					businessException.ajouterErreur(CodesResultatDAL.DECONNEXION_ECHEC);
				}
			}
			
		}
		return listeArticlesEnVente;

	}
		


	@Override
	public List<ArticleVendu> selectAllArticlesByUtilisateur(Utilisateur utilisateur) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<ArticleVendu> selectAllArticlesByCategorie(Utilisateur utilisateur) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArticleVendu insertArticle(ArticleVendu article) throws BusinessException {
		Connection cnt;
		
		try {
			cnt=ConnectionProvider.getConnection();
			PreparedStatement pstmt = cnt.prepareStatement(INSERT_ARTICLE, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1,article.getNomArticle());
			pstmt.setString(2,article.getDescription());
			pstmt.setDate(3,java.sql.Date.valueOf(article.getDateDebutEncheres()));
			pstmt.setDate(4,java.sql.Date.valueOf(article.getDateFinEncheres()));
			pstmt.setInt(5,article.getPrixInitial());
			pstmt.setInt(6, article.getVendeur().getNoUtilisateur());
			pstmt.setInt(7,article.getCategorie().getId());
			
			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				article.setNoArticle(rs.getInt(1));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return article;
	}

	@Override
	public void updateArticle(ArticleVendu updateArticle) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteArticleById(ArticleVendu article) throws BusinessException {
		// TODO Auto-generated method stub
		
	}
	
	//A faire
	@Override
	public List<ArticleVendu> selectAllArticlesByNoCategorie(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ArticleVendu> selectAllArticlesByNoUtilisateur(int noUtilisateur) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<ArticleVendu> selectAllVentesEnCoursByNoCategorie(int id) throws BusinessException {
		
	    List<ArticleVendu> articlesList = new ArrayList<>();
	    Connection cnx = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;


	    try {
	        cnx = ConnectionProvider.getConnection();
	        pstmt = cnx.prepareStatement(SELECT_ALL_VENTE_EC_BY_CATEGORIE_ID);
	        pstmt.setInt(1, id);

	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	        	ArticleVendu article = new ArticleVendu();
	        	article = BuilderArticleVenduPourListe(rs);
	        	articlesList.add(article);
	        }
	    } catch (SQLException e) {
	    	e.printStackTrace();
    		BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ALL_VENTE_EC_BY_CATEGORIE_ID_ECHEC);
	    } finally {
	    	closeResources(cnx, pstmt, rs);
	    }
	    
	    return articlesList;
	}
	
	@Override
	public List<ArticleVendu> selectAllVentesEnCoursByNomArticle(String nomArticle) throws BusinessException {
		
	    List<ArticleVendu> articlesList = new ArrayList<>();
	    Connection cnx = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        cnx = ConnectionProvider.getConnection();
	        pstmt = cnx.prepareStatement(SELECT_ALL_VENTE_EC_BY_NOM_ART);
	        pstmt.setString(1, "%" + nomArticle.toLowerCase() + "%");

	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	        	ArticleVendu article = new ArticleVendu();
	        	article = BuilderArticleVenduPourListe(rs);
	        	articlesList.add(article);
	        }
	    } catch (SQLException e) {
	    	e.printStackTrace();
    		BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ALL_VENTE_EC_BY_CATEGORIE_ID_ECHEC);
	    } finally {
	    	closeResources(cnx, pstmt, rs);
	    }
	    
	    return articlesList;
	}
	
	@Override
	public List<ArticleVendu> selectAllVentesNonCommenceesByNoUtilisateur(int id) throws BusinessException {
	    List<ArticleVendu> articlesList = new ArrayList<>();
	    Connection cnx = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        cnx = ConnectionProvider.getConnection();
	        pstmt = cnx.prepareStatement(SELECT_ALL_VENTE_NC_BY_UTILISATEUR_ID);
	        pstmt.setInt(1, id);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	        	ArticleVendu article = new ArticleVendu();
	        	article = BuilderArticleVenduPourListe(rs);
	        	articlesList.add(article);
	        }
	    } catch (SQLException e) {
	    	e.printStackTrace();
    		BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ALL_VENTE_NC_BY_UTILISATEUR_ID_ECHEC);
	    } finally {
	    	closeResources(cnx, pstmt, rs);
	    }

	    return articlesList;
	}
	
	@Override
	public List<ArticleVendu> selectAllVentesEnCoursByNoUtilisateur(int id) throws BusinessException {
	    List<ArticleVendu> articlesList = new ArrayList<>();
	    Connection cnx = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        cnx = ConnectionProvider.getConnection();
	        pstmt = cnx.prepareStatement(SELECT_ALL_VENTE_EC_BY_UTILISATEUR_ID);
	        pstmt.setInt(1, id);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	        	ArticleVendu article = new ArticleVendu();
	        	article = BuilderArticleVenduPourListe(rs);
	        	articlesList.add(article);
	        }
	    } catch (SQLException e) {
	    	e.printStackTrace();
    		BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ALL_VENTE_EC_BY_UTILISATEUR_ID_ECHEC);
	    } finally {
	    	closeResources(cnx, pstmt, rs);
	    }

	    return articlesList;
	}
	
	@Override
	public List<ArticleVendu> selectAllVentesTermineesByNoUtilisateur(int id) throws BusinessException {
	    List<ArticleVendu> articlesList = new ArrayList<>();
	    Connection cnx = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        cnx = ConnectionProvider.getConnection();
	        pstmt = cnx.prepareStatement(SELECT_ALL_VENTE_VE_BY_UTILISATEUR_ID);
	        pstmt.setInt(1, id);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	        	ArticleVendu article = new ArticleVendu();
	        	article = BuilderArticleVenduPourListe(rs);
	        	articlesList.add(article);
	        }
	    } catch (SQLException e) {
	    	e.printStackTrace();
    		BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ALL_VENTE_EC_BY_UTILISATEUR_ID_ECHEC);
	    } finally {
	    	closeResources(cnx, pstmt, rs);
	    }

	    return articlesList;
	}

	@Override
	public List<ArticleVendu> selectAllEncheresEnCours() throws BusinessException {
		List<ArticleVendu> articlesList = new ArrayList<>();
	    Connection cnx = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        cnx = ConnectionProvider.getConnection();
	        pstmt = cnx.prepareStatement(SELECT_ALL_VENTE_EC);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	        	ArticleVendu article = new ArticleVendu();
	        	article = BuilderArticleVenduPourListe(rs);
	        	articlesList.add(article);
	        }
	    } catch (SQLException e) {
	    	e.printStackTrace();
    		BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ALL_VENTE_EC_BY_UTILISATEUR_ID_ECHEC);
	    } finally {
	    	closeResources(cnx, pstmt, rs);
	    }

	    return articlesList;
	}

	private ArticleVendu BuilderArticleVenduPourListe (ResultSet rs) throws SQLException, BusinessException {
		
	    ArticleVendu article = new ArticleVendu();

	    article.setNoArticle(rs.getInt("no_article"));
	    article.setNomArticle(rs.getString("nom_article"));
	    article.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
	    article.setPrixVente(rs.getInt("prix_vente"));
	    
	 // Récupérer l'utilisateur (vendeur) à partir de la base de données
	    int noUtilisateur = rs.getInt("no_utilisateur");
	    UtilisateurManager utilisateurManager = new UtilisateurManager();
	    Utilisateur vendeur = utilisateurManager.selectionner(noUtilisateur);
	    //Utilisateur vendeur =UtilisateurDAO.selectById(noUtilisateur);
	    article.setVendeur(vendeur);

	    return article;
	}
	
	// Fermeture des ressources
	public static void closeResources(Connection cnx, PreparedStatement pstmt, ResultSet rs) {
	    
		if (rs != null) {
	        try {
	        	rs.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	            BusinessException businessException = new BusinessException();
	            businessException.ajouterErreur(CodesResultatDAL.DECONNEXION_RESULTSET_ECHEC);
	        }
	    }
	    if (pstmt != null) {
	        try {
	        	pstmt.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	            BusinessException businessException = new BusinessException();
	            businessException.ajouterErreur(CodesResultatDAL.DECONNEXION_PREPAREDSTATEMENT_ECHEC);
	        }
	    }
	    if (cnx != null) {
	        try {
	        	cnx.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	            BusinessException businessException = new BusinessException();
	            businessException.ajouterErreur(CodesResultatDAL.DECONNEXION_ECHEC);
	        }
	    }
	}


	@Override
	public List<ArticleVendu> selectionnerArticlesFiltres(String categorie, String mot, String etatVente) {
		List<ArticleVendu> articles = new ArrayList<>();
		Connection cnx = null;
		
		try {
			cnx=ConnectionProvider.getConnection();
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL_ARTICLES_ETAT_MOT_CATEGORIE);
			pstmt.setString(1, etatVente);
			pstmt.setString(2, "%"+mot+"%");
			if(categorie.equals("Toutes")) {
				pstmt.setNull(3, Types.VARCHAR);
				pstmt.setNull(4, Types.VARCHAR);
			}else {
				pstmt.setString(3, categorie);
				pstmt.setString(4, categorie);
			}
			
			
			ResultSet rs=pstmt.executeQuery();
			
			while(rs.next()) {
				int noUtilisateur = rs.getInt("no_utilisateur");
				String pseudo = rs.getString("pseudo");
				Utilisateur utilisateur = new Utilisateur(noUtilisateur, pseudo);
				ArticleVendu article = new ArticleVendu(rs.getInt("no_article"), rs.getString("nom_article"), rs.getDate("date_fin_encheres").toLocalDate(), rs.getInt("prix_vente"), utilisateur);
				
				articles.add(article);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_BY_ETAT_MOT_CATEGORIE_ECHEC);
		}finally {
			if(cnx !=null) {
				try {
					cnx.close();
				} catch (SQLException e) {
					e.printStackTrace();
					BusinessException businessException = new BusinessException();
					businessException.ajouterErreur(CodesResultatDAL.DECONNEXION_ECHEC);
				}
			}
			
		}
		
		return articles;
		
	}


}
