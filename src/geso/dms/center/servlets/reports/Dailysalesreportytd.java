package geso.dms.center.servlets.reports;

import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.ReportWithPivot;
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
import org.openswing.swing.table.columns.client.PercentageColumn;


import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Font;
import com.aspose.cells.PivotFieldDataDisplayFormat;
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTableAutoFormatType;
import com.aspose.cells.PivotTables;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.aspose.cells.Cell;


public class Dailysalesreportytd extends HttpServlet {
	private static final long serialVersionUID = 1L;
	   Utility util=new Utility();
	   String tungay="";
		String denngay="";
		String userTen="";
		boolean bfasle=true; 
       
		String[] dataClmNames = new String[] {"Channel", "Region", "Area", "BusinessUnit"
	    		, "MonthlyTargetPrimary", "MonthlyTargetSecondary", "MonthlyAchievedPrimary"
	    		, "MonthlyAchievedSecondary", "PerCentMTDPrimary", "PerCentMTDSecondary"
	    		, "MonthlyArchivePrimaryLastYear", "MonthlyArchiveSecondLastYear", "PerCentGrowthMTDPrimary"
	    		, "PerCentGrowthMTDSecondary", "YTDArchivePrimary", "YTDArchiveSecond"
	    		, "YTDArchivePrimaryLastYear", "YTDArchiveSecondLastYear", "PerCentGrowthYTDPrimary"
	    		, "PerCentGrowthYTDSecondary"
	    		, "Province", "DistributorCode", "Distributor", "SalesSup"};
		
	    String[] excelClmNames = new String[]{"Channel,row,false", "Region,row,false", "Area,row,false", "Business Unit,row,false"
	    		, "Monthly Target Primary,data", "Monthly Target Secondary,data", "Monthly Achieved Primary,data"
	    		, "Monthly Achieved Secondary,data", "%MTD Primary,data", "%MTD Secondary,data"
	    		, "Monthly Archive Primary LastYear,data", "Monthly Archive Second LastYear,data", "%Growth MTD Primary,data"
	    		, "%Growth MTD Secondary,data", "YTD Archive Primary,data", "YTD Archive Second,data"
	    		, "YTD Archive Primary Last Year,data", "YTD Archive Second Last Year,data", "%Growth YTD Primary,data"
	    		, "%Growth YTD Secondary,data"
	    		, "Province,row,false", "Distributor Code,row,false", "Distributor,row,false", "Sales Sup,row,false"};
	    
	    String[] displayClmNames = new String[]{"Monthly Target Primary,data", "Monthly Target Secondary,data", "Monthly Achieved Primary,data"
	    		, "Monthly Achieved Secondary,data", "%MTD Primary,data", "%MTD Secondary,data"
	    		, "Monthly Archive Primary LastYear,data", "Monthly Archive Second LastYear,data", "%Growth MTD Primary,data"
	    		, "%Growth MTD Secondary,data", "YTD Archive Primary,data", "YTD Archive Second,data"
	    		, "YTD Archive Primary Last Year,data", "YTD Archive Second Last Year,data", "%Growth YTD Primary,data"
	    		, "%Growth YTD Secondary,data"};
    public Dailysalesreportytd() {
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
		request.setCharacterEncoding("UTF-8");
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
		String nextJSP = "/AHF/pages/Center/DailySalesReportYTD.jsp";
		response.sendRedirect(nextJSP);
	}

