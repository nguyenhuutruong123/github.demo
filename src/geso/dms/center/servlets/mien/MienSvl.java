package geso.dms.center.servlets.mien;


import geso.dms.center.beans.mien.*;
import geso.dms.center.beans.mien.imp.*;
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


public class MienSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
	
    public MienSvl()
    {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    IMienList obj;    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    out = response.getWriter();
	    obj = new MienList();    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String vmId = util.getId(querystring);

	    if (action.equals("delete")){	   		  	    	
	    	Delete(vmId, obj);
	    	out.print(vmId);
	    }
	   	
	   
	    obj.setUserId(userId);
	    obj.init("");
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Center/Mien.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IMienList obj;
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
	        
	    
	    if (action.equals("new")){
	    	// Empty Bean for distributor
	    	IMien vmBean = (IMien) new Mien("");
	    	vmBean.setUserId(userId);
	    	// Save Data into session
	    	session.setAttribute("vmBean", vmBean);
    		
    		String nextJSP = "/AHF/pages/Center/MienNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	   if (action.equals("search"))	    
	    {obj = new MienList();
    	
	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.init(search);
			obj.setUserId(userId);
			
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/AHF/pages/Center/Mien.jsp");	    	
	    	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IMienList obj){
		   // PrintWriter out = response.getWriter();
			Utility util = new Utility();
			String vungmien = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("VungMien")));
	    	if ( vungmien == null)
	    		vungmien = "";
	    	obj.setMien(vungmien);
	    	
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
			query = query + " from Mien a, nhanvien b, nhanvien c where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq";

	    	if (vungmien.length()>0){
				query = query + " and upper(dbo.ftBoDau(a.ten)) like upper(N'%" + util.replaceAEIOU(vungmien) + "%')";
				
	    	}
	    	
	    	if (diengiai.length()>0){
				query = query + " and upper(dbo.ftBoDau(a.diengiai)) like upper(N'%" + util.replaceAEIOU(diengiai) + "%')";
				
	    	}
	  
	    	if(trangthai.length() > 0){
	    		query = query + " and a.trangthai = '" + trangthai + "'";
	    		
	    	}
	    	query = query + " order by ten";
	    	System.out.println("sql:"+ query);
	    	return query;
		}	
	
	private void Delete(String id, IMienList obj)
	{
		dbutils db = new dbutils();
		ResultSet rs1 = db.get("select count(vung_fk) as num from Vung where Mien_fk='"+ id + "'");
		try{
			rs1.next();			
			if (rs1.getString("num").equals("0")){
				db.update("delete from Mien where pk_seq = '" + id + "'");
				db.shutDown();
			}
			else
				obj.setMsg("Miền nay da co vùng roi, nen khong the xoa duoc");
		}catch(Exception e){}
	}

}

