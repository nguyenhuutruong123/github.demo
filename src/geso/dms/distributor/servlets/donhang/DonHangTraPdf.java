package geso.dms.distributor.servlets.donhang;

import geso.dms.center.beans.doctien.doctienrachu;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.distributor.beans.donhang.IDonhang;
import geso.dms.distributor.beans.donhang.ISanpham;
import geso.dms.distributor.beans.donhang.imp.Donhang;
import geso.dms.distributor.beans.dontrahang.IDontrahang;
import geso.dms.distributor.beans.dontrahang.IDontrahangList;
import geso.dms.distributor.beans.dontrahang.imp.Dontrahang;
import geso.dms.distributor.beans.dontrahang.imp.DontrahangList;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.awt.Rectangle;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import sun.org.mozilla.javascript.internal.regexp.SubString;





import com.aspose.cells.BorderLineType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class DonHangTraPdf extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	public DonHangTraPdf() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IDontrahang obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		
		Utility util = new Utility();
		
		String querystring = request.getQueryString();
		String userId = (String) session.getAttribute("userId");
		
		if (userId.length() == 0)
			userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
		String id = util.antiSQLInspection(request.getParameter("pdf"));

		String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		if(view == null) view = "";
		
		String action = util.getAction(querystring);
		
		String lsxId = util.getId(querystring);
		obj = new Dontrahang();
		obj.setUserId(userId);
		obj.setView(view);
		obj.setId(id);
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", " inline; filename=DonTraHang.pdf");

		Document document = new Document();
		ServletOutputStream outstream = response.getOutputStream();
		//mau 1 HO
		this.CreatePxk(document, response, outstream, obj);
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{}
	
	private void CreatePxk(Document document, HttpServletResponse response, ServletOutputStream outstream, 
			IDontrahang dhBean) throws IOException
	{
		try
		{		
			dbutils db =  new dbutils();
			
			String tennpp = "";
			String diachi = "";
			String dienthoai = "";
			String masothue = "";
			String ngaygiaohang = "";
			String ghichu = "";
			String ghichukhac = "";
			String kho = "";
			String kenh = "";
			String query = " \n select b.ten,b.DIENTHOAI,b.diachi,a.pk_seq,a.NGAYTRA,kho_fk,a.ghichu,kh.ten as tenkho,kbh.ten as tenkenh from DONTRAHANG a inner join nhaphanphoi b on "+
					       " \n a.npp_fk =b.pk_seq "+
					       " \n left join kho kh on kh.pk_seq=a.kho_Fk "+
					       " \n left join KENHBANHANG kbh on kbh.pk_seq=a.kbh_fk "+
						   " \n where a.pk_seq = " + dhBean.getId();
			System.out.println("query: " + query);
			ResultSet rs = db.get(query);
			try
			{
				while (rs.next()) {
					ngaygiaohang = rs.getString("ngaytra");
					tennpp = rs.getString("ten");
					diachi = rs.getString("diachi");
					dienthoai = rs.getString("DIENTHOAI");
					ghichu = rs.getString("ghichu");
					kho = rs.getString("tenkho");
					kenh = rs.getString("tenkenh");
				}
				rs.close();
			}
			catch (Exception er) {
				er.printStackTrace();
			}
//			System.out.println("ngaygiaohang: " + ngaygiaohang);
//			DateFormat formatterDay = new SimpleDateFormat("dd-MM-yyyy");
//			if(ngaygiaohang.trim().length() >0)  ngaygiaohang = formatterDay.format(ngaygiaohang);
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			NumberFormat formatter1 = new DecimalFormat("#,###,###"); 
			NumberFormat formatter2 = new DecimalFormat("#,###,###.####"); 

			PdfWriter.getInstance(document, outstream);
			document.open();
			document.left(1.0f);
			
			//lay doi tuong khach hang
			//chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 15, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			Font fontnomar = new Font(bf,10,Font.NORMAL);
			//font2.setColor(BaseColor.GREEN);
			//KHAI BAO 1 BANG CO BAO NHIEU COT

			PdfPTable tableheader = new PdfPTable(2);
			tableheader.setWidthPercentage(100);//chieu dai cua báº£ng 
			tableheader.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withsheader = {18.0f, 82.0f};//SET DO RONG CAC COT
			tableheader.setWidths(withsheader); 

			Image hinhanh = Image.getInstance( getServletContext().getInitParameter("path")+"/images/logosgp.png");
			
			//hinhanh.scalePercent(36);
			hinhanh.scalePercent(26);
			hinhanh.setAlignment(Element.ALIGN_CENTER);
			//hinhanh.setAlignment(Element.ALIGN_MIDDLE);

			PdfPCell cellslogo = new PdfPCell(hinhanh);

			cellslogo.setPadding(2);
			cellslogo.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			cellslogo.setVerticalAlignment(Element.ALIGN_MIDDLE);

			/*		
			Paragraph dvbh1 = new Paragraph("Điện thoại: " + dienthoai+"-Địa chỉ: " + diachi, fontnomar);
			dvbh1.setSpacingAfter(1);
			dvbh1.setAlignment(Element.ALIGN_LEFT);
			cellslogo.addElement(dvbh1);
			*/

			tableheader.addCell(cellslogo);

			Paragraph pxk = new Paragraph("PHIẾU TRẢ HÀNG NCC", font);
			pxk.setSpacingAfter(1);
			pxk.setAlignment(Element.ALIGN_CENTER);
			PdfPCell celltieude = new PdfPCell();
			celltieude.addElement(pxk);

			Paragraph dvbh = new Paragraph("Nhà phân phối: " + tennpp, fontnomar);
			dvbh.setSpacingAfter(1);
			dvbh.setAlignment(Element.ALIGN_LEFT);
			celltieude.addElement(dvbh);

			dvbh = new Paragraph("ĐT: " + dienthoai+" - ĐC: " + diachi, fontnomar);
			dvbh.setSpacingAfter(1);
			dvbh.setAlignment(Element.ALIGN_LEFT);
			celltieude.addElement(dvbh);
			String ngaygh = "";
			if(ngaygiaohang.trim().length() >0){
				ngaygh = ngaygiaohang.substring(8, 10)+ "-" + ngaygiaohang.substring(5, 7) + "-" + ngaygiaohang.substring(0, 4);
			}else {
				ngaygh="";
			}
			
			
			celltieude.addElement(new Paragraph("Mã số: " + dhBean.getId()+ ", 			 Ngày trả hàng: " + ngaygh, fontnomar));

			tableheader.addCell(celltieude);

			//Add bang vao document
			document.add(tableheader);						

			PdfPTable table_info = new PdfPTable(1);
			float[] with3 = {50.0f};//SET DO RONG CAC COT
			table_info.setWidthPercentage(100);
			table_info.setWidths(with3);
			PdfPCell cell111= new PdfPCell();

			cell111.addElement(new Paragraph("Đối tượng nhận: " + "CÔNG TY TNHH THỰC PHẨM ÁNH HỒNG", fontnomar));
			cell111.addElement(new Paragraph("Kho xuất hàng: " + kho,fontnomar));
			cell111.addElement(new Paragraph("Kênh bán hàng: " + kenh,fontnomar));
			cell111.addElement(new Paragraph("Ghi chú: " + ghichu,fontnomar));

			cell111.setPaddingBottom(10);
			table_info.addCell(cell111);
			document.add(table_info);
			
			query = "select tyleVAT,isnull(donhangkhac,0) as donhangkhac, isnull(ghichu,'') as ghichu from donhang where pk_seq = " +dhBean.getId();
			System.out.println("lay tilevat: " + query);
			double tileThueVat = 1;
			int donhangkhac = 0;
			String ghichudh = "";
			rs = db.get(query);
			try {
				if(rs.next()){
					tileThueVat = rs.getDouble("tylevat");
					donhangkhac = rs.getInt("donhangkhac");
					ghichudh = rs.getString("ghichu");
				}
				rs.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			System.out.println("tylevat: " + tileThueVat);
			
			//donhang combo donhangkhac =2 thi dùng form khac, donhangkhac =1,0 thì dùng form bình thường
			PdfPTable table = new PdfPTable(8);
			double totalle = 0;
			double totalTienCK = 0;
			double sotiengiam = 0;
			double ckdh = 0;
			float tongtienkhuyenmai = 0;
				//Table Content
				table = new PdfPTable(8);//Chu y nhe 6 cot o day, thi xuong duoi nho set withs la 6 cot
				table.setWidthPercentage(100);//chieu dai cua báº£ng 
				table.setHorizontalAlignment(Element.ALIGN_LEFT);
				//float[] withs = {5.0f, 9.0f, 30.0f, 8.0f, 8.0f, 6.0f, 10.0f, 6.0f, 0.0f, 12.0f};
				float[] withs = {4.0f, 7.0f, 15.0f, 6.0f, 4.0f, 8.0f, 7.0f, 15.0f};
				table.setWidths(withs);
//				String[] th = new String[]{"STT", "Mã hàng", "Tên hàng", "Kho", "Số lượng", "ĐVT", "Giá trước VAT", "Đơn giá", "%CK", "", "Thành tiền sau VAT"};
				String[] th = new String[]{"STT", "Mã hàng", "Tên hàng", "Số lượng", "ĐVT", "Đơn giá", "%CK", "Thành tiền sau VAT"};
				PdfPCell[] cell = new PdfPCell[8];
				for (int i = 0; i < 8 ; i++)
				{
					cell[i] = new PdfPCell(new Paragraph(th[i], fontnomar));
					cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
					//cell[i].setBackgroundColor(BaseColor.LIGHT_GRAY);		
					table.addCell(cell[i]);			
				}
				float size = 8.5f;
				Font font4 = new Font(bf, size);
				PdfPCell cells_detail = new PdfPCell();
			

				query ="\n  select sp.ma as spMa,sp.ten as spten,b.soluong,b.DONGIA,b.donvi,0 ck,b.sotienbvat from DONTRAHANG a inner join DONTRAHANG_SP b on a.pk_seq=b.DONTRAHANG_FK "+
						"\n inner join sanpham sp on sp.pk_seq=b.SANPHAM_FK "+
				"\n where a.pk_seq = '" + dhBean.getId() + "' ";
				System.out.println("IN DH: " + query);
				rs = db.get(query);
				int i = 0;
				totalTienCK = 0;
				sotiengiam = 0;
				ckdh = 0;
				double thanhtienavat = 0;
				double thanhtienbvat = 0;
				try
				{
					while (rs.next())
					{
						i++;
						String spMa = rs.getString("spMa");
						String spTen = rs.getString("spten");
						String spDonVi = rs.getString("donvi");
						double SoLuong = rs.getDouble("SoLuong");
						double dongia = rs.getDouble("dongia");
						
						double thanhTien = SoLuong*(dongia) ;
						double sotienbvat =  rs.getDouble("sotienbvat");
						ckdh = rs.getDouble("ck");
						thanhtienbvat += sotienbvat;
						thanhtienavat += thanhTien;

						String[] arr = new String[] {Integer.toString(i), spMa, spTen, formatter.format(SoLuong), spDonVi, formatter2.format(dongia),
								 formatter1.format(0), formatter1.format(thanhTien)};
						for (int j = 0; j < 8; j++)
						{
							cells_detail = new PdfPCell(new Paragraph(arr[j], font4));
							if (j == 2)
							{
								cells_detail.setHorizontalAlignment(Element.ALIGN_LEFT);
							}
							else
							{
								cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
							}
							cells_detail.setVerticalAlignment(Element.ALIGN_MIDDLE);
							table.addCell(cells_detail);
						}
					}
				} 
				catch (SQLException e)
				{
					e.printStackTrace();
				}

			PdfPCell cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			Paragraph km = new Paragraph("Tổng thành tiền ", fontnomar);
			km.setAlignment(Element.ALIGN_RIGHT);
			cell11.addElement(km);
			cell11.setColspan(7);
			table.addCell(cell11);

			
			
			 cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			Paragraph tongtien = new Paragraph(formatter1.format(thanhtienavat), fontnomar);
			tongtien.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(tongtien);
			table.addCell(cell11);

		

			PdfPCell cell12 = new PdfPCell();
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			km = new Paragraph("Tổng tiền sau VAT ", fontnomar);
			km.setAlignment(Element.ALIGN_RIGHT);
			cell12.addElement(km);
			cell12.setColspan(7);
			table.addCell(cell12);

			cell12 = new PdfPCell();
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			tongtien = new Paragraph(formatter1.format(thanhtienbvat), fontnomar);
			tongtien.setAlignment(Element.ALIGN_CENTER);
			cell12.addElement(tongtien);
			table.addCell(cell12);


			//doc tien bang chu
			doctienrachu doctien = new doctienrachu();
			cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			Long tongtienst = Math.round(thanhtienbvat);
			String tien_chu = doctien.tranlate(String.valueOf(tongtienst));
			tien_chu = tien_chu.substring(0, 1).toUpperCase() + tien_chu.substring(1);
			km = new Paragraph("Số tiền bằng chữ: " + tien_chu, fontnomar);
			km.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(km);
			cell11.setColspan(11);
			table.addCell(cell11);

			document.add(table);

			PdfPTable tablefooter = new PdfPTable(5);
			tablefooter.setWidthPercentage(100);
			tablefooter.setHorizontalAlignment(Element.ALIGN_CENTER);
			float[] withfooterder = {30.0f, 30.0f, 30.0f, 30.0f, 30.0f};//SET DO RONG CAC COT
			tablefooter.setWidths(withfooterder); 

			Paragraph para = new Paragraph("Khách hàng", fontnomar);
			para.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(para);
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell11.setBorderWidthRight(0);
			tablefooter.addCell(para);

			para = new Paragraph("Giao nhận", fontnomar);
			para.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(para);
			cell11.setBorderWidthLeft(0);
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell11.setBorderWidthRight(0);
			tablefooter.addCell(para);

			para = new Paragraph("Thủ kho", fontnomar);
			para.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(para);
			cell11.setBorderWidthLeft(0);
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell11.setBorderWidthRight(0);
			tablefooter.addCell(para);

			para = new Paragraph("Kế toán", fontnomar);
			para.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(para);
			cell11.setBorderWidthLeft(0);
			cell11.setBorderWidthRight(0);
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablefooter.addCell(para);

			para = new Paragraph("Người lập phiếu", fontnomar);
			para.add("\n");
			para.add("\n");
			para.add("\n");
			para.add("\n");
			/*para.add("\n");
			para.add("\n");
			para.add("\n");*/

			para.setAlignment(Element.ALIGN_CENTER);
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell11.addElement(para);
			cell11.setBorderWidthLeft(0);
			cell11.setBorder(0);
			tablefooter.addCell(para);
			
			document.add(tablefooter);
			document.close(); 
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}

	private String XuatFileExcel(HttpServletResponse response, String id) throws IOException
	{
		NumberFormat formatter = new DecimalFormat("#,###,###");
		dbutils db = null;
		try 
		{
			db = new dbutils();
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=ErpDonDatHang_" +getDateTime()+".xls");
			
			Workbook workbook = new Workbook();
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);	    
			worksheet.setName("Sheet1");
			Cells cells = worksheet.getCells();	
			Cell cell;
			cells.setRowHeight(0, 50.0f);
			worksheet.setGridlinesVisible(false);
			String dhId = id;
			String query = "\n select a.pk_Seq as SoDonHang, a.NgayNhap as NgayDonHang, c.smartId as MaNV, c.ten as TenNV, " +
			"\n d.ten as TenKH, d.DiaChi as DiaChiKH, e.ma as spMa, e.ten as spTen, f.donvi as spDonVi, " + 
			"\n b.SoLuong, b.DonGiaGoc as spGia, b.GiaMua as spGiaSauCK,(b.SoLuong * DonGiaGoc) as spThanhTien, " +
			"\n (b.SoLuong * GiaMua) as spThanhTienSauCK, b.SoTT " +
			"\n from donhang a inner join donhang_Sanpham b on b.donhang_fk = a.pk_Seq " +
			"\n inner join daidienkinhdoanh c on c.pk_Seq = a.ddkd_fk " +
			"\n inner join khachhang d on d.pk_seq = a.khachhang_fk " +
			"\n inner join sanpham e on e.pk_seq = b.sanpham_fk " +
			"\n inner join donvidoluong f on f.pk_Seq = e.dvdl_fk " +
			"\n where a.pk_Seq = " + dhId +
			"\n union all " +
			"\n select a.pk_Seq as SoDonHang, a.NgayNhap as NgayDonHang, c.smartId as MaNV, c.ten as TenNV, " +
			"\n d.ten as TenKH, d.DiaChi as DiaChiKH, e.ma as spMa, e.ten as spTen, f.donvi as spDonVi, " +
			"\n b.SoLuong, 0 as spGia, 0 as spGiaSauCK, (0) as spThanhTien, " +
			"\n (0) as spThanhTienSauCK, ROW_NUMBER() OVER(PARTITION BY B.donhangId ORDER BY B.CTKMID DESC) AS SOTT " +
			"\n from donhang a inner join donhang_Ctkm_trakm b on b.donhangId = a.pk_Seq " +
			"\n inner join daidienkinhdoanh c on c.pk_Seq = a.ddkd_fk  " +
			"\n inner join khachhang d on d.pk_seq = a.khachhang_fk " +
			"\n inner join sanpham e on e.ma = b.spMa " +
			"\n inner join donvidoluong f on f.pk_Seq = e.dvdl_fk " +
			"\n where a.pk_Seq = " + dhId +
			"\n union all " +
			"\n select a.pk_Seq as SoDonHang, a.NgayNhap as NgayDonHang, c.smartId as MaNV, c.ten as TenNV, " +
			"\n d.ten as TenKH, d.DiaChi as DiaChiKH, '' as spMa,'' as spTen,'' as spDonVi, " +
			"\n 1 as SoLuong, 1 as spGia, 1 as spGiaSauCK, -1 * (b.TongGiatri) as spThanhTien, " +
			"\n -1 * (b.TongGiatri) as spThanhTienSauCK, ROW_NUMBER() OVER(PARTITION BY B.donhangId ORDER BY B.CTKMID DESC) AS SOTT " +
			"\n from donhang a " +
			"\n inner join donhang_Ctkm_trakm b on b.donhangId = a.pk_Seq " +
			"\n inner join daidienkinhdoanh c on c.pk_Seq = a.ddkd_fk " +
			"\n inner join khachhang d on d.pk_seq = a.khachhang_fk " +
			"\n where b.spMa is null " +
			"\n and a.pk_Seq = " + dhId;
			System.out.println("Query Excel: " + query);

			ResultSet rs = db.get(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();// số cột trong câu query[3 cột]
			int countRow = 0;// dòng đầu tiên dữ liệu
			int column =0;// cột đầu tiên dữ liệu

			//Vẽ header 
			for (int i = 1; i <= socottrongSql; i++)
			{
				if (!rsmd.getColumnName(i).equals("SoTT"))
				{
					cell = cells.getCell(countRow,i-1 + column);cell.setValue(rsmd.getColumnName(i));
					ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, true, 0);
					Style style;
					style=cell.getStyle();
					style.setTextWrapped(true);
					style.setHAlignment(TextAlignmentType.CENTER);
					style.setVAlignment(TextAlignmentType.CENTER);
					cell.setStyle(style);
				}
			}// Vẽ ra  ->  Mã NV |  Đại diện kinh doanh |   Doanh Số    
			countRow++;

			// đỗ dữ liệu
			while (rs.next())
			{
				for (int i = 1; i <= socottrongSql; i++)
				{
					if (!rsmd.getColumnName(i).equals("SoTT"))
					{
						cell = cells.getCell(countRow,i-1 );
						if (rsmd.getColumnType(i) == Types.DOUBLE )// số liệu dạng float để đổ pivot bên value
						{
							ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
							cell.setValue(rs.getDouble(i));
						}
						else
						{
							cell.setValue(rs.getString(i)); 
							ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
						}
					}
				}
				++countRow;
			}

			if (rs != null) rs.close();

			OutputStream out = response.getOutputStream();
			workbook.save(out);
			out.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally {
			if (db != null) {
				db.shutDown();
			}
		}

		return "";
	}
}
