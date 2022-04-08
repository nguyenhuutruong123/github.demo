package geso.dms.center.servlets.reports;

import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;


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


public class DaiDienKinhDoanh extends HttpServlet {
	   
    public DaiDienKinhDoanh() {
        super();
        
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			OutputStream out = response.getOutputStream();
			String userTen = (String) request.getSession().getAttribute("userTen");			
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; "
					+ "filename=DanhSachDDKD(tt).xls");
			ExportToExcel(out, userTen);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	private void ExportToExcel(OutputStream out,String UserCreate)throws Exception{
		try{
			Workbook workbook = new Workbook();
			createHeader(workbook, UserCreate);
			fillData(workbook);
			workbook.save(out,0);
		}catch(Exception ex){
			throw new Exception(ex.getMessage());
		}
		
	}
	private void createHeader(Workbook workbook,String UserCreate)throws Exception{
		try{
			Worksheets worksheets = workbook.getWorksheets();
		    Worksheet worksheet = worksheets.getSheet(0);
		    worksheet.setName("DanhSachDDKD");
		    
		    Cells cells = worksheet.getCells();	    
		    cells.setRowHeight(1, 13);    
		    Cell cell = cells.getCell("B1");	
		    ReportAPI.getCellStyle(cell,Color.RED, true, 16, "DANH SÁCH ĐẠI DIỆN KINH DOANH");
		    cell = cells.getCell("B2");
		    ReportAPI.getCellStyle(cell,Color.BLUE,true,10,"Date Create: " + ReportAPI.NOW("dd/MM/yyyy : hh-mm-ss"));
		    cell = cells.getCell("B3");
		    ReportAPI.getCellStyle(cell,Color.BLUE,true,10,"Distributor: " + UserCreate);
		    
		    cells.setColumnWidth(1, 15.0f);
		    cells.setColumnWidth(2, 30.0f);
		    cells.setColumnWidth(3, 30.0f);
		    cells.setColumnWidth(4, 30.0f);
		    cells.setColumnWidth(5, 30.0f);
		    
		    
		    cell = cells.getCell("AA1");		cell.setValue("Mã DDKD");				ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("AB1");		cell.setValue("Tên DDKD");				ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("AC1");		cell.setValue("Trạng Thái DDKD");				ReportAPI.setCellHeader(cell);
			cell = cells.getCell("AD1");		cell.setValue("Mã NPP");				ReportAPI.setCellHeader(cell);
			cell = cells.getCell("AE1");		cell.setValue("Tên NPP");				ReportAPI.setCellHeader(cell);
			cell = cells.getCell("AF1");		cell.setValue("Mã GSBH");				ReportAPI.setCellHeader(cell);
			cell = cells.getCell("AG1");		cell.setValue("Tên GSBH");				ReportAPI.setCellHeader(cell);
			cell = cells.getCell("AH1");		cell.setValue("Trạng Thái GSBH");			ReportAPI.setCellHeader(cell);
			cell = cells.getCell("AI1");		cell.setValue("Khu Vực");				ReportAPI.setCellHeader(cell);
			cell = cells.getCell("AJ1");		cell.setValue("Vùng/Miền");				ReportAPI.setCellHeader(cell);
			
		}catch(Exception ex){
			throw new Exception("Khong the tao duoc tua de cho bao cao");
		}
		
	}
	private void fillData(Workbook workbook)throws Exception{
		try{
			Worksheets worksheets = workbook.getWorksheets();
		    Worksheet worksheet = worksheets.getSheet(0);
		    Cells cells = worksheet.getCells();
		    Cell cell=null;
		    dbutils db = new dbutils();
		    
		    
			String query ="select a.pk_seq as id, a.ten, a.dienthoai, a.diachi, a.trangthai AS TTDDKD, a.ngaytao," +
						"	   b.ten as nguoitao, a.ngaysua, c.ten as nguoisua, d.pk_seq as nppId," +
						"	   d.ten as nppTen, F.PK_SEQ AS gsbhId,f.ten as gsbhTen, f.TRANGTHAI AS TTGSBH, g.ten as kbhTen," +
						"	   k.TEN AS TenKV,v.TEN AS TenVung " +
						"from daidienkinhdoanh a, nhanvien b," +
						" 		nhanvien c, nhaphanphoi d, ddkd_gsbh e, giamsatbanhang f, kenhbanhang g,VUNG v,KHUVUC k  " +
						"where a.nguoitao = b.pk_seq  and a.nguoisua = c.pk_seq	  and a.npp_fk = d.pk_seq  and a.pk_seq = e.ddkd_fk " +
						"	  and e.gsbh_fk = f.pk_seq" +
						"	  and f.kbh_fk=g.pk_seq" +
						"	  AND k.PK_SEQ = d.KHUVUC_FK" +
						"	  AND v.PK_SEQ = k.VUNG_FK";
			
			ResultSet rs = db.get(query);
			int rowIndex = 2;
			while(rs.next()){
				
				cell = cells.getCell("AA" + String.valueOf(rowIndex));		
				cell.setValue(rs.getString("id"));
				
				cell = cells.getCell("AB" + String.valueOf(rowIndex));		
				cell.setValue(rs.getString("ten"));
				
				cell = cells.getCell("AC" + String.valueOf(rowIndex));	
				cell.setValue(rs.getInt("TTDDKD")==1? "Hoạt động":"Ko Hoạt Động");
				
				
				cell = cells.getCell("AD" + String.valueOf(rowIndex));		
				cell.setValue(rs.getString("nppId"));
				
				
				cell = cells.getCell("AE" + String.valueOf(rowIndex));		
				cell.setValue(rs.getString("nppTen"));
				
				cell = cells.getCell("AF" + String.valueOf(rowIndex));		
				cell.setValue(rs.getString("gsbhId"));
				
				cell = cells.getCell("AG" + String.valueOf(rowIndex));		
				cell.setValue(rs.getString("gsbhTen"));
				
				cell = cells.getCell("AH" + String.valueOf(rowIndex));		
				cell.setValue(rs.getInt("TTGSBH")==1? "Hoạt động":"Ko Hoạt động");
				
				
				cell = cells.getCell("AI" + String.valueOf(rowIndex));		
				cell.setValue(rs.getString("TenKV"));
				
				
				cell = cells.getCell("AJ" + String.valueOf(rowIndex));		
				cell.setValue(rs.getString("TenVung"));
				++rowIndex;				
			}
			if(rs!=null){
				rs.close();
			}

			ReportAPI.setHidden(workbook, 10);
			PivotTables pivotTables = worksheet.getPivotTables();
			String pos = Integer.toString(rowIndex - 1);
			int j = pivotTables.add("=DanhSachDDKD!AA1:AJ" + pos, "B12", "PivotTable");
			PivotTable pivotTable = pivotTables.get(j);
			pivotTable.setRowGrand(true);
			pivotTable.setColumnGrand(true);
			pivotTable.setAutoFormat(true);
			
			
			for(int i=0;i<9;++i){
				pivotTable.addFieldToArea(PivotFieldType.ROW,i);	
				pivotTable.getRowFields().get(i).setAutoSubtotals(false);
			}
			
		}catch(Exception ex){
			throw new Exception("Khong the dien du lieu vao file Excel hoac khong co du lieu");
		}
	}

}
