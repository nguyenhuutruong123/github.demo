package geso.dms.distributor.servlets.hoadontaichinhNPP;

import geso.dms.center.beans.doctien.doctienrachu;
import geso.dms.distributor.beans.hoadontaichinh.IHoadontaichinh;
import geso.dms.distributor.beans.hoadontaichinhNPP.*;
import geso.dms.distributor.beans.hoadontaichinhNPP.imp.*;
import geso.dms.distributor.db.sql.dbutils;
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

public class ErpHoadontaichinhNPPPdfSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErpHoadontaichinhNPPPdfSvl()
	{
		super();
	}
	float CONVERT = 28.346457f; // = 1cm
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		dbutils db = new dbutils();
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		// String userTen = (String) session.getAttribute("userTen");

		Utility util = new Utility();
		if (userId.length() == 0)
			userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));

		String querystring = request.getQueryString();
		String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("pdf")));
		IErpHoadontaichinhNPP pxkBean = new ErpHoadontaichinhNPP(id);
		pxkBean.setUserId(userId);
		
		String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));

		String task = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("task"));
		if (querystring.indexOf("pdf") > 0)
		{
			//pxkBean.initPdf();

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", " inline; filename=PhieuXuatKhoTT.pdf");

			Document document = new Document();
			ServletOutputStream outstream = response.getOutputStream();
			
				this.CreatePxk(document, outstream, pxkBean);

			String msg = this.CapnhatTT(id);
			pxkBean.setMsg(msg);
		} 
		
	}


	
	private String CapnhatTT(String id) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "";
			String trangthai="";
			// Kiem tra trạng thái hiện tại của Hóa đơn
			
			query = " select trangthai from ERP_HOADONNPP where pk_seq = "+ id +" ";
			ResultSet rs = db.get(query);
			if(rs!= null)
			{
				while(rs.next())
				{
					trangthai = rs.getString("trangthai");
				}rs.close();
			}
			
			if(!trangthai.equals("3") && !trangthai.equals("5"))
			{
				// Cập nhật trạng thái Đã in
				query = "update ERP_HOADONNPP set trangthai = '4' where pk_seq = '" + id + "' ";
				if(!db.update(query))
				{
					msg = "Không thể cập nhật ERP_HOADONNPP " + query;
					db.getConnection().rollback();
					return msg;
				}
			
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		
		return "";
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doGet(request, response);
	}

	private void CreatePxk(Document document, ServletOutputStream outstream, IErpHoadontaichinhNPP pxkBean) throws IOException
	{
		try
		{
						
			dbutils db = new dbutils();
				
					//LAY THONG TIN NCC
					String kbh="";
					String ddh="";
					String npp_fk="";
					String khId="";
					
					String ctyTen="";
					String cty_MST ="";
					String cty_Diachi="";
					String cty_Sotaikhoan= "";
					String cty_Dienthoai= "";
					String cty_Fax= "";
					String khoxuat ="";
					String hinhthucTT= "";
					String ngayxuathd ="";
					String nguoimuahang="";
					double chietkhauDH = 0;
					String tennpp_ban="";
					String sql =
					"SELECT  A.PK_SEQ, A.KBH_FK ,(select XUATTAIKHO from NHAPHANPHOI where PK_SEQ = A.NPP_FK) AS KHO," +
					" (SELECT KHACHHANG_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS KHACHHANG_FK," +
					" (SELECT NPP_DAT_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS NPP_FK," +
					" (SELECT NPP_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS NPP_ban," +
					" (SELECT HINHTHUCTT FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS HTTT," +
					" (SELECT ngayxuathd FROM ERP_HOADONNPP where pk_seq= '" + pxkBean.getId() + "' ) as ngayxuathd," +
					" ( SELECT case when khachhang_fk is not null and nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
					"               else isnull(nguoimua,'') end " +
					"   FROM ERP_HOADONNPP" +
					"   WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua,isnull(chietkhau,0) as chietkhauDH "  +
				    "FROM ERP_DONDATHANGNPP A " +
				    "WHERE A.PK_SEQ IN  " +
				    "(select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = '" + pxkBean.getId() + "' )";
		
					System.out.println("[INIT_DONHANG]"+sql);
					
					ResultSet rsCheck = db.get(sql);					
										
					if(rsCheck.next())
					{
						npp_fk = rsCheck.getString("NPP_FK")== null ? "" :rsCheck.getString("NPP_FK") ;
						khId = rsCheck.getString("KHACHHANG_FK")== null ? "" :rsCheck.getString("KHACHHANG_FK");
						ddh = rsCheck.getString("PK_SEQ");
						nguoimuahang = rsCheck.getString("nguoimua");
						kbh = rsCheck.getString("KBH_FK");
						khoxuat = rsCheck.getString("KHO");
						hinhthucTT = rsCheck.getString("HTTT");
						ngayxuathd = rsCheck.getString("ngayxuathd");
						chietkhauDH = rsCheck.getDouble("chietkhauDH");
						tennpp_ban =rsCheck.getString("NPP_ban");
						rsCheck.close();
					}
			
				
			
			   
			 //LAY THONG TIN KHACHHANG
			   String kyhieu=""; 
				 sql ="SELECT  A.KYHIEU " +
					 "FROM ERP_HOADONNPP A " +
					 "WHERE A.PK_SEQ = '" + pxkBean.getId() + "' ";
				
				System.out.println("[INIT_ERP_HOADONNPP 11]"+sql);
				
				ResultSet rsHD = db.get(sql);					
									
				if(rsHD.next())
				{					
					kyhieu = rsHD.getString("KYHIEU");
					rsHD.close();
				} 
				   
				   
				String Donvi="";
				String kh_MST ="";
				String kh_Diachi="";
		
		String sql1="";	
		String sql2="";			
		if(khId.length() > 0){
			/*  sql = " select  TEN ,isnull(DIACHI,'') as DIACHI, isnull(MASOTHUE,'') as MASOTHUE  "+
			        " from KHACHHANG " +
			        " where PK_SEQ = '"+ khId +"' and kbh_fk='100052' ";*/
			
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
		  	  " FROM   ERP_HOADONNPP WHERE KHACHHANG_FK ='"+khId+"' and PK_SEQ = '"+pxkBean.getId()+"'";
			 sql1="select ten,isnull(diachi,'') diachi,ISNULL(SOTAIKHOANNH,'') SOTAIKHOANNH,ISNULL(DIENTHOAI,'') DIENTHOAI,ISNULL(FAX,'') FAX,ISNULL(MASOTHUE,'') MASOTHUE from nhaphanphoi where pk_seq="+tennpp_ban;			
			 sql2="select ten,isnull(diachi,'') diachi,ISNULL(SOTAIKHOANNH,'') SOTAIKHOANNH,ISNULL(DIENTHOAI,'') DIENTHOAI,ISNULL(FAX,'') FAX,ISNULL(MASOTHUE,'') MASOTHUE from nhaphanphoi where pk_seq="+npp_fk;

		}else{
			/*sql = " select  TEN ,DIACHI, MASOTHUE  "+
		    " from NHAPHANPHOI " +
		    " where PK_SEQ = '"+ npp_fk +"'  ";*/
			 sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
		  	  " FROM   ERP_HOADONNPP WHERE NPP_DAT_FK ='"+npp_fk+"' and PK_SEQ = '"+pxkBean.getId()+"'";
			 sql1="select ten,isnull(diachi,'') diachi,ISNULL(SOTAIKHOANNH,'') SOTAIKHOANNH,ISNULL(DIENTHOAI,'') DIENTHOAI,ISNULL(FAX,'') FAX,ISNULL(MASOTHUE,'') MASOTHUE from nhaphanphoi where pk_seq="+tennpp_ban;
			 sql2="select ten,isnull(diachi,'') diachi,ISNULL(SOTAIKHOANNH,'') SOTAIKHOANNH,ISNULL(DIENTHOAI,'') DIENTHOAI,ISNULL(FAX,'') FAX,ISNULL(MASOTHUE,'') MASOTHUE from nhaphanphoi where pk_seq="+npp_fk;
		}
			   
		   
		   System.out.println("Lấy TT KH1 "+sql);
		   ResultSet rsLayKH= db.get(sql);
		   if(rsLayKH.next())
		   {
			   Donvi = rsLayKH.getString("TEN");
			   kh_MST = rsLayKH.getString("MASOTHUE");
			   kh_Diachi = rsLayKH.getString("DIACHI");
			  
			   rsLayKH.close();
			   
		   }   
		  
		   String tennpp="";
		   String masothuenpp="";
		   String sotaikhoannpp="";
		   String faxnpp="";
		   String dtnpp="";
		   String diachinpp="";
		   ResultSet rsLaynpp= db.get(sql1);
		   if(rsLaynpp.next())
		   {
			   tennpp_ban = rsLaynpp.getString("TEN");	
			   sotaikhoannpp=rsLaynpp.getString("SOTAIKHOANNH");
			   masothuenpp=rsLaynpp.getString("MASOTHUE");
			   faxnpp=rsLaynpp.getString("FAX");
			   dtnpp=rsLaynpp.getString("DIENTHOAI");
			   diachinpp=rsLaynpp.getString("diachi");
			   rsLaynpp.close();
			   
		   }   
		   
		   String tennppmua="";
		   String masothuenppmua="";
		   String sotaikhoannppmua="";
		   String faxnppmua="";
		   String dtnppmua="";
		   String diachinppmua="";
		   ResultSet rsLaynppmua1= db.get(sql2);
		   if(rsLaynppmua1.next())
		   {
			   tennppmua = rsLaynppmua1.getString("TEN");	
			   masothuenppmua=rsLaynppmua1.getString("SOTAIKHOANNH");
			   sotaikhoannppmua=rsLaynppmua1.getString("MASOTHUE");
			   faxnppmua=rsLaynppmua1.getString("FAX");
			   dtnppmua=rsLaynppmua1.getString("DIENTHOAI");
			   diachinppmua=rsLaynppmua1.getString("diachi");
			   rsLaynppmua1.close();
			   
		   }   
		   System.out.println("___________________"+sql2);
		    NumberFormat formatter = new DecimalFormat("#,###,###");
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			NumberFormat formatter4 = new DecimalFormat("#,###,###.####");
			document.setPageSize(PageSize.A4.rotate());
			document.setMargins(2.0f*CONVERT, 1.5f*CONVERT, 1.7f*CONVERT, 2.0f*CONVERT); // L,R,T,B
			PdfWriter writer = PdfWriter.getInstance(document, outstream);
			
			document.open() ;
			
		

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			
			
			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setWidthPercentage(100);			

			PdfPCell cell = new PdfPCell();
			cell.setBorder(0);
			cell.setPaddingTop(0.17f * CONVERT);
			cell.setPaddingLeft(2.6f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			String [] ngayHD = ngayxuathd.split("-");
			Paragraph pxk = new Paragraph(ngayHD[2] + "                        " + ngayHD[1] +  "                             " + ngayHD[0] , new Font(bf, 8, Font.BOLDITALIC));
			pxk.setAlignment(Element.ALIGN_CENTER);
			pxk.setSpacingAfter(2);
			cell.addElement(pxk);

			tableheader.addCell(cell);
			document.add(tableheader);
			
			// Thông tin Khach Hang
			PdfPTable table1 =new PdfPTable(2);
			table1.setWidthPercentage(100);
			float[] withs1 = {2.0f * CONVERT, 15.0f * CONVERT};
			table1.setWidths(withs1);									
			
			
			// DONG 1-- NGUOI ban
			PdfPCell cell_nguoimua = new PdfPCell();	
			pxk = new Paragraph(""  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(8);
			pxk.setSpacingBefore(8);
			cell_nguoimua.addElement(pxk);
			cell_nguoimua.setBorder(0);						
			table1.addCell(cell_nguoimua);	
			
			PdfPCell cell_nguoimua1 = new PdfPCell();
			pxk.setSpacingAfter(8);
			pxk.setSpacingBefore(8);
			pxk = new Paragraph(tennpp_ban, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell_nguoimua1.addElement(pxk);
			cell_nguoimua1.setBorder(0);						
			table1.addCell(cell_nguoimua1);
			
			// DONG 2-- dia chi npp ban
			PdfPCell cell8 = new PdfPCell();	
			//cell8.setPaddingLeft(2.7f * CONVERT);
			pxk = new Paragraph(" "  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			//pxk.setSpacingAfter(4);
			cell8.addElement(pxk);
			cell8.setBorder(0);						
			table1.addCell(cell8);	
			
			PdfPCell cell8a = new PdfPCell();
			//cell8a.setPaddingTop(-0.19f * CONVERT);
			//cell8a.setPaddingLeft(3.7f * CONVERT);
			pxk = new Paragraph(diachinpp, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell8a.addElement(pxk);
			cell8a.setBorder(0);						
			table1.addCell(cell8a);
			

			// DONG 3 ---- so tai khoan
			PdfPCell cell10 = new PdfPCell();
		//	cell10.setPaddingLeft(2.5f * CONVERT);	
			//cell10.setPaddingTop(-0.1f * CONVERT);
			pxk = new Paragraph(" ", new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			//pxk.setSpacingAfter(2);
			//pxk.setSpacingBefore(12.0f);
			cell10.addElement(pxk);
			cell10.setBorder(0);						
			table1.addCell(cell10);
					
			
			
			
			PdfPCell cell14 = new PdfPCell();
		//	cell14.setPaddingTop(-0.1f * CONVERT);
		//	cell14.setPaddingLeft(1.6f * CONVERT);
			pxk = new Paragraph(sotaikhoannpp, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
		//	pxk.setSpacingAfter(2);
			//pxk.setSpacingBefore(12.0f);
			cell14.addElement(pxk);
			cell14.setBorder(0);						
			table1.addCell(cell14);		
			
			
		
			// DONG 5 ----dien thoai
			PdfPCell cell17 = new PdfPCell();	
		//	cell17.setPaddingLeft(2.9f * CONVERT);
			//cell17.setPaddingTop(0.1f * CONVERT);
			pxk = new Paragraph("", new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell17.addElement(pxk);
			cell17.setBorder(0);						
			table1.addCell(cell17);	
			
			
			PdfPCell cell18 = new PdfPCell();
			//cell18.setPaddingLeft(5.0f * CONVERT);
			//cell18.setPaddingTop(0.1f * CONVERT);
			pxk = new Paragraph( dtnpp +"                                                " +faxnpp+"                                                " +"NA"+"                                                " +masothuenpp, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell18.addElement(pxk);
			cell18.setBorder(0);						
			table1.addCell(cell18);       
				
// DONG 1-- NGUOI mua
			// DONG 2-- dia chi npp ban
						PdfPCell cellnguoi_m = new PdfPCell();	
						//cell8.setPaddingLeft(2.7f * CONVERT);
						pxk = new Paragraph(" "  , new Font(bf, 10, Font.NORMAL));
						pxk.setAlignment(Element.ALIGN_LEFT);
						//pxk.setSpacingAfter(4);
						cellnguoi_m.addElement(pxk);
						cellnguoi_m.setBorder(0);						
						table1.addCell(cellnguoi_m);	
						
						PdfPCell cellnguoi_m1 = new PdfPCell();
						//cell8a.setPaddingTop(-0.19f * CONVERT);
						//cell8a.setPaddingLeft(3.7f * CONVERT);
						pxk = new Paragraph(Donvi, new Font(bf, 10, Font.NORMAL));
						pxk.setAlignment(Element.ALIGN_LEFT);
						pxk.setSpacingAfter(2);
						cellnguoi_m1.addElement(pxk);
						cellnguoi_m1.setBorder(0);						
						table1.addCell(cellnguoi_m1);
						
						// DONG 2-- dia chi  ban
						PdfPCell celldc_m = new PdfPCell();	
						//cell8.setPaddingLeft(2.7f * CONVERT);
						pxk = new Paragraph(" "  , new Font(bf, 10, Font.NORMAL));
						pxk.setAlignment(Element.ALIGN_LEFT);
						//pxk.setSpacingAfter(4);
						celldc_m.addElement(pxk);
						celldc_m.setBorder(0);						
						table1.addCell(celldc_m);	
						
						PdfPCell celldc_m1 = new PdfPCell();
						//cell8a.setPaddingTop(-0.19f * CONVERT);
						//cell8a.setPaddingLeft(3.7f * CONVERT);
						pxk = new Paragraph(kh_Diachi, new Font(bf, 10, Font.NORMAL));
						pxk.setAlignment(Element.ALIGN_LEFT);
						pxk.setSpacingAfter(2);
						celldc_m1.addElement(pxk);
						celldc_m1.setBorder(0);						
						table1.addCell(celldc_m1);
						
						// DONG 5 ----dien thoai npp mua
						PdfPCell cell17m = new PdfPCell();	
					//	cell17.setPaddingLeft(2.9f * CONVERT);
						//cell17.setPaddingTop(0.1f * CONVERT);
						pxk = new Paragraph("", new Font(bf, 10, Font.NORMAL));
						pxk.setAlignment(Element.ALIGN_LEFT);
						pxk.setSpacingAfter(2);
						cell17m.addElement(pxk);
						cell17m.setBorder(0);						
						table1.addCell(cell17m);	
						
						
						PdfPCell cell18m = new PdfPCell();
						//cell18.setPaddingLeft(5.0f * CONVERT);
						//cell18.setPaddingTop(0.1f * CONVERT);
						pxk = new Paragraph( dtnppmua +"                                                " +faxnppmua+"                                                " +"NA"+"                                                " +masothuenppmua, new Font(bf, 10, Font.NORMAL));
						pxk.setAlignment(Element.ALIGN_LEFT);
						pxk.setSpacingAfter(2);
						cell18m.addElement(pxk);
						cell18m.setBorder(0);						
						table1.addCell(cell18m);  
						
						// DONG 3 ---- so tai khoan nhapp ban
						PdfPCell celltkb = new PdfPCell();
					//	cell10.setPaddingLeft(2.5f * CONVERT);	
						//cell10.setPaddingTop(-0.1f * CONVERT);
						pxk = new Paragraph(" ", new Font(bf, 10, Font.NORMAL));
						pxk.setAlignment(Element.ALIGN_LEFT);
						//pxk.setSpacingAfter(2);
						//pxk.setSpacingBefore(12.0f);
						celltkb.addElement(pxk);
						celltkb.setBorder(0);						
						table1.addCell(celltkb);
								
						
						
						
						PdfPCell celltkb1 = new PdfPCell();
					//	cell14.setPaddingTop(-0.1f * CONVERT);
					//	cell14.setPaddingLeft(1.6f * CONVERT);
						pxk = new Paragraph(sotaikhoannppmua, new Font(bf, 10, Font.NORMAL));
						pxk.setAlignment(Element.ALIGN_LEFT);
					//	pxk.setSpacingAfter(2);
						//pxk.setSpacingBefore(12.0f);
						celltkb1.addElement(pxk);
						celltkb1.setBorder(0);						
						table1.addCell(celltkb1);	
			
						// DONG 3 ---- ten nguoi dai dien nhapp ban
						PdfPCell celltkb11 = new PdfPCell();
					//	cell10.setPaddingLeft(2.5f * CONVERT);	
						//cell10.setPaddingTop(-0.1f * CONVERT);
						pxk = new Paragraph(" ", new Font(bf, 10, Font.NORMAL));
						pxk.setAlignment(Element.ALIGN_LEFT);
						//pxk.setSpacingAfter(2);
						//pxk.setSpacingBefore(12.0f);
						celltkb11.addElement(pxk);
						celltkb11.setBorder(0);						
						table1.addCell(celltkb11);
								
						
						PdfPCell celltkb12 = new PdfPCell();
					//	cell14.setPaddingTop(-0.1f * CONVERT);
					//	cell14.setPaddingLeft(1.6f * CONVERT);
						pxk = new Paragraph("", new Font(bf, 10, Font.NORMAL));
						pxk.setAlignment(Element.ALIGN_LEFT);
					//	pxk.setSpacingAfter(2);
						//pxk.setSpacingBefore(12.0f);
						celltkb12.addElement(pxk);
						celltkb12.setBorder(0);						
						table1.addCell(celltkb12);
						
						// DONG 3 ---- ten nguoi dai dien nhapp ban
						PdfPCell celltkb13 = new PdfPCell();
					//	cell10.setPaddingLeft(2.5f * CONVERT);	
						//cell10.setPaddingTop(-0.1f * CONVERT);
						pxk = new Paragraph(" ", new Font(bf, 10, Font.NORMAL));
						pxk.setAlignment(Element.ALIGN_LEFT);
						//pxk.setSpacingAfter(2);
						//pxk.setSpacingBefore(12.0f);
						celltkb13.addElement(pxk);
						celltkb13.setBorder(0);						
						table1.addCell(celltkb13);
								
						
						PdfPCell celltkb14 = new PdfPCell();
					//	cell14.setPaddingTop(-0.1f * CONVERT);
					//	cell14.setPaddingLeft(1.6f * CONVERT);
						pxk = new Paragraph("", new Font(bf, 10, Font.NORMAL));
						pxk.setAlignment(Element.ALIGN_LEFT);
					//	pxk.setSpacingAfter(2);
						//pxk.setSpacingBefore(12.0f);
						celltkb14.addElement(pxk);
						celltkb14.setBorder(0);						
						table1.addCell(celltkb14);
						
						
						// DONG 3 ---- ten nguoi dai dien nhapp ban
						PdfPCell celltkb15 = new PdfPCell();
					//	cell10.setPaddingLeft(2.5f * CONVERT);	
						//cell10.setPaddingTop(-0.1f * CONVERT);
						pxk = new Paragraph(" ", new Font(bf, 10, Font.NORMAL));
						pxk.setAlignment(Element.ALIGN_LEFT);
						//pxk.setSpacingAfter(2);
						//pxk.setSpacingBefore(12.0f);
						celltkb15.addElement(pxk);
						celltkb15.setBorder(0);						
						table1.addCell(celltkb15);
								
						
						PdfPCell celltkb16 = new PdfPCell();
					//	cell14.setPaddingTop(-0.1f * CONVERT);
					//	cell14.setPaddingLeft(1.6f * CONVERT);
						pxk = new Paragraph(hinhthucTT, new Font(bf, 10, Font.NORMAL));
						pxk.setAlignment(Element.ALIGN_LEFT);
					//	pxk.setSpacingAfter(2);
						//pxk.setSpacingBefore(12.0f);
						celltkb16.addElement(pxk);
						celltkb16.setBorder(0);						
						table1.addCell(celltkb16);
						
						document.add(table1);
				
			// Table Content
			PdfPTable root = new PdfPTable(2);
			root.setKeepTogether(false);
			root.setSplitLate(false);
			root.setWidthPercentage(100);
			root.setHorizontalAlignment(Element.ALIGN_LEFT);
			root.getDefaultCell().setBorder(0);
			float[] cr = { 95.0f, 100.0f };
			root.setWidths(cr);

			String[] th = new String[]{ " ", " ", " ", "  ", " "," "};
			
			PdfPTable sanpham = new PdfPTable(th.length);
			sanpham.setSpacingBefore(10.8f);
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			float[] withsKM = { 7.0f, 60f, 17f, 20f, 20f, 20.0f};
			sanpham.setWidths(withsKM);
			

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			double totalCK = 0;
			double vatCK = 0;
			double vatt=0;
				
			String	 query = "";
			if(SoNgay(ngayxuathd)){
				query = 
					"select distinct c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,  \n" +
					"	case when d.pk_seq = dhsp.dvdl_fk then dhsp.soluong  \n" +
					"			else dhsp.soluong * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as soluong,  \n" +
					"	'NA' as solo,  \n" +
					"	'2030-31-12' as NGAYHETHAN, dhsp.chietkhau, dhsp.thuevat, \n" +
					"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = dhsp.sanpham_fk ) as dongia,  	\n" +
					"	CASE WHEN ROW_NUMBER() OVER(PARTITION BY c.ma ORDER BY c.ma DESC) = 1 THEN \n"+
				    "  	    ( select  case when  ISNULL(TIENVAT,0) = 0 then 0 else ROUND((round(SOLUONG*DONGIA, 0)*VAT/100), 0) - ISNULL(TIENVAT,0) end from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = c.pk_seq )  \n"+
				    "   ELSE 0 END as chenhlech, \n"+
					"   ISNULL((select ISNULL(bgsp.CHIETKHAU,0) \n" +
					"           from BANGGIABANDOITAC bg inner join BANGGIABANDOITAC_DOITAC bgdt on bg.PK_SEQ= bgdt.BANGGIABANDOITAC_FK  \n" +
					"						             inner join BANGGIABANDOITAC_SANPHAM bgsp on bgdt.BANGGIABANDOITAC_FK = bgsp.BGBANDOITAC_FK \n" +
					"						              and bgsp.SANPHAM_FK = dhsp.sanpham_fk  \n" +
					"           where bg.KENH_FK = dh.KBH_FK and bgdt.NPP_FK = dh.NPP_FK ),0) as CHIETKHAU_BG \n" +
					"from ERP_YCXUATKHONPP a " +
					"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk = a.PK_SEQ				\n" +					       									
					"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ = e.ddh_fk    									 \n" +
					"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk= dh.PK_SEQ 	and dhsp.sanpham_fk= dhsp.sanpham_fk	 \n" +
					"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  					\n" +
					"     inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	 \n" +
					"where a.PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk = '" + ddh + "' ) and dhsp.soluong > 0 and a.TRANGTHAI != '3' ";
			}
			else{
				query = "select distinct c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,  " +
							"	case when d.pk_seq = dhsp.dvdl_fk then b.soluong  " +
							"			else b.soluong * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as soluong,  " +
							"	case solo when 'NA' then ' ' else b.solo end as solo,  " +
							"	case solo when 'NA' then ' ' else isnull(b.NGAYHETHAN,'') end as NGAYHETHAN, dhsp.thuevat, " +
							"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = b.sanpham_fk ) as dongia  	 " +
							"	,case when	ROW_NUMBER() OVER(PARTITION BY c.ma ORDER BY c.ma DESC) !=1 then 0 else dhsp.chietkhau end as chietkhau "+
							"from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk	 " +
							"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk = a.PK_SEQ									       									 " +
							"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ = e.ddh_fk    									 " +
							"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk= dh.PK_SEQ 	and dhsp.sanpham_fk= b.sanpham_fk	 " +
							"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  						 " +
							"     inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	 " +
							"where a.PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk = '" + ddh + "' ) and b.soluong > 0 and a.TRANGTHAI != '3' ";
			}
			
			System.out.println("[ERP_DONDATHANG_SANPHAM]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			double chenhlech = 0;
			double thanhtien = 0;
			double thueGTGT = 0;
			double sotientt =0;
			
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				double vat = rsSP.getDouble("thuevat");
				vatt=vat;
				
				if(SoNgay(ngayxuathd)){
					chenhlech = rsSP.getDouble("chenhlech");
					thanhtien = Math.round(soLUONG * dongia - chietkhau);
					thueGTGT = Math.round(thanhtien*vat/100);
					
					// NẾU KH ETC LẤY TIỀN VAT SAU KHI SỬA
					if(chenhlech != 0 && khId.trim().length() > 0)
					{
					   thueGTGT = thueGTGT - chenhlech;
					}
										
					sotientt = thanhtien + thueGTGT;
					
					// NẾU XUẤT CHO ĐỐI TÁC (ĐÀ NẴNG && HCM) ĐƠN GIÁ SAU CHIẾT KHẤU (CK ĐƠN HÀNG + CK BẢNG GIÁ)
					if(npp_fk.trim().length() > 0)
					{
						dongia = roundNumer((rsSP.getDouble("dongia") * (100 - (chietkhauDH + rsSP.getDouble("chietkhau_bg")))/100 ),4);
						chietkhau = 0;
						thanhtien = Math.round(soLUONG * dongia);
						thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
						sotientt = thanhtien + thueGTGT;
					}
				}
				else{
					thanhtien = soLUONG*dongia;
					thueGTGT = thanhtien *vat/100;
					sotientt = thanhtien + (thanhtien*vat/100) ;				
					vatCK = rsSP.getDouble("thuevat");
				}
					
				if(ddh.equals("100526")) // Tam thoi sua Vat cho Nhà QN 
				{
					thueGTGT = 571428 ;
					sotientt = thanhtien + thueGTGT;
					totalThueGTGT += thueGTGT ;
					totalTienTruocVAT+= (soLUONG*dongia);
					totalCK += chietkhau;
					
				}
				else
				{
					if(SoNgay(ngayxuathd)){
						totalThueGTGT +=thueGTGT;
						totalTienTruocVAT+=thanhtien;
						totalSotienTT +=sotientt;
						totalCK += chietkhau;
					}
					else{
						totalThueGTGT += (soLUONG*dongia)*vat/100;
						totalTienTruocVAT+= (soLUONG*dongia);
						totalCK += chietkhau;
					}
				}
				
				
				String chuoi ="";
				if(rsSP.getString("ngayhethan").trim().length() > 0 && !rsSP.getString("ngayhethan").equals("null"))
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi= ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}
				
				thanhtien = Math.round(thanhtien);
				thueGTGT = Math.round(thueGTGT);
				sotientt = Math.round(sotientt);
				
				
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("TEN"), rsSP.getString("DONVI"),
						DinhDangTRAPHACO(formatter.format(soLUONG)), DinhDangTRAPHACO(formatter4.format(dongia)) , DinhDangTRAPHACO(formatter.format(thanhtien)) };


				for (int j = 0; j < 6; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLD)));
					
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f * CONVERT);
					cells.setPaddingTop(2.5f);
					
					sanpham.addCell(cells);
				}
							
				
				stt++;
				
			}
		    
			if(totalCK > 0)
			{
				stt = stt -1 ;
				//CHIẾT KHẤU DÒNG HÀNG (NẾU CÓ) : SUM(CHIETKHAU) CỦA TẤT CẢ CÁC SP
				String [] arr5 = new String[] {Integer.toString(stt+1),"CK" ,"Chiết khấu sản phẩm " ," ","",""};
				for (int j = 0; j < arr5.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr5[j], new Font(bf, 10 , Font.BOLD)));
					cells.setFixedHeight(0.6f*CONVERT);
					cells.setPaddingTop(2.5f);
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setBorder(0);
					sanpham.addCell(cells);
				}
			}
			// DONG TRONG
			int kk=0;
			while(kk < 10-stt)
			{
				String[] arr_bosung = new String[] { " ", " " , " ", " "," ", " " };
	
				for (int j = 0; j < 5; j++)
				{
					cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 10, Font.NORMAL)));
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f*CONVERT);
										
					sanpham.addCell(cells);
				}
				
				kk++;
			}
			
			document.add(sanpham);
			
			PdfPTable table2 =new PdfPTable(2);
			table2.setWidthPercentage(100);
			float[] withs2 = {10.0f * CONVERT, 15.0f * CONVERT};
			table2.setWidths(withs2);									
			
			
			// DONG 1-- NGUOI ban
			PdfPCell cell_21 = new PdfPCell();	
			pxk = new Paragraph(""  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(8);
			pxk.setSpacingBefore(8);
			cell_21.addElement(pxk);
			cell_21.setBorder(0);						
			table2.addCell(cell_21);	
			
			PdfPCell cell_22 = new PdfPCell();
			pxk.setSpacingAfter(8);
			pxk.setSpacingBefore(8);
			pxk = new Paragraph(thanhtien+"", new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell_22.addElement(pxk);
			cell_22.setBorder(0);						
			table2.addCell(cell_22);
			
			// DONG 1-- NGUOI ban
			PdfPCell cell_23 = new PdfPCell();	
			pxk = new Paragraph(vatt+"%"  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(8);
			pxk.setSpacingBefore(8);
			cell_23.setPaddingLeft(4.1f * CONVERT);
			cell_23.addElement(pxk);
			cell_23.setBorder(0);	
			
			table2.addCell(cell_23);	
			
			PdfPCell cell_24 = new PdfPCell();
			pxk.setSpacingAfter(8);
			pxk.setSpacingBefore(8);
			pxk = new Paragraph(totalThueGTGT+"", new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell_24.addElement(pxk);
			cell_24.setBorder(0);						
			table2.addCell(cell_24);
						
			
			// DONG 1-- NGUOI ban
			PdfPCell cell_25 = new PdfPCell();	
			pxk = new Paragraph(""  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(8);
			pxk.setSpacingBefore(8);
			cell_25.addElement(pxk);
			cell_25.setBorder(0);						
			table2.addCell(cell_25);	
			
			PdfPCell cell_26 = new PdfPCell();
			pxk.setSpacingAfter(8);
			pxk.setSpacingBefore(8);
			pxk = new Paragraph(formatter1.format(Math.round(totalSotienTT - ( totalCK + totalCK*vatCK/100) ))+"", new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell_26.addElement(pxk);
			cell_26.setBorder(0);						
			table2.addCell(cell_26);
			document.add(table2);
			
			
			PdfPTable table3 =new PdfPTable(2);
			table2.setWidthPercentage(100);
			float[] withs3 = { 15.0f * CONVERT};
			table3.setWidths(withs2);									
			
			
			doctienrachu doctien = new doctienrachu();
		    String tien = doctien.docTien(Math.round(totalSotienTT - (totalCK + totalCK*vatCK/100)));		   
		  //Viết hoa ký tự đầu tiên
		    String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
			
			PdfPCell cell_27 = new PdfPCell();	
			pxk = new Paragraph(tien  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(8);
			pxk.setSpacingBefore(8);
			cell_27.addElement(pxk);
			cell_27.setPaddingLeft(3.3f * CONVERT);
			cell_27.setBorder(0);	
			table3.addCell(cell_27);	
			
		
		    
			PdfPCell cell_28 = new PdfPCell();
			
			pxk = new Paragraph("", new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell_28.addElement(pxk);
			cell_28.setBorder(0);	
			table3.addCell(cell_28);
			
			document.add(table3);
			/*// Tong tien thanh toan	
			totalSotienTT = Math.round(totalTienTruocVAT) + Math.round(totalThueGTGT);
			
			String[] arr = new String[] {"                             ", DinhDangTRAPHACO(formatter1.format(totalTienTruocVAT -totalCK )),"",DinhDangTRAPHACO( formatter1.format(totalThueGTGT- (totalCK*vatCK/100) )),DinhDangTRAPHACO(formatter1.format(Math.round(totalSotienTT - ( totalCK + totalCK*vatCK/100) ))) };
				for (int j = 0; j < arr.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLDITALIC)));
					if (j == 0)
					{
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						cells.setColspan(8);
					} else if(j==1)
					{
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
						
					}else
					{
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPaddingLeft(0.8f * CONVERT);
					cells.setPaddingTop(5.0f);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f*CONVERT);
					sanpham.addCell(cells);
				}
				
				
				// Tien bang chu
				doctienrachu doctien = new doctienrachu();
			    String tien = doctien.docTien(Math.round(totalSotienTT - (totalCK + totalCK*vatCK/100)));		   
			  //Viết hoa ký tự đầu tiên
			    String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
			    
				String[] arr1 = new String[] {"                                           " + TienIN};
				for (int j = 0; j < arr1.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr1[j], new Font(bf, 10, Font.BOLDITALIC)));
					if (j == 0)
					{
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						cells.setColspan(12);
					} 
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPaddingLeft(0.8f * CONVERT);
					cells.setPaddingTop(5.0f);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f*CONVERT);
					sanpham.addCell(cells);
				}
							*/												
		
			
			document.close();
		
			
		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @param document
	 * @param outstream
	 * @param pxkBean
	 * @throws IOException
	 */
	
	
	
	private String DinhDangTRAPHACO(String sotien)
	{
		sotien = sotien.replaceAll("\\.", "_");
		sotien = sotien.replaceAll(",", "\\.");
		sotien = sotien.replaceAll("_", ",");
		
		return sotien;
	}

/*	public static void main(String[] arg)
	{
		ErpHoadontaichinhNPPPdfSvl hd = new ErpHoadontaichinhNPPPdfSvl();
		
		System.out.println(hd.DinhDangTRAPHACO("12,000.56"));
	}*/
	
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
	
	
	private double roundNumer(double num, int dec)
	{
		double result = Math.round(num*Math.pow(10,dec))/Math.pow(10,dec);
		return result;
	}
	
	private boolean SoNgay (String ngayxuathd){
		boolean kt = false;
		int songay = 0;
		//NẾU NGÀY XUẤT HÓA ĐƠN > '2014-12-08' THÌ ĐƯA VỀ ĐỊNH DẠNG MỚI
		dbutils db = new dbutils();
		String layngay = "select datediff(DD,'2014-01-09', '"+ngayxuathd+"') songay";
		ResultSet checkngay = db.get(layngay);
		
		try{
			if(checkngay.next())
			{
				songay = checkngay.getInt("songay");
				checkngay.close();
			}
			if(songay >=0 ) kt = true;
		}
		catch (Exception e){
			e.printStackTrace();
			kt = false;
		}
		
		return kt;
		
	}

}
