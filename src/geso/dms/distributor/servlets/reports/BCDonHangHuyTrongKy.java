package geso.dms.distributor.servlets.reports;

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
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTables;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

/**
 * Servlet implementation class BCDonHangHuyTrongKy
 */
public class BCDonHangHuyTrongKy extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String setQuery(HttpServletRequest request,String tungay,String denngay,String st) {
	
		String query =
				  "	select type, t.diachi, isnull(dd.TEN, nv.TEN) nguoixoa, ngayxoa, makh, tenkh, dhid, t.ngayhoadon, masp, tensanpham, soluong, dongia, chietkhau, tongtien, sanluong, scheme " +
	    		  "	from " +
	    		  "	( " +
				  "	select  N'Đơn hàng hủy' as type, kh.DIACHI AS diachi,isnull(dh.NGUOIXOA, dh.nguoisua) as nguoixoa, dh.ngaysua as ngayxoa,"
				+ "			kh.smartid as makh, kh.ten as tenkh, dh.pk_seq as dhId, dh.ngaynhap as ngayhoadon,"
				+ "			sp.ma as masp, sp.ten as tensanpham, dh_sp.soluong as soluong, dh_sp.giamua as dongia,"
				+ "			dh_sp.chietkhau as chietkhau, dh_sp.soluong*dh_sp.giamua - dh_sp.chietkhau  as tongtien,dh_sp.soluong * isnull(sp.trongluong,0) as sanluong  , '' as scheme"
				+ "   from donhang dh "
				+ "		 inner join khachhang kh on kh.pk_seq = dh.khachhang_fk "
				+ "		 inner join donhang_sanpham dh_sp on dh_sp.donhang_fk= dh.pk_seq "
				+ "        inner join sanpham sp on sp.pk_seq = dh_sp.sanpham_fk "
				+ "  where dh.ngaynhap >='"	+ tungay	+ 
				                   "' and dh.ngaynhap <='"+ denngay	+ 
				                   "' and dh.trangthai = 2 "+ st
				+ "        union all"
				+ "		 select  N'Đơn hàng hủy' as type, kh.DIACHI AS diachi,isnull(dh.NGUOIXOA, dh.nguoisua) as nguoixoa, dh.ngaysua as ngayxoa,"
				+ "		 		kh.smartid as makh, kh.ten as tenkh, dh.pk_seq as dhId, dh.ngaynhap as ngayhoadon,"
				+ "		 		spkm.spma as masp, sp.ten as tensp, spkm.soluong as soluong, 0 as dongia, 0 as chietkhau,"
				+ "        		0 as tongtien,spkm.soluong * isnull(sp.trongluong,0) as sanluong , ctkm.scheme as scheme "
				+ "        from 	donhang_ctkm_trakm spkm inner join sanpham sp on spkm.spma=sp.ma"
				+ " 	     		inner join ctkhuyenmai ctkm on ctkm.pk_seq = ctkmid "
				+ " 				inner join donhang dh on dh.pk_seq=donhangid inner "
				+ " 				join khachhang kh on kh.pk_seq = dh.khachhang_fk "
				+ " 		where   dh.ngaynhap >='" + tungay + 
				                    "' and dh.ngaynhap <='" + denngay	+ 
				                    "' and dh.trangthai = 2  "+ st +
					" union all" + 
					" select  N'Đơn hàng thu hồi' as type, kh.DIACHI AS diachi,pth.nguoisua as nguoixoa, pth.ngaysua as ngayxoa, "+
					" kh.smartid as makh, kh.ten as tenkh, dh.pk_seq as dhId, dh.ngaynhap as ngayhoadon, "+
					" sp.ma as masp, sp.ten as tensanpham, " +
					"   pth_sp.soluong as soluong,0 as dongia, 0 as chietkhau,0 as tongtien,pth_sp.soluong  * isnull(sp.trongluong,0) as sanluong , '' as scheme "+
					" from phieuthuhoi pth "+
					" inner join donhang dh on dh.pk_seq= PTH.donhang_fk "+
					" inner join khachhang kh on kh.pk_seq = dh.khachhang_fk "+ 
					" inner join phieuthuhoi_sanpham pth_sp on pth_sp.PTH_FK= pth.pk_seq "+ 
					" inner join sanpham sp on sp.pk_seq = pth_sp.sanpham_fk  "+
					" where dh.ngaynhap >='"+tungay+"' "+
					" and dh.ngaynhap <='"+denngay+"' "+
					"  " +st+
					" union all "+
					" select N'Đơn hàng thu hồi' as type, kh.DIACHI AS diachi,pth.nguoisua as nguoixoa, pth.ngaysua as ngayxoa, "+
					" kh.smartid as makh, kh.ten as tenkh, dh.pk_seq as dhId, dh.ngaynhap as ngayhoadon, "+
					" sp.ma as masp, sp.ten as tensanpham, pth_sp.soluong as soluong,  0 as dongia,  0 as chietkhau, "+
						" 0 as tongtien,pth_sp.soluong  * isnull(sp.trongluong,0) as sanluong ,''  as scheme  "+
					" from phieuthuhoi pth "+
					" inner join donhang dh on dh.pk_seq= PTH.donhang_fk "+
					" inner join khachhang kh on kh.pk_seq = dh.khachhang_fk "+ 
					" inner join phieuthuhoi_spkm pth_sp on pth_sp.PTH_FK= pth.pk_seq "+ 
					" inner join sanpham sp on sp.pk_seq = pth_sp.sanpham_fk  "+
					" where dh.ngaynhap >='"+tungay+"' "+
					" and dh.ngaynhap <='"+denngay+"' "+
					"  " +st+ "" +
				    " ) as t " +
				    " left join nhanvien nv on nv.PK_SEQ = t.nguoixoa " +
				    " left join DAIDIENKINHDOANH dd on dd.PK_SEQ = t.nguoixoa " +
					" order by dhId, scheme	" ;
		System.out.println("Get Don hang Huy : "+query);
		return query;
	}

	

	public BCDonHangHuyTrongKy() {
		super();

	}

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
			Ireport obj = new Report();

	    Utility util = new Utility();
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	//    System.out.println(userId);
	    obj.setuserId(userId);
	    obj.init();	    
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Distributor/BCDonHangHuyTrongKy.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		Ireport obj = new Report();
		Utility util = new Utility();
		String userId = (String) session.getAttribute("userId");  
	    String userTen = (String) session.getAttribute("userTen"); 

	    if(userId ==null)
	    	userId ="";
	    obj.setuserId(userId);
	    
	    
   	 	String nppId=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
   	 	if(nppId ==null) 
   	 		nppId ="";
   	 	obj.setnppId(nppId);
   	 
	   	 
	   	 String ddkdId=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdId")));
	   	 if(ddkdId == null)
	   		ddkdId ="";
	   obj.setddkdId(ddkdId);
	   	 
	   	 String khachhangId=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khachhangId")));
	   	 if(khachhangId == null)
	   		khachhangId ="";
	   	 obj.setkhachhangId(khachhangId);
	   	 
	   	String tungay=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays")));
	   	 if(tungay ==null)
	   		 tungay ="";
	   	 obj.settungay(tungay);
	   	 
	   	 String denngay=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays")));
	   	 if(denngay == null)
	   		 denngay ="";
	   	 obj.setdenngay(denngay);
	   	geso.dms.distributor.util.Utility Ult = new  geso.dms.distributor.util.Utility();
	   	  nppId = Ult.getIdNhapp(userId);
	   	
	   	 String st=" and dh.npp_fk ='"+ nppId +"'";
	   		 if(ddkdId.length()>0)
	   			 st =" and dh.ddkd_fk ='"+ ddkdId +"'";
	   		 if(khachhangId.length()>0)
	   			 st = st +" and dh.khachhang_fk ='"+ khachhangId +"'";
		 String []fieldsHien = request.getParameterValues("fieldsHien");
		 obj.setFieldShow(fieldsHien);
		 
		 String [] fieldsAn =request.getParameterValues("fieldsAn");
			obj.setFieldHidden(fieldsAn)  ;
		 
			String action=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			 if(action.equals("tao"))
			 { 
			try {
					request.setCharacterEncoding("utf-8");
					response.setCharacterEncoding("utf-8");
					//response.setContentType("application/vnd.ms-excel");
					//response.setHeader("Content-Disposition","attachment; filename=BCDonHangHuyTrongKyNPP.xls");
					response.setContentType("application/xlsm");
			        response.setHeader("Content-Disposition", "attachment; filename=BaoCaoRotDonHang.xlsm");
			        
					OutputStream out = response.getOutputStream();
					
					String query = setQuery(request,tungay,denngay,st);								
					CreatePivotTable(out,fieldsHien,obj,query); 
		
				} catch (Exception ex) {
					request.getSession().setAttribute("errors", ex.getMessage());
				}
			 }
			 	obj.init();	    
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
				String nextJSP = "/AHF/pages/Distributor/BCDonHangHuyTrongKy.jsp";
				response.sendRedirect(nextJSP);
	}

	private void CreatePivotTable(OutputStream out,String[]manghien,Ireport obj, String query) throws Exception {
		try {
			
			//String chuoi=getServletContext().getInitParameter("path") + "\\BaoCaoRotDonHang.xlsm";
			
			//FileInputStream fstream = new FileInputStream(chuoi);
			
			//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\BaoCaoRotDonHang.xlsm");
			File f= new File(getServletContext().getInitParameter("path")+"\\BaoCaoRotDonHang.xlsm");
			FileInputStream fstream = new FileInputStream(f) ;
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			CreateHeader(workbook,obj);
			FillData(workbook,manghien,query); // 
			workbook.save(out);
			fstream.close();
			
		} catch (Exception ex) {
			System.out.println("Error : "+ex.toString());
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}
	public String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
         Date date = new Date();
         return dateFormat.format(date);
		
	}

	private void CreateHeader(Workbook workbook,Ireport obj) {
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);	    
	    worksheet.setName("Sheet1");
	    Cells cells = worksheet.getCells();	    
	    cells.setRowHeight(0, 50.0f);	    
	    Cell cell = cells.getCell("A1");
	  
	    ReportAPI.getCellStyle(cell,Color.RED, true, 16, "BÁO CÁO ĐƠN HÀNG HỦY TRONG KỲ");
	    cell = cells.getCell("A2");
	    ReportAPI.getCellStyle(cell,Color.BLUE,true,10,"Từ ngày : " +  obj.gettungay() + "  Đến ngày : " +obj.getdenngay());
	    cell = cells.getCell("A3");
	    ReportAPI.getCellStyle(cell,Color.BLUE,true,10,"Ngày tạo : " + getDateTime()); 
	    cell = cells.getCell("A4");
	    ReportAPI.getCellStyle(cell,Color.BLUE,true,10,"Tạo bởi : " + obj.getuserTen());
	    
	    
	    
	    cell = cells.getCell("AA1");		cell.setValue("Nguoi Xoa");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AB1");		cell.setValue("Ngay Xoa");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AC1");		cell.setValue("Ten Khach Hang");		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AD1");		cell.setValue("Ma Don Hang");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AE1");		cell.setValue("Ngay Hoa Don");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AF1");		cell.setValue("Ten San Pham");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AG1");		cell.setValue("Ma Khach Hang");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AH1");		cell.setValue("Dia Chi");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AI1");		cell.setValue("Ma San Pham");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AJ1");		cell.setValue("CTKM");					ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AK1");		cell.setValue("Chiet Khau");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AL1");		cell.setValue("So Luong");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AM1");		cell.setValue("Don Gia");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AN1");		cell.setValue("Tong Tien");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AO1");		cell.setValue("Loai Don Hang");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AP1");		cell.setValue("TrongLuong");				ReportAPI.setCellHeader(cell);
		
	    
	}
	private void FillData(Workbook workbook,String[] manghien, String query) throws Exception {
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		
		for(int i=0;i<20;++i){
	    	cells.setColumnWidth(i, 15.0f);	    	
	    }	
		dbutils db = new dbutils();
		ResultSet rs = db.get(query);
		int index = 2;
	    Cell cell = null;	    
		while (rs.next()) {
			cell = cells.getCell("AA" + String.valueOf(index));		cell.setValue(rs.getString("nguoixoa"));			
			cell = cells.getCell("AB" + String.valueOf(index));		cell.setValue(rs.getString("ngayxoa"));
			cell = cells.getCell("AC" + String.valueOf(index));		cell.setValue(rs.getString("tenkh"));
			cell = cells.getCell("AD" + String.valueOf(index));		cell.setValue(rs.getString("dhId"));
			cell = cells.getCell("AE" + String.valueOf(index));		cell.setValue(rs.getString("ngayhoadon"));
			cell = cells.getCell("AF" + String.valueOf(index));		cell.setValue(rs.getString("tensanpham"));
			cell = cells.getCell("AG" + String.valueOf(index));		cell.setValue(rs.getString("makh"));
			cell = cells.getCell("AH" + String.valueOf(index));		cell.setValue(rs.getString("diachi"));
			cell = cells.getCell("AI" + String.valueOf(index));		cell.setValue(rs.getString("masp"));
			cell = cells.getCell("AJ" + String.valueOf(index));		cell.setValue(rs.getString("scheme"));
			cell = cells.getCell("AK" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("chietkhau")));
			cell = cells.getCell("AL" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("soluong")));	 
			cell = cells.getCell("AM" + String.valueOf(index));		 cell.setValue(Float.parseFloat(rs.getString("dongia")));	 
			cell = cells.getCell("AN" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("tongtien")));
			cell = cells.getCell("AO" + String.valueOf(index));		cell.setValue(rs.getString("type"));
			cell = cells.getCell("AP" + String.valueOf(index));		cell.setValue(rs.getFloat("sanluong"));
			index++;
		}
		if(rs!=null){
				rs.close();
		}
		if(db!=null){
			db.shutDown();
		}
	
	
	}
}
