package geso.dms.center.servlets.hinhthuckinhdoanh;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.*;

import geso.dms.center.db.sql.*;
import geso.dms.center.beans.hinhthuckinhdoanh.imp.Hinhthuckinhdoanh;
import geso.dms.center.beans.hinhthuckinhdoanh.imp.HinhthuckinhdoanhList;
import geso.dms.center.beans.hinhthuckinhdoanh.IHinhthuckinhdoanh;
import geso.dms.center.beans.hinhthuckinhdoanh.IHinhthuckinhdoanhList;
import geso.dms.distributor.util.Utility;

import java.sql.ResultSet;

public class HinhthuckinhdoanhSvl extends javax.servlet.http.HttpServlet
		implements javax.servlet.Servlet {

	static final long serialVersionUID = 1L;

	public HinhthuckinhdoanhSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		dbutils db = new dbutils();
		Utility util = new Utility();
		// HttpServletRequest request;
		IHinhthuckinhdoanhList obj;

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		out.println(userId);
		obj = new HinhthuckinhdoanhList();

		if (userId.length() == 0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

		String action = util.getAction(querystring);
		out.println(action);

		String hinhthucId = util.getId(querystring);
		out.println(hinhthucId);

		// Is a Nhan hang deleted?
		if (action.equals("delete")) {
			Delete(hinhthucId);
		}
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);

		String nextJSP = "/AHF/pages/Center/HinhThucKinhDoanh.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	    PrintWriter out = response.getWriter();
	    
		dbutils db  = new dbutils();
	    Utility util = new Utility();
	   //HttpServletRequest request;
	    IHinhthuckinhdoanhList obj;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
		HttpSession session = request.getSession();
	    //this.request = request;
	    
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    // Perform searching. Each Business Unit is saved into Donvikinhdoanh
	    if (geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")).equals("search"))
	    {
	    	obj = new HinhthuckinhdoanhList();
	    	
	    	String search = getSearchQuery(request,obj);
	    	
	    	obj.setQuery(search);
	    			    
			session.setAttribute("obj", obj);

    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/AHF/pages/Center/HinhThucKinhDoanh.jsp");
	    }
	    
	    // Create a new Business Unit
	    if (geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")).equals("new")){
	    	// Empty Bean for distributor
	    	IHinhthuckinhdoanh htkdBean = new Hinhthuckinhdoanh();
	    	
	    	// Save Data into session
    		session.setAttribute("htkdBean", htkdBean);
    		session.setAttribute("userId", userId);
    		
	    	String nextJSP = "/AHF/pages/Center/HinhThucKinhDoanhNew.jsp";
    		response.sendRedirect(nextJSP);
	    
	    }

	}

	private void Delete(String htkdId){
		
		dbutils db  = new dbutils();
	    Utility util = new Utility();
	   //HttpServletRequest request;
	    IHinhthuckinhdoanhList obj = new HinhthuckinhdoanhList();
		
	   	db = new dbutils();
//	   	ResultSet rs = db.get("select count(pk_seq) as num from sanpham where dvdl_fk ='" + htkdId + "'");
	   	try{
//	   	    while(rs.next())
//	   		if(rs.getString("num").equals("0")){
	   			String command = "delete from hinhthuckinhdoanh where pk_seq ='" + htkdId + "'";
	   			db.update(command);
	   			db.shutDown();
//	   		}
//	   		else
//	   			obj.setMsg("Don vi do luong nay da co san pham roi, khong the xoa duoc");
	   	}catch(Exception e){}
	}


	private String getSearchQuery(ServletRequest request,IHinhthuckinhdoanhList obj){
		
		dbutils db  = new dbutils();
	    Utility util = new Utility();
	   //HttpServletRequest request;
		
//	    PrintWriter out = response.getWriter();
		String ma = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ma")));
    	if (ma == null)
    		ma = "";
    	obj.setMa(ma);;
    	
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
    	
    	String query = "select a.pk_seq, a.ma, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from hinhthuckinhdoanh a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ ";
    	
    	if (ma.length()>0){
			query = query + " and upper(dbo.ftBoDau(a.ma)) like upper(N'%" + util.replaceAEIOU(ma) + "%')";
			
    	}

    	if (diengiai.length()>0){
			query = query + " and upper(dbo.ftBoDau(a.diengiai)) like upper(N'%" + util.replaceAEIOU(diengiai) + "%')";
			
    	}
    	
    	if (tungay.length() > 0) {
    		query = query + " and a.ngaytao >= '" + tungay + "'";
    	}
    	
    	if (denngay.length() > 0) {
    		query = query + " and a.ngaytao <= '" + denngay + "'";
    	}
    	
    	if(trangthai.length() > 0){
    		query = query + " and a.trangthai = '" + trangthai + "'";
    	}
    	System.out.println("Tim kiem : "+query);
		return query;
	}		
	
}
