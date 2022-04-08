package geso.dms.distributor.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTables;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class DailySalesDistributorSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;   
    public DailySalesDistributorSvl() {
        super();
        
    }    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
	    Utility util = new Utility();
	    
	    IStockintransit obj = new Stockintransit();
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    obj.setuserId(userId);
	    obj.init();	    
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Distributor/DailySalesReportDistributor.jsp";
		response.sendRedirect(nextJSP);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
	    IStockintransit obj = new Stockintransit(); 	    
	    Utility util = new Utility();
	    
	    String userId = (String) session.getAttribute("userId"); 
	    if(userId ==null)
	    	userId ="";
	    obj.setuserId(userId);	    
	    
	    String userTen = (String) session.getAttribute("userTen");	    	
	    if(userTen==null)
	    	userTen="";
        obj.setuserTen(userTen);        
        
   	 	String nppId=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
   	 	if(nppId ==null) 
   	 		nppId ="";
   	 	obj.setnppId(nppId); 
   	 	
	   	 String kenhId=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")));
	   	 if(kenhId == null)
	   		 kenhId ="";
	   	 obj.setkenhId(kenhId);
	   	 
	   	 String dvkdId=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
	   	 if(dvkdId == null)
	   		 dvkdId ="";
	   	 obj.setdvkdId(dvkdId);	   	 
	   	 
	   	 obj.settungay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays"))));
	   	 obj.setdenngay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays"))));
	   	 
	   	 String []fieldsHien = request.getParameterValues("fieldsHien");
		 obj.setFieldShow(fieldsHien);
		 
		 Utility Ult = new Utility();
		 nppId = Ult.getIdNhapp(userId);
		 obj.setnppId(nppId);
		 obj.setnppTen(Ult.getTenNhaPP());
		 
		 
		 String sql ="";
		 String action=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));		 
		 if(action.equals("create"))
		 {
			try{						
				response.setContentType("application/vnd.ms-excel");
		        response.setHeader("Content-Disposition", "attachment; filename=DailySalesReport(npp).xls");
		        OutputStream out = response.getOutputStream();
		        String query =  setQuery(sql,obj, userId);
		        CreatePivotTable(out,obj,query);		        
			}catch(Exception ex){
				obj.setMsg(ex.getMessage());
			}
		 }
		obj.init();	    
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Distributor/DailySalesReportDistributor.jsp";
		response.sendRedirect(nextJSP);
	}
	private String setQuery(String sql, IStockintransit obj, String userId) {
		String fromYear = obj.gettungay().substring(0, 4);
		String fromMonth = obj.gettungay().substring(5, 7);
		
	/*	String query =  "select kbh.ten as Channel, qh.ten as Province, dvkd.donvikinhdoanh as Business_unit," +
				"		v.ten as Vung, kv.ten as Khuvuc, gsbh.ten as Sales_sup," +
				"		npp.ten as Distributor,npp.pk_seq as DisCode," +
				"		isnull(ct.chitieupri,0) as MonthlyTargetPrimary," +
				"		isnull(ct.chitieusec,0) as MonthlyTargetSecondary," +
				"		isnull(ds.giatrimua,0) as MonthlyAchievedPrimary," +
				"		isnull(ds.giatriban, 0) as MonthlyAchievedSecondary," +
				"		case when ct.chitieupri > 0 then cast(substring(convert(nvarchar(20),100*ds.giatrimua/ct.chitieupri),1,5) as float)" +
				"			else 0.0" +
				"		end as phantramMTD_Primary," +
				"		case when ct.chitieusec > 0 then cast(substring(convert(nvarchar(20),100*ds.giatriban/ct.chitieusec),1,5) as float)" +
				"			else 0.0" +
				"		end as phantramMTD_Secondary  " +
				"from (" +
				"		select  case when ddh.npp_fk is null then dh.npp_fk else ddh.npp_fk end as npp_fk," +
				"				case when ddh.gsbh_fk is null then dh.gsbh_fk else ddh.gsbh_fk end as gsbh_fk," +
				"				case when ddh.kbh_fk is null then dh.kbh_fk else ddh.kbh_fk end as kbh_fk," +
				"				case when ddh.giatrimua is null then 0 else ddh.giatrimua end as giatrimua," +
				"				case when dh.giatriban is null then 0 else dh.giatriban end as giatriban" +
				"		from (" +
				"				select nh.npp_fk,nh.kbh_fk,gsbh.pk_seq as 'gsbh_fk',sum(convert(numeric(18),sotienavat))as giatrimua" +
				"				from nhaphang nh" +
				"					inner join giamsatbanhang gsbh on nh.dvkd_fk = gsbh.dvkd_fk and nh.kbh_fk = gsbh.kbh_fk" +
				"					inner join NHAPP_GIAMSATBH nppgs on nppgs.npp_fk = nh.npp_fk and nppgs.gsbh_fk = gsbh.pk_seq "+
				"				where gsbh.trangthai ='1' and nh.ngaynhan >= '"+ obj.gettungay() +"' and nh.ngaynhan <='" + obj.getdenngay() +"' and nh.npp_fk ='"+ obj.getnppId() +"'" +
				"				group by nh.npp_fk,nh.kbh_fk,gsbh.pk_seq" +
				"			) ddh" +
				"		    FULL OUTER JOIN (" +
				"		                    select dh1.npp_fk,dh1.kbh_fk,dh1.gsbh_fk,sum(dhsp.soluong * dhsp.giamua) as giatriban" +
				"			    	        from donhang dh1 inner join donhang_sanpham dhsp on dhsp.donhang_fk = dh1.pk_seq " +
				"		                    where dh1.ngaynhap >='" + obj.gettungay() +"' and dh1.ngaynhap <='" + obj.getdenngay() +"' and dh1.npp_fk ='"+ obj.getnppId() +"' and dh1.trangthai = 1" +
				"			                group by dh1.npp_fk,dh1.kbh_fk,dh1.gsbh_fk" +
				"						) dh on ddh.npp_fk = dh.npp_fk and ddh.kbh_fk = dh.kbh_fk and ddh.gsbh_fk = dh.gsbh_fk" +
				"	) ds" +
				"	inner join kenhbanhang kbh on  kbh.pk_seq = ds.kbh_fk" +
				"	inner join giamsatbanhang gsbh on  gsbh.pk_seq = ds.gsbh_fk" +
				"	inner join donvikinhdoanh dvkd on dvkd.pk_seq = gsbh.dvkd_fk" +
				"	inner join nhaphanphoi npp on npp.pk_seq = ds.npp_fk" +
				"	left join khuvuc kv on kv.pk_seq = npp.khuvuc_fk" +
				"	left join vung v on v.pk_seq = kv.vung_fk" +
				"	left join" +
				"	(" +
				"		select  case when ctpri.nhapp_fk is null then ctsec.nhapp_fk 		 else ctpri.nhapp_fk end  as npp_fk," +
				"				case when ctpri.thang is null then ctsec.thang else ctpri.thang end as thang," +
				"				case when ctpri.nam is null then ctsec.nam else ctpri.nam end as nam," +
				"				case when ctpri.dvkd_fk is null then ctsec.dvkd_fk else ctpri.dvkd_fk end  as dvkd_fk ," +
				"				case when ctpri.kenh_fk is null then ctsec.kenh_fk else ctpri.kenh_fk end  as kbh_fk," +
				"				case when ctpri.chitieu is null then 0 else ctpri.chitieu end  as chitieupri," +
				"				case when ctsec.chitieu is null then 0 else ctsec.chitieu end  as chitieusec" +
				"		from (" +
				"				select thang, nam, ctprinpp.nhapp_fk, kenh_fk, ctpri.dvkd_fk, ctprinpp.chitieu" +
				"				from chitieu ctpri inner join chitieu_nhapp ctprinpp on ctpri.pk_seq = ctprinpp.chitieu_fk" +
				"				where ctpri.thang ='" + fromMonth +"' and ctpri.nam ='" + fromYear +"' and ctprinpp.nhapp_fk ='"+ obj.getnppId() +"' " +
				"			) ctpri" +
				"			FULL OUTER JOIN(" +
				"				select thang,nam, ctsecnpp.nhapp_fk, kenh_fk, ctsec.dvkd_fk, ctsecnpp.chitieu" +
				"				from CHITIEU_SEC ctsec" +
				"					inner join CHITIEU_NHAPP_SEC ctsecnpp 	on ctsec.pk_seq = ctsecnpp.CHITIEU_SEC_FK" +
				"				where ctsec.thang ='" + fromMonth +"' and ctsec.nam ='" + fromYear +"' and ctsecnpp.nhapp_fk ='"+ obj.getnppId() +"' " +
				"			) ctsec" +
				"					on ctpri.thang =ctsec.thang and ctpri.nam = ctsec.nam and ctpri.nhapp_fk = ctsec.nhapp_fk and ctpri.dvkd_fk = ctsec.dvkd_fk" +
				"	) ct on ct.npp_fk = ds.npp_fk and ct.kbh_fk = ds.kbh_fk and dvkd.pk_seq = ct.dvkd_fk" +
				"	left join quanhuyen qh on qh.pk_seq = npp.quanhuyen_fk  " +
				"where npp.pk_seq =" + obj.getnppId() ;
				*/
		String query = "select kbh.ten as Channel, qh.ten as Province, dvkd.donvikinhdoanh as Business_unit," +
		"		v.ten as Vung, kv.ten as Khuvuc," +
		"		npp.ten as Distributor,npp.pk_seq as DisCode," +
		"		isnull(ct.chitieupri,0) as MonthlyTargetPrimary," +
		"		isnull(ct.chitieusec,0) as MonthlyTargetSecondary," +
		"		isnull(ds.giatrimua,0) as MonthlyAchievedPrimary," +
		"		isnull(ds.giatriban, 0) as MonthlyAchievedSecondary," +
	    "     case when ct.chitieupri > 0 then isnull(cast(substring(convert(nvarchar(20),100*ds.giatrimua/ct.chitieupri),1,5) as float),0)  "+
        "     else 0.0    "+
        "     end as phantramMTD_Primary,   "+
        "     case when ct.chitieusec > 0 then isnull(cast(substring(convert(nvarchar(20),100*ds.giatriban/ct.chitieusec),1,5) as float),0)  "+
        "           else 0.0    "+
        "     end as phantramMTD_Secondary  "+  
        "     from  "+
        "(        "+                  
        "     select  case when ctpri.nhapp_fk is null then ctsec.nhapp_fk             else ctpri.nhapp_fk end  as npp_fk, "+
        "           case when ctpri.thang is null then ctsec.thang else ctpri.thang end as thang, "+
        "           case when ctpri.nam is null then ctsec.nam else ctpri.nam end as nam, "+
        "           case when ctpri.dvkd_fk is null then ctsec.dvkd_fk else ctpri.dvkd_fk end  as dvkd_fk , "+
        "           case when ctpri.kenh_fk is null then ctsec.kenh_fk else ctpri.kenh_fk end  as kbh_fk, "+
        "           case when ctpri.chitieu is null then 0 else ctpri.chitieu end  as chitieupri, "+
        "           case when ctsec.chitieu is null then 0 else ctsec.chitieu end  as chitieusec "+
        "     from ( "+
        "           select thang, nam, ctprinpp.nhapp_fk, kenh_fk, ctpri.dvkd_fk, ctprinpp.chitieu "+
        "           from chitieu ctpri inner join chitieu_nhapp ctprinpp on ctpri.pk_seq = ctprinpp.chitieu_fk "+
        "           where ctpri.thang ='"+ fromMonth +"' and ctpri.nam ='"+ fromYear +"' and ctprinpp.nhapp_fk ='"+ obj.getnppId() +"' "+
        "     ) ctpri "+
        "     FULL OUTER JOIN( "+
        "           select thang,nam, ctsecnpp.nhapp_fk, kenh_fk, ctsec.dvkd_fk, ctsecnpp.chitieu "+
        "           from CHITIEU_SEC ctsec "+
        "                 inner join CHITIEU_NHAPP_SEC ctsecnpp     on ctsec.pk_seq = ctsecnpp.CHITIEU_SEC_FK "+
        "           where ctsec.thang ='"+ fromMonth +"' and ctsec.nam ='"+ fromYear +"'and ctsecnpp.nhapp_fk ='"+ obj.getnppId() +"' "+
        "     ) ctsec "+
        "                 on ctpri.thang =ctsec.thang and ctpri.nam = ctsec.nam and ctpri.nhapp_fk = ctsec.nhapp_fk and ctpri.dvkd_fk = ctsec.dvkd_fk "+
        "     ) ct "+
        "     inner join kenhbanhang kbh on  kbh.pk_seq = ct.kbh_fk "+
        "     inner join donvikinhdoanh dvkd on dvkd.pk_seq = ct.dvkd_fk "+
        "     inner join nhaphanphoi npp on npp.pk_seq = ct.npp_fk and npp.pk_seq ='"+obj.getnppId() +"'"+
        "     left join khuvuc kv on kv.pk_seq = npp.khuvuc_fk "+
        "     left join vung v on v.pk_seq = kv.vung_fk "+
        "     left join quanhuyen qh on qh.pk_seq = npp.quanhuyen_fk "+ 
        "     left join "+
        " (   "+
        "     select  case when ddh.npp_fk is null then dh.npp_fk else ddh.npp_fk end as npp_fk, "+
    "         case when ddh.dvkd_fk is null then dh.dvkd_fk else ddh.dvkd_fk end as dvkd_fk,    "+    
        "           case when ddh.kbh_fk is null then dh.kbh_fk else ddh.kbh_fk end as kbh_fk, "+
        "           case when ddh.giatrimua is null then 0 else ddh.giatrimua end as giatrimua, "+
        "           case when dh.giatriban is null then 0 else dh.giatriban end as giatriban "+
        "     from ( "+
        "           select nh.npp_fk,nh.kbh_fk,sp1.dvkd_fk,sum(isnull(nhsp.gianet,0)* isnull(nhsp.soluong,0)) as giatrimua "+
        "           from (select * from nhaphang where ngaynhan>'"+ obj.gettungay()+"' and ngaynhan <='"+ obj.getdenngay()+"' and npp_fk ='"+ obj.getnppId() +"') nh "+
  "     inner join nhaphang_sp nhsp on nhsp.nhaphang_fk = nh.pk_seq  "+
        "           inner join sanpham sp1 on sp1.ma = nhsp.sanpham_fk "+
        "           group by nh.npp_fk,nh.kbh_fk,sp1.dvkd_fk "+
        "     ) ddh "+
        "    FULL OUTER JOIN ( "+
        "         select dh.npp_fk,dh.kbh_fk,sp.dvkd_fk,sum(dhsp.soluong*dhsp.giamua) as giatriban "+
        "           from (select * from donhang where trangthai ='1' and ngaynhap >='"+ obj.gettungay() +"' and ngaynhap <='"+ obj.getdenngay() +"' and npp_fk ='"+ obj.getnppId() +"') dh inner join donhang_sanpham dhsp on dhsp.donhang_fk = dh.pk_seq inner join sanpham sp on sp.pk_seq = dhsp.sanpham_fk "+
        "           group by dh.npp_fk,dh.kbh_fk,sp.dvkd_fk "+
        "     ) dh on ddh.npp_fk = dh.npp_fk and ddh.kbh_fk = dh.kbh_fk and ddh.dvkd_fk = dh.dvkd_fk "+
 " ) ds on ct.npp_fk = ds.npp_fk and ct.kbh_fk = ds.kbh_fk and ds.dvkd_fk = ct.dvkd_fk ";

		//geso.dms.center.util.Utility ut = new geso.dms.center.util.Utility();
		if(obj.getkenhId().length() > 0)
			query += " and kbh.pk_seq = '"+obj.getkenhId()+"'";
		if(obj.getdvkdId().length() > 0)
			query += " and dvkd.pk_seq = '"+obj.getdvkdId()+"'";
		
		System.out.println(query);
				return query;
		
		
		
	}
	private void CreatePivotTable(OutputStream out, IStockintransit obj, String query) throws Exception
    {   
	     try{
	    	 Workbook workbook = new Workbook();			
	    	 CreateStaticHeader(workbook,obj); 
			 FillData(workbook,obj.getFieldShow(),query); 
			 workbook.save(out, 0);
	     }catch(Exception ex){
	    	 throw new Exception(ex.getMessage());
	     }
   }
	
	private void CreateStaticHeader(Workbook workbook,IStockintransit obj) throws Exception 
	{
		try{
			Worksheets worksheets = workbook.getWorksheets();
		    Worksheet worksheet = worksheets.getSheet(0);
		    worksheet.setName("DailySalesReport");
		    Cells cells = worksheet.getCells();
		    Cell cell;		       
		    cells.setRowHeight(0, 50.0f);	    
		    cell = cells.getCell("A1");	
		    ReportAPI.getCellStyle(cell,Color.RED, true, 16, "Daily Sales Report ");
		    cell = cells.getCell("A2");
		    ReportAPI.getCellStyle(cell,Color.BLUE,true,10,"From : " + obj.gettungay() + " To : " +obj.getdenngay() );
		    cell = cells.getCell("A3");
		    ReportAPI.getCellStyle(cell,Color.BLUE,true,10,"Date Create : " + obj.getDateTime()); 
		    cell = cells.getCell("A4");
		    ReportAPI.getCellStyle(cell,Color.BLUE,true,10,"User : " + obj.getuserTen());
		    
		    cell = cells.getCell("AA1"); cell.setValue("Business Unit");
		    //cell = cells.getCell("AB1"); cell.setValue("Sales Sup");
		    cell = cells.getCell("AB1"); cell.setValue("Distributor Code");   
		    cell = cells.getCell("AC1"); cell.setValue("Monthly Target Primary");
		    cell = cells.getCell("AD1"); cell.setValue("Monthly Target Second");
		    cell = cells.getCell("AE1"); cell.setValue("Monthly Archive Primary");
		    cell = cells.getCell("AF1"); cell.setValue("Monthly Archive Second");
		    cell = cells.getCell("AG1"); cell.setValue("%MTD Primary");
		    cell = cells.getCell("AH1"); cell.setValue("%MTD Second");	
			
		}catch(Exception ex){
			throw new Exception("Khong the tao duoc Header cho bao cao...");
		}
	}
	private void FillData(Workbook workbook,String[] fieldShow, String query)throws Exception 
	{
		try{
			Worksheets worksheets = workbook.getWorksheets();
		    Worksheet worksheet = worksheets.getSheet(0);
		    Cells cells = worksheet.getCells();
		    dbutils db = new dbutils();
		    ResultSet rs = db.get(query);
		    
		    int index = 2;
		    Cell cell = null;	    
		    while (rs.next()) {		    	
				cell = cells.getCell("AA" + Integer.toString(index));cell.setValue(rs.getString("Business_unit"));
			//	cell = cells.getCell("AB" + Integer.toString(index));cell.setValue(rs.getString("Sales_sup"));
				cell = cells.getCell("AB" + Integer.toString(index));cell.setValue(rs.getString("DisCode"));
				cell = cells.getCell("AC" + Integer.toString(index));cell.setValue(Float.parseFloat(rs.getString("MonthlyTargetPrimary")));
				cell = cells.getCell("AD" + Integer.toString(index));cell.setValue(Float.parseFloat(rs.getString("MonthlyTargetSecondary")));
				cell = cells.getCell("AE" + Integer.toString(index));cell.setValue(Float.parseFloat(rs.getString("MonthlyAchievedPrimary")));
				cell = cells.getCell("AF" + Integer.toString(index));cell.setValue(Float.parseFloat(rs.getString("MonthlyAchievedSecondary")));
				cell = cells.getCell("AG" + Integer.toString(index));cell.setValue(Float.parseFloat(rs.getString("phantramMTD_Primary")));
				cell = cells.getCell("AH" + Integer.toString(index));cell.setValue(Float.parseFloat(rs.getString("phantramMTD_Secondary")));
				index++;						
			}
		    if(rs!=null){
		    	rs.close();
		    	
		    }
		    if(db!=null){
		    	db.shutDown();
		    }
		   
		    ReportAPI.setHidden(workbook,16);
		    PivotTables pivotTables = worksheet.getPivotTables();
		    String pos = Integer.toString(index-1);
		    int indexPivot = pivotTables.add("=DailySalesReport!AA1:AH" + pos,"B12","PivotTable");
		    
		    
		    PivotTable pivotTable = pivotTables.get(indexPivot);
		    pivotTable.setRowGrand(true);
		    pivotTable.setColumnGrand(true);
		    pivotTable.setAutoFormat(true); 
		    Hashtable<String, Integer> selected = new Hashtable<String, Integer>();		  
		    selected.put("BusinessUnit", 0);
	//	    selected.put("SalesSup", 1);
		    selected.put("DistributorCode", 1);
		    selected.put("MonthlyTargetPrimary", 2);
		    selected.put("MonthlyTargetSecond", 3);
		    selected.put("MonthlyArchivePrimary",4);
		    selected.put("MonthlyArchiveSecond", 5);
		    selected.put("MTD", 6);
		    int dataCount = 1;
		    for(int i=0;i<fieldShow.length;i++){
		    	int value = selected.get(fieldShow[i]);
		    	switch(value){
		    	case 0: case 1: 
		    		pivotTable.addFieldToArea(PivotFieldType.ROW, value);
		    		break;
		    	case 2: case 3: case 4 :case 5:
		    		pivotTable.addFieldToArea(PivotFieldType.DATA, value);
		    		++dataCount;
		    		break;
		    	case 6:
		    		pivotTable.addFieldToArea(PivotFieldType.DATA, 6);
		    		pivotTable.addFieldToArea(PivotFieldType.DATA, 7);
		    		dataCount +=2;
		    	}
		    }
		    if(dataCount>2){
		    	pivotTable.addFieldToArea(PivotFieldType.COLUMN, pivotTable.getDataField());
		    	pivotTable.getFields(PivotFieldType.COLUMN).get(0).setDisplayName("Data");
		    }
		    
		}catch(Exception ex){
			throw new Exception("Khong the dien du lieu vao file Excel");
		}	
		
	}	
}
