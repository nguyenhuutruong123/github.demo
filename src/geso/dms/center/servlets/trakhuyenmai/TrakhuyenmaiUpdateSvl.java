package geso.dms.center.servlets.trakhuyenmai;

import geso.dms.center.beans.dieukienkhuyenmai.ISanpham;
import geso.dms.center.beans.dieukienkhuyenmai.imp.Sanpham;
import geso.dms.center.beans.trakhuyenmai.*;
import geso.dms.center.beans.trakhuyenmai.imp.*;
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

public class TrakhuyenmaiUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	//ITrakhuyenmai trakmBean;
	//PrintWriter out; 
	//dbutils db;
	
    public TrakhuyenmaiUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		
		ITrakhuyenmai trakmBean;
		PrintWriter out = response.getWriter(); 

		Utility util = new Utility();
		
		//--- check user
	    String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		if (view == null) {
			view = "";
		}
		String param = "";
		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "TrakhuyenmaiSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
		 //--- check user
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    	    
	    String id = util.getId(querystring);

	    trakmBean = new Trakhuyenmai(id);
	    trakmBean.setUserId(userId);
        trakmBean.init();
        
        session.setAttribute("trakmBean", trakmBean);
        String nextJSP = "/AHF/pages/Center/TraKhuyenMaiUpdate.jsp";
        String action = util.getAction(querystring);
		if (action.equals("display")) {
			nextJSP = "/AHF/pages/Center/TraKhuyenMaiDisplay.jsp";
		}
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		ITrakhuyenmai trakmBean;
		PrintWriter out = response.getWriter(); 
		Utility util = new Utility();
		
		//--- check user
	    String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		if (view == null) {
			view = "";
		}
		String param = "";
		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "TrakhuyenmaiSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
		 //--- check user
		
	    String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));	
	    if(id == null){  	
	    	trakmBean = new Trakhuyenmai("");
	    }else{
	    	trakmBean = new Trakhuyenmai(id);
	    }
	    	    
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		trakmBean.setUserId(userId);	       
    			
		String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		if (diengiai == null)
			diengiai = "";
		trakmBean.setDiengiai(diengiai);
		
		String type = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("type")));
		if (type == null)
			type = "";
		trakmBean.setType(type);
				
		String tongtien = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tongtien")));
		if (tongtien == null)
			tongtien = "";
		trakmBean.setTongtien(tongtien);
		
		String chietkhau = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chietkhau")));
		if (chietkhau == null)
			chietkhau = "";
		trakmBean.setChietkhau(chietkhau);
		
		String tongluong = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tongluong")));
		if (tongluong == null)
			tongluong = "";
		trakmBean.setTongluong(tongluong);
		
		String hinhthuc = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("hinhthuc")));
		if (hinhthuc == null)
			hinhthuc = "";
		trakmBean.setHinhthuc(hinhthuc);
		
		String nhomspId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhomsp")));
		if (nhomspId == null)
			nhomspId = "";
		trakmBean.setNhomspId(nhomspId);
		
		String ngaysua = getDateTime();
    	trakmBean.setNgaysua(ngaysua);
    	
    	String[] masp = request.getParameterValues("masp");
		String[] tensp = request.getParameterValues("tensp");
		//String[] dongia = request.getParameterValues("dongia");
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
	    				if(soluong[i].trim().length() > 0){
	    					if( soluong[i].trim().length()<=0)  soluong[i]="0";
	    					System.out.println("soluong[i]= " + soluong[i]);
	    					sp_nhomSpIds.put(masp[i], Integer.parseInt(soluong[i]));
	    				}
    				}
    			}
    		}
		}
		else
		{
			if(masp != null)
			{
				for(int i = 0; i < masp.length; i++)
				{
					if(masp[i] != "")
					{
						if( soluong[i].trim().length()<=0)  soluong[i]="0";
						System.out.println("soluong[i]= " + soluong[i]);
						//Sanpham sp = new Sanpham("", masp[i], tensp[i], soluong[i], dongia[i]);
						Sanpham sp = new Sanpham("", masp[i], tensp[i], soluong[i], "");
						spSudunglist.add(sp);
					}
				}
			}
		}
		
		trakmBean.setSp_nhomspIds(sp_nhomSpIds);
		trakmBean.setSpSudungList(spSudunglist);
	
 		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
 		if(action.equals("save"))
 		{
    		if (id == null || id.trim().length()==0)
    		{
    			if (!trakmBean.CreateTrakm(masp, soluong)){
    		    	trakmBean.setUserId(userId);
    		    	trakmBean.createRS();
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("trakmBean", trakmBean);
    				
    				String nextJSP = "/AHF/pages/Center/TraKhuyenMaiNew.jsp";
    				response.sendRedirect(nextJSP);
    			}
    			else{
    				ITrakhuyenmaiList obj = new TrakhuyenmaiList();
    				obj.setUserId(userId);
    				obj.init("");
    				
				
    				session.setAttribute("obj", obj);
    				session.setAttribute("userId", userId);
		    		
    				response.sendRedirect("/AHF/pages/Center/TraKhuyenMai.jsp");	    	
    			}		
    		}else{
    			if (!(trakmBean.UpdateTrakm(masp, soluong))){			
    		    	trakmBean.setUserId(userId);
    		    	trakmBean.createRS();
    		    	
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("trakmBean", trakmBean);
    				
    				String nextJSP = "/AHF/pages/Center/TraKhuyenMaiUpdate.jsp";
    				response.sendRedirect(nextJSP);
    			}
    			else{
    				ITrakhuyenmaiList obj = new TrakhuyenmaiList();
    				obj.setUserId(userId);
    				obj.init("");
    				
				
    				session.setAttribute("obj", obj);
    				session.setAttribute("userId", userId);
		    		
    				response.sendRedirect("/AHF/pages/Center/TraKhuyenMai.jsp");    	
    			}
    		}
	    }
		else
		{
			trakmBean.createRS();		
			if(id != null && nhomspId.length() == 0)
				trakmBean.createTrakmSpList();
			session.setAttribute("userId", userId);
			session.setAttribute("trakmBean", trakmBean);
			String nextJSP;
			if (id == null || id.trim().length()==0){
				nextJSP = "/AHF/pages/Center/TraKhuyenMaiNew.jsp";
			}
			else{
				nextJSP = "/AHF/pages/Center/TraKhuyenMaiUpdate.jsp";					
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
