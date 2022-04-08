package geso.dms.center.servlets.reports;

import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

import geso.dms.center.db.sql.*;

public class AccumulatedPromotion extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public AccumulatedPromotion() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		dbutils db = new dbutils();
		String sql = "select pk_seq, scheme from ctkhuyenmai where loaict='3'";		
		ResultSet ctkmIds = db.get(sql);
		
		HttpSession session = request.getSession();
		String userTen = (String)session.getAttribute("userTen");
		String userId=	(String)session.getAttribute("userId");		
		session.setAttribute("userTen", userTen);
		session.setAttribute("userId", userId);
		session.setAttribute("baoloi", "");
		session.setAttribute("ctkmIds", ctkmIds);
		session.setAttribute("ctkmId", "");
		
		String nextJSP = "/AHF/pages/Center/AccumulatedPromotion.jsp";
		response.sendRedirect(nextJSP);		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		OutputStream out = response.getOutputStream(); 
		Utility util = new Utility();
		try
		{
			HttpSession session = request.getSession();
			String userTen = (String)session.getAttribute("userTen");
			String userId=	(String)session.getAttribute("userId");
			String ctkmId = util.antiSQLInspection(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ctkmId"))));
			if(userTen==null) 
				userTen ="";
			if(userId ==null) 
				userId ="";
			if(ctkmId ==null) 
				ctkmId ="";
			
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=KhuyenMaiTichLuy.xlsm");

	        CreatePivotTable(out, response, request, ctkmId, userId, userTen);
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
	
	private void CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request, String ctkmId, String userId, String userTen) throws IOException, SQLException
    {    
		String chuoi =  getServletContext().getInitParameter("path") + "\\KhuyenMaiTichLuy.xlsm";
		FileInputStream fstream = new FileInputStream(chuoi);
		//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\KhuyenMaiTichLuy.xlsm");
		//FileInputStream fstream = new FileInputStream(f) ;	
		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
	     
	     CreateStaticHeader(workbook, ctkmId, userId, userTen);

	     boolean result = CreateStaticData(workbook, ctkmId, userId, userTen);
	     if(result == false)
	     {
	    	dbutils db = new dbutils();
			String sql = "select pk_seq, scheme from ctkhuyenmai where loaict = '3'";		
			ResultSet ctkmIds = db.get(sql);
			
			HttpSession session = request.getSession();
			session.setAttribute("userTen", userTen);
			session.setAttribute("userId", userId);
			session.setAttribute("baoloi","Khong có báo cáo với điều kiện đã chọn" );
			
			String nextJSP = "/AHF/pages/Center/AccumulatedPromotion.jsp";
			response.sendRedirect(nextJSP);	
			
		    if(ctkmIds!=null)
			{
		    	ctkmIds.close();
		    }
		    db.shutDown();
	     }
	     else
	     {
	    	 workbook.save(out);
	    	 
	     }
	     fstream.close();
   }
	
	private void CreateStaticHeader(Workbook workbook, String ctkmId, String userId, String userTen) 
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");

		Cells cells = worksheet.getCells();

		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
	    cell.setValue("TRẢ KHUYẾN MẠI TÍCH LŨY");   	
	    
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A2");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A3");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + userTen);
	    
	    cell = cells.getCell("DA1"); cell.setValue("Vung");
	    cell = cells.getCell("DB1"); cell.setValue("KhuVuc");
	    cell = cells.getCell("DC1");cell.setValue("Scheme");
	    cell = cells.getCell("DD1"); cell.setValue("MaNhaPhanPhoi");
	    cell = cells.getCell("DE1"); cell.setValue("NhaPhanPhoi");
	    cell = cells.getCell("DF1"); cell.setValue("MaKhachHang");
	  	cell = cells.getCell("DG1"); cell.setValue("KhachHang");
	  	cell = cells.getCell("DH1"); cell.setValue("DiaChi");
	    cell = cells.getCell("DI1"); cell.setValue("SoXuatDangKy");
	    cell = cells.getCell("DJ1"); cell.setValue("SoXuatThoa");
	    cell = cells.getCell("DK1"); cell.setValue("DoanhSo");
	    cell = cells.getCell("DL1"); cell.setValue("PhanTram");
	    cell = cells.getCell("DM1"); cell.setValue("TongTien");

	}

	private boolean CreateStaticData(Workbook workbook, String ctkmId, String userId, String userTen) 
	{
		dbutils db = new dbutils();
		Utility util=new Utility();
		
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
		//la user nha phan phoi
	  
	    String IdNpp="";
	    boolean DuoiNPPLay=false;
	    String sql="select pk_seq from nhanvien where trangthai=1 and phanloai='1' and pk_seq="+userId;
	   ResultSet rs1=db.get(sql);
	   		try {
	   			if(rs1.next()){
				  
				   IdNpp=    util.getIdNhapp(userId);
				   DuoiNPPLay=true;
			   }
	   			rs1.close();
			} catch (Exception e1) {
				
			}
	    
	    
	    String query = "";
	    
	    query = "select top(1) tinhds.NGAYDS, tinhds.NGAYKTDS, c.PK_SEQ as dkkmId, ISNULL(c.tongluong, '0') as tongluong, ISNULL(c.tongtien, '0') as tongtien, LOAI   \n" +
	    		"from CTKHUYENMAI a inner join CTKM_DKKM b on a.PK_SEQ = b.CTKHUYENMAI_FK  \n" +
	    			"inner join DIEUKIENKHUYENMAI c on b.DKKHUYENMAI_FK = c.PK_SEQ  \n" +
	    			"inner join NGAYTINHDSKM tinhds on a.PK_SEQ = tinhds.CTKM_FK \n" +
	    		"where a.PK_SEQ = '" + ctkmId + "'";

		 ResultSet rsDieukien = db.get(query);
		 String tungay = "";
		 String denngay = "";
		 String dkkmId = "";
		 String loai = "";
		 
		 System.out.println("Lay loai dk ckmt..... "+query);
		 
		 if(rsDieukien != null)
		 {
			 try 
			 {
				if(rsDieukien.next())
				{
					tungay = rsDieukien.getString("NGAYDS");
					denngay = rsDieukien.getString("NGAYKTDS");
					dkkmId = rsDieukien.getString("dkkmId");
					loai = rsDieukien.getString("loai");
				}
				rsDieukien.close();
			}
			 catch (Exception e) 
			 {
				//System.out.println("115.Error: " + e.getMessage());
			}
		 }
	    
	    
		 if(loai.equals("2")) //bat ky trong
		 {
			 query = "select VUNG.TEN as vungTen, KHUVUC.TEN as kvTen, TRAKHUYENMAI.SCHEME, NHAPHANPHOI.MA as nppMa, NHAPHANPHOI.TEN as nppTen, khachhangmua.KHACHHANG_FK, KHACHHANG.SMARTID as MAKHACHHANG ,KHACHHANG.TEN as khTen, ISNULL(KHACHHANG.DIACHI, 'na') as DiaChi,  \n" +
			 			"ISNULL(KHACHHANG.DIENTHOAI, 'na') as DienThoai, khachhangmua.tongtien as DoanhSo, dangkytichluy.dangky, isnull(dieukien.tongtien, '0') as tongtienTheoDK, \n" +
			 		"case when dieukien.tongluong > 0 then khachhangmua.tongluong / dieukien.tongluong \n" +
				 		"when dieukien.tongtien > 0 then khachhangmua.tongtien / dieukien.tongtien \n" +
				 		"else 0 \n" +
				 		"end as soxuat, \n" +
			 		"trakhuyenmai.phantramtoida, trakhuyenmai.LOAI as loaitra, trakhuyenmai.ptChietKhau, trakhuyenmai.ttTrakm \n" +
			 		"from \n" +
			 		"( \n" +
				 		"select '" + ctkmId + "' as ctkmId, ISNULL(tongluong, 0) as tongluong, ISNULL(tongtien, 0) as tongtien, '1' as type \n" +
				 		"from DIEUKIENKHUYENMAI  \n" +
				 		"where PK_SEQ in (select DKKHUYENMAI_FK from CTKM_DKKM where CTKHUYENMAI_FK = '" + ctkmId + "') \n" +
			 		") \n" +
			 		"dieukien inner join \n" +
			 		"(			 \n" +
				 		"select '" + ctkmId + "' as ctkmId, a.NPP_FK, a.KHACHHANG_FK, SUM(b.SOLUONG * b.GIAMUA) as tongtien, SUM(b.soluong) as tongluong, '1' as type \n" +
				 		"from DONHANG a inner join DONHANG_SANPHAM b on a.PK_SEQ = b.DONHANG_FK  \n" +
				 		"where a.NGAYNHAP >= '" + tungay.trim() + "' and a.NGAYNHAP <= '" + denngay.trim() + "' and a.trangthai != '2' \n" +
				 			"and b.SANPHAM_FK in ( select a.sanpham_fk  \n" +
				 		"from  DIEUKIENKM_SANPHAM a inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ  \n" +
				 		"where DIEUKIENKHUYENMAI_FK in (select DKKHUYENMAI_FK from CTKM_DKKM where CTKHUYENMAI_FK = '" + ctkmId + "') ) \n" +
				 		"and a.NPP_FK in ( select NPP_FK from PHANBOKHUYENMAI where CTKM_FK = '" + ctkmId + "' ) \n" +
				 		"group by a.NPP_FK, KHACHHANG_FK \n" +
			 		")  \n" +
			 		"khachhangmua on dieukien.type = khachhangmua.type and dieukien.ctkmId = khachhangmua.ctkmId \n" +
			 		"inner join  \n" +
			 		"( \n" +
				 		"select ctkm.PK_SEQ as ctkmId, ctkm.SCHEME, tinhds.phantramtoida, trakm.LOAI, isnull(trakm.CHIETKHAU, '0') as ptChietKhau, isnull(trakm.TONGTIEN, '0') as ttTrakm \n" +
				 		"from CTKHUYENMAI ctkm inner join NGAYTINHDSKM tinhds on ctkm.PK_SEQ = tinhds.CTKM_FK \n" +
				 		"inner join CTKM_TRAKM ctkm_trakm on ctkm.PK_SEQ = ctkm_trakm.CTKHUYENMAI_FK  \n" +
				 		"inner join TRAKHUYENMAI trakm on ctkm_trakm.TRAKHUYENMAI_FK = trakm.PK_SEQ  \n" +
				 		"where ctkm.PK_SEQ= '" + ctkmId + "' \n" +
			 		") \n" +
			 		"trakhuyenmai on khachhangmua.ctkmId = trakhuyenmai.ctkmId \n" +
			 		"FULL OUTER join \n" +
			 		"( \n" +
				 		"select a.ctkm_fk as ctkmId, a.npp_fk, b.khachhang_fk, b.dangky \n" +
				 		"from DANGKYKM_TICHLUY a inner join DANGKYKM_TICHLUY_KHACHHANG b on a.pk_seq = b.dkkmtichluy_fk \n" +
				 		"where a.trangthai != 2 and a.ctkm_fk = '" + ctkmId + "' \n" +
			 		") \n" +
			 		"dangkytichluy on khachhangmua.NPP_FK = dangkytichluy.npp_fk and khachhangmua.KHACHHANG_FK = dangkytichluy.khachhang_fk   \n" +
			 				"and khachhangmua.ctkmId = dangkytichluy.ctkmId \n" +
			 		"inner join KHACHHANG on dangkytichluy.KHACHHANG_FK = KHACHHANG.PK_SEQ \n" +
			 		"inner join NHAPHANPHOI on dangkytichluy.NPP_FK = NHAPHANPHOI.PK_SEQ \n" +
			 		"inner join KHUVUC on NHAPHANPHOI.KHUVUC_FK = KHUVUC.PK_SEQ \n " +
			 		"inner join VUNG on KHUVUC.VUNG_FK = VUNG.PK_SEQ \n" +
			 		"where 1=1 \n";
			 	
			 if(DuoiNPPLay){
				query=query+" and  NHAPHANPHOI.PK_SEQ ="+IdNpp; 
			 }else{
				 query=query+" and  NHAPHANPHOI.PK_SEQ in "+util.quyen_npp(userId); 
			 }
		 }
		 else  //bat buoc tung san pham
		 {
			 query = "select VUNG.TEN as vungTen, KHUVUC.TEN as kvTen, TRAKHUYENMAI.SCHEME, NHAPHANPHOI.MA as nppMa, NHAPHANPHOI.TEN as nppTen, khachhangmua.KHACHHANG_FK, KHACHHANG.SMARTID as MAKHACHHANG, KHACHHANG.TEN as khTen, ISNULL(KHACHHANG.DIACHI, 'na') as DiaChi,  \n" +
				 		"ISNULL(KHACHHANG.DIENTHOAI, 'na') as DienThoai, sum(khachhangmua.tongtien) as DoanhSo, dangkytichluy.dangky, 0 as tongtienTheoDK \n" +
				 		"MIN(khachhangmua.tongluong / dieukien.SOLUONG) as soxuat, \n" +
				 		"trakhuyenmai.phantramtoida, trakhuyenmai.LOAI as loaitra, trakhuyenmai.ptChietKhau, trakhuyenmai.ttTrakm \n" +
			 		"from \n" +
			 		"( \n" +
				 		"select a.sanpham_fk, a.SOLUONG \n" +
				 		"from  DIEUKIENKM_SANPHAM a inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ  \n" +
				 		"where DIEUKIENKHUYENMAI_FK in (select DKKHUYENMAI_FK from CTKM_DKKM where CTKHUYENMAI_FK = '" + ctkmId + "')  \n" +
			 		")  \n" +
			 		"dieukien inner join \n" +
			 		"(						 \n" +
				 		"select '" + ctkmId + "' as ctkmId, a.NPP_FK, a.KHACHHANG_FK, b.SANPHAM_FK, SUM(b.SOLUONG * b.GIAMUA) as tongtien, SUM(b.soluong) as tongluong  \n" +
				 		"from DONHANG a inner join DONHANG_SANPHAM b on a.PK_SEQ = b.DONHANG_FK  \n" +
				 		"where a.NGAYNHAP >= '" + tungay.trim() + "' and a.NGAYNHAP <= '" + denngay.trim() + "' and a.trangthai != '2' \n" +
				 				"and a.NPP_FK in ( select NPP_FK from PHANBOKHUYENMAI where CTKM_FK = '" + ctkmId + "' ) \n" +
				 		"group by a.NPP_FK, KHACHHANG_FK, b.SANPHAM_FK \n" +
			 		") \n" +
			 		"khachhangmua on dieukien.SANPHAM_FK = khachhangmua.SANPHAM_FK \n" +
			 		"inner join  \n" +
			 		"( \n" +
				 		"select ctkm.PK_SEQ as ctkmId, ctkm.SCHEME, tinhds.phantramtoida, trakm.LOAI, \n" +
				 		"isnull(trakm.CHIETKHAU, '0') as ptChietKhau, isnull(trakm.TONGTIEN, '0') as ttTrakm \n" +
				 		"from CTKHUYENMAI ctkm inner join NGAYTINHDSKM tinhds on ctkm.PK_SEQ = tinhds.CTKM_FK  \n" +
				 		"inner join CTKM_TRAKM ctkm_trakm on ctkm.PK_SEQ = ctkm_trakm.CTKHUYENMAI_FK \n \n" +
				 		"inner join TRAKHUYENMAI trakm on ctkm_trakm.TRAKHUYENMAI_FK = trakm.PK_SEQ \n" +
				 		"where ctkm.PK_SEQ= '" + ctkmId + "' \n" +
			 		") \n" +
			 		"trakhuyenmai on khachhangmua.ctkmId = trakhuyenmai.ctkmId \n" +
			 		"Full outer join \n" +
			 		"( \n" +
				 		"select a.ctkm_fk as ctkmId, a.npp_fk, b.khachhang_fk, b.dangky \n" +
				 		"from DANGKYKM_TICHLUY a inner join DANGKYKM_TICHLUY_KHACHHANG b on a.pk_seq = b.dkkmtichluy_fk \n" +
				 		"where a.trangthai != 2 and a.ctkm_fk = '" + ctkmId + "' \n" +
			 		") \n" +
			 		"dangkytichluy on khachhangmua.NPP_FK = dangkytichluy.npp_fk and khachhangmua.KHACHHANG_FK = dangkytichluy.khachhang_fk  \n" +
			 		"and khachhangmua.ctkmId = dangkytichluy.ctkmId \n" +
			 		"inner join KHACHHANG on dangkytichluy.KHACHHANG_FK = KHACHHANG.PK_SEQ \n" +
			 		"inner join NHAPHANPHOI on dangkytichluy.NPP_FK = NHAPHANPHOI.PK_SEQ \n" +
			 		"inner join KHUVUC on NHAPHANPHOI.KHUVUC_FK = KHUVUC.PK_SEQ \n" +
			 		"inner join VUNG on KHUVUC.VUNG_FK = VUNG.PK_SEQ \n" +
			 		"where khachhangmua.tongluong >= dieukien.SOLUONG  \n" ;
			 		if(DuoiNPPLay){
					query=query+" and  NHAPHANPHOI.PK_SEQ ="+IdNpp; 
				 }else{
					 query=query+" and  NHAPHANPHOI.PK_SEQ in "+util.quyen_npp(userId); 
				 }
				query=query+ 	"   group by	VUNG.TEN, KHUVUC.TEN, TRAKHUYENMAI.SCHEME, NHAPHANPHOI.MA, NHAPHANPHOI.TEN, khachhangmua.KHACHHANG_FK,  " +
			 		"KHACHHANG.TEN, KHACHHANG.DIACHI, KHACHHANG.DIENTHOAI, dangkytichluy.dangky,  " +
			 		"trakhuyenmai.phantramtoida, trakhuyenmai.LOAI, trakhuyenmai.ptChietKhau, trakhuyenmai.ttTrakm  " ;
			 	}

		 System.out.println("1.Bao cao khuyen mai tich luy: " + query);
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
					String scheme = rs.getString("scheme");
					String maNpp = rs.getString("nppMa");
					String nppTen = rs.getString("nppTen");
					String maKh = rs.getString("MAKHACHHANG");	
					String tenKh = rs.getString("khTen");	
					String diachi = rs.getString("diachi");	
					int dangky = rs.getInt("dangky");	
					int soxuat = rs.getInt("soxuat");
					
					if(soxuat > dangky)
						soxuat = dangky;
					
					
					float phantram = rs.getFloat("phantramtoida");
					float tongtienTheoDK = rs.getFloat("tongtienTheoDK");
					float doanhso = rs.getFloat("doanhso");
					
					float ptChietkhauTKM = rs.getFloat("ptChietKhau");
					float ttTraKm = rs.getFloat("ttTraKM");
					
					double tonggiatriKM = 0;
					if(ptChietkhauTKM > 0)
					{
						tonggiatriKM = ptChietkhauTKM * doanhso / 100;
						//System.out.println("11.Gia tri theo chiet khau: " + tonggiatriKM);
					
						double tienToida =  dangky * ( phantram * tongtienTheoDK * ptChietkhauTKM ) / ( 100 * 100 ) ;
						if(tonggiatriKM > tienToida)
							tonggiatriKM =  tienToida;
					}
					else
					{
						tonggiatriKM = soxuat * ttTraKm;
						//System.out.println("22.Gia tri tongtien: " + tonggiatriKM);
						double tienToida =  dangky * ( phantram * tongtienTheoDK / 100) * ( ttTraKm / tongtienTheoDK );
						if(tonggiatriKM > tienToida)
							tonggiatriKM = tienToida;
					}
					
					
					
					//System.out.println("44.Tien toi da: " + tonggiatriKM);
					
					cell = cells.getCell("DA" + Integer.toString(i)); 	cell.setValue(vung);
					cell = cells.getCell("DB" + Integer.toString(i)); 	cell.setValue(khuvuc);
					cell = cells.getCell("DC" + Integer.toString(i)); 	cell.setValue(scheme);
					cell = cells.getCell("DD" + Integer.toString(i)); 	cell.setValue(maNpp);
					cell = cells.getCell("DE" + Integer.toString(i)); 	cell.setValue(nppTen);
					cell = cells.getCell("DF" + Integer.toString(i)); 	cell.setValue(maKh);				
					cell = cells.getCell("DG" + Integer.toString(i)); 	cell.setValue(tenKh);
					cell = cells.getCell("DH" + Integer.toString(i)); 	cell.setValue(diachi);
					cell = cells.getCell("DI" + Integer.toString(i)); 	cell.setValue(dangky);
					cell = cells.getCell("DJ" + Integer.toString(i)); 	cell.setValue(soxuat);
					cell = cells.getCell("DK" + Integer.toString(i)); 	cell.setValue(doanhso);
					cell = cells.getCell("DL" + Integer.toString(i)); 	cell.setValue(phantram);
					cell = cells.getCell("DM" + Integer.toString(i)); 	cell.setValue(tonggiatriKM);

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
				//System.out.println("115.Error: " + e.getMessage());
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
