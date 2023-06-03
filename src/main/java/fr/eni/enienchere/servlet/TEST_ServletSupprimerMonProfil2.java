/*
 * 02/06/2023 CCN 
 * Servlet de test pour suppression de profil
 * A integrer sur servlet de prod si TEST ok 
 */

package fr.eni.enienchere.servlet;

import java.io.IOException;
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
 * Servlet implementation class ServletSupprimerMonProfil
 */
@WebServlet("/delete")
public class TEST_ServletSupprimerMonProfil2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    }
           
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
    	//On récupere l'utilisateur connecté
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("userConnected");
		//On récupère son id
		int noUtilisateur = utilisateur.getNoUtilisateur();	
		System.out.println(noUtilisateur); //Pour les tests en console
		UtilisateurManager utilisateurManager = new UtilisateurManager();
		//boolean verifDelete = false;
		utilisateurManager.deleteById(noUtilisateur);
        session.invalidate();
        request.getRequestDispatcher("WEB-INF/jsp/JSPAccueil.jsp").forward(request,response);
        //response.sendRedirect(request.getContextPath()+"/");
//		HttpSession session = request.getSession();
//		
//		int no_utilisateur = (Integer) session.getAttribute("userID");	
//		
//		UtilisateurManager utilisateurManager = new UtilisateurManager();
//        utilisateurManager.deleteById(no_utilisateur);
//        
//        session.invalidate();
//        response.sendRedirect(request.getContextPath()+"/accueil");
	}

}
