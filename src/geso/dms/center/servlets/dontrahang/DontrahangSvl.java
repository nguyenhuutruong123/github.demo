package geso.dms.center.servlets.dontrahang;

import geso.dms.center.util.Utility;
import geso.dms.center.beans.dontrahang.IDontrahangList;
import geso.dms.center.beans.dontrahang.imp.DontrahangList;
import geso.dms.center.beans.dontrahang.IDontrahang;
import geso.dms.center.beans.dontrahang.imp.Dontrahang;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class DontrahangSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public DontrahangSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    	    
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		Utility util = (Utility) session.getAttribute("util");
		if(!util.check(userId, userTen, sum)){
			response.sendRedirect("/index.jsp");
		}else{
			session.setMaxInactiveInterval(1200);	    	    
			String querystring = request.getQueryString();
			String action = util.getAction(querystring);
			
			if (action == null){
				action = "";
			}
	    
			String id = util.getId(querystring);
			if (id == null){
				id = "";
			}
			System.out.println("Action trong note xoa : "+action);
			if (action.equals("display")){				   
			    IDontrahang dthBean = (IDontrahang) new Dontrahang();
			    dthBean.setUserId(userId);				    
			   	dthBean.setId(id);
			    dthBean.initUpdate();
				    
			    String nextJSP = "/AHF/pages/Center/DonTraHangDisplay.jsp";
				    	    
				session.setAttribute("dthBean", dthBean);			
				
				response.sendRedirect(nextJSP);
			}else{						
				System.out.println("Ra Cho Nay Nek!");
				 IDontrahangList obj;
				obj = new DontrahangList();
				obj.setUserId(userId);
				obj.createDthlist("");
				session.setAttribute("obj", obj);
				
				String nextJSP = "/AHF/pages/Center/DonTraHang.jsp";
				response.sendRedirect(nextJSP);
				
			}
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    	    
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		Utility util = (Utility) session.getAttribute("util");
		if(!util.check(userId, userTen, sum)){
			response.sendRedirect("/index.jsp");
		}else{
			session.setMaxInactiveInterval(1200);	    	    
			
			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			 IDontrahangList obj;
			if (action.equals("search")){				   		
				obj = new DontrahangList();
				obj.setUserId(userId);
				String search = getSearchQuery(request,userId,obj);
				obj.createDthlist(search);
				session.setAttribute("obj", obj);
		
				String nextJSP = "/AHF/pages/Center/DonTraHang.jsp";
				response.sendRedirect(nextJSP);
			}else{
				if (action.equals("duyet")){	
					String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));					
					IDontrahang dthBean = (IDontrahang) new Dontrahang();
				    dthBean.setUserId(userId);				    
				   	dthBean.setId(id);
				   	dthBean.DuyetDthnpp();
				}

				obj = new DontrahangList();
				obj.setUserId(userId);
				obj.createDthlist("");
				session.setAttribute("obj", obj);
				
				String nextJSP = "/AHF/pages/Center/DonTraHang.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}

	private String getSearchQuery(HttpServletRequest request,String userId, IDontrahangList obj){
//	    PrintWriter out = response.getWriter();
		Utility util = new Utility();
    	String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
    	if (tungay == null)
    		tungay = "";    	
    	obj.setTungay(tungay);

    	String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
    	if (denngay == null)
    		denngay = "";    	
    	obj.setDenngay(denngay);
    	   	
    	String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));   	
    	if (trangthai == null)
    		trangthai = "";    	
	   	
    	obj.setTrangthai(trangthai);
    	
	    String query = "select distinct a.ngaytra, a.pk_seq as chungtu,e.donvikinhdoanh, a.sotienavat, b.ten as nguoitao, c.ten as nguoisua, a.trangthai" +
	    		" from dontrahang a, nhanvien b, nhanvien c, dontrahang_sp d," +
	    		" donvikinhdoanh e where a.nguoitao = b.pk_seq and a.nguoisua =" +
	    		" c.pk_seq and a.pk_seq = d.dontrahang_fk and a.dvkd_fk = e.pk_seq  ";
	    query = query + " and a.npp_fk in" + util.quyen_npp(userId);
	    query = query + " and a.kbh_fk in" + util.quyen_kenh(userId);
   
    	if (tungay.length()>0){
			query = query + " and a.ngaytra >= '" + tungay+ "'";
    		
    	}

    	if (denngay.length()>0){
			query = query + " and a.ngaytra <= '" + denngay+ "'";
    		
    	}

    	if(trangthai.length() > 0){
    		query = query + " and a.trangthai = '" + trangthai + "'";
    		
    	}
    	
    	System.out.println("Query o Svl: " + query + "\n");
    	
    	query = query + " order by a.ngaytra, a.trangthai";
    	return query;
	}	
}
