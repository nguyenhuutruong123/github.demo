package geso.dms.distributor.servlets.reports;
import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTables;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;


public class StockAlarmDistributor extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public String setQuery(HttpServletRequest request, String sql){
       String  query  = "SELECT * FROM ( " +
        		"SELECT KBH.PK_SEQ AS KBHID, KBH.TEN AS KBHTEN, VUNG.PK_SEQ AS VUNGID, VUNG.TEN AS VUNGTEN,TT.TEN AS TINHTHANH," +
    			"KV.PK_SEQ AS KVID, KV.TEN AS KVTEN," +
    			"ISNULL(QH.PK_SEQ,0) AS QHID, ISNULL(QH.TEN,'') AS QUANHUYEN,NPP.PK_SEQ AS NPPID, NPP.TEN AS NPPTEN,DVKD.PK_SEQ AS DVKDID, DVKD.DONVIKINHDOANH AS DVKD," +
    			"NHAN.PK_SEQ AS NHANID, NHAN.TEN AS NHANTEN,CHUNGLOAI.PK_SEQ AS CHUNGLOAIID, CHUNGLOAI.TEN AS CHUNGLOAITEN," +
    			"SP.MA AS MASP, SP.TEN AS TENSP, " +
    			"CASE WHEN NPPKHO.SOLUONG <=0 THEN 0 ELSE NPPKHO.SOLUONG END AS INV,ISNULL(STOCKINTRANS.INS,0) AS INS,cast(ISNULL(AVGSALES.AVGSALES,0) as float) AS AVGSALES," +
    			"CASE WHEN NPPKHO.SOLUONG +ISNULL(STOCKINTRANS.INS,0) <= 0 THEN 0 WHEN (ISNULL(AVGSALES.AVGSALES,0) > 0) THEN " +
    			" CAST(((NPPKHO.SOLUONG +ISNULL(STOCKINTRANS.INS,0))/ISNULL(AVGSALES.AVGSALES,0)) AS int) " +
    			"ELSE 1000 " +
    			"END AS INVDAY " +
    			"FROM NHAPP_KHO NPPKHO " +
    			"	INNER JOIN SANPHAM SP ON NPPKHO.SANPHAM_FK = SP.PK_SEQ" +
    			"	INNER JOIN NHANHANG NHAN ON NHAN.PK_SEQ = SP.NHANHANG_FK" +
    			"	INNER JOIN CHUNGLOAI CHUNGLOAI ON CHUNGLOAI.PK_SEQ = SP.CHUNGLOAI_FK" +
    			"	INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK" +
    			"	INNER JOIN NHAPHANPHOI NPP ON NPPKHO.NPP_FK = NPP.PK_SEQ" +
    			"	INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ = NPPKHO.KBH_FK	" +
    			"	INNER JOIN KHUVUC KV ON KV.PK_SEQ = NPP.KHUVUC_FK" +
    			"	INNER JOIN VUNG VUNG ON VUNG.PK_SEQ = KV.VUNG_FK" +
    			"	LEFT  JOIN QUANHUYEN QH on QH.PK_SEQ = NPP.QUANHUYEN_FK " +
    			"   LEFT JOIN TINHTHANH TT ON TT.PK_SEQ = NPP.TINHTHANH_FK" +
    			"	LEFT JOIN" +
    			"	( " +
    			"		SELECT KBH.PK_SEQ AS KBHID,NPP.PK_SEQ AS NPPID,NHAPHANG.KHO_FK,SP.MA AS MASP,SUM(CONVERT(INT,NHAPHANG_SP.SOLUONG)) AS INS " +
    			"      FROM NHAPHANG NHAPHANG  " +
    			"			INNER JOIN NHAPHANG_SP NHAPHANG_SP ON NHAPHANG_SP.NHAPHANG_FK = NHAPHANG.PK_SEQ" +
    			"			INNER JOIN SANPHAM SP ON NHAPHANG_SP.SANPHAM_FK = SP.MA	" +
    			"			INNER JOIN NHAPHANPHOI NPP ON NHAPHANG.NPP_FK = NPP.PK_SEQ" +
    			"			INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ = NHAPHANG.KBH_FK" +
    			"		WHERE NHAPHANG.TRANGTHAI = '0' and NHAPHANG_SP.gianet > 0 AND NHAPHANG.NGAYNHAN ='"+ this.getDateTime() +"'" +
    			"		GROUP BY KBH.PK_SEQ,NPP.PK_SEQ,SP.MA,NHAPHANG.KHO_FK" +
    			"	)STOCKINTRANS ON STOCKINTRANS.KBHID = KBH.PK_SEQ AND STOCKINTRANS.NPPID = NPP.PK_SEQ AND STOCKINTRANS.MASP = SP.MA AND NPPKHO.KHO_FK = STOCKINTRANS.KHO_FK " +
    			"	LEFT JOIN" +
    			"	( " +
    			"		SELECT" +
    			"		KBH.PK_SEQ AS KBHID,NPP.PK_SEQ AS NPPID,DH_SP.KHO_FK,SP.MA AS MASP," +
    			"		CAST(SUM(DH_SP.SOLUONG)/72 AS NUMERIC(18,3)) AS AVGSALES" +
    			"		FROM DONHANG_SANPHAM DH_SP" +
    			"		INNER JOIN DONHANG DH ON DH.PK_SEQ = DH_SP.DONHANG_FK" +
    			"		INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = DH.NPP_FK" +
    			"		INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ = DH.KBH_FK" +
    			"		INNER JOIN SANPHAM SP ON SP.PK_SEQ = DH_SP.SANPHAM_FK" +
    			"		WHERE DH.TRANGTHAI = '1'" +
    			"			  AND DH.NGAYNHAP > (SELECT CONVERT(VARCHAR(10),DATEADD(day,-12*7-2,dbo.GetLocalDate(DEFAULT)),120))" +
    			"			  AND DH.NGAYNHAP <= (SELECT CONVERT(VARCHAR(10),DATEADD(day,-2,dbo.GetLocalDate(DEFAULT)),120))" +
    			"		GROUP BY KBH.PK_SEQ,NPP.PK_SEQ,SP.MA,DH_SP.KHO_FK" +
    			"	)AVGSALES ON AVGSALES.KBHID = KBH.PK_SEQ AND AVGSALES.NPPID = NPP.PK_SEQ AND AVGSALES.MASP = SP.MA AND AVGSALES.KHO_FK = NPPKHO.KHO_FK " +
    			"WHERE SP.TRANGTHAI = '1' and NPPKHO.kho_fk ='100000'";
    
