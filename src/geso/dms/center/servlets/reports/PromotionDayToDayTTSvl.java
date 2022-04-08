package geso.dms.center.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;

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
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTableAutoFormatType;
import com.aspose.cells.PivotTables;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.sun.mail.iap.Response;

public class PromotionDayToDayTTSvl extends HttpServlet 
{	
	
	public PromotionDayToDayTTSvl() {
		super();
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Utility util = new Utility();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");		
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		String querystring = request.getQueryString();

		String userId = util.getUserId(querystring);
		String userTen = (String) session.getAttribute("userTen");

		obj.setuserId(userId);
		obj.setuserTen(userTen);
		obj.init();
		session.setAttribute("obj", obj);
		session.setAttribute("util", util);
		session.setAttribute("userTen", userTen);
		session.setAttribute("userId", userId);

		String nextJSP = "/AHF/pages/Center/PromotionDayToDayTT.jsp";
		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		OutputStream out = response.getOutputStream();
		
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		obj.init();
		
		Utility util = new Utility();
		
		String nextJSP = "/AHF/pages/Center/PromotionDayToDayTT.jsp";

    	if(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("unghang")))!= null)
    		obj.setUnghang("1");
		else
			obj.setUnghang("0");
    				
		System.out.println(obj.getUnghang());
		
		obj.setuserTen((String) session.getAttribute("userTen") != null ? (String) session
				.getAttribute("userTen") : "");

	/*	obj.setYear(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("year"))) != null ? 
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("year")) : ""));

		obj.setMonth(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("month"))) != null ? 
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("month")) : ""));*/

		obj.settungay(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
		obj.setdenngay(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
		
		obj.setuserId((String) session.getAttribute("userId") != null ? 
				(String) session.getAttribute("userId") : "");

		obj.setnppId((String) util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"))) != null ? 
				(String) util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")) : ""));

		obj.setPrograms(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("programs"))) != null ?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("programs")) : ""));

		obj.setvungId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"))) != null ? 
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")) : ""));

		obj.setkhuvucId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"))) != null ? 
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")) : ""));

		obj.setkenhId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId"))) != null ? 
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")) : ""));

		 String []fieldsHien = request.getParameterValues("fieldsHien");
		 obj.setFieldShow(fieldsHien!=null? fieldsHien:null);
		 
		String sql = "";
		
		sql=sql +  " and npp.pk_seq in "+ util.quyen_npp(obj.getuserId())  +
		"  and kbh.pk_seq in "+ util.quyen_kenh(obj.getuserId()) ;
		
		if (obj.getkenhId().length() > 0) {
			sql += " and kbh.PK_SEQ='" + obj.getkenhId() +"' ";
		}
		if (obj.getvungId().length() > 0) {
			sql += " and v.PK_SEQ='" + obj.getvungId() +"' ";
		}
		if (obj.getkhuvucId().length() > 0) {
			sql += " and k.PK_SEQ='" + obj.getkhuvucId() +"' ";
		}
		if (obj.getnppId().length() > 0) {
			sql +=" and npp.PK_SEQ='" + obj.getnppId()  +"' ";
		}
		if (obj.getPrograms().length() > 0) {
			sql += " and ctkm.SCHEME='" + obj.getPrograms() +"' ";
		}
		String action = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));
		if(action.equals("search"))
		{
			obj.init();
			
		}
		if (action.equals("Taomoi")) 
		{
			try 
			{
				if (action.equals("Taomoi"))
				{
					response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition", "attachment; filename=BCXuatKhuyenMaiTT.xlsm");
			        
					boolean isTrue = CreatePivotTable(out, sql, obj);
					if (!isTrue) {					
						response.setContentType("text/html");
			            PrintWriter writer = new PrintWriter(out);
			            writer.print("Khong co du lieu trong khoang thoi gian da chon");
			            writer.close();
					}
				}else{
					response.sendRedirect(nextJSP);
				}
			} 
			catch (Exception ex) 
			{
				obj.setMsg(ex.getMessage());				
//				response.sendRedirect(nextJSP);	
			}

			
		}
		else 
		{
			session.setAttribute("obj", obj);
			//session.setAttribute("userId", obj.getuserId());
			response.sendRedirect(nextJSP);	
		}
	}

	private boolean CreatePivotTable(OutputStream out, String sql,IStockintransit obj) throws Exception 
	{ 																
		//String chuoi=getServletContext().getInitParameter("path") + "\\XuatKhuyenMaiTTDayToDay.xlsm";
		
		//FileInputStream fstream = new FileInputStream(chuoi);
		File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\XuatKhuyenMaiTTDayToDay.xlsm");
		FileInputStream fstream = new FileInputStream(f) ;
		Workbook workbook = new Workbook();
		
		workbook.open(fstream);
		
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		
		CreateStaticHeader(workbook, obj);
		
		boolean isTrue = CreateStaticData(workbook, obj,sql);
		
		if(!isTrue){
			return false;
		}else{
			workbook.save(out);
		}	
		
		fstream.close();
		return true;

	}

	private void CreateStaticHeader(Workbook workbook, IStockintransit obj)throws Exception 
	{
		try
		{
	 		Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");
			
		    Cells cells = worksheet.getCells();
		    Style style;
		    Font font = new Font();
		    font.setColor(Color.RED);//mau chu
		    font.setSize(16);// size chu
		   	font.setBold(true);
		   	
		    cells.setRowHeight(0, 20.0f);
		    Cell cell = cells.getCell("A1");
		    style = cell.getStyle();
		    style.setFont(font); 
		    style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu 	   
		    
		    cell.setValue("BÁO CÁO XUẤT KHUYẾN MÃI NGÀY");  getCellStyle(workbook,"A1",Color.RED,true,14);	  	
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("A3");
		    
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Tháng : " + obj.getMonth() + "" );
		    
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("B3"); 
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Năm : " + obj.getYear() + "" );
		    
		    cells.setRowHeight(4, 18.0f);
		    cell = cells.getCell("A4");getCellStyle(workbook,"A4",Color.NAVY,true,9);
		    cell.setValue("Ngày báo cáo: " + this.getDateTime());
		    
		    cells.setRowHeight(5, 18.0f);
		    cell = cells.getCell("A5");getCellStyle(workbook,"A5",Color.NAVY,true,9);
		    cell.setValue("Được tạo bởi:  " + obj.getuserTen());

			cell = cells.getCell("AA1");		cell.setValue("KenhBanHang");
			cell = cells.getCell("AB1");		cell.setValue("ChiNhanh");
			cell = cells.getCell("AC1");		cell.setValue("KhuVuc");
			cell = cells.getCell("AD1");		cell.setValue("MaNhaPhanPhoi");
			cell = cells.getCell("AE1");		cell.setValue("NhaPhanPhoi");
			cell = cells.getCell("AF1");		cell.setValue("MaChuongTrinh");
			cell = cells.getCell("AG1");		cell.setValue("DienGiai");
			cell = cells.getCell("AH1");		cell.setValue("MaSanPham");			
			cell = cells.getCell("AI1");		cell.setValue("SanPham");
			cell = cells.getCell("AJ1");		cell.setValue("NhanHang");
			cell = cells.getCell("AK1");		cell.setValue("ChungLoai");
			cell = cells.getCell("AL1");		cell.setValue("Tinh/Thanh");
			cell = cells.getCell("AM1");		cell.setValue("Quan/Huyen");
			cell = cells.getCell("AN1");		cell.setValue("LoaiChuongTrinh");
			cell = cells.getCell("AO1");		cell.setValue("DaiDienKinhDoanh");
			cell = cells.getCell("AP1");		cell.setValue("MaKhachHang");
			cell = cells.getCell("AQ1");		cell.setValue("TenKhachHang");
			cell = cells.getCell("AR1");		cell.setValue("Ngay");
			cell = cells.getCell("AS1");		cell.setValue("SoLuong(Le)");
			cell = cells.getCell("AT1");		cell.setValue("SoTien");
			cell = cells.getCell("AU1");		cell.setValue("SoLuong(Thung)");
			cell = cells.getCell("AV1");		cell.setValue("DoanhSo");
			
		}
		catch(Exception e)
		{
			throw new Exception(e.getMessage());
		}
	}

	private boolean CreateStaticData(Workbook workbook,IStockintransit obj,String sql)throws Exception 
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		Cells cells = worksheet.getCells();
		dbutils db = new dbutils();

		String query = 	"SELECT  CTKM.PK_SEQ AS CTKMID ,DH.PK_SEQ AS DHID, KBH.PK_SEQ AS CHANNELID, KBH.TEN AS CHANNEL,VUNG.PK_SEQ AS REGIONID,VUNG.TEN AS REGION, KHUVUC.PK_SEQ AS AREAID,KHUVUC.TEN AS AREA, "+  
    " TINH.PK_SEQ AS PROVINCEID, ISNULL(TINH.TEN,'Chưa xác định')  AS PROVINCE, QUAN.PK_SEQ AS TOWNID,  ISNULL(QUAN.TEN,'Chưa xác định') AS TOWN, "+
    " ISNULL(GSBH.PK_SEQ,0) AS SALESSUPID,   ISNULL(GSBH.TEN, 'Khong xac dinh') AS SALESSUP, NPP.PK_SEQ AS DISTRIBUTORID,  NPP.TEN AS DISTRIBUTOR,  "+
    " DDKD.PK_SEQ AS SALEREPID, isnull(DDKD.TEN, 'Chưa khai báo') AS SALESREP,   KH.SMARTID as khId, KH.ten AS CUSTOMER, DH.NGAYNHAP "+ 
    " AS DATE,CTKM.SCHEME AS PROGRAM_CODE,  ISNULL(ctkm.diengiai + '__' + ctkm.tungay + '-' + ctkm.denngay, 'Không xác định') AS PROGRAM, "+  
    " CASE WHEN CTKM.LOAICT = 1 THEN N'Bình thường' WHEN CTKM.LOAICT = 2 THEN 'On Top'  "+
    " WHEN CTKM.LOAICT = 3 THEN 'Tích lũy' else 'Chua Xac Dinh' END AS PROGRAM_TYPE,   NH.PK_SEQ AS BRANDID, NH.TEN AS BRAND,CL.PK_SEQ AS CATEGORYID, "+
    " CL.TEN AS CATEGORY,  SP.MA AS SKU_CODE,SP.TEN AS SKU,ISNULL(DH.SOLUONG, 0) AS PIECE,    ISNULL(DH.TONGGIATRI, '0') AS AMOUNT, "+ 
    " CASE WHEN (DH.SOLUONG/QC.SOLUONG1) IS NULL  THEN 0 ELSE (DH.SOLUONG/QC.SOLUONG1) END AS QUANTITY, "+
    " ( "+
    " select   sum(c.soluong * c.giamua ) as doanhso "+  
    " from donhang dh1 "+
    " inner join donhang_ctkm_trakm b on dh1.PK_SEQ=B.DONHANGID "+
    " inner join donhang_sanpham c on b.donhangId = c.donhang_fk "+     
    " inner join ctkm_dkkm d on B.CTKMID = d.ctkhuyenmai_fk   "+
    " inner join dieukienkm_sanpham e on d.dkkhuyenmai_fk = e.dieukienkhuyenmai_fk "+  
    " and c.sanpham_fk = e.sanpham_fk "+
    " where   b.CTKMID =DH.CTKMID AND c.donhang_fk = DH.PK_SEQ "+
    " ) AS DOANHSO "+
    " FROM ( "+
    " SELECT  DH.PK_SEQ,DH.NPP_FK,DH.KBH_FK,DH.DDKD_FK,DH.GSBH_FK,DH.KHACHHANG_FK,DH.NGAYNHAP "+
    " ,TRAKM.CTKMID,TRAKM.SPMA,TRAKM.SOLUONG,TRAKM.TONGGIATRI FROM "+ 
    " DONHANG  DH "+
    " INNER JOIN  DONHANG_CTKM_TRAKM TRAKM  ON TRAKM.DONHANGID = DH.PK_SEQ   "+
    " WHERE DH.TRANGTHAI='1' AND DH.NGAYNHAP >='"+obj.gettungay()+"' and dh.ngaynhap <='"+obj.getdenngay()+"' "+
    " AND DH.PK_SEQ NOT IN (	SELECT DONHANG_FK FROM DONHANGTRAVE "+ 	
    " WHERE DONHANG_FK IS NOT NULL AND TRANGTHAI = '3' ) "+  
    " ) DH "+
    " INNER JOIN NHAPHANPHOI NPP ON DH.NPP_FK=NPP.PK_SEQ "+
    " INNER JOIN CTKHUYENMAI CTKM ON CTKM.PK_SEQ=DH.CTKMID "+
    " LEFT JOIN TINHTHANH TINH ON TINH.PK_SEQ = NPP.TINHTHANH_FK   "+
    " LEFT JOIN QUANHUYEN QUAN ON QUAN.PK_SEQ = NPP.QUANHUYEN_FK "+   
    " LEFT JOIN SANPHAM SP ON DH.SPMA = SP.MA    "+
    " LEFT JOIN NHANHANG NH ON NH.PK_SEQ = SP.NHANHANG_FK   "+
    " LEFT JOIN CHUNGLOAI CL ON CL.PK_SEQ = SP.CHUNGLOAI_FK  "+
    " INNER JOIN KHACHHANG KH ON KH.PK_SEQ = DH.KHACHHANG_FK  "+ 
    " LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK = SP.PK_SEQ    "+
    " INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ =DH.KBH_FK  "+
    " INNER JOIN DAIDIENKINHDOANH DDKD ON DDKD.PK_SEQ = DH.DDKD_FK "+
    " INNER JOIN GIAMSATBANHANG GSBH ON GSBH.PK_SEQ = DH.GSBH_FK "+  
    " INNER JOIN KHUVUC KHUVUC ON KHUVUC.PK_SEQ = NPP.KHUVUC_FK "+
    " INNER JOIN VUNG VUNG ON VUNG.PK_SEQ = KHUVUC.VUNG_FK where 1=1";
		
		
		if (obj.getkenhId().length() > 0) {
			query += " and KBH.PK_SEQ='" + obj.getkenhId() +"' ";
		}
		if (obj.getvungId().length() > 0) {
			query += " and VUNG.PK_SEQ='" + obj.getvungId() +"' ";
		}
		if (obj.getkhuvucId().length() > 0) {
			query += " and KHUVUC.PK_SEQ='" + obj.getkhuvucId() +"' ";
		}
		if (obj.getnppId().length() > 0) {
			query +=" and NPP.PK_SEQ='" + obj.getnppId()  +"' ";
		}
		if (obj.getPrograms().length() > 0) {
			query += " and CTKM.SCHEME='" + obj.getPrograms() +"' ";
		}
		
		if(obj.getUnghang().equals("0")){
			query = query + " AND CTKM.KHO_FK ='100000' ";
		}
		query = query + " order by DH.PK_SEQ ,CTKM.PK_SEQ";
		System.out.print("1.BC Xuat khuyen mai TT: " + query);
		ResultSet rs = db.get(query);
		int i = 2;
		if (rs != null) 
		{
			try 
			{
				cells.setColumnWidth(0, 10.0f);
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
				cells.setColumnWidth(20, 15.0f);
				
				Cell cell = null;
				String dhid="";
				String Chanel="";
				String Region ="";
				String area ="";
				
				String Distributor = "";
				String PromotionProgram ="";

				String Brand = "" ;
				
				String Province ="";
				String Town = "";
				String DisCode = "";
				String ProgramCode = "";
				String ProgramType ="";
				String SKUCode = "";
				
				String SaleRep ="";
				String Customer = "";
				int CustomerKey=0;
				String Offdate ="";
				String SKU = "";
				String CTKMID="";
				double Amount=0;
				 double Piece = 0;
					double Quanlity = 0;
					double doanhso=0;
				while (rs.next())
				{
					
					
					 Chanel = rs.getString("Channel");
					 Region = rs.getString("Region");
					 area = rs.getString("Area");
					
					 Distributor = rs.getString("Distributor");
					 PromotionProgram = rs.getString("PROGRAM");

					 Brand = "" ;
					
					if(rs.getString("Brand") == null)
						Brand = "Khong xac dinh";
					else
						Brand = rs.getString("Brand");
						
					String Category = "";

					if(rs.getString("Category") == null)
						Category = "Khong xac dinh";
					else
						Category = rs.getString("Category");
					
					 Province = rs.getString("Province");
					 Town = rs.getString("Town");
					 DisCode = rs.getString("DISTRIBUTORID");
					 ProgramCode = rs.getString("PROGRAM_CODE");
					 ProgramType = rs.getString("PROGRAM_TYPE");
					
					 SKUCode = "";
					
					if(rs.getString("SKU_code") == null)
						SKUCode = "Khong xac dinh";
					else
						SKUCode = rs.getString("SKU_code");

					 SKU = "";
					if(rs.getString("SKU") == null)
						SKU = "Khong xac dinh";
					else
						SKU = rs.getString("SKU");
					
					 SaleRep = rs.getString("SALESREP");
					 Customer = rs.getString("Customer");
					 CustomerKey = Integer.parseInt(rs.getString("khId"));
					 Offdate = rs.getString("DATE");

			
					 
					if (rs.getString("Piece") != null) {
						Piece = rs.getDouble("Piece");
					}

					 Amount = rs.getDouble("Amount");
				
					if (rs.getString("QUANTITY") != null) {
						Quanlity = rs.getDouble("QUANTITY");
					}
					
					 doanhso =rs.getDouble("DOANHSO");
										
					cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(Chanel);
					cell = cells.getCell("AB" + Integer.toString(i)); cell.setValue(Region);
					cell = cells.getCell("AC" + Integer.toString(i)); cell.setValue(area);
					cell = cells.getCell("AD" + Integer.toString(i)); cell.setValue(DisCode);
					cell = cells.getCell("AE" + Integer.toString(i)); cell.setValue(Distributor); 
					cell = cells.getCell("AF" + Integer.toString(i)); cell.setValue(ProgramCode);
					cell = cells.getCell("AG" + Integer.toString(i)); cell.setValue(PromotionProgram);
					cell = cells.getCell("AH" + Integer.toString(i)); cell.setValue(SKUCode);
					cell = cells.getCell("AI" + Integer.toString(i)); cell.setValue(SKU);
					cell = cells.getCell("AJ" + Integer.toString(i)); cell.setValue(Brand);
					cell = cells.getCell("AK" + Integer.toString(i)); cell.setValue(Category);
					cell = cells.getCell("AL" + Integer.toString(i)); cell.setValue(Province);
					cell = cells.getCell("AM" + Integer.toString(i)); cell.setValue(Town);					
					cell = cells.getCell("AN" + Integer.toString(i)); cell.setValue(ProgramType);
					cell = cells.getCell("AO" + Integer.toString(i)); cell.setValue(SaleRep);
					cell = cells.getCell("AP" + Integer.toString(i)); cell.setValue(CustomerKey);
					cell = cells.getCell("AQ" + Integer.toString(i)); cell.setValue(Customer);
					cell = cells.getCell("AR" + Integer.toString(i)); cell.setValue(Offdate);
					cell = cells.getCell("AS" + Integer.toString(i)); cell.setValue(Piece);
					cell = cells.getCell("AT" + Integer.toString(i)); cell.setValue(Amount);
					cell = cells.getCell("AU" + Integer.toString(i)); cell.setValue(Quanlity);
					if(dhid.equals(rs.getString("dhid")) && rs.getString("CTKMID").equals(CTKMID)){
						dhid=rs.getString("dhid");
						CTKMID=rs.getString("CTKMID");
						doanhso=0;
					}
					cell = cells.getCell("AV" + Integer.toString(i)); cell.setValue(doanhso);
					i++;

				}
				
				
				if(rs != null) rs.close();
				if(db != null)  db.shutDown();
				
				if(i==2){
					throw new Exception("Khong co bao cao voi dieu kien da chon");
				}
		    	return true;

				
			} 
			catch (Exception e) 
			{	System.out.println(e.toString());
				if (db != null)
					db.shutDown();	
				throw new Exception(e.getMessage());
			}


		} 
		else 
		{
			if (db != null)
				db.shutDown();
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

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	private String getPiVotName()
	{
		String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
	    String name = sdf.format(cal.getTime());
	    name = name.replaceAll("-", "");
	    name = name.replaceAll(" ", "_");
	    name = name.replaceAll(":", "");
	    return "_" + name;
	    
	}
	private void setAn(Workbook workbook, int i) {
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();
		for (int j = 26; j <= i; j++) {
			cells.hideColumn(j);
		}

	}
	
}
