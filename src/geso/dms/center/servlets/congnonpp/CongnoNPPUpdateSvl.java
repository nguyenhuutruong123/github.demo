package geso.dms.center.servlets.congnonpp;

import geso.dms.center.beans.congnonpp.*;
import geso.dms.center.beans.congnonpp.imp.*;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/CongnoNPPUpdateSvl")
public class CongnoNPPUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
           
    public CongnoNPPUpdateSvl() {
        super();
        
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();		
		Utility util = new Utility();
	
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
    
		if (userId.length()==0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		String id = util.getId(querystring);
		String nextJSP = "";
		ICongnonpp cnnpp = new Congnonpp();
		cnnpp.setuserId(userId);
		cnnpp.setId(id);

		if(request.getQueryString().indexOf("display") >= 0 ) 
        {        	
        	nextJSP = "/AHF/pages/Center/CongnoNPPDisplay.jsp";
        }
        else
        {
        	nextJSP = "/AHF/pages/Center/CongnoNPPUpdate.jsp";
        }
		String Display = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("display"));
		ICongnonpp voucherBean = new Congnonpp(Display);	
		
		
		session.setAttribute("voucherBean", voucherBean);
	 	response.sendRedirect(nextJSP);	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
		HttpSession session = request.getSession();
		
		ICongnonpp voucherBean = new Congnonpp();
		
		Utility util = new Utility();
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		voucherBean.setuserId(userId);	  
	    
		String thoigian = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thoigian")));
		if(thoigian ==null)
			thoigian = "";
		voucherBean.setThoigian(thoigian);
			
		String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		if(diengiai ==null)
			diengiai = "";
		voucherBean.setDiengiai(diengiai);
		
		String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
		if(dvkdId ==null)
			dvkdId ="";
		voucherBean.setDvkdId(dvkdId);
		
		String kbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhId")));
		if(kbhId ==null)
			kbhId ="";
		voucherBean.setKbhId(kbhId);
		
		String httt = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("httt")));
		if(httt ==null)
			httt ="";
		voucherBean.setHinhthuctt(httt);
	    
	    String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
	    if(id == null)
	    	id = "0";
	     voucherBean.setId(id);
	     
	    String[] maNPP = request.getParameterValues("nppId");
		String[] tenNPP = request.getParameterValues("nppTen");
		String[] sotien = request.getParameterValues("sotien");		
		
		List<INhaphanphoi> ctnppList = new ArrayList<INhaphanphoi>();
		if(maNPP != null)
		{		
			INhaphanphoi ctnpp = null;
			String[] param = new String[4];
			int m = 0;
			System.out.println("manpp lenght : "+maNPP.length);
			while(m < maNPP.length)
			{
				if(maNPP[m] != "")
				{										
					param[0] = maNPP[m];
					param[1] = tenNPP[m];
					param[2] = sotien[m].replaceAll(",", "");						
					
					ctnpp = new Nhaphanphoi(param);					
					ctnppList.add(ctnpp);
				}
				m++;
			}	
		}
		
	    try
	    {	    
	    if(action.equals("save"))			    
	    {	    	
	    		if(!voucherBean.update(ctnppList))
	    		{	    			
    		    	session.setAttribute("voucherBean", voucherBean);
    		    	voucherBean.CreateCongnonppList();    		    	
    		    	String nextJSP = "/AHF/pages/Center/CongnoNPPUpdate.jsp";
    				response.sendRedirect(nextJSP);
	    		}
	    		else
	    		{
	    			ICongnonppList obj = new CongnonppList();
				    obj.init("");    
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);
						
					String nextJSP = "/AHF/pages/Center/CongnoNPP.jsp";
					response.sendRedirect(nextJSP);
	    		}			    	   
	    }
	    else
	    {
	    	session.setAttribute("voucherBean", voucherBean);
	    	String nextJSP = "/AHF/pages/Center/CongnoNPPUpdate.jsp";
			response.sendRedirect(nextJSP);
	    }
	    }catch(Exception ex){
	    	ex.printStackTrace();
	    }
	}

}
