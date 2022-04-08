package geso.dms.center.servlets.duyettratrungbay;

import geso.dms.center.beans.duyettratrungbay.IDuyettratrungbay;
import geso.dms.center.beans.duyettratrungbay.IDuyettratrungbayList;
import geso.dms.center.beans.duyettratrungbay.imp.Duyettratrungbay;
import geso.dms.center.beans.duyettratrungbay.imp.DuyettratrungbayList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class DuyettratrungbayUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public DuyettratrungbayUpdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		Utility util = new Utility();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IDuyettratrungbay dttbBean = new Duyettratrungbay();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		dttbBean.setUserId(userId);
		String id = util.getId(querystring);
		dttbBean.setId(id);
		
		dttbBean.init();
		session.setAttribute("dttbBean", dttbBean);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Center/DuyetTraTrungBayNew.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
	}

}
