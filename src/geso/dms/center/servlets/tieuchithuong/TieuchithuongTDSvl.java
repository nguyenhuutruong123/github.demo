package geso.dms.center.servlets.tieuchithuong;

import geso.dms.center.beans.tieuchithuong.ITieuchithuongTD;
import geso.dms.center.beans.tieuchithuong.ITieuchithuongTDList;
import geso.dms.center.beans.tieuchithuong.imp.TieuchithuongTD;
import geso.dms.center.beans.tieuchithuong.imp.TieuchithuongTDList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TieuchithuongTDSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
   
    public TieuchithuongTDSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    ITieuchithuongTDList obj = new TieuchithuongTDList();
	    obj.setUserId(userId);
	    
	    String action = util.getAction(querystring);
	    String ctskuId = util.getId(querystring);
	    
	    //System.out.println("___Action: " + action + " -- Id la: " + ctskuId);
	    if(action.trim().equals("duyet"))
	    {
	    	dbutils db = new dbutils();
	    	db.update("update TieuchithuongTD set trangthai = '1' where pk_seq = '" + ctskuId + "'");
	    	db.shutDown();
	    }
	    
	    if(action.trim().equals("delete"))
	    {
	    	XoaChiTieu(ctskuId);
	    }

	    obj.init("");
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/AHF/pages/Center/TieuChiThuongTD.jsp";
		response.sendRedirect(nextJSP);
	}

	private void XoaChiTieu(String ctskuId) 
	{
		dbutils db = new dbutils();
    	
    	try 
    	{
			db.getConnection().setAutoCommit(false);
			
			if(!db.update("delete TieuchithuongTD_SANPHAM where thuongtd_fk = '" + ctskuId + "'"))
	    	{
	    		db.getConnection().rollback();
				return;
	    	}
			
			if(!db.update("delete TieuchithuongTD_NPP where thuongtd_fk = '" + ctskuId + "'"))
	    	{
	    		db.getConnection().rollback();
				return;
	    	}
	    	
	    	if(!db.update("delete TieuchithuongTD where pk_seq = '" + ctskuId + "'"))
			{
				db.getConnection().rollback();
				return;
			}
	    	db.getConnection().commit();
	    	db.shutDown();
		} 
    	catch (Exception e)
    	{
    		try 
    		{
				db.getConnection().rollback();
			} catch (SQLException e1) {}
    	}
    	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    
	    out = response.getWriter();
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));     
	    ITieuchithuongTDList obj;
	    
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    
	    //AJAX
	    String type = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("type"));
	    if(type == null)
	    	type = "";
	    
	    if(type.equals("Ajax"))
	    {
	    	String vungId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"));
	    	String khuvucId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"));
	    	
	    	
	    	
	    }
	    else
	    {
		    if(action.equals("new"))
		    {
	    		ITieuchithuongTD tctsku = new TieuchithuongTD();
	    		tctsku.setUserId(userId);
	    		tctsku.createRs();
	    		
		    	session.setAttribute("tctskuBean", tctsku);  	
	    		session.setAttribute("userId", userId);
			
	    		response.sendRedirect("/AHF/pages/Center/TieuChiThuongTDNew.jsp");
		    }
		    else
		    {
		    	obj = new TieuchithuongTDList();
			    obj.setUserId(userId);
	
		    	String search = getSearchQuery(request, obj);
		    	
		    	obj.setUserId(userId);
		    	obj.init(search);
					
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
			
	    		response.sendRedirect("/AHF/pages/Center/TieuChiThuongTD.jsp");	
		    }
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, ITieuchithuongTDList obj) 
	{
		String thang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang"));
		if(thang == null)
			thang = "";
		obj.setThang(thang);
		
		String nam = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nam"));
		if(nam == null)
			nam = "";
		obj.setNam(nam);
		
		String sql = "select a.scheme, a.pk_seq, a.thang, a.nam, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua  " +
					"from TieuchithuongTD a inner join NHANVIEN b on a.NGUOITAO = b.pk_seq " +
					"inner join NHANVIEN c on a.NGUOISUA = c.pk_seq where a.pk_seq > 0 ";
		if(thang.length() > 0)
			sql += " and a.thang = '" + thang + "' ";
		if(nam.length() > 0)
			sql += " and a.nam = '" + nam + "' ";
		
		sql += "order by nam desc, thang desc";
		
		return sql;
	}

}
