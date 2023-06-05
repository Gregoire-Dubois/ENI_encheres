package fr.eni.enienchere.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
	private static final String SELECT_ARTICLE_BY_ID = "SELECT * FROM ("
														+ "AV.nomArticle, AV.description, C.libelle AS categorie, E.montant_enchere, UE.pseudo AS pseudo_encherisseur, AV.prix_initial, AV.date_fin_encheres,"
														+ "R.rue AS rue_retrait, R.code_postal AS code_postal_retrait, R.ville AS ville_retrait, UV.pseudo AS pseudo_vendeur, "
														+ "ROW_NUMBER() OVER (ORDER BY E.montant_enchere DESC) AS row_number"
														+ "FROM ARTICLES_VENDUS AV"
														+ "INNER JOIN CATEGORIES C ON AV.no_categorie = C.no_categorie"
														+ "LEFT JOIN ENCHERES E ON AV.no_article = E.no_article"
														+ "LEFT JOIN UTILISATEURS UE ON E.no_utilisateur = UE.no_utilisateur"
														+ "LEFT JOIN UTILISATEURS UV ON AV.no_utilisateur = UV.no_utilisateur"
														+ "LEFT JOIN RETRAITS R ON AV.no_article = R.no_article"
														+ "WHERE AV.no_article = ?)"
														+ "AS subquery WHERE row_number = 1;";


	
	private static final String SELECT_ALL_ARTICLE = "SELECT * FROM articles_vendus;";
	

	@Override
	public ArticleVendu selectArticleById(int articleId) throws BusinessException {
		ArticleVendu article = null;
		Connection cnx = null;
		
		try {
			cnx = ConnectionProvider.getConnection();
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ARTICLE_BY_ID);
			
			pstmt.setInt(1, articleId);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {	
				article = new ArticleVendu();
				article.setNoArticle(rs.getInt(articleId));
				article.setNomArticle(rs.getString("nom_article"));
				article.setDescription(rs.getString("description"));
            
				// Récupération du libellé de la catégorie article
				String libelleCategorie = rs.getString("categorie");
				Categorie categorie = new Categorie(libelleCategorie);
				article.setCategorie(categorie);
            
				article.setPrixVente(rs.getInt("montant_enchere"));
            
				// Récupération du pseudo de l'encherisseur
				String pseudoEncherisseur = rs.getString("pseudo_encherisseur");
				Utilisateur encherisseur = UtilisateurDAO.selectByPseudo(rs.getString("pseudo_encherisseur"));
				encherisseur.setPseudo(pseudoEncherisseur);
				encherisseur.setVendeur(encherisseur);
            ;
            article.setPseudoEncherisseur(rs.getString("pseudo_encherisseur"));
            article.setPrixInitial(rs.getInt("prix_initial"));
            article.setDateFinEncheres(rs.getDate("date_fin_encheres"));
            article.setRueRetrait(rs.getString("rue_retrait"));
            article.setCodePostalRetrait(rs.getString("code_postal_retrait"));
            article.setVilleRetrait(rs.getString("ville_retrait"));
            article.setPseudoVendeur(rs.getString("pseudo_vendeur"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_BY_ID_ECHEC);
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
		
		
		return utilisateur;
	}

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
				articlesVendus = new ArticleVendu(
						rst.getInt(1), //noArticle
						rst.getString(2), //nom article
						rst.getString(3), // description
						rst.getDate(4).toLocalDate(), // datedebut
						rst.getDate(5).toLocalDate(), //datefin
						rst.getInt(6), //prix
						rst.getInt(7), // prix 
						rst.getString(10) // état vente 
						//(Categorie)rst.getObject(9)

						);
												
				listeArticlesEnVente.add(articlesVendus);
			}
			System.out.println("-------->"+listeArticlesEnVente);

			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

}
