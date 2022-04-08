package geso.dms.center.servlets.ghepkhogiay;

import geso.dms.center.beans.ghepkhogiay.LenhSanXuat;
import geso.dms.center.beans.ghepkhogiay.LenhSanXuatList;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LenhSanXuatSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LenhSanXuatSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		Utility cutil =  (Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
			LenhSanXuatList obj;
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
		    if (action.trim().length() == 0)
		    	action = util.getAction(querystring);
		    System.out.println("action is: " + action);	
		    
		    session.setAttribute("action", action);
	   	    
		    obj = new LenhSanXuatList();
		    
		    String tuNgay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tuNgay")));
		    if (tuNgay == null)
		    	tuNgay = "";
		    obj.setTuNgay(tuNgay);
		    String denNgay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denNgay")));
		    if (denNgay == null)
		    	denNgay = "";
		    obj.setDenNgay(denNgay);
		    String maySanXuatId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("maySanXuatId")));
		    if (maySanXuatId == null)
		    	maySanXuatId = "";
		    obj.setMaySanXuatId(maySanXuatId);
		    
		    String lenhSanXuatId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("lenhSanXuatId")));
		    if (lenhSanXuatId == null)
		    	lenhSanXuatId = "";
		    obj.setLenhSanXuatId(lenhSanXuatId);
		    
		    String toHopId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("toHopId")));
		    if (toHopId == null)
		    	toHopId = "";
		    obj.setToHopId(toHopId);
		    
		    obj.init();

		    if (action.equals("chot") == true)
		    {
		    	String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chot")));;
		    	LenhSanXuat lenhSanXuat = obj.getLenhSanXuatById(id);
		    	lenhSanXuat.chot();
		    }
//		    obj.DBClose();
//		    
//		    obj = new LenhSanXuatList();
//		    
//		    obj.setTuNgay(tuNgay);
//		    obj.setDenNgay(denNgay);
//		    obj.setMaySanXuatId(maySanXuatId);
		    
		    obj.init();
		    String nextJSP = "/AHF/pages/Center/LenhSanXuat.jsp";
			session.setAttribute("obj", obj);
			response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LenhSanXuatList obj  = new LenhSanXuatList();
		String userId;
		
	    Utility util = new Utility();
		HttpSession session = request.getSession();
	    userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = util.getAction(request.getQueryString());
	    	if (action == null)
	    		action = "";	    
    	}
	    
	    String kichBanId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kichBanId")));
		if (kichBanId == null)
		{
			kichBanId = "";
		}
		obj.setKichBanId(kichBanId);
	    
    	String tuNgay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays")));
		if (tuNgay == null)
		{
			tuNgay = "";
		}
		obj.setTuNgay(tuNgay);
    	
		String denNgay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays")));
		if (denNgay == null)
		{
			denNgay = "";
		}
		obj.setDenNgay(denNgay);

		String maySanXuatId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("maySanXuatId")));
		if (maySanXuatId == null)
		{
			maySanXuatId = "";
		}
		obj.setMaySanXuatId(maySanXuatId);
		
		String lenhSanXuatId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("lenhSanXuatId")));
	    if (lenhSanXuatId == null)
	    	lenhSanXuatId = "";
	    obj.setLenhSanXuatId(lenhSanXuatId);
	    
	    String toHopId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("toHopId")));
	    if (toHopId == null)
	    	toHopId = "";
	    obj.setToHopId(toHopId);
	    
		session.setAttribute("action", action);

    	obj.init();
    	session.setAttribute("obj", obj);
    	request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Center/LenhSanXuat.jsp";
		response.sendRedirect(nextJSP);
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}