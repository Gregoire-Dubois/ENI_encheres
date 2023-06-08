package fr.eni.enienchere.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.enienchere.bo.ArticleVendu;
import fr.eni.enienchere.bo.Enchere;
import fr.eni.enienchere.bo.Utilisateur;
import fr.eni.enienchere.BusinessException;
import fr.eni.enienchere.bll.ArticleManager;
import fr.eni.enienchere.bll.EnchereManager;
import fr.eni.enienchere.bll.UtilisateurManager;
import fr.eni.enienchere.dal.ArticleDAOJdbcImpl;
import fr.eni.enienchere.dal.DAOFactory;

/**
 * Servlet implementation class ServletFaireUneEnchere
 */
@WebServlet("/encherir")
public class ServletFaireUneEnchere extends HttpServlet {
	private static final long serialVersionUID = 1L;
	EnchereManager enchereManager = null;
	UtilisateurManager utilisateurManager = null;
	ArticleManager articleManager = null;
	   
    @Override
    public void init() throws ServletException {
        super.init();
        enchereManager = EnchereManager.getInstance();
        utilisateurManager = UtilisateurManager.getInstance();
        articleManager = ArticleManager.getInstance();
    }

/**
    	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    	 */
    	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		
    		HttpSession session = request.getSession();
    		
    		RequestDispatcher rd = null;
    		ArticleDAOJdbcImpl etatVente = new ArticleDAOJdbcImpl();
    		int id = Integer.parseInt(request.getParameter("idArticle")) ;
    		String ev = etatVente.idArticleInList(id);  	  
    		session.setAttribute("etatVente", ev);
    		session.setAttribute("idArticle", id);
    		
    		
    		Enchere enchere = new Enchere();
    		try {
				enchere = EnchereManager.getInstance().selectEnchereMaxByArticle(id);
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		if (enchere != null){ 
    		request.setAttribute("enchere", enchere);
    		} else {
    			ArticleVendu article = new ArticleVendu();
        		try {
    				article = articleManager.selectArticleById(id);
    			} catch (BusinessException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			request.setAttribute("article", article);
    			//enchere = null;
    		}
    		rd = request.getRequestDispatcher("/WEB-INF/jsp/tests/JSPencherir.jsp");
    		rd.forward(request, response); 
    		
    	}

    	/**
    	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
    	 */
    	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		HttpSession session = request.getSession();
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("userConnected"); 
            String message="";
            int montantEnchere = Integer.parseInt( request.getParameter("montantEnchere"));
            System.out.println("montantEnchere vaut " + montantEnchere);
            int idArticle = Integer.parseInt(request.getParameter("idArticle"));
            System.out.println("Larticle est :" + idArticle);
            //int idUtilisateur = Integer.parseInt(request.getParameter("no_utilisateur"));
            int idUtilisateur = utilisateur.getNoUtilisateur(); 
            Enchere enchereRetourner = null;
            Utilisateur utilisateurRenvoyer = null;
            Enchere enchere = null;
            Utilisateur acheteur = null;
            

                try {
                	enchere = enchereManager.selectEnchereMaxByArticle(idArticle);
                	if (enchere == null) {
                		ArticleVendu article = articleManager.selectArticleById(idArticle);
                		enchere = new Enchere(article,utilisateur,LocalDateTime.now(),montantEnchere);
                	}
                    acheteur = utilisateurManager.selectionner(idUtilisateur);
                    System.out.println("Enchere vaut " + enchere);
                    System.out.println("L'acheteur est :" + acheteur);
                } catch (BusinessException e) {
                    e.printStackTrace();
                }
              //prix de vente est soit = au prix initial ou soit supperieur
                if((enchere.getArticle().getPrixInitial() <= enchere.getArticle().getPrixVente()) || (enchere.getArticle().getPrixVente() == 0)){
                	System.out.println("Je passe dans le premier if");
                    //compare le montantEnchere avec le prix article
                    if (enchere.getArticle().getPrixVente() < montantEnchere){
                    	System.out.println("Je passe dans le deuxième if");
                        //controle pour savoir si l'acheteur a deja fais la derniere enchere
                        /*
                    	*if((enchere.getUtilisateur() == null) || (acheteur.getNoUtilisateur() != enchere.getUtilisateur().getNoUtilisateur())) {
                        *	System.out.println("Je passe dans le troisième if");
                        */
                        //controle pour savoir si le credit de l'utilisateur est superrieur au prix de vente
                            if(acheteur.getCredit() >= enchere.getArticle().getPrixVente()) {
                            	System.out.println("J'arrive au try !");
                                try {
                                        enchereRetourner = enchereManager.insertNewEnchere(acheteur,idArticle,montantEnchere);
                                        System.out.println("J'ai tenté un insert");
                                        utilisateurRenvoyer = utilisateurManager.selectionner(idUtilisateur);
                                        message="Votre enchere a reussi !";
                                        session.setAttribute("utilisateur",utilisateurRenvoyer);
                                        request.setAttribute("message_succes",message);
                                        request.setAttribute("enchere", enchereRetourner);
                                        request.getRequestDispatcher("WEB-INF/jsp/JSPAccueil.jsp").forward(request,response);
                                    } catch ( BusinessException e) {
                                        e.printStackTrace();
                                    } 
                               

                            }else {
                               System.out.println("Votre Credit est inferieur au montant de l'enchere");
                                session.setAttribute("utilisateur",acheteur);
                                request.setAttribute("enchere",enchere);
                                request.setAttribute("message",message);
                                request.getRequestDispatcher("WEB-INF/jsp/JSPencherir.jsp").forward(request,response);
                            }
                    } 
                    /*
                    else{
                            System.out.println("Vous etes deja le dernier encherisseur");
                            session.setAttribute("utilisateur",acheteur);
                            request.setAttribute("enchere",enchere);
                            request.setAttribute("message",message);
                            request.getRequestDispatcher("WEB-INF/jsp/tests/JSPencherir.jsp").forward(request,response);
                    }
                    
                    }*/
                    else {
                    	System.out.println("Prix de vente supperieur au montant de l'enchere");
                        session.setAttribute("utilisateur",acheteur);
                        request.setAttribute("enchere",enchere);
                        request.setAttribute("message",message);
                        request.getRequestDispatcher("WEB-INF/jsp/tests/JSPencherir.jsp").forward(request,response);
                    }
                }else{
                	System.out.println("le prix initial est supperieur aux prix de vente ");
                    session.setAttribute("utilisateur",acheteur);
                    request.setAttribute("enchere",enchere);
                    request.setAttribute("message",message);
                    request.getRequestDispatcher("WEB-INF/Vjsp/tests/JSPencherir.jsp").forward(request,response);
                }
            }
    	}
    	      
            

  
