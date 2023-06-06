/**
 * @author CCN
 * Creation le 02/06/23
 */
package fr.eni.enienchere.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.enienchere.BusinessException;
import fr.eni.enienchere.bo.Categorie;

/**
 * @author CCN
 *
 */
public class CategorieDAOJdbcImpl implements CategorieDAO {

	//Définition des requêts SQL
	private static final String SELECT_ALL_CATEGORIES = "SELECT * FROM CATEGORIES";
	private static final String SELECT_CATEGORIE_BY_ID = "SELECT * FROM CATEGORIES WHERE NO_CATEGORIE = ?";
	private static final String SELECT_CATEGORIE_BY_NAME = "SELECT * FROM CATEGORIES WHERE LIBELLE = ?";

	@Override
	public List<Categorie> selectAllCategorie() throws BusinessException {
		List<Categorie> listCategorie = new ArrayList<>();
		Connection cnx = null;
		try {
			cnx = ConnectionProvider.getConnection();
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL_CATEGORIES);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				Categorie categorie = new Categorie();
				categorie.setId(rs.getInt("no_categorie"));
				categorie.setLibelle(rs.getString("libelle"));
				listCategorie.add(categorie);
			}
		}catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ALL_CATEGORIES_ECHEC);
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
		return listCategorie;
	}

	@Override
	public Categorie selectCategorieById(int categorieId) throws BusinessException {
		Connection cnx = null;
		Categorie categorie = null;
		try {
			cnx = ConnectionProvider.getConnection();
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_CATEGORIE_BY_ID);
			pstmt.setInt(1, categorieId);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				categorie = new Categorie(rs.getInt(1), rs.getString(2));
			}
		} catch (SQLException e){
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_CATEGORIE_BY_ID_ECHEC);
		}
		return categorie;
	}

	@Override
	public Categorie selectByName(String nomCategorie) throws BusinessException {
		Connection cnx = null;
		Categorie categorie = null;
		try {
			cnx = ConnectionProvider.getConnection();
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_CATEGORIE_BY_NAME);
			pstmt.setString(1, nomCategorie);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				categorie = new Categorie(rs.getInt(1), rs.getString(2));
			}
		} catch (SQLException e){
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_CATEGORIE_BY_NAME_ECHEC);
		}
		return categorie;
	}

}
