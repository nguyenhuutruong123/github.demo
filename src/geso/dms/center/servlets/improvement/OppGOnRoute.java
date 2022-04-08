package geso.dms.center.servlets.improvement;
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
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTables;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class OppGOnRoute extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public OppGOnRoute() {
        super();
    }
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
       
	    cell = cells.getCell("B2"); getCellStyle(workbook,"B2",Color.BLUE,true,10);
       
	    cell.setValue("From " + dateFrom + "      To " + dateTo);    
	    cell = cells.getCell("B3");getCellStyle(workbook,"B3",Color.BLUE,true,10);
	    cell.setValue("Date Create: " + this.getDateTime());
	    cell = cells.getCell("B4");getCellStyle(workbook,"B4",Color.BLUE,true,10);
	    cell.setValue("User:  " + UserName);
	    

	    

	  

	  
	  
	    //tieu de, hang dau tien cua bang du lieu, cell la toa do cua exel
	    cell = cells.getCell("AA1"); cell.setValue("Channel"); 
	    cell = cells.getCell("AB1"); cell.setValue("Region");
	    cell = cells.getCell("AC1"); cell.setValue("Area");
	    cell = cells.getCell("AD1"); cell.setValue("Distributor");
	    cell = cells.getCell("AE1"); cell.setValue("Distributor Code");
	    cell = cells.getCell("AF1"); cell.setValue("Sales Rep");	   
	    cell = cells.getCell("AG1"); cell.setValue("Route");
	    cell = cells.getCell("AH1"); cell.setValue("Customer");
	    cell = cells.getCell("AI1"); cell.setValue("Province");
	    cell = cells.getCell("AJ1"); cell.setValue("Monthly Avg Order");
	    cell = cells.getCell("AK1"); cell.setValue("Address");
	    cell = cells.getCell("AL1"); cell.setValue("MTD");
	    cell = cells.getCell("AM1"); cell.setValue("%");
	    
	   
	    worksheet.setName("Sales Opp On Route (Order) tt");
	}
	private void CreateStaticData(Workbook workbook) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    dbutils db = new dbutils();
	   String ngaynhap ="2011-12-02";
	   String ngaynhap1 ="2011-08-02";
	   //ngay truyen vao lay bao cao
       //du lieu vao pivotTable la du lieu tho
	   //va tinh 1 lan select toi don hang tinh la 1 va tinh % cua 1 so voi tong va sau khi cong thi PivotTable moi cong dung
	   //
       
