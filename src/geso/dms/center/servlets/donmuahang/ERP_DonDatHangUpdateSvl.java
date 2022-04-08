package geso.dms.center.servlets.donmuahang;

import geso.dms.center.beans.donmuahang.IDonmuahangList;
import geso.dms.center.beans.donmuahang.IERP_DonDatHang;
import geso.dms.center.beans.donmuahang.IERP_DonDatHang_SP;
import geso.dms.center.beans.donmuahang.imp.DonmuahangList;
import geso.dms.center.beans.donmuahang.imp.ERP_DonDatHang;
import geso.dms.center.beans.donmuahang.imp.ERP_DonDatHang_SP;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Servlet implementation class ERP_DonDatHangUpdateSvl
 */
@WebServlet("/ERP_DonDatHangUpdateSvl")
public class ERP_DonDatHangUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ERP_DonDatHangUpdateSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	  
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	   
	    
	    String querystring = request.getQueryString();
	    String action = util.getAction(querystring);
	   
	    
	    String ddhId = util.getId(querystring);
	    
	    
	    String userId = util.getUserId(querystring);
	   System.out.println("Action : "+action);
	    if(action.equals("edit"))
	    {
	    	 IERP_DonDatHang dhBean=new ERP_DonDatHang(ddhId);
	 	    dhBean.Init();
	 	    session.setAttribute("kenhid", dhBean.getIDKenhBanHang());
	    	session.setAttribute("nhappid",dhBean.getNppId());
	    	session.setAttribute("dvkdid",dhBean.getdvkdid());
	    	session.setAttribute("donhangid", dhBean.getId());
	    	
	 	 String  nextJSP = "/AHF/pages/Center/Erp_DonDatHangUpdate.jsp";
	 	 
	 	 session.setAttribute("obj", dhBean);
	 	response.sendRedirect(nextJSP);
	 	return;
	 	    
	    }else if(action.equals("display")){
	    	 IERP_DonDatHang dhBean=new ERP_DonDatHang(ddhId);
		 	  dhBean.Init();
		 	 String  nextJSP = "/AHF/pages/Center/Erp_DonDatHangDisplay.jsp";
		 	 session.setAttribute("obj", dhBean);
		 	response.sendRedirect(nextJSP);
		 	return;
	    } 
	    else if(action.equals("print"))
	    {

			String dhid=util.getId(querystring);
			System.out.println(dhid);
			userId=util.getUserId(querystring);
		
			IERP_DonDatHang dhBean = new ERP_DonDatHang(dhid);
			response.setContentType("application/pdf");
		
			Document document = new Document();
			ServletOutputStream outstream = response.getOutputStream();
			this.CreatePxk(document, outstream,dhBean);
			
	    }
	    
	    
	    
	}
	public String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	private void CreatePxk(Document document, ServletOutputStream outstream,IERP_DonDatHang dhBean ) throws IOException
	{
		try
		{		
			
			List<IERP_DonDatHang_SP > spList = dhBean.getListSanPham();
			
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			NumberFormat formatter1 = new DecimalFormat("#,###,###.##"); 
			NumberFormat formatter2 = new DecimalFormat("#,###,##0.00000"); 
			NumberFormat formatter3 = new DecimalFormat("#,###,##0.000"); 
			
			PdfWriter.getInstance(document, outstream);
			document.open();
			//lay doi tuong khach hang
			
			//chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\COUR.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 15, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			Font font3 = new Font(bf, 8, Font.UNDERLINE);
			Font fontnomar=new Font(bf,10,Font.NORMAL);
			Font font8normal=new Font(bf,8,Font.NORMAL);
			//font2.setColor(BaseColor.GREEN);
			 //KHAI BAO 1 BANG CO BAO NHIEU COT
			String str_tieude="";
			if(dhBean.getISKM().equals("1")){
			 str_tieude="BIÊN BẢN XÁC NHẬN ĐƠN HÀNG KHUYẾN MÃI";
			
			}else{
				str_tieude="BIÊN BẢN XÁC NHẬN ĐƠN ĐẶT HÀNG";
			}
		     
			
			Paragraph tieude=new Paragraph(str_tieude, font);
			tieude.setAlignment(Element.ALIGN_CENTER);
			
			document.add(tieude);
			
			Paragraph sono=new Paragraph("No :" + dhBean.getId(),  font2);
			sono.setIndentationRight(44);
			sono.setAlignment(Element.ALIGN_RIGHT);
			
			sono.setSpacingBefore(16);
			document.add(sono);
			
			
			Paragraph nguoidathang =new Paragraph("Người đặt hàng: "+ dhBean.getNppTen()+"("+dhBean.getNppId()+")" , fontnomar);
		
			nguoidathang.setSpacingBefore(8);
			
			document.add(nguoidathang);
			
			Paragraph diachi =new Paragraph("Địa chỉ/Address: " + dhBean.getdiachixhd() , fontnomar);
			diachi.setIndentationLeft(30);
			diachi.setSpacingBefore(8);
			
			document.add(diachi);
			
			Paragraph masothue =new Paragraph("Mã số thuế/ Tax number: "+ dhBean.getmasothue(), fontnomar);
			masothue.setIndentationLeft(30);
			masothue.setSpacingBefore(8);
			document.add(masothue);
			
			
			Paragraph daidien =new Paragraph("Đại diện/ Representative: ", fontnomar);
			daidien.setIndentationLeft(30);
			daidien.setSpacingBefore(8);
			
			
			document.add(daidien);
			
			Paragraph diadiemxhd =new Paragraph("Địa điểm giao hàng: " + dhBean.getdiachi(), fontnomar);
			diadiemxhd.setSpacingBefore(8);
			document.add(diadiemxhd);
			
			Paragraph ngaydenghigh =new Paragraph("Ngày đề nghị giao hàng: " + dhBean.getNgaydenghigh(), fontnomar);
			ngaydenghigh.setSpacingBefore(8);
			document.add(ngaydenghigh);
			
			Paragraph ngaydathang =new Paragraph("Ngày đặt hàng: " + dhBean.getNgaygiaodich(), fontnomar);
			ngaydathang.setSpacingBefore(8);
			if(dhBean.getGhichu().length() <=0){
				ngaydathang.setSpacingAfter(8);
			}
			document.add(ngaydathang);
			
			
			if(dhBean.getGhichu().length() >0){
				Paragraph ghichu =new Paragraph("Ghi chú: " + dhBean.getGhichu(), fontnomar);
				ghichu.setSpacingBefore(8);
				ghichu.setSpacingAfter(8);
				document.add(ghichu);	
			}
			

			PdfPTable table = new PdfPTable(10);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withs = {4.0f,11.0f,25.0f, 7.0f, 7.0f, 8.0f, 5.0f, 7.0f, 10.0f, 10.0f}; 			
	        table.setWidths(withs);
			String[] th = new String[]{"STT", "Mã sản phẩm/ Material Code", "Tên Hàng/ Material Description ","Kho", "Trọng lượng", "Thể tích", 
											"ĐVT/ Unit", "Số lượng/ Quantity ", "Đơn giá/ Unit-price (Before Vat)VNĐ","Thành tiền /Amount VNĐ"};
			PdfPCell[] cell = new PdfPCell[10];
			for(int i=0; i < 10 ; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], font2));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell[i]);			
			}
			
			double tienchietkhau=0;
			float size= 8.5f;
			Font font4 = new Font(bf,size );
			PdfPCell cells_detail = new PdfPCell();
			double totalle=0;
			
			long tongluong = 0;
			double tongtien = 0;
			
			double tongTrongluong = 0;
			double tongThetich = 0;
			
			for(int i = 0; i <= spList.size(); i++)
			{
				if(i < spList.size())
				{
					IERP_DonDatHang_SP sanpham = (IERP_DonDatHang_SP)spList.get(i);
					
					double dongia = sanpham.getDonGia();
					
					double trongluong = Double.parseDouble(sanpham.getTrongluong()) * sanpham.getsoluongduyet();
					double thetich = Double.parseDouble(sanpham.getThetich()) * sanpham.getsoluongduyet();
					
					String[] arr = new String[]{Integer.toString(i+1), sanpham.getMaSanPham(), sanpham.getTenSanPham(),sanpham.getTenKhoTT(), formatter3.format(trongluong), 
												formatter2.format(thetich), sanpham.getDonViTinh(),
												"" + sanpham.getsoluongduyet(),formatter1.format(sanpham.getDonGia())+"" ,formatter1.format(sanpham.getsoluongduyet()* sanpham.getDonGia())};
					
					tongluong += sanpham.getsoluongduyet();
					tongtien += sanpham.getsoluongduyet()* sanpham.getDonGia();
					
					tongTrongluong += trongluong;
					tongThetich += thetich;
				
					for(int j=0; j < 10; j++)
					{
						cells_detail = new PdfPCell(new Paragraph(arr[j], font4));
						if(j==2)
						{
							cells_detail.setHorizontalAlignment(Element.ALIGN_LEFT);
						}
						else
						{
							cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
						cells_detail.setVerticalAlignment(Element.ALIGN_MIDDLE);
						
						if(j > 3)
							cells_detail.setHorizontalAlignment(Element.ALIGN_RIGHT);
						
						table.addCell(cells_detail);
					}
				}
				
				
				
			}	
			
			if(dhBean.getloaichietkhau().equals("1"))
			{
				tienchietkhau = dhBean.getChietkhau() * dhBean.getTongtientruocVAT()/100;
			}
			else
			{
				tienchietkhau=dhBean.getChietkhau();
			}
			
			//in cot chiet khau
			String[] arr = new String[]{Integer.toString(spList.size()+1),"", dhBean.getNoidungchietkhau(),"", "", "", "", "", "", formatter.format(tienchietkhau),""};
			
			for(int j=0; j < 10; j++)
			{
				cells_detail = new PdfPCell(new Paragraph(arr[j], font4));
				//cells_detail.setPadding(4.0f);
				
				if(j==2)
				{
					cells_detail.setHorizontalAlignment(Element.ALIGN_LEFT);
					//cells_detail.setBorderWidthRight(0);
				}
				else
				{
					cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
				}
				cells_detail.setVerticalAlignment(Element.ALIGN_MIDDLE);
				
				if(j > 3){
					cells_detail.setHorizontalAlignment(Element.ALIGN_RIGHT);
					//cells_detail.setBorderWidthLeft(0);
					
				}
				if(j>2  && j<6){
					//cells_detail.setBorderWidthRight(0);
					//cells_detail.setBorderWidthLeft(0);
				}
				
				//cells_detail.setBorderWidthTop(0);
				//cells_detail.setBorderWidthBottom(0);
				
				table.addCell(cells_detail);
			}
			
			double tiensauck=dhBean.getTongtientruocVAT()-tienchietkhau;
			String[] arr2 = new String[]{"Tổng cộng", formatter3.format(tongTrongluong), formatter2.format(tongThetich), " ", formatter.format(tongluong), " ", formatter.format(tiensauck)};
			for(int j = 0; j < 7; j++)
			{
				cells_detail = new PdfPCell(new Paragraph(arr2[j], font4));
				//cells_detail.setPadding(4.0f);
				
				if( j == 0 )
				{
					cells_detail.setColspan(4);
					cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
				}
				else
				{
					cells_detail.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				table.addCell(cells_detail);
			}
			
			arr2 = new String[]{"Tiền thuế (VAT)","", " ", formatter.format(tiensauck*dhBean.getVAT()/100)};
			for(int j = 0; j < 4; j++)
			{
				cells_detail = new PdfPCell(new Paragraph(arr2[j], font4));
				
				
				if( j == 0 )
				{
					//cells_detail.setBorderWidthRight(0);
					cells_detail.setColspan(7);
					cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
				}
				else
				{
					cells_detail.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				
				table.addCell(cells_detail);
			}
			double tiensauthue=tiensauck +(tiensauck*dhBean.getVAT()/100);
			arr2 = new String[]{"Tiền sau thuế ","", " ", formatter.format(tiensauthue)};
			for(int j = 0; j < 4; j++)
			{
				cells_detail = new PdfPCell(new Paragraph(arr2[j], font4));
				//cells_detail.setPadding(4.0f);
				
				if( j == 0 )
				{
					cells_detail.setColspan(7);
					cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
					//cells_detail.setBorderWidthRight(0);
				}
				else
				{
					cells_detail.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				/*if(j==1 ||j==2){
					cells_detail.setBorderWidthLeft(0);
					cells_detail.setBorderWidthRight(0);
				}*/
				table.addCell(cells_detail);
			}

			document.add(table);
			

			Paragraph ngaytao=new Paragraph("Ngày In: " + this.getDateTime(),fontnomar);
			ngaytao.setSpacingAfter(10);
			ngaytao.setAlignment(Element.ALIGN_RIGHT);
			document.add(ngaytao);
			
			PdfPTable tablefooter = new PdfPTable(3);//Chu y nhe 7 cot o day, thi xuong duoi nho set withs la 6 cot
			tablefooter.setWidthPercentage(100);//chieu dai cua báº£ng 
		
			tablefooter.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withfooter = {4.0f,6.0f,4.0f};
			
			
			 cells_detail = new PdfPCell(new Paragraph("Đại diện bên mua ", font2));
			 cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
			 cells_detail.setBorder(0);
			 tablefooter.addCell(cells_detail);
			 
			 cells_detail = new PdfPCell(new Paragraph("", font2));
			 cells_detail.setBorder(0);
			 tablefooter.addCell(cells_detail);
			 
			 cells_detail = new PdfPCell(new Paragraph("Đại diện bên bán  ", font2));
			 cells_detail.setBorder(0);
			 cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
			 tablefooter.addCell(cells_detail);
			 
			 
			 cells_detail = new PdfPCell(new Paragraph("(Ký ghi rõ họ tên)", font8normal));
			 cells_detail.setBorder(0);
			 cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
			 tablefooter.addCell(cells_detail);
			
			 
			 cells_detail = new PdfPCell(new Paragraph("", font2));
			 cells_detail.setBorder(0);
			
			 tablefooter.addCell(cells_detail);
			 
			 
			 cells_detail = new PdfPCell(new Paragraph("(Ký ghi rõ họ tên)", font8normal));
			 cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
			 cells_detail.setBorder(0);
			 tablefooter.addCell(cells_detail);
			 
			 
			tablefooter.setWidths(withfooter);
			
			document.add(tablefooter);
			document.close(); 
		}
		catch(DocumentException e)
		{
			e.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int loi=0;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		dbutils db = new dbutils();
		
		Utility util= new  Utility();
		session.setAttribute("util",util);
	    String id = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id"));    
		String userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
		String userTen=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userTen"));
		
		
		session.setAttribute("userId", userId);
		session.setAttribute("userTen", userTen);
		
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));		
 		String tenform=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tenform"));
 		String nextJSP="";
 		if(tenform.equals("newform")){
 			nextJSP = "/AHF/pages/Center/Erp_DonDatHangNew.jsp";
 		}else{
 			nextJSP = "/AHF/pages/Center/Erp_DonDatHangUpdate.jsp";
 		}
		IERP_DonDatHang dhBean=new ERP_DonDatHang();
		dhBean.Init();
		String ngaygiaodich = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaygiaodich")));
		dhBean.setNgaygiaodich(ngaygiaodich);
	    dhBean.setId(id);
	    
    	String ngaytao=this.getDateTime();
		String ngaysua=ngaytao;			
    	dhBean.setNgaytao(ngaytao);
    	dhBean.setNgaysua(ngaysua);
    	dhBean.setNguoitao(userId);
    	dhBean.setNguoisua(userId);
    
    	String loaichietkhau=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaick"));
    	
    	String chietkhau=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chietkhau"));
    	
    	if(loaichietkhau==null){
    		loaichietkhau="0";
    	}
    	dhBean.setloaichietkhau(loaichietkhau);
    	
    	System.out.println("loai chiet khai :"+loaichietkhau);
    	
    	try{
    	dhBean.setChietkhau(Double.parseDouble(chietkhau));
    	}catch(Exception er){
    		dhBean.setChietkhau(0);
    	}
    	String vat = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("VAT")));
    	System.out.println("lvat :"+vat);
    	try{
    		dhBean.setVAT(Double.parseDouble(vat));
    	}catch(Exception er){
    		dhBean.setVAT(10);
    	}
    	
    	String nhaccid=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhaccid")));
    	
    	if(nhaccid==null){
    		nhaccid="";
    	}
    	dhBean.setIdNhaCungCap(nhaccid);
    	
    
    	String kenhbanhang=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhbanhang")));
    	if(kenhbanhang==null){
    		kenhbanhang="";
    		
    	}
    	
    	dhBean.setIDKenhBanHang(kenhbanhang);
    	
    	
    	String nhappid = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhappid")));
    	
    	if(nhappid == null){
    		nhappid = "";
    	}
    	
    	dhBean.setNppId(nhappid);
    	System.out.println("Nha Phan Phoi Id : "+nhappid);
    	String tennpp = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tennpp")));
    	dhBean.setNppTen(tennpp);
    	
    	String sql="select khosap from nhaphanphoi where pk_seq=" +nhappid;
    	ResultSet rs=db.get(sql);
    	if(rs!=null){
    		try{
	    		if(rs.next()){
	    			dhBean.setKhottId(rs.getString("khosap"));
	    		}
    		}catch(Exception er){
    			
    		}
    	}
    	
    	
    	
    	String dvkdid=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdid")));
    	if(dvkdid==null){
    		dvkdid="";
    	}
    	//set de lay bang gia.
    	session.setAttribute("kenhid", kenhbanhang);
    	session.setAttribute("nhappid",nhappid);
    	session.setAttribute("dvkdid",dvkdid);
    	session.setAttribute("donhangid",id);
    	dhBean.setdvkdid(dvkdid);
    	
    	List<IERP_DonDatHang_SP>	spList = new ArrayList<IERP_DonDatHang_SP>();
    	//String[] idsp=request.getParameterValues("idsp");
		String[] masp = request.getParameterValues("masp");
		String[] tensp = request.getParameterValues("tensp");
		String[] soluong = request.getParameterValues("soluong");
		String[] donvitinh = request.getParameterValues("donvitinh");
		String[] khodat = request.getParameterValues("khodat");
		String[] soluongduyet = request.getParameterValues("soluongduyet");
		String[] quycach = request.getParameterValues("quycach");
		Hashtable<String, Integer> spThieuList = new Hashtable<String, Integer>();
		
		
		
		if(action.equals("reload")||action.equals("save")||action.equals("update")){
			
			if(masp != null)
			{		
			
				int m = 0;
				while(m < masp.length)
				{
				if(masp[m] != "")
				{
					//if(soluongduyet[m].length() >  0) //chi them nhung san pham co so luong > 0
					//{
						IERP_DonDatHang_SP sanpham = new ERP_DonDatHang_SP();
						 sql="select pk_seq from sanpham where trangthai!='2' and ma='"+masp[m]+"'";
						//System.out.println("Don Gia Nek :"+sql);
						ResultSet rs_getid=db.get(sql);
						try
						{
						if(rs_getid.next()){
							sanpham.setIdSanPham(rs_getid.getString("pk_seq"));
						}
						
						}catch(Exception er){
							sanpham.setIdSanPham("");
						}
						
						sanpham.setMaSanPham(masp[m]);
						sanpham.setTenSanPham(tensp[m]);
						sanpham.setDonViTinh(donvitinh[m]);
						sanpham.setQuyCach(quycach[m]);
						
						sanpham.setKhoTT(khodat[m]);
						int soluong1=0;
						try{
							soluong1= Integer.parseInt(soluong[m]);
						}catch(Exception er){
							
						}
						int soluong2=0;
						try{
							soluong2= Integer.parseInt(soluongduyet[m]);
						}catch(Exception er){
							
						}
						sanpham.setSoLuong(soluong1);
						sanpham.setSoluongduyet(soluong2);
						
						//thuc hien update lai gia khi co truong hop thay doi nha phan phoi,vi moi nha phan phoi co mot bang gia khac nhau
						String sql_getgia="select npp_fk,b.sanpham_fk,giamuanpp from BangGiaMuaNPP bgmnpp inner join BANGGIAMUANPP_NPP a "+
											" on bgmnpp.pk_Seq=a.bangGiaMuaNpp_fk    inner join BGMUANPP_SANPHAM  "+
											" b on a.bangGiaMuaNpp_fk=b.bgmuanpp_fk  where bgmnpp.trangthai='1'"+
											" and  kenh_fk='"+kenhbanhang+"' and dvkd_fk='"+dvkdid+"' and  npp_fk="+nhappid +"  and b.sanpham_fk="+sanpham.getIdSanPham();
						
						
					  System.out.println("HoaDonNewSvl : line 249 :"+sql_getgia);
						
						ResultSet rs_getgia =db.get(sql_getgia);
						try{
						if(rs_getgia.next()){
							sanpham.setDonGia(rs_getgia.getDouble("giamuanpp"));
						}
						else{
							sanpham.setDonGia(0);
						}
						}catch(Exception er){
							
						}
						spList.add(sanpham);
						
						//kiem tra san pham da du ton kho khong
						/*int sl = 0;
						
						String sql_gettonkho="select available from erp_khott_sanpham where  khott_fk = '"+dhBean.getKhottId()+"'  and sanpham_fk ="+ sanpham.getIdSanPham();
						System.out.println(sql_gettonkho);
						ResultSet slspAvailable = db.get(sql_gettonkho);
						if(slspAvailable != null)
						{
							try
							{
							if(	slspAvailable.next()){
								sl = slspAvailable.getInt("available");
							}else{
							
							}
								slspAvailable.close();
								
								//lay nhung sp da co trong don hang
								if(id != null)
								{
									int slg = 0;
									String sqlquery = "select ISNULL(soluong, 0) as soluong from dondathang_sp where dondathang_fk = '" + id + "' and SANPHAM_FK in (select pk_seq from sanpham where ma='" + masp[m].trim() + "')";
									ResultSet SlgRs = db.get(sqlquery);	
									if(SlgRs != null)
									{
										if(SlgRs.next())
										{
											slg = SlgRs.getInt("soluong");
										}
										SlgRs.close();
									}
									sl = sl+slg;
								}							
							} 
							catch (SQLException e) {
							System.out.println("Khong Lam Gi Duoc : "+e.toString());
								
							}
							*/
							if(soluong[m] == "")
								soluong[m] = "0";
							//System.out.println(sl);
							/*sanpham.setSoluongton(sl);
							if(Integer.parseInt(soluong[m]) > sl)
								spThieuList.put(masp[m],sl);*/
						//}
						
					//}
				}
				m++;
			}	
		}
		dhBean.setListSanPham(spList);	
		dhBean.setSpThieuList(spThieuList);
    	}
		
		String[] scheme = request.getParameterValues("ckDetail.scheme");
		String[] scheme_sotien= request.getParameterValues("ckDetail.sotien");
		
		String ghichu= geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ghichu"));
		if(ghichu == null)
			ghichu = "";
		dhBean.setGhichu(ghichu);
		
		String noidungchietkhau=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("noidungchietkhau"));
		if(noidungchietkhau == null)
			noidungchietkhau = "";
		dhBean.setNoidungchietkhau(noidungchietkhau);
		
		dhBean.setScheme(scheme);
		dhBean.setSotien(scheme_sotien);
		
		if(action.equals("save"))
		{
			if(id == null)
			{
				if (!(dhBean.Save()))
				{		
					session.setAttribute("obj", dhBean);
					response.sendRedirect(nextJSP);	 
					
					
				}else
				{
						IDonmuahangList	 obj = new DonmuahangList();
					    obj.setUserId(userId);
					    obj.createDdhlist("");
						session.setAttribute("obj", obj);
								
						 nextJSP = "/AHF/pages/Center/DonMuaHang.jsp";
						response.sendRedirect(nextJSP);
				 			    									
				}
			}else{
				String ischot=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ischot"));
				if(!dhBean.Edit(ischot)){
					session.setAttribute("obj", dhBean);
					response.sendRedirect(nextJSP);	 
				}else{
					IDonmuahangList	 obj = new DonmuahangList();
				    obj.setUserId(userId);
				    obj.createDdhlist("");
					session.setAttribute("obj", obj);		
					nextJSP = "/AHF/pages/Center/DonMuaHang.jsp";
					response.sendRedirect(nextJSP);
				}
				
			}
			
		}else if(action.equals("reload")){
			session.setAttribute("obj", dhBean);
			response.sendRedirect(nextJSP);	
			
		}
		
	}

}
