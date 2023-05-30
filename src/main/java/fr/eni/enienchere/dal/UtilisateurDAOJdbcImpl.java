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
	private static final String SELECT_BY_PSEUDO_ET_MDP="SELECT * FROM UTILISATEURS WHERE pseudo=? AND mot_de_passe=?";
	private static final String INSERT_UTILISATEUR="INSERT INTO UTILISATEURS(pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
	
	@Override
	public Utilisateur selectById(int id) throws BusinessException {
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
	
	//Permet une selection par l'email et le mot de passe
	
	@Override
	public Utilisateur selectByEmailMdp(String email, String mdp) throws BusinessException {
		Utilisateur utilisateur = null;
		Connection cnx = null;
		
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
			//Gestion des erreurs
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

	//Permet une selection par le pseudo et le mot de passe	
		@Override
		public Utilisateur selectByPseudoMdp(String pseudo, String mdp) throws BusinessException {
			Utilisateur utilisateur = null;
			Connection cnx;
			
			try {
				cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_PSEUDO_ET_MDP);
				
				pstmt.setString(1, pseudo);
				pstmt.setString(2, mdp);
				
				ResultSet rs = pstmt.executeQuery();
				
				while(rs.next()) {
				utilisateur = new Utilisateur(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getInt(11), rs.getBoolean(12));
				}
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			
			return utilisateur;
		}
		
		//Permet l'insertion d'un utilisateur	
		@Override
		public Utilisateur insert(Utilisateur utilisateur) throws BusinessException {
			Connection cnt;
			
			try {
				cnt = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnt.prepareStatement(INSERT_UTILISATEUR, PreparedStatement.RETURN_GENERATED_KEYS);
				pstmt.setString(1,utilisateur.getPseudo());
				pstmt.setString(2,utilisateur.getNom());
				pstmt.setString(3,utilisateur.getPrenom());
				pstmt.setString(4,utilisateur.getEmail());
				pstmt.setString(5, utilisateur.getTelephone());
				pstmt.setString(6,utilisateur.getRue());
				pstmt.setString(7,utilisateur.getCodePostal());
				pstmt.setString(8,utilisateur.getVille());
				pstmt.setString(9,utilisateur.getMotDePasse()); //hash à ajouter
				pstmt.setInt(10, 200); //valeur arbitraire à voir ce qu'on fait, peut être un random?
				pstmt.setInt(11, 0); //par défaut, la personne qui s'inscrit n'est pas administrateur
				pstmt.executeUpdate();
				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					utilisateur.setNoUtilisateur(rs.getInt(1));
				}
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			
			return utilisateur;
		}

}
