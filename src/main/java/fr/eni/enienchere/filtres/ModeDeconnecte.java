package fr.eni.enienchere.filtres;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class ModeDeconnecte
 */
@WebFilter(dispatcherTypes = {
				DispatcherType.REQUEST, 
				DispatcherType.FORWARD, 
				//DispatcherType.INCLUDE, 
				//DispatcherType.ERROR
		}
					, urlPatterns = { "/*" })
public class ModeDeconnecte implements Filter {

    /**
     * Default constructor. 
     */
    public ModeDeconnecte() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
		public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpSession session = httpRequest.getSession();
			if(session.getAttribute("userConnected")!=null) {
				chain.doFilter(request, response);
			}else {
				if ((httpRequest.getServletPath().toLowerCase().contains("modification")||
					 httpRequest.getServletPath().toLowerCase().contains("profil")||
					 httpRequest.getServletPath().toLowerCase().contains("deconnexion")||
					 httpRequest.getServletPath().toLowerCase().contains("supprimermoncompte")||
					 httpRequest.getServletPath().toLowerCase().contains("vendre")||
					 httpRequest.getServletPath().toLowerCase().contains("filtre"))){
					//System.out.println(httpRequest.getServletPath().toLowerCase());
					HttpServletResponse httpResponse = (HttpServletResponse) response;
					httpResponse.sendRedirect(httpRequest.getContextPath()+"/accueil");	
					return;
				}else {
					chain.doFilter(request, response);
				}
			}
		
		}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
