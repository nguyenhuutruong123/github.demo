package geso.dms.center.servlets.dangkykhuyenmaitichluy;

import geso.dms.center.beans.dangkykhuyenmaitichluy.IDangkykhuyenmaitichluyTT;
import geso.dms.center.beans.dangkykhuyenmaitichluy.IDangkykhuyenmaitichluyTTList;
import geso.dms.center.beans.dangkykhuyenmaitichluy.imp.DangkykhuyenmaitichluyTT;
import geso.dms.center.beans.dangkykhuyenmaitichluy.imp.DangkykhuyenmaitichluyTTList;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DangkykhuyenmaitichluyTTSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public DangkykhuyenmaitichluyTTSvl() {
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
		
		IDangkykhuyenmaitichluyTTList obj;
		PrintWriter out; 
		out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    //String id = util.getId(querystring);  	
	    obj  = new DangkykhuyenmaitichluyTTList();
	    obj.setUserId(userId);
	    obj.init();
        session.setAttribute("obj",obj);
        String nextJSP = "/AHF/pages/Center/DangKyKhuyenMaiTichLuyTT.jsp";
        response.sendRedirect(nextJSP);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}
		else{
			IDangkykhuyenmaitichluyTTList obj;
			PrintWriter out; 

			out = response.getWriter();
			Utility util = new Utility();
		  	obj  = new DangkykhuyenmaitichluyTTList();
			   
		  	obj.setUserId(userId);
			    
		    userId= util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		    obj.setUserId(userId);
		    
		    String diengiai =  util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		    if(diengiai == null)
		    	diengiai ="";
		    obj.setDiengiai(diengiai);
		    System.out.println("Diengiai "+diengiai);
		    
		    String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
		    if(tungay == null)
		    	tungay ="";
		    obj.setTungay(tungay);
		    
		    String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
		    if(denngay == null)
		    	denngay ="";
		    obj.setDenngay(denngay);
		    
		    String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
		    if(trangthai == null)
		    	trangthai ="";
		    obj.setTrangthai(trangthai);
		    
		    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		    
		    obj.init();
		    System.out.println("Action :"+action);
		    if(action.equals("new"))
		    { 
		    	IDangkykhuyenmaitichluyTT objj = new DangkykhuyenmaitichluyTT();
		    	objj.setUserId(userId);
		    	objj.createRs();
	    	    session.setAttribute("obj",objj);
		        String nextJSP = "/AHF/pages/Center/DangKyKhuyenMaiTichLuyTTNew.jsp";
		        response.sendRedirect(nextJSP);    	
		    }
		    else
		    {	
		    	session.setAttribute("obj",obj);
		    	
		    	String nextJSP = "/AHF/pages/Center/DangKyKhuyenMaiTichLuyTT.jsp";
		    	response.sendRedirect(nextJSP);
		    }
			
	}}

}
