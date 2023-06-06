package fr.eni.enienchere.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import fr.eni.enienchere.BusinessException;
import fr.eni.enienchere.bo.ArticleVendu;
import fr.eni.enienchere.bo.Retrait;

public class RetraitDAOJdbcImpl implements RetraitDAO{
	
	private static final String INSERT_RETRAIT="INSERT INTO RETRAITS(no_article,rue, code_postal, ville) VALUES(?,?,?,?)";
	
	@Override
	public Retrait insert(ArticleVendu article) throws BusinessException {
		Connection cnt;
		
		try {
			cnt=ConnectionProvider.getConnection();
			PreparedStatement pstmt = cnt.prepareStatement(INSERT_RETRAIT, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, article.getNoArticle());
			pstmt.setString(2,article.getRetrait().getRue());
			pstmt.setString(3,article.getRetrait().getCodePostal());
			pstmt.setString(4,article.getRetrait().getVille());
						
			pstmt.executeUpdate();
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return article.getRetrait();
	
	}

}
