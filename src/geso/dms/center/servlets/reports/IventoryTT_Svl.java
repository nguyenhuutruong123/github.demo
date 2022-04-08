package geso.dms.center.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.OracleConnUtils;
import geso.dms.distributor.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.record.formula.functions.Npv;

import com.aspose.cells.BorderLineType;
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
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;



public class IventoryTT_Svl extends HttpServlet 
{	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public IventoryTT_Svl() 
	{
		super();
	}

	private String gettenuser(String userId_)
	{
		dbutils db=new dbutils();
		String sql_getnam="select ten from nhanvien where pk_seq = "+ userId_;
		ResultSet rs_tenuser=db.get(sql_getnam);
		if(rs_tenuser!= null){
			try{
				while(rs_tenuser.next()){
					return rs_tenuser.getString("ten");
				}
			}catch(Exception er){
				return "";
			}finally{
				try{
					if(rs_tenuser != null) rs_tenuser.close();
					if(db != null) db.shutDown();

				}catch(Exception e){

				}
			}
		}
		return "";
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		Utility util = new Utility();
		HttpSession session = request.getSession();
		String userTen = (String) session.getAttribute("userTen");
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);


		IStockintransit obj = new Stockintransit();	
		obj.settungay("");
		obj.setdenngay("");
		obj.setMsg("");
		obj.setnppId("");
		obj.setuserId(userId);
		obj.init();

		session.setAttribute("obj", obj);
		session.setAttribute("userTen", userTen);
		String nextJSP = "/AHF/pages/Center/InventoryTT.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("aaaaaaaaaaaaa");
		OutputStream out = response.getOutputStream(); 
		IStockintransit obj = new Stockintransit();	
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String nextJSP = "/AHF/pages/Center/InventoryTT.jsp";
		Utility util = new Utility();
		try {
			
			obj.setuserTen((String) session.getAttribute("userTen")!=null?
					(String) session.getAttribute("userTen"):"");

			obj.settungay(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"))!=null?
					geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")):"");

			obj.setuserId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"))!=null?
					geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")):"");

			obj.setnppId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"))!=null?
					geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")):"");

			obj.setkenhId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId"))!=null?
					geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")):"");

			obj.setdvkdId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId"))!=null?
					geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")):"");

			obj.setvungId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"))!=null?
					geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")):"");
			
			obj.setTrangthaispId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthaispId"))!=null?
					geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthaispId")):"");

			obj.setnhanhangId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhanhangId"))!=null?
					geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhanhangId")):"");

			obj.setkhuvucId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"))!=null?
					geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")):"");

			obj.setchungloaiId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chungloaiId"))!=null?
					geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chungloaiId")):"");

			String giatinh = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("giatinh")); // 1. Giá mua NPP, 2. Giá bán NPP
			
			String avalible = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("piece"));
			String condition = "";
			if(avalible.equals("1")){
				condition += " and tkn.AVAILABLE > 0";
			}

