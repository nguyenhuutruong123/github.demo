package geso.dms.center.servlets.xuatkho;

import geso.dms.center.beans.xuatkho.IErpXuatkho;
import geso.dms.center.beans.xuatkho.imp.ErpXuatkho;
import geso.dms.center.db.sql.dbutils;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ErpXuatkhoPdfSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErpXuatkhoPdfSvl()
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

		String id = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id"));
		IErpXuatkho pxkBean = new ErpXuatkho(id);
		pxkBean.setUserId(userId);

		String task = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("task"));
		if (task.equals("xuatkho"))
		{
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", " inline; filename=PhieuXuatKhoTT.pdf");

			Document document = new Document();
			ServletOutputStream outstream = response.getOutputStream();

			this.CreatePhieuxuatkho_DinhKem(document, outstream, pxkBean);
			
		} 
		else
			if (task.equals("layhang"))
			{
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", " inline; filename=PhieuLayHangTT.pdf");

				Document document = new Document();
				ServletOutputStream outstream = response.getOutputStream();

				this.CreatePxk(document, outstream, pxkBean);
			}
		else
		{
			if (task.equals("dondathangPdf"))
			{
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", " inline; filename=DonDatHangTT.pdf");

				Document document = new Document();
				ServletOutputStream outstream = response.getOutputStream();

				this.DonDatHangPdf(document, outstream, pxkBean);
			}
		}
		

	}

	private void DonDatHangPdf(Document document, ServletOutputStream outstream, IErpXuatkho pxkBean)
  {
		try
		{
			
			//LAY THONG TIN DON HANG
			dbutils db = new dbutils();
					
					String sql = "select  b.DIACHI as dcKho,b.LienHe as ttLienHe,b.congty as ctyTen , a.vat,a.NgayDonHang, b.MA as maKHO, b.TEN as tenKHO, c.TEN as nppTEN, c.DIACHI as diaCHI, c.DIENTHOAI " +
								 "from ERP_DONDATHANG a inner join ERP_KHOTT b on a.KHO_FK = b.PK_SEQ " +
								 "		inner join NHAPHANPHOI c on a.NPP_FK = c.PK_SEQ  " +
								 "where a.PK_SEQ = '" + pxkBean.getId() + "' ";
					
					System.out.println("[INIT_ERP_DONDATHANG]"+sql);
					
					ResultSet rsINFO = db.get(sql);
					String ngayYC = "";
					String khTEN = "";
					String khDIACHI = "";
					String khDIENTHOAI = "";
					String khoTEN = "";
					float vat=0;
					
					String ctyTen="";
					String dcKho="";
					String ttLienHe="";
					
					if(rsINFO.next())
					{
						ctyTen=rsINFO.getString("ctyTen");
						dcKho = rsINFO.getString("dcKho");
						ttLienHe =rsINFO.getString("ttLienHe");
						
						ngayYC = rsINFO.getString("NgayDonHang");
						khTEN = rsINFO.getString("nppTEN");
						khDIACHI = rsINFO.getString("diaCHI");
						khDIENTHOAI = rsINFO.getString("DIENTHOAI");
						khoTEN = rsINFO.getString("tenKHO");
						vat=rsINFO.getFloat("vat");
						rsINFO.close();
					}
			
			
			
			
			NumberFormat formatter = new DecimalFormat("#,###,###.##");
			PdfWriter.getInstance(document, outstream);
			document.open();
			// document.setPageSize(new Rectangle(210.0f, 297.0f));

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			
			PdfPTable tableheader=new PdfPTable(2);
			tableheader.setWidthPercentage(100);
			tableheader.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withsheader = {100.0f, 350.0f};
			tableheader.setWidths(withsheader); 
			
			Image hinhanh = Image.getInstance( getServletContext().getInitParameter("path")+"/logo.gif");	
			hinhanh.setAlignment(Element.ALIGN_CENTER);
			hinhanh.setWidthPercentage(60.0f);
			hinhanh.scalePercent(70);
			
			PdfPCell cellslogo = new PdfPCell(hinhanh);
			cellslogo.setPadding(2);
			cellslogo.setBorder(0);
			tableheader.addCell(cellslogo);
			
			PdfPCell celltieude = new PdfPCell();
			
			
			Paragraph pxk = new Paragraph(ctyTen, new Font(bf, 8, Font.BOLD));
			pxk.setSpacingAfter(2);
			pxk.setAlignment(Element.ALIGN_LEFT);
			//document.add(pxk);
			celltieude.addElement(pxk);

			pxk = new Paragraph(dcKho, new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(2);
			pxk.setAlignment(Element.ALIGN_LEFT);
			//document.add(pxk);
			celltieude.addElement(pxk);
			
			pxk = new Paragraph("Văn phòng: 319/D4 Lý Thường Kiệt, P15, Q11, Tp Hồ Chí Minh", new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(2);
			pxk.setAlignment(Element.ALIGN_LEFT);
			//document.add(pxk);
			celltieude.addElement(pxk);
			
			pxk = new Paragraph(ttLienHe, new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(2);
			pxk.setAlignment(Element.ALIGN_LEFT);
			//document.add(pxk);
			celltieude.addElement(pxk);
			
			pxk = new Paragraph("Tài khoản số: 060001323663 Tại NH Sacombank-PGD Lý Thường Kiệt, TP Hồ CHÍ MINH", new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(2);
			pxk.setAlignment(Element.ALIGN_LEFT);
			//document.add(pxk);
			celltieude.setBorder(0);
			celltieude.addElement(pxk);
			
			tableheader.addCell(celltieude);
			
			document.add(tableheader);	
			
			
			
			pxk = new Paragraph("PHIẾU GIAO HÀNG", font);
			pxk.setSpacingAfter(3);
			pxk.setSpacingBefore(10);
			pxk.setAlignment(Element.ALIGN_CENTER);
			document.add(pxk);

			pxk = new Paragraph("Số: " + pxkBean.getId(), new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(5);
			pxk.setAlignment(Element.ALIGN_RIGHT);
			document.add(pxk);
			
			String[] ngayARR = ngayYC.split("-");
			pxk = new Paragraph("Ngày: " + ngayARR[2] + "    Tháng: " + ngayARR[1] + "     Năm: " + ngayARR[0], new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(5);
			pxk.setAlignment(Element.ALIGN_CENTER);
			document.add(pxk);

			pxk = new Paragraph("Khách hàng: " + khTEN, new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(3);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);

			pxk = new Paragraph("Địa chỉ: " + khDIACHI, new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(3);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			pxk = new Paragraph("Số điện thoại : " + khDIENTHOAI, new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(3);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			pxk = new Paragraph("Xuất tại kho: " + khoTEN, new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(3);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);

			// Table Content
			PdfPTable root = new PdfPTable(2);
			root.setKeepTogether(false);
			root.setSplitLate(false);
			root.setWidthPercentage(100);
			root.setHorizontalAlignment(Element.ALIGN_LEFT);
			root.getDefaultCell().setBorder(0);
			float[] cr = { 95.0f, 100.0f };
			root.setWidths(cr);

			PdfPTable table = new PdfPTable(5);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withs = { 7.0f, 22.0f, 40.0f, 13.0f, 13.0f };
			table.setWidths(withs);

			Font font4 = new Font(bf, 8, Font.BOLD);

			String[] th = new String[]{ "STT", "Mã số", "Tên sản phẩm", "ĐVT", "Quy cách","Đơn giá" ,"Thùng", "Lẻ","Thành tiền" };
			
			PdfPTable sanpham = new PdfPTable(th.length);
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withsKM = { 8.0f, 10.0f, 35.0f, 15.0f, 15.0f, 15.0f,10.0f, 10.0f,20.0f };
			sanpham.setWidths(withsKM);

			PdfPCell[] cell = new PdfPCell[th.length];
			for (int i = 0; i < th.length; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], font4));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);

				cell[i].setPadding(4);
				sanpham.addCell(cell[i]);
			}

			PdfPCell cells = new PdfPCell();
			double totalTrongLuong = 0;
			double totalTheTich = 0;
			double totalthung = 0;
			double totalle = 0;
			
			double totalTienTruocVAT=0;
			
			String query =
			"		select c.MA, c.TEN, d.DONVI,b.dvdl_fk,   "+
			"		b.soluong,b.dongia,    "+
			"		isnull(c.TRONGLUONG, 0) as TRONGLUONG, isnull(c.THETICH, 0) as THETICH   ,  "+
			"	ISNULL  "+
			"	(  "+
			"		case when b.dvdl_fk= (select dvdl2_fk from QUYCACH where DVDL2_FK=b.dvdl_fk and DVDL1_FK=c.DVDL_FK and sanpham_fk=b.sanpham_fk ) then 1  "+
			"		when b.dvdl_fk=c.DVDL_FK then   "+
			"		(select SOLUONG1 from QUYCACH where DVDL2_FK=100018 and DVDL1_FK=c.DVDL_FK and sanpham_fk=b.sanpham_fk) end ,1  "+
			"	) as QuyCach,ISNULL( ( select SOLUONG1 from QUYCACH where sanpham_fk = c.PK_SEQ and DVDL2_FK = '100018' ), 1 ) as quycachTHG   "+
			"	from ERP_DONDATHANG a inner join ERP_DONDATHANG_SANPHAM b on a.PK_SEQ = b.dondathang_fk    "+
			"		inner join SANPHAM c on b.sanpham_fk = c.PK_SEQ     "+
			"		left join DONVIDOLUONG d on d.PK_SEQ=b.dvdl_fk   "+
			"	where a.PK_SEQ = '"+ pxkBean.getId() +"' ";
			
			
			System.out.println("[ERP_DONDATHANG_SANPHAM]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double quycachTHG = rsSP.getDouble("quycachTHG");
				
				double quyCACH = rsSP.getDouble("quycach");
				
				double thung = Math.round( (soLUONG / quyCACH) - 0.5 );
				double le = Math.round( (soLUONG % quyCACH) - 0.5 );
				double dongia = rsSP.getDouble("dongia");
				double thanhtien = soLUONG*dongia;
				
				
				double SoluongLe =soLUONG*quyCACH/quycachTHG;   
						
				totalTienTruocVAT+=thanhtien;
				
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA"), rsSP.getString("TEN"), rsSP.getString("DONVI"), rsSP.getString("quycachTHG"),
						formatter.format(dongia),formatter.format(thung),
												formatter.format(le),formatter.format(thanhtien) };

				totalthung += thung;
				totalle += le;
				totalTrongLuong += ( SoluongLe * rsSP.getDouble("TRONGLUONG") );
				totalTheTich += ( SoluongLe * rsSP.getDouble("THETICH") );

				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 8, Font.NORMAL)));
					if (j == 2)
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					else
						cells.setHorizontalAlignment(Element.ALIGN_CENTER);
				
					if(j==8)
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPadding(2.0f);

					sanpham.addCell(cells);
				}
				
				stt++;
				
			}
			
			int soDong=0;
			query= " select count(*) as soDong from ERP_DONDATHANG_CTKM_TRAKM where DONDATHANGID='"+pxkBean.getId()+"' ";
			ResultSet rs=db.get(query);
			while(rs.next())
			{
				soDong=rs.getInt("soDong");
			}
			if(soDong>0)
			{
				String[] arr = new String[] { "Hàng Khuyến Mại" };
				for (int j = 0; j < arr.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], font4));
					if (j == 0)
					{
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						cells.setColspan(9);
					} else 
					{
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPadding(0.1f * CONVERT);
	
					sanpham.addCell(cells);
				}
				
				 query =
							"	select c.MA, c.TEN, d.DONVI, ISNULL( ( select SOLUONG1 from QUYCACH where sanpham_fk = c.PK_SEQ and DVDL2_FK = '100018' ), 1 ) as quycachTHG,  "+ 
							"		b.soluong,0 as  dongia, km.SCHEME,   "+
							"		isnull(c.TRONGLUONG, 0) as TRONGLUONG, isnull(c.THETICH, 0) as THETICH , "+  
							"	ISNULL "+
							"	( "+
							"		( select SOLUONG1 from QUYCACH where sanpham_fk = c.PK_SEQ and DVDL2_FK = '100018' ), 1 "+
							" ) as QuyCach "+
							"	from ERP_DONDATHANG a inner join ERP_DONDATHANG_CTKM_TRAKM b on a.PK_SEQ = b.DONDATHANGID "+  
							"		inner join SANPHAM c on b.SPMA = c.MA   "+
							"		left join DONVIDOLUONG d on c.DVDL_FK = d.PK_SEQ   "+
							"		left join CTKHUYENMAI km on km.PK_SEQ=b.CTKMID "+
							"	where a.PK_SEQ = '"+pxkBean.getId()+"' ";
								 rsSP = db.get(query);
								 
								 System.out.println("[INIT_ERP_DONDATHANG_CTKM_TRAKM]"+query);
								 
								stt = 1;
								while(rsSP.next())
								{
									double soLUONG = rsSP.getDouble("soluong");
									double quyCACH = rsSP.getDouble("quycach");
									
									double quycachTHG = rsSP.getDouble("quycachTHG");
									double SoluongLe =soLUONG*quyCACH/quycachTHG;   
									
									
									double thung = Math.round( (soLUONG / quyCACH) - 0.5 );
									double le = Math.round( (soLUONG % quyCACH) - 0.5 );
									double dongia = rsSP.getDouble("dongia");
									double thanhtien = soLUONG*dongia;
									totalTienTruocVAT+=thanhtien;
									
									 arr = new String[] { Integer.toString(stt), rsSP.getString("MA"), rsSP.getString("TEN"), rsSP.getString("DONVI"), rsSP.getString("quycachTHG"),
											formatter.format(dongia),formatter.format(thung),
																	formatter.format(le),formatter.format(thanhtien) };

									totalthung += thung;
									totalle += le;
									totalTrongLuong += ( SoluongLe * rsSP.getDouble("TRONGLUONG") );
									totalTheTich += ( SoluongLe * rsSP.getDouble("THETICH") );

									for (int j = 0; j < th.length; j++)
									{
										cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 8, Font.NORMAL)));
										if (j == 2 || j == 7)
											cells.setHorizontalAlignment(Element.ALIGN_LEFT);
										else
											cells.setHorizontalAlignment(Element.ALIGN_CENTER);
										
										if (j > 5 && j != 8)
											cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
										
										cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
										cells.setPadding(2.0f);

										sanpham.addCell(cells);
									}
									stt++;
								}
						}
			if(rs!=null)
				rs.close();
			
		String[] arr = new String[] { "","Tổng cộng", formatter.format(totalthung), formatter.format(totalle),formatter.format(totalTienTruocVAT) };
			for (int j = 0; j < arr.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr[j], font4));
				if (j == 0)
				{
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					cells.setColspan(3);
				} else if(j==1)
				{
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					cells.setColspan(3);
				}else
				{
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(0.1f * CONVERT);
				sanpham.addCell(cells);
			}
			
			arr = new String[] { "","Tổng giá trị đơn hàng",formatter.format(totalTienTruocVAT) };
			for (int j = 0; j < arr.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr[j], font4));
				if (j == 0)
				{
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					cells.setColspan(3);
				} else if(j==1)
				{
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					cells.setColspan(5);
				}else
				{
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(0.1f * CONVERT);

				sanpham.addCell(cells);
			}
			
			double totalTienCK = 0;
			double tongtien_sau_hoahong = 0;
			query="select DienGiai,GiaTri,Loai  from ERP_DONDATHANG_CHIETKHAU where dondathang_fk='"+pxkBean.getId()+"' ";
			rs=db.get(query);
			int i=0;
			while(rs.next())
			{
				int loaiCK=rs.getInt("Loai");
				double GiaTri=rs.getDouble("GiaTri");
				double TienCK=0;
				
				if(loaiCK==0)
				{
					TienCK=GiaTri;
				}
				else  
				{
					if( i == 0)
					{
						double tt_hoahong =GiaTri * totalTienTruocVAT / 100;
						tongtien_sau_hoahong = totalTienTruocVAT - tt_hoahong;
						TienCK=tt_hoahong;
					}
					else
					{
						TienCK=(GiaTri * tongtien_sau_hoahong )  / 100;
					}
				}
				i++;
				totalTienCK +=TienCK ;
				
				arr = new String[] {"",rs.getString("DienGiai"),formatter.format(TienCK) };
				for (int j = 0; j < arr.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], font4));
					if (j == 0)
					{
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						cells.setColspan(3);
					} else if(j==1)
					{
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						cells.setColspan(5);
					}else
					{
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPadding(0.1f * CONVERT);

					sanpham.addCell(cells);
				}
			}
			if(rs!=null)
			{
				rs.close();
			}
			
			arr = new String[] { "","Tổng số tiền phải thanh toán (Sau VAT )",formatter.format( ( totalTienTruocVAT-totalTienCK)*((1+vat/100))  ) };
			for (int j = 0; j < arr.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr[j], font4));
				if (j == 0)
				{
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					cells.setColspan(3);
				} else if(j==1)
				{
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					cells.setColspan(5);
				}else
				{
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(0.1f * CONVERT);

				sanpham.addCell(cells);
			}
			
			

			document.add(sanpham);

			PdfPTable tableHead = new PdfPTable(3);
			tableHead.setWidthPercentage(50);
			tableHead.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableHead.setSpacingBefore(3);
			tableHead.setSpacingAfter(5);
			float[] with = { 35.0f, 40.0f, 10.0f }; // set chieu rong cac columns
			tableHead.setWidths(with);

			DecimalFormat df = new DecimalFormat("#,###,##0.00000");
			double thung_total = Math.round(10000 * totalTheTich) / 10000.0;

			PdfPCell cell1 = new PdfPCell(new Paragraph("Tổng thể tích: ", new Font(bf, 8, Font.NORMAL)));
			PdfPCell cell2 = new PdfPCell(new Paragraph(df.format(thung_total), font2));
			PdfPCell cell3 = new PdfPCell(new Paragraph("M3", font2));

			cell1.setBorder(0);
			cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell2.setBorder(0);
			cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell3.setBorder(0);
			cell3.setHorizontalAlignment(Element.ALIGN_LEFT);

			tableHead.addCell(cell1);
			tableHead.addCell(cell2);
			tableHead.addCell(cell3);

			cell1 = new PdfPCell(new Paragraph("Tổng khối lượng: ", new Font(bf, 8, Font.NORMAL)));
			cell2 = new PdfPCell(new Paragraph(Double.toString(totalTrongLuong), font2));
			cell3 = new PdfPCell(new Paragraph("Gram", font2));

			cell1.setBorder(0);
			cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell2.setBorder(0);
			cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell3.setBorder(0);
			cell3.setHorizontalAlignment(Element.ALIGN_LEFT);

			tableHead.addCell(cell1);
			tableHead.addCell(cell2);
			tableHead.addCell(cell3);

			document.add(tableHead);

			// Table Footer
			PdfPTable tableFooter = new PdfPTable(5);
			tableFooter.setWidthPercentage(90);
			tableFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableFooter.setWidths(new float[]
			{ 30.0f, 30.0f, 30.0f, 30.0f, 30.0f });

			PdfPCell cell11 = new PdfPCell(new Paragraph("Người lập phiếu", new Font(bf, 7, Font.BOLD)));
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell12 = new PdfPCell(new Paragraph("Thủ kho", new Font(bf, 7, Font.BOLD)));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell13 = new PdfPCell(new Paragraph("Người duyệt", new Font(bf, 7, Font.BOLD)));
			cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell14 = new PdfPCell(new Paragraph("Người giao", new Font(bf, 7, Font.BOLD)));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell15 = new PdfPCell(new Paragraph("Người nhận", new Font(bf, 7, Font.BOLD)));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);

			cell11.setBorder(0);
			cell12.setBorder(0);
			cell13.setBorder(0);
			cell14.setBorder(0);
			cell15.setBorder(0);

			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			tableFooter.addCell(cell13);
			tableFooter.addCell(cell14);
			tableFooter.addCell(cell15);

			cell11 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 7, Font.NORMAL)));
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell12 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 7, Font.NORMAL)));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell13 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 7, Font.NORMAL)));
			cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell14 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 7, Font.NORMAL)));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell15 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 7, Font.NORMAL)));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);

			cell11.setBorder(0);
			cell12.setBorder(0);
			cell13.setBorder(0);
			cell14.setBorder(0);
			cell15.setBorder(0);

			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			tableFooter.addCell(cell13);
			tableFooter.addCell(cell14);
			tableFooter.addCell(cell15);

			for (i = 0; i < 15; i++)
			{
				cell11 = new PdfPCell(new Paragraph("", new Font(bf, 7, Font.NORMAL)));
				cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell12 = new PdfPCell(new Paragraph("", new Font(bf, 7, Font.NORMAL)));
				cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell13 = new PdfPCell(new Paragraph("", new Font(bf, 7, Font.NORMAL)));
				cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell14 = new PdfPCell(new Paragraph("", new Font(bf, 7, Font.NORMAL)));
				cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell15 = new PdfPCell(new Paragraph("", new Font(bf, 7, Font.NORMAL)));
				cell14.setHorizontalAlignment(Element.ALIGN_CENTER);

				cell11.setBorder(0);
				cell12.setBorder(0);
				cell13.setBorder(0);
				cell14.setBorder(0);
				cell15.setBorder(0);

				tableFooter.addCell(cell11);
				tableFooter.addCell(cell12);
				tableFooter.addCell(cell13);
				tableFooter.addCell(cell14);
				tableFooter.addCell(cell15);

			}

			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			tableFooter.addCell(cell13);
			tableFooter.addCell(cell14);
			tableFooter.addCell(cell15);
			
			
			
			document.add(tableFooter);
			document.close();
		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	  
  }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doGet(request, response);
	}

	private void CreatePxk(Document document, ServletOutputStream outstream, IErpXuatkho pxkBean) throws IOException
	{
		try
		{
			
		//LAY THONG TIN DON HANG
			dbutils db = new dbutils();
			String sql = "select   b.DIACHI as dcKho,b.LienHe as ttLienHe,b.congty as ctyTen , a.NgayYeuCau, b.MA as maKHO, b.TEN as tenKHO, c.TEN as nppTEN, c.DIACHI as diaCHI, c.DIENTHOAI " +
					 "from ERP_YCXUATKHO a inner join ERP_KHOTT b on a.KHO_FK = b.PK_SEQ " +
					 "		inner join NHAPHANPHOI c on a.NPP_FK = c.PK_SEQ  " +
					 "where a.PK_SEQ = '" + pxkBean.getId() + "' ";
					
					ResultSet rsINFO = db.get(sql);
					String ngayYC = "";
					String khTEN = "";
					String khDIACHI = "";
					String khDIENTHOAI = "";
					String khoTEN = "";
					String ctyTen="";
					String dcKho="";
					String ttLienHe="";
				if(rsINFO.next())
				{
					ctyTen=rsINFO.getString("ctyTen");
					dcKho = rsINFO.getString("dcKho");
					ttLienHe =rsINFO.getString("ttLienHe");
					
					ngayYC = rsINFO.getString("NgayYeuCau");
					khTEN = rsINFO.getString("nppTEN");
					khDIACHI = rsINFO.getString("diaCHI");
					khDIENTHOAI = rsINFO.getString("DIENTHOAI");
					khoTEN = rsINFO.getString("tenKHO");
					rsINFO.close();
				}
			NumberFormat formatter = new DecimalFormat("#,###,###.##");
			PdfWriter.getInstance(document, outstream);
			document.open();

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			
			PdfPTable tableheader=new PdfPTable(2);
			tableheader.setWidthPercentage(100);
			tableheader.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withsheader = {100.0f, 350.0f};
			tableheader.setWidths(withsheader); 
			
			Image hinhanh = Image.getInstance( getServletContext().getInitParameter("path")+"/logo.gif");	
			hinhanh.setAlignment(Element.ALIGN_CENTER);
			hinhanh.setWidthPercentage(60.0f);
			hinhanh.scalePercent(70);
			
			PdfPCell cellslogo = new PdfPCell(hinhanh);
			cellslogo.setPadding(2);
			cellslogo.setBorder(0);
			tableheader.addCell(cellslogo);
			
			PdfPCell celltieude = new PdfPCell();
			
			
			Paragraph pxk = new Paragraph(ctyTen, new Font(bf, 8, Font.BOLD));
			pxk.setSpacingAfter(2);
			pxk.setAlignment(Element.ALIGN_LEFT);
			celltieude.addElement(pxk);

			pxk = new Paragraph(dcKho, new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(2);
			pxk.setAlignment(Element.ALIGN_LEFT);
			celltieude.addElement(pxk);
			
			pxk = new Paragraph("Văn phòng: 319/D4 Lý Thường Kiệt, P15, Q11, Tp Hồ Chí Minh", new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(2);
			pxk.setAlignment(Element.ALIGN_LEFT);
			celltieude.addElement(pxk);
			
			pxk = new Paragraph(ttLienHe, new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(2);
			pxk.setAlignment(Element.ALIGN_LEFT);
			//document.add(pxk);
			celltieude.addElement(pxk);
			
			pxk = new Paragraph("Tài khoản số: 060001323663 Tại NH Sacombank-PGD Lý Thường Kiệt, TP Hồ CHÍ MINH", new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(2);
			pxk.setAlignment(Element.ALIGN_LEFT);
			//document.add(pxk);
			celltieude.setBorder(0);
			celltieude.addElement(pxk);
			
			tableheader.addCell(celltieude);
			
			document.add(tableheader);	
			
			
			
			pxk = new Paragraph("PHIẾU GIAO HÀNG", font);
			pxk.setSpacingAfter(3);
			pxk.setSpacingBefore(10);
			pxk.setAlignment(Element.ALIGN_CENTER);
			document.add(pxk);

			pxk = new Paragraph("Số: " + pxkBean.getId(), new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(5);
			pxk.setAlignment(Element.ALIGN_RIGHT);
			document.add(pxk);
			
			String[] ngayARR = ngayYC.split("-");
			pxk = new Paragraph("Ngày: " + ngayARR[2] + "    Tháng: " + ngayARR[1] + "     Năm: " + ngayARR[0], new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(5);
			pxk.setAlignment(Element.ALIGN_CENTER);
			document.add(pxk);

			pxk = new Paragraph("Khách hàng: " + khTEN, new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(3);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);

			pxk = new Paragraph("Địa chỉ: " + khDIACHI, new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(3);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			pxk = new Paragraph("Số điện thoại : " + khDIENTHOAI, new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(3);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			pxk = new Paragraph("Xuất tại kho: " + khoTEN, new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(3);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);

			// Table Content
			PdfPTable root = new PdfPTable(2);
			root.setKeepTogether(false);
			root.setSplitLate(false);
			root.setWidthPercentage(100);
			root.setHorizontalAlignment(Element.ALIGN_LEFT);
			root.getDefaultCell().setBorder(0);
			float[] cr = { 95.0f, 100.0f };
			root.setWidths(cr);

			PdfPTable table = new PdfPTable(5);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withs = { 7.0f, 22.0f, 40.0f, 13.0f, 13.0f };
			table.setWidths(withs);

			Font font4 = new Font(bf, 8, Font.BOLD);

			PdfPTable sanpham = new PdfPTable(8);
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withsKM = { 10.0f, 15.0f, 35.0f, 15.0f, 15.0f, 10.0f, 10.0f, 30.0f };
			sanpham.setWidths(withsKM);

			String[] th = new String[]{ "STT", "Mã số", "Tên sản phẩm", "ĐVT", "Quy cách", "Thùng", "Lẻ", "Scheme" };
			PdfPCell[] cell = new PdfPCell[8];
			for (int i = 0; i < 8; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], font4));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);

				cell[i].setPadding(4);
				sanpham.addCell(cell[i]);
			}

			PdfPCell cells = new PdfPCell();
			double totalTrongLuong = 0;
			double totalTheTich = 0;
			double totalthung = 0;
			double totalle = 0;
			
			String query = "select c.MA, c.TEN, d.DONVI, ISNULL( ( select SOLUONG1 from QUYCACH where sanpham_fk = c.PK_SEQ and DVDL2_FK = '100018' ), 1 ) as quycach, b.soluongXUAT, b.SCHEME, " +
							"	isnull(c.TRONGLUONG, 0) as TRONGLUONG, isnull(c.THETICH, 0) as THETICH " +
							"from ERP_YCXUATKHO a inner join ERP_YCXUATKHO_SANPHAM b on a.PK_SEQ = b.ycxk_fk " +
							"	inner join SANPHAM c on b.sanpham_fk = c.PK_SEQ " +
							"	left join DONVIDOLUONG d on c.DVDL_FK = d.PK_SEQ " +
							"where a.PK_SEQ = '" + pxkBean.getId() + "' ORDER BY C.TEN ";
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluongXUAT");
				double quyCACH = rsSP.getDouble("quycach");
				
				double thung = Math.round( (soLUONG / quyCACH) - 0.5 );
				double le = Math.round( (soLUONG % quyCACH) - 0.5 );
				
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA"), rsSP.getString("TEN"), rsSP.getString("DONVI"), rsSP.getString("quycach"),
											thung<=0?"":	formatter.format(thung),
											le <= 0?"":	formatter.format(le), rsSP.getString("SCHEME") };

				totalthung += thung;
				totalle += le;
				totalTrongLuong += ( soLUONG * rsSP.getDouble("TRONGLUONG") );
				totalTheTich += ( soLUONG * rsSP.getDouble("THETICH") );

				for (int j = 0; j < 8; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 8, Font.NORMAL)));
					if (j == 2 || j == 7)
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					else
						cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					
					if (j >= 4 && j != 7)
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPadding(2.0f);

					sanpham.addCell(cells);
				}
				
				stt++;
				
			}
			
			String[] arr = new String[] { "", "", "Tổng cộng", "", "", formatter.format(totalthung), formatter.format(totalle), "" };
			for (int j = 0; j < 8; j++)
			{
				cells = new PdfPCell(new Paragraph(arr[j], font4));
				if (j <= 3)
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
				else
				{
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(2.0f);

				sanpham.addCell(cells);
			}

			document.add(sanpham);

			PdfPTable tableHead = new PdfPTable(3);
			tableHead.setWidthPercentage(50);
			tableHead.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableHead.setSpacingBefore(3);
			tableHead.setSpacingAfter(5);
			float[] with = { 35.0f, 40.0f, 10.0f }; // set chieu rong cac columns
			tableHead.setWidths(with);

			DecimalFormat df = new DecimalFormat("#,###,##0.00000");
			double thung_total = Math.round(10000 * totalTheTich) / 10000.0;

			PdfPCell cell1 = new PdfPCell(new Paragraph("Tổng thể tích: ", new Font(bf, 8, Font.NORMAL)));
			PdfPCell cell2 = new PdfPCell(new Paragraph(df.format(thung_total), font2));
			PdfPCell cell3 = new PdfPCell(new Paragraph("M3", font2));

			cell1.setBorder(0);
			cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell2.setBorder(0);
			cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell3.setBorder(0);
			cell3.setHorizontalAlignment(Element.ALIGN_LEFT);

			tableHead.addCell(cell1);
			tableHead.addCell(cell2);
			tableHead.addCell(cell3);

			cell1 = new PdfPCell(new Paragraph("Tổng khối lượng: ", new Font(bf, 8, Font.NORMAL)));
			cell2 = new PdfPCell(new Paragraph(Double.toString(totalTrongLuong), font2));
			cell3 = new PdfPCell(new Paragraph("Gram", font2));

			cell1.setBorder(0);
			cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell2.setBorder(0);
			cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell3.setBorder(0);
			cell3.setHorizontalAlignment(Element.ALIGN_LEFT);

			tableHead.addCell(cell1);
			tableHead.addCell(cell2);
			tableHead.addCell(cell3);

			document.add(tableHead);

			// Table Footer
			PdfPTable tableFooter = new PdfPTable(5);
			tableFooter.setWidthPercentage(90);
			tableFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableFooter.setWidths(new float[]
			{ 30.0f, 30.0f, 30.0f, 30.0f, 30.0f });

			PdfPCell cell11 = new PdfPCell(new Paragraph("Người lập phiếu", new Font(bf, 7, Font.BOLD)));
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell12 = new PdfPCell(new Paragraph("Thủ kho", new Font(bf, 7, Font.BOLD)));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell13 = new PdfPCell(new Paragraph("Người duyệt", new Font(bf, 7, Font.BOLD)));
			cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell14 = new PdfPCell(new Paragraph("Người giao", new Font(bf, 7, Font.BOLD)));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell15 = new PdfPCell(new Paragraph("Người nhận", new Font(bf, 7, Font.BOLD)));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);

			cell11.setBorder(0);
			cell12.setBorder(0);
			cell13.setBorder(0);
			cell14.setBorder(0);
			cell15.setBorder(0);

			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			tableFooter.addCell(cell13);
			tableFooter.addCell(cell14);
			tableFooter.addCell(cell15);

			cell11 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 7, Font.NORMAL)));
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell12 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 7, Font.NORMAL)));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell13 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 7, Font.NORMAL)));
			cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell14 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 7, Font.NORMAL)));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell15 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 7, Font.NORMAL)));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);

			cell11.setBorder(0);
			cell12.setBorder(0);
			cell13.setBorder(0);
			cell14.setBorder(0);
			cell15.setBorder(0);

			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			tableFooter.addCell(cell13);
			tableFooter.addCell(cell14);
			tableFooter.addCell(cell15);

			for (int i = 0; i < 15; i++)
			{
				cell11 = new PdfPCell(new Paragraph("", new Font(bf, 7, Font.NORMAL)));
				cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell12 = new PdfPCell(new Paragraph("", new Font(bf, 7, Font.NORMAL)));
				cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell13 = new PdfPCell(new Paragraph("", new Font(bf, 7, Font.NORMAL)));
				cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell14 = new PdfPCell(new Paragraph("", new Font(bf, 7, Font.NORMAL)));
				cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell15 = new PdfPCell(new Paragraph("", new Font(bf, 7, Font.NORMAL)));
				cell14.setHorizontalAlignment(Element.ALIGN_CENTER);

				cell11.setBorder(0);
				cell12.setBorder(0);
				cell13.setBorder(0);
				cell14.setBorder(0);
				cell15.setBorder(0);

				tableFooter.addCell(cell11);
				tableFooter.addCell(cell12);
				tableFooter.addCell(cell13);
				tableFooter.addCell(cell14);
				tableFooter.addCell(cell15);

			}

			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			tableFooter.addCell(cell13);
			tableFooter.addCell(cell14);
			tableFooter.addCell(cell15);
			
			document.add(tableFooter);
			
			
			if(document != null)
				document.close();
		} 
		catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void CreatePhieuxuatkho_DinhKem(Document document, ServletOutputStream outstream, IErpXuatkho pxkBean) throws IOException
	{
		try
		{
		//LAY THONG TIN DON HANG
			dbutils db = new dbutils();
			String sql = "select  a.GhiChu, b.DIACHI as dcKho,b.LienHe as ttLienHe,b.congty as ctyTen , a.NgayYeuCau, b.MA as maKHO, b.TEN as tenKHO, c.TEN as nppTEN, c.DIACHI as diaCHI, c.DIENTHOAI " +
					 "from ERP_YCXUATKHO a inner join ERP_KHOTT b on a.KHO_FK = b.PK_SEQ " +
					 "		inner join NHAPHANPHOI c on a.NPP_FK = c.PK_SEQ  " +
					 "where a.PK_SEQ = '" + pxkBean.getId() + "' ";
					
					ResultSet rsINFO = db.get(sql);
					String ngayYC = "";
					String khTEN = "";
					String khDIACHI = "";
					String khDIENTHOAI = "";
					String khoTEN = "";
					String ctyTen="";
					String dcKho="";
					String ttLienHe="";
					String ghiChu="";
				if(rsINFO.next())
				{
					ctyTen=rsINFO.getString("ctyTen");
					dcKho = rsINFO.getString("dcKho");
					ttLienHe =rsINFO.getString("ttLienHe");
					
					ngayYC = rsINFO.getString("NgayYeuCau");
					khTEN = rsINFO.getString("nppTEN");
					khDIACHI = rsINFO.getString("diaCHI");
					khDIENTHOAI = rsINFO.getString("DIENTHOAI");
					khoTEN = rsINFO.getString("tenKHO");
					ghiChu = rsINFO.getString("ghiChu")==null?"": rsINFO.getString("ghiChu");
					rsINFO.close();
				}
			
			NumberFormat formatter = new DecimalFormat("#,###,###.##");
			PdfWriter.getInstance(document, outstream);
			document.open();


			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			
			PdfPTable tableheader=new PdfPTable(2);
			tableheader.setWidthPercentage(100);
			tableheader.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withsheader = {100.0f, 350.0f};
			tableheader.setWidths(withsheader); 
			
			Image hinhanh = Image.getInstance( getServletContext().getInitParameter("path")+"/logo.gif");	
			hinhanh.setAlignment(Element.ALIGN_CENTER);
			hinhanh.setWidthPercentage(60.0f);
			hinhanh.scalePercent(70);
			
			PdfPCell cellslogo = new PdfPCell(hinhanh);
			cellslogo.setPadding(2);
			cellslogo.setBorder(0);
			tableheader.addCell(cellslogo);
			
			PdfPCell celltieude = new PdfPCell();
			
			
			Paragraph pxk = new Paragraph(ctyTen, new Font(bf, 8, Font.BOLD));
			pxk.setSpacingAfter(2);
			pxk.setAlignment(Element.ALIGN_LEFT);
			celltieude.addElement(pxk);

			pxk = new Paragraph(dcKho, new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(2);
			pxk.setAlignment(Element.ALIGN_LEFT);
			celltieude.addElement(pxk);
			
			pxk = new Paragraph("Văn phòng: 319/D4 Lý Thường Kiệt, P15, Q11, Tp Hồ Chí Minh", new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(2);
			pxk.setAlignment(Element.ALIGN_LEFT);
			celltieude.addElement(pxk);
			
			pxk = new Paragraph(ttLienHe, new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(2);
			pxk.setAlignment(Element.ALIGN_LEFT);
			//document.add(pxk);
			celltieude.addElement(pxk);
			
			pxk = new Paragraph("Tài khoản số: 060001323663 Tại NH Sacombank-PGD Lý Thường Kiệt, TP Hồ CHÍ MINH", new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(2);
			pxk.setAlignment(Element.ALIGN_LEFT);
			//document.add(pxk);
			celltieude.setBorder(0);
			celltieude.addElement(pxk);
			
			tableheader.addCell(celltieude);
			
			document.add(tableheader);	
			
			
			
			pxk = new Paragraph("PHIẾU GIAO HÀNG CHI TIẾT", font);
			pxk.setSpacingAfter(3);
			pxk.setSpacingBefore(10);
			pxk.setAlignment(Element.ALIGN_CENTER);
			document.add(pxk);

			pxk = new Paragraph("Số: " + pxkBean.getId(), new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(5);
			pxk.setAlignment(Element.ALIGN_RIGHT);
			document.add(pxk);
			
			String[] ngayARR = ngayYC.split("-");
			pxk = new Paragraph("Ngày: " + ngayARR[2] + "    Tháng: " + ngayARR[1] + "     Năm: " + ngayARR[0], new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(5);
			pxk.setAlignment(Element.ALIGN_CENTER);
			document.add(pxk);

			pxk = new Paragraph("Khách hàng: " + khTEN, new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(3);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);

			pxk = new Paragraph("Địa chỉ: " + khDIACHI, new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(3);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			pxk = new Paragraph("Số điện thoại : " + khDIENTHOAI, new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(3);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			pxk = new Paragraph("Xuất tại kho: " + khoTEN, new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(3);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			
			pxk = new Paragraph("Ghi chú: " + ghiChu, new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(3);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			

			// Table Content
			PdfPTable root = new PdfPTable(2);
			root.setKeepTogether(false);
			root.setSplitLate(false);
			root.setWidthPercentage(100);
			root.setHorizontalAlignment(Element.ALIGN_LEFT);
			root.getDefaultCell().setBorder(0);
			float[] cr = { 95.0f, 100.0f };
			root.setWidths(cr);

			PdfPTable table = new PdfPTable(5);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withs = { 7.0f, 22.0f, 40.0f, 13.0f, 13.0f };
			table.setWidths(withs);

			Font font4 = new Font(bf, 8, Font.BOLD);

			PdfPTable sanpham = new PdfPTable(11);
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withsKM = { 7.0f, 10.0f, 20.0f, 10.0f, 12.0f, 15.0f, 15.0f, 12.0f, 10.0f, 10.0f, 20.0f };
			sanpham.setWidths(withsKM);

			String[] th = new String[]{ "STT", "Mã số", "Tên sản phẩm", "ĐVT", "Số lô", "Ngày sản xuất", "Ngày hết hạn", "Quy cách", "Thùng", "Lẻ", "Scheme" };
			PdfPCell[] cell = new PdfPCell[11];
			for (int i = 0; i < 11; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], font4));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);

				cell[i].setPadding(4);
				sanpham.addCell(cell[i]);
			}

			PdfPCell cells = new PdfPCell();
			double totalTrongLuong = 0;
			double totalTheTich = 0;
			double totalthung = 0;
			double totalle = 0;
			
			String query = "select c.MA, c.TEN, d.DONVI, b.solo, ISNULL( ( select SOLUONG1 from QUYCACH where sanpham_fk = c.PK_SEQ and DVDL2_FK = '100018' ), 1 ) as quycach, b.soluong, b.SCHEME, " +
							"	isnull(c.TRONGLUONG, 0) as TRONGLUONG, isnull(c.THETICH, 0) as THETICH, " +
							"	( select ngayhethan from ERP_KHOTT_SP_CHITIET where khott_fk = a.kho_fk  and sanpham_fk = c.pk_seq and solo = b.solo ) as ngayhethan, " +
							"	( select ngaysanxuat from ERP_KHOTT_SP_CHITIET where khott_fk = a.kho_fk  and sanpham_fk = c.pk_seq and solo = b.solo ) as ngaysanxuat	" +
							"from ERP_YCXUATKHO a inner join ERP_YCXUATKHO_SANPHAM_CHITIET b on a.PK_SEQ = b.ycxk_fk " +
							"	inner join SANPHAM c on b.sanpham_fk = c.PK_SEQ " +
							"	left join DONVIDOLUONG d on c.DVDL_FK = d.PK_SEQ " +
							"where a.PK_SEQ = '" + pxkBean.getId() + "' and b.soluong > 0 ";
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double quyCACH = rsSP.getDouble("quycach");
				
				double thung = Math.round( (soLUONG / quyCACH) - 0.5 );
				double le = Math.round( (soLUONG % quyCACH) - 0.5 );
				
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA"), rsSP.getString("TEN"), rsSP.getString("DONVI"), rsSP.getString("SOLO"), rsSP.getString("ngaysanxuat"), rsSP.getString("ngayhethan"), rsSP.getString("quycach"),
												formatter.format(thung),
												formatter.format(le), rsSP.getString("SCHEME") };

				totalthung += thung;
				totalle += le;
				totalTrongLuong += ( soLUONG * rsSP.getDouble("TRONGLUONG") );
				totalTheTich += ( soLUONG * rsSP.getDouble("THETICH") );

				for (int j = 0; j < 11; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 8, Font.NORMAL)));
					if (j == 2 || j == 10)
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					else
						cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					
					if (j >= 8 && j != 10)
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPadding(2.0f);

					sanpham.addCell(cells);
				}
				
				stt++;
				
			}
			
			String[] arr = new String[] { "", "", "Tổng cộng", "", "", "", "", "", formatter.format(totalthung), formatter.format(totalle), "" };
			for (int j = 0; j < 11; j++)
			{
				cells = new PdfPCell(new Paragraph(arr[j], font4));
				if (j <= 3)
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
				else
				{
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(2.0f);

				sanpham.addCell(cells);
			}

			document.add(sanpham);

			PdfPTable tableHead = new PdfPTable(3);
			tableHead.setWidthPercentage(50);
			tableHead.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableHead.setSpacingBefore(3);
			tableHead.setSpacingAfter(5);
			float[] with = { 35.0f, 40.0f, 10.0f }; // set chieu rong cac columns
			tableHead.setWidths(with);

			DecimalFormat df = new DecimalFormat("#,###,##0.00000");
			double thung_total = Math.round(10000 * totalTheTich) / 10000.0;

			PdfPCell cell1 = new PdfPCell(new Paragraph("Tổng thể tích: ", new Font(bf, 8, Font.NORMAL)));
			PdfPCell cell2 = new PdfPCell(new Paragraph(df.format(thung_total), font2));
			PdfPCell cell3 = new PdfPCell(new Paragraph("M3", font2));

			cell1.setBorder(0);
			cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell2.setBorder(0);
			cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell3.setBorder(0);
			cell3.setHorizontalAlignment(Element.ALIGN_LEFT);

			tableHead.addCell(cell1);
			tableHead.addCell(cell2);
			tableHead.addCell(cell3);

			cell1 = new PdfPCell(new Paragraph("Tổng khối lượng: ", new Font(bf, 8, Font.NORMAL)));
			cell2 = new PdfPCell(new Paragraph(Double.toString(totalTrongLuong), font2));
			cell3 = new PdfPCell(new Paragraph("Gram", font2));

			cell1.setBorder(0);
			cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell2.setBorder(0);
			cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell3.setBorder(0);
			cell3.setHorizontalAlignment(Element.ALIGN_LEFT);

			tableHead.addCell(cell1);
			tableHead.addCell(cell2);
			tableHead.addCell(cell3);

			document.add(tableHead);

			// Table Footer
			PdfPTable tableFooter = new PdfPTable(5);
			tableFooter.setWidthPercentage(90);
			tableFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableFooter.setWidths(new float[]
			{ 30.0f, 30.0f, 30.0f, 30.0f, 30.0f });

			PdfPCell cell11 = new PdfPCell(new Paragraph("Người lập phiếu", new Font(bf, 7, Font.BOLD)));
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell12 = new PdfPCell(new Paragraph("Thủ kho", new Font(bf, 7, Font.BOLD)));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell13 = new PdfPCell(new Paragraph("Người duyệt", new Font(bf, 7, Font.BOLD)));
			cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell14 = new PdfPCell(new Paragraph("Người giao", new Font(bf, 7, Font.BOLD)));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell15 = new PdfPCell(new Paragraph("Người nhận", new Font(bf, 7, Font.BOLD)));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);

			cell11.setBorder(0);
			cell12.setBorder(0);
			cell13.setBorder(0);
			cell14.setBorder(0);
			cell15.setBorder(0);

			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			tableFooter.addCell(cell13);
			tableFooter.addCell(cell14);
			tableFooter.addCell(cell15);

			cell11 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 7, Font.NORMAL)));
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell12 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 7, Font.NORMAL)));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell13 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 7, Font.NORMAL)));
			cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell14 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 7, Font.NORMAL)));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell15 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 7, Font.NORMAL)));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);

			cell11.setBorder(0);
			cell12.setBorder(0);
			cell13.setBorder(0);
			cell14.setBorder(0);
			cell15.setBorder(0);

			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			tableFooter.addCell(cell13);
			tableFooter.addCell(cell14);
			tableFooter.addCell(cell15);

			for (int i = 0; i < 15; i++)
			{
				cell11 = new PdfPCell(new Paragraph("", new Font(bf, 7, Font.NORMAL)));
				cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell12 = new PdfPCell(new Paragraph("", new Font(bf, 7, Font.NORMAL)));
				cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell13 = new PdfPCell(new Paragraph("", new Font(bf, 7, Font.NORMAL)));
				cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell14 = new PdfPCell(new Paragraph("", new Font(bf, 7, Font.NORMAL)));
				cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell15 = new PdfPCell(new Paragraph("", new Font(bf, 7, Font.NORMAL)));
				cell14.setHorizontalAlignment(Element.ALIGN_CENTER);

				cell11.setBorder(0);
				cell12.setBorder(0);
				cell13.setBorder(0);
				cell14.setBorder(0);
				cell15.setBorder(0);

				tableFooter.addCell(cell11);
				tableFooter.addCell(cell12);
				tableFooter.addCell(cell13);
				tableFooter.addCell(cell14);
				tableFooter.addCell(cell15);

			}

			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			tableFooter.addCell(cell13);
			tableFooter.addCell(cell14);
			tableFooter.addCell(cell15);
			
			
			
			document.add(tableFooter);
			document.close();
		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	

	private void CreatePhieulayhang(Document document, ServletOutputStream outstream, IErpXuatkho pxkBean) throws IOException
	{
		try
		{
		//LAY THONG TIN DON HANG
			dbutils db = new dbutils();
			String sql = "select   b.DIACHI as dcKho,b.LienHe as ttLienHe,b.congty as ctyTen , a.NgayYeuCau, b.MA as maKHO, b.TEN as tenKHO, c.TEN as nppTEN, c.DIACHI as diaCHI, c.DIENTHOAI " +
					 "from ERP_YCXUATKHO a inner join ERP_KHOTT b on a.KHO_FK = b.PK_SEQ " +
					 "		inner join NHAPHANPHOI c on a.NPP_FK = c.PK_SEQ  " +
					 "where a.PK_SEQ = '" + pxkBean.getId() + "' ";
					
					ResultSet rsINFO = db.get(sql);
					String ngayYC = "";
					String khTEN = "";
					String khDIACHI = "";
					String khDIENTHOAI = "";
					String khoTEN = "";
					String ctyTen="";
					String dcKho="";
					String ttLienHe="";
				if(rsINFO.next())
				{
					ctyTen=rsINFO.getString("ctyTen");
					dcKho = rsINFO.getString("dcKho");
					ttLienHe =rsINFO.getString("ttLienHe");
					
					ngayYC = rsINFO.getString("NgayYeuCau");
					khTEN = rsINFO.getString("nppTEN");
					khDIACHI = rsINFO.getString("diaCHI");
					khDIENTHOAI = rsINFO.getString("DIENTHOAI");
					khoTEN = rsINFO.getString("tenKHO");
					rsINFO.close();
				}
			
			NumberFormat formatter = new DecimalFormat("#,###,###.##");
			PdfWriter.getInstance(document, outstream);
			document.open();


			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			
			PdfPTable tableheader=new PdfPTable(2);
			tableheader.setWidthPercentage(100);
			tableheader.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withsheader = {100.0f, 350.0f};
			tableheader.setWidths(withsheader); 
			
			Image hinhanh = Image.getInstance( getServletContext().getInitParameter("path")+"/logo.gif");	
			hinhanh.setAlignment(Element.ALIGN_CENTER);
			hinhanh.setWidthPercentage(60.0f);
			hinhanh.scalePercent(70);
			
			PdfPCell cellslogo = new PdfPCell(hinhanh);
			cellslogo.setPadding(2);
			cellslogo.setBorder(0);
			tableheader.addCell(cellslogo);
			
			PdfPCell celltieude = new PdfPCell();
			
			
			Paragraph pxk = new Paragraph(ctyTen, new Font(bf, 8, Font.BOLD));
			pxk.setSpacingAfter(2);
			pxk.setAlignment(Element.ALIGN_LEFT);
			celltieude.addElement(pxk);

			pxk = new Paragraph(dcKho, new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(2);
			pxk.setAlignment(Element.ALIGN_LEFT);
			celltieude.addElement(pxk);
			
			pxk = new Paragraph("Văn phòng: 319/D4 Lý Thường Kiệt, P15, Q11, Tp Hồ Chí Minh", new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(2);
			pxk.setAlignment(Element.ALIGN_LEFT);
			celltieude.addElement(pxk);
			
			pxk = new Paragraph(ttLienHe, new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(2);
			pxk.setAlignment(Element.ALIGN_LEFT);
			celltieude.addElement(pxk);
			
			pxk = new Paragraph("Tài khoản số: 060001323663 Tại NH Sacombank-PGD Lý Thường Kiệt, TP Hồ CHÍ MINH", new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(2);
			pxk.setAlignment(Element.ALIGN_LEFT);
			celltieude.setBorder(0);
			celltieude.addElement(pxk);
			
			tableheader.addCell(celltieude);
			
			document.add(tableheader);	
			
			
			
			pxk = new Paragraph("PHIẾU LẤY HÀNG", font);
			pxk.setSpacingAfter(3);
			pxk.setSpacingBefore(10);
			pxk.setAlignment(Element.ALIGN_CENTER);
			document.add(pxk);

			pxk = new Paragraph("Số: " + pxkBean.getId(), new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(5);
			pxk.setAlignment(Element.ALIGN_RIGHT);
			document.add(pxk);
			
			
			String[] ngayARR = ngayYC.split("-");
			pxk = new Paragraph("Ngày: " + ngayARR[2] + "    Tháng: " + ngayARR[1] + "     Năm: " + ngayARR[0], new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(5);
			pxk.setAlignment(Element.ALIGN_CENTER);
			document.add(pxk);

			pxk = new Paragraph("Khách hàng: " + khTEN, new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(3);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);

			pxk = new Paragraph("Địa chỉ: " + khDIACHI, new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(3);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			pxk = new Paragraph("Số điện thoại : " + khDIENTHOAI, new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(3);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			pxk = new Paragraph("Xuất tại kho: " + khoTEN, new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(3);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);

			// Table Content
			PdfPTable root = new PdfPTable(2);
			root.setKeepTogether(false);
			root.setSplitLate(false);
			root.setWidthPercentage(100);
			root.setHorizontalAlignment(Element.ALIGN_LEFT);
			root.getDefaultCell().setBorder(0);
			float[] cr = { 95.0f, 100.0f };
			root.setWidths(cr);

			PdfPTable table = new PdfPTable(5);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withs = { 7.0f, 22.0f, 40.0f, 13.0f, 13.0f };
			table.setWidths(withs);

			Font font4 = new Font(bf, 8, Font.BOLD);

			PdfPTable sanpham = new PdfPTable(9);
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withsKM = { 7.0f, 10.0f, 40.0f, 12.0f, 15.0f, 12.0f, 10.0f, 10.0f, 25.0f };
			sanpham.setWidths(withsKM);

			String[] th = new String[]{ "STT", "Mã số", "Tên sản phẩm", "ĐVT", "Số lô", "Quy cách", "Thùng", "Lẻ", "Scheme" };
			PdfPCell[] cell = new PdfPCell[9];
			for (int i = 0; i < 9; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], font4));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);

				cell[i].setPadding(4);
				sanpham.addCell(cell[i]);
			}

			PdfPCell cells = new PdfPCell();
			double totalTrongLuong = 0;
			double totalTheTich = 0;
			double totalthung = 0;
			double totalle = 0;
			
			String query = "select c.MA, c.TEN, d.DONVI, b.solo, ISNULL( ( select SOLUONG1 from QUYCACH where sanpham_fk = c.PK_SEQ and DVDL2_FK = '100018' ), 1 ) as quycach, b.soluong, b.SCHEME, " +
							"	isnull(c.TRONGLUONG, 0) as TRONGLUONG, isnull(c.THETICH, 0) as THETICH " +
							"from ERP_YCXUATKHO a inner join ERP_YCXUATKHO_SANPHAM_CHITIET b on a.PK_SEQ = b.ycxk_fk " +
							"	inner join SANPHAM c on b.sanpham_fk = c.PK_SEQ " +
							"	left join DONVIDOLUONG d on c.DVDL_FK = d.PK_SEQ " +
							"where a.PK_SEQ = '" + pxkBean.getId() + "'";
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double quyCACH = rsSP.getDouble("quycach");
				
				double thung = Math.round( (soLUONG / quyCACH) - 0.5 );
				double le = Math.round( (soLUONG % quyCACH) - 0.5 );
				
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA"), rsSP.getString("TEN"), rsSP.getString("DONVI"), rsSP.getString("SOLO"), rsSP.getString("quycach"),
												formatter.format(thung),
												formatter.format(le), rsSP.getString("SCHEME") };

				totalthung += thung;
				totalle += le;
				totalTrongLuong += ( soLUONG * rsSP.getDouble("TRONGLUONG")*quyCACH );
				totalTheTich += ( soLUONG * rsSP.getDouble("THETICH") *quyCACH );

				for (int j = 0; j < 9; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 8, Font.NORMAL)));
					if (j == 2 || j == 8)
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					else
						cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					
					if (j >= 6 && j != 8)
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPadding(2.0f);

					sanpham.addCell(cells);
				}
				
				stt++;
				
			}
			
			String[] arr = new String[] { "", "", "Tổng cộng", "", "", "", formatter.format(totalthung), formatter.format(totalle), "" };
			for (int j = 0; j < 9; j++)
			{
				cells = new PdfPCell(new Paragraph(arr[j], font4));
				if (j <= 3)
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
				else
				{
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(2.0f);

				sanpham.addCell(cells);
			}

			document.add(sanpham);

			PdfPTable tableHead = new PdfPTable(3);
			tableHead.setWidthPercentage(50);
			tableHead.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableHead.setSpacingBefore(3);
			tableHead.setSpacingAfter(5);
			float[] with = { 35.0f, 40.0f, 10.0f }; // set chieu rong cac columns
			tableHead.setWidths(with);

			DecimalFormat df = new DecimalFormat("#,###,##0.00000");
			double thung_total = Math.round(10000 * totalTheTich) / 10000.0;

			PdfPCell cell1 = new PdfPCell(new Paragraph("Tổng thể tích: ", new Font(bf, 8, Font.NORMAL)));
			PdfPCell cell2 = new PdfPCell(new Paragraph(df.format(thung_total), font2));
			PdfPCell cell3 = new PdfPCell(new Paragraph("M3", font2));

			cell1.setBorder(0);
			cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell2.setBorder(0);
			cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell3.setBorder(0);
			cell3.setHorizontalAlignment(Element.ALIGN_LEFT);

			tableHead.addCell(cell1);
			tableHead.addCell(cell2);
			tableHead.addCell(cell3);

			cell1 = new PdfPCell(new Paragraph("Tổng khối lượng: ", new Font(bf, 8, Font.NORMAL)));
			cell2 = new PdfPCell(new Paragraph(Double.toString(totalTrongLuong), font2));
			cell3 = new PdfPCell(new Paragraph("Gram", font2));

			cell1.setBorder(0);
			cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell2.setBorder(0);
			cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell3.setBorder(0);
			cell3.setHorizontalAlignment(Element.ALIGN_LEFT);

			tableHead.addCell(cell1);
			tableHead.addCell(cell2);
			tableHead.addCell(cell3);

			document.add(tableHead);

			// Table Footer
			PdfPTable tableFooter = new PdfPTable(5);
			tableFooter.setWidthPercentage(90);
			tableFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableFooter.setWidths(new float[]
			{ 30.0f, 30.0f, 30.0f, 30.0f, 30.0f });

			PdfPCell cell11 = new PdfPCell(new Paragraph("Người lập phiếu", new Font(bf, 7, Font.BOLD)));
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell12 = new PdfPCell(new Paragraph("Thủ kho", new Font(bf, 7, Font.BOLD)));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell13 = new PdfPCell(new Paragraph("Người duyệt", new Font(bf, 7, Font.BOLD)));
			cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell14 = new PdfPCell(new Paragraph("Người giao", new Font(bf, 7, Font.BOLD)));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell15 = new PdfPCell(new Paragraph("Người nhận", new Font(bf, 7, Font.BOLD)));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);

			cell11.setBorder(0);
			cell12.setBorder(0);
			cell13.setBorder(0);
			cell14.setBorder(0);
			cell15.setBorder(0);

			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			tableFooter.addCell(cell13);
			tableFooter.addCell(cell14);
			tableFooter.addCell(cell15);

			cell11 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 7, Font.NORMAL)));
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell12 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 7, Font.NORMAL)));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell13 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 7, Font.NORMAL)));
			cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell14 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 7, Font.NORMAL)));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell15 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 7, Font.NORMAL)));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);

			cell11.setBorder(0);
			cell12.setBorder(0);
			cell13.setBorder(0);
			cell14.setBorder(0);
			cell15.setBorder(0);

			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			tableFooter.addCell(cell13);
			tableFooter.addCell(cell14);
			tableFooter.addCell(cell15);

			for (int i = 0; i < 15; i++)
			{
				cell11 = new PdfPCell(new Paragraph("", new Font(bf, 7, Font.NORMAL)));
				cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell12 = new PdfPCell(new Paragraph("", new Font(bf, 7, Font.NORMAL)));
				cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell13 = new PdfPCell(new Paragraph("", new Font(bf, 7, Font.NORMAL)));
				cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell14 = new PdfPCell(new Paragraph("", new Font(bf, 7, Font.NORMAL)));
				cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell15 = new PdfPCell(new Paragraph("", new Font(bf, 7, Font.NORMAL)));
				cell14.setHorizontalAlignment(Element.ALIGN_CENTER);

				cell11.setBorder(0);
				cell12.setBorder(0);
				cell13.setBorder(0);
				cell14.setBorder(0);
				cell15.setBorder(0);

				tableFooter.addCell(cell11);
				tableFooter.addCell(cell12);
				tableFooter.addCell(cell13);
				tableFooter.addCell(cell14);
				tableFooter.addCell(cell15);

			}

			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			tableFooter.addCell(cell13);
			tableFooter.addCell(cell14);
			tableFooter.addCell(cell15);
			
			
			
			document.add(tableFooter);
			document.close();
		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
