package geso.dms.center.servlets.reports;

import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
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
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTables;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class DailySecondarySalesSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
        public DailySecondarySalesSvl() {
        super();
        // TODO Auto-generated constructor stub
    }
        
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        	request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			HttpSession session = request.getSession();

			String userTen="";
	        String tungay = "";
	        String denngay = "";

			userTen = (String)session.getAttribute("userTen");			
			String nextJSP = "/AHF/pages/Center/rp_DailySecondarySales.jsp";
			response.sendRedirect(nextJSP);
			
    	}

    	
    	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        	request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
		//	PrintWriter out = response.getWriter();
			HttpSession session = request.getSession();
			
	        String userTen="";
	        String tungay = "";
	        String denngay = "";
	        Utility util = new Utility();
			userTen = (String)session.getAttribute("userTen");
			tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
			denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
			
			OutputStream out = response.getOutputStream();
    	    try
    	    {
    	    	response.setContentType("application/vnd.ms-excel");
    	        response.setHeader("Content-Disposition", "attachment; filename=DailySecondarySales.xls");
    	        
    	        CreatePivotTable(out, tungay, denngay, userTen);
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
    	private void CreatePivotTable(OutputStream out, String tungay, String denngay, String userTen) throws IOException
        {    //khoi tao de viet pivottable

    		//buoc 1
    	     Workbook workbook = new Workbook();
    	     //Buoc2 tao khung
    	    //ham tao khu du lieu
    	     CreateStaticHeader(workbook, tungay, denngay, userTen);
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
    	    cells.setRowHeight(0, 25.0f);
    	    Cell cell = cells.getCell("A1");  
    	    cell.setValue("Daily Secondary Sales Report");   	
    	    
    	  
    	    style = cell.getStyle();
    	   
    	    Font font2 = new Font();
    	    font2.setColor(Color.RED);//mau chu
    	    font2.setSize(16);// size chu
    	    font2.setBold(true);
    	    style.setFont(font2); 
           
    	    style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu       
    	    cell.setStyle(style);
           
    	    cells.setRowHeight(1, 20.0f);
    	    cell = cells.getCell("A3"); getCellStyle(workbook,"A3",Color.BLUE,true,10);           
    	    cell.setValue("From " + dateFrom + "      To " + dateTo);    
    	    
    	    cells.setRowHeight(2, 20.0f);
    	    cell = cells.getCell("A4");getCellStyle(workbook,"A4",Color.BLUE,true,10);
    	    cell.setValue("Reporting Date: " + this.getDateTime());
    	    
    	    cells.setRowHeight(3, 20.0f);
    	    cell = cells.getCell("A5");getCellStyle(workbook,"A5",Color.BLUE,true,10);
    	    cell.setValue("Created by:  " + UserName);
    	        	  
    	    cells.setRowHeight(4, 20.0f);
    	    //tieu de, hang dau tien cua bang du lieu, cell la toa do cua exel
    	    cell = cells.getCell("AA1"); cell.setValue("Chanel");
    	    cell = cells.getCell("AB1"); cell.setValue("Region"); 
    	    cell = cells.getCell("AC1"); cell.setValue("Area");
    	    cell = cells.getCell("AD1"); cell.setValue("Sales Sup");
    	    cell = cells.getCell("AE1"); cell.setValue("Dist Code");
    	    cell = cells.getCell("AF1"); cell.setValue("Distributor");
    	    cell = cells.getCell("AG1"); cell.setValue("Sale Rep.");
    	    cell = cells.getCell("AH1"); cell.setValue("Customer");
    	    cell = cells.getCell("AI1"); cell.setValue("Province");
    	    cell = cells.getCell("AJ1"); cell.setValue("Town");    	    
    	    cell = cells.getCell("AK1"); cell.setValue("Brands");
    	    cell = cells.getCell("AL1"); cell.setValue("Categoty");
    	    cell = cells.getCell("AM1"); cell.setValue("SKU Code");
    	    cell = cells.getCell("AN1"); cell.setValue("SKU");    	    
    	    cell = cells.getCell("AO1"); cell.setValue("Quantity");
    	    cell = cells.getCell("AP1"); cell.setValue("Piece");
    	    cell = cells.getCell("AQ1"); cell.setValue("Amount");
    	    cell = cells.getCell("AR1"); cell.setValue("Outlet Type");   	    
    	    cell = cells.getCell("AS1"); cell.setValue("Off Date");
    	    cell = cells.getCell("AT1"); cell.setValue("Outlet Position");
    	    cell = cells.getCell("AU1"); cell.setValue("Outlet Class");
    	   
    	    worksheet.setName("Daily Secondary Sales Report");
    	}
    	private void CreateStaticData(Workbook workbook) 
    	{
    		dbutils db = new dbutils();		
    		/*ResultSet tb=  db.get("select b.npp_fk,a.ctkmid,sum(a.tonggiatri) as num from DONHANG_CTKM_TRAKM a, donhang b where a.donhangid = b.pk_seq group by b.npp_fk,ctkmid") ;
    		try {
				while(tb.next())
				{
				  db.update("update phanbokhuyenmai set dasudung ='"+ tb.getString("num") +"' where npp_fk ='"+ tb.getString("npp_fk")+"' and ctkm_fk='"+ tb.getString("ctkmid") +"'");
				  //System.out.print(arg0);
				}
			} catch(Exception e1) {
				
				e1.printStackTrace();
			}
    		*/
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
    	 String sql ="";		
    	 sql = sql +" select  f.ten as region, e.ten as area, d.ten as distributor, d.sitecode as distcode, g.ten as brand,h.ten as category, c.ma + '_' + c.ten as SKU, "; 
    	 sql =sql +" c.ma as SKUcode, i.ten as Saleperson, d.sitecode + '_' + j.ten + '( ' + j.diachi +  ' )' as customer, k.vitri as outletposition, ";
    	 sql =sql +" isnull(m.diengiai, 'Chua Xac Dinh') as outlettype, l.diengiai as chanel, a.ngaynhap as offdate, b.soluong as piece, (b.giamua - b.chietkhau) as amount, "; 
    	 sql =sql +" isnull(n.ten, 'Chua xac dinh') as salesuper,";
    	 sql =sql +" left((cast((o.soluong2 * b.soluong) as float) / cast(o.soluong1 as float)),5) as quantity,";
    	 sql =sql +" isnull(p.ten, 'Chua xac dinh') as town, ";
    	 sql =sql +" isnull(q.ten, 'Chua xac dinh') as province, ";
    	 sql =sql +" hch.diengiai as OutletClass ";
   sql =sql +" from donhang a inner join donhang_sanpham b on a.pk_seq = b.donhang_fk inner join sanpham c on b.sanpham_fk = c.pk_seq "; 
    	 sql =sql +" inner join nhaphanphoi d on a.npp_fk = d.pk_seq inner join khuvuc e on d.khuvuc_fk = e.pk_seq  ";
    	 sql =sql +" inner join vung f on e.vung_fk = f.pk_seq inner join nhanhang g on c.nhanhang_fk = g.pk_seq inner join chungloai h on c.chungloai_fk = h.pk_seq "; 
    	 sql =sql +" inner join daidienkinhdoanh i on a.ddkd_fk = i.pk_seq inner join khachhang j on a.khachhang_fk = j.pk_seq ";
    	 sql =sql +" inner join vitricuahang k on j.vtch_fk = k.pk_seq inner join kenhbanhang l on j.kbh_fk = l.pk_seq left join loaicuahang m on j.lch_fk = m.pk_seq "; 
    	 sql =sql +" left join giamsatbanhang n on a.gsbh_fk = n.pk_seq inner join quycach o on o.sanpham_fk = c.pk_seq ";
    	 sql =sql +" left join quanhuyen p on j.quanhuyen_fk = p.pk_seq left join tinhthanh q on j.tinhthanh_fk = q.pk_seq inner join hangcuahang hch on j.hch_fk = hch.pk_seq ";
    	 sql =sql +" where a.trangthai = '1' ";
 
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
    				cells.setColumnWidth(13, 15.0f);
    				cells.setColumnWidth(14, 15.0f);
    				cells.setColumnWidth(15, 15.0f);
    				cells.setColumnWidth(16, 15.0f);
    				cells.setColumnWidth(17, 15.0f);
    				cells.setColumnWidth(18, 15.0f);
    				cells.setColumnWidth(19, 15.0f);
    				cells.setColumnWidth(20, 15.0f);
//    				cells.setColumnWidth(21, 15.0f);
//    				cells.setColumnWidth(22, 15.0f);
    				
    				//set do rong cho dong
    				for(int j = 12; j <= 22; j++)
    					cells.setRowHeight(j, 14.0f);
    				
    				Cell cell = null;
    				while(rs.next())// lap den cuoi bang du lieu
    				{ int k = 0;
    					if (i>1){
    					k=i;}
    				
    					//lay tu co so du lieu, gan bien
    					 String Chanel = rs.getString("chanel");//kenh rs.getString("region");
    					 String Region = rs.getString("region");//vung mien rs.getString("area");
    					 String Brands = rs.getString("brand");//khu vuc rs.getString("salesuper");
    					 String Category = rs.getString("category");//giam sat ban hang rs.getString("distributor");
    					 String Area = rs.getString("area");
    					 String SaleSup = rs.getString("salesuper");//nha phan phoi rs.getString("distcode");
    					 String Distributor = rs.getString("distributor");
    					 String DistCode = rs.getString("distcode");//dai dien kinh doanh rs.getString("brand");
    					 String Province = rs.getString("province");//don vi kinh doanh rs.getString("category");
    					 String Sku = rs.getString("SKU");
    					 String SkuCode = rs.getString("SKUcode");
    					 String OffDate = rs.getString("offdate");//ma san pham rs.getString("Saleperson");
    					 String SaleRepresent = rs.getString("Saleperson");//ten san pham rs.getString("customer");
    					 String Piece = rs.getString("piece");//doanh so trung binh ban ra tu nha phan phoi rs.getString("outlettype");
    					 float Amount = Float.parseFloat(rs.getString("Amount"));// ten khach hang rs.getString("chanel");
    						//(b.giamua - b.chietkhau) as amount
    					 if(Amount <0 ) Amount = 0; 
    					 String Customer = rs.getString("customer");
    					 String OutletType = rs.getString("outlettype");
    					 float Quantity = Float.parseFloat(rs.getString("quantity"));// so luong tinh theo thung
    					 String Town = rs.getString("town");
    					 String OutletPosition =rs.getString("outletposition");
    					 String OutletClass = rs.getString("OutletClass");
//    					 String GroupCustomer = rs.getString("OutletClass");
    					 cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(Chanel);
    					 cell = cells.getCell("AB" + Integer.toString(i)); cell.setValue(Region);
    					 cell = cells.getCell("AC" + Integer.toString(i)); cell.setValue(Area);
    					 cell = cells.getCell("AD" + Integer.toString(i)); cell.setValue(SaleSup);
    					 cell = cells.getCell("AE" + Integer.toString(i)); cell.setValue(DistCode);
    					 cell = cells.getCell("AF" + Integer.toString(i)); cell.setValue(Distributor);    					 
    					 cell = cells.getCell("AG" + Integer.toString(i)); cell.setValue(SaleRepresent);
    					 cell = cells.getCell("AH" + Integer.toString(i)); cell.setValue(Customer);
    					 cell = cells.getCell("AI" + Integer.toString(i)); cell.setValue(Province);
    					 cell = cells.getCell("AJ" + Integer.toString(i)); cell.setValue(Town);
    					 cell = cells.getCell("AK" + Integer.toString(i)); cell.setValue(Brands);
    					 cell = cells.getCell("AL" + Integer.toString(i)); cell.setValue(Category);
    					 cell = cells.getCell("AM" + Integer.toString(i)); cell.setValue(SkuCode);
    					 cell = cells.getCell("AN" + Integer.toString(i)); cell.setValue(Sku);    					 
    					 cell = cells.getCell("AO" + Integer.toString(i)); cell.setValue(Quantity);
    					 cell = cells.getCell("AP" + Integer.toString(i)); cell.setValue(Integer.parseInt(Piece));
    					 cell = cells.getCell("AQ" + Integer.toString(i)); cell.setValue(Amount);
    					 cell = cells.getCell("AR" + Integer.toString(i)); cell.setValue(OutletType);    					 
    					 cell = cells.getCell("AS" + Integer.toString(i)); cell.setValue(OffDate);    					     					 
    					 cell = cells.getCell("AT" + Integer.toString(i)); cell.setValue(OutletPosition);
    					 cell = cells.getCell("AU" + Integer.toString(i)); cell.setValue(OutletClass);
    					
    					 i++;
    				}
    				if(rs != null) rs.close();
    				if(db != null) db.shutDown();
    			}
    			catch(Exception e){ e.printStackTrace(); }
    		}
    		//xong buoc dua du lieu vao exel
    		
    		
    		
    	    
    		
    		//create pivot
    		 getAn(workbook,49);
    		PivotTables pivotTables = worksheet.getPivotTables();

    	    //Adding a PivotTable to the worksheet
    		String pos = Integer.toString(i-1);	//pos la dong cuoi cung	,A12 la toa do dau cua banng du lieu, Q pos la dong cuoi
    	    int index = pivotTables.add("=AA1:AU" + pos,"A8","PivotTableDemo");
             System.out.println("index:"+index);
    	    //Accessing the instance of the newly added PivotTable
    	    PivotTable pivotTable = pivotTables.get(index);//truyen index

    	    pivotTable.setRowGrand(true);
    	    pivotTable.setColumnGrand(true);
    	    pivotTable.setAutoFormat(true);
    	    //Setting the PivotTable autoformat type.
    	    //pivotTable.setAutoFormatType(PivotTableAutoFormatType.REPORT6);
     
    	       	   
    	    pivotTable.addFieldToArea(PivotFieldType.ROW, 0);	  pivotTable.getRowFields().get(0).setAutoSubtotals(false); 
    	    pivotTable.addFieldToArea(PivotFieldType.ROW, 1);	pivotTable.getRowFields().get(1).setAutoSubtotals(false);
    	    pivotTable.addFieldToArea(PivotFieldType.ROW, 2);	pivotTable.getRowFields().get(2).setAutoSubtotals(false);
    	    pivotTable.addFieldToArea(PivotFieldType.ROW, 3);	pivotTable.getRowFields().get(3).setAutoSubtotals(false);
    	    pivotTable.addFieldToArea(PivotFieldType.ROW, 4);	pivotTable.getRowFields().get(4).setAutoSubtotals(false);
    	    pivotTable.addFieldToArea(PivotFieldType.ROW, 5);	pivotTable.getRowFields().get(5).setAutoSubtotals(false);
    	    pivotTable.addFieldToArea(PivotFieldType.ROW, 6);	pivotTable.getRowFields().get(6).setAutoSubtotals(false);
    	 
