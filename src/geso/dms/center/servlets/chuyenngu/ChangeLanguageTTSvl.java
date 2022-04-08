package geso.dms.center.servlets.chuyenngu;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import geso.dms.center.util.*;
import javax.servlet.http.HttpServlet;
public class ChangeLanguageTTSvl extends HttpServlet
{
    static final long serialVersionUID = 1L;
    
    public ChangeLanguageTTSvl() 
    {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");

		Utility util = new Utility();
   		String nnId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Id")));
   		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
   		
   		HttpSession session = request.getSession();
   		String userTen = (String) session.getAttribute("userTen");
   		String isTT = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("isTT")));
   		
   		System.out.println("nnId:" + nnId);
   		System.out.println("userId:" + userId);
   		System.out.println("userTen:" + userTen);
   		
   		
   		session.setAttribute("nnId", nnId );
   		session.setAttribute("userId", userId);	
   		session.setAttribute("userTen", null);
   		session.setAttribute("userTen", userTen);
		session.setAttribute("sum", util.calSum(userId, userTen));
		session.setAttribute("util", util);
		session.setMaxInactiveInterval(30000);
		
		System.out.println("isTT: "+isTT);
		String nextJSP = "";
		if (isTT != null && isTT.equals("0")) {
			nextJSP = "/AHF/Distributor/mainpage.jsp";
		}
		else {
			nextJSP = "/AHF/Center/mainpage.jsp";
		}
		response.sendRedirect(nextJSP);
	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
	}
	
	private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}