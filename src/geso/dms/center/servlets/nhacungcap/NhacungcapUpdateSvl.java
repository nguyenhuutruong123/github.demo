package geso.dms.center.servlets.nhacungcap;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import geso.dms.center.util.Utility;
import geso.dms.center.beans.nhacungcap.INhacungcap;
import geso.dms.center.beans.nhacungcap.imp.Nhacungcap;
import geso.dms.center.beans.nhacungcap.INhacungcapList;
import geso.dms.center.beans.nhacungcap.imp.NhacungcapList;

 public class NhacungcapUpdateSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
   
	public NhacungcapUpdateSvl() {
		super();
	}   	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		
	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    INhacungcap ncc = new Nhacungcap();

// Get data from NhacungcapUpdate.jsp	
	    
	    Utility util = new Utility();
	    
	    HttpSession session = request.getSession();
		
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
	    
	    String ten = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("TenNCC_long")));
    	String tenviettat = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("TenNCC_short")));
    	String diachi = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("DiaChi")));
    	String masothue = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("MaSoThue")));
		String ngaysua =  getDateTime();
		String nguoisua = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		String sotaikhoan = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sotaikhoan")));
		String dienthoai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dienthoai")));
		String fax = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("fax")));
		String nguoidaidien = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nguoidaidien")));
		String trangthai;
		if(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")))!= null)
			trangthai = "1";
		else
			trangthai = "0";
		ncc.setTrangthai(trangthai);
		
		String nccId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nccId")));

// Save data into Nhacungcap Bean		
		ncc.setId(nccId);
		ncc.setNgaysua(ngaysua);
		ncc.setNguoisua(nguoisua);
		ncc.setTrangthai(trangthai);
		ncc.setSotaikhoan(sotaikhoan);
		ncc.setDienthoai(dienthoai);
		ncc.setFax(fax);
		ncc.setNguoidaidien(nguoidaidien);
		boolean error = false;
		if (masothue.trim().length()> 0)
			ncc.setMasothue(masothue);
		else{
			ncc.setMessage("Vui lòng nhập vào mã số thuế cùa Nhà Cung Cấp");
			error = true;
		}

		if (diachi.trim().length()> 0)
			ncc.setDiachi(diachi);
		else{
			ncc.setMessage("Vui lòng nhập vào địa chỉ của Nhà Cung Cấp");
			error = true;
		}

		if (tenviettat.trim().length()> 0)
			ncc.setTenviettat(tenviettat);
		else{
			ncc.setMessage("Vui lòng nhập vào tên viết tắt của Nhà Cung Cấp");
			error = true;
		}
		
		if (ten.trim().length()> 0)
			ncc.setTen(ten);
		else{
			ncc.setMessage("Vui lòng nhập vào tên của Nhà Cung Cấp");
			error = true;
		}

		session = request.getSession();
		session.setAttribute("userId", nguoisua);
		if (error){
    		session.setAttribute("ncc", ncc);
    		String nextJSP = "/AHF/pages/Center/NhaCungCapUpdate.jsp";
    		response.sendRedirect(nextJSP);
		}
		else{
			if (!ncc.UpdateNcc()){
				session.setAttribute("ncc", ncc);    		
	    		String nextJSP = "/AHF/pages/Center/NhaCungCapUpdate.jsp";
	    		response.sendRedirect(nextJSP);
			}
			else{
		    	INhacungcapList obj = new NhacungcapList();	    	
		    	
		    	obj.init();
				
		    	//Data object is saved into session
		    	session.setAttribute("obj", obj);
								
		    	String nextJSP = "/AHF/pages/Center/NhaCungCap.jsp";
		    	response.sendRedirect(nextJSP);
			}
		}
	} 	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
}