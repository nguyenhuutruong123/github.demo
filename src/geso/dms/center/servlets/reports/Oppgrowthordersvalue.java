package geso.dms.center.servlets.reports;

import geso.dms.center.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Font;
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTables;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class Oppgrowthordersvalue extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 	
	Utility util=new Utility();
    String tungay="";
	String denngay="";
	String userTen="";
	boolean bfasle=true; //Bien nay khai bao de khi chuong trinh bi loi thi khong xuat ra pivot
	
    public Oppgrowthordersvalue() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/*		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	//	PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String userTen = (String)session.getAttribute("userTen");
		String querystring=request.getQueryString();
		String userId=	util.getUserId(querystring);
		session.setAttribute("tungay", "");
 		session.setAttribute("denngay","");
    	 session.setAttribute("loi", "");
		session.setAttribute("userTen", userTen);
		String nextJSP = "/AHF/pages/Center/OpportunitiesGrowthOrdersValue.jsp";
		response.sendRedirect(nextJSP);*/
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OutputStream out = response.getOutputStream(); 
		try
		    {
			HttpSession session = request.getSession();
			userTen = (String)session.getAttribute("userTen");
			tungay=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
			denngay=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
	
	    	response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "attachment; filename=CoHoiTangGiaTriDonHang.xls");
	        CreatePivotTable(out,response,request);
	     }
	    catch (Exception ex)
	    {
	        ex.printStackTrace();
	
	        // say sorry
	        response.setContentType("text/html");
	        PrintWriter writer = new PrintWriter(out);
	
	        writer.println("<html>");
	        writer.println("<head>");
	        writer.println("<title>sorry</title>");
	        writer.println("</head>");
	        writer.println("<body>");
	        writer.println("<h1>Xin loi, khong the tao pivot table...</h1>");
	        ex.printStackTrace(writer);
	        writer.println("</body>");
	        writer.println("</html>");
	        writer.close();
	    }
	}
	private void CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request) throws IOException
    {    //khoi tao de viet pivottable
		//buoc 1
	     Workbook workbook = new Workbook();
	     //Buoc2 tao khung
	    //ham tao khu du lieu
	     CreateStaticHeader(workbook, tungay,denngay, userTen);
	     //Buoc3 
	     // day du lieu vao
	     CreateStaticData(workbook);
	     if(bfasle==false){
	    	String loi="Chưa có dữ liệu trong thời gian này, voi lòng chọn lại thời gian xem báo cáo";
	    	
	    	HttpSession session = request.getSession();
	 		session.setAttribute("loi", loi);
	 		session.setAttribute("tungay", tungay);
	 		session.setAttribute("denngay", denngay);
	    	session.setAttribute("userTen", userTen);
	 		String nextJSP = "/AHF/pages/Center/OpportunitiesGrowthOrdersValue.jsp";
	 		response.sendRedirect(nextJSP);
	     }else{
	    	 //Saving the Excel file
	    	 workbook.save(out);
	     }
   }
	
	private void CreateStaticHeader(Workbook workbook, String dateFrom, String dateTo, String UserName) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	   
	    
	    
	   	Style style;
	    //cells.setColumnWidth(0, 200.0f);
	    cells.setRowHeight(0, 20.0f);
	    Cell cell = cells.getCell("A1");  
	    cell.setValue("Opportunities to Increase Orders' Value");   	
	    style = cell.getStyle();
		   
	    Font font2 = new Font();
	    font2.setColor(Color.RED);//mau chu
	    font2.setSize(16);// size chu
	    font2.setBold(true);
	    style.setFont(font2); 
       
	    style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu       
	    cell.setStyle(style);
       
	    cell = cells.getCell("A3"); getCellStyle(workbook,"A3",Color.BLUE,true,10);
       
