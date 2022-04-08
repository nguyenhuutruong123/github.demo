package geso.dms.center.servlets.hoadongtgt;

import geso.dms.center.beans.hoadon.IHoaDon;
import geso.dms.center.beans.hoadon.imp.HoaDon;
import geso.dms.center.beans.hoadongtgt.IHoaDonGTGT;
import geso.dms.center.beans.hoadongtgt.imp.HoaDonGTGT;
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


public class HoaDonGTGTNewSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HoaDonGTGTNewSvl() {
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
		
		String sql_getdondathang="select pk_seq from dondathang where trangthai='4' and trangthai!='2' order by pk_seq desc";//lay nhung don dat hang da xuat kho
		ResultSet rs_dondathang=db.get(sql_getdondathang);
		session.setAttribute("rs_dondathang", rs_dondathang);
		
		 String sql_khott="select pk_seq,ten from khott where trangthai!='2' ";
         ResultSet rs_khott=db.get(sql_khott);
         session.setAttribute("rs_khott", rs_khott);
         
         String sql_getdvkd="select pk_seq,donvikinhdoanh as ten from donvikinhdoanh where trangthai='1'";//lay nhung don dat hang da chot,va chua chuyen het hang sang kho khac
			ResultSet rs_dvkd=db.get(sql_getdvkd);
			session.setAttribute("rs_dvkd", rs_dvkd);
			IHoaDonGTGT phieuxuat=new HoaDonGTGT(pxkId);
			
			IHoaDon hoadon=new HoaDon(phieuxuat.getIdDonDatHang());
			phieuxuat.setListSanPhamDDH(phieuxuat.getIdDonDatHang());
			session.setAttribute("hoadon",phieuxuat );
			session.setAttribute("obj",hoadon);
			if(action.equals("update")){
				String 	nextJSP = "/AHF/pages/Center/HoaDonGTGTUpdate.jsp";
				response.sendRedirect(nextJSP);
			}else if(action.equals("chot")){
				session.setAttribute("type", "0");
				String 	nextJSP = "/AHF/pages/Center/HoaDonGTGTDisplay.jsp";
				response.sendRedirect(nextJSP);
			}else if(action.equals("display")){
				
				session.setAttribute("type", "1");
				String 	nextJSP = "/AHF/pages/Center/HoaDonGTGTDisplay.jsp";
				response.sendRedirect(nextJSP);
			}else if(action.equals("delete")){
				String sql="update hoadon  set trangthai='2' where pk_seq= " + pxkId;
				session.setAttribute("userId", userId);
				session.setAttribute("tungay", "");
				session.setAttribute("denngay", "");
				session.setAttribute("nhappid","" );
				
				db.update(sql);
				IHoaDonGTGT   obj = new HoaDonGTGT();
				obj.setTrangthai("0");
			       obj.setListHoaDonGTGT("");
				   session.setAttribute("obj", obj);
				  String  nextJSP = "/AHF/pages/Center/HoaDonGTGT.jsp";
				   response.sendRedirect(nextJSP);
			}
	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
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
 		String tenform=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tenform")));
 		String nextJSP="";
 		if(tenform.equals("newform")){
 			nextJSP = "/AHF/pages/Center/HoaDonGTGTNew.jsp";
 		}else{
 		   nextJSP = "/AHF/pages/Center/HoaDonGTGTUpdate.jsp";
 		}
 		IHoaDonGTGT hoadongtgt=new HoaDonGTGT();
 		
 		String ddhid=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddhid")));
 		if(ddhid==null){
 			ddhid="";
 		}
 		hoadongtgt.setId(id);
 		
