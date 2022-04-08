package geso.dms.center.servlets.khoasongay;

import geso.dms.center.beans.khoasongay.IKhoasotudong;
import geso.dms.center.beans.khoasongay.imp.Khoasotudong;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class KhoasotudongSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	String Bienchung;
	
    public KhoasotudongSvl() 
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
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    obj = new Khoasotudong();
	    obj.setUserId(userId);
	    obj.init();
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Center/KhoaSoTuDong.jsp";
		response.sendRedirect(nextJSP);
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
	    
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		obj.setUserId(userId);
		
		String[] vungIds = request.getParameterValues("vungIds");
		obj.setVungIds(vungIds);
	
		String[] kvIds = request.getParameterValues("kvIds");
	
		String[] nppIds = request.getParameterValues("nppIds");
		String[] danhsachnpp=request.getParameterValues("danhsachnpp");
		String[] ngaylui = request.getParameterValues("ngaylui");
		
		obj = new Khoasotudong();
	    obj.setUserId(userId);
	    obj.setDanhSachNhaPP(danhsachnpp);
	    obj.setNgaylui(ngaylui);
	    
	    obj.setVungIds(vungIds);
	    obj.setKvIds(kvIds);
	    obj.setNppIds(nppIds);
	   
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    
	    int phut =0;
	    try{
	    phut=Integer.parseInt(util.antiSQLInspection( geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("phut"))));
	    }catch(Exception er){
	    	
	    }
	    obj.SetPhut(phut);
	    
	    int gio =0;
	    try{
	    	gio=Integer.parseInt(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gio"))));
	    }catch(Exception er){
	    	
	    }
	    obj.SetGio(gio);
	    
	    int beforeday =0;
	    try{
	    	beforeday=Integer.parseInt(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("beforeday"))));
	    }catch(Exception er){
	    	
	    }
	    obj.SetBeforeDay(beforeday);
	    
	    int ngayluidh =0;
	    try{
	    	ngayluidh=Integer.parseInt(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngayluidh"))));
	    }catch(Exception er){
	    	
	    }
	    obj.setNgayluidh(ngayluidh);
	   
	   
	    
		if(!action.equals("save"))
		{ 
			 obj.createRs();
			session.setAttribute("obj", obj);
			String nextJSP = "/AHF/pages/Center/KhoaSoTuDong.jsp";
			response.sendRedirect(nextJSP);
		}
		else
		{
			try
			{
				if(obj.updateKhoaso())
					obj.setMsg("Thiết lập khóa sổ tự động thành công");
				
			}
			catch (Exception e)
			{
				obj.setMsg("Khong the Cap nhat khoa so tu dong " + e.toString());
			}
			 obj.createRs();
			session.setAttribute("obj", obj);
			String nextJSP = "/AHF/pages/Center/KhoaSoTuDong.jsp";
			response.sendRedirect(nextJSP);
			
		}
		
	}

}
