package fr.eni.enienchere.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import fr.eni.enienchere.bo.Utilisateur;
import fr.eni.enienchere.BusinessException;
import fr.eni.enienchere.bo.ArticleVendu;
import fr.eni.enienchere.bo.Enchere;

public class EnchereDAOJdbcImpl implements EnchereDAO {
	
	//Builder de l'enchere
    private Enchere enchereBuilder(ResultSet rs) throws BusinessException {
        Enchere enchere = new Enchere();
        enchere.setId(rs.getInt("id_enchere"));
        ArticleVendu article = this.getEnchereArticle(rs.getInt("id_art"));
        enchere.setArticle(article);
        Utilisateur utilisateur = this.getEnchereUtilisateur(rs.getInt("ench_idUtilisateur"));
        enchere.setUtilisateur(utilisateur);
        enchere.setDateEnchere((Timestamp) rs.getObject("dateEnchere")); // ------> apres
        enchere.setMontantEnchere(rs.getInt("montantEnchere"));
      
        return enchere;
    }

	@Override
	public List<Enchere> selectAllEnchere() throws BusinessException {
		// TODO Auto-generated method stub
		List<Enchere> listEnchere = new ArrayList<>();
		final String SELECT_ALL_ENCHERE = "SELECT  * FROM ENCHERES" //A corriger évidemment
	}

}
