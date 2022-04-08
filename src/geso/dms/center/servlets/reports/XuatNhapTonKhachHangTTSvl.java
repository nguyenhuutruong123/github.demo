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

public class XuatNhapTonKhachHangTTSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
  
    public XuatNhapTonKhachHangTTSvl() {
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
		obj.setnppId("");
		obj.init();
		obj.setuserId(userId);
		session.setAttribute("obj", obj);
		session.setAttribute("userTen", userTen);
		String nextJSP = "/AHF/pages/Center/XuatNhapTonKhachHang_center.jsp";
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
				response.setHeader("Content-Disposition", "Attachment; filename=XuatNhapTonKH" + this.getPiVotName() + ".xlsm");
	
		        CreatePivotTable(out, response, request, obj, bfasle);
			}
			else
			{
				response.sendRedirect("/AHF/pages/Center/XuatNhapTonKhachHang_center.jsp");
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
		String chuoi=getServletContext().getInitParameter("path") + "\\XuatNhapTonKhachHangTT.xlsm";
		
		FileInputStream fstream = new FileInputStream(chuoi);
		//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\XuatNhapTonKhachHangTT.xlsm");
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
	    cell.setValue("XUẤT NHẬP TỒN CỦA KHÁCH HÀNG");   	
	    
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
	    cell = cells.getCell("EC1"); cell.setValue("Vung");
	    cell = cells.getCell("ED1"); cell.setValue("KhuVuc");
	    cell = cells.getCell("EE1"); cell.setValue("MaNhaPhanPhoi");
	    cell = cells.getCell("EF1"); cell.setValue("NhaPhanPhoi"); //6
	    cell = cells.getCell("EG1"); cell.setValue("KhachHang"); //7
	    cell = cells.getCell("EH1"); cell.setValue("NhanHang");
	    cell = cells.getCell("EI1"); cell.setValue("ChungLoai");
	    cell = cells.getCell("EJ1"); cell.setValue("MaSanPham");
	    cell = cells.getCell("EK1"); cell.setValue("TenSanPham"); //11
	    cell = cells.getCell("EL1"); cell.setValue("Kho");  //12
	    cell = cells.getCell("EM1"); cell.setValue("Nhap");	//13  
	    cell = cells.getCell("EN1"); cell.setValue("Ban");
	    cell = cells.getCell("EO1"); cell.setValue("TonCuoi");
	   
	}
	
	private void CreateStaticData(Workbook workbook, IStockintransit obj, boolean bfasle) 
	{
		geso.dms.center.util.Utility Uti_center = new geso.dms.center.util.Utility();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    dbutils db = new dbutils();
	    
	    String dk1 = "";
	    String dk2 = "";
	    String dk3 = "";
	    
	    if (obj.getkenhId().length() > 0) {
	    	dk3 += " and aaa.KBH_FK ='" + obj.getkenhId() + "'";
	    }
		else {
			dk3 += " and aaa.KBH_FK in " + Uti_center.quyen_kenh(obj.getuserId());
		}
	    
	    if(obj.getdvkdId().length() > 0) {
	    	dk3 += " and sp.DVKD_FK ='" + obj.getdvkdId() + "'";
	    }
	    
	    if(obj.getvungId().length() > 0) {
	    	dk3 += " and vung.PK_SEQ ='" + obj.getvungId() + "'";
	    }
	    
	    if(obj.getkhuvucId().length() > 0) {
	    	dk3 += " and kv.PK_SEQ ='" + obj.getkhuvucId() + "'";
	    }
	    
	    if(obj.getnhanhangId().length() > 0) {
	    	dk3 += " and sp.NHANHANG_FK ='" + obj.getnhanhangId() + "'";
	    }
	    
	    if(obj.getchungloaiId().length() > 0) {
	    	dk3 += " and sp.CHUNGLOAI_FK ='" + obj.getchungloaiId() + "'";
	    }
	    
	    if(obj.getnppId().length() > 0) {
	    	dk1 += " and NPP_FK ='" + obj.getnppId() + "'";
	    	dk2 += " and NPP_FK ='" + obj.getnppId() + "'";
	    } else {
	    	dk1 += " and NPP_FK in " + Uti_center.quyen_npp(obj.getuserId());
	    	dk2 += " and NPP_FK in " + Uti_center.quyen_npp(obj.getuserId());
	    }
	    
	    dk3 += " and aaa.SANPHAM_FK in " + Uti_center.quyen_sanpham(obj.getuserId());
	    

	    String sql =" select kbh.TEN as Chanel, dvkd.DIENGIAI as DVKD, vung.TEN as Vung, kv.TEN as KhuVuc, npp.TEN as Distributor, npp.PK_SEQ as Distcode, kh.TEN as KhachHang, cl.TEN as ChungLoai, nh.TEN as NhanHang, sp.PK_SEQ as SKUcode, sp.TEN as SKU, kho.TEN as Warehouse, kh.pk_seq as OutletCode, kh.TEN as OutletName, soluong as nhap, SOLUONG-ton as ban, ton as toncuoi from " +
				    " (" +
				    " select tl.*, isnull(tk.TON, 0) as ton from (" +
				    "	select tl.NPP_FK, tl.KBH_FK, tl.KHO_FK, tl.KHACHHANG_FK, tl.SANPHAM_FK, sum(dhsp.SOLUONG) as SOLUONG from (" +
				    "		select dh.NPP_FK, dh.KBH_FK, dh.KHO_FK, dh.KHACHHANG_FK, dhsp.SANPHAM_FK,	" +
				    "			max(dh.NGAYNHAP) as NGAYNHAP" +
				    "		from DONHANG dh inner join DONHANG_SANPHAM dhsp on dh.PK_SEQ = dhsp.DONHANG_FK" +
				    "		where (dh.TRANGTHAI = 1 or dh.TRANGTHAI = 3) " +
				    "			 and '"+obj.gettungay()+"' <= NGAYNHAP and NGAYNHAP <= '"+obj.getdenngay()+"' " + dk1 +
				    "		group by dh.NPP_FK, dh.KBH_FK, dh.KHO_FK, dh.KHACHHANG_FK, dhsp.SANPHAM_FK" +
				    "	) tl" +
				    "	inner join DONHANG dh on tl.NPP_FK = dh.NPP_FK and tl.KBH_FK = dh.KBH_FK and tl.KHO_FK = dh.KHO_FK and tl.KHACHHANG_FK = dh.KHACHHANG_FK and tl.NGAYNHAP = dh.NGAYNHAP" +
				    "	inner join DONHANG_SANPHAM dhsp on dh.PK_SEQ = dhsp.DONHANG_FK" +
				    "	group by tl.NPP_FK, tl.KBH_FK, tl.KHO_FK, tl.KHACHHANG_FK, tl.SANPHAM_FK, tl.NGAYNHAP" +
				    " ) tl" +
				    " LEFT JOIN" +
				    " (  " +
				    "	select tk.NPP_FK, tk.KBH_FK, tk.KHO_FK, tk.KH_FK, tk.SANPHAM_FK, khk.SOLUONG as TON from (" +
				    "		select NPP_FK, KBH_FK, KHO_FK, KH_FK, SANPHAM_FK, max(NGAY) as NGAY " +
				    "		from KHACHHANG_KHO" +
				    "		where '"+obj.gettungay()+"' <= NGAY and NGAY <= '"+obj.getdenngay()+"' " + dk2 +
				    "		group by NPP_FK, KBH_FK, KHO_FK, KH_FK, SANPHAM_FK" +
				    "	) tk" +
				    "		inner join KHACHHANG_KHO khk on tk.NPP_FK = khk.NPP_FK and tk.KBH_FK = khk.KBH_FK and tk.KHO_FK = khk.KHO_FK and tk.KH_FK = khk.KH_FK and tk.SANPHAM_FK = khk.SANPHAM_FK and tk.NGAY = khk.NGAY" +
				    " ) tk on tl.NPP_FK = tk.NPP_FK and tl.KBH_FK = tk.KBH_FK and tl.KHO_FK = tk.KHO_FK and tl.KHACHHANG_FK = tk.KH_FK and tl.SANPHAM_FK = tk.SANPHAM_FK" +
				    " ) aaa " +
				    "	inner join KENHBANHANG kbh on aaa.KBH_FK = kbh.PK_SEQ " +
				    "	inner join NHAPHANPHOI npp on aaa.NPP_FK = npp.PK_SEQ " +
				    "	inner join KHO kho on aaa.KHO_FK = kho.PK_SEQ " +
				    "	inner join KHACHHANG kh on aaa.KHACHHANG_FK = kh.PK_SEQ " +
				    "	inner join SANPHAM sp on aaa.SANPHAM_FK = sp.PK_SEQ " +
				    "	inner join DONVIKINHDOANH dvkd on sp.DVKD_FK = dvkd.PK_SEQ " +
				    "	inner join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK " +
				    "	inner join VUNG vung on vung.PK_SEQ = kv.VUNG_FK " +
				    "	inner join NHANHANG nh on sp.NHANHANG_FK = nh.PK_SEQ " +
				    "	inner join CHUNGLOAI cl on sp.CHUNGLOAI_FK = cl.PK_SEQ	" +
				    " where soluong >= ton " + dk3;
		
	    
	    //String sql = "select * from BCTonKhoNgay";
		 System.out.println("[XuatNhapTonKhachHangTTSvl.CreateStaticData]: sql = " + sql);
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
				while(rs.next())
				{ 
					String Channel = rs.getString("Chanel");
					String BusinessUnit = rs.getString("DVKD");
					String Region = rs.getString("Vung");
					String Area = rs.getString("KhuVuc");
					String Distributor = rs.getString("Distributor");
					String DistributorCode = rs.getString("Distcode");
					String KhachHang = rs.getString("KhachHang");
					String Brands = rs.getString("NhanHang");
					String Category = rs.getString("ChungLoai");
					String SkuCode = rs.getString("SKUcode");
					String Sku = rs.getString("SKU");
					String Warehouse = rs.getString("Warehouse");
					//String OutletCode= rs.getString("OutletCode");
					String Nhap = rs.getString("nhap");
					String Ban = rs.getString("ban");
					String TonCuoi = rs.getString("toncuoi");
					
					cell = cells.getCell("EA" + Integer.toString(i)); cell.setValue(Channel); //0
					cell = cells.getCell("EB" + Integer.toString(i)); cell.setValue(BusinessUnit); //1
					cell = cells.getCell("EC" + Integer.toString(i)); cell.setValue(Region); //2
					cell = cells.getCell("ED" + Integer.toString(i)); cell.setValue(Area);   ///3
					cell = cells.getCell("EE" + Integer.toString(i)); cell.setValue(DistributorCode); //4
					cell = cells.getCell("EF" + Integer.toString(i)); cell.setValue(Distributor); //5
					cell = cells.getCell("EG" + Integer.toString(i)); cell.setValue(KhachHang); //5
					cell = cells.getCell("EH" + Integer.toString(i)); cell.setValue(Brands); //6
					cell = cells.getCell("EI" + Integer.toString(i)); cell.setValue(Category); //7
					cell = cells.getCell("EJ" + Integer.toString(i)); cell.setValue(SkuCode); //8
					cell = cells.getCell("EK" + Integer.toString(i)); cell.setValue(Sku); //9
					cell = cells.getCell("EL" + Integer.toString(i)); cell.setValue(Warehouse); //10
					cell = cells.getCell("EM" + Integer.toString(i)); cell.setValue(Double.parseDouble(Nhap)); //11
					cell = cells.getCell("EN" + Integer.toString(i)); cell.setValue(Double.parseDouble(Ban)); //12
					cell = cells.getCell("EO" + Integer.toString(i)); cell.setValue(Double.parseDouble(TonCuoi));	//13
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
