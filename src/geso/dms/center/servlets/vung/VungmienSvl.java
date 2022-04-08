package geso.dms.center.servlets.vung;


import geso.dms.center.beans.vung.*;
import geso.dms.center.beans.vung.imp.*;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class VungmienSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
	
    public VungmienSvl()
    {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    IVungmienList obj;    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    out = response.getWriter();
	    obj = new VungmienList();  
	    obj.init("");
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String vmId = util.getId(querystring);

		//--- check user
	
		//obj.setView(view);
		String param = "";
		
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			//obj.DBClose();
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "VungmienSvl", param, Utility.XEM ))
		{
			//obj.DBClose();
			Utility.RedireactLogin(session, request, response);
		}
		 //--- check user
		
		
	    if (action.equals("delete")){	   		  	    	
	    	obj.setMsg(Delete(vmId, obj));
	    	out.print(vmId);
	    }
	   	
	   
	    
	    obj.setUserId(userId);
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Center/VungMien.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IVungmienList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out = response.getWriter();
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
			//obj.DBClose();
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "VungmienSvl", param, Utility.XEM ))
		{
			//obj.DBClose();
			Utility.RedireactLogin(session, request, response);
		}
		 //--- check user
	    
	    if (action.equals("new")){
	    	// Empty Bean for distributor
	    	IVungmien vmBean = (IVungmien) new Vungmien("");
	    	vmBean.setUserId(userId);
	    	// Save Data into session
	    	session.setAttribute("vmBean", vmBean);
    		
    		String nextJSP = "/AHF/pages/Center/VungMienNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	   if (action.equals("search"))	    
	    {obj = new VungmienList();
    	
	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.init(search);
			obj.setUserId(userId);
			
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/AHF/pages/Center/VungMien.jsp");	    	
	    	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IVungmienList obj){
		obj.getDataSearch().clear();
		   // PrintWriter out = response.getWriter();
			Utility util = new Utility();
			String vungmien = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("VungMien")));
	    	if ( vungmien == null)
	    		vungmien = "";
	    	obj.setVungmien(vungmien);
	    	
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
	    	
	    	String query;
	    	query = "select a.pk_seq as id,isnull(a.ma,'') as ma, a.ten, a.diengiai, a.trangthai, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua"; 
			query = query + " from vung a, nhanvien b, nhanvien c where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq";

	    	if (vungmien.length()>0){
				query = query + " and upper(dbo.ftBoDau(a.ten)) like upper(?)";
				obj.getDataSearch().add( util.replaceAEIOU(vungmien) );
				
	    	}
	    	
	    	if (diengiai.length()>0){
				query = query + " and upper(dbo.ftBoDau(a.diengiai)) like upper(?)";
				obj.getDataSearch().add( util.replaceAEIOU(diengiai) );
	    	}
	  
	    	if(trangthai.length() > 0){
	    		query = query + " and a.trangthai = ?";
	    		obj.getDataSearch().add( trangthai );
	    	}
	    	query = query + " order by ten";
	    	System.out.println("sql:"+ query);
	    	return query;
		}	
	
	private String Delete(String id, IVungmienList obj)
	{
		
		dbutils db = new dbutils();
		String msg = "";
		
		try{
	   		ResultSet rs = db.get("select count(pk_seq) as num from sanpham where dvdl_fk ='" + id + "'");
	   	    while(rs.next())
	   	    {
	   	    	if(!Utility.KiemTra_PK_SEQ_HopLe( id, "vung", db))
				{
					/*
					 * db.getConnection().rollback(); db.getConnection().setAutoCommit(true);
					 */
					msg = "Định danh không hợp lệ!";
				}
	   	    	if(rs.getString("num").equals("0")){
		   			String command = "delete from vung where pk_seq ='" + id + "'";
		   			if(!db.update(command))
		   				msg="Lỗi trong quá trình xóa!";
		   			 
		   		}
		   		else 
		   			msg = "Vùng này đã có khu vực rồi, không xóa được!";
	   	    }
	   		rs.close();
	   			
	   	}catch(Exception e){
	   		e.printStackTrace();
	   		msg = "Lỗi trong quá trình xóa!";
	   	}
	   	finally {
	   		db.shutDown();
	   	}
		return msg;
	}
}

