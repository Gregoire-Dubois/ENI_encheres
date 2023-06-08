package fr.eni.enienchere.servlets.tests;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.enienchere.BusinessException;
import fr.eni.enienchere.bo.ArticleVendu;
import fr.eni.enienchere.bo.Utilisateur;
import fr.eni.enienchere.dal.ArticleDAOJdbcImpl;
import fr.eni.enienchere.dal.UtilisateurDAOJdbcImpl;

/**
 * Servlet implementation class ServletTestEncherirAcquisitionDetailMAventeFinEnchere
 */
@WebServlet("/ServletTestEncherirAcquisitionDetailMAventeFinEnchere")
public class ServletTestEncherirAcquisitionDetailMAventeFinEnchere extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		HttpSession session = request.getSession();
		RequestDispatcher rd = null;
		
		
		//filtrer l'affichage JSP en fonctionde l'état des vente (EN NC EV)
		ArticleDAOJdbcImpl etatVente = new ArticleDAOJdbcImpl();
		int id = Integer.parseInt(request.getParameter("idArticle")) ;
		System.out.println(id);
		String ev = etatVente.idArticleInList(id);
		session.setAttribute("etatVente", ev);
		
		
		//récupérer les infos d'un article pour sa page enchères

		ArticleDAOJdbcImpl detailsArticle = new ArticleDAOJdbcImpl();
		ArticleVendu details = null;;
		try {
			details = detailsArticle.selectArticleById(id);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.setAttribute("details", details);
		
		//récupérer les infos de l'encherisseur
		UtilisateurDAOJdbcImpl acquereur = new UtilisateurDAOJdbcImpl();
	    Utilisateur encherisseur = null;
	    try {
	        encherisseur = acquereur.getEncherisseurByArticleId(id);
	    } catch (BusinessException e) {
	        e.printStackTrace();
	    }
	    session.setAttribute("encherisseur", encherisseur);
	    System.out.println(encherisseur);

		
		
		rd = request.getRequestDispatcher("/WEB-INF/jsp/JSPPageEncherirAcquisitionDetailMaVente.jsp");
		rd.forward(request, response); 
		 
		


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}

