package geso.dms.center.servlets.hdnhaphang;

import geso.dms.center.beans.hdnhaphang.IHDnhaphangList;
import geso.dms.center.beans.hdnhaphang.imp.HDnhaphangList;
import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/HDnhaphangTTSvl")
public class HDnhaphangTTSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	IHDnhaphangList obj;
	private int items = 50;
	private int splittings = 10; 
    
    public HDnhaphangTTSvl() {
        super();
        
    }
    
    private void settingPage(IHDnhaphangList obj) {
		Utility util = new Utility();
		if(getServletContext().getInitParameter("items") != null){
	    	String i = getServletContext().getInitParameter("items").trim();
	    	if(util.isNumeric(i))
	    		items = Integer.parseInt(i);
	    }
	    
	    if(getServletContext().getInitParameter("splittings") != null){
	    	String i = getServletContext().getInitParameter("splittings").trim();
	    	if(util.isNumeric(i))
	    		splittings = Integer.parseInt(i);
	    }
	    
   	obj.setItems(items);
   	obj.setSplittings(splittings);
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	  
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();	    
	    
	    String querystring = request.getQueryString();	   
	    String userId = util.getUserId(querystring);
	    
	   userId = util.getUserId(querystring);
	        
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    obj = (IHDnhaphangList) new HDnhaphangList();
	    settingPage(obj);	    
	    obj.setUserId(userId);	    
	    obj.init();
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Center/HDNhapHangTT.jsp";
		response.sendRedirect(nextJSP);		
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
	    settingPage(obj);
	    
	    Utility util = new Utility();
		userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    out.println("ACTION LA :"+action);
	    
	    if (action.equals("search"))
	    {
	    	System.out.println("vào search");
	    		    	
	    	String search = getSearchQuery(request, obj);
	    	obj.setQuery(search);
			obj.setUserId(userId);
			obj.init();
			
	    	session.setAttribute("obj", obj);

    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/AHF/pages/Center/HDNhapHangTT.jsp");    	
	    	
	    }
	    else if(action.equals("view") || action.equals("next") || action.equals("prev")){
		    	
		    	String search = getSearchQuery(request, obj);
		    	
		    	obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
		    	obj.setUserId(userId);
		    	obj.setQuery(search);
		    	obj.init();
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/AHF/pages/Center/HDNhapHangTT.jsp");
		    }
	    else{
	    String search = getSearchQuery(request, obj);
	    System.out.println(search);
	    
	   	obj.setUserId(userId);
	   	obj.setQuery(search);
	   	obj.init();
	   	//obj.createNhaphanglist(search);
			
	   	session.setAttribute("obj", obj);
	    		
	   	String nextJSP = "/AHF/pages/Center/HDNhapHangTT.jsp";	    		    	
	   	//out.println(search);
    	response.sendRedirect(nextJSP);
	    }
		}
	}

	private String getSearchQuery(HttpServletRequest request, IHDnhaphangList obj){
//	    PrintWriter out = response.getWriter();
		Utility util=new Utility();
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
    	if (userId == null)
    		userId = "";
    	obj.setDangnhap(userId);    	
    	
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
    	
    	String npp = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("npp")));
    	if (npp == null)
    		npp = "";    	
    	obj.setNppId(npp);
    	   	
    	String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));   	
    	if (trangthai == null)
    		trangthai = "";    	
	  	
    	obj.setTrangthai(trangthai);
    	
	   //String query = "select distinct a.ngaychungtu,a.dathang_fk, a.ngaynhan, a.pk_seq as chungtu,npp.ten as tennpp ,e.donvikinhdoanh, f.ten as kbh ,a.sotienavat, b.ten as nguoitao, c.ten as nguoisua, a.trangthai from hdnhaphang a, nhanvien b, nhanvien c, hdnhaphang_sp d, donvikinhdoanh e,nhaphanphoi npp, kenhbanhang f where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.pk_seq = d.nhaphang_fk and a.dvkd_fk = e.pk_seq and a.kbh_fk = f.pk_seq ";
    	String query = "select distinct hd.pk_seq as chungtu, hd.ngaychungtu, hd.DATHANG_FK, hd.NGAYNHAN, dvkd.DONVIKINHDOANH, "+
	    "npp.TEN, kbh.TEN as kbh, hd.SOTIENAVAT, nt.TEN as nguoitao, ns.TEN as nguoisua, hd.TRANGTHAI "+
	    "from HDNHAPHANG hd "+
	    "inner join NHANVIEN nt on nt.PK_SEQ = hd.NGUOITAO "+
	    "inner join NHANVIEN ns on ns.PK_SEQ = hd.NGUOISUA "+
	    "inner join HDNHAPHANG_SP hdsp on hdsp.NHAPHANG_FK = hd.PK_SEQ "+
	    "inner join NHAPHANPHOI npp on npp.PK_SEQ = hd.NPP_FK "+
	    "inner join DONVIKINHDOANH dvkd on dvkd.PK_SEQ = hd.DVKD_FK "+
	    "inner join KENHBANHANG kbh on kbh.PK_SEQ = hd.KBH_FK"; 
	   
    	if (sku.length()>0){
			query = query + " and hdsp.sanpham_fk like '" + sku + "'";
    	}
    	
    	if (tungay.length()>0){
			query = query + " and hd.ngaychungtu >= '" + tungay+ "'";
    	}

    	if (denngay.length()>0){
			query = query + " and hd.ngaychungtu <= '" + denngay+ "'";
    		
    	}

    	if(trangthai.length() > 0){
    		query = query + " and hd.trangthai = '" + trangthai + "'";
    		
    	}
    	
    	if(npp.length() > 0){
    		query = query + " and npp.pk_seq = '"+npp+"'";
    	}
    	//query = query + " order by npp.ten, trangthai, ngaychungtu, chungtu";
    	return query;
	}	
}
