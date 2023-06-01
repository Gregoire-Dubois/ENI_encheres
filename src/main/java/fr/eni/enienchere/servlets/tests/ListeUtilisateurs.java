package fr.eni.enienchere.servlets.tests;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.enienchere.bll.UtilisateurManager;
import fr.eni.enienchere.bo.Utilisateur;


@WebServlet("/liste_utilisateurs")
public class ListeUtilisateurs extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Utilisateur> lst;
		lst = UtilisateurManager.getInstance().findAll(null, null);
		request.setAttribute("liste", lst);
		getServletContext().getRequestDispatcher("/WEB-INF/jsp/tests/JSPListeUtilisateur.jsp").forward(request, response);

	}

}
