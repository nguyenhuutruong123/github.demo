package geso.dms.center.servlets.kehoachbanhang;

import geso.dms.center.beans.kehoachbanhang.IKehoachbanhang;
import geso.dms.center.beans.kehoachbanhang.IKehoachbanhangList;
import geso.dms.center.beans.kehoachbanhang.imp.Kehoachbanhang;
import geso.dms.center.beans.kehoachbanhang.imp.KehoachbanhangList;
import geso.dms.distributor.beans.tuyenbanhang.*;
import geso.dms.distributor.beans.tuyenbanhang.imp.*;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class KehoachbanhangUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	
    public KehoachbanhangUpdateSvl() 
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
		
		IKehoachbanhang tbhBean;
		PrintWriter out; 


		
		out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    
	    String id = util.getId(querystring);  	
	    tbhBean = new Kehoachbanhang(id);
	    tbhBean.setUserId(userId);
	    tbhBean.init();
	    

		String action = util.getAction(querystring);
		if (action.equals("display"))
		{
			tbhBean.setIsDisplay("1");
		}
	          
        session.setAttribute("tbhBean", tbhBean);
        String nextJSP = "/AHF/pages/Center/KeHoachBanHangUpdate.jsp";
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
		
		IKehoachbanhang tbhBean;
		PrintWriter out; 

		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");

		
		out = response.getWriter();
		
	    String id = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id"));
	    if(id == null)
	    	tbhBean = new Kehoachbanhang("");
	    else
	    	tbhBean = new Kehoachbanhang(id);
	    
		userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
		tbhBean.setUserId(userId);
	    
		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
		if (nppId == null)
			nppId = "";
		tbhBean.setNppId(nppId);
		
    	String ddkdId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdTen"));
		if (ddkdId == null)
			ddkdId = "";				
    	tbhBean.setDdkdId(ddkdId);
    	
    	String diengiai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tbhTen"));
    	if (diengiai == null)
    		diengiai = "";
    	tbhBean.setDiengiai(diengiai);
    	
    	String nlv = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nlv"));
		if (nlv == null)
			nlv = "";
		tbhBean.setNgaylamviec(nlv);
		System.out.println("NLV: "+nlv);
		String khTen = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khTen"));
		if (khTen == null)
			khTen = "";
		tbhBean.setTenkhachhang(khTen);
		
		String diachi = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diachi"));
		if (diachi == null)
			diachi = "";
		tbhBean.setDiachi(diachi);

		
		String[] kh_tbh_dptIds = request.getParameterValues("kh_tbh_dptList");
		tbhBean.setKh_Tbh_DptIds(kh_tbh_dptIds);
		
		String[] kh_tbh_cdptIds = request.getParameterValues("kh_tbh_cdptList");
		tbhBean.setKh_Tbh_CdptIds(kh_tbh_cdptIds);
		
		String[] thutu=request.getParameterValues("thutu");
		tbhBean.setSoTT(thutu);
		
		String ngaysua = getDateTime();
    	tbhBean.setNgaysua(ngaysua);
    	
		boolean error = false;
		
		if (ddkdId.trim().length()== 0){
			tbhBean.setMessage("Vui lòng chọn đại diện kinh doanh.");
			error = true;
		}
		
		if (diengiai.trim().length()== 0){
			tbhBean.setMessage("Vui lòng nhập diễn giải.");
			error = true;
		}
		
		if (nlv.trim().length()== 0){
			tbhBean.setMessage("Vui lòng chọn ngày làm việc.");
			error = true;		
		}
		
 		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
 		
 		if(action.equals("save"))
		{
			if ( id == null)
			{
				System.out.println("Ban nhan nut tao moi ... \n");
				if (!(tbhBean.CreateTbh()))
				{	
			    	tbhBean.setUserId(userId);
				    tbhBean.createRS();
				    
			    	// Save Data into session
			    	session.setAttribute("tbhBean", tbhBean);
		    		
		    		String nextJSP = "/AHF/pages/Center/KeHoachBanHangNew.jsp";
		    		response.sendRedirect(nextJSP);
				}
				else{
					IKehoachbanhangList obj = new KehoachbanhangList();
					obj.setUserId(userId);
					obj.init("");
					session.setAttribute("obj", obj);
						
					String nextJSP = "/AHF/pages/Center/KeHoachBanHang.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}	
			}
			else{

				if (!(tbhBean.UpdateTbh()))
				{
					tbhBean.setUserId(userId);
					tbhBean.init();
					session.setAttribute("tbhBean", tbhBean);
					String nextJSP = "/AHF/pages/Center/KeHoachBanHangUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else{
					IKehoachbanhangList obj = new KehoachbanhangList();
					obj.setUserId(userId);
					obj.init("");
					session.setAttribute("obj", obj);
						
					String nextJSP = "/AHF/pages/Center/KeHoachBanHang.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
			}
		}
 		else
		{		
			tbhBean.setUserId(userId);	
			tbhBean.createRS();
			session.setAttribute("tbhBean", tbhBean);			
			String nextJSP;
			if (id == null){			
				nextJSP = "/AHF/pages/Center/KeHoachBanHangNew.jsp";
			}else{
				nextJSP = "/AHF/pages/Center/KeHoachBanHangUpdate.jsp";   						
			}
			response.sendRedirect(nextJSP);		
		}	
		}
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
