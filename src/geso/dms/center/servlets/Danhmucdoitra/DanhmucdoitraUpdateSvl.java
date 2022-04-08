package geso.dms.center.servlets.Danhmucdoitra;


import geso.dms.center.beans.Danhmucdoitra.IDanhmucdoitra;
import geso.dms.center.beans.Danhmucdoitra.IDanhmucdoitraList;
import geso.dms.center.beans.Danhmucdoitra.imp.Danhmucdoitra;
import geso.dms.center.beans.Danhmucdoitra.imp.DanhmucdoitraList;
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

public class DanhmucdoitraUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private PrintWriter out;  

    public DanhmucdoitraUpdateSvl() 
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

	    IDanhmucdoitra lchBean = new Danhmucdoitra(id);
	    
        lchBean.setUserId(userId);
        session.setAttribute("lchBean", lchBean);
        String nextJSP = "/AHF/pages/Center/DanhmucdoitraUpdate.jsp";
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("vao day new");
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		IDanhmucdoitra lchBean;
		this.out = response.getWriter();
		
		Utility util = new Utility();
		
	    String id =  util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
	    if(id == null){  	
	    	lchBean = new Danhmucdoitra("");
	    }else{
	    	lchBean = new Danhmucdoitra(id);
	    }
	        
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		lchBean.setUserId(userId);
	    
    	String tendanhmuc = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tendanhmuc")));
		if (tendanhmuc == null)
			tendanhmuc = "";				
    	lchBean.setLoaicuahang(tendanhmuc);
    	
    	String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaicuahang")));
		if (diengiai == null)
			diengiai = "";
		lchBean.setDiengiai(diengiai);

		String loaidh = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Loaidh")));
    	lchBean.setTrangthai(loaidh);

		String ngaysua = getDateTime();
    	lchBean.setNgaysua(ngaysua);
		
		String nguoisua = userId;
		lchBean.setNguoisua(nguoisua);
    	
		
		boolean error = false;
				
		if (diengiai.trim().length()== 0){
			lchBean.setMessage("Vui Long Nhap lý do Cua Hang");
			error = true;
		}

	
 		
 		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if(!error){
	    	if(action.equals("save"))
	    	{
	    		if ( id == null){
	    			if (!(lchBean.CreateLch())){				
	    				session.setAttribute("lchBean", lchBean);
	    				lchBean.setUserId(userId);
	    				String nextJSP = "/AHF/pages/Center/DanhmucdoitraNew.jsp";
	    				response.sendRedirect(nextJSP);
	    			}else{
	    				IDanhmucdoitraList obj = new DanhmucdoitraList();
	    				obj.setUserId(userId);
	    				session.setAttribute("obj", obj);
						
	    				String nextJSP = "/AHF/pages/Center/Danhmucdoitra.jsp";
	    				response.sendRedirect(nextJSP);			    			    									
	    			}
				
	    		}else{
	    			if (!(lchBean.UpdateLch())){			
	    				session.setAttribute("lchBean", lchBean);
	    				String nextJSP = "/AHF/pages/Center/DanhmucdoitraUpdate.jsp";
	    				response.sendRedirect(nextJSP);
	    			}
	    			else{
	    				IDanhmucdoitraList obj = new DanhmucdoitraList();
	    				obj.setUserId(userId);
	    				session.setAttribute("obj", obj);
						
	    				String nextJSP = "/AHF/pages/Center/Danhmucdoitra.jsp";
	    				response.sendRedirect(nextJSP);			    			    									
	    			}
	    		}
	    	}else{
	    		lchBean.setUserId(userId);
	    		session.setAttribute("lchBean", lchBean);
			System.out.println("va _______________");
	    		String nextJSP;
	    		if (id == null){			
	    			nextJSP = "/AHF/pages/Center/DanhmucdoitraNew.jsp";
	    		}else{
	    			nextJSP = "/AHF/pages/Center/DanhmucdoitraUpdate.jsp";   						
	    		}
	    		response.sendRedirect(nextJSP);
			
	    	}
	    }else{
    		lchBean.setUserId(userId);
    		session.setAttribute("lchBean", lchBean);
		
    		String nextJSP;
    		if (id == null){			
    			nextJSP = "/AHF/pages/Center/DanhmucdoitraNew.jsp";
    		}else{
    			nextJSP = "/AHF/pages/Center/DanhmucdoitraUpdate.jsp";   						
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
