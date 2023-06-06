package fr.eni.enienchere.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.eni.enienchere.BusinessException;
import fr.eni.enienchere.bo.ArticleVendu;
import fr.eni.enienchere.bo.Categorie;
import fr.eni.enienchere.bo.Enchere;
import fr.eni.enienchere.bo.Retrait;
import fr.eni.enienchere.bo.Utilisateur;
import fr.eni.enienchere.servlet.ServletPageVendreUnArticle;

public class ArticleDAOJdbcImpl implements ArticleDAO {
	
	private static final String INSERT_ARTICLE="INSERT INTO ARTICLES_VENDUS(nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,no_utilisateur,no_categorie) VALUES(?,?,?,?,?,?,?)";
	
	private static final String SELECT_ALL_ARTICLE = "SELECT no_article, nom_article, date_fin_encheres, prix_vente, pseudo\n"
														+ "FROM ARTICLES_VENDUS\n"
														+ "INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur\n"
														+ "WHERE etat_vente = 'EC';"; 
	
	@Override
	public ArticleVendu selectArticleById(int articleId) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
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
				
				String pseudo = rst.getString("pseudo"); 

				Utilisateur utilisateur = new Utilisateur(pseudo); 
				
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

}
