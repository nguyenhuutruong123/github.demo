package geso.dms.center.servlets.upromoAllcation;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geso.dms.center.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

public class upromoAllcationnpp extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public upromoAllcationnpp() {
        super();
        // TODO Auto-generated constructor stub
    }

    String nppId;
    String nppTen;
    String userId;  

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 request.setCharacterEncoding("UTF-8");
 	    response.setCharacterEncoding("UTF-8");
 	    response.setContentType("text/html; charset=UTF-8");
 	   // PrintWriter out1 = response.getWriter();
 	    	    
 	    HttpSession session = request.getSession();	    

 	    Utility util = new Utility();
 	  //  out1 = response.getWriter();
 	    	    
 	    String querystring = request.getQueryString();
 	     userId = util.getUserId(querystring);
 	   // out1.println(userId);
 	     
 	     System.out.println("user:"+userId);
 	   dbutils db = new dbutils();
 	   ResultSet rs = db.get("select a.pk_seq, a.ten, a.sitecode from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + userId + "'");
 		try{
 			if (!(rs == null)){
 				rs.next();
 				nppId = rs.getString("pk_seq");
 				nppTen = rs.getString("ten");
 				//this.sitecode = rs.getString("sitecode");
 				
 			}else{
 			//	this.msg = "select a.pk_seq, a.ten, a.sitecode from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + this.userId + "'";
 				nppId = "";
 				nppTen = "";
 				//this.sitecode = "";				
 			}
 			
 		}catch(Exception e){}	
 	 System.out.println("nha pp:"+ nppId);
 	   
 	
    	OutputStream out = response.getOutputStream();
 	    try
 	    {
 	    	response.setContentType("application/vnd.ms-excel");
 	        response.setHeader("Content-Disposition", "attachment; filename=PromotionAllocationReport.xls");
 	        
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
 	     CreateStaticHeader(workbook, "12-11-2011", "30-10-2011",this.nppTen);
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
 	    cell.setValue("Using Promotion Allocation Report");   	
 	    
 	  
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
 	    cell = cells.getCell("AE1"); cell.setValue("Programs Code");
 	    cell = cells.getCell("AF1"); cell.setValue("Programs Name");
 	    cell = cells.getCell("AG1"); cell.setValue("Used Fixed");
 	   
 	    cell = cells.getCell("AH1"); cell.setValue("% Used");
 	    cell = cells.getCell("AI1"); cell.setValue("Allocation");
 	    cell = cells.getCell("AJ1"); cell.setValue("Remain");
 	   
 	   
 	    worksheet.setName("Using Promotion Allocation Report(tt)");
 	}
 	private void CreateStaticData(Workbook workbook) 
 	{
 		Worksheets worksheets = workbook.getWorksheets();
 	    Worksheet worksheet = worksheets.getSheet(0);
 	    Cells cells = worksheet.getCells();
 	    
 	    //khoi tao ket noi csdl
 		/*dbutils db = new dbutils();
 		String query = "select f.ten as region, e.ten as area, d.ten as distributor, d.sitecode as distcode, g.ten as brand,h.ten as category, c.ma + '_' + c.ten as SKU, c.ma as SKUcode, i.ten as Saleperson, ";
 		query = query + "d.sitecode + '_' + j.ten + '( ' + j.diachi +  ' )' as customer, k.vitri as outlettype, l.diengiai as chanel, a.ngaynhap as offdate, b.soluong as amount, isnull(m.ten, 'Chua xac dinh') as salesuper ";
 		query = query +	"from donhang a inner join donhang_sanpham b on a.pk_seq = b.donhang_fk inner join sanpham c on b.sanpham_fk = c.pk_seq inner join nhaphanphoi d on a.npp_fk = d.pk_seq ";
 		query = query + "inner join khuvuc e on d.khuvuc_fk = e.pk_seq inner join vung f on e.vung_fk = f.pk_seq inner join nhanhang g on c.nhanhang_fk = g.pk_seq inner join chungloai h on c.chungloai_fk = h.pk_seq ";
 		query = query + "inner join daidienkinhdoanh i on a.ddkd_fk = i.pk_seq inner join khachhang j on a.khachhang_fk = j.pk_seq inner join vitricuahang k on j.vtch_fk = k.pk_seq inner join kenhbanhang l on j.kbh_fk = l.pk_seq left join giamsatbanhang m on a.gsbh_fk = m.pk_seq";
 		ResultSet rs = db.get(query);*/
 	   dbutils db = new dbutils();
 	String sql  ="";
 	sql =sql +	" select distinct g.ten as Chanel, d.ten as Region, e.ten as Area, a.ten as Distributor, a.sitecode as Distcode, ";
	 sql = sql+  " c.scheme as ProgramsCode, isnull(c.diengiai, 'Chua xac dinh') as PromotionPrograms, b.ngansach as Allocation, b.dasudung as Used, ";
	  sql = sql+   "(b.ngansach - b.dasudung) as Remain, c.tungay as fromdate, c.denngay as todate ";
sql = sql + " from nhaphanphoi a inner join phanbokhuyenmai b on b.npp_fk = a.pk_seq inner join ctkhuyenmai c on b.ctkm_fk = c.pk_seq ";
	sql  = sql +" inner join khuvuc d on a.khuvuc_fk = d.pk_seq inner join vung e on d.vung_fk = e.pk_seq ";
	sql = sql + " inner join nhapp_kbh f on f.npp_fk = a.pk_seq inner join kenhbanhang g on f.kbh_fk = g.pk_seq and a.pk_seq ='"+ this.nppId +"'";
	System.out.println(sql);
  ResultSet rs = db.get(sql);

 		int i = 2;
 		if(rs!=null)
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
 				
 				
 				
 				Cell cell = null;
 				while(rs.next())// lap den cuoi bang du lieu
 				{ //lay tu co so du lieu, gan bien
 					String Chanel = rs.getString("Chanel");
 					String Region = rs.getString("Region");
 					String Area = rs.getString("Area");
 					String Distributor = rs.getString("Distributor");
 					String ProgramsCode=rs.getString("ProgramsCode");
 					String ProgramsName = rs.getString("PromotionPrograms");
 					String UsedFixed = rs.getString("Used");
 					String Allocation = rs.getString("Allocation"); 
 				    String Remain =rs.getString("Remain");//(reiam /Allocation) *1000
 					//
 				//	String phantramUsed = rs.getString("Chanel");//tinh
 					//lay tu co so du lieu, gan bien
						
 					cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(Chanel);
					cell = cells.getCell("AB" + Integer.toString(i)); cell.setValue(Region);
					cell = cells.getCell("AC" + Integer.toString(i)); cell.setValue(Area);
					cell = cells.getCell("AD" + Integer.toString(i)); cell.setValue(Distributor);
					cell = cells.getCell("AE" + Integer.toString(i)); cell.setValue(ProgramsCode);
					cell = cells.getCell("AF" + Integer.toString(i)); cell.setValue(ProgramsName);
					cell = cells.getCell("AG" + Integer.toString(i)); cell.setValue(Integer.parseInt(UsedFixed));
					
					
					cell = cells.getCell("AI" + Integer.toString(i)); cell.setValue(Integer.parseInt(Allocation));
					//allocation - usedfixd
					cell = cells.getCell("AJ" + Integer.toString(i)); cell.setValue(Integer.parseInt(Remain));
					//(used fixd * 100) / allocation
					cell = cells.getCell("AH" + Integer.toString(i)); cell.setValue((Integer.parseInt(UsedFixed)/Integer.parseInt(Allocation)) *100);
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
 	    int index = pivotTables.add("=AA1:AJ" + pos,"A12","PivotTableDemo");
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
 	    pivotTable.addFieldToArea(PivotFieldType.ROW, 4);
 	    pivotTable.addFieldToArea(PivotFieldType.ROW, 5);
 	    pivotTable.addFieldToArea(PivotFieldType.ROW, 3);
 	    pivotTable.addFieldToArea(PivotFieldType.DATA, 8);
 	    pivotTable.addFieldToArea(PivotFieldType.DATA, 6);
 	    pivotTable.addFieldToArea(PivotFieldType.DATA, 9);
 	    pivotTable.addFieldToArea(PivotFieldType.DATA, 7);
 	    
 	    
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
