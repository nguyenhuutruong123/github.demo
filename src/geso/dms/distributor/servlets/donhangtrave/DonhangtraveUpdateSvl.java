package geso.dms.distributor.servlets.donhangtrave;

import geso.dms.distributor.beans.donhangtrave.*;
import geso.dms.distributor.beans.donhangtrave.imp.*;

import geso.dms.distributor.db.sql.dbutils;
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

public class DonhangtraveUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	IDonhangtrave dhtvBean;
	List<ISanpham> spList;
	PrintWriter out; 
	
    public DonhangtraveUpdateSvl() {
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
        dhtvBean.setUserId(userId); //phai co UserId truoc khi Init
        dhtvBean.init();
     
        session.setAttribute("dhtvBean", dhtvBean);
        session.setAttribute("nppId", dhtvBean.getNppId());
        
        session.setAttribute("khId", dhtvBean.getKhId());
        session.setAttribute("dhId", dhtvBean.getDonhangId());
        session.setAttribute("ddkdId", dhtvBean.getDdkdId());
        session.setAttribute("ngaydonhang", dhtvBean.getNgaygiaodich() );
        
        String nextJSP = "/AHF/pages/Distributor/DonHangTraVeUpdate.jsp";
        
        if (request.getQueryString().indexOf("display") >= 0 ) 
        	nextJSP = "/AHF/pages/Distributor/DonHangTraVeDisplay.jsp";
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
	    if (id == null){  	
	    	dhtvBean = new Donhangtrave("");
	    }else{
	    	dhtvBean = new Donhangtrave(id);
	    }
	    
		String userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
		dhtvBean.setUserId(userId);
		
		String dhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dhId"));
		if (dhId == null)
			dhId = "";
		System.out.println("dhId" +dhId);
		dhtvBean.setDonhangId(dhId);
		session.setAttribute("dhId", dhId);
		
		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
		if (nppId == null)
			nppId = "";
		System.out.println("nppId" +nppId);
		dhtvBean.setNppId(nppId);
		session.setAttribute("nppId", nppId);
		
    	String ngaygd = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
    	if (ngaygd == null || ngaygd == "")
			ngaygd = this.getDateTime();			
    	dhtvBean.setNgaygiaodich(ngaygd);
    	session.setAttribute("ngaydonhang", ngaygd );    	
    	
    	String gsbhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gsbhTen"));
    	if (gsbhId == null)
    		gsbhId = "";    	
    	dhtvBean.setGsbhId(gsbhId);
    	
    	String ddkdId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdTen"));
    	if (ddkdId == null)
    		ddkdId = "";    	
    	dhtvBean.setDdkdId(ddkdId);
    	session.setAttribute("ddkdId", ddkdId);
    	
    	String khId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khId"));
    	if (khId == null)
    		khId = "";    	
    	dhtvBean.setKhId(khId);
    	session.setAttribute("khId", khId);
    	
    	String smartId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("smartId"));
    	if (smartId == null)
    		smartId = "";    	
    	dhtvBean.setSmartId(smartId);

    	String khTen = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khTen"));
    	if (khTen == null)
    		khTen = "";    	
    	dhtvBean.setKhTen(khTen);
    	
    	String khoId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khoTen"));
    	if (khoId == null)
    		khoId = "";    	
    	dhtvBean.setKhoId(khoId);
    	session.setAttribute("khoId", khoId);
    	
    	String lydo = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("lydo"));
    	if (lydo == null)
    		lydo = "";    	
    	dhtvBean.setLydo(lydo);
    	String ghichutt = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ghichutt"));
    	if (ghichutt == null)
    		ghichutt = "";    	
    	dhtvBean.setGhichu(ghichutt);
    	System.out.println("ghichutt" +ghichutt);
    	String chietkhau = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ChietKhau"));
    	if (chietkhau == null)
    		chietkhau = "";    	
    	dhtvBean.setChietkhau(chietkhau);
    	
    	String VAT = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("VAT"));
    	if (VAT == null)
    		VAT = "";    	
    	dhtvBean.setVAT(VAT);
		
		String ngaysua = getDateTime();
    	dhtvBean.setNgaysua(ngaysua);
		
		String[] masp = request.getParameterValues("masp");
		String[] tensp = request.getParameterValues("tensp");
		String[] soluong = request.getParameterValues("soluong");
		String[] donvitinh = request.getParameterValues("donvitinh");
		String[] dongia = request.getParameterValues("dongia");
		//String[] spchietkhau = request.getParameterValues("spchietkhau");
		
		spList = new ArrayList<ISanpham>();
		if (masp != null)
		{		
			ISanpham sanpham = null;
			String[] param = new String[8];
			int m = 0;
			while(m < masp.length)
			{
				if (masp[m] != "")
				{
					if (soluong[m] != "") //chi them nhung san pham co so luong > 0
					{
						param[0] = "idSP";
						param[1] = masp[m];
						param[2] = tensp[m];
						param[3] = soluong[m];
						param[4] = donvitinh[m];
						param[5] = dongia[m];
						param[6] = "";
						param[7] = "";
						
						sanpham = new Sanpham(param);
						spList.add(sanpham);
					}
				}
				m++;
			}	
		}
				
		//Kiem tra nhung san pham khi chot donhangtrave(dk: soluong mat hang tra ve phai nho hon tong so luong khi dat)
		Hashtable<String, Integer> spThieuList = new Hashtable<String, Integer>();
		if (masp != null)
		{
			int m = 0;
			while(m < masp.length)
			{				
				dbutils db = dhtvBean.getDb();
				
				if (soluong[m].length() > 0)
				{
					String sl = "0";
					String query = "\n select sum(a.soluong) as soluongmathang " +
					"\n from donhang_sanpham a inner join donhang b on a.donhang_fk = b.pk_seq " +
					"\n where a.sanpham_fk = (select pk_seq from sanpham where ma = '" + masp[m].trim() + "') " +
					"\n and b.trangthai = '1' and b.npp_fk = '" + nppId + "' " +
					"\n and b.kho_fk = '" + khoId + "' and b.KHACHHANG_FK = '" + khId + "' " +
					"\n group by a.sanpham_fk ";					
					System.out.println("Query Check spThieuList: " + query);					
					ResultSet slspdadat = db.get(query);
					
					if (slspdadat != null)
					{
						try 
						{
							slspdadat.next();
							sl = slspdadat.getString("soluongmathang");
							slspdadat.close();
						} 
						catch(Exception e) {}
					}
					if (soluong[m] == "")
						soluong[m] = "0";
					
					if (Integer.parseInt(soluong[m].replace(",", "")) > Integer.parseInt(sl))
						spThieuList.put(masp[m], Integer.parseInt(sl));
				}
				m++;
			}
		}
		dhtvBean.setSpThieuList(spThieuList);
		
		boolean error = false;
		
		if (ngaygd.trim().length()== 0){
			dhtvBean.setMessage("Vui Long Chon Ngay Giao Dich");
			error = true;
		}
		
		if (ddkdId.trim().length()== 0){
			dhtvBean.setMessage("Vui Long Chon Dai Dien Kinh Doanh");
			error = true;
		}
		
		if (khId.trim().length()== 0){
			dhtvBean.setMessage("Vui Long Chon Khach Hang .......");
			error = true;
		}
			
 		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
 		System.out.println("action ____" + action);
		if (action.equals("save"))
		{
			if (id == null)
			{
				if (!(dhtvBean.CreateDhtv(spList)))
				{							
					dhtvBean.createRS();
					dhtvBean.setSpList(spList);
					session.setAttribute("dhtvBean", dhtvBean);
					String nextJSP = "/AHF/pages/Distributor/DonHangTraVeNew.jsp";
					response.sendRedirect(nextJSP);
				}else{
					IDonhangtraveList obj = new DonhangtraveList();
					obj.setUserId(userId);
					obj.init("");
					dhtvBean.setSpList(spList);
					session.setAttribute("obj", obj);
						
					String nextJSP = "/AHF/pages/Distributor/DonHangTraVe.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
				
			} else { 
				if (!(dhtvBean.UpdateDhtv(spList)))
				{
					dhtvBean.init();
					dhtvBean.setSpList(spList);
					dhtvBean.setKhId(khId);
					dhtvBean.setKhTen(khTen);
					dhtvBean.setDdkdId(ddkdId);
					session.setAttribute("dhtvBean", dhtvBean);
					String nextJSP = "/AHF/pages/Distributor/DonHangTraVeUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IDonhangtraveList obj = new DonhangtraveList();
					obj.setUserId(userId);
					obj.init("");
					session.setAttribute("obj", obj);
						
					String nextJSP = "/AHF/pages/Distributor/DonHangTraVe.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
			}
		}
		else
		{
			dhtvBean.setUserId(userId);
			dhtvBean.createRS();
			//dhtvBean.setSpList(spList);
			String nextJSP = "/AHF/pages/Distributor/DonHangTraVeNew.jsp";
			if (id != null)
			{
				nextJSP = "/AHF/pages/Distributor/DonHangTraVeUpdate.jsp";   						
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
