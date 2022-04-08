package geso.dms.distributor.servlets.phieuthanhtoan;

import geso.dms.distributor.beans.phieuthanhtoan.IPhieuthanhtoan;
import geso.dms.distributor.beans.phieuthanhtoan.IPhieuthanhtoanList;
import geso.dms.distributor.beans.phieuthanhtoan.imp.Phieuthanhtoan;
import geso.dms.distributor.beans.phieuthanhtoan.imp.PhieuthanhtoanList;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

public class PhieuthanhtoanUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	IPhieuthanhtoan obj;
	dbutils db;
	PrintWriter out;  

    public PhieuthanhtoanUpdateSvl()
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
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	 
	    String action = util.getAction(querystring);
	    out.println(action);	
	    
	    String id = util.getId(querystring);  	
        System.out.println("action:"+ action +" id :"+ id);
	    
        
	    obj = new Phieuthanhtoan(id);
	    obj.setUserId(userId);
	    obj.setId(id);
        obj.init();
       
        if(action.equals("chotphieu"))
        {
        	chot(id);
        	IPhieuthanhtoanList obj = new PhieuthanhtoanList();
     	    obj.setUserId(userId);
     	    
     	    obj.init(" ");
     	    
     		session.setAttribute("obj", obj);
     				
     		String nextJSP = "/AHF/pages/Distributor/PhieuThanhToan.jsp";
     		response.sendRedirect(nextJSP);
        	
        }
        else if(action.equals("delete"))
        {
	         delete(id);
	         IPhieuthanhtoanList obj = new PhieuthanhtoanList();
	  	     obj.setUserId(userId);
	  	    
	  	     obj.init(" ");
	  	    
	  		 session.setAttribute("obj", obj);
	  				
	  		String nextJSP = "/AHF/pages/Distributor/PhieuThanhToan.jsp";
	  		response.sendRedirect(nextJSP);
        }
        else if(action.equals("display"))
        {
        	session.setAttribute("obj", obj);
 	        String nextJSP = "/AHF/pages/Distributor/PhieuThanhToanDisplay.jsp";
 	        response.sendRedirect(nextJSP);
        }
        else
        {
	        session.setAttribute("obj", obj);
	        String nextJSP = "/AHF/pages/Distributor/PhieuThanhToanUpdate.jsp";
	        response.sendRedirect(nextJSP);
        }
	}
   void delete(String id)
   {
	   db = new dbutils();
	 String  sql ="select * from phieuthanhtoan_donhang where phieuthanhtoan_fk ='"+ id +"'";
		  ResultSet rs = db.get(sql);
		  try {
			while(rs.next())
			  {
				  String sotien = rs.getString("dathanhtoan");
				  String donhang =rs.getString("donhang_fk");
				  sql ="update donhang set dathanhtoan = dathanhtoan -'"+ sotien +"' where pk_seq ='"+ donhang +"'";
				  db.update(sql);
			  }
	    	} catch(Exception e) {
			e.printStackTrace();
		   }
	       sql ="delete from phieuthanhtoan_donhang where phieuthanhtoan_fk ='"+ id +"'";
	       db.update(sql);
	       sql ="delete from phieuthanhtoan where pk_seq ='"+ id +"'";
	       db.update(sql);
   }
   void chot(String id)
   {db = new dbutils();
	   String sql ="update phieuthanhtoan set trangthai ='1' where pk_seq ='"+ id +"'";
	   db.update(sql);
   }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		/*
		this.out = response.getWriter();
		db = new dbutils();
		*/
		Utility util = new Utility();
		String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
    	if(id ==null)
    		id ="";
    	if(id.length()>0)
	      obj = new Phieuthanhtoan(id);
    	else
    		obj = new Phieuthanhtoan();
	    
    	obj.setId(id);
    	
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		obj.setUserId(userId);
		
		String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
		System.out.println("nha pp:"+nppId);
		
		if (nppId == null)
			nppId = "";
		obj.setNppId(nppId);
	    
    	String ddkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdId")));
		if (ddkdId == null)
			ddkdId = "";				
    	obj.setddkdId(ddkdId);
    	
    	String tuyen= util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tuyen")));
    	if (tuyen == null)
    		tuyen = "";    	
    	obj.setTuyen(tuyen);
    
    	String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
		if (tungay == null)
			tungay = "";				
    	obj.setTungay(tungay);
    	
    	String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
		if (denngay == null)
			denngay = "";				
    	obj.setDenngay(denngay);
    	
    	String sotien = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sotien")));
		if (sotien == null)
			sotien = "0";				
    	obj.setSotien(sotien);
    	
    	String hinhthuc = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("hinhthuc")));
    	if(hinhthuc ==null)
    		hinhthuc ="1";
    	obj.setHinhthuc(hinhthuc);
    	
    	String ngaytt = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaythanhtoan")));
		if (ngaytt == null || ngaytt == "" )
			ngaytt = this.getDateTime();				
    	obj.setNgaythanhtoan(ngaytt);
    	
    	String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
    	if(diengiai == null)
    	   diengiai ="";
    	obj.setDiengiai(diengiai);
    
    	String nvgn = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nvgn")));
    	if(nvgn == null)
    	   nvgn ="";
    	obj.setNvgn(nvgn);
    
    	String[] dhids = request.getParameterValues("dhid");
    	obj.setdhIds(dhids);
    	if(dhids!=null){
    	System.out.println("tong so chuoi1:"+dhids.length);
    	for(int i=0 ; i<dhids.length ;i++)
    	{
    		System.out.println("tong so chuoi1:"+dhids[i]);
    	}
    	}
     	String[] thanhtoan= request.getParameterValues("thanhtoan1");
     	if(thanhtoan!=null){
        	System.out.println("tong so chuoi2:"+thanhtoan.length);
        	for(int i=0 ; i<thanhtoan.length ;i++)
        	{
        		System.out.println("thanhtoan 1:"+thanhtoan[i]);
        	}
        	}
    	obj.setThanhtoan(thanhtoan);
    	
    	String action =  geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
    	
    	obj.init();
    	if(id.length()>0)
    	{    
    		 if(action.equals("submit1"))
    		 {
    			  session.setAttribute("obj", obj);	
		    	  String nextJSP = "/AHF/pages/Distributor/PhieuThanhToanUpdate.jsp";
				  response.sendRedirect(nextJSP);
    		 }
    		 else 
    		 {if(!obj.Update())
    		  {
	    		  session.setAttribute("obj", obj);	
		    	  String nextJSP = "/AHF/pages/Distributor/PhieuThanhToanUpdate.jsp";
				  response.sendRedirect(nextJSP);
    		  }
    		  else
    		  {
    			  IPhieuthanhtoanList obj = new PhieuthanhtoanList();
	    		    obj.setUserId(userId);
	    		    
	    		    obj.init(" ");
	    		    
	    			session.setAttribute("obj", obj);
	    					
	    			String nextJSP = "/AHF/pages/Distributor/PhieuThanhToan.jsp";
	    			response.sendRedirect(nextJSP);
    		  }}
    	}
    	else
    	{
    		if(action.equals("submit"))
   		 {
   			  session.setAttribute("obj", obj);	
		    	  String nextJSP = "/AHF/pages/Distributor/PhieuThanhToanNew.jsp";
				  response.sendRedirect(nextJSP);
   		 }
   		 else 
   		 {
    	if(action.equals("save"))
    	{
		    	if(!obj.save())
		    	{ session.setAttribute("obj", obj);	
		    	  String nextJSP = "/AHF/pages/Distributor/PhieuThanhToanNew.jsp";
				  response.sendRedirect(nextJSP);
		    	}
		    	else
		    	{
		    		IPhieuthanhtoanList obj = new PhieuthanhtoanList();
		    		    obj.setUserId(userId);
		    		    
		    		    obj.init(" ");
		    		    
		    			session.setAttribute("obj", obj);
		    					
		    			String nextJSP = "/AHF/pages/Distributor/PhieuThanhToan.jsp";
		    			response.sendRedirect(nextJSP);
		    	}
    	  }
    	else
    	{  	
    		  session.setAttribute("obj", obj);	
	    	  String nextJSP = "/AHF/pages/Distributor/PhieuThanhToanNew.jsp";
			  response.sendRedirect(nextJSP);
    	}
   		 }
    	}
    		
    /*	String ngayhoadon = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngayhoadon"));
		if (ngayhoadon == null)
			ngayhoadon = this.getDateTime();				
    	obj.setNgaydonhang(ngayhoadon);
    	
    	String sodudau = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sodudau"));
		if (sodudau == null)
			sodudau = "";				
    	obj.setSodudau(sodudau);
    	
    	String tongthanhtoan = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thanhtoan"));
		if (tongthanhtoan == null)
			tongthanhtoan = "";				
    	obj.setTongthanhtoan(tongthanhtoan);
    	
    	String flag = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("option"));
		if (flag == null)
			flag = "";		//hien tat ca cac don hang truoc ngayhoadon		
    	obj.setNgaydhSelected(flag);
    	
    	String[] pttIds = request.getParameterValues("pttIds");
    	obj.setPhieuthutienIds(pttIds);
		String[] dhIds = request.getParameterValues("dhIds");
		obj.setDonhangIds(dhIds);
		
		String[] dhTenList = request.getParameterValues("dhTenList");
		obj.setDonhangListIds(dhTenList);
		String[] dh_ttIds = request.getParameterValues("tienthanhtoan");
		obj.setDh_ThanhtoanIds(dh_ttIds);
		String[] dh_clIds = request.getParameterValues("tienconlai");
		obj.setDh_ConlaiIds(dh_clIds);
		
    	String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
    	
    	/*if(action.equals("save"))
		{
			if ( id == null){
				if (!(obj.CreatePtt())){	
					obj.createRS();
					session.setAttribute("obj", obj);			
					String nextJSP = "/AHF/pages/Distributor/PhieuThanhToanNew.jsp";
					response.sendRedirect(nextJSP);
				}else{
					IPhieuthanhtoanList obj = new PhieuthanhtoanList();
					obj.setUserId(userId);
					obj.init("");
					session.setAttribute("obj", obj);
						
					String nextJSP = "/AHF/pages/Distributor/PhieuThanhToan.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
				
			}else{
				if (!(obj.UpdatePtt()))
				{
					obj.init();
					session.setAttribute("obj", obj);
					String nextJSP = "/AHF/pages/Distributor/PhieuThanhToanUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else{
					IPhieuthanhtoanList obj = new PhieuthanhtoanList();
					obj.setUserId(userId);
					obj.init("");
					session.setAttribute("obj", obj);
						
					String nextJSP = "/AHF/pages/Distributor/PhieuThuTien.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
			}
		}
		else
		{
			obj.createRS();
			session.setAttribute("obj", obj);
			
			String nextJSP;
			if (id == null){			
				nextJSP = "/AHF/pages/Distributor/PhieuThanhToanNew.jsp";
			}else{
				nextJSP = "/AHF/pages/Distributor/PhieuThanhToanUpdate.jsp";   						
			}
			response.sendRedirect(nextJSP);		
		}
		*/
	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	

}
