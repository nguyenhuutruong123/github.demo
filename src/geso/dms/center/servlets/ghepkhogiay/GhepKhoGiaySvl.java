package geso.dms.center.servlets.ghepkhogiay;

import geso.dms.center.beans.ghepkhogiay.KeHoachList;
import geso.dms.center.beans.ghepkhogiay.KeHoachSanXuatN;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GhepKhoGiaySvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GhepKhoGiaySvl() {
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
			KeHoachList obj;
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
		    
		    session.setAttribute("action", action);
	   	    
		    obj = new KeHoachList();
		    obj.init();
//		    Set<Float> keys = obj.getHash().keySet();
//	        for(Float key: keys){
//	            Hashtable<String, SanPham> tmpHash = obj.getHash().get(key);
//	            Set<String> spKeys = tmpHash.keySet();
//	            for(String key1: spKeys){
//	            	SanPham sp = tmpHash.get(key1);
//	            	System.out.println("San pham " + sp.getId() + ": " + sp.getSoLuong());
//	            }
//	        }
			session.setAttribute("obj", obj);
			String nextJSP = "/AHF/pages/Center/GhepKhoGiay.jsp";
			response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		PrintWriter out; 
		String userId;
	    out = response.getWriter();
	    Utility util = new Utility();
		HttpSession session = request.getSession();
	    userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = util.getAction(request.getQueryString());
	    	if (action == null)
	    		action = "";	    }
	    session.setAttribute("action", action);
	    out.println(action); 
	          
	    if (action.equals("new"))
	    {
	    	// Empty Bean for center
	    	KeHoachSanXuatN sanPhamList = new KeHoachSanXuatN();
//	    	sanPhamList.init();
	    	// Save Data into session
	    	session.setAttribute("action", "save");
	    	session.setAttribute("obj", sanPhamList);
    		String nextJSP = "/AHF/pages/Center/GhepKhoGiayNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else 
    	{
	    	KeHoachList obj  = new KeHoachList();
	    	
	    	String trangThai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangThai")));
		    if (trangThai == null)
		    	trangThai = "";
		    obj.setTrangThai(trangThai);
	    	
		    String Sdays = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays")));
		    if (Sdays == null)
		    	Sdays = "";
		    obj.setTuNgay(Sdays);
		    
		    String Edays = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays")));
		    if (Edays == null)
		    	Edays = "";
		    obj.setDenNgay(Edays);
		    
	    	String toHopId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("toHopId")));
		    if (toHopId == null)
		    	toHopId = "";
		    obj.setToHopId(toHopId);
		    
		    String kichBanId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kichBanId")));
		    if (kichBanId == null)
		    	kichBanId = "";
		    System.out.println("kichBanId: " + kichBanId);
		    obj.setKichBanId(kichBanId);
		    
		    
		    String donHangId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("donHangId")));
		    if (donHangId == null)
		    	donHangId = "";
		    obj.setDonHangId(donHangId);
		    
		    obj.init();
		    
	    	if (action.equals("search"))//action == search
	    	{
//		    	
//		    	String search = this.getSearchQuery(request, obj);
//		    	obj.setUserId(userId);
//		    	obj.init(search);
//				obj1.setNhaPhanPhoiId(obj.getNhaPhanPhoiId());	
//				obj1.init();
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		    		
	    		response.sendRedirect("/AHF/pages/Center/GhepKhoGiay.jsp");	    		    	
		    }
		    else{
				//phantrang
//				obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
//				String search = getSearchQuery(request, obj);
//				obj.setUserId(userId);
//				obj.init(search);
//				obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
				/////////
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
	    		response.sendRedirect("/AHF/pages/Center/GhepKhoGiay.jsp");	    		   
			}
    	}
	}
}