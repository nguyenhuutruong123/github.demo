package geso.dms.center.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.distributor.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

/*
 Thiet lap 3 function cho thuc hien viec lay doanh so 
 theo cac muc khach nhau.... 
 */

public class BCThongKeSuDungDMS extends HttpServlet {
		
	private static final long serialVersionUID = 1L;

	public BCThongKeSuDungDMS() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		String querystring = request.getQueryString();
		Utility util = new Utility();
		
		obj.setuserId(util.getUserId(querystring));
		obj.setuserTen((String) session.getAttribute("userTen"));	
		
		obj.init();
		
		String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		if(view == null)
			view = "";		
		obj.setLoaiMenu(view);
		
		Utility ut = new Utility();
		ut.getUserInfo((String) session.getAttribute("userId"), obj.getDb());
		
		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Center/BCThongKeSuDungDMS.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		OutputStream out = response.getOutputStream();		

		String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		System.out.println("loaiMenu = " + view);
		if(view == null)
			view = "";

		obj.setLoaiMenu(view);

		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");

		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");
		Utility util = new Utility();

		obj.settungay(Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays"))));
		obj.setdenngay(Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays"))));

//		String tuthang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tuthang").length()< 2 ? ("0"+request.getParameter("tuthang")) :request.getParameter("tuthang")) ;
		/*String toithang=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denthang").length()< 2 ? ("0"+request.getParameter("denthang")) :request.getParameter("denthang")) ;*/
//		obj.setFromMonth(tuthang);
		/*obj.setToMonth(toithang);*/
		/*obj.setToYear(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dennam")));*/
		obj.setFromYear(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tunam")));

		String[] vungid = request.getParameterValues("vungId");
		String s = "";
		if (vungid != null)
		{
			for( int i = 0; i < vungid.length; i++)
			{
				s += vungid[i] +",";
			}
			if(s.length() > 0)
			{
				s = s.substring(0,s.length() -1);
			}		
		}
		System.out.println("VUng: "+s);
		obj.setvungId(s);
		String[] khuvucId = request.getParameterValues("khuvucId");
		s = "";
		if(khuvucId != null)
		{

			for(int i = 0; i < khuvucId.length; i++)
			{
				s += khuvucId[i] +",";
			}
			if(s.length() > 0)
			{
				s = s.substring(0,s.length() -1);
			}	
		}
		obj.setkhuvucId(s);
		String[] nppId = request.getParameterValues("nppId");
		s = "";
		if (nppId != null)
		{
			for(int i = 0; i < nppId.length; i++)
			{
				s += nppId[i] +",";
			}
			if(s.length() > 0)
			{
				s = s.substring(0,s.length() -1);
			}
		}		

		String trangthai = Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));		    
		if(trangthai == null)
			trangthai = "";	    	   	    
		Utility ut = new Utility();
		ut.getUserInfo((String) session.getAttribute("userId"), obj.getDb());

		if(s.length() <= 0)
		{
			s = " select pk_seq from nhaphanphoi where 1 = 1 ";		

			if(obj.getkhuvucId().length() > 0)
			{
				s+= " and khuvuc_fk in ("+obj.getkhuvucId()+") ";
			}

			if (view != null && view.equals("NPP"))
			{				
				if(ut.getLoaiNv().equals("3"))
					s += " and pk_seq in "+util.quyen_npp(userId)+" ";
				else
				{
					String nppid = ut.getIdNhapp(userId);
					s += " and pk_seq in ("+nppid+") ";
				}			
			}
			else
			{
				s += " and pk_seq in "+util.quyen_npp(userId)+" ";
			}


			if(obj.getvungId().length() > 0)
			{
				s+= " and khuvuc_fk in (select pk_seq from khuvuc where vung_fk in ("+obj.getvungId()+") )";
			}
		}

		obj.setnppId(s);
		String action = (String) Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));
		String nextJSP = "/AHF/pages/Center/BCThongKeSuDungDMS.jsp";

		System.out.println("Action la: " + action);

		try{
			if (action.equals("Taomoi")) 
			{			
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BCThongKeDMS.xlsm");
				Workbook workbook = new Workbook();
				TaoBaoCaoNew(workbook, userTen,obj.gettungay(),obj.getdenngay(),obj.getnppId(),trangthai, userId);

				workbook.save(out);	

			}
			else
			{
				obj.init();
				session.setAttribute("obj", obj);
				response.sendRedirect(nextJSP);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			obj.init();
			obj.setMsg("Lỗi không tạo được báo cáo ! Vui Lòng kiểm tra lại");
			response.sendRedirect(nextJSP);
		}

		session.setAttribute("obj", obj);	
	}		
	
	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
