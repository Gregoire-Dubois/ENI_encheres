package fr.eni.enienchere.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.enienchere.bo.Utilisateur;

/**
 * Servlet implementation class ServletAffichageMonProfile
 */
@WebServlet("/ServletAffichageMonProfile")
public class ServletAffichageMonProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	
		Utilisateur user = new Utilisateur();
		//UtilisateurDAOJdbcImpl userDaoJdbcImpl = new UtilisateurDAOJdbcImpl();
		//System.out.println(user.toString());
		//request.setAttribute("user", user);
		request.getSession().setAttribute("user", user);
		getServletContext().getRequestDispatcher("/WEB-INF/JSPMonProfile.jsp").forward(request, response);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}