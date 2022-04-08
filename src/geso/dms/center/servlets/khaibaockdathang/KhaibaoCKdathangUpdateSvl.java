package geso.dms.center.servlets.khaibaockdathang;

import geso.dms.center.beans.khaibaockdathang.IKhaibaoCKdathang;
import geso.dms.center.beans.khaibaockdathang.IKhaibaoCKdathanglist;
import geso.dms.center.beans.khaibaockdathang.imp.KhaibaoCKdathang;
import geso.dms.center.beans.khaibaockdathang.imp.KhaibaoCKdathanglist;
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

public class KhaibaoCKdathangUpdateSvl extends HttpServlet  {

	private static final long serialVersionUID = 1L;
	private PrintWriter out;  
       
    public KhaibaoCKdathangUpdateSvl() {
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
	    
	    if (userId.length()==0)
	    	userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    
	    String id = util.getId(querystring);  	

	    IKhaibaoCKdathang ckdhBean = new KhaibaoCKdathang(id);
	    ckdhBean.createCkdh();
	    ckdhBean.createRs();
	    
        ckdhBean.setUserId(userId);
        session.setAttribute("ckdhBean", ckdhBean);
        String nextJSP = "";
        if(!querystring.contains("display"))
		{
        	nextJSP = "/AHF/pages/Center/KhaiBaoCKDatHangUpdate.jsp";
		}
		else
		{
			nextJSP = "/AHF/pages/Center/KhaibaoCKdathangDisplay.jsp";
		}
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		IKhaibaoCKdathang ckdhBean;
		this.out = response.getWriter();
		
		Utility util = new Utility();
		
	    String id =  geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id"));
	    if(id == null){  	
	    	ckdhBean = new KhaibaoCKdathang("");
	    }else{
	    	ckdhBean = new KhaibaoCKdathang(id);
	    }
	    
	    
		String userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
		ckdhBean.setUserId(userId);
	    
    	String scheme = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("scheme"));
		if (scheme == null)
			scheme = "";
    	ckdhBean.setScheme(scheme);
		
		String loaick = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaick"));
		if (loaick == null)
			loaick = "";
		ckdhBean.setLoaiCK(loaick);
		System.out.println("\n " + loaick +" \n ");
		
		String diengiai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		ckdhBean.setDiengiai(diengiai);
		
		String vungId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"));
		if (vungId == null)
			vungId = "";
		ckdhBean.setVungIds(vungId);
		
		String khuvucId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"));
		if (khuvucId == null)
			khuvucId = "";
		ckdhBean.setKvIds(khuvucId);
		
		String nganhhangId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nganhhangId"));
		if (nganhhangId == null)
			nganhhangId = "";
		ckdhBean.setNganhhangIds(nganhhangId);
		
		String nhanhangId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhanhangId"));
		if (nhanhangId == null)
			nhanhangId = "";
		ckdhBean.setNhanhangIds(nhanhangId);
		
		String nhomspId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhomspId"));
		if (nhomspId == null)
			nhomspId = "";
		ckdhBean.setNhomspIds(nhomspId);
		
