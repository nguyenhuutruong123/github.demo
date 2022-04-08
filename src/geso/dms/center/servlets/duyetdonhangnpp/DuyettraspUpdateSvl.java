package geso.dms.center.servlets.duyetdonhangnpp;

import geso.dms.center.beans.duyettrasanpham.IDuyettrasanpham;
import geso.dms.center.beans.duyettrasanpham.IDuyettrasanphamList;
import geso.dms.center.beans.duyettrasanpham.imp.Duyettrasanpham;
import geso.dms.center.beans.duyettrasanpham.imp.DuyettrasanphamList;

import geso.dms.distributor.beans.donhangtrave.ISanpham;
import geso.dms.distributor.beans.donhangtrave.imp.Sanpham;
import geso.dms.distributor.db.sql.dbutils;

import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DuyettraspUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	
    public DuyettraspUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		IDuyettrasanpham dtspBean;
		List<ISanpham> spList;
		PrintWriter out;
		
		HttpSession session = request.getSession();
		
		out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    //out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String id = util.getId(querystring);

	    dtspBean = new Duyettrasanpham(id);
        dtspBean.setUserId(userId); //phai co UserId truoc khi Init
        dtspBean.init();
     
        session.setAttribute("dtspBean", dtspBean);
        String nextJSP = "/AHF/pages/Center/DuyetTraSanPhamUpdate.jsp";
        
        if(request.getQueryString().indexOf("display") >= 0 ) 
        	nextJSP = "/AHF/pages/Center/DuyetTraSanPhamDisplay.jsp";
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		IDuyettrasanpham dtspBean;
		List<ISanpham> spList;
		PrintWriter out;
		
		HttpSession session = request.getSession();
		
		Utility util = new Utility();
		String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if(action.equals("save"))
		{
			String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
			dtspBean = new Duyettrasanpham(id);
			dtspBean.setUserId(userId);
			 
			if (!(dtspBean.duyetSptrave(id, userId)))
			{							
		        dtspBean.init();
		        
		        session.setAttribute("dtspBean", dtspBean);
		        String nextJSP = "/AHF/pages/Center/DuyetTraSanPhamUpdate.jsp";
		        
		        response.sendRedirect(nextJSP);
			}
			else
			{
				IDuyettrasanphamList obj = new DuyettrasanphamList();
				obj.setUserId(userId);
				obj.init("");
				    
				session.setAttribute("obj", obj);
							
				String nextJSP = "/AHF/pages/Center/DuyetTraSanPham.jsp";
				response.sendRedirect(nextJSP);	    			    									
			}
		}
	}
	
	
}
