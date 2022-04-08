package geso.dms.distributor.servlets.nhaphang;

import geso.dms.distributor.beans.donhang.IDonhang;
import geso.dms.distributor.beans.donhang.imp.Donhang;
import geso.dms.distributor.beans.nhaphang.imp.Nhaphang;
import geso.dms.distributor.beans.nhaphang.imp.NhaphangList;
import geso.dms.distributor.beans.nhaphang.INhaphang;
import geso.dms.distributor.beans.nhaphang.INhaphangList;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NhaphangSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
{
	static final long serialVersionUID = 1L;

	public NhaphangSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		// kiểm tra quyền xem menu
	    String param="";
		if( Utility.CheckRuleUser( session,  request, response, "NhaphangSvl", param, Utility.XEM ) || Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
			return;
		}
		
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session
				.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/AHF/index.jsp");
		} else
		{
			INhaphangList obj;

			
			PrintWriter out = response.getWriter();

			Utility util = new Utility();
			out = response.getWriter();

			String querystring = request.getQueryString();
			// out.println(action);

			userId = util.getUserId(querystring);
			String action = util.getAction(querystring);
			String id = util.getId(querystring);
			
			/*dbutils db = new dbutils();
			// Kiểm tra id có hợp lệ không
		    if(id != null && id.trim().length() > 0 
		    		&& !geso.dms.center.util.Utility.KiemTra_PK_SEQ_HopLe(id, "nhaphang", db)){
		    	db.shutDown();
	    		return;
	    	}*/

			if (userId.length() == 0)
				userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
			obj = new NhaphangList();
			if (action.equals("hangve"))
			{
				obj.HangVe(id);
			}
			
			if (action.equals("delete"))
			{
				String msg = Delete(id);
				if(msg.length() > 0)
					obj.setMsg(msg);
			}

			obj.setUserId(userId);
			obj.createNhaphanglist("");
			session.setAttribute("obj", obj);

			String nextJSP = "/AHF/pages/Distributor/NhapHang.jsp";
			response.sendRedirect(nextJSP);
		}
	}
	
	private String Delete(String Id) 
	{
		dbutils db = new dbutils();
		try 
		{
			db.getConnection().setAutoCommit(false);
			String sql = " delete from nhaphang_sp where nhaphang_fk = ( select pk_seq from nhaphang where pk_seq = '"+ Id +"' and loai = '1' ) ";
			if(db.updateReturnInt(sql) < 1)
			{
				db.getConnection().rollback();
				return "Không thể xóa nhập hàng.";
			}
			
			sql = " delete from nhaphang where pk_seq = '"+ Id +"' and loai = '1' ";
			if(db.updateReturnInt(sql) != 1)
			{
				db.getConnection().rollback();
				return "Không thể xóa nhập hàng.";
			}
			
			db.getConnection().commit();
			return "";
		} 
		catch (Exception e) 
		{
			try { db.getConnection().rollback(); } catch (SQLException e1) { e1.printStackTrace(); }
			return "Không thể xóa nhập hàng.";
		}
		finally{ try { db.getConnection().setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); } db.shutDown(); }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		
		// kiểm tra quyền xem menu
	    String param="";
		if( Utility.CheckRuleUser( session,  request, response, "NhaphangSvl", param, Utility.XEM ) || Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
			return;
		}
		
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session
				.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/AHF/index.jsp");
		} else
		{
			INhaphangList obj;

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			obj = new NhaphangList();

			Utility util = new Utility();
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			if (action == null)
			{
				action = "";
			}
			out.println(action);

			String search = getSearchQuery(request, obj);
			System.out.println(search);
			
			System.out.println("new : "+action);
			if (action.equals("new")) 
			{
				// Empty Bean for distributor
				INhaphang nhaphang = (INhaphang) new Nhaphang();
				nhaphang.setUserId(userId);
				session.setAttribute("nhaphang", nhaphang); 
				String nextJSP = "/AHF/pages/Distributor/NhapHangNew.jsp";
				response.sendRedirect(nextJSP);
			} 
			
			else if (action.equals("view") || action.equals("next") || action.equals("prev"))
			{
				obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
				obj.setUserId(userId);
				obj.createNhaphanglist(search);
				session.setAttribute("obj", obj);
				obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
				String nextJSP = "/AHF/pages/Distributor/NhapHang.jsp";
				response.sendRedirect(nextJSP);
			} else
			{
				obj.setUserId(userId);
				obj.createNhaphanglist(search);
				
				session.setAttribute("obj", obj);
				String nextJSP = "/AHF/pages/Distributor/NhapHang.jsp";
				out.println(search);
				response.sendRedirect(nextJSP);
			}
			

		}

	}

	private String getSearchQuery(HttpServletRequest request, INhaphangList obj)
	{
		// PrintWriter out = response.getWriter();
		Utility util = new Utility();
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		if (userId == null)
			userId = "";
		obj.setDangnhap(userId);
	

		String nppId = util.getIdNhapp(userId);
		obj.getDataSearch().clear();
		/*
		 * String query =
		 * "select a.pk_seq from nhaphanphoi a, nhanvien b where a.ma = b.dangnhap and b.pk_seq = '"
		 * + this.userId + "'";
		 * 
		 * ResultSet rs = db.get(query); try{ if (rs != null){ rs.next(); nppId
		 * = rs.getString("pk_seq"); rs.close(); }else{ nppId = ""; }
		 * }catch(Exception e){}
		 */
		String sku = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sku")));
		if (sku == null)
			sku = "";
		obj.setSKU(sku);

		String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
		if (tungay == null)
			tungay = "";
		obj.setTungay(tungay);

		String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
		if (denngay == null)
			denngay = "";
		obj.setDenngay(denngay);

		String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
		if (trangthai == null)
			trangthai = "";

		obj.setTrangthai(trangthai);

		String query =  " select distinct isnull(a.hangve,'0') as hangve , a.ngaychungtu,a.DONDATHANG_FK AS dathang_fk, a.ngaynhan, a.SO_Number, a.pk_seq as chungtu,a.HDTaiChinh,e.donvikinhdoanh, f.ten as kbh ,a.sotienavat, b.ten as nguoitao, c.ten as nguoisua, a.trangthai, isnull(a.loai, 0) loai " +
						" from nhaphang a, nhanvien b, nhanvien c, " +
						" nhaphang_sp d, donvikinhdoanh e, kenhbanhang f " +
						" where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.pk_seq = d.nhaphang_fk " +
						" and a.dvkd_fk = e.pk_seq and a.npp_fk=? and a.kbh_fk = f.pk_seq ";
		obj.getDataSearch().add(nppId);

		if (sku.length() > 0)
		{
			query = query + " and d.sanpham_fk like ?";
			obj.getDataSearch().add(sku);
		}

		/*if (tungay.length() > 0)
		{
			query = query + " and a.ngaychungtu >= '" + tungay + "'";
		}*/
		if (tungay.length() > 0)
		{
			query = query + " and a.ngaychungtu >= ?";
			obj.getDataSearch().add(tungay);
		}

		if (denngay.length() > 0)
		{
			query = query + " and a.ngaychungtu <= ?";
			obj.getDataSearch().add(denngay);
		}

		if (trangthai.length() > 0)
		{
			query = query + " and a.trangthai = ?";
			obj.getDataSearch().add(trangthai);
		}
		return query;
	}

}
