package geso.dms.center.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

public class UsingPromotionSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
  
    public UsingPromotionSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	String userTen;
        String userId;
        
        Utility util=new Utility();
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
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Center/rp_UsingPromotion.jsp";
		response.sendRedirect(nextJSP);
    	 
		
		
 	}

 	
 	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 		OutputStream out = response.getOutputStream(); 
 		String tungay;
        String denngay;
        Utility util=new Utility();
		try
		    {
			HttpSession session = request.getSession();
			String userTen = (String)session.getAttribute("userTen");
			tungay=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
			denngay=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
	
	    	response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "attachment; filename=UsingPromotion.xls");
	        
	        String userId = (String) session.getAttribute("userId");
			CreatePivotTable(out,response,request, tungay, denngay, userTen, userId );
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

 	private void CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request, String tungay, String denngay, String userTen, String useId) throws IOException
     {       //khoi tao de viet pivottable
 		boolean bfasle=true;
		//buoc 1
	     Workbook workbook = new Workbook();
	     //Buoc2 tao khung
	    //ham tao khu du lieu
	     CreateStaticHeader(workbook, tungay,denngay, userTen);
	     //Buoc3 
	     // day du lieu vao
	     CreateStaticData(workbook, useId, tungay, denngay, bfasle);
	     if(bfasle==false){
	    	String loi="Chua co bao cao trong thoi gian nay, vui long chon lai thoi gian lay bao cao";
	    	
	    	 HttpSession session = request.getSession();
	 		session.setAttribute("loi", loi);
	 		session.setAttribute("tungay", tungay);
	 		session.setAttribute("denngay", denngay);
	    	 session.setAttribute("userTen", userTen);
	 		String nextJSP = "/AHF/pages/Center/rp_UsingPromotion.jsp";
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
 	    cells.setRowHeight(0, 25.0f);
 	    Cell cell = cells.getCell("A1");  
 	    cell.setValue("Using Promotion");   	
 	    
 	  
 	    style = cell.getStyle();
 	   
 	    Font font2 = new Font();
        font2.setColor(Color.RED);//mau chu
        font2.setSize(16);// size chu
        font2.setBold(true);
        style.setFont(font2); 
        style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu  
        cell.setStyle(style);
        
        cells.setRowHeight(2, 20.0f);
        cell = cells.getCell("A3"); getCellStyle(workbook,"A3",Color.BLUE,true,10);        
 	    cell.setValue("From " + dateFrom + "      To " + dateTo);  
 	    
 	    cells.setRowHeight(3, 20.0f);
 	    cell = cells.getCell("A4");getCellStyle(workbook,"A4",Color.BLUE,true,10);
 	    cell.setValue("Reporting Date: " + this.getDateTime());
 	    
 	    cells.setRowHeight(4, 20.0f);
 	    cell = cells.getCell("A5");getCellStyle(workbook,"A5",Color.BLUE,true,10);
 	    cell.setValue("Created by:  " + UserName);
 	    
	  
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
 	   	   
 	    worksheet.setName("Using Promotion");
 	}
 	private void CreateStaticData(Workbook workbook, String userId, String tungay, String denngay, boolean bfasle) 
 	{
 		Worksheets worksheets = workbook.getWorksheets();
 	    Worksheet worksheet = worksheets.getSheet(0);
 	    Cells cells = worksheet.getCells();
 	    
 	   
 	   dbutils db = new dbutils();
 	   String sql  ="";
 	   sql =  sql +	" select distinct g.ten as Chanel, d.ten as Region, e.ten as Area, a.ten as Distributor, a.sitecode as Distcode, ";
 	   sql =  sql+  " c.scheme as ProgramsCode, isnull(c.diengiai, 'Chua xac dinh') as PromotionPrograms, b.ngansach as Allocation, b.dasudung as Used, ";
 	   sql =  sql+  " (b.ngansach - b.dasudung) as Remain, c.tungay as fromdate, c.denngay as todate ";
 	   sql =  sql + " from nhaphanphoi a inner join phanbokhuyenmai b on b.npp_fk = a.pk_seq inner join ctkhuyenmai c on b.ctkm_fk = c.pk_seq ";
 	   sql =  sql +" inner join khuvuc d on a.khuvuc_fk = d.pk_seq inner join vung e on d.vung_fk = e.pk_seq ";
 	   sql =  sql + " inner join nhapp_kbh f on f.npp_fk = a.pk_seq inner join kenhbanhang g on f.kbh_fk = g.pk_seq where c.tungay >='" + tungay + "' and c.denngay <='" + denngay + "'";
 	   
		//phanquyen
		geso.dms.center.util.Utility ut = new geso.dms.center.util.Utility();
		sql += " and a.pk_seq in "+ ut.quyen_npp(userId) + " and g.pk_seq in " + ut.quyen_kenh(userId);
							//+" and sp.pk_seq in "+ ut.quyen_sanpham(userId);

 	   
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
 					String UsedFixed = "0";
 					if (rs.getString("Used") != null)
 					{
 						UsedFixed=rs.getString("Used");
 					}
 					String Allocation ="0";
 					if (rs.getString("Allocation") != null)
 					{
 						Allocation =rs.getString("Allocation");
 					}
 					
 				    String Remain ="0";
 				    if (rs.getString("Remain") != null)
 				    {
 				    	Remain=rs.getString("Remain");
 				    }
						
 					cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(Chanel);
					cell = cells.getCell("AB" + Integer.toString(i)); cell.setValue(Region);
					cell = cells.getCell("AC" + Integer.toString(i)); cell.setValue(Area);
					cell = cells.getCell("AD" + Integer.toString(i)); cell.setValue(Distributor);
					cell = cells.getCell("AE" + Integer.toString(i)); cell.setValue(ProgramsCode);
					cell = cells.getCell("AF" + Integer.toString(i)); cell.setValue(ProgramsName);
					cell = cells.getCell("AG" + Integer.toString(i)); cell.setValue(Float.parseFloat(UsedFixed));
					
					
					cell = cells.getCell("AI" + Integer.toString(i)); cell.setValue(Float.parseFloat(Allocation));
					//allocation - usedfixd
					cell = cells.getCell("AJ" + Integer.toString(i)); cell.setValue(Float.parseFloat(Remain));
					//(used fixd * 100) / allocation
					cell = cells.getCell("AH" + Integer.toString(i)); cell.setValue((Float.parseFloat(UsedFixed)/Float.parseFloat(Allocation)) * 100);
				i++;
 					}
 			
 			
 		//xong buoc dua du lieu vao exel
 		
 		//create pivot
 		getAn(workbook,49);
 		PivotTables pivotTables = worksheet.getPivotTables();

 	    //Adding a PivotTable to the worksheet
 		
 		String pos = Integer.toString(i-1);	//pos la dong cuoi cung	,A12 la toa do dau cua banng du lieu, Q pos la dong cuoi
 	    int index = pivotTables.add("=AA1:AJ" + pos,"A8","PivotTableDemo");
          System.out.println("index:"+index);
 	    //Accessing the instance of the newly added PivotTable
 	    PivotTable pivotTable = pivotTables.get(index);//truyen index

 	    pivotTable.setRowGrand(false);
 	    pivotTable.setColumnGrand(true);
 	    pivotTable.setAutoFormat(false);
 	    pivotTable.setSubtotalHiddenPageItems(false);
 	   
 	    pivotTable.addFieldToArea(PivotFieldType.ROW, 0);
 	    pivotTable.addFieldToArea(PivotFieldType.ROW, 1);
 	    pivotTable.addFieldToArea(PivotFieldType.ROW, 2);
 	    pivotTable.addFieldToArea(PivotFieldType.ROW, 4);pivotTable.getRowFields().get(3).setAutoSubtotals(false);
 	    pivotTable.addFieldToArea(PivotFieldType.ROW, 5);pivotTable.getRowFields().get(4).setAutoSubtotals(false);
 	    pivotTable.addFieldToArea(PivotFieldType.ROW, 3);pivotTable.getRowFields().get(5).setAutoSubtotals(false);
 	    pivotTable.addFieldToArea(PivotFieldType.ROW, 8);pivotTable.getRowFields().get(6).setAutoSubtotals(false);
 	    pivotTable.addFieldToArea(PivotFieldType.ROW, 6);pivotTable.getRowFields().get(7).setAutoSubtotals(false);
 	    pivotTable.addFieldToArea(PivotFieldType.ROW, 9);pivotTable.getRowFields().get(8).setAutoSubtotals(false);
 	    pivotTable.addFieldToArea(PivotFieldType.ROW, 7); pivotTable.getRowFields().get(9).setAutoSubtotals(false);
 	   
 	   bfasle=true;
 		}
 		catch (Exception e){
		bfasle=false;
		e.printStackTrace(); 
		System.out.println("Error : Oppgrowthordersonroute : "+e.toString());
 		}
 		finally{
 			if(rs != null)
				try {
					rs.close();
				} catch(Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(db != null) db.shutDown();
 		}
 		}else{
 			if(db != null) db.shutDown();
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
         DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
         Date date = new Date();
         return dateFormat.format(date);	
 	}
}
