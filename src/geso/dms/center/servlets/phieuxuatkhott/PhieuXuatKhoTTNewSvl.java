package geso.dms.center.servlets.phieuxuatkhott;

import geso.dms.center.beans.hoadon.IHoaDon;
import geso.dms.center.beans.hoadon.imp.HoaDon;
import geso.dms.center.beans.phieuxuatkhott.IPhieuXuatKhoTT;
import geso.dms.center.beans.phieuxuatkhott.imp.PhieuXuatKhoTT;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import B.ID;


public class PhieuXuatKhoTTNewSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PhieuXuatKhoTTNewSvl() {
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

	    Utility util = new Utility();
	   
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    //out.println(userId);
	    session.setAttribute("userTen", gettenuser(userId));
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	    String pxkId = util.getId(querystring);
	    dbutils db=new dbutils();
		//Thuc hien truyen qua cac resultset
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
		
		 String sql_khott="select pk_seq,ten from khott where trangthai!='2' ";
         ResultSet rs_khott=db.get(sql_khott);
         session.setAttribute("rs_khott", rs_khott);
         
         String sql_getdvkd="select pk_seq,donvikinhdoanh as ten from donvikinhdoanh where trangthai='1' ";//lay nhung don dat hang da chot,va chua chuyen het hang sang kho khac
			ResultSet rs_dvkd=db.get(sql_getdvkd);
			session.setAttribute("rs_dvkd", rs_dvkd);
			IPhieuXuatKhoTT phieuxuat=new PhieuXuatKhoTT(pxkId);
			IHoaDon hoadon=new HoaDon(phieuxuat.getDonDatHangID());
			session.setAttribute("phieuxuat",phieuxuat );
			session.setAttribute("obj",hoadon);
			if(action.equals("update")){
				String 	nextJSP = "/AHF/pages/Center/PhieuXuatKhoTTUpdate.jsp";
				response.sendRedirect(nextJSP);
			}else if(action.equals("chot")){
				String 	nextJSP = "/AHF/pages/Center/PhieuXuatKhoTTChot.jsp";
				response.sendRedirect(nextJSP);
			}else if(action.equals("display")){
				String 	nextJSP = "/AHF/pages/Center/PhieuXuatKhoTTDisplay.jsp";
				response.sendRedirect(nextJSP);
			}else if(action.equals("delete")){
				String sql="update phieuxuatkhott  set trangthai='2' where pk_seq= " + pxkId;
				db.update(sql);
				IPhieuXuatKhoTT   obj = new PhieuXuatKhoTT();
			       obj.init("");
				   session.setAttribute("obj", obj);
				  String  nextJSP = "/AHF/pages/Center/PhieuXuatKhoTT.jsp";
				   response.sendRedirect(nextJSP);
			}
	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		dbutils db = new dbutils();
		
		Utility util = new Utility();
		
