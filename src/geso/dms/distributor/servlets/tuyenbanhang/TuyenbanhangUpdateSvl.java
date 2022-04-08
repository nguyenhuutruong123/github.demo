package geso.dms.distributor.servlets.tuyenbanhang;

import geso.dms.distributor.beans.tuyenbanhang.*;
import geso.dms.distributor.beans.tuyenbanhang.imp.*;
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

import org.apache.log4j.TTCCLayout;

public class TuyenbanhangUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	
    public TuyenbanhangUpdateSvl() 
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
		
		ITuyenbanhang tbhBean;
		PrintWriter out; 

		out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    
	    String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
	    if (view == null) {
	    	view = "";
	    }
	    
	    String npp_fk = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("npp_fk")));
	    if (npp_fk == null) {
	    	npp_fk = "";
	    }
	    
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    
	    String id = util.getId(querystring);
	    String param="";
		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		else if( Utility.CheckRuleUser( session,  request, response, "TuyenbanhangSvl", param, Utility.SUA ))
		{
			Utility.RedireactLogin(session, request, response);
		}else{
		    tbhBean = new Tuyenbanhang(id);
		    tbhBean.setView(view);
		    if (npp_fk != null && npp_fk.length() > 0) {
		    	tbhBean.setNppId(npp_fk);
		    }
		    System.out.println("npp_fk: "+npp_fk);
		    tbhBean.setUserId(userId);
		    tbhBean.init();
		          
	        session.setAttribute("tbhBean", tbhBean);
	        String nextJSP = "/AHF/pages/Distributor/TuyenBanHangUpdate.jsp";
	        response.sendRedirect(nextJSP);
		}

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

			ITuyenbanhang tbhBean;
			PrintWriter out; 


			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			out = response.getWriter();

			String id = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id"));
			if(id == null)
				tbhBean = new Tuyenbanhang("");
			else
				tbhBean = new Tuyenbanhang(id);

			userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
			tbhBean.setUserId(userId);

			String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
			if (nppId == null)
				nppId = "";
			tbhBean.setNppId(nppId);

			String view = cutil.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
			if (view == null) {
				view = "";
			}
			tbhBean.setView(view);

			String ddkdId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdTen"));
			if (ddkdId == null)
				ddkdId = "";				
			tbhBean.setDdkdId(ddkdId);

			String diengiai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tbhTen"));
			if (diengiai == null)
				diengiai = "";
			tbhBean.setDiengiai(diengiai);

			String nlv = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaylamviec"));
			if (nlv == null)
				nlv = "";
			tbhBean.setNgaylamviec(nlv);

			String khTen = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khTen"));
			if (khTen == null)
				khTen = "";
			tbhBean.setTenkhachhang(khTen);

			String diachi = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diachi"));
			if (diachi == null)
				diachi = "";
			tbhBean.setDiachi(diachi);

			String[] tansoIds = request.getParameterValues("tansoList");
			tbhBean.setTansoList(tansoIds);

			String[] kh_tbh_dptIds = request.getParameterValues("kh_tbh_dptList");
			tbhBean.setKh_Tbh_DptIds(kh_tbh_dptIds);

			String[] kh_tbh_cdptIds = request.getParameterValues("kh_tbh_cdptList");
			tbhBean.setKh_Tbh_CdptIds(kh_tbh_cdptIds);

			String[] thutu=request.getParameterValues("thutu");
			tbhBean.setSoTT(thutu);

			String ngaysua = getDateTime();
			tbhBean.setNgaysua(ngaysua);

			boolean error = false;

			if (ddkdId.trim().length()== 0){
				tbhBean.setMessage("Vui lòng chọn đại diện kinh doanh.");
				error = true;
			}

			if (diengiai.trim().length()== 0){
				tbhBean.setMessage("Vui lòng nhập diễn giải.");
				error = true;
			}

			if (nlv.trim().length()== 0){
				tbhBean.setMessage("Vui lòng chọn ngày làm việc.");
				error = true;		
			}

			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			/*
 		if(action.equals("tim"))
 		{
 			tbhBean.setUserId(userId);
 			tbhBean.createRS();
 			session.setAttribute("tbhBean", tbhBean);			
 			String nextJSP = "/AHF/pages/Distributor/TuyenBanHangNew.jsp";
 			response.sendRedirect(nextJSP);	
 		}
 		else
 		{
	 		if(action.equals("sangtrai"))
	 		{
	 			if ( id == null)
	 			{
					if(kh_tbh_dptIds != null || kh_tbh_cdptIds != null)
					{
		 				if (!(tbhBean.CreateTbh()))
		 				{	
							tbhBean.setUserId(userId);
							tbhBean.createRS();
							session.setAttribute("tbhBean", tbhBean);			
							String nextJSP = "/AHF/pages/Distributor/TuyenBanHangNew.jsp";
							response.sendRedirect(nextJSP);
						}
						else
						{ 
			 				tbhBean.setUserId(userId);
			 				tbhBean.init();
							tbhBean.createRS();
							session.setAttribute("tbhBean", tbhBean);
							String nextJSP = "/AHF/pages/Distributor/TuyenBanHangUpdate.jsp";
							response.sendRedirect(nextJSP);
						}
					}
					else
					{  //khong co lua chon nao
						tbhBean.setUserId(userId);
						tbhBean.createRS();
						session.setAttribute("tbhBean", tbhBean);			
						String nextJSP = "/AHF/pages/Distributor/TuyenBanHangNew.jsp";
						response.sendRedirect(nextJSP);
					}
	 			}
	 			else
	 			{  	//it nhat co lua chon voi luu lai
	 				if(kh_tbh_dptIds != null || kh_tbh_cdptIds != null)
	 			    {
	 					tbhBean.UpdateTbh();
	 		    	}

	 				tbhBean.setUserId(userId);
	 				tbhBean.init();
					tbhBean.createRS();
					session.setAttribute("tbhBean", tbhBean);
					String nextJSP = "/AHF/pages/Distributor/TuyenBanHangUpdate.jsp";
					response.sendRedirect(nextJSP);
	 			}
	 		}
	 		else
	 		{
	 			 System.out.println("Ban nhan nut save...\n");
			     if(id == null)
			     {
			    	if (!(tbhBean.CreateTbh()))
	 				{	
						tbhBean.setUserId(userId);
						tbhBean.createRS();
						session.setAttribute("tbhBean", tbhBean);			
						String nextJSP = "/AHF/pages/Distributor/TuyenBanHangNew.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{ 
						ITuyenbanhangList obj = new TuyenbanhangList();
						obj.setUserId(userId);
						obj.init("");
						session.setAttribute("obj", obj);

						String nextJSP = "/AHF/pages/Distributor/TuyenBanHang.jsp";
						response.sendRedirect(nextJSP);
					}
			     }
			     else
			     {
			    	 if (!(tbhBean.UpdateTbh()))
	 				{	
						tbhBean.setUserId(userId);
						tbhBean.init();
						session.setAttribute("tbhBean", tbhBean);			
						String nextJSP = "/AHF/pages/Distributor/TuyenBanHangUpdate.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{ 
						ITuyenbanhangList obj = new TuyenbanhangList();
						obj.setUserId(userId);
						obj.init("");
						session.setAttribute("obj", obj);

						String nextJSP = "/AHF/pages/Distributor/TuyenBanHang.jsp";
						response.sendRedirect(nextJSP);
					}
			     }

	 		}
		}
		}
			 */
			System.out.println("action:"+action);
			System.out.println("action:"+view);
			if(action.equals("save") && ( view != null || view.equals("TT") ) )
			{
				if ( id == null)
				{
					String param="";
					if(view.trim().length() > 0) param ="&view="+view;
					if ( Utility.CheckSessionUser( session,  request, response))
					{
						Utility.RedireactLogin(session, request, response);
					}
					else if( Utility.CheckRuleUser( session,  request, response, "TuyenbanhangSvl", param, Utility.THEM ))
					{
						Utility.RedireactLogin(session, request, response);
					}
					else{
						if (!(tbhBean.CreateTbh()))
						{	
							tbhBean.setUserId(userId);
							tbhBean.createRS();
	
							// Save Data into session
							session.setAttribute("tbhBean", tbhBean);
	
							String nextJSP = "/AHF/pages/Distributor/TuyenBanHangNew.jsp";
							response.sendRedirect(nextJSP);
						}
						else{
							
								ITuyenbanhangList obj = new TuyenbanhangList();
								obj.setView(view);
								obj.setUserId(userId);
								obj.init("");
								session.setAttribute("obj", obj);
		
								String nextJSP = "/AHF/pages/Distributor/TuyenBanHang.jsp";
								response.sendRedirect(nextJSP);		
						}
					}
				}
				else{
					String param="";
					if(view.trim().length() > 0) param ="&view="+view;
					if ( Utility.CheckSessionUser( session,  request, response))
					{
						Utility.RedireactLogin(session, request, response);
					}
					else if( Utility.CheckRuleUser( session,  request, response, "TuyenbanhangSvl", param, Utility.SUA ))
					{
						Utility.RedireactLogin(session, request, response);
					}
					else{
						if (!(tbhBean.UpdateTbh()))
						{
							tbhBean.setUserId(userId);
							tbhBean.init();
							session.setAttribute("tbhBean", tbhBean);
							String nextJSP = "/AHF/pages/Distributor/TuyenBanHangUpdate.jsp";
							response.sendRedirect(nextJSP);
						}
						else{
							ITuyenbanhangList obj = new TuyenbanhangList();
							obj.setView(view);
							obj.setUserId(userId);
							obj.init("");
							session.setAttribute("obj", obj);

							String nextJSP = "/AHF/pages/Distributor/TuyenBanHang.jsp";
							response.sendRedirect(nextJSP);			    			    									
						}
					}
					
				}
			}
			else
			{	
				System.out.println("action lè : "+action);
				tbhBean.setUserId(userId);	
				tbhBean.createRS();
				session.setAttribute("tbhBean", tbhBean);			
				String nextJSP;
				if (id == null){			
					nextJSP = "/AHF/pages/Distributor/TuyenBanHangNew.jsp";
				}else{
					nextJSP = "/AHF/pages/Distributor/TuyenBanHangUpdate.jsp";   						
				}
				response.sendRedirect(nextJSP);		
			}	
		}
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
