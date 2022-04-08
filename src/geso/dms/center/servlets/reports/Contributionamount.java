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
public class Contributionamount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public Contributionamount() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OutputStream out = response.getOutputStream();
	    try
	    {
	    	response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "attachment; filename=TargetAllocationonContributionAmount.xls");
	        
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
	    cell.setValue("Daily Sales Report (YTD)");   	
	    
	  
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
	    cell.setValue("Reporting Date: " + this.getDateTime());
	    cell = cells.getCell("B4");getCellStyle(workbook,"B4",Color.BLUE,true,10);
	    cell.setValue("Created by:  " + UserName);
	    

	    

	  

	  
	  
	    //tieu de, hang dau tien cua bang du lieu, cell la toa do cua exel
	    cell = cells.getCell("EA1"); cell.setValue("Channel"); 
	    cell = cells.getCell("EB1"); cell.setValue("Region"); 
	    cell = cells.getCell("EC1"); cell.setValue("Area");
	    cell = cells.getCell("ED1"); cell.setValue("Distributor");
	    cell = cells.getCell("EE1"); cell.setValue("Monthly Avg Second Sales");
	    cell = cells.getCell("EF1"); cell.setValue("%");
	    cell = cells.getCell("EG1"); cell.setValue("Target");	   
	    	   
	    worksheet.setName("Contribution Amount(tar)");
	}
	private void CreateStaticData(Workbook workbook) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    
	    //khoi tao ket noi csdl
		dbutils db = new dbutils();
		String sql = "select kbh.ten as Channel, dvkd.donvikinhdoanh as Business_unit, v.ten as Vung, kv.ten as Khuvuc, gsbh.ten as Sales_sup, npp.ten as Distributor, ct.chitieupri as MonthlyTargetPrimary,";
		sql = sql + " ct.chitieusec as MonthlyTargetSecondary, ds.giatrimua as MonthlyAchievedPrimary, ds.giatriban as MonthlyAchievedSecondary, case when ct.chitieupri > 0 then 100*ds.giatrimua/ct.chitieupri else 0 end as phantramMTD_Primary,";
		sql = sql + " case when ds.giatriban > 0 then 100*ds.giatriban/ct.chitieusec else 0 end as phantramMTD_Secondary, case when namtruoc.giatrimua is null then 0 else namtruoc.giatrimua end as MonthlyArchivePrimaryLastYear,";
		sql = sql + " case when namtruoc.giatriban is null then 0 else namtruoc.giatriban end as MonthlyArchiveSecondLastYear, case when namtruoc.giatrimua = 0 then 0 else 100*ds.giatrimua/namtruoc.giatrimua end as phantramGrowth_MTD_Primary,";
		sql = sql + " case when namtruoc.giatriban = 0 then 0 else 100*ds.giatriban/namtruoc.giatriban end as phantramGrowth_MTD_Secondary, namht.giatriban as YTDArchivePrimary, namht.giatrimua as YTDArchiveSecond,";
		sql = sql + " namcu.giatriban as YTDArchivePrimaryLastYear, namcu.giatrimua as YTDArchiveSecondLastYear, case when namcu.giatriban =0 then 0 else 100*namht.giatriban/namcu.giatriban end as phantramGrowth_YTD_Primary,";
		sql = sql + " case when namcu.giatrimua =0 then 0 else 100*namht.giatrimua/namcu.giatrimua end as phantramGrowth_YTD_Secondary from (select case when ddh.npp_fk is null then dh.npp_fk else ddh.npp_fk end as npp_fk,";
		sql = sql + " case when ddh.gsbh_fk is null then dh.gsbh_fk else ddh.gsbh_fk end as gsbh_fk, case when ddh.kbh_fk is null then dh.kbh_fk else ddh.kbh_fk end as kbh_fk, case when ddh.giatrimua is null then 0 else ddh.giatrimua end as giatrimua,";
		sql = sql + " case when dh.giatriban is null then 0 else dh.giatriban end as giatriban from ( select npp_fk,kbh_fk,gsbh_fk,sum(convert(numeric(18),sotienavat))as giatrimua from dondathang where cast(trangthai as int)>2 and loaidonhang ='1' and substring(ngaydat,1,4)='2011' and substring(ngaydat,6,2) ='11' group by npp_fk,kbh_fk,gsbh_fk ) ddh";
		sql = sql + " FULL OUTER JOIN (select npp_fk,kbh_fk,gsbh_fk,sum(tonggiatri) as giatriban from donhang where substring(ngaynhap,1,4)='2011' and substring(ngaynhap,6,2) ='11' and trangthai >= 1 group by npp_fk,kbh_fk,gsbh_fk ) dh";
		sql = sql + " on ddh.npp_fk = dh.npp_fk and ddh.kbh_fk = dh.kbh_fk and ddh.gsbh_fk = dh.gsbh_fk) ds inner join kenhbanhang kbh on  kbh.pk_seq = ds.kbh_fk inner join giamsatbanhang gsbh on  gsbh.pk_seq = ds.gsbh_fk";
		sql = sql + " inner join donvikinhdoanh dvkd on dvkd.pk_seq = gsbh.dvkd_fk inner join nhaphanphoi npp on npp.pk_seq = ds.npp_fk inner join khuvuc kv on kv.pk_seq = npp.khuvuc_fk inner join vung v on v.pk_seq = kv.vung_fk";
		sql = sql + " inner join (select case when ctpri.nhapp_fk is null then ctsec.nhapp_fk else ctpri.nhapp_fk end  as npp_fk, case when ctpri.thang is null then ctsec.thang else ctpri.thang end as thang,";
		sql = sql + " case when ctpri.nam is null then ctsec.nam else ctpri.nam end as nam , case when ctpri.dvkd_fk is null then ctsec.dvkd_fk else ctpri.dvkd_fk end  as dvkd_fk , case when ctpri.kenh_fk is null then ctsec.kenh_fk else ctpri.kenh_fk end  as kbh_fk,";
		sql = sql + " case when ctpri.chitieu is null then 0 else ctpri.chitieu end  as chitieupri, case when ctsec.chitieu is null then 0 else ctsec.chitieu end  as chitieusec from (select thang, nam, ctprinpp.nhapp_fk, kenh_fk, ctpri.dvkd_fk, ctprinpp.chitieu ";
		sql = sql + " from chitieu ctpri inner join chitieu_nhapp ctprinpp on ctpri.pk_seq = ctprinpp.chitieu_fk where ctpri.thang =11 and ctpri.nam =2011) ctpri FULL OUTER JOIN (select thang,nam, ctsecnpp.nhapp_fk, kenh_fk, ctsec.dvkd_fk, ctsecnpp.chitieu from ";
		sql = sql + " CHITIEU_SEC ctsec inner join CHITIEU_NHAPP_SEC ctsecnpp on ctsec.pk_seq = ctsecnpp.CHITIEU_SEC_FK where ctsec.thang =11 and ctsec.nam =2011) ctsec on ctpri.thang =ctsec.thang and ctpri.nam = ctsec.nam and ctpri.nhapp_fk = ctsec.nhapp_fk and ctpri.dvkd_fk = ctsec.dvkd_fk";
		sql = sql + " ) ct on ct.npp_fk = ds.npp_fk and ct.kbh_fk = ds.kbh_fk and dvkd.pk_seq = ct.dvkd_fk left join (select case when ddh.npp_fk is null then dh.npp_fk else ddh.npp_fk end as npp_fk, case when ddh.gsbh_fk is null then dh.gsbh_fk else ddh.gsbh_fk end as gsbh_fk,";
		sql = sql + " case when ddh.kbh_fk is null then dh.kbh_fk else ddh.kbh_fk end as kbh_fk, case when ddh.giatrimua is null then 0 else ddh.giatrimua end as giatrimua, case when dh.giatriban is null then 0 else dh.giatriban end as giatriban";
		sql = sql + " from ( select npp_fk,kbh_fk,gsbh_fk,sum(convert(numeric(18),sotienavat))as giatrimua from dondathang where cast(trangthai as int)>2 and loaidonhang ='1' and substring(ngaydat,1,4)='2010' and substring(ngaydat,6,2) ='11' group by npp_fk,kbh_fk,gsbh_fk ) ddh FULL OUTER JOIN (";
		sql = sql + " select npp_fk,kbh_fk,gsbh_fk,sum(tonggiatri) as giatriban from donhang where substring(ngaynhap,1,4)='2010' and substring(ngaynhap,6,2) ='11' and trangthai >= 1 group by npp_fk,kbh_fk,gsbh_fk ) dh on ddh.npp_fk = dh.npp_fk and ddh.kbh_fk = dh.kbh_fk and ddh.gsbh_fk = dh.gsbh_fk";
		sql = sql + " ) namtruoc on namtruoc.npp_fk = ds.npp_fk and namtruoc.kbh_fk = ds.kbh_fk and namtruoc.gsbh_fk = ds.gsbh_fk left join ( select case when ddh.npp_fk is null then dh.npp_fk else ddh.npp_fk end as npp_fk,";
		sql = sql + " case when ddh.gsbh_fk is null then dh.gsbh_fk else ddh.gsbh_fk end as gsbh_fk, case when ddh.kbh_fk is null then dh.kbh_fk else ddh.kbh_fk end as kbh_fk, case when ddh.giatrimua is null then 0 else ddh.giatrimua end as giatrimua,";
		sql = sql + " case when dh.giatriban is null then 0 else dh.giatriban end as giatriban from ( select npp_fk,kbh_fk,gsbh_fk,sum(convert(numeric(18),sotienavat))as giatrimua from dondathang where cast(trangthai as int)>2 and loaidonhang ='1' and substring(ngaydat,1,4)='2011' group by npp_fk,kbh_fk,gsbh_fk ) ddh";
		sql = sql + " FULL OUTER JOIN ( select npp_fk,kbh_fk,gsbh_fk,sum(tonggiatri) as giatriban from donhang where substring(ngaynhap,1,4)='2011' and trangthai >= 1 group by npp_fk,kbh_fk,gsbh_fk ) dh";
		sql = sql + " on ddh.npp_fk = dh.npp_fk and ddh.kbh_fk = dh.kbh_fk and ddh.gsbh_fk = dh.gsbh_fk ) namht on  namht.npp_fk = ds.npp_fk and namht.kbh_fk = ds.kbh_fk and namht.gsbh_fk = ds.gsbh_fk";
		sql = sql + " left join (select case when ddh.npp_fk is null then dh.npp_fk else ddh.npp_fk end as npp_fk, case when ddh.gsbh_fk is null then dh.gsbh_fk else ddh.gsbh_fk end as gsbh_fk,";
		sql = sql + " case when ddh.kbh_fk is null then dh.kbh_fk else ddh.kbh_fk end as kbh_fk, case when ddh.giatrimua is null then 0 else ddh.giatrimua end as giatrimua, case when dh.giatriban is null then 0 else dh.giatriban end as giatriban";
		sql = sql + " from ( select npp_fk,kbh_fk,gsbh_fk,sum(convert(numeric(18),sotienavat))as giatrimua from dondathang where cast(trangthai as int)>2 and loaidonhang ='1' and substring(ngaydat,1,4)='2010' group by npp_fk,kbh_fk,gsbh_fk ) ddh";
		sql = sql + " FULL OUTER JOIN ( select npp_fk,kbh_fk,gsbh_fk,sum(tonggiatri) as giatriban from donhang where substring(ngaynhap,1,4)='2010' and trangthai >= 1 group by npp_fk,kbh_fk,gsbh_fk ) dh";
		sql = sql + " on ddh.npp_fk = dh.npp_fk and ddh.kbh_fk = dh.kbh_fk and ddh.gsbh_fk = dh.gsbh_fk) namcu on  namcu.npp_fk = ds.npp_fk and namcu.kbh_fk = ds.kbh_fk and namcu.gsbh_fk = ds.gsbh_fk";
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
				cells.setColumnWidth(19, 25.0f);
				//set do rong cho dong
				for(int j = 12; j <= 18; j++)
					cells.setRowHeight(j, 14.0f);
				
				Cell cell = null;
				while(rs.next())// lap den cuoi bang du lieu
				{ 
				
					//lay tu co so du lieu, gan bien
					String Channel = rs.getString("Channel");
					String Region=rs.getString("Vung");
					String Area =rs.getString("Khuvuc");			
					String Distributor = rs.getString("Business_unit");		
					String MonthlyAvgSecondSales = rs.getString("MonthlyTargetPrimary");
					String phantram=rs.getString("MonthlyTargetSecondary");
					String Target = rs.getString("MonthlyAchievedPrimary");
									
					cell = cells.getCell("EA" + Integer.toString(i)); cell.setValue(Channel);
					cell = cells.getCell("EB" + Integer.toString(i)); cell.setValue(Region);
					cell = cells.getCell("EC" + Integer.toString(i)); cell.setValue(Area);
					cell = cells.getCell("ED" + Integer.toString(i)); cell.setValue(Distributor);
					cell = cells.getCell("EE" + Integer.toString(i)); cell.setValue(Float.parseFloat(MonthlyAvgSecondSales));
					cell = cells.getCell("EF" + Integer.toString(i)); cell.setValue(Float.parseFloat(phantram));
					cell = cells.getCell("EG" + Integer.toString(i)); cell.setValue(Float.parseFloat(Target));
					
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
	    int index = pivotTables.add("=EA1:EG" + pos,"A12","PivotTableDemo");
         System.out.println("index:"+index);
	    //Accessing the instance of the newly added PivotTable
	    PivotTable pivotTable = pivotTables.get(index);//truyen index

	    pivotTable.setRowGrand(false);
	    pivotTable.setColumnGrand(false);
	    pivotTable.setAutoFormat(false);
	    //Setting the PivotTable autoformat type.
	    //pivotTable.setAutoFormatType(PivotTableAutoFormatType.REPORT6);
 
	   
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 0); 	pivotTable.getRowFields().get(0).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 1);	pivotTable.getRowFields().get(1).setAutoSubtotals(true);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 2);	pivotTable.getRowFields().get(2).setAutoSubtotals(true);
	    
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 3);	pivotTable.getRowFields().get(3).setAutoSubtotals(true);
	   
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 4);
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 5);pivotTable.getDataFields().get(1).setDataDisplayFormat(PivotFieldDataDisplayFormat.PERCENTAGE_OF_TOTAL);
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 6);
	  
	    
	    pivotTable.addFieldToArea(PivotFieldType.COLUMN, pivotTable.getDataField());
	    pivotTable.getDataFields().get(0).setDisplayName("Monthly Avg second sales");
	    pivotTable.getDataFields().get(1).setDisplayName("%");
	    pivotTable.getDataFields().get(2).setDisplayName("Target");
	  
	    
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
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}
