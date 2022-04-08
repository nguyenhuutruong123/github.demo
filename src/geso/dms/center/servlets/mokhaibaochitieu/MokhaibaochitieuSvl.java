package geso.dms.center.servlets.mokhaibaochitieu;

import geso.dms.center.beans.mokhaibaochitieu.IMokhaibaochitieu;
import geso.dms.center.beans.mokhaibaochitieu.imp.Mokhaibaochitieu;
import geso.dms.center.util.Utility;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class MokhaibaochitieuSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MokhaibaochitieuSvl() {
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
	    
	    IMokhaibaochitieu mkbctBean = new Mokhaibaochitieu();
	    mkbctBean.setUserId(userId);
	    mkbctBean.init();
	    
		session.setAttribute("mkbctBean", mkbctBean);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Center/MoKhaiBaoChiTieu.jsp";
		response.sendRedirect(nextJSP);
	    
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");	    
		HttpSession session = request.getSession();
		Utility util = new Utility();
		
		IMokhaibaochitieu mkbctBean = new Mokhaibaochitieu();
		
		String userId = (String)session.getAttribute("userId");
		mkbctBean.setUserId(userId);
		
		String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
		mkbctBean.setDvkdId(dvkdId);
		
		String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
		mkbctBean.setNppId(nppId);
		
//		String year = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("year")));
//		mkbctBean.setYear(year);
		
//		String month = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("month")));
//		mkbctBean.setMonth(month);
	    
	    mkbctBean.setNppId(nppId);
	    
	    String action = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));
	    if(!action.equals("nosubmit")){
	    	mkbctBean.Execute();
	    }
	    
	    mkbctBean.init();
	    
		session.setAttribute("mkbctBean", mkbctBean);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Center/MoKhaiBaoChiTieu.jsp";
		response.sendRedirect(nextJSP);
	    
	}
	

}
