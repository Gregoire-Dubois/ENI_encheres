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
 * Servlet implementation class ServletInscription
 */
@WebServlet("/inscription")
public class ServletInscription extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletInscription() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/tests/JSPInscription.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//On récupère les données du formulaire
		String pseudo = request.getParameter("pseudo");
		//System.out.println(pseudo);
		String nom = request.getParameter("nom");
		//System.out.println(nom);
		String prenom = request.getParameter("prenom");
		//System.out.println(prenom);
		String email = request.getParameter("email");
		//System.out.println(email);
		String telephone = request.getParameter("telephone");
		//System.out.println(telephone);
		String rue = request.getParameter("rue");
		//System.out.println(rue);
		String codePostal = request.getParameter("codePostal");
		//System.out.println(codePostal);
		String ville = request.getParameter("ville");
		//System.out.println(ville);
		String mdp = request.getParameter("mdp");//ajouter hash?
		//System.out.println(mdp);
		String confirmationMdp = request.getParameter("confirmationMdp");//ajouter hash?
		//System.out.println(confirmationMdp);
		
		//Si le mdp et le confirmationMdp ne sont pas identiques => erreur
		//Sinon on poursuit le processus d'inscription
		if(!mdp.equals(confirmationMdp)) {
			System.out.println("Les mots de passes ne sont pas identiques"); //Affichage console pour les tests
			//A faire : Erreur retour sur la page d'inscription
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/tests/JSPInscription.jsp");
			rd.forward(request, response);
		}else {
			try {
				UtilisateurManager utilisateurManager = new UtilisateurManager();
				Utilisateur utilisateur = utilisateurManager.inscrire(pseudo, nom, prenom, email, telephone, rue, codePostal, ville, mdp);
				
				
					request.getSession().setAttribute("userConnected", utilisateur);
					
					RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/tests/JSPAccueil.jsp");
					rd.forward(request, response);	
				
				
				
			} catch (BusinessException e) {
				e.printStackTrace();
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/tests/JSPInscription.jsp");
				rd.forward(request, response);
				//A faire: afficher les messages d'erreurs sur la page d'inscription
			}
			
		}
		
		
		
	
	}

}
