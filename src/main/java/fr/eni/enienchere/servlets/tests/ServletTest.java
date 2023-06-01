package fr.eni.enienchere.servlets.tests;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.enienchere.BusinessException;
import fr.eni.enienchere.bll.UtilisateurManager;
import fr.eni.enienchere.bo.Utilisateur;

/**
 * Servlet implementation class ServletTest
 */
@WebServlet("/ServletTest")
public class ServletTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletTest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//Test selection par noUtilisateur
			UtilisateurManager utilisateurManager = new UtilisateurManager();
			Utilisateur utilisateur = utilisateurManager.selectionner(1);
			System.out.println("select by id : " +utilisateur);
			//Test selection par email et mdp
			Utilisateur utilisateur2 = utilisateurManager.selectionnerEmailMdp("bob@leponge.fr", "leponge");
			System.out.println("select_by_email :" +utilisateur2);
			//Test selection par pseudo et mdp
			Utilisateur utilisateur3 = utilisateurManager.selectionnerPseudoMdp("bobby", "leponge");
			System.out.println("select_by_pseudo :" +utilisateur3);
			//Test insertion d'un utilisateur
			Utilisateur utilisateur4 = new Utilisateur(0,"Test","Kirby","Xenos","semper@outlook.ca",null,"51 cki ctqewjjvim","26764","Saint-Jalles","MTB79WPE3SN",null,null);
			//Utilisateur utilisateur5 = utilisateurManager.inscrire(utilisateur4);
			//System.out.println("inscription :" +utilisateur5);
			//Test selection par email et mdp si l'email n'existe pas -- Résultat retourne null -- Exception à gérer
			Utilisateur utilisateur6 = utilisateurManager.selectionnerEmailMdp("bob@lep.fr", "leponge");
			System.out.println("select_by_email :" +utilisateur6);
			//Test selection par email et mdp si le mot de passe est incorrect -- Résultat retourne null -- Exception à gérer
			Utilisateur utilisateur8 = utilisateurManager.selectionnerEmailMdp("bob@leponge.fr", "lenge");
			System.out.println("select_by_email :" +utilisateur8);
			//Test selection par email et mdp si le pseudo n'existe pas -- Résultat retourne null -- Exception à gérer
			Utilisateur utilisateur7 = utilisateurManager.selectionnerPseudoMdp("boi", "leponge");
			System.out.println("select_by_pseudo :" +utilisateur7);
			//Test selection par email et mdp si le pseudo n'existe pas -- Résultat retourne null -- Exception à gérer
			Utilisateur utilisateur9 = utilisateurManager.selectionnerPseudoMdp("bobby", "lepoe");
			System.out.println("select_by_pseudo :" +utilisateur9);
		} catch (BusinessException e) {
	
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