String sql =" select c.ten as Channel,f.ten as Region,e.ten as Area,d.ten as Distributor, ";
	   sql =   sql+" g.ten as Sales_Rep,h.ten as Customer,t.ngaylamviec as Route,b.tong as Monthly_Avg_Order, 1 as MTD,y.ten as Province,case when b.tong >0 then 1/b.tong else 1 end as phantram,d.diachi as Adress,a.npp_fk as Distributor_code ";
	   sql =   sql+" from donhang a ";
	   sql =   sql+" inner join (select npp_fk,gsbh_fk,khachhang_fk,kbh_fk,cast(count(*)as float)/3 as tong from donhang where trangthai ='1' and ngaynhap >= '"+ ngaynhap1 +"' and ngaynhap <='"+ ngaynhap +"' group by npp_fk,gsbh_fk,khachhang_fk,kbh_fk) b ";
	   sql =   sql+" on a.npp_fk = b.npp_fk and a.khachhang_fk =b.khachhang_fk and a.gsbh_fk = b.gsbh_fk and a.kbh_fk = b.kbh_fk ";//tong cac don hang tinh theo chi so nho nhat 
	   sql =   sql+" inner join kenhbanhang c on c.pk_seq = a.kbh_fk ";
	   sql =   sql+" inner join nhaphanphoi d on d.pk_seq = a.npp_fk ";
	   sql =   sql+" inner join khuvuc e on e.pk_seq = d.khuvuc_fk ";
	   sql =   sql+" inner join vung f on f.pk_seq = e.vung_fk ";
	   sql =   sql+" inner join giamsatbanhang g on g.pk_seq = a.gsbh_fk ";
	   sql =   sql+" inner join khachhang h on h.pk_seq = a.khachhang_fk ";
	   sql =   sql+" left join KHACHHANG_TUYENBH i on i.khachhang_fk = a.khachhang_fk ";
	   sql =   sql+" left join tuyenbanhang t on t.pk_seq = i.tbh_fk and t.ddkd_fk = a.ddkd_fk";
	   sql =   sql+" left join quanhuyen y on y.pk_seq = d.quanhuyen_fk ";
	   sql =   sql+" where a.trangthai ='1' and SUBSTRING(a.ngaynhap,1,4) = '2011' and SUBSTRING(a.ngaynhap,6,2) = '12' ";
	   System.out.println(sql);
	   ResultSet rs = db.get(sql);

	    
		
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
					String Channel = rs.getString("Channel");
					String Region = rs.getString("Region");
					String Area = rs.getString("Area");
					String Distributor = rs.getString("Distributor");
					String DistributorCode = rs.getString("Distributor_code");
					String SalesRep = rs.getString("Sales_Rep");
					String Route = rs.getString("Route");
					String Customer = rs.getString("Customer");
					String Province = rs.getString("Province");
					String MonthlyAvgOrder = rs.getString("Monthly_Avg_Order");
					String Address = rs.getString("Adress");
					String MTD = rs.getString("MTD");
					String phantram = rs.getString("phantram");
					
					
					
					
					
					
					
					cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(Channel);
					cell = cells.getCell("AB" + Integer.toString(i)); cell.setValue(Region);
					cell = cells.getCell("AC" + Integer.toString(i)); cell.setValue(Area);
					cell = cells.getCell("AD" + Integer.toString(i)); cell.setValue(Distributor);
					cell = cells.getCell("AE" + Integer.toString(i)); cell.setValue(DistributorCode);
					cell = cells.getCell("AF" + Integer.toString(i)); cell.setValue(SalesRep);
					cell = cells.getCell("AG" + Integer.toString(i)); cell.setValue(Route);
					cell = cells.getCell("AH" + Integer.toString(i)); cell.setValue(Customer);
					cell = cells.getCell("AI" + Integer.toString(i)); cell.setValue(Province);
					cell = cells.getCell("AJ" + Integer.toString(i)); cell.setValue(Float.parseFloat(MonthlyAvgOrder));
					cell = cells.getCell("AK" + Integer.toString(i)); cell.setValue(Address);
					cell = cells.getCell("AL" + Integer.toString(i)); cell.setValue(Integer.parseInt(MTD));
					cell = cells.getCell("AM" + Integer.toString(i)); cell.setValue(Float.parseFloat(phantram));
					
				
					
					i++;
				}
			}
			catch (Exception e){ e.printStackTrace(); }
		}
		//xong buoc dua du lieu vao exel
		
		
		
	    
		
		//create pivot
		 getAn(workbook,49);
		PivotTables pivotTables = worksheet.getPivotTables();

	    //Adding a PivotTable to the worksheet
		String pos = Integer.toString(i-1);	//pos la dong cuoi cung	,A12 la toa do dau cua banng du lieu, Q pos la dong cuoi
	    int index = pivotTables.add("=AA1:AM" + pos,"A12","PivotTableDemo");
         System.out.println("index:"+index);
	    //Accessing the instance of the newly added PivotTable
	    PivotTable pivotTable = pivotTables.get(index);//truyen index

	    pivotTable.setRowGrand(true);
	    pivotTable.setColumnGrand(true);
	    pivotTable.setAutoFormat(true);
	    //Setting the PivotTable autoformat type.
	    //pivotTable.setAutoFormatType(PivotTableAutoFormatType.REPORT6);
 
	   
	  
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 0);	   
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 1);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 2);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 3);
	   // pivotTable.addFieldToArea(PivotFieldType.ROW, 4);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 5);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 6);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 7);
	    //pivotTable.addFieldToArea(PivotFieldType.ROW, 8);
	  //  pivotTable.addFieldToArea(PivotFieldType.ROW, 9);
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 9);
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 11);
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 12);
	    pivotTable.addFieldToArea(PivotFieldType.COLUMN,pivotTable.getDataField());
	    pivotTable.getDataFields().get(0).setDisplayName("Monthly_Avg_Order");
	 	   pivotTable.getDataFields().get(1).setDisplayName("MTD");
	 	  pivotTable.getDataFields().get(2).setDisplayName("%");

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
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
