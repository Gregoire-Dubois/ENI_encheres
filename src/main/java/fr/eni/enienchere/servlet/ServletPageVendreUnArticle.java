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

import fr.eni.enienchere.bo.Categorie;
import fr.eni.enienchere.bo.Utilisateur;
import fr.eni.enienchere.BusinessException;
import fr.eni.enienchere.bll.CategorieManager;
import fr.eni.enienchere.bll.UtilisateurManager;


/**
 * Servlet implementation class ServletPageVendreUnArticle
 */
@WebServlet("/ServletPageVendreUnArticle")
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

		doGet(request, response);
	}

}
