package geso.dms.distributor.servlets.xuatkho;

import geso.dms.center.beans.doctien.doctienrachu;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

@WebServlet("/XuatKhoPdfSvl")
public class XuatKhoPdfSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public XuatKhoPdfSvl()
	{
		super();

	}

	float CONVERT = 28.346457f; // = 1cm
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");

		Utility util = new Utility();
		
		if (userId.length() == 0)
			userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));

		String querystring = request.getQueryString();
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
		
		if(type.equals("XK"))
		{
			this.XuatKhoPdf(document, outstream, id );			
		}else if(type.equals("XHH"))
		{
			this.XuatHangHongPdf(document, outstream, id );
		}else if(type.equals("XKK"))
		{
			this.XuatKhoKhacPdf(document, outstream, id );
		}else if(type.equals("DonBanNPP"))
		{
			 DonBanHangNpp( document,  outstream,id) ;
		}
		else 
		{
			this.DonTraHangPdf(document, outstream, id );	
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
		
		if(type.equals("XK"))
		{
			this.XuatKhoPdf(document, outstream, id );			
		}else if(type.equals("XHH"))
		{
			this.XuatHangHongPdf(document, outstream, id );
		}else if(type.equals("XKK"))
		{
			this.XuatKhoKhacPdf(document, outstream, id );
		}
		else 
		{
			this.DonTraHangPdf(document, outstream, id );	
		}
		

	}
	private void XuatKhoKhacPdf(Document document, ServletOutputStream outstream, String id)
	{
		try
		{	
			dbutils db = new dbutils();
			document.setPageSize(PageSize.A4);
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
			
			PdfPTable table = new PdfPTable(1);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withsheader = {300.0f};
			table.setWidths(withsheader); 
			
			
			
			PdfPCell cell = new PdfPCell(new Paragraph("XUẤT KHO KHÁC", header));
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

			String sql=
				"	select a.ghichu,b.ten as khoten,c.ten as kbhten,d.ten as nppten,d.ma as nppma,a.ngayxuat  "+
				"	from XUATKHOKHAC a   "+
				"		inner join kho b on b.pk_seq=a.kho_fk  "+
				"		inner join kenhbanhang c on c.pk_seq=a.kbh_fk "+
				"		inner join nhaphanphoi d on d.pk_seq=a.npp_fk  "+
				"	where a.pk_seq='"+id+"'	";
						
						
			ResultSet rs = db.get(sql);
			try
			{
				if(rs.next())
				{
					cell = new PdfPCell(new Paragraph("Cửa hàng:", bold));cell.setBorder(0);table.addCell(cell);
					cell = new PdfPCell(new Paragraph(rs.getString("nppTen").toUpperCase(), normal));cell.setBorder(0);table.addCell(cell);
					
					cell = new PdfPCell(new Paragraph("Kho xuất:", bold));cell.setBorder(0);table.addCell(cell);
					cell = new PdfPCell(new Paragraph(rs.getString("khoten"), normal));cell.setBorder(0);table.addCell(cell);
					
					cell = new PdfPCell(new Paragraph("Ngày xuất:", bold));cell.setBorder(0);table.addCell(cell);
					cell = new PdfPCell(new Paragraph(rs.getString("ngayxuat"), normal));cell.setBorder(0);table.addCell(cell);
					
					cell = new PdfPCell(new Paragraph("Ghi chú:", bold));cell.setBorder(0);table.addCell(cell);
					cell = new PdfPCell(new Paragraph(rs.getString("ghichu"), normal));cell.setBorder(0);table.addCell(cell);
					
				
				}
				rs.close();
			}
			catch(Exception er )
			{
				er.printStackTrace();
			}
			document.add(table);
			
			//Table Content
			float[]	with= {5.0f, 10.0f, 30.0f, 11.0f, 13.0f, 13.0f,10.0f}; 	
			table = new PdfPTable(with.length);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
	    table.setWidths(with);
	     
			String[] th = new String[]{ "STT", "Mã sản phẩm", "Tên sản phẩm","Đơn vị tinh", "Đơn giá","Số lượng", "Thành tiền"};
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
			"	select b.ma as spma,b.ten as spten ,a.soluongchuan as SoLuong,c.donvi,a.DonGia,a.ThanhTien  "+
			"		from   "+
			"		XUATKHOKHAC_sanpham  a  "+ 
			"		inner join sanpham b on b.pk_seq=a.sanpham_fk  "+
			"			inner join donvidoluong c on c.pk_seq=a.dvdl_fk "+
			"	 where a.XUATKHOKHAC_FK='"+id+"'";   
			 rs = db.get(query);
			 PdfPCell cellSP = null;
				int stt = 1;
				double totalSoLuong=0;
				double totalThanhTien=0;
				NumberFormat format = new DecimalFormat("#,###,###");
				while(rs.next())
				{
					double soluong = rs.getDouble("SOLUONG");
					double dongia = rs.getDouble("DONGIA");
					totalSoLuong+=soluong;
					totalThanhTien+=soluong*dongia;
					
					cellSP = new PdfPCell(new Paragraph(Integer.toString(stt), normal));
					cellSP.setPadding(3.0f);
					table.addCell(cellSP);		
					
					cellSP = new PdfPCell(new Paragraph(rs.getString("spMa"), normal));
					cellSP.setPadding(3.0f);
					table.addCell(cellSP);	
					
					cellSP = new PdfPCell(new Paragraph(rs.getString("spTen"), normal));
					cellSP.setPadding(3.0f);
					table.addCell(cellSP);	
					
					cellSP = new PdfPCell(new Paragraph(rs.getString("donvi"), normal));
					cellSP.setPadding(3.0f);
					table.addCell(cellSP);
					
				
					
					cellSP = new PdfPCell(new Paragraph(format.format(dongia) , normal));
					cellSP.setPadding(3.0f);
					cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cellSP);	
					
					cellSP = new PdfPCell(new Paragraph(format.format(soluong) , normal));
					cellSP.setPadding(3.0f);
					cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cellSP);	
					
					
					cellSP = new PdfPCell(new Paragraph(format.format(soluong * dongia) , normal));
					cellSP.setPadding(3.0f);
					cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cellSP);	
					stt++;
				}
				
				cellSP = new PdfPCell(new Paragraph("Tổng cộng", bold));
				cellSP.setColspan(5);
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);		
				
				cellSP = new PdfPCell(new Paragraph(format.format(totalSoLuong), normal));
				cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cellSP.setPadding(3.0f);
				cellSP.setColspan(1);
				table.addCell(cellSP);
				
				cellSP = new PdfPCell(new Paragraph(format.format(totalThanhTien), normal));
				cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);
				
				
				document.add(table);
				rs.close();
			document.close(); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	private void XuatHangHongPdf(Document document, ServletOutputStream outstream, String id)
	{
		try
		{	
			dbutils db = new dbutils();
			document.setPageSize(PageSize.A4);
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
			
			PdfPTable table = new PdfPTable(1);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withsheader = {300.0f};
			table.setWidths(withsheader); 
			
			
			
			PdfPCell cell = new PdfPCell(new Paragraph("XUẤT HÀNG HỎNG", header));
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

			String sql=
				"	select a.ghichu,b.ten as khoten,c.ten as kbhten,d.ten as nppten,d.ma as nppma,a.ngayxuat  "+
				"	from xuathanghong a   "+
				"		inner join kho b on b.pk_seq=a.kho_fk  "+
				"		inner join kenhbanhang c on c.pk_seq=a.kbh_fk "+
				"		inner join nhaphanphoi d on d.pk_seq=a.npp_fk  "+
				"	where a.pk_seq='"+id+"'	";
						
						
			ResultSet rs = db.get(sql);
			try
			{
				if(rs.next())
				{
					cell = new PdfPCell(new Paragraph("Cửa hàng:", bold));cell.setBorder(0);table.addCell(cell);
					cell = new PdfPCell(new Paragraph(rs.getString("nppTen").toUpperCase(), normal));cell.setBorder(0);table.addCell(cell);
					
					cell = new PdfPCell(new Paragraph("Kho xuất:", bold));cell.setBorder(0);table.addCell(cell);
					cell = new PdfPCell(new Paragraph(rs.getString("khoten"), normal));cell.setBorder(0);table.addCell(cell);
					
					cell = new PdfPCell(new Paragraph("Ngày xuất:", bold));cell.setBorder(0);table.addCell(cell);
					cell = new PdfPCell(new Paragraph(rs.getString("ngayxuat"), normal));cell.setBorder(0);table.addCell(cell);
					
					cell = new PdfPCell(new Paragraph("Ghi chú:", bold));cell.setBorder(0);table.addCell(cell);
					cell = new PdfPCell(new Paragraph(rs.getString("ghichu"), normal));cell.setBorder(0);table.addCell(cell);
					
				
				}
				rs.close();
			}
			catch(Exception er )
			{
				er.printStackTrace();
			}
			document.add(table);
			
			//Table Content
			float[]	with= {5.0f, 10.0f, 30.0f, 11.0f, 13.0f, 13.0f,10.0f}; 	
			table = new PdfPTable(with.length);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
	    table.setWidths(with);
	     
			String[] th = new String[]{ "STT", "Mã sản phẩm", "Tên sản phẩm","Đơn vị tinh", "Đơn giá","Số lượng", "Thành tiền"};
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
			"	select b.ma as spma,b.ten as spten ,a.soluongchuan as SoLuong,c.donvi,a.DonGia,a.ThanhTien  "+
			"		from   "+
			"		xuathanghong_sanpham  a  "+ 
			"		inner join sanpham b on b.pk_seq=a.sanpham_fk  "+
			"			inner join donvidoluong c on c.pk_seq=a.dvdl_fk "+
			"	 where a.XUATHANGHONG_FK='"+id+"'";   
			 rs = db.get(query);
			 PdfPCell cellSP = null;
				int stt = 1;
				double totalSoLuong=0;
				double totalThanhTien=0;
				NumberFormat format = new DecimalFormat("#,###,###");
				while(rs.next())
				{
					double soluong = rs.getDouble("SOLUONG");
					double dongia = rs.getDouble("DONGIA");
					totalSoLuong+=soluong;
					totalThanhTien+=soluong*dongia;
					
					cellSP = new PdfPCell(new Paragraph(Integer.toString(stt), normal));
					cellSP.setPadding(3.0f);
					table.addCell(cellSP);		
					
					cellSP = new PdfPCell(new Paragraph(rs.getString("spMa"), normal));
					cellSP.setPadding(3.0f);
					table.addCell(cellSP);	
					
					cellSP = new PdfPCell(new Paragraph(rs.getString("spTen"), normal));
					cellSP.setPadding(3.0f);
					table.addCell(cellSP);	
					
					cellSP = new PdfPCell(new Paragraph(rs.getString("donvi"), normal));
					cellSP.setPadding(3.0f);
					table.addCell(cellSP);
					
				
					
					cellSP = new PdfPCell(new Paragraph(format.format(dongia) , normal));
					cellSP.setPadding(3.0f);
					cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cellSP);	
					
					cellSP = new PdfPCell(new Paragraph(format.format(soluong) , normal));
					cellSP.setPadding(3.0f);
					cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cellSP);	
					
					
					cellSP = new PdfPCell(new Paragraph(format.format(soluong * dongia) , normal));
					cellSP.setPadding(3.0f);
					cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cellSP);	
					stt++;
				}
				
				cellSP = new PdfPCell(new Paragraph("Tổng cộng", bold));
				cellSP.setColspan(5);
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);		
				
				cellSP = new PdfPCell(new Paragraph(format.format(totalSoLuong), normal));
				cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cellSP.setPadding(3.0f);
				cellSP.setColspan(1);
				table.addCell(cellSP);
				
				cellSP = new PdfPCell(new Paragraph(format.format(totalThanhTien), normal));
				cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);
				
				
				document.add(table);
				rs.close();
			document.close(); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	private void XuatKhoPdf(Document document, ServletOutputStream outstream, String id)
	{
		try
		{	
			dbutils db = new dbutils();
			document.setPageSize(PageSize.A4);
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
			
			PdfPTable table = new PdfPTable(1);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withsheader = {300.0f};
			table.setWidths(withsheader); 
			
			
			
			PdfPCell cell = new PdfPCell(new Paragraph("XUẤT KHO VỀ CÔNG TY", header));
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

			String sql=
			"	select a.PK_SEQ as xkId,a.NGAYXUAT,b.MA as nppMa,b.TEN as nppTen,b.DIACHI as nppDiaChi,  "+
			"		c.DIENGIAI as kbhTen,d.DIENGIAI as dvkdTen,e.TEN as khoXuat,f.TEN as khoNhan "+
			"	from XUATKHO a  "+
			"		inner join NHAPHANPHOI b on b.PK_SEQ=a.NPP_FK "+
			"		inner join KENHBANHANG c on c.PK_SEQ=a.KBH_FK  "+
			"		inner join DONVIKINHDOANH d on d.PK_SEQ=a.DVKD_FK "+
			"		inner join KHO e on e.PK_SEQ=a.KHO_FK "+
			"		inner join ERP_KHOTT f on f.PK_SEQ=a.KHOTT_FK " +
			"   where a.pk_seq='"+id+"'     ";
			ResultSet rs = db.get(sql);
			try
			{
				if(rs.next())
				{
					cell = new PdfPCell(new Paragraph("Cửa hàng:", bold));cell.setBorder(0);table.addCell(cell);
					cell = new PdfPCell(new Paragraph(rs.getString("nppTen").toUpperCase(), normal));cell.setBorder(0);table.addCell(cell);
					
					cell = new PdfPCell(new Paragraph("Đơn vị kinh doanh :", bold));cell.setBorder(0);table.addCell(cell);
					cell = new PdfPCell(new Paragraph(rs.getString("dvkdTen").toUpperCase(), normal));cell.setBorder(0);table.addCell(cell);
					
					
					cell = new PdfPCell(new Paragraph("Kênh bán hàng :", bold));cell.setBorder(0);table.addCell(cell);
					cell = new PdfPCell(new Paragraph(rs.getString("kbhTen").toUpperCase(), normal));cell.setBorder(0);table.addCell(cell);
					
					cell = new PdfPCell(new Paragraph("Kho xuất:", bold));cell.setBorder(0);table.addCell(cell);
					cell = new PdfPCell(new Paragraph(rs.getString("khoXuat"), normal));cell.setBorder(0);table.addCell(cell);
					
					cell = new PdfPCell(new Paragraph("Kho nhận:", bold));cell.setBorder(0);table.addCell(cell);
					cell = new PdfPCell(new Paragraph(rs.getString("khoNhan"), normal));cell.setBorder(0);table.addCell(cell);
				
				}
				rs.close();
			}
			catch(Exception er )
			{
				er.printStackTrace();
			}
			document.add(table);
			
			//Table Content
			float[]	with= {5.0f, 10.0f, 30.0f, 11.0f, 13.0f, 13.0f,10.0f}; 	
			table = new PdfPTable(with.length);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
	    table.setWidths(with);
	     
			String[] th = new String[]{ "STT", "Mã sản phẩm", "Tên sản phẩm","Đơn vị tinh", "Số lượng", "Đơn giá", "Thành tiền"};
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
			"	select b.MA  as spMa,b.ten as spTen,a.SOLUONG,a.DONGIA,a.SOLUONG*a.DONGIA as ThanhTien,c.donvi  "+ 
			"	 from XUATKHO_SANPHAM a inner join SANPHAM b on b.PK_SEQ=a.SANPHAM_FK    "+
			"	inner join DONVIDOLUONG c on c.PK_SEQ=b.DVDL_FK   "+
			"	 where a.XUATKHO_FK='"+id+"'";   
			 rs = db.get(query);
			 PdfPCell cellSP = null;
				int stt = 1;
				double totalSoLuong=0;
				double totalThanhTien=0;
				NumberFormat format = new DecimalFormat("#,###,###");
				while(rs.next())
				{
					double soluong = rs.getDouble("SOLUONG");
					double dongia = rs.getDouble("DONGIA");
					totalSoLuong+=soluong;
					totalThanhTien+=soluong*dongia;
					
					cellSP = new PdfPCell(new Paragraph(Integer.toString(stt), normal));
					cellSP.setPadding(3.0f);
					table.addCell(cellSP);		
					
					cellSP = new PdfPCell(new Paragraph(rs.getString("spMa"), normal));
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
					
					cellSP = new PdfPCell(new Paragraph(format.format(dongia) , normal));
					cellSP.setPadding(3.0f);
					cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cellSP);	
					
					cellSP = new PdfPCell(new Paragraph(format.format(soluong * dongia) , normal));
					cellSP.setPadding(3.0f);
					cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cellSP);	
					stt++;
				}
				
				cellSP = new PdfPCell(new Paragraph("Tổng cộng", bold));
				cellSP.setColspan(6);
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);		
				
				cellSP = new PdfPCell(new Paragraph(format.format(totalThanhTien), normal));
				cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);
				
				
				document.add(table);
				rs.close();
			document.close(); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void DonTraHangPdf(Document document, ServletOutputStream outstream, String id)
	{
		try
		{	
			dbutils db = new dbutils();
			document.setPageSize(PageSize.A4);
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
			
			PdfPTable table = new PdfPTable(1);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withsheader = {300.0f};
			table.setWidths(withsheader); 
			
			
			
			PdfPCell cell = new PdfPCell(new Paragraph("ĐƠN TRẢ HÀNG ", header));
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

			String sql=
				"	select c.TEN as nppTen,c.MA as nppMa,c.DIACHI as nppDiaChi,c.DIENTHOAI as nppDienThoai  "+
				"		,b.SMARTID,b.TEN as khTen,b.DIACHI as khDiaChi,d.TEN as ddkdTen,d.DIENTHOAI as ddkdDienThoai,a.NGAYNHAP  "+  
				"	from DONHANGTRAVE a inner join KHACHHANG b on b.PK_SEQ=a.KHACHHANG_FK  "+
				"	inner join NHAPHANPHOI c on c.PK_SEQ=a.NPP_FK  "+
				"	inner join DAIDIENKINHDOANH d on d.PK_SEQ=a.DDKD_FK " +
				" where a.pk_seq='"+id+"' ";
			ResultSet rs = db.get(sql);
			
			try
			{
				if(rs.next())
				{
					cell = new PdfPCell(new Paragraph("Tên nhà phân phối :", bold));cell.setBorder(0);table.addCell(cell);
					cell = new PdfPCell(new Paragraph(rs.getString("nppTen").toUpperCase(), normal));cell.setBorder(0);table.addCell(cell);
					
					cell = new PdfPCell(new Paragraph("Tên nhân viên bán hàng :", bold));cell.setBorder(0);table.addCell(cell);
					cell = new PdfPCell(new Paragraph(rs.getString("ddkdTen").toUpperCase(), normal));cell.setBorder(0);table.addCell(cell);
					
					
					cell = new PdfPCell(new Paragraph("Tên khách hàng :", bold));cell.setBorder(0);table.addCell(cell);
					cell = new PdfPCell(new Paragraph(rs.getString("khTen").toUpperCase(), normal));cell.setBorder(0);table.addCell(cell);
					
					cell = new PdfPCell(new Paragraph("Địa chỉ:", bold));cell.setBorder(0);table.addCell(cell);
					cell = new PdfPCell(new Paragraph(rs.getString("khDiaChi"), normal));cell.setBorder(0);table.addCell(cell);
				
				}
				rs.close();
			}
			catch(Exception er )
			{
				er.printStackTrace();
			}
			document.add(table);
			
			//Table Content
			float[]	with= {5.0f, 10.0f, 30.0f, 11.0f, 13.0f, 13.0f,10.0f}; 	
			table = new PdfPTable(with.length);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
	    table.setWidths(with);
	     
			String[] th = new String[]{ "STT", "Mã sản phẩm", "Tên sản phẩm","Đơn vị tinh", "Số lượng", "Đơn giá", "Thành tiền"};
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
			"select b.MA  as spMa,b.ten as spTen,a.SOLUONG,a.GIAMUA,a.SOLUONG*a.GIAMUA as ThanhTien,c.donvi "+
			" from DONHANGTRAVE_SANPHAM a inner join SANPHAM b on b.PK_SEQ=a.SANPHAM_FK  "+
			"inner join DONVIDOLUONG c on c.PK_SEQ=b.DVDL_FK " +
			" where a.DONHANGTRAVE_FK='"+id+"'   ";
			
			System.out.println("[SQL]"+query);
			
			 rs = db.get(query);
			 PdfPCell cellSP = null;
				int stt = 1;
				double totalSoLuong=0;
				double totalThanhTien=0;
				NumberFormat format = new DecimalFormat("#,###,###");
				while(rs.next())
				{
					double soluong = rs.getDouble("SOLUONG");
					double dongia = rs.getDouble("GIAMUA");
					totalSoLuong+=soluong;
					totalThanhTien+=soluong*dongia;
					
					cellSP = new PdfPCell(new Paragraph(Integer.toString(stt), normal));
					cellSP.setPadding(3.0f);
					table.addCell(cellSP);		
					
					cellSP = new PdfPCell(new Paragraph(rs.getString("spMa"), normal));
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
					
					cellSP = new PdfPCell(new Paragraph(format.format(dongia) , normal));
					cellSP.setPadding(3.0f);
					cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cellSP);	
					
					cellSP = new PdfPCell(new Paragraph(format.format(soluong * dongia) , normal));
					cellSP.setPadding(3.0f);
					cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cellSP);	
					stt++;
				}
				
				cellSP = new PdfPCell(new Paragraph("Tổng cộng", bold));
				cellSP.setColspan(6);
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);		
				
				cellSP = new PdfPCell(new Paragraph(format.format(totalThanhTien), normal));
				cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);
				
				
				document.add(table);
				rs.close();
			document.close(); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void DonBanHangNpp(Document document, ServletOutputStream outstream, String id) throws IOException
	{
		try
		{	
			dbutils db = new dbutils();
			String tennpp = "";
			String diachinpp = "";
			String dienthoainpp = "";
			String manpp = "";
			String tenkh="";
			String diachikh="";
			String dienthoaikh="";
			
			String ddkdten="";
			String ngaygiaodich="";
			
			
			String sql ="  select dh.ngaynhap , npp.ma as manpp,npp.ten as tennpp,npp.diachi as diachinpp,npp.dienthoai as dienthoainpp "+ 
						" ,nppmua.ten,isnull(nppmua.dienthoai,'') as dienthoai , "+
						" 			isnull(nppmua.diachi,'') as diachi    "+
						" 		from  DONHANG_NPP dh   inner join   NHAPHANPHOI nppmua  on nppmua.pk_seq=dh.NPP_FK_MUA "+  
						" 		inner join nhaphanphoi npp on npp.pk_seq=dh.NPP_FK_BAN     "+
						" 		where dh.PK_SEQ=" +id;
			ResultSet rs=db.get(sql);
			try{
				if(rs.next()){
					manpp=rs.getString("manpp");
					ngaygiaodich=rs.getString("ngaynhap");
					dienthoainpp=rs.getString("dienthoainpp");
					diachinpp=rs.getString("diachinpp");
					tennpp=rs.getString("tennpp");
					tenkh= rs.getString("ten");
					diachikh= rs.getString("diachi");
					dienthoaikh= rs.getString("dienthoai");
				}
				rs.close();
			}catch(Exception er ){
				er.printStackTrace();
			}	
		 
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			NumberFormat formatter1 = new DecimalFormat("#,###,###.##"); 
			
			PdfWriter.getInstance(document, outstream);
			document.open();
			//lay doi tuong khach hang
			//chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 14, Font.BOLD);
			
			Font font12 = new Font(bf, 12, Font.BOLD);
			
			Font font2 = new Font(bf, 9, Font.BOLD);
			Font fontnomar=new Font(bf,9,Font.NORMAL);
			
			Font fontlink =new Font(bf,10,Font.UNDERLINE);
			fontlink.setColor(BaseColor.BLUE);
			
			PdfPTable tableheader=new PdfPTable(2);
			tableheader.setWidthPercentage(100);//chieu dai cua báº£ng 
			tableheader.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			
			float[] withsheader = {35.0f, 65.0f};//SET DO RONG CAC COT
			tableheader.setWidths(withsheader); 
			
			Image hinhanh=Image.getInstance( getServletContext().getInitParameter("path")+"/images/logoDBH.jpg");	
			hinhanh.scalePercent(50);
			hinhanh.setAbsolutePosition(190, 201);
			
			hinhanh.setAlignment(Element.ALIGN_CENTER);
			PdfPCell cellslogo = new PdfPCell(hinhanh);
		
			
			cellslogo.setPadding(0);
			cellslogo.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellslogo.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cellslogo.setBorder(0);
			
			tableheader.addCell(cellslogo);
			
			PdfPCell celltieude = new PdfPCell();
			celltieude.setBorder(0);
			
			
			Paragraph dvbh = new Paragraph(tennpp, font12);
			dvbh.setSpacingAfter(3);
			dvbh.setAlignment(Element.ALIGN_LEFT);
			celltieude.addElement(dvbh);
			
			dvbh = new Paragraph("Địa chỉ: "+diachinpp, fontnomar);
			dvbh.setSpacingAfter(3);
			dvbh.setAlignment(Element.ALIGN_LEFT);
			celltieude.addElement(dvbh);
			
			dvbh = new Paragraph("Điện thoại: " + dienthoainpp, fontnomar);
			dvbh.setSpacingAfter(3);
			dvbh.setAlignment(Element.ALIGN_LEFT);
			celltieude.addElement(dvbh);
			
			tableheader.addCell(celltieude);
			 
			PdfPCell cell1 = new PdfPCell();
			cell1.setTop(0);
			cell1.setPadding(0);
			cell1.setBorder(0);
			
			Paragraph pxk = new Paragraph("PHIẾU XUẤT BÁN NHÀ PHÂN PHỐI", font);
			pxk.setSpacingAfter(0);
			pxk.setSpacingBefore(0);
			pxk.setAlignment(Element.ALIGN_CENTER);
			cell1.addElement(pxk);
			cell1.setColspan(2);
			tableheader.addCell(cell1);
			document.add(tableheader);
 
			PdfPTable table_info=new PdfPTable(3);
			float[] with3 = {50.0f,20.0f,20.0f};//SET DO RONG CAC COT
			table_info.setWidthPercentage(100);
			table_info.setWidths(with3);
			PdfPCell cell111= new PdfPCell();
			
			cell111.setBorder(0);
			cell111.addElement(new Paragraph("   "  ,fontnomar));
			cell111.addElement(new Paragraph("Tên khách hàng : " + tenkh ,fontnomar));
			cell111.addElement(new Paragraph("Số điện thoại : " + dienthoaikh  ,fontnomar));
			cell111.addElement(new Paragraph("Địa chỉ : " + diachikh  ,fontnomar));
 
			
			cell111.setPaddingBottom(10);
			table_info.addCell(cell111);
			
			cell111=new  PdfPCell();
			cell111.setBorder(0);
			cell111.addElement(new Paragraph("Mã cửa hàng : " +manpp ,fontnomar));
			table_info.addCell(cell111);
			
			
			cell111=new  PdfPCell();
			cell111.setBorder(0);
			cell111.addElement(new Paragraph("Ngày : " +ngaygiaodich ,fontnomar));
			cell111.addElement(new Paragraph("Số : " +id ,fontnomar));
			table_info.addCell(cell111);
			
			document.add(table_info);
			
		
			sql=" select   b.pk_seq as spId, b.ma as spMa, b.ten as spTen, a.soluong, "+ 
				" isnull(c.donvi, 'Chua xac dinh') as donvi, a.giamua*1.1 as dongia   "+
				" from donhangnpp_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq "+ 
				" left join donvidoluong c on b.dvdl_fk = c.pk_seq "+ 
				" where a.donhangnpp_fk ="+id;
			  rs=db.get(sql);
			
			PdfPTable table = new PdfPTable(6);//Chu y nhe 6 cot o day, thi xuong duoi nho set withs la 6 cot
			table.setWidthPercentage(100); 
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withs = {8.0f,30.0f,10.0f, 15.0f, 15.0f, 15.0f}; 			
	        table.setWidths(withs);
			String[] th = new String[]{"STT",  "Sản phẩm","Số lượng", "Đơn giá","Thành tiền","Ghi chú"};
			
			PdfPCell[] cell = new PdfPCell[10];
			for(int i=0; i < 6; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], font2));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[i].setBackgroundColor(BaseColor.LIGHT_GRAY);		
				table.addCell(cell[i]);			
			}
			float size= 8.5f;
			Font font4 = new Font(bf,size );
			PdfPCell cells_detail = new PdfPCell();
			double totalle=0;
			int dem=1;
			while ( rs.next())
			{
 
				double dongia=rs.getDouble("dongia");
			 
				double thanhtien=rs.getDouble("dongia")*rs.getDouble("soluong");
				 
				
				String[] arr = new String[]{Integer.toString(dem), rs.getString("spten"), rs.getString("soluong"), 
											formatter1.format(dongia) 
											 ,formatter1.format(thanhtien),""};
							totalle = totalle + (thanhtien);
				
				for(int j=0; j <6; j++)
				{
					cells_detail = new PdfPCell(new Paragraph(arr[j], font4));
					if(j==1){
						cells_detail.setHorizontalAlignment(Element.ALIGN_LEFT);
					}else  if(j==3 || j==4|| j==5) {
						cells_detail.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}else{
						cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
					}
					cells_detail.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells_detail.setBorderWidthBottom(0);
					 
					table.addCell(cells_detail);
				}
				dem=dem+1;
			}	
			rs.close();
			 
			
 
			PdfPCell cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			Paragraph km=new Paragraph("Cộng tiền hàng ", font2);
			km.setAlignment(Element.ALIGN_RIGHT);
			cell11.addElement(km);
			cell11.setColspan(4);
			table.addCell(cell11);
			
			PdfPCell cell12 = new PdfPCell();
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			Paragraph tongtien =new Paragraph( formatter1.format(totalle), font2);
			tongtien.setAlignment(Element.ALIGN_RIGHT);
			cell12.addElement(tongtien);
			table.addCell(cell12);
			
			
			cell12 = new PdfPCell();
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			tongtien =new Paragraph("", font2);
			tongtien.setAlignment(Element.ALIGN_RIGHT);
			cell12.addElement(tongtien);
			table.addCell(cell12);
		
			doctienrachu doctien=new doctienrachu();
			cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_LEFT);
			Long tongtienst= Math.round(totalle);
			km=new Paragraph("Viết bằng chữ : " + doctien.tranlate(tongtienst+""),  fontnomar);
			km.setAlignment(Element.ALIGN_LEFT);
			cell11.addElement(km);
			cell11.setColspan(7);
			table.addCell(cell11);
			
			document.add(table);
			
		
			PdfPTable tablefooter = new PdfPTable(1);
			tablefooter.setWidthPercentage(100);//chieu dai cua báº£ng 
			tablefooter.setHorizontalAlignment(Element.ALIGN_CENTER);
			float[] withfooterder = {100.0f};//SET DO RONG CAC COT
			tablefooter.setWidths(withfooterder); 
			
			 
			document.add(tablefooter);
			 
			document.close(); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
}
