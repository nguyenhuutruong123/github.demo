package geso.dms.center.servlets.reports;

//Lo trinh ban hang

import geso.dms.center.beans.lotrinh.*;
import geso.dms.center.beans.lotrinh.imp.*;
import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.CodingErrorAction;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;

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
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;



public class LoTrinhBanHangReportNew extends HttpServlet
{

	private static final long serialVersionUID = 1L;
	Utility util = new Utility();
	NumberFormat formatter = new DecimalFormat("#,###,###.###");
	public LoTrinhBanHangReportNew()
	{
		super();
	}	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		// PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String userTen = (String) session.getAttribute("userTen");
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		String view = util.antiSQLInspection(request.getParameter("view"));
		session.setAttribute("userId", userId);
		session.setAttribute("tungay", "");
		session.setAttribute("denngay", "");
		session.setAttribute("loi", "");
		session.setAttribute("userTen", userTen);

		ILoTrinh obj = new LoTrinh();
		if (view == null) view = "";
		System.out.println("View: "+view);
		if (view.length() >  0) {
			obj.setView(view);
		}
		obj.setUserId(userId);
		obj.setStatus("1");
		obj.init();

		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Center/LoTrinhBanHangReportNew.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		// PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		ILoTrinh obj = new LoTrinh();
		
		String nnId = (String)session.getAttribute("nnId");

		String nppId = util.antiSQLInspection(request.getParameter("nppId"));

		String kenhId = util.antiSQLInspection(request.getParameter("kenhId"));
		if (kenhId == null)
			kenhId = "";
		obj.setKenhId(kenhId);

		if (nppId == null)
			nppId = "";
		obj.setnppId(nppId);

		String ddkdId = util.antiSQLInspection(request.getParameter("ddkdId"));
		if (ddkdId == null)
			ddkdId = "";
		obj.setddkdId(ddkdId);

		String tuyenId = util.antiSQLInspection(request.getParameter("tuyenId"));
		if (tuyenId == null)
			tuyenId = "";
		obj.settuyenId(tuyenId);
		
		 String userId = util.antiSQLInspection(request.getParameter("userId")==null?"":request.getParameter("userId"));    
		 obj.setUserId(userId);
		
		String tungay = request.getParameter("tungay");
  	if(tungay == null) tungay = ""; else tungay = tungay.trim();
  	obj.setTungay(tungay);
  	
  	String denngay = request.getParameter("denngay");
  	if(denngay == null || denngay.trim().length() <= 0) 
  		denngay = getDateTime();
  	else 
  		denngay = denngay.trim();
  	obj.setDenngay(denngay);

  	String khuvucId = util.antiSQLInspection(request.getParameter("khuvucId"));
  	
		if (khuvucId == null)
			khuvucId = "";
		obj.setkhuvucId(khuvucId);
		
		
		String vungId = util.antiSQLInspection(request.getParameter("vungId"));
  	
		if (vungId == null)
			vungId = "";
		obj.setvungId(vungId);
		
		
		/*String tinhthanhId = util.antiSQLInspection(request.getParameter("tinhthanhId"));
  	
		if (tinhthanhId == null)
			tinhthanhId = "";
		System.out.println("Tinh thanh "+tinhthanhId);
		obj.setTinhthanhId(tinhthanhId);*/
		
		String view = util.antiSQLInspection(request.getParameter("view"));
		if (view == null)
			view = "";
		obj.setView(view);
		
		session.setAttribute("loi", "");
		
		obj.init();
		String action = request.getParameter("action");

