package geso.dms.distributor.servlets.phieuthanhtoan;

import geso.dms.distributor.beans.phieuthanhtoan.IPhieuthanhtoan;
import geso.dms.distributor.beans.phieuthanhtoan.IPhieuthanhtoanList;

import geso.dms.distributor.beans.phieuthanhtoan.imp.Phieuthanhtoan;
import geso.dms.distributor.beans.phieuthanhtoan.imp.PhieuthanhtoanList;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PhieuthanhtoanSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out; 
		
    public PhieuthanhtoanSvl() 
    {
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
	    //out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	    //out.println(action);
	    	    
	    String pttId = util.getId(querystring);
	    
	    IPhieuthanhtoanList obj = new PhieuthanhtoanList();
	    obj.setUserId(userId);
	    
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Distributor/PhieuThanhToan.jsp";
		response.sendRedirect(nextJSP);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out = response.getWriter();
	    IPhieuthanhtoanList obj = new PhieuthanhtoanList();	    
		Utility util = new Utility();
	    HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));	    
	    obj.setUserId(userId);
	    obj.setUserId(userId);
	    
    	String search = getSearchQuery(request, obj);
    	System.out.println(search);
    	
    	
    	obj.init(search);
			
    	session.setAttribute("obj", obj);  	
		session.setAttribute("userId", userId);
		session.setAttribute("nvgnId", obj.getNvgnId());		    			 
	    		
    	response.sendRedirect("/AHF/pages/Distributor/PhieuThanhToan.jsp");	
	    
	}
	
	private String getSearchQuery(HttpServletRequest request, IPhieuthanhtoanList obj) 
	{	
		Utility util = new Utility();
		String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
    	if ( nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
    	
    	String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
    	if ( tungay == null)
    		tungay = "";
    	obj.setTungay(tungay);
    	
    	String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
    	if (denngay == null)
    		denngay = "";    	
    	obj.setDenngay(denngay);
    	
    	String nvgnId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nvgnId")));
    	if (nvgnId == null)
    		nvgnId = "";    	
    	System.out.println("NVGN:" + nvgnId);
    	obj.setNvgnId(nvgnId);
    	   	
    	String pttId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("pttId")));
    	if (pttId == null)
    		pttId = "";    	
    	obj.setPttId(pttId);

    	String khId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khId")));
    	if ( khId == null)
    		khId = "";
    	obj.setKhId(khId);
    	System.out.println("Khach hang Id:" + khId);
		String query = 	obj.getQuery();
		String khquery = obj.getKHquery();
		
    	if (nvgnId.length() > 0)
    	{
    		query = query + " AND PTTNVGN.NVGN_FK = '" + nvgnId + "' ";		
    		khquery = khquery + " AND PTTNVGN.NVGN_FK = '" + nvgnId + "' ";
    		
    	}
    	
    	if (pttId.length() > 0)
    	{
    		query = query + " AND PTTNVGN.PK_SEQ = '" + pttId + "' ";		
    		khquery = khquery + " AND PTTNVGN.PK_SEQ = '" + pttId + "' ";
    	}

    	if (khId.length() > 0)
    	{
    		query = query + " AND KH_CN.KHACHHANG_FK = '" + khId + "' ";		
    		khquery = khquery + " AND KH_CN.KHACHHANG_FK = '" + khId + "' ";
    	}

    	if (tungay.length() > 0)
    	{
			query = query + " and PTTNVGN.NGAYTHU >= '" + tungay + "'";			
			
    	}
    	
    	if (denngay.length() > 0)
    	{
    		query = query + " and PTTNVGN.NGAYTHU <= '" + denngay + "'";
		
    	}
    	query = query + "  ORDER BY KHTEN, NGAY DESC";
    	System.out.println("Danh sach khach hang:" + khquery);
    	obj.setKHquery(khquery);
    	return query;
	}
	
	

}
