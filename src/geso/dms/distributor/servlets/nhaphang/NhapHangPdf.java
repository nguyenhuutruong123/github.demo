package geso.dms.distributor.servlets.nhaphang;

import geso.dms.center.beans.doctien.doctienrachu;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.distributor.beans.nhaphang.INhaphang;
import geso.dms.distributor.beans.nhaphang.imp.Nhaphang;
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

public class NhapHangPdf extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	public NhapHangPdf() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		INhaphang obj;
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
		obj = new Nhaphang();
		obj.setUserId(userId);
		obj.setId(id);
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", " inline; filename=NhapHang.pdf");

		Document document = new Document();
		ServletOutputStream outstream = response.getOutputStream();
		//mau 1 HO
		this.CreatePxk(document, response, outstream, obj);
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{}
	
	private void CreatePxk(Document document, HttpServletResponse response, ServletOutputStream outstream, 
			INhaphang dhBean) throws IOException
	{
		try
		{		
			dbutils db =  new dbutils();
			
			String ngayyeucau = "";
			String ghichu = "";
			String loai = "";
			String ngaynhan = "";
			String kho_fk = "";
			String CHUNGTU = "";
			String dondathang_fk = "";
			String ghichuTT = "";

			String query = 
					" select isnull(nh.ghichuimport, 0) ghichuTT,isnull(nh.loai, 0) loai, nh.ngaychungtu as ngayyeucau, isnull(nh.ghichu,'') as ghichu" +
					"	, nh.npp_fk,   nh.ngaynhan, nh.trangthai, isnull(nh.kho_fk, 100000) as kho_fk ,nh.CHUNGTU " +
					"	, kbh.ten KBH ,isnull(nh.dondathang_fk,0) as dondathang_fk" +
					" from NhapHang nh " +
					" inner join kenhbanhang kbh on kbh.pk_seq = nh.kbh_fk " +
					" where nh.pk_seq =  " + dhBean.getId();
			System.out.println("query: " + query);
			ResultSet rs = db.get(query);
			try
			{
				while (rs.next()) {
					ngayyeucau = rs.getString("ngayyeucau");
					ghichu = rs.getString("ghichu");
					ghichuTT = rs.getString("ghichuTT");

					ngaynhan = rs.getString("ngaynhan");
					kho_fk = rs.getString("kho_fk");
					CHUNGTU = rs.getString("CHUNGTU");
					dondathang_fk = rs.getString("dondathang_fk");
				}
				rs.close();
			}
			catch (Exception er) {
				er.printStackTrace();
			}
			query ="\n  select b.PK_SEQ, b.MA, b.TEN, a.DONVI, GiaNet as DONGIA, isnull(a.SOLO, 'NA') as SOLO, a.SOLUONG, "+
					"\n ISNULL(SOLUONGNHAN, a.SOLUONG) as soluongNHAN, tienbvat, vat, tienavat,  "
					+ "\n isnull(a.SCHEME, '') as SCHEME, a.kho_fk as khoId,a.chietkhau,10 as thuevat,k.diengiai as tenkho "
					+ "\n from nhaphang_sp a inner join SANPHAM b on a.SANPHAM_FK = b.pk_seq 		"+
			"\n inner join DONVIDOLUONG c on b.DVDL_FK = c.PK_SEQ "+
			"\n left join kho k on k.pk_seq = a.kho_Fk " +
			 "\n where a.NHAPHANG_FK =  '" + dhBean.getId() + "' ";
			System.out.println("IN DH: " + query);
			rs = db.get(query);
			int i = 0;
			
			double vat = 0;
			double tonggiatri = 0;
			double tiensauvat = 0;

			try
			{
				while (rs.next())
				{
					i++;
					
					double SoLuong = rs.getDouble("SoLuong");
					double dongia = rs.getDouble("dongia");
					
					double thuevat = rs.getDouble("thuevat");
                    vat +=SoLuong*(dongia)*thuevat/100;
                    tonggiatri +=SoLuong*(dongia);

					double thanhTien = SoLuong*(dongia) ;

				
				}
			} 
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			tiensauvat=tonggiatri+vat;

			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			NumberFormat formatter1 = new DecimalFormat("#,###,###"); 
			NumberFormat formatter2 = new DecimalFormat("#,###,###.####"); 

			PdfWriter.getInstance(document, outstream);
			document.open();
			document.left(1.0f);
			
			
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 15, Font.BOLD);
			Font fontnomar = new Font(bf,10,Font.NORMAL);
			PdfPTable tableheader = new PdfPTable(1);
			tableheader.setWidthPercentage(100);//chieu dai cua báº£ng 
			tableheader.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			Paragraph pxk = new Paragraph("PHIẾU NHẬN HÀNG", font);
			pxk.setSpacingAfter(1);
			pxk.setAlignment(Element.ALIGN_CENTER);
			PdfPCell celltieude = new PdfPCell();
			celltieude.addElement(pxk);

		
			tableheader.addCell(celltieude);

			//Add bang vao document
			document.add(tableheader);	

			PdfPTable table_info = new PdfPTable(1);
			float[] with3 = {50.0f};//SET DO RONG CAC COT
			table_info.setWidthPercentage(100);
			table_info.setWidths(with3);
			PdfPCell cell111= new PdfPCell();
		
             if(dondathang_fk.equals("0")){ 
            	 dondathang_fk="";	
             }
			cell111.addElement(new Paragraph("Ngày xuất kho: " +ngayyeucau+         "                           Ngày nhận hàng:"+ngaynhan , fontnomar));
			cell111.addElement(new Paragraph("Số chừng từ: " + CHUNGTU,fontnomar));
			cell111.addElement(new Paragraph("Đơn đặt hàng: " + dondathang_fk,fontnomar));
			cell111.addElement(new Paragraph("Ghi chú: " + ghichu,fontnomar));
			cell111.addElement(new Paragraph("Ghi chú TT: " + ghichuTT,fontnomar));

			cell111.addElement(new Paragraph("Tổng giá trị: " + formatter1.format(tonggiatri),fontnomar));
			cell111.addElement(new Paragraph("Tiền Vat: "     + formatter1.format(vat)+ "                                                      Tổng tiền sau Vat: " + formatter1.format(tiensauvat),fontnomar));

			cell111.setPaddingBottom(10);
			table_info.addCell(cell111);
			document.add(table_info);
			
			
			
			//donhang combo donhangkhac =2 thi dùng form khac, donhangkhac =1,0 thì dùng form bình thường
			PdfPTable table = new PdfPTable(7);
		
				//Table Content
				table = new PdfPTable(7);//Chu y nhe 6 cot o day, thi xuong duoi nho set withs la 6 cot
				table.setWidthPercentage(100);//chieu dai cua báº£ng 
				table.setHorizontalAlignment(Element.ALIGN_LEFT);
				//float[] withs = {5.0f, 9.0f, 30.0f, 8.0f, 8.0f, 6.0f, 10.0f, 6.0f, 0.0f, 12.0f};
				float[] withs = { 12.0f, 15.0f, 6.0f, 6.0f, 8.0f,  15.0f,15.0f};
				table.setWidths(withs);
//				String[] th = new String[]{"STT", "Mã hàng", "Tên hàng", "Kho", "Số lượng", "ĐVT", "Giá trước VAT", "Đơn giá", "%CK", "", "Thành tiền sau VAT"};
				String[] th = new String[]{"Mã sản phẩm", "Tên sản phẩm", "Đơn vị", "Số lượng", "Đơn giá", "Mã chương trình", "Kho"};
				PdfPCell[] cell = new PdfPCell[8];
				for ( i = 0; i <7 ; i++)
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
			

				query ="\n  select b.PK_SEQ, b.MA, b.TEN, a.DONVI, GiaNet as DONGIA, isnull(a.SOLO, 'NA') as SOLO, a.SOLUONG, "+
						"\n ISNULL(SOLUONGNHAN, a.SOLUONG) as soluongNHAN, tienbvat, vat, tienavat,  "
						+ "\n isnull(a.SCHEME, '') as SCHEME, a.kho_fk as khoId,a.chietkhau,10 as thuevat,k.diengiai as tenkho "
						+ "\n from nhaphang_sp a inner join SANPHAM b on a.SANPHAM_FK = b.pk_seq 		"+
				"\n inner join DONVIDOLUONG c on b.DVDL_FK = c.PK_SEQ "+
				"\n left join kho k on k.pk_seq = a.kho_Fk " +
				 "\n where a.NHAPHANG_FK =  '" + dhBean.getId() + "' ";
				System.out.println("IN DH: " + query);
				rs = db.get(query);
				 i = 0;
				
				try
				{
					while (rs.next())
					{
						i++;
						String spMa = rs.getString("ma");
						String spTen = rs.getString("ten");
						String spDonVi = rs.getString("donvi");
						double SoLuong = rs.getDouble("SoLuong");
						double dongia = rs.getDouble("dongia");
						String SCHEME = rs.getString("SCHEME");
						String tenkho = rs.getString("tenkho");
						

						String[] arr = new String[] { spMa, spTen, spDonVi,formatter.format(SoLuong),  formatter2.format(dongia),
								SCHEME, tenkho};
						for (int j = 0; j < 7; j++)
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
			

			document.add(table);

			
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
