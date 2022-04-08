package geso.dms.center.servlet.reports;

import geso.dms.center.bean.report.ICenterReport;
import geso.dms.center.db.sql.dbutils;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTables;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;


public class StockInTransit extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	ICenterReport obj;
	String query = "";
	int length = 0;
	
	public void setQuery(HttpServletRequest request){
		obj = new ICenterReport();		      
        obj.setFromDate(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays")));
        obj.setToDate(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays")));
        obj.setChanel(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhs")));
        obj.setRegion(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungs")));
        obj.setErea(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucs")));
        obj.setDistributor(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("npps")));
        obj.setBusinessUnit(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkds")));
        obj.setBrands(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhans")));
        obj.setCategory(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chungloais")));
        obj.setProductName(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sanphams")));
        obj.setUnit(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("units")));
       
        obj.setFieldShow(request.getParameterValues("fieldsHien"));
		obj.setFieldHidden(request.getParameterValues("fieldsAn"));
		if (obj.getFieldHidden() == null) {
			if (obj.getFieldShow() != null) {
				length = obj.getFieldShow().length;
			} else {
				length = 0;
			}
		} else {
			length = obj.getFieldShow().length + obj.getFieldHidden().length;
		}        
        obj.setUserName((String)request.getSession().getAttribute("userTen"));
        obj.setUserID((String)request.getSession().getAttribute("userId")); 
        obj.setTitle("Stock In Transit (Without VAT)    (VND/Case/Ton)  ");
        
              
        
        query = " SELECT KBH.PK_SEQ AS KENHID, KBH.TEN AS KENH,	VUNG.PK_SEQ AS VUNGID, VUNG.TEN AS VUNG," +
	        		"KV.PK_SEQ AS KVID, KV.TEN AS KHUVUC,ISNULL(QH.PK_SEQ,0) AS QHID, ISNULL(QH.TEN,'') AS QUANHUYEN," +
	        		"NPP.PK_SEQ AS NPPID, NPP.TEN AS NPPTEN,ISNULL(GSBH.PK_SEQ,0) AS GSBHID, ISNULL(GSBH.TEN, '') AS GSBH," +
	        		"NHAPHANG.CHUNGTU,DVKD.PK_SEQ AS DVKDID, DVKD.DONVIKINHDOANH,NHAN.PK_SEQ, NHAN.TEN AS NHANTEN,CHUNGLOAI.PK_SEQ, CHUNGLOAI.TEN AS CLTEN," +
	        		"SP.MA AS SPMA, SP.TEN AS SPTEN,NHAPHANG_SP.SOLUONG AS QUANTITY,NHAPHANG_SP.SOLUONG*GIANET AS AMOUNT " +
        		" FROM NHAPHANG NHAPHANG	" +
        			" INNER JOIN NHAPHANG_SP NHAPHANG_SP ON NHAPHANG_SP.NHAPHANG_FK = NHAPHANG.PK_SEQ " +
        			" INNER JOIN SANPHAM SP ON NHAPHANG_SP.SANPHAM_FK = SP.MA " +
        			" INNER JOIN NHANHANG NHAN ON NHAN.PK_SEQ = SP.NHANHANG_FK " +
        			" INNER JOIN CHUNGLOAI CHUNGLOAI ON CHUNGLOAI.PK_SEQ = SP.CHUNGLOAI_FK " +
        			" INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK " +
        			" INNER JOIN NHAPHANPHOI NPP ON NHAPHANG.NPP_FK = NPP.PK_SEQ " +
        			" INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ = NHAPHANG.KBH_FK " +
        			" INNER JOIN KHUVUC KV ON KV.PK_SEQ = NPP.KHUVUC_FK	" +
        			" INNER JOIN VUNG VUNG ON VUNG.PK_SEQ = KV.VUNG_FK " +
        			" LEFT  JOIN QUANHUYEN QH on QH.PK_SEQ = NPP.QUANHUYEN_FK " +
        			" LEFT JOIN DONDATHANG DDH ON DDH.PK_SEQ = NHAPHANG.DATHANG_FK " +
        			" LEFT JOIN GIAMSATBANHANG GSBH ON GSBH.PK_SEQ = DDH.GSBH_FK " +
        		" WHERE NHAPHANG.TRANGTHAI = '0' AND NHAPHANG.NGAYCHUNGTU >='" + obj.getFromDate() + "' AND NHAPHANG.NGAYCHUNGTU <= '" + obj.getToDate() + "'";
        
        System.out.print(query);
        
        
	}
	public String getQuery(){
		return query;
	}
    
    public StockInTransit() {
        super();
        
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");			
			response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "attachment; filename=DailySalesReport(YTD)(tt).xls");
	        OutputStream out = response.getOutputStream();
	        
	        setQuery(request);        //Thiết lập chuỗi truy vấn vào CSDL lấy tham số từ Client
	        CreatePivotTable(out, response, request);	// Create PivotTable 
	        
		}catch(Exception ex){
			request.getSession().setAttribute("errors", ex.getMessage());
		}
	}
	private void CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request)
			throws Exception{
		try {
			Workbook workbook = new Workbook();			
			CreateHeader(workbook); 
			FillData(workbook); 
			workbook.save(out, 0);
		} catch (Exception ex) {
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}
	private void CreateHeader(Workbook workbook) {
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);	    
	    worksheet.setName("StockInTransit");
	    
	    
	    Cells cells = worksheet.getCells();	    
	    cells.setRowHeight(0, 50.0f);	    
	    Cell cell = cells.getCell("A1");	
	    ReportAPI.getCellStyle(cell,Color.RED, true, 16, obj.getTitle());
	    cell = cells.getCell("A2");
	    ReportAPI.getCellStyle(cell,Color.BLUE,true,10,"From : " + obj.getFromDate() + " To : " + obj.getToDate());
	    cell = cells.getCell("A3");
	    ReportAPI.getCellStyle(cell,Color.BLUE,true,10,"Date Create : " + obj.getCreateDate()); 
	    cell = cells.getCell("A4");
	    ReportAPI.getCellStyle(cell,Color.BLUE,true,10,"User : " + obj.getUserName());
	    
	    
	    cell = cells.getCell("AA1");		cell.setValue("Channel");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AB1");		cell.setValue("Region");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AC1");		cell.setValue("Area");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AD1");		cell.setValue("Sales Sup");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AE1");		cell.setValue("Distributor");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AF1");		cell.setValue("Billing Number");		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AG1");		cell.setValue("SKU");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AH1");		cell.setValue("Piece");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AI1");		cell.setValue("Amount");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AJ1");		cell.setValue("Province");					ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AK1");		cell.setValue("Town");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AL1");		cell.setValue("Distributor code");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AM1");		cell.setValue("Business Unit");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AN1");		cell.setValue("Catergory");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AO1");		cell.setValue("SKU Code");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AP1");		cell.setValue("Brands");			ReportAPI.setCellHeader(cell);	    
		cell = cells.getCell("AQ1");		cell.setValue("Pridate");			ReportAPI.setCellHeader(cell);
	}
	private void FillData(Workbook workbook) throws Exception {
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		
		for(int i=0;i<length;++i){
	    	cells.setColumnWidth(i, 15.0f);
	    }	
		dbutils db = new dbutils();
		ResultSet rs = db.get(getQuery());
		int index = 2;
	    Cell cell = null;	    
	    while (rs.next()) {
			cell = cells.getCell("AA" + String.valueOf(index));		cell.setValue(rs.getString("KENH"));			
			cell = cells.getCell("AB" + String.valueOf(index));		cell.setValue(rs.getString("VUNG"));
			cell = cells.getCell("AC" + String.valueOf(index));		cell.setValue(rs.getString("KHUVUC"));
			cell = cells.getCell("AD" + String.valueOf(index));		cell.setValue(rs.getString("GSBH"));
			cell = cells.getCell("AE" + String.valueOf(index));		cell.setValue(rs.getString("NPPTEN"));
			cell = cells.getCell("AF" + String.valueOf(index));		cell.setValue(rs.getString("CHUNGTU"));
			cell = cells.getCell("AG" + String.valueOf(index));		cell.setValue(rs.getString("SPTEN"));
			cell = cells.getCell("AH" + String.valueOf(index));		cell.setValue(rs.getString("QUANTITY"));
			cell = cells.getCell("AI" + String.valueOf(index));		cell.setValue(rs.getString("AMOUNT"));
			cell = cells.getCell("AJ" + String.valueOf(index));		cell.setValue(rs.getString("QUANHUYEN"));
			cell = cells.getCell("AK" + String.valueOf(index));		cell.setValue("");
			cell = cells.getCell("AL" + String.valueOf(index));		cell.setValue(rs.getString("NPPID"));
			cell = cells.getCell("AM" + String.valueOf(index));		cell.setValue(rs.getString("DONVIKINHDOANH"));	    	 
			cell = cells.getCell("AN" + String.valueOf(index));		cell.setValue(rs.getString("CLTEN"));
			cell = cells.getCell("AO" + String.valueOf(index));		cell.setValue(rs.getString("SPMA"));
			cell = cells.getCell("AP" + String.valueOf(index));		cell.setValue(rs.getString("NHANTEN"));
			cell = cells.getCell("AQ" + String.valueOf(index));		cell.setValue("Pridate");			
			index++;
		}
	    ReportAPI.setHidden(workbook,length);
		PivotTables pivotTables = worksheet.getPivotTables();
		String pos = Integer.toString(index-1);	
	    int j = pivotTables.add("=StockInTransit!AA1:AQ" + pos,"B12","PivotTable");	   
	    
	    PivotTable pivotTable = pivotTables.get(j);
	    pivotTable.setRowGrand(true);
	    pivotTable.setColumnGrand(true);
	    pivotTable.setAutoFormat(true);   
	    
	    for(int i=0;i<obj.getFieldShow().length;++i){
	    	pivotTable.addFieldToArea(PivotFieldType.ROW, i); 		
   	     	pivotTable.getRowFields().get(i).setAutoSubtotals(false);
	    }
	}

}
