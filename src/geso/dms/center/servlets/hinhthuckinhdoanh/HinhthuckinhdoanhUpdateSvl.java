package geso.dms.center.servlets.hinhthuckinhdoanh;

import geso.dms.center.beans.hinhthuckinhdoanh.IHinhthuckinhdoanh;
import geso.dms.center.beans.hinhthuckinhdoanh.IHinhthuckinhdoanhList;
import geso.dms.center.beans.hinhthuckinhdoanh.imp.Hinhthuckinhdoanh;
import geso.dms.center.beans.hinhthuckinhdoanh.imp.HinhthuckinhdoanhList;
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

public class HinhthuckinhdoanhUpdateSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

	static final long serialVersionUID = 1L;
	   
	   
	public HinhthuckinhdoanhUpdateSvl() {
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
	    
	    String htkdId = util.getId(querystring);
	    out.println(htkdId);
	    IHinhthuckinhdoanh htkdBean = (IHinhthuckinhdoanh) new Hinhthuckinhdoanh();
	    htkdBean.setId(htkdId);
	    htkdBean.init();
		session.setAttribute("htkdBean", htkdBean);
		session.setAttribute("userId", userId);

		String nextJSP = "/AHF/pages/Center/HinhThucKinhDoanhUpdate.jsp";
    	response.sendRedirect(nextJSP);

	}  	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		Utility util = new Utility();
		
//		PrintWriter out = response.getWriter();
		IHinhthuckinhdoanh htkdBean = new Hinhthuckinhdoanh();	
		
		// Collecting data from DonViKinhDoanhUpdate.jsp
		
    	String dvdlId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvdlId")));
    	htkdBean.setId(dvdlId);

		String ma = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ma")));
		htkdBean.setMa(ma);

		String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		htkdBean.setDiengiai(diengiai);

		String ngaysua = getDateTime();
		htkdBean.setNgaysua(ngaysua);
		
		String nguoisua = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		htkdBean.setNguoisua(nguoisua);
    	
    	String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
    	if (trangthai == null)
    		trangthai = "0";
    	else
    		trangthai = "1";
    	htkdBean.setTrangthai(trangthai);
		
		boolean error = false;
		if (ma.trim().length()> 0)
			htkdBean.setMa(ma);
		else{
			htkdBean.setMessage("Vui lòng nhập vào Đơn vị đo lường");
			error = true;
		}

	
		if (error){ //Error in data entry
			session.setAttribute("userId", nguoisua);
			session.setAttribute("htkdBean", htkdBean);   		
    		String nextJSP = "/AHF/pages/Center/HinhThucKinhDoanhUpdate.jsp";
    		response.sendRedirect(nextJSP);
		}
		else{
			// userId is saved into session
			session.setAttribute("userId", nguoisua);
		
			//if there is any error when saving Business Unit

			if (!htkdBean.UpdateHtkd()){
				session.setAttribute("htkdBean", htkdBean);
				String nextJSP = "/AHF/pages/Center/HinhThucKinhDoanhUpdate.jsp";
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
