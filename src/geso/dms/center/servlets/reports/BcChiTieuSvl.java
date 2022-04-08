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



import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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

@WebServlet("/BcChiTieuSvl")
public class BcChiTieuSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public BcChiTieuSvl()
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
		String nextJSP = "/AHF/pages/Center/BcChiTieu.jsp";
		response.sendRedirect(nextJSP);
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();	
		Utility util = new Utility();
		
		obj.setuserId((String)session.getAttribute("userId")==null?"":(String) session.getAttribute("userId"));
		obj.setuserTen((String)session.getAttribute("userTen")==null? "":(String) session.getAttribute("userTen"));
		obj.setnppId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"))==null?"":util.antiSQLInspection(request.getParameter("nppId"))));
		obj.setkenhId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId"))==null? "":util.antiSQLInspection(request.getParameter("kenhId"))));
		obj.setdvkdId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId"))==null? "":util.antiSQLInspection(request.getParameter("dvkdId"))));
		obj.setMonth(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("month"))==null? "":util.antiSQLInspection(request.getParameter("month"))));
		obj.setYear(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("year"))==null? "":util.antiSQLInspection(request.getParameter("year"))));	   	 
		obj.setvungId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"))==null? "":util.antiSQLInspection(request.getParameter("vungId"))));	   	 
		obj.setkhuvucId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"))==null? "":util.antiSQLInspection(request.getParameter("khuvucId"))));	 
		obj.setdvkdId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId"))==null? "":util.antiSQLInspection(request.getParameter("dvkdId"))));		 
		obj.setgsbhId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gsbhId"))==null? "":util.antiSQLInspection(request.getParameter("gsbhId"))));
		obj.setnppId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"))==null? "":util.antiSQLInspection(request.getParameter("nppId"))));
		
		String nextJSP = "/AHF/pages/Center/BcChiTieu.jsp";		 
		try
		{
			String action=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));
			if(action.equals("Taomoi"))
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BcChiTieu.xlsm");
				OutputStream out = response.getOutputStream();
				CreatePivotTable(out,obj);
			}	else
			{
				obj.init();	    
				session.setAttribute("obj", obj);
				session.setAttribute("userId", obj.getuserId());		
				response.sendRedirect(nextJSP);
			}
		}catch(Exception ex)
		{
			obj.setMsg(ex.getMessage());
			obj.init();	    
			session.setAttribute("obj", obj);
			session.setAttribute("userId", obj.getuserId());		
			response.sendRedirect(nextJSP);
		}
		
	}
	
	private String setQuery( IStockintransit obj) 
	{
		String query="";
		query=
				" select COUNT(kv.TEN)	OVER( PARTITION BY vung.ten,kv.ten ) AS 'Row', 		"+ 			
						"	kbh.ten as kenh,dvkd.donvikinhdoanh ,vung.ten as vten,kv.ten as kvTen,npp.ma as nppMa,npp.ten as nppTen,		"+ 			
						"	ctnhom.kenh_fk,ctnhom.dvkd_fk,ctnhom.songaylamviec,ctnhom.donhang,sodh.donhang as datdonhang, 		"+ 			
						"	ctnhom.giaohang as ctgiaohang,ctnhom.tonkho as cttonkho,dieuchinh.tyle as tyledieuchinh,dhchuachot.sodonhang as sodhchuachot,		"+ 			
						"	isnull(ctnhom.[100151],0) as ct100151,isnull(ds.[100151],0) as ds100151 ,isnull(ctnhom.[100152],0) as ct100152,		"+ 			
						"	isnull(ds.[100152],0) as ds100152 ,		"+ 			
						"	isnull(ctnhom.[100153],0) as ct100153,isnull(ds.[100153],0) as ds100153 ,isnull(ctnhom.[100154],0) as ct100154,		"+ 			
						"	isnull(ds.[100154],0) as ds100154 ,isnull(ctnhom.[100159],0) as ct100159,isnull(ds.[100159],0) as ds100159,		"+ 			
						"	isnull(ctpri.[100151],0) as ctpri100151,isnull(dspri.[100151],0) as dspri100151 ,isnull(ctpri.[100152],0) as ctpri100152,		"+ 			
						"	isnull(dspri.[100152],0) as dspri100152 ,isnull(ctpri.[100153],0) as ctpri100153,isnull(dspri.[100153],0) as dspri100153 ,		"+ 			
						"	isnull(ctpri.[100154],0) as ctpri100154,isnull(dspri.[100154],0) as dspri100154 ,isnull(ctpri.[100159],0) as ctpri100159,		"+ 			
						"	isnull(dspri.[100159],0) as dspri100159		"+ 			
						"from 		"+ 			
						"(		"+ 			
						"	select ct.nhapp_fk as npp_fk ,ct.dvkd_fk,ct.kenh_fk,ct.songaylamviec, b.nhomsanpham_fk ,sum(a.sodonhang) as donhang ,		"+ 			
						"		sum(b.chitieu) as chitieunhom ,sum(a.tonkho) as tonkho,sum(a.giaohang ) as giaohang 		"+ 			
						"	from chitieunpp_ddkd_nhomsp b inner join chitieunpp_ddkd a on a.chitieunpp_fk=b.chitieunpp_fk and b.ddkd_fk=a.ddkd_fk		"+ 			
						"	inner join chitieunpp ct on ct.pk_seq=b.chitieunpp_fk		"+ 			
						"	where ct.thang=11 and ct.nam=2012		"+ 			
						"	group by ct.nhapp_fk ,ct.dvkd_fk,ct.kenh_fk,b.nhomsanpham_fk,ct.songaylamviec		"+ 			
						") p pivot( sum(chitieunhom)for nhomsanpham_fk in ([100151],[100152],[100153],[100154],[100159])		"+ 			
						") as ctnhom		"+ 			
						"left join 		"+ 			
						"(		"+ 			
						"	select sp.dvkd_fk,dh.kbh_fk,npp_fk, count(distinct dh.pk_seq) as donhang		"+ 			
						"	from donhang dh inner join donhang_sanpham dh_sp on dh.pk_seq=dh_sp.donhang_fk		"+ 			
						"		inner join sanpham sp on sp.pk_seq=dh_sp.sanpham_fk		"+ 			
						"	where dh.trangthai='1' and dh.ngaynhap like '2012-11%' 		"+ 			
						"	group by sp.dvkd_fk,dh.kbh_fk,npp_fk		"+ 			
						") sodh on ctnhom.kenh_fk=sodh.kbh_fk and ctnhom.dvkd_fk=sodh.dvkd_fk and ctnhom.npp_fk=sodh.npp_fk		"+ 			
						"left join 		"+ 			
						"( 		"+ 			
						"	select count(distinct a.pk_seq) as sodonhang,npp_fk,kbh_fk,c.dvkd_fk 		"+ 			
						"	from donhang a 		"+ 			
						"		inner join donhang_sanpham b on b.donhang_fk=a.pk_seq 		"+ 			
						"		inner join sanpham c on c.pk_seq=b.sanpham_fk 		"+ 			
						"	where a.trangthai=0 and a.ngaynhap like '2012-11%' 		"+ 			
						"	group by npp_fk,kbh_fk,c.dvkd_fk 		"+ 			
						")as dhchuachot on dhchuachot.npp_fk=ctnhom.npp_fk and dhchuachot.kbh_fk=ctnhom.kenh_fk and dhchuachot.dvkd_fk=ctnhom.dvkd_fk 		"+ 			
						"left join 		"+ 			
						"( 		"+ 			
						"	select a.npp_fk,a.kbh_fk,a.dvkd_fk,sum(b.tonhientai) as tonhientai,sum(b.tonmoi) as tonmoi ,		"+ 			
						"		sum(b.tonmoi)*100/nullif(sum(b.tonhientai),0) as tyle 		"+ 			
						"	from dieuchinhtonkho a inner join dieuchinhtonkho_sp b on b.dieuchinhtonkho_fk=a.pk_seq 		"+ 			
						"	where a.trangthai=1 and a.ngaydc like '2012-11%' 		"+ 			
						"	group by a.npp_fk,a.kbh_fk,a.dvkd_fk 		"+ 			
						")as dieuchinh on dieuchinh.npp_fk=ctnhom.npp_fk and dieuchinh.kbh_fk=ctnhom.kenh_fk and dieuchinh.dvkd_fk=ctnhom.dvkd_fk 		"+ 			
						"left join 		"+ 			
						"(		"+ 			
						"	select dvkd_fk, npp_fk,kbh_fk , [100151],[100152],[100153],[100154],[100159]		"+ 			
						"	from 		"+ 			
						"	( 		"+ 			
						"		select sp.dvkd_fk,dh.kbh_fk,npp_fk,nsp.nhomsp_fk, sum( soluong* trongluong) as sanluong 		"+ 			
						"		from donhang dh inner join donhang_sanpham dh_sp on dh.pk_seq=dh_sp.donhang_fk		"+ 			
						"			inner join sanpham sp on sp.pk_seq=dh_sp.sanpham_fk		"+ 			
						"			inner join ( select max(nsp_fk) as nhomsp_fk,sp_fk as sanpham_fk from nhomsanpham_sanpham		"+ 			
						"			where nsp_fk in (100151,100152,100153,100154,100159) 		"+ 			
						"		group by sp_fk 		"+ 			
						"	) as nsp on nsp.sanpham_fk=sp.pk_seq		"+ 			
						"	where dh.trangthai='1' and dh.ngaynhap like '2012-11%' 		"+ 			
						"	group by sp.dvkd_fk,dh.kbh_fk,npp_fk,nsp.nhomsp_fk 		"+ 			
						"	union all 		"+ 			
						"	select sp2.dvkd_fk as dvkd_fk, dh.kbh_fk, dh.npp_fk , nsp.nhomsp_fk ,		"+ 			
						"		(-1)* sum(trongluong * isnull(dh_sp.soluong, dh_sp1.soluong) ) as doanhso		"+ 			
						"	from donhangtrave dh		"+ 			
						"		left outer join donhangtrave_sanpham dh_sp on dh_sp.donhangtrave_fk = dh.pk_seq		"+ 			
						"		left outer join donhang_sanpham dh_sp1 on dh.donhang_fk = dh_sp1.donhang_fk		"+ 			
						"		inner join sanpham sp2 on sp2.pk_seq = isnull(dh_sp.sanpham_fk, dh_sp1.sanpham_fk)		"+ 			
						"		inner join 		"+ 			
						"		(		"+ 			
						"			select max(nsp_fk) as nhomsp_fk,sp_fk as sanpham_fk from nhomsanpham_sanpham		"+ 			
						"			where nsp_fk in (100151,100152,100153,100154,100159)		"+ 			
						"			group by sp_fk		"+ 			
						"		) as nsp on nsp.sanpham_fk=sp2.pk_seq		"+ 			
						"	where dh.trangthai='3' and dh.ngaynhap like '2012-11%'		"+ 			
						"	group by sp2.dvkd_fk,dh.kbh_fk,npp_fk,nsp.nhomsp_fk		"+ 			
						") a pivot ( sum( sanluong) for nhomsp_fk in ([100151],[100152],[100153],[100154],[100159] ) ) as t ) as ds on ctnhom.kenh_fk=ds.kbh_fk and ctnhom.dvkd_fk=ds.dvkd_fk and ctnhom.npp_fk=ds.npp_fk		"+ 			
						"left join 		"+ 			
						"(		"+ 			
						"	select dvkd_fk, npp_fk,kbh_fk , [100151],[100152],[100153],[100154],[100159]		"+ 			
						"	from 		"+ 			
						"	( 		"+ 			
						"		select sp.dvkd_fk,dh.kbh_fk,npp_fk,nsp.nhomsp_fk,sum( soluong* trongluong) as sanluong 		"+ 			
						"		from hdnhaphang dh inner join hdnhaphang_sp dh_sp on dh.pk_seq=dh_sp.nhaphang_fk 		"+ 			
						"			inner join sanpham sp on sp.ma=dh_sp.sanpham_fk		"+ 			
						"			inner join 		"+ 			
						"			(		"+ 			
						"				select max(nsp_fk) as nhomsp_fk,sp_fk as sanpham_fk from nhomsanpham_sanpham		"+ 			
						"				where nsp_fk in (100151,100152,100153,100154,100159)			"+ 			
						"				group by sp_fk 		"+ 			
						"			) as nsp on nsp.sanpham_fk=sp.pk_seq		"+ 			
						"		where dh.ngaynhan like '2012-11%' and dh.trangthai <>2 and dh.loaidonhang='0' 		"+ 			
						"		group by sp.dvkd_fk,dh.kbh_fk,npp_fk,nsp.nhomsp_fk ) a		"+ 			
						"		 pivot( sum( sanluong) for nhomsp_fk in ([100151],[100152],[100153],[100154],[100159] ) ) as t 		"+ 			
						"		) as dspri on ctnhom.kenh_fk=dspri.kbh_fk and ctnhom.dvkd_fk=dspri.dvkd_fk and ctnhom.npp_fk=dspri.npp_fk		"+ 			
						"left join 		"+ 			
						"(		"+ 			
						"	select dvkd_fk, npp_fk,kenh_fk , [100151],[100152],[100153],[100154],[100159] 		"+ 			
						"	from		"+ 			
						"		( 		"+ 			
						"			select ct.kenh_fk,ct.dvkd_fk, b.npp_fk,nhomsp_fk , sum(b.chitieu) as chitieu 		"+ 			
						"			from chitieu ct		"+ 			
						"				inner join chitieu_nhapp_nhomsp b on ct.pk_seq=b.chitieu_fk		"+ 			
						"			where ct.thang=11 and ct.nam=2012 		"+ 			
						"			group by ct.kenh_fk ,ct.dvkd_fk, b.npp_fk ,nhomsp_fk 		"+ 			
						"		)a pivot ( sum( chitieu) for nhomsp_fk in ( [100151],[100152],[100153],[100154],[100159] ) ) as t		"+ 			
						"	) ctpri on ctpri.kenh_fk=ctnhom.kenh_fk and ctnhom.dvkd_fk=ctpri.dvkd_fk and ctnhom.npp_fk=ctpri.npp_fk		"+ 			
						"inner join nhaphanphoi npp on npp.pk_seq=ctnhom.npp_fk		"+ 			
						"inner join khuvuc kv on kv.pk_seq=npp.khuvuc_fk		"+ 			
						"inner join vung on vung.pk_seq=kv.vung_fk		"+ 			
						"inner join kenhbanhang kbh on kbh.pk_seq=ctnhom.kenh_fk		"+ 			
						"inner join donvikinhdoanh dvkd on dvkd.pk_seq=ctnhom.dvkd_fk 		"+ 			
						"order by vung.TEN,kv.TEN,npp.TEN	 ";
		System.out.println("Query BcChiTieuSvl: " + query);
		return query;
	}
	
	
	
	private void CreatePivotTable(OutputStream out,IStockintransit obj) throws Exception
	{   
		try
		{
			//String chuoi=getServletContext().getInitParameter("path") + "\\BcChiTieu.xlsm";
			//FileInputStream fstream = new FileInputStream(chuoi);
			File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\BcChiTieu.xlsm");
			FileInputStream fstream = new FileInputStream(f) ;
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			
			CreateStaticHeader(workbook,obj);
			String query=setQuery(obj);
			FillData(workbook, obj,query );
			
			workbook.save(out);
			fstream.close();
		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}	    
	}
	
	
	private void CreateStaticHeader(Workbook workbook, IStockintransit obj) 
	{
		
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		Cells cells = worksheet.getCells();
		
		Style style;		
		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
		cell.setValue("TÌNH HÌNH THỰC HIỆN CHỈ TIÊU NHÀ PHÂN PHỐI");
		
		style = cell.getStyle();
		
		Font font2 = new Font();
		font2.setColor(Color.RED);// mau chu
		font2.setSize(14);// size chu
		font2.setBold(true);
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.CENTER);// canh le cho chu
		cell.setStyle(style);
		
		cells.setRowHeight(3, 18.0f);
		cell = cells.getCell("A3");
		
		ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Tháng : " + obj.getMonth() + "" );
		
		cells.setRowHeight(3, 18.0f);
		cell = cells.getCell("B3"); 
		ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Năm : " + obj.getYear() + "" );
		
		cells.setRowHeight(4, 18.0f);
		cell = cells.getCell("A4");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
		
		cells.setRowHeight(5, 18.0f);
		cell = cells.getCell("A5");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());
		
		int index=1;
		cell = cells.getCell(GetExcelColumnName(index)+"8"); cell.setValue("KHU VỰC");index++;
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		ReportAPI.mergeCells(worksheet, 7, 10, 0, 0);
		cells.setRangeOutlineBorder(7, 10, 0, 0 ,BorderLineType.THIN,Color.BLACK);
		
		style=cell.getStyle();
		
		style.setTextWrapped(true);
		style.setHAlignment(TextAlignmentType.CENTER);
		style.setVAlignment(TextAlignmentType.CENTER);
		cell.setStyle(style);
		
		
		cell = cells.getCell(GetExcelColumnName(index)+"8"); cell.setValue("NHÀ PHÂN PHỐI");index++;
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		ReportAPI.mergeCells(worksheet, 7, 10, 1, 1);
		cells.setRangeOutlineBorder(7, 10, 1, 1 ,BorderLineType.THIN,Color.BLACK);
		style=cell.getStyle();
		style.setTextWrapped(true);
		style.setHAlignment(TextAlignmentType.CENTER);
		style.setVAlignment(TextAlignmentType.CENTER);
		cell.setStyle(style);
		
		
		
		cell = cells.getCell(GetExcelColumnName(index)+"8"); cell.setValue("DOANH SỐ BÁN RA");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		ReportAPI.mergeCells(worksheet, 7, 7, 2, 5);
		cells.setRangeOutlineBorder(7, 7, 2, 5 ,BorderLineType.THIN,Color.BLACK);
		style=cell.getStyle();
		style.setTextWrapped(true);
		style.setHAlignment(TextAlignmentType.CENTER);
		style.setVAlignment(TextAlignmentType.CENTER);
		cell.setStyle(style);
		
		
		cell = cells.getCell(GetExcelColumnName(index)+"9"); cell.setValue("CHỈ TIÊU SẢN LƯỢNG");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		cells.setColumnWidth(2, 20.0f);
		ReportAPI.mergeCells(worksheet, 8, 9, 2, 2);
		cells.setRangeOutlineBorder(8, 9, 2, 2 ,BorderLineType.THIN,Color.BLACK);
		style=cell.getStyle();
		style.setTextWrapped(true);
		style.setHAlignment(TextAlignmentType.CENTER);
		style.setVAlignment(TextAlignmentType.CENTER);
		cell.setStyle(style);
		
		cell = cells.getCell(GetExcelColumnName(index)+"10"); cell.setValue("KG");index++;
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		
		cell = cells.getCell(GetExcelColumnName(index)+"9"); cell.setValue("THỰC HIỆN");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		ReportAPI.mergeCells(worksheet, 8, 8, 3, 4);
		cells.setRangeOutlineBorder(8, 8, 3, 4 ,BorderLineType.THIN,Color.BLACK);
		style=cell.getStyle();
		style.setTextWrapped(true);
		style.setHAlignment(TextAlignmentType.CENTER);
		style.setVAlignment(TextAlignmentType.CENTER);
		cell.setStyle(style);
	
		
		cell = cells.getCell(GetExcelColumnName(index)+"10"); cell.setValue("SẢN LƯỢNG");	index++;
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		cells.setColumnWidth(3, 15.0f);
		cells.setRowHeight(9,25.0f);
		ReportAPI.mergeCells(worksheet, 9, 9, 3, 3);
		cells.setRangeOutlineBorder(9, 9, 3, 3 ,BorderLineType.THIN,Color.BLACK);
		style=cell.getStyle();
		style.setTextWrapped(true);
		style.setHAlignment(TextAlignmentType.CENTER);
		style.setVAlignment(TextAlignmentType.CENTER);
		cell.setStyle(style);
		
		
		cell = cells.getCell( GetExcelColumnName(index)+"10");cell.setValue("DOANH THU");  	  index++;
		cells.setColumnWidth(4, 15.0f);
		style=cell.getStyle();
		
		style.setTextWrapped(true);
		style.setHAlignment(TextAlignmentType.CENTER);
		style.setVAlignment(TextAlignmentType.CENTER);
		cell.setStyle(style);
		
		
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		ReportAPI.mergeCells(worksheet, 9, 9, 4, 4);
		cells.setRangeOutlineBorder(9, 9, 4, 4 ,BorderLineType.THIN,Color.BLACK);		
		
		cell = cells.getCell( GetExcelColumnName(index)+"9");cell.setValue("%ĐẠT");  	  index++;
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		ReportAPI.mergeCells(worksheet, 8, 9, 5, 5);
		cells.setRangeOutlineBorder(8, 9, 5, 5 ,BorderLineType.THIN,Color.BLACK);
		
		
		cell = cells.getCell( GetExcelColumnName(index)+"8");cell.setValue("MỨC CHIẾT KHẤU");  	  index++;
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		
		
		/*String sql="select distinct nhomsanpham_fk,nsp.ten as ten  from  chitieunpp_ddkd_nhomsp "+  
				" inner join chitieunpp b on b.pk_Seq=chitieunpp_fk  "+
				" inner join nhomsanpham nsp on nsp.pk_seq=nhomsanpham_fk "+
				" where b.thang="+obj.getMonth()+" and b.nam="+ obj.getYear() ;
		dbutils db=new dbutils();
		if(obj.getdvkdId().length()>0)
		{
			sql=sql+ " and b.dvkd_fk= "+ obj.getdvkdId();
			
		}
		if(obj.getkenhId().length()>0)
		{
			sql=sql+ " and b.kenh_fk= "+ obj.getkenhId();
			
		}
		ResultSet rs=db.get(sql);
		if(rs!=null)
		{
			try 
			{
				while (rs.next())
				{		 
					cell = cells.getCell(GetExcelColumnName(index)+"8"); cell.setValue(rs.getString("ten"));	index++;
					cell = cells.getCell(GetExcelColumnName(index)+"9"); cell.setValue("GIAO CHỈ TIÊU");	index++;
					cell = cells.getCell(GetExcelColumnName(index)+"9"); cell.setValue("THỰC HIỆN");index++;
					cell = cells.getCell(GetExcelColumnName(index)+"9"); cell.setValue("% ĐẠT");index++;
					cell = cells.getCell(GetExcelColumnName(index)+"9"); cell.setValue("MỨC CHIẾT KHẤU");index++;
				}
				rs.close();
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		
		//Phan Chi Tieu Pri
		sql="select distinct nhomsp_fk as nhomsanpham_fk ,nsp.ten as ten  from  chitieu_nhapp_nhomsp "+  
				" inner join chitieu b on b.pk_Seq=chitieu_fk  "+
				" inner join nhomsanpham nsp on nsp.pk_seq=nhomsp_fk "+
				" where b.thang="+obj.getMonth()+" and b.nam="+ obj.getYear() ;
		
		if(obj.getdvkdId().length()>0){
			sql=sql+ " and b.dvkd_fk= "+ obj.getdvkdId();
			
		}
		if(obj.getkenhId().length()>0){
			sql=sql+ " and b.kenh_fk= "+ obj.getkenhId();
			
		}
		System.out.println("Thong Tin : "+sql);
		
		rs=db.get(sql);
		
		if(rs!=null)
		{
			try 
			{
				while (rs.next())
				{
					cell = cells.getCell(GetExcelColumnName(index)+"8"); cell.setValue(rs.getString("ten"));	index++;
					cell = cells.getCell(GetExcelColumnName(index)+"9"); cell.setValue("GIAO CHỈ TIÊU");	index++;
					cell = cells.getCell(GetExcelColumnName(index)+"9"); cell.setValue("THỰC HIỆN");index++;
					cell = cells.getCell(GetExcelColumnName(index)+"9"); cell.setValue("% ĐẠT");index++;
					cell = cells.getCell(GetExcelColumnName(index)+"9"); cell.setValue("MỨC CHIẾT KHẤU");index++;
				}
				rs.close();
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		
		cell = cells.getCell(GetExcelColumnName(index)+"1"); cell.setValue("ChiTieuPri");	index++;
		cell = cells.getCell(GetExcelColumnName(index)+"1"); cell.setValue("ThucDatPri");index++;
		cell = cells.getCell(GetExcelColumnName(index)+"1"); cell.setValue("PhanTramPri");
		
		sql="select loaichitieu from chitieu_sec where thang="+obj.getMonth()+" and nam="+obj.getYear() +" and trangthai<>'2'";
		//loai chi tieu sanluong la 2
		ResultSet rscheck=db.get(sql);
		try
		{
			if(rscheck.next())
			{
				if(rscheck.getString("loaichitieu").equals("1"))
				{
					cells.setRowHeight(5, 18.0f);
					cell = cells.getCell("A6");
					ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Báo cáo chia chỉ tiêu  theo đơn vị tiền tệ ");
				}else
				{
					cells.setRowHeight(5, 18.0f);
					cell = cells.getCell("A6");
					ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Báo cáo chỉ tiêu theo sản lượng");
				}
				rscheck.close();
			}*/
		
		/*}catch(Exception er)
		{
			
		}*/
	}
	
	private boolean FillData(Workbook workbook, IStockintransit obj, String query) throws Exception
	{
		dbutils db = new dbutils();		
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);		
		Cells cells = worksheet.getCells();
		ResultSet hdRs = db.get(query);
		int i = 12;
		String kvTen_Prev="";
		String kvTEN="";
		Style style;
		Cell cell = null;
		while (hdRs.next())
			{
				kvTEN=hdRs.getString("kvTEN");
				if(!kvTen_Prev.equals(kvTEN))
				{
					kvTen_Prev=hdRs.getString("kvTEN");
					cell = cells.getCell("A" + Integer.toString(i));
					cell.setValue(kvTEN);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
					ReportAPI.mergeCells(worksheet, i-1, i-1+hdRs.getInt("Row")-1, 0, 0);
					cells.setRangeOutlineBorder( i-1, i-1+hdRs.getInt("Row")-1, 0, 0 ,BorderLineType.THIN,Color.BLACK);
					
					style=cell.getStyle();
					style.setTextWrapped(true);
					style.setHAlignment(TextAlignmentType.CENTER);
					style.setVAlignment(TextAlignmentType.CENTER);
					cell.setStyle(style);
				}
				else 
				{
					cell = cells.getCell("A" + Integer.toString(i));
					cell.setValue("");
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.NONE, true, 0);
				}
				cell = cells.getCell("B" + Integer.toString(i));
				cell.setValue(hdRs.getString("nppTEN").trim().toUpperCase());
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);			
				
				cell = cells.getCell("C" + Integer.toString(i));
				cell.setValue(hdRs.getDouble("DONHANG"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);			
				
				cell = cells.getCell("D" + Integer.toString(i));
				cell.setValue(hdRs.getDouble("DATDONHANG"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
	
				i++;
			}
		return true;
	}
	
	private String GetExcelColumnName(int columnNumber)
	{
		int dividend = columnNumber;
		String columnName = "";
		int modulo;
		
		while (dividend > 0)
		{
			modulo = (dividend - 1) % 26;
			columnName = (char)(65 + modulo) + columnName;
			dividend = (int)((dividend - modulo) / 26);
		} 
		
		return columnName;
	}
	
	
	
}
