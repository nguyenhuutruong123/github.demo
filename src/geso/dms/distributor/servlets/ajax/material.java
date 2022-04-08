package geso.dms.distributor.servlets.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import geso.dms.center.db.sql.dbutils;

 public class material extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
	public material() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    ResultSet rs;
	    String ma = request.getQueryString();
	    
	    dbutils db = new dbutils();
//	    if(ma != null){
//	    	rs = db.get("select ma, ten from sanpham where ma like '%" + ma + "%'");
//	    }else{
	    	rs = db.get("select ma, ten from sanpham order by ma");
//	    }
	    String masp ="";
	    try{
	    	while(rs.next()) {
	    		out.println(rs.getString("ma") + "  " + rs.getString("ten"));
	    		
	    	}
	    	
	    }catch(Exception e){}
	    
	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    	        
//	    String ma = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("masp"));
	    dbutils db = new dbutils();
	    ResultSet rs = db.get("select ma, ten from sanpham");
	    try{
	    	while(rs.next()) {
	    		String masp = rs.getString("ma") + "  " + rs.getString("ten");
	    		out.println(masp);
	    	}
	    }catch(Exception e){}

	}   	  	    
}