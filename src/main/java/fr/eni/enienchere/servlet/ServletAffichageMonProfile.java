package fr.eni.enienchere.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.enienchere.BusinessException;
import fr.eni.enienchere.bll.UtilisateurManager;
import fr.eni.enienchere.bo.Utilisateur;
import fr.eni.enienchere.dal.UtilisateurDAOJdbcImpl;

/**
 * Servlet implementation class ServletAffichageMonProfile
 */
@WebServlet("/ServletAffichageMonProfile")
public class ServletAffichageMonProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = null;
		
		Utilisateur user = new Utilisateur();
		UtilisateurDAOJdbcImpl userDaoJdbcImpl = new UtilisateurDAOJdbcImpl();
		//System.out.println(user.toString());
/*		
		try {
			//user = userDaoJdbcImpl.selectById(4);
//			System.out.println("----------->"+user);
			//request.setAttribute("user", user);	
			//request.getSession().setAttribute("user", user);
			//getServletContext().getRequestDispatcher("/WEB-INF/JSPMonProfile.jsp").forward(request, response);
			//getServletContext().getRequestDispatcher("/WEB-INF/JSPMonProfile.jsp").forward(request, response);
			
			//rd = request.getRequestDispatcher("WEB-INF/JSPMonProfile.jsp");
			//rd.forward(request, response);
			
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
*/
		
		request.setAttribute("user", user);
		
		//request.getSession().setAttribute("user", user);
		rd = request.getRequestDispatcher("/WEB-INF/JSPMonProfile.jsp");
		rd.forward(request, response);
		
		
		
		//user = UtilisateurManager
		
		
		
		
		
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}