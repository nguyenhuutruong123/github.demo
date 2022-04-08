package geso.dms.center.servlets.hangcuahang;

import geso.dms.center.beans.hangcuahang.IHangcuahang;
import geso.dms.center.beans.hangcuahang.IHangcuahangList;
import geso.dms.center.beans.hangcuahang.imp.Hangcuahang;
import geso.dms.center.beans.hangcuahang.imp.HangcuahangList;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

 public class HangcuahangUpdateSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   

	public HangcuahangUpdateSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		if ( Utility.CheckSessionUser( session,  request, response)) {
			Utility.RedireactLogin(session, request, response);
		}
		
		IHangcuahangList obj;
		   PrintWriter out;
		
		out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    //Utility util = new Utility();
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String id = util.getId(querystring);  	
	    String action= util.getAction(querystring);
	    IHangcuahang hchBean = new Hangcuahang(id);
	    
        hchBean.setUserId(userId);
        session.setAttribute("hchBean", hchBean);
        String nextJSP = "/AHF/pages/Center/HangCuaHangUpdate.jsp";
        if(action.equals("display"))
        	nextJSP = "/AHF/pages/Center/HangCuaHangDisplay.jsp";
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IHangcuahangList obj;
		   PrintWriter out;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		if ( Utility.CheckSessionUser( session,  request, response)) {
			Utility.RedireactLogin(session, request, response);
		}
		
		IHangcuahang hchBean;
		out = response.getWriter();
		
		Utility util = new Utility();
		
	    String id =  util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
	    if(id == null){  	
	    	hchBean = new Hangcuahang("");
	    }else{
	    	hchBean = new Hangcuahang(id);
	    }
	        
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		hchBean.setUserId(userId);
	    
    	String hang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("hangcuahang")));
		if (hang == null)
			hang = "";				
    	hchBean.setHangcuahang(hang);
    	
    	String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		if (diengiai == null)
			diengiai = "";
		hchBean.setDiengiai(diengiai);

		String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
    	if (trangthai == null || trangthai.trim().length() == 0)
    		trangthai = "0";
    	else
    		trangthai = "1";
    	hchBean.setTrangthai(trangthai);
    	
    	String thangtb = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thangtb")));
		if (thangtb == null)
			thangtb = "";
		hchBean.setThangtb(thangtb);
		
		String tumuc = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tumuc")));
		if (tumuc == null)
			tumuc = "";
		hchBean.settumuc(tumuc.replace(",", ""));
		
		String denmuc = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denmuc")));
		if (denmuc == null)
			denmuc = "";
		hchBean.setdenmuc(denmuc.replace(",", ""));

    	
		/*
		 * hchBean.settumuc(util.antiSQLInspection(geso.dms.center.util.Utility.
		 * antiSQLInspection(request.getParameter("tumuc"))));
		 * hchBean.setdenmuc(util.antiSQLInspection(geso.dms.center.util.Utility.
		 * antiSQLInspection(request.getParameter("denmuc"))));
		 */
		String ngaysua = getDateTime();
    	hchBean.setNgaysua(ngaysua);
		
		String nguoisua = userId;
		hchBean.setNguoisua(nguoisua);
    	
		
		boolean error = false;
				
		if (hang.trim().length()== 0){
			hchBean.setMessage("Vui lòng nhập vào Hạng Khách Hàng");
			error = true;
		}

		if (diengiai.trim().length()== 0){
			hchBean.setMessage("Vui lòng nhập vào Diễn giải Hạng Khách Hàng");
			error = true;
		}
 		
 		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    //if (!error){
	    	if(action.equals("save") && !error)
	    	{
	    		if ( id == null || id.trim().length() == 0){
	    			if (!(hchBean.CreateHch())){				
	    				session.setAttribute("hchBean", hchBean);
	    				hchBean.setUserId(userId);
	    				String nextJSP = "/AHF/pages/Center/HangCuaHangNew.jsp";
	    				response.sendRedirect(nextJSP);
	    			}else{
	    				obj = new HangcuahangList();
	    				obj.setUserId(userId);
	    				session.setAttribute("obj", obj);
						
	    				String nextJSP = "/AHF/pages/Center/HangCuaHang.jsp";
	    				response.sendRedirect(nextJSP);			    			    									
	    			}
				
	    		}else{
	    			if (!(hchBean.UpdateHch())){			
	    				session.setAttribute("hchBean", hchBean);
	    				String nextJSP = "/AHF/pages/Center/HangCuaHangUpdate.jsp";
	    				response.sendRedirect(nextJSP);
	    			}
	    			else{
	    				obj = new HangcuahangList();
	    				obj.setUserId(userId);
	    				session.setAttribute("obj", obj);
						
	    				String nextJSP = "/AHF/pages/Center/HangCuaHang.jsp";
	    				response.sendRedirect(nextJSP);			    			    									
	    			}
	    		}
	    	}else{
	    		
	    		session.setAttribute("hchBean", hchBean);
	    		hchBean.setUserId(userId);
	    		String nextJSP;
	    		if (id == null){			
	    			nextJSP = "/AHF/pages/Center/HangCuaHangNew.jsp";
	    		}else{
	    			nextJSP = "/AHF/pages/Center/HangCuaHangUpdate.jsp";   						
	    		}
	    		response.sendRedirect(nextJSP);
	    	}
		/*
		 * }else{ hchBean.setUserId(userId); session.setAttribute("hchBean", hchBean);
		 * 
		 * String nextJSP = ""; if (id == null || id.trim().length() == 0){ nextJSP =
		 * "/AHF/pages/Center/HangCuaHangNew.jsp"; }else{ nextJSP =
		 * "/AHF/pages/Center/HangCuaHangUpdate.jsp"; } response.sendRedirect(nextJSP);
		 * 
		 * }
		 */
	}
	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}