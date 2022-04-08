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
 * Servlet implementation class BangKeCongNoKH
 */
public class BangKeCongNoKH extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public BangKeCongNoKH() {
	        super();
	        
	    }

	private String setQuery(HttpServletRequest request,String tungay,String denngay,String st) {
		String query =" select " +  
					"dh.ngaynhap as ngay,ddkd.pk_seq as nvbhId,ddkd.ten as nvbhTen,kh.pk_seq as makh,kh.ten as tenkh," +
					"kh.diachi as diachi,dh.pk_seq as dhId,dh.NGAYNHAP AS ngayhoadon,sp.ma as masp,sp.ten as tensanpham," +
					"dh_sp.soluong as soluong,dh_sp.giamua as dongia,dh_sp.chietkhau as chietkhau,dh_sp.soluong*dh_sp.giamua - dh_sp.chietkhau  as tongtien, '' as scheme"+
				" from 	donhang dh	inner join khachhang kh on kh.pk_seq = dh.khachhang_fk " +
								"	inner join donhang_sanpham dh_sp on dh_sp.donhang_fk= dh.pk_seq " +						
							    "   inner join sanpham sp on sp.pk_seq = dh_sp.sanpham_fk " +						
							    "   inner join daidienkinhdoanh ddkd on ddkd.pk_seq = dh.ddkd_fk " +						
			    " where dh.ngaynhap >='" + tungay +"' and dh.ngaynhap <='" + denngay+ "' and dh.trangthai = 1 " ;	
				if(st.length()>0)
			    query= query + st;
			    query= query + " union " +						
					" select " +  
							"dh.ngaynhap as ngay,ddkd.pk_seq as nvbhId,	ddkd.ten as nvbhTen,kh.pk_seq as makh,kh.ten as tenkh," +
							"kh.diachi as diachi,dh.pk_seq as dhId,dh.NGAYNHAP AS ngayhoadon,spkm.spma as masp,sp.ten as tensp," +
							"spkm.soluong as soluong,0 as dongia,0 as chietkhau,0 as tongtien,ctkm.scheme as scheme " +						
					" from donhang_ctkm_trakm spkm " +	
							" inner join sanpham sp on spkm.spma=sp.ma" +			
							" inner join ctkhuyenmai ctkm on ctkm.pk_seq = ctkmid	" +					
							" inner join donhang dh on dh.pk_seq=donhangid			" +			
							" inner join khachhang kh on kh.pk_seq = dh.khachhang_fk " +						
							" inner join donhang_sanpham dh_sp on dh_sp.donhang_fk= dh.pk_seq	" +					
							" inner join daidienkinhdoanh ddkd on ddkd.pk_seq = dh.ddkd_fk " +						
					" where dh.ngaynhap >='" + tungay + "' and dh.ngaynhap <='" + denngay +"' and dh.trangthai = 1  " ;
					if(st.length()>0)
						query = query + st;
						query =query+" order by dhId, scheme";
		return query;
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
			String nextJSP = "/AHF/pages/Distributor/BCDonHangBanTrongKy.jsp";
			response.sendRedirect(nextJSP);
	}

	
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		Utility util = new Utility();
		Ireport obj = new Report();
		String userId = (String) session.getAttribute("userId");  
	 //   String userId= geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
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
	   	
	   	 String st=" and dh.npp_fk ='"+ nppId +"'";
	   		 if(ddkdId.length()>0)
	   			 st =" and dh.ddkd_fk ='"+ ddkdId +"'";
	   		 if(khachhangId.length()>0)
	   			 st = st +" and dh.khachhang_fk ='"+ khachhangId +"'";
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
					CreatePivotTable(out,obj,fieldsHien,query); 
		
				} catch (Exception ex) {
					request.getSession().setAttribute("errors", ex.getMessage());
				}
			 }
			 	obj.init();	    
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
				String nextJSP = "/AHF/pages/Distributor/BCDonHangBanTrongKy.jsp";
				response.sendRedirect(nextJSP);
	}

	private void CreatePivotTable(OutputStream out,Ireport obj,String [] manghien, String query) throws Exception {
		try {
			Workbook workbook = new Workbook();
			
			CreateHeader(workbook,obj); 
			FillData(workbook,manghien,query); 
			
			
			workbook.save(out, 0);
		} catch (Exception ex) {
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}

	private void CreateHeader(Workbook workbook,Ireport obj) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);	    
	    worksheet.setName("BCDonHangBanTrongKyNPP");
	    Cells cells = worksheet.getCells();	 
	    
	
	
	    cells.setRowHeight(0, 50.0f);	    
	    Cell cell = cells.getCell("A1");	
	    ReportAPI.getCellStyle(cell,Color.RED, true, 16, "Báo cáo đơn hàng bán trong kỳ");
	    cell = cells.getCell("A2");
	    ReportAPI.getCellStyle(cell,Color.BLUE,true,10,"From : " + obj.gettungay() + " To : " + obj.getdenngay());
	    cell = cells.getCell("A3");
	    ReportAPI.getCellStyle(cell,Color.BLUE,true,10,"Date Create : " +obj.getDateTime()); 
	    cell = cells.getCell("A4");
	    ReportAPI.getCellStyle(cell,Color.BLUE,true,10,"User : " + obj.getuserTen());
	    
	    
	    
	    cell = cells.getCell("AA1");		cell.setValue("Ngày");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AB1");		cell.setValue("Nhân viên bán hàng");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AC1");		cell.setValue("Tên khách hàng");		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AD1");		cell.setValue("Đơn hàng");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AE1");		cell.setValue("Ngày hóa đơn");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AF1");		cell.setValue("Tên sản phẩm");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AG1");		cell.setValue("CTKM");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AH1");		cell.setValue("Mã khách hàng");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AI1");		cell.setValue("SKU");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AJ1");		cell.setValue("Địa chỉ");					ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AK1");		cell.setValue("Chiết khấu");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AL1");		cell.setValue("Số lượng");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AM1");		cell.setValue("Đơn giá");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AN1");		cell.setValue("Tổng tiền");				ReportAPI.setCellHeader(cell);
	    
	    
	}
	private void FillData(Workbook workbook,String[] manghien, String query) throws Exception
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		
		for(int i=0;i<13;++i)
		{
	    	cells.setColumnWidth(i, 15.0f);	    	
	    }	
		dbutils db = new dbutils();
		ResultSet rs = db.get(query);
		int index = 2;
	    Cell cell = null;	    
		while (rs.next())
		{
			cell = cells.getCell("AA" + String.valueOf(index));		cell.setValue(rs.getString("ngay"));			
			cell = cells.getCell("AB" + String.valueOf(index));		cell.setValue(rs.getString("nvbhTen"));
			cell = cells.getCell("AC" + String.valueOf(index));		cell.setValue(rs.getString("tenkh"));
			cell = cells.getCell("AD" + String.valueOf(index));		cell.setValue(rs.getString("dhId"));
			cell = cells.getCell("AE" + String.valueOf(index));		cell.setValue(rs.getString("ngayhoadon"));
			cell = cells.getCell("AF" + String.valueOf(index));		cell.setValue(rs.getString("tensanpham"));
			cell = cells.getCell("AG" + String.valueOf(index));		cell.setValue(rs.getString("scheme"));
			cell = cells.getCell("AH" + String.valueOf(index));		cell.setValue(rs.getString("makh"));
			cell = cells.getCell("AI" + String.valueOf(index));		cell.setValue(rs.getString("masp"));
			cell = cells.getCell("AJ" + String.valueOf(index));		cell.setValue(rs.getString("diachi"));
			cell = cells.getCell("AK" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("chietkhau")));
			cell = cells.getCell("AL" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("soluong")));
			cell = cells.getCell("AM" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("dongia")));	    	 
			cell = cells.getCell("AN" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("tongtien")));
			index++;
		}
		ReportAPI.setHidden(workbook,20);
		PivotTables pivotTables = worksheet.getPivotTables();
		String pos = Integer.toString(index-1);	
	    int j = pivotTables.add("=BCDonHangBanTrongKyNPP!AA1:AN" + pos,"B12","PivotTable");
	   
	    PivotTable pivotTable = pivotTables.get(j);
	    pivotTable.setRowGrand(true);
	    pivotTable.setColumnGrand(true);
	    pivotTable.setAutoFormat(true);   
	    
	    Hashtable<String, Integer> selected = new Hashtable<String, Integer>();
	    selected.put("Ngay",0);
	    selected.put("Nhan_vien_ban_hang",1);
	    selected.put("Ten_khach_hang",2);
	    selected.put("So_hoa_don",4);
	    selected.put("SKU",5);
	    selected.put("CTKM",6);
	    selected.put("Ten_hang",4);
	    selected.put("Ma_khach_hang",7);
	    selected.put("Dia_chi",9);
	    selected.put("Chiet_khau",10);
		selected.put("So_luong",11);
	    selected.put("Don_gia",12);
	    selected.put("Tong_tien",13);
	    int dem =0;
	     for(int i=0;i<manghien.length ;i++)
			   {
			    	
			    	int so = selected.get(manghien[i]);
			    	System.out.println("so:"+ so +" mang:"+ manghien[i]);
			   	if(so == 10 || so == 11 || so == 12 || so == 13)
			    	{
			    		pivotTable.addFieldToArea(PivotFieldType.DATA, so);	
			    			
			    	}
			    	else
			    	pivotTable.addFieldToArea(PivotFieldType.ROW, so); 		
		   	  
			    }
	     if(dem >1)
	 	    pivotTable.addFieldToArea(PivotFieldType.COLUMN, pivotTable.getDataField());
	}


}

