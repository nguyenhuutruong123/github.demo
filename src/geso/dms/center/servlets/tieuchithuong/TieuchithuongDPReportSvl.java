package geso.dms.center.servlets.tieuchithuong;

import geso.dms.center.beans.tieuchithuong.ITieuchithuongDPList;
import geso.dms.center.beans.tieuchithuong.imp.TieuchithuongDPList;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
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
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class TieuchithuongDPReportSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    
    public TieuchithuongDPReportSvl() {
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
	    
	    ITieuchithuongDPList obj = new TieuchithuongDPList();
	    obj.setUserId(userId);
	    
	    obj.initReport("");
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/AHF/pages/Center/TieuChiThuongDPReport.jsp";
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
	    ITieuchithuongDPList obj = new TieuchithuongDPList();
	    
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
	    
	    String schemeId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("schemeId"));
	    if(schemeId == null)
	    	schemeId = "";
	    obj.setSchemeIds(schemeId);
	    
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    
	    if(action.equals("taobc"))
	    {
    		if(obj.getNam().trim().length() <= 0 || obj.getThang().trim().length() <= 0 )
    		{
    			obj.setUserId(userId);
        		obj.initReport("");
        		obj.setMsg("Thời gian bạn chọn không hợp lệ");

    	    	session.setAttribute("obj", obj);  	
        		session.setAttribute("userId", userId);
    		
        		response.sendRedirect("/AHF/pages/Center/TieuChiThuongDPReport.jsp");
    		}
    		else
    		{
    			response.setContentType("application/xlsm");
    			response.setHeader("Content-Disposition", "attachment; filename=TieuChiThuongDP.xlsm");
    			
    			OutputStream outPv = response.getOutputStream();
    			obj.setUserId(userName);
    			createPivotTable(obj, outPv);
    		}
	    }
	    else
	    {
    		obj.setUserId(userId);
    		obj.initReport("");

	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/AHF/pages/Center/TieuChiThuongDPReport.jsp");
	    }
	   
	}

	private void createPivotTable(ITieuchithuongDPList obj, OutputStream outPv)
	{
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();	
		
		try 
		{
			//fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\TieuChiThuongDP.xlsm");
			File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\TieuChiThuongDP.xlsm");
			fstream = new FileInputStream(f) ;
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			
		     CreateStaticHeader(workbook, obj.getThang(), obj.getNam(), obj.getUserId(), obj);
		     CreateStaticData(workbook, obj);
		    
		     workbook.save(outPv);
				
			fstream.close();
		} 
		catch (Exception e) { System.out.println("__EXCEPTION REPORT: " + e.getMessage()); }
	}
	
	private void CreateStaticHeader(Workbook workbook, String dateFrom, String dateTo, String UserName, ITieuchithuongDPList obj) 
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
	    
	    String tieude = "THƯỞNG ĐỘ PHỦ";
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
	    cell = cells.getCell("EI1"); 	cell.setValue("TieuChi");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("EJ1"); 	cell.setValue("TuMuc");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("EK1"); 	cell.setValue("DenMuc");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("EL1"); 	cell.setValue("SoOutLet");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("EM1"); 	cell.setValue("ThuongSR");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("EN1"); 	cell.setValue("ThuongSS");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("EO1"); 	cell.setValue("ThuongASM");  ReportAPI.setCellHeader(cell);
	}
	
	private boolean CreateStaticData(Workbook workbook, ITieuchithuongDPList obj) 
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    
	    String condition = "";
	    if(obj.getVungId().length() > 0)
	    	condition += " and VUNG.pk_seq = '" + obj.getVungId() + "' ";
	    if(obj.getKvId().length() > 0)
	    	condition += " and KHUVUC.pk_seq = '" + obj.getKvId() + "' ";
	    if(obj.getNppIds().length() > 0)
	    	condition += " and NHAPHANPHOI.pk_seq = '" + obj.getNppIds() + "' ";
		
		String query = "";
		
		query =	" select Vung.TEN as vungTen, KhuVuc.TEN as kvTen, NhaPhanPhoi.SITECODE, NhaPhanPhoi.TEN as nppTen, isnull(ASM.TEN, '') as asmTen, GIAMSATBANHANG.TEN as gsbhTen, " +
					"DAIDIENKINHDOANH.TEN as ddkdTen, (select scheme from TIEUCHITHUONGDP where pk_seq = dophu.tctId) as Scheme, dophu.*  " +
				"from  " +
				"( " +
					" select thuctemua.tctId, thuctemua.NPP_FK, thuctemua.ASM, thuctemua.GSBH_FK, thuctemua.DDKD_FK, tieuchi.tieuchi, tumuc, denmuc,  " +
						"SUM(thuctemua.soOutLet) as soOutLet,  " +
						"SUM(distinct thuongSR) as thuongSR, SUM(distinct thuongSS) as thuongSS,  SUM(distinct thuongASM) as thuongASM   " +
					"from " +
					"( " +
						"select donhangmua.*, nhomchao.tctId, nhomchao.totalThungChao, nhomchao.totalLeChao   " +
						"from  " +
						"(  " +
							"select a.NPP_FK, a.ASM, a.GSBH_FK, a.DDKD_FK, a.KHACHHANG_FK, SUM(b.soluong) as totalLe,   " +
								"SUM(b.soluong * isnull(c.soluong2, 0) / isnull(c.soluong1, 1) ) as totalThung,    " +
								"COUNT(distinct a.khachhang_fk) as soOutLet,  SUM(b.SOLUONG * b.GIAMUA) as DoanhSo   " +
							"from DONHANG a inner join DONHANG_SANPHAM b on a.PK_SEQ = b.DONHANG_FK left join QUYCACH c on b.SANPHAM_FK = c.SANPHAM_FK  " +
								"and c.DVDL2_FK = '100018'  where a.TRANGTHAI = 1 and a.NGAYNHAP like '%" + obj.getNam() + "-" + ( obj.getThang().trim().length() < 2 ? "0" + obj.getThang() : obj.getThang()  )  + "%'    " +
							"group by a.NPP_FK, a.ASM, a.GSBH_FK, a.DDKD_FK, a.KHACHHANG_FK    " +
						")  " +
						"donhangmua inner join  " +
						"(  " +
							"select f.PK_SEQ as tctId, e.PK_SEQ as dkdpId,  a.NPP_FK, a.ASM, a.GSBH_FK, a.DDKD_FK, a.KHACHHANG_FK,  " +
								"SUM(b.soluong * isnull(c.soluong2, 0) / isnull(c.soluong1, 1) ) as totalThungChao, SUM(b.soluong) as totalLeChao   " +
							"from DONHANG a inner join DONHANG_SANPHAM b on a.PK_SEQ = b.DONHANG_FK left join QUYCACH c on b.SANPHAM_FK = c.SANPHAM_FK   " +
								"and c.DVDL2_FK = '100018'  inner join DieuKienDoPhu_SanPham d on b.SANPHAM_FK = d.sanpham_fk   " +
							"inner join DieuKienDoPhu e on d.dieukien_fk = e.pk_seq inner join TIEUCHITHUONGDP f on e.pk_seq = f.nhomdieukien_fk    " +
							"where a.TRANGTHAI = 1 and a.NGAYNHAP like '%" + obj.getNam() + "-" + ( obj.getThang().trim().length() < 2 ? "0" + obj.getThang() : obj.getThang()  ) + "%' and f.thang = '" + obj.getThang() + "' and f.nam = '" + obj.getNam() + "'     " +
							"group by f.PK_SEQ, e.PK_SEQ, a.NPP_FK, a.ASM, a.GSBH_FK, a.DDKD_FK, a.KHACHHANG_FK   " +
						")  " +
						"nhomchao on donhangmua.NPP_FK = nhomchao.NPP_FK and donhangmua.GSBH_FK = nhomchao.GSBH_FK  " +
							"and donhangmua.DDKD_FK = nhomchao.DDKD_FK and donhangmua.KHACHHANG_FK = nhomchao.KHACHHANG_FK  " +
						"inner join   " +
						"(   " +
							"select a.pk_seq as tctId, b.pk_seq as dkdpId, b.soluong, b.sotien, b.IsThung   " +
							"from TIEUCHITHUONGDP a inner join DieuKienDoPhu b on a.nhomdieukien_fk = b.pk_seq    " +
							"where a.thang = '" + obj.getThang() + "' and a.nam = '" + obj.getNam() + "'  and a.trangthai = '1'  " +
						")   " +
						"dieukien on nhomchao.tctId = dieukien.tctId  and nhomchao.dkdpId = dieukien.dkdpId   " +
						"where	( dieukien.sotien > 0 and donhangmua.DoanhSo >= dieukien.sotien )   " +
							"or ( dieukien.soluong > 0 and dieukien.isThung = 0 and nhomchao.totalLeChao > dieukien.soluong )   " +
							"or ( dieukien.soluong > 0 and dieukien.isThung = 1 and nhomchao.totalThungChao > dieukien.soluong )   " +
					") " +
					"thuctemua, " +
					"( " +
						"select thuongdp_fk, tieuchi, doanhso, tumuc, denmuc, thuongSR, thuongSS, thuongASM  " +
						"from TIEUCHITHUONGDP_TIEUCHI  " +
						"where thuongdp_fk in ( select pk_seq from TIEUCHITHUONGDP where THANG = '" + obj.getThang() + "' and NAM = '" + obj.getNam() + "' and TRANGTHAI = '1' )	 " +
					") " +
					"tieuchi  " +
					"where tieuchi.thuongdp_fk = thuctemua.tctId and thuctemua.DoanhSo >= tieuchi.doanhso " +
					"group by thuctemua.tctId, thuctemua.NPP_FK, thuctemua.ASM, thuctemua.GSBH_FK, thuctemua.DDKD_FK, tieuchi.tieuchi, tumuc, denmuc " +
					"having SUM(thuctemua.soOutLet) >= tumuc  " +
				") " +
				"dophu " +
				"inner join NHAPHANPHOI on dophu.npp_fk = NHAPHANPHOI.pk_seq   " +
				"inner join KHUVUC on NHAPHANPHOI.khuvuc_fk = KHUVUC.pk_seq " +
				"inner join VUNG on KHUVUC.vung_fk = VUNG.pk_seq   " +
				"inner join GIAMSATBANHANG on dophu.gsbh_fk = GIAMSATBANHANG.pk_seq  " +
				"inner join DAIDIENKINHDOANH on dophu.ddkd_fk = DAIDIENKINHDOANH.pk_seq  " +
				"left join ASM on ASM.PK_SEQ = KHUVUC.asm_fk " +
				"order by tumuc desc, soOutLet desc ";
				//"where NHAPHANPHOI.TRANGTHAI = '1' and GIAMSATBANHANG.TRANGTHAI = '1' and DAIDIENKINHDOANH.TRANGTHAI = '1' " +
					//"and KHUVUC.TRANGTHAI = '1' and VUNG.TRANGTHAI = '1' ";
		
		if(condition.trim().length() > 0)
			query += condition;
		
		System.out.println("1.Chi tieu do phu: " + query);
		ResultSet rs = db.get(query);
		
		Hashtable<String, Integer> ddkd_selected = new Hashtable<String, Integer>();
		
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
					
					String maNPP = rs.getString("sitecode");
					String tenNPP = rs.getString("nppten");
					
					String asm = rs.getString("asmTen");
					String gsbh = rs.getString("gsbhTen");
					String ddkd = rs.getString("ddkdTen");
					
					String ddkd_fk = rs.getString("ddkd_fk");
					
					System.out.println("__CHECK EXIT: " + ddkd_selected.get(ddkd_fk));
					if(ddkd_selected.get(ddkd_fk) == null)
					{
						ddkd_selected.put(ddkd_fk, 1);
						
						String scheme = rs.getString("scheme");
						
						double thuongSR = rs.getDouble("thuongSR");
						double thuongSS = rs.getDouble("thuongSS");
						double thuongASM = rs.getDouble("thuongASM");
						
						String tieuchi = rs.getString("tieuchi");
						long tumuc = Math.round(rs.getDouble("tumuc"));
						long denmuc = Math.round(rs.getDouble("denmuc"));
						long soOutLet = Math.round(rs.getDouble("soOutLet"));
						
						/*long tienThuong = Math.round(soOutLet * thuongSR);		
						long tienThuongSS = Math.round(soOutLet * thuongSS);
						long tienThuongASM = Math.round(soOutLet * thuongASM);*/
						
						long tienThuong = Math.round(thuongSR);		
						long tienThuongSS = Math.round(thuongSS);
						long tienThuongASM = Math.round(thuongASM);
						
						cell = cells.getCell("EA" + Integer.toString(i)); 	cell.setValue(vung);
						cell = cells.getCell("EB" + Integer.toString(i)); 	cell.setValue(khuvuc);
						cell = cells.getCell("EC" + Integer.toString(i)); 	cell.setValue(maNPP);
						cell = cells.getCell("ED" + Integer.toString(i)); 	cell.setValue(tenNPP);
						cell = cells.getCell("EE" + Integer.toString(i)); 	cell.setValue(asm);
						cell = cells.getCell("EF" + Integer.toString(i)); 	cell.setValue(gsbh);
						cell = cells.getCell("EG" + Integer.toString(i)); 	cell.setValue(ddkd);	
						cell = cells.getCell("EH" + Integer.toString(i)); 	cell.setValue(scheme);	
						
						cell = cells.getCell("EI" + Integer.toString(i)); 	cell.setValue(tieuchi);
						cell = cells.getCell("EJ" + Integer.toString(i)); 	cell.setValue(tumuc);	
						cell = cells.getCell("EK" + Integer.toString(i)); 	cell.setValue(denmuc);	
						
						cell = cells.getCell("EL" + Integer.toString(i)); 	cell.setValue(soOutLet);
						cell = cells.getCell("EM" + Integer.toString(i)); 	cell.setValue(tienThuong);
						cell = cells.getCell("EN" + Integer.toString(i)); 	cell.setValue(tienThuongSS);
						cell = cells.getCell("EO" + Integer.toString(i)); 	cell.setValue(tienThuongASM);
					}

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
	

}
