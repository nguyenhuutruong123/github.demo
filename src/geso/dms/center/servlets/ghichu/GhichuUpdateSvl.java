package geso.dms.center.servlets.ghichu;

import geso.dms.center.beans.ghichu.*;
import geso.dms.center.beans.ghichu.imp.*;
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

public class GhichuUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private PrintWriter out;  

    public GhichuUpdateSvl() 
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

	    IGhichu kvBean = new Ghichu(id);
	    
        kvBean.setUserId(userId);
        session.setAttribute("kvBean", kvBean);
        String nextJSP = "/AHF/pages/Center/GhiChuUpdate.jsp";
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		IGhichu kvBean;
		this.out = response.getWriter();
		
		Utility util = new Utility();
		
	    String id =  util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
	    if(id == null){  	
	    	kvBean = new Ghichu("");
	    }else{
	    	kvBean = new Ghichu(id);
	    }
	    
	    
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		kvBean.setUserId(userId);
	    
		String ttId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tinhthanh")));
		if (ttId == null)
			ttId = "";				
    	kvBean.setTinhthanhId(ttId);
    	
    	String qhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("quanhuyen")));
		if (qhId == null)
			qhId = "";				
    	kvBean.setQuanhuyenId(qhId);
    	
    	String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
    	System.out.println("Diengiai : "+diengiai);
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

 		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    
	    	if(action.equals("save"))
	    	{
	    		if (!kiemtraNhap(ttId, qhId, diengiai, kvBean)){
		    		if ( id == null||id.length()==0){
		    			if (!(kvBean.CreatePx())){				
		    				session.setAttribute("kvBean", kvBean);
		    				kvBean.setUserId(userId);
		    				String nextJSP = "/AHF/pages/Center/GhiChuNew.jsp";
		    				response.sendRedirect(nextJSP);
		    			}else{
		    				IGhichuList obj = new GhichuList();
		    				obj.setUserId(userId);
		    				session.setAttribute("obj", obj);
							
		    				String nextJSP = "/AHF/pages/Center/GhiChu.jsp";
		    				response.sendRedirect(nextJSP);			    			    									
		    			}
					
		    		}else{
		    			if (!(kvBean.UpdatePx())){			
		    				session.setAttribute("kvBean", kvBean);
		    				String nextJSP = "/AHF/pages/Center/GhiChuUpdate.jsp";
		    				response.sendRedirect(nextJSP);
		    			}
		    			else{
		    				IGhichuList obj = new GhichuList();
		    				obj.setUserId(userId);
		    				session.setAttribute("obj", obj);
							
		    				String nextJSP = "/AHF/pages/Center/GhiChu.jsp";
		    				response.sendRedirect(nextJSP);			    			    									
		    			}
		    		}
	    	}else{
	    		kvBean.setUserId(userId);
	    		session.setAttribute("kvBean", kvBean);
			
	    		String nextJSP;
	    		if (id == null){			
	    			nextJSP = "/AHF/pages/Center/GhiChuNew.jsp";
	    		}else{
	    			nextJSP = "/AHF/pages/Center/GhiChuUpdate.jsp";   						
	    		}
	    		response.sendRedirect(nextJSP);
			
	    	}
	    }else{
    		kvBean.setUserId(userId);
    		kvBean.createRS();
    		session.setAttribute("kvBean", kvBean);
		
    		String nextJSP;
    		if (id == null){			
    			nextJSP = "/AHF/pages/Center/GhiChuNew.jsp";
    		}else{
    			nextJSP = "/AHF/pages/Center/GhiChuUpdate.jsp";   						
    		}
    		response.sendRedirect(nextJSP);
	    	
	    }
	}
	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private boolean kiemtraNhap(String ttId, String qhId, String diengiai, IGhichu kvBean)
	{
		boolean error = false;
		
		/*if (ttId.trim().length()== 0){
			kvBean.setMessage("Vui l??ng ch???n th??ng tin t???nh th??nh.");
			error = true;
		}
		
		if (qhId.trim().length()== 0){
			kvBean.setMessage("Vui l??ng ch???n th??ng tin qu???n huy???n.");
			error = true;
		}*/

		if (diengiai.trim().length()== 0){
			kvBean.setMessage("Vui l??ng nh???p ?????y ????? th??ng tin.");
			error = true;
		}
		
		return error;
	}

}
