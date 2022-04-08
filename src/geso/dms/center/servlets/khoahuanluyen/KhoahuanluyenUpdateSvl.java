package geso.dms.center.servlets.khoahuanluyen;

import geso.dms.center.beans.khoahuanluyen.IKhoahuanluyen;
import geso.dms.center.beans.khoahuanluyen.IKhoahuanluyenList;
import geso.dms.center.beans.khoahuanluyen.imp.Khoahuanluyen;
import geso.dms.center.beans.khoahuanluyen.imp.KhoahuanluyenList;
import geso.dms.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class KhoahuanluyenUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
       
    public KhoahuanluyenUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IKhoahuanluyen khlBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    	    
	    String id = util.getId(querystring);
	   
	    khlBean = new Khoahuanluyen(id);
	    khlBean.setId(id);
	    khlBean.setUserId(userId);
	    
	    khlBean.init();
        session.setAttribute("khlBean", khlBean);
        
        String nextJSP = "/AHF/pages/Center/KhoaHuanLuyenUpdate.jsp";
        if(querystring.indexOf("display") >= 0)
        	nextJSP = "/AHF/pages/Center/KhoaHuanLuyenDisplay.jsp";
        
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IKhoahuanluyen khlBean;

		Utility util = new Utility();
	    String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));	
	    if(id == null){  	
	    	khlBean = new Khoahuanluyen("");
	    }else{
	    	khlBean = new Khoahuanluyen(id);
	    }
	    	    
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		khlBean.setUserId(userId);	       
    			
		String tieude = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tieude")));
		if (tieude == null)
			tieude = "";
		khlBean.setTenkhoa(tieude);
		
		String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		if (diengiai == null)
			diengiai = "";
		khlBean.setDiengiai(diengiai);
		
		String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
		if (tungay == null)
			tungay = "";
		khlBean.setTungay(tungay);
		
		String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
		if (denngay == null)
			denngay = "";
		khlBean.setDenngay(denngay);
		
		
    	String[] nppIds = request.getParameterValues("nppIds");
    	if(nppIds != null)
    	{
    		String str = "";
    		for(int i = 0; i < nppIds.length; i++ )
    			str += nppIds[i] + ",";
    		if(str.length() > 0)
    			str = str.substring(0, str.length() - 1);
    		khlBean.setNppIds(str);
    	}

    	String[] gsbhIds = request.getParameterValues("gsbhIds");
    	if(gsbhIds != null)
    	{
    		String str = "";
    		for(int i = 0; i < gsbhIds.length; i++ )
    			str += gsbhIds[i] + ",";
    		if(str.length() > 0)
    			str = str.substring(0, str.length() - 1);
    		khlBean.setGsbhIds(str);
    	}
    	
    	String[] gsbh_hoanthanhIds = request.getParameterValues("gsbh_hoanthanhIds");
    	String gsbh_hoanthanh = "";
    	if(gsbh_hoanthanhIds != null)
    	{
    		for(int i = 0; i < gsbh_hoanthanhIds.length; i++ )
    			gsbh_hoanthanh += gsbh_hoanthanhIds[i] + ",";
    		
    		if(gsbh_hoanthanh.length() > 0)
    			gsbh_hoanthanh = gsbh_hoanthanh.substring(0, gsbh_hoanthanh.length() - 1);
    	}
    	
    	String[] ddkdIds = request.getParameterValues("ddkdIds");
    	if(ddkdIds != null)
    	{
    		String str = "";
    		for(int i = 0; i < ddkdIds.length; i++ )
    			str += ddkdIds[i] + ",";
    		if(str.length() > 0)
    			str = str.substring(0, str.length() - 1);
    		khlBean.setNvbhIds(str);
    	}
    	
    	String[] ddkd_hoanthanhIds = request.getParameterValues("ddkd_hoanthanhIds");
    	String ddkd_hoanthanh = "";
    	if(ddkd_hoanthanhIds != null)
    	{
    		for(int i = 0; i < ddkd_hoanthanhIds.length; i++ )
    			ddkd_hoanthanh += ddkd_hoanthanhIds[i] + ",";
    		
    		if(gsbh_hoanthanh.length() > 0)
    			ddkd_hoanthanh = ddkd_hoanthanh.substring(0, ddkd_hoanthanh.length() - 1);
    	}
				
		
 		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
 		if(action.equals("save"))
 		{
    		if (id == null )
    		{
    			if (!khlBean.createKhl())
    			{
    		    	khlBean.setUserId(userId);
    		    	khlBean.createRs();
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("khlBean", khlBean);
    				
    				String nextJSP = "/AHF/pages/Center/KhoaHuanLuyenNew.jsp";
    				response.sendRedirect(nextJSP);
    			}
    			else
    			{
    				IKhoahuanluyenList obj = new KhoahuanluyenList();
    				obj.setUserId(userId);
    				obj.init("");
    				
    				session.setAttribute("obj", obj);
    				session.setAttribute("userId", userId);
		    		
    				response.sendRedirect("/AHF/pages/Center/KhoaHuanLuyen.jsp");	    	
    			}		
    		}
    		else
    		{
    			if (!(khlBean.updateKhl(gsbh_hoanthanh, ddkd_hoanthanh)))
    			{			
    		    	khlBean.setUserId(userId);
    		    	khlBean.createRs();
    		    	
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("khlBean", khlBean);
    				
    				String nextJSP = "/AHF/pages/Center/KhoaHuanLuyenUpdate.jsp";
    				response.sendRedirect(nextJSP);
    			}
    			else
    			{
    				IKhoahuanluyenList obj = new KhoahuanluyenList();
    				obj.setUserId(userId);
    				obj.init("");
    				
    				session.setAttribute("obj", obj);
    				session.setAttribute("userId", userId);
		    		
    				response.sendRedirect("/AHF/pages/Center/KhoaHuanLuyen.jsp");	   	
    			}
    		}
	    }
		else
		{
			khlBean.createRs();
			session.setAttribute("userId", userId);
			session.setAttribute("khlBean", khlBean);
			String nextJSP;
			if (id == null){
				nextJSP = "/AHF/pages/Center/KhoaHuanLuyenNew.jsp";
			}
			else{
				nextJSP = "/AHF/pages/Center/KhoaHuanLuyenUpdate.jsp";   						
			}
			response.sendRedirect(nextJSP);
		}		
	}

}
