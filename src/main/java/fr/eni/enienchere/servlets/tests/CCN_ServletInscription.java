package fr.eni.enienchere.servlets.tests;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.enienchere.BusinessException;
import fr.eni.enienchere.bll.DRAFT_UtilisateurManager;
import fr.eni.enienchere.bo.Utilisateur;

/**
 * Servlet implementation class CCN_ServletInscription
 */
@WebServlet("/CCN_ServletInscription")
public class CCN_ServletInscription extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/tests/CCN_JSPInscription.jsp");
        rd.forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pseudo       = request.getParameter("pseudo");
        String nom          = request.getParameter("nom");
        String prenom       = request.getParameter("prenom");
        String email        = request.getParameter("email");
        String telephone    = request.getParameter("telephone");
        String rue          = request.getParameter("rue");
        String codePostal   = request.getParameter("codePostal");
        String ville        = request.getParameter("ville");
        String motDePasse   = request.getParameter("mdp");
        String mdpConfirm = request.getParameter("confirmationMdp");

		Utilisateur newUser = new Utilisateur(pseudo,nom,prenom,email,telephone,rue,codePostal,ville,mdpConfirm,0,false);
		DRAFT_UtilisateurManager utilisateurManager = DRAFT_UtilisateurManager.getInstante();
		
		//Si les mots de passe ne sont pas identiques, on recharge la page avec les infos
		if (!motDePasse.equals(mdpConfirm)) {
			System.out.println("Les mots de passes ne sont pas identiques"); //Affichage console pour les tests
			//A faire : Erreur retour sur la page d'inscription
			request.setAttribute("newUser", newUser);
			request.getRequestDispatcher("/WEB-INF/jsp/tests/CCN_JSPInscription.jsp").forward(request, response);	
		} else {
			try {
				Utilisateur utilisateur = utilisateurManager.insert(newUser);
				if(utilisateur !=null) {
					request.getSession().setAttribute("userConnected", utilisateur);
					
					RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/tests/JSPAccueil.jsp");
					rd.forward(request, response);	
				}else {
					
					RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/tests/CCN_JSPInscription.jsp");
					rd.forward(request, response);
			}
			}catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
