package geso.dms.center.servlets.reports;

import geso.dms.center.util.Utility;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.WritableWorkbook;


public class DailyPrimarySalesSvl extends HttpServlet {
	
    public DailyPrimarySalesSvl() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
//	    PrintWriter out = response.getWriter();
	    	    
	   //HttpSession session = request.getSession();	    
       Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId3 = util.getUserId(querystring);
	    System.out.println("chuoi truyen qua:"+userId3);
	   // String id = util.getId(querystring);  	
 
	    //String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	   // String userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
//		OutputStream outstream = null;
		try
		{
			File inputWorkbook = new File("D:\\reports\\Planning\\"+userId3);
			
			Workbook w = Workbook.getWorkbook(inputWorkbook);

			WorkbookSettings wbSettings = new WorkbookSettings();

			wbSettings.setLocale(new Locale("en", "EN"));			
			response.setContentType("application/vnd.ms-excel");
		    response.setHeader("Content-Disposition", "attachment; filename=DailyPrimarySales.xls");
		    response.setLocale(new Locale("en", "EN"));	
		    response.flushBuffer();
		    ServletOutputStream outstream = response.getOutputStream();
		    
//		    out = response.getOutputStream();
			WritableWorkbook workbook = Workbook.createWorkbook(outstream, w);
			
			workbook.write();		
			workbook.close();
		}
		catch(jxl.JXLException ex)
		{
			System.out.print("Exception...");
		}
		finally
	    {
	     
	    }
		

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
