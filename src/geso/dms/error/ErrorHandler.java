package geso.dms.error;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ErrorHandler extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

    public ErrorHandler() {
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			
		// Analyze the servlet exception       
	      Throwable throwable = (Throwable)request.getAttribute("javax.servlet.error.exception");
	      Integer statusCode = (Integer)
	      request.getAttribute("javax.servlet.error.status_code");
	      String servletName = (String)request.getAttribute("javax.servlet.error.servlet_name");
	      if (servletName == null){
	         servletName = "Unknown";
	      }
	      
	      String requestUri = (String)request.getAttribute("javax.servlet.error.request_uri");
	      if (requestUri == null){
	         requestUri = "Unknown";
	      }

	      // Set response content type
	      response.setContentType("text/html");
	 
	      PrintWriter out = response.getWriter();
		  String title = "Error/Exception Information";
	      String docType =
	      "<!doctype html public \"-//w3c//dtd html 4.0 " +
	      "transitional//en\">\n";
	      out.println(docType +
	        "<html>\n" +
	        "<head><title>" + title + "</title></head>\n" +
	        "<body>\n");

	      if (throwable == null && statusCode == null)
	      {
	         out.println("<h2>Error information is missing</h2>");
	         out.println("Please return to the <a href=\"" + 
	           response.encodeURL("http://localhost:8080/") + "\">Home Page</a>.");
	      }
	      else if (statusCode != null)
	      {
	         //out.println("The status code : " + statusCode);
	    	 /* String table = "<table width='99%' border='0' cellspacing='0' cellpadding='0' style='padding-left:2px;' >" +
	    	  					"<tr height='22px'>" +
	    	  						"<td colspan='2' style='background-color: #80CB9B; font-family: Arial, Helvetica, sans-serif; " +
	    	  									"font-size: 9pt; font-weight: bold'>&nbsp; Http 404 Exception</td> " +
	    	  					"</tr>" +
	    	  					"<tr>" +
	    	  						"<td style='vertical-align:top;' width='410px'><img src='/AHF/pages/images/Loi1.png' /><br />" +
	    	  						 									"<img src='/AHF/pages/images/Loi2.png' /></td>" +
	    	  						 "<td style='vertical-align:top;' ><img src='/AHF/pages/images/404.jpg' /></td>" +
	    	  					"</tr>";*/
	    	  
	    	   String table = "<table width='99%' border='0' cellspacing='0' cellpadding='0' style='padding-left:2px;' >" +
								"<tr height='22px'>" +
									"<td style='background-color: #80CB9B; font-family: Arial, Helvetica, sans-serif; " +
												"font-size: 9pt; font-weight: bold'>&nbsp; Http 404 Exception</td> " +
								"</tr>" +
								"<tr>" +
									 "<td style='vertical-align:top;' ><img src='/AHF/pages/images/404.jpg' /></td>" +
								"</tr>";
	    	  		 
	    	  table += "</table>";
	    	 /* out.println("<b style='font-size: 2.0em'>ERROR...</b><hr>" +
	    	  				"<p  style='font-size: 1.2em'>Chúng tôi không thể xử lý yêu cầu của bạn, vui lòng Refresh lại trình duyệt và thử lại. <br/>" +
	    	  				"<br>Nếu vẫn xảy ra lỗi trên, vui lòng liên hệ Admin để giải quyết.<br /><br /><br /> " +
	    	  				"Cảm ơn bạn.<br></p>");*/
	    	  out.println(table);
	    	  //response.sendRedirect("/AHF/error_page.jsp");
	      }
	      else
	      {
	         out.println("<h2>Error information</h2>");
	         out.println("Servlet Name : " + servletName + 
	                             "</br></br>");
	         out.println("Exception Type : " + 
	                             throwable.getClass( ).getName( ) + 
	                             "</br></br>");
	         out.println("The request URI: " + requestUri + 
	                             "<br><br>");
	         out.println("The exception message: " + 
	                                 throwable.getMessage( ));
	      }
	      out.println("</body>");
	      out.println("</html>");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		 doGet(request, response);
	}


}
