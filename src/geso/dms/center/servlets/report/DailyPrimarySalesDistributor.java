package geso.dms.center.servlets.report;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;

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


public class DailyPrimarySalesDistributor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public DailyPrimarySalesDistributor() {
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
		
		obj.setdiscount("1");
		obj.setvat("1");
		obj.init();
		
		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Distributor/DailyPrimarySalesDistributor.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		obj.setdiscount("1");
		obj.setvat("1");
		
		OutputStream out = response.getOutputStream();
		
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		obj.setuserTen(userTen);
	    if(userId == null)
	    	userId = "";
	    obj.setuserId(userId);
	    
		Utility util = new Utility();
		 obj.setnppId(util.getIdNhapp(obj.getuserId())); 
		obj.settungay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays"))));
		obj.setdenngay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays"))));
		
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");
		
		obj.setkenhId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId"))!=null? util.antiSQLInspection(request.getParameter("kenhId")):""));
	   	 
		
		
		obj.setvungId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"))!=null? 
						util.antiSQLInspection(request.getParameter("vungId")):""));
	   	 
		
	
		obj.setkhuvucId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"))!=null? 
				util.antiSQLInspection(request.getParameter("khuvucId")):""));
	 
		
		obj.setgsbhId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gsbhs"))!=null? 
				util.antiSQLInspection(request.getParameter("gsbhs")):""));
	 
		
		obj.setdvkdId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId"))!=null? 
				util.antiSQLInspection(request.getParameter("dvkdId")):""));
	 
		
		obj.setnhanhangId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhanhangId"))!=null? 
				util.antiSQLInspection(request.getParameter("nhanhangId")):""));
	 
		
		obj.setchungloaiId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chungloaiId"))!=null? 
				util.antiSQLInspection(request.getParameter("chungloaiId")):""));
	 
		
		/*String vat = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vats")));
		if (vat.equals("1"))
			obj.setvat("1.05");
		else
			obj.setvat("1");
		
		String dsc = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("discount")));
		if (dsc.equals("1"))
			obj.setdiscount("1");
		else
			obj.setdiscount("0");
		
		String promotion = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("promotion"));
		if(promotion == null)
			promotion = "0";
		obj.setpromotion(promotion);*/
		
		String xemtheo = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("xemtheo"));
		System.out.println("Xem theo: " + xemtheo + "\n");
		if(xemtheo.equals("1")) //xem theo thang
		{
			String tuthang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tuthang"));
			if(tuthang == null)
				tuthang = "";
			obj.setFromMonth((tuthang.length() >1?tuthang:"0"+tuthang));
			
			
			String denthang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denthang"));
			if(denthang == null)
				denthang = "";
			obj.setToMonth( (denthang.length() >1?denthang:"0"+denthang));
			
			
			String tunam = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tunam"));
			if(tunam == null)
				tunam = "";
			obj.setFromYear(tunam);
			
			
			
			String dennam = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dennam"));
			if(dennam == null)
				dennam = "";
			obj.setToYear(dennam);
			
		}
		
		String[] fieldsHien = request.getParameterValues("fieldsHien");
		obj.setFieldShow(fieldsHien);
		
		String sql = " and npp.PK_SEQ='"+obj.getnppId()+"'" ;
		
		if (obj.getkenhId().length() > 0)
			sql = sql + " and kbh.pk_seq ='" + obj.getkenhId() + "'";
		if (obj.getvungId().length() > 0)
			sql = sql + " and v.pk_seq ='" + obj.getvungId() + "'";
		if (obj.getkhuvucId().length() > 0)
			sql = sql + " and kv.pk_seq ='" + obj.getkhuvucId() + "'";
		if (obj.getdvkdId().length() > 0)
			sql = sql + " and dvkd.PK_SEQ ='" + obj.getdvkdId() + "'";
		if (obj.getnhanhangId().length() > 0)
			sql = sql + " and kbh.pk_seq ='" + obj.getnhanhangId() + "'";
		if (obj.getchungloaiId().length() > 0)
			sql = sql + " and cl.pk_seq ='" + obj.getchungloaiId()	+ "'";
		
		System.out.println("SQL la: " + sql + "\n");
		
		/*if(Integer.parseInt(promotion) == 0) // khong lay khuyen mai + trung bay
		{
			sql = sql + " and nhsp.gianet > 0  ";
		}*/
		String gia ="nhsp.gianet";
		/*if(Integer.parseInt(dsc) > 0) 
			gia = "nhsp.GIAGROSS";*/
		
		String action = (String) util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));
		String nextJSP = "/AHF/pages/Distributor/DailyPrimarySalesDistributor.jsp";
		
		System.out.println("Action la: " + action);
		if (action.equals("Taomoi")) 
		{			
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=MuaHangHangNgay.xlsm");
			
			String query = setQuery(obj, sql, gia); 

	        try 
	        {
				if(!CreatePivotTable(out, response, request, obj, query))
				{
					response.setContentType("text/html");
				    PrintWriter writer = new PrintWriter(out);
				    writer.print("Xin loi khong co bao cao trong thoi gian nay");
				    writer.close();
				}
			} 
	        catch (Exception e) 
	        
	        {
				obj.setMsg("Khong the tao bao cao " + e.getMessage());
			}
		}
		
		obj.init();
		session.setAttribute("obj", obj);
		response.sendRedirect(nextJSP);
		return;
	}	
	
	public String setQuery(IStockintransit obj, String sql, String gia)
	{
		 String query = "";
		 if(obj.getFromMonth().length() <= 0) //xem theo ngay
		 {
			query = "\n select " +
		    		"\n kbh.ten as Channel,v.ten as Region, kv.ten as Area,nhan.ten as Brands,cl.ten as Category,nh.ngaydonhang as Shipdate," +
		    		"\n npp.sitecode as Distributor_code, npp.ten as Distributor,tt.ten as Province,nh.pk_seq as Docno,nh.pk_seq as Distcode," +
		    		"\n nhsp.sanpham_fk as SKU_code,sp.ten as SKU, DVKD.DONVIKINHDOANH, nhsp.soluong as Peice, " +
		    		"\n nhsp.soluong * nhsp.dongiagoc as Amount, " +
		    		"\n case nh.trangthai when 0 then N'Chưa chốt' "+
		    		"\n 	when 1 then N'Chờ duyệt' "+
		    		"\n 	when 2 then N'Đã duyệt' "+
		    		"\n 	when 3 then N'Đã hủy' "+
		    		"\n 	when 4 then N'Hoàn tất' end as trangthai "+
		    		"\n from ERP_DONDATHANG nh " +
					"\n inner join ERP_DONDATHANG_SANPHAM nhsp on nh.pk_seq = nhsp.dondathang_fk " +
					"\n inner join sanpham sp on sp.pk_seq = nhsp.sanpham_fk " +
					"\n INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK " +
					"\n inner join nhaphanphoi npp on npp.pk_seq = nh.npp_fk " +
					"\n left join khuvuc kv on kv.pk_seq = npp.khuvuc_fk " +
					"\n left join vung v on v.pk_seq = kv.vung_fk " +
					"\n left join nhanhang nhan on nhan.pk_seq = sp.nhanhang_fk " +
					"\n left join chungloai cl on cl.pk_seq = sp.chungloai_fk " +
					"\n left join tinhthanh tt on tt.pk_seq = npp.tinhthanh_fk " +
					"\n left join kenhbanhang kbh on kbh.pk_seq = nh.kbh_fk where nh.loaidonhang='0' " ;
			if(obj.gettungay().length() > 0)
				query += "\n and nh.ngaydonhang >= '" + obj.gettungay() + "'";
			if(obj.getdenngay().length() > 0)
				query += "\n and nh.ngaydonhang <= '" + obj.getdenngay() + "'";
		}
		else  //xem theo thang
		{
			query = "\n select kbh.ten as Channel,v.ten as Region, kv.ten as Area,nhan.ten as Brands,cl.ten as Category, substring(nh.ngaydonhang, 1, 7) as Shipdate," +
		    		"\n npp.sitecode as Distributor_code, npp.ten as Distributor,tt.ten as Province,nh.pk_seq as Docno,nh.pk_seq as Distcode," +
		    		"\n nhsp.sanpham_fk as SKU_code,sp.ten as SKU, DVKD.DONVIKINHDOANH,  " +
		    		"\n sum(cast(nhsp.soluong as int)) as Peice,"+
		    		"\n case nh.trangthai when 0 then N'Chưa chốt' "+
		    		"\n 	when 1 then N'Chờ duyệt' "+
		    		"\n 	when 2 then N'Đã duyệt' "+
		    		"\n 	when 3 then N'Đã hủy' "+
		    		"\n 	when 4 then N'Hoàn tất' end as trangthai, "+
		    		"\n  nhsp.soluong * nhsp.dongiagoc as Amount " ;
		    		/*if(obj.getvat().equals("1")){
		    			query=query+ " sum(nhsp.soluong * " + gia + " ) as Amount " ;
		    		}else{
		    			query=query+ " sum(nhsp.soluong * " + gia + " + nhsp.vat ) as Amount " ;
		    		}*/
		    		query=query+	"\n from ERP_DONDATHANG nh " +
					"\n inner join ERP_DONDATHANG_SANPHAM nhsp on nh.pk_seq = nhsp.dondathang_fk " +
					"\n inner join sanpham sp on sp.pk_seq = nhsp.sanpham_fk " +
					"\n INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK " +
					"\n inner join nhaphanphoi npp on npp.pk_seq = nh.npp_fk " +
					"\n left join khuvuc kv on kv.pk_seq = npp.khuvuc_fk " +
					"\n left join vung v on v.pk_seq = kv.vung_fk " +
					"\n left join nhanhang nhan on nhan.pk_seq = sp.nhanhang_fk " +
					"\n left join chungloai cl on cl.pk_seq = sp.chungloai_fk " +
					"\n left join tinhthanh tt on tt.pk_seq = npp.tinhthanh_fk " +
					"\n left join kenhbanhang kbh on kbh.pk_seq = nh.kbh_fk " +
					"\n where nh.loaidonhang='0' ";
			
			if(obj.getFromMonth().length() > 0)
				query += "\n and nh.ngaydonhang>= '"+obj.getFromYear()+"-" + obj.getFromMonth() + "-01'";
			if(obj.getToMonth().length() > 0)
				query += "\n and nh.ngaydonhang <= '" +obj.getToYear()+"-"+ obj.getToMonth() + "-31'";
			
		}
    	
	    if(query.length() > 0)
	    	query = query + sql;
	    
	    if(obj.getFromMonth().length() > 0)
	    {
		   query += "\n group by kbh.ten, v.ten, kv.ten, nhan.ten, cl.ten, substring(nh.ngaychungtu, 1, 7), npp.sitecode, npp.ten, tt.ten, " +
		   		"\n nh.chungtu, nh.pk_seq, nhsp.sanpham_fk, sp.ten, DVKD.DONVIKINHDOANH";
	    }
	    
	    System.out.println("Query Mua hang hang ngay: " + query);
	    return query;   
	}
	
	private boolean CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj, String query) throws Exception 
	{
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		
		if(obj.getFromMonth().length() <= 0) //Xem theo Ngay
		{			 
			String chuoi = getServletContext().getInitParameter("path") + "\\DailyPrimarySalesDistributor.xlsm";
			fstream = new FileInputStream(chuoi);
		/*	File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\DailyPrimarySalesDistributor.xlsm");
			fstream = new FileInputStream(f) ;*/
		}
		else
		{
			String chuoi = getServletContext().getInitParameter("path") + "\\DailyPrimarySalesDistributor_Thang.xlsm";
			fstream = new FileInputStream(chuoi);
			/*File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\DailyPrimarySalesDistributor_Thang.xlsm");
			fstream = new FileInputStream(f) ;*/
		}
				
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateHeader(workbook,obj);
		isFillData = FillData(workbook, obj, query);
   
		if (!isFillData)
		{
			if(fstream != null)
				fstream.close();
			return false;
		}
		
		workbook.save(out);
		fstream.close();
		return true;	
	}
	
	private void CreateHeader(Workbook workbook, IStockintransit obj)throws Exception
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
	    
	    String tieude = "BÁO CÁO DOANH SỐ MUA HÀNG THEO NGÀY";
	    if(obj.getFromMonth().length() > 0)
	    	tieude = "BÁO CÁO DOANH SỐ MUA HÀNG THEO THÁNG";
	    ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);
	            
	    String message = "";
		if(obj.getdiscount().equals("0"))
		{
			message += "Không chiết khấu";

			if(obj.getvat().equals("1"))
			{
				message += ", không VAT";
			}
			else
			{
				message += ", có VAT";
			}			
		}
		else
		{
			message += "Có chiết khấu";
			if(obj.getvat().equals("1"))
			{
				message += ", không VAT";
			}
			else
			{
				message +=", có VAT";
			}
		}
		
		 if(Integer.parseInt(obj.getpromotion()) == 0) // khng lay khuyen mai + trung bay
		 {
			 message += ", không khuyến mại";
		 }
		 else
		 {
			 message += ", có khuyến mại";
		 }
		
		cells.setRowHeight(2, 18.0f);
		cell = cells.getCell("A2");
		ReportAPI.getCellStyle(cell, Color.RED, true, 9, message);   

	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("A4");
	    
	    if(obj.getFromMonth() == "")
	    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ ngày : " + obj.gettungay() + "" );
	    else
	    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ tháng : " + obj.getFromMonth() + "" );
	    
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("B4"); 
	    if(obj.getFromMonth() == "")
	    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến ngày : " + obj.getdenngay() + "" );
	    else
	    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến tháng : " + obj.getToMonth() + "" );
		
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A5");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A6");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());
	    		
		cell = cells.getCell("DA1");		cell.setValue("KenhBanHang");			ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("DB1");		cell.setValue("DonViKinhDoanh");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("DC1");		cell.setValue("ChiNhanh");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("DD1");		cell.setValue("KhuVuc");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("DE1");		cell.setValue("MaNhaPhanPhoi");					ReportAPI.setCellHeader(cell);
		cell = cells.getCell("DF1");		cell.setValue("NhaPhanPhoi");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("DG1");		cell.setValue("NhanHang");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("DH1");		cell.setValue("ChungLoai");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("DI1");		cell.setValue("SoChungTu");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("DJ1");		cell.setValue("MaSanPham");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("DK1");		cell.setValue("TenSanPham");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("DL1");		cell.setValue("TinhThanh");		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("DM1");
		
		if(obj.getFromMonth() != "")
			cell.setValue("Thang");
		else
			cell.setValue("NgayChungTu");
		
		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("DN1");		cell.setValue("SoLuongLe");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("DO1");		cell.setValue("TongTien");				ReportAPI.setCellHeader(cell);
		//cell = cells.getCell("DP1");		cell.setValue("SanLuong(Kg)");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("DP1");		cell.setValue("TrangThai");				ReportAPI.setCellHeader(cell);
	}
	
	private boolean FillData(Workbook workbook, IStockintransit obj, String query) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		
		Cells cells = worksheet.getCells();	
		
		for(int i = 0;i < 7; ++i)
		{
	    	cells.setColumnWidth(i, 15.0f);
	    	if(i == 4)
	    		cells.setColumnWidth(i, 30.0f);
	    }	
		
		ResultSet rs = db.get(query);
		int index = 2;
		
		if (rs != null) 
		{
			try 
			{
				Cell cell = null;
				while (rs.next())
				{					
					cell = cells.getCell("DA" + String.valueOf(index));		cell.setValue(rs.getString("Channel"));	//Kenh ban hang  0	
					cell = cells.getCell("DB" + String.valueOf(index));		cell.setValue(rs.getString("DonViKinhDoanh"));	//Kenh ban hang  1	
					cell = cells.getCell("DC" + String.valueOf(index));		cell.setValue(rs.getString("Region"));	//Vung/Mien  	2
					cell = cells.getCell("DD" + String.valueOf(index));		cell.setValue(rs.getString("Area"));		//khu vuc    3
					cell = cells.getCell("DE" + String.valueOf(index));		cell.setValue(rs.getString("Distributor_code"));	//   4
					cell = cells.getCell("DF" + String.valueOf(index));		cell.setValue(rs.getString("Distributor"));	// 5
					cell = cells.getCell("DG" + String.valueOf(index));		cell.setValue(rs.getString("Brands"));	//Nhan hang   	6
					cell = cells.getCell("DH" + String.valueOf(index));		cell.setValue(rs.getString("Category"));	//Chung loai 7
					cell = cells.getCell("DI" + String.valueOf(index));		cell.setValue(rs.getString("Docno"));	//8
					cell = cells.getCell("DJ" + String.valueOf(index));		cell.setValue(rs.getString("SKU_code"));				//Ma san pham 9
					cell = cells.getCell("DK" + String.valueOf(index));		cell.setValue(rs.getString("SKU"));	//Ten san pham   10
					cell = cells.getCell("DL" + String.valueOf(index));		cell.setValue(rs.getString("Province"));	//Tinh thanh 11
					cell = cells.getCell("DM" + String.valueOf(index));		cell.setValue(rs.getString("Shipdate"));	//So Tien    12
					cell = cells.getCell("DN" + String.valueOf(index));		cell.setValue(rs.getFloat("Peice"));	//13
					cell = cells.getCell("DO" + String.valueOf(index));		cell.setValue(rs.getFloat("Amount")); //14
					//cell = cells.getCell("DP" + String.valueOf(index));		cell.setValue(rs.getFloat("sanluong")); //14
					cell = cells.getCell("DP" + String.valueOf(index));		cell.setValue(rs.getString("trangthai")); //14
					
					index++;					
				}

				if (rs != null)
					rs.close();
				
				if(db != null)
					db.shutDown();
			}
			catch(Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}
		else
		{
			return false;
		}		
		return true;
	}	
	
	
}
