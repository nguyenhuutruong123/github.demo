package geso.dms.center.servlets.phieunhapkhott;

import geso.dms.center.beans.khott.imp.KhoTT;
import geso.dms.center.beans.nhaphanphoi.imp.Nhaphanphoi;
import geso.dms.center.beans.phieunhapkhott.imp.PhieuNhapKhoTT;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.donhangnhapp.imp.DonHangNPP;
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


public class PhieuNhapKhoTTSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
      Utility util=new Utility(); 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PhieuNhapKhoTTSvl() {
        super();
        // TODO Auto-generated constructor stub
    }
    private String gettenuser(String userId_){
		dbutils db=new dbutils();
		String sql_getnam="select ten from nhanvien where pk_seq="+ userId_;
		ResultSet rs_tenuser=db.get(sql_getnam);
		if(rs_tenuser!= null){
			try{
			  while(rs_tenuser.next()){
				  return rs_tenuser.getString("ten");
			  }
			}catch(Exception er){
				return "";
			}
		}
		return "";
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		// TODO Auto-generated method stub
		   request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    
		    PrintWriter out = response.getWriter();
		    HttpSession session = request.getSession();

		    String querystring = request.getQueryString();
		    String userId = util.getUserId(querystring);
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		    //
		    session.setAttribute("userTen", gettenuser(userId));
		    String id=util.getId(querystring);
		    String action = util.getAction(querystring);	    
          String sql_khott="select pk_seq,ten from khott where trangthai!='2'";
          dbutils db=new dbutils();
          ResultSet rs_khott=db.get(sql_khott);
          session.setAttribute("rs_khott", rs_khott); 
          String sql_getdvkd="select pk_seq,donvikinhdoanh as ten from donvikinhdoanh where trangthai!='2'";
          ResultSet rs_dvkd=db.get(sql_getdvkd);
          session.setAttribute("rs_dvkd", rs_dvkd); 
          String sql_getsanpham="select pk_seq,ma,ten from sanpham where trangthai!='2'";
          ResultSet rs_sanpham=db.get(sql_getsanpham);
          session.setAttribute("rs_sanpham", rs_sanpham);
          
          PhieuNhapKhoTT obj = new PhieuNhapKhoTT();	
          obj.setId(id);
		    if (action.equals("delete")){	   		
		      obj.DeletePhieuNhapKhoTT();
		      
		    }
		    obj.setListNhapKho("");
	    	//Data object is saved into session
	    	session.setAttribute("obj", obj);		
	    	// userId is saved into session
	    	session.setAttribute("userId", userId);
			session.setAttribute("tungay", "");
			session.setAttribute("denngay", "");
			session.setAttribute("dvkdid","");
			session.setAttribute("sanphamid","");
	    	String nextJSP = "/AHF/pages/Center/PhieuNhapKhoTT.jsp";
	    	response.sendRedirect(nextJSP);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		// TODO Auto-generated method stub
		String action=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		HttpSession session =request.getSession();
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		//tu ten dang nhap,lay ra nha phan phoi
		String trangthai=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
		session.setAttribute("userId", userId);
		String khott_=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khott")));
		session.setAttribute("khottid",khott_);
		 String sql_khott="select pk_seq,ten from khott where trangthai!='2'";
         dbutils db=new dbutils();
         ResultSet rs_khott=db.get(sql_khott);
         session.setAttribute("rs_khott", rs_khott);
         
		String dvkdid=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdid")));
		session.setAttribute("dvkdid",dvkdid);		
		String sanphamid=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sanphamid")));
		session.setAttribute("sanphamid",sanphamid);
         String sql_getdvkd="select pk_seq,donvikinhdoanh as ten from donvikinhdoanh where trangthai!='2'";
         ResultSet rs_dvkd=db.get(sql_getdvkd);
         session.setAttribute("rs_dvkd", rs_dvkd); 
         String sql_getsanpham="select pk_seq,ma,ten from sanpham where trangthai!='2'";
         ResultSet rs_sanpham=db.get(sql_getsanpham);
         session.setAttribute("rs_sanpham", rs_sanpham);
         
		String nextjsp;
		if(action.equals("new"))//TRUONG HOP THEM MOI
		{ 
			nextjsp="/AHF/pages/Center/PhieuNhapKhoTTNew.jsp";
			PhieuNhapKhoTT obj=new PhieuNhapKhoTT();
			session.setAttribute("check", "0");
			session.setAttribute("obj", obj);
		;
			response.sendRedirect(nextjsp);
		}
		else //Trong truong hop tim kiem 
		{
			
			session.setAttribute("type","1" );//set type =0 trong truong hop tim kiem
			PhieuNhapKhoTT obj=new PhieuNhapKhoTT();
			String tungay=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
			String denngay=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
			//cau lenh truyen vao de tim kiem
			String sql1="select a.pk_seq,a.ngaynhap,a.trangthai,a.ngaytao,a.ngaysua,NT.Ten as nguoitao,NS.ten as nguoisua,khott_fk,b.ten from phieunhapkhott a inner join khott "+
			" b on a.khott_fk=b.pk_seq  inner join nhanvien as NT on a.nguoitao=NT.pk_seq inner join nhanvien as NS on NS.Pk_seq=a.nguoisua where 1=1";
			
		    if(trangthai!="")
		    {
		    	sql1=sql1+ " and a.trangthai ='"+ trangthai+"'";
		    }
			session.setAttribute("tungay", "");
			session.setAttribute("denngay","");//Truyen qua mac dinh 2 gia tri trong
		    if(tungay!="") {
		    	sql1=sql1+ " and A.NGAYNHAP >= '"+tungay+"'"; 
		    	session.setAttribute("tungay", tungay);//truyen qua tu ngay co gia tri
		    }
		    if(denngay!=""){
		     	sql1=sql1+ " and A.NGAYNHAP <= '"+denngay+"'"; 
		     
				session.setAttribute("denngay", denngay);//truyen qua den ngay co gia tri
		    }
		    if(!khott_.equals("0")){
		    	sql1=sql1+ " and khott_fk="+khott_;
		    }
		    if(!dvkdid.equals("0")){
		    	sql1=sql1+" and exists(select distinct(b.pk_seq) from phieunhapkhott b inner join phieunhapkhott_sanpham a on a.phieunhapkhott_fk=b.pk_seq  inner join sanpham s on s.pk_seq=a.sanpham_fk where s.dvkd_fk="+dvkdid+")";
		    }
		    if(!sanphamid.equals("0")){
		    	sql1=sql1+"and exists(select distinct(b.pk_seq) from phieunhapkhott b inner join phieunhapkhott_sanpham a on a.phieunhapkhott_fk=b.pk_seq where sanpham_fk="+sanphamid+" )";
		    	}
		    System.out.println(sql1);
			obj.setListNhapKho(sql1);	
			obj.setTrangthai(trangthai);
			
			session.setAttribute("obj", obj);
			String nextJSP = "/AHF/pages/Center/PhieuNhapKhoTT.jsp";
			session.setAttribute("userId", userId);
			response.sendRedirect(nextJSP);
			
		}
	}

}
