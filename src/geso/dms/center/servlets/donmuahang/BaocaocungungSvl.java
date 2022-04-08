package geso.dms.center.servlets.donmuahang;
import geso.dms.center.beans.baocao.*;
import geso.dms.center.beans.baocao.imp.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import geso.dms.center.db.sql.*;
import geso.dms.center.util.Utility;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
//import jxl.write.Formula;
import jxl.write.Label;
//import jxl.write.Number;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.sql.ResultSet;

public class BaocaocungungSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public BaocaocungungSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
//   PrintWriter out = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
	    Utility util = new Utility();
	    String querystring = request.getQueryString();
	    	    	    
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
		IMucdocungunghang obj = new Mucdocungunghang();
		obj.setUserId(userId);
		obj.init();
		session.setAttribute("obj", obj);
		
		String nextJSP = "/AHF/pages/Center/Mucdocungung.jsp";
		response.sendRedirect(nextJSP);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IMucdocungunghang obj;
		int p_row = 0;

		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
//	    PrintWriter out = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
	    Utility util = new Utility();
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    obj = new Mucdocungunghang();
	    
	    if (action.equals("excel")){
			OutputStream outstream = null;
			try
			{
				WorkbookSettings wbSettings = new WorkbookSettings();

				wbSettings.setLocale(new Locale("en", "EN"));			
				response.setContentType("application/vnd.ms-excel");
			    response.setHeader("Content-Disposition", "attachment; filename=Mucdocungunghang.xls");
			    
			    outstream = response.getOutputStream();
				WritableWorkbook workbook = Workbook.createWorkbook(outstream, wbSettings);
			    
				workbook.createSheet("MucDoCungUngHang", 0);
				WritableSheet Sheet = workbook.getSheet(0);
				
				workbook.setColourRGB(Colour.RED, 0xff, 0, 0);
//				workbook.addNameArea("validation_range", Sheet, 4, 65, 9, 65);
//				Sheet.getSettings().setPrintArea(4,4,15,35);
				
				this.CreateHeader(Sheet);
				this.CreateContent(Sheet, getSearchQuery(request, obj));
				
				workbook.write();		
				workbook.close();
			}
			catch(jxl.JXLException ex)
			{
				System.out.print("Exception...");
			}
			finally
		    {
		     if (outstream != null)
		    	 outstream.close();
		    }
			
//			this.doGet(request, response);
	    }
	    
	    if (action.equals("pdf")){
		    response.setContentType("application/pdf");
			Document document = new Document();
			ServletOutputStream outstream = response.getOutputStream();
			this.CreateDondathangHPC(document, request, outstream, obj);			
	    }

	    if (action.equals("search")){
	    	
	    	String search = getSearchQuery(request, obj);	    	
	    	
	    	obj.setUserId(userId);
	    	obj.setSearch(search);
	    	obj.init();
				
	    	session.setAttribute("obj", obj);
		    		
	    	String nextJSP = "/AHF/pages/Center/Mucdocungung.jsp";	    	
		    response.sendRedirect(nextJSP);	    	
	    }
	}
	
	private void CreateDondathangHPC(Document document, HttpServletRequest request, ServletOutputStream outstream, 	IMucdocungunghang obj) throws IOException
	{
		try{			

		    PdfWriter.getInstance(document, outstream);
			document.open();
			
			//chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			
			BaseFont bf = BaseFont.createFont("C:\\WINDOWS\\Fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 12, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			Font font3 = new Font(bf, 8, Font.UNDERLINE);
			 
			Paragraph mdcu = new Paragraph("Muc do cung ung hang hoa", font);
			mdcu.setSpacingAfter(15);
			mdcu.setAlignment(Element.ALIGN_CENTER);
			document.add(mdcu);
			
			PdfPTable tableHead = new PdfPTable(2);
			tableHead.setWidthPercentage(50);
			tableHead.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableHead.setSpacingAfter(10);
			float[] with = {15.0f, 20.0f}; //set chieu rong cac columns
			tableHead.setWidths(with);
			
			
			PdfPCell cell1 = new PdfPCell(new Paragraph("Cong ty", font2));
			PdfPCell cell2 = new PdfPCell(new Paragraph("International Consumer Products", font2));
			cell1.setBorder(0);
			cell2.setBorder(0);
			tableHead.addCell(cell1);
			tableHead.addCell(cell2);
			
			PdfPCell cell3 = new PdfPCell(new Paragraph("Ngay bao cao: ", font2));
			PdfPCell cell4 = new PdfPCell(new Paragraph(getDateTime(), font2));
			cell3.setBorder(0);
			cell4.setBorder(0);
			tableHead.addCell(cell3);
			tableHead.addCell(cell4);
							
			document.add(tableHead);
			//Table Content
			PdfPTable table = new PdfPTable(12);
			table.setWidthPercentage(110);
			float[] widths = {10.0f, 15.0f, 25.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f}; //set chieu rong cac columns
	        table.setWidths(widths);
	        
			String[] th = new String[]{"DVKD", "Ma Code", "Dien giai", "Ngay", "Dat(Le) ","Dat(T)", "Giao(T)", "Chenh lech(T)", "Kho", "Vung", "Khu vuc", "Nha phan phoi"};
			PdfPCell[] cell = new PdfPCell[12];
						
			for(int i=0; i <= 11 ; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], font2));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[i].setPadding(2);
				cell[i].setBackgroundColor(BaseColor.WHITE);							
				table.addCell(cell[i]);
				
			}
			dbutils db = new dbutils();
			String query = this.getSearchQuery(request, obj);
			ResultSet rs = db.get(query);
			try{
				while(rs.next()){
			
				Font font4 = new Font(bf, 7);			
				cell[0] = new PdfPCell(new Paragraph(rs.getString("dvkd"), font4));		
				cell[0].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[0].setPadding(2);
				table.addCell(cell[0]);				

				cell[1] = new PdfPCell(new Paragraph(rs.getString("masp"), font4));		
				cell[1].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[1].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[1].setPadding(2);
				table.addCell(cell[1]);				
				
				cell[2] = new PdfPCell(new Paragraph(rs.getString("tensp"), font4));		
				cell[2].setHorizontalAlignment(Element.ALIGN_LEFT);
				cell[2].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[2].setPadding(2);
				table.addCell(cell[2]);				
				
				cell[3] = new PdfPCell(new Paragraph(rs.getString("ngaydat"), font4));		
				cell[3].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[3].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[3].setPadding(2);
				table.addCell(cell[3]);				

				
				String dadat = "" + Float.valueOf(rs.getString("dadat")).floatValue()*Float.valueOf(rs.getString("quycach")).floatValue();
				cell[4] = new PdfPCell(new Paragraph(dadat , font4));		
				cell[4].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[4].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[4].setPadding(2);
				table.addCell(cell[4]);				
				
				dadat = "" + Float.valueOf(rs.getString("dadat")).floatValue();
				cell[5] = new PdfPCell(new Paragraph(dadat , font4));		
				cell[5].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[5].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[5].setPadding(2);
				table.addCell(cell[5]);				

				String dagiao = "" + Float.valueOf(rs.getString("dagiao")).floatValue() /Float.valueOf(rs.getString("quycach")).floatValue(); 
				cell[6] = new PdfPCell(new Paragraph(dagiao, font4));		
				cell[6].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[6].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[6].setPadding(2);
				table.addCell(cell[6]);				
				
				cell[7] = new PdfPCell(new Paragraph("" + (Float.valueOf(dadat).floatValue()- Float.valueOf(dagiao).floatValue()), font4));		
				cell[7].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[7].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[7].setPadding(2);
				table.addCell(cell[7]);				

				cell[8] = new PdfPCell(new Paragraph(rs.getString("kho"), font4));		
				cell[8].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[8].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[8].setPadding(2);
				table.addCell(cell[8]);				

				cell[9] = new PdfPCell(new Paragraph(rs.getString("vung"), font4));		
				cell[9].setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell[9].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[9].setPadding(2);
				table.addCell(cell[9]);				

				cell[10] = new PdfPCell(new Paragraph(rs.getString("khuvuc"), font4));		
				cell[10].setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell[10].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[10].setPadding(2);
				table.addCell(cell[10]);				
				
				cell[11] = new PdfPCell(new Paragraph(rs.getString("nppTen"), font4));		
				cell[11].setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell[11].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[11].setPadding(2);
				table.addCell(cell[11]);					
				}
			}catch(java.sql.SQLException e){}
					
			document.add(table);
			
			document.close(); 
		}
		catch(DocumentException e)
		{
			e.printStackTrace();
		}

	}

	private String getSearchQuery(HttpServletRequest request, 	IMucdocungunghang obj){
		Utility util = new Utility();
		
		String masp = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("masp")));
    	if (masp == null)
    		masp = "";
    	obj.setMasp(masp);
    	
		String tensp = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tensp")));
    	if (tensp == null)
    		tensp = "";
    	obj.setTensp(tensp);
    	
    	String tungay = util.antiSQLInspection(request.getParameter(("tungay")));
    	if (tungay == null)
    		tungay = "";    	
    	obj.setTuNgay(tungay);

    	String denngay = util.antiSQLInspection(request.getParameter(("denngay")));
    	if (denngay == null)
    		denngay = "";    	
    	obj.setDenNgay(denngay);
    	   	
    	String khoId = util.antiSQLInspection(request.getParameter(("khoId")));   	
    	if (khoId == null)
    		khoId = "";    	
	
    	obj.setKhoId(khoId);
    	
    	String vungId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")));   	
    	if (vungId == null)
    		vungId = "0";    	
	
    	obj.setVungId(vungId) ;
    	
    	String kvId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kvId")));   	
    	if (kvId == null)
    		kvId = "0";    	
	
    	obj.setKvId(kvId);
    	
    	String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));   	
    	if (nppId == null)
    		nppId = "0";    	
	
    	obj.setNppId(nppId);
    	
		String 	query = "select distinct " + 
						"ddh.pk_seq as ddhId, " + 
						"dvkd.donvikinhdoanh as dvkd, " +
						"sp.ma as masp, sp.ten as tensp, ddh.ngaydat, ddh_sp.soluong as dagiao, " + 
						"(ddh_sp.soluong + th.soluong) as dadat, qc.soluong1 as quycach, npp.khosap as kho, " +
						"v.ten as vung, kv.ten as khuvuc, npp.ten as nppTen " +
						"from thieuhang th " +
						"inner join dondathang ddh on th.dondathang_fk=ddh.pk_seq " + 
						"inner join dondathang_sp ddh_sp on ddh.pk_seq = ddh_sp.dondathang_fk " +  
						"inner join sanpham sp on sp.pk_seq = th.sanpham_fk and th.sanpham_fk=ddh_sp.sanpham_fk " +
						"inner join donvikinhdoanh dvkd on dvkd.pk_seq = sp.dvkd_fk " +
						"inner join quycach qc on sp.dvdl_fk = qc.dvdl1_fk and qc.dvdl2_fk='100018' and qc.sanpham_fk=sp.pk_seq " +
						"inner join nhaphanphoi npp on npp.pk_seq = ddh.npp_fk " +
						"inner join khuvuc kv on npp.khuvuc_fk = kv.pk_seq " +
						"inner join vung v on v.pk_seq = kv.vung_fk where th.loai='1'" ;
					//	"order by dvkd.donvikinhdoanh ";
    	

		if (masp.length() > 0){
			query = query + " and upper(dbo.ftBoDau(sp.ma))  like '%" + util.replaceAEIOU(masp) + "%'";
		}
		
		if (tensp.length() > 0){
			query = query + " and sp.ten like '%" + tensp + "%'";
		}
		
		if (tungay.length() > 0){
			query = query + " and ddh.ngaydat >= '" + tungay + "'";
		}
		
		if (denngay.length() > 0){
			query = query + " and ddh.ngaydat <= '" + denngay + "'";
		}

		if (khoId.length() > 0){
			query = query + " and npp.khosap = '" + khoId + "'";
		}
		
		if (!vungId.equals("0")){
			query = query + " and v.pk_seq = '" + vungId + "'";
		}
		
		if (!kvId.equals("0")){
			query = query + " and kv.pk_seq = '" + kvId + "'";
		}

		if (!nppId.equals("0")){
			query = query + " and npp.pk_seq = '" + nppId + "'";
		}

		query = query + "  order by dvkd.donvikinhdoanh, ddh.ngaydat, sp.ma, npp.khosap, v.ten, kv.ten, npp.ten";
		return query;
	}			

	private void CreateHeader(WritableSheet ws) throws WriteException
	{
		//Co dinh 8 row dau tien
		ws.getSettings().setVerticalFreeze(5);
	    ws.getSettings().setDefaultRowHeight(17*20);
	    
	    ws.setColumnView(0, 20);
	    ws.setColumnView(1, 30);
	    ws.setColumnView(2, 13);
	    ws.setColumnView(3, 13);
	    ws.setColumnView(4, 13);
	    ws.setColumnView(5, 15);
	    ws.setColumnView(7, 18);
	    ws.setColumnView(8, 18);
	    ws.setColumnView(9, 18);
	    ws.setColumnView(10, 18);
	    ws.setColumnView(11, 18);
	    
	    WritableFont wf = new WritableFont(WritableFont.TIMES, 15 , WritableFont.BOLD);
	    WritableCellFormat wcf = new WritableCellFormat(wf);
	    wcf.setAlignment(Alignment.CENTRE);
	    wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
	    Label title = new Label(1, 1, "Bao cao cung ung", wcf);
	    ws.addCell(title);
	    ws.mergeCells(1, 1, 5, 1); //tron 4 cell lien tiep
	    //ws.setRowView(1, 24*20);
	    
	    WritableFont font = new WritableFont(WritableFont.TIMES, WritableFont.DEFAULT_POINT_SIZE, WritableFont.NO_BOLD, false,UnderlineStyle.SINGLE);
	    wcf = new WritableCellFormat(font);
	    wcf.setWrap(true);
	    
	    WritableFont wf2 = new WritableFont(WritableFont.TIMES, WritableFont.DEFAULT_POINT_SIZE , WritableFont.BOLD);
	    WritableCellFormat wcf2 = new WritableCellFormat(wf2);
	    	    
	    Label company = new Label(0, 2, "Cong ty: ", wcf);
	    ws.addCell(company);	
	    
	    Label icp = new Label(1, 2, "International Consumer Products", wcf2);
	    ws.addCell(icp);	
	    
	    Label ngay = new Label(0, 3, "Ngay bao cao: ", wcf);
	    ws.addCell(ngay);	
	    
	    Label date = new Label(1, 3, getDateTime(), wcf2);
	    ws.addCell(date);	
	    	    		    
	    font = new WritableFont(WritableFont.TIMES, WritableFont.DEFAULT_POINT_SIZE, WritableFont.BOLD, false);
	    wcf = new WritableCellFormat(font);
	    wcf.setWrap(true);
	    wcf.setAlignment(Alignment.CENTRE);
	    wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
	    wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
	    	    
	    Label dvkd = new Label(0, 4, "DVKD", wcf);	    
	    ws.addCell(dvkd);	

	    Label h_macode = new Label(1, 4, "Ma Code", wcf);	    
	    ws.addCell(h_macode);	
	    
	    Label h_diengiai = new Label(2, 4, "Dien Giai", wcf);	   
	    ws.addCell(h_diengiai);	 
	    
	    Label h_ngay = new Label(3, 4, "Ngay", wcf);    
	    ws.addCell(h_ngay);	 
	    
	    Label h_datle = new Label(4, 4, "So luong dat (Le)", wcf);	   
	    ws.addCell(h_datle);

	    Label h_dat = new Label(5, 4, "So luong dat (T)", wcf);	   
	    ws.addCell(h_dat);
	    
	    Label h_giao = new Label(6, 4, "So luong giao (T)", wcf);
	    ws.addCell(h_giao);
	    
	    Label h_chenhlech = new Label(7, 4, "Chenh lech (T)", wcf);
	    ws.addCell(h_chenhlech);	
	    
	    Label h_kho = new Label(8, 4, "Kho", wcf);
	    ws.addCell(h_kho);
//	    ws.mergeCells(5, 6, 7, 6); //tron 3 cot lien nhau
	    
	    Label h_vung = new Label(9, 4, "Vung", wcf);
	    ws.addCell(h_vung);	    
	    
	    Label h_khuvuc = new Label(10, 4, "Khu vuc", wcf);
	    ws.addCell(h_khuvuc);
	    
	    Label h_npp = new Label(11, 4, "Nha  Phan Phoi", wcf);
	    ws.addCell(h_npp);
	}
	
	private void CreateContent(WritableSheet ws, String query) throws WriteException
	{
		WritableFont wf = new WritableFont(WritableFont.TIMES,WritableFont.DEFAULT_POINT_SIZE, WritableFont.NO_BOLD);
	    WritableCellFormat wcf_left = new WritableCellFormat(wf);
	    wcf_left.setAlignment(Alignment.LEFT);
	    wcf_left.setBorder(Border.ALL, BorderLineStyle.THIN);
	    
	    WritableCellFormat wcf_center = new WritableCellFormat(wf);
	    wcf_center.setAlignment(Alignment.CENTRE);
	    wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN);
	    
	    WritableCellFormat wcf_right = new WritableCellFormat(wf);
	    wcf_right.setAlignment(Alignment.RIGHT);
	    wcf_right.setBorder(Border.ALL, BorderLineStyle.THIN);
	    
	    WritableCellFormat wcf_num = new WritableCellFormat(NumberFormats.THOUSANDS_INTEGER);
	    wcf_num.setAlignment(Alignment.RIGHT);
	    wcf_num.setBorder(Border.ALL, BorderLineStyle.THIN);
	    
	    WritableCellFormat cfi2 = new WritableCellFormat(NumberFormats.THOUSANDS_INTEGER);
	    cfi2.setAlignment(Alignment.CENTRE);
	    cfi2.setBorder(Border.ALL, BorderLineStyle.THIN);
	    
		dbutils db = new dbutils();
		ResultSet rs = db.get(query);
		int p_row = 0;

		try{	
			int i = 5;
			
			while(rs.next())
			{
				Label dvkd = new Label(0, i, rs.getString("dvkd"), wcf_left);
				ws.addCell(dvkd);

				Label macode = new Label(1, i, rs.getString("masp"), wcf_left);
				ws.addCell(macode);
			
				Label diengiai = new Label(2, i, rs.getString("tensp"), wcf_left);
				ws.addCell(diengiai);
		    
				Label ngaydat = new Label(3, i, rs.getString("ngaydat"), wcf_center);
				ws.addCell(ngaydat);
				
				float dat = Float.valueOf(rs.getString("dadat")).floatValue()*Float.valueOf(rs.getString("quycach")).floatValue();
				Label dadatle = new Label(4, i, "" + dat, wcf_center);
				ws.addCell(dadatle);

				dat = Float.valueOf(rs.getString("dadat")).floatValue();
				Label dadat = new Label(5, i, "" + dat, wcf_center);
				ws.addCell(dadat);
		    
				float dagiao = Float.valueOf(rs.getString("dagiao")).floatValue()/Long.valueOf(rs.getString("quycach")).floatValue();
		    
				Label giao = new Label(6, i,"" + dagiao , wcf_center);
				ws.addCell(giao);
		    
				Label chenhlech = new Label(7, i, "" + (dat - dagiao) , wcf_center);
				ws.addCell(chenhlech);
		    
				Label kho = new Label(8, i, rs.getString("kho"), wcf_center);
				ws.addCell(kho);
		    
				Label vung = new Label(9, i, rs.getString("vung"), wcf_center);
				ws.addCell(vung);
		    
				Label khuvuc = new Label(10, i, rs.getString("khuvuc"), wcf_center);
				ws.addCell(khuvuc);
		    
				Label nppTen = new Label(11, i, rs.getString("nppTen"), wcf_center);
				ws.addCell(nppTen);
				
				i++;
			}
			p_row = i-1;
		}catch(java.sql.SQLException e){}

	}
	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
