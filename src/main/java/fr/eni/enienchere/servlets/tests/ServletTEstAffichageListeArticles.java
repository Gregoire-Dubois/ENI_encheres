package fr.eni.enienchere.servlets.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import fr.eni.enienchere.BusinessException;
import fr.eni.enienchere.bll.ArticleManager;
import fr.eni.enienchere.bo.ArticleVendu;

/**
 * Servlet implementation class ServletTEstAffichageListeArticles
 */
@WebServlet("/ServletTEstAffichageListeArticles")
public class ServletTEstAffichageListeArticles extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ArticleManager tousArticles = new ArticleManager();		
		ArrayList<ArticleVendu> xArrayList  = new ArrayList<>();
		
		try {
			xArrayList = tousArticles.selectionnerArticles();

			
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			List<Integer> listeErreurs = e.getListeCodesErreur();
			request.setAttribute("listeErreurs", listeErreurs);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/JSPAccueil.jsp");

		}
		
		HttpSession session = request.getSession();
		session.setAttribute("articles", xArrayList);
		getServletContext().getRequestDispatcher("/WEB-INF/jsp/JSPAccueil.jsp").forward(request, response);	

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}