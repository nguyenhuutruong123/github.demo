package geso.dms.center.servlets.reports;

import geso.dms.center.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

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
import javax.servlet.http.HttpSession;

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
import com.aspose.cells.Cell;

public class Promotionallfordist extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public Promotionallfordist() {
        super();
        // TODO Auto-generated constructor stub
    }

    private String gettenuser(String userId_){

    	dbutils db=new dbutils();
		String sql_getnam="select ten from nhanvien where pk_seq="+ userId_;
		ResultSet rs_tenuser=db.get(sql_getnam);
		if(rs_tenuser!= null){
			try{
			  while(rs_tenuser.next()){
				  return rs_tenuser.getString("ten");
			  }
			}catch(Exception er){
				return "";
			}
		}
		return "";
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   Utility util=new Utility();
		   String tungay="";
		   String denngay="";
		   String userTen="";
		   boolean bfasle=true; //Bien nay khai bao de khi chuong trinh bi loi thi khong xuat ra pivot

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	//	PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		userTen = (String)session.getAttribute("userTen");
		String querystring=request.getQueryString();
		String userId=	util.getUserId(querystring);
		session.setAttribute("tungay", "");
 		session.setAttribute("denngay","");
    	 session.setAttribute("loi", "");
		session.setAttribute("userTen", userTen);
		String nextJSP = "/AHF/pages/Center/PromotionAlloForDist.jsp";
		response.sendRedirect(nextJSP);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OutputStream out = response.getOutputStream(); 
    	Utility util=new Utility();
   	    String tungay="";
   		String denngay="";
   		String userTen="";
   		boolean bfasle=true; //Bien nay khai bao de khi chuong trinh bi loi thi khong xuat ra pivot

		try
		    {
			HttpSession session = request.getSession();
			userTen = (String)session.getAttribute("userTen");
			tungay=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
			denngay=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
	
	    	response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "attachment; filename=Promotionallfordist.xls");
	        CreatePivotTable(out,response,request, tungay, denngay, userTen, bfasle);
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
	private void CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request, String tungay, String denngay, String userTen, boolean bfasle) throws IOException
    {    //khoi tao de viet pivottable
		//buoc 1
	     Workbook workbook = new Workbook();
	     //Buoc2 tao khung
	    //ham tao khu du lieu
	     CreateStaticHeader(workbook, tungay,denngay, userTen);
	     //Buoc3 
	     // day du lieu vao
	     CreateStaticData(workbook, tungay, denngay, userTen, bfasle);
	     if(bfasle==false){
	    	String loi="Chưa có dữ liệu báo cáo trong thời gian này, vui lòng chọn lại thời  gian xem báo cáo";
	    	
	    	 HttpSession session = request.getSession();
	 		session.setAttribute("loi", loi);
	 		session.setAttribute("tungay", tungay);
	 		session.setAttribute("denngay", denngay);
	    	 session.setAttribute("userTen", userTen);
	 		String nextJSP = "/AHF/pages/Center/PromotionAlloForDist.jsp";
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
	    cell.setValue("Promotion Allocation For Distributor");   	
	    
	  
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
	   // cell = cells.getCell("AA1"); cell.setValue("Chanel");
	    cell = cells.getCell("AA1"); cell.setValue("Region");
	    cell = cells.getCell("AB1"); cell.setValue("Area");
	    cell = cells.getCell("AC1"); cell.setValue("Monthly Avg Second Sales");
	    cell = cells.getCell("AD1"); cell.setValue("% In Nation");
	    cell = cells.getCell("AE1"); cell.setValue("Promotion Allocation");
	    cell = cells.getCell("AF1"); cell.setValue("Barnd");
	    cell = cells.getCell("AG1"); cell.setValue("Cat");
	   
	    
	    worksheet.setName("Promotion Allocation For Dist");
	}
	private void CreateStaticData(Workbook workbook, String tungay, String denngay, String userTen, boolean bfasle) 
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    ResultSet rs1;
	    String tongdoanhso="0";
	    String sql1 = "select sum(soluong*giamua)/3 as tongdoanhso from donhang_sanpham a inner join donhang b on b.pk_seq = a.donhang_fk where b.ngaynhap <= '2011-12-01' and b.ngaynhap >= '2011-11-01'";
	    rs1 = db.get(sql1);
	   try {
		rs1.next();
		tongdoanhso = rs1.getString("tongdoanhso");
	} catch(Exception e1) {
		
		e1.printStackTrace();
	}
	   
	    
	    //khoi tao ket noi csdl
		
		String sql  = "select d.ten as Channel,f.ten as Region ,e.ten as Area, b.ten as Distributor,nh.ten as Brands,"+
						" cl.ten as Category,a.doanhso_npp as Monthly_Avg_second_sales,cast(a.doanhso_npp as float)/cast(c.doanhso_khuvuc as float) * 1000 as Promtion_Allocation ,g.ten as Province,a.npp_fk as Distributor_code";
		sql = sql + " from ( select b.npp_fk,b.kbh_fk,c.nhanhang_fk,c.chungloai_fk,sum(soluong*giamua)/3 as doanhso_npp from donhang_sanpham a";
		sql = sql + " inner join donhang b on b.pk_seq = a.donhang_fk "; 
		sql = sql + " inner join sanpham c on c.pk_seq = a.sanpham_fk ";
		sql = sql + " where b.ngaynhap <= '"+tungay+"' and b.ngaynhap >= '2011-08-01'";
		sql = sql + " group by b.npp_fk,b.kbh_fk,c.nhanhang_fk,c.chungloai_fk ";
		sql = sql + " ) a inner join nhaphanphoi b on b.pk_seq = a.npp_fk inner join (";			
		sql = sql + " select d.khuvuc_fk,b.kbh_fk,c.nhanhang_fk,c.chungloai_fk,sum(soluong*giamua)/3 as doanhso_khuvuc from donhang_sanpham a";
		sql = sql + " inner join donhang b on b.pk_seq = a.donhang_fk "; 
		sql = sql + " inner join sanpham c on c.pk_seq = a.sanpham_fk ";
		sql = sql + " inner join nhaphanphoi d on d.pk_seq = b.npp_fk   ";
		sql = sql + " where b.ngaynhap <= '"+tungay+"' and b.ngaynhap >= '2011-08-01'";
		sql = sql + " group by d.khuvuc_fk,b.kbh_fk,c.nhanhang_fk,c.chungloai_fk";
		sql = sql + " ) c on b.khuvuc_fk = c.khuvuc_fk and  c.kbh_fk = a.kbh_fk and c.nhanhang_fk = a.nhanhang_fk and c.chungloai_fk =  a.chungloai_fk"; 
		sql = sql + " inner join kenhbanhang d on d.pk_seq = a.kbh_fk inner join khuvuc e on e.pk_seq = b.khuvuc_fk inner join vung f on f.pk_seq = e.vung_fk";
		sql = sql + " left join tinhthanh g on  g.pk_seq = b.tinhthanh_fk inner join nhanhang nh on nh.pk_seq = a.nhanhang_fk inner join chungloai cl on cl.pk_seq =a.chungloai_fk";
		System.out.println("Lay Du Lieu :"+sql);
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
				
				//for(int j = 12; j <= 11; j++)
					//cells.setRowHeight(j, 14.0f);
				
				Cell cell = null;
				while(rs.next())// lap den cuoi bang du lieu
				{
				
					//lay tu co so du lieu, gan bien
					//String Channel = rs.getString("Brand");
					String Region =rs.getString("Region");
					String Area =rs.getString("Area");
					String MonthlyAvgSecondSales =rs.getString("Monthly_Avg_second_sales");
					//String phantramInNation =rs.getString("phantram");					
					String Brand = rs.getString("Brands");
					String Cat =rs.getString("Category");
					String PromotionAllocation = rs.getString("Promtion_Allocation");		
					
					
					
					//cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(Channel);
					cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(Region);
					cell = cells.getCell("AB" + Integer.toString(i)); cell.setValue(Area);
					cell = cells.getCell("AC" + Integer.toString(i)); cell.setValue(Float.parseFloat(MonthlyAvgSecondSales));
					cell = cells.getCell("AD" + Integer.toString(i)); cell.setValue(100 * Float.parseFloat(MonthlyAvgSecondSales)/193267336.839844);				
					cell = cells.getCell("AE" + Integer.toString(i)); cell.setValue(Float.parseFloat(PromotionAllocation));
					cell = cells.getCell("AF" + Integer.toString(i)); cell.setValue(Brand);
					cell = cells.getCell("AG" + Integer.toString(i)); cell.setValue(Cat);
					i++;
				}
			
		
		//xong buoc dua du lieu vao exel
		
		
		
	    
		
		//create pivot
		 getAn(workbook,49);
		PivotTables pivotTables = worksheet.getPivotTables();

	    //Adding a PivotTable to the worksheet
		String pos = Integer.toString(i-1);	//pos la dong cuoi cung	,A12 la toa do dau cua banng du lieu, Q pos la dong cuoi
	    int index = pivotTables.add("=AA1:AG" + pos,"A12","PivotTableDemo");
        // System.out.println("index:"+index);
	    //Accessing the instance of the newly added PivotTable
	    PivotTable pivotTable = pivotTables.get(index);//truyen index

	    pivotTable.setRowGrand(false);
	    pivotTable.setColumnGrand(true);
	    pivotTable.setAutoFormat(true);
	    //Setting the PivotTable autoformat type.
	    //pivotTable.setAutoFormatType(PivotTableAutoFormatType.REPORT6);
 
	   
	  
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 0);	 pivotTable.getRowFields().get(0).setAutoSubtotals(false);  
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 1);	pivotTable.getRowFields().get(1).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 2);	pivotTable.getDataFields().get(0).setDisplayName("Monthly Avg second sales");
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 3);	pivotTable.getDataFields().get(1).setDisplayName("% in Nation");
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 4);	pivotTable.getDataFields().get(2).setDisplayName("Promotion Allocation");
	    pivotTable.getDataFields().get(1).setDataDisplayFormat(PivotFieldDataDisplayFormat.PERCENTAGE_OF_TOTAL);
	    pivotTable.addFieldToArea(PivotFieldType.COLUMN, pivotTable.getDataField());
	    bfasle=true;
			}
			catch (Exception e){
				bfasle=false;
				e.printStackTrace(); 
				System.out.println("Error : Promotionnalfordist : "+e.toString());
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
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}
