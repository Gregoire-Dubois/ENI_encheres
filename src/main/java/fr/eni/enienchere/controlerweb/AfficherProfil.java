package fr.eni.enienchere.controlerweb;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.enienchere.BusinessException;
import fr.eni.enienchere.bll.UtilisateurManager;
import fr.eni.enienchere.bo.Utilisateur;




@WebServlet("/afficher_profil")
public class AfficherProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id;
		id = Integer.parseInt(request.getParameter("36"));
		Utilisateur u;
		try {
			u = UtilisateurManager.getInstance().selectionner(id);
			request.setAttribute("user", u);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		getServletContext().getRequestDispatcher("/WEB-INF/jsp/tests/JSPProfilUtilisateur.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