/*	private void TaoBaoCao(Workbook workbook,String nguoitao,String tungay,String denngay,String nppid, String trangthai  )throws Exception
	{
		try{
			FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BCThongKeDMS.xlsm");
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("ThongKeDMS");
		
			dbutils db = new dbutils();
			Cells cells = worksheet.getCells();
			cells.setRowHeight(0, 50.0f);
			Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 16,"Báo Cáo Thống Kê Sử Dụng DMS");

			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Ngày tạo : "+ getDateTime());
			
			cell = cells.getCell("A2");
			cells.setColumnWidth(0, 20.0f);
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Tháng: "+ tungay);
			cell = cells.getCell("B2");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Năm: "+ denngay);
			cells.setColumnWidth(4, 20.0f);
			cells.setColumnWidth(9, 15.0f);
			cells.setColumnWidth(8, 15.0f);
			cells.setColumnWidth(7, 15.0f);
			cells.setColumnWidth(6, 15.0f);
			cell = cells.getCell("A4");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10,"Người tạo : " + nguoitao);
		
			String tt="";
			System.out.println("Trang thai "+trangthai);
			if(trangthai.trim().length()>0)
			{
				if(trangthai.equals("0"))
					tt=" dh.TRANGTHAI <>  1 and dh.TRANGTHAI <>  2" ;
				else
					tt=" dh.TRANGTHAI =  1" ;
				
			}
			else
				tt=" dh.TRANGTHAI <>  2 ";
			
			String sql = " WITH ddkd_khachhang_w AS( "
					 + "\n select pk_seq, ddkd_fk, khachhang_fk, lat, long, thoidiem, thoidiemdi, thoigiantaicuahang, dolech, ghichu, isdungtuyen, tbh_fk, ngayid, sott, tanso "
					 + "\n	from ddkd_khachhang a  "
					 + "\n	where  MONTH(thoidiem) = '"+tungay+"' and YEAR(thoidiem) = '"+denngay+"' " +
			 		")"; 
			//db.update(sql);
			
			// Dùng bảng tạm sinh ra trong 1 phiên kết nối để giảm bớt join lấy báo cáo.
			sql +=" , ThongKeTuan1_w as( "
			 +"\n	select tbh.pk_seq as Tbh_fk, SoKh1.sokh as ASO,isnulL(VT1.sokh,0) as SOKHVT,isnull(TGKH1.TGKh,'') as TG,ISNULL(TGLV1.TGLV,'') as TGLV,isnull(DS1.DoanhSo,0) as DoanhSo,isnull(SODH1.DH,0) as SODH,ISNULL(SoDHTT1.DHtraituyen, 0) as SoDHTT, ISNULL(SoVTTT1.VTtraituyen, 0) as SoVTTT,isnull(SKU1.DH,0) as SKU,isnull(AC1.DH,0) as Anh  from  TUYENBANHANG tbh  "
			 +"\n 	inner join "
			 +"\n  	("
			 +"\n 		select COUNT(distinct khachhang_fk) as sokh,TBH_FK "
			 +"\n 		from KHACHHANG_TUYENBH where exists (select 1 from khachhang kh where trangthai = 1 and kh.pk_seq = KHACHHANG_TUYENBH.khachhang_fk )"
			 +"\n 		group by TBH_FK"
			 +"\n  	) SoKh1 on SoKh1.TBH_FK = tbh.PK_SEQ"
			 +"\n  	left join"
			 +"\n  	("
			 +"\n 		select COUNT(a.khachhang_fk) as sokh,b.TBH_FK "
			 +"\n 		from ddkd_khachhang_w a inner join KHACHHANG_TUYENBH b"
			 +"\n 		on a.khachhang_fk = b.KHACHHANG_FK"
			 +"\n 		where "
			 +"\n 	 MONTH(thoidiem) = '"+tungay+"' and YEAR(thoidiem) = '"+denngay+"' and"
			 +"\n 	 DATEPART(WEEK, thoidiem)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,thoidiem), 0))+ 1 = 1 "
			 + "\n  	and exists (select 1 from khachhang kh where trangthai = 1 and kh.pk_seq = b.khachhang_fk ) "
			 +"\n 		group by b.TBH_FK"
			 +"\n  	) VT1 on VT1.TBH_FK = tbh.PK_SEQ"
			 +"\n  	left join"
			 +"\n  	("
			 +"\n 		select convert(char(5), (select dateadd(minute,Sum(isnull(a.thoigiantaicuahang,0)),0)), 108) as TGKh,b.TBH_FK "
			 +"\n 		from ddkd_khachhang_w a inner join KHACHHANG_TUYENBH b"
			 +"\n 		on a.khachhang_fk = b.KHACHHANG_FK"
			 +"\n 		where MONTH(thoidiem) = '"+tungay+"' and YEAR(thoidiem) = '"+denngay+"'"
			 +"\n 	 	AND DATEPART(WEEK, thoidiem)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,thoidiem), 0))+ 1 = 1 "
			 + "\n 		and exists (select 1 from khachhang kh where trangthai = 1 and kh.pk_seq = b.khachhang_fk ) "
			 +"\n 		group by b.TBH_FK"
			 +"\n  	) TGKH1 on TGKH1.TBH_FK = tbh.PK_SEQ"
			 +"\n  	left join"
			 +"\n  	("
			 +"\n 		select  CONVERT(char(8), DATEADD(SECOND, DATEDIFF(SECOND,Min(a.thoidiem),Max(a.thoidiemdi)),0), 108) as TGLV,b.TBH_FK "
			 +"\n 		from ddkd_khachhang_w a inner join KHACHHANG_TUYENBH b"
			 +"\n 		on a.khachhang_fk = b.KHACHHANG_FK"
			 +"\n 		where "
			 +"\n 	 	MONTH(thoidiem) = '"+tungay+"' and YEAR(thoidiem) = '"+denngay+"'"
			 +"\n 	 	AND DATEPART(WEEK, thoidiem)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,thoidiem), 0))+ 1 = 1 "
			 + "\n 		and exists (select 1 from khachhang kh where trangthai = 1 and kh.pk_seq = b.khachhang_fk ) "
			 +"\n 		group by b.TBH_FK"
			 +"\n  	) TGLV1 on TGLV1.TBH_FK = tbh.PK_SEQ"
			 +"\n  	left join"
			 +"\n  	("
			 +"\n 		select SUM(dhsp.SOLUONG*dhsp.GIAMUA) as DoanhSo,a.TBH_FK "
			 +"\n 		from KHACHHANG_TUYENBH a"
			 +"\n 		inner join DONHANG dh on dh.KHACHHANG_FK = a.KHACHHANG_FK"
			 +"\n 		inner join DONHANG_SANPHAM dhsp on dhsp.DONHANG_FK = dh.PK_SEQ"
			 +"\n 		where "+tt
			 +"\n 		and "
			 +"\n 	 	MONTH(NGAYNHAP) = '"+tungay+"' and YEAR(NGAYNHAP) = '"+denngay+"'"
			 +"\n 	 	AND DATEPART(WEEK, NGAYNHAP)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,NGAYNHAP), 0))+ 1 = 1"
			 +"\n 	"
			 +"\n 		group by a.TBH_FK"
			 +"\n  	) DS1 on DS1.TBH_FK = tbh.PK_SEQ"
			 +"\n  	left join"
			 +"\n  	("
			 +"\n 		select COUNT(dh.PK_SEQ) as DH,a.TBH_FK "
			 +"\n 		from KHACHHANG_TUYENBH a"
			 +"\n 		inner join DONHANG dh on dh.KHACHHANG_FK = a.KHACHHANG_FK"
			 +"\n 		where "+tt
			 +"\n 		and "
			 +"\n 	 	MONTH(NGAYNHAP) = '"+tungay+"' and YEAR(NGAYNHAP) = '"+denngay+"'"
			 +"\n 	 	AND DATEPART(WEEK, NGAYNHAP)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,NGAYNHAP), 0))+ 1 = 1"
			 +"\n 		group by a.TBH_FK"
			 +"\n  	) SODH1 on SODH1.TBH_FK = tbh.PK_SEQ"
			 +"\n   left join"
			 +"\n  	("
			 +"\n 		select COUNT(dhsp.SANPHAM_FK)/(COUNT(distinct dh.PK_SEQ)*1.0) as DH,a.TBH_FK "
			 +"\n 		from KHACHHANG_TUYENBH a"
			 +"\n 		inner join DONHANG dh on dh.KHACHHANG_FK = a.KHACHHANG_FK"
			 +"\n 		inner join DONHANG_SANPHAM dhsp on dhsp.DONHANG_FK = dh.PK_SEQ"
			 +"\n 		where "+tt
			 +"\n 		and MONTH(NGAYNHAP) = '"+tungay+"' and YEAR(NGAYNHAP) = '"+denngay+"'"
			 +"\n 	 	AND DATEPART(WEEK, NGAYNHAP)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,NGAYNHAP), 0))+ 1 = 1"
			 +"\n 		group by a.TBH_FK"
			 +"\n  	) SKU1 on SKU1.TBH_FK = tbh.PK_SEQ"
			 +"\n  	left join"
			 +"\n  	("
			 +"\n 		select COUNT(b.FILENAME) as DH,a.TBH_FK "
			 +"\n 		from KHACHHANG_TUYENBH a"
			 +"\n 		inner join KHACHHANG_ANHCHUP b on a.KHACHHANG_FK = b.KH_FK"
			 +"\n 	 	where MONTH(b.NGAY) = '"+tungay+"' and YEAR(b.NGAY) = '"+denngay+"'"
			 +"\n 	 	AND DATEPART(WEEK, NGAY)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,NGAY), 0))+ 1 = 1"
			 +"\n 		group by a.TBH_FK"
			 +"\n  	) AC1 on AC1.TBH_FK = tbh.PK_SEQ "
			 +"\n   left join "
			 +"\n 	("
			 +"\n 		select COUNT(dh.PK_SEQ) as DHtraituyen,a.TBH_FK "
			 +"\n 		from KHACHHANG_TUYENBH a "
			 +"\n 		inner join DONHANG dh on dh.KHACHHANG_FK = a.KHACHHANG_FK "
			 +"\n 		inner join DONHANG_SANPHAM dhsp on dhsp.DONHANG_FK = dh.PK_SEQ "
			 +"\n 		where  dh.TRANGTHAI <>  2  "
			 +"\n 		and dh.isdungtuyen='0' "
			 +"\n 		and MONTH(dh.ngaynhap) = '"+tungay+"' and YEAR(dh.ngaynhap) = '"+denngay+"' "
			 +"\n 		AND DATEPART(WEEK, dh.ngaynhap)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,dh.ngaynhap), 0))+ 1 = 1 "			 	
			 +"\n 		group by a.TBH_FK "
			 +"\n  	) SoDHTT1 on DS1.TBH_FK = tbh.PK_SEQ "
			 +"\n  	left join "
			 +"\n   ( "
			 +"\n  		select COUNT(a.khachhang_fk) as VTtraituyen,b.TBH_FK  "
			 +"\n  		from ddkd_khachhang_w a inner join KHACHHANG_TUYENBH b "
			 +"\n  		on a.khachhang_fk = b.KHACHHANG_FK "
			 +"\n  		where "
			 +"\n   	MONTH(a.thoidiem) = '"+tungay+"' and YEAR(a.thoidiem) = '"+denngay+"' "
			 +"\n  		and a.isdungtuyen = '0' "
			 +"\n  		AND DATEPART(WEEK, a.thoidiem)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,a.thoidiem), 0))+ 1 = 1 "
			 +"\n   	and exists (select 1 from khachhang kh where trangthai = 1 and kh.pk_seq = b.khachhang_fk ) "
			 +"\n  		group by b.TBH_FK " 
			 +"\n   ) SoVTTT1 on VT1.TBH_FK = tbh.PK_SEQ " +
	 		  "\n )";
			
			 
			//System.out.println("QRDATATK1: "+query);
			//db.update(query);
			
			
			sql +=   "IF OBJECT_ID('tempdb.dbo.#ThongKeTuan2') IS NOT NULL "
					+"\n DROP TABLE #ThongKeTuan2 "
					+"\n CREATE TABLE #ThongKeTuan2 ("
					+"\n Tbh_fk numeric(18,0),  "
					+"\n ASO int ,"
					+ "\n SOKHVT int,"
					+ "\n TG varchar(100),"
					+ "\n TGLV varchar(100),"
					+ "\n DoanhSo float,"
					+ "\n SODH  int,"
					+ "\n SODHTT  int,"
					+ "\n SOVTTT  int,"
					+ "\n SKU float,"
					+ "\n Anh int  "
					+ " )";
			//db.update(query);
			
			
			sql += 
					 "\n , ThongKeTuan2_w as ( "
					 +"\n	select tbh.pk_seq as Tbh_fk, SoKh2.sokh as ASO,isnulL(VT2.sokh,0) as SOKHVT, "
					 + " 		isnull(TGKH2.TGKh,'') as TG,ISNULL(TGLV2.TGLV,'') as TGLV,isnull(DS2.DoanhSo,0) as DoanhSo,isnull(SODH2.DH,0) as SODH,ISNULL(SoDHTT2.DHtraituyen, 0) as SoDHTT, ISNULL(SoVTTT2.VTtraituyen, 0) as SoVTTT,isnull(SKU2.DH,0) as SKU,isnull(AC2.DH,0) as Anh  from  TUYENBANHANG tbh  "
					 +"\n  	inner join "
					 +"\n  	("
					 +"\n 	select COUNT(distinct khachhang_fk) as sokh,TBH_FK "
					 +"\n 	from KHACHHANG_TUYENBH where exists (select 1 from khachhang kh where trangthai = 1 and kh.pk_seq = KHACHHANG_TUYENBH.khachhang_fk ) "
					 +"\n 	"
					 +"\n 	group by TBH_FK"
					 +"\n  ) SoKh2 on SoKh2.TBH_FK = tbh.PK_SEQ"
					 +"\n  left join"
					 +"\n  ("
					 +"\n 	select COUNT(a.khachhang_fk) as sokh,b.TBH_FK "
					 +"\n 	from ddkd_khachhang_w a inner join KHACHHANG_TUYENBH b"
					 +"\n 	on a.khachhang_fk = b.KHACHHANG_FK"
					 +"\n 	where "
					 +"\n 	 MONTH(thoidiem) = '"+tungay+"' and YEAR(thoidiem) = '"+denngay+"'"
					 +"\n 	AND DATEPART(WEEK, thoidiem)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,thoidiem), 0))+ 1 = 2 "
					 + "\n  and exists (select 1 from khachhang kh where trangthai = 1 and kh.pk_seq = b.khachhang_fk )"
					 +"\n 	group by b.TBH_FK"
					 +"\n  ) VT2 on VT2.TBH_FK = tbh.PK_SEQ"
					 +"\n  left join"
					 +"\n  ("
					 +"\n 	select convert(char(5), (select dateadd(minute,Sum(isnull(a.thoigiantaicuahang,0)),0)), 108)"
					 +"\n 	 as TGKh,b.TBH_FK "
					 +"\n 	from ddkd_khachhang_w a inner join KHACHHANG_TUYENBH b"
					 +"\n 	on a.khachhang_fk = b.KHACHHANG_FK"
					 +"\n 	where "
					 +"\n 	 MONTH(thoidiem) = '"+tungay+"' and YEAR(thoidiem) = '"+denngay+"'"
					 +"\n 	 AND DATEPART(WEEK, thoidiem)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,thoidiem), 0))+ 1 = 2"
					 + "\n  and exists (select 1 from khachhang kh where trangthai = 1 and kh.pk_seq = b.khachhang_fk )"
					 +"\n 	group by b.TBH_FK"
					 +"\n  ) TGKH2 on TGKH2.TBH_FK = tbh.PK_SEQ"
					 +"\n  left join"
					 +"\n  ("
					 +"\n 	select  CONVERT(char(8), DATEADD(SECOND, DATEDIFF(SECOND,Min(a.thoidiem),Max(a.thoidiemdi)),0), 108) as TGLV,b.TBH_FK "
					 +"\n 	from ddkd_khachhang_w a inner join KHACHHANG_TUYENBH b"
					 +"\n 	on a.khachhang_fk = b.KHACHHANG_FK"
					 +"\n 	where "
					 +"\n 	 MONTH(thoidiem) = '"+tungay+"' and YEAR(thoidiem) = '"+denngay+"'"
					 +"\n 	AND DATEPART(WEEK, thoidiem)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,thoidiem), 0))+ 1 = 2"
					 + "\n  and exists (select 1 from khachhang kh where trangthai = 1 and kh.pk_seq = b.khachhang_fk )"
					 +"\n 	group by b.TBH_FK"
					 +"\n  ) TGLV2 on TGLV2.TBH_FK = tbh.PK_SEQ"
					 +"\n  left join"
					 +"\n  ("
					 +"\n 	select SUM(dhsp.SOLUONG*dhsp.GIAMUA) as DoanhSo,a.TBH_FK "
					 +"\n 	from KHACHHANG_TUYENBH a"
					 +"\n 	inner join DONHANG dh on dh.KHACHHANG_FK = a.KHACHHANG_FK"
					 +"\n 	inner join DONHANG_SANPHAM dhsp on dhsp.DONHANG_FK = dh.PK_SEQ"
					 +"\n 	where "+tt
					 +"\n 	and MONTH(dh.NGAYNHAP) = '"+tungay+"' and YEAR(dh.NGAYNHAP) = '"+denngay+"'"
					 +"\n 	AND DATEPART(WEEK, NGAYNHAP)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,NGAYNHAP), 0))+ 1 = 2"
					 +"\n 	"
					 +"\n 	group by a.TBH_FK"
					 +"\n  ) DS2 on DS2.TBH_FK = tbh.PK_SEQ"
					 +"\n  left join"
					 +"\n  ("
					 +"\n 	select COUNT(dh.PK_SEQ) as DH,a.TBH_FK "
					 +"\n 	from KHACHHANG_TUYENBH a"
					 +"\n 	inner join DONHANG dh on dh.KHACHHANG_FK = a.KHACHHANG_FK"
					 +"\n 	where "+tt
					 +"\n 	and MONTH(dh.NGAYNHAP) = '"+tungay+"' and YEAR(dh.NGAYNHAP) = '"+denngay+"'"
					 +"\n 	 AND DATEPART(WEEK, NGAYNHAP)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,NGAYNHAP), 0))+ 1 = 2"
					 +"\n 	group by a.TBH_FK"
					 +"\n  ) SODH2 on SODH2.TBH_FK = tbh.PK_SEQ"
					 +"\n   left join"
					 +"\n  ("
					 +"\n 	select COUNT(dhsp.SANPHAM_FK)/(COUNT(distinct dh.PK_SEQ)*2.0) as DH,a.TBH_FK "
					 +"\n 	from KHACHHANG_TUYENBH a"
					 +"\n 	inner join DONHANG dh on dh.KHACHHANG_FK = a.KHACHHANG_FK"
					 +"\n 	inner join DONHANG_SANPHAM dhsp on dhsp.DONHANG_FK = dh.PK_SEQ"
					 +"\n 	where "+tt
					 +"\n 	and MONTH(dh.NGAYNHAP) = '"+tungay+"' and YEAR(dh.NGAYNHAP) = '"+denngay+"'"
					 +"\n 	AND DATEPART(WEEK, NGAYNHAP)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,NGAYNHAP), 0))+ 1 = 2"
					 +"\n 	group by a.TBH_FK"
					 +"\n  ) SKU2 on SKU2.TBH_FK = tbh.PK_SEQ"
					 +"\n  left join"
					 +"\n  ("
					 +"\n 	select COUNT(b.FILENAME) as DH,a.TBH_FK "
					 +"\n 	from KHACHHANG_TUYENBH a"
					 +"\n 	inner join KHACHHANG_ANHCHUP b on a.KHACHHANG_FK = b.KH_FK"
					 +"\n 	where"
					 +"\n 	 MONTH(b.NGAY) = '"+tungay+"' and YEAR(b.NGAY) = '"+denngay+"'"
					 +"\n 	AND DATEPART(WEEK, NGAY)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,NGAY), 0))+ 1 = 2"
					 +"\n 	group by a.TBH_FK"
					 +"\n  ) AC2 on AC2.TBH_FK = tbh.PK_SEQ "
					 +"\n   left join "
					 +"\n ("
					 +"\n 	select COUNT(dh.PK_SEQ) as DHtraituyen,a.TBH_FK "
					 +"\n 	from KHACHHANG_TUYENBH a "
					 +"\n 	inner join DONHANG dh on dh.KHACHHANG_FK = a.KHACHHANG_FK "
					 +"\n 	inner join DONHANG_SANPHAM dhsp on dhsp.DONHANG_FK = dh.PK_SEQ "
					 +"\n 	where  dh.TRANGTHAI <>  2  "
					 +"\n 	and dh.isdungtuyen='0' "
					 +"\n 	and "
					 +"\n 	MONTH(dh.ngaynhap) = '"+tungay+"' and YEAR(dh.ngaynhap) = '"+denngay+"' "
					 +"\n 	AND DATEPART(WEEK, dh.ngaynhap)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,dh.ngaynhap), 0))+ 1 = 2 "			 	
					 +"\n 	group by a.TBH_FK "
					 +"\n  ) SoDHTT2 on DS2.TBH_FK = tbh.PK_SEQ "
					 +"\n  left join "
					 +"\n   ( "
					 +"\n  	select COUNT(a.khachhang_fk) as VTtraituyen,b.TBH_FK  "
					 +"\n  	from ddkd_khachhang_w a inner join KHACHHANG_TUYENBH b "
					 +"\n  	on a.khachhang_fk = b.KHACHHANG_FK "
					 +"\n  	where "
					 +"\n   	MONTH(a.thoidiem) = '"+tungay+"' and YEAR(a.thoidiem) = '"+denngay+"' "
					 +"\n  	and a.isdungtuyen = '0' "
					 +"\n  	AND DATEPART(WEEK, a.thoidiem)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,a.thoidiem), 0))+ 1 = 2 "
					 +"\n   and exists (select 1 from khachhang kh where trangthai = 1 and kh.pk_seq = b.khachhang_fk ) "
					 +"\n  	group by b.TBH_FK " 
					 +"\n   ) SoVTTT2 on VT2.TBH_FK = tbh.PK_SEQ " +
			 		  "\n )";
					//System.out.println("QRDATATK2: "+query);
					//db.update(query);
					
					query="IF OBJECT_ID('tempdb.dbo.#ThongKeTuan3') IS NOT NULL "
							+"\n DROP TABLE #ThongKeTuan3 "
							+"\n CREATE TABLE #ThongKeTuan3 ("
							+"\n Tbh_fk numeric(18,0),  "
							+"\n ASO int ,"
							+ "\n SOKHVT int,"
							+ "\n TG varchar(100),"
							+ "\n TGLV varchar(100),"
							+ "\n DoanhSo float,"
							+ "\n SODH  int,"
							+ "\n SODHTT  int,"
							+ "\n SOVTTT  int,"
							+ "\n SKU float,"
							+ "\n Anh int  "
							+ " )";
					db.update(query);
					
					sql += 
							 "\n , ThongKeTuan3_w as ( "
							 + "select  tbh.pk_seq as Tbh_fk, SoKh3.sokh as ASO, isnulL(VT3.sokh,0) as SOKHVT, "
							 +" \n isnull(TGKH3.TGKh,'') as TG,ISNULL(TGLV3.TGLV,'') as TGLV, isnull(DS3.DoanhSo,0) as DoanhSo, isnull(SODH3.DH,0) as SODH, ISNULL(SoDHTT3.DHtraituyen, 0) as SoDHTT, ISNULL(SoVTTT3.VTtraituyen, 0) as SoVTTT,isnull(SKU3.DH,0) as SKU,isnull(AC3.DH,0) as Anh "
							 + "\n from  TUYENBANHANG tbh  "
							 +"\n  inner join "
							 +"\n  ("
							 +"\n 	select COUNT(distinct khachhang_fk) as sokh,TBH_FK "
							 +"\n 	from KHACHHANG_TUYENBH "
							 + "\n  where exists (select 1 from khachhang kh where trangthai = 1 and kh.pk_seq = KHACHHANG_TUYENBH.khachhang_fk )"
							 +"\n 	group by TBH_FK"
							 +"\n  ) SoKh3 on SoKh3.TBH_FK = tbh.PK_SEQ"
							 +"\n  left join"
							 +"\n  ("
							 +"\n 	select COUNT(a.khachhang_fk) as sokh,b.TBH_FK "
							 +"\n 	from ddkd_khachhang_w a inner join KHACHHANG_TUYENBH b"
							 +"\n 	on a.khachhang_fk = b.KHACHHANG_FK"
							 +"\n 	where"
							 +"\n 	 MONTH(a.thoidiem) = '"+tungay+"' and YEAR(a.thoidiem) = '"+denngay+"'"
							 +"\n 	 AND DATEPART(WEEK, thoidiem)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,thoidiem), 0))+ 1 = 3"
							 + "\n  and exists (select 1 from khachhang kh where trangthai = 1 and kh.pk_seq = b.khachhang_fk )"
							 +"\n 	group by b.TBH_FK"
							 +"\n  ) VT3 on VT3.TBH_FK = tbh.PK_SEQ"
							 +"\n  left join"
							 +"\n  ("
							 +"\n 	select convert(char(5), (select dateadd(minute,Sum(isnull(a.thoigiantaicuahang,0)),0)), 108) as TGKh,b.TBH_FK "
							 +"\n 	from ddkd_khachhang_w a inner join KHACHHANG_TUYENBH b"
							 +"\n 	on a.khachhang_fk = b.KHACHHANG_FK"
							 +"\n 	where"
							 +"\n 	 MONTH(a.thoidiem) = '"+tungay+"' and YEAR(a.thoidiem) = '"+denngay+"'"
							 +"\n 	  AND DATEPART(WEEK, thoidiem)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,thoidiem), 0))+ 1 = 3"
							 + "\n  and exists (select 1 from khachhang kh where trangthai = 1 and kh.pk_seq = b.khachhang_fk )"
							 +"\n 	group by b.TBH_FK"
							 +"\n  ) TGKH3 on TGKH3.TBH_FK = tbh.PK_SEQ"
							 +"\n  left join"
							 +"\n  ("
							 +"\n 	select  CONVERT(char(8), DATEADD(SECOND, DATEDIFF(SECOND,Min(a.thoidiem),Max(a.thoidiemdi)),0), 108) as TGLV,b.TBH_FK "
							 +"\n 	from ddkd_khachhang_w a inner join KHACHHANG_TUYENBH b"
							 +"\n 	on a.khachhang_fk = b.KHACHHANG_FK"
							 +"\n 	where"
							 +"\n 	 MONTH(a.thoidiem) = '"+tungay+"' and YEAR(a.thoidiem) = '"+denngay+"'"
							 +"\n 	  AND DATEPART(WEEK, thoidiem)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,thoidiem), 0))+ 1 = 3"
							 + "\n  and exists (select 1 from khachhang kh where trangthai = 1 and kh.pk_seq = b.khachhang_fk )"
							 +"\n 	group by b.TBH_FK"
							 +"\n  ) TGLV3 on TGLV3.TBH_FK = tbh.PK_SEQ"
							 +"\n  left join"
							 +"\n  ("
							 +"\n 	select SUM(dhsp.SOLUONG*dhsp.GIAMUA) as DoanhSo,a.TBH_FK "
							 +"\n 	from KHACHHANG_TUYENBH a"
							 +"\n 	inner join DONHANG dh on dh.KHACHHANG_FK = a.KHACHHANG_FK"
							 +"\n 	inner join DONHANG_SANPHAM dhsp on dhsp.DONHANG_FK = dh.PK_SEQ"
							 +"\n 	where "+tt
							 +"\n 	and"
							 +"\n 	 MONTH(dh.NGAYNHAP) = '"+tungay+"' and YEAR(dh.NGAYNHAP) = '"+denngay+"'"
							 +"\n 	  AND DATEPART(WEEK, NGAYNHAP)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,NGAYNHAP), 0))+ 1 = 3"
							 +"\n 	group by a.TBH_FK"
							 +"\n  ) DS3 on DS3.TBH_FK = tbh.PK_SEQ"
							 +"\n  left join"
							 +"\n  ("
							 +"\n 	select COUNT(dh.PK_SEQ) as DH,a.TBH_FK "
							 +"\n 	from KHACHHANG_TUYENBH a"
							 +"\n 	inner join DONHANG dh on dh.KHACHHANG_FK = a.KHACHHANG_FK"
							 +"\n 	where "+tt
							 +"\n 	and"
							 +"\n 	 MONTH(dh.NGAYNHAP) = '"+tungay+"' and YEAR(dh.NGAYNHAP) = '"+denngay+"'"
							 +"\n 	 AND DATEPART(WEEK, NGAYNHAP)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,NGAYNHAP), 0))+ 1 = 3"
							 +"\n 	group by a.TBH_FK"
							 +"\n  ) SODH3 on SODH3.TBH_FK = tbh.PK_SEQ"
							 +"\n   left join"
							 +"\n  ("
							 +"\n 	select COUNT(dhsp.SANPHAM_FK)/(COUNT(distinct dh.PK_SEQ)*3.0) as DH,a.TBH_FK "
							 +"\n 	from KHACHHANG_TUYENBH a"
							 +"\n 	inner join DONHANG dh on dh.KHACHHANG_FK = a.KHACHHANG_FK"
							 +"\n 	inner join DONHANG_SANPHAM dhsp on dhsp.DONHANG_FK = dh.PK_SEQ"
							 +"\n 	where "+tt
							 +"\n 	and"
							 +"\n 	 MONTH(dh.NGAYNHAP) = '"+tungay+"' and YEAR(dh.NGAYNHAP) = '"+denngay+"'"
							 +"\n 	AND DATEPART(WEEK, NGAYNHAP)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,NGAYNHAP), 0))+ 1 = 3"
							 +"\n 	group by a.TBH_FK"
							 +"\n  ) SKU3 on SKU3.TBH_FK = tbh.PK_SEQ"
							 +"\n  left join"
							 +"\n  ("
							 +"\n 	select COUNT(b.FILENAME) as DH,a.TBH_FK "
							 +"\n 	from KHACHHANG_TUYENBH a"
							 +"\n 	inner join KHACHHANG_ANHCHUP b on a.KHACHHANG_FK = b.KH_FK"
							 +"\n 	and"
							 +"\n 	 MONTH(b.NGAY) = '"+tungay+"' and YEAR(b.ngay) = '"+denngay+"'"
							 +"\n 	 AND DATEPART(WEEK, NGAY)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,NGAY), 0))+ 1 = 3"
							 +"\n 	group by a.TBH_FK"
							 +"\n  ) AC3 on AC3.TBH_FK = tbh.PK_SEQ "
							 +"\n   left join "
							 +"\n ("
							 +"\n 	select COUNT(dh.PK_SEQ) as DHtraituyen,a.TBH_FK "
							 +"\n 	from KHACHHANG_TUYENBH a "
							 +"\n 	inner join DONHANG dh on dh.KHACHHANG_FK = a.KHACHHANG_FK "
							 +"\n 	inner join DONHANG_SANPHAM dhsp on dhsp.DONHANG_FK = dh.PK_SEQ "
							 +"\n 	where  dh.TRANGTHAI <>  2  "
							 +"\n 	and dh.isdungtuyen='0' "
							 +"\n 	and "
							 +"\n 	MONTH(dh.ngaynhap) = '"+tungay+"' and YEAR(dh.ngaynhap) = '"+denngay+"' "
							 +"\n 	AND DATEPART(WEEK, dh.ngaynhap)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,dh.ngaynhap), 0))+ 1 = 3 "			 	
							 +"\n 	group by a.TBH_FK "
							 +"\n  ) SoDHTT3 on DS3.TBH_FK = tbh.PK_SEQ "
							 +"\n  left join "
							 +"\n   ( "
							 +"\n  	select COUNT(a.khachhang_fk) as VTtraituyen,b.TBH_FK  "
							 +"\n  	from ddkd_khachhang_w a inner join KHACHHANG_TUYENBH b "
							 +"\n  	on a.khachhang_fk = b.KHACHHANG_FK "
							 +"\n  	where "
							 +"\n   	MONTH(a.thoidiem) = '"+tungay+"' and YEAR(a.thoidiem) = '"+denngay+"' "
							 +"\n  	and a.isdungtuyen = '0' "
							 +"\n  	AND DATEPART(WEEK, a.thoidiem)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,a.thoidiem), 0))+ 1 = 3 "
							 +"\n   and exists (select 1 from khachhang kh where trangthai = 1 and kh.pk_seq = b.khachhang_fk ) "
							 +"\n  	group by b.TBH_FK " 
							 +"\n   ) SoVTTT3 on VT3.TBH_FK = tbh.PK_SEQ " +
							  "\n )";
							System.out.println("QRDATATK3: "+query);
							db.update(query);
							
							query="IF OBJECT_ID('tempdb.dbo.#ThongKeTuan4') IS NOT NULL "
									+"\n DROP TABLE #ThongKeTuan4 "
									+"\n CREATE TABLE #ThongKeTuan4 ("
									+"\n Tbh_fk numeric(18,0),  "
									+"\n ASO int ,"
									+ "\n SOKHVT int,"
									+ "\n TG varchar(100),"
									+ "\n TGLV varchar(100),"
									+ "\n DoanhSo float,"
									+ "\n SODH  int,"
									+ "\n SODHTT  int,"
									+ "\n SOVTTT  int,"
									+ "\n SKU float,"
									+ "\n Anh int  "
									+ " )";
							db.update(query);
							
							sql += 
									 "\n , ThongKeTuan4_w as ( "
									 + "select  tbh.pk_seq as Tbh_fk, SoKh4.sokh as ASO, isnulL(VT4.sokh,0) as SOKHVT, "
									 +" isnull(TGKH4.TGKh,'') as TG, ISNULL(TGLV4.TGLV,'') as TGLV, isnull(DS4.DoanhSo,0) as DoanhSo, isnull(SODH4.DH,0) as SODH,ISNULL(SoDHTT4.DHtraituyen, 0) as SoDHTT, ISNULL(SoVTTT4.VTtraituyen, 0) as SoVTTT, isnull(SKU4.DH,0) as SKU, isnull(AC4.DH,0) as Anh "
									 + "\n from  TUYENBANHANG tbh  "
									 +"\n  inner join "
									 +"\n  ("
									 +"\n 	select COUNT(distinct khachhang_fk) as sokh,TBH_FK "
									 +"\n 	from KHACHHANG_TUYENBH "
									 + "\n  where exists (select 1 from khachhang kh where trangthai = 1 and kh.pk_seq = KHACHHANG_TUYENBH.khachhang_fk )"
									 +"\n 	group by TBH_FK"
									 +"\n  ) SoKh4 on SoKh4.TBH_FK = tbh.PK_SEQ"
									 +"\n  left join"
									 +"\n  ("
									 +"\n 	select COUNT(a.khachhang_fk) as sokh,b.TBH_FK "
									 +"\n 	from ddkd_khachhang_w a inner join KHACHHANG_TUYENBH b"
									 +"\n 	on a.khachhang_fk = b.KHACHHANG_FK"
									 +"\n 	where  "
									 +"\n 	 MONTH(a.thoidiem) = '"+tungay+"' and YEAR(a.thoidiem) = '"+denngay+"'"
									 +"\n 	AND DATEPART(WEEK, thoidiem)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,thoidiem), 0))+ 1 = 4"
									 + "\n  and exists (select 1 from khachhang kh where trangthai = 1 and kh.pk_seq = b.khachhang_fk )"
									 +"\n 	group by b.TBH_FK"
									 +"\n  ) VT4 on VT4.TBH_FK = tbh.PK_SEQ"
									 +"\n  left join"
									 +"\n  ("
									 +"\n 	select convert(char(5), (select dateadd(minute,Sum(isnull(a.thoigiantaicuahang,0)),0)), 108) as TGKh,b.TBH_FK "
									 +"\n 	from ddkd_khachhang_w a inner join KHACHHANG_TUYENBH b"
									 +"\n 	on a.khachhang_fk = b.KHACHHANG_FK"
									 +"\n 	where "
									 +"\n 	 MONTH(a.thoidiem) = '"+tungay+"' and YEAR(a.thoidiem) = '"+denngay+"'"
									 +"\n 	AND DATEPART(WEEK, thoidiem)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,thoidiem), 0))+ 1 = 4"
									 + "\n  and exists (select 1 from khachhang kh where trangthai = 1 and kh.pk_seq = b.khachhang_fk )"
									 +"\n 	group by b.TBH_FK"
									 +"\n  ) TGKH4 on TGKH4.TBH_FK = tbh.PK_SEQ"
									 +"\n  left join"
									 +"\n  ("
									 +"\n 	select  CONVERT(char(8), DATEADD(SECOND, DATEDIFF(SECOND,Min(a.thoidiem),Max(a.thoidiemdi)),0), 108) as TGLV,b.TBH_FK "
									 +"\n 	from ddkd_khachhang_w a inner join KHACHHANG_TUYENBH b"
									 +"\n 	on a.khachhang_fk = b.KHACHHANG_FK"
									 +"\n 	where "
									 +"\n 	 MONTH(a.thoidiem) = '"+tungay+"' and YEAR(a.thoidiem) = '"+denngay+"'"
									 +"\n 	AND DATEPART(WEEK, thoidiem)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,thoidiem), 0))+ 1 = 4"
									 + "\n  and exists (select 1 from khachhang kh where trangthai = 1 and kh.pk_seq = b.khachhang_fk )"
									 +"\n 	group by b.TBH_FK"
									 +"\n  ) TGLV4 on TGLV4.TBH_FK = tbh.PK_SEQ"
									 +"\n  left join"
									 +"\n  ("
									 +"\n 	select SUM(dhsp.SOLUONG*dhsp.GIAMUA) as DoanhSo,a.TBH_FK "
									 +"\n 	from KHACHHANG_TUYENBH a"
									 +"\n 	inner join DONHANG dh on dh.KHACHHANG_FK = a.KHACHHANG_FK"
									 +"\n 	inner join DONHANG_SANPHAM dhsp on dhsp.DONHANG_FK = dh.PK_SEQ"
									 +"\n 	where "+tt
									 +"\n 	 AND MONTH(dh.ngaynhap) = '"+tungay+"' and YEAR(dh.ngaynhap) = '"+denngay+"'"
									 +"\n 	AND DATEPART(WEEK, dh.ngaynhap)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,dh.ngaynhap), 0))+ 1 = 4"
									 +"\n 	group by a.TBH_FK"
									 +"\n  ) DS4 on DS4.TBH_FK = tbh.PK_SEQ"
									 +"\n  left join"
									 +"\n  ("
									 +"\n 	select COUNT(dh.PK_SEQ) as DH,a.TBH_FK "
									 +"\n 	from KHACHHANG_TUYENBH a"
									 +"\n 	inner join DONHANG dh on dh.KHACHHANG_FK = a.KHACHHANG_FK"
									 +"\n 	where "+tt
									 +"\n 	and MONTH(dh.ngaynhap) = '"+tungay+"' and YEAR(dh.ngaynhap) = '"+denngay+"'"
									 +"\n 	AND DATEPART(WEEK, dh.ngaynhap)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,dh.ngaynhap), 0))+ 1 = 4"
									 +"\n 	group by a.TBH_FK"
									 +"\n  ) SODH4 on SODH4.TBH_FK = tbh.PK_SEQ"
									 +"\n   left join"
									 +"\n  ("
									 +"\n 	select COUNT(dhsp.SANPHAM_FK)/(COUNT(distinct dh.PK_SEQ)*4.0) as DH,a.TBH_FK "
									 +"\n 	from KHACHHANG_TUYENBH a"
									 +"\n 	inner join DONHANG dh on dh.KHACHHANG_FK = a.KHACHHANG_FK"
									 +"\n 	inner join DONHANG_SANPHAM dhsp on dhsp.DONHANG_FK = dh.PK_SEQ"
									 +"\n 	where "+tt
									 +"\n 	and MONTH(dh.ngaynhap) = '"+tungay+"' and YEAR(dh.ngaynhap) = '"+denngay+"'"
									 +"\n 	AND DATEPART(WEEK, dh.ngaynhap)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,dh.ngaynhap), 0))+ 1 = 4"
									 +"\n 	group by a.TBH_FK"
									 +"\n  ) SKU4 on SKU4.TBH_FK = tbh.PK_SEQ"
									 +"\n  left join"
									 +"\n  ("
									 +"\n 	select COUNT(b.FILENAME) as DH,a.TBH_FK "
									 +"\n 	from KHACHHANG_TUYENBH a"
									 +"\n 	inner join KHACHHANG_ANHCHUP b on a.KHACHHANG_FK = b.KH_FK"
									 +"\n 	where"
									 +"\n 	MONTH(b.ngay) = '"+tungay+"' and YEAR(b.ngay) = '"+denngay+"'"
									 +"\n 	AND DATEPART(WEEK, b.ngay)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,b.ngay), 0))+ 1 = 4"
									 +"\n 	group by a.TBH_FK"
									 +"\n  ) AC4 on AC4.TBH_FK = tbh.PK_SEQ "
									 +"\n   left join "
									 +"\n ("
									 +"\n 	select COUNT(dh.PK_SEQ) as DHtraituyen,a.TBH_FK "
									 +"\n 	from KHACHHANG_TUYENBH a "
									 +"\n 	inner join DONHANG dh on dh.KHACHHANG_FK = a.KHACHHANG_FK "
									 +"\n 	inner join DONHANG_SANPHAM dhsp on dhsp.DONHANG_FK = dh.PK_SEQ "
									 +"\n 	where  dh.TRANGTHAI <>  2  "
									 +"\n 	and dh.isdungtuyen='0' "
									 +"\n 	and "
									 +"\n 	MONTH(dh.ngaynhap) = '"+tungay+"' and YEAR(dh.ngaynhap) = '"+denngay+"' "
									 +"\n 	AND DATEPART(WEEK, dh.ngaynhap)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,dh.ngaynhap), 0))+ 1 = 4 "			 	
									 +"\n 	group by a.TBH_FK "
									 +"\n  ) SoDHTT4 on DS4.TBH_FK = tbh.PK_SEQ "
									 +"\n  left join "
									 +"\n   ( "
									 +"\n  	select COUNT(a.khachhang_fk) as VTtraituyen,b.TBH_FK  "
									 +"\n  	from ddkd_khachhang_w a inner join KHACHHANG_TUYENBH b "
									 +"\n  	on a.khachhang_fk = b.KHACHHANG_FK "
									 +"\n  	where "
									 +"\n   	MONTH(a.thoidiem) = '"+tungay+"' and YEAR(a.thoidiem) = '"+denngay+"' "
									 +"\n  	and a.isdungtuyen = '0' "
									 +"\n  	AND DATEPART(WEEK, a.thoidiem)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,a.thoidiem), 0))+ 1 = 4 "
									 +"\n   and exists (select 1 from khachhang kh where trangthai = 1 and kh.pk_seq = b.khachhang_fk ) "
									 +"\n  	group by b.TBH_FK " 
									 +"\n   ) SoVTTT4 on VT4.TBH_FK = tbh.PK_SEQ " +
									  "\n )";
									System.out.println("QRDATATK4: "+query);
									db.update(query);
									
									query="IF OBJECT_ID('tempdb.dbo.#ThongKeTuan5') IS NOT NULL "
											+"\n DROP TABLE #ThongKeTuan5 "
											+"\n CREATE TABLE #ThongKeTuan5 ("
											+"\n Tbh_fk numeric(18,0),  "
											+"\n ASO int ,"
											+ "\n SOKHVT int,"
											+ "\n TG varchar(100),"
											+ "\n TGLV varchar(100),"
											+ "\n DoanhSo float,"
											+ "\n SODH  int,"
											+ "\n SODHTT  int,"
											+ "\n SOVTTT  int,"
											+ "\n SKU float,"
											+ "\n Anh int  "
											+ " )";
									db.update(query);
					
							sql += 
									 "\n , ThongKeTuan5_w as ( "
									 + "select  tbh.pk_seq as Tbh_fk, SoKh5.sokh as ASO, isnulL(VT5.sokh,0) as SOKHVT,"
									 +" isnull(TGKH5.TGKh,'') as TG,ISNULL(TGLV5.TGLV,'') as TGLV,isnull(DS5.DoanhSo,0) as DoanhSo, isnull(SODH5.DH,0) as SODH, ISNULL(SoDHTT5.DHtraituyen, 0) as SoDHTT, ISNULL(SoVTTT5.VTtraituyen, 0) as SoVTTT,isnull(SKU5.DH,0) as SKU,isnull(AC5.DH,0) as Anh "
									 + "\n from  TUYENBANHANG tbh  "
									 +"\n  inner join "
									 +"\n  ("
									 +"\n 	select COUNT(distinct khachhang_fk) as sokh,TBH_FK "
									 +"\n 	from KHACHHANG_TUYENBH "
									 + "\n  where exists (select 1 from khachhang kh where trangthai = 1 and kh.pk_seq = KHACHHANG_TUYENBH.khachhang_fk )"
									 +"\n 	group by TBH_FK"
									 +"\n  ) SoKh5 on SoKh5.TBH_FK = tbh.PK_SEQ"
									 +"\n  left join"
									 +"\n  ("
									 +"\n 	select COUNT(a.khachhang_fk) as sokh,b.TBH_FK "
									 +"\n 	from ddkd_khachhang_w a inner join KHACHHANG_TUYENBH b"
									 +"\n 	on a.khachhang_fk = b.KHACHHANG_FK"
									 +"\n 	where"
									 +"\n 	MONTH(a.thoidiem) = '"+tungay+"' and YEAR(a.thoidiem) = '"+denngay+"'"
									 +"\n 	AND DATEPART(WEEK, a.thoidiem)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,a.thoidiem), 0))+ 1 = 5"
									 + "\n  and exists (select 1 from khachhang kh where trangthai = 1 and kh.pk_seq = b.khachhang_fk )"
									 +"\n 	group by b.TBH_FK"
									 +"\n  ) VT5 on VT5.TBH_FK = tbh.PK_SEQ"
									 +"\n  left join"
									 +"\n  ("
									 +"\n 	select convert(char(5), (select dateadd(minute,Sum(isnull(a.thoigiantaicuahang,0)),0)), 108) as TGKh,b.TBH_FK "
									 +"\n 	from ddkd_khachhang_w a inner join KHACHHANG_TUYENBH b"
									 +"\n 	on a.khachhang_fk = b.KHACHHANG_FK"
									 +"\n 	where"
									 +"\n 	MONTH(a.thoidiem) = '"+tungay+"' and YEAR(a.thoidiem) = '"+denngay+"'"
									 +"\n 	AND DATEPART(WEEK, a.thoidiem)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,a.thoidiem), 0))+ 1 = 5"
									 + "\n  and exists (select 1 from khachhang kh where trangthai = 1 and kh.pk_seq = b.khachhang_fk )"
									 +"\n 	group by b.TBH_FK"
									 +"\n  ) TGKH5 on TGKH5.TBH_FK = tbh.PK_SEQ"
									 +"\n  left join"
									 +"\n  ("
									 +"\n 	select  CONVERT(char(8), DATEADD(SECOND, DATEDIFF(SECOND,Min(a.thoidiem),Max(a.thoidiemdi)),0), 108) as TGLV,b.TBH_FK "
									 +"\n 	from ddkd_khachhang_w a inner join KHACHHANG_TUYENBH b"
									 +"\n 	on a.khachhang_fk = b.KHACHHANG_FK"
									 +"\n 	where"
									 +"\n 	MONTH(a.thoidiem) = '"+tungay+"' and YEAR(a.thoidiem) = '"+denngay+"'"
									 +"\n 	AND DATEPART(WEEK, a.thoidiem)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,a.thoidiem), 0))+ 1 = 5"
									 + "\n  and exists (select 1 from khachhang kh where trangthai = 1 and kh.pk_seq = b.khachhang_fk )"
									 +"\n 	group by b.TBH_FK"
									 +"\n  ) TGLV5 on TGLV5.TBH_FK = tbh.PK_SEQ"
									 +"\n  left join"
									 +"\n  ("
									 +"\n 	select SUM(dhsp.SOLUONG*dhsp.GIAMUA) as DoanhSo,a.TBH_FK "
									 +"\n 	from KHACHHANG_TUYENBH a"
									 +"\n 	inner join DONHANG dh on dh.KHACHHANG_FK = a.KHACHHANG_FK"
									 +"\n 	inner join DONHANG_SANPHAM dhsp on dhsp.DONHANG_FK = dh.PK_SEQ"
									 +"\n 	where "+tt
									 +"\n 	and"
									 +"\n 	MONTH(dh.ngaynhap) = '"+tungay+"' and YEAR(dh.ngaynhap) = '"+denngay+"'"
									 +"\n 	AND DATEPART(WEEK, dh.ngaynhap)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,dh.ngaynhap), 0))+ 1 = 5"
									 +"\n 	"
									 +"\n 	group by a.TBH_FK"
									 +"\n  ) DS5 on DS5.TBH_FK = tbh.PK_SEQ"
									 +"\n  left join"
									 +"\n  ("
									 +"\n 	select COUNT(dh.PK_SEQ) as DH,a.TBH_FK "
									 +"\n 	from KHACHHANG_TUYENBH a"
									 +"\n 	inner join DONHANG dh on dh.KHACHHANG_FK = a.KHACHHANG_FK"
									 +"\n 	where "+tt
									 +"\n 	and"
									 +"\n 	MONTH(dh.ngaynhap) = '"+tungay+"' and YEAR(dh.ngaynhap) = '"+denngay+"'"
									 +"\n 	AND DATEPART(WEEK, dh.ngaynhap)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,dh.ngaynhap), 0))+ 1 = 5"
									 +"\n 	group by a.TBH_FK"
									 +"\n  ) SODH5 on SODH5.TBH_FK = tbh.PK_SEQ"
									 +"\n   left join"
									 +"\n  ("
									 +"\n 	select COUNT(dhsp.SANPHAM_FK)/(COUNT(distinct dh.PK_SEQ)*5.0) as DH,a.TBH_FK "
									 +"\n 	from KHACHHANG_TUYENBH a"
									 +"\n 	inner join DONHANG dh on dh.KHACHHANG_FK = a.KHACHHANG_FK"
									 +"\n 	inner join DONHANG_SANPHAM dhsp on dhsp.DONHANG_FK = dh.PK_SEQ"
									 +"\n 	where "+tt
									 +"\n 	and"
									 +"\n 	MONTH(dh.ngaynhap) = '"+tungay+"' and YEAR(dh.ngaynhap) = '"+denngay+"'"
									 +"\n 	AND DATEPART(WEEK, dh.ngaynhap)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,dh.ngaynhap), 0))+ 1 = 5"
									 +"\n 	group by a.TBH_FK"
									 +"\n  ) SKU5 on SKU5.TBH_FK = tbh.PK_SEQ"
									 +"\n  left join"
									 +"\n  ("
									 +"\n 	select COUNT(b.FILENAME) as DH,a.TBH_FK "
									 +"\n 	from KHACHHANG_TUYENBH a"
									 +"\n 	inner join KHACHHANG_ANHCHUP b on a.KHACHHANG_FK = b.KH_FK"
									 +"\n 	where"
									 +"\n 	MONTH(b.ngay) = '"+tungay+"' and YEAR(b.ngay) = '"+denngay+"'"
									 +"\n 	AND DATEPART(WEEK, b.NGAY)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,b.NGAY), 0))+ 1 = 5"
									 +"\n 	group by a.TBH_FK"
									 +"\n  ) AC5 on AC5.TBH_FK = tbh.PK_SEQ"
									 +"\n   left join "
									 +"\n ("
									 +"\n 	select COUNT(dh.PK_SEQ) as DHtraituyen,a.TBH_FK "
									 +"\n 	from KHACHHANG_TUYENBH a "
									 +"\n 	inner join DONHANG dh on dh.KHACHHANG_FK = a.KHACHHANG_FK "
									 +"\n 	inner join DONHANG_SANPHAM dhsp on dhsp.DONHANG_FK = dh.PK_SEQ "
									 +"\n 	where  dh.TRANGTHAI <>  2  "
									 +"\n 	and dh.isdungtuyen='0' "
									 +"\n 	and "
									 +"\n 	MONTH(dh.ngaynhap) = '"+tungay+"' and YEAR(dh.ngaynhap) = '"+denngay+"' "
									 +"\n 	AND DATEPART(WEEK, dh.ngaynhap)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,dh.ngaynhap), 0))+ 1 = 5 "			 	
									 +"\n 	group by a.TBH_FK "
									 +"\n  ) SoDHTT5 on DS5.TBH_FK = tbh.PK_SEQ "
									 +"\n  left join "
									 +"\n   ( "
									 +"\n  	select COUNT(a.khachhang_fk) as VTtraituyen,b.TBH_FK  "
									 +"\n  	from ddkd_khachhang_w a inner join KHACHHANG_TUYENBH b "
									 +"\n  	on a.khachhang_fk = b.KHACHHANG_FK "
									 +"\n  	where "
									 +"\n   	MONTH(a.thoidiem) = '"+tungay+"' and YEAR(a.thoidiem) = '"+denngay+"' "
									 +"\n  	and a.isdungtuyen = '0' "
									 +"\n  	AND DATEPART(WEEK, a.thoidiem)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,a.thoidiem), 0))+ 1 = 5 "
									 +"\n   and exists (select 1 from khachhang kh where trangthai = 1 and kh.pk_seq = b.khachhang_fk ) "
									 +"\n  	group by b.TBH_FK " 
									 +"\n   ) SoVTTT5 on VT5.TBH_FK = tbh.PK_SEQ " +
									  "\n  )";
									System.out.println("QRDATATK5: "+query);
											db.update(query);
											
			 sql +=
					 		"\n select V.TEN as Vung,kv.TEN as KHUVUC, ddkd.TEN as NVBH,"
					 		+ "\n tbh.NGAYID, t1.ASO,t1.SOKHVT,t1.TG,t1.TGLV,t1.DoanhSo,t1.SODH,t1.SoDHTT, t1.SoVTTT,t1.SKU,t1.Anh, "
							 +"\n tbh.NGAYID,t2.ASO,t2.SOKHVT,t2.TG,t2.TGLV,t2.DoanhSo,t2.SODH,t2.SoDHTT, t2.SoVTTT,t2.SKU,t2.Anh, "
							 +"\n tbh.NGAYID,t3.ASO,t3.SOKHVT,t3.TG,t3.TGLV,t3.DoanhSo,t3.SODH,t3.SoDHTT, t3.SoVTTT,t3.SKU,t3.Anh, "
							 +"\n tbh.NGAYID,t4.ASO,t4.SOKHVT,t4.TG,t4.TGLV,t4.DoanhSo,t4.SODH,t4.SoDHTT, t4.SoVTTT,t4.SKU,t4.Anh, "
							 +"\n tbh.NGAYID,t5.ASO,t5.SOKHVT,t5.TG,t5.TGLV,t5.DoanhSo,t5.SODH,t5.SoDHTT, t5.SoVTTT,t5.SKU,t5.Anh "
							 +"\n  from NHAPHANPHOI npp "
							 +"\n left join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK"
							 +"\n left join VUNG v on v.PK_SEQ = kv.VUNG_FK"
							 +"\n inner join DAIDIENKINHDOANH ddkd on ddkd.NPP_FK = npp.PK_SEQ"
							 +"\n inner join TUYENBANHANG tbh on tbh.NPP_FK = npp.PK_SEQ  and tbh.DDKD_FK = ddkd.PK_SEQ"
							 +"\n inner join ThongKeTuan1_w t1 on t1.tbh_fk = tbh.pk_seq "
							 +"\n inner join ThongKeTuan2_w t2 on t2.tbh_fk = tbh.pk_seq "
							 +"\n inner join ThongKeTuan3_w t3 on t3.tbh_fk = tbh.pk_seq "
							 +"\n inner join ThongKeTuan4_w t4 on t4.tbh_fk = tbh.pk_seq "
							 +"\n inner join ThongKeTuan5_w t5 on t5.tbh_fk = tbh.pk_seq "
							 +"\n  where ddkd.TRANGTHAI = 1 and   npp.PK_SEQ in ("+nppid+") and npp.trangthai = 1 "
							 +"\n order by ddkd.PK_SEQ,tbh.NGAYID";

					 
				
			System.out.println("Get NVBH ko PDA: "+sql);
			ResultSet rs = db.get(sql);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();
			
			int countRow = 5;

			for( int i =1 ; i <=socottrongSql ; i ++  )
			{
				cell = cells.getCell(countRow,i-1 );cell.setValue(rsmd.getColumnName(i));
				cells.setColumnWidth(i, 20.0f);
				ReportAPI.setCellBackground(cell, new Color(70,130,180), BorderLineType.THIN, true, 0);	
			 
			}
			
			
			int stt = 0;
			while(rs.next())
			{
				
				stt++;
			
				for(int i =1;i <=socottrongSql ; i ++)
				{
					cell = cells.getCell(countRow,i-1 );
					if(rsmd.getColumnName(i).equals("STT"))
					{					
						cell.setValue(stt);
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
						//System.out.println("STT: "+stt);

					}else
					if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i) == Types.INTEGER || rsmd.getColumnType(i) == Types.DECIMAL || rsmd.getColumnType(i) == Types.FLOAT || rsmd.getColumnType(i) == Types.NUMERIC )
					{
						cell.setValue(rs.getDouble(i));
						if(!rsmd.getColumnName(i).equals("SKU"))
							ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
						else
							ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
						
					}
					else
					{
							
							
							cell.setValue(rs.getString(i));
							
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					}
				}
				++countRow;
			}
			fstream.close();
			
			

			if(rs!=null)rs.close();
			if(db!=null){
				db.shutDown();
			}
			
	
		}catch(Exception ex){
			
			System.out.println("Errrorr : "+ex.toString());
			ex.printStackTrace();
			throw new Exception("Lỗi không tạo được báo cáo !");
		}
	}
	*/
	private void TaoBaoCaoNew(Workbook workbook,String nguoitao,String tungay,String denngay,String nppid, String trangthai, String userid  )throws Exception
	{
		try{
			
			String query = GetQuery( tungay, denngay, nppid,  trangthai,  userid );
			
			FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BCThongKeDMS.xlsm");
			//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\BCThongKeDMS.xlsm");
			//FileInputStream fstream = new FileInputStream(f) ;
			workbook.open(fstream);
			
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("ThongKeDMS");
		
			dbutils db = new dbutils();
			Cells cells = worksheet.getCells();
			cells.setRowHeight(0, 50.0f);
			Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 16,"Báo Cáo Thống Kê Sử Dụng DMS");

			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Ngày tạo : "+ getDateTime());
			
			cell = cells.getCell("A2");
			cells.setColumnWidth(0, 20.0f);
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Tháng: "+ tungay);
			cell = cells.getCell("B2");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Năm: "+ denngay);
			cells.setColumnWidth(4, 20.0f);
			cells.setColumnWidth(9, 15.0f);
			cells.setColumnWidth(8, 15.0f);
			cells.setColumnWidth(7, 15.0f);
			cells.setColumnWidth(6, 15.0f);
			cell = cells.getCell("A4");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10,"Người tạo : " + nguoitao);
		
			/*String tt="";
			System.out.println("Trang thai "+trangthai);
			if(trangthai.trim().length()>0)
			{
				if(trangthai.equals("0"))
					tt=" dh.TRANGTHAI <>  1 and dh.TRANGTHAI <>  2" ;
				else
					tt=" dh.TRANGTHAI =  1" ;
				
			}
			else
				tt=" dh.TRANGTHAI <>  2 ";
			
			query = "IF OBJECT_ID('tempdb.dbo.#BC') IS NOT NULL DROP TABLE #BC " +      
			"\n CREATE TABLE #BC " +      
			"\n (   " +      
			"\n   	Tbh_fk NUMERIC(18,0), NPP_FK NUMERIC(18,0), " +      
			"\n   	TUAN INT, ASO INT,ASO1 INT , ddkd_fk numeric(18,0), " +      
			"\n   	SOKHVT INT, TG VARCHAR(250), TGLV VARCHAR(250), DoanhSo FLOAT, SODH INT, SoDHTT INT, SoVTTT INT, SKU INT, Anh INT " +      
			"\n ) ";
			db.update(query);
			
			query =	"\n INSERT #BC(Tbh_fk, NPP_FK, TUAN, ASO, ASO1,ddkd_fk, SOKHVT, TG, TGLV, DoanhSo, SODH, SoDHTT, SoVTTT, SKU, Anh) " +      
			"\n select tbh.pk_seq as Tbh_fk, tbh.NPP_FK, tbh.TUAN, SoKh1.sokh as ASO, SoKh12.sokh as ASO1,tbh.DDKD_FK,isnulL(VT1.sokh,0) as SOKHVT,isnull(VT1.TGKh,'') as TG,ISNULL(VT1.TGLV,'') as TGLV,isnull(DS1.DoanhSo,0) as DoanhSo,isnull(DS1.SODH,0) as SODH,ISNULL(SoDHTT1.DHtraituyen, 0) as SoDHTT, ISNULL(SoVTTT1.VTtraituyen, 0) as SoVTTT,isnull(DS1.TBSKU,0) as SKU,isnull(AC1.DH,0) as Anh   " +      
			"\n from   " +      
			"\n ( " +      
			"\n 	SELECT pk_seq, DIENGIAI, NGAYLAMVIEC, DDKD_FK, NGAYID, NPP_FK, TUAN  FROM " +      
			"\n 	TUYENBANHANG tbh, ( " +      
			"\n 		select 1 AS TUAN " +      
			"\n 		UNION  " +      
			"\n 		SELECT 2 AS TUAN " +      
			"\n 		UNION  " +      
			"\n 		SELECT 3 AS TUAN " +      
			"\n 		UNION " +      
			"\n 		SELECT 4 AS TUAN " +      
			"\n 		UNION  " +      
			"\n 		SELECT 5 AS TUAN " +      
			"\n 	) as TUAN  " +
			"\n		union " +
			"\n		select '1' as pk_seq,N'Chủ nhật' as diengiai, 'Chu nhat' as ngaylamviec, PK_SEQ,'1' as ngayid, NPP_FK, TUAN from DAIDIENKINHDOANH kd, " +
			"\n	( " +
			"\n	select 1 AS TUAN " +
			"\n	UNION  " +
			"\n	SELECT 2 AS TUAN " +
			"\n	UNION  " +
			"\n	SELECT 3 AS TUAN " +
			"\n	UNION " +
			"\n	SELECT 4 AS TUAN " +
			"\n	UNION  " +
			"\n	SELECT 5 AS TUAN " +
			"\n	) as TUAN where exists(select 1 from TUYENBANHANG t where t.DDKD_FK = kd.PK_SEQ)" +      
			"\n )tbh " +      
			"\n 	 " +    
			"\n left join  " +      
			"\n ( " +      
			"\n 	select SOKH, ngayid1, a.ddkd_fk " +    
			"\n		FROM DDKD_SOKH a left join TUYENBANHANG t on a.TBH_FK = t.PK_SEQ  " +    
			"\n		INNER JOIN ( " +    
			"\n			select  DATEPART(DW, ngay) as ngayid1, max(NGAY) as NGAY" + 
			"\n			FROM DDKD_SOKH a left join TUYENBANHANG t on a.TBH_FK = t.PK_SEQ  " +    
			"\n			WHERE MONTH(NGAY) = "+tungay+" AND YEAR(NGAY) = "+denngay+" " +    
			"\n			AND SOKH IS NOT NULL " +    
			"\n			GROUP BY DATEPART(DW, ngay) " +    
			"\n		) tmp on tmp.NGAY = a.NGAY " +    
			"\n		WHERE MONTH(a.NGAY) = "+tungay+" AND YEAR(a.NGAY) = "+denngay+"" +      
			"\n ) SoKh12 on SoKh12.ngayid1 = tbh.ngayid and tbh.ddkd_fk = Sokh12.ddkd_fk" +    
			"\n left join  " +      
			"\n ( " +      
			"\n 	select SOKH, ngayid1, tmp.tuan, a.ddkd_fk " +    
			"\n		FROM DDKD_SOKH a left join TUYENBANHANG t on a.TBH_FK = t.PK_SEQ  " +    
			"\n		INNER JOIN ( " +    
			"\n			select DATEPART(DW, ngay) as ngayid1, max(NGAY) as NGAY," +
			"\n 	DATEPART(WEEK, NGAY)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,NGAY), 0))+ 1 AS TUAN   " +    
			"\n			FROM DDKD_SOKH a left join TUYENBANHANG t on a.TBH_FK = t.PK_SEQ  " +    
			"\n			WHERE MONTH(NGAY) = "+tungay+" AND YEAR(NGAY) = "+denngay+" " +    
			"\n			AND SOKH IS NOT NULL " +    
			"\n			GROUP BY DATEPART(DW, ngay), DATEPART(WEEK, NGAY)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,NGAY), 0))+ 1  " +    
			"\n		) tmp on tmp.NGAY = a.NGAY " +    
			"\n		WHERE MONTH(a.NGAY) = "+tungay+" AND YEAR(a.NGAY) = "+denngay+"" +      
			"\n ) SoKh1 on SoKh1.ngayid1 = tbh.ngayid and sokh1.TUAN = tbh.TUAN and tbh.ddkd_fk = Sokh1.ddkd_fk" +      
			"\n left join " +      
			"\n ( " +      
			"\n 	select COUNT(a.khachhang_fk) as sokh,a.ddkd_fk, convert(char(5), dateadd(minute,Sum(isnull(a.thoigiantaicuahang,0)),0), 108) as TGKh,  " +      
			"\n 	CONVERT(char(8), DATEADD(SECOND, DATEDIFF(SECOND,Min(a.thoidiem),Max(a.thoidiemdi)),0), 108) as TGLV, DATEPART(DW, thoidiem) as ngayid, " +      
			"\n 	DATEPART(WEEK, thoidiem)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,thoidiem), 0))+ 1 AS TUAN " +      
			"\n 	from ddkd_khachhang a " +      
			"\n 	where MONTH(thoidiem) = '"+tungay+"' and YEAR(thoidiem) = '"+denngay+"' " +        
			"\n 	--and exists (select 1 from khachhang kh where trangthai = 1 and kh.pk_seq = a.khachhang_fk )  " +      
			"\n 	group by DATEPART(DW, thoidiem),a.ddkd_fk ,DATEPART(WEEK, thoidiem)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,thoidiem), 0))+ 1 " +      
			"\n ) VT1 on VT1.ngayId = tbh.ngayId and VT1.TUAN = tbh.TUAN and VT1.ddkd_fk = tbh.ddkd_fk " +      
			"\n left join " +      
			"\n ( " +      
			"\n 	select SUM(dhsp.SOLUONG*dhsp.GIAMUA) as DoanhSo, COUNT(distinct dh.PK_SEQ) as SODH, dh.ddkd_fk,  " +      
			"\n 	COUNT(dhsp.SANPHAM_FK)/(COUNT(distinct dh.PK_SEQ)*1.0) as TBSKU, DATEPART(DW, ngaytao) as ngayid, " +      
			"\n 	DATEPART(WEEK, NGAYTAO)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0, NGAYTAO), 0))+ 1 AS TUAN  " +      
			"\n 	from DONHANG dh inner join DONHANG_SANPHAM dhsp on dhsp.DONHANG_FK = dh.PK_SEQ " +      
			"\n 	where " +  tt +
			"\n 	and MONTH(NGAYTAO) = '"+tungay+"' and YEAR(NGAYTAO) = '"+denngay+"' " +          
			"\n 	group by DATEPART(DW, ngaytao),dh.ddkd_fk, DATEPART(WEEK, NGAYTAO)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0, NGAYTAO), 0))+ 1 " +      
			"\n ) DS1 on DS1.ngayid = tbh.ngayid and DS1.TUAN = tbh.TUAN  and DS1.ddkd_fk = tbh.ddkd_fk " +      
			"\n left join " +      
			"\n ( " +      
			"\n 	select COUNT(b.FILENAME) as DH,b.ddkd_fk, DATEPART(DW, ngay) as ngayid, DATEPART(WEEK, NGAY)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,NGAY), 0))+ 1 AS TUAN " +     
			"\n 	from KHACHHANG_ANHCHUP b " +      
			"\n 	where MONTH(b.NGAY) = '"+tungay+"' and YEAR(b.NGAY) = '"+denngay+"' " +           
			"\n 	group by DATEPART(DW, ngay),b.ddkd_fk, DATEPART(WEEK, NGAY)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,NGAY), 0))+ 1 " +      
			"\n ) AC1 on AC1.ngayid = tbh.ngayid and AC1.TUAN = tbh.TUAN and AC1.ddkd_fk = tbh.ddkd_fk" +      
			"\n left join  " +      
			"\n ( " +      
			"\n 	select COUNT(distinct dh.PK_SEQ) as DHtraituyen, dh.ddkd_fk, DATEPART(DW, ngaytao) as ngayid, DATEPART(WEEK, dh.NGAYTAO)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,dh.NGAYTAO), 0))+ 1 AS TUAN " +      
			"\n 	from DONHANG dh inner join DONHANG_SANPHAM dhsp on dhsp.DONHANG_FK = dh.PK_SEQ  " +      
			"\n 	where " +    tt +  
			"\n 	and dh.isdungtuyen='0'  " +      
			"\n 	and MONTH(dh.NGAYTAO) = '"+tungay+"' and YEAR(dh.NGAYTAO) = '"+denngay+"'  " +            
			"\n 	group by DATEPART(DW, dh.ngaytao),dh.ddkd_fk, DATEPART(WEEK, dh.NGAYTAO)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,dh.NGAYTAO), 0))+ 1 " +      
			"\n ) SoDHTT1 on SoDHTT1.ngayid = tbh.ngayid and SoDHTT1.TUAN = tbh.TUAN and SODHTT1.ddkd_fk = tbh.ddkd_fk " +      
			"\n left join  " +      
			"\n (  " +      
			"\n 	select COUNT(a.khachhang_fk) as VTtraituyen,a.ddkd_fk, DATEPART(DW, thoidiem) as ngayid, DATEPART(WEEK, a.thoidiem)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,a.thoidiem), 0))+ 1 AS TUAN  " +      
			"\n 	from ddkd_khachhang a where MONTH(a.thoidiem) = '"+tungay+"' and YEAR(a.thoidiem) = '"+denngay+"'  " +      
			"\n 	and a.isdungtuyen = '0'  " +           
			"\n 	--and exists (select 1 from khachhang kh where trangthai = 1 and kh.pk_seq = b.khachhang_fk )  " +      
			"\n 	group by DATEPART(DW, thoidiem), a.ddkd_fk,DATEPART(WEEK, a.thoidiem)  - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,a.thoidiem), 0))+ 1 " +      
			"\n ) SoVTTT1 on SoVTTT1.ngayid = tbh.ngayid and SoVTTT1.TUAN = tbh.TUAN and SoVTTT1.ddkd_fk = tbh.ddkd_fk ";
			db.update(query);
			
			query = "\n select V.TEN as Vung,kv.TEN as KHUVUC, ddkd.TEN as NVBH,npp.ten as TENNPP, " +      
			"\n  tbh.NGAYID, isnull(t1.ASO, t1.ASO1) as ASO,t1.SOKHVT,t1.TG,t1.TGLV,t1.DoanhSo,t1.SODH,t1.SoDHTT, t1.SoVTTT,t1.SKU,t1.Anh,  " +      
			"\n  tbh.NGAYID,isnull(t2.ASO, t2.ASO1) as ASO,t2.SOKHVT,t2.TG,t2.TGLV,t2.DoanhSo,t2.SODH,t2.SoDHTT, t2.SoVTTT,t2.SKU,t2.Anh,  " +      
			"\n  tbh.NGAYID,isnull(t3.ASO, t3.ASO1) as ASO,t3.SOKHVT,t3.TG,t3.TGLV,t3.DoanhSo,t3.SODH,t3.SoDHTT, t3.SoVTTT,t3.SKU,t3.Anh,  " +      
			"\n  tbh.NGAYID,isnull(t4.ASO, t4.ASO1) as ASO,t4.SOKHVT,t4.TG,t4.TGLV,t4.DoanhSo,t4.SODH,t4.SoDHTT, t4.SoVTTT,t4.SKU,t4.Anh,  " +      
			"\n  tbh.NGAYID,isnull(t5.ASO, t5.ASO1) as ASO,t5.SOKHVT,t5.TG,t5.TGLV,t5.DoanhSo,t5.SODH,t5.SoDHTT, t5.SoVTTT,t5.SKU,t5.Anh  " +      
			"\n from NHAPHANPHOI npp  " +      
			"\n left join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK " +      
			"\n left join VUNG v on v.PK_SEQ = kv.VUNG_FK" +
			"\n inner join phamvihoatdong pv on pv.npp_fk = npp.pk_seq and nhanvien_fk = "+userid+" " +      
			"\n inner join DAIDIENKINHDOANH ddkd on ddkd.NPP_FK = npp.PK_SEQ " +      
			"\n inner join ( " +
			"\n select PK_SEQ, DDKD_FK, NGAYID from TUYENBANHANG " +
			"\n union " +
			"\n select '1' as pk_seq, dm.pk_seq as ddkd_fk,'1' as ngayid from DAIDIENKINHDOANH dm " +
			"\n ) tbh on tbh.DDKD_FK = ddkd.PK_SEQ " +      
			"\n LEFT join #BC t1 on t1.tbh_fk = tbh.pk_seq AND t1.TUAN = 1 	and ddkd.PK_SEQ = t1.ddkd_fk " +      
			"\n LEFT join #BC t2 on t2.tbh_fk = tbh.pk_seq AND t2.TUAN = 2  and ddkd.PK_SEQ = t2.ddkd_fk" +      
			"\n LEFT join #BC t3 on t3.tbh_fk = tbh.pk_seq AND t3.TUAN = 3  and ddkd.PK_SEQ = t3.ddkd_fk" +      
			"\n LEFT join #BC t4 on t4.tbh_fk = tbh.pk_seq AND t4.TUAN = 4  and ddkd.PK_SEQ = t4.ddkd_fk" +      
			"\n LEFT join #BC t5 on t5.tbh_fk = tbh.pk_seq AND t5.TUAN = 5  and ddkd.PK_SEQ = t5.ddkd_fk" +      
			"\n where (ddkd.TRANGTHAI = 1 or exists (select 1 from DDKD_SOKH d where d.DDKD_FK = ddkd.PK_SEQ and MONTH(d.NGAY) = "+tungay+" AND YEAR(d.NGAY) = "+denngay+")) " +
			"\n and npp.PK_SEQ in ("+nppid+") and npp.trangthai = 1  " +      
			"\n  order by ddkd.PK_SEQ,tbh.NGAYID ";
*/			System.out.println("Get NVBH ko PDA: \n"+query);
			ResultSet rs = db.get(query);			
			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();			
			int countRow = 5;

			/*for( int i =1 ; i <=socottrongSql ; i ++  )
			{
				cell = cells.getCell(countRow,i-1 );cell.setValue(rsmd.getColumnName(i));
				cells.setColumnWidth(i, 20.0f);
				ReportAPI.setCellBackground(cell, new Color(70,130,180), BorderLineType.THIN, true, 0);	
			 
			}*/
			
			int stt = 0;
			while(rs.next())
			{
				stt++;
				for(int i =1;i <=socottrongSql ; i ++)
				{
					cell = cells.getCell(countRow,i-1 );
					if(rsmd.getColumnName(i).equals("STT"))
					{					
						cell.setValue(stt);
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
						//System.out.println("STT: "+stt);

					}else
					if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i) == Types.INTEGER || rsmd.getColumnType(i) == Types.DECIMAL || rsmd.getColumnType(i) == Types.FLOAT || rsmd.getColumnType(i) == Types.NUMERIC )
					{
						cell.setValue(rs.getDouble(i));
						if(!rsmd.getColumnName(i).equals("SKU"))
							ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
						else
							ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					}
					else
					{						
						cell.setValue(rs.getString(i));							
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					}
				}
				++countRow;
			}
			fstream.close();
		/*	
			query = "IF OBJECT_ID('tempdb.dbo.#BC') IS NOT NULL DROP TABLE #BC";
			db.update(query);*/
			
			if(rs!=null)rs.close();
			if(db!=null){
				db.shutDown();
			}	
	
		}catch(Exception ex){
			System.out.println("Errrorr : "+ex.toString());
			ex.printStackTrace();
			throw new Exception("Lỗi không tạo được báo cáo !");
		}
	}
	public String GetQuery(String tungay,String denngay,String nppid, String trangthai, String userid )
	{
		String tt= "1"; // duy noi chinh lai lay da chot thoi
		/*if(trangthai.trim().length()>0)
		{
			if(trangthai.equals("0"))
				tt="0" ;
			else if(trangthai.equals("1"))
				tt="1" ;
			else
				tt="0,1,3";
		}
		else
			tt="0,1,3";*/

		String query = "\n select v.ten vung, kv.ten kv,npp.ten npp, case when isnull(npp.trangthai,0)=0 then N'Không hoạt động' else N'Hoạt động' end HoatDong, " +
		"\n 	isnull(ddkd.MAERP,'') as manverp,ddkd.smartId maddkd,ddkd.ten ddkd,case when ddkd.TRANGTHAI=0 then N'Ngưng hoạt động' else N'Hoạt động' end as trangthaiddkd,ddkdkbh.DIENGIAI,gsbh.ten gsbh, ddkd.trangthai ,CONVERT(VARCHAR(10), cast(mcp.ngay as datetime),105) Ngay, " + 
		"\n 	anpp.vtnpp,anpp.roidinpp,akh.vtkh,akh.roidikh, mcp.SOKH ASO,akh.viengthamkh,akh.traituyen,akh.tgbanhang,akh.TGLV, " + 
		"\n 	dh.DoanhSo,dh.SODH,dh.dhtraituyen,dh.TBSKU,anh.sohinh" + 
		"\n from ddkd_sokh mcp" + 
		"\n inner join DAIDIENKINHDOANH ddkd on ddkd.pk_seq = mcp.ddkd_fk" + 
		"\n LEFT join KENHBANHANG ddkdkbh on ddkdkbh.pk_seq = ddkd.kbh_fk" + 
		"\n inner join NHAPHANPHOI npp on npp.pk_seq =mcp.npp_fk" + 
		"\n inner join giamsatbanhang gsbh on gsbh.pk_seq= mcp.gsbh_fk" + 
		"\n inner join khuvuc kv on kv.pk_Seq = npp.khuvuc_fk" + 
		"\n inner join vung v on v.pk_seq = kv.vung_fk" + 
		"\n outer apply" + 
		"\n (" + 
		"\n 	select  convert( varchar,min(thoidiem),120) vtnpp,convert( varchar,max(isnull(di,di1)),120) roidinpp from ddkd_npp " + 
		"\n 	where ddkd_fk = mcp.ddkd_fk and ddkd_npp.ngay = mcp.NGAY" + 
		"\n )anpp" + 
		"\n outer apply" + 
		"\n (" + 
		"\n 	select  convert( varchar,min(thoidiem),120) vtkh,convert( varchar,max(thoidiemdi),120) roidikh, count(distinct khachhang_fk) viengthamkh" + 
		"\n 		, count ( distinct case isDungTuyen  when 0 then khachhang_fk else null end) traituyen" + 
		"\n 		,convert(char(5), dateadd(minute,Sum(isnull(thoigiantaicuahang,0)),0), 108) tgbanhang" + 
		"\n 		,CONVERT(char(8), DATEADD(SECOND, DATEDIFF(SECOND,Min(thoidiem),Max(thoidiemdi)),0), 108) as TGLV" + 
		"\n 	from ddkd_khachhang " + 
		"\n 	where ddkd_fk = mcp.ddkd_fk and ddkd_khachhang.ngayghinhan = mcp.NGAY" + 
		"\n )akh" + 
		"\n outer apply" + 
		"\n (" + 
		"\n 	select SUM(dhsp.SOLUONG*dhsp.GIAMUA) as DoanhSo, COUNT(distinct dh.PK_SEQ) as SODH" + 
		"\n 		, count ( distinct case isDungTuyen  when 0 then dh.PK_SEQ else null end) dhtraituyen" + 
		"\n 		,dbo.phepchia(COUNT(dhsp.SANPHAM_FK),(COUNT(distinct dh.PK_SEQ)*1.0)) as TBSKU" + 
		"\n 	from donhang dh " + 
		"\n 	inner join donhang_sanpham dhsp on dh.pk_seq = dhsp.donhang_fk" + 
		"\n 	where dh.ddkd_fk = mcp.ddkd_fk and dh.ngaynhap = mcp.ngay and dh.trangthai in ("+tt+")" +
		//"\n			and not exists ( select 1 from donhangtrave where trangthai = 3 and donhang_fk = dh.pk_seq)   " + 
		"\n )dh" + 
		"\n outer apply" +  
		"\n (" + 
		"\n 	select count(b.FILENAME) sohinh" + 
		"\n 	from KHACHHANG_ANHCHUP b " + 
		"\n 	where b.NGAY = mcp.ngay and b.ddkd_fk = mcp.ddkd_fk	" + 
		"\n )anh " + 
		"\n where --datepart(dw,mcp.ngay) !=1 and "  + 
//		"\n 	and month(mcp.ngay) ="+tungay+" and year(mcp.ngay) = "+denngay+" "
		 "\n  mcp.NGAY >= '"+tungay+"' and mcp.NGAY <= '"+denngay+"' and npp.pk_seq in ("+nppid+")" + 
		"\n order by v.ten, kv.ten,npp.ten,ddkd.ten, mcp.ngay";
		return query;
	}
}
