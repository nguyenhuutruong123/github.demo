package geso.dms.center.servlets.bm;

import geso.dms.center.util.Utility;
import geso.dms.center.beans.bm.imp.*;
import geso.dms.center.beans.bm.*;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class BmSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public BmSvl() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utility util = new Utility();
		IBmList obj = new BmList();
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");

	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();

	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);

	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String idlist = util.getId(querystring);
	    
	    //String vungId = util.getParameter(querystring, "vungId");
	    String bmId = util.getParameter(querystring, "delete");
	    
	    
	    
//		String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
//		if (view == null) {
//			view = "";
//		}
		
		String param = "";
//		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "BmSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
	    
	    
	    if (action.equals("delete")){	
	    	obj.Delete(bmId, "");
	    }	    
	    obj.setUserId(userId);
	    obj.init();

	    session.setAttribute("obj", obj);
	
		String nextJSP = "/AHF/pages/Center/BM.jsp";
		response.sendRedirect(nextJSP);

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utility util = new Utility();
		IBmList obj = new BmList();
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");

	    HttpSession session = request.getSession();

	    String querystring = request.getQueryString();

	    String userId = util.getUserId(querystring);

	    if (userId.length()==0)
	    	userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));
	    
	    System.out.println(action);
	    
		String param = "";
//		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "BmSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
	    
	    
	    if(action.equals("new")){
			IBm bmBean = new Bm();
			
		    if (userId.length()==0)
		    	userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
		    		    	    
		    bmBean.init_New();

		    session.setAttribute("bmBean", bmBean);
		
			String nextJSP = "/AHF/pages/Center/BMNew.jsp";
		
			response.sendRedirect(nextJSP);
	    	
			return;
	    }
	    
	    //search
	    obj.getDataSearch().clear();
		
	    String ten = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("bmTen")));
	    if(ten == null) ten = "";
	    obj.setTen(ten);
	    
	    String dienthoai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("DienThoai")));	    
	    if(dienthoai == null) dienthoai = "";
	    obj.setDienthoai(dienthoai);
	    
	    String kbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhId")));
	    if(kbhId == null) kbhId = "";
	    obj.setKbhId(kbhId);
	    
	    String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
	    if(dvkdId == null) dvkdId = "";
	    obj.setDvkdId(dvkdId);
	    
	    String vungId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")));
	    if(vungId == null) vungId = "";
	    obj.setVungId(vungId);
	    
	    String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("TrangThai")));
	    if(trangthai == null) trangthai = "2";

	    obj.setTrangthai(trangthai);
	    
	    userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    if(userId == null) userId = "";    
	    obj.setUserId(userId);
	    obj.init();

		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
			
		String nextJSP = "/AHF/pages/Center/BM.jsp";
		response.sendRedirect(nextJSP);
	}

}
