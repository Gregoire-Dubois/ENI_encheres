package fr.eni.enienchere.servlets.tests;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.enienchere.BusinessException;
import fr.eni.enienchere.bll.UtilisateurManager;
import fr.eni.enienchere.bo.Utilisateur;

/**
 * Servlet implementation class ServletConnexion
 */
@WebServlet("/connexion")
public class ServletConnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletConnexion() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/tests/JSPConnexion.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String identifiant = request.getParameter("identifiant");
		String mdp = request.getParameter("mdp"); //hash à rajouter?
		
		UtilisateurManager utilisateurManager = new UtilisateurManager();
		
		//On teste si l'identification par email/mdp OU pseudo/mdp fonctionne
		//Si oui, on va sur la page d'accueil en mode connecté sinon on retourne sur la page de connexion avec un message d'erreur
		try {
			Utilisateur utilisateur = utilisateurManager.login(identifiant, mdp);
			//System.out.println(utilisateur);
			if(utilisateur!=null) {
				request.getSession().setAttribute("userConnected", utilisateur);
				
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/tests/JSPAccueil.jsp");
				rd.forward(request, response);
			}else {
				//Message d'erreur à afficher sur la page de connexion
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/tests/JSPConnexion.jsp");
				rd.forward(request, response);
				
			}
		} catch (BusinessException e1) {
			e1.printStackTrace();
		}
	}
}
