package geso.dms.center.servlets.hoadon;

import geso.dms.center.beans.hoadon.IHoaDon;
import geso.dms.center.beans.hoadon.imp.HoaDon;
import geso.dms.center.beans.phieuchuyenkhott.IPhieuChuyenKhoTT;
import geso.dms.center.beans.phieuchuyenkhott.imp.PhieuChuyenKhoTT;
import geso.dms.center.beans.phieunhapkhott.imp.PhieuNhapKhoTT;
import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class HoaDonSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HoaDonSvl() {
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
		    
		    HttpSession session = request.getSession();

		    String querystring = request.getQueryString();
		    
		    Utility util=new Utility();
		    
		    String userId = util.getUserId(querystring);
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		    //
		    session.setAttribute("userTen", gettenuser(userId));
		    /*
		    String id=util.getId(querystring);
		    String action = util.getAction(querystring);	
		    
		    String sql_khott="select pk_seq,ten from khott where trangthai!='2'";
		    dbutils db=new dbutils();
		    ResultSet rs_khott=db.get(sql_khott);
		    session.setAttribute("rs_khott", rs_khott); 
		    String sql_getnhapp="select pk_seq,ten  from nhaphanphoi where trangthai!='2'";
		    ResultSet rs_nhapp=db.get(sql_getnhapp);
		    session.setAttribute("rs_nhapp", rs_nhapp); 

         
		    IHoaDon obj = new HoaDon();	
		    obj.setId(id);
		    obj.setListHoaDon("");
		    obj.setTrangthai("");
	    	//Data object is saved into session
	    	session.setAttribute("obj", obj);		
	    	// userId is saved into session
	    	session.setAttribute("userId", userId);
			session.setAttribute("tungay", "");
			session.setAttribute("denngay", "");
			session.setAttribute("nppid","");
			session.setAttribute("khottid","");
	    	String nextJSP = "/AHF/pages/Center/HoaDon.jsp";
	    	response.sendRedirect(nextJSP);
	    	*/
		    dbutils db=new dbutils();
		    String sql_getnhapp="select pk_seq,ten from nhaphanphoi where trangthai!='2'";
			ResultSet rs_nhapp=db.get(sql_getnhapp);
			session.setAttribute("rs_nhapp", rs_nhapp);
			
			String sql_getnhacc="select pk_seq,ten from nhacungcap where trangthai!='2'";
			ResultSet rs_nhacc=db.get(sql_getnhacc);
			session.setAttribute("rs_nhacc", rs_nhacc);
			
			String sql_getkenhbanhang="select pk_seq,ten from kenhbanhang where trangthai!='2'";
			ResultSet rs_kenhbanhang=db.get(sql_getkenhbanhang);
			session.setAttribute("rs_kenhbanhang", rs_kenhbanhang);
			
			String sql_getdondathang="select pk_seq from dondathang where trangthai='1'";//lay nhung don dat hang da chot,va chua chuyen het hang sang kho khac
			ResultSet rs_dondathang=db.get(sql_getdondathang);
			session.setAttribute("rs_dondathang", rs_dondathang);
			String sql_getdvkd="select pk_seq,donvikinhdoanh as ten from donvikinhdoanh where trangthai='1'";//lay nhung don dat hang da chot,va chua chuyen het hang sang kho khac
			ResultSet rs_dvkd=db.get(sql_getdvkd);
			session.setAttribute("rs_dvkd", rs_dvkd);
			String nextjsp="/AHF/pages/Center/HoaDonNew.jsp";
			IHoaDon obj=new HoaDon();
			//mac dinh cho loai hoa don =0
			session.setAttribute("loaihoadon","0");
			session.setAttribute("obj", obj);
			response.sendRedirect(nextjsp); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		HttpSession session =request.getSession();
		
		Utility util = new Utility();
		
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
			
			String sql_getdondathang="select pk_seq from dondathang where trangthai='1'";//lay nhung don dat hang da chot,va chua chuyen het hang sang kho khac
			ResultSet rs_dondathang=db.get(sql_getdondathang);
			session.setAttribute("rs_dondathang", rs_dondathang);
			String sql_getdvkd="select pk_seq,donvikinhdoanh as ten from donvikinhdoanh where trangthai='1'";//lay nhung don dat hang da chot,va chua chuyen het hang sang kho khac
			ResultSet rs_dvkd=db.get(sql_getdvkd);
			session.setAttribute("rs_dvkd", rs_dvkd);
			nextjsp="/AHF/pages/Center/HoaDonNew.jsp";
			IHoaDon obj=new HoaDon();
			//mac dinh cho loai hoa don =0
			session.setAttribute("loaihoadon","0");
			session.setAttribute("obj", obj);
			response.sendRedirect(nextjsp);
			
		}
		else //Trong truong hop tim kiem 
		{
			
			session.setAttribute("type","1" );//set type =0 trong truong hop tim kiem
			IPhieuChuyenKhoTT obj=new PhieuChuyenKhoTT();
			String tungay=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
			String denngay=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
			//cau lenh truyen vao de tim kiem
			String sql1="select a.pk_seq,a.ngaychuyen,a.trangthai,a.ngaytao,nt.ten as nguoitao," +
				" a.ngaysua,ns.ten as nguoisua,a.khottchuyen_fk,kc.ten as tenkhochuyen,"+
				" kn.ten as tenkhonhan,a.khottnhan_fk,a.phieunhapkhott_fk from phieuchuyenkhott a "+
				" inner join nhanvien ns on ns.pk_seq=a.nguoisua inner join nhanvien nt on nt.pk_seq=a.nguoitao "+
				" inner join khott kc on kc.pk_seq=a.khottchuyen_fk inner join khott kn on kn.pk_seq=a.khottnhan_fk ";

		    if(trangthai=="")
		    {
		    	sql1=sql1+ "  where a.trangthai!='2' ";//kh�c 2 co nghia la load tat ca tru nhung cai da xoa
		    }
		    else
		    {
		    	sql1=sql1+ " where a.trangthai ='"+ trangthai+"'";
		    }
			session.setAttribute("tungay", "");
			session.setAttribute("denngay","");//Truyen qua mac dinh 2 gia tri trong
		    if(tungay!="") {
		    	sql1=sql1+ " and A.ngaychuyen >= '"+tungay+"'"; 
		    	session.setAttribute("tungay", tungay);//truyen qua tu ngay co gia tri
		    }
		    if(denngay!=""){
		     	sql1=sql1+ " and A.ngaychuyen <= '"+denngay+"'"; 
		     
				session.setAttribute("denngay", denngay);//truyen qua den ngay co gia tri
		    }
		    if(!kho.equals("0")){
		    	sql1=sql1+ " and khottchuyen_fk="+kho;
		    }
		    
		   
			obj.setListChuyenKho(sql1);	
			obj.setTrangthai(trangthai);
			
			session.setAttribute("obj", obj);
			String nextJSP = "/AHF/pages/Center/PhieuChuyenKhoTT.jsp";
			session.setAttribute("userId", userId);
			response.sendRedirect(nextJSP);
		}
	}

}
