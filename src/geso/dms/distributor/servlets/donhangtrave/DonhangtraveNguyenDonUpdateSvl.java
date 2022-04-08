package geso.dms.distributor.servlets.donhangtrave;

import geso.dms.distributor.beans.donhangtrave.*;
import geso.dms.distributor.beans.donhangtrave.imp.*;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
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

public class DonhangtraveNguyenDonUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	IDonhangtrave dhtvBean;
	List<ISanpham> spList;
	PrintWriter out; 
	
    public DonhangtraveNguyenDonUpdateSvl() {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    //out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    
	    String id = util.getId(querystring);

	    dhtvBean = new Donhangtrave(id);
	    
	    String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		if (!dhtvBean.checkNull(view))
			view = "";
		dhtvBean.setView(view);
	    
        dhtvBean.setUserId(userId);
        dhtvBean.init_TraNguyenDon();
     
        session.setAttribute("dhtvBean", dhtvBean);
        session.setAttribute("nppId", dhtvBean.getNppId());        
        session.setAttribute("khId", dhtvBean.getKhId());
        session.setAttribute("dhId", dhtvBean.getDonhangId());
        session.setAttribute("ddkdId", dhtvBean.getDdkdId());
        session.setAttribute("ngaydonhang", dhtvBean.getNgaygiaodich() );
        
        String nextJSP = "/AHF/pages/Distributor/DonHangTraVeNguyenDonUpdate.jsp";
        
        if(request.getQueryString().indexOf("display") >= 0 ) 
        	nextJSP = "/AHF/pages/Distributor/DonHangTraVeNguyenDonDisplay.jsp";
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		this.out = response.getWriter();
		
	    String id = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id"));
	    if(id == null){  	
	    	dhtvBean = new Donhangtrave("");
	    }else{
	    	dhtvBean = new Donhangtrave(id);
	    }
	    
	    String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		if (!dhtvBean.checkNull(view))
			view = "";
		dhtvBean.setView(view);
	    
		String userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
		dhtvBean.setUserId(userId);
		
		String dhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dhId"));
		if (dhId == null)
			dhId = "";
		dhtvBean.setDonhangId(dhId);
		session.setAttribute("dhId", dhId);
		
		String khId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khId"));
		if (khId == null)
			khId = "";
		dhtvBean.setKhId(khId);
		System.out.println("dhId: "+dhId+"--khId: "+khId);
		
		String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
		if (tungay == null)
			tungay = "";
		dhtvBean.setTungay(tungay);
		
		String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
		if (denngay == null)
			denngay = "";
		dhtvBean.setDenngay(denngay);
		
		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
		if (nppId == null)
			nppId = "";
		System.out.println("nppId" +nppId);
		dhtvBean.setNppId(nppId);
		session.setAttribute("nppId", nppId);
		
    	String ngaygd = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaygiaodich"));
    	if (ngaygd == null || ngaygd == "")
			ngaygd = this.getDateTime();			
    	dhtvBean.setNgaygiaodich(ngaygd);
    	session.setAttribute("ngaydonhang", ngaygd );
    	
    	String ddkdId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdId"));
    	if (ddkdId == null)
    		ddkdId = "";    	
    	dhtvBean.setDdkdId(ddkdId);
    	session.setAttribute("ddkdId", ddkdId);    	    	

    	String lydo = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("lydo"));
    	if (lydo == null)
    		lydo = "";    	
    	dhtvBean.setLydo(lydo);   	

		String ngaysua = getDateTime();
    	dhtvBean.setNgaysua(ngaysua);

		boolean error = false;
		
		if (!dhtvBean.checkNull(ddkdId)){
			dhtvBean.setMessage("Vui lòng chọn Nhân viên bán hàng.");
			error = true;
		}
		
		if (!dhtvBean.checkNull(ngaygd)){
			dhtvBean.setMessage("Vui lòng chọn ngày trả hàng.");
			error = true;
		}	
	
 		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
 		System.out.println("Action post: "+action);
		 		
 		if (!error && action.equals("save"))
		{
			if (!dhtvBean.checkNull(id))
			{
				if (!(dhtvBean.CreateDhtv_NguyenDon()))
				{							
					dhtvBean.createRs_TraNguyenDon();
					session.setAttribute("dhtvBean", dhtvBean);
					String nextJSP = "/AHF/pages/Distributor/DonHangTraVeNguyenDonNew.jsp";
					response.sendRedirect(nextJSP);
				}else{
					IDonhangtraveList obj = new DonhangtraveList();
					obj.setUserId(userId);
					obj.init_TraNguyenDonList("");
					session.setAttribute("obj", obj);
						
					String nextJSP = "/AHF/pages/Distributor/DonHangTraVeNguyenDon.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
				
			}else{
				if (!(dhtvBean.UpdateDhtv_NguyenDon()))
				{
					dhtvBean.init_TraNguyenDon();
					dhtvBean.setKhId(khId);
					dhtvBean.setDdkdId(ddkdId);
					session.setAttribute("dhtvBean", dhtvBean);
					String nextJSP = "/AHF/pages/Distributor/DonHangTraVeNguyenDonUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IDonhangtraveList obj = new DonhangtraveList();
					obj.setUserId(userId);
					obj.init_TraNguyenDonList("");
					session.setAttribute("obj", obj);
						
					String nextJSP = "/AHF/pages/Distributor/DonHangTraVeNguyenDon.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
			}
		}
		else
		{			
			dhtvBean.setUserId(userId);
			dhtvBean.createRs_TraNguyenDon();
			String nextJSP = "/AHF/pages/Distributor/DonHangTraVeNguyenDonNew.jsp";
			if (dhtvBean.checkNull(id))
			{
				nextJSP = "/AHF/pages/Distributor/DonHangTraVeNguyenDonUpdate.jsp";   						
			}
			session.setAttribute("dhtvBean", dhtvBean);
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