		String ngayxuathd = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngayxuathd")));
		String sohoadon=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sohoadon")));
		hoadongtgt.setNgaygiaodich(ngayxuathd);
		hoadongtgt.setSoHoaDon(sohoadon);
		hoadongtgt.setVat(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("VAT"))));
    	String ngaytao=this.getDateTime();
		String ngaysua=ngaytao;			
		hoadongtgt.setNgaytao(ngaytao);
		hoadongtgt.setNgaysua(ngaysua);
		hoadongtgt.setNguoitao(userId);
		hoadongtgt.setNguoisua(userId);
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
		
		String sql_getdondathang="select pk_seq from dondathang where trangthai='4' and trangthai!='2'  order by pk_seq desc";//lay nhung don dat hang da chot,va chua chuyen het hang sang kho khac
		ResultSet rs_dondathang=db.get(sql_getdondathang);
		session.setAttribute("rs_dondathang", rs_dondathang);
		
		 String sql_khott="select pk_seq,ten from khott where trangthai!='2' ";
         ResultSet rs_khott=db.get(sql_khott);
         session.setAttribute("rs_khott", rs_khott);
         
            String sql_getdvkd="select pk_seq,donvikinhdoanh as ten from donvikinhdoanh where trangthai='1'";//lay nhung don dat hang da chot,va chua chuyen het hang sang kho khac
			ResultSet rs_dvkd=db.get(sql_getdvkd);
			session.setAttribute("rs_dvkd", rs_dvkd);
			IHoaDon hdBean=new HoaDon(ddhid);
			hoadongtgt.setIdDonHangDat(ddhid);
			hoadongtgt.setListSanPhamDDH(ddhid);
			if(action.equals("loadddh")){
			
				session.setAttribute("hoadon",hoadongtgt );
				session.setAttribute("obj",hdBean);
				response.sendRedirect(nextJSP);
			}else if(action.equals("save")){
			  if(hoadongtgt.SaveHoaDonGTGT() ){
				  IHoaDonGTGT obj = new HoaDonGTGT();	
				    obj.setId(id);
				    obj.setListHoaDonGTGT("");
				    obj.setTrangthai("");
			    	//Data object is saved into session
			    	session.setAttribute("obj", obj);		
			    	// userId is saved into session
			    	session.setAttribute("userId", userId);
					session.setAttribute("tungay", "");
					session.setAttribute("denngay", "");

			     nextJSP = "/AHF/pages/Center/HoaDonGTGT.jsp";
			    	response.sendRedirect(nextJSP);
			  }else{
				  session.setAttribute("hoadon",hoadongtgt );
					session.setAttribute("obj",hdBean);
				  response.sendRedirect(nextJSP);
			  }
			  
			}else if(action.equals("update")){
				if(hoadongtgt.EditHoaDonGTGT()){
					IHoaDonGTGT obj = new HoaDonGTGT();	
				    obj.setId(id);
				    obj.setListHoaDonGTGT("");
				    obj.setTrangthai("");
			    	//Data object is saved into session
			    	session.setAttribute("obj", obj);		
			    	// userId is saved into session
			    	session.setAttribute("userId", userId);
					session.setAttribute("tungay", "");
					session.setAttribute("denngay", "");
			        nextJSP = "/AHF/pages/Center/HoaDonGTGT.jsp";
				    response.sendRedirect(nextJSP);
				}else{
					 session.setAttribute("hoadon",hoadongtgt );
						session.setAttribute("obj",hdBean);
					  response.sendRedirect(nextJSP);
				}
				
			}else if(action.equals("chot")){
				if(hoadongtgt.ChotHoaDonGTGT()){
					 
					IHoaDonGTGT obj = new HoaDonGTGT();	
				    obj.setId(id);
				    obj.setListHoaDonGTGT("");
				    obj.setTrangthai("");
			    	//Data object is saved into session
			    	session.setAttribute("obj", obj);		
			    	// userId is saved into session
			    	session.setAttribute("userId", userId);
					session.setAttribute("tungay", "");
					session.setAttribute("denngay", "");
			        nextJSP = "/AHF/pages/Center/HoaDonGTGT.jsp";
				    response.sendRedirect(nextJSP);
				}else{
					 session.setAttribute("hoadon",hoadongtgt );
						session.setAttribute("obj",hdBean);
					  response.sendRedirect(nextJSP);
				} 
			} 
	}

}