	private void reConfixPivot(Workbook workbook, OutputStream out, PivotTable pivotTable, String[] excelClmNames, String[] displayClmNames)
	{
		//ReportWithPivot rp = new ReportWithPivot();
		//rp.setCellStyle(workbook, "A9", Color.BLUE, true, 10);
		//pivotTable.getDataFields().get(1).setDataDisplayFormat(PivotFieldDataDisplayFormat.PERCENTAGE_OF_TOTAL);
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
		
		//cells.setColumnWidth(19, 40.0f);
		//cells.getCell("A9").applyStyle(arg0, arg1)
	    
		//--muon chinh lai pivot thi viet lenh phia tren va luon de lai 2 dong lenh sau
		pivotTable.addFieldToArea(PivotFieldType.COLUMN, pivotTable.getDataField());
		//Saving the Excel file
   	 	try {
			workbook.save(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OutputStream out = response.getOutputStream(); 
		try
		    {
			HttpSession session = request.getSession();
			 userTen = (String)session.getAttribute("userTen");
		 tungay=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
		 denngay=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
	
	    	response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "attachment; filename=DailySalesReportYTD.xls");
	        
	        ReportWithPivot rp = new ReportWithPivot();
	        rp.setWorkbook(new Workbook());
			boolean b = rp.createReport(out, tungay, denngay, userTen, "Daily Sales Report (YTD)", dataClmNames, excelClmNames, displayClmNames, getSQL());
			reConfixPivot(rp.getWorkbook(), out, rp.getPivotTable(), excelClmNames, displayClmNames);
		     if(b == false){
		    	String loi="Chua co du lieu trong khoang thoi gian nay, vui long chon lai thoi gian lay bao cao!";
		    	
		    	 
		 		session.setAttribute("loi", loi);
		 		session.setAttribute("tungay", tungay);
		 		session.setAttribute("denngay", denngay);
		    	 session.setAttribute("userTen", userTen);
		 		String nextJSP = "/AHF/pages/Center/DailySalesReportYTD.jsp";
		 		response.sendRedirect(nextJSP);
		     }

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
	
		
	private String getSQL() 
	{
		String test = "select 'GT' as Channel, 'Nam' as Region, 'TPHCM' as Area, 'GESO' as BusinessUnit"
			+", 32 as MonthlyTargetPrimary, 4 as MonthlyTargetSecondary, 9 as MonthlyAchievedPrimary"
+", 2 as MonthlyAchievedSecondary, 2 PerCentMTDPrimary, 20 as PerCentMTDSecondary"
+", 9 as MonthlyArchivePrimaryLastYear, 93 as MonthlyArchiveSecondLastYear"
+", 23 as PerCentGrowthMTDPrimary, 93 as PerCentGrowthMTDSecondary"
+", 36 as YTDArchivePrimary, 82 as YTDArchiveSecond, 2 as YTDArchivePrimaryLastYear"
+", 83 as YTDArchiveSecondLastYear, 91 as PerCentGrowthYTDPrimary"
+", 89 as PerCentGrowthYTDSecondary, 'hcm' as Province, 'kjaka' as DistributorCode, 'kjfdka' as Distributor, 'jkjkjk' as SalesSup";
		
		System.out.println("daily sale report ytd: " +test);
		
			String sql = "select kbh.ten as Channel, dvkd.donvikinhdoanh as Business_unit, v.ten as Vung, kv.ten as Khuvuc, gsbh.ten as Sales_sup, npp.ten as Distributor, ct.chitieupri as MonthlyTargetPrimary,"
		 + " ct.chitieusec as MonthlyTargetSecondary, ds.giatrimua as MonthlyAchievedPrimary, ds.giatriban as MonthlyAchievedSecondary, case when ct.chitieupri > 0 then 100*ds.giatrimua/ct.chitieupri else 0 end as phantramMTD_Primary,"
		 + " case when ct.chitieusec > 0 then 100*ds.giatriban/ct.chitieusec else 0 end as phantramMTD_Secondary, case when namtruoc.giatrimua is null then 0 else namtruoc.giatrimua end as MonthlyArchivePrimaryLastYear,"
		 + " case when namtruoc.giatriban is null then 0 else namtruoc.giatriban end as MonthlyArchiveSecondLastYear, case when namtruoc.giatrimua = 0 then 0 else 100*ds.giatrimua/namtruoc.giatrimua end as phantramGrowth_MTD_Primary,"
		 + " case when namtruoc.giatriban = 0 then 0 else 100*ds.giatriban/namtruoc.giatriban end as phantramGrowth_MTD_Secondary, namht.giatriban as YTDArchivePrimary, namht.giatrimua as YTDArchiveSecond,"
		 + " namcu.giatriban as YTDArchivePrimaryLastYear, namcu.giatrimua as YTDArchiveSecondLastYear, case when namcu.giatriban =0 then 0 else 100*namht.giatriban/namcu.giatriban end as phantramGrowth_YTD_Primary,"
		 + " case when namcu.giatrimua =0 then 0 else 100*namht.giatrimua/namcu.giatrimua end as phantramGrowth_YTD_Secondary from (select case when ddh.npp_fk is null then dh.npp_fk else ddh.npp_fk end as npp_fk,"
		 + " case when ddh.gsbh_fk is null then dh.gsbh_fk else ddh.gsbh_fk end as gsbh_fk, case when ddh.kbh_fk is null then dh.kbh_fk else ddh.kbh_fk end as kbh_fk, case when ddh.giatrimua is null then 0 else ddh.giatrimua end as giatrimua,"
		 + " case when dh.giatriban is null then 0 else dh.giatriban end as giatriban from ( select npp_fk,kbh_fk,gsbh_fk,sum(convert(numeric(18),sotienavat))as giatrimua from dondathang where cast(trangthai as int)>2 and loaidonhang ='1' and substring(ngaydat,1,4)='2011' and substring(ngaydat,6,2) ='11' group by npp_fk,kbh_fk,gsbh_fk ) ddh"
		 + " FULL OUTER JOIN (select npp_fk,kbh_fk,gsbh_fk,sum(tonggiatri) as giatriban from donhang where substring(ngaynhap,1,4)='2011' and substring(ngaynhap,6,2) ='11' and trangthai >= 1 group by npp_fk,kbh_fk,gsbh_fk ) dh"
		 + " on ddh.npp_fk = dh.npp_fk and ddh.kbh_fk = dh.kbh_fk and ddh.gsbh_fk = dh.gsbh_fk) ds inner join kenhbanhang kbh on  kbh.pk_seq = ds.kbh_fk inner join giamsatbanhang gsbh on  gsbh.pk_seq = ds.gsbh_fk"
		 + " inner join donvikinhdoanh dvkd on dvkd.pk_seq = gsbh.dvkd_fk inner join nhaphanphoi npp on npp.pk_seq = ds.npp_fk inner join khuvuc kv on kv.pk_seq = npp.khuvuc_fk inner join vung v on v.pk_seq = kv.vung_fk"
		 + " inner join (select case when ctpri.nhapp_fk is null then ctsec.nhapp_fk else ctpri.nhapp_fk end  as npp_fk, case when ctpri.thang is null then ctsec.thang else ctpri.thang end as thang,"
		 + " case when ctpri.nam is null then ctsec.nam else ctpri.nam end as nam , case when ctpri.dvkd_fk is null then ctsec.dvkd_fk else ctpri.dvkd_fk end  as dvkd_fk , case when ctpri.kenh_fk is null then ctsec.kenh_fk else ctpri.kenh_fk end  as kbh_fk,"
		 + " case when ctpri.chitieu is null then 0 else ctpri.chitieu end  as chitieupri, case when ctsec.chitieu is null then 0 else ctsec.chitieu end  as chitieusec from (select thang, nam, ctprinpp.nhapp_fk, kenh_fk, ctpri.dvkd_fk, ctprinpp.chitieu "
		 + " from chitieu ctpri inner join chitieu_nhapp ctprinpp on ctpri.pk_seq = ctprinpp.chitieu_fk where ctpri.thang =11 and ctpri.nam =2011) ctpri FULL OUTER JOIN (select thang,nam, ctsecnpp.nhapp_fk, kenh_fk, ctsec.dvkd_fk, ctsecnpp.chitieu from "
		 + " CHITIEU_SEC ctsec inner join CHITIEU_NHAPP_SEC ctsecnpp on ctsec.pk_seq = ctsecnpp.CHITIEU_SEC_FK where ctsec.thang =11 and ctsec.nam =2011) ctsec on ctpri.thang =ctsec.thang and ctpri.nam = ctsec.nam and ctpri.nhapp_fk = ctsec.nhapp_fk and ctpri.dvkd_fk = ctsec.dvkd_fk"
		 + " ) ct on ct.npp_fk = ds.npp_fk and ct.kbh_fk = ds.kbh_fk and dvkd.pk_seq = ct.dvkd_fk left join (select case when ddh.npp_fk is null then dh.npp_fk else ddh.npp_fk end as npp_fk, case when ddh.gsbh_fk is null then dh.gsbh_fk else ddh.gsbh_fk end as gsbh_fk,"
		 + " case when ddh.kbh_fk is null then dh.kbh_fk else ddh.kbh_fk end as kbh_fk, case when ddh.giatrimua is null then 0 else ddh.giatrimua end as giatrimua, case when dh.giatriban is null then 0 else dh.giatriban end as giatriban"
		 + " from ( select npp_fk,kbh_fk,gsbh_fk,sum(convert(numeric(18),sotienavat))as giatrimua from dondathang where cast(trangthai as int)>2 and loaidonhang ='1' and substring(ngaydat,1,4)='2010' and substring(ngaydat,6,2) ='11' group by npp_fk,kbh_fk,gsbh_fk ) ddh FULL OUTER JOIN ("
		 + " select npp_fk,kbh_fk,gsbh_fk,sum(tonggiatri) as giatriban from donhang where substring(ngaynhap,1,4)='2010' and substring(ngaynhap,6,2) ='11' and trangthai >= 1 group by npp_fk,kbh_fk,gsbh_fk ) dh on ddh.npp_fk = dh.npp_fk and ddh.kbh_fk = dh.kbh_fk and ddh.gsbh_fk = dh.gsbh_fk"
		 + " ) namtruoc on namtruoc.npp_fk = ds.npp_fk and namtruoc.kbh_fk = ds.kbh_fk and namtruoc.gsbh_fk = ds.gsbh_fk left join ( select case when ddh.npp_fk is null then dh.npp_fk else ddh.npp_fk end as npp_fk,"
		 + " case when ddh.gsbh_fk is null then dh.gsbh_fk else ddh.gsbh_fk end as gsbh_fk, case when ddh.kbh_fk is null then dh.kbh_fk else ddh.kbh_fk end as kbh_fk, case when ddh.giatrimua is null then 0 else ddh.giatrimua end as giatrimua,"
		 + " case when dh.giatriban is null then 0 else dh.giatriban end as giatriban from ( select npp_fk,kbh_fk,gsbh_fk,sum(convert(numeric(18),sotienavat))as giatrimua from dondathang where cast(trangthai as int)>2 and loaidonhang ='1' and substring(ngaydat,1,4)='2011' group by npp_fk,kbh_fk,gsbh_fk ) ddh"
		 + " FULL OUTER JOIN ( select npp_fk,kbh_fk,gsbh_fk,sum(tonggiatri) as giatriban from donhang where substring(ngaynhap,1,4)='2011' and trangthai >= 1 group by npp_fk,kbh_fk,gsbh_fk ) dh"
		 + " on ddh.npp_fk = dh.npp_fk and ddh.kbh_fk = dh.kbh_fk and ddh.gsbh_fk = dh.gsbh_fk ) namht on  namht.npp_fk = ds.npp_fk and namht.kbh_fk = ds.kbh_fk and namht.gsbh_fk = ds.gsbh_fk"
		 + " left join (select case when ddh.npp_fk is null then dh.npp_fk else ddh.npp_fk end as npp_fk, case when ddh.gsbh_fk is null then dh.gsbh_fk else ddh.gsbh_fk end as gsbh_fk,"
		 + " case when ddh.kbh_fk is null then dh.kbh_fk else ddh.kbh_fk end as kbh_fk, case when ddh.giatrimua is null then 0 else ddh.giatrimua end as giatrimua, case when dh.giatriban is null then 0 else dh.giatriban end as giatriban"
		 + " from ( select npp_fk,kbh_fk,gsbh_fk,sum(convert(numeric(18),sotienavat))as giatrimua from dondathang where cast(trangthai as int)>2 and loaidonhang ='1' and substring(ngaydat,1,4)='2010' group by npp_fk,kbh_fk,gsbh_fk ) ddh"
		 + " FULL OUTER JOIN ( select npp_fk,kbh_fk,gsbh_fk,sum(tonggiatri) as giatriban from donhang where substring(ngaynhap,1,4)='2010' and trangthai >= 1 group by npp_fk,kbh_fk,gsbh_fk ) dh"
		 + " on ddh.npp_fk = dh.npp_fk and ddh.kbh_fk = dh.kbh_fk and ddh.gsbh_fk = dh.gsbh_fk) namcu on  namcu.npp_fk = ds.npp_fk and namcu.kbh_fk = ds.kbh_fk and namcu.gsbh_fk = ds.gsbh_fk";
			
			return test;
	}
	
}
