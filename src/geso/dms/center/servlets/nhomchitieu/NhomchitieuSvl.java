package geso.dms.center.servlets.nhomchitieu;

import geso.dms.center.beans.nhomchitieu.INhomchitieu;
import geso.dms.center.beans.nhomchitieu.INhomchitieuList;
import geso.dms.center.beans.nhomchitieu.imp.Nhomchitieu;
import geso.dms.center.beans.nhomchitieu.imp.NhomchitieuList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NhomchitieuSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
{
	static final long serialVersionUID = 1L;

	public NhomchitieuSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");

		HttpSession session = request.getSession();

		Utility util = new Utility();
		PrintWriter out = response.getWriter();
		INhomchitieuList obj = new NhomchitieuList();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		out.println(userId);

		if (userId.length() == 0)
			userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));

		String action = util.getAction(querystring);
		out.println(action);

		String nkmId = util.getId(querystring);

		if (action.equals("delete"))
		{
			Delete(nkmId);
		}

		if (action.equals("chot"))
		{
			System.out.println("1.Action la: Chot + Id: " + nkmId);
			Chot(nkmId);
		}

		else if (action.equals("bochot"))
		{
			System.out.println("1.Action la: Chot + Id: " + nkmId);
			Bochot(nkmId);
		}

		obj.init();

		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);

		String nextJSP = "/AHF/pages/Center/NhomChiTieu.jsp";
		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		Utility Ult = new Utility();
		HttpSession session = request.getSession();
		String userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));

		INhomchitieuList obj = new NhomchitieuList();

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if (action == null)
		{
			action = "";
		}
		out.println(action);
		String tennhom = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tennhom"));
		if (tennhom == null)
			tennhom = "";
		obj.setDiengiai(tennhom);

		String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
		if (tungay == null)
			tungay = "";
		obj.setTungay(tungay);

		String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
		if (denngay == null)
			denngay = "";
		obj.setDenngay(denngay);

		String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null)
			trangthai = "";

		if (trangthai.equals("2"))
			trangthai = "";
		obj.setTrangthai(trangthai);

		if (action.equals("new"))
		{
			INhomchitieu nkmBean = (INhomchitieu) new Nhomchitieu();
			nkmBean.UpdateRS();
			session.setAttribute("nkmBean", nkmBean);
			session.setAttribute("userId", userId);
			String nextJSP = "/AHF/pages/Center/NhomChiTieuNew.jsp";
			response.sendRedirect(nextJSP);
		} else if (action.equals("view") || action.equals("next") || action.equals("prev"))
		{
			obj.setUserId(userId);
			obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
			obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
			obj.init();
			session.setAttribute("obj", obj);
			String nextJSP = "/AHF/pages/Center/NhomChiTieu.jsp";
			response.sendRedirect(nextJSP);

		} else
		{
			obj.init();
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			response.sendRedirect("/AHF/pages/Center/NhomChiTieu.jsp");
		}
	}

	private void Delete(String nkmId)
	{
		String query;
		String command;
		try
		{
			dbutils db = new dbutils();
			db.getConnection().setAutoCommit(false);
			command = "delete from nhomsanpham_sanpham where nsp_fk ='" + nkmId + "'";
			if (!db.update(command))
			{

			}
			command = "delete from nhomsanpham where pk_seq ='" + nkmId + "'";
			if (!db.update(command))
			{

			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	private void Chot(String nkmId)
	{
		dbutils db = new dbutils();
		String command = "update nhomsanpham set trangthai = '1' where pk_seq ='" + nkmId + "'";
		db.update(command);
		if (db != null)
			db.shutDown();
	}

	private void Bochot(String nkmId)
	{
		dbutils db = new dbutils();
		String command = "update nhomsanpham set trangthai = '0' where pk_seq ='" + nkmId + "'";
		db.update(command);
		if (db != null)
			db.shutDown();
	}

}
