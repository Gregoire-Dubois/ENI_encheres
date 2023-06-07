package fr.eni.enienchere.servlets.tests;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.enienchere.dal.ArticleDAOJdbcImpl;

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
		ArticleDAOJdbcImpl etatVente = new ArticleDAOJdbcImpl();
		int id = Integer.parseInt(request.getParameter("idArticle")) ;
		String ev = etatVente.idArticleInList(id);
		session.setAttribute("etatVente", ev);
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

