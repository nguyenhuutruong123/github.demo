package geso.dms.center.servlets.loaicuahang;

import geso.dms.center.beans.loaicuahang.imp.*;
import geso.dms.center.beans.loaicuahang.*;
import geso.dms.center.util.Utility;
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

public class LoaicuahangUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private PrintWriter out;  

    public LoaicuahangUpdateSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		
		if ( Utility.CheckSessionUser( session,  request, response)) {
			Utility.RedireactLogin(session, request, response);
		}
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String id = util.getId(querystring);  	
	    String action= util.getAction(querystring);
	    ILoaicuahang lchBean = new Loaicuahang(id);
	    
        lchBean.setUserId(userId);
        session.setAttribute("lchBean", lchBean);
        String nextJSP = "/AHF/pages/Center/LoaiCuaHangUpdate.jsp";
        if(action.equals("display"))
        	nextJSP = "/AHF/pages/Center/LoaiCuaHangDisplay.jsp";
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		if ( Utility.CheckSessionUser( session,  request, response)) {
			Utility.RedireactLogin(session, request, response);
		}
		
		ILoaicuahang lchBean;
		this.out = response.getWriter();
		
		Utility util = new Utility();
		
	    String id =  util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
	    if(id == null){  	
	    	lchBean = new Loaicuahang("");
	    }else{
	    	lchBean = new Loaicuahang(id);
	    }
	        
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		lchBean.setUserId(userId);
	    
		String khongVat = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khongVat")));
		if (khongVat == null)
			khongVat = "0";		
		else
			khongVat = "1";
    	lchBean.setKhongVat(khongVat);
    	
		
    	String loai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaicuahang")));
		if (loai == null)
			loai = "";				
    	lchBean.setLoaicuahang(loai);
    	
    	String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		if (diengiai == null)
			diengiai = "";
		lchBean.setDiengiai(diengiai);

		String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
    	if (trangthai == null || trangthai.trim().length()==0)
    		trangthai = "0";
    	else
    		trangthai = "1";
    	lchBean.setTrangthai(trangthai);

		String ngaysua = getDateTime();
    	lchBean.setNgaysua(ngaysua);
		
		String nguoisua = userId;
		lchBean.setNguoisua(nguoisua);
    	
		
		boolean error = false;
				
		if (loai.trim().length()== 0){
			lchBean.setMessage("Vui lòng nhập vào Loại Khách Hàng");
			error = true;
		}

		if (diengiai.trim().length()== 0){
			lchBean.setMessage("Vui lòng nhập vào Diễn giải Loại Khách Hàng");
			error = true;
		}
 		
 		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if(!error){
	    	if(action.equals("save"))
	    	{
	    		if ( id == null || id.length()==0){
	    			if (!(lchBean.CreateLch())){				
	    				session.setAttribute("lchBean", lchBean);
	    				lchBean.setUserId(userId);
	    				String nextJSP = "/AHF/pages/Center/LoaiCuaHangNew.jsp";
	    				response.sendRedirect(nextJSP);
	    			}else{
	    				ILoaicuahangList obj = new LoaicuahangList();
	    				obj.setUserId(userId);
	    				session.setAttribute("obj", obj);
						
	    				String nextJSP = "/AHF/pages/Center/LoaiCuaHang.jsp";
	    				response.sendRedirect(nextJSP);			    			    									
	    			}
				
	    		}else{
	    			if (!(lchBean.UpdateLch())){			
	    				session.setAttribute("lchBean", lchBean);
	    				String nextJSP = "/AHF/pages/Center/LoaiCuaHangUpdate.jsp";
	    				response.sendRedirect(nextJSP);
	    			}
	    			else{
	    				ILoaicuahangList obj = new LoaicuahangList();
	    				obj.setUserId(userId);
	    				session.setAttribute("obj", obj);
						
	    				String nextJSP = "/AHF/pages/Center/LoaiCuaHang.jsp";
	    				response.sendRedirect(nextJSP);			    			    									
	    			}
	    		}
	    	}else{
	    		lchBean.setUserId(userId);
	    		session.setAttribute("lchBean", lchBean);
			
	    		String nextJSP;
	    		if (id == null){			
	    			nextJSP = "/AHF/pages/Center/LoaiCuaHangNew.jsp";
	    		}else{
	    			nextJSP = "/AHF/pages/Center/LoaiCuaHangUpdate.jsp";   						
	    		}
	    		response.sendRedirect(nextJSP);
			
	    	}
	    }else{
    		lchBean.setUserId(userId);
    		session.setAttribute("lchBean", lchBean);
		
    		String nextJSP;
    		if (id == null){			
    			nextJSP = "/AHF/pages/Center/LoaiCuaHangNew.jsp";
    		}else{
    			nextJSP = "/AHF/pages/Center/LoaiCuaHangUpdate.jsp";   						
    		}
    		response.sendRedirect(nextJSP);
	    	
	    }
	}
	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
