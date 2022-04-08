package geso.dms.distributor.servlets.xuatkho;

import geso.dms.distributor.beans.xuatkho.IErpYeucauxuatkhoNpp;
import geso.dms.distributor.beans.xuatkho.IErpYeucauxuatkhoNppList;
import geso.dms.distributor.beans.xuatkho.imp.ErpYeucauxuatkhoNpp;
import geso.dms.distributor.beans.xuatkho.imp.ErpYeucauxuatkhoNppList;
import geso.dms.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpYeucauxuatkhoNppUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
    public ErpYeucauxuatkhoNppUpdateSvl() 
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
			response.sendRedirect("/SalesUpERP/index.jsp");
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
		    IErpYeucauxuatkhoNpp lsxBean = new ErpYeucauxuatkhoNpp(id);
		    lsxBean.setUserId(userId); 
		    
		    String nextJSP = "";
		    
    		lsxBean.init();
    		
    		if(!querystring.contains("display"))
    		{
    			nextJSP = "/AHF/pages/Distributor/ErpYeuCauXuatKhoNppUpdate.jsp";	
    		}
    		else
    		{
    			nextJSP = "/AHF/pages/Distributor/ErpYeuCauXuatKhoNppDisplay.jsp";
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
			response.sendRedirect("/SalesUpERP/index.jsp");
		}
		else
		{
			request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
			
			session.setMaxInactiveInterval(30000);
			
			this.out = response.getWriter();
			IErpYeucauxuatkhoNpp lsxBean;
			
			Utility util = new Utility();	
			String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
		    if(id == null)
		    {  	
		    	lsxBean = new ErpYeucauxuatkhoNpp("");
		    }
		    else
		    {
		    	lsxBean = new ErpYeucauxuatkhoNpp(id);
		    }
	
		    lsxBean.setUserId(userId);
		    
		    String ngayyeucau = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaychuyen")));
		    if(ngayyeucau == null || ngayyeucau.trim().length() <= 0)
		    	ngayyeucau = getDateTime();
		    lsxBean.setNgayyeucau(ngayyeucau);
		    
		    String ghichu = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ghichu")));
		    if(ghichu == null)
		    	ghichu = "";
		    lsxBean.setGhichu(ghichu);
		    
		    String xuatchoId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("xuatchoId")));
		    if(xuatchoId == null)
		    	xuatchoId = "0";
		    lsxBean.setXuatcho(xuatchoId);
		    
			String khonhapId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khonhapId")));
			if (khonhapId == null)
				khonhapId = "";				
			lsxBean.setKhoNhapId(khonhapId);
			
			String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
			if (nppId == null)
				nppId = "";				
			lsxBean.setNppId(nppId);
			
			String khId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khId")));
			if (khId == null)
				khId = "";				
			lsxBean.setKhId(khId);
			
			String[] ddhIds = request.getParameterValues("ddhIds");
			if (ddhIds != null)
			{
				String _scheme = "";
				for(int i = 0; i < ddhIds.length; i++)
				{
					_scheme += ddhIds[i] + ",";
				}
				
				if(_scheme.trim().length() > 0)
				{
					_scheme = _scheme.substring(0, _scheme.length() - 1);
					lsxBean.setDondathangId(_scheme);
				}
			}
			
			String[] spId = request.getParameterValues("spId");
			lsxBean.setSpId(spId);
			
			String[] spMa = request.getParameterValues("spMa");
			lsxBean.setSpMa(spMa);
			
			String[] spTen = request.getParameterValues("spTen");
			lsxBean.setSpTen(spTen);
			
			String[] dvt = request.getParameterValues("donvi");
			lsxBean.setSpDonvi(dvt);
			
			String[] soluongDAT = request.getParameterValues("soluongDAT");
			lsxBean.setSpSoluongDat(soluongDAT);
			
			String[] tonkho = request.getParameterValues("tonkho");
			lsxBean.setSpTonKho(tonkho);
			
			String[] daxuat = request.getParameterValues("daxuat");
			lsxBean.setSpDaXuat(daxuat);
			
			String[] soluong = request.getParameterValues("soluong");
			lsxBean.setSpSoluong(soluong);
			
			String[] spLoai = request.getParameterValues("spLoai");
			lsxBean.setSpLoai(spLoai);
			
			String[] spScheme = request.getParameterValues("scheme");
			lsxBean.setSpScheme(spScheme);
			
			if(spId != null)
			{
				Hashtable<String, String> sanpham_soluong = new Hashtable<String, String>();
				for(int i = 0; i < spId.length; i++ )
				{
					//System.out.println("---SP MA LA: " + spMa[i]);
					String temID = spId[i] + "__" + spLoai[i] + "__" + spScheme[i].trim();
					
					String[] spSOLO = request.getParameterValues(temID + "_spSOLO");
					String[] soLUONGXUAT = request.getParameterValues(temID + "_spSOLUONG");
					
					if(soLUONGXUAT != null)
					{
						for(int j = 0; j < soLUONGXUAT.length; j++ )
						{
							if(soLUONGXUAT[j] != null && soLUONGXUAT[j].trim().length() > 0)
							{
								//System.out.println("---KEY SVL: " + ( spId[i] + "__" + spLoai[i] + "__" + spScheme[i].trim() + "__" + spSOLO[j] )  + "   --- GIA TRI: " + soLUONGXUAT[j].replaceAll(",", "") );
								sanpham_soluong.put(spId[i] + "__" + spLoai[i] + "__" + spScheme[i].trim() + "__" + spSOLO[j], soLUONGXUAT[j].replaceAll(",", "") );
							}
						}
					}
				}
				
				lsxBean.setSanpham_Soluong(sanpham_soluong);
			}
			
			
		    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		    
			if(action.equals("save"))
			{	
				if(id == null)
				{
					if(!lsxBean.create())
					{
						lsxBean.createRs();
	    		    	session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/AHF/pages/Distributor/ErpYeuCauXuatKhoNppNew.jsp";
	    				
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpYeucauxuatkhoNppList obj = new ErpYeucauxuatkhoNppList();
						
						obj.setUserId(userId);
						obj.init("");  
				    	session.setAttribute("obj", obj);  	
			    		session.setAttribute("userId", userId);
			    		
			    		String nextJSP = "/AHF/pages/Distributor/ErpYeuCauXuatKhoNpp.jsp";
			    		response.sendRedirect(nextJSP);
					}
				}
				else
				{
					if(!lsxBean.update())
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/AHF/pages/Distributor/ErpYeuCauXuatKhoNppUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpYeucauxuatkhoNppList obj = new ErpYeucauxuatkhoNppList();
						
					    obj.setUserId(userId);
					    obj.init("");
						session.setAttribute("obj", obj);							
						String nextJSP = "/AHF/pages/Distributor/ErpYeuCauXuatKhoNpp.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
				if(action.equals("changePO"))
				{
					lsxBean.setSanpham_Soluong(new Hashtable<String, String>());
					lsxBean.setSpId(null);
					lsxBean.setSpMa(null);
				}
				
				lsxBean.createRs();
				
				session.setAttribute("lsxBean", lsxBean);
				
				String nextJSP = "/AHF/pages/Distributor/ErpYeuCauXuatKhoNppNew.jsp";
				if(id != null)
					nextJSP = "/AHF/pages/Distributor/ErpYeuCauXuatKhoNppUpdate.jsp";
				
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
