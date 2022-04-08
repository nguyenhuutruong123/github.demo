package geso.dms.center.servlets.donvikinhdoanh;

import geso.dms.center.beans.donvikinhdoanh.imp.Donvikinhdoanh;
import geso.dms.center.beans.donvikinhdoanh.IDonvikinhdoanh;
import geso.dms.center.beans.donvikinhdoanh.imp.DonvikinhdoanhList;
import geso.dms.center.beans.donvikinhdoanh.IDonvikinhdoanhList;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

 public class DvkdUpdateSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   	static final long serialVersionUID = 1L;

	public DvkdUpdateSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
//		PrintWriter out = response.getWriter();
		IDonvikinhdoanh dvkdBean = new Donvikinhdoanh();	
		
		// Collecting data from DonViKinhDoanhUpdate.jsp
		
		Utility util = new Utility();
		
    	String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
    	dvkdBean.setId(dvkdId);

		String dvkd = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkd")));
    	dvkdBean.setDvkd(dvkd);

		String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
    	dvkdBean.setDiengiai(diengiai);

    	String ngaysua = getDateTime();
		dvkdBean.setNgaysua(ngaysua);
		
		String nguoisua = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
    	dvkdBean.setNguoisua(nguoisua);
    	
    	String trangthai;
    	if(util.antiSQLInspection(request.getParameter("trangthai"))!= null && util.antiSQLInspection(request.getParameter("trangthai")).trim().length()>0)
			trangthai = "1";
		else
			trangthai = "0";
		dvkdBean.setTrangthai(trangthai);
		
		String[] nccSelected = request.getParameterValues("nccId");
		dvkdBean.setNccSelected(nccSelected);
		
		boolean error = false;
		if (dvkd.trim().length()> 0)
			dvkdBean.setDvkd(dvkd);
		else{
			dvkdBean.setMessage("Vui lòng nhập vào Đơn vị kinh doanh");
			error = true;
		}
		
		if (nccSelected == null){
			dvkdBean.setMessage("Vui lòng nhập vào Nhà Cung Cấp");
			error = true;
		}
		
		if (error){ //Error in data entry
			dvkdBean.init();
			session.setAttribute("userId", nguoisua);
			session.setAttribute("dvkdBean", dvkdBean);   		
    		String nextJSP = "/AHF/pages/Center/DonViKinhDoanhUpdate.jsp";
    		response.sendRedirect(nextJSP);
		}
		else{
			// userId is saved into session
			session.setAttribute("userId", nguoisua);
		
			//if there is any error when saving Business Unit
			try{
			if (!dvkdBean.UpdateDvkd()){System.out.println("update hok thanh cong!");
				session.setAttribute("dvkdBean", dvkdBean);
				String nextJSP = "/AHF/pages/Center/DonViKinhDoanhUpdate.jsp";
	    		response.sendRedirect(nextJSP);
	    		}
			else{
					IDonvikinhdoanhList obj = new DonvikinhdoanhList();
									
					// Data is saved into session
					session.setAttribute("obj", obj);
																		
					String nextJSP = "/AHF/pages/Center/DonViKinhDoanh.jsp";
					response.sendRedirect(nextJSP);
							
		}
			}catch (Exception e) {e.toString();}
			
		}
		
	}   	  	    
	

	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	
}