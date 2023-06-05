package fr.eni.enienchere.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import fr.eni.enienchere.bo.Utilisateur;
import fr.eni.enienchere.BusinessException;
import fr.eni.enienchere.bo.ArticleVendu;
import fr.eni.enienchere.bo.Categorie;
import fr.eni.enienchere.bo.Enchere;

public class EnchereDAOJdbcImpl implements EnchereDAO {
	
	private static final String SELECT_BY_NO_ARTICLE = "SELECT no_enchere ,\r\n"
			+ "		  e.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.prix_vente, a.etat_vente,\r\n"
			+ "		  e.no_utilisateur, u.pseudo, u.nom, u.prenom, u.email, u.telephone, u.rue, u.code_postal, u.ville, u.mot_de_passe, u.credit, u.administrateur,\r\n"
			+ "		  no_categorie,\r\n"
			+ "		  date_enchere, montant_enchere\r\n"
			+ "			  FROM ENCHERES AS e INNER JOIN UTILISATEURS AS u ON e.no_utilisateur=u.no_utilisateur\r\n"
			+ "								 INNER JOIN ARTICLES_VENDUS as a on e.no_article = a.no_article\r\n"
			+ "					where e.no_article=?;";
	
	@Override
	public List<Enchere> selectAllEnchere() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Enchere> selectAllEnchereByNoArticle(int noArticle) throws BusinessException {
		List<Enchere> listeEncheres = new ArrayList<>();
		Connection cnx = null;
		
		try {
			cnx = ConnectionProvider.getConnection();
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_NO_ARTICLE);
			pstmt.setInt(1, noArticle);
			ResultSet rs= pstmt.executeQuery();
			while(rs.next()) {
				int noEnchere= rs.getInt("no_enchere");
				Categorie categorie = new Categorie();
				categorie.setId(rs.getInt("no_categorie"));
				ArticleVendu article = new ArticleVendu(noArticle, rs.getString("nom_article"), rs.getString("description"), (rs.getDate("date_debut_encheres").toLocalDate()), (rs.getDate("date_fin_encheres").toLocalDate()), rs.getInt("prix_initial"), rs.getInt("prix_vente"), rs.getString("etat_vente"), categorie);
				Utilisateur utilisateur = new Utilisateur(rs.getInt("no_utilisateur"), rs.getString("pseudo"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email"), rs.getString("telephone"), rs.getString("rue"), rs.getString("code_postal"), rs.getString("ville"), rs.getString("mot_de_passe"), rs.getInt("credit"), rs.getBoolean("administrateur"));
				LocalDateTime dateEnchere = rs.getTimestamp("date_enchere").toLocalDateTime();
				int montantEnchere = rs.getInt("montant_enchere");
				
				Enchere enchere = new Enchere(noEnchere, article, utilisateur, dateEnchere, montantEnchere);
				
				listeEncheres.add(enchere);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_BY_NO_ARTICLE_ECHEC);
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
		
		
		return listeEncheres;
		
	}
	
	//A faire
	@Override
	public List<Enchere> selectAllEnchereByNoUtilisateur(int noUtilisateur) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//Builder de l'enchere - Mise en commentaire pour les tests, je ne sais pas à quoi ça va servir
//    private Enchere enchereBuilder(ResultSet rs) throws BusinessException {
//        Enchere enchere = new Enchere();
//        enchere.setId(rs.getInt("id_enchere"));
//        ArticleVendu article = this.getEnchereArticle(rs.getInt("id_art"));
//        enchere.setArticle(article);
//        Utilisateur utilisateur = this.getEnchereUtilisateur(rs.getInt("ench_idUtilisateur"));
//        enchere.setUtilisateur(utilisateur);
//        enchere.setDateEnchere((Timestamp) rs.getObject("dateEnchere")); // ------> apres
//        enchere.setMontantEnchere(rs.getInt("montantEnchere"));
//      
//        return enchere;
//    }
//
//	@Override
//	public List<Enchere> selectAllEnchere() throws BusinessException {
//		// TODO Auto-generated method stub
//		List<Enchere> listEnchere = new ArrayList<>();
//		final String SELECT_ALL_ENCHERE = "SELECT  * FROM ENCHERES" //A corriger évidemment
//	}

}
