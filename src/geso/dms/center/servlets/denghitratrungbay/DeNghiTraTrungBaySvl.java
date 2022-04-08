package geso.dms.center.servlets.denghitratrungbay;

import geso.dms.center.beans.denghitratrungbay.IDeNghiTraTrungBayList;
import geso.dms.center.beans.denghitratrungbay.imp.DeNghiTraTrungBayList;

import geso.dms.center.util.Utility;

import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/DeNghiTraTrungBaySvl")
public class DeNghiTraTrungBaySvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public DeNghiTraTrungBaySvl()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IDeNghiTraTrungBayList obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		
		Utility util = new Utility();
		
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		
		if (userId.length() == 0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		
		String action = util.getAction(querystring);
		
		String type = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("type") == null ? "" : request.getParameter("type")));
		String lsxId = util.getId(querystring);
		obj = new DeNghiTraTrungBayList();
		
		String loaidonhang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaidonhang"));
		if (loaidonhang == null)
			loaidonhang = "0";
		obj.setLoaidonhang(loaidonhang);
		System.out.println("---LOAI DON HANG: " + loaidonhang);
		
		if (action.equals("delete"))
		{
			/* String msg = this.DeleteChuyenKho(lsxId); */
			/* obj.setMsg(msg); */
		}
		obj.setUserId(userId);
		obj.init("");
		
		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Center/DeNghiTraTrungBay.jsp";
		response.sendRedirect(nextJSP);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if (action == null)
		{
			action = "";
		}
		
		String loaidonhang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaidonhang"));
		if (loaidonhang == null)
			loaidonhang = "0";
		
		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
		if (nppId == null)
			nppId = "";
		
		String ctId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ctId"));
		if (ctId == null)
			ctId = "";
		
		String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
		if (tungay == null)
			tungay = "";
		
		String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
		if (denngay == null)
			denngay = "";
		
		String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null)
			trangthai = "";
		
		IDeNghiTraTrungBayList obj = new DeNghiTraTrungBayList();
		obj.setLoaidonhang(loaidonhang);
		
		obj.setNppTen(nppId);
		obj.setctId(ctId);
		obj.setTungay(tungay);
		obj.setDenngay(denngay);
		obj.setTrangthai(trangthai);
		
		Utility util = new Utility();
		
		HttpSession session = request.getSession();
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		obj.setUserId(userId);
		{
			if (action.equals("view") || action.equals("next") || action.equals("prev"))
			{
				obj.setUserId(userId);
				String search = getSearchQuery(request, obj);
				obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
				
				obj.init(search);
				obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
				session.setAttribute("obj", obj);
				
				String nextJSP = "/AHF/pages/Center/DeNghiTraTrungBay.jsp";
				response.sendRedirect(nextJSP);
			} else
			{
				String search = getSearchQuery(request, obj);
				obj.setUserId(userId);
				obj.init(search);
				
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
				
				String nextJSP = "/AHF/pages/Center/DeNghiTraTrungBay.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}
	
	private String getSearchQuery(HttpServletRequest request, IDeNghiTraTrungBayList obj)
	{
		
		Utility util = new Utility();
		
		String query = 
				"	select a.PK_SEQ as dnId,a.NGAYDENGHI,a.TRANGTHAI ,b.SCHEME,b.DIENGIAI,c.MA as nppMa,c.TEN as nppTen,a.LANTHANHTOAN," +
				"a.NGAYSUA,d.TEN as nsua  " + "	from DENGHITRATRUNGBAY a inner join CTTRUNGBAY b on  b.PK_SEQ=a.CTTRUNGBAY_FK " + 
				"		inner join NHAPHANPHOI c on c.PK_SEQ=a.NPP_FK " + "		inner join NHANVIEN d on d.PK_SEQ= a.NGUOISUA " + 
				" where 1=1 ";
		
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
		obj.setTrangthai(trangthai);
		
		String ctId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chungtu"));
		if (ctId == null)
			ctId = "";
		obj.setctId(ctId);
		
		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
		if (nppId == null)
			nppId = "";
		obj.setNppTen(nppId);
		
		if (tungay.length() > 0)
			query += " and a.NGAYDENGHI >= '" + tungay + "'";
		
		if (denngay.length() > 0)
			query += " and a.NGAYDENGHI <= '" + denngay + "'";
		
		if (trangthai.length() > 0)
			query += " and a.TrangThai = '" + trangthai + "'";
		
		if (ctId.length() > 0)
			query += " and a.PK_SEQ like '%" + ctId + "%'";
		
		if (nppId.length() > 0)
		{
			query += " and a.NPP_FK= '" + nppId + "'";
		}
		
		System.out.print(query);
		return query;
	}
	
	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
