package geso.dms.distributor.servlets.mucchietkhau;

import geso.dms.distributor.beans.mucchietkhau.IMucchietkhau;
import geso.dms.distributor.beans.mucchietkhau.IMucchietkhauList;
import geso.dms.distributor.beans.mucchietkhau.imp.Mucchietkhau;
import geso.dms.distributor.beans.mucchietkhau.imp.MucchietkhauList;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MucchietkhauSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	
    public MucchietkhauSvl() 
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
		
		IMucchietkhauList obj;
		PrintWriter out; 
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out  = response.getWriter();
	    	    
  
	    obj = new MucchietkhauList();
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String mckId = util.getId(querystring);

	    if (action.equals("delete")){	   		  	    	
	    	Delete(mckId, obj);
	    	out.print(mckId);
	    }
	   	    
	    
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Distributor/MucChietKhau.jsp";
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
		
		IMucchietkhauList obj;
		PrintWriter out; 
		
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
	          
	    if (action.equals("new"))
	    {
	    	// Empty Bean for distributor
	    	IMucchietkhau mckBean = (IMucchietkhau) new Mucchietkhau("");
	    	mckBean.setUserId(userId);
	    	mckBean.createRS();
	    	
	    	// Save Data into session
	    	session.setAttribute("mckBean", mckBean);
    		
    		String nextJSP = "/AHF/pages/Distributor/MucChietKhauNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    if (action.equals("search"))	    
	    {
	    	obj = new MucchietkhauList();
	    	String search = this.getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/AHF/pages/Distributor/MucChietKhau.jsp");	    		    	
	    }
		}
	}
	
	private String getSearchQuery(HttpServletRequest request,IMucchietkhauList obj) 
	{
		Utility util = new Utility();
		HttpSession session = request.getSession();		
		String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
    	if ( nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
		String chietkhau = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chietkhau")));
    	if ( chietkhau == null)
    		chietkhau = "";
    	obj.setMucchietkhau(chietkhau);
    	  	
    	String khTen = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khTen")));
    	if ( khTen == null)
    		khTen = "";
    	obj.setKhachhang(khTen);
    	    	
    	String query = "select * from vwMucchietkhau where nppId ='" + nppId + "'";
    		
    	if (chietkhau.length() > 0)
    	{
			query = query + " and  chietkhau=  '" + chietkhau + "'";			
    	}
    	
    	if (khTen.length() > 0)
    	{
     		query = query + " and mckId in (select chietkhau_fk from khachhang where  upper(dbo.ftBoDau(ten)) like  upper(N'%" + util.replaceAEIOU(khTen) + "%'))";
    	}
    	  	
    	query = query + " order by chietkhau";
    	System.out.println("");
    	System.out.println("chiet khau:"+ query);
    	
    	return query;
	}

	private void Delete(String mckId, 		IMucchietkhauList obj) 
	{    
		dbutils db = new dbutils(); 
		try {
			
			db.getConnection().setAutoCommit(false);
					
			String query = "Select * from KhachHang where chietkhau_fk = '" + mckId + "'";
			ResultSet kh = db.get(query);
			int testWhile = 0;
		   
	    	while(kh.next())
	    	{
	    		System.out.println("===" + testWhile++);
	    		db.update("update KHACHHANG set CHIETKHAU_FK = null where PK_SEQ= '" + kh.getString("PK_SEQ") + "'");
	    	}
	    	
			db.update("delete from mucchietkhau where pk_seq='" + mckId + "'");
			obj.setMsg("Đã xóa thành công ^^!");		
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
		} catch(Exception e) {	
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			obj.setMsg("Khong The Thuc Hien Xoa ASM Nay,Vui Long Lien He Voi Admin De Sua Loi Nay"); 
			e.printStackTrace();
		}	    	  	
	}

}
