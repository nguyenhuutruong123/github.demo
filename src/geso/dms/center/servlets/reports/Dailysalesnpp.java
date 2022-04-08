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



public class Dailysalesnpp extends HttpServlet {	
    public Dailysalesnpp() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		obj.setvat("1");
		obj.init();	
		session.setAttribute("obj", obj);		
		session.setAttribute("userId", obj.getuserId());
		String nextJSP = "/AHF/pages/Distributor/DailySalesReportDistributor.jsp";
		response.sendRedirect(nextJSP);
				
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		Utility util = new Utility();
		
		obj.setuserId((String) session.getAttribute("userId")!=null? (String) session.getAttribute("userId"):"");
		obj.setnppId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"))!=null? "": util.antiSQLInspection(request.getParameter("nppId"))));	 
		
	    Utility Ult = new Utility();		
		obj.setnppId(Ult.getIdNhapp(obj.getuserId()));
	    
        obj.setuserTen((String) session.getAttribute("userTen")!=null? 
        			(String) session.getAttribute("userTen"):""); 
        
	   
	   	obj.setkenhId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId"))!=null? util.antiSQLInspection(request.getParameter("kenhId")):""));
	   	 
	   	obj.setDdkd(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdId"))!=null? 	util.antiSQLInspection(request.getParameter("ddkdId")):""));
	   	 
	   	obj.setdvkdId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId"))!=null? util.antiSQLInspection(request.getParameter("dvkdId")):""));	   	 
	   	 
	   	obj.settungay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays"))));
	   	
	   	obj.setdenngay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays"))));	   	 
	   
		obj.setnhanhangId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhanhangId"))!=null? util.antiSQLInspection(request.getParameter("nhanhangId")):""));
		
		obj.setchungloaiId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chungloaiId"))!=null? util.antiSQLInspection(request.getParameter("chungloaiId")):""));		
		
		String vat = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vats")));
		
		/*if (vat.equals("1"))	
			obj.setvat("1.1");
		else*/
			obj.setvat("1");
		String dsc = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("discount")));
		
		if (dsc.equals("1"))
			obj.setdiscount("1");
		else
			obj.setdiscount("0");
	   /*	 
	   	 String []fieldsHien = request.getParameterValues("fieldsHien");
		 obj.setFieldShow(fieldsHien!=null? fieldsHien:null);*/
		 
		 String sql = "";
		 
		 if(obj.getkenhId().length()>0){
			 sql +=" and kbh.pk_seq = '" + obj.getkenhId() + "' ";
		 }
		 if(obj.getdvkdId().length()>0){
			 sql +=" and dvkd.pk_seq = '" +  obj.getdvkdId() +"'";
		 }
		 
		 if(obj.getnhanhangId().length()>0){
			 sql +=" and nh.pk_seq='" +  obj.getnhanhangId() +"'";
		 }
		 
		 if(obj.getchungloaiId().length()>0){
			 sql +=" and cl.pk_seq='" +  obj.getchungloaiId() +"'";
		 }
		 
		 if(obj.getDdkd().length()>0){
			 sql +=" and ddkd.pk_seq='" + obj.getDdkd() + "'";
		 }
		 String nextJSP = "/AHF/pages/Distributor/DailySalesReportDistributor.jsp";		 
		 String action=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));		 
		 
		 if(action.equals("create"))
		 {
			try{						
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=DoanhSoNPP.xlsm");
								
		        OutputStream out = response.getOutputStream();
		        String query =  setQuery(sql,obj);

		        if(!CreatePivotTable(out,obj,query)){
		        	response.setContentType("text/html");
		            PrintWriter writer = new PrintWriter(out);
		            writer.print("Khong co du lieu trong thoi gian nay");
		            writer.close();
		        }

			}catch(Exception ex){
				obj.setMsg(ex.getMessage());				
				response.sendRedirect(nextJSP);	
			}
		 }
		 else{
			response.sendRedirect(nextJSP);			
		 }
		 
		obj.init();
		session.setAttribute("obj", obj);
		session.setAttribute("userId", obj.getuserId());
	}

	private String setQuery(String sql,IStockintransit obj)
	{
		String dk = "";
		if(obj.getnhanhangId().length()  > 0)
			dk += " and nh.pk_seq = '"+obj.getnhanhangId()+"' ";
		
		if(obj.getchungloaiId().length()  > 0)
			dk += " and cl.pk_seq = '"+obj.getchungloaiId()+"' ";
		
		if(obj.getdvkdId().length()  > 0)
			dk += " and sp.dvkd_fk = '"+obj.getdvkdId()+"' ";
		
		
		String query=	"\n SELECT V.TEN AS REGION, KV.TEN AS AREA, NPP.TEN AS DISTRIBUTOR, DVKD.DIENGIAI AS DVKD, NPP.SITECODE AS DISTCODE, " + 	 
						"\n NH.TEN AS BRAND, CL.TEN AS CATEGORY, SP.MA + '_' + SP.TEN AS SKU, SP.MA AS SKUCODE, ISNULL(SP.MA,'') MADDT, " +
						"\n DDKD.TEN AS SALESREP, KH.TEN AS CUSTOMER, isnull(KH.DIACHI,N'Chưa xác định') AS DIACHI, isnull(cast(KH.DIENTHOAI as nvarchar(20)),N'Chưa xác định') AS DIENTHOAI, " +
						"\n isnull(KH.PHUONG,N'Chưa xác định') AS PHUONG, isnull(QH.TEN,N'Chưa xác định') AS QUANHUYEN, isnull(TT.TEN,N'Chưa xác định') AS TINHTHANH, \n" +
						"\n KH.SMARTID AS CUSTOMERCODE, KH.PHUONG AS PHUONG, VTCH.VITRI AS OUTLETPOSITION, ISNULL(M.DIENGIAI,    'Chua Xac Dinh') AS OUTLETTYPE, " + 
						"\n KBH.DIENGIAI AS CHANEL, DH.NGAYNHAP AS OFFDATE, DH.GIAMUA DONGIAGOC, DH.SOLUONG AS PIECE, (DH.GIAMUA * DH.SOLUONG* "+ obj.getvat()+") AS AMOUNT, " +
						"\n ISNULL(GSBH.TEN, 'Chua xac dinh') AS SALESUPER, '' AS QUANTITY, ISNULL(QH.TEN, 'Chua xac dinh') AS TOWN, " +   
						"\n ISNULL(TT.TEN, 'Chua xac dinh') AS PROVINCE, HCH.DIENGIAI AS OUTLETCLASS, NKH.DIENGIAI AS NHOMKHACHHANG, " +
						"\n NSP.DIENGIAI AS NHOMSANPHAM ,isnull(DH.SOLUONG * sp.trongluong ,0) as sanluong, DH.tennhomnpp,   \n" +
						"\n DH.CHIETKHAUNPP as chietkhauNPP, ISNULL((select top 1 c.ptChietKhau " +
						"\n         from BANGGIABANLECHUAN_NPP a inner join BANGGIABANLECHUAN b on a.BANGGIABANLECHUAN_FK = b.PK_SEQ  " +
						"\n         inner join BANGGIABLC_SANPHAM c on c.BGBLC_FK = b.PK_SEQ and c.SANPHAM_FK = DH.SANPHAM_FK  " +
						"\n         where a.NPP_FK= DH.NPP_FK and b.TuNgay <= DH.NGAYNHAP and b.KENH_FK =  DH.KBH_FK  " +
						"\n         order by b.TuNgay desc),0) chietkhauTT, ISNULL(SP.TENVIETTAT,'') TENTATSP "+
						"\n FROM (  " +
							"\n SELECT  DH.NGAYNHAP, DH.DDKD_FK, DH.KHACHHANG_FK, " + 
							"\n DH.NPP_FK, DH.GSBH_FK, DH.KHO_FK, DH.KBH_FK, " +
							"\n DH_SP.SANPHAM_FK, " +  	
							"\n CAST( DH_SP.GIAMUA as float) AS GIAMUA , " +
							"\n (-1)*DH_SP.SOLUONG AS SOLUONG, " + 
							"\n  0 CHIETKHAUNPP ,nnpp.ten as tennhomnpp  " +
							"\n FROM  DONHANGTRAVE DH " +  	
							"\n inner  JOIN  DONHANGTRAVE_SANPHAM DH_SP ON DH_SP.DONHANGTRAVE_FK = DH.PK_SEQ " +  	
							"\n  left join NhomNpp_Npp npp on DH.NPP_FK=npp.Npp_FK  "+
							 "\n left join NhomNpp nnpp on nnpp.PK_SEQ=npp.NhomNpp_FK "+  
							"\n WHERE DH.donhang_fk is null and DH.TRANGTHAI = 3 AND DH.NPP_FK ='"+ obj.getnppId() +"' " +
							"\n AND DH.NGAYNHAP >='" + obj.gettungay() + "' AND DH.NGAYNHAP <= '" + obj.getdenngay() + "' " +  	
							"\n UNION ALL " +
							
							"\n SELECT  DH.NGAYNHAP, DH.DDKD_FK, DH.KHACHHANG_FK, DH.NPP_FK, DH.GSBH_FK, DH.KHO_FK, DH.KBH_FK, " +
							"\n DH_SP.SANPHAM_FK, case when dh.donhangkhac = '1' then 0 else CAST(DH_SP.dongiagoc as float) end GIAMUA, DH_SP.SOLUONG, DH_SP.CHIETKHAU + ISNULL(CK.ptchietkhau,0) CHIETKHAUNPP ,nnpp.ten as tennhomnpp  " +
							"\n FROM DONHANG DH  " +
							"\n INNER JOIN DONHANG_SANPHAM  DH_SP ON DH.PK_SEQ = DH_SP.DONHANG_FK " +  	
							 "\n left join NhomNpp_Npp npp on DH.NPP_FK=npp.Npp_FK  " +
							 "\n left join NhomNpp nnpp on nnpp.PK_SEQ=npp.NhomNpp_FK  " +
							 "\n left join KHACHHANG_SANPHAMCK CK on DH.KHACHHANG_FK = CK.khachhang_fk and DH.KBH_FK = CK.KENHBANHANG_FK and DH_SP.SANPHAM_FK = CK.sanpham_fk  "+
							"\n WHERE DH.TRANGTHAI = 1 and not exists ( select 1 from DONHANGTRAVE x where x.donhang_fk = dh.pk_seq and x.trangthai = 3 ) " +
							"\n		 AND DH.NPP_FK = '" + obj.getnppId() + "' AND DH.NGAYNHAP >='" + obj.gettungay() + "' " +
							"\n			AND DH.NGAYNHAP <= '" + obj.getdenngay() + "' " +   
						"\n )DH " +	  
						"\n INNER JOIN SANPHAM SP ON DH.SANPHAM_FK = SP.PK_SEQ " +   
						"\n INNER JOIN NHAPHANPHOI NPP ON DH.NPP_FK = NPP.PK_SEQ " +  
						"\n LEFT JOIN KHUVUC KV ON NPP.KHUVUC_FK = KV.PK_SEQ " + 
						"\n LEFT JOIN VUNG V ON KV.VUNG_FK = V.PK_SEQ " +  
						"\n LEFT JOIN NHANHANG NH ON SP.NHANHANG_FK = NH.PK_SEQ " +
						"\n LEFT JOIN CHUNGLOAI CL ON SP.CHUNGLOAI_FK = CL.PK_SEQ " +     
						"\n LEFT JOIN DAIDIENKINHDOANH DDKD ON DH.DDKD_FK = DDKD.PK_SEQ " +
						"\n LEFT JOIN KHACHHANG KH ON DH.KHACHHANG_FK = KH.PK_SEQ " +   
						"\n LEFT JOIN VITRICUAHANG VTCH ON KH.VTCH_FK = VTCH.PK_SEQ " +  
						"\n LEFT JOIN KENHBANHANG KBH ON KH.KBH_FK = KBH.PK_SEQ " +   
						"\n LEFT JOIN LOAICUAHANG M ON KH.LCH_FK = M.PK_SEQ " +
						"\n LEFT JOIN GIAMSATBANHANG GSBH ON DH.GSBH_FK = GSBH.PK_SEQ " +    
						"\n INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK " +  
						//"LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK = SP.PK_SEQ \n" +  
						"\n LEFT JOIN QUANHUYEN QH ON KH.QUANHUYEN_FK = QH.PK_SEQ " + 
						"\n LEFT JOIN TINHTHANH TT ON KH.TINHTHANH_FK = TT.PK_SEQ " +    
						"\n LEFT JOIN HANGCUAHANG HCH ON KH.HCH_FK = HCH.PK_SEQ " +  
						"\n LEFT JOIN NHOMKHACHHANG_KHACHHANG NKHKH ON NKHKH.KH_FK = KH.PK_SEQ " +   
						"\n LEFT JOIN NHOMKHACHHANG NKH ON NKH.PK_SEQ = NKHKH.NKH_FK " + 
						"\n LEFT JOIN " + 
						"\n ( " +
								"SELECT SP_FK, MAX(NSP_FK) AS NHOMSP " + 
								"FROM NHOMSANPHAM_SANPHAM  GROUP BY SP_FK " +
						"\n ) NSPSP ON NSPSP.SP_FK = SP.PK_SEQ " + 
						"\n LEFT JOIN NHOMSANPHAM NSP ON NSP.PK_SEQ = NSPSP.NHOMSP " +  
						"\n WHERE DH.NPP_FK ='"+ obj.getnppId() +" ' AND DH.NGAYNHAP >='" + obj.gettungay() + "' " + 
							   "\n AND DH.NGAYNHAP <= '"+ obj.getdenngay() +"' " +dk+
							   "\n ORDER BY DVKD.DIENGIAI, DDKD.TEN, DH.NGAYNHAP,DH.tennhomnpp \n " ;
		
	 		 System.out.print("QUERY: NPP " + query);
		 return query;

	
	}
	private boolean CreatePivotTable(OutputStream out,IStockintransit obj,String query) throws Exception
    {    
		String chuoi=getServletContext().getInitParameter("path") + "\\DoanhSoNPP.xlsm";
		 FileInputStream fstream = new FileInputStream(chuoi);		
		 /*File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\DoanhSoNPP.xlsm");
			FileInputStream fstream = new FileInputStream(f) ;*/
			 Workbook workbook = new Workbook();
		 workbook.open(fstream);
		 workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
	     	    
	     CreateStaticHeader(workbook,obj);	    
	     boolean isFillData = CreateStaticData(workbook,obj,query);
	     
	     if(!isFillData)
	    	 return false;
	     
	     workbook.save(out);
	     fstream.close();
	     
	     return true;
   }
	
	private void CreateStaticHeader(Workbook workbook, IStockintransit obj) throws Exception
	{
		
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
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
	    
	    ReportAPI.getCellStyle(cell,Color.RED, true, 14, "BÁO CÁO DOANH SỐ BÁN HÀNG THEO NGÀY");
	            
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
		
		/*cells.setRowHeight(2, 18.0f);
		cell = cells.getCell("A2");
		ReportAPI.getCellStyle(cell, Color.RED, true, 9, message); */  

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
	    		
	   
		cell = cells.getCell("DA1"); cell.setValue("Kenh Ban Hang"); 
		cell = cells.getCell("DB1"); cell.setValue("Don Vi Kinh Doanh");
		cell = cells.getCell("DC1"); cell.setValue("Nhan Vien Ban Hang");
		cell = cells.getCell("DD1"); cell.setValue("Nhan Hang");
		cell = cells.getCell("DE1"); cell.setValue("Chung Loai");	  
		cell = cells.getCell("DF1"); cell.setValue("Ten San Pham");
		cell = cells.getCell("DG1"); cell.setValue("Ma San Pham");	  
		cell = cells.getCell("DH1"); cell.setValue("Khach Hang");
		cell = cells.getCell("DI1"); cell.setValue("Hang Cua Hang");
		cell = cells.getCell("DJ1"); cell.setValue("Loai Cua Hang");
		cell = cells.getCell("DK1"); cell.setValue("Ngay");
		cell = cells.getCell("DL1"); cell.setValue("Nhom San Pham");
		cell = cells.getCell("DM1"); cell.setValue("Nhom Khach Hang");
		cell = cells.getCell("DN1"); cell.setValue("Ma Khach Hang");
		cell = cells.getCell("DO1"); cell.setValue("Tong Tien");
		/*cell = cells.getCell("DP1"); cell.setValue("San Luong");*/
		cell = cells.getCell("DP1"); cell.setValue("Phuong");
		cell = cells.getCell("DQ1"); cell.setValue("DiaChi");
		cell = cells.getCell("DR1"); cell.setValue("DienThoai");
		cell = cells.getCell("DS1"); cell.setValue("QuanHuyen");
		cell = cells.getCell("DT1"); cell.setValue("TinhThanh");
		cell = cells.getCell("DU1"); cell.setValue("chietkhauNPP");
		cell = cells.getCell("DV1"); cell.setValue("NhomNhaPhanPhoi");
		cell = cells.getCell("DW1"); cell.setValue("chietkhauTT");
		cell = cells.getCell("DX1"); cell.setValue("DonGiaGoc");
		cell = cells.getCell("DY1"); cell.setValue("TenSanPham(VietTat)");		
		cell = cells.getCell("DZ1"); cell.setValue("So Luong Le");
		cell = cells.getCell("EA1"); cell.setValue("Ma San Pham");
		cell = cells.getCell("EB1"); cell.setValue("So Luong Thung");
		   
	}
	private boolean CreateStaticData(Workbook workbook,IStockintransit obj,String query) throws Exception
	{
		
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    ResultSet rs = db.get(query);
		int i = 2;
		if(rs!= null)
		{
			try 
			{
				cells.setColumnWidth(0, 25.0f);
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
				cells.setColumnWidth(11, 15.0f);
				cells.setColumnWidth(12, 15.0f);
				cells.setColumnWidth(13, 15.0f);
				cells.setColumnWidth(14, 15.0f);
				cells.setColumnWidth(15, 15.0f);
				cells.setColumnWidth(16, 15.0f);		
				cells.setColumnWidth(17, 15.0f);	
				cells.setColumnWidth(18, 15.0f);	
							
				for(int j = 12; j <= 22; j++)
					cells.setRowHeight(j, 14.0f);				
				Cell cell = null;
				while(rs.next())
				{
					String Channel = rs.getString("chanel");
					String Dvkd = rs.getString("dvkd");
					String SalesRep = rs.getString("SalesRep");
					String Brands = rs.getString("brand");
					String Category = rs.getString("category");
					
					long Amount = Math.round(Float.parseFloat(rs.getString("Amount")));

					String Sku = rs.getString("SKU");
					String SkuCode = rs.getString("SKUcode");

					long Piece = Math.round(Float.parseFloat(rs.getString("piece")));
					
					long dongiagoc = Math.round(Float.parseFloat(rs.getString("dongiagoc")));
					
					String Customer = rs.getString("customer");
					String CustomerCode = rs.getString("customercode");
					String OutletClass = rs.getString("OutletClass");
					String OutletType = rs.getString("outlettype");
					String OffDate = rs.getString("offdate");
					String phuong = rs.getString("phuong");
					
					//long Quantity = Math.round(Float.parseFloat(rs.getString("quantity")));
					
					String GroupSku = rs.getString("nhomsanpham");
					String GroupCustomer = rs.getString("nhomkhachhang");	
					String diachi = rs.getString("diachi");
					String dienthoai = rs.getString("dienthoai");
					String quanhuyen = rs.getString("quanhuyen");
					String tinhthanh = rs.getString("tinhthanh");
					String chietkhautt = rs.getString("chietkhauTT");
					String chietkhaunpp = rs.getString("chietkhauNPP");
					String nhomnpp = rs.getString("tennhomnpp")==null?"":rs.getString("tennhomnpp");
					String tentatsp = rs.getString("tentatsp");
					String maDDT = rs.getString("maDDT");

					cell = cells.getCell("DA" + Integer.toString(i)); cell.setValue(Channel);	//0
					cell = cells.getCell("DB" + Integer.toString(i)); cell.setValue(Dvkd);		//1
					cell = cells.getCell("DC" + Integer.toString(i)); cell.setValue(SalesRep);	//2
					cell = cells.getCell("DD" + Integer.toString(i)); cell.setValue(Brands);	//3
					cell = cells.getCell("DE" + Integer.toString(i)); cell.setValue(Category);	//4
					cell = cells.getCell("DF" + Integer.toString(i)); cell.setValue(Sku);		//5
					cell = cells.getCell("DG" + Integer.toString(i)); cell.setValue(SkuCode);	//6
					cell = cells.getCell("DH" + Integer.toString(i)); cell.setValue(Customer);	//7
					cell = cells.getCell("DI" + Integer.toString(i)); cell.setValue(OutletClass);	//8
					cell = cells.getCell("DJ" + Integer.toString(i)); cell.setValue(OutletType);	//9
					cell = cells.getCell("DK" + Integer.toString(i)); cell.setValue(OffDate);		//10
					cell = cells.getCell("DL" + Integer.toString(i)); cell.setValue(GroupSku);		//11
					cell = cells.getCell("DM" + Integer.toString(i)); cell.setValue(GroupCustomer);	//12
					cell = cells.getCell("DN" + Integer.toString(i)); cell.setValue(CustomerCode);	//13
					cell = cells.getCell("DO" + Integer.toString(i)); cell.setValue(Amount);	//14					
					//cell = cells.getCell("DP" + Integer.toString(i)); cell.setValue(rs.getFloat("sanluong")); //16
					cell = cells.getCell("DP" + Integer.toString(i)); cell.setValue(phuong); //16
					cell = cells.getCell("DQ" + Integer.toString(i)); cell.setValue(diachi); //16
					cell = cells.getCell("DR" + Integer.toString(i)); cell.setValue(dienthoai); //16
					cell = cells.getCell("DS" + Integer.toString(i)); cell.setValue(quanhuyen); //16
					cell = cells.getCell("DT" + Integer.toString(i)); cell.setValue(tinhthanh); //16
					cell = cells.getCell("DU" + Integer.toString(i)); cell.setValue(chietkhaunpp); //17
					cell = cells.getCell("DV" + Integer.toString(i)); cell.setValue(nhomnpp); //17
					cell = cells.getCell("DW" + Integer.toString(i)); cell.setValue(chietkhautt); //18
					cell = cells.getCell("DX" + Integer.toString(i)); cell.setValue(dongiagoc); //19
					cell = cells.getCell("DY" + Integer.toString(i)); cell.setValue(tentatsp); //19
					cell = cells.getCell("DZ" + Integer.toString(i)); cell.setValue(Piece); //15
					cell = cells.getCell("EA" + Integer.toString(i)); cell.setValue(maDDT); //16
					cell = cells.getCell("EB" + Integer.toString(i)); cell.setValue(""); //16
						i++;
				}
				if (rs != null)
					rs.close();	
				if(db!=null){
					db.shutDown();
				}
				if(i==2){
					throw new Exception("Không cáo báo cáo trong thời gian đã chọn ....!!!");
				}

			   			

			}
			catch (Exception e){
				e.printStackTrace();
				throw new Exception("Không có báo cáo trong thời gian này :"+e.getMessage());
			}
			
		}else{
			return false;
		}	
		return true;
	}	
}
