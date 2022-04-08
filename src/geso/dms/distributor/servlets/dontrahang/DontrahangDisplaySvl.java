package geso.dms.distributor.servlets.dontrahang;

import geso.dms.distributor.beans.donhangnhapp.IDonhangnpp;
import geso.dms.distributor.beans.donhangnhapp.ISanPhamDhNpp;
import geso.dms.distributor.beans.donhangnhapp.imp.DonHangNPP;
import geso.dms.distributor.beans.donhangnhapp.imp.SanPhamDhNpp;
import geso.dms.distributor.beans.dontrahang.IDontrahang;
import geso.dms.distributor.beans.dontrahang.imp.Dontrahang;
import geso.dms.distributor.util.Utility;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.oreilly.servlet.MultipartRequest;

public class DontrahangDisplaySvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DontrahangDisplaySvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		
		session.setMaxInactiveInterval(1200);
//		PrintWriter out = response.getWriter();
		String nextJSP;
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    
//	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String id = util.getId(querystring);  	
	    String action = util.getAction(querystring);
//	    out.println(id);
	   
	    IDontrahang dthBean = (IDontrahang) new Dontrahang();
	    dthBean.setUserId(userId);
	    dthBean.setId(id);
	    dthBean.initUpdate();
    	
    	 String printpdf=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("print")));
 		if(printpdf==null)
 			printpdf="";
 		 String dis=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dis")));
			if(dis==null)
				dis="";
			
			 if(!dis.equals("")){
					
				 byte[] buffer=null;
			        
				   
				    	String filename=dthBean.displayfile(id, userId, 1);
				    	if(!filename.equals(""))
				    	{
				    	 try {
						    String pdfFilename = "C:\\upload\\"+filename;    // I use id here to determine pdf
						    File f = new File(pdfFilename);    
		
						    BufferedInputStream is = new BufferedInputStream(new FileInputStream(f));            
						    ByteArrayOutputStream bos = new ByteArrayOutputStream((int)f.length());
		
						    int ch;
						    long actual=0;
						    while((ch=is.read())!=-1){
						        bos.write(ch);    
						        actual++;
						    }
						    bos.flush();
						    bos.close();
						    buffer=bos.toByteArray();    
		
						    response.getOutputStream().write(buffer, 0, buffer.length);
						            
						    }
						    catch (Exception e) {
						    e.printStackTrace();
						    } 
				    	 return;
				    }else
				    {
				    	session.setAttribute("obj", dthBean);	
						String	nextJSP1 = "/AHF/pages/Distributor/NhanHangMuaNhaPPDisplay.jsp";
						response.sendRedirect(nextJSP1);
						return;
				    }
				
			}
 		 if(!printpdf.equals("")){
 			
 					Document document = new Document();
 				ServletOutputStream outstream = response.getOutputStream();
 				try {
					this.CreateDondathangHPC(document, outstream, dthBean);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
 				document.close();
 				return;
 			}	
	   
	    nextJSP = "/AHF/pages/Distributor/DonTraHangDisplay.jsp";
	    	    
		session.setAttribute("dthBean", dthBean);			
	
		response.sendRedirect(nextJSP);
		}

	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	
	}
	private void CreateDondathangHPC(Document document, ServletOutputStream outstream, IDontrahang ddhBean) throws IOException, SQLException
	{
		try{		
			
			ResultSet rs=ddhBean.getthongtinnpp(ddhBean.getNppId());
			while (rs.next()){
			 NumberFormat formatter = new DecimalFormat("#,###,###.###");
		    PdfWriter.getInstance(document, outstream);
			document.open();
			//chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("C:\\WINDOWS\\Fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 12, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.NORMAL);
			Font font3 = new Font(bf, 8, Font.NORMAL);
			 
			Paragraph ddh = new Paragraph("Đề Nghị Trả Hàng Về NCC", font);
			ddh.setSpacingAfter(15);
			ddh.setAlignment(Element.ALIGN_CENTER);
			document.add(ddh);
			
			PdfPTable tableHead = new PdfPTable(2);
			tableHead.setWidthPercentage(90);
			tableHead.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableHead.setSpacingAfter(2);
			float[] with = {24.0f, 90.0f}; //set chieu rong cac columns
			tableHead.setWidths(with);
			
			PdfPCell cell1 = new PdfPCell(new Paragraph("Tên NPP: ", font3));
			PdfPCell cell2 = new PdfPCell(new Paragraph(ddhBean.getNppTen(), font2));
			cell1.setBorder(0);
			cell2.setBorder(0);
			tableHead.addCell(cell1);
			tableHead.addCell(cell2);
		
			PdfPCell cell5 = new PdfPCell(new Paragraph("Fax Đường Biên Hòa:", font3));
			PdfPCell cell6 = new PdfPCell(new Paragraph("", font2));
			cell5.setBorder(0);
			cell6.setBorder(0);
				tableHead.addCell(cell5);
			tableHead.addCell(cell6);
			
			PdfPCell cell9 = new PdfPCell(new Paragraph("Tỉnh/ TP:", font3));
			PdfPCell cell10 = new PdfPCell(new Paragraph(rs.getString("tinh"), font2));
			cell9.setBorder(0);
			cell10.setBorder(0);
			tableHead.addCell(cell9);
			tableHead.addCell(cell10);
			PdfPCell cell11a = new PdfPCell(new Paragraph("Tel/Fax NPP:", font3));
			PdfPCell cell12a = new PdfPCell(new Paragraph(rs.getString("dienthoai")+"/"+rs.getString("fax"), font2));
			cell11a.setBorder(0);
			cell12a.setBorder(0);
			
			tableHead.addCell(cell11a);
			tableHead.addCell(cell12a);
			
			PdfPCell cell13a = new PdfPCell(new Paragraph("Ngày Trả Hàng:", font3));
			PdfPCell cell14a = new PdfPCell(new Paragraph(ddhBean.getNgayth(), font2));
			cell13a.setBorder(0);
			cell14a.setBorder(0);
			
			tableHead.addCell(cell13a);
			tableHead.addCell(cell14a);
		
			tableHead.setSpacingAfter(20.0f);
			document.add(tableHead);
			
			
			PdfPTable table = new PdfPTable(7);
			table.setWidthPercentage(100);
			float[] withs = {12.0f, 19.0f, 10.0f,  10.0f, 10.0f, 10.0f,10.0f};
	        table.setWidths(withs);
	        
	        
			String[] th = new String[]{"Mã SP", "Tên SP", "Tồn kho", "Trả hàng", "Đơn vị", "Đơn giá","T.Tiền"};
			PdfPCell[] cell = new PdfPCell[7];
			for(int i=0; i < 7 ; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], font2));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[i].setPadding(5);
				cell[i].setBackgroundColor(BaseColor.WHITE);
							
				table.addCell(cell[i]);
				
			}
			String[] spId = ddhBean.getSpId() ; 
			String[] masp = ddhBean.getMasp() ; 
			 String[] tensp = ddhBean.getTensp(); 
			 String[] ton = ddhBean.getTon(); 
			 String[] sl = ddhBean.getSl(); 
			 String[] tienbVAT = ddhBean.getTienbVAT(); 
			 String[] dg = ddhBean.getDongia(); 
			 String[] dv = ddhBean.getDonvi() ; 
			 int i=0;
			while(spId[i]!=null)
			 {
				System.out.println(spId[i]+"______________");
				 cell[0] = new PdfPCell(new Paragraph(masp[i], font2));		
					cell[0].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[0].setPadding(4);
					table.addCell(cell[0]);	
					
					cell[1] = new PdfPCell(new Paragraph(tensp[i], font2));		
					cell[1].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell[1].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[1].setPadding(4);
					table.addCell(cell[1]);	
					
					cell[2] = new PdfPCell(new Paragraph(ton[i], font2));		
					cell[2].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell[2].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[2].setPadding(4);
					table.addCell(cell[2]);	
					
					cell[3] = new PdfPCell(new Paragraph(sl[i]+"", font2));		
					cell[3].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell[3].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[3].setPadding(4);
					table.addCell(cell[3]);	
					
					cell[4] = new PdfPCell(new Paragraph(dv[i]+"", font2));		
					cell[4].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell[4].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[4].setPadding(4);
					table.addCell(cell[4]);	
					
			
					
					cell[5] = new PdfPCell(new Paragraph(formatter.format(Double.parseDouble(dg[i])), font2));		
					cell[5].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell[5].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[5].setPadding(4);
					table.addCell(cell[5]);
					
					cell[6] = new PdfPCell(new Paragraph(formatter.format(Double.parseDouble(tienbVAT[i])), font2));		
					cell[6].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell[6].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[6].setPadding(4);
					table.addCell(cell[6]);
					i++;
			 }
			
			document.add(table);
			
			PdfPTable tablefoot = new PdfPTable(2);
			tablefoot.setWidthPercentage(90);
			tablefoot.setHorizontalAlignment(Element.ALIGN_LEFT);
			tablefoot.setSpacingBefore(10);
			
			float[] withf = {350.0f,60.0f}; //set chieu rong cac columns
			tablefoot.setWidths(withf);
			
		
			
			PdfPCell cellf1 = new PdfPCell(new Paragraph("Tổng Tiền:", font3));
			PdfPCell cellf2 = new PdfPCell(new Paragraph(formatter.format(Double.parseDouble(ddhBean.getTongtienbVAT())), font2));
			cellf1.setBorder(0);
			cellf2.setBorder(0);
			cellf1.setPaddingLeft(330);

			tablefoot.addCell(cellf1);
			tablefoot.addCell(cellf2);
		
			PdfPCell cellf3 = new PdfPCell(new Paragraph("Thuế VAT(VND):", font3));
			PdfPCell cellf4 = new PdfPCell(new Paragraph(formatter.format(Double.parseDouble(ddhBean.getVat())), font2));
			cellf3.setBorder(0);
			cellf4.setBorder(0);
			cellf3.setPaddingLeft(330);

		
			tablefoot.addCell(cellf3);
			tablefoot.addCell(cellf4);
			
			PdfPCell cellf5 = new PdfPCell(new Paragraph("Tổng Giá Trị:", font3));
			PdfPCell cellf6 = new PdfPCell(new Paragraph(formatter.format(Double.parseDouble(ddhBean.getTongtienaVAT())), font2));
			cellf5.setBorder(0);
			cellf6.setBorder(0);
			cellf5.setPaddingLeft(330);

		
			tablefoot.addCell(cellf5);
			tablefoot.addCell(cellf6);
			
			document.add(tablefoot);
			document.close();
		
		  
		}
		}
		catch(DocumentException e)
		{
			e.printStackTrace();
		}
		ddhBean.DBclose();
		
	}

}
