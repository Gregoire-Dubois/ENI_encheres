package fr.eni.enienchere.servlets.tests;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.enienchere.bll.UtilisateurManager;
import fr.eni.enienchere.bo.Utilisateur;

/**
 * Servlet implementation class ServletModifierMonProfil
 */
@WebServlet("/modifierMonProfil")
public class ServletModifierMonProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletModifierMonProfil() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = null;
		rd = request.getRequestDispatcher("/WEB-INF/jsp/JSPModifierMonProfile.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UtilisateurManager utilisateurManager = new UtilisateurManager();
		HttpSession session = request.getSession();
		Utilisateur utilisateurConnecte = (Utilisateur) session.getAttribute("utilisateurConnecte");
		
		try {
			String pseudo = request.getParameter("pseudo");
			String nom = request.getParameter("nom");
			String prenom = request.getParameter("prenom");
			String email = request.getParameter("email");
			String telephone = request.getParameter("telephone");
			String rue = request.getParameter("rue");
			String codePostal = request.getParameter("codepostal");
			String ville = request.getParameter("ville");
			String password = request.getParameter("motDePasseActuel");
			String nouveauMotDePasse = request.getParameter("nouveauMotDePasse");
			String confirmation = request.getParameter("motDePasseConfirmation");
			
			utilisateurConnecte.setPseudo(pseudo);
			utilisateurConnecte.setNom(nom);
			utilisateurConnecte.setPrenom(prenom);
			utilisateurConnecte.setEmail(email);
			utilisateurConnecte.setTelephone(telephone);
			utilisateurConnecte.setRue(rue);
			utilisateurConnecte.setCodePostal(codePostal);
			utilisateurConnecte.setVille(ville);
			
			if (password.equals(utilisateurConnecte.getMotDePasse()) && nouveauMotDePasse.equals(confirmation)) {
				utilisateurConnecte.setMotDePasse(nouveauMotDePasse);
			} 
						
			utilisateurManager.modifierUtilisateur(utilisateurConnecte);
			session.setAttribute("UtilisateurConnecte", utilisateurConnecte);
			
			this.getServletContext().getRequestDispatcher("/jsp/JSPMonProfil").forward(request, response);
		
		} catch (Exception e) {
		e.printStackTrace();
		}
	}

}