//    	    pivotTable.addFieldToArea(PivotFieldType.ROW, 7);	pivotTable.getRowFields().get(7).setAutoSubtotals(false);
//    	    pivotTable.addFieldToArea(PivotFieldType.ROW, 8);	pivotTable.getRowFields().get(8).setAutoSubtotals(false);
//    	    pivotTable.addFieldToArea(PivotFieldType.ROW, 9);	pivotTable.getRowFields().get(9).setAutoSubtotals(false);
    	    pivotTable.addFieldToArea(PivotFieldType.ROW, 10);	pivotTable.getRowFields().get(7).setAutoSubtotals(false);
    	    pivotTable.addFieldToArea(PivotFieldType.ROW, 11);	pivotTable.getRowFields().get(8).setAutoSubtotals(false);
    	    pivotTable.addFieldToArea(PivotFieldType.ROW, 12);	pivotTable.getRowFields().get(9).setAutoSubtotals(false);
    	    pivotTable.addFieldToArea(PivotFieldType.ROW, 13);	pivotTable.getRowFields().get(10).setAutoSubtotals(false);
  
//    	    pivotTable.addFieldToArea(PivotFieldType.ROW, 14);	pivotTable.getRowFields().get(14).setAutoSubtotals(false);
//    	    pivotTable.addFieldToArea(PivotFieldType.ROW, 15);	pivotTable.getRowFields().get(15).setAutoSubtotals(false);
    	    pivotTable.addFieldToArea(PivotFieldType.ROW, 16);	pivotTable.getRowFields().get(11).setAutoSubtotals(false);
//    	    pivotTable.addFieldToArea(PivotFieldType.ROW, 17);	pivotTable.getRowFields().get(17).setAutoSubtotals(false);
//    	    pivotTable.addFieldToArea(PivotFieldType.ROW, 18);	pivotTable.getRowFields().get(18).setAutoSubtotals(false);
//    	    pivotTable.addFieldToArea(PivotFieldType.ROW, 19);	pivotTable.getRowFields().get(19).setAutoSubtotals(false);
//    	    pivotTable.addFieldToArea(PivotFieldType.ROW, 20);	pivotTable.getRowFields().get(20).setAutoSubtotals(false);
    	    
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
