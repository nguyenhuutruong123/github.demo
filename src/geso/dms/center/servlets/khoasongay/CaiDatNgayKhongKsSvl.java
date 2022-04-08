package geso.dms.center.servlets.khoasongay;

import geso.dms.center.beans.khoasongay.IKhoasotudong;
import geso.dms.center.beans.khoasongay.imp.Khoasotudong;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class CaiDatNgayKhongKsSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    public CaiDatNgayKhongKsSvl() 
    {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		IKhoasotudong obj;
		PrintWriter out; 
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    System.out.println("Truy van "+querystring);
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action=util.getAction(querystring);
	    String id=util.getId(querystring);
	    
	    if(action.equals("delete")){
	    	this.Xoa(id);
	    }
	    obj = new Khoasotudong();
	    
	    
	    obj.setUserId(userId);
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Center/Thietlapngaykhoaso.jsp";
		response.sendRedirect(nextJSP);
	}

	private void Xoa(String id) {
		// TODO Auto-generated method stub
		dbutils db=new dbutils();
		
		String sql=" select * from khoasongay where ngayks " +
				"  >=(select ngaykhongks from ngaykhongks  where pk_seq="+id+")";
		System.out.println(sql);
		ResultSet rs=db.get(sql);
		try{
			if(rs.next()){
				
			}else{
		
			 sql="delete ngaykhongks where pk_seq="+id;
			 db.update(sql);
			}
		}catch(Exception er){
			
		}
		
		db.shutDown();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		IKhoasotudong obj = new Khoasotudong();
		PrintWriter out; 
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
	    Utility util=new Utility();
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
		
		obj = new Khoasotudong();
	    
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    System.out.println ("action  : "+action);
	    
	   String nam=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nam"));
	   
	 
	    
	    String NgayKs= util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngayks")));
	    obj.SetNgayKhoaSo(NgayKs);
	    
	    System.out.println ("Ngay KS : "+NgayKs);
		if(action.equals("save"))
		{ 
					dbutils db=new dbutils();
					String sql="insert into ngaykhongks (ngaykhongks) values ('"+NgayKs+"')";
					db.update(sql);
					
					
					db.shutDown();
		}
		if(action.equals("themngaycn")){
			dbutils db=new dbutils();
			String sql="select Replace(convert(char(10), ngay , 102) , '.', '-' )  as ngay from ( "+
						" select   dateadd(dd,number,'"+nam+"0101')  as ngay from master..spt_values "+
						 " where type = 'p' "+
						 " and year(dateadd(dd,number,'"+nam+"0101'))=year('"+nam+"0101') "+
						 " and DATEPART(dw,dateadd(dd,number,'"+nam+"0101')) = 1 "+
						 " ) as a";
			System.out.println("Du lieu nek : "+sql);
			ResultSet rs=db.get(sql);
			try{
				while(rs.next()){
					 sql="insert into ngaykhongks (ngaykhongks) values ('"+rs.getString("ngay")+"')";
					
					db.update(sql);
				}
				rs.close();
			}catch(Exception er){
				System.out.println(er.toString());
			}
			
			
			
			db.shutDown();
		}
		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Center/Thietlapngaykhoaso.jsp";
		response.sendRedirect(nextJSP);
	}
	  
	

	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
