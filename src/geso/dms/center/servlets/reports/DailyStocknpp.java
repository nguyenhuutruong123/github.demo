package geso.dms.center.servlets.reports;

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
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.PivotField;
import com.aspose.cells.PivotFieldDataDisplayFormat;
import com.aspose.cells.PivotFieldSubtotalType;
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotFields;
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTableAutoFormatType;
import com.aspose.cells.PivotTables;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class DailyStocknpp extends HttpServlet {	
	
    public DailyStocknpp() {
        super();
    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");	
		IStockintransit obj = new Stockintransit();
		 Utility util=new Utility();
		
		HttpSession session = request.getSession();
		String userTen = (String)session.getAttribute("userTen");
		String querystring=request.getQueryString();
		String userId=	util.getUserId(querystring);
		

		obj.setuserTen(userTen);
		obj.setuserId(userId);
		obj.setMsg("");
		obj.settungay("");
		obj.setdenngay("");
		
		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Center/rp_DailyStocknpp.jsp";
		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
/*		IStockintransit obj = new Stockintransit();
		String nextJSP = "/AHF/pages/Center/rp_DailyStocknpp.jsp";
		Utility util = new Utility();
		try
		    {
			HttpSession session = request.getSession();
			obj.setuserTen((String)session.getAttribute("userTen")!=null? 
					(String)session.getAttribute("userTen"):"");
			
			
			obj.settungay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")))!=null? 
					util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")):""));
			
			
			obj.setdenngay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")))!=null?  
					util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")):""));
			
			obj.setuserId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")))!=null?
						util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")):""));
			
			OutputStream out = response.getOutputStream(); 
			
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=DailyStock1(NPP).xlsm");
			
			boolean isTrue =  CreatePivotTable(out,obj);
	       if(!isTrue){
	    	   
	       }else{
	    	   PrintWriter writer = new PrintWriter(out);
	    	   writer.write("Xin loi. Khong tao duoc bao cao trong thoi gian nay..!!");
	    	   writer.close();
	       }
	     }
		catch (Exception ex) {
			obj.setMsg(ex.getMessage());
			response.sendRedirect(nextJSP);
		}*/
		String nextJSP = "/AHF/pages/Center/rp_DailyStocknpp.jsp";
		Utility util = new Utility();
		IStockintransit obj = new Stockintransit();
		HttpSession session = request.getSession();
		obj.setuserTen((String)session.getAttribute("userTen")!=null? 
				(String)session.getAttribute("userTen"):"");
		
		
		obj.settungay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")))!=null? 
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")):""));
		
		
		obj.setdenngay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")))!=null?  
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")):""));
		
		obj.setuserId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")))!=null?
					util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")):""));
		
		OutputStream out = response.getOutputStream(); 

		try
		{
			
			String userTen = (String)session.getAttribute("userTen");
			String userId=	(String)session.getAttribute("userId");
			
			if(userTen==null) 
				userTen ="";
			if(userId ==null) 
				userId ="";
		
			
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=DailyStock1(NPP).xlsm");

			boolean isTrue =  CreatePivotTable(out,obj);
		       if(isTrue){
		    	   
		       }else{
		    	
		       }
	    }
	    catch (Exception ex)
	    {
	    	obj.setMsg(ex.getMessage());
	    	response.sendRedirect(nextJSP);
	    }
	}
	private boolean CreatePivotTable(OutputStream out,IStockintransit obj) throws Exception
    {   
		
		//FileInputStream fstream;
		//String chuoi=getServletContext().getInitParameter("path") + "\\DailyStock(NPP).xlsm";
		//fstream = new FileInputStream(chuoi);
		File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\DailyStock(NPP).xlsm");
		FileInputStream fstream = new FileInputStream(f) ;
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

	     CreateStaticHeader(workbook, obj.gettungay(),obj.getdenngay(), obj.getuserTen());
	  
	    boolean isTrue = CreateStaticData(workbook,obj);
	    if(!isTrue){
	    	return false;
	    }
	    workbook.save(out);
	    fstream.close();
	    return true;
   }
	
	private void CreateStaticHeader(Workbook workbook, String tungay, String denngay, String UserName)  throws Exception
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    worksheet.setName("Sheet1");
	    Cells cells = worksheet.getCells();
	   
	    
	    
	   	Style style;
	    //cells.setColumnWidth(0, 200.0f);
	    cells.setRowHeight(0, 20.0f);
	    Cell cell = cells.getCell("A1");  
	    cell.setValue("TỒN KHO THEO NGÀY");   	
	    
	  
	    style = cell.getStyle();
	   
	   Font font2 = new Font();
       font2.setColor(Color.RED);//mau chu
       font2.setSize(14);// size chu
       font2.setBold(true);
       style.setFont(font2); 
       style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu       
       cell.setStyle(style);
       
	    cell = cells.getCell("A3"); getCellStyle(workbook,"A3",Color.NAVY,true,9);
       
	    cell.setValue("Từ ngày  " + tungay + "      Đến ngày  " + denngay);    
	    cell = cells.getCell("A4");getCellStyle(workbook,"A4",Color.NAVY,true,9);
	    cell.setValue("Ngày Tạo: " + this.getDateTime());
	    cell = cells.getCell("A5");getCellStyle(workbook,"A5",Color.NAVY,true,9);
	    cell.setValue("Tạo Bởi:  " + UserName);
	    
	  
	    //tieu de, hang dau tien cua bang du lieu, cell la toa do cua exel
	    cell = cells.getCell("EA1"); cell.setValue("KenhBanHang"); 
	    cell = cells.getCell("EB1"); cell.setValue("TenSanPham");
	    cell = cells.getCell("EC1"); cell.setValue("SoLuongQuyLe");
	    cell = cells.getCell("ED1"); cell.setValue("Ngay");	   
	    cell = cells.getCell("EE1"); cell.setValue("MaNhaPhanPhoi");
	    cell = cells.getCell("EF1"); cell.setValue("MaSanPham");
	    cell = cells.getCell("EG1"); cell.setValue("SoLuongThung");
	    cell = cells.getCell("EH1"); cell.setValue("Kho");
	    cell = cells.getCell("EI1"); cell.setValue("DonViKinhDoanh");
	    cell = cells.getCell("EJ1"); cell.setValue("ChungLoai");
	    cell = cells.getCell("EK1"); cell.setValue("NhanHang");
	    cell = cells.getCell("EL1"); cell.setValue("SoTien");
	    cell = cells.getCell("EM1"); cell.setValue("SanLuong");
	  
	 
	}
	private boolean CreateStaticData(Workbook workbook,IStockintransit obj)  throws Exception
	{dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    
	    Utility util = new Utility();
	
			 String sql = " select isnull(d.ten, 'Chưa xác định') as Chanel, f.ten as Region, h.ten as Area, e.ten as Distributor, e.sitecode as Distcode, "+
		   "  b.ten as SKU, b.ma as SKUcode, a.ngay as Date, c.ten as Warehouse, g.ten as Province, "+
		   " i.donvikinhdoanh as BusinessUnit, k.ten as Brands, j.ten as Category, a.soluong as Piece, "+
		   " case when a.soluong is null then 0 else a.soluong/qc.soluong1 end as Quatity,"+   
		   " case when a.soluong * nppk.giamua*1.1 is null then 0 else a.soluong * nppk.giamua*1.1 end as Amount , "+
		   " case when a.soluong * isnull(b.trongluong,0) is null then 0 else a.soluong * isnull(b.trongluong,0) end as SanLuong "+
		   " from tonkhongay a inner join sanpham b on a.sanpham_fk = b.pk_seq inner join kho c on a.kho_fk = c.pk_seq "+ 
		   " left join kenhbanhang d on a.kbh_fk = d.pk_seq	inner join nhaphanphoi e on a.npp_fk = e.pk_seq "+
		   " inner join khuvuc f on e.khuvuc_fk = f.pk_seq left join tinhthanh g on e.tinhthanh_fk = g.pk_seq "+
		   " inner join vung h on f.vung_fk = h.pk_seq inner join donvikinhdoanh i on b.dvkd_fk = i.pk_seq "+
		   " inner join chungloai j on b.chungloai_fk = j.pk_seq inner join nhanhang k on b.nhanhang_fk = k.pk_seq "+ 
	       " left join ( "+
		   " select distinct bgm.kenh_fk as kbh_fk,bgm_sp.sanpham_fk,bgmnpp.npp_fk,bgm_sp.giamuanpp as giamua from banggiamuanpp_npp bgmnpp "+ 
        " inner join banggiamuanpp bgm on bgm.pk_seq = bgmnpp.banggiamuanpp_fk "+
        " inner join bgmuanpp_sanpham bgm_sp on bgm_sp.bgmuanpp_fk = bgm.pk_seq "+
		   " where bgmnpp.npp_fk ='"+util.getIdNhapp(obj.getuserId())+"'"+
		   " ) nppk on "+
        " nppk.npp_fk = a.npp_fk "+ 
        " and nppk.sanpham_fk = a.sanpham_fk and nppk.kbh_fk = a.kbh_fk "+ 
	       " left join quycach qc on qc.dvdl1_fk = b.dvdl_fk and a.sanpham_fk = qc.sanpham_fk "+ 
	       " where a.ngay >='" + obj.gettungay() + "' and a.ngay <= '" + obj.getdenngay() + "' and a.npp_fk = '"+util.getIdNhapp(obj.getuserId())+"' ";
		 System.out.println("Lay Du Lieu :"+sql);

			ResultSet rs = db.get(sql);
		 int i = 2;
		 
		if(rs != null)
		{
			try 
			{// se do rong cho cac cot se dung
				cells.setColumnWidth(0, 15.0f);
				cells.setColumnWidth(1, 15.0f);
				cells.setColumnWidth(2, 15.0f);
				cells.setColumnWidth(3, 15.0f);
				cells.setColumnWidth(4, 15.0f);
				cells.setColumnWidth(5, 15.0f);
				cells.setColumnWidth(6, 15.0f);
				cells.setColumnWidth(7, 15.0f);
				
				
				//set do rong cho dong
				
				
				Cell cell = null;
				while(rs.next())// lap den cuoi bang du lieu
				{ 
				
					//lay tu co so du lieu, gan bien
					String Channel = rs.getString("Chanel");
					String SKU = rs.getString("SKU");				
					double Piece = rs.getDouble("Piece");
					String Date = rs.getString("Date");					
					String DistributorCode = rs.getString("Distributor");
					String SkuCode = rs.getString("SKUcode");					
					double Quantily = rs.getDouble("Quatity");
					String Warehouse = rs.getString("Warehouse");
					String BusinessUnit = rs.getString("BusinessUnit");
					String Category = rs.getString("Category");
					String Brands = rs.getString("Brands");
					
				
					cell = cells.getCell("EA" + Integer.toString(i)); cell.setValue(Channel);
					cell = cells.getCell("EB" + Integer.toString(i)); cell.setValue(SKU);
					cell = cells.getCell("EC" + Integer.toString(i)); cell.setValue(Piece);
					cell = cells.getCell("ED" + Integer.toString(i)); cell.setValue(Date);					
					cell = cells.getCell("EE" + Integer.toString(i)); cell.setValue(DistributorCode);
					cell = cells.getCell("EF" + Integer.toString(i)); cell.setValue(SkuCode);
					cell = cells.getCell("EG" + Integer.toString(i)); cell.setValue(Quantily);
					cell = cells.getCell("EH" + Integer.toString(i)); cell.setValue(Warehouse);			
					cell = cells.getCell("EI" + Integer.toString(i)); cell.setValue(BusinessUnit);
					cell = cells.getCell("EJ" + Integer.toString(i)); cell.setValue(Category);
					cell = cells.getCell("EK" + Integer.toString(i)); cell.setValue(Brands);
					cell = cells.getCell("EL" + Integer.toString(i)); cell.setValue(rs.getDouble("Amount"));
					cell = cells.getCell("EM" + Integer.toString(i)); cell.setValue(rs.getDouble("SanLuong"));
					i++;
					
				}
				if(rs!=null) 	rs.close();
				if(db != null)  db.shutDown();
				
				if(i==2)
					throw new Exception("Không có báo cáo trong thời gian này...!!!");
		
		
		
			 getAn(workbook,156);
			
	    
	   
	}
	catch (Exception e){
		System.out.println("Loi Nek Con : "+e.toString());
		throw new Exception(e.getMessage());
	}
}else{
	return false;
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
	    for(int j = 130; j <= i; j++)
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
}
