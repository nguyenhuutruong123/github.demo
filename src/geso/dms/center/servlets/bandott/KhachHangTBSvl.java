package geso.dms.center.servlets.bandott;

import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;
import geso.dms.center.beans.bandott.IBandott;
import geso.dms.center.beans.bandott.imp.Bandott;
import geso.dms.center.db.sql.dbutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.*;


public class KhachHangTBSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
       
    public KhachHangTBSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

	    IBandott obj = new Bandott();
	    obj.setUserId(userId);
	    
	    String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		if(view == null)
			view = "";
		obj.setView(view);
		
		if(view.equals("khachhangTB"))
		{
			obj.initTB();
			session.setAttribute("obj", obj);
			
			String nextJSP = "/AHF/pages/Center/KhachHangTrungBayICP.jsp";
			response.sendRedirect(nextJSP);
		}
		else
		{
			obj.init();
			session.setAttribute("obj", obj);
			
			String nextJSP = "/AHF/pages/Center/BanDoTT.jsp";
			response.sendRedirect(nextJSP);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    OutputStream out = response.getOutputStream(); 
	    
	    HttpSession session = request.getSession();	
	    
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));     
	    IBandott obj = new Bandott();
	    obj.setUserId(userId);
	    
	    String vungId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vung")));
		if (vungId == null)
			vungId = "";
		obj.setVungId(vungId);
		
		String kvId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvuc")));
		if (kvId == null)
			kvId = "";
		obj.setKvId(kvId);
		
		String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("npp")));
		if (nppId == null)
			nppId = "";
		obj.setNppId(nppId);
		
		String cttbId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("cttbIds")));
		if (cttbId == null)
			cttbId = "";
		obj.setCttbId(cttbId);
		
		String ngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngay")));
		if (ngay == null)
			ngay = getDateTime();
		obj.setDate(ngay);
		
	    String ddkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkd")));
		if (ddkdId == null)
			ddkdId = "";
		obj.setDdkdId(ddkdId);
		
		String tbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tbh")));
		if (tbhId == null)
			tbhId = "";
		obj.setTbhId(tbhId);
		
		String address = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("address")));
		if (address == null)
			address = "";
		obj.setAddress(address);
		
		String trungtam = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trungtam")));
		if (trungtam == null)
			trungtam = "";
		obj.setTrungtam(trungtam);
		
		String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
		if (denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
	        
		String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		if(view == null)
			view = "";
		
		System.out.println("-----VIEW: " + view);
		if(view.equals("khachhangTB"))
		{
			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			if(action == null)
				action = "";
			System.out.println("1.Action: " + action);
			
			if(action.equals("excel"))
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=AuditTrungBay.xlsm");
			
				
				this.ExportTrungBay_To_Excel(request, out, obj, "admin");
				out.close();
			}
			else
			{
				obj.initTB();
				session.setAttribute("obj", obj);
				
				String nextJSP = "/AHF/pages/Center/KhachHangTrungBayICP.jsp";
				response.sendRedirect(nextJSP);
			}
		}
		else
		{
			obj.init();
			session.setAttribute("obj", obj);
			
			String nextJSP = "/AHF/pages/Center/BanDoTT.jsp";
			response.sendRedirect(nextJSP);
		}
			
	}
	
	private void ExportTrungBay_To_Excel(HttpServletRequest request, OutputStream out, IBandott obj, String userName)
	{
		//FileInputStream fstream = null;
		Workbook workbook = new Workbook();		
		
		try 
		{
			//fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ErpBCAuditTrungBay.xlsm");
			File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\ErpBCAuditTrungBay.xlsm");
			FileInputStream fstream = new FileInputStream(f) ;
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			
		    CreateStaticHeader(workbook, obj, userName);	     
		    //CreateStaticData(workbook, obj);
		    
		    workbook.save(out);
				
			fstream.close();
			
		} 
		catch (Exception e) 
		{
			System.out.println("EXception: " + e.getMessage());
		}
		
	}

	
	@SuppressWarnings("deprecation")
	private void CreateStaticHeader(Workbook workbook, IBandott obj, String userName) 
	{
		if(obj.getCttbId().trim().length() > 0)
		{
			dbutils db = new dbutils();
			ResultSet rsScheme = db.get("select scheme, ngaytrungbay, ngayketthucTB " +
										"from cttrungbay where pk_seq = '" + obj.getCttbId() + "' ");
			
			String schemeName = "";
			String tungay = "";
			String denngay = "";
			
			if(rsScheme != null)
			{
				try 
				{
					if(rsScheme.next())
					{
						schemeName = rsScheme.getString("scheme");
						tungay = rsScheme.getString("ngaytrungbay");
						denngay = rsScheme.getString("ngayketthucTB");
					}
					
					rsScheme.close();
				} 
				catch (Exception e) {}
			}
			
			Worksheets worksheets = workbook.getWorksheets();
		    Worksheet worksheet = worksheets.getSheet(0);
		   	   
		    worksheet.setName("Sheet1");
		    
		    Cells cells = worksheet.getCells();
			
		    Style style;
		    Font font = new Font();
		    font.setColor(Color.RED);//mau chu
		    font.setSize(16);// size chu
		   	font.setBold(true);
		   	
		    //cells.setRowHeightPixel(0, 30);
		    
		    cells.setColumnWidth(0, 20.0f);
		    cells.setColumnWidth(1, 20.0f);
		    cells.setColumnWidth(2, 30.0f);
		    cells.setColumnWidth(3, 15.0f);
		    cells.setColumnWidth(4, 30.0f);
		    cells.setColumnWidth(5, 30.0f);
		    cells.setColumnWidth(6, 20.0f);
		    cells.setColumnWidth(7, 20.0f);
		    cells.setColumnWidth(8, 20.0f);
		    
		    
		    Cell cell = cells.getCell("A1");
		    style = cell.getStyle();
		    style.setFont(font); 
		    style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu 	  
		    
		    String tieude = "BÁO CÁO TRƯNG BÀY ( SCHEME: " + schemeName + " )";
		    ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);
		           
		    cells.setRowHeight(4, 18.0f);
		    cell = cells.getCell("A3");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Từ ngày: " + tungay);
		    
		    cells.setRowHeight(4, 18.0f);
		    cell = cells.getCell("B3");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Đến ngày: " + denngay);
		    
		    
		    
		    cells.setRowHeight(4, 18.0f);
		    cell = cells.getCell("A4");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
		    
		    cells.setRowHeight(5, 18.0f);
		    cell = cells.getCell("A5");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + userName);
		    

		    //tieu de, hang dau tien cua bang du lieu, cell la toa do cua exel
		    cell = cells.getCell("A7"); 	cell.setValue("Mien");   //ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("B7"); 	cell.setValue("Vung");   //ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("C7"); 	cell.setValue("NhaPhanPhoi");   //ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("D7"); 	cell.setValue("MaKhachHang");  //ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("E7"); 	cell.setValue("TenKhachHang"); //ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("F7"); 	cell.setValue("DiaChi"); //ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("G7"); 	cell.setValue("QuanHuyen"); //ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("H7"); 	cell.setValue("TinhThanh"); //ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("I7"); 	cell.setValue("DienThoai"); //ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("J7"); 	cell.setValue("DangKy");	   //ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("K7"); 	cell.setValue("Dat");	   //ReportAPI.setCellHeader(cell);
		    
		    //San Pham
		    int pos = 12;
		    String query = "select sanpham_fk, (select ten from SanPham where pk_seq = sanpham_fk) as spTen " +
		    			   "from NHOMSPTRUNGBAY_SANPHAM where NHOMSPTRUNGBAY_FK in ( select nhomsptrungbay_fk from CTTB_NHOMSPTRUNGBAY where cttrungbay_fk = '" + obj.getCttbId() + "' )";
		    
		    String spIds = "";
		    ResultSet rsSp = db.get(query);
		    if(rsSp != null)
		    {
		    	try 
		    	{
					while(rsSp.next())
					{
						spIds += rsSp.getString("sanpham_fk") + ",";
						cell = cells.getCell(GetExcelColumnName(pos) + "7"); 	cell.setValue(rsSp.getString("spTen"));	   //ReportAPI.setCellHeader(cell);
						pos++;
					}
					rsSp.close();
				} 
		    	catch (Exception e) 
				{
					System.out.println("Exception: " + e.getMessage());
				}
		    }
		    
		    String chuoiSearch = "";
		    String dkSearch = "";
		    if(spIds.trim().length() > 0)
		    {
		    	spIds = spIds.substring(0, spIds.length() - 1);
		    	
		    	String[] arr = spIds.split(",");
		    	for(int i = 0 ; i < arr.length; i++)
		    	{
		    		chuoiSearch += " ,isnull([" + arr[i] + "], 0) as '" + arr[i] + "' ";
		    		dkSearch += "[" + arr[i] + "]" + ",";
		    	}
		    }
		    
		    if(dkSearch.length() > 0)
		    	dkSearch = dkSearch.substring(0, dkSearch.length() - 1);
		    
		    cells.setColumnWidth(pos - 1, 20.0f);
		    cells.setColumnWidth(pos, 20.0f);
		    cells.setColumnWidth(pos + 1, 20.0f);
		    cells.setColumnWidth(pos + 2, 20.0f);
		    cells.setColumnWidth(pos + 3, 20.0f);
		    cells.setColumnWidth(pos + 4, 20.0f);
		    
		    cell = cells.getCell(GetExcelColumnName(pos) + "7"); 	cell.setValue("Da Nhan Thuong");	   //ReportAPI.setCellHeader(cell);
		    cell = cells.getCell(GetExcelColumnName(pos + 1) + "7"); 	cell.setValue("Vi Tri");	   //ReportAPI.setCellHeader(cell);
		    cell = cells.getCell(GetExcelColumnName(pos + 2) + "7"); 	cell.setValue("Ghi Chu");	   //ReportAPI.setCellHeader(cell);
		    cell = cells.getCell(GetExcelColumnName(pos + 3) + "7"); 	cell.setValue("Ngay");	   //ReportAPI.setCellHeader(cell);
		    cell = cells.getCell(GetExcelColumnName(pos + 4) + "7"); 	cell.setValue("Anh Ben Trai");	   //ReportAPI.setCellHeader(cell);
		    cell = cells.getCell(GetExcelColumnName(pos + 5) + "7"); 	cell.setValue("Anh Chinh Dien");	   //ReportAPI.setCellHeader(cell);
		    cell = cells.getCell(GetExcelColumnName(pos + 6) + "7"); 	cell.setValue("Anh Ben Phai");	   //ReportAPI.setCellHeader(cell);
		    
			
		    
		    /*query = "select  g.pk_seq as vungId, g.ten as vungTen, f.pk_seq as kvId, f.ten as kvTen, e.pk_seq as nppId, e.ten as nppTen,  " +
					"d.pk_seq as khId, d.ten as khTen, isnull(d.diachi, 'NA') as diachi, isnull(d.dienthoai, 'NA') as dienthoai, c.scheme, isnull(i.ten, 'NA') as quanhuyen, isnull(h.ten, 'NA') as tinhthanh,     " +
					"a.dangky, '' as dat, isnull(danhanthuong, 0) as danhanthuong, isnull(a.thoidiem, getdate()) as thoidiem, " +
					"isnull(a.ghichu, '') as ghichu, isnull(a.ImageFilePath, '') as fileHinh, -1 as type     " +
				"from DKTrungBay_KhachHang a inner join DangKyTrungBay b on a.dktrungbay_fk = b.pk_seq    " +
					"inner join CtTrungBay c on b.cttrungbay_fk = c.pk_seq   " +
					"inner join KhachHang d on a.khachhang_fk = d.pk_seq    " +
					"inner join NhaPhanPhoi e on d.npp_fk = e.pk_seq    " +
					"inner join KhuVuc f on e.khuvuc_fk = f.pk_seq    " +
					"inner join Vung g on f.vung_fk = g.pk_seq  " +
					"left join  TinhThanh h on d.tinhthanh_fk = h.pk_seq " +
					"left join QuanHuyen i on d.quanhuyen_fk = i.pk_seq   " +
				"where c.pk_seq = '100020' and b.trangthai != 2";*/
		    
		    String thang = getMonth();
		    String nam = getYear();
		    
		    query = "select cttb.*, sptb.*  " +
		    		"from " +
		    		"( " +
		    			"select a.dktrungbay_fk, g.pk_seq as vungId, g.ten as vungTen, f.pk_seq as kvId, f.ten as kvTen, e.pk_seq as nppId, e.ten as nppTen,    " +
		    				"d.pk_seq as khId, d.ten as khTen, isnull(d.diachi, 'NA') as diachi, isnull(d.dienthoai, 'NA') as dienthoai, c.scheme, isnull(i.ten, 'NA') as quanhuyen, isnull(h.ten, 'NA') as tinhthanh,       " +
		    				"a.dangky, '' as dat, case when isnull(danhanthuong, 0) = 0 then N'Đã nhận ' else N'Chưa nhận' end as danhanthuong, " +
		    				"isnull(a.thoidiem, dbo.GetLocalDate(DEFAULT)) as thoidiem,  " +
		    				"isnull(a.ghichu, '') as ghichu, isnull(a.vitri, 'NA') as vitri, "+
		    				"isnull( ( select ImageFilePath from DKTRUNGBAY_KHACHHANG_CHITIET where loai = '0' and KHACHHANG_FK = d.PK_SEQ and DKTRUNGBAY_FK = a.DKTRUNGBAY_FK and thang = '"+thang+"' and nam = '"+nam+"' ) , '') as hinhBenTrai, " +
		    				"isnull( ( select ImageFilePath from DKTRUNGBAY_KHACHHANG_CHITIET where loai = '2' and KHACHHANG_FK = d.PK_SEQ and DKTRUNGBAY_FK = a.DKTRUNGBAY_FK and thang = '"+thang+"' and nam = '"+nam+"' ) , '') as hinhBenPhai, " +
		    				"isnull( ( select ImageFilePath from DKTRUNGBAY_KHACHHANG_CHITIET where loai = '1' and KHACHHANG_FK = d.PK_SEQ and DKTRUNGBAY_FK = a.DKTRUNGBAY_FK and thang = '"+thang+"' and nam = '"+nam+"' ) , '') as hinhGiua,   " +		    				
		    				" -1 as type       " +
		    			"from DKTrungBay_KhachHang a inner join DangKyTrungBay b on a.dktrungbay_fk = b.pk_seq      " +
		    			"inner join CtTrungBay c on b.cttrungbay_fk = c.pk_seq    " +
		    			"inner join KhachHang d on a.khachhang_fk = d.pk_seq     " +
		    			"inner join NhaPhanPhoi e on d.npp_fk = e.pk_seq     " +
		    			"inner join KhuVuc f on e.khuvuc_fk = f.pk_seq     " +
		    			"inner join Vung g on f.vung_fk = g.pk_seq   " +
		    			"left join  TinhThanh h on d.tinhthanh_fk = h.pk_seq  " +
		    			"left join QuanHuyen i on d.quanhuyen_fk = i.pk_seq    " +
		    			"where c.pk_seq = '" + obj.getCttbId() + "' and b.trangthai != 2  " +
		    		")  " +
		    		"cttb left join  " +
		    		"( " +
		    			"select  dktrungbay_fk, khachhang_fk " + chuoiSearch + " from   " +
		    			"( " +
		    				"select dktrungbay_fk, khachhang_fk, sanpham_fk, isnull(soluong, 0) as soluong   " +
		    				"from dktrungbay_sanpham " +
		    				"where dktrungbay_fk in ( select pk_seq from DangKyTrungBay where trangthai != 2 and cttrungbay_fk = '" + obj.getCttbId() + "' )  " +
		    			") p " +
		    			"pivot  " +
		    			"( " +
		    				"sum(soluong)for  sanpham_fk in  " +
		    				"(  " +
		    					" " + dkSearch + "  " +
		    				") " +
		    			")  " +
		    			"as a  " +
		    			")  sptb on cttb.dktrungbay_fk = sptb.dktrungbay_fk and cttb.khId = sptb.khachhang_fk";
		    
		    
		    System.out.println("Lay data: " + query);
		    ResultSet rsData = db.get(query);
		    
		    int row = 8;
		    if(rsData != null)
		    {
		    	String[] spArr = null;
		    	
		    	if(spIds.length() > 0)
		    	{
		    		spArr = spIds.split(",");
		    	}
		    		
		    	try 
		    	{
					while(rsData.next())
					{
						//cells.setRowHeightPixel(pos, 60);
						
						cells.setRowHeight(row - 1, 30.0f);
						
						cell = cells.getCell("A" + Integer.toString(row)); 	cell.setValue(rsData.getString("vungTen"));   
					    cell = cells.getCell("B" + Integer.toString(row)); 	cell.setValue(rsData.getString("kvTen"));  
					    cell = cells.getCell("C" + Integer.toString(row)); 	cell.setValue(rsData.getString("nppTen"));  
					    cell = cells.getCell("D" + Integer.toString(row)); 	cell.setValue(rsData.getString("khId"));  
					    cell = cells.getCell("E" + Integer.toString(row)); 	cell.setValue(rsData.getString("khTen"));  
					    cell = cells.getCell("F" + Integer.toString(row)); 	cell.setValue(rsData.getString("diachi"));  
					    cell = cells.getCell("G" + Integer.toString(row)); 	cell.setValue(rsData.getString("quanhuyen"));  
					    cell = cells.getCell("H" + Integer.toString(row)); 	cell.setValue(rsData.getString("tinhthanh"));   
					    cell = cells.getCell("I" + Integer.toString(row)); 	cell.setValue(rsData.getString("dienthoai"));  
					    cell = cells.getCell("J" + Integer.toString(row)); 	cell.setValue(rsData.getString("dangky"));  	   
					    cell = cells.getCell("K" + Integer.toString(row)); 	cell.setValue(rsData.getString("dat"));  	   
						
					    pos = 12;
					    if(spArr != null)
					    {
					    	for(int i = 0; i < spArr.length; i++)
					    	{
					    		cell = cells.getCell(GetExcelColumnName(pos) + Integer.toString(row)); 	cell.setValue(rsData.getString(spArr[i]));	  
					    		pos++;
					    	}
					    }
					    
					    cell = cells.getCell(GetExcelColumnName(pos) + Integer.toString(row)); 	cell.setValue(rsData.getString("danhanthuong"));	  
					    cell = cells.getCell(GetExcelColumnName(pos + 1) + Integer.toString(row)); 	cell.setValue(rsData.getString("vitri"));	  
					    cell = cells.getCell(GetExcelColumnName(pos + 2) + Integer.toString(row)); 	cell.setValue(rsData.getString("ghichu"));	
					    cell = cells.getCell(GetExcelColumnName(pos + 3) + Integer.toString(row)); 	cell.setValue(rsData.getString("thoidiem"));	  
					    
					    String img = rsData.getString("hinhBenTrai");
					    String img2 = rsData.getString("hinhGiua");
					    String img3 = rsData.getString("hinhBenPhai");
					    
					    if(img.trim().length() > 0)
					    {
						    //worksheet.getPictures().add(row, pos + 3, "D:\\project\\DuongBienHoaICP\\DuongBienHoa\\WebContent\\pages\\images\\404.jpg");
						    try
						    {
						    	worksheet.getPictures().add(row-1, pos + 3, "C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\DuongBienHoa\\pages\\upload\\" + img);
							    
							    Picture picture = worksheet.getPictures().get( worksheet.getPictures().size() - 1 );
							    picture.setWidth(30);
							    picture.setHeight(30);
						    }
						    catch (Exception e) { System.out.println("Exception: " + e.getMessage()); }
					    }
					    
					    if(img2.trim().length() > 0)
					    {
						    //worksheet.getPictures().add(row, pos + 3, "D:\\project\\DuongBienHoaICP\\DuongBienHoa\\WebContent\\pages\\images\\404.jpg");
						    try
						    {
						    	worksheet.getPictures().add(row-1, pos + 4, "C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\DuongBienHoa\\pages\\upload\\" + img2);
							    
							    Picture picture = worksheet.getPictures().get( worksheet.getPictures().size() - 1 );
							    picture.setWidth(30);
							    picture.setHeight(30);
						    }
						    catch (Exception e) { System.out.println("Exception: " + e.getMessage()); }
					    }
					    
					    if(img3.trim().length() > 0)
					    {
						    //worksheet.getPictures().add(row, pos + 3, "D:\\project\\DuongBienHoaICP\\DuongBienHoa\\WebContent\\pages\\images\\404.jpg");
						    try
						    {
						    	worksheet.getPictures().add(row-1, pos + 5, "C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\DuongBienHoa\\pages\\upload\\" + img3);
							    
							    Picture picture = worksheet.getPictures().get( worksheet.getPictures().size() - 1 );
							    picture.setWidth(30);
							    picture.setHeight(30);
						    }
						    catch (Exception e) { System.out.println("Exception: " + e.getMessage()); }
					    }
					    
					  //Get a reference of comments collection with the first sheet
					    
					    /*Comments comments = worksheet.getComments();
					    //Add a comment to cell A1
					    int commentIndex = comments.add(15, 15);
					    Comment comment = comments.get(commentIndex);
					    comment.setNote("First note.");
					    comment.getFont().setName("Times New Roman");

					    //Load/Read an image into stream
					    String logo_url = "e:\\test\\school.jpg";
					    //Creating the instance of the FileInputStream object to open the logo/picture in the stream
					    FileInputStream inFile = new FileInputStream(logo_url);
					    //Setting the logo/picture
					    byte[] picData = new byte[inFile.available()];
					    inFile.read(picData);

					    //Set image data to the shape associated with the comment
					    comment.get
					    comment.getCommentShape().getFillFormat().setImageData(picData);*/
					    
					    
					    row++;
					}
					rsData.close();
				} 
		    	catch (Exception e) 
		    	{
					System.out.println("Exception: " + e.getMessage());
				}
		    }
		    
		}
	}

	private String GetExcelColumnName(int columnNumber)
	{
	    int dividend = columnNumber;
	    String columnName = "";
	    int modulo;

	    while (dividend > 0)
	    {
	        modulo = (dividend - 1) % 26;
	        columnName = (char)(65 + modulo) + columnName;
	        dividend = (int)((dividend - modulo) / 26);
	    } 

	    return columnName;
	}
	
	public static void main(String[] arg)
	{
		KhachHangTBSvl bd = new KhachHangTBSvl();
		
		System.out.println(" So 1 = : " + bd.GetExcelColumnName(1));
		System.out.println(" So 2 = : " + bd.GetExcelColumnName(2));
		System.out.println(" So 27 = : " + bd.GetExcelColumnName(27));
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private String getMonth() {
		DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        return dateFormat.format(date);
	}
	
	private String getYear() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return dateFormat.format(date);
	}

}
