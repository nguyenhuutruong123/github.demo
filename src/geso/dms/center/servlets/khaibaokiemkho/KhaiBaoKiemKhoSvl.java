package geso.dms.center.servlets.khaibaokiemkho;

import geso.dms.center.beans.khaibaokiemkho.IKhaiBaoKiemKho;
import geso.dms.center.beans.khaibaokiemkho.IKhaiBaoKiemKhoList;
import geso.dms.center.beans.khaibaokiemkho.imp.KhaiBaoKiemKho;
import geso.dms.center.beans.khaibaokiemkho.imp.KhaiBaoKiemKhoList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/KhaiBaoKiemKhoSvl")
public class KhaiBaoKiemKhoSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	public KhaiBaoKiemKhoSvl()
	{
		super();
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    IKhaiBaoKiemKhoList obj = new KhaiBaoKiemKhoList();
	    obj.setUserId(userId);

	    String action = util.getAction(querystring);
	    String khlId = util.getId(querystring);
	    String msg = "";
	    
	    if(action.trim().equals("delete"))
	    {
	    	msg = Delete(khlId, userId);
	    }
	    if(action.trim().equals("unchot"))
	    {
	    	msg = Unchot(khlId, userId);
	    }
	    if(action.trim().equals("duyet"))
	    {
	    	msg = Duyet(khlId, userId);
	    }

	    obj.init("");
	    obj.setMsg(msg);
	    session.setAttribute("obj", obj);
	    
	    String nextJSP = "/AHF/pages/Center/KhaiBaoKiemKho.jsp";
		response.sendRedirect(nextJSP);
	}

	private String Delete(String id,String userId)
	{
		dbutils db = new dbutils();
		try
		{
			db.getConnection().setAutoCommit(false);
		
			String query = "delete from KHAIBAOKIEMKHODOITHU_SANPHAM where KHAIBAOKIEMKHO_FK = '" + id + "'";
			if (!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể xóa bảng giá,vui lòng liên hệ Admin" + query;
			}
			query = "delete from KHAIBAOKIEMKHOKHACHHANG_SANPHAM where KHAIBAOKIEMKHO_FK ='" + id + "'";
			if (!db.update(query))
			{
				System.out.print(query);
				db.getConnection().rollback();
				return "Không thể xóa bảng giá,vui lòng liên hệ Admin" + query;
			}
			query = "delete from KHAIBAOKIEMKHO_KHACHHANG where KHAIBAOKIEMKHO_FK ='" + id + "'";
			if (!db.update(query))
			{
				System.out.print(query);
				db.getConnection().rollback();
				return "Không thể xóa bảng giá,vui lòng liên hệ Admin" + query;
			}
			query = "delete from KHAIBAOKIEMKHO where pk_seq = '" + id + "'";
			if (!db.update(query))
			{
				System.out.print(query);
				db.getConnection().rollback();
				return "Không thể xóa, vui lòng liên hệ Admin" + query;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e)
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return "Không thể xóa, vui lòng liên hệ Admin" + e.getMessage();
		}
		finally
		{
			db.shutDown();
		}
		return "";
	}
	
	private String Duyet(String id,String userId)
	{
		dbutils db = new dbutils();
		try
		{
			String query = "update KHAIBAOKIEMKHO set TRANGTHAI = 1 where pk_seq = '" + id + "'";
			if (!db.update(query))
			{
				System.out.print(query);
				db.getConnection().rollback();
				return "Không thể cập nhật,vui lòng liên hệ Admin" + query;
			}

		} catch (Exception e)
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return "Không thể cập nhật " + e.getMessage();
		}
		finally
		{
			db.shutDown();
		}
		return "";
	}
	private String Unchot(String id,String userId)
	{
		dbutils db = new dbutils();
		try
		{
			String query = "update KHAIBAOKIEMKHO set TRANGTHAI = 0 where pk_seq = '" + id + "'";
			if (!db.update(query))
			{
				System.out.print(query);
				db.getConnection().rollback();
				return "Không thể cập nhật,vui lòng liên hệ Admin" + query;
			}

		} catch (Exception e)
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return "Không thể cập nhật " + e.getMessage();
		}
		finally
		{
			db.shutDown();
		}
		return "";
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    
	    out = response.getWriter();
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));	    
	    
	    IKhaiBaoKiemKhoList obj = new KhaiBaoKiemKhoList();
	    obj.setUserId(userId);
	    
	    String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
	  	if ( diengiai == null)
	  		diengiai = "";
	  	obj.setDiengiai(diengiai);
		
		String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
	    
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    
	    if(action.equals("new"))
	    {
	    	IKhaiBaoKiemKho khl = new KhaiBaoKiemKho();
    		khl.setUserId(userId);
    		khl.createRs();
	    	session.setAttribute("csxBean", khl);  	
    		session.setAttribute("userId", userId);
    		response.sendRedirect("/AHF/pages/Center/KhaiBaoKiemKhoNew.jsp");
	    }
	    else
	    {
	    	
		    obj.setUserId(userId);

	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/AHF/pages/Center/KhaiBaoKiemKho.jsp");	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IKhaiBaoKiemKhoList obj) 
	{
		String sql = " select a.pk_seq, a.DIENGIAI, d.ten as nguoitao, a.ngaytao as ngaytao,  " +
		  " 	e.ten as nguoisua, a.ngaysua as ngaysua, " +
		  " 	isnull(a.TrangThai,0) as TRANGTHAI, a.TuNgay, a.DENNGAY " +
		  " from KHAIBAOKIEMKHO a, nhanvien d, nhanvien e   " +
		  " where a.nguoitao = d.pk_seq and a.nguoisua = e.pk_seq";
		if(obj.getDiengiai().length() > 0)
			sql += " and a.DIENGIAI LIKE N'%"+obj.getDiengiai()+"%'";
		if(obj.getTungay().length() > 0)
			sql += " and a.TUNGAY >= '"+obj.getTungay()+"'";
		if(obj.getDenngay().length() > 0)
			sql += " and a.DENNGAY >= '"+obj.getDenngay()+"'";
		return sql;
	}
}
