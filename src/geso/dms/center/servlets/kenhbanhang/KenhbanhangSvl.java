package geso.dms.center.servlets.kenhbanhang;

import geso.dms.center.beans.daidienkinhdoanh.IDaidienkinhdoanhList;
import geso.dms.center.beans.kenhbanhang.*;
import geso.dms.center.beans.kenhbanhang.imp.*;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class KenhbanhangSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	
    public KenhbanhangSvl()
    {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		IKenhbanhangList obj;
		PrintWriter out;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
	    obj = new KenhbanhangList();
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    String ssUserId = (String)session.getAttribute("userId");
	    
	/*    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String kbhId = util.getId(querystring);
	    */
	    
	   
		String param = "";
	    
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "KenhbanhangSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
		
	  /*  if (action.equals("delete")){	   		  	    	
	    	Delete(kbhId);
	    	out.print(kbhId);
	    }*/
	   	
	   
	    obj.setUserId(userId);
	    obj.init("");
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		obj.setRequestObj(request);
		String nextJSP = "/AHF/pages/Center/KenhBanHang.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IKenhbanhangList obj=new KenhbanhangList();;
		PrintWriter out;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out = response.getWriter();
	    
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    out.println(action); 
	
	    String param = "";
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			obj.DBClose();
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "KenhbanhangSvl", param, Utility.XEM ))
		{
			obj.DBClose();
			Utility.RedireactLogin(session, request, response);
		}
		
		obj.setUserId(userId);
		obj.setRequestObj(request);
		if(action.equals("Xoa"))
		{
			
			String IdXoa = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("IdXoa")));
			if (IdXoa == null)
				IdXoa = "";
			System.out.println("IdXoa = "+ IdXoa);
			if(IdXoa.trim().length() > 0)
				Delete(session, request,response,IdXoa, obj);
			else
				obj.setMsg("Lỗi dữ liệu");
			
			obj.init("");
			session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/AHF/pages/Center/KenhBanHang.jsp");	    	
	    	
		}
		else
	    if (action.equals("new")){
	    	int[] quyen = Utility.Getquyen("KenhbanhangSvl", "",userId);
			if(quyen[Utility.THEM]==1)
			{
	    	// Empty Bean for distributor
	    	IKenhbanhang kbhBean = (IKenhbanhang) new Kenhbanhang("");
	    	kbhBean.setUserId(userId);
	    	// Save Data into session
	    	session.setAttribute("kbhBean", kbhBean);
    		
    		String nextJSP = "/AHF/pages/Center/KenhBanHangNew.jsp";
    		response.sendRedirect(nextJSP);
			}else
				obj.setMsg( "User không được phần quyền thêm");
    		
	    }
	    if (action.equals("search")){
	    	String search = getSearchQuery(request,obj);
	    	
	    	obj.init(search);
			obj.setUserId(userId);
			
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/AHF/pages/Center/KenhBanHang.jsp");	    	
	    	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request,IKenhbanhangList obj)
	{
		   // PrintWriter out = response.getWriter();
		
		//IKenhbanhangList obj = new KenhbanhangList();
		PrintWriter out;
		obj.getDataSearch().clear();
		Utility util = new Utility();
			
			String kenhbanhang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("KenhBanHang")));
	    	if ( kenhbanhang == null)
	    		kenhbanhang = "";
	    	obj.setKenhbanhang(kenhbanhang );
	    	
	    	String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("DienGiai")));
	    	if (diengiai == null)
	    		diengiai = "";    	
	    	obj.setDiengiai(diengiai);
	    	
	    	String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("TrangThai")));   	
	    	if (trangthai == null)
	    		trangthai = "";    	
		
	    	if (trangthai.equals("2"))
	    		trangthai = "";
	    	
	    	obj.setTrangthai(trangthai);
	    	//geso.dms.distributor.util.Utility util = new geso.dms.distributor.util.Utility();
			String query = "select a.pk_seq as id,isnull(a.FLEX_VALUE,'') as ma, a.ten as kbhTen, a.diengiai, a.trangthai, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua from kenhbanhang a, nhanvien b, nhanvien c ";
			query = query + " where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq"; 			
	    	if (kenhbanhang.length()>0){
	    	
				query = query + " and upper(a.ten) like upper (?)";
				obj.getDataSearch().add( "%" + kenhbanhang + "%" );
				
	    	}
	    	
	    	if (diengiai.length()>0){
				query = query + " and upper(a.diengiai) like upper(?)";
				obj.getDataSearch().add( "%" + diengiai + "%" );
	    	}
	  
	    	if(trangthai.length() > 0){
	    		query = query + " and a.trangthai =? ";
	    		obj.getDataSearch().add( trangthai );
	    	}
	    	query = query + "  order by a.ten";
	    	return query;
		}	
	
	boolean kiemtra(String sql, dbutils db)
	{
    	ResultSet rs = db.get(sql);
		try {//kiem tra ben san pham
		while(rs.next())
		{ if(rs.getString("num").equals("0"))
		   return true;
		}
		} catch(Exception e) {
		
			e.printStackTrace();
		}
		 return false;
	}
	private void Delete(String id)
	{
		
		IKenhbanhangList obj = new KenhbanhangList();
		PrintWriter out;
		
		dbutils db = new dbutils();
		String sql ="select count(kbh_fk) as num from khachhang where kbh_fk='"+ id + "'";
		if(kiemtra(sql, db))
		{  sql = " select count(kbh_fk) as num from giamsatbanhang where kbh_fk ='"+ id +"'";
		   System.out.println(sql);
		   if(kiemtra(sql, db))
		   {
			   db.update("delete from kenhbanhang where pk_seq = '" + id + "'");
				db.shutDown();
		   }
		   else
			   obj.setMsg("Kênh này đã có khách hàng rồi, không thể xóa được");
		}
		else
			obj.setMsg("Kênh này đã có khách hàng rồi, không thể xóa được");
	}

	private void Delete(HttpSession session, HttpServletRequest request,HttpServletResponse response,String id, IKenhbanhangList obj)
	{
	
		dbutils db = new dbutils();

		try
		{
			String param = "";

			if( Utility.CheckRuleUser( session,  request, response, "KenhbanhangSvl", param, Utility.XOA ))
			{
				Utility.RedireactLogin(session, request, response);
			}
			
			if(!Utility.KiemTra_PK_SEQ_HopLe( id, "kenhbanhang", db))
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				obj.setMsg("Định danh không hợp lệ!");
			}
			
			db.getConnection().setAutoCommit(false);
			
			String sql ="select count(kbh_fk) as num from khachhang where kbh_fk='"+ id + "'";
			if(kiemtra(sql, db))
			{ 
				sql = " select count(kbh_fk) as num from giamsatbanhang where kbh_fk ='"+ id +"'";
			   System.out.println(sql);
			   if(kiemtra(sql, db))
			   {
				   sql = "select count(kbh_fk) as num from BANGGIABANLECHUAN where kbh_fk ='"+ id +"'";
				   if (kiemtra(sql, db)) {
					   sql="delete from kenhbanhang where pk_seq = '" + id + "'";
					   //sql="update kenhbanhang set trangthai = 0 where pk_seq = '" + id + "'";
					   System.out.println("delete "+sql);
					
					   if(!db.update(sql)){
						db.getConnection().rollback();
						db.getConnection().setAutoCommit(true);   
					   }
				   }
				   else {
					   geso.dms.center.util.Utility.rollback_throw_exception(db);
					   obj.setMsg("Kênh này đã có trong bảng giá bán lẻ chuẩn rồi, không thể xóa được");
				   }
			   }
			   else{
				   geso.dms.center.util.Utility.rollback_throw_exception(db);
				   obj.setMsg("Kênh này đã có khách hàng rồi, không thể xóa được");
			   }
			}
			else{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				obj.setMsg("Kênh này đã có khách hàng rồi, không thể xóa được");
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
		} catch (Exception e)
		{
			e.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			obj.setMsg("Bạn không thực hiện xóa được. Vui lòng thực hiện lại" + e.toString());
		}

	}
}
