package geso.dms.center.servlets.ngayle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.*;
import java.sql.ResultSet;

import geso.dms.center.db.sql.*;
import geso.dms.center.beans.ngayle.INgayLe;
import geso.dms.center.beans.ngayle.INgayLeList;
import geso.dms.center.beans.ngayle.imp.NgayLe;
import geso.dms.center.beans.ngayle.imp.NgayLeList;
import geso.dms.center.beans.nhacungcap.imp.Nhacungcap;
import geso.dms.center.beans.nhacungcap.imp.NhacungcapList;
import geso.dms.center.beans.nhacungcap.INhacungcap;
import geso.dms.center.beans.nhacungcap.INhacungcapList;
import geso.dms.distributor.util.Utility;

public class NgayLeSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   

   public NgayLeSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		dbutils db;
		
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    HttpSession session = request.getSession();
	    String command = "";     
	    
	    String querystring = request.getQueryString();
    	
	    Utility util = new Utility();
    	String userId = util.getUserId(querystring);
    	if (userId == null)
    		userId = "";
    	
    	String action = util.getAction(querystring);
    	if (action == null)
    		action = "";
    	
    	String id = util.getId(querystring);
    	if (id == null)
    		id = "";
    	
    	String param = "";
    	
    	if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "NgayLeSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
    	
    	INgayLeList obj = new NgayLeList();
    	String msg = "";
    	if (action.equals("delete")){
    		int[] quyen = geso.dms.center.util.Utility.Getquyen("NgayLeSvl", "",userId);
			if(quyen[Utility.XOA]==1)
			{
	    		db  = new dbutils();
	    		//command = "select count (pk_seq) as num from nhacungcap_dvkd where ncc_fk='" + id + "' and checked ='1'";
	    		command = "select count (pk_seq) as num from ngayle ";
	    		System.out.println(command);
	    		ResultSet rs = db.get(command);
	    		if(rs!=null)
					try {
						if(rs.next()){
								command = "delete from ngayle where pk_seq ='" + id + "'";
								db.update(command);		
							}
					if(rs!=null) rs.close();
		        	if(db!=null) db.shutDown();
					} catch(Exception e) {
					
						e.printStackTrace();
					}
			}else{
				msg = "User không được phân quyền xóa";
			}
	   	}
		   	
	   	if (action.equals("update")){
	   		int[] quyen = geso.dms.center.util.Utility.Getquyen("NgayLeSvl", "",userId);
			if(quyen[Utility.SUA]==1)
			{
		   		INgayLe ncc = new NgayLe();		   		
		   		ncc.setId(id);
		   		ncc.init();
		   		 
		       	session.setAttribute("ncc", ncc);
		       	session.setAttribute("userId", userId);
		        String nextJSP = "/AHF/pages/Center/NgayLeUpdate.jsp";
		       	response.sendRedirect(nextJSP);
			}else{
				msg = "User không được phân quyền sửa";
			}
	   	}else{
	    
	   		//INhacungcapList obj = new NhacungcapList();	    	
	    	
	   		obj.init();
		 
	   		//Data object is saved into session	   		
	   		session.setAttribute("obj", obj);
	   			   		
	   		// userId is saved into session
	   		session.setAttribute("userId", userId);
			
	   		String nextJSP = "/AHF/pages/Center/NgayLe.jsp";
	   		response.sendRedirect(nextJSP);	   			   		   			   		
	   	}
		 
   	  	    
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    Utility util = new Utility();
	    HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    INgayLeList obj = new NgayLeList();
	    // Perform searching. Each distributor is saved into Nhacungcap
	    if (geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")).equals("search")){
	    	String ngaytao = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaytao")));
	    	if(ngaytao == null)
	    		ngaytao = "";
	    	String ngaysua = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaysua")));
	    	if(ngaysua == null)
	    		ngaysua = "";
	    	String nguoitao = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nguoitao")));
	    	if(nguoitao == null)
	    		 nguoitao = "";
	    	String nguoisua = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nguoisua")));
	    	if(nguoisua == null)
	    		nguoisua = "";
	    	String ngayle = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngayle")));
	    	if(ngayle == null)
	    		ngayle = "";
	    	String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
	    	if(diengiai == null)
	    		diengiai = "";

	    	String query = "";
	    	
	    	query = "select a.pk_seq, a.ngaysua, a.ngaytao,a.nguoisua,a.nguoitao, a.ngayle, a.diengiai "
	    			+ "from ngayle a, nhanvien b, nhanvien c where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq "; 
	    		
	    	
	    	if (ngaytao.length()>0){
	    		obj.setNgaytao(ngaytao);
	    		query = query + " and upper(a.ngaytao) = upper(?)";
	    		obj.getDataSearch().add("" + ngaytao + "");
	    	}

	    	if (ngaysua.length()>0){
	    		obj.setNgaysua(ngaysua);
	    		query = query + " and upper(a.ngaysua) = upper(?)"; 
	    		obj.getDataSearch().add("" + ngaysua + "");
	    	}

	    	//out.println(query);
	    	if (nguoisua.length() > 0){
	    		obj.setNguoisua(nguoisua);
	    		query = query + " and a.nguoisua = ?" ; 
	    		obj.getDataSearch().add( ""+nguoisua+"" );
	    	}
	    	 
	    	if (nguoitao.length() > 0){
	    		obj.setNguoitao(nguoitao);
	    		query = query + " and a.nguoitao = ?" ; 
	    		obj.getDataSearch().add( ""+nguoitao+"" );
	    	}
	    	
	    	if (ngayle.length() > 0){
	    		obj.setNgayle(ngayle);
	    		query = query + " and a.ngayle = ?" ; 
	    		obj.getDataSearch().add("" + ngayle + "");
	    	}
	    	if (diengiai.length() > 0){
	    		obj.setDiengiai(diengiai);
	    		query = query + "and upper(a.diengiai) like upper(?)" ; 
	    		obj.getDataSearch().add("%" + (diengiai) + "%");
	    	}
	    	
    		    	
	    	//System.out.print(query);
	    	obj.setQuery(query);
	    	obj.init();
			
	    	//Data object is saved into session
	    	session.setAttribute("obj", obj);
			
	    	// userId is saved into session
	    	session.setAttribute("userId", userId);
			
	    	String nextJSP = "/AHF/pages/Center/NgayLe.jsp";
	    	response.sendRedirect(nextJSP);
	    }
	    String msg = "";
	    // Create a new distributor
	    if (geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")).equals("new")){
	    	int[] quyen = geso.dms.center.util.Utility.Getquyen("NgayLeSvl", "",userId);
			if(quyen[Utility.THEM]==1)
			{
		    	// Empty Bean for distributor
		    	INgayLe nccBean = new NgayLe();
		    	
		    	// Save Distributor into session
	    		session.setAttribute("nccBean", nccBean);
	    		session.setAttribute("userId", userId);
		    	String nextJSP = "/AHF/pages/Center/NgayLeNew.jsp";
	    		response.sendRedirect(nextJSP);
			}else{
				msg = "User không được phân quyền thêm";
			}
	    
	    }
	  
	}
}