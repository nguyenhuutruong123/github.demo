package geso.dms.distributor.servlets.ctkmkhachhang;

import geso.dms.distributor.beans.ctkmkhachhang.ICtkmkhachhang;
import geso.dms.distributor.beans.ctkmkhachhang.ICtkmkhachhangList;
import geso.dms.distributor.beans.ctkmkhachhang.imp.Ctkmkhachhang;
import geso.dms.distributor.beans.ctkmkhachhang.imp.CtkmkhachhangList;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class CtkmkhachhangUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
   
    public CtkmkhachhangUpdateSvl() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		
		  ICtkmkhachhang obj; 
		   request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    PrintWriter out = response.getWriter();
		    	    
		        

		    Utility util = new Utility();
		    out = response.getWriter();
		    
		    String querystring = request.getQueryString();
//		    out.println(action);
		    
		    userId = util.getUserId(querystring);
		        
		    if (userId.length()==0)
		    	userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
		    String Id = util.getId(querystring);  	
		    
		    
		    obj = new Ctkmkhachhang();
		    String[] nhom = Id.split("-");
		    obj.setId(nhom[1]);
		    obj.setCtkmId(nhom[0]);
		    
		    obj.setuserId(userId);
		    obj.init();
			session.setAttribute("obj", obj);
					
	    	String nextJSP = "/AHF/pages/Distributor/CtkmkhachhangUpdate.jsp";
			response.sendRedirect(nextJSP);
		}
     
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		  ICtkmkhachhang obj; 
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	   // this.out = response.getWriter();
	    obj = new Ctkmkhachhang();
		
		 String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		 
	    String Id = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id"));
	    if(Id == null)
	    	Id ="";
		obj.setId(Id);
		
		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
		if(nppId == null)
		  nppId="";
		obj.setnppId(nppId);
		userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
		if(userId==null){
		  userId ="";
		}
		obj.setuserId(userId);
		String chon = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chon"));
		if(chon == null)
			chon = "0";
		obj.setuserId(userId);
	  
		String ctkmId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ctkmid"));
		if(ctkmId == null)
			ctkmId = "0";
		obj.setCtkmId(ctkmId);
		  
		String[] ddkdIds = request.getParameterValues("ddkdIds");
	    obj.setDdkdIds(ddkdIds);
		
		String[] khachhang = request.getParameterValues("khachhang");
	    obj.setKhachhang(khachhang);
	    

	    if(action.equals("save"))
	    { 
	    	if(!obj.save())
	    	{
	    		obj.init();
	    		session.setAttribute("obj", obj);
	    				
	        	String nextJSP = "/AHF/pages/Distributor/CtkmkhachhangUpdate.jsp";
	    		response.sendRedirect(nextJSP);
	    	}
	    	else
	    	{
	    		ICtkmkhachhangList objl = new CtkmkhachhangList();
    		    objl.setuserId(userId);
    		    objl.init();
    			session.setAttribute("obj", objl);
    					
    	    	String nextJSP = "/AHF/pages/Distributor/Ctkmkhachhang.jsp";
    			response.sendRedirect(nextJSP);
	    	}
	    }
	    else
	    {
	    	obj.init();
    		session.setAttribute("obj", obj);
    				
        	String nextJSP = "/AHF/pages/Distributor/CtkmkhachhangUpdate.jsp";
    		response.sendRedirect(nextJSP);
	    }
	   
	}
	}
}
