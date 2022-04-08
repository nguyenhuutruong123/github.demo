package geso.dms.center.servlets.asm;

import geso.dms.center.beans.asm.IAsm;
import geso.dms.center.beans.asm.IAsmList;
import geso.dms.center.beans.asm.imp.Asm;
import geso.dms.center.beans.asm.imp.AsmList;
import geso.dms.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ASMSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public ASMSvl() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utility util = new Utility();
		IAsmList obj = new AsmList();
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");

	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();

	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
		String param = "";
//		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "ASMSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String idlist = util.getId(querystring);
	    
	    if (action.equals("delete")){	   		
		    String bmId = idlist.split(";")[0];
		    //String vungId = idlist.split(";")[1];

	    	obj.Delete(bmId, "");
	    }	    
	    obj.setUserId(userId);
	    obj.init();

	    session.setAttribute("obj", obj);
	
		String nextJSP = "/AHF/pages/Center/ASM.jsp";
		response.sendRedirect(nextJSP);

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utility util = new Utility();
		IAsmList obj = new AsmList();
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
		if( Utility.CheckRuleUser( session,  request, response, "ASMSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
	    
	    if(action.equals("new")){
			IAsm asmBean = new Asm();
			
		    if (userId.length()==0)
		    	userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
		    		    	    
		    asmBean.init_New();

		    session.setAttribute("asmBean", asmBean);
		
			String nextJSP = "/AHF/pages/Center/ASMNew.jsp";
		
			response.sendRedirect(nextJSP);
	    	
			return;
	    }
	    obj.getDataSearch().clear();
	    String ten = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("asmTen")));
	    if(ten == null) ten = "";
	    obj.setTen(ten);
	    
	    String ma = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("asmMa")));
	    if(ma == null) ma = "";
	    obj.setMa(ma);
	        
	    String dienthoai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("DienThoai")));	    
	    if(dienthoai == null) dienthoai = "";
	    obj.setDienthoai(dienthoai);
	    
	    String kbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhId")));
	    if(kbhId == null) kbhId = "";
	    obj.setKbhId(kbhId);
	    
	    String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
	    if(dvkdId == null) dvkdId = "";
	    obj.setDvkdId(dvkdId);
	    
	    String kvId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kvId")));
	    if(kvId == null) 
	    	kvId = "";
	    obj.setKvId(kvId);
	    
	    String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("TrangThai")));
	    if(trangthai == null) trangthai = "2";
	    
	    obj.setTrangthai(trangthai);
	    String loaigiamsat = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaigiamsat")));   	
    	if (loaigiamsat == null)
    		loaigiamsat = "";  
    	if (loaigiamsat.equals("2"))
    		loaigiamsat = "";
    	obj.setThuviec(loaigiamsat);
	    userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    if(userId == null) userId = "";    
	    
	    obj.setUserId(userId);
	    obj.init();

		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
			
		String nextJSP = "/AHF/pages/Center/ASM.jsp";
		response.sendRedirect(nextJSP);
	}

}
