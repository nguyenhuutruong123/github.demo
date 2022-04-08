package geso.dms.distributor.servlets.dondathang;

import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.dondathang.IErpDenghidathangNpp;
import geso.dms.distributor.beans.dondathang.IErpDenghidathangNppList;
import geso.dms.distributor.beans.dondathang.imp.ErpDenghidathangNpp;
import geso.dms.distributor.beans.dondathang.imp.ErpDenghidathangNppList;
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

public class ErpDenghidathangNppUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
    public ErpDenghidathangNppUpdateSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		
		// kiểm tra quyền xem menu
	    String param="";
		if( Utility.CheckRuleUser( session,  request, response, "ErpDenghidathangNppSvl", param, Utility.XEM ) || Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
			return;
		}
		
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
		    IErpDenghidathangNpp lsxBean = new ErpDenghidathangNpp(id);
		    lsxBean.setUserId(userId); 
		    
		    // Kiểm id hợp lệ
		    if(id != null && id.trim().length() > 0 
		    		&& !Utility.KiemTra_PK_SEQ_HopLe(id, "ERP_Denghidathang", lsxBean.getDb())){
		    	lsxBean.DBclose();
	    		return;
	    	}
		    
		    String nextJSP = "";
		    
    		lsxBean.init();
    		
    		session.setAttribute("dvkdId", lsxBean.getDvkdId());
			session.setAttribute("kbhId", lsxBean.getKbhId());
			session.setAttribute("nppId", lsxBean.getNppId());
    		
    		if(!querystring.contains("display"))
    		{
    			int[] quyen = Utility.Getquyen("ErpDenghidathangNppSvl", "",lsxBean.getUserId());
    			if(quyen[Utility.SUA]!=1){
    				lsxBean.DBclose();
    				return;
    			}
    			nextJSP = "/AHF/pages/Distributor/ErpDeNghiDatHangNppUpdate.jsp";
    		}
    		else
    		{
    			nextJSP = "/AHF/pages/Distributor/ErpDeNghiDatHangNppDisplay.jsp";
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
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		
		// kiểm tra quyền xem menu
	    String param="";
		if( Utility.CheckRuleUser( session,  request, response, "ErpDenghidathangNppSvl", param, Utility.XEM ) || Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
			return;
		}
		
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}
		else
		{
			
			session.setMaxInactiveInterval(30000);
			
			this.out = response.getWriter();
			IErpDenghidathangNpp lsxBean;
			
			Utility util = new Utility();	
			String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
		    if(id == null || (id!=null && id.trim().length()<=0))
		    {  	
		    	lsxBean = new ErpDenghidathangNpp("");
		    }
		    else
		    {
		    	lsxBean = new ErpDenghidathangNpp(id);
		    }
		    
		 // Kiểm tra id có hợp lệ không
		    if(id != null && id.trim().length() > 0 
		    		&& !Utility.KiemTra_PK_SEQ_HopLe(id, "ERP_Denghidathang", lsxBean.getDb())){
		    	lsxBean.DBclose();
	    		return;
	    	}
	
		    lsxBean.setUserId(userId);
		    
		    String ngayyeucau = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaychuyen")));
		    if(ngayyeucau == null || ngayyeucau.trim().length() <= 0)
		    	ngayyeucau = getDateTime();
		    lsxBean.setNgayyeucau(ngayyeucau);
		    
		    String ngaydenghi = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaydenghi")));
		    if(ngaydenghi == null || ngaydenghi.trim().length() <= 0)
		    	ngaydenghi = "";
		    lsxBean.setNgaydenghi(ngaydenghi);
		    	    
		    String ghichu = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ghichu")));
		    if(ghichu == null)
		    	ghichu = "";
		    lsxBean.setGhichu(ghichu);
		    
			String khonhapId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khonhapId")));
			if (khonhapId == null)
				khonhapId = "";				
			lsxBean.setKhoNhapId(khonhapId);
			
			String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
			if (dvkdId == null)
				dvkdId = "";				
			lsxBean.setDvkdId(dvkdId);
			
			String kbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhId")));
			if (kbhId == null)
				kbhId = "";				
			lsxBean.setKbhId(kbhId);
			
			String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
			if (nppId == null)
				nppId = "";				
			lsxBean.setNppId(nppId);
			
			String chietkhau = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ptChietkhau")));
			if (chietkhau == null)
				chietkhau = "";				
			lsxBean.setChietkhau(chietkhau);
			
			String vat = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ptVat")));
			if (vat == null)
				vat = "";				
			lsxBean.setVat(vat);
			
			String loaidonhang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaidonhang")));
			if (loaidonhang == null)
				loaidonhang = "";				
			lsxBean.setLoaidonhang(loaidonhang); 
			
			String tansuatdathang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tansuatdathang")));
			if (tansuatdathang == null)
				tansuatdathang = "6";				
			lsxBean.setTansauatdathang(tansuatdathang);
			
			String[] schemeIds = request.getParameterValues("schemeIds");
			if (schemeIds != null)
			{
				String _scheme = "";
				for(int i = 0; i < schemeIds.length; i++)
				{
					_scheme += schemeIds[i] + ",";
				}
				
				if(_scheme.trim().length() > 0)
				{
					_scheme = _scheme.substring(0, _scheme.length() - 1);
					lsxBean.setSchemeId(_scheme);
				}
			}
			
			String[] spMa = request.getParameterValues("spMA");
			String[] soluong = request.getParameterValues("spSOLUONG");
			
			if(spMa != null)
			{
				Hashtable<String, String> sp_soluong = new Hashtable<String, String>();
				for(int i = 0; i < spMa.length; i++ )
				{
					if(soluong[i].trim().length() > 0)
					{
						sp_soluong.put(spMa[i], soluong[i]);
					}
				}
				lsxBean.setSanpham_soluong(sp_soluong);
			}
			
		    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			if(action.equals("save"))
			{	
				if(id == null || (id!=null && id.trim().length()<=0))
				{
					boolean kq = false;
					int[] quyen = Utility.Getquyen("ErpDenghidathangNppSvl", "",lsxBean.getUserId());
					if(quyen[Utility.THEM]==1){
						kq = lsxBean.createNK();
					}
					
					
					if(!kq)
					{
						lsxBean.createRs();
	    		    	session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/AHF/pages/Distributor/ErpDeNghiDatHangNppNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpDenghidathangNppList obj = new ErpDenghidathangNppList();
						obj.setLoaidonhang(loaidonhang);
						
						obj.setUserId(userId);
						obj.init("");  
				    	session.setAttribute("obj", obj);  	
			    		session.setAttribute("userId", userId);
			    		
			    		String nextJSP = "/AHF/pages/Distributor/ErpDeNghiDatHangNpp.jsp";
			    		response.sendRedirect(nextJSP);
					}
				}
				else
				{
					boolean kq = false;
					int[] quyen = Utility.Getquyen("ErpDenghidathangNppSvl", "",lsxBean.getUserId());
					if(quyen[Utility.SUA]==1){
						kq = lsxBean.updateNK();
					}
					
					if(!kq)
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/AHF/pages/Distributor/ErpDeNghiDatHangNppUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpDenghidathangNppList obj = new ErpDenghidathangNppList();
						obj.setLoaidonhang(loaidonhang);
						
					    obj.setUserId(userId);
					    obj.init("");
						session.setAttribute("obj", obj);							
						String nextJSP = "/AHF/pages/Distributor/ErpDeNghiDatHangNpp.jsp";
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
				
				session.setAttribute("lsxBean", lsxBean);
				
				String nextJSP = "";
				
				nextJSP = "/AHF/pages/Distributor/ErpDeNghiDatHangNppNew.jsp";
				if(id != null)
				{
					nextJSP = "/AHF/pages/Distributor/ErpDeNghiDatHangNppUpdate.jsp";
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
