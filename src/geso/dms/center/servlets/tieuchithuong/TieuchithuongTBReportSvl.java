package geso.dms.center.servlets.tieuchithuong;

import geso.dms.center.beans.tieuchithuong.ITieuchithuongTBList;
import geso.dms.center.beans.tieuchithuong.imp.TieuchithuongTBList;
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

public class TieuchithuongTBReportSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    
    public TieuchithuongTBReportSvl() {
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
	    
	    ITieuchithuongTBList obj = new TieuchithuongTBList();
	    obj.setUserId(userId);
	    
	    obj.initReport("");
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/AHF/pages/Center/TieuChiThuongTBReport.jsp";
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
	    ITieuchithuongTBList obj = new TieuchithuongTBList();
	    
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
    		
        		response.sendRedirect("/AHF/pages/Center/TieuChiThuongTBReport.jsp");
    		}
    		else
    		{
    			response.setContentType("application/xlsm");
    			response.setHeader("Content-Disposition", "attachment; filename=ThuongTrungBay"+util.setTieuDe(obj)+".xlsm");
    			
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
		
    		response.sendRedirect("/AHF/pages/Center/TieuChiThuongTBReport.jsp");
	    }
	   
	}

	private void createPivotTable(ITieuchithuongTBList obj, OutputStream outPv)
	{
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();	
		
		try 
		{
			//fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\TieuChiThuongTB.xlsm");
			File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\TieuChiThuongTB.xlsm");
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
	
	private void CreateStaticHeader(Workbook workbook, String dateFrom, String dateTo, String UserName, ITieuchithuongTBList obj) 
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
	    
	    String tieude = "THƯỞNG TRƯNG BÀY";
	    ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);
	         
	    /*if(obj.getType().trim().equals("1"))
	    {
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("A2");
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Tháng: " + dateFrom );
		    
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("B2"); 
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Năm: " + dateTo );
	    }
	    else
	    {*/
	    	cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("A2");
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ ngày: " + obj.getTungay() );
		    
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("B2"); 
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến ngày: " + obj.getDenngay() );
	    //}
		
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
	    cell = cells.getCell("EI1"); 	cell.setValue("DangKy");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("EJ1"); 	cell.setValue("DeNghi");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("EK1"); 	cell.setValue("Duyet");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("EL1"); 	cell.setValue("PhanTram");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("EM1"); 	cell.setValue("ThuongSR");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("EN1"); 	cell.setValue("ThuongSS");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("EO1"); 	cell.setValue("ThuongASM");  ReportAPI.setCellHeader(cell);
	}
	
	private boolean CreateStaticData(Workbook workbook, ITieuchithuongTBList obj) 
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
		
		String query = " select Vung.TEN as vungTen, KhuVuc.TEN as kvTen, NhaPhanPhoi.SITECODE, NhaPhanPhoi.TEN as nppTen, isnull(ASM.TEN, '') as asmTen, GIAMSATBANHANG.TEN as gsbhTen, " +
							"DAIDIENKINHDOANH.TEN as ddkdTen,	dophu.*  " +
						"from " +
						"( " +
							"select thucdat.cttbId, dieukien.SCHEME, thucdat.NPP_FK, thucdat.ASM, thucdat.GSBH_FK, thucdat.DDKD_FK,  " +
								"SUM(thucdat.dangKy) as dangKy, SUM(thucdat.deNghi) as deNghi, SUM(thucdat.soXuat) as soXuat, SUM(thucdat.soXuat) * 100 / SUM(thucdat.deNghi) as PTDat,  " +
								"SUM( case when thucdat.soXuat * dieukien.thuongSR > dieukien.thuongTDSR then dieukien.thuongTDSR else thucdat.soXuat * dieukien.thuongSR end ) as thuongSR,   " +
								"SUM( case when thucdat.soXuat * dieukien.thuongSS > dieukien.thuongTDSS then dieukien.thuongTDSS else thucdat.soXuat * dieukien.thuongSS end ) as thuongSS,   " +
								"SUM( case when thucdat.soXuat * dieukien.thuongASM > dieukien.thuongTDASM then dieukien.thuongTDASM else thucdat.soXuat * dieukien.thuongASM end ) as thuongASM  " +
							"from " +
							"(  " +
								"select a.CTTRUNGBAY_FK as cttbId, f.NPP_FK, g.PK_SEQ as ddkd_fk, i.PK_SEQ as gsbh_fk, k.PK_SEQ as ASM,  " +
									"SUM(d.XUATDANGKY) as dangKy, SUM(d.XUATDENGHI) as deNghi, SUM(d.XUATDUYET) as soXuat, SUM(d.XUATDUYET) * 100 / SUM(d.XUATDENGHI) as PTDat   " +
								"from DENGHITRATRUNGBAY a     " +
									"inner join CTTRUNGBAY c on a.CTTRUNGBAY_FK = c.PK_SEQ " +
									"inner join DENGHITRATB_KHACHHANG d on a.PK_SEQ = d.DENGHITRATB_FK   " +
									"inner join KHACHHANG_TUYENBH e on d.KHACHHANG_FK = e.KHACHHANG_FK   " +
									"inner join TUYENBANHANG f on e.TBH_FK = f.PK_SEQ   " +
									"inner join DAIDIENKINHDOANH g on f.DDKD_FK = g.PK_SEQ   " +
									"inner join DDKD_GSBH h on g.PK_SEQ = h.DDKD_FK   " +
									"inner join GIAMSATBANHANG i on h.GSBH_FK = i.PK_SEQ    " +
									"left join ASM_GSBH j on i.PK_SEQ = j.GSBH_FK   " +
									"left join ASM k on j.ASM_FK = k.PK_SEQ   " +
								"where c.NGAYTRUNGBAY >= '" + obj.getTungay() + "' " + ( obj.getDenngay().trim().length() > 0 ? " and c.NGAYTRUNGBAY <= '" + obj.getDenngay() + "' " : "  "  ) +
										" and a.trangthai = '1'  " + ( obj.getSchemeIds().trim().length() > 0 ? " and a.CTTRUNGBAY_FK = '" + obj.getSchemeIds() + "' " : "  "  ) + 
								"group by a.CTTRUNGBAY_FK, f.NPP_FK, g.PK_SEQ, i.PK_SEQ, k.PK_SEQ " +
							")  " +
							"thucdat inner join " +
							"( " +
								"select a.PK_SEQ as cttbId, a.SCHEME, b.tumuc, b.denmuc, b.thuongSR, b.thuongTDSR, b.thuongSS, b.thuongTDSS, b.thuongASM, b.thuongTDASM   " +
								"from CTTRUNGBAY a inner join TIEUCHITHUONGTB_TIEUCHI b on a.PK_SEQ = b.cttb_fk  " +
								"where a.NGAYTRUNGBAY >= '" + obj.getTungay() + "' " + ( obj.getDenngay().trim().length() > 0 ? " and a.NGAYTRUNGBAY <= '" + obj.getDenngay() + "' " : "  "  ) +
							") " +
							"dieukien on thucdat.cttbId = dieukien.cttbId  " +
							"where thucdat.PTDat >= dieukien.tumuc and thucdat.PTDat < dieukien.denmuc " +
							"group by thucdat.cttbId, dieukien.SCHEME, thucdat.NPP_FK, thucdat.ASM, thucdat.GSBH_FK, thucdat.DDKD_FK  " +
						") " +
						"dophu inner join NHAPHANPHOI on dophu.npp_fk = NHAPHANPHOI.pk_seq  " +
						"inner join KHUVUC on NHAPHANPHOI.khuvuc_fk = KHUVUC.pk_seq " +
						"inner join VUNG on KHUVUC.vung_fk = VUNG.pk_seq  " +
						"inner join GIAMSATBANHANG on dophu.gsbh_fk = GIAMSATBANHANG.pk_seq  " +
						"inner join DAIDIENKINHDOANH on dophu.ddkd_fk = DAIDIENKINHDOANH.pk_seq  " +
						"left join ASM on ASM.PK_SEQ = KHUVUC.asm_fk  "+
						"where  1=1 " ;
		System.out.println("1.Chi tieu trung bay: " + query);
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
					
					String maNPP = rs.getString("sitecode");
					String tenNPP = rs.getString("nppten");
					
					String asm = rs.getString("asmTen") == null ? "" : rs.getString("asmTen") ;
					String gsbh = rs.getString("gsbhTen");
					String ddkd = rs.getString("ddkdTen");
					
					String scheme = rs.getString("scheme");
					
					double dangky = rs.getDouble("dangky");
					double denghi = rs.getDouble("denghi");
					double dat = rs.getDouble("soXuat");
					double phantram = rs.getDouble("PTDat");
					
					double thuongSR = rs.getDouble("thuongSR");
					double thuongSS = rs.getDouble("thuongSS");
					double thuongASM = rs.getDouble("thuongASM");
					 
					cell = cells.getCell("EA" + Integer.toString(i)); 	cell.setValue(vung);
					cell = cells.getCell("EB" + Integer.toString(i)); 	cell.setValue(khuvuc);
					cell = cells.getCell("EC" + Integer.toString(i)); 	cell.setValue(maNPP);
					cell = cells.getCell("ED" + Integer.toString(i)); 	cell.setValue(tenNPP);
					cell = cells.getCell("EE" + Integer.toString(i)); 	cell.setValue(asm);
					cell = cells.getCell("EF" + Integer.toString(i)); 	cell.setValue(gsbh);
					cell = cells.getCell("EG" + Integer.toString(i)); 	cell.setValue(ddkd);	
					cell = cells.getCell("EH" + Integer.toString(i)); 	cell.setValue(scheme);
					
					cell = cells.getCell("EI" + Integer.toString(i)); 	cell.setValue(dangky);
					cell = cells.getCell("EJ" + Integer.toString(i)); 	cell.setValue(denghi);
					cell = cells.getCell("EK" + Integer.toString(i)); 	cell.setValue(dat);
					cell = cells.getCell("EL" + Integer.toString(i)); 	cell.setValue(phantram);
					
					cell = cells.getCell("EM" + Integer.toString(i)); 	cell.setValue(thuongSR);
					cell = cells.getCell("EN" + Integer.toString(i)); 	cell.setValue(thuongSS);
					cell = cells.getCell("EO" + Integer.toString(i)); 	cell.setValue(thuongASM);
					
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
