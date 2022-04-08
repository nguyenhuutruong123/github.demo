package geso.dms.distributor.servlets.donhangtrave;

import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;

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

@WebServlet("/DonhangtravePdfSvl")
public class DonhangtravePdfSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public DonhangtravePdfSvl()
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
		
		this.CreatePhieuGiaoHang(document, outstream, id );
		
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
	}
	
	private void CreatePhieuGiaoHang(Document document, ServletOutputStream outstream, String id)
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
	
	
}
