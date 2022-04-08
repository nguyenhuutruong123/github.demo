package geso.dms.center.servlets.reports;

import geso.dms.center.db.sql.dbutils;

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

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Font;
import com.aspose.cells.PivotFieldDataDisplayFormat;
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTables;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class Oppgrowthordersonroutenpp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Oppgrowthordersonroutenpp() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OutputStream out = response.getOutputStream();
	    try
	    {
	    	response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "attachment; filename=OpportunitiesGrowthOrdersOnRoutett.xls");
	        
	        CreatePivotTable(out);
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	private void CreatePivotTable(OutputStream out) throws IOException
    {    //khoi tao de viet pivottable
		//buoc 1
	     Workbook workbook = new Workbook();
	     //Buoc2 tao khung
	    //ham tao khu du lieu
	     CreateStaticHeader(workbook, "12-11-2011", "30-10-2011", "Nguyen Duy Hai");
	     //Buoc3 
	     // day du lieu vao
	     CreateStaticData(workbook);
	
	     //Saving the Excel file
	     workbook.save(out);
   }
	
	private void CreateStaticHeader(Workbook workbook, String dateFrom, String dateTo, String UserName) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	   
	    
	    
	   	Style style;
	    //cells.setColumnWidth(0, 200.0f);
	    cells.setRowHeight(0, 20.0f);
	    Cell cell = cells.getCell("B1");  
	    cell.setValue("Opportunities Growth Orders on Route");   	
	    
	  
	    style = cell.getStyle();
	   
	   Font font2 = new Font();
       font2.setColor(Color.RED);//mau chu
       font2.setSize(16);// size chu
       style.setFont(font2); 
       style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu       
       cell.setStyle(style);
       
	    cell = cells.getCell("A2"); getCellStyle(workbook,"A2",Color.BLUE,true,10);
       
	    cell.setValue("From " + dateFrom + "      To " + dateTo);    
	    cell = cells.getCell("A3");getCellStyle(workbook,"A3",Color.BLUE,true,10);
	    cell.setValue("Reporting Date: " + this.getDateTime());
	    cell = cells.getCell("A4");getCellStyle(workbook,"A4",Color.BLUE,true,10);
	    cell.setValue("Created by:  " + UserName);
	    

	    

	  

	  
	  
	    //tieu de, hang dau tien cua bang du lieu, cell la toa do cua exel
	    cell = cells.getCell("AA1"); cell.setValue("Sales Rep"); 
	    cell = cells.getCell("AB1"); cell.setValue("Customer");
	    cell = cells.getCell("AC1"); cell.setValue("Address");
	    cell = cells.getCell("AD1"); cell.setValue("Monthly Average Oder");
	    cell = cells.getCell("AE1"); cell.setValue("MTD");
	    cell = cells.getCell("AF1"); cell.setValue("%");  
	    cell = cells.getCell("AG1"); cell.setValue("Route");  
	   worksheet.setName("Opp Growth Orders on Route");
	}
	private void CreateStaticData(Workbook workbook) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    
	    
		dbutils db = new dbutils();
		String sql = "select kbh.ten as Channel,v.ten as Region,kv.ten as Area,npp.ten as Distributor,";
		sql = sql + " gsbh.ten as Sales_Rep,kh.ten as Customer,tbh.ngaylamviec as Route,substring(convert(nvarchar(20),b.tong),1,5) as Monthly_Avg_Order, 1 as MTD,y.ten as Province,";
		sql = sql + " case when b.tong >0 then 100/b.tong else 1 end as '%', npp.diachi as Adress,dh.npp_fk as Distributor_code from donhang dh";                        
		sql = sql + " inner join (select npp_fk,gsbh_fk,khachhang_fk,kbh_fk,cast(count(*) as float)/3 as tong from donhang where trangthai ='1' and ngaynhap >='2011-09-01' and ngaynhap <='2011-11-30' group by npp_fk,gsbh_fk,khachhang_fk,kbh_fk) b";
		sql = sql + " on dh.npp_fk = b.npp_fk and dh.khachhang_fk =b.khachhang_fk and dh.gsbh_fk = b.gsbh_fk and dh.kbh_fk = b.kbh_fk";
		sql = sql + " inner join kenhbanhang kbh on kbh.pk_seq = dh.kbh_fk";
		sql = sql + " inner join nhaphanphoi npp on npp.pk_seq = dh.npp_fk";
		sql = sql + " inner join khuvuc kv on kv.pk_seq = npp.khuvuc_fk";
		sql = sql + " inner join vung v on v.pk_seq = kv.vung_fk";
		sql = sql + " inner join giamsatbanhang gsbh on gsbh.pk_seq = dh.gsbh_fk";
		sql = sql + " inner join khachhang kh on kh.pk_seq = dh.khachhang_fk";
		sql = sql + " left join KHACHHANG_TUYENBH khtbh on khtbh.khachhang_fk = dh.khachhang_fk";
		sql = sql + " left join tuyenbanhang tbh on tbh.pk_seq = khtbh.tbh_fk and tbh.ddkd_fk = dh.ddkd_fk and tbh.npp_fk = dh.npp_fk";
		sql = sql + " left join quanhuyen y on y.pk_seq = npp.quanhuyen_fk";
		sql = sql + " where year(dh.ngaynhap) = 2011 and month(dh.ngaynhap) = 11 and dh.trangthai ='1'";


		ResultSet rs = db.get(sql);
		System.out.print(sql);
		int i = 2;
		if(i>0)
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
				
				
				//set do rong cho dong
				//for(int j = 12; j <= 11; j++)
					//cells.setRowHeight(j, 14.0f);
				
				Cell cell = null;
				while(rs.next())// lap den cuoi bang du lieu
				{ 
				
					//lay tu co so du lieu, gan bien
					String SalesRep =rs.getString("Sales_Rep");
					String Customer =rs.getString("Customer");
					String Address =rs.getString("Adress");
					String MonthlyAvgOrder =rs.getString("Monthly_Avg_Order");
					String MTD = rs.getString("MTD");				
					String Route = rs.getString("Route");
					
					
					
					
					
					
					cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(SalesRep);
					cell = cells.getCell("AB" + Integer.toString(i)); cell.setValue(Customer);
					cell = cells.getCell("AC" + Integer.toString(i)); cell.setValue(Address);
					cell = cells.getCell("AD" + Integer.toString(i)); cell.setValue(Float.parseFloat(MonthlyAvgOrder));
					cell = cells.getCell("AE" + Integer.toString(i)); cell.setValue(Float.parseFloat(MTD));								
					cell = cells.getCell("AF" + Integer.toString(i)); cell.setValue(Float.parseFloat(MTD)/Float.parseFloat(MonthlyAvgOrder) * 100);
					cell = cells.getCell("AG" + Integer.toString(i)); cell.setValue(Route);
				
					
					i++;
				}
			}
			catch (Exception e){ e.printStackTrace(); }
		}
		//xong buoc dua du lieu vao exel
		
		
		
	    
		
		//create pivot
		 //getAn(workbook,49);
		PivotTables pivotTables = worksheet.getPivotTables();

	    //Adding a PivotTable to the worksheet
		String pos = Integer.toString(i-1);	//pos la dong cuoi cung	,A12 la toa do dau cua banng du lieu, Q pos la dong cuoi
	    int index = pivotTables.add("=AA1:AG" + pos,"A12","PivotTableDemo");
         System.out.println("index:"+index);
	    //Accessing the instance of the newly added PivotTable
	    PivotTable pivotTable = pivotTables.get(index);//truyen index

	    pivotTable.setRowGrand(false);
	    pivotTable.setColumnGrand(true);
	    pivotTable.setAutoFormat(true);
	    //Setting the PivotTable autoformat type.
	    //pivotTable.setAutoFormatType(PivotTableAutoFormatType.REPORT6);
 
	   
	  
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 0);	 	pivotTable.getRowFields().get(0).setAutoSubtotals(false); 
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 1);		pivotTable.getRowFields().get(1).setAutoSubtotals(false); 
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 2);		pivotTable.getRowFields().get(2).setAutoSubtotals(false); 
	   
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 3);		pivotTable.getDataFields().get(0).setDisplayName("Monthly Avg.Order");
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 4);		pivotTable.getDataFields().get(1).setDisplayName("MTD");
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 5);  	pivotTable.getDataFields().get(2).setDisplayName("%");
	    pivotTable.getDataFields().get(2).setDataDisplayFormat(PivotFieldDataDisplayFormat.PERCENTAGE_OF_COLUMN);
	    pivotTable.addFieldToArea(PivotFieldType.COLUMN, pivotTable.getDataField());
	  
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
