package geso.dms.center.servlets.report;

import java.io.IOException;
import java.io.OutputStream;

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

/**
 * Servlet implementation class DailySalesReport
 */
public class DailySalesReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	ICenterReport obj;
	String query = "";
	String[] FieldShow;
	String[] FieldHidden;
	int FieldLength = 0;
	
	public void setQuery(HttpServletRequest request){
		obj = new ICenterReport();
		String month = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("months"));
        String year = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("years"));        
        obj.setFromDate(year + "-" + month + "-" + geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays")));
        obj.setToDate( year + "-" + month + "-" + geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays")));
        obj.setChanel(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhs")));
        obj.setRegion(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungs")));
        obj.setErea(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucs")));
        obj.setDistributor(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("npps")));
        obj.setBusinessUnit(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkds")));
        obj.setUnit(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("units")));
        obj.setUserName((String)request.getSession().getAttribute("userTen"));
        obj.setUserID((String)request.getSession().getAttribute("userId")); 
        obj.setTitle("Daily Sales Report");
        query = "";
	}
	public String getQuery(){
		return query;
	}
	
    public DailySalesReport() {
        super();
       
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");			
			response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "attachment; filename=DailySalesReport.xls");
	        OutputStream out = response.getOutputStream();
	        
	        
	        setQuery(request);        //Thiết lập chuỗi truy vấn vào CSDL lấy tham số từ Client
	        CreatePivotTable(out, response, request);	// Create PivotTable
	        
	        
		}catch(Exception ex){
			request.getSession().setAttribute("errors", ex.getMessage());
		}
	}
	
	private void CreatePivotTable(OutputStream out,HttpServletResponse response,
			HttpServletRequest request) throws Exception{
		try{
			Workbook workbook = new Workbook();			
			String Distributor = "";
			
			FieldShow = request.getParameterValues("fieldsHien");
			FieldHidden = request.getParameterValues("fieldsAn");	
			if(FieldHidden==null){
				if(FieldShow!=null){
					FieldLength = FieldShow.length;
				}
				else{
					FieldLength = 0;
				}				 
			}
			else
			{
				FieldLength = FieldShow.length + FieldHidden.length;
			}			
			CreateStaticHeader(workbook,Distributor);	//Tạo Header cho trang báo cáo			
			CreateStaticData(workbook);			//Thiết lập in xuất dữ liệu
			workbook.save(out,0);
			
		}catch(Exception ex){
			throw new Exception(ex.getMessage());
		}		
	}
	private void CreateStaticData(Workbook workbook) throws Exception{
		
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet("C");
	    Cells cells = worksheet.getCells();	    	    
	    for(int i=0;i<FieldLength;++i){
	    	cells.setColumnWidth(i, 15.0f);	    	
	    }	    
	    int index = 2;
	    Cell cell = null;
	    for (int j=0;j<10;++j){	    	
	    	cell = cells.getCell("A" + String.valueOf(index));		cell.setValue(j);			
			cell = cells.getCell("B" + String.valueOf(index));		cell.setValue(j);
			cell = cells.getCell("C" + String.valueOf(index));		cell.setValue(j);
			cell = cells.getCell("D" + String.valueOf(index));		cell.setValue(j);
			cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(j);
			cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(j);
			cell = cells.getCell("G" + String.valueOf(index));		cell.setValue(j);
			cell = cells.getCell("H" + String.valueOf(index));		cell.setValue(j);
			cell = cells.getCell("I" + String.valueOf(index));		cell.setValue(j);
			cell = cells.getCell("J" + String.valueOf(index));		cell.setValue(j);
			cell = cells.getCell("K" + String.valueOf(index));		cell.setValue(j);
			cell = cells.getCell("L" + String.valueOf(index));		cell.setValue(j);
			cell = cells.getCell("M" + String.valueOf(index));		cell.setValue(j);			
			index ++;			
	    }
	    
	    worksheet = worksheets.getSheet(0);
		PivotTables pivotTables = worksheet.getPivotTables();
		String pos = Integer.toString(index-1);	
	
	    int j = pivotTables.add("=C!A1:M" + pos,"B7","PivotTable");
	    PivotTable pivotTable = pivotTables.get(j);
	  
	    pivotTable.setRowGrand(true);
	    pivotTable.setColumnGrand(true);
	    pivotTable.setAutoFormat(true);  
	    
	    
	    
	    
		for (int i = 0; i < FieldShow.length-1; ++i) {			
			if (i > 2) {
				pivotTable.addFieldToArea(PivotFieldType.DATA, i);
			} else {
				pivotTable.addFieldToArea(PivotFieldType.ROW, i);
				pivotTable.getRowFields().get(i).setAutoSubtotals(false);
			}
		}	
	}
	private void CreateStaticHeader(Workbook workbook, String Distributor) {
		
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    worksheet.setName("Daily Sales Report(tt)");
	    Cells cells = worksheet.getCells();	    
	    cells.setRowHeight(4, 50.0f);
	    
	    
	    Cell cell = cells.getCell("B2");	
	    ReportAPI.getCellStyle(cell,Color.RED, true, 16, obj.getTitle());
	    cell = cells.getCell("B4");
	    ReportAPI.getCellStyle(cell,Color.BLUE,true,10,"Date Create : " + obj.getCreateDate());
	    
	    cell = cells.getCell("B5");
	    ReportAPI.getCellStyle(cell,Color.BLUE,true,10,"User : " + obj.getUserName());
	    
	    
	    worksheet = worksheets.addSheet();	    
	    worksheet.setName("C");
	    cells = worksheet.getCells();
	    cells.setRowHeight(0, 50.0f);
	    
		
		cell = cells.getCell("A1");		cell.setValue("Region");						ReportAPI.setCellHeader(cell);		
		cell = cells.getCell("B1");		cell.setValue("Area");							ReportAPI.setCellHeader(cell);
		cell = cells.getCell("C1");		cell.setValue("Business Unit");					ReportAPI.setCellHeader(cell);		
		cell = cells.getCell("D1");		cell.setValue("Monthly Target Primary");		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("E1");		cell.setValue("Monthly Target Secondary");		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("F1");		cell.setValue("Monthly Archive Primary");		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("G1");		cell.setValue("Monthly Archive Secondary");		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("H1");		cell.setValue("% MTD Primary");					ReportAPI.setCellHeader(cell);
		cell = cells.getCell("I1");		cell.setValue("% MTD Secondary");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("J1");		cell.setValue("Province");						ReportAPI.setCellHeader(cell);
		cell = cells.getCell("K1");		cell.setValue("Distributor code");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("L1");		cell.setValue("Distributor");					ReportAPI.setCellHeader(cell);
		cell = cells.getCell("M1");		cell.setValue("Sales Sup");						ReportAPI.setCellHeader(cell);	
		
	}
}
