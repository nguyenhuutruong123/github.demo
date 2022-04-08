package geso.dms.distributor.servlets.mucchietkhau;

import geso.dms.distributor.beans.mucchietkhau.*;
import geso.dms.distributor.beans.mucchietkhau.imp.*;
import geso.dms.distributor.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class MucchietkhauUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	
    public MucchietkhauUpdateSvl() 
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
			Utility util = new Utility();			
	    	String querystring = request.getQueryString();
		    userId = util.getUserId(querystring);	  
		    
		    if (userId.length() == 0)
		    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));		    	    
		    String id = util.getId(querystring); 	
	
		    IMucchietkhau mckBean = new Mucchietkhau(id);
		    mckBean.setUserId(userId);
	        mckBean.init();
	        session.setAttribute("listKHAndCK", mckBean.getListKHCK(id));
	        session.setAttribute("mckBean", mckBean);
	        String nextJSP = "/AHF/pages/Distributor/MucChietKhauUpdate.jsp";
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
		
		IMucchietkhau mckBean;		
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    Utility util = new Utility();
	    String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));	
	    if(id == null){  	
	    	mckBean = new Mucchietkhau("");
	    }else{
	    	mckBean = new Mucchietkhau(id);
	    }
	    	    
		userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		mckBean.setUserId(userId);
	    
    	String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		if (diengiai == null)
			diengiai = "";				
    	mckBean.setDiengiai(diengiai);
    	
    	String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
		if (nppId == null)
			nppId = "";
		mckBean.setNppId(nppId);
    	
		String chietkhau = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chietkhau")));
		if (chietkhau == null)
			chietkhau = "";
		mckBean.setChietkhau(chietkhau);
		
		String ddkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdTen")));
		if (ddkdId == null)
			ddkdId = "";
		mckBean.setDdkdId(ddkdId);
		
		String[] khIds = request.getParameterValues("khIds");
		mckBean.setKh_MckIds(khIds);
		
		String ngaysua = getDateTime();
    	mckBean.setNgaysua(ngaysua);
		
		boolean error = false;
				
		if (diengiai.trim().length()== 0){
			mckBean.setMessage("Vui Long Nhap Dien Giai Muc Chiet Khau");
			error = true;
		}

		if (chietkhau.trim().length()== 0){
			mckBean.setMessage("Vui Long Nhap % Chiet Khau");
			error = true;
		}
		
		List<String> list = new ArrayList<String>();
		list = (List<String>)session.getAttribute("listKHAndCK");
		
 		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (!error){
	    	if(action.equals("save"))
	    	{
	    		if ( id == null){
	    			if (!(mckBean.CreateMck(khIds))){	
	    				mckBean.createRS();
	    				session.setAttribute("mckBean", mckBean);			
	    				String nextJSP = "/AHF/pages/Distributor/MucChietKhauNew.jsp";
	    				response.sendRedirect(nextJSP);
	    			}else{
	    				IMucchietkhauList obj = new MucchietkhauList();
	    				obj.setUserId(userId);
	    				obj.init("");
	    				session.setAttribute("obj", obj);
						
	    				String nextJSP = "/AHF/pages/Distributor/MucChietKhau.jsp";
	    				response.sendRedirect(nextJSP);			    			    									
	    			}
				
	    		}else{
	    			if (!(mckBean.UpdateMck(khIds,list)))
	    			{
	    				mckBean.init();
	    				session.setAttribute("mckBean", mckBean);
	    				String nextJSP = "/AHF/pages/Distributor/MucChietKhauUpdate.jsp";
	    				response.sendRedirect(nextJSP);
	    			}
	    			else{
	    				IMucchietkhauList obj = new MucchietkhauList();
	    				obj.setUserId(userId);
	    				obj.init("");
	    				session.setAttribute("obj", obj);
						
	    				String nextJSP = "/AHF/pages/Distributor/MucChietKhau.jsp";
	    				response.sendRedirect(nextJSP);			    			    									
	    			}
	    		}
	    	}else	{
	    		mckBean.createRS();
	    		session.setAttribute("mckBean", mckBean);
			
	    		String nextJSP;
	    		if (id == null){			
	    			nextJSP = "/AHF/pages/Distributor/MucChietKhauNew.jsp";
	    		}else{
	    			nextJSP = "/AHF/pages/Distributor/MucChietKhauUpdate.jsp";   						
	    		}
	    		response.sendRedirect(nextJSP);	    	
	    	}
	    }
	    else{
    		mckBean.createRS();
    		session.setAttribute("mckBean", mckBean);
		
    		String nextJSP;
    		if (id == null){			
    			nextJSP = "/AHF/pages/Distributor/MucChietKhauNew.jsp";
    		}else{
    			nextJSP = "/AHF/pages/Distributor/MucChietKhauUpdate.jsp";   						
    		}
    		response.sendRedirect(nextJSP);	  	    		
	   	}
		}
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
