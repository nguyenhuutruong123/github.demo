package geso.dms.distributor.servlets.khoasongay;

import geso.dms.center.db.sql.dbutils;
import geso.dms.distributor.beans.khoasongay.IKhoasongay;
import geso.dms.distributor.beans.khoasongay.imp.Khoasongay;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class KhoasongaySvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;


	public KhoasongaySvl() 
	{
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
			IKhoasongay obj;

			PrintWriter out;

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			out = response.getWriter();

			Utility util = new Utility();

			String querystring = request.getQueryString();

			userId = util.getUserId(querystring);
			out.println(userId);

			if (userId.length()==0)
				userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

			obj = new Khoasongay();
			obj.setUserId(userId);
			obj.init();

			session.setAttribute("obj", obj);
			String nextJSP = "/AHF/pages/Distributor/KhoaSoNgay.jsp";
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

			IKhoasongay obj;

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			obj = new Khoasongay();
			Utility util = new Utility();

			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
			obj.setUserId(userId);

			String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
			if (nppId == null)
				nppId = "";				
			obj.setNppId(nppId);

			


			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));


			obj.init();
			if(action.equals("save"))
			{
				obj.KhoaSoNgay("");
				obj.init();
				obj.setMessege(obj.getMessege());
				session.setAttribute("obj", obj);

				String nextJSP = "/AHF/pages/Distributor/KhoaSoNgay.jsp";
				response.sendRedirect(nextJSP);	

			}
			else  //submit
			{
				obj.init();
				session.setAttribute("obj", obj);			
				String nextJSP = "/AHF/pages/Distributor/KhoaSoNgay.jsp";
				response.sendRedirect(nextJSP);	
			}

		}
	}

	private String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}

}
