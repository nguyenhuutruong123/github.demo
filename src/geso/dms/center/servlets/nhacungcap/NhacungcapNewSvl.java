package geso.dms.center.servlets.nhacungcap;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import geso.dms.center.beans.nhacungcap.imp.Nhacungcap;
import geso.dms.center.beans.nhacungcap.imp.NhacungcapList;
import geso.dms.center.beans.nhacungcap.INhacungcap;
import geso.dms.center.beans.nhacungcap.INhacungcapList;
import geso.dms.center.util.Utility;

 public class NhacungcapNewSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
	public NhacungcapNewSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
		PrintWriter out = response.getWriter();
		INhacungcap nccBean = new Nhacungcap();	
		
		Utility util = new Utility();
		
		HttpSession session = request.getSession();
		
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		
		// Collecting data from NhaCungCapNew.jsp
    	String tenncc = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("TenNCC_long")));
    	String tenviettat = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("TenNCC_short")));
    	String diachi = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("DiaChi")));
    	String masothue = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("MaSoThue")));
		String ngaytao = getDateTime();
		String ngaysua = ngaytao;
		String nguoitao = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		String nguoisua = nguoitao;
		String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
		String sotaikhoan = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sotaikhoan")));
		String dienthoai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dienthoai")));
		String fax = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("fax")));
		String nguoidaidien = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nguoidaidien")));
		if(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")))!= null)
			trangthai = "1";
		else
			trangthai = "0";
		nccBean.setTrangthai(trangthai);
				
		boolean error = false;
		if (masothue.trim().length()> 0)
			nccBean.setMasothue(masothue);
		else{
			nccBean.setMessage("Vui lòng nhập mã số thuế của Nhà Cung Cấp");
			error = true;
		}

		if (diachi.trim().length()> 0)
			nccBean.setDiachi(diachi);
		else{
			nccBean.setMessage("Vui lòng nhập vào địa chỉ của Nhà Cung Cấp");
			error = true;
		}

		if (tenviettat.trim().length()> 0)
			nccBean.setTenviettat(tenviettat);
		else{
			nccBean.setMessage("Vui lòng nhập vào tên viết tắt của Nhà Cung Cấp");
			error = true;
		}
		
		if (tenncc.trim().length()> 0)
			nccBean.setTen(tenncc);
		else{
			nccBean.setMessage("Vui lòng nhập vào tên Nhà Cung Cấp");
			error = true;
			out.println("Vui lòng nhập vào tên Nhà Cung Cấp");
		}
		session = request.getSession();
		session.setAttribute("userId", nguoitao);
		if (error){
    		session.setAttribute("nccBean", nccBean);   		
    		String nextJSP = "/AHF/pages/Center/NhaCungCapNew.jsp";
    		response.sendRedirect(nextJSP);
		}
		else{
			// Prepare for saving the new distributor
			nccBean.setNgaytao(ngaytao);
			nccBean.setNgaysua(ngaysua);
			nccBean.setNguoitao(nguoitao);
			nccBean.setNguoisua(nguoisua);
			nccBean.setTrangthai(trangthai);
			nccBean.setSotaikhoan(sotaikhoan);
			nccBean.setDienthoai(dienthoai);
			nccBean.setFax(fax);
			nccBean.setNguoidaidien(nguoidaidien);
			//if there is any error when saving distributor
			if ( !nccBean.saveNewNcc()){
				nccBean.setMessage("Ten nha cung cap da bi trung");
	    		session.setAttribute("nccBean", nccBean);
	    		String nextJSP = "/AHF/pages/Center/NhaCungCapNew.jsp";
	    		response.sendRedirect(nextJSP);
			}
			else{
				// Collect all Supplier
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