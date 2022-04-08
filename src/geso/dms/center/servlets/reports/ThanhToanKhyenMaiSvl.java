package geso.dms.center.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;


import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

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
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;


public class ThanhToanKhyenMaiSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
   
    public ThanhToanKhyenMaiSvl(){
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

		String nextJSP = "/AHF/pages/Center/ThanhToanKhuyenMaiTT.jsp";
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
		
		String nextJSP = "/AHF/pages/Center/ThanhToanKhuyenMaiTT.jsp";

    	if(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("unghang")))!= null)
    		obj.setUnghang("1");
		else
			obj.setUnghang("0");
    				
		System.out.println(obj.getUnghang());
		
		obj.setuserTen((String) session.getAttribute("userTen") != null ? (String) session
				.getAttribute("userTen") : "");

		obj.setYear(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("year"))) != null ? 
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("year")) : ""));

		obj.setMonth(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("month"))) != null ? 
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("month")) : ""));

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

		String sql = "";
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

		if (action.equals("Taomoi")) 
		{
			try 
			{
				if (action.equals("Taomoi"))
				{
					//response.setContentType("application/xlsm");
					//response.setHeader("Content-Disposition", "attachment; filename=BCXuatKhuyenMaiTT.xlsm");
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition", "attachment; filename=ThanhToanKhuyenMai.xls");
					
					boolean isTrue = CreatePivotTable(out, sql, obj);
					if (!isTrue)
					{					
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
			}
		}
		else 
		{
			session.setAttribute("obj", obj);
			response.sendRedirect(nextJSP);	
		}
	}
	
	private boolean CreatePivotTable(OutputStream out, String sql,IStockintransit obj) throws Exception 
	{ 																
		Workbook workbook = new Workbook();	

		dbutils db = new dbutils();
		String query = "select a.pk_seq, a.ten from nhaphanphoi a inner join khuvuc b on a.khuvuc_fk = b.pk_seq where a.trangthai = '1'";
		if(obj.getkhoId().length() > 0)
			query += " and a.khuvuc_fk = '" + obj.getkhuvucId() + "'";
		if(obj.getvungId().length() >0)
			query += " and b.vung_fk = '" + obj.getvungId() + "'";
		if(obj.getkenhId().length() > 0)
			query += " and a.pk_seq in (select npp_fk from nhapp_kbh where kbh_fk = '" + obj.getkenhId() + "')";
		
		query += " order by a.khuvuc_fk";
		System.out.println("Query lay npp: " + query + "\n");
		
		ResultSet rs = db.get(query);
		Hashtable<String, String> dsNpp = new Hashtable<String, String>();
		if(rs != null)
		{
			while(rs.next())
			{
				dsNpp.put(rs.getString("pk_seq"), rs.getString("ten"));
			}
			rs.close();
		}
		
		Enumeration<String> keys = dsNpp.keys();
		int i = 0;
		while(keys.hasMoreElements())
		{
			String key = keys.nextElement();
			try
			{
				CreateStaticHeader(workbook, obj, i, key, dsNpp.get(key));
				CreateStaticData(workbook, obj, sql, i, key);
				//workbook.getWorksheets().addSheet();
			}
			catch (Exception e) {}
			i++;
			System.out.println("i la: " + i + "\n");
		}
		dsNpp.clear();
		
		//workbook.getWorksheets().removeName("Sheet1");
		workbook.save(out);
		return true;

	}

	private void CreateStaticHeader(Workbook workbook, IStockintransit obj, int pos, String pk_seq, String nppTen)throws Exception 
	{
		try
		{
	 		Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(pos);
			worksheet.setName("NPP " + pk_seq);
			
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
		    
		    cell.setValue("BÁO CÁO THANH TOÁN KHUYẾN MÃI");  getCellStyle(workbook,"A1",Color.RED,true,14);	  	
		    
		    
		    cells.setRowHeight(2, 18.0f);
			cell = cells.getCell("A2");
			ReportAPI.getCellStyle(cell, Color.RED, true, 9, "Nhà phân phối: " + nppTen);   

		    
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("A4");
		    
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Tháng : " + obj.getMonth() + "" );
		    
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("B4"); 
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Năm : " + obj.getYear() + "" );
		    
		    cells.setRowHeight(4, 18.0f);
		    cell = cells.getCell("A5");getCellStyle(workbook,"A4",Color.NAVY,true,9);
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Ngày báo cáo: " + this.getDateTime() );
		    
		    cells.setRowHeight(5, 18.0f);
		    cell = cells.getCell("A6");getCellStyle(workbook,"A5",Color.NAVY,true,9);
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Được tạo bởi:  " + obj.getuserTen());

			cell = cells.getCell("DA1");		cell.setValue("KenhBanHang");    	ReportAPI.setCellHeader(cell);
			cell = cells.getCell("DB1");		cell.setValue("ChiNhanh"); 			ReportAPI.setCellHeader(cell);
			cell = cells.getCell("DC1");		cell.setValue("KhuVuc"); 			ReportAPI.setCellHeader(cell);
			cell = cells.getCell("DD1");		cell.setValue("MaNhaPhanPhoi");		ReportAPI.setCellHeader(cell);
			cell = cells.getCell("DE1");		cell.setValue("NhaPhanPhoi");  		ReportAPI.setCellHeader(cell);
			cell = cells.getCell("DF1");		cell.setValue("MaChuongTrinh"); 	ReportAPI.setCellHeader(cell);
			cell = cells.getCell("DG1");		cell.setValue("DienGiai");			ReportAPI.setCellHeader(cell);
			cell = cells.getCell("DH1");		cell.setValue("MaSanPham");			ReportAPI.setCellHeader(cell);			
			cell = cells.getCell("DI1");		cell.setValue("SanPham");			ReportAPI.setCellHeader(cell);
			cell = cells.getCell("DJ1");		cell.setValue("NhanHang");			ReportAPI.setCellHeader(cell);
			cell = cells.getCell("DK1");		cell.setValue("ChungLoai");			ReportAPI.setCellHeader(cell);
			cell = cells.getCell("DL1");		cell.setValue("Tinh/Thanh");		ReportAPI.setCellHeader(cell);
			cell = cells.getCell("DM1");		cell.setValue("Quan/Huyen");
			cell = cells.getCell("DN1");		cell.setValue("LoaiChuongTrinh");	ReportAPI.setCellHeader(cell);
			cell = cells.getCell("DO1");		cell.setValue("DaiDienKinhDoanh"); 	ReportAPI.setCellHeader(cell);
			cell = cells.getCell("DP1");		cell.setValue("MaKhachHang");		ReportAPI.setCellHeader(cell);
			cell = cells.getCell("DQ1");		cell.setValue("TenKhachHang");		ReportAPI.setCellHeader(cell);
			cell = cells.getCell("DR1");		cell.setValue("Ngay");				ReportAPI.setCellHeader(cell);
			cell = cells.getCell("DS1");		cell.setValue("SoLuong(Le)");		ReportAPI.setCellHeader(cell);
			cell = cells.getCell("DT1");		cell.setValue("SoTien");			ReportAPI.setCellHeader(cell);
			cell = cells.getCell("DU1");		cell.setValue("SoLuong(Thung)");	ReportAPI.setCellHeader(cell);
			
			worksheets.addCopy(pos);
		}
		catch(Exception e)
		{
			throw new Exception(e.getMessage());
		}
	}

	private boolean CreateStaticData(Workbook workbook, IStockintransit obj, String sql, int pos, String pk_seq) throws Exception 
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(pos);
		Cells cells = worksheet.getCells();
		dbutils db = new dbutils();

		String query = getQueryString(obj);
		
		if (obj.getkenhId().length() > 0) {
			query += " and KBH.PK_SEQ='" + obj.getkenhId() +"' ";
		}
		/*
		if (obj.getvungId().length() > 0) {
			query += " and VUNG.PK_SEQ='" + obj.getvungId() +"' ";
		}
		if (obj.getkhuvucId().length() > 0) {
			query += " and KHUVUC.PK_SEQ='" + obj.getkhuvucId() +"' ";
		}*/
	
		query +=" and NPP.PK_SEQ = '" + pk_seq  +"' ";
		
		if (obj.getPrograms().length() > 0) {
			query += " and CTKM.SCHEME='" + obj.getPrograms() +"' ";
		}
		
		if(obj.getUnghang().equals("0")){
			query = query + " AND CTKM.KHO_FK ='100000' ";
		}
		
		System.out.print("BC Xuat khuyen mai TT cua Npp " + pk_seq + ": " + query + "\n");
		ResultSet rs = db.get(query);
		int i = 2;
		if (rs != null) 
		{
			try 
			{
				for(int j = 0; j < 21; j++)
					cells.setColumnWidth(j, 15.0f);
				
				
				Cell cell = null;
				while (rs.next())
				{
					String Chanel = rs.getString("Channel");
					String Region = rs.getString("Region");
					String area = rs.getString("Area");

					String Distributor = rs.getString("Distributor");
					String PromotionProgram = rs.getString("PROGRAM");

					String Brand = "" ;
					
					if(rs.getString("Brand") == null)
						Brand = "Khong xac dinh";
					else
						Brand = rs.getString("Brand");
					
					String Category = "";

					if(rs.getString("Category") == null)
						Category = "Khong xac dinh";
					else
						Category = rs.getString("Category");
					
					String Province = rs.getString("Province");
					String Town = rs.getString("Town");
					String DisCode = rs.getString("DISTRIBUTORID");
					String ProgramCode = rs.getString("PROGRAM_CODE");
					String ProgramType = rs.getString("PROGRAM_TYPE");
					
					String SKUCode = "";
					
					if(rs.getString("SKU_code") == null)
						SKUCode = "Khong xac dinh";
					else
						SKUCode = rs.getString("SKU_code");

					String SKU = "";
					if(rs.getString("SKU") == null)
						SKU = "Khong xac dinh";
					else
						SKU = rs.getString("SKU");
					
					String SaleRep = rs.getString("SALESREP");
					//String CustomerKey = rs.getString("khId");
					String Customer = rs.getString("Customer");
					int CustomerKey = rs.getInt("khId");
					String Offdate = rs.getString("DATE");

					float Piece = rs.getFloat("Piece");
					float Amount = rs.getFloat("Amount");
					float Quanlity = rs.getFloat("QUANTITY");

										
					cell = cells.getCell("DA" + Integer.toString(i)); cell.setValue(Chanel);
					cell = cells.getCell("DB" + Integer.toString(i)); cell.setValue(Region);
					cell = cells.getCell("DC" + Integer.toString(i)); cell.setValue(area);
					cell = cells.getCell("DD" + Integer.toString(i)); cell.setValue(DisCode);
					cell = cells.getCell("DE" + Integer.toString(i)); cell.setValue(Distributor); 
					cell = cells.getCell("DF" + Integer.toString(i)); cell.setValue(ProgramCode);
					cell = cells.getCell("DG" + Integer.toString(i)); cell.setValue(PromotionProgram);
					cell = cells.getCell("DH" + Integer.toString(i)); cell.setValue(SKUCode);
					cell = cells.getCell("DI" + Integer.toString(i)); cell.setValue(SKU);
					cell = cells.getCell("DJ" + Integer.toString(i)); cell.setValue(Brand);
					cell = cells.getCell("DK" + Integer.toString(i)); cell.setValue(Category);
					cell = cells.getCell("DL" + Integer.toString(i)); cell.setValue(Province);
					cell = cells.getCell("DM" + Integer.toString(i)); cell.setValue(Town);					
					cell = cells.getCell("DN" + Integer.toString(i)); cell.setValue(ProgramType);
					cell = cells.getCell("DO" + Integer.toString(i)); cell.setValue(SaleRep);
					cell = cells.getCell("DP" + Integer.toString(i)); cell.setValue(CustomerKey);
					cell = cells.getCell("DQ" + Integer.toString(i)); cell.setValue(Customer);
					cell = cells.getCell("DR" + Integer.toString(i)); cell.setValue(Offdate);
					cell = cells.getCell("DS" + Integer.toString(i)); cell.setValue(Piece);
					cell = cells.getCell("DT" + Integer.toString(i)); cell.setValue(Amount);
					cell = cells.getCell("DU" + Integer.toString(i)); cell.setValue(Quanlity);
					i++;
				}
				
				if(rs != null) 
					rs.close();
				if(db != null) 
					db.shutDown();
				
				//tao pivot
				if(i > 2)
				{
					//ReportAPI.setHidden(workbook, 92);
					PivotTables pivotTables = worksheet.getPivotTables();
					
					String pos2 = Integer.toString(i - 1); 
					int index = pivotTables.add("=DA1:DU" + pos2, "A10", "PivotTable1");	
					PivotTable pivotTable = pivotTables.get(index);
	
					pivotTable.setAutoFormat(true);
					
					pivotTable.setRowGrand(false);
					pivotTable.setColumnGrand(true);
					
					pivotTable.addFieldToArea(PivotFieldType.ROW, 0);     
					pivotTable.addFieldToArea(PivotFieldType.ROW, 1);
					pivotTable.addFieldToArea(PivotFieldType.ROW, 2);
					pivotTable.addFieldToArea(PivotFieldType.ROW, 5);   pivotTable.getRowFields().get(3).setAutoSubtotals(false);
					pivotTable.addFieldToArea(PivotFieldType.ROW, 6);
					
					//pivotTable.addFieldToArea(PivotFieldType.COLUMN, 17);
					
					pivotTable.addFieldToArea(PivotFieldType.DATA, 18);
					pivotTable.getDataFields().get(0).setShowAllItems(true);
					pivotTable.getDataFields().get(0).setDisplayName("SoLuongLe");
					
					pivotTable.addFieldToArea(PivotFieldType.DATA, 19);
					pivotTable.getDataFields().get(1).setNumber(3);
					pivotTable.getDataFields().get(1).setDisplayName("SoTien");
	
					pivotTable.addFieldToArea(PivotFieldType.COLUMN, pivotTable.getDataField());
				}
		    	return true;
			} 
			catch (Exception e) {}
		} 
		
		return false;
			
	}

	private String getQueryString(IStockintransit obj) 
	{
		String query = "SELECT KBH.PK_SEQ AS CHANNELID, KBH.TEN AS CHANNEL,VUNG.PK_SEQ AS REGIONID,VUNG.TEN AS REGION," +
		"KHUVUC.PK_SEQ AS AREAID,KHUVUC.TEN AS AREA,  " +
		"TINH.PK_SEQ AS PROVINCEID,TINH.TEN AS PROVINCE, QUAN.PK_SEQ AS TOWNID, " +
		"QUAN.TEN AS TOWN,ISNULL(GSBH.PK_SEQ,0) AS SALESSUPID,  " +
		"ISNULL(GSBH.TEN, 'Khong xac dinh') AS SALESSUP, NPP.PK_SEQ AS DISTRIBUTORID, " +
		"NPP.TEN AS DISTRIBUTOR, DDKD.PK_SEQ AS SALEREPID, isnull(DDKD.TEN, 'Chua khai bao') AS SALESREP,  " +
		"CAST(KH.PK_SEQ as nvarchar(8)) as khId, KH.ten AS CUSTOMER, DH.NGAYNHAP AS DATE,CTKM.SCHEME AS PROGRAM_CODE, " +
		"ISNULL(ctkm.diengiai + '__' + ctkm.tungay + '-' + ctkm.denngay, 'Khong xac dinh') AS PROGRAM,  " +
		"CASE WHEN CTKM.LOAICT = 1 THEN 'Binh Thuong' WHEN CTKM.LOAICT = 2 THEN 'On Top'  " +
		"WHEN CTKM.LOAICT = 3 THEN 'Tich Luy' else 'Chua Xac Dinh' END AS PROGRAM_TYPE,  " +
		"NH.PK_SEQ AS BRANDID, NH.TEN AS BRAND,CL.PK_SEQ AS CATEGORYID,CL.TEN AS CATEGORY, " +
		"SP.MA AS SKU_CODE,SP.TEN AS SKU,ISNULL(TRAKM.SOLUONG, 0) AS PIECE,   " +
		"ISNULL(TRAKM.TONGGIATRI, '0') AS AMOUNT, CASE WHEN (TRAKM.SOLUONG/QC.SOLUONG1) IS NULL " +
		"THEN 0 ELSE (TRAKM.SOLUONG/QC.SOLUONG1) END AS QUANTITY  " +
		"FROM DONHANG_CTKM_TRAKM TRAKM " +
		"INNER JOIN CTKHUYENMAI CTKM ON TRAKM.CTKMID = CTKM.PK_SEQ " +
		"								AND SUBSTRING(CTKM.DENNGAY, 1 , 7)  = '"+ obj.getYear() + "-" + obj.getMonth() + "' " +
		"INNER JOIN DONHANG DH ON TRAKM.DONHANGID = DH.PK_SEQ AND DH.TRANGTHAI='1' " +
		"INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = DH.NPP_FK " +
		"INNER JOIN TINHTHANH TINH ON TINH.PK_SEQ = NPP.TINHTHANH_FK " +
		"INNER JOIN QUANHUYEN QUAN ON QUAN.PK_SEQ = TINH.PK_SEQ  " +
		"LEFT JOIN SANPHAM SP ON TRAKM.SPMA = SP.MA " +
		"LEFT JOIN NHANHANG NH ON NH.PK_SEQ = SP.NHANHANG_FK " +
		"LEFT JOIN CHUNGLOAI CL ON CL.PK_SEQ = SP.CHUNGLOAI_FK " +
		"INNER JOIN KHACHHANG KH ON KH.PK_SEQ = DH.KHACHHANG_FK " +
		"LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK = SP.PK_SEQ  " +
		"INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ = KH.KBH_FK " +
		"INNER JOIN DAIDIENKINHDOANH DDKD ON DDKD.PK_SEQ = DH.DDKD_FK " +
		"LEFT JOIN GIAMSATBANHANG GSBH ON GSBH.PK_SEQ = DH.GSBH_FK " +
		"INNER JOIN KHUVUC KHUVUC ON KHUVUC.PK_SEQ = NPP.KHUVUC_FK " +
		"INNER JOIN VUNG VUNG ON VUNG.PK_SEQ = KHUVUC.VUNG_FK  " +
		"WHERE ISNULL(TRAKM.SOLUONG, '0') >= 0 " +
		"AND DH.PK_SEQ NOT IN " +
		"(" +
		"	SELECT DONHANG_FK FROM DONHANGTRAVE " +
		"	WHERE DONHANG_FK IS NOT NULL AND TRANGTHAI = '3' " +
		") ";
		
		return query;
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

	private String getDateTime() 
	{
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
	
	private void setAn(Workbook workbook, int i) 
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();
		for (int j = 26; j <= i; j++) {
			cells.hideColumn(j);
		}
	}

}
