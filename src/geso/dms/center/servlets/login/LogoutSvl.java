package geso.dms.center.servlets.login;

import geso.dms.center.servlets.count.SessionCounter;
import geso.dms.center.util.Utility;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geso.dms.center.db.sql.*;
import geso.dms.center.util.*;

public class LogoutSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LogoutSvl() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
/*    	String userId = (String)request.getSession(false).getAttribute("userId");  
    	String userTen = (String)request.getSession(false).getAttribute("userTen");
    	String sum = (String)request.getSession(false).getAttribute("sum");
    	String site = (String)request.getSession(false).getAttribute("site");
    	Utility util = (Utility)request.getSession(false).getAttribute("util");
    	if(util.check(userId, userTen, sum)){*/
/*    		dbutils db = new dbutils();
    		String query = "update nhanvien set islogin='0', sessionid='0' where pk_seq='" + userId + "'";
    		db.update(query);
    		db.shutDown();*/
			HttpSession session = request.getSession();
			/*session.setAttribute("userId", "");  
			session.setAttribute("userTen", "");*/
			String userId=(String)session.getAttribute("userId");
			String query ="update NHANVIEN set ISLOGIN='0' where PK_SEQ='"+userId+"'";
			dbutils db=new dbutils();
			db.update(query);
			db.shutDown();
			session.removeAttribute("userId");
			session.removeAttribute("userTen");
			System.out.println("vao GET");
			//session.invalidate();
    		response.sendRedirect("index.jsp");
//    	}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
 /*   	String userId = (String)request.getSession(false).getAttribute("userId");  
    	String userTen = (String)request.getSession(false).getAttribute("userTen");
    	String sum = (String)request.getSession(false).getAttribute("sum");
    	String site = (String)request.getSession(false).getAttribute("site");
    	Utility util = (Utility)request.getSession(false).getAttribute("util");
    	if(util.check(userId, userTen, sum)){*/
/*    		dbutils db = new dbutils();
    		String query = "update nhanvien set islogin='0', sessionid='0' where pk_seq='" + userId + "'";
    		db.update(query);
    		db.shutDown();*/
			HttpSession session = request.getSession();
			/*session.setAttribute("userId", "");  
			session.setAttribute("userTen", "");*/
			//session.removeAttribute("userId");
			//session.removeAttribute("userTen");
			//SessionCounter.activeSessions--;
			System.out.println("vao POST");
			String userId=(String)session.getAttribute("userId");
			String query ="update NHANVIEN set ISLOGIN='0' where PK_SEQ='"+userId+"'";
			dbutils db=new dbutils();
			db.update(query);
			db.shutDown();
			session.invalidate();
    		response.sendRedirect("index.jsp");
 //   	}
	}

}
