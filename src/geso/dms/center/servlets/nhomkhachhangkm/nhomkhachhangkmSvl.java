package geso.dms.center.servlets.nhomkhachhangkm;

import geso.dms.center.beans.nhomkhachhangkm.INhomkhachhangkm;
import geso.dms.center.beans.nhomkhachhangkm.INhomkhachhangkmList;
import geso.dms.center.beans.nhomkhachhangkm.imp.Nhomkhachhangkm;
import geso.dms.center.beans.nhomkhachhangkm.imp.NhomkhachhangkmList;
import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class nhomkhachhangkmSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public nhomkhachhangkmSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		Utility util = new Utility();
		INhomkhachhangkmList obj;
		// HttpServletRequest request;
		// HttpServletResponse response;

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();

		obj = new NhomkhachhangkmList();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		out.println(userId);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		String action = util.getAction(querystring);
		out.println(action);

		String idlist = util.getId(querystring);

		// Is a Chung loai deleted?
		if (action.equals("delete"))
		{
			if (!Delete(idlist))
				obj.setmsg("Nhóm khách hàng này đã có chương trình khuyến mãi rồi");
		}

		// obj.setuserId(userId);
		obj.init("");

		// Save data into session
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);

		String nextJSP = "/AHF/pages/Center/Nhomkhachhangkm.jsp";
		response.sendRedirect(nextJSP);

	}

	boolean Delete(String idlist)
	{
		dbutils db = new dbutils();
		String num = "";
		String query = "select count(*) as num  from ctkhuyenmai where nhomkhnpp_fk='" + idlist + "'";
		ResultSet rs = db.get(query);
		try
		{
			rs.next();
			num = rs.getString("num");
		} catch (Exception e)
		{

			e.printStackTrace();
		}
		if (num.equals("0"))
		{
			String sql = "delete from CTKM_KHACHHANG where NHOMKHNPP_FK='" + idlist + "'";
			db.update(sql);
		
			
			 sql = "delete from nhomkhachhangnpp where pk_seq ='" + idlist + "'";
			db.update(sql);
			return true;
			
			
		}
		return false;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		Utility util = new Utility();
		INhomkhachhangkmList obj = new NhomkhachhangkmList();

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();

		String userId = util.antiSQLInspection(request.getParameter("userId"));
		obj.setuserId(userId);

		String Diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (Diengiai == null)
			Diengiai = "";
		obj.setDiengiai(Diengiai);

		String Trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if (Trangthai == null)
			Trangthai = "0";
		obj.setTrangthai(Trangthai);

		String Tungay = util.antiSQLInspection(request.getParameter("tungay"));
		if (Tungay == null)
			Tungay = "";
		obj.setTungay(Tungay);

		String Denngay = util.antiSQLInspection(request.getParameter("denngay"));
		if (Denngay == null)
			Denngay = "";
		obj.setDenngay(Denngay);

		String action = request.getParameter("action");
		if (action.equals("search"))
		{
			obj.init("  ");
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);

			String nextJSP = "/AHF/pages/Center/Nhomkhachhangkm.jsp";
			response.sendRedirect(nextJSP);
		} else if (action.equals("xoa"))
		{
			obj = new NhomkhachhangkmList();
			obj.init("");
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);

			String nextJSP = "/AHF/pages/Center/Nhomkhachhangkm.jsp";
			response.sendRedirect(nextJSP);
		} else
		{
			INhomkhachhangkm objj = new Nhomkhachhangkm();
			objj.createRs();
			session.setAttribute("nkhKmBean", objj);
			String nextJSP = "/AHF/pages/Center/NhomKhachHangKmNew.jsp";
			response.sendRedirect(nextJSP);
		}
	}
}
