package geso.dms.center.servlets.khaibaockdathang;

import geso.dms.center.beans.dondathang.imp.ErpDondathangList;
import geso.dms.center.beans.khaibaockdathang.IKhaibaoCKdathang;
import geso.dms.center.beans.khaibaockdathang.IKhaibaoCKdathanglist;
import geso.dms.center.beans.khaibaockdathang.imp.KhaibaoCKdathang;
import geso.dms.center.beans.khaibaockdathang.imp.KhaibaoCKdathanglist;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class KhaibaoCKdathangSvl extends HttpServlet {

	private static final long serialVersionUID = 1L;
    
    public KhaibaoCKdathangSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IKhaibaoCKdathanglist obj;
		PrintWriter out;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
	    obj = new KhaibaoCKdathanglist();
	    
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String ckdhId = util.getId(querystring);		  
	    if (action.equals("delete")){	   		  	    	
	    	Delete(ckdhId);
	    }
	    else if(action.equals("chot"))
	    {
	    	String msg = this.Chot(ckdhId);
			obj.setMsg(msg);
	    }
	    obj.setUserId(userId);
	    obj.init("");
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Center/KhaiBaoCKDatHang.jsp";
		response.sendRedirect(nextJSP);
	}

	private String Chot(String ckId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);

			String query = "update chietkhaudathang set trangthai = '1' where pk_seq = '" + ckId + "'";
			if(!db.update(query))
			{
				msg = "Khong the chot: " + query;
				db.getConnection().rollback();
				return msg;
			}

			db.getConnection().commit();
			db.shutDown();
		}
		catch (Exception e) 
		{
			db.update("roolback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}

		return "";
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IKhaibaoCKdathanglist obj;
		PrintWriter out;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out = response.getWriter();
	    
		HttpSession session = request.getSession();
	    String userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }

	    if (action.equals("new"))
	    {
	    	IKhaibaoCKdathang ckdhBean = (IKhaibaoCKdathang) new KhaibaoCKdathang("");
	    	ckdhBean.setUserId(userId);
	    	ckdhBean.createRs();
	    	// Save Data into session
	    	session.setAttribute("ckdhBean", ckdhBean);
    		
    		String nextJSP = "/AHF/pages/Center/KhaiBaoCKDatHangNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    if (action.equals("search"))
	    {
	    	obj = new KhaibaoCKdathanglist();
	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.init(search);
			obj.setUserId(userId);
			
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/AHF/pages/Center/KhaiBaoCKDatHang.jsp");	    	
	    	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IKhaibaoCKdathanglist obj)
	{
		String scheme = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("scheme"));
    	if ( scheme == null)
    		scheme = "";
    	obj.setScheme(scheme);
    	
    	String loaick = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaick"));
    	if ( loaick == null)
    		loaick = "";
    	obj.setLoaiCK(loaick);
    	
    	String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));
    	if ( trangthai == null)
    		trangthai = "";
    	obj.setLoaiCK(loaick);
    	
    	String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
    	if (tungay == null)
    		tungay = "";    	
    	obj.setTungay(tungay);
    	
    	String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
    	if (denngay == null)
    		denngay = "";    	
    	obj.setDenngay(denngay);
    	
    	String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
    	if (nppId == null)
    		nppId = "";    	
    	obj.setNppId(nppId);
    	
    	String query = "select distinct a.pk_seq as id, a.scheme, a.loaick, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua, a.tungay, a.denngay, isnull(a.diengiai, 'na') as diengiai, a.trangthai from chietkhaudathang a, nhanvien b, nhanvien c";
		query = query + " where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq"; 
		
    	if (nppId.length()>0){
    		query = "select distinct a.pk_seq as id, a.scheme, a.loaick, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua, a.tungay, a.denngay, isnull(a.diengiai, 'na') as diengiai, a.trangthai from chietkhaudathang a, nhanvien b, nhanvien c, chietkhaudathang_npp d ";
    		query = query + " where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.pk_seq = d.chietkhaudathang_fk "; 
			query = query + " and d.npp_fk = '" + nppId + "'";	
    	}
    		
		
    	if (scheme.length() > 0)
    	{
			query = query + " and a.scheme like N'%" + scheme + "%'";
    	}
    	
    	if (loaick.length()>0){
			query = query + " and a.loaick = '" + loaick + "'";	
    	}
  
    	if (trangthai.length()>0){
			query = query + " and a.trangthai = '" + trangthai + "'";	
    	}
    	
    	if (tungay.length()>0){
			query = query + " and a.denngay >= '" + tungay + "'";	
    	}
    	
    	if (denngay.length()>0){
			query = query + " and a.tungay <= '" + denngay + "'";	
    	}
    	
    	
    	System.out.println("Query la: " + query + "\n");
    	return query;
	}	
	
	private void Delete(String ckdhId)
	{
		dbutils db = new dbutils();
		try{
			db.update("delete chietkhaudathang_npp where chietkhaudathang_fk = '" + ckdhId + "'");
			db.update("delete chietkhaudathang_sp where chietkhaudathang_fk = '" + ckdhId + "'");
			db.update("delete chietkhaudathang_tieuchi where chietkhaudathang_fk = '" + ckdhId + "'");
			db.update("delete chietkhaudathang where pk_seq = '" + ckdhId + "'");
			db.shutDown();
		}catch(Exception e){}
	}
	
}
