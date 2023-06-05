package fr.eni.enienchere.test;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.enienchere.BusinessException;
import fr.eni.enienchere.bll.UtilisateurManager;
import fr.eni.enienchere.bo.ArticleVendu;
import fr.eni.enienchere.bo.Enchere;
import fr.eni.enienchere.bo.Utilisateur;

/**
 * Servlet implementation class test_ccn
 */
@WebServlet("/testing/test_ccn")
public class test_ccn extends HttpServlet {
private static final long serialVersionUID = 1L;

/**
 * @see HttpServlet#HttpServlet()
 */
public test_ccn() {
    super();
    // TODO Auto-generated constructor stub
}

/**
 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
 */
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	try {
		UtilisateurManager utilisateurManager = new UtilisateurManager();
		Utilisateur utilisateur = utilisateurManager.selectionner(1);
		System.out.println("select by id : " +utilisateur);
		Utilisateur utilisateur2 = utilisateurManager.selectionnerEmailMdp("bob@leponge.fr", "leponge");
		System.out.println("select_by_email :" +utilisateur2);
	} catch (BusinessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	// Test du  List<Enchere> selectAllEnchereByNoArticle(int noArticle);
		try {
			ArticleVendu article = new ArticleVendu();
			article.setNoArticle(9);
			List<Enchere> listeEncheres;
			listeEncheres = article.getListeEncheres();
			for(Enchere enchere : listeEncheres) {
				System.out.println(enchere);
			}
			
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}

/**
 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
 */
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
}

}


/* JE PUSH UN COMMENTAIRE*/
/*Salut*/

/*
 * Je teste une nouvelle fois un push - Tanguy
 * 
 * 
 */
//Test
///Comment CCN
//J'ai faim

// grave la dale 
//menage!!!!!!!!!!
//grrrrr