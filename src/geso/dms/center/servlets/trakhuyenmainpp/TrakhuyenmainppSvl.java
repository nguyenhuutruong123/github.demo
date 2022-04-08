package geso.dms.center.servlets.trakhuyenmainpp;

import geso.dms.center.beans.trakhuyenmai.imp.TrakhuyenmaiList;
import geso.dms.center.beans.trakhuyenmainpp.ITrakhuyenmainpp;
import geso.dms.center.beans.trakhuyenmainpp.ITrakhuyenmainppList;
import geso.dms.center.beans.trakhuyenmainpp.imp.Trakhuyenmainpp;
import geso.dms.center.beans.trakhuyenmainpp.imp.TrakhuyenmainppList;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TrakhuyenmainppSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
       PrintWriter out;    
       
    public TrakhuyenmainppSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String trakmId = util.getId(querystring);

	  /*  if (action.equals("delete")){	
	    	Delete(trakmId);
	    	out.print(trakmId);
	    }
	*/
	    ITrakhuyenmainppList obj;
	    obj = new TrakhuyenmainppList();
	    obj.setUserId(userId);
	    obj.init("");
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Center/TraKhuyenMaiNpp.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    
		
		ITrakhuyenmainppList obj;
		request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    HttpSession session = request.getSession();
		    PrintWriter out = response.getWriter();
		    
		    Utility util = new Utility();
		    
		    obj = new TrakhuyenmainppList();
		       String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userid")));
		       obj.setUserId(userId);
		      
		       String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		       if(diengiai == null)
		    	   diengiai ="";
		       obj.setDiengiai(diengiai);
		       
		       String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
		       if(tungay == null)
		    	   tungay ="";
		       obj.setTungay(tungay);
		       
		       String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
		       if(denngay == null)
		    	   denngay ="";
		    	obj.setDenngay(denngay);
		     	
		    	String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		    	
		    if(action.equals("Tao moi"))
		    {
		    ITrakhuyenmainpp objj = new Trakhuyenmainpp();
		    
		    userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userid")));
		    obj.setUserId(userId);
		    objj.init();
		    session.setAttribute("obj",objj);
		    String nextJSP = "/AHF/pages/Center/TraKhuyenMaiNppNew.jsp";
			response.sendRedirect(nextJSP);
		    }
		    else
		    {
		    	// obj = new TrakhuyenmainppList();
		 	    obj.setUserId(userId);
		 	    obj.init("");
		 		session.setAttribute("obj", obj);
		 				
		 		String nextJSP = "/AHF/pages/Center/TraKhuyenMaiNpp.jsp";
		 		response.sendRedirect(nextJSP);
		    }
		    
	}

}
