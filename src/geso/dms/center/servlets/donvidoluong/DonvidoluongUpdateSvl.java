package geso.dms.center.servlets.donvidoluong;

import geso.dms.center.beans.donvidoluong.IDonvidoluong;
import geso.dms.center.beans.donvidoluong.IDonvidoluongList;
import geso.dms.center.beans.donvidoluong.imp.Donvidoluong;
import geso.dms.center.beans.donvidoluong.imp.DonvidoluongList;
import geso.dms.center.db.sql.dbutils;
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

 public class DonvidoluongUpdateSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
   
	public DonvidoluongUpdateSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    Utility util = new Utility();
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();

	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String dvdlId = util.getId(querystring);
	    out.println(dvdlId);
	    IDonvidoluong dvdlBean = (IDonvidoluong) new Donvidoluong();
	    dvdlBean.setId(dvdlId);
		dvdlBean.init();
		session.setAttribute("dvdlBean", dvdlBean);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Center/DonViDoLuongUpdate.jsp";
		if(action.equals("display")) {
			nextJSP = "/AHF/pages/Center/DonViDoLuongDisplay.jsp";
		}
		

    	response.sendRedirect(nextJSP);

	}  	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		Utility util = new Utility();
		
//		PrintWriter out = response.getWriter();
		IDonvidoluong dvdlBean = new Donvidoluong();	
		
		// Collecting data from DonViKinhDoanhUpdate.jsp
		
    	String dvdlId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvdlId")));
    	dvdlBean.setId(dvdlId);

		String dvdl = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvdl")));
		dvdlBean.setDonvi(dvdl);

		String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		dvdlBean.setDiengiai(diengiai);

		String ngaysua = getDateTime();
    	dvdlBean.setNgaysua(ngaysua);
		
		String nguoisua = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		dvdlBean.setNguoisua(nguoisua);
    	
    	String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
    	if (trangthai == null)
    		trangthai = "0";
    	else
    		trangthai = "1";
    	dvdlBean.setTrangthai(trangthai);
		
		boolean error = false;
		if (dvdl.trim().length()> 0)
			dvdlBean.setDonvi(dvdl);
		else{
			dvdlBean.setMessage("Vui lòng nhập vào Đơn vị đo lường");
			error = true;
		}

	
		if (error){ //Error in data entry
			session.setAttribute("userId", nguoisua);
			session.setAttribute("dvdlBean", dvdlBean);   		
    		String nextJSP = "/AHF/pages/Center/DonViDoLuongUpdate.jsp";
    		response.sendRedirect(nextJSP);
		}
		else{
			// userId is saved into session
			session.setAttribute("userId", nguoisua);
		
			//if there is any error when saving Business Unit

			if (!dvdlBean.UpdateDvdl()){
				session.setAttribute("dvdlBean", dvdlBean);
				String nextJSP = "/AHF/pages/Center/DonViDoLuongUpdate.jsp";
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
