package geso.dms.center.servlets.tieuchithuong;

import geso.dms.center.beans.tieuchithuong.ITieuchithuongTL;
import geso.dms.center.beans.tieuchithuong.ITieuchithuongTLList;
import geso.dms.center.beans.tieuchithuong.imp.TieuchithuongTL;
import geso.dms.center.beans.tieuchithuong.imp.TieuchithuongTLList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TieuchithuongTLSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
   
    public TieuchithuongTLSvl() {
        super();
    }
    
    private String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
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
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    ITieuchithuongTLList obj = new TieuchithuongTLList();
	    obj.setUserId(userId);
	    
	    String action = util.getAction(querystring);
	    String ctskuId = util.getId(querystring);
	    
	    System.out.println("___Action: " + action + " -- Id la: " + ctskuId);
	   /* if(action.trim().equals("duyet"))
	    {
	    	Duyet(ctskuId,userId);
	    }*/
	    
	    if(action.trim().equals("delete"))
	    {
	    	String msg = XoaChiTieu(ctskuId);
	    	obj.setMsg(msg);
	    }
	    
	    if(action.trim().equals("boduyet"))
	    {
	    	BoDuyet(ctskuId,userId);
	    }

	    obj.init("");
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/AHF/pages/Center/TieuChiThuongTL.jsp";
		response.sendRedirect(nextJSP);
	}
	private String Duyet(String ctskuId,String userId )
	{
		dbutils db = new dbutils();
    	try
    	{
    		db.getConnection().setAutoCommit(false);	
    		
    		int sodong = db.updateReturnInt("update tieuchithuongTL set trangthai = '1' where trangthai = 0 and pk_seq = '" + ctskuId + "'");
    		
    		db.getConnection().commit();
    		db.shutDown();
    		if(sodong > 0)
    			return "Duyệt thành công";
    		else
    			return "Tích lũy đã duyệt hoặc bị hủy";
    	}
    	catch (Exception e) 
    	{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			db.shutDown();
			e.printStackTrace();
			return "ExceptionL" + e.getMessage();

		}
    	
	}
	
	private String BoDuyet(String ctskuId,String userId )
	{
		dbutils db = new dbutils();
    	try
    	{
    		db.getConnection().setAutoCommit(false);	
    		db.update("update tieuchithuongTL set trangthai = '0' where pk_seq = '" + ctskuId + "' and trangthai = '1' ");
    		
    		db.getConnection().commit();
    		db.shutDown();
    		return "Bỏ duyệt thành công";
    	
    	}
    	catch (Exception e) 
    	{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			db.shutDown();
			e.printStackTrace();
			return "ExceptionL" + e.getMessage();

		}
    	
	}

	private String XoaChiTieu(String ctskuId) 
	{
		dbutils db = new dbutils();
    	
    	try 
    	{
			db.getConnection().setAutoCommit(false);
			
			
			String query = " select DienGiai from DANGKYKM_TICHLUY where trangthai !=2 and thuongtl_fk = " + ctskuId;
			ResultSet rs = db.get(query);
			if(rs.next())
			{
				String dg = rs.getString("DienGiai");
				Utility.rollback_and_shutdown(db);
				return "Tích lũy đã tồn tại đăng ký Đăng ký  ["+dg+"]  ";
			}
			
			if(!db.update("delete TIEUCHITHUONGTL_MUCTHUONG where thuongtl_fk = '" + ctskuId + "'"))
	    	{
				Utility.rollback_and_shutdown(db);
				return "Loi 1  ";
	    	}
			
			if(!db.update("delete TIEUCHITHUONGTL_SANPHAM where thuongtl_fk = '" + ctskuId + "'"))
	    	{
				Utility.rollback_and_shutdown(db);
				return "Loi 2  ";
	    	}
			
			if(!db.update("delete TIEUCHITHUONGTL_SPTRA where thuongtl_fk = '" + ctskuId + "'"))
	    	{
				Utility.rollback_and_shutdown(db);
				return "Loi 3  ";
	    	}
			
			if(!db.update("delete TIEUCHITHUONGTL_TIEUCHI where thuongtl_fk = '" + ctskuId + "'"))
	    	{
				Utility.rollback_and_shutdown(db);
				return "Loi 4  ";
	    	}
	    	
	    	if(!db.update("delete TIEUCHITHUONGTL where pk_seq = '" + ctskuId + "'"))
			{
	    		Utility.rollback_and_shutdown(db);
				return "Loi 5  ";
			}
	    	db.getConnection().commit();
	    	db.shutDown();
	    	return "Xóa thành công";
		} 
    	catch (Exception e)
    	{
    		Utility.rollback_and_shutdown(db);
    		return "Exception:" + e.getMessage();
    	}
    	
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
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));     
	    ITieuchithuongTLList obj;
	    
		String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    //AJAX
	    String type = request.getParameter("type");
	    if(type == null)
	    	type = "";
	    
	    if(type.equals("Ajax"))
	    {
	    	String vungId = request.getParameter("vungId");
	    	String khuvucId = request.getParameter("khuvucId");

	    }
	    else
	    {
	    	
	    	
	    	if(action.equals("Chot"))
		    {
	    		String ctskuId = util.antiSQLInspection(request.getParameter("IdXoa"));
				if (ctskuId == null)
					ctskuId = "";
				System.out.println("ctskuId = "+ ctskuId);
				
	    		String ms = Duyet(ctskuId, userId);
	    		obj = new TieuchithuongTLList();
			    obj.setUserId(userId);	
			    obj.setMsg(ms);
		    	String search = getSearchQuery(request, obj);		    	
		    	obj.setUserId(userId);
		    	obj.init(search);					
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);		
	    		response.sendRedirect("/AHF/pages/Center/TieuChiThuongTL.jsp");	
		    }
	    	else
		    if(action.equals("new"))
		    {
	    		ITieuchithuongTL tctsku = new TieuchithuongTL();
	    		tctsku.setUserId(userId);
	    		tctsku.createRs();
	    		
		    	session.setAttribute("tctskuBean", tctsku);  	
	    		session.setAttribute("userId", userId);
			
	    		response.sendRedirect("/AHF/pages/Center/TieuChiThuongTLNew.jsp");
		    }
		    else
		    {
		    	obj = new TieuchithuongTLList();
			    obj.setUserId(userId);
	
		    	String search = getSearchQuery(request, obj);
		    	
		    	obj.setUserId(userId);
		    	obj.init(search);
					
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
			
	    		response.sendRedirect("/AHF/pages/Center/TieuChiThuongTL.jsp");	
		    }
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, ITieuchithuongTLList obj) 
	{
		String thang = request.getParameter("thang");
		if(thang == null)
			thang = "";
		obj.setThang(thang);
		
		String nam = request.getParameter("nam");
		if(nam == null)
			nam = "";
		obj.setNam(nam);
		
		String diengiai = request.getParameter("diengiai");
		if(diengiai == null)
			diengiai = "";
		obj.setDiengiai(diengiai);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String sql = "select a.scheme, a.pk_seq, a.thang, a.nam, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua" +
				     "\n,case when a.TRANGTHAI = '1' then isnull(cast(dktl.PK_SEQ as varchar), '1') else '0' end as dktl  " +
					"\nfrom TIEUCHITHUONGTL a inner join NHANVIEN b on a.NGUOITAO = b.pk_seq " +
					"\n left join DUYETTRAKHUYENMAI dktl on dktl.ctkm_fk = a.pk_seq and dktl.TRANGTHAI = 1 "+
					"\ninner join NHANVIEN c on a.NGUOISUA = c.pk_seq where a.pk_seq > 0 ";
		if(thang.length() > 0)
		{
			sql += "\n and SUBSTRING(a.thang,6,2) <= '" + (thang.length()>1?thang:"0"+thang) + "'  ";
			//sql += "\n and SUBSTRING(a.nam,6,2) <= '" + (thang.length()>1?thang:"0"+thang) + "' ";
		}
		if(nam.length() > 0)
		{
			sql += "\n and SUBSTRING(a.thang,1,4) <= '" + nam + "' ";
			//sql += "\n and SUBSTRING(a.nam,1,4) >='" + nam + "' ";
		}
			
		if(diengiai.length() > 0)
			sql += "\n and (a.diengiai like N'%" + diengiai + "%' or a.scheme like N'%" + diengiai + "%') ";
		if(trangthai.length() > 0)
			sql += "\n and a.trangthai = '" + trangthai + "' ";
		
		sql += "\n order by nam desc, thang desc";
		System.out.println("queryshearch: "+sql);
	
		return sql;
	}


}
