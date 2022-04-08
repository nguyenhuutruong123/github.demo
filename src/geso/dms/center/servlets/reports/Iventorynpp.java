package geso.dms.center.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.Idbutils;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.aspose.cells.PivotFieldDataDisplayFormat;
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTables;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class Iventorynpp extends HttpServlet {	
	public Iventorynpp() {
        super();
       
    }
	private String gettenuser(String userId_){
		dbutils db = new dbutils();
		String sql_getnam = "select ten from nhanvien where pk_seq = "+userId_;
		ResultSet rs_tenuser = db.get(sql_getnam);
		if (rs_tenuser != null){
			try{
				while (rs_tenuser.next()){
					return rs_tenuser.getString("ten");
				}
			}
			catch(Exception er){
				return "";
			}
			
			finally{
				try{
					if(rs_tenuser != null) rs_tenuser.close();
					if(db != null) db.shutDown();
				}
				catch(Exception e ){}
			}
		}

		return "";
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		IStockintransit obj = new Stockintransit();
		
		Utility util = new Utility();
		//HttpSession session = request.getSession();
		//String userTen = (String)session.getAttribute("userTen");
		//String querystring=request.getQueryString();
		//String userId=	util.getUserId(querystring);
		obj.settungay("");
		obj.setdenngay("");
		obj.setMsg("");
		obj.setuserId(userId);
		obj.setuserTen(userTen);
    	
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		session.setAttribute("userTen", userTen);
		session.setAttribute("util", util);
		String nextJSP = "/AHF/pages/Center/Inventorynpp.jsp";
		response.sendRedirect(nextJSP);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		OutputStream out = response.getOutputStream(); 
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		Utility util = new Utility();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  
		String manpp=util.getIdNhapp(userId);
		String nextJSP = "/AHF/pages/Center/Inventorynpp.jsp";
		dbutils db = null;
		
		try {
			obj.setuserTen((String) session.getAttribute("userTen")!=null?
					(String) session.getAttribute("userTen"):"");

			obj.settungay(Utility.antiSQLInspection((request.getParameter("tungay")))!=null?
					Utility.antiSQLInspection(request.getParameter("tungay")):"");

			obj.setdenngay(Utility.antiSQLInspection((request.getParameter("denngay")))!=null?
					Utility.antiSQLInspection(request.getParameter("denngay")):"");

			obj.setuserId(Utility.antiSQLInspection((request.getParameter("userId")))!=null?
					Utility.antiSQLInspection(request.getParameter("userId")):"");		

			String avaliable = Utility.antiSQLInspection(request.getParameter("avail"));
			String giatinh = Utility.antiSQLInspection(request.getParameter("giatinh")); 
			// 1. Giá mua NPP, 2. Giá bán NPP

			/*response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition",
					"attachment; filename=TonHienTai.xls");*/

			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=Iventorynpp.xlsm");
			db = new dbutils();
			String sql = "select * from banggiamuanpp_npp bgmnpp where bgmnpp.npp_fk = '"+manpp+"'";
			ResultSet rscheck = db.get(sql);
			if(rscheck!=null)
				if(!rscheck.next()){
					obj.setMsg("Trên trung tâm chưa chọn bảng giá mua cho nhà phân phối, vui lòng liên hệ với Admin để xử lý.");
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);
					session.setAttribute("userTen", userTen);
					session.setAttribute("util", util);
					response.sendRedirect(nextJSP);

				}else{

					boolean isTrue = CreatePivotTable(out, obj, avaliable, giatinh);
					if(!isTrue){
						PrintWriter writer = new PrintWriter(out);
						writer.write("Không có dữ liệu trong khoảng thời gian này.");
						writer.close();
					}
				}
			rscheck.close();
			DbClose(db);
		} 
		catch (Exception ex) {
			DbClose(db);
			obj.setMsg(ex.getMessage());
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			session.setAttribute("userTen", userTen);
			session.setAttribute("util", util);
			response.sendRedirect(nextJSP);
			System.out.println("Lỗi ngoại lệ không lấy được báo cáo: "+ex.toString());
		}
	}
	
	private boolean CreatePivotTable(OutputStream out,IStockintransit obj, String avaliable, String giatinh) throws Exception
	{   
		String chuoi=getServletContext().getInitParameter("path") + "\\Iventory(NPP).xlsm";

		FileInputStream fstream;
		fstream = new FileInputStream(chuoi);
		//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\Iventory(NPP).xlsm");
		//FileInputStream fstream = new FileInputStream(f) ;
		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateStaticHeader(workbook, obj.getDateTime(),obj.getDateTime(), obj.getuserTen(), giatinh);

		boolean isTrue = CreateStaticData(workbook,obj,avaliable, giatinh);
		if(!isTrue)
			return false;
		workbook.save(out);
		fstream.close();
		return true;	    
	}
	
	private void CreateStaticHeader(Workbook workbook, String dateFrom, String dateTo, String UserName, String giatinh) throws Exception 
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);	   	   
		Cells cells = worksheet.getCells();
		Style style;
		//cells.setColumnWidth(0, 200.0f);
		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");  
		cell.setValue("TỒN HIỆN TẠI");   

		style = cell.getStyle();
		Font font2 = new Font();
		font2.setColor(Color.RED);//mau chu
		font2.setSize(14);// size chu
		style.setFont(font2); 
		style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu       
		cell.setStyle(style);
		cell = cells.getCell("A2"); getCellStyle(workbook,"A2",Color.NAVY,true,9);
		cell.setValue("Từ ngày " + dateFrom + " Đến ngày " + dateTo);    
		cell = cells.getCell("A3");getCellStyle(workbook,"A3",Color.NAVY,true,9);
		cell.setValue("Ngày Tạo : " + this.getDateTime());
		cell = cells.getCell("A4");getCellStyle(workbook,"A4",Color.NAVY,true,9);
		cell.setValue("Tạo bởi:  " + UserName);

		//tieu de, hang dau tien cua bang du lieu, cell la toa do cua exel
		cell = cells.getCell("AA1"); cell.setValue("Kenh Ban Hang");
		cell = cells.getCell("AB1"); cell.setValue("Ten San Pham");
		cell = cells.getCell("AC1"); cell.setValue("So Luong Quy Le");	  
		cell = cells.getCell("AD1"); cell.setValue("Kho");
		cell = cells.getCell("AE1"); cell.setValue("Ma Nha Phan Phoi");
		cell = cells.getCell("AF1"); cell.setValue("Ma San Pham");
		cell = cells.getCell("AG1"); cell.setValue("So Luong Thung");
		cell = cells.getCell("AH1"); cell.setValue("Don Vi Kinh Doanh");
		cell = cells.getCell("AI1"); cell.setValue("Chung Loai");
		cell = cells.getCell("AJ1"); cell.setValue("Nhan Hang");
		cell = cells.getCell("AK1"); cell.setValue("So Tien");    		
		cell = cells.getCell("AL1"); cell.setValue("Don Gia");
		cell = cells.getCell("AM1"); cell.setValue("Ten San Pham(Viet Tat)");
		cell = cells.getCell("AN1"); cell.setValue("Booked");
		cell = cells.getCell("AO1"); cell.setValue("NVBH");
		cell = cells.getCell("AP1"); cell.setValue("TonTamTinh");

		worksheet.setName("Sheet1");
	}
	
	private boolean CreateStaticData(Workbook workbook,IStockintransit obj, String avaliable, String giatinh) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		Utility utl = new  Utility();
		String manpp ="";
		manpp = utl.getIdNhapp(obj.getuserId());

		String condition = "";
		if(avaliable.equals("1")){
			condition +=" and tkn.AVAILABLE > 0";
		}

		String sql ="\n select kbh.ten as Channel, sp.ma as Sku_code, isnull(sp.MA_DDT,'') as MADDT, " +
		"\n sp.ten as SKU, sp.tenviettat as tentatsp, tkn.AVAILABLE as Piece, k.ten as Warehouse, " +
		"\n tkn.npp_fk as Distributor_code ," +
		"\n nh.ten as Brans, '' as Quantily, " +
		"\n dvkd.donvikinhdoanh as Business_unit, cl.ten as Category, tkn.booked, isnull(ttt.BOOKED_SQL,0) TonTamTinh "; 

		if(giatinh.equals("1"))
		{
			sql += "\n ,cast(gm.dongia as numeric(18,0)) as dongia , tkn.AVAILABLE * cast(gm.dongia as numeric(18,0)) as Amount ";
		}
		else
		{
			sql += "\n ,cast(gb.dongia as numeric(18,0)) as dongia , tkn.AVAILABLE * cast(gb.dongia as numeric(18,0)) as Amount ";
		}

		sql +=
			"\n 	,case when tkn.AVAILABLE* isnull(sp.trongluong,0) is null then 0 else tkn.AVAILABLE * isnull(sp.trongluong,0) end as SanLuong,'' as NVBH "+
			"\n from (select * from nhapp_kho where npp_fk = '"+manpp+"') tkn "+
			"\n inner join kenhbanhang kbh on kbh.pk_seq = tkn.kbh_fk "+
			"\n inner join sanpham sp on sp.pk_seq = tkn.sanpham_fk "+
			"\n inner join donvikinhdoanh dvkd on dvkd.pk_seq = sp.dvkd_fk "+
			"\n inner join kho k on k.pk_seq = tkn.kho_fk "+
			/*" left join quycach qc on qc.dvdl1_fk = sp.dvdl_fk and qc.sanpham_fk = sp.pk_seq "+*/
			"\n left join donvidoluong dvdl on dvdl.pk_seq = sp.dvdl_fk "+
			"\n left join chungloai cl on cl.pk_seq = sp.chungloai_fk "+
			"\n left join nhanhang nh on nh.pk_seq = sp.nhanhang_fk "+
			"\n outer apply ( select [dbo].[GiaMuaNppSanPham](tkn.kbh_fk,tkn.npp_fk,tkn.sanpham_fk,dbo.getNgayHienTai() ) dongia) gm " +
			"\n outer apply ( select [dbo].[GiaBanLeNppSanPham](null,tkn.kbh_fk,tkn.npp_fk,tkn.sanpham_fk,dbo.getNgayHienTai() ) dongia) gb " +
			"\n outer apply [dbo].[ufn_TonTamTinh_ChiTiet_Full_Input](tkn.npp_fk,tkn.kbh_fk,tkn.kho_fk,tkn.sanpham_fk)  ttt " + 
			"\n where 1=1 " + condition;
		System.out.println("GET DU LIEU : "+sql);
		ResultSet rs = db.get(sql);

		int i = 2;
		if (rs!=null)
		{
			try 
			{// se do rong cho cac cot se dung
				cells.setColumnWidth(0, 25.0f);
				cells.setColumnWidth(1, 25.0f);
				cells.setColumnWidth(2, 30.0f);
				cells.setColumnWidth(3, 15.0f);
				cells.setColumnWidth(4, 15.0f);			
				cells.setColumnWidth(5, 15.0f);	
				cells.setColumnWidth(6, 15.0f);	
				cells.setColumnWidth(7, 15.0f);

				Cell cell = null;
				while(rs.next())// lap den cuoi bang du lieu
				{
					//lay tu co so du lieu, gan bien
					String Channel = rs.getString("Channel");
					String SKU =rs.getString("SKU");
					String Piece =rs.getString("Piece");		
					String Warehouse = rs.getString("Warehouse");
					String DistributorCode =rs.getString("Distributor_code");
					String SkuCode = rs.getString("SKU_code");		
					String Quantily = rs.getString("Quantily");
					String BusinessUnit = rs.getString("Business_unit");
					String Category = rs.getString("Category");
					String Brands = rs.getString("Brans");
					double Amount = rs.getDouble("Amount");

					//cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(Channel);
					cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(Channel);
					cell = cells.getCell("AB" + Integer.toString(i)); cell.setValue(SKU);
					cell = cells.getCell("AC" + Integer.toString(i)); cell.setValue(Long.parseLong(Piece));
					cell = cells.getCell("AD" + Integer.toString(i)); cell.setValue(Warehouse);				
					cell = cells.getCell("AE" + Integer.toString(i)); cell.setValue(DistributorCode);
					cell = cells.getCell("AF" + Integer.toString(i)); cell.setValue(SkuCode);
					cell = cells.getCell("AG" + Integer.toString(i)); cell.setValue(Quantily);
					cell = cells.getCell("AH" + Integer.toString(i)); cell.setValue(BusinessUnit);
					cell = cells.getCell("AI" + Integer.toString(i)); cell.setValue(Category);
					cell = cells.getCell("AJ" + Integer.toString(i)); cell.setValue(Brands);
					cell = cells.getCell("AK" + Integer.toString(i)); cell.setValue(Amount);
					cell = cells.getCell("AL" + Integer.toString(i)); cell.setValue(rs.getDouble("dongia"));
					cell = cells.getCell("AM" + Integer.toString(i)); cell.setValue(rs.getString("tentatsp"));
					cell = cells.getCell("AN" + Integer.toString(i)); cell.setValue(rs.getDouble("booked"));
					cell = cells.getCell("AO" + Integer.toString(i)); cell.setValue(rs.getString("NVBH"));
					cell = cells.getCell("AP" + Integer.toString(i)); cell.setValue(rs.getDouble("TonTamTinh"));
					i++;
				}

				if(i==2)
					throw new Exception("Không có báo cáo trong khoảng thời gian này...!");
				//create pivot
				getAn(workbook,49);

			}catch(Exception ex){
				System.out.println(ex.toString());
				throw new Exception(ex.getMessage());

			}

			finally{
				if(rs!=null)
					rs.close();
				if(db != null)
					db.shutDown();
			}
		}	    

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
	
	public void DbClose(Idbutils db) {
		if (db != null) {
			try {
				db.shutDown();
			}
			catch (Exception e) {
				
			}
		}
	}
}
