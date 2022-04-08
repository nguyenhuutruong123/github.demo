package geso.dms.center.servlets.khuvuc;

import geso.dms.center.beans.khuvuc.*;
import geso.dms.center.beans.khuvuc.imp.*;
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


public class KhuvucSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
	
    public KhuvucSvl()
    {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		IKhuvucList obj;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
	    obj = new KhuvucList();
	    obj.init("");
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String kvId = util.getId(querystring);

	  //--- check user
	    String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		if (view == null)
			view = "";
		//obj.setView(view);
		String param = "";
		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			//obj.DBClose();
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "KhuvucSvl", param, Utility.XEM ))
		{
			//obj.DBClose();
			Utility.RedireactLogin(session, request, response);
		}
		 //--- check user
		
	    if (action.equals("delete")){	   		  	    	
	    	obj.setMsg(Delete(kvId));
	    	out.print(kvId);
	    }
	   	
	   
	    obj.setUserId(userId);
	    obj.init("");
		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Center/KhuVuc.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		IKhuvucList obj = new KhuvucList();
		Utility util = new Utility();
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out = response.getWriter();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    out.println(action); 
	        
	    //--- check user
	    String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		if (view == null)
			view = "";
		//obj.setView(view);
		String param = "";
		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			//obj.DBClose();
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "KhuvucSvl", param, Utility.XEM ))
		{
			//obj.DBClose();
			Utility.RedireactLogin(session, request, response);
		}
		 //--- check user
	    
	    if (action.equals("new")){
	    	// Empty Bean for distributor
	    	IKhuvuc kvBean = (IKhuvuc) new Khuvuc("");
	    	kvBean.setUserId(userId);
	    	// Save Data into session
	    	session.setAttribute("kvBean", kvBean);
    		
    		String nextJSP = "/AHF/pages/Center/KhuVucNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }	    
	    
	    if (action.equals("search")){
	    	String search = getSearchQuery(request,obj);
	    	
	    	//obj = new KhuvucList(search);
	    	obj.init(search);
			obj.setUserId(userId);
			
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
    		session.setAttribute("abc", search);
	    		
    		response.sendRedirect("/AHF/pages/Center/KhuVuc.jsp");	    	
	    	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request,IKhuvucList obj){
		   // PrintWriter out = response.getWriter();
			
		//IKhuvucList obj = new KhuvucList();
		obj.getDataSearch().clear();
		Utility util = new Utility();
		
			String vung = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("VungMien")));
	    	if ( vung == null)
	    		vung = "";
	    	obj.setVmId(vung);
	    	
	    	String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("TrangThai")));   	
	    	if (trangthai == null)
	    		trangthai = "";    	
		
	   if (trangthai.equals("2"))
	  	trangthai = "";
	    	
	    	obj.setTrangthai(trangthai);
	    	
	    //	String query = "select a.pk_seq as id, a.ten as kvTen, a.trangthai as trangthai, b.pk_seq as vmId, b.ten as vmTen, c.ten as nguoitao, a.ngaytao as ngaytao, d.ten as nguoisua, a.ngaysua as ngaysua, a.diengiai";
			//query = query + " from khuvuc a, vung b, nhanvien c, nhanvien d";
		//	query = query + " where a.vung_fk=b.pk_seq and a.nguoitao = c.pk_seq and a.nguoisua = d.pk_seq";
			
			String query = "select a.pk_seq as id,isnull(a.ma,'') as ma, a.ten as kvTen, a.trangthai as trangthai, b.pk_seq as vmId, b.ten as vmTen, c.ten as nguoitao, a.ngaytao as ngaytao, d.ten as nguoisua, a.ngaysua as ngaysua, a.diengiai";
			query = query + " from khuvuc a, vung b, nhanvien c, nhanvien d";
			query = query + " where a.vung_fk=b.pk_seq and a.nguoitao = c.pk_seq and a.nguoisua = d.pk_seq";
		
			//Utility util = new Utility();
	    	if (vung.length()>0){
			//	query = query + " and a.vung_fk ='" + vung + "'";		
	    		query = query + " and a.vung_fk =?";
	    		obj.getDataSearch().add( vung  );
	    	}
	  
	    	if(trangthai.length() > 0){
	    		query = query + " and a.trangthai =?";	
	    		obj.getDataSearch().add( trangthai  );
	    	}
	    	query = query + " order by a.ten";
	    	System.out.println("cau lenh:"+ query);
	    	return query;
		}	
	boolean kiemtra(String sql,dbutils db)
	{
		ResultSet rs = db.get(sql);
		try {//kiem tra ben san pham
			while(rs.next())
			{ 
				if(rs.getString("num").equals("0"))
				return true;
			}
			rs.close();
		} catch(Exception e) {

			e.printStackTrace();
			return false;
		}
		return false;
	}

	private String Delete(String id)
	{	
		String msg = "";
		dbutils db  = new dbutils();
	   	
	   	try{
	   		String sql = "select count(khuvuc_fk) as num from nhaphanphoi where khuvuc_fk='"+ id + "'";
	   		if(kiemtra(sql,db))
			{
				 sql ="select count(khuvuc_fk) as num from giamsatbanhang where khuvuc_fk='"+ id + "'";
				if(kiemtra(sql,db))
				{
					if(!Utility.KiemTra_PK_SEQ_HopLe( id, "khuvuc", db))
					{
						/*
						 * db.getConnection().rollback();
						 * 
						 * db.getConnection().setAutoCommit(true);
						 */
						msg = "Định danh không hợp lệ!";
					}
					db.update("delete from khuvuc where pk_seq = '" + id + "'");
					 
				}
				else
					msg = "Khu vực này đã tồn tại trong nhà phân phối rồi nên không thể xóa được";
		
			}
			else
				msg = "Khu vực này đã tồn tại trong nhà phân phối rồi nên không thể xóa được";
	   			
	   	}catch(Exception e){
	   		e.printStackTrace();
	   		msg = "Lỗi trong quá trình xóa!";
	   	}
	   	finally {
	   		db.shutDown();
	   	}
	   	System.out.println("msg: "+ msg);
	   	return msg;
	
		/*
		 * IKhuvucList obj = new KhuvucList(); try { dbutils db = new dbutils(); String
		 * sql ="select count(khuvuc_fk) as num from nhaphanphoi where khuvuc_fk='"+ id
		 * + "'"; if(kiemtra(sql)) { sql
		 * ="select count(khuvuc_fk) as num from giamsatbanhang where khuvuc_fk='"+ id +
		 * "'"; if(kiemtra(sql)) { if(!Utility.KiemTra_PK_SEQ_HopLe( id, "khuvuc", db))
		 * {
		 * 
		 * db.getConnection().rollback();
		 * 
		 * db.getConnection().setAutoCommit(true);
		 * obj.setMsg("Định danh không hợp lệ!"); }
		 * db.update("delete from khuvuc where pk_seq = '" + id + "'"); db.shutDown(); }
		 * else obj.
		 * setMsg("khu vuc nay da ton tai trong giam sat ban hang roi nen khong the xoa duoc"
		 * );
		 * 
		 * } else obj.
		 * setMsg("khu vuc nay da ton tai trong nha phan phoi roi nen khong the xoa duoc"
		 * ); } catch (SQLException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
	/*	ResultSet rs1 = db.get("select count(khuvuc_fk) as num from nhaphanphoi where khuvuc_fk='"+ id + "'");
		try{
			rs1.next();			
			if (rs1.getString("num").equals("0")){		
				db.update("delete from khuvuc where pk_seq = '" + id + "'");
				db.shutDown();
			}
		}catch(Exception e){}
		*/
	}

}
