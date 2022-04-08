package geso.dms.center.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.distributor.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import com.aspose.cells.ConsolidationFunction;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.PivotField;
import com.aspose.cells.PivotFieldDataDisplayFormat;
import com.aspose.cells.PivotFieldSubtotalType;
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTableAutoFormatType;
import com.aspose.cells.Style;
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTables;


import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class PdaTTSvl extends HttpServlet {
	public PdaTTSvl() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		String querystring = request.getQueryString();
		
		Utility util = new Utility();
		obj.setuserId(util.getUserId(querystring));
		obj.setuserTen((String) session.getAttribute("userTen"));
		obj.setnppId(util.getIdNhapp(obj.getuserId()));
		
		
		obj.init();
		obj.settype("1");
		session.setAttribute("obj", obj);		
		session.setAttribute("userId", obj.getuserId());
		session.setAttribute("userTen", obj.getuserTen());
		String nextJSP = "/AHF/pages/Center/PdaTT.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		OutputStream out = response.getOutputStream();
		Utility util = new Utility();
		
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		
		obj.settungay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays"))));
		obj.setdenngay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays"))));
		
		
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");
		obj.setkenhId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId"))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")):""));
		
		obj.setvungId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")):""));
			
		obj.setkhuvucId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")):""));
		
		obj.setgsbhId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gsbhs"))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gsbhs")):""));
		
		obj.setnppId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")):""));
		
		obj.setDdkd(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdId"))!=null?	
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdId")):""));
		
		 obj.settype(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("typeid")));
		
		System.out.println(obj.gettype());
		 //truong hop bao cao thang type la 1.
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")!=null? util.antiSQLInspection(request.getParameter("action"))):"";
		String nextJSP = "/AHF/pages/Center/PdaTT.jsp";
		
		if(action.equals("Taomoi")){
			try{
		
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=PDATT.xlsm");
		        if(!CreatePivotTable(out,obj)){
		        
		        	
		        	
		        }
			}catch(Exception ex){
				obj.setMsg(ex.getMessage());
			}
		}else{			
			
		}
		obj.init();
		session.setAttribute("obj", obj);	
		response.sendRedirect(nextJSP);
	}

	private boolean CreatePivotTable(OutputStream out, IStockintransit obj) throws Exception 
	{
		//String chuoi=getServletContext().getInitParameter("path") + "\\PDATT.xlsm";
		//FileInputStream fstream = new FileInputStream(chuoi);
		File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\PDATT.xlsm");
		FileInputStream fstream = new FileInputStream(f) ;
		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateStaticHeader(workbook, obj);

		boolean isFill = CreateStaticData(workbook, obj);
		
		if (!isFill){
			fstream.close();
			return false;
		}else {
			workbook.save(out);
			fstream.close();
			return true;
		}
	}

	private void CreateStaticHeader(Workbook workbook, IStockintransit obj) throws Exception 
	{
		try{
			
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");

			Cells cells = worksheet.getCells();

			cells.setRowHeight(0, 20.0f);
			Cell cell = cells.getCell("A1");
		    cell.setValue("BÁO CÁO PDA");   	
		    
		    cells.setRowHeight(2, 18.0f);
		    cell = cells.getCell("A3"); 
		    getCellStyle(workbook,"A3",Color.NAVY,true,10);
		    
		    cell.setValue("Từ ngày: " + obj.gettungay());	
		    
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("B3"); getCellStyle(workbook,"B3",Color.NAVY,true,9);
		    
			cell.setValue("Đến ngày: " + obj.getdenngay());  
			
		    cells.setRowHeight(4, 18.0f);
		    cell = cells.getCell("A4");getCellStyle(workbook,"A4",Color.NAVY,true,9);
		    cell.setValue("Ngày báo cáo: " + this.getDateTime());
		    
		    cells.setRowHeight(5, 18.0f);
		    cell = cells.getCell("A5");getCellStyle(workbook,"A5",Color.NAVY,true,9);
		    cell.setValue("Được tạo bởi :  " + obj.getuserTen());
					
		 	cell = cells.getCell("AA1"); cell.setValue("KenhBanHang");
		 	cell = cells.getCell("AB1"); cell.setValue("ChiNhanh");
		    cell = cells.getCell("AC1"); cell.setValue("KhuVuc");
		    cell = cells.getCell("AD1"); cell.setValue("GiamSat");
		    cell = cells.getCell("AE1"); cell.setValue("MaNhaPhanPhoi");	
		    cell = cells.getCell("AF1"); cell.setValue("NhaPhanPhoi");
		    cell = cells.getCell("AG1"); cell.setValue("DaiDienKinhDoanh");
			cell = cells.getCell("AH1"); cell.setValue("SoCuaHang");
			cell = cells.getCell("AI1"); cell.setValue("SoViengTham");
			cell = cells.getCell("AJ1"); cell.setValue("CoDoanhSo");
			cell = cells.getCell("AK1"); cell.setValue("%ViengTham");
			cell = cells.getCell("AL1"); cell.setValue("%DoanhSo/ViengTham");
			cell = cells.getCell("AM1"); cell.setValue("DoanhSo");
			

		}catch(Exception ex){
			throw new Exception("Bao cao bi loi khi khoi tao");
		}
		
	      
	}
	
	private boolean CreateStaticData(Workbook workbook,IStockintransit obj) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();

	    ResultSet rs1;
	    
	    String query = 
	    	" SELECT VUNG.TEN AS VUNGTEN, KHUVUC.TEN AS KVTEN, GSBH.TEN AS GSBHTEN, NPP.TEN AS NPPTEN,  " +
	    	" 	DDKD.TEN AS DDKDTEN, NPP.SITECODE,  " +
	    	" 	SOCUAHANG.*, isnull(VT.SOVT, 0) as SOVTPDA, ISNULL(DS.SOCHCODS, 0) AS CODSPDA, ISNULL(DS.DOANHSO, 0) AS DOANHSO  " +
	    	" FROM (  " +
	    	" 	SELECT TBH.NPP_FK, A.DDKD_FK, A.NGAYID,  " +
	    	" 		SUM(ISNULL(A.SOKH,0)) AS SOKH, SUM(ISNULL(A.SOKHTOADO,0)) AS SOKHTOADO " +
	    	" 	FROM DDKD_SOKH A " +
	    	" 	INNER JOIN TUYENBANHANG TBH ON A.DDKD_FK = TBH.DDKD_FK AND A.NGAYID = TBH.NGAYID " +
	    	" 	WHERE A.NGAY BETWEEN '" + obj.gettungay() + "' AND '" + obj.getdenngay() + "'  " +
	    	" 	GROUP BY TBH.NPP_FK, A.DDKD_FK, A.NGAYID " +
	    	" ) SOCUAHANG  " +
	    	" LEFT JOIN (  " +
	    	" 	SELECT DDKD_FK, KH.NPP_FK, COUNT(DISTINCT DDKDKH.KHACHHANG_FK) AS SOVT  " +
	    	" 	FROM ddkd_khachhang DDKDKH  " +
	    	" 	INNER JOIN KHACHHANG KH ON KH.PK_SEQ = DDKDKH.KHACHHANG_FK  " +
	    	" 	WHERE REPLACE(CONVERT(NVARCHAR(10), DDKDKH.thoidiem , 102) , '.', '-' ) BETWEEN '" + obj.gettungay() + "' AND '" + obj.getdenngay() + "'  " +
	    	" 	GROUP BY DDKD_FK, KH.NPP_FK  " +
	    	" ) VT ON SOCUAHANG.NPP_FK = VT.NPP_FK AND SOCUAHANG.DDKD_FK = VT.DDKD_FK  " +
	    	" LEFT JOIN (  " +
	    	" 	SELECT DH.DDKD_FK, DH.NPP_FK, COUNT (DISTINCT DH.KHACHHANG_FK) AS SOCHCODS, SUM(DH.TONGGIATRI) as DOANHSO  " +
	    	" 	FROM DONHANG DH  " +
	    	" 	WHERE DH.NGAYNHAP BETWEEN '" + obj.gettungay() + "' AND '" + obj.getdenngay() + "' AND DH.IsPDA = 1 AND DH.TRANGTHAI = 1  " +
	    	" 	GROUP BY DH.DDKD_FK, DH.NPP_FK  " +
	    	" 	HAVING COUNT(DH.PK_SEQ) > 0  " +
	    	" ) DS ON SOCUAHANG.NPP_FK = DS.NPP_FK AND SOCUAHANG.DDKD_FK = DS.DDKD_FK  " +
	    	" INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=SOCUAHANG.NPP_FK " +
	    	" INNER JOIN DAIDIENKINHDOANH DDKD ON DDKD.PK_SEQ=SOCUAHANG.DDKD_FK  " +
	    	" INNER JOIN DDKD_GSBH ON DDKD_GSBH.DDKD_FK=SOCUAHANG.DDKD_FK  " +
	    	" INNER JOIN GIAMSATBANHANG GSBH ON GSBH.PK_SEQ=DDKD_GSBH.GSBH_FK  " +
	    	" INNER JOIN KHUVUC ON KHUVUC.PK_SEQ=NPP.KHUVUC_FK  " +
	    	" INNER JOIN VUNG ON VUNG.PK_SEQ=KHUVUC.VUNG_FK " +
	    	" WHERE DDKD.TRANGTHAI = 1 ";
	    
	    	/*" SELECT VUNG.TEN AS VUNGTEN, KHUVUC.TEN AS KVTEN, GSBH.TEN AS GSBHTEN, NPP.TEN AS NPPTEN, " +
	    	" 	KBH.TEN AS KBHTEN,DDKD.TEN AS DDKDTEN,NPP.SITECODE, " +
	    	" 	SOCUAHANG.*, isnull(VT.SOVT, 0) as SOVTPDA, ISNULL(DS.SOCHCODS, 0) AS CODSPDA " +
	    	" FROM ( " +
	    	" 	SELECT KPI.NPP_FK, KPI.KBH_FK, KPI.DDKD_FK, " +
	    	"		isnull((SELECT TOP(1) B.SOCUAHIEU FROM KPI B " +
	    	"		 WHERE B.NPP_FK = KPI.NPP_FK AND B.KBH_FK = KPI.KBH_FK AND B.DDKD_FK = KPI.DDKD_FK " +
	    	"			AND B.NGAY BETWEEN '" + obj.gettungay() + "' AND '" + obj.getdenngay() + "' " +
	    	" 		 ORDER BY B.NGAY DESC " +
	    	"		), 0) AS CHTTPDA " +
	    	" 	FROM KPI " +
	    	" 	WHERE KPI.NGAY BETWEEN '" + obj.gettungay() + "' AND '" + obj.getdenngay() + "' " +
	    	" 	GROUP BY KPI.NPP_FK, KPI.KBH_FK, KPI.DDKD_FK " +
	    	" ) SOCUAHANG " +
	    	" LEFT JOIN ( " +
	    	" 	SELECT DDKD_FK, KH.KBH_FK, KH.NPP_FK, COUNT(DISTINCT DDKDKH.KHACHHANG_FK) AS SOVT " +
	    	" 	FROM ddkd_khachhang DDKDKH " +
	    	" 	INNER JOIN KHACHHANG KH ON KH.PK_SEQ = DDKDKH.KHACHHANG_FK " +
	    	" 	WHERE REPLACE(CONVERT(NVARCHAR(10), DDKDKH.thoidiem , 102) , '.', '-' ) BETWEEN '" + obj.gettungay() + "' AND '" + obj.getdenngay() + "' " +
	    	" 	GROUP BY DDKD_FK, KH.KBH_FK, KH.NPP_FK " +
	    	" ) VT ON SOCUAHANG.NPP_FK = VT.NPP_FK AND SOCUAHANG.KBH_FK = VT.KBH_FK AND SOCUAHANG.DDKD_FK = VT.DDKD_FK " +
	    	" LEFT JOIN ( " +
	    	" 	SELECT DH.DDKD_FK, DH.KBH_FK, DH.NPP_FK, COUNT (DISTINCT DH.KHACHHANG_FK) AS SOCHCODS " +
	    	" 	FROM DONHANG DH " +
	    	" 	WHERE DH.NGAYNHAP BETWEEN '" + obj.gettungay() + "' AND '" + obj.getdenngay() + "' AND DH.IsPDA = 1 AND DH.TRANGTHAI = 1 " +
	    	" 	GROUP BY DH.DDKD_FK, DH.KBH_FK, DH.NPP_FK " +
	    	" 	HAVING COUNT(DH.PK_SEQ) > 0 " +
	    	" ) DS ON SOCUAHANG.NPP_FK = DS.NPP_FK AND SOCUAHANG.KBH_FK = DS.KBH_FK AND SOCUAHANG.DDKD_FK = DS.DDKD_FK " +
	    	" INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=SOCUAHANG.NPP_FK " +
	    	" INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ=SOCUAHANG.KBH_FK " +
	    	" INNER JOIN DAIDIENKINHDOANH DDKD ON DDKD.PK_SEQ=SOCUAHANG.DDKD_FK " +
	    	" INNER JOIN DDKD_GSBH ON DDKD_GSBH.DDKD_FK=SOCUAHANG.DDKD_FK " +
	    	" INNER JOIN GIAMSATBANHANG GSBH ON GSBH.PK_SEQ=DDKD_GSBH.GSBH_FK " +
	    	" INNER JOIN KHUVUC ON KHUVUC.PK_SEQ=NPP.KHUVUC_FK " +
	    	" INNER JOIN VUNG ON VUNG.PK_SEQ=KHUVUC.VUNG_FK " +
	    	" WHERE 1=1 ";*/
	    
	    if(obj.getDdkd() != null && obj.getDdkd().trim().length() > 0) {
	    	query += " AND DDKD.PK_SEQ = " + obj.getDdkd();
	    }
	    if(obj.getnppId() != null && obj.getnppId().trim().length() > 0) {
	    	query += " AND NPP.PK_SEQ = " + obj.getnppId();
	    }
	    if(obj.getvungId() != null && obj.getvungId().trim().length() > 0) {
	    	query += " AND VUNG.PK_SEQ = " + obj.getvungId();
	    }
	    if(obj.getkhuvucId() != null && obj.getkhuvucId().trim().length() > 0) {
	    	query += " AND KHUVUC.PK_SEQ = " + obj.getkhuvucId();
	    }
	    if(obj.getgsbhId() != null && obj.getgsbhId().trim().length() > 0) {
	    	query += " AND GSBH.PK_SEQ = " + obj.getgsbhId();
	    }
	    /*if(obj.getkenhId() != null && obj.getkenhId().trim().length() > 0) {
	    	query += " AND KBH.PK_SEQ = " + obj.getkenhId();
	    }*/

	    query +=" AND NPP.PK_SEQ IN (select npp_fk from phamvihoatdong where nhanvien_fk = " + obj.getuserId() + ") " +
	    		//" AND KBH.PK_SEQ IN (select kenh_fk as kbh_fk from nhanvien_kenh where nhanvien_fk = " + obj.getuserId() + ")" +
	    		" ORDER BY VUNG.TEN, KHUVUC.TEN, NPP.TEN, DDKD.TEN ";

	    System.out.println("[PdaNppSvl.CreateStaticData] query = " + query);
	    
	    rs1 = db.get(query);
	    
		int i = 2;
		
		try{
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
			cells.setColumnWidth(21, 15.0f);
			cells.setColumnWidth(22, 15.0f);
			cells.setColumnWidth(23, 15.0f);
			cells.setColumnWidth(24, 15.0f);
			cells.setColumnWidth(25, 15.0f);
						
			i=2;			
			if(rs1!=null){
			Cell cell = null;
			Style style;
			while(rs1.next())// lap den cuoi bang du lieu
			{
				//lay tu co so du lieu, gan bien
				String Channel = "GT";//rs1.getString("KBHTEN");
			
				String Region = rs1.getString("VUNGTEN");
				
				String Area = rs1.getString("KVTEN");
				
				String Distributor = rs1.getString("NPPTEN");
				
				String SalesRep = rs1.getString("DDKDTEN");				
				
				String Sitecode=rs1.getString("SITECODE");
				
				String Salessup =rs1.getString("GSBHTEN");
				
				long CHTT = rs1.getLong("SOKH");				
				long SOVT = CHTT == 0 ? 0 : rs1.getLong("SOVTPDA");
				long CODS = CHTT == 0 ? 0 : rs1.getLong("CODSPDA");
				long PTVT = CHTT == 0 ? 0 : SOVT * 100 / CHTT;
				long PTDS = SOVT == 0 ? 0 : CODS * 100 / SOVT;
				double DS = rs1.getDouble("DOANHSO");
				
				cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(Channel); //0
				cell = cells.getCell("AB" + Integer.toString(i)); cell.setValue(Region);//1
				cell = cells.getCell("AC" + Integer.toString(i)); cell.setValue(Area);//2
				cell = cells.getCell("AD" + Integer.toString(i)); cell.setValue(Salessup);//3
				cell = cells.getCell("AE" + Integer.toString(i)); cell.setValue(Sitecode);//4
				cell = cells.getCell("AF" + Integer.toString(i)); cell.setValue(Distributor);//5
				cell = cells.getCell("AG" + Integer.toString(i)); cell.setValue(SalesRep);//6
				style = cell.getStyle();
				style.setNumber(2);
				cell = cells.getCell("AH" + Integer.toString(i)); cell.setValue(CHTT);//7
				style = cell.getStyle();
				style.setNumber(2);
				cell = cells.getCell("AI" + Integer.toString(i)); cell.setValue(SOVT);//8
				style = cell.getStyle();
				style.setNumber(2);
				cell = cells.getCell("AJ" + Integer.toString(i)); cell.setValue(CODS);//9
				style = cell.getStyle();
				style.setNumber(2);
				cell = cells.getCell("AK" + Integer.toString(i)); cell.setValue(PTVT);//10
				style = cell.getStyle();
				style.setNumber(2);
				cell = cells.getCell("AL" + Integer.toString(i)); cell.setValue(PTDS);//11
				style = cell.getStyle();
				style.setNumber(2);
				cell = cells.getCell("AM" + Integer.toString(i)); cell.setValue(DS);//12
				style = cell.getStyle();
				style.setNumber(2);
				
				i++;
			}
			
			if(rs1!=null){
				rs1.close();
			}

			if(db!=null){
				db.shutDown();
			}
			
			if(i==2){
				obj.setMsg("Khong co bao cao trong thoi gian nay");
				return false;
			}
	
							
			setHidden(workbook,49);
		    return true;
		}else{
			obj.setMsg("Khong co bao cao trong thoi gian nay");
			return false;
		}
	}
	catch(Exception ex)
	{
		System.out.println("Khong The Tao Duoc Bao Cao :" + ex.toString());
		ex.printStackTrace();
		obj.setMsg("Khong The Tao Duoc Bao Cao :"+ex.toString());
		return false;
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
	
	private void setHidden(Workbook workbook,int i)
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
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}
