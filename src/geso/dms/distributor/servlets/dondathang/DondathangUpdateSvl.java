package geso.dms.distributor.servlets.dondathang;

import geso.dms.distributor.beans.dondathang.IDondathang;
import geso.dms.distributor.beans.dondathang.IDondathangList;
import geso.dms.distributor.beans.dondathang.imp.Dondathang;
import geso.dms.distributor.beans.dondathang.imp.DondathangList;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
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

 public class DondathangUpdateSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
	public DondathangUpdateSvl() {
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
		
		
		session.setMaxInactiveInterval(30000);
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
	   
	    IDondathang ddhBean = (IDondathang) new Dondathang();
	    ddhBean.setUserId(userId);
	    
	    if(action.equals("dathang"))
	    {
	    	ddhBean.setDndhId(id);
	    	ddhBean.init2();
	    	nextJSP = "/AHF/pages/Distributor/DonDatHangNewfromPR.jsp";
	    }
	    else	    
	    {
	    	ddhBean.setId(id);
	    	ddhBean.init1();
	    	nextJSP = "/AHF/pages/Distributor/DonDatHangUpdate.jsp";
	    }
	    
		session.setAttribute("ddhBean", ddhBean);			
	
		response.sendRedirect(nextJSP);
		}

	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		
		session.setMaxInactiveInterval(1200);
