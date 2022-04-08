package geso.dms.center.servlets.report;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
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
import com.aspose.cells.PivotFieldDataDisplayFormat;
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTableAutoFormatType;
import com.aspose.cells.PivotTables;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class StockAlarm extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public String getQuery(HttpServletRequest request, String sql, String userId, String lessday, String moreday){
		String query = "";		
		geso.dms.center.util.Utility ut = new geso.dms.center.util.Utility();
		query  = " SELECT * FROM (" +
		   "\n SELECT KBH.PK_SEQ AS KBHID, KBH.TEN AS KBHTEN,VUNG.PK_SEQ AS VUNGID, VUNG.TEN AS VUNGTEN,TT.TEN AS TINHTHANH," +
			"\n KV.PK_SEQ AS KVID, KV.TEN AS KVTEN," +
			"\n ISNULL(QH.PK_SEQ,0) AS QHID, ISNULL(QH.TEN,'') AS QUANHUYEN, NPP.PK_SEQ AS NPPID, NPP.TEN AS NPPTEN,DVKD.PK_SEQ AS DVKDID, DVKD.DONVIKINHDOANH AS DVKD," +
			"\n NHAN.PK_SEQ AS NHANID, NHAN.TEN AS NHANTEN,CHUNGLOAI.PK_SEQ AS CHUNGLOAIID, CHUNGLOAI.TEN AS CHUNGLOAITEN," +
			"\n SP.MA AS MASP, SP.TEN AS TENSP,NPPKHO.SOLUONG  + ISNULL(STOCKINTRANS.INS,0) AS INV,"+
			"\n NPPKHO.SOLUONG*GIAMUANPP  + ISNULL(STOCKINTRANS.INS*GIAMUANPP,0) AS USDINV, NPPKHO.SOLUONG as INSKNP, NPPKHO.SOLUONG*GIAMUANPP as USDINSKNP,"+
			"\n ISNULL(STOCKINTRANS.INS,0) AS INS,cast(ISNULL(AVGSALES.AVGSALES,0) as float) AS AVGSALES,cast(ISNULL(AVGSALES.AVGSALES*GIAMUANPP,0) as float) AS USDAVGSALES," +
		//	"\n CASE WHEN (ISNULL(AVGSALES.AVGSALES,0) > 0) THEN CAST((NPPKHO.SOLUONG/ISNULL(AVGSALES.AVGSALES,0)) AS NUMERIC(18,0)) " +
			"\n CASE WHEN NPPKHO.SOLUONG  + ISNULL(STOCKINTRANS.INS,0) =0 THEN 0 WHEN (ISNULL(AVGSALES.AVGSALES,0) > 0) THEN" +
			"\n cast( (NPPKHO.SOLUONG + ISNULL(STOCKINTRANS.INS,0))/ISNULL(AVGSALES.AVGSALES,0) as int) " +
			"\n ELSE 1000 " +
			"\n END AS INVDAY ," +
			"\n CASE WHEN NPPKHO.SOLUONG  + ISNULL(STOCKINTRANS.INS,0) =0 THEN 0 WHEN (ISNULL(AVGSALES.AVGSALES,0) > 0) THEN" +
			"\n cast( NPPKHO.SOLUONG/ISNULL(AVGSALES.AVGSALES,0) as int) " +
			"\n ELSE 1000 " +
			"\n END AS INVDAYKNP " +
			"\n FROM NHAPP_KHO NPPKHO " +
			"\n	INNER JOIN SANPHAM SP ON NPPKHO.SANPHAM_FK = SP.PK_SEQ " +
			"\n	INNER JOIN NHANHANG NHAN ON NHAN.PK_SEQ = SP.NHANHANG_FK" +
			"\n	INNER JOIN CHUNGLOAI CHUNGLOAI ON CHUNGLOAI.PK_SEQ = SP.CHUNGLOAI_FK" +
			"\n	INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK" +
			"\n	INNER JOIN NHAPHANPHOI NPP ON NPPKHO.NPP_FK = NPP.PK_SEQ" +
			"\n	INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ = NPPKHO.KBH_FK	" +
			"\n	INNER JOIN ( SELECT bg.KENH_FK,BG_NPP.NPP_FK,BG_NPP_SP.SANPHAM_FK ,MAX( BG_NPP_SP.GIABANLECHUAN)  AS GIAMUANPP " +
			"\n	FROM  BANGGIABANLECHUAN bg inner join  " +
			"\n	BANGGIABANLECHUAN_NPP BG_NPP   on bg.PK_SEQ= BG_NPP.BANGGIABANLECHUAN_FK " +
			"\n	INNER JOIN BANGGIABLC_SANPHAM BG_NPP_SP ON     BG_NPP_SP.BGBLC_FK = BG_NPP.BANGGIABANLECHUAN_FK " +
			"\n	GROUP BY BG_NPP.NPP_FK,BG_NPP_SP.SANPHAM_FK	,bg.KENH_FK ) BG on BG.KENH_FK = KBH.PK_SEQ AND BG.NPP_FK = NPP.PK_SEQ " +
			"\n AND BG.SANPHAM_FK = SP.PK_SEQ "+
			"\n	INNER JOIN KHUVUC KV ON KV.PK_SEQ = NPP.KHUVUC_FK" +
			"\n	INNER JOIN VUNG VUNG ON VUNG.PK_SEQ = KV.VUNG_FK" +
			"\n	LEFT  JOIN QUANHUYEN QH on QH.PK_SEQ = NPP.QUANHUYEN_FK " +
			"\n  LEFT JOIN TINHTHANH TT ON TT.PK_SEQ = NPP.TINHTHANH_FK" +
			"\n	LEFT JOIN" +
			"\n	( " +
			"\n		SELECT KBH.PK_SEQ AS KBHID,NPP.PK_SEQ AS NPPID,NHAPHANG_SP.KHO_FK,SP.MA AS MASP,SUM(CONVERT(FLOAT,NHAPHANG_SP.SOLUONGNHAN)) AS INS " +
			"\n      FROM NHAPHANG NHAPHANG  " +
			"\n			INNER JOIN NHAPHANG_SP NHAPHANG_SP ON NHAPHANG_SP.NHAPHANG_FK = NHAPHANG.PK_SEQ" +
			"\n			INNER JOIN SANPHAM SP ON NHAPHANG_SP.SANPHAM_FK = SP.MA	" +
			"\n			INNER JOIN NHAPHANPHOI NPP ON NHAPHANG.NPP_FK = NPP.PK_SEQ" +
			"\n			INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ = NHAPHANG.KBH_FK" +
			"\n		  WHERE NHAPHANG.TRANGTHAI = '0' and NHAPHANG_SP.kho_fk ='100000' --and NHAPHANG_SP.gianet > 0  "
			+ "\n and NHAPHANG.NGAYNHAN >= (SELECT CONVERT(VARCHAR(10),DATEADD(day,-30,dbo.GetLocalDate(DEFAULT)),120))";
			query += "\n and npp.pk_seq in "+ ut.quyen_npp(userId) + " and kbh.pk_seq in " + ut.quyen_kenh(userId)
			+" and sp.pk_seq in "+ ut.quyen_sanpham(userId);
			query +="\n		GROUP BY KBH.PK_SEQ,NPP.PK_SEQ,SP.MA,NHAPHANG_SP.KHO_FK" +
			"\n	)STOCKINTRANS ON STOCKINTRANS.KBHID = KBH.PK_SEQ AND STOCKINTRANS.NPPID = NPP.PK_SEQ AND STOCKINTRANS.MASP = SP.MA AND NPPKHO.KHO_FK = STOCKINTRANS.KHO_FK" +
			"\n	LEFT JOIN" +
			"\n	( " +
			"\n		SELECT" +
			"\n		KBH.PK_SEQ AS KBHID,NPP.PK_SEQ AS NPPID,SP.MA AS MASP,DH_SP.KHO_FK, " +
			"\n		CAST(SUM(DH_SP.SOLUONG)/72 AS NUMERIC(18,3)) AS AVGSALES" +
			"\n		FROM DONHANG_SANPHAM DH_SP" +
			"\n		INNER JOIN DONHANG DH ON DH.PK_SEQ = DH_SP.DONHANG_FK" +
			"\n		INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = DH.NPP_FK" +
			"\n		INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ = DH.KBH_FK" +
			"\n		INNER JOIN SANPHAM SP ON SP.PK_SEQ = DH_SP.SANPHAM_FK" +
			"\n		WHERE DH.TRANGTHAI = '1' and DATEDIFF ( dd , npp.NGAYTAO , getdate() ) >= 72 " +
			"\n			  AND DH.NGAYNHAP > (SELECT CONVERT(VARCHAR(10),DATEADD(day,-12*7,dbo.GetLocalDate(DEFAULT)),120))" +
			"\n			  AND DH.NGAYNHAP <= (SELECT CONVERT(VARCHAR(10),DATEADD(day,0,dbo.GetLocalDate(DEFAULT)),120))" +
			"\n		GROUP BY KBH.PK_SEQ,NPP.PK_SEQ,SP.MA,DH_SP.KHO_FK" +
			"\n		union " +
			"\n		SELECT " +
			"\n		KBH.PK_SEQ AS KBHID,NPP.PK_SEQ AS NPPID,SP.MA AS MASP,DH_SP.KHO_FK,  " +
			"\n		CAST(SUM(DH_SP.SOLUONG)/DATEDIFF ( dd , npp.NGAYTAO , getdate() ) AS NUMERIC(18,3)) AS AVGSALES " +
			"\n		FROM DONHANG_SANPHAM DH_SP " +
			"\n		INNER JOIN DONHANG DH ON DH.PK_SEQ = DH_SP.DONHANG_FK " +
			"\n		INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = DH.NPP_FK " +
			"\n		INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ = DH.KBH_FK " +
			"\n		INNER JOIN SANPHAM SP ON SP.PK_SEQ = DH_SP.SANPHAM_FK " +
			"\n		WHERE DH.TRANGTHAI = '1' and DATEDIFF ( dd , npp.NGAYTAO , getdate() ) <= 72 " +
			"\n			  AND DH.NGAYNHAP > (SELECT CONVERT(VARCHAR(10),DATEADD(day,-12*7,dbo.GetLocalDate(DEFAULT)),120)) " +
			"\n			  AND DH.NGAYNHAP <= (SELECT CONVERT(VARCHAR(10),DATEADD(day,0,dbo.GetLocalDate(DEFAULT)),120)) " +
			"\n		GROUP BY KBH.PK_SEQ,NPP.PK_SEQ,SP.MA,DH_SP.KHO_FK, npp.NGAYTAO " +
			"\n	)AVGSALES ON AVGSALES.KBHID = KBH.PK_SEQ AND AVGSALES.NPPID = NPP.PK_SEQ AND AVGSALES.MASP = SP.MA AND AVGSALES.KHO_FK = NPPKHO.KHO_FK " +
			"\n WHERE SP.TRANGTHAI = '1' and NPPKHO.kho_fk ='100000'";//chi la o kho chinh

//
		 if(sql.length() > 0)
	        	query = query + sql;
	       
	    	//phanquyen
		
			query += " and npp.pk_seq in "+ ut.quyen_npp(userId) + " and kbh.pk_seq in " + ut.quyen_kenh(userId)
								+" and sp.pk_seq in "+ ut.quyen_sanpham(userId);
           ///query +=" ) TONG where TONG.INVDAY <= '" + lessday + "' or TONG.INVDAY >= '" + moreday + "' ";
           query +=" ) TONG where (INV + INS != 0 or AVGSALES != 0) and (TONG.INVDAY <= '" + lessday + "' or TONG.INVDAY >= '" + moreday +"')";
     
        System.out.println("Stock Alarm: " + query);
        
        return query;
	}
      
   
    public StockAlarm() {
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
//	    System.out.println(userId);
	    obj.setuserId(userId);
	    
	    obj.init();	   
	    
	    System.out.println("Bao Cao Canh Bao Hang Ton Kho");
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Center/StockAlarm.jsp";
		response.sendRedirect(nextJSP);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
	    IStockintransit obj = new Stockintransit();
	    String userId = (String) session.getAttribute("userId");  
	    String userTen = (String) session.getAttribute("userTen"); 
  
	    if(userId ==null)
	    	userId ="";
	    obj.setuserId(userId);
	    
	    obj.setuserTen(userTen);
   	 	String nppId=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
   	 	
   	 	if(nppId ==null) 
   	 		nppId ="";
   	 	obj.setnppId(nppId);
   	    	 
	   	String kenhId=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId"));
	   	if(kenhId == null)
	   		kenhId ="";
	   	
	   	obj.setkenhId(kenhId);
	   	 
	   	String dvkdId=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId"));
	   	if(dvkdId == null)
	   		 dvkdId ="";
	   	
	   	obj.setdvkdId(dvkdId);
	   	 
	   	String nhanhangId=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhanhangId"));
	   	if(nhanhangId ==null)
	   		nhanhangId = "";
	   	 
	   	obj.setnhanhangId(nhanhangId);
	   	 
	   	String chungloaiId=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chungloaiId"));
	   	
	   	if(chungloaiId==null)
	   		chungloaiId = "";
	   	
	   	obj.setchungloaiId(chungloaiId);
	   	 
	   	 
	   	String vungId=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"));
	   	
	   	if(vungId ==null)
	   		 vungId = "";
	   	obj.setvungId(vungId);
	   	 
		String instransit = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("instransit")); // <!---
		obj.setdiscount(instransit);
	   	
	   	
	   	
	   	String khuvucId=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"));
	   	
	   	if(khuvucId == null)
	   		 khuvucId ="";
	   	
	   	obj.setkhuvucId(khuvucId);
	   	 
	   	String gsbhId=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gsbhId"));
	   	if(gsbhId ==null)
	   		 gsbhId ="";
	   	
	   	obj.setgsbhId(gsbhId);
	   	 
	   	String lessday=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("lessday"));
	   	obj.setlessday(lessday);
	   	
	   	String moreday=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("moreday"));
	    obj.setlessday(lessday);
	     
	    String sql ="";
		if(kenhId.length()>0) sql = sql +" and kbh.pk_seq ='"+ kenhId +"'";
		if(vungId.length()>0) sql =sql +" and vung.pk_seq ='"+ vungId +"'";
		if(khuvucId.length()>0)sql = sql + " and kv.pk_seq ='"+ khuvucId +"'";
		if(dvkdId.length()>0) sql = sql +" and sp.dvkd_fk ='"+ dvkdId +"'";
		if(nppId.length()>0)sql =sql +" and npp.pk_seq ='"+ nppId +"'";
		if(gsbhId.length()>0) sql = sql +" and gsbh.pk_seq ='"+ gsbhId +"'";
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
			    response.setHeader("Content-Disposition", "attachment; filename=StockAlarm(TT).xlsm");
				OutputStream out = response.getOutputStream();
				        
				String query = getQuery(request,sql,userId, lessday, moreday);        //Thiết lập chuỗi truy vấn vào CSDL lấy tham số từ Client
				CreatePivotTable(out, response, request,obj, query);	// Create PivotTable 
				request.getSession().setAttribute("errors", "");
			}catch(Exception ex)
			{
				obj.setMsg("Lỗi : "+ ex.toString());
			}
		}
		
		obj.init();	    
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Center/StockAlarm.jsp";
		response.sendRedirect(nextJSP);
	}
	
	private void CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj, String query) throws Exception
	{
		try 
		{
			
			String chuoi=getServletContext().getInitParameter("path") + "\\StockAlarm(TT).xlsm";
			
			FileInputStream fstream = new FileInputStream(chuoi);
			
			//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\StockAlarm(TT).xlsm");
			//FileInputStream fstream = new FileInputStream(f) ;
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);			 
			CreateHeader(workbook, obj); 
			FillData(workbook, query,obj); 
			workbook.save(out);
			fstream.close();
		} 
		catch (Exception ex) {
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}
	
	private void CreateHeader(Workbook workbook,IStockintransit obj ) 
	{

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
		
	    
	    cell = cells.getCell("AA1");		cell.setValue("KenhBanHang");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AB1");		cell.setValue("DonViKinhDoanh");					ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AC1");		cell.setValue("ChiNhanh");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AD1");		cell.setValue("KhuVuc");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AE1");		cell.setValue("MaNhaPhanPhoi");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AF1");		cell.setValue("NhaPhanPhoi");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AG1");		cell.setValue("MaSanPham");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AH1");		cell.setValue("TenSanPham");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AI1");		cell.setValue("NhanHang");		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AJ1");		cell.setValue("ChungLoai");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AK1");		cell.setValue("TinhThanh");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AL1");		cell.setValue("QuanHuyen");					ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AM1");		cell.setValue("TonKho(+Hangdangdiduong)");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AN1");		cell.setValue("SoLuongBanTB");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AO1");		cell.setValue("SoNgayTonKho");				ReportAPI.setCellHeader(cell);		
		cell = cells.getCell("AP1");		cell.setValue("DSTonKho(+Hangdiduong)");				ReportAPI.setCellHeader(cell);		
		cell = cells.getCell("AQ1");		cell.setValue("DoanhSoBanTB");				ReportAPI.setCellHeader(cell);		
	}
	
	private void FillData(Workbook workbook, String query,IStockintransit obj ) throws Exception 
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		
		for(int i = 0; i < 6; i++)
		{
	    	cells.setColumnWidth(i, 15.0f);	
	    }
		
		dbutils db = new dbutils();
		ResultSet rs = db.get(query);
		int index = 2;
	    Cell cell = null;	    
	    while (rs.next()) 
	    {
	    	cell = cells.getCell("AA" + String.valueOf(index));		cell.setValue(rs.getString("KBHTEN"));	
	    	cell = cells.getCell("AB" + String.valueOf(index));		cell.setValue(rs.getString("DVKD"));				//Don vi kinh doanh
			cell = cells.getCell("AC" + String.valueOf(index));		cell.setValue(rs.getString("VUNGTEN"));
			cell = cells.getCell("AD" + String.valueOf(index));		cell.setValue(rs.getString("KVTEN"));
			cell = cells.getCell("AE" + String.valueOf(index));		cell.setValue(rs.getString("NPPID"));
			cell = cells.getCell("AF" + String.valueOf(index));		cell.setValue(rs.getString("NPPTEN"));
			cell = cells.getCell("AG" + String.valueOf(index));		cell.setValue(rs.getString("MASP"));
			cell = cells.getCell("AH" + String.valueOf(index));		cell.setValue(rs.getString("TENSP"));
			cell = cells.getCell("AI" + String.valueOf(index));		cell.setValue(rs.getString("NHANTEN"));
			cell = cells.getCell("AJ" + String.valueOf(index));		cell.setValue(rs.getString("CHUNGLOAITEN"));
			cell = cells.getCell("AK" + String.valueOf(index));		cell.setValue(rs.getString("TINHTHANH"));
			cell = cells.getCell("AL" + String.valueOf(index));		cell.setValue(rs.getString("QUANHUYEN"));		
			//cell = cells.getCell("AM" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("INV")));
			if(obj.getdiscount().equals("0")){
				cell = cells.getCell("AM" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("INV")));
			}else{
				cell = cells.getCell("AM" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("INSKNP")));
			}
			cell = cells.getCell("AN" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("AVGSALES")));
			
			
			if(obj.getdiscount().equals("0")){
				cell = cells.getCell("AO" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("INVDAY")));	
			}else{
				cell = cells.getCell("AO" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("INVDAYKNP")));	
			}
			
			
			cell = cells.getCell("AP" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("USDINV")));
			if(obj.getdiscount().equals("0")){
				cell = cells.getCell("AP" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("USDINV")));
			}else{
				cell = cells.getCell("AP" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("USDINSKNP")));
			}
			cell = cells.getCell("AQ" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("USDAVGSALES")));	
			index++;
		}
	    if(rs!=null) 
	    	rs.close();
	    db.shutDown();

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

	public String getDateTime() 
	{
		 DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
         Date date = new Date();
         return dateFormat.format(date);	
	}
}
