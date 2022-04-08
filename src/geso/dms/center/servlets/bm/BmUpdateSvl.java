package geso.dms.center.servlets.bm;

import geso.dms.center.beans.bm.IBm;
import geso.dms.center.beans.bm.imp.Bm;
import geso.dms.center.beans.bm.IBmList;
import geso.dms.center.beans.bm.imp.BmList;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class BmUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BmUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utility util = new Utility();

		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");

	    HttpSession session = request.getSession();

	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);

		IBm bmBean = new Bm();
		
	    System.out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    System.out.println(action);
	    
	    String Id = util.getId(querystring);

	    bmBean.setId(Id);
	    
	    bmBean.init_Update();
		// Save data into session
		session.setAttribute("bmBean", bmBean);
	
		String nextJSP = "/AHF/pages/Center/BMUpdate.jsp";
		if(querystring.indexOf("display") > 0)
        	nextJSP = "/AHF/pages/Center/BMDisplay.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utility util = new Utility();
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");

	    HttpSession session = request.getSession();
	    IBm bmBean = new Bm();
	    
	    String Id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Id")));
	    if(Id == null) Id = "";
	    bmBean.setId(Id);

	    String ten = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("bmTen")));
	    if(ten == null) ten = "";
	    bmBean.setTen(ten);
	    	    
	    String kbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhId")));
	    if(kbhId == null) kbhId = "";
	    bmBean.setKbhId(kbhId);
	    
	    String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
	    if(dvkdId == null) dvkdId = "";
	    bmBean.setDvkdId(dvkdId);
	    
	    String ttppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("TTPP")));
	    if(ttppId == null) ttppId = "";
	    bmBean.setTtppId(ttppId);
	    
	    String diachi = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("DiaChi")));	    
	    if(diachi == null) diachi = "";
	    bmBean.setDiachi(diachi);

	    String email = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Email")));	    
	    if(email == null) email = "";
	    bmBean.setEmail(email);

	    String dienthoai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("DienThoai")));	    
	    if(dienthoai == null) dienthoai = "";
	    bmBean.setDienthoai(dienthoai);

	    String smartid = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("smartid")));	    
	    if(smartid == null) smartid = "";
	    bmBean.setSmartid(smartid);
	    
	    String[] vungId = request.getParameterValues("vungId");
	    bmBean.setVungId(vungId);
	    
	    String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("TrangThai")));
	    if(trangthai == null) trangthai = "0";
	    else trangthai = "1";
	    bmBean.setTrangthai(trangthai);
	    
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    if(userId == null) userId = "";    
	    bmBean.setUserId(userId);
	    
	    String action = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));
	    if(action == null) action = ""; 
	    
	    if(action.equals("save")){
	    	if(bmBean.Save(request)){
	    	    IBmList obj = new BmList();
	    	    obj.setUserId(userId);
	    		obj.init();

	    	    session.setAttribute("obj", obj);
	    	
	    		String nextJSP = "/AHF/pages/Center/BM.jsp";
	    		response.sendRedirect(nextJSP);	    		
	    	}
	    	else
	    	{
	    		bmBean.init_New();
	    		// Save data into session
	    		session.setAttribute("bmBean", bmBean);
	    		String nextJSP = "/AHF/pages/Center/BMNew.jsp";
	    		response.sendRedirect(nextJSP);
	    	}
	    }
	}
}
