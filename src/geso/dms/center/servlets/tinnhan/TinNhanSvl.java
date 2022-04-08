package geso.dms.center.servlets.tinnhan;

import geso.dms.center.beans.tinnhan.ITinNhan;
import geso.dms.center.beans.tinnhan.ITinNhanList;
import geso.dms.center.beans.tinnhan.TinNhanList;
import geso.dms.center.beans.tinnhan.imp.TinNhan;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.donhang.IDonhang;
import geso.dms.distributor.beans.donhang.imp.Donhang;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lowagie.tools.Toolbox.Console;

@WebServlet("/TinNhanSvl")
public class TinNhanSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	public TinNhanSvl()
	{
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		ITinNhanList obj;
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
		
		String lsxId = util.getId(querystring);
		obj = new TinNhanList();
		
		String loaidonhang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaidonhang"));
		if (loaidonhang == null)
			loaidonhang = "0";
		obj.setLoaidonhang(loaidonhang);
		System.out.println("---LOAI DON HANG: " + loaidonhang);
		
		if (action.equals("delete"))
		{
			String msg = this.DeleteChuyenKho(lsxId);
			obj.setMsg(msg);
		} else if (action.equals("chot"))
		{
			String msg = this.Chot(lsxId);
			obj.setMsg(msg);
		} else if (action.equals("convert"))
		{
			String msg = this.ConvertPO(lsxId, userId);
			obj.setMsg(msg);
			if (msg.length() <= 0)
			{
				String dhId = "";
				String query = "select donhang_fk From Log_InBOX where pk_Seq='" + lsxId + "'";
				dbutils db = new dbutils();
				ResultSet rs = db.get(query);
				try
				{
					while (rs.next())
					{
						dhId = rs.getString("donhang_fk");
					}
				} catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					db.shutDown();
				}
				IDonhang dhBean = new Donhang(dhId);
				dhBean.setUserId(userId);
				dhBean.init();
				dhBean.setKhId(dhBean.getKhId());
				session.setAttribute("bgstId", dhBean.getBgstId());
				session.setAttribute("khoId", dhBean.getKhoId());
				String nextJSP;
				if (request.getQueryString().indexOf("display") >= 0)
				{
					nextJSP = "/AHF/pages/Distributor/DonHangDisplay.jsp";
				} else
				{
					nextJSP = "/AHF/pages/Distributor/DonHangUpdate.jsp";
				}
				session.setAttribute("dhBean", dhBean);
				session.setAttribute("khId", dhBean.getKhId());
				session.setAttribute("ddkdId", dhBean.getDdkdId());
				session.setAttribute("nppId", dhBean.getNppId());
				response.sendRedirect(nextJSP);
			} else
			{
				obj.setUserId(userId);
				obj.init("");
				session.setAttribute("obj", obj);
				String nextJSP = "/AHF/pages/Distributor/TinNhan.jsp";
				response.sendRedirect(nextJSP);
			}
		}else
		{
			obj.setUserId(userId);
			obj.init("");
			session.setAttribute("obj", obj);
			String nextJSP = "/AHF/pages/Distributor/TinNhan.jsp";
			response.sendRedirect(nextJSP);
		}
	}
	
	private String ConvertPO(String lsxId, String userId)
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			ITinNhan tn = new TinNhan(lsxId);
			tn.setUserId(userId);
			tn.init();
			db.getConnection().setAutoCommit(false);
			msg = tn.Convert(db);
			System.out.println("[Query]" + msg);
			if (msg.length() > 0)
			{
				db.getConnection().rollback();
				return msg;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e)
		{
			e.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return "Exception: " + e.getMessage();
		} finally
		{
			if (db != null)
				db.shutDown();
		}
		return "";
	}
	
	private String Chot(String lsxId)
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			String query = "update ERP_Denghidathang set trangthai = '1' where pk_seq = '" + lsxId + "'";
			if (!db.update(query))
			{
				msg = "Khong the chot: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			Utility util = new Utility();
			msg = util.Check_Huy_NghiepVu_KhoaSo("ERP_DeNghiDatHang", lsxId, "ngaydenghi");
			if (msg.length() > 0)
			{
				db.getConnection().rollback();
				return msg;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e)
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			db.shutDown();
			return "Exception: " + e.getMessage();
		} finally
		{
			if (db != null)
				db.shutDown();
		}
		
		return "";
	}
	
	private String DeleteChuyenKho(String lsxId)
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "Delete ERP_Denghidathang where pk_seq = '" + lsxId + "'";
			if (!db.update(query))
			{
				msg = "1.Khong the xoa: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "Delete ERP_Denghidathang_SanPham where denghidathang_fk = '" + lsxId + "'";
			if (!db.update(query))
			{
				msg = "2.Khong the xoa: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			db.getConnection().commit();
			db.shutDown();
		} catch (Exception e)
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		
		return "";
		
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
		
		ITinNhanList obj = new TinNhanList();
		obj.setLoaidonhang(loaidonhang);
		
		Utility util = new Utility();
		
		HttpSession session = request.getSession();
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		
		if (action.equals("Tao moi"))
		{
			/* ITinNhan lsxBean = new TinNhan(); */
			/*
			 * lsxBean.setLoaidonhang(loaidonhang); lsxBean.setUserId(userId);
			 * 
			 * lsxBean.createRs(); session.setAttribute("dvkdId",
			 * lsxBean.getDvkdId()); session.setAttribute("kbhId",
			 * lsxBean.getKbhId()); session.setAttribute("nppId", lsxBean.getNppId());
			 * session.setAttribute("lsxBean", lsxBean);
			 * 
			 * String nextJSP = "/AHF/pages/Distributor/TinNhanNew.jsp";
			 * response.sendRedirect(nextJSP);
			 */
		} else{
			if (action.equals("view") || action.equals("next") || action.equals("prev"))
			{
				String search = getSearchQuery(request, obj);
				obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
				obj.setUserId(userId);
				obj.init(search);
				obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
				session.setAttribute("obj", obj);				
				String nextJSP = "/AHF/pages/Distributor/TinNhan.jsp";
				response.sendRedirect(nextJSP);
			} else {
				String search = getSearchQuery(request, obj);
				System.out.println("day la cau truy van:" +search);				
				obj.setUserId(userId);
				obj.init(search);				
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
				String nextJSP = "/AHF/pages/Distributor/TinNhan.jsp";
				response.sendRedirect(nextJSP);
		
				
			}
		}
	}
	
	private String getSearchQuery(HttpServletRequest request, ITinNhanList obj)
	{
		String query = "select a.pk_seq,a.NgayTao,a.NgayGioTN,a.NoiDung,a.DonHang_fk,a.dienthoai,b.TEN as nvTen,b.NPP_FK,a.TrangThai  " + "	from Log_InBox a inner join DAIDIENKINHDOANH b on b.DIENTHOAI=a.dienthoai  " + "	where 1=1 ";
		
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
		
		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
		if (nppId == null)
			nppId = "";
		obj.setNppTen(nppId);
		
		if (tungay.length() > 0)
			query += " and a.NgayTao >= '" + tungay + "'";
		
		if (denngay.length() > 0)
			query += " and a.NgayTao <= '" + denngay + "'";
		
		if (trangthai.length() > 0)
			query += " and a.TrangThai = '" + trangthai + "'";
		
		if (nppId.length() > 0)
		{
			query += " and b.NPP_FK= '" + nppId + "'";
		}
		
		System.out.print("Tin nhan:  "+ query);
		return query;
	}
	
	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
}
