package geso.dms.center.servlets.mokhaibaotrungbay;

import geso.dms.center.util.Utility;
import geso.dms.center.beans.mokhaibaotrungbay.*;
import geso.dms.center.beans.mokhaibaotrungbay.imp.*;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MokhaibaotrungbaySvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public MokhaibaotrungbaySvl() {
        super();
 
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utility util = new Utility();
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    IMokhaibaotrungbay mkbtbBean = new Mokhaibaotrungbay();
	    mkbtbBean.setUserId(userId);
	    mkbtbBean.init();
	    
		session.setAttribute("mkbtbBean", mkbtbBean);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Center/MoKhaiBaoTrungBay.jsp";
		response.sendRedirect(nextJSP);
	    
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");	    
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("userId");
		
		String schemeId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("schemeId"));
		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));

		IMokhaibaotrungbay mkbtbBean = new Mokhaibaotrungbay();
	    mkbtbBean.setUserId(userId);
	    mkbtbBean.setSchemeId(schemeId);
	    mkbtbBean.setNppId(nppId);
	    mkbtbBean.Execute();
	    
	    mkbtbBean.init();
	    
		session.setAttribute("mkbtbBean", mkbtbBean);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Center/MoKhaiBaoTrungBay.jsp";
		response.sendRedirect(nextJSP);
	    
	}
	

}
