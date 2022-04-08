package geso.dms.center.servlets.chitieu;

import geso.dms.center.beans.chitieu.IChuLuc;
import geso.dms.center.beans.chitieu.IChuLucList;
import geso.dms.center.beans.chitieu.imp.ChuLuc;
import geso.dms.center.beans.chitieu.imp.ChuLucList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ChuLucSvl")
public class ChuLucSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	public ChuLucSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		this.out = response.getWriter();

		HttpSession session = request.getSession();

		Utility util = new Utility();
		out = response.getWriter();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		out.println(userId);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

		IChuLucList obj = new ChuLucList();
		obj.setUserId(userId);

		String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		if (view == null)
			view = "";
		obj.setView(view);

		String action = util.getAction(querystring);
		String ctskuId = util.getId(querystring);

		// System.out.println("___Action: " + action + " -- Id la: " + ctskuId);
		if (action.trim().equals("duyet"))
		{
			dbutils db = new dbutils();
			db.update("update ChiTieuChuLuc set trangthai = '1' where pk_seq = '" + ctskuId + "'");
			db.shutDown();
		}
		if (action.trim().equals("delete"))
		{
			XoaChiTieu(ctskuId);
		}
		obj.init("");
		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Center/ChuLuc.jsp";
		response.sendRedirect(nextJSP);
	}

	private void XoaChiTieu(String ctskuId)
	{
		dbutils db = new dbutils();

		try
		{
			db.getConnection().setAutoCommit(false);

			if (!db.update("delete ChiTieuChuLuc_NHOMSP where ChiTieuChuLuc_fk = '" + ctskuId + "'"))
			{
				db.getConnection().rollback();
				return;
			}

			if (!db.update("delete ChiTieuChuLuc_SanPham where ChiTieuChuLuc_fk = '" + ctskuId + "'"))
			{
				db.getConnection().rollback();
				return;
			}

			if (!db.update("delete ChiTieuChuLuc_DDKD where ChiTieuChuLuc_fk = '" + ctskuId + "'"))
			{
				db.getConnection().rollback();
				return;
			}
			
			if (!db.update("delete ChiTieuChuLuc_GSBH where ChiTieuChuLuc_fk = '" + ctskuId + "'"))
			{
				db.getConnection().rollback();
				return;
			}
			
			if (!db.update("delete ChiTieuChuLuc_BM where ChiTieuChuLuc_fk = '" + ctskuId + "'"))
			{
				db.getConnection().rollback();
				return;
			}
			
		
			if (!db.update("delete ChiTieuChuLuc where pk_seq = '" + ctskuId + "'"))
			{
				db.getConnection().rollback();
				return;
			}
			db.getConnection().commit();
			db.shutDown();
		} catch (Exception e)
		{
			try
			{
				db.getConnection().rollback();
			} catch (SQLException e1)
			{
			}
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		this.out = response.getWriter();

		HttpSession session = request.getSession();

		out = response.getWriter();
		Utility util = new Utility();

		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		IChuLucList obj;

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if (action == null)
		{
			action = "";
		}

		String loaichitieu = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaichitieu"));
		if (loaichitieu == null)
			loaichitieu = "0";

		String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		if (view == null)
			view = "";

		if (action.equals("new"))
		{
			IChuLuc tctsku = new ChuLuc();
			tctsku.setLoaichitieu(loaichitieu);
			tctsku.setView(view);
			tctsku.setUserId(userId);
			tctsku.createRs();
			session.setAttribute("tctskuBean", tctsku);
			session.setAttribute("userId", userId);
			response.sendRedirect("/AHF/pages/Center/ChuLucNew.jsp");
		} else
		{
			obj = new ChuLucList();
			obj.setUserId(userId);
			obj.setView(view);
			String search = getSearchQuery(request, obj);
			obj.init(search);
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			response.sendRedirect("/AHF/pages/Center/ChuLuc.jsp");
		}
	}

	private String getSearchQuery(HttpServletRequest request, IChuLucList obj)
	{
		/*Utility util = new Utility();*/
		String thang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang"));
		if (thang == null)
			thang = "";
		obj.setThang(thang);

		String nam = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nam"));
		if (nam == null)
			nam = "";
		obj.setNam(nam);


		String sql = 
				"	select a.ma, a.pk_seq, a.Ky,a.Quy,a.nam, a.ten , a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua  "+ 
						"		from ChiTieuChuLuc a  "+
						"		inner join NHANVIEN b on a.NGUOITAO = b.pk_seq "+ 
						"		inner join NHANVIEN c on a.NGUOISUA = c.pk_seq  "+
						"  where 1=1 " ; 
		if (thang.length() > 0)
			sql += " and a.thang = '" + thang + "' ";
		if (nam.length() > 0)
			sql += " and a.nam = '" + nam + "' ";

		sql += "order by nam desc, thang desc";

		return sql;
	}
}
