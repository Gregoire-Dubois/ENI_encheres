package fr.eni.enienchere.dal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import fr.eni.enienchere.bo.Utilisateur;

public class UtilisateurDAOJdbcImpl implements UtilisateurDAO{

	@Override
	public Utilisateur select() {

		
		try (Connection cnx = ConnectionProvider.getConnection()){
			
				Statement stmt = cnx.createStatement();
				ResultSet rst = stmt.executeQuery("SELECT * FROM CATEGORIES");
				while (rst.next()) {
					System.out.println(rst.getString("libelle"));;
					
				}
				
				cnx.close();
				
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Probleme");
		}
		
		return null;
		
	}

}
