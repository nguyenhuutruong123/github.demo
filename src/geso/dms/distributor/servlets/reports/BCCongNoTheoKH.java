package geso.dms.distributor.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.report.Ireport;
import geso.dms.distributor.beans.report.imp.Report;


import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Font;
import com.aspose.cells.HorizontalAlignmentType;
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTables;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

/**
 * Servlet implementation class BCCongNoTheoKH
 */
public class BCCongNoTheoKH extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String setQuery(HttpServletRequest request,String tungay,String denngay,String st) {
			String query = "SELECT	NPP.PK_SEQ AS NPPID, NPP.TEN AS NPP,KH.PK_SEQ AS KHID, KH.TEN AS KHACHHANG," + 									
						" KH.DIACHI AS DIACHI,ISNULL(DAUKY.NO,0) AS NODAUKY,ISNULL(DAUKY.CO,0) AS CODAUKY," +
						" ISNULL(TRONGKY.NO,0) AS NOTRONGKY,ISNULL(TRONGKY.CO,0) AS COTRONGKY," +
						" CASE WHEN (ISNULL(DAUKY.NO,0) + ISNULL(TRONGKY.NO,0) - (ISNULL(DAUKY.CO,0) + ISNULL(TRONGKY.CO,0))>0) " +									
						" THEN (ISNULL(DAUKY.NO,0) + ISNULL(TRONGKY.NO,0) - (ISNULL(DAUKY.CO,0) + ISNULL(TRONGKY.CO,0))) " + 									
						" ELSE '0' END AS NOCUOIKY," +
						" CASE WHEN (ISNULL(DAUKY.NO,0) + ISNULL(TRONGKY.NO,0) - (ISNULL(DAUKY.CO,0) + ISNULL(TRONGKY.CO,0))<0) " +									
						" THEN (ISNULL(DAUKY.CO,0) + ISNULL(TRONGKY.CO,0) - (ISNULL(DAUKY.NO,0) + ISNULL(TRONGKY.NO,0))) " +									
						" ELSE '0'	END AS COCUOIKY " +	
										
				" FROM KHACHHANG KH	" + 									
						" INNER JOIN NHAPHANPHOI NPP ON KH.NPP_FK = NPP.PK_SEQ " + 										
						" LEFT JOIN 	" + 									
						"( " + 
						"	SELECT 		" + 							
						"		NPP.PK_SEQ AS NPPID, NPP.TEN AS NPP," + 								
						"		KH.PK_SEQ AS KHID, KH.TEN AS KHACHHANG,		" + 						
						"		KH.DIACHI AS DIACHI,	" + 							
						"		CASE WHEN(SUM(DH_SP.GIAMUA*DH_SP.SOLUONG - DH_SP.CHIETKHAU)*1.1 - SUM(DH.DATHANHTOAN) > 0) THEN (SUM(DH_SP.GIAMUA*DH_SP.SOLUONG - DH_SP.CHIETKHAU)*1.1 - SUM(DH.DATHANHTOAN)) " + 								
						"		ELSE '0'	" + 							
						"		END AS NO,	" + 									
						"		CASE WHEN(SUM(DH_SP.GIAMUA*DH_SP.SOLUONG - DH_SP.CHIETKHAU)*1.1 - SUM(DH.DATHANHTOAN) < 0) THEN (SUM(DH.DATHANHTOAN) - SUM(DH_SP.GIAMUA*DH_SP.SOLUONG - DH_SP.CHIETKHAU)*1.1) " + 								
						"		ELSE '0' " + 								
						"		END AS CO	" + 							
										
						"	FROM (select * from donhang where trangthai ='1' "+ st +") DH 	" + 								
						"		INNER JOIN DONHANG_SANPHAM DH_SP ON DH_SP.DONHANG_FK = DH.PK_SEQ	" + 								
						"		INNER JOIN KHACHHANG KH ON KH.PK_SEQ = DH.KHACHHANG_FK	" + 								
						"		INNER JOIN NHAPHANPHOI NPP ON KH.NPP_FK = NPP.PK_SEQ		" + 							
						//"		WHERE NPP.PK_SEQ = '102433' AND DH.NGAYNHAP < '2011-12-01'	" + 	
						//		" WHERE DH.TRANGTHAI='1' " + st +
						"		GROUP BY NPP.PK_SEQ, NPP.TEN, KH.PK_SEQ, KH.TEN, KH.DIACHI		" + 							
						"	)DAUKY ON DAUKY.NPPID = NPP.PK_SEQ AND DAUKY.KHID = KH.PK_SEQ		" + 								
										
