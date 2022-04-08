package geso.dms.center.servlets.reports;
 
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class DailyStockTTSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
  
    public DailyStockTTSvl() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		Utility util = new Utility();
		HttpSession session = request.getSession();
		String userTen = (String) session.getAttribute("userTen");
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		
		IStockintransit obj = new Stockintransit();	
		obj.settungay("");
		obj.setdenngay("");
		obj.setMsg("");
		obj.setuserId(userId);
		obj.setnppId("");
		obj.init();
		obj.setuserId(userId);
		session.setAttribute("obj", obj);
		session.setAttribute("userTen", userTen);
		String nextJSP = "/AHF/pages/Center/rp_DailyStock_center.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		OutputStream out = response.getOutputStream();
		IStockintransit obj = new Stockintransit();	
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		//String nextJSP = "/AHF/pages/Center/rp_DailyStock_center.jsp";
		Utility util = new Utility();
		boolean bfasle = true;
		try
		{	
			obj.setuserTen((String) session.getAttribute("userTen")!=null?
					(String) session.getAttribute("userTen"):"");
			
			obj.settungay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays")))!=null?
					util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays"))):"");
			
			obj.setdenngay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays")))!=null?
					util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays"))):"");
			
			obj.setuserId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"))):"");
			
			obj.setnppId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")))!=null?
					util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"))):"");
			
			obj.setkenhId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")))!=null?
					util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId"))):"");
			
			obj.setdvkdId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")))!=null?
					util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId"))):"");
			
			obj.setvungId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")))!=null?
					util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"))):"");
			
			obj.setnhanhangId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhanhangId")))!=null?
					util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhanhangId"))):"");
			
			obj.setkhuvucId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")))!=null?
					util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"))):"");
			
			obj.setchungloaiId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chungloaiId")))!=null?
					util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chungloaiId"))):"");
			
			obj.init();
			session.setAttribute("obj", obj);
			
			String action = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));
			if(action.equals("taomoi"))
			{
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "Attachment; filename=TonKhoHangNgay" + this.getPiVotName() + ".xlsm");

	        CreatePivotTable(out, response, request, obj, bfasle);
			}
			else
			{
				response.sendRedirect("/AHF/pages/Center/rp_DailyStock_center.jsp");
			}
	    }
	    catch (Exception ex)
	    {
	        ex.printStackTrace();
	        // say sorry
	        response.setContentType("text/html");
	        PrintWriter writer = new PrintWriter(out);
	
	        writer.println("<html>");
	        writer.println("<head>");
	        writer.println("<title>sorry</title>");
	        writer.println("</head>");
	        writer.println("<body>");
	        writer.println("<h1>Xin loi, khong the tao pivot table...</h1>");
	        ex.printStackTrace(writer);
	        writer.println("</body>");
	        writer.println("</html>");
	        writer.close();
	    }
	}
	private void CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj, boolean bfasle) throws IOException
    {    
		String chuoi=getServletContext().getInitParameter("path") + "\\TonKhoHangNgayTT.xlsm";
		
		FileInputStream fstream = new FileInputStream(chuoi);
		
		//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\TonKhoHangNgayTT.xlsm");
		//FileInputStream fstream = new FileInputStream(f) ;
		
		Workbook workbook = new Workbook();

		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
	     
	    CreateStaticHeader(workbook, obj.gettungay(),obj.getdenngay(),obj.getuserTen());
	     //Buoc3 
	    CreateStaticData(workbook,obj,bfasle);
	     
	    workbook.save(out);
	    fstream.close();
   }
	
	private void CreateStaticHeader(Workbook workbook, String tungay, String denngay, String UserName) 
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");

		Cells cells = worksheet.getCells();

		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
	    cell.setValue("TỒN KHO HÀNG NGÀY");   	
	    
	    cells.setRowHeight(2, 20.0f);
	    cell = cells.getCell("A3"); 
	    getCellStyle(workbook,"A3",Color.NAVY,true,9);	    
	    cell.setValue("Từ ngày: " + tungay);
	    
	    
	    cells.setRowHeight(3, 20.0f);
	    cell = cells.getCell("B3"); getCellStyle(workbook,"B3",Color.NAVY,true,9);	        
	    cell.setValue("Đến ngày: " + denngay);    
	    
	    cells.setRowHeight(4, 20.0f);
	    cell = cells.getCell("A4");getCellStyle(workbook,"A4",Color.NAVY,true,9);
	    cell.setValue("Ngày báo cáo: " + this.getDateTime());
	    
	    cells.setRowHeight(5, 20.0f);
	    cell = cells.getCell("A5");getCellStyle(workbook,"A5",Color.NAVY,true,9);
	    cell.setValue("Được tạo bởi:  " + UserName);
			    
	    cell = cells.getCell("EA1"); cell.setValue("KenhBanHang");
	    cell = cells.getCell("EB1"); cell.setValue("DonViKinhDoanh");
	    cell = cells.getCell("EC1"); cell.setValue("ChiNhanh");
	    cell = cells.getCell("ED1"); cell.setValue("Vung");
	    cell = cells.getCell("EE1"); cell.setValue("MaNhaPhanPhoi");
	    cell = cells.getCell("EF1"); cell.setValue("NhaPhanPhoi"); //6
	    cell = cells.getCell("EG1"); cell.setValue("NhanHang");
	    cell = cells.getCell("EH1"); cell.setValue("ChungLoai");
	    cell = cells.getCell("EI1"); cell.setValue("MaSanPham");
	    cell = cells.getCell("EJ1"); cell.setValue("TenSanPham"); //9
	    cell = cells.getCell("EK1"); cell.setValue("Kho");  //10
	    cell = cells.getCell("EL1"); cell.setValue("SoLuongLe");	//11  
	    cell = cells.getCell("EM1"); cell.setValue("SoTien");
	    cell = cells.getCell("EN1"); cell.setValue("Date");
	    cell = cells.getCell("EO1"); cell.setValue("SanLuong(Kg)");
	   
	}
	
	private void CreateStaticData(Workbook workbook, IStockintransit obj, boolean bfasle) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    dbutils db = new dbutils();

	    String sql = " select isnull(d.ten, 'Chua xac dinh') as Chanel, h.ten as Region," +
	    		" f.ten as Area, e.ten as Distributor, e.sitecode as Distcode, "+
		   " b.ma  +'_'+  b.ten as SKU, b.ma as SKUcode, a.ngay as Date, c.ten as Warehouse, g.ten as Province, "+
		   " i.donvikinhdoanh as BusinessUnit, k.ten as Brands, j.ten as Category, a.soluong as Piece, "+
		   " case when a.soluong is null then 0 else a.soluong/isnull(qc.soluong1,1) end as Quatity,"+   
		   " case when a.soluong * nppk.giamua*1.1 is null then 0 else a.soluong * nppk.giamua*1.1 end as Amount, "+
		   " case when a.soluong * isnull(b.trongluong,0)is null then 0 else a.soluong * isnull(b.trongluong,0) end as SanLuong "+
		   " from (" +
		   "select * from tonkhongay " +
			" where ngay >='" + obj.gettungay() + "' and ngay <= '" + obj.getdenngay() + "' and soluong > 0" +
		   ") a inner join sanpham b on a.sanpham_fk = b.pk_seq inner join kho c on a.kho_fk = c.pk_seq " + 
		   " inner join kenhbanhang d on a.kbh_fk = d.pk_seq	inner join nhaphanphoi e on a.npp_fk = e.pk_seq " +
		   " inner join khuvuc f on e.khuvuc_fk = f.pk_seq inner join tinhthanh g on e.tinhthanh_fk = g.pk_seq " +
		   " inner join vung h on f.vung_fk = h.pk_seq inner join donvikinhdoanh i on b.dvkd_fk = i.pk_seq " +
		   " inner join chungloai j on b.chungloai_fk = j.pk_seq inner join nhanhang k on b.nhanhang_fk = k.pk_seq " + 
	       " left join ( "+
		   " select distinct bgm.kenh_fk as kbh_fk,bgm_sp.sanpham_fk,bgmnpp.npp_fk,bgm_sp.giamuanpp as giamua from banggiamuanpp_npp bgmnpp "+ 
		   " inner join banggiamuanpp bgm on bgm.pk_seq = bgmnpp.banggiamuanpp_fk "+
		   " inner join bgmuanpp_sanpham bgm_sp on bgm_sp.bgmuanpp_fk = bgm.pk_seq ";
		
	    	if(obj.getnppId().length() > 0)
	    		sql = sql + " where bgmnpp.npp_fk ='"+ obj.getnppId() +"'";
	    
		sql = sql + " ) nppk on " +
        " nppk.npp_fk = a.npp_fk " + 
        " and nppk.sanpham_fk = a.sanpham_fk and nppk.kbh_fk = a.kbh_fk " + 
	    " left join quycach qc on qc.dvdl1_fk = b.dvdl_fk and a.sanpham_fk = qc.sanpham_fk where 1=1  ";
	    
		geso.dms.center.util.Utility Uti_center = new geso.dms.center.util.Utility();
		
		if (obj.getkenhId().length() > 0)
			sql = sql + " and d.pk_seq ='" + obj.getkenhId() + "'";
		else
			sql = sql + " and d.pk_seq in " + Uti_center.quyen_kenh(obj.getuserId());
		
		if (obj.getvungId().length() > 0)
			sql = sql + " and h.pk_seq ='" + obj.getvungId() + "'";
		if (obj.getkhuvucId().length() > 0)
			sql = sql + " and f.pk_seq ='" + obj.getkhuvucId() + "'";
		if (obj.getdvkdId().length() > 0)
			sql = sql + " and i.PK_SEQ ='" + obj.getdvkdId() + "'";
		
		if(obj.getnppId().length() > 0)
			sql = sql + " and e.pk_seq ='" + obj.getnppId() + "'";
		else
			sql = sql + " and e.pk_seq in " + Uti_center.quyen_npp(obj.getuserId());
		
		if (obj.getnhanhangId().length() > 0)
			sql = sql + " and k.pk_seq ='" + obj.getnhanhangId() + "'";
		if (obj.getchungloaiId().length() > 0)
			sql = sql + " and j.pk_seq ='" + obj.getchungloaiId() + "'";
		
		sql = sql + " and b.pk_seq in " + Uti_center.quyen_sanpham(obj.getuserId());
	    
	    //String sql = "select * from BCTonKhoNgay";
		 System.out.print("Ton kho ngay: " + sql + "\n");
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
				cells.setColumnWidth(8, 15.0f);
				cells.setColumnWidth(9, 15.0f);
				cells.setColumnWidth(10, 15.0f);
				cells.setColumnWidth(11, 15.0f);
				cells.setColumnWidth(12, 15.0f);
				
				//set do rong cho dong
				for(int j = 1; j <= 7; j++)
					cells.setRowHeight(j, 14.0f);
				
				Cell cell = null;
				long Piece = 0;
				float Amount = 0;
				while(rs.next())
				{ 
					String Channel = rs.getString("Chanel");
					String Region = rs.getString("Region");
					String Area = rs.getString("Area");				
					String Distributor = rs.getString("Distributor");
					String DistributorCode = rs.getString("Distcode");			
					String Sku = rs.getString("SKU");
					String SkuCode = rs.getString("SKUcode");
					String Warehouse = rs.getString("Warehouse");
					String Date= rs.getString("Date");
					
					if(rs.getString("Amount") != null);
					 	Amount = rs.getLong("Amount");
					 	
					if(rs.getString("Piece") != null)
						Piece = rs.getLong("Piece");
					
					String BusinessUnit = "HPC";
					if(rs.getString("BusinessUnit") != null)
						BusinessUnit = rs.getString("BusinessUnit");
					
					String Brands = rs.getString("Brands");
					String Category = rs.getString("Category");
					
					cell = cells.getCell("EA" + Integer.toString(i)); cell.setValue(Channel); //0
					cell = cells.getCell("EB" + Integer.toString(i)); cell.setValue(BusinessUnit); //1
					cell = cells.getCell("EC" + Integer.toString(i)); cell.setValue(Region); //2
					cell = cells.getCell("ED" + Integer.toString(i)); cell.setValue(Area);   ///3
					cell = cells.getCell("EE" + Integer.toString(i)); cell.setValue(DistributorCode); //4
					cell = cells.getCell("EF" + Integer.toString(i)); cell.setValue(Distributor); //5
					cell = cells.getCell("EG" + Integer.toString(i)); cell.setValue(Brands); //6
					cell = cells.getCell("EH" + Integer.toString(i)); cell.setValue(Category); //7
					cell = cells.getCell("EI" + Integer.toString(i)); cell.setValue(SkuCode); //8
					cell = cells.getCell("EJ" + Integer.toString(i)); cell.setValue(Sku); //9
					cell = cells.getCell("EK" + Integer.toString(i)); cell.setValue(Warehouse); //10
					cell = cells.getCell("EL" + Integer.toString(i)); cell.setValue(Piece); //11
					cell = cells.getCell("EM" + Integer.toString(i)); cell.setValue(Amount); //12
					cell = cells.getCell("EN" + Integer.toString(i)); cell.setValue(Date);	//13
					cell = cells.getCell("EO" + Integer.toString(i)); cell.setValue(rs.getFloat("sanluong"));	//13
					i++;
				}
			
			if(rs!= null)
				rs.close();
			db.shutDown();
			
	 	    bfasle=true;
			}
			catch (Exception e)
			{
				bfasle=false;
				e.printStackTrace(); 
				System.out.println("Error : loi daily stock : " +e.toString());
			}
		}
		else
		{
			bfasle=false;
		}
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
	
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy :  hh-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private String getPiVotName()
	{
		String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
	    String name = sdf.format(cal.getTime());
	    name = name.replaceAll("-", "");
	    name = name.replaceAll(" ", "_");
	    name = name.replaceAll(":", "");
	    return "_" + name;
	    
	}
}
