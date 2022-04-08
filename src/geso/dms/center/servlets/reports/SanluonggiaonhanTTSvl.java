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
import javax.servlet.annotation.WebServlet;
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

@WebServlet("/SanluonggiaonhanTTSvl")
public class SanluonggiaonhanTTSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public SanluonggiaonhanTTSvl() {
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
		obj.init();
		
		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Center/SanLuongNVGNCenter.jsp";
		response.sendRedirect(nextJSP);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")):""));
		
		obj.setvungId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")):""));
			
		obj.setkhuvucId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")):""));
		
		obj.setnvgnId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nvgnId")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nvgnId")):""));
		
		obj.setnppId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")):""));
		
		obj.setdvkdId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")):""));
		
		obj.setnhanhangId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhanhangId")))!=null?
			util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhanhangId")):""));
		
		obj.setchungloaiId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chungloaiId")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chungloaiId")):""));											
					
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
			sql = sql + " and nh.pk_seq ='" + obj.getnhanhangId() + "'";
		if (obj.getchungloaiId().length() > 0)
			sql = sql + " and cl.pk_seq ='" + obj.getchungloaiId()	+ "'";
		
		if (obj.getnvgnId().length() > 0)
			sql = sql + " and NVGN.PK_SEQ ='" + obj.getnvgnId()	+ "'";
				
		
		System.out.println("SQL la: " + sql + "\n");
		
		String action = (String) util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));
		String nextJSP = "/AHF/pages/Center/SanLuongNVGNCenter.jsp";
		
		
		
		try{
			if (action.equals("Taomoi")) 
			{			
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=DoanhSoNvgnTT.xlsm");
				
				String query = setQuery(sql, obj);
				//System.out.println("Query lay du lieu: " + query + "\n");
		        if(!CreatePivotTable(out,obj,query))
		        {
		        	response.setContentType("text/html");
		            PrintWriter writer = new PrintWriter(out);
		            writer.print("Xin loi khong co bao cao trong thoi gian nay");
		            writer.close();
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
		String sql1=sql;		

		      String query = "";

		      query = "SELECT  NVGN.PK_SEQ AS NVGNID,NVGN.TEN  AS TENNVGN,NPPID, dvkd.donvikinhdoanh as BU, V.TEN AS REGION, KV.TEN AS AREA, NPP.TEN AS DISTRIBUTOR, NPP.SITECODE AS DISTCODE, "+
		    	 	"NH.TEN AS BRAND,  CL.TEN AS CATEGORY, SP.MA + '_' + SP.TEN AS SKU, SP.MA AS SKUCODE, KBH.DIENGIAI AS CHANEL, DH.NGAYNHAP as ngay, "+
		    	 	"SUM(DH.SOLUONG) AS PIECE, sum(DH.soluong * SP.trongluong) as SLTT,  sum(dh.giamua * dh.soluong * 1) as amount, "+
		    	 	"SUM(ISNULL(ROUND((CAST((QC.SOLUONG2 * DH.SOLUONG) AS FLOAT) / CAST(QC.SOLUONG1 AS FLOAT)),3), 0)) AS QUANTITY "+ 
		    	 	"FROM (  "+			    		    	 
		    	 	"SELECT  PXK.NVGN_FK, DH.NGAYNHAP, DH.DDKD_FK, DH.NPP_FK as NPPID, DH.GSBH_FK, DH.KHO_FK, DH.KBH_FK, DH_SP.SANPHAM_FK, "+ 
		    	 	"DH_SP.GIAMUA, DH_SP.SOLUONG, DH_SP.CHIETKHAU "+   
		    	 	"FROM DONHANG DH "+   
		    	 	"INNER JOIN DONHANG_SANPHAM  DH_SP ON DH.PK_SEQ = DH_SP.DONHANG_FK "+
		    	 	"LEFT JOIN PHIEUXUATKHO_DONHANG PXK_DH ON PXK_DH.DONHANG_FK=DH.PK_SEQ "+ 
		    	 	"LEFT JOIN PHIEUXUATKHO PXK ON PXK.PK_SEQ=PXK_DH.PXK_FK AND PXK.TRANGTHAI <> 2 "+
		    	 	"WHERE  DH.TRANGTHAI = 1 AND DH.NGAYNHAP >='"+obj.gettungay()+"' AND DH.NGAYNHAP <= '"+obj.getdenngay()+"' "+
		    	 	") DH "+  
		    	 	"INNER JOIN SANPHAM SP ON DH.SANPHAM_FK = SP.PK_SEQ "+
		    	 	"INNER JOIN NHAPHANPHOI NPP ON DH.NPPID = NPP.PK_SEQ "+ 
		    	 	"INNER JOIN KHUVUC KV ON NPP.KHUVUC_FK = KV.PK_SEQ "+
		    	 	"LEFT JOIN NHANVIENGIAONHAN NVGN ON NVGN.PK_SEQ=DH.NVGN_FK "+
		    	 	"INNER JOIN VUNG V ON KV.VUNG_FK = V.PK_SEQ "+ 
		    	 	"INNER JOIN NHANHANG NH ON SP.NHANHANG_FK = NH.PK_SEQ "+ 
		    	 	"LEFT JOIN CHUNGLOAI CL ON SP.CHUNGLOAI_FK = CL.PK_SEQ "+ 
		    	 	"INNER JOIN kenhbanhang KBH on DH.kbh_fk = KBH.pk_seq "+
		    	 	"INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK "+ 
		    	 	"LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK = SP.PK_SEQ "+ sql1+ 
		    	 	" group by nppid, dvkd.donvikinhdoanh, V.TEN, KV.TEN, NPP.TEN, NPP.SITECODE, NH.TEN, CL.TEN, SP.MA + '_' + SP.TEN, "+
		    	 	"SP.MA, KBH.DIENGIAI, DH.NGAYNHAP ,NVGN.PK_SEQ ,NVGN.TEN";		  		
	 				
				System.out.println("QUERY Doanh So NhanVienGiaoNhan la: " + query);					     
	    
	     return query;			
		
	}	
	
	private boolean CreatePivotTable(OutputStream out, IStockintransit obj, String query)throws Exception 
	{
		boolean isFillData = false;
		//FileInputStream fstream = null;
		Workbook workbook = new Workbook();		
		//String chuoi=getServletContext().getInitParameter("path") + "\\DoanhSoNVGNTT.xlsm";			
		//fstream = new FileInputStream(chuoi);		
		File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\DoanhSoNVGNTT.xlsm");
		FileInputStream fstream = new FileInputStream(f) ;
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		CreateHeader(workbook,obj);
		isFillData = FillData(workbook, obj, query);
			
		if (!isFillData){
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
	    
	    String tieude = "BÁO CÁO DOANH SỐ NHÂN VIÊN GIAO NHẬN";	    				
	    ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);
	    
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
		cell = cells.getCell("DE1");		cell.setValue("NhaPhanPhoi");
		cell = cells.getCell("DF1");		cell.setValue("NhanHang");
		cell = cells.getCell("DG1");		cell.setValue("ChungLoai");
		cell = cells.getCell("DH1");		cell.setValue("MaNhaPhanPhoi");
		cell = cells.getCell("DI1");		cell.setValue("TenSanPham");
		cell = cells.getCell("DJ1");		cell.setValue("MaSanPham");
		cell = cells.getCell("DK1");		cell.setValue("Ngay");
		cell = cells.getCell("DL1");		cell.setValue("SoTien");
		cell = cells.getCell("DM1");		cell.setValue("SoLuong(Le)");
		cell = cells.getCell("DN1");		cell.setValue("SoLuong(Thung)");
		cell = cells.getCell("DO1");		cell.setValue("SanLuong(Kg)");
		cell = cells.getCell("DP1");		cell.setValue("NVGN ID");	
		cell = cells.getCell("DQ1");		cell.setValue("Ten NVGN");	
	}
	
	private boolean FillData(Workbook workbook, IStockintransit obj, String query) throws Exception{
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
				cells.setColumnWidth(8, 20.0f);
				cells.setColumnWidth(9, 20.0f);
				cells.setColumnWidth(10, 20.0f);
				cells.setColumnWidth(11, 20.0f);
				cells.setColumnWidth(12, 20.0f);
				cells.setColumnWidth(13, 20.0f);

				while (rs.next()) 
				{					
					String chanel = rs.getString("chanel");
					String bu = rs.getString("bu");
					String region = rs.getString("region");
					String area = rs.getString("area");
					String distributor = rs.getString("distributor");
					String brand = rs.getString("brand");
					String category = rs.getString("category");
					String discode = rs.getString("distcode");
					String sku = rs.getString("SKU");
					String skuCode = rs.getString("SKUcode");
					String ngay = rs.getString("ngay");				
					float amount = rs.getFloat("amount");
					float piece = rs.getFloat("piece");
					//float q = rs.getFloat("soluong2")*piece/rs.getFloat("soluong1");
					float q = rs.getFloat("quantity");
					float sanluong = rs.getFloat("SLTT");
					String nvgnid = rs.getString("nvgnid");
					String tennvgn = rs.getString("tennvgn");
					
					
					cell = cells.getCell("DA" + Integer.toString(i));	cell.setValue(chanel);	//0
					cell = cells.getCell("DB" + Integer.toString(i));	cell.setValue(bu); 		//1
					cell = cells.getCell("DC" + Integer.toString(i));	cell.setValue(region); 	//2
					cell = cells.getCell("DD" + Integer.toString(i));	cell.setValue(area);	//3
					cell = cells.getCell("DE" + Integer.toString(i));	cell.setValue(distributor); //4					
					cell = cells.getCell("DF" + Integer.toString(i));	cell.setValue(brand);	//5
					cell = cells.getCell("DG" + Integer.toString(i));	cell.setValue(category);//6
					cell = cells.getCell("DH" + Integer.toString(i));	cell.setValue(discode);	//7
					cell = cells.getCell("DI" + Integer.toString(i));	cell.setValue(sku);		//8
					cell = cells.getCell("DJ" + Integer.toString(i));	cell.setValue(skuCode);	//9
					cell = cells.getCell("DK" + Integer.toString(i));	cell.setValue(ngay);	//10
					cell = cells.getCell("DL" + Integer.toString(i));	cell.setValue(amount);	//11
					cell = cells.getCell("DM" + Integer.toString(i));	cell.setValue(piece);	//12
					cell = cells.getCell("DN" + Integer.toString(i));	cell.setValue(q);		//13	
					cell = cells.getCell("DO" + Integer.toString(i));	cell.setValue(sanluong);		//13
					cell = cells.getCell("DP" + Integer.toString(i));	cell.setValue(nvgnid);	//15
					cell = cells.getCell("DQ" + Integer.toString(i));	cell.setValue(tennvgn);	//16
					++i;					
				}

				if (rs != null) rs.close();
				
				if(db != null) db.shutDown();
				
				if(i==2){					
					throw new Exception("Xin lỗi, không có báo cáo với điều kiện đã chọn!");
				}
				  
			}catch(Exception ex){
				throw new Exception(ex.getMessage());
			}
		}else{
			return false;
		}		
		return true;
	}		

}
