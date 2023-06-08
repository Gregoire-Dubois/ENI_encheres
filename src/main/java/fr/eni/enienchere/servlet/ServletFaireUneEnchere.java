package fr.eni.enienchere.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

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
import fr.eni.enienchere.dal.UtilisateurDAOJdbcImpl;

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
    		/*
    		RequestDispatcher rd = null;
    		
    		ArticleDAOJdbcImpl etatVente = new ArticleDAOJdbcImpl();
    		int id = Integer.parseInt(request.getParameter("idArticle")) ;
    		String ev = etatVente.idArticleInList(id);  	  
    		session.setAttribute("etatVente", ev);
    		session.setAttribute("idArticle", id);
    		*/
    		
    		//filtrer l'affichage JSP en fonctionde l'état des vente (EN NC EV)
    		ArticleDAOJdbcImpl etatVente = new ArticleDAOJdbcImpl();
    		int id = Integer.parseInt(request.getParameter("idArticle")) ;
    		String ev = etatVente.idArticleInList(id);
    		session.setAttribute("etatVente", ev);
    		Utilisateur utilisateur = (Utilisateur) session.getAttribute("userConnected");
    		
    		//récupérer les infos d'un article pour sa page enchères
    		
    		ArticleDAOJdbcImpl detailsArticle = new ArticleDAOJdbcImpl();
    		ArticleVendu details = null;;
    		try {
    			details = detailsArticle.selectArticleById(id);
    		} catch (BusinessException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		session.setAttribute("details", details);
    		
    		//Bouton Enchérir
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
    		
    		//récupérer les infos de l'encherisseur
    		UtilisateurDAOJdbcImpl acquereur = new UtilisateurDAOJdbcImpl();
    	    Utilisateur encherisseur = null;
    	    try {
    	        encherisseur = acquereur.getEncherisseurByArticleId(id);
    	    } catch (BusinessException e) {
    	        e.printStackTrace();
    	    }
    	    session.setAttribute("encherisseur", encherisseur);
    	    
    		rd = request.getRequestDispatcher("/WEB-INF/jsp/JSPPageEncherirAcquisitionDetailMaVente.jsp");
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
                  //On récupère la liste d'erreurs générée plus tôt
        			List<Integer> listeErreursEnchere = e.getListeCodesErreur();
        			request.setAttribute("listeErreursEnchere", listeErreursEnchere);
                }
              //prix de vente est soit = au prix initial ou soit supperieur
                if((enchere.getArticle().getPrixInitial() <= enchere.getArticle().getPrixVente()) || (enchere.getArticle().getPrixVente() == 0)){
                	System.out.println("Je passe dans le premier if");
                    //compare le montantEnchere avec le prix article
                    if (enchere.getArticle().getPrixVente() < montantEnchere){
                    	System.out.println("Je passe dans le deuxième if");
                        //controle pour savoir si l'acheteur a les crédits suffisants
                        //if((acheteur.getCredit() >= montantEnchere)) {
                        //	System.out.println("Je passe dans le troisième if");
                        //controle pour savoir si le credit de l'utilisateur est superieur au prix de vente
                            if(acheteur.getCredit() >= montantEnchere) {
                            	System.out.println("J'arrive au try !");
                                try {
                                        enchereRetourner = enchereManager.insertNewEnchere(acheteur,idArticle,montantEnchere);
                                        System.out.println("J'ai tenté un insert");
                                        utilisateurRenvoyer = utilisateurManager.selectionner(idUtilisateur);
                                        session.setAttribute("utilisateur",utilisateurRenvoyer);
                                        request.setAttribute("enchere", enchereRetourner);
                                        request.getRequestDispatcher("WEB-INF/jsp/JSPAccueil.jsp").forward(request,response);
                                    } catch ( BusinessException e) {
                                        e.printStackTrace();
                                                                           } 
                               

                            }else {
                              
                            	BusinessException businessException = new BusinessException();
                            	businessException.ajouterErreur(CodesResultatServlet.CREDIT_INFERIEUR_PRIX_VENTE);
                            	List<Integer> listeErreursEnchere = businessException.getListeCodesErreur();
                    			request.setAttribute("listeErreursEnchere", listeErreursEnchere);
                                session.setAttribute("utilisateur",acheteur);
                                request.setAttribute("enchere",enchere);
                                request.getRequestDispatcher("WEB-INF/jsp/JSPEncherir.jsp").forward(request,response);
                            } 
                    } 
                    /*
                    else{
                            System.out.println("Vous etes deja le dernier encherisseur");
                            session.setAttribute("utilisateur",acheteur);
                            request.setAttribute("enchere",enchere);
                            request.getRequestDispatcher("WEB-INF/jsp/tests/JSPencherir.jsp").forward(request,response);
                    }
                    
                    }*/
                    else {
                    	//System.out.println("Prix de vente supérieur au montant de l'enchere");
                    	BusinessException businessException = new BusinessException();
                    	businessException.ajouterErreur(CodesResultatServlet.ENCHERE_INFERIEURE_PRIX_VENTE);
                    	List<Integer> listeErreursEnchere = businessException.getListeCodesErreur();
            			request.setAttribute("listeErreursEnchere", listeErreursEnchere);
                        session.setAttribute("utilisateur",acheteur);
                        request.setAttribute("enchere",enchere);
                        request.getRequestDispatcher("WEB-INF/jsp/JSPEncherir.jsp").forward(request,response);
                    }
                }else{
                	//System.out.println("le prix initial est supérieur aux prix de vente ");
                	BusinessException businessException = new BusinessException();
                	businessException.ajouterErreur(CodesResultatServlet.ENCHERE_IMPOSSIBLE);
                	List<Integer> listeErreursEnchere = businessException.getListeCodesErreur();
        			request.setAttribute("listeErreursEnchere", listeErreursEnchere);
                    session.setAttribute("utilisateur",acheteur);
                    request.setAttribute("enchere",enchere);
                    request.getRequestDispatcher("WEB-INF/jsp/JSPEncherir.jsp").forward(request,response);
                }
            }
    	}
    	      
            

  
