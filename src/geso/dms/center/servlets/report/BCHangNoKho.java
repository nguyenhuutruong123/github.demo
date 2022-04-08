package geso.dms.center.servlets.report;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
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
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;


public class BCHangNoKho extends HttpServlet {
	private static final long serialVersionUID = 1L;      			

    public BCHangNoKho() {
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
//		    System.out.println(userId);
		    obj.setuserId(userId);
		    obj.init();	    
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			String nextJSP = "/AHF/pages/Center/Hangnokho.jsp";
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
		Utility util = new Utility();
		
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		
		obj.settungay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays"))));
		obj.setdenngay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays"))));
	
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");
		obj.setkenhId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId"))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")):""));
		
		obj.setvungId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")):""));
			
		obj.setkhuvucId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")):""));
		
		obj.setnppId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")):""));

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action") != null? util.antiSQLInspection(request.getParameter("action"))) : "";
		String nextJSP = "/AHF/pages/Center/Hangnokho.jsp";
		
		if(action.equals("Taomoi"))
		{
			try
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BCHangNo.xlsm");
		        if(!CreatePivotTable(out,obj))
		        {
		        	obj.setMsg("Không có dữ liệu trong thời gian này.");
		        	obj.init();
		    		session.setAttribute("obj", obj);	
		    		response.sendRedirect(nextJSP);
		        }
		        
			}
			catch(Exception ex)
			{
				obj.setMsg(ex.getMessage());
			}
		}
	
		obj.init();
		session.setAttribute("obj", obj);	
		response.sendRedirect(nextJSP);
	}
	
	private boolean CreatePivotTable(OutputStream out, IStockintransit obj) throws Exception 
	{
		//String chuoi = getServletContext().getInitParameter("path") + "\\BCHangNo.xlsm";
		//FileInputStream fstream = new FileInputStream(chuoi);
		File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\BCHangNo.xlsm");
		FileInputStream fstream = new FileInputStream(f) ;
		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateStaticHeader(workbook, obj);

		boolean isFill = CreateStaticData(workbook, obj);
		
		if (!isFill){
			fstream.close();
			return false;
		}else {
			workbook.save(out);
			fstream.close();
			return true;
		}
	}
	
	private void CreateStaticHeader(Workbook workbook, IStockintransit obj) throws Exception 
	{
		try{
			
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");

			Cells cells = worksheet.getCells();

			cells.setRowHeight(0, 20.0f);
			Cell cell = cells.getCell("A1");
		    cell.setValue("BÁO CÁO HÀNG NỢ");   	
		    
		    cells.setRowHeight(2, 18.0f);
		    cell = cells.getCell("A2"); 
		    getCellStyle(workbook,"A2",Color.NAVY,true,10);
		    cell.setValue("Từ ngày: " + obj.gettungay());	
		    	
		    
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("B2"); 
		    getCellStyle(workbook,"B2",Color.NAVY,true,9);
			cell.setValue("Đến ngày: " + obj.getdenngay());  
			 
		    cells.setRowHeight(4, 18.0f);
		    cell = cells.getCell("A3");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
		    
		    cells.setRowHeight(5, 18.0f);
		    cell = cells.getCell("A4");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());
			
		    cell = cells.getCell("DA1"); 	cell.setValue("KenhBanHang");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DB1"); 	cell.setValue("DonViKinhDoanh");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DC1"); 	cell.setValue("Vung");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DD1"); 	cell.setValue("KhuVuc");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DE1"); 	cell.setValue("NhanHang");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DF1"); 	cell.setValue("ChungLoai");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DG1"); 	cell.setValue("NgayHD");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DH1"); 	cell.setValue("SoDatHang");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DI1"); 	cell.setValue("TinhThanh");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DJ1"); 	cell.setValue("QuanHuyen");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DK1"); 	cell.setValue("MaNpp");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DL1"); 	cell.setValue("TenNpp");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DM1"); 	cell.setValue("HDTaiChinh");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DN1"); 	cell.setValue("SoPhieu");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DO1"); 	cell.setValue("SKU");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DP1"); 	cell.setValue("TenSKU");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DQ1"); 	cell.setValue("SoLuongHD");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DR1"); 	cell.setValue("SoLuongXK");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DS1"); 	cell.setValue("TienChuaXuat");  ReportAPI.setCellHeader(cell);
		}
		catch(Exception ex)
		{
			throw new Exception("Bao cao bi loi khi khoi tao");
		}
	}
	
	private boolean CreateStaticData(Workbook workbook, IStockintransit obj) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
		
	    String query = " SELECT KBH.TEN AS CHANNEL,DVKD.DONVIKINHDOANH,V.TEN AS REGION, KV.TEN AS AREA,NHAN.TEN AS BRANDS,CL.TEN AS CATEGORY,"+
		 		" NH.NGAYCHUNGTU AS SHIPDATE ,NH.DATHANG_FK AS SODATHANG, TT.TEN AS PROVINCE,QH.TEN AS QUANHUYEN,"+
		 		" NPP.SITECODE AS DISTRIBUTOR_CODE, NPP.TEN AS DISTRIBUTOR,NH.HDTAICHINH AS DOCNO ,NH.PK_SEQ ,"+ 
		 		" NHSP.SANPHAM_FK AS SKU_CODE,SP.TEN AS SKU,  NHSP.SOLUONG  AS SOLUONGHD,"+ 
		 		"(SELECT ISNULL( SUM( cast( NH_SP1.SOLUONG as numeric(18, 0) ) ) , 0) FROM NHAPHANG NH1"+ 
		 		" INNER JOIN NHAPHANG_SP NH_SP1"+
		 		" ON NH1.PK_SEQ=NH_SP1.NHAPHANG_FK WHERE  NH.CHUNGTU=NH1.HDTAICHINH AND NH_SP1.SANPHAM_FK=NHSP.SANPHAM_FK"+
		 		" ) AS SOLUONGXUATKHO ,NHSP.GIANET AS TIENCHUAXUAT "+
		 		" FROM HDNHAPHANG NH"+  
		 		" INNER JOIN HDNHAPHANG_SP NHSP ON NH.PK_SEQ = NHSP.NHAPHANG_FK"+  
		 		" INNER JOIN SANPHAM SP ON SP.MA = NHSP.SANPHAM_FK"+  
		 		" INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK"+  
		 		" INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = NH.NPP_FK"+      		 
		 		" LEFT JOIN KHUVUC KV ON KV.PK_SEQ = NPP.KHUVUC_FK"+  
		 		" LEFT JOIN VUNG V ON V.PK_SEQ = KV.VUNG_FK"+  
		 		" LEFT JOIN NHANHANG NHAN ON NHAN.PK_SEQ = SP.NHANHANG_FK"+  
		 		" LEFT JOIN CHUNGLOAI CL ON CL.PK_SEQ = SP.CHUNGLOAI_FK"+  
		 		" LEFT JOIN TINHTHANH TT ON TT.PK_SEQ = NPP.TINHTHANH_FK"+  
		 		" LEFT JOIN QUANHUYEN QH ON QH.PK_SEQ=NPP.QUANHUYEN_FK"+
		 		" LEFT JOIN KENHBANHANG KBH ON KBH.PK_SEQ = NH.KBH_FK"+  
		 		" WHERE NH.TRANGTHAI =0 AND  NH.NGAYCHUNGTU >= '"+ obj.gettungay() +"' AND NH.NGAYCHUNGTU <= '"+ obj.getdenngay() +"' ";
	    
	    if(obj.getvungId().length() > 0)
	    	query += " and v.pk_seq = '" + obj.getvungId() + "' ";
	    
	    if(obj.getkhuvucId().length() > 0)
	    	query += " and kv.pk_seq = '" + obj.getkhuvucId() + "' ";
	    
	    if(obj.getkenhId().length() > 0)
	    	query += " and kbh.pk_seq = '" + obj.getkenhId() + "' ";
	    
	    if(obj.getnppId().length() > 0)
	    	query += " and npp.pk_seq = '" + obj.getnppId() + "' ";
	  
		System.out.println("1.Khoi tao DL : " + query);
		ResultSet rs = db.get(query);
		
		int i = 2;
		if(rs!=null)
		{
			try 
			{
				for(int j = 0; j < 15; j++)
					cells.setColumnWidth(i, 15.0f);
				
				Cell cell = null;
				while(rs.next())
				{
					String kenh = rs.getString("channel");
					String dvkd = rs.getString("donvikinhdoanh");
					String vung = rs.getString("region");
					String khuvuc = rs.getString("area");
					String nhanhang = rs.getString("brands");
					String chungloai = rs.getString("category");
					String shipdate = rs.getString("shipdate");
					String sodathang = rs.getString("sodathang");										
					String tinhthanh = rs.getString("province");
					String qh = rs.getString("quanhuyen");	
					String distcode = rs.getString("distributor_code");
					String dist = rs.getString("distributor");
					String docno = rs.getString("docno");
					String pkseq = rs.getString("pk_seq");
					String sku_code = rs.getString("sku_code");
					String sku = rs.getString("sku");
					double soluonghd = rs.getDouble("soluonghd");
					double soluongxuatkho = rs.getDouble("soluongxuatkho");
					//Float tienchuaxuat = rs.getFloat("tienchuaxuat");
            		
					cell = cells.getCell("DA" + String.valueOf(i)); 	cell.setValue(kenh);
					cell = cells.getCell("DB" + String.valueOf(i));  	cell.setValue(dvkd);
					cell = cells.getCell("DC" + String.valueOf(i));  	cell.setValue(vung);
					cell = cells.getCell("DD" + String.valueOf(i));  	cell.setValue(khuvuc);
					cell = cells.getCell("DE" + String.valueOf(i));  	cell.setValue(nhanhang);
					cell = cells.getCell("DF" + String.valueOf(i));  	cell.setValue(chungloai);				
					cell = cells.getCell("DG" + String.valueOf(i)); 	cell.setValue(shipdate);
					cell = cells.getCell("DH" + String.valueOf(i));  	cell.setValue(sodathang);
					cell = cells.getCell("DI" + String.valueOf(i));  	cell.setValue(tinhthanh);
					cell = cells.getCell("DJ" + String.valueOf(i));  	cell.setValue(qh);
					
					cell = cells.getCell("DK" + String.valueOf(i));  	cell.setValue(distcode);
					cell = cells.getCell("DL" + String.valueOf(i));  	cell.setValue(dist);
					cell = cells.getCell("DM" + String.valueOf(i));  	cell.setValue(docno);
					cell = cells.getCell("DN" + String.valueOf(i));  	cell.setValue(pkseq);
					cell = cells.getCell("DO" + String.valueOf(i));  	cell.setValue(sku_code);
					cell = cells.getCell("DP" + String.valueOf(i));  	cell.setValue(sku);
					cell = cells.getCell("DQ" + String.valueOf(i));  	cell.setValue(soluonghd);
					cell = cells.getCell("DR" + String.valueOf(i));  	cell.setValue(soluongxuatkho);
					cell = cells.getCell("DS" + String.valueOf(i));  	cell.setValue((soluonghd-soluongxuatkho) *rs.getDouble("tienchuaxuat"));
					
					i++;
				}
				if(rs!=null)
					rs.close();
				if(db != null) 
					db.shutDown();
				if(i==2){
					obj.setMsg("Khong co bao cao trong thoi gian nay...");
				}
			
			} 
			catch (Exception e) 
			{
				System.out.println("115.Error: " + e.getMessage());
			}
		} 
		else 
		{
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

}
