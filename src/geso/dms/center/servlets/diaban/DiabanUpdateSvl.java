package geso.dms.center.servlets.diaban;

import geso.dms.center.beans.diaban.imp.*;
import geso.dms.center.beans.diaban.*;
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

public class DiabanUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private PrintWriter out;  

    public DiabanUpdateSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String id = util.getId(querystring);  	
	    
	    System.out.println("id = " + id);
	    IDiaban kvBean = new Diaban(id);
	    
        kvBean.setUserId(userId);
        session.setAttribute("kvBean", kvBean);
        String nextJSP = "/AHF/pages/Center/DiabanUpdate.jsp";
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		IDiaban kvBean;
		this.out = response.getWriter();
		
		Utility util = new Utility();
		
	    String id =  util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
	    if(id == null){  	
	    	id ="";
	    	kvBean = new Diaban("");
	    }else{
	    	kvBean = new Diaban(id);
	    }
	    
	    
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		kvBean.setUserId(userId);
	    
		String diaban = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diaban")));
		if (diaban == null)
			diaban = "";				
    	kvBean.setTen(diaban);
    	
    	String khuvuc = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvuc")));
		if (khuvuc == null)
			khuvuc = "";				
    	kvBean.setKhuvucId(khuvuc);

    	
    	String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		if (diengiai == null)
			diengiai = "";
		kvBean.setDiengiai(diengiai);

		String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
    	if (trangthai == null)
    		trangthai = "0";
    	else
    		trangthai = "1";
    	kvBean.setTrangthai(trangthai);

		String ngaysua = getDateTime();
    	kvBean.setNgaysua(ngaysua);
		
		String nguoisua = userId;
		kvBean.setNguoisua(nguoisua);
    	
		
		boolean error = false;
				
		if (diaban.trim().length()== 0){
			kvBean.setMessage("Vui Long Nhap Ten Khu Vuc");
			error = true;
		}
		
		if (khuvuc.trim().length()== 0){
			kvBean.setMessage("Vui Long Chon Vung Mien");
			error = true;
		}

		if (diengiai.trim().length()== 0){
			kvBean.setMessage("Vui Long Nhap Dien Giai Khu Vuc");
			error = true;
		}
 		
 		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (!error){
	    	if(action.equals("save"))
	    	{
	    		if ( id == null || id.trim().length()<=0){
	    			if (!(kvBean.CreateDiaBan())){				
	    				session.setAttribute("kvBean", kvBean);
	    				kvBean.setUserId(userId);
	    				String nextJSP = "/AHF/pages/Center/DiabanUpdate.jsp";
	    				response.sendRedirect(nextJSP);
	    			}else{
	    				IDiabanList obj = new DiabanList();
	    				obj.setUserId(userId);
	    				session.setAttribute("obj", obj);
						
	    				String nextJSP = "/AHF/pages/Center/Diaban.jsp";
	    				response.sendRedirect(nextJSP);			    			    									
	    			}
				
	    		}else{
	    			if (!(kvBean.UpdateDiaBan())){			
	    				session.setAttribute("kvBean", kvBean);
	    				String nextJSP = "/AHF/pages/Center/DiabanUpdate.jsp";
	    				response.sendRedirect(nextJSP);
	    			}
	    			else{
	    				IDiabanList obj = new DiabanList();
	    				obj.setUserId(userId);
	    				session.setAttribute("obj", obj);
						
	    				String nextJSP = "/AHF/pages/Center/Diaban.jsp";
	    				response.sendRedirect(nextJSP);			    			    									
	    			}
	    		}
	    	}else{
	    		kvBean.setUserId(userId);
	    		session.setAttribute("kvBean", kvBean);
			
	    		String nextJSP;
	    		nextJSP = "/AHF/pages/Center/DiabanUpdate.jsp";   
	    		response.sendRedirect(nextJSP);
			
	    	}
	    }else{
    		kvBean.setUserId(userId);
    		session.setAttribute("kvBean", kvBean);
		
    		String nextJSP;
    		nextJSP = "/AHF/pages/Center/DiabanUpdate.jsp";   
    		response.sendRedirect(nextJSP);
	    	
	    }
	}
	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
