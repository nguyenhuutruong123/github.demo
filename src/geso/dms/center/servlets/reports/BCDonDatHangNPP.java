package geso.dms.center.servlets.reports;

import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.report.Ireport;
import geso.dms.distributor.beans.report.imp.Report;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class BCDonDatHangNPP extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	private String setQuery(HttpServletRequest request,String tungay,String denngay,String st) 
	{
		String query = 
		"SELECT DDH.PK_SEQ AS MADDH,DDH.SOID AS SOID, DDH.NGAYGIODAT AS NGAYDAT,ISNULL(DDH.NGAYDENGHIGH,'') AS NGAYDENGHIGIAOHANG,LYDOHUY, "+
		"	CASE DDH.ISKM WHEN 1 THEN N'Đơn hàng KM' WHEN 0 THEN '' END ISKM,CASE DDH.DOIHANG WHEN 1 THEN N'Đơn đổi hàng' ELSE '' END DONDOIHANG, "+
		"	CASE DDH.TRANGTHAI "+
		"		WHEN '0' THEN N'Npp chưa chốt' "+
		"		WHEN '1' THEN N'Npp chốt /chờ TT xử lý' "+
		"		WHEN '2' THEN N'TT đã xác nhận' "+
		"		WHEN '3' THEN N'Đã xuất HĐTT' "+
		"		WHEN '4' THEN N'Đang giao hàng' "+
		"		WHEN '5' THEN N'Hoàn tất' "+
		"		WHEN '6' THEN N'Đã hủy' END AS TRANGTHAI ,NPP.MA AS MANPP, NPP.TEN AS TENNPP, KV.TEN AS VUNG,V.TEN AS MIEN , GSBH.TEN AS TENGSBH,SP.MA AS MASP, SP.TEN AS TENSP,DVDL.DONVI, "+
		"	ISNULL( DH_SP.SOLUONG,0) AS SLDAT,ISNULL(DH_SP.SOLUONGDUYET,0) AS SLDUYET ,(DH_SP.DONGIA*DH_SP.SOLUONGDUYET) AS TONGTIEN,(DH_SP.DONGIA* ISNULL(DH_SP.SOLUONG,0)) AS TONGTIENDAT "+
		"FROM DONDATHANG DDH "+
		"	INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = DDH.NPP_FK "+
		"	LEFT JOIN KHUVUC KV ON KV.PK_SEQ = NPP.KHUVUC_FK "+
		"	LEFT JOIN VUNG V ON V.PK_SEQ=KV.VUNG_FK "+
		"	INNER JOIN DONDATHANG_SP DH_SP ON DH_SP.DONDATHANG_FK = DDH.PK_SEQ "+
		"	INNER JOIN SANPHAM SP ON SP.PK_SEQ = DH_SP.SANPHAM_FK "+
		"	LEFT JOIN GIAMSATBANHANG GSBH ON GSBH.PK_SEQ = DDH.GSBH_FK "+
		"	INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ=SP.DVDL_FK "+
		" WHERE DDH.NGAYDAT >='"+tungay+"' AND DDH.NGAYDAT <= '"+denngay+"' AND "+
		"(DH_SP.SOLUONG > '0' OR DH_SP.SOLUONGDUYET > '0') AND (DDH.TRANGTHAI >= '0') ";
		 
		if(st.length()>0)
			query= query + st;			   
		System.out.println("BC Don Dat Hang NPP : " + query);
		return query;
	}
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
			request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
			HttpSession session = request.getSession();
			Ireport obj = new Report();

		    Utility util = new Utility();
		    String querystring = request.getQueryString();
		    String userId = util.getUserId(querystring);			   
		    obj.setuserId(userId);
		    obj.init();	    
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			String nextJSP = "/AHF/pages/Center/BCDonDatHangNPP.jsp";
			response.sendRedirect(nextJSP);
		
	}

	
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		Ireport obj = new Report();
		Utility util = new Utility();
		
		String userId = (String) session.getAttribute("userId");
		if (userId == null)
			userId = "";
		obj.setuserId(userId);
		
		String userTen = (String) session.getAttribute("userTen");
		obj.setuserTen(userTen);			
		
		String nppId ="";		
		nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
		if (nppId == null)
			nppId = "";
		obj.setnppId(nppId);
		
		String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays")));
		if (tungay == null)
			tungay = "";
		obj.settungay(tungay);

		String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays")));
		if (denngay == null)
			denngay = "";
		obj.setdenngay(denngay);		
		
		String st = " and ddh.npp_fk in " + util .quyen_npp(obj.getuserId()) + " and ddh.kbh_fk in  " + util.quyen_kenh(obj.getuserId());
		
		if(nppId.length() > 0)
			st += " and ddh.npp_fk ='" + nppId + "'";		
		st += " and ddh.npp_fk in "+ util.quyen_npp(obj.getuserId()) ;

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if (action.equals("tao")) 
		{
			try 
			{
				request.setCharacterEncoding("utf-8");
			
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BCDonDatHangNPP" + this.getPiVotName() + ".xlsm");

				OutputStream out = response.getOutputStream();

				String query = setQuery(request, tungay, denngay, st);				
				CreatePivotTable(out, obj, query);
			} 
			catch (Exception ex) 
			{
				request.getSession().setAttribute("errors", ex.getMessage());
				System.out.println("EXCEPTION "+ex.getMessage());
			}
		}
		
	}

	private void CreatePivotTable(OutputStream out,Ireport obj, String query) throws Exception {
		try 
		{
				//FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BCDonDatHangNPP.xlsm");
				Workbook workbook = new Workbook();
				
				File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\BCDonDatHangNPP.xlsm");
				FileInputStream fstream = new FileInputStream(f) ;
				
				workbook.open(fstream);
				workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);							
		
			CreateHeader(workbook,obj); 
			FillData(workbook, query, obj);			
			workbook.save(out);
			fstream.close();
		} 
		catch (Exception ex) {
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}
	


	private void CreateHeader(Workbook workbook,Ireport obj) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);	    
	    worksheet.setName("Sheet1");
	    Cells cells = worksheet.getCells();	 
	    
	    cells.setRowHeight(0, 20.0f);	    
	    Cell cell = cells.getCell("A1");	
	    ReportAPI.getCellStyle(cell,Color.RED, true, 16, "Báo Cáo Đơn Đặt Hàng Nhà Phân Phối");
	    cell = cells.getCell("A2");
	    ReportAPI.getCellStyle(cell,Color.NAVY,true,10,"Từ ngày: " + obj.gettungay() + "  Đến ngày : " + obj.getdenngay());
	    cell = cells.getCell("A3");
	    ReportAPI.getCellStyle(cell,Color.NAVY,true,10,"Ngày tạo : " + this.getDateTime()); 
	    cell = cells.getCell("A4");
	    ReportAPI.getCellStyle(cell,Color.NAVY,true,10,"Người tạo : " + obj.getuserTen());
	    
	    
	    cell = cells.getCell("AA1");		cell.setValue("Ma DDH");							ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AB1");		cell.setValue("SO Id");								ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AC1");		cell.setValue("Ngay Dat");							ReportAPI.setCellHeader(cell);		
		cell = cells.getCell("AD1");		cell.setValue("Ngay De Nghi Giao Hang");			ReportAPI.setCellHeader(cell);						
		cell = cells.getCell("AE1");		cell.setValue("Trang Thai");						ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AF1");		cell.setValue("Ten NPP");							ReportAPI.setCellHeader(cell);	
		cell = cells.getCell("AG1");		cell.setValue("Vung");								ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AH1");		cell.setValue("Mien");								ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AI1");		cell.setValue("Ten GSBH");							ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AJ1");		cell.setValue("Ma san pham");						ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AK1");		cell.setValue("Ten san pham");						ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AL1");		cell.setValue("Don Vi");							ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AM1");		cell.setValue("So luong dat");						ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AN1");		cell.setValue("So luong duyet");					ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AO1");		cell.setValue("Tong tien");							ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AP1");		cell.setValue("TongTienDat");							ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AQ1");		cell.setValue("Ma NPP");							ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AR1");		cell.setValue("LyDoHuy");							ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AS1");		cell.setValue("ISKM");							ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AT1");		cell.setValue("Don Doi Hang");							ReportAPI.setCellHeader(cell);
	}
	
	
	
	private void FillData(Workbook workbook, String query, Ireport obj) throws Exception
	{
		ResultSet rs = null;
		dbutils db = new dbutils();
		try
		{	
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			Cells cells = worksheet.getCells();
			
			for(int i=0;i<4;++i)
			{
		    	cells.setColumnWidth(i, 15.0f);	    	
		    }	
			 rs = db.get(query);
			int index = 2;
		    Cell cell = null;	    
			while (rs.next())
			{
				cell = cells.getCell("AA" + String.valueOf(index));		cell.setValue(rs.getString("MaDDH"));			
				cell = cells.getCell("AB" + String.valueOf(index));		cell.setValue(rs.getString("SOId"));
				cell = cells.getCell("AC" + String.valueOf(index));		cell.setValue(rs.getString("NgayDat"));				
				cell = cells.getCell("AD" + String.valueOf(index));		cell.setValue(rs.getString("NgayDeNghiGiaoHang"));				
				cell = cells.getCell("AE" + String.valueOf(index));		cell.setValue(rs.getString("TrangThai"));							
				cell = cells.getCell("AF" + String.valueOf(index));		cell.setValue(rs.getString("TenNPP"));
				cell = cells.getCell("AG" + String.valueOf(index));		cell.setValue(rs.getString("Vung"));
				cell = cells.getCell("AH" + String.valueOf(index));		cell.setValue(rs.getString("Mien"));
				
				cell = cells.getCell("AI" + String.valueOf(index));		cell.setValue(rs.getString("TenGSBH"));
				cell = cells.getCell("AJ" + String.valueOf(index));		cell.setValue(rs.getString("MaSP"));
				cell = cells.getCell("AK" + String.valueOf(index));		cell.setValue(rs.getString("TenSP"));
				cell = cells.getCell("AL" + String.valueOf(index));		cell.setValue(rs.getString("DonVi"));
				cell = cells.getCell("AM" + String.valueOf(index));		cell.setValue(rs.getFloat("SlDat"));
				cell = cells.getCell("AN" + String.valueOf(index));		cell.setValue(rs.getFloat("SlDuyet"));				
				cell = cells.getCell("AO" + String.valueOf(index));		cell.setValue(rs.getDouble("TongTien"));  								
				cell = cells.getCell("AP" + String.valueOf(index));		cell.setValue(rs.getDouble("TongTienDat"));
				cell = cells.getCell("AQ" + String.valueOf(index));		cell.setValue(rs.getString("MaNPP"));
				cell = cells.getCell("AR" + String.valueOf(index));		cell.setValue(rs.getString("LyDoHuy"));
				cell = cells.getCell("AS" + String.valueOf(index));		cell.setValue(rs.getString("ISKM"));
				cell = cells.getCell("AT" + String.valueOf(index));		cell.setValue(rs.getString("DonDoiHang"));
				index++;
			}
			if(rs!=null){
				rs.close();
			}	
			
			ReportAPI.setHidden(workbook,20);
			 
		
		    			
		}
		catch(Exception ex)
		{
			System.out.println("Error Here : "+ex.toString());
			if(rs != null)
			{
				rs.close();
			}
			
			if(db != null)
				db.shutDown();
			
			throw new Exception(ex.getMessage());
		}
	}
		

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
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

}
