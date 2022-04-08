package geso.dms.center.servlets.chietkhau;

import geso.dms.center.beans.mucchietkhau.IChietkhau;
import geso.dms.center.beans.mucchietkhau.IChietkhauList;
import geso.dms.center.beans.mucchietkhau.imp.Chietkhau;
import geso.dms.center.beans.mucchietkhau.imp.ChietkhauList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChietkhauSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ChietkhauSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IChietkhauList obj;
		PrintWriter out;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
	    obj = new ChietkhauList();
	    
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String ckId = util.getId(querystring);

	    if (action.equals("delete")){	   		  	    	
	    	Delete(ckId);
	    }
	   	
	    obj.setUserId(userId);
	    obj.init("");
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Center/ChietKhau.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IChietkhauList obj;
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
	    	IChietkhau ckBean = (IChietkhau) new Chietkhau("");
	    	ckBean.setUserId(userId);
	    	ckBean.createRs();
	    	// Save Data into session
	    	session.setAttribute("ckBean", ckBean);
    		
    		String nextJSP = "/AHF/pages/Center/ChietKhauNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    if (action.equals("search"))
	    {
	    	obj = new ChietkhauList();
	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.init(search);
			obj.setUserId(userId);
			
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/AHF/pages/Center/ChietKhau.jsp");	    	
	    	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IChietkhauList obj)
	{
		String sotien = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sotien"));
    	if ( sotien == null)
    		sotien = "";
    	obj.setSotien(sotien);
    	
    	String chietkhau = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chietkhau"));
    	if (chietkhau == null)
    		chietkhau = "";    	
    	obj.setChietkhau(chietkhau);
    	
    	String query = "select a.pk_seq as id, isnull(a.diengiai, 'na') as diengiai, a.sotien, a.mucchietkhau, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua from Chietkhau a, nhanvien b, nhanvien c ";
		query = query + " where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq"; 	
		
    	if (sotien.length() > 0)
    	{
			query = query + " and a.sotien = '" + sotien + "'";
			
    	}
    	
    	if (chietkhau.length()>0){
			query = query + " and a.chietkhau = '" + chietkhau + "')";
			
    	}
  
    	query = query + "  order by a.sotien asc";
    	return query;
	}	
	
	private void Delete(String ckId)
	{
		dbutils db = new dbutils();
		db.update("delete chietkhau where pk_seq = '" + ckId + "'");
		db.shutDown();
	}

}
