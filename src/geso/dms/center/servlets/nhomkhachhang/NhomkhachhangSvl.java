package geso.dms.center.servlets.nhomkhachhang;

import geso.dms.center.beans.nhomkhachhang.INhomkhachhang;
import geso.dms.center.beans.nhomkhachhang.imp.Nhomkhachhang;
import geso.dms.center.beans.nhomkhachhang.INhomkhachhangList;
import geso.dms.center.beans.nhomkhachhang.imp.NhomkhachhangList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NhomkhachhangSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   PrintWriter out;
   
	public NhomkhachhangSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		
		   INhomkhachhangList obj;
		 
	
	    response.setContentType("text/html");
	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    out = response.getWriter();
	    obj = new NhomkhachhangList();
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String nspId = util.getId(querystring);

	    if (action.equals("delete")){	   		  	    	
	    	Delete(nspId);

	    }

	    List<INhomkhachhang> nkhlist = new ArrayList<INhomkhachhang>(); 
	    
	    getNkhBeanList(nkhlist,"");
	    
		// Save data into session
	    obj.setNkhList(nkhlist);
	    
		session.setAttribute("obj", obj);

		String nextJSP = "/AHF/pages/Center/NhomKhachHang.jsp";
		response.sendRedirect(nextJSP);
	    
	}  	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		
		INhomkhachhangList obj;
		   
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    
		HttpSession session = request.getSession();
	    String userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));

	    obj = new NhomkhachhangList();
	
	    String makhachhang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("maKH"));
	    if(makhachhang==null) makhachhang="";
	    obj.setMaKH(makhachhang);
	    
	    String diengiai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai"));
	    if(diengiai==null) diengiai="";
	    obj.setDiengiai(diengiai);
	    
	    String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));
	    if(trangthai==null) trangthai="";
	    obj.setTrangthai(trangthai);
	    
	    String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
	    if(tungay==null) tungay="";
	    obj.setTungay(tungay);
	    
	    String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
	    if(denngay==null) denngay="";
	    obj.setDenngay(denngay);

	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    out.println(action); 
	    
	    System.out.println("Action Post: "+action);
	  
	    if (action.equals("new")){
	    
	    	INhomkhachhang nkhBean = (INhomkhachhang) new Nhomkhachhang();
	    	
	    	nkhBean.UpdateRS();
	    	// Save Data into session
    		session.setAttribute("nkhBean", nkhBean);
    		session.setAttribute("userId", userId);
    		

    		String nextJSP = "/AHF/pages/Center/NhomKhachHangNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    if (action.equals("search")){
	    	
		    	String search = getSearchQuery(request);
		    	System.out.print(search);
		    	
//		    	out.println(search);
		    	List<INhomkhachhang> nkhlist = new ArrayList<INhomkhachhang>(); 
		    	getNkhBeanList(nkhlist, search);	    	

	    		// Saving data into session
			    obj.setNkhList(nkhlist);
			    obj.setSearch(true);
				session.setAttribute("obj", obj);

	    		session.setAttribute("userId", userId);
		    		
	    		response.sendRedirect("/AHF/pages/Center/NhomKhachHang.jsp");
		    }
	    if (action.equals("1")){
	    	List<INhomkhachhang> nkhlist = new ArrayList<INhomkhachhang>(); 
	    
	    	getNkhBeanList(nkhlist, "");
	    
		// 	Save data into session
	    	obj.setNkhList(nkhlist);
	    
	    	session.setAttribute("obj", obj);
	    	session.setAttribute("userId", userId);

	    	String nextJSP = "/AHF/pages/Center/NhomKhachHang.jsp";
	    	response.sendRedirect(nextJSP);
	    }
	}

	private void Delete(String nkhId){
	    
		//HttpServletRequest request;
		  //HttpServletResponse response;
		   INhomkhachhangList obj;
		   dbutils db = new dbutils();
		
		String command;
		
		command = "delete from nhomkhachhang_khachhang where nkh_fk ='" + nkhId + "'";
		db.update(command);

		command = "delete from nhomkhachhang where pk_seq ='" + nkhId + "'";
	   	db.update(command);
		
	}

	
	private void  getNkhBeanList(List<INhomkhachhang> nkhlist, String search){	
	    String query;
	    
	    
	  //HttpServletRequest request;
		  //HttpServletResponse response;
		   INhomkhachhangList obj;
		   dbutils db = new dbutils();
	    
	    if (search.length() > 0){
	    	query = search;
	    }else{
	    	query = "select a.pk_seq,isnull(a.ten,'') as ten, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from nhomkhachhang a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ order by diengiai";
	    }
	   	ResultSet rs = db.get(query);
	   	try{	
	   		String[] param = new String[11];
    		INhomkhachhang nkhBean;
    		while (rs.next()){	    			
				param[0]= rs.getString("pk_seq");
				param[1]= rs.getString("diengiai");				
				param[2]= rs.getString("trangthai");
				param[3]= rs.getString("ngaytao");
				param[4]= rs.getString("ngaysua");
				param[5]= rs.getString("nguoitao");
				param[6]= rs.getString("nguoisua");			
				param[7] = rs.getString("ten");
				nkhBean = new Nhomkhachhang(param);					
				nkhlist.add(nkhBean);
    		}    		
	   	}catch(Exception e){}
	}
	

	private String getSearchQuery(ServletRequest request){
//	    PrintWriter out = response.getWriter();
		geso.dms.distributor.util.Utility util = new geso.dms.distributor.util.Utility();

		
		//HttpServletRequest request;
		  //HttpServletResponse response;
		   INhomkhachhangList obj = new NhomkhachhangList();
		   dbutils db = new dbutils();
		
		String maKH = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("maKH"));
		if(maKH == null)
			maKH = "";
		
		String diengiai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai"));
    	if (diengiai == null)
    		diengiai = "";
   	
    	   		 
    	String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
    	if (tungay == null)
    		tungay = "";    	
    	
    	String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
    	if (denngay == null)
    		denngay = "";    	

    	String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));   	
    	if (trangthai == null)
    		trangthai = "";    	
	
    	if (trangthai.equals("2"))
    		trangthai = "";

    	//String query = "select a.pk_seq, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from nhomkhachhang a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ ";
    	String query = " select distinct a.pk_seq, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua " +
    			      " from nhomkhachhang a, nhomkhachhang_khachhang kh_nkh, khachhang kh, nhanvien b, nhanvien c " +
    			      " where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ  and a.pk_seq = kh_nkh.nkh_fk and kh_nkh.kh_fk = kh.pk_seq";
    	
    	if(maKH.length() > 0){
    		query += " and upper(kh.pk_seq) like upper('%" + maKH + "%')";
    		obj.setMaKH(maKH);
    	}
    	if (diengiai.length()>0){
			query = query + " and upper(dbo.ftBoDau(a.diengiai)) like upper(N'%" + util.replaceAEIOU(diengiai) + "%')";
			obj.setDiengiai(diengiai);
    	}
    	
    	if (tungay.length() > 0) {
    		query = query + " and a.ngaytao >= '" + tungay + "'";
    		obj.setTungay(tungay);
    	}
    	
    	if (denngay.length() > 0) {
    		query = query + " and a.ngaytao <= '" + denngay + "'";
    		obj.setDenngay(denngay);
    	}
    	
    	if(trangthai.length() > 0){
    		query = query + " and a.trangthai = '" + trangthai + "'";
    		obj.setTrangthai(trangthai);
    	}
    	query = query + "  order by a.diengiai";
    	System.out.print(query);
    	return query;
	}
	


}
