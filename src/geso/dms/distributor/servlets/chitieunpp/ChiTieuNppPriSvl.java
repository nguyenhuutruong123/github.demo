package geso.dms.distributor.servlets.chitieunpp;

import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.chitieunpp.imp.ChiTieuDDKD;
import geso.dms.distributor.beans.chitieunpp.imp.ChiTieuNhaPP;
import geso.dms.distributor.db.sql.dbutils;

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


public class ChiTieuNppPriSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChiTieuNppPriSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		 Utility util =new Utility();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		ChiTieuNhaPP obj = new ChiTieuNhaPP();
		String querystring = request.getQueryString();
		userId = util.getUserId(querystring);  
		if (userId.length()==0)
		userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
		session.setAttribute("userId", userId); 
		obj.setUserId(userId);
		//lay id nha phan phoi va ten nha pp
		String nhappid=util.getIdNhapp(userId);
		String tennhapp=util.getTenNhaPP();
		
		dbutils db = new dbutils();
		
		String action = util.getAction(querystring);
		//out.println(action);   
		String idlist = util.getId(querystring); 
		String nextJSP = "/AHF/pages/Distributor/ChiTieuNppPri.jsp";//default
		// truyen qua form dvkd :
		String sql_getdvkd="select pk_seq,donvikinhdoanh from donvikinhdoanh where trangthai=1";		
		ResultSet rs_dvkd= db.get(sql_getdvkd);
		session.setAttribute("rs_dvkd",rs_dvkd);
		String sql_getchiteu_pri="select kbh.ten as kenhbanhang ,a.chitieu,c.donvikinhdoanh,thang,nam,songaylamviec,nsp.pk_seq,nsp.ten "+ 
								" from chitieu_nhapp_nhomsp a inner join chitieu b  on a.chitieu_fk=b.pk_seq  "+
								" inner join donvikinhdoanh c on c.pk_seq=b.dvkd_fk  "+
								 " inner join kenhbanhang kbh on kbh.pk_seq=b.kenh_fk   "+
								 " inner join nhomsanpham nsp on nsp.pk_seq=nhomsp_fk "+
								  " where a.npp_fk="+nhappid;
		System.out.println(sql_getchiteu_pri);
		ResultSet rs_chitieu_pri=db.get(sql_getchiteu_pri);
		session.setAttribute("rs_chitieu_pri",rs_chitieu_pri);
		    session.setAttribute("thang", 0);
			session.setAttribute("nam", 0);
			session.setAttribute("tumuc","");
			session.setAttribute("toimuc","");
			session.setAttribute("tennhapp", tennhapp);
		response.sendRedirect(nextJSP);
	}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 Utility util =new Utility();
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	  //lay id nha phan phoi va ten nha pp
	    ChiTieuNhaPP chitieu=new ChiTieuNhaPP();
	    
	    chitieu.setUserId(userId);
	    
	    String nhappid=util.getIdNhapp(userId);
		String tennhapp=util.getTenNhaPP();
		dbutils db = new dbutils();
		
	    String thangtk=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang"));
	    String namtk=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nam"));
	    String tumuc=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tumuc"));
	    String toimuc=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("toimuc"));
	    String action =geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    String dvkd_fk=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("selectdvkd"));
	    
	    chitieu.setNhappId(nhappid);
	   /* //Thuc hien set list chitieuddkd 
	    List<ChiTieuDDKD> listchitieuddkd=new ArrayList<ChiTieuDDKD>();
		 String sql_getchitieuddkd="select pk_seq,ten from daidienkinhdoanh where npp_fk="+ nhappid;
		
		 ResultSet rs_chitieunpp=db.get(sql_getchitieuddkd);
		 try{
			if(rs_chitieunpp!=null){
				while(rs_chitieunpp.next()){
					ChiTieuDDKD ct=new ChiTieuDDKD();
					ct.setID("0");
					ct.setDDKDId(rs_chitieunpp.getString("pk_seq"));
					ct.setDDKDTen(rs_chitieunpp.getString("ten"));
					ct.setChiTieu(0);
					listchitieuddkd.add(ct);
				}
			}
		 }catch(Exception ex){
			 System.out.println("Error ChiTieuNppSvl 148  :"+ ex.toString() );
		 }*/
		 
			
			
//	    chitieu.setListChiTieuDDKD(listchitieuddkd);
	    
	    session.setAttribute("tennhapp", tennhapp);
	    session.setAttribute("nam",Integer.parseInt(namtk));
	    session.setAttribute("thang",Integer.parseInt(thangtk));
	    session.setAttribute("tumuc",tumuc);
	    session.setAttribute("toimuc",toimuc);
	    session.setAttribute("dvkdid",dvkd_fk);
	 // truyen qua form dvkd :
		String sql_getdvkd="select pk_seq,donvikinhdoanh from donvikinhdoanh where trangthai=1";
		ResultSet rs_dvkd= db.get(sql_getdvkd);
		session.setAttribute("rs_dvkd",rs_dvkd);
	    if(action.equals("search"))
	    {
	    	String sql_getchiteu_pri="select kbh.ten as kenhbanhang ,a.chitieu,c.donvikinhdoanh,thang,nam,songaylamviec,nsp.pk_seq,nsp.ten "+ 
			" from chitieu_nhapp_nhomsp a inner join chitieu b  on a.chitieu_fk=b.pk_seq  "+
			" inner join donvikinhdoanh c on c.pk_seq=b.dvkd_fk  "+
			 " inner join kenhbanhang kbh on kbh.pk_seq=b.kenh_fk   "+
			 " inner join nhomsanpham nsp on nsp.pk_seq=nhomsp_fk "+
			  " where a.npp_fk="+nhappid;

	    	String where ="";
	    	if(!thangtk.equals("0")){
	    		where=" and THANG="+ thangtk + " ";	
	    	}
	    	if(!namtk.equals("0")){
	    		where=where + " and NAM="+namtk +" ";
	    	}
	    	if(!tumuc.equals("")){
	    		where=where + " and a.chitieu >=" + tumuc +" " ;//in circumstance equal bigger 
	    	}
	    	if(!toimuc.equals("")){
	    		where=where + " and a.chitieu <=" + toimuc;
	    	}
	    	if(!dvkd_fk.equals("")){
	    		where=where + " and dvkd_fk =" + dvkd_fk;

	    	}
	    	if(where !=""){//if have search condition
	    		sql_getchiteu_pri=sql_getchiteu_pri+ where;	
	    	}
	    	ResultSet rs_chitieu_pri=db.get(sql_getchiteu_pri);
			session.setAttribute("rs_chitieu_pri",rs_chitieu_pri);
	    	//System.out.println("CAU LENH TIM KIEM :" + sql_getchiteu_pri);
	    	session.setAttribute("obj", chitieu);
	    	String nextJSP = "/AHF/pages/Distributor/ChiTieuNppPri.jsp";//default
	    	response.sendRedirect(nextJSP);
	    }
	}}

}
