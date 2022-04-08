package geso.dms.center.servlets.khoahuanluyen;

import geso.dms.center.beans.khoahuanluyen.*;
import geso.dms.center.beans.khoahuanluyen.imp.*;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class KhoahuanluyenSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public KhoahuanluyenSvl() {
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
	    
	    IKhoahuanluyenList obj = new KhoahuanluyenList();
	    obj.setUserId(userId);
	    
	    String action = util.getAction(querystring);
	    String khlId = util.getId(querystring);
	    String msg = "";
	    
	    if(action.trim().equals("delete"))
	    {
	    	dbutils db = new dbutils();
	    	if(!db.update("update KhoaHuanLuyen set trangthai = '2' where pk_seq = '" + khlId + "'"))
	    	{
	    		msg = "Không thể xóa KhoaHuanLuyen";
	    	}
	    	db.shutDown();
	    }
	    
	    if(action.trim().equals("chot"))
	    {
	    	dbutils db = new dbutils();
	    	if(!db.update("update KhoaHuanLuyen set trangthai = '1' where pk_seq = '" + khlId + "'"))
	    	{
	    		msg = "Không thể chốt KhoaHuanLuyen";
	    	}
	    	db.shutDown();
	    }

	    obj.init("");
	    obj.setMsg(msg);
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/AHF/pages/Center/KhoaHuanLuyen.jsp";
		response.sendRedirect(nextJSP);
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
	    IKhoahuanluyenList obj;
	    
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    
	    if(action.equals("new"))
	    {
    		IKhoahuanluyen khl = new Khoahuanluyen();
    		khl.createRs();
    		khl.setUserId(userId);

	    	session.setAttribute("khlBean", khl);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/AHF/pages/Center/KhoaHuanLuyenNew.jsp");
	    }
	    else
	    {
	    	obj = new KhoahuanluyenList();
		    obj.setUserId(userId);

	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/AHF/pages/Center/KhoaHuanLuyen.jsp");	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IKhoahuanluyenList obj) 
	{
		String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String sql = "select a.pk_seq, a.tungay, a.denngay, a.tieude, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua  " +
					"from KhoaHuanLuyen a inner join NHANVIEN b on a.NGUOITAO = b.pk_seq " +
					"inner join NHANVIEN c on a.NGUOISUA = c.pk_seq where a.pk_seq > 0 ";
		
		if(tungay.length() > 0)
			sql += " and a.tungay >= '" + tungay + "' ";
		if(denngay.length() > 0)
			sql += " and a.denngay <= '" + denngay + "' ";
		if(trangthai.length() > 0)
			sql += " and a.trangthai = '" + trangthai + "' ";
		
		sql += " order by a.denngay desc, a.trangthai asc ";
		
		return sql;
	}

}
