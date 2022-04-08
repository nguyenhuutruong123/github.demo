package geso.dms.center.servlets.phieuxuatkhott;

import geso.dms.center.beans.hoadon.IHoaDon;
import geso.dms.center.beans.hoadon.imp.HoaDon;
import geso.dms.center.beans.phieuchuyenkhott.imp.PhieuChuyenKhoTT;
import geso.dms.center.beans.phieuxuatkhott.IPhieuXuatKhoTT;
import geso.dms.center.beans.phieuxuatkhott.imp.PhieuXuatKhoTT;
import geso.dms.distributor.beans.phieuxuatkho.imp.PhieuxuatkhoList;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import com.sun.org.apache.bcel.internal.generic.NEW;


public class PhieuXuatKhoTTSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       Utility util=new Utility();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PhieuXuatKhoTTSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	  
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	   
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    //out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	   
	    
	    String pxkId = util.getId(querystring);

	    if(action.equals("chotphieu"))
	    {
	    	dbutils db = new dbutils();
	    	try 
	    	{
				//db.getConnection().setAutoCommit(false);
				//if(!this.Chotphieuxuat(pxkId, db))
		    	//	return;
		    	//if(!this.createPth(pxkId, db))
		    	//	return;
		    //	db.getConnection().commit();
		    	//db.shutDown();
			} 
	    	catch (Exception e) {e.printStackTrace();}	    	
	    }
	   	    session.setAttribute("tungay", "");
	   	    session.setAttribute("denngay", "");
	     IPhieuXuatKhoTT   obj = new PhieuXuatKhoTT();
	     	obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Center/PhieuXuatKhoTT.jsp";
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
		String kho=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khochuyen")));
		 String sql_khott="select pk_seq,ten from khott where trangthai!='2' ";
         dbutils db=new dbutils();
         ResultSet rs_khott=db.get(sql_khott);
         session.setAttribute("rs_khott", rs_khott);
         
		String nextjsp;
		if(action.equals("new"))//TRUONG HOP THEM MOI
		{ 
			String sql_getnhapp="select pk_seq,ten from nhaphanphoi where trangthai!='2'";
			ResultSet rs_nhapp=db.get(sql_getnhapp);
			session.setAttribute("rs_nhapp", rs_nhapp);
			
			String sql_getnhacc="select pk_seq,ten from nhacungcap where trangthai!='2'";
			ResultSet rs_nhacc=db.get(sql_getnhacc);
			session.setAttribute("rs_nhacc", rs_nhacc);
			
			String sql_getkenhbanhang="select pk_seq,ten from kenhbanhang where trangthai!='2'";
			ResultSet rs_kenhbanhang=db.get(sql_getkenhbanhang);
			session.setAttribute("rs_kenhbanhang", rs_kenhbanhang);
			
			String sql_getdondathang="select pk_seq from dondathang where trangthai='3' and trangthai!='2'  order by pk_seq desc";//lay nhung don dat hang da chot,va chua chuyen het hang sang kho khac
			ResultSet rs_dondathang=db.get(sql_getdondathang);
			session.setAttribute("rs_dondathang", rs_dondathang);
			String sql_getdvkd="select pk_seq,donvikinhdoanh as ten from donvikinhdoanh where trangthai='1'";//lay nhung don dat hang da chot,va chua chuyen het hang sang kho khac
			ResultSet rs_dvkd=db.get(sql_getdvkd);
			session.setAttribute("rs_dvkd", rs_dvkd);
			nextjsp="/AHF/pages/Center/PhieuXuatKhoTTNew.jsp";
			IHoaDon obj=new HoaDon();
		
			session.setAttribute("obj", obj);
			IPhieuXuatKhoTT phieuxuat=new PhieuXuatKhoTT();
			session.setAttribute("phieuxuat", phieuxuat);
			response.sendRedirect(nextjsp);
			
		}else if(action.equals("search")){
			 
			String	sql1="select a.pk_Seq,a.ngayxuat,a.ngaytao,nt.ten as nguoitao,ns.ten as nguoisua,a.ngaysua,a.trangthai,ddh_fk from phieuxuatkhott  a inner join nhanvien as nt on nt.pk_seq=a.nguoitao inner join nhanvien as ns on ns.pk_seq=a.nguoisua  where 1=1 ";

			  if(trangthai!=""){
			   
			    	sql1=sql1+ " and a.trangthai ='"+ trangthai+"'";
			    }
			  session.setAttribute("tungay", "");
			  session.setAttribute("dengay", "");
			  
				String tungay=(String) util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
				String denngay=(String)util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
			    if(tungay!="") {
			    	sql1=sql1+ " and A.NGAYXUAT >= '"+tungay+"'"; 
			    	session.setAttribute("tungay", tungay);//truyen qua tu ngay co gia tri
			    }
			    if(denngay!=""){
			     	sql1=sql1+ " and A.NGAYXUAT <= '"+denngay+"'"; 
			     
					session.setAttribute("denngay", denngay);//truyen qua den ngay co gia tri
			    }
			    IPhieuXuatKhoTT   obj = new PhieuXuatKhoTT();
			    obj.setTrangthai(trangthai);
		     	obj.init(sql1);
		    
			session.setAttribute("obj", obj);
					
			String nextJSP = "/AHF/pages/Center/PhieuXuatKhoTT.jsp";
			response.sendRedirect(nextJSP);
			   System.out.println(sql1) ;
		}
	}

}
