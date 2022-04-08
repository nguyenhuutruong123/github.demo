package geso.dms.center.servlets.donvikinhdoanh;

import geso.dms.center.beans.donvikinhdoanh.IDonvikinhdoanh;
import geso.dms.center.beans.donvikinhdoanh.imp.Donvikinhdoanh;
import geso.dms.center.beans.donvikinhdoanh.IDonvikinhdoanhList;
import geso.dms.center.beans.donvikinhdoanh.imp.DonvikinhdoanhList;
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

 public class DvkdNewSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;

   public DvkdNewSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		IDonvikinhdoanh dvkdBean = new Donvikinhdoanh();	
		
		// Collecting data from DonViKinhDoanhNew.jsp
		Utility util = new Utility();
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
    	String dvkd = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkd")));
    	dvkdBean.setDvkd(dvkd);
    	
    	String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
    	dvkdBean.setDiengiai(diengiai);

    	String ngaytao = getDateTime();
		dvkdBean.setNgaytao(ngaytao);
		
		String ngaysua = ngaytao;
		dvkdBean.setNgaysua(ngaysua);
		
		String nguoitao = userId;
		dvkdBean.setNguoitao(userId);
		
		String nguoisua = nguoitao;
    	dvkdBean.setNguoisua(nguoisua);
    	
    	String trangthai;
    	if(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")))!= null)
			trangthai = "1";
		else
			trangthai = "0";
		dvkdBean.setTrangthai(trangthai);
		
		String[] nccSelected = request.getParameterValues("nccId");
		dvkdBean.setNccSelected(nccSelected);
		
		boolean error = false;
		if (dvkd.trim().length()== 0){
			dvkdBean.setMessage("Vui lòng nhập vào Đơn vị kinh doanh");
			error = true;
		}

		if (nccSelected == null){
			dvkdBean.setMessage("Vui lòng nhập vào Nhà Cung Cấp");
			error = true;
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("userId", util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"))));
		
		if (error){ //error in data entry    		
//			dvkdBean.init();
			session.setAttribute("dvkdBean", dvkdBean);			
    		session.setAttribute("userId", userId);
    		String nextJSP = "/AHF/pages/Center/DonViKinhDoanhNew.jsp";
    		response.sendRedirect(nextJSP);
		}
		else{
			// userId is saved into session
			session.setAttribute("userId", nguoitao);
			
			//if there is any error when saving Business Unit
			if (!dvkdBean.saveNewDvkd()){		
				dvkdBean.setMessage("Ban da bi trung ten don vi kinh doanh roi");
				
				session.setAttribute("dvkdBean", dvkdBean);
	    		session.setAttribute("userId", userId);
				String nextJSP = "/AHF/pages/Center/DonViKinhDoanhNew.jsp";
	    		response.sendRedirect(nextJSP);
			}
			else{
				IDonvikinhdoanhList obj = new DonvikinhdoanhList();		    	
		    	session.setAttribute("obj", obj);
		    	session.setAttribute("userId", userId);
				
		    	String nextJSP = "/AHF/pages/Center/DonViKinhDoanh.jsp";
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