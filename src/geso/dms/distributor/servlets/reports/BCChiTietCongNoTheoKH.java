package geso.dms.distributor.servlets.reports;

import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.report.Ireport;
import geso.dms.distributor.beans.report.imp.Report;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
 * Servlet implementation class BCChiTietCongNoTheoKH
 */
public class BCChiTietCongNoTheoKH extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	private String setQuery(HttpServletRequest request,String st) {
			String query = "SELECT	"+			
				" DH.NGAYNHAP AS NGAY," +				
				" DH.PK_SEQ AS SOHOADON," +				
				" KH.TEN AS KHACHHANG," +				
				" ISNULL(SUM(DH_SP.SOLUONG*DH_SP.GIAMUA - DH_SP.CHIETKHAU),0)*1.1 AS TONGNO" +					
			" FROM (select * from donhang where trangthai ='1' "+ st +") DH " +					
				"INNER JOIN DONHANG_SANPHAM DH_SP ON DH.PK_SEQ = DH_SP.DONHANG_FK " +					
				"INNER JOIN KHACHHANG KH ON KH.PK_SEQ = DH.KHACHHANG_FK " +					
				"INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = KH.NPP_FK " +					
			//" WHERE NPP.PK_SEQ = '102433'  AND DH.NGAYNHAP >= '" + tungay +"' AND DH.NGAYNHAP <= '" + denngay +"' " +					
			" GROUP BY DH.NGAYNHAP, DH.PK_SEQ, KH.TEN";
		return query;
	}

    public BCChiTietCongNoTheoKH() {
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
	//    System.out.println(userId);
	    obj.setuserId(userId);
	    obj.init();	    
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Distributor/BCChiTietCongNoTheoKH.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		Utility util = new Utility();
		Ireport obj = new Report();
		String userId = (String) session.getAttribute("userId");  
	  String userTen = (String) session.getAttribute("userTen"); 
		      if(userId ==null)
		    	userId ="";
		    obj.setuserId(userId);
		    obj.setuserTen(userTen);
	  
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
	   			 st = st + " and ngaynhap >='"+ denngay +"'";
	   		
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
					response.setHeader("Content-Disposition","attachment; filename=BCChiTietCongNoTheoKH.xls");
					OutputStream out = response.getOutputStream();
					
					String query  = setQuery(request,st);								
					CreatePivotTable(out,obj,fieldsHien,query); 
		
				} catch (Exception ex) {
					request.getSession().setAttribute("errors", ex.getMessage());
				}
			 }
			 	obj.init();	    
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
				String nextJSP = "/AHF/pages/Distributor/BCChiTietCongNoTheoKH.jsp";
				response.sendRedirect(nextJSP);
	}

	private void CreatePivotTable(OutputStream out,Ireport obj,String[] manghien, String query) throws Exception {
		try {
			Workbook workbook = new Workbook();
			CreateHeader(workbook,obj); // Táº¡o Header cho trang bÃ¡o cÃ¡o
			FillData(workbook,manghien,query); // Thiáº¿t láº­p in xuáº¥t dá»¯ liá»‡u
			workbook.save(out, 0);
		} catch (Exception ex) {
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}

	private void CreateHeader(Workbook workbook,Ireport obj) {
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);	    
	    worksheet.setName("BCChiTietCongNoTheoKH");
	    Cells cells = worksheet.getCells();	    
	    cells.setRowHeight(0, 50.0f);	    
	    Cell cell = cells.getCell("A1");
	   
	    ReportAPI.getCellStyle(cell,Color.RED, true, 16, "BÁO CÁO CHI TIẾT CÔNG NỢ THEO KHÁCH HÀNG");
	    cell = cells.getCell("A2");
	    ReportAPI.getCellStyle(cell,Color.BLUE,true,10,"From : " +obj.gettungay() + " To : " + obj.getdenngay());
	    cell = cells.getCell("A3");
	    ReportAPI.getCellStyle(cell,Color.BLUE,true,10,"Date create: " + obj.getDateTime()); 
	    cell = cells.getCell("A4");
	    ReportAPI.getCellStyle(cell,Color.BLUE,true,10,"User: " + obj.getuserTen());
	    
	    
	    
	    cell = cells.getCell("AA1");		cell.setValue("Số hóa đơn");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AB1");		cell.setValue("Ngày");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AC1");			cell.setValue("Khách hàng");	ReportAPI.setCellHeader(cell);
		//cell = cells.getCell("AD1");		cell.setValue("Số sêri");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AD1");		cell.setValue("Tổng nợ");		ReportAPI.setCellHeader(cell);
				
	    
	    
	}
	private void FillData(Workbook workbook,String[] manghien, String query) throws Exception {
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		
		for(int i=0;i<3;++i){
	    	cells.setColumnWidth(i, 15.0f);	    	
	    }	
		dbutils db = new dbutils();
		ResultSet rs = db.get(query);
		int index = 2;
	    Cell cell = null;	    
		while (rs.next()) {
			cell = cells.getCell("AA" + String.valueOf(index));		cell.setValue(rs.getString("SOHOADON"));			
			cell = cells.getCell("AB" + String.valueOf(index));		cell.setValue(rs.getString("KHACHHANG"));
			cell = cells.getCell("AC" + String.valueOf(index));		cell.setValue(rs.getString("NGAY"));
		//	cell = cells.getCell("AD" + String.valueOf(index));	cell.setValue(Float.parseFloat(rs.getString("TONGNO")));	
			cell = cells.getCell("AD" + String.valueOf(index));	cell.setValue(Float.parseFloat(rs.getString("TONGNO")));
			//cell = cells.getCell("AD" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("TONGNO")));						
			index++;
		}
		rs.close();
		ReportAPI.setHidden(workbook,10);
		PivotTables pivotTables = worksheet.getPivotTables();
		String pos = Integer.toString(index-1);	
	    int j = pivotTables.add("=BCChiTietCongNoTheoKH!AA1:AD" + pos,"B12","PivotTable");	    
	    PivotTable pivotTable = pivotTables.get(j);
	    pivotTable.setRowGrand(true);
	    pivotTable.setColumnGrand(true);
	    pivotTable.setAutoFormat(true);  
	    Hashtable<String, Integer> selected = new Hashtable<String, Integer>();
	    selected.put("Ngay",1);
	    selected.put("KhachHang",2);
	    selected.put("So_hoa_don",0);
	  //  selected.put("So_seri",3);
	    selected.put("Chi_tiet_cong_no",3);
	//	pivotTable.addFieldToArea(PivotFieldType.ROW, 1); 		
	   
	   for(int i=0;i<manghien.length ;i++)
		{
	    	int so = selected.get(manghien[i]);
			    System.out.println("so "+ so +" ,mang:"+ manghien[i] );
			   if(so ==3)
			    	pivotTable.addFieldToArea(PivotFieldType.DATA, so); 		
			   else
				   pivotTable.addFieldToArea(PivotFieldType.ROW, so); 		
		}
	   
	}
}
