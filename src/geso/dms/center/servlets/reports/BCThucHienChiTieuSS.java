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

@WebServlet("/BCThucHienChiTieuSS")
public class BCThucHienChiTieuSS extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public BCThucHienChiTieuSS()
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
		String nextJSP = "/AHF/pages/Center/BCThucHienChiTieuSS.jsp";
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
	String nextJSP = "/AHF/pages/Center/BCThucHienChiTieuSS.jsp";
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
		"	select  kbh.ten as kenh,dvkd.donvikinhdoanh ,vung.ten as vung,kv.ten as khuvuc,ctSec.gsbh_fk as MaGiamSat,gsbh.ten as giamsat,  \n       "+
		"			ctSec.kenh_fk,ctSec.dvkd_fk, ctSec.ctDonHang ,tdDonHang.tdDonHang,ctNgayLamViec,tdNgayLamViec,ctSec.ctSanLuong,ThucDatSanLuongSec.tdSanluong,ctSec.ThoiGian,ctPri.ctSanLuongPri,ThucDatSanLuongPri.tdSanLuongPri,  \n       "+
		"			ctSec.ctDonHang as ctSanLuongDonHang,SanLuongDonHang.SoDonHangDatCtSanLuong  \n       "+
		"	from  		  \n       "+
		"		( 		  \n       "+
		"			select  b.gsbh_fk ,ct.kenh_fk,ct.dvkd_fk,a.donhang ,ct.songaylamviec as ctNgayLamViec,a.DONHANG*ct.SONGAYLAMVIEC as ctDonHang,  \n       "+
		"				a.chitieu as TongCtNhom ,isnull(a.SanLuongTrenDh,0) as ctSanLuongDonHang,SUM(b.chitieu) as ctSanLuong ,cast(ct.NAM AS varchar(4)) + '-' +case when THANG<10 then '0'+cast(ct.THANG AS varchar(2)) else cast(ct.THANG AS varchar(2)) end as ThoiGian  \n       "+
		"			from   \n       "+
		"				chitieusec_gsbh_nhomsp b inner join chitieusec_gsbh a on  a.chitieusec_fk=b.chitieusec_fk  and a.gsbh_fk=b.gsbh_fk 		  \n       "+
		"				inner join chitieu_sec ct on ct.pk_seq=b.chitieusec_fk    \n       "+
		"			where ct.TRANGTHAI!=2  \n       "+
		"			and cast(CT.NAM AS varchar(4))+  '-' +case when THANG<10 then '0'+cast(ct.THANG AS varchar(2)) else cast(ct.THANG AS varchar(2)) end  >='"+fromDate+"'       \n       "+
		"				and cast(ct.NAM AS varchar(4))+  '-' +case when THANG<10 then '0'+cast(ct.THANG AS varchar(2)) else cast(ct.THANG AS varchar(2)) end <='"+toDate+"'       \n       "+
		"			group by b.gsbh_fk ,ct.kenh_fk,ct.dvkd_fk,a.donhang,ct.songaylamviec,a.chitieu ,a.SanLuongTrenDh,CT.thang,ct.nam  \n       "+
		"		) as ctSec 		  \n       "+
		"		inner join giamsatbanhang gsbh on gsbh.pk_seq=ctSec.gsbh_fk   \n       "+
		"		inner join khuvuc kv on kv.pk_seq=gsbh.khuvuc_fk 	  \n       "+
		"		inner join vung  on vung.pk_seq=kv.vung_fk 		  \n       "+
		"		inner join kenhbanhang kbh on kbh.pk_seq=ctSec.kenh_fk 	  \n       "+
		"		inner join donvikinhdoanh dvkd on dvkd.pk_seq=ctSec.dvkd_fk     \n       "+
		"		left join  	  \n       "+
		"		( 	  \n       "+
		"			select sp.dvkd_fk,dh.kbh_fk,gsbh_fk ,count(distinct dh.pk_seq) as tdDonhang,count (distinct dh.ngaynhap) as tdNgaylamviec,substring(dh.NGAYNHAP,1,7)as ThoiGian       \n       "+
		"			from donhang dh inner join donhang_sanpham dh_sp on dh.pk_seq=dh_sp.donhang_fk        \n       "+
		"				inner join sanpham sp on sp.pk_seq=dh_sp.sanpham_fk         \n       "+
		"			where  dh.trangthai='1' and substring(dh.NGAYNHAP,1,7 )>= '"+fromDate+"'  and substring(dh.NGAYNHAP,1,7 )<= '"+toDate+"'         \n       "+
		"				and dh.PK_SEQ not in(select DONHANG_FK from DONHANGTRAVE where TRANGTHAI='3' and DONHANG_FK is not null)     \n       "+
		"			group by sp.dvkd_fk,dh.kbh_fk,gsbh_fk ,SUBSTRING(dh.ngaynhap,1,7)	  \n       "+
		" 			HAVING SUM(DH_SP.SOLUONG*SP.TRONGLUONG)>=9 \n"+
		"		) tdDonHang on ctSec.kenh_fk=tdDonHang.kbh_fk and ctSec.dvkd_fk=tdDonHang.dvkd_fk and ctSec.gsbh_fk=tdDonHang.gsbh_fk  and tdDonHang.ThoiGian=ctSec.ThoiGian  \n       "+
		"		left join  	  \n       "+
		"		( 		  \n       "+
		"			select dvkd_fk, GSBH_FK,kbh_fk ,sum(tdSanluong)  as tdSanluong,Sanluong.ThoiGian  \n       "+
		"			from   \n       "+
		"				(    \n       "+
		"						select sp.dvkd_fk,dh.kbh_fk,dh.GSBH_FK,sp.PK_SEQ as spId , sum( soluong*  trongluong) as tdSanluong,substring(dh.NGAYNHAP,1,7)as ThoiGian       \n       "+
		"  \n       "+
		"					from donhang dh inner join donhang_sanpham dh_sp on dh.pk_seq=dh_sp.donhang_fk inner join sanpham sp on sp.pk_seq=dh_sp.sanpham_fk         \n       "+
		"						where  dh.trangthai='1' and substring(dh.NGAYNHAP,1,7 )>= '"+fromDate+"'  and substring(dh.NGAYNHAP,1,7 )<= '"+toDate+"'         \n       "+
		"							and dh.PK_SEQ not in(select DONHANG_FK from DONHANGTRAVE where TRANGTHAI='3' and DONHANG_FK is not null)       \n       "+
		"						group by sp.dvkd_fk,dh.kbh_fk,dh.GSBH_FK,sp.PK_SEQ,substring(dh.NGAYNHAP,1,7)       \n       "+
		"					union all       \n       "+
		"						select sp.dvkd_fk,dh.kbh_fk,dh.GSBH_FK,sp.PK_SEQ as spId , (-1)*sum( soluong*  trongluong) as tdSanluong,substring(dh.NGAYNHAP,1,7)as ThoiGian       \n       "+
		"						from DONHANGTRAVE dh inner join DONHANGTRAVE_SANPHAM dh_sp on dh_sp.DONHANGTRAVE_FK=dh.pk_seq inner join sanpham sp on sp.pk_seq=dh_sp.sanpham_fk         \n       "+
		"						where dh.donhang_fk is null and dh.trangthai='3' and substring(dh.NGAYNHAP,1,7 )>= '"+fromDate+"'  and substring(dh.NGAYNHAP,1,7 )<= '"+toDate+"'         \n       "+
		"						group by sp.dvkd_fk,dh.kbh_fk,dh.GSBH_FK,sp.PK_SEQ ,substring(dh.NGAYNHAP,1,7)     \n       "+
		"				) as Sanluong inner join  \n       "+
		"				(   \n       "+
		"					select  sp_fk as sanpham_fk,NhomChiTieu.ThoiGian   \n       "+
		"					from nhomsanpham_sanpham  nsp inner join   \n       "+
		"						(  \n       "+
		"							select   b.nhomsanpham_fk,cast(ct.NAM AS varchar(4)) + '-' +case when THANG<10 then '0'+cast(ct.THANG AS varchar(2)) else cast(ct.THANG AS varchar(2)) end as ThoiGian  \n       "+
		"							from chitieusec_gsbh_nhomsp b inner join chitieusec_gsbh a on  	  \n       "+
		"							a.chitieusec_fk=b.chitieusec_fk  and a.gsbh_fk=b.gsbh_fk 		  \n       "+
		"							inner join chitieu_sec ct on ct.pk_seq=b.chitieusec_fk    \n       "+
		"							where ct.TRANGTHAI!=2  \n       "+
		"								and cast(CT.NAM AS varchar(4))+  '-' +case when THANG<10 then '0'+cast(ct.THANG AS varchar(2)) else cast(ct.THANG AS varchar(2)) end  >='"+fromDate+"'       \n       "+
		"								and cast(ct.NAM AS varchar(4))+  '-' +case when THANG<10 then '0'+cast(ct.THANG AS varchar(2)) else cast(ct.THANG AS varchar(2)) end <='"+toDate+"'       \n       "+
		"							group by b.nhomsanpham_fk,CT.NAM,CT.THANG  \n       "+
		"						)as NhomChiTieu on  nsp.NSP_FK=NhomChiTieu.NHOMSANPHAM_FK  \n       "+
		"					group by SP_FK,ThoiGian  \n       "+
		"				) as nsp on nsp.sanpham_fk=Sanluong.spId and Sanluong.ThoiGian=nsp.ThoiGian  \n       "+
		"			group by   dvkd_fk, GSBH_FK,kbh_fk,Sanluong.ThoiGian  \n       "+
		"		) as ThucDatSanLuongSec on ctSec.kenh_fk=ThucDatSanLuongSec.kbh_fk and ctSec.dvkd_fk=ThucDatSanLuongSec.dvkd_fk and ctSec.gsbh_fk=ThucDatSanLuongSec.gsbh_fk  and ThucDatSanLuongSec.ThoiGian=ctSec.ThoiGian  \n       "+
		"		left join  \n       "+
		"		(  \n       "+
		"			select KENH_FK,DVKD_FK,GSBH_FK,ctSanLuongPri,ThoiGian  \n       "+
		"			from  \n       "+
		"			(  \n       "+
		"				select ct.DVKD_FK,ct.KENH_FK,b.GSBH_FK,sum(b.CHITIEU) as ctSanLuongPri,cast(ct.NAM AS varchar(4)) + '-' +case when THANG<10 then '0'+cast(ct.THANG AS varchar(2)) else cast(ct.THANG AS varchar(2)) end as ThoiGian  \n       "+
		"				from chitieu ct inner join   chitieu_gsbh_nhomsp  b on ct.pk_seq=b.chitieu_fk    \n       "+
		"				where  ct.TRANGTHAI!=2  \n       "+
		"				and cast(ct.NAM AS varchar(4)) + '-' +case when THANG<10 then '0'+cast(ct.THANG AS varchar(2)) else cast(ct.THANG AS varchar(2)) end >='"+fromDate+"'   \n       "+
		"				and cast(ct.NAM AS varchar(4)) + '-' +case when THANG<10 then '0'+cast(ct.THANG AS varchar(2)) else cast(ct.THANG AS varchar(2)) end <='"+toDate+"'   \n       "+
		"				group by ct.DVKD_FK,ct.KENH_FK,b.GSBH_FK,ct.NAM,ct.THANG  \n       "+
		"			)as ChiTieu  \n       "+
		"		)as ctPri on ctPri.ThoiGian=ctSec.ThoiGian and ctPri.DVKD_FK=ctSec.DVKD_FK and ctPri.GSBH_FK=ctSec.GSBH_FK and ctPri.KENH_FK=ctSec.KENH_FK  \n       "+
		"		left join    \n       "+
		"		(	  \n       "+
		"			select KBH_FK,DVKD_FK,GSBH_FK,SanLuong.ThoiGian,sum(SanLuong) as tdSanLuongPri  \n       "+
		"			from   \n       "+
		"				(  \n       "+
		"					select sp.dvkd_fk,dh.kbh_fk,gs.GSBH_FK ,sp.PK_SEQ as spId, sum(soluong* trongluong) as sanluong ,substring(dh.NGAYCHUNGTU,1,7) as ThoiGian  \n       "+
		"					from   \n       "+
		"						hdnhaphang dh inner join  hdnhaphang_sp dh_sp on dh.pk_seq=dh_sp.nhaphang_fk    \n       "+
		"						inner join sanpham sp on sp.ma=dh_sp.sanpham_fk   \n       "+
		"						inner join NHAPP_GIAMSATBH gs on gs.NPP_FK=dh.NPP_FK  \n       "+
		"					where   dh.trangthai='1' and substring(dh.NGAYCHUNGTU,1,7 )>= '"+fromDate+"'  and substring(dh.NGAYCHUNGTU,1,7 )<= '"+toDate+"'   \n       "+
		"						and substring(gs.NGAYKETTHUC,1,7 )>=substring(dh.NGAYCHUNGTU,1,7)    \n       "+
		"					group by sp.dvkd_fk,sp.PK_SEQ,dh.KBH_FK,dh.DVKD_FK,gs.GSBH_FK,substring(dh.NGAYCHUNGTU,1,7 )  \n       "+
		"  \n       "+
		"		)as SanLuong inner join   \n       "+
		"				(    \n       "+
		"					select  sp_fk as sanpham_fk,NhomChiTieuThang.ThoiGian   \n       "+
		"					from nhomsanpham_sanpham  nsp inner join   \n       "+
		"					(  \n       "+
		"						select NhomChiTieu.NHOMSANPHAM_FK,NhomChiTieu.ThoiGian  \n       "+
		"						from   \n       "+
		"						(  \n       "+
		"							select b.NHOMSANPHAM_FK,cast(ct.NAM AS varchar(4)) + '-' +case when THANG<10 then '0'+cast(ct.THANG AS varchar(2)) else cast(ct.THANG AS varchar(2)) end as ThoiGian  \n       "+
		"							  from chitieu ct inner join   chitieu_gsbh_nhomsp  b on ct.pk_seq=b.chitieu_fk    \n       "+
		"							where  ct.TRANGTHAI!=2  \n       "+
		"							and cast(ct.NAM AS varchar(4)) + '-' +case when THANG<10 then '0'+cast(ct.THANG AS varchar(2)) else cast(ct.THANG AS varchar(2)) end >='"+fromDate+"'  \n       "+
		"							and cast(ct.NAM AS varchar(4)) + '-' +case when THANG<10 then '0'+cast(ct.THANG AS varchar(2)) else cast(ct.THANG AS varchar(2)) end <='"+toDate+"'  \n       "+
		"							group by b.NHOMSANPHAM_FK,ct.NAM,ct.THANG  \n       "+
		"						)as NhomChiTieu   \n       "+
		"					)as NhomChiTieuThang  on  nsp.NSP_FK=NhomChiTieuThang.NHOMSANPHAM_FK  \n       "+
		"					group by SP_FK,ThoiGian  \n       "+
		"				) as nsp on nsp.sanpham_fk=Sanluong.spId and Sanluong.ThoiGian=nsp.ThoiGian	  \n       "+
		"			group by KBH_FK,DVKD_FK,GSBH_FK,SanLuong.ThoiGian	  \n       "+
		"		)as ThucDatSanLuongPri on ThucDatSanLuongPri.DVKD_FK=ctSec.DVKD_FK and ThucDatSanLuongPri.GSBH_FK=ctSec.GSBH_FK  and ThucDatSanLuongPri.KBH_FK=ctSec.KENH_FK and ThucDatSanLuongPri.ThoiGian=ctSec.ThoiGian  \n       "+
		"		left join  \n       "+
		"		(       \n       "+
		"			select ChiTieu.KENH_FK,ChiTieu.DVKD_FK,ChiTieu.GSBH_FK,ChiTieu.ThoiGian  ,COUNT(ThucDat.DonHangId) as SoDonHangDatCtSanLuong       \n       "+
		"			from       \n       "+
		"				(       \n       "+
		"					select gsbh_fk ,ct.kenh_fk,ct.dvkd_fk , ctDonHang,ctSanLuongDonHang   ,ct.ThoiGian       \n       "+
		"					from       \n       "+
		"					(		       \n       "+
		"						select  b.gsbh_fk ,ct.kenh_fk,ct.dvkd_fk,a.donhang ,ct.songaylamviec as ctNgayLamViec,a.DONHANG*ct.SONGAYLAMVIEC as ctDonHang,  \n       "+
		"							a.chitieu as TongCtNhom ,isnull(a.SanLuongTrenDh,0) as ctSanLuongDonHang,SUM(b.chitieu) as ctSanLuong ,cast(ct.NAM AS varchar(4)) + '-' +case when THANG<10 then '0'+cast(ct.THANG AS varchar(2)) else cast(ct.THANG AS varchar(2)) end as ThoiGian  \n       "+
		"						from   \n       "+
		"							chitieusec_gsbh_nhomsp b inner join chitieusec_gsbh a on  a.chitieusec_fk=b.chitieusec_fk  and a.gsbh_fk=b.gsbh_fk 		  \n       "+
		"							inner join chitieu_sec ct on ct.pk_seq=b.chitieusec_fk    \n       "+
		"							where ct.TRANGTHAI!=2  \n       "+
		"								and cast(CT.NAM AS varchar(4))+  '-' +case when THANG<10 then '0'+cast(ct.THANG AS varchar(2)) else cast(ct.THANG AS varchar(2)) end  >='"+fromDate+"'       \n       "+
		"								and cast(ct.NAM AS varchar(4))+  '-' +case when THANG<10 then '0'+cast(ct.THANG AS varchar(2)) else cast(ct.THANG AS varchar(2)) end <='"+toDate+"'       \n       "+
		"						group by b.gsbh_fk ,ct.kenh_fk,ct.dvkd_fk,a.donhang,ct.songaylamviec,a.chitieu ,a.SanLuongTrenDh,CT.thang,ct.nam  \n       "+
		"					) as ct       \n       "+
		"				)as ChiTieu left join        \n       "+
		"				(	       \n       "+
		"					select dh.PK_SEQ as DonHangId,sp.dvkd_fk,dh.kbh_fk,gsbh_fk ,sum(dh_sp.SOLUONG*sp.TRONGLUONG) as sanluong,substring(dh.NGAYNHAP,1,7)as ThoiGian       \n       "+
		"					from donhang dh inner join donhang_sanpham dh_sp on dh.pk_seq=dh_sp.donhang_fk inner join sanpham sp on sp.pk_seq=dh_sp.sanpham_fk         \n       "+
		"					where  dh.trangthai='1' and substring(dh.NGAYNHAP,1,7 )>= '"+fromDate+"'  and substring(dh.NGAYNHAP,1,7 )<= '"+toDate+"'    \n       "+
		"						and dh.PK_SEQ not in( select DONHANG_FK from DONHANGTRAVE where TRANGTHAI=3 and DONHANG_FK is not null )        \n       "+
		"					group by dh.PK_SEQ,sp.dvkd_fk,dh.kbh_fk,gsbh_fk,substring(dh.NGAYNHAP,1,7)       \n       "+
		"				)ThucDat on ChiTieu.kenh_fk=ThucDat.kbh_fk and ChiTieu.dvkd_fk=ThucDat.dvkd_fk and ChiTieu.GSBH_FK=ThucDat.GSBH_FK   and ThucDat.ThoiGian=ChiTieu.ThoiGian    and isnull(ThucDat.sanluong,0)>=ChiTieu.ctSanLuongDonHang     \n       "+
		"			group by  ChiTieu.KENH_FK,ChiTieu.DVKD_FK,ChiTieu.GSBH_FK,ChiTieu.ThoiGian       \n       "+
		"		)as SanLuongDonHang on SanLuongDonHang.DVKD_FK=ctSec.DVKD_FK and SanLuongDonHang.GSBH_FK=ctSec.GSBH_FK and SanLuongDonHang.KENH_FK=ctSec.KENH_FK and SanLuongDonHang.ThoiGian=ctSec.ThoiGian  \n       "+
		" where 1=1  ";
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
		System.out.println("BCThucHienChiTieuSS....."+sql);
		return  sql  ;
	}

	private void CreatePivotTable(OutputStream out, IStockintransit obj, String query) throws Exception
	{
		try
		{
			String chuoi = getServletContext().getInitParameter("path") + "\\BCThucHienChiTieuSS.xlsm";
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
		cell.setValue("TÌNH HÌNH THỰC HIỆN CHỈ TIÊU GIÁM SÁT BÁN HÀNG");
		
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
		cell = cells.getCell("DE1"); cell.setValue("MaGiamSat");	
		cell = cells.getCell("DF1");cell.setValue("GiamSatBanHang");  	    
		cell = cells.getCell("DG1"); cell.setValue("ChieuTieuNgayLamViec");
		cell = cells.getCell("DH1"); cell.setValue("ThucDatNgayLamViec");
		cell = cells.getCell("DI1"); cell.setValue("ChiTieuSoDH");	    
		cell = cells.getCell("DJ1"); cell.setValue("ThucDatSoDH");
		cell = cells.getCell("DK1"); cell.setValue("ChiTieuSec");
		cell = cells.getCell("DL1"); cell.setValue("ThucDatSec");
		cell = cells.getCell("DM1"); cell.setValue("ChiTieuPri");
		cell = cells.getCell("DN1"); cell.setValue("ThucDatPri");
		cell = cells.getCell("DO1"); cell.setValue("ThoiGian");
		cell = cells.getCell("DO1"); cell.setValue("ThoiGian");
		cell = cells.getCell("DP1"); cell.setValue("ChiTieuSanluongDonHang");
		cell = cells.getCell("DQ1"); cell.setValue("SoDonHangDatChiTieuSanLuong");
		
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
					cell = cells.getCell("DE" + String.valueOf(index));		cell.setValue(rs.getString("MaGiamSat"));
					cell = cells.getCell("DF" + String.valueOf(index));		cell.setValue(rs.getString("GiamSat"));	
					cell = cells.getCell("DG" + String.valueOf(index));		cell.setValue(rs.getDouble("ctNgayLamViec"));	
					cell = cells.getCell("DH" + String.valueOf(index));		cell.setValue(rs.getDouble("tdNgayLamViec"));
					cell = cells.getCell("DI" + String.valueOf(index));		cell.setValue(rs.getDouble("ctDonHang"));	
					cell = cells.getCell("DJ" + String.valueOf(index));		cell.setValue(rs.getDouble("tdDonHang"));
					cell = cells.getCell("DK" + String.valueOf(index));		cell.setValue(rs.getDouble("ctSanLuong"));	
					cell = cells.getCell("DL" + String.valueOf(index));		cell.setValue(rs.getDouble("tdSanLuong"));
					cell = cells.getCell("DM" + String.valueOf(index));		cell.setValue(rs.getDouble("ctSanLuongPri"));	
					cell = cells.getCell("DN" + String.valueOf(index));		cell.setValue(rs.getDouble("tdSanLuongPri"));
					cell = cells.getCell("DO" + String.valueOf(index));		cell.setValue(rs.getString("ThoiGian"));	
					cell = cells.getCell("DP" + String.valueOf(index));		cell.setValue(rs.getDouble("ctSanLuongDonHang"));
					cell = cells.getCell("DQ" + String.valueOf(index));		cell.setValue(rs.getDouble("SoDonHangDatCtSanLuong"));
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
