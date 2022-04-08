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
//import javax.servlet.annotation.WebServlet;
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


//@WebServlet("/Productpricenpp")
public class Productpricenpp extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public Productpricenpp() {
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
	    String userTen;
	    String tungay;
	    String denngay;
	    boolean bfasle=true;
	    String userId;  

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	//	PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		userTen = (String)session.getAttribute("userTen");
		String querystring=request.getQueryString();
		userId=	util.getUserId(querystring);
		session.setAttribute("tungay", "");
 		session.setAttribute("denngay","");
    	 session.setAttribute("loi", "");
		session.setAttribute("userTen", userTen);
		String nextJSP = "/AHF/pages/Center/ProductPricenpp.jsp";
		response.sendRedirect(nextJSP);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OutputStream out = response.getOutputStream(); 
		Utility util=new Utility();
	    String userTen;
	    String tungay;
	    String denngay;
	    boolean bfasle=true;
	    String userId;  

		try
		    {
			HttpSession session = request.getSession();
			 userTen = (String)session.getAttribute("userTen");
			 userId = (String)session.getAttribute("userId");
			 tungay=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
			 denngay=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
	
	    	response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "attachment; filename=ProductPrice.xls");
	        CreatePivotTable(out,response,request, tungay, denngay, userTen, userId, bfasle);
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
	private void CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request, String tungay, String denngay, String userTen, String userId, boolean bfasle) throws IOException
    {    //khoi tao de viet pivottable
		//buoc 1
	     Workbook workbook = new Workbook();
	     //Buoc2 tao khung
	    //ham tao khu du lieu
	     CreateStaticHeader(workbook, tungay,denngay, userTen);
	     //Buoc3 
	     // day du lieu vao
	     CreateStaticData(workbook, tungay, denngay, userTen, userId, bfasle);
	     if(bfasle==false){
	    	String loi="chua co bao cao trong thoi gian nay, vui long chon lai thoi gian xem bao cao";
	    	
	    	 HttpSession session = request.getSession();
	 		session.setAttribute("loi", loi);
	 		session.setAttribute("tungay", tungay);
	 		session.setAttribute("denngay", denngay);
	    	 session.setAttribute("userTen", userTen);
	 		String nextJSP = "/AHF/pages/Center/ProductPricenpp.jsp";
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
	    cell.setValue("Product Price");   	
	    
	  
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
	   
	    cell = cells.getCell("AA1"); cell.setValue("BusinessUnit");
	    cell = cells.getCell("AB1"); cell.setValue("Brands");
	    cell = cells.getCell("AC1"); cell.setValue("Category");	  
	    cell = cells.getCell("AD1"); cell.setValue("Sku Code");
	    cell = cells.getCell("AE1"); cell.setValue("Sku");
	    cell = cells.getCell("AF1"); cell.setValue("Cogs");
	    cell = cells.getCell("AG1"); cell.setValue("Distributor Price");
	    cell = cells.getCell("AH1"); cell.setValue("Retailer Price");
	    cell = cells.getCell("AI1"); cell.setValue("VAT");
	    cell = cells.getCell("AJ1"); cell.setValue("Dist With VAT");
	    cell = cells.getCell("AK1"); cell.setValue("Retailer Price With VAT");
	    
	    
	    worksheet.setName("Product Price(npp)");
	}
	private void CreateStaticData(Workbook workbook, String tungay, String denngay, String userTen, String userId, boolean bfasle) 
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	   

	    String sql1 = "select a.pk_seq, a.ten, a.sitecode from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + userId + "'";
		ResultSet rs1 = db.get(sql1);
		String manpp ="";
		  try {
				rs1.next();
				manpp = rs1.getString("pk_seq");
			} catch(Exception e1) {
				
				e1.printStackTrace();
			}
	    
	    
	    //khoi tao ket noi csdl
		
		String sql  = "select dvkd.donvikinhdoanh as Business_unit,nh.ten as Brands,cl.ten as Category,sp.pk_seq as SKU_Code,sp.ten as SKU,bgmsp.giamuanpp as Distributor_price,bgblsp.giabanlenpp as Retailer_price,"+
				" 10 as VAT,cast(bgmsp.giamuanpp as int)*0.1 + cast(bgmsp.giamuanpp as int) as Dist_price_with_VAT,cast(bgblsp.giabanlenpp as int)*0.1 + cast(bgblsp.giabanlenpp as int) as Retailer_price,"+
				" case when bq.binhquan is NULL then 0 else bq.binhquan end  as Cogs "+
				" from BANGGIAMUANPP bgm "+
				" inner join BGMUANPP_SANPHAM bgmsp on bgmsp.bgmuanpp_fk = bgm.pk_seq and bgm.kenh_fk = 100000"+
				" inner join sanpham sp on sp.pk_seq =bgmsp.sanpham_fk"+
				" inner join BANGGIAMUANPP_NPP bgm_npp on bgm_npp.banggiamuanpp_fk = bgm.pk_seq"+
				" inner join BANGGIABANLENPP bgblnpp on bgblnpp.npp_fk = bgm_npp.npp_fk and bgblnpp.dvkd_fk = bgm.dvkd_fk"+
				" inner join BGBANLENPP_SANPHAM bgblsp on bgblsp.bgbanlenpp_fk = bgblnpp.pk_seq and bgblsp.sanpham_fk = sp.pk_seq"+
				" inner join donvikinhdoanh dvkd on dvkd.pk_seq = sp.dvkd_fk"+
				" inner join nhanhang nh on nh.pk_seq = sp.nhanhang_fk"+
				" inner join chungloai cl on cl.pk_seq =sp.chungloai_fk"+
				" left join (select sanpham_fk,avg(cast(giamua as int)) as binhquan from nhaphang_sp where nhaphang_fk in (select pk_seq from nhaphang where trangthai ='1') group by sanpham_fk) bq on bq.sanpham_fk = sp.ma"+
				" where  bgm_npp.npp_fk = '"+manpp+"'";


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
				
			
				
				Cell cell = null;
				while(rs.next())// lap den cuoi bang du lieu
				{
				
					//lay tu co so du lieu, gan bien
					String BusinessUnit = rs.getString("Business_unit");
					String Brands =rs.getString("Brands");
					String Category =rs.getString("Category");			
										
					String SKUcode = rs.getString("SKU_Code");
					String SKU =rs.getString("SKU");
					String Cogs = rs.getString("Cogs");		
					String DistributorPrice = rs.getString("Distributor_price");
					String RetailerPrice = rs.getString("Retailer_price");
					String VAT = rs.getString("VAT");
					String DistWithVAT = rs.getString("Dist_price_with_VAT");
					String RetailerPriceWithVAT = rs.getString("Retailer_price");
					
					
					
					//cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(Channel);
					cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(BusinessUnit);
					cell = cells.getCell("AB" + Integer.toString(i)); cell.setValue(Brands);
					cell = cells.getCell("AC" + Integer.toString(i)); cell.setValue(Category);
					cell = cells.getCell("AD" + Integer.toString(i)); cell.setValue(SKUcode);				
					cell = cells.getCell("AE" + Integer.toString(i)); cell.setValue(SKU);
					cell = cells.getCell("AF" + Integer.toString(i)); cell.setValue(Float.parseFloat(Cogs));
					cell = cells.getCell("AG" + Integer.toString(i)); cell.setValue(Float.parseFloat(DistributorPrice));
					cell = cells.getCell("AH" + Integer.toString(i)); cell.setValue(Float.parseFloat(RetailerPrice));
					cell = cells.getCell("AI" + Integer.toString(i)); cell.setValue(Float.parseFloat(VAT));
					cell = cells.getCell("AJ" + Integer.toString(i)); cell.setValue(Float.parseFloat(DistWithVAT));
					cell = cells.getCell("AK" + Integer.toString(i)); cell.setValue(Float.parseFloat(RetailerPriceWithVAT));
				
				
					i++;
				}
			
		
		//xong buoc dua du lieu vao exel
		
		
		
	    
	
		//create pivot
		 getAn(workbook,49);
		PivotTables pivotTables = worksheet.getPivotTables();

	    //Adding a PivotTable to the worksheet
		String pos = Integer.toString(i-1);	//pos la dong cuoi cung	,A12 la toa do dau cua banng du lieu, Q pos la dong cuoi
	    int index = pivotTables.add("=AA1:AK" + pos,"A12","PivotTableDemo");
        // System.out.println("index:"+index);
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
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 5);	pivotTable.getDataFields().get(0).setDisplayName("Cogs");
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 6);	pivotTable.getDataFields().get(1).setDisplayName("Distributor price");
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 7);	pivotTable.getDataFields().get(2).setDisplayName("Retailer price");
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 8);	pivotTable.getDataFields().get(3).setDisplayName("VAT(%)");
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 9);	pivotTable.getDataFields().get(4).setDisplayName("Dist price With VAT");
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 10);	pivotTable.getDataFields().get(5).setDisplayName("Retailer price  With VAT");
	    pivotTable.addFieldToArea(PivotFieldType.COLUMN, pivotTable.getDataField());
	    pivotTable.getDataFields().get(0).setNumber(3);
	    pivotTable.getDataFields().get(1).setNumber(3);
	    pivotTable.getDataFields().get(2).setNumber(3);
	    pivotTable.getDataFields().get(3).setNumber(3);
	    pivotTable.getDataFields().get(4).setNumber(3);
	    pivotTable.getDataFields().get(5).setNumber(3);
	    bfasle=true;
			}
			catch (Exception e){
				bfasle=false;
				e.printStackTrace(); 
				System.out.println("Error : Inventory Report : " +e.toString());
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
