package fr.eni.enienchere.servlet;

import java.io.IOException;
import java.util.List;

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
import fr.eni.enienchere.dal.UtilisateurDAOJdbcImpl;

/**
 * Servlet implementation class ServletAffichageMonProfile
 */
@WebServlet("/profil")
public class ServletAffichageMonProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int noVendeur = 0; //Partie à vérifier
		int noUtilisateur = 0;
		Utilisateur utilisateur = null;
		Utilisateur utilisateur2=null;
		utilisateur2 = (Utilisateur) session.getAttribute("userConnected");
		int noUtilisateur2 = utilisateur2.getNoUtilisateur();
		//noUtilisateur = utilisateur.getNoUtilisateur();
		//System.out.println(request.getParameter("pseudo"));// 1

		
		
		//Si on a cliqué sur Mon Profil et pas le lien vers un autre profil
		if (request.getParameter("idVendeur") == null) { //Pour le moment on est dans ce cas, le reste est à vérifier
			
			//On récupère l'utilisateur actuellement connecté
			utilisateur = (Utilisateur) session.getAttribute("userConnected");
			
			//noUtilisateur = utilisateur.getNoUtilisateur();//A vérifier si c'est utile mais je ne touche pas pour le moment
			
			//On met en attribut de session l'utilisateur connecté dans "utilisateur"
			session.setAttribute("utilisateur", utilisateur);
			
			//On redirige vers la JSP qui affiche le profil
//			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/JSPMonProfile.jsp");
//			rd.forward(request, response);
		} else {
			noVendeur = Integer.parseInt(request.getParameter("idVendeur"));
			System.out.println(noVendeur);
			if (noUtilisateur2 == noVendeur) {
				request.setAttribute("utilisateur", utilisateur2);
//				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/JSPMonProfile.jsp");
//				rd.forward(request, response);
			} else {

				try {

					UtilisateurManager utilisateurManager = new UtilisateurManager();
					Utilisateur vendeur = utilisateurManager.selectionner(noVendeur);
					request.setAttribute("utilisateur", vendeur);

//					RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/JSPMonProfile.jsp");
//					rd.forward(request, response);
				} catch (BusinessException e) {
					e.printStackTrace();

					List<Integer> listeErreursProfil = e.getListeCodesErreur();
					request.setAttribute("listeErreursProfil", listeErreursProfil);

//					RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/JSPMonProfile.jsp");
//					rd.forward(request, response);

				}
			}
		}
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/JSPMonProfile.jsp");
		rd.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
	}

}