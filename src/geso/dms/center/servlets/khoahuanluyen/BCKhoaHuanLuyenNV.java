package geso.dms.center.servlets.khoahuanluyen;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class BCKhoaHuanLuyenNV extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public BCKhoaHuanLuyenNV() {
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
		
		String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		if(view == null)
			view = "KHL";
		obj.setView(view);
		
		if(view.equals("KHL"))
		{
			String nextJSP = "/AHF/pages/Center/BCKhoaHuanLuyen.jsp";
			response.sendRedirect(nextJSP);
		}
		else
		{
			if(view.equals("DSNV"))
			{
				String nextJSP = "/AHF/pages/Center/BCDanhSachNhanVien.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				String nextJSP = "/AHF/pages/Center/TonKhoTheoQuyDinh.jsp";
				response.sendRedirect(nextJSP);
			}
		}
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
		
		obj.setTungay(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"))):"");
		
		obj.setDenngay(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"))):"");
		
		obj.setKbhId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId"))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId"))):"");
		
		obj.setVungId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"))):"");
			
		obj.setKvId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"))):"");
		
		obj.setNppId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"))):"");
		
		obj.setGia(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gia")!=null?util.antiSQLInspection(request.getParameter("gia")):""));
		
		
		String[] khlIds = request.getParameterValues("khlIds");
		String khoahuanluyen = "";
		if(khlIds != null)
		{
			for(int i = 0; i < khlIds.length; i++ )
				khoahuanluyen += khlIds[i] + ",";
			
			if(khoahuanluyen.length() > 0)
				khoahuanluyen = khoahuanluyen.substring(0, khoahuanluyen.length() - 1);
			
			obj.setKhlIds(khoahuanluyen);
		}
		
		String muclay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("muclay"));
		if(muclay == null)
			muclay = "0";

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action") != null?
				util.antiSQLInspection(request.getParameter("action")) : "");
		String nextJSP = "";
		
		String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		if(view == null)
			view = "KHL";
		
		if(view.equals("KHL"))
			nextJSP = "/AHF/pages/Center/BCKhoaHuanLuyen.jsp";
		else
		{
			if(view.equals("DSNV"))
			{
				nextJSP = "/AHF/pages/Center/BCDanhSachNhanVien.jsp";
			}
			else
			{
				nextJSP = "/AHF/pages/Center/TonKhoTheoQuyDinh.jsp";
			}
		}
		
		if(action.equals("Taomoi"))
		{
			try
			{
				if(view.equals("KHL"))
				{
					response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition", "attachment; filename=KhoaHuanLuyen.xlsm");
					if(!CreatePivotTable(out, obj, muclay))
			        {
			        	obj.setMsg("Không có dữ liệu trong thời gian này.");
			        	obj.initBC("");
			    		session.setAttribute("obj", obj);	
			    		response.sendRedirect(nextJSP);
			    		return;
			        }
				}
				else
				{
					if(view.equals("DSNV"))
					{
						response.setContentType("application/xlsm");
						response.setHeader("Content-Disposition", "attachment; filename=DanhSachNhanVien.xlsm");
						if(!CreatePivotTable_DSNV(out, obj, muclay))
				        {
				        	obj.setMsg("Không có dữ liệu trong thời gian này.");
				        	obj.initBC("");
				    		session.setAttribute("obj", obj);	
				    		response.sendRedirect(nextJSP);
				    		return;
				        }
					}
					else
					{
						String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
						if(tungay == null)
							tungay = getDateTime();
						obj.setTungay(tungay);
						
						String[] nppIds = request.getParameterValues("nppIds");
						String[] ngayquydinh = request.getParameterValues("ngayquydinh");
						
						response.setContentType("application/xlsm");
						response.setHeader("Content-Disposition", "attachment; filename=TonKhoTheoQuyDinh.xlsm");
						if(!CreatePivotTable_TKQD(out, obj, nppIds, ngayquydinh))
				        {
				        	obj.setMsg("Không có dữ liệu trong thời gian này.");
				        	obj.initBC("");
				    		session.setAttribute("obj", obj);	
				    		response.sendRedirect(nextJSP);
				    		return;
				        }
					}
				}
			}
			catch(Exception ex)
			{
				obj.setMsg(ex.getMessage());
				obj.initBC("");
				session.setAttribute("obj", obj);	
				response.sendRedirect(nextJSP);
				
			}
		}else{
		 
			obj.initBC("");
			session.setAttribute("obj", obj);	
			response.sendRedirect(nextJSP);
		}
	
		
	}

	
	/***************** Begin Danh Sach Nhan vien ***********************/
	private boolean CreatePivotTable_DSNV(OutputStream out, IKhoahuanluyenList obj, String muclay) throws Exception 
	{
		String chuoi = getServletContext().getInitParameter("path") + "\\BCDanhSachNhanVien.xlsm";
		
		FileInputStream fstream = new FileInputStream(chuoi);
		//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\BCDanhSachNhanVien.xlsm");
		//FileInputStream fstream = new FileInputStream(f) ;
		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateStaticHeader_DSNV(workbook, obj, muclay);

		boolean isFill = CreateStaticData_DSNV(workbook, obj, muclay);
		
		if (!isFill){
			fstream.close();
			return false;
		}else {
			workbook.save(out);
			fstream.close();
			return true;
		}
	}

	private void CreateStaticHeader_DSNV(Workbook workbook, IKhoahuanluyenList obj, String muclay) throws Exception 
	{
		try
		{	
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");

			Cells cells = worksheet.getCells();

			cells.setRowHeight(0, 20.0f);
			Cell cell = cells.getCell("A1");
		    cell.setValue("DANH SÁCH NHÂN VIÊN");   	
		    
		    cells.setRowHeight(4, 18.0f);
		    cell = cells.getCell("A2");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
		    
		    cells.setRowHeight(5, 18.0f);
		    cell = cells.getCell("A3");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getUserTen());
			
		    cell = cells.getCell("DA1"); 	cell.setValue("Vung");  					ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DB1"); 	cell.setValue("KhuVuc");  					ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DC1"); 	cell.setValue("TenNhanVien");  				ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DD1"); 	cell.setValue("MANVERP");  				ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DE1"); 	cell.setValue("ChucVu");  					ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DF1"); 	cell.setValue("DienThoai");  				ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DG1"); 	cell.setValue("Email"); 				 	ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DH1"); 	cell.setValue("DiaChi");  					ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DI1"); 	cell.setValue("CMND"); 	 					ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DJ1"); 	cell.setValue("NgaySinh");  				ReportAPI.setCellHeader(cell);	 
		    cell = cells.getCell("DK1"); 	cell.setValue("QueQuan");  					ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DL1"); 	cell.setValue("NhaPhanPhoi");  				ReportAPI.setCellHeader(cell);
		    

			cell = cells.getCell("DM1"); 	cell.setValue("TrangThai");  				ReportAPI.setCellHeader(cell);
			cell = cells.getCell("DN1"); 	cell.setValue("MaNV");  					ReportAPI.setCellHeader(cell);
			cell = cells.getCell("DO1"); 	cell.setValue("NgayBatDau");  				ReportAPI.setCellHeader(cell);
			cell = cells.getCell("DP1"); 	cell.setValue("NgayKetThuc");  				ReportAPI.setCellHeader(cell);
			cell = cells.getCell("DQ1"); 	cell.setValue("GiamSatBanHang");  			ReportAPI.setCellHeader(cell);
			cell = cells.getCell("DR1"); 	cell.setValue("GiamSatBanHangTienNhiem"); 	ReportAPI.setCellHeader(cell);
			cell = cells.getCell("DS1"); 	cell.setValue("ChuTaiKhoan");  				ReportAPI.setCellHeader(cell);
			cell = cells.getCell("DT1"); 	cell.setValue("SoTaiKhoan"); 		 		ReportAPI.setCellHeader(cell);
			cell = cells.getCell("DU1"); 	cell.setValue("NganHang");  				ReportAPI.setCellHeader(cell);
			cell = cells.getCell("DV1"); 	cell.setValue("ChiNhanh"); 		 			ReportAPI.setCellHeader(cell);
		}
		catch(Exception ex)
		{
			throw new Exception("Bao cao bi loi khi khoi tao");
		}
	}
	
	private boolean CreateStaticData_DSNV(Workbook workbook, IKhoahuanluyenList obj, String muclay) 
	{
		geso.dms.center.util.Utility util=new geso.dms.center.util.Utility();
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
		
	    String query = 
	    		"select '' as NVERP,a.ten, isnull(a.dienthoai, 'NA') as dienthoai, isnull(a.EMAIL, 'NA') as email, isnull(a.diachi, 'NA') as diachi, isnull(a.cmnd, 'NA') as cmnd, " +
	    		"isnull(a.ngaysinh, 'NA') as ngaysinh, isnull(a.quequan, 'NA') as quequan, '' as nppTen, c.PK_SEQ as kvId, c.ten as kvTen, d.PK_SEQ as vungId, d.ten as vungTen, 'ASM' as chucvu, " +
	    		"CASE WHEN a.trangthai = '1' THEN N'Hoạt động' ELSE N'Ngưng hoạt động' END AS TRANGTHAI, cast(a.SmartID as nvarchar(50)) as ma, b.ngaybatdau, b.ngayketthuc, '' as gsbh, '' as gsbhtn, '' as chutk, '' as sotk, '' as nganhang, '' as chinhanh " +
	    		"from ASM a " +
	    		"inner join ASM_KHUVUC b on a.PK_SEQ = b.ASM_FK inner join KHUVUC c on b.KHUVUC_FK = c.PK_SEQ " +
	    		"inner join VUNG d on c.VUNG_FK = d.PK_SEQ " +
	    		"where a.TRANGTHAI in ('1','0') " +	    				
	    		"union all " +
	    		
	    		/*"select a.ten, isnull(a.dienthoai, 'NA') as dienthoai, isnull(a.EMAIL, 'NA') as email, isnull(a.diachi, 'NA') as diachi, isnull(a.cmnd, 'NA') as cmnd, " +
	    		"isnull(a.ngaysinh, 'NA') as ngaysinh, isnull(a.quequan, 'NA') as quequan, '' as nppTen, b.PK_SEQ as kvId, b.ten as kvTen, c.PK_SEQ as vungId, c.ten as vungTen, 'SS' as chucvu, " +
	    		"a.trangthai, a.pk_seq as ma, b.ngaybatdau, b.ngayketthuc, '' as gsbh, '' as gsbhtn, '' as chutk, '' as sotk, '' as nganhang, '' as chinhanh " +
	    		"from GIAMSATBANHANG a " +
	    		"INNER JOIN GSBH_KHUVUC GSKV ON GSKV.GSBH_FK = A.PK_SEQ " +
	    		"INNER JOIN NHAPP_GIAMSATBH NPPGS ON NPPGS.GSBH_FK = A.PK_SEQ " +
	    		"INNER JOIN KHUVUC B ON GSKV.KHUVUC_FK = B.PK_SEQ INNER JOIN VUNG C ON B.VUNG_FK = C.PK_SEQ " +	    	
	    		"inner join KHUVUC b on a.KHUVUC_FK = b.PK_SEQ inner join VUNG c on b.VUNG_FK = c.PK_SEQ  " +
	    		"where a.TRANGTHAI = '1' and a.pk_seq in (select  gsbh_fk from nhapp_giamsatbh where npp_fk in  "+util.quyen_npp(obj.getUserId())+" ) " +*/
	    		
				"select '' as NVERP,a.ten, isnull(a.dienthoai, 'NA') as dienthoai, isnull(a.EMAIL, 'NA') as email, isnull(a.diachi, 'NA') as diachi, isnull(a.cmnd, 'NA') as cmnd, " +
				"isnull(a.ngaysinh, 'NA') as ngaysinh, isnull(a.quequan, 'NA') as quequan, NPP.TEN as nppTen, b.PK_SEQ as kvId, b.ten as kvTen, c.PK_SEQ as vungId, c.ten as vungTen, 'SS' as chucvu, " +
				"CASE WHEN a.trangthai = '1' THEN N'Hoạt động' ELSE N'Ngưng hoạt động' END AS TRANGTHAI, cast(a.SmartID as nvarchar(50)) as ma, " +
				"NPPGS.ngaybatdau, NPPGS.ngayketthuc, " +
				"'' as gsbh, ISNULL(GSBH.TEN, '') as gsbhtn, ISNULL(A.chutaikhoan,'') as chutk, ISNULL(A.taikhoan,'') as sotk, ISNULL(A.nganhang,'') as nganhang, ISNULL(A.ChiNhanh,'') as chinhanh " +
				"from GIAMSATBANHANG a " +
				"INNER JOIN GSBH_KHUVUC GSKV ON GSKV.GSBH_FK = A.PK_SEQ " +
				"INNER JOIN NHAPP_GIAMSATBH NPPGS ON NPPGS.GSBH_FK = A.PK_SEQ " +
				"INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = NPPGS.NPP_FK " +
				"INNER JOIN KHUVUC B ON GSKV.KHUVUC_FK = B.PK_SEQ INNER JOIN VUNG C ON B.VUNG_FK = C.PK_SEQ " +
				"LEFT JOIN GIAMSATBANHANG GSBH ON GSBH.PK_SEQ = A.GSBHTN_FK " +
				"where a.TRANGTHAI in ('1','0') AND NPP.TRANGTHAI = '1' and a.pk_seq in (select  gsbh_fk from nhapp_giamsatbh where npp_fk in  "+util.quyen_npp(obj.getUserId())+" ) " +
					    		
	    		"union all " +
	    		/*"select a.ten, isnull(a.dienthoai, 'NA') as dienthoai, 'NA' as email, isnull(a.diachi, 'NA') as diachi, isnull(a.cmnd, 'NA') as cmnd, " +
	    		"isnull(a.ngaysinh, 'NA') as ngaysinh, isnull(a.quequan, 'NA') as quequan, b.ten as nppTen, c.PK_SEQ as kvId, c.ten as kvTen, d.PK_SEQ as vungId, d.ten as vungTen, 'SR' as chucvu, " +
	    		" " +
	    		"from DAIDIENKINHDOANH a " +
	    		"inner join NHAPHANPHOI b on a.NPP_FK = b.PK_SEQ " +
	    		"inner join KHUVUC c on b.KHUVUC_FK = c.PK_SEQ inner join VUNG d on c.VUNG_FK = d.PK_SEQ " +
	    		"where a.TRANGTHAI = '1' and b.pk_seq in  "+ util.quyen_npp(obj.getUserId());*/
	    
			    "select  ISNULL(A.MAERP,'') as NVERP,a.ten, isnull(a.dienthoai, 'NA') as dienthoai, 'NA' as email, isnull(a.diachi, 'NA') as diachi, isnull(a.cmnd, 'NA') as cmnd, " +
			    "isnull(a.ngaysinh, 'NA') as ngaysinh, isnull(a.quequan, 'NA') as quequan, b.ten as nppTen, c.PK_SEQ as kvId, c.ten as kvTen, d.PK_SEQ as vungId, d.ten as vungTen, 'SR' as chucvu, " +
			    "CASE WHEN a.trangthai = '1' THEN N'Hoạt động' ELSE N'Ngưng hoạt động' END AS TRANGTHAI, cast(a.SmartID as nvarchar(50)) as ma, ISNULL(A.NGAYBATDAU, '') AS NGAYBATDAU, ISNULL(A.NGAYKETTHUC, '') AS NGAYKETTHUC, " +
			    "GSBH.TEN as gsbh, '' as gsbhtn, ISNULL(A.chutaikhoan,'') as chutk, ISNULL(A.taikhoan,'') as sotk, ISNULL(A.nganhang,'') as nganhang, '' as chinhanh " +
			    "from DAIDIENKINHDOANH a " +
			    "inner join NHAPHANPHOI b on a.NPP_FK = b.PK_SEQ " +
			    "inner join KHUVUC c on b.KHUVUC_FK = c.PK_SEQ inner join VUNG d on c.VUNG_FK = d.PK_SEQ " +
			    "INNER JOIN DDKD_GSBH DDGS ON DDGS.DDKD_FK = A.PK_SEQ " +
			    "INNER JOIN GIAMSATBANHANG GSBH ON GSBH.PK_SEQ = DDGS.GSBH_FK " +
			    "where a.TRANGTHAI in ('1','0') and b.pk_seq in  "+ util.quyen_npp(obj.getUserId());
	    
	    query = " select * from ( " + query + " ) dsnv where 1 = 1 ";
	    
	    if(obj.getVungId().length() > 0)
	    	query += " and vungId = '" + obj.getVungId() + "' ";
	    
	    if(obj.getKvId().length() > 0)
	    	query += " and kvId = '" + obj.getKvId() + "' ";
	    
	    if(muclay.length() > 0 && !muclay.equals("0") )
	    	query += " and chucvu = '" + muclay + "' ";
	
		System.out.println("1.DSNV: " + query);
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
					String ten = rs.getString("ten");
					String chucvu = rs.getString("chucvu");
					String dienthoai = rs.getString("dienthoai");
					String email = rs.getString("email");
					String diachi = rs.getString("diachi");
					String cmnd = rs.getString("cmnd");	
					String ngaysinh = rs.getString("ngaysinh");	
					String quequan = rs.getString("quequan");	
					String nppTen = rs.getString("nppTen");
					String NVERP = rs.getString("NVERP");
					String trangthai = rs.getString("trangthai");
					String ma = rs.getString("ma");
					String ngaybd = rs.getString("ngaybatdau");
					String ngaykt = rs.getString("ngayketthuc");
					String gsbh = rs.getString("gsbh");
					String gsbhtn = rs.getString("gsbhtn");
					String chutk = rs.getString("chutk");
					String sotk = rs.getString("sotk");
					String nganhang = rs.getString("nganhang");
					String chinhanh = rs.getString("chinhanh");
            		
					cell = cells.getCell("DA" + Integer.toString(i)); 	cell.setValue(vung);
					cell = cells.getCell("DB" + Integer.toString(i)); 	cell.setValue(khuvuc);
					cell = cells.getCell("DC" + Integer.toString(i)); 	cell.setValue(ten);
					cell = cells.getCell("DD" + Integer.toString(i)); 	cell.setValue(NVERP);
					cell = cells.getCell("DE" + Integer.toString(i)); 	cell.setValue(chucvu);
					cell = cells.getCell("DF" + Integer.toString(i)); 	cell.setValue(dienthoai);
					cell = cells.getCell("DG" + Integer.toString(i)); 	cell.setValue(email);				
					cell = cells.getCell("DH" + Integer.toString(i)); 	cell.setValue(diachi);
					cell = cells.getCell("DI" + Integer.toString(i)); 	cell.setValue(cmnd);
					cell = cells.getCell("DJ" + Integer.toString(i)); 	cell.setValue(ngaysinh);
					cell = cells.getCell("DK" + Integer.toString(i)); 	cell.setValue(quequan);
					cell = cells.getCell("DL" + Integer.toString(i)); 	cell.setValue(nppTen);
					
					cell = cells.getCell("DM" + Integer.toString(i)); 	cell.setValue(trangthai);
					cell = cells.getCell("DN" + Integer.toString(i)); 	cell.setValue(ma);
					cell = cells.getCell("DO" + Integer.toString(i)); 	cell.setValue(ngaybd);
					cell = cells.getCell("DP" + Integer.toString(i)); 	cell.setValue(ngaykt);
					cell = cells.getCell("DQ" + Integer.toString(i)); 	cell.setValue(gsbh);
					cell = cells.getCell("DR" + Integer.toString(i)); 	cell.setValue(gsbhtn);
					cell = cells.getCell("DS" + Integer.toString(i)); 	cell.setValue(chutk);
					cell = cells.getCell("DT" + Integer.toString(i)); 	cell.setValue(sotk);
					cell = cells.getCell("DU" + Integer.toString(i)); 	cell.setValue(nganhang);
					cell = cells.getCell("DV" + Integer.toString(i)); 	cell.setValue(chinhanh);

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
	
	/***************** End Danh Sach Nhan vien ***********************/

	
	
	/***************** Begin Khoa Huan Luyen ***********************/
	private boolean CreatePivotTable(OutputStream out, IKhoahuanluyenList obj, String muclay) throws Exception 
	{
		String chuoi = "";
		File f;
		if(muclay.equals("0"))
			chuoi = getServletContext().getInitParameter("path") + "\\KhoaHuanLuyen.xlsm";
	/*	{
			f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\KhoaHuanLuyen.xlsm");
			
		}*/
			else
			chuoi = getServletContext().getInitParameter("path") + "\\KhoaHuanLuyen_GSBH.xlsm";
			/*{
				f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\KhoaHuanLuyen_GSBH.xlsm");
				
			}*/
		FileInputStream fstream = new FileInputStream(chuoi);
			
		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateStaticHeader(workbook, obj, muclay);

		boolean isFill = CreateStaticData(workbook, obj, muclay);
		
		if (!isFill){
			fstream.close();
			return false;
		}else {
			workbook.save(out);
			fstream.close();
			return true;
		}
	}
	
	private void CreateStaticHeader(Workbook workbook, IKhoahuanluyenList obj, String muclay) throws Exception 
	{
		try
		{	
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");

			Cells cells = worksheet.getCells();

			cells.setRowHeight(0, 20.0f);
			Cell cell = cells.getCell("A1");
		    cell.setValue("KHÓA HUẤN LUYỆN NHÂN VIÊN");   	
		    
		    cells.setRowHeight(4, 18.0f);
		    cell = cells.getCell("A2");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
		    
		    cells.setRowHeight(5, 18.0f);
		    cell = cells.getCell("A3");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getUserTen());
			
		    cell = cells.getCell("DA1"); 	cell.setValue("Vung");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DB1"); 	cell.setValue("KhuVuc");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DC1"); 	cell.setValue("KhoaHuanLuyen");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DD1"); 	cell.setValue("TuNgay");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DE1"); 	cell.setValue("DenNgay");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DF1"); 	cell.setValue("MaNhaPhanPhoi");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DG1"); 	cell.setValue("TenNhaPhanPhoi");  ReportAPI.setCellHeader(cell);
		    
		    if(muclay.equals("0"))
		    {
		    	cell = cells.getCell("DH1"); 	cell.setValue("DaiDienKinhDoanh");  ReportAPI.setCellHeader(cell);
		    }
		    else
		    {
		    	cell = cells.getCell("DH1"); 	cell.setValue("GiamSatBanHang");  ReportAPI.setCellHeader(cell);
		    }
		    

		}
		catch(Exception ex)
		{
			throw new Exception("Bao cao bi loi khi khoi tao");
		}
	}
	
	private boolean CreateStaticData(Workbook workbook, IKhoahuanluyenList obj, String muclay) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
		geso.dms.center.util.Utility util=new geso.dms.center.util.Utility();
	    String query = "";
	    if(muclay.equals("0")) //Lay theo Dai Dien Kinh Doanh
	    {
	    	query = "select a.tieude, a.tungay, a.denngay, d.TEN as ddkdTen, e.ma as nppMa, e.TEN as nppTen, f.TEN as kvTen, g.TEN as vungTen " +
	    			"from KHOAHUANLUYEN a inner join KHOAHUANLUYEN_DDKD b on a.pk_seq = b.khoahuanluyen_fk " +
	    			"inner join DAIDIENKINHDOANH d on b.ddkd_fk = d.PK_SEQ " +
	    			"inner join NHAPHANPHOI e on d.NPP_FK = e.PK_SEQ " +
	    			"inner join KHUVUC f on e.KHUVUC_FK = f.PK_SEQ " +
	    			"inner join VUNG g on f.vung_fk = g.pk_seq  " +
	    			"where b.trangthai = '1' and e.pk_seq in  "+util.quyen_npp(obj.getUserId()) ;
	    }
	    else  //Lay theo GSBH
	    {
	    	query = "select a.tieude, a.tungay, a.denngay, d.TEN as ddkdTen, 'na' as nppMa, 'na' as nppTen, f.TEN as kvTen, g.TEN as vungTen " +
	    			"from KHOAHUANLUYEN a inner join KHOAHUANLUYEN_GSBH b on a.pk_seq = b.khoahuanluyen_fk " +
	    			"inner join GIAMSATBANHANG d on b.gsbh_fk = d.PK_SEQ  " +
	    			"inner join KHUVUC f on d.KHUVUC_FK = f.PK_SEQ " +
	    			"inner join VUNG g on f.vung_fk = g.pk_seq  " +
	    			"where b.trangthai = '1' and d.pk_seq in (select  gsbh_fk from nhapp_giamsatbh where npp_fk in  "+util.quyen_npp(obj.getUserId())+" )";
	    }
	    
	    if(obj.getKhlId().length() > 0)
	    	query += " and a.pk_seq in (" + obj.getKhlId() + ") ";
	    
	    if(obj.getVungId().length() > 0)
	    	query += " and g.pk_seq = '" + obj.getVungId() + "' ";
	    
	    if(obj.getKvId().length() > 0)
	    	query += " and f.pk_seq = '" + obj.getKvId() + "' ";
	    
	    query += " order by d.pk_seq asc ";
	
		System.out.println("1.Khoa huan luyen: " + query);
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
					String tieude = rs.getString("tieude");
					String tungay = rs.getString("tungay");
					String denngay = rs.getString("denngay");
					String maNpp = rs.getString("nppMa");
					String nppTen = rs.getString("nppTen");
					String ddkdTen = rs.getString("ddkdTen");	
            		
					cell = cells.getCell("DA" + Integer.toString(i)); 	cell.setValue(vung);
					cell = cells.getCell("DB" + Integer.toString(i)); 	cell.setValue(khuvuc);
					cell = cells.getCell("DC" + Integer.toString(i)); 	cell.setValue(tieude);
					cell = cells.getCell("DD" + Integer.toString(i)); 	cell.setValue(tungay);
					cell = cells.getCell("DE" + Integer.toString(i)); 	cell.setValue(denngay);
					cell = cells.getCell("DF" + Integer.toString(i)); 	cell.setValue(maNpp);				
					cell = cells.getCell("DG" + Integer.toString(i)); 	cell.setValue(nppTen);
					cell = cells.getCell("DH" + Integer.toString(i)); 	cell.setValue(ddkdTen);

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

	/***************** End Khoa Huan Luyen ***********************/
	
	
	/***************** Begin Ton Kho Theo Quy Dinh ***********************/
	private boolean CreatePivotTable_TKQD(OutputStream out, IKhoahuanluyenList obj, String[] nppIds, String[] ngayquydinh) throws Exception
	{
		String chuoi = getServletContext().getInitParameter("path") + "\\TonKhoTheoQuyDinh.xlsm";
		
		FileInputStream fstream = new FileInputStream(chuoi);
			
		//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\TonKhoTheoQuyDinh.xlsm");
		//FileInputStream fstream = new FileInputStream(f) ;
		
		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateStaticHeader_TKQD(workbook, obj);

		boolean isFill = CreateStaticData_TKQD(workbook, obj, nppIds, ngayquydinh);
		
		if (!isFill){
			fstream.close();
			return false;
		}else {
			workbook.save(out);
			fstream.close();
			return true;
		}
	}
	
	private void CreateStaticHeader_TKQD(Workbook workbook, IKhoahuanluyenList obj) throws Exception
	{
		try
		{	
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");

			Cells cells = worksheet.getCells();

			cells.setRowHeight(0, 20.0f);
			Cell cell = cells.getCell("A1");
		    cell.setValue("TỒN KHO THEO QUY ĐỊNH");   	
		    
		    cells.setRowHeight(4, 18.0f);
		    cell = cells.getCell("A2");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
		    
		    cells.setRowHeight(5, 18.0f);
		    cell = cells.getCell("A3");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getUserTen());
			
		    cell = cells.getCell("DA1"); 	cell.setValue("Vung");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DB1"); 	cell.setValue("KhuVuc");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DC1"); 	cell.setValue("NhaPhanPhoi");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DD1"); 	cell.setValue("SoNgayLamViec");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DE1"); 	cell.setValue("ChiTieu");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DF1"); 	cell.setValue("TonQuyDinh");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DG1"); 	cell.setValue("TonThucTe");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DH1"); 	cell.setValue("Ngay");  ReportAPI.setCellHeader(cell);
		    
		}
		catch(Exception ex)
		{
			throw new Exception("Bao cao bi loi khi khoi tao");
		}
	}

	private boolean CreateStaticData_TKQD(Workbook workbook, IKhoahuanluyenList obj, String[] nppIds, String[] ngayquydinh) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
		geso.dms.center.util.Utility util=new geso.dms.center.util.Utility();
		
	    String nam = obj.getTungay().substring(0, 4);
	    String thang = obj.getTungay().substring(5, 7);	    	  
	    	    
	    Integer tungay = Integer.parseInt(obj.getTungay().substring(8,obj.getTungay().trim().length()));
	    Integer denngay = Integer.parseInt(obj.getDenngay().substring(8,obj.getDenngay().trim().length()));
	    int i = 2;
	    System.out.println("Tu Ngay: " + tungay + "Den ngay: " + denngay + "Thang: " + thang + " -- Nam: " + nam);
	    
	    String query = "";	    
	    if(nppIds != null)
	    {
			
	    for(int k=tungay; k<=denngay; k++ )
	    {
	    	String str = "00"+k;
	    	System.out.println("STR : "+str);
	    	String d = str.substring(str.length()-2, str.length());
	    	System.out.println("D : "+d);
	    	String temp = "";
		    	for(int z = 0; z < nppIds.length; z++)
		    	{
		    		if(ngayquydinh[z] == "")
		    			ngayquydinh[z] = "0";
		    		
		    		temp +=  " select " + nppIds[z] + " as nppId, '" + ngayquydinh[z] + "' as ngaytonkho union";
		    	}
		    	
		    	if(temp.trim().length() > 0)
		    	{
		    		temp = temp.substring(0, temp.length() - 6);
		    	}
		    	
		    	query = 
		    		" select v.pk_seq as vungId, v.ten as vungTen, kv.pk_seq as kvId, kv.ten as kvTen, npp.ten as nppTen, tonquydinh.nppId, \n" +
					" tonquydinh.songaylamviec, tonquydinh.chitieu, tonquydinh.tonquydinh, tonthucte.tonkhoNpp,'"+obj.getTungay().substring(0,8)+d+"' as ngay  \n" +
					" from \n" +
					" ( \n" +
					"	select temp.nppId, isnull(chitieu.SONGAYLAMVIEC, 0) as SONGAYLAMVIEC, isnull(chitieu.ct, 0) as chitieu,  \n" +
					"	ISNULL( cast( (chitieu.ct *  temp.ngaytonkho   / chitieu.SONGAYLAMVIEC ) as numeric(18, 0) ) , 0) as tonquydinh, '1' as loaiTon    \n" +
					"	from \n" +
					"	( \n" +
							temp +
					"	) temp \n" +
							
					"	left join \n" +
					"	( \n" +
					"		select b.nhapp_fk as nppId, a.songaylamviec, b.chitieu as ct \n" +
					"		from CHITIEU a inner join CHITIEU_NHAPP b on a.pk_seq = b.chitieu_fk   \n" +
					"		where a.trangthai = '1' and a.thang = '" + Integer.parseInt(thang) +  "' and a.nam = '" + nam + "'	\n " +
					"	) chitieu on temp.nppId = chitieu.nppId \n" +
					") tonquydinh \n" +
					" INNER join \n" +
					"( \n" +
						" select tonngay.NPP_FK,SUM( tonngay.tongtonkho ) as tonkhoNpp, '1' as loaiTon      \n" +  
					   "  	from  \n" +  
					   "  	(  \n" +  
					   "  		select npp_fk, isnull(sum(a.SOLUONG * b.TRONGLUONG ), 0) as tongtonkho, '1' as type    \n" +  
					   "  		from tonkhongay a inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ where  KHO_FK=100000 and ngay = '" + obj.getTungay().substring(0,8)+d + "'  \n" +  
					   "  		group by npp_fk  \n" +  
					   "  		 union all \n" +  
					   "  		select a.NPP_FK, sum( cast(b.soluong as float) * c.TRONGLUONG ) as tongluong, '1' as type    \n" +  
					   "  		from hdnhaphang a inner  join hdnhaphang_sp b on a.pk_seq = b.nhaphang_fk    \n" +  
					   "  		inner join SANPHAM c on b.SANPHAM_FK = c.MA   \n" +  
					   "  		where a.ngaychungtu <=  '" + obj.getTungay().substring(0,8)+d + "' and a.ngaychungtu >= '2013-10-01' and a.trangthai != '2'   AND  ISNULL(a.LOAIDONHANG,'0')='0'   \n" +  
					   "  		group by NPP_FK  \n" +  
					   "  			 union all \n" +  
					   "  		select a.NPP_FK, (-1)* isnull(sum(cast(b.soluong as float) * c.TRONGLUONG ), 0) as tongluongDaNhap, '1' as type    \n" +  
					   "  		from nhaphang a inner join nhaphang_sp b on a.pk_seq = b.nhaphang_fk    inner join SANPHAM c on b.SANPHAM_FK = c.MA  \n" +  
					   "  		where a.ngaynhan <= '" + obj.getTungay().substring(0,8)+d + "' and a.ngaynhan >= '2013-10-01' 	\n" +
					    "	AND a.ngaychungtu <= '" + obj.getTungay().substring(0,8)+d + "' and a.ngaychungtu >= '2013-10-01' 		and a.trangthai = '1' \n" +
					    " and exists (SELECT PK_SEQ FROM HDNHAPHANG HDNH WHERE HDNH.CHUNGTU=A.HDTAICHINH AND HDNH.NPP_FK=A.NPP_FK and a.ngaychungtu >= '2013-10-01' AND ISNULL(HDNH.LOAIDONHANG,'0')='0'  ) \n" +  
					   "  		group by a.NPP_FK  \n" +  
					   " 			union all  		\n" +
					   "			select b.NPP_FK,  isnull( sum(soluong * c.TRONGLUONG) , 0) as nppUnghang, '1' as type \n " +  
					   "  			from donhang_ctkm_trakm a inner join DONHANG b on a.DONHANGID = b.PK_SEQ  \n" +  
					   "  			inner join SANPHAM c on a.SPMA = c.MA  \n" +  
					   "  			where spMa is not null and b.ngaynhap <= '" + obj.getTungay().substring(0,8)+d + "' and b.ngaynhap >= '2013-09-01' and b.trangthai = '1'  \n" +  
					   "  			AND b.PK_SEQ NOT IN (	SELECT DONHANG_FK FROM DONHANGTRAVE WHERE DONHANG_FK IS NOT NULL AND TRANGTHAI = '3' )  \n" +  
					   "  			group by b.NPP_FK  \n" + 
					   "  			union all  \n" +   
					   "    select a.NPP_FK, (-1)* isnull(sum(cast(b.soluong as float) * c.TRONGLUONG ), 0) as tongluongDaNhap, '1' as type "+     
					   "    from nhaphang a inner join nhaphang_sp b on a.pk_seq = b.nhaphang_fk    inner join SANPHAM c on b.SANPHAM_FK = c.MA "+  
					   "    where a.ngaynhan <='" + obj.getTungay().substring(0,8)+d + "' and a.ngaynhan >= '2013-10-01' 	 "+
					   "    AND a.ngaychungtu <= '" + obj.getTungay().substring(0,8)+d + "' and a.ngaychungtu >= '2013-10-01' 		and a.trangthai = '1' and a.LOAIHOADON=1   "+
					   "    and exists (SELECT PK_SEQ FROM HDNHAPHANG HDNH WHERE HDNH.CHUNGTU=A.HDTAICHINH AND HDNH.NPP_FK=A.NPP_FK and a.ngaychungtu >= '2013-10-01' AND ISNULL(HDNH.LOAIDONHANG,'0')='1'  ) "+ 
					   "    group by a.NPP_FK "+
					   "  	) tonngay   " +  
					   "  	group by tonngay.NPP_FK "+
					") \n" +
					"tonthucte on tonquydinh.loaiTon = tonthucte.loaiTon and tonquydinh.nppId = tonthucte.NPP_FK \n" +
					" left join nhaphanphoi npp on tonquydinh.nppId = npp.pk_seq \n" +
					" left join khuvuc kv on npp.khuvuc_fk = kv.pk_seq \n" +
					" left join vung v on kv.vung_fk = v.pk_seq where 1=1  and npp.pk_seq in (select npp_fk from khoasongay where ngayks ='"+obj.getTungay().substring(0,8)+d+"')";
		    	
		    
		     
		    if(obj.getVungId().length() > 0)
		    	query += " and v.pk_seq = '" + obj.getVungId() + "' ";
		    
		    if(obj.getKvId().length() > 0)
		    	query += " and kv.pk_seq = '" + obj.getKvId() + "' ";
		    System.out.println("1.Ton kho theo quy dinh: " + query);
		
			ResultSet rs = db.get(query);
		
		// vong lap o day 
	
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
								String nppTen = rs.getString("nppTen");
								int songaylv = rs.getInt("songaylamviec");	
								double chitieu = rs.getDouble("chitieu");
								double tonquydinh = rs.getDouble("tonquydinh");
								double tonkhoNpp = rs.getDouble("tonkhoNpp");
								String ngay = rs.getString("ngay");
			            		
								cell = cells.getCell("DA" + Integer.toString(i)); 	cell.setValue(vung);
								cell = cells.getCell("DB" + Integer.toString(i)); 	cell.setValue(khuvuc);
								cell = cells.getCell("DC" + Integer.toString(i)); 	cell.setValue(nppTen);
								cell = cells.getCell("DD" + Integer.toString(i)); 	cell.setValue(songaylv);
								cell = cells.getCell("DE" + Integer.toString(i)); 	cell.setValue(chitieu);
								cell = cells.getCell("DF" + Integer.toString(i)); 	cell.setValue(tonquydinh);				
								cell = cells.getCell("DG" + Integer.toString(i)); 	cell.setValue(tonkhoNpp);
								cell = cells.getCell("DH" + Integer.toString(i)); 	cell.setValue(ngay);
			
								i++;
							}
							if(rs!=null)
								rs.close();
						
						} 
						catch (Exception e) 
						{
							System.out.println("115.Error: " + e.getMessage());
							return false;
						}
					} 
		}
	    
	    } //vong lap 
		else 
		{
			if(db != null) 
				db.shutDown();
			return false;
		}
		if(i==2){
			throw new Exception("Khong co bao cao trong thoi gian nay...");
		}
		if(db != null) 
			db.shutDown();
		return true;
	}
	
	/***************** End Ton Kho Theo Quy Dinh ***********************/
	
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
}