    if(sql.length()>0)
    	query = query + sql;
    query +=" ) TONG where (INV + INS != 0 or AVGSALES != 0) and TONG.INVDAY <= 14 or TONG.INVDAY >= 21 ";
    return query;
}
	
    public StockAlarmDistributor() {
        super();       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
	    IStockintransit obj = new Stockintransit();
	    Utility util = new Utility();
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    obj.setuserId(userId);
	    obj.init();	    
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Distributor/StockAlarm.jsp";
		response.sendRedirect(nextJSP);
	}

	
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
			request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
			HttpSession session = request.getSession();
		    IStockintransit obj = new Stockintransit();
		    String userId = (String) session.getAttribute("userId");  
		    String userTen = (String) session.getAttribute("userTen"); 
		    Utility util = new Utility();
		    
		    if(userId ==null)
		    	userId ="";
		    obj.setuserId(userId);
		    
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
		   	 
		   	 String nhanhangId=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhanhangId")));
		   	 if(nhanhangId ==null)
		   		 nhanhangId = "";
		   	 obj.setnhanhangId(nhanhangId);
		   	 
		   	 String chungloaiId=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chungloaiId")));
		   	 if(chungloaiId==null)
		   		chungloaiId = "";
		   	 obj.setchungloaiId(chungloaiId);
		   	 
			 
			 geso.dms.distributor.util.Utility  Ult = new geso.dms.distributor.util.Utility();
			 nppId = Ult.getIdNhapp(userId);
			 obj.setnppId(nppId);
			 obj.setnppTen(Ult.getTenNhaPP());
			 String sql ="";

			 if(kenhId.length()>0) sql = sql +" and kbh.pk_seq ='"+ kenhId +"'";
			 if(dvkdId.length()>0) sql = sql +" and sp.dvkd_fk ='"+ dvkdId +"'";
			 if(nppId.length()>0)sql =sql +" and npp.pk_seq ='"+ nppId +"'";
			 if(nhanhangId.length()>0) sql = sql +" and nhan.pk_seq ='"+ nhanhangId +"'";
			 if(chungloaiId.length()>0)sql = sql +" and chungloai.pk_seq ='"+ chungloaiId +"'";
			 System.out.println("lenh them:"+ sql);
			 String action=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));

			 if(action.equals("tao"))
			 {
				try{
					request.setCharacterEncoding("utf-8");
					response.setCharacterEncoding("utf-8");			
					response.setContentType("application/xlsm");
			        response.setHeader("Content-Disposition", "attachment; filename=CanhBaoHangTonKho.xlsm");
			        OutputStream out = response.getOutputStream();
			        String query  = this.setQuery(request, sql);
			        
			        System.out.println("Query: " + query);
					CreatePivotTable(out, response, request,obj, query);	// Create PivotTable 
					        
				}catch(Exception ex){
					request.getSession().setAttribute("errors", ex.getMessage());
				}
			}
			obj.init();	    
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			String nextJSP = "/AHF/pages/Distributor/StockAlarm.jsp";
			response.sendRedirect(nextJSP);
	}
	
	private void CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj , String query)
	{	
		try {

			FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") +"\\StockAlarm(NPP).xlsm");	
			//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\StockAlarm(NPP).xlsm");
			//FileInputStream fstream = new FileInputStream(f) ;
//		 	FileInputStream fstream = new FileInputStream("D:\\DMS\\Best\\WebContent\\pages\\Templates\\StockAlarm(NPP).xlsm");
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			 
			CreateHeader(workbook, obj); 
			FillData(workbook, obj, query); 
			workbook.save(out);
			fstream.close();			
			
		} catch (Exception ex) {
			System.out.println("Error Message: " + ex.getMessage());
		}
	}
	
	private void FillData(Workbook workbook,IStockintransit obj, String query)throws Exception {
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		
		for(int i=0;i<6;++i){
	    	cells.setColumnWidth(i, 15.0f);	    	
	    }	
		dbutils db = new dbutils();
		ResultSet rs = db.get(query);
		int index = 2;
	    Cell cell = null;	    
		while (rs.next()) {
			cell = cells.getCell("DA" + String.valueOf(index));		cell.setValue(rs.getString("KBHTEN"));
			cell = cells.getCell("DB" + String.valueOf(index));		cell.setValue(rs.getString("DVKD"));
			cell = cells.getCell("DC" + String.valueOf(index));		cell.setValue(rs.getString("NHANTEN"));
			cell = cells.getCell("DD" + String.valueOf(index));		cell.setValue(rs.getString("CHUNGLOAITEN"));
			cell = cells.getCell("DE" + String.valueOf(index));		cell.setValue(rs.getString("MASP"));
			cell = cells.getCell("DF" + String.valueOf(index));		cell.setValue(rs.getString("TENSP"));			
			cell = cells.getCell("DG" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("INV")));
			cell = cells.getCell("DH" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("AVGSALES")));
			cell = cells.getCell("DI" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("INVDAY")));		
			index++;
		}
		
		if(rs!=null) rs.close();
		if(db!=null){
			db.shutDown();
		}
	 

	}
	
	private void CreateHeader(Workbook workbook,IStockintransit obj ) {
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");

		Cells cells = worksheet.getCells();

		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
	    cell.setValue("CẢNH BÁO TỒN KHO");   	
	    	    
	    cells.setRowHeight(2, 20.0f);
	    cell = cells.getCell("A3");getCellStyle(workbook,"A3",Color.NAVY,true,9);
	    cell.setValue("Ngày báo cáo: " + this.getDateTime());
	    
	    cells.setRowHeight(3, 20.0f);
	    cell = cells.getCell("A4");getCellStyle(workbook,"A4",Color.NAVY,true,9);
	    cell.setValue("Được tạo bởi:  " + obj.getuserTen());
			    	    
	    cell = cells.getCell("DA1");	cell.setValue("KenhBanHang");				ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("DB1");	cell.setValue("DonViKinhDoanh");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("DC1");	cell.setValue("NhanHang");					ReportAPI.setCellHeader(cell);
		cell = cells.getCell("DD1");	cell.setValue("ChungLoai");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("DE1");	cell.setValue("MaSanPham");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("DF1");	cell.setValue("TenSanPham");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("DG1");	cell.setValue("TonKho(+Hangdangdiduong)")	;			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("DH1");	cell.setValue("DoanhSoBanTB");	ReportAPI.setCellHeader(cell);
		cell = cells.getCell("DI1");	cell.setValue("SoNgayTonKho")	;		ReportAPI.setCellHeader(cell);
	}
	
	public String getDateTime() 
	{
		 DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
         Date date = new Date();
         return dateFormat.format(date);	
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
	
}