		if (action.equals("exportmcp"))
		{
			System.out.println("XuatMcp__");
			if(tungay.trim().length() <= 0 || denngay.trim().length() <= 0 )
			{
				if(tungay.trim().length() <= 0)
					session.setAttribute("loi", "Bạn phải chọn ngày bắt đầu xem báo cáo"); 
				else
					session.setAttribute("loi", "Bạn phải chọn ngày kết thúc xem báo cáo");
			
				session.setAttribute("obj", obj);
				String nextJSP = "/AHF/pages/Center/LoTrinhBanHangReportNew.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
			//	XuatFileExcelSR(response, obj, nnId);
			}
		}  
		else
		{
			if(action.equals("chitiet"))
			{
				System.out.println("XuatDetail__");
				if(tungay.trim().length() <= 0 || denngay.trim().length() <= 0 )
				{
					if(tungay.trim().length() <= 0)
						session.setAttribute("loi", "Bạn phải chọn ngày bắt đầu xem báo cáo"); 
					else
						session.setAttribute("loi", "Bạn phải chọn ngày kết thúc xem báo cáo");
					session.setAttribute("obj", obj);
					String nextJSP = "/AHF/pages/Center/LoTrinhBanHangReportNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					/*System.out.println("VO Xuat FILE Detail__");
					XuatFileExcelChiTiet(response, obj, nnId);*/
					
					try
					{
						response.setContentType("application/xlsm");
						System.out.println(" my contentType 2 :" +  response.getContentType());
						/*response.setHeader("Content-Disposition", "attachment; filename=BCLoTrinhBanHangChiTiet"+util.setTieuDe(obj)+".xlsm");*/
						response.setHeader("Content-Disposition", "attachment; filename=BCLoTrinhBanHangChiTiet.xlsm");
						OutputStream out = response.getOutputStream();
						String query = setQuery(obj);
						CreatePivotTable(out, obj,query);
						obj.DBclose();
						return;
					}
					catch(Exception e)
					{
						response.setContentType("text/html; charset=UTF-8");
						e.printStackTrace();
						session.setAttribute("loi", "Không có báo cáo theo điều kiện("+e.getMessage()+")");
						session.setAttribute("obj", obj);
						String nextJSP = "/AHF/pages/Center/LoTrinhBanHangReportNew.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
				String status = util.antiSQLInspection(request.getParameter("status"));
				obj.setStatus(status);
			//	obj.createNPP();
				session.setAttribute("obj", obj);
				String nextJSP = "/AHF/pages/Center/LoTrinhBanHangReportNew.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}
	
	public String setQuery(ILoTrinh lotrinh)
	{	
		String tuyen =   "\n 	select distinct ddkd_sokh.DDKD_FK,Ngay,NgayId,SoKH,khachhang_fks ,Allkhachhang_fks  " + 
						 "\n 	from ddkd_sokh     " + 
						 "\n 	inner join daidienkinhdoanh ddkd on ddkd_sokh.DDKD_FK = ddkd.pk_seq   " + 
						 "\n 	WHERE ddkd_sokh.NGAY >='"+lotrinh.getTungay()+"' AND  ddkd_sokh.NGAY <='"+lotrinh.getDenngay()+"'    " + 
						 "\n 	and ddkd_sokh.ngayId !=0  " ;
		
		if(lotrinh.getnppId().trim().length() > 0)
		{
			tuyen += "\n and exists ( select  1 from daidienkinhdoanh x where npp_fk = '" + lotrinh.getnppId() + "' and x.pk_seq = ddkd_sokh.DDKD_FK )  ";
		}
		else if(lotrinh.getddkdId().trim().length() > 0)
		{
			tuyen += "\n and ddkd_sokh.DDKD_FK = '" + lotrinh.getddkdId() + "' ";
		}
		else 
		{
			/*if(lotrinh.getddkdId().trim().length() > 0|| lotrinh.getTinhthanhId().trim().length() > 0 )
			{
				if(lotrinh.getnppId().trim().length() > 0)
					tuyen += "\n and exists ( select  1 from daidienkinhdoanh_npp x where npp_fk = '" + lotrinh.getnppId() + "' and x.ddkd_fk = ddkd_sokh.DDKD_FK )  ";
					if(lotrinh.getTinhthanhId().trim().length() > 0)
					tuyen += "\n and ddkd.diaban_fk in ("+lotrinh.getTinhthanhId()+") ";
			}
			else*/
				if(lotrinh.getkhuvucId().trim().length() > 0)
					tuyen += "\n and ddkd.diaban_fk in (select pk_seq from diaban where khuvuc_fk = "+lotrinh.getkhuvucId()+") ";					
				else if(lotrinh.getvungId().length() > 0)
					tuyen += "\n and ddkd.diaban_fk in (select pk_seq from diaban " +
							 "\n 						where khuvuc_fk in ( select khuvuc_fk from khuvuc where vung_fk = "+lotrinh.getvungId()+") ) ";
		}
		
		if (lotrinh.getddkdId() != null && lotrinh.getddkdId().length() > 0) {
			tuyen += "\n and ddkd.pk_seq = "+lotrinh.getddkdId();
		}
		
		geso.dms.center.util.Utility util2 = new geso.dms.center.util.Utility();
		if (!lotrinh.getView().equals("NPP"))
		{
			tuyen += "\n and ddkd_sokh.DDKD_FK in ("+util2.Quyen_Ddkd(lotrinh.getUserId())+" ) " ;
		}
		
		String query = "\n  with ddkdsokh as (  " + tuyen + " ) " + 
					 "\n ,info" + 
					 "\n as " + 
					 "\n (" + 
					 "\n 	select info.ghichu,info.ddkd_fk,khachhang_fk,thoidiem,thoidiemdi,lat,long,ngayghinhan, info.isDungTuyen,	info.TanSo,info.NgayId     " + 
					 "\n 	from ddkd_khachhang info inner join ddkdsokh on info.ddkd_fk = ddkdsokh.DDKD_FK and ddkdsokh.NGAY = info.ngayghinhan" + 
					 "\n )" + 
					 "\n   ,vt as ( " + 
					 "\n    		select a.khachhang_fk, a.ddkd_fk ,a.ngayghinhan ngaythuchien " + 
					 "\n  		from info a" + 
					 "\n  		group by a.khachhang_fk, a.ddkd_fk ,a.ngayghinhan " + 
					 "\n    )  " + 
					 "\n    , dh as( " + 
					 "\n    	select dh.KHACHHANG_FK,dh.PK_SEQ,dh.NPP_FK,dh.DDKD_FK,dh.NGAYNHAP  " + 
					 "\n    	from donhang dh " + 
					 "\n    	inner join  ddkdsokh on dh.DDKD_FK = ddkdsokh.DDKD_FK and dh.NGAYNHAP = ddkdsokh.NGAY " + 
					 "\n    	where dh.TRANGTHAI != 2 and dh.IsPDA = 1  " + 
					 "\n    ) " + 
					 "\n    , khngay as " + 
					 "\n    ( " + 
					 "\n 		select  NGAY ,KhachHang_fk, DDKD_FK, max(dungtuyen) dungtuyen" + 
					 "\n 		from " + 
					 "\n 		(" + 
					 "\n    			select  ddkdsokh.NGAY ,x.KhachHang_fk, x.DDKD_FK,1 dungtuyen" + 
					 "\n    			from DDKD_Ngay_KH_Log x" + 
					 "\n 			inner join ddkdsokh on x.DDKD_FK = ddkdsokh.DDKD_FK and ddkdsokh.NGAY = x.Ngay " + 
					 "\n    			union " + 
					 "\n    			select  x.NGAYNHAP, x.khachhang_fk, x.DDKD_FK,0  " + 
					 "\n    			from  donhang x " + 
					 "\n 			inner join ddkdsokh on x.DDKD_FK = ddkdsokh.DDKD_FK and ddkdsokh.NGAY = x.NGAYNHAP " + 
					 "\n    			union " + 
					 "\n    			select  x.ngaythuchien, x.khachhang_fk, x.DDKD_FK,0" + 
					 "\n    			from  vt x" + 
					 "\n 			inner join ddkdsokh on x.DDKD_FK = ddkdsokh.DDKD_FK and ddkdsokh.NGAY = x.ngaythuchien " + 
					 "\n 		)a" + 
					 "\n 		 group by  NGAY ,KhachHang_fk, DDKD_FK" + 
					 "\n    )     " + 
					 "\n " + 
					 "\n    select	ROW_NUMBER() OVER (PARTITION BY MANHANVIEN,NGAY ORDER BY  MANHANVIEN,NGAY, case when NPP is null then 0 else 1 end,isnull(TGDenBatDau,'3000')  )-1 STT     " + 
					 "\n    	,kbh.ten as kenh,c.ten as asm,b.ten gsbh,V.TEN AS VUNG,kv.ten as khuvuc,tt.ten as tinhthanh,qh.ten as quanhuyen,kq.*   " + 
					 "\n    from    " + 
					 "\n    (   " + 
					 "\n      select distinct null NPP,ddkd.SMARTID MANHANVIEN, ddkd.TEN ddkdTen,'' MaKH,''LoaiKH,'' KhachHang,''DiaChi   " + 
					 "\n    			, N'Thứ ' + cast( ISNULL(CODI.NGAYID,0) as varchar) as NGAYID,  CODI.NGAY   " + 
					 "\n    			,cast ( isnull( CODI.sokh,0) as varchar) PHAIVT, cast ( isnull(  vtham.SOVT ,0) as varchar) DaVT       " + 
					 "\n      			,isnull(doanhso.SODH,0) as SODH,isnull(doanhso.DHCOVT,0)as DHCOVT   " + 
					 "\n      			,isnull(doanhso.SODH,0) - isnull(doanhso.DHCOVT,0) DHKOVT       " + 
					 "\n      			, convert ( varchar ,vtham.TGDenBatDau,120)TGDenBatDau  ,convert ( varchar ,TGCuoiCung,120) TGDi " + 
					 "\n   			, cast ( DATEDIFF(MINUTE, vtham.TGDenBatDau,vtham.TGCuoiCung)  - isnull(vtham.tongdichuyen_phut,0) as varchar) TgDiChuyen	 " + 
					 "\n   			,cast ( DATEDIFF(MINUTE, vtham.TGDenBatDau,vtham.TGCuoiCung) as varchar) TongTg   " + 
					 "\n      			, isnull(doanhso.doanhso,0) as DOANHTHU,'' SKU	, '' GhiChuViengTham	     " + 
					 "\n       from  ddkdsokh CODI " + 
					 "\n   	 inner join   daidienkinhdoanh ddkd on CODI.DDKD_FK = ddkd.PK_SEQ      " + 
					 "\n       outer apply " + 
					 "\n       (     " + 
					 "\n     		select SUM(soluong*giamua) as doanhso ,COUNT(distinct dh.PK_SEQ) SODH     " + 
					 "\n     			, COUNT ( distinct case when vt.khachhang_fk is not null then dh.pk_seq else null end )  DHCOVT     " + 
					 "\n     		from dh      " + 
					 "\n     		inner join DONHANG_SANPHAM dh_sp	 on dh.PK_SEQ = dh_sp.DONHANG_FK      " + 
					 "\n   		left join vt on vt.khachhang_fk =dh.KHACHHANG_FK and dh.ddkd_fk = vt.ddkd_fk  and dh.NGAYNHAP  = vt.ngaythuchien     " + 
					 "\n   		where  CODI.ddkd_fk = dh.DDKD_FK and CODI.ngay = dh.NGAYNHAP        " + 
					 "\n       )DOANHSO      " + 
					 "\n   	outer apply    " + 
					 "\n       (       " + 
					 "\n      		SELECT  COUNT(DISTINCT DDKH.KHACHHANG_FK) AS SOVT  " + 
					 "\n      		,min(DDKH.thoidiem) TGDenBatDau, case when max(DDKH.thoidiemdi) > max(DDKH.thoidiem) then max(DDKH.thoidiemdi) else max(DDKH.thoidiem) end TGCuoiCung " + 
					 "\n   		, sum(   DATEDIFF(MINUTE, DDKH.thoidiem, DDKH.thoidiemdi) ) tongdichuyen_phut " + 
					 "\n   		, sum(   DATEDIFF(hour, DDKH.thoidiem, DDKH.thoidiemdi) ) tongdichuyen_gio      " + 
					 "\n      		FROM info DDKH      " + 
					 "\n      		where  DDKH.ddkd_fk = CODI.ddkd_fk and DDKH.ngayghinhan = CODI.NGAY           " + 
					 "\n       ) vtham   " + 
					 "\n       where CODI.sokh is not null    " + 
					 "\n       union all         " + 
					 "\n       select     " + 
					 "\n  		" + 
					 "\n  		npp.ten NPP,tdv.SMARTID MaTDV,tdv.ten TenTDV,kh.SMARTID MaKH, lch.diengiai LoaiKH,kh.ten KhachHang   " + 
					 "\n    		,kh.DiaChi,'' ngayId,khngay.ngay	,'' phaivt " + 
					 "\n   		,	isnull (DDKD.VTNT, case when khngay.dungtuyen >= 1 then N'Không VT' else '' end ) davt   " + 
					 "\n    		,ISNULL(DonHang.SODH,0) as SODH   ,isnull(DonHang.DHCOVT,0) as DHCOVT     " + 
					 "\n    		, ISNULL(DonHang.SODH,0)  - isnull(DonHang.DHCOVT,0) DHKOVT     " + 
					 "\n    		,convert ( varchar ,DDKD.ThoiDiemDen,120),convert ( varchar ,DDKD.ThoiDiemDi,120)   " + 
					 "\n    		,'' TGDiChuyen   " + 
					 "\n     		, cast ( isnull(DATEDIFF(MINUTE, ThoiDiemDen, ThoiDiemDi), 0) as varchar)  as TongTG     " + 
					 "\n     		,isnull(DonHang.DoanhSo,0) DS, cast ( isnull(DonHang.SKU,0) as varchar) SKU ,  DDKD.ghichu   " + 
					 "\n     	from  khngay    " + 
					 "\n     	inner join daidienkinhdoanh tdv on tdv.pk_Seq = khngay.ddkd_fk   " + 
					 "\n 	  inner join KHACHHANG kh on kh.PK_SEQ = khngay.KhachHang_fk" + 
					 "\n    	left join loaicuahang lch on lch.pk_seq = kh.lch_fk   " + 
					 "\n     	inner join nhaphanphoi npp on npp.pk_seq = kh.npp_fk   " + 
					 "\n     	outer apply        " + 
					 "\n     	(        " + 
					 "\n     		select	 min(a.thoidiem) as ThoiDiemDen,        " + 
					 "\n     			 min(a.thoidiemdi) as ThoiDiemDi	, max(a.ghichu)	ghichu			" + 
					 "\n    			 ,case when	a.isDungTuyen = 0  then N'Ngoài Tuyến'  else 	N'Đúng tuyến' end as VTNT ,	a.TanSo,a.NgayId     " + 
					 "\n     		from info a    " + 
					 "\n     		where  khngay.ddkd_fk = a.DDKD_FK and kh.pk_seq = a.KHACHHANG_FK and khngay.ngay = a.ngayghinhan    " + 
					 "\n     		group by  a.isDungTuyen,	a.TanSo,a.NgayId     " + 
					 "\n     	)  	DDKD  " + 
					 "\n     	outer apply     " + 
					 "\n     	(      " + 
					 "\n    		select COUNT(distinct dh.PK_SEQ)SODH      " + 
					 "\n    			,	COUNT ( distinct case when vt.khachhang_fk is not null then dh.pk_seq else null end)  DHCOVT     " + 
					 "\n     			,SUM(SOLUONG *GIAMUA)DoanhSo,COUNT( distinct dh_sp.sanpham_fk) as SKU     " + 
					 "\n     			     " + 
					 "\n     		from  dh     " + 
					 "\n     		inner join DONHANG_SANPHAM dh_sp on dh.PK_SEQ = dh_sp.DONHANG_FK    " + 
					 "\n     		left join   vt on vt.khachhang_fk = dh.KHACHHANG_FK and dh.ddkd_fk = vt.ddkd_fk and dh.NGAYNHAP  = vt.ngaythuchien     " + 
					 "\n  		where kh.pk_seq = dh.KHACHHANG_FK  and  dh.ddkd_fk =  khngay.DDKD_FK and khngay.ngay   = dh.ngaynhap   " + 
					 "\n     	)  DonHang  " + 
					 "\n     )kq  " + 
					 "\n 	left join DAIDIENKINHDOANH a1 on kq.MANHANVIEN=a1.SMARTID	  "+
					 "\n 	left join ddkd_gsbh a on a.DDKD_FK=a1.pk_seq				  "+
					 "\n 	left join GIAMSATBANHANG b on b.pk_seq=a.GSBH_FK			  "+
					 "\n 	left join ASM C on C.pk_seq=B.asm_fk						  "+
					 "\n 	left join KHACHHANG KH on KH.SMARTID=kq.MAKH				  "+
					 "\n 	left join nhaphanphoi npp on npp.pk_seq=kh.npp_fk			  "+
					 "\n 	left join KENHBANHANG kbh on kbh.pk_seq=KH.KBH_FK			  "+
					 "\n 	left join QUANHUYEN QH on QH.pk_seq=KH.QUANHUYEN_FK			  "+
					 "\n 	left join TINHTHANH TT on TT.pk_seq=KH.TINHTHANH_FK			  "+
					 "\n 	 left join khuvuc kv on kv.pk_seq = npp.khuvuc_fk			  "+
					 "\n 	 left join vung v on v.pk_seq = kv.vung_fk					  "+
					 "\n    where 1=1  " + 
					 "\n     order by manhanvien,ngay ";
					
			System.out.println("query = "+query);
			return query;
	}
	
	
	private void CreatePivotTable(OutputStream out,ILoTrinh obj,String query) throws Exception 
	{
		try 
		{
		
			Workbook workbook = new Workbook();
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			CreateHeader(workbook,obj); 
			FillData(workbook, obj,query);			
			workbook.save(out);
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}
	

	private void CreateHeader(Workbook workbook,ILoTrinh obj) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);	    
	    worksheet.setName("Sheet1");
	    Cells cells = worksheet.getCells();	 
	    
	    cells.setRowHeight(0, 20.0f);	   
	    Cell cell = cells.getCell("A1");	
	    ReportAPI.getCellStyle(cell,Color.NAVY,true,10,"BÁO CÁO LỘ TRÌNH BÁN HÀNG CHI TIẾT"  );
	    cell = cells.getCell("A3");
	    ReportAPI.getCellStyle(cell,Color.NAVY,true,10,"Từ ngày  : " + obj.getTungay() + "  đến ngày : " + obj.getDenngay());
	    cell = cells.getCell("A4");
	    ReportAPI.getCellStyle(cell,Color.NAVY,true,10,"Ngày tạo : " + this.getDateTime()); 
	    
	    
	    int cot = 0;
	    cell = cells.getCell(4,cot++);ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, true, 0); cell.setValue("STT");
	    cell = cells.getCell(4,cot++);ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, true, 0); cell.setValue("KÊNH");
	    cell = cells.getCell(4,cot++);ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, true, 0); cell.setValue("TÊN ASM");
	    cell = cells.getCell(4,cot++);ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, true, 0); cell.setValue("TÊN GIÁM SÁT BÁN HÀNG");
	    cell = cells.getCell(4,cot++);ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, true, 0); cell.setValue("VÙNG");
	    cell = cells.getCell(4,cot++);ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, true, 0); cell.setValue("KHU VỰC");
	    cell = cells.getCell(4,cot++);ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, true, 0); cell.setValue("TỈNH THÀNH");
	    cell = cells.getCell(4,cot++);ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, true, 0); cell.setValue("QUẬN HUYỆN");
	    cell = cells.getCell(4,cot++);ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, true, 0); cell.setValue("CHI NHÁNH / NPP");
	    cell = cells.getCell(4,cot++);ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, true, 0); cell.setValue("MÃ NVBH");
	    cell = cells.getCell(4,cot++);ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, true, 0); cell.setValue("TDV");
	    cell = cells.getCell(4,cot++);ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, true, 0); cell.setValue("MÃ KH");
	    cell = cells.getCell(4,cot++);ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, true, 0); cell.setValue("LOẠI KH");
	    cell = cells.getCell(4,cot++);ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, true, 0); cell.setValue("KHÁCH HÀNG");
	    cell = cells.getCell(4,cot++);ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, true, 0); cell.setValue("ĐỊA CHỈ");
	    
	    cell = cells.getCell(4,cot++);ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, true, 0); cell.setValue("TUYẾN BH");
	    cell = cells.getCell(4,cot++);ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, true, 0); cell.setValue("NGÀY");
	    cell = cells.getCell(4,cot++);ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, true, 0); cell.setValue("PHẢI VT");
	    cell = cells.getCell(4,cot++);ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, true, 0); cell.setValue("ĐÃ VT");
	    cell = cells.getCell(4,cot++);ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, true, 0); cell.setValue("SỐ ĐH");
	    cell = cells.getCell(4,cot++);ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, true, 0); cell.setValue("ĐH CÓ VT");
	    cell = cells.getCell(4,cot++);ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, true, 0); cell.setValue("ĐH KHÔNG VT"); 
	    cell = cells.getCell(4,cot++);ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, true, 0); cell.setValue("TG ĐẾN");
	    cell = cells.getCell(4,cot++);ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, true, 0); cell.setValue("TG ĐI");
	    cell = cells.getCell(4,cot++);ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, true, 0); cell.setValue("TG DI CHUYỂN");
	    cell = cells.getCell(4,cot++);ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, true, 0); cell.setValue("TỔNG TG");
	    cell = cells.getCell(4,cot++);ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, true, 0); cell.setValue("DOANH THU");
	    cell = cells.getCell(4,cot++);ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, true, 0); cell.setValue("SKU");
	    cell = cells.getCell(4,cot++);ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, true, 0); cell.setValue("GHI CHÚ VIẾNG THĂM");
  
	}
	
	public boolean isDouble(String s)
	{
		try
		{
			double a= Double.parseDouble(s);
			
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	private void FillData(Workbook workbook, ILoTrinh obj,String query) throws Exception
	{
		//System.out.println("cau qery" + query);
		
		ResultSet rs = null;
		try
		{	
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			Cells cells = worksheet.getCells();
				
			
			int index = 5;
			int column = 0;
		    Cell cell = null;
		    boolean coData= false;
		    
		    rs = obj.getDb().get(query);
		    
		    ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();
		    
		    while(rs.next())
			{
		    	coData = true;
				Color c = Color.WHITE;
				if(rs.getInt("STT") <=0)
					c = Color.SILVER;
				for(int i =1;i <=socottrongSql ; i ++)
				{
					
					cell = cells.getCell(index,column + i-1 );
						
					if( i !=1 && isDouble(rs.getString(i)) && ! rsmd.getColumnName(i).toUpperCase().contains("MA") )
					{
						int format = 43;
							if(rsmd.getColumnName(i).contains("Tỷ lệ"))	
								format = 10;
						cell.setValue(rs.getDouble(i));
						ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, false, format);
					}
					else
					{
						if(i != 1)
							cell.setValue(rs.getString(i));
						ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, false, 0);
					}
				}
				
				++index;
			}

			if(rs!=null){
				rs.close();
			}	

			if(!coData)
				throw new Exception("Không có dữ liệu theo điều kiện lọc");
		    			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			if(rs != null)
			{
				rs.close();
			}
			ex.printStackTrace();
			
			throw new Exception(ex.getMessage());
		}
	}
	

	public double getKhoangCachHaversine(double lat1, double long1, double lat2, double long2)
  {
      double R = 6371.00;
      double dLat, dLon, a, c;

      dLat = toRad(lat2 - lat1);
      dLon = toRad(long2 - long1);
      lat1 = toRad(lat1);
      lat2 = toRad(lat2);
      a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
          Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
      c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

      return Math.abs(R * c * 1000  ); //m
  }
	
	private double toRad(double value)
  {
      return value * Math.PI / 180;
  }
	
	private String getDateTime() 
	{
      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      Date date = new Date();
      return dateFormat.format(date);	
	}
	
	private String getMonth() 
	{
      DateFormat dateFormat = new SimpleDateFormat("MM");
      Date date = new Date();
      return dateFormat.format(date);	
	}
	
	private String getYear() 
	{
      DateFormat dateFormat = new SimpleDateFormat("yyyy");
      Date date = new Date();
      return dateFormat.format(date);	
	}
	

	public static void main(String[] args)
	{
		/*Calendar tu_ngay = new GregorianCalendar(2013, 1, 28);		
		
		tu_ngay.add(Calendar.DATE, 1);
		System.out.println("COMPARE: " + tu_ngay.get(Calendar.YEAR));
		System.out.println("COMPARE: " + tu_ngay.get(Calendar.MONTH));
		System.out.println("COMPARE: " + tu_ngay.get(Calendar.DAY_OF_MONTH));*/	
	}
	
	
	
}

