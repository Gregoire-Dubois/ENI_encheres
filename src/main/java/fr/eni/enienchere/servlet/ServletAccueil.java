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
		 HttpSession session = request.getSession();
		 List<Categorie> listeCategorie = new ArrayList<>();
		 ArrayList<ArticleVendu> xArrayList  = new ArrayList<>();
		try {
			ArticleManager tousArticles = new ArticleManager();		
			
			xArrayList = tousArticles.selectionnerArticles();
			
	       
	        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
	       	CategorieManager categorieManager = new CategorieManager();
	       	listeCategorie = categorieManager.selectAll();
		} catch (BusinessException e) {

			e.printStackTrace();
			List<Integer> listeErreurs = e.getListeCodesErreur();
			request.setAttribute("listeErreurs", listeErreurs);
			

		}	
		
		
       	session.setAttribute("listeCategorie",listeCategorie);
       	
       	
		session.setAttribute("articles", xArrayList);
		
       	
       	
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/JSPAccueil.jsp");
		rd.forward(request, response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String mot = request.getParameter("rechercher");
		System.out.println(mot);
		String categorie = request.getParameter("categorie");
		HttpSession session = request.getSession();
		
		try {
			List<ArticleVendu> listeArticles = new ArrayList<>();
			ArticleManager articleManager = new ArticleManager();
			listeArticles=articleManager.selectionnerArticlesFiltres("EC", mot, categorie);
			
			if(!listeArticles.isEmpty()) {
				for(ArticleVendu a : listeArticles) {
					System.out.println(a);
				}
				session.setAttribute("articles", listeArticles);
				//response.sendRedirect(request.getContextPath()+"/accueil");
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/JSPAccueil.jsp");
				rd.forward(request, response);
			}else {
				System.out.println("Liste null");
				BusinessException businessException = new BusinessException();
				businessException.ajouterErreur(CodesResultatServlet.PAS_D_ARTICLES_ERREUR);
				throw businessException;
			}
			
		} catch (BusinessException e) {
			e.printStackTrace();
			List<Integer> listeErreurs = e.getListeCodesErreur();
			request.setAttribute("listeErreurs", listeErreurs);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/JSPAccueil.jsp");
			rd.forward(request, response);
		}
		
		
	}

}
