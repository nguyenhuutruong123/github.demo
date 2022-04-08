package geso.dms.distributor.servlets.hoadontaichinhNPP;

import geso.dms.distributor.beans.hoadontaichinhNPP.IErpHoadontaichinhNPP;
import geso.dms.distributor.beans.hoadontaichinhNPP.IErpHoadontaichinhNPPList;
import geso.dms.distributor.beans.hoadontaichinhNPP.imp.ErpHoadontaichinhNPP;
import geso.dms.distributor.beans.hoadontaichinhNPP.imp.ErpHoadontaichinhNPPList;
import geso.dms.distributor.util.Utility;
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

public class ErpHoadontaichinhNPPUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
    public ErpHoadontaichinhNPPUpdateSvl() 
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
		    IErpHoadontaichinhNPP lsxBean = new ErpHoadontaichinhNPP(id);
		    lsxBean.setUserId(userId); 
		    
		    String nextJSP = "";
		    
    		lsxBean.init();
    		
    		if(!querystring.contains("display"))
    		{
    			nextJSP = "/AHF/pages/Distributor/ErpHoaDonTaiChinhNPPUpdate.jsp";	
    		}
    		else
    		{
    			nextJSP = "/AHF/pages/Distributor/ErpHoaDonTaiChinhNPPDisplay.jsp";
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
			IErpHoadontaichinhNPP lsxBean;
			
			Utility util = new Utility();	
			String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
		    if(id == null)
		    {  	
		    	lsxBean = new ErpHoadontaichinhNPP("");
		    }
		    else
		    {
		    	lsxBean = new ErpHoadontaichinhNPP(id);
		    }
	
		    lsxBean.setUserId(userId);
		    
		    String ngayxuatHD = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngayxuat")));
		    if(ngayxuatHD == null || ngayxuatHD.trim().length() <= 0)
		    	ngayxuatHD = getDateTime();
		    lsxBean.setNgayxuatHD(ngayxuatHD);
		    System.out.println("Ngày xuất hóa đơn  "+ ngayxuatHD);
		    System.out.println("Ngày xuất hóa đơn .... "+ lsxBean.getNgayxuatHD());
		    
		    String ngayghinhan = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngayghinhan")));
		    if(ngayghinhan == null || ngayghinhan.trim().length() <= 0)
		    	ngayghinhan = getDateTime();
		    lsxBean.setNgayghinhanCN(ngayghinhan);
		    
		    String ghichu = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ghichu")));
		    if(ghichu == null)
		    	ghichu = "";
		    lsxBean.setGhichu(ghichu);
		    
		    String nguoimua = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nguoimuahang")));
		    if(nguoimua == null)
		    	nguoimua = "";
		    lsxBean.setNguoimua(nguoimua);
		    
		    String innguoimua = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("innguoimuahang")));
		    if(innguoimua == null || innguoimua.trim().length() <= 0)
		    {
		    	innguoimua = "0";
		    }else
		    {
		    	innguoimua = "1";
		    }
		    lsxBean.setInNguoimua(innguoimua);
		    
		    String loaiXHD = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaiXHD")));
		    if(loaiXHD == null)
		    	loaiXHD = "";
		    lsxBean.setLoaiXHD(loaiXHD);
		    
		    String khId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khId")));
		    if(khId == null)
		    	khId = "";
		    lsxBean.setKhId(khId);
		    
		    String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
		    if(nppId == null)
		    	nppId = "";
		    lsxBean.setNppId(nppId);
		    
		    String kyhieuhoadon = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kyhieuhoadon")));
		    if(kyhieuhoadon == null)
		    	kyhieuhoadon = "";
		    lsxBean.setKyhieuhoadon(kyhieuhoadon);
		    
		    String sohoadon = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sohoadon")));
		    if(sohoadon == null)
		    	sohoadon = "";
		    lsxBean.setSohoadon(sohoadon);
		    
		    String hinhthuctt = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("hinhthuctt")));
		    if(hinhthuctt == null)
		    	hinhthuctt = "";
		    lsxBean.setHinhthucTT(hinhthuctt);
		    
		    
		    String bvat = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tongtien")));
		    if(bvat == null)
		    	bvat = "0";
		    lsxBean.setTongtienBVAT(bvat);
		    
		    String tongchietkhau = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tongchietkhau")));
		    if(tongchietkhau == null)
		    	tongchietkhau = "0";
		    lsxBean.setTongCK(tongchietkhau);
		    
		    String tienvat = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tienvat")));
		    if(tienvat == null)
		    	tienvat = "0";
		    lsxBean.setTongVAT(tienvat);
		    
		    String tiensauvat = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tiensauvat")));
		    if(tiensauvat == null)
		    	tiensauvat = "0";
		    lsxBean.setTongtienAVAT(tiensauvat);
		    
		    String mavv = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("mavv")));
		    if(mavv == null)
		    	mavv = "";
		    lsxBean.setMavuviec(mavv);
		    
		    String[] ddhid = request.getParameterValues("ddhid");
		    
		    String ddh = "";
			if (ddhid != null) 
			{
				for(int i = 0; i < ddhid.length; i++)
				{
					ddh += ddhid[i] + ",";
				}
				
				if(ddh.trim().length() > 0)
				{
					ddh = ddh.substring(0, ddh.length() - 1);
					lsxBean.setDondathangId(ddh);
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
			
			String[] spDongia = request.getParameterValues("spDongia");
			lsxBean.setSpDongia(spDongia);
						
			String[] spChietkhau = request.getParameterValues("spChietkhau");
			lsxBean.setSpChietkhau(spChietkhau);
			
			String[] soluong = request.getParameterValues("soluong");
			lsxBean.setSpSoluong(soluong);
			
			String[] spLoai = request.getParameterValues("spLoai");
			lsxBean.setSpLoai(spLoai);
			
			String[] spScheme = request.getParameterValues("scheme");
			lsxBean.setSpScheme(spScheme);
			
			String[] spVat = request.getParameterValues("spVat");
			lsxBean.setSpVat(spVat);
			
			String[] spTienthue = request.getParameterValues("spTienthue");
			lsxBean.setSpTienthua(spTienthue);
			
			//THEM CAC LOAI CHIET KHAU
			String[] dhCK_diengiai = request.getParameterValues("dhCK_diengiai");
			lsxBean.setDhck_Diengiai(dhCK_diengiai);
			String[] dhCK_giatri = request.getParameterValues("dhCK_giatri");
			lsxBean.setDhck_giatri(dhCK_giatri);
			String[] dhCK_loai = request.getParameterValues("dhCK_loai");
			lsxBean.setDhck_loai(dhCK_loai);
			
		    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		    
		    Hashtable<String, String> sanpham_soluong = new Hashtable<String, String>();
		    if(spMa!=null)
		    {
				for(int i = 0; i < spMa.length; i++ )
				{
					System.out.println("---SP MA LA: " + spMa[i]);
					String temID = spMa[i];
					
					String[] spSOLO = request.getParameterValues(temID + "_spSOLO");
					String[] soLUONGXUAT = request.getParameterValues(temID + "_spSOLUONG");
					
					String[] spNgayHetHan = request.getParameterValues(temID + "_spNGAYHETHAN");
					
					if(soLUONGXUAT != null)
					{
						for(int j = 0; j < soLUONGXUAT.length; j++ )
						{
							if(soLUONGXUAT[j] != null && soLUONGXUAT[j].trim().length() > 0)
							{
								sanpham_soluong.put(spMa[i] + "__" + spSOLO[j]+ "__" + spNgayHetHan[j], soLUONGXUAT[j].replaceAll(",", "") );								
							}
						}
					}
				}
		    }
				lsxBean.setSanpham_Soluong(sanpham_soluong);
		    
		    
		    
			if(action.equals("save"))
			{	
				if(id == null)
				{
					if(!lsxBean.create())
					{
						lsxBean.createRs();
	    		    	session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/AHF/pages/Distributor/ErpHoaDonTaiChinhNPPNew.jsp";
	    				
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpHoadontaichinhNPPList obj = new ErpHoadontaichinhNPPList();
						
						obj.setUserId(userId);
						obj.init("");  
				    	session.setAttribute("obj", obj);  	
			    		session.setAttribute("userId", userId);
			    		
			    		String nextJSP = "/AHF/pages/Distributor/ErpHoaDonTaiChinhNPP.jsp";
			    		response.sendRedirect(nextJSP);
					}
				}
				else
				{
					if(!lsxBean.update())
					{
						lsxBean.init();
						lsxBean.setSohoadon(sohoadon);
						session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/AHF/pages/Distributor/ErpHoaDonTaiChinhNPPUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
/*						IErpHoadontaichinhNPPList obj = new ErpHoadontaichinhNPPList();
						
					    obj.setUserId(userId);
					    obj.init("");
						session.setAttribute("obj", obj);							
						String nextJSP = "/AHF/pages/Distributor/ErpHoaDonTaiChinhNPP.jsp";
						response.sendRedirect(nextJSP);*/
						
						lsxBean.init();
						lsxBean.setSohoadon(sohoadon);
						session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/AHF/pages/Distributor/ErpHoaDonTaiChinhNPPUpdate.jsp";
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
	    				String nextJSP = "/AHF/pages/Distributor/ErpHoaDonTaiChinhNPPUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpHoadontaichinhNPPList obj = new ErpHoadontaichinhNPPList();
						
					    obj.setUserId(userId);
					    obj.init("");
						session.setAttribute("obj", obj);							
						String nextJSP = "/AHF/pages/Distributor/ErpHoaDonTaiChinhNPP.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				else
				{
					String nextJSP ="";
				
					session.setAttribute("userId", userId);
					
					//BO NHUNG THONG TIN CUA HOA DON CU
					lsxBean.setTongtienBVAT("0");
					lsxBean.setTongCK("0");
					lsxBean.setTongVAT("0");
					lsxBean.setTongtienBVAT("0");
					lsxBean.setSpMa(null);
					
					if(id == null)
					{
						lsxBean.createRs();	
						session.setAttribute("lsxBean", lsxBean);					
						nextJSP = "/AHF/pages/Distributor/ErpHoaDonTaiChinhNPPNew.jsp";
					}
					if(id != null)
					{
						lsxBean.loadContents();
						session.setAttribute("lsxBean", lsxBean);
						nextJSP = "/AHF/pages/Distributor/ErpHoaDonTaiChinhNPPUpdate.jsp";
					}
						 					
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
