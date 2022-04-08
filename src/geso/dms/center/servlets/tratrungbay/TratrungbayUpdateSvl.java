package geso.dms.center.servlets.tratrungbay;

import geso.dms.center.beans.nhomsptrungbay.ISanpham;
import geso.dms.center.beans.nhomsptrungbay.imp.Sanpham;
import geso.dms.center.beans.tratrungbay.*;
import geso.dms.center.beans.tratrungbay.imp.*;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
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

public class TratrungbayUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out; 

    public TratrungbayUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		
		ITratrungbay tratbBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
		String param = "";
//		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "TratrungbaySvl", param, Utility.SUA ))
		{
			Utility.RedireactLogin(session, request, response);
		}
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    	    
	    String id = util.getId(querystring);

	    
	    
	    tratbBean = new Tratrungbay(id);
	    tratbBean.setUserId(userId);
        tratbBean.init();
        
        session.setAttribute("tratbBean", tratbBean);
        String nextJSP = "/AHF/pages/Center/TraTrungBayUpdate.jsp";
        
        String action = util.getAction(querystring);
        if(action.equals("display")) {
        	nextJSP = "/AHF/pages/Center/TraTrungBayDisplay.jsp";
        }
        
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		dbutils db;
		ITratrungbay tratbBean;
		this.out = response.getWriter();
		Utility util = new Utility();
	    String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));	
	    if(id == null){  	
	    	tratbBean = new Tratrungbay("");
	    }else{
	    	tratbBean = new Tratrungbay(id);
	    }
	    	    
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		tratbBean.setUserId(userId);	       
    			
		String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		if (diengiai == null)
			diengiai = "";
		tratbBean.setDiengiai(diengiai);
		
		String type = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("type")));
		if (type == null)
			type = "";
		tratbBean.setType(type);
				
		String tongtien = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tongtien")));
		if (tongtien == null)
			tongtien = "";
		tratbBean.setTongtien(tongtien);
		
		String tongluong = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tongluong")));
		if (tongluong == null)
			tongluong = "";
		tratbBean.setTongluong(tongluong);
		
		String hinhthuc = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("hinhthuc")));
		if (hinhthuc == null)
			hinhthuc = "";
		tratbBean.setHinhthuc(hinhthuc);
		
		String nhomspId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhomsp")));
		if (nhomspId == null)
			nhomspId = "";
		tratbBean.setNhomspId(nhomspId);
		
		String ngaysua = getDateTime();
    	tratbBean.setNgaysua(ngaysua);
    	
    	String[] masp = request.getParameterValues("masp");
		String[] tensp = request.getParameterValues("tensp");
		String[] dongia = request.getParameterValues("dongia");
		String[] soluong = request.getParameterValues("soluong");
				
		Hashtable<String, Integer> sp_nhomSpIds = new Hashtable<String, Integer>();
		List<ISanpham> spSudunglist = new ArrayList<ISanpham>();
		
		if(nhomspId.length() > 0 )
		{
    		if(masp != null)
    		{
    			for(int i = 0; i < masp.length; i++)
    			{
    				if(soluong != null)
    				{
	    				if(soluong[i].length() > 0)
	    					sp_nhomSpIds.put(masp[i], Integer.parseInt(soluong[i]));
    				}
    			}
    		}
		}

		if(nhomspId.length() == 0)
		{
			if(masp != null)
    		{
    			for(int i = 0; i < masp.length; i++)
    			{
    				if(masp[i].length() > 0)
    				{
						Sanpham sp = new Sanpham("", masp[i], tensp[i], soluong[i], dongia[i]);
						spSudunglist.add(sp);
    				}
    			}
    		}
		}

		tratbBean.setSp_nhomspIds(sp_nhomSpIds);
		tratbBean.setSpSudungList(spSudunglist);
	
 		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
 		if(action.equals("save"))
 		{
    		if (id == null )
    		{
    			if (!tratbBean.CreateTratb(masp, dongia, soluong)){
    		    	tratbBean.setUserId(userId);
    		    	tratbBean.createRS();
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("tratbBean", tratbBean);
    				
    				String nextJSP = "/AHF/pages/Center/TraTrungBayNew.jsp";
    				response.sendRedirect(nextJSP);
    			}
    			else{
    				ITratrungbayList obj = new TratrungbayList();
    				obj.setUserId(userId);
    				obj.init("");
				
    				session.setAttribute("obj", obj);
    				session.setAttribute("userId", userId);
		    		
    				response.sendRedirect("/AHF/pages/Center/TraTrungBay.jsp");	    	
    			}		
    		}else{
    			if (!(tratbBean.UpdateTratb(masp, dongia, soluong))){			
    		    	tratbBean.setUserId(userId);
    		    	tratbBean.createRS();
    		    	
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("tratbBean", tratbBean);
    				
    				String nextJSP = "/AHF/pages/Center/TraTrungBayUpdate.jsp";
    				response.sendRedirect(nextJSP);
    			}
    			else{
    				ITratrungbayList obj = new TratrungbayList();
    				obj.setUserId(userId);
    				obj.init("");
				
    				session.setAttribute("obj", obj);
    				session.setAttribute("userId", userId);
		    		
    				response.sendRedirect("/AHF/pages/Center/TraTrungBay.jsp");	    	
    			}
    		}
	    }
		else
		{
			tratbBean.createRS();		
			if(id != null && nhomspId.length() == 0)
				tratbBean.createTratbSpList();
			session.setAttribute("userId", userId);
			session.setAttribute("tratbBean", tratbBean);
			String nextJSP;
			if (id == null){
				nextJSP = "/AHF/pages/Center/TraTrungBayNew.jsp";
			}
			else{
				nextJSP = "/AHF/pages/Center/TraTrungBayUpdate.jsp";					
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
