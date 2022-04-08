package geso.dms.distributor.servlets.reports;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Column;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.google.common.collect.SetMultimap;

public class BCDonHangBanTrongKy extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	private String setQuery(Ireport obj) 
	{
		String query = "";

		return query;
	}

	private String setQuery_KhongPiVot(Ireport obj, String view) 
	{
		Utility util = new Utility();

		String conditionDH  = "";

		if(obj.getvungId().trim().length() > 0)
			conditionDH += " and v.pk_seq = "+ obj.getvungId() +"  ";
		
		if(obj.getkhuvucId().trim().length() > 0)
			conditionDH += " and kv.pk_seq = "+ obj.getkhuvucId() +"  ";	
		
		if(obj.getnppId().length() > 0)
			conditionDH += " and a.npp_fk ='" + obj.getnppId() + "'";
		
		if (obj.getddkdId().length() > 0)
			conditionDH += " and a.ddkd_fk ='" + obj.getddkdId() + "'";
		
		if (obj.getkhachhangId().length() > 0)
			conditionDH += " and a.khachhang_fk ='" + obj.getkhachhangId() + "'";

		if(view.equals("TT")){
			conditionDH += " and a.npp_fk in " + util.quyen_npp(obj.getuserId()) + " and a.kbh_fk in  " + util.quyen_kenh(obj.getuserId());
		}
		

		final String convert_date = "1899-12-30";
		
		String query = 
			
			 "\n with dh as " + 
			 "\n ( " + 
			 "\n     select a.ngaytao as ngaytaodh,month(a.ngaynhap) thang, year(a.ngaynhap) nam , kbh.TEN KBH ,v.ten Vung, kv.Ten KV,tinh.TEN TinhThanh " + 
			 "\n    ,Datediff(d,'"+convert_date+"',a.ngaynhap) ngaynhap " + 
			 "\n    ,npp.manpp, npp.ten TenNpp, route.ten tenroute, ddkd.smartId MaNVBH, ddkd.ten TenNVBH " + 
			 "\n    ,kh.smartId MaKH,kh.mathamchieu, kh.ten TenKH, kh.DIACHI, isnull(a.nguoichot, a.ddkdTAO_fk) nguoichot, convert( varchar,a.NGAYCHOT,120)NGAYCHOT,convert(nvarchar,(isnull(a.ThoiDiemChot,a.NgayChot)),120) as ngaygiochot   " + 
			 "\n    ,tbh.DienGiai TBH, tt.Ten TrangThai, gs.smartId MaGSBH, gs.ten TenGSBH " + 
			 "\n    ,case a.isdungtuyen when 1 then N'Đúng tuyến' else N'Trái tuyến' end Tuyen " + 
			 "\n    ,a.PK_SEQ,a.CHIETKHAU " + 
			 "\n    ,a.DDKD_FK,a.KHACHHANG_FK,a.NPP_FK,a.GSBH_FK,a.KHO_FK,a.KBH_FK,a.NgayGio  " + 
			 "\n    ,a.IsPDA,a.FlagModified,a.TONGGIATRI," + 
			 "\n    day(a.ngaynhap) ngay, a.ngaygiaohang , case when npp.trangthai = 1 then N'Hoạt động' else N'Ngưng hoạt động' end npptrangthai," + 
			 "\n    case when ddkd.trangthai = 1 then N'Hoạt động' else N'Ngưng hoạt động' end ddkdtrangthai," + 
			 "\n    hch.hang khhch, lch.diengiai khloai, isnull(gc.diengiai, '') ghichu, isnull(a.ghichu, '') ghichukhac " + 
			 "\n 	from donhang a  " + 
			 "\n 	left join tuyenbanhang tbh on tbh.pk_seq= a.tbh_fk " + 
			 "\n 	left join khachhang kh on kh.pk_seq = a.khachhang_Fk " + 
			 "\n 	left join ghichu gc on gc.pk_seq = a.ghichu_Fk " + 
			 "\n 	left join hangcuahang hch on hch.pk_seq = kh.hch_fk " + 
			 "\n 	left join loaicuahang lch on lch.pk_seq = kh.lch_fk " + 
			 "\n 	left join giamsatbanhang gs on gs.pk_seq = a.gsbh_fk " + 
			 "\n 	left join daidienkinhdoanh ddkd on ddkd.pk_seq = a.ddkd_fk " + 
			 "\n 	left join dms_route route on route.pk_seq = ddkd.route_fk " + 
			 "\n 	left join kenhbanhang kbh on kbh.pk_seq = a.kbh_fk " + 
			 "\n 	left join Nhaphanphoi npp on npp.PK_SEQ = a.NPP_FK " + 
			 "\n 	left join KHUVUC kv on kv.PK_SEQ =  npp.KHUVUC_FK " + 
			 "\n 	left join vung v on v.pk_seq =  kv.vung_fk " + 
			 "\n 	left join tinhthanh tinh on tinh.PK_SEQ = npp.TINHTHANH_FK " + 
			 "\n 	left join TrangThaiNghiepVu tt on tt.pk_seq = a.trangthai and tt.TableName ='donhang' " + 
			 "\n 	where a.trangthai<>2 and a.trangthai in (0,1) and isnull(a.issam, 0) <> 1 and a.ngaynhap >='"+obj.gettungay()+"' " +
			 "\n 	and a.ngaynhap <= '"+obj.getdenngay()+"'   " + conditionDH + 
			 "\n ),	" + 
			 "\n thle as " + 
			 "\n ( " + 
			 "\n     select a.ngaytao as ngaytaodh,month(a.NGAYNHAP) thang, year(a.NGAYNHAP) nam , kbh.TEN KBH ,v.ten Vung, kv.Ten KV,tinh.TEN TinhThanh " + 
			 "\n    ,Datediff(d,'"+convert_date+"',a.NGAYNHAP) ngaynhap " + 
			 "\n    ,npp.manpp, npp.ten TenNpp, route.ten tenroute, ddkd.smartId MaNVBH, ddkd.ten TenNVBH " + 
			 "\n    ,kh.smartId MaKH,kh.mathamchieu, kh.ten TenKH, kh.DIACHI,a.NGUOIDUYET nguoichot, convert( varchar,a.NGAYDUYET,120)NGAYCHOT ,convert(nvarchar,(isnull(a.NGAYGIOCHOT,a.NGAYDUYET)),120) NGAYGIOCHOT     " + 
			 "\n    ,'' TBH, tt.Ten TrangThai, gs.smartId MaGSBH, gs.ten TenGSBH " + 
			 "\n    ,''  Tuyen " +  
			 "\n    , a.PK_SEQ, 0 CHIETKHAU " + 
			 "\n    ,a.DDKD_FK,a.KHACHHANG_FK,a.NPP_FK,a.GSBH_FK,a.KHO_FK,a.KBH_FK,a.NGAYSUA  " + 
			 "\n    ,0 IsPDA, 0 FlagModified,a.TongTraHang  TONGGIATRI ," + 
			 "\n    day(a.ngaynhap) ngay, '' ngaygiaohang , case when npp.trangthai = 1 then N'Hoạt động' else N'Ngưng hoạt động' end npptrangthai," + 
			 "\n    case when ddkd.trangthai = 1 then N'Hoạt động' else N'Ngưng hoạt động' end ddkdtrangthai," + 
			 "\n    hch.hang khhch, lch.diengiai khloai,  isnull(a.ghichutt,'') ghichu, '' ghichukhac  " + 
			 "\n 	from DONHANGTRAVE a  " + 
			 "\n 	left join khachhang kh on kh.pk_seq = a.khachhang_Fk " + 
			 "\n 	left join hangcuahang hch on hch.pk_seq = kh.hch_fk " + 
			 "\n 	left join loaicuahang lch on lch.pk_seq = kh.lch_fk " + 
			 "\n 	left join giamsatbanhang gs on gs.pk_seq = a.gsbh_fk " + 
			 "\n 	left join daidienkinhdoanh ddkd on ddkd.pk_seq = a.ddkd_fk " + 
			 "\n 	left join dms_route route on route.pk_seq = ddkd.route_fk " + 
			 "\n 	left join kenhbanhang kbh on kbh.pk_seq = a.kbh_fk " + 
			 "\n 	left join Nhaphanphoi npp on npp.PK_SEQ = a.NPP_FK " + 
			 "\n 	left join KHUVUC kv on kv.PK_SEQ =  npp.KHUVUC_FK " + 
			 "\n 	left join vung v on v.pk_seq =  kv.vung_fk " + 
			 "\n 	left join tinhthanh tinh on tinh.PK_SEQ = npp.TINHTHANH_FK " + 
			 "\n	left join TrangThaiNghiepVu tt on tt.pk_seq = a.trangthai and tt.TableName ='DonHangTraVe' " + 
			 "\n 	where a.trangthai = 3 and a.DONHANG_FK is  null  and ngaynhap >='"+obj.gettungay()+"'  " + 
			 "\n	and ngaynhap <= '"+obj.getdenngay()+"'    " + conditionDH +  
			 "\n ),	" + 
			 "\n dhct as " +
			 "\n ( " + 
			 "\n 	select dh.ngaynhap ngaydonhang,dh.PK_SEQ dhId,dh.* , dhsp.sanpham_fk ,dhsp.soluong,dhsp.giamua,dhsp.soluong * dhsp.giamua thanhtien, k.ten,'' CTKM, 0 TienKM , 0 trahang_fk, 0NgayTra  " + 
			 "\n 	from dh  " + 
			 "\n 	inner join donhang_sanpham dhsp on dh.pk_seq = dhsp.donhang_fk  " + 
			 "\n 	inner join kho k on k.pk_seq =dh.kho_fk  " + 
			 "\n 	union all  " + 
			 "\n 	select  dh.ngaynhap ngaydonhang,dh.PK_SEQ dhId,dh.* , dhsp.sanpham_fk ,dhsp.soluong,0 giamua,0 thanhtien, k.ten , ctkm.scheme CTKM, 0 TienKM , 0 trahang_fk, 0 NgayTra " + 
			 "\n 	from dh  " + 
			 "\n 	inner join donhang_ctkm_trakm dhsp on dh.pk_seq = dhsp.donhangId  " + 
			 "\n 	inner join ctkhuyenmai ctkm on ctkm.pk_seq = dhsp.ctkmId  " + 
			 "\n 	inner join kho k on k.pk_seq =ctkm.kho_fk  " + 
			 "\n 	where dhsp.spma is not null  " + 
			 "\n 	union all  " + 
			 "\n 	select  dh.ngaynhap ngaydonhang,dh.PK_SEQ dhId,dh.* , 0 sanpham_fk ,dhsp.soluong,0 giamua,0 thanhtien, k.ten , ctkm.scheme CTKM, dhsp.tonggiatri TienKM , 0 trahang_fk, 0 NgayTra  " + 
			 "\n 	from dh  " + 
			 "\n 	inner join donhang_ctkm_trakm dhsp on dh.pk_seq = dhsp.donhangId  " + 
			 "\n 	inner join ctkhuyenmai ctkm on ctkm.pk_seq = dhsp.ctkmId  " + 
			 "\n 	inner join kho k on k.pk_seq =ctkm.kho_fk  " + 
			 "\n 	where dhsp.spma is  null  " + 
			 "\n 	union all " + 
	 
			 "\n 	select  dh.ngaynhap ngaydonhang,dh.PK_SEQ dhId,dh.* , dhsp.sanpham_fk ,(-1)*dhsp.soluong,dhsp.giamua,(-1)*dhsp.soluong * dhsp.giamua thanhtien, k.ten,'' CTKM, 0 TienKM , th.PK_SEQ trahang_fk,Datediff(d,'"+convert_date+"',th.NGAYNHAP)  NgayTra  " + 
			 "\n 	from dh  " + 
			 "\n 	inner join DONHANGTRAVE th on th.DONHANG_FK = dh.PK_SEQ " + 
			 "\n 	inner join donhang_sanpham dhsp on dh.pk_seq = dhsp.donhang_fk  " + 
			 "\n 	inner join kho k on k.pk_seq =dh.kho_fk " + 
			 "\n 	where th.trangthai = 3 " + 
			 "\n 	union all  " + 
			 "\n 	select  dh.ngaynhap ngaydonhang,dh.PK_SEQ dhId,dh.* , dhsp.sanpham_fk ,(-1)*dhsp.soluong,0 giamua,0 thanhtien, k.ten , ctkm.scheme CTKM, 0 TienKM  , th.PK_SEQ trahang_fk,Datediff(d,'"+convert_date+"',th.NGAYNHAP) NGAYNHAP  " + 
			 "\n 	from dh  " + 
			 "\n 	inner join DONHANGTRAVE th on th.DONHANG_FK = dh.PK_SEQ " + 
			 "\n 	inner join donhang_ctkm_trakm dhsp on dh.pk_seq = dhsp.donhangId  " + 
			 "\n 	inner join ctkhuyenmai ctkm on ctkm.pk_seq = dhsp.ctkmId  " + 
			 "\n 	inner join kho k on k.pk_seq =ctkm.kho_fk  " + 
			 "\n 	where dhsp.spma is not null and  th.trangthai = 3  " + 
			 "\n 	union all  " + 
			 "\n 	select  dh.ngaynhap ngaydonhang,dh.PK_SEQ dhId,dh.* , 0 sanpham_fk ,0 soluong,0 giamua,0 thanhtien, k.ten , ctkm.scheme CTKM,(-1)* dhsp.tonggiatri TienKM  , th.PK_SEQ trahang_fk,Datediff(d,'"+convert_date+"',th.NGAYNHAP) NGAYNHAP  " + 
			 "\n 	from dh  " + 
			 "\n 	inner join DONHANGTRAVE th on th.DONHANG_FK = dh.PK_SEQ " + 
			 "\n 	inner join donhang_ctkm_trakm dhsp on dh.pk_seq = dhsp.donhangId  " + 
			 "\n 	inner join ctkhuyenmai ctkm on ctkm.pk_seq = dhsp.ctkmId  " + 
			 "\n 	inner join kho k on k.pk_seq =ctkm.kho_fk  " + 
			 "\n 	where dhsp.spma is  null and  th.trangthai = 3  " + 
			 "\n 	union all " + 
			 "\n 	select  0 ngaydonhang,0 dhId,thle.* , dhsp.sanpham_fk ,(-1)*dhsp.soluong,dhsp.giamua,(-1)*dhsp.soluong * dhsp.giamua thanhtien, k.ten,'' CTKM, 0 TienKM , thle.PK_SEQ trahang_fk,thle.NGAYNHAP NgayTra " + 
			 "\n 	from thle  " + 
			 "\n 	inner join DONHANGTRAVE_SANPHAM dhsp on thle.PK_SEQ = dhsp.donhangtrave_fk  " + 
			 "\n 	inner join kho k on k.pk_seq =thle.kho_fk " + 
			 "\n 	where 1=1 " + 
			 "\n )  " +
			 
			 "\n select dhct.nam, dhct.thang, dhct.KBH, dhct.ngay, dhct.Vung, dhct.KV,dhct.TinhThanh, dhct.ngaydonhang NGAYNHAP,dhct.ngaytaodh ,isnull(convert(nvarchar(10),dhct.ngaygiaohang ,126),'') as ngaygiaohang " + 
			 "\n , dhct.manpp,dhct.tenNPP,dhct.npptrangthai,tenroute,dhct.MaNVBH,dhct.TenNVBH, dhct.ddkdtrangthai, dhct.MaKH,dhct.mathamchieu  " + 
			 "\n , dhct.TenKH,dhct.khhch,dhct.khloai,dhct.DIACHI  " + 
			 "\n , isnull(nvc.ten, dd.ten) NguoiChot, dhct.NGAYCHOT,case when len(isnull(dhct.ngaygiochot,''))>10 then SUBSTRING(dhct.ngaygiochot,12,11) else '00:00:00' end as Gio,dhct.TBH, dhct.TrangThai  " + 
			 "\n , dhct.MaGSBH, dhct.TenGSBH,dhct.Tuyen,dhct.dhId MaDonHang  " + 
			 "\n , sp.ma MaSP , sp.ten TenSP , nh.Ten NhanHang, nganh.ten NganhHang, dhct.SoLuong, dhct.GIAMUA  " + 
			 "\n , dhct.thanhtien/dhx.tyleVat thanhtienchuathue, dhct.thanhtien, dhct.TienKM  " + 
			 "\n , dhct.CTKM ,dhct.trahang_fk,dhct.NgayTra, dhct.ghichu, dhct.ghichukhac,loaix.ten loaidon " + 
			 "\n from dhct  " +
			 "\n left join donhang dhx on dhx.pk_seq =  dhct.pk_seq " +
			 "\n left join loaidonhang loaix on loaix.id = dhx.donhangkhac " + 
			 "\n left join sanpham sp on sp.pk_seq = dhct.sanpham_fk  " + 
			 "\n left join nhanhang nh on nh.pk_seq =sp.nhanhang_fk  " + 
			 "\n left join nganhhang nganh on nganh.pk_seq = sp.nganhhang_fk  " + 
			 "\n left join nhanvien nvc on nvc.pk_seq = dhct.nguoichot  " +
			 "\n left join DAIDIENKINHDOANH dd on dd.PK_SEQ = dhct.nguoichot " + 
			 "\n order by dhct.pk_seq ";
		
		//query = " SELECT top(100) * FROM TEST_RESULTSET ";
		
		System.out.println("setQuery_KhongPiVot: " + query);

		return query ;
	}


	private String setQuery(Ireport obj,String tungay,String denngay,String st, String st_v,String stloc) 
	{
		//
		String query ="\n	with dhban as ( "+
		"\n	select a.PK_SEQ,b.SANPHAM_FK,b.SOLUONG ,b.GIAMUA,a.CHIETKHAU,b.CHIETKHAU as chietkhausp "+
		"\n		,a.NGAYNHAP,a.DDKD_FK,a.KHACHHANG_FK,a.NPP_FK,a.GSBH_FK,a.KHO_FK,a.KBH_FK,a.NGAYCHOT,a.NgayGio, "+
		"\n		a.IsPDA,a.FlagModified,a.isdungtuyen,a.TONGGIATRI,a.trangthai,a.TBH_FK ,a.nguoichot, isnull(a.ghichu, '') ghichu,  " +
		"\n     from donhang  a inner join DONHANG_SANPHAM b "+
		"\n			on a.PK_SEQ=b.DONHANG_FK "+
		"\n			where trangthai<>2 and isnull(a.issam, 0) <> 1  and ngaynhap >='"+tungay+"' and ngaynhap <= '"+denngay+"' "+stloc+
		"\n		),dhkm as ( "+
		"\n			select a.PK_SEQ,b.SPMA,b.SOLUONG ,b.CTKMID,a.CHIETKHAU,b.CHIETKHAU as chietkhausp "+
		"\n		,a.NGAYNHAP,a.DDKD_FK,a.KHACHHANG_FK,a.NPP_FK,a.GSBH_FK,a.KHO_FK,a.KBH_FK,a.NGAYCHOT,a.NgayGio, "+
		"\n		a.IsPDA,b.TONGGIATRI,a.trangthai,a.nguoichot,a.FlagModified,a.TBH_FK,a.isdungtuyen, isnull(a.ghichu, '') ghichu " +
		"\n     from donhang a "+
		"\n		inner join donhang_ctkm_trakm b "+
		"\n			on a.PK_SEQ=b.DONHANGID "+
		"\n			where trangthai<>2  and isnull(a.issam, 0) <> 1  and ngaynhap >='"+tungay+"' and ngaynhap <= '"+denngay+"' "+stloc+
		"\n		),dhtb as  ( "+
		"\n		select a.PK_SEQ, a.CHIETKHAU "+
		"\n		,a.NGAYNHAP,a.DDKD_FK,a.KHACHHANG_FK,a.NPP_FK,a.GSBH_FK,a.KHO_FK,a.KBH_FK,a.NGAYCHOT,a.NgayGio, "+
		"\n		a.IsPDA,a.trangthai,a.nguoichot,a.FlagModified,a.TBH_FK,a.isdungtuyen, isnull(a.ghichu, '') ghichu "+
		"\n		 from   donhang a  "+
		"\n			where trangthai<>2 and isnull(a.issam, 0) <> 1  and ngaynhap >='"+tungay+"' and ngaynhap <= '"+denngay+"' "+stloc+") "+

		"\n 	select tt.TEN as TinhThanh,qh.TEN as QuanHuyen,v.TEN as TENVUNG, kv.TEN as TENKHUVUC, NPP.SITECODE AS NPPID,NPP.TEN AS NPPTEN,dh.NGAYNHAP AS ngayhoadon ,ddkd.pk_seq as nvbhId,ddkd.SMARTID as MaNVBH,ddkd.ten as nvbhTen,kh.smartid as makh, "+
		"\n    		kh.ten as tenkh, kh.diachi as diachi,dh.pk_seq as dhId "+
		"\n    		,sp.ma as masp, isnull(sp.ma,'') maddt ,sp.ten as tensanpham, isnull(dh.soluong,0) as soluong, "+
		"\n    		isnull(dh.giamua,0) as dongia,isnull(dh.chietkhaunpp,0) as chietkhaunpp , "+
		"\n    		isnull(dh.soluong,0)* isnull(sp.trongluong,0)  as sanluong , "+
		"\n    		round(isnull(dh.soluong,0)*isnull(dh.giamua,0),0)   as tongtien , 0 as tienkhuyenmai , '' as scheme, dh.trangthai, "+
		"\n    		isnull((select lnpp.Ma from LoaiNPP lnpp where lnpp.pk_seq=NPP.LoaiNPP),'N/A') as maloainpp, isnull(sp.tenviettat,'') as tentatsp, "+ 
		"\n isnull(dh.chietkhau,0) as chietkhauTT, cast(Round(cast(DATEDIFF(MINUTE,NgayGio,ngaychot)/60.0 as numeric(18,1) ),1) as varchar(max)) as TG, " +
		"\n         kho, isnull(nh.ten,'') as NhanHang, isnull(cl.ten,'') as ChungLoai, dh.nguoichot as NguoiChot, NgayChot, Case when DATEPART(HOUR, NGAYCHOT) < 13 then N'Sáng' else N'Chiều' end as Gio,  TBH, 0 as SOHD, dh.SLSP as SLSP, case when loaidh = 1 then 'PDA' else 'WEB' end as loaidh," +
		"\n  		case when FlagModified = 1 then N'đã xử lý' else N'chưa xử lý' end as FlagModified, KBH, ch.diengiai as loaich , nsp.ten as nhomsp " +
		"\n 		,isnull(dh.soluong,0)/qc.soluong1 as slthung, gsbh.ten as gsbh, gsbh.smartid as magsbh, case when dh.isdungtuyen = 1 then N'Đúng tuyến' when dh.isdungtuyen = 0 then N'Trái tuyến' else N' ' end isdungtuyen  " +
		"\n			,isnull(dh.nhomskus,'') as nhomskus ";	
		if(obj.getnhomspct().equals("1"))
		{
			query += " , nspct.DIENGIAI as nhomchitieu ";
		}

		query += 
			"\n ,dh.ghichu from  	"+
			"\n    		  ( "+
			"\n    		  select  	0 as tongtien,0 as chietkhau,dh.pk_Seq, dh.ngaynhap,dh.ddkd_fk,dh.khachhang_fk,dh.npp_fk,dh.gsbh_fk,dh.kho_fk,dh.kbh_fk,isnull(dh_sp.sanpham_fk,dh_sp1.sanpham_fk) as sanpham_fk, "+
			"\n    		  CAST( isnull(dh_sp.giamua,dh_sp1.dongiagoc) as numeric(18,0)) as  giamua ,(-1)* isnull(dh_sp.soluong,dh_sp1.soluong) as soluong, (-1)*(isnull(dh_sp1.chietkhau,0)+ ISNULL(CK.ptchietkhau,0) ) AS CHIETKHAUNPP , "+
			"\n    		  (select ten from TrangThaiNghiepVu tt where dh.trangthai=tt.pk_Seq and TableName='donhangtrave')  as trangthai, '' NGAYCHOT, '' NgayGio,kho.ten+(case when (select top(1)khonv  from donhang where pk_seq = dh_sp1.donhang_fk) = 1 then ' NVBH' else '' end) as kho,'' as NguoiChot, '' as TBH, 0 AS SLSP, '' as loaidh, '' as FlagModified, '' as kbh, '' isdungtuyen "+
			"\n				, nsp.TEN as nhomskus, '' ghichu " +
			"\n    		  from donhangtrave dh "+ 
			"\n    		  left outer join donhangtrave_sanpham dh_sp on dh_sp.donhangtrave_fk = dh.pk_Seq "+ 
			"\n    		  left outer join  donhang_sanpham  dh_sp1 on dh.donhang_fk=dh_sp1.donhang_fk "+ 
			"\n				left join NHOMSKUS_SANPHAM ssp on ssp.SP_FK = dh_sp1.SANPHAM_FK " +
			"\n				left join NHOMSKUS nsp on nsp.PK_SEQ=ssp.NSKUS_FK " +
			"\n    		  left join KHACHHANG_SANPHAMCK CK on DH.KHACHHANG_FK = CK.khachhang_fk and DH.KBH_FK = CK.KENHBANHANG_FK and DH_SP1.SANPHAM_FK = CK.sanpham_fk "+
			"\n    		  inner join kho kho on kho.pk_seq = dh_sp.kho_fk "+
			"\n    		  where dh.trangthai=3  and dh.ngaynhap >='"+tungay+"' and dh.ngaynhap <= '"+denngay+"'";
		if (obj.getNhomskusId().length() > 0) {
			query += "\n and nsp.pk_seq = '" + obj.getNhomskusId() + "' \n";
			//query += " and nsp.PK_SEQ in (select NHOMSKUS_FK from NHANVIEN_NHOMSKUS where nhanvien_fk ='" + obj.getuserId() + "' )";

		}
		/*	if(st.length()>0)
			query= query + st;*/
		query=query+	"\n "+ 
		"\n 		  union all  "+ 
		"\n "+
		"\n 			select "+
		"\n dhban.tonggiatri as tongtien,dhban.chietkhau, dhban.pk_seq,	dhban.ngaynhap,dhban.ddkd_fk,dhban.khachhang_fk,dhban.npp_fk,dhban.gsbh_fk,dhban.kho_fk,dhban.kbh_fk,dhban.sanpham_fk, "+
		"\n (dhban.giamua ) giamua ,dhban.soluong,dhban.chietkhausp + ISNULL(CK.ptchietkhau,0) CHIETKHAUNPP, "+
		"\n (select ten from TrangThaiNghiepVu tt where dhban.trangthai=tt.pk_Seq and TableName='donhang')  as trangthai,NGAYCHOT,NgayGio  "+
		"\n	,kho.ten as kho,isnull(nv.ten,'') as NguoiChot, isnull(t.diengiai, '') as TBH, 1 as SLSP, dhban.isPDA as loaidh, dhban.FlagModified, kbh.diengiai as kbh, dhban.isdungtuyen as isdungtuyen  "+
		"\n ,nsp.TEN as nhomskus, dhban.ghichu " +
		"\n 			from  dhban  		 "+				
		"\n 			left join TUYENBANHANG t on t.pk_seq = dhban.tbh_fk "+
		"\n 			inner join kho on dhban.kho_fk = kho.pk_seq " +
		"\n 			inner join kenhbanhang kbh on dhban.kbh_fk = kbh.pk_seq ";
		if(obj.getkenhId().length()>0)
		{
			query += "and kbh.pk_seq ='"+obj.getkenhId()+"' ";
		}
		query += 
			"\n 			left join KHACHHANG_SANPHAMCK CK on dhban.KHACHHANG_FK = CK.khachhang_fk and dhban.KBH_FK = CK.KENHBANHANG_FK and dhban.SANPHAM_FK = CK.sanpham_fk "+
			"\n 			left join nhanvien nv on nv.Pk_seq = dhban.nguoichot "+
			"\n				left join NHOMSKUS_SANPHAM ssp on ssp.SP_FK = dhban.SANPHAM_FK " +
			"\n				left join NHOMSKUS nsp on nsp.PK_SEQ=ssp.NSKUS_FK " +
			"\n 			where dhban.trangthai<>2  and dhban.ngaynhap >='"+tungay+"' and dhban.ngaynhap <= '"+denngay+"' " ; 
		if (obj.getNhomskusId().length() > 0) {
			query += "\n and nsp.pk_seq = '" + obj.getNhomskusId() + "' \n";
			//query += " and nsp.PK_SEQ in (select NHOMSKUS_FK from NHANVIEN_NHOMSKUS where nhanvien_fk ='" + obj.getuserId() + "' )";

		}
		/*if(st.length()>0)
				query= query + st;	*/
		query=query+	"\n ) dh "+
		"\n INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = DH.NPP_FK ";
		if(obj.getLoaiNppId().length()>0)
		{
			query+= " and NPP.LoaiNPP=" + obj.getLoaiNppId() + " ";
		}
		query+="\n inner join khachhang kh on kh.pk_seq = dh.khachhang_fk "+ 
		"\n left join loaicuahang ch on ch.pk_seq = kh.lch_fk	"+
		"\n left join TINHTHANH tt on tt.PK_SEQ=kh.TINHTHANH_FK "+
		"\n left join QUANHUYEN qh on qh.PK_SEQ=kh.QUANHUYEN_FK "+
		"\n inner join sanpham sp on sp.pk_seq = dh.sanpham_fk  " +
		"\n left join nhomsanpham_sanpham nhsp on nhsp.sp_fk = sp.pk_seq and dh.sanpham_fk = nhsp.sp_fk " +
		"\n left join nhomsanpham nsp on nsp.pk_seq = nhsp.nsp_fk and nsp.type = 0 ";
		if(obj.getnhomspct().equals("1"))
		{
			query += "\n  inner join NHOMSANPHAMChiTieu_SANPHAM nctsp on nctsp.SP_FK = sp.PK_SEQ "+
			"\n  inner join NHOMSANPHAMCHITIEU nspct on nspct.PK_SEQ = nctsp.NSP_FK ";
		}

		query +="\n left join nhanhang nh on nh.pk_seq = sp.nhanhang_fk   "+
		"\n left join chungloai cl on cl.pk_seq = sp.chungloai_fk  "+
		"\n left join DONVIDOLUONG dvdl on sp.DVDL_FK = dvdl.PK_SEQ" +
		"\n left join QUYCACH qc on qc.SANPHAM_FK = sp.PK_SEQ and qc.DVDL1_FK = dvdl.PK_SEQ and qc.DVDL2_FK ='100018' " +
		"\n inner join daidienkinhdoanh ddkd on ddkd.pk_seq = dh.ddkd_fk " + 
		"\n left join DDKD_GSBH ddgs on ddgs.ddkd_fk = ddkd.pk_seq "+ 
		"\n left join giamsatbanhang gsbh on gsbh.pk_seq = ddgs.gsbh_fk "+	
		"\n inner join KHUVUC kv on kv.PK_SEQ = NPP.KHUVUC_FK \n"+
		"\n inner join VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+
		"\n where 1=1 ";

		/*if(st.length()>0)
					query= query + st;*/
		if(st_v.length() > 0)
			query= query + st_v;

		query= query + "\n "+
		"\n UNION "+	//UNION  danh dau 2 phan lay du lieu cua setQuery nay.	
		"\n " +
		"\n Select '' as TinhThanh,'' as QuanHuyen,v.TEN as TENVUNG, kv.TEN as TENKHUVUC, NPP.SITECODE AS NPPID,NPP.TEN AS NPPTEN,dh.NGAYNHAP AS ngayhoadon,ddkd.pk_seq as nvbhId,"+
		"\n ddkd.SMARTID as MaNVBH, ddkd.ten as nvbhTen,kh.smartid as makh,kh.ten as tenkh,kh.diachi "+ 
		"\n as diachi,dh.pk_seq as dhId,sp.ma as masp, isnull(sp.ma,'') maddt, "+
		"\n sp.ten as tensp, isnull(dh.soluong,0) as soluong  ,0 as dongia,0 as chietkhau,isnull(dh.soluong,0)* isnull(sp.trongluong,0) as sanluong,0 as tongtien,dh.tonggiatri as tienkhuyenmai ,ctkm.scheme as scheme,dh.trangthai, "+
		"\n isnull((select lnpp.Ma from LoaiNPP lnpp where lnpp.pk_seq=NPP.LoaiNPP),'N/A') as maloainpp, isnull(sp.tenviettat,'') as tentatsp, "+
		"\n isnull(dh.chietkhau,0) as chietkhauTT,'' as TG,kho.ten as kho,'' as NhanHang,'' as ChungLoai ,'' as NguoiChot,'' ngaychot,'' gio, TBH, 0 as SOHD, 0 as SLSP, case when loaidh = 1 then 'PDA' else 'WEB' end as loaidh "+
		"\n  		,case when FlagModified = 1 then N'đã xử lý' else N'chưa xử lý' end as FlagModified, kbh, ch.diengiai as loaich, '' as nhomsp "+
		"\n 		,isnull(dh.soluong,0)/qc.soluong1 as slthung, gsbh.ten as gsbh , gsbh.smartid as magsbh, case when dh.isdungtuyen = 1 then N'Đúng tuyến' when dh.isdungtuyen = 0 then N'Trái tuyến' else N' ' end isdungtuyen  " +
		"\n			, isnull(dh.nhomskus,'') as nhomskus ";
		if(obj.getnhomspct().equals("1"))
		{
			query += " , nspct.DIENGIAI as nhomchitieu ";
		}

		query +="\n ,dh.ghichu from "+
		"\n 			(	select 0 as chietkhau, dh.pk_seq,	dh.ngaynhap,dh.ddkd_fk,dh.khachhang_fk,dh.npp_fk,dh.gsbh_fk,dh.kho_fk,dh.kbh_fk,dh_sp1.spma ,dh_sp1.ctkmid,(-1)*dh_sp1.soluong as soluong,(-1)* dh_sp1.tonggiatri as tonggiatri, "+
		"\n  			(select ten from TrangThaiNghiepVu tt where dh.trangthai=tt.pk_Seq and TableName='donhangtrave') as trangthai, '' as TBH, '' as loaidh,'' as FlagModified, '' as kbh, '' isdungtuyen "+
		"\n				,  nsp.TEN as nhomskus, '' ghichu " +
		"\n 			from donhangtrave dh  "+
		"\n 		 	inner  join donhang_ctkm_trakm  dh_sp1 on dh.donhang_fk=dh_sp1.donhangid  "+
		"\n 			left join NHOMSKUS_SANPHAM ssp on ssp.SP_FK = (select pk_seq from sanpham where ma = dh_sp1.SPMA) " +
		"\n				left join NHOMSKUS nsp on nsp.PK_SEQ=ssp.NSKUS_FK " +
		"\n 	 		where dh.trangthai=3  and dh.ngaynhap >='"+tungay+"' and dh.ngaynhap <= '"+denngay+"'  ";
		if (obj.getNhomskusId().length() > 0) {
			query += "\n and nsp.pk_seq = '" + obj.getNhomskusId() + "' \n";
			//query += " and nsp.PK_SEQ in (select NHOMSKUS_FK from NHANVIEN_NHOMSKUS where nhanvien_fk ='" + obj.getuserId() + "' )";

		}
		/*   if(st.length()>0)
				    query= query + st;*/

		query=query+ "\n 		union all "+
		"\n 			select  "+
		"\n  dhkm.chietkhau,dhkm.pk_seq,	dhkm.ngaynhap,dhkm.ddkd_fk,dhkm.khachhang_fk,dhkm.npp_fk,dhkm.gsbh_fk,ctkm.kho_fk,dhkm.kbh_fk,dhkm.spma ,dhkm.ctkmid,dhkm.soluong as soluong,dhkm.tonggiatri , "+
		"\n  (select ten from TrangThaiNghiepVu tt where dhkm.trangthai=tt.pk_Seq and TableName='DonHang')  as trangthai, isnull(t.diengiai, '') as TBH, dhkm.isPDA as loaidh, dhkm.FlagModified, kbh.diengiai as kbh, dhkm.isdungtuyen as isdungtuyen "+
		"\n		, nsp.TEN as nhomskus, dhkm.ghichu " + 
		"\n 		 	from dhkm   "+
		"\n 			left join TUYENBANHANG t on t.pk_seq = dhkm.tbh_fk " +
		"\n		left join NHOMSKUS_SANPHAM ssp on ssp.SP_FK = (select pk_seq from sanpham where ma = dhkm.SPMA) " +
		"\n		 left join NHOMSKUS nsp on nsp.PK_SEQ=ssp.NSKUS_FK " +
		"\n 			inner join kenhbanhang kbh on kbh.pk_seq = dhkm.kbh_fk ";
		if(obj.getkenhId().length()>0)
		{
			query += "and kbh.pk_seq ='"+obj.getkenhId()+"' \n";
		}

		query +=
			"				inner join ctkhuyenmai ctkm on ctkm.pk_seq = dhkm.ctkmid \n" +
			" 		 		where dhkm.trangthai<>2  and dhkm.ngaynhap >='"+tungay+"' and dhkm.ngaynhap <= '"+denngay+"' \n";
		if (obj.getNhomskusId().length() > 0) {
			query += "\n and nsp.pk_seq = '" + obj.getNhomskusId() + "' \n";
			//query += " and nsp.PK_SEQ in (select NHOMSKUS_FK from NHANVIEN_NHOMSKUS where nhanvien_fk ='" + obj.getuserId() + "' )";

		}
		/* if(st.length()>0)
				    query= query + st;*/
		query=query+  " ) dh \n"+
		" INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = DH.NPP_FK \n" +
		" inner join kho on kho.pk_seq  = dh.kho_fk ";
		if(obj.getLoaiNppId().length()>0)
		{
			query+=" and NPP.LoaiNPP="+obj.getLoaiNppId()+" \n";
		}

		query+=" left join sanpham sp on dh.spma=sp.ma  \n"+
		" left join nhomsanpham_sanpham nhsp on nhsp.sp_fk = sp.pk_seq \n" +
		" left join nhomsanpham nsp on nsp.pk_seq = nhsp.nsp_fk and nsp.type = 0 \n";
		if(obj.getnhomspct().equals("1"))
		{
			query += "  left join NHOMSANPHAMChiTieu_SANPHAM nctsp on nctsp.SP_FK = sp.PK_SEQ \n"+
			"	left join NHOMSANPHAMCHITIEU nspct on nspct.PK_SEQ = nctsp.NSP_FK \n";
		}
		query +=" left join ctkhuyenmai ctkm on ctkm.pk_seq = dh.ctkmid	 \n"+	
		" inner join khachhang kh on kh.pk_seq = dh.khachhang_fk  \n" +
		" left join loaicuahang ch on ch.pk_seq = kh.lch_fk	\n"+
		" inner join daidienkinhdoanh ddkd on ddkd.pk_seq = dh.ddkd_fk "+ 
		"\n left join DDKD_GSBH ddgs on ddgs.ddkd_fk = ddkd.pk_seq "+ 
		"\n left join giamsatbanhang gsbh on gsbh.pk_seq = ddgs.gsbh_fk "+	
		" inner join KHUVUC kv on kv.PK_SEQ = NPP.KHUVUC_FK \n"+
		" inner join VUNG v on v.PK_SEQ = kv.VUNG_FK "+
		"\n left join DONVIDOLUONG dvdl on sp.DVDL_FK = dvdl.PK_SEQ" +
		"\n left join QUYCACH qc on qc.SANPHAM_FK = sp.PK_SEQ and qc.DVDL1_FK = dvdl.PK_SEQ and qc.DVDL2_FK ='100018' " +
		" where 1=1 \n";

		if(st_v.length() > 0)
			query= query + st_v;
		query= query +
		"	UNION ALL      \n"+
		"	select '' as TinhThanh,'' as QuanHuyen,v.TEN as TENVUNG, kv.TEN as TENKHUVUC, npp.SITECODE as nppid,npp.ten as nppten,dh.ngaynhap as ngayhoadon,ddkd.pk_seq as nvbhid, \n"+ 
		"		ddkd.SMARTID as MaNVBH, ddkd.ten as nvbhten,kh.smartid as makh,kh.ten as tenkh,kh.diachi as diachi,dh.pk_seq as dhid,sp.ma as masp, \n"+ 
		"		sp.ten as tensp, isnull(sp.ma_ddt,'') maddt, isnull(dh.soluong,0) as soluong  ,0 as dongia,0 as chietkhau,isnull(dh.soluong,0)* isnull(sp.trongluong,0) as sanluong,0 as tongtien, \n"+
		"		dh.tonggiatri as tienkhuyenmai ,ctkm.scheme as scheme,dh.trangthai, \n"+
		"		isnull((select lnpp.ma from loainpp lnpp where lnpp.pk_seq=npp.loainpp),'n/a') as maloainpp, isnull(sp.tenviettat,'') tentatsp, \n"+ 
		"		isnull(dh.chietkhau,0) as chietkhauTT,'' as TG,kho.ten  as kho ,'' as NhanHang,'' as ChungLoai,'' as NguoiChot,'' ngaychot, '' gio, TBH, 0 as SOHD, 0 as SLSP, case when loaidh = 1 then 'PDA' else 'WEB' end as loaidh "+
		"\n  	,case when FlagModified = 1 then N'đã xử lý' else N'chưa xử lý' end as FlagModified, kbh, ch.diengiai as loaich, '' as nhomsp "+
		"\n 	,isnull(dh.soluong,0)/qc.soluong1 as slthung, gsbh.ten as gsbh , gsbh.smartid as magsbh, case when dh.isdungtuyen = 1 then N'Đúng tuyến' when dh.isdungtuyen = 0 then N'Trái tuyến' else N' ' end isdungtuyen  " +
		"\n  , isnull(dh.nhomskus,'') as nhomskus ";
		if(obj.getnhomspct().equals("1"))
		{
			query += " , nspct.DIENGIAI as nhomchitieu ";
		}
		query +=", dh.ghichu	" +
		"			from  \n"+
		"					(	 \n"+
		"					select 0 as chietkhau, dh.pk_seq,	dh.ngaynhap,dh.ddkd_fk,dh.khachhang_fk,dh.npp_fk,dh.gsbh_fk,dh.kho_fk,dh.kbh_fk,dh_sp1.SANPHAM_FK , \n"+ 
		"					dh_sp1.cttb_fk,(-1)*dh_sp1.soluong as soluong,(-1)* dh_sp1.tonggiatri as tonggiatri,  \n"+
		"					(select ten from TrangThaiNghiepVu tt where dh.trangthai=tt.pk_Seq and TableName='DonHangTraVe')  as trangthai, '' as TBH, '' as loaidh, '' as FlagModified , '' as kbh, '' isdungtuyen \n"+
		"					,  nsp.TEN as nhomskus, '' ghichu \n" +					
		"					from donhangtrave dh   \n"+
		"					inner  join donhang_cttb_tratb  dh_sp1 on dh.DONHANG_FK=dh_sp1.DONHANG_FK \n"+ 
		" 				left join NHOMSKUS_SANPHAM ssp on ssp.SP_FK = dh_sp1.SANPHAM_FK \n" + 
		"				left join NHOMSKUS nsp on nsp.PK_SEQ=ssp.NSKUS_FK \n" +
		" 	 				where dh.trangthai=3  and dh.ngaynhap >='"+tungay+"' and dh.ngaynhap <= '"+denngay+"'  \n";
		if (obj.getNhomskusId().length() > 0) {
			query += "\n and nsp.pk_seq = '" + obj.getNhomskusId() + "' \n";
			//query += " and nsp.PK_SEQ in (select NHOMSKUS_FK from NHANVIEN_NHOMSKUS where nhanvien_fk ='" + obj.getuserId() + "' )";

		}
		/*   if(st.length()>0)
			    query= query + st+*/
		query += "					union all   \n"+
		" select  dhtb.chietkhau,dhtb.pk_seq,	dhtb.ngaynhap,dhtb.ddkd_fk,dhtb.khachhang_fk,dhtb.npp_fk,dhtb.gsbh_fk,dhtb.kho_fk,dhtb.kbh_fk,dh_sp.sanpham_fk , \n"+
		" dh_sp.cttb_fk,dh_sp.soluong as soluong,dh_sp.tonggiatri , \n"+
		"	(select ten from TrangThaiNghiepVu tt where dhtb.trangthai=tt.pk_Seq and TableName='DonHang') 	as trangthai, isnull(t.diengiai, '') as TBH, dhtb.isPDA as loaidh, dhtb.FlagModified, kbh.diengiai as kbh, dhtb.isdungtuyen as isdungtuyen \n"+
		"	,  nsp.TEN as nhomskus, dhtb.ghichu \n" +
		"		 			from  \n"+
		"		 			dhtb  \n"+  
		" 					left join TUYENBANHANG t on t.pk_seq = dhtb.tbh_fk \n" +
		"					inner join kenhbanhang kbh on kbh.pk_seq = dhtb.kbh_fk \n";
		if(obj.getkenhId().length()>0)
		{
			query += "and kbh.pk_seq ='"+obj.getkenhId()+"' ";
		}
		query += "		 	inner join  \n"+
		"		 	 		donhang_cttb_tratb  dh_sp on dhtb.pk_seq=dh_sp.donhang_fk \n"+  
		"				left join NHOMSKUS_SANPHAM ssp on ssp.SP_FK = dh_sp.SANPHAM_FK \n" +
		"				left join NHOMSKUS nsp on nsp.PK_SEQ=ssp.NSKUS_FK \n" +
		" 		 			where dhtb.trangthai<>2  and dhtb.ngaynhap >='"+tungay+"' and dhtb.ngaynhap <= '"+denngay+"' \n";
		if (obj.getNhomskusId().length() > 0) {
			query += "\n and nsp.pk_seq = '" + obj.getNhomskusId() + "' \n";
		}
		//query += " and nsp.PK_SEQ in (select NHOMSKUS_FK from NHANVIEN_NHOMSKUS where nhanvien_fk ='" + obj.getuserId() + "' )";
		/* if(st.length()>0)
			    query= query + st;*/
		query=query+  
		"	) dh  "+
		" inner join nhaphanphoi npp on npp.pk_seq = dh.npp_fk \n"+ 
		" left join sanpham sp on dh.sanpham_fk=sp.pk_seq \n"+
		" left join nhomsanpham_sanpham nhsp on nhsp.sp_fk = sp.pk_seq  \n" +
		" left join nhomsanpham nsp on nsp.pk_seq = nhsp.nsp_fk  and nsp.type = 0  \n";
		if(obj.getnhomspct().equals("1"))
		{
			query += "  inner join NHOMSANPHAMChiTieu_SANPHAM nctsp on nctsp.SP_FK = sp.PK_SEQ \n"+
			"	inner join NHOMSANPHAMCHITIEU nspct on nspct.PK_SEQ = nctsp.NSP_FK \n";
		}
		query +=" left join cttrungbay ctkm on ctkm.pk_seq = dh.cttb_fk \n"+	 	
		" inner join khachhang kh on kh.pk_seq = dh.khachhang_fk  \n"+
		" left join loaicuahang ch on ch.pk_seq = kh.lch_fk	\n"+
		" inner join daidienkinhdoanh ddkd on ddkd.pk_seq = dh.ddkd_fk "+   
		"\n left join DDKD_GSBH ddgs on ddgs.ddkd_fk = ddkd.pk_seq "+ 
		"\n left join giamsatbanhang gsbh on gsbh.pk_seq = ddgs.gsbh_fk "+	
		"\n left join DONVIDOLUONG dvdl on sp.DVDL_FK = dvdl.PK_SEQ" +
		"\n left join QUYCACH qc on qc.SANPHAM_FK = sp.PK_SEQ and qc.DVDL1_FK = dvdl.PK_SEQ and qc.DVDL2_FK ='100018' " +
		"\n inner join KHUVUC kv on kv.PK_SEQ = NPP.KHUVUC_FK "+
		"\n inner join VUNG v on v.PK_SEQ = kv.VUNG_FK "+
		"\n inner join kho kho on kho.pk_seq = dh.kho_fk "+
		"\n where 1=1 ";

		if(st_v.length() > 0)
			query= query + st_v;

		query +=    "\n UNION ALL " +      
		"\n select tt.TEN as TinhThanh,qh.TEN as QuanHuyen,v.TEN as TENVUNG, kv.TEN as TENKHUVUC, npp.SITECODE as nppid,npp.ten as nppten,dh.ngaynhap as ngayhoadon,ddkd.pk_seq as nvbhid,  " +      
		"\n 	ddkd.SMARTID as MaNVBH, ddkd.ten as nvbhten,kh.smartid as makh,kh.ten as tenkh,kh.diachi as diachi,dh.DONHANG_FK as dhid, '' as masp,  '' as maddt,  " +      
		"\n 	'' as tensp,0 as soluong  ,0 as dongia,0 as chietkhau,0 as sanluong,0 as tongtien,  " +      
		"\n 	0 as tienkhuyenmai ,'' as scheme, (select ten from TrangThaiNghiepVu tt where dh.trangthai=tt.pk_Seq and TableName='donhang')  as trangthai,  " +      
		"\n 	isnull((select lnpp.ma from loainpp lnpp where lnpp.pk_seq=npp.loainpp),'n/a') as maloainpp, '' as tentatsp,  " +      
		"\n 	0 as chietkhauTT,cast(Round(cast(DATEDIFF(MINUTE,dh.NgayGio,dh.ngaychot)/60.0 as numeric(18,1) ),1) as varchar(max)) as TG, kho.ten as kho , " +      
		"\n 	'' AS NhanHang, '' as ChungLoai, dh.nguoichot as NguoiChot,NgayChot,  " +      
		"\n 	Case when DATEPART(HOUR, NGAYCHOT) < 13 then N'Sáng' else N'Chiều' end as Gio, TBH, 1 AS SOHD, 0 as SLSP, case when loaidh = 1 then 'PDA' else 'WEB' end as loaidh " +  
		"\n  	,case when FlagModified = 1 then N'đã xử lý' else N'chưa xử lý' end as FlagModified, kbh, ch.diengiai as loaich, '' as nhomsp "+
		"\n 	,'' as slthung, gsbh.ten as gsbh, gsbh.smartid as magsbh, case when dh.isdungtuyen = 1 then N'Đúng tuyến' when dh.isdungtuyen = 0 then N'Trái tuyến' else N' ' end isdungtuyen  " +
		"\n , '' as nhomskus, dh.ghichu " +
		"\n from   " +      
		"\n (	  " +      
		"\n 	SELECT dhtb.PK_SEQ AS DONHANG_FK,dhtb.NgayGio, dhtb.NGAYCHOT, nv.TEN as NGUOICHOT,isnull(t.diengiai, '') as TBH, dhtb.DDKD_FK , dhtb.NGAYNHAP, dhtb.TRANGTHAI, dhtb.NPP_FK , dhtb.KHACHHANG_FK , dhtb.KHO_FK , (SELECT COUNT(SANPHAM_FK) FROM DONHANG_SANPHAM WHERE DONHANG_FK = dhtb.PK_SEQ) AS SLSP, '' AS MASP, 0 AS SL, 0 AS DONGIA, dhtb.isPDA as loaidh, dhtb.FlagModified as FlagModified, '' CHUNGLOAI, kbh.diengiai as kbh, dhtb.isdungtuyen as isdungtuyen, dhtb.ghichu " +      
		"\n 	FROM dhtb " +      
		"\n 	left join TUYENBANHANG t on t.pk_seq = dhtb.tbh_fk" +
		"\n 	inner join kenhbanhang kbh on kbh.pk_seq = dhtb.kbh_fk ";
		if(obj.getkenhId().length()>0)
		{
			query += "and kbh.pk_seq ='"+obj.getkenhId()+"' ";
		}
		query += "\n 	left join nhanvien nv on nv.Pk_seq = dhtb.nguoichot  "+
		"\n 	where dhtb.trangthai<>2  and dhtb.ngaynhap >= '"+ tungay +"' and dhtb.ngaynhap <= '"+ denngay +"'  ";
		/* if(st.length()>0)
				query= query + st;*/
		query = query +
		"\n ) dh  	 " +      
		"\n inner join nhaphanphoi npp on npp.pk_seq = dh.npp_fk  " +      
		"\n inner join khachhang kh on kh.pk_seq = dh.khachhang_fk   " + 
		"\n left join loaicuahang ch on ch.pk_seq = kh.lch_fk	"+
		"\n inner join daidienkinhdoanh ddkd on ddkd.pk_seq = dh.ddkd_fk  " +  	
		"\n left join DDKD_GSBH ddgs on ddgs.ddkd_fk = ddkd.pk_seq "+ 
		"\n left join giamsatbanhang gsbh on gsbh.pk_seq = ddgs.gsbh_fk "+	
		"\n inner join KHUVUC kv on kv.PK_SEQ = NPP.KHUVUC_FK  " +      
		"\n left join TINHTHANH tt on tt.PK_SEQ=kh.TINHTHANH_FK  " +      
		"\n left join QUANHUYEN qh on qh.PK_SEQ=kh.QUANHUYEN_FK  " +      
		"\n inner join VUNG v on v.PK_SEQ = kv.VUNG_FK  " +      
		"\n  inner join kho kho on kho.pk_seq = dh.kho_fk  ";
		if (obj.getNhomskusId().length() > 0) {
			query += "\n where 1!=1";
		} else {
			query += "\n where 1=1";
		}
		if(st_v.length() > 0)
			query = query + st_v;

		query = query + " order by dhId, scheme	";

		System.out.println("Query BC đơn hàng bán trong kỳ: " + query);
		return query;
	}


	private String setQueryIP(Ireport obj,String tungay,String denngay,String st, String st_v) 
	{


		String query =
			"\n 	select v.TEN as TENVUNG, kv.TEN as TENKHUVUC, NPP.SITECODE AS NPPID,NPP.TEN AS NPPTEN,dh.NGAYNHAP AS ngayhoadon ,ddkd.pk_seq as nvbhId,ddkd.ten as nvbhTen,kh.smartid as makh, \n"+
			"\n    		kh.ten as tenkh, kh.diachi as diachi,dh.pk_seq as dhId \n"+
			"\n    		 			,sp.ma as masp, isnull(sp.ma,'') maddt ,sp.ten as tensanpham, isnull(dh.soluong,0) as soluong, \n"+
			"\n    		 isnull(dh.giamua,0) as dongia,isnull(dh.chietkhaunpp,0) as chietkhaunpp , \n"+
			"\n    		 isnull(dh.soluong,0)* isnull(sp.trongluong,0)  as sanluong , \n"+
			"\n    		 round(isnull(dh.soluong,0)*isnull(dh.giamua,0),0)   as tongtien, 0 as tienkhuyenmai , '' as scheme, dh.trangthai, \n"+
			"\n    		 isnull((select lnpp.Ma from LoaiNPP lnpp where lnpp.pk_seq=NPP.LoaiNPP),'N/A') as maloainpp, isnull(sp.tenviettat,'') as tentatsp, \n"+ 
			"\n    			0 chietkhauTT,cast(Round(cast(DATEDIFF(MINUTE,NgayGio,ngaychot)/60.0 as numeric(18,1) ),1) as varchar(max)) as TG \n"+
			"\n    		  from  	"+
			"\n    		  ( "+
			" select \n"+
			" dh.pk_seq,	dh.ngaynhap,dh.ddkd_fk,dh.khachhang_fk,dh.npp_fk,dh.gsbh_fk,dh.kho_fk,dh.kbh_fk,dh_sp.sanpham_fk, \n"+ 
			" (DH_SP.giamua ) giamua ,dh_sp.soluong,dh_sp.chietkhau + ISNULL(CK.ptchietkhau,0) CHIETKHAUNPP, \n"+
			"  case when dh.trangthai = 0 then N'Chưa duyệt' when dh.trangthai = 9 then N'PDA' when dh.trangthai = 1 then N'Đã duyệt' end  as trangthai,NGAYCHOT,NgayGio \n"+
			" from  donhangIP dh 		inner join \n"+
			" DONHANG_SANPHAMSPIP  dh_sp on dh.pk_seq=dh_sp.donhang_fk \n"+ 
			" left join KHACHHANG_SANPHAMCK CK on DH.KHACHHANG_FK = CK.khachhang_fk and DH.KBH_FK = CK.KENHBANHANG_FK and DH_SP.SANPHAM_FK = CK.sanpham_fk  \n"+
			" where dh.trangthai<>2  and dh.ngaynhap >='"+tungay+"' and dh.ngaynhap <= '"+denngay+"' \n" ; 

		query=query+	" ) dh \n"+
		"left JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = DH.NPP_FK \n";

		query+=" inner join khachhang kh on kh.pk_seq = dh.khachhang_fk \n"+ 
		" inner join sanpham sp on sp.pk_seq = dh.sanpham_fk   \n"+
		" inner join daidienkinhdoanh ddkd on ddkd.pk_seq = dh.ddkd_fk \n"+	
		" left join KHUVUC kv on kv.PK_SEQ = NPP.KHUVUC_FK \n"+
		" left join VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+
		" where 1=1 ";


		System.out.println("BC Don Hang Ban IP day nay: " + query);
		return query;

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

		Ireport obj = new Report();
		obj.setuserId(userId);
		obj.setView(view);
		obj.init();	  
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);

		if(!view.equals("TT"))
		{
			String nextJSP = "/AHF/pages/Center/BCDonHangBanTrongKyNPP.jsp";
			response.sendRedirect(nextJSP);
		}
		else
		{
			obj.setnppId("");
			String nextJSP = "/AHF/pages/Center/BCDonHangBanTrongKyNPP.jsp";
			response.sendRedirect(nextJSP);
		}
	}


	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		Ireport obj = new Report();
		Utility util = new Utility();

		String userId = (String) session.getAttribute("userId");
		if (userId == null)
			userId = "";
		obj.setuserId(userId);

		String userTen = (String) session.getAttribute("userTen");
		obj.setuserTen(userTen);
		String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
		if (nppId == null)
			nppId = "";
		obj.setnppId(nppId);

		String loainppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loainppId")));
		if(loainppId == null)
			loainppId = "";
		obj.setLoaiNppId(loainppId);


		String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		obj.setView(view);
		if(view != null && !view.equals("TT")) {
			nppId = util.getIdNhapp(userId);
			obj.setnppId(nppId);
		}

		String vungId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")));
		if (vungId == null)
			vungId = "";
		obj.setvungId(vungId);

		String khuvucId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")));
		if (khuvucId == null)
			khuvucId = "";
		obj.setkhuvucId(khuvucId);

		String kenhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")));
		if (kenhId == null)
			kenhId = "";
		obj.setkenhId(kenhId);

		String nhomskusId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhomskusId")));
		if (nhomskusId == null)
			nhomskusId = "";
		obj.setNhomskusId(nhomskusId);

		System.out.println("Nha Phan Phoi ID : "+obj.getnppId());
		String ddkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdId")));
		if (ddkdId == null)
			ddkdId = "";
		obj.setddkdId(ddkdId);

		String khachhangId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khachhangId")));
		if (khachhangId == null)
			khachhangId = "";
		obj.setkhachhangId(khachhangId);

		String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays")));
		if (tungay == null)
			tungay = "";
		obj.settungay(tungay);

		String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays")));
		if (denngay == null)
			denngay = "";
		obj.setdenngay(denngay);

		if(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhomspct")))!= null)
			obj.setnhomspct("1");
		else
			obj.setnhomspct("0");

		/////////// set vo bean  o tren






		String st = "";
		String st_loc = "";
		String st_v = "";

		if(vungId.trim().length() > 0)
			st_v += " and v.pk_seq = "+ vungId +"  ";
		if(khuvucId.trim().length() > 0)
			st_v += " and kv.pk_seq = "+ khuvucId +"  ";		
		if(nppId.length() > 0)
		{
			st += " and dh.npp_fk ='" + nppId + "'";
			st_loc += " and a.npp_fk ='" + nppId + "'";
		}
		if (ddkdId.length() > 0)
		{
			st += " and dh.ddkd_fk ='" + ddkdId + "'";
			st_loc += " and a.ddkd_fk ='" + ddkdId + "'";
		}
		if (khachhangId.length() > 0)
		{
			st += " and dh.khachhang_fk ='" + khachhangId + "'";
			st_loc += " and a.khachhang_fk ='" + khachhangId + "'";
		}

		if(view.equals("TT")){
			st += " and dh.npp_fk in " + util.quyen_npp(obj.getuserId()) + " and dh.kbh_fk in  " + util.quyen_kenh(obj.getuserId());

			st_loc += " and a.npp_fk in " + util.quyen_npp(obj.getuserId()) + " and a.kbh_fk in  " + util.quyen_kenh(obj.getuserId());
		}

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));

		if (action.equals("taoKoPivot")) 
		{
			try 
			{
				System.out.println("dia chi: " + getServletContext().getInitParameter("pathBaoCao"));
				String query = setQuery_KhongPiVot(obj, view);				

				//System.out.println("QUERY: "+query);
				String filename = this.getDateTime();
				File theDir = new File(getServletContext().getInitParameter("pathBaoCao") + "\\BCDonHangBanTrongKyKhongPivot"+filename);
				theDir.mkdir();
				CreatePivotTableKhongPivot(obj, filename);
				System.out.println("Error Here : "+this.getDateTime());

				try {
					System.out.println("path: " + getServletContext().getInitParameter("pathBaoCao") + "\\BCDonHangBanTrongKyKhongPivot"+filename+"\\");
					System.out.println("file name: " + "BCDonHangBanTrongKyKhongPivot"+filename);
					 
					WebService.CallAPI("BC_KhongSort", "A10", getServletContext().getInitParameter("pathBaoCao") + "\\BCDonHangBanTrongKyKhongPivot"+filename+"\\","BCDonHangBanTrongKyKhongPivot"+filename,".xlsm", query);
				}
				catch (Exception  e)
				{
					e.printStackTrace();
				}
				
				obj.setuserId(userId);
				obj.init();	 
				obj.setUrl(getServletContext().getInitParameter("pathBaoCao")  +"/BCDonHangBanTrongKyKhongPivot"+filename+"\\"+"BCDonHangBanTrongKyKhongPivot"+filename+".xlsm");
				obj.setKey(getServletContext().getInitParameter("pathBaoCao")   +"\\BCDonHangBanTrongKyKhongPivot"+filename+"\\success.txt");
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);

				if(!view.equals("TT"))
				{
					String nextJSP = "/AHF/pages/Center/BCDonHangBanTrongKyNPP.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					String nextJSP = "/AHF/pages/Center/BCDonHangBanTrongKyNPP.jsp";
					response.sendRedirect(nextJSP);
				}

				return;

			} 
			catch (Exception ex) 
			{
				ex.printStackTrace();
				System.out.println("Error Here : "+ex.toString());
				obj.setMsg(ex.getMessage());
				request.getSession().setAttribute("errors", ex.getMessage());
			}
		}
		else
			if (action.equals("tao")) 
			{
				try 
				{
					System.out.println("dia chi: " + getServletContext().getInitParameter("pathBaoCao"));
					String query = setQuery(obj, tungay, denngay, st, st_v,st_loc);
					//System.out.println("QUERY: "+query);
					String filename=this.getDateTime();
					File theDir = new File(getServletContext().getInitParameter("pathBaoCao")   +"\\BCDonHangBanTrongKyNPP"+filename);
					theDir.mkdir();
					CreatePivotTable(obj,filename);
					System.out.println("Error Here : "+this.getDateTime());

					try {
						WebService.BCDonHangBanTrongKy(getServletContext().getInitParameter("pathBaoCao")   +"\\BCDonHangBanTrongKyNPP"+filename+"\\","BCDonHangBanTrongKyNPP"+filename,".xlsm", query);

					}
					catch(Exception  e)
					{
						e.printStackTrace();
					}
					obj.setuserId(userId);
					obj.init();	 
					obj.setUrl(getServletContext().getInitParameter("pathBaoCao")  +"/BCDonHangBanTrongKyKhongPivot"+filename+"\\"+"BCDonHangBanTrongKyKhongPivot"+filename+".xlsm");
					obj.setKey(getServletContext().getInitParameter("pathBaoCao")   +"\\BCDonHangBanTrongKyKhongPivot"+filename+"\\success.txt");
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);

					if(!view.equals("TT"))
					{
						String nextJSP = "/AHF/pages/Center/BCDonHangBanTrongKyNPP.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{
						String nextJSP = "/AHF/pages/Center/BCDonHangBanTrongKyNPP.jsp";
						response.sendRedirect(nextJSP);
					}

					return;
				} 
				catch (Exception ex) 
				{
					ex.printStackTrace();
					System.out.println("Error Here : "+ex.toString());
					obj.setMsg(ex.getMessage());
					request.getSession().setAttribute("errors", ex.getMessage());
				}
			}
			else if(action.equals("taonew"))
			{

				try 
				{
					System.out.println("dia chi: " + getServletContext().getInitParameter("pathBaoCao"));
					String query = "select * from BCDONHANGBANTRONGKI where ngayhoadon >='"+tungay+"' and ngayhoadon<='"+denngay+"' ";
					System.out.println("QUERY ::::: "+query);
					String filename=this.getDateTime();
					File theDir = new File(getServletContext().getInitParameter("pathBaoCao")   +"\\BCDonHangBanTrongKyNPP"+filename);
					theDir.mkdir();
					CreatePivotTable(obj,filename);
					System.out.println("Error Here : "+this.getDateTime());

					try {
						WebService.BCDonHangBanTrongKy(getServletContext().getInitParameter("pathBaoCao")   +"\\BCDonHangBanTrongKyNPP"+filename+"\\","BCDonHangBanTrongKyNPP"+filename,".xlsm", query);

					}catch(Exception  e)
					{

					}
					obj.setuserId(userId);
					obj.init();	 
					obj.setUrl(getServletContext().getInitParameter("pathServicesFile")   +"/BCDonHangBanTrongKyNPP"+filename+"\\"+"BCDonHangBanTrongKyNPP"+filename+".xlsm");
					obj.setKey(getServletContext().getInitParameter("pathBaoCao")   +"\\BCDonHangBanTrongKyNPP"+filename+"\\success.txt");
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);

					if(!view.equals("TT"))
					{
						String nextJSP = "/AHF/pages/Center/BCDonHangBanTrongKyNPP.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{
						String nextJSP = "/AHF/pages/Center/BCDonHangBanTrongKyNPP.jsp";
						response.sendRedirect(nextJSP);
					}


					return;

				} 
				catch (Exception ex) 
				{
					ex.printStackTrace();
					System.out.println("Error Here: " + ex.toString());
					obj.setMsg(ex.getMessage());
					request.getSession().setAttribute("errors", ex.getMessage());
				}

			}
			else if (action.equals("tao2")) 
			{
				try 
				{
					request.setCharacterEncoding("utf-8");

					response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition", "attachment; filename=BCDonHangBanIP.xlsm");

					OutputStream out = response.getOutputStream();

					String query = setQueryIP(obj, tungay, denngay, st, st_v);

					CreatePivotTable2(out, obj, query);
				} 
				catch (Exception ex) 
				{
					ex.printStackTrace();
					System.out.println("Error Here : "+ex.toString());
					obj.setMsg(ex.getMessage());
					request.getSession().setAttribute("errors", ex.getMessage());
				}
			}else
			{
				obj.setuserId(userId);
				obj.init();	    
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);

				if(!view.equals("TT"))
				{
					String nextJSP = "/AHF/pages/Center/BCDonHangBanTrongKyNPP.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					String nextJSP = "/AHF/pages/Center/BCDonHangBanTrongKyNPP.jsp";
					response.sendRedirect(nextJSP);
				}
			}
	}

	public String getBaseUrl(HttpServletRequest request) {
		String scheme = "://";
		String serverName = request.getServerName();
		String serverPort = (request.getServerPort() == 80) ? "" : ":" + request.getServerPort();
		String contextPath = request.getContextPath();
		return scheme + serverName + serverPort + contextPath;
	}

	public void createExcel(Ireport obj,String query)
	{
		ResultSet rs = obj.getDb().get(query);
	}

	private void CreatePivotTableKhongPivot(Ireport obj,String filename) throws Exception {

		OutputStream out;
		try 
		{				
			out = new FileOutputStream(getServletContext().getInitParameter("pathBaoCao")   +"\\BCDonHangBanTrongKyKhongPivot"+filename+"\\BCDonHangBanTrongKyKhongPivot"+filename+".xlsm");
			FileInputStream fstream = new FileInputStream( getServletContext().getInitParameter("path") + "\\BCDonHangBanTrongKyKhongPivot.xlsm");
			Workbook workbook = new Workbook();
			try{
				workbook.open(fstream);
				workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
				CreateHeaderKhongPivot(workbook, obj); 
			}
			catch (Exception e) {
				fstream.close();
			}

			workbook.save(out);
			fstream.close();
			out.close();
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error Here : "+ex.toString());
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}
	private void CreatePivotTable(Ireport obj,String filename) throws Exception {

		OutputStream out;
		try 
		{				
			out = new FileOutputStream(getServletContext().getInitParameter("pathBaoCao")   +"\\BCDonHangBanTrongKyNPP"+filename+"\\BCDonHangBanTrongKyNPP"+filename+".xlsm");
			FileInputStream fstream = new FileInputStream( getServletContext().getInitParameter("path") + "\\BCDonHangBanTrongKyNPP.xlsm");
			Workbook workbook = new Workbook();
			try{
				workbook.open(fstream);
				workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
				CreateHeader(workbook,obj,filename+".csv"); 
			}catch (Exception e){
				fstream.close();
			}

			workbook.save(out);
			fstream.close();
			out.close();
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error Here : "+ex.toString());
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}
	private void CreatePivotTable2(OutputStream out,Ireport obj, String query) throws Exception {
		try 
		{
			FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BCDonHangBanIP.xlsm");
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

			CreateHeader2(workbook,obj); 
			FillData2(workbook, query, obj);			
			workbook.save(out);
			fstream.close();
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error Here : "+ex.toString());
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}

	private void CreateHeaderKhongPivot(Workbook workbook,Ireport obj) 
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);	    
		worksheet.setName("Sheet1");
		Cells cells = worksheet.getCells();	 

		cells.setRowHeight(0, 20.0f);	    
		Cell cell = cells.getCell("A1");	
		ReportAPI.getCellStyle(cell,Color.RED, true, 16, "Báo Cáo Đơn Hàng Bán Trong Kỳ");
		cell = cells.getCell("A2");
		ReportAPI.getCellStyle(cell,Color.NAVY,true,10,"Từ ngày: " + obj.gettungay() + "  Đến ngày : " + obj.getdenngay());
		cell = cells.getCell("A3");
		ReportAPI.getCellStyle(cell,Color.NAVY,true,10,"Ngày tạo : " + this.getDateTime()); 
		cell = cells.getCell("A4");
		ReportAPI.getCellStyle(cell,Color.NAVY,true,10,"Người tạo : " + obj.getuserTen());		
	}
	
	private void CreateHeader(Workbook workbook,Ireport obj,String filename) 
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);	    
		worksheet.setName("Sheet1");
		Cells cells = worksheet.getCells();	 

		cells.setRowHeight(0, 20.0f);	    
		Cell cell = cells.getCell("A1");	
		ReportAPI.getCellStyle(cell,Color.RED, true, 16, "Báo Cáo Đơn Hàng Bán Trong Kỳ");
		cell = cells.getCell("A2");
		ReportAPI.getCellStyle(cell,Color.NAVY,true,10,"Từ ngày: " + obj.gettungay() + "  Đến ngày : " + obj.getdenngay());
		cell = cells.getCell("A3");
		ReportAPI.getCellStyle(cell,Color.NAVY,true,10,"Ngày tạo : " + this.getDateTime()); 
		cell = cells.getCell("A4");
		ReportAPI.getCellStyle(cell,Color.NAVY,true,10,"Người tạo : " + obj.getuserTen());


		cell = cells.getCell("AA7");		cell.setValue("Ngay Hoa Don");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AB7");		cell.setValue("Nhan Vien Ban Hang");	ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AC7");		cell.setValue("Ten Khach Hang");		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AD7");		cell.setValue("Ma Don Hang");			ReportAPI.setCellHeader(cell);

		cell = cells.getCell("AE7");		cell.setValue("Ten San Pham");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AF7");		cell.setValue("CTKM");					ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AG7");		cell.setValue("Ma Khach Hang");  		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AH7");		cell.setValue("Ma San Pham");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AI7");		cell.setValue("Dia Chi");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AJ7");		cell.setValue("Chiet Khau NPP");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AK7");		cell.setValue("So Luong");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AL7");		cell.setValue("Don Gia (VAT)");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AM7");		cell.setValue("Tong Tien");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AN7");		cell.setValue("Trang Thai");  			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AO7");		cell.setValue("Ma NPP");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AP7");		cell.setValue("Ten NPP");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AQ7");		cell.setValue("Ten San Pham (Viet Tat)");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AR7");		cell.setValue("Chiet Khau TT");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AS7");		cell.setValue("TienKhuyenMai");  				ReportAPI.setCellHeader(cell);
		//cell = cells.getCell("AT7");		cell.setValue("SanLuong");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AT7");		cell.setValue("MaLoaiNPP");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AU7");		cell.setValue("Ma San Pham");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AV7");		cell.setValue("Vung");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AW7");		cell.setValue("Khu Vuc");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AX7");		cell.setValue("Thoi Gian Chot(Tieng)");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AY7");		cell.setValue("KHO");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AZ7");		cell.setValue("TinhThanh");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BA7");		cell.setValue("QuanHuyen");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BB7");		cell.setValue("NhanHang");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BC7");		cell.setValue("ChungLoai");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BD7");		cell.setValue("NguoiChot");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BE7");		cell.setValue("Ngay Chot");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BF7");		cell.setValue("Gio");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BG7");		cell.setValue("Tuyen ban hang");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BH7");		cell.setValue("So Ma Don Hang");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BI7");		cell.setValue("So Ma San Pham");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BJ7");		cell.setValue("MaNVBH");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BK7");		cell.setValue("Loai DH");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BL7");		cell.setValue("Tinh Trang DH");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BM7");		cell.setValue("Kenh Ban Hang");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BN7");		cell.setValue("Loai Khach Hang");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BO7");		cell.setValue("Nhom San Pham");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BP7");		cell.setValue("So luong thung");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BQ7");		cell.setValue("Ma GSBH");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BR7");		cell.setValue("Ten GSBH");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BS7");		cell.setValue("Tuyen D/S");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BT7");		cell.setValue("NhomSKU");  				ReportAPI.setCellHeader(cell);
	}


	private void CreateHeader2(Workbook workbook,Ireport obj) 
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);	    
		worksheet.setName("Sheet1");
		Cells cells = worksheet.getCells();	 

		cells.setRowHeight(0, 20.0f);	    
		Cell cell = cells.getCell("A1");	
		ReportAPI.getCellStyle(cell,Color.RED, true, 16, "Báo Cáo Đơn Hàng Bán IP");
		cell = cells.getCell("A2");
		ReportAPI.getCellStyle(cell,Color.NAVY,true,10,"Từ ngày: " + obj.gettungay() + "  Đến ngày : " + obj.getdenngay());
		cell = cells.getCell("A3");
		ReportAPI.getCellStyle(cell,Color.NAVY,true,10,"Ngày tạo : " + this.getDateTime()); 
		cell = cells.getCell("A4");
		ReportAPI.getCellStyle(cell,Color.NAVY,true,10,"Người tạo : " + obj.getuserTen());


		cell = cells.getCell("AA1");		cell.setValue("Ngay Don Hang");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AB1");		cell.setValue("Nhan Vien Ban Hang");	ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AC1");		cell.setValue("Ten Khach Hang");		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AD1");		cell.setValue("Ma Don Hang");			ReportAPI.setCellHeader(cell);

		cell = cells.getCell("AE1");		cell.setValue("Ten San Pham");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AF1");		cell.setValue("CTKM");					ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AG1");		cell.setValue("Ma Khach Hang");  		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AH1");		cell.setValue("Ma San Pham");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AI1");		cell.setValue("Dia Chi");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AJ1");		cell.setValue("Chiet Khau NPP");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AK1");		cell.setValue("So Luong");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AL1");		cell.setValue("Don Gia (VAT)");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AM1");		cell.setValue("Tong Tien");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AN1");		cell.setValue("Trang Thai");  			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AO1");		cell.setValue("Ma NPP");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AP1");		cell.setValue("Ten NPP");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AQ1");		cell.setValue("Ten San Pham (Viet Tat)");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AR1");		cell.setValue("Chiet Khau TT");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AS1");		cell.setValue("TienKhuyenMai");  				ReportAPI.setCellHeader(cell);
		//cell = cells.getCell("AT1");		cell.setValue("SanLuong");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AT1");		cell.setValue("MaLoaiNPP");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AU1");		cell.setValue("Ma San Pham");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AV1");		cell.setValue("Vung");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AW1");		cell.setValue("Khu Vuc");  				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AX1");		cell.setValue("Thoi Gian Chot(Tieng)");  				ReportAPI.setCellHeader(cell);

	}


	private void FillData(Workbook workbook, String query, Ireport obj) throws Exception
	{
		ResultSet rs = null;
		dbutils db = new dbutils();
		try
		{	
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			Cells cells = worksheet.getCells();
			for(int i=26;i<=67 ;i++)
			{
				cells.hideColumn(i);
			}
			for(int i=0;i<15;++i)
			{
				cells.setColumnWidth(i, 15.0f);	    	
			}	
			rs = db.get(query);
			int index = 2;
			Cell cell = null;	    
			while (rs.next())
			{
				cell = cells.getCell("AA" + String.valueOf(index));		cell.setValue(rs.getString("ngayhoadon"));			
				cell = cells.getCell("AB" + String.valueOf(index));		cell.setValue(rs.getString("nvbhTen"));
				cell = cells.getCell("AC" + String.valueOf(index));		cell.setValue(rs.getString("tenkh"));
				cell = cells.getCell("AD" + String.valueOf(index));		cell.setValue(rs.getString("dhId"));
				cell = cells.getCell("AE" + String.valueOf(index));		cell.setValue(rs.getString("tensanpham"));
				cell = cells.getCell("AF" + String.valueOf(index));		cell.setValue(rs.getString("scheme"));
				cell = cells.getCell("AG" + String.valueOf(index));		cell.setValue(rs.getString("makh"));
				cell = cells.getCell("AH" + String.valueOf(index));		cell.setValue(rs.getString("masp"));
				cell = cells.getCell("AI" + String.valueOf(index));		cell.setValue(rs.getString("diachi"));
				cell = cells.getCell("AJ" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("chietkhauNPP")));
				cell = cells.getCell("AK" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("soluong")));
				cell = cells.getCell("AL" + String.valueOf(index));		cell.setValue((rs.getString("dongia")));
				cell = cells.getCell("AM" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("tongtien")));
				cell = cells.getCell("AN" + String.valueOf(index));		cell.setValue(rs.getString("trangthai"));
				cell = cells.getCell("AO" + String.valueOf(index));		cell.setValue(rs.getString("NPPID"));
				cell = cells.getCell("AP" + String.valueOf(index));		cell.setValue(rs.getString("NPPTEN"));	
				cell = cells.getCell("AQ" + String.valueOf(index));		cell.setValue(rs.getString("tentatsp"));
				cell = cells.getCell("AR" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("chietkhauTT")));
				cell = cells.getCell("AS" + String.valueOf(index));		cell.setValue(rs.getFloat("tienkhuyenmai"));	
				//cell = cells.getCell("AT" + String.valueOf(index));		cell.setValue(rs.getFloat("sanluong"));	
				cell = cells.getCell("AT" + String.valueOf(index));		cell.setValue(rs.getString("maloainpp"));
				cell = cells.getCell("AU" + String.valueOf(index));		cell.setValue(rs.getString("maddt"));
				cell = cells.getCell("AV" + String.valueOf(index));		cell.setValue(rs.getString("tenvung"));
				cell = cells.getCell("AW" + String.valueOf(index));		cell.setValue(rs.getString("tenkhuvuc"));
				cell = cells.getCell("AX" + String.valueOf(index));		cell.setValue(rs.getString("TG"));
				cell = cells.getCell("AY" + String.valueOf(index));		cell.setValue(rs.getString("kho"));
				cell = cells.getCell("AZ" + String.valueOf(index));		cell.setValue(rs.getString("TinhThanh"));
				cell = cells.getCell("BA" + String.valueOf(index));		cell.setValue(rs.getString("QuanHuyen"));
				cell = cells.getCell("BB" + String.valueOf(index));		cell.setValue(rs.getString("NhanHang"));
				cell = cells.getCell("BC" + String.valueOf(index));		cell.setValue(rs.getString("ChungLoai"));
				cell = cells.getCell("BD" + String.valueOf(index));		cell.setValue(rs.getString("NguoiChot"));
				cell = cells.getCell("BE" + String.valueOf(index));		cell.setValue(rs.getString("NgayChot"));
				cell = cells.getCell("BF" + String.valueOf(index));		cell.setValue(rs.getString("Gio"));
				cell = cells.getCell("BG" + String.valueOf(index));		cell.setValue(rs.getString("TBH"));
				cell = cells.getCell("BH" + String.valueOf(index));		cell.setValue(rs.getFloat("SOHD"));
				cell = cells.getCell("BI" + String.valueOf(index));		cell.setValue(rs.getFloat("SLSP"));
				cell = cells.getCell("BJ" + String.valueOf(index));		cell.setValue(rs.getString("MaNVBH"));
				cell = cells.getCell("BK" + String.valueOf(index));		cell.setValue(rs.getString("LOAIDH"));
				cell = cells.getCell("BL" + String.valueOf(index));		cell.setValue(rs.getString("FlagModified"));
				cell = cells.getCell("BM" + String.valueOf(index));		cell.setValue(rs.getString("Kbh"));
				cell = cells.getCell("BN" + String.valueOf(index));		cell.setValue(rs.getString("loaich"));
				cell = cells.getCell("BO" + String.valueOf(index));		cell.setValue(rs.getString("nhomsp"));
				cell = cells.getCell("BP" + String.valueOf(index));		cell.setValue(rs.getString("slthung"));
				cell = cells.getCell("BQ" + String.valueOf(index));		cell.setValue(rs.getString("magsbh"));
				cell = cells.getCell("BR" + String.valueOf(index));		cell.setValue(rs.getString("gsbh"));
				cell = cells.getCell("BS" + String.valueOf(index));		cell.setValue(rs.getString("isdungtuyen"));
				index++;
			}
			if(rs!=null){
				rs.close();
			}	

			ReportAPI.setHidden(workbook,15);

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			if(rs != null)
			{
				rs.close();
			}

			if(db != null)
				db.shutDown();

			throw new Exception(ex.getMessage());
		}
	}
	private void FillData2(Workbook workbook, String query, Ireport obj) throws Exception
	{
		ResultSet rs = null;
		dbutils db = new dbutils();
		try
		{	
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			Cells cells = worksheet.getCells();

			for(int i=0;i<15;++i)
			{
				cells.setColumnWidth(i, 15.0f);	    	
			}	
			rs = db.get(query);
			int index = 2;
			Cell cell = null;	    
			while (rs.next())
			{
				cell = cells.getCell("AA" + String.valueOf(index));		cell.setValue(rs.getString("ngayhoadon"));			
				cell = cells.getCell("AB" + String.valueOf(index));		cell.setValue(rs.getString("nvbhTen"));
				cell = cells.getCell("AC" + String.valueOf(index));		cell.setValue(rs.getString("tenkh"));
				cell = cells.getCell("AD" + String.valueOf(index));		cell.setValue(rs.getString("dhId"));
				cell = cells.getCell("AE" + String.valueOf(index));		cell.setValue(rs.getString("tensanpham"));
				cell = cells.getCell("AF" + String.valueOf(index));		cell.setValue(rs.getString("scheme"));
				cell = cells.getCell("AG" + String.valueOf(index));		cell.setValue(rs.getString("makh"));
				cell = cells.getCell("AH" + String.valueOf(index));		cell.setValue(rs.getString("masp"));
				cell = cells.getCell("AI" + String.valueOf(index));		cell.setValue(rs.getString("diachi"));
				cell = cells.getCell("AJ" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("chietkhauNPP")));
				cell = cells.getCell("AK" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("soluong")));
				cell = cells.getCell("AL" + String.valueOf(index));		cell.setValue((rs.getString("dongia")));
				cell = cells.getCell("AM" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("tongtien")));
				cell = cells.getCell("AN" + String.valueOf(index));		cell.setValue(rs.getString("trangthai"));
				cell = cells.getCell("AO" + String.valueOf(index));		cell.setValue(rs.getString("NPPID"));
				cell = cells.getCell("AP" + String.valueOf(index));		cell.setValue(rs.getString("NPPTEN"));	
				cell = cells.getCell("AQ" + String.valueOf(index));		cell.setValue(rs.getString("tentatsp"));
				cell = cells.getCell("AR" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("chietkhauTT")));
				cell = cells.getCell("AS" + String.valueOf(index));		cell.setValue(rs.getFloat("tienkhuyenmai"));	
				//cell = cells.getCell("AT" + String.valueOf(index));		cell.setValue(rs.getFloat("sanluong"));	
				cell = cells.getCell("AT" + String.valueOf(index));		cell.setValue(rs.getString("maloainpp"));
				cell = cells.getCell("AU" + String.valueOf(index));		cell.setValue(rs.getString("maddt"));
				cell = cells.getCell("AV" + String.valueOf(index));		cell.setValue(rs.getString("tenvung"));
				cell = cells.getCell("AW" + String.valueOf(index));		cell.setValue(rs.getString("tenkhuvuc"));
				cell = cells.getCell("AX" + String.valueOf(index));		cell.setValue(rs.getString("TG"));
				index++;
			}
			if(rs!=null){
				rs.close();
			}	

			ReportAPI.setHidden(workbook,15);

		}
		catch(Exception ex)
		{
			if(rs != null)
			{
				rs.close();
			}

			if(db != null)
				db.shutDown();

			throw new Exception(ex.getMessage());
		}
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	private String getPiVotName()
	{
		String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		String name = sdf.format(cal.getTime());
		name = name.replaceAll("-", "");
		name = name.replaceAll(" ", "_");
		name = name.replaceAll(":", "");
		return "_" + name;

	}
	public static void main(String[] args) {
		File theDir = new File("C:\\java-tomcat\\thongbao\\BCDonHangBanTrongKyNPP");
		theDir.mkdir();
	}
}
