package geso.dms.center.servlets.donvidoluong;

import geso.dms.center.beans.donvidoluong.IDonvidoluong;
import geso.dms.center.beans.donvidoluong.IDonvidoluongList;
import geso.dms.center.beans.donvidoluong.imp.Donvidoluong;
import geso.dms.center.beans.donvidoluong.imp.DonvidoluongList;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

 public class DonvidoluongNewSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
	public DonvidoluongNewSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		Utility util = new Utility();
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		IDonvidoluong dvdlBean = new Donvidoluong();	
		
		// Collecting data from DonvidoluongNew.jsp
		
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
    	
		String dvdl = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvdl")));
    	dvdlBean.setDiengiai(dvdl);

		String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
    	if (diengiai != null){
    		dvdlBean.setDiengiai(diengiai);
    	}else{
    		dvdlBean.setDiengiai("");
    	}
    	
    	String ngaytao = getDateTime();
		dvdlBean.setNgaytao(ngaytao);
		
		String ngaysua = ngaytao;
		dvdlBean.setNgaysua(ngaysua);
		
		String nguoitao = userId;
		dvdlBean.setNguoitao(userId);
		
		String nguoisua = nguoitao;
    	dvdlBean.setNguoisua(nguoisua);
    	
    	String trangthai;
    	if(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")))!= null)
			trangthai = "1";
		else
			trangthai = "0";
		dvdlBean.setTrangthai(trangthai);
			
		boolean error = false;
		if (dvdl.trim().length()> 0)
			dvdlBean.setDonvi(dvdl);
		else{
			dvdlBean.setMessage("Vui lòng nhập vào Đơn vị đo lường");
			error = true;
		}
		
		session.setAttribute("userId", util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"))));
		
		if (error){ //error in data entry
			session.setAttribute("dvdlBean", dvdlBean);
    		session.setAttribute("userId", userId);
    		String nextJSP = "/AHF/pages/Center/DonViDoLuongNew.jsp";
    		response.sendRedirect(nextJSP);
		}
		else{
			// userId is saved into session
			session.setAttribute("userId", nguoitao);
			//if there is any error when saving a Brand?
			if (!dvdlBean.saveNewDvdl()){			
				session.setAttribute("dvdlBean", dvdlBean);
	    		session.setAttribute("userId", userId);
				String nextJSP = "/AHF/pages/Center/DonViDoLuongNew.jsp";
	    		response.sendRedirect(nextJSP);
			}
			else{
			    IDonvidoluongList obj = new DonvidoluongList();
				session.setAttribute("obj", obj);
				session.setAttribute("userId", nguoisua);
					
				String nextJSP = "/AHF/pages/Center/DonViDoLuong.jsp";
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
