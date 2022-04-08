package geso.dms.distributor.servlets.Nhaphangnpp;

import geso.dms.distributor.beans.Nhaphangnpp.INhaphangnpp;
import geso.dms.distributor.beans.Nhaphangnpp.imp.Nhaphangnpp;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NhaphangnppDisplaySvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public NhaphangnppDisplaySvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{

		PrintWriter out = response.getWriter();
		String nextJSP;
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String id = util.getId(querystring);  	
	    out.println(id);
	   
	    INhaphangnpp Nhaphangnpp = (INhaphangnpp) new Nhaphangnpp();
	    Nhaphangnpp.setUserId(userId);
	    
    	Nhaphangnpp.setId(id);
    	Nhaphangnpp.init();
    	
    	if(Nhaphangnpp.getNppId()==null){
    		nextJSP = "/AHF/pages/index.jsp";
    	}else
    	{
	    nextJSP = "/AHF/pages/Distributor/NhaphangnppDisplay.jsp";
    	}	    
		session.setAttribute("Nhaphangnpp", Nhaphangnpp);			
	
		response.sendRedirect(nextJSP);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
