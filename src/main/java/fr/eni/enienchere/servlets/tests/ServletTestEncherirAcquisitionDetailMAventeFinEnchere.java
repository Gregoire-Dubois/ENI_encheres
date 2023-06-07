package fr.eni.enienchere.servlets.tests;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletTestEncherirAcquisitionDetailMAventeFinEnchere
 */
@WebServlet("/ServletTestEncherirAcquisitionDetailMAventeFinEnchere")
public class ServletTestEncherirAcquisitionDetailMAventeFinEnchere extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = null;
		rd = request.getRequestDispatcher("/WEB-INF/jsp/JSPPageEncherirAcquisitionDetailMaVente.jsp");
		rd.forward(request, response); 

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}


/*
 * 
 * affiche toutes les enchères en cours sauf celles du client qui fait la requete

SELECT no_article, nom_article, date_fin_encheres, prix_vente, pseudo
FROM ARTICLES_VENDUS
INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur
WHERE etat_vente = 'EC' AND pseudo != 'PSeudoAExclure';




Affiche toutes les requetes des annonces non commencée y compris celles du client 
SELECT no_article, nom_article, date_debut_encheres,date_fin_encheres, prix_vente, pseudo
FROM ARTICLES_VENDUS
INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur
WHERE etat_vente = 'NC';

*/
