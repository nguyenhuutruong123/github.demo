package geso.dms.center.servlets.dondathang;

import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@WebServlet("/ErpDondathangPdf")
public class ErpDondathangPdf extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErpDondathangPdf()
	{
		super();

	}

	float CONVERT = 28.346457f; // = 1cm

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		
		// kiểm tra quyền xem menu
	    String param="";
	    if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "ErpDondathangNppSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
		
		String userId = (String) session.getAttribute("userId");

		Utility util = new Utility();

		if (userId.length() == 0)
			userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));

		String querystring = request.getQueryString();
		String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("pdf")));
		
		dbutils db = new dbutils();
		// Kiểm tra id có hợp lệ không
	    if(id != null && id.trim().length() > 0 
	    		&& !Utility.KiemTra_PK_SEQ_HopLe(id, "ERP_DONDATHANG", db)){
	    	db.shutDown();
    		return;
    	}

		String excel = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("excel")));

		if(excel==null)
		{
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", " inline; filename=DonHangTraVe.pdf");

			Document document = new Document();
			ServletOutputStream outstream = response.getOutputStream();

			String type = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("type"));
			System.out.println("type:"+type);
			if(type == null)
				type = "";

			this.DonDatHangPdf(document, outstream, id );	
		}else 
		{
			OutputStream out = response.getOutputStream();
			request.setCharacterEncoding("utf-8");
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=DonDatHang_"+id+".xlsm");
			try
			{
				DonDatHangExcel(out,id);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	private void DonDatHangPdf(Document document, ServletOutputStream outstream, String id)
	{
		try
		{	
			dbutils db = new dbutils();
			document.setPageSize(PageSize.A4.rotate());
			PdfWriter.getInstance(document, outstream);
			document.open();
			//lay doi tuong khach hang
			//chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

			Font header = new Font(bf, 14, Font.BOLD);
			Font bold = new Font(bf, 10, Font.BOLD);
			Font normal = new Font(bf, 9, Font.NORMAL);
			Font underline = new Font(bf, 12, Font.UNDERLINE);

			//KHAI BAO 1 BANG CO BAO NHIEU COT

			PdfPTable table = new PdfPTable(2);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withsheader = {50,250.0f};
			table.setWidths(withsheader); 
			Image hinhanh=Image.getInstance( getServletContext().getInitParameter("path")+"/images/logosgp.png");
			hinhanh.scalePercent(50);
			hinhanh.setAlignment(Element.ALIGN_LEFT);
			
			PdfPCell cellslogo = new PdfPCell(hinhanh);
			cellslogo.setPadding(2);
			cellslogo.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			cellslogo.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cellslogo.setBorder(0);
			table.addCell(cellslogo);
			PdfPCell cell = new PdfPCell(new Paragraph("ĐƠN ĐẶT HÀNG ", header));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			cell.setPaddingBottom(10);
			table.addCell(cell);
			document.add(table);				


			float[] withs = {30.0f, 100.0f}; 	
			table = new PdfPTable(withs.length);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidths(withs);

			table.setSpacingAfter(8);

			double congNo=0.0;
			double ckThanhToan=0.0 ;
			double ckDonHang=0.0 ;
			double ckKhuyenMai=0.0 ;
			String sql=
					"	select a.NgayDonHang,a.NgayDeNghi,b.DONVIKINHDOANH as dvkdTEN,  "+
							"		c.DIENGIAI as kbhTEN,d.MA as nppMa,d.DIACHI as nppDiaChi,d.TEN as nppTEN,isnull(d.FAX,'') as nppFAX,isnull(d.DIENTHOAI,'') as nppDienThoai, "+
							"			e.TEN as khoTEN,a.GHICHU,f.TEN as ttTen,a.CongNo, "+
							"	(	 "+
							"		select SUM(chietkhau) from ERP_DONDATHANG_CHIETKHAU  "+
							"		where DIENGIAI=N'Chiết khấu thanh toán' and DONDATHANG_FK=a.PK_SEQ "+
							"	)as ckThanhToan, "+
							"	(	 "+
							"		select SUM(chietkhau) from ERP_DONDATHANG_CHIETKHAU  "+
							"		where DIENGIAI=N'Chiết khấu đặt hàng' and DONDATHANG_FK=a.PK_SEQ "+
							"	)as ckDonHang, "+
							"	(	 "+
							"		select SUM(TONGGIATRI) from ERP_DONDATHANG_CTKM_TRAKM "+
							"		where  DONDATHANGID=a.PK_SEQ and soluong is null  "+ 
							"	)as ckKhuyenMai  "+
							"	from ERP_DONDATHANG a inner join DONVIKINHDOANH b on b.PK_SEQ=a.DVKD_FK "+
							"		INNER JOIN KENHBANHANG c on c.PK_SEQ=a.KBH_FK "+
							"		INNER JOIN NHAPHANPHOI d on d.PK_SEQ=a.NPP_FK "+
							"		INNER JOIN ERP_KHOTT e on e.PK_SEQ=a.Kho_FK "+
							"		LEFT JOIN TINHTHANH f on f.PK_SEQ=d.TINHTHANH_FK "+	
							" where a.pk_Seq=?  ";

			System.out.println("[INIT]"+sql);

			ResultSet rs = db.getByPreparedStatement(sql, new Object[]{id});
			try
			{
				if(rs.next())
				{
					cell = new PdfPCell(new Paragraph("Tên nhà phân phối :", bold));cell.setBorder(0);table.addCell(cell);
					cell = new PdfPCell(new Paragraph(rs.getString("nppTen").toUpperCase(), normal));cell.setBorder(0);table.addCell(cell);

					cell = new PdfPCell(new Paragraph("FAX :", bold));cell.setBorder(0);table.addCell(cell);
					cell = new PdfPCell(new Paragraph(rs.getString("nppFAX").toUpperCase(), normal));cell.setBorder(0);table.addCell(cell);

					cell = new PdfPCell(new Paragraph("Tỉnh/TP :", bold));cell.setBorder(0);table.addCell(cell);
					cell = new PdfPCell(new Paragraph(rs.getString("ttTen").toUpperCase(), normal));cell.setBorder(0);table.addCell(cell);

					cell = new PdfPCell(new Paragraph("Tel/FAX NPP:", bold));cell.setBorder(0);table.addCell(cell);
					cell = new PdfPCell(new Paragraph(rs.getString("nppDienThoai"), normal));cell.setBorder(0);table.addCell(cell);

					cell = new PdfPCell(new Paragraph("Ngày đặt hàng:", bold));cell.setBorder(0);table.addCell(cell);
					cell = new PdfPCell(new Paragraph(rs.getString("NgayDonHang"), normal));cell.setBorder(0);table.addCell(cell);

					cell = new PdfPCell(new Paragraph("Ngày đề nghị giao hàng:", bold));cell.setBorder(0);table.addCell(cell);
					cell = new PdfPCell(new Paragraph(rs.getString("NgayDeNghi"), normal));cell.setBorder(0);table.addCell(cell);

					ckDonHang = rs.getDouble("ckDonHang");
					ckKhuyenMai=rs.getDouble("ckKhuyenMai");
					ckThanhToan=rs.getDouble("ckThanhToan");
					congNo =rs.getDouble("CongNo");
				}
				rs.close();
			}
			catch(Exception er )
			{
				er.printStackTrace();
			}
			document.add(table);

			//Table Content
			float[]	with= {5.0f, 10.0f,10.0f,30.0f, 11.0f, 13.0f, 13.0f,10.0f}; 	
			table = new PdfPTable(with.length);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidths(with);

			String[] th = new String[]{ "STT", "Mã sản phẩm","Số đơn hàng", "Tên sản phẩm","Đơn vị tinh", "Số lượng", "Đơn giá", "Thành tiền"};
			PdfPCell[] cells = new PdfPCell[with.length];
			for(int i = 0; i < with.length ; i++)
			{
				cells[i] = new PdfPCell(new Paragraph(th[i], bold));
				cells[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cells[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells[i].setBackgroundColor(BaseColor.WHITE);	
				table.addCell(cells[i]);			
			}

			String query = 
					"	select b.MA as spMa,a.dondathang_fk,b.TEN as spTen,a.dongia, a.dongiagoc,a.soluongttduyet as soluong,a.thuevat,a.chietkhau,c.DONVI,b.MA_DDT "+
							"	from ERP_DONDATHANG_SANPHAM a inner join SANPHAM b on b.PK_SEQ=a.sanpham_fk  "+
							"		left join DONVIDOLUONG c on c.PK_SEQ=a.dvdl_fk  "+
							"	where a.dondathang_fk=?";

			System.out.println("[SQL]"+query);

			rs = db.getByPreparedStatement(query, new Object[]{id});
			PdfPCell cellSP = null;
			int stt = 1;
			double totalAVAT=0;
			double totalVAT=0;
			double totalCK=0;
			double totalBVAT=0;
			double soluong =0;
			double dongia=0 ;
			double dongiagoc = 0;
			double thuevat=0 ;

			NumberFormat format = new DecimalFormat("#,###,###");
			while(rs.next())
			{
				soluong = rs.getDouble("SOLUONG");
				dongia = rs.getDouble("dongia");
				dongiagoc = rs.getDouble("dongiagoc");
				thuevat = rs.getDouble("thuevat");
				//totalCK += rs.getDouble("chietkhau");
				totalBVAT += soluong*dongia;
				totalAVAT += soluong*dongia;
				cellSP = new PdfPCell(new Paragraph(Integer.toString(stt), normal));
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);		

				cellSP = new PdfPCell(new Paragraph(rs.getString("spMa"), normal));
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);	

				cellSP = new PdfPCell(new Paragraph(rs.getString("dondathang_fk"), normal));
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);	

				cellSP = new PdfPCell(new Paragraph(rs.getString("spTen"), normal));
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);	

				cellSP = new PdfPCell(new Paragraph(rs.getString("donvi"), normal));
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);

				cellSP = new PdfPCell(new Paragraph(format.format(soluong) , normal));
				cellSP.setPadding(3.0f);
				cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cellSP);	

				cellSP = new PdfPCell(new Paragraph(format.format(dongia/1.1) , normal));
				cellSP.setPadding(3.0f);
				cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cellSP);	

				cellSP = new PdfPCell(new Paragraph(format.format(soluong * dongia/1.1) , normal));
				cellSP.setPadding(3.0f);
				cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cellSP);	
				stt++;
			}
			rs.close();
			query=			
					"	select a.DONDATHANGID,isnull(b.MA,'') as spMa,isnull(b.TEN,'') as spTen,a.TONGGIATRI/NULLIF(a.SOLUONG,0) as DonGia,  "+
							"		a.soluong,0 as thuevat,a.chietkhau,ISNULL(c.DONVI,'') as DonVi,a.TONGGIATRI,d.SCHEME,d.DIENGIAI  "+
							"	from ERP_DONDATHANG_CTKM_TRAKM a left join SANPHAM b on b.ma=a.SPMA  "+
							"		left join DONVIDOLUONG c on c.PK_SEQ=b.DVDL_FK  "+
							"		inner join  CTKHUYENMAI d on d.PK_SEQ=a.CTKMID  "+
							"	where a.DONDATHANGID=?";
			System.out.println("[ERP_DONDATHANG_CTKM_TRAKM] " + query);
			rs = db.getByPreparedStatement(query, new Object[]{id});
			while(rs.next())
			{
				soluong = rs.getDouble("SOLUONG");
				dongia = 0;
				thuevat = 0;
				//totalCK += rs.getDouble("chietkhau");
				if(soluong > 0)
				{
					cellSP = new PdfPCell(new Paragraph(Integer.toString(stt), normal));
					cellSP.setPadding(3.0f);
					table.addCell(cellSP);		
	
					cellSP = new PdfPCell(new Paragraph(rs.getString("spMa"), normal));
					cellSP.setPadding(3.0f);
					table.addCell(cellSP);	
	
					cellSP = new PdfPCell(new Paragraph(rs.getString("dondathangid"), normal));
					cellSP.setPadding(3.0f);
					table.addCell(cellSP);	
					
					cellSP = new PdfPCell(new Paragraph(rs.getString("spTen"), normal));
					cellSP.setPadding(3.0f);
					table.addCell(cellSP);	
	
					cellSP = new PdfPCell(new Paragraph(rs.getString("donvi"), normal));
					cellSP.setPadding(3.0f);
					table.addCell(cellSP);
	
					cellSP = new PdfPCell(new Paragraph(format.format(soluong) , normal));
					cellSP.setPadding(3.0f);
					cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cellSP);	
	
					cellSP = new PdfPCell(new Paragraph(format.format(dongia/1.1) , normal));
					cellSP.setPadding(3.0f);
					cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cellSP);	
	
					cellSP = new PdfPCell(new Paragraph(format.format(soluong * dongia/1.1) , normal));
					cellSP.setPadding(3.0f);
					cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cellSP);	
				}
				stt++;	
			}
			rs.close();
			
			totalBVAT = totalBVAT / 1.1;
			totalVAT += ( totalBVAT-totalCK-ckDonHang) * 0.1;
			double totalThanhTien=(totalAVAT)-ckKhuyenMai;
			System.out.println("ck "+totalCK+", ckdh "+ckDonHang+", ckkm "+ckKhuyenMai);
			System.out.println("bvat "+ totalBVAT+", vat "+totalVAT+", avat "+totalAVAT+", tt "+totalThanhTien);

			cellSP = new PdfPCell(new Paragraph("Tổng tiền", bold));
			cellSP.setBorder(0);
			cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellSP.setColspan(7);
			cellSP.setPadding(3.0f);
			table.addCell(cellSP);		

			cellSP = new PdfPCell(new Paragraph(format.format(totalBVAT), normal));
			cellSP.setBorder(0);
			cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellSP.setPadding(3.0f);
			table.addCell(cellSP);


			cellSP = new PdfPCell(new Paragraph("Thuế VAT(10%)", bold));
			cellSP.setBorder(0);
			cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellSP.setColspan(7);
			cellSP.setPadding(3.0f);
			table.addCell(cellSP);		

			cellSP = new PdfPCell(new Paragraph(format.format(totalVAT), normal));
			cellSP.setBorder(0);
			cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellSP.setPadding(3.0f);
			table.addCell(cellSP);


			cellSP = new PdfPCell(new Paragraph("Tổng tiền sau VAT", bold));
			cellSP.setBorder(0);
			cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellSP.setColspan(7);
			cellSP.setPadding(3.0f);
			table.addCell(cellSP);		

			cellSP = new PdfPCell(new Paragraph(format.format(totalAVAT), normal));
			cellSP.setBorder(0);
			cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellSP.setPadding(3.0f);
			table.addCell(cellSP);

			if(ckThanhToan>0)
			{

				cellSP = new PdfPCell(new Paragraph("Chiết khấu thanh toán", bold));cellSP.setBorder(0); cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cellSP.setColspan(7);cellSP.setPadding(3.0f);table.addCell(cellSP);		

				cellSP = new PdfPCell(new Paragraph(format.format(ckThanhToan), normal));
				cellSP.setBorder(0);cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);cellSP.setPadding(3.0f);
				table.addCell(cellSP);
			}

			/*if(ckDonHang>0)*/
			/*{
				cellSP = new PdfPCell(new Paragraph("Chiết khấu đặt hàng", bold));cellSP.setBorder(0); cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cellSP.setColspan(6);cellSP.setPadding(3.0f);table.addCell(cellSP);		

				cellSP = new PdfPCell(new Paragraph(format.format(ckDonHang), normal));
				cellSP.setBorder(0);cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);cellSP.setPadding(3.0f);
				table.addCell(cellSP);
			}*/

			/*if(ckKhuyenMai>0)*/
			{
				cellSP = new PdfPCell(new Paragraph("Chiết khấu khuyến mại", bold));cellSP.setBorder(0); cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cellSP.setColspan(7);cellSP.setPadding(3.0f);table.addCell(cellSP);		

				cellSP = new PdfPCell(new Paragraph(format.format(ckKhuyenMai), normal));
				cellSP.setBorder(0);cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);cellSP.setPadding(3.0f);
				table.addCell(cellSP);
			}		

			/*if(totalCK>0)*/
			/*{
				cellSP = new PdfPCell(new Paragraph("Chiết khấu sản phẩm", bold));cellSP.setBorder(0); cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cellSP.setColspan(7);cellSP.setPadding(3.0f);table.addCell(cellSP);		

				cellSP = new PdfPCell(new Paragraph(format.format(totalCK), normal));
				cellSP.setBorder(0);cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);cellSP.setPadding(3.0f);
				table.addCell(cellSP);
			}

			cellSP = new PdfPCell(new Paragraph("Công nợ", bold));
			cellSP.setBorder(0);
			cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellSP.setColspan(7);
			cellSP.setPadding(3.0f);
			table.addCell(cellSP);*/		

			/*cellSP = new PdfPCell(new Paragraph(format.format(congNo), normal));
			cellSP.setBorder(0);
			cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellSP.setPadding(3.0f);
			table.addCell(cellSP);*/

			cellSP = new PdfPCell(new Paragraph("Tổng tiền thanh toán", bold));
			cellSP.setBorder(0);
			cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellSP.setColspan(7);
			cellSP.setPadding(3.0f);
			table.addCell(cellSP);		

			cellSP = new PdfPCell(new Paragraph(format.format(totalThanhTien), normal));
			cellSP.setBorder(0);
			cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellSP.setPadding(3.0f);
			table.addCell(cellSP);		
			document.add(table);

			with= new float[]{80.0f,80.0f}; 	
			table = new PdfPTable(with.length);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidths(with);

			th = new String[]{ "Xác nhận NPP", "Xác nhận giám sát"};
			cells = new PdfPCell[with.length];
			for(int i = 0; i < with.length ; i++)
			{
				cells[i] = new PdfPCell(new Paragraph(th[i], bold));
				cells[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cells[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells[i].setBackgroundColor(BaseColor.WHITE);
				cells[i].setBorder(0);
				table.addCell(cells[i]);			
			}
			document.add(table);


			rs.close();
			document.close(); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private void DonDatHangExcel(OutputStream out,String id) throws Exception
	{
		try{
			dbutils db = new  dbutils();
			String query=
					"	select c.MA as MaSanPham,c.MA_DDT as MaDDT, d.DONVI as DonViTinh,b.soluong as SoLuong  "+
							"	from ERP_DONDATHANG a  "+
							"		inner join ERP_DONDATHANG_SANPHAM b on b.dondathang_fk=a.PK_SEQ "+
							"		inner join SANPHAM c on c.PK_SEQ=b.sanpham_fk "+
							"		inner join DONVIDOLUONG d on d.PK_SEQ=b.dvdl_fk "+
							"	where b.dondathang_fk=?";

			Workbook workbook = new Workbook();
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);	   
			worksheet.setName("Đơn đặt hàng");

			Cells cells = worksheet.getCells();
			cells.setRowHeight(0, 50.0f);
			Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 16,"THÔNG TIN ĐƠN HÀNG");


			ResultSet rs = db.getByPreparedStatement(query, new Object[]{id});// select pk_seq as[Mã NV], ten as [Đại diện kinh doanh ],doanh so as [Doanh Số] from daidienkinhdoanh

			ResultSetMetaData rsmd = rs.getMetaData();

			int socottrongSql = rsmd.getColumnCount();// số cột trong câu query[3 cột]

			int countRow = 8;// dòng đầu tiên dữ liệu
			int column =0;// cột đầu tiên dữ liệu


			//Vẽ header 
			for( int i =1 ; i <=socottrongSql ; i ++  )
			{
				cell = cells.getCell(countRow,i-1 + column);cell.setValue(rsmd.getColumnName(i));
				ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);   
			}// Vẽ ra  ->  Mã NV |  Đại diện kinh doanh |   Doanh Số    

			countRow ++;

			// đỗ dữ liệu
			while(rs.next())
			{
				for(int i =1;i <=socottrongSql ; i ++)
				{
					cell = cells.getCell(countRow,i-1 );
					if(rsmd.getColumnType(i) == Types.DOUBLE )// số liệu dạng float để đổ pivot bên value
						cell.setValue(rs.getDouble(i));
					else
						cell.setValue(rs.getString(i)); 
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
				}
				++countRow;
			}

			if(rs!=null)rs.close();
			if(db!=null){
				db.shutDown();
			}
			workbook.save(out);


		}catch(Exception ex){

			System.out.println("Errrorr : "+ex.toString());
			throw new Exception(" abcd");
		}

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen"); 
		Utility util = new Utility();
		String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("pdf")));
		System.out.println("id:"+ id);

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", " inline; filename=DonHangTraVe.pdf");

		Document document = new Document();
		ServletOutputStream outstream = response.getOutputStream();

		String type = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("type"));
		System.out.println("type:"+type);
		if(type == null)
			type = "";

		this.DonDatHangPdf(document, outstream, id );
	}
}
