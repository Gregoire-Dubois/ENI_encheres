package fr.eni.enienchere.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("eni_ucred")) {
					String [] idpwd = cookie.getValue().split(":");
					if (idpwd.length >= 1 ) {
						request.setAttribute("identifiant", UtilisateurManager.decrypt(idpwd[0]));
						request.setAttribute("mdp", UtilisateurManager.decrypt(idpwd[1]));
						String identifiant = request.getParameter("identifiant");
						String mdp = request.getParameter("mdp");
						System.out.println(idpwd[0] + " " + idpwd[1]);
					}
				}
			}
		}
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/JSPConnexion.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String identifiant = request.getParameter("identifiant");
		String mdp = request.getParameter("mdp");
		String cookieId = request.getParameter("se-souvenir-de-moi");
		UtilisateurManager utilisateurManager = new UtilisateurManager();
		//String mdphash = Utilisateur.hashPwd(mdp);
		
		if(cookieId != null){
			//Ajout du cryptage 08/06/23
			String encryptedId = UtilisateurManager.encrypt(identifiant);
	        String encryptedPassword = UtilisateurManager.encrypt(mdp);
			//Génération du cookie
	        Cookie cookie = new Cookie("eni_ucred", encryptedId+":"+encryptedPassword);
			cookie.setMaxAge(60 * 60 * 24 * 30);
			response.addCookie(cookie);
		}

		//On teste si l'identification par email/mdp OU pseudo/mdp fonctionne
		//Si oui, on va sur la page d'accueil en mode connecté sinon on retourne sur la page de connexion avec un message d'erreur
		try {
			Utilisateur utilisateur = utilisateurManager.login(identifiant, mdp);
			//On récupère la session
			HttpSession session = request.getSession();
			//On indique en paramètre de la session que l'utilisateur est bien connecté
			session.setAttribute("userConnected", utilisateur);		
			response.sendRedirect(request.getContextPath()+"/accueil");	
			//				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/tests/JSPAccueil.jsp");
			//				rd.forward(request, response);
		} catch (BusinessException e1) {
			e1.printStackTrace();
			//Test affichage des erreurs en console OK
			List<Integer> listeErreurs = e1.getListeCodesErreur();
			//			for(int erreur: listeErreurs) {
			//				System.out.println(erreur);
			//			}
			request.setAttribute("listeErreurs", listeErreurs);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/JSPConnexion.jsp");
			rd.forward(request, response);
			
			
		}


	}
}

