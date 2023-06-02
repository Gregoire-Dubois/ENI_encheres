package fr.eni.enienchere.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;


import fr.eni.enienchere.BusinessException;
import fr.eni.enienchere.bo.Utilisateur;

public class UtilisateurDAOJdbcImpl implements UtilisateurDAO{

	private static final String SELECT_BY_ID = "SELECT * FROM UTILISATEURS WHERE no_utilisateur=?;";
	private static final String SELECT_BY_EMAIL_ET_MDP="SELECT * FROM UTILISATEURS WHERE email=? AND mot_de_passe=?";
	/*private static final String SELECT_ALL = "SELECT * FROM UTILISATEURS;"; A Supprimer*/
	private static final String UPDATE_BY_ID = "UPDATE UTILISATEURS SET pseudo = ?, nom = ?, prenom = ?, email = ?, telephone = ? , rue = ?, code_postal = ?, ville = ?, mot_de_passe = ? WHERE no_utilisateur = ?";
	private static final String DELETE_BY_ID = "DELETE FROM UTILISATEUR WHERE no_utilisateur=?;";
	private static final String DELETE_BY_EMAIL = "DELETE FROM UTILISATEUR WHERE email=?;";
	private static final String SELECT_BY_PSEUDO_ET_MDP="SELECT * FROM UTILISATEURS WHERE pseudo=? AND mot_de_passe=?";
	private static final String INSERT_UTILISATEUR="INSERT INTO UTILISATEURS(pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
	private static final String SELECT_BY_PSEUDO="SELECT * FROM UTILISATEURS WHERE pseudo=?";
	private static final String SELECT_BY_EMAIL="SELECT * FROM UTILISATEURS WHERE email=?";
	
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
	/*
	 * Ajout hashpwd CCN 01/06/23
	 */
	
	@Override
	public Utilisateur selectByEmailMdp(String email, String mdp) throws BusinessException {
		Utilisateur utilisateur = null;
		Connection cnx = null;
		
		try {
			cnx = ConnectionProvider.getConnection();
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_EMAIL_ET_MDP);
			
			pstmt.setString(1, email);
			pstmt.setString(2, Utilisateur.hashPwd(mdp));
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
			utilisateur = new Utilisateur(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getInt(11), rs.getBoolean(12));
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
	

	public Utilisateur selectByPseudo(String pseudo) throws BusinessException {
		Utilisateur utilisateur = null;
		Connection cnx = null;
		
		try {
			cnx = ConnectionProvider.getConnection();
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_PSEUDO);
			
			pstmt.setString(1, pseudo);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
			utilisateur = new Utilisateur(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getInt(11), rs.getBoolean(12));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_BY_PSEUDO_ECHEC);
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
	public Utilisateur selectByEmail(String email) throws BusinessException {
		Utilisateur utilisateur = null;
		Connection cnx = null;
		
		try {
			cnx = ConnectionProvider.getConnection();
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_EMAIL);
			
			pstmt.setString(1, email);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
			utilisateur = new Utilisateur(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getInt(11), rs.getBoolean(12));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_BY_EMAIL_ECHEC);
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

	/* Requête : Modification du compte utilisateur
	 * Fait par Tanguy
	 * ajout hashpwd CCN 01/06/23
	 */
		
	@Override
	public void updateUtilisateur(Utilisateur utilisateur) throws BusinessException {
		Connection cnx;
		
	    try {
	    	cnx = ConnectionProvider.getConnection();
	    	PreparedStatement pstmt = cnx.prepareStatement(UPDATE_BY_ID);
	    	pstmt.setString(1, utilisateur.getPseudo());
	    	pstmt.setString(2, utilisateur.getNom());
	    	pstmt.setString(3, utilisateur.getPrenom());
	    	pstmt.setString(4, utilisateur.getEmail());
	    	//gestion du null
	    	if(utilisateur.getTelephone() == null){
	    		pstmt.setNull(5, Types.VARCHAR);
	    	}else {
	    		pstmt.setString(5, utilisateur.getTelephone());
	    	}
	    	pstmt.setString(6, utilisateur.getRue());
	    	pstmt.setString(7, utilisateur.getCodePostal());
	    	pstmt.setString(8, utilisateur.getVille());
	    	pstmt.setString(9, Utilisateur.hashPwd(utilisateur.getMotDePasse()));
	    	pstmt.setInt(10, utilisateur.getNoUtilisateur());
	    	pstmt.executeUpdate();
	    	pstmt.close();


    	}catch (SQLException e) {
    		e.printStackTrace();
    	}
	  }	
	
	/* Requête : Suppression du compte utilisateur par son N° Utilisateur
	 * Fait par Tanguy
	 */

	@Override
	public void deleteById(int id) throws BusinessException {
		Connection cnx;
		
		try {
			cnx = ConnectionProvider.getConnection();
	    	PreparedStatement pstmt = cnx.prepareStatement(DELETE_BY_ID);
	    	pstmt.setInt(1, id);
			pstmt.executeUpdate();
			cnx.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* Requête : Suppression du compte utilisateur par son email
	 * Fait par Tanguy
	 */

	@Override
	public void deleteByEmail(String email) throws BusinessException {
		Connection cnx;
		
		try {
			cnx = ConnectionProvider.getConnection();
	    	PreparedStatement pstmt = cnx.prepareStatement(DELETE_BY_EMAIL);
	    	pstmt.setString(1, email);
			pstmt.executeUpdate();
			cnx.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Permet une selection par le pseudo et le mot de passe	
	/*
	 * Ajout du hash CCN 01/06/23
	 */
		@Override
		public Utilisateur selectByPseudoMdp(String pseudo, String mdp) throws BusinessException {
			Utilisateur utilisateur = null;
			Connection cnx;
			
			try {
				cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_PSEUDO_ET_MDP);
				
				pstmt.setString(1, pseudo);
				pstmt.setString(2, Utilisateur.hashPwd(mdp));
				
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
		/*
		 * Ajout du hash CCN 01/06/23
		 */
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
				pstmt.setString(9,Utilisateur.hashPwd(utilisateur.getMotDePasse())); 
				pstmt.setInt(10, 0); 
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
	/*
		// A compléter
		@Override
		public List<Utilisateur> selectAll() throws BusinessException {
			// TODO Auto-generated method stub
			return null;
		}
		
		*/


}


