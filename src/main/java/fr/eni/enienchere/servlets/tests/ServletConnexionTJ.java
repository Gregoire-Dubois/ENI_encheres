package fr.eni.enienchere.servlets.tests;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.enienchere.BusinessException;
import fr.eni.enienchere.bll.UtilisateurManager;
import fr.eni.enienchere.bo.Utilisateur;

/**
 * Servlet implementation class ServletConnexionTJ
 * A SUPPRIMER ?
 */
@WebServlet("/ServletConnexionTJ")
public class ServletConnexionTJ extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletConnexionTJ() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = null;
		rd = request.getRequestDispatcher("WEB-INF/JSPPageConnexion.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
       	
		PrintWriter out= response.getWriter();
		
		HttpSession session = request.getSession();
		
		String erreur = null;
		
		int identifiant = Integer.parseInt(request.getParameter("id"));
		String password = request.getParameter("mdp");
		
		
		try {
			
			//Valider pseudo utilisateur, verification si il est bien dans la bdd
			Utilisateur user = UtilisateurManager.selectionner(identifiant);
			//Si la connexion est reussie
			if(user!= null && password.equals(user.getMotDePasse())) {
			request.getSession().setAttribute("ConnectedUser", user);
			
			this.getServletContext().getRequestDispatcher("/ServletAccueilConnecte").forward(request, response);
			
		} else {
			request.setAttribute("erreur", "pseudo et/ou mot de passe incorrect(s)! Veuillez ressaisir vos identifiants...");
			erreur = (String) session.getAttribute("erreur");
			out.println(erreur);
			this.getServletContext().getRequestDispatcher("/ServletConnexionTJ").forward(request, response);
	}

}
