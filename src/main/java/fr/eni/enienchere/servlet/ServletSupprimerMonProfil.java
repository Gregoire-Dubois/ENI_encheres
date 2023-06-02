package fr.eni.enienchere.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.enienchere.bll.UtilisateurManager;
import fr.eni.enienchere.bo.Utilisateur;

/**
 * Servlet implementation class ServletSupprimerMonProfil
 */
@WebServlet("/supprimermoncompte")
public class ServletSupprimerMonProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	//On récupere l'utilisateur connecté
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("userConnected");
		//On récupère son id
		int noUtilisateur = utilisateur.getNoUtilisateur();	
		System.out.println(noUtilisateur); //Pour les tests en console
		UtilisateurManager utilisateurManager = new UtilisateurManager();
        utilisateurManager.deleteById(noUtilisateur);
        
        session.invalidate();
        response.sendRedirect(request.getContextPath()+"/accueil");
    }
           
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		int no_utilisateur = (Integer) session.getAttribute("userID");	
		
		UtilisateurManager utilisateurManager = new UtilisateurManager();
        utilisateurManager.deleteById(no_utilisateur);
        
        session.invalidate();
        response.sendRedirect(request.getContextPath()+"/accueil");
	}

}
