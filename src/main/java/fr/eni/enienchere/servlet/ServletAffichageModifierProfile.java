/*
 * 02/06/23 CCN : Modification de la servlet pour permettre l'affichage des valeurs de sessions dans la JSP
 */

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
 * Servlet implementation class ServletAffichageModifierProfile
 */
@WebServlet("/modification")
public class ServletAffichageModifierProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	


	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			RequestDispatcher rd = null;
			HttpSession session = request.getSession();//A vérifier si ça sert
			Utilisateur utilisateur = (Utilisateur) session.getAttribute("userConnected");///A vérifier si ça sert
			//Pour test en console
			//System.out.println(utilisateur);
			
			//On redirige vers la page de modification du profil
			rd = request.getRequestDispatcher("WEB-INF/jsp/JSPModifierMonProfile.jsp");
			rd.forward(request, response);
	}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("Je suis là");
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		String pseudo       = request.getParameter("pseudo");
		//System.out.println(pseudo);
        String nom          = request.getParameter("nom");
        String prenom       = request.getParameter("prenom");
        String email        = request.getParameter("email");
        String telephone    = request.getParameter("telephone");
        //System.out.println(telephone);
        String rue          = request.getParameter("rue");
        String codePostal   = request.getParameter("codePostal");
        String ville        = request.getParameter("ville");
        String motDePasse   = request.getParameter("mdp");
        String nouveauMdp   = request.getParameter("nouveauMdp");
        //System.out.println("nouveau" + nouveauMdp);
        String confirmationMdp = request.getParameter("confirmationMdp");
        
       
        UtilisateurManager utilisateurManager = new UtilisateurManager();
        BusinessException businessException = new BusinessException();
        Utilisateur utilisateur;
        
        int id = ((Utilisateur) session.getAttribute("userConnected")).getNoUtilisateur();
        
        try {
        	 //On vérifie d'abord si le mot de passe correspond à l'utilisateur.
			utilisateur = utilisateurManager.selectionnerParIdMdp(id, motDePasse); //Si il n'y a pas de correspondance, message d'erreur
			//System.out.println(utilisateur);
			if(!nouveauMdp.isEmpty()) { //Si le nouveau mot de passe n'est pas vide, on vérifie qu'il est egal à la confirmation sinon message d'erreur.
				if(nouveauMdp != confirmationMdp) {
					businessException.ajouterErreur(CodesResultatServlet.MDP_NON_IDENTIQUE_ECHEC);
					throw businessException;
				}//Il manque une action à faire ici dans le cas où les deux mots de passe correspondent.
			}else { 
				//On doit maintenant insérer sans modifier les crédits et le role.
				
				Utilisateur utilisateurModif = new Utilisateur(utilisateur.getNoUtilisateur(), pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse, utilisateur.getCredit(), utilisateur.getAdministrateur());
				utilisateurManager.modifierUtilisateur(utilisateurModif);
				request.setAttribute("utilisateur", utilisateurModif);
				session.setAttribute("userConnected",utilisateurModif);
				//System.out.println(utilisateurModif);

				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/JSPMonProfile.jsp");
				rd.forward(request, response);
			}
			
			
		} catch (BusinessException e) {
			e.printStackTrace();
			List<Integer> listeErreursModification = e.getListeCodesErreur();
			request.setAttribute("listeErreursInscription", listeErreursModification);
			//On envoie à la JSP
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/JSPModifierMonProfile.jsp");
			rd.forward(request, response);
		}
        
	}
	/*	request.setCharacterEncoding("utf-8");
		
		String pseudo = request.getParameter("pseudo");
		String prenom = request.getParameter("prenom");
		String telephone = request.getParameter("telephone"); 
		String codePostal = request.getParameter("codepostal"); 
		String motDePass = request.getParameter("password");
		String nouveauMotDePass = request.getParameter("newPassword");
		String confirmationNouveauMotDePass = request.getParameter("confirmNewPassword");
		String nom = request.getParameter("nom");
		String email = request.getParameter("email"); 
		String rue = request.getParameter("rue"); 
		String ville = request.getParameter("ville");
		
		System.out.println(pseudo);
		System.out.println(prenom);
		System.out.println(telephone);
		System.out.println(codePostal);
		System.out.println(motDePass);
		System.out.println(nouveauMotDePass);
		System.out.println(confirmationNouveauMotDePass);
		System.out.println(nom);
		System.out.println(email);	
		 
*/
	
}