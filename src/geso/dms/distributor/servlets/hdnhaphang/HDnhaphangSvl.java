package geso.dms.distributor.servlets.hdnhaphang;

import geso.dms.distributor.beans.hdnhaphang.IHDnhaphangList;
import geso.dms.distributor.beans.hdnhaphang.imp.HDnhaphangList;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/HDNhapHangSvl")
public class HDnhaphangSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public HDnhaphangSvl() {
        super();
       
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		IHDnhaphangList obj;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
    

	    Utility util = new Utility();
	    out = response.getWriter();
	    
	    String querystring = request.getQueryString();
//	    out.println(action);
	    
	   userId = util.getUserId(querystring);
	        
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    obj = (IHDnhaphangList) new HDnhaphangList();
	    obj.setUserId(userId);
	    obj.createNhaphanglist("");
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Distributor/HDNhapHang.jsp";
		response.sendRedirect(nextJSP);
		}
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
		IHDnhaphangList obj;

		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    obj = (IHDnhaphangList) new HDnhaphangList();		
	
	    Utility util = new Utility();
		userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    out.println(action); 	        
	    
	    String search = getSearchQuery(request, obj);
	    System.out.println(search);
	    
	   	obj.setUserId(userId);
	   	obj.createNhaphanglist(search);
			
	   	session.setAttribute("obj", obj);
	    		
	   	String nextJSP = "/AHF/pages/Distributor/HDNhapHang.jsp";	    		    	
	   	out.println(search);
    	response.sendRedirect(nextJSP);
    	
		}

	}   


	private String getSearchQuery(HttpServletRequest request, IHDnhaphangList obj){
//	    PrintWriter out = response.getWriter();
		Utility util=new Utility();
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
    	if (userId == null)
    		userId = "";
    	obj.setDangnhap(userId);
    	dbutils db = new dbutils();
    	
    	    	String nppId = util.getIdNhapp(userId);
    	
    	/*
    	 * String query = "select a.pk_seq from nhaphanphoi a, nhanvien b where a.ma = b.dangnhap and b.pk_seq = '" + this.userId + "'";

		ResultSet rs = db.get(query);
		try{
			if (rs != null){
				rs.next();
				nppId = rs.getString("pk_seq");
				rs.close();
			}else{
				nppId = "";
			}
		}catch(Exception e){}
		
    	*/
		String sku = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sku")));
    	if (sku == null)
    		sku = "";
    	obj.setSKU(sku);
    	
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
    	
	   String query = "select distinct a.ngaychungtu,a.dathang_fk, a.ngaynhan, a.pk_seq as chungtu,e.donvikinhdoanh, f.ten as kbh ,a.sotienavat, b.ten as nguoitao, c.ten as nguoisua, a.trangthai from hdnhaphang a, nhanvien b, nhanvien c, hdnhaphang_sp d, donvikinhdoanh e, kenhbanhang f where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.pk_seq = d.nhaphang_fk and a.dvkd_fk = e.pk_seq and a.npp_fk='" + nppId + "' and a.kbh_fk = f.pk_seq ";  	
    	if (sku.length()>0){
			query = query + " and d.sanpham_fk like '" + sku + "'";
    	}
    	
    	if (tungay.length()>0){
			query = query + " and a.ngaychungtu >= '" + tungay+ "'";
    	}

    	if (denngay.length()>0){
			query = query + " and a.ngaychungtu <= '" + denngay+ "'";
    		
    	}

    	if(trangthai.length() > 0){
    		query = query + " and a.trangthai = '" + trangthai + "'";
    		
    	}
    	query = query + " order by trangthai, ngaychungtu, chungtu";
    	return query;
	}	
	

}
