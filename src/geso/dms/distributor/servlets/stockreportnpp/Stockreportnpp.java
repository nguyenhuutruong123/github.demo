package geso.dms.distributor.servlets.stockreportnpp;

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
import javax.servlet.ServletOutputStream;
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


public class Stockreportnpp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Stockreportnpp() {
        super();
        // TODO Auto-generated constructor stub
    }

    String nppId;
    String nppTen;
    String userId;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		
		request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		   // PrintWriter out1 = response.getWriter();
  

		    Utility util = new Utility();
		  //  out1 = response.getWriter();
		    	    
		    String querystring = request.getQueryString();
		     userId = util.getUserId(querystring);
		     util.getIdNhapp(userId);
		   // out1.println(userId);
		    /* 
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
	*/
		
		
		ServletOutputStream out = response.getOutputStream();
	    try
	    {
	    	response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "attachment; filename=DailyStockReport.xls");
	        
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
	     CreateStaticHeader(workbook, "12-11-2011", "30-10-2011", this.nppTen);
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
	    cell.setValue("Daily Stock Report");   	
	    
	  
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
	    cell = cells.getCell("EA1"); cell.setValue("SKU"); 
	    cell = cells.getCell("EB1"); cell.setValue("Piece");
	    cell = cells.getCell("EC1"); cell.setValue("Date");
	    cell = cells.getCell("ED1"); cell.setValue("Distributor Code");
	    cell = cells.getCell("EE1"); cell.setValue("SKU Code");
	    //cell = cells.getCell("EF1"); cell.setValue("Quantily");	   
	    cell = cells.getCell("EF1"); cell.setValue("Warehouse");
	    cell = cells.getCell("EG1"); cell.setValue("Business Unit");
	    cell = cells.getCell("EH1"); cell.setValue("Category");
	    cell = cells.getCell("EI1"); cell.setValue("Brands");
	    //cell = cells.getCell("EK1"); cell.setValue("Amount");
	   
	    worksheet.setName("Daily Stock Report(Npp)");
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
	    String sql  = "select isnull(d.ten, 'Chua xac dinh') as Chanel, f.ten as Region, h.ten as Area, e.ten as Distributor, e.sitecode as Distcode,";
	    	sql = sql + " b.ma + '_' + b.ten as SKU, b.ma as SKUcode, a.ngay as Date, c.ten as Warehouse, g.ten as Province,";
	    	sql = sql + " i.donvikinhdoanh as BusinessUnit, k.ten as Brands, j.ten as Category, a.soluong as Piece";
	    	sql = sql + " from tonkhongay a inner join sanpham b on a.sanpham_fk = b.pk_seq inner join kho c on a.kho_fk = c.pk_seq";
	    	sql = sql + " left join kenhbanhang d on a.kbh_fk = d.pk_seq	inner join nhaphanphoi e on a.npp_fk = e.pk_seq";
	    	sql = sql + " inner join khuvuc f on e.khuvuc_fk = f.pk_seq inner join tinhthanh g on e.tinhthanh_fk = g.pk_seq";
	    	sql = sql + " inner join vung h on f.vung_fk = h.pk_seq inner join donvikinhdoanh i on b.dvkd_fk = i.pk_seq";
	    	sql = sql + " inner join chungloai j on b.chungloai_fk = j.pk_seq inner join nhanhang k on b.nhanhang_fk = k.pk_seq"; 	
	    	sql = sql + " where e.pk_seq = '"+ this.nppId +"'";

		 ResultSet rs = db.get(sql);
		 int i = 2;
		if(rs != null)
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
				//cells.setColumnWidth(10, 15.0f);
				
				
				//set do rong cho dong
				//for(int j = 12; j <= 6; j++)
					//cells.setRowHeight(j, 14.0f);
				
				Cell cell = null;
				while(rs.next())// lap den cuoi bang du lieu
				{ 
				
					//lay tu co so du lieu, gan bien
					String SKU = rs.getString("SKU");//kenh rs.getString("region");
					String Piece = rs.getString("Piece");//vung mien rs.getString("area");					
					String Date = rs.getString("Date");;					
					String DistributorCode = rs.getString("Distcode");;
					String SKUCode= rs.getString("SKUcode");//dai dien kinh doanh rs.getString("brand");
					//String Amount = rs.getString("Amount");//don vi kinh doanh rs.getString("category");
					//String Quantily = rs.getString("Quantily");//nhan hang rs.getString("SKU");
					String Warehouse = rs.getString("Warehouse");
					String BusinessUnit = rs.getString("BusinessUnit");
					String Category = rs.getString("Category");
					String Brands = rs.getString("Brands");
					//String Amount = rs.getString("Amount");
					
					
					
					
					cell = cells.getCell("EA" + Integer.toString(i)); cell.setValue(SKU);
					cell = cells.getCell("EB" + Integer.toString(i)); cell.setValue(Integer.parseInt(Piece));
					cell = cells.getCell("EC" + Integer.toString(i)); cell.setValue(Date);
					cell = cells.getCell("ED" + Integer.toString(i)); cell.setValue(DistributorCode);
					cell = cells.getCell("EE" + Integer.toString(i)); cell.setValue(SKUCode);
					//cell = cells.getCell("EF" + Integer.toString(i)); cell.setValue(Quantily);
					cell = cells.getCell("EF" + Integer.toString(i)); cell.setValue(Warehouse);
					cell = cells.getCell("EG" + Integer.toString(i)); cell.setValue(BusinessUnit);
					cell = cells.getCell("EH" + Integer.toString(i)); cell.setValue(Category);
					cell = cells.getCell("EI" + Integer.toString(i)); cell.setValue(Brands);
					//cell = cells.getCell("EK" + Integer.toString(i)); cell.setValue(Amount);					
					
					i++;
				}
			}
			catch (Exception e){ e.printStackTrace(); }
		}
		//xong buoc dua du lieu vao exel
		
		
		
	    
		
		//create pivot
		 getAn(workbook,156);
		PivotTables pivotTables = worksheet.getPivotTables();

	    //Adding a PivotTable to the worksheet
		String pos = Integer.toString(i-1);	//pos la dong cuoi cung	,A12 la toa do dau cua banng du lieu, Q pos la dong cuoi
	    int index = pivotTables.add("=EA1:EI" + pos,"A12","PivotTableDemo");
         System.out.println("index:"+index);
	    //Accessing the instance of the newly added PivotTable
	    PivotTable pivotTable = pivotTables.get(index);//truyen index

	    pivotTable.setRowGrand(true);
	    pivotTable.setColumnGrand(true);
	    pivotTable.setAutoFormat(true);
	    //Setting the PivotTable autoformat type.
	    //pivotTable.setAutoFormatType(PivotTableAutoFormatType.REPORT6);
 
	   
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 0);
	    
	    
	    pivotTable.addFieldToArea(PivotFieldType.COLUMN, 2);
	    //pivotTable.addFieldToArea(PivotFieldType.DATA, 5);
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 1);
	    
	    
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
	    for(int j = 130; j <= i; j++)
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
