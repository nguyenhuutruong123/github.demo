package geso.dms.center.servlets.nhomctkhuyenmai;

import geso.dms.center.beans.nhomctkhuyenmai.INhomctkhuyenmai;
import geso.dms.center.beans.nhomctkhuyenmai.INhomctkhuyenmaiList;
import geso.dms.center.beans.nhomctkhuyenmai.imp.Nhomctkhuyenmai;
import geso.dms.center.beans.nhomctkhuyenmai.imp.NhomctkhuyenmaiList;
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


public class NhomctkhuyenmaiUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
 
    public NhomctkhuyenmaiUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		
		INhomctkhuyenmai nctkmBean;
		PrintWriter out = response.getWriter(); 

		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    	    
	    String id = util.getId(querystring);

	    nctkmBean = new Nhomctkhuyenmai(id);
	    nctkmBean.setUserId(userId);
	    nctkmBean.init();
        
        session.setAttribute("nctkmBean", nctkmBean);
        String nextJSP = "/AHF/pages/Center/NhomCTKMUpdate.jsp";
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		INhomctkhuyenmai nctkmBean;
		Utility util = new Utility();
	    String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));	
	    if(id == null){  	
	    	nctkmBean = new Nhomctkhuyenmai("");
	    }else{
	    	nctkmBean = new Nhomctkhuyenmai(id);
	    }
	    	    
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		nctkmBean.setUserId(userId);	       
    			
		String tennhom = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tennhom")));
		if (tennhom == null)
			tennhom = "";
		nctkmBean.setTen(tennhom);
		
		String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		if (diengiai == null)
			diengiai = "";
		nctkmBean.setDiengiai(diengiai);
		
		String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
		if (tungay == null)
			tungay = "";
		nctkmBean.setTungay(tungay);
		System.out.println("tu ngay la: " + nctkmBean.getTungay());
		
		String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
		if (denngay == null)
			denngay = "";
		nctkmBean.setDenngay(denngay);
	
    	String[] ctkmIds = request.getParameterValues("ctkmIds");
    	String ctkmId = "";
    	if(ctkmIds != null)
    	{
    		for(int i = 0; i < ctkmIds.length; i++)
    			ctkmId += ctkmIds[i] + ",";
    	}
    	if(ctkmId.length() > 0)
    		ctkmId = ctkmId.substring(0, ctkmId.length() -1);
    	
    	System.out.println("nhom ctkmIds: " + ctkmId);
    	nctkmBean.setCtkmIds(ctkmId);
		
 		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
 		if(action.equals("save"))
 		{
    		if (id == null )
    		{
    			if (!nctkmBean.save())
    			{
    		    	nctkmBean.setUserId(userId);
    		    	nctkmBean.createRs();
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("nctkmBean", nctkmBean);
    				
    				String nextJSP = "/AHF/pages/Center/NhomCTKMNew.jsp";
    				response.sendRedirect(nextJSP);
    			}
    			else
    			{
    				INhomctkhuyenmaiList obj = new NhomctkhuyenmaiList();
    				obj.setUserId(userId);
    				obj.createNhomctkmBean("");
    				
    				session.setAttribute("obj", obj);
    				session.setAttribute("userId", userId);
		    		
    				response.sendRedirect("/AHF/pages/Center/NhomCTKhuyenMai.jsp");	    	
    			}		
    		}
    		else
    		{
    			if (!nctkmBean.update())
    			{			
    		    	nctkmBean.setUserId(userId);
    		    	nctkmBean.init();
    		    	
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("nctkmBean", nctkmBean);
    				
    				String nextJSP = "/AHF/pages/Center/NhomCTKMUpdate.jsp";
    				response.sendRedirect(nextJSP);
    			}
    			else
    			{
    				INhomctkhuyenmaiList obj = new NhomctkhuyenmaiList();
    				obj.setUserId(userId);
    				obj.createNhomctkmBean("");
    				
    				session.setAttribute("obj", obj);
    				session.setAttribute("userId", userId);
		    		
    				response.sendRedirect("/AHF/pages/Center/NhomCTKhuyenMai.jsp");	   	
    			}
    		}
	    }
		else
		{
			nctkmBean.createRs();		
			
			session.setAttribute("nctkmBean", nctkmBean);
			session.setAttribute("userId", userId);
			
			String nextJSP;
			if (id == null)
			{
				nextJSP = "/AHF/pages/Center/NhomCTKMNew.jsp";
			}
			else
			{
				nextJSP = "/AHF/pages/Center/NhomCTKMUpdate.jsp";   						
			}
			response.sendRedirect(nextJSP);
		}		
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
