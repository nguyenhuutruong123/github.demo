package geso.dms.center.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.pivotTest;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.Format.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Font;
//import com.aspose.cells.PivotField;
//import com.aspose.cells.PivotField;
//import com.aspose.cells.PivotFieldDataDisplayFormat;
import com.aspose.cells.PivotFieldType;
//import com.aspose.cells.PivotFields;
import com.aspose.cells.PivotField;
import com.aspose.cells.PivotFieldDataDisplayFormat;
import com.aspose.cells.PivotFieldSubtotalType;
import com.aspose.cells.PivotFields;
import com.aspose.cells.PivotItemPosition;
import com.aspose.cells.PivotPageFields;
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTableAutoFormatType;
import com.aspose.cells.PivotTables;

import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class Dailysecondarysalesnpp extends HttpServlet {
	
	public Dailysecondarysalesnpp() {
		super();

	}
       
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		IStockintransit obj = new Stockintransit();

		HttpSession session = request.getSession();
		String userTen = (String) session.getAttribute("userTen");
		String querystring = request.getQueryString();
		Utility util = new Utility();
		String userId = util.getUserId(querystring);

		obj.setuserTen(userTen);
		obj.setuserId(userId);
		obj.init();
		session.setAttribute("obj", obj);
		session.setAttribute("util", util);
		String nextJSP = "/AHF/pages/Center/DailySecondarySalesnpp.jsp";
		response.sendRedirect(nextJSP);
	}
    	
    	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			OutputStream out = response.getOutputStream();
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			String nextJSP = "/AHF/pages/Center/DailySecondarySalesnpp.jsp";
			HttpSession session = request.getSession();
			IStockintransit obj = new Stockintransit();
			Utility util = new Utility();
			try {
	
				obj.setuserTen((String) session.getAttribute("userTen") != null ? (String) session
						.getAttribute("userTen") : "");
	
				obj.settungay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"))) != null ? request
						.getParameter("tungay") : "");
	
				obj.setdenngay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"))) != null ? request
						.getParameter("denngay") : "");
				obj.setuserId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"))) != null ? request
						.getParameter("userId") : "");
	
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition",
						"attachment; filename=DailySecondarySales.xls");
	
				boolean isTrue = CreatePivotTable(out, obj);
				if (!isTrue) {
					PrintWriter writer = new PrintWriter(out);
					writer.println("Xin loi: Khong co bao cao trong thoi gian nay");
					writer.close();
				}
			} catch (Exception ex) {
				obj.setMsg(ex.getMessage());
				response.sendRedirect(nextJSP);
			}
			obj.init();
			session.setAttribute("obj", obj);
			session.setAttribute("util", util);
    	}
    	
    	private boolean CreatePivotTable(OutputStream out,IStockintransit obj) throws Exception
        {      
    		
			Workbook workbook = new Workbook();
			CreateStaticHeader(workbook,obj);			
			boolean isTrue = CreateStaticData(workbook, obj);
			if(!isTrue)
				return false;
			workbook.save(out);
			return true;
       }
    	
    	private void CreateStaticHeader(Workbook workbook,IStockintransit obj) throws Exception
    	{
    		Worksheets worksheets = workbook.getWorksheets();
    	    Worksheet worksheet = worksheets.getSheet(0);
    	   	   
    	    Cells cells = worksheet.getCells();
    	   
    	    
    	    
    	   	Style style;
    	    //cells.setColumnWidth(0, 200.0f);
    	    cells.setRowHeight(0, 20.0f);
    	    Cell cell = cells.getCell("A1");  
    	    cell.setValue("Daily Secondary Sales Report");   	
    	    
    	  
    	    style = cell.getStyle();
    	   
    	   Font font2 = new Font();
    	   font2.setColor(Color.RED);//mau chu
    	   font2.setSize(16);// size chu
    	   style.setFont(font2); 
    	   style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu       
    	   cell.setStyle(style);
    	   
    	    cell = cells.getCell("A2"); getCellStyle(workbook,"A2",Color.BLUE,true,10);
    	   
    	    cell.setValue("From " + obj.gettungay() + "      To " + obj.getdenngay());    
    	    cell = cells.getCell("A3");getCellStyle(workbook,"A3",Color.BLUE,true,10);
    	    cell.setValue("Date Create: " + this.getDateTime());
    	    cell = cells.getCell("A4");getCellStyle(workbook,"A4",Color.BLUE,true,10);
    	    cell.setValue("User:  " + obj.getuserTen());
    	    

    	    

    	  

    	  
    	  
    	    //tieu de, hang dau tien cua bang du lieu, cell la toa do cua exel
    	   cell = cells.getCell("AA1"); cell.setValue("Channel"); 
    	   cell = cells.getCell("AB1"); cell.setValue("Sales Rep");
    	   cell = cells.getCell("AC1"); cell.setValue("Brands");
    	   cell = cells.getCell("AD1"); cell.setValue("Categoty");
    	   cell = cells.getCell("AE1"); cell.setValue("Amount");
    	   cell = cells.getCell("AF1"); cell.setValue("SKU");
    	   cell = cells.getCell("AG1"); cell.setValue("SKU Code");
    	   cell = cells.getCell("AH1"); cell.setValue("piece");
    	   cell = cells.getCell("AI1"); cell.setValue("Customer");
    	   cell = cells.getCell("AJ1"); cell.setValue("Outlet Class");
    	   cell = cells.getCell("AK1"); cell.setValue("Outlet Type");
    	   cell = cells.getCell("AL1"); cell.setValue("Off Date");
    	   cell = cells.getCell("AM1"); cell.setValue("Quantity");
    	   cell = cells.getCell("AN1"); cell.setValue("Group SKU");
    	   cell = cells.getCell("AO1"); cell.setValue("Group Customer");
    	   
    	    
    	 
    	   
    	    worksheet.setName("Daily Secondary Sales Report(npp)");
    	}
    	
		private boolean CreateStaticData(Workbook workbook,IStockintransit obj) throws Exception
    	{
			dbutils db = new dbutils();
    		Worksheets worksheets = workbook.getWorksheets();
    	    Worksheet worksheet = worksheets.getSheet(0);
    	    Cells cells = worksheet.getCells();    	    
    		 Utility utili = new Utility();    		 
    	 
    	 String sql ="select  v.ten as region, kv.ten as area, npp.ten as distributor, npp.sitecode as distcode, nh.ten as brand,cl.ten as category, sp.ma + '_' + sp.ten as SKU, "+ 
    	 		" sp.ma as SKUcode, ddkd.ten as SalesRep, npp.sitecode + '_' + kh.ten + '( ' + kh.diachi +  ' )' as customer, vtch.vitri as outletposition, "+
    	 		" isnull(m.diengiai, 'Chua Xac Dinh') as outlettype, kbh.diengiai as chanel, dh.ngaynhap as offdate, dhsp.soluong as piece, dhsp.giamua*dhsp.soluong*1.1 as amount,"+  
    	 		" isnull(gsbh.ten, 'Chua xac dinh') as salesuper,"+
    	 		" left((cast((qc.soluong2 * dhsp.soluong) as float) / cast(qc.soluong1 as float)),5) as quantity,"+
    	 		" isnull(qh.ten, 'Chua xac dinh') as town, "+
    	 		" isnull(tt.ten, 'Chua xac dinh') as province,"+ 
    	 		" hch.diengiai as OutletClass, nkh.diengiai as nhomkhachhang, nsp.diengiai as nhomsanpham"+
    	 		" from donhang dh inner join donhang_sanpham dhsp on dh.pk_seq = dhsp.donhang_fk inner join sanpham sp on dhsp.sanpham_fk = sp.pk_seq"+  
    	 		" inner join nhaphanphoi npp on dh.npp_fk = npp.pk_seq inner join khuvuc kv on npp.khuvuc_fk = kv.pk_seq  "+
    	 		" inner join vung v on kv.vung_fk = v.pk_seq inner join nhanhang nh on sp.nhanhang_fk = nh.pk_seq inner join chungloai cl on sp.chungloai_fk = cl.pk_seq"+  
    	 		" inner join daidienkinhdoanh ddkd on dh.ddkd_fk = ddkd.pk_seq inner join khachhang kh on dh.khachhang_fk = kh.pk_seq "+
    	 		" inner join vitricuahang vtch on kh.vtch_fk = vtch.pk_seq inner join kenhbanhang kbh on kh.kbh_fk = kbh.pk_seq left join loaicuahang m on kh.lch_fk = m.pk_seq"+  
    	 		" left join giamsatbanhang gsbh on dh.gsbh_fk = gsbh.pk_seq inner join quycach qc on qc.sanpham_fk = sp.pk_seq "+
    	 		" left join quanhuyen qh on kh.quanhuyen_fk = qh.pk_seq left join tinhthanh tt on kh.tinhthanh_fk = tt.pk_seq inner join hangcuahang hch on kh.hch_fk = hch.pk_seq"+ 
    	 		" left join nhomkhachhang_khachhang nkhkh on nkhkh.kh_fk = kh.pk_seq"+
    	 		" left join nhomkhachhang nkh on nkh.pk_seq = nkhkh.nkh_fk"+
    	 		" left join (select sp_fk, max(nsp_fk) as nhomsp from nhomsanpham_sanpham group by sp_fk) nspsp on nspsp.sp_fk = sp.pk_seq"+
    	 		" left join nhomsanpham nsp on nsp.pk_seq = nspsp.nhomsp "+
    	 		" where dh.trangthai = '1' and dh.npp_fk ='"+utili.getIdNhapp(obj.getuserId())+
    	 			"' and dh.ngaynhap >= '"+obj.gettungay()+"' and dh.ngaynhap <= '"+obj.getdenngay()+"'";


    	 System.out.println("Lay doanh so NPP: " + sql + "\n");
    	
    	ResultSet rs = db.get(sql);
    		int i = 2;
    		if(rs!= null)
    		{
    			try 
    			{// se do rong cho cac cot se dung
    				cells.setColumnWidth(0, 15.0f);
    				cells.setColumnWidth(1, 15.0f);
    				cells.setColumnWidth(2, 15.0f);
    				cells.setColumnWidth(3, 15.0f);
    				cells.setColumnWidth(4, 15.0f);
    				cells.setColumnWidth(5, 15.0f);
    				cells.setColumnWidth(6, 15.0f);
    				cells.setColumnWidth(7, 15.0f);
    				cells.setColumnWidth(8, 15.0f);
    				cells.setColumnWidth(9, 15.0f);
    				cells.setColumnWidth(10, 15.0f);
    				cells.setColumnWidth(11, 15.0f);
    				cells.setColumnWidth(12, 15.0f);
    				cells.setColumnWidth(13, 15.0f);
    				cells.setColumnWidth(14, 15.0f);
    				cells.setColumnWidth(15, 15.0f);
    				cells.setColumnWidth(16, 15.0f);
    				cells.setColumnWidth(17, 15.0f);
    				cells.setColumnWidth(18, 15.0f);
    				cells.setColumnWidth(19, 15.0f);
    				cells.setColumnWidth(20, 15.0f);
    				cells.setColumnWidth(21, 15.0f);
    				cells.setColumnWidth(22, 15.0f);
    				
    				//set do rong cho dong
    				for(int j = 12; j <= 22; j++)
    					cells.setRowHeight(j, 14.0f);
    				
    				Cell cell = null;
    				while(rs.next())// lap den cuoi bang du lieu
    				{ 
    				
    					//lay tu co so du lieu, gan bien
    					 	String Channel = rs.getString("chanel");
    					 	String SalesRep = rs.getString("SalesRep");
    					 	String Brands = rs.getString("brand");
    					 	String Category = rs.getString("category");
    					 	float Amount = Float.parseFloat(rs.getString("Amount"));						
    						if(Amount < 0) Amount = 0;
    						String Sku = rs.getString("SKU");
    						String SkuCode = rs.getString("SKUcode");
    						String Piece = rs.getString("piece");
    						String Customer = rs.getString("customer");
    						String OutletClass = rs.getString("OutletClass");
    						String OutletType = rs.getString("outlettype");
    						String OffDate = rs.getString("offdate");
    						float Quantity = Float.parseFloat(rs.getString("quantity"));
    						String GroupSku = rs.getString("nhomsanpham");
    						String GroupCustomer = rs.getString("nhomkhachhang");
    						
    						
    					
    					cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(Channel);
    					cell = cells.getCell("AB" + Integer.toString(i)); cell.setValue(SalesRep);
    					cell = cells.getCell("AC" + Integer.toString(i)); cell.setValue(Brands);
    					cell = cells.getCell("AD" + Integer.toString(i)); cell.setValue(Category);
    					cell = cells.getCell("AE" + Integer.toString(i)); cell.setValue(Amount);
    					cell = cells.getCell("AF" + Integer.toString(i)); cell.setValue(Sku);
    					cell = cells.getCell("AG" + Integer.toString(i)); cell.setValue(SkuCode);
    					cell = cells.getCell("AH" + Integer.toString(i)); cell.setValue(Float.parseFloat(Piece));
    					cell = cells.getCell("AI" + Integer.toString(i)); cell.setValue(Customer);
    					cell = cells.getCell("AJ" + Integer.toString(i)); cell.setValue(OutletClass);
    					cell = cells.getCell("AK" + Integer.toString(i)); cell.setValue(OutletType);
    					cell = cells.getCell("AL" + Integer.toString(i)); cell.setValue(OffDate);
    					cell = cells.getCell("AM" + Integer.toString(i)); cell.setValue(Quantity);
    					cell = cells.getCell("AN" + Integer.toString(i)); cell.setValue(GroupSku);
    					cell = cells.getCell("AO" + Integer.toString(i)); cell.setValue(GroupCustomer);
    				
    					i++;
    					if(i>65000){
    		    			if(rs!=null) rs.close();
    		    			if (db != null) db.shutDown();
    						throw new Exception("Du lieu vuot qua 65.000 dong. Vui long cho dieu kien cho bao cao.");
    					}
    				}
    			}
    			catch (Exception e){ 
    				
    			}
    			if(rs!=null)
    				rs.close();
    			
    			if (db != null) db.shutDown();
    			
    			if(i==2)
    				throw new Exception("Xin loi. Khong co bao cao trong thoi gian nay...");
    		}
    		
    		 getAn(workbook,49);
    			PivotTables pivotTables = worksheet.getPivotTables();

    		    //Adding a PivotTable to the worksheet
    			String pos = Integer.toString(i-1);	//pos la dong cuoi cung	,A12 la toa do dau cua banng du lieu, Q pos la dong cuoi
    		    int index = pivotTables.add("=AA1:AO" + pos,"A12","PivotTableDemo");
    		     System.out.println("index:"+index);
    		    //Accessing the instance of the newly added PivotTable
    		    PivotTable pivotTable = pivotTables.get(index);//truyen index

    		    pivotTable.setRowGrand(false);
    		    pivotTable.setColumnGrand(true);
    		    pivotTable.setAutoFormat(true);
    		  

    		   
    		    pivotTable.addFieldToArea(PivotFieldType.ROW, 0);pivotTable.getRowFields().get(0).setAutoSubtotals(false);
    		    pivotTable.addFieldToArea(PivotFieldType.ROW, 1);pivotTable.getRowFields().get(1).setAutoSubtotals(false);
    		    pivotTable.addFieldToArea(PivotFieldType.ROW, 2);pivotTable.getRowFields().get(2).setAutoSubtotals(false);
    		    pivotTable.addFieldToArea(PivotFieldType.ROW, 3);pivotTable.getRowFields().get(3).setAutoSubtotals(false);    		  
    		    pivotTable.addFieldToArea(PivotFieldType.DATA, 4); pivotTable.getDataFields().get(0).setDisplayName("Amount");
    		   return true;
   }
    	private void getCellStyle(Workbook workbook, String a, Color mau, Boolean dam, int size)
    	{
    		Worksheets worksheets = workbook.getWorksheets();
    	    Worksheet worksheet = worksheets.getSheet(0);
    	   	   
    	    Cells cells = worksheet.getCells();
    		Style style;
    		Cell cell = cells.getCell(a); 
    		 style = cell.getStyle();
    	        Font font1 = new Font();
    	        font1.setColor(mau);
    	        font1.setBold(dam);
    	        font1.setSize(size);
    	        style.setFont(font1);
    	        cell.setStyle(style);
    	}
    	private void getAn(Workbook workbook,int i)
    	{
    		Worksheets worksheets = workbook.getWorksheets();
    	    Worksheet worksheet = worksheets.getSheet(0);
    	   	   
    	    Cells cells = worksheet.getCells();
    	    for(int j = 26; j <= i; j++)
    	    { 
    	    	cells.hideColumn(j);
    	    }
    		
    	}
    	private String getDateTime() 
    	{
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
            Date date = new Date();
            return dateFormat.format(date);	
    	}
    }
