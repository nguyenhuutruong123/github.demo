package geso.dms.distributor.servlets.chitieunpp;

import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.chitieunpp.IChitieuSKUInList;
import geso.dms.distributor.beans.chitieunpp.IChitieuSKUInTT;
import geso.dms.distributor.beans.chitieunpp.imp.ChitieuSKUInList;
import geso.dms.distributor.beans.chitieunpp.imp.ChitieuSKUInTT;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChiTieuSKUInSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
       
    public ChiTieuSKUInSvl() {
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
	    
	    IChitieuSKUInList obj = new ChitieuSKUInList();
	    obj.setUserId(userId);
	    
	    String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
	    if(view == null)
	    	view = "";
	    System.out.println("______View la: " + view);
	    
	    if(view.equals("TT"))
	    {
	    	String action = util.getAction(querystring);
	    	String ctId = util.getId(querystring);
	    	
	    	System.out.println("___Action: " + action + " ___ Chi tieu Id: " + ctId);
	    	
	    	if(action.equals("delete"))
	    	{
	    		DeleChiTieuTT(ctId);
	    	}
	    	
	    	if(action.equals("duyet"))
	    	{
	    		dbutils db = new dbutils();
	    		
	    		db.update("update chitieuskuin_tt set trangthai = '1' where pk_seq = '" + ctId + "'");
	    		db.shutDown();
	    	}
	    	
	    	obj.initTT("");
			session.setAttribute("obj", obj);
		    String nextJSP = "/AHF/pages/Center/ChiTieuSKUIn.jsp";
			response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	 String action = util.getAction(querystring);
	    	 String nhId = util.getId(querystring);
	    	 System.out.print(action);
	    	 System.out.print(nhId);
	    	 if (action.equals("delete"))
	 	    {	
	    		System.out.println("vao delete");
	 	    	String msg = Delete(nhId);
	 	    	if(msg.length() > 0)
	 	    		obj.setMsg(msg);
	 	    }
	    	 if(action.equals("chot"))
	    	 {
	    		 System.out.print("vao chot");
	    		 String msg = Chot(nhId);
		 	    	if(msg.length() > 0)
		 	    		obj.setMsg(msg); 
	    	 }
		    obj.init("");
			session.setAttribute("obj", obj);
		    String nextJSP = "/AHF/pages/Distributor/ChiTieuSKUIn.jsp";
			response.sendRedirect(nextJSP);
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
	    IChitieuSKUInList obj;
	    
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    
	    String task = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("task"));
	    if (task == null){
	    	task = "";
	    }
	    System.out.println("____Task la: " + task);
	    
	    if(action.equals("new"))
	    {
	    	if(task.equals("TT"))
	    	{
		    	IChitieuSKUInTT ctskuBean = new ChitieuSKUInTT();
		    	ctskuBean.setUserId(userId);
		    	ctskuBean.init();
		    	session.setAttribute("ctskuBean", ctskuBean);  	
	    		session.setAttribute("userId", userId);
			
	    		response.sendRedirect("/AHF/pages/Center/ChiTieuSKUInNew.jsp");	
	    	}
	    	else
	    	{
	    		IChitieuSKUInTT ctskuBean = new ChitieuSKUInTT();
	    		ctskuBean.setUserId(userId);
	    		ctskuBean.init();
	    	    session.setAttribute("obj", ctskuBean);  	
	    		session.setAttribute("userId", userId);
	    		
	    		response.sendRedirect("/AHF/pages/Distributor/ChiTieuSKUInNew.jsp");	
	    	}
	    }
	    else
	    {
	    	if(task.equals("TT"))
	    	{
	    		obj = new ChitieuSKUInList();
			    obj.setUserId(userId);
	
		    	String search = getSearchQuery2(request, obj,userId);
		    	
		    	obj.setUserId(userId);
		    	System.out.println(search);
		    	obj.initTT(search);
					
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
			
	    		response.sendRedirect("/AHF/pages/Center/ChiTieuSKUIn.jsp");
	    	}
	    	else
	    	{
		    	obj = new ChitieuSKUInList();
			    obj.setUserId(userId);
	
		    	String search = getSearchQuery(request, obj,userId);
		    	
		    	obj.setUserId(userId);
		    	System.out.println(search);
		    	obj.init(search);
					
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
			
	    		response.sendRedirect("/AHF/pages/Distributor/ChiTieuSKUIn.jsp");
	    	}
	    }
	}

	private String getSearchQuery(HttpServletRequest request, IChitieuSKUInList obj,String userId) 
	{
		String query="";
		Utility util = new Utility();
		String nppid=util.getIdNhapp(userId);
		System.out.println("nppid "+nppid);
    	if ( userId == null)
    		userId = "";
    	obj.setNppId(util.getIdNhapp(userId));
   
		String nam = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nam")));
		if ( nam.trim().equals("0"))
			nam = "";
    	System.out.println("nam "+nam);
    	obj.setNam(nam);
    	
    	String thang=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang")));
    	if ( thang.trim().equals("0"))
    		thang = "";
    	System.out.println("thang "+thang);
    	obj.setThang(thang);
    	
    	String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
    	if ( trangthai == null)
    		trangthai = "";
    	obj.setTrangthai(trangthai);
    	    	   
    	query =  "select a.thang,a.pk_seq, a.nam, a.diengiai, a.trangthai, a.nhapp_fk, a.ngaytao, a.ngaysua, b.ten as nguoitao, a.ngaytao, c.ten as nguoisua " +
		  "from CHITIEUSKUIN a inner join NHANVIEN b on a.nguoitao = b.pk_seq inner join NHANVIEN c on a.nguoisua = c.pk_seq " +
		  "where a.nhapp_fk = '" + nppid + "' and 1=1 ";
    		
    	if (nam.length() > 0)
    	{
			query = query + " and a.nam ='" + nam + "'";			
    	}
    	
    	if (trangthai.length() > 0)
    	{
			query = query + " and a.trangthai ='" + trangthai + "'";			
    	}
    	
    	if (thang.length() > 0)
    	{
    		query = query + " and a.thang  = '" + thang + "'"; 
    	}
    	query = query + " order by a.nam desc, a.thang desc";
    	return query;
	}
	
	private String getSearchQuery2(HttpServletRequest request, IChitieuSKUInList obj,String userId) 
	{
		String query="";
		Utility util = new Utility();
		
		String nppid=util.getIdNhapp(userId);
    	if ( userId == null)
    		userId = "";
    	obj.setNppId(util.getIdNhapp(userId));
   
		String nam = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nam")));
		if ( nam.trim().equals("0"))
			nam = "";
    	obj.setNam(nam);
    	
    	String thang=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang")));
    	if ( thang.trim().equals("0"))
    		thang = "";
    	System.out.println("thang "+thang);
    	obj.setThang(thang);
    	
    	String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
    	if ( trangthai == null)
    		trangthai = "";
    	obj.setTrangthai(trangthai);
    	    	   
    	query =  "select a.thang, a.pk_seq, a.nam, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, a.ngaytao, c.ten as nguoisua " +
		  "from CHITIEUSKUIN_TT a inner join NHANVIEN b on a.nguoitao = b.pk_seq inner join NHANVIEN c on a.nguoisua = c.pk_seq " +
		  "where a.thang > 0 ";
    		
    	if (nam.length() > 0)
    	{
			query = query + " and a.nam ='" + nam + "'";			
    	}
    	
    	if (trangthai.length() > 0)
    	{
			query = query + " and a.trangthai ='" + trangthai + "'";			
    	}
    	
    	if (thang.length() > 0)
    	{
    		query = query + " and a.thang  = '" + thang + "'"; 
    	}
    	query = query + " order by a.nam desc, a.thang desc";
    	return query;
	}
	
	private String Delete(String nhId)
	{
		dbutils db = new dbutils();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			if(!db.update("delete CHITIEUSKUIN where pk_seq ='"+ nhId+"'"))
			{
				db.getConnection().rollback();
				return "Lỗi "+ "delete CHITIEUSKUIN where pk_seq ='"+ nhId+"'";
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			return "";
		} 
		catch (Exception e)
		{ 
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return "Không thể xóa! Lỗi: " + e.getMessage(); 
		}
	}
	
	private String Chot(String nhId)
	{
		dbutils db = new dbutils();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			if(!db.update("update CHITIEUSKUIN set trangthai='1'  where pk_seq ='"+ nhId+"'"))
			{
				db.getConnection().rollback();
				return "Lỗi "+ "update CHITIEUSKUIN set trangthai='1'  where pk_seq ='"+ nhId+"'";
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			return "";
		} 
		catch (Exception e)
		{ 
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return "Không thể xóa! Lỗi: " + e.getMessage(); 
		}
		
	}
	
	private String DeleChiTieuTT(String ctId)
	{
		dbutils db = new dbutils();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			if(!db.update("delete chitieuskuin_tt  where pk_seq ='" + ctId + "'"))
			{
				db.getConnection().rollback();
				return "Lỗi "+ "delete chitieuskuin_tt  where pk_seq ='" + ctId + "'";
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			return "";
		} 
		catch (Exception e)
		{ 
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return "Không thể xóa! Lỗi: " + e.getMessage(); 
		}
	}
	
}
