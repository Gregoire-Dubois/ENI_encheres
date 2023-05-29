package fr.eni.enienchere.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.eni.enienchere.BusinessException;
import fr.eni.enienchere.bo.Utilisateur;
import fr.eni.enienchere.dal.CodesResultatDAL;

public class UtilisateurDAOJdbcImpl implements UtilisateurDAO{

	private static final String SELECT_BY_ID = "SELECT * FROM UTILISATEURS WHERE no_utilisateur=?;";
	private static final String SELECT_BY_EMAIL_ET_MDP="SELECT * FROM UTILISATEURS WHERE email=? AND mot_de_passe=?";
	@Override
	public Utilisateur select_by_id(int id) throws BusinessException {
		Utilisateur utilisateur = null;
		Connection cnx = null;
		
		try {
			cnx = ConnectionProvider.getConnection();
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_ID);
			
			pstmt.setInt(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
			utilisateur = new Utilisateur(id, rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getInt(11), rs.getBoolean(12));
			}
			
		} catch (SQLException e) {
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
		
		
		return utilisateur;
		
	}
	@Override
	public Utilisateur select_by_email_mdp(String email, String mdp) throws BusinessException {
		Utilisateur utilisateur = null;
		Connection cnx;
		
		try {
			cnx = ConnectionProvider.getConnection();
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_EMAIL_ET_MDP);
			
			pstmt.setString(1, email);
			pstmt.setString(2, mdp);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
			utilisateur = new Utilisateur(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getInt(11), rs.getBoolean(12));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return utilisateur;
	}

}
