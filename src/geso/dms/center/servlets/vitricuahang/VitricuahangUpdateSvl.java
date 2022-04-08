package geso.dms.center.servlets.vitricuahang;

import geso.dms.center.beans.vitricuahang.imp.*;
import geso.dms.center.beans.vitricuahang.*;
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

public class VitricuahangUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private PrintWriter out;  

    public VitricuahangUpdateSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
		if ( Utility.CheckSessionUser( session,  request, response)) {
			Utility.RedireactLogin(session, request, response);
		}
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String id = util.getId(querystring);  	
	    String action= util.getAction(querystring);
	    IVitricuahang vtchBean = new Vitricuahang(id);
	    
        vtchBean.setUserId(userId);
        session.setAttribute("vtchBean", vtchBean);
        String nextJSP = "/AHF/pages/Center/ViTriCuaHangUpdate.jsp";
        if(action.equals("display"))
        	nextJSP = "/AHF/pages/Center/ViTriCuaHangDisplay.jsp";
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
		
		IVitricuahang vtchBean;
		this.out = response.getWriter();
		Utility util = new Utility();
		
	    String id =  util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
	    if(id == null){  	
	    	vtchBean = new Vitricuahang("");
	    }else{
	    	vtchBean = new Vitricuahang(id);
	    }
	    
	    
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		vtchBean.setUserId(userId);
	    
    	String vitri = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vitricuahang")));
		if (vitri == null)
			vitri = "";				
    	vtchBean.setVitricuahang(vitri);
    	
    	String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		if (diengiai == null)
			diengiai = "";
		vtchBean.setDiengiai(diengiai);

		String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
    	if (trangthai == null)
    		trangthai = "0";
    	else
    		trangthai = "1";
    	vtchBean.setTrangthai(trangthai);

		String ngaysua = getDateTime();
    	vtchBean.setNgaysua(ngaysua);
		
		String nguoisua = userId;
		vtchBean.setNguoisua(nguoisua);
    	
		
		boolean error = false;
				
		if (vitri.trim().length()== 0){
			vtchBean.setMessage("Vui Long Nhap Vi Tri Cua Hang");
			error = true;
		}

		if (diengiai.trim().length()== 0){
			vtchBean.setMessage("Vui Long Nhap Dien Giai Vi Tri Cua Hang");
			error = true;
		}
 		
 		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    
		if(action.equals("save"))
		{
			if ( id == null){
				if (!(vtchBean.CreateVtch())){				
					session.setAttribute("vtchBean", vtchBean);
					vtchBean.setUserId(userId);
					String nextJSP = "/AHF/pages/Center/ViTriCuaHangNew.jsp";
					response.sendRedirect(nextJSP);
				}else{
					IVitricuahangList obj = new VitricuahangList();
					obj.setUserId(userId);
					session.setAttribute("obj", obj);
						
					String nextJSP = "/AHF/pages/Center/ViTriCuaHang.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
				
			}else{
				if (!(vtchBean.UpdateVtch())){			
					session.setAttribute("vtchBean", vtchBean);
					String nextJSP = "/AHF/pages/Center/ViTriCuaHangUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else{
					IVitricuahangList obj = new VitricuahangList();
					obj.setUserId(userId);
					session.setAttribute("obj", obj);
						
					String nextJSP = "/AHF/pages/Center/ViTriCuaHang.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
			}
		}else{
			vtchBean.setUserId(userId);
			session.setAttribute("vtchBean", vtchBean);
			
			String nextJSP;
			if (id == null){			
				nextJSP = "/AHF/pages/Center/ViTriCuaHangNew.jsp";
			}else{
				nextJSP = "/AHF/pages/Center/ViTriCuaHangUpdate.jsp";   						
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
