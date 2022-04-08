package geso.dms.center.servlets.gioihancongnott;

import geso.dms.center.beans.gioihancongnott.IGioiHanCongNoTT;
import geso.dms.center.beans.gioihancongnott.IGioiHangCongNoTT_Npp;
import geso.dms.center.beans.gioihancongnott.imp.GioiHanCongNoTT;
import geso.dms.center.beans.gioihancongnott.imp.GioiHangCongNo_Npp;
import geso.dms.distributor.beans.gioihancongno.IGioihancongno;
import geso.dms.distributor.beans.gioihancongno.IGioihancongnoList;
import geso.dms.distributor.beans.gioihancongno.imp.Gioihancongno;
import geso.dms.distributor.beans.gioihancongno.imp.GioihancongnoList;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.awt.Desktop.Action;
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

import Z.DB;


public class GioiHanCongNoTTSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GioiHanCongNoTTSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		IGioiHanCongNoTT obj;
		PrintWriter out; 
		String userId;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
	    obj = new GioiHanCongNoTT();
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
	    obj.setListGioHanCongNo("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Center/GioiHanCongNoTT.jsp";
		                                     
		response.sendRedirect(nextJSP);
	}
	private String getSearchQuery(HttpServletRequest request) 
	{
		
		IGioiHanCongNoTT obj = new GioiHanCongNoTT();
		PrintWriter out; 
		String userId;
		
		Utility util = new Utility();
		
		HttpSession session = request.getSession();		
		String TenNhaPP = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("TenNhaPP")));
		session.setAttribute("TenNhaPP",TenNhaPP);
    	if ( TenNhaPP == null){
    		TenNhaPP = "";
		}
    	
		String songayno = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("songayno")));
    	if ( songayno == null)
    		songayno = "";
    	obj.setSongayno(songayno);
    	
    	String sotienno = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sotienno")));
    	if ( sotienno == null)
    		sotienno = "";
    	obj.setSotienno(sotienno);
    
    	String	 query ="select a.pk_seq,a.diengiai,a.songayno,a.sotienno,a.ngaytao,a.ngaysua ,nt.ten as nguoitao,ns.ten as nguoisua from gioihancongnott a inner join nhanvien as nt on nt.pk_seq=a.nguoitao  inner join nhanvien as ns on ns.pk_seq=a.nguoisua  where 1=1 ";

    	if (songayno.length() > 0)
    	{
			query = query + " and songayno='" + songayno + "'";			
    	}
    	
    	if (sotienno.length() > 0)
    	{
			query = query + " and sotienno='" + sotienno + "'";			
    	}
    	
    	if (TenNhaPP.length() > 0)
    	{ 
    		query=query+ " and a.pk_seq in (select ghcntt_fk from nhaphanphoi where ten like '%"+TenNhaPP+"%')";
     	}
    	// System.out.println(query); 	
    	query = query + " order by songayno";
    	return query;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		IGioiHanCongNoTT obj = new GioiHanCongNoTT();
		PrintWriter out; 
		String userId;
		
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out = response.getWriter();
	    
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    out.println(action); 
	          
	    if (action.equals("new"))
	    {
	    	// Empty Bean for distributor
	    	List<IGioiHangCongNoTT_Npp> list=new ArrayList<IGioiHangCongNoTT_Npp>();
	    	IGioiHanCongNoTT ghcnBean = new GioiHanCongNoTT();
	    	ghcnBean.setUserId(userId);
	    	dbutils db=new dbutils();
	    	String sql="select a.pk_seq,ten,diachi,ten as khuvuc from nhaphanphoi a inner join khuvuc b on b.pk_seq=a.khuvuc_fk";
	    	ResultSet rs=db.get(sql);
	    	if(rs!=null){
	    		try{
	    			while(rs.next()){
	    				IGioiHangCongNoTT_Npp npp=new GioiHangCongNo_Npp();
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
	    	ghcnBean.setListNhapp(list);
	    	String sqlgetkhuvuc="select pk_seq,ten from khuvuc";
	    	ResultSet rs_khuvuc=db.get(sqlgetkhuvuc);
	    	session.setAttribute("rs_khuvuc", rs_khuvuc);
	    	// Save Data into session
	    	session.setAttribute("ghcnBean", ghcnBean);
    		String nextJSP = "/AHF/pages/Center/GioiHanCongNoTTNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    
	    if (action.equals("search"))
	    {
	    	obj = new GioiHanCongNoTT();
	    	String search = this.getSearchQuery(request);
	    	
	    	obj.setUserId(userId);
	    	obj.setListGioHanCongNo(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/AHF/pages/Center/GioiHanCongNoTT.jsp");	    		    	
	    }
	}

}
