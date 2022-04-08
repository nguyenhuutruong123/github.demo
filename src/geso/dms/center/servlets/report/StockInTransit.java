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


public class StockInTransit extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	//ICenterReport obj;

	
	public String getQuery(HttpServletRequest request,String sql,String userId)
	{
     String   query = 	"\n SELECT distinct  KBH.PK_SEQ AS KENHID, KBH.TEN AS KENH,	VUNG.PK_SEQ AS VUNGID, VUNG.TEN AS VUNG,NHAPHANG.NGAYNHAN as Pridate," +
     					"\n KV.PK_SEQ AS KVID, KV.TEN AS KHUVUC,ISNULL(QH.PK_SEQ,0) AS QHID, ISNULL(QH.TEN,'') AS QUANHUYEN," +
     					"\n NPP.MA AS NPPID, NPP.TEN AS NPPTEN,ISNULL(GSBH.PK_SEQ,0) AS GSBHID, ISNULL(GSBH.TEN, '') AS GSBH," +
     					"\n isnull(NHAPHANG.HDTAICHINH,'NA') AS CHUNGTU,DVKD.PK_SEQ AS DVKDID, DVKD.DONVIKINHDOANH,NHAN.PK_SEQ, NHAN.TEN AS NHANTEN,CHUNGLOAI.PK_SEQ, CHUNGLOAI.TEN AS CLTEN," +
     					"\n SP.MA AS SPMA, SP.TEN AS SPTEN, isnull(NHAPHANG_SP.SOLUONGnhan,0) AS QUANTITY, isnull(NHAPHANG_SP.SOLUONGnhan*bg_npp_sp.GIAMUANPP,0) AS AMOUNT ,TT.TEN AS TINHTHANH, po.SendingDate," +
     					"\n Nhaphang.SO_Number as SoSO, NHAPHANG.dondathang_fk AS SoPO, kho.ten as tenkho, "+
     					"	case when  ( sp.DVDL_FK = dvdl.PK_SEQ) \n"+
     					"				then (select top(1) round((NHAPHANG_SP.SOLUONG*soluong2)/SOLUONG1,1) from QUYCACH qc where (DVDL2_FK =100018 or DVDL2_FK = 1200532) and  qc.SANPHAM_FK = sp.PK_SEQ   order by DVDL2_FK) \n"+
     					"	else \n"+
     					"	 NHAPHANG_SP.soluong end as soluongthung,'' as donvi " +
     					"\n  FROM NHAPHANG NHAPHANG	" +
     					"\n  INNER JOIN NHAPHANG_SP NHAPHANG_SP ON NHAPHANG_SP.NHAPHANG_FK = NHAPHANG.PK_SEQ " +
     					"\n  INNER JOIN SANPHAM SP ON NHAPHANG_SP.SANPHAM_FK = SP.PK_SEQ " +
     					"\n  INNER JOIN NHANHANG NHAN ON NHAN.PK_SEQ = SP.NHANHANG_FK " +
     					"\n  INNER JOIN CHUNGLOAI CHUNGLOAI ON CHUNGLOAI.PK_SEQ = SP.CHUNGLOAI_FK " +
     					"\n  INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK " +
     					"\n  INNER JOIN NHAPHANPHOI NPP ON NHAPHANG.NPP_FK = NPP.PK_SEQ " +
     					"\n  INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ = NHAPHANG.KBH_FK " +
     					"\n  INNER JOIN KHUVUC KV ON KV.PK_SEQ = NPP.KHUVUC_FK	" +
     					"\n  INNER JOIN VUNG VUNG ON VUNG.PK_SEQ = KV.VUNG_FK " +
     					"\n  LEFT JOIN QUANHUYEN QH on QH.PK_SEQ = NPP.QUANHUYEN_FK " +
     					"\n LEFT join DONVIDOLUONG dvdl on dvdl.DONVI = NHAPHANG_SP.DONVI "+
     					"\n  left join BANGGIAMUANPP_NPP bg_npp on bg_npp.NPP_FK = npp.PK_SEQ and bg_npp.NPP_FK = npp.pk_seq    \n" +
     					"\n  left join BGMUANPP_SANPHAM bg_npp_sp on bg_npp_sp.sanpham_fk = sp.pk_seq and bg_npp_sp.BGMUANPP_FK = bg_npp.BANGGIAMUANPP_FK   \n" +
     					"\n  LEFT JOIN TINHTHANH TT ON TT.PK_SEQ = NPP.TINHTHANH_FK "+
     					"\n  LEFT JOIN DONDATHANG DDH ON DDH.PK_SEQ = NHAPHANG.DATHANG_FK " +
     					"\n  LEFT JOIN GIAMSATBANHANG GSBH ON GSBH.PK_SEQ = DDH.GSBH_FK " +
     					"\n  left join ERP_DONDATHANG po on po.PK_SEQ = NHAPHANG.DONDATHANG_FK \n"+
     					"\n left join KHO  kho on kho.PK_SEQ = NHAPHANG_SP.Kho_FK " +
     					"\n  WHERE NHAPHANG.TRANGTHAI = '0' ";//AND NHAPHANG.NGAYCHUNGTU >='" + obj.getFromDate() + "' AND NHAPHANG.NGAYCHUNGTU <= '" + obj.getToDate() + "'";
    	//phanquyen
		geso.dms.center.util.Utility ut = new geso.dms.center.util.Utility();
		query += " and npp.pk_seq in " + ut.quyen_npp(userId) + " and kbh.pk_seq in " + ut.quyen_kenh(userId) + " and sp.pk_seq in "+ ut.quyen_sanpham(userId);

        if(sql.length()>0)
        	query = query + sql;
        
       System.out.println("Hang dang nhap kho: " + query);
       return query;
	}
	

    public StockInTransit() {
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
			String nextJSP = "/AHF/pages/Center/StockInTransit.jsp";
			response.sendRedirect(nextJSP);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
	    IStockintransit obj = new Stockintransit();
	    String userId = (String) session.getAttribute("userId");  
	    String userTen = (String) session.getAttribute("userTen"); 

	    if(userId == null)
	    	userId = "";
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
		 
		 String []fieldsHien = request.getParameterValues("fieldsHien");
		 obj.setFieldShow(fieldsHien);
		 
		 String [] fieldsAn =request.getParameterValues("fieldsAn");
			obj.setFieldHidden(fieldsAn)  ;
		 
		 String sql ="";
		 if(tungay.length()>0)
			 sql = sql + " and NHAPHANG.NGAYCHUNGTU >='"+ tungay +"'";
		 if(denngay.length()>0) 
			 sql = sql + " and  CONVERT(VARCHAR(10), NHAPHANG.NGAYCHUNGTU, 120) <='"+ denngay +"'";
		 if(kenhId.length()>0) 
			 sql = sql + " and kbh.pk_seq ='"+ kenhId +"'";
		 if(vungId.length()>0) 
			 sql = sql +" and vung.pk_seq ='"+ vungId +"'";
		 if(khuvucId.length() > 0)
			 sql = sql + " and kv.pk_seq ='"+ khuvucId +"'";
		 if(dvkdId.length()> 0) 
			 sql = sql +" and sp.dvkd_fk ='"+ dvkdId +"'";
		 if(nppId.length()>0) 
			 sql = sql +" and npp.pk_seq ='"+ nppId +"'";
		 if(gsbhId.length()>0) 
			 sql = sql +" and gsbh.pk_seq ='"+ gsbhId +"'";
		 if(nhanhangId.length()>0) 
			 sql = sql +" and nhan.pk_seq ='"+ nhanhangId +"'";
		 if(chungloaiId.length()>0)
			 sql = sql +" and chungloai.pk_seq ='"+ chungloaiId +"'";
		 if(dvdlId.length()>0) 
			 sql = sql + " and sp.dvdl_fk ='"+ dvdlId +"'";
		 if(sanphamId.length()>0) 
			 sql = sql + " and sp.pk_seq = '"+ sanphamId +"'";
		 
		 String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		 if(tungay.length() > 0 && denngay.length() > 0)
		 {
				 if(action.equals("tao"))
				 {
					try{
						//request.setCharacterEncoding("utf-8");
						//response.setCharacterEncoding("utf-8");			
						//response.setContentType("application/vnd.ms-excel");
				        //response.setHeader("Content-Disposition", "attachment; filename=HangChuaNhapKho.xls");
						response.setContentType("application/xlsm");
						response.setHeader("Content-Disposition", "attachment; filename=HangChuaNhapKho.xlsm");
				        OutputStream out = response.getOutputStream();
				        System.out.println("chuoi noi them:" + sql);
				        String query=this.getQuery(request,sql,userId); 
				        
				        CreatePivotTable(out, response, request,fieldsHien,obj,query);	// Create PivotTable 
				        
					}catch(Exception ex){
						System.out.println("Loi Tai Day : "+ ex.toString());
						obj.setMsg("Khong The Tao Duoc Bao Cao. Loi : "+ ex.toString());
					}
				 }
		 }
		 else
			 obj.setMsg("Ban phai chon ngay ");
		
	 	obj.init();	    
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Center/StockInTransit.jsp";
		response.sendRedirect(nextJSP);
	}
	private void CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,String[] manghien,IStockintransit obj ,String query)throws Exception
	{
		try 
		{
			FileInputStream fstream = null;
			Workbook workbook = new Workbook();		
			
			
			String fstreamstr = getServletContext().getInitParameter("path") + "\\HangChuaNhapKhoTT.xlsm";
			//fstream = new FileInputStream("C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\Best\\pages\\Templates\\HangChuaNhapKhoTT.xlsm");		
			fstream = new FileInputStream(fstreamstr);
			//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\HangChuaNhapKhoTT.xlsm");
			//FileInputStream fstream = new FileInputStream(f) ;
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

			CreateHeader(workbook,obj); 
			FillData(workbook,manghien,query); 
			workbook.save(out);
			
			fstream.close();
		} 
		catch (Exception ex) 
		{
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}
	private void CreateHeader(Workbook workbook,IStockintransit obj) 
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
	    
	    String tieude = "BÁO CÁO HÀNG CHƯA NHẬP KHO";
	    ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);
	           
	    String message = "có VAT";
		
		cells.setRowHeight(2, 18.0f);
		cell = cells.getCell("A2");
		ReportAPI.getCellStyle(cell, Color.RED, true, 9, message);   

		cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("A4");
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ ngày: " + obj.gettungay() + "" );
	   
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("C4"); 
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến ngày: " + obj.getdenngay() + "" );
	    
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A5");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A6");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());
	    
	    
	    cell = cells.getCell("AA1");		cell.setValue("KenhBanHang");			//ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AB1");		cell.setValue("DonviKinhDoanh");				//ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AC1");		cell.setValue("ChiNhanh");			//ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AD1");		cell.setValue("KhuVuc");				//ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AE1");		cell.setValue("MaNhaPhanPhoi");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AF1");		cell.setValue("NhaPhanPhoi");			ReportAPI.setCellHeader(cell);	
		cell = cells.getCell("AG1");		cell.setValue("NhanHang");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AH1");		cell.setValue("ChungLoai");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AI1");		cell.setValue("SoChungTu");		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AJ1");		cell.setValue("MaSanPham");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AK1");		cell.setValue("TenSanPham");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AL1");		cell.setValue("TinhThanh");					ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AM1");		cell.setValue("QuanHuyen");			ReportAPI.setCellHeader(cell);	
		cell = cells.getCell("AN1");		cell.setValue("NgayChungTu");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AO1");		cell.setValue("SoLuongLe");			ReportAPI.setCellHeader(cell);	    
		cell = cells.getCell("AP1");		cell.setValue("SoTien");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AQ1");		cell.setValue("NgayChot");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AR1");		cell.setValue("SoPO");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AS1");		cell.setValue("SoSO");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AT1");		cell.setValue("Kho");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AU1");		cell.setValue("Soluongthung");			ReportAPI.setCellHeader(cell);
	}
	private void FillData(Workbook workbook,String[] manghien,String query) throws Exception 
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		
		for(int i = 0; i < 5; i++)
		{
	    	cells.setColumnWidth(i, 15.0f);
	    	if(i == 4)
	    		cells.setColumnWidth(i, 30.0f);
	    }	
		
		dbutils db = new dbutils();

		ResultSet rs = db.get(query);
		int index = 2;
	    Cell cell = null;	    
	    while (rs.next()) {
	    	cell = cells.getCell("AA" + String.valueOf(index));		cell.setValue(rs.getString("KENH"));
	    	cell = cells.getCell("AB" + String.valueOf(index));		cell.setValue(rs.getString("DONVIKINHDOANH"));	
			cell = cells.getCell("AC" + String.valueOf(index));		cell.setValue(rs.getString("VUNG"));
			cell = cells.getCell("AD" + String.valueOf(index));		cell.setValue(rs.getString("KHUVUC"));
			cell = cells.getCell("AE" + String.valueOf(index));		cell.setValue(rs.getString("NPPID"));
			cell = cells.getCell("AF" + String.valueOf(index));		cell.setValue(rs.getString("NPPTEN"));
			cell = cells.getCell("AG" + String.valueOf(index));		cell.setValue(rs.getString("NHANTEN"));
			cell = cells.getCell("AH" + String.valueOf(index));		cell.setValue(rs.getString("CLTEN"));
			cell = cells.getCell("AI" + String.valueOf(index));		cell.setValue(rs.getString("CHUNGTU"));
			cell = cells.getCell("AJ" + String.valueOf(index));		cell.setValue(rs.getString("SPMA"));
			cell = cells.getCell("AK" + String.valueOf(index));		cell.setValue(rs.getString("SPTEN"));
			cell = cells.getCell("AL" + String.valueOf(index));		cell.setValue(rs.getString("TINHTHANH"));	
			cell = cells.getCell("AM" + String.valueOf(index));		cell.setValue(rs.getString("QUANHUYEN")); 
			cell = cells.getCell("AN" + String.valueOf(index));		cell.setValue(rs.getString("Pridate"));
			cell = cells.getCell("AO" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("QUANTITY"))); //14
			cell = cells.getCell("AP" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("AMOUNT")));   //15  
			String SendingDate = rs.getString("SendingDate")==null?"":rs.getString("SendingDate");
			if(SendingDate != "")
				SendingDate = SendingDate.substring(0, 10);
			cell = cells.getCell("AQ" + String.valueOf(index));		cell.setValue(SendingDate);   //15
			cell = cells.getCell("AR" + String.valueOf(index));		cell.setValue(rs.getString("SoPO"));   
			cell = cells.getCell("AS" + String.valueOf(index));		cell.setValue(rs.getString("SoSO")); 
			cell = cells.getCell("AT" + String.valueOf(index));		cell.setValue(rs.getString("tenkho")); 
			cell = cells.getCell("AU" + String.valueOf(index));		cell.setValue(rs.getString("soluongthung")); 
			index++;
		} 
	    if(rs!=null) rs.close();
	    if(db!=null)
	    	db.shutDown();
	    /*
	  
	    Hashtable<String, Integer> selected = new Hashtable<String, Integer>();
			    selected.put("Channel",0);
			    selected.put("Region",1);
			    selected.put("Area",2);
			    selected.put("Sales_Sup",3);
			    selected.put("Distributor",4);
			    selected.put("Billing_number",5);
			    selected.put("SKU",6);
			    selected.put("Brands",7);
			    selected.put("Catergory",8);
			    selected.put("Province",9);
			    selected.put("Town",10);
			    selected.put("Distributor_code",11);
			    selected.put("Business_Unit",12);
			    selected.put("Pridate",13);
			    selected.put("SKU_Code",14);
			    selected.put("Piece",15);
			    selected.put("Amount",16);
			   
			  System.out.println(" Town :" + selected.get("Town"));
			  System.out.println(" Catergory :" + selected.get("Catergory"));
			    */
		/*	 
		ReportAPI.setHidden(workbook,16);
		PivotTables pivotTables = worksheet.getPivotTables();
		String pos = Integer.toString(index-1);	
	    int j = pivotTables.add("=StockInTransit!AA1:AP" + pos,"A11","PivotTable");	   
	    
	    PivotTable pivotTable = pivotTables.get(j);
	    pivotTable.setRowGrand(false);
	    pivotTable.setColumnGrand(true);
	    pivotTable.setAutoFormat(true);  
	    pivotTable.setAutoFormatType(PivotTableAutoFormatType.TABLE10);
	   
	   int dem = 0;
	    for(int i=0;i < manghien.length ;++i)
	    {
	    	System.out.println(" tong so mang  hien:"+ selected.get(manghien[i]) +" mang "+manghien[i]);
	    	int so = selected.get(manghien[i]);
	    	if(so == 15 || so == 16)
	    	{ 
	    		dem++;
	    		pivotTable.addFieldToArea(PivotFieldType.DATA, so);
	    		if(so == 15)
	    			pivotTable.getDataField().setDisplayName("SoLuongLe");
	    		if(so == 16)
	    			pivotTable.getDataField().setDisplayName("SoTien");
	    	}
	    	else
	    		pivotTable.addFieldToArea(PivotFieldType.ROW, so); 		
   	     }
	    
	    //if(dem >1)
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 0);	pivotTable.getRowFields().get(0).setAutoSubtotals(true);
	 	pivotTable.addFieldToArea(PivotFieldType.ROW, 1);	pivotTable.getRowFields().get(1).setAutoSubtotals(true);
	 	pivotTable.addFieldToArea(PivotFieldType.ROW, 2);	pivotTable.getRowFields().get(2).setAutoSubtotals(true);
	 	pivotTable.addFieldToArea(PivotFieldType.ROW, 3);	pivotTable.getRowFields().get(3).setAutoSubtotals(true);
	 	pivotTable.addFieldToArea(PivotFieldType.ROW, 5);	pivotTable.getRowFields().get(4).setAutoSubtotals(true);

	 	pivotTable.addFieldToArea(PivotFieldType.ROW, 10);    pivotTable.getRowFields().get(5).setAutoSubtotals(false);
	 	   
	 	pivotTable.addFieldToArea(PivotFieldType.DATA, 14); 	pivotTable.getDataFields().get(0).setDisplayName("SoLuongLe");
	 	pivotTable.getDataFields().get(0).setNumber(3);
	 	
	 	pivotTable.addFieldToArea(PivotFieldType.DATA, 15); 	pivotTable.getDataFields().get(1).setDisplayName("SoTien");
	 	pivotTable.getDataFields().get(1).setNumber(3);

	    pivotTable.addFieldToArea(PivotFieldType.COLUMN, pivotTable.getDataField());*/
	    
	}
	private String getDateTime() 
 	{
         DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
         Date date = new Date();
         return dateFormat.format(date);	
 	}
}
