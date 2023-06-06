package fr.eni.enienchere.dal;

import java.util.List;

import fr.eni.enienchere.BusinessException;
import fr.eni.enienchere.bo.ArticleVendu;
import fr.eni.enienchere.bo.Utilisateur;

public interface ArticleDAO {
	
    public ArticleVendu selectArticleById(int articleId) throws BusinessException;

    public List<ArticleVendu> selectAllArticles() throws BusinessException;

    public List<ArticleVendu> selectAllArticlesByUtilisateur(Utilisateur utilisateur) throws BusinessException;

    public List<ArticleVendu> selectAllArticlesByCategorie(Utilisateur utilisateur) throws BusinessException;

    public ArticleVendu insertArticle(ArticleVendu article) throws BusinessException;

    public void updateArticle(ArticleVendu updateArticle) throws  BusinessException;
    
    public void deleteArticleById(ArticleVendu article) throws BusinessException;
    
    // Liste des articles actuellement en vente filtré par une catégorie
    public List<ArticleVendu> selectAllVentesEnCoursByNoCategorie(int id) throws BusinessException;
    
    // Liste des ventes non commencées et qui ont pour vendeur l'utilisateur connecté 
    public List<ArticleVendu> selectAllVentesNonCommenceesByNoUtilisateur(int id) throws BusinessException;
    
    // Liste des ventes en cours et qui ont pour vendeur l'utilisateur connecté 
    public List<ArticleVendu> selectAllVentesEnCoursByNoUtilisateur(int id) throws BusinessException;
    
 // Liste des ventes terminées et qui ont pour vendeur l'utilisateur connecté 
    public List<ArticleVendu> selectAllVentesTermineesByNoUtilisateur(int id) throws BusinessException;

    //A faire
	public List<ArticleVendu> selectAllArticlesByNoCategorie(int id) throws BusinessException;

	public List<ArticleVendu> selectAllArticlesByNoUtilisateur(int noUtilisateur) throws BusinessException;

}
