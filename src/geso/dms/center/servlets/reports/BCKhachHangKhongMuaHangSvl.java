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
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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


@WebServlet("/BCKhachHangKhongMuaHangSvl")
public class BCKhachHangKhongMuaHangSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public BCKhachHangKhongMuaHangSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

    String nextJSP="";
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset:UTF-8");
		HttpSession session = request.getSession();
		 String querystring = request.getQueryString();
		 Utility util =new Utility();
		 
		    String userId = util.getUserId(querystring);
		    //out.println(userId);
		
		Stockintransit obj = new Stockintransit();
		obj.setuserId(userId);
		obj.init();
		session.setAttribute("obj", obj);
		nextJSP="/AHF/pages/Center/BCKhachHangKhongMuaHang.jsp";
		response.sendRedirect(nextJSP);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset:UTF-8");
		HttpSession session = request.getSession();
		String userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
		String userTen = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userTen"));
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		Stockintransit obj = new Stockintransit();
		obj.setuserId(userId);
		obj.setuserTen(userTen);
		
		String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays"));
		obj.settungay(tungay);
		
		String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays"));
		obj.setdenngay(denngay);
		
		String nppid = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhapp"));
		if(nppid==null) nppid = "";
		obj.setnppId(nppid);
		
		String vungId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"));
		if(vungId == null) vungId = "";
		obj.setvungId(vungId);
		
		String khuvucId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"));
		if(khuvucId == null) khuvucId = "";
		obj.setkhuvucId(khuvucId);
		
		if(action.equals("excel"))
		{
			try{
			response.setContentType("application/xlsm");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Content-Disposition", "attachment; filename=BCKhachHangKhongMua.xlsm");
			
			setQuery(obj);
			OutputStream out = response.getOutputStream();
			ExportToExcel(out,obj);
			session.setAttribute("obj", obj);
			String nextJSP = "/AHF/pages/Center/BCKhachHangKhongMuaHang.jsp";
			response.sendRedirect(nextJSP);
			}
			catch(Exception e)
			{
				obj.setMsg("Không thể xuất excel");
				session.setAttribute("obj", obj);
				String nextJSP = "/AHF/pages/Center/BCKhachHangKhongMuaHang.jsp";
				response.sendRedirect(nextJSP);
			}
		}
		else
		{
			obj.init();
			session.setAttribute("obj", obj);
			String nextJSP = "/AHF/pages/Center/BCKhachHangKhongMuaHang.jsp";
			response.sendRedirect(nextJSP);
		}
		
	}
	String query="";
	public void setQuery(Stockintransit obj)
	{
		query= " select kh.PK_SEQ AS khId,kh.TEN AS tenKH, ISNULL(kh.DIACHI,N'Chưa xác định') AS dcKH, ISNULL(kh.Phuong,N'Chưa xác định') AS phuong, " +
			   " ISNULL(kh.CHUCUAHIEU,N'Chưa xác định') AS chucuahieu, ISNULL(kh.NGUOIDAIDIEN,N'Chưa xác định') AS nguoidaidien, " +
			   " kh.SMARTID AS smartid, ISNULL(th.TEN,N'Chưa xác định') AS tinhthanh, ISNULL(qh.TEN,N'Chưa xác định') AS quanhuyen, kbh.TEN AS kenhbanhang, " +
			   " ISNULL(vtch.DIENGIAI,N'Chưa xác định') AS vitricuahang, " +
			   " ISNULL(lch.DIENGIAI,N'Chưa xác định') AS loaicuahang, ISNULL(hch.DIENGIAI,N'Chưa xác định') AS hangcuahang,  (case kh.TRANGTHAI when '0' then N'Ngưng hoạt động' else N'Hoạt động' end) AS trangthai, " +
			   " npp.TEN AS nhaphanphoi " +
			   " from " +
			   " ( " +
			   " 	select kh.PK_SEQ,kh.TEN,kh.DIACHI,kh.Phuong,kh.CHUCUAHIEU,kh.NGUOIDAIDIEN,kh.SMARTID,kh.TINHTHANH_FK,kh.QUANHUYEN_FK,kh.KBH_FK,kh.VTCH_FK,kh.HCH_FK,kh.TRANGTHAI,kh.NPP_FK,kh.LCH_FK " +
			   " 	from KHACHHANG kh where  not exists (select KHACHHANG_FK from " +
			   " 	donhang dh where khachhang_fk=kh.pk_seq and dh.ngaynhap >='"+obj.gettungay()+"' and dh.trangthai <>2 and dh.ngaynhap <='"+obj.getdenngay()+"' ) " +
			   ") as kh " +
			   " LEFT JOIN TINHTHANH th ON kh.TINHTHANH_FK = th.PK_SEQ " +
			   " LEFT JOIN QUANHUYEN qh ON kh.QUANHUYEN_FK = qh.PK_SEQ " +
			   " LEFT JOIN KENHBANHANG kbh ON kh.KBH_FK = kbh.PK_SEQ " +
			   " LEFT JOIN VITRICUAHANG vtch ON kh.VTCH_FK = vtch.PK_SEQ " +
			   " LEFT JOIN LOAICUAHANG lch ON kh.LCH_FK = lch.PK_SEQ " +
			   " LEFT JOIN HANGCUAHANG hch ON kh.HCH_FK = hch.PK_SEQ " +
			   " LEFT JOIN NHAPHANPHOI npp ON kh.NPP_FK = npp.PK_SEQ " ;
			   if(obj.getnppId().length()>0) 
			   {
				   query+= " where kh.NPP_FK='" + obj.getnppId() + "' ";
			   }
			   
			    query += " order by npp.TEN " ;
		System.out.println("Khách hàng không mua hàng : "+query);
	}
	
	public String getQuery()
	{
		return this.query;
	}
	private void ExportToExcel(OutputStream out,IStockintransit obj)throws Exception
	{
		try
		{ 

			String chuoi=getServletContext().getInitParameter("path") + "\\BCKhachHangKhongMua.xlsm";
			
			FileInputStream fstream = new FileInputStream(chuoi);
			//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\BCKhachHangKhongMua.xlsm");
			//FileInputStream fstream = new FileInputStream(f) ;
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			//System.out.println("2/den day: " + chuoi);
			CreateHeader(workbook,obj);
			
			FillData(workbook,obj);
			
			workbook.save(out);	
			fstream.close();
		}
		catch(Exception ex)
		{
			throw new Exception(ex.getMessage());
		}
	}
	
	private void CreateHeader(Workbook workbook,IStockintransit obj) throws Exception 
	{
		try 
		{
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");
			
			Cells cells = worksheet.getCells();
			cells.setRowHeight(0, 50.0f);
			Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 16,
					"DANH SÁCH KHÁCH HÀNG KHÔNG MUA");
			cell = cells.getCell("A2");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10,
					"Từ ngày : " + obj.gettungay() + " Tới ngày: " + obj.getdenngay());
			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Ngày tạo : "
					+ obj.getDateTime());
			cell = cells.getCell("A4");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10,
					"Người tạo : " + obj.getuserTen());
			
			cell = cells.getCell("AA1");		cell.setValue("khId");
			cell = cells.getCell("AB1");		cell.setValue("TenKH");
			cell = cells.getCell("AC1");		cell.setValue("DiaChiKH");
			cell = cells.getCell("AD1");		cell.setValue("Phuong");
			cell = cells.getCell("AE1");		cell.setValue("ChuCuaHieu");
			cell = cells.getCell("AF1");		cell.setValue("NguoiDaiDien");
			cell = cells.getCell("AG1");		cell.setValue("MaKhachHang");
			cell = cells.getCell("AH1");		cell.setValue("TinhThanh");
			cell = cells.getCell("AI1");		cell.setValue("QuanHuyen");
			cell = cells.getCell("AJ1");		cell.setValue("KenhBanHang");
			cell = cells.getCell("AK1");		cell.setValue("ViTriCuaHang");
			cell = cells.getCell("AL1");		cell.setValue("LoaiCuaHang");
			cell = cells.getCell("AM1");		cell.setValue("HangCuaHang");
			cell = cells.getCell("AN1");		cell.setValue("TrangThai");
			cell = cells.getCell("AO1");		cell.setValue("NhaPhanPhoi");
		} catch (Exception ex) {
			System.out.println(ex.toString());
			throw new Exception("Khong the tao duoc Header cho bao cao.!!!");
		}
	}
	private void FillData(Workbook workbook,IStockintransit obj)throws Exception{
		try{
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setGridlinesVisible(false);
			Cells cells = worksheet.getCells();
			dbutils db = new dbutils();
			ResultSet rs = db.get(getQuery());
			Cell cell = null;
			int countRow = 2;
			while(rs.next()){
				cell = cells.getCell("AA" + String.valueOf(countRow));		cell.setValue(rs.getString("khId"));
				cell = cells.getCell("AB" + String.valueOf(countRow));		cell.setValue(rs.getString("tenKH"));
				cell = cells.getCell("AC" + String.valueOf(countRow));		cell.setValue(rs.getString("dcKH"));
				cell = cells.getCell("AD" + String.valueOf(countRow));		cell.setValue(rs.getString("phuong"));
				cell = cells.getCell("AE" + String.valueOf(countRow));		cell.setValue(rs.getString("chucuahieu"));
				cell = cells.getCell("AF" + String.valueOf(countRow));		cell.setValue(rs.getString("nguoidaidien"));
				cell = cells.getCell("AG" + String.valueOf(countRow));		cell.setValue(rs.getString("smartid"));
				cell = cells.getCell("AH" + String.valueOf(countRow));		cell.setValue(rs.getString("tinhthanh"));
				cell = cells.getCell("AI" + String.valueOf(countRow));		cell.setValue(rs.getString("quanhuyen"));
				cell = cells.getCell("AJ" + String.valueOf(countRow));		cell.setValue(rs.getString("kenhbanhang"));
				cell = cells.getCell("AK" + String.valueOf(countRow));		cell.setValue(rs.getString("vitricuahang"));
				cell = cells.getCell("AL" + String.valueOf(countRow));		cell.setValue(rs.getString("loaicuahang"));
				cell = cells.getCell("AM" + String.valueOf(countRow));		cell.setValue(rs.getString("hangcuahang"));
				cell = cells.getCell("AN" + String.valueOf(countRow));		cell.setValue(rs.getString("trangthai"));
				cell = cells.getCell("AO" + String.valueOf(countRow));		cell.setValue(rs.getString("nhaphanphoi"));
				
				++countRow;
			}
			if(rs!=null)rs.close();
			if(db!=null){
				db.shutDown();
			}
			//ReportAPI.setHidden(workbook,20);
	
		}catch(Exception ex){
			
			System.out.println("Errrorr : "+ex.toString());
			throw new Exception("Qua trinh dien du lieu vao file Excel va tao PivotTable bi loi.!!!");
		}
	}
}
