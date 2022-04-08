package geso.dms.distributor.servlets.donhang;

import geso.dms.center.beans.doctien.doctienrachu;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.distributor.beans.donhang.IDonhang;
import geso.dms.distributor.beans.donhang.ISanpham;
import geso.dms.distributor.beans.donhang.imp.Donhang;
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

public class InBangKepdfSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	public InBangKepdfSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum)) {
			response.sendRedirect("/AHF/index.jsp");
		}
		else{
			IDonhang dhBean;
			dbutils db;

			Utility util = new Utility();
			String querystring = request.getQueryString();
			String dhid = util.getId(querystring);
			String excel = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("excel"));
			String dhCxl = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dhCxl"));

			System.out.println("[excel]"+excel+" - dhCxl : "+ dhCxl);
			if (excel != null)
			{
				XuatFileExcel(response, excel);
			}
			else if (dhCxl == null)
			{
				userId = util.getUserId(querystring);
				dhBean = new Donhang(dhid); 
				dhBean.setUserId(userId); //phai co UserId truoc khi Init
				dhBean.init();	
				dhBean.setKhId(dhBean.getKhId());
				List<ISanpham> splist = (List<ISanpham>)dhBean.getSpList();
				List<ISanpham> spkmlist = (List<ISanpham>)dhBean.getScheme_SpList();
				Hashtable<String, Float> scheme_tongtien = dhBean.getScheme_Tongtien(); 
				Hashtable<String, Float> scheme_chietkhau = dhBean.getScheme_Chietkhau(); 
				db = dhBean.getDb();

				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition"," inline; filename=HoaDonGTGT.pdf");

				//hien thi hop thoai dialog save file xuong may Client
				//Document document = new Document(PageSize.A4, 10, 30, 20, 20); //(PageSize.A5.rotate(),10, 20, 10, 20);
				
				//Document document = new Document(PageSize.A5.rotate(), 10, 30, 20, 20);
				//float left = 30;  float right = 30; float top = 60;  float bottom = 0;
				//Document document = new Document(PageSize.A5.rotate(), 10, 50, 20, 20);
				Document document = new Document(PageSize.A5.rotate(), 10, 50, 20, 0);
				document.left(2.0f);
				ServletOutputStream outstream = response.getOutputStream();
				if(dhBean.getDonhangkhac().equals("2")){
					//don hang combo
					this.CreatePxk_donhangkhac(document, outstream, splist, spkmlist, scheme_tongtien, scheme_chietkhau, dhBean, db);
				}else {
					this.CreatePxk(document, outstream, splist, spkmlist, scheme_tongtien, scheme_chietkhau, dhBean, db);
				}
				
				
				splist.clear();
				spkmlist.clear();
				scheme_tongtien.clear();
				scheme_chietkhau.clear();
				dhBean.DBclose();
				util = null;
				
				if (db != null) 
					db.shutDown();
				
				System.out.println("[In]: " + excel);
			}
			else if (dhCxl != null)
			{
				userId = util.getUserId(querystring);
				dhBean = new Donhang(dhid); 
				dhBean.setUserId(userId); //phai co UserId truoc khi Init
				dhBean.init();	
				dhBean.setKhId(dhBean.getKhId());
				List<ISanpham> splist = (List<ISanpham>)dhBean.getSpList();
				List<ISanpham> spkmlist = (List<ISanpham>)dhBean.getScheme_SpList();
				Hashtable<String, Float> scheme_tongtien = dhBean.getScheme_Tongtien(); 
				Hashtable<String, Float> scheme_chietkhau = dhBean.getScheme_Chietkhau(); 
				db = dhBean.getDb();

				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition"," inline; filename=HoaDonGTGT.pdf");

				//hien thi hop thoai dialog save file xuong may Client
				Document document = new Document(PageSize.A5.rotate(),10, 30, 10, 20);
				ServletOutputStream outstream = response.getOutputStream();
				if(dhBean.getDonhangkhac().equals("2")){
					this.CreatePxk_donhangkhac(document, outstream, splist, spkmlist, scheme_tongtien, scheme_chietkhau, dhBean, db);
				}else {
					this.CreatePxk(document, outstream, splist, spkmlist, scheme_tongtien, scheme_chietkhau, dhBean, db);
				}
				
				db.shutDown();
				splist.clear();
				spkmlist.clear();
				scheme_tongtien.clear();
				scheme_chietkhau.clear();
				dhBean.DBclose();
				util = null;
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");

		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum)) {
			response.sendRedirect("/AHF/index.jsp");
		}
		else{
			IDonhang dhBean;
			dbutils db;
			userId = cutil.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
			String id=cutil.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));

			dhBean= new Donhang(id);
			dhBean.setUserId(userId); //phai co UserId truoc khi Init
			dhBean.init();
			dhBean.setKhId(dhBean.getKhId());

			dhBean.CreateSpDisplay();

			List<ISanpham> splist = (List<ISanpham>)dhBean.getSpList();
			List<ISanpham> spkmlist = (List<ISanpham>)dhBean.getScheme_SpList();
			Hashtable<String, Float> scheme_tongtien = dhBean.getScheme_Tongtien(); //khuyen mai tien
			Hashtable<String, Float> scheme_chietkhau = dhBean.getScheme_Chietkhau(); //khuyen mai chiet khau
			db = new dbutils();

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition"," inline; filename=HoaDonGTGT.pdf");

			Document document = new Document();
			ServletOutputStream outstream = response.getOutputStream();
			this.CreatePxk(document, outstream, splist,spkmlist,scheme_tongtien,scheme_chietkhau, dhBean, db);
			db.shutDown();
			splist.clear();
			spkmlist.clear();
			scheme_tongtien.clear();
			scheme_chietkhau.clear();
			dhBean.DBclose();
			cutil=null;

		}

	}
	
	private void CreatePxk(Document document, ServletOutputStream outstream, List<ISanpham> spList, 
			List<ISanpham> spkmlist, Hashtable<String, Float> scheme_tongtien, Hashtable<String, Float> scheme_chietkhau, 
			IDonhang dhBean, dbutils db) throws IOException
	{
		try
		{		
			String sql = "update donhang set trangthaiin = '1' where pk_seq = '"+ dhBean.getId() +"'";
			db.update(sql);
			
			String tennpp = "";
			String diachi = "";
			String dienthoai = "";
			String masothue = "";
			String ngaygiaohang = "";
			String ghichu = "";
			String ghichukhac = "";
			String madonhang = "";
			String query = "\n select isnull(dh.ghichu,'') as ghichukhac, isnull(dh.tylevat,1) as tylevat, isnull(gc.DIENGIAI,'')ghichu,isnull(dh.ngaynhap,'') ngaygiaohang, npp.ten, isnull(npp.diachi,'') as diachi, " +
							"\n isnull(npp.masothue,'') as masothue, isnull(npp.dienthoai,'') as dienthoai,dh.madonhang " +
							"\n from nhaphanphoi npp " +
							"\n inner join donhang dh on dh.npp_fk = npp.pk_seq" +
							"\n	left join ghichu gc on dh.ghichu_fk = gc.PK_SEQ " + 
							"\n where dh.pk_seq = " + dhBean.getId();
			System.out.println("query: " + query);
			ResultSet rs = db.get(query);
			try
			{
				while (rs.next()) {
					ngaygiaohang = rs.getString("ngaygiaohang");
					tennpp = rs.getString("ten");
					diachi = rs.getString("diachi");
					dienthoai = rs.getString("dienthoai");
					masothue = rs.getString("masothue");
					madonhang = rs.getString("madonhang");
					ghichu = rs.getString("ghichu");
					ghichukhac = rs.getString("ghichukhac");
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
			Font fontnomar1 = new Font(bf,10,Font.BOLD);
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

			Paragraph pxk = new Paragraph("PHIẾU GIAO HÀNG", font);
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
			
			/*dvbh = new Paragraph("ĐT: " + dienthoai, fontnomar);
			dvbh.setSpacingAfter(1);
			dvbh.setAlignment(Element.ALIGN_LEFT);
			celltieude.addElement(dvbh);
			
			dvbh = new Paragraph("ĐC: " + diachi, fontnomar);
			dvbh.setSpacingAfter(1);
			dvbh.setAlignment(Element.ALIGN_LEFT);
			celltieude.addElement(dvbh);*/

			/*dvbh = new Paragraph("Địa chỉ: " + diachi, fontnomar);
			dvbh.setSpacingAfter(3);
			dvbh.setAlignment(Element.ALIGN_LEFT);
			celltieude.addElement(dvbh);*/

			//tableheader.addCell(celltieude);

			/*PdfPCell cellinfo = new PdfPCell();*/
			String ngay = dhBean.getNgaygiaodich().substring(8, 10)+ "-" + dhBean.getNgaygiaodich().substring(5, 7) 
				+ "-" + dhBean.getNgaygiaodich().substring(0, 4);
			String ngaygh = "";
			if(ngaygiaohang.trim().length() >0){
				ngaygh = ngaygiaohang.substring(8, 10)+ "-" + ngaygiaohang.substring(5, 7) + "-" + ngaygiaohang.substring(0, 4);
			}else {
				ngaygh="";
			}
			System.out.println("Ngày giao hàng"+ngaygh);
			
			celltieude.addElement(new Paragraph("Số chứng từ: " + dhBean.getId() + " - Ngày chứng từ: " + ngay + ", 			 Ngày giao hàng: " + ngaygh, fontnomar));
//			celltieude.addElement(new Paragraph("Ngày giao hàng: " + ngaygiaohang, fontnomar));
			celltieude.addElement(new Paragraph("Mã TT: " + madonhang, fontnomar1));

			//celltieude.addElement(new Paragraph("Ngày chứng từ: " + ngay,fontnomar));
			tableheader.addCell(celltieude);

			//Add bang vao document
			document.add(tableheader);						

			PdfPTable table_info = new PdfPTable(1);
			float[] with3 = {50.0f};//SET DO RONG CAC COT
			table_info.setWidthPercentage(100);
			table_info.setWidths(with3);
			PdfPCell cell111= new PdfPCell();

			String tenkh = "";
			String diachikh = "";
			String dienthoaikh = "";
			String masothuekh = "";
			String quanhuyen = "";
			String tinhthanh = "";
			String phuongxa = "";
			String sql_getinfokh = "\n select khachhang.ten,isnull(khachhang.dienthoai,'') as dienthoai, isnull(khachhang.diachi,'') as diachi, " +
			"\n isnull(q.TEN,'') as quan, isnull(t.TEN,'') as tinhthanh, isnull(px.ten,'') as phuongxa, isnull(khachhang.masothue,'') as masothue " +
			"\n from khachhang " +
			"\n left join QUANHUYEN q ON q.pk_seq = khachhang.quanhuyen_fk " +
			"\n left join TINHTHANH t on t.pk_seq = khachhang.tinhthanh_fk " +
			"\n left join phuongxa px on px.Pk_seq = khachhang.phuongxa_fk " +
			"\n where khachhang.pk_seq = " + dhBean.getKhId();
			System.out.println("sql_getinfokh: " + sql_getinfokh);
			ResultSet rs_kh = db.get(sql_getinfokh);
			try {
				if (rs_kh.next()) {
					tenkh = rs_kh.getString("ten");
					diachikh = rs_kh.getString("diachi");
					dienthoaikh = rs_kh.getString("dienthoai");
					masothuekh = rs_kh.getString("masothue");
					quanhuyen = rs_kh.getString("quan");
					tinhthanh = rs_kh.getString("tinhthanh");
					phuongxa = rs_kh.getString("phuongxa");
				}
				rs_kh.close();
			}
			catch (Exception er )
			{
				er.printStackTrace();
				System.out.println("Lỗi ngoại lệ lấy dữ liệu KH: " + er.toString());
			}
			
			//lay dai dien kinh doanh
			String ddkdten="";
			String dienthoaiDDKD = "";
			String sql_getddkd = "\n select pk_seq, ten, isnull(dienthoai, '') as dienthoai " +
			"\n from daidienkinhdoanh where pk_seq = " + dhBean.getDdkdId();
			ResultSet rs_getddkd = db.get(sql_getddkd);
			System.out.println("sql_getddkd: " + sql_getddkd);
			try
			{
				if (rs_getddkd.next())
				{
					ddkdten = rs_getddkd.getString("ten");
					dienthoaiDDKD = rs_getddkd.getString("dienthoai");
				}
				rs_getddkd.close();
			}
			catch (Exception er ) {
				er.printStackTrace();
				System.out.println("Lỗi ngoại lệ lấy dữ liệu DDKD: " + er.toString());
			}

			String ddkd = "Đại diện kinh doanh: " + ddkdten;
			if (dienthoaiDDKD.trim().equals("NA"))
				dienthoaiDDKD = "";
			
			if (dienthoaiDDKD.trim().equals("N/A"))
				dienthoaiDDKD = "";
			
			if (dienthoaiDDKD.length() > 0)
				ddkd += " (ĐT:" + dienthoaiDDKD + ")";

			cell111.addElement(new Paragraph(ddkd, fontnomar));

			String dd_khachhang = "Mã khách hàng: " + dhBean.getSmartId() + "     Tên khách hàng : " + tenkh ;
			if (dienthoaikh.length() > 0)
				dd_khachhang += " (" + dienthoaikh + ")";
			cell111.addElement(new Paragraph(dd_khachhang ,fontnomar));
			
			if (phuongxa.length() != 0)
				phuongxa = ", phường "+phuongxa;
			
			if (quanhuyen.length() != 0)
				quanhuyen = ", quận " +quanhuyen;
			
			if (tinhthanh.length() != 0)
				tinhthanh = ", Tỉnh/Tp "+tinhthanh;
			
			cell111.addElement(new Paragraph("Địa chỉ: " + diachikh + phuongxa + quanhuyen + tinhthanh, fontnomar));
			cell111.addElement(new Paragraph("Ghi chú: " + ghichu,fontnomar));
			cell111.addElement(new Paragraph("Ghi chú khác: " + ghichukhac,fontnomar));
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
			PdfPTable table = new PdfPTable(11);
			double totalle = 0;
			double totalTienCK = 0;
			double sotiengiam = 0;
			double ckdh = 0;
			float tongtienkhuyenmai = 0;
				//Table Content
				table = new PdfPTable(11);//Chu y nhe 6 cot o day, thi xuong duoi nho set withs la 6 cot
				table.setWidthPercentage(100);//chieu dai cua báº£ng 
				table.setHorizontalAlignment(Element.ALIGN_LEFT);
				//float[] withs = {5.0f, 9.0f, 30.0f, 8.0f, 8.0f, 6.0f, 10.0f, 6.0f, 0.0f, 12.0f};
				float[] withs = {4.0f, 7.0f, 19.0f, 6.0f, 6.0f, 10.0f, 8.0f, 6.0f, 0.0f, 12.0f, 9.0f};
				table.setWidths(withs);
//				String[] th = new String[]{"STT", "Mã hàng", "Tên hàng", "Kho", "Số lượng", "ĐVT", "Giá trước VAT", "Đơn giá", "%CK", "", "Thành tiền sau VAT"};
				String[] th = new String[]{"STT", "Mã hàng", "Tên hàng", "Số lượng", "ĐVT", "Giá trước VAT", "Đơn giá", "%CK", "", "Thành tiền sau VAT","Ghi chú"};
				PdfPCell[] cell = new PdfPCell[11];
				for (int i = 0; i < 11 ; i++)
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
				totalle = 0;

				query = "\n select d.ten as khoTen,c.donvi as spDonVi, b.ma as spMa, b.ten as spTen, " +
				"\n a.SoLuong, a.GiaMua, a.DonGiaGoc, isnull(ptCKDH,0) as ckDH, " +
				"\n isnull(ptCKTT,0) as ckTT, isnull(ptCKDLN,0) as ckDLN, " +
				"\n isnull(ptCKTT,0) + isnull(ptCKDH,0) + isnull(ptCKDLN,0) as TongCK, " +
				"\n (select isnull(chietkhau,0) from donhang where pk_seq = a.donhang_fk) as chietkhau, " +
				"\n (select isnull(tyleVAT,1) from donhang where pk_seq = a.donhang_fk) as tyleVAT, " +
				"\n (select isnull(Sotiengiam,0) from donhang where pk_seq = a.donhang_fk) as sotiengiam "+
				"\n from DonHang_SanPham a " +
				"\n inner join SanPham b on b.pk_Seq = a.sanpham_fk " +
				"\n inner join DonViDoLuong c on c.pk_Seq = b.DVDL_FK " +
				"\n INNER JOIN Kho d on d.pk_Seq = a.Kho_FK " +
				"\n where a.donhang_fk = '" + dhBean.getId() + "' ";
				System.out.println("IN DH: " + query);
				rs = db.get(query);
				int i = 0;
				totalTienCK = 0;
				sotiengiam = 0;
				ckdh = 0;
				try
				{
					while (rs.next())
					{
						i++;
						String spMa = rs.getString("spMa");
						String spTen = rs.getString("spTen");
						String spDonVi = rs.getString("spDonVi");
						String khoTen = rs.getString("khoTen");
						double SoLuong = rs.getDouble("SoLuong");
						double GiaSauCK = rs.getDouble("GiaMua");
						double DonGiaGoc =0;
						if(donhangkhac ==1){
							DonGiaGoc =0;
						}else {
							DonGiaGoc =rs.getDouble("DonGiaGoc");
						}
						
						double totalCK = rs.getDouble("TongCK");
						double thanhTien = Math.round(SoLuong*(DonGiaGoc)) ;
						double tienSauCK =   Math.round(SoLuong*(DonGiaGoc * (1 - totalCK / 100)));
						double tienCK = thanhTien - tienSauCK;
						System.out.println("Thanhtien: "+thanhTien+" Tiensauck: " + tienSauCK);
						ckdh = rs.getDouble("chietkhau");
						double tylevat = rs.getDouble("tyleVat");
						sotiengiam = rs.getDouble("sotiengiam");
						totalTienCK += tienCK;
						totalle += thanhTien;
//						String[] arr = new String[] {Integer.toString(i), spMa, spTen, khoTen, formatter.format(SoLuong), spDonVi, formatter2.format(DonGiaGoc/tylevat),
//								formatter2.format(DonGiaGoc), formatter1.format(totalCK), "", formatter1.format(thanhTien)};
						String[] arr = new String[] {Integer.toString(i), spMa, spTen, formatter.format(SoLuong), spDonVi, formatter2.format(DonGiaGoc/tylevat),
								formatter2.format(DonGiaGoc), formatter1.format(totalCK), "", formatter1.format(thanhTien),""};
						for (int j = 0; j < 11; j++)
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
				
				query ="select count(*) sodem from DONHANG_CTKM_TRAKM dhkm " +
						"\n	inner join CTKHUYENMAI ctkm on ctkm.PK_SEQ = dhkm.CTKMID "+
						"\n	inner join kho k on k.pk_seq = ctkm.KHO_FK"+
						"\n	inner join sanpham sp on sp.ma = dhkm.SPMA"+
						"\n	left join DonViDoLuong dvt on dvt.pk_Seq = sp.DVDL_FK "+
						"\n	where donhangId ='" + dhBean.getId() + "' and dhkm.SPMA is not null";
				rs = db.get(query);
				int count =0;
				try {
					if(rs.next()){
						count = rs.getInt("sodem");
					}
					rs.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
//				if(count>0 || spkmlist.size() > 0  ){
//					PdfPCell cellkhuyenmai = new PdfPCell();
//					cellkhuyenmai.setHorizontalAlignment(Element.ALIGN_CENTER);
//					Paragraph km1= new Paragraph("Hàng khuyến mãi", fontnomar);
//					km1.setAlignment(Element.ALIGN_LEFT);
//					cellkhuyenmai.addElement(km1);
//					cellkhuyenmai.setColspan(11);
//					table.addCell(cellkhuyenmai);
//				}
				
				
				int demtt = 0;
				query = "\n  select case when ctkm.SCHEME='Sample' then N'Hàng Mẫu' else ctkm.SCHEME end as scheme, dhkm.donhangId, dhkm.SPMA ,sp.ten as spten, k.ten as khoTen, dhkm.soluong, dvt.DONVI as spDonVi"+
						"\n	from DONHANG_CTKM_TRAKM dhkm"+
						"\n	inner join CTKHUYENMAI ctkm on ctkm.PK_SEQ = dhkm.CTKMID "+
						"\n	inner join kho k on k.pk_seq = ctkm.KHO_FK "+
						"\n	inner join sanpham sp on sp.ma = dhkm.SPMA "+
						"\n	left join DonViDoLuong dvt on dvt.pk_Seq = sp.DVDL_FK "+
						"\n	where donhangId ='" + dhBean.getId() + "' and dhkm.SPMA is not null";
						System.out.println("IN DH: " + query);
						rs = db.get(query);
					if(rs!=null){
						try {
							while(rs.next()){
								demtt++;
								i++;
								String spMa = rs.getString("spMa");
								String spTen = rs.getString("spTen");
								String spDonVi = rs.getString("spDonVi");
								String khoTen = rs.getString("khoTen");
								String schemekm = rs.getString("SCHEME");
								double SoLuong = rs.getDouble("SoLuong");
								double GiaSauCK = 0;// rs.getDouble("GiaMua");
								double DonGiaGoc = 0;//rs.getDouble("DonGiaGoc");
								double totalCK = 0;// rs.getDouble("TongCK");
								double thanhTien = 0;// SoLuong*(DonGiaGoc) ;
								double tienSauCK = 0;// SoLuong*(DonGiaGoc * (1 - totalCK / 100));
								double tienCK = 0;// thanhTien - tienSauCK;
								System.out.println("Thanhtien: "+thanhTien+" Tiensauck: " + tienSauCK);
		//						ckdh = rs.getDouble("chietkhau");
		//						sotiengiam = rs.getDouble("sotiengiam");
//								totalTienCK += tienCK;
//								totalle += thanhTien;
//							String[] arr = new String[] {Integer.toString(i), spMa, spTen, khoTen, formatter.format(SoLuong), spDonVi, 
//									formatter2.format(DonGiaGoc), formatter1.format(totalCK), "", formatter1.format(thanhTien)};
								String[] arr = new String[]{"" + i, spMa, spTen,  formatter1.format(SoLuong),
										spDonVi,"0", formatter1.format(DonGiaGoc), formatter1.format(totalCK) ,  formatter1.format(tienSauCK), 
										formatter1.format(thanhTien),schemekm};

								for (int j = 0; j < 11; j++) {
									cells_detail = new PdfPCell(new Paragraph(arr[j], font4));
									if (j == 2) {
										cells_detail.setHorizontalAlignment(Element.ALIGN_LEFT);
									}
									else {
										cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
									}
									cells_detail.setVerticalAlignment(Element.ALIGN_MIDDLE);
									table.addCell(cells_detail);	
								}	
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
						
						
/*
				
				//hang khuyen mai
				int demtt = 0;
				for (i = 0; i < spkmlist.size(); i++)
				{
					ISanpham sanpham = (ISanpham)spkmlist.get(i);
					double dongia = Double.parseDouble(sanpham.getDongia());
					double chietkhau = Double.parseDouble(sanpham.getSoluong()) * Double.parseDouble(sanpham.getDongia()) 
						/ 100 * Double.parseDouble(sanpham.getChietkhau());
					String sql_getkho = "\n select kho_fk, kho.ten from ctkhuyenmai " +
					"\n inner join kho on kho.pk_Seq = kho_fk " +
					"\n where scheme = '" + sanpham.getCTKM() + "'";
					rs = db.get(sql_getkho);
					String TenKho = "Khuyến mại";
					try
					{
						if (rs.next())
						{
							TenKho = rs.getString("ten");
						}
						rs.close();
					}
					catch (Exception er)
					{
						er.printStackTrace();
					}
					
					demtt = demtt + 1;
					String[] arr = new String[]{"" + demtt, sanpham.getMasanpham(), sanpham.getTensanpham(), TenKho, 
							sanpham.getSoluong(), sanpham.getDonvitinh(),formatter1.format(dongia*tileThueVat), formatter1.format(dongia), 
							formatter1.format(Double.parseDouble(sanpham.getChietkhau())), formatter.format(chietkhau), 
							formatter.format(Double.parseDouble(sanpham.getSoluong()) * Double.parseDouble(sanpham.getDongia()) - chietkhau )};

					for (int j = 0; j < 11; j++)
					{
						cells_detail = new PdfPCell(new Paragraph(arr[j], font4));
						if (j == 2) {
							cells_detail.setHorizontalAlignment(Element.ALIGN_LEFT);
						}
						else {
							cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
						}

						cells_detail.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cells_detail);	
					}
				}	
				*/

				tongtienkhuyenmai = 0;
				if (scheme_tongtien.size() > 0)
				{
					Enumeration<String> keys = scheme_tongtien.keys();
					while (keys.hasMoreElements())
					{
						String key = keys.nextElement();
						tongtienkhuyenmai = tongtienkhuyenmai + scheme_tongtien.get(key);
						String tongtien= Float.toString(scheme_tongtien.get(key));
						String sql_tenschem = "select diengiai, scheme from ctkhuyenmai where scheme = '" + key + "'";
						rs = db.get(sql_tenschem);
						String Tenscheme = "";
						String mascheme = "";
						try {
							if (rs.next()) {					
								Tenscheme = rs.getString("diengiai");
								mascheme = rs.getString("scheme");
							}
							rs.close();
						}
						catch (Exception er) {
							System.out.println("Exception 545: " + er.toString());
						}
						
						demtt = demtt + 1;
						String[] arr = new String[]{"" + demtt, key, Tenscheme,  "", "", "", "","", "", "-" + tongtien,mascheme};
						for (int j = 0; j < 11; j++)
						{
							cells_detail = new PdfPCell(new Paragraph(arr[j], font4));
							if (j == 2) {
								cells_detail.setHorizontalAlignment(Element.ALIGN_LEFT);
							}
							else{
								cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
							}

							cells_detail.setVerticalAlignment(Element.ALIGN_MIDDLE);
							table.addCell(cells_detail);
						}
					}
				}

				if (scheme_chietkhau.size() > 0)
				{
					Enumeration<String> keys = scheme_chietkhau.keys();
					while (keys.hasMoreElements())
					{
						String key = keys.nextElement();
						String sql_tenschem = "\n select diengiai,scheme from ctkhuyenmai where scheme = '" + key + "'";
						rs = db.get(sql_tenschem);
						String Tenschem = "";
						String mascheme = "";
						try {
							if (rs.next()) {						
								Tenschem = rs.getString("diengiai");	
								mascheme = rs.getString("scheme");
							}
							rs.close();
						}
						catch (Exception er) {
							er.printStackTrace();
						}

						tongtienkhuyenmai = tongtienkhuyenmai + scheme_chietkhau.get(key);
						String tienchietkhau = Float.toString(scheme_chietkhau.get(key));
						demtt = demtt + 1;
						String[] arr = new String[]{"" + demtt, key, Tenschem, "", "", "", "","", "", "-" + tienchietkhau, mascheme};
						for (int j = 0; j < 11; j++)
						{
							cells_detail = new PdfPCell(new Paragraph(arr[j], font4));
							if (j == 2) {
								cells_detail.setHorizontalAlignment(Element.ALIGN_LEFT);
							}
							else {
								cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
							}
							cells_detail.setVerticalAlignment(Element.ALIGN_MIDDLE);
							table.addCell(cells_detail);
						}
					}
				}
			
			
			
			PdfPCell cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			Paragraph km = new Paragraph("Tổng tiền trước VAT ", fontnomar);
			km.setAlignment(Element.ALIGN_RIGHT);
			cell11.addElement(km);
			cell11.setColspan(9);
			table.addCell(cell11);

			PdfPCell cell12 = new PdfPCell();
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			Paragraph tongtien = new Paragraph(formatter1.format(totalle/tileThueVat), fontnomar);
			tongtien.setAlignment(Element.ALIGN_CENTER);
			cell12.addElement(tongtien);
			table.addCell(cell12);

			PdfPCell cell13 = new PdfPCell();
			cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
			Paragraph ghichuHangKM = new Paragraph("");
			ghichuHangKM.setAlignment(Element.ALIGN_CENTER);
			cell13.addElement(ghichuHangKM);
			table.addCell(cell13);
			
			// Cộng thêm chiết khấu tổng đơn hàng
			totalTienCK += (totalle*(ckdh/100.0));
			///

			cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			km = new Paragraph("Cộng tiền hàng ", fontnomar);
			km.setAlignment(Element.ALIGN_RIGHT);
			cell11.addElement(km);
			cell11.setColspan(9);
			table.addCell(cell11);

			cell12 = new PdfPCell();
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			tongtien = new Paragraph(formatter1.format(totalle), fontnomar);
			tongtien.setAlignment(Element.ALIGN_CENTER);
			cell12.addElement(tongtien);
			table.addCell(cell12);
			
			cell13 = new PdfPCell();
			cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
			ghichuHangKM = new Paragraph("");
			ghichuHangKM.setAlignment(Element.ALIGN_CENTER);
			cell13.addElement(ghichuHangKM);
			table.addCell(cell13);
			
			// Cộng thêm chiết khấu tổng đơn hàng
			totalTienCK += (totalle*(ckdh/100.0));
			
			if (totalTienCK > 0)
			{
				cell11 = new PdfPCell();
				cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
				km = new Paragraph("Tiền chiết khấu ", fontnomar);
				km.setAlignment(Element.ALIGN_RIGHT);
				cell11.addElement(km);
				cell11.setColspan(9);
				table.addCell(cell11);

				cell12 = new PdfPCell();
				cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
				tongtien = new Paragraph( formatter1.format(totalTienCK), fontnomar);
				tongtien.setAlignment(Element.ALIGN_CENTER);
				cell12.addElement(tongtien);
				table.addCell(cell12);
				
				cell13 = new PdfPCell();
				cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
				ghichuHangKM = new Paragraph("");
				ghichuHangKM.setAlignment(Element.ALIGN_CENTER);
				cell13.addElement(ghichuHangKM);
				table.addCell(cell13);
			}
			
			if (sotiengiam > 0)
			{
				cell11 = new PdfPCell();
				cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
				km = new Paragraph("Số tiền giảm ", fontnomar);
				km.setAlignment(Element.ALIGN_RIGHT);
				cell11.addElement(km);
				cell11.setColspan(9);
				table.addCell(cell11);

				cell12 = new PdfPCell();
				cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
				tongtien = new Paragraph(formatter1.format(sotiengiam), fontnomar);
				tongtien.setAlignment(Element.ALIGN_CENTER);
				cell12.addElement(tongtien);
				table.addCell(cell12);
				
				cell13 = new PdfPCell();
				cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
				ghichuHangKM = new Paragraph("");
				ghichuHangKM.setAlignment(Element.ALIGN_CENTER);
				cell13.addElement(ghichuHangKM);
				table.addCell(cell13);
			}

			if (tongtienkhuyenmai > 0)
			{
				cell11 = new PdfPCell();
				cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
				km = new Paragraph("Tổng số tiền chiết khấu khuyến mại ", fontnomar);
				km.setAlignment(Element.ALIGN_RIGHT);
				cell11.addElement(km);
				cell11.setColspan(9);
				table.addCell(cell11);

				cell12 = new PdfPCell();
				cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
				tongtien = new Paragraph(formatter1.format(tongtienkhuyenmai) , fontnomar);
				tongtien.setAlignment(Element.ALIGN_CENTER);
				cell12.addElement(tongtien);
				table.addCell(cell12);
				
				cell13 = new PdfPCell();
				cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
				ghichuHangKM = new Paragraph("");
				ghichuHangKM.setAlignment(Element.ALIGN_CENTER);
				cell13.addElement(ghichuHangKM);
				table.addCell(cell13);
			}

			//
			cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_RIGHT);
			km = new Paragraph("Tổng tiền thanh toán(đã làm tròn) ", fontnomar);
			km.setAlignment(Element.ALIGN_RIGHT);
			cell11.addElement(km);
			cell11.setColspan(9);
			table.addCell(cell11);

			//double tongtiensauthue = totalle / 100 * 10 + totalle;
			double tongtiensauthue = totalle;
			cell12 = new PdfPCell();
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			tongtien = new Paragraph(formatter.format(tongtiensauthue - tongtienkhuyenmai-totalTienCK-sotiengiam) , fontnomar);
			tongtien.setAlignment(Element.ALIGN_CENTER);
			cell12.addElement(tongtien);
			table.addCell(cell12);
			
			cell13 = new PdfPCell();
			cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
			ghichuHangKM = new Paragraph("");
			ghichuHangKM.setAlignment(Element.ALIGN_CENTER);
			cell13.addElement(ghichuHangKM);
			table.addCell(cell13);

			//doc tien bang chu
			doctienrachu doctien = new doctienrachu();
			cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			Long tongtienst = Math.round(tongtiensauthue - tongtienkhuyenmai - totalTienCK - sotiengiam);
			System.out.println("[tongtiensauthue]: " + tongtiensauthue + "[tongtienkhuyenmai]: " + tongtienkhuyenmai);
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
	private void CreatePxk_donhangkhac(Document document, ServletOutputStream outstream, List<ISanpham> spList, 
			List<ISanpham> spkmlist, Hashtable<String, Float> scheme_tongtien, Hashtable<String, Float> scheme_chietkhau, 
			IDonhang dhBean, dbutils db) throws IOException
	{
		try
		{		
			String sql = "update donhang set trangthaiin = '1' where pk_seq = '"+ dhBean.getId() +"'";
			db.update(sql);
			
			String tennpp = "";
			String diachi = "";
			String dienthoai = "";
			String masothue = "";
			String ngaygiaohang = "";
			String ghichu = "";
			String ghichukhac = "";
			String query = "\n select isnull(dh.ghichu,'') as ghichukhac, isnull(dh.tylevat,1) as tylevat, isnull(gc.DIENGIAI,'')ghichu,isnull(dh.ngaygiaohang,'') ngaygiaohang, npp.ten, isnull(npp.diachi,'') as diachi, " +
							"\n isnull(npp.masothue,'') as masothue, isnull(npp.dienthoai,'') as dienthoai " +
							"\n from nhaphanphoi npp " +
							"\n inner join donhang dh on dh.npp_fk = npp.pk_seq" +
							"\n	left join ghichu gc on dh.ghichu_fk = gc.PK_SEQ " + 
							"\n where dh.pk_seq = " + dhBean.getId();
			
			ResultSet rs = db.get(query);
			try
			{
				while (rs.next()) {
					ngaygiaohang = rs.getString("ngaygiaohang");
					tennpp = rs.getString("ten");
					diachi = rs.getString("diachi");
					dienthoai = rs.getString("dienthoai");
					masothue = rs.getString("masothue");
					ghichu = rs.getString("ghichu");
					ghichukhac = rs.getString("ghichukhac");
				}
				rs.close();
			}
			catch (Exception er) {
				er.printStackTrace();
			}
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

			Paragraph pxk = new Paragraph("PHIẾU GIAO HÀNG", font);
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
			
			String ngay = dhBean.getNgaygiaodich().substring(8, 10)+ "-" + dhBean.getNgaygiaodich().substring(5, 7) 
				+ "-" + dhBean.getNgaygiaodich().substring(0, 4);
			String ngaygh = "";
			if(ngaygiaohang.trim().length() >0){
				ngaygh = ngaygiaohang.substring(8, 10)+ "-" + ngaygiaohang.substring(5, 7) + "-" + ngaygiaohang.substring(0, 4);
			}else {
				ngaygh="";
			}
			
			celltieude.addElement(new Paragraph("Số chứng từ: " + dhBean.getId() + " - Ngày chứng từ: " + ngay + ", 			 Ngày giao hàng: " + ngaygh, fontnomar));
	
			
//			celltieude.addElement(new Paragraph("Số chứng từ: " + dhBean.getId() + " - Ngày chứng từ: " + ngay, fontnomar));
//			celltieude.addElement(new Paragraph("Ngày giao hàng: " + ngaygiaohang, fontnomar));

			//celltieude.addElement(new Paragraph("Ngày chứng từ: " + ngay,fontnomar));
			tableheader.addCell(celltieude);

			//Add bang vao document
			document.add(tableheader);						

			PdfPTable table_info = new PdfPTable(1);
			float[] with3 = {50.0f};//SET DO RONG CAC COT
			table_info.setWidthPercentage(100);
			table_info.setWidths(with3);
			PdfPCell cell111= new PdfPCell();

			String tenkh = "";
			String diachikh = "";
			String dienthoaikh = "";
			String masothuekh = "";
			String quanhuyen = "";
			String tinhthanh = "";
			String phuongxa = "";
			String sql_getinfokh = "\n select khachhang.ten,isnull(khachhang.dienthoai,'') as dienthoai, isnull(khachhang.diachi,'') as diachi, " +
			"\n isnull(q.TEN,'') as quan, isnull(t.TEN,'') as tinhthanh, isnull(px.ten,'') as phuongxa, isnull(khachhang.masothue,'') as masothue " +
			"\n from khachhang " +
			"\n left join QUANHUYEN q ON q.pk_seq = khachhang.quanhuyen_fk " +
			"\n left join TINHTHANH t on t.pk_seq = khachhang.tinhthanh_fk " +
			"\n left join phuongxa px on px.Pk_seq = khachhang.phuongxa_fk " +
			"\n where khachhang.pk_seq = " + dhBean.getKhId();
			System.out.println("sql_getinfokh: " + sql_getinfokh);
			ResultSet rs_kh = db.get(sql_getinfokh);
			try {
				if (rs_kh.next()) {
					tenkh = rs_kh.getString("ten");
					diachikh = rs_kh.getString("diachi");
					dienthoaikh = rs_kh.getString("dienthoai");
					masothuekh = rs_kh.getString("masothue");
					quanhuyen = rs_kh.getString("quan");
					tinhthanh = rs_kh.getString("tinhthanh");
					phuongxa = rs_kh.getString("phuongxa");
				}
				rs_kh.close();
			}
			catch (Exception er )
			{
				er.printStackTrace();
				System.out.println("Lỗi ngoại lệ lấy dữ liệu KH: " + er.toString());
			}
			
			//lay dai dien kinh doanh
			String ddkdten="";
			String dienthoaiDDKD = "";
			String sql_getddkd = "\n select pk_seq, ten, isnull(dienthoai, '') as dienthoai " +
			"\n from daidienkinhdoanh where pk_seq = " + dhBean.getDdkdId();
			ResultSet rs_getddkd = db.get(sql_getddkd);
			System.out.println("sql_getddkd: " + sql_getddkd);
			try
			{
				if (rs_getddkd.next())
				{
					ddkdten = rs_getddkd.getString("ten");
					dienthoaiDDKD = rs_getddkd.getString("dienthoai");
				}
				rs_getddkd.close();
			}
			catch (Exception er ) {
				er.printStackTrace();
				System.out.println("Lỗi ngoại lệ lấy dữ liệu DDKD: " + er.toString());
			}

			String ddkd = "Đại diện kinh doanh: " + ddkdten;
			if (dienthoaiDDKD.trim().equals("NA"))
				dienthoaiDDKD = "";
			
			if (dienthoaiDDKD.trim().equals("N/A"))
				dienthoaiDDKD = "";
			
			if (dienthoaiDDKD.length() > 0)
				ddkd += " (ĐT:" + dienthoaiDDKD + ")";

			cell111.addElement(new Paragraph(ddkd, fontnomar));

			String dd_khachhang = "Mã khách hàng: " + dhBean.getSmartId() + "     Tên khách hàng : " + tenkh ;
			if (dienthoaikh.length() > 0)
				dd_khachhang += " (" + dienthoaikh + ")";
			cell111.addElement(new Paragraph(dd_khachhang ,fontnomar));
			
			if (phuongxa.length() != 0)
				phuongxa = ", phường "+phuongxa;
			
			if (quanhuyen.length() != 0)
				quanhuyen = ", quận " +quanhuyen;
			
			if (tinhthanh.length() != 0)
				tinhthanh = ", Tỉnh/Tp "+tinhthanh;
			
			cell111.addElement(new Paragraph("Địa chỉ: " + diachikh + phuongxa + quanhuyen + tinhthanh, fontnomar));
			cell111.addElement(new Paragraph("Ghi chú: " + ghichu,fontnomar));
			cell111.addElement(new Paragraph("Ghi chú khác: " + ghichukhac,fontnomar));
			cell111.setPaddingBottom(10);
			table_info.addCell(cell111);
			document.add(table_info);
			
			query = "select tyleVAT,isnull(donhangkhac,0) as donhangkhac from donhang where pk_seq = " +dhBean.getId();
			System.out.println("lay tilevat: " + query);
			double tileThueVat = 1;
			int donhangkhac = 0;
			rs = db.get(query);
			try {
				if(rs.next()){
					tileThueVat = rs.getDouble("tylevat");
					donhangkhac = rs.getInt("donhangkhac");
				}
				rs.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			System.out.println("tylevat: " + tileThueVat);
			
			//donhang combo donhangkhac =2 thi dùng form khac, donhangkhac =1,0 thì dùng form bình thường
			PdfPTable table = new PdfPTable(11);
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
				float[] withs = {4.0f, 25.0f, 6.0f, 8.0f, 4.0f, 8.0f, 8.0f, 9.0f};
				table.setWidths(withs);
				String[] th = new String[]{"STT", "Tên hàng", "Kho", "Số lượng", "ĐVT", "Đơn giá", "%CK", "Thành tiền"};
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
				totalle = 0;
				query = "\n select d.ten as khoTen,'Combo' as spDonVi, a.tenSpComBo as spTen, " +
						"\n a.soluongSpComBo as SoLuong, a.giaSpComBo as GiaMua, 0 as ckDH, 0 as ckDLN, " +
						"\n 0 as TongCK, isnull(chietkhau,0) as chietkhau, isnull(tyleVAT,1)   as tyleVAT, " +
						"\n   isnull(Sotiengiam,0)   as sotiengiam "+
						"\n from donhang a " +
						"\n INNER JOIN Kho d on d.pk_Seq = a.Kho_FK " +
						"\n where a.pk_seq = '" + dhBean.getId() + "' and donhangkhac='2' ";
				
				System.out.println("IN DH: " + query);
				rs = db.get(query);
				int i = 0;
				totalTienCK = 0;
				sotiengiam = 0;
				ckdh = 0;
				try
				{
					while (rs.next())
					{
						i++;
//						String spMa = rs.getString("spMa");
						String spTen = rs.getString("spTen");
						String spDonVi = rs.getString("spDonVi");
						String khoTen = rs.getString("khoTen");
						double SoLuong = rs.getDouble("SoLuong");
						double GiaSauCK = rs.getDouble("GiaMua");
						double DonGiaGoc = 0;//rs.getDouble("DonGiaGoc");
						double totalCK = rs.getDouble("TongCK");
						double thanhTien = SoLuong*(GiaSauCK) ;
						double tienSauCK = 0;//  SoLuong*(DonGiaGoc * (1 - totalCK / 100));
						double tienCK = 0;//thanhTien - tienSauCK;
						System.out.println("Thanhtien: "+thanhTien+" Tiensauck: " + tienSauCK);
						ckdh = rs.getDouble("chietkhau");
						double tylevat = rs.getDouble("tyleVat");
						sotiengiam = rs.getDouble("sotiengiam");
						totalTienCK += 0;// tienCK;
						totalle += thanhTien;
						String[] arr = new String[] {Integer.toString(i), spTen, khoTen, formatter.format(SoLuong), spDonVi, formatter2.format(GiaSauCK),
								 formatter1.format(totalCK), formatter1.format(thanhTien)};
						for (int j = 0; j < 8; j++)
						{
							cells_detail = new PdfPCell(new Paragraph(arr[j], font4));
							if (j == 1)
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
			Paragraph km = new Paragraph("Tổng tiền trước VAT ", fontnomar);
			km.setAlignment(Element.ALIGN_RIGHT);
			cell11.addElement(km);
			cell11.setColspan(7);
			table.addCell(cell11);

			PdfPCell cell12 = new PdfPCell();
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			Paragraph tongtien = new Paragraph(formatter1.format(totalle/tileThueVat), fontnomar);
			tongtien.setAlignment(Element.ALIGN_CENTER);
			cell12.addElement(tongtien);
			table.addCell(cell12);
			// Cộng thêm chiết khấu tổng đơn hàng
//			totalTienCK += (totalle*(ckdh/100.0));
			totalTienCK += 0;
			///

			cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			km = new Paragraph("Cộng tiền hàng ", fontnomar);
			km.setAlignment(Element.ALIGN_RIGHT);
			cell11.addElement(km);
			cell11.setColspan(7);
			table.addCell(cell11);

			cell12 = new PdfPCell();
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			tongtien = new Paragraph(formatter1.format(totalle), fontnomar);
			tongtien.setAlignment(Element.ALIGN_CENTER);
			cell12.addElement(tongtien);
			table.addCell(cell12);
			// Cộng thêm chiết khấu tổng đơn hàng
//			totalTienCK += (totalle*(ckdh/100.0));
			totalTienCK += 0;
			if (totalTienCK > 0)
			{
				cell11 = new PdfPCell();
				cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
				km = new Paragraph("Tiền chiết khấu ", fontnomar);
				km.setAlignment(Element.ALIGN_RIGHT);
				cell11.addElement(km);
				cell11.setColspan(7);
				table.addCell(cell11);

				cell12 = new PdfPCell();
				cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
				tongtien = new Paragraph( formatter1.format(totalTienCK), fontnomar);
				tongtien.setAlignment(Element.ALIGN_CENTER);
				cell12.addElement(tongtien);
				table.addCell(cell12);
			}
			
			if (sotiengiam > 0)
			{
				cell11 = new PdfPCell();
				cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
				km = new Paragraph("Số tiền giảm ", fontnomar);
				km.setAlignment(Element.ALIGN_RIGHT);
				cell11.addElement(km);
				cell11.setColspan(7);
				table.addCell(cell11);

				cell12 = new PdfPCell();
				cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
				tongtien = new Paragraph(formatter1.format(sotiengiam), fontnomar);
				tongtien.setAlignment(Element.ALIGN_CENTER);
				cell12.addElement(tongtien);
				table.addCell(cell12);
			}

			if (tongtienkhuyenmai > 0)
			{
				cell11 = new PdfPCell();
				cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
				km = new Paragraph("Tổng số tiền chiết khấu khuyến mại ", fontnomar);
				km.setAlignment(Element.ALIGN_RIGHT);
				cell11.addElement(km);
				cell11.setColspan(7);
				table.addCell(cell11);

				cell12 = new PdfPCell();
				cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
				tongtien = new Paragraph(formatter1.format(tongtienkhuyenmai) , fontnomar);
				tongtien.setAlignment(Element.ALIGN_CENTER);
				cell12.addElement(tongtien);
				table.addCell(cell12);
			}

			//
			cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_RIGHT);
			km = new Paragraph("Tổng tiền thanh toán(đã làm tròn) ", fontnomar);
			km.setAlignment(Element.ALIGN_RIGHT);
			cell11.addElement(km);
			cell11.setColspan(7);
			table.addCell(cell11);

			//double tongtiensauthue = totalle / 100 * 10 + totalle;
			double tongtiensauthue = totalle;
			cell12 = new PdfPCell();
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			tongtien = new Paragraph(formatter.format(tongtiensauthue - tongtienkhuyenmai-totalTienCK-sotiengiam) , fontnomar);
			tongtien.setAlignment(Element.ALIGN_RIGHT);
			cell12.addElement(tongtien);
			table.addCell(cell12);

			//doc tien bang chu
			doctienrachu doctien = new doctienrachu();
			cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			System.out.println("tongtiensauthue" + tongtiensauthue);
			System.out.println("tongtienkhuyenmai" + tongtienkhuyenmai);
			System.out.println("totalTienCK" + totalTienCK);
			System.out.println("sotiengiam" + sotiengiam);
			Long tongtienst = Math.round(tongtiensauthue - tongtienkhuyenmai - totalTienCK - sotiengiam);
			System.out.println("[tongtiensauthue]: " + tongtiensauthue + "[tongtienkhuyenmai]: " + tongtienkhuyenmai);
			System.out.println("tongtienst:"+tongtienst);
			String tien_chu = doctien.tranlate(String.valueOf(tongtienst));
			tien_chu = tien_chu.substring(0, 1).toUpperCase() + tien_chu.substring(1);
			km = new Paragraph("Số tiền bằng chữ: " + tien_chu, fontnomar);
			km.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(km);
			cell11.setColspan(8);
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

			para.setAlignment(Element.ALIGN_CENTER);
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell11.addElement(para);
			cell11.setBorderWidthLeft(0);
			cell11.setBorder(0);
			tablefooter.addCell(para);
			
			document.add(tablefooter);
			document.close(); 
		}
		catch (DocumentException e)
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
