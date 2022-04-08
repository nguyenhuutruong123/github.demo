package geso.dms.distributor.servlets.kehoachnhanvien;


import geso.dms.distributor.beans.kehoachnhanvien.IKhaibaongaynghi;
import geso.dms.distributor.beans.kehoachnhanvien.IKhaibaongaynghiList;
import geso.dms.distributor.beans.kehoachnhanvien.imp.Khaibaongaynghi;
import geso.dms.distributor.beans.kehoachnhanvien.imp.KhaibaongaynghiList;
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


public class KhaibaoNgaynghiUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    public KhaibaoNgaynghiUpdateSvl() 
    {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		IKhaibaongaynghi obj;
		PrintWriter out; 
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	    String id = util.getId(querystring);
	    String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
	    if(view == null)
	    	view = "";
	    obj = new Khaibaongaynghi();
	    obj.setUserId(userId);
	    obj.setId(id);
	    obj.setView(view);
		obj.init();
		session.setAttribute("obj", obj);
		String nextJSP = "";
		if(action.equals("update"))
			nextJSP = "/AHF/pages/Distributor/KhaiBaoNgayNghiUpdate.jsp";
		else
			nextJSP = "/AHF/pages/Distributor/KhaiBaoNgayNghiDisplay.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		IKhaibaongaynghi obj = new Khaibaongaynghi();
		PrintWriter out; 
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
	    Utility util=new Utility();
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    obj.setUserId(userId);
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    System.out.println ("action  : "+action);
	    
	    String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
	    if(id == null) id = "";
	    obj.setId(id);
	    
	    String ddkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdId")));
	    if(ddkdId == null) ddkdId = "";
	    obj.setDdkdId(ddkdId);
	    
	    String thang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang")));
	    if(thang == null) ddkdId = "";
	    obj.setThang(thang);
	    
	    String nam = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nam")));
	    if(nam == null) nam = "";
	    obj.setNam(nam);
	    
	    String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
	    if(view == null) view = "";
	    obj.setView(view);
	    
	    boolean flag = false;
	    String[] Ngay = request.getParameterValues("ngay");
	    for(int i = 0; i< Ngay.length; i++)
	    	if(Ngay[i].trim().length() != 0)
	    		flag = true;
	    if(!flag)
	    	 Ngay = new String[0];
	    obj.SetNgayNghi(Ngay);
	    
	    String[] ghichu = request.getParameterValues("ghichu");
	    if(ghichu == null)
	    	ghichu = new String[0];
	    obj.SetGhichu(ghichu);
	    
		if(action.equals("save"))
		{ 
			if(ddkdId.length() == 0)
				obj.setMsg("Vui lòng chọn Nhân viên bán hàng.");
			if(!flag)
				obj.setMsg("Vui lòng chọn ngày nghỉ.");
			
			if(obj.getMsg().length() == 0 && id.length() == 0 && obj.CreateNew()){
				IKhaibaongaynghiList lst = new KhaibaongaynghiList();
				lst.setUserId(userId);			    
				lst.setView(view);
				session.setAttribute("obj", lst);
				lst.init();
				String nextJSP = "/AHF/pages/Distributor/KhaiBaoNgayNghi.jsp";
				response.sendRedirect(nextJSP);
				return;
			}
			else if(obj.getMsg().length() == 0 && id.length() > 0 && obj.Update()){
				IKhaibaongaynghiList lst = new KhaibaongaynghiList();
				lst.setUserId(userId);			    
				lst.setView(view);
				session.setAttribute("obj", lst);
				lst.init();
				String nextJSP = "/AHF/pages/Distributor/KhaiBaoNgayNghi.jsp";
				response.sendRedirect(nextJSP);
				return;
			}
			else{
				obj.init();
				session.setAttribute("obj", obj);
				String nextJSP = "/AHF/pages/Distributor/KhaiBaoNgayNghiNew.jsp";
				response.sendRedirect(nextJSP);
				return;
			}
		}
	}
	  
	

	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
