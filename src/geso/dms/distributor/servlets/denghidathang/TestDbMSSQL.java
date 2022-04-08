package geso.dms.distributor.servlets.denghidathang;

import java.io.IOException;
//import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestDbMSSQL extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
	public TestDbMSSQL() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
//	    PrintWriter out = response.getWriter();
	   	    
	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
/*	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private String getDauky(String date){
		String dauky = "0";
		
	    String year = date.substring(0, 4);
		String month = date.substring(5, 7);
		String day = date.substring(8, 10);
						
		return dauky;
	}
	
	private String getCuoiky(String date){
		String dauky = "0";
		
		
		return dauky;
	}*/
	
}