		String mucVuot = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("mucVuot")));
		if (mucVuot == null)
			mucVuot = "";
		ckdhBean.setMucvuot(mucVuot);
		
		String chietkhauMucVuot = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chietkhauMucVuot")));
		if (chietkhauMucVuot == null)
			chietkhauMucVuot = "";
		ckdhBean.setCKMucvuot(chietkhauMucVuot);
		
		String diengiaimucvuot = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiaiMucVuot")));
		if (diengiaimucvuot == null)
			diengiaimucvuot = "";
		ckdhBean.setDiengiaiMucvuot(diengiaimucvuot);
		
		String activeTab = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("activeTab")));
		if (activeTab == null)
			activeTab = "";
		ckdhBean.setActiveTab(activeTab);
		
		String[] nppId = request.getParameterValues("nppIds");
		String str = "";
		if(nppId != null)
		{
			for(int i = 0; i < nppId.length; i++)
				str += nppId[i] + ",";
			if(str.length() > 0)
				str = str.substring(0, str.length() - 1);
		}
		ckdhBean.setNppIds(str);
		
		
		String[] spId = request.getParameterValues("spIds");
		//String[] spChietkhau = request.getParameterValues("spChietkhau");
		str = "";
		if (spId != null)
		{
//			Hashtable<String, String> htb_npp_chitieu = new Hashtable<String, String>();
//			for (int i = 0; i < spId.length; i++)
//			{
//				if(spChietkhau[i].length()>0)
//				htb_npp_chitieu.put(spId[i], spChietkhau[i].replaceAll(",", ""));
//				str += spId[i] + ",";
//				if(str.length() > 0)
//					str = str.substring(0, str.length() - 1);
//			}
//			ckdhBean.setChitieu(htb_npp_chitieu);
			for(int i = 0; i < spId.length; i++)
				str += spId[i] + ",";
			if(str.length() > 0)
				str = str.substring(0, str.length() - 1);
		}
		ckdhBean.setSpIds(str);
		
		String[] diengiaiMuc = request.getParameterValues("diengiaiMuc");
		ckdhBean.setGhichu(diengiaiMuc);
    	
		String[] tumuc = request.getParameterValues("tumuc");
		ckdhBean.setTumuc(tumuc);
		
		String[] denmuc = request.getParameterValues("denmuc");
		ckdhBean.setDenmuc(denmuc);
		
		String[] thuongSR = request.getParameterValues("chietkhau");
		ckdhBean.setChietkhauDh(thuongSR);
		
		String tungay= util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
		ckdhBean.setTungay(tungay);
		
		String denngay= util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
		ckdhBean.setDenngay(denngay);
	    
		String ngaysua = getDateTime();
    	ckdhBean.setNgaysua(ngaysua);
		
		String nguoisua = userId;
		ckdhBean.setNguoisua(nguoisua);
    		
 		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    
		if(action.equals("save"))
		{
			System.out.println("\n save \n ");
			if ( id == null){
				System.out.println("\n null \n ");
				if (!(ckdhBean.CreateCkdh())){				
					ckdhBean.setUserId(userId);
					ckdhBean.createRs();
					session.setAttribute("ckdhBean", ckdhBean);
					String nextJSP = "/AHF/pages/Center/KhaiBaoCKDatHangNew.jsp";
					response.sendRedirect(nextJSP);
				}else{
					IKhaibaoCKdathanglist obj = new KhaibaoCKdathanglist();
					obj.setUserId(userId);
					obj.init("");
					session.setAttribute("obj", obj);
						
					String nextJSP = "/AHF/pages/Center/KhaiBaoCKDatHang.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
				
				
			}else{
					if (!(ckdhBean.UpdateCkdh())){			
						ckdhBean.createRs();
						session.setAttribute("ckdhBean", ckdhBean);
						String nextJSP = "/AHF/pages/Center/KhaiBaoCKDatHangUpdate.jsp";
						response.sendRedirect(nextJSP);
					}
					else{
						IKhaibaoCKdathanglist obj = new KhaibaoCKdathanglist();
						obj.setUserId(userId);
						obj.init("");
						session.setAttribute("obj", obj);
							
						String nextJSP = "/AHF/pages/Center/KhaiBaoCKDatHang.jsp";
						response.sendRedirect(nextJSP);			    			    									
					}
				
			}
		}
		else
		{
			ckdhBean.setUserId(userId);
			session.setAttribute("ckdhBean", ckdhBean);
			ckdhBean.createRs();
			String nextJSP;
			if (id == null){			
				nextJSP = "/AHF/pages/Center/KhaiBaoCKDatHangNew.jsp";
			}else{
				nextJSP = "/AHF/pages/Center/KhaiBaoCKDatHangUpdate.jsp";   						
			}
			response.sendRedirect(nextJSP);
			
		}
	}
	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}
