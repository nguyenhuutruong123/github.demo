package geso.dms.center.servlets.xacnhanuser;

import geso.dms.center.db.sql.dbutils;
import geso.dms.distributor.util.Utility;
import geso.dms.center.beans.upload.IUpload;
import geso.dms.center.beans.upload.imp.Upload;
import geso.dms.center.beans.xacnhanuser.IXacnhanuser;
import geso.dms.center.beans.xacnhanuser.imp.Xacnhanuser;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import com.oreilly.servlet.MultipartRequest;

@WebServlet("/ImportTonKhoSvl")
public class XacnhanUserSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "c:\\java-tomcat\\upload\\excel\\";
	PrintWriter out;
	NumberFormat formatter = new DecimalFormat("#,###,###");

	public XacnhanUserSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String nccId = (String)session.getAttribute("nccId");
		IXacnhanuser obj = new Xacnhanuser();
		obj.setNccId(nccId);

		String querystring = request.getQueryString();
		Utility util = new Utility();
		String userId = util.getUserId(querystring);
		obj.setUserId(userId);

		String nppId = util.getId(querystring);
		String action = util.getAction(querystring);
		System.out.println("_____" + action);
		obj.setNppId(nppId);

		String task = util.antiSQLInspection(request.getParameter("task"));
		if(task == null) task = "";
		obj.setTask(task);

	 
		obj.init();
		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Center/XacnhanUser.jsp";
		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IXacnhanuser obj = null;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String nccId = (String)session.getAttribute("nccId");
		String contentType = request.getContentType();
		Utility util = new Utility();
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		if (userId == null)
		{
			userId = "";
		}
		System.out.println("[Xacnhanuser.doPost] userId = " + userId);

		String action = util.antiSQLInspection(request.getParameter("action"));
		if (action == null)
		{
			action = "";
		}
		System.out.println("[Xacnhanuser.doPost] action = " + action);

		OutputStream out = response.getOutputStream();
		obj = new Xacnhanuser();
		obj.setNccId(nccId);
		obj.setUserId(userId);

	 
		if (action.equals("xacnhan"))
		{
			obj.GuiEmail_Xacnhan();
			
			 
		}  
		obj.init();
		session.setAttribute("userId", userId);
		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Center/XacnhanUser.jsp";
	
		response.sendRedirect(nextJSP);
	}
	
	 
  

	 
 }

