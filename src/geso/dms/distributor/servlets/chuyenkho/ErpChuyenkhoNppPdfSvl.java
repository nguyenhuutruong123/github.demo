package geso.dms.distributor.servlets.chuyenkho;
import geso.dms.center.beans.doctien.doctienrachu;
import geso.dms.center.db.sql.dbutils;
import geso.dms.distributor.beans.chuyenkho.IErpChuyenkhoNpp;
import geso.dms.distributor.beans.chuyenkho.imp.ErpChuyenkhoNpp;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
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
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfContentByte;


import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class ErpChuyenkhoNppPdfSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErpChuyenkhoNppPdfSvl()
	{
		super();
	}
	float CONVERT = 28.346457f; // = 1cm
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		// String userTen = (String) session.getAttribute("userTen");

		if (userId.length() == 0)
			userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
		
		Utility util=new Utility();

		String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
		System.out.println("nppId:"+nppId);
		IErpChuyenkhoNpp pxkBean = new ErpChuyenkhoNpp();
		pxkBean.setNppId(nppId);
		String querystring=request.getQueryString();
		String id=util.getId(querystring); 
		
		pxkBean.setId(id);
		pxkBean.setUserId(userId);
		
		dbutils db = new dbutils();

		
		if (querystring.contains("chuyenkho"))
		{
			//pxkBean.initPdf();

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", " inline; filename=PhieuXuatKhoTT.pdf");

			Document document = new Document();
			ServletOutputStream outstream = response.getOutputStream();
			try{
				if(nppId.equals("106226"))  // BINH THUAN
				{
					this.CreatePxk(document, outstream, pxkBean);
				} 
				else if(nppId.equals("106231"))  // DA NANG
				{
					this.CreatePxk_DANANG(document, outstream, pxkBean);
				}
				else if(nppId.equals("106210"))  // HO CHI MINH
				{
					this.CreatePxk_HOCHIMINH(document, outstream, pxkBean);
				}	
				else if(nppId.equals("106250"))
				{
					this.CreatePxk_KHANHHOA(document, outstream, pxkBean);
				}
				else  
				{
					this.CreatePxk_CANTHO(document, outstream, pxkBean);
				}
				
				String query ="UPDATE ERP_CHUYENKHONPP SET Dain = 1 where PK_SEQ = "+id;
				if(!db.update(query))
				{
					
				}
			}
			catch(Exception e) {}
		} 
		

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doGet(request, response);
	}

	private void CreatePxk(Document document, ServletOutputStream outstream, IErpChuyenkhoNpp pxkBean) throws IOException
	{
		try
		{
						
			dbutils db = new dbutils();
				
			//LAY THONG TIN NCC
					String ddh="";
					double Vat= 0;
					
					String ctyTen="";
					String cty_MST ="";
					String cty_Diachi="";
					String cty_Sotaikhoan= "";
					String cty_Dienthoai= "";
					String cty_Fax= "";
					String khoxuat ="";
										
			
			String  sql = " select PK_SEQ, TEN ,DIACHI,'' AS DIENTHOAI,'' AS FAX,ISNULL(MASOTHUE,'') AS MASOTHUE, '' as SOTAIKHOAN "+
				        " from NHAPHANPHOI ";				  
				   
			   
			   System.out.println("Lấy TT CTY "+sql);
			   ResultSet rsINFO = db.get(sql);
			   if(rsINFO.next())
			   {
				   ctyTen = rsINFO.getString("TEN");
				   cty_MST = rsINFO.getString("MASOTHUE");
				   cty_Diachi = rsINFO.getString("DIACHI");
				   cty_Sotaikhoan = rsINFO.getString("SOTAIKHOAN");
				   cty_Dienthoai = rsINFO.getString("DIENTHOAI");
				   cty_Fax = rsINFO.getString("FAX");
				   rsINFO.close();
				   
			   }
			
			   // LAY KHO XUAT/ KHO NHAP
			   String ngaychuyen = "";
			   String khoxuat_fk = "";
			   String tenNPP = "";
			   String lenhdieudong = "";
			   String lddcua = "";
			   String lddveviec = "";
			   String ngaylenhdieudong = "";
			   String sohopdong = "";
			   String ngayhopdong = "";
			   String nguoivanchuyen = "";
			   String ptvanchuyen = "";
			
			   
			   sql = " select NGAYCHUYEN,C.TEN AS KHOXUAT ,B.TEN AS TENNPP, " +
			   		 "        lenhdieudong, lddcua, lddveviec, ngaydieudong, sohopdong,ngayhopdong, nguoivanchuyen, ptvanchuyen " +
		        	 " from  ERP_CHUYENKHONPP A INNER JOIN NHAPHANPHOI B ON A.NPP_FK = B.PK_SEQ" +
		        	 "       INNER JOIN KHO C ON A.KHOXUAT_FK=C.PK_SEQ " +
		        	 " where A.PK_SEQ = "+ pxkBean.getId() +" ";				  
		   	   
				   System.out.println("KHO XUAT/ KHO NHAP_ "+sql);
				   ResultSet rskho = db.get(sql);
				   if(rskho.next())
				   {
					   ngaychuyen = rskho.getString("NGAYCHUYEN");
					   khoxuat_fk = rskho.getString("KHOXUAT");
					   tenNPP = rskho.getString("TENNPP");

					   lenhdieudong =rskho.getString("lenhdieudong")== null ? "":rskho.getString("lenhdieudong") ;
					   lddcua =rskho.getString("lddcua")== null ? "  ":rskho.getString("lddcua");
					   lddveviec =rskho.getString("lddveviec")== null ? "  ":rskho.getString("lddveviec");
					   ngaylenhdieudong =rskho.getString("ngaydieudong")== null ? "  ":rskho.getString("ngaydieudong");
					   sohopdong =rskho.getString("sohopdong")== null ? "":rskho.getString("sohopdong");
					   ngayhopdong =rskho.getString("ngayhopdong")== null ? "  ":rskho.getString("ngayhopdong");
					   nguoivanchuyen =rskho.getString("nguoivanchuyen")== null ? "  ":rskho.getString("nguoivanchuyen");
					   ptvanchuyen =rskho.getString("ptvanchuyen")== null ? "  ":rskho.getString("ptvanchuyen");
					   
						
				   rskho.close();
					   
				   }
			   			 
		    NumberFormat formatter = new DecimalFormat("#,###,###.##");
		    document.setPageSize(PageSize.A4.rotate());
			document.setMargins(1.5f*CONVERT, 1.0f*CONVERT, 1.8f*CONVERT, 1.0f*CONVERT); // L,R,T,B
			PdfWriter writer = PdfWriter.getInstance(document, outstream);

			document.open() ;
			
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);

			
			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setWidthPercentage(100);			

			PdfPCell cell = new PdfPCell();
			cell.setBorder(0);
			cell.setPaddingTop(0.7f * CONVERT);
			cell.setPaddingLeft(1.9f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			String [] ngayck = ngaychuyen.split("-");
			Paragraph pxk = new Paragraph(ngayck[2] + "                        " + ngayck[1] +  "                        " + ngayck[0].substring(2, 4) , new Font(bf, 8, Font.BOLDITALIC));
			pxk.setAlignment(Element.ALIGN_CENTER);
			pxk.setSpacingAfter(2);
			cell.addElement(pxk);

			tableheader.addCell(cell);
			document.add(tableheader);

			
			// Thông tin NCC
			PdfPTable table1 =new PdfPTable(1);
			table1.setWidthPercentage(100);
			
			// TÊN TỔ CHỨC,CÁ NHÂN
			PdfPCell cell7 = new PdfPCell();			
			pxk = new Paragraph(""  , new Font(bf, 9, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);			
			cell7.setPaddingTop(-0.1f*CONVERT);
			cell7.addElement(pxk);
			cell7.setBorder(0);						
			table1.addCell(cell7);				
						
			// ĐỊA CHỈ
			PdfPCell cell9 = new PdfPCell();			
			pxk = new Paragraph(" "   , new Font(bf, 9, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell9.addElement(pxk);
			cell9.setBorder(0);						
			table1.addCell(cell9);	
			
			//MÃ SỐ THUẾ
			PdfPCell cell11 = new PdfPCell();
			pxk = new Paragraph(" " , new Font(bf, 9, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell11.setPaddingLeft(1.5f*CONVERT);
			cell11.addElement(pxk);
			cell11.setBorder(0);						
			table1.addCell(cell11);	
			
			document.add(table1);
			
			// CĂN CỨ LỆNH ĐIỀU ĐỘNG
			String chuoingay = "                             ";
			if(ngaylenhdieudong.trim().length() > 0)
			{
			  String[] ngaydd = ngaylenhdieudong.split("-");
			  chuoingay = "         "+ ngaydd[2] + "                 " + ngaydd[1] + "                   " + ngaydd[0] +"  ";
			}
			
			PdfPTable tableLDD = new PdfPTable(4);
			tableLDD.setWidthPercentage(100);
			float[] withs =  { 8.0f*CONVERT, 6.0f*CONVERT, 5.5f*CONVERT, 5.0f*CONVERT };
			tableLDD.setWidths(withs);
			
			// LỆNH DD SỐ
			PdfPCell cell_so = new PdfPCell();			
			pxk = new Paragraph(" "+ lenhdieudong  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_so.setPaddingTop(0.5f*CONVERT);
			cell_so.setPaddingLeft(4.5f*CONVERT);
			cell_so.addElement(pxk);
			cell_so.setBorder(0);	
			tableLDD.addCell(cell_so);
			
			// NGÀY DD 
			PdfPCell cell_ngaydd = new PdfPCell();			
			pxk = new Paragraph(" "+ chuoingay  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_ngaydd.setPaddingTop(0.5f*CONVERT);
			cell_ngaydd.addElement(pxk);
			cell_ngaydd.setBorder(0);
			tableLDD.addCell(cell_ngaydd);
			
			// LỆNH DD CỦA
			PdfPCell cell_lddcua = new PdfPCell();			
			pxk = new Paragraph(" "+ lddcua  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_lddcua.setPaddingTop(0.5f*CONVERT);
			cell_lddcua.setPaddingLeft(0.8f*CONVERT);
			cell_lddcua.addElement(pxk);
			cell_lddcua.setBorder(0);
			tableLDD.addCell(cell_lddcua);
			
			// LỆNH DD VỀ VIỆC
			PdfPCell cell_veviec = new PdfPCell();			
			pxk = new Paragraph(" "+ lddveviec  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_veviec.setPaddingTop(0.5f*CONVERT);
			cell_veviec.setPaddingLeft(2.4f*CONVERT);
			cell_veviec.addElement(pxk);
			cell_veviec.setBorder(0);
			tableLDD.addCell(cell_veviec);
			
			document.add(tableLDD);				
			
			
			// HỌ TÊN NGƯỜI VẬN CHUYỂN
			PdfPTable tableVC = new PdfPTable(4);
			tableVC.setWidthPercentage(100);
			float[] withs1 =  { 9.5f*CONVERT, 6.0f*CONVERT, 5.5f*CONVERT, 5.0f*CONVERT };
			tableVC.setWidths(withs1);
			
			// NGƯỜI VC
			PdfPCell cell_ten = new PdfPCell();			
			pxk = new Paragraph(" "+ nguoivanchuyen  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_ten.setPaddingTop(0.1f*CONVERT);
			cell_ten.setPaddingLeft(4.5f*CONVERT);
			cell_ten.addElement(pxk);
			cell_ten.setBorder(0);
			tableVC.addCell(cell_ten);
			
			// SỐ HD
			PdfPCell cell_sohd = new PdfPCell();			
			pxk = new Paragraph(" "+ sohopdong  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_sohd.setPaddingTop(0.1f*CONVERT);
			cell_sohd.setPaddingLeft(2.5f*CONVERT);
			cell_sohd.addElement(pxk);
			cell_sohd.setBorder(0);	
			tableVC.addCell(cell_sohd);
			
			// NGÀY HỢP ĐỒNG
			String chuoi = "";
			if(ngayhopdong.trim().length() > 0)
			{
				String[] ngayhd = ngayhopdong.split("-");
				chuoi = ngayhd[2] + "/" + ngayhd[1] + "/" + ngayhd[0];
			}
			
			PdfPCell cell_ngayhd = new PdfPCell();			
			pxk = new Paragraph(" "+ chuoi  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_ngayhd.setPaddingTop(0.1f*CONVERT);
			cell_ngayhd.setPaddingLeft(2.8f*CONVERT);
			cell_ngayhd.addElement(pxk);
			cell_ngayhd.setBorder(0);	
			tableVC.addCell(cell_ngayhd);
			
			// PHƯƠNG TIỆN VC
			PdfPCell cell_pt = new PdfPCell();			
			pxk = new Paragraph(" "+ ptvanchuyen  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_pt.setPaddingTop(0.1f*CONVERT);
			cell_pt.setPaddingLeft(4.0f*CONVERT);
			cell_pt.addElement(pxk);
			cell_pt.setBorder(0);	
			tableVC.addCell(cell_pt);
			
			document.add(tableVC);							
								
			
			// XUẤT TẠI KHO  + NHẬP TẠI KHO		
			PdfPTable tableKHO = new PdfPTable(2);
			tableKHO.setWidthPercentage(100);			
			float[] withs3 =  { 13.0f*CONVERT, 13.0f*CONVERT };
			tableKHO.setWidths(withs3);
			
			
			// XUẤT TẠI KHO
			PdfPCell cell_khoxuat = new PdfPCell();			
			pxk = new Paragraph(" "+ khoxuat_fk  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_khoxuat.setPaddingTop(0.1f*CONVERT);
			cell_khoxuat.setPaddingLeft(2.5f*CONVERT);
			cell_khoxuat.addElement(pxk);
			cell_khoxuat.setBorder(0);
			tableKHO.addCell(cell_khoxuat);
			
			
			// NHẬP TẠI KHO	
			PdfPCell cell_khonhap = new PdfPCell();			
			pxk = new Paragraph(" "  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_khonhap.setPaddingTop(0.1f*CONVERT);
			cell_khonhap.setPaddingLeft(1.5f*CONVERT);
			cell_khonhap.addElement(pxk);
			cell_khonhap.setBorder(0);	
			tableKHO.addCell(cell_khonhap);
			
			document.add(tableKHO);			
					
			
			
			// Table Content

			Font font4 = new Font(bf, 8, Font.BOLD);
			Font font5 = new Font(bf, 8, Font.BOLDITALIC);

			//String[] th = new String[]{ "STT", "Tên hàng hóa,dịch vụ", "Số kiểm soát", "Hạn dùng","Số kiện" ,"ĐVT", "Thực xuất", "Thực nhận","Đơn giá", "Thành tiền"};
			String[] th = new String[]{ " ", " ", " ", " "," " ," "," ", " "," "};
			
			PdfPTable table = new PdfPTable(9);
			table.setWidthPercentage(100);
			table.setSpacingBefore(1.7f*CONVERT);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withssp =  { 16.0f, 185.0f, 32.0f, 56.0f, 32.0f, 56.0f, 56.0f,60.0f,84.0f  };
			table.setWidths(withssp);

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			
			double tongslnhan=0;
			double tongslchuyen=0;
			
			String query =
			"	select d.ten as tenSP, case when c.solo = 'NA' then '' else isnull(c.solo,'') end as solo, isnull(c.ngayhethan,'') as handung, e.DONVI,c.soluong as SOLUONG, b.DONGIA  "+
			"	from ERP_CHUYENKHONPP a " +
			"   inner join ERP_CHUYENKHONPP_SANPHAM b on a.pk_seq=b.chuyenkho_fk "+	
			"   inner join ERP_CHUYENKHONPP_SANPHAM_CHITIET c on a.pk_seq=c.chuyenkho_fk  and b.sanpham_fk= c.sanpham_fk " +
			"   inner join SANPHAM d on c.SANPHAM_FK = d.PK_SEQ " +
			"   inner join DONVIDOLUONG e on b.dvdl_fk = e.PK_SEQ "+			
			"	where a.PK_SEQ ="+ pxkBean.getId() +" ";
			
			
			System.out.println("[ERP_CHUYENKHO_SANPHAM1]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			while(rsSP.next())
			{
				double soluongChuyen = rsSP.getDouble("soluong");
				tongslchuyen +=soluongChuyen;
				
				double soluongNhan= 0;
				double dongia = rsSP.getDouble("dongia");
				double thanhtien = soluongChuyen*dongia;	
						
				totalTienTruocVAT+=thanhtien;
				String chuoingaysd = "";
				if(rsSP.getString("handung").trim().length() > 0)
				{
					String[] hsd = rsSP.getString("handung").split("-");
					chuoingaysd = hsd[2] + "/" + hsd[1] + "/" + hsd[0] ;
				}
								
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("tenSP"), rsSP.getString("solo"), chuoingaysd, 
						rsSP.getString("DONVI"),formatter.format(soluongChuyen), "","", "" };


				for (int j = 0; j < 9; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 8, Font.BOLD)));
					if (j <= 4){
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else{
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					
					
					if(j == 1)
						cells.setPaddingLeft(0.2f*CONVERT);
					if(j == 2)
						cells.setPaddingLeft(0.7f*CONVERT);
					if(j == 3)
						cells.setPaddingLeft(1.2f*CONVERT);
					if(j == 4)
						cells.setPaddingLeft(1.1f*CONVERT);
					if(j == 7)
						cells.setPaddingRight(-0.5f*CONVERT);
					if(j == 8)
						cells.setPaddingRight(-0.3f*CONVERT);
					
						
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPadding(2.0f);	
					cells.setFixedHeight(0.6f*CONVERT);
					cells.setBorder(0);
										
					table.addCell(cells);
				}
				
				stt++;
				
			}
						
		// DÒNG TRỐNG
			
			// DONG TRONG
			int kk=0;
			while(kk < 13-stt)
			{
				String[] arr_bosung = new String[] { " ", " ", " ", " "," " ," ", " "," ", " "};
	
				for (int j = 0; j < 9; j++)
				{
					cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 10, Font.NORMAL)));
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setPadding(2.0f);
					cells.setFixedHeight(0.6f*CONVERT);
					cells.setBorder(0);
										
					table.addCell(cells);
				}
				
				kk++;
			}
			
		String[] arr = new String[] {" ", " "," "," "," ",formatter.format(tongslchuyen), " "," ",""};// totalTienTruocVAT
			for (int j = 0; j < 9; j++)
			{
				cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 8, Font.BOLD)));
				if (j <= 4){
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
				}
				else{
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				
				
				if(j == 1)
					cells.setPaddingLeft(0.2f*CONVERT);
				if(j == 2)
					cells.setPaddingLeft(0.7f*CONVERT);
				if(j == 3)
					cells.setPaddingLeft(1.2f*CONVERT);
				if(j == 4)
					cells.setPaddingLeft(1.1f*CONVERT);
				if(j == 7)
					cells.setPaddingRight(-0.5f*CONVERT);
				if(j == 8)
					cells.setPaddingRight(-0.3f*CONVERT);
				
				cells.setVerticalAlignment(Element.ALIGN_TOP);
				cells.setFixedHeight(0.8f*CONVERT);
				cells.setBorder(0);
				table.addCell(cells);
			}
			
					
																			
			document.add(table);
			
			/*//Table thời gian nhập xuất
			PdfPTable tableTg = new PdfPTable(3);
			tableTg.setWidthPercentage(90);
			tableTg.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableTg.setWidths(new float[]
			{ 150.0f, 100.0f, 150.0f });

			PdfPCell cella = new PdfPCell(new Paragraph("Xuất, ngày          tháng          năm", new Font(bf, 9, Font.NORMAL)));
			cella.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cellb = new PdfPCell(new Paragraph("", new Font(bf, 8, Font.BOLD)));
			cellb.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cellc = new PdfPCell(new Paragraph("Nhập, ngày          tháng          năm", new Font(bf, 9, Font.NORMAL)));
			cellc.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			cella.setBorder(0);
			cellb.setBorder(0);
			cellc.setBorder(0);
			
			tableTg.addCell(cella);
			tableTg.addCell(cellb);
			tableTg.addCell(cellc);

			document.add(tableTg);*/
								
			
			document.close();
		
			
		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void CreatePxk_HOCHIMINH(Document document, ServletOutputStream outstream, IErpChuyenkhoNpp pxkBean) throws IOException
	{
		try
		{
						
			dbutils db = new dbutils();
				
			//LAY THONG TIN NCC
			String ddh="";
			double Vat= 0;
			
			String khoxuat="";
			String khonhan ="";
								
	
			String  sql = " select xuattaikho as khoxuat," +
					      "       (SELECT xuattaikho FROM NHAPHANPHOI WHERE pk_seq = " +
					      "       (select npp_dat_fk from ERP_CHUYENKHONPP where pk_seq = "+ pxkBean.getId() +")) as khonhan "+
				          " from NHAPHANPHOI " +
				          " where pk_seq = '"+ pxkBean.getNppId() +"'";				  
				   
			   
			   System.out.println("Lấy Kho xuất/Kho nhận "+sql);
			   ResultSet rsINFO = db.get(sql);
			   if(rsINFO.next())
			   {
				   khoxuat = rsINFO.getString("khoxuat");
				   khonhan = rsINFO.getString("khonhan");
				   rsINFO.close();
				   
			   }
			
			   // LAY KHO XUAT/ KHO NHAP
			   String ngaychuyen = "";
			   String khoxuat_fk = "";
			   String tenNPP = "";
			   String lenhdieudong = "";
			   String lddcua = "";
			   String lddveviec = "";
			   String ngaylenhdieudong = "";
			   String sohopdong = "";
			   String ngayhopdong = "";
			   String nguoivanchuyen = "";
			   String ptvanchuyen = "";
			
			   
			   sql = " select NGAYCHUYEN,C.TEN AS KHOXUAT ,B.TEN AS TENNPP, " +
			   		 "        lenhdieudong, lddcua, lddveviec, ngaydieudong, sohopdong,ngayhopdong, nguoivanchuyen, ptvanchuyen " +
		        	 " from  ERP_CHUYENKHONPP A INNER JOIN NHAPHANPHOI B ON A.NPP_FK = B.PK_SEQ" +
		        	 "       INNER JOIN KHO C ON A.KHOXUAT_FK=C.PK_SEQ " +
		        	 " where A.PK_SEQ = "+ pxkBean.getId() +" ";				  
		   	   
				   System.out.println("KHO XUAT/ KHO NHAP1_ "+sql);
				   ResultSet rskho = db.get(sql);
				   if(rskho.next())
				   {
					   ngaychuyen = rskho.getString("NGAYCHUYEN");
					   khoxuat_fk = rskho.getString("KHOXUAT");
					   tenNPP = rskho.getString("TENNPP");

					   lenhdieudong =rskho.getString("lenhdieudong")== null ? "":rskho.getString("lenhdieudong") ;
					   lddcua =rskho.getString("lddcua")== null ? "  ":rskho.getString("lddcua");
					   lddveviec =rskho.getString("lddveviec")== null ? "  ":rskho.getString("lddveviec");
					   ngaylenhdieudong =rskho.getString("ngaydieudong")== null ? "  ":rskho.getString("ngaydieudong");
					   sohopdong =rskho.getString("sohopdong")== null ? "":rskho.getString("sohopdong");
					   ngayhopdong =rskho.getString("ngayhopdong")== null ? "  ":rskho.getString("ngayhopdong");
					   nguoivanchuyen =rskho.getString("nguoivanchuyen")== null ? "  ":rskho.getString("nguoivanchuyen");
					   ptvanchuyen =rskho.getString("ptvanchuyen")== null ? "  ":rskho.getString("ptvanchuyen");
					   
						
				   rskho.close();
					   
				   }
			   			 
		    NumberFormat formatter = new DecimalFormat("#,###,###.##");
		    document.setPageSize(PageSize.A4.rotate());
			document.setMargins(1.5f*CONVERT, 1.7f*CONVERT, 3.3f*CONVERT, 1.0f*CONVERT); // L,R,T,B
			PdfWriter writer = PdfWriter.getInstance(document, outstream);

			document.open() ;
			
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);

			
			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setWidthPercentage(100);			

			PdfPCell cell = new PdfPCell();
			cell.setBorder(0);
			cell.setPaddingTop(0.3f * CONVERT);
			cell.setPaddingLeft(2.4f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			String [] ngayck = ngaychuyen.split("-");
			Paragraph pxk = new Paragraph(ngayck[2] + "                        " + ngayck[1] +  "                       " + ngayck[0].substring(2, 4) , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_CENTER);
			pxk.setSpacingAfter(2);
			cell.addElement(pxk);

			tableheader.addCell(cell);
			document.add(tableheader);

			
			// Thông tin NCC
			PdfPTable table1 =new PdfPTable(1);
			table1.setWidthPercentage(100);
			
			// TÊN TỔ CHỨC,CÁ NHÂN
			PdfPCell cell7 = new PdfPCell();			
			pxk = new Paragraph(""  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell7.setPaddingTop(0.05f*CONVERT);
			cell7.addElement(pxk);
			cell7.setBorder(0);						
			table1.addCell(cell7);				
						
			// ĐỊA CHỈ
			PdfPCell cell9 = new PdfPCell();			
			pxk = new Paragraph(" "   , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell9.addElement(pxk);
			cell9.setBorder(0);						
			table1.addCell(cell9);	
			
			//MÃ SỐ THUẾ
			PdfPCell cell11 = new PdfPCell();
			pxk = new Paragraph(" " , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell11.setPaddingLeft(1.4f*CONVERT);
			cell11.addElement(pxk);
			cell11.setBorder(0);						
			table1.addCell(cell11);	
			
			document.add(table1);
			
			// CĂN CỨ LỆNH ĐIỀU ĐỘNG
			String chuoingay = "                             ";
			if(ngaylenhdieudong.trim().length() > 0)
			{
			  String[] ngaydd = ngaylenhdieudong.split("-");
			  chuoingay = "         "+ ngaydd[2] + "                 " + ngaydd[1] + "              " + ngaydd[0] +"";
			}
			
			PdfPTable tableLDD = new PdfPTable(4);
			tableLDD.setWidthPercentage(100);
			float[] withs =  { 8.0f*CONVERT, 6.0f*CONVERT, 5.5f*CONVERT, 5.0f*CONVERT };
			tableLDD.setWidths(withs);
			
			// LỆNH DD SỐ
			PdfPCell cell_so = new PdfPCell();			
			pxk = new Paragraph(" "+ lenhdieudong  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_so.setPaddingTop(1.0f*CONVERT);
			cell_so.setPaddingLeft(5.0f*CONVERT);
			cell_so.addElement(pxk);
			cell_so.setBorder(0);	
			tableLDD.addCell(cell_so);
			
			// NGÀY DD 
			PdfPCell cell_ngaydd = new PdfPCell();			
			pxk = new Paragraph(" "+ chuoingay  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_ngaydd.setPaddingTop(1.0f*CONVERT);
			cell_ngaydd.setPaddingLeft(0.5f*CONVERT);
			cell_ngaydd.addElement(pxk);
			cell_ngaydd.setBorder(0);
			tableLDD.addCell(cell_ngaydd);
			
			// LỆNH DD CỦA
			PdfPCell cell_lddcua = new PdfPCell();			
			pxk = new Paragraph(" "+ lddcua  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_lddcua.setPaddingTop(1.0f*CONVERT);
			cell_lddcua.setPaddingLeft(1.4f*CONVERT);
			cell_lddcua.addElement(pxk);
			cell_lddcua.setBorder(0);
			tableLDD.addCell(cell_lddcua);
			
			// LỆNH DD VỀ VIỆC
			PdfPCell cell_veviec = new PdfPCell();			
			pxk = new Paragraph(" "+ lddveviec  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_veviec.setPaddingTop(1.0f*CONVERT);
			cell_veviec.setPaddingLeft(2.4f*CONVERT);
			cell_veviec.addElement(pxk);
			cell_veviec.setBorder(0);
			tableLDD.addCell(cell_veviec);
			
			document.add(tableLDD);				
			
			
			// HỌ TÊN NGƯỜI VẬN CHUYỂN
			PdfPTable tableVC = new PdfPTable(4);
			tableVC.setWidthPercentage(100);
			float[] withs1 =  { 9.5f*CONVERT, 6.0f*CONVERT, 5.5f*CONVERT, 5.0f*CONVERT };
			tableVC.setWidths(withs1);
			
			// NGƯỜI VC
			PdfPCell cell_ten = new PdfPCell();			
			pxk = new Paragraph(" "+ nguoivanchuyen  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_ten.setPaddingTop(0.1f*CONVERT);
			cell_ten.setPaddingLeft(4.9f*CONVERT);
			cell_ten.addElement(pxk);
			cell_ten.setBorder(0);
			tableVC.addCell(cell_ten);
			
			// SỐ HD
			PdfPCell cell_sohd = new PdfPCell();			
			pxk = new Paragraph(" "+ sohopdong  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_sohd.setPaddingTop(0.1f*CONVERT);
			cell_sohd.setPaddingLeft(2.9f*CONVERT);
			cell_sohd.addElement(pxk);
			cell_sohd.setBorder(0);	
			tableVC.addCell(cell_sohd);
			
			// NGÀY HỢP ĐỒNG
			String chuoi = "";
			if(ngayhopdong.trim().length() > 0)
			{
				String[] ngayhd = ngayhopdong.split("-");
				chuoi = ngayhd[2] + "/" + ngayhd[1] + "/" + ngayhd[0];
			}
			
			PdfPCell cell_ngayhd = new PdfPCell();			
			pxk = new Paragraph(" "+ chuoi  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_ngayhd.setPaddingTop(0.1f*CONVERT);
			cell_ngayhd.setPaddingLeft(3.1f*CONVERT);
			cell_ngayhd.addElement(pxk);
			cell_ngayhd.setBorder(0);	
			tableVC.addCell(cell_ngayhd);
			
			// PHƯƠNG TIỆN VC
			PdfPCell cell_pt = new PdfPCell();			
			pxk = new Paragraph(" "+ ptvanchuyen  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_pt.setPaddingTop(0.1f*CONVERT);
			cell_pt.setPaddingLeft(3.0f*CONVERT);
			cell_pt.addElement(pxk);
			cell_pt.setBorder(0);	
			tableVC.addCell(cell_pt);
			
			document.add(tableVC);							
								
			
			// XUẤT TẠI KHO  + NHẬP TẠI KHO		
			PdfPTable tableKHO = new PdfPTable(2);
			tableKHO.setWidthPercentage(100);			
			float[] withs3 =  { 13.0f*CONVERT, 13.0f*CONVERT };
			tableKHO.setWidths(withs3);
			
			
			// XUẤT TẠI KHO
			PdfPCell cell_khoxuat = new PdfPCell();			
			pxk = new Paragraph(" "+ khoxuat_fk  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_khoxuat.setPaddingTop(0.1f*CONVERT);
			cell_khoxuat.setPaddingLeft(2.5f*CONVERT);
			cell_khoxuat.addElement(pxk);
			cell_khoxuat.setBorder(0);
			tableKHO.addCell(cell_khoxuat);
			
			
			// NHẬP TẠI KHO	
			PdfPCell cell_khonhap = new PdfPCell();			
			pxk = new Paragraph(" "+khonhan  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_khonhap.setPaddingTop(0.1f*CONVERT);
			cell_khonhap.setPaddingLeft(2.8f*CONVERT);
			cell_khonhap.addElement(pxk);
			cell_khonhap.setBorder(0);	
			tableKHO.addCell(cell_khonhap);
			
			document.add(tableKHO);			
					
			
			
			// Table Content

			Font font4 = new Font(bf, 8, Font.BOLD);
			Font font5 = new Font(bf, 8, Font.BOLDITALIC);

			//String[] th = new String[]{ "STT", "Tên hàng hóa,dịch vụ", "Số kiểm soát", "Hạn dùng","Số kiện" ,"ĐVT", "Thực xuất", "Thực nhận","Đơn giá", "Thành tiền"};
			String[] th = new String[]{ " ", " ", " ", " "," " ," "," ", " "," "};
			
			PdfPTable table = new PdfPTable(9);
			table.setWidthPercentage(100);
			table.setSpacingBefore(2.1f*CONVERT);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withssp =  { 11.4f, 139.0f, 32.0f, 56.0f, 32.0f, 56.0f, 56.0f,60.0f,84.0f  }; //24.4f, 140.0f,
			table.setWidths(withssp);

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			
			double tongslnhan=0;
			double tongslchuyen=0;
			
			String query =
			"	select d.ten as tenSP, case when c.solo = 'NA' then '' else isnull(c.solo,'') end as solo, isnull(c.ngayhethan,'') as handung, e.DONVI,c.soluong as SOLUONG, b.DONGIA  "+
			"	from ERP_CHUYENKHONPP a " +
			"   inner join ERP_CHUYENKHONPP_SANPHAM b on a.pk_seq=b.chuyenkho_fk "+	
			"   inner join ERP_CHUYENKHONPP_SANPHAM_CHITIET c on a.pk_seq=c.chuyenkho_fk  and b.sanpham_fk= c.sanpham_fk " +
			"   inner join SANPHAM d on c.SANPHAM_FK = d.PK_SEQ " +
			"   inner join DONVIDOLUONG e on b.dvdl_fk = e.PK_SEQ "+			
			"	where a.PK_SEQ ="+ pxkBean.getId() +" ";
			
			
			System.out.println("[ERP_CHUYENKHO_SANPHAM]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			while(rsSP.next())
			{
				double soluongChuyen = rsSP.getDouble("soluong");
				tongslchuyen +=soluongChuyen;
				
				double soluongNhan= 0;
			
				double dongia = rsSP.getDouble("dongia");
				double thanhtien = soluongChuyen*dongia;	
						
				totalTienTruocVAT+=thanhtien;
				String chuoingaysd = "";
				if(rsSP.getString("handung").trim().length() > 0)
				{
					String[] hsd = rsSP.getString("handung").split("-");
					chuoingaysd = hsd[2] + "/" + hsd[1] + "/" + hsd[0] ;
				}
								
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("tenSP"), rsSP.getString("solo"), chuoingaysd, 
						rsSP.getString("DONVI"),formatter.format(soluongChuyen)," ", "" , "" };


				for (int j = 0; j < 9; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 9, Font.NORMAL)));
					if (j <= 4){
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else{
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					
					
					if(j == 1)
						cells.setPaddingLeft(0.3f*CONVERT);
					if(j == 2)
						cells.setPaddingLeft(-1.1f*CONVERT);
					if(j == 3)
						cells.setPaddingLeft(-1.2f*CONVERT);
					if(j == 4)
						cells.setPaddingLeft(-1.5f*CONVERT);
					if(j == 5)
						cells.setPaddingRight(4.7f*CONVERT);
					
						
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPadding(2.0f);	
					cells.setFixedHeight(0.6f*CONVERT);
					cells.setBorder(0);
										
					table.addCell(cells);
				}
				
				stt++;
				
			}
						
		// DÒNG TRỐNG
			
			// DONG TRONG
			int kk=0;
			while(kk < 11-stt)
			{
				String[] arr_bosung = new String[] { " ", " ", " ", " "," " ," ", " "," ", " "};
	
				for (int j = 0; j < 9; j++)
				{
					cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 11, Font.NORMAL)));
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setPadding(2.0f);
					cells.setFixedHeight(0.6f*CONVERT);
					cells.setBorder(0);
										
					table.addCell(cells);
				}
				
				kk++;
			}
			
		String[] arr = new String[] {" ", " "," "," "," "," "," "," ", ""};//totalTienTruocVAT
			for (int j = 0; j < 9; j++)
			{
				cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 9, Font.BOLD)));
				if (j <= 4){
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
				}
				else{
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				
				

				if(j == 1)
					cells.setPaddingLeft(1.2f*CONVERT);
				if(j == 2)
					cells.setPaddingLeft(-1.1f*CONVERT);
				if(j == 3)
					cells.setPaddingLeft(-0.2f*CONVERT);
				if(j == 4)
					cells.setPaddingLeft(-0.5f*CONVERT);
				if(j == 5)
					cells.setPaddingRight(3.0f*CONVERT);
				
				cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
				cells.setPadding(2.0f);	
				cells.setFixedHeight(0.8f*CONVERT);
				cells.setBorder(0);
				table.addCell(cells);
			}
			
																								
			document.add(table);
								
			//Table thời gian nhập xuất
			PdfPTable tableTg = new PdfPTable(3);
			tableTg.setWidthPercentage(90);
			tableTg.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableTg.setWidths(new float[]
			{ 150.0f, 100.0f, 150.0f });

			PdfPCell cella = new PdfPCell(new Paragraph(""+ ngayck[2] + "                  "+ ngayck[1] + "                  "+ ngayck[0], new Font(bf, 9, Font.NORMAL)));
			cella.setPaddingLeft(1.2f*CONVERT);
			cella.setPaddingTop(0.2f*CONVERT);
			cella.setHorizontalAlignment(Element.ALIGN_LEFT);
			PdfPCell cellb = new PdfPCell(new Paragraph("", new Font(bf, 8, Font.BOLD)));
			cellb.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cellc = new PdfPCell(new Paragraph(" ", new Font(bf, 9, Font.NORMAL)));
			cellc.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			cella.setBorder(0);
			cellb.setBorder(0);
			cellc.setBorder(0);
			
			tableTg.addCell(cella);
			tableTg.addCell(cellb);
			tableTg.addCell(cellc);

			document.add(tableTg);
			
			document.close();
		
			
		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void CreatePxk_CANTHO(Document document, ServletOutputStream outstream, IErpChuyenkhoNpp pxkBean) throws IOException
	{
		try
		{
						
			dbutils db = new dbutils();
				
			//LAY THONG TIN NCC
					String ddh="";
					double Vat= 0;
					
					String ctyTen="";
					String cty_MST ="";
					String cty_Diachi="";
					String cty_Sotaikhoan= "";
					String cty_Dienthoai= "";
					String cty_Fax= "";
					String khoxuat ="";
										
			
			String  sql = " select PK_SEQ, TEN ,DIACHI,'' AS DIENTHOAI,'' AS FAX,ISNULL(MASOTHUE,'') AS MASOTHUE, '' as SOTAIKHOAN "+
				        " from NHAPHANPHOI ";				  
				   
			   
			   System.out.println("Lấy TT CTY "+sql);
			   ResultSet rsINFO = db.get(sql);
			   if(rsINFO.next())
			   {
				   ctyTen = rsINFO.getString("TEN");
				   cty_MST = rsINFO.getString("MASOTHUE");
				   cty_Diachi = rsINFO.getString("DIACHI");
				   cty_Sotaikhoan = rsINFO.getString("SOTAIKHOAN");
				   cty_Dienthoai = rsINFO.getString("DIENTHOAI");
				   cty_Fax = rsINFO.getString("FAX");
				   rsINFO.close();
				   
			   }
			
			   // LAY KHO XUAT/ KHO NHAP
			   String ngaychuyen = "";
			   String khoxuat_fk = "";
			   String tenNPP = "";
			   String lenhdieudong = "";
			   String lddcua = "";
			   String lddveviec = "";
			   String ngaylenhdieudong = "";
			   String sohopdong = "";
			   String ngayhopdong = "";
			   String nguoivanchuyen = "";
			   String ptvanchuyen = "";
			
			   
			   sql = " select NGAYCHUYEN,C.TEN AS KHOXUAT ,B.TEN AS TENNPP, " +
			   		 "        lenhdieudong, lddcua, lddveviec, ngaydieudong, sohopdong,ngayhopdong, nguoivanchuyen, ptvanchuyen " +
		        	 " from  ERP_CHUYENKHONPP A INNER JOIN NHAPHANPHOI B ON A.NPP_FK = B.PK_SEQ" +
		        	 "       INNER JOIN KHO C ON A.KHOXUAT_FK=C.PK_SEQ " +
		        	 " where A.PK_SEQ = "+ pxkBean.getId() +" ";				  
		   	   
				   System.out.println("KHO XUAT/ KHO NHAP_ "+sql);
				   ResultSet rskho = db.get(sql);
				   if(rskho.next())
				   {
					   ngaychuyen = rskho.getString("NGAYCHUYEN");
					   khoxuat_fk = rskho.getString("KHOXUAT");
					   tenNPP = rskho.getString("TENNPP");

					   lenhdieudong =rskho.getString("lenhdieudong")== null ? "":rskho.getString("lenhdieudong") ;
					   lddcua =rskho.getString("lddcua")== null ? "  ":rskho.getString("lddcua");
					   lddveviec =rskho.getString("lddveviec")== null ? "  ":rskho.getString("lddveviec");
					   ngaylenhdieudong =rskho.getString("ngaydieudong")== null ? "  ":rskho.getString("ngaydieudong");
					   sohopdong =rskho.getString("sohopdong")== null ? "":rskho.getString("sohopdong");
					   ngayhopdong =rskho.getString("ngayhopdong")== null ? "  ":rskho.getString("ngayhopdong");
					   nguoivanchuyen =rskho.getString("nguoivanchuyen")== null ? "  ":rskho.getString("nguoivanchuyen");
					   ptvanchuyen =rskho.getString("ptvanchuyen")== null ? "  ":rskho.getString("ptvanchuyen");
					   
						
				   rskho.close();
					   
				   }
			   			 
		    NumberFormat formatter = new DecimalFormat("#,###,###.##");
		    document.setPageSize(PageSize.A4.rotate());
			document.setMargins(1.5f*CONVERT, 1.0f*CONVERT, 2.0f*CONVERT, 1.0f*CONVERT); // L,R,T,B
			PdfWriter writer = PdfWriter.getInstance(document, outstream);

			document.open() ;
			
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);

			
			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setWidthPercentage(100);			

			PdfPCell cell = new PdfPCell();
			cell.setBorder(0);
			cell.setPaddingTop(0.55f * CONVERT);
			cell.setPaddingLeft(1.9f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			String [] ngayck = ngaychuyen.split("-");
			Paragraph pxk = new Paragraph(ngayck[2] + "                        " + ngayck[1] +  "                        " + ngayck[0].substring(2, 4) , new Font(bf, 8, Font.BOLDITALIC));
			pxk.setAlignment(Element.ALIGN_CENTER);
			pxk.setSpacingAfter(2);
			cell.addElement(pxk);

			tableheader.addCell(cell);
			document.add(tableheader);

			
			// Thông tin NCC
			PdfPTable table1 =new PdfPTable(1);
			table1.setWidthPercentage(100);
			
			// TÊN TỔ CHỨC,CÁ NHÂN
			PdfPCell cell7 = new PdfPCell();			
			pxk = new Paragraph(""  , new Font(bf, 9, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell7.setPaddingTop(0.05f*CONVERT);
			cell7.addElement(pxk);
			cell7.setBorder(0);						
			table1.addCell(cell7);				
						
			// ĐỊA CHỈ
			PdfPCell cell9 = new PdfPCell();			
			pxk = new Paragraph(" "   , new Font(bf, 9, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell9.addElement(pxk);
			cell9.setBorder(0);						
			table1.addCell(cell9);	
			
			//MÃ SỐ THUẾ
			PdfPCell cell11 = new PdfPCell();
			pxk = new Paragraph(" " , new Font(bf, 9, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell11.setPaddingLeft(1.5f*CONVERT);
			cell11.addElement(pxk);
			cell11.setBorder(0);						
			table1.addCell(cell11);	
			
			document.add(table1);
			
			// CĂN CỨ LỆNH ĐIỀU ĐỘNG
			String chuoingay = "                             ";
			if(ngaylenhdieudong.trim().length() > 0)
			{
			  String[] ngaydd = ngaylenhdieudong.split("-");
			  chuoingay = "         "+ ngaydd[2] + "                 " + ngaydd[1] + "                   " + ngaydd[0] +"  ";
			}
			
			PdfPTable tableLDD = new PdfPTable(4);
			tableLDD.setWidthPercentage(100);
			float[] withs =  { 8.0f*CONVERT, 6.0f*CONVERT, 5.5f*CONVERT, 5.0f*CONVERT };
			tableLDD.setWidths(withs);
			
			// LỆNH DD SỐ
			PdfPCell cell_so = new PdfPCell();			
			pxk = new Paragraph(" "+ lenhdieudong  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_so.setPaddingTop(0.5f*CONVERT);
			cell_so.setPaddingLeft(4.5f*CONVERT);
			cell_so.addElement(pxk);
			cell_so.setBorder(0);	
			tableLDD.addCell(cell_so);
			
			// NGÀY DD 
			PdfPCell cell_ngaydd = new PdfPCell();			
			pxk = new Paragraph(" "+ chuoingay  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_ngaydd.setPaddingTop(0.5f*CONVERT);
			cell_ngaydd.addElement(pxk);
			cell_ngaydd.setBorder(0);
			tableLDD.addCell(cell_ngaydd);
			
			// LỆNH DD CỦA
			PdfPCell cell_lddcua = new PdfPCell();			
			pxk = new Paragraph(" "+ lddcua  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_lddcua.setPaddingTop(0.5f*CONVERT);
			cell_lddcua.setPaddingLeft(0.8f*CONVERT);
			cell_lddcua.addElement(pxk);
			cell_lddcua.setBorder(0);
			tableLDD.addCell(cell_lddcua);
			
			// LỆNH DD VỀ VIỆC
			PdfPCell cell_veviec = new PdfPCell();			
			pxk = new Paragraph(" "+ lddveviec  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_veviec.setPaddingTop(0.5f*CONVERT);
			cell_veviec.setPaddingLeft(2.4f*CONVERT);
			cell_veviec.addElement(pxk);
			cell_veviec.setBorder(0);
			tableLDD.addCell(cell_veviec);
			
			document.add(tableLDD);				
			
			
			// HỌ TÊN NGƯỜI VẬN CHUYỂN
			PdfPTable tableVC = new PdfPTable(4);
			tableVC.setWidthPercentage(100);
			float[] withs1 =  { 9.5f*CONVERT, 6.0f*CONVERT, 5.5f*CONVERT, 5.0f*CONVERT };
			tableVC.setWidths(withs1);
			
			// NGƯỜI VC
			PdfPCell cell_ten = new PdfPCell();			
			pxk = new Paragraph(" "+ nguoivanchuyen  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_ten.setPaddingTop(0.1f*CONVERT);
			cell_ten.setPaddingLeft(4.5f*CONVERT);
			cell_ten.addElement(pxk);
			cell_ten.setBorder(0);
			tableVC.addCell(cell_ten);
			
			// SỐ HD
			PdfPCell cell_sohd = new PdfPCell();			
			pxk = new Paragraph(" "+ sohopdong  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_sohd.setPaddingTop(0.1f*CONVERT);
			cell_sohd.setPaddingLeft(2.5f*CONVERT);
			cell_sohd.addElement(pxk);
			cell_sohd.setBorder(0);	
			tableVC.addCell(cell_sohd);
			
			// NGÀY HỢP ĐỒNG
			String chuoi = "";
			if(ngayhopdong.trim().length() > 0)
			{
				String[] ngayhd = ngayhopdong.split("-");
				chuoi = ngayhd[2] + "/" + ngayhd[1] + "/" + ngayhd[0];
			}
			
			PdfPCell cell_ngayhd = new PdfPCell();			
			pxk = new Paragraph(" "+ chuoi  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_ngayhd.setPaddingTop(0.1f*CONVERT);
			cell_ngayhd.setPaddingLeft(2.8f*CONVERT);
			cell_ngayhd.addElement(pxk);
			cell_ngayhd.setBorder(0);	
			tableVC.addCell(cell_ngayhd);
			
			// PHƯƠNG TIỆN VC
			PdfPCell cell_pt = new PdfPCell();			
			pxk = new Paragraph(" "+ ptvanchuyen  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_pt.setPaddingTop(0.1f*CONVERT);
			cell_pt.setPaddingLeft(4.0f*CONVERT);
			cell_pt.addElement(pxk);
			cell_pt.setBorder(0);	
			tableVC.addCell(cell_pt);
			
			document.add(tableVC);							
								
			
			// XUẤT TẠI KHO  + NHẬP TẠI KHO		
			PdfPTable tableKHO = new PdfPTable(2);
			tableKHO.setWidthPercentage(100);			
			float[] withs3 =  { 13.0f*CONVERT, 13.0f*CONVERT };
			tableKHO.setWidths(withs3);
			
			
			// XUẤT TẠI KHO
			PdfPCell cell_khoxuat = new PdfPCell();			
			pxk = new Paragraph(" "+ khoxuat_fk  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_khoxuat.setPaddingTop(0.2f*CONVERT);
			cell_khoxuat.setPaddingLeft(2.5f*CONVERT);
			cell_khoxuat.addElement(pxk);
			cell_khoxuat.setBorder(0);
			tableKHO.addCell(cell_khoxuat);
			
			
			// NHẬP TẠI KHO	
			PdfPCell cell_khonhap = new PdfPCell();			
			pxk = new Paragraph(" "  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_khonhap.setPaddingTop(0.2f*CONVERT);
			cell_khonhap.setPaddingLeft(1.5f*CONVERT);
			cell_khonhap.addElement(pxk);
			cell_khonhap.setBorder(0);	
			tableKHO.addCell(cell_khonhap);
			
			document.add(tableKHO);			
					
			
			
			// Table Content

			Font font4 = new Font(bf, 8, Font.BOLD);
			Font font5 = new Font(bf, 8, Font.BOLDITALIC);

			//String[] th = new String[]{ "STT", "Tên hàng hóa,dịch vụ", "Số kiểm soát", "Hạn dùng","Số kiện" ,"ĐVT", "Thực xuất", "Thực nhận","Đơn giá", "Thành tiền"};
			String[] th = new String[]{ " ", " ", " ", " "," " ," "," ", " "," "};
			
			PdfPTable table = new PdfPTable(9);
			table.setWidthPercentage(100);
			table.setSpacingBefore(1.6f*CONVERT);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withssp =  { 16.0f, 160.0f, 32.0f, 56.0f, 32.0f, 56.0f, 56.0f,60.0f,84.0f  };
			table.setWidths(withssp);

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			
			double tongslnhan=0;
			double tongslchuyen=0;
			
			String query =
			"	select d.ten as tenSP, case when c.solo = 'NA' then '' else isnull(c.solo,'') end as solo, isnull(c.ngayhethan,'') as handung, e.DONVI,c.soluong as SOLUONG, b.DONGIA  "+
			"	from ERP_CHUYENKHONPP a " +
			"   inner join ERP_CHUYENKHONPP_SANPHAM b on a.pk_seq=b.chuyenkho_fk "+	
			"   inner join ERP_CHUYENKHONPP_SANPHAM_CHITIET c on a.pk_seq=c.chuyenkho_fk  and b.sanpham_fk= c.sanpham_fk " +
			"   inner join SANPHAM d on c.SANPHAM_FK = d.PK_SEQ " +
			"   inner join DONVIDOLUONG e on b.dvdl_fk = e.PK_SEQ "+			
			"	where a.PK_SEQ ="+ pxkBean.getId() +" ";
			
			
			System.out.println("[ERP_CHUYENKHO_SANPHAM1]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			while(rsSP.next())
			{
				double soluongChuyen = rsSP.getDouble("soluong");
				tongslchuyen +=soluongChuyen;
				
				double soluongNhan= 0;
			
				double dongia = rsSP.getDouble("dongia");
				double thanhtien = soluongChuyen*dongia;	
						
				totalTienTruocVAT+=thanhtien;
				String chuoingaysd = "";
				if(rsSP.getString("handung").trim().length() > 0)
				{
					String[] hsd = rsSP.getString("handung").split("-");
					chuoingaysd = hsd[2] + "/" + hsd[1] + "/" + hsd[0] ;
				}
								
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("tenSP"), rsSP.getString("solo"), chuoingaysd, 
						rsSP.getString("DONVI"),formatter.format(soluongChuyen)," ", "" , "" };


				for (int j = 0; j < 9; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 8, Font.BOLD)));
					if (j <= 4){
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else{
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					
					
					if(j == 1)
						cells.setPaddingLeft(0.2f*CONVERT);
					if(j == 2)
						cells.setPaddingLeft(0.5f*CONVERT);
					if(j == 3)
						cells.setPaddingLeft(1.0f*CONVERT);
					if(j == 4)
						cells.setPaddingLeft(0.8f*CONVERT);
					if(j == 7)
						cells.setPaddingRight(-0.5f*CONVERT);
					if(j == 8)
						cells.setPaddingRight(-0.3f*CONVERT);
					
						
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPadding(2.0f);	
					cells.setFixedHeight(0.6f*CONVERT);
					cells.setBorder(0);
										
					table.addCell(cells);
				}
				
				stt++;
				
			}
						
		// DÒNG TRỐNG
			
			// DONG TRONG
			int kk=0;
			while(kk < 13-stt)
			{
				String[] arr_bosung = new String[] { " ", " ", " ", " "," " ," ", " "," ", " "};
	
				for (int j = 0; j < 9; j++)
				{
					cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 10, Font.NORMAL)));
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setPadding(2.0f);
					cells.setFixedHeight(0.6f*CONVERT);
					cells.setBorder(0);
										
					table.addCell(cells);
				}
				
				kk++;
			}
			
		String[] arr = new String[] {" ", " "," "," "," ",formatter.format(tongslchuyen)," "," ", ""};//totalTienTruocVAT
			for (int j = 0; j < 9; j++)
			{
				cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 8, Font.BOLD)));
				if (j <= 4){
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
				}
				else{
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				
				
				if(j == 1)
					cells.setPaddingLeft(0.2f*CONVERT);
				if(j == 2)
					cells.setPaddingLeft(1.0f*CONVERT);
				if(j == 3)
					cells.setPaddingLeft(1.0f*CONVERT);
				if(j == 4)
					cells.setPaddingLeft(0.8f*CONVERT);
				if(j == 7)
					cells.setPaddingRight(-0.5f*CONVERT);
				if(j == 8)
					cells.setPaddingRight(-0.3f*CONVERT);
				
				cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
				cells.setPadding(2.0f);	
				cells.setFixedHeight(0.8f*CONVERT);
				cells.setBorder(0);
				table.addCell(cells);
			}
			
																								
			document.add(table);
											
			document.close();
		
			
		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void CreatePxk_DANANG(Document document, ServletOutputStream outstream, IErpChuyenkhoNpp pxkBean) throws IOException
	{
		try
		{
						
			dbutils db = new dbutils();
				
			//LAY THONG TIN NCC
					String ddh="";
					double Vat= 0;
					
					String khoxuat="";
					String khonhan ="";
										
			
			String  sql = " select xuattaikho as khoxuat," +
					      "       (SELECT xuattaikho FROM NHAPHANPHOI WHERE pk_seq = " +
					      "       (select npp_dat_fk from ERP_CHUYENKHONPP where pk_seq = "+ pxkBean.getId() +")) as khonhan "+
				          " from NHAPHANPHOI " +
				          " where pk_seq = '"+ pxkBean.getNppId() +"'";				  
				   
			   
			   System.out.println("Lấy Kho xuất/Kho nhận "+sql);
			   ResultSet rsINFO = db.get(sql);
			   if(rsINFO.next())
			   {
				   khoxuat = rsINFO.getString("khoxuat");
				   khonhan = rsINFO.getString("khonhan");
				   rsINFO.close();
				   
			   }
			
			   // LAY KHO XUAT/ KHO NHAP
			   String ngaychuyen = "";
			   String khoxuat_fk = "";
			   String tenNPP = "";
			   String lenhdieudong = "";
			   String lddcua = "";
			   String lddveviec = "";
			   String ngaylenhdieudong = "";
			   String sohopdong = "";
			   String ngayhopdong = "";
			   String nguoivanchuyen = "";
			   String ptvanchuyen = "";
			
			   
			   sql = " select NGAYCHUYEN,B.TEN AS TENNPP, " +
			   		 "        lenhdieudong, lddcua, lddveviec, ngaydieudong, sohopdong,ngayhopdong, nguoivanchuyen, ptvanchuyen" +
		        	 " from  ERP_CHUYENKHONPP A INNER JOIN NHAPHANPHOI B ON A.NPP_FK = B.PK_SEQ" +
		        	 " where A.PK_SEQ = "+ pxkBean.getId() +" ";				  
		   	   
				   System.out.println("KHO XUAT/ KHO NHAP_ "+sql);
				   ResultSet rskho = db.get(sql);
				   if(rskho.next())
				   {
					   ngaychuyen = rskho.getString("NGAYCHUYEN");
					   tenNPP = rskho.getString("TENNPP");

					   lenhdieudong =rskho.getString("lenhdieudong")== null ? "":rskho.getString("lenhdieudong") ;
					   lddcua =rskho.getString("lddcua")== null ? "  ":rskho.getString("lddcua");
					   lddveviec =rskho.getString("lddveviec")== null ? "  ":rskho.getString("lddveviec");
					   ngaylenhdieudong =rskho.getString("ngaydieudong")== null ? "  ":rskho.getString("ngaydieudong");
					   sohopdong =rskho.getString("sohopdong")== null ? "":rskho.getString("sohopdong");
					   ngayhopdong =rskho.getString("ngayhopdong")== null ? "  ":rskho.getString("ngayhopdong");
					   nguoivanchuyen =rskho.getString("nguoivanchuyen")== null ? "  ":rskho.getString("nguoivanchuyen");
					   ptvanchuyen =rskho.getString("ptvanchuyen")== null ? "  ":rskho.getString("ptvanchuyen");
					   
					   
						
				   rskho.close();
					   
				   }
			   			 
		    NumberFormat formatter = new DecimalFormat("#,###,###.##");
		    document.setPageSize(PageSize.A4.rotate());
			document.setMargins(1.8f*CONVERT, 1.0f*CONVERT, 2.1f*CONVERT, 1.0f*CONVERT); // L,R,T,B
			PdfWriter writer = PdfWriter.getInstance(document, outstream);

			document.open() ;
			
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);

			
			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setWidthPercentage(100);			

			PdfPCell cell = new PdfPCell();
			cell.setBorder(0);
			cell.setPaddingTop(0.55f * CONVERT);
			cell.setPaddingLeft(0.6f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			String [] ngayck = ngaychuyen.split("-");
			Paragraph pxk = new Paragraph(ngayck[2] + "                        " + ngayck[1] +  "                        " + ngayck[0] , new Font(bf, 9, Font.BOLDITALIC));
			pxk.setAlignment(Element.ALIGN_CENTER);
			pxk.setSpacingAfter(2);
			cell.addElement(pxk);

			tableheader.addCell(cell);
			document.add(tableheader);

			
			// Thông tin NCC
			PdfPTable table1 =new PdfPTable(1);
			table1.setWidthPercentage(100);
			
			// TÊN TỔ CHỨC,CÁ NHÂN
			PdfPCell cell7 = new PdfPCell();			
			pxk = new Paragraph(""  , new Font(bf, 9, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell7.setPaddingTop(0.05f*CONVERT);
			cell7.addElement(pxk);
			cell7.setBorder(0);						
			table1.addCell(cell7);				
						
			// ĐỊA CHỈ
			PdfPCell cell9 = new PdfPCell();			
			pxk = new Paragraph(" "   , new Font(bf, 9, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell9.addElement(pxk);
			cell9.setBorder(0);						
			table1.addCell(cell9);	
			
			//MÃ SỐ THUẾ
			PdfPCell cell11 = new PdfPCell();
			pxk = new Paragraph(" " , new Font(bf, 9, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell11.setPaddingLeft(1.5f*CONVERT);
			cell11.addElement(pxk);
			cell11.setBorder(0);						
			table1.addCell(cell11);	
			
			document.add(table1);
			
			// CĂN CỨ LỆNH ĐIỀU ĐỘNG
			String chuoingay = "                             ";
			if(ngaylenhdieudong.trim().length() > 0)
			{
			  String[] ngaydd = ngaylenhdieudong.split("-");
			  chuoingay = "         "+ ngaydd[2] + "                   " + ngaydd[1] + "                   " + ngaydd[0] +"  ";
			}
			
			PdfPTable tableLDD = new PdfPTable(4);
			tableLDD.setWidthPercentage(100);
			float[] withs =  { 8.0f*CONVERT, 6.0f*CONVERT, 5.5f*CONVERT, 5.0f*CONVERT };
			tableLDD.setWidths(withs);
			
			// LỆNH DD SỐ
			PdfPCell cell_so = new PdfPCell();			
			pxk = new Paragraph(" "+ lenhdieudong  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_so.setPaddingTop(0.6f*CONVERT);
			cell_so.setPaddingLeft(4.9f*CONVERT);
			cell_so.addElement(pxk);
			cell_so.setBorder(0);	
			tableLDD.addCell(cell_so);
			
			// NGÀY DD 
			PdfPCell cell_ngaydd = new PdfPCell();			
			pxk = new Paragraph(" "+ chuoingay  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_ngaydd.setPaddingTop(0.6f*CONVERT);
			cell_ngaydd.addElement(pxk);
			cell_ngaydd.setBorder(0);
			tableLDD.addCell(cell_ngaydd);
			
			// LỆNH DD CỦA
			PdfPCell cell_lddcua = new PdfPCell();			
			pxk = new Paragraph(" "+ lddcua  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_lddcua.setPaddingTop(0.6f*CONVERT);
			cell_lddcua.setPaddingLeft(0.8f*CONVERT);
			cell_lddcua.addElement(pxk);
			cell_lddcua.setBorder(0);
			tableLDD.addCell(cell_lddcua);
			
			// LỆNH DD VỀ VIỆC
			PdfPCell cell_veviec = new PdfPCell();			
			pxk = new Paragraph(" "+ lddveviec  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_veviec.setPaddingTop(0.6f*CONVERT);
			cell_veviec.setPaddingLeft(1.4f*CONVERT);
			cell_veviec.addElement(pxk);
			cell_veviec.setBorder(0);
			tableLDD.addCell(cell_veviec);
			
			document.add(tableLDD);				
			
			
			// HỌ TÊN NGƯỜI VẬN CHUYỂN
			PdfPTable tableVC = new PdfPTable(4);
			tableVC.setWidthPercentage(100);
			float[] withs1 =  { 9.5f*CONVERT, 6.0f*CONVERT, 5.5f*CONVERT, 5.0f*CONVERT };
			tableVC.setWidths(withs1);
			
			// NGƯỜI VC
			PdfPCell cell_ten = new PdfPCell();			
			pxk = new Paragraph(" "+ nguoivanchuyen  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_ten.setPaddingTop(0.1f*CONVERT);
			cell_ten.setPaddingLeft(4.7f*CONVERT);
			cell_ten.addElement(pxk);
			cell_ten.setBorder(0);
			tableVC.addCell(cell_ten);
			
			// SỐ HD
			PdfPCell cell_sohd = new PdfPCell();			
			pxk = new Paragraph(" "+ sohopdong  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_sohd.setPaddingTop(0.1f*CONVERT);
			cell_sohd.setPaddingLeft(3.2f*CONVERT);
			cell_sohd.addElement(pxk);
			cell_sohd.setBorder(0);	
			tableVC.addCell(cell_sohd);
			
			// NGÀY HỢP ĐỒNG
			String chuoi = "";
			if(ngayhopdong.trim().length() > 0)
			{
				String[] ngayhd = ngayhopdong.split("-");
				chuoi = ngayhd[2] + "/" + ngayhd[1] + "/" + ngayhd[0];
			}
			
			PdfPCell cell_ngayhd = new PdfPCell();			
			pxk = new Paragraph(" "+ chuoi  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_ngayhd.setPaddingTop(0.1f*CONVERT);
			cell_ngayhd.setPaddingLeft(2.3f*CONVERT);
			cell_ngayhd.addElement(pxk);
			cell_ngayhd.setBorder(0);	
			tableVC.addCell(cell_ngayhd);
			
			// PHƯƠNG TIỆN VC
			PdfPCell cell_pt = new PdfPCell();			
			pxk = new Paragraph(" "+ ptvanchuyen  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_pt.setPaddingTop(0.1f*CONVERT);
			cell_pt.setPaddingLeft(3.5f*CONVERT);
			cell_pt.addElement(pxk);
			cell_pt.setBorder(0);	
			tableVC.addCell(cell_pt);
			
			document.add(tableVC);							
								
			
			// XUẤT TẠI KHO  + NHẬP TẠI KHO		
			PdfPTable tableKHO = new PdfPTable(2);
			tableKHO.setWidthPercentage(100);			
			float[] withs3 =  { 13.0f*CONVERT, 13.0f*CONVERT };
			tableKHO.setWidths(withs3);
			
			
			// XUẤT TẠI KHO
			PdfPCell cell_khoxuat = new PdfPCell();			
			pxk = new Paragraph(" "+ khoxuat  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_khoxuat.setPaddingTop(-0.05f*CONVERT);
			cell_khoxuat.setPaddingLeft(2.2f*CONVERT);
			cell_khoxuat.addElement(pxk);
			cell_khoxuat.setBorder(0);
			tableKHO.addCell(cell_khoxuat);
			
			
			// NHẬP TẠI KHO	
			PdfPCell cell_khonhap = new PdfPCell();			
			pxk = new Paragraph(" "+khonhan  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_khonhap.setPaddingTop(-0.05f*CONVERT);
			cell_khonhap.setPaddingLeft(2.2f*CONVERT);
			cell_khonhap.addElement(pxk);
			cell_khonhap.setBorder(0);	
			tableKHO.addCell(cell_khonhap);
			
			document.add(tableKHO);			
					
			
			
			// Table Content


			//String[] th = new String[]{ "STT", "Tên hàng hóa,dịch vụ", "Số kiểm soát", "Hạn dùng","Số kiện" ,"ĐVT", "Thực xuất", "Thực nhận","Đơn giá", "Thành tiền"};
			String[] th = new String[]{ " ", " ", " ", " "," " ," "," ", " "," "};
			
			PdfPTable table = new PdfPTable(9);
			table.setWidthPercentage(100);
			table.setSpacingBefore(2.1f*CONVERT);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withssp =  { 20.0f, 145.0f, 22.6f, 75.6f, 24.4f, 56.0f, 56.0f,60.0f,84.0f  }; 
			table.setWidths(withssp);

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			
			double tongslnhan=0;
			double tongslchuyen=0;
			
			String query =
			"	select d.ten as tenSP, case when c.solo = 'NA' then '' else isnull(c.solo,'') end as solo, isnull(c.ngayhethan,'') as handung, e.DONVI,c.soluong as SOLUONG, b.DONGIA  "+
			"	from ERP_CHUYENKHONPP a " +
			"   inner join ERP_CHUYENKHONPP_SANPHAM b on a.pk_seq=b.chuyenkho_fk "+	
			"   inner join ERP_CHUYENKHONPP_SANPHAM_CHITIET c on a.pk_seq=c.chuyenkho_fk  and b.sanpham_fk= c.sanpham_fk " +
			"   inner join SANPHAM d on c.SANPHAM_FK = d.PK_SEQ " +
			"   inner join DONVIDOLUONG e on b.dvdl_fk = e.PK_SEQ "+			
			"	where a.PK_SEQ ="+ pxkBean.getId() +" ";
			
			
			System.out.println("[ERP_CHUYENKHO_SANPHAM1]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			while(rsSP.next())
			{
				double soluongChuyen = rsSP.getDouble("soluong");
				tongslchuyen +=soluongChuyen;
				
				double soluongNhan= 0;
				
				double dongia = rsSP.getDouble("dongia");
				double thanhtien = soluongChuyen*dongia;	
						
				totalTienTruocVAT+=thanhtien;
				String chuoingaysd = "";
				if(rsSP.getString("handung").trim().length() > 0)
				{
					String[] hsd = rsSP.getString("handung").split("-");
					chuoingaysd = hsd[2] + "/" + hsd[1] + "/" + hsd[0] ;
				}
								
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("tenSP"), rsSP.getString("solo"), chuoingaysd, 
						rsSP.getString("DONVI"),formatter.format(soluongChuyen), " ", "" , "" };


				for (int j = 0; j < 9; j++)
				{
					if (j <= 4){
						cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 9, Font.BOLD)));
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else if(j==5){
						cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLD)));
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					else{
						cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 9, Font.BOLD)));
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					
					if(j == 0)
						cells.setPaddingLeft(0.9f*CONVERT);
					if(j == 1)
						cells.setPaddingLeft(0.8f*CONVERT);
					if(j == 2)
						cells.setPaddingLeft(-0.6f*CONVERT);
					if(j == 3)
						cells.setPaddingLeft(-1.0f*CONVERT);
					if(j == 4)
						cells.setPaddingLeft(2.0f*CONVERT);
					if(j == 7)
						cells.setPaddingRight(-0.8f*CONVERT);
					if(j == 8)
						cells.setPaddingRight(-0.3f*CONVERT);
					
						
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPadding(2.0f);	
					cells.setFixedHeight(0.6f*CONVERT);
					cells.setBorder(0);
										
					table.addCell(cells);
				}
				
				stt++;
				
			}
						
		// DÒNG TRỐNG
			
			// DONG TRONG
			int kk=0;
			while(kk < 11-stt)
			{
				String[] arr_bosung = new String[] { " ", " ", " ", " "," " ," ", " "," ", " "};
	
				for (int j = 0; j < 9; j++)
				{
					cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 9, Font.NORMAL)));
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setPadding(2.0f);
					cells.setFixedHeight(0.6f*CONVERT);
					cells.setBorder(0);
										
					table.addCell(cells);
				}
				
				kk++;
			}
			
		String[] arr = new String[] {" ", " "," "," "," ",formatter.format(tongslchuyen), " "," ", ""};//totalTienTruocVAT
			for (int j = 0; j < 9; j++)
			{
				if (j <= 4){
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 9, Font.BOLD)));
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
				}
				else if(j==5){
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLD)));
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				else{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 9, Font.BOLD)));
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				
				if(j == 0)
					cells.setPaddingLeft(0.9f*CONVERT);
				if(j == 1)
					cells.setPaddingLeft(0.8f*CONVERT);
				if(j == 2)
					cells.setPaddingLeft(-0.6f*CONVERT);
				if(j == 3)
					cells.setPaddingLeft(-1.0f*CONVERT);
				if(j == 4)
					cells.setPaddingLeft(2.0f*CONVERT);
				if(j == 7)
					cells.setPaddingRight(-0.8f*CONVERT);
				if(j == 8)
					cells.setPaddingRight(-0.3f*CONVERT);
				
				cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
				cells.setPadding(2.0f);	
				cells.setFixedHeight(0.8f*CONVERT);
				cells.setBorder(0);
				table.addCell(cells);
			}
			
																								
			document.add(table);
											
			document.close();
		
			
		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	private void CreatePxk_KHANHHOA(Document document, ServletOutputStream outstream, IErpChuyenkhoNpp pxkBean) throws IOException
	{
		try
		{
						
			dbutils db = new dbutils();
				
			//LAY THONG TIN NCC
					String ddh="";
					double Vat= 0;
					
					String khoxuat="";
					String khonhan ="";
										
			
			String  sql = " select xuattaikho as khoxuat," +
					      "       (SELECT xuattaikho FROM NHAPHANPHOI WHERE pk_seq = " +
					      "       (select npp_dat_fk from ERP_CHUYENKHONPP where pk_seq = "+ pxkBean.getId() +")) as khonhan "+
				          " from NHAPHANPHOI " +
				          " where pk_seq = '"+ pxkBean.getNppId() +"'";				  
				   
			   
			   System.out.println("Lấy Kho xuất/Kho nhận "+sql);
			   ResultSet rsINFO = db.get(sql);
			   if(rsINFO.next())
			   {
				   khoxuat = rsINFO.getString("khoxuat");
				   khonhan = rsINFO.getString("khonhan");
				   rsINFO.close();
				   
			   }
			
			   // LAY KHO XUAT/ KHO NHAP
			   String ngaychuyen = "";
			   String khoxuat_fk = "";
			   String tenNPP = "";
			   String lenhdieudong = "";
			   String lddcua = "";
			   String lddveviec = "";
			   String ngaylenhdieudong = "";
			   String sohopdong = "";
			   String ngayhopdong = "";
			   String nguoivanchuyen = "";
			   String ptvanchuyen = "";
			
			   
			   sql = " select NGAYCHUYEN,B.TEN AS TENNPP, " +
			   		 "        lenhdieudong, lddcua, lddveviec, ngaydieudong, sohopdong,ngayhopdong, nguoivanchuyen, ptvanchuyen" +
		        	 " from  ERP_CHUYENKHONPP A INNER JOIN NHAPHANPHOI B ON A.NPP_FK = B.PK_SEQ" +
		        	 " where A.PK_SEQ = "+ pxkBean.getId() +" ";				  
		   	   
				   System.out.println("KHO XUAT/ KHO NHAP_ "+sql);
				   ResultSet rskho = db.get(sql);
				   if(rskho.next())
				   {
					   ngaychuyen = rskho.getString("NGAYCHUYEN");
					   tenNPP = rskho.getString("TENNPP");

					   lenhdieudong =rskho.getString("lenhdieudong")== null ? "":rskho.getString("lenhdieudong") ;
					   lddcua =rskho.getString("lddcua")== null ? "  ":rskho.getString("lddcua");
					   lddveviec =rskho.getString("lddveviec")== null ? "  ":rskho.getString("lddveviec");
					   ngaylenhdieudong =rskho.getString("ngaydieudong")== null ? "  ":rskho.getString("ngaydieudong");
					   sohopdong =rskho.getString("sohopdong")== null ? "":rskho.getString("sohopdong");
					   ngayhopdong =rskho.getString("ngayhopdong")== null ? "  ":rskho.getString("ngayhopdong");
					   nguoivanchuyen =rskho.getString("nguoivanchuyen")== null ? "  ":rskho.getString("nguoivanchuyen");
					   ptvanchuyen =rskho.getString("ptvanchuyen")== null ? "  ":rskho.getString("ptvanchuyen");
					   
					   
						
				   rskho.close();
					   
				   }
			   			 
		    NumberFormat formatter = new DecimalFormat("#,###,###.##");
		    document.setPageSize(PageSize.A4.rotate());
			document.setMargins(1.1f*CONVERT, 1.0f*CONVERT, 2.6f*CONVERT, 1.0f*CONVERT); // L,R,T,B
			PdfWriter writer = PdfWriter.getInstance(document, outstream);

			document.open() ;
			
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);

			
			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setWidthPercentage(100);			

			PdfPCell cell = new PdfPCell();
			cell.setBorder(0);
			cell.setPaddingTop(0.65f * CONVERT);//0.55
			cell.setPaddingLeft(1.4f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			String [] ngayck = ngaychuyen.split("-");
			Paragraph pxk = new Paragraph("       "+ngayck[2] + "                     " + ngayck[1] +  "                   " + ngayck[0] , new Font(bf, 9, Font.BOLDITALIC));
			pxk.setAlignment(Element.ALIGN_CENTER);
			pxk.setSpacingAfter(2);
			cell.addElement(pxk);

			tableheader.addCell(cell);
			document.add(tableheader);

			
			// Thông tin NCC
			PdfPTable table1 =new PdfPTable(1);
			table1.setWidthPercentage(100);
			
			// TÊN TỔ CHỨC,CÁ NHÂN
			PdfPCell cell7 = new PdfPCell();			
			pxk = new Paragraph(""  , new Font(bf, 9, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell7.setPaddingTop(0.05f*CONVERT);
			cell7.addElement(pxk);
			cell7.setBorder(0);						
			table1.addCell(cell7);				
						
			// ĐỊA CHỈ
			PdfPCell cell9 = new PdfPCell();			
			pxk = new Paragraph(" "   , new Font(bf, 9, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell9.addElement(pxk);
			cell9.setBorder(0);						
			table1.addCell(cell9);	
			
			//MÃ SỐ THUẾ
			PdfPCell cell11 = new PdfPCell();
			pxk = new Paragraph(" " , new Font(bf, 9, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell11.setPaddingLeft(1.5f*CONVERT);
			cell11.addElement(pxk);
			cell11.setBorder(0);						
			table1.addCell(cell11);	
			
			document.add(table1);
			
			// CĂN CỨ LỆNH ĐIỀU ĐỘNG
			String chuoingay = "                             ";
			if(ngaylenhdieudong.trim().length() > 0)
			{
			  String[] ngaydd = ngaylenhdieudong.split("-");
			  chuoingay = "         "+ ngaydd[2] + "                   " + ngaydd[1] + "                   " + ngaydd[0] +"  ";
			}
			
			PdfPTable tableLDD = new PdfPTable(4);
			tableLDD.setWidthPercentage(100);
			float[] withs =  { 8.0f*CONVERT, 6.0f*CONVERT, 5.5f*CONVERT, 5.0f*CONVERT };
			tableLDD.setWidths(withs);
			
			// LỆNH DD SỐ
			PdfPCell cell_so = new PdfPCell();			
			pxk = new Paragraph(" "+ lenhdieudong  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_so.setPaddingTop(0.6f*CONVERT);
			cell_so.setPaddingLeft(4.9f*CONVERT);
			cell_so.addElement(pxk);
			cell_so.setBorder(0);	
			tableLDD.addCell(cell_so);
			
			// NGÀY DD 
			PdfPCell cell_ngaydd = new PdfPCell();			
			pxk = new Paragraph(" "+ chuoingay  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_ngaydd.setPaddingTop(0.5f*CONVERT);
			cell_ngaydd.addElement(pxk);
			cell_ngaydd.setBorder(0);
			tableLDD.addCell(cell_ngaydd);
			
			// LỆNH DD CỦA
			PdfPCell cell_lddcua = new PdfPCell();			
			pxk = new Paragraph(" "+ lddcua  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_lddcua.setPaddingTop(0.5f*CONVERT);
			cell_lddcua.setPaddingLeft(0.8f*CONVERT);
			cell_lddcua.addElement(pxk);
			cell_lddcua.setBorder(0);
			tableLDD.addCell(cell_lddcua);
			
			// LỆNH DD VỀ VIỆC
			PdfPCell cell_veviec = new PdfPCell();			
			pxk = new Paragraph(" "+ lddveviec  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_veviec.setPaddingTop(0.5f*CONVERT);
			cell_veviec.setPaddingLeft(1.4f*CONVERT);
			cell_veviec.addElement(pxk);
			cell_veviec.setBorder(0);
			tableLDD.addCell(cell_veviec);
			
			document.add(tableLDD);				
			
			
			// HỌ TÊN NGƯỜI VẬN CHUYỂN
			PdfPTable tableVC = new PdfPTable(4);
			tableVC.setWidthPercentage(100);
			float[] withs1 =  { 9.5f*CONVERT, 6.0f*CONVERT, 5.5f*CONVERT, 5.0f*CONVERT };
			tableVC.setWidths(withs1);
			
			// NGƯỜI VC
			PdfPCell cell_ten = new PdfPCell();			
			pxk = new Paragraph(" "+ nguoivanchuyen  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_ten.setPaddingTop(0f*CONVERT);
			cell_ten.setPaddingLeft(4.7f*CONVERT);
			cell_ten.addElement(pxk);
			cell_ten.setBorder(0);
			tableVC.addCell(cell_ten);
			
			// SỐ HD
			PdfPCell cell_sohd = new PdfPCell();			
			pxk = new Paragraph(" "+ sohopdong  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_sohd.setPaddingTop(0f*CONVERT);
			cell_sohd.setPaddingLeft(3.2f*CONVERT);
			cell_sohd.addElement(pxk);
			cell_sohd.setBorder(0);	
			tableVC.addCell(cell_sohd);
			
			// NGÀY HỢP ĐỒNG
			String chuoi = "";
			if(ngayhopdong.trim().length() > 0)
			{
				String[] ngayhd = ngayhopdong.split("-");
				chuoi = ngayhd[2] + "/" + ngayhd[1] + "/" + ngayhd[0];
			}
			
			PdfPCell cell_ngayhd = new PdfPCell();			
			pxk = new Paragraph(" "+ chuoi  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_ngayhd.setPaddingTop(0f*CONVERT);
			cell_ngayhd.setPaddingLeft(2.3f*CONVERT);
			cell_ngayhd.addElement(pxk);
			cell_ngayhd.setBorder(0);	
			tableVC.addCell(cell_ngayhd);
			
			// PHƯƠNG TIỆN VC
			PdfPCell cell_pt = new PdfPCell();			
			pxk = new Paragraph(" "+ ptvanchuyen  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_pt.setPaddingTop(0f*CONVERT);
			cell_pt.setPaddingLeft(3.5f*CONVERT);
			cell_pt.addElement(pxk);
			cell_pt.setBorder(0);	
			tableVC.addCell(cell_pt);
			
			document.add(tableVC);							
								
			
			// XUẤT TẠI KHO  + NHẬP TẠI KHO		
			PdfPTable tableKHO = new PdfPTable(2);
			tableKHO.setWidthPercentage(100);			
			float[] withs3 =  { 13.0f*CONVERT, 13.0f*CONVERT };
			tableKHO.setWidths(withs3);
			
			
			// XUẤT TẠI KHO
			PdfPCell cell_khoxuat = new PdfPCell();			
			pxk = new Paragraph(" "+ khoxuat  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_khoxuat.setPaddingTop(-0.15f*CONVERT);
			cell_khoxuat.setPaddingLeft(2.2f*CONVERT);
			cell_khoxuat.addElement(pxk);
			cell_khoxuat.setBorder(0);
			tableKHO.addCell(cell_khoxuat);
			
			
			// NHẬP TẠI KHO	
			PdfPCell cell_khonhap = new PdfPCell();			
			pxk = new Paragraph(" "+khonhan  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_khonhap.setPaddingTop(-0.15f*CONVERT);
			cell_khonhap.setPaddingLeft(2.2f*CONVERT);
			cell_khonhap.addElement(pxk);
			cell_khonhap.setBorder(0);	
			tableKHO.addCell(cell_khonhap);
			
			document.add(tableKHO);			
					
			
			
			// Table Content


			//String[] th = new String[]{ "STT", "Tên hàng hóa,dịch vụ", "Số kiểm soát", "Hạn dùng","Số kiện" ,"ĐVT", "Thực xuất", "Thực nhận","Đơn giá", "Thành tiền"};
			String[] th = new String[]{ " ", " ", " ", " "," " ," "," ", " "," "};
			
			PdfPTable table = new PdfPTable(9);
			table.setWidthPercentage(100);
			table.setSpacingBefore(2.2f*CONVERT);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			//float[] withssp =  { 20.0f, 145.0f, 22.6f, 75.6f, 24.4f, 56.0f, 56.0f,60.0f,84.0f  };
			float[] withssp =  { 20.0f, 137.0f, 55.6f, 50.6f, 24.4f, 56.0f, 56.0f,60.0f,84.0f  }; 

			table.setWidths(withssp);

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			
			double tongslnhan=0;
			double tongslchuyen=0;
			
			String query =
			"	select d.ten as tenSP, case when c.solo = 'NA' then '' else isnull(Rtrim(c.solo),'') end as solo, isnull(c.ngayhethan,'') as handung, e.DONVI,c.soluong as SOLUONG, b.DONGIA  "+
			"	from ERP_CHUYENKHONPP a " +
			"   inner join ERP_CHUYENKHONPP_SANPHAM b on a.pk_seq=b.chuyenkho_fk "+	
			"   inner join ERP_CHUYENKHONPP_SANPHAM_CHITIET c on a.pk_seq=c.chuyenkho_fk  and b.sanpham_fk= c.sanpham_fk " +
			"   inner join SANPHAM d on c.SANPHAM_FK = d.PK_SEQ " +
			"   inner join DONVIDOLUONG e on b.dvdl_fk = e.PK_SEQ "+			
			"	where a.PK_SEQ ="+ pxkBean.getId() +" ";
			
			
			System.out.println("[ERP_CHUYENKHO_SANPHAM1]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			while(rsSP.next())
			{
				double soluongChuyen = rsSP.getDouble("soluong");
				tongslchuyen +=soluongChuyen;
				
				double soluongNhan= 0;
				
				double dongia = rsSP.getDouble("dongia");
				double thanhtien = soluongChuyen*dongia;	
						
				totalTienTruocVAT+=thanhtien;
				String chuoingaysd = "";
				if(rsSP.getString("handung").trim().length() > 0)
				{
					String[] hsd = rsSP.getString("handung").split("-");
					chuoingaysd = hsd[2] + "/" + hsd[1] + "/" + hsd[0] ;
				}
				
			/*	String[] arr = new String[] { Integer.toString(stt), rsSP.getString("tenSP"), rsSP.getString("solo"), chuoingaysd, 
						rsSP.getString("DONVI"),formatter.format(soluongChuyen), " ", "" , "" };*/
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("tenSP"), rsSP.getString("solo")+"   "+ chuoingaysd, "",
						rsSP.getString("DONVI"),formatter.format(soluongChuyen), " ", "" , "" };


				for (int j = 0; j < 9; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 9, Font.BOLD)));
					if (j <= 4){
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else{
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					
					if(j == 0)
						cells.setPaddingLeft(0.9f*CONVERT);
					if(j == 1)
						cells.setPaddingLeft(0.8f*CONVERT);
					if(j == 2)
						cells.setPaddingLeft(-0.6f*CONVERT);
					
					if(j == 4)
						cells.setPaddingLeft(2.0f*CONVERT);
					if(j == 7)
						cells.setPaddingRight(-0.8f*CONVERT);
					if(j == 8)
						cells.setPaddingRight(-0.3f*CONVERT);
					
						
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPadding(2.0f);	
					cells.setFixedHeight(0.6f*CONVERT);
					cells.setBorder(0);
										
					table.addCell(cells);
				}
				
				stt++;
				
			}
						
		// DÒNG TRỐNG
			
			// DONG TRONG
			int kk=0;
			while(kk < 11-stt)
			{
				String[] arr_bosung = new String[] { " ", " ", " ", " "," " ," ", " "," ", " "};
	
				for (int j = 0; j < 9; j++)
				{
					cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 9, Font.NORMAL)));
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setPadding(2.0f);
					cells.setFixedHeight(0.6f*CONVERT);
					cells.setBorder(0);
										
					table.addCell(cells);
				}
				
				kk++;
			}
			
		String[] arr = new String[] {" ", " "," "," "," ",formatter.format(tongslchuyen), " "," ", ""};//totalTienTruocVAT
			for (int j = 0; j < 9; j++)
			{
				cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 9, Font.BOLD)));
				if (j <= 4){
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
				}
				else{
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				
				
				if(j == 0)
					cells.setPaddingLeft(0.9f*CONVERT);
				if(j == 1)
					cells.setPaddingLeft(0.8f*CONVERT);
				if(j == 2)
					cells.setPaddingLeft(-0.6f*CONVERT);
				
				if(j == 4)
					cells.setPaddingLeft(2.0f*CONVERT);
				if(j == 7)
					cells.setPaddingRight(-0.8f*CONVERT);
				if(j == 8)
					cells.setPaddingRight(-0.3f*CONVERT);
				
				
				cells.setVerticalAlignment(Element.ALIGN_TOP);
				cells.setPaddingTop(-1.0f);	
				cells.setFixedHeight(0.6f*CONVERT);
				cells.setBorder(0);
				table.addCell(cells);
			}
			
																								
			document.add(table);
											
			document.close();
		
			
		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private String getSTT(int stt)
	{
		if (stt < 10)
			return "000" + Integer.toString(stt);
		if (stt < 100)
			return "00" + Integer.toString(stt);
		if (stt < 1000)
			return "0" + Integer.toString(stt);
		return Integer.toString(stt);
	}

}
