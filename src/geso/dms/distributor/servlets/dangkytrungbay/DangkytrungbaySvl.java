package geso.dms.distributor.servlets.dangkytrungbay;

import geso.dms.distributor.beans.dangkytrungbay.IDangkytrungbay;
import geso.dms.distributor.beans.dangkytrungbay.IDangkytrungbayList;
import geso.dms.distributor.beans.dangkytrungbay.imp.Dangkytrungbay;
import geso.dms.distributor.beans.dangkytrungbay.imp.DangkytrungbayList;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DangkytrungbaySvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

    
    public DangkytrungbaySvl() 
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
		
		IDangkytrungbayList obj;
		PrintWriter out; 
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out  = response.getWriter();
	    	    
	    
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String dktbId = util.getId(querystring);

	    if(action.equals("delete"))
	    {	   		  	    	
	    	Delete(dktbId);
	    	out.print(dktbId);
	    }
	    
	    if(action.equals("chotphieu"))
	    {
	    	Chotphieu(dktbId);
	    	out.print(dktbId);
	    }
	   	    
	    obj = new DangkytrungbayList();
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Distributor/DangKyTrungBay.jsp";
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
		IDangkytrungbayList obj;
		PrintWriter out; 
		
		obj = new DangkytrungbayList();
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out = response.getWriter();
	    
		Utility util = new Utility();
	    userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    out.println(action); 
	          
	    if (action.equals("Tao moi"))
	    {
	    	// Empty Bean for distributor
	    	IDangkytrungbay dktbBean = (IDangkytrungbay) new Dangkytrungbay("");
	    	dktbBean.setUserId(userId);
	    	dktbBean.createRS();
	    	
	    	// Save Data into session
	    	session.setAttribute("obj", dktbBean);
    		
    		String nextJSP = "/AHF/pages/Distributor/DangKyTrungBayNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }else
	    {
	    	String search = this.getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/AHF/pages/Distributor/DangKyTrungBay.jsp");	    		    	
	    }
		}
	}
	
	private String getSearchQuery(HttpServletRequest request, IDangkytrungbayList obj) 
	{	
		Utility util = new Utility();
		String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
    	if ( nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
    	String sophieu = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sophieu")));
    	if ( sophieu== null)
    		sophieu = "";
    	obj.setScheme(sophieu);
    	
    	String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
    	if ( tungay== null)
    		tungay = "";
    	obj.setTungay(tungay);
    	
    	String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
    	if ( denngay == null)
    		denngay = "";
    	obj.setDenngay(denngay);
    	
    	String query = "select a.pk_seq as dktbId, b.pk_seq as cttbId, b.diengiai as cttbTen, a.ngaydangky, a.ngaysua, a.trangthai, c.ten as nguoitao, d.ten as nguoisua ";
		query = query + "from dangkytrungbay a inner join cttrungbay b on a.cttrungbay_fk = b.pk_seq inner join nhanvien c on a.nguoitao = c.pk_seq inner join nhanvien d on a.nguoisua = d.pk_seq ";
		query = query + "where a.npp_fk = '" + nppId + "'";
		
		if(sophieu.length() > 0)
			query =  query + " and a.pk_seq like '%" + sophieu + "%'";
		if(tungay.length() > 0)
			query = query + " and a.ngaydangky >= '" + convertDate(tungay) + "'";
		if(denngay.length() > 0)
			query = query + " and a.ngaydangky <= '" + convertDate(denngay) + "'";
    	
    	return query + " order by a.ngaydangky DESC";
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
	
	private void Delete(String dktbId) 
	{
		dbutils db = new dbutils(); 
		try 
		{
			db.getConnection().setAutoCommit(false);
			String sql="Delete dktrungbay_khachhang where dktrungbay_fk='" + dktbId + "'";
			if( !db.update(sql))
			{
				System.out.println("1.Khong the xoa dktb: " + sql);
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return ;
			}
			sql="Delete dangkytrungbay where pk_seq='" + dktbId + "'";
			if( !db.update(sql))
			{
				System.out.println("1.Khong the xoa dktb: " + sql);
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return ;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
		} 
		catch(Exception e)
		{
			System.out.println("115.Khong the xoa dktb: " + e.getMessage());
			geso.dms.center.util.Utility.rollback_throw_exception(db);	
		}		
	}
	private void Chotphieu(String dktbId) 
	{
		dbutils db = new dbutils(); 
		try{
		
		db.getConnection().setAutoCommit(false);
		db.update("update dangkytrungbay set trangthai='1' where pk_seq = '" + dktbId + "'");
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		
		db.shutDown();
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			
		}
		
	}


}
