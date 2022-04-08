package geso.dms.center.servlets.tieuchidanhgia;

import geso.dms.center.beans.tieuchidanhgia.*;
import geso.dms.center.beans.tieuchidanhgia.imp.*;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TieuchidanhgiaSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public TieuchidanhgiaSvl() {
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
	    
	    ITieuchidanhgiaList obj = new TieuchidanhgiaList();
	    obj.setUserId(userId);
	    
	    String action = util.getAction(querystring);
	    String khlId = util.getId(querystring);
	    String msg = "";
	    
	    if(action.trim().equals("delete"))
	    {
	    	dbutils db = new dbutils();
	    	if(!db.update("update TieuChiDanhGia set trangthai = '2' where pk_seq = '" + khlId + "'"))
	    	{
	    		msg = "Không thể xóa TieuChiDanhGia";
	    	}
	    	db.shutDown();
	    }
	    
	    if(action.trim().equals("chot"))
	    {
	    	dbutils db = new dbutils();
	    	if(!db.update("update TIEUCHIDANHGIA set trangthai = '1' where pk_seq = '" + khlId + "'"))
	    	{
	    		msg = "Không thể chốt TIEUCHIDANHGIA";
	    	}
	    	db.shutDown();
	    }
	    if(action.trim().equals("ToExcel"))
	    {
	    	
	    }

	    obj.init("");
	    obj.setMsg(msg);
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/AHF/pages/Center/TieuChiDanhGia.jsp";
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
	    ITieuchidanhgiaList obj;
	    
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    
	    if(action.equals("new"))
	    {
    		ITieuchidanhgia khl = new Tieuchidanhgia();
    		khl.createRs();
    		khl.setUserId(userId);

	    	session.setAttribute("tcdgBean", khl);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/AHF/pages/Center/TieuChiDanhGiaNew.jsp");
	    }
	    else
	    {
	    	obj = new TieuchidanhgiaList();
		    obj.setUserId(userId);

	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/AHF/pages/Center/TieuChiDanhGia.jsp");	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, ITieuchidanhgiaList obj) 
	{
		String thang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang"));
		if(thang == null)
			thang = "";
		obj.setTungay(thang);
		
		String nam = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nam"));
		if(nam == null)
			nam = "";
		obj.setDenngay(nam);
		
		String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String sql = "select a.pk_seq, a.thang, a.nam, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua  " +
		  			 "from tieuchidanhgia a inner join NHANVIEN b on a.NGUOITAO = b.pk_seq " +
		  			 "inner join NHANVIEN c on a.NGUOISUA = c.pk_seq where a.pk_seq > 0 ";
		
		if(thang.length() > 0)
			sql += " and a.thang = '" + thang + "' ";
		if(nam.length() > 0)
			sql += " and a.nam = '" + nam + "' ";
		if(trangthai.length() > 0)
			sql += " and a.trangthai = '" + trangthai + "' ";
		
		sql += " order by a.nam desc, a.thang desc, a.trangthai asc ";
		
		return sql;
	}

}