						"	LEFT JOIN " + 										
						"	(				" + 						
						"		SELECT 			" + 						
						"			NPP.PK_SEQ AS NPPID, NPP.TEN AS NPP,	" + 							
						"			KH.PK_SEQ AS KHID, KH.TEN AS KHACHHANG,	" + 							
						"			KH.DIACHI AS DIACHI,	" + 							
						"			CASE WHEN(SUM(DH_SP.GIAMUA*DH_SP.SOLUONG - DH_SP.CHIETKHAU)*1.1 - SUM(DH.DATHANHTOAN) > 0) THEN (SUM(DH_SP.GIAMUA*DH_SP.SOLUONG - DH_SP.CHIETKHAU)*1.1 - SUM(DH.DATHANHTOAN)) " + 								
						"			ELSE '0'	" + 							
						"			END AS NO,		" + 																
						"			CASE WHEN(SUM(DH_SP.GIAMUA*DH_SP.SOLUONG - DH_SP.CHIETKHAU)*1.1 - SUM(DH.DATHANHTOAN) < 0) THEN (SUM(DH.DATHANHTOAN) - SUM(DH_SP.GIAMUA*DH_SP.SOLUONG - DH_SP.CHIETKHAU)*1.1) " + 								
						"			ELSE '0' " + 								
						"			END AS CO	" + 							
										
