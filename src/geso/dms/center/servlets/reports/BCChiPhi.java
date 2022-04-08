package geso.dms.center.servlets.reports;

import geso.dms.center.beans.khoahuanluyen.IKhoahuanluyenList;
import geso.dms.center.beans.khoahuanluyen.imp.KhoahuanluyenList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.distributor.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import T.BH;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class BCChiPhi extends HttpServlet
{
	private static final long serialVersionUID = 1L;

    public BCChiPhi() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		IKhoahuanluyenList obj = new KhoahuanluyenList();
		String querystring = request.getQueryString();
		
		Utility util = new Utility();
		obj.setUserId(util.getUserId(querystring));
		obj.setUserten((String) session.getAttribute("userTen"));
		
		obj.initBC("");
		session.setAttribute("obj", obj);		
		session.setAttribute("userId", obj.getUserId());
		session.setAttribute("userTen", obj.getUserTen());
		String nextJSP = "/AHF/pages/Center/BCChiPhi.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		IKhoahuanluyenList obj = new KhoahuanluyenList();
		OutputStream out = response.getOutputStream();
		Utility util = new Utility();
		
		String userId = (String) session.getAttribute("userId");
		obj.setUserId(userId);
		
		String userTen = (String) session.getAttribute("userTen");
		obj.setUserten(userTen);
		
		String thang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang"));
		if(thang == null)
			thang = "";
		obj.setTungay(thang);
		
		String nam = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nam"));
		if(nam == null)
			nam = "";
		obj.setDenngay(nam);
		
		obj.setVungId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")):""));
			
		obj.setKvId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")):""));
		
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action") != null? util.antiSQLInspection(request.getParameter("action"))) : "";
		String nextJSP = "/AHF/pages/Center/BCChiPhi.jsp";
		
		if(action.equals("Taomoi"))
		{
			try
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=ChiPhiTheoSanLuong.xlsm");
		        if(!CreatePivotTable(out, obj))
		        {
		        	obj.setMsg("Không có dữ liệu trong thời gian này.");
		        	obj.initBC("");
		    		session.setAttribute("obj", obj);	
		    		response.sendRedirect(nextJSP);
		        }
		        
			}
			catch(Exception ex)
			{
				obj.setMsg(ex.getMessage());
			}
		}
	
		obj.initBC("");
		session.setAttribute("obj", obj);	
		response.sendRedirect(nextJSP);
	}
	
	private boolean CreatePivotTable(OutputStream out, IKhoahuanluyenList obj) throws Exception 
	{
		//String chuoi = getServletContext().getInitParameter("path") + "\\ChiPhiTheoSanLuong.xlsm";
			
		//FileInputStream fstream = new FileInputStream(chuoi);
		File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\ChiPhiTheoSanLuong.xlsm");
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
	
	

	private void CreateStaticHeader(Workbook workbook, IKhoahuanluyenList obj) throws Exception 
	{
		try
		{	
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");

			Cells cells = worksheet.getCells();
			Cell cell = cells.getCell("A1");

			cell.setValue("BÁO CÁO CHI PHÍ TRÊN SẢN LƯỢNG"); 
			
		    /*cell = cells.getCell("A2");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
		    cell = cells.getCell("A3");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getUserTen());*/

	 	    Font font = new Font();
	 	    font.setName("Times New Roman");
	 	    font.setColor(Color.RED);//mau chu
	 	    font.setSize(16);// size chu
	 	   	font.setBold(true);
	 	   	
	 	   	cell = cells.getCell("A1");
	 	   	String tieude = "CÔNG TY CỔ PHẦN ĐƯỜNG BIÊN HÒA";
    	    ReportAPI.getCellStyle(cell, Color.BLACK, false, 10, tieude);
    	    
    	    cell = cells.getCell("A2");
    	    tieude = "PHÒNG KINH DOANH";
    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
    	    
    	    cell = cells.getCell("K3");
    	    String ngaybc = ReportAPI.NOW("yyyy-MM-dd");
    	    
    	    String ngay = ngaybc.substring(8, 10);
    	    String nam = ngaybc.substring(0, 4);
    	    String thang = ngaybc.substring(5, 7);
    	    
    	    tieude = "Ngày " + ngay + " tháng " + thang + " năm " + nam;
    	    ReportAPI.getCellStyle(cell, Color.BLACK, false, 10, tieude);
    	    
    	    cell = cells.getCell("A4");
    	    tieude = "BẢNG KÊ TỔNG HỢP CHI PHÍ THU NHẬP VÀ SẢN LƯỢNG TIÊU THỤ CỦA HTPP";
    	    ReportAPI.getCellStyle(cell, Color.BLACK, false, 15, tieude);
    	    
	    	    	    
    	    cell = cells.getCell("A7");
    	    tieude = "STT";
    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
    	    
    	    cell = cells.getCell("B7");
    	    tieude = "Nhà phân phối";
    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
    	    
   
    	    /********San Luong***********/
    	    cell = cells.getCell("C7");
    	    tieude = "Sản lượng (Kg)";
    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
    	    
    	    cell = cells.getCell("C8");
    	    tieude = "Công ty xuất";
    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
    	    
    	    cell = cells.getCell("D8");
    	    tieude = "Chỉ tiêu";
    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
    	    
    	    cell = cells.getCell("E8");
    	    tieude = "Thực hiện";
    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
    	    
    	    cell = cells.getCell("F8");
    	    tieude = "TH / CT (%)";
    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
    	    
    	    
    	    cell = cells.getCell("G7");
    	    tieude = "NVBH";
    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
    	    
    	    
    	    /********Chi Phi***********/
    	    cell = cells.getCell("H7");
    	    tieude = "Chi Phí";
    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
    	    
    	    cell = cells.getCell("H8");
    	    tieude = "Lương nhân viên";
    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
    	    
    	    cell = cells.getCell("I8");
    	    tieude = "Lương giám sát";
    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
    	    
    	    cell = cells.getCell("J8");
    	    tieude = "Tổng";
    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
    	    
    	    
    	    /********Binh Quan***********/
    	    cell = cells.getCell("K7");
    	    tieude = "Lương BQ";
    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
    	    
    	    cell = cells.getCell("K8");
    	    tieude = "NVBH";
    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
    	    
    	    cell = cells.getCell("L8");
    	    tieude = "GSBH";
    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
    	    
    	 
    	    cell = cells.getCell("M7");
    	    tieude = "Chi phí / sản lượng";
    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
    	    
		}
		catch(Exception ex)
		{
			throw new Exception("Bao cao bi loi khi khoi tao");
		}
	}
	
	
	private boolean CreateStaticData(Workbook workbook, IKhoahuanluyenList obj) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
		
	    String thang = obj.getTungay();
    	if(thang.length() < 2)
    		thang = "0" + thang;
    	String ngaydh = obj.getDenngay() + "-" + thang;
    	
	    String query = "select kv.TEN as kvTen, npp.PK_SEQ as nppId, npp.TEN as nppTen,  " +
	    					"ISNULL(chitieu.Chitieu, 0) as ChiTieu, ISNULL(thucdat.sltt, 0) as ThucHien, ISNULL(congtyxuat.sltt, 0) as CTyXuat, " +
	    					"'0' as luongNVBH, '0' as luongGSBH " +
	    		"from NHAPHANPHOI npp left join " +
	    		"( " +
	    			"select a.NHAPP_FK as nppId, sum(b.chitieu) as Chitieu    " +
	    			"from chitieunpp a inner join  Chitieunpp_ddkd b on a.pk_seq = b.chitieunpp_fk     " +
	    			"where a.thang = '" + obj.getTungay() + "' and a.nam = '" + obj.getDenngay() + "' and a.trangthai = '1'  " +
	    			"group by a.NHAPP_FK   " +
	    		")  " +
	    		"chitieu on npp.PK_SEQ = chitieu.nppId left join  " +
	    		"( " +
	    			"select b.NPP_FK as nppId, SUM(a.SOLUONG * c.TRONGLUONG) - isnull( ( select SUM(dhtv_sp.soluong * sp.TRONGLUONG) " +
	    										"from DONHANGTRAVE dhtv inner join DONHANGTRAVE_SANPHAM dhtv_sp on dhtv.PK_SEQ = dhtv_sp.DONHANGTRAVE_FK  " +
	    											"inner join SANPHAM sp on dhtv_sp.SANPHAM_FK = sp.PK_SEQ  " +
	    										"where dhtv.DONHANG_FK is null and dhtv.TRANGTHAI = '3'   " +
	    											"and dhtv.NGAYDUYET like '2012-09%' and dhtv.NPP_FK = b.NPP_FK  " +
	    										"group by dhtv.NPP_FK ), 0 )  as sltt  " +
	    			"from  donhang_sanpham a inner join donhang b on a.DONHANG_FK = b.PK_SEQ   " +
	    				"inner join SANPHAM c on a.SANPHAM_FK = c.PK_SEQ  " +
	    			"where b.ngaynhap like '" + ngaydh + "%' and b.trangthai = '1'  and b.PK_SEQ not in ( select DONHANG_FK from DONHANGTRAVE where TRANGTHAI = '3' and NGAYDUYET like '2012-09%' )   " +
	    			"group by b.NPP_FK  " +
	    		")  " +
	    		"thucdat on npp.PK_SEQ = thucdat.nppId left join " +
	    		"( " +
	    			"select a.NPP_FK as nppId, SUM(b.SOLUONG * c.TRONGLUONG) as sltt  " +
	    			"from NHAPHANG a inner join NHAPHANG_SP b on a.PK_SEQ = b.NHAPHANG_FK  inner join SANPHAM c on b.SANPHAM_FK = c.MA  " +
	    			"where a.NGAYCHUNGTU like '" + ngaydh + "%'  " +
	    			"group by a.NPP_FK  " +
	    		")  " +
	    		"congtyxuat on npp.PK_SEQ = congtyxuat.nppId  " +
	    		"inner join KHUVUC kv on npp.KHUVUC_FK = kv.PK_SEQ  " +
	    		"inner join VUNG v on kv.VUNG_FK = v.PK_SEQ  " +
	    	"union all  " +
	    		"select a.TEN as kvTen, '-2' as nppId, '' as nppTen, '0', '0', '0', '0', '0' " +
	    		"from KHUVUC a inner join VUNG b on a.VUNG_FK = b.PK_SEQ  " +
	    	"union all  " +
	    		"select a.TEN as kvTen, '100000000' as nppId, a.ten + N' Toltal' as nppTen, '0', '0', '0', '0', '0'  " +
	    		"from KHUVUC a inner join VUNG b on a.VUNG_FK = b.PK_SEQ " +
	    	"order by kvTen asc, nppId  asc";
	   
	    /*if(obj.getVungId().length() > 0)
	    	query += " and vungId = '" + obj.getVungId() + "' ";
	    
	    if(obj.getKvId().length() > 0)
	    	query += " and kvId = '" + obj.getKhlId() + "' ";*/
	    
	
		System.out.println("1.Tinh chi phi: " + query);
		ResultSet rs = db.get(query);
		
		
		Hashtable<String, Integer> soDDKD = new Hashtable<String, Integer>();
		Hashtable<String, Double> lgDDKD = getLuongDDKD(obj, db, soDDKD, thang);
		Hashtable<String, Double> lgGSBH = getLuongGiamSat(obj, db, thang);
		
		int i = 9;
		int stt = 0;
		
		if(rs!=null)
		{
			try 
			{
				cells.setColumnWidth(0, 8.0f);
				cells.setColumnWidth(1, 35.0f);
				
				for(int k = 2; k < 11; k++)
				{
					cells.setColumnWidth(k, 15.0f);
				}
				
				Cell cell = null;
				
				double ttCtyXuat = 0;
				double ttChiTieu = 0;
				double ttThucHien = 0;
				double ttLuongNV = 0;
				double ttLuongGS = 0;
				double ttSoNV = 0;
				double ttNpp = 0;
				
				String tenKV = "";
				
				while(rs.next())
				{
					String nppId = rs.getString("nppId");
					
					if( !tenKV.equals(rs.getString("kvTen").trim()))
					{
						ttLuongGS = 0;
						ttCtyXuat = 0;
						ttChiTieu = 0;
						ttSoNV = 0;
						ttLuongNV = 0;
						ttNpp = 0;
						
						tenKV = rs.getString("kvTen").trim();
					}
					
					if(nppId.equals("-2"))
					{
						String kvTen = rs.getString("kvTen");
										
						cell = cells.getCell("A" + Integer.toString(i)); 	//cell.setValue(kvTen);
						ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, kvTen);
						
						cell = cells.getCell("C" + Integer.toString(i)); 	cell.setValue("");
						cell = cells.getCell("D" + Integer.toString(i)); 	cell.setValue("");
						cell = cells.getCell("E" + Integer.toString(i)); 	cell.setValue("");
						cell = cells.getCell("F" + Integer.toString(i)); 	cell.setValue("");
						
						cell = cells.getCell("G" + Integer.toString(i)); 	cell.setValue("");
						cell = cells.getCell("H" + Integer.toString(i)); 	cell.setValue("");
						cell = cells.getCell("I" + Integer.toString(i)); 	cell.setValue("");
						cell = cells.getCell("J" + Integer.toString(i)); 	cell.setValue("");
						
						cell = cells.getCell("K" + Integer.toString(i)); 	cell.setValue("");
						cell = cells.getCell("L" + Integer.toString(i)); 	cell.setValue("");
					}
					else
					{
						if(! nppId.equals("100000000"))
						{
							String nppTen = rs.getString("nppTen");
							
							double CTyXuat = rs.getDouble("CTyXuat");				ttCtyXuat += CTyXuat;
							double ChiTieu = rs.getDouble("ChiTieu");				ttChiTieu += ChiTieu;
							double ThucHien = rs.getDouble("ThucHien");				ttThucHien += ThucHien;
							
							double luongGSBH = 0;
							if(lgGSBH.get(nppId) != null )
								luongGSBH = lgGSBH.get(nppId);
																					ttLuongGS += luongGSBH;
							
							double lgNVBH = 0;
							if(lgDDKD.get(nppId) != null )
								lgNVBH = lgDDKD.get(nppId);
																					ttLuongNV += lgNVBH;
							
							int soNVBH = 0;
							if(soDDKD.get(nppId) != null)
								soNVBH = soDDKD.get(nppId);
																					ttSoNV += soNVBH;
							stt++;
							ttNpp++;
							
							cell = cells.getCell("A" + Integer.toString(i)); 	cell.setValue(stt);	
							
							cell = cells.getCell("B" + Integer.toString(i)); 	cell.setValue(nppTen);
							
							cell = cells.getCell("C" + Integer.toString(i)); 	cell.setValue(CTyXuat);
							cell = cells.getCell("D" + Integer.toString(i)); 	cell.setValue(ChiTieu);
							cell = cells.getCell("E" + Integer.toString(i)); 	cell.setValue(ThucHien);
							
							double pt = 0;
							if(ChiTieu > 0)
								pt = ThucHien * 100 / ChiTieu;
							cell = cells.getCell("F" + Integer.toString(i)); 	cell.setValue(pt);
							
							cell = cells.getCell("G" + Integer.toString(i)); 	cell.setValue(soNVBH);
							cell = cells.getCell("H" + Integer.toString(i)); 	cell.setValue(lgNVBH);
							cell = cells.getCell("I" + Integer.toString(i)); 	cell.setValue(luongGSBH);
							cell = cells.getCell("J" + Integer.toString(i)); 	cell.setValue(lgNVBH + luongGSBH);
							
							double lgBinhquan = 0;
							if(soNVBH > 0)
								lgBinhquan = lgNVBH / soNVBH;
							
							cell = cells.getCell("K" + Integer.toString(i)); 	cell.setValue(lgBinhquan);
							cell = cells.getCell("L" + Integer.toString(i)); 	cell.setValue(luongGSBH);     //cell.setValue(luongGSBH / 8);
							
							double chiphi = 0;
							if(ThucHien > 0)
								chiphi = (lgNVBH + luongGSBH) / ThucHien;
							cell = cells.getCell("M" + Integer.toString(i)); 	cell.setValue(chiphi);
							
						}
						else  //Total theo khu vuc
						{
							String nppTen = rs.getString("nppTen");
												
							cell = cells.getCell("A" + Integer.toString(i)); 	//cell.setValue(nppTen);
							ReportAPI.getCellStyle(cell, Color.RED, true, 10, nppTen);
							
							cell = cells.getCell("C" + Integer.toString(i)); 	cell.setValue(ttCtyXuat);
							cell = cells.getCell("D" + Integer.toString(i)); 	cell.setValue(ttChiTieu);
							cell = cells.getCell("E" + Integer.toString(i)); 	cell.setValue(ttThucHien);
							
							double pt = 0;
							if(ttChiTieu > 0)
								pt = ttThucHien * 100 / ttChiTieu;
							cell = cells.getCell("F" + Integer.toString(i));  	cell.setValue(pt);
							
							cell = cells.getCell("G" + Integer.toString(i)); 	cell.setValue(ttSoNV);
							cell = cells.getCell("H" + Integer.toString(i)); 	cell.setValue(ttLuongNV);
							cell = cells.getCell("I" + Integer.toString(i)); 	cell.setValue(ttLuongGS);
							cell = cells.getCell("J" + Integer.toString(i)); 	cell.setValue(ttLuongNV + ttLuongGS);
							
							double lgBinhquan = 0;
							if(ttSoNV > 0)
								lgBinhquan = ttLuongNV / ttSoNV;
							
							cell = cells.getCell("K" + Integer.toString(i)); 	cell.setValue(lgBinhquan);
							
							
							double lgBinhquanGS = ttLuongGS;
							if(ttNpp > 0)
								lgBinhquanGS = ttLuongGS / ttNpp;
							cell = cells.getCell("L" + Integer.toString(i)); 	cell.setValue(lgBinhquanGS);
							
							double chiphi = 0;
							if(ttThucHien > 0)
								chiphi = (ttLuongNV + ttLuongGS) / ttThucHien;
							cell = cells.getCell("M" + Integer.toString(i)); 	cell.setValue(chiphi);
						}
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
	
	
	
	private Hashtable<String, Double> getLuongGiamSat(IKhoahuanluyenList obj, dbutils db, String thang)
	{
		Hashtable<String, Double> luongGSBH = new Hashtable<String, Double>();
		
		String ngaydh = obj.getDenngay() + "-" + thang;
		String dauthang = obj.getDenngay() + "-" + thang + "-01";
    	String cuoithang = obj.getDenngay() + "-" + thang + "-" + LastDayOfMonth(Integer.parseInt(thang), Integer.parseInt(obj.getDenngay())) ;
    	
		String query = "";
		query = "select thongtinluong.*, chitieunlv.nlvChitieu, chitieunlv.nlvThucte, ISNULL(tongphucap.tienphucap, 0) as TienPhuCap  " +
		"from " +
		"( " +
			"select v.pk_seq as vungId, v.ten as vungTen, kv.pk_seq as kvId, kv.ten as kvTen, gsbhId, gsbh.ten, gsbh.dienthoai, isnull(gsbh.tinhtrang, 0) as tinhtrang,  " +
				"case when sum ( PT_Dat * trongso / 100 ) < avg(mucbaohiem) then avg(mucbaohiem)  " +
				"else sum ( PT_Dat * trongso / 100 ) end as PT_KPI,  " +
				"avg(luongcoban) as luongcb, avg(ptluongtg) as ptluongtg, avg(ptluonghieuqua) as ptluonghieuqua, " +
				"avg(BHXH) as BHXH, avg(CongDoan) as CongDoan, avg(tdnc) as tdnc  " +
			"from  " +
			"(  " +
				"select chitieudera.kvId, chitieudera.gsbhId, chitieudera.nhomthuongId,     " +
					"case when ( thucdat.loainhom = 'Nhom Chinh' ) and ( ( thucdat.sltt * 100 / chitieudera.chitieu )  < bhnhomthuong.mucbaohiem ) then bhnhomthuong.mucbaohiem     " +
					"else ( thucdat.sltt * 100 / chitieudera.chitieu ) end  as PT_Dat,     " +
					"case thucdat.loainhom when 'Nhom Chinh' then 'CT01' else 'CT02' end as ma     " +
				"from  " +
				"(   " +
					"select gsbh.KHUVUC_FK as kvId, c.gsbh_fk as gsbhId, nt.pk_seq as nhomthuongId, sum(d.chitieu) as Chitieu    " +
					"from chitieunpp a inner join  Chitieunpp_ddkd b on a.pk_seq = b.chitieunpp_fk     " +
						"inner join ddkd_gsbh c on b.ddkd_fk = c.ddkd_fk    " +
						"inner join ChitieuNPP_DDKD_NHOMSP d on d.ddkd_fk = b.ddkd_fk     " +
						"inner join NHOMSANPHAM NSP on NSP.pk_seq = d.nhomsanpham_fk     " +
						"inner join Nhomthuong_NhomSP nt_nsp on nt_nsp.NhomSanPham_fk = NSP.pk_seq    " +
						"inner join Nhomthuong nt on nt_nsp.nhomthuong_fk = nt.pk_seq     " +
						"inner join GIAMSATBANHANG gsbh on c.GSBH_FK = gsbh.PK_SEQ  " +
					"where a.thang = '" + obj.getTungay() + "' and a.nam = '" + obj.getDenngay() + "' and a.trangthai = '1' and nt.trangthai = '1' and NSP.loainhom = '3'    " +
					"group by gsbh.KHUVUC_FK, c.gsbh_fk, nt.pk_seq   " +
				")    " +
				"chitieudera inner join    " +
				"(    " +
					"select gsbh.khuvuc_fk as kvId, dh.GSBH_FK as gsbhId, nt.pk_seq as nhomthuongId, case when nt.loai = 1 then 'Nhom Chinh' else 'Nhom Con Lai' end as LoaiNhom,      " +
						"sum( a.soluong * isnull(sp.trongluong, 0) ) as SLTT       " +
					"from donhang_sanpham a inner join nhomsanpham_sanpham b on a.sanpham_fk = b.sp_fk     " +
						"inner join NHOMSANPHAM NSP on NSP.pk_seq = b.nsp_fk      " +
						"inner join Nhomthuong_NhomSP nt_nsp on nt_nsp.NhomSanPham_fk = NSP.pk_seq      " +
						"inner join Nhomthuong nt on nt_nsp.nhomthuong_fk = nt.pk_seq      " +
						"inner join TrongSoKPI_ChiTiet e on e.nhomthuong_fk = nt.pk_seq      " +
						"inner join TrongSoKPI ts on e.trongsokpi_fk = ts.pk_seq      " +
						"inner join sanpham sp on a.sanpham_fk = sp.pk_seq       " +
						"inner join DONHANG dh on a.DONHANG_FK = dh.PK_SEQ  " +
						"inner join GIAMSATBANHANG gsbh on dh.GSBH_FK = gsbh.PK_SEQ   " +
					"where dh.ngaynhap like '%" + ngaydh + "%' and dh.trangthai = '1'      " +
						"and e.nhomthuong_fk is not null and ts.chucvu = 'SS'  and nt.trangthai = '1' and NSP.loainhom = '3'      " +
						"and ts.tinhthunhap_fk in ( select pk_seq from TinhThuNhap    where trangthai = '1' and thang = '" + obj.getTungay() + "' and nam = '" + obj.getDenngay() + "'   )       " +
					"group by  gsbh.khuvuc_fk, dh.GSBH_FK, dh.NPP_FK, nt.pk_seq, nt.loai     " +
				")    " +
				"thucdat on chitieudera.nhomthuongId = thucdat.nhomthuongId and chitieudera.gsbhId = thucdat.gsbhId  and chitieudera.kvId = thucdat.kvId  " +
				"left join    " +
				"(    " +
					"select b.khuvuc_fk as kvId, isnull(a.nhomthuong_fk, -1) as nhomthuongId, a.mucbaohiem    " +
					"from TrongSoKPI_ChiTiet a inner join TrongSoKPI b on a.trongsoKPI_fk = b.pk_seq     " +
					"where b.chucvu = 'SS' and  b.tinhthunhap_fk in ( select pk_seq from TinhThuNhap where thang = '" + obj.getTungay() + "' and nam = '" + obj.getDenngay() + "' and trangthai = '1' )    " +
				")   " +
				"bhnhomthuong on chitieudera.nhomthuongId = bhnhomthuong.nhomthuongId  and chitieudera.kvId = bhnhomthuong.kvId  " +
			"union all    " +
				"select chitieuSDH.kvId, chitieuSDH.gsbhId, -1 as nhomthuongId,    " +
					"case when (	( cast( thucdatSDH.sodonhang as float) / chitieuSDH.CT_Donhang) * 100 ) < mucbaohiem then mucbaohiem    " +
					"else  (	( cast( thucdatSDH.sodonhang as float) / chitieuSDH.CT_Donhang) * 100 ) end as PT_DonHang, 'CT03' as ma    " +
				"from    " +
				"(   " +
					"select gsbh.KHUVUC_FK as kvId, c.gsbh_fk as gsbhId, -1 as nhomthuongId, SUM(distinct a.SONGAYLAMVIEC) * sum(isnull(b.sodonhang, 0)) as CT_Donhang     " +
					"from chitieunpp a inner join  Chitieunpp_ddkd b on a.pk_seq = b.chitieunpp_fk    " +
						"inner join ddkd_gsbh c on b.ddkd_fk = c.ddkd_fk   " +
						"inner join GIAMSATBANHANG gsbh on c.GSBH_FK = gsbh.PK_SEQ  " +
					"where a.thang = '" + obj.getTungay() + "' and a.nam = '" + obj.getDenngay() + "' and a.trangthai = '1'    " +
					"group by gsbh.KHUVUC_FK, c.gsbh_fk   " +
				")   " +
				"chitieuSDH inner join    " +
				"(    " +
					"select gsbh.KHUVUC_FK as kvId, a.gsbh_fk as gsbhId, count(distinct a.pk_seq) as sodonhang    " +
					"from donhang a inner join GIAMSATBANHANG gsbh on a.GSBH_FK = gsbh.PK_SEQ  " +
					"where ngaynhap like '%2012-09%' and a.trangthai = '1'    " +
					"group by gsbh.KHUVUC_FK, gsbh_fk   " +
				")   " +
				"thucdatSDH on chitieuSDH.gsbhId = thucdatSDH.gsbhId  and chitieuSDH.gsbhId = thucdatSDH.gsbhId  " +
				"left join    " +
				"(   " +
					"select b.khuvuc_fk as kvId, isnull(a.nhomthuong_fk, -1) as nhomthuongId, a.mucbaohiem    " +
					"from TrongSoKPI_ChiTiet a inner join TrongSoKPI b on a.trongsoKPI_fk = b.pk_seq    " +
					"where b.chucvu = 'SS' and a.ma = 'CT03'    " +
						"and b.tinhthunhap_fk in ( select pk_seq from TinhThuNhap where thang = '" + obj.getTungay() + "' and nam = '" + obj.getDenngay() + "' and trangthai = '1' )    " +
				")   " +
				"bhdonhang on chitieuSDH.nhomthuongId = bhdonhang.nhomthuongId  and chitieuSDH.kvId = bhdonhang.kvId   " +
			"union all    " +
				"select thucdatquytrinh.kvId, thucdatquytrinh.gsbhId, thucdatquytrinh.nhomthuongId,    " +
					"case when thucdatquytrinh.PT_QuyTrinh < isnull(bhquytrinh.mucbaohiem, 0) then isnull(bhquytrinh.mucbaohiem, 0)    " +
					"else thucdatquytrinh.PT_QuyTrinh end as PT_QuyTrinh, thucdatquytrinh.ma    " +
				"from    " +
				"(    " +
					"select gsbh.KHUVUC_FK as kvId, b.gsbh_fk as gsbhId, -1 as nhomthuongId, sum( isnull(b.chamlan1, 0) * a.trongso / 100 ) * 100 / 5 as PT_QuyTrinh, 'CT04' as ma    " +
					"from tieuchidanhgia_tieuchi a inner join tieuchidanhgia_tieuchi_gsbh b on a.pk_seq = b.tieuchidanhgia_tieuchi_fk   " +
						"inner join GIAMSATBANHANG gsbh on b.gsbh_fk = gsbh.PK_SEQ  " +
					"where a.tieuchidanhgia_fk in ( select pk_seq from tieuchidanhgia where thang = '" + obj.getTungay() + "' and nam = '" + obj.getDenngay() + "' and trangthai = '1' )    " +
					"group by gsbh.KHUVUC_FK, b.gsbh_fk    " +
				")   " +
				"thucdatquytrinh  left join    " +
				"(   " +
					"select isnull(a.nhomthuong_fk, -1) as nhomthuongId, a.mucbaohiem   " +
					"from TrongSoKPI_ChiTiet a inner join TrongSoKPI b on a.trongsoKPI_fk = b.pk_seq    " +
					"where b.chucvu = 'SS' and a.ma = 'CT04'    " +
					"and b.tinhthunhap_fk in ( select pk_seq from TinhThuNhap where thang = '" + obj.getTungay() + "' and nam = '" + obj.getDenngay() + "' and trangthai = '1' )    " +
				")   " +
				"bhquytrinh on thucdatquytrinh.nhomthuongId = bhquytrinh.nhomthuongId   " +
			")    " +
			"KPI inner join    " +
			"(	   " +
				"select b.khuvuc_fk, isnull(a.nhomthuong_fk, -1) as nhomthuongId, a.trongso, a.ma,    " +
					"a.thuongvuotmuc, b.luongcoban, b.ptluongtg, b.ptluonghieuqua, b.baohiemden as mucbaohiem, b.baohiem as BHXH, b.congdoan, b.tdnc     " +
				"from TrongSoKPI_ChiTiet a inner join TrongSoKPI b on a.trongsoKPI_fk = b.pk_seq     " +
				"where b.chucvu = 'SS' and  b.tinhthunhap_fk in ( select pk_seq from TinhThuNhap where thang = '" + obj.getTungay() + "' and nam = '" + obj.getDenngay() + "' and trangthai = '1' )     " +
			")    " +
			"TrongSoKPI on KPI.ma  = TrongSoKPI.ma and KPI.nhomthuongId = TrongSoKPI.nhomthuongId  and KPI.kvId = TrongSoKPI.khuvuc_fk  " +
				"inner join giamsatbanhang gsbh on KPI.gsbhId = gsbh.pk_seq    " +
				"inner join khuvuc kv on gsbh.khuvuc_fk = kv.pk_seq    " +
				"inner join vung v on kv.vung_fk = v.pk_seq   " +
			"group by v.pk_seq , v.ten, kv.pk_seq, kv.ten, gsbhId, gsbh.ten, gsbh.dienthoai, gsbh.tinhtrang " +
		") " +
		"thongtinluong left join  " +
		"( " +
			"select chitieu.kvId, chitieu.gsbhId, chitieu.nlvChitieu, thucdatdonhang.nlvThucte    " +
			"from  " +
			"(   " +
				"select  gsbh.KHUVUC_FK as kvId, c.gsbh_fk as gsbhId, max(a.songaylamviec) as nlvChitieu    " +
				"from chitieunpp a inner join  Chitieunpp_ddkd b on a.pk_seq = b.chitieunpp_fk    " +
					"inner join ddkd_gsbh c on b.ddkd_fk = c.ddkd_fk  " +
					"inner join GIAMSATBANHANG gsbh on c.GSBH_FK = gsbh.PK_SEQ    " +
				"where a.thang = '" + obj.getTungay() + "' and a.nam = '" + obj.getDenngay() + "' and a.trangthai = '1'    " +
				"group by gsbh.KHUVUC_FK, c.gsbh_fk    " +
			")    " +
			"chitieu left join    " +
			"(    " +
				"select kvId, gsbhId, max(isnull(nlvThucte, 0)) as nlvThucte   " +
				"from    " +
				"(   " +
					"select gsbh.KHUVUC_FK as kvId,  a.gsbh_fk as gsbhId, count(distinct ngaynhap)  as nlvThucte   " +
					"from donhang a inner join GIAMSATBANHANG gsbh on a.GSBH_FK = gsbh.PK_SEQ  " +
					"where ngaynhap like '%" + ngaydh + "%' and a.TRANGTHAI = '1'    " +
					"group by gsbh.KHUVUC_FK, gsbh_fk " +
				")   " +
				"gsbh group by kvId, gsbhId   " +
			")     " +
			"thucdatdonhang on chitieu.gsbhId = thucdatdonhang.gsbhId   and chitieu.kvId = thucdatdonhang.kvId " +
		") " +
		"chitieunlv on thongtinluong.gsbhId = chitieunlv.gsbhId and thongtinluong.kvId = chitieunlv.kvId   " +
		"cross join " +
		"( " +
			"select SUM(c.trongso) as tienphucap  " +
			"from TinhPhuCap a inner join TinhPhuCap_ChucVu b on a.pk_seq = b.TinhPhuCap_FK  " +
				"inner join TinhPhuCap_ChucVu_PhuCap c on b.PK_SEQ = c.tinhphucap_chucvu_fk " +
			"where a.trangthai = '1' and  a.thang = '" + obj.getTungay() + "' and a.nam = '" + obj.getDenngay() + "' and b.chucvu = 'SS' " +
		") " +
		"tongphucap  ";
		
		System.out.println("_Luong GSBH: " + query);
		
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					String gsbhId = rs.getString("gsbhId");
					
					double nlvChitieu = rs.getDouble("nlvChitieu");
					double nlvThucte = rs.getDouble("nlvThucte");
					
					double luongcb = rs.getDouble("luongcb");
					double ptluongtg = rs.getDouble("ptluongtg");
					double ptluonghieuqua = rs.getDouble("ptluonghieuqua");
					
					double luongtg = 0;
					double ptKpi = 0;
					double luonghq = 0;
					double thunhap = 0;
					double phucap = 0;
					
					double bhxh = 0;
					double congdoan = 0;
					
					phucap = rs.getDouble("TienPhuCap");
					if(phucap > 0)
						phucap = phucap * (nlvThucte / nlvChitieu);
					
					bhxh = (rs.getDouble("BHXH") / 100 ) * luongcb;
					
					String tinhtrang = rs.getString("tinhtrang");
					if(tinhtrang.equals("0")) //Nhan vien chinh thuc
					{
						luongtg = luongcb * (nlvThucte / nlvChitieu) * (ptluongtg / 100);
						
						ptKpi = Math.round(rs.getDouble("PT_KPI") * 100) / 100.0;
						
						double tdnc = rs.getDouble("tdnc");
						if(nlvThucte < tdnc)
						{
							luonghq = ( luongcb * (ptluonghieuqua / 100) ) * (ptKpi / 100) * (nlvThucte / nlvChitieu);
						}
						else
						{
							luonghq = ( luongcb * (ptluonghieuqua / 100) ) * (ptKpi / 100);
						}
						thunhap = luongtg + luonghq + phucap;
						
						congdoan = (rs.getDouble("congdoan") / 100 ) * thunhap;
						double chiphi = bhxh + congdoan;
						
						thunhap = thunhap - chiphi;
						
					}
					else
					{
						luongtg = luongcb * (ptluongtg / 100) * (nlvThucte / nlvChitieu);

						thunhap = ( (luongtg * 90) / 100 ) + phucap;
						
						congdoan = (rs.getDouble("congdoan") / 100 ) * thunhap;
						double chiphi = bhxh + congdoan;
						
						thunhap = thunhap - chiphi;
					}
					
					ResultSet rsSonpp = db.get("select count(NPP_FK) as soNPP from NHAPP_GIAMSATBH where GSBH_FK = '" + gsbhId + "' and NGAYBATDAU <= '" + cuoithang + "' and NGAYKETTHUC >= '" + dauthang + "'");
					if(rsSonpp != null)
					{
						if(rsSonpp.next())
						{
							int soNPP = rsSonpp.getInt("soNPP");
							if(soNPP > 0)
							{
								ResultSet rsNpp = db.get("select NPP_FK from NHAPP_GIAMSATBH where GSBH_FK = '" + gsbhId + "' and NGAYBATDAU <= '" + cuoithang + "' and NGAYKETTHUC >= '" + dauthang + "'");
								if(rsNpp != null)
								{
									while(rsNpp.next())
									{
										String nppId = rsNpp.getString("NPP_FK");
										
										luongGSBH.put(nppId, thunhap / soNPP);
									}
									rsNpp.close();
								}
							}
						}
					}
					rsSonpp.close();
				}
				rs.close();
			} 
			catch (Exception e) {}
		}
		
		return luongGSBH;
	}
	
	
	
	private Hashtable<String, Double> getLuongDDKD(IKhoahuanluyenList obj, dbutils db, Hashtable<String, Integer> soDDKD, String thang)
	{
		Hashtable<String, Double> luongDDKD = new Hashtable<String, Double>();
		
		String ngaydh = obj.getDenngay() + "-" + thang;
		
		String query = "select thongtinluong.*, chitieunlv.nlvChitieu, chitieunlv.nlvThucte, ISNULL(tongphucap.tienphucap, 0) as TienPhuCap  " +
				"from " +
				"(" +
				"select v.pk_seq as vungId, v.ten as vungTen, kv.ten as kvTen, npp.pk_seq as nppId, npp.ten as nppTen, ddkd.ten, ddkd.dienthoai, KPI.kvId, KPI.ddkdId,     " +
							"sum(KPI.thuongSLtieuthu) as thuongSLtieuthu, 	   " +
							"case when  sum ( KPI.PT_Dat * TrongSoKPI.trongso / 100 ) < avg(TrongSoKPI.mucbaohiem) then avg(TrongSoKPI.mucbaohiem)    " +
							"else sum ( KPI.PT_Dat * TrongSoKPI.trongso / 100 ) end as PT_KPI,   " +
							"avg(luongcoban) as luongcb, avg(ptluongtg) as ptluongtg, avg(ptluonghieuqua) as ptluonghieuqua,  " +
							"avg(baohiem) as bhxh, avg(congdoan) as congdoan, avg(tdnc) as tdnc    " +
				"from  " +
				"(   " +
					"select  chitieudera.kvId, chitieudera.ddkdId, chitieudera.nhomthuongId,    " +
						"case when (thucdat.loainhom = 'Nhom Chinh') and ( ( thucdat.sltt * 100 / chitieudera.chitieu ) >= 100 )    " +
						"then thuongvuotmuc.thuong * thucdat.sltt  else 0 end as thuongSLtieuthu,   " +
						"case when (  ( thucdat.sltt / chitieudera.chitieu ) * 100 ) < thuongvuotmuc.mucbaohiem then thuongvuotmuc.mucbaohiem    " +
						"else ( thucdat.sltt / chitieudera.chitieu ) * 100 end as PT_Dat,   " +
						"case thucdat.loainhom when 'Nhom Chinh' then 'CT01' else 'CT02' end as ma    " +
					"from   " +
					"(   " +
						"select c.khuvuc_fk as kvId, b.ddkd_fk as ddkdId, nt.pk_seq as nhomthuongId, sum(d.chitieu) as Chitieu   " +
						"from chitieunpp a inner join  Chitieunpp_ddkd b on a.pk_seq = b.chitieunpp_fk   " +
								"inner join NhaPhanPhoi c on a.nhapp_fk = c.pk_seq    " +
								"inner join ChitieuNPP_DDKD_NHOMSP d on d.ddkd_fk = b.ddkd_fk and d.chitieunpp_fk = a.pk_seq    " +
							"inner join NHOMSANPHAM NSP on NSP.pk_seq = d.nhomsanpham_fk    " +
							"inner join Nhomthuong_NhomSP nt_nsp on nt_nsp.NhomSanPham_fk = NSP.pk_seq    " +
							"inner join Nhomthuong nt on nt_nsp.nhomthuong_fk = nt.pk_seq    " +
						"where a.thang = '" + obj.getTungay() + "' and a.nam = '" + obj.getDenngay() + "' and a.trangthai = '1'  and nt.trangthai = '1' and NSP.loainhom = '3'    " +
						"group by c.khuvuc_fk, b.ddkd_fk, nt.pk_seq   " +
					")   " +
					"chitieudera inner join   " +
					"(   " +
						"select npp.khuvuc_fk as kvId, dh.ddkd_fk as ddkdId, nt.pk_seq as nhomthuongId,    " +
							"case when nt.loai = 1 then 'Nhom Chinh' else 'Nhom Con Lai' end as LoaiNhom, sum( a.soluong * isnull(sp.trongluong, 0) ) as SLTT      " +
						"from   donhang_sanpham a inner join nhomsanpham_sanpham b on a.sanpham_fk = b.sp_fk    " +
							"inner join nhomthuong_nhomsp c on b.nsp_fk = c.nhomsanpham_fk    " +
							"inner join NHOMSANPHAM NSP on NSP.pk_seq = b.nsp_fk   " +
							"inner join Nhomthuong nt on c.nhomthuong_fk = nt.pk_seq    " +
							"inner join TrongSoKPI_ChiTiet e on e.nhomthuong_fk = nt.pk_seq    " +
							"inner join TrongSoKPI ts on e.trongsokpi_fk = ts.pk_seq    " +
							"inner join donhang dh on a.donhang_fk = dh.pk_seq    " +
							"inner join nhaphanphoi npp on dh.npp_fk = npp.pk_seq and ts.khuvuc_fk = npp.khuvuc_fk     " +
							"inner join sanpham sp on a.sanpham_fk = sp.pk_seq    " +
						"where dh.ngaynhap like '" + ngaydh + "%' and dh.trangthai = '1' and nt.trangthai = '1' and NSP.loainhom = '3'    " +
							"and e.nhomthuong_fk is not null  and ts.chucvu = 'SR'    " +
							"and ts.tinhthunhap_fk in ( select pk_seq from TinhThuNhap    where trangthai = '1' and thang = '" + obj.getTungay() + "' and nam = '" + obj.getDenngay() + "'   )     " +
						"group by npp.khuvuc_fk, dh.ddkd_fk, nt.pk_seq, nt.loai   " +
					") " +
					"thucdat on chitieudera.nhomthuongId = thucdat.nhomthuongId and chitieudera.kvId = thucdat.kvId and chitieudera.ddkdId = thucdat.ddkdId     " +
					"left join   " +
					"(   " +
						"select b.khuvuc_fk as kvId, isnull(a.nhomthuong_fk, -1) as nhomthuongId, a.thuongvuotmuc as thuong, a.mucbaohiem    " +
						"from TrongSoKPI_ChiTiet a inner join TrongSoKPI b on a.trongsoKPI_fk = b.pk_seq    " +
						"where b.chucvu = 'SR' and  b.tinhthunhap_fk in ( select pk_seq from TinhThuNhap where thang = '" + obj.getTungay() + "' and nam = '" + obj.getDenngay() + "' and trangthai = '1' )    " +
					")   " +
					"thuongvuotmuc on chitieudera.kvId = thuongvuotmuc.kvId and chitieudera.nhomthuongId = thuongvuotmuc.nhomthuongId    " +
			"union all    " +
				"select chitieuSDH.kvId, chitieuSDH.ddkdId, -1 as nhomthuongId, 0 as thuongSLTieuthu,   " +
					"case when ( ( cast( thucdatSDH.sodonhang as float) / chitieuSDH.CT_Donhang) * 100 ) < mucbaohiem then mucbaohiem    " +
					"else ( ( cast( thucdatSDH.sodonhang as float) / chitieuSDH.CT_Donhang) * 100 ) end as PT_DonHang, 'CT03' as ma    " +
				"from   " +
				"(   " +
					"select c.khuvuc_fk as kvId, b.ddkd_fk as ddkdId, a.SONGAYLAMVIEC * b.sodonhang as CT_Donhang    " +
					"from chitieunpp a inner join  Chitieunpp_ddkd b on a.pk_seq = b.chitieunpp_fk   inner join NhaPhanPhoi c on a.nhapp_fk = c.pk_seq    " +
					"where a.thang = '" + obj.getTungay() + "' and a.nam = '" + obj.getDenngay() + "' and a.trangthai = '1'   " +
				")   " +
				"chitieuSDH inner join    " +
				"(   " +
					"select b.khuvuc_fk as kvId, a.ddkd_fk as ddkdId, count(distinct a.pk_seq) as sodonhang    " +
					"from donhang a inner join nhaphanphoi b on a.npp_fk = b.pk_seq    " +
					"where a.ngaynhap like '" + ngaydh + "%' and a.trangthai = '1'    " +
					"group by b.khuvuc_fk, a.ddkd_fk    " +
				")   " +
				"thucdatSDH on chitieuSDH.kvId = thucdatSDH.kvId and chitieuSDH.ddkdId = thucdatSDH.ddkdId   " +
				"left join   " +
				"(  " +
					"select b.khuvuc_fk as kvId, isnull(a.nhomthuong_fk, -1) as nhomthuongId, a.thuongvuotmuc as thuong, a.mucbaohiem, a.ma    " +
					"from TrongSoKPI_ChiTiet a inner join TrongSoKPI b on a.trongsoKPI_fk = b.pk_seq    " +
					"where b.chucvu = 'SR' and a.ma = 'CT03' and    " +
						"b.tinhthunhap_fk in ( select pk_seq from TinhThuNhap where thang = '" + obj.getTungay() + "' and nam = '" + obj.getDenngay() + "' and trangthai = '1' )    " +
				")   " +
				"thuongvuotmuc on chitieuSDH.kvId = thuongvuotmuc.kvId  " +
			")  " +
			"KPI inner join    " +
			"(   " +
				"select b.khuvuc_fk as kvId, isnull(a.nhomthuong_fk, -1) as nhomthuongId, a.trongso, a.ma,    " +
					"a.thuongvuotmuc, b.luongcoban, b.ptluongtg, b.ptluonghieuqua, a.mucbaohiem, b.baohiem, b.congdoan, b.tdnc    " +
				"from TrongSoKPI_ChiTiet a inner join TrongSoKPI b on a.trongsoKPI_fk = b.pk_seq    " +
				"where b.chucvu = 'SR' and  b.tinhthunhap_fk in ( select pk_seq from TinhThuNhap where thang = '" + obj.getTungay() + "' and nam = '" + obj.getDenngay() + "' and trangthai = '1' )    " +
			")    " +
			"TrongSoKPI on KPI.ma = TrongSoKPI.ma and KPI.nhomthuongId = TrongSoKPI.nhomthuongId and KPI.kvId = TrongSoKPI.kvId   " +
			"inner join daidienkinhdoanh ddkd on KPI.ddkdId = ddkd.pk_seq   " +
			"inner join nhaphanphoi npp on ddkd.npp_fk = npp.pk_seq   " +
			"inner join khuvuc kv on KPI.kvId = kv.pk_seq   " +
			"inner join vung v on kv.vung_fk = v.pk_seq   " +
			"group by v.pk_seq, v.ten, kv.ten, npp.pk_seq, npp.ten, ddkd.ten, ddkd.dienthoai, KPI.kvId, KPI.ddkdId " +
			")  " + 
		"thongtinluong left join " +
			"(  " +
				"select chitieu.kvId, chitieu.ddkdId, chitieu.nlvChitieu, thucdatdonhang.nlvThucte " +
				"from " +
				"(  " +
					"select c.khuvuc_fk as kvId, b.ddkd_fk as ddkdId, a.songaylamviec as nlvChitieu  " +
					"from chitieunpp a inner join  Chitieunpp_ddkd b on a.pk_seq = b.chitieunpp_fk  " +
						"inner join NhaPhanPhoi c on a.nhapp_fk = c.pk_seq  " +
					"where a.thang = '" + obj.getTungay() + "' and a.nam = '" + obj.getDenngay() + "' and a.trangthai = '1'  " +
					"group by c.khuvuc_fk, b.ddkd_fk, a.songaylamviec " +
				")  " +
				"chitieu left join  " +
				"( " +
					"select b.khuvuc_fk as kvId, a.ddkd_fk as ddkdId, count(distinct a.ngaynhap) as nlvThucte  " +
					"from donhang a inner join nhaphanphoi b on a.npp_fk = b.pk_seq   " +
					"where a.ngaynhap like '" + ngaydh + "%' and a.trangthai = '1'  " +
					"group by b.khuvuc_fk, a.ddkd_fk " +
				")  " +
				"thucdatdonhang on chitieu.ddkdId = thucdatdonhang.ddkdId and chitieu.kvId = thucdatdonhang.kvId " +
			") " +
		"chitieunlv on thongtinluong.kvId = chitieunlv.kvId and thongtinluong.ddkdId = chitieunlv.ddkdId  " + 
		"cross join  " +
		"( " +
			"select SUM(c.trongso) as tienphucap  " +
			"from TinhPhuCap a inner join TinhPhuCap_ChucVu b on a.pk_seq = b.TinhPhuCap_FK   " +
					"inner join TinhPhuCap_ChucVu_PhuCap c on b.PK_SEQ = c.tinhphucap_chucvu_fk  " +
			"where a.trangthai = '1' and  a.thang = '" + obj.getTungay() + "' and a.nam = '" + obj.getDenngay() + "' and b.chucvu = 'SR' " +
		") tongphucap ";
		
		System.out.println("1___Lay luong ddkd: " + query);
		
		ResultSet rsDDKD = db.get(query);
		
		if(rsDDKD != null)
		{
			try 
			{
				int count = 0;
				String maNPP = "";
				
				while(rsDDKD.next())
				{
					String nppId = rsDDKD.getString("nppId");
					if( ! maNPP.equals(nppId))
					{
						maNPP = rsDDKD.getString("nppId");
						count = 0;
					}
					
					double nlvChitieu = rsDDKD.getDouble("nlvChitieu");
					double nlvThucte = rsDDKD.getDouble("nlvThucte");
					
					double luongcb = rsDDKD.getDouble("luongcb");
					double ptluongtg = rsDDKD.getDouble("ptluongtg");
					double ptluonghieuqua = rsDDKD.getDouble("ptluonghieuqua");
					
					double luongtg = luongcb * (nlvThucte / nlvChitieu) * (ptluongtg / 100);

					double ptKpi = Math.round(rsDDKD.getDouble("PT_KPI") * 100) / 100.0;
					
					double luonghq = 0;
					
					
					double tdnc = rsDDKD.getDouble("tdnc");
					
					/*****************************************/
					if(nlvThucte < tdnc)
					{
						luonghq = ( luongcb * (ptluonghieuqua / 100) ) * (ptKpi / 100) * (nlvThucte / nlvChitieu);
					}
					else
						luonghq = ( luongcb * (ptluonghieuqua / 100) ) * (ptKpi / 100);
					/*****************************************/
					
					
					double thuongSLTieuthu = rsDDKD.getDouble("thuongSLTieuthu");
					
					double phucap = rsDDKD.getDouble("TienPhuCap");
					if(phucap > 0)
						phucap = phucap * (nlvThucte / nlvChitieu);
					
					double bhxh = (rsDDKD.getDouble("BHXH") / 100 ) * luongcb;
					double congdoan = (rsDDKD.getDouble("congdoan") / 100 ) * (luongtg + luonghq + thuongSLTieuthu + phucap);
					double chiphi = bhxh + congdoan;
					
					double thunhap = luongtg + luonghq + thuongSLTieuthu + phucap - chiphi;
					
					count++;
					soDDKD.put(nppId, count);
					
					if(luongDDKD.containsKey(nppId))
					{
						double luongDD = luongDDKD.get(nppId) + thunhap;
						luongDDKD.put(nppId, luongDD);
					}
					else
					{
						luongDDKD.put(nppId, thunhap);
					}
					
					
				}
				rsDDKD.close();
			} 
			catch (Exception e) 
			{
				System.out.println("115.Exception: " + e.getMessage());
			}
			
		}
		
		return luongDDKD;
	}

	
	private String LastDayOfMonth(int month, int year) 
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

        return ngay;
    } 
	
	public static void main(String[] arg)
	{
		BCChiPhi bc = new  BCChiPhi();
		
		System.out.println("Bat dau chay...");
		
		Hashtable<String, Integer> soDDKD = new Hashtable<String, Integer>();
		Hashtable<String, Double> lgDDkd = bc.getLuongDDKD(null, new dbutils(), soDDKD, "9");
		
		Enumeration<String> keys = lgDDkd.keys();
		while(keys.hasMoreElements())
		{
			String key = keys.nextElement();
			System.out.println("__DDKD Key: " + key + " -- values: " + lgDDkd.get(key) + " - So DDKD: " + soDDKD.get(key));
		}
		
		Hashtable<String, Double> lgGSBH = bc.getLuongGiamSat(null, new dbutils(), "9");
		
		Enumeration<String> keyss = lgGSBH.keys();
		while(keyss.hasMoreElements())
		{
			String key = keyss.nextElement();
			System.out.println("__GSBH Key: " + key + " -- values: " + lgGSBH.get(key));
		}
	}	
	
}
