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


public class DailySecondarySalesTT_focusedSKUSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DailySecondarySalesTT_focusedSKUSvl() {
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
		session.setAttribute("checkedSKU", "");
		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Center/DailySecondarySalesReportCenter_focusedSKU.jsp";
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
		
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		Utility util = new Utility();
		
		obj.settungay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays"))));
		obj.setdenngay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays"))));
		
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");
		obj.setkenhId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId"))):"");
		
		obj.setvungId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"))):"");
			
		obj.setkhuvucId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"))):"");
		
		obj.setgsbhId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gsbhs")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gsbhs"))):"");
		
		obj.setnppId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"))):"");
		
		obj.setdvkdId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId"))):"");
		
		String nspId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nspId"))!=null?util.antiSQLInspection(request.getParameter("nspId")):"");
		obj.SetNhoSPId(nspId);
				
		String vat = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vats")));
		if (vat.equals("1"))
			obj.setvat("1.1");
		else
			obj.setvat("1");
		String dsc = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("discount")));
		if (dsc.equals("1"))
			obj.setdiscount("1");
		else
			obj.setdiscount("0");		
		
		
		geso.dms.center.util.Utility utilcenter = new geso.dms.center.util.Utility();
		
		String sql = " where npp.pk_seq in " + utilcenter.quyen_npp(obj.getuserId()) + " and kbh.pk_seq in  " + utilcenter.quyen_kenh(obj.getuserId());
		if (obj.getkenhId().length() > 0)
			sql = sql + " and kbh.pk_seq ='" + obj.getkenhId() + "'";
		if (obj.getvungId().length() > 0)
			sql = sql + " and v.pk_seq ='" + obj.getvungId() + "'";
		if (obj.getkhuvucId().length() > 0)
			sql = sql + " and kv.pk_seq ='" + obj.getkhuvucId() + "'";
		if (obj.getdvkdId().length() > 0)
			sql = sql + " and dvkd.PK_SEQ ='" + obj.getdvkdId() + "'";
		if (obj.getnppId().length() > 0)
			sql = sql + " and npp.pk_seq ='" + obj.getnppId() + "'";
		if (obj.getnhanhangId().length() > 0)
			sql = sql + " and kbh.pk_seq ='" + obj.getnhanhangId() + "'";
		if (obj.getchungloaiId().length() > 0)
			sql = sql + " and cl.pk_seq ='" + obj.getchungloaiId()	+ "'";

		String[] skutest =request.getParameterValues("skutest");
	 	String[] valuechecked=request.getParameterValues("valuechecked");
	 	String whereNSP = "";
	 	String	chuoichuyen="";
	 	if(valuechecked!=null)
	 	{
	 		if(valuechecked.length>0)
	 		for(int i= 0; i<skutest.length;i++ ){
	 		if(valuechecked[i].equals("1")){
	 			whereNSP+= skutest[i]+ ",";
	 			chuoichuyen=chuoichuyen + ","+ skutest[i];
	 		}
	 		
	 		}
	 	}
	 		if(whereNSP !="")	 		
	 			whereNSP =whereNSP.substring(0, whereNSP.length()-1);
	 		
		obj.SetNhoSPId(whereNSP);
		System.out.println("NHom San Pham :"+whereNSP);
		session.setAttribute("checkedSKU",whereNSP);
		if(obj.GetNhoSPId().length() > 0){
			 sql += " and NSP.PK_SEQ in (" + obj.GetNhoSPId() + ")" ;
		 }
		
		System.out.println("SQL la: " + sql + "\n");
		
		String action = (String) util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));
		String nextJSP = "/AHF/pages/Center/DailySecondarySalesReportCenter_focusedSKU.jsp";
		
		System.out.println("Action la: " + action);
		
		try{
			if (action.equals("Taomoi")) 
			{			
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=DoanhSoTT_SKUFocused.xlsm");
				
				String query = setQuery(sql, obj);
				System.out.println("Query lay du lieu: " + query + "\n");
		        
				if(!CreatePivotTable(out,obj,query))
		        {
		        	obj.setMsg("Không có dữ liệu trong thời gian này");

		        }
			}
			else
			{
				response.sendRedirect(nextJSP);
			}
		}
		catch(Exception ex)
		{
			System.out.println("Xay ra exception roi..." + ex.toString());
			obj.setMsg(ex.getMessage());
			response.sendRedirect(nextJSP);
		}
		obj.init();
		session.setAttribute("obj", obj);	
	}	
	
	private String setQuery(String sql, IStockintransit obj) {
		String	Customer = "";
		/*
		 * Kiem tra ngay bat dau tao bao cao,neu ngay bat dau >2012-04-01 thi lay co giam sat
		 */
		
		Customer = "SELECT	dvkd.donvikinhdoanh as BU, V.TEN AS REGION, V.pk_seq as regionId, KV.TEN AS AREA, KV.PK_seq as areaId, NPP.TEN AS DISTRIBUTOR, NPP.SITECODE AS DISTCODE, NPP.pk_seq as nppId, " +
					"NH.TEN AS BRAND, NH.PK_SEQ as BRANDID, CL.TEN AS CATEGORY, CL.pk_seq as CATEGORYID, SP.MA + '_' + SP.TEN AS SKU, SP.MA AS SKUCODE, DDKD.TEN AS SALESREP, DDKD.pk_seq as SALESREPID, " +
					"NPP.SITECODE + '_' + KH.TEN + '( ' + KH.DIACHI +  ' )' AS CUSTOMER, KH.SMARTID AS CUSTOMERCODE, case when CHARINDEX('_', kh.smartid) > 0 then rtrim(substring(kh.smartid, CHARINDEX('_', kh.smartid)+1, 10)) + npp.sitecode " +
					"WHEN CHARINDEX('_', kh.smartid) <= 0 then kh.smartid + '-' + npp.sitecode end as smartid, " +
					"VTCH.VITRI AS OUTLETPOSITION, ISNULL(M.DIENGIAI, 'Chua Xac Dinh') AS OUTLETTYPE, KBH.DIENGIAI AS CHANEL, KBH.pk_seq as CHANELID, " +
					"DH.NGAYNHAP AS OFFDATE, DH.SOLUONG AS PIECE, ";
					
					 if(obj.getdiscount().equals("1"))
				    	 Customer +=" dh.giamua * dh.soluong * " + obj.getvat() + " as amount ,";
				     else
				    	 Customer +=" dh.giamua * dh.soluong * " + obj.getvat()+" - dhsp.chietkhau  as amount ,";
	
		Customer += "ISNULL(GSBH.TEN, 'Chua xac dinh') AS SALESUPER, ISNULL(ROUND((CAST((QC.SOLUONG2 * DH.SOLUONG) AS FLOAT) / CAST(QC.SOLUONG1 AS FLOAT)),3), 0) AS QUANTITY, " +
					"ISNULL(QH.TEN, 'Chua xac dinh') AS TOWN, ISNULL(TT.TEN, 'Chua xac dinh') AS PROVINCE, HCH.DIENGIAI AS OUTLETCLASS, NKH.DIENGIAI AS NHOMKHACHHANG, NSP.DIENGIAI AS NHOMSANPHAM " +
					"FROM( SELECT  DH.NGAYNHAP, DH.DDKD_FK, DH.KHACHHANG_FK, DH.NPP_FK, DH.GSBH_FK, DH.KHO_FK, DH.KBH_FK, ISNULL(DH_SP.SANPHAM_FK, DH_SP1.SANPHAM_FK) AS SANPHAM_FK, " +
					"ISNULL(DH_SP.GIAMUA, DH_SP1.GIAMUA) AS GIAMUA , (-1)*ISNULL(DH_SP.SOLUONG, DH_SP1.SOLUONG) AS SOLUONG, " +
					"(-1)*ISNULL(DH_SP1.CHIETKHAU, 0) AS CHIETKHAU  FROM  DONHANGTRAVE DH LEFT OUTER JOIN  DONHANGTRAVE_SANPHAM DH_SP ON DH_SP.DONHANGTRAVE_FK = DH.PK_SEQ " +
					"LEFT OUTER JOIN  DONHANG_SANPHAM DH_SP1 ON DH.DONHANG_FK = DH_SP1.DONHANG_FK  " +
					"WHERE DH.TRANGTHAI = 3 AND  DH.NGAYNHAP >='" + obj.gettungay() + "' AND DH.NGAYNHAP <= '" + obj.getdenngay() + "' " +
					"UNION ALL SELECT  DH.NGAYNHAP, DH.DDKD_FK, DH.KHACHHANG_FK, DH.NPP_FK, DH.GSBH_FK, DH.KHO_FK, DH.KBH_FK, DH_SP.SANPHAM_FK, DH_SP.GIAMUA, " +
					"DH_SP.SOLUONG, DH_SP.CHIETKHAU  FROM DONHANG DH  INNER JOIN DONHANG_SANPHAM  DH_SP ON DH.PK_SEQ = DH_SP.DONHANG_FK WHERE DH.TRANGTHAI = 1 " +
					"AND DH.NGAYNHAP >='" + obj.gettungay() + "' AND DH.NGAYNHAP <= '" + obj.getdenngay() + "' ) DH INNER JOIN SANPHAM SP ON DH.SANPHAM_FK = SP.PK_SEQ " +
					"INNER JOIN NHAPHANPHOI NPP ON DH.NPP_FK = NPP.PK_SEQ INNER JOIN KHUVUC KV ON NPP.KHUVUC_FK = KV.PK_SEQ INNER JOIN VUNG V ON KV.VUNG_FK = V.PK_SEQ " +
					"INNER JOIN NHANHANG NH ON SP.NHANHANG_FK = NH.PK_SEQ LEFT JOIN CHUNGLOAI CL ON SP.CHUNGLOAI_FK = CL.PK_SEQ " +
					"INNER JOIN DAIDIENKINHDOANH DDKD ON DH.DDKD_FK = DDKD.PK_SEQ INNER JOIN KHACHHANG KH ON DH.KHACHHANG_FK = KH.PK_SEQ " +
					"LEFT JOIN VITRICUAHANG VTCH ON KH.VTCH_FK = VTCH.PK_SEQ INNER JOIN KENHBANHANG KBH ON DH.KBH_FK = KBH.PK_SEQ " +
					"LEFT JOIN LOAICUAHANG M ON KH.LCH_FK = M.PK_SEQ LEFT JOIN GIAMSATBANHANG GSBH ON DH.GSBH_FK = GSBH.PK_SEQ " +
					"INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK = SP.PK_SEQ " +
					"LEFT JOIN QUANHUYEN QH ON KH.QUANHUYEN_FK = QH.PK_SEQ LEFT JOIN TINHTHANH TT ON KH.TINHTHANH_FK = TT.PK_SEQ " +
					"LEFT JOIN HANGCUAHANG HCH ON KH.HCH_FK = HCH.PK_SEQ LEFT JOIN NHOMKHACHHANG_KHACHHANG NKHKH ON NKHKH.KH_FK = KH.PK_SEQ " +
					"LEFT JOIN NHOMKHACHHANG NKH ON NKH.PK_SEQ = NKHKH.NKH_FK " +
					"LEFT JOIN " + 
					"( " +
							"SELECT SP_FK, MAX(NSP_FK) AS NHOMSP " + 
							"FROM NHOMSANPHAM_SANPHAM  GROUP BY SP_FK " +
					") NSPSP ON NSPSP.SP_FK = SP.PK_SEQ " + 
					"LEFT JOIN NHOMSANPHAM NSP ON NSP.PK_SEQ = NSPSP.NHOMSP " +  sql + 
					"ORDER BY DH.NGAYNHAP";
					
		 
	     return Customer;			
		
	}	
	
	private boolean CreatePivotTable(OutputStream out, IStockintransit obj, String query)throws Exception 
	{
		boolean isFillData = false;
		
		
		
		Workbook workbook = new Workbook();

		//fstream = new FileInputStream("C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\Best\\pages\\Templates\\DoanhSoTT_Customer_focusedSKU.xlsm");
		
		String chuoi=getServletContext().getInitParameter("path") + "\\DoanhSoTT_Customer_focusedSKU.xlsm";
		 FileInputStream fstream = new FileInputStream(chuoi);		
		 //File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\DoanhSoTT_Customer_focusedSKU.xlsm");
		//	FileInputStream fstream = new FileInputStream(f) ;
		workbook.open(fstream);
		
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateHeaderLevel(workbook,obj);
		
		isFillData = FillDataLevel(workbook, obj, query);			

		if (!isFillData){
			if(fstream != null) fstream.close();
			return false;
		}
		
		workbook.save(out);
		fstream.close();
		return true;	
	}
	
	
	private void CreateHeaderLevel(Workbook workbook, IStockintransit obj)throws Exception{
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
		    
		    String tieude = "BÁO CÁO DOANH SỐ SKU TẬP TRUNG THEO NGÀY";
		    if(obj.getFromMonth().length() > 0)
		    	tieude = "BÁO CÁO DOANH SỐ SKU TẬP TRUNG THEO THÁNG";
		    ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);
		            
		    String message = "";
			if(obj.getdiscount().equals("0")){
				message += "Không chiết khấu";

				if(obj.getvat().equals("1")){
					message += ", không VAT";
				}else{
					message += ", có VAT";
				}			
			}else{
				message += "Có chiết khấu";
				if(obj.getvat().equals("1")){
					message += ", không VAT";
				}else{
					message +=", có VAT";
				}
			}
			
			cells.setRowHeight(2, 18.0f);
			cell = cells.getCell("A2");
			ReportAPI.getCellStyle(cell, Color.RED, true, 9, message);   

		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("A4");
		    
	    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ ngày : " + obj.gettungay() + "" );
		    
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("B4"); 
	    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến ngày : " + obj.getdenngay() + "" );
			
		    cells.setRowHeight(4, 18.0f);
		    cell = cells.getCell("A5");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
		    
		    cells.setRowHeight(5, 18.0f);
		    cell = cells.getCell("A6");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());

			
			cell = cells.getCell("DA1");		cell.setValue("KenhBanHang");
			cell = cells.getCell("DB1");		cell.setValue("DonViKinhDoanh");
			cell = cells.getCell("DC1");		cell.setValue("ChiNhanh");
			cell = cells.getCell("DD1");		cell.setValue("KhuVuc");
			cell = cells.getCell("DE1");		cell.setValue("GiamSat");			
			cell = cells.getCell("DF1");		cell.setValue("NhaPhanPhoi");		
			cell = cells.getCell("DG1");		cell.setValue("NhanHang");
			cell = cells.getCell("DH1");		cell.setValue("ChungLoai");
			cell = cells.getCell("DI1");		cell.setValue("MaNhaPhanPhoi"); //cell.setValue("Smart Id"); 
			cell = cells.getCell("DJ1");		cell.setValue("MaNhanVienBanHang");
			cell = cells.getCell("DK1");		cell.setValue("NhanVienBanHang");
			cell = cells.getCell("DL1");		cell.setValue("TenSanPham");
			cell = cells.getCell("DM1");		cell.setValue("MaSanPham");
			cell = cells.getCell("DN1");		cell.setValue("KhachHang");	
			cell = cells.getCell("DO1");		cell.setValue("ViTriCuaHang");
			cell = cells.getCell("DP1");		cell.setValue("LoaiCuaHang");
			cell = cells.getCell("DQ1"); 		cell.setValue("Ngay");
			cell = cells.getCell("DR1");		cell.setValue("NhomSanPham");
			cell = cells.getCell("DS1");		cell.setValue("NhomKhachHang");
			cell = cells.getCell("DT1");		cell.setValue("SoTien");
			cell = cells.getCell("DU1");		cell.setValue("SoLuong(Le)");
			cell = cells.getCell("DV1");		cell.setValue("SoLuong(Thung)");
			cell = cells.getCell("DW1");		cell.setValue("SmartId");
			cell = cells.getCell("DX1");		cell.setValue("MaKhachHang");
		}catch(Exception ex){
			throw new Exception("Xin loi,Khong tao duoc header cho bao cao");
		}
		
	}
	
	private boolean FillDataLevel(Workbook workbook, IStockintransit obj, String query) throws Exception{
		
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();		
		ResultSet rs = db.get(query);
		int i = 2;		
		if (rs != null) {
			try {
				Cell cell = null;
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
				cells.setColumnWidth(10, 20.0f);
				cells.setColumnWidth(11, 20.0f);
				cells.setColumnWidth(12, 20.0f);
				cells.setColumnWidth(13, 20.0f);
				cells.setColumnWidth(14, 20.0f);
				cells.setColumnWidth(15, 20.0f);
				cells.setColumnWidth(16, 20.0f);
				cells.setColumnWidth(17, 20.0f);
				cells.setColumnWidth(18, 20.0f);
				cells.setColumnWidth(19, 20.0f);
				cells.setColumnWidth(20, 20.0f);
				cells.setColumnWidth(21, 20.0f);
				cells.setColumnWidth(22, 20.0f);
				
				while (rs.next()) {					
					String chanel = rs.getString("chanel");
					String bu = rs.getString("bu");
					String region = rs.getString("region");
					String area = rs.getString("area");
					String salesuper = rs.getString("salesuper");
					String distributor = rs.getString("distributor");				
					String salesRep = rs.getString("SalesRep");
					String salesRepId = rs.getString("SalesRepId");					
					String brand = rs.getString("brand");
					String category = rs.getString("category");
					String discode = rs.getString("distcode");
					String sku = rs.getString("SKU");
					String skuCode = rs.getString("SKUcode");					
					String customer = rs.getString("customer");	
					String customer_code = rs.getString("customercode");
					String outlessClass = rs.getString("OutletClass");
					String outlettype = rs.getString("outlettype");
					String offdate =  rs.getString("offdate");
					String groupCustomer = rs.getString("nhomkhachhang");
					String groupSKU = rs.getString("nhomsanpham");
					float amount = rs.getFloat("amount");
					float piece = rs.getFloat("piece");
					float quantity = rs.getFloat("quantity");	
					
					//String smartId = rs.getString("SmartId");
					String smartId = "123456";
					
					cell = cells.getCell("DA" + Integer.toString(i));	cell.setValue(chanel);
					cell = cells.getCell("DB" + Integer.toString(i));	cell.setValue(bu);
					cell = cells.getCell("DC" + Integer.toString(i));	cell.setValue(region);
					cell = cells.getCell("DD" + Integer.toString(i));	cell.setValue(area);
					cell = cells.getCell("DE" + Integer.toString(i));	cell.setValue(salesuper);					
					cell = cells.getCell("DF" + Integer.toString(i));	cell.setValue(distributor);					
					cell = cells.getCell("DG" + Integer.toString(i));	cell.setValue(brand);
					cell = cells.getCell("DH" + Integer.toString(i));	cell.setValue(category);
					cell = cells.getCell("DI" + Integer.toString(i));	cell.setValue(discode);					
					cell = cells.getCell("DJ" + Integer.toString(i));	cell.setValue(salesRepId);
					cell = cells.getCell("DK" + Integer.toString(i));	cell.setValue(salesRep);					
					cell = cells.getCell("DL" + Integer.toString(i));	cell.setValue(sku);
					cell = cells.getCell("DM" + Integer.toString(i));	cell.setValue(skuCode);
					cell = cells.getCell("DN" + Integer.toString(i));	cell.setValue(customer);
					cell = cells.getCell("DO" + Integer.toString(i));	cell.setValue(outlessClass);
					cell = cells.getCell("DP" + Integer.toString(i));	cell.setValue(outlettype);
					cell = cells.getCell("DQ" + Integer.toString(i));	cell.setValue(offdate);
					cell = cells.getCell("DR" + Integer.toString(i));	cell.setValue(groupSKU);
					cell = cells.getCell("DS" + Integer.toString(i));	cell.setValue(groupCustomer);
					cell = cells.getCell("DT" + Integer.toString(i));	cell.setValue(amount);
					cell = cells.getCell("DU" + Integer.toString(i));	cell.setValue(piece);
					cell = cells.getCell("DV" + Integer.toString(i));	cell.setValue(quantity);	
					cell = cells.getCell("DW" + Integer.toString(i));	cell.setValue(smartId);
					cell = cells.getCell("DX" + Integer.toString(i));	cell.setValue(customer_code);	
					++i;					
				}
				if (rs != null) rs.close();
				
				if(db != null) 	db.shutDown();
				
				if(i==2){					
					throw new Exception("Chưa có dữ liệu báo cáo trong thời gian này.");
				}
				System.out.print("I = " + i);
				
			}catch(Exception ex){
				throw new Exception(ex.getMessage());
			}
		}else{
			return false;
		}		
		return true;
	}		
	
	
}
