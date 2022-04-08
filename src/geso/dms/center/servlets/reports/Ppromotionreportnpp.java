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
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
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
public class Ppromotionreportnpp extends HttpServlet {	
    public Ppromotionreportnpp() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		HttpSession session = request.getSession();
		String userTen = (String) session.getAttribute("userTen");
		String querystring = request.getQueryString();
		
		Utility Ult = new Utility();
		String userId = Ult.getUserId(querystring);	
		IStockintransit obj = new Stockintransit();
		
		obj.setuserTen(userTen);
		obj.setuserId(userId);
		obj.init();
		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Distributor/PromotionReportnpp.jsp";
		response.sendRedirect(nextJSP);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OutputStream out = response.getOutputStream(); 
		Stockintransit obj = new Stockintransit();
		String nextJSP = "/AHF/pages/Distributor/PromotionReportnpp.jsp";
		HttpSession session = request.getSession();
		Utility util = new Utility();
		try {	
	    	if(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("unghang")))!= null)
	    		obj.setUnghang("1");
			else
				obj.setUnghang("0");
	    				
			System.out.println(obj.getUnghang());
			
			obj.setuserTen((String) session.getAttribute("userTen")!=null?
						(String) session.getAttribute("userTen"):"");			
			
			obj.setuserId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")))!=null? 
					util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")):""));
			
			obj.setYear(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("year")))!=null? 
					util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("year"))):"" );
			
			obj.setMonth(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("month")))!=null?
					util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("month")):""));
			
			
			obj.setPrograms(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("programs")))!=null? 
					util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("programs")):""));
			
			obj.setFieldShow(request.getParameterValues("fieldsHien")!=null?
					request.getParameterValues("fieldsHien"):null);
			
			String sql = "";
			if(obj.getPrograms().length()>0){
				sql +=" AND ctkm.SCHEME = '" + obj.getPrograms() +"'";
			}
			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			if(action.equals("create")){
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BaoCaoXuatKhuyenMai.xlsm");
				
				boolean isTrue = CreatePivotTable(out, obj,sql);
				
				if(!isTrue){
					PrintWriter writer = new PrintWriter(out);
					writer.write("Xin loi. Khong co bao cao trong thoi gian nay..");
					writer.close();
				}
			}		
			
		} catch (Exception ex) {
			obj.setMsg(ex.getMessage());
			response.sendRedirect(nextJSP);
		}
		obj.init();
		session.setAttribute("obj", obj);
	}
	private boolean CreatePivotTable(OutputStream out, IStockintransit obj, String sql) throws Exception
    {  
		//String chuoi=getServletContext().getInitParameter("path") + "\\XuatKhuyenMai.xlsm";
		//FileInputStream fstream = new FileInputStream(chuoi);
		File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\XuatKhuyenMai.xlsm");
		FileInputStream fstream = new FileInputStream(f) ;
	//	FileInputStream fstream = new FileInputStream("D:\\Best Stable\\Best\\WebContent\\pages\\Templates\\XuatKhuyenMai.xlsm");
		
		Workbook workbook = new Workbook();
		
		workbook.open(fstream);
		
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		
		CreateStaticHeader(workbook, obj.getMonth(), obj.getYear(), obj.getuserTen());
		
		boolean isTrue = CreateStaticData(workbook, obj,sql);
		
		if(!isTrue){
			return false;
		}else{
			workbook.save(out);
		}	
		
		fstream.close();
		return true;
   }
	
	private void CreateStaticHeader(Workbook workbook, 
				String month, String year, String UserName)throws Exception 
	{
		try{
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
		    
		    cell.setValue("BÁO CÁO XUẤT KHUYẾN MÃI ĐÃ SỬ DỤNG");  getCellStyle(workbook,"A1",Color.RED,true,14);	  	
		    
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("A3");
		    
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Tháng : " + month + "" );
		    
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("B3"); 
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Năm : " + year + "" );
		    
		    cells.setRowHeight(4, 18.0f);
		    cell = cells.getCell("A4");getCellStyle(workbook,"A4",Color.NAVY,true,9);
		    cell.setValue("Ngày báo cáo: " + this.getDateTime());
		    
		    cells.setRowHeight(5, 18.0f);
		    cell = cells.getCell("A5");getCellStyle(workbook,"A5",Color.NAVY,true,9);
		    cell.setValue("Được tạo bởi Nhà phân phối:  " + UserName);
		   
		    cell = cells.getCell("AA1"); cell.setValue("Kenh ban hang"); 		   
		    cell = cells.getCell("AB1"); cell.setValue("Ma chuong trinh");
		    cell = cells.getCell("AC1"); cell.setValue("Ten chuong trinh");	
		    cell = cells.getCell("AD1"); cell.setValue("Loai chuong trinh");
		    cell = cells.getCell("AE1"); cell.setValue("Ma san pham");		  
		    cell = cells.getCell("AF1"); cell.setValue("Ten san pham");   
		    cell = cells.getCell("AG1"); cell.setValue("Nhan hang");
		    cell = cells.getCell("AH1"); cell.setValue("Chung loai");
		    cell = cells.getCell("AI1"); cell.setValue("Ma nha phan phoi");
		    cell = cells.getCell("AJ1"); cell.setValue("Tinh thanh");
		    cell = cells.getCell("AK1"); cell.setValue("Quan huyen");
		  	cell = cells.getCell("AL1"); cell.setValue("Ma khach hang");
		    cell = cells.getCell("AM1"); cell.setValue("Ten khach hang");
		    cell = cells.getCell("AN1"); cell.setValue("Ngay");
		    cell = cells.getCell("AO1"); cell.setValue("So luong le");
		    cell = cells.getCell("AP1"); cell.setValue("Tong tien");
		    cell = cells.getCell("AQ1"); cell.setValue("So luong thung");		   
		}catch(Exception ex){
			throw new Exception("Khong tao duoc header cho bao cao");
		}
	}

	private boolean CreateStaticData(Workbook workbook, IStockintransit obj, String query)throws Exception {
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		dbutils db = new dbutils();
		Utility utl = new Utility();
		String manpp = "";
		manpp = utl.getIdNhapp(obj.getuserId());
		

		String sql = "select kbh.ten as Channel, tt.ten as Region, qh.ten as Area, npp.ten as " + 
					 		 "Distributor,dhtkm.spma as SKU_code, sp.ten as SKU, ctkm.diengiai + '__' + ctkm.tungay + '-' + ctkm.denngay  as Program_Program, " +
					 		 "nh.ten as Brand, cl.ten as Category,tt.ten as Province, qh.ten as Town, npp.pk_seq as Distributor_code, " +
					 		 "ctkm.scheme as Program_code,  case when ctkm.loaict = 1 then 'Binh Thuong' when ctkm.loaict = 2 then 'On Top' " +  
					 		 "when ctkm.loaict = 3 then 'Tich Luy' end as Program_Type, kh.smartid as Customer_Key, kh.ten as Customer,dh.ngaynhap as Date, " + 
					 		 "isnull(dhtkm.soluong,0) as Piece, dhtkm.tonggiatri  as Amount ,  case when (dhtkm.soluong/qc.soluong1) is null " +
					 		 "then 0 else (dhtkm.soluong/qc.soluong1) end as Quanlity  " +
					 "from donhang_ctkm_trakm dhtkm " +
					 "inner join ctkhuyenmai ctkm on dhtkm.ctkmId = ctkm.pk_seq and substring(ctkm.denngay, 1 , 7)  = '"+ obj.getYear() + "-" + obj.getMonth() + "' " +
					 "inner join donhang dh on dhtkm.donhangId = dh.pk_seq and dh.trangthai='1' and dh.npp_fk = '"+ manpp +"' " +	
					 "inner join nhaphanphoi npp on npp.pk_seq = dh.npp_fk " +
					 "inner join tinhthanh tt on tt.pk_seq = npp.tinhthanh_fk inner join quanhuyen qh on qh.pk_seq = tt.pk_seq " + 
					 "left join sanpham sp on dhtkm.spma = sp.ma left join nhanhang nh on nh.pk_seq = sp.nhanhang_fk " +
					 "left join chungloai cl on cl.pk_seq = sp.chungloai_fk inner join khachhang kh on kh.pk_seq = dh.khachhang_fk " + 
					 "left join quycach qc on qc.sanpham_fk = sp.pk_seq inner join kenhbanhang kbh on kbh.pk_seq = kh.kbh_fk " +					
					 "where isnull(dhtkm.soluong, '0') >= 0 and dh.pk_seq not in " +
					 "( " +
						"select donhang_fk from donhangtrave " + 
						"where donhang_fk is not null and trangthai = '3' and npp_fk = '"+ manpp +"' " + 
		 			 ") " ;
		
		if(obj.getUnghang().equals("0")){
			sql = sql + " and ctkm.kho_fk='100000' ";
		}
				
		System.out.println("Xuat Khuyen Mai NPP: " + sql);
		ResultSet rs = db.get(sql);
		int i = 2;
		if (rs != null) {
			try {
				Cell cell = null;
				while (rs.next())// lap den cuoi bang du lieu
				{
					// lay tu co so du lieu, gan bien
					String Chanel = rs.getString("Channel");
					String PromotionProgram = rs.getString("Program_Program");
					
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
					String DisCode = rs.getString("Distributor_code");
					String ProgramCode = rs.getString("Program_code");
					String ProgramType = rs.getString("Program_Type");

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

					String CustomerKey = rs.getString("Customer_Key");
					String Customer = rs.getString("Customer");
					String Date = rs.getString("Date");
					
					float Piece = rs.getFloat("Piece");

					float Amount = rs.getFloat("Amount");
					
					float Quanlity = rs.getFloat("Quanlity");
					
					cell = cells.getCell("AA" + Integer.toString(i));					cell.setValue(Chanel);
					cell = cells.getCell("AB" + Integer.toString(i));					cell.setValue(ProgramCode);
					cell = cells.getCell("AC" + Integer.toString(i));					cell.setValue(PromotionProgram);
					cell = cells.getCell("AD" + Integer.toString(i));					cell.setValue(ProgramType);
					cell = cells.getCell("AE" + Integer.toString(i));					cell.setValue(SKUCode); 
					cell = cells.getCell("AF" + Integer.toString(i));					cell.setValue(SKU); 
					cell = cells.getCell("AG" + Integer.toString(i));					cell.setValue(Brand);
					cell = cells.getCell("AH" + Integer.toString(i));					cell.setValue(Category);
					cell = cells.getCell("AI" + Integer.toString(i));					cell.setValue(DisCode);
					cell = cells.getCell("AJ" + Integer.toString(i));					cell.setValue(Province);
					cell = cells.getCell("AK" + Integer.toString(i));					cell.setValue(Town);
					cell = cells.getCell("AL" + Integer.toString(i));					cell.setValue(CustomerKey);
					
					cell = cells.getCell("AM" + Integer.toString(i));					
					//cell.setValue("Khong xac dinh");
					cell.setValue(Customer);
					
					cell = cells.getCell("AN" + Integer.toString(i));					cell.setValue(Date);
					cell = cells.getCell("AO" + Integer.toString(i));					cell.setValue(Piece);
					cell = cells.getCell("AP" + Integer.toString(i));					cell.setValue(Amount);
					cell = cells.getCell("AQ" + Integer.toString(i));					cell.setValue(Quanlity);
					i++;
				}
   				

				if (rs != null)
					rs.close();
				if (db != null)
					db.shutDown();
				if(i==2){
					throw new Exception("Xin loi. Khong co bao cao trong thoi gian nay..");
				}
				
/*				PivotTables pivotTables = worksheet.getPivotTables();
				
				String pos = Integer.toString(i - 1); 
				int index = pivotTables.add("=AA1:AQ" + pos, "A8", "BCXuatKM");
	
				PivotTable pivotTable = pivotTables.get(index);

				pivotTable.setRowGrand(true);
				pivotTable.setColumnGrand(true);
				pivotTable.setAutoFormat(true);
				pivotTable.setAutoFormatType(PivotTableAutoFormatType.TABLE10);
				
				Hashtable<String, Integer> selected = new Hashtable<String, Integer>();
				selected.put("Kenh", 0);
				selected.put("MaChuongTrinh", 1);
				selected.put("TenChuongTrinh", 2);
				selected.put("LoaiChuongTrinh", 3);
				selected.put("MaSanPham", 4);
				selected.put("TenSanPham", 5);				
				selected.put("NhanHang", 6);
				selected.put("ChungLoai", 7);
				selected.put("MaNhaPhanPhoi",8);
				selected.put("Tinh/Thanh", 9);
				selected.put("Quan/Huyen", 10);
				selected.put("MaKhachHang", 11);
				selected.put("KhachHang", 12);
				selected.put("Ngay", 13);								
				selected.put("SoLuong(Le)", 14);
				selected.put("SoTien", 15);
				selected.put("SoLuong(Thung)", 16);				
				
				int dataIndex = 0;
				int rowIndex = 0;
				for(i=0;i<obj.getFieldShow().length;++i)
				{
					String str = obj.getFieldShow()[i];
					int value = selected.get(obj.getFieldShow()[i]);
					if(value==14 || value==15 || value==16){
						pivotTable.addFieldToArea(PivotFieldType.DATA, value);
						pivotTable.getDataFields().get(dataIndex).setDisplayName(str);
					    pivotTable.getDataFields().get(dataIndex).setNumber(2);

						dataIndex++;
					}else{
						pivotTable.addFieldToArea(PivotFieldType.ROW, value);
						pivotTable.getRowFields().get(rowIndex).setAutoSubtotals(false);
						rowIndex++;
					}
				}
				
	    		
		    	 pivotTable.addFieldToArea(PivotFieldType.COLUMN, pivotTable.getDataField());
		    	 pivotTable.getColumnFields().get(0).setDisplayName("Data");		
//		    	 setAn(workbook, 49);
				return true;*/
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		} else {
			return false;
		}
		this.setAn(workbook, 49);
		return true;
	}

	private void getCellStyle(Workbook workbook, String a, Color mau, Boolean dam, int size) {
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

	private void setAn(Workbook workbook, int i) {
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();
		for (int j = 26; j <= i; j++) {
			cells.hideColumn(j);
		}

	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
