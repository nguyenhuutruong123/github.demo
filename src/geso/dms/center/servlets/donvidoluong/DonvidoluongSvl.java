package geso.dms.center.servlets.donvidoluong;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.*;

import geso.dms.center.db.sql.*;
import geso.dms.center.beans.donvidoluong.imp.Donvidoluong;
import geso.dms.center.beans.donvidoluong.imp.DonvidoluongList;
import geso.dms.center.beans.donvidoluong.IDonvidoluong;
import geso.dms.center.beans.donvidoluong.IDonvidoluongList;
import geso.dms.distributor.util.Utility;

import java.sql.ResultSet;

public class DonvidoluongSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;

	public DonvidoluongSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		   
		    Utility util = new Utility();
		   //HttpServletRequest request;
		    IDonvidoluongList obj;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();	    
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    String msg = "";
	    out.println(userId);
	    obj = new DonvidoluongList();
		
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String donviId = util.getId(querystring);
	    out.println(donviId);
	    
	  //--- check user
	    String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		if (view == null) {
			view = "";
		}
		String param = "";
		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "DonvidoluongSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
		 //--- check user
	    
	    
	    
	    //Is a Nhan hang deleted?
	    if (action.equals("delete")){	   		
	    	 msg = Delete(donviId);
	    }
	    obj.setMsg(msg);
	    session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		
			
		String nextJSP = "/AHF/pages/Center/DonViDoLuong.jsp";
		response.sendRedirect(nextJSP);
		
	}  	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	    PrintWriter out = response.getWriter();
	    
	    Utility util = new Utility();
	   //HttpServletRequest request;
	    IDonvidoluongList obj ;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
		HttpSession session = request.getSession();
	    //this.request = request;
	    
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    
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
		if( Utility.CheckRuleUser( session,  request, response, "DonvidoluongSvl", param, Utility.XEM ))
		{
			//obj.DBClose();
			Utility.RedireactLogin(session, request, response);
		}
		 //--- check user
	    
	    
	    
	    // Perform searching. Each Business Unit is saved into Donvikinhdoanh
	    if (geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")).equals("search"))
	    {
	    	obj = new DonvidoluongList();
	    	
	    	String search = getSearchQuery(request,obj);
	    	
	    	obj.setQuery(search);
	    			    
			session.setAttribute("obj", obj);

    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/AHF/pages/Center/DonViDoLuong.jsp");
	    }
	    
	    // Create a new Business Unit
	    if (geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")).equals("new")){
	    	// Empty Bean for distributor
	    	IDonvidoluong dvdlBean = new Donvidoluong();
	    	
	    	// Save Data into session
    		session.setAttribute("dvdlBean", dvdlBean);
    		session.setAttribute("userId", userId);
    		
	    	String nextJSP = "/AHF/pages/Center/DonViDoLuongNew.jsp";
    		response.sendRedirect(nextJSP);
	    
	    }

	}

	private String Delete(String donviId){
		String msg = "";
		dbutils db  = new dbutils();
	   	
	   	try{
	   		ResultSet rs = db.get("select count(pk_seq) as num from sanpham where dvdl_fk ='" + donviId + "'");
	   	    while(rs.next())
	   	    {
	   	    	if(rs.getString("num").equals("0")){
		   			String command = "delete from donvidoluong where pk_seq ='" + donviId + "'";
		   			if(!db.update(command))
		   				msg="Lỗi trong quá trình xóa!";
		   			 
		   		}
		   		else 
		   			msg = "Đã có đơn vị đo lường này trong sản phẩm, nên không thể xóa được";
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


	private String getSearchQuery(ServletRequest request,IDonvidoluongList obj){
		
		obj.getDataSearch().clear();
		dbutils db  = new dbutils();
	    Utility util = new Utility();
	   //HttpServletRequest request;
		
//	    PrintWriter out = response.getWriter();
		String dvdl = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvdl")));
    	if (dvdl == null)
    		dvdl = "";
    	obj.setDvdl(dvdl);
    	
		String diengiai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai"));
    	if (diengiai == null)
    		diengiai = "";
    	 
    	obj.setDiengiai(diengiai);
    	
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
	
    	if (trangthai.equals("2"))
    		trangthai = "";
    	obj.setTrangthai(trangthai);
    	
    	String query = "select a.pk_seq, a.donvi, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from donvidoluong a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ ";
    	
    	if (dvdl.length()>0){
			query = query + " and upper(dbo.ftBoDau(a.donvi)) like upper(?)";
			
			obj.getDataSearch().add( "%" +util.replaceAEIOU(dvdl) + "%"  );
    	}

    	if (diengiai.length()>0){
			query = query + " and upper(dbo.ftBoDau(a.diengiai)) like upper(?)";
			obj.getDataSearch().add( "%" +util.replaceAEIOU(diengiai) + "%"  );
			
    	}
    	
    	if (tungay.length() > 0) {
    		query = query + " and a.ngaytao >= ?";
    		obj.getDataSearch().add(  tungay );
    	}
    	
    	if (denngay.length() > 0) {
    		query = query + " and a.ngaytao <= ?";
    		obj.getDataSearch().add(  denngay );
    	}
    	
    	if(trangthai.length() > 0){
    		query = query + " and a.trangthai = ?";
    		obj.getDataSearch().add(  trangthai );
    	}
    	System.out.println("Tim kiem : "+query);
		return query;
	}		
}