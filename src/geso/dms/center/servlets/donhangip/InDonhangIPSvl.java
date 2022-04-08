package geso.dms.center.servlets.donhangip;

import geso.dms.distributor.beans.donhang.IDonhangList;

import geso.dms.distributor.beans.donhang.imp.DonhangList;

import geso.dms.distributor.beans.donhang.IDonhang;
import geso.dms.distributor.beans.donhang.imp.Donhang;

import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class InDonhangIPSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	
    public InDonhangIPSvl() 
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
		IDonhangList obj;
		PrintWriter out; 
		String nppId;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out  = response.getWriter();

	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    //out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    String id = util.getId(querystring);  	
	    
	    if(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"))) != null)
	    	nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
	    /*
	    dbutils db = new dbutils();
	    ResultSet rs = db.get("select a.pk_seq, a.ten, a.sitecode from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + this.userId + "'");
			if (!(rs == null)){
				try {
					rs.next();
					this.nppId = rs.getString("pk_seq");
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			
	    */
	    nppId=util.getIdNhapp(userId);
	    String action = util.getAction(querystring);
	    //out.println(action);
	    String nextJSP="";
	    if(action.equals("display")) {
	    	
	    	IDonhang dhBean=new Donhang(id);
			 dhBean.setUserId(userId); //phai co UserId truoc khi Init
			 dhBean.init();	
			 dhBean.setKhId(dhBean.getKhId());
			 dhBean.CreateSpDisplay();
			 	session.setAttribute("dhBean", dhBean);
		        session.setAttribute("khId", dhBean.getKhId());
		        session.setAttribute("ddkdId", dhBean.getDdkdId());
          	nextJSP = "/AHF/pages/Distributor/DonHangDisplayPrint.jsp";
	    }else{
	    obj = new DonhangList();
	    obj.setUserId(userId);
	    
		String query = "select ROW_NUMBER() OVER(ORDER BY a.ngaynhap DESC) AS 'stt', a.pk_seq as dhId, a.ngaynhap, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua, d.ten as khTen, d.pk_seq as khId, e.pk_seq as ddkdId, e.ten as ddkdTen, f.ten as nppTen, a.VAT";
		query = query + " from donhangIP a left join nhanvien b on a.nguoitao = b.pk_seq left join nhanvien c on a.nguoisua = c.pk_seq inner join khachhang d on a.khachhang_fk = d.pk_seq inner join daidienkinhdoanh e on a.ddkd_fk = e.pk_seq inner join nhaphanphoi f on a.npp_fk = f.pk_seq";
		query = query + " where a.npp_fk = '" + nppId + "' and a.trangthai='1' ";
		
		query = "select top(50) * from (" + query + ") as dhList";

	    obj.init(query);
	    
		session.setAttribute("obj", obj);
		session.setAttribute("khId", "");	
		 nextJSP = "/AHF/pages/Distributor/InDonHang.jsp";
	    }
	    
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
		
		IDonhangList obj;
		PrintWriter out; 
		
		String nppId;
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
	   
	   
	    if(action.equals("search"))
	    {
	    	obj = new DonhangList();
	    	String search = getSearchQuery(request, "", "", obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
					    	  	
    		session.setAttribute("userId", userId);
    		session.setAttribute("obj", obj);
    			    		
    		response.sendRedirect("/AHF/pages/Distributor/InDonHang.jsp");	    		    	
	    }
	    
	    if(action.equals("next"))
	    {
	    	obj = new DonhangList();
	    	
	    	String currentPage = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("currentPage"));
	    	String[] listPage = request.getParameterValues("list");
	    	int[] list = new int[20];
	    	for(int i = 0; i < listPage.length; i++)
	    		list[i] = Integer.parseInt(listPage[i]) + 1;
	    	obj.setListPages(list);
	    	
	    	int num = Integer.parseInt(currentPage) + 1;
	    	obj.setCurrentPage(num);
	    	
	    	obj.setUserId(userId);
	    	
	    	String search = getSearchQuery(request, Integer.toString(num), "20", obj);
		    obj.init(search);
			session.setAttribute("obj", obj);
					
			String nextJSP = "/AHF/pages/Distributor/InDonHang.jsp";
			response.sendRedirect(nextJSP);
	    }
	    
	    if(action.equals("prev"))
	    {
	    	obj = new DonhangList();
	    	
	    	String currentPage = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("currentPage"));
	    	String[] listPage = request.getParameterValues("list");
	    	int[] list = new int[20];
	    	for(int i = 0; i < listPage.length; i++)
	    		list[i] = Integer.parseInt(listPage[i]) - 1;
	    	obj.setListPages(list);
	    	
	    	int num = Integer.parseInt(currentPage) - 1;
	    	obj.setCurrentPage(num);
	    	
	    	obj.setUserId(userId);
		    
	    	String search = getSearchQuery(request, Integer.toString(num), "20", obj);
		    obj.init(search);
			session.setAttribute("obj", obj);
					
			String nextJSP = "/AHF/pages/Distributor/InDonHang.jsp";
			response.sendRedirect(nextJSP);
	    }
	    
	    if(action.equals("view"))
	    {
	    	obj = new DonhangList();
	    	
	    	String currentPage = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("currentPage"));
	    	String[] listPage = request.getParameterValues("list");
	    	int[] list = new int[20];
	    	
	    	if(currentPage.equals("1"))
	    	{
	    		list = new int[]{1, 2 , 3 , 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
	    	}
	    	else
	    	{
	    		if(currentPage.equals("-1"))
	    		{
	    			int pos = obj.getLastPage() / 20; //so dong tren 1 trang
	    			currentPage = Integer.toString(pos);
	    			
	    			System.out.print("Curent Page la: " + Integer.toString(pos));
	    			
	    			int j = 0;
	    			int k = 20;
	    			while(j < 20)
	    			{
	    				list[j] = pos - k;
	    				j++;
	    				k--;
	    			}
	    		}
	    		else
	    		{
			    	for(int i = 0; i < listPage.length; i++)
			    		list[i] = Integer.parseInt(listPage[i]) + 1;
	    		}
	    	}
	    	
	    	obj.setListPages(list);
	    	obj.setCurrentPage(Integer.parseInt(currentPage));
	    	
	    	obj.setUserId(userId);
		
	    	String search = getSearchQuery(request, currentPage, "20", obj);
		    obj.init(search);
			session.setAttribute("obj", obj);
					
			String nextJSP = "/AHF/pages/Distributor/InDonHang.jsp";
			response.sendRedirect(nextJSP);
	    }
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, String pages, String soDong, IDonhangList obj) 
	{
    	String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
    	if ( nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
    	String ddkdId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdTen"));
    	if ( ddkdId == null)
    		ddkdId = "";
    	obj.setDdkdId(ddkdId);
    	String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
    	if (tungay == null)
    		tungay = "";    	
    	obj.setTungay(tungay);
    	
    	String sodonhang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sodonhang"));
    	if (sodonhang == null)
    		sodonhang = "";  
    	//lay field trang thai thay vao la so don hang.de khoi mat cong sua cac file khac
    	obj.setTrangthai(sodonhang);
    
    	String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
    	if (denngay == null)
    		denngay = "";    	
    	obj.setDenngay(denngay);
    	
    	String query = "select ROW_NUMBER() OVER(ORDER BY a.ngaynhap DESC) AS 'stt', a.pk_seq as dhId, a.ngaynhap, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua, d.ten as khTen, d.pk_seq as khId, e.pk_seq as ddkdId, e.ten as ddkdTen, f.ten as nppTen,  a.VAT";
		query = query + " from donhangIP a inner join nhanvien b on a.nguoitao = b.pk_seq inner join nhanvien c on a.nguoisua = c.pk_seq inner join khachhang d on a.khachhang_fk = d.pk_seq inner join daidienkinhdoanh e on a.ddkd_fk = e.pk_seq inner join nhaphanphoi f on a.npp_fk = f.pk_seq";
		query = query + " where a.npp_fk = '" + nppId + "' and a.trangthai='1'";
    	
    	if (ddkdId.length() > 0)
    	{
			query = query + " and e.pk_seq = '" + ddkdId + "'";		
    	}    	
    	if (sodonhang.length()>0)
    	{
			query = query + " and a.pk_seq like '%" + sodonhang + "%'";			
    	}    	
    	if (tungay.length()>0)
    	{
			query = query + " and a.ngaynhap > '" + tungay + "'";			
    	}    	
    	if (denngay.length()>0)
    	{
			query = query + " and a.ngaynhap < '" + denngay + "'";	
    	}
    	
    	query = "select top(20) * from (" + query + ") as dhList";
    	
    	if (pages.length() > 0)
    	{
    		int pos = (Integer.parseInt(pages) - 1) * Integer.parseInt(soDong);
    		query = query + " where stt > '" + Integer.toString(pos) + "'";	
    	}
    	
    	//System.out.print("\nQuery cua ban: " + query);
    	return query;
	}

}
