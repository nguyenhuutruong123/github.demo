package geso.dms.center.servlets.TBL_PXK;


import geso.dms.center.beans.report.IBcHoaDon;
import geso.dms.center.beans.report.imp.BcHoaDon;
import geso.dms.center.db.sql.db_Sync;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TBL_PXK_Display extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public TBL_PXK_Display() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
//		PrintWriter out = response.getWriter();
		String nextJSP;
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	        
	    if (userId.length()==0)
	    	userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    
	    String id = util.getId(querystring);  	
	    String action = util.getAction(querystring);

		IBcHoaDon obj = (IBcHoaDon) new BcHoaDon();
		obj.setUserId(userId);
		
		obj.setSohoadon(id.split("-")[0]);
		obj.setSoId(id.split("-")[1]);
		obj.initDisplay();
    	session.setAttribute("obj", obj);
    	nextJSP = "/AHF/pages/Center/TBL_PXK_Display.jsp";
    	response.sendRedirect(nextJSP);    		

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
