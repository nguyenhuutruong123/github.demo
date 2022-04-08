package geso.dms.center.servlets.tieuchithuong;

import geso.dms.center.beans.tieuchithuong.ITieuchithuongSKUList;
import geso.dms.center.beans.tieuchithuong.imp.TieuchithuongSKUList;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
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

public class TieuchithuongSKUReportSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    
    public TieuchithuongSKUReportSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    HttpSession session = request.getSession();	
	    
	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    ITieuchithuongSKUList obj = new TieuchithuongSKUList();
	    obj.setUserId(userId);
	    
	    obj.initReport("");
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/AHF/pages/Center/TieuChiThuongSKUReport.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    HttpSession session = request.getSession();	
	    
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));     
	    ITieuchithuongSKUList obj = new TieuchithuongSKUList();
	    
	    String userName = (String) session.getAttribute("userTen");  
	    if(userName == null)
	    	userName = "";
	    
	    String thang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang"));
	    if(thang == null)
	    	thang = "";
	    obj.setThang(thang);
	    
	    String nam = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nam"));
	    if(nam == null)
	    	nam = "";
	    obj.setNam(nam);
	    
	    String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays"));
	    if(tungay == null)
	    	tungay = "";
	    obj.setTungay(tungay);
	    
	    String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays"));
	    if(denngay == null)
	    	denngay = "";
	    obj.setDenngay(denngay);
	    
	    String xemtheo = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("xemtheo"));
	    if(xemtheo == null)
	    	xemtheo = "1";
	    System.out.println("___Type la: " + xemtheo);
	    obj.setType(xemtheo);
	    
	    String vungId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"));
	    if(vungId == null)
	    	vungId = "";
	    obj.setVungId(vungId);
	    
	    String khuvucId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"));
	    if(khuvucId == null)
	    	khuvucId = "";
	    obj.setKvId(khuvucId);
	    
	    String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
	    if(nppId == null)
	    	nppId = "";
	    obj.setNppIds(nppId);
	    
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    
	    if(action.equals("taobc"))
	    {
	    	if( obj.getType().equals("0") ) //Lay theo ngay
	    	{
	    		System.out.println("____Tu ngay: " + obj.getTungay() + " -- Den ngay: " + obj.getDenngay() );
	    		if(obj.getTungay().trim().length() <= 0 || obj.getDenngay().trim().length() <= 0 )
	    		{
	    			obj.setUserId(userId);
	        		obj.initReport("");
	        		obj.setMsg("Thời gian bạn chọn không hợp lệ");

	    	    	session.setAttribute("obj", obj);  	
	        		session.setAttribute("userId", userId);
	    		
	        		response.sendRedirect("/AHF/pages/Center/TieuChiThuongSKUReport.jsp");
	    		}
	    		else
	    		{
	    			String thangBd = obj.getTungay().substring(5, 7);
	    			String thangKt = obj.getDenngay().substring(5, 7);
	    			String namBd = obj.getTungay().substring(0, 4);
	    			String namKt = obj.getDenngay().substring(0, 4);
	    			
	    			System.out.println("__Thang bat dau: " + thangBd + " ___ Thang ket thuc: " + thangKt + "___ Nam bat dau: " + namBd + " ____Nam ket thuc: " + namKt);
	    			if(!thangBd.equals(thangKt) || !namBd.equals(namKt) )
	    			{
	    				obj.setUserId(userId);
		        		obj.initReport("");
		        		obj.setMsg("Thời gian bạn chọn không hợp lệ, bạn phải chọn thời gian trong cùng 1 tháng của năm");

		    	    	session.setAttribute("obj", obj);  	
		        		session.setAttribute("userId", userId);
		    		
		        		response.sendRedirect("/AHF/pages/Center/TieuChiThuongSKUReport.jsp");
	    			}
	    			else
	    			{	
		    			response.setContentType("application/xlsm");
		    			response.setHeader("Content-Disposition", "attachment; filename=TieuChiThuongSKUTapTrung.xlsm");
		    			
		    			OutputStream outPv = response.getOutputStream();
		    			obj.setUserId(userName);
		    			createPivotTable(obj, outPv);
	    			}
	    		}
	    	}
	    	else
	    	{
	    		if(obj.getNam().trim().length() <= 0 || obj.getThang().trim().length() <= 0 )
	    		{
	    			obj.setUserId(userId);
	        		obj.initReport("");
	        		obj.setMsg("Thời gian bạn chọn không hợp lệ");

	    	    	session.setAttribute("obj", obj);  	
	        		session.setAttribute("userId", userId);
	    		
	        		response.sendRedirect("/AHF/pages/Center/TieuChiThuongSKUReport.jsp");
	    		}
	    		else
	    		{
	    			response.setContentType("application/xlsm");
	    			response.setHeader("Content-Disposition", "attachment; filename=TieuChiThuongSKUTapTrung.xlsm");
	    			
	    			OutputStream outPv = response.getOutputStream();
	    			obj.setUserId(userName);
	    			createPivotTable(obj, outPv);
	    		}
	    	}
	    	
	    }
	    else
	    {
    		obj.setUserId(userId);
    		obj.initReport("");

	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/AHF/pages/Center/TieuChiThuongSKUReport.jsp");
	    }
	   
	}

	private void createPivotTable(ITieuchithuongSKUList obj, OutputStream outPv)
	{
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();	
		
		try 
		{
			//fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\TieuChiThuongSKUTapTrung.xlsm");
			File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\TieuChiThuongSKUTapTrung.xlsm");
			 fstream = new FileInputStream(f) ;
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			
		     CreateStaticHeader(workbook, obj.getThang(), obj.getNam(), obj.getUserId(), obj);	     
		     CreateStaticData(workbook, obj);
		    
		     workbook.save(outPv);
				
			fstream.close();
		} 
		catch (Exception e) {}
	}
	
	private void CreateStaticHeader(Workbook workbook, String dateFrom, String dateTo, String UserName, ITieuchithuongSKUList obj) 
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
	    
	    String tieude = "THƯỞNG SKU TẬP TRUNG THEO TRỌNG SỐ";
	    ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);
	         
	    if(obj.getType().trim().equals("1"))
	    {
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("A2");
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Tháng: " + dateFrom );
		    
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("B2"); 
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Năm: " + dateTo );
	    }
	    else
	    {
	    	cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("A2");
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ ngày: " + obj.getTungay() );
		    
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("B2"); 
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến ngày: " + obj.getDenngay() );
	    }
		
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A3");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A4");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + UserName);
	    
	    cell = cells.getCell("EA1"); 	cell.setValue("Vung");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("EB1"); 	cell.setValue("KhuVuc");  ReportAPI.setCellHeader(cell);	     
	    cell = cells.getCell("EC1"); 	cell.setValue("MaNhaPhanPhoi");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("ED1"); 	cell.setValue("TenNhaPhanPhoi");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("EE1"); 	cell.setValue("ASM");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("EF1"); 	cell.setValue("GiamSatBanHang");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("EG1"); 	cell.setValue("DaiDienKinhDoanh");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("EH1"); 	cell.setValue("Scheme");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("EI1"); 	cell.setValue("PhaiDat");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("EJ1"); 	cell.setValue("ThucDat");  ReportAPI.setCellHeader(cell);
	    //cell = cells.getCell("AK1"); 	cell.setValue("SoXuat");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("EK1"); 	cell.setValue("PhanTram");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("EL1"); 	cell.setValue("Thuong SR");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("EM1"); 	cell.setValue("Thuong SS");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("EN1"); 	cell.setValue("Thuong ASM");  ReportAPI.setCellHeader(cell);
	}
	
	private boolean CreateStaticData(Workbook workbook, ITieuchithuongSKUList obj) 
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    
	    /*double thuong = 0;
	    double thuongtoida = -1;
	    double thuongGS = 0;
	    double thuongtoidaGS = -1;
	    int phaidat = 0;
	    int batbuoc = 0;
	    
	    String pk_seq = "";
	    String sql = "select chitieu.*, batbuoc.trongso  from ( " +
	    				"select pk_seq, phaidat, thuong, thuongtoida, thuongGS, thuongtoidaGS " +
	    				"from tieuchithuongSKU " +
	    				"where thang = '" + obj.getThang() + "' and nam = '" + obj.getNam() + "' " +
	    			") chitieu " +
	    		"cross join " +
	    		"( " +
	    		"select COUNT(*) as trongso " +
	    		"from TIEUCHITHUONGSKU_NHOMSP  " +
	    		"where trongso > 0 and tieuchithuongsku_fk in (select pk_seq from TIEUCHITHUONGSKU where thang = '" + obj.getThang() + "' and nam = '" + obj.getNam() + "')" +
	    		") batbuoc";
	    
	    System.out.println("12.Init Chi tieu: " + sql);
	    
	    ResultSet rsCt = db.get(sql);
	    if(rsCt != null)
	    {
	    	try 
	    	{
				while(rsCt.next())
				{
					thuong = rsCt.getDouble("thuong");
					thuongtoida = rsCt.getDouble("thuongtoida");
					batbuoc = rsCt.getInt("trongso");
					thuongGS = rsCt.getDouble("thuongGS");
					thuongtoidaGS = rsCt.getDouble("thuongtoidaGS");
					phaidat = rsCt.getInt("phaidat");
					pk_seq = rsCt.getString("pk_seq");
				}
				rsCt.close();
			} 
	    	catch (SQLException e) {}
	    	
	    }*/
	    
	    String condition = "";
	    if(obj.getVungId().length() > 0)
	    	condition += " and VUNG.pk_seq = '" + obj.getVungId() + "' ";
	    if(obj.getKvId().length() > 0)
	    	condition += " and KHUVUC.pk_seq = '" + obj.getKvId() + "' ";
	    if(obj.getNppIds().length() > 0)
	    	condition += " and NHAPHANPHOI.pk_seq = '" + obj.getNppIds() + "' ";
		
	    String dauthang = getDauthang(Integer.parseInt(obj.getThang()),  Integer.parseInt(obj.getNam()));
	    String cuoithang = getCuoithang(Integer.parseInt(obj.getThang()),  Integer.parseInt(obj.getNam()));
	    
		/*String query = "select DETAIL.npp_fk, DETAIL.gsbh_fk, DETAIL.ddkd_fk, VUNG.ten as vungTen, KHUVUC.ten as kvTen, " +
					"NHAPHANPHOI.ma as siteCode, NHAPHANPHOI.ten as nppTen, GIAMSATBANHANG.ten as gsbhten, DAIDIENKINHDOANH.ten as ddkdTen, CHITIEU.SOXUAT " +
			"from " +
			"( " +
			"select a.npp_fk, a.gsbh_fk, a.ddkd_fk, nhomsanpham_sanpham.nsp_fk, " +
			"cast((sum(b.soluong) / (" + (double)phaidat + ")) as numeric(18, 5)) as soxuat " +
			"from donhang a inner join donhang_sanpham b on a.pk_seq = b.donhang_fk  " +
			"inner join nhomsanpham_sanpham on b.sanpham_fk = nhomsanpham_sanpham.sp_fk " +
			"inner join TIEUCHITHUONGSKU_NHOMSP on nhomsanpham_sanpham.nsp_fk = TIEUCHITHUONGSKU_NHOMSP.nhomsp_fk " +
			"where  a.ngaynhap >= '" + dauthang + "' and a.ngaynhap <= '" + cuoithang + "' and a.trangthai = '1'  " +
			"and TIEUCHITHUONGSKU_NHOMSP.tieuchithuongsku_fk  in (	select pk_seq from TIEUCHITHUONGSKU " +
																	"where thang = '" + obj.getThang() + "' and nam = '" + obj.getNam() + "' ) " +
			"group by a.npp_fk, a.gsbh_fk, a.ddkd_fk, nhomsanpham_sanpham.nsp_fk, TIEUCHITHUONGSKU_NHOMSP.trongso " +
			"having cast( (sum(b.soluong) / " + (double)phaidat + ") as numeric(18, 5) ) > 0 ) " +
			"DETAIL inner join " +
			"( " +
			"select a.npp_fk, a.gsbh_fk, a.ddkd_fk, " +
			"cast((sum(b.soluong) / (" + phaidat + ")) as numeric(18, 0)) as soxuat " +
			"from donhang a inner join donhang_sanpham b on a.pk_seq = b.donhang_fk " +
			"where  a.ngaynhap >= '" + dauthang + "' and a.ngaynhap <= '" + cuoithang + "' and a.trangthai = '1' " +
			"and sanpham_fk in ( " +
					"select distinct c.sp_fk " +
					"from TIEUCHITHUONGSKU a inner join TIEUCHITHUONGSKU_NHOMSP b on a.pk_seq = b.TIEUCHITHUONGSKU_FK " +
					"inner join NHOMSANPHAM_SANPHAM c on b.nhomsp_fk = c.nsp_fk where a.thang = '" + obj.getThang() + "' and a.nam = '" + obj.getNam() + "' ) " +
					"group by a.npp_fk, a.gsbh_fk, a.ddkd_fk " +
					"having cast((sum(b.soluong) / (" + phaidat + ")) as numeric(18, 0)) > 0 " +
			") CHITIEU on DETAIL.npp_fk = CHITIEU.npp_fk and DETAIL.ddkd_fk = CHITIEU.ddkd_fk and DETAIL.gsbh_fk = CHITIEU.gsbh_fk " +
	"left JOIN " +
			"( " +
			"select nhomsp_fk, trongso from TIEUCHITHUONGSKU_NHOMSP " +
			"where trongso > 0 and tieuchithuongsku_fk in (select pk_seq from TIEUCHITHUONGSKU where thang = '" + obj.getThang() + "' and nam = '" + obj.getNam() + "') " +
			")  " +
		"TRONGSO on DETAIL.nsp_fk = TRONGSO.nhomsp_fk " +
		"inner join NHAPHANPHOI on DETAIL.npp_fk = NHAPHANPHOI.pk_seq " +
		"inner join KHUVUC on NHAPHANPHOI.khuvuc_fk = KHUVUC.pk_seq " +
		"inner join VUNG on KHUVUC.vung_fk = VUNG.pk_seq " +
		"inner join GIAMSATBANHANG on DETAIL.gsbh_fk = GIAMSATBANHANG.pk_seq " +
		"inner join DAIDIENKINHDOANH on DETAIL.ddkd_fk = DAIDIENKINHDOANH.pk_seq " +
		"where ( DETAIL.soxuat * 100 >= TRONGSO.TrongSo ) ";
		
		if(condition.length() > 0)
			query += condition;
		
		query += "group by DETAIL.npp_fk, DETAIL.gsbh_fk, DETAIL.ddkd_fk, VUNG.ten, KHUVUC.ten, " +
		"NHAPHANPHOI.ma, NHAPHANPHOI.ten, GIAMSATBANHANG.ten, DAIDIENKINHDOANH.ten, CHITIEU.SOXUAT " +
		"having count(DETAIL.ddkd_fk) >= '" + batbuoc + "' " +
		"order by DETAIL.npp_fk asc";*/
		
		
		String query = "";
		
		if(obj.getType().equals("1"))
		{
			query =	"select DETAIL.npp_fk, DETAIL.gsbh_fk, DETAIL.ddkd_fk, DETAIL.PHAIDAT, CHITIEU.THUCDAT, CHITIEU.tctId, " +
					"CHITIEU.scheme, CHITIEU.thuong, isnull(CHITIEU.thuongtoida, -1) as thuongtoida, CHITIEU.thuongGS, isnull(CHITIEU.thuongtoidaGS, -1) as thuongtoidaGS, " +
					"CHITIEU.thuongASM, isnull(CHITIEU.thuongtoidaASM, -1) as thuongtoidaASM, " +
					"VUNG.ten as vungTen, KHUVUC.ten as kvTen, NHAPHANPHOI.ma as siteCode, NHAPHANPHOI.ten as nppTen, " +
					"ASM.pk_seq as asmId, isnull(ASM.ten, 'na') as asmTen, GIAMSATBANHANG.ten as gsbhten, DAIDIENKINHDOANH.ten as ddkdTen " +
			"from " +
			"( " +
				"select a.npp_fk, a.ddkd_fk, a.gsbh_fk, TIEUCHITHUONGSKU.pk_seq as tctId, TIEUCHITHUONGSKU_DDKD.phaidat, nhomsanpham_sanpham.nsp_fk, " +
						" sum(b.soluong) / TIEUCHITHUONGSKU_DDKD.phaidat as soxuat " +
				"from donhang a inner join donhang_sanpham b on a.pk_seq = b.donhang_fk  " +
						"inner join nhomsanpham_sanpham on b.sanpham_fk = nhomsanpham_sanpham.sp_fk " +
						"inner join TIEUCHITHUONGSKU_NHOMSP on nhomsanpham_sanpham.nsp_fk = TIEUCHITHUONGSKU_NHOMSP.nhomsp_fk " +
						"inner join TIEUCHITHUONGSKU on TIEUCHITHUONGSKU.pk_seq = TIEUCHITHUONGSKU_NHOMSP.tieuchithuongsku_fk " +
						"inner join TIEUCHITHUONGSKU_DDKD on TIEUCHITHUONGSKU_DDKD.tieuchithuongsku_fk = TIEUCHITHUONGSKU.pk_seq " +
									"and TIEUCHITHUONGSKU_DDKD.ddkd_fk = a.ddkd_fk " +
				"where  a.ngaynhap >= '" + dauthang + "' and a.ngaynhap <= '" + cuoithang + "' and a.trangthai = '1'  " +
						"and TIEUCHITHUONGSKU.thang = '" + obj.getThang() + "' and TIEUCHITHUONGSKU.nam = '" + obj.getNam() + "' and TIEUCHITHUONGSKU.trangthai = '1' " +
				"group by a.npp_fk, a.ddkd_fk, a.gsbh_fk, TIEUCHITHUONGSKU.pk_seq, TIEUCHITHUONGSKU_DDKD.phaidat, nhomsanpham_sanpham.nsp_fk " +
				"having ( sum(b.soluong) / TIEUCHITHUONGSKU_DDKD.phaidat )  >= 1 " +
			") " +
			"DETAIL inner join " +
			"( " +
			
				"select thuong.npp_fk, thuong.ddkd_fk, thuong.gsbh_fk, thuong.tctId, thuong.scheme, thuong.phaidat, thuong.nsp_fk, " +
				"			sum(thuong.thucdat) as thucdat, " +
				"			sum(thuong.thuong) as thuong, sum(thuong.thuongtoida) as thuongtoida, " +
				"			sum(thuong.thuongGS) as thuongGS, sum(thuong.thuongtoidaGS) as thuongtoidaGS, " +
				"			sum(thuong.thuongASM) as thuongASM, sum(thuong.thuongtoidaASM) as thuongtoidaASM " +
				"from " +
				"( " +
					"select a.npp_fk, a.ddkd_fk, gsbh_fk, TIEUCHITHUONGSKU.pk_seq as tctId, TIEUCHITHUONGSKU.scheme, TIEUCHITHUONGSKU_DDKD.phaidat, " +
					"	nhomsanpham_sanpham.nsp_fk, nhomsanpham_sanpham.sp_fk, " +
					"	sum(nhomsanpham_sanpham.thuong_sr) as thuong, sum(distinct nhomsanpham_sanpham.thuong_td_sr) as thuongtoida, " +
					"	sum(nhomsanpham_sanpham.thuong_ss) as thuongGS, sum(distinct nhomsanpham_sanpham.thuong_td_ss) as thuongtoidaGS,  " +
					"	sum(nhomsanpham_sanpham.thuong_asm) as thuongASM, sum(distinct nhomsanpham_sanpham.thuong_td_asm) as thuongtoidaASM, " +
					"	sum(b.soluong) as thucdat " +
					"from donhang a inner join donhang_sanpham b on a.pk_seq = b.donhang_fk " +
					"	inner join nhomsanpham_sanpham on b.sanpham_fk = nhomsanpham_sanpham.sp_fk " +
					"	inner join TIEUCHITHUONGSKU_NHOMSP on nhomsanpham_sanpham.nsp_fk = TIEUCHITHUONGSKU_NHOMSP.nhomsp_fk  " +
					"	inner join TIEUCHITHUONGSKU on TIEUCHITHUONGSKU.pk_seq = TIEUCHITHUONGSKU_NHOMSP.tieuchithuongsku_fk  " +
					"	inner join TIEUCHITHUONGSKU_DDKD on TIEUCHITHUONGSKU_DDKD.tieuchithuongsku_fk = TIEUCHITHUONGSKU.pk_seq  " +
					"		and TIEUCHITHUONGSKU_DDKD.ddkd_fk = a.ddkd_fk  " +
					"where  a.ngaynhap >= '" + dauthang + "' and a.ngaynhap <= '" + cuoithang + "' and a.trangthai = '1' " +
					"	and TIEUCHITHUONGSKU.thang = '" + obj.getThang() + "' and TIEUCHITHUONGSKU.nam = '" + obj.getNam() + "' and TIEUCHITHUONGSKU.trangthai = '1' " +
					"group by a.npp_fk, a.ddkd_fk, gsbh_fk, TIEUCHITHUONGSKU.pk_seq, TIEUCHITHUONGSKU.scheme,  " +
					"TIEUCHITHUONGSKU_DDKD.phaidat, nhomsanpham_sanpham.nsp_fk, nhomsanpham_sanpham.sp_fk " +
					"having ( sum(b.soluong) / TIEUCHITHUONGSKU_DDKD.phaidat ) >= 1 " +
				") thuong " +
				"group by thuong.npp_fk, thuong.ddkd_fk, thuong.gsbh_fk, thuong.tctId, thuong.scheme, thuong.phaidat, thuong.nsp_fk " +
				
			") " +
			"CHITIEU on DETAIL.npp_fk = CHITIEU.npp_fk and DETAIL.ddkd_fk = CHITIEU.ddkd_fk and DETAIL.gsbh_fk = CHITIEU.gsbh_fk and DETAIL.tctId = CHITIEU.tctId " +
			"left JOIN " +
			"( " +
				"select tieuchithuongsku_fk as tctId, nhomsp_fk, trongso from TIEUCHITHUONGSKU_NHOMSP  " +
				"where trongso > 0 and tieuchithuongsku_fk in (select pk_seq from TIEUCHITHUONGSKU where thang = '" + obj.getThang() + "' and nam = '" + obj.getNam() + "' and trangthai = '1' ) " +
			")  " +
			"TRONGSO on DETAIL.nsp_fk = TRONGSO.nhomsp_fk and  DETAIL.tctId = TRONGSO.tctId " +
			"inner join NHAPHANPHOI on DETAIL.npp_fk = NHAPHANPHOI.pk_seq " +
			"inner join KHUVUC on NHAPHANPHOI.khuvuc_fk = KHUVUC.pk_seq " +
			"inner join VUNG on KHUVUC.vung_fk = VUNG.pk_seq " +
			"inner join GIAMSATBANHANG on DETAIL.gsbh_fk = GIAMSATBANHANG.pk_seq " +
			"inner join DAIDIENKINHDOANH on DETAIL.ddkd_fk = DAIDIENKINHDOANH.pk_seq " +
			"left join ASM_KHUVUC on ASM_KHUVUC.khuvuc_fk = KHUVUC.pk_seq " +
			"left join ASM on ASM_KHUVUC.asm_fk = ASM.pk_seq " +
			"where ( DETAIL.soxuat * 100 >= TRONGSO.TrongSo ) ";
			
			if(condition.length() > 0)
			query += condition;
			
			query += "group by DETAIL.npp_fk, DETAIL.gsbh_fk, DETAIL.ddkd_fk, DETAIL.PHAIDAT, CHITIEU.THUCDAT, CHITIEU.tctId, " +
						"CHITIEU.scheme, CHITIEU.thuong, CHITIEU.thuongtoida, CHITIEU.thuongGS, CHITIEU.thuongtoidaGS, CHITIEU.thuongASM, CHITIEU.thuongtoidaASM, " +
						"VUNG.ten, KHUVUC.ten, ASM.pk_seq, ASM.ten, NHAPHANPHOI.ma, NHAPHANPHOI.ten, " +
						"GIAMSATBANHANG.ten, DAIDIENKINHDOANH.ten  " +
						
			"having count(DETAIL.ddkd_fk) >= ( 	select COUNT(*)  " +
												"from TIEUCHITHUONGSKU_NHOMSP " +
												" where trongso > 0 and tieuchithuongsku_fk = CHITIEU.tctId ) " +
			"order by DETAIL.npp_fk asc";
		}
		else
		{
			String thang = obj.getTungay().substring(5, 7);
			if(thang.startsWith("0"))
				thang = thang.replaceAll("0", "");
			String nam = obj.getTungay().substring(0, 4);
			
			query =	"select DETAIL.npp_fk, DETAIL.gsbh_fk, DETAIL.ddkd_fk, DETAIL.PHAIDAT, CHITIEU.THUCDAT, CHITIEU.tctId, " +
					"CHITIEU.scheme, CHITIEU.thuong, isnull(CHITIEU.thuongtoida, -1) as thuongtoida, CHITIEU.thuongGS, isnull(CHITIEU.thuongtoidaGS, -1) as thuongtoidaGS, " +
					"CHITIEU.thuongASM, isnull(CHITIEU.thuongtoidaASM, -1) as thuongtoidaASM, " +
					"VUNG.ten as vungTen, KHUVUC.ten as kvTen, NHAPHANPHOI.ma as siteCode, NHAPHANPHOI.ten as nppTen, " +
					"ASM.pk_seq as asmId, isnull(ASM.ten, 'na') as asmTen, GIAMSATBANHANG.ten as gsbhten, DAIDIENKINHDOANH.ten as ddkdTen " +
			"from " +
			"( " +
				"select a.npp_fk, a.ddkd_fk, a.gsbh_fk, TIEUCHITHUONGSKU.pk_seq as tctId, TIEUCHITHUONGSKU_DDKD.phaidat, nhomsanpham_sanpham.nsp_fk, " +
						" sum(b.soluong) / TIEUCHITHUONGSKU_DDKD.phaidat as soxuat " +
				"from donhang a inner join donhang_sanpham b on a.pk_seq = b.donhang_fk  " +
						"inner join nhomsanpham_sanpham on b.sanpham_fk = nhomsanpham_sanpham.sp_fk " +
						"inner join TIEUCHITHUONGSKU_NHOMSP on nhomsanpham_sanpham.nsp_fk = TIEUCHITHUONGSKU_NHOMSP.nhomsp_fk " +
						"inner join TIEUCHITHUONGSKU on TIEUCHITHUONGSKU.pk_seq = TIEUCHITHUONGSKU_NHOMSP.tieuchithuongsku_fk " +
						"inner join TIEUCHITHUONGSKU_DDKD on TIEUCHITHUONGSKU_DDKD.tieuchithuongsku_fk = TIEUCHITHUONGSKU.pk_seq " +
									"and TIEUCHITHUONGSKU_DDKD.ddkd_fk = a.ddkd_fk " +
				"where  a.ngaynhap >= '" + obj.getTungay() + "' and a.ngaynhap <= '" + obj.getDenngay() + "' and a.trangthai = '1'  " +
						"and TIEUCHITHUONGSKU.thang = '" + thang + "' and TIEUCHITHUONGSKU.nam = '" + nam + "' and TIEUCHITHUONGSKU.trangthai = '1' " +
				"group by a.npp_fk, a.ddkd_fk, a.gsbh_fk, TIEUCHITHUONGSKU.pk_seq, TIEUCHITHUONGSKU_DDKD.phaidat, nhomsanpham_sanpham.nsp_fk " +
				"having ( sum(b.soluong) / TIEUCHITHUONGSKU_DDKD.phaidat )  >= 1 " +
			") " +
			"DETAIL inner join " +
			"( " +
			
				"select thuong.npp_fk, thuong.ddkd_fk, thuong.gsbh_fk, thuong.tctId, thuong.scheme, thuong.phaidat, thuong.nsp_fk, " +
				"			sum(thuong.thucdat) as thucdat, " +
				"			sum(thuong.thuong) as thuong, sum(thuong.thuongtoida) as thuongtoida, " +
				"			sum(thuong.thuongGS) as thuongGS, sum(thuong.thuongtoidaGS) as thuongtoidaGS, " +
				"			sum(thuong.thuongASM) as thuongASM, sum(thuong.thuongtoidaASM) as thuongtoidaASM " +
				"from " +
				"( " +
					"select a.npp_fk, a.ddkd_fk, gsbh_fk, TIEUCHITHUONGSKU.pk_seq as tctId, TIEUCHITHUONGSKU.scheme, TIEUCHITHUONGSKU_DDKD.phaidat, " +
					"	nhomsanpham_sanpham.nsp_fk, nhomsanpham_sanpham.sp_fk, " +
					"	sum(nhomsanpham_sanpham.thuong_sr) as thuong, sum(distinct nhomsanpham_sanpham.thuong_td_sr) as thuongtoida, " +
					"	sum(nhomsanpham_sanpham.thuong_ss) as thuongGS, sum(distinct nhomsanpham_sanpham.thuong_td_ss) as thuongtoidaGS,  " +
					"	sum(nhomsanpham_sanpham.thuong_asm) as thuongASM, sum(distinct nhomsanpham_sanpham.thuong_td_asm) as thuongtoidaASM, " +
					"	sum(b.soluong) as thucdat " +
					"from donhang a inner join donhang_sanpham b on a.pk_seq = b.donhang_fk " +
					"	inner join nhomsanpham_sanpham on b.sanpham_fk = nhomsanpham_sanpham.sp_fk " +
					"	inner join TIEUCHITHUONGSKU_NHOMSP on nhomsanpham_sanpham.nsp_fk = TIEUCHITHUONGSKU_NHOMSP.nhomsp_fk  " +
					"	inner join TIEUCHITHUONGSKU on TIEUCHITHUONGSKU.pk_seq = TIEUCHITHUONGSKU_NHOMSP.tieuchithuongsku_fk  " +
					"	inner join TIEUCHITHUONGSKU_DDKD on TIEUCHITHUONGSKU_DDKD.tieuchithuongsku_fk = TIEUCHITHUONGSKU.pk_seq  " +
					"		and TIEUCHITHUONGSKU_DDKD.ddkd_fk = a.ddkd_fk  " +
					"where  a.ngaynhap >= '" + obj.getTungay() + "' and a.ngaynhap <= '" + obj.getDenngay() + "' and a.trangthai = '1' " +
					"	and TIEUCHITHUONGSKU.thang = '" + thang + "' and TIEUCHITHUONGSKU.nam = '" + nam + "' and TIEUCHITHUONGSKU.trangthai = '1' " +
					"group by a.npp_fk, a.ddkd_fk, gsbh_fk, TIEUCHITHUONGSKU.pk_seq, TIEUCHITHUONGSKU.scheme,  " +
					"TIEUCHITHUONGSKU_DDKD.phaidat, nhomsanpham_sanpham.nsp_fk, nhomsanpham_sanpham.sp_fk " +
					"having ( sum(b.soluong) / TIEUCHITHUONGSKU_DDKD.phaidat ) >= 1 " +
				") thuong " +
				"group by thuong.npp_fk, thuong.ddkd_fk, thuong.gsbh_fk, thuong.tctId, thuong.scheme, thuong.phaidat, thuong.nsp_fk " +
				
			") " +
			"CHITIEU on DETAIL.npp_fk = CHITIEU.npp_fk and DETAIL.ddkd_fk = CHITIEU.ddkd_fk and DETAIL.gsbh_fk = CHITIEU.gsbh_fk and DETAIL.tctId = CHITIEU.tctId " +
			"left JOIN " +
			"( " +
				"select tieuchithuongsku_fk as tctId, nhomsp_fk, trongso from TIEUCHITHUONGSKU_NHOMSP  " +
				"where trongso > 0 and tieuchithuongsku_fk in (select pk_seq from TIEUCHITHUONGSKU where thang = '" + thang + "' and nam = '" + nam + "' and trangthai = '1' ) " +
			")  " +
			"TRONGSO on DETAIL.nsp_fk = TRONGSO.nhomsp_fk and  DETAIL.tctId = TRONGSO.tctId " +
			"inner join NHAPHANPHOI on DETAIL.npp_fk = NHAPHANPHOI.pk_seq " +
			"inner join KHUVUC on NHAPHANPHOI.khuvuc_fk = KHUVUC.pk_seq " +
			"inner join VUNG on KHUVUC.vung_fk = VUNG.pk_seq " +
			"inner join GIAMSATBANHANG on DETAIL.gsbh_fk = GIAMSATBANHANG.pk_seq " +
			"inner join DAIDIENKINHDOANH on DETAIL.ddkd_fk = DAIDIENKINHDOANH.pk_seq " +
			"left join ASM_KHUVUC on ASM_KHUVUC.khuvuc_fk = KHUVUC.pk_seq " +
			"left join ASM on ASM_KHUVUC.asm_fk = ASM.pk_seq " +
			"where ( DETAIL.soxuat * 100 >= TRONGSO.TrongSo ) ";
			
			if(condition.length() > 0)
			query += condition;
			
			query += "group by DETAIL.npp_fk, DETAIL.gsbh_fk, DETAIL.ddkd_fk, DETAIL.PHAIDAT, CHITIEU.THUCDAT, CHITIEU.tctId, " +
						"CHITIEU.scheme, CHITIEU.thuong, CHITIEU.thuongtoida, CHITIEU.thuongGS, CHITIEU.thuongtoidaGS, CHITIEU.thuongASM, CHITIEU.thuongtoidaASM, " +
						"VUNG.ten, KHUVUC.ten, ASM.pk_seq, ASM.ten, NHAPHANPHOI.ma, NHAPHANPHOI.ten, " +
						"GIAMSATBANHANG.ten, DAIDIENKINHDOANH.ten  " +
						
			"having count(DETAIL.ddkd_fk) >= ( 	select COUNT(*)  " +
												"from TIEUCHITHUONGSKU_NHOMSP " +
												" where trongso > 0 and tieuchithuongsku_fk = CHITIEU.tctId ) " +
			"order by DETAIL.npp_fk asc";
		}
		
		System.out.println("1.Chi tieu SKU tap trung: " + query);
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
					String vung = rs.getString("vungTen");
					String khuvuc = rs.getString("kvTen");
					
					String asm = rs.getString("asmTen");
					String gsbh = rs.getString("gsbhTen");
					String ddkd = rs.getString("ddkdTen");
					String maNPP = rs.getString("sitecode");
					String tenNPP = rs.getString("nppten");
					String scheme = rs.getString("scheme");
					
					long phaidat = Math.round(rs.getDouble("phaidat"));
					long thucdat = Math.round(rs.getDouble("thucdat"));
					//float soxuat = rs.getFloat("soxuat");
					double phantram = rs.getDouble("thucdat") * 100 / rs.getDouble("phaidat");
					
					double thuong = rs.getDouble("thuong");
					double thuongGS = rs.getDouble("thuongGS");
					double thuongASM = rs.getDouble("thuongASM");
					 
				    double thuongtoida = rs.getDouble("thuongtoida"); 
				    double thuongtoidaGS = rs.getDouble("thuongtoidaGS");
				    double thuongtoidaASM = rs.getDouble("thuongtoidaASM");
					
				   // System.out.println("__DDKD_TEN_THUONGSR: " + ddkd + " -- Thuong: " + thuong + " -- Thuong toi da: " + thuongtoida);
				   // System.out.println("__DDKD_TEN_THUONGSS: " + ddkd + " -- Thuong: " + thuongGS + " -- Thuong toi da: " + thuongtoidaGS);
				   // System.out.println("__DDKD_TEN_THUONGASM: " + ddkd + " -- Thuong: " + thuongASM + " -- Thuong toi da: " + thuongtoidaASM);
				    
					//long tienThuong = Math.round(soxuat * thuong);
					long tienThuong = Math.round(thucdat * thuong);
					if(thuongtoida > -1)
					{
						//System.out.println("__DDKD_TEN_THUONGSR: " + ddkd + " -- Tien Thuong: " + tienThuong + " -- Thuong toi da: " + Math.round(thuongtoida));
						if(tienThuong > Math.round(thuongtoida))
							tienThuong = Math.round(thuongtoida);
					}
					
					//long tienThuongGS = Math.round(soxuat * thuongGS);
					long tienThuongGS = Math.round(thucdat * thuongGS);
					if(thuongtoidaGS > -1)
					{
						if(tienThuongGS > Math.round(thuongtoidaGS))
							tienThuongGS = Math.round(thuongtoidaGS);
					}
					
					//long tienThuongASM = Math.round(soxuat * thuongASM);
					long tienThuongASM = Math.round(thucdat * thuongASM);
					if(thuongtoidaASM > -1)
					{
						if(tienThuongASM > Math.round(thuongtoidaASM))
							tienThuongASM = Math.round(thuongtoidaASM);
					}

					cell = cells.getCell("EA" + Integer.toString(i)); 	cell.setValue(vung);
					cell = cells.getCell("EB" + Integer.toString(i)); 	cell.setValue(khuvuc);
					cell = cells.getCell("EC" + Integer.toString(i)); 	cell.setValue(maNPP);
					cell = cells.getCell("ED" + Integer.toString(i)); 	cell.setValue(tenNPP);
					cell = cells.getCell("EE" + Integer.toString(i)); 	cell.setValue(asm);
					cell = cells.getCell("EF" + Integer.toString(i)); 	cell.setValue(gsbh);
					cell = cells.getCell("EG" + Integer.toString(i)); 	cell.setValue(ddkd);	
					cell = cells.getCell("EH" + Integer.toString(i)); 	cell.setValue(scheme);	
					cell = cells.getCell("EI" + Integer.toString(i)); 	cell.setValue(phaidat);
					cell = cells.getCell("EJ" + Integer.toString(i)); 	cell.setValue(thucdat);
					//cell = cells.getCell("AK" + Integer.toString(i)); 	cell.setValue(soxuat);
					cell = cells.getCell("EK" + Integer.toString(i)); 	cell.setValue(phantram);
					cell = cells.getCell("EL" + Integer.toString(i)); 	cell.setValue(tienThuong);
					cell = cells.getCell("EM" + Integer.toString(i)); 	cell.setValue(tienThuongGS);
					cell = cells.getCell("EN" + Integer.toString(i)); 	cell.setValue(tienThuongASM);
					
					i++;
				}
				if(rs!=null)
					rs.close();
				if(db != null) 
					db.shutDown();
				if(i==2){
					throw new Exception("Khong co bao cao trong thoi gian nay...");
				}
			
			} 
			catch (Exception e) 
			{
				System.out.println("Exception: " + e.getMessage());
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
	
	private String getDauthang(int thang, int nam)
	{
		String _thang = Integer.toString(thang);
		if(thang < 10)
			_thang = "0" + _thang;
		
		return Integer.toString(nam) + "-" + _thang + "-01";
	}

	private String getCuoithang(int month, int year) 
    {
        String ngay = "";
        switch (month)
        {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                {
                    ngay = "31";
                    break;
                }
            case 4:
            case 6:
            case 9:
            case 11:
                {
                    ngay = "30";
                    break;
                }
            case 2:
                {
                    if (year % 4 == 0)
                        ngay = "29";
                    else
                        ngay = "28";
                    break;
                }
        }

        String _thang = Integer.toString(month);
		if(month < 10)
			_thang = "0" + _thang;
		
        return Integer.toString(year) + "-" + _thang + "-" + ngay;
    } 

}
