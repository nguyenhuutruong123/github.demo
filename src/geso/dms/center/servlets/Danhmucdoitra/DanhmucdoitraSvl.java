package geso.dms.center.servlets.Danhmucdoitra;


import geso.dms.center.beans.Danhmucdoitra.IDanhmucdoitra;
import geso.dms.center.beans.Danhmucdoitra.IDanhmucdoitraList;
import geso.dms.center.beans.Danhmucdoitra.imp.Danhmucdoitra;
import geso.dms.center.beans.Danhmucdoitra.imp.DanhmucdoitraList;
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


public class DanhmucdoitraSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
	
    public DanhmucdoitraSvl()
    {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("da vao day");
		//ILoaicuahangList obj;
		IDanhmucdoitraList obj;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
	    obj = new DanhmucdoitraList();
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String hchId = util.getId(querystring);

	    if (action.equals("delete")){	   		  	    	
	    	Delete(hchId);
	    	out.print(hchId);
	    }
	   	
	    
	    obj.setUserId(userId);
	    obj.init("");
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Center/Danhmucdoitra.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		IDanhmucdoitraList obj;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out = response.getWriter();
	    obj = new DanhmucdoitraList();
		HttpSession session = request.getSession();
	    String userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    out.println(action); 
	        
	    
	    if (action.equals("new")){
	    	// Empty Bean for distributor
	    	IDanhmucdoitra lchBean = (IDanhmucdoitra) new Danhmucdoitra("");
	    	lchBean.setUserId(userId);
	    	// Save Data into session
	    	session.setAttribute("lchBean", lchBean);
    		
    		String nextJSP = "/AHF/pages/Center/DanhmucdoitraNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    if (action.equals("search")){	    
	    	String search = getSearchQuery(request,obj);
	    		
	    	obj.init(search);
			obj.setUserId(userId);
			
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/AHF/pages/Center/Danhmucdoitra.jsp");	    	
	    	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request,IDanhmucdoitraList obj){
		   // PrintWriter out = response.getWriter();
		
		//ILoaicuahangList obj = new LoaicuahangList();
			
			String loai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("LoaiCuaHang"));
	    	if ( loai == null)
	    		loai = "";
	    	obj.setLoaicuahang(loai);
	    	
	    	String diengiai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("DienGiai"));
	    	if (diengiai == null)
	    		diengiai = "";    	
	    	obj.setDiengiai(diengiai);
	    	
	    	String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("TrangThai"));   	
	    	if (trangthai == null)
	    		trangthai = "";    	
		
	    	if (trangthai.equals("2"))
	    		trangthai = "";
	    	
	    	obj.setTrangthai(trangthai);
	    	
	    	String query;
	    	query = "select a.pk_seq as id, a.loai, a.diengiai, a.trangthai, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua from loaicuahang a, nhanvien b, nhanvien c where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq"; 

	    	Utility util = new Utility();
	    	if (loai.length()>0){
				query = query + " and dbo.ftBoDau(upper(a.loai)) like upper(N'%" + util.replaceAEIOU(loai) + "%')";
				
	    	}
	    	
	    	if (diengiai.length()>0){
	    	
				query = query + " and dbo.ftBoDau(upper(a.diengiai)) like upper(N'%" + util.replaceAEIOU(diengiai) + "%')";
				
	    	}
	  
	    	if(trangthai.length() > 0){
	    		query = query + " and a.trangthai = '" + trangthai + "'";
	    		
	    	}
	    	query = query + " order by loai";
	    	//System.out.println("Tim kiem : "+query);
	    	return query;
		}	
	
	private void Delete(String id)
	{
		
		IDanhmucdoitraList obj = new DanhmucdoitraList();
		
		dbutils db = new dbutils();
		try{
			db.getConnection().setAutoCommit(false);
			if(db.update("delete from doitrahang where pk_seq = '" + id + "'"))
			{
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
				db.shutDown();
			}
			else
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				obj.setMsg("Không thể xóa đổi trả hàng này");
			}
		}catch(Exception e){}
		
	}

}
