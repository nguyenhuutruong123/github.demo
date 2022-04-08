package geso.dms.distributor.servlets.chitieunpp;
import geso.dms.distributor.beans.chitieunpp.IChiTieuNhaPP;
import geso.dms.distributor.beans.chitieunpp.imp.ChiTieuDDKD;
import geso.dms.distributor.beans.chitieunpp.imp.ChiTieuNhaPP;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChiTieuNhaPPNewSvl extends HttpServlet {
	private static final long serialVersionUID = 1L; 
	 
 
    public ChiTieuNhaPPNewSvl() {
        super();
        // TODO Auto-generated constructor stub
    }
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
      
        return dateFormat.format(date);	
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
			Utility util=new Utility(); 
			
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String querystring = request.getQueryString();
		userId = util.getUserId(querystring); 
		if (userId.length()==0)
		userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		String id=util.getId(querystring);
		IChiTieuNhaPP obj = new ChiTieuNhaPP(id);
		session.setAttribute("userId", userId);
		
		obj.setUserId(userId);
		obj.init();
		
		session.setAttribute("obj",obj);
		String action = util.getAction(querystring);
		
		if(action.equals("update")){
			String nextJSP = "/AHF/pages/Distributor/ChiTieuNppUpdate.jsp";//default
			response.sendRedirect(nextJSP);
		}
		else if(action.equals("display")){
			
			String nextJSP = "/AHF/pages/Distributor/ChiTieuNppDisplay.jsp";//default
			response.sendRedirect(nextJSP);
		}
		else if(action.equals("Chot")){
			
			String nextJSP = "/AHF/pages/Distributor/ChiTieuNPP.jsp";//default
			session.setAttribute("obj", obj);
			response.sendRedirect(nextJSP);
		}

		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}
}