/*	    cell.setValue("From " + dateFrom + "      To " + dateTo);    
	    cell = cells.getCell("B3");getCellStyle(workbook,"B3",Color.BLUE,true,10);*/
	    
	    cell.setValue("Reporting Date: " + this.getdate());
	    cell = cells.getCell("A4");getCellStyle(workbook,"A4",Color.BLUE,true,10);
	    cell.setValue("Created by:  " + UserName);
  
	  
	    //tieu de, hang dau tien cua bang du lieu, cell la toa do cua exel
	    cell = cells.getCell("AA1"); cell.setValue("Channel"); 
	    cell = cells.getCell("AB1"); cell.setValue("Region");
	    cell = cells.getCell("AC1"); cell.setValue("Area");
	    cell = cells.getCell("AD1"); cell.setValue("Sales Sup");
	    cell = cells.getCell("AE1"); cell.setValue("Distributor");
	    cell = cells.getCell("AF1"); cell.setValue("Sales Rep");	  
	    cell = cells.getCell("AG1"); cell.setValue("Route");
	    cell = cells.getCell("AH1"); cell.setValue("Customer");
	    cell = cells.getCell("AI1"); cell.setValue("(3 Month) Avg. Value Per Order");
	    cell = cells.getCell("AJ1"); cell.setValue("(MTD) Avg. Value Per Order");
	    cell = cells.getCell("AK1"); cell.setValue("Opportunities");
	 
	   
	    worksheet.setName("CoHoiTangGiaTriDonHangTrenTuyen");
	}
	private void CreateStaticData(Workbook workbook) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    
	    //khoi tao ket noi csdl
		dbutils db = new dbutils();
		String sql = "select kbh.ten as channel,v.ten as Region ,kv.ten as Area ,gsbh.ten as Sales_Sup,dh.npp_fk as Distributor_code,npp.ten as Distributor,ddkd.ten as Sales_Rep,kh.ten as Customer,tbh.ngaylamviec as Route, qh.ten as Province," + 
					 " case when avgoforder.giatri > 0 then cast(avgds.doanhso/avgoforder.giatri as Integer) else 0 end as 'AvgValuePerOrder', case when mtdnumoforder.giatri > 0 then cast(mtd.doanhso/mtdnumoforder.giatri as Integer) else 0 end as 'MTDSecondarySales'," + 
					 " case when (avgoforder.giatri > 0 and avgds.doanhso/avgoforder.giatri > mtd.doanhso/mtdnumoforder.giatri) then cast((avgds.doanhso/avgoforder.giatri-mtd.doanhso/mtdnumoforder.giatri)/(avgds.doanhso/avgoforder.giatri) as Integer) else 0 end as 'Opportunities'" + 
					 " from donhang dh" + 
					 " inner join (select npp_fk,kbh_fk,gsbh_fk,ddkd_fk, khachhang_fk, sum(tonggiatri) as doanhso from donhang where trangthai > 1 and ngaynhap >='2011-09-01' and ngaynhap <= '2011-12-31' group by npp_fk,kbh_fk,gsbh_fk,ddkd_fk, khachhang_fk) avgds " + 
					 " on avgds.npp_fk = dh.npp_fk and avgds.kbh_fk = dh.kbh_fk and avgds.ddkd_fk = dh.ddkd_fk and avgds.gsbh_fk = dh.gsbh_fk and avgds.khachhang_fk=dh.khachhang_fk " +
					 " left join (select npp_fk,kbh_fk,gsbh_fk,ddkd_fk, khachhang_fk, sum(tonggiatri) as doanhso from donhang where trangthai > 1 and ngaynhap >='2011-12-01' and ngaynhap <= '2011-12-31' group by npp_fk,kbh_fk,gsbh_fk,ddkd_fk, khachhang_fk) mtd " + 
					 " on mtd.npp_fk = dh.npp_fk and mtd.kbh_fk = dh.kbh_fk and mtd.ddkd_fk = dh.ddkd_fk and mtd.gsbh_fk = dh.gsbh_fk  and mtd.khachhang_fk=dh.khachhang_fk " +
					 " inner join (select npp_fk,kbh_fk,gsbh_fk,ddkd_fk, khachhang_fk, count(pk_seq) as giatri from donhang where trangthai > 1 and ngaynhap >='2011-12-01' and ngaynhap <= '2011-12-31' group by npp_fk,kbh_fk,gsbh_fk,ddkd_fk, khachhang_fk) mtdnumoforder " + 
					 " on mtdnumoforder.npp_fk = dh.npp_fk and mtdnumoforder.kbh_fk = dh.kbh_fk and mtdnumoforder.ddkd_fk = dh.ddkd_fk and mtdnumoforder.gsbh_fk = dh.gsbh_fk  and mtdnumoforder.khachhang_fk=dh.khachhang_fk " +
					 " inner join (select npp_fk,kbh_fk,gsbh_fk,ddkd_fk, khachhang_fk, count(pk_seq) as giatri from donhang where trangthai > 1 and ngaynhap >='2011-09-01' and ngaynhap <= '2011-12-31' group by npp_fk,kbh_fk,gsbh_fk,ddkd_fk, khachhang_fk) avgoforder " + 
					 " on avgoforder.npp_fk = dh.npp_fk and avgoforder.kbh_fk = dh.kbh_fk and avgoforder.ddkd_fk = dh.ddkd_fk and avgoforder.gsbh_fk = dh.gsbh_fk  and avgoforder.khachhang_fk=dh.khachhang_fk " + 
					 " inner join kenhbanhang kbh on kbh.pk_seq = dh.kbh_fk " +
					 " inner join nhaphanphoi npp on npp.pk_seq = dh.npp_fk " +
					 " left join khuvuc kv on kv.pk_seq = npp.khuvuc_fk " +  
					 " left join vung v on v.pk_seq = kv.vung_fk " +
					 " inner join giamsatbanhang gsbh on gsbh.pk_seq = dh.gsbh_fk " +
					 " inner join daidienkinhdoanh ddkd on ddkd.pk_seq = dh.ddkd_fk " + 
					 " inner join khachhang kh on kh.pk_seq = dh.khachhang_fk" + 
					 " left join quanhuyen qh on qh.pk_seq = npp.quanhuyen_fk" + 
					 " left join KHACHHANG_TUYENBH kh_tbh on kh_tbh.KHACHHANG_FK = dh.khachhang_fk" + 
					 " left join tuyenbanhang tbh on tbh.pk_Seq = kh_tbh.TBH_FK and tbh.ddkd_fk = dh.ddkd_fk"; 

		System.out.print(sql);
	  	ResultSet rs = db.get(sql);
	
		int i = 2;
		if(rs != null){
			
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
//				cells.setColumnWidth(11, 15.0f);
//				cells.setColumnWidth(12, 15.0f);
//				cells.setColumnWidth(13, 15.0f);
//				cells.setColumnWidth(14, 15.0f);
				//set do rong cho dong
				//for(int j = 12; j <= 11; j++)
					//cells.setRowHeight(j, 14.0f);
				
				Cell cell = null;
				while(rs.next())// lap den cuoi bang du lieu
				{ 
				
					//lay tu co so du lieu, gan bien
					
					String Channel =rs.getString("Channel");
					String Region =rs.getString("Region");
					String Area =rs.getString("Area");					
					String SalesSup =rs.getString("Sales_Sup");
					String Distributor =rs.getString("Distributor");
					
					String SalesRep =rs.getString("Sales_Rep");
					String Route =rs.getString("Route");
					String Customer =rs.getString("Customer");
					
					String AvgValuePerOrder = rs.getString("AvgValuePerOrder");
					String MTDSecondarySales = rs.getString("MTDSecondarySales");				
					String Opportunitiess =rs.getString("Opportunities");
				
					cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(Channel);
					cell = cells.getCell("AB" + Integer.toString(i)); cell.setValue(Region);
					cell = cells.getCell("AC" + Integer.toString(i)); cell.setValue(Area);
					cell = cells.getCell("AD" + Integer.toString(i)); cell.setValue(SalesSup);
					cell = cells.getCell("AE" + Integer.toString(i)); cell.setValue(Distributor);				
					cell = cells.getCell("AF" + Integer.toString(i)); cell.setValue(SalesRep);
					cell = cells.getCell("AG" + Integer.toString(i)); cell.setValue(Route);
					cell = cells.getCell("AH" + Integer.toString(i)); cell.setValue(Customer);					
					cell = cells.getCell("AI" + Integer.toString(i)); cell.setValue(Float.parseFloat(AvgValuePerOrder));
					cell = cells.getCell("AJ" + Integer.toString(i)); cell.setValue(Float.parseFloat(MTDSecondarySales));
					cell = cells.getCell("AK" + Integer.toString(i)); cell.setValue(Float.parseFloat(Opportunitiess));
					
					i++;
				}
			
		
		//xong buoc dua du lieu vao exel
		
		
		
	    
		
		//create pivot
		 getAn(workbook,49);
		PivotTables pivotTables = worksheet.getPivotTables();

	    //Adding a PivotTable to the worksheet
		String pos = Integer.toString(i-1);	//pos la dong cuoi cung	,A12 la toa do dau cua banng du lieu, Q pos la dong cuoi
	    int index = pivotTables.add("=AA1:AK" + pos,"A8","PivotTableDemo");
         System.out.println("index:"+index);
	    //Accessing the instance of the newly added PivotTable
	    PivotTable pivotTable = pivotTables.get(index);//truyen index

	    pivotTable.setRowGrand(false);
	    pivotTable.setColumnGrand(true);
	    pivotTable.setAutoFormat(true);
	    //Setting the PivotTable autoformat type.
	    //pivotTable.setAutoFormatType(PivotTableAutoFormatType.REPORT6);
 
	   
	  
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 0);	pivotTable.getRowFields().get(0).setAutoSubtotals(false); 
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 1);	pivotTable.getRowFields().get(1).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 2);	pivotTable.getRowFields().get(2).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 3);	pivotTable.getRowFields().get(3).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 4);	pivotTable.getRowFields().get(4).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 5);   pivotTable.getRowFields().get(5).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 6);	pivotTable.getRowFields().get(6).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 7);	pivotTable.getRowFields().get(7).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 8);	pivotTable.getRowFields().get(8).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 9);	pivotTable.getRowFields().get(9).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 10);	pivotTable.getRowFields().get(10).setAutoSubtotals(false);
//	    pivotTable.addFieldToArea(PivotFieldType.ROW, 4);
//	    pivotTable.addFieldToArea(PivotFieldType.COLUMN, pivotTable.getDataField());
	    bfasle=true;
		}
		catch (Exception e){
			bfasle=false;
			e.printStackTrace(); 
			System.out.println("Error : Opportunities Growth Orders Value : "+e.toString());
		}
	}else{
		bfasle=false;
		}
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
	private String getdate() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}
