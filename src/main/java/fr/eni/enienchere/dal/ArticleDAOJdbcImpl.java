package fr.eni.enienchere.dal;

import java.util.List;

import fr.eni.enienchere.BusinessException;
import fr.eni.enienchere.bo.ArticleVendu;
import fr.eni.enienchere.bo.Utilisateur;

public class ArticleDAOJdbcImpl implements ArticleDAO {

	@Override
	public ArticleVendu selectArticleById(int articleId) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ArticleVendu> selectAllArticles() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ArticleVendu> selectAllArticlesByUtilisateur(Utilisateur utilisateur) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArticleVendu insertArticle(ArticleVendu article) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
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
