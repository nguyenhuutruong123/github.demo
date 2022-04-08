package geso.dms.center.servlets.duyetdonhangnpp;

import geso.dms.center.beans.duyetdontrahangnpp.IDuyetdontrahangnpp;
import geso.dms.center.beans.duyetdontrahangnpp.imp.DuyetdontrahangnppDislay;
import geso.dms.distributor.beans.donhangtrave.IDonhangtrave;
import geso.dms.distributor.beans.donhangtrave.ISanpham;
import geso.dms.distributor.beans.donhangtrave.imp.Donhangtrave;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class DuyetdontrahangnppDislaySvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
      public DuyetdontrahangnppDislaySvl() {
        super();
        // TODO Auto-generated constructor stub
    }
      
      PrintWriter out; 
  	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
		IDuyetdontrahangnpp dhtvBean;
		
		HttpSession session = request.getSession();
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String id = util.getId(querystring);

	    dhtvBean = new DuyetdontrahangnppDislay(id);
      //  dhtvBean.setUserId(userId); //phai co UserId truoc khi Init
         session.setAttribute("dhtvBean", dhtvBean);
        String nextJSP = "/AHF/pages/Center/DonTraHangVeDislaynpp.jsp";
        response.sendRedirect(nextJSP);
       
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
