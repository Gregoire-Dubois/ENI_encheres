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
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/JSPInscription.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
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
		UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();
		HttpSession session;
		
		//Si les mots de passe ne sont pas identiques, on recharge la page avec les infos
		if (!motDePasse.equals(mdpConfirm)) {
			//System.out.println("Les mots de passes ne sont pas identiques"); //Affichage console pour les tests
			//Reste à reste affichage message d'erreur en console
			session = request.getSession();
			session.setAttribute("newUser", newUser);
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatServlet.MDP_NON_IDENTIQUE_ECHEC);
			//On récupère la liste d'erreurs générée plus tôt
			List<Integer> listeErreursInscription = businessException.getListeCodesErreur();
			
			//Pour les tests
			for(int erreur : listeErreursInscription) {
				System.out.println(erreur);
			}
			
			//On ajoute la liste dans les attributs de la requête pour les communiquer à la JSP
			request.setAttribute("listeErreursInscription", listeErreursInscription);
			request.getRequestDispatcher("/WEB-INF/jsp/JSPInscription.jsp").forward(request, response);	
		} else {
			try {
				Utilisateur utilisateur = utilisateurManager.insert(newUser);
					
					request.getSession().setAttribute("userConnected", utilisateur);
					response.sendRedirect(request.getContextPath()+"/accueil");

			}catch (BusinessException e) {
				//System.out.println("Je suis bien la?");
				e.printStackTrace();
				
				//On récupère la liste d'erreurs générée plus tôt
				List<Integer> listeErreursInscription = e.getListeCodesErreur();
				
				//Pour les tests
				for(int erreur : listeErreursInscription) {
					System.out.println(erreur);
				}
				
				//On ajoute la liste dans les attributs de la requête pour les communiquer à la JSP
				request.setAttribute("listeErreursInscription", listeErreursInscription);
				
				//On envoie à la JSP
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/JSPInscription.jsp");
				rd.forward(request, response);
			}
		}
		
		
	
	}

}
