package geso.dms.center.servlets.phieuxuatkhodms;

import geso.dms.center.beans.phieuxuatkhodms.IPhieuxuatkhoDms;
import geso.dms.center.beans.phieuxuatkhodms.IPhieuxuatkhoDmsList;
import geso.dms.center.beans.phieuxuatkhodms.imp.PhieuxuatkhoDms;
import geso.dms.center.beans.phieuxuatkhodms.imp.PhieuxuatkhoDmsList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Sheet;
import jxl.Workbook;

import com.oreilly.servlet.MultipartRequest;

public class PhieuxuatkhoDmsUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	PrintWriter out;

	public PhieuxuatkhoDmsUpdateSvl() 
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
		}
		else
		{
			session.setMaxInactiveInterval(30000);

			Utility util = new Utility();

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);

			if (userId.length()==0)
				userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"))); 

			String id = util.getId(querystring); 

			//GIU NGUYEN TIM KIEM
			IPhieuxuatkhoDmsList lsxBeanList = new PhieuxuatkhoDmsList();

			String tungaytk = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
			if(tungaytk == null)
				tungaytk = "";	    	   	    
			lsxBeanList.setTungay(tungaytk);

			String denngaytk = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
			if(denngaytk == null)
				denngaytk = "";	    	   	    
			lsxBeanList.setDenngay(denngaytk);

			String vungtk = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"));
			if(vungtk == null)
				vungtk = "";	    	   	    
			lsxBeanList.setVungId(vungtk);

			String khuvuctk = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"));
			if(khuvuctk == null)
				khuvuctk = "";	    	   	    
			lsxBeanList.setKhuvucId(khuvuctk);

			String npptk = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
			if(npptk == null)
				npptk = "";	    	   	    
			lsxBeanList.setNppTen(npptk);

			String kbhtk = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId"));
			if(kbhtk == null)
				kbhtk = "";	    	   	    
			lsxBeanList.setKbhId(kbhtk);		    		   

			String trangthaitk = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));		    
			if(trangthaitk == null)
				trangthaitk = "";	    	   	    
			lsxBeanList.setTrangthai(trangthaitk);

			session.setAttribute("lsxBeanList", lsxBeanList);

			//--------------------------------------------------------

			IPhieuxuatkhoDms lsxBean = new PhieuxuatkhoDms(id);
			lsxBean.setUserId(userId); 		    		  

			String nextJSP = "";

			lsxBean.init();

			session.setAttribute("dvkdId", lsxBean.getDvkdId());
			session.setAttribute("kbhId", lsxBean.getKbhId());
			session.setAttribute("nppId", lsxBean.getNppId());

			if(!querystring.contains("display"))
			{
				nextJSP = "/AHF/pages/Center/PhieuxuatkhoDmsUpdate.jsp";
			}
			else
			{
				nextJSP = "/AHF/pages/Center/PhieuxuatkhoDmsDisplay.jsp";
			}

			session.setAttribute("lsxBean", lsxBean);
			response.sendRedirect(nextJSP);
		}		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen"); 
		NumberFormat formater = new DecimalFormat("#,###,###.###");

		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}
		else
		{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			session.setMaxInactiveInterval(30000);
			this.out = response.getWriter();
			IPhieuxuatkhoDms lsxBean;
			Utility util = new Utility();

			String id=null;
			String sochungtu=null;
			String ngaychungtu=null;
			String nppId=null;
			String action = null;		    
			String kbhId = null;
			
			String[] spMa =	null;
			String[] spTen=	null;
			String[] dongia = null;
			String[] spScheme =	null;
			
			id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
			if(id == null)
			{  	
				lsxBean = new PhieuxuatkhoDms("");
			}
			else
			{
				lsxBean = new PhieuxuatkhoDms(id);
			}

			lsxBean.setUserId(userId);

			sochungtu = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sochungtu")));
			if(sochungtu == null || sochungtu.trim().length() <= 0)
				sochungtu = "";
			lsxBean.setSochungtu(sochungtu);
			
			ngaychungtu = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaychungtu")));
			if(ngaychungtu == null || ngaychungtu.trim().length() <= 0)
				ngaychungtu = getDateTime();
			lsxBean.setNgaychungtu(ngaychungtu);

			String sopo = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sopo")));
			if(sopo == null || sopo.trim().length() <= 0)
				sopo = "";
			lsxBean.setSopo(sopo);
			
			String soso = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("soso")));
			if(soso == null || sopo.trim().length() <= 0)
				soso = "";
			lsxBean.setSoso(soso);

			String ghichu = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ghichu")));
			if(ghichu == null)
				ghichu = "";
			lsxBean.setGhichu(ghichu);

			String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
			if (dvkdId == null)
				dvkdId = "";				
			lsxBean.setDvkdId(dvkdId);

			kbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhId")));
			if (kbhId == null)
				kbhId = "";				
			lsxBean.setKbhId(kbhId);

			nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
			if (nppId == null)
				nppId = "";				
			lsxBean.setNppId(nppId);

			spScheme = request.getParameterValues("scheme");
			lsxBean.setSpScheme(spScheme);

			spMa = request.getParameterValues("spMa");
			lsxBean.setSpMa(spMa);

			spTen = request.getParameterValues("spTen");
			lsxBean.setSpTen(spTen);

			String[] dvt = request.getParameterValues("donvi");
			lsxBean.setSpDonvi(dvt);

			String[] soluong = request.getParameterValues("soluong");
			lsxBean.setSpSoluong(soluong);

			dongia = request.getParameterValues("dongia");
			lsxBean.setSpGianhap(dongia);

			String[] spvat = request.getParameterValues("spvat");
			lsxBean.setSpVat(spvat);

			IPhieuxuatkhoDmsList lsxBeanList = new PhieuxuatkhoDmsList();

			String tungaytk = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungaytk"));
			if(tungaytk == null)
				tungaytk = "";	    	   	    
			lsxBeanList.setTungay(tungaytk);

			String denngaytk = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngaytk"));
			if(denngaytk == null)
				denngaytk = "";	    	   	    
			lsxBeanList.setDenngay(denngaytk);

			String vungtk = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungtk"));
			if(vungtk == null)
				vungtk = "";	    	   	    
			lsxBeanList.setVungId(vungtk);

			String khuvuctk = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvuctk"));
			if(khuvuctk == null)
				khuvuctk = "";	    	   	    
			lsxBeanList.setKhuvucId(khuvuctk);

			String npptk = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("npptk"));
			if(npptk == null)
				npptk = "";	    	   	    
			lsxBeanList.setNppTen(npptk);

			String kbhtk = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhtk"));
			if(kbhtk == null)
				kbhtk = "";	    	   	    
			lsxBeanList.setKbhId(kbhtk);		    		   

			String trangthaitk = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthaitk"));		    
			if(trangthaitk == null)
				trangthaitk = "";	    	   	    
			lsxBeanList.setTrangthai(trangthaitk);

			session.setAttribute("lsxBeanList", lsxBeanList);

			action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));		    
			//lsxBean.init();
			if(action.equals("save"))
			{	
				if(id == null)
				{
					boolean kq = false;
					kq = lsxBean.createNK();

					if(!kq)
					{
						lsxBean.createRs();
						session.setAttribute("dvkdId", lsxBean.getDvkdId());
						session.setAttribute("kbhId", lsxBean.getKbhId());
						session.setAttribute("nppId", lsxBean.getNppId());
						session.setAttribute("ngaychungtu", lsxBean.getNgaychungtu());
						session.setAttribute("lsxBean", lsxBean);
						String nextJSP = "/AHF/pages/Center/PhieuxuatkhoDmsNew.jsp";

						response.sendRedirect(nextJSP);
					}
					else
					{
						IPhieuxuatkhoDmsList obj = new PhieuxuatkhoDmsList();						
						obj.setUserId(userId);
						obj.init("");  
						session.setAttribute("obj", obj);  	
						session.setAttribute("userId", userId);
						
						String nextJSP = "/AHF/pages/Center/PhieuxuatkhoDms.jsp";

						response.sendRedirect(nextJSP);
					}
				}
				else
				{
					boolean kq = false;
					kq = lsxBean.updateNK();

					if(!kq)
					{
						lsxBean.createRs();
						session.setAttribute("dvkdId", lsxBean.getDvkdId());
						session.setAttribute("kbhId", lsxBean.getKbhId());
						session.setAttribute("nppId", lsxBean.getNppId());
						session.setAttribute("ngaychungtu", lsxBean.getNgaychungtu());
						session.setAttribute("lsxBean", lsxBean);
						String nextJSP = "/AHF/pages/Center/PhieuxuatkhoDmsUpdate.jsp";

						response.sendRedirect(nextJSP);
					}
					else
					{
						IPhieuxuatkhoDmsList obj = new PhieuxuatkhoDmsList();							
						obj.setUserId(userId);					    					   

						//String search = getSearchQuery(request, obj);
						obj.init("");
						session.setAttribute("obj", obj);
						String nextJSP = "/AHF/pages/Center/PhieuxuatkhoDms.jsp";

						response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
				lsxBean.createRs();
				session.setAttribute("dvkdId", lsxBean.getDvkdId());
				session.setAttribute("kbhId", lsxBean.getKbhId());
				session.setAttribute("nppId", lsxBean.getNppId());
				session.setAttribute("ngaychungtu", lsxBean.getNgaychungtu());
				session.setAttribute("lsxBean", lsxBean);
				String nextJSP = "/AHF/pages/Center/PhieuxuatkhoDmsNew.jsp";
				
				if(id != null)
				{
					nextJSP = "/AHF/pages/Center/PhieuxuatkhoDmsUpdate.jsp";
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
