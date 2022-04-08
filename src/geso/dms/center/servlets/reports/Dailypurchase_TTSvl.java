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


public class Dailypurchase_TTSvl extends HttpServlet 
{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Dailypurchase_TTSvl() {
        super();    
    }
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
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
		String nextJSP = "/AHF/pages/Center/RDailypurchase_center.jsp";
		response.sendRedirect(nextJSP);
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{		
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
	    
   	 	String nppId=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
   	 	if(nppId ==null) 
   	 		nppId ="";
   	 	obj.setnppId(nppId);
     	
   	 	obj.setuserTen(userTen);
   	 	
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
	   	 
	   	 String tungay=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays"));
	   	 if(tungay ==null)
	   		 tungay ="";
	   	 obj.settungay(tungay);
	   	 
	   	 String denngay=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays"));
	   	 if(denngay == null)
	   		 denngay ="";
	   	 obj.setdenngay(denngay);
	   	 
	 	   	 String vungId=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"));
	   	 if(vungId ==null)
	   		 vungId = "";
	   	 obj.setvungId(vungId);
	   	 
	   	 String khuvucId=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"));
	   	 if(khuvucId == null)
	   		 khuvucId ="";
	   	 obj.setkhuvucId(khuvucId);
	   	 
	   	 String gsbhId=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gsbhId"));
	   	 if(gsbhId ==null)
	   		 gsbhId ="";
	   	 obj.setgsbhId(gsbhId);
	   	 
	   	 String sanphamId=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sanphamId"));
	   	 if(sanphamId == null)
	   		 sanphamId ="";
	   	 obj.setsanphamId(sanphamId);
	   	 
	   	 String dvdlId=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvdlId"));
		 if(dvdlId == null)
			 dvdlId ="";
		 obj.setdvdlId(dvdlId);
		 
		 String action=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		 
		 String nextJSP = "/AHF/pages/Center/RDailypurchase_center.jsp";
		 if(action.equals("tao")){
		 
			 OutputStream out = response.getOutputStream();
			 try
			 {				
				 response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "Attachment; filename=HangNhapKho" + this.getPiVotName() + ".xlsm");
		        if(!CreatePivotTable(out, obj))
		        {
		        	PrintWriter writer = new PrintWriter(out);
					writer.println("Xin loi: Khong co bao cao trong thoi gian nay");			
					writer.close();
		        }
			}
			 catch(Exception ex){
				 obj.setMsg(ex.toString());
			}
		 }else{
			obj.init();
			session.setAttribute("obj", obj);
			session.setAttribute("userId", obj.getuserId());
			response.sendRedirect(nextJSP);
		 }
	}
	private boolean CreatePivotTable(OutputStream out,IStockintransit obj) throws Exception
    {  		
	    try
	    {
	    	FileInputStream fstream = null;
			Workbook workbook = new Workbook();		
			
			 fstream = new FileInputStream(getServletContext().getInitParameter("path")+"\\Dailypurchase_TTSvl.xlsm");		
				//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\Dailypurchase_TTSvl.xlsm");
				//fstream = new FileInputStream(f) ;
			
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			
		     CreateStaticHeader(workbook, obj.gettungay(),obj.getdenngay(), obj.getuserTen());	    
		     CreateStaticData(workbook,obj);
		     
		     workbook.save(out);
		     fstream.close();
	    }
	    catch(Exception ex)
	    {
	    	return false;
	    }
	    return true;
   }
	
	private void CreateStaticHeader(Workbook workbook, String dateFrom, String dateTo, String UserName) 
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
	    
	    String tieude = "BÁO CÁO HÀNG NHẬP KHO";
	    ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);
	           
	    String message = "có VAT";
		
		cells.setRowHeight(2, 18.0f);
		cell = cells.getCell("A2");
		ReportAPI.getCellStyle(cell, Color.RED, true, 9, message);   

	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("A4");
	    
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ ngày : " + dateFrom + "" );
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến ngày : " + dateTo + "" );
	    
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A5");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A6");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + UserName);
	    
	   
	    cell = cells.getCell("AA1");		cell.setValue("KenhBanHang");			ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AB1");		cell.setValue("DonviKinhDoanh");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AC1");		cell.setValue("ChiNhanh");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AD1");		cell.setValue("KhuVuc");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AE1");		cell.setValue("MaNhaPhanPhoi");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AF1");		cell.setValue("NhaPhanPhoi");			ReportAPI.setCellHeader(cell);	
		cell = cells.getCell("AG1");		cell.setValue("NhanHang");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AH1");		cell.setValue("ChungLoai");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AI1");		cell.setValue("SoChungTu");		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AJ1");		cell.setValue("SoHoaDon");		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AK1");		cell.setValue("MaSanPham");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AL1");		cell.setValue("TenSanPham");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AM1");		cell.setValue("Kho");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AN1");		cell.setValue("TinhThanh");					ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AO1");		cell.setValue("QuanHuyen");			ReportAPI.setCellHeader(cell);	
		cell = cells.getCell("AP1");		cell.setValue("NgayChungTu");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AQ1");		cell.setValue("SoLuongLe");			ReportAPI.setCellHeader(cell);	    
		cell = cells.getCell("AR1");		cell.setValue("SoTien");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AS1");		cell.setValue("TrongLuong");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AT1");		cell.setValue("NgayHoaDon");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AU1");		cell.setValue("HangBeVo");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AV1");		cell.setValue("MaSanPham");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AW1");		cell.setValue("NgayNhanHang");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AX1");		cell.setValue("NgayChot");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AY1");		cell.setValue("SoLuongThung");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AZ1");		cell.setValue("DonGia");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BA1");		cell.setValue("soSO");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BB1");		cell.setValue("soPO");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BC1");		cell.setValue("scheme");			ReportAPI.setCellHeader(cell);
	    
	}

	private boolean CreateStaticData(Workbook workbook, IStockintransit obj) throws Exception 
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();


		String sql = 
			"select  kbh.ten as Channel, v.ten as Region,kv.ten as Area, tt.ten as Province,qh.ten as Town , npp.sitecode as Distributor_code,  \n"  +
			"	npp.ten as Distributor, dvkd.donvikinhdoanh as Business_unit, nhsp.sanpham_fk as SKU_code, sp.ten as SKU, nh.ngaychungtu, nh.ngaynhan as Purdate, \n"+
			"	nhan.ten as Brands, cl.ten as Category, kho.ten as khoTen,  \n"  +
			"	nhsp.soluongnhan as Piece, nh.chungtu as Series_number ,nhsp.soluongnhan * bg_npp_sp.GIAMUANPP as Amount,nhsp.soluongnhan * nhsp.gianet as Amount1,bg_npp_sp.GIAMUANPP as dongia, nhsp.gianet as dongia1, nhsp.soluong*sp.trongluong as trongluong, nh.HDTAICHINH as Order_Number, \n"+
			"	HD.NGAYCHUNGTU as NgayHoaDon, Isnull(nhsp.HangBeVo,0) as HangBeVo, sp.ma_ddt as maDDT, po.SendingDate, 	HD.NGAYCHUNGTU as NgayHoaDon, Isnull(nhsp.HangBeVo,0) as HangBeVo, sp.ma_ddt as maDDT, po.SendingDate,\n"+
			"	case when  ( sp.DVDL_FK = dvdl.PK_SEQ) \n"+
			"				then (select top(1) round((nhsp.SOLUONG*soluong2)/SOLUONG1,1) from QUYCACH qc where (DVDL2_FK =100018 or DVDL2_FK = 1200532) and  qc.SANPHAM_FK = sp.PK_SEQ   order by DVDL2_FK) \n"+
			"	else \n"+
			"	 nhsp.soluong end as soluongthung, nh.SO_number as soSO, po.pk_seq as soPO, nhsp.scheme  \n"  +
			"from nhaphang nh \n" +
			" 	left join hdnhaphang  hd ON HD.CHUNGTU=NH.HDTAICHINH  \n"  +
			"	inner join nhaphang_sp nhsp on nhsp.nhaphang_fk = nh.pk_seq  \n"  +
			"	inner join kho on kho.pk_seq = nhsp.kho_fk  \n"  +
			"	left  join kenhbanhang kbh on kbh.pk_seq = nh.kbh_fk  \n"  +
			"	inner join nhaphanphoi npp on npp.pk_seq = nh.npp_fk   \n"  +
			"	left join donvikinhdoanh dvkd on dvkd.pk_seq = nh.dvkd_fk  \n"  +
			"	inner join sanpham sp on sp.pk_seq = nhsp.sanpham_fk   \n" +
			//"	inner join BGBANLENPP_SANPHAM bgsp on sp.PK_SEQ = bgsp.SANPHAM_FK \n"  +
			"	 left join BANGGIAMUANPP_NPP bg_npp on bg_npp.NPP_FK = npp.PK_SEQ and bg_npp.NPP_FK = npp.pk_seq    \n" +
			"	 left join BGMUANPP_SANPHAM bg_npp_sp on bg_npp_sp.sanpham_fk = sp.pk_seq and bg_npp_sp.BGMUANPP_FK = bg_npp.BANGGIAMUANPP_FK   \n" +
			"	left join nhanhang nhan on nhan.pk_seq = sp.nhanhang_fk  \n"  +
			"	left join chungloai cl on cl.pk_seq = sp.chungloai_fk   \n"  +
			 " LEFT join DONVIDOLUONG dvdl on dvdl.DONVI = nhsp.DONVI "+
			//"	left join quycach qc on qc.sanpham_fk = sp.pk_seq and sp.dvdl_fk = qc.dvdl1_fk  \n"  +
			"	left join khuvuc kv on kv.pk_seq = npp.khuvuc_fk  \n"  +
			"	left join vung v on v.pk_seq = kv.vung_fk  \n"  +
			"	left join quanhuyen qh on qh.pk_seq = npp.quanhuyen_fk  \n"  +
			"	left join tinhthanh tt on tt.pk_seq = npp.tinhthanh_fk \n"+
			"	left join ERP_DONDATHANG po on po.PK_SEQ = nh.DONDATHANG_FK \n"+	
			" where nh.trangthai = '1'" + " and convert(varchar(10),nh.NGAYNHAN,120) >= '"+ obj.gettungay() + "' " + " and convert(varchar(10),nh.NGAYNHAN,120) <= '"+ obj.getdenngay() + "'" ;

		if (obj.getnppId().trim().length() > 0 ) {
			sql = sql + " and  npp.pk_seq=" + obj.getnppId();
		}

		// phanquyen
		geso.dms.center.util.Utility ut = new geso.dms.center.util.Utility();
		sql += " and npp.pk_seq in " + ut.quyen_npp(obj.getuserId())
				+ " and kbh.pk_seq in " + ut.quyen_kenh(obj.getuserId())
				+ " and sp.pk_seq in " + ut.quyen_sanpham(obj.getuserId());

		 if(obj.getkenhId().length() > 0) 
			 sql = sql + " and kbh.pk_seq ='" + obj.getkenhId() +"'";
		 if(obj.getvungId().length()>0) 
			 sql = sql +" and v.pk_seq ='" + obj.getvungId() +"'";
		 if(obj.getkhuvucId().length() > 0)
			 sql = sql + " and kv.pk_seq ='" + obj.getkhuvucId() +"'";
		 if(obj.getdvkdId().length()> 0) 
			 sql = sql +" and sp.dvkd_fk ='" + obj.getdvkdId() +"'";
		 if(obj.getnppId().length()>0) 
			 sql = sql +" and npp.pk_seq ='" + obj.getnppId() +"'";
		 if(obj.getnhanhangId().length()>0) 
			 sql = sql +" and nhan.pk_seq ='"+ obj.getnhanhangId() +"'";
		 if(obj.getchungloaiId().length()>0)
			 sql = sql +" and cl.pk_seq ='"+ obj.getchungloaiId() +"'";
		 if(obj.getdvdlId().length()>0) 
			 sql = sql + " and sp.dvdl_fk ='"+ obj.getdvdlId() +"'";
		 if(obj.getsanphamId().length()>0) 
			 sql = sql + " and sp.pk_seq = '"+ obj.getsanphamId() +"'";
		 
		 sql += " order by nh.ngaynhan";
		
		 System.out.println("Hang Nhap Kho: " + sql + "\n");
		ResultSet rs = db.get(sql);

		int i = 2;
		if (rs != null) 
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

				Cell cell = null;
				while (rs.next())// lap den cuoi bang du lieu
				{

					// lay tu co so du lieu, gan bien
					String Channel = rs.getString("Channel");
					String Region = rs.getString("Region");
					String Area = rs.getString("Area");
					String Distributor = rs.getString("Distributor");
				
					String DistributorCode = rs.getString("Distributor_code");
					String BusinessUnit = rs.getString("Business_unit");
					String Brands = rs.getString("Brands");
					String Category = rs.getString("Category");
					String Piece = rs.getString("Piece");
				/*	String Soluongthung = rs.getString("Soluongthung");*/
					String SKU = rs.getString("SKU");
					String khoTen = rs.getString("khoTen");
					String dongia = rs.getString("dongia");
					String Province = rs.getString("Province");
					String Town = rs.getString("Town");
					String SKUcode = rs.getString("SKU_Code");
					String Purdate = rs.getString("Purdate");
					//So Chung Tu
					String SeriesNumber = rs.getString("Series_number");
					//So Hoa Don
					String Order_Number = rs.getString("Order_Number");
					double hang_be_vo=rs.getDouble("hangbevo");
					String SendingDate = rs.getString("SendingDate")==null?"":rs.getString("SendingDate");
					if(SendingDate != "")
						SendingDate = SendingDate.substring(0, 10);
				
					cell = cells.getCell("AA" + Integer.toString(i));	cell.setValue(Channel);
					cell = cells.getCell("AB" + Integer.toString(i)); 	cell.setValue(BusinessUnit);
					cell = cells.getCell("AC" + Integer.toString(i));	cell.setValue(Region);
					cell = cells.getCell("AD" + Integer.toString(i));	cell.setValue(Area);
					cell = cells.getCell("AE" + Integer.toString(i));	cell.setValue(DistributorCode);
					cell = cells.getCell("AF" + Integer.toString(i));	cell.setValue(Distributor);
					cell = cells.getCell("AG" + Integer.toString(i));	cell.setValue(Brands);
					cell = cells.getCell("AH" + Integer.toString(i));	cell.setValue(Category);
					cell = cells.getCell("AI" + Integer.toString(i));	cell.setValue(SeriesNumber);
					cell = cells.getCell("AJ" + Integer.toString(i));	cell.setValue(Order_Number);
					cell = cells.getCell("AK" + Integer.toString(i));	cell.setValue(SKUcode);
					cell = cells.getCell("AL" + Integer.toString(i));	cell.setValue(SKU); //10
					cell = cells.getCell("AM" + Integer.toString(i));	cell.setValue(khoTen); //11
					cell = cells.getCell("AN" + Integer.toString(i));	cell.setValue(Province);
					cell = cells.getCell("AO" + Integer.toString(i));	cell.setValue(Town);
					cell = cells.getCell("AP" + Integer.toString(i));	cell.setValue(rs.getString("ngaychungtu")); //14
					cell = cells.getCell("AQ" + Integer.toString(i));	cell.setValue(Float.parseFloat(Piece)); //15
					cell = cells.getCell("AR" + Integer.toString(i));	cell.setValue(rs.getDouble("amount"));	 //16
					cell = cells.getCell("AS" + Integer.toString(i));	cell.setValue(rs.getDouble("trongluong"));
					cell = cells.getCell("AT" + Integer.toString(i));	cell.setValue(rs.getString("NgayHoaDon"));
					cell = cells.getCell("AU" + Integer.toString(i));	cell.setValue(hang_be_vo);
					cell = cells.getCell("AV" + Integer.toString(i));	cell.setValue(SKUcode);
					cell = cells.getCell("AW" + Integer.toString(i));	cell.setValue(Purdate);
					cell = cells.getCell("AX" + Integer.toString(i));	cell.setValue(SendingDate);
					cell = cells.getCell("AY" + Integer.toString(i));	cell.setValue(rs.getDouble("soluongthung"));
					cell = cells.getCell("AZ" + Integer.toString(i));	cell.setValue(rs.getDouble("dongia"));
					cell = cells.getCell("BA" + Integer.toString(i));	cell.setValue(rs.getDouble("soSO"));
					cell = cells.getCell("BB" + Integer.toString(i));	cell.setValue(rs.getDouble("soPO"));
					cell = cells.getCell("BC" + Integer.toString(i));	cell.setValue(rs.getString("scheme"));
					
					i++;
				
				}
				if (rs != null) 
				{
					rs.close();
				}
				if(db != null) 
					db.shutDown();
				if(i==2){
					throw new Exception("Khong co bao cao trong thoi gian nay....!!");
				}
			

			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				throw new Exception(
						"Xin loi. Da xay ra loi trong qua trinh dien du lieu vao file Excel");
			}
		} 
		else {
			if(db != null) 
				db.shutDown();			
			return false;
		}
		
		if(db != null) 
			db.shutDown();
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
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
}
