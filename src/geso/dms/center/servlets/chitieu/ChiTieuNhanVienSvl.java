package geso.dms.center.servlets.chitieu;

import geso.dms.center.beans.chitieu.IChiTieuNhanVien;
import geso.dms.center.beans.chitieu.IChiTieuNhanVienList;
import geso.dms.center.beans.chitieu.imp.ChiTieuNhanVien;
import geso.dms.center.beans.chitieu.imp.ChiTieuNhanVienList;
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


@WebServlet("/ChiTieuNhanVienSvl")
public class ChiTieuNhanVienSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public ChiTieuNhanVienSvl() 
	{
		super();
	}
	PrintWriter out;

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

		IChiTieuNhanVienList obj = new ChiTieuNhanVienList();
		obj.setUserId(userId);

		String loaichitieu = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaichitieu"));
		if (loaichitieu == null)
			loaichitieu = "0";
		obj.setType(loaichitieu);

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
			db.update("update ChiTieuNhanVien set trangthai = '1' where pk_seq = '" + ctskuId + "'");
			db.shutDown();
		}
		if (action.trim().equals("delete"))
		{
			XoaChiTieu(ctskuId);
		}
		obj.init("");
		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Center/ChiTieuNhanvien.jsp";
		
		if(loaichitieu.equals("2"))
		{
			nextJSP = "/AHF/pages/Center/ChiTieuNhanVienDoPhu.jsp";
		}
		else if(loaichitieu.equals("3"))
		{
			nextJSP = "/AHF/pages/Center/ChiTieuNhanVienSanPhamChuLuc.jsp";
		}
		response.sendRedirect(nextJSP);
	}

	private void XoaChiTieu(String ctskuId)
	{
		dbutils db = new dbutils();

		try
		{
			db.getConnection().setAutoCommit(false);

			if (!db.update("delete ChiTieuNhanVien_SanPham where ChiTieuNhanVien_fk = '" + ctskuId + "'"))
			{
				db.getConnection().rollback();
				return;
			}

			if (!db.update("delete ChiTieuNhanVien_DDKD where ChiTieuNhanVien_fk = '" + ctskuId + "'"))
			{
				db.getConnection().rollback();
				return;
			}
			
			if (!db.update("delete ChiTieuNhanVien_DDKD_NHOMSP where ChiTieuNhanVien_fk = '" + ctskuId + "'"))
			{
				db.getConnection().rollback();
				return;
			}
			
			
			if (!db.update("delete ChiTieuNhanVien_GSBH where ChiTieuNhanVien_fk = '" + ctskuId + "'"))
			{
				db.getConnection().rollback();
				return;
			}
			
			if (!db.update("delete ChiTieuNhanVien_GSBH_NHOMSP where ChiTieuNhanVien_fk = '" + ctskuId + "'"))
			{
				db.getConnection().rollback();
				return;
			}
			
			if (!db.update("delete ChiTieuNhanVien_ASM where ChiTieuNhanVien_fk = '" + ctskuId + "'"))
			{
				db.getConnection().rollback();
				return;
			}
			
			if (!db.update("delete ChiTieuNhanVien_ASM_NHOMSP where ChiTieuNhanVien_fk = '" + ctskuId + "'"))
			{
				db.getConnection().rollback();
				return;
			}

			
			if (!db.update("delete ChiTieuNhanVien_BM where ChiTieuNhanVien_fk = '" + ctskuId + "'"))
			{
				db.getConnection().rollback();
				return;
			}
			
			if (!db.update("delete ChiTieuNhanVien_BM_NHOMSP where ChiTieuNhanVien_fk = '" + ctskuId + "'"))
			{
				db.getConnection().rollback();
				return;
			}
			
			if (!db.update("delete ChiTieuNhanVien_TieuChi where ChiTieuNhanVien_fk = '" + ctskuId + "'"))
			{
				db.getConnection().rollback();
				return;
			}
			if (!db.update("delete ChiTieuNhanVien where pk_seq = '" + ctskuId + "'"))
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
		IChiTieuNhanVienList obj;

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
			IChiTieuNhanVien tctsku = new ChiTieuNhanVien();
			tctsku.setLoaichitieu(loaichitieu);
			tctsku.setView(view);
			tctsku.setUserId(userId);
			tctsku.createRs();
			session.setAttribute("tctskuBean", tctsku);
			session.setAttribute("userId", userId);
			response.sendRedirect("/AHF/pages/Center/ChiTieuNhanVienNew.jsp");
		} else
		{
			obj = new ChiTieuNhanVienList();
			obj.setUserId(userId);
			obj.setType(loaichitieu);
			obj.setView(view);
			String search = getSearchQuery(request, obj);
			obj.init(search);
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			response.sendRedirect("/AHF/pages/Center/ChiTieuNhanVien.jsp");
		}
	}

	private String getSearchQuery(HttpServletRequest request, IChiTieuNhanVienList obj)
	{
		Utility util = new Utility();
		String thang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang"));
		if (thang == null)
			thang = "";
		obj.setThang(thang);

		String nam = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nam"));
		if (nam == null)
			nam = "";
		obj.setNam(nam);

		String nppId="";
		if(obj.getView().length()>0)
		{
			nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
			if (nppId == null)
				nppId = "";
			obj.setNppIds(nppId);
		}else
		{
			nppId=util.getIdNhapp(obj.getUserId());
			obj.setNppIds(nppId);
		}

		String sql = 
				"	select a.ma, a.pk_seq, a.Thang ,a.Quy,a.nam, a.ten , a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua, a.Ky   "+ 
						"		from ChiTieuNhanVien a  "+
						"		inner join NHANVIEN b on a.NGUOITAO = b.pk_seq "+ 
						"		inner join NHANVIEN c on a.NGUOISUA = c.pk_seq  "+
						"  where 1=1 and a.LoaiChiTieu='"+obj.getType()+"' " ; 
		if (thang.length() > 0)
			sql += " and a.thang = '" + thang + "' ";
		if (nam.length() > 0)
			sql += " and a.nam = '" + nam + "' ";


		return sql;
	}
}