			String action = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));
			if(action.equals("search"))
			{

				obj.init();
				session.setAttribute("obj", obj);
				response.sendRedirect(nextJSP);
			}
			if(action.equals("excel"))
			{
				//response.setContentType("application/vnd.ms-excel");
				//response.setHeader("Content-Disposition", "attachment; filename=TonKhoHienTai.xls");
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=TonHienTai.xlsm");

				boolean isTrue = CreatePivotTable(out, obj, condition, giatinh);
				if(!isTrue)
				{
					PrintWriter writer = new PrintWriter(out);
					writer.write("Khong tao duoc bao cao trong thoi gian nay...!!!");
				}	
			}else if(action.equals("chitiet"))
			{
				//response.setContentType("application/vnd.ms-excel");
				//response.setHeader("Content-Disposition", "attachment; filename=TonKhoHienTai.xls");
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=TonHienTai.xlsm");

				boolean isTrue = CreatePivotTableCT(out, obj, condition, giatinh);
				if(!isTrue)
				{
					PrintWriter writer = new PrintWriter(out);
					writer.write("Khong tao duoc bao cao trong thoi gian nay...!!!");
				}	
			}else
				if(action.equals("excel2"))
				{
					//response.setContentType("application/vnd.ms-excel");
					//response.setHeader("Content-Disposition", "attachment; filename=TonKhoHienTai.xls");
					response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition", "attachment; filename=TonHienTaiIP.xlsm");

					boolean isTrue = CreatePivotTable2(out, obj, condition, giatinh);
					if(!isTrue)
					{
						PrintWriter writer = new PrintWriter(out);
						writer.write("Khong tao duoc bao cao trong thoi gian nay...!!!");
					}	
				}
			
		} catch (Exception ex) {
			obj.setMsg(ex.getMessage());
			ex.printStackTrace();
			response.sendRedirect(nextJSP);
		}
	}

	private boolean CreatePivotTable(OutputStream out,IStockintransit obj, String condition, String giatinh) throws Exception
	{   
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();		

		String fstreamstr = getServletContext().getInitParameter("path") + "\\TonHienTaiTT.xlsm";
		fstream = new FileInputStream(fstreamstr);
		//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\TonHienTaiTT.xlsm");
		//FileInputStream fstream = new FileInputStream(f) ;
		//fstream = new FileInputStream("D:\\Project\\ICP\\Best\\WebContent\\pages\\Templates\\TonHienTaiTT.xlsm");
		//fstream = new FileInputStream("C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\Best\\pages\\Templates\\TonHienTaiTT.xlsm");		

		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateStaticHeader(workbook, obj.gettungay(),obj.getdenngay(), obj.getuserTen(), giatinh);	     
		boolean isTrue = CreateStaticData(workbook, obj, condition, giatinh);
		if(!isTrue){
			return false;
		}
		workbook.save(out);

		fstream.close();
		return true;
	}
	
	private boolean CreatePivotTableCT(OutputStream out,IStockintransit obj, String condition, String giatinh) throws Exception
	{   
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();		

		String fstreamstr = getServletContext().getInitParameter("path") + "\\TonHienTaiTTCT.xlsm";
		fstream = new FileInputStream(fstreamstr);
		//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\TonHienTaiTT.xlsm");
		//FileInputStream fstream = new FileInputStream(f) ;
		//fstream = new FileInputStream("D:\\Project\\ICP\\Best\\WebContent\\pages\\Templates\\TonHienTaiTT.xlsm");
		//fstream = new FileInputStream("C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\Best\\pages\\Templates\\TonHienTaiTT.xlsm");		

		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateStaticHeaderCT(workbook, obj.gettungay(),obj.getdenngay(), obj.getuserTen(), giatinh);	     
		boolean isTrue = CreateStaticDataCT(workbook, obj, condition, giatinh);
		if(!isTrue){
			return false;
		}
		workbook.save(out);

		fstream.close();
		return true;
	}
	private boolean CreatePivotTable2(OutputStream out,IStockintransit obj, String condition, String giatinh) throws Exception
	{   
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();		

		
		//fstream = new FileInputStream("D:\\Project\\ICP\\Best\\WebContent\\pages\\Templates\\TonHienTaiTT.xlsm");
		//fstream = new FileInputStream("C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\Best\\pages\\Templates\\TonHienTaiTT.xlsm");		

		
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateStaticHeader2(workbook, obj.gettungay(),obj.getdenngay(), obj.getuserTen(), giatinh);	     
		boolean isTrue = CreateStaticData2(workbook, obj, condition, giatinh);
		if(!isTrue){
			return false;
		}
		workbook.save(out);


		return true;
	}

	private void CreateStaticHeader(Workbook workbook, String dateFrom, String dateTo, String UserName, String giatinh) 
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

		String tieude = "BÁO CÁO TỒN KHO HIỆN TẠI";
		ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);

		cells.setRowHeight(4, 18.0f);
		cell = cells.getCell("A3");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));

		cells.setRowHeight(5, 18.0f);
		cell = cells.getCell("A4");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + UserName);


		//tieu de, hang dau tien cua bang du lieu, cell la toa do cua exel
		cell = cells.getCell("AA1"); 	cell.setValue("KenhBanHang");  ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AB1"); 	cell.setValue("DonViKinhDoanh");  ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AC1");	cell.setValue("ChiNhanh");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AD1");	cell.setValue("KhuVuc");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AE1");	cell.setValue("MaNhaPhanPhoi");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AF1");	cell.setValue("NhaPhanPhoi");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AG1"); 	cell.setValue("NhanHang");  ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AH1"); 	cell.setValue("ChungLoai");  ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AI1"); 	cell.setValue("MaSanPham");  ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AJ1"); 	cell.setValue("TenSanPham"); ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AK1"); 	cell.setValue("Kho");   ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AL1"); 	cell.setValue("SoLuongLe");	   ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AM1"); 	cell.setValue("SoLuongThung");  ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AN1"); 	cell.setValue("SoTien");  ReportAPI.setCellHeader(cell);
		//cell = cells.getCell("AO1"); 	cell.setValue("SanLuong(Kg)");  ReportAPI.setCellHeader(cell);		
		cell = cells.getCell("AO1"); 	cell.setValue("Don Gia");  ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AP1"); 	cell.setValue("TenSanPham(VietTat)");  ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AQ1"); 	cell.setValue("Booked");  ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AR1"); 	cell.setValue("NVBH");  ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AS1"); 	cell.setValue("TrangthaiDMS");  ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AT1"); 	cell.setValue("TonTamTinh");  ReportAPI.setCellHeader(cell);
	}
	
	private void CreateStaticHeaderCT(Workbook workbook, String dateFrom, String dateTo, String UserName, String giatinh) 
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

		String tieude = "BÁO CÁO TỒN KHO HIỆN TẠI";
		ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);

		cells.setRowHeight(4, 18.0f);
		cell = cells.getCell("A3");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));

		cells.setRowHeight(5, 18.0f);
		cell = cells.getCell("A4");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + UserName);


		//tieu de, hang dau tien cua bang du lieu, cell la toa do cua exel
		cell = cells.getCell("AA1"); 	cell.setValue("KenhBanHang");  ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AB1"); 	cell.setValue("DonViKinhDoanh");  ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AC1");	cell.setValue("ChiNhanh");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AD1");	cell.setValue("KhuVuc");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AE1");	cell.setValue("MaNhaPhanPhoi");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AF1");	cell.setValue("NhaPhanPhoi");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AG1"); 	cell.setValue("NhanHang");  ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AH1"); 	cell.setValue("ChungLoai");  ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AI1"); 	cell.setValue("MaSanPham");  ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AJ1"); 	cell.setValue("TenSanPham"); ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AK1"); 	cell.setValue("Kho");   ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AL1"); 	cell.setValue("SoLuongLe");	   ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AM1"); 	cell.setValue("SoLuongThung");  ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AN1"); 	cell.setValue("SoTien");  ReportAPI.setCellHeader(cell);
		//cell = cells.getCell("AO1"); 	cell.setValue("SanLuong(Kg)");  ReportAPI.setCellHeader(cell);		
		cell = cells.getCell("AO1"); 	cell.setValue("Don Gia");  ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AP1"); 	cell.setValue("TenSanPham(VietTat)");  ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AQ1"); 	cell.setValue("Booked");  ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AR1"); 	cell.setValue("NVBH");  ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AS1"); 	cell.setValue("TrangthaiDMS");  ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AT1"); 	cell.setValue("NgayNhapKho");  ReportAPI.setCellHeader(cell);
	}
	
	private void CreateStaticHeader2(Workbook workbook, String dateFrom, String dateTo, String UserName, String giatinh) 
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

		String tieude = "BÁO CÁO TỒN KHO HIỆN TẠI DVKD IP (ERP)";
		ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);

		cells.setRowHeight(4, 18.0f);
		cell = cells.getCell("A3");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " +getDateTime()+"");

		cells.setRowHeight(5, 18.0f);
		cell = cells.getCell("A4");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + UserName);


	
	}
	private boolean CreateStaticData(Workbook workbook, IStockintransit obj, String condition, String giatinh) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();

		geso.dms.center.util.Utility Uti_center = new geso.dms.center.util.Utility();

		String sql = "";
		if (obj.getkenhId().length() > 0)
			sql = sql + " and kbh.pk_seq ='" + obj.getkenhId() + "'";
		if (obj.getvungId().length() > 0)
			sql = sql + " and v.pk_seq ='" + obj.getvungId() + "'";
		if (obj.getkhuvucId().length() > 0)
			sql = sql + " and kv.pk_seq ='" + obj.getkhuvucId() + "'";
		if (obj.getdvkdId().length() > 0)
			sql = sql + " and dvkd.PK_SEQ ='" + obj.getdvkdId() + "'";
		if (obj.getnhanhangId().length() > 0)
			sql = sql + " and nh.pk_seq ='" + obj.getnhanhangId() + "'";
		if (obj.getchungloaiId().length() > 0)
			sql = sql + " and cl.pk_seq ='" + obj.getchungloaiId() + "'";
		if (obj.getTrangthaispId().length() > 0)
			sql = sql + " and sp.trangthai ='" + obj.getTrangthaispId() + "'";

		sql = sql + condition;
		
		String sub_query = "\n with temp_ttt as " +
		"\n ( " +
		"\n 	     select ngaynhapkho,NPP_FK, " +
		"\n              case when isnull(npp.DUNGCHUNGKENH,0) = 1 then 100025 else data.KBH_FK end as kbh_fk, " +
		"\n              kho_Fk,SANPHAM_FK,null solo,null ngayhethan,SUM(soluong) as BOOKED_SQL " +
		"\n 	     from " +
		"\n 	     ( " +
		"\n 	         select b.ngaynhapkho ,a.NPP_FK,a.KBH_FK,ctkm.KHO_FK as kho_Fk, b.sanpham_fk,null solo, " +
		"\n 	             SUM(cast (b.soluong as float)) as soluong ,N'DONHANG_KHUYENMAI' as Type,null ngayhethan " +
		"\n 	         from donhang a inner join DONHANG_CTKM_TRAKM_CHITIET b on a.pk_seq = b.donhangId " +
		"\n 	         INNER JOIN CTKHUYENMAI CTKM ON CTKM.PK_SEQ=b.CTKMID " +
		"\n 	         where a.TRANGTHAI = 0 " +
		"\n 	         group by b.ngaynhapkho, ctkm.KHO_FK, a.npp_fk, b.sanpham_fk, a.KBH_FK " +
		"\n 	         UNION ALL " +
		"\n 	         select b.ngaynhapkho,a.NPP_FK,a.KBH_FK,a.kho_fk as kho_Fk, b.sanpham_fk,null solo, " +
		"\n                  SUM(cast (b.soluong as float)) as soluong, N'donhang' as Type,null ngayhethan " +
		"\n 	         from donhang a inner join donhang_sanpham_chitiet b on a.pk_seq = b.donhang_fk " +
		"\n          where a.trangthai = 0 " +
		"\n 	         group by b.ngaynhapkho ,a.kho_fk, a.npp_fk, b.sanpham_fk,a.KBH_FK " +
		"\n 	    )as data " +
		"\n     inner join NHAPHANPHOI npp on npp.PK_SEQ=data.NPP_FK " +
		"\n     group by data.NGAYNHAPKHO,NPP_FK,kho_Fk,SANPHAM_FK,solo,ngayhethan, " +
		"\n         case when isnull(npp.DUNGCHUNGKENH,0) = 1 then 100025 else data.KBH_FK end " +
		"\n ) ";

		String query = sub_query + "\n select kbh.ten as Channel, sp.ma as Sku_code, isnull(sp.MA_DDT,'') as MADDT, " +
		"\n sp.ten as SKU, sp.tenviettat as tentatsp, tkn.AVAILABLE as Piece,k.ten  as Warehouse, " +
		"\n V.PK_SEQ AS VUNGID, V.TEN AS VUNGTEN, KV.PK_SEQ AS KVID, KV.TEN AS KVTEN, " +
		"\n NPP.PK_SEQ AS NPPID, NPP.TEN AS NPPTEN, " +
		"\n nh.ten as Brans,round (isnull(tkn.AVAILABLE,0)/qc.soluong1, 2) as Quantily, " +
		"\n dvkd.donvikinhdoanh as Business_unit,cl.ten as Category,tkn.booked, isnull( ttt.BOOKED_SQL,0) TonTamTinh ";
		
		if(giatinh.equals("1"))
		{
			query += "\n ,gm.dongia, gm.dongia * tkn.available as Amount, ";
		}else
		{
			query += "\n ,gb.dongia ,gb.dongia * tkn.available as Amount, ";
		}
		
		query += "\n case when tkn.AVAILABLE * isnull(sp.trongluong,0) is null then 0 else tkn.AVAILABLE * isnull(sp.trongluong,0) end as SanLuong, " +
		"\n '' as NVBH, case when npp.trangthai = 1 then N'Hoạt động' else N'Không hoạt động' end trangthaidms " +
		"\n from " +
		"\n ( " +
		"\n     select * from nhapp_kho" +
		"\n     where npp_fk in " + Uti_center.quyen_npp(obj.getuserId()) +"" + 
		"\n     and sanpham_fk in " + Uti_center.quyen_sanpham(obj.getuserId()) +" " +
		"\n     and kbh_fk in "+ Uti_center.quyen_kenh(obj.getuserId()) ;
		if(obj.getnppId().length() > 0)
			query = query + "\n     and npp_fk = '" + obj.getnppId()+"'";

		query = query + "\n ) tkn "+
		"\n inner join kenhbanhang kbh on kbh.pk_seq = tkn.kbh_fk "+
		"\n inner join sanpham sp on sp.pk_seq = tkn.sanpham_fk "+
		"\n inner join donvikinhdoanh dvkd on dvkd.pk_seq = sp.dvkd_fk "+
		"\n inner join kho k on k.pk_seq = tkn.kho_fk "+
		"\n left join donvidoluong dvdl on dvdl.pk_seq = sp.dvdl_fk " +
		"\n left join QUYCACH qc on qc.SANPHAM_FK = sp.PK_SEQ and qc.DVDL1_FK = dvdl.PK_SEQ and qc.DVDL2_FK ='100018' " +
		"\n left join chungloai cl on cl.pk_seq = sp.chungloai_fk " +
		"\n left join nhanhang nh on nh.pk_seq = sp.nhanhang_fk " +
		"\n inner join nhaphanphoi npp on npp.pk_seq = tkn.npp_fk " +
		"\n left join khuvuc kv on npp.khuvuc_fk = kv.pk_seq  " +
		"\n left join vung v on kv.vung_fk = v.pk_seq " + 
		"\n --outer apply [dbo].[ufn_TonTamTinh_ChiTiet_Full_Input](tkn.npp_fk,tkn.kbh_fk,tkn.kho_fk,tkn.sanpham_fk) ttt " +
		"\n outer apply (select [dbo].[GiaMuaNppSanPham](tkn.kbh_fk,tkn.npp_fk,tkn.sanpham_fk,dbo.getNgayHienTai()) dongia) gm " ;
		if(giatinh.equals("0")){
			query+=	"\n outer apply (select [dbo].[GiaBanLeNppSanPham_Kbh](null,tkn.kbh_fk,tkn.npp_fk,tkn.sanpham_fk,dbo.getNgayHienTai(),npp.KHUVUC_FK) dongia) gb " ;

		}else{
			query+="\n outer apply (select [dbo].[GiaBanLeNppSanPham](null,tkn.kbh_fk,tkn.npp_fk,tkn.sanpham_fk,dbo.getNgayHienTai()) dongia) gb " ; 
		}
		query+="\n outer apply " + 
		"\n ( " +
		"\n     select isnull(BOOKED_SQL,0) BOOKED_SQL " +
		"\n	    from temp_ttt where npp_fk = tkn.npp_fk and kbh_fk = tkn.kbh_fk and kho_fk = tkn.kho_fk and sanpham_fk = tkn.sanpham_fk " +
		"\n ) ttt " +
		"\n where 1 = 1 " + sql;

		System.out.println("Ton hien tai: " + query);
		ResultSet rs = db.get(query);

		int i = 2;
		if(rs!=null)
		{
			try 
			{// se do rong cho cac cot se dung
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

				Cell cell = null;
				while(rs.next())// lap den cuoi bang du lieu
				{

					//lay tu co so du lieu, gan bien
					String Channel = rs.getString("Channel");
					String SKU =rs.getString("SKU");
					String Piece =rs.getString("Piece");			

					String Warehouse = rs.getString("Warehouse");
					String SkuCode = rs.getString("SKU_code");		
					double Quantily = rs.getDouble("Quantily");
					String BusinessUnit = rs.getString("Business_unit");
					String Category = rs.getString("Category");
					String Brands = rs.getString("Brans");
					double Amount = rs.getDouble("Amount");
					//System.out.println("Amount la: " + Amount + "\n");

					cell = cells.getCell("AA" + Integer.toString(i)); 	cell.setValue(Channel);
					cell = cells.getCell("AB" + Integer.toString(i)); 	cell.setValue(BusinessUnit);
					cell = cells.getCell("AC" + Integer.toString(i));	cell.setValue(rs.getString("VUNGTEN"));
					cell = cells.getCell("AD" + Integer.toString(i));	cell.setValue(rs.getString("KVTEN"));
					cell = cells.getCell("AE" + Integer.toString(i));	cell.setValue(rs.getString("NPPID"));
					cell = cells.getCell("AF" + Integer.toString(i));	cell.setValue(rs.getString("NPPTEN"));
					cell = cells.getCell("AG" + Integer.toString(i)); 	cell.setValue(Brands);
					cell = cells.getCell("AH" + Integer.toString(i)); 	cell.setValue(Category);
					cell = cells.getCell("AI" + Integer.toString(i)); 	cell.setValue(SkuCode);
					cell = cells.getCell("AJ" + Integer.toString(i)); 	cell.setValue(SKU);
					cell = cells.getCell("AK" + Integer.toString(i)); 	cell.setValue(Warehouse);				
					cell = cells.getCell("AL" + Integer.toString(i)); 	cell.setValue(Float.parseFloat(Piece));
					cell = cells.getCell("AM" + Integer.toString(i)); 	cell.setValue(Quantily);
					cell = cells.getCell("AN" + Integer.toString(i)); 	cell.setValue(Amount); 
					cell = cells.getCell("AO" + Integer.toString(i)); 	cell.setValue(rs.getDouble("dongia")); 
					cell = cells.getCell("AP" + Integer.toString(i)); 	cell.setValue(rs.getString("tentatsp"));
					cell = cells.getCell("AQ" + Integer.toString(i)); 	cell.setValue(rs.getDouble("booked"));
					cell = cells.getCell("AR" + Integer.toString(i)); 	cell.setValue(rs.getString("NVBH"));
					cell = cells.getCell("AS" + Integer.toString(i)); 	cell.setValue(rs.getString("trangthaidms"));
					cell = cells.getCell("AT" + Integer.toString(i)); 	cell.setValue(rs.getDouble("TonTamTinh"));
					i++;
					/*if(i > 65000)
					{						
						if(rs!=null) rs.close();
						if(db != null) 
							db.shutDown();
						throw new Exception("Xin loi.Du lieu vuot qua 65.000 dong. Vui long chon dieu kien lay bao cao");
					}*/
				}
				if(rs!=null)
					rs.close();
				if(db != null) 
					db.shutDown();
				if(i==2){
					throw new Exception("Khong co bao cao trong thoi gian nay...");
				}
				/*
				getAn(workbook,49);
				PivotTables pivotTables = worksheet.getPivotTables();

			    //Adding a PivotTable to the worksheet
				String pos = Integer.toString(i-1);	
			    int index = pivotTables.add("=AA1:AN" + pos,"A9","PivotTableDemo");

			    PivotTable pivotTable = pivotTables.get(index);//truyen index

			    pivotTable.setRowGrand(false);
			    pivotTable.setColumnGrand(true);
			    pivotTable.setAutoFormat(true);

			    pivotTable.setAutoFormatType(PivotTableAutoFormatType.TABLE10);

			    pivotTable.addFieldToArea(PivotFieldType.ROW, 0);	pivotTable.getRowFields().get(0).setAutoSubtotals(true);  
			    pivotTable.addFieldToArea(PivotFieldType.ROW, 1);	pivotTable.getRowFields().get(1).setAutoSubtotals(true);
			    pivotTable.addFieldToArea(PivotFieldType.ROW, 2);	pivotTable.getRowFields().get(2).setAutoSubtotals(true);
			    pivotTable.addFieldToArea(PivotFieldType.ROW, 3);	pivotTable.getRowFields().get(3).setAutoSubtotals(true);  
			    pivotTable.addFieldToArea(PivotFieldType.ROW, 5);	pivotTable.getRowFields().get(4).setAutoSubtotals(true);
			    pivotTable.addFieldToArea(PivotFieldType.ROW, 8);	pivotTable.getRowFields().get(5).setAutoSubtotals(false);
			    pivotTable.addFieldToArea(PivotFieldType.ROW, 9);	pivotTable.getRowFields().get(6).setAutoSubtotals(false);

			    pivotTable.addFieldToArea(PivotFieldType.COLUMN, 10); //kho

			    pivotTable.addFieldToArea(PivotFieldType.DATA, 11);	
			    pivotTable.getDataFields().get(0).setDisplayName("SoLuongLe");
			    pivotTable.getDataFields().get(0).setNumber(3);

			    pivotTable.addFieldToArea(PivotFieldType.DATA, 13);	
			    pivotTable.getDataFields().get(1).setDisplayName("SoTien");
			    pivotTable.getDataFields().get(1).setNumber(3);

			    pivotTable.addFieldToArea(PivotFieldType.COLUMN, pivotTable.getDataField());*/

			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(e.getMessage());
			}
		} else {
			if(db != null) db.shutDown();
			return false;
		}

		if(db != null) db.shutDown();
		return true;

	}
	
	
	
	private boolean CreateStaticDataCT(Workbook workbook, IStockintransit obj, String condition, String giatinh) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();

		geso.dms.center.util.Utility Uti_center = new geso.dms.center.util.Utility();

		String sql = "";
		if (obj.getkenhId().length() > 0)
			sql = sql + " and kbh.pk_seq ='" + obj.getkenhId() + "'";
		if (obj.getvungId().length() > 0)
			sql = sql + " and v.pk_seq ='" + obj.getvungId() + "'";
		if (obj.getkhuvucId().length() > 0)
			sql = sql + " and kv.pk_seq ='" + obj.getkhuvucId() + "'";
		if (obj.getdvkdId().length() > 0)
			sql = sql + " and dvkd.PK_SEQ ='" + obj.getdvkdId() + "'";
		if (obj.getnhanhangId().length() > 0)
			sql = sql + " and nh.pk_seq ='" + obj.getnhanhangId() + "'";
		if (obj.getchungloaiId().length() > 0)
			sql = sql + " and cl.pk_seq ='" + obj.getchungloaiId() + "'";

		sql = sql + condition;

		String query = "select kbh.ten as Channel, sp.ma as Sku_code, isnull(sp.MA_DDT,'') as MADDT, sp.ten as SKU, sp.tenviettat as tentatsp, tkn.AVAILABLE as Piece,k.ten  as Warehouse, " +
				" V.PK_SEQ AS VUNGID, V.TEN AS VUNGTEN, KV.PK_SEQ AS KVID, KV.TEN AS KVTEN, " +
				" NPP.PK_SEQ AS NPPID, NPP.TEN AS NPPTEN, " +
				" nh.ten as Brans,round (isnull(tkn.AVAILABLE,0)/qc.soluong1, 2) as Quantily,"+
				" dvkd.donvikinhdoanh as Business_unit,cl.ten as Category,tkn.booked, ";
		
		if(giatinh.equals("1"))
		{
			query +=
				"\n	isnull( "+ 
				"\n	(  "+
				"\n		select top(1) bgmsp.giamuanpp  "+
				"\n		from banggiamuanpp  bgm inner join banggiamuanpp_npp bgmnpp on bgm.pk_seq=bgmnpp.banggiamuanpp_fk "+ 
				"\n			inner join bgmuanpp_sanpham bgmsp on bgmsp.bgmuanpp_fk=bgm.pk_seq "+ 
				"\n		where bgmsp.sanpham_fk=sp.pk_seq  "+
				"\n			and bgmnpp.npp_fk=tkn.npp_fk  "+
				"\n			and bgm.kenh_fk=tkn.kbh_fk and bgm.dvkd_fk = sp.dvkd_fk "+  
				"\n		order by bgm.tungay desc   "+
				"\n	),0) as dongia , "+
				"\n	isnull( "+ 
				"\n	(  "+
				"\n		select top(1) bgmsp.giamuanpp  "+
				"\n		from banggiamuanpp  bgm inner join banggiamuanpp_npp bgmnpp on bgm.pk_seq=bgmnpp.banggiamuanpp_fk "+ 
				"\n			inner join bgmuanpp_sanpham bgmsp on bgmsp.bgmuanpp_fk=bgm.pk_seq "+ 
				"\n		where bgmsp.sanpham_fk=sp.pk_seq  "+
				"\n			and bgmnpp.npp_fk=tkn.npp_fk  "+
				"\n			and bgm.kenh_fk=tkn.kbh_fk and bgm.dvkd_fk = sp.dvkd_fk "+  
				"\n		order by bgm.tungay desc   "+
				"\n	),0) * tkn.available as Amount, ";
		}else
		{
			query +=
				"\n	ISNULL((SELECT TOP 1 CAST(GIABANLECHUAN AS FLOAT)/1.1 AS GIABANLC "+ 
				"\n			 FROM BANGGIABANLECHUAN  A INNER JOIN BANGGIABLC_SANPHAM B ON A.PK_SEQ=B.BGBLC_FK "+ 
				"\n			                           INNER JOIN BANGGIABANLECHUAN_NPP C ON A.PK_SEQ = C.BANGGIABANLECHUAN_FK "+ 
				"\n			 WHERE C.NPP_FK = tkn.NPP_FK AND B.SANPHAM_FK = SP.PK_SEQ AND A.KENH_FK = tkn.KBH_FK  and A.dvkd_fk = sp.dvkd_fk   "+ 
				"\n			 ORDER BY A.TUNGAY DESC ),0) AS dongia, "+ 
				"\n	ISNULL((SELECT TOP 1 CAST(GIABANLECHUAN AS FLOAT)/1.1 AS GIABANLC "+ 
				"\n			 FROM BANGGIABANLECHUAN  A INNER JOIN BANGGIABLC_SANPHAM B ON A.PK_SEQ=B.BGBLC_FK "+ 
				"\n			                           INNER JOIN BANGGIABANLECHUAN_NPP C ON A.PK_SEQ = C.BANGGIABANLECHUAN_FK "+ 
				"\n			 WHERE C.NPP_FK = tkn.NPP_FK AND B.SANPHAM_FK = SP.PK_SEQ AND A.KENH_FK = tkn.KBH_FK  and A.dvkd_fk = sp.dvkd_fk   "+ 
				"\n			 ORDER BY A.TUNGAY DESC ),0) * tkn.available as Amount,  ";
		}
		
		query+=
				
		"\n case when tkn.AVAILABLE * isnull(sp.trongluong,0) is null then 0 else tkn.AVAILABLE * isnull(sp.trongluong,0) end as SanLuong,'' as NVBH, case when npp.trangthai = 1 then N'Hoạt động' else N'Không hoạt động' end trangthaidms, tkn.ngaynhapkho  "+
		"\n from (select * from nhapp_kho_chitiet" +
		"\n where npp_fk in " + Uti_center.quyen_npp(obj.getuserId()) +"" +
		"\n and sanpham_fk in " + Uti_center.quyen_sanpham(obj.getuserId()) +" " +
		"\n and kbh_fk in "+ Uti_center.quyen_kenh(obj.getuserId()) ;
		if(obj.getnppId().length() > 0)
			query = query + " and npp_fk = '" + obj.getnppId()+"'";

		query = query + ") tkn "+
				"\n inner join kenhbanhang kbh on kbh.pk_seq = tkn.kbh_fk "+
				"\n inner join sanpham sp on sp.pk_seq = tkn.sanpham_fk "+
				"\n inner join donvikinhdoanh dvkd on dvkd.pk_seq = sp.dvkd_fk "+
				"\n inner join kho k on k.pk_seq = tkn.kho_fk "+
			/*	"\n left join quycach qc on qc.dvdl1_fk = sp.dvdl_fk and qc.sanpham_fk = sp.pk_seq "+*/
				"\n left join donvidoluong dvdl on dvdl.pk_seq = sp.dvdl_fk " +
				"\n left join QUYCACH qc on qc.SANPHAM_FK = sp.PK_SEQ and qc.DVDL1_FK = dvdl.PK_SEQ and qc.DVDL2_FK ='100018' " +
				"\n left join chungloai cl on cl.pk_seq = sp.chungloai_fk " +
				"\n left join nhanhang nh on nh.pk_seq = sp.nhanhang_fk " +
				"\n inner join nhaphanphoi npp on npp.pk_seq = tkn.npp_fk " +
				"\n left join khuvuc kv on npp.khuvuc_fk = kv.pk_seq  " +
				"\n left join vung v on kv.vung_fk = v.pk_seq " +
				"\n where 1 = 1 " + sql;
		/*query += "\n union ";
	
	     query += "select kbh.ten as Channel, sp.ma as Sku_code, isnull(sp.MA_DDT,'') as MADDT, sp.ten as SKU, sp.tenviettat as tentatsp, tkn.AVAILABLE as Piece,k.ten + ' NVBH' as Warehouse, " +
				" V.PK_SEQ AS VUNGID, V.TEN AS VUNGTEN, KV.PK_SEQ AS KVID, KV.TEN AS KVTEN, " +
				" NPP.PK_SEQ AS NPPID, NPP.TEN AS NPPTEN, " +
				" nh.ten as Brans,round (isnull(tkn.AVAILABLE,0)/qc.soluong1, 2) as Quantily,"+
				" dvkd.donvikinhdoanh as Business_unit,cl.ten as Category,tkn.booked, ";
		
		if(giatinh.equals("1"))
		{
			query +=
				"\n	isnull( "+ 
				"\n	(  "+
				"\n		select top(1) bgmsp.giamuanpp  "+
				"\n		from banggiamuanpp  bgm inner join banggiamuanpp_npp bgmnpp on bgm.pk_seq=bgmnpp.banggiamuanpp_fk "+ 
				"\n			inner join bgmuanpp_sanpham bgmsp on bgmsp.bgmuanpp_fk=bgm.pk_seq "+ 
				"\n		where bgmsp.sanpham_fk=sp.pk_seq  "+
				"\n			and bgmnpp.npp_fk=tkn.npp_fk  "+
				"\n			and bgm.kenh_fk=tkn.kbh_fk and bgm.dvkd_fk = sp.dvkd_fk "+  
				"\n		order by bgm.tungay desc   "+
				"\n	),0) as dongia , "+
				"\n	isnull( "+ 
				"\n	(  "+
				"\n		select top(1) bgmsp.giamuanpp  "+
				"\n		from banggiamuanpp  bgm inner join banggiamuanpp_npp bgmnpp on bgm.pk_seq=bgmnpp.banggiamuanpp_fk "+ 
				"\n			inner join bgmuanpp_sanpham bgmsp on bgmsp.bgmuanpp_fk=bgm.pk_seq "+ 
				"\n		where bgmsp.sanpham_fk=sp.pk_seq  "+
				"\n			and bgmnpp.npp_fk=tkn.npp_fk  "+
				"\n			and bgm.kenh_fk=tkn.kbh_fk and bgm.dvkd_fk = sp.dvkd_fk "+  
				"\n		order by bgm.tungay desc   "+
				"\n	),0) * tkn.available as Amount, ";
		}else
		{
			query +=
				"\n	ISNULL((SELECT TOP 1 CAST(GIABANLECHUAN AS FLOAT)/1.1 AS GIABANLC "+ 
				"\n			 FROM BANGGIABANLECHUAN  A INNER JOIN BANGGIABLC_SANPHAM B ON A.PK_SEQ=B.BGBLC_FK "+ 
				"\n			                           INNER JOIN BANGGIABANLECHUAN_NPP C ON A.PK_SEQ = C.BANGGIABANLECHUAN_FK "+ 
				"\n			 WHERE C.NPP_FK = tkn.NPP_FK AND B.SANPHAM_FK = SP.PK_SEQ AND A.KENH_FK = tkn.KBH_FK  and A.dvkd_fk = sp.dvkd_fk   "+ 
				"\n			 ORDER BY A.TUNGAY DESC ),0) AS dongia, "+ 
				"\n	ISNULL((SELECT TOP 1 CAST(GIABANLECHUAN AS FLOAT)/1.1 AS GIABANLC "+ 
				"\n			 FROM BANGGIABANLECHUAN  A INNER JOIN BANGGIABLC_SANPHAM B ON A.PK_SEQ=B.BGBLC_FK "+ 
				"\n			                           INNER JOIN BANGGIABANLECHUAN_NPP C ON A.PK_SEQ = C.BANGGIABANLECHUAN_FK "+ 
				"\n			 WHERE C.NPP_FK = tkn.NPP_FK AND B.SANPHAM_FK = SP.PK_SEQ AND A.KENH_FK = tkn.KBH_FK  and A.dvkd_fk = sp.dvkd_fk   "+ 
				"\n			 ORDER BY A.TUNGAY DESC ),0) * tkn.available as Amount,  ";
		}
		
		query+=
				
		"\n case when tkn.AVAILABLE * isnull(sp.trongluong,0) is null then 0 else tkn.AVAILABLE * isnull(sp.trongluong,0) end as SanLuong, ddkd.ten as NVBH, case when npp.trangthai = 1 then N'Hoạt động' else N'Không hoạt động' end trangthaidms "+
		"\n from (select * from NVBH_KHO" +
		"\n where npp_fk in " + Uti_center.quyen_npp(obj.getuserId()) +"" +
		"\n and sanpham_fk in " + Uti_center.quyen_sanpham(obj.getuserId()) +" " +
		"\n and kbh_fk in "+ Uti_center.quyen_kenh(obj.getuserId()) ;
		if(obj.getnppId().length() > 0)
			query = query + " and npp_fk = '" + obj.getnppId()+"'";

		query = query + ") tkn "+
				"\n inner join kenhbanhang kbh on kbh.pk_seq = tkn.kbh_fk "+
				"\n inner join sanpham sp on sp.pk_seq = tkn.sanpham_fk "+
				"\n inner join donvikinhdoanh dvkd on dvkd.pk_seq = sp.dvkd_fk "+
				"\n inner join kho k on k.pk_seq = tkn.kho_fk "+
				"\n inner join Daidienkinhdoanh ddkd on ddkd.pk_seq = tkn.nvbh_fk "+
				"\n left join quycach qc on qc.dvdl1_fk = sp.dvdl_fk and qc.sanpham_fk = sp.pk_seq "+
				"\n left join donvidoluong dvdl on dvdl.pk_seq = sp.dvdl_fk " +
				"\n left join QUYCACH qc on qc.SANPHAM_FK = sp.PK_SEQ and qc.DVDL1_FK = dvdl.PK_SEQ and qc.DVDL2_FK ='100018' " +
				"\n left join chungloai cl on cl.pk_seq = sp.chungloai_fk " +
				"\n left join nhanhang nh on nh.pk_seq = sp.nhanhang_fk " +
				"\n inner join nhaphanphoi npp on npp.pk_seq = tkn.npp_fk " +
				"\n left join khuvuc kv on npp.khuvuc_fk = kv.pk_seq  " +
				"\n left join vung v on kv.vung_fk = v.pk_seq " +
				"\n where 1 = 1 " + sql;*/

		
		System.out.println("Ton hien tai: " + query);
		ResultSet rs = db.get(query);

		int i = 2;
		if(rs!=null)
		{
			try 
			{// se do rong cho cac cot se dung
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

				Cell cell = null;
				while(rs.next())// lap den cuoi bang du lieu
				{

					//lay tu co so du lieu, gan bien
					String Channel = rs.getString("Channel");
					String SKU =rs.getString("SKU");
					String Piece =rs.getString("Piece");			

					String Warehouse = rs.getString("Warehouse");
					String SkuCode = rs.getString("SKU_code");		
					double Quantily = rs.getDouble("Quantily");
					String BusinessUnit = rs.getString("Business_unit");
					String Category = rs.getString("Category");
					String Brands = rs.getString("Brans");
					double Amount = rs.getDouble("Amount");
					//System.out.println("Amount la: " + Amount + "\n");

					cell = cells.getCell("AA" + Integer.toString(i)); 	cell.setValue(Channel);
					cell = cells.getCell("AB" + Integer.toString(i)); 	cell.setValue(BusinessUnit);
					cell = cells.getCell("AC" + Integer.toString(i));	cell.setValue(rs.getString("VUNGTEN"));
					cell = cells.getCell("AD" + Integer.toString(i));	cell.setValue(rs.getString("KVTEN"));
					cell = cells.getCell("AE" + Integer.toString(i));	cell.setValue(rs.getString("NPPID"));
					cell = cells.getCell("AF" + Integer.toString(i));	cell.setValue(rs.getString("NPPTEN"));
					cell = cells.getCell("AG" + Integer.toString(i)); 	cell.setValue(Brands);
					cell = cells.getCell("AH" + Integer.toString(i)); 	cell.setValue(Category);
					cell = cells.getCell("AI" + Integer.toString(i)); 	cell.setValue(SkuCode);
					cell = cells.getCell("AJ" + Integer.toString(i)); 	cell.setValue(SKU);
					cell = cells.getCell("AK" + Integer.toString(i)); 	cell.setValue(Warehouse);				
					cell = cells.getCell("AL" + Integer.toString(i)); 	cell.setValue(Float.parseFloat(Piece));
					cell = cells.getCell("AM" + Integer.toString(i)); 	cell.setValue(Quantily);
					cell = cells.getCell("AN" + Integer.toString(i)); 	cell.setValue(Amount); 
					cell = cells.getCell("AO" + Integer.toString(i)); 	cell.setValue(rs.getDouble("dongia")); 
					cell = cells.getCell("AP" + Integer.toString(i)); 	cell.setValue(rs.getString("tentatsp"));
					cell = cells.getCell("AQ" + Integer.toString(i)); 	cell.setValue(rs.getDouble("booked"));
					cell = cells.getCell("AR" + Integer.toString(i)); 	cell.setValue(rs.getString("NVBH"));
					cell = cells.getCell("AS" + Integer.toString(i)); 	cell.setValue(rs.getString("trangthaidms"));
					cell = cells.getCell("AT" + Integer.toString(i)); 	cell.setValue(rs.getString("ngaynhapkho"));
					i++;
					/*if(i > 65000)
					{						
						if(rs!=null) rs.close();
						if(db != null) 
							db.shutDown();
						throw new Exception("Xin loi.Du lieu vuot qua 65.000 dong. Vui long chon dieu kien lay bao cao");
					}*/
				}
				if(rs!=null)
					rs.close();
				if(db != null) 
					db.shutDown();
				if(i==2){
					throw new Exception("Khong co bao cao trong thoi gian nay...");
				}
				/*
				getAn(workbook,49);
				PivotTables pivotTables = worksheet.getPivotTables();

			    //Adding a PivotTable to the worksheet
				String pos = Integer.toString(i-1);	
			    int index = pivotTables.add("=AA1:AN" + pos,"A9","PivotTableDemo");

			    PivotTable pivotTable = pivotTables.get(index);//truyen index

			    pivotTable.setRowGrand(false);
			    pivotTable.setColumnGrand(true);
			    pivotTable.setAutoFormat(true);

			    pivotTable.setAutoFormatType(PivotTableAutoFormatType.TABLE10);

			    pivotTable.addFieldToArea(PivotFieldType.ROW, 0);	pivotTable.getRowFields().get(0).setAutoSubtotals(true);  
			    pivotTable.addFieldToArea(PivotFieldType.ROW, 1);	pivotTable.getRowFields().get(1).setAutoSubtotals(true);
			    pivotTable.addFieldToArea(PivotFieldType.ROW, 2);	pivotTable.getRowFields().get(2).setAutoSubtotals(true);
			    pivotTable.addFieldToArea(PivotFieldType.ROW, 3);	pivotTable.getRowFields().get(3).setAutoSubtotals(true);  
			    pivotTable.addFieldToArea(PivotFieldType.ROW, 5);	pivotTable.getRowFields().get(4).setAutoSubtotals(true);
			    pivotTable.addFieldToArea(PivotFieldType.ROW, 8);	pivotTable.getRowFields().get(5).setAutoSubtotals(false);
			    pivotTable.addFieldToArea(PivotFieldType.ROW, 9);	pivotTable.getRowFields().get(6).setAutoSubtotals(false);

			    pivotTable.addFieldToArea(PivotFieldType.COLUMN, 10); //kho

			    pivotTable.addFieldToArea(PivotFieldType.DATA, 11);	
			    pivotTable.getDataFields().get(0).setDisplayName("SoLuongLe");
			    pivotTable.getDataFields().get(0).setNumber(3);

			    pivotTable.addFieldToArea(PivotFieldType.DATA, 13);	
			    pivotTable.getDataFields().get(1).setDisplayName("SoTien");
			    pivotTable.getDataFields().get(1).setNumber(3);

			    pivotTable.addFieldToArea(PivotFieldType.COLUMN, pivotTable.getDataField());*/

			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(e.getMessage());
			}
		} else {
			if(db != null) db.shutDown();
			return false;
		}

		if(db != null) db.shutDown();
		return true;

	}
	
	private boolean CreateStaticData2(Workbook workbook, IStockintransit obj, String condition, String giatinh) throws Exception
	{
		OracleConnUtils db = new OracleConnUtils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();

		geso.dms.center.util.Utility Uti_center = new geso.dms.center.util.Utility();

		String sql = "";
		
		sql = sql + condition;

/*		String query = "select  ITEM_CODE as SanPham,ITEM_DESC as TenSP,NVL(COUNT_LOT,0) as SoCuon,CUSTOMER_NUMBER as MaKH,CUSTOMER_NAME as TenKH,CUST_CODE as MaRutGon,ASM_CODE as MaASM,ASM_NAME as TenASM,ASSIGN_CUS as KHDacBiet"
				+ " from apps.SGP_IP_ONHAND ";*/
		
		String query = "select * "
				+ " from apps.SGP_IP_ONHAND ";

		System.out.println("Ton hien tai: " + query);
		ResultSet rs = db.get(query);
		ResultSetMetaData rsmd = rs.getMetaData();
		int socottrongSql = rsmd.getColumnCount();
		int countRow = 10;
		Cell cell = null;
		for( int i =1 ; i <=socottrongSql ; i ++  )
		{
			cell = cells.getCell(countRow,i-1 );cell.setValue(rsmd.getColumnName(i));
			cells.setColumnWidth(i, 20.0f);
			ReportAPI.setCellBackground(cell, new Color(70,130,180), BorderLineType.THIN, true, 0);	
		 
		}
		countRow ++;
		int sonvkdpda = 0;
		int stt = 0;
		while(rs.next())
		{
			boolean kt = false;
			boolean ck = true;
			stt++;
			String value = "";
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
		

	

		if(db != null) db.shutDown();
		return true;

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
	private void getAn(Workbook workbook,int i)
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
