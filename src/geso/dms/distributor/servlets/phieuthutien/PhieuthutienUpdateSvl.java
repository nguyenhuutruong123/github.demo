package geso.dms.distributor.servlets.phieuthutien;

import geso.dms.distributor.beans.phieuthutien.IPhieuthutien;
import geso.dms.distributor.beans.phieuthutien.IPhieuthutienList;
import geso.dms.distributor.beans.phieuthutien.imp.Phieuthutien;
import geso.dms.distributor.beans.phieuthutien.imp.PhieuthutienList;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

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

public class PhieuthutienUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out; 
       
    public PhieuthutienUpdateSvl() 
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
	    	    
	    String id = util.getId(querystring);  	

	    IPhieuthutien pttBean = new Phieuthutien(id);
	    pttBean.setUserId(userId);
	    pttBean.setClaim(true);
	    pttBean.initUpdate();
        
        
        session.setAttribute("pttBean", pttBean);
        
        String nextJSP = "/AHF/pages/Distributor/PhieuThuTienUpdate_2.jsp";
        
        if(querystring.indexOf("display") >= 0 ) 
        	nextJSP = "/AHF/pages/Distributor/PhieuThuTienDisplay.jsp";
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		this.out = response.getWriter();
		dbutils db = new dbutils();
		Utility util = new Utility();
	    String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
		IPhieuthutien pttBean;
		
	    if(id == null){  	
	    	pttBean = new Phieuthutien("");
	    }else{
	    	pttBean = new Phieuthutien(id);
	    }
	    
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		pttBean.setUserId(userId);
		
		String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
		if (nppId == null)
			nppId = "";
		pttBean.setNppId(nppId);
	    
    	String nvgnId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nvgnId")));
		if (nvgnId == null)
			nvgnId = "";				
		System.out.println("NVGN:" + nvgnId);
    	pttBean.setNvgnId(nvgnId);
    	  	
    	String pxkId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("pxkId")));
		if (pxkId == null)
			pxkId = "";				
    	pttBean.setPxkId(pxkId);

    	String[] khIds = request.getParameterValues("khIds");
		
    	pttBean.setKhIds(khIds);

    	String khId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khId")));
		if (khId == null)
			khId = "";				
		
    	pttBean.setKhId(khId);

    	String tongsotien = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tongsotien")));
		if (tongsotien == null)
			tongsotien = "";				
    	pttBean.setTongsotien(tongsotien);
    	
    	String ngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngay")));
		if (ngay == null || ngay == "")
			ngay = this.getDateTime();				
    	pttBean.setNgay(ngay);
    	
    	String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		if (diengiai == null)
			diengiai = "PTT-[" + ngay + "]-NVGN:" + nvgnId;				
    	pttBean.setDiengiai(diengiai);

    	String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
    	if (tungay == null) tungay = "";
    	pttBean.setTungay(tungay);

    	String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
    	if (denngay == null) denngay = "";
    	pttBean.setDenngay(denngay);
   	
    	String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
    	
    	if(action.equals("save"))
		{
			if ( id == null){
				if (!(pttBean.CreatePtt(request))){	
					pttBean.initNew();
					session.setAttribute("pttBean", pttBean);			
					String nextJSP = "/AHF/pages/Distributor/PhieuThuTienNew_2.jsp";
					response.sendRedirect(nextJSP);
				}else{
					IPhieuthutienList obj = new PhieuthutienList();
					obj.setUserId(userId);
					obj.init("");
					session.setAttribute("obj", obj);
						
					String nextJSP = "/AHF/pages/Distributor/PhieuThuTien.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
				
			}else{
				if (!(pttBean.UpdatePtt(request)))
				{
					pttBean.initUpdate();
					session.setAttribute("pttBean", pttBean);
					String nextJSP = "/AHF/pages/Distributor/PhieuThuTienUpdate_2.jsp";
					response.sendRedirect(nextJSP);
				}
				else{
					IPhieuthutienList obj = new PhieuthutienList();
					obj.setUserId(userId);
					obj.init("");
					session.setAttribute("obj", obj);
						
					String nextJSP = "/AHF/pages/Distributor/PhieuThuTien.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
			}
		}else{
				String nextJSP = "";

				if(action.equals("claim")){
					pttBean.setClaim(true);
					if(khIds != null){
						if (id == null){
							nextJSP = "/AHF/pages/Distributor/PhieuThuTienNew_2.jsp";
							pttBean.initNew();
						}else{
							nextJSP = "/AHF/pages/Distributor/PhieuThuTienUpdate_2.jsp";
							pttBean.initUpdate();						
						}
					}else{
						if (id == null){
							nextJSP = "/AHF/pages/Distributor/PhieuThuTienNew_1.jsp";
							pttBean.initNew();
							pttBean.setMessage("Vui long chon it nhat mot khach hang");
						}else{
							nextJSP = "/AHF/pages/Distributor/PhieuThuTienUpdate_2.jsp";
							pttBean.initUpdate();						
						}
								
					}
					
				}else{
					if(action.equals("noclaim")){
						pttBean.setClaim(false);
						if (id == null){
							nextJSP = "/AHF/pages/Distributor/PhieuThuTienNew_1.jsp";
							pttBean.initNew();
						}else{
							nextJSP = "/AHF/pages/Distributor/PhieuThuTienUpdate_1.jsp";
							pttBean.initUpdate();						
						}					
					}else{
						pttBean.setClaim(false);
						if (id == null){
							nextJSP = "/AHF/pages/Distributor/PhieuThuTienNew_1.jsp";
							pttBean.initNew();
						}else{
							nextJSP = "/AHF/pages/Distributor/PhieuThuTienUpdate_1.jsp";
							pttBean.initUpdate();
						}
							
					}

				}
							
			session.setAttribute("pttBean", pttBean);
			session.setAttribute("nvgnId", nvgnId);
				
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