	    String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));    
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		 session.setAttribute("userTen", gettenuser(userId));
		session.setAttribute("userId", userId);
	
		
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));		
 		String tenform=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tenform"));
 		String nextJSP="";
 		if(tenform.equals("newform")){
 			nextJSP = "/AHF/pages/Center/PhieuXuatKhoTTNew.jsp";
 		}else{
 		   nextJSP = "/AHF/pages/Center/PhieuXuatKhoTTUpdate.jsp";
 		}
 		IPhieuXuatKhoTT phieuxuat=new PhieuXuatKhoTT();
 		
 		String ddhid=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddhid")));
 		if(ddhid==null){
 			ddhid="";
 		}
	    phieuxuat.setId(id);
		String ghichu = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ghichu")));
		String lydoxuat=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("lydoxuat")));
		phieuxuat.setGhiChu(ghichu);
		phieuxuat.setLyDoXuat(lydoxuat);
		
		String ngayxuatkho = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngayxuatkho")));
		phieuxuat.setNgaylap(ngayxuatkho);
	    String khottid=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khottid")));
	    phieuxuat.setKhoTTId(khottid);
	    String SoSo=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("soso")));
	    phieuxuat.setSoId(SoSo);
    	String ngaytao=this.getDateTime();
		String ngaysua=ngaytao;			
		phieuxuat.setNgaytao(ngaytao);
		phieuxuat.setNgaysua(ngaysua);
		phieuxuat.setNguoitao(userId);
		phieuxuat.setNguoisua(userId);
    	//Thuc hien truyen qua cac resultset
		String sql_getnhapp="select pk_seq,ten from nhaphanphoi where trangthai!='2'";
		ResultSet rs_nhapp=db.get(sql_getnhapp);
		session.setAttribute("rs_nhapp", rs_nhapp);
		
		String sql_getnhacc="select pk_seq,ten from nhacungcap where trangthai!='2'";
		ResultSet rs_nhacc=db.get(sql_getnhacc);
		session.setAttribute("rs_nhacc", rs_nhacc);
		
		String sql_getkenhbanhang="select pk_seq,ten from kenhbanhang where trangthai!='2'";
		ResultSet rs_kenhbanhang=db.get(sql_getkenhbanhang);
		session.setAttribute("rs_kenhbanhang", rs_kenhbanhang);
		
		String sql_getdondathang="select pk_seq from dondathang where trangthai='3' and trangthai!='2' order by pk_seq desc";//lay nhung don dat hang da chot,va chua chuyen het hang sang kho khac
		ResultSet rs_dondathang=db.get(sql_getdondathang);
		session.setAttribute("rs_dondathang", rs_dondathang);
		
		 String sql_khott="select pk_seq,ten from khott where trangthai!='2' ";
         ResultSet rs_khott=db.get(sql_khott);
         session.setAttribute("rs_khott", rs_khott);
         
         String sql_getdvkd="select pk_seq,donvikinhdoanh as ten from donvikinhdoanh where trangthai='1'";//lay nhung don dat hang da chot,va chua chuyen het hang sang kho khac
			ResultSet rs_dvkd=db.get(sql_getdvkd);
			
			session.setAttribute("rs_dvkd", rs_dvkd);
			IHoaDon hoadon=new HoaDon(ddhid);
			phieuxuat.setDonDatHangId(ddhid);
			phieuxuat.setKhoTTId(hoadon.getKhottId());
			phieuxuat.setListSanPham();
			if(action.equals("loadddh")){
			
				session.setAttribute("phieuxuat",phieuxuat );
				session.setAttribute("obj",hoadon);
				response.sendRedirect(nextJSP);
				
			}else if(action.equals("save")){
			  if(phieuxuat.SavePhieuXuatKho()){
				  String sophieuxuat=phieuxuat.getID();
				  phieuxuat=new PhieuXuatKhoTT();
				  phieuxuat.setMessage("Ban Da Xuat Thanh Cong Phieu Xuat " + sophieuxuat+". Chon Don Dat Hang De Tiep Tuc Tao Moi Phieu Xuat");
				  hoadon=new HoaDon(); 
				  session.setAttribute("phieuxuat",phieuxuat );
					session.setAttribute("obj",hoadon);
					response.sendRedirect(nextJSP);
			  }else{
				  session.setAttribute("phieuxuat",phieuxuat );
					session.setAttribute("obj",hoadon);
				  response.sendRedirect(nextJSP);
			  }
			  
			}else if(action.equals("update")){
				if(phieuxuat.EditPhieuXuatKho()){
					 
					  phieuxuat=new PhieuXuatKhoTT();
					  phieuxuat.setMessage("Ban Da Sua Thanh Cong Phieu Xuat "+id +". Chon Don Dat Hang De Tiep Tuc Tao Moi Phieu Xuat");
					     hoadon=new HoaDon(); 
					  session.setAttribute("phieuxuat",phieuxuat );
						session.setAttribute("obj",hoadon);
						nextJSP = "/AHF/pages/Center/PhieuXuatKhoTTNew.jsp";
						response.sendRedirect(nextJSP);
					
				}else{
					nextJSP = "/AHF/pages/Center/PhieuXuatKhoTTUpdate.jsp";
					 session.setAttribute("phieuxuat",phieuxuat );
						session.setAttribute("obj",hoadon);
					  response.sendRedirect(nextJSP);
				}
				
			}else if(action.equals("chot")){
				  if(phieuxuat.ChotPhieuXuatKho()){
				   IPhieuXuatKhoTT   obj = new PhieuXuatKhoTT();
			       obj.init("");
				   session.setAttribute("obj", obj);
				   nextJSP = "/AHF/pages/Center/PhieuXuatKhoTT.jsp";
				   response.sendRedirect(nextJSP);
				   }else
				   {
					   nextJSP = "/AHF/pages/Center/PhieuXuatKhoTTChot.jsp";
					   session.setAttribute("phieuxuat",phieuxuat );
					   session.setAttribute("obj",hoadon);
					  response.sendRedirect(nextJSP);
				   }	
			} 
					
	}

}
