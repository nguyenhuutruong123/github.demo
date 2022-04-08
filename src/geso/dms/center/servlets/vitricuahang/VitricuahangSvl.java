package geso.dms.center.servlets.vitricuahang;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import geso.dms.center.db.sql.*;
import geso.dms.center.util.Utility;
import geso.dms.center.beans.vitricuahang.*;
import geso.dms.center.beans.vitricuahang.imp.*;

 public class VitricuahangSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
		private static final long serialVersionUID = 1L;
		
		PrintWriter out;
		
	    public VitricuahangSvl()
	    {
	        super();
	    }

		
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
			IVitricuahangList obj;
			
			request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    this.out  = response.getWriter();
		    	    
		    HttpSession session = request.getSession();	    

		    Utility util = new Utility();
		    out = response.getWriter();
		    obj = new VitricuahangList();    
		    String querystring = request.getQueryString();
		    String userId = util.getUserId(querystring);
		    out.println(userId);
		    
		    String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		    if (view == null) 
		    	view = "";

		    String param = "";
		    if(view.trim().length() > 0) param ="&view="+view;
		    if ( Utility.CheckSessionUser( session,  request, response)) {
		    	Utility.RedireactLogin(session, request, response);
		    }
		    if( Utility.CheckRuleUser( session,  request, response, "VitricuahangSvl", param, Utility.XEM )) {
		    	Utility.RedireactLogin(session, request, response);
		    }
		    
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		    
		    String action = util.getAction(querystring);
		    out.println(action);
		    
		    String vtchId = util.getId(querystring);

		    if (action.equals("delete")){	   		  	    	
		    	if(view.trim().length() > 0) param = "&view=" + view;
		    	int[] quyen = Utility.Getquyen("VitricuahangSvl", param, userId);
		    	if(quyen[Utility.XOA] != 1) {
		    		return;
		    	}
		    	
		    	Delete(vtchId,obj);
		    	out.print(vtchId);
		    }
		   	
		  
		    obj.setUserId(userId);
		    obj.init("");
			session.setAttribute("obj", obj);
					
			String nextJSP = "/AHF/pages/Center/ViTriCuaHang.jsp";
			response.sendRedirect(nextJSP);
		}

		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
		{
			
			IVitricuahangList obj;
			
			request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    this.out = response.getWriter();
		    obj = new VitricuahangList();
		    Utility util = new Utility();
		    
			HttpSession session = request.getSession();
		    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		    
		    String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		    if (view == null) 
		    	view = "";
		    obj.setView(view);

		    String param = "";
		    if(view.trim().length() > 0) param ="&view="+view;
		    if ( Utility.CheckSessionUser( session,  request, response)) {
		    	obj.closeDB();
		    	Utility.RedireactLogin(session, request, response);
		    }
		    if( Utility.CheckRuleUser( session,  request, response, "VitricuahangSvl", param, Utility.XEM )) {
		    	obj.closeDB();
		    	Utility.RedireactLogin(session, request, response);
		    }
		    
		    String vitri = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ViTriCuaHang")));
		    if(vitri==null) vitri="";
		    obj.setVitricuahang(vitri);
		    
		    String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("DienGiai")));
		    if(diengiai==null) diengiai="";
		    obj.setDiengiai(diengiai);
		    
		    String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("TrangThai")));
		    if(trangthai==null) trangthai="";
		    obj.setTrangthai(trangthai);
		    
		    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		    if (action == null){
		    	action = "";
		    }
		    out.println(action); 
		        
		   
		    
		    if (action.equals("new")){
		    	// Empty Bean for distributor
		    	IVitricuahang vtchBean = (IVitricuahang) new Vitricuahang("");
		    	vtchBean.setUserId(userId);
		    	// Save Data into session
		    	session.setAttribute("vtchBean", vtchBean);
	    		
	    		String nextJSP = "/AHF/pages/Center/ViTriCuaHangNew.jsp";
	    		response.sendRedirect(nextJSP);
	    		
		    }
		    if (action.equals("search")){
		    	String search = getSearchQuery(request, obj);
		    	
		    	//obj = new VitricuahangList(search);
		    	obj.init(search);
				obj.setUserId(userId);
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		    		
	    		response.sendRedirect("/AHF/pages/Center/ViTriCuaHang.jsp");	    	
		    	
		    }
		}
		
		private String getSearchQuery(HttpServletRequest request, IVitricuahangList obj){
			   // PrintWriter out = response.getWriter();
			
			Utility util = new Utility();
				
			String vitri = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ViTriCuaHang")));
	    	if ( vitri == null)
	    		vitri = "";
	    	obj.setVitricuahang(vitri);
	    	
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
	    	query = "select a.pk_seq as id, a.vitri, a.diengiai, a.trangthai, a.ngaytao, b.ten as nguoitao, a.ngaysua, b.ten as nguoisua"; 
			query = query + " from vitricuahang a, nhanvien b, nhanvien c where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq";
			//geso.dms.distributor.util.Utility util = new geso.dms.distributor.util.Utility();
	    	if (vitri.length()>0){
				query = query + " and upper(dbo.ftBoDau(a.vitri)) like upper(?)";
				obj.getDataSearch().add( "%" + util.replaceAEIOU(vitri) + "%" );
	    	}
	    	
	    	if (diengiai.length()>0){
				query = query + " and upper(dbo.ftBoDau(a.diengiai)) like upper(?)";
				obj.getDataSearch().add( "%" + util.replaceAEIOU(diengiai) + "%" );
	    	}
	  
	    	if(trangthai.length() > 0){
	    		query = query + " and a.trangthai = ?";
	    		obj.getDataSearch().add(trangthai);
	    	}
	    	query = query + "  order by vitri";
	    	return query;
		}	
		
		private void Delete(String id,IVitricuahangList obj)
		{
			dbutils db = new dbutils();
			
			ResultSet rs1 = db.get("select count(vtch_fk) as num from khachhang where vtch_fk='"+ id + "'");
			try{
				rs1.next();			
				if (rs1.getString("num").equals("0")){
					db.update("delete from vitricuahang where pk_seq = '" + id + "'");
					db.shutDown();
				}
				else
					obj.setMsg("vị trí này đã phát sinh dữ liệu nên không thể xóa");
			}catch(Exception e){}				
		}

	}
