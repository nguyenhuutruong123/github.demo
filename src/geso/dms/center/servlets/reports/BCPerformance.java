package geso.dms.center.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;
import geso.dms.center.util.WebService;
import geso.dms.distributor.beans.report.Ireport;
import geso.dms.distributor.beans.report.imp.Report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import com.aspose.cells.Column;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class BCPerformance extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	private String setQueryTheoThang(IStockintransit obj, String view) 
	{
		Utility util = new Utility();

		
		String query =  "\n with mcp as " + 
						"\n ( " + 
						"\n  " + 
						"\n 	select DDKD_FK,Ngay,NgayId,SOKH,ALLSOKH,route_fk,npp_fk,gsbh_fk  " + 
						"\n 	from ddkd_sokh where ngay = '"+obj.getdenngay()+"' " + 
						"\n ) " + 
						"\n , info as " + 
						"\n ( " + 
						"\n 	select dbo.SoNgayLamViecTrongThang( '"+obj.getdenngay()+"',1) SoNgayLamViecQuyDinh,   dbo.SoNgayLamViecTrongThang( '"+obj.getdenngay()+"',0) SoNgayLamViecDaQua " + 
						"\n ) " + 
						"\n  " + 
						"\n select  v.Ten [VÙNG], kv.ten KV, isnull(tt.ten,'NA') +'/' + isnull(qh.ten,'NA') [TỈNH/QUẬN]   " + 
						"\n 	, npp.manpp [MÃ NPP], npp.ten [TÊN NPP] ,kbh.ten [Kênh] " +
						"\n     , case when npp.trangthai = 1 then N'Hoạt động' else N'Ngưng hoạt động' end [Trạng thái NPP] " +
						"\n     , rou.ten [TUYẾN ĐƯỜNG], ddkd.smartId [MÃ NVBH], ddkd.ten [TÊN NVBH] " + 
						"\n     , case when ddkd.trangthai = 1 then N'Hoạt động' else N'Ngưng hoạt động' end [Trạng thái DDKD] " +
						"\n     , ddkd.ngaybatdau [Ngày vào làm] " +
						"\n 	,ddkd.NGAYTAO [Ngày khởi tạo hệ thống] "+
						"\n 	,case when ddkd.TRANGTHAI != '1' then ddkd.NGAYSUA else '' end as [Ngày kết thúc hệ thống] "+
						"\n 	, info.SoNgayLamViecQuyDinh [SỐ NGÀY LÀM VIỆC QUY ĐỊNH],info.SoNgayLamViecDaQua  [SỐ NGÀY LÀM VIỆC ĐÃ QUA], cc.cong [SỐ NGÀY LÀM VIỆC THỰC TẾ]  " + 
						"\n 	, isnull(ctthang.chitieu,0) [CHỈ TIÊU DS], ThucDat.thucdatTruocVat [THỰC ĐẠT DS],  dbo.PhepChia (ThucDat.thucdatTruocVat,isnull(ctthang.chitieu,0) ) [% THỰC HIỆN] " + 
						"\n 	, mcp.ALLSOKH ASO, isnull(toado.SOKHTOADO,0) [AOS có định vị], thucdat.AO , dbo.PhepChia (thucdat.AO , mcp.ALLSOKH )[Penetration(%)] " + 
						"\n 	, pvt.phaivt [PHẢI VT], vt.davt [ĐÃ VT], akh.traituyen [Đã viếng thăm trái tuyến], dvt.num [Số KH đã VT] " +
						"\n     , dbo.PhepChia ( vt.davt ,pvt.phaivt) [%VT], ThucDat.SoDonHang [SỐ ĐH], ThucDat.SoDonHangDaChot [SỐ ĐH ĐÃ CHỐT], dbo.PhepChia (thucdat.SoDonHang ,  vt.davt ) [% BÁN HÀNG THÀNH CÔNG THỰC TẾ] " + 
						"\n 	, dbo.PhepChia (thucdat.SoDonHang ,pvt.phaivt ) [% BÁN HÀNG THÀNH CÔNG KẾ HOẠCH] " + 
						"\n 	, dbo.PhepChia (thucdat.SoDonHang , info.SoNgayLamViecDaQua ) [ĐH BÌNH QUÂN NGÀY] " + 
						"\n 	, ThucDat.sku, dbo.PhepChia (ThucDat.sku,thucdat.SoDonHang ) [SKU/ĐH], dbo.PhepChia (ThucDat.thucdatTruocVat,thucdat.SoDonHang ) DropSize " + 
						"\n 	, dbo.PhepChia (ThucDat.thucdatTruocVat,thucdat.AO ) VPO " + 
						"\n 	, dbo.PhepChia (ThucDat.thucdatTruocVat,info.SoNgayLamViecDaQua ) [DS BÌNH QUÂN NGÀY] " + 
						"\n 	, dbo.PhepChia (ThucDat.thucdatTruocVat,info.SoNgayLamViecQuyDinh ) EST " + 
						"\n 	, dbo.PhepChia ( dbo.PhepChia (ThucDat.thucdatTruocVat,info.SoNgayLamViecQuyDinh ) , 0 ) [% EST] " +						
						"\n from mcp " + 
						"\n inner join info on 1=1 " + 
						"\n inner join DaiDienkinhdoanh ddkd on ddkd.pk_seq=  mcp.ddkd_fk " + 
						"\n left join kenhbanhang kbh on kbh.pk_seq = ddkd.kbh_fk  " + 
						"\n left join DMS_ROUTE rou on rou.pk_seq = mcp.route_fk " + 
						"\n inner join Nhaphanphoi npp on npp.pk_seq = ddkd.npp_fk " + 
						"\n inner join KhuVuc kv on kv.pk_seq = npp.khuvuc_fk " + 
						"\n inner join vung v on v.pk_seq = kv.vung_fk " + 
						"\n left join tinhthanh tt on tt.pk_seq = npp.tinhthanh_fk " +  
						"\n left join quanhuyen qh on qh.pk_seq = npp.quanhuyen_fk " + 
						"\n outer apply " + 
						"\n ( " + 
						"\n 	select sum(isnull(chitieu,0) ) chitieu " +
						"\n		from ChiTieuNhanVien_DDKD nv 	" +
						"\n		inner join chitieunhanvien ct on ct.THANG =  month(mcp.NGAY) and ct.NAM =  year(mcp.NGAY) and TRANGTHAI = 1 "
						+ "\n and ct.pk_seq=nv.CTNV_FK " +
						"\n		where nv.tieuchi = 1 and nv.NhanVien_FK = ddkd.pk_seq  "+
						"\n )ctthang " + 
					
						"\n outer apply " + 
						"\n ( " + 
						"\n 	select count(distinct case when dh.trangthai = 1 then khachhang_fk else null end ) AO" +
						"\n			, count(distinct case when dh.trangthai = 1 then pk_SEQ else null end  ) SoDonHangDaChot, count(distinct pk_SEQ  ) SoDonHang" +
						"\n			, count ( case when dh.trangthai = 1 then pk_SEQ else null end ) sku " + 
						"\n 		, sum( case when dh.trangthai = 1 then  soluong * giamua else 0 end) thucdatTruocVat  " + 
						"\n 	from donhang dh inner join donhang_sanpham dhsp on dh.pk_seq = dhsp.DONHANG_FK " + 
						"\n 	where dh.ddkd_fk = ddkd.pk_seq and trangthai in(0,1) and month(dh.ngaynhap) = month(mcp.NGAY)  and year(dh.ngaynhap) = year(mcp.NGAY) and dh.ngaynhap <= mcp.ngay" +
						"\n				and not exists ( select 1 from donhangtrave where trangthai = 3 and donhang_fk = dh.pk_seq)  and dh.TONGGIATRI>0 " + 
						"\n )ThucDat " + 
						"\n outer apply " + 
						"\n ( " + 
						"\n 	select sum(SOKH ) phaivt " + 
						"\n 	from ddkd_sokh dh  " + 
						"\n 	where  dh.ddkd_fk =  ddkd.pk_seq and month(dh.NGAY) = month(mcp.NGAY)  and year(dh.NGAY) = year(mcp.NGAY) and dh.NGAY <= mcp.ngay " + 
						"\n )pvt " + 
						"\n outer apply " + 
						"\n ( " + 
						"\n 	select sum(data)davt " + 
						"\n 	from " + 
						"\n 	( " + 
						"\n 		select dh.ngayghinhan,count(distinct khachhang_fk ) data " + 
						"\n 		from ddkd_khachhang dh  " + 
						"\n 		where  dh.ddkd_fk =  ddkd.pk_seq and month(dh.ngayghinhan) = month(mcp.NGAY)  and year(dh.ngayghinhan) = year(mcp.NGAY) and dh.ngayghinhan <= mcp.ngay " + 
						"\n 		group by dh.ngayghinhan " + 
						"\n 	) x " + 
						"\n )vt " +
						"\n outer apply " + 
						"\n ( " + 
						"\n 	select sum(cong ) cong " + 
						"\n 	from DDKD_Ngay_Cong dh  " + 
						"\n 	where  dh.ddkd_fk =  ddkd.pk_seq and month(dh.NGAY) = month(mcp.NGAY)  and year(dh.NGAY) = year(mcp.NGAY) and dh.NGAY <= mcp.ngay " + 
						"\n )cc " + 
						
						 /*--------------------------------------------*/
						 " outer apply "+ 
						 "  ( "+ 
						 " 	select count(distinct khachhang_fk ) num  "+ 
						 " 	from ddkd_khachhang dh   "+ 
						 " 	where  dh.ddkd_fk =  DDKD.PK_SEQ and month(dh.ngayghinhan) = month(MCP.NGAY)  and year(dh.ngayghinhan) = year(MCP.NGAY) and dh.ngayghinhan <= MCP.NGAY "+ 
						 "  ) dvt "+ 
						 "  outer apply  "+ 
						 "  ( "+ 
						 " 	SELECT T.NPP_FK, COUNT( DISTINCT CT.KHACHHANG_FK) SOKHTOADO  "+ 
						 " 	FROM DAIDIENKINHDOANH DD "+ 
						 " 	INNER JOIN TUYENBANHANG T ON T.DDKD_FK = DD.PK_SEQ "+ 
						 " 	INNER JOIN KHACHHANG_TUYENBH CT ON CT.TBH_FK = T.PK_SEQ "+ 
						 " 	WHERE EXISTS ( SELECT 1 FROM KHACHHANG KH WHERE KH.LAT IS NOT NULL AND KH.TRANGTHAI = '1' AND KH.PK_SEQ = CT.KHACHHANG_FK ) "+ 
						 " 	AND DD.PK_SEQ =  DDKD.PK_SEQ AND T.NPP_FK = NPP.PK_SEQ "+ 
						 " 	GROUP BY T.NPP_FK "+ 
						 "  ) toado "+ 
						 "   outer apply  "+ 
						 "  (  "+ 
						 " 	select  convert( varchar,min(thoidiem),120) vtkh,convert( varchar,max(thoidiemdi),120) roidikh, count(distinct khachhang_fk) viengthamkh  "+ 
						 " 	, count ( distinct case isDungTuyen  when 0 then khachhang_fk else null end) traituyen  "+ 
						 " 	,convert(char(5), dateadd(minute,Sum(isnull(thoigiantaicuahang,0)),0), 108) tgbanhang  "+ 
						 " 	,CONVERT(char(8), DATEADD(SECOND, DATEDIFF(SECOND,Min(thoidiem),Max(thoidiemdi)),0), 108) as TGLV  "+ 
						 " 	from ddkd_khachhang   "+ 
						 "  	where ddkd_fk = mcp.ddkd_fk  "+ 
						 " 	and month(ddkd_khachhang.ngayghinhan) = month(mcp.NGAY)  and year(ddkd_khachhang.ngayghinhan) = year(mcp.NGAY) and ddkd_khachhang.ngayghinhan <= mcp.ngay  "+ 
				 		 "  ) akh "+
						 /*--------------------------------------------*/
						"\n where 1=1 ";
		if(obj.getvungId().trim().length() > 0)
			query += " and v.pk_seq = "+ obj.getvungId() +"  "; 
		if(obj.getkhuvucId().trim().length() > 0)
			query += " and kv.pk_seq = "+ obj.getkhuvucId() +"  ";		
		if(obj.getnppId().length() > 0)
			query += " and npp.pk_seq ='" + obj.getnppId() + "'";
		if (obj.getDdkd().length() > 0)
			query += " and ddkd.pk_seq ='" + obj.getDdkd() + "'";
		 if(obj.getView().equals("TT")){

		query += " and npp.pk_seq in " + util.quyen_npp(obj.getuserId()) + " and ddkd.kbh_fk in  " + util.quyen_kenh(obj.getuserId());
		
		 }
		query +="\n order by [VÙNG],KV,[TÊN NPP],[TÊN NVBH] ";
		
		System.out.println("bc:______" + query);

		return query ;
	}

	private String setQueryTheoNgay(IStockintransit obj, String view) 
	{
		Utility util = new Utility();

		
		String query =  "\n with mcp as " + 
						"\n ( " + 
						"\n  " + 
						"\n 	select DDKD_FK,Ngay,NgayId,SOKH,ALLSOKH,route_fk,npp_fk,gsbh_fk  " + 
						"\n 	from ddkd_sokh where ngay = '"+obj.getdenngay()+"' " + 
						"\n ) " + 
						"\n select  v.Ten [VÙNG], kv.ten KV, isnull(tt.ten,'NA') +'/' + isnull(qh.ten,'NA') [TỈNH/QUẬN]   " + 
						"\n 	, npp.manpp [MÃ NPP], npp.ten [TÊN NPP],kbh.ten [Kênh] " +
						"\n     , case when npp.trangthai = 1 then N'Hoạt động' else N'Ngưng hoạt động' end [Trạng thái NPP] " +
						"\n     , asm.ten [ASM], gs.ten [GIÁM SÁT], ddkd.smartId [MÃ NVBH], ddkd.ten [TÊN NVBH] " + 
						"\n     , case when ddkd.trangthai = 1 then N'Hoạt động' else N'Ngưng hoạt động' end [Trạng thái DDKD] " +
						"\n     , ddkd.ngaybatdau [Ngày vào làm], ThucDat.thucdatTruocVat [THỰC ĐẠT DS] , mcp.ALLSOKH ASO, isnull(toado.SOKHTOADO,0) [ASO có định vị]" +
						"\n		, thucdat.AO , dbo.PhepChia (thucdat.AO , mcp.ALLSOKH )[TỶ LỆ PHỦ(%)] " + 
						"\n 	, pvt.phaivt [PHẢI VT], vt.davt [ĐÃ VT], akh.traituyen [Đã viếng thăm trái tuyến], dvt.num [Số KH đã VT] " +
						"\n     , dbo.PhepChia ( vt.davt ,pvt.phaivt) [%VT], ThucDat.SoDonHang [SỐ ĐH], ThucDat.SoDonHangDaChot [SỐ ĐH ĐÃ CHỐT], dbo.PhepChia (thucdat.SoDonHang ,  vt.davt ) [% BÁN HÀNG THÀNH CÔNG THỰC TẾ] " + 
						"\n 	, dbo.PhepChia (thucdat.SoDonHang ,pvt.phaivt ) [% BÁN HÀNG THÀNH CÔNG KẾ HOẠCH] " + 
						"\n 	, ThucDat.sku, dbo.PhepChia (ThucDat.sku,thucdat.SoDonHang ) [SKU/ĐH], dbo.PhepChia (ThucDat.thucdatTruocVat,thucdat.SoDonHang ) DropSize " + 
						"\n 	, dbo.PhepChia (ThucDat.thucdatTruocVat,thucdat.AO ) VPO " + 						
						"\n from mcp " + 
						"\n inner join DaiDienkinhdoanh ddkd on ddkd.pk_seq=  mcp.ddkd_fk" +
						"\n outer apply " +
						"\n (" +
						"\n		select top 1 gs.ten, gs.pk_seq, gs.kbh_fk, gs.asm_fk " +
						"\n		from giamsatbanhang gs " +
						"\n		inner join ddkd_gsbh x on x.gsbh_fk = gs.pk_seq " +
						"\n		where  x.ddkd_fk = ddkd.pk_seq order by gs.trangthai desc " +
						"\n )gs " + 
						"\n inner join Nhaphanphoi npp on npp.pk_seq = ddkd.npp_fk " + 
						"\n inner join KhuVuc kv on kv.pk_seq = npp.khuvuc_fk" +
						"\n outer apply " +
						"\n (" +
						"\n		select top 1 asm.pk_seq , asm.ten, asm.bm_fk " +
						"\n 	from asm  " +
						"\n		where asm.pk_seq =  gs.asm_fk  order by asm.trangthai desc " +
						"\n )asm " + 
						"\n inner join vung v on v.pk_seq = kv.vung_fk " + 
						"\n left join tinhthanh tt on tt.pk_seq = npp.tinhthanh_fk " + 
						"\n left join kenhbanhang kbh on kbh.pk_seq = ddkd.kbh_fk " + 
						"\n left join quanhuyen qh on qh.pk_seq = npp.quanhuyen_fk " + 				
						"\n outer apply " + 
						"\n ( " + 
						"\n 	select count(distinct case when dh.trangthai = 1 then khachhang_fk else null end ) AO" +
						"\n			, count(distinct case when dh.trangthai = 1 then pk_SEQ else null end  ) SoDonHangDaChot, count(distinct pk_SEQ  ) SoDonHang" +
						"\n			, count ( case when dh.trangthai = 1 then pk_SEQ else null end ) sku " + 
						"\n 		, sum( case when dh.trangthai = 1 then  soluong * giamua else 0 end) thucdatTruocVat  " + 
						"\n 	from donhang dh inner join donhang_sanpham dhsp on dh.pk_seq = dhsp.DONHANG_FK " + 
						"\n 	where dh.ddkd_fk = ddkd.pk_seq and trangthai in(0,1) and dh.ngaynhap >='"+obj.gettungay()+"'   and dh.ngaynhap  <= mcp.ngay  " +
						"\n				and not exists ( select 1 from donhangtrave where trangthai = 3 and donhang_fk = dh.pk_seq)  and dh.TONGGIATRI>0  " + 
						"\n )ThucDat " + 
						"\n outer apply " + 
						"\n ( " + 
						"\n 	select sum(SOKH ) phaivt " + 
						"\n 	from ddkd_sokh dh  " + 
						"\n 	where  dh.ddkd_fk =  ddkd.pk_seq  and dh.NGAY >='"+obj.gettungay()+"' and dh.NGAY <= mcp.ngay " + 
						"\n )pvt " + 
						"\n outer apply " + 
						"\n ( " + 
						"\n 	select sum(data)davt " + 
						"\n 	from " + 
						"\n 	( " + 
						"\n 		select dh.ngayghinhan,count(distinct khachhang_fk ) data " + 
						"\n 		from ddkd_khachhang dh  " + 
						"\n 		where  dh.ddkd_fk =  ddkd.pk_seq and dh.ngayghinhan >='"+obj.gettungay()+"'  and dh.ngayghinhan <= mcp.ngay " + 
						"\n 		group by dh.ngayghinhan " + 
						"\n 	) x " + 
						"\n )vt " +					
						 /*--------------------------------------------*/
						 " outer apply "+ 
						 "  ( "+ 
						 " 		select count(distinct khachhang_fk ) num  "+ 
						 " 		from ddkd_khachhang dh   "+ 
						 " 		where  dh.ddkd_fk =  DDKD.PK_SEQ  and dh.ngayghinhan >='"+obj.gettungay()+"' and    dh.ngayghinhan <= MCP.NGAY "+ 
						 "  ) dvt "+ 
						 "  outer apply  "+ 
						 "  ( "+ 
						 " 		SELECT T.NPP_FK, COUNT( DISTINCT CT.KHACHHANG_FK) SOKHTOADO  "+ 
						 " 		FROM DAIDIENKINHDOANH DD "+ 
						 " 		INNER JOIN TUYENBANHANG T ON T.DDKD_FK = DD.PK_SEQ "+ 
						 " 		INNER JOIN KHACHHANG_TUYENBH CT ON CT.TBH_FK = T.PK_SEQ "+ 
						 " 		WHERE EXISTS ( SELECT 1 FROM KHACHHANG KH WHERE KH.LAT IS NOT NULL AND KH.TRANGTHAI = '1' AND KH.PK_SEQ = CT.KHACHHANG_FK ) "+ 
						 " 		AND DD.PK_SEQ =  DDKD.PK_SEQ AND T.NPP_FK = NPP.PK_SEQ "+ 
						 " 		GROUP BY T.NPP_FK "+ 
						 "  ) toado "+ 
						 "   outer apply  "+ 
						 "  (  "+ 
						 " 		select  convert( varchar,min(thoidiem),120) vtkh,convert( varchar,max(thoidiemdi),120) roidikh, count(distinct khachhang_fk) viengthamkh  "+ 
						 " 			, count ( distinct case isDungTuyen  when 0 then khachhang_fk else null end) traituyen  "+ 
						 " 			,convert(char(5), dateadd(minute,Sum(isnull(thoigiantaicuahang,0)),0), 108) tgbanhang  "+ 
						 " 			,CONVERT(char(8), DATEADD(SECOND, DATEDIFF(SECOND,Min(thoidiem),Max(thoidiemdi)),0), 108) as TGLV  "+ 
						 " 		from ddkd_khachhang   "+ 
						 "  	where ddkd_fk = mcp.ddkd_fk  "+ 
						 " 			and ddkd_khachhang.ngayghinhan >='"+obj.gettungay()+"'  and ddkd_khachhang.ngayghinhan <= mcp.ngay  "+ 
						 "  ) akh "+
						 /*--------------------------------------------*/
						"\n where 1=1 ";
		if(obj.getvungId().trim().length() > 0)
			query += " and v.pk_seq = "+ obj.getvungId() +"  ";
		if(obj.getkhuvucId().trim().length() > 0)
			query += " and kv.pk_seq = "+ obj.getkhuvucId() +"  ";		
		if(obj.getnppId().length() > 0)
			query += " and npp.pk_seq ='" + obj.getnppId() + "'";
		if (obj.getDdkd().length() > 0)
			query += " and ddkd.pk_seq ='" + obj.getDdkd() + "'";
	   		if(obj.getPhanloai().equals("2"))//HO
		{
			if(obj.getLoaiNv().equals("1")) // BM
				query += " and asm.BM_FK = ( select top 1 BM_FK from nhanvien where pk_seq ="+obj.getuserId()+" ) ";
			else
				if(obj.getLoaiNv().equals("2")) // BM
					query += " and  asm.pk_seq  = ( select top 1 asm_fk from nhanvien where pk_seq ="+obj.getuserId()+" ) ";
				else
					if(obj.getLoaiNv().equals("3")) // BM
						query += " and  gs.pk_seq  = ( select top 1 gsbh_fk from nhanvien where pk_seq ="+obj.getuserId()+" ) ";
					else
						query += " and npp.pk_seq in " + util.quyen_npp(obj.getuserId()) ;
		}
		else
		{
			query += " and npp.pk_seq =   " + obj.getnppId();
		}
		
		
		
		
		query +="\n order by [VÙNG],KV,[TÊN NPP],[TÊN NVBH] ";
		
		System.out.println("bc: ngay______" + query);

		return query ;
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

		String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		if(view == null)
			view = "";

		IStockintransit obj = new Stockintransit();
		obj.setuserId(userId);
		obj.setView(view);
		obj.init_user();	  
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);


		String nextJSP = "/AHF/pages/Center/BCPerformance.jsp";
		response.sendRedirect(nextJSP);
	}


	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		Utility util = new Utility();

		String userId = (String) session.getAttribute("userId");
		if (userId == null)
			userId = "";
		obj.setuserId(userId);

		String userTen = (String) session.getAttribute("userTen");
		obj.setuserTen(userTen);
		String nppId = Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
		if (nppId == null)
			nppId = "";
		obj.setnppId(nppId);	

		String view = Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		obj.setView(view);
		
		String vungId = Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")));
		if (vungId == null)
			vungId = "";
		obj.setvungId(vungId);

		String khuvucId = Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")));
		if (khuvucId == null)
			khuvucId = "";
		obj.setkhuvucId(khuvucId);

		String kenhId = Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")));
		if (kenhId == null)
			kenhId = "";
		obj.setkenhId(kenhId);

	
		String ddkdId = Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdId")));
		if (ddkdId == null)
			ddkdId = "";
		obj.setDdkd(ddkdId);

		String tungay = Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays")));
		if (tungay == null)
			tungay = "";
		obj.settungay(tungay);

		String denngay = Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays")));
		if (denngay == null)
			denngay = "";
		obj.setdenngay(denngay);

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		obj.init_user();	 
		
		if (action.equals("taoNgay")) 
		{
			try 
			{
				request.setCharacterEncoding("utf-8");
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BCPerformanceTheoNgay.xlsm");
				OutputStream out = response.getOutputStream();
				String query = setQueryTheoNgay(obj, view);
				ExportToExcel( out, obj, query );
				obj.getDb().shutDown();
			} 
			catch (Exception ex) 
			{
				ex.printStackTrace();
				System.out.println("Error Here : "+ex.toString());
				request.getSession().setAttribute("errors", ex.getMessage());
			}
		}else
		if (action.equals("tao")) 
		{
			try 
			{
				request.setCharacterEncoding("utf-8");
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BCPerformance.xlsm");
				OutputStream out = response.getOutputStream();
				String query = setQueryTheoThang(obj, view);
				ExportToExcel( out, obj, query );
				obj.getDb().shutDown();
			} 
			catch (Exception ex) 
			{
				ex.printStackTrace();
				System.out.println("Error Here : "+ex.toString());
				request.getSession().setAttribute("errors", ex.getMessage());
			}
		}else
			{
				obj.setuserId(userId);
			   
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);

				String nextJSP = "/AHF/pages/Center/BCPerformance.jsp";
				response.sendRedirect(nextJSP);
			}

	}
	
	private void ExportToExcel(OutputStream out,IStockintransit obj,String query )throws Exception
	{
		try{ 					
			
			FileInputStream fstream = null;
			Workbook workbook = new Workbook();
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			TaoBaoCao(workbook, obj, query);			
			workbook.save(out);					

		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
	}
	
	private void TaoBaoCao(com.aspose.cells.Workbook workbook,IStockintransit obj,String query)throws Exception
	{
		
		try{		

			com.aspose.cells.Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			com.aspose.cells.Cells cells = worksheet.getCells();
			Cell cell = cells.getCell("A1");;	
		   
			cells.setRowHeight(0, 20.0f);
			ReportAPI.getCellStyle(cell, Color.RED, true, 16, "Báo Cáo Performance ");
			cell = cells.getCell("A2");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 10, "  Đến ngày : " + obj.getdenngay());
			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 10, "Ngày tạo : " + this.getDateTime());
			cell = cells.getCell("A4");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 10, "Người tạo : " + obj.getuserTen());			
			
			ResultSet rs = obj.getDb().get(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();
			
			int location  = 0;
			int row = 10;
			for( int i =1 ; i <=socottrongSql ; i ++  )
			{
				cell = cells.getCell(row, location + i-1 );
				cell.setValue(rsmd.getColumnName(i).replace("(%)",""));
				ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			}
			
			row ++;
			while(rs.next())
			{
				for(int i =1;i <=socottrongSql ; i ++)
				{					
					cell = cells.getCell(row,location + i-1 );
					
					if(!rsmd.getColumnName(i).contains("Ma") && rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i) == Types.INTEGER || rsmd.getColumnType(i) == Types.DECIMAL )
					{
						int format = 37;
							if(rsmd.getColumnName(i).contains("%")|| rsmd.getColumnName(i).contains("(%)") )	
								format = 10;
						cell.setValue(rs.getDouble(i));
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, format);
					}
					else
					{
						cell.setValue(rs.getString(i));
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					}
				}
				
				++row;
			}
			
			if(rs!=null)rs.close();		
			
		}catch(Exception ex){

			ex.printStackTrace();
			throw new Exception("Lỗi ! Không có dữ liệu để xuất file !");
		}	
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		Date date = new Date();
		return dateFormat.format(date);
	}



}
