package geso.dms.center.servlets.phieuthanhtoantt;

import geso.dms.center.beans.phieuthanhtoantt.IPhieuThanhToanTT;
import geso.dms.center.beans.phieuthanhtoantt.IPhieuThanhToanTT_DH;
import geso.dms.center.beans.phieuthanhtoantt.imp.PhieuThanhToanTT;
import geso.dms.center.beans.phieuthanhtoantt.imp.PhieuThanhToanTT_DH;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

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


public class PhieuThanhToanTTNewSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       Utility util=new Utility();
       PrintWriter out; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PhieuThanhToanTTNewSvl() {
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
	    this.out  = response.getWriter();
		HttpSession session= request.getSession();
		String querystring=request.getQueryString();
		String userId=util.getUserId(querystring);
		String id=util.getId(querystring);
		session.setAttribute("userId", userId);
		session.setAttribute("userTen",gettenuser(userId));
		String action=util.getAction(querystring);
		IPhieuThanhToanTT obj=new PhieuThanhToanTT(id);
		session.setAttribute("obj", obj);
		
		System.out.println("Action Trong Truong Hop Nay La :"+action);
		if(action.equals("update")){
			String nextJSP = "/AHF/pages/Center/PhieuThanhToanTTUpdate.jsp";
			response.sendRedirect(nextJSP);
		}else if(action.equals("display")){
			String nextJSP = "/AHF/pages/Center/PhieuThanhToanTTDiplay.jsp";
			response.sendRedirect(nextJSP);
		}
		
	}
	//Get DateTime
	private String getDateTime() {
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
	
		String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
    	if(id ==null)
    		id ="";
    	//if(id.length()>0)
	    //IPhieuThanhToanTT obj = new PhieuThanhToanTT();
    	//else
    	IPhieuThanhToanTT obj = new PhieuThanhToanTT();
    	obj.setId(id);
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		obj.setUserId(userId);
		obj.setNguoiTao(userId);
		String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhappid")));
		
		//System.out.println("nha pp:"+nppId);
		
		if (nppId == null)
			nppId = "";
		obj.setIdNhaPhanPhoi(nppId);   
    	String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
		if (tungay == null)
			tungay = "";				
    	obj.setTungay(tungay);
    	
    	String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
		if (denngay == null)
			denngay = "";				
    	obj.setDenngay(denngay);
    	
    	String sotien = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thanhtoan")));
		if (sotien == null)
			sotien = "0";
		try{
    	obj.setSotien(Double.parseDouble(sotien));
		}catch(Exception err){
			obj.setSotien(0);
		}
    	String hinhthuc = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("hinhthuc")));
    	if(hinhthuc ==null)
    		hinhthuc ="1";
    	obj.setHinhthuc(hinhthuc);
    	
    	String ngaytt = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaythanhtoan")));
		if (ngaytt == null || ngaytt == "" )
			ngaytt = this.getDateTime();				
    	obj.setNgaythanhtoan(ngaytt);
    	obj.setNgayTao(this.getDateTime());
    	obj.setNgaySua(this.getDateTime());
    	obj.setNguoiSua(userId);
    	String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
    	if(diengiai == null)
    	   diengiai ="";
    	obj.setDiengiai(diengiai);
    	
    	String khuvucid=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucid")));
    	if(khuvucid==null){
    		khuvucid="";
    	}
    	obj.setKhuVucId(khuvucid);
    	String ngayhoadon1=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngayhoadon1")));
		String ngayhoadon= util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngayhoadon")));
		obj.setTungay(ngayhoadon);
		obj.setDenngay(ngayhoadon1);
    	String action =  geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
    	
    	List<IPhieuThanhToanTT_DH> listdonhang=new ArrayList<IPhieuThanhToanTT_DH>();
    	if(action.equals("new") || action.equals("update")){
     		String[] thanhtoan1= request.getParameterValues("thanhtoan1");
     		String[] tiendonhang1= request.getParameterValues("tiendonhang1");
     		String[] dathanhtoan1= request.getParameterValues("dathanhtoan1");
     		String[] valuecheck= request.getParameterValues("valuecheck");
     		String[] iddonhang= request.getParameterValues("iddonhang");
     		String[] ngaydathang= request.getParameterValues("ngaydathang");
     		if(thanhtoan1!=null){
        	//System.out.println("tong so chuoi2:"+thanhtoan.length);
     			for(int i=0 ; i<thanhtoan1.length ;i++)
     			{
     				IPhieuThanhToanTT_DH donhang=new PhieuThanhToanTT_DH();
     				//System.out.println("thanhtoan 1:"+thanhtoan[i]);
     				try{
     				donhang.setDaThanhToan( Double.parseDouble(dathanhtoan1[i]));
     				}catch(Exception er){
     				donhang.setDaThanhToan(0);	
     				}
     				donhang.setId(valuecheck[i]);
     				donhang.setIdDonHang(iddonhang[i]);
     				donhang.setNgayDat(ngaydathang[i]);
     				try{
     				donhang.setTienDonHang(Double.parseDouble(tiendonhang1[i]));
     				}catch(Exception er){
     					donhang.setTienDonHang(0);
     				}
     				donhang.setTienThanhToan(Double.parseDouble(thanhtoan1[i]));
     				listdonhang.add(donhang);
        		}
        	}
    		}
     			obj.setListDonHang(listdonhang);
     			String formname=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("formname"));
     			String nextJSP=""; 
     		if(formname.equals("newform")){
     			nextJSP = "/AHF/pages/Center/PhieuThanhToanTTNew.jsp";
     		}else{
     			nextJSP = "/AHF/pages/Center/PhieuThanhToanTTUpdate.jsp";
     		}
     		if(action.equals("loadnpp")){
     		
     			 session.setAttribute("obj", obj);	
				 response.sendRedirect(nextJSP);
     		}
     		else if(action.equals("submit"))
    		{ 
    			dbutils db=new dbutils();
    			/**
    			 * thuc hien kiem tra khach hang nay co con nhung phieu thanh toan ma chua chot khong,chua chot =0,va phai chua duoc xoa
    			 *Nếu đang tồn tại thì không cho thêm mới nữa, vì quy định 1 nhà phân phối chỉ được thanh toán khi những phiếu thanh toán cũ phải được thanh toán
    			 * chốt hết,Trong trường hợp sửa thì vẫn cho load ra bình thường, vì chỉ tồn tại <=1 phiếu có thể sửa của 1 nhà phân phối
    			 */
    			String sql_getcountdh="select count(pk_seq)as count from phieuthanhtoantt where nhapp_fk="+ nppId +" and trangthai='0'";
    			
    			System.out.println("Dem So Don Hang : "+sql_getcountdh);
    			ResultSet rs_getdata=db.get(sql_getcountdh);
    			try{
    				rs_getdata.next();
    				if(rs_getdata.getInt("count")!=0 && formname.equals("newform")){
    					obj.setMessage("Nhà Phân Phối Này Cần Phải Chốt Những Phiếu Thanh Toán Cũ Để Có Thể Tạo Phiếu Mới");
    					obj.setIdNhaPhanPhoi("");
    				}else{
    					//Thuc hien loc ra nhung don hang cua nha phan phoi
        				obj.setListDonHangInit();
        				//thuc hien lay tien no cu cua nha phan phoi
        				obj.setNoCuInit();
    				}
    			}catch(Exception er){
    			   System.out.println("Error PhieuThanhToanTTNewSvl :" + er.toString());
    			}
   			      session.setAttribute("obj", obj);	
				  response.sendRedirect(nextJSP);
    		}
    		else if(action.equals("new")){	//truong hop them moi
    				if(!obj.saveThanhToan())
    				{
    					session.setAttribute("obj", obj);	
    					response.sendRedirect(nextJSP);
		    		}
    				else{
		    			IPhieuThanhToanTT dh = new PhieuThanhToanTT();
		    			dh.setUserId(userId); 
		    		    dh.setListThanhToan("");
		    			session.setAttribute("obj", dh);		    					
		    			 nextJSP = "/AHF/pages/Center/PhieuThanhToanTT.jsp";
		    			response.sendRedirect(nextJSP);
    				}
    		}else if(action.equals("update")){//truong hop update
    			if(!obj.UpdateThanhToan()){
    				//truong hop sua khong duoc
    				session.setAttribute("obj", obj);	
					response.sendRedirect(nextJSP);	
    			}else{//sua thanh cong
    				IPhieuThanhToanTT dh = new PhieuThanhToanTT();
	    			dh.setUserId(userId); 
	    		    dh.setListThanhToan("");
	    			session.setAttribute("obj", dh);		    					
	    			 nextJSP = "/AHF/pages/Center/PhieuThanhToanTT.jsp";
	    			response.sendRedirect(nextJSP);
    			}
    		}
	}
    			
}
