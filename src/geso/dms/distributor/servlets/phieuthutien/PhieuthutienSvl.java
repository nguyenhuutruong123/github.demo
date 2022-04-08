package geso.dms.distributor.servlets.phieuthutien;

import geso.dms.distributor.beans.phieuthutien.IPhieuthutien;
import geso.dms.distributor.beans.phieuthutien.IPhieuthutienList;
import geso.dms.distributor.beans.phieuthutien.imp.Phieuthutien;
import geso.dms.distributor.beans.phieuthutien.imp.PhieuthutienList;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PhieuthutienSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out; 
		
    public PhieuthutienSvl() 
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
	    System.out.println("userId :" + userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	    System.out.println(action);
	    
	    String pttId = util.getId(querystring);
	    System.out.println("Chot phieu:" + pttId);
	    String msg="";
	    if (action.equals("chotphieu"))
	    {	   		  	    	
			IPhieuthutien ptt = new Phieuthutien(pttId);
			ptt.PostPtt();
			msg=ptt.getMessage();
	    	System.out.println("Chot phieu:" + pttId);
	    }
	    
	    if (action.equals("delete"))
	    {	   		  	    	
			IPhieuthutien ptt = new Phieuthutien(pttId);
			ptt.DeletePtt();
	    	System.out.println(pttId);
	    }
	   	    
	    IPhieuthutienList obj = new PhieuthutienList();
	    obj.setUserId(userId);
	    obj.init("");
	    obj.setMsg(msg);
		session.setAttribute("obj", obj);
		session.setAttribute("tbhId", "");
		String nextJSP = "/AHF/pages/Distributor/PhieuThuTien.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out = response.getWriter();
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String nvgnId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nvgnId")));
	    
	    if (nvgnId == null) nvgnId = "";
	    
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    out.println(action); 
	          
	    if(action.equals("Tao moi"))
	    {
	    	IPhieuthutien pttBean = (IPhieuthutien) new Phieuthutien("");
	    	pttBean.setUserId(userId);
	    	pttBean.initNew();

	    	session.setAttribute("pttBean", pttBean);
    		
    		String nextJSP = "/AHF/pages/Distributor/PhieuThuTienNew_1.jsp";
    		response.sendRedirect(nextJSP);    		
	    }
	    else
	    	
	    if(action.equals("nhaplai"))
	    {
	    	IPhieuthutienList obj = new PhieuthutienList();
	 	    obj.setUserId(userId);
	 	    obj.init("");
	 	    
	 		session.setAttribute("obj", obj);
	 		session.setAttribute("tbhId", "");
	 				
	 		String nextJSP = "/AHF/pages/Distributor/PhieuThuTien.jsp";
	 		response.sendRedirect(nextJSP);
	    }
	      else
	    {
	    	IPhieuthutienList obj = new PhieuthutienList();
	    	String search = this.getSearchQuery(request, obj);
	    	System.out.println(search);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
    		session.setAttribute("query", search);
	    		
    		response.sendRedirect("/AHF/pages/Distributor/PhieuThuTien.jsp");	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IPhieuthutienList obj) 
	{	
		Utility util = new Utility();
		String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
    	if ( nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
		String nvgnId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nvgnId")));
    	if ( nvgnId == null)
    		nvgnId = "";
    	obj.setNvgnId(nvgnId);
    	
    	String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
    	if ( tungay == null)
    		tungay = "";
    	obj.setTungay(tungay);
    	
    	String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
    	if (denngay == null)
    		denngay = "";    	
    	obj.setDenngay(denngay);
    	
    	String sotien = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sotien")));
    	if (sotien == null)
    		sotien = "";    	
    	obj.setSotien(sotien);
    	   	
    	String query = "";
		query = "select ptt.pk_seq as pttId, nvgn.ten as nvgnTen, ptt.sotien, ptt.ngaythu, ptt.diengiai, " + 
				" ptt.trangthai, ptt.ngaytao, ptt.ngaysua, nv1.ten as nguoitao, nv2.ten as nguoisua" + 
				" from phieuthutien_nvgn ptt" + 
				" inner join nhanviengiaonhan nvgn on ptt.nvgn_fk = nvgn.pk_seq" + 
				" inner join nhanvien nv1 on ptt.nguoitao = nv1.pk_seq" + 
				" inner join nhanvien nv2 on ptt.nguoisua = nv2.pk_seq" + 
				" where ptt.npp_fk = '" + nppId + "'" ; 
    		
    	if (nvgnId.length() > 0)
    	{
    		query = query + " and nvgn_fk = '" + nvgnId + "'";		
    	}
    	
    	if (tungay.length() > 0)
    	{
			query = query + " and ptt.ngaythu >= '" + convertDate(tungay) + "'";			
    	}
    	
    	if (denngay.length() > 0)
    	{
    		query = query + " and ptt.ngaythu <= '" + convertDate(denngay) + "'";
    	}
    	
    	if (sotien.length() > 0)
    	{
    		query = query + " and ptt.sotien = '" + sotien + "'";
    	}
    	
    	return query;
	}
	
	
	
	private String convertDate(String date) 
	{
		//chuyen dinh dang dd-MM-yyyy sang dinh dang yyyy-MM-dd
		if(!date.contains("-"))
			return "";
		String[] arr = date.split("-");
		if(arr[0].length() < arr[2].length())
			return arr[2] + "-" + arr[1] + "-" + arr[0];
		return date;
	}
	


}
