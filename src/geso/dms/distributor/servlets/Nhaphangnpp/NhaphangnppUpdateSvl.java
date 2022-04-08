package geso.dms.distributor.servlets.Nhaphangnpp;

import geso.dms.distributor.beans.Nhaphangnpp.INhaphangnpp;
import geso.dms.distributor.beans.Nhaphangnpp.INhaphangnppList;
import geso.dms.distributor.beans.Nhaphangnpp.imp.Nhaphangnpp;
import geso.dms.distributor.beans.Nhaphangnpp.imp.NhaphangnppList;
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

public class NhaphangnppUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
    public NhaphangnppUpdateSvl() 
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
		    INhaphangnpp lsxBean = new Nhaphangnpp(id);
		    lsxBean.setUserId(userId); 
		    
		    String nextJSP = "";
		    
    		lsxBean.init();
    		
    		if(!querystring.contains("display"))
    		{
    			nextJSP = "/AHF/pages/Distributor/NhapHangnppUpdate.jsp";	
    		}
    		else
    		{
    			nextJSP = "/AHF/pages/Distributor/NhapHangnppDisplay.jsp";
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
			INhaphangnpp lsxBean;
			
			Utility util = new Utility();	
			String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
		    if(id == null)
		    {  	
		    	lsxBean = new Nhaphangnpp("");
		    }
		    else
		    {
		    	lsxBean = new Nhaphangnpp(id);
		    }
	
		    lsxBean.setUserId(userId);
		    
		    String ngayyeucau = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaychuyen")));
		    if(ngayyeucau == null || ngayyeucau.trim().length() <= 0)
		    	ngayyeucau = getDateTime();
		    lsxBean.setNgayyeucau(ngayyeucau);
		    
		    String ngaynhan = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaynhan")));
		    if(ngaynhan == null )
		    	ngaynhan = "";
		    lsxBean.setNgaynhap(ngaynhan);
		    
		    String sochungtu = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sochungtu")));
		    if(sochungtu == null )
		    	sochungtu = "";
		    lsxBean.setSOchungtu(sochungtu);
		    
		    String ghichu = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ghichu")));
		    if(ghichu == null)
		    	ghichu = "";
		    lsxBean.setGhichu(ghichu);
		    
		    String khonhanID = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khonhanID")));
		    if(khonhanID == null || khonhanID.trim().length() <= 0 )
		    	khonhanID = "100000";
		    lsxBean.setKhonhanId(khonhanID);
		    System.out.println("kho nhan id"+khonhanID);
		    String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
			if (nppId == null)
				nppId = "";				
			lsxBean.setNppId(nppId);
			
			String[] spId = request.getParameterValues("spId");
			lsxBean.setSpId(spId);
			
			String[] spTen = request.getParameterValues("spTen");
			lsxBean.setSpTen(spTen);
			
			String[] spSolo = request.getParameterValues("solo");
			lsxBean.setSpSolo(spSolo);
			
			String[] soluongXUAT = request.getParameterValues("soluongXUAT");
			lsxBean.setSpXuat(soluongXUAT);
			
			String[] soluong = request.getParameterValues("soluong");
			lsxBean.setSpSoluong(soluong);
			
			String[] spLoai = request.getParameterValues("spLoai");
			lsxBean.setSpLoai(spLoai);
			
			String[] spScheme = request.getParameterValues("scheme");
			lsxBean.setSpScheme(spScheme);
			
			
			String[] spNgayHetHan = request.getParameterValues("spNgayHetHan");
			lsxBean.setSpNgayHetHan(spNgayHetHan);
			
		  String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		    
			if(action.equals("save"))
			{	
				if(id == null)
				{
					if(!lsxBean.create())
					{
						lsxBean.createRs();
	    		    	session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/AHF/pages/Distributor/NhapHangnppNew.jsp";
	    				
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						INhaphangnppList obj = new NhaphangnppList();
						
						obj.setUserId(userId);
						obj.createNhaphanglist("");;  
				    	session.setAttribute("obj", obj);  	
			    		session.setAttribute("userId", userId);
			    		
			    		String nextJSP = "/AHF/pages/Distributor/NhapHangnpp.jsp";
			    		response.sendRedirect(nextJSP);
					}
				}
				else
				{
					if(!lsxBean.update())
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/AHF/pages/Distributor/NhapHangnppUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						INhaphangnppList obj = new NhaphangnppList();
						
					    obj.setUserId(userId);
					    obj.createNhaphanglist("");;
						session.setAttribute("obj", obj);							
						String nextJSP = "/AHF/pages/Distributor/NhapHangnpp.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
				if(action.equals("chot"))
				{
					if(!lsxBean.chot())
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/AHF/pages/Distributor/NhapHangnppUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						INhaphangnppList obj = new NhaphangnppList();
						
					    obj.setUserId(userId);
					    obj.createNhaphanglist("");
						session.setAttribute("obj", obj);							
						String nextJSP = "/AHF/pages/Distributor/NhapHangnpp.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				else
				{
					lsxBean.createRs();
	
					session.setAttribute("lsxBean", lsxBean);
					
					String nextJSP = "/AHF/pages/Distributor/NhapHangnppNew.jsp";
					if(id != null)
						nextJSP = "/AHF/pages/Distributor/NhapHangnppUpdate.jsp";
					
					response.sendRedirect(nextJSP);
				}
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
