/**
 * Author : KHOAND 
 * class name : MuaHangNhaPPKhacSvl
 * Date : 2011-10-20
 */

package geso.dms.distributor.servlets.donhangnpp;

import geso.dms.distributor.beans.donhangnhapp.IDonhangnpp;
import geso.dms.distributor.beans.donhangnhapp.ISanPhamDhNpp;
import geso.dms.distributor.beans.donhangnhapp.imp.DonHangNPP;
import geso.dms.distributor.beans.donhangnhapp.imp.SanPhamDhNpp;
import geso.dms.distributor.db.sql.dbutils;

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

public class DonHangNppUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/*
	 * khai bao spthieuList dang hashtable de luu cac san pham trong chi tiet dat hang co so luong vuot qua dinh muc trong kho
	 */
	

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DonHangNppUpdateSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	/**
	 * Ham lay id cua san pham dua vao ma
	 */
	private String get_idsanpham(String masp)
	{
		String id="";
		dbutils db=new dbutils();
		String sql="select pk_seq from sanpham where ma ='"+masp+"'";
		ResultSet rs=db.get(sql);
		try{
		
		if(rs.next())
		{
			return rs.getString("pk_seq");
		}
		}
		catch(Exception er){
			System.out.print("Error DonHangNppUpdateSvl : line 82 -" + er.toString());
			return "";
		}
		return id;
	}
	/**
	 * Phuong thuc lay chuoi tim kiem
	 * 
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		IDonhangnpp dhbean;
		List<ISanPhamDhNpp> spList;
		PrintWriter out; 
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		
		userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
		String madh=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id"));//truong truong hop don hang sua thi moi co id,nguoc lai th� khong co
		String action=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));//action =new : che do them, nguoc lai la che do sua;
			dhbean=new DonHangNPP();
		dhbean.setUserId(userId);//sau khi set userid thi chuong trinh se set mancc,va ten ncc cho doi tuong dhbean(IDonhangnpp)
		dhbean.setId(madh);
		String manhapp_ban=cutil.getIdNhapp(userId);
		dhbean.setNppId_Ban(manhapp_ban);
		out = response.getWriter();
	    String nppId_mua ;//khai bao nha phan phoi mua hang
		 nppId_mua = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
		if (nppId_mua == null)
			nppId_mua = "";
		dhbean.set_NppId_Mua(nppId_mua);
		String VAT = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("VAT"));
    	if (VAT == null)
    		VAT = "";    	
    	dhbean.setVAT(VAT);
		
		dhbean.setNguoisua(userId);
		String ngaysua = getDateTime();
    	dhbean.setNgaysua(ngaysua);
    	String ngaytao=getDateTime();
    	dhbean.setNgaytao(ngaytao);
    	String kbhid=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhbh"));
    	String khoid=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khobh"));
    	dhbean.setKho(khoid);//mac dinh la kenh kho hang.
    	dhbean.setKenhBanHang(kbhid);// Can phai tim hieu them de set kenh 
    	
    	dhbean.createRs();
    	String ngaygd = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));//tungay la ten cua o textbox ben form donhangnppnew
    	if (ngaygd == null || ngaygd == "")
			ngaygd = this.getDateTime();			
    	dhbean.setNgayGiaoDich(ngaygd);
    	String nhappmua=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhappmua"));
    	dhbean.set_NppId_Mua(nhappmua);
    	
    	String formname=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("formname"));
    	
    	String[] masp = request.getParameterValues("masp");
		String[] tensp = request.getParameterValues("tensp");
		String[] soluong = request.getParameterValues("soluong");
		String[] donvitinh = request.getParameterValues("donvitinh");
		String[] dongia = request.getParameterValues("dongia");
		String[] thanhtien = request.getParameterValues("thanhtien");
		dbutils db=new dbutils();
		spList = new ArrayList<ISanPhamDhNpp>();
		  if(masp != null){		
			
			int m = 0;
			while(m < masp.length){
				ISanPhamDhNpp sanpham = new SanPhamDhNpp();
				if(masp[m] != ""){
					if(soluong[m] != ""){ //chi them nhung san pham co so luong > 0
						sanpham.setMaSanPham(masp[m]);
						//System.out.println("ma san pham"+ masp[m]);
						sanpham.setTenSanPham(tensp[m]);
						sanpham.setSoLuong(Integer.parseInt(soluong[m].replaceAll(",", "")));
						sanpham.setDVT(donvitinh[m]);
						//sanpham.setDonHangNPP(DongHangNpp)
						sanpham.setGiaMua(Double.parseDouble(dongia[m].replaceAll(",", "")));
						sanpham.setThanhTien(Double.parseDouble(thanhtien[m].replaceAll(",", "")));
						spList.add(sanpham);
					}
				}
				m++;
			}	
		  }
		  dhbean.setListSanPhamNew(spList);//add cac chi tiet san pham  vao don hang
		//Kiem tra so luong cua nhung san pham ban co ton du trong kho khong?
		  java.util.Hashtable<String,Integer> spThieuList = new java.util.Hashtable<String,Integer>();
		  if(masp!= null){
				int m = 0;			
				while(m < masp.length)
				{			
					String sl="0";
					int soluongcu=0;
					if(masp[m].length() > 0)
					{	 
						String query = "select available from nhapp_kho where npp_fk='" + manhapp_ban +"' and kho_fk = '"+khoid+"' ";
						
						query = query + " and sanpham_fk in (select pk_seq from sanpham where ma='" + masp[m].trim() + "')";
						//System.out.println("DonHangNppUpdateSvl : 158  : "+query);
						ResultSet slspAvailable = db.get(query);
						if(slspAvailable != null)
						{
							try
							{
								slspAvailable.next();
								sl = slspAvailable.getString("available");
								slspAvailable.close();
							}
							catch(Exception ex)
							{
									System.out.println( "Error  : 156 DonHangNppUpdateSvl : " + ex.toString() );
							}
						}
						//Truong hop dang thuc hien sua hangton=hangtontrongkho+soluong hang tren phieu dang sua, 
						if(dhbean.getId()!="")
						{
								String sql_hangtrenphieu="select soluong as soluongcu from donhangnpp_sanpham where donhangnpp_fk="+dhbean.getId()+" and sanpham_fk= "+ get_idsanpham(masp[m].trim());
								ResultSet rs_hangcu=db.get(sql_hangtrenphieu);
								//System.out.println(" Cua lenh : " +sql_hangtrenphieu);
								if(rs_hangcu!=null)
								{
									try
									{
										if(rs_hangcu.next())
										{
									    	soluongcu = rs_hangcu.getInt("soluongcu");
										    rs_hangcu.close();
										}
									}
									catch(Exception ex)
									{
											System.out.println( "Error  : 192 DonHangNppUpdateSvl : " + ex.toString() );
									}
								}
						}
					}
					int soluongnhap=0;
					try
					{
						soluongnhap=Integer.parseInt(soluong[m]);
					}
					catch(Exception er)
					{
						
					}
					int soluongton=0;
					try
					{
					 soluongton=Integer.parseInt(sl);
					}
					catch(Exception er)
					{
						
					}
					 if(soluongnhap>soluongton+soluongcu)//truong hop them moi, chi can xem luong hang con ton trong kho co du voi luong xuat khong
					{ 
							spThieuList.put(masp[m],soluongton);
					}
					m++;
				}
		  }
		  dhbean.setSPThieuList(spThieuList);
		  session.setAttribute("obj", dhbean);
		  session.setAttribute("userId", userId);
		  dhbean.createRs();
		  if(action.equals("chotdonhang")) {
			  dhbean.setTrangthai("1");//1 la chot don hang
		  }
		  else{
			  dhbean.setTrangthai("0");//0 chua chot don hang
		  }
		  //System.out.println("ten dang nhap moi la ;" +userId );
		  if(action.equals("new"))//Truong hop su ly save 1 donhang moi
		  {
			  dhbean.setNguoitao(userId);// Truong hop them moi thi moi set thuoc tinh nguoi tao
			  if( dhbean.saveDonHangNPP())//da luu thanh cong
			  {
				
				  dhbean.SetDonHangNPP("");
				  
				  String nextJSP = "/AHF/pages/Distributor/DonHangNhaPP.jsp";
				  response.sendRedirect(nextJSP);
			  }
			  else//truong hop luu khong dc
			  {
				
				  String nextJSP = "/AHF/pages/Distributor/DonHangNhaPPNew.jsp";
				  response.sendRedirect(nextJSP);
			  }
			  
		  }
		  else if(action.equals("update") || action.equals("chotdonhang"))
		  {
			  //set tinh trang cua don hang
			  
			  if(dhbean.editDonHangNPP())
			  {
				  dhbean.SetDonHangNPP("");
				
				  String nextJSP = "/AHF/pages/Distributor/DonHangNhaPP.jsp";
					 response.sendRedirect(nextJSP);
			  }
			  else
			  {
				
				// System.out.println("Khong du so luong : " + dhbean.getthongbao());
				  String nextJSP = "/AHF/pages/Distributor/DonHangNPPUpdate.jsp";
					 response.sendRedirect(nextJSP);
			  }
			  
		  }else{
		 
			  String nextJSP="";
			 if(!formname.equals("formnew")){
				  nextJSP = "/AHF/pages/Distributor/DonHangNPPUpdate.jsp";
			 }else{
				   nextJSP = "/AHF/pages/Distributor/DonHangNhaPPNew.jsp";
			 }
				 response.sendRedirect(nextJSP);
		
		  }
		
		  
		}
	}

}
