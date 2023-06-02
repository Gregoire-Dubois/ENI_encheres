package fr.eni.enienchere.servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class ServletAffichageModifierProfile
 */
@WebServlet("/ServletAffichageModifierProfile")
public class ServletAffichageModifierProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			RequestDispatcher rd = null;
			rd = request.getRequestDispatcher("WEB-INF/jsp/JSPModifierMonProfile.jsp");
			rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pseudo = request.getParameter("pseudo");
		String prenom = request.getParameter("prenom");
		String telephone = request.getParameter("telephone"); 
		String codePostal = request.getParameter("codepostal"); 
		String motDePass = request.getParameter("password");
		String nouveauMotDePass = request.getParameter("newPassword");
		String confirmationNouveauMotDePass = request.getParameter("confirmNewPassword");
		String nom = request.getParameter("nom");
		String email = request.getParameter("email"); 
		String rue = request.getParameter("rue"); 
		String ville = request.getParameter("ville");
		
		System.out.println(pseudo);
		System.out.println(prenom);
		System.out.println(telephone);
		System.out.println(codePostal);
		System.out.println(motDePass);
		System.out.println(nouveauMotDePass);
		System.out.println(confirmationNouveauMotDePass);
		System.out.println(nom);
		System.out.println(email);	
		 
		

	}

}
