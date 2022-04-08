package geso.dms.center.servlets.reports;

import geso.dms.center.beans.khoahuanluyen.IKhoahuanluyenList;
import geso.dms.center.beans.khoahuanluyen.imp.KhoahuanluyenList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.distributor.util.Utility;

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


import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
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
public class BCLuongThuong extends HttpServlet
{
	private static final long serialVersionUID = 1L;

    public BCLuongThuong() {
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
		String nextJSP = "/AHF/pages/Center/BCLuongThuong.jsp";
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
		
		String dinhdang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dinhdang"));
		if(dinhdang == null)
			dinhdang = "0";
		
		obj.setKbhId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId"))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")):""));
		
		obj.setVungId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")):""));
			
		obj.setKvId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")):""));
		
		obj.setNppId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")):""));
		
		String muclay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("muclay"));
		if(muclay == null)
			muclay = "0";

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action") != null? util.antiSQLInspection(request.getParameter("action"))) : "";
		String nextJSP = "/AHF/pages/Center/BCLuongThuong.jsp";
		
		if(action.equals("Taomoi"))
		{
			try
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=LuongNhanVien.xlsm");
		        if(!CreatePivotTable(out, obj, muclay, dinhdang))
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
	
	private boolean CreatePivotTable(OutputStream out, IKhoahuanluyenList obj, String muclay, String dinhdang) throws Exception 
	{
		String chuoi = "";
		if(muclay.equals("0"))
		{
			if(dinhdang.equals("0"))
			{
				chuoi = getServletContext().getInitParameter("path") + "\\LuongThuong.xlsm";
			}
			else if(dinhdang.equals("1"))
			{
				chuoi = getServletContext().getInitParameter("path") + "\\LuongThuong_In.xlsm";
			}else
			{
				chuoi=getServletContext().getInitParameter("path") + "\\LuongThuong_ToTrinh.xlsm";
			}
		}
		else 
		{
			if(dinhdang.equals("0"))
			{
				chuoi = getServletContext().getInitParameter("path") + "\\LuongThuong_GSBH.xlsm";
			}
			else if(dinhdang.equals("1"))
			{
				chuoi = getServletContext().getInitParameter("path") + "\\LuongThuong_GSBH_In.xlsm";
			}else 
			{
				chuoi=getServletContext().getInitParameter("path") + "\\LuongThuong_ToTrinh.xlsm";
			}
		}
		
		FileInputStream fstream = new FileInputStream(chuoi);
			
		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		
		if(!dinhdang.equals("2"))
			CreateStaticHeader(workbook, obj, muclay, dinhdang);
		
		if(dinhdang.equals("2")&&muclay.equals("1"))
			CreateStaticHeader_Npp(workbook, obj, muclay, "GiamSatBanHang", 0);
		
		boolean isFill = false;
		if(dinhdang.equals("0"))
		{
			isFill = CreateStaticData(workbook, obj, muclay);
		}
		else if(dinhdang.equals("1"))
		{
			isFill = CreateStaticData_In_BangLuong(workbook, obj, muclay);
		}else  
		{
			isFill = CreateStaticData_ToTrinh(workbook, obj, muclay);
		}
		if (!isFill){
			fstream.close();
			return false;
		}else {
			workbook.save(out);
			fstream.close();
			return true;
		}
	}
	
	
	private void CreateStaticHeader(Workbook workbook, IKhoahuanluyenList obj, String muclay, String dinhdang) throws Exception 
	{
		try
		{	
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");

			Cells cells = worksheet.getCells();
			Cell cell = cells.getCell("A1");
			
			if(dinhdang.equals("0"))
			{
				if(muclay.equals("0"))
				{
					cell.setValue("BÁO CÁO TÍNH LƯƠNG CỦA NHÂN VIÊN BÁN HÀNG");   	
				}
				else
				{
					cell.setValue("BÁO CÁO TÍNH LƯƠNG CỦA GIÁM SÁT BÁN HÀNG"); 
				}
			
			    cell = cells.getCell("A2");
			    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
			    cell = cells.getCell("A3");
			    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getUserTen());
			}
		   
		    if(dinhdang.equals("0"))
		    {
			    if(muclay.equals("0"))
			    {
			    	cell = cells.getCell("DA1"); 	cell.setValue("Vung");  ReportAPI.setCellHeader(cell);
					cell = cells.getCell("DB1"); 	cell.setValue("KhuVuc");  ReportAPI.setCellHeader(cell);
					cell = cells.getCell("DC1"); 	cell.setValue("NhaPhanPhoi");  ReportAPI.setCellHeader(cell);
			    	cell = cells.getCell("DD1"); 	cell.setValue("DaiDienKinhDoanh");  ReportAPI.setCellHeader(cell);
			    	cell = cells.getCell("DE1"); 	cell.setValue("DienThoai");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("DF1"); 	cell.setValue("LuongCoBan");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("DG1"); 	cell.setValue("CTNgayLamViec");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("DH1"); 	cell.setValue("TDNgayLamViec");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("DI1"); 	cell.setValue("CTNhomChinh");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("DJ1"); 	cell.setValue("TDNhomChinh");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("DK1"); 	cell.setValue("%DatNhomChinh");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("DL1"); 	cell.setValue("CTSoDonHang");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("DM1"); 	cell.setValue("TDSoDonHang");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("DN1"); 	cell.setValue("%SoDonHang");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("DO1"); 	cell.setValue("CTNhomConLai");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("DP1"); 	cell.setValue("TDNhomConLai");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("DQ1"); 	cell.setValue("%DatNhomConLai");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("DR1"); 	cell.setValue("%LuongThoiGian");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("DS1"); 	cell.setValue("LuongThoiGian");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("DT1"); 	cell.setValue("%KPI");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("DU1"); 	cell.setValue("%LuongHieuQua");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("DV1"); 	cell.setValue("LuongHieuQua");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("DW1"); 	cell.setValue("ThuongSLTieuThu");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("DX1"); 	cell.setValue("PhuCap");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("DY1"); 	cell.setValue("BaoHiem");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("DZ1"); 	cell.setValue("CongDoan");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("EA1"); 	cell.setValue("ThuNhap");  ReportAPI.setCellHeader(cell);
			    }
			    else
			    {
			    	cell = cells.getCell("DA1"); 	cell.setValue("Vung");  ReportAPI.setCellHeader(cell);
					cell = cells.getCell("DB1"); 	cell.setValue("KhuVuc");  ReportAPI.setCellHeader(cell);
			    	cell = cells.getCell("DC1"); 	cell.setValue("GiamSatBanHang");  ReportAPI.setCellHeader(cell);
			    	cell = cells.getCell("DD1"); 	cell.setValue("DienThoai");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("DE1"); 	cell.setValue("LuongCoBan");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("DF1"); 	cell.setValue("CTNgayLamViec");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("DG1"); 	cell.setValue("TDNgayLamViec");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("DH1"); 	cell.setValue("CTNhomChinh");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("DI1"); 	cell.setValue("TDNhomChinh");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("DJ1"); 	cell.setValue("%DatNhomChinh");  ReportAPI.setCellHeader(cell);
				    
				    cell = cells.getCell("DK1"); 	cell.setValue("CTSoDonHang");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("DL1"); 	cell.setValue("TDSoDonHang");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("DM1"); 	cell.setValue("%SoDonHang");  ReportAPI.setCellHeader(cell);
				    
				    cell = cells.getCell("DN1"); 	cell.setValue("CTQuyTrinh");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("DO1"); 	cell.setValue("TDQuyTrinh");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("DP1"); 	cell.setValue("%QuyTrinh");  ReportAPI.setCellHeader(cell);
				    
				    cell = cells.getCell("DQ1"); 	cell.setValue("CTNhomConLai");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("DR1"); 	cell.setValue("TDNhomConLai");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("DS1"); 	cell.setValue("%DatNhomConLai");  ReportAPI.setCellHeader(cell);
				    
				    cell = cells.getCell("DT1"); 	cell.setValue("%LuongThoiGian");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("DU1"); 	cell.setValue("LuongThoiGian");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("DV1"); 	cell.setValue("%KPI");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("DW1"); 	cell.setValue("%LuongHieuQua");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("DX1"); 	cell.setValue("LuongHieuQua");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("DY1"); 	cell.setValue("PhuCap");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("DZ1"); 	cell.setValue("ThuongDoanhSo");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("EA1"); 	cell.setValue("ThuongVuotMuc");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("EB1"); 	cell.setValue("BaoHiem");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("EC1"); 	cell.setValue("CongDoan");  ReportAPI.setCellHeader(cell);
				    cell = cells.getCell("ED1"); 	cell.setValue("ThuNhap");  ReportAPI.setCellHeader(cell);
			    }
		    }
		    else  //Dinh dang In
		    {
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
	    	    
	    	    cell = cells.getCell("P3");
	    	    String ngaybc = ReportAPI.NOW("yyyy-MM-dd");
	    	    
	    	    String ngay = ngaybc.substring(8, 10);
	    	    String nam = ngaybc.substring(0, 4);
	    	    String thang = ngaybc.substring(5, 7);
	    	    
	    	    tieude = "Ngày " + ngay + " tháng " + thang + " năm " + nam;
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, false, 10, tieude);
	    	    
	    	    cell = cells.getCell("A4");
	    	    tieude = "TỜ TRÌNH";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, false, 15, tieude);
	    	    
	    	    if(muclay.equals("0"))
	    	    {
		    	    cell = cells.getCell("A5");
		    	    tieude = "Vv: Bảng tính thu nhập tháng " + thang + "-" + nam + " của nhân viên bán hàng HTPP";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 13, tieude);
	    	    }
	    	    else
	    	    {
	    	    	cell = cells.getCell("A5");
		    	    tieude = "Vv: Bảng tính thu nhập tháng " + thang + "-" + nam + " của giám sát bán hàng";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 13, tieude);
	    	    }
	    	    
	    	    
	    	    cell = cells.getCell("A6");
	    	    tieude = "Kính gửi: Tổng giám đốc";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, tieude);
	    	    
		    	if(muclay.equals("0"))  /************************** DAI DIEN KINH DOANH ***************************/
			    {		    	    
		    	    cell = cells.getCell("A7");
		    	    tieude = "STT";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    cell = cells.getCell("B7");
		    	    tieude = "Họ và tên";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    cell = cells.getCell("C7");
		    	    tieude = "Mức lương công việc";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);

		    	    cell = cells.getCell("D7");
		    	    tieude = "Ngày công";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);

		    	    
		    	    /********Nhom chinh***********/
		    	    cell = cells.getCell("E7");
		    	    tieude = "Sản lượng nhóm chính";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    cell = cells.getCell("E8");
		    	    tieude = "Chỉ tiêu";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    cell = cells.getCell("F8");
		    	    tieude = "Thực hiện";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    cell = cells.getCell("G8");
		    	    tieude = "Tỉ lệ đạt";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    
		    	    /********Don Hang***********/
		    	    cell = cells.getCell("H7");
		    	    tieude = "Đơn hàng";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    cell = cells.getCell("H8");
		    	    tieude = "Chỉ tiêu";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    cell = cells.getCell("I8");
		    	    tieude = "Thực hiện";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    cell = cells.getCell("J8");
		    	    tieude = "Tỉ lệ đạt";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    
		    	    /********San Pham Moi***********/
		    	    cell = cells.getCell("K7");
		    	    tieude = "Sản phẩm mới";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    cell = cells.getCell("K8");
		    	    tieude = "Chỉ tiêu";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    cell = cells.getCell("L8");
		    	    tieude = "Thực hiện";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    cell = cells.getCell("M8");
		    	    tieude = "Tỉ lệ đạt";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    
		    	    cell = cells.getCell("N7");
		    	    tieude = "KPI (%)";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    cell = cells.getCell("O7");
		    	    tieude = "Lương thời gian";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    cell = cells.getCell("P7");
		    	    tieude = "Lương công việc";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    cell = cells.getCell("Q7");
		    	    tieude = "Phụ cấp";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    cell = cells.getCell("R7");
		    	    tieude = "Thưởng sản lượng";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    cell = cells.getCell("S7");
		    	    tieude = "Bảo hiểm";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    cell = cells.getCell("T7");
		    	    tieude = "Công đoàn";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    cell = cells.getCell("U7");
		    	    tieude = "Thu nhập";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
			    }
			    else  /************************** GIAM SAT BAN HANG ***************************/
			    {
		    	    cell = cells.getCell("A7");
		    	    tieude = "Họ và tên";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    cell = cells.getCell("B7");
		    	    tieude = "Mức lương công việc";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);

		    	    cell = cells.getCell("C7");
		    	    tieude = "Ngày công";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);

		    	    
		    	    /********Nhom chinh***********/
		    	    cell = cells.getCell("D7");
		    	    tieude = "Sản lượng nhóm chính";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    cell = cells.getCell("D8");
		    	    tieude = "Chỉ tiêu";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    cell = cells.getCell("E8");
		    	    tieude = "Thực hiện";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    cell = cells.getCell("F8");
		    	    tieude = "Tỉ lệ đạt";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    
		    	    /********Don Hang***********/
		    	    cell = cells.getCell("G7");
		    	    tieude = "Đơn hàng";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    cell = cells.getCell("G8");
		    	    tieude = "Chỉ tiêu";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    cell = cells.getCell("H8");
		    	    tieude = "Thực hiện";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    cell = cells.getCell("I8");
		    	    tieude = "Tỉ lệ đạt";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    /********Quy Trinh Lam viec***********/
		    	    cell = cells.getCell("J7");
		    	    tieude = "Quy trình làm việc";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    cell = cells.getCell("J8");
		    	    tieude = "Chỉ tiêu";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    cell = cells.getCell("K8");
		    	    tieude = "Thực hiện";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    cell = cells.getCell("L8");
		    	    tieude = "Tỉ lệ đạt";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    
		    	    /********San Pham Moi***********/
		    	    cell = cells.getCell("M7");
		    	    tieude = "Sản phẩm mới";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    cell = cells.getCell("M8");
		    	    tieude = "Chỉ tiêu";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    cell = cells.getCell("N8");
		    	    tieude = "Thực hiện";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    cell = cells.getCell("O8");
		    	    tieude = "Tỉ lệ đạt";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    
		    	    cell = cells.getCell("P7");
		    	    tieude = "KPI (%)";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    cell = cells.getCell("Q7");
		    	    tieude = "Lương thời gian";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    cell = cells.getCell("R7");
		    	    tieude = "Lương công việc";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    cell = cells.getCell("S7");
		    	    tieude = "Phụ cấp";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    cell = cells.getCell("T7");
		    	    tieude = "Bảo hiểm";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    cell = cells.getCell("U7");
		    	    tieude = "Công đoàn";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    	    
		    	    cell = cells.getCell("V7");
		    	    tieude = "Thu nhập";
		    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
			    }
		    }
		    
		}
		catch(Exception ex)
		{
			throw new Exception("Bao cao bi loi khi khoi tao");
		}
	}
	
	private void CreateStaticHeader_Npp(Workbook workbook, IKhoahuanluyenList obj, String muclay,String nppTen,int stt) throws Exception 
	{
		try
		{	
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(stt);
			worksheet.setName(nppTen);
			Cells cells = worksheet.getCells();
			
			Cell cell = cells.getCell("A1");
	 	    Font font = new Font();
	 	    font.setName("Times New Roman");
	 	    font.setColor(Color.RED);//mau chu
	 	    font.setSize(16);// size chu
	 	   	font.setBold(true);
	 	   	
	 	   	Style style = cell.getStyle();
	 	   	style.setHAlignment(TextAlignmentType.CENTER);
	 	   	style.setVAlignment(TextAlignmentType.CENTER);
	 	   	
	 	   	cell = cells.getCell("A1");
	 	   	String tieude = "CÔNG TY CỔ PHẦN ĐƯỜNG BIÊN HÒA";
	 	  	cells.merge(0, 0, 0, 5);
    	    ReportAPI.getCellStyle(cell, Color.BLACK, false, 10, tieude);
    	    
    	    cells.merge(1, 0, 1, 5);
    	    cell = cells.getCell("A2");
    	    tieude = "PHÒNG KINH DOANH";
    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
    	    
    	    cell = cells.getCell("P3");
    	    String ngaybc = ReportAPI.NOW("yyyy-MM-dd");
    	    
    	    String ngay = ngaybc.substring(8, 10);
    	    String nam = ngaybc.substring(0, 4);
    	    String thang = ngaybc.substring(5, 7);
    	    
    	    tieude = "Ngày " + ngay + " tháng " + thang + " năm " + nam;
    	    ReportAPI.getCellStyle(cell, Color.BLACK, false, 10, tieude);
    	    
    	    
    	    cells.merge(3, 0, 3, 15);
    	    cell = cells.getCell("A4");
    	    tieude = "TỜ TRÌNH";
    	    cell.setStyle(style);
    	    ReportAPI.getCellStyle(cell, Color.BLACK, false, 15, tieude);
    	    
    	    if(muclay.equals("0"))
    	    {
    	    	cells.merge(4, 0, 4, 15);
	    	    cell = cells.getCell("A5");
	    	    cell.setStyle(style);
	    	    tieude = "Vv: Bảng tính thu nhập tháng " + obj.getTungay() + "-" + obj.getDenngay() + " của nhân viên bán hàng HTPP";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 13, tieude);
    	    }
    	    else
    	    {
    	    	cells.merge(4, 0, 4, 15);
    	    	cell = cells.getCell("A5");
    	    	cell.setStyle(style);
	    	    tieude = "Vv: Bảng tính thu nhập tháng " + thang + "-" + nam + " của giám sát bán hàng";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 13, tieude);
    	    }
    	    cells.merge(5, 0, 5, 15);
    	    cell = cells.getCell("A6");
    	    tieude = "Kính gửi: Tổng giám đốc";
    	    cell.setStyle(style);
    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, tieude);
    	    
    	   
    	  
	    	if(muclay.equals("0"))  /************************** DAI DIEN KINH DOANH ***************************/
		    {	
	    		setBorder(cell,cells,6,7,0,20);
	    		
	    		cells.merge(6, 0, 7, 0);
	    	    cell = cells.getCell("A7");
	    	    tieude = "STT";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    cells.merge(6, 1, 7, 1);
	    	    cell = cells.getCell("B7");
	    	    tieude = "Họ và tên";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    cells.merge(6, 2, 7, 2);
	    	    cell = cells.getCell("C7");
	    	    tieude = "Mức lương công việc";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);

	    	    cells.merge(6, 3, 7, 3);
	    	    cell = cells.getCell("D7");
	    	    tieude = "Ngày công";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	  
	    	    /********Nhom chinh***********/
	    	    cell = cells.getCell("E7");
	    	    tieude = "Sản lượng nhóm chính";
	    	    cells.merge(6, 4, 6, 6);
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    cell = cells.getCell("E8");
	    	    tieude = "Chỉ tiêu";   
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    cell = cells.getCell("F8");
	    	    tieude = "Thực hiện";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    cell = cells.getCell("G8");
	    	    tieude = "Tỉ lệ đạt";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    /********Don Hang***********/
	    	    cells.merge(6, 7, 6, 9);
	    	    cell = cells.getCell("H7");
	    	    tieude = "Đơn hàng";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    cell = cells.getCell("H8");
	    	    tieude = "Chỉ tiêu";
	    	    
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    cell = cells.getCell("I8");
	    	    tieude = "Thực hiện";
	    	    
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    cell = cells.getCell("J8");
	    	    tieude = "Tỉ lệ đạt";
	    	    
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    
	    	    /********San Pham Moi***********/
	    	    
	    	    cell = cells.getCell("K7");
	    	    tieude = "Sản phẩm mới";
	    	    cells.merge(6, 10, 6, 12);
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    cell = cells.getCell("K8");
	    	    tieude = "Chỉ tiêu";
	    	    
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    cell = cells.getCell("L8");
	    	    tieude = "Thực hiện";
	    	    
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    cell = cells.getCell("M8");
	    	    tieude = "Tỉ lệ đạt";
	    	    
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    cells.merge(6, 13, 7, 13);
	    	    cell = cells.getCell("N7");
	    	    tieude = "KPI (%)";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    
	    	    cells.merge(6, 14, 7, 14);
	    	    cell = cells.getCell("O7");
	    	    tieude = "Lương thời gian";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    
	    	    cells.merge(6, 15, 7, 15);
	    	    cell = cells.getCell("P7");
	    	    tieude = "Lương công việc";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    
	    	    cells.merge(6, 16, 7, 16);
	    	    cell = cells.getCell("Q7");
	    	    tieude = "Phụ cấp";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    

	    	    cells.merge(6, 17, 7, 17);
	    	    cell = cells.getCell("R7");
	    	    tieude = "Thưởng sản lượng";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    cells.merge(6, 18, 7, 18);
	    	    cell = cells.getCell("S7");
	    	    tieude = "Bảo hiểm";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    
	    	    cells.merge(6, 19, 7, 19);
	    	    cell = cells.getCell("T7");
	    	    tieude = "Công đoàn";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    
	    	    cells.merge(6, 20, 7, 20);
	    	    cell = cells.getCell("U7");
	    	    tieude = "Thu nhập";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    		
	    	    
		    }
		    else  /************************** GIAM SAT BAN HANG ***************************/
		    {
		    	setBorder(cell,cells,6,7,0,23);
		    	cells.merge(6,0, 7,0);
	    	    cell = cells.getCell("A7");
	    	    tieude = "Họ và tên";   
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    cells.merge(6,1, 7,1);
	    	    cell = cells.getCell("B7");
	    	    tieude = "Mức lương công việc";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);

	    	    cells.merge(6,2, 7,2);
	    	    cell = cells.getCell("C7");
	    	    tieude = "Ngày công";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);

	    	    
	    	    /********Nhom chinh***********/
	    	    cells.merge(6,3, 6,5);
	    	    cell = cells.getCell("D7");
	    	    
	    	    tieude = "Sản lượng nhóm chính";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    cell = cells.getCell("D8");
	    	    tieude = "Chỉ tiêu";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    cell = cells.getCell("E8");
	    	    tieude = "Thực hiện";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    cell = cells.getCell("F8");
	    	    tieude = "Tỉ lệ đạt";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    
	    	    /********Don Hang***********/
	    	    cells.merge(6,6, 6,8);
	    	    cell = cells.getCell("G7");
	    	    tieude = "Đơn hàng";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    cell = cells.getCell("G8");
	    	    tieude = "Chỉ tiêu";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    cell = cells.getCell("H8");
	    	    tieude = "Thực hiện";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    cell = cells.getCell("I8");
	    	    tieude = "Tỉ lệ đạt";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    /********Quy Trinh Lam viec***********/
	    	    cells.merge(6,9, 6,11);
	    	    cell = cells.getCell("J7");
	    	    tieude = "Quy trình làm việc";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    cell = cells.getCell("J8");
	    	    tieude = "Chỉ tiêu";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    cell = cells.getCell("K8");
	    	    tieude = "Thực hiện";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    cell = cells.getCell("L8");
	    	    tieude = "Tỉ lệ đạt";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    
	    	    /********San Pham Moi***********/
	    	    cells.merge(6,12, 6,14);
	    	    cell = cells.getCell("M7");
	    	    tieude = "Sản phẩm mới";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    cell = cells.getCell("M8");
	    	    tieude = "Chỉ tiêu";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    cell = cells.getCell("N8");
	    	    tieude = "Thực hiện";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    cell = cells.getCell("O8");
	    	    tieude = "Tỉ lệ đạt";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    cells.merge(6,15, 7,15);
	    	    cell = cells.getCell("P7");
	    	    tieude = "KPI (%)";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    cells.merge(6,16, 7,16);
	    	    cell = cells.getCell("Q7");
	    	    tieude = "Lương thời gian";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    cells.merge(6,17, 7,17);
	    	    cell = cells.getCell("R7");
	    	    tieude = "Lương công việc";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    cells.merge(6,18, 7,18);
	    	    cell = cells.getCell("S7");
	    	    tieude = "Thưởnng doanh số";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    cells.merge(6,19, 7,19);
	    	    cell = cells.getCell("T7");
	    	    tieude = "Thưởnng vượt mức";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    
	    	    
	    	    cells.merge(6,20, 7,20);
	    	    cell = cells.getCell("U7");
	    	    tieude = "Phụ cấp";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    cells.merge(6,21, 7,21);
	    	    cell = cells.getCell("V7");
	    	    tieude = "Bảo hiểm";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    cells.merge(6,22, 7,22);
	    	    cell = cells.getCell("W7");
	    	    tieude = "Công đoàn";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    	    cells.merge(6,23, 7,23);
	    	    cell = cells.getCell("X7");
	    	    tieude = "Thu nhập";
	    	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	 
		    }
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			//throw new Exception("Bao cao bi loi khi khoi tao "+ex.getMessage());
		}
	}	
		
	private boolean CreateStaticData_In_BangLuong(Workbook workbook, IKhoahuanluyenList obj, String muclay) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    String query = "";
	    double luongcb = 0;
	    double ptluongtg = 0;
	    double ptluonghieuqua = 0;
    	System.out.println("____Muc lay la: " + muclay);
    	query=getQuery(muclay, obj);
    	  if(obj.getVungId().length() > 0)
  	    	query += " and thongtinluong.vungId = '" + obj.getVungId() + "' ";
  	    if(obj.getKvId().length() > 0)
  	    	query += " and thongtinluong.kvId = '" + obj.getKvId() + "' ";
  	    if(muclay.equals("1"))
  	    	query+="order by thongtinluong.kvId ";
		System.out.println("1.Tinh luong theo bang In: " + query);
		ResultSet rs = db.getScrol(query);
		int i = 9;
		if(rs != null)
		{
			try 
			{
				for(int j = 0; j < 15; j++)
					cells.setColumnWidth(i, 12.0f);
				
				Cell cell = null;
				
				String kvGroup = "";
				String kvTen = "";
				String nppGroup = "";
				String nppTen = "";
				int stt = 1;
				
				double ttNc = 0;
				double tdNc = 0;
				double thNc = 0;
				double ttNp = 0;
				double tdNp = 0;
				double thNp = 0;
				double ttDh = 0;
				double tdDh = 0;
				double thDh = 0;
				
				double ttKpi = 0;
				double ttLtg = 0;
				double ttLcv = 0;
				double ttPc = 0;
				double ttTsl = 0;
				
				double ttBh = 0;
				double ttCd = 0;
				
				double ttTn = 0;

				
				//Dem so DDKD cua NPP
				Hashtable<String, Integer> NPP_SO_DDKD = new Hashtable<String, Integer>();
				if(muclay.equals("0"))
				{
					while(rs.next())
					{
						String npp = rs.getString("nppId");
						System.out.println("___nppId__"+npp);
						if(!NPP_SO_DDKD.containsKey(npp))
						{
							NPP_SO_DDKD.put(npp, 1);
						}
						else
						{
							int soluong = NPP_SO_DDKD.get(npp);
							NPP_SO_DDKD.put(npp, soluong + 1);
						}
					}
					rs.beforeFirst();
				}
				while(rs.next())
				{
					if(muclay.equals("0")) //DDKD
					{
						kvGroup = rs.getString("kvTen");
						
						if( ! kvGroup.trim().equals(kvTen) ) 
						{
							System.out.println("1.In ra Khu vuc Khac dau tien...");
							
							//Truoc khi chuyen sang KV khac
							
							cell = cells.getCell("A" + Integer.toString(i)); 	cell.setValue("Khu vực: " + kvGroup);
							kvTen = kvGroup;
							
							i++;
							
							//Phai in NPP Trong nay luon
							nppGroup = rs.getString("nppTen");
							
							if( ! nppGroup.equals(nppTen) )
							{
								System.out.println("2.In ra Nha phan phoi Khac dau tien...");
								
								ttNc = tdNc = thNc = ttNp = tdNp = thNp = ttDh = tdDh = thDh = ttKpi = ttLtg = ttLcv = ttPc = ttTsl = ttBh = ttCd = ttTn = 0;
								
								cell = cells.getCell("A" + Integer.toString(i)); 	cell.setValue("NPP: " + nppGroup);
								
								nppTen = nppGroup;
								i++;
								stt = 1;
								
								//In cac ddkd trong nay
								String ten = rs.getString("ten");
								String nppId = rs.getString("nppId");
								
								double nlvChitieu = rs.getDouble("nlvChitieu");
								double nlvThucte = rs.getDouble("nlvThucte");
								
								double ctNhomchinh = rs.getDouble("ctNhomChinh");	ttNc += ctNhomchinh;
								double ttNhomchinh = rs.getDouble("ttNhomChinh");	tdNc += ttNhomchinh;
								double ptNhomchinh = rs.getDouble("ptNhomchinh");   thNc += ptNhomchinh;
								
								
								double ctDonhang = rs.getDouble("ctSodonhang");     ttDh += ctDonhang;
								double ttDonhang = rs.getDouble("ttSodonhang");		tdDh += ttDonhang;
								double ptDonhang = rs.getDouble("ptSodonhang");		thDh += ptDonhang;
								
								double ctNhomphu = rs.getDouble("ctNhomphu");		ttNp += ctNhomphu;
								double ttNhomphu = rs.getDouble("ttNhomphu");		tdNp += ttNhomphu;
								double ptNhomphu = rs.getDouble("ptNhomphu");		thNp += ptNhomphu;
								
								luongcb = rs.getDouble("luongcb");
								ptluongtg = rs.getDouble("ptluongtg");
								ptluonghieuqua = rs.getDouble("ptluonghieuqua");
								
								double luongtg = luongcb * (nlvThucte / nlvChitieu) * (ptluongtg / 100);		ttLtg += luongtg;
								
								double ptKpi = ( Math.round(rs.getDouble("PT_KPI") * 100) / 100.0  ) * ( nlvThucte / nlvChitieu );				ttKpi += ptKpi;
								
								/*****************************************/
								double luonghq = 0;
								double tdnc = rs.getDouble("tdnc");
								System.out.println("____Thuc dat ngay cong: " + tdnc);
								
								if(nlvThucte < tdnc)
									luonghq = ( luongcb * (ptluonghieuqua / 100) ) * (ptKpi / 100) * (nlvThucte / nlvChitieu);
								else
									luonghq = ( luongcb * (ptluonghieuqua / 100) ) * (ptKpi / 100);
								/*****************************************/
								
								ttLcv += luonghq;
								//double luonghq = ( luongcb * (ptluonghieuqua / 100) ) * (ptKpi / 100);			ttLcv += luonghq;
								
								double thuongSLTieuthu = rs.getDouble("thuongSLTieuthu");						ttTsl += thuongSLTieuthu;
								
								/*double phucap = rs.getDouble("TienPhuCap");	
								if(phucap >= 0)
								{
									phucap = phucap * (nlvThucte / nlvChitieu);
									ttPc += phucap;
								}*/
								
								double TienPhuCap_TheoNC = rs.getDouble("TienPhuCap_TheoNC");
								if(TienPhuCap_TheoNC > 0)
									TienPhuCap_TheoNC = TienPhuCap_TheoNC * (nlvThucte / nlvChitieu);
								
								double TienPhuCap_KoTheoNC = rs.getDouble("TienPhuCap_KoTheoNC");
								
								double phucap = TienPhuCap_TheoNC + TienPhuCap_KoTheoNC;
								if(phucap > 0)
									ttPc += phucap;
								
								double bhxh = (rs.getDouble("BHXH") / 100 ) * luongcb;      ttBh += bhxh;
								double congdoan = (rs.getDouble("congdoan") / 100 ) * (luongtg + luonghq + thuongSLTieuthu + phucap);	ttCd += congdoan;
								double chiphi = bhxh + congdoan;
								
								double thunhap = luongtg + luonghq + thuongSLTieuthu + phucap - chiphi;					ttTn += thunhap;
			            		
								cell = cells.getCell("A" + Integer.toString(i)); 	cell.setValue(stt);
								cell = cells.getCell("B" + Integer.toString(i)); 	cell.setValue(ten);
								cell = cells.getCell("C" + Integer.toString(i)); 	cell.setValue(luongcb);
								cell = cells.getCell("D" + Integer.toString(i)); 	cell.setValue(nlvThucte);
								cell = cells.getCell("E" + Integer.toString(i)); 	cell.setValue(ctNhomchinh);
								cell = cells.getCell("F" + Integer.toString(i)); 	cell.setValue(ttNhomchinh);
								cell = cells.getCell("G" + Integer.toString(i)); 	cell.setValue(ptNhomchinh);
								cell = cells.getCell("H" + Integer.toString(i)); 	cell.setValue(ctDonhang);
								cell = cells.getCell("I" + Integer.toString(i)); 	cell.setValue(ttDonhang);
								cell = cells.getCell("J" + Integer.toString(i)); 	cell.setValue(ptDonhang);
								cell = cells.getCell("K" + Integer.toString(i)); 	cell.setValue(ctNhomphu);
								cell = cells.getCell("L" + Integer.toString(i)); 	cell.setValue(ttNhomphu);
								cell = cells.getCell("M" + Integer.toString(i)); 	cell.setValue(ptNhomphu);

								cell = cells.getCell("N" + Integer.toString(i)); 	cell.setValue(ptKpi);
								cell = cells.getCell("O" + Integer.toString(i)); 	cell.setValue(luongtg);
								cell = cells.getCell("P" + Integer.toString(i)); 	cell.setValue(luonghq);
								cell = cells.getCell("Q" + Integer.toString(i)); 	cell.setValue(phucap);
								cell = cells.getCell("R" + Integer.toString(i)); 	cell.setValue(thuongSLTieuthu);
								cell = cells.getCell("S" + Integer.toString(i)); 	cell.setValue(bhxh);
								cell = cells.getCell("T" + Integer.toString(i)); 	cell.setValue(congdoan);
								cell = cells.getCell("U" + Integer.toString(i)); 	cell.setValue(thunhap);
								
								stt++;
								i++;
								
								if(stt == (NPP_SO_DDKD.get(nppId) + 1) )
								{
									cell = cells.getCell("A" + Integer.toString(i)); 	cell.setValue("       Tổng Cộng");
									
									cell = cells.getCell("E" + Integer.toString(i)); 	cell.setValue(ttNc);
									cell = cells.getCell("F" + Integer.toString(i)); 	cell.setValue(tdNc);
									cell = cells.getCell("G" + Integer.toString(i)); 	cell.setValue(thNc * 100 / ttNc);
									
									cell = cells.getCell("H" + Integer.toString(i)); 	cell.setValue(ttDh);
									cell = cells.getCell("I" + Integer.toString(i)); 	cell.setValue(tdDh);
									cell = cells.getCell("J" + Integer.toString(i)); 	cell.setValue(tdDh * 100 / ttDh);
									
									cell = cells.getCell("K" + Integer.toString(i)); 	cell.setValue(ttNp);
									cell = cells.getCell("L" + Integer.toString(i)); 	cell.setValue(tdNp);
									cell = cells.getCell("M" + Integer.toString(i)); 	cell.setValue(tdNp * 100 / ttNp );
									
									cell = cells.getCell("N" + Integer.toString(i)); 	cell.setValue(ttKpi / (stt - 1) );
									cell = cells.getCell("O" + Integer.toString(i)); 	cell.setValue(ttLtg);
									cell = cells.getCell("P" + Integer.toString(i)); 	cell.setValue(ttLcv);
									cell = cells.getCell("Q" + Integer.toString(i)); 	cell.setValue(ttPc);
									cell = cells.getCell("R" + Integer.toString(i)); 	cell.setValue(ttTsl);
									cell = cells.getCell("S" + Integer.toString(i)); 	cell.setValue(ttBh);
									cell = cells.getCell("T" + Integer.toString(i)); 	cell.setValue(ttCd);
									cell = cells.getCell("U" + Integer.toString(i)); 	cell.setValue(ttTn);
									
									i++;
								}
								
							}
							else
							{
								System.out.println("3.In cac dong NPP giong nhau...");
								
								//In cac ddkd trong nay
								String ten = rs.getString("ten");
								String nppId = rs.getString("nppId");

								double nlvChitieu = rs.getDouble("nlvChitieu");
								double nlvThucte = rs.getDouble("nlvThucte");
								
								double ctNhomchinh = rs.getDouble("ctNhomChinh");	ttNc += ctNhomchinh;
								double ttNhomchinh = rs.getDouble("ttNhomChinh");	tdNc += ttNhomchinh;
								double ptNhomchinh = rs.getDouble("ptNhomchinh");   thNc += ptNhomchinh;
								
								
								double ctDonhang = rs.getDouble("ctSodonhang");     ttDh += ctDonhang;
								double ttDonhang = rs.getDouble("ttSodonhang");		tdDh += ttDonhang;
								double ptDonhang = rs.getDouble("ptSodonhang");		thDh += ptDonhang;
								
								double ctNhomphu = rs.getDouble("ctNhomphu");		ttNp += ctNhomphu;
								double ttNhomphu = rs.getDouble("ttNhomphu");		tdNp += ttNhomphu;
								double ptNhomphu = rs.getDouble("ptNhomphu");		thNp += ptNhomphu;
								
								luongcb = rs.getDouble("luongcb");
								ptluongtg = rs.getDouble("ptluongtg");
								ptluonghieuqua = rs.getDouble("ptluonghieuqua");
								
								double luongtg = luongcb * (nlvThucte / nlvChitieu) * (ptluongtg / 100);		ttLtg += luongtg;
								
								double ptKpi = ( Math.round(rs.getDouble("PT_KPI") * 100) / 100.0  ) * ( nlvThucte / nlvChitieu );				ttKpi += ptKpi;
								
								/*****************************************/
								double luonghq = 0;
								double tdnc = rs.getDouble("tdnc");
								System.out.println("____Thuc dat ngay cong: " + tdnc);
								
								if(nlvThucte < tdnc)
									luonghq = ( luongcb * (ptluonghieuqua / 100) ) * (ptKpi / 100) * (nlvThucte / nlvChitieu);
								else
									luonghq = ( luongcb * (ptluonghieuqua / 100) ) * (ptKpi / 100);
								/*****************************************/
								
								ttLcv += luonghq;
								//double luonghq = ( luongcb * (ptluonghieuqua / 100) ) * (ptKpi / 100);			ttLcv += luonghq;
								
								double thuongSLTieuthu = rs.getDouble("thuongSLTieuthu");						ttTsl += thuongSLTieuthu;
								
								/*double phucap = rs.getDouble("TienPhuCap");	
								if(phucap >= 0)
								{
									phucap = phucap * (nlvThucte / nlvChitieu);
									ttPc += phucap;
								}*/
								
								double TienPhuCap_TheoNC = rs.getDouble("TienPhuCap_TheoNC");
								if(TienPhuCap_TheoNC > 0)
									TienPhuCap_TheoNC = TienPhuCap_TheoNC * (nlvThucte / nlvChitieu);
								
								double TienPhuCap_KoTheoNC = rs.getDouble("TienPhuCap_KoTheoNC");
								
								double phucap = TienPhuCap_TheoNC + TienPhuCap_KoTheoNC;
								if(phucap >= 0)
								{
									ttPc += phucap;
								}
								
								double bhxh = (rs.getDouble("BHXH") / 100 ) * luongcb;      ttBh += bhxh;
								double congdoan = (rs.getDouble("congdoan") / 100 ) * (luongtg + luonghq + thuongSLTieuthu + phucap);	ttCd += congdoan;
								double chiphi = bhxh + congdoan;
								
								double thunhap = luongtg + luonghq + thuongSLTieuthu + phucap - chiphi;					ttTn += thunhap;
			            		
								cell = cells.getCell("A" + Integer.toString(i)); 	cell.setValue(stt);
								cell = cells.getCell("B" + Integer.toString(i)); 	cell.setValue(ten);
								cell = cells.getCell("C" + Integer.toString(i)); 	cell.setValue(luongcb);
								cell = cells.getCell("D" + Integer.toString(i)); 	cell.setValue(nlvThucte);
								cell = cells.getCell("E" + Integer.toString(i)); 	cell.setValue(ctNhomchinh);
								cell = cells.getCell("F" + Integer.toString(i)); 	cell.setValue(ttNhomchinh);
								cell = cells.getCell("G" + Integer.toString(i)); 	cell.setValue(ptNhomchinh);
								cell = cells.getCell("H" + Integer.toString(i)); 	cell.setValue(ctDonhang);
								cell = cells.getCell("I" + Integer.toString(i)); 	cell.setValue(ttDonhang);
								cell = cells.getCell("J" + Integer.toString(i)); 	cell.setValue(ptDonhang);
								cell = cells.getCell("K" + Integer.toString(i)); 	cell.setValue(ctNhomphu);
								cell = cells.getCell("L" + Integer.toString(i)); 	cell.setValue(ttNhomphu);
								cell = cells.getCell("M" + Integer.toString(i)); 	cell.setValue(ptNhomphu);

								cell = cells.getCell("N" + Integer.toString(i)); 	cell.setValue(ptKpi);
								cell = cells.getCell("O" + Integer.toString(i)); 	cell.setValue(luongtg);
								cell = cells.getCell("P" + Integer.toString(i)); 	cell.setValue(luonghq);
								cell = cells.getCell("Q" + Integer.toString(i)); 	cell.setValue(phucap);
								cell = cells.getCell("R" + Integer.toString(i)); 	cell.setValue(thuongSLTieuthu);
								cell = cells.getCell("S" + Integer.toString(i)); 	cell.setValue(bhxh);
								cell = cells.getCell("T" + Integer.toString(i)); 	cell.setValue(congdoan);
								cell = cells.getCell("U" + Integer.toString(i)); 	cell.setValue(thunhap);
								
								stt++;
								i++;
								
								if(stt == (NPP_SO_DDKD.get(nppId) + 1) )
								{
									cell = cells.getCell("A" + Integer.toString(i)); 	cell.setValue("       Tổng Cộng");
									
									cell = cells.getCell("E" + Integer.toString(i)); 	cell.setValue(ttNc);
									cell = cells.getCell("F" + Integer.toString(i)); 	cell.setValue(tdNc);
									cell = cells.getCell("G" + Integer.toString(i)); 	cell.setValue(thNc * 100 / ttNc);
									
									cell = cells.getCell("H" + Integer.toString(i)); 	cell.setValue(ttDh);
									cell = cells.getCell("I" + Integer.toString(i)); 	cell.setValue(tdDh);
									cell = cells.getCell("J" + Integer.toString(i)); 	cell.setValue(tdDh * 100 / ttDh);
									
									cell = cells.getCell("K" + Integer.toString(i)); 	cell.setValue(ttNp);
									cell = cells.getCell("L" + Integer.toString(i)); 	cell.setValue(tdNp);
									cell = cells.getCell("M" + Integer.toString(i)); 	cell.setValue(tdNp * 100 / ttNp );
									
									cell = cells.getCell("N" + Integer.toString(i)); 	cell.setValue(ttKpi / (stt - 1) );
									cell = cells.getCell("O" + Integer.toString(i)); 	cell.setValue(ttLtg);
									cell = cells.getCell("P" + Integer.toString(i)); 	cell.setValue(ttLcv);
									cell = cells.getCell("Q" + Integer.toString(i)); 	cell.setValue(ttPc);
									cell = cells.getCell("R" + Integer.toString(i)); 	cell.setValue(ttTsl);
									cell = cells.getCell("S" + Integer.toString(i)); 	cell.setValue(ttBh);
									cell = cells.getCell("T" + Integer.toString(i)); 	cell.setValue(ttCd);
									cell = cells.getCell("U" + Integer.toString(i)); 	cell.setValue(ttTn);
									
									i++;
								}
							}
							
						}
						else   //In NPP
						{
							//Phai in NPP Trong nay luon
							nppGroup = rs.getString("nppTen");
							
							if( ! nppGroup.equals(nppTen) )
							{
								System.out.println("2.2. In ra Nha phan phoi Khac dau tien...");
								
								ttNc = tdNc = thNc = ttNp = tdNp = thNp = ttDh = tdDh = thDh = ttKpi = ttLtg = ttLcv = ttPc = ttTsl = ttBh = ttCd = ttTn = 0;
								
								cell = cells.getCell("A" + Integer.toString(i)); 	cell.setValue("NPP: " + nppGroup);
								
								nppTen = nppGroup;
								i++;
								stt = 1;
								
								//In cac ddkd trong nay
								String ten = rs.getString("ten");
								String nppId = rs.getString("nppId");

								double nlvChitieu = rs.getDouble("nlvChitieu");
								double nlvThucte = rs.getDouble("nlvThucte");
								
								double ctNhomchinh = rs.getDouble("ctNhomChinh");	ttNc += ctNhomchinh;
								double ttNhomchinh = rs.getDouble("ttNhomChinh");	tdNc += ttNhomchinh;
								double ptNhomchinh = rs.getDouble("ptNhomchinh");   thNc += ptNhomchinh;
								
								
								double ctDonhang = rs.getDouble("ctSodonhang");     ttDh += ctDonhang;
								double ttDonhang = rs.getDouble("ttSodonhang");		tdDh += ttDonhang;
								double ptDonhang = rs.getDouble("ptSodonhang");		thDh += ptDonhang;
								
								double ctNhomphu = rs.getDouble("ctNhomphu");		ttNp += ctNhomphu;
								double ttNhomphu = rs.getDouble("ttNhomphu");		tdNp += ttNhomphu;
								double ptNhomphu = rs.getDouble("ptNhomphu");		thNp += ptNhomphu;
								
								luongcb = rs.getDouble("luongcb");
								ptluongtg = rs.getDouble("ptluongtg");
								ptluonghieuqua = rs.getDouble("ptluonghieuqua");
								
								double luongtg = luongcb * (nlvThucte / nlvChitieu) * (ptluongtg / 100);		ttLtg += luongtg;
								
								double ptKpi = ( Math.round(rs.getDouble("PT_KPI") * 100) / 100.0  ) * ( nlvThucte / nlvChitieu );				ttKpi += ptKpi;
								
								/*****************************************/
								double luonghq = 0;
								double tdnc = rs.getDouble("tdnc");
								System.out.println("____Thuc dat ngay cong: " + tdnc);
								
								if(nlvThucte < tdnc)
									luonghq = ( luongcb * (ptluonghieuqua / 100) ) * (ptKpi / 100) * (nlvThucte / nlvChitieu);
								else
									luonghq = ( luongcb * (ptluonghieuqua / 100) ) * (ptKpi / 100);
								/*****************************************/
								
								ttLcv += luonghq;
								
								//double luonghq = ( luongcb * (ptluonghieuqua / 100) ) * (ptKpi / 100);			ttLcv += luonghq;
								
								double thuongSLTieuthu = rs.getDouble("thuongSLTieuthu");						ttTsl += thuongSLTieuthu;
								
								/*double phucap = rs.getDouble("TienPhuCap");	
								if(phucap >= 0)
								{
									phucap = phucap * (nlvThucte / nlvChitieu);
									ttPc += phucap;
								}*/
								
								double TienPhuCap_TheoNC = rs.getDouble("TienPhuCap_TheoNC");
								if(TienPhuCap_TheoNC > 0)
									TienPhuCap_TheoNC = TienPhuCap_TheoNC * (nlvThucte / nlvChitieu);
								
								double TienPhuCap_KoTheoNC = rs.getDouble("TienPhuCap_KoTheoNC");
								
								double phucap = TienPhuCap_TheoNC + TienPhuCap_KoTheoNC;
								if(phucap >= 0)
								{
									ttPc += phucap;
								}
								
								double bhxh = (rs.getDouble("BHXH") / 100 ) * luongcb;	ttBh += bhxh;
								double congdoan = (rs.getDouble("congdoan") / 100 ) * (luongtg + luonghq + thuongSLTieuthu + phucap);	ttCd += congdoan;
								double chiphi = bhxh + congdoan;
								
								double thunhap = luongtg + luonghq + thuongSLTieuthu + phucap - chiphi;					ttTn += thunhap;
			            		
								cell = cells.getCell("A" + Integer.toString(i)); 	cell.setValue(stt);
								cell = cells.getCell("B" + Integer.toString(i)); 	cell.setValue(ten);
								cell = cells.getCell("C" + Integer.toString(i)); 	cell.setValue(luongcb);
								cell = cells.getCell("D" + Integer.toString(i)); 	cell.setValue(nlvThucte);
								cell = cells.getCell("E" + Integer.toString(i)); 	cell.setValue(ctNhomchinh);
								cell = cells.getCell("F" + Integer.toString(i)); 	cell.setValue(ttNhomchinh);
								cell = cells.getCell("G" + Integer.toString(i)); 	cell.setValue(ptNhomchinh);
								cell = cells.getCell("H" + Integer.toString(i)); 	cell.setValue(ctDonhang);
								cell = cells.getCell("I" + Integer.toString(i)); 	cell.setValue(ttDonhang);
								cell = cells.getCell("J" + Integer.toString(i)); 	cell.setValue(ptDonhang);
								cell = cells.getCell("K" + Integer.toString(i)); 	cell.setValue(ctNhomphu);
								cell = cells.getCell("L" + Integer.toString(i)); 	cell.setValue(ttNhomphu);
								cell = cells.getCell("M" + Integer.toString(i)); 	cell.setValue(ptNhomphu);

								cell = cells.getCell("N" + Integer.toString(i)); 	cell.setValue(ptKpi);
								cell = cells.getCell("O" + Integer.toString(i)); 	cell.setValue(luongtg);
								cell = cells.getCell("P" + Integer.toString(i)); 	cell.setValue(luonghq);
								cell = cells.getCell("Q" + Integer.toString(i)); 	cell.setValue(phucap);
								cell = cells.getCell("R" + Integer.toString(i)); 	cell.setValue(thuongSLTieuthu);
								cell = cells.getCell("S" + Integer.toString(i)); 	cell.setValue(bhxh);
								cell = cells.getCell("T" + Integer.toString(i)); 	cell.setValue(congdoan);
								cell = cells.getCell("U" + Integer.toString(i)); 	cell.setValue(thunhap);
								
								stt++;
								i++;
								
								if(stt == (NPP_SO_DDKD.get(nppId) + 1) )
								{
									cell = cells.getCell("A" + Integer.toString(i)); 	cell.setValue("       Tổng Cộng");
									
									cell = cells.getCell("E" + Integer.toString(i)); 	cell.setValue(ttNc);
									cell = cells.getCell("F" + Integer.toString(i)); 	cell.setValue(tdNc);
									cell = cells.getCell("G" + Integer.toString(i)); 	cell.setValue(thNc * 100 / ttNc);
									
									cell = cells.getCell("H" + Integer.toString(i)); 	cell.setValue(ttDh);
									cell = cells.getCell("I" + Integer.toString(i)); 	cell.setValue(tdDh);
									cell = cells.getCell("J" + Integer.toString(i)); 	cell.setValue(tdDh * 100 / ttDh);
									
									cell = cells.getCell("K" + Integer.toString(i)); 	cell.setValue(ttNp);
									cell = cells.getCell("L" + Integer.toString(i)); 	cell.setValue(tdNp);
									cell = cells.getCell("M" + Integer.toString(i)); 	cell.setValue(tdNp * 100 / ttNp );
									
									cell = cells.getCell("N" + Integer.toString(i)); 	cell.setValue(ttKpi / (stt - 1) );
									cell = cells.getCell("O" + Integer.toString(i)); 	cell.setValue(ttLtg);
									cell = cells.getCell("P" + Integer.toString(i)); 	cell.setValue(ttLcv);
									cell = cells.getCell("Q" + Integer.toString(i)); 	cell.setValue(ttPc);
									cell = cells.getCell("R" + Integer.toString(i)); 	cell.setValue(ttTsl);
									cell = cells.getCell("S" + Integer.toString(i)); 	cell.setValue(ttBh);
									cell = cells.getCell("T" + Integer.toString(i)); 	cell.setValue(ttCd);
									cell = cells.getCell("U" + Integer.toString(i)); 	cell.setValue(ttTn);
									
									i++;
								}
								
							}
							else
							{
								System.out.println("3.2. In cac dong NPP giong nhau...");
								
								//In cac ddkd trong nay
								String ten = rs.getString("ten");
								String nppId = rs.getString("nppId");
								
								double nlvChitieu = rs.getDouble("nlvChitieu");
								double nlvThucte = rs.getDouble("nlvThucte");
								
								double ctNhomchinh = rs.getDouble("ctNhomChinh");	ttNc += ctNhomchinh;
								double ttNhomchinh = rs.getDouble("ttNhomChinh");	tdNc += ttNhomchinh;
								double ptNhomchinh = rs.getDouble("ptNhomchinh");   thNc += ptNhomchinh;
								
								
								double ctDonhang = rs.getDouble("ctSodonhang");     ttDh += ctDonhang;
								double ttDonhang = rs.getDouble("ttSodonhang");		tdDh += ttDonhang;
								double ptDonhang = rs.getDouble("ptSodonhang");		thDh += ptDonhang;
								
								double ctNhomphu = rs.getDouble("ctNhomphu");		ttNp += ctNhomphu;
								double ttNhomphu = rs.getDouble("ttNhomphu");		tdNp += ttNhomphu;
								double ptNhomphu = rs.getDouble("ptNhomphu");		thNp += ptNhomphu;
								
								luongcb = rs.getDouble("luongcb");
								ptluongtg = rs.getDouble("ptluongtg");
								ptluonghieuqua = rs.getDouble("ptluonghieuqua");
								
								double luongtg = luongcb * (nlvThucte / nlvChitieu) * (ptluongtg / 100);		ttLtg += luongtg;
								
								double ptKpi = ( Math.round(rs.getDouble("PT_KPI") * 100) / 100.0  ) * ( nlvThucte / nlvChitieu );				ttKpi += ptKpi;
								
								/*****************************************/
								double luonghq = 0;
								double tdnc = rs.getDouble("tdnc");
								System.out.println("____Thuc dat ngay cong: " + tdnc);
								
								if(nlvThucte < tdnc)
									luonghq = ( luongcb * (ptluonghieuqua / 100) ) * (ptKpi / 100) * (nlvThucte / nlvChitieu);
								else
									luonghq = ( luongcb * (ptluonghieuqua / 100) ) * (ptKpi / 100);
								/*****************************************/
								
								ttLcv += luonghq;
								
								//double luonghq = ( luongcb * (ptluonghieuqua / 100) ) * (ptKpi / 100);			ttLcv += luonghq;
								
								double thuongSLTieuthu = rs.getDouble("thuongSLTieuthu");						ttTsl += thuongSLTieuthu;
								
								/*double phucap = rs.getDouble("TienPhuCap");	
								if(phucap >= 0)
								{
									phucap = phucap * (nlvThucte / nlvChitieu);
									ttPc += phucap;
								}*/
								
								double TienPhuCap_TheoNC = rs.getDouble("TienPhuCap_TheoNC");
								if(TienPhuCap_TheoNC > 0)
									TienPhuCap_TheoNC = TienPhuCap_TheoNC * (nlvThucte / nlvChitieu);
								
								double TienPhuCap_KoTheoNC = rs.getDouble("TienPhuCap_KoTheoNC");
								
								double phucap = TienPhuCap_TheoNC + TienPhuCap_KoTheoNC;
								if(phucap >= 0)
								{
									ttPc += phucap;
								}
								
								double bhxh = (rs.getDouble("BHXH") / 100 ) * luongcb;	ttBh += bhxh;
								double congdoan = (rs.getDouble("congdoan") / 100 ) * (luongtg + luonghq + thuongSLTieuthu + phucap);		ttCd += congdoan;
								double chiphi = bhxh + congdoan;
								
								double thunhap = luongtg + luonghq + thuongSLTieuthu + phucap - chiphi;					ttTn += thunhap;
			            		
								cell = cells.getCell("A" + Integer.toString(i)); 	cell.setValue(stt);
								cell = cells.getCell("B" + Integer.toString(i)); 	cell.setValue(ten);
								cell = cells.getCell("C" + Integer.toString(i)); 	cell.setValue(luongcb);
								cell = cells.getCell("D" + Integer.toString(i)); 	cell.setValue(nlvThucte);
								cell = cells.getCell("E" + Integer.toString(i)); 	cell.setValue(ctNhomchinh);
								cell = cells.getCell("F" + Integer.toString(i)); 	cell.setValue(ttNhomchinh);
								cell = cells.getCell("G" + Integer.toString(i)); 	cell.setValue(ptNhomchinh);
								cell = cells.getCell("H" + Integer.toString(i)); 	cell.setValue(ctDonhang);
								cell = cells.getCell("I" + Integer.toString(i)); 	cell.setValue(ttDonhang);
								cell = cells.getCell("J" + Integer.toString(i)); 	cell.setValue(ptDonhang);
								cell = cells.getCell("K" + Integer.toString(i)); 	cell.setValue(ctNhomphu);
								cell = cells.getCell("L" + Integer.toString(i)); 	cell.setValue(ttNhomphu);
								cell = cells.getCell("M" + Integer.toString(i)); 	cell.setValue(ptNhomphu);

								cell = cells.getCell("N" + Integer.toString(i)); 	cell.setValue(ptKpi);
								cell = cells.getCell("O" + Integer.toString(i)); 	cell.setValue(luongtg);
								cell = cells.getCell("P" + Integer.toString(i)); 	cell.setValue(luonghq);
								cell = cells.getCell("Q" + Integer.toString(i)); 	cell.setValue(phucap);
								cell = cells.getCell("R" + Integer.toString(i)); 	cell.setValue(thuongSLTieuthu);
								cell = cells.getCell("S" + Integer.toString(i)); 	cell.setValue(bhxh);
								cell = cells.getCell("T" + Integer.toString(i)); 	cell.setValue(congdoan);
								cell = cells.getCell("U" + Integer.toString(i)); 	cell.setValue(thunhap);
								
								stt++;
								i++;
								
								if(stt == (NPP_SO_DDKD.get(nppId) + 1) )
								{
									cell = cells.getCell("A" + Integer.toString(i)); 	cell.setValue("       Tổng Cộng");
									
									cell = cells.getCell("E" + Integer.toString(i)); 	cell.setValue(ttNc);
									cell = cells.getCell("F" + Integer.toString(i)); 	cell.setValue(tdNc);
									cell = cells.getCell("G" + Integer.toString(i)); 	cell.setValue(thNc * 100 / ttNc);
									
									cell = cells.getCell("H" + Integer.toString(i)); 	cell.setValue(ttDh);
									cell = cells.getCell("I" + Integer.toString(i)); 	cell.setValue(tdDh);
									cell = cells.getCell("J" + Integer.toString(i)); 	cell.setValue(tdDh * 100 / ttDh);
									
									cell = cells.getCell("K" + Integer.toString(i)); 	cell.setValue(ttNp);
									cell = cells.getCell("L" + Integer.toString(i)); 	cell.setValue(tdNp);
									cell = cells.getCell("M" + Integer.toString(i)); 	cell.setValue(tdNp * 100 / ttNp );
									
									cell = cells.getCell("N" + Integer.toString(i)); 	cell.setValue(ttKpi / (stt - 1) );
									cell = cells.getCell("O" + Integer.toString(i)); 	cell.setValue(ttLtg);
									cell = cells.getCell("P" + Integer.toString(i)); 	cell.setValue(ttLcv);
									cell = cells.getCell("Q" + Integer.toString(i)); 	cell.setValue(ttPc);
									cell = cells.getCell("R" + Integer.toString(i)); 	cell.setValue(ttTsl);
									cell = cells.getCell("S" + Integer.toString(i)); 	cell.setValue(ttBh);
									cell = cells.getCell("T" + Integer.toString(i)); 	cell.setValue(ttCd);
									cell = cells.getCell("U" + Integer.toString(i)); 	cell.setValue(ttTn);
									
									i++;
								}
							}
						}
		
					}
					else /********************** GIAM SAT BAN HANG *************************/
					{
						
						kvGroup = rs.getString("kvTen");
						
						if( ! kvGroup.trim().equals(kvTen) ) 
						{
							System.out.println("1.In ra Khu vuc mucGSBH Khac dau tien...");
							
							cell = cells.getCell("A" + Integer.toString(i)); 	cell.setValue("Khu vực: " + kvGroup);
							kvTen = kvGroup;
							
							i++;
							
							String ten = rs.getString("ten");

							double nlvChitieu = rs.getDouble("nlvChitieu");
							double nlvThucte = rs.getDouble("nlvThucte");
							
							double ctNhomchinh = rs.getDouble("ctNhomChinh");
							double ttNhomchinh = rs.getDouble("ttNhomChinh");
							double ptNhomchinh = rs.getDouble("ptNhomchinh");
							
							double ctDonhang = rs.getDouble("ctSodonhang");
							double ttDonhang = rs.getDouble("ttSodonhang");
							double ptDonhang = rs.getDouble("ptSodonhang");
							
							double ctQuytrinh = rs.getDouble("ctQuytrinh");
							double ttQuytrinh = rs.getDouble("ttQuytrinh");
							double ptQuytrinh = rs.getDouble("ptQuytrinh");
							
							double ctNhomphu = rs.getDouble("ctNhomphu");
							double ttNhomphu = rs.getDouble("ttNhomphu");
							double ptNhomphu = rs.getDouble("ptNhomphu");
							
							luongcb = rs.getDouble("luongcb");
							ptluongtg = rs.getDouble("ptluongtg");
							ptluonghieuqua = rs.getDouble("ptluonghieuqua");
							
							
							double luongtg = 0;
							double ptKpi = 0;
							double luonghq = 0;
							double thunhap = 0;
							/*double phucap = 0;
							
							phucap = rs.getDouble("TienPhuCap");
							if(phucap > 0)
								phucap = phucap * (nlvThucte / nlvChitieu);*/
							
							double TienPhuCap_TheoNC = rs.getDouble("TienPhuCap_TheoNC");
							if(TienPhuCap_TheoNC > 0)
								TienPhuCap_TheoNC = TienPhuCap_TheoNC * (nlvThucte / nlvChitieu);
							
							double TienPhuCap_KoTheoNC = rs.getDouble("TienPhuCap_KoTheoNC");
							
							double phucap = TienPhuCap_TheoNC + TienPhuCap_KoTheoNC;
							
							
							double bhxh = (rs.getDouble("BHXH") / 100 ) * luongcb;
							double congdoan = 0;
							
							String tinhtrang = rs.getString("tinhtrang");
							if(tinhtrang.equals("0")) //Nhan vien chinh thuc
							{
								luongtg = luongcb * (nlvThucte / nlvChitieu) * (ptluongtg / 100);
								
								ptKpi = ( Math.round(rs.getDouble("PT_KPI") * 100) / 100.0  ) * ( nlvThucte / nlvChitieu );
								
								/**********************************/
								double tdnc = rs.getDouble("tdnc");
								System.out.println("____Thuc dat ngay cong: " + tdnc);
								
								if(nlvThucte < tdnc)
								{
									luonghq = ( luongcb * (ptluonghieuqua / 100) ) * (ptKpi / 100) * (nlvThucte / nlvChitieu);
								}
								else
								{
									luonghq = ( luongcb * (ptluonghieuqua / 100) ) * (ptKpi / 100);
								}
								/**********************************/
								
								//luonghq = ( luongcb * (ptluonghieuqua / 100) ) * (ptKpi / 100);
								
								congdoan = (rs.getDouble("congdoan") / 100 ) * (luongtg + luonghq + phucap);
								double chiphi = bhxh + congdoan; 
								
								thunhap = luongtg + luonghq + phucap - chiphi;
							}
							else
							{
								luongtg = luongcb * (ptluongtg / 100) * (nlvThucte / nlvChitieu);
								
								congdoan = (rs.getDouble("congdoan") / 100 ) * (luongtg + luonghq + phucap);
								double chiphi = bhxh + congdoan; 
								
								thunhap = ( (luongtg * 90) / 100 ) + phucap - chiphi;
							}
							
		            		
							cell = cells.getCell("A" + Integer.toString(i)); 	cell.setValue(ten);						
							cell = cells.getCell("B" + Integer.toString(i)); 	cell.setValue(luongcb);
							cell = cells.getCell("C" + Integer.toString(i)); 	cell.setValue(nlvThucte);
							
							cell = cells.getCell("D" + Integer.toString(i)); 	cell.setValue(ctNhomchinh);
							cell = cells.getCell("E" + Integer.toString(i)); 	cell.setValue(ttNhomchinh);
							cell = cells.getCell("F" + Integer.toString(i)); 	cell.setValue(ptNhomchinh);
							
							cell = cells.getCell("G" + Integer.toString(i)); 	cell.setValue(ctDonhang);
							cell = cells.getCell("H" + Integer.toString(i)); 	cell.setValue(ttDonhang);
							cell = cells.getCell("I" + Integer.toString(i)); 	cell.setValue(ptDonhang);
							
							cell = cells.getCell("J" + Integer.toString(i)); 	cell.setValue(ctQuytrinh);
							cell = cells.getCell("K" + Integer.toString(i)); 	cell.setValue(ttQuytrinh);
							cell = cells.getCell("L" + Integer.toString(i)); 	cell.setValue(ptQuytrinh);
							
							cell = cells.getCell("M" + Integer.toString(i)); 	cell.setValue(ctNhomphu);
							cell = cells.getCell("N" + Integer.toString(i)); 	cell.setValue(ttNhomphu);
							cell = cells.getCell("O" + Integer.toString(i)); 	cell.setValue(ptNhomphu);
							
							cell = cells.getCell("P" + Integer.toString(i)); 	cell.setValue(ptKpi);
							cell = cells.getCell("Q" + Integer.toString(i)); 	cell.setValue(luongtg);
							cell = cells.getCell("R" + Integer.toString(i)); 	cell.setValue(luonghq);
							cell = cells.getCell("S" + Integer.toString(i)); 	cell.setValue(phucap);
							cell = cells.getCell("T" + Integer.toString(i)); 	cell.setValue(bhxh);
							cell = cells.getCell("U" + Integer.toString(i)); 	cell.setValue(congdoan);
							cell = cells.getCell("V" + Integer.toString(i)); 	cell.setValue(thunhap);
							
						}
						else
						{
							System.out.println("1.In ra Khu vuc mucGSBH Giong nhau...");

							String ten = rs.getString("ten");

							double nlvChitieu = rs.getDouble("nlvChitieu");
							double nlvThucte = rs.getDouble("nlvThucte");
							
							double ctNhomchinh = rs.getDouble("ctNhomChinh");
							double ttNhomchinh = rs.getDouble("ttNhomChinh");
							double ptNhomchinh = rs.getDouble("ptNhomchinh");
							
							double ctDonhang = rs.getDouble("ctSodonhang");
							double ttDonhang = rs.getDouble("ttSodonhang");
							double ptDonhang = rs.getDouble("ptSodonhang");
							
							double ctQuytrinh = rs.getDouble("ctQuytrinh");
							double ttQuytrinh = rs.getDouble("ttQuytrinh");
							double ptQuytrinh = rs.getDouble("ptQuytrinh");
							
							double ctNhomphu = rs.getDouble("ctNhomphu");
							double ttNhomphu = rs.getDouble("ttNhomphu");
							double ptNhomphu = rs.getDouble("ptNhomphu");
							
							luongcb = rs.getDouble("luongcb");
							ptluongtg = rs.getDouble("ptluongtg");
							ptluonghieuqua = rs.getDouble("ptluonghieuqua");
							
							
							double luongtg = 0;
							double ptKpi = 0;
							double luonghq = 0;
							double thunhap = 0;
							/*double phucap = 0;
							
							phucap = rs.getDouble("TienPhuCap");
							if(phucap > 0)
								phucap = phucap * (nlvThucte / nlvChitieu);*/
							
							double TienPhuCap_TheoNC = rs.getDouble("TienPhuCap_TheoNC");
							if(TienPhuCap_TheoNC > 0)
								TienPhuCap_TheoNC = TienPhuCap_TheoNC * (nlvThucte / nlvChitieu);
							
							double TienPhuCap_KoTheoNC = rs.getDouble("TienPhuCap_KoTheoNC");
							
							double phucap = TienPhuCap_TheoNC + TienPhuCap_KoTheoNC;
							
							
							double bhxh = (rs.getDouble("BHXH") / 100 ) * luongcb;
							double congdoan = 0;
							
							String tinhtrang = rs.getString("tinhtrang");
							if(tinhtrang.equals("0")) //Nhan vien chinh thuc
							{
								luongtg = luongcb * (nlvThucte / nlvChitieu) * (ptluongtg / 100);
								
								ptKpi = ( Math.round(rs.getDouble("PT_KPI") * 100) / 100.0  ) * ( nlvThucte / nlvChitieu );
								
								/**********************************/
								double tdnc = rs.getDouble("tdnc");
								System.out.println("____Thuc dat ngay cong: " + tdnc);
								
								if(nlvThucte < tdnc)
								{
									luonghq = ( luongcb * (ptluonghieuqua / 100) ) * (ptKpi / 100) * (nlvThucte / nlvChitieu);
								}
								else
								{
									luonghq = ( luongcb * (ptluonghieuqua / 100) ) * (ptKpi / 100);
								}
								/**********************************/
								
								//luonghq = ( luongcb * (ptluonghieuqua / 100) ) * (ptKpi / 100);
								
								congdoan = (rs.getDouble("congdoan") / 100 ) * (luongtg + luonghq + phucap);
								double chiphi = bhxh + congdoan; 
								
								thunhap = luongtg + luonghq + phucap - chiphi;
							}
							else
							{
								luongtg = luongcb * (ptluongtg / 100) * (nlvThucte / nlvChitieu);
								
								congdoan = (rs.getDouble("congdoan") / 100 ) * (luongtg + luonghq + phucap);
								double chiphi = bhxh + congdoan;
								
								thunhap = ( (luongtg * 90) / 100 ) + phucap - chiphi;
							}
							
		            		
							cell = cells.getCell("A" + Integer.toString(i)); 	cell.setValue(ten);						
							cell = cells.getCell("B" + Integer.toString(i)); 	cell.setValue(luongcb);
							cell = cells.getCell("C" + Integer.toString(i)); 	cell.setValue(nlvThucte);
							
							cell = cells.getCell("D" + Integer.toString(i)); 	cell.setValue(ctNhomchinh);
							cell = cells.getCell("E" + Integer.toString(i)); 	cell.setValue(ttNhomchinh);
							cell = cells.getCell("F" + Integer.toString(i)); 	cell.setValue(ptNhomchinh);
							
							cell = cells.getCell("G" + Integer.toString(i)); 	cell.setValue(ctDonhang);
							cell = cells.getCell("H" + Integer.toString(i)); 	cell.setValue(ttDonhang);
							cell = cells.getCell("I" + Integer.toString(i)); 	cell.setValue(ptDonhang);
							
							cell = cells.getCell("J" + Integer.toString(i)); 	cell.setValue(ctQuytrinh);
							cell = cells.getCell("K" + Integer.toString(i)); 	cell.setValue(ttQuytrinh);
							cell = cells.getCell("L" + Integer.toString(i)); 	cell.setValue(ptQuytrinh);
							
							cell = cells.getCell("M" + Integer.toString(i)); 	cell.setValue(ctNhomphu);
							cell = cells.getCell("N" + Integer.toString(i)); 	cell.setValue(ttNhomphu);
							cell = cells.getCell("O" + Integer.toString(i)); 	cell.setValue(ptNhomphu);
							
							cell = cells.getCell("P" + Integer.toString(i)); 	cell.setValue(ptKpi);
							cell = cells.getCell("Q" + Integer.toString(i)); 	cell.setValue(luongtg);
							cell = cells.getCell("R" + Integer.toString(i)); 	cell.setValue(luonghq);
							cell = cells.getCell("S" + Integer.toString(i)); 	cell.setValue(phucap);
							cell = cells.getCell("T" + Integer.toString(i)); 	cell.setValue(bhxh);
							cell = cells.getCell("U" + Integer.toString(i)); 	cell.setValue(congdoan);
							cell = cells.getCell("V" + Integer.toString(i)); 	cell.setValue(thunhap);
						}
							
					}
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

	
	private boolean CreateStaticData(Workbook workbook, IKhoahuanluyenList obj, String muclay) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    
	    String query = "";
	    String sql = "";
	    
	    double luongcb = 0;
	    double ptluongtg = 0;
	    double ptluonghieuqua = 0;
	    double baohiemtu = 0;
	    double baohiemden = 0;
    	System.out.println("____Muc lay la: " + muclay);
    	query=getQuery(muclay, obj);
	    if(obj.getVungId().length() > 0)
	    	query += " and thongtinluong.vungId = '" + obj.getVungId() + "' ";
	    if(obj.getKvId().length() > 0)
	    	query += " and thongtinluong.kvId = '" + obj.getKvId() + "' ";
		System.out.println("1.Tinh luong SQL - Dang Chuan: " + query);
		ResultSet rs =db.get(query); 
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
					if(muclay.equals("0")) //DDKD
					{
						String vung = rs.getString("vungTen");
						String khuvuc = rs.getString("kvTen");
						String nppTen = rs.getString("nppTen");
						String ten = rs.getString("ten");
						String dienthoai = rs.getString("dienthoai");
					
						double nlvChitieu = rs.getDouble("nlvChitieu");
						double nlvThucte = rs.getDouble("nlvThucte");
						
						double ctNhomchinh = rs.getDouble("ctNhomChinh");
						double ttNhomchinh = rs.getDouble("ttNhomChinh");
						double ptNhomchinh = rs.getDouble("ptNhomchinh");
						
						double ctDonhang = rs.getDouble("ctSodonhang");
						double ttDonhang = rs.getDouble("ttSodonhang");
						double ptDonhang = rs.getDouble("ptSodonhang");
						
						double ctNhomphu = rs.getDouble("ctNhomphu");
						double ttNhomphu = rs.getDouble("ttNhomphu");
						double ptNhomphu = rs.getDouble("ptNhomphu");
						
						luongcb = rs.getDouble("luongcb");
						ptluongtg = rs.getDouble("ptluongtg");
						ptluonghieuqua = rs.getDouble("ptluonghieuqua");
						
						double luongtg = luongcb * (nlvThucte / nlvChitieu) * (ptluongtg / 100);

						double ptKpi = ( Math.round(rs.getDouble("PT_KPI") * 100) / 100.0 ) * ( nlvThucte / nlvChitieu ) ;
						//System.out.println("____KPI: " + ptKpi);
						
						double luonghq = 0;
						
						
						double tdnc = rs.getDouble("tdnc");
						System.out.println("____Thuc dat ngay cong 1: " + tdnc + " -- Ngay LV Thuc te: " + nlvThucte);
						
						/*****************************************/
						if(nlvThucte < tdnc)
						{
							luonghq = ( luongcb * (ptluonghieuqua / 100) ) * (ptKpi / 100) * (nlvThucte / nlvChitieu);
							
							//System.out.println("DDKD: " + ten + " __ Luong CB: " + luongcb + " -- PT luongHQ: " + ptluonghieuqua + " -- KPI: " + ptKpi + " -- Ngay Lam Thuc Te: " + nlvThucte + " -- Ngay lam viec chi tieu: " + nlvChitieu + " -- Luong HQ: " + luonghq  );
						}
						else
							luonghq = ( luongcb * (ptluonghieuqua / 100) ) * (ptKpi / 100);
						/*****************************************/
						
						
						double thuongSLTieuthu = rs.getDouble("thuongSLTieuthu");
						
						//double phucap = rs.getDouble("TienPhuCap");
						//if(phucap > 0)
							//phucap = phucap * (nlvThucte / nlvChitieu);
						
						double TienPhuCap_TheoNC = rs.getDouble("TienPhuCap_TheoNC");
						if(TienPhuCap_TheoNC > 0)
							TienPhuCap_TheoNC = TienPhuCap_TheoNC * (nlvThucte / nlvChitieu);
						
						double TienPhuCap_KoTheoNC = rs.getDouble("TienPhuCap_KoTheoNC");
						
						double phucap = TienPhuCap_TheoNC + TienPhuCap_KoTheoNC;
						
						double bhxh = (rs.getDouble("BHXH") / 100 ) * luongcb;
						double congdoan = (rs.getDouble("congdoan") / 100 ) * (luongtg + luonghq + thuongSLTieuthu + phucap);
						double chiphi = bhxh + congdoan;
						
						double thunhap = luongtg + luonghq + thuongSLTieuthu + phucap - chiphi;
	            		
						cell = cells.getCell("DA" + Integer.toString(i)); 	cell.setValue(vung);
						cell = cells.getCell("DB" + Integer.toString(i)); 	cell.setValue(khuvuc);
						cell = cells.getCell("DC" + Integer.toString(i)); 	cell.setValue(nppTen);
						cell = cells.getCell("DD" + Integer.toString(i)); 	cell.setValue(ten);
						cell = cells.getCell("DE" + Integer.toString(i)); 	cell.setValue(dienthoai);
						
						cell = cells.getCell("DF" + Integer.toString(i)); 	cell.setValue(luongcb);
						cell = cells.getCell("DG" + Integer.toString(i)); 	cell.setValue(nlvChitieu);
						cell = cells.getCell("DH" + Integer.toString(i)); 	cell.setValue(nlvThucte);
						cell = cells.getCell("DI" + Integer.toString(i)); 	cell.setValue(ctNhomchinh);
						cell = cells.getCell("DJ" + Integer.toString(i)); 	cell.setValue(ttNhomchinh);
						cell = cells.getCell("DK" + Integer.toString(i)); 	cell.setValue(ptNhomchinh);
						cell = cells.getCell("DL" + Integer.toString(i)); 	cell.setValue(ctDonhang);
						cell = cells.getCell("DM" + Integer.toString(i)); 	cell.setValue(ttDonhang);
						cell = cells.getCell("DN" + Integer.toString(i)); 	cell.setValue(ptDonhang);
						cell = cells.getCell("DO" + Integer.toString(i)); 	cell.setValue(ctNhomphu);
						cell = cells.getCell("DP" + Integer.toString(i)); 	cell.setValue(ttNhomphu);
						cell = cells.getCell("DQ" + Integer.toString(i)); 	cell.setValue(ptNhomphu);
						
						cell = cells.getCell("DR" + Integer.toString(i)); 	cell.setValue(ptluongtg);
						cell = cells.getCell("DS" + Integer.toString(i)); 	cell.setValue(luongtg);
						cell = cells.getCell("DT" + Integer.toString(i)); 	cell.setValue(ptKpi);
						cell = cells.getCell("DU" + Integer.toString(i)); 	cell.setValue(ptluonghieuqua);
						cell = cells.getCell("DV" + Integer.toString(i)); 	cell.setValue(luonghq);
						cell = cells.getCell("DW" + Integer.toString(i)); 	cell.setValue(thuongSLTieuthu);
						cell = cells.getCell("DX" + Integer.toString(i)); 	cell.setValue(phucap);
						cell = cells.getCell("DY" + Integer.toString(i)); 	cell.setValue(bhxh);
						cell = cells.getCell("DZ" + Integer.toString(i)); 	cell.setValue(congdoan);
						cell = cells.getCell("EA" + Integer.toString(i)); 	cell.setValue(thunhap);
					}
					else //GSBH
					{
						String vung = rs.getString("vungTen");
						String khuvuc = rs.getString("kvTen");
						String ten = rs.getString("ten");
						String dienthoai = rs.getString("dienthoai");
						
						double nlvChitieu = rs.getDouble("nlvChitieu");
						double nlvThucte = rs.getDouble("nlvThucte");
						
						double ctNhomchinh = rs.getDouble("ctNhomChinh");
						double ttNhomchinh = rs.getDouble("ttNhomChinh");
						double ptNhomchinh = rs.getDouble("ptNhomchinh");
						
						double ctDonhang = rs.getDouble("ctSodonhang");
						double ttDonhang = rs.getDouble("ttSodonhang");
						double ptDonhang = rs.getDouble("ptSodonhang");
						
						double ctQuytrinh = rs.getDouble("ctQuytrinh");
						double ttQuytrinh = rs.getDouble("ttQuytrinh");
						double ptQuytrinh = rs.getDouble("ptQuytrinh");
						
						double ctNhomphu = rs.getDouble("ctNhomphu");
						double ttNhomphu = rs.getDouble("ttNhomphu");
						double ptNhomphu = rs.getDouble("ptNhomphu");
						
						luongcb = rs.getDouble("luongcb");
						ptluongtg = rs.getDouble("ptluongtg");
						ptluonghieuqua = rs.getDouble("ptluonghieuqua");
						
						
						double luongtg = 0;
						double ptKpi = 0;
						double luonghq = 0;
						double thuongdoanhso = rs.getDouble("ThuongDoanhSo");
						double thuongvuotmuc=rs.getDouble("ThuongVuotMuc");
						double thunhap = 0;
						//double phucap = 0;
						
						double bhxh = 0;
						double congdoan = 0;
						
						/*phucap = rs.getDouble("TienPhuCap");
						if(phucap > 0)
							phucap = phucap * (nlvThucte / nlvChitieu);*/
						
						double TienPhuCap_TheoNC = rs.getDouble("TienPhuCap_TheoNC");
						if(TienPhuCap_TheoNC > 0)
							TienPhuCap_TheoNC = TienPhuCap_TheoNC * (nlvThucte / nlvChitieu);
						
						double TienPhuCap_KoTheoNC = rs.getDouble("TienPhuCap_KoTheoNC");
						
						double phucap = TienPhuCap_TheoNC + TienPhuCap_KoTheoNC;
						
						bhxh = (rs.getDouble("BHXH") / 100 ) * luongcb;
						
						String tinhtrang = rs.getString("tinhtrang");
						if(tinhtrang.equals("0")) //Nhan vien chinh thuc
						{
							luongtg = luongcb * (nlvThucte / nlvChitieu) * (ptluongtg / 100);
							
							ptKpi = ( Math.round(rs.getDouble("PT_KPI") * 100) / 100.0 ) * ( nlvThucte / nlvChitieu );
							
							
							/**********************************/
							double tdnc = rs.getDouble("tdnc");
							//System.out.println("____Thuc dat ngay cong 2: " + tdnc);
							
							if(nlvThucte < tdnc)
							{
								luonghq = ( luongcb * (ptluonghieuqua / 100) ) * (ptKpi / 100) * (nlvThucte / nlvChitieu);
								
								//System.out.println("Luong CB: " + luongcb + " -- PT luongHQ: " + ptluonghieuqua + " -- KPI: " + ptKpi + " -- Ngay Lam Thuc Te: " + nlvThucte + " -- Ngay lam viec chi tieu: " + nlvChitieu + " -- Luong HQ: " + luonghq  );
							}
							else
							{
								luonghq = ( luongcb * (ptluonghieuqua / 100) ) * (ptKpi / 100);
							}
							/**********************************/
							
							
							thunhap = luongtg + luonghq + phucap + thuongdoanhso+thuongvuotmuc;
							
							congdoan = (rs.getDouble("congdoan") / 100 ) * thunhap;
							double chiphi = bhxh + congdoan;
							
							thunhap = thunhap - chiphi;
							
						}
						else
						{
							luongtg = luongcb * (ptluongtg / 100) * (nlvThucte / nlvChitieu);

							thunhap = ( (luongtg * 90) / 100 ) + phucap + thuongdoanhso+thuongvuotmuc;
							
							congdoan = (rs.getDouble("congdoan") / 100 ) * thunhap;
							double chiphi = bhxh + congdoan;
							
							thunhap = thunhap - chiphi;
						}
						
	            		
						cell = cells.getCell("DA" + Integer.toString(i)); 	cell.setValue(vung);
						cell = cells.getCell("DB" + Integer.toString(i)); 	cell.setValue(khuvuc);
						cell = cells.getCell("DC" + Integer.toString(i)); 	cell.setValue(ten);
						cell = cells.getCell("DD" + Integer.toString(i)); 	cell.setValue(dienthoai);
						
						cell = cells.getCell("DE" + Integer.toString(i)); 	cell.setValue(luongcb);
						cell = cells.getCell("DF" + Integer.toString(i)); 	cell.setValue(nlvChitieu);
						cell = cells.getCell("DG" + Integer.toString(i)); 	cell.setValue(nlvThucte);
						
						cell = cells.getCell("DH" + Integer.toString(i)); 	cell.setValue(ctNhomchinh);
						cell = cells.getCell("DI" + Integer.toString(i)); 	cell.setValue(ttNhomchinh);
						cell = cells.getCell("DJ" + Integer.toString(i)); 	cell.setValue(ptNhomchinh);
						
						cell = cells.getCell("DK" + Integer.toString(i)); 	cell.setValue(ctDonhang);
						cell = cells.getCell("DL" + Integer.toString(i)); 	cell.setValue(ttDonhang);
						cell = cells.getCell("DM" + Integer.toString(i)); 	cell.setValue(ptDonhang);
						
						cell = cells.getCell("DN" + Integer.toString(i)); 	cell.setValue(ctQuytrinh);
						cell = cells.getCell("DO" + Integer.toString(i)); 	cell.setValue(ttQuytrinh);
						cell = cells.getCell("DP" + Integer.toString(i)); 	cell.setValue(ptQuytrinh);
						
						cell = cells.getCell("DQ" + Integer.toString(i)); 	cell.setValue(ctNhomphu);
						cell = cells.getCell("DR" + Integer.toString(i)); 	cell.setValue(ttNhomphu);
						cell = cells.getCell("DS" + Integer.toString(i)); 	cell.setValue(ptNhomphu);
						
						cell = cells.getCell("DT" + Integer.toString(i)); 	cell.setValue(ptluongtg);
						cell = cells.getCell("DU" + Integer.toString(i)); 	cell.setValue(luongtg);
						cell = cells.getCell("DV" + Integer.toString(i)); 	cell.setValue(ptKpi);
						cell = cells.getCell("DW" + Integer.toString(i)); 	cell.setValue(ptluonghieuqua);
						cell = cells.getCell("DX" + Integer.toString(i)); 	cell.setValue(luonghq);
						cell = cells.getCell("DY" + Integer.toString(i)); 	cell.setValue(phucap);
						cell = cells.getCell("DZ" + Integer.toString(i)); 	cell.setValue(thuongdoanhso);
						cell = cells.getCell("EA" + Integer.toString(i)); 	cell.setValue(thuongvuotmuc);
						cell = cells.getCell("EB" + Integer.toString(i)); 	cell.setValue(bhxh);
						cell = cells.getCell("EC" + Integer.toString(i)); 	cell.setValue(congdoan);
						cell = cells.getCell("ED" + Integer.toString(i)); 	cell.setValue(thunhap);
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
				e.printStackTrace();
				System.out.println("115.Error: " + e.getMessage());
				e.printStackTrace();
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
	
	private boolean CreateStaticData_ToTrinh(Workbook workbook, IKhoahuanluyenList obj, String muclay) throws Exception
	{
		System.out.println("___Dinh dang to trinh___");
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet =null;
	    Cells cells =null;
	    String query = "";
	    double luongcb = 0;
	    double ptluongtg = 0;
	    double ptluonghieuqua = 0;
	    double baohiemtu = 0;
	    double baohiemden = 0;
    	System.out.println("____Muc lay la: " + muclay);
    	query=getQuery(muclay, obj);
    	  if(obj.getVungId().length() > 0)
  	    	query += " and thongtinluong.vungId = '" + obj.getVungId() + "' ";
  	    if(obj.getKvId().length() > 0)
  	    	query += " and thongtinluong.kvId = '" + obj.getKvId() + "' ";
	    if(muclay.indexOf("0")>=0)
	    	query+=" order by thongtinluong.nppId ";
		System.out.println("1.Tinh luong SQL - Dang to Trinh: " + query);
		ResultSet rs = db.get(query);
		int i = 0;
		i=9;
		int stt=1;
		int sheet_Index=-1;
		String nppPrev="";
		Hashtable<String,String> sheetNppIds=new Hashtable<String,String>();
		if(rs!=null)
		{
			try 
			{			
				Cell cell = null;
				while(rs.next())
				{
					if(muclay.equals("0")) //DDKD
					{
						String vung = rs.getString("vungTen");
						String khuvuc = rs.getString("kvTen");
						String	nppTen = rs.getString("nppTen");
						String ten = rs.getString("ten");
						String dienthoai = rs.getString("dienthoai");
						String 	nppId=rs.getString("nppId");
						
						double nlvChitieu = rs.getDouble("nlvChitieu");
						double nlvThucte = rs.getDouble("nlvThucte");
						
						double ctNhomchinh = rs.getDouble("ctNhomChinh");
						double ttNhomchinh = rs.getDouble("ttNhomChinh");
						double ptNhomchinh = rs.getDouble("ptNhomchinh");
						
						double ctDonhang = rs.getDouble("ctSodonhang");
						double ttDonhang = rs.getDouble("ttSodonhang");
						double ptDonhang = rs.getDouble("ptSodonhang");
						
						double ctNhomphu = rs.getDouble("ctNhomphu");
						double ttNhomphu = rs.getDouble("ttNhomphu");
						double ptNhomphu = rs.getDouble("ptNhomphu");
						
						luongcb = rs.getDouble("luongcb");
						ptluongtg = rs.getDouble("ptluongtg");
						ptluonghieuqua = rs.getDouble("ptluonghieuqua");
						
						double luongtg = luongcb * (nlvThucte / nlvChitieu) * (ptluongtg / 100);

						double ptKpi = ( Math.round(rs.getDouble("PT_KPI") * 100) / 100.0 ) * ( nlvThucte / nlvChitieu ) ;
						//System.out.println("____KPI: " + ptKpi);
						
						double luonghq = 0;
						
						
						double tdnc = rs.getDouble("tdnc");
					//	System.out.println("____Thuc dat ngay cong 1: " + tdnc + " -- Ngay LV Thuc te: " + nlvThucte);
						/*****************************************/
						if(nlvThucte < tdnc)
						{
							luonghq = ( luongcb * (ptluonghieuqua / 100) ) * (ptKpi / 100) * (nlvThucte / nlvChitieu);
							
							//System.out.println("DDKD: " + ten + " __ Luong CB: " + luongcb + " -- PT luongHQ: " + ptluonghieuqua + " -- KPI: " + ptKpi + " -- Ngay Lam Thuc Te: " + nlvThucte + " -- Ngay lam viec chi tieu: " + nlvChitieu + " -- Luong HQ: " + luonghq  );
						}
						else
							luonghq = ( luongcb * (ptluonghieuqua / 100) ) * (ptKpi / 100);
						/*****************************************/
						
						
						double thuongSLTieuthu = rs.getDouble("thuongSLTieuthu");
						
						//double phucap = rs.getDouble("TienPhuCap");
						//if(phucap > 0)
							//phucap = phucap * (nlvThucte / nlvChitieu);
						
						double TienPhuCap_TheoNC = rs.getDouble("TienPhuCap_TheoNC");
						if(TienPhuCap_TheoNC > 0)
							TienPhuCap_TheoNC = TienPhuCap_TheoNC * (nlvThucte / nlvChitieu);
						
						double TienPhuCap_KoTheoNC = rs.getDouble("TienPhuCap_KoTheoNC");
						
						double phucap = TienPhuCap_TheoNC + TienPhuCap_KoTheoNC;
						
						double bhxh = (rs.getDouble("BHXH") / 100 ) * luongcb;
						double congdoan = (rs.getDouble("congdoan") / 100 ) * (luongtg + luonghq + thuongSLTieuthu + phucap);
						double chiphi = bhxh + congdoan;
						
						double thunhap = luongtg + luonghq + thuongSLTieuthu + phucap - chiphi;
						if(nppPrev.equals(nppId))
						{
							//System.out.println("[Same][nppPrev]"+nppPrev+"[nppId]"+nppId+"[nppTen]"+nppTen);
							
						    worksheet = worksheets.getSheet(sheet_Index);
						    cells=worksheet.getCells();
						    cell = cells.getCell("A" + Integer.toString(i)); 	cell.setValue(stt);
					     	cell = cells.getCell("B" + Integer.toString(i)); 	cell.setValue(ten);
							cell = cells.getCell("C" + Integer.toString(i)); 	cell.setValue(luongcb);
							cell = cells.getCell("D" + Integer.toString(i)); 	cell.setValue(nlvThucte);
							cell = cells.getCell("E" + Integer.toString(i)); 	cell.setValue(ctNhomchinh);
							cell = cells.getCell("F" + Integer.toString(i)); 	cell.setValue(ttNhomchinh);
							cell = cells.getCell("G" + Integer.toString(i)); 	cell.setValue(ptNhomchinh);
							cell = cells.getCell("H" + Integer.toString(i)); 	cell.setValue(ctDonhang);
							cell = cells.getCell("I" + Integer.toString(i)); 	cell.setValue(ttDonhang);
							cell = cells.getCell("J" + Integer.toString(i)); 	cell.setValue(ptDonhang);
							cell = cells.getCell("K" + Integer.toString(i)); 	cell.setValue(ctNhomphu);
							cell = cells.getCell("L" + Integer.toString(i)); 	cell.setValue(ttNhomphu);
							cell = cells.getCell("M" + Integer.toString(i)); 	cell.setValue(ptNhomphu);

							cell = cells.getCell("N" + Integer.toString(i)); 	cell.setValue(ptKpi);
							cell = cells.getCell("O" + Integer.toString(i)); 	cell.setValue(luongtg);
							cell = cells.getCell("P" + Integer.toString(i)); 	cell.setValue(luonghq);
							cell = cells.getCell("Q" + Integer.toString(i)); 	cell.setValue(phucap);
							cell = cells.getCell("R" + Integer.toString(i)); 	cell.setValue(thuongSLTieuthu);
							cell = cells.getCell("S" + Integer.toString(i)); 	cell.setValue(bhxh);
							cell = cells.getCell("T" + Integer.toString(i)); 	cell.setValue(congdoan);
							cell = cells.getCell("U" + Integer.toString(i)); 	cell.setValue(thunhap);
							setBorder(cell,cells,i-1,i-1,0,20);
							Style style;
							for(int j=1;j<=20;j++)
							{
								cell=cells.getCell(i-1, j);
								style=cell.getStyle();
								style.setNumber(3);
								cell.setStyle(style);
							}
						
						}else
						{
						//	System.out.println("[Diff][nppPrev]"+nppPrev+"[nppId]"+nppId+"[nppTen]"+nppTen);
							sheet_Index++;
							i=9;
							stt=1;
							nppPrev=rs.getString("nppId");;
							worksheets.addSheet();
							worksheet = worksheets.getSheet(sheet_Index);
							cells=worksheet.getCells();
							for(int j = 1; j < 21; j++)
								cells.setColumnWidth(j, 10.0f);	
							if(!sheetNppIds.containsKey(nppId)){
								CreateStaticHeader_Npp(workbook, obj, muclay, nppTen, sheet_Index);
								System.out.println("___Sheet___"+nppId);
							}
							sheetNppIds.put(nppId, nppTen);
						    cell = cells.getCell("A" + Integer.toString(i)); 	cell.setValue(stt);
					     	cell = cells.getCell("B" + Integer.toString(i)); 	cell.setValue(ten);
							cell = cells.getCell("C" + Integer.toString(i)); 	cell.setValue(luongcb);
							cell = cells.getCell("D" + Integer.toString(i)); 	cell.setValue(nlvThucte);
							cell = cells.getCell("E" + Integer.toString(i)); 	cell.setValue(ctNhomchinh);
							cell = cells.getCell("F" + Integer.toString(i)); 	cell.setValue(ttNhomchinh);
							cell = cells.getCell("G" + Integer.toString(i)); 	cell.setValue(ptNhomchinh);
							cell = cells.getCell("H" + Integer.toString(i)); 	cell.setValue(ctDonhang);
							cell = cells.getCell("I" + Integer.toString(i)); 	cell.setValue(ttDonhang);
							cell = cells.getCell("J" + Integer.toString(i)); 	cell.setValue(ptDonhang);
							cell = cells.getCell("K" + Integer.toString(i)); 	cell.setValue(ctNhomphu);
							cell = cells.getCell("L" + Integer.toString(i)); 	cell.setValue(ttNhomphu);
							cell = cells.getCell("M" + Integer.toString(i)); 	cell.setValue(ptNhomphu);

							cell = cells.getCell("N" + Integer.toString(i)); 	cell.setValue(ptKpi);
							cell = cells.getCell("O" + Integer.toString(i)); 	cell.setValue(luongtg);
							cell = cells.getCell("P" + Integer.toString(i)); 	cell.setValue(luonghq);
							cell = cells.getCell("Q" + Integer.toString(i)); 	cell.setValue(phucap);
							cell = cells.getCell("R" + Integer.toString(i)); 	cell.setValue(thuongSLTieuthu);
							cell = cells.getCell("S" + Integer.toString(i)); 	cell.setValue(bhxh);
							cell = cells.getCell("T" + Integer.toString(i)); 	cell.setValue(congdoan);
							cell = cells.getCell("U" + Integer.toString(i)); 	cell.setValue(thunhap);
							setBorder(cell,cells,i-1,i-1,0,20);
							Style style;
							for(int j=1;j<=20;j++)
							{
								cell=cells.getCell(i-1, j);
								style=cell.getStyle();
								style.setNumber(3);
								cell.setStyle(style);
							}
						}
					}
					else //GSBH
					{
						String vung = rs.getString("vungTen");
						String khuvuc = rs.getString("kvTen");
						String ten = rs.getString("ten");
						String dienthoai = rs.getString("dienthoai");
											
						double nlvChitieu = rs.getDouble("nlvChitieu");
						double nlvThucte = rs.getDouble("nlvThucte");
						
						double ctNhomchinh = rs.getDouble("ctNhomChinh");
						double ttNhomchinh = rs.getDouble("ttNhomChinh");
						double ptNhomchinh = rs.getDouble("ptNhomchinh");
						
						double ctDonhang = rs.getDouble("ctSodonhang");
						double ttDonhang = rs.getDouble("ttSodonhang");
						double ptDonhang = rs.getDouble("ptSodonhang");
						
						double ctQuytrinh = rs.getDouble("ctQuytrinh");
						double ttQuytrinh = rs.getDouble("ttQuytrinh");
						double ptQuytrinh = rs.getDouble("ptQuytrinh");
						
						double ctNhomphu = rs.getDouble("ctNhomphu");
						double ttNhomphu = rs.getDouble("ttNhomphu");
						double ptNhomphu = rs.getDouble("ptNhomphu");
						
						luongcb = rs.getDouble("luongcb");
						ptluongtg = rs.getDouble("ptluongtg");
						ptluonghieuqua = rs.getDouble("ptluonghieuqua");
						
						
						double luongtg = 0;
						double ptKpi = 0;
						double luonghq = 0;
						double thuongdoanhso = rs.getDouble("ThuongDoanhSo");
						double thuongvuotmuc=rs.getDouble("ThuongVuotMuc");
						double thunhap = 0;
						//double phucap = 0;
						
						double bhxh = 0;
						double congdoan = 0;
						
						/*phucap = rs.getDouble("TienPhuCap");
						if(phucap > 0)
							phucap = phucap * (nlvThucte / nlvChitieu);*/
						
						double TienPhuCap_TheoNC = rs.getDouble("TienPhuCap_TheoNC");
						if(TienPhuCap_TheoNC > 0)
							TienPhuCap_TheoNC = TienPhuCap_TheoNC * (nlvThucte / nlvChitieu);
						
						double TienPhuCap_KoTheoNC = rs.getDouble("TienPhuCap_KoTheoNC");
						
						double phucap = TienPhuCap_TheoNC + TienPhuCap_KoTheoNC;
						
						bhxh = (rs.getDouble("BHXH") / 100 ) * luongcb;
						
						String tinhtrang = rs.getString("tinhtrang");
						if(tinhtrang.equals("0")) //Nhan vien chinh thuc
						{
							luongtg = luongcb * (nlvThucte / nlvChitieu) * (ptluongtg / 100);
							
							ptKpi = ( Math.round(rs.getDouble("PT_KPI") * 100) / 100.0 ) * ( nlvThucte / nlvChitieu );
							
							
							/**********************************/
							double tdnc = rs.getDouble("tdnc");
							//System.out.println("____Thuc dat ngay cong 2: " + tdnc);
							
							if(nlvThucte < tdnc)
							{
								luonghq = ( luongcb * (ptluonghieuqua / 100) ) * (ptKpi / 100) * (nlvThucte / nlvChitieu);
								
								//System.out.println("Luong CB: " + luongcb + " -- PT luongHQ: " + ptluonghieuqua + " -- KPI: " + ptKpi + " -- Ngay Lam Thuc Te: " + nlvThucte + " -- Ngay lam viec chi tieu: " + nlvChitieu + " -- Luong HQ: " + luonghq  );
							}
							else
							{
								luonghq = ( luongcb * (ptluonghieuqua / 100) ) * (ptKpi / 100);
							}
							/**********************************/
							
							
							thunhap = luongtg + luonghq + phucap + thuongdoanhso+thuongvuotmuc;
							
							congdoan = (rs.getDouble("congdoan") / 100 ) * thunhap;
							double chiphi = bhxh + congdoan;
							
							thunhap = thunhap - chiphi;
							
						}
						else
						{
							luongtg = luongcb * (ptluongtg / 100) * (nlvThucte / nlvChitieu);

							thunhap = ( (luongtg * 90) / 100 ) + phucap + thuongdoanhso+thuongvuotmuc;
							
							congdoan = (rs.getDouble("congdoan") / 100 ) * thunhap;
							double chiphi = bhxh + congdoan;
							
							thunhap = thunhap - chiphi;
						}
						
				    worksheet = worksheets.getSheet(0);
				    cells=worksheet.getCells();
				    for(int j = 1; j < 24; j++)
						cells.setColumnWidth(j, 10.0f);		
					cell = cells.getCell("A" + Integer.toString(i)); 	cell.setValue(ten);						
					cell = cells.getCell("B" + Integer.toString(i)); 	cell.setValue(luongcb);
					cell = cells.getCell("C" + Integer.toString(i)); 	cell.setValue(nlvThucte);
					
					cell = cells.getCell("D" + Integer.toString(i)); 	cell.setValue(ctNhomchinh);
					cell = cells.getCell("E" + Integer.toString(i)); 	cell.setValue(ttNhomchinh);
					cell = cells.getCell("F" + Integer.toString(i)); 	cell.setValue(ptNhomchinh);
					
					cell = cells.getCell("G" + Integer.toString(i)); 	cell.setValue(ctDonhang);
					cell = cells.getCell("H" + Integer.toString(i)); 	cell.setValue(ttDonhang);
					cell = cells.getCell("I" + Integer.toString(i)); 	cell.setValue(ptDonhang);
					
					cell = cells.getCell("J" + Integer.toString(i)); 	cell.setValue(ctQuytrinh);
					cell = cells.getCell("K" + Integer.toString(i)); 	cell.setValue(ttQuytrinh);
					cell = cells.getCell("L" + Integer.toString(i)); 	cell.setValue(ptQuytrinh);
					
					cell = cells.getCell("M" + Integer.toString(i)); 	cell.setValue(ctNhomphu);
					cell = cells.getCell("N" + Integer.toString(i)); 	cell.setValue(ttNhomphu);
					cell = cells.getCell("O" + Integer.toString(i)); 	cell.setValue(ptNhomphu);
					
					cell = cells.getCell("P" + Integer.toString(i)); 	cell.setValue(ptKpi);
					cell = cells.getCell("Q" + Integer.toString(i)); 	cell.setValue(luongtg);
					cell = cells.getCell("R" + Integer.toString(i)); 	cell.setValue(luonghq);
					cell= cells.getCell("S" + Integer.toString(i)); 	cell.setValue(thuongdoanhso);
					cell= cells.getCell("T" + Integer.toString(i)); 	cell.setValue(thuongvuotmuc);
					cell = cells.getCell("U" + Integer.toString(i)); 	cell.setValue(phucap);
					cell = cells.getCell("V" + Integer.toString(i)); 	cell.setValue(bhxh);
					cell = cells.getCell("W" + Integer.toString(i)); 	cell.setValue(congdoan);
					cell = cells.getCell("X" + Integer.toString(i)); 	cell.setValue(thunhap);	
					setBorder(cell,cells,i-1,i-1,0,23);
					Style style;
					for(int j=1;j<=24;j++)
					{
						cell=cells.getCell(i-1, j);
						style=cell.getStyle();
						style.setNumber(3);
						cell.setStyle(style);
					}
				}
				i++;
				stt++;
			} 
				if(rs!=null)
					rs.close();
				if(db != null) 
					db.shutDown();
				if(i==8)
				{
					throw new Exception("Khong co bao cao trong thoi gian nay...");
				}
		}
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("115.Error: " + e.getMessage());
				return false;
			}
		}
		return true;
	}
	
	private void setBorder(Cell cell,Cells cells,int startRow,int endRow,int startColumn,int endColumn)
	{
		Style style;
		style=cell.getStyle();
	    style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
	    cells.setRangeStyle(startRow, endRow,startColumn,endColumn, style);
	}

	public static void main(String [] arg)
	{
		String str="10001,1222,";
		if(str.indexOf("NhậnViên2")>=0)
			System.out.print("Co roi");
		else 
			System.out.print("Chua co roi");
		System.out.println(str.indexOf("10001"));
			
	}

	
	String getQuery(String muclay,IKhoahuanluyenList obj)
	{
		String query="";
		String thang = obj.getTungay();
    	if(thang.length() < 2)
    		thang = "0" + thang;
    	String ngaydh = obj.getDenngay() + "-" + thang;
		if(muclay.equals("0")) //Lay theo Dai Dien Kinh Doanh
	    {
    		query = 
    				"select thongtinluong.*, tinh_thunhap.luongcb, chitieunlv.nlvChitieu, chitieunlv.nlvThucte,   \n "  +
    				"		isnull(chitieuNC.ctNhomChinh, 0) as ctNhomChinh, isnull(chitieuNC.ttNhomChinh, 0) as ttNhomChinh,  \n "  +
    				"		case when isnull(chitieuNC.ctNhomChinh, 0) = 0 then 0 else isnull(chitieuNC.ttNhomChinh, 0) * 100 / isnull(chitieuNC.ctNhomChinh, 0) end as ptNhomChinh,  \n "  +
    				"		isnull(chitieuNP.ctNhomPhu, 0) as ctNhomPhu, isnull(chitieuNP.ttNhomPhu, 0) as ttNhomPhu,  \n "  +
    				"		case when isnull(chitieuNP.ctNhomPhu, 0) = 0 then 0 else isnull(chitieuNP.ttNhomPhu, 0) * 100 / isnull(chitieuNP.ctNhomPhu, 0) end as ptNhomPhu,  \n "  +
    				"		isnull(chitieuDH.ctSoDonHang, 0) as ctSoDonHang, isnull(chitieuDH.ttSodonhang, 0) as ttSodonhang,  \n "  +
    				"	case when isnull(chitieuDH.ctSoDonHang, 0) = 0 then 0 else isnull(chitieuDH.ttSodonhang, 0) * 100 / isnull(chitieuDH.ctSoDonHang, 0) end as ptSodonhang,  \n "  +
    				"	ISNULL(tongphucap.tpcTheoNC, 0) as TienPhuCap_TheoNC, ISNULL(tongphucap.tpcKoTheoNC, 0) as TienPhuCap_KoTheoNC   \n "  +
    				"from  \n "  +
    				"(  \n "  +
    				"	select v.pk_seq as vungId, v.ten as vungTen, kv.ten as kvTen,npp.DiaChi, npp.pk_seq as nppId, npp.ten as nppTen, ddkd.ten,npp.SOTAIKHOANNH as SoTaiKhoan,npp.chutaikhoan, ddkd.dienthoai, KPI.kvId, KPI.ddkdId,  ptluongtg, ptluonghieuqua, baohiem as bhxh, congdoan, tdnc,       \n "  +
    				"		SUM(Tongthuong) as thuongSLtieuthu, 	   \n "  +
    				"		case when  SUM(PT_Dat) < TrongSoKPI.BaoHiem then BaoHiem  \n "  +
    				"		else SUM(PT_Dat) end as PT_KPI   \n "  +
    				"	from   \n "  +
    				"	(   \n "  +
    				"		select TrongSoCoThuongVM.kvId, TrongSoCoThuongVM.ddkdId, SUM(PT_Dat * TrongSo / 100) as PT_Dat,    \n "  +
    				"				case when SUM(tvm) > 0 then SUM(tvm)    \n "  +
    				"				else  IsNULL( (   \n "  +
    				"								select b.thuong   \n "  +
    				"								from THUONGVUOTMUC_CHUCVU_DDKD a inner join THUONGVUOTMUC_CHUCVU_TIEUCHI b on a.thuongvuotmuc_chucvu_fk = b.thuongvuotmuc_chucvu_fk   \n "  +
    				"								where a.thuongvuotmuc_chucvu_fk in ( select pk_seq from THUONGVUOTMUC_CHUCVU where thuongvuotmuc_fk in ( select pk_seq from ThuongVuotMuc where thang = '"+obj.getTungay()+"' and nam = '"+obj.getDenngay()+"' and trangthai = '1' ) )   \n "  +
    				"									and a.ddkd_fk = TrongSoCoThuongVM.ddkdId  and b.tumuc <= SUM(PT_Dat * TrongSo / 100) and SUM(PT_Dat * TrongSo / 100) < b.denmuc     \n "  +
    				"							), 0 ) * SUM(TrongSoCoThuongVM.SLTT) end as TongThuong     \n "  +
    				"		from   \n "  +
    				"		(   \n "  +
    				"			select TrongSoNSP.kvId, TrongSoNSP.ddkdId, TrongSoNSP.nhomthuongId, TrongSoNSP.SLTT, TrongSoNSP.PT_Dat, TrongSoNSP.TRONGSO,    \n "  +
    				"				IsNULL( (     \n "  +
    				"							select b.thuong   \n "  +
    				"							from THUONGVUOTMUC_CHUCVU_DDKD a inner join THUONGVUOTMUC_CHUCVU_TIEUCHI b on a.thuongvuotmuc_chucvu_fk = b.thuongvuotmuc_chucvu_fk   \n "  +
    				"							where a.thuongvuotmuc_chucvu_fk in ( select pk_seq from THUONGVUOTMUC_CHUCVU where thuongvuotmuc_fk in ( select pk_seq from ThuongVuotMuc where thang = '"+obj.getTungay()+"' and nam = '"+obj.getDenngay()+"' and trangthai = '1' ) )   \n "  +
    				"								and a.ddkd_fk = TrongSoNSP.ddkdId	and b.tumuc <= TrongSoNSP.PT_Dat and TrongSoNSP.PT_Dat < b.denmuc and b.nhomthuong_fk = TrongSoNSP.nhomthuongId   \n "  +
    				"						), 0) * TrongSoNSP.SLTT  as tvm     \n "  +
    				"			from    \n "  +
    				"			(   \n "  +
    				"				select  chitieudera.kvId, chitieudera.ddkdId, chitieudera.nhomthuongId, thucdat.sltt,     \n "  +
    				"					case when (  ( thucdat.sltt / chitieudera.chitieu ) * 100 ) < thuongvuotmuc.mucbaohiem then thuongvuotmuc.mucbaohiem     \n "  +
    				"					else ( thucdat.sltt / chitieudera.chitieu ) * 100 end as PT_Dat,    \n "  +
    				"					case thucdat.loainhom when 'Nhom Chinh' then 'CT01' else 'CT02' end as ma, thuongvuotmuc.TRONGSO	   \n "  +
    				"				from    \n "  +
    				"				(    \n "  +
    				"					select c.khuvuc_fk as kvId, b.ddkd_fk as ddkdId, nt.pk_seq as nhomthuongId, sum(d.chitieu) as Chitieu     \n "  +
    				"					from chitieunpp a inner join  Chitieunpp_ddkd b on a.pk_seq = b.chitieunpp_fk    \n "  +
    				"						inner join NhaPhanPhoi c on a.nhapp_fk = c.pk_seq      \n "  +
    				"						inner join ChitieuNPP_DDKD_NHOMSP d on d.ddkd_fk = b.ddkd_fk and d.chitieunpp_fk = a.pk_seq     \n "  +
    				"						inner join NHOMSANPHAM NSP on NSP.pk_seq = d.nhomsanpham_fk     \n "  +
    				"						inner join Nhomthuong_NhomSP nt_nsp on nt_nsp.NhomSanPham_fk = NSP.pk_seq     \n "  +
    				"						inner join Nhomthuong nt on nt_nsp.nhomthuong_fk = nt.pk_seq     \n "  +
    				"					where a.thang = '"+obj.getTungay()+"' and a.nam = '"+obj.getDenngay()+"' and a.trangthai = '1'  and nt.trangthai = '1' and NSP.loainhom = '3'    \n "  +
    				"					group by c.khuvuc_fk, b.ddkd_fk, nt.pk_seq   \n "  +
    				"				)   \n "  +
    				"				chitieudera inner join    \n "  +
    				"				(    \n "  +
    				"					select npp.khuvuc_fk as kvId, dh.ddkd_fk as ddkdId, nt.pk_seq as nhomthuongId,      \n "  +
    				"						case when nt.loai = 1 then 'Nhom Chinh' else 'Nhom Con Lai' end as LoaiNhom, sum( a.soluong * isnull(sp.trongluong, 0) ) as SLTT       \n "  +
    				"					from   donhang_sanpham a inner join nhomsanpham_sanpham b on a.sanpham_fk = b.sp_fk      \n "  +
    				"						inner join nhomthuong_nhomsp c on b.nsp_fk = c.nhomsanpham_fk       \n "  +
    				"						inner join NHOMSANPHAM NSP on NSP.pk_seq = b.nsp_fk      \n "  +
    				"						inner join Nhomthuong nt on c.nhomthuong_fk = nt.pk_seq      \n "  +
    				"						inner join TrongSoKPI_ChiTiet e on e.nhomthuong_fk = nt.pk_seq      \n "  +
    				"						inner join TrongSoKPI ts on e.trongsokpi_fk = ts.pk_seq \n "  +
    				"						inner join TRONGSOKPI_NPP tsNpp on tsNpp.TRONGSOKPI_FK=ts.PK_SEQ       \n "  +
    				"						inner join donhang dh on a.donhang_fk = dh.pk_seq      \n "  +
    				"						inner join nhaphanphoi npp on dh.npp_fk = npp.pk_seq  and npp.PK_SEQ=tsNpp.NPP_FK\n "  +
    				"						inner join sanpham sp on a.sanpham_fk = sp.pk_seq       \n "  +
    				"					where dh.ngaynhap like '"+ngaydh+"%' and dh.trangthai = '1' and nt.trangthai = '1' and NSP.loainhom = '3'     \n "  +
    				"						and e.nhomthuong_fk is not null  and ts.chucvu = 'SR'      \n "  +
    				"						and ts.tinhthunhap_fk in ( select pk_seq from TinhThuNhap  where trangthai = '1' and thang = '"+obj.getTungay()+"' and nam = '"+obj.getDenngay()+"'   )       \n "  +
    				"					group by npp.khuvuc_fk, dh.ddkd_fk, nt.pk_seq, nt.loai     \n "  +
    				"				)    \n "  +
    				"				thucdat on chitieudera.nhomthuongId = thucdat.nhomthuongId and chitieudera.kvId = thucdat.kvId and chitieudera.ddkdId = thucdat.ddkdId       \n "  +
    				"				left join     \n "  +
    				"				(     \n "  +
    				"					select c.PK_SEQ as DdkdId,isnull(a.nhomthuong_fk, -1) as nhomthuongId, a.trongso, a.ma, a.mucbaohiem     \n "  +
    				"					from TrongSoKPI_ChiTiet a inner join TrongSoKPI b on a.trongsoKPI_fk = b.pk_seq     \n "  +
    				"							inner join TRONGSOKPI_NPP tsNpp on tsNpp.TRONGSOKPI_FK=b.PK_SEQ \n "  +
    				"							inner join DAIDIENKINHDOANH c on c.NPP_FK=tsNpp.NPP_FK      \n "  +
    				"					where b.chucvu = 'SR' and a.NHOMTHUONG_FK is not null and  b.tinhthunhap_fk in ( select pk_seq from TinhThuNhap where thang = '"+obj.getTungay()+"' and nam = '"+obj.getDenngay()+"' and trangthai = '1' )       \n "  +
    				"				)    \n "  +
    				"				thuongvuotmuc on   chitieudera.ddkdId=thuongvuotmuc.DdkdId and  chitieudera.nhomthuongId = thuongvuotmuc.nhomthuongId     \n "  +
    				"			)   \n "  +
    				"			TrongSoNSP   \n "  +
    				"			group by TrongSoNSP.kvId, TrongSoNSP.ddkdId, TrongSoNSP.nhomthuongId, TrongSoNSP.PT_Dat, TrongSoNSP.SLTT, TrongSoNSP.TRONGSO   \n "  +
    				"		)   \n "  +
    				"		TrongSoCoThuongVM    \n "  +
    				"		group by TrongSoCoThuongVM.kvId, TrongSoCoThuongVM.ddkdId  \n "  +
    				"	union all    \n "  +
    				"	select chitieuSDH.kvId, chitieuSDH.ddkdId,   \n "  +
    				"		case when ( ( cast( thucdatSDH.sodonhang as float) / chitieuSDH.CT_Donhang) * 100 ) < mucbaohiem then mucbaohiem * thuongvuotmuc.TRONGSO /100   \n "  +
    				"		else ( ( cast( thucdatSDH.sodonhang as float) / chitieuSDH.CT_Donhang) * 100 ) * ( thuongvuotmuc.TRONGSO / 100 ) end as PT_Dat_DonHang,   \n "  +
    				"		0 as tongthuong   \n "  +
    				"	from   \n "  +
    				"	(   \n "  +
    				"		select c.khuvuc_fk as kvId, b.ddkd_fk as ddkdId, a.SONGAYLAMVIEC * b.sodonhang as CT_Donhang    \n "  +
    				"		from chitieunpp a inner join  Chitieunpp_ddkd b on a.pk_seq = b.chitieunpp_fk   \n "  +
    				"			inner join NhaPhanPhoi c on a.nhapp_fk = c.pk_seq    \n "  +
    				"		where a.thang = '"+obj.getTungay()+"' and a.nam = '"+obj.getDenngay()+"' and a.trangthai = '1'    \n "  +
    				"	)   \n "  +
    				"	chitieuSDH inner join    \n "  +
    				"	(   \n "  +
    				"		select b.khuvuc_fk as kvId, a.ddkd_fk as ddkdId, count(distinct a.pk_seq) as sodonhang    \n "  +
    				"		from donhang a inner join nhaphanphoi b on a.npp_fk = b.pk_seq    \n "  +
    				"		where a.ngaynhap like '"+ngaydh+"%' and a.trangthai = '1'    \n "  +
    				"		group by b.khuvuc_fk, a.ddkd_fk    \n "  +
    				"	)   \n "  +
    				"	thucdatSDH on chitieuSDH.kvId = thucdatSDH.kvId and chitieuSDH.ddkdId = thucdatSDH.ddkdId   \n "  +
    				"	left join    \n "  +
    				"	(   \n "  +
    				"		select ddkd.PK_SEQ as ddkdId , a.mucbaohiem, a.TRONGSO    \n "  +
    				"		from TrongSoKPI_ChiTiet a inner join TrongSoKPI b on a.trongsoKPI_fk = b.pk_seq    \n "  +
    				"		inner join TRONGSOKPI_NPP c on c.TRONGSOKPI_FK=b.PK_SEQ\n "  +
    				"		inner join DAIDIENKINHDOANH ddkd on ddkd.NPP_FK=c.NPP_FK\n "  +
    				"		where b.chucvu = 'SR' and a.ma = 'CT03' and  \n "  +
    				"			b.tinhthunhap_fk in ( select pk_seq from TinhThuNhap where thang = '"+obj.getTungay()+"' and nam = '"+obj.getDenngay()+"' and trangthai = '1' )    \n "  +
    				"	)   \n "  +
    				"	thuongvuotmuc on chitieuSDH.ddkdId=thuongvuotmuc.ddkdId \n "  +
    				"	)  KPI inner join    \n "  +
    				"		(   \n "  +
    				"			select  ddkd.PK_SEQ as ddkdId ,LUONGCOBAN, PTLUONGTG, PTLUONGHIEUQUA, BAOHIEMTU, BAOHIEMDEN, BaoHiem, CongDoan, tdnc    \n "  +
    				"			from TrongSoKPI a inner join TRONGSOKPI_NPP  b on b.TRONGSOKPI_FK=a.PK_SEQ\n "  +
    				"				inner join DAIDIENKINHDOANH ddkd on ddkd.NPP_FK=b.NPP_FK\n "  +
    				"			where CHUCVU = 'SR' and a.tinhthunhap_fk in ( select pk_seq from TinhThuNhap where thang = '"+obj.getTungay()+"' and nam = '"+obj.getDenngay()+"' and trangthai = '1' )    \n "  +
    				"		) TrongSoKPI on KPI.ddkdId=TrongSoKPI.ddkdId \n "  +
    				"		inner join daidienkinhdoanh ddkd on KPI.ddkdId = ddkd.pk_seq    \n "  +
    				"		inner join nhaphanphoi npp on ddkd.npp_fk = npp.pk_seq    \n "  +
    				"		inner join khuvuc kv on KPI.kvId = kv.pk_seq     \n "  +
    				"		inner join vung v on kv.vung_fk = v.pk_seq     \n "  +
    				"	group by v.pk_seq, v.ten, kv.ten, npp.pk_seq,npp.DiaChi, npp.ten,npp.SOTAIKHOANNH ,npp.chutaikhoan, ddkd.ten, ddkd.dienthoai, KPI.kvId, KPI.ddkdId,ptluongtg, ptluonghieuqua, baohiem, congdoan, tdnc  \n "  +
    				")   \n "  +
    				"\n "  +
    				"thongtinluong left join  \n "  +
    				"(   \n "  +
    				"	select chitieu.kvId, chitieu.ddkdId, chitieu.nlvChitieu, thucdatdonhang.nlvThucte  \n "  +
    				"	from  \n "  +
    				"	(   \n "  +
    				"		select c.khuvuc_fk as kvId, b.ddkd_fk as ddkdId, a.songaylamviec as nlvChitieu   \n "  +
    				"		from chitieunpp a inner join  Chitieunpp_ddkd b on a.pk_seq = b.chitieunpp_fk   \n "  +
    				"			inner join NhaPhanPhoi c on a.nhapp_fk = c.pk_seq   \n "  +
    				"		where a.thang = '"+obj.getTungay()+"' and a.nam = '"+obj.getDenngay()+"' and a.trangthai = '1'   \n "  +
    				"		group by c.khuvuc_fk, b.ddkd_fk, a.songaylamviec  \n "  +
    				"	)   \n "  +
    				"	chitieu left join   \n "  +
    				"	(  \n "  +
    				"		select b.khuvuc_fk as kvId, a.ddkd_fk as ddkdId, count(distinct a.ngaynhap) as nlvThucte   \n "  +
    				"		from donhang a inner join nhaphanphoi b on a.npp_fk = b.pk_seq    \n "  +
    				"		where a.ngaynhap like '"+ngaydh+"%' and a.trangthai = '1'   \n "  +
    				"		group by b.khuvuc_fk, a.ddkd_fk  \n "  +
    				"	)   \n "  +
    				"	thucdatdonhang on chitieu.ddkdId = thucdatdonhang.ddkdId and chitieu.kvId = thucdatdonhang.kvId  \n "  +
    				")  \n "  +
    				"chitieunlv on thongtinluong.kvId = chitieunlv.kvId and thongtinluong.ddkdId = chitieunlv.ddkdId   \n "  +
    				"left join   \n "  +
    				"(\n "  +
    				"	select  chitieudera.kvId, chitieudera.ddkdId, chitieudera.ctNhomChinh, thucdat.slttNhomChinh as ttNhomChinh  \n "  +
    				"	from   \n "  +
    				"	(   \n "  +
    				"		select c.khuvuc_fk as kvId, b.ddkd_fk as ddkdId, nt.pk_seq as nhomthuongId, sum(d.chitieu) as ctNhomChinh   \n "  +
    				"		from chitieunpp a inner join  Chitieunpp_ddkd b on a.pk_seq = b.chitieunpp_fk   \n "  +
    				"				inner join NhaPhanPhoi c on a.nhapp_fk = c.pk_seq   \n "  +
    				"				inner join ChitieuNPP_DDKD_NHOMSP d on d.ddkd_fk = b.ddkd_fk and d.chitieunpp_fk = a.pk_seq  \n "  +
    				"			inner join NHOMSANPHAM NSP on NSP.pk_seq = d.nhomsanpham_fk   \n "  +
    				"			inner join Nhomthuong_NhomSP nt_nsp on nt_nsp.NhomSanPham_fk = NSP.pk_seq   \n "  +
    				"			inner join Nhomthuong nt on nt_nsp.nhomthuong_fk = nt.pk_seq   \n "  +
    				"		where a.thang = '"+obj.getTungay()+"' and a.nam = '"+obj.getDenngay()+"' and a.trangthai = '1'  and nt.trangthai = '1' and NSP.loainhom = '3' and nt.loai = '1'   \n "  +
    				"		group by c.khuvuc_fk, b.ddkd_fk, nt.pk_seq  \n "  +
    				"	)  \n "  +
    				"	chitieudera left join   \n "  +
    				"	(  \n "  +
    				"		select npp.khuvuc_fk as kvId, dh.ddkd_fk as ddkdId, nt.pk_seq as nhomthuongId,   \n "  +
    				"			case when nt.loai = 1 then 'Nhom Chinh' else 'Nhom Con Lai' end as LoaiNhom, sum( a.soluong * isnull(sp.trongluong, 0) ) as slttNhomChinh   \n "  +
    				"		from   donhang_sanpham a inner join nhomsanpham_sanpham b on a.sanpham_fk = b.sp_fk   \n "  +
    				"				inner join nhomthuong_nhomsp c on b.nsp_fk = c.nhomsanpham_fk  \n "  +
    				"				inner join NHOMSANPHAM NSP on NSP.pk_seq = b.nsp_fk    \n "  +
    				"				inner join Nhomthuong nt on c.nhomthuong_fk = nt.pk_seq  \n "  +
    				"				inner join TrongSoKPI_ChiTiet e on e.nhomthuong_fk = nt.pk_seq   \n "  +
    				"				inner join TrongSoKPI ts on e.trongsokpi_fk = ts.pk_seq   \n "  +
    				"				inner join TRONGSOKPI_NPP n on n.TRONGSOKPI_FK=ts.PK_SEQ\n "  +
    				"				inner join donhang dh on a.donhang_fk = dh.pk_seq   \n "  +
    				"				inner join nhaphanphoi npp on dh.npp_fk = npp.pk_seq and npp.PK_SEQ=n.NPP_FK\n "  +
    				"				inner join sanpham sp on a.sanpham_fk = sp.pk_seq   \n "  +
    				"		where dh.ngaynhap like '"+ngaydh+"%' and dh.trangthai = '1' and nt.trangthai = '1' and NSP.loainhom = '3' and nt.loai = '1'   \n "  +
    				"			and e.nhomthuong_fk is not null  and ts.chucvu = 'SR'   \n "  +
    				"			and ts.tinhthunhap_fk in ( select pk_seq from TinhThuNhap    \n "  +
    				"										where trangthai = '1' and thang = '"+obj.getTungay()+"' and nam = '"+obj.getDenngay()+"'   )    \n "  +
    				"		group by npp.khuvuc_fk, dh.ddkd_fk, nt.pk_seq, nt.loai  \n "  +
    				"	)  \n "  +
    				"	thucdat on chitieudera.nhomthuongId = thucdat.nhomthuongId and chitieudera.kvId = thucdat.kvId and chitieudera.ddkdId = thucdat.ddkdId   \n "  +
    				")  \n "  +
    				"chitieuNC on thongtinluong.kvId = chitieuNC.kvId and thongtinluong.ddkdId = chitieuNC.ddkdId   \n "  +
    				"left join   \n "  +
    				"(  \n "  +
    				"	select  chitieudera.kvId, chitieudera.ddkdId, chitieudera.ctNhomPhu, thucdat.slttNhomPhu as ttNhomPhu    \n "  +
    				"	from  \n "  +
    				"	(   \n "  +
    				"	select c.khuvuc_fk as kvId, b.ddkd_fk as ddkdId, nt.pk_seq as nhomthuongId, sum(d.chitieu) as ctNhomPhu   \n "  +
    				"	from chitieunpp a inner join  Chitieunpp_ddkd b on a.pk_seq = b.chitieunpp_fk   \n "  +
    				"		inner join NhaPhanPhoi c on a.nhapp_fk = c.pk_seq   \n "  +
    				"		inner join ChitieuNPP_DDKD_NHOMSP d on d.ddkd_fk = b.ddkd_fk and d.chitieunpp_fk = a.pk_seq   \n "  +
    				"		inner join NHOMSANPHAM NSP on NSP.pk_seq = d.nhomsanpham_fk   \n "  +
    				"		inner join Nhomthuong_NhomSP nt_nsp on nt_nsp.NhomSanPham_fk = NSP.pk_seq   \n "  +
    				"		inner join Nhomthuong nt on nt_nsp.nhomthuong_fk = nt.pk_seq   \n "  +
    				"	where a.thang = '"+obj.getTungay()+"' and a.nam = '"+obj.getDenngay()+"' and a.trangthai = '1'  and nt.trangthai = '1' and NSP.loainhom = '3' and nt.loai = '2'   \n "  +
    				"	group by c.khuvuc_fk, b.ddkd_fk, nt.pk_seq  \n "  +
    				")  \n "  +
    				"chitieudera left join  \n "  +
    				"(  \n "  +
    				"	select npp.khuvuc_fk as kvId, dh.ddkd_fk as ddkdId, nt.pk_seq as nhomthuongId,   \n "  +
    				"		case when nt.loai = 1 then 'Nhom Chinh' else 'Nhom Con Lai' end as LoaiNhom, sum( a.soluong * isnull(sp.trongluong, 0) ) as slttNhomPhu    \n "  +
    				"	from   donhang_sanpham a inner join nhomsanpham_sanpham b on a.sanpham_fk = b.sp_fk    \n "  +
    				"			inner join nhomthuong_nhomsp c on b.nsp_fk = c.nhomsanpham_fk   \n "  +
    				"			inner join NHOMSANPHAM NSP on NSP.pk_seq = b.nsp_fk   \n "  +
    				"			inner join Nhomthuong nt on c.nhomthuong_fk = nt.pk_seq   \n "  +
    				"			inner join TrongSoKPI_ChiTiet e on e.nhomthuong_fk = nt.pk_seq   \n "  +
    				"			inner join TrongSoKPI ts on e.trongsokpi_fk = ts.pk_seq  \n "  +
    				"			inner join TRONGSOKPI_NPP tsNpp on tsNpp.TRONGSOKPI_FK=ts.PK_SEQ       \n "  +
    				"			inner join donhang dh on a.donhang_fk = dh.pk_seq      \n "  +
    				"			inner join nhaphanphoi npp on dh.npp_fk = npp.pk_seq  and npp.PK_SEQ=tsNpp.NPP_FK\n "  +
    				"			inner join sanpham sp on a.sanpham_fk = sp.pk_seq   \n "  +
    				"	where dh.ngaynhap like '"+ngaydh+"%' and dh.trangthai = '1' and nt.trangthai = '1' and NSP.loainhom = '3' and nt.loai = '2'   \n "  +
    				"		and e.nhomthuong_fk is not null  and ts.chucvu = 'SR'   \n "  +
    				"		and ts.tinhthunhap_fk in ( select pk_seq from TinhThuNhap    \n "  +
    				"									where trangthai = '1' and thang = '"+obj.getTungay()+"' and nam = '"+obj.getDenngay()+"'   )    \n "  +
    				"	group by npp.khuvuc_fk, dh.ddkd_fk, nt.pk_seq, nt.loai  \n "  +
    				"	) thucdat on chitieudera.nhomthuongId = thucdat.nhomthuongId and chitieudera.kvId = thucdat.kvId and chitieudera.ddkdId = thucdat.ddkdId   \n "  +
    				")  \n "  +
    				"chitieuNP on thongtinluong.kvId = chitieuNP.kvId and thongtinluong.ddkdId = chitieuNP.ddkdId   \n "  +
    				"left join   \n "  +
    				"(   \n "  +
    				"	select  chitieudera.kvId, chitieudera.ddkdId, chitieudera.ctSoDonHang, thucdat.ttSodonhang as ttSodonhang    \n "  +
    				"	from  \n "  +
    				"	(   \n "  +
    				"		select c.khuvuc_fk as kvId, b.ddkd_fk as ddkdId, a.SONGAYLAMVIEC * b.sodonhang as ctSoDonHang   \n "  +
    				"		from chitieunpp a inner join  Chitieunpp_ddkd b on a.pk_seq = b.chitieunpp_fk   \n "  +
    				"				inner join NhaPhanPhoi c on a.nhapp_fk = c.pk_seq   \n "  +
    				"		where a.thang = '"+obj.getTungay()+"' and a.nam = '"+obj.getDenngay()+"' and a.trangthai = '1'   \n "  +
    				"	)  \n "  +
    				"	chitieudera left join   \n "  +
    				"	(  \n "  +
    				"		select b.khuvuc_fk as kvId, a.ddkd_fk as ddkdId, count(distinct a.pk_seq) as ttSodonhang   \n "  +
    				"		from donhang a inner join nhaphanphoi b on a.npp_fk = b.pk_seq    \n "  +
    				"		where a.ngaynhap like '"+ngaydh+"%' and a.trangthai = '1'   \n "  +
    				"		group by b.khuvuc_fk, a.ddkd_fk  \n "  +
    				"	) thucdat on chitieudera.kvId = thucdat.kvId and chitieudera.ddkdId = thucdat.ddkdId	  \n "  +
    				")  \n "  +
    				"chitieuDH on thongtinluong.kvId = chitieuDH.kvId and thongtinluong.ddkdId = chitieuDH.ddkdId   \n "  +
    				"inner join TinhThuNhap_DDKD tinh_thunhap on thongtinluong.ddkdId = tinh_thunhap.ddkd_fk and tinh_thunhap.tinhthunhap_fk in ( select pk_seq from TinhThuNhap where trangthai = '1' and nam = '"+obj.getDenngay()+"' and thang = '"+obj.getTungay()+"' )   \n "  +
    				"left join   \n "  +
    				"(  \n "  +
    				"	select pcTheoNC.ddkd_fk, pcTheoNC.tpcTheoNC, pcKoTheoNC.tpcKoTheoNC  \n "  +
    				"	from  \n "  +
    				"	(  \n "  +
    				"		select b.ddkd_fk, isnull(SUM(c.trongso), 0) as tpcTheoNC    \n "  +
    				"		from TINHPHUCAP_CHUCVU a inner join  TINHPHUCAP_CHUCVU_DDKD b on a.PK_SEQ = b.tinhphucap_chucvu_fk    \n "  +
    				"		left join TINHPHUCAP_CHUCVU_PHUCAP c on c.TINHPHUCAP_CHUCVU_FK = a.PK_SEQ  and c.tinhtheongaycong = '1'    \n "  +
    				"		where  a.CHUCVU = 'SR' and a.tinhphucap_fk in ( select PK_SEQ from TinhPhuCap where thang = '"+obj.getTungay()+"' and nam = '"+obj.getDenngay()+"' and trangthai = '1' )     \n "  +
    				"		group by b.ddkd_fk   \n "  +
    				"	)  \n "  +
    				"	pcTheoNC,    \n "  +
    				"	(  \n "  +
    				"		select b.ddkd_fk,isnull(SUM(d.trongso), 0) as tpcKoTheoNC     \n "  +
    				"		from TINHPHUCAP_CHUCVU a inner join  TINHPHUCAP_CHUCVU_DDKD b on a.PK_SEQ = b.tinhphucap_chucvu_fk    \n "  +
    				"		left join TINHPHUCAP_CHUCVU_PHUCAP d on d.TINHPHUCAP_CHUCVU_FK = a.PK_SEQ  and d.tinhtheongaycong = '0'   \n "  +
    				"		where a.CHUCVU = 'SR' and a.tinhphucap_fk in ( select PK_SEQ from TinhPhuCap where thang = '"+obj.getTungay()+"' and nam = '"+obj.getDenngay()+"' and trangthai = '1' )    \n "  +
    				"		group by b.ddkd_fk   \n "  +
    				"	)  \n "  +
    				"	pcKoTheoNC where pcTheoNC.ddkd_fk = pcKoTheoNC.ddkd_fk \n "  +
    				") tongphucap on thongtinluong.ddkdId = tongphucap.ddkd_fk " ;
	    	
	    }
	    else  //Lay theo GSBH
	    {
		query =
			 	 /* "declare '"+obj.getTungay()+"' varchar(2),'"+obj.getDenngay()+"' varchar(4)   \n   "   +
				  " set '"+obj.getTungay()+"'='"+obj.getTungay().trim()+"'   \n   "   +
				  " set '"+obj.getDenngay()+"'='"+obj.getDenngay().trim()+"'   \n   "   +*/
				  " select thongtinluong.*, tinh_thunhap.luongcb, chitieunlv.nlvChitieu, chitieunlv.nlvThucte,      \n   "   +
				  "				isnull(chitieuNC.ctNhomChinh, 0) as ctNhomChinh, isnull(chitieuNC.ttNhomChinh, 0) as ttNhomChinh,     \n   "   +
				  "				case when isnull(chitieuNC.ctNhomChinh, 0) = 0 then 0 else isnull(chitieuNC.ttNhomChinh, 0) * 100 / isnull(chitieuNC.ctNhomChinh, 0) end as ptNhomChinh,      \n   "   +
				  "				isnull(chitieuNP.ctNhomPhu, 0) as ctNhomPhu, isnull(chitieuNP.ttNhomPhu, 0) as ttNhomPhu,      \n   "   +
				  "				case when isnull(chitieuNP.ctNhomPhu, 0) = 0 then 0 else isnull(chitieuNP.ttNhomPhu, 0) * 100 / isnull(chitieuNP.ctNhomPhu, 0) end as ptNhomPhu,      \n   "   +
				  "				isnull(chitieuDH.ctSoDonHang, 0) as ctSoDonHang, isnull(chitieuDH.ttSodonhang, 0) as ttSodonhang,      \n   "   +
				  "				case when isnull(chitieuDH.ctSoDonHang, 0) = 0 then 0 else isnull(chitieuDH.ttSodonhang, 0) * 100 / isnull(chitieuDH.ctSoDonHang, 0) end as ptSoDonHang,      \n   "   +
				  "				isnull(quytrinhLV.ctQuyTrinh, 0) as ctQuyTrinh, isnull(quytrinhLV.ttQuyTrinh, 0) as ttQuyTrinh, isnull(quytrinhLV.ttQuyTrinh, 0) as ptQuyTrinh,     \n   "   +
				  "				ISNULL(tongphucap.tpcTheoNC, 0) as TienPhuCap_TheoNC, ISNULL(tongphucap.tpcKoTheoNC, 0) as TienPhuCap_KoTheoNC, isnull(ThuongDoanhSo.Thuong, 0) as ThuongDoanhSo,isnull(thuong.ThuongVuotMuc,0) as ThuongVuotMuc        \n   "   +
				  "		from     \n   "   +
				  "		(     \n   "   +
				  "			select v.pk_seq as vungId, v.ten as vungTen, kv.pk_seq as kvId, kv.ten as kvTen, KPI.gsbhId, gsbh.ten, gsbh.dienthoai, isnull(gsbh.tinhtrang, 0) as tinhtrang,     \n   "   +
				  "			 isnull(gsbh.nganhang,'Chua xac dinh') as NganHang,isnull(gsbh.ChiNhanh,'Chua xac dinh') as ChiNhanh ,gsbh.TaiKhoan as SoTaiKhoan,    \n   "   +
				  "				case when sum ( PT_Dat * trongso / 100 ) < avg(mucbaohiem) then avg(mucbaohiem)      \n   "   +
				  "				else sum ( PT_Dat * trongso / 100 ) end as PT_KPI,      \n   "   +
				  "				 avg(ptluongtg) as ptluongtg, avg(ptluonghieuqua) as ptluonghieuqua,     \n   "   +
				  "				avg(BHXH) as BHXH, avg(CongDoan) as CongDoan, avg(tdnc) as tdnc , '1' as type     \n   "   +
				  "			from      \n   "   +
				  "			(      \n   "   +
				  "				select chitieudera.kvId, chitieudera.gsbhId, chitieudera.nhomthuongId,         \n   "   +
				  "					case when ( thucdat.loainhom = 'Nhom Chinh' ) and ( ( thucdat.sltt * 100 / chitieudera.chitieu )  < bhnhomthuong.mucbaohiem ) then bhnhomthuong.mucbaohiem         \n   "   +
				  "					else ( thucdat.sltt * 100 / chitieudera.chitieu ) end  as PT_Dat,         \n   "   +
				  "					case thucdat.loainhom when 'Nhom Chinh' then 'CT01' else 'CT02' end as ma         \n   "   +
				  "				from      \n   "   +
				  "				(       \n   "   +
				  "					select gsbh.KHUVUC_FK as kvId, c.gsbh_fk as gsbhId, nt.pk_seq as nhomthuongId, sum(d.chitieu) as Chitieu        \n   "   +
				  "					from chitieunpp a inner join  Chitieunpp_ddkd b on a.pk_seq = b.chitieunpp_fk         \n   "   +
				  "						inner join ddkd_gsbh c on b.ddkd_fk = c.ddkd_fk        \n   "   +
				  "						inner join ChitieuNPP_DDKD_NHOMSP d on d.ddkd_fk = b.ddkd_fk  and d.chitieunpp_fk = a.pk_seq       \n   "   +
				  "						inner join NHOMSANPHAM NSP on NSP.pk_seq = d.nhomsanpham_fk         \n   "   +
				  "						inner join Nhomthuong_NhomSP nt_nsp on nt_nsp.NhomSanPham_fk = NSP.pk_seq        \n   "   +
				  "						inner join Nhomthuong nt on nt_nsp.nhomthuong_fk = nt.pk_seq         \n   "   +
				  "						inner join GIAMSATBANHANG gsbh on c.GSBH_FK = gsbh.PK_SEQ      \n   "   +
				  "					where a.thang = '"+obj.getTungay()+"' and a.nam = '"+obj.getDenngay()+"' and a.trangthai = '1' and nt.trangthai = '1' and NSP.loainhom = '3'        \n   "   +
				  "					group by gsbh.KHUVUC_FK, c.gsbh_fk, nt.pk_seq       \n   "   +
				  "				)        \n   "   +
				  "				chitieudera inner join        \n   "   +
				  "				(        \n   "   +
				  "					select gsbh.khuvuc_fk as kvId, dh.GSBH_FK as gsbhId, nt.pk_seq as nhomthuongId, case when nt.loai = 1 then 'Nhom Chinh' else 'Nhom Con Lai' end as LoaiNhom,           \n   "   +
				  "						sum( a.soluong * isnull(sp.trongluong, 0) ) as SLTT , sum( a.soluong ) as SLTT_LT           \n   "   +
				  "					from donhang_sanpham a inner join nhomsanpham_sanpham b on a.sanpham_fk = b.sp_fk          \n   "   +
				  "						inner join NHOMSANPHAM NSP on NSP.pk_seq = b.nsp_fk           \n   "   +
				  "						inner join Nhomthuong_NhomSP nt_nsp on nt_nsp.NhomSanPham_fk = NSP.pk_seq           \n   "   +
				  "						inner join Nhomthuong nt on nt_nsp.nhomthuong_fk = nt.pk_seq               \n   "   +
				  "						inner join sanpham sp on a.sanpham_fk = sp.pk_seq            \n   "   +
				  "						inner join DONHANG dh on a.DONHANG_FK = dh.PK_SEQ       \n   "   +
				  "						inner join GIAMSATBANHANG gsbh on dh.GSBH_FK = gsbh.PK_SEQ         \n   "   +
				  "					where dh.ngaynhap like '"+ngaydh+"%' and dh.trangthai = '1'           \n   "   +
				  "						and dh.PK_SEQ not in ( select DONHANG_FK from DONHANGTRAVE where TRANGTHAI = '3' and DONHANG_FK is not null )      \n   "   +
				  "						and nt_nsp.NHOMTHUONG_FK in ( select NHOMTHUONG_FK from TrongSoKPI_ChiTiet where NHOMTHUONG_FK is not null and TRONGSOKPI_FK in ( select pk_seq from TRONGSOKPI where CHUCVU = 'SS' and tinhthunhap_fk in ( select PK_SEQ from TinhThuNhap where trangthai = '1' and thang = '"+obj.getTungay()+"' and nam = '"+obj.getDenngay()+"' ) ) )      \n   "   +
				  "					group by  gsbh.khuvuc_fk, dh.GSBH_FK, nt.pk_seq, nt.loai     \n   "   +
				  "				)        \n   "   +
				  "				thucdat on chitieudera.nhomthuongId = thucdat.nhomthuongId and chitieudera.gsbhId = thucdat.gsbhId  and chitieudera.kvId = thucdat.kvId      \n   "   +
				  "				left join        \n   "   +
				  "				(        \n   "   +
				  "					select c.gsbh_fk as gsbhId, isnull(a.nhomthuong_fk, -1) as nhomthuongId, a.mucbaohiem        \n   "   +
				  "					from TrongSoKPI_ChiTiet a inner join TrongSoKPI b on a.trongsoKPI_fk = b.pk_seq  inner join TinhThuNhap_GSBH c on c.TRONGSOKPI_FK=b.PK_SEQ    \n   "   +
				  "					where b.chucvu = 'SS' and  b.tinhthunhap_fk in ( select pk_seq from TinhThuNhap where thang = '"+obj.getTungay()+"' and nam = '"+obj.getDenngay()+"' and trangthai = '1' )        \n   "   +
				  "				)       \n   "   +
				  "				bhnhomthuong on chitieudera.nhomthuongId = bhnhomthuong.nhomthuongId  and chitieudera.gsbhId = bhnhomthuong.gsbhId   \n   "   +
				  "			union all        \n   "   +
				  "				select chitieuSDH.kvId, chitieuSDH.gsbhId, -1 as nhomthuongId,        \n   "   +
				  "					case when (	( cast( thucdatSDH.sodonhang as float) / chitieuSDH.CT_Donhang) * 100 ) < mucbaohiem then mucbaohiem        \n   "   +
				  "					else  (	( cast( thucdatSDH.sodonhang as float) / chitieuSDH.CT_Donhang) * 100 ) end as PT_DonHang, 'CT03' as ma        \n   "   +
				  "				from        \n   "   +
				  "				(       \n   "   +
				  "					select gsbh.KHUVUC_FK as kvId, c.gsbh_fk as gsbhId, -1 as nhomthuongId, SUM(distinct a.SONGAYLAMVIEC) * sum(isnull(b.sodonhang, 0)) as CT_Donhang         \n   "   +
				  "					from chitieunpp a inner join  Chitieunpp_ddkd b on a.pk_seq = b.chitieunpp_fk        \n   "   +
				  "						inner join ddkd_gsbh c on b.ddkd_fk = c.ddkd_fk       \n   "   +
				  "						inner join GIAMSATBANHANG gsbh on c.GSBH_FK = gsbh.PK_SEQ      \n   "   +
				  "					where a.thang = '"+obj.getTungay()+"' and a.nam = '"+obj.getDenngay()+"' and a.trangthai = '1'        \n   "   +
				  "					group by gsbh.KHUVUC_FK, c.gsbh_fk       \n   "   +
				  "				)       \n   "   +
				  "				chitieuSDH inner join        \n   "   +
				  "				(        \n   "   +
				  "					select gsbh.KHUVUC_FK as kvId, a.gsbh_fk as gsbhId, count(distinct a.pk_seq) as sodonhang        \n   "   +
				  "					from donhang a inner join GIAMSATBANHANG gsbh on a.GSBH_FK = gsbh.PK_SEQ      \n   "   +
				  "					where ngaynhap like '"+ngaydh+"%' and a.trangthai = '1'        \n   "   +
				  "					group by gsbh.KHUVUC_FK, gsbh_fk       \n   "   +
				  "				)       \n   "   +
				  "				thucdatSDH on chitieuSDH.gsbhId = thucdatSDH.gsbhId  and chitieuSDH.gsbhId = thucdatSDH.gsbhId      \n   "   +
				  "				left join        \n   "   +
				  "				(       \n   "   +
				  "					select c.gsbh_fk as gsbhId, isnull(a.nhomthuong_fk, -1) as nhomthuongId, a.mucbaohiem        \n   "   +
				  "					from TrongSoKPI_ChiTiet a inner join TrongSoKPI b on a.trongsoKPI_fk = b.pk_seq  inner join TinhThuNhap_GSBH c on c.TRONGSOKPI_FK=b.PK_SEQ      \n   "   +
				  "					where b.chucvu = 'SS' and a.ma = 'CT03'        \n   "   +
				  "						and b.tinhthunhap_fk in ( select pk_seq from TinhThuNhap where thang = '"+obj.getTungay()+"' and nam = '"+obj.getDenngay()+"' and trangthai = '1' )        \n   "   +
				  "				)       \n   "   +
				  "				bhdonhang on chitieuSDH.nhomthuongId = bhdonhang.nhomthuongId  and chitieuSDH.gsbhId = bhdonhang.gsbhId   \n   "   +
				  "			union all        \n   "   +
				  "				select thucdatquytrinh.kvId, thucdatquytrinh.gsbhId, thucdatquytrinh.nhomthuongId,        \n   "   +
				  "					case when thucdatquytrinh.PT_QuyTrinh < isnull(bhquytrinh.mucbaohiem, 0) then isnull(bhquytrinh.mucbaohiem, 0)        \n   "   +
				  "					else thucdatquytrinh.PT_QuyTrinh end as PT_QuyTrinh, thucdatquytrinh.ma        \n   "   +
				  "				from        \n   "   +
				  "				(        \n   "   +
				  "					select gsbh.KHUVUC_FK as kvId, b.gsbh_fk as gsbhId, -1 as nhomthuongId, sum( isnull(b.chamlan1, 0) * a.trongso / 100 ) * 100 / 5 as PT_QuyTrinh, 'CT04' as ma        \n   "   +
				  "					from tieuchidanhgia_tieuchi a inner join tieuchidanhgia_tieuchi_gsbh b on a.pk_seq = b.tieuchidanhgia_tieuchi_fk       \n   "   +
				  "						inner join GIAMSATBANHANG gsbh on b.gsbh_fk = gsbh.PK_SEQ      \n   "   +
				  "					where a.tieuchidanhgia_fk in ( select pk_seq from tieuchidanhgia where thang = '"+obj.getTungay()+"' and nam = '"+obj.getDenngay()+"' and trangthai = '1' )        \n   "   +
				  "					group by gsbh.KHUVUC_FK, b.gsbh_fk        \n   "   +
				  "				)       \n   "   +
				  "				thucdatquytrinh  left join        \n   "   +
				  "				(       \n   "   +
				  "					select c.gsbh_fk as gsbhId, isnull(a.nhomthuong_fk, -1) as nhomthuongId, a.mucbaohiem       \n   "   +
				  "					from TrongSoKPI_ChiTiet a inner join TrongSoKPI b on a.trongsoKPI_fk = b.pk_seq  inner join TinhThuNhap_GSBH c on c.TRONGSOKPI_FK=b.PK_SEQ     \n   "   +
				  "					where b.chucvu = 'SS' and a.ma = 'CT04'        \n   "   +
				  "					and b.tinhthunhap_fk in ( select pk_seq from TinhThuNhap where thang = '"+obj.getTungay()+"' and nam = '"+obj.getDenngay()+"' and trangthai = '1' )        \n   "   +
				  "				)       \n   "   +
				  "				bhquytrinh on thucdatquytrinh.nhomthuongId = bhquytrinh.nhomthuongId  and thucdatquytrinh.gsbhId = bhquytrinh.gsbhId   \n   "   +
				  "			)        \n   "   +
				  "			KPI inner join        \n   "   +
				  "			(	       \n   "   +
				  "				select c.gsbh_fk as gsbhId, isnull(a.nhomthuong_fk, -1) as nhomthuongId, a.trongso, a.ma,        \n   "   +
				  "					a.thuongvuotmuc, b.luongcoban, b.ptluongtg, b.ptluonghieuqua, b.baohiemden as mucbaohiem, b.baohiem as BHXH, b.congdoan, b.tdnc         \n   "   +
				  "				from TrongSoKPI_ChiTiet a inner join TrongSoKPI b on a.trongsoKPI_fk = b.pk_seq  inner join TinhThuNhap_GSBH c on c.TRONGSOKPI_FK=b.PK_SEQ     \n   "   +
				  "				where b.chucvu = 'SS' and  b.tinhthunhap_fk in ( select pk_seq from TinhThuNhap where thang = '"+obj.getTungay()+"' and nam = '"+obj.getDenngay()+"' and trangthai = '1' )         \n   "   +
				  "			)        \n   "   +
				  "			TrongSoKPI on KPI.ma  = TrongSoKPI.ma and KPI.nhomthuongId = TrongSoKPI.nhomthuongId  and KPI.gsbhId = TrongSoKPI.gsbhId   \n   "   +
				  "				inner join giamsatbanhang gsbh on KPI.gsbhId = gsbh.pk_seq        \n   "   +
				  "				inner join khuvuc kv on gsbh.khuvuc_fk = kv.pk_seq        \n   "   +
				  "				inner join vung v on kv.vung_fk = v.pk_seq       \n   "   +
				  "			group by v.pk_seq , v.ten, kv.pk_seq, kv.ten, KPI.gsbhId, gsbh.ten, gsbh.dienthoai, gsbh.tinhtrang,gsbh.nganhang,gsbh.ChiNhanh ,gsbh.TaiKhoan    \n   "   +
				  "		)     \n   "   +
				  "		thongtinluong left join      \n   "   +
				  "		(     \n   "   +
				  "			select chitieu.kvId, chitieu.gsbhId, chitieu.nlvChitieu, thucdatdonhang.nlvThucte        \n   "   +
				  "			from      \n   "   +
				  "			(       \n   "   +
				  "				select  gsbh.KHUVUC_FK as kvId, c.gsbh_fk as gsbhId, max(a.songaylamviec) as nlvChitieu        \n   "   +
				  "				from chitieunpp a inner join  Chitieunpp_ddkd b on a.pk_seq = b.chitieunpp_fk        \n   "   +
				  "					inner join ddkd_gsbh c on b.ddkd_fk = c.ddkd_fk      \n   "   +
				  "					inner join GIAMSATBANHANG gsbh on c.GSBH_FK = gsbh.PK_SEQ        \n   "   +
				  "				where a.thang = '"+obj.getTungay()+"' and a.nam = '"+obj.getDenngay()+"' and a.trangthai = '1'        \n   "   +
				  "				group by gsbh.KHUVUC_FK, c.gsbh_fk        \n   "   +
				  "			)        \n   "   +
				  "			chitieu left join        \n   "   +
				  "			(        \n   "   +
				  "				select kvId, gsbhId, max(isnull(nlvThucte, 0)) as nlvThucte       \n   "   +
				  "				from        \n   "   +
				  "				(       \n   "   +
				  "					select gsbh.KHUVUC_FK as kvId,  a.gsbh_fk as gsbhId, count(distinct ngaynhap)  as nlvThucte       \n   "   +
				  "					from donhang a inner join GIAMSATBANHANG gsbh on a.GSBH_FK = gsbh.PK_SEQ      \n   "   +
				  "					where ngaynhap like '"+ngaydh+"%' and a.TRANGTHAI = '1'        \n   "   +
				  "					group by gsbh.KHUVUC_FK, gsbh_fk     \n   "   +
				  "				)       \n   "   +
				  "				gsbh group by kvId, gsbhId       \n   "   +
				  "			)         \n   "   +
				  "			thucdatdonhang on chitieu.gsbhId = thucdatdonhang.gsbhId   and chitieu.kvId = thucdatdonhang.kvId     \n   "   +
				  "		)     \n   "   +
				  "		chitieunlv on thongtinluong.gsbhId = chitieunlv.gsbhId and thongtinluong.kvId = chitieunlv.kvId       \n   "   +
				  "		left join      \n   "   +
				  "		(      \n   "   +
				  "			 select chitieudera.kvId, chitieudera.gsbhId, chitieudera.ctNhomChinh, thucdat.slttNhomChinh as ttNhomChinh         \n   "   +
				  "			from        \n   "   +
				  "			(        \n   "   +
				  "				select gsbh.KHUVUC_FK as kvId, c.gsbh_fk as gsbhId, nt.pk_seq as nhomthuongId, sum(d.chitieu) as ctNhomChinh         \n   "   +
				  "				from chitieunpp a inner join  Chitieunpp_ddkd b on a.pk_seq = b.chitieunpp_fk        \n   "   +
				  "					inner join ddkd_gsbh c on b.ddkd_fk = c.ddkd_fk        \n   "   +
				  "					inner join ChitieuNPP_DDKD_NHOMSP d on d.ddkd_fk = b.ddkd_fk  and d.chitieunpp_fk = a.pk_seq      \n   "   +
				  "					inner join NHOMSANPHAM NSP on NSP.pk_seq = d.nhomsanpham_fk        \n   "   +
				  "					inner join Nhomthuong_NhomSP nt_nsp on nt_nsp.NhomSanPham_fk = NSP.pk_seq        \n   "   +
				  "					inner join Nhomthuong nt on nt_nsp.nhomthuong_fk = nt.pk_seq     \n   "   +
				  "					inner join GIAMSATBANHANG gsbh on c.GSBH_FK = gsbh.PK_SEQ        \n   "   +
				  "				where a.thang = '"+obj.getTungay()+"' and a.nam = '"+obj.getDenngay()+"' and a.trangthai = '1' and nt.trangthai = '1' and NSP.loainhom = '3'  and nt.loai = '1'        \n   "   +
				  "				group by gsbh.KHUVUC_FK, c.gsbh_fk, nt.pk_seq       \n   "   +
				  "			)        \n   "   +
				  "			chitieudera left join       \n   "   +
				  "			(       \n   "   +
				  "				select gsbh.khuvuc_fk as kvId, dh.GSBH_FK as gsbhId, nt.pk_seq as nhomthuongId, case when nt.loai = 1 then 'Nhom Chinh' else 'Nhom Con Lai' end as LoaiNhom,           \n   "   +
				  "					sum( a.soluong * isnull(sp.trongluong, 0) ) as slttNhomChinh         \n   "   +
				  "				from donhang_sanpham a inner join nhomsanpham_sanpham b on a.sanpham_fk = b.sp_fk          \n   "   +
				  "					inner join NHOMSANPHAM NSP on NSP.pk_seq = b.nsp_fk           \n   "   +
				  "					inner join Nhomthuong_NhomSP nt_nsp on nt_nsp.NhomSanPham_fk = NSP.pk_seq           \n   "   +
				  "					inner join Nhomthuong nt on nt_nsp.nhomthuong_fk = nt.pk_seq               \n   "   +
				  "					inner join sanpham sp on a.sanpham_fk = sp.pk_seq            \n   "   +
				  "					inner join DONHANG dh on a.DONHANG_FK = dh.PK_SEQ       \n   "   +
				  "					inner join GIAMSATBANHANG gsbh on dh.GSBH_FK = gsbh.PK_SEQ         \n   "   +
				  "				where dh.ngaynhap like '"+ngaydh+"%' and dh.trangthai = '1' and nt.loai = '1'          \n   "   +
				  "					and dh.PK_SEQ not in ( select DONHANG_FK from DONHANGTRAVE where TRANGTHAI = '3' and DONHANG_FK is not null )      \n   "   +
				  "					and nt_nsp.NHOMTHUONG_FK in ( select NHOMTHUONG_FK from TrongSoKPI_ChiTiet where NHOMTHUONG_FK is not null and TRONGSOKPI_FK in ( select pk_seq from TRONGSOKPI where CHUCVU = 'SS' and tinhthunhap_fk in ( select PK_SEQ from TinhThuNhap where trangthai = '1' and thang = '"+obj.getTungay()+"' and nam = '"+obj.getDenngay()+"' ) ) )      \n   "   +
				  "				group by  gsbh.khuvuc_fk, dh.GSBH_FK, nt.pk_seq, nt.loai     \n   "   +
				  "			)       \n   "   +
				  "			thucdat on chitieudera.nhomthuongId = thucdat.nhomthuongId and chitieudera.gsbhId = thucdat.gsbhId and chitieudera.kvId = thucdat.kvId     \n   "   +
				  "		)     \n   "   +
				  "		chitieuNC on thongtinluong.gsbhId = chitieuNC.gsbhId and thongtinluong.kvId = chitieuNC.kvId      \n   "   +
				  "		left join      \n   "   +
				  "		(      \n   "   +
				  "			select chitieudera.kvId, chitieudera.gsbhId, chitieudera.ctNhomPhu, thucdat.slttNhomPhu as ttNhomPhu        \n   "   +
				  "			from        \n   "   +
				  "			(        \n   "   +
				  "				select gsbh.KHUVUC_FK as kvId, c.gsbh_fk as gsbhId, nt.pk_seq as nhomthuongId, sum(d.chitieu) as ctNhomPhu         \n   "   +
				  "				from chitieunpp a inner join  Chitieunpp_ddkd b on a.pk_seq = b.chitieunpp_fk         \n   "   +
				  "					inner join ddkd_gsbh c on b.ddkd_fk = c.ddkd_fk        \n   "   +
				  "					inner join ChitieuNPP_DDKD_NHOMSP d on d.ddkd_fk = b.ddkd_fk  and d.chitieunpp_fk = a.pk_seq      \n   "   +
				  "					inner join NHOMSANPHAM NSP on NSP.pk_seq = d.nhomsanpham_fk        \n   "   +
				  "					inner join Nhomthuong_NhomSP nt_nsp on nt_nsp.NhomSanPham_fk = NSP.pk_seq        \n   "   +
				  "					inner join Nhomthuong nt on nt_nsp.nhomthuong_fk = nt.pk_seq      \n   "   +
				  "					inner join GIAMSATBANHANG gsbh on c.GSBH_FK = gsbh.PK_SEQ        \n   "   +
				  "				where a.thang = '"+obj.getTungay()+"' and a.nam = '"+obj.getDenngay()+"' and a.trangthai = '1' and nt.trangthai = '1' and NSP.loainhom = '3'  and nt.loai = '2'        \n   "   +
				  "				group by gsbh.KHUVUC_FK, c.gsbh_fk, nt.pk_seq       \n   "   +
				  "			)       \n   "   +
				  "			chitieudera left join      \n   "   +
				  "			(       \n   "   +
				  "				select gsbh.khuvuc_fk as kvId, dh.GSBH_FK as gsbhId, nt.pk_seq as nhomthuongId, case when nt.loai = 1 then 'Nhom Chinh' else 'Nhom Con Lai' end as LoaiNhom,           \n   "   +
				  "					sum( a.soluong * isnull(sp.trongluong, 0) ) as slttNhomPhu            \n   "   +
				  "				from donhang_sanpham a inner join nhomsanpham_sanpham b on a.sanpham_fk = b.sp_fk          \n   "   +
				  "					inner join NHOMSANPHAM NSP on NSP.pk_seq = b.nsp_fk           \n   "   +
				  "					inner join Nhomthuong_NhomSP nt_nsp on nt_nsp.NhomSanPham_fk = NSP.pk_seq           \n   "   +
				  "					inner join Nhomthuong nt on nt_nsp.nhomthuong_fk = nt.pk_seq               \n   "   +
				  "					inner join sanpham sp on a.sanpham_fk = sp.pk_seq            \n   "   +
				  "					inner join DONHANG dh on a.DONHANG_FK = dh.PK_SEQ       \n   "   +
				  "					inner join GIAMSATBANHANG gsbh on dh.GSBH_FK = gsbh.PK_SEQ         \n   "   +
				  "				where dh.ngaynhap like '"+ngaydh+"%' and dh.trangthai = '1'   and nt.loai = '2'        \n   "   +
				  "					and dh.PK_SEQ not in ( select DONHANG_FK from DONHANGTRAVE where TRANGTHAI = '3' and DONHANG_FK is not null )      \n   "   +
				  "					and nt_nsp.NHOMTHUONG_FK in ( select NHOMTHUONG_FK from TrongSoKPI_ChiTiet where NHOMTHUONG_FK is not null and TRONGSOKPI_FK in ( select pk_seq from TRONGSOKPI where CHUCVU = 'SS' and tinhthunhap_fk in ( select PK_SEQ from TinhThuNhap where trangthai = '1' and thang = '"+obj.getTungay()+"' and nam = '"+obj.getDenngay()+"' ) ) )      \n   "   +
				  "				group by  gsbh.khuvuc_fk, dh.GSBH_FK, nt.pk_seq, nt.loai     \n   "   +
				  "			)       \n   "   +
				  "			thucdat on chitieudera.nhomthuongId = thucdat.nhomthuongId and chitieudera.gsbhId = thucdat.gsbhId and chitieudera.kvId = thucdat.kvId     \n   "   +
				  "		)     \n   "   +
				  "		chitieuNP on thongtinluong.gsbhId = chitieuNP.gsbhId and thongtinluong.kvId = chitieuNP.kvId      \n   "   +
				  "		left join      \n   "   +
				  "		(      \n   "   +
				  "			select chitieudera.kvId, chitieudera.gsbhId, chitieudera.ctSoDonHang, thucdat.ttSodonhang as ttSodonhang       \n   "   +
				  "			from        \n   "   +
				  "			(       \n   "   +
				  "				select gsbh.KHUVUC_FK as kvId, c.gsbh_fk as gsbhId, SUM(distinct a.SONGAYLAMVIEC) * sum(b.sodonhang) as ctSoDonHang        \n   "   +
				  "				from chitieunpp a inner join  Chitieunpp_ddkd b on a.pk_seq = b.chitieunpp_fk inner join ddkd_gsbh c on b.ddkd_fk = c.ddkd_fk        \n   "   +
				  "					inner join GIAMSATBANHANG gsbh on c.GSBH_FK = gsbh.PK_SEQ      \n   "   +
				  "				where a.thang = '"+obj.getTungay()+"' and a.nam = '"+obj.getDenngay()+"' and a.trangthai = '1'        \n   "   +
				  "				group by gsbh.KHUVUC_FK, c.gsbh_fk       \n   "   +
				  "			)        \n   "   +
				  "			chitieudera left join        \n   "   +
				  "			(        \n   "   +
				  "				select gsbh.KHUVUC_FK as kvId, a.gsbh_fk as gsbhId, count(distinct a.pk_seq) as ttSodonhang        \n   "   +
				  "				from donhang a inner join GIAMSATBANHANG gsbh on a.GSBH_FK = gsbh.PK_SEQ       \n   "   +
				  "				where a.ngaynhap like '"+ngaydh+"%' and a.trangthai = '1'        \n   "   +
				  "				group by gsbh.KHUVUC_FK, gsbh_fk       \n   "   +
				  "			)      \n   "   +
				  "			thucdat on chitieudera.gsbhId = thucdat.gsbhId	and chitieudera.kvId = thucdat.kvId     \n   "   +
				  "		)     \n   "   +
				  "		chitieuDH on thongtinluong.gsbhId = chitieuDH.gsbhId and thongtinluong.kvId = chitieuDH.kvId      \n   "   +
				  "		left join    \n   "   +
				  "		(     \n   "   +
				  "			select  b.gsbh_fk as gsbhId, 100 as ctQuyTrinh, sum( isnull(b.chamlan1, 0) * a.trongso / 100 ) * 100 / 5 as ttQuyTrinh      \n   "   +
				  "			from tieuchidanhgia_tieuchi a inner join tieuchidanhgia_tieuchi_gsbh b on a.pk_seq = b.tieuchidanhgia_tieuchi_fk      \n   "   +
				  "			where a.tieuchidanhgia_fk in ( select pk_seq from tieuchidanhgia where thang = '"+obj.getTungay()+"' and nam = '"+obj.getDenngay()+"' and trangthai = '1' )      \n   "   +
				  "			group by gsbh_fk      \n   "   +
				  "		)     \n   "   +
				  "		quytrinhLV on thongtinluong.gsbhId = quytrinhLV.gsbhId      \n   "   +
				  "		inner join TinhThuNhap_GSBH tinh_thunhap on thongtinluong.gsbhId = tinh_thunhap.gsbh_fk and tinh_thunhap.tinhthunhap_fk in ( select pk_seq from TinhThuNhap where trangthai = '1' and nam = '"+obj.getDenngay()+"' and thang = '"+obj.getTungay()+"' )      \n   "   +
				  "		left join     \n   "   +
				  "		(     \n   "   +
				  "			select pcTheoNC.gsbh_fk, pcTheoNC.tpcTheoNC, pcKoTheoNC.tpcKoTheoNC     \n   "   +
				  "			from     \n   "   +
				  "			(     \n   "   +
				  "				select b.gsbh_fk, isnull(SUM(c.trongso), 0) as tpcTheoNC       \n   "   +
				  "				from TINHPHUCAP_CHUCVU a inner join  TINHPHUCAP_CHUCVU_GSBH b on a.PK_SEQ = b.tinhphucap_chucvu_fk       \n   "   +
				  "				left join TINHPHUCAP_CHUCVU_PHUCAP c on c.TINHPHUCAP_CHUCVU_FK = a.PK_SEQ  and c.tinhtheongaycong = '1'       \n   "   +
				  "				where  a.CHUCVU = 'SS' and a.tinhphucap_fk in ( select PK_SEQ from TinhPhuCap where thang = '"+obj.getTungay()+"' and nam = '"+obj.getDenngay()+"' and trangthai = '1' )        \n   "   +
				  "				group by b.gsbh_fk      \n   "   +
				  "			)     \n   "   +
				  "			pcTheoNC,       \n   "   +
				  "			(     \n   "   +
				  "				select b.gsbh_fk,isnull(SUM(d.trongso), 0) as tpcKoTheoNC        \n   "   +
				  "				from TINHPHUCAP_CHUCVU a inner join  TINHPHUCAP_CHUCVU_GSBH b on a.PK_SEQ = b.tinhphucap_chucvu_fk       \n   "   +
				  "				left join TINHPHUCAP_CHUCVU_PHUCAP d on d.TINHPHUCAP_CHUCVU_FK = a.PK_SEQ  and d.tinhtheongaycong = '0'      \n   "   +
				  "				where a.CHUCVU = 'SS' and a.tinhphucap_fk in ( select PK_SEQ from TinhPhuCap where thang = '"+obj.getTungay()+"' and nam = '"+obj.getDenngay()+"' and trangthai = '1' )       \n   "   +
				  "				group by b.gsbh_fk      \n   "   +
				  "			)     \n   "   +
				  "			pcKoTheoNC where pcTheoNC.gsbh_fk = pcKoTheoNC.gsbh_fk    \n   "   +
				  "		)     \n   "   +
				  "		tongphucap on thongtinluong.gsbhId = tongphucap.gsbh_fk     \n   "   +
				  "		left join     \n   "   +
				  "		( 	    \n   "   +
				  "			select Tu, Den, Thuong, '1' as type from TIEUCHITINHTHUONG_CHITIET      \n   "   +
				  "			where diengiai = N'Doanh số bán (Secondary sales)'     \n   "   +
				  "				and TieuChiTinhThuong_Fk in ( select pk_seq from TIEUCHITINHTHUONG where loai = '2' and thang = '"+obj.getTungay()+"' and nam = '"+obj.getDenngay()+"' )     \n   "   +
				  "		)     \n   "   +
				  "		ThuongDoanhSo  on ThuongDoanhSo.type = thongtinluong.type And ThuongDoanhSo.Tu <= ( ( isnull(chitieuNC.ttNhomChinh, 0 )+  isnull( chitieuNP.ttNhomPhu, 0 ) ) * 100 / ( ( isnull(chitieuNC.ctNhomChinh, 0) + isnull(chitieuNP.ctNhomPhu, 0) )  )  )    \n   "   +
				  "					 And ( (isnull(chitieuNC.ttNhomChinh, 0) + isnull(chitieuNP.ttNhomPhu, 0) ) * 100 / ( (isnull(chitieuNC.ctNhomChinh, 0) + isnull(chitieuNP.ctNhomPhu, 0) )  )  < ThuongDoanhSo.Den )    \n   "   +
				  "left join   \n   "   +
				  "(   \n   "   +
				  "	select thuongvuotmuc.gsbhId,SUM(tvm) as ThuongVuotMuc from   \n   "   +
				  "	(	   \n   "   +
				  "		select TrongSoNSP.kvId, TrongSoNSP.gsbhId, TrongSoNSP.nhomthuongId, TrongSoNSP.SLTT, TrongSoNSP.PT_Dat, TrongSoNSP.TRONGSO,       \n   "   +
				  "			IsNULL( (        \n   "   +
				  "						select b.thuong   \n   "   +
				  "						from THUONGVUOTMUC_CHUCVU_GSBH a inner join THUONGVUOTMUC_CHUCVU_TIEUCHI b on a.thuongvuotmuc_chucvu_fk = b.thuongvuotmuc_chucvu_fk      \n   "   +
				  "						where a.thuongvuotmuc_chucvu_fk in ( select pk_seq from THUONGVUOTMUC_CHUCVU where chucvu='SS' and thuongvuotmuc_fk in ( select pk_seq from ThuongVuotMuc where thang = '"+obj.getTungay()+"' and nam = '"+obj.getDenngay()+"' and trangthai = '1' ) )      \n   "   +
				  "							and a.gsbh_fk = TrongSoNSP.gsbhId	and b.tumuc <= TrongSoNSP.PT_Dat and TrongSoNSP.PT_Dat < b.denmuc and b.nhomthuong_fk = TrongSoNSP.nhomthuongId      \n   "   +
				  "					), 0)   as tvm        \n   "   +
				  "		from       \n   "   +
				  "		(      \n   "   +
				  "			select  chitieudera.kvId, chitieudera.gsbhId, chitieudera.nhomthuongId, thucdat.sltt,        \n   "   +
				  "				case when (  ( thucdat.sltt / chitieudera.chitieu ) * 100 ) < thuongvuotmuc.mucbaohiem then thuongvuotmuc.mucbaohiem        \n   "   +
				  "				else ( thucdat.sltt / chitieudera.chitieu ) * 100 end as PT_Dat,       \n   "   +
				  "				case thucdat.loainhom when 'Nhom Chinh' then 'CT01' else 'CT02' end as ma, thuongvuotmuc.TRONGSO	      \n   "   +
				  "			from       \n   "   +
				  "			(       \n   "   +
				  "				select  gsbh.KHUVUC_FK as kvId, c.gsbh_fk as gsbhId, nt.pk_seq as nhomthuongId, sum(d.chitieu) as Chitieu          \n   "   +
				  "				from chitieunpp a inner join  Chitieunpp_ddkd b on a.pk_seq = b.chitieunpp_fk         \n   "   +
				  "					inner join ddkd_gsbh c on b.ddkd_fk = c.ddkd_fk        \n   "   +
				  "					inner join ChitieuNPP_DDKD_NHOMSP d on d.ddkd_fk = b.ddkd_fk  and d.chitieunpp_fk = a.pk_seq       \n   "   +
				  "					inner join NHOMSANPHAM NSP on NSP.pk_seq = d.nhomsanpham_fk         \n   "   +
				  "					inner join Nhomthuong_NhomSP nt_nsp on nt_nsp.NhomSanPham_fk = NSP.pk_seq        \n   "   +
				  "					inner join Nhomthuong nt on nt_nsp.nhomthuong_fk = nt.pk_seq         \n   "   +
				  "					inner join GIAMSATBANHANG gsbh on c.GSBH_FK = gsbh.PK_SEQ        \n   "   +
				  "				where a.thang = '"+obj.getTungay()+"' and a.nam = '"+obj.getDenngay()+"' and a.trangthai = '1'  and nt.trangthai = '1' and NSP.loainhom = '3'       \n   "   +
				  "				group by gsbh.KHUVUC_FK, c.gsbh_fk, nt.pk_seq       \n   "   +
				  "			)      \n   "   +
				  "			chitieudera inner join       \n   "   +
				  "			(       \n   "   +
				  "				select   dh.GSBH_FK as GsbhId, nt.pk_seq as nhomthuongId,         \n   "   +
				  "					case when nt.loai = 1 then 'Nhom Chinh' else 'Nhom Con Lai' end as LoaiNhom, sum( a.soluong * isnull(sp.trongluong, 0) ) as SLTT          \n   "   +
				  "				from   donhang_sanpham a inner join nhomsanpham_sanpham b on a.sanpham_fk = b.sp_fk         \n   "   +
				  "					inner join nhomthuong_nhomsp c on b.nsp_fk = c.nhomsanpham_fk          \n   "   +
				  "					inner join NHOMSANPHAM NSP on NSP.pk_seq = b.nsp_fk         \n   "   +
				  "					inner join Nhomthuong nt on c.nhomthuong_fk = nt.pk_seq         \n   "   +
				  "					inner join TrongSoKPI_ChiTiet e on e.nhomthuong_fk = nt.pk_seq         \n   "   +
				  "					inner join TrongSoKPI ts on e.trongsokpi_fk = ts.pk_seq    \n   "   +
				  "					inner join TinhThuNhap_GSBH g on g.TRONGSOKPI_FK=ts.PK_SEQ    \n   "   +
				  "					inner join donhang dh on a.donhang_fk = dh.pk_seq and  dh.GSBH_FK=g.gsbh_fk        \n   "   +
				  "					inner join sanpham sp on a.sanpham_fk = sp.pk_seq          \n   "   +
				  "				where dh.ngaynhap like '"+ngaydh+"%' and dh.trangthai = '1' and nt.trangthai = '1' and NSP.loainhom = '3'        \n   "   +
				  "					and e.nhomthuong_fk is not null  and ts.chucvu = 'SS'         \n   "   +
				  "					and ts.tinhthunhap_fk in ( select pk_seq from TinhThuNhap  where trangthai = '1' and thang = '"+obj.getTungay()+"' and nam = '"+obj.getDenngay()+"'   )          \n   "   +
				  "				group by  dh.GSBH_FK, nt.pk_seq, nt.loai        \n   "   +
				  "			)       \n   "   +
				  "			thucdat on chitieudera.nhomthuongId = thucdat.nhomthuongId  and chitieudera.gsbhId = thucdat.GsbhId          \n   "   +
				  "			left join        \n   "   +
				  "			(        \n   "   +
				  "				select c.gsbh_fk as GsbhId,isnull(a.nhomthuong_fk, -1) as nhomthuongId, a.trongso, a.ma, a.mucbaohiem        \n   "   +
				  "				from TrongSoKPI_ChiTiet a inner join TrongSoKPI b on a.trongsoKPI_fk = b.pk_seq        \n   "   +
				  "						inner join TinhThuNhap_GSBH c on c.TRONGSOKPI_FK=b.PK_SEQ    \n   "   +
				  "				where b.chucvu = 'SS' and a.NHOMTHUONG_FK is not null and  b.tinhthunhap_fk in ( select pk_seq from TinhThuNhap where thang = '"+obj.getTungay()+"' and nam = '"+obj.getDenngay()+"' and trangthai = '1' )          \n   "   +
				  "			)       \n   "   +
				  "			thuongvuotmuc on   chitieudera.gsbhId=thuongvuotmuc.GsbhId and  chitieudera.nhomthuongId = thuongvuotmuc.nhomthuongId        \n   "   +
				  "		)      \n   "   +
				  "		TrongSoNSP      \n   "   +
				  "		group by TrongSoNSP.kvId, TrongSoNSP.gsbhId, TrongSoNSP.nhomthuongId, TrongSoNSP.PT_Dat, TrongSoNSP.SLTT, TrongSoNSP.TRONGSO      \n   "   +
				  "	)as thuongvuotmuc   \n   "   +
				  "	group by gsbhid   \n   "   +
				  ")thuong on thuong.gsbhId=thongtinluong.gsbhId		" +
				  " where 1=1 "		; 
		
	    }
		return query;
	}
	
}
