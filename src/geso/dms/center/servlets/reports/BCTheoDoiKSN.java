package geso.dms.center.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.distributor.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;


public class BCTheoDoiKSN extends HttpServlet {
   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public BCTheoDoiKSN() {
        super();
       
    }
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		String querystring = request.getQueryString();
		Utility util = new Utility();
		
		obj.setuserId(util.getUserId(querystring));
		obj.setuserTen((String) session.getAttribute("userTen"));
			
		obj.init();
		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Center/TheoDoiKhoaSo.jsp";
		response.sendRedirect(nextJSP);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		OutputStream out = response.getOutputStream();
		Utility util = new Utility();
		
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		
		obj.settungay(util.antiSQLInspection(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays")))));
		obj.setdenngay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays"))));
		
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");	
		
		obj.setvungId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"))):"");
			
		obj.setkhuvucId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"))):"");
		
		
		obj.setnppId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"))):"");
		
		String action = (String) geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		String nextJSP = "/AHF/pages/Center/TheoDoiKhoaSo.jsp";
		
		String sql = "";
		if(obj.getvungId().length()>0){
			sql += " and v.pk_seq = '" + obj.getvungId() + "'";
		}
		if(obj.getkhuvucId().length()>0){
			sql += " and kv.pk_seq='"+ obj.getkhuvucId() + "'";
		}
		if(obj.getnppId().length()>0){
			sql +=" and n.pk_seq='" + obj.getnppId() +"'";
		}
		try{
			
			if (action.equals("create")) {			
			/*	response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition",
						"attachment; filename=THEO-DOI-KHOA-SO-NPP.xls");
*/	
				
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=THEO-DOI-KHOA-SO-NPP.xlsm");
				
				String query = setQuery(sql, obj);
		        if(!ExportToExcel(out,obj,query)){
		        	response.setContentType("text/html");
		            PrintWriter writer = new PrintWriter(out);
		            writer.print("Xin loi khong co bao cao trong thoi gian nay");
		            writer.close();
		        }
			}else{
				response.sendRedirect(nextJSP);
			}
			
		}catch(Exception ex){
			obj.setMsg(ex.getMessage());
			response.sendRedirect(nextJSP);
		}
		obj.init();
		session.setAttribute("obj", obj);		
	}
	private String setQuery(String sql,IStockintransit obj){
		String query = "SELECT  v.TEN AS Region,kv.TEN AS Area,n.PK_SEQ AS DistributorCode," +
				"		n.SITECODE AS SiteCode,	n.TEN AS Distributor,k.NGAYKS AS Date," +
				"		1 as  Data, mo.ngaytao as TIMEOPEN " +
				"   	 FROM KHOASONGAY k" +
				"		INNER JOIN NHAPHANPHOI n ON n.PK_SEQ = k.NPP_FK " +
				"		LEFT JOIN mokhoaso mo on mo.npp_fk = n.pk_seq "+
				"		INNER JOIN KHUVUC kv ON kv.PK_SEQ = n.KHUVUC_FK" +
				"		INNER JOIN VUNG v ON v.PK_SEQ = kv.VUNG_FK" +
				"	WHERE k.NGAYKS>= '" + obj.gettungay() +"' AND k.NGAYKS <='" + obj.getdenngay() +"' " + sql + " order by k.ngayks";
		System.out.println("__+__query_"+query);
		return query;
	}
	private boolean ExportToExcel(OutputStream out, IStockintransit obj,String query)throws Exception {
	
		String chuoi=getServletContext().getInitParameter("path") + "\\THEO-DOI-KHOA-SO-NPP.xlsm";
		 FileInputStream fstream = new FileInputStream(chuoi);	
		// File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\THEO-DOI-KHOA-SO-NPP.xlsm");
		//	FileInputStream fstream = new FileInputStream(f) ;
		 Workbook workbook = new Workbook();
	workbook.open(fstream);
	workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		
		
		boolean isFillData = false;
		CreateHeader(workbook, obj);
		isFillData = FillData(workbook, obj, query);
		if (!isFillData)
			return false;
		workbook.save(out);
		fstream.close();
		return true;	
	}


	private boolean FillData(Workbook workbook, IStockintransit obj,
			String query) throws Exception {
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();		
		ResultSet rs = db.get(query);
		int i = 2;
		if(rs!=null){
			Cell cell = null;
			try{
				while(rs.next()){					
					String region = rs.getString("Region");
					String area = rs.getString("Area");
					String distributorCode = rs.getString("DistributorCode");
					String siteCode = rs.getString("SiteCode");
					String distributor = rs.getString("Distributor");
					String date = rs.getString("Date");
					String data = rs.getString("Data");
					String timeopen = rs.getString("Timeopen");
					
					
					cell = cells.getCell("EA" + Integer.toString(i));	cell.setValue(region);
					cell = cells.getCell("EB" + Integer.toString(i));	cell.setValue(area);
					cell = cells.getCell("EC" + Integer.toString(i));	cell.setValue(distributorCode);
					cell = cells.getCell("ED" + Integer.toString(i));	cell.setValue(siteCode);
					cell = cells.getCell("EE" + Integer.toString(i));	cell.setValue(distributor);
					cell = cells.getCell("EF" + Integer.toString(i));	cell.setValue(date);										
					cell = cells.getCell("EG" + Integer.toString(i));	cell.setValue(data);
					cell = cells.getCell("EH" + Integer.toString(i));	cell.setValue(timeopen);
					++i;					
					if(i>65000){
						if (rs != null) rs.close();							
						if(db != null) db.shutDown();
						throw new Exception("Du lieu vuot qua gioi han file Excel. Vui long chon dieu kien can lay bao cao");							
					}
				}
				if (rs != null)
					rs.close();
				
				if(db != null) db.shutDown();
				
				if(i==2){					
					throw new Exception("Xin loi,khong co bao cao voi dieu kien da chon....!!!");
				}
			/*	setHidden(workbook, 170);
				PivotTables pivotTables = worksheet.getPivotTables();
				String pos = Integer.toString(i - 1);
				int index = pivotTables.add("=KhoaSoNgay!EA1:EG" + pos, "A12","PivotTable");
				PivotTable pivotTable = pivotTables.get(index);
				
				
				pivotTable.addFieldToArea(PivotFieldType.ROW, 0);	
				pivotTable.getRowFields().get(0).setAutoSubtotals(false);
				
				pivotTable.addFieldToArea(PivotFieldType.ROW, 1);
				pivotTable.getRowFields().get(1).setAutoSubtotals(false);
				
				pivotTable.addFieldToArea(PivotFieldType.ROW, 2);
				pivotTable.getRowFields().get(2).setAutoSubtotals(false);
				
				pivotTable.addFieldToArea(PivotFieldType.ROW, 3);
				pivotTable.getRowFields().get(3).setAutoSubtotals(false);
				
				pivotTable.addFieldToArea(PivotFieldType.ROW, 4);
				pivotTable.getRowFields().get(4).setAutoSubtotals(false);
				
				pivotTable.addFieldToArea(PivotFieldType.DATA, 6);
				pivotTable.getDataFields().get(0).setDisplayName("KSN");
				
				pivotTable.addFieldToArea(PivotFieldType.COLUMN, 5);
				pivotTable.getColumnFields().get(0).setDisplayName("Date");*/
				
								
				  
			}catch(Exception ex){
				throw new Exception(ex.getMessage());
			}
		}else{
			return false;
		}
		return true;
	}


	private void CreateHeader(Workbook workbook, IStockintransit obj)throws Exception {
		try{
			
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");

			Cells cells = worksheet.getCells();
			cells.setRowHeight(0, 20.0f);
			Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 16,
					"THEO DÕI KHÓA SỔ NGÀY ");
			cell = cells.getCell("A2");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 9,
					"Từ ngày : " + obj.gettungay() + "Đến ngày: " + obj.getdenngay());
			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 10, "Ngày tạo : "
					+ obj.getDateTime());
			cell = cells.getCell("A4");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 10,
					"Được tạo bởi : " + obj.getuserTen());			
			
			cell = cells.getCell("EA1");		cell.setValue("Region");
			cell = cells.getCell("EB1");		cell.setValue("Area");			
			cell = cells.getCell("EC1");		cell.setValue("Distributor Code");
			cell = cells.getCell("ED1");		cell.setValue("SiteCode");	
			cell = cells.getCell("EE1");		cell.setValue("Distributor");
			cell = cells.getCell("EF1");		cell.setValue("Date");		
			cell = cells.getCell("EG1");		cell.setValue("Data");			
			cell = cells.getCell("EH1");		cell.setValue("TIMEOPEN");			
		}catch(Exception ex){
			throw new Exception("Khong tao duoc Header cho bao cao");
		}
		
	}
/*	private void setHidden(Workbook workbook,int columCount){
		
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);	   	   
	    Cells cells = worksheet.getCells();
	    for(int i=120;i<=columCount;++i){
	    	cells.hideColumn(i);
	    }*/
}
	

