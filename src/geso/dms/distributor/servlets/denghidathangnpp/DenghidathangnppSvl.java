package geso.dms.distributor.servlets.denghidathangnpp;

import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.denghidathang.IDenghidathang;
import geso.dms.distributor.beans.denghidathang.IDenghidathangList;
import geso.dms.distributor.beans.denghidathang.imp.Denghidathang;
import geso.dms.distributor.beans.denghidathang.imp.DenghidathangList;
import geso.dms.distributor.beans.denghidathangnpp.IDenghidathangnpp;
import geso.dms.distributor.beans.denghidathangnpp.IDenghidathangnppList;
import geso.dms.distributor.beans.denghidathangnpp.imp.Denghidathangnpp;
import geso.dms.distributor.beans.denghidathangnpp.imp.DenghidathangnppList;
import geso.dms.distributor.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class DenghidathangnppSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 
	   String userId;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		IDenghidathangnppList obj;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
//	    PrintWriter out = response.getWriter();
	    	    
		
		userId = (String) session.getAttribute("userId");
		userTen = (String) session.getAttribute("userTen");  	
		sum = (String) session.getAttribute("sum");
		Utility util = (Utility) session.getAttribute("util");
		if(!util.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
			session.setMaxInactiveInterval(1200);
			String querystring = request.getQueryString();
			String action = util.getAction(querystring);
	    
			String dndhId = util.getId(querystring);
	    
			if (action.equals("delete")){	   		  	    	
				Delete(dndhId);
			}
	        
			obj = new DenghidathangnppList();
	        obj.setUserId(userId);
			obj.createDndhlist();
			session.setAttribute("obj", obj);
				
			String nextJSP = "/AHF/pages/Distributor/DeNghiDatHangNpp.jsp";
			response.sendRedirect(nextJSP);
		}	}	

	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		IDenghidathangnppList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    
		
		userId = (String) session.getAttribute("userId");
		userTen = (String) session.getAttribute("userTen");  	
		sum = (String) session.getAttribute("sum");
		Utility util = (Utility) session.getAttribute("util");
	
		 obj = (IDenghidathangnppList) new DenghidathangnppList();
			this.userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			if (action == null){
				action = "";
			}
	        String sku = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sku")));
	        if(sku == null)
	        	sku ="";
	        obj.setSKU(sku);
	        String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
	        if(tungay == null)
	        	tungay ="";
	        obj.setTungay(tungay);
	        
	        String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
	        if(denngay == null)
	        	denngay ="";
	        obj.setDenngay(denngay);
	        
	        String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
	        if(trangthai == null)
	        	trangthai ="";
	        obj.setTrangthai(trangthai);
	        
			out.print(action);
			if (action.equals("Tao moi")){	  
				IDenghidathangnpp dndhBean1 = new Denghidathangnpp();
				dndhBean1.setUserId(userId);
				dndhBean1.init0();
				//dndhBean1.init1();
				session.setAttribute("dndhBean", dndhBean1);
			//	out.println(dndhBean.getMessage());
	    	
				String nextJSP = "/AHF/pages/Distributor/DeNghiDatHangNppDislay.jsp";
				response.sendRedirect(nextJSP);    		
			}
			else{
				
				//obj = new DenghidathangnppList();
				obj.setUserId(userId);
				obj.createDndhlist();
				session.setAttribute("obj", obj);
				String nextJSP = "/AHF/pages/Distributor/DeNghiDatHangNpp.jsp";	
				response.sendRedirect(nextJSP);
			}
		}}
		
	//}   	  	    
	
	private void Delete(String dndhId){
		dbutils db = new dbutils();
		try{
			ResultSet rs = db.get("select dadathang from denghidathang where pk_seq='" + dndhId + "'");
			rs.next();
			if(Long.valueOf(rs.getString("dadathang")).longValue() < 1000) {
				db.update("delete from denghidathang_sp where denghidathang_fk='" + dndhId + "'");
				db.update("delete from denghidathang where pk_seq ='" + dndhId + "'");
			}
			rs.close();
			db.shutDown();
		}catch(Exception e){}
	}

}
