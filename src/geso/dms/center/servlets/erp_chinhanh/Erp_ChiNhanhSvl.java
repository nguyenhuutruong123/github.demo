package geso.dms.center.servlets.erp_chinhanh;


import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geso.dms.center.beans.erp_chinhanh.IErp_chinhanh;
import geso.dms.center.beans.erp_chinhanh.IErp_chinhanhList;
import geso.dms.center.beans.erp_chinhanh.imp.Erp_chinhanh;
import geso.dms.center.beans.erp_chinhanh.imp.Erp_chinhanhList;


public class Erp_ChiNhanhSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public Erp_ChiNhanhSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		if (userId.length() == 0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

		String cnId = util.getId(querystring);
		if (userId.length() == 0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		IErp_chinhanhList cnList = new Erp_chinhanhList();
		cnList.setTRANGTHAI("");
		String action = util.getAction(querystring);
		if (action.equals("delete"))
		{
			cnList.setID(cnId);
			cnList.Delete();
		}
		cnList.init("");
		session.setAttribute("cnList", cnList);
		response.sendRedirect("pages/Center/Erp_ChiNhanh.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{
		IErp_chinhanhList cnList;

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String userId;
		Utility util = new Utility();

		cnList = new Erp_chinhanhList();
		String ma = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ma")));
		if (ma == null)
			ma = "";
		cnList.setMA(ma);

		String ngaytao = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngay")));
		if (ngaytao == null)
			ngaytao = "";
		cnList.setNGAYTAO(ngaytao);

		String ten = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ten")));
		if (ten == null)
			ten = "";
		cnList.setTEN(ten);

		String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tt")));
		if (trangthai == null)
			trangthai = "";
		cnList.setTRANGTHAI(trangthai);

		HttpSession session = request.getSession();
		userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if (action.equals("search"))
		{
			cnList.setUserid(userId);
			cnList.init("");
			session.setAttribute("cnList", cnList);
			response.sendRedirect("pages/Center/Erp_ChiNhanh.jsp");
		}

		else
		{
			IErp_chinhanh cnBean =new  Erp_chinhanh();
			session = request.getSession();
			session.setAttribute("cnBean", cnBean);
			response.sendRedirect("pages/Center/Erp_ChiNhanhNew.jsp");
		}
	}

}
