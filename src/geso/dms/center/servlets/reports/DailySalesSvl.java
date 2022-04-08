package geso.dms.center.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
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

import java.util.Hashtable;

public class DailySalesSvl extends HttpServlet
{
	

	private static final long serialVersionUID = 1L;
	public DailySalesSvl()
	{
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		Utility util = new Utility();
		
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		obj.setuserId(userId);
		obj.init();
		
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Center/DailySalesReport.jsp";
		
		String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		if (view == null)
			view = "";
		
		if (!view.equals("TT"))
		{
			nextJSP = "/AHF/pages/Distributor/DailySalesReport.jsp";
			response.sendRedirect(nextJSP);
		} else
		{
			nextJSP = "/AHF/pages/Center/DailySalesReport.jsp";
			response.sendRedirect(nextJSP);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		
		if (userId == null)
			userId = "";
		
		obj.setuserId(userId);
		
		Utility util = new Utility();
		
		String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		if (view == null)
			view = "";
		
		String nppId = "";
		if (view.equals("TT"))
		{
			nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
			if (nppId == null)
				nppId = "";
			obj.setnppId(nppId);
		} else
		{
			
			nppId = util.getIdNhapp(userId);
			
			obj.setnppId(nppId);
		}
		
		obj.setuserTen(userTen);
		String kenhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")));
		if (kenhId == null)
			kenhId = "";
		obj.setkenhId(kenhId);
		
		String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
		
		if (dvkdId == null)
			dvkdId = "";
		obj.setdvkdId(dvkdId);
		
		obj.setMonth(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang") == null ? "" : request.getParameter("thang"))));
		obj.setYear(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nam") == null ? "" : request.getParameter("nam"))));
		//obj.setQuy(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("quy") == null ? "" : request.getParameter("quy"))));
		obj.settype(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("apdung") == null ? "" : request.getParameter("apdung"))));
		
		System.out.println("__Thang__"+geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang"))+"___"+obj.getMonth());
		String vungId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")));
		if (vungId == null)
			vungId = "";
		
		obj.setvungId(vungId);
		
		String khuvucId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")));
		if (khuvucId == null)
			khuvucId = "";
		obj.setkhuvucId(khuvucId);
		
		String dvdlId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvdlId")));
		if (dvdlId == null)
			dvdlId = "";
		obj.setdvdlId(dvdlId);
		
		String sql = "";
		
		String action = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));
		
		if (action.equals("Taomoi"))
		{
			try
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=ThucDatSoVoiChiTieuNPP_.xlsm");
				OutputStream out = response.getOutputStream();
				String query = setQuery(sql, obj, userId,request);
				CreatePivotTable(out, obj, query);
			} catch (Exception ex)
			{
				obj.setMsg(ex.getMessage());
				ex.printStackTrace();
				obj.init();
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
				if (!view.equals("TT"))
				{
					String nextJSP = "/AHF/pages/Distributor/DailySalesReport.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					String nextJSP = "/AHF/pages/Center/DailySalesReport.jsp";
					response.sendRedirect(nextJSP);
				}
			}
		} else
		{
			obj.init();
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			if (!view.equals("TT"))
			{
				String nextJSP = "/AHF/pages/Distributor/DailySalesReport.jsp";
				response.sendRedirect(nextJSP);
			} else
			{
				String nextJSP = "/AHF/pages/Center/DailySalesReport.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}
	
	private void CreateStaticHeader(Workbook workbook,IStockintransit obj) throws Exception 
	{
	
		try
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
		    
		    ReportAPI.getCellStyle(cell,Color.RED, true, 14, "BÁO CÁO THỰC ĐẠT SO VỚI CHỈ TIÊU NHÀ PHÂN PHỐI");
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("A3");
		    
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ ngày : " + obj.gettungay() + "" );
		    
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("C3"); 
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến ngày : " + obj.getdenngay() + "" );
			
		    cells.setRowHeight(4, 18.0f);
		    cell = cells.getCell("A4");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + obj.getDateTime());
		    
		    cells.setRowHeight(5, 18.0f);
		    cell = cells.getCell("A5");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());
					
		    
		    Color color= Color.YELLOW ;
		   //33 dong
		    int cotdautien = 50;
		    int dongbatdauHeader = 0;
		    
		    cell = cells.getCell(dongbatdauHeader,cotdautien++); cell.setValue("Mien");
		    ReportAPI.setCellBackground(cell, color, BorderLineType.THIN, true, 0);
		    
		    cell = cells.getCell(dongbatdauHeader,cotdautien++); cell.setValue("KhuVuc");
		    ReportAPI.setCellBackground(cell, color, BorderLineType.THIN, true, 0);
		    
		    cell = cells.getCell(dongbatdauHeader,cotdautien++); cell.setValue("Tinh/Thanh");
		    ReportAPI.setCellBackground(cell, color, BorderLineType.THIN, true, 0);
		    
		    cell = cells.getCell(dongbatdauHeader,cotdautien++); cell.setValue("Quan/Huyen");
		    ReportAPI.setCellBackground(cell, color, BorderLineType.THIN, true, 0);
		    
		    cell = cells.getCell(dongbatdauHeader,cotdautien++); cell.setValue("MaNhaPhanPhoi");
		    ReportAPI.setCellBackground(cell, color, BorderLineType.THIN, true, 0);
		    
		    cell = cells.getCell(dongbatdauHeader,cotdautien++); cell.setValue("NhaPhanPhoi");
		    ReportAPI.setCellBackground(cell, color, BorderLineType.THIN, true, 0);
		    
		    color= color== Color.SILVER ?  Color.YELLOW: Color.SILVER;
		    
		    cell = cells.getCell(dongbatdauHeader,cotdautien++); cell.setValue("ChiTieuDoanhSoBanRa");
		    ReportAPI.setCellBackground(cell, color, BorderLineType.THIN, true, 0);
		    
		    cell = cells.getCell(dongbatdauHeader,cotdautien++); cell.setValue("ThucDatDoanhSoBanRa");
		    ReportAPI.setCellBackground(cell, color, BorderLineType.THIN, true, 0);
		    
		    cell = cells.getCell(dongbatdauHeader,cotdautien++); cell.setValue("TyLeDoanhSoBanRa");
		    ReportAPI.setCellBackground(cell, color, BorderLineType.THIN, true, 0);
		    
		    color= color== Color.SILVER ?  Color.YELLOW: Color.SILVER;
		    
		    cell = cells.getCell(dongbatdauHeader,cotdautien++); cell.setValue("ChiTieuSoLuongBanRa");
		    ReportAPI.setCellBackground(cell, color, BorderLineType.THIN, true, 0);
		    
		    cell = cells.getCell(dongbatdauHeader,cotdautien++); cell.setValue("ThucDatSoluongBanRa");
		    ReportAPI.setCellBackground(cell, color, BorderLineType.THIN, true, 0);
		    
		    cell = cells.getCell(dongbatdauHeader,cotdautien++); cell.setValue("TyLeSoluongBanRa");
		    ReportAPI.setCellBackground(cell, color, BorderLineType.THIN, true, 0);

		    color= color== Color.SILVER ?  Color.YELLOW: Color.SILVER;
		    
		    cell = cells.getCell(dongbatdauHeader,cotdautien++); cell.setValue("ChiTieuDoanhSoMuaVao");
		    ReportAPI.setCellBackground(cell, color, BorderLineType.THIN, true, 0);
		    
		    cell = cells.getCell(dongbatdauHeader,cotdautien++); cell.setValue("ThucDatDoanhSoMuaVao");
		    ReportAPI.setCellBackground(cell, color, BorderLineType.THIN, true, 0);
		    
		    cell = cells.getCell(dongbatdauHeader,cotdautien++); cell.setValue("TyleDoanhSoMuaVao");
		    ReportAPI.setCellBackground(cell, color, BorderLineType.THIN, true, 0);
		    
		    color= color== Color.SILVER ?  Color.YELLOW: Color.SILVER;
		    
		    cell = cells.getCell(dongbatdauHeader,cotdautien++); cell.setValue("ChiTieuSoLuongMuaVao");
		    ReportAPI.setCellBackground(cell, color, BorderLineType.THIN, true, 0);
		    
		    cell = cells.getCell(dongbatdauHeader,cotdautien++); cell.setValue("ThucDatSoluongMuaVao");
		    ReportAPI.setCellBackground(cell, color, BorderLineType.THIN, true, 0);
		    
		    cell = cells.getCell(dongbatdauHeader,cotdautien++); cell.setValue("TyleSoluongMuaVao");
		    ReportAPI.setCellBackground(cell, color, BorderLineType.THIN, true, 0);
		    
		    color= color== Color.SILVER ?  Color.YELLOW: Color.SILVER;
		    
		    cell = cells.getCell(dongbatdauHeader,cotdautien++); cell.setValue("ChiTieuSoDonHang");
		    ReportAPI.setCellBackground(cell, color, BorderLineType.THIN, true, 0);
		    
		    cell = cells.getCell(dongbatdauHeader,cotdautien++); cell.setValue("ThucDatSoDonHang");
		    ReportAPI.setCellBackground(cell, color, BorderLineType.THIN, true, 0);
		    
		    cell = cells.getCell(dongbatdauHeader,cotdautien++); cell.setValue("TyleSoDonHang");
		    ReportAPI.setCellBackground(cell, color, BorderLineType.THIN, true, 0);
		    
		    color= color== Color.SILVER ?  Color.YELLOW: Color.SILVER;
		    
		    cell = cells.getCell(dongbatdauHeader,cotdautien++); cell.setValue("ChiTieuTyleGiaoHang");
		    ReportAPI.setCellBackground(cell, color, BorderLineType.THIN, true, 0);
		    
		    cell = cells.getCell(dongbatdauHeader,cotdautien++); cell.setValue("ThucDatTyleGiaoHang");
		    ReportAPI.setCellBackground(cell, color, BorderLineType.THIN, true, 0);
		    
		    cell = cells.getCell(dongbatdauHeader,cotdautien++); cell.setValue("TyleTyleGiaoHang");
		    ReportAPI.setCellBackground(cell, color, BorderLineType.THIN, true, 0);
		    
		    color= color== Color.SILVER ?  Color.YELLOW: Color.SILVER;
		    
		    cell = cells.getCell(dongbatdauHeader,cotdautien++); cell.setValue("ChiTieuThoiGianGiaoHang");
		    ReportAPI.setCellBackground(cell, color, BorderLineType.THIN, true, 0);
		    
		    cell = cells.getCell(dongbatdauHeader,cotdautien++); cell.setValue("ThucDatThoiGianGiaoHang");
		    ReportAPI.setCellBackground(cell, color, BorderLineType.THIN, true, 0);
		    
		    cell = cells.getCell(dongbatdauHeader,cotdautien++); cell.setValue("TyleThoiGianGiaoHang");
		    ReportAPI.setCellBackground(cell, color, BorderLineType.THIN, true, 0);
		    
		    cell = cells.getCell(dongbatdauHeader,cotdautien++); cell.setValue("Nhom");
		    ReportAPI.setCellBackground(cell, color, BorderLineType.THIN, true, 0);
		    
		    cell = cells.getCell(dongbatdauHeader,cotdautien++); cell.setValue("ChiTieuNSP");
		    ReportAPI.setCellBackground(cell, color, BorderLineType.THIN, true, 0);
		    
		    cell = cells.getCell(dongbatdauHeader,cotdautien++); cell.setValue("ThucDatNSP");
		    ReportAPI.setCellBackground(cell, color, BorderLineType.THIN, true, 0);
		    
		    cell = cells.getCell(dongbatdauHeader,cotdautien++); cell.setValue("VsNSP");
		    ReportAPI.setCellBackground(cell, color, BorderLineType.THIN, true, 0);
		    
		    cell = cells.getCell(dongbatdauHeader,cotdautien++); cell.setValue("phanloai");
		    ReportAPI.setCellBackground(cell, color, BorderLineType.THIN, true, 0);
		    cell = cells.getCell(dongbatdauHeader,cotdautien++); cell.setValue("tennhom");
		    ReportAPI.setCellBackground(cell, color, BorderLineType.THIN, true, 0);
		    
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("Khong the tao duoc Header cho bao cao...");
		}
	}
	
	
	private void CreatePivotTable(OutputStream out, IStockintransit obj, String query) throws Exception
	{
		try
		{
			String chuoi = getServletContext().getInitParameter("path") + "\\ThucDatSoVoiChiTieuNPP.xlsm";
			FileInputStream fstream = new FileInputStream(chuoi);
			//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\ThucDatSoVoiChiTieuNPP.xlsm");
			//FileInputStream fstream = new FileInputStream(f) ;
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			CreateStaticHeader(workbook,obj); 		
	 		FillData(workbook,obj.getFieldShow(),query,obj); 	
			workbook.save(out);
			fstream.close();
		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
	}
	
	private String setQuery(String sql, IStockintransit obj, String userId,HttpServletRequest request)
	{
		
		String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		if (view == null)
			view = "";
		
		String condition ="";
		geso.dms.distributor.util.Utility util1=new geso.dms.distributor.util.Utility();
		String loainv = "";
		util1.getUserInfo(userId,  new geso.dms.distributor.db.sql.dbutils());
		loainv = util1.getLoaiNv();
		if(loainv.equals("3"))
		{
			obj.setnppId("select npp_fk from phamvihoatdong where nhanvien_fk = '"+userId+"'");
			System.out.println("xxx");
		}
		if(obj.getnppId().length()>0)
		{
			/*if (!view.equals("TT"))
				condition+=" and npp.TRUCTHUOC_FK='"+obj.getnppId()+"'";
			else */
				condition+=" and npp.pk_Seq in ( "+obj.getnppId()+" )";
			
		}
		else

			if(obj.getkhuvucId().length()>0)
			{
				condition+=" and kv.pk_seq='"+obj.getvungId()+"'";
			}else
		if(obj.getvungId().length()>0)
		{
			condition+=" and v.pk_seq='"+obj.getvungId()+"'";
		}
		String query=
			 "\n declare @dauthang varchar(10) ='"+( obj.getYear() +"-" + (obj.getMonth().trim().length()<=1?"0"+obj.getMonth():obj.getMonth())   +"-01"  )+"' -- " + 
			 "\n declare @cuoithang varchar(10) = CONVERT(char(10),DATEADD(DAY,-(DAY(@dauthang)),DATEADD(MONTH,1,@dauthang)),120)  " + 
			 "\n select v.TEN as vTEN,kv.TEN as kvTEN,tt.TEN as ttTEN,qh.TEN as qhTEN,npp.MA as nppMA,npp.TEN as nppTEN  --   " + 
			 "\n  	,DoanhSoBanRa, isnull(tdDsBanra,0) AS tdDoanhSoBanRa,case when DoanhSoBanRa = 0 then 0 else  isnull(tdDsBanra,0)/DoanhSoBanRa end VsDoanhSoBanRa  " + 
			 "\n  	,SoluongBanRa, isnull(tdSlBanra,0) AS tdSoLuongBanRa,case when SoluongBanRa = 0 then 0 else  isnull(tdSlBanra,0)/SoluongBanRa end VsSoluongBanRa  " + 
			 "\n  	,DoanhSoMuaVao, isnull(tdDsMuaVao,0) AS tdDoanhSoMuaVao,case when DoanhSoMuaVao = 0 then 0 else  isnull(tdDsMuaVao,0)/DoanhSoMuaVao end VsDoanhSoMuaVao  " + 
			 "\n  	,SoluongMuaVao, isnull(tdSlMuaVao,0) AS tdSoLuongMuaVao,case when SoluongMuaVao = 0 then 0 else  isnull(tdSlMuaVao,0)/SoluongMuaVao end VsSoluongMuaVao  " + 
			 "\n  	,SoDonHang as ctSoDonHang, isnull(tdDH,0) as tdSoDonHang,case when SoDonHang = 0 then 0 else  isnull(tdDH,0)/SoDonHang end VsSoDonHang  " + 
			 "\n  	,TyLeGiaoHang , 0 tdTyLeGiaoHang,0 VsTyLeGiaoHang  " + 
			 "\n  	, ThoiGianGiaoHang,0 tdThoiGianGiaoHang,0 VsThoiGianGiaoHang  " + 
			 "\n 	 , isnull(ISNULL(nspct.spma,ctnsp.nsp_fk),'0') as loai" +
			 "\n	,isnull(chitieu_nhom,0)chitieu_nhom,isnull(thucdat_nhom,0)thucdat_nhom, case when isnull(chitieu_nhom,0) =0 then 0 else isnull(thucdat_nhom,0)/chitieu_nhom end VsNhom" +
			 "\n  	,(select ten from loaichitieunhom where loai = ctnsp.loai) phanloai" +
			 "\n 	,isnull(ISNULL(nspct.spten,nspct.ten),'0') as tennhom " + 
			 "\n   from CHITIEUNHAPHANPHOI ctnpp --   " + 
			 "\n  inner join CHITIEUNHAPHANPHOI_NPP ctnppct on ctnpp.PK_SEQ = ctnppct.CTNPP_FK --   " + 
			 "\n  inner join NHAPHANPHOI npp on npp.PK_SEQ=ctnppct.npp_fk --   " + 
			 "\n  inner join KHUVUC kv on kv.PK_SEQ=npp.KHUVUC_FK --   " + 
			 "\n  inner join VUNG v on v.PK_SEQ=kv.VUNG_FK    --   " + 
			 "\n  inner join TINHTHANH tt on tt.PK_SEQ=npp.TINHTHANH_FK  --   " + 
			 "\n  inner join QUANHUYEN qh on qh.PK_SEQ=npp.QUANHUYEN_Fk  --  " + 
			 "\n  left join  " + 
			 "\n  (  " + 
			 "\n 	select npp_fk,sum(soluong) tdSlBanra,sum(soluong*giamua)tdDsBanra, count(distinct dh.pk_seq) tdDH   " + 
			 "\n 	from donhang dh  " + 
			 "\n 	inner join   " + 
			 "\n 	 (	  " + 
			 "\n 		select DONHANG_FK,giamua,SANPHAM_fk,SUM(soluong)soluong from  " + 
			 "\n 		(  " + 
			 "\n 			select DONHANG_FK,SANPHAM_FK,soluong,giamua from DONHANG_SANPHAM   " + 
//			 "\n 			union all  " + 
//			 "\n 			select donhangid,(select pk_seq from sanpham where ma =spma),soluong,isnull(dongia,0) from DONHANG_CTKM_TRAKM where SPMA is not null  " + 
			 "\n 		)dhsp  " + 
			 "\n 		group by DONHANG_FK,giamua,SANPHAM_fk  " + 
			 "\n 	 )dh_sp on dh.PK_SEQ =dh_sp.DONHANG_FK  " + 
			 "\n 	where dh.trangthai = 1 and dh.ngaynhap>=@dauthang and dh.ngaynhap<=@cuoithang  " + 
			 "\n 	group by npp_fk  " + 
			 "\n  )tdbr on tdbr.npp_fk = npp.pk_seq  " + 
			 "\n  left join  " + 
			 "\n  (  " + 
			 "\n 	select npp_fk,sum(soluong) tdSlMuaVao,sum(soluong*dongia)tdDsMuaVao  " + 
			 "\n 	from nHAPhANG dh  " + 
			 "\n 	inner join NhapHang_SP dh_sp on dh.PK_SEQ =dh_sp.nhaphang_fk  " + 
			 "\n 	where dh.trangthai = 1 and dh.NgayChungTu>=@dauthang and dh.NgayChungTu<=@cuoithang  " + 
			 "\n 	group by npp_fk  " + 
			 "\n  )tdmv on tdmv.npp_fk = npp.pk_seq  " + 
			 "\n  left join [dbo].[ufn_ThucDatNSP](0,100002 ,@dauthang ,@cuoithang) ctnsp on ctnsp.ma =npp.pk_seq  " +
			 
			 "\n left join NHOMSANPHAMCHITIEU nspct on ctnsp.nsp_fk = nspct.PK_SEQ " + 
			 "\n  where ctnpp.trangthai =1 and ctnpp.thang= "+obj.getMonth()+" and ctnpp.nam="+obj.getYear()+" \n";
			 query += condition;
			
		System.out.println("_BAO CAO_THUC_DAT_CHI_TIEU__"+query);
		
		return query;
	}
	
	private void FillData(Workbook workbook,String[] fieldShow, String query,IStockintransit obj)throws Exception 
	{
		try
		{
			Worksheets worksheets = workbook.getWorksheets();
		    Worksheet worksheet = worksheets.getSheet(0);
		    Cells cells = worksheet.getCells();
		    dbutils db = new dbutils();
		    
		    String sql = "";
		    
		    Cell cell = null;	    

		    ResultSet rs = db.get(query);
		    ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();
		    System.out.println("Query BC: "+query);
		    int index = 1;
		    int cotdautien = 49;
		    
		    NumberFormat numberFormat = new DecimalFormat("#,###,##0.##");
		    NumberFormat percentageFormat = NumberFormat.getPercentInstance();
		    percentageFormat.setMinimumFractionDigits(3);
		    
		    while (rs.next()) 
		    {		
		    	for(int i = 1 ; i <= socottrongSql ; i++ )
		    	{
		    		if(i<=socottrongSql -5)
		    		{
		    			if(i <= 6 || i==socottrongSql -5)
		    			{
			    			cell = cells.getCell(index,cotdautien+i);cell.setValue(rs.getString(i));
					    	ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		    			}
		    			else
		    			{
		    				String v = "";
		    				double kq =rs.getDouble(i);
		    				if(i == 6 + 3|| i == 6 + 3*2|| i == 6 + 3*3|| i == 6 + 3*4|| i == 6 + 3*5|| i == 6 + 3*6|| i == 6 + 3*7)
		    				{
		    					v = percentageFormat.format(kq);
		    				}
		    				else
		    					v = numberFormat.format(kq);
		    				cell = cells.getCell(index,cotdautien+i);cell.setValue(v);
					    	ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		    			}
		    		}
		    		else
		    		{
		    			cell = cells.getCell(index,cotdautien+i);
		    			if(rsmd.getColumnType(i)== Types.DOUBLE)
		    				cell.setValue(rs.getDouble(i));
		    			else
		    				cell.setValue(rs.getString(i));
		    				
				    	ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		    		}
		    	}
			    
		    	
				index++;
			}
		    if(rs!=null)
		    {
		    	rs.close();
		    }
		    
		    if(db != null) db.shutDown();
		    
		    if(index==1)
		    {
		    	throw new Exception("Xin loi. Khong co bao cao voi dieu kien da chon...!!!");
		    }
		    //ReportAPI.setHidden(workbook, 50);
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}	
	}
}
