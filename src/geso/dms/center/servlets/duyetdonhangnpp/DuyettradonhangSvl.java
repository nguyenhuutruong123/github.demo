package geso.dms.center.servlets.duyetdonhangnpp;

import geso.dms.center.beans.duyettradonhang.IDuyettradonhang;
import geso.dms.center.beans.duyettradonhang.IDuyettradonhangList;
import geso.dms.center.beans.duyettradonhang.imp.Duyettradonhang;
import geso.dms.center.beans.duyettradonhang.imp.DuyettradonhangList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.User;

public class DuyettradonhangSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public DuyettradonhangSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		IDuyettradonhangList obj;
		PrintWriter out;
		String userId;

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		out = response.getWriter();

		HttpSession session = request.getSession();

		Utility util = new Utility();
		out = response.getWriter();

		String querystring = request.getQueryString();
		userId = util.getUserId(querystring);
		out.println(userId);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

		String action = util.getAction(querystring);
		out.println(action);
		obj = new DuyettradonhangList();
		obj.setUserId(userId);
		if(action.equals("huy"))
		{
			String NppId=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
			String DthId=util.getId(querystring);
			//System.out.print("[DthId]"+DthId+"[NppId]"+NppId+"[userId]"+userId);
			obj.setMsg(Huy(DthId, NppId, userId));
		}
		obj.init("");
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Center/DuyetDonHangTra.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IDuyettradonhangList obj;
		obj = new DuyettradonhangList();
		PrintWriter out;
		String userId;

		Utility util = new Utility();

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		out = response.getWriter();

		HttpSession session = request.getSession();
		userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if (action == null)
		{
			action = "";
		}
		out.println(action);

		if (action.equals("view") || action.equals("next") || action.equals("prev"))
		{

			obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
			obj.init("");
			obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
			session.setAttribute("obj", obj);
			String nextJSP = "/SalesUpTest/pages/Erp/DuyetDonHangTra.jsp";
			response.sendRedirect(nextJSP);
		}
		if (action.equals("taomoi"))
		{
			IDuyettradonhang dtdhBean = (IDuyettradonhang) new Duyettradonhang("");
			dtdhBean.setUserId(userId);
			dtdhBean.createRS();
			session.setAttribute("dtdhBean", dtdhBean);
			String nextJSP = "/AHF/pages/Center/DuyetDonHangTraNew.jsp";
			response.sendRedirect(nextJSP);

		}
		else{
			String search = getSearchQuery(request, obj);
			obj.setUserId(userId);
			obj.init(search);
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			response.sendRedirect("/AHF/pages/Center/DuyetDonHangTra.jsp");
		}
	}

	private String getSearchQuery(HttpServletRequest request, IDuyettradonhangList obj)
	{
		PrintWriter out;
		String userId;
		Utility util = new Utility();
		String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppTen")));
		if (nppId == null)
			nppId = "";
		obj.setNppId(nppId);

		String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
		if (trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);

		String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
		if (tungay == null)
			tungay = "";
		obj.setTungay(tungay);

		String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
		if (denngay == null)
			denngay = "";
		obj.setDenngay(denngay);

		String query = "select a.pk_seq as dhtvId, a.ngaynhap, a.trangthai, a.ngaytao, a.ngaysua, isnull(b.ten, '') as nguoiduyet, d.ten as khTen, d.pk_seq as khId, e.pk_seq as ddkdId, e.ten as ddkdTen, f.ten as nppTen, f.pk_seq as nppId, a.VAT, isnull(a.ngayduyet, '') as ngayduyet";
		query = query +
			" from donhangtrave a left join nhanvien b on a.nguoiduyet = b.pk_seq inner join khachhang d on a.khachhang_fk = d.pk_seq inner join daidienkinhdoanh e on a.ddkd_fk = e.pk_seq inner join nhaphanphoi f on a.npp_fk = f.pk_seq";
		query = query + " where a.trangthai in (1,2 ,3) and a.donhang_fk is not null ";

		if (nppId.length() > 0)
		{
			query = query + " and a.npp_fk = '" + nppId + "'";
		}

		if (trangthai.length() > 0)
		{
			query = query + " and a.trangthai ='" + trangthai + "'";

		}

		if (tungay.length() > 0)
		{
			query = query + " and a.ngaynhap > '" + tungay + "'";

		}

		if (denngay.length() > 0)
		{
			query = query + " and a.ngaynhap < '" + denngay + "'";

		}
		return query;
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public String Huy(String DthId,String NppId,String UserId)
	{
		String Msg="";
		String ngayHuy=getDateTime();
		String query="Update DonHangTraVe Set TrangThai=2,NguoiSua='"+UserId+"',NgaySua='"+ngayHuy+"',NgayDuyet='"+ngayHuy+"',NguoiDuyet='"+UserId+"' Where PK_SEQ='"+DthId+"' AND NPP_FK='"+NppId+"'  ";
		dbutils db=new dbutils();
		if(!db.update(query))
		{
			System.out.println("----------- Khong the huy don tra hang --------------"+query);
			Msg="Khong the huy don hang tra ve "+query;
		}
		db.shutDown();
		return Msg;
		
	}

}
