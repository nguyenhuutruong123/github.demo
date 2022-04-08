package geso.dms.center.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;
import geso.dms.center.util.WebService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
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

import com.aspose.cells.BorderLineType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;


public class PromotionReportTTSvl extends HttpServlet 
{	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public PromotionReportTTSvl() {
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
		String view = request.getParameter("view") != null ? request.getParameter("view") : "";
		obj.setView(view);
		obj.setuserId(userId);
		obj.setuserTen(userTen);
		obj.init();
		session.setAttribute("obj", obj);
		session.setAttribute("util", util);
		session.setAttribute("userTen", userTen);
		session.setAttribute("userId", userId);

		String nextJSP = "/AHF/pages/Center/RPromotionReport_center.jsp";
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
		
		String view = request.getParameter("view") != null ? request.getParameter("view") : "";
		obj.setView(view);
		
		Utility util = new Utility();
		
		String nextJSP = "/AHF/pages/Center/RPromotionReport_center.jsp";

    	if(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("unghang")))!= null)
    		obj.setUnghang("1");
		else
			obj.setUnghang("0");
    						
		
		obj.setuserTen((String) session.getAttribute("userTen") != null ? (String) session
				.getAttribute("userTen") : "");

		String year = request.getParameter("year") != null ? request.getParameter("year") : "";
		obj.setYear(util.antiSQLInspection(year) );

		String month = util.antiSQLInspection(request.getParameter("year") != null ? request.getParameter("month") : "");
		obj.setMonth(util.antiSQLInspection(month));

		obj.setuserId((String) session.getAttribute("userId") != null ? 
				(String) session.getAttribute("userId") : "");

		
		String nppId = util.antiSQLInspection(request.getParameter("nppId") != null ? request.getParameter("nppId") : "");
		obj.setnppId(nppId);
		/*if(view.equals("NPP"))
		{
		
			obj.setnppId( util.getIdNhapp(obj.getuserId()) );
		}*/

		String programs = util.antiSQLInspection(request.getParameter("programs") != null ? request.getParameter("programs") : "");
		obj.setPrograms(programs);

		String vungId = util.antiSQLInspection(request.getParameter("vungId") != null ? request.getParameter("vungId") : "");
		obj.setvungId(vungId);

		String khuvucId = util.antiSQLInspection(request.getParameter("khuvucId") != null ? request.getParameter("khuvucId") : "");
		
		obj.setkhuvucId(khuvucId);
		
		String tungay = util.antiSQLInspection(request.getParameter("tungay") != null ? request.getParameter("tungay") : "");
		
		obj.settungay(tungay);
		
		String denngay = util.antiSQLInspection(request.getParameter("denngay") != null ? request.getParameter("denngay") : "");
		
		obj.setdenngay(denngay);

		String kenhId = util.antiSQLInspection(request.getParameter("kenhId") != null ? request.getParameter("kenhId") : "");
		
		obj.setkenhId(kenhId);
		
		if(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dhchot")))!= null)
    		obj.setDhChot("1");
		else
			obj.setDhChot("0");

		 String []fieldsHien = request.getParameterValues("fieldsHien");
		 obj.setFieldShow(fieldsHien!=null? fieldsHien:null);
		 
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
		
		
		
		obj.init();
		String action = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));
		System.out.println("User Id "+obj.getuserId());
		System.out.println("action"+action);
		if(action.equals("search"))
		{
			obj.init();
		}
		if (action.equals("xuatexel")) 
		{
			
			
			try 
			{
				if (action.equals("xuatexel"))
				{
					response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition", "attachment; filename=BCXKM.xlsm");
					Workbook workbook = new Workbook();
					TaoBaoCao(workbook,obj.gettungay(),obj.getdenngay(),obj.getnppId(), obj);
					
					workbook.save(out);	
	
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
		
		
		if (action.equals("xuatexelcu")) 
		{
			
			
			try 
			{
				if (action.equals("xuatexelcu"))
				{
					response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition", "attachment; filename=BCXuatKhuyenMaiTT.xlsm");
			        System.out.println(")))))))_________________+ vô dây đi");
					boolean isTrue = CreatePivotTable2(out, sql, obj);
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
		
		if (action.equals("Taobcmoi")) 
		{
			
			
			try 
			{
				if (action.equals("Taobcmoi"))
				{
					response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition", "attachment; filename=BCXuatKhuyenMaiTT.xlsm");
			        System.out.println(")))))))_________________+ vô dây đi");
					boolean isTrue = CreatePivotTable1(out, sql, obj);
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
		
		
		
		if (action.equals("Taomoi")) 
		{
			
			
			try 
			{
				if (action.equals("Taomoi"))
				{
					System.out.println("lvl 2");
					String query = setQuery(obj,util).replaceAll("\t", "").replaceAll("\n", "");
				
					/*String filename=this.getDateTime1();
					//ResultSet rs=db.get(query);
					File theDir = new File(getServletContext().getInitParameter("pathBaoCao")   +"\\BCXuatKhuyenMaiTT"+filename);
					theDir.mkdir();
					System.out.println("________vào đây: " + query);
					CreatePivotTable(obj,filename);
					System.out.println("lvl 3");
					
					try {
						//   WebService.BCXuatKhuyenMai(getServletContext().getInitParameter("pathBaoCao")   +"\\BCXuatKhuyenMaiTT"+filename+"\\","BCXuatKhuyenMaiTT"+filename,".xlsm", query);
						   WebService.CallAPI("BC_KhongSort", "AA8", getServletContext().getInitParameter("pathBaoCao") + "\\BCXuatKhuyenMaiTT"+filename+"\\","BCXuatKhuyenMaiTT"+filename,".xlsm", query);
						   
							
					}catch(Exception  e)
					{
						e.printStackTrace();
					}
					System.out.println("lvl 4");
					obj.init();
					    obj.setUrl(getServletContext().getInitParameter("pathServicesFile")   +"/BCXuatKhuyenMaiTT"+filename+"\\"+"BCXuatKhuyenMaiTT"+filename+".xlsm");
					    obj.setKey(getServletContext().getInitParameter("pathBaoCao")   +"\\BCXuatKhuyenMaiTT"+filename+"\\success.txt");
						session.setAttribute("obj", obj);
					    response.sendRedirect(nextJSP);*/
					String filename = this.getDateTime1();
					
					File theDir = new File(getServletContext().getInitParameter("pathBaoCao") + "\\BCXuatKhuyenMaiTT"+filename);
					theDir.mkdir();
					CreatePivotTable(obj,filename);
					System.out.println("Error Here : "+this.getDateTime());

					try {
						System.out.println("path: " + getServletContext().getInitParameter("pathBaoCao") + "\\BCXuatKhuyenMaiTT"+filename+"\\");
						System.out.println("file name: " + "BCDonHangBanTrongKyKhongPivot"+filename);
						 
						WebService.CallAPI("BC_KhongSort", "AA8", getServletContext().getInitParameter("pathBaoCao") + "\\BCXuatKhuyenMaiTT"+filename+"\\","BCXuatKhuyenMaiTT"+filename,".xlsm", query);
					}
					catch (Exception  e)
					{
						e.printStackTrace();
					}
					
 					obj.init();	 
					obj.setUrl(getServletContext().getInitParameter("pathBaoCao")  +"/BCXuatKhuyenMaiTT"+filename+"\\"+"BCXuatKhuyenMaiTT"+filename+".xlsm");
					obj.setKey(getServletContext().getInitParameter("pathBaoCao")   +"\\BCXuatKhuyenMaiTT"+filename+"\\success.txt");
					session.setAttribute("obj", obj);
					response.sendRedirect(nextJSP); 
  					    return;
				
				}else{
					response.sendRedirect(nextJSP);
					return;
				}
			} 
			catch (Exception ex) 
			{
				obj.setMsg(ex.getMessage());				
				//response.sendRedirect(nextJSP);
			}

			
		}
		else 
		{
			session.setAttribute("obj", obj);
			//session.setAttribute("userId", obj.getuserId());
			response.sendRedirect(nextJSP);	
		}
	}

	private void CreatePivotTable(IStockintransit obj,String filename) throws Exception 
	{ 																
		/*String chuoi=getServletContext().getInitParameter("path") + "\\XuatKhuyenMaiTT.xlsm";
		
		FileInputStream fstream = new FileInputStream(chuoi);
		//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\XuatKhuyenMaiTT.xlsm");
		//FileInputStream fstream = new FileInputStream(f) ;
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
		return true;*/
		OutputStream out;
		try 
		{				
			out = new FileOutputStream(getServletContext().getInitParameter("pathBaoCao")   +"\\BCXuatKhuyenMaiTT"+filename+"\\BCXuatKhuyenMaiTT"+filename+".xlsm");
			FileInputStream fstream = new FileInputStream( getServletContext().getInitParameter("path") + "\\XuatKhuyenMaiTT.xlsm");
			Workbook workbook = new Workbook();
			try{
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			CreateHeader(workbook,obj); 
			}catch (Exception e){
				e.printStackTrace();
				fstream.close();
			}

			workbook.save(out);
			 fstream.close();
			out.close();
		} 
		catch (Exception ex) {
			System.out.println("Error Here : "+ex.toString());
			throw new Exception("Error Message: " + ex.getMessage());
		}

	}
	
	private void CreateHeader(Workbook workbook,IStockintransit obj) 
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
	    
	    cell.setValue("BÁO CÁO XUẤT KHUYẾN MÃI");  getCellStyle(workbook,"A1",Color.RED,true,14);	  	
	    
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("A3");
	    
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ ngày : " + obj.gettungay() + "" );
	    
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("B3"); 
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến ngày : " + obj.getdenngay() + "" );
	    
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A4");getCellStyle(workbook,"A4",Color.NAVY,true,9);
	    cell.setValue("Ngày báo cáo: " + this.getDateTime());
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A5");getCellStyle(workbook,"A5",Color.NAVY,true,9);
	    cell.setValue("Được tạo bởi:  " + obj.getuserTen());
		cell = cells.getCell("AA7");		cell.setValue("KenhBanHang");
		cell = cells.getCell("AB7");		cell.setValue("ChiNhanh");
		cell = cells.getCell("AC7");		cell.setValue("KhuVuc");
		cell = cells.getCell("AD7");		cell.setValue("MaNhaPhanPhoi");
		cell = cells.getCell("AE7");		cell.setValue("NhaPhanPhoi");
		cell = cells.getCell("AF7");		cell.setValue("MaChuongTrinh");
		cell = cells.getCell("AG7");		cell.setValue("DienGiai");
		cell = cells.getCell("AH7");		cell.setValue("MaSanPhamTra");			
		cell = cells.getCell("AI7");		cell.setValue("TenSanPhamTra");
		cell = cells.getCell("AJ7");		cell.setValue("MaSanPhamMua");
		cell = cells.getCell("AK7");		cell.setValue("TenSanPhamMua");
		cell = cells.getCell("AL7");		cell.setValue("NhanHang");
		cell = cells.getCell("AM7");		cell.setValue("ChungLoai");
		cell = cells.getCell("AN7");		cell.setValue("Tinh/Thanh");
		cell = cells.getCell("AO7");		cell.setValue("Quan/Huyen");
		cell = cells.getCell("AP7");		cell.setValue("LoaiChuongTrinh");
		cell = cells.getCell("AQ7");		cell.setValue("DaiDienKinhDoanh");
		cell = cells.getCell("AR7");		cell.setValue("MaKhachHang");
		cell = cells.getCell("AS7");		cell.setValue("TenKhachHang");
		cell = cells.getCell("AT7");		cell.setValue("Ngay");
		cell = cells.getCell("AU7");		cell.setValue("SoLuongTra");
		cell = cells.getCell("AV7");		cell.setValue("SoTien");
	//	cell = cells.getCell("AW7");		cell.setValue("SoLuong(Thung)");
		cell = cells.getCell("AW7");		cell.setValue("DoanhSo");
		cell = cells.getCell("AX7");		cell.setValue("DiaChiKH");
		cell = cells.getCell("AY7");		cell.setValue("SoLuongMua");
		cell = cells.getCell("AZ7");		cell.setValue("NPP TU CHAY");
		cell = cells.getCell("BA7");		cell.setValue("HinhThuc");
		cell = cells.getCell("BB7");		cell.setValue("Loai");
		cell = cells.getCell("BC7");		cell.setValue("DonHang");
		cell = cells.getCell("BD7");		cell.setValue("ERPScheme");
		cell = cells.getCell("BE7");		cell.setValue("Tungay");
		cell = cells.getCell("BF7");		cell.setValue("DenNgay");
		cell = cells.getCell("BG7");		cell.setValue("MaNVBH");
		cell = cells.getCell("BH7");		cell.setValue("SoSuat");
	}
	
	
	private boolean CreatePivotTable1(OutputStream out, String sql,IStockintransit obj) throws Exception 
	{ 																
		String chuoi=getServletContext().getInitParameter("path") + "\\XuatKhuyenMaiTT.xlsm";
		
		FileInputStream fstream = new FileInputStream(chuoi);
		//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\XuatKhuyenMaiTT.xlsm");
		//FileInputStream fstream = new FileInputStream(f) ;
		Workbook workbook = new Workbook();
		
		workbook.open(fstream);
		
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		
		CreateStaticHeader(workbook, obj);
		
		boolean isTrue = CreateStaticData1(workbook, obj,sql);
		
		if(!isTrue){
			return false;
		}else{
			workbook.save(out);
		}	
		
		fstream.close();
		return true;

	}
	
	private boolean CreatePivotTable2(OutputStream out, String sql,IStockintransit obj) throws Exception 
	{ 																
		String chuoi=getServletContext().getInitParameter("path") + "\\XuatKhuyenMaiTT.xlsm";
		
		FileInputStream fstream = new FileInputStream(chuoi);
		//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\XuatKhuyenMaiTT.xlsm");
		//FileInputStream fstream = new FileInputStream(f) ;
		Workbook workbook = new Workbook();
		
		workbook.open(fstream);
		
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		
		CreateStaticHeader(workbook, obj);
		
		boolean isTrue = CreateStaticData2(workbook, obj,sql);
		
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
		    
		    cell.setValue("BÁO CÁO XUẤT KHUYẾN MÃI");  getCellStyle(workbook,"A1",Color.RED,true,14);	  	
		    
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("A3");
		    
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ ngày : " + obj.gettungay() + "" );
		    
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("B3"); 
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến ngày : " + obj.getdenngay() + "" );
		    
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
			cell = cells.getCell("AH1");		cell.setValue("MaSanPhamTra");			
			cell = cells.getCell("AI1");		cell.setValue("TenSanPhamTra");
			cell = cells.getCell("AJ1");		cell.setValue("MaSanPhamMua");
			cell = cells.getCell("AK1");		cell.setValue("TenSanPhamMua");
			cell = cells.getCell("AL1");		cell.setValue("NhanHang");
			cell = cells.getCell("AM1");		cell.setValue("ChungLoai");
			cell = cells.getCell("AN1");		cell.setValue("Tinh/Thanh");
			cell = cells.getCell("AO1");		cell.setValue("Quan/Huyen");
			cell = cells.getCell("AP1");		cell.setValue("LoaiChuongTrinh");
			cell = cells.getCell("AQ1");		cell.setValue("DaiDienKinhDoanh");
			cell = cells.getCell("AR1");		cell.setValue("MaKhachHang");
			cell = cells.getCell("AS1");		cell.setValue("TenKhachHang");
			cell = cells.getCell("AT1");		cell.setValue("Ngay");
			cell = cells.getCell("AU1");		cell.setValue("SoLuongTra");
			cell = cells.getCell("AV1");		cell.setValue("SoTien");
		//	cell = cells.getCell("AW1");		cell.setValue("SoLuong(Thung)");
			cell = cells.getCell("AW1");		cell.setValue("DoanhSo");
			cell = cells.getCell("AX1");		cell.setValue("DiaChiKH");
			cell = cells.getCell("AY1");		cell.setValue("SoLuongMua");
			cell = cells.getCell("AZ1");		cell.setValue("NPP TU CHAY");
			cell = cells.getCell("BA1");		cell.setValue("HinhThuc");
			cell = cells.getCell("BB1");		cell.setValue("Loai");
			cell = cells.getCell("BC1");		cell.setValue("DonHang");
			cell = cells.getCell("BD1");		cell.setValue("ERPScheme");
			cell = cells.getCell("BE1");		cell.setValue("Tungay");
			cell = cells.getCell("BF1");		cell.setValue("DenNgay");
			
		}
		catch(Exception e)
		{
			throw new Exception(e.getMessage());
		}
	}
	private void TaoBaoCao(Workbook workbook,String tungay,String denngay,String nppid,IStockintransit obj )throws Exception
	{
		try{
			
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("BCXKM");
			dbutils db = new dbutils();
			Utility  util=new Utility();
			Cells cells = worksheet.getCells();
			cells.setRowHeight(0, 50.0f);
			Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 16,"Báo Cáo Xuất Khuyến Mãi");

			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Ngày tạo : "+ getDateTime());
			
			cell = cells.getCell("A2");
			cells.setColumnWidth(0, 20.0f);
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Từ ngày: "+ tungay);
			cell = cells.getCell("B2");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Đến ngày: "+ denngay);
			cells.setColumnWidth(4, 20.0f);
			cells.setColumnWidth(9, 15.0f);
			cells.setColumnWidth(8, 15.0f);
			cells.setColumnWidth(7, 15.0f);
			cells.setColumnWidth(6, 15.0f);
			cell = cells.getCell("A4");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10,"Người tạo : " );
		
			
			
			String query =  "\n	SELECT VUNG.TEN AS REGION,           "     +
				"\n		KHUVUC.TEN AS AREA,            "     +
				"\n		NPP.MA AS DISTRIBUTORID,           "     +
				"\n		NPP.TEN AS DISTRIBUTOR,      "     +
				"\n		DieuKienKmTraKm.MaSanPhamTra,DieuKienKmTraKm.TenSanPhamTra,DieuKienKmTraKm.SoLuongTra,  DieuKienKmTraKm.SoTienTraKM," +
				"\n		DieuKienKmTraKm.slthung as 'Cay/Thung', DieuKienKmTraKm.slle as SoLuongLe  " +
				"\n 	,DieuKienKmTraKm.MaSanPhamMua,DieuKienKmTraKm.TenSPMua ,DieuKienKmTraKm.DoanhSo,NH.ten as NhanHang, cl.ten as ChungLoai, DieuKienKmTraKm.DONHANGID as MaDonHang, ctkm.SCHEME, ctkm.DIENGIAI,  ctkm.schemeerp, ctkm.tungay, ctkm.denngay "+
				"\n	FROM          "     +
				"\n	(	         "     +
				
				
				"\n	Select TraKm.*--,DieuKienKm.MaSanPhamMua,DieuKienKm.idSanPhamMua,DieuKienKm.TenSanPhamMua,DieuKienKm.doanhso,DieuKienKm.SoLuongMua,DieuKienKm.SanLuongMua "
				+ "\n from          "     +
				"\n		(          "     +
				"\n			SELECT TRAKM.DONHANGID,TRAKM.CTKMID,TRAKM.SOLUONG as SoLuongTra,'' AS SoLuongTraQuyDoi,TRAKM.SOLUONG*SP.TRONGLUONG AS SanLuongTra,TRAKM.TONGGIATRI as SoTienTraKm,TRAKM.SPMA as MaSanPhamTra,SP.PK_SEQ as IdSpTra,SP.TEN as TenSanPhamTra         " +
				"\n 		,ISNULL(floor((trakm.soluong*qc.soluong2)/qc.SOLUONG1), floor((trakm.soluong*qc1.soluong2)/qc1.SOLUONG1)) as slthung, "+
				"\n			isnull((Convert(int,trakm.soluong)*Convert(int,qc.soluong2))%Convert(int,qc.SOLUONG1), (Convert(int,trakm.soluong)*Convert(int,qc1.soluong2))%Convert(int,qc1.SOLUONG1)) as slle   "     +
				"\n 		,spmua.MA as MaSanPhamMua, spmua.TEN as TenSPMua, isnull(dhsp.dongiagoc*dkkm.soluongmua,0) as DoanhSo, spmua.pk_seq as IdSPMua "+
				"\n			FROM donhang_ctkm_dkkm dkkm   "+
				"\n			left join sanpham spmua on dkkm.sanpham_fk =spmua.PK_SEQ  "+
				"\n			inner join DONHANG_SANPHAM dhsp on dhsp.SANPHAM_FK = spmua.PK_SEQ and dhsp.DONHANG_FK = dkkm.donhang_fk "+
				"\n			left join DONHANG_CTKM_TRAKM TRAKM on trakm.CTKMID = dkkm.ctkm_fk and TRAKM.DONHANGID = dkkm.donhang_fk             " +
				"\n			inner join DONHANG DH ON DH.PK_SEQ = TRAKM.DONHANGID "     +
				"\n			INNER JOIN CTKHUYENMAI CTKM ON TRAKM.CTKMID = CTKM.PK_SEQ  "     +
				"\n			LEFT JOIN SANPHAM SP ON TRAKM.SPMA = SP.MA   " +
				"\n 		left join DONVIDOLUONG dvdl on sp.DVDL_FK = dvdl.PK_SEQ" +
				"\n 		left join QUYCACH qc on qc.SANPHAM_FK = sp.PK_SEQ and qc.DVDL1_FK = dvdl.PK_SEQ and qc.DVDL2_FK ='100018' " +
				"\n 		left join QUYCACH qc1 on qc1.SANPHAM_FK = sp.PK_SEQ and qc1.DVDL1_FK = dvdl.PK_SEQ and qc1.DVDL2_FK= '1200532' " +
				"\n			WHERE 1=1 and dkkm.pk_seq in (select top 1 pk_seq from donhang_ctkm_dkkm where dkkm.donhang_fk = DONHANG_FK " +
				"\n			and dkkm.ctkm_fk = ctkm_fk )";
				/*if(obj.getYear().length() > 0 && obj.getMonth().length() >0){
				query +="\n 		and SUBSTRING(DH.NGAYNHAP, 1 , 7)  = '"+obj.getYear()+"-"+obj.getMonth()+"'  ";
				}*/
				if(obj.gettungay().length() > 0 && obj.getdenngay().length() > 0){
					query += "\n and dh.ngaynhap >= '"+obj.gettungay()+"' and dh.ngaynhap <= '"+obj.getdenngay()+"' ";
				}
				query +=	"\n union "+
				"\n			SELECT dkkm.DONHANG_fk,dkkm.CTKM_fk,0 as SoLuongTra,'' AS SoLuongTraQuyDoi,0 AS SanLuongTra,0 as SoTienTraKm,0 as MaSanPhamTra,0 as IdSpTra,'' as TenSanPhamTra         " +
				"\n 		,0 as slthung, 0 as slle   "     +
				"\n 		,spmua.MA as MaSanPhamMua, spmua.TEN as TenSPMua, isnull(dhsp.dongiagoc*dkkm.soluongmua,0) as DoanhSo, spmua.pk_seq as IdSPMua "+
				"\n			FROM donhang_ctkm_dkkm dkkm   "+
				"\n			left join sanpham spmua on dkkm.sanpham_fk =spmua.PK_SEQ  "+
				"\n			inner join DONHANG_SANPHAM dhsp on dhsp.SANPHAM_FK = spmua.PK_SEQ and dhsp.DONHANG_FK = dkkm.donhang_fk "+
				"\n			left join DONHANG_CTKM_TRAKM TRAKM on trakm.CTKMID = dkkm.ctkm_fk and TRAKM.DONHANGID = dkkm.donhang_fk             " +
				"\n			inner join DONHANG DH ON DH.PK_SEQ = TRAKM.DONHANGID "     +
				"\n			INNER JOIN CTKHUYENMAI CTKM ON TRAKM.CTKMID = CTKM.PK_SEQ  "     +
				"\n			LEFT JOIN SANPHAM SP ON TRAKM.SPMA = SP.MA   " +
				"\n 		left join DONVIDOLUONG dvdl on sp.DVDL_FK = dvdl.PK_SEQ" +
				"\n 		left join QUYCACH qc on qc.SANPHAM_FK = sp.PK_SEQ and qc.DVDL1_FK = dvdl.PK_SEQ and qc.DVDL2_FK ='100018' " +
				"\n 		left join QUYCACH qc1 on qc1.SANPHAM_FK = sp.PK_SEQ and qc1.DVDL1_FK = dvdl.PK_SEQ and qc1.DVDL2_FK= '1200532' " +
				"\n			WHERE 1=1 and dkkm.pk_seq not in (select top 1 pk_seq from donhang_ctkm_dkkm where dkkm.donhang_fk = DONHANG_FK " +
				"\n			and dkkm.ctkm_fk = ctkm_fk )";
				if(obj.gettungay().length() > 0 && obj.getdenngay().length() > 0){
					query += "\n and dh.ngaynhap >= '"+obj.gettungay()+"' and dh.ngaynhap <= '"+obj.getdenngay()+"' ";
				}
				query += "\n		)as TraKm --Inner JOIN          "     +
			
				"\n	) AS DieuKienKmTraKm          "     +
				"\n	inner join CTKHUYENMAI CTKM ON CTKM.PK_SEQ=DieuKienKmTraKm.CTKMID          "     +
				"\n	inner join DONHANG dh on DieuKienKmTraKm.DONHANGID=dh.PK_SEQ          "     +
				"\n	LEFT JOIN SANPHAM SP ON DieuKienKmTraKm.IdSPMua = SP.PK_SEQ          "     +
				"\n	LEFT JOIN NHANHANG NH ON NH.PK_SEQ = SP.NHANHANG_FK           "     +
				"\n	LEFT JOIN CHUNGLOAI CL ON CL.PK_SEQ = SP.CHUNGLOAI_FK           "     +
				"\n	INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = DH.NPP_FK           "     +
				"\n	left JOIN TINHTHANH TINH ON TINH.PK_SEQ = NPP.TinhThanh_FK           "     +
				"\n	left JOIN QUANHUYEN QUAN ON QUAN.PK_SEQ = TINH.PK_SEQ            "     +
				"\n	INNER JOIN KHACHHANG KH ON KH.PK_SEQ = DH.KHACHHANG_FK           "     +
				"\n	INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ = dh.KBH_FK           "     +
				"\n	INNER JOIN DAIDIENKINHDOANH DDKD ON DDKD.PK_SEQ = DH.DDKD_FK           "     +
				"\n	LEFT JOIN GIAMSATBANHANG GSBH ON GSBH.PK_SEQ = DH.GSBH_FK           "     +
				"\n	INNER JOIN KHUVUC KHUVUC ON KHUVUC.PK_SEQ = NPP.KHUVUC_FK           "     +
				"\n	INNER JOIN VUNG VUNG ON VUNG.PK_SEQ = KHUVUC.VUNG_FK             "     +
				"\n WHERE ISNULL(DieuKienKmTraKm.SoLuongTra, '0') >= 0  "     +
				"\n AND DH.PK_SEQ NOT IN  "     +
				"\n ( "     +
				"\n	SELECT DONHANG_FK FROM DONHANGTRAVE  "     +
				"\n	WHERE DONHANG_FK IS NOT NULL AND TRANGTHAI = '3'  "     +
				"\n ) ";
		System.out.println("Chot : "+obj.getDhChot());
		System.out.println("Unghang : "+obj.getUnghang());
		if(obj.getDhChot().equals("1"))
		{
			query+="and dh.TRANGTHAI='1' ";
		}
		else
		{
			query+="and dh.TRANGTHAI != '2' ";
		}
		
		if(!obj.getView().equals("NPP"))
			query+=" and NPP.PK_SEQ IN "+util.quyen_npp(obj.getuserId()) +" and kbh.pk_seq in "+util.quyen_kenh(obj.getuserId());
		
		
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
		
		query = query + " order by DieuKienKmTraKm.DONHANGID,DieuKienKmTraKm.CtKMId, DieuKienKmTraKm.MaSanPhamTra  ";
		
		System.out.print("1.BC Xuat khuyen mai TT: " + query);
								
							
				
					 		
					 
				
			System.out.println("Get NVBH ko PDA: "+query);
			ResultSet rs = db.get(query);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();
			
			int countRow = 10;

			for( int i =1 ; i <=socottrongSql ; i ++  )
			{
				cell = cells.getCell(countRow,i-1 );cell.setValue(rsmd.getColumnName(i));
				cells.setColumnWidth(i, 20.0f);
				ReportAPI.setCellBackground(cell, new Color(70,130,180), BorderLineType.THIN, true, 0);	
			 
			}
			countRow ++;
			
			int stt = 0;
			while(rs.next())
			{
				
				stt++;
			
				for(int i =1;i <=socottrongSql ; i ++)
				{
					cell = cells.getCell(countRow,i-1 );
					if(rsmd.getColumnName(i).equals("STT"))
					{					
						cell.setValue(stt);
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
						//System.out.println("STT: "+stt);

					}else
					if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i) == Types.INTEGER || rsmd.getColumnType(i) == Types.DECIMAL)
					{
						cell.setValue(rs.getDouble(i));
						
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
					}
					else
					{
							
							
							cell.setValue(rs.getString(i));
							
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					}
				}
				++countRow;
			}

			if(rs!=null)rs.close();
			if(db!=null){
				db.shutDown();
			}
			
	
		}catch(Exception ex){
			
			System.out.println("Errrorr : "+ex.toString());
			ex.printStackTrace();
			throw new Exception("Lỗi không tạo được báo cáo !");
		}
	}

	
	private boolean CreateStaticData1(Workbook workbook,IStockintransit obj,String sql)throws Exception 
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		Cells cells = worksheet.getCells();
		dbutils db = new dbutils();
		Utility  util=new Utility();
		
		
		String query = 				
				"\n	SELECT KBH.PK_SEQ AS CHANNELID, KBH.TEN AS CHANNEL,VUNG.PK_SEQ AS REGIONID,VUNG.TEN AS REGION,           "     +
				"\n		KHUVUC.PK_SEQ AS AREAID,KHUVUC.TEN AS AREA,            "     +
				"\n		isnull(TINH.PK_SEQ,0) AS PROVINCEID,isnull(TINH.TEN,'NA') AS PROVINCE, isnull(QUAN.PK_SEQ ,0)AS TOWNID,           "     +
				"\n		isnull(QUAN.TEN,'NA') AS TOWN,ISNULL(GSBH.PK_SEQ,0) AS SALESSUPID,            "     +
				"\n		ISNULL(GSBH.TEN, '') AS SALESSUP, NPP.MA AS DISTRIBUTORID,           "     +
				"\n		NPP.TEN AS DISTRIBUTOR, DDKD.PK_SEQ AS SALEREPID, isnull(DDKD.TEN, 'Chua khai bao') AS SALESREP,            "     +
				"\n		CAST(KH.SMARTID as nvarchar(8)) as khId, KH.ten AS CUSTOMER,KH.DIACHI AS diachi ,DH.NGAYNHAP AS DATE,CTKM.SCHEME AS PROGRAM_CODE,           "     +
				"\n		ISNULL(ctkm.diengiai + '__' + ctkm.tungay + '-' + ctkm.denngay, '') AS PROGRAM,            "     +
				"\n		CASE WHEN CTKM.LOAICT = 1 THEN 'Binh Thuong' WHEN CTKM.LOAICT = 2 THEN 'On Top'            "     +
				"\n		WHEN CTKM.LOAICT = 3 THEN 'Tich Luy' else 'Chua Xac Dinh' END AS PROGRAM_TYPE,            "     +
				"\n		NH.PK_SEQ AS BRANDID, NH.TEN AS BRAND,CL.PK_SEQ AS CATEGORYID,CL.TEN AS CATEGORY,  DieuKienKmTraKm.* , CTKM.NPPTUCHAY,  " +
				"\n     case when ctkm.kho_fk = 100001 then N'Trả trước' else N'Trả sau' end as Hinhthuc, '0' as MaSanPhamMua, '' as TenSanPhamMua,  " +
				"\n     '0' as idSanPhamMua,'0' as doanhso,'0' as SoLuongMua,'0' as SanLuongMua, '' as loai,  ctkm.schemeerp, ctkm.tungay, ctkm.denngay,ddkd.SMARTID MANVBH "     +
				"\n	FROM          "     +
				"\n	(          "     +
				"\n			SELECT TRAKM.DONHANGID,TRAKM.CTKMID,TRAKM.SOLUONG as SoLuongTra,'' AS SoLuongTraQuyDoi,TRAKM.SOLUONG*SP.TRONGLUONG AS SanLuongTra,TRAKM.TONGGIATRI as SoTienTraKm,TRAKM.SPMA as MaSanPhamTra,SP.PK_SEQ as IdSpTra,SP.TEN as TenSanPhamTra          "     +
				"\n			FROM DONHANG_CTKM_TRAKM TRAKM           " +
				"\n			inner join DONHANG DH ON DH.PK_SEQ = TRAKM.DONHANGID "     +
				"\n			INNER JOIN CTKHUYENMAI CTKM ON TRAKM.CTKMID = CTKM.PK_SEQ  "     +
				"\n			LEFT JOIN SANPHAM SP ON TRAKM.SPMA = SP.MA   " +
				"\n			WHERE 1=1";
				/*if(obj.getYear().length() > 0 && obj.getMonth().length() >0){
				query +="\n 		and SUBSTRING(DH.NGAYNHAP, 1 , 7)  = '"+obj.getYear()+"-"+obj.getMonth()+"'  ";
				}*/
				if(obj.gettungay().length() > 0 && obj.getdenngay().length() > 0){
					query += "\n and dh.ngaynhap >= '"+obj.gettungay()+"' and dh.ngaynhap <= '"+obj.getdenngay()+"' ";
				}
			
				query += "\n		) AS DieuKienKmTraKm          "     +
				"\n	inner join CTKHUYENMAI CTKM ON CTKM.PK_SEQ=DieuKienKmTraKm.CTKMID          "     +
				"\n	inner join DONHANG dh on DieuKienKmTraKm.DONHANGID=dh.PK_SEQ          "     +
				"\n	LEFT JOIN SANPHAM SP ON DieuKienKmTraKm.IdSpTra = SP.PK_SEQ          "     +
				"\n	LEFT JOIN NHANHANG NH ON NH.PK_SEQ = SP.NHANHANG_FK           "     +
				"\n	LEFT JOIN CHUNGLOAI CL ON CL.PK_SEQ = SP.CHUNGLOAI_FK           "     +
			
				"\n	INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = DH.NPP_FK           "     +
				"\n	left JOIN TINHTHANH TINH ON TINH.PK_SEQ = NPP.TinhThanh_FK           "     +
				"\n	left JOIN QUANHUYEN QUAN ON QUAN.PK_SEQ = TINH.PK_SEQ            "     +
				"\n	INNER JOIN KHACHHANG KH ON KH.PK_SEQ = DH.KHACHHANG_FK           "     +
				"\n	INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ = npp.KBH_FK           "     +
				"\n	INNER JOIN DAIDIENKINHDOANH DDKD ON DDKD.PK_SEQ = DH.DDKD_FK           "     +
				"\n	LEFT JOIN GIAMSATBANHANG GSBH ON GSBH.PK_SEQ = DH.GSBH_FK           "     +
				"\n	INNER JOIN KHUVUC KHUVUC ON KHUVUC.PK_SEQ = NPP.KHUVUC_FK           "     +
				"\n	INNER JOIN VUNG VUNG ON VUNG.PK_SEQ = KHUVUC.VUNG_FK             "     +
				"\n WHERE ISNULL(DieuKienKmTraKm.SoLuongTra, '0') >= 0  "     +
				"\n AND DH.PK_SEQ NOT IN  "     +
				"\n ( "     +
				"\n	SELECT DONHANG_FK FROM DONHANGTRAVE  "     +
				"\n	WHERE DONHANG_FK IS NOT NULL AND TRANGTHAI = '3'  "     +
				"\n ) ";
		System.out.println("Chot : "+obj.getDhChot());
		System.out.println("Unghang : "+obj.getUnghang());
		if(obj.getDhChot().equals("1"))
		{
			query+="and dh.TRANGTHAI='1' ";
		}
		else
		{
			query+="and dh.TRANGTHAI != '2' ";
		}
		
		query+=" and NPP.PK_SEQ IN "+util.quyen_npp(obj.getuserId()) +" and kbh.pk_seq in "+util.quyen_kenh(obj.getuserId());
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
		
		query = query + " order by DieuKienKmTraKm.DONHANGID,DieuKienKmTraKm.CtKMId, DieuKienKmTraKm.MaSanPhamTra  ";
		
		System.out.print("1.BC Xuat khuyen mai TT: " + query);
		ResultSet rs = db.get(query);
		int i = 2;
		if (rs != null) 
		{
			try 
			{// se do rong cho cac cot se dung
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
				cells.setColumnWidth(21, 15.0f);
				cells.setColumnWidth(22, 15.0f);
				cells.setColumnWidth(23, 15.0f);
				cells.setColumnWidth(24, 15.0f);	
				cells.setColumnWidth(25, 15.0f);
				cells.setColumnWidth(26, 15.0f);
				Cell cell = null;
				String masptra = "";
				String Prev="";
				while (rs.next())
				{
					String Cur=rs.getString("DONHANGID")+"_"+rs.getString("CtKMId")+rs.getString("MaSanPhamTra");
					String Chanel = rs.getString("Channel");
					String Region = rs.getString("Region");
					String area = rs.getString("Area");

					String Distributor = rs.getString("Distributor");
					String PromotionProgram = rs.getString("PROGRAM");

					String Brand = "" ;
					
					if(rs.getString("Brand") == null)
						Brand = "";
					else
						Brand = rs.getString("Brand");
					
					String Category = "";

					if(rs.getString("Category") == null)
						Category = "";
					else
						Category = rs.getString("Category");
					
					String Province = rs.getString("Province");
					String Town = rs.getString("Town");
					String DisCode = rs.getString("DISTRIBUTORID");
					String ProgramCode = rs.getString("PROGRAM_CODE");
					String ProgramType = rs.getString("PROGRAM_TYPE");
					
					String MaSanPhamTra = "";
					
					if(rs.getString("MaSanPhamTra") == null)
						MaSanPhamTra = "";
					else
						MaSanPhamTra = rs.getString("MaSanPhamTra");

					String TenSanPhamTra = "";
					if(rs.getString("TenSanPhamTra") == null)
						TenSanPhamTra = "";
					else
						TenSanPhamTra = rs.getString("TenSanPhamTra");
					
					
					String MaSanPhamMua = "";
					if(rs.getString("MaSanPhamMua") == null)
						MaSanPhamMua = "";
					else
						MaSanPhamMua = rs.getString("MaSanPhamMua");

					String TenSanPhamMua = "";
					if(rs.getString("TenSanPhamMua") == null)
						TenSanPhamMua = "";
					else
						TenSanPhamMua = rs.getString("TenSanPhamMua");
					
					
					String SaleRep = rs.getString("SALESREP");
					//String CustomerKey = rs.getString("khId");
					String Customer = rs.getString("Customer");
					int CustomerKey = Integer.parseInt(rs.getString("khId"));
					String Offdate = rs.getString("DATE");

					long doanhso = Math.round(rs.getDouble("DOANHSO"));
					cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(Chanel);
					cell = cells.getCell("AB" + Integer.toString(i)); cell.setValue(Region);
					cell = cells.getCell("AC" + Integer.toString(i)); cell.setValue(area);
					cell = cells.getCell("AD" + Integer.toString(i)); cell.setValue(DisCode);
					cell = cells.getCell("AE" + Integer.toString(i)); cell.setValue(Distributor); 
					cell = cells.getCell("AF" + Integer.toString(i)); cell.setValue(ProgramCode);
					cell = cells.getCell("AG" + Integer.toString(i)); cell.setValue(PromotionProgram);
					cell = cells.getCell("AH" + Integer.toString(i)); cell.setValue(MaSanPhamTra);
					cell = cells.getCell("AI" + Integer.toString(i)); cell.setValue(TenSanPhamTra);
					cell = cells.getCell("AJ" + Integer.toString(i)); cell.setValue(MaSanPhamMua);
					cell = cells.getCell("AK" + Integer.toString(i)); cell.setValue(TenSanPhamMua);
					cell = cells.getCell("AL" + Integer.toString(i)); cell.setValue(Brand);
					cell = cells.getCell("AM" + Integer.toString(i)); cell.setValue(Category);
					cell = cells.getCell("AN" + Integer.toString(i)); cell.setValue(Province);
					cell = cells.getCell("AO" + Integer.toString(i)); cell.setValue(Town);					
					cell = cells.getCell("AP" + Integer.toString(i)); cell.setValue(ProgramType);
					cell = cells.getCell("AQ" + Integer.toString(i)); cell.setValue(SaleRep);
					cell = cells.getCell("AR" + Integer.toString(i)); cell.setValue(CustomerKey);
					cell = cells.getCell("AS" + Integer.toString(i)); cell.setValue(Customer);
					cell = cells.getCell("AT" + Integer.toString(i)); cell.setValue(Offdate);
					
					if(!Cur.equals(Prev)){
						Prev=Cur;
						masptra=rs.getString("MaSanPhamTra");
						//System.out.println("Diff[MaSp]"+rs.getString("MaSanPhamTra")+"[Ma]"+masptra);
						cell = cells.getCell("AU" + Integer.toString(i));		cell.setValue(rs.getDouble("SoLuongTra"));
						cell = cells.getCell("BA" + Integer.toString(i)); 		cell.setValue(rs.getDouble("sanluongTra"));
					}else{
						//System.out.println("Same [MaSp]"+rs.getString("MaSanPhamTra")+"[Ma]"+masptra);
						cell = cells.getCell("AU" + Integer.toString(i));		cell.setValue(0);
						cell = cells.getCell("BA" + Integer.toString(i)); 		cell.setValue(0);
					}
					
					/*cell = cells.getCell("AU" + Integer.toString(i)); cell.setValue(rs.getDouble("SoLuongTra"));*/
					cell = cells.getCell("AV" + Integer.toString(i)); cell.setValue(rs.getDouble("SoTienTraKm"));
					
					cell = cells.getCell("AW" + Integer.toString(i)); cell.setValue(doanhso);
					cell = cells.getCell("AX" + Integer.toString(i)); cell.setValue(rs.getString("diachi"));
					cell = cells.getCell("AY" + Integer.toString(i)); cell.setValue(rs.getDouble("SoLuongMua"));
					cell = cells.getCell("AZ" + Integer.toString(i)); cell.setValue(rs.getDouble("NPPTUCHAY"));	
					cell = cells.getCell("BA" + Integer.toString(i)); cell.setValue(rs.getString("hinhthuc"));	
					cell = cells.getCell("BB" + Integer.toString(i)); cell.setValue(rs.getString("loai"));	
					cell = cells.getCell("BC" + Integer.toString(i)); cell.setValue(rs.getString("DONHANGID"));	
					cell = cells.getCell("BD" + Integer.toString(i)); cell.setValue(rs.getString("schemeerp"));	
					cell = cells.getCell("BE" + Integer.toString(i)); cell.setValue(rs.getString("tungay"));	
					cell = cells.getCell("BF" + Integer.toString(i)); cell.setValue(rs.getString("denngay"));	
					
					i++;

				}
				
				

				if(rs != null) rs.close();
				if(db != null)  db.shutDown();
				
				if(i==2){
					throw new Exception("Khong co bao cao voi dieu kien da chon");
				}
				
				//setAn(workbook, 49);
		    	return true;

				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println(e.toString());
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
	
	
	
	
	
	private boolean CreateStaticData(Workbook workbook,IStockintransit obj,String sql)throws Exception 
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		Cells cells = worksheet.getCells();
		dbutils db = new dbutils();
		Utility  util=new Utility();
		
		
		String query = 				
				"\n	SELECT KBH.PK_SEQ AS CHANNELID, KBH.TEN AS CHANNEL,VUNG.PK_SEQ AS REGIONID,VUNG.TEN AS REGION,           "     +
				"\n		KHUVUC.PK_SEQ AS AREAID,KHUVUC.TEN AS AREA,            "     +
				"\n		isnull(TINH.PK_SEQ,0) AS PROVINCEID,isnull(TINH.TEN,'NA') AS PROVINCE, isnull(QUAN.PK_SEQ ,0)AS TOWNID,           "     +
				"\n		isnull(QUAN.TEN,'NA') AS TOWN,ISNULL(GSBH.PK_SEQ,0) AS SALESSUPID,            "     +
				"\n		ISNULL(GSBH.TEN, '') AS SALESSUP, NPP.MA AS DISTRIBUTORID,           "     +
				"\n		NPP.TEN AS DISTRIBUTOR, DDKD.PK_SEQ AS SALEREPID, isnull(DDKD.TEN, 'Chua khai bao') AS SALESREP,            "     +
				"\n		CAST(KH.SMARTID as nvarchar(8)) as khId, KH.ten AS CUSTOMER,KH.DIACHI AS diachi ,DH.NGAYNHAP AS DATE,CTKM.SCHEME AS PROGRAM_CODE,           "     +
				"\n		ISNULL(ctkm.diengiai + '__' + ctkm.tungay + '-' + ctkm.denngay, '') AS PROGRAM,            "     +
				"\n		CASE WHEN CTKM.LOAICT = 1 THEN 'Binh Thuong' WHEN CTKM.LOAICT = 2 THEN 'On Top'            "     +
				"\n		WHEN CTKM.LOAICT = 3 THEN 'Tich Luy' else 'Chua Xac Dinh' END AS PROGRAM_TYPE,            "     +
				"\n		NH.PK_SEQ AS BRANDID, NH.TEN AS BRAND,CL.PK_SEQ AS CATEGORYID,CL.TEN AS CATEGORY,  DieuKienKmTraKm.* , CTKM.NPPTUCHAY,  " +
				"\n     case when ctkm.kho_fk = 100001 then N'Trả trước' else N'Trả sau' end as Hinhthuc ,  ctkm.schemeerp, ctkm.tungay, ctkm.denngay  "     +
				"\n	FROM          "     +
				"\n	(	         "     +
		
				"\n	select data.loai,data.DONHANGID,data.CTKMID,data.SoLuongTra,data.SoLuongMua,data.SanLuongTra, '' sanluongmua,sum(data.SoTienTraKm) as SoTienTraKm, data.doanhso,data.MaSanPhamTra, data.MaSanPhamMua,data.IdSpTra, data.IdSanphammua,data.TenSanPhamTra, data.TenSanPhammua from ( "     +
						"\n			Select 'TRA' as loai,TraKm.*          "     +
						"\n			from                   "     + 
						"\n			(                 "     +
								"\n				SELECT TRAKM.DONHANGID,TRAKM.CTKMID,TRAKM.SOLUONG as SoLuongTra, '0' as SoLuongMua,'0' AS SoLuongTraQuyDoi,TRAKM.SOLUONG*SP.TRONGLUONG AS SanLuongTra,case when  trakm.Sanpham_fk is null then TRAKM.TONGGIATRI else 0 end  as SoTienTraKm, '0' as doanhso,TRAKM.SPMA as MaSanPhamTra,'0' MaSanPhamMua,'0' IdSanphammua,SP.PK_SEQ as IdSpTra,'' TenSanPhammua,SP.TEN as TenSanPhamTra     "     +               
							"\n				FROM DONHANG_CTKM_TRAKM TRAKM     "     +            
							"\n				inner join DONHANG DH ON DH.PK_SEQ = TRAKM.DONHANGID       "     +    
							"\n				INNER JOIN CTKHUYENMAI CTKM ON TRAKM.CTKMID = CTKM.PK_SEQ     "     +       
							"\n				LEFT JOIN SANPHAM SP ON TRAKM.SPMA = SP.MA      "     +       
							"\n				WHERE 1=1         "     ;
							if(obj.gettungay().length() > 0 && obj.getdenngay().length() > 0){
							query += "\n and dh.ngaynhap >= '"+obj.gettungay()+"' and dh.ngaynhap <= '"+obj.getdenngay()+"' ";
						}
					query +=	"\n			)as TraKm           "     +
					"\n		 union all "     +
					 "\n		 select N'DKMUA' as loai,dh.PK_SEQ,a.pk_seq as CtKMId,'0' as soluongtra,b.soluongmua ,'0' soluongquydoi,'0' sanluongtra,'0' sotientrakm,( b.soluongmua * c.dongiagoc ) as doanhso,'0' MaSanPhamTra,sp.MA as MaSanPhamMua,sp.PK_SEQ as IdSpmua, '0' IdSpTra,sp.ten as TenSanPhammua,'' TenSanPhamTra "     +
					 "\n			from donhang dh inner join donhang_ctkm_dkkm b on dh.pk_seq = b.donhang_fk         " +
						"\n			inner join donhang_sanpham c on b.donhang_fk = c.donhang_fk    and c.sanpham_fk = b.sanpham_fk      "     +  
						"\n			inner JOIN SANPHAM SP ON b.SANPHAM_FK = SP.PK_SEQ   "     +
						"\n			inner join ctkhuyenmai a on a.PK_SEQ = b.ctkm_fk  "     +
						"\n			inner join ctkm_dkkm d on a.pk_seq = d.ctkhuyenmai_fk            "     +
						"\n			where 1=1 and exists (select 1 from donhang_ctkm_trakm where ctkmid = b.ctkm_fk and c.donhang_fk = donhangid ) " ;
						if(obj.gettungay().length() > 0 && obj.getdenngay().length() > 0){
							query += "\n and dh.ngaynhap >= '"+obj.gettungay()+"' and dh.ngaynhap <= '"+obj.getdenngay()+"' ";
						}
					query +=
						"\n	)as data "     +
						"\n	group by data.loai,data.DONHANGID,data.CTKMID,data.SoLuongTra,data.SoLuongMua,data.SanLuongTra, data.doanhso,data.MaSanPhamTra, data.MaSanPhamMua,data.IdSpTra, data.IdSanphammua,data.TenSanPhamTra, data.TenSanPhammua "     +
				"\n	 ) AS DieuKienKmTraKm          "     + 
				"\n	inner join CTKHUYENMAI CTKM ON CTKM.PK_SEQ=DieuKienKmTraKm.CTKMID          "     +
				"\n	inner join DONHANG dh on DieuKienKmTraKm.DONHANGID=dh.PK_SEQ          "     +
				"\n	LEFT JOIN SANPHAM SP ON isnull(DieuKienKmTraKm.IdSanphammua,DieuKienKmTraKm.IdSpTra) = SP.PK_SEQ          "     +
				"\n	LEFT JOIN NHANHANG NH ON NH.PK_SEQ = SP.NHANHANG_FK           "     +
				"\n	LEFT JOIN CHUNGLOAI CL ON CL.PK_SEQ = SP.CHUNGLOAI_FK           "     +
				/*"\n	LEFT JOIN SANPHAM SPM ON DieuKienKmTraKm.IdSpTra = SP.PK_SEQ          "     +
				"\n	LEFT JOIN NHANHANG NHM ON NH.PK_SEQ = SP.NHANHANG_FK           "     +
				"\n	LEFT JOIN CHUNGLOAI CLM ON CL.PK_SEQ = SP.CHUNGLOAI_FK           "     +*/
				"\n	INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = DH.NPP_FK           "     +
				"\n	left JOIN TINHTHANH TINH ON TINH.PK_SEQ = NPP.TinhThanh_FK           "     +
				"\n	left JOIN QUANHUYEN QUAN ON QUAN.PK_SEQ = TINH.PK_SEQ            "     +
				"\n	INNER JOIN KHACHHANG KH ON KH.PK_SEQ = DH.KHACHHANG_FK           "     +
				"\n	INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ = npp.KBH_FK           "     +
				"\n	INNER JOIN DAIDIENKINHDOANH DDKD ON DDKD.PK_SEQ = DH.DDKD_FK           "     +
				"\n	LEFT JOIN GIAMSATBANHANG GSBH ON GSBH.PK_SEQ = DH.GSBH_FK           "     +
				"\n	INNER JOIN KHUVUC KHUVUC ON KHUVUC.PK_SEQ = NPP.KHUVUC_FK           "     +
				"\n	INNER JOIN VUNG VUNG ON VUNG.PK_SEQ = KHUVUC.VUNG_FK             "     +
				"\n WHERE ISNULL(DieuKienKmTraKm.SoLuongTra, '0') >= 0  "     +
				"\n AND DH.PK_SEQ NOT IN  "     +
				"\n ( "     +
				"\n	SELECT DONHANG_FK FROM DONHANGTRAVE  "     +
				"\n	WHERE DONHANG_FK IS NOT NULL AND TRANGTHAI = '3'  "     +
				"\n ) ";
		System.out.println("Chot : "+obj.getDhChot());
		System.out.println("Unghang : "+obj.getUnghang());
		if(obj.getDhChot().equals("1"))
		{
			query+="and dh.TRANGTHAI='1' ";
		}
		else
		{
			query+="and dh.TRANGTHAI != '2' ";
		}
		
		query+=" and NPP.PK_SEQ IN "+util.quyen_npp(obj.getuserId()) +" and kbh.pk_seq in "+util.quyen_kenh(obj.getuserId());
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
		
		query = query + " order by DieuKienKmTraKm.DONHANGID,DieuKienKmTraKm.CtKMId, DieuKienKmTraKm.MaSanPhamTra  ";
		
		System.out.print("1.BC Xuat khuyen mai TT: " + query);
		ResultSet rs = db.get(query);
		int i = 2;
		if (rs != null) 
		{
			try 
			{// se do rong cho cac cot se dung
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
				cells.setColumnWidth(21, 15.0f);
				cells.setColumnWidth(22, 15.0f);
				cells.setColumnWidth(23, 15.0f);
				cells.setColumnWidth(24, 15.0f);	
				cells.setColumnWidth(25, 15.0f);	
				Cell cell = null;
				String masptra = "";
				String Prev="";
				while (rs.next())
				{
					String Cur=rs.getString("DONHANGID")+"_"+rs.getString("CtKMId")+rs.getString("MaSanPhamTra");
					String Chanel = rs.getString("Channel");
					String Region = rs.getString("Region");
					String area = rs.getString("Area");

					String Distributor = rs.getString("Distributor");
					String PromotionProgram = rs.getString("PROGRAM");

					String Brand = "" ;
					
					if(rs.getString("Brand") == null)
						Brand = "";
					else
						Brand = rs.getString("Brand");
					
					String Category = "";

					if(rs.getString("Category") == null)
						Category = "";
					else
						Category = rs.getString("Category");
					
					String Province = rs.getString("Province");
					String Town = rs.getString("Town");
					String DisCode = rs.getString("DISTRIBUTORID");
					String ProgramCode = rs.getString("PROGRAM_CODE");
					String ProgramType = rs.getString("PROGRAM_TYPE");
					
					String MaSanPhamTra = "";
					
					if(rs.getString("MaSanPhamTra") == null)
						MaSanPhamTra = "";
					else
						MaSanPhamTra = rs.getString("MaSanPhamTra");

					String TenSanPhamTra = "";
					if(rs.getString("TenSanPhamTra") == null)
						TenSanPhamTra = "";
					else
						TenSanPhamTra = rs.getString("TenSanPhamTra");
					
					
					String MaSanPhamMua = "";
					if(rs.getString("MaSanPhamMua") == null)
						MaSanPhamMua = "";
					else
						MaSanPhamMua = rs.getString("MaSanPhamMua");

					String TenSanPhamMua = "";
					if(rs.getString("TenSanPhamMua") == null)
						TenSanPhamMua = "";
					else
						TenSanPhamMua = rs.getString("TenSanPhamMua");
					
					
					String SaleRep = rs.getString("SALESREP");
					//String CustomerKey = rs.getString("khId");
					String Customer = rs.getString("Customer");
					int CustomerKey = Integer.parseInt(rs.getString("khId"));
					String Offdate = rs.getString("DATE");

					Double doanhso = rs.getDouble("DOANHSO");
					cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(Chanel);
					cell = cells.getCell("AB" + Integer.toString(i)); cell.setValue(Region);
					cell = cells.getCell("AC" + Integer.toString(i)); cell.setValue(area);
					cell = cells.getCell("AD" + Integer.toString(i)); cell.setValue(DisCode);
					cell = cells.getCell("AE" + Integer.toString(i)); cell.setValue(Distributor); 
					cell = cells.getCell("AF" + Integer.toString(i)); cell.setValue(ProgramCode);
					cell = cells.getCell("AG" + Integer.toString(i)); cell.setValue(PromotionProgram);
					cell = cells.getCell("AH" + Integer.toString(i)); cell.setValue(MaSanPhamTra);
					cell = cells.getCell("AI" + Integer.toString(i)); cell.setValue(TenSanPhamTra);
					cell = cells.getCell("AJ" + Integer.toString(i)); cell.setValue(MaSanPhamMua);
					cell = cells.getCell("AK" + Integer.toString(i)); cell.setValue(TenSanPhamMua);
					cell = cells.getCell("AL" + Integer.toString(i)); cell.setValue(Brand);
					cell = cells.getCell("AM" + Integer.toString(i)); cell.setValue(Category);
					cell = cells.getCell("AN" + Integer.toString(i)); cell.setValue(Province);
					cell = cells.getCell("AO" + Integer.toString(i)); cell.setValue(Town);					
					cell = cells.getCell("AP" + Integer.toString(i)); cell.setValue(ProgramType);
					cell = cells.getCell("AQ" + Integer.toString(i)); cell.setValue(SaleRep);
					cell = cells.getCell("AR" + Integer.toString(i)); cell.setValue(CustomerKey);
					cell = cells.getCell("AS" + Integer.toString(i)); cell.setValue(Customer);
					cell = cells.getCell("AT" + Integer.toString(i)); cell.setValue(Offdate);
					
					if(!Cur.equals(Prev)){
						Prev=Cur;
						masptra=rs.getString("MaSanPhamTra");
						//System.out.println("Diff[MaSp]"+rs.getString("MaSanPhamTra")+"[Ma]"+masptra);
						cell = cells.getCell("AU" + Integer.toString(i));		cell.setValue(rs.getDouble("SoLuongTra"));
						cell = cells.getCell("BA" + Integer.toString(i)); 		cell.setValue(rs.getDouble("sanluongTra"));
					}else{
						//System.out.println("Same [MaSp]"+rs.getString("MaSanPhamTra")+"[Ma]"+masptra);
						cell = cells.getCell("AU" + Integer.toString(i));		cell.setValue(0);
						cell = cells.getCell("BA" + Integer.toString(i)); 		cell.setValue(0);
					}
					
					/*cell = cells.getCell("AU" + Integer.toString(i)); cell.setValue(rs.getDouble("SoLuongTra"));*/
					cell = cells.getCell("AV" + Integer.toString(i)); cell.setValue(rs.getDouble("SoTienTraKm"));
					
					cell = cells.getCell("AW" + Integer.toString(i)); cell.setValue(doanhso);
					cell = cells.getCell("AX" + Integer.toString(i)); cell.setValue(rs.getString("diachi"));
					cell = cells.getCell("AY" + Integer.toString(i)); cell.setValue(rs.getDouble("SoLuongMua"));
					cell = cells.getCell("AZ" + Integer.toString(i)); cell.setValue(rs.getDouble("NPPTUCHAY"));	
					cell = cells.getCell("BA" + Integer.toString(i)); cell.setValue(rs.getString("hinhthuc"));	
					cell = cells.getCell("BB" + Integer.toString(i)); cell.setValue(rs.getString("loai"));	
					cell = cells.getCell("BC" + Integer.toString(i)); cell.setValue(rs.getString("DONHANGID"));	
					cell = cells.getCell("BD" + Integer.toString(i)); cell.setValue(rs.getString("schemeerp"));	
					cell = cells.getCell("BE" + Integer.toString(i)); cell.setValue(rs.getString("tungay"));	
					cell = cells.getCell("BF" + Integer.toString(i)); cell.setValue(rs.getString("denngay"));	
					
					i++;

				}
				
				

				if(rs != null) rs.close();
				if(db != null)  db.shutDown();
				
				if(i==2){
					throw new Exception("Khong co bao cao voi dieu kien da chon");
				}
				
				//setAn(workbook, 49);
		    	return true;

				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println(e.toString());
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
	
	
	
	private boolean CreateStaticData2(Workbook workbook,IStockintransit obj,String sql)throws Exception 
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		Cells cells = worksheet.getCells();
		dbutils db = new dbutils();
		Utility  util=new Utility();
		
		
		String query = 				
				"\n	SELECT KBH.PK_SEQ AS CHANNELID, KBH.TEN AS CHANNEL,VUNG.PK_SEQ AS REGIONID,VUNG.TEN AS REGION,           "     +
				"\n		KHUVUC.PK_SEQ AS AREAID,KHUVUC.TEN AS AREA,            "     +
				"\n		isnull(TINH.PK_SEQ,0) AS PROVINCEID,isnull(TINH.TEN,'NA') AS PROVINCE, isnull(QUAN.PK_SEQ ,0)AS TOWNID,           "     +
				"\n		isnull(QUAN.TEN,'NA') AS TOWN,ISNULL(GSBH.PK_SEQ,0) AS SALESSUPID,            "     +
				"\n		ISNULL(GSBH.TEN, '') AS SALESSUP, NPP.MA AS DISTRIBUTORID,           "     +
				"\n		NPP.TEN AS DISTRIBUTOR, DDKD.PK_SEQ AS SALEREPID, isnull(DDKD.TEN, 'Chua khai bao') AS SALESREP,            "     +
				"\n		CAST(KH.SMARTID as nvarchar(8)) as khId, KH.ten AS CUSTOMER,KH.DIACHI AS diachi ,DH.NGAYNHAP AS DATE,CTKM.SCHEME AS PROGRAM_CODE,           "     +
				"\n		ISNULL(ctkm.diengiai + '__' + ctkm.tungay + '-' + ctkm.denngay, '') AS PROGRAM,            "     +
				"\n		CASE WHEN CTKM.LOAICT = 1 THEN 'Binh Thuong' WHEN CTKM.LOAICT = 2 THEN 'On Top'            "     +
				"\n		WHEN CTKM.LOAICT = 3 THEN 'Tich Luy' else 'Chua Xac Dinh' END AS PROGRAM_TYPE,            "     +
				"\n		NH.PK_SEQ AS BRANDID, NH.TEN AS BRAND,CL.PK_SEQ AS CATEGORYID,CL.TEN AS CATEGORY,  DieuKienKmTraKm.* , CTKM.NPPTUCHAY,  " +
				"\n     case when ctkm.kho_fk = 100001 then N'Trả trước' else N'Trả sau' end as Hinhthuc, '' loai,  ctkm.schemeerp, ctkm.tungay, ctkm.denngay    "     +
				"\n	FROM          "     +
				"\n	(	         "     +
				""     +
				"\n	Select TraKm.*,DieuKienKm.MaSanPhamMua,DieuKienKm.idSanPhamMua,DieuKienKm.TenSanPhamMua,DieuKienKm.doanhso,DieuKienKm.SoLuongMua,DieuKienKm.SanLuongMua from          "     +
				"\n		(          "     +
				"\n			SELECT TRAKM.DONHANGID,TRAKM.CTKMID,TRAKM.SOLUONG as SoLuongTra,'' AS SoLuongTraQuyDoi,TRAKM.SOLUONG*SP.TRONGLUONG AS SanLuongTra,TRAKM.TONGGIATRI as SoTienTraKm,TRAKM.SPMA as MaSanPhamTra,SP.PK_SEQ as IdSpTra,SP.TEN as TenSanPhamTra          "     +
				"\n			FROM DONHANG_CTKM_TRAKM TRAKM           " +
				"\n			inner join DONHANG DH ON DH.PK_SEQ = TRAKM.DONHANGID "     +
				"\n			INNER JOIN CTKHUYENMAI CTKM ON TRAKM.CTKMID = CTKM.PK_SEQ  "     +
				"\n			LEFT JOIN SANPHAM SP ON TRAKM.SPMA = SP.MA   " +
				"\n			WHERE 1=1";
				/*if(obj.getYear().length() > 0 && obj.getMonth().length() >0){
				query +="\n 		and SUBSTRING(DH.NGAYNHAP, 1 , 7)  = '"+obj.getYear()+"-"+obj.getMonth()+"'  ";
				}*/
				if(obj.gettungay().length() > 0 && obj.getdenngay().length() > 0){
					query += "\n and dh.ngaynhap >= '"+obj.gettungay()+"' and dh.ngaynhap <= '"+obj.getdenngay()+"' ";
				}
			
				query += "\n		)as TraKm left JOIN          "     +
				"\n		(          "     +
				"\n			select a.pk_seq as CtKMId, c.donhang_fk as DhId, c.SANPHAM_FK as idSanPhamMua,SP.TEN AS TenSanPhamMua,SP.MA AS MaSanPhamMua,sum( c.soluong * c.giamua - isnull(c.chietkhau, '0') ) as DoanhSo, sum(c.soluong) as SoLuongMua,SUM(c.soluong*sp.TrongLuong)as SanLuongMua          "     +
				"\n			from ctkhuyenmai a inner join donhang_ctkm_trakm b on a.pk_seq = b.ctkmid            "     +
				"\n			inner join donhang_sanpham c on b.donhangId = c.donhang_fk           " +
				"\n			inner join DONHANG dh on dh.PK_SEQ = b.DONHANGID "     +
				"\n			inner join ctkm_dkkm d on a.pk_seq = d.ctkhuyenmai_fk           "     +
				"\n			inner join dieukienkm_sanpham e on d.dkkhuyenmai_fk = e.dieukienkhuyenmai_fk and c.sanpham_fk = e.sanpham_fk           "     +
				"\n			LEFT JOIN SANPHAM SP ON c.SANPHAM_FK = SP.PK_SEQ  "     +
				"\n			where 1=1 ";
			/*	"\n			select a.pk_seq as CtKMId, b.donhang_fk as DhId, b.SANPHAM_FK as idSanPhamMua,SP.TEN AS TenSanPhamMua,SP.MA AS MaSanPhamMua,sum( b.soluongmua * c.giamua - isnull(c.chietkhau, '0') ) as DoanhSo, sum(b.soluongmua) as SoLuongMua,SUM(b.soluongmua*sp.TrongLuong)as SanLuongMua          "     +
				"\n			from ctkhuyenmai a inner join donhang_ctkm_dkkm b on a.pk_seq = b.ctkm_fk            "     +
				"\n			inner join donhang_sanpham c on b.donhang_fk = c.donhang_fk           " +
				"\n			inner join DONHANG dh on dh.PK_SEQ = b.DONHANG_fk "     +
				"\n			inner join  BANGGIABANLECHUAN_NPP gb on dh.NPP_FK = gb.NPP_FK "     +
				"\n			inner join BANGGIABLC_SANPHAM cbh on cbh.BGBLC_FK = gb.BANGGIABANLECHUAN_FK and cbh.SANPHAM_FK = c.SANPHAM_FK "     +
				"\n			inner join ctkm_dkkm d on a.pk_seq = d.ctkhuyenmai_fk           "     +
				"\n			LEFT JOIN SANPHAM SP ON b.SANPHAM_FK = SP.PK_SEQ  "     +
				"\n			where 1=1 ";*/
				
				/*if(obj.getYear().length() > 0 && obj.getMonth().length() >0){
					query +="\n 		and SUBSTRING(DH.NGAYNHAP, 1 , 7)  = '"+obj.getYear()+"-"+obj.getMonth()+"'  ";
					}*/
				
				
				if(obj.gettungay().length() > 0 && obj.getdenngay().length() > 0){
						query += "\n and dh.ngaynhap >= '"+obj.gettungay()+"' and dh.ngaynhap <= '"+obj.getdenngay()+"' ";
					}
				
				
		
				query +="\n			group by a.pk_seq, c.donhang_fk ,c.SANPHAM_FK ,SP.TEN ,SP.MA        "     +
				"\n		)DieuKienKm on TraKm.DONHANGID=DieuKienKm.DhId and TraKm.CTKMID=DieuKienKm.CtKMId          "     +
			/*	"\n		Select 'TRA' as loai,TraKm.*         "     +
				"\n		from                   "     +
				"\n				(                "     +   
				"\n					SELECT TRAKM.DONHANGID,TRAKM.CTKMID,TRAKM.SOLUONG as SoLuongTra,'' AS SoLuongTraQuyDoi,TRAKM.SOLUONG*SP.TRONGLUONG AS SanLuongTra,TRAKM.TONGGIATRI as SoTienTraKm,TRAKM.SPMA as MaSanPhamTra,SP.PK_SEQ as IdSpTra,SP.TEN as TenSanPhamTra                   "     +
				"\n					FROM DONHANG_CTKM_TRAKM TRAKM                "     +    
				"\n					inner join DONHANG DH ON DH.PK_SEQ = TRAKM.DONHANGID          "     +
				"\n					INNER JOIN CTKHUYENMAI CTKM ON TRAKM.CTKMID = CTKM.PK_SEQ           "     +
				"\n					LEFT JOIN SANPHAM SP ON TRAKM.SPMA = SP.MA            "     +
				"\n					WHERE 1=1 and dh.PK_SEQ=6768345          "     +
				"\n				)as TraKm          "     +
					
				"\n		   union all          "     +  	
							
				"\n			select N'DKMUA' as loai,a.PK_SEQ,b.CTKMID,case when dk.SOLUONG>0 then dk.SOLUONG*b.SOXUAT else   d.SOLUONG end soluong ,'' soluongquydoi,'' sanluongtra,d.SOLUONG *d.giamua as doanhso,sp.MA,sp.PK_SEQ,sp.ten from DONHANG a          "     +
				"\n			inner join DONHANG_CTKM_TRAKM b on a.PK_SEQ=b.DONHANGID         "     +
				"\n			inner join CTKM_DKKM c on c.CTKHUYENMAI_FK=b.CTKMID         "     +
				"\n			inner join DONHANG_SANPHAM d on d.DONHANG_FK=a.PK_SEQ         "     +
				"\n			inner join sanpham sp on sp.PK_SEQ=d.SANPHAM_FK         "     +
				"\n			inner join  BANGGIABANLECHUAN_NPP gb on a.NPP_FK = gb.NPP_FK         "     +
				"\n			inner join BANGGIABLC_SANPHAM cbh on cbh.BGBLC_FK = gb.BANGGIABANLECHUAN_FK and cbh.SANPHAM_FK = d.SANPHAM_FK          "     +
				"\n			inner join DIEUKIENKM_SANPHAM dk on dk.DIEUKIENKHUYENMAI_FK=c.DKKHUYENMAI_FK and dk.SANPHAM_FK=d.SANPHAM_FK         "     +
				"\n			where a.PK_SEQ=6768345 and b.SPMA is null         "     +
				"\n		)         "     +*/
				"\n	) AS DieuKienKmTraKm          "     +
				"\n	inner join CTKHUYENMAI CTKM ON CTKM.PK_SEQ=DieuKienKmTraKm.CTKMID          "     +
				"\n	inner join DONHANG dh on DieuKienKmTraKm.DONHANGID=dh.PK_SEQ          "     +
				"\n	LEFT JOIN SANPHAM SP ON DieuKienKmTraKm.IdSpTra = SP.PK_SEQ          "     +
				"\n	LEFT JOIN NHANHANG NH ON NH.PK_SEQ = SP.NHANHANG_FK           "     +
				"\n	LEFT JOIN CHUNGLOAI CL ON CL.PK_SEQ = SP.CHUNGLOAI_FK           "     +
			
				"\n	INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = DH.NPP_FK           "     +
				"\n	left JOIN TINHTHANH TINH ON TINH.PK_SEQ = NPP.TinhThanh_FK           "     +
				"\n	left JOIN QUANHUYEN QUAN ON QUAN.PK_SEQ = TINH.PK_SEQ            "     +
				"\n	INNER JOIN KHACHHANG KH ON KH.PK_SEQ = DH.KHACHHANG_FK           "     +
				"\n	INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ = KH.KBH_FK           "     +
				"\n	INNER JOIN DAIDIENKINHDOANH DDKD ON DDKD.PK_SEQ = DH.DDKD_FK           "     +
				"\n	LEFT JOIN GIAMSATBANHANG GSBH ON GSBH.PK_SEQ = DH.GSBH_FK           "     +
				"\n	INNER JOIN KHUVUC KHUVUC ON KHUVUC.PK_SEQ = NPP.KHUVUC_FK           "     +
				"\n	INNER JOIN VUNG VUNG ON VUNG.PK_SEQ = KHUVUC.VUNG_FK             "     +
				"\n WHERE ISNULL(DieuKienKmTraKm.SoLuongTra, '0') >= 0  "     +
				"\n AND DH.PK_SEQ NOT IN  "     +
				"\n ( "     +
				"\n	SELECT DONHANG_FK FROM DONHANGTRAVE  "     +
				"\n	WHERE DONHANG_FK IS NOT NULL AND TRANGTHAI = '3'  "     +
				"\n ) ";
		System.out.println("Chot : "+obj.getDhChot());
		System.out.println("Unghang : "+obj.getUnghang());
		if(obj.getDhChot().equals("1"))
		{
			query+="and dh.TRANGTHAI='1' ";
		}
		else
		{
			query+="and dh.TRANGTHAI != '2' ";
		}
		
		query+=" and NPP.PK_SEQ IN "+util.quyen_npp(obj.getuserId()) +" and kbh.pk_seq in "+util.quyen_kenh(obj.getuserId());
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
		
		query = query + " order by DieuKienKmTraKm.DONHANGID,DieuKienKmTraKm.CtKMId, DieuKienKmTraKm.MaSanPhamTra  ";
		
		System.out.print("1.BC Xuat khuyen mai TT: " + query);
		ResultSet rs = db.get(query);
		int i = 2;
		if (rs != null) 
		{
			try 
			{// se do rong cho cac cot se dung
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
				cells.setColumnWidth(21, 15.0f);
				cells.setColumnWidth(22, 15.0f);
				cells.setColumnWidth(23, 15.0f);
				cells.setColumnWidth(24, 15.0f);	
				cells.setColumnWidth(25, 15.0f);	
				Cell cell = null;
				String masptra = "";
				String Prev="";
				while (rs.next())
				{
					String Cur=rs.getString("DONHANGID")+"_"+rs.getString("CtKMId")+rs.getString("MaSanPhamTra");
					String Chanel = rs.getString("Channel");
					String Region = rs.getString("Region");
					String area = rs.getString("Area");

					String Distributor = rs.getString("Distributor");
					String PromotionProgram = rs.getString("PROGRAM");

					String Brand = "" ;
					
					if(rs.getString("Brand") == null)
						Brand = "";
					else
						Brand = rs.getString("Brand");
					
					String Category = "";

					if(rs.getString("Category") == null)
						Category = "";
					else
						Category = rs.getString("Category");
					
					String Province = rs.getString("Province");
					String Town = rs.getString("Town");
					String DisCode = rs.getString("DISTRIBUTORID");
					String ProgramCode = rs.getString("PROGRAM_CODE");
					String ProgramType = rs.getString("PROGRAM_TYPE");
					
					String MaSanPhamTra = "";
					
					if(rs.getString("MaSanPhamTra") == null)
						MaSanPhamTra = "";
					else
						MaSanPhamTra = rs.getString("MaSanPhamTra");

					String TenSanPhamTra = "";
					if(rs.getString("TenSanPhamTra") == null)
						TenSanPhamTra = "";
					else
						TenSanPhamTra = rs.getString("TenSanPhamTra");
					
					
					String MaSanPhamMua = "";
					if(rs.getString("MaSanPhamMua") == null)
						MaSanPhamMua = "";
					else
						MaSanPhamMua = rs.getString("MaSanPhamMua");

					String TenSanPhamMua = "";
					if(rs.getString("TenSanPhamMua") == null)
						TenSanPhamMua = "";
					else
						TenSanPhamMua = rs.getString("TenSanPhamMua");
					
					
					String SaleRep = rs.getString("SALESREP");
					//String CustomerKey = rs.getString("khId");
					String Customer = rs.getString("Customer");
					int CustomerKey = Integer.parseInt(rs.getString("khId"));
					String Offdate = rs.getString("DATE");

					long doanhso = Math.round(rs.getDouble("DOANHSO"));
					cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(Chanel);
					cell = cells.getCell("AB" + Integer.toString(i)); cell.setValue(Region);
					cell = cells.getCell("AC" + Integer.toString(i)); cell.setValue(area);
					cell = cells.getCell("AD" + Integer.toString(i)); cell.setValue(DisCode);
					cell = cells.getCell("AE" + Integer.toString(i)); cell.setValue(Distributor); 
					cell = cells.getCell("AF" + Integer.toString(i)); cell.setValue(ProgramCode);
					cell = cells.getCell("AG" + Integer.toString(i)); cell.setValue(PromotionProgram);
					cell = cells.getCell("AH" + Integer.toString(i)); cell.setValue(MaSanPhamTra);
					cell = cells.getCell("AI" + Integer.toString(i)); cell.setValue(TenSanPhamTra);
					cell = cells.getCell("AJ" + Integer.toString(i)); cell.setValue(MaSanPhamMua);
					cell = cells.getCell("AK" + Integer.toString(i)); cell.setValue(TenSanPhamMua);
					cell = cells.getCell("AL" + Integer.toString(i)); cell.setValue(Brand);
					cell = cells.getCell("AM" + Integer.toString(i)); cell.setValue(Category);
					cell = cells.getCell("AN" + Integer.toString(i)); cell.setValue(Province);
					cell = cells.getCell("AO" + Integer.toString(i)); cell.setValue(Town);					
					cell = cells.getCell("AP" + Integer.toString(i)); cell.setValue(ProgramType);
					cell = cells.getCell("AQ" + Integer.toString(i)); cell.setValue(SaleRep);
					cell = cells.getCell("AR" + Integer.toString(i)); cell.setValue(CustomerKey);
					cell = cells.getCell("AS" + Integer.toString(i)); cell.setValue(Customer);
					cell = cells.getCell("AT" + Integer.toString(i)); cell.setValue(Offdate);
					
					if(!Cur.equals(Prev)){
						Prev=Cur;
						masptra=rs.getString("MaSanPhamTra");
						//System.out.println("Diff[MaSp]"+rs.getString("MaSanPhamTra")+"[Ma]"+masptra);
						cell = cells.getCell("AU" + Integer.toString(i));		cell.setValue(rs.getDouble("SoLuongTra"));
						cell = cells.getCell("BA" + Integer.toString(i)); 		cell.setValue(rs.getDouble("sanluongTra"));
					}else{
						//System.out.println("Same [MaSp]"+rs.getString("MaSanPhamTra")+"[Ma]"+masptra);
						cell = cells.getCell("AU" + Integer.toString(i));		cell.setValue(0);
						cell = cells.getCell("BA" + Integer.toString(i)); 		cell.setValue(0);
					}
					
					/*cell = cells.getCell("AU" + Integer.toString(i)); cell.setValue(rs.getDouble("SoLuongTra"));*/
					cell = cells.getCell("AV" + Integer.toString(i)); cell.setValue(rs.getDouble("SoTienTraKm"));
					
					cell = cells.getCell("AW" + Integer.toString(i)); cell.setValue(doanhso);
					cell = cells.getCell("AX" + Integer.toString(i)); cell.setValue(rs.getString("diachi"));
					cell = cells.getCell("AY" + Integer.toString(i)); cell.setValue(rs.getDouble("SoLuongMua"));
					cell = cells.getCell("AZ" + Integer.toString(i)); cell.setValue(rs.getDouble("NPPTUCHAY"));	
					cell = cells.getCell("BA" + Integer.toString(i)); cell.setValue(rs.getString("hinhthuc"));	
					
					cell = cells.getCell("BB" + Integer.toString(i)); cell.setValue(rs.getString("loai"));	
					cell = cells.getCell("BC" + Integer.toString(i)); cell.setValue(rs.getString("DONHANGID"));	
					cell = cells.getCell("BD" + Integer.toString(i)); cell.setValue(rs.getString("schemeerp"));	
					cell = cells.getCell("BE" + Integer.toString(i)); cell.setValue(rs.getString("tungay"));	
					cell = cells.getCell("BF" + Integer.toString(i)); cell.setValue(rs.getString("denngay"));	
					i++;

				}
				
				

				if(rs != null) rs.close();
				if(db != null)  db.shutDown();
				
				if(i==2){
					throw new Exception("Khong co bao cao voi dieu kien da chon");
				}
				
				//setAn(workbook, 49);
		    	return true;

				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println(e.toString());
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

	
	private String getDateTime1() {
		DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		Date date = new Date();
		return dateFormat.format(date);
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
	
	private String setQuery(IStockintransit obj,Utility util) 
	{
		
		//, "MaSanPhamMua", "TenSanPhamMua", "BRAND", "CATEGORY", "PROVINCE", "TOWN", "PROGRAM_TYPE", "SALESREP"
		//, "khId", "CUSTOMER", "DATE", "SoLuongTra", "SoTienTraKm", "DOANHSO", "diachi", "SoLuongMua", "NPPTUCHAY"
		//, "Hinhthuc", "loai", "DONHANGID", "schemeerp", "tungay", "denngay"
		String query = 				
					"\n	SELECT KBH.TEN AS CHANNEL,VUNG.TEN AS REGION,KHUVUC.TEN AS AREA, NPP.MANPP AS DISTRIBUTORID,             " +
				"\n	NPP.TEN AS DISTRIBUTOR,CTKM.SCHEME AS PROGRAM_CODE  , ISNULL(ctkm.diengiai + '__' + ctkm.tungay + '-' + ctkm.denngay, '') AS PROGRAM" +
				"\n		,     DieuKienKmTraKm.MaSanPhamTra,DieuKienKmTraKm.TenSanPhamTra  " +
				"\n	, DieuKienKmTraKm.MaSanPhamMua, DieuKienKmTraKm.TenSanPhamMua, NH.TEN AS BRAND,CL.TEN AS CATEGORY,  " +
				"\n	                  " +
				"\n		isnull(TINH.TEN,'NA') AS PROVINCE,isnull(QUAN.TEN,'NA') AS TOWN,  " +
				"\n		CASE WHEN CTKM.LOAICT = 1 THEN 'Binh Thuong' WHEN CTKM.LOAICT = 2 THEN 'On Top'              " +
				"\n		WHEN CTKM.LOAICT = 3 THEN 'Tich Luy' else 'Chua Xac Dinh' END AS PROGRAM_TYPE" +
				"\n	, isnull(DDKD.TEN, 'Chua khai bao') AS SALESREP,    " +
				"\n	CAST(KH.SMARTID as nvarchar(8)) as khId, KH.ten AS CUSTOMER,DH.NGAYNHAP AS DATE,DieuKienKmTraKm.SoLuongTra,DieuKienKmTraKm.SoTienTraKm  " +
				"\n	,DieuKienKmTraKm.DOANHSO, KH.DIACHI AS diachi ,DieuKienKmTraKm.SoLuongMua,   CTKM.NPPTUCHAY,  " +
				"\n	case when ctkm.kho_fk = 100001 then N'Trả trước' else N'Trả sau' end as Hinhthuc ,  " +
				"\n	DieuKienKmTraKm.loai,   DieuKienKmTraKm.DONHANGID   ,  " +
				"\n	ctkm.schemeerp, ctkm.tungay, ctkm.denngay ,DDKD.smartId MANVBH , ss.soxuat    " +
				"\n	FROM          "     +
				"\n	(	         "     +
		
				"\n	select data.loai,data.DONHANGID,data.CTKMID,data.SoLuongTra,data.SoLuongMua,data.SanLuongTra, '' sanluongmua,sum(data.SoTienTraKm) as SoTienTraKm, data.doanhso,data.MaSanPhamTra, data.MaSanPhamMua,data.IdSpTra, data.IdSanphammua,data.TenSanPhamTra, data.TenSanPhammua from ( "     +
				"\n			Select 'TRA' as loai,TraKm.*          "     +
				"\n			from                   "     + 
				"\n			(                 "     +
				"\n				SELECT TRAKM.DONHANGID,TRAKM.CTKMID,TRAKM.SOLUONG as SoLuongTra, '0' as SoLuongMua,'0' AS SoLuongTraQuyDoi,TRAKM.SOLUONG*SP.TRONGLUONG AS SanLuongTra,case when  trakm.Sanpham_fk is null then TRAKM.TONGGIATRI else 0 end  as SoTienTraKm, '0' as doanhso,TRAKM.SPMA as MaSanPhamTra,'0' MaSanPhamMua,'0' IdSanphammua,SP.PK_SEQ as IdSpTra,'' TenSanPhammua,SP.TEN as TenSanPhamTra     "     +               
				"\n				FROM DONHANG_CTKM_TRAKM TRAKM     "     +            
				"\n				inner join DONHANG DH ON DH.PK_SEQ = TRAKM.DONHANGID       "     +    
				"\n				INNER JOIN CTKHUYENMAI CTKM ON TRAKM.CTKMID = CTKM.PK_SEQ     "     +       
				"\n				LEFT JOIN SANPHAM SP ON TRAKM.SPMA = SP.MA      "     +       
				"\n				WHERE 1=1         "     ;
							if(obj.gettungay().length() > 0 && obj.getdenngay().length() > 0){
							query += "\n and dh.ngaynhap >= '"+obj.gettungay()+"' and dh.ngaynhap <= '"+obj.getdenngay()+"' ";
						}
					query +=	"\n			)as TraKm           "     +
					"\n		 union all "     +
					 "\n		 select N'DKMUA' as loai,dh.PK_SEQ,a.pk_seq as CtKMId,'0' as soluongtra,b.soluongmua ,'0' soluongquydoi,'0' sanluongtra,'0' sotientrakm,( b.soluongmua * c.dongiagoc ) as doanhso,'0' MaSanPhamTra,sp.MA as MaSanPhamMua,sp.PK_SEQ as IdSpmua, '0' IdSpTra,sp.ten as TenSanPhammua,'' TenSanPhamTra "     +
					 "\n			from donhang dh inner join donhang_ctkm_dkkm b on dh.pk_seq = b.donhang_fk         " +
						"\n			inner join donhang_sanpham c on b.donhang_fk = c.donhang_fk          "     +  
						"\n			inner JOIN SANPHAM SP ON b.SANPHAM_FK = SP.PK_SEQ   "     +
						"\n			inner join ctkhuyenmai a on a.PK_SEQ = b.ctkm_fk  "     +
						"\n			inner join  BANGGIABANLECHUAN_NPP gb on dh.NPP_FK = gb.NPP_FK  "     +
						"\n			inner join BANGGIABLC_SANPHAM cbh on cbh.BGBLC_FK = gb.BANGGIABANLECHUAN_FK and cbh.SANPHAM_FK = c.SANPHAM_FK and cbh.sanpham_fk = sp.pk_seq "     +
						"\n			inner join ctkm_dkkm d on a.pk_seq = d.ctkhuyenmai_fk            "     +
						"\n			where 1=1 and exists (select 1 from donhang_ctkm_trakm where ctkmid = b.ctkm_fk and c.donhang_fk = donhangid ) " ;
						if(obj.gettungay().length() > 0 && obj.getdenngay().length() > 0){
							query += "\n and dh.ngaynhap >= '"+obj.gettungay()+"' and dh.ngaynhap <= '"+obj.getdenngay()+"' ";
						}
					query +=
						"\n	)as data "     +
						"\n	group by data.loai,data.DONHANGID,data.CTKMID,data.SoLuongTra,data.SoLuongMua,data.SanLuongTra, data.doanhso,data.MaSanPhamTra, data.MaSanPhamMua,data.IdSpTra, data.IdSanphammua,data.TenSanPhamTra, data.TenSanPhammua "     +
				"\n	 ) AS DieuKienKmTraKm          "     + 
				"\n	inner join CTKHUYENMAI CTKM ON CTKM.PK_SEQ=DieuKienKmTraKm.CTKMID          "     +
				"\n	inner join DONHANG dh on DieuKienKmTraKm.DONHANGID=dh.PK_SEQ          "     +
				"\n	LEFT JOIN SANPHAM SP ON isnull(DieuKienKmTraKm.IdSanphammua,DieuKienKmTraKm.IdSpTra) = SP.PK_SEQ          "     +
				"\n	LEFT JOIN NHANHANG NH ON NH.PK_SEQ = SP.NHANHANG_FK           "     +
				"\n	LEFT JOIN CHUNGLOAI CL ON CL.PK_SEQ = SP.CHUNGLOAI_FK           "     +
				/*"\n	LEFT JOIN SANPHAM SPM ON DieuKienKmTraKm.IdSpTra = SP.PK_SEQ          "     +
				"\n	LEFT JOIN NHANHANG NHM ON NH.PK_SEQ = SP.NHANHANG_FK           "     +
				"\n	LEFT JOIN CHUNGLOAI CLM ON CL.PK_SEQ = SP.CHUNGLOAI_FK           "     +*/
				"\n	INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = DH.NPP_FK           "     +
				"\n	left JOIN TINHTHANH TINH ON TINH.PK_SEQ = NPP.TinhThanh_FK           "     +
				"\n	left JOIN QUANHUYEN QUAN ON QUAN.PK_SEQ = TINH.PK_SEQ            "     +
				"\n	INNER JOIN KHACHHANG KH ON KH.PK_SEQ = DH.KHACHHANG_FK           "     +
				"\n	INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ = npp.KBH_FK           "     +
				"\n	INNER JOIN DAIDIENKINHDOANH DDKD ON DDKD.PK_SEQ = DH.DDKD_FK           "     +
				"\n	LEFT JOIN GIAMSATBANHANG GSBH ON GSBH.PK_SEQ = DH.GSBH_FK           "     +
				"\n	INNER JOIN KHUVUC KHUVUC ON KHUVUC.PK_SEQ = NPP.KHUVUC_FK           "     +
				"\n	INNER JOIN VUNG VUNG ON VUNG.PK_SEQ = KHUVUC.VUNG_FK             "     +
				"\n	outer apply  "     +
				"\n	(  "     +
				"\n		select max(case when tkm.loai = 2 then 1 else ss.soxuat end ) soxuat   "     +
				"\n		from donhang_ctkm_trakm ss "
				+ "\n	inner join trakhuyenmai tkm on tkm.pk_seq = ss.trakmId  "     +
				"\n		where  ss.donhangId = DieuKienKmTraKm.DONHANGID and ss.CTKMID = DieuKienKmTraKm.CTKMID  "     +
				"\n	) ss           "     +
				"\n WHERE ISNULL(DieuKienKmTraKm.SoLuongTra, '0') >= 0  "     +
				"\n AND DH.PK_SEQ NOT IN  "     +
				"\n ( "     +
				"\n	SELECT DONHANG_FK FROM DONHANGTRAVE  "     +
				"\n	WHERE DONHANG_FK IS NOT NULL AND TRANGTHAI = '3'  "     +
				"\n ) ";
		System.out.println("Chot : "+obj.getDhChot());
		System.out.println("Unghang : "+obj.getUnghang());
		if(obj.getDhChot().equals("1"))
		{
			query+="and dh.TRANGTHAI='1' ";
		}
		else
		{
			query+="and dh.TRANGTHAI != '2' ";
		}
		if(!obj.getView().equals("NPP"))
			query+=" and NPP.PK_SEQ IN "+util.quyen_npp(obj.getuserId()) +" and kbh.pk_seq in "+util.quyen_kenh(obj.getuserId());
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
		
		query = query + " order by DieuKienKmTraKm.DONHANGID,DieuKienKmTraKm.CtKMId, DieuKienKmTraKm.MaSanPhamTra  \n";
		
		System.out.print("1.BC Xuat khuyen mai TT: " + query);
		return query;
	
	}
	
}