//		PrintWriter out = response.getWriter();
		IDondathang ddhBean = (IDondathang) new Dondathang();
	    Utility util = new Utility();
	    
		String id =  util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
	    
	    if(id == null){  	
	    	id = "";
	    }
	    ddhBean.setId(id);	        
	    
		userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		ddhBean.setUserId(userId);
	    
		ddhBean.setNguoitao(userId);
		ddhBean.setNguoisua(userId);
		
		String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
		if (nppId == null)
			nppId = "0";
		ddhBean.setNppId(nppId);
		
		String dndhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dndhId")));
		if (dndhId == null)
			dndhId = "0";
		ddhBean.setDndhId(dndhId);
		
    	String ngaydh = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaydh")));
		if (ngaydh == null)
			ngaydh = getDateTime();
		
    	ddhBean.setNgaydh(ngaydh);

		String nccId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nccId")));
		ddhBean.setNccId(nccId);
	    
		String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
		ddhBean.setDvkdId(dvkdId);
		
		String kbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhId")));
		ddhBean.setKbhId(kbhId);
		
		String doihang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("doihang")));
		if(doihang==null){
			doihang="0";
		}
		ddhBean.setDoiHang(doihang);
		
		String loaidonhang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaidonhang")));
		if(loaidonhang==null){
			loaidonhang="0";
		}
		ddhBean.setLoaiDonHang(loaidonhang);
		
		String congtyvanchuyen=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("congtyvanchuyen")));
		
		if(congtyvanchuyen==null){
			congtyvanchuyen="0";
		}
		ddhBean.setcongTyGiaohang(congtyvanchuyen);
		
		
		String ghichu=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ghichu")));
		
		if(ghichu==null){
			ghichu="";
		}
		ddhBean.setGhiChu(ghichu);
		
		System.out.println("congtyvanchuyen ; " + congtyvanchuyen);
		
		String ngaydenghigiaohang = ( util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaydenghigiaohang"))==null?"":util.antiSQLInspection(request.getParameter("ngaydenghigiaohang")))  );
		if(ngaydenghigiaohang==null){
			ngaydenghigiaohang="";
		}
		
		ddhBean.setNgaydenghigh(ngaydenghigiaohang);
		
//		out.print(" dvkdId =" + dvkdId);
					
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		System.out.println("Action la: " + action);
		
		if(action.equals("save")){
			if (id.length()==0){			
				if (!(ddhBean.CreateDdhnpp(request))){				
					ddhBean.initSelectboxData();
					ddhBean.setcongTyGiaohang("1");
					session.setAttribute("ddhBean", ddhBean);
					
					String nextJSP = "/AHF/pages/Distributor/DonDatHangNewfromPR.jsp";
					response.sendRedirect(nextJSP);
				}else{
					IDondathangList obj = new DondathangList();
					obj.setUserId(userId);
					obj.createDdhlist("");
					session.setAttribute("obj", obj);
								
					String nextJSP = "/AHF/pages/Distributor/DonDatHang.jsp";
					response.sendRedirect(nextJSP);			    			    									
					
				}
			}else{
				if (!(ddhBean.UpdateDdhnpp(request))){	
					ddhBean.initSelectboxData();
					session.setAttribute("ddhBean", ddhBean);					
					String nextJSP = "/AHF/pages/Distributor/DonDatHangUpdate.jsp";
					response.sendRedirect(nextJSP);
				}else{
					IDondathangList obj = new DondathangList();
					obj.setUserId(userId);
					obj.createDdhlist("");
					session.setAttribute("obj", obj);
								
					String nextJSP = "/AHF/pages/Distributor/DonDatHang.jsp";
					response.sendRedirect(nextJSP);			    			    									
					
				}
			}
			
		
		}else{
			if(action.equals("print")){
			    request.setCharacterEncoding("UTF-8");
			    response.setCharacterEncoding("UTF-8");		
				response.setContentType("application/pdf");
				
			    ddhBean.init1();
			    
				Document document = new Document();
				ServletOutputStream outstream = response.getOutputStream();
				this.CreateDondathangHPC(document, outstream, ddhBean);
				
				
			}else{
				if(action.equals("chot")){
					if (!(ddhBean.ChotDdhnpp(request))){
						ddhBean.initSelectboxData();
						session.setAttribute("ddhBean", ddhBean);					
						String nextJSP = "/AHF/pages/Distributor/DonDatHangUpdate.jsp";
						response.sendRedirect(nextJSP);
					}else{
						IDondathangList obj = new DondathangList();
						obj.setUserId(userId);
						obj.createDdhlist("");
						session.setAttribute("obj", obj);
									
						String nextJSP = "/AHF/pages/Distributor/DonDatHang.jsp";
						response.sendRedirect(nextJSP);			    			    									
						
					}
				}
				else if(action.equals("freshorder")){
					String nextJSP;
					
			    	if(ngaydh.length() > 0 & nccId.length() > 0 & dvkdId.length() > 0 & kbhId.length() > 0){
				    	ddhBean.init3();
				    	ddhBean.setcongTyGiaohang("0");
				    	
				    	nextJSP = "/AHF/pages/Distributor/DonDatHangNew.jsp";	
			    	}else{
			    		ddhBean.init0();
			    		nextJSP = "/AHF/pages/Distributor/DonDatHangParam.jsp";
			    	}
					session.setAttribute("ddhBean", ddhBean);			
					
					response.sendRedirect(nextJSP);			    	
			    	
			    }
			else
			{	
				System.out.println("Vào chỗ này");
				String nextJSP;
				if (id.length()==0){						
					nextJSP = "/AHF/pages/Distributor/DonDatHangNew.jsp";
				}else{				
					ddhBean.init1();
					nextJSP = "/AHF/pages/Distributor/DonDatHangUpdate.jsp";   						
				}
				session.setAttribute("ddhBean", ddhBean);			
			
				response.sendRedirect(nextJSP);
			
			}
			}
		}
		}

	}

	private void CreateDondathangHPC(Document document, ServletOutputStream outstream, IDondathang ddhBean) throws IOException
	{
		try{			

		    PdfWriter.getInstance(document, outstream);
			document.open();
			//chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("C:\\WINDOWS\\Fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 12, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			Font font3 = new Font(bf, 8, Font.UNDERLINE);
			 
			Paragraph ddh = new Paragraph("ĐƠN ĐẶT HÀNG", font);
			ddh.setSpacingAfter(15);
			ddh.setAlignment(Element.ALIGN_CENTER);
			document.add(ddh);
			
			PdfPTable tableHead = new PdfPTable(4);
			tableHead.setWidthPercentage(90);
			tableHead.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableHead.setSpacingAfter(10);
			float[] with = {15.0f, 40.0f, 30f, 18.0f}; //set chieu rong cac columns
			tableHead.setWidths(with);
			
			PdfPCell cell1 = new PdfPCell(new Paragraph("Tên NPP: ", font3));
			PdfPCell cell2 = new PdfPCell(new Paragraph(ddhBean.getNppTen(), font2));
			PdfPCell cell3 = new PdfPCell(new Paragraph("Fax BHS: ", font3));
			PdfPCell cell4 = new PdfPCell(new Paragraph("", font2));
			cell1.setBorder(0);
			cell2.setBorder(0);
			cell3.setBorder(0);
			cell4.setBorder(0);
			tableHead.addCell(cell1);
			tableHead.addCell(cell2);
			tableHead.addCell(cell3);
			tableHead.addCell(cell4);
			
			PdfPCell cell5 = new PdfPCell(new Paragraph("Tỉnh/ TP: ", font3));
			PdfPCell cell6 = new PdfPCell(new Paragraph("", font2));
			PdfPCell cell7 = new PdfPCell(new Paragraph("Tel/ Fax NPP: ", font3));
			PdfPCell cell8 = new PdfPCell(new Paragraph("", font2));
			cell5.setBorder(0);
			cell6.setBorder(0);
			cell7.setBorder(0);
			cell8.setBorder(0);
			tableHead.addCell(cell5);
			tableHead.addCell(cell6);
			tableHead.addCell(cell7);
			tableHead.addCell(cell8);
			
			PdfPCell cell9 = new PdfPCell(new Paragraph("Ngày Đặt Hàng: ", font3));
			PdfPCell cell10 = new PdfPCell(new Paragraph(ddhBean.getNgaydh(), font2));
			cell9.setBorder(0);
			cell10.setBorder(0);
			tableHead.addCell(cell9);
			tableHead.addCell(cell10);
			PdfPCell cell11a = new PdfPCell(new Paragraph("Ngày đề nghị giao hàng :", font3));
			PdfPCell cell12a = new PdfPCell(new Paragraph(ddhBean.getNgaydenghigh(), font2));
			
			
			cell11a.setBorder(0);
			cell12a.setBorder(0);
			tableHead.addCell(cell11a);
			tableHead.addCell(cell12a);
			document.add(tableHead);
			
			//Table Content
			PdfPTable table = new PdfPTable(10);
			table.setWidthPercentage(100);
			float[] withs = {16.0f, 42.0f, 11.0f, 12.0f, 22.0f, 14.0f, 12.0f, 13.0f, 12.0f, 22.0f};
	        table.setWidths(withs);
	        
			String[] th = new String[]{"Mã Code", "Diễn Giải", "Quy cách", "ĐV", "Đơn Giá NPP", "Đặt hàng"};
			String[] th2 = new String[]{"(Trước VAT)", "Số Lượng","KL(kg)","TT(m3)", "Thùng", "TT(Trước VAT)"};
			PdfPCell[] cell = new PdfPCell[10];
			for(int i=0; i <= 5 ; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], font2));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[i].setPadding(5);
				if(i <= 3 )
					cell[i].setRowspan(2);		
				if( i == 5 )
				{
					cell[i].setColspan(5);
				}
				cell[i].setBackgroundColor(BaseColor.WHITE);
							
				table.addCell(cell[i]);
				
			}
			for(int i=0; i < 6; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th2[i], font2));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[i].setPadding(5);
				cell[i].setBackgroundColor(BaseColor.WHITE);
				table.addCell(cell[i]);				
			}
			
		    String[] masp = ddhBean.getMasp() ; 
		    String[] tensp = ddhBean.getTensp(); 		    
		    String[] qc  = ddhBean.getQuycach();
		    String[] dv1  = ddhBean.getDv1();
		    String[] sl = ddhBean.getSl(); 
		    String[] tienbVAT = ddhBean.getTienbVAT();
		    String[] dg = ddhBean.getDongia() ;
		    String[] kl = ddhBean.getKhoiluong();
		    String[] tt = ddhBean.getThetich();
		    //String[] dv = ddhBean.getDonvi() ; 
		    NumberFormat formatter = new DecimalFormat("#,###,###");
		    
			for(int j=0; j < Integer.valueOf(ddhBean.getSize()).intValue(); j++)
			{
			if(Integer.valueOf(sl[j]).intValue() >0){
				Font font4 = new Font(bf, 8);
//				if(j % 2 != 0) 	font4.setColor(BaseColor.GREEN);
					
				cell[0] = new PdfPCell(new Paragraph(masp[j], font4));		
				cell[0].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[0].setPadding(4);
				table.addCell(cell[0]);				
				
				cell[1] = new PdfPCell(new Paragraph(tensp[j], font4));		
				cell[1].setHorizontalAlignment(Element.ALIGN_LEFT);
				cell[1].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[1].setMinimumHeight(25);
				cell[1].setPadding(4);
				table.addCell(cell[1]);				
				
				cell[2] = new PdfPCell(new Paragraph(qc[j], font4));		
				cell[2].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[2].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[2].setPadding(4);
				table.addCell(cell[2]);				
				
				cell[3] = new PdfPCell(new Paragraph(dv1[j], font4));		
				cell[3].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[3].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[3].setPadding(4);
				table.addCell(cell[3]);				

				cell[4] = new PdfPCell(new Paragraph(formatter.format(Double.valueOf(dg[j])), font4));		
				cell[4].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[4].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[4].setPadding(4);
				table.addCell(cell[4]);				

				cell[5] = new PdfPCell(new Paragraph("" + (Double.valueOf(sl[j]).doubleValue()*Double.valueOf(qc[j]).doubleValue()), font4));		
				cell[5].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[5].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[5].setPadding(4);
				table.addCell(cell[5]);				
				String khoiluongtmp="0";
				try{
					khoiluongtmp=	formatter.format(Double.parseDouble(kl[j])* Double.parseDouble(qc[j]));
				}catch(Exception er){
					
				}
				cell[6] = new PdfPCell(new Paragraph(khoiluongtmp , font4));		
				cell[6].setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell[6].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[6].setPadding(4);
				table.addCell(cell[6]);				
				
				String thetichtmp="0";
				try{
					thetichtmp=	formatter.format(Double.parseDouble(tt[j])* Double.parseDouble(qc[j]));
				}catch(Exception er){
					
				}
				
				cell[7] = new PdfPCell(new Paragraph(thetichtmp, font4));		
				cell[7].setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell[7].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[7].setPadding(4);
				table.addCell(cell[6]);
				
				cell[8] = new PdfPCell(new Paragraph(sl[j], font4));		
				cell[8].setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell[8].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[8].setPadding(4);
				table.addCell(cell[8]);
				
				cell[9] = new PdfPCell(new Paragraph(formatter.format(Double.parseDouble(tienbVAT[j])), font4));		
				cell[9].setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell[9].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[9].setPadding(4);
				table.addCell(cell[9]);
			}
				
			}		
			document.add(table);
			
			//Table Footer
			PdfPTable tableFooter = new PdfPTable(2);
			tableFooter.setWidthPercentage(40);
			tableFooter.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableFooter.setSpacingBefore(10);
			float[] with3 = {25.0f, 25.0f}; 
			tableFooter.setWidths(with3);
			
			PdfPCell cell11 = new PdfPCell(new Paragraph("Tổng Tiền: ", new Font(bf, 9, Font.BOLD)));
			cell11.setHorizontalAlignment(Element.ALIGN_LEFT);
			PdfPCell cell12 = new PdfPCell(new Paragraph(formatter.format(Double.parseDouble(ddhBean.getTongtienbVAT())), new Font(bf, 9, Font.ITALIC)));
			cell11.setBorder(0);
			cell12.setBorder(0);
			cell12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			
			cell11 = new PdfPCell(new Paragraph("Thuế VAT (5%): ", new Font(bf, 9, Font.BOLDITALIC)));
			cell11.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell12 = new PdfPCell(new Paragraph(formatter.format(Double.parseDouble(ddhBean.getVat())), new Font(bf, 9, Font.ITALIC)));
			cell11.setBorder(0);
			cell12.setBorder(0);
			cell12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			
			cell11 = new PdfPCell(new Paragraph("Tổng Giá Trị: ", new Font(bf, 9, Font.BOLD)));
			cell11.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell12 = new PdfPCell(new Paragraph(formatter.format(Double.parseDouble(ddhBean.getTongtienaVAT())), new Font(bf, 9, Font.ITALIC)));
			cell11.setBorder(0);
			cell12.setBorder(0);
			cell12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			
			document.add(tableFooter);
			
			tableFooter = new PdfPTable(2);
			tableFooter.setWidthPercentage(80);
			tableFooter.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableFooter.setSpacingBefore(15);
			tableFooter.setWidths(new float[]{50.0f, 50.0f});
			
			cell11 = new PdfPCell(new Paragraph("Xác Nhận NPP", new Font(bf, 11, Font.BOLD)));
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell12 = new PdfPCell(new Paragraph("Xác Nhận Giám Sát BHS", new Font(bf, 11, Font.BOLD)));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell11.setBorder(0);
			cell12.setBorder(0);
			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			document.add(tableFooter);
			document.close(); 
		}
		catch(DocumentException e)
		{
			e.printStackTrace();
		}
		ddhBean.DBclose();
	}

	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}