package geso.dms.center.servlets.phieuchuyenkhott;

import geso.dms.center.beans.phieuchuyenkhott.IPhieuChuyenKhoTT;
import geso.dms.center.beans.phieuchuyenkhott.IPhieuChuyenKhoTT_SanPham;
import geso.dms.center.beans.phieuchuyenkhott.imp.PhieuChuyenKhoTT;
import geso.dms.center.beans.phieuchuyenkhott.imp.PhieuChuyenKhoTT_SanPham;
import geso.dms.center.beans.phieunhapkhott.imp.PhieuNhapKhoTT;
import geso.dms.center.beans.phieunhapkhott.imp.PhieuNhapKhoTT_SanPham;
import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import A.I;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;


public class PhieuChuyenKhoTTNewSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       Utility util =new Utility();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PhieuChuyenKhoTTNewSvl() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * Ham tra ve chuoi thoi gian theo dinh dang dd-MMM-yyyy
	 * @return
	 */
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
        
        
	}
	 /*
     * lay ten user
     */
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
		    session.setAttribute("userId", userId);
		    session.setAttribute("userTen", gettenuser(userId));
		    String id=util.getId(querystring);
		    String action = util.getAction(querystring);	    		  
		    String sql_khott="select pk_seq,ten from khott where trangthai!='2'";
		    dbutils db=new dbutils();
		    ResultSet rs_khott=db.get(sql_khott);   
			 ResultSet rs_khott_nhan=db.get(sql_khott);
	         session.setAttribute("rs_khott_nhan", rs_khott_nhan);
		    session.setAttribute("rs_khott", rs_khott); 		  	    
		    IPhieuChuyenKhoTT obj;
		    String nextJSP="";
		   
		    if(action.equals("update")){
		    	String sql_getphieunhap="select pk_seq from phieunhapkhott where trangthai='1' and trangthai!='3'";//lay nhung don hang da chot,va chua chuyen het hang sang kho khac
				ResultSet rs_getphieunhap=db.get(sql_getphieunhap);
				session.setAttribute("rs_phieunhap", rs_getphieunhap);
		    	obj = new PhieuChuyenKhoTT(id);	
		    	String madhsua=obj.getId_PhieuNhap();
		    	String sql_getdetailspecial="select available as soluong,masp,sp.ten,sp.pk_seq,isnull(pck_sp.soluong,0)as soluongchuyen from tonkhoicp ton inner join sanpham sp on ton.masp=sp.ma left join phieuchuyenkhott_sanpham pck_sp   on pck_sp.sanpham_fk=sp.pk_seq and phieuchuyenkhott_fk= "+id+"   where kho= "+obj.getKhoID_Chuyen() +" order by masp " ;
		    	//System.out.println("Special: "+sql_getdetailspecial);
		    	List<IPhieuChuyenKhoTT_SanPham>  spList = new ArrayList<IPhieuChuyenKhoTT_SanPham>();
		    	ResultSet 	rs_layhang=db.get(sql_getdetailspecial);
				if(rs_layhang!=null){
				   try{
						spList.clear();
					while(rs_layhang.next()){
						IPhieuChuyenKhoTT_SanPham sp = new PhieuChuyenKhoTT_SanPham();
						sp.setId("0");//Muon tam file it de luu lai checked nhung mat hang ben form them moi,neu checked=true thi luu gia tri nay bang 1,khi load moi ra mac dinh chua chon =0
						sp.setSanPhamId(rs_layhang.getString("pk_seq"));
						sp.setSoLuong( rs_layhang.getInt("soluong"));
						sp.setSoLuongChuyen(rs_layhang.getInt("soluongchuyen"));
						sp.setTenSanPham(rs_layhang.getString("ten"));
						sp.setMaSanPham(rs_layhang.getString("masp"));
						spList.add(sp);
				   }
				   }catch(Exception er){
					   
				   }
				}
		    	obj.setListSanPham(spList);
		    	session.setAttribute("madhsua", madhsua);
		    	session.setAttribute("obj", obj);		   
		    	 nextJSP = "/AHF/pages/Center/PhieuChuyenKhoTTUpdate.jsp";
		    
		    	
		    }else if(action.equals("display")){
		    	String sql_getphieunhap="select pk_seq from phieunhapkhott where trangthai='1' and trangthai!='3'";//lay nhung don hang da chot,va chua chuyen het hang sang kho khac
				ResultSet rs_getphieunhap=db.get(sql_getphieunhap);
				session.setAttribute("rs_phieunhap", rs_getphieunhap);
		    	obj = new PhieuChuyenKhoTT(id);	
		    	session.setAttribute("obj", obj);		   
		    	 nextJSP = "/AHF/pages/Center/PhieuChuyenKhoTTDisplay.jsp";
		    	
		    }else if(action.equals("chot")){
		    	obj = new PhieuChuyenKhoTT(id);	
		    	session.setAttribute("obj", obj);		   
		    	 nextJSP = "/AHF/pages/Center/PhieuChuyenKhoTTChot.jsp";	
		    }
		    
			response.sendRedirect(nextJSP);
		  
	    	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int loi=0;
		//khai bao ket noi
		  dbutils db=new dbutils();
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		String userTen=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userTen")));
		session.setAttribute("userTen", userTen);
			String madh=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));//truong truong hop don hang sua thi moi co id,nguoc lai th� khong co
		String action=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));//action =new : che do them, nguoc lai la che do sua;
		if(action==null){
			action="";
		}
		//System.out.println("Action trong truong hop nay la  : " + action);
		IPhieuChuyenKhoTT ctbean=new PhieuChuyenKhoTT();
	
		ctbean.setId(madh);
 		//this.out = response.getWriter();
		ctbean.setNguoisua(userId);
		String ngaysua = getDateTime();
		ctbean.setNgaysua(ngaysua);
    	String ngaytao=getDateTime();
    	ctbean.setNguoitao(userId);
    	ctbean.setNgaytao(ngaytao);
    	List<IPhieuChuyenKhoTT_SanPham>  spList = new ArrayList<IPhieuChuyenKhoTT_SanPham>();
    	if(action.equals("update") || action.equals("new")){
    	String[] masp = request.getParameterValues("masp");
    	String[] masanpham=request.getParameterValues("masanpham");
    	
    	String[] tensp =request.getParameterValues("tensp");
		String[] soluong = request.getParameterValues("soluong");
		String[] soluongchuyen = request.getParameterValues("soluongchuyen");

		  if(masp != null){					
			int m = 0;
			while(m < masp.length){
				IPhieuChuyenKhoTT_SanPham sp = new PhieuChuyenKhoTT_SanPham();
				if(masp[m] != ""){
					if(masp[m] != ""){ //chi them nhung san pham co so luong > 0
					   sp.setSanPhamId(masp[m]);
					   sp.setTenSanPham(tensp[m]);
					
					   try{
						   sp.setSoLuong(Integer.parseInt(soluong[m]));

					   }catch(Exception er){
					   }
					   try{
					   sp.setSoLuongChuyen(Integer.parseInt(soluongchuyen[m]));	  
					   }catch(Exception er){}
					 
					   sp.setMaSanPham(masanpham[m]);
					   spList.add(sp);
					}
				}
				m++;
			}	
		  }
			ctbean.setListSanPham(spList);
    	}
		  //truyen qua form nhung phieu nhap kho
		  String sql_getphieunhap="select pk_seq from phieunhapkhott where trangthai='1'";//lay nhung don hang da chot
			ResultSet rs_getphieunhap=db.get(sql_getphieunhap);
			session.setAttribute("rs_phieunhap", rs_getphieunhap);
		 //lay thong tin ve ngaynhapkho
		  	String ngaychuyenkho=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaychuyenkho")));
		  	ctbean.setNgayChuyen(ngaychuyenkho);
		  	String	nextJSP;
		  	String tenform=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tenform")));	
			String khott_nhan=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khottnhan")));
			ctbean.setKhoId_Nhan(khott_nhan);
			String phieunhapkho=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("phieunhapkho")));
			ctbean.setId_PhieuNhap(phieunhapkho);
			String khochuyenid=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khottchuyen")));
			ctbean.setKhoId_Chuyen(khochuyenid);
			ctbean.setNguoisua(userId);
			ctbean.setNguoitao(userId);
			
			//truyen qua mot resultset danh sach cac kho trung tam
		    String sql_getkhott="select pk_seq,ten from khott where trangthai!='2'";
			ResultSet rs_khott= db.get(sql_getkhott);
			 ResultSet rs_khott_nhan=db.get(sql_getkhott);
	         session.setAttribute("rs_khott_nhan", rs_khott_nhan);
			session.setAttribute("rs_khott",rs_khott);	
			//trong truong hop neu cap nhat file thi update se khong lay duoc
			if(tenform.equals("newform")){
				nextJSP= "/AHF/pages/Center/PhieuChuyenKhoTTNew.jsp";
			}else{
				nextJSP= "/AHF/pages/Center/PhieuChuyenKhoTTUpdate.jsp";
			}
			String madhsua=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("madhsua")));
	    	session.setAttribute("madhsua", madhsua);
			ctbean.setListSanPham(spList);
		  	if(action.equals("new")){ // mode them moi
			  if(ctbean.SavePhieuChuyenKhoTT()){
				  String sql_getdvkd="select pk_seq,donvikinhdoanh as ten from donvikinhdoanh where trangthai!='2'";
		           ResultSet rs_dvkd=db.get(sql_getdvkd);
		           session.setAttribute("rs_dvkd", rs_dvkd); 
		           String sql_getsanpham="select pk_seq,ma,ten from sanpham where trangthai!='2'";
		           ResultSet rs_sanpham=db.get(sql_getsanpham);
		           session.setAttribute("rs_sanpham", rs_sanpham);
		          session.setAttribute("dvkdid","");
		          session.setAttribute("sanphamid","");
			  //sau khi luu xong thi thuc hien quay lai form PhieuNhapKhoTT.jsp
				  ctbean = new PhieuChuyenKhoTT();	   
				  ctbean.setListChuyenKho("");
		    	//Data object is saved into session   
				session.setAttribute("tungay", "");
				session.setAttribute("denngay", "");
				nextJSP= "/AHF/pages/Center/PhieuChuyenKhoTT.jsp";
			  }
			 }else if(action.equals("update")){//truong hop update
				 if(ctbean.EditPhieuChuyenKhoTT()){
					  String sql_getdvkd="select pk_seq,donvikinhdoanh as ten from donvikinhdoanh where trangthai!='2'";
			           ResultSet rs_dvkd=db.get(sql_getdvkd);
			           session.setAttribute("rs_dvkd", rs_dvkd); 
			           String sql_getsanpham="select pk_seq,ma,ten from sanpham where trangthai!='2'";
			           ResultSet rs_sanpham=db.get(sql_getsanpham);
			           session.setAttribute("rs_sanpham", rs_sanpham);
			          session.setAttribute("dvkdid","");
			          session.setAttribute("sanphamid","");
					  //sau khi luu xong thi thuc hien quay lai form PhieuNhapKhoTT.jsp
						  ctbean = new PhieuChuyenKhoTT();	   
						  ctbean.setListChuyenKho("");
				    	//Data object is saved into session   
						session.setAttribute("tungay", "");
						session.setAttribute("denngay", "");
					  nextJSP= "/AHF/pages/Center/PhieuChuyenKhoTT.jsp";
					  }
			 }else if(action.equals("chot")){
				String khonhanid=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khonhanid")));
				ctbean.setKhoId_Nhan(khonhanid);
				ctbean.setId(madh);
				ctbean.setListSanPhamById();
				 if(ctbean.ChotPhieuChuyenKhoTT()){
					 
					  //sau khi luu xong thi thuc hien quay lai form PhieuNhapKhoTT.jsp
					  String sql_getdvkd="select pk_seq,donvikinhdoanh as ten from donvikinhdoanh where trangthai!='2'";
			           ResultSet rs_dvkd=db.get(sql_getdvkd);
			           session.setAttribute("rs_dvkd", rs_dvkd); 
			           String sql_getsanpham="select pk_seq,ma,ten from sanpham where trangthai!='2'";
			           ResultSet rs_sanpham=db.get(sql_getsanpham);
			           session.setAttribute("rs_sanpham", rs_sanpham);
			          session.setAttribute("dvkdid","");
			          session.setAttribute("sanphamid","");
						  ctbean = new PhieuChuyenKhoTT();	   
						  ctbean.setListChuyenKho("");
				    	//Data object is saved into session   
						session.setAttribute("tungay", "");
						session.setAttribute("denngay", "");
					  nextJSP= "/AHF/pages/Center/PhieuChuyenKhoTT.jsp";
				 }else{
					 String message=ctbean.getMessage();
					 ctbean = new PhieuChuyenKhoTT(madh);	
					 ctbean.setMessage(message);
					 nextJSP= "/AHF/pages/Center/PhieuChuyenKhoTTChot.jsp";
				 }
					 
			 }
			 else if(action.equals("layhang")){
				 //Lay san pham
				 String sql_layhang="";
				
					  sql_layhang="select available as soluong,masp,sp.ten,0 as soluongchuyen,sp.pk_seq from tonkhoicp ton inner join sanpham sp on ton.masp=sp.ma where kho=" + khochuyenid +" order by masp";
					
						System.out.println("Lay Hang : "+sql_layhang);
				ResultSet 	rs_layhang=db.get(sql_layhang);
				if(rs_layhang!=null){
				   try{
						spList.clear();
						while(rs_layhang.next()){
						IPhieuChuyenKhoTT_SanPham sp = new PhieuChuyenKhoTT_SanPham();
						sp.setSanPhamId(rs_layhang.getString("pk_seq"));
						sp.setSoLuong( rs_layhang.getInt("soluong"));
						sp.setSoLuongChuyen( rs_layhang.getInt("soluongchuyen"));
						sp.setTenSanPham(rs_layhang.getString("ten"));
						sp.setMaSanPham(rs_layhang.getString("masp"));
						spList.add(sp);
				   }
				   }catch(Exception er){
					   System.out.println(er.toString());
				   }
					ctbean.setListSanPham(spList);
				}

			 }
		  
		  session.setAttribute("obj", ctbean);
		  response.sendRedirect(nextJSP);
	}

}
