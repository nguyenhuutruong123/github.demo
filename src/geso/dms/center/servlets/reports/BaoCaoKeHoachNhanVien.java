package geso.dms.center.servlets.reports;

import geso.dms.center.beans.kehoachnhanvien.IKeHoachNhanVienChiTiet;
import geso.dms.center.beans.kehoachnhanvien.IKeHoachNhanVienNgay;
import geso.dms.center.beans.kehoachnhanvien.imp.KeHoachNhanVienChiTiet;
import geso.dms.center.beans.kehoachnhanvien.imp.KeHoachNhanVienList;
import geso.dms.center.beans.kehoachnhanvien.imp.KeHoachNhanVienNgay;
import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.distributor.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.sun.org.apache.xml.internal.serializer.utils.Utils;

public class BaoCaoKeHoachNhanVien extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public BaoCaoKeHoachNhanVien() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
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
		
		String thang = getDateTime("MM");
		String nam = getDateTime("yyyy");
		
		obj.setFromMonth(thang); obj.setFromYear(nam);
		obj.setToMonth(thang); obj.setToYear(nam);
		//obj.init();
		///obj.settype("1");
		session.setAttribute("obj", obj);
		session.setAttribute("userId", obj.getuserId());
		session.setAttribute("userTen", obj.getuserTen());
		String nextJSP = "/AHF/pages/Center/BaoCaoKeHoachNhanVien.jsp";
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
		
		obj.setFromMonth(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tuthang"))));
		obj.setToMonth(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denthang"))));
		obj.setFromYear(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tunam"))));
		obj.setToYear(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dennam"))));
		obj.setuserId(userId!=null? userId:"");
		
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action") != null? util.antiSQLInspection(request.getParameter("action"))) : "";
		String nextJSP = "/AHF/pages/Center/BaoCaoKeHoachNhanVien.jsp";
		
		if(action.equals("Taomoi"))
		{
			try
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BaoCaoKeHoachNhanVien_"+util.setTieuDe(obj)+".xlsm");
		        if(!CreatePivotTable(out,obj))
		        {
		        	obj.setMsg("Không có dữ liệu trong thời gian này.");
		    		session.setAttribute("obj", obj);	
		    		response.sendRedirect(nextJSP);
		        }
		        
			}
			catch(Exception ex)
			{
				obj.setMsg(ex.getMessage());
			}
		}
		
		session.setAttribute("obj", obj);	
		response.sendRedirect(nextJSP);
	}
	
	private boolean CreatePivotTable(OutputStream out, IStockintransit obj) throws Exception 
	{
		//String chuoi = getServletContext().getInitParameter("path") + "\\BaoCaoKeHoachNhanVien.xlsm";
		//FileInputStream fstream = new FileInputStream(chuoi);
		File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\BaoCaoKeHoachNhanVien.xlsm");
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
		try {
			
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");

			Cells cells = worksheet.getCells();

			cells.setRowHeight(0, 20.0f);
			Cell cell = cells.getCell("A1");
		    cell.setValue("BÁO CÁO KẾ HOẠCH NHÂN VIÊN");   	
		    
		    cells.setRowHeight(2, 18.0f);
		    cell = cells.getCell("A2"); 
		    getCellStyle(workbook,"A2",Color.NAVY,true,10);
		    cell.setValue("Từ tháng " + obj.getFromMonth() + " năm " + obj.getFromYear() + " đến tháng " + obj.getToMonth() + " năm " + obj.getToYear());	
			 
		    cells.setRowHeight(4, 18.0f);
		    cell = cells.getCell("A3");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
		    
		    cells.setRowHeight(5, 18.0f);
		    cell = cells.getCell("A4");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());
		    
		    Color bgColor = new Color(197, 217, 241);
			short headerAlign = TextAlignmentType.CENTER;
		    cell = cells.getCell("A6"); 	cell.setValue("Nhân Viên" );  ReportAPI.setCellHeader(cell, bgColor, true, headerAlign);
		    cell = cells.getCell("B6"); 	cell.setValue("Chức Vụ");  ReportAPI.setCellHeader(cell, bgColor, true, headerAlign);
		    cell = cells.getCell("C6"); 	cell.setValue("Ngày");  ReportAPI.setCellHeader(cell, bgColor, true, headerAlign);
		    cell = cells.getCell("D6"); 	cell.setValue("Kế Hoạch");  ReportAPI.setCellHeader(cell, bgColor, true, headerAlign);
		    cell = cells.getCell("E6"); 	cell.setValue("Ghi Chú");  ReportAPI.setCellHeader(cell, bgColor, true, headerAlign);
		    cell = cells.getCell("F6"); 	cell.setValue("Thực Hiện");  ReportAPI.setCellHeader(cell, bgColor, true, headerAlign);
		    cell = cells.getCell("G6"); 	cell.setValue("Ghi Chú Thực Hiện");  ReportAPI.setCellHeader(cell, bgColor, true, headerAlign);

		}
		catch(Exception ex)
		{
			throw new Exception("Xảy ra lỗi khi tạo báo cáo (" + ex.getMessage() + ")");
		}
	}
	
	private boolean CreateStaticData(Workbook workbook, IStockintransit obj) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    
	    String queryIdCapDuoi = KeHoachNhanVienList.getIdNhanVienCapDuoiQuery(db, obj.getuserId());
	    
	    String 	fromDate = obj.getFromYear() + "-" + (obj.getFromMonth().length() < 2 ? "0" + obj.getFromMonth() : obj.getFromMonth()),
	    		toDate = obj.getToYear() + "-" + (obj.getToMonth().length() < 2 ? "0" + obj.getToMonth() : obj.getToYear());
	    
	    String
	    query = " select * from (" +
				" 	select a.pk_seq, nv.ten as nhanvien, nv.loai, a.thang, a.nam, isnull(a.trangthai, 0) as trangthai, '0' as loaikehoach " +
				" 	from kehoachnv a " + 
				" 	inner join nhanvien nv on nv.pk_seq = a.nhanvien_fk " + 
				" 	where a.trangthai = 1 and a.nhanvien_fk in (" + queryIdCapDuoi + ")" +
				" 		AND '" + fromDate + "' <= CAST(a.NAM AS NVARCHAR(10)) + '-' + (CASE WHEN a.THANG < 10 THEN '0' + CAST(a.THANG AS NVARCHAR(10)) ELSE CAST(a.THANG AS NVARCHAR(10)) END ) " + 
				"		AND CAST(a.NAM AS NVARCHAR(10)) + '-' + (CASE WHEN a.THANG < 10 THEN '0' + CAST(a.THANG AS NVARCHAR(10)) ELSE CAST(a.THANG AS NVARCHAR(10)) END ) <= '" + toDate + "' " + 
				" ) a order by a.loai, a.nhanvien, a.nam desc, a.thang desc, a.loaikehoach, a.trangthai ";
	    
		
		System.out.println("[BaoCaoKeHoachNhanVien.CreateStaticData] query " + query);
		ResultSet rs = db.get(query);
		
		try {
			for(int j = 0; j < 15; j++)
				cells.setColumnWidth(j, 15.0f);
			Cell cell = null;
			
			int i = 7; int _i = i;
			while(rs.next())
			{
				String khId = rs.getString("pk_seq");
				String nhanvien = rs.getString("nhanvien");
				String loai = rs.getString("loai") == null ? "" : rs.getString("loai");
				String thang = rs.getString("thang");
				String nam = rs.getString("nam");
				String chucvu = loai.equals("4") ? "Nhân Viên TT" : loai.equals("1") ? "BM" : loai.equals("2") ? "ASM" : loai.equals("3") ? "GSBH" : "";
				
				
				Color lighgray = new Color(230,230,230);
				cell = cells.getCell("A" + Integer.toString(i)); 	cell.setValue(nhanvien); ReportAPI.setCellHeader(cell, lighgray, true);
				cell = cells.getCell("B" + Integer.toString(i)); 	cell.setValue(chucvu); ReportAPI.setCellHeader(cell, lighgray, true);
				_i = i;
				
				//Lấy chi tiết
				IKeHoachNhanVienNgay[] ngayList = new IKeHoachNhanVienNgay[0];
				if(thang != null && thang.trim().length() > 0 && nam != null && nam.trim().length() > 0)
				{
					try
					{
						int _nam = Integer.parseInt(thang);
						int _thang = Integer.parseInt(nam);
						
						Calendar mycal = new GregorianCalendar(_nam, _thang-1, 1);
						int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
						
						ngayList = new IKeHoachNhanVienNgay[daysInMonth];
						
						for(int k = 0; k < daysInMonth; k++) 
						{
							IKeHoachNhanVienNgay khNgay = new KeHoachNhanVienNgay("");
							khNgay.setNgay(String.valueOf(k+1));
							ngayList[k] = khNgay;
						}
					}
					catch(Exception e) 
					{
						 e.printStackTrace();
					}
				}
				
				query = " SELECT TT.KEHOACHNV_FK, TT.NGAY, NPP.TEN AS NPP, ISNULL(TT.LAT, '') AS LAT, ISNULL(TT.LONG, '') AS LONG, ISNULL(TT.GHICHU, '') AS GHICHU, ISNULL(TT.GHICHU2, '') AS GHICHU2 " +
						" FROM KEHOACHNV_NPP TT " +
						" INNER JOIN NHAPHANPHOI NPP ON TT.NPP_FK = NPP.PK_SEQ " +
						" WHERE TT.KEHOACHNV_FK = " + khId +
						" ORDER BY TT.NGAY ";
		    	System.out.println(query);
		    	ResultSet rs2 =  db.get(query);
		    	try 
				{
					while(rs2.next()) {
						int ngay = rs2.getInt("NGAY");
						IKeHoachNhanVienNgay khNgay = ngayList[ngay-1];
						
						IKeHoachNhanVienChiTiet chitiet = new KeHoachNhanVienChiTiet(khId);
						chitiet.setNgay(rs2.getString("NGAY"));
						chitiet.setNppId(rs2.getString("NPP"));
						chitiet.setLat(rs2.getString("LAT"));
						chitiet.setLon(rs2.getString("LONG"));
						chitiet.setGhiChu(rs2.getString("GHICHU"));
						chitiet.setGhiChu2(rs2.getString("GHICHU2"));
						
						khNgay.getNppList().add(chitiet);
					}
					rs2.close();
				}
				catch(Exception e) 
				{
					e.printStackTrace();
				}
		    	
		    	query = " SELECT TT.KEHOACHNV_FK, TT.NGAY, T.TEN AS TINHTHANH, QH.TEN AS QUANHUYEN, ISNULL(TT.LAT, '') AS LAT, ISNULL(TT.LONG, '') AS LONG, ISNULL(TT.DIACHI, '') AS DIACHI, ISNULL(TT.GHICHU, '') AS GHICHU, ISNULL(TT.GHICHU2, '') AS GHICHU2 " +
						" FROM KEHOACHNV_THITRUONG TT" +
						" INNER JOIN TINHTHANH T ON TT.TINH_FK = T.PK_SEQ " +
						" INNER JOIN QUANHUYEN QH ON TT.QUANHUYEN_FK = QH.PK_SEQ " +
						" WHERE TT.KEHOACHNV_FK = " + khId +
						" ORDER BY TT.NGAY ";
		    	System.out.println(query);
		    	rs2 =  db.get(query);
		    	try 
				{
					while(rs2.next()) {
						int ngay = rs2.getInt("NGAY");
						IKeHoachNhanVienNgay khNgay = ngayList[ngay-1];
						
						IKeHoachNhanVienChiTiet chitiet = new KeHoachNhanVienChiTiet(khId);
						chitiet.setNgay(rs2.getString("NGAY"));
						chitiet.setTinhId(rs2.getString("TINHTHANH"));
						chitiet.setQuanHuyenId(rs2.getString("QUANHUYEN"));
						chitiet.setLat(rs2.getString("LAT"));
						chitiet.setLon(rs2.getString("LONG"));
						chitiet.setDiaChi(rs2.getString("DIACHI"));
						chitiet.setGhiChu(rs2.getString("GHICHU"));
						chitiet.setGhiChu2(rs2.getString("GHICHU2"));
						
						khNgay.getThiTruongList().add(chitiet);
					}
					rs2.close();
				}
				catch(Exception e) 
				{
					e.printStackTrace();
				}
				
				if(ngayList.length > 0) {
					for(int j = 0; j < ngayList.length; j++) {
						IKeHoachNhanVienNgay ngay = ngayList[j]; 
						
						String _ngay = ngay.getNgay().trim().length() < 2 ? "0" + ngay.getNgay() : ngay.getNgay();
						String _thang = thang.trim().length() < 2 ? "0" + thang : thang;
						
						cell = cells.getCell("C" + Integer.toString(i)); cell.setValue(_ngay + "-" + _thang + "-" + nam);
						ReportAPI.setCellHeader(cell, Color.WHITE, false, TextAlignmentType.RIGHT);
						
						if(ngay.getNppList().size() > 0 || ngay.getThiTruongList().size() > 0) {
							//NPP
							for(int k = 0; k < ngay.getNppList().size(); k++) {
								IKeHoachNhanVienChiTiet chitiet = ngay.getNppList().get(k);
								if(i != _i) {
									cell = cells.getCell("A" + Integer.toString(i)); 	ReportAPI.setCellHeader(cell, Color.WHITE, false);
									cell = cells.getCell("B" + Integer.toString(i)); 	ReportAPI.setCellHeader(cell, Color.WHITE, false);
								}
								cell = cells.getCell("C" + Integer.toString(i));	ReportAPI.setCellHeader(cell, Color.WHITE, false, TextAlignmentType.RIGHT);
								cell = cells.getCell("D" + Integer.toString(i)); 	cell.setValue(chitiet.getNppId()); ReportAPI.setCellHeader(cell, Color.WHITE, false); 
								cell = cells.getCell("E" + Integer.toString(i)); 	cell.setValue(chitiet.getGhiChu()); ReportAPI.setCellHeader(cell, Color.WHITE, false);
								cell = cells.getCell("F" + Integer.toString(i)); 	cell.setValue(chitiet.getLat().trim().length() > 0 && chitiet.getLon().trim().length() > 0 ? "Đã viếng thăm" : "" ); ReportAPI.setCellHeader(cell, Color.WHITE, false);
								cell = cells.getCell("G" + Integer.toString(i)); 	cell.setValue(chitiet.getGhiChu2()); ReportAPI.setCellHeader(cell, Color.WHITE, false);
								i++;
							}
							
							//Thị trường
							for(int k = 0; k < ngay.getThiTruongList().size(); k++) {
								IKeHoachNhanVienChiTiet chitiet = ngay.getThiTruongList().get(k);
								if(i != _i) {
									cell = cells.getCell("A" + Integer.toString(i)); 	ReportAPI.setCellHeader(cell, Color.WHITE, false);
									cell = cells.getCell("B" + Integer.toString(i)); 	ReportAPI.setCellHeader(cell, Color.WHITE, false);
								}
								cell = cells.getCell("C" + Integer.toString(i));	ReportAPI.setCellHeader(cell, Color.WHITE, false, TextAlignmentType.RIGHT);
								cell = cells.getCell("D" + Integer.toString(i)); 	cell.setValue(chitiet.getTinhId() + " - " + chitiet.getQuanHuyenId()); ReportAPI.setCellHeader(cell, Color.WHITE, false); 
								cell = cells.getCell("E" + Integer.toString(i)); 	cell.setValue(chitiet.getGhiChu()); ReportAPI.setCellHeader(cell, Color.WHITE, false); 
								cell = cells.getCell("F" + Integer.toString(i)); 	cell.setValue(chitiet.getLat().trim().length() > 0 && chitiet.getLon().trim().length() > 0 ? "Đã viếng thăm tại địa chỉ: " + chitiet.getDiaChi() : "" ); ReportAPI.setCellHeader(cell, Color.WHITE, false); 
								cell = cells.getCell("G" + Integer.toString(i)); 	cell.setValue(chitiet.getGhiChu2()); ReportAPI.setCellHeader(cell, Color.WHITE, false); 
								i++;
							}
							
						} 
						else 
						{
							if(i != _i) {
								cell = cells.getCell("A" + Integer.toString(i)); 	ReportAPI.setCellHeader(cell, Color.WHITE, false);
								cell = cells.getCell("B" + Integer.toString(i)); 	ReportAPI.setCellHeader(cell, Color.WHITE, false);
							}
							cell = cells.getCell("C" + Integer.toString(i)); ReportAPI.setCellHeader(cell, Color.WHITE, false, TextAlignmentType.RIGHT);
							cell = cells.getCell("D" + Integer.toString(i)); ReportAPI.setCellHeader(cell, Color.WHITE, false);
							cell = cells.getCell("E" + Integer.toString(i)); ReportAPI.setCellHeader(cell, Color.WHITE, false);
							cell = cells.getCell("F" + Integer.toString(i)); ReportAPI.setCellHeader(cell, Color.WHITE, false);
							cell = cells.getCell("G" + Integer.toString(i)); ReportAPI.setCellHeader(cell, Color.WHITE, false);
							i++;
						}
					}
				} else {
					if(i != _i) {
						cell = cells.getCell("A" + Integer.toString(i)); 	ReportAPI.setCellHeader(cell, Color.WHITE, false);
						cell = cells.getCell("B" + Integer.toString(i)); 	ReportAPI.setCellHeader(cell, Color.WHITE, false);
					}
					cell = cells.getCell("C" + Integer.toString(i)); ReportAPI.setCellHeader(cell, Color.WHITE, false, TextAlignmentType.RIGHT);
					cell = cells.getCell("D" + Integer.toString(i)); ReportAPI.setCellHeader(cell, Color.WHITE, false);
					cell = cells.getCell("E" + Integer.toString(i)); ReportAPI.setCellHeader(cell, Color.WHITE, false);
					cell = cells.getCell("F" + Integer.toString(i)); ReportAPI.setCellHeader(cell, Color.WHITE, false);
					cell = cells.getCell("G" + Integer.toString(i)); ReportAPI.setCellHeader(cell, Color.WHITE, false);
					i++;
				}
				
				
			}
			if(rs!=null) rs.close();
			if(db != null) db.shutDown();
			if(i==2){
				throw new Exception("Khong co bao cao trong thoi gian nay...");
			}
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
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

	private String getDateTime(String pattern) 
	{
		Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
	}
}
