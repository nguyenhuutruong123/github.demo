package demo.test;

import geso.dms.center.util.Csrf;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthFilter implements Filter{
	
	
	
	public AuthFilter() {
    }
 
    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        //System.out.println("AuthFilter init!");
    }
 
    @Override
    public void destroy() {
        //System.out.println("AuthFilter destroy!");
    }
 
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    	HttpServletRequest req = (HttpServletRequest) request;

    	Csrf csrf = new Csrf( (HttpServletRequest)request, (HttpServletResponse)response, true, true, true);

    	String _token = (String) req.getParameter("_token");

    	//System.out.println("AuthFilter doFilter: " + _token);

    	//System.out.println(((HttpServletRequest)request).getServletPath());

    	String path = ((HttpServletRequest) request).getRequestURI();
    	if (path.startsWith("/LoginSvl")) {
    		chain.doFilter(request, response); // Just continue chain.
    		return;
    	}

    	/* if (((HttpServletRequest)request).getServletPath().equals("/index.jsp")){
        	chain.doFilter(request, response);
        	return;
        }*/


    	/*  if(req.getContextPath().equals("/TNI")){
        	chain.doFilter(request, response);
        	return;
        }*/

//    	if(!csrf.__validate_post()){
//
//    		//throw new ServletException("Potential CSRF detected!! Inform a scary sysadmin ASAP.");
//    	}
//
//    	if(!csrf.__validate_get()){
//
//    		//throw new ServletException("Potential CSRF detected!! Inform a scary sysadmin ASAP.");
//    	}

    	chain.doFilter(request, response);
    }
}
