package geso.dms.center.servlets.khachhang;

import geso.dms.center.beans.khachhang.*;
import geso.dms.center.beans.khachhang.imp.*;
import geso.dms.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class KhachhangChuaPhanTuyenTTSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	int items = 1;
	int splittings = 1;
	
    public KhachhangChuaPhanTuyenTTSvl() 
    {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		
		IKhachhangList obj;
		PrintWriter out; 
		
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out  = response.getWriter();
   

	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	   	    
	    obj = new KhachhangList();
	    items = Integer.parseInt(getServletContext().getInitParameter("items"));
	    splittings = Integer.parseInt(getServletContext().getInitParameter("splittings"));
	    //String s1 = getServletConfig().getInitParameter("databaseType");
	    //System.out.println("items: "+s+"; config: " + s1);
	    obj.setUserId(userId);
	    
    	obj.setItems(items);
    	obj.setSplittings(splittings);
    	//System.out.println("items: "+items);
    	
	    obj.khChuaPhanTuyen("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Center/KhachHangChuaPhanTuyen.jsp";
		response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		
		IKhachhangList obj = new KhachhangList();
		obj.setItems(items);
    	obj.setSplittings(splittings);
		PrintWriter out; 
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out = response.getWriter();
	    Utility util = new Utility();
	    userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    
	    out.println(action); 
	          
	    if (action.equals("search"))
	    {	    
	    	
	    	String search = getSearchQuery(request, obj);
	    	//search = search + " and a.npp_fk='" + userId + "' order by a.ten";
	    	
	    	//obj = new KhachhangList(search);
	    	obj.setUserId(userId);
	    	obj.khChuaPhanTuyen(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/AHF/pages/Center/KhachHangChuaPhanTuyen.jsp");	    		    	
	    } else
	    if(action.equals("view") || action.equals("next") || action.equals("prev")){
	    	
	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
	    	obj.setUserId(userId);
	    	
	    	obj.khChuaPhanTuyen(search);
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	session.setAttribute("obj", obj);
	    	response.sendRedirect("/AHF/pages/Center/KhachHangChuaPhanTuyen.jsp");
	    }
	    else{

	    	
	    	
	    	obj.setUserId(userId);
		   
	    	
		    obj.khChuaPhanTuyen("");
			session.setAttribute("obj", obj);
					
			String nextJSP = "/AHF/pages/Center/KhachHangChuaPhanTuyen.jsp";
			response.sendRedirect(nextJSP);
	    }
	    
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IKhachhangList obj)
	{		
		Utility util = new Utility();
		String ten = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khTen")));
    	if ( ten == null)
    		ten = "";
    	obj.setTen(ten);
    	
    	String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
    	if ( nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
    	String hchId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("hchTen")));
    	if (hchId == null)
    		hchId = "";    	
    	obj.setHchId(hchId);
    	
    	String kbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhTen")));
    	if (kbhId == null)
    		kbhId = "";    	
    	obj.setKbhId(kbhId);
    	
    	String vtchId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vtchTen")));
    	if (vtchId == null)
    		vtchId = "";    	
    	obj.setVtId(vtchId);
    	
    	String lchId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("lchTen")));
    	if (lchId == null)
    		lchId = "";    	
    	obj.setLchId(lchId);
    	
		String query = "";
    	
    	if (ten.length()>0)
    	{ 

			query = query + " and upper(dbo.ftBoDau(a.ten)) like upper(N'%" + util.replaceAEIOU(ten) + "%')";			
    	}
    	
    	if (kbhId.length()>0){
			query = query + " and a.kbh_fk ='" + kbhId + "'";			
    	}
    	
    	if (hchId.length()>0){
			query = query + " and a.hch_fk='" + hchId + "'";			
    	}
    	
    	if (vtchId.length()>0){
			query = query + " and a.vtch_fk='" + vtchId + "'";			
    	}
    	
    	if (lchId.length()>0){
			query = query + " and a.lch_fk='" + lchId + "'";			
    	}
    	
    	    	return query;
	}	
	

}
