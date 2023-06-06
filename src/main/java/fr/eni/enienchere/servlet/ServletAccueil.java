package fr.eni.enienchere.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.enienchere.BusinessException;
import fr.eni.enienchere.bll.ArticleManager;
import fr.eni.enienchere.bll.CategorieManager;
import fr.eni.enienchere.bll.UtilisateurManager;
import fr.eni.enienchere.bo.ArticleVendu;
import fr.eni.enienchere.bo.Categorie;
import fr.eni.enienchere.bo.Utilisateur;

/**
 * Servlet implementation class ServletAccueil
 */
@WebServlet("/accueil")
public class ServletAccueil extends HttpServlet {
	private static final long serialVersionUID = 1L;
    CategorieManager categorieManager = null;
    //RetraitManager retraitManager = null;
    UtilisateurManager utilisateurManager = null;
    //ArticleManager articleManager = null;
    //EnchereManager enchereManager = null
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAccueil() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
		// affichage des articles en vente lors d'une ouverture sans indentification
		
		ArticleManager tousArticles = new ArticleManager();		
		ArrayList<ArticleVendu> xArrayList  = new ArrayList<>();
		
		try {
			xArrayList = tousArticles.selectionnerArticles();

			
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			List<Integer> listeErreurs = e.getListeCodesErreur();
			request.setAttribute("listeErreurs", listeErreurs);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/JSPAccueil.jsp");

		}	
		
		
		//Affiche de la liste des catégories
		List<Categorie> listeCategorie = new ArrayList<>();
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
       	categorieManager = new CategorieManager();
       	try {
			listeCategorie = categorieManager.selectAll();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       	session.setAttribute("listeCategorie",listeCategorie);
       	
       	// affichage de liste des articles sans identification de l'utilisateur       	
		session.setAttribute("articles", xArrayList);
		//getServletContext().getRequestDispatcher("/WEB-INF/jsp/JSPAccueil.jsp").forward(request, response);	
       	
       	
       	
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/JSPAccueil.jsp");
		rd.forward(request, response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
	}

}
