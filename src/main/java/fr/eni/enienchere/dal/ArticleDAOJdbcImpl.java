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
	
private static final String SELECT_ARTICLE_BY_ID = "SELECT\r\n"
		+ "a.no_article, \r\n"
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
	
	private static final String SELECT_ALL_VENTE_EC = "SELECT"
			+ "a.no_article, nom_article, date_fin_encheres, prix_vente, a.no_utilisateur, u.pseudo"
			+ "FROM articles_vendus a"
			+ "LEFT JOIN utilisateurs u ON a.no_utilisateur = u.no_utilisateur"
			+ "WHERE etat_vente = 'EC';";
	
	private static final String SELECT_ALL_VENTE_EC_BY_CATEGORIE_ID = "SELECT"
			+ "article.no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente,"
			+ "vendeur.no_utilisateur as no_vendeur, vendeur.pseudo as pseudo_vendeur, encherisseur.no_utilisateur as no_encherisseur,"
			+ "encherisseur.pseudo as pseudo_encherisseur, article.no_categorie, categorie.libelle as nom_categorie, retrait.rue,"
			+ "retrait.code_postal, retrait.ville"
			+ "FROM ARTICLES_VENDUS article"
			+ "LEFT JOIN ENCHERES enchere ON article.no_article = enchere.no_article AND"
			+ "enchere.no_enchere = (SELECT max(enchere.no_enchere) FROM ENCHERES enchere WHERE article.no_article = enchere.no_article)"
			+ "LEFT JOIN CATEGORIES categorie ON article.no_categorie = categorie.no_categorie"
			+ "LEFT JOIN UTILISATEURS vendeur ON article.no_utilisateur = vendeur.no_utilisateur"
			+ "LEFT JOIN UTILISATEURS encherisseur ON article.no_utilisateur = encherisseur.no_utilisateur"
			+ "LEFT JOIN RETRAITS retrait ON article.no_article = retrait.no_article"
			+ "WHERE article.etat_vente = 'EC' AND article.no_categorie = ?;";
	
	private static final String SELECT_ALL_VENTE_EC_BY_NOM_ART = "SELECT"
			+ "a.no_article, nom_article, date_fin_encheres, prix_vente, prix_initial, a.no_utilisateur, u.pseudo"
			+ "FROM articles_vendus a"
			+ "LEFT JOIN utilisateurs u ON a.no_utilisateur = u.no_utilisateur"
			+ "WHERE a.etat_vente = 'EC' AND lower(a.nom_article) LIKE '%?%';";

	private static final String SELECT_ALL_VENTE_NC_BY_UTILISATEUR_ID = "SELECT"
			+ "a.no_article, nom_article, date_fin_encheres, prix_vente, prix_initial, a.no_utilisateur, u.pseudo"
			+ "FROM articles_vendus a"
			+ "LEFT JOIN utilisateurs u ON a.no_utilisateur = u.no_utilisateur"
			+ "WHERE a.etat_vente = 'NC' AND u.no_utilisateur = ?;";
	
	private static final String SELECT_ALL_VENTE_EC_BY_UTILISATEUR_ID = "SELECT"
			+ "a.no_article, nom_article, date_fin_encheres, prix_vente, prix_initial, a.no_utilisateur, u.pseudo"
			+ "FROM articles_vendus a"
			+ "LEFT JOIN utilisateurs u ON a.no_utilisateur = u.no_utilisateur"
			+ "WHERE a.etat_vente = 'EC' AND u.no_utilisateur = ?;";
	
	private static final String SELECT_ALL_VENTE_VE_BY_UTILISATEUR_ID = "SELECT"
			+ "a.no_article, nom_article, date_fin_encheres, prix_vente, prix_initial, a.no_utilisateur, u.pseudo"
			+ "FROM articles_vendus a"
			+ "LEFT JOIN utilisateurs u ON a.no_utilisateur = u.no_utilisateur"
			+ "WHERE a.etat_vente = 'VE' AND u.no_utilisateur = ?;";
	
	private static final String SELECT_ALL_ENCHERES_EC_BY_UTILISATEUR_ID = "SELECT"
			+ "a.no_article, nom_article, date_fin_encheres, montant_enchere, e.no_utilisateur, u.pseudo"
			+ "FROM ARTICLES_VENDUS a"
			+ "LEFT JOIN ENCHERES e ON a.no_article = e.no_article"
			+ "LEFT JOIN UTILISATEURS u ON e.no_utilisateur = u.no_utilisateur"
			+ "WHERE a.etat_vente = 'EC'"
			+ "AND e.montant_enchere = ("
			    + "SELECT MAX(montant_enchere)"
			    + "FROM ENCHERES"
			    + "WHERE no_article = a.no_article)"
		    + "AND e.no_utilisateur = ?";
	
	private static final String SELECT_ALL_ENCHERES_VE_BY_UTILISATEUR_ID = "SELECT"
			+ "a.no_article, nom_article, date_fin_encheres, montant_enchere, e.no_utilisateur, u.pseudo"
			+ "FROM ARTICLES_VENDUS a"
			+ "LEFT JOIN ENCHERES e ON a.no_article = e.no_article"
			+ "LEFT JOIN UTILISATEURS u ON e.no_utilisateur = u.no_utilisateur"
			+ "WHERE a.etat_vente = 'VE'"
			+ "AND e.montant_enchere = ("
			    + "SELECT MAX(montant_enchere)"
			    + "FROM ENCHERES"
			    + "WHERE no_article = a.no_article)"
		    + "AND e.no_utilisateur = ?";
	
	private static final String SELECT_ALL_ARTICLES_ETAT_MOT_CATEGORIE = "SELECT no_article, nom_article, prix_vente, date_fin_encheres, a.no_utilisateur, pseudo \r\n"
			+ "	FROM ARTICLES_VENDUS AS a INNER JOIN UTILISATEURS as u on a.no_utilisateur=u.no_utilisateur \r\n"
			+ "							  INNER JOIN CATEGORIES as c on c.no_categorie=a.no_categorie\r\n"
			+ "			where etat_vente=? and LOWER(nom_article) like ? and ((c.libelle = ?) or (? IS NULL))";
	
	private static final String SELECT_EC_ARTICLES_SANS_UTILISATEUR = "SELECT no_article, nom_article, prix_vente, date_fin_encheres, a.no_utilisateur, pseudo \r\n"
			+ "			FROM ARTICLES_VENDUS AS a INNER JOIN UTILISATEURS as u on a.no_utilisateur=u.no_utilisateur \r\n"
			+ "									  INNER JOIN CATEGORIES as c on c.no_categorie=a.no_categorie\r\n"
			+ "						where etat_vente='EC' and a.no_utilisateur!=?;";
	
	private static final String SELECT_MES_ENCHERES="SELECT e.no_article, nom_article, prix_vente, date_fin_encheres, a.no_utilisateur, pseudo \r\n"
			+ "						FROM ENCHERES as e INNER JOIN UTILISATEURS as u on e.no_utilisateur=u.no_utilisateur \r\n"
			+ "												  INNER JOIN ARTICLES_VENDUS as a on a.no_article=e.no_article\r\n"
			+ "												  \r\n"
			+ "									where etat_vente='EC' and e.no_utilisateur=?;";
	
	private static final String SELECT_ENCHERES_REMPORTEES="SELECT e.no_article, nom_article, prix_vente, date_fin_encheres, a.no_utilisateur, pseudo \r\n"
			+ "						FROM ENCHERES as e INNER JOIN UTILISATEURS as u on e.no_utilisateur=u.no_utilisateur \r\n"
			+ "												  INNER JOIN ARTICLES_VENDUS as a on a.no_article=e.no_article and e.montant_enchere = a.prix_vente\r\n"
			+ "												  \r\n"
			+ "									where etat_vente='VE' and e.no_utilisateur=?;";
	private static final String SELECT_EC_ARTICLES_SANS_UTILISATEUR_PLUS_ENCHERES_REMPORTEES="SELECT a.no_article, nom_article, prix_vente, date_fin_encheres, a.no_utilisateur, pseudo \r\n"
			+ "						FROM ARTICLES_VENDUS AS a INNER JOIN UTILISATEURS as u on a.no_utilisateur=u.no_utilisateur \r\n"
			+ "												  INNER JOIN CATEGORIES as c on c.no_categorie=a.no_categorie\r\n"
			+ "									where etat_vente='EC' and a.no_utilisateur!=?\r\n"
			+ "UNION\r\n"
			+ "SELECT e.no_article, nom_article, prix_vente, date_fin_encheres, a.no_utilisateur, pseudo \r\n"
			+ "									FROM ENCHERES as e INNER JOIN UTILISATEURS as u on e.no_utilisateur=u.no_utilisateur \r\n"
			+ "															  INNER JOIN ARTICLES_VENDUS as a on a.no_article=e.no_article and e.montant_enchere = a.prix_vente\r\n"
			+ "\r\n"
			+ "												where etat_vente='VE' and e.no_utilisateur=?;";
	
	private static final String SELECT_MES_ENCHERES_PLUS_ENCHERES_REMPORTEES="SELECT e.no_article, nom_article, prix_vente, date_fin_encheres, a.no_utilisateur, pseudo \r\n"
			+ "									FROM ENCHERES as e INNER JOIN UTILISATEURS as u on e.no_utilisateur=u.no_utilisateur \r\n"
			+ "															  INNER JOIN ARTICLES_VENDUS as a on a.no_article=e.no_article and e.montant_enchere = a.prix_vente\r\n"
			+ "\r\n"
			+ "												where etat_vente='VE' and e.no_utilisateur=?\r\n"
			+ "UNION\r\n"
			+ "SELECT e.no_article, nom_article, prix_vente, date_fin_encheres, a.no_utilisateur, pseudo \r\n"
			+ "									FROM ENCHERES as e INNER JOIN UTILISATEURS as u on e.no_utilisateur=u.no_utilisateur \r\n"
			+ "															  INNER JOIN ARTICLES_VENDUS as a on a.no_article=e.no_article\r\n"
			+ "															\r\n"
			+ "												where etat_vente='EC' and e.no_utilisateur=?;";
	
	private static final String SELECT_MES_VENTES_EN_COURS="SELECT no_article, nom_article, prix_vente, date_fin_encheres, a.no_utilisateur, u.pseudo \r\n"
			+ "	FROM ARTICLES_VENDUS AS a INNER JOIN UTILISATEURS AS u ON u.no_utilisateur=a.no_utilisateur\r\n"
			+ "	WHERE etat_vente='EC' and a.no_utilisateur=?;";
	
	private static final String SELECT_MES_VENTES_NON_DEBUTEES="SELECT no_article, nom_article, prix_vente, date_fin_encheres, a.no_utilisateur, u.pseudo \r\n"
			+ "	FROM ARTICLES_VENDUS AS a INNER JOIN UTILISATEURS AS u ON u.no_utilisateur=a.no_utilisateur\r\n"
			+ "	WHERE etat_vente='NC' and a.no_utilisateur=?;";
	
	private static final String SELECT_MES_VENTES_TERMINEES="SELECT no_article, nom_article, prix_vente, date_fin_encheres, a.no_utilisateur, u.pseudo \r\n"
			+ "	FROM ARTICLES_VENDUS AS a INNER JOIN UTILISATEURS AS u ON u.no_utilisateur=a.no_utilisateur\r\n"
			+ "	WHERE etat_vente='VE' and a.no_utilisateur=?;";
	
	private static final String SELECT_MES_VENTES_EN_COURS_PLUS_NON_DEBUTEES="SELECT no_article, nom_article, prix_vente, date_fin_encheres, a.no_utilisateur, u.pseudo \r\n"
			+ "	FROM ARTICLES_VENDUS AS a INNER JOIN UTILISATEURS AS u ON u.no_utilisateur=a.no_utilisateur\r\n"
			+ "	WHERE etat_vente='EC' and a.no_utilisateur=?\r\n"
			+ "UNION\r\n"
			+ "SELECT no_article, nom_article, prix_vente, date_fin_encheres, a.no_utilisateur, u.pseudo \r\n"
			+ "	FROM ARTICLES_VENDUS AS a INNER JOIN UTILISATEURS AS u ON u.no_utilisateur=a.no_utilisateur\r\n"
			+ "	WHERE etat_vente='NC' and a.no_utilisateur=?;";
			
	private static final String SELECT_MES_VENTES_EN_COURS_PLUS_TERMINEES="SELECT no_article, nom_article, prix_vente, date_fin_encheres, a.no_utilisateur, u.pseudo \r\n"
			+ "	FROM ARTICLES_VENDUS AS a INNER JOIN UTILISATEURS AS u ON u.no_utilisateur=a.no_utilisateur\r\n"
			+ "	WHERE etat_vente='EC' and a.no_utilisateur=?\r\n"
			+ "UNION\r\n"
			+ "SELECT no_article, nom_article, prix_vente, date_fin_encheres, a.no_utilisateur, u.pseudo \r\n"
			+ "	FROM ARTICLES_VENDUS AS a INNER JOIN UTILISATEURS AS u ON u.no_utilisateur=a.no_utilisateur\r\n"
			+ "	WHERE etat_vente='VE' and a.no_utilisateur=.;";
	private static final String SELECT_MES_VENTES_NON_DEBUTEES_PLUS_TERMINEES="SELECT no_article, nom_article, prix_vente, date_fin_encheres, a.no_utilisateur, u.pseudo \r\n"
			+ "	FROM ARTICLES_VENDUS AS a INNER JOIN UTILISATEURS AS u ON u.no_utilisateur=a.no_utilisateur\r\n"
			+ "	WHERE etat_vente='NC' and a.no_utilisateur=?\r\n"
			+ "UNION\r\n"
			+ "SELECT no_article, nom_article, prix_vente, date_fin_encheres, a.no_utilisateur, u.pseudo \r\n"
			+ "	FROM ARTICLES_VENDUS AS a INNER JOIN UTILISATEURS AS u ON u.no_utilisateur=a.no_utilisateur\r\n"
			+ "	WHERE etat_vente='VE' and a.no_utilisateur=?;";
	private static final String SELECT_MES_VENTES_EN_COURS_PLUS_NON_DEBUTEES_PLUS_TERMINEES="SELECT no_article, nom_article, prix_vente, date_fin_encheres, a.no_utilisateur, u.pseudo \r\n"
			+ "	FROM ARTICLES_VENDUS AS a INNER JOIN UTILISATEURS AS u ON u.no_utilisateur=a.no_utilisateur\r\n"
			+ "	WHERE a.no_utilisateur=?;";
	
	
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
    		

	        while (rs.next()) {
	        	int noArticle = rs.getInt("no_article");
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
                article = new ArticleVendu(noArticle, description, categorieArt, prixVente, acquereur,
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
	public List<ArticleVendu> selectAllVentesNonCommenceesByNoUtilisateur(int no_utilisateur) throws BusinessException {
		
	    List<ArticleVendu> articlesList = new ArrayList<>();
	    Connection cnx = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        cnx = ConnectionProvider.getConnection();
	        pstmt = cnx.prepareStatement(SELECT_ALL_VENTE_NC_BY_UTILISATEUR_ID);
	        pstmt.setInt(1, no_utilisateur);
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
	public List<ArticleVendu> selectAllVentesEnCoursByNoUtilisateur(int no_utilisateur) throws BusinessException {
		
	    List<ArticleVendu> articlesList = new ArrayList<>();
	    Connection cnx = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        cnx = ConnectionProvider.getConnection();
	        pstmt = cnx.prepareStatement(SELECT_ALL_VENTE_EC_BY_UTILISATEUR_ID);
	        pstmt.setInt(1, no_utilisateur);
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
	public List<ArticleVendu> selectAllVentesTermineesByNoUtilisateur(int no_utilisateur) throws BusinessException {
		
	    List<ArticleVendu> articlesList = new ArrayList<>();
	    Connection cnx = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        cnx = ConnectionProvider.getConnection();
	        pstmt = cnx.prepareStatement(SELECT_ALL_VENTE_VE_BY_UTILISATEUR_ID);
	        pstmt.setInt(1, no_utilisateur);
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

	@Override
	public List<ArticleVendu> selectAllEncheresEnCoursByNoUtilisateur(int no_utilisateur) throws BusinessException {
		
		List<ArticleVendu> articlesList = new ArrayList<>();
	    Connection cnx = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        cnx = ConnectionProvider.getConnection();
	        pstmt = cnx.prepareStatement(SELECT_ALL_ENCHERES_EC_BY_UTILISATEUR_ID);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	        	ArticleVendu article = new ArticleVendu();
	    	    
	    	    article.setNoArticle(rs.getInt("a.no_article"));
	    	    article.setNomArticle(rs.getString("nom_article"));
	    	    article.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
	    	    article.setPrixVente(rs.getInt("montant_enchere"));
	    	    
	    	    Utilisateur vendeur = article.getVendeur();
	    	    vendeur.setNoUtilisateur(rs.getInt("a.no_utilisateur"));
	    	    vendeur.setPseudo(rs.getString("u.pseudo"));
	    	    
	    	    article.setVendeur(vendeur);

	        	articlesList.add(article);
	        }
	    } catch (SQLException e) {
	    	e.printStackTrace();
    		BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ALL_ENCHERES_EC_BY_UTILISATEUR_ID_ECHEC);
	    } finally {
	    	closeResources(cnx, pstmt, rs);
	    }

	    return articlesList;
	}

	@Override
	public List<ArticleVendu> selectAllEncheresGagneesByNoUtilisateur(int no_utilisateur) throws BusinessException {
		
		List<ArticleVendu> articlesList = new ArrayList<>();
	    Connection cnx = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        cnx = ConnectionProvider.getConnection();
	        pstmt = cnx.prepareStatement(SELECT_ALL_ENCHERES_VE_BY_UTILISATEUR_ID);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	        	ArticleVendu article = new ArticleVendu();
	    	    
	    	    article.setNoArticle(rs.getInt("a.no_article"));
	    	    article.setNomArticle(rs.getString("nom_article"));
	    	    article.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
	    	    article.setPrixVente(rs.getInt("montant_enchere"));
	    	    
	    	    Utilisateur vendeur = article.getVendeur();
	    	    vendeur.setNoUtilisateur(rs.getInt("a.no_utilisateur"));
	    	    vendeur.setPseudo(rs.getString("u.pseudo"));
	    	    
	    	    article.setVendeur(vendeur);

	        	articlesList.add(article);
	        }
	    } catch (SQLException e) {
	    	e.printStackTrace();
    		BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ALL_ENCHERES_VE_BY_UTILISATEUR_ID_ECHEC);
	    } finally {
	    	closeResources(cnx, pstmt, rs);
	    }

	    return articlesList;
	}
	
	private ArticleVendu BuilderArticleVenduPourListe (ResultSet rs) throws SQLException, BusinessException {
		
	    ArticleVendu article = new ArticleVendu();
	    
	    article.setNoArticle(rs.getInt("a.no_article"));
	    article.setNomArticle(rs.getString("nom_article"));
	    article.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
	    
	    // S'il n'y a pas eu d'enchère (prix_vente = null, alors on mettra le prix initial
	    int prixVente = rs.getInt("prix_vente");
	    if (rs.wasNull()) {
	        int prixInitial = rs.getInt("prix_initial");
	        article.setPrixVente(prixInitial);
	    } else {
	        article.setPrixVente(prixVente);
	    }
	    
	    Utilisateur vendeur = article.getVendeur();
	    vendeur.setNoUtilisateur(rs.getInt("a.no_utilisateur"));
	    vendeur.setPseudo(rs.getString("u.pseudo"));
	    
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
	
	@Override
	public List<ArticleVendu> selectArticlesECSansUtilisateur(int idUtilisateur) throws BusinessException {
		List<ArticleVendu> articles = new ArrayList<>();
		Connection cnx = null;
		PreparedStatement pstmt = null;
	 

	    try {
    		cnx = ConnectionProvider.getConnection();
    		pstmt = cnx.prepareStatement(SELECT_EC_ARTICLES_SANS_UTILISATEUR);

    		pstmt.setInt(1, idUtilisateur);
    		ResultSet rs = pstmt.executeQuery();

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
			businessException.ajouterErreur(CodesResultatDAL.SELECT_EC_ARTICLES_SANS_UTILISATEUR_ECHEC);
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

	@Override
	public List<ArticleVendu> selectMesEncheresEnCours(int idUtilisateur) throws BusinessException {
		List<ArticleVendu> articles = new ArrayList<>();
		Connection cnx = null;
		PreparedStatement pstmt = null;
	 

	    try {
    		cnx = ConnectionProvider.getConnection();
    		pstmt = cnx.prepareStatement(SELECT_MES_ENCHERES);

    		pstmt.setInt(1, idUtilisateur);
    		ResultSet rs = pstmt.executeQuery();

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
			businessException.ajouterErreur(CodesResultatDAL.SELECT_MES_ENCHERES_ECHEC);
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

	@Override
	public List<ArticleVendu> selectEncheresRemportees(int idUtilisateur) throws BusinessException {
		List<ArticleVendu> articles = new ArrayList<>();
		Connection cnx = null;
		PreparedStatement pstmt = null;
	 

	    try {
    		cnx = ConnectionProvider.getConnection();
    		pstmt = cnx.prepareStatement(SELECT_ENCHERES_REMPORTEES);

    		pstmt.setInt(1, idUtilisateur);
    		ResultSet rs = pstmt.executeQuery();

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
			businessException.ajouterErreur(CodesResultatDAL.SELECT_MES_ENCHERES_REMPORTEES_ECHEC);
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

	@Override
	public List<ArticleVendu> selectArticlesECSansUtilisateurPlusEncheresRemportees(int idUtilisateur) {
		List<ArticleVendu> articles = new ArrayList<>();
		Connection cnx = null;
		PreparedStatement pstmt = null;
	 

	    try {
    		cnx = ConnectionProvider.getConnection();
    		pstmt = cnx.prepareStatement(SELECT_EC_ARTICLES_SANS_UTILISATEUR_PLUS_ENCHERES_REMPORTEES);

    		pstmt.setInt(1, idUtilisateur);
    		pstmt.setInt(2, idUtilisateur);
    		ResultSet rs = pstmt.executeQuery();

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
			businessException.ajouterErreur(CodesResultatDAL.SELECT_EC_ARTICLES_SANS_UTILISATEUR_PLUS_ENCHERES_REMPORTEES_ECHEC);
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

	@Override
	public List<ArticleVendu> selectArticlesEncheresRemporteesPlusMesEncheresEnCours(int idUtilisateur) {
		List<ArticleVendu> articles = new ArrayList<>();
		Connection cnx = null;
		PreparedStatement pstmt = null;
	 

	    try {
    		cnx = ConnectionProvider.getConnection();
    		pstmt = cnx.prepareStatement(SELECT_MES_ENCHERES_PLUS_ENCHERES_REMPORTEES);

    		pstmt.setInt(1, idUtilisateur);
    		pstmt.setInt(2, idUtilisateur);
    		ResultSet rs = pstmt.executeQuery();

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
			businessException.ajouterErreur(CodesResultatDAL.SELECT_MES_ENCHERES_PLUS_ENCHERES_REMPORTEES_ECHEC);
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

	@Override
	public List<ArticleVendu> selectMesVentesEnCours(int idUtilisateur) {
		List<ArticleVendu> articles = new ArrayList<>();
		Connection cnx = null;
		PreparedStatement pstmt = null;
	 

	    try {
    		cnx = ConnectionProvider.getConnection();
    		pstmt = cnx.prepareStatement(SELECT_MES_VENTES_EN_COURS);

    		pstmt.setInt(1, idUtilisateur);
    		ResultSet rs = pstmt.executeQuery();

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
			businessException.ajouterErreur(CodesResultatDAL.SELECT_MES_VENTES_EN_COURS_ECHEC);
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

	@Override
	public List<ArticleVendu> selectMesVentesNonDebutees(int idUtilisateur) {
		List<ArticleVendu> articles = new ArrayList<>();
		Connection cnx = null;
		PreparedStatement pstmt = null;
	 

	    try {
    		cnx = ConnectionProvider.getConnection();
    		pstmt = cnx.prepareStatement(SELECT_MES_VENTES_NON_DEBUTEES);

    		pstmt.setInt(1, idUtilisateur);
    		ResultSet rs = pstmt.executeQuery();

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
			businessException.ajouterErreur(CodesResultatDAL.SELECT_MES_VENTES_NON_DEBUTEES_ECHEC);
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

	@Override
	public List<ArticleVendu> selectMesVentesTerminees(int idUtilisateur) {
		List<ArticleVendu> articles = new ArrayList<>();
		Connection cnx = null;
		PreparedStatement pstmt = null;
	 

	    try {
    		cnx = ConnectionProvider.getConnection();
    		pstmt = cnx.prepareStatement(SELECT_MES_VENTES_TERMINEES);

    		pstmt.setInt(1, idUtilisateur);
    		ResultSet rs = pstmt.executeQuery();

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
			businessException.ajouterErreur(CodesResultatDAL.SELECT_MES_VENTES_TERMINEES_ECHEC);
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

	@Override
	public List<ArticleVendu> selectMesVentesEnCoursPlusVentesNonDebutees(int idUtilisateur) {
		List<ArticleVendu> articles = new ArrayList<>();
		Connection cnx = null;
		PreparedStatement pstmt = null;
	 

	    try {
    		cnx = ConnectionProvider.getConnection();
    		pstmt = cnx.prepareStatement(SELECT_MES_VENTES_EN_COURS_PLUS_NON_DEBUTEES);

    		pstmt.setInt(1, idUtilisateur);
    		pstmt.setInt(2, idUtilisateur);
    		ResultSet rs = pstmt.executeQuery();

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
			businessException.ajouterErreur(CodesResultatDAL.SELECT_MES_VENTES_EN_COURS_PLUS_NON_DEBUTEES);
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

	@Override
	public List<ArticleVendu> selectMesVentesEnCoursPlusVentesTerminees(int idUtilisateur) {
		List<ArticleVendu> articles = new ArrayList<>();
		Connection cnx = null;
		PreparedStatement pstmt = null;
	 

	    try {
    		cnx = ConnectionProvider.getConnection();
    		pstmt = cnx.prepareStatement(SELECT_MES_VENTES_EN_COURS_PLUS_TERMINEES);

    		pstmt.setInt(1, idUtilisateur);
    		pstmt.setInt(2, idUtilisateur);
    		ResultSet rs = pstmt.executeQuery();

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
			businessException.ajouterErreur(CodesResultatDAL.SELECT_MES_VENTES_EN_COURS_PLUS_TERMINEES_ECHEC);
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

	@Override
	public List<ArticleVendu> selectVentesNonDebuteesVentesTerminees(int idUtilisateur) {
		List<ArticleVendu> articles = new ArrayList<>();
		Connection cnx = null;
		PreparedStatement pstmt = null;
	 

	    try {
    		cnx = ConnectionProvider.getConnection();
    		pstmt = cnx.prepareStatement(SELECT_MES_VENTES_NON_DEBUTEES_PLUS_TERMINEES);

    		pstmt.setInt(1, idUtilisateur);
    		pstmt.setInt(2, idUtilisateur);
    		ResultSet rs = pstmt.executeQuery();

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
			businessException.ajouterErreur(CodesResultatDAL.SELECT_MES_VENTES_NON_DEBUTEES_PLUS_TERMINEES_ECHEC);
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

	@Override
	public List<ArticleVendu> selectMesVentesEnCoursNonDebuteesEtTerminees(int idUtilisateur) {
		List<ArticleVendu> articles = new ArrayList<>();
		Connection cnx = null;
		PreparedStatement pstmt = null;
	 

	    try {
    		cnx = ConnectionProvider.getConnection();
    		pstmt = cnx.prepareStatement(SELECT_MES_VENTES_EN_COURS_PLUS_NON_DEBUTEES_PLUS_TERMINEES);

    		pstmt.setInt(1, idUtilisateur);
    		pstmt.setInt(2, idUtilisateur);
    		ResultSet rs = pstmt.executeQuery();

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
			businessException.ajouterErreur(CodesResultatDAL.SELECT_MES_VENTES_ECHEC);
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
