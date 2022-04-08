package geso.dms.center.servlets.mucchietkhautt;
import geso.dms.center.beans.mucchietkhautt.IMucChietKhauTT;
import geso.dms.center.beans.mucchietkhautt.IMucChietKhauTT_NhaPP;
import geso.dms.center.beans.mucchietkhautt.imp.MucChietKhauTT;
import geso.dms.center.beans.mucchietkhautt.imp.MucChietKhauTT_NhaPP;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MucChietKhauTTSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PrintWriter out;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MucChietKhauTTSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		
		
		IMucChietKhauTT obj;
		 
		String userId;
		
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
	    obj = new MucChietKhauTT();
	    Utility util = new Utility();
	    out = response.getWriter();
	
	    String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String ghcnId = util.getId(querystring);

	    if (action.equals("delete")){	   		  	    	
	    	
	    
	    }

	    obj.setUserId(userId);
	    obj.setListMucChietKhau("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Center/MucChietKhauTT.jsp";
		                                     
		response.sendRedirect(nextJSP);
	}
	private String getSearchQuery(HttpServletRequest request) 
	{
		IMucChietKhauTT obj = new MucChietKhauTT() ;
		 
		String userId;
		Utility util = new Utility();
		HttpSession session = request.getSession();		
		String TenNhaPP = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("TenNhaPP")));
		session.setAttribute("TenNhaPP",TenNhaPP);
    	if ( TenNhaPP == null){
    		TenNhaPP = "";
		}
    	String chietkhau = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("mucchietkhau")));
    	if ( chietkhau == null)
    		chietkhau = "0";
    	System.out.println("muc chiet khau"+ chietkhau);
    		obj.setChietKhau(Double.parseDouble(chietkhau));
    	
		String query ="select a.pk_seq,a.diengiai,a.chietkhau,a.ngaytao,a.ngaysua ,nt.ten "+
			" as nguoitao,ns.ten as nguoisua from mucchietkhautt a inner join nhanvien as nt on nt.pk_seq=a.nguoitao "+ 
			// " inner join nhanvien as ns on ns.pk_seq=a.nguoisua where a.trangthai!='2' ";	    
			 " inner join nhanvien as ns on ns.pk_seq=a.nguoisua ";
		int dem = 0;
		if (Double.parseDouble(chietkhau) > 0)
    	{  dem  =1;
			query = query + " where cast(chietkhau as float) ='"+ chietkhau +"'" ;			
    	}
    	
    	if (TenNhaPP.length() > 0)
    	{ if(dem ==1 )
    		query=query+ " and a.pk_seq in (select mucchietkhautt_fk from nhaphanphoi where ten like '%"+TenNhaPP+"%')";
    	else
    		query=query+ " where a.pk_seq in (select mucchietkhautt_fk from nhaphanphoi where ten like '%"+TenNhaPP+"%')";
     	}
    	 System.out.println(query); 	
    	
    	return query;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		IMucChietKhauTT obj = new MucChietKhauTT() ;
		 
		String userId;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out = response.getWriter();
		HttpSession session = request.getSession();
		
		Utility util = new Utility();
		
	    userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    out.println(action); 
	          
	    if (action.equals("new"))
	    {
	    	// Empty Bean for distributor
	    	List<IMucChietKhauTT_NhaPP> list=new ArrayList<IMucChietKhauTT_NhaPP>();
	    	IMucChietKhauTT ghcnBean = new MucChietKhauTT();
	    	dbutils db=new dbutils();
	    	String sql="select a.pk_seq,a.ten,a.diachi,b.ten as khuvuc from nhaphanphoi a inner join khuvuc b on a.khuvuc_fk =b.pk_seq ";
	    	ResultSet rs=db.get(sql);
	    	if(rs!=null){
	    		try{
	    			while(rs.next()){
	    				IMucChietKhauTT_NhaPP npp=new MucChietKhauTT_NhaPP();
	    				npp.setDiaChi(rs.getString("diachi"));
	    				npp.setIDNhaPP(rs.getString("pk_seq"));
	    				npp.setTenNhaPP(rs.getString("ten"));
	    				npp.setKhuVuc(rs.getString("khuvuc"));
	    				npp.setID("0");//Mac dinh set =0 de check =false;
	    				list.add(npp);
	    			}
	    		}catch(Exception er){
	    			System.out.println("Error GioiHanCongNoTTSvl : 167"+ er.toString());
	    		}
	    	}
	    	ghcnBean.setListNhaPhanPhoi(list);
	    	String sqlgetkhuvuc="select pk_seq,ten from khuvuc";
	    	ResultSet rs_khuvuc=db.get(sqlgetkhuvuc);
	    	session.setAttribute("rs_khuvuc", rs_khuvuc);
	    	// Save Data into session
	    	session.setAttribute("ghcnBean", ghcnBean);
    		String nextJSP = "/AHF/pages/Center/MucChietKhauTTNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    if (action.equals("search"))
	    {
	    	obj = new MucChietKhauTT();
	    	String search = this.getSearchQuery(request);
	    	obj.setUserId(userId);
	    	obj.setListMucChietKhau(search);	
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
    		response.sendRedirect("/AHF/pages/Center/MucChietKhauTT.jsp");	    		    	
	    }
	}

}
