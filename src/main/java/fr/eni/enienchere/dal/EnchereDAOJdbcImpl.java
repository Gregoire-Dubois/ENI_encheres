package fr.eni.enienchere.dal;

import java.net.URISyntaxException;
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
import fr.eni.enienchere.bll.ArticleManager;
import fr.eni.enienchere.bll.UtilisateurManager;
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
	
	private static final String INSERT_NEW_ENCHERE = "INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere) values (?, ?, ?, ?);";
	
	private static final String SELECT_ENCHERE_MAX_BY_ARTICLE = "	SELECT \r\n"
			+ "	e.no_enchere, \r\n"
			+ "	e.no_article, \r\n"
			+ "	a.nom_article, \r\n"
			+ "	a.description, \r\n"
			+ "	a.date_debut_encheres, \r\n"
			+ "	a.date_fin_encheres, \r\n"
			+ "	a.prix_initial, \r\n"
			+ "	a.prix_vente, \r\n"
			+ "	a.etat_vente,\r\n"
			+ "	e.no_utilisateur,\r\n"
			+ "	a.no_categorie,\r\n"
			+ "	e.date_enchere, \r\n"
			+ "	e.montant_enchere\r\n"
			+ "	FROM ENCHERES AS e INNER JOIN ARTICLES_VENDUS a on e.no_article = a.no_article and e.montant_enchere = (SELECt max(e.montant_enchere) from encheres e where e.no_article = a.no_article)\r\n"
			+ "	where a.no_article= ?;";
	
	private static final String SELECT_BY_NO_ENCHERE = "SELECT * FROM ENCHERES WHERE no_enchere = ?";
	
    private Utilisateur getEnchereUtilisateur(int noUtilisateur) throws BusinessException {
        Utilisateur encherisseur = null;
        UtilisateurManager utilisateurManager = new UtilisateurManager();
        encherisseur = utilisateurManager.selectionner(noUtilisateur);
        return encherisseur;
    }
    
    private ArticleVendu getEnchereArticle (int noArticle) throws BusinessException {
        ArticleVendu articleVendu = null;
        ArticleManager articleManager = new ArticleManager();
        articleVendu = articleManager.selectArticleById(noArticle);
        return articleVendu;
    }
    
	//Builder de l'enchere
    private Enchere enchereBuilder(ResultSet rs) throws BusinessException, SQLException {
        Enchere enchere = new Enchere();
        enchere.setNoEnchere(rs.getInt("no_enchere"));        
        ArticleVendu article = this.getEnchereArticle(rs.getInt("no_article"));
        enchere.setArticle(article);
        Utilisateur utilisateur = this.getEnchereUtilisateur(rs.getInt("no_utilisateur"));
        enchere.setUtilisateur(utilisateur);
        enchere.setDateEnchere(rs.getTimestamp("date_enchere").toLocalDateTime());
        enchere.setMontantEnchere(rs.getInt("montantEnchere"));
      
        return enchere;
    }
    
    private void retraitPoints(int soldeActuel, int montantEnchere, Utilisateur acheteur) throws BusinessException {
        int soldeDebit = soldeActuel - montantEnchere;
        Utilisateur utilisateurRetraitPoints = new Utilisateur(acheteur.getNoUtilisateur(), soldeDebit);
        DAOFactory.getUtilisateurDAO().updateUtilisateurApresEnchere(utilisateurRetraitPoints);
    }

    private void ajoutPoints(int soldeActuel, int montantEnchere, Utilisateur acheteur) throws BusinessException {
        int soldeCredit = soldeActuel + montantEnchere;
        Utilisateur utilisateurAjoutPoints = new Utilisateur(acheteur.getNoUtilisateur(), soldeCredit);
        DAOFactory.getUtilisateurDAO().updateUtilisateurApresEnchere(utilisateurAjoutPoints);
    }  
    
	@Override
	public Enchere selectEnchereByNoEnchere(int noEnchere) throws BusinessException {
		Enchere enchere = null;
		Connection cnx = null;
		
		try {
			cnx = ConnectionProvider.getConnection();
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_NO_ENCHERE);
			pstmt.setInt(1, noEnchere);
			ResultSet rs= pstmt.executeQuery();
			while(rs.next()) {
				enchere = enchereBuilder(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_BY_NO_ENCHERE_ECHEC);
		}
		return enchere;
	}

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
	
	@Override
	public Enchere selectEnchereMaxByArticle(int noArticle) throws BusinessException {
		Enchere enchere = null;
		Connection cnx = null;

		try {
			cnx = ConnectionProvider.getConnection();
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ENCHERE_MAX_BY_ARTICLE);
			pstmt.setInt(1, noArticle);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				enchere = enchereBuilder(rs); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ENCHERE_MAX_BY_ARTICLE_ECHEC);
		}
	return enchere;
}

	@Override
	public Enchere insertNewEnchere(Utilisateur acheteur, int noArticle, int montantEnchere) throws BusinessException {
		Connection cnx = null;
		//Variable de l'enchère tentée 
		Enchere enchereAjout = null;
		//Je stocke les données de la meilleure enchère actuelle pour traitement après update
		Enchere enchere = DAOFactory.getEnchereDAO().selectEnchereMaxByArticle(noArticle);
		int noAjout = 0;
		LocalDateTime now = LocalDateTime.now();
		//comparaison du prix initial ou de la meilleure enchère avec la proposition en cours
		if ((enchere.getArticle().getPrixInitial() < montantEnchere || (enchere.getArticle().getPrixVente() < montantEnchere))) {
			//on vérifie que l'enchère n'est pas effectuée par l'actuel meilleur enchérisseur
			if (enchere.getUtilisateur() == null || acheteur.getNoUtilisateur() != enchere.getUtilisateur().getNoUtilisateur()) {
				//contrôle sur le crédit de points disponible pour l'utilisateur
				if (enchere.getUtilisateur().getCredit() > montantEnchere)  {
					try {
						cnx = ConnectionProvider.getConnection();
						PreparedStatement pstmt = cnx.prepareStatement(INSERT_NEW_ENCHERE, PreparedStatement.RETURN_GENERATED_KEYS);
						pstmt.setInt(1, acheteur.getNoUtilisateur());
						pstmt.setInt(2, noArticle);
						pstmt.setObject(3, now);
						pstmt.setInt(4, montantEnchere);
						pstmt.executeUpdate();
						ResultSet rs = pstmt.getGeneratedKeys();
						if (rs.next()) {
							noAjout = rs.getInt(1);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					//si ça fonctionne un trigger en base met à jour le prix vente dans la table ARTICLEs avec la nouvelle enchère
					//Ensuite on ajoute des points à l'ancien meilleur enchérisseur s'il existe
					if (enchere.getMontantEnchere() > 0) {
						ajoutPoints(enchere.getUtilisateur().getCredit(), enchere.getMontantEnchere(), enchere.getUtilisateur());
					}
					//dans tous les cas je débite l'enchérisseur actuel
					retraitPoints(acheteur.getCredit(), montantEnchere, acheteur);
					//je récupère l'enchère créée 
					enchereAjout = DAOFactory.getEnchereDAO().selectEnchereByNoEnchere(noAjout);
				} else {
					throw new BusinessException();
				}
			} else {
				throw new BusinessException();
			}
		} else {
			throw new BusinessException();
		}
		return enchereAjout;
	}

//	@Override
//	public List<Enchere> selectAllEnchere() throws BusinessException {
//		// TODO Auto-generated method stub
//		List<Enchere> listEnchere = new ArrayList<>();
//		final String SELECT_ALL_ENCHERE = "SELECT  * FROM ENCHERES" //A corriger évidemment
//	}

}
