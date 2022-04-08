package geso.dms.center.servlets.duyetdonhangnpp;

import geso.dms.center.beans.duyettrasanpham.*;
import geso.dms.center.beans.duyettrasanpham.imp.DuyettrasanphamList;
import geso.dms.center.db.sql.dbutils;
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


public class DuyettrasanphamSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    
	
	
    public DuyettrasanphamSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		dbutils db = new dbutils();
		IDuyettrasanphamList obj;
	   	PrintWriter out; 
	   	String userId;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String id = util.getId(querystring);
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    if(action.equals("huy"))
	    {
	    	String sql = "update donhangtrave set trangthai = '2' where pk_seq = '"+id+"' ";
	    	if(!db.update(sql))
	    	{
	    		geso.dms.center.util.Utility.rollback_throw_exception(db);
	    		return;
	    	}
	    }

	    obj = new DuyettrasanphamList();
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
		session.setAttribute("tbhId", "");
		session.setAttribute("khId", "");
				
		String nextJSP = "/AHF/pages/Center/DuyetTraSanPham.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IDuyettrasanphamList obj = new DuyettrasanphamList();
	   	PrintWriter out; 
	   	String userId;
		
	   	Utility util = new Utility();
	   	
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out = response.getWriter();
	    
		HttpSession session = request.getSession();
	    userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
    	String search = getSearchQuery(request, obj);
    	obj.setUserId(userId);
    	obj.init(search);
			
    	session.setAttribute("obj", obj);  	
		session.setAttribute("userId", userId);
    		
		response.sendRedirect("/AHF/pages/Center/DuyetTraSanPham.jsp");
	}
	
	private String getSearchQuery(HttpServletRequest request , IDuyettrasanphamList obj ) 
	{
		
		//IDuyettrasanphamList obj = new DuyettrasanphamList();
	   	PrintWriter out; 
	   	String userId;
	   	
	   	Utility util = new Utility();
		
    	String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppTen")));
    	if ( nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
    	String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
    	if (trangthai == null)
    		trangthai = "";    	
    	obj.setTrangthai(trangthai);
    	
    	String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
    	if (tungay == null)
    		tungay = "";    	
    	obj.setTungay(tungay);
    	
    	String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
    	if (denngay == null)
    		denngay = "";    	
    	obj.setDenngay(denngay);
    	
    	String query = "select a.pk_seq as dhtvId, a.ngaynhap, a.trangthai, a.ngaytao, a.ngaysua, isnull(b.ten, '') as nguoiduyet, d.ten as khTen, d.pk_seq as khId, e.pk_seq as ddkdId, e.ten as ddkdTen, f.ten as nppTen, f.pk_seq as nppId, a.VAT, isnull(a.ngayduyet, '') as ngayduyet";
		query = query + " from donhangtrave a left join nhanvien b on a.nguoiduyet = b.pk_seq inner join khachhang d on a.khachhang_fk = d.pk_seq inner join daidienkinhdoanh e on a.ddkd_fk = e.pk_seq inner join nhaphanphoi f on a.npp_fk = f.pk_seq";
		query = query + " where a.trangthai in (1,2, 3) and a.donhang_fk is null";
    	
    	if (nppId.length() > 0)
    	{
			query = query + " and a.npp_fk = '" + nppId + "'";		
    	}
    	
    	if (trangthai.length()>0){
			query = query + " and a.trangthai ='" + trangthai + "'";
			
    	}
    	
    	if (tungay.length()>0){
			query = query + " and a.ngaynhap > '" + tungay + "'";
			
    	}
    	
    	if (denngay.length()>0){
			query = query + " and a.ngaynhap < '" + denngay + "'";
			
    	}
    	query = query + " order by a.pk_seq desc";
    	return query;
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}
