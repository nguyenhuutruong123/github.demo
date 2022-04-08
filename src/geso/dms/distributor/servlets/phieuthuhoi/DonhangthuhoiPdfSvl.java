package geso.dms.distributor.servlets.phieuthuhoi;

import geso.dms.distributor.beans.donhangthuhoi.IDonhangthuhoi;
import geso.dms.distributor.beans.donhangthuhoi.imp.Donhangthuhoi;
import geso.dms.distributor.beans.donhang.ISanpham;
import geso.dms.distributor.beans.donhang.imp.Sanpham;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class DonhangthuhoiPdfSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	
    public DonhangthuhoiPdfSvl() 
    {
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
			response.sendRedirect("/AHF/index.jsp");
		}else{
		IDonhangthuhoi dhthBean;
		dbutils db;
		
		Utility util = new Utility();
		String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);	    
	    String id = util.getId(querystring);
	    
	    dhthBean = new Donhangthuhoi(id);
	    dhthBean.setUserId(userId);
	    dhthBean.init();
	    ResultSet khachhang = dhthBean.getKhachhang();
	    List<ISanpham> spList = dhthBean.getSanphamList();
	    
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition"," inline; filename=Donhangthuhoi.pdf");
		
		Document document = new Document();
		ServletOutputStream outstream = response.getOutputStream();
		this.CreatePth(document, outstream, spList, khachhang, dhthBean);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
 
		this.doGet(request, response);
	}
	
	private void CreatePth(Document document, ServletOutputStream outstream, List<ISanpham> spList, ResultSet kh, IDonhangthuhoi dhthBean) throws IOException
	{
		try{			
			PdfWriter.getInstance(document, outstream);
			document.open();
			
			//chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 15, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			Font font3 = new Font(bf, 8, Font.UNDERLINE);
			//font2.setColor(BaseColor.GREEN);
			 
			kh.next();
			
			Paragraph pxk = new Paragraph("Don Hang Thu Hoi San Pham", font);
			pxk.setSpacingAfter(10);
			pxk.setAlignment(Element.ALIGN_CENTER);
			document.add(pxk);
			
			pxk = new Paragraph("(Ngay tao: " + kh.getDate("ngaytao").toString() + "    So phieu: " + kh.getString("dhthId") + ")", font2);
			pxk.setSpacingAfter(15);
			pxk.setAlignment(Element.ALIGN_CENTER);
			document.add(pxk);
			
			pxk = new Paragraph();
			pxk.setSpacingAfter(10);
			pxk.setAlignment(Element.ALIGN_LEFT);
			Chunk chunk = new Chunk("Ma don hang: ", font3);
			pxk.add(chunk);
			chunk = new Chunk(kh.getString("dhId"), font2);
			pxk.add(chunk);
			document.add(pxk);
			
			PdfPTable tableHead = new PdfPTable(4);
			tableHead.setWidthPercentage(90);
			tableHead.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableHead.setSpacingAfter(10);
			float[] with = {25.0f, 60.0f, 15f, 45.0f}; //set chieu rong cac columns
			tableHead.setWidths(with);
			
			PdfPCell cell1 = new PdfPCell(new Paragraph("Nhan vien giao hang: ", font3));
			PdfPCell cell2 = new PdfPCell(new Paragraph("", font2));
			//PdfPCell cell3 = new PdfPCell(new Paragraph("Xuat tu kho: ", font3));
			//PdfPCell cell4 = new PdfPCell(new Paragraph("WH0004 - Kho chinh Tien Thanh ", font2));
			cell1.setBorder(0);
			cell2.setBorder(0);
			//cell3.setBorder(0);
			//cell4.setBorder(0);
			tableHead.addCell(cell1);
			tableHead.addCell(cell2);
			//tableHead.addCell(cell3);
			//tableHead.addCell(cell4);
			
			PdfPCell cell5 = new PdfPCell(new Paragraph("NPP: ", font3));
			PdfPCell cell6 = new PdfPCell(new Paragraph(dhthBean.getNppTen(), font2));
			
			cell5.setBorder(0);
			cell6.setBorder(0);	
			cell6.setColspan(3);
			tableHead.addCell(cell5);
			tableHead.addCell(cell6);
			
			PdfPCell cell9 = new PdfPCell(new Paragraph("", font3));
			PdfPCell cell10 = new PdfPCell(new Paragraph("", font2));
			cell9.setBorder(0);
			cell10.setBorder(0);
			cell10.setColspan(3);
			tableHead.addCell(cell9);
			tableHead.addCell(cell10);
			
			document.add(tableHead);
			
			//Table Content			
			PdfPTable table = new PdfPTable(5);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withs = {4.0f, 15.0f, 30.0f, 7.0f, 10.0f};
	        table.setWidths(withs);
	        
			String[] th = new String[]{"STT", "Ma sp", "Ten san pham", "So luong", "Dien giai"};
			PdfPCell[] cell = new PdfPCell[5];
			for(int i=0; i < 5 ; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], font2));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[i].setPadding(5);
				//cell[i].setBackgroundColor(BaseColor.CYAN);
				table.addCell(cell[i]);			
			}
	
			for(int i=0; i < spList.size(); i++)
			{
				Sanpham sanpham = (Sanpham)spList.get(i);
				String[] arr = new String[]{Integer.toString(i), sanpham.getMasanpham(), sanpham.getTensanpham(), sanpham.getSoluong(), ""};
				Font font4 = new Font(bf, 8);
				PdfPCell cells = new PdfPCell();
				for(int j=0; j < 5; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], font4));
					if(j==2)
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					else
						cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPadding(3);
					table.addCell(cells);
				}
			}		
			document.add(table);
			
			//Table Footer			
			PdfPTable tableFooter = new PdfPTable(3);
			tableFooter.setWidthPercentage(90);
			tableFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableFooter.setSpacingBefore(15);
			tableFooter.setWidths(new float[]{30.0f, 30.0f, 30.0f});
			
			PdfPCell cell12 = new PdfPCell(new Paragraph("Nhân viên nhập liệu", font2));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell13 = new PdfPCell(new Paragraph("Thủ kho", font2));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell14 = new PdfPCell(new Paragraph("Nhân viên giao nhận", font2));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell12.setBorder(0);
			cell13.setBorder(0);
			cell14.setBorder(0);
			tableFooter.addCell(cell12);
			tableFooter.addCell(cell13);
			tableFooter.addCell(cell14);
			document.add(tableFooter);
			document.close();
			
			kh.close();
		}
		catch(DocumentException e)
		{
			e.printStackTrace();
		} 
		catch(Exception e) {}		
	}


}
