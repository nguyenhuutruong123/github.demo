package geso.dms.center.servlets.nhomsptrungbay;

import geso.dms.center.beans.nhomsptrungbay.*;
import geso.dms.center.beans.nhomsptrungbay.imp.*;
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

public class NhomsptrungbayUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
	
    public NhomsptrungbayUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		INhomsptrungbay nsptbBean;
		
		dbutils db;
		
		HttpSession session = request.getSession();
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    	    
	    String id = util.getId(querystring); 
	    String action = util.getAction(querystring);

	    nsptbBean = new Nhomsptrungbay(id);
	    nsptbBean.setUserId(userId);
        nsptbBean.init();
        
        session.setAttribute("nsptbBean", nsptbBean);
        String nextJSP = "/AHF/pages/Center/NhomSpTrungBayUpdate.jsp";
        if(action.equals("display"))
        	nextJSP = "/AHF/pages/Center/NhomSpTrungBayDisplay.jsp";
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		INhomsptrungbay nsptbBean;
		
		dbutils db;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		this.out = response.getWriter();
		
		Utility util = new Utility();
		
	    String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));	
	    if(id == null){  	
	    	nsptbBean = new Nhomsptrungbay("");
	    }else{
	    	nsptbBean = new Nhomsptrungbay(id);
	    }
	    	    
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		nsptbBean.setUserId(userId);	       
    			
		String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		if (diengiai == null)
			diengiai = "";
		nsptbBean.setDiengiai(diengiai);
		
		String hinhthuc = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("option")));
		if (hinhthuc == null)
			hinhthuc = "";
		nsptbBean.setHinhthuc(hinhthuc);
		
		String tongluong = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tongluong")));
		if (tongluong == null)
			tongluong = "";
		nsptbBean.setTongluong(tongluong);
		
		String tongtien = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tongtien")));
		if (tongtien == null)
			tongtien = "";
		nsptbBean.setTongtien(tongtien);
		
		String type = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("type")));
		if (type == null)
			type = "";
		nsptbBean.setType(type);
		
		String nhomspId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhomsp")));
		if (nhomspId == null)
			nhomspId = "";
		nsptbBean.setNhomspId(nhomspId);
		
		String ngaysua = getDateTime();
    	nsptbBean.setNgaysua(ngaysua);
    	
    	String[] masp = request.getParameterValues("masp");
		String[] tensp = request.getParameterValues("tensp");
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

		if(nhomspId.length() == 0 && type.length() > 0)
		{
			if(masp != null)
    		{
    			for(int i = 0; i < masp.length; i++)
    			{
    				if(masp[i].length() > 0)
    				{
						Sanpham sp = new Sanpham("", masp[i], tensp[i], soluong[i], "");
						spSudunglist.add(sp);
    				}
    			}
    		}
		}

		nsptbBean.setSp_nhomspIds(sp_nhomSpIds);
		nsptbBean.setSpSudungList(spSudunglist);
	
 		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
 		if(action.equals("save"))
 		{
    		if (id == null )
    		{
    			if (!nsptbBean.CreateNsptb(masp, soluong)){
    		    	nsptbBean.setUserId(userId);
    		    	nsptbBean.createRS();
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("nsptbBean", nsptbBean);
    				
    				String nextJSP = "/AHF/pages/Center/NhomSpTrungBayNew.jsp";
    				response.sendRedirect(nextJSP);
    			}
    			else{
    				INhomsptrungbayList obj = new NhomsptrungbayList();
    				obj.setUserId(userId);
    				obj.init("");
				
    				session.setAttribute("obj", obj);
    				session.setAttribute("userId", userId);
		    		
    				response.sendRedirect("/AHF/pages/Center/NhomSpTrungBay.jsp");	    	
    			}		
    		}else{
    			if (!(nsptbBean.UpdateNsptb(masp, soluong))){			
    		    	nsptbBean.setUserId(userId);
    		    	nsptbBean.createRS();
    		    	
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("nsptbBean", nsptbBean);
    				
    				String nextJSP = "/AHF/pages/Center/NhomSpTrungBayUpdate.jsp";
    				response.sendRedirect(nextJSP);
    			}
    			else{
    				INhomsptrungbayList obj = new NhomsptrungbayList();
    				obj.setUserId(userId);
    				obj.init("");
				
    				session.setAttribute("obj", obj);
    				session.setAttribute("userId", userId);
		    		
    				response.sendRedirect("/AHF/pages/Center/NhomSpTrungBay.jsp");  	
    			}
    		}
	    }
		else
		{
			nsptbBean.createRS();		
			if(id != null && nhomspId.length() == 0)
				nsptbBean.createNsptbSpList();
			session.setAttribute("userId", userId);
			session.setAttribute("nsptbBean", nsptbBean);
			String nextJSP;
			if (id == null){
				nextJSP = "/AHF/pages/Center/NhomSpTrungBayNew.jsp";
			}
			else{
				nextJSP = "/AHF/pages/Center/NhomSpTrungBayUpdate.jsp";   						
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
