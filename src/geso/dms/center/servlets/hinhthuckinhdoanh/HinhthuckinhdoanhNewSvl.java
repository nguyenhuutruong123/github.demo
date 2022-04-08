package geso.dms.center.servlets.hinhthuckinhdoanh;

import geso.dms.center.beans.hinhthuckinhdoanh.IHinhthuckinhdoanh;
import geso.dms.center.beans.hinhthuckinhdoanh.IHinhthuckinhdoanhList;
import geso.dms.center.beans.hinhthuckinhdoanh.imp.Hinhthuckinhdoanh;
import geso.dms.center.beans.hinhthuckinhdoanh.imp.HinhthuckinhdoanhList;
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

public class HinhthuckinhdoanhNewSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

	static final long serialVersionUID = 1L;
	   
	public HinhthuckinhdoanhNewSvl() {
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
		IHinhthuckinhdoanh htkdBean = new Hinhthuckinhdoanh();	
		
		// Collecting data from DonvidoluongNew.jsp
		
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
    	
		String ma = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ma")));
		htkdBean.setMa(ma);

		String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
    	if (diengiai != null){
    		htkdBean.setDiengiai(diengiai);
    	}else{
    		htkdBean.setDiengiai("");
    	}
    	
    	String ngaytao = getDateTime();
    	htkdBean.setNgaytao(ngaytao);
		
		String ngaysua = ngaytao;
		htkdBean.setNgaysua(ngaysua);
		
		String nguoitao = userId;
		htkdBean.setNguoitao(userId);
		
		String nguoisua = nguoitao;
		htkdBean.setNguoisua(nguoisua);
    	
    	String trangthai;
    	if(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")))!= null)
			trangthai = "1";
		else
			trangthai = "0";
    	htkdBean.setTrangthai(trangthai);
			
		boolean error = false;
		if (ma.trim().length()> 0)
			htkdBean.setMa(ma);
		else{
			htkdBean.setMessage("Vui lòng nhập vào mã hình thức kinh doanh");
			error = true;
		}
		
		session.setAttribute("userId", util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"))));
		
		if (error){ //error in data entry
			session.setAttribute("htkdBean", htkdBean);
    		session.setAttribute("userId", userId);
    		String nextJSP = "/AHF/pages/Center/HinhThucKinhDoanhNew.jsp";
    		response.sendRedirect(nextJSP);
		}
		else{
			// userId is saved into session
			session.setAttribute("userId", nguoitao);
			//if there is any error when saving a Brand?
			if (!htkdBean.saveNewHtkd()){			
				session.setAttribute("htkdBean", htkdBean);
	    		session.setAttribute("userId", userId);
				String nextJSP = "/AHF/pages/Center/HinhThucKinhDoanhNew.jsp";
	    		response.sendRedirect(nextJSP);
			}
			else{
			    IHinhthuckinhdoanhList obj = new HinhthuckinhdoanhList();
				session.setAttribute("obj", obj);
				session.setAttribute("userId", nguoisua);
					
				String nextJSP = "/AHF/pages/Center/HinhThucKinhDoanh.jsp";
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
