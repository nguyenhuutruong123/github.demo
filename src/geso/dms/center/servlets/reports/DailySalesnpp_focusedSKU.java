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


public class DailySalesnpp_focusedSKU extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DailySalesnpp_focusedSKU() {
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
		session.setAttribute("checkedSKU","");
		session.setAttribute("userId", obj.getuserId());
		String nextJSP = "/AHF/pages/Distributor/DailySalesReportDistributor_focusedSKU.jsp";
		response.sendRedirect(nextJSP);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		obj.setMsg("");
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
	   	 
		 
		 String sql = "";
		 
		 
		 System.out.println(obj.getkenhId().length());
		 if(obj.getkenhId().length()>0){
			 sql +=" and kbh.pk_seq = '" + obj.getkenhId() + "' ";
		 }
		 if(obj.getdvkdId().length()>0){
			 sql +=" and dvkd.pk_seq = '" +  obj.getdvkdId() +"'";
		 }
		 

		 if(obj.getDdkd().length()>0){
			 sql +=" and ddkd.pk_seq='" + obj.getDdkd() + "'";
		 }

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
			obj.SetNhoSPId(whereNSP);

			session.setAttribute("checkedSKU",whereNSP);
			if(obj.GetNhoSPId().length() > 0){
				 sql += " and NSP.PK_SEQ in (" + obj.GetNhoSPId() + ")" ;
			 }
			
		 
		 String nextJSP = "/AHF/pages/Distributor/DailySalesReportDistributor_focusedSKU.jsp";		 
		 String action=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));		 
		 
		 
		 if(action.equals("tao"))
		 {
			try{						
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=DoanhSoNPP_FocusedSKU.xlsm");
		        OutputStream out = response.getOutputStream();
		        String query =  setQuery(sql,obj);

		 
		        boolean k1=CreatePivotTable(out,obj,query);

		        if(!k1)
		        {
		            obj.setMsg("Không có dữ liệu trong thời gian này");
		    	 	response.sendRedirect(nextJSP);

		        }

			}catch(Exception ex)
			{
				obj.setMsg(ex.getMessage());				
			}
		 }
         obj.init();
         session.setAttribute("obj", obj);
	 	session.setAttribute("userId", obj.getuserId());


	}

	private String setQuery(String sql,IStockintransit obj)
	{
		String query=	"SELECT	" +
						"V.TEN AS REGION, " + 
						"KV.TEN AS AREA, " +
						"NPP.TEN AS DISTRIBUTOR, " +  
						"DVKD.DIENGIAI AS DVKD, " +
						"NPP.SITECODE AS DISTCODE, " + 	 
						"NH.TEN AS BRAND, " +
						"CL.TEN AS CATEGORY, " +
						"SP.TEN AS SKU, " +   
						"SP.MA AS SKUCODE, " +
						"DDKD.TEN AS SALESREP, " + 
						"KH.TEN AS CUSTOMER, " +
						"isnull(KH.DIACHI,N'Chưa xác định') AS DIACHI, " +
						"isnull(cast(KH.DIENTHOAI as nvarchar(20)),N'Chưa xác định') AS DIENTHOAI, " +
						"isnull(KH.PHUONG,N'Chưa xác định') AS PHUONG, " +
						"isnull(QH.TEN,N'Chưa xác định') AS QUANHUYEN, " +
						"isnull(TT.TEN,N'Chưa xác định') AS TINHTHANH, " +
						"KH.SMARTID AS CUSTOMERCODE, " +
						"VTCH.VITRI AS OUTLETPOSITION, " + 
						"ISNULL(M.DIENGIAI,    'Chua Xac Dinh') AS OUTLETTYPE, " + 
						"KBH.DIENGIAI AS CHANEL, " +
						"DH.NGAYNHAP AS OFFDATE, " +
						"DH.SOLUONG AS PIECE,	" ; 

			
		//	" 	 dh.giamua * (dh.soluong) * 1.1  as amount" +
			 if(obj.getdiscount().equals("1"))          
				 query +=	" (DH.GIAMUA * DH.SOLUONG* "+ obj.getvat()+") AS AMOUNT ,"; 
			 else{
				 query +=	" ((DH.GIAMUA * DH.SOLUONG )- DH.CHIETKHAU )  * "+ obj.getvat()+" AS AMOUNT ,";
			 }
			 
		query = query +	"ISNULL(GSBH.TEN, 'Chua xac dinh') AS SALESUPER, " +  	 
						"CASE WHEN ROUND((CAST((QC.SOLUONG2 * DH.SOLUONG) AS FLOAT) / CAST(QC.SOLUONG1 AS FLOAT)),3) IS NULL THEN 0 " + 
						"ELSE ROUND((CAST((QC.SOLUONG2 * DH.SOLUONG) AS FLOAT) / CAST( QC.SOLUONG1 AS FLOAT)), 3) " +
						"END AS QUANTITY, " +
						"ISNULL(QH.TEN, 'Chua xac dinh') AS TOWN, " +   
						"ISNULL(TT.TEN, 'Chua xac dinh') AS PROVINCE, " + 
						"HCH.DIENGIAI AS OUTLETCLASS, " +
						"NKH.DIENGIAI AS NHOMKHACHHANG, " +
						"NSP.DIENGIAI AS NHOMSANPHAM   " +
						"FROM	" +   
						"(  " +
							"SELECT  DH.NGAYNHAP, DH.DDKD_FK, DH.KHACHHANG_FK, " + 
									"DH.NPP_FK, DH.GSBH_FK, DH.KHO_FK, DH.KBH_FK, " +
									"ISNULL(DH_SP.SANPHAM_FK, DH_SP1.SANPHAM_FK) AS SANPHAM_FK, " +  	
									"ISNULL(DH_SP.GIAMUA, DH_SP1.GIAMUA) AS GIAMUA , " +
									"(-1)*ISNULL(DH_SP.SOLUONG, DH_SP1.SOLUONG) AS SOLUONG, " + 
									"(-1)*ISNULL(DH_SP1.CHIETKHAU, 0) AS CHIETKHAU  " +

							"FROM  DONHANGTRAVE DH " +  	
							"LEFT OUTER JOIN  DONHANGTRAVE_SANPHAM DH_SP ON DH_SP.DONHANGTRAVE_FK = DH.PK_SEQ " +  	
							"LEFT OUTER JOIN  DONHANG_SANPHAM DH_SP1 ON DH.DONHANG_FK = DH_SP1.DONHANG_FK  " +
							"WHERE DH.TRANGTHAI = 3 AND DH.NPP_FK ='"+ obj.getnppId() +"' " +
							"AND DH.NGAYNHAP >='" + obj.gettungay() + "' AND DH.NGAYNHAP <= '" + obj.getdenngay() + "' " +  	
		
							"UNION ALL " +
		
							"SELECT  DH.NGAYNHAP, DH.DDKD_FK, DH.KHACHHANG_FK, DH.NPP_FK, DH.GSBH_FK, DH.KHO_FK, DH.KBH_FK, " +
									"DH_SP.SANPHAM_FK, DH_SP.GIAMUA, DH_SP.SOLUONG, DH_SP.CHIETKHAU  " +
							"FROM DONHANG DH  " +
							"INNER JOIN DONHANG_SANPHAM  DH_SP ON DH.PK_SEQ = DH_SP.DONHANG_FK " +  	
							"WHERE DH.TRANGTHAI = 1 AND DH.NPP_FK = '" + obj.getnppId() + "' AND DH.NGAYNHAP >='" + obj.gettungay() + "' " +
								   "AND DH.NGAYNHAP <= '" + obj.getdenngay() + "' " +   
						")DH " +	  
						"INNER JOIN SANPHAM SP ON DH.SANPHAM_FK = SP.PK_SEQ " +   
						"INNER JOIN NHAPHANPHOI NPP ON DH.NPP_FK = NPP.PK_SEQ " +  
						"LEFT JOIN KHUVUC KV ON NPP.KHUVUC_FK = KV.PK_SEQ " +  
						"LEFT JOIN VUNG V ON KV.VUNG_FK = V.PK_SEQ " +   
						"LEFT JOIN NHANHANG NH ON SP.NHANHANG_FK = NH.PK_SEQ " +  
						"LEFT JOIN CHUNGLOAI CL ON SP.CHUNGLOAI_FK = CL.PK_SEQ " +     
						"LEFT JOIN DAIDIENKINHDOANH DDKD ON DH.DDKD_FK = DDKD.PK_SEQ " +  
						"LEFT JOIN KHACHHANG KH ON DH.KHACHHANG_FK = KH.PK_SEQ " +   
						"LEFT JOIN VITRICUAHANG VTCH ON KH.VTCH_FK = VTCH.PK_SEQ " +  
						"LEFT JOIN KENHBANHANG KBH ON KH.KBH_FK = KBH.PK_SEQ " +    
						"LEFT JOIN LOAICUAHANG M ON KH.LCH_FK = M.PK_SEQ " +
						"LEFT JOIN GIAMSATBANHANG GSBH ON DH.GSBH_FK = GSBH.PK_SEQ " +    
						"INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK " +  
						"LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK = SP.PK_SEQ " +   
						"LEFT JOIN QUANHUYEN QH ON KH.QUANHUYEN_FK = QH.PK_SEQ " +  
						"LEFT JOIN TINHTHANH TT ON KH.TINHTHANH_FK = TT.PK_SEQ " +     
						"LEFT JOIN HANGCUAHANG HCH ON KH.HCH_FK = HCH.PK_SEQ " +  
						"LEFT JOIN NHOMKHACHHANG_KHACHHANG NKHKH ON NKHKH.KH_FK = KH.PK_SEQ " +   
						"LEFT JOIN NHOMKHACHHANG NKH ON NKH.PK_SEQ = NKHKH.NKH_FK " + 
						"LEFT JOIN " + 
						"( " +
								"SELECT SP_FK, MAX(NSP_FK) AS NHOMSP " + 
								"FROM NHOMSANPHAM_SANPHAM  GROUP BY SP_FK " +
						") NSPSP ON NSPSP.SP_FK = SP.PK_SEQ " + 
						"LEFT JOIN NHOMSANPHAM NSP ON NSP.PK_SEQ = NSPSP.NHOMSP " +
						"WHERE DH.NPP_FK ='"+ obj.getnppId() +" ' AND DH.NGAYNHAP >='" + obj.gettungay() + "' " + 
							   "AND DH.NGAYNHAP <= '"+ obj.getdenngay() +"' " + sql +
							   " ORDER BY DVKD.DIENGIAI, DDKD.TEN, DH.NGAYNHAP " ;
		
	 		 System.out.println("QUERY: NPP.................................. " + query);
		 return query;

	}
	private boolean CreatePivotTable(OutputStream out,IStockintransit obj,String query) throws Exception
    {    
		 
		String chuoi=getServletContext().getInitParameter("path") + "\\DoanhSoNPP_focusedSKU.xlsm";		
		FileInputStream fstream = new FileInputStream(chuoi);		
	/*	File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\DoanhSoNPP_focusedSKU.xlsm");
		FileInputStream fstream = new FileInputStream(f) ;
	*/	//FileInputStream fstream = new FileInputStream("C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\Best\\pages\\Templates\\DoanhSoNPP_focusedSKU.xlsm");		

//		 FileInputStream fstream = new FileInputStream("D:\\Best Stable\\Best\\WebContent\\pages\\Templates\\DoanhSoNPP_focusedSKU.xlsm");
		 Workbook workbook = new Workbook();
		 workbook.open(fstream);
		 workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
	     	    
	     CreateStaticHeader(workbook,obj);	    
	     boolean isFillData = CreateStaticData(workbook,obj,query);
	     
	     if(!isFillData){
	    	 fstream.close();
	    	 return false; 
	     }
	     else
	     {
	    	 workbook.save(out);
	    	 fstream.close();
	     
	    	 return true;
	     }
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
		cell = cells.getCell("DP1"); cell.setValue("So Luong Le");
		cell = cells.getCell("DQ1"); cell.setValue("So Luong Thung");
		cell = cells.getCell("DR1"); cell.setValue("DiaChi");
		cell = cells.getCell("DS1"); cell.setValue("DienThoai");
		cell = cells.getCell("DT1"); cell.setValue("Phuong");
		cell = cells.getCell("DU1"); cell.setValue("QuanHuyen");
		cell = cells.getCell("DV1"); cell.setValue("TinhThanh");
	   
	}
	private boolean CreateStaticData(Workbook workbook,IStockintransit obj,String query) throws Exception
	{
		
		dbutils db = new dbutils();
		if(db != null) System.out.println("Connected");
		else System.out.println("Not Connected");
		
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    System.out.println("Query here:" + query);
	    
	    ResultSet rs = db.get(query);
	    System.out.println("Query here:" + query);
	    		
		int i = 2;
		if(rs!= null)
		{
			System.out.println("I am here");
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
					
					String Customer = rs.getString("customer");
					String CustomerCode = rs.getString("customercode");
					String OutletClass = rs.getString("OutletClass");
					String OutletType = rs.getString("outlettype");
					String OffDate = rs.getString("offdate");
					
					long Quantity = Math.round(Float.parseFloat(rs.getString("quantity")));
					
					String GroupSku = rs.getString("nhomsanpham");
					String GroupCustomer = rs.getString("nhomkhachhang");
					String diachi = rs.getString("diachi");	
					String dienthoai = rs.getString("dienthoai");	
					String phuong = rs.getString("phuong");	
					String quanhuyen = rs.getString("quanhuyen");	
					String tinhthanh = rs.getString("tinhthanh");	
					
					System.out.println(GroupSku + ";" + Channel + ";" + Sku);
					
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
					cell = cells.getCell("DP" + Integer.toString(i)); cell.setValue(Piece); //15
					cell = cells.getCell("DQ" + Integer.toString(i)); cell.setValue(Quantity); //16
					cell = cells.getCell("DR" + Integer.toString(i)); cell.setValue(diachi); 
					cell = cells.getCell("DS" + Integer.toString(i)); cell.setValue(dienthoai); 
					cell = cells.getCell("DT" + Integer.toString(i)); cell.setValue(phuong); 
					cell = cells.getCell("DU" + Integer.toString(i)); cell.setValue(quanhuyen); 
					cell = cells.getCell("DV" + Integer.toString(i)); cell.setValue(tinhthanh); 
					i++;
				}
				if(i==2)
				{
					throw new Exception("Xin loi,khong co bao cao voi dieu kien da chon....!!!");
				}
		
/*				ReportAPI.setHidden(workbook, 50);
				PivotTables pivotTables = worksheet.getPivotTables();			
				String pos = Integer.toString(i - 1); 
				int index = pivotTables.add("=DA1:DQ" + pos, "A8","DoanhSoNPP");				
				PivotTable pivotTable = pivotTables.get(index);

				pivotTable.setRowGrand(true);
				pivotTable.setColumnGrand(true);
				pivotTable.setAutoFormat(true);	  
				pivotTable.setAutoFormatType(PivotTableAutoFormatType.TABLE10);
				
				Hashtable<String, Integer> selected = new Hashtable<String, Integer>();
				selected.put("Channel",0);
				selected.put("Dvkd",1);
			   	selected.put("SalesRep", 2); 
			    selected.put("Brands",3);
			    selected.put("Category",4);	 			   
			    selected.put("SKU",5);
			    selected.put("SKUCode",6);
			    selected.put("Customer",7);
			    selected.put("OutletClass",8);
			    selected.put("OutletType",9);
			    selected.put("Date",10);
			    selected.put("GroupSKU",11);
			    selected.put("GroupCustomer",12);
			    selected.put("CustomerCode",13);
			    selected.put("Amount",14);
			    selected.put("Piece",15);
			    selected.put("Quantity",16);
			    		    
			    for(String value:obj.getFieldShow()){
			    	int VALUE = selected.get(value);
			    	switch(VALUE){
				    	case 0: case 1: case 2:
							pivotTable.addFieldToArea(PivotFieldType.ROW, VALUE);	 
							break;  		
	
				    	case 3 : case 4 : case 5 : case 6 : case 7:
				    	case 8: case 9: case 11: case 12:case 13:
				    		pivotTable.addFieldToArea(PivotFieldType.ROW, VALUE);
				    		break;
				    		
				    	case 14: 
				    		pivotTable.addFieldToArea(PivotFieldType.DATA, VALUE);
				    		pivotTable.getDataFields().get(0).setNumber(3);
				    		pivotTable.getDataFields().get(0).setDisplayName("Tong Tien");	
				    		break;
				    		
				    	case 15 : 
				    		pivotTable.addFieldToArea(PivotFieldType.DATA, VALUE);
				    		pivotTable.getDataFields().get(1).setNumber(3);
				    		pivotTable.getDataFields().get(1).setDisplayName("So Luong Le");
				    		break;
				    		
				    	case 16:
				    		pivotTable.addFieldToArea(PivotFieldType.DATA, VALUE);
				    		pivotTable.getDataFields().get(2).setNumber(3);
				    		pivotTable.getDataFields().get(2).setDisplayName("So Luong Thung");
				    		break;				    		
				    		
				    	case 10:
				    		pivotTable.addFieldToArea(PivotFieldType.COLUMN, VALUE);
				    		break;
				    		
				    	default:
				    		pivotTable.addFieldToArea(PivotFieldType.ROW, 12);
				    		break;				    		
			    	}
			    }*/
			   			

			}
			catch(Exception e)
			{
				System.out.println(e.toString());
				return false;
			}
			
		}else{
			if(db!=null) db.shutDown();
			return false;
		}	

		if (rs != null) rs.close();	
		if(db!=null) db.shutDown();

		return true;
	}	
}
