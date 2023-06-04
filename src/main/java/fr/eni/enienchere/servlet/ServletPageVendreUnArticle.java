package fr.eni.enienchere.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.enienchere.bo.ArticleVendu;
import fr.eni.enienchere.bo.Categorie;
import fr.eni.enienchere.bo.Retrait;
import fr.eni.enienchere.bo.Utilisateur;
import fr.eni.enienchere.BusinessException;
import fr.eni.enienchere.bll.ArticleManager;
import fr.eni.enienchere.bll.CategorieManager;
import fr.eni.enienchere.bll.UtilisateurManager;


/**
 * Servlet implementation class ServletPageVendreUnArticle
 */
@WebServlet("/vendre")
public class ServletPageVendreUnArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
    CategorieManager categorieManager = null;
    //RetraitManager retraitManager = null;
    UtilisateurManager utilisateurManager = null;
    //ArticleManager articleManager = null;
    //EnchereManager enchereManager = null
  

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Import de la liste de catégorie pour la transmettre à la JSP
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
       	
       	
       	
       	
		RequestDispatcher rd = null; 
		rd = request.getRequestDispatcher("WEB-INF/jsp/JSPPageVendreUnArticle.jsp");
		rd.forward(request, response);
	
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		//On récupère les données
		String nomArticle = request.getParameter("article");
		System.out.println(nomArticle);
		String description = request.getParameter("description");
		System.out.println(description);
		int categorieId = Integer.parseInt(request.getParameter("categorie"));
		
		System.out.println(categorieId);
		
		Categorie categorie = new Categorie();
		categorie.setId(categorieId);

		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //Format en base à vérifier, ok je pense
		LocalDate dateDebutEncheres = LocalDate.parse(request.getParameter("debutEnchere"), dtf);
		System.out.println(dateDebutEncheres);
		
		LocalDate dateFinEncheres = LocalDate.parse(request.getParameter("finEnchere"), dtf);
		System.out.println(dateFinEncheres);
		int prixInitial = Integer.parseInt(request.getParameter("prixDepart"));
		System.out.println(prixInitial);
		String rueRetrait = request.getParameter("rue");
		
		String codePostalRetrait = request.getParameter("codePostal");
		String villeRetrait = request.getParameter("ville");
		
		Utilisateur vendeur = new Utilisateur();
		vendeur = (Utilisateur) session.getAttribute("userConnected");
		System.out.println(vendeur);
		
		
		Retrait retrait = new Retrait(rueRetrait, codePostalRetrait, villeRetrait);
		System.out.println(retrait);
		
		//On fait appel à la méthode ajouter de ArticleManager pour effectuer une vérification des données (à faire) avant entrée en bdd
		ArticleManager articleManager = new ArticleManager();
		
		try {
			ArticleVendu article = articleManager.ajouter(nomArticle, description, dateDebutEncheres, dateFinEncheres, prixInitial, categorie, retrait, vendeur);
			System.out.println(article);
			//Test
			List<ArticleVendu> test = vendeur.getListeVentes();
			if(test!=null) {
				for(ArticleVendu a:test) {
					System.out.println("test" + a);
				}
			}
			
			response.sendRedirect(request.getContextPath()+"/accueil");
		} catch (BusinessException e) {
			e.printStackTrace();
			
			//On récupère la liste d'erreurs générée plus tôt
			List<Integer> listeErreursAjoutArticle = e.getListeCodesErreur();
			
			//Pour les tests
			for(int erreur : listeErreursAjoutArticle) {
				System.out.println(erreur);
			}
			
			//On ajoute la liste dans les attributs de la requête pour les communiquer à la JSP
			request.setAttribute("listeErreursAjoutArticle", listeErreursAjoutArticle);
			
			//On envoie à la JSP
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/JSPPageVendreUnArticle.jsp");
			rd.forward(request, response);
		}
		
	}

}
