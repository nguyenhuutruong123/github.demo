package geso.dms.distributor.servlets.phieuthuhoi;

import geso.dms.distributor.beans.donhangthuhoi.IDonhangthuhoiList;
import geso.dms.distributor.beans.donhangthuhoi.imp.DonhangthuhoiList;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DonhangthuhoiSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	
    public DonhangthuhoiSvl()
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		
		IDonhangthuhoiList obj;
		PrintWriter out; 

		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out = response.getWriter();
   

	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String dhthId = util.getId(querystring);

	    if (action.equals("chotphieuxuat")){	   		  	    	
	    	Chotdonhangthuhoi(dhthId);
	    	out.print(dhthId);
	    }
	   	    
	    obj = new DonhangthuhoiList();
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Distributor/DonHangThuHoi.jsp";
		response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		
		IDonhangthuhoiList obj  = new DonhangthuhoiList();
		PrintWriter out; 
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out = response.getWriter();
	    Utility util = new Utility();
	    userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
    	String search = this.getSearchQuery(request, obj);
    	
    	obj.setUserId(userId);
    	obj.init(search);
			
    	session.setAttribute("obj", obj);  	
		session.setAttribute("userId", userId);
    		
		response.sendRedirect("/AHF/pages/Distributor/DonHangThuHoi.jsp");
		}
	}

	private String getSearchQuery(HttpServletRequest request, IDonhangthuhoiList obj) 
	{	
		Utility util = new Utility();
		String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
    	if ( nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
		String nvgnId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nvgnTen")));
    	if ( nvgnId == null)
    		nvgnId = "";
    	obj.setNvgnId(nvgnId);
    	
    	String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
    	if ( trangthai == null)
    		trangthai = "";
    	obj.setTrangthai(trangthai);
    	
    	String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
    	if (tungay == null)
    		tungay = "";    	
    	obj.setTungay(tungay);
    	   	
    	String query = "";
    	query = "select dhth.pk_seq as dhthId, dhth.phieuxuatkho_fk as pxkId, dhth.trangthai, dhth.ngaytao, dhth.ngaysua, dhth.donhang_fk, nv.ten as nguoitao, nv2.ten as nguoisua ";
		query = query + "from donhangthuhoi dhth inner join phieuxuatkho pxk on dhth.phieuxuatkho_fk = pxk.pk_seq inner join nhanvien nv on dhth.nguoitao = nv.pk_seq inner join nhanvien nv2 on dhth.nguoisua = nv2.pk_seq ";
		query = query + "where dhth.npp_fk = '" + nppId + "' ";
    		
    	if (nvgnId.length() > 0)
    	{
			query = query + " and pxk.nvgn_fk='" + nvgnId + "'";			
    	}
    	
    	if (trangthai.length() > 0)
    	{
			query = query + " and dhth.trangthai='" + trangthai + "'";			
    	}
    	
    	if (tungay.length() > 0)
    	{
    		query = query + " and dhth.ngaytao >= '" + tungay + "'"; 
    	}
    	query = query + " order by dhth.pk_seq DESC";
    	return query;
	}
	
	private void Chotdonhangthuhoi(String dhthId) 
	{
		dbutils db = new dbutils(); 
		db.update("commit");
	}

}
