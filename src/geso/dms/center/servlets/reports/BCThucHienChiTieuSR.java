package geso.dms.center.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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

@WebServlet("/BCThucHienChiTieuSR")
public class BCThucHienChiTieuSR extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public BCThucHienChiTieuSR()
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
		String nextJSP = "/AHF/pages/Center/BCThucHienChiTieuSR.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		

		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		Utility util = new Utility();

		obj.settungay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays"))));
		obj.setdenngay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays"))));

		obj.setuserId(userId != null ? userId : "");
		obj.setuserTen(userTen != null ? userTen : "");
		obj.setkenhId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId"))) != null ? util.antiSQLInspection(request
				.getParameter("kenhId")) : "");

		obj.setvungId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"))) != null ? util.antiSQLInspection(request
				.getParameter("vungId")) : "");

		obj.setkhuvucId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"))) != null ? util
				.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")) : ""));

		obj.setgsbhId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gsbhs"))) != null ? util.antiSQLInspection(request
				.getParameter("gsbhs")) : "");

		obj.setnppId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"))) != null ? util.antiSQLInspection(request
				.getParameter("nppId")) : "");

		obj.setdvkdId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId"))) != null ? util.antiSQLInspection(request
				.getParameter("dvkdId")) : "");

		obj.setnhanhangId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhanhangId"))) != null ? util
				.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhanhangId")) : ""));
		obj.setchungloaiId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chungloaiId"))) != null ? util
				.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chungloaiId")) : ""));
		
		String tuthang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tuthang"));
		if (tuthang == null)
			tuthang = "";
		obj.setFromMonth((tuthang.length() > 1 ? tuthang : "0" + tuthang));

		String denthang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denthang"));
		if (denthang == null)
			denthang = "";
		obj.setToMonth((denthang.length() > 1 ? denthang : "0" + denthang));

		String tunam = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tunam"));
		if (tunam == null)
			tunam = "";
		obj.setFromYear(tunam);

		String dennam = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dennam"));
		if (dennam == null)
			dennam = "";
		obj.setToYear(dennam);

		String[] fieldsHien = request.getParameterValues("fieldsHien");
		obj.setFieldShow(fieldsHien);

	
		String action = (String) util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));
		String nextJSP = "/AHF/pages/Center/BCThucHienChiTieuSR.jsp";
		try
		{
			if (action.equals("Taomoi"))
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=ThucHienChiTieuTT.xlsm");
				OutputStream out = response.getOutputStream();
				String query = setQuery(obj);
				CreatePivotTable(out, obj, query);
			}
		} catch (Exception ex)
		{
			obj.setMsg(ex.getMessage());
		}
		obj.init();
		session.setAttribute("obj", obj);
		session.setAttribute("userId", obj.getuserId());
		response.sendRedirect(nextJSP);
	}

	private String setQuery(IStockintransit obj)
	{
		String fromDate=obj.getFromYear()+'-'+obj.getFromMonth();
		String toDate=obj.getToYear()+'-'+obj.getToMonth();
		String sql = 
		"select kbh.ten as kenh,dvkd.donvikinhdoanh ,vung.ten as vung,kv.ten as khuvuc,npp.MA as MaNhaPhanPhoi,npp.ten as nhaphanphoi  ,        \n "+
		"ChiTieu.npp_fk,ChiTieu.ddkd_fk,ChiTieu.kenh_fk,ChiTieu.dvkd_fk, ddkd.ten as daidienkinhdoanh,ctNgaylamviec ,tdNgaylamviec,ctDonHang,tdDonhang,ctSanLuong, tdSanLuong,       \n "+
		"ChiTieu.ctDonHang,ChiTieu.ctDonHang as ctSanLuongDonHang,SanLuongDonHang.SoDonHangDatCtSanLuong,ChiTieu.ThoiGian     \n "+
		"from      \n "+ 
		"(      \n "+
		"	select ctnpp.nhapp_fk as npp_fk,ctnpp.kenh_fk,ctnpp.dvkd_fk,ctnpp.DDKD_FK,ctnpp.ThoiGian,ctnpp.ctNgaylamviec,ctDonHang,ctSanLuong,ctSanLuongDonHang     \n "+
		"	from     \n "+
		"	(     \n "+
		"		select ctnpp.nhapp_fk ,ctnpp.kenh_fk,ctnpp.dvkd_fk, ctnpp.ddkd_fk ,  ctnpp.songaylamviec as ctNgaylamviec, ctDonHang,ctSanLuongDonHang as ctSanLuongDonHang,sum(ctnpp.chitieu)as ctSanLuong  ,ctnpp.ThoiGian     \n "+
		"		from     \n "+
		"		(		     \n "+
		"			select ctnpp.nhapp_fk ,ctnpp.kenh_fk,ctnpp.dvkd_fk, b.ddkd_fk , ctnpp.songaylamviec ,a.SODONHANG,isnull(a.sanluongtrendh,0) as ctSanLuongDonHang ,ctnpp.songaylamviec*  a.sodonhang as ctDonHang ,b.chitieu ,cast(ctnpp.NAM AS varchar(4)) + '-' +case when THANG<10 then '0'+cast(ctnpp.THANG AS varchar(2)) else cast(ctnpp.THANG AS varchar(2)) end as ThoiGian     \n "+
		"			from chitieunpp_ddkd_nhomsp b inner join chitieunpp_ddkd a on  a.chitieunpp_fk=b.chitieunpp_fk and a.ddkd_fk=b.ddkd_fk       \n "+
		"					inner join chitieunpp ctnpp on ctnpp.pk_seq=b.chitieunpp_fk         \n "+
		"			where   trangthai<>'2'      \n "+
		"			and cast(ctnpp.NAM AS varchar(4)) + '-' +case when THANG<10 then '0'+cast(ctnpp.THANG AS varchar(2)) else cast(ctnpp.THANG AS varchar(2)) end  >='"+fromDate+"'     \n "+
		"			and cast(ctnpp.NAM AS varchar(4)) + '-' +case when THANG<10 then '0'+cast(ctnpp.THANG AS varchar(2)) else cast(ctnpp.THANG AS varchar(2)) end <='"+toDate+"'     \n "+
		"			) as ctnpp     \n "+
		"		group by  ctnpp.nhapp_fk ,ctnpp.kenh_fk,ctnpp.dvkd_fk,ctnpp.DDKD_FK,ctnpp.ThoiGian,ctnpp.songaylamviec,ctDonHang,ctnpp.ctSanLuongDonHang     \n "+
		"		) as ctnpp     \n "+
		"	)as ChiTieu     \n "+
		"	inner join nhaphanphoi npp on npp.pk_seq=ChiTieu.npp_fk      \n "+
		"	inner join khuvuc kv on kv.pk_seq=npp.khuvuc_fk       \n "+
		"	inner join vung  on vung.pk_seq=kv.vung_fk      \n "+
		"	inner join kenhbanhang kbh on kbh.pk_seq=ChiTieu.kenh_fk       \n "+
		"	inner join donvikinhdoanh dvkd on dvkd.pk_seq=ChiTieu.dvkd_fk      \n "+
		"	inner join daidienkinhdoanh ddkd on ddkd.pk_seq=ChiTieu.ddkd_fk      \n "+
		"	left join        \n "+
		"(      \n "+
		"	select dvkd_fk, ddkd_fk,kbh_fk,npp_fk ,sum(tdSanluong)  as tdSanluong,Sanluong.ThoiGian     \n "+
		"	from      \n "+
		"	(       \n "+
		"			select dh.KBH_FK,dh.DVKD_FK,dh.DDKD_FK,dh.NPP_FK,dh.spId,sum(dh.tdSanluong) as tdSanLuong,dh.ThoiGian     \n "+
		"			from      \n "+
		"			(	     \n "+
		"				select sp.dvkd_fk,dh.kbh_fk,ddkd_fk,dh.npp_fk,sp.PK_SEQ as spId , sum( soluong*  trongluong) as tdSanluong,substring(dh.NGAYNHAP,1,7)as ThoiGian     \n "+
		"				from donhang dh inner join donhang_sanpham dh_sp on dh.pk_seq=dh_sp.donhang_fk inner join sanpham sp on sp.pk_seq=dh_sp.sanpham_fk       \n "+
		"				where  dh.trangthai='1' and substring(dh.NGAYNHAP,1,7 )>= '"+fromDate+"'  and substring(dh.NGAYNHAP,1,7 )<= '"+toDate+"'       \n "+
		"					and dh.PK_SEQ not in(select DONHANG_FK from DONHANGTRAVE where TRANGTHAI='3' and DONHANG_FK is not null)     \n "+
		"				group by sp.dvkd_fk,dh.kbh_fk,ddkd_fk,dh.npp_fk,sp.PK_SEQ,substring(dh.NGAYNHAP,1,7)     \n "+
		"			union all     \n "+
		"				select sp.dvkd_fk,dh.kbh_fk,ddkd_fk,dh.npp_fk,sp.PK_SEQ as spId , (-1)*sum( soluong*  trongluong) as tdSanluong,substring(dh.NGAYNHAP,1,7)as ThoiGian     \n "+
		"				from DONHANGTRAVE dh inner join DONHANGTRAVE_SANPHAM dh_sp on dh_sp.DONHANGTRAVE_FK=dh.pk_seq inner join sanpham sp on sp.pk_seq=dh_sp.sanpham_fk       \n "+
		"				where dh.donhang_fk is null and dh.trangthai='3' and substring(dh.NGAYNHAP,1,7 )>= '"+fromDate+"'  and substring(dh.NGAYNHAP,1,7 )<= '"+toDate+"'       \n "+
		"				group by sp.dvkd_fk,dh.kbh_fk,ddkd_fk,dh.npp_fk,sp.PK_SEQ,substring(dh.NGAYNHAP,1,7)     \n "+
		"			)as dh     \n "+
		"			group by dh.dvkd_fk,dh.kbh_fk,ddkd_fk,dh.npp_fk,dh.spId,dh.ThoiGian     \n "+
		"	) as Sanluong inner join     \n "+
		"		(      \n "+
		"			select  sp_fk as sanpham_fk,NhomChiTieuThang.ThoiGian      \n "+
		"			from nhomsanpham_sanpham  nsp inner join      \n "+
		"			(     \n "+
		"				select   b.nhomsanpham_fk,cast(ctnpp.NAM AS varchar(4)) + '-' +case when THANG<10 then '0'+cast(ctnpp.THANG AS varchar(2)) else cast(ctnpp.THANG AS varchar(2)) end as ThoiGian     \n "+
		"				 from chitieunpp_ddkd_nhomsp b inner join chitieunpp_ddkd a on      \n "+
		"					a.chitieunpp_fk=b.chitieunpp_fk and a.ddkd_fk=b.ddkd_fk      \n "+
		"					inner join chitieunpp ctnpp on ctnpp.pk_seq=b.chitieunpp_fk         \n "+
		"				where  trangthai<>'2'      \n "+
		"				and cast(ctnpp.NAM AS varchar(4)) + '-' +case when THANG<10 then '0'+cast(ctnpp.THANG AS varchar(2)) else cast(ctnpp.THANG AS varchar(2)) end  >='"+fromDate+"'     \n "+
		"				and cast(ctnpp.NAM AS varchar(4)) + '-' +case when THANG<10 then '0'+cast(ctnpp.THANG AS varchar(2)) else cast(ctnpp.THANG AS varchar(2)) end <='"+toDate+"'     \n "+
		"				group by b.nhomsanpham_fk,ctnpp.NAM,ctnpp.THANG      \n "+
		"			)as NhomChiTieuThang  on  nsp.NSP_FK=NhomChiTieuThang.NHOMSANPHAM_FK     \n "+
		"			group by SP_FK,ThoiGian     \n "+
		"		) as nsp on nsp.sanpham_fk=Sanluong.spId and Sanluong.ThoiGian=nsp.ThoiGian     \n "+
		"	group by   dvkd_fk, ddkd_fk,kbh_fk,npp_fk,Sanluong.ThoiGian     \n "+
		"     \n "+
		")as ThucDatSanLuong on ChiTieu.kenh_fk=ThucDatSanLuong.kbh_fk and ChiTieu.dvkd_fk=ThucDatSanLuong.dvkd_fk and ChiTieu.ddkd_fk=ThucDatSanLuong.ddkd_fk     \n "+
		"	and ThucDatSanLuong.ThoiGian=ChiTieu.ThoiGian AND ThucDatSanLuong.NPP_FK=ChiTieu.npp_fk     \n "+
		" left join     \n "+
		/*"(     \n "+
		"	 select DonHang.KBH_FK,DonHang.DVKD_FK,DonHang.DDKD_FK,DonHang.NPP_FK,DonHang.ThoiGian ,SUM(DonHang.tdDonhang) as tdDonhang,SUM(DonHang.tdNgaylamviec) as tdNgaylamviec     \n "+
		"	from     \n "+
		"	(	     \n "+
		"		select sp.dvkd_fk,dh.kbh_fk,ddkd_fk,dh.npp_fk ,count(distinct dh.pk_seq) as tdDonhang,count (distinct dh.ngaynhap) as tdNgaylamviec,substring(dh.NGAYNHAP,1,7)as ThoiGian     \n "+
		"		from donhang dh inner join donhang_sanpham dh_sp on dh.pk_seq=dh_sp.donhang_fk      \n "+
		"			inner join sanpham sp on sp.pk_seq=dh_sp.sanpham_fk       \n "+
		"		where  dh.trangthai='1' and substring(dh.NGAYNHAP,1,7 )>= '"+fromDate+"'  and substring(dh.NGAYNHAP,1,7 )<= '"+toDate+"'       \n "+
		"		and dh.PK_SEQ not in(select DONHANG_FK from DONHANGTRAVE where TRANGTHAI='3' and DONHANG_FK is not null)     \n "+
		"		group by sp.dvkd_fk,dh.kbh_fk,ddkd_fk,dh.npp_fk,substring(dh.NGAYNHAP,1,7)     \n "+
		"	)DonHang     \n "+
		"	group by  DonHang.KBH_FK,DonHang.DVKD_FK,DonHang.DDKD_FK,DonHang.NPP_FK,DonHang.ThoiGian     \n "+
		")as ThucDatDonHang on ChiTieu.kenh_fk=ThucDatDonHang.kbh_fk and ChiTieu.dvkd_fk=ThucDatDonHang.dvkd_fk and ChiTieu.ddkd_fk=ThucDatDonHang.ddkd_fk     \n "+
		"and ThucDatDonHang.ThoiGian=ChiTieu.ThoiGian and ThucDatDonHang.NPP_FK=ChiTieu.npp_fk     \n "+*/


"(     \n "+
"	 select DonHang.KBH_FK,DonHang.DVKD_FK,DonHang.DDKD_FK,DonHang.NPP_FK,DonHang.ThoiGian ,SUM(DonHang.tdDonhang) as tdDonhang,SUM(DonHang.tdNgaylamviec) as tdNgaylamviec     \n "+
"	from     \n "+
"	(	     \n "+
"		select sp.dvkd_fk,dh.kbh_fk,ddkd_fk,dh.npp_fk ,count(distinct dh.pk_seq) as tdDonhang,count (distinct dh.ngaynhap) as tdNgaylamviec,substring(dh.NGAYNHAP,1,7)as ThoiGian     \n "+
"		from donhang dh inner join donhang_sanpham dh_sp on dh.pk_seq=dh_sp.donhang_fk      \n "+
"			inner join sanpham sp on sp.pk_seq=dh_sp.sanpham_fk       \n "+
"		where  dh.trangthai='1' and substring(dh.NGAYNHAP,1,7 )>= '"+fromDate+"'  and substring(dh.NGAYNHAP,1,7 )<= '"+toDate+"'       \n "+
"		and dh.PK_SEQ not in(select DONHANG_FK from DONHANGTRAVE where TRANGTHAI='3' and DONHANG_FK is not null)     \n "+
"		group by sp.dvkd_fk,dh.kbh_fk,ddkd_fk,dh.npp_fk,substring(dh.NGAYNHAP,1,7)     \n " +
"       having sum(dh_sp.soluong*sp.trongluong)>=9                                                                                    "+
"	)DonHang     \n "+
"	group by  DonHang.KBH_FK,DonHang.DVKD_FK,DonHang.DDKD_FK,DonHang.NPP_FK,DonHang.ThoiGian     \n "+
")as ThucDatDonHang on ChiTieu.kenh_fk=ThucDatDonHang.kbh_fk and ChiTieu.dvkd_fk=ThucDatDonHang.dvkd_fk and ChiTieu.ddkd_fk=ThucDatDonHang.ddkd_fk     \n "+
"and ThucDatDonHang.ThoiGian=ChiTieu.ThoiGian and ThucDatDonHang.NPP_FK=ChiTieu.npp_fk     \n "+
		
		
		
		
		" left join     \n "+
		"(     \n "+
		"	select ThucDat.KBH_FK,ThucDat.DVKD_FK,ThucDat.DDKD_FK,ThucDat.NPP_FK,ThucDat.ThoiGian,COUNT(ThucDat.PK_SEQ) as SoDonHangDatCtSanLuong     \n "+
		"	from     \n "+
		"	(     \n "+
		"		select ctnpp.nhapp_fk as npp_fk,ctnpp.kenh_fk,ctnpp.dvkd_fk,ctnpp.DDKD_FK,ctnpp.ThoiGian,ctnpp.ctNgaylamviec,ctDonHang,ctSanLuong,ctSanLuongDonHang     \n "+
		"		from     \n "+
		"     \n "+
		"	(     \n "+
		"			select ctnpp.nhapp_fk ,ctnpp.kenh_fk,ctnpp.dvkd_fk, ctnpp.ddkd_fk ,  ctnpp.songaylamviec as ctNgaylamviec, ctDonHang,ctSanLuongDonHang as ctSanLuongDonHang,sum(ctnpp.chitieu)as ctSanLuong  ,ctnpp.ThoiGian     \n "+
		"			from     \n "+
		"			(		     \n "+
		"				select ctnpp.nhapp_fk ,ctnpp.kenh_fk,ctnpp.dvkd_fk, b.ddkd_fk , ctnpp.songaylamviec ,a.SODONHANG,isnull(a.sanluongtrendh,0) as ctSanLuongDonHang ,ctnpp.songaylamviec*  a.sodonhang as ctDonHang ,b.chitieu ,cast(ctnpp.NAM AS varchar(4)) + '-' +case when THANG<10 then '0'+cast(ctnpp.THANG AS varchar(2)) else cast(ctnpp.THANG AS varchar(2)) end as ThoiGian     \n "+
		"				from chitieunpp_ddkd_nhomsp b inner join chitieunpp_ddkd a on  a.chitieunpp_fk=b.chitieunpp_fk and a.ddkd_fk=b.ddkd_fk       \n "+
		"						inner join chitieunpp ctnpp on ctnpp.pk_seq=b.chitieunpp_fk         \n "+
		"				where   trangthai<>'2'      \n "+
		"				and cast(ctnpp.NAM AS varchar(4)) + '-' +case when THANG<10 then '0'+cast(ctnpp.THANG AS varchar(2)) else cast(ctnpp.THANG AS varchar(2)) end  >='"+fromDate+"'     \n "+
		"				and cast(ctnpp.NAM AS varchar(4)) + '-' +case when THANG<10 then '0'+cast(ctnpp.THANG AS varchar(2)) else cast(ctnpp.THANG AS varchar(2)) end <='"+toDate+"'     \n "+
		"				) as ctnpp     \n "+
		"			group by  ctnpp.nhapp_fk ,ctnpp.kenh_fk,ctnpp.dvkd_fk,ctnpp.DDKD_FK,ctnpp.ThoiGian,ctnpp.songaylamviec,ctDonHang,ctnpp.ctSanLuongDonHang     \n "+
		"			) as ctnpp     \n "+
		"	)as ChiTieu left join      \n "+
		"	(	     \n "+
		"		select dh.PK_SEQ,sp.dvkd_fk,dh.kbh_fk,ddkd_fk,dh.npp_fk ,sum(dh_sp.SOLUONG*sp.TRONGLUONG) as sanluong,substring(dh.NGAYNHAP,1,7)as ThoiGian     \n "+
		"		from donhang dh inner join donhang_sanpham dh_sp on dh.pk_seq=dh_sp.donhang_fk inner join sanpham sp on sp.pk_seq=dh_sp.sanpham_fk       \n "+
		"		where  dh.trangthai='1' and substring(dh.NGAYNHAP,1,7 )>= '"+fromDate+"'  and substring(dh.NGAYNHAP,1,7 )<= '"+toDate+"'  and dh.PK_SEQ not in( select DONHANG_FK from DONHANGTRAVE where TRANGTHAI=3 and DONHANG_FK is not null )      \n "+
		"		group by dh.PK_SEQ,sp.dvkd_fk,dh.kbh_fk,ddkd_fk,dh.npp_fk,substring(dh.NGAYNHAP,1,7)     \n "+
		"	)ThucDat on ChiTieu.kenh_fk=ThucDat.kbh_fk and ChiTieu.dvkd_fk=ThucDat.dvkd_fk and ChiTieu.ddkd_fk=ThucDat.ddkd_fk     \n "+
		"		and ThucDat.ThoiGian=ChiTieu.ThoiGian and ThucDat.NPP_FK=ChiTieu.npp_fk and ThucDat.sanluong>=ChiTieu.ctSanLuongDonHang     \n "+
		"	group by  ThucDat.KBH_FK,ThucDat.DVKD_FK,ThucDat.DDKD_FK,ThucDat.NPP_FK,ThucDat.ThoiGian     \n "+
		")as SanLuongDonHang on ChiTieu.kenh_fk=SanLuongDonHang.kbh_fk and ChiTieu.dvkd_fk=SanLuongDonHang.dvkd_fk and ChiTieu.ddkd_fk=SanLuongDonHang.ddkd_fk     \n "+
		"and SanLuongDonHang.ThoiGian=ChiTieu.ThoiGian and SanLuongDonHang.NPP_FK=ChiTieu.npp_fk      \n "+
		" where 1=1";
		geso.dms.center.util.Utility utilcenter = new geso.dms.center.util.Utility();
		
		if (obj.getPhanloai().equals("2"))
		{
			sql += " and npp.pk_seq in " + utilcenter.quyen_npp(obj.getuserId()) + " and kbh.pk_seq in  "
					+ utilcenter.quyen_kenh(obj.getuserId()) ;
		}
		if (obj.getkenhId().length() > 0)
			sql = sql + " and kbh.pk_seq ='" + obj.getkenhId() + "'";
		if (obj.getvungId().length() > 0)
			sql = sql + " and vung.pk_seq ='" + obj.getvungId() + "'";
		if (obj.getkhuvucId().length() > 0)
			sql = sql + " and kv.pk_seq ='" + obj.getkhuvucId() + "'";
		if (obj.getdvkdId().length() > 0)
			sql = sql + " and dvkd.PK_SEQ ='" + obj.getdvkdId() + "'";
		if (obj.getnppId().length() > 0)
			sql = sql + " and npp.pk_seq ='" + obj.getnppId() + "'";
		return  sql  ;
	}

	private void CreatePivotTable(OutputStream out, IStockintransit obj, String query) throws Exception
	{
		try
		{
			String chuoi = getServletContext().getInitParameter("path") + "\\BCThucHienChiTieuSR.xlsm";
			FileInputStream fstream = new FileInputStream(chuoi);
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

			CreateStaticHeader(workbook, obj);
			FillData(workbook, obj.getFieldShow(), query, obj);
			workbook.save(out);
			fstream.close();
		} catch (Exception ex)
		{
			throw new Exception(ex.getMessage());
		}
	}

	private void CreateStaticHeader(Workbook workbook, IStockintransit obj) throws Exception
	{
		String fromDate=obj.getFromYear()+'-'+obj.getFromMonth();
		String toDate=obj.getToYear()+'-'+obj.getToMonth();
		
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		Cells cells = worksheet.getCells();

		Style style;		
		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
		cell.setValue("TÌNH HÌNH THỰC HIỆN CHỈ TIÊU ĐẠI DIỆN KINH DOANH");

		style = cell.getStyle();

		Font font2 = new Font();
		font2.setColor(Color.RED);// mau chu
		font2.setSize(14);// size chu
		font2.setBold(true);
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu
		cell.setStyle(style);

	    cells.setRowHeight(2, 18.0f);
	    cell = cells.getCell("A3");
	    
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ Tháng : " + fromDate + "" );
	    
	    cells.setRowHeight(2, 18.0f);
	    cell = cells.getCell("B3"); 
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến tháng : " +toDate + "" );
		
		cells.setRowHeight(3, 18.0f);
		cell = cells.getCell("A4");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));

		cells.setRowHeight(4, 18.0f);
		cell = cells.getCell("A5");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());

	    cell = cells.getCell("DA1"); cell.setValue("KenhBanHang");
	    cell = cells.getCell("DB1"); cell.setValue("DonViKinhDoanh");
	    cell = cells.getCell("DC1"); cell.setValue("ChiNhanh");
	    cell = cells.getCell("DD1"); cell.setValue("KhuVuc");
	    cell = cells.getCell("DE1"); cell.setValue("MaNhaPhanPhoi");	
	    cell = cells.getCell("DF1");cell.setValue("NhaPhanPhoi");  	    
	    cell = cells.getCell("DG1"); cell.setValue("DaiDienKinhDoanh");	    	    
	    cell = cells.getCell("DH1"); cell.setValue("ChieuTieuNgayLamViec");
	    cell = cells.getCell("DI1"); cell.setValue("ThucDatNgayLamViec");
	    cell = cells.getCell("DJ1"); cell.setValue("ChiTieuSoDH");	    
	    cell = cells.getCell("DK1"); cell.setValue("ThucDatSoDH");
	    cell = cells.getCell("DL1"); cell.setValue("ChiTieuSanLuong");
	    cell = cells.getCell("DM1"); cell.setValue("ThucDatSanLuong");
	    cell = cells.getCell("DN1"); cell.setValue("ThoiGian");
	    cell = cells.getCell("DO1"); cell.setValue("ChiTieuSanluongDonHang");
	    cell = cells.getCell("DP1"); cell.setValue("SoDonHangDatChiTieuSanLuong");

	}

	private void FillData(Workbook workbook, String[] fieldShow, String query, IStockintransit obj) throws Exception
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();

		cells.setColumnWidth(0, 10.0f);
		cells.setColumnWidth(1, 15.0f);
		cells.setColumnWidth(2, 15.0f);
		cells.setColumnWidth(3, 15.0f);
		cells.setColumnWidth(4, 15.0f);
		cells.setColumnWidth(5, 15.0f);
		cells.setColumnWidth(6, 15.0f);
		cells.setColumnWidth(7, 15.0f);
		cells.setColumnWidth(8, 15.0f);
		cells.setColumnWidth(9, 15.0f);
		cells.setColumnWidth(10, 15.0f);
		cells.setColumnWidth(11, 15.0f);
		cells.setColumnWidth(12, 15.0f);
		cells.setColumnWidth(13, 15.0f);
		
		dbutils db = new dbutils();
		ResultSet rs = db.get(query);
		System.out.println("115 Bao cao thuc hien chi tieu "+query);
		int index = 2;
		if(rs!=null)
		{
			 try 
			 {
				 Cell cell = null;
				while (rs.next())
				{
					cell = cells.getCell("DA" + String.valueOf(index));		cell.setValue(rs.getString("Kenh"));	
					cell = cells.getCell("DB" + String.valueOf(index));		cell.setValue(rs.getString("DonViKinhDoanh"));	
					cell = cells.getCell("DC" + String.valueOf(index));		cell.setValue(rs.getString("Vung"));	
					cell = cells.getCell("DD" + String.valueOf(index));		cell.setValue(rs.getString("KhuVuc"));
					cell = cells.getCell("DE" + String.valueOf(index));		cell.setValue(rs.getString("MaNhaPhanPhoi"));
					cell = cells.getCell("DF" + String.valueOf(index));		cell.setValue(rs.getString("NhaPhanPhoi"));	
					cell = cells.getCell("DG" + String.valueOf(index));		cell.setValue(rs.getString("DaiDienKinhDoanh"));
					cell = cells.getCell("DH" + String.valueOf(index));		cell.setValue(rs.getDouble("ctNgayLamViec"));	
					cell = cells.getCell("DI" + String.valueOf(index));		cell.setValue(rs.getDouble("tdNgayLamViec"));
					cell = cells.getCell("DJ" + String.valueOf(index));		cell.setValue(rs.getDouble("ctDonHang"));	
					cell = cells.getCell("DK" + String.valueOf(index));		cell.setValue(rs.getDouble("tdDonHang"));
					cell = cells.getCell("DL" + String.valueOf(index));		cell.setValue(rs.getDouble("ctSanLuong"));	
					cell = cells.getCell("DM" + String.valueOf(index));		cell.setValue(rs.getDouble("tdSanLuong"));
					cell = cells.getCell("DN" + String.valueOf(index));		cell.setValue(rs.getString("ThoiGian"));	
					cell = cells.getCell("DO" + String.valueOf(index));		cell.setValue(rs.getDouble("ctSanLuongDonHang"));
					cell = cells.getCell("DP" + String.valueOf(index));		cell.setValue(rs.getDouble("SoDonHangDatCtSanLuong"));
					index++;
				 }
				if (rs != null)
					rs.close();
				
				if(db != null)
					db.shutDown();
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
}