						"	FROM (select * from donhang where trangthai ='1' "+ st +") DH " + 									
						"	INNER JOIN DONHANG_SANPHAM DH_SP ON DH_SP.DONHANG_FK = DH.PK_SEQ	" + 								
						"	INNER JOIN KHACHHANG KH ON KH.PK_SEQ = DH.KHACHHANG_FK	" + 								
						"	INNER JOIN NHAPHANPHOI NPP ON KH.NPP_FK = NPP.PK_SEQ	" +
					//	"	WHERE DH.TRANGTHAI='1' AND DH.NGAYNHAP > '" + tungay +"' AND DH.NGAYNHAP <='"+ denngay + "' " + st +
					//	"	WHERE NPP.PK_SEQ = '102433' AND DH.NGAYNHAP > '" + tungay +"' AND DH.NGAYNHAP <='"+ denngay + "' " + st +									
						"	GROUP BY NPP.PK_SEQ, NPP.TEN, KH.PK_SEQ, KH.TEN, KH.DIACHI " + 					
						" )TRONGKY ON TRONGKY.NPPID = NPP.PK_SEQ AND TRONGKY.KHID = KH.PK_SEQ ";							
						//" WHERE NPP.PK_SEQ = '102433' ";
			return query;
		
	}

  
    public BCCongNoTheoKH() {
        super();
       
    }

	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		Ireport obj = new Report();
	    Utility util = new Utility();
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
//	    System.out.println(userId);
	    obj.setuserId(userId);
	    obj.init();	    
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Distributor/BCCongNoTheoKH.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		Ireport obj = new Report();
		Utility util = new Utility();
		String userId = (String) session.getAttribute("userId");  
	    String userTen = (String) session.getAttribute("userTen"); 

	    if(userId ==null)
	    	userId ="";
	    obj.setuserId(userId);
	    
   	 	String nppId=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
   	 	if(nppId ==null) 
   	 		nppId ="";
   	 	obj.setnppId(nppId);
   	 
	   	 
	   	 String ddkdId=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdId")));
	   	 if(ddkdId == null)
	   		ddkdId ="";
	   obj.setddkdId(ddkdId);
	   	 
	   	 String khachhangId=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khachhangId")));
	   	 if(khachhangId == null)
	   		khachhangId ="";
	   	 obj.setkhachhangId(khachhangId);
	   	 
	   	String tungay=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays")));
	   	 if(tungay ==null)
	   		 tungay ="";
	   	 obj.settungay(tungay);
	   	 
	   	 String denngay=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays")));
	   	 if(denngay == null)
	   		 denngay ="";
	   	 obj.setdenngay(denngay);
	   	 
	   	 
	   	 geso.dms.distributor.util.Utility Ult = new  geso.dms.distributor.util.Utility();
	   	  nppId = Ult.getIdNhapp(userId);
	   	 String st=" and npp_fk ='"+ nppId +"'";
   		 if(ddkdId.length()>0)
   			 st =" and ddkd_fk ='"+ ddkdId +"'";
   		 if(khachhangId.length()>0)
   			 st = st +" and khachhang_fk ='"+ khachhangId +"'";
	     if(tungay.length()>0)
	    	 st = st + " and ngaynhap >='"+ tungay +"'";
	     if(denngay.length()>0)
	    	 st = st + " and ngaynhap <='"+ denngay +"'";
		 String []fieldsHien = request.getParameterValues("fieldsHien");
		 obj.setFieldShow(fieldsHien);
		 
		 String [] fieldsAn =request.getParameterValues("fieldsAn");
			obj.setFieldHidden(fieldsAn)  ;
		 
			String action=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			 if(action.equals("tao"))
			 { 
			try {
					request.setCharacterEncoding("utf-8");
					response.setCharacterEncoding("utf-8");
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition","attachment; filename=BCDonHangBanTrongKyNPP.xls");
					OutputStream out = response.getOutputStream();
					
					String query = setQuery(request,tungay,denngay,st);								
					CreatePivotTable(out,fieldsHien,obj,query); 
		
				} catch (Exception ex) {
					request.getSession().setAttribute("errors", ex.getMessage());
				}
			 }
			 	obj.init();	    
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
				String nextJSP = "/AHF/pages/Distributor/BCCongNoTheoKH.jsp";
				response.sendRedirect(nextJSP);
	}

	private void CreatePivotTable(OutputStream out,String[] manghien,Ireport obj, String query) throws Exception {
		try {
			Workbook workbook = new Workbook();
			CreateHeader(workbook,obj); 
			FillData(workbook,manghien,query); 
			workbook.save(out, 0);
		} catch (Exception ex) {
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}

	private void CreateHeader(Workbook workbook,Ireport obj) {
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);	    
	    worksheet.setName("BCCongNoTheoKhachHang");
	    Cells cells = null;
	    cells = worksheet.getCells();	    
	    cells.setRowHeight(0, 50.0f);
	    
	    Cell cell = null;
	    cell = cells.getCell("A1");		
	    cells.merge(0, 0, 0, 3);
	    ReportAPI.getCellStyle(cell,Color.RED, true, 16, "Báo Cáo Công Nợ Theo Khách Hàng");
	    
	    
	   
	    cells.merge(1, 0, 1, 2);
	    cell = cells.getCell("A2");
	    ReportAPI.getCellStyle(cell,Color.BLUE,true,10,"From : " + obj.gettungay()+ " To : " + obj.getdenngay());
	    setHeaderCell(cell);
	    
	    cells.merge(2, 0, 2, 2);
	    cell = cells.getCell("A3");	   
	    ReportAPI.getCellStyle(cell,Color.BLUE,true,10,"Date Create : " + obj.getDateTime());
	    setHeaderCell(cell);
	    
	    cells.merge(3, 0, 3, 2);
	    cell = cells.getCell("A4");
	    ReportAPI.getCellStyle(cell,Color.BLUE,true,10,"User " + obj.getuserTen());
	    setHeaderCell(cell);
	    
	    
	    
	    
	    
	    cell = cells.getCell("A11");	
	    cell.setStyle(null);
	    cells.merge(10, 0, 11, 0);	    
	    cells.setColumnWidth(0, 5.0f);	
	    MergeCellAndBorder(cells,10, 0, 11, 0);
	    cell.setValue("STT");	ReportAPI.setCellHeader(cell);

	    
	    
	    cells.merge(10,1,11,1);
	    cells.setColumnWidth(1, 18.0f);
	    MergeCellAndBorder(cells,10,1,11,1);
	    cell = cells.getCell("B11");		cell.setValue("Mã khách hàng");	ReportAPI.setCellHeader(cell);

	    
	    
	    cells.merge(10, 2, 11, 2);
	    cells.setColumnWidth(2, 20.0f);
	    MergeCellAndBorder(cells,10,2,11,2);
	    cell = cells.getCell("C11");		cell.setValue("Tên khách hàng");	ReportAPI.setCellHeader(cell);

	    
	    
	    cells.merge(10, 3, 11, 3);	
	    cells.setColumnWidth(3, 30.0f);
	    MergeCellAndBorder(cells,10, 3, 11, 3);	
	    cell = cells.getCell("D11");		cell.setValue("Địa chỉ");	ReportAPI.setCellHeader(cell);
	    
	    cells.merge(10, 4, 10, 5);
	    cells.setColumnWidth(4, 20.0f);
	    MergeCellAndBorder(cells,10, 4, 10, 5);
	    cell = cells.getCell("E11");		cell.setValue("Số dư đầu kỳ");	ReportAPI.setCellHeader(cell);
	    
	    cells = worksheet.getCells();
	    cell = cells.getCell("E12");		cell.setValue("Có");	ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("F12");		cell.setValue("Nợ");	ReportAPI.setCellHeader(cell);
	    cells.merge(10, 6, 10, 7);
	    cells.setColumnWidth(5, 20.0f);
	    MergeCellAndBorder(cells,10,6,10,7);
	    cell = cells.getCell("G11");			cell.setValue("Phát sinh trong kỳ"); ReportAPI.setCellHeader(cell);
	    
	    cells = worksheet.getCells();
	    cell = cells.getCell("G12");		cell.setValue("Có");ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("H12");		cell.setValue("Nợ");ReportAPI.setCellHeader(cell);
	    
	    
	    cells.merge(10, 8, 10, 9);
	    cells.setColumnWidth(6, 20.0f);
	    MergeCellAndBorder(cells,10, 8, 10, 9);
	    cell = cells.getCell("I11");			cell.setValue("Số dư cuối kỳ");ReportAPI.setCellHeader(cell);
	    
	    cells = worksheet.getCells();
	    cell = cells.getCell("I12");		cell.setValue("Có");ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("J12");		cell.setValue("Nợ");ReportAPI.setCellHeader(cell);
	    
	    cells.setColumnWidth(7, 20.0f);	cells.setColumnWidth(8, 20.0f);	cells.setColumnWidth(9, 20.0f);
	   
	    
	    
	}
	private void FillData(Workbook workbook,String[] manghien, String query) throws Exception {
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		
		for(int i=0;i<10;++i){
	    	cells.setColumnWidth(i, 15.0f);	    	
	    }	
		dbutils db = new dbutils();
		ResultSet rs = db.get(query);
		int index = 13;
	    Cell cell = null;
	    
		while (rs.next()) {
			cell = cells.getCell("A" + String.valueOf(index));		cell.setValue(index-12);			
			cell = cells.getCell("B" + String.valueOf(index));		cell.setValue(rs.getString("KHID"));
			cell = cells.getCell("C" + String.valueOf(index));		cell.setValue(rs.getString("KHACHHANG"));
			cell = cells.getCell("D" + String.valueOf(index));		cell.setValue(rs.getString("DIACHI"));
			cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(rs.getString("NODAUKY"));						
			cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(rs.getString("CODAUKY"));			
			cell = cells.getCell("G" + String.valueOf(index));		cell.setValue(rs.getString("NOTRONGKY"));
			cell = cells.getCell("H" + String.valueOf(index));		cell.setValue(rs.getString("COTRONGKY"));			
			cell = cells.getCell("I" + String.valueOf(index));		cell.setValue(rs.getString("NOCUOIKY"));
			cell = cells.getCell("J" + String.valueOf(index));		cell.setValue(rs.getString("NOCUOIKY"));			
			index++;
			
		}	
	}
	private void setHeaderCell(Cell cell) {		
		Style style = cell.getStyle();		
		style.setHAlignment(HorizontalAlignmentType.CENTRED);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		cell.setStyle(style);
	}
	private void MergeCellAndBorder(Cells cells,int y1,int x1,int y2,int x2){
		cells.merge(y1, x1, y2, x2);
		cells.getCells(y1, x1, y2, x2, true);
		Iterator<Cell> iCell = cells.getCellIterator();
		while (iCell.hasNext()) {
			setHeaderCell(iCell.next());
		}	
	}

}
