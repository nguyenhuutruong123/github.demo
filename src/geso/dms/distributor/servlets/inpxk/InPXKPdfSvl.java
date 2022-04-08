package geso.dms.distributor.servlets.inpxk;

import geso.dms.center.beans.doctien.doctienrachu;
import geso.dms.distributor.beans.inpxk.IInPXK;

import geso.dms.distributor.beans.inpxk.imp.InPXK;

import geso.dms.distributor.beans.donhang.IDonhang;
import geso.dms.distributor.beans.donhang.ISanpham;
import geso.dms.distributor.beans.donhang.imp.Donhang;
import geso.dms.distributor.beans.donhang.imp.Sanpham;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.Hashtable;
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
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class InPXKPdfSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	
    public InPXKPdfSvl()
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
		
		IInPXK pxkBean;
		dbutils db=null;

		
		Utility util = new Utility();
		
		String querystring = request.getQueryString();
		
		if(querystring != null)
		{
			userId = util.getUserId(querystring);
		    
		    if (userId.length() == 0)
		    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		    if(querystring.indexOf("pdf") > 0)
		    {
				String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("pdf")));
				pxkBean = new InPXK(id);
			    pxkBean.setUserId(userId);
			    
			    pxkBean.init2();
			    db = new dbutils();
			    List<ISanpham> sanphamList = this.createPxk_SpList(id, db);
				List<ISanpham> sanphamKMList = this.createPxk_SpkmList(id, db);
				List<ISanpham> tienKMList = this.createPxk_TienkmList(id, db);
			    
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition"," inline; filename=InPXK.pdf");
				
				//hien thi hop thoai dialog save file xuong may Client
				//response.setHeader("Content-Disposition"," attachment; filename=\"Phieuxuatkho.pdf\" ");
				
				Document document = new Document(PageSize.A5.rotate(),10, 20, 10, 20);
				ServletOutputStream outstream = response.getOutputStream();
				
				this.CreatePxk(document, outstream, sanphamList, sanphamKMList, tienKMList, pxkBean);
				
				pxkBean.DBclose();
				pxkBean=null;
				sanphamList=null;
				sanphamKMList=null;
				tienKMList=null;
				util=null;
		    }
		    
		}
		else
		{
			pxkBean = (IInPXK)session.getAttribute("pxkBean");
			
			db = new dbutils();
			List<ISanpham> sanphamList = pxkBean.getPxk_spList();
			List<ISanpham> sanphamKMList = pxkBean.getPxk_spkmList();
			List<ISanpham> tienKMList = pxkBean.getPxk_tienkmList();
			
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition"," inline; filename=InPXK.pdf");
			
			//hien thi hop thoai dialog save file xuong may Client
			//response.setHeader("Content-Disposition"," attachment; filename=\"Phieuxuatkho.pdf\" ");
			
			Document document = new Document(PageSize.A5.rotate(),10, 20, 10, 20);
			ServletOutputStream outstream = response.getOutputStream();
			
			this.CreatePxk(document, outstream, sanphamList, sanphamKMList, tienKMList, pxkBean);
		
			pxkBean.DBclose();
			pxkBean=null;
			sanphamList=null;
			sanphamKMList=null;
			tienKMList=null;
			util=null;
		}
			if(db!=null){
				db.shutDown();
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
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		
		IInPXK pxkBean;
		Utility util = new Utility();
		userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		
		//HttpSession session = request.getSession();
		//pxkBean = (IPhieuxuatkho)session.getAttribute("pxkBean");
		
		String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
		pxkBean = new InPXK(id);
		pxkBean.setUserId(userId);
		pxkBean.init2();
		
	
		List<ISanpham> sanphamList = pxkBean.getPxk_spList();		
		List<ISanpham> sanphamKMList = pxkBean.getPxk_spkmList();
		List<ISanpham> tienKMList = pxkBean.getPxk_tienkmList();
		
		System.out.println("DoPost So san pham la: " + sanphamKMList.size() + "\n");
				
		response.setContentType("application/pdf");
		
		//hien thi hop thoai dialog save file xuong may Client
		response.setHeader("Content-Disposition"," attachment; filename=\"InPXK.pdf\" ");
		
		Document document = new Document(PageSize.A5.rotate(),10, 20, 10, 20);
		ServletOutputStream outstream = response.getOutputStream();
		
		this.CreatePxk(document, outstream, sanphamList, sanphamKMList, tienKMList, pxkBean);
		pxkBean.DBclose();
		pxkBean=null;
		sanphamList=null;
		sanphamKMList=null;
		tienKMList=null;
		util=null;
		
		}
	}
	
	private void CreatePxk(Document document, ServletOutputStream outstream, List<ISanpham> spList, List<ISanpham> sanphamKMList, List<ISanpham> tienKMList, IInPXK pxkBean) throws IOException
	{
		try
		{			
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			PdfWriter.getInstance(document, outstream);
			document.open();

			//chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 9, Font.BOLD);
			Font font3 = new Font(bf, 9, Font.UNDERLINE);
			//font2.setColor(BaseColor.GREEN);
			 
			Paragraph pxk = new Paragraph("In ngày: " + getDateTime(), new Font(bf, 6, Font.BOLDITALIC));
			pxk.setSpacingAfter(2);
			pxk.setSpacingBefore(-25);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			pxk = new Paragraph("Phiếu xuất kho", font);
			pxk.setSpacingAfter(3);
			pxk.setSpacingBefore(-15);
			pxk.setAlignment(Element.ALIGN_CENTER);
			document.add(pxk);
			
			pxk = new Paragraph("(Ngày lập phiếu: " + pxkBean.getNgaylap() + "  --   Số phiếu: " + pxkBean.getId() + ")", font2);
			pxk.setSpacingAfter(5);
			pxk.setAlignment(Element.ALIGN_CENTER);
			document.add(pxk);
			
			PdfPTable tableHead = new PdfPTable(2);
			tableHead.setWidthPercentage(100);
			tableHead.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableHead.setSpacingAfter(5);
			float[] with = {10.0f, 60.0f}; //set chieu rong cac columns
			tableHead.setWidths(with);
			
			PdfPCell cell1 = new PdfPCell(new Paragraph("NV giao nhận: ", font3));
			PdfPCell cell2 = new PdfPCell(new Paragraph(pxkBean.getNvgnTen(), font2));
			cell1.setBorder(0);
			cell2.setBorder(0);
			tableHead.addCell(cell1);
			tableHead.addCell(cell2);
	
			PdfPCell cell5 = new PdfPCell(new Paragraph("Nhà phân phối: ", font3));
			PdfPCell cell6 = new PdfPCell(new Paragraph(pxkBean.getNppTen(), font2));
			cell5.setBorder(0);
			cell6.setBorder(0);
			tableHead.addCell(cell5);
			tableHead.addCell(cell6);
			
			document.add(tableHead);
			
			//Table Content
			PdfPTable root = new PdfPTable(2);
			root.setKeepTogether(false);
			root.setSplitLate(false);
			root.setWidthPercentage(100);
			root.setHorizontalAlignment(Element.ALIGN_LEFT);
			root.getDefaultCell().setBorder(0);
			float[] cr = {95.0f, 100.0f};
			root.setWidths(cr);
			//phan nay de in ma vach kenh MT
			 boolean bien=false;
			 int dong=5;  
			 if(spList.size() >0){
			  Sanpham sanpham1 = (Sanpham)spList.get(0);
		    	System.out.println("Kenh  : "+sanpham1.getDongia());
		    	if(sanpham1.getDongia().equals("Modern Trade")){
		    		bien=true;
		    		dong=6;
		    	}
			 }
		    	
			
			PdfPTable table = new PdfPTable(dong);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			
			if(bien){
				float[]  withs1 = {6.0f, 22.0f,22.0f, 30.0f, 8.0f, 11.0f};
				  table.setWidths(withs1);
			}else{
				float[] withs = {7.0f, 22.0f, 40.0f, 13.0f, 13.0f};
				 table.setWidths(withs);
			}
			
	      
	        
	        Font font4 = new Font(bf, 7);
			PdfPCell cells = new PdfPCell();
			
			PdfPCell tieude = new PdfPCell(new Paragraph("Hàng bán", font2));
			tieude.setColspan(dong);
			tieude.setBorder(0);
			tieude.setPadding(4);
			table.addCell(tieude);
			 
	        if(spList.size() > 0)
	        {
	        	
				  
				   
				    	
				   
				  	
				String[] th = new String[]{"STT", "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Kho"};
	        	if(bien){
	        		th = new String[]{"STT", "Mã sản phẩm","Mã vạch", "Tên sản phẩm", "Số lượng", "Kho"};
	        		
	        	}
				System.out.println(dong);
				PdfPCell[] cell = new PdfPCell[dong];
				for(int i= 0; i < dong ; i++)
				{
					cell[i] = new PdfPCell(new Paragraph(th[i], font4));
					cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[i].setPadding(2);
					//cell[i].setBackgroundColor(BaseColor.CYAN);
					table.addCell(cell[i]);			
				}
				
				int soluong = 0;
				for(int i = 0; i < spList.size(); i++)
				{
					Sanpham sanpham = (Sanpham)spList.get(i);
					System.out.println("Don Gia ma La Kenh :"+sanpham.getDongia());
					
					String[] arr = new String[]{Integer.toString(i+1), sanpham.getMasanpham(), sanpham.getTensanpham(), sanpham.getSoluong(), sanpham.getDonvitinh()};
					if(bien){
						arr = new String[]{Integer.toString(i+1), sanpham.getMasanpham(),sanpham.getBarcode(), sanpham.getTensanpham(), sanpham.getSoluong(), sanpham.getDonvitinh()};

					}
					//System.out.println(sanpham.getMasanpham() + "\n");
					
					for(int j = 0; j < dong; j++)
					{
						cells = new PdfPCell(new Paragraph(arr[j], font4));
						if( j==2 )
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						else
							cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					
						cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cells.setPadding(2);
						table.addCell(cells);
					}
					soluong += Integer.parseInt(sanpham.getSoluong());
				}	
				cells = new PdfPCell(new Paragraph("Tổng cộng", font4));
				cells.setColspan(dong-2);
				cells.setHorizontalAlignment(Element.ALIGN_CENTER);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(5);
				table.addCell(cells);
				
				cells = new PdfPCell(new Paragraph(Integer.toString(soluong), font4));
				cells.setHorizontalAlignment(Element.ALIGN_CENTER);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(5);
				table.addCell(cells);
				
				cells = new PdfPCell();
				//cells.setColspan(2);
				cells.setPadding(2);
				table.addCell(cells);
				
				cells = new PdfPCell(new Paragraph(" ", font4));
				cells.setColspan(dong);
				cells.setBorder(0);
				cells.setPadding(1);
				table.addCell(cells);
				//document.add(table);
	        }
	        
	        root.addCell(table);
	        
	       
			PdfPTable table2 = new PdfPTable(4);
			table2.setWidthPercentage(100);
			table2.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] widths = {13.0f, 57.0f, 16.0f, 19.0f}; //, 14.0f
	        table2.setWidths(widths);

	        tieude = new PdfPCell(new Paragraph("Danh sách khách hàng", font2));
			tieude.setColspan(4);
			tieude.setBorder(0);
			tieude.setPadding(4);
			table2.addCell(tieude);
			
			String[] th2 = new String[]{"Mã", "Tên khách hàng", "Hóa đơn", "Số tiền"}; //, "Nợ cũ"
			 int dongkh=4;
			
		  
		   
			PdfPCell[] c = new PdfPCell[dongkh];
			
			for(int i = 0; i < dongkh ; i++)
			{
				c[i] = new PdfPCell(new Paragraph(th2[i], font4));
				c[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				c[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				c[i].setPadding(2);
				//cell[i].setBackgroundColor(BaseColor.CYAN);
				table2.addCell(c[i]);			
			}

			ResultSet rs = pxkBean.getDhIdsList();
			Hashtable<String, Long> credit = pxkBean.getCredits();
			
			if (rs != null)
			{
				try
				{
					int i = 1;
					float total = 0;
					long nocu=0;
					while(rs.next())
					{								
						c[0] = new PdfPCell(new Paragraph(rs.getString("smartid"), font4));
						c[0].setHorizontalAlignment(Element.ALIGN_CENTER);
						c[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
						c[0].setPadding(2);
						//	cell[i].setBackgroundColor(BaseColor.CYAN);
						table2.addCell(c[0]);
						
						c[1] = new PdfPCell(new Paragraph(rs.getString("khTen")+ " - " + rs.getString("dc"), font4));
						c[1].setHorizontalAlignment(Element.ALIGN_LEFT);
						c[1].setVerticalAlignment(Element.ALIGN_MIDDLE);
						c[1].setPadding(2);
						//	cell[i].setBackgroundColor(BaseColor.CYAN);
						table2.addCell(c[1]);

						c[2] = new PdfPCell(new Paragraph(rs.getString("dhId"), font4));
						c[2].setHorizontalAlignment(Element.ALIGN_CENTER);
						c[2].setVerticalAlignment(Element.ALIGN_MIDDLE);
						c[2].setPadding(2);
						//	cell[i].setBackgroundColor(BaseColor.CYAN);
						table2.addCell(c[2]);
						
						float tonggtri = 0;
						if(rs.getString("tonggiatri") != null)
						{
							tonggtri = Float.parseFloat(rs.getString("tonggiatri"));
						}
						total += tonggtri;
						
						c[3] = new PdfPCell(new Paragraph(formatter.format(tonggtri).toString(), font4));
						c[3].setHorizontalAlignment(Element.ALIGN_CENTER);
						c[3].setVerticalAlignment(Element.ALIGN_MIDDLE);
						c[3].setPadding(2);
						//	cell[i].setBackgroundColor(BaseColor.CYAN);
						table2.addCell(c[3]);
						
						/*if(rs.getString("khId") != null)
						{
							if(credit.containsKey(rs.getString("khId")))
							{
								nocu = nocu + (Long)credit.get(rs.getString("khId")).longValue();
								c[4] = new PdfPCell(new Paragraph(formatter.format((Long)credit.get(rs.getString("khId")).longValue()), font4));
							}
							else{
								c[4] = new PdfPCell(new Paragraph("", font4));
							}
						}
						c[4].setHorizontalAlignment(Element.ALIGN_CENTER);
						c[4].setVerticalAlignment(Element.ALIGN_MIDDLE);
						c[4].setPadding(2);
						//	cell[i].setBackgroundColor(BaseColor.CYAN);
						table2.addCell(c[4]);*/
						
						i++;
					}
					c[0] = new PdfPCell(new Paragraph("Tổng cộng", font4));
					c[0].setHorizontalAlignment(Element.ALIGN_CENTER);
					c[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
					c[0].setPadding(5);
					//	cell[i].setBackgroundColor(BaseColor.CYAN);
					c[0].setColspan(2);
					table2.addCell(c[0]);
					
					c[1] = new PdfPCell(new Paragraph(Integer.toString(i-1), font4));
					c[1].setHorizontalAlignment(Element.ALIGN_CENTER);
					c[1].setVerticalAlignment(Element.ALIGN_MIDDLE);
					c[1].setPadding(5);
					//	cell[i].setBackgroundColor(BaseColor.CYAN);
					
					table2.addCell(c[1]);

					c[2] = new PdfPCell(new Paragraph(formatter.format(Math.round(total)).toString(), font4));
					c[2].setHorizontalAlignment(Element.ALIGN_CENTER);
					c[2].setVerticalAlignment(Element.ALIGN_MIDDLE);
					c[2].setPadding(2);
					//	cell[i].setBackgroundColor(BaseColor.CYAN);
					
					table2.addCell(c[2]);

					/*c[3] = new PdfPCell(new Paragraph(formatter.format(nocu).toString(), font4));
					c[3].setHorizontalAlignment(Element.ALIGN_CENTER);
					c[3].setVerticalAlignment(Element.ALIGN_MIDDLE);
					c[3].setPadding(2);
					//	cell[i].setBackgroundColor(BaseColor.CYAN);
					table2.addCell(c[3]);*/
					
					cells = new PdfPCell(new Paragraph(" ", font4));
					cells.setColspan(4);
					cells.setPadding(1);
					cells.setBorder(0);
					table2.addCell(cells);
				}
				catch(Exception e){}
			}
			
			root.addCell(table2);

			document.add(root);
			
			if(sanphamKMList.size() > 0)
			{
			int	dongkm=6;
			if(bien){
				dongkm=7;
			}
				PdfPTable tableKM = new PdfPTable(dongkm);
				tableKM.setWidthPercentage(100);
				tableKM.setHorizontalAlignment(Element.ALIGN_LEFT);
				if(bien){
					float[] withsKM = {7.0f, 65.0f, 20.0f,20.0f, 30.0f, 18.0f, 15.0f};
				    tableKM.setWidths(withsKM);
				}else{
					float[] withsKM = {7.0f, 65.0f, 20.0f, 30.0f, 18.0f, 15.0f};
				    tableKM.setWidths(withsKM);
				}
			    tieude = new PdfPCell(new Paragraph("Hàng khuyến mãi", font2));
				tieude.setColspan(dongkm);
				tieude.setBorder(0);
				tieude.setPadding(4);
				tableKM.addCell(tieude);
				String[] thKM = new String[]{"STT", "Chương trình", "Mã sản phẩm", "Tên sản phẩm", "Số lượng / Số tiền", "Kho"};
				
				if(bien){
					 thKM = new String[]{"STT", "Chương trình", "Mã sản phẩm","Mã vạch", "Tên sản phẩm", "Số lượng / Số tiền", "Kho"};
				}
				PdfPCell[] cellKM = new PdfPCell[dongkm];
				for(int i = 0; i < dongkm ; i++)
				{
					cellKM[i] = new PdfPCell(new Paragraph(thKM[i], font4));
					cellKM[i].setHorizontalAlignment(Element.ALIGN_CENTER);
					cellKM[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cellKM[i].setPadding(2);
					tableKM.addCell(cellKM[i]);			
				}
				
				int m = 0;
				while(m < sanphamKMList.size())
				{
					Sanpham sanpham = (Sanpham)sanphamKMList.get(m);
					String masanpham=sanpham.getMasanpham();
					
					String[] arr = new String[]{Integer.toString(m+1), sanpham.getCTKM(), masanpham, sanpham.getTensanpham(), sanpham.getSoluong(), sanpham.getDonvitinh(), sanpham.getDongia()};
					if(bien){
						 arr = new String[]{Integer.toString(m+1), sanpham.getCTKM(), masanpham,sanpham.getBarcode(), sanpham.getTensanpham(), sanpham.getSoluong(), sanpham.getDonvitinh(), sanpham.getDongia()};
					}
					cells = new PdfPCell();
					
					for(int j = 0; j < dongkm; j++)
					{
						cells = new PdfPCell(new Paragraph(arr[j], font4));
						if(j==3 || j == 1)
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						else
							cells.setHorizontalAlignment(Element.ALIGN_CENTER);
						cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cells.setPadding(2);
						tableKM.addCell(cells);
					}
					m++;
				}		
				
				int n = 0;
				while( n < tienKMList.size())
				{
					Sanpham sanpham = (Sanpham)tienKMList.get(n);
					String[] arr = new String[]{Integer.toString(m), sanpham.getCTKM(), "", "", sanpham.getChietkhau(), "", ""};
					if(bien){
						arr = new String[]{Integer.toString(m), sanpham.getCTKM(), "","", "", sanpham.getChietkhau(), "", ""};

					}
					cells = new PdfPCell();
					
					for(int j = 0; j < dongkm; j++)
					{
						cells = new PdfPCell(new Paragraph(arr[j], font4));
						if(j==3)
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						else
							cells.setHorizontalAlignment(Element.ALIGN_CENTER);
						cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cells.setPadding(2);
						tableKM.addCell(cells);
					}
					n++;
					m++;
				}		
					
				document.add(tableKM);
			}
			
			//Table Footer			
			PdfPTable tableFooter = new PdfPTable(4);
			tableFooter.setWidthPercentage(90);
			tableFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableFooter.setSpacingBefore(15);
			tableFooter.setWidths(new float[]{20.0f, 30.0f, 30.0f, 25.0f});
			
			PdfPCell cell11 = new PdfPCell(new Paragraph("Thủ kho", font2));
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell12 = new PdfPCell(new Paragraph("Nhân viên giao nhận", font2));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell13 = new PdfPCell(new Paragraph("Kế toán hàng hóa", font2));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell14 = new PdfPCell(new Paragraph("Kế toán công nợ", font2));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell11.setBorder(0);
			cell12.setBorder(0);
			cell13.setBorder(0);
			cell14.setBorder(0);
			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			tableFooter.addCell(cell13);
			tableFooter.addCell(cell14);
			document.add(tableFooter);
			
			Paragraph pxk0=new Paragraph("Địa chỉ :",new Font(bf,8));
			pxk0.setSpacingBefore(90);
			document.add(pxk0);
			dbutils db = new dbutils();
			
		    String query = "select b.pk_seq as dhId, b.ngaynhap, isnull(a.tonggiatri, '0') as tonggiatri,c.smartid, " +
		    		" c.pk_seq as khId, c.ten as khTen, c.diachi as dc ,isnull( c.phuong,'N/A') as phuong  ,isnull(qh.ten,'') as quanhuyen " + 
	                " from phieuxuatkho_donhang a inner join donhang b on a.donhang_fk = b.pk_seq  " +
	        		" inner join khachhang c on b.khachhang_fk = c.pk_seq " +
	        		" left join quanhuyen qh on qh.pk_seq=c.quanhuyen_fk " +
	        		"  where a.pxk_fk = '" +pxkBean.getId()+ "' "; 
		    System.out.println("Cau Lenh Query : "+query);
			 rs = db.get(query);
			 int j=0;
			try {
				while (rs.next())
				{

					Paragraph pxk1 = new Paragraph(rs.getString("khTen")+ " - " + rs.getString("dc")+" -- "+ "P." +rs.getString("phuong")+"---Quận."+ rs.getString("quanhuyen") , new Font(bf, 6));
					if(j==0)
						pxk1.setSpacingBefore(5);	
					else 
						pxk1.setSpacingBefore(5);	
					document.add(pxk1);
					j++;
				}
				rs.close();
			} catch (Exception e) {
			
			}

			
			
			document.newPage();
			//tao danh sach don hang
		
			String[] dhId = pxkBean.getDhIds();
			System.out.println("So phan tu trong don hang la: " + dhId.length);
			if(dhId != null)
			{
			for(int i=0; i < dhId.length; i++)
			{
				createDonHangPdf(document, dhId[i], db, pxkBean.getUserId());
			}
			}
			db.shutDown();
			document.close(); 
		}
		catch(DocumentException e)
		{
			e.printStackTrace();
			System.out.println("Error Here  : "+e.toString());
		}
	}
	

	private void createDonHangPdf(Document document, String id, dbutils db, String userId) 
	{
		try 
		{
			IDonhang dhBean;
			dhBean = new Donhang(id);
			dhBean.setUserId(userId); //phai co UserId truoc khi Init
			dhBean.init();
			dhBean.setKhId(dhBean.getKhId());
	
			//dhBean.CreateSpDisplay();
		
			List<ISanpham> spList = (List<ISanpham>)dhBean.getSpList();
			List<ISanpham> spkmlist = (List<ISanpham>)dhBean.getScheme_SpList();
			Hashtable<String, Float> scheme_tongtien = dhBean.getScheme_Tongtien(); 
			Hashtable<String, Float> scheme_chietkhau = dhBean.getScheme_Chietkhau();
			
			System.out.println("----So san pham: " + spList.size());
			
			String tennpp = "";
			String diachi = "";
			String dienthoai = "";
			String masothue = "";
			
			String sql_getinfodis = "select ten,isnull(diachi,'') as diachi, isnull( masothue, '') as masothue," +
									"isnull( dienthoai,'') as dienthoai from nhaphanphoi where pk_seq='" + dhBean.getNppId() + "'";
			
			ResultSet rs=db.get(sql_getinfodis);
			try
			{
				if(rs.next()){
					tennpp=rs.getString("ten");
					diachi=rs.getString("diachi");
					dienthoai=rs.getString("dienthoai");
					masothue=rs.getString("masothue");
				}
				rs.close();
			}
			catch(Exception er){}
			
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			NumberFormat formatter1 = new DecimalFormat("#,###,###"); 
			
			//chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 15, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			Font fontnomar=new Font(bf,10,Font.NORMAL);
			//font2.setColor(BaseColor.GREEN);
			 //KHAI BAO 1 BANG CO BAO NHIEU COT
			
			PdfPTable tableheader=new PdfPTable(3);
			tableheader.setWidthPercentage(100);//chieu dai cua báº£ng 
			tableheader.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withsheader = {30.0f, 55.0f, 25.0f};//SET DO RONG CAC COT
			tableheader.setWidths(withsheader); 
			
			Image hinhanh=Image.getInstance( getServletContext().getInitParameter("path")+"/images/logosgp.png");	
			hinhanh.setAlignment(Element.ALIGN_CENTER);
			hinhanh.scalePercent(68);
			//hinhanh.setAbsolutePosition(1.0f * CONVERT, document.getPageSize().getHeight() - 2.0f * CONVERT);
			PdfPCell cellslogo = new PdfPCell(hinhanh);
			
			
			cellslogo.setPaddingTop(1);
			cellslogo.setFixedHeight(78.4f);
			cellslogo.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			tableheader.addCell(cellslogo);
			
			Paragraph pxk = new Paragraph("PHIẾU GIAO HÀNG", font);
			pxk.setSpacingAfter(5);
			pxk.setAlignment(Element.ALIGN_CENTER);
			PdfPCell celltieude = new PdfPCell();
			celltieude.addElement(pxk);
			
			Paragraph dvbh = new Paragraph("Nhà phân phối: " + tennpp, fontnomar);
			dvbh.setSpacingAfter(2);
			dvbh.setAlignment(Element.ALIGN_LEFT);
			celltieude.addElement(dvbh);
			
			dvbh = new Paragraph("Điện thoại: " + dienthoai, fontnomar);
			dvbh.setSpacingAfter(2);
			dvbh.setAlignment(Element.ALIGN_LEFT);
			celltieude.addElement(dvbh);
			
			dvbh = new Paragraph("Địa chỉ: " + diachi, fontnomar);
			dvbh.setSpacingAfter(4);
			dvbh.setAlignment(Element.ALIGN_LEFT);
			celltieude.addElement(dvbh);
	
			tableheader.addCell(celltieude);
			
			PdfPCell cellinfo = new PdfPCell();
			
			cellinfo.addElement(new Paragraph("Số chứng từ    : " + dhBean.getId(),fontnomar));
						 
			String ngay = dhBean.getNgaygiaodich().substring(8,10)+ "-" +dhBean.getNgaygiaodich().substring(5,7)+"-"+dhBean.getNgaygiaodich().substring(0,4);
			cellinfo.addElement(new Paragraph("Ngày chứng từ: " + ngay,fontnomar));
			tableheader.addCell(cellinfo);
						
			//Add bang vao document
			document.add(tableheader);						
			
			PdfPTable table_info=new PdfPTable(1);
			float[] with3 = {50.0f};//SET DO RONG CAC COT
			table_info.setWidthPercentage(100);
			table_info.setWidths(with3);
			PdfPCell cell111= new PdfPCell();
			
			String tenkh="";
			String diachikh="";
			String dienthoaikh="";
			String masothuekh="";
			String quanhuyen="";
			String tinhthanh="";
			String phuongxa="";
			String sql_getinfokh =	"select khachhang.ten,isnull(khachhang.dienthoai,'') as dienthoai,isnull(khachhang.diachi,'') as diachi,isnull(q.TEN,'')as quan,isnull(t.TEN,'')as tinhthanh,isnull(khachhang.phuong,'')as phuongxa,isnull(khachhang.masothue,'') as masothue " +
									"from khachhang left join QUANHUYEN q ON q.pk_seq=khachhang.quanhuyen_fk left join TINHTHANH t on t.pk_seq=khachhang.tinhthanh_fk where khachhang.pk_seq=" +dhBean.getKhId();
			System.out.println(sql_getinfokh);
			ResultSet rs_kh=db.get(sql_getinfokh);
			try{
				if(rs_kh.next()){
					tenkh= rs_kh.getString("ten");
					diachikh= rs_kh.getString("diachi");
					dienthoaikh= rs_kh.getString("dienthoai");
					masothuekh= rs_kh.getString("masothue");
					quanhuyen=rs_kh.getString("quan");
					tinhthanh=rs_kh.getString("tinhthanh");
					phuongxa=rs_kh.getString("phuongxa");
				}
				rs_kh.close();
			}catch(Exception er )
			{
				er.printStackTrace();
				System.out.println("Loi Khach Hang: " + er.toString());
			}
			//lay dai dien kinh doanh
			String ddkdten="";
			String dienthoaiDDKD = "";
			String sql_getddkd	=	"select pk_seq, ten, isnull(dienthoai, '') as dienthoai " +
								"from daidienkinhdoanh where pk_seq="+dhBean.getDdkdId();
			ResultSet rs_getddkd = db.get(sql_getddkd);
			System.out.println(sql_getddkd);
			try
			{
				if(rs_getddkd.next())
				{
					ddkdten = rs_getddkd.getString("ten");
					dienthoaiDDKD = rs_getddkd.getString("dienthoai");
				}
				rs_getddkd.close();
			}
			catch(Exception er ){
				System.out.println("Loi DDkD : " + er.toString());
			}
			
			
			String ddkd = "Đại diện kinh doanh: " + ddkdten;
			if(dienthoaiDDKD.trim().equals("NA"))
				dienthoaiDDKD="";
			if(dienthoaiDDKD.trim().equals("N/A"))
				dienthoaiDDKD="";
			if(dienthoaiDDKD.length() > 0)
				ddkd += " (ĐT:" + dienthoaiDDKD + ")";
			
			cell111.addElement(new Paragraph(ddkd, fontnomar));
			
			String nocu = "";
			if(dhBean.getNoCu() == 0)
				nocu = "";
			else
				nocu = formatter1.format(dhBean.getNoCu());
			String dd_khachhang = "Mã khách hàng: " + dhBean.getSmartId() + "     Tên khách hàng : " + tenkh ;
			if(dienthoaikh.length() > 0)
				dd_khachhang += " (" + dienthoaikh + ")";
			
			cell111.addElement(new Paragraph(dd_khachhang ,fontnomar));
			if(phuongxa.length()!=0)
				phuongxa=", phường "+phuongxa;
			if(quanhuyen.length()!=0)
				quanhuyen=", quận " +quanhuyen;
			if(tinhthanh.length()!=0)
				tinhthanh=", Tỉnh/Tp "+tinhthanh;
			cell111.addElement(new Paragraph("Địa chỉ: " + diachikh + phuongxa +quanhuyen  +tinhthanh ,fontnomar));
			cell111.setPaddingBottom(10);
			table_info.addCell(cell111);
			
			
			document.add(table_info);
			//Table Content
			PdfPTable table = new PdfPTable(10);//Chu y nhe 6 cot o day, thi xuong duoi nho set withs la 6 cot
			table.setWidthPercentage(100);//chieu dai cua báº£ng 
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withs = {5.0f,14.0f,30.0f,10.0f, 8.0f, 8.0f, 10.0f,5.0f,0.0f,12.0f}; 			
			table.setWidths(withs);
			String[] th = new String[]{"STT","Mã hàng", "Tên hàng","Kho","Số lượng","ĐVT","Đơn giá","%CK","","Thành tiền"};
			PdfPCell[] cell = new PdfPCell[10];
			for(int i=0; i < 10 ; i++)
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

			String query=
					"	select d.ten as khoTen,c.donvi as spDonVi, b.ma as spMa,b.ten as spTen,a.SoLuong,a.GiaMua,a.DonGiaGoc, isnull(ptCKDH,0) as ckDH  "+
							"	,isnull(ptCKTT,0) as ckTT,isnull(ptCKDLN,0) as ckDLN,isnull(ptCKTT,0)+isnull(ptCKDH,0)+isnull(ptCKDLN,0) as TongCK, (select isnull(chietkhau,0) from donhang where pk_seq = a.donhang_fk )as chietkhau ,(select isnull(Sotiengiam,0) from donhang where pk_seq = a.donhang_fk )as sotiengiam  "+
							" from DonHang_SanPham a inner join SanPham b on b.pk_Seq=a.sanpham_fk  "+
							"   inner join DonViDoLuong c on c.pk_Seq=b.DVDL_FK  "+
							"  INNER JOIN Kho d on d.pk_Seq=a.Kho_FK  " +
							" where a.donhang_fk='"+dhBean.getId()+"'  ";

			  rs = db.get(query);
			  int i=0;
			  double totalTienCK=0;
			  double sotiengiam=0;
			  double ckdh = 0;
			try
			{
				while(rs.next())
				{
					i++;
					String spMa= rs.getString("spMa");
					String spTen= rs.getString("spTen");
					String spDonVi= rs.getString("spDonVi");
					String khoTen= rs.getString("khoTen");
					double SoLuong =rs.getDouble("SoLuong");
					double GiaSauCK =rs.getDouble("GiaMua");
					double DonGiaGoc =rs.getDouble("DonGiaGoc");
					double totalCK =rs.getDouble("TongCK");
					double thanhTien = SoLuong*( DonGiaGoc) ;
					double tienSauCK = SoLuong*( DonGiaGoc *(1-totalCK/100)) ;
					double tienCK = thanhTien -tienSauCK;
					totalTienCK+=tienCK;
					ckdh =rs.getDouble("chietkhau");
					 sotiengiam =rs.getDouble("sotiengiam");
					
					totalle+= thanhTien;
					String[] arr = new String[]{Integer.toString(i), spMa, spTen, khoTen,formatter.format(SoLuong ), spDonVi ,formatter1.format(DonGiaGoc), formatter1.format( totalCK )  ,"", formatter1.format( thanhTien ) };
					for(int j=0; j < 10; j++)
					{
						cells_detail = new PdfPCell(new Paragraph(arr[j], font4));
						if(j==2)
						{
							cells_detail.setHorizontalAlignment(Element.ALIGN_LEFT);
						}else
						{
							cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
						cells_detail.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cells_detail);
					}
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}

			if(spkmlist.size()>0)
			{
				PdfPCell cellkhuyenmai = new PdfPCell();
				cellkhuyenmai.setHorizontalAlignment(Element.ALIGN_CENTER);
				Paragraph km1=new Paragraph("Hàng khuyến mại", font2);
				km1.setAlignment(Element.ALIGN_LEFT);
				cellkhuyenmai.addElement(km1);
				cellkhuyenmai.setColspan(10);
				table.addCell(cellkhuyenmai);
			}
			//hang khuyen mai
			int demtt=0;
			for(i = 0; i < spkmlist.size(); i++)
			{
				ISanpham sanpham = (ISanpham)spkmlist.get(i);
				double dongia=Double.parseDouble(sanpham.getDongia());

				double chietkhau=Double.parseDouble(sanpham.getSoluong())* Double.parseDouble(sanpham.getDongia()) /100 * Double.parseDouble(sanpham.getChietkhau());
				String sql_getkho="select kho_fk,kho.ten  from ctkhuyenmai inner join kho on  kho.pk_Seq=kho_fk  where scheme ='"+ sanpham.getCTKM()+"'";
				rs = db.get(sql_getkho);
				String TenKho="Khuyến mại";
				try
				{
					if(rs.next())
					{
						TenKho=rs.getString("ten");
					}
					rs.close();
				}catch(Exception er)
				{
					System.out.println("Loi tai day :"+er.toString());
				}
				demtt=demtt+1;
				String[] arr = new String[]{""+demtt,sanpham.getMasanpham(), sanpham.getTensanpham(),TenKho ,sanpham.getSoluong(),sanpham.getDonvitinh(),formatter1.format(dongia),formatter1.format(Double.parseDouble(sanpham.getChietkhau())),formatter.format(chietkhau),formatter.format(Double.parseDouble(sanpham.getSoluong())* Double.parseDouble(sanpham.getDongia())-chietkhau )};

				for(int j=0; j < 10; j++)
				{
					cells_detail = new PdfPCell(new Paragraph(arr[j], font4));
					if(j==2){
						cells_detail.setHorizontalAlignment(Element.ALIGN_LEFT);
					}else{
						cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
					}

					cells_detail.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cells_detail);	
				}
			}	
			//Khai  bao 1 bien luu tien khuyen mai
			float tongtienkhuyenmai=0;
			// Xuat ra khuyen mai tra tien
			if(scheme_tongtien.size() > 0)
			{
				Enumeration<String> keys = scheme_tongtien.keys();
				while(keys.hasMoreElements())
				{
					String key = keys.nextElement();
					tongtienkhuyenmai=tongtienkhuyenmai+scheme_tongtien.get(key);
					String tongtien= Float.toString(scheme_tongtien.get(key));
					String sql_tenschem="select diengiai from ctkhuyenmai where scheme='"+key+"'";
					rs	=	db.get(sql_tenschem);
					String Tenschem="";
					try{
						if(rs.next()){					
							Tenschem=rs.getString("diengiai");						
						}
						rs.close();
					}catch(Exception er){
						System.out.println("Loi tai day :"+er.toString());
					}
					demtt=demtt+1;
					String[] arr = new String[]{""+demtt,key,Tenschem, "", "" ,"" ,"","","","-"+tongtien };
					for(int j=0; j < 10; j++)
					{
						cells_detail = new PdfPCell(new Paragraph(arr[j], font4));
						if(j==2){
							cells_detail.setHorizontalAlignment(Element.ALIGN_LEFT);
						}else{
							cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
						}

						cells_detail.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cells_detail);

					}
				}

			}

			if(scheme_chietkhau.size() > 0)
			{

				Enumeration<String> keys = scheme_chietkhau.keys();
				while(keys.hasMoreElements())
				{
					String key = keys.nextElement();
					String sql_tenschem="select diengiai from ctkhuyenmai where scheme='"+key+"'";
					rs	=	db.get(sql_tenschem);
					String Tenschem="";
					try{
						if(rs.next()){						
							Tenschem=rs.getString("diengiai");					
						}
						rs.close();
					}catch(Exception er){
						System.out.println("Loi tai day :"+er.toString());
					}

					tongtienkhuyenmai=tongtienkhuyenmai+scheme_chietkhau.get(key);
					String tienchietkhau= Float.toString(scheme_chietkhau.get(key));
					demtt=demtt+1;
					String[] arr = new String[]{""+demtt,key,Tenschem, "", "" ,"" ,"","","-"+tienchietkhau,"" };
					for(int j=0; j < 10; j++)
					{
						cells_detail = new PdfPCell(new Paragraph(arr[j], font4));
						if(j==2){
							cells_detail.setHorizontalAlignment(Element.ALIGN_LEFT);
						}else{
							cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
						cells_detail.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cells_detail);

					}
				}

			}

			PdfPCell cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			Paragraph km=new Paragraph("Cộng tiền hàng ", font2);
			km.setAlignment(Element.ALIGN_RIGHT);
			cell11.addElement(km);
			cell11.setColspan(9);
			table.addCell(cell11);

			PdfPCell cell12 = new PdfPCell();
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			Paragraph tongtien =new Paragraph( formatter1.format(totalle), font2);
			tongtien.setAlignment(Element.ALIGN_RIGHT);
			cell12.addElement(tongtien);
			table.addCell(cell12);
			
			// Cộng thêm chiết khấu tổng đơn hàng
			totalTienCK += (totalle*(ckdh/100.0));
			if(totalTienCK>0)
			{
				
				cell11 = new PdfPCell();
				cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
				 km=new Paragraph("Tiền chiết khấu ", font2);
				km.setAlignment(Element.ALIGN_RIGHT);
				cell11.addElement(km);
				cell11.setColspan(9);
				table.addCell(cell11);

				 cell12 = new PdfPCell();
				cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
				 tongtien =new Paragraph( formatter1.format(totalTienCK), font2);
				tongtien.setAlignment(Element.ALIGN_RIGHT);
				cell12.addElement(tongtien);
				table.addCell(cell12);
				
				
			}
			
			if(sotiengiam>0)
			{
				
				cell11 = new PdfPCell();
				cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
				 km=new Paragraph("Số tiền giảm ", font2);
				km.setAlignment(Element.ALIGN_RIGHT);
				cell11.addElement(km);
				cell11.setColspan(9);
				table.addCell(cell11);

				 cell12 = new PdfPCell();
				cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
				 tongtien =new Paragraph( formatter1.format(sotiengiam), font2);
				tongtien.setAlignment(Element.ALIGN_RIGHT);
				cell12.addElement(tongtien);
				table.addCell(cell12);
				
				
			}

			
			if(tongtienkhuyenmai>0)
			{
				cell11 = new PdfPCell();
				cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
				km=new Paragraph("Tổng số tiền chiết khấu khuyến mại ", font2);
				km.setAlignment(Element.ALIGN_RIGHT);
				cell11.addElement(km);
				cell11.setColspan(9);
				table.addCell(cell11);

				cell12 = new PdfPCell();
				cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
				tongtien =new Paragraph(formatter1.format(tongtienkhuyenmai) , font2);
				tongtien.setAlignment(Element.ALIGN_RIGHT);
				cell12.addElement(tongtien);
				table.addCell(cell12);
				
			}
			
			//
			cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_RIGHT);
			km=new Paragraph("Tổng tiền thanh toán(đã làm tròn) ", font2);
			km.setAlignment(Element.ALIGN_RIGHT);
			cell11.addElement(km);
			cell11.setColspan(9);
			table.addCell(cell11);

			//
			//double tongtiensauthue = totalle / 100 * 10 + totalle;
			double tongtiensauthue = totalle;
			cell12 = new PdfPCell();
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			tongtien =new Paragraph(formatter.format(tongtiensauthue - tongtienkhuyenmai-totalTienCK) , font2);
			tongtien.setAlignment(Element.ALIGN_RIGHT);
			cell12.addElement(tongtien);
			table.addCell(cell12);

			//doc tien bang chu
			doctienrachu doctien=new doctienrachu();
			cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			Long tongtienst= Math.round(tongtiensauthue - tongtienkhuyenmai-totalTienCK);
			System.out.println("[tongtiensauthue]"+tongtiensauthue+"[tongtienkhuyenmai]"+tongtienkhuyenmai);
			km=new Paragraph("Số tiền bằng chữ : " + doctien.tranlate(tongtienst+""),  font2);
			km.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(km);
			cell11.setColspan(10);
			table.addCell(cell11);

			document.add(table);


			PdfPTable tablefooter = new PdfPTable(5);
			tablefooter.setWidthPercentage(100);//chieu dai cua báº£ng 
			tablefooter.setHorizontalAlignment(Element.ALIGN_CENTER);
			float[] withfooterder = {30.0f, 30.0f, 30.0f, 30.0f, 30.0f};//SET DO RONG CAC COT
			tablefooter.setWidths(withfooterder); 

			//nguoimua hang 
			Paragraph para = new Paragraph("Khách hàng", fontnomar);

			para.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(para);
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell11.setBorderWidthRight(0);
			tablefooter.addCell(para);

			//nvgn
			para = new Paragraph("Giao nhận", fontnomar);
			para.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(para);
			cell11.setBorderWidthLeft(0);
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell11.setBorderWidthRight(0);
			tablefooter.addCell(para);

			//o giua
			para = new Paragraph("Thủ kho", fontnomar);
			para.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(para);
			cell11.setBorderWidthLeft(0);
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell11.setBorderWidthRight(0);
			tablefooter.addCell(para);

			//Káº¿ toÃ¡n
			para = new Paragraph("Kế toán", fontnomar);
			para.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(para);
			cell11.setBorderWidthLeft(0);
			cell11.setBorderWidthRight(0);
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablefooter.addCell(para);

			//nguoi nhan hang
			para = new Paragraph("Người lập phiếu", fontnomar);
			para.add("\n");
			para.add("\n");
			para.add("\n");
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
			
			document.newPage();
		} 
		catch (Exception e) 
		{
			System.out.println("115.Exception: " + e.getMessage());
		}

	}
	
	
	private String getDateTime()
	{
		String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
	    return sdf.format(cal.getTime());
	}
	
	private List<ISanpham> createPxk_SpList(String pxkId, dbutils db)
	{
		List<ISanpham> pxk_splist = new ArrayList<ISanpham>();
		String query = "select a.sanpham_fk as spId, isnull(b.barcode,'') as barcode, b.ma as spMa, a.soluong, b.ten as spTen, c.ten as khoTen, d.ten as kbhTen from phieuxuatkho_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq ";
		query += " left join kho c on a.kho_fk = c.pk_seq left join kenhbanhang d on a.kbh_fk = d.pk_seq where a.pxk_fk = '" + pxkId + "'";
		
		System.out.print("SPLIST_NPXK: " + query + "\n");
		
		ResultSet rsPxk_sp = db.get(query);
		if(rsPxk_sp != null)
		{
			String[] param = new String[11];
			ISanpham sp = null;	
			try 
			{
				while(rsPxk_sp.next())
				{
					param[0] = rsPxk_sp.getString("spId");
					param[1] = rsPxk_sp.getString("spMa");
					param[2] = rsPxk_sp.getString("spTen");
					param[3] = rsPxk_sp.getString("soluong");
					
					param[4] = "";
					if(rsPxk_sp.getString("khoTen") != null)
						param[4] = rsPxk_sp.getString("khoTen");
					
					param[5] = "";
					if(rsPxk_sp.getString("kbhTen") != null)
						param[5] = rsPxk_sp.getString("kbhTen");
					
					param[6] = "";
					param[7] = "";
					param[8]=rsPxk_sp.getString("barcode");
					
					sp = new Sanpham(param);
					pxk_splist.add(sp);
				}
				rsPxk_sp.close();
			} 
			catch(Exception e) {
				e.printStackTrace();
			}
			finally{try {
				if(rsPxk_sp != null)
					rsPxk_sp.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}}
		}
		return pxk_splist;
	}
	
	private List<ISanpham> createPxk_SpkmList(String pxkId, dbutils db)
	{
		List<ISanpham> pxk_spkmlist = new ArrayList<ISanpham>();
		String query = "select c.ten as khoTen, d.ten as kbhTen, e.scheme + '-' + e.diengiai as ctkmTen, a.sanpham_fk as spId, isnull(b.barcode,'') as barcode," +
				" b.ma as spMa, b.ten as spTen, sum(a.soluong) as soluong from phieuxuatkho_spkm a inner " +
				" join sanpham b on a.sanpham_fk = b.pk_seq inner join kho c on a.kho_fk = c.pk_seq ";
		query += "inner join kenhbanhang d on a.kbh_fk = d.pk_seq inner join ctkhuyenmai e  " +
				"on a.scheme = e.pk_seq where a.pxk_fk = '" + pxkId + "' group by c.ten, d.ten, " +
						" e.scheme + '-' + e.diengiai, a.sanpham_fk, b.ma, b.ten ,b.barcode ";
		System.out.println("Get San Pham Khuyen Mai : "+query);
		ResultSet rsPxk_spkm = db.get(query);
		if(rsPxk_spkm != null)
		{	
			try
			{
				while(rsPxk_spkm.next())
				{
					String[] param = new String[11];
					ISanpham sp = null;
					
					param[0] = rsPxk_spkm.getString("spId");					
					param[1] = rsPxk_spkm.getString("spMa");		
					param[2] = rsPxk_spkm.getString("spTen");
					param[3] = rsPxk_spkm.getString("soluong");
					
					//luu kho
					param[4] = "";
					if(rsPxk_spkm.getString("khoTen") != null)
						param[4] = rsPxk_spkm.getString("khoTen");
					
					//luu kenh ban hang
					param[5] = "";
					if(rsPxk_spkm.getString("kbhTen") != null)
						param[5] = rsPxk_spkm.getString("kbhTen");
					System.out.println("Kenh ban hang : "+param[5]);	
					//luu ten ctkm
					param[6] = "";
					if(rsPxk_spkm.getString("ctkmTen") != null)
						param[6] = rsPxk_spkm.getString("ctkmTen");
					
					param[7] = "";
					param[8]=rsPxk_spkm.getString("barcode");
					
					sp = new Sanpham(param);
					pxk_spkmlist.add(sp);
				}
				rsPxk_spkm.close();
			} 
			catch(Exception e) {}
			finally{try {
				if( rsPxk_spkm != null)
					rsPxk_spkm.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}}
		}
		return pxk_spkmlist;
	}
	
	private List<ISanpham> createPxk_TienkmList(String pxkId, dbutils db) 
	{
		List<ISanpham> pxk_tienkmlist = new ArrayList<ISanpham>();
		String query = "select b.scheme + '-' + b.diengiai as ctkmTen, sum(a.tonggiatri) as tonggiatri from phieuxuatkho_tienkm a inner join ctkhuyenmai b on a.scheme = b.pk_seq ";
		query += " where a.pxk_fk = '" + pxkId + "' group by b.scheme + '-' + b.diengiai";
		
		ResultSet rsPxk_spkm = db.get(query);
		if(rsPxk_spkm != null)
		{	
			try
			{
				while(rsPxk_spkm.next())
				{
					String[] param = new String[8];
					ISanpham sp = null;
					
					param[0] = "";					
					param[1] = "";		
					param[2] = "";
					param[3] = "";		
					param[4] = "";
					param[5] = "";
					
					//luu ten ctkm
					param[6] = "";
					if(rsPxk_spkm.getString("ctkmTen") != null)
						param[6] = rsPxk_spkm.getString("ctkmTen");
					
					param[7] = "";
					if(rsPxk_spkm.getString("tonggiatri") != null)
						param[7] = rsPxk_spkm.getString("tonggiatri");
					
					sp = new Sanpham(param);
					pxk_tienkmlist.add(sp);
				}
				rsPxk_spkm.close();
			} 
			catch(Exception e) {}
			finally{try {
				if(rsPxk_spkm != null)
					rsPxk_spkm.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}}
		}
		return pxk_tienkmlist;
	}

}
