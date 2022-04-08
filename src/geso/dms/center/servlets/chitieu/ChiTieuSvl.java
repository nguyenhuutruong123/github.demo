package geso.dms.center.servlets.chitieu;

import geso.dms.center.beans.chitieu.IChiTieu;
import geso.dms.center.beans.chitieu.imp.ChiTieu;
import geso.dms.center.util.Utility;
import java.io.IOException;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChiTieuSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ChiTieuSvl()
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

		IChiTieu obj = new ChiTieu();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		obj.setUserId(userId);

		String querystring = request.getQueryString();
		String action = util.getAction(querystring);
		String idlist = util.getId(querystring);
		try
		{
			obj.setID(Double.parseDouble(idlist));
		} catch (Exception er)
		{
		}


		String nextJSP = "/AHF/pages/Center/ChiTieu.jsp";// default
		obj.CreateRs();

		String loaict = "1";
		if (action.equals("delete"))
		{
			obj.DeleteChitieu_Sec();
			obj.setListChiTieu("", loaict);
			session.setAttribute("obj", obj);
		} else if (action.equals("chot"))
		{

			obj.ChotChiTieu_Sec();
			obj.setListChiTieu("", loaict);
			session.setAttribute("obj", obj);
		} else if (action.equals("unchot"))
		{// circumstance add new
			obj.setID(Double.parseDouble(idlist));
			obj.UnChotChiTieu_Sec();
			obj.setListChiTieu("", loaict);
			session.setAttribute("obj", obj);
		} else
		{
			obj.setListChiTieu("", loaict);
			session.setAttribute("obj", obj);
		}
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		Utility util = new Utility();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		String username = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userTen")));

		String thangtk = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang")));
		String namtk = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nam")));
		String tumuc = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tumuc")));
		String toimuc = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("toimuc")));
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));

		ChiTieu chitieu = new ChiTieu();
		chitieu.setUserId(userId);
		HttpSession session = request.getSession();
		String loaict = (String) util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaichitieu")));

		String dvkdid = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("selectdvkd")));

		chitieu.setDVKDID(dvkdid);
		chitieu.setThang(thangtk);
		chitieu.setNam(namtk);

		chitieu.SetTumuc(tumuc);
		chitieu.SetToimuc(toimuc);

		Utility Ult = new Utility();
		if (action.equals("search"))
		{
			String sql_getdata = "";

			sql_getdata = 
			"SELECT   C.PK_SEQ,c.trangthai,kenh_fk, C.THANG, C.NAM,  C.CHITIEU,C.DIENGIAI, C.NGAYKETTHUC,C.DVKD_FK,D.donvikinhdoanh,C.SONGAYLAMVIEC, C.NGAYTAO, C.NGAYSUA,NT.TEN AS NGUOITAO, "+ 
			"NT.PK_SEQ AS NGUOISUA FROM dbo.CHITIEU_SEC AS C INNER JOIN " + 
			"dbo.NHANVIEN AS NT ON C.NGUOITAO = NT.PK_SEQ INNER JOIN dbo.NHANVIEN AS NS ON C.NGUOISUA = NS.PK_SEQ "+ 
			" inner join DONVIKINHDOANH D on D.pk_seq=C.DVKD_FK where 1=1 " + " and kenh_fk in " + Ult.quyen_kenh(userId);
			String where = "";
			if (!thangtk.equals("0"))
			{
				where = " and C.THANG=" + thangtk + " ";
			}
			if (!namtk.equals("0"))
			{
				where = where + "and C.NAM=" + namtk + " ";
			}
			if (!tumuc.equals(""))
			{
				where = where + "and C.CHITIEU >=" + tumuc + " ";
			}
			if (!toimuc.equals(""))
			{
				where = where + "and C.CHITIEU <=" + toimuc;
			}
			if (!dvkdid.equals(""))
			{
				where = where + "and C.DVKD_FK=" + dvkdid;
			}
			if (where != "")
			{
				sql_getdata = sql_getdata + where;
			}
			chitieu.setListChiTieu(sql_getdata, loaict);
			chitieu.CreateRs();
			session.setAttribute("obj", chitieu);
			String nextJSP = "/AHF/pages/Center/ChiTieu.jsp";// default
			response.sendRedirect(nextJSP);
		} else if (action.equals("new"))
		{
			IChiTieu obj = new ChiTieu();
			obj.setUserId(userId);
			obj.CreateRs();
			session.setAttribute("userId", userId);
			session.setAttribute("userTen", username);
			session.setAttribute("obj", obj);
			session.setAttribute("check", "0");
			String nextJSP = "/AHF/pages/Center/ChiTieuNew.jsp";
			response.sendRedirect(nextJSP);

		}
	}

}
