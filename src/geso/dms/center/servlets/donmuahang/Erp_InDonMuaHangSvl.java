package geso.dms.center.servlets.donmuahang;

import geso.dms.center.beans.donmuahang.IERP_DonDatHang;
import geso.dms.center.beans.donmuahang.IERP_DonDatHang_SP;
import geso.dms.center.beans.donmuahang.imp.ERP_DonDatHang;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
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

public class Erp_InDonMuaHangSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public Erp_InDonMuaHangSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{	
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/Growth/index.jsp");
		}else{
			
			dbutils db;
		
			Utility util=new Utility();
			String querystring=request.getQueryString();
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
	
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
	
	

}
