package geso.dms.distributor.servlet.report;

import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlet.report.ReportAPI;
import geso.dms.distributor.bean.report.IDistributorReport;

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

/**
 * Servlet implementation class BCNoQuaHan
 */
public class BCNoQuaHan extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	String query = "";
	IDistributorReport obj;
	int length = 0;

	private void setQuery(HttpServletRequest request) {
		obj = new IDistributorReport();
		obj.setFromDate(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays")));
		obj.setToDate(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays")));

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
		
		obj.setUserName((String) request.getSession().getAttribute("userTen"));
		obj.setUserId((String) request.getSession().getAttribute("userId"));
		obj.setTitle("Báo cáo đơn hàng hủy trong kỳ");
		query = "";
	}

	private String getQuery() {
		return query;
	}

   
    public BCNoQuaHan() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition","attachment; filename=BCDonHangHuyTrongKy.xls");
			OutputStream out = response.getOutputStream();
			setQuery(request); // Thiết lập chuỗi truy vấn vào CSDL lấy tham số
								// từ Client
			CreatePivotTable(out); // Create PivotTable

		} catch (Exception ex) {
			request.getSession().setAttribute("errors", ex.getMessage());
		}
	}

	private void CreatePivotTable(OutputStream out) throws Exception {
		try {
			Workbook workbook = new Workbook();
			CreateHeader(workbook); // Tạo Header cho trang báo cáo
			FillData(workbook); // Thiết lập in xuất dữ liệu
			workbook.save(out, 0);
		} catch (Exception ex) {
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}

	private void CreateHeader(Workbook workbook) {
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);	    
	    worksheet.setName("BCNoQuaHan");
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
	    
	    
	    
	    cell = cells.getCell("AA1");		cell.setValue("STT");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AB1");		cell.setValue("Tên Khách Hàng");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AC1");		cell.setValue("Số Hóa Đơn");		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AD1");		cell.setValue("Ngày Hóa Đơn");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AE1");		cell.setValue("Số Tiền Cho Nợ");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AF1");		cell.setValue("Số Tiền Nợ");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AG1");		cell.setValue("Số Ngày Nợ");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AH1");		cell.setValue("Mã Khách Hàng");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AI1");		cell.setValue("Địa Chỉ");				ReportAPI.setCellHeader(cell);		
	    
	    
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
			cell = cells.getCell("AA" + String.valueOf(index));		cell.setValue(rs.getString("nguoixoa"));			
			cell = cells.getCell("AB" + String.valueOf(index));		cell.setValue(rs.getString("ngayxoa"));
			cell = cells.getCell("AC" + String.valueOf(index));		cell.setValue(rs.getString("tenkh"));
			cell = cells.getCell("AD" + String.valueOf(index));		cell.setValue(rs.getString("dhId"));
			cell = cells.getCell("AE" + String.valueOf(index));		cell.setValue(rs.getString("ngayhoadon"));
			cell = cells.getCell("AF" + String.valueOf(index));		cell.setValue(rs.getString("tensanpham"));
			cell = cells.getCell("AG" + String.valueOf(index));		cell.setValue(rs.getString("tongtien"));
			cell = cells.getCell("AH" + String.valueOf(index));		cell.setValue(rs.getString("chietkhau"));
			cell = cells.getCell("AI" + String.valueOf(index));		cell.setValue(rs.getString("masp"));
			cell = cells.getCell("AJ" + String.valueOf(index));		cell.setValue(rs.getString("scheme"));
			cell = cells.getCell("AK" + String.valueOf(index));		cell.setValue(rs.getString("diachi"));
			cell = cells.getCell("AL" + String.valueOf(index));		cell.setValue(rs.getString("makh"));
			cell = cells.getCell("AM" + String.valueOf(index));		cell.setValue(rs.getString("soluong"));	    	 
			cell = cells.getCell("AN" + String.valueOf(index));		cell.setValue(rs.getString("dongia"));
			index++;
		}
		ReportAPI.setHidden(workbook,length);
		PivotTables pivotTables = worksheet.getPivotTables();
		String pos = Integer.toString(index-1);	
	    int j = pivotTables.add("=BCNoQuaHan!AA1:AN" + pos,"B12","PivotTable");	    
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
