package geso.dms.distributor.servlets.phieuthuhoi;

import geso.dms.distributor.beans.phieuthuhoi.IPhieuthuhoi;
import geso.dms.distributor.beans.phieuthuhoi.imp.Phieuthuhoi;
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

public class PhieuthuhoiPdfSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	
    public PhieuthuhoiPdfSvl()
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
		
		IPhieuthuhoi pthBean;
		Utility util = new Utility();
		
		String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    
	    String id = util.getId(querystring);
	    pthBean = new Phieuthuhoi(id);
	    pthBean.setUserId(userId);
	    pthBean.init();
	    List<ISanpham> spList = pthBean.getSanphamList();
	    List<ISanpham> spkmList = pthBean.getSpkmList();

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition","inline; filename=Phieuthuhoi.pdf");
		
		Document document = new Document();
		ServletOutputStream outstream = response.getOutputStream();
		this.CreatePth(document, outstream, id, spList, spkmList, pthBean);
		if(spkmList!=null){
			spkmList.clear();

		}
		if(spList!=null){
			spList.clear();
		}
		if(pthBean!=null){
			pthBean.DBclose();
			pthBean=null;
		}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		this.doGet(request, response);
	}
	
	private void CreatePth(Document document, ServletOutputStream outstream, String id, List<ISanpham> spList, List<ISanpham> spKMList, IPhieuthuhoi pthBean) throws IOException
	{		
		try 
		{
			PdfWriter.getInstance(document, outstream);
			
			document.open();
			
			//chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 15, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			Font font3 = new Font(bf, 8, Font.UNDERLINE);
			//font2.setColor(BaseColor.GREEN);
			
			Paragraph pxk = new Paragraph("Phiếu thu hồi sản phẩm", font);
			pxk.setSpacingAfter(10);
			pxk.setAlignment(Element.ALIGN_CENTER);
			document.add(pxk);
			
			pxk = new Paragraph("(Ngày tạo: " + pthBean.getNgaytao() + "   -  Số phiếu: " + pthBean.getId() + ")", font2);
			pxk.setSpacingAfter(15);
			pxk.setAlignment(Element.ALIGN_CENTER);
			document.add(pxk);
			
			pxk = new Paragraph();
			pxk.setSpacingAfter(10);
			pxk.setAlignment(Element.ALIGN_LEFT);
			Chunk chunk = new Chunk("Phiếu xuất kho: ", font3);
			pxk.add(chunk);
			chunk = new Chunk(pthBean.getPhieuxuatkho(), font2);
			pxk.add(chunk);
			document.add(pxk);
			
			PdfPTable tableHead = new PdfPTable(4);
			tableHead.setWidthPercentage(90);
			tableHead.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableHead.setSpacingAfter(10);
			float[] with = {25.0f, 60.0f, 20f, 45.0f}; //set chieu rong cac columns
			tableHead.setWidths(with);
			
			PdfPCell cell1 = new PdfPCell(new Paragraph("Nv giao hàng:  ", font3));
			PdfPCell cell2 = new PdfPCell(new Paragraph(pthBean.getNvgnTen(), font2));
			PdfPCell cell3 = new PdfPCell(new Paragraph("Kho nhập: ", font3));
			PdfPCell cell4 = new PdfPCell(new Paragraph("", font2));
			cell1.setBorder(0);
			cell2.setBorder(0);
			cell3.setBorder(0);
			cell4.setBorder(0);
			tableHead.addCell(cell1);
			tableHead.addCell(cell2);
			tableHead.addCell(cell3);
			tableHead.addCell(cell4);
			
			PdfPCell cell5 = new PdfPCell(new Paragraph("Nhà phân phối: ", font3));
			PdfPCell cell6 = new PdfPCell(new Paragraph(pthBean.getNppTen(), font2));
			
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
			float[] withs = {10.0f, 15.0f, 30.0f, 7.0f, 10.0f};
	        table.setWidths(withs);
	        
	        if(spList.size() > 0)
	        {
	        	//san pham khuyen mai
				pxk = new Paragraph();
				pxk.setSpacingAfter(15);
				pxk.setSpacingBefore(10);
				pxk.setAlignment(Element.ALIGN_LEFT);
				Chunk chk = new Chunk("Thu hồi hàng bán", font3);
				pxk.add(chk);
				document.add(pxk);
				
				String[] th = new String[]{"STT", "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Diễn giải"};
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
		
				for(int i = 0; i < spList.size(); i++)
				{ 
					Sanpham sanpham = (Sanpham)spList.get(i);
					String[] arr = new String[]{Integer.toString(i + 1), sanpham.getMasanpham(), sanpham.getTensanpham(), sanpham.getSoluong(), ""};
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
	        }
	        
			if(spKMList.size() > 0)
			{
				//san pham khuyen mai
				pxk = new Paragraph();
				pxk.setSpacingAfter(15);
				pxk.setSpacingBefore(10);
				pxk.setAlignment(Element.ALIGN_LEFT);
				Chunk chk = new Chunk("Thu hồi hàng khuyến mại", font3);
				pxk.add(chk);
				document.add(pxk);
				
				PdfPTable tableKM = new PdfPTable(6);
				tableKM.setWidthPercentage(100);
				tableKM.setHorizontalAlignment(Element.ALIGN_LEFT);
				float[] withsKM = {10.0f, 15.0f, 45.0f, 10.0f, 20.0f, 20.0f};
		        tableKM.setWidths(withsKM);
		        
				String[] thKM = new String[]{"STT", "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Kho", "Kênh bán hàng"};
				PdfPCell[] cellKM = new PdfPCell[6];
				for(int i=0; i < 6 ; i++)
				{
					cellKM[i] = new PdfPCell(new Paragraph(thKM[i], font2));
					cellKM[i].setHorizontalAlignment(Element.ALIGN_CENTER);
					cellKM[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cellKM[i].setPadding(5);
					//cell[i].setBackgroundColor(BaseColor.CYAN);
					tableKM.addCell(cellKM[i]);			
				}
				
				for(int i = 0; i < spKMList.size(); i++)
				{
					//Sanpham sanpham1 = (Sanpham)spKmSauthudoi.get(i);//san pham sau thu hoi
				
					Sanpham sanpham = (Sanpham)spKMList.get(i);
					//String[] arr = new String[]{Integer.toString(i), sanpham.getMasanpham(), sanpham.getTensanpham(),""+(Integer.parseInt(sanpham.getSoluong())-Integer.parseInt(sanpham1.getSoluong()))};
					
					String[] arr = new String[]{Integer.toString(i + 1), sanpham.getMasanpham(), sanpham.getTensanpham(), sanpham.getSoluong(), sanpham.getDonvitinh(), sanpham.getDongia()};
					Font font4 = new Font(bf, 8);
					PdfPCell cells = new PdfPCell();
					for(int j=0; j < 6; j++)
					{
						cells = new PdfPCell(new Paragraph(arr[j], font4));
						if(j==2)
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						else
							cells.setHorizontalAlignment(Element.ALIGN_CENTER);
						cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cells.setPadding(3);
						tableKM.addCell(cells);
					}
				}		
				document.add(tableKM);
			}
			dbutils db = new dbutils();
			ResultSet khRs = getKhachHangList(id,db);
			if(khRs != null)
			{
				//san pham khuyen mai
				pxk = new Paragraph();
				pxk.setSpacingAfter(15);
				pxk.setSpacingBefore(10);
				pxk.setAlignment(Element.ALIGN_LEFT);
				Chunk chk = new Chunk("Danh sách khách hàng", font3);
				pxk.add(chk);
				document.add(pxk);
				
				PdfPTable tbKhachHang = new PdfPTable(6);
				tbKhachHang.setWidthPercentage(100);
				tbKhachHang.setHorizontalAlignment(Element.ALIGN_LEFT);
				float[] withsKM = {10.0f, 20.0f, 20.0f, 30.0f, 45.0f, 15.0f};
				tbKhachHang.setWidths(withsKM);
		        
				String[] thKM = new String[]{"STT", "Mã khách hàng", "Mã số thuế", "Tên khách hàng", "Địa chỉ", "Số hóa đơn"};
				PdfPCell[] cellKM = new PdfPCell[6];
				for(int i=0; i < 6 ; i++)
				{
					cellKM[i] = new PdfPCell(new Paragraph(thKM[i], font2));
					cellKM[i].setHorizontalAlignment(Element.ALIGN_CENTER);
					cellKM[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cellKM[i].setPadding(5);
					tbKhachHang.addCell(cellKM[i]);			
				}
				
				//danh sach khach hang lay khi in phieu xuat kho
				try 
				{
					int m = 1;
					while(khRs.next())
					{
						String[] arr = new String[]{Integer.toString(m), khRs.getString("smartid"), khRs.getString("masothue"), khRs.getString("khTen"), khRs.getString("diachi"), khRs.getString("dhId")};
						Font font4 = new Font(bf, 8);
						PdfPCell cells = new PdfPCell();
						for(int j = 0; j < 6; j++)
						{
							cells = new PdfPCell(new Paragraph(arr[j], font4));
							if(j == 4)
								cells.setHorizontalAlignment(Element.ALIGN_LEFT);
							else
								cells.setHorizontalAlignment(Element.ALIGN_CENTER);
							cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cells.setPadding(3);
							tbKhachHang.addCell(cells);
							m++;
						}
					}
					khRs.close();
					
					document.add(tbKhachHang);
				} 
				catch(Exception e) {}
				
			}
			if(db!=null){
				db.shutDown();
			}
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
			PdfPCell cell14 = new PdfPCell(new Paragraph("Nhân viên giao nhận                 Kế toán", font2));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell12.setBorder(0);
			cell13.setBorder(0);
			cell14.setBorder(0);
			tableFooter.addCell(cell12);
			tableFooter.addCell(cell13);
			tableFooter.addCell(cell14);
			document.add(tableFooter);
			document.close();		
		} 
		catch (DocumentException e) {}
	}
	
	public ResultSet getKhachHangList(String pthID,dbutils db )
	{
		if(pthID.length() <= 0)
			return null;
		
		String query = "select c.smartid , c.pk_seq as khId, isnull(c.masothue, '') as masothue, c.diachi, c.ten as khTen, b.pk_seq as dhId from PHIEUTHUHOI_DONHANG a inner join donhang b on a.donhang_fk = b.pk_seq inner join khachhang c on b.khachhang_fk = c.pk_seq ";
		query += "where a.pth_fk = '" + pthID + "'";
		ResultSet rs = db.get(query);
	
		return rs;
	}
	
	
	
				
}
