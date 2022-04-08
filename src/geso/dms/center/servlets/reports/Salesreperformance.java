package geso.dms.center.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;

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


public class Salesreperformance extends HttpServlet {
	private static final long serialVersionUID = 1L; 
	public Salesreperformance() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		String nextJSP = "/AHF/pages/Center/Salesreperfomancenew.jsp";
		response.sendRedirect(nextJSP);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();	
		Utility util = new Utility();

		obj.setuserId((String)session.getAttribute("userId")==null?"":
			(String) session.getAttribute("userId"));

		obj.setuserTen((String)session.getAttribute("userTen")==null? "":
			(String) session.getAttribute("userTen"));

		obj.setnppId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")))==null?"":
			util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"))));

		obj.setkenhId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")))==null? "":
			util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId"))));

		obj.setdvkdId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")))==null? "":
			util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId"))));

		obj.setMonth(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("month")))==null? "":
			util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("month"))));

		obj.setYear(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("year")))==null? "":
			util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("year"))));	   	 

		obj.setvungId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")))==null? "":
			util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"))));	   	 

		obj.setkhuvucId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")))==null? "":
			util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"))));	 


		obj.setdvdlId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvdlId")))==null? "":
			util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvdlId"))));		 

		obj.setDdkd(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdId")))==null? "":
			util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdId"))));


		String []fieldsHien = request.getParameterValues("fieldsHien");
		obj.setFieldShow(fieldsHien);		 


		String nextJSP = "/AHF/pages/Center/Salesreperfomancenew.jsp";		 
		try{
			String action=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));
			if(action.equals("Taomoi")){
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", 
						"attachment; filename=ThucHienChiTieuTT.xlsm");
				OutputStream out = response.getOutputStream();
				String query = setQuery(obj);
				CreatePivotTable(out,obj,query);
			}			
		}catch(Exception ex){
			obj.setMsg(ex.getMessage());
		}
		obj.init();	    
		session.setAttribute("obj", obj);
		session.setAttribute("userId", obj.getuserId());		
		response.sendRedirect(nextJSP);
	}
	private String setQuery( IStockintransit obj) {

		String fromYear = obj.getYear();
		String fromMonth = obj.getMonth();
		String fromDate=fromYear+'-'+fromMonth;
		String query="";
		geso.dms.center.db.sql.dbutils db=new geso.dms.center.db.sql.dbutils();
		String sql="select distinct nhomsp_fk as nhomsanpham_fk ,dbo.ftBoDau(nsp.ten) as ten  from  ChiTieuNhanVien_DDKD_NHOMSP "+  
				" inner join ChiTieuNhanVien b on b.pk_Seq=ChiTieuNhanVien_fk  "+
				" inner join nhomsanpham nsp on nsp.pk_seq=nhomsp_fk "+
				" where b.thang="+obj.getMonth()+" and b.nam="+ obj.getYear() ;
		ResultSet rs=db.get(sql);
		String chuoi="";
		String[] arraychuoi= new String[20];
		String chuoiselct=" '' ";
		String chuoingoac="[0]";//co dau []
		if(rs!=null){
			int i=0;
			try {
				while (rs.next()){

					if(i==0){
						chuoingoac="["+rs.getString("nhomsanpham_fk")+"]";
						chuoi=rs.getString("nhomsanpham_fk");
						chuoiselct="isnull(ctBan.["+rs.getString("nhomsanpham_fk")+"],0) AS ctBan"+rs.getString("nhomsanpham_fk")+",ISNULL(tdBan.["+rs.getString("nhomsanpham_fk")+"],0) AS tdBan"+rs.getString("nhomsanpham_fk");
					}else{
						chuoi=chuoi+","+rs.getString("nhomsanpham_fk");
						chuoiselct=chuoiselct+", isnull(ctBan.["+rs.getString("nhomsanpham_fk")+"],0) AS ctBan"+rs.getString("nhomsanpham_fk")+",ISNULL(DS.["+rs.getString("nhomsanpham_fk")+"],0) AS tdBan"+rs.getString("nhomsanpham_fk");
						chuoingoac=chuoingoac+",["+rs.getString("nhomsanpham_fk")+"]";
					}
					arraychuoi[i]=rs.getString("nhomsanpham_fk");
					i++;

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		query=
				"\n select vTen,kvTen,ttTEN,gsTEN,gsMa,nppMA,nppTEN,nvMa,nvTEN,ctDs,dsBan.DoanhSo as tdDS,"+
				"\n isnull(dsBan.DoanhSo*100/ctDs,0) as '%', isnull(tctBan.Thuong,0) as ThuongDS, SoDonHang,isnull(sodh, 0)as sodh,isnull(tctSodh.Thuong,0) as ThuongDH,"+
				"\n giatritbdonhang, isnull(Gttbdh, 0)as gttbdh,isnull(tctGttb.Thuong,0) as ThuongTBDH, SoKhachHangMoi, isnull(soKHmoi.sokhmoi,0) as khmoi, isnull(tctSokhmoi.Thuong,0) as ThuongKHMoi, "+
				"\n SoKhachHangMuaHang, ISNULL(sokh,0) as sokhds, ISNULL(tctSokhmoi.Thuong,0) as ThuongKHds,"+chuoiselct+"  "+ 
				"\n from  "+ 
				"\n	(  "+ 
				"\n		select  v.TEN as vTen,kv.TEN as kvTen,tt.TEN as ttTEN,gs.SmartId as gsMa,gs.ten as gsTen,d.MA as nppMA,D.TEN as nppTEN,c.SMARTID as nvMa,c.TEN as nvTEN,"+
				"\n b.ChiTieu  as ctDs,b.SoDonHang, b.GiaTriTBDonHang,b.SoKhachHangMoi,b.SoKhachHangMuaHang,  "+ 
				"\n				a.Nam, a.Thang, b.DDKD_FK,b.NPP_FK,b.GSBH_FK,"+chuoingoac+"  "+ 
				"\n		from ChiTieuNhanVien a inner join ChiTieuNhanVien_DDKD  b on a.Pk_Seq=b.ChiTieuNhanVien_FK  "+ 
				"\n		left join  "+ 
				"\n		(    "+ 
				"\n			select ChiTieuNhanVien_FK,DDKD_fk,"+chuoingoac+"  "+ 
				"\n			from   "+ 
				"\n			(    "+ 
				"\n				select ChiTieuNhanVien_FK,DDKD_fk,CHITIEU,nhomsp_fk    "+ 
				"\n				from ChiTieuNhanVien_DDKD_NHOMSP     "+ 
				"\n			) as nhom pivot  ( sum(CHITIEU) for nhomsp_fk in ("+chuoingoac+"  )) as t   "+ 
				"\n		)as aa on aa.ChiTieuNhanVien_FK=b.ChiTieuNhanVien_FK and aa.ddkd_fk=b.ddkd_fk    "+ 
				"\n	inner join DAIDIENKINHDOANH c on c.PK_SEQ=b.DDKD_FK  "+ 
				"\n	inner join GiamSatBanHang gs on gs.pk_seq=b.GSBH_FK 														"+														
				"\n	inner join NHAPHANPHOI d on d.PK_SEQ=b.NPP_FK  "+ 
				"\n	inner join KHUVUC kv on kv.PK_SEQ=d.KHUVUC_FK  "+ 
				"\n	inner join VUNG v on v.PK_SEQ=kv.VUNG_FK  "+ 
				"\n	inner join TINHTHANH tt on tt.PK_SEQ=d.TINHTHANH_FK  " +
				"\n  where a.thang ='"+fromMonth+"' and a.Nam='"+fromYear+"' and a.trangthai = 1 														"+ 
				"\n )as ctBan  "+ 
				"\n left join   "+ 
				"\n (  "+ 
				"\n	select a.NPP_FK,a.DDKD_FK,a.GSBH_FK,SUM(b.SoLuong*b.dongiagoc) as DoanhSo, count(distinct b.DONHANG_FK) as sodh,"+
				"\n SUM(b.SoLuong*b.dongiagoc)/count(distinct b.DONHANG_FK) as Gttbdh, count(distinct a.KHACHHANG_FK) as sokh "+ 
				"\n	 from DONHANG a  "+ 
				"\n		inner join DONHANG_SANPHAM b on b.DONHANG_FK=a.PK_SEQ  "+ 
				"\n	 where a.TRANGTHAI=1 and a.NgayNhap like  '"+fromDate+"%' "+ 
				"\n	 group by a.NPP_FK,a.DDKD_FK,a.GSBH_FK  "+ 
				"\n )as dsBan on dsBan.DDKD_FK=ctBan.DDKD_FK and dsBan.GSBH_FK=ctBan.GSBH_FK  "+ 
				"\n and dsBan.NPP_FK=ctBan.NPP_FK  "+ 
				"\n left join   "+ 
				"\n (  "+ 
				"\n    select t.NPP_FK,t.DDKD_FK,t.GSBH_FK,"+chuoingoac+"  "+ 
				"\n    from  "+ 
				"\n	(  "+ 
				"\n	 select a.NPP_FK,a.DDKD_FK,a.GSBH_FK,SUM(b.SoLuong) as SoLuong,c.NSP_FK  "+ 
				"\n	 from DONHANG a  "+ 
				"\n		inner join DONHANG_SANPHAM b on b.DONHANG_FK=a.PK_SEQ  "+ 
				"\n		inner join NHOMSANPHAM_SANPHAM  c on c.SP_FK=b.SANPHAM_FK  "+ 
				"\n	 where a.TRANGTHAI=1 and a.NgayNhap like  '"+fromDate+"%'   "+ 
				"\n	 group by a.NPP_FK,a.DDKD_FK,a.GSBH_FK,c.NSP_FK  "+ 
				"\n	 )as t pivot  ( sum(SoLuong) for NSP_FK in ( "+chuoingoac+"  )) as t   "+ 
				"\n )as tdBan on tdBan.DDKD_FK=ctBan.DDKD_FK and tdBan.NPP_FK=ctBan.NPP_FK  "+ 
				"\n	and tdBan.GSBH_FK=ctBan.GSBH_FK  "+
				"\n left join "+
				"\n ( "+
				"\n	select count(distinct a.pk_seq) as sokhmoi, a.NPP_FK,c.DDKD_FK from KHACHHANG a inner join KHACHHANG_TUYENBH b on a.PK_SEQ = b.KHACHHANG_FK "+ 
				"\n	inner join TUYENBANHANG c on b.TBH_FK = c.PK_SEQ "+
				"\n	where a.ngaytao like '"+fromDate+"%' and a.TRANGTHAI = 1 "+
				"\n	group by a.NPP_FK,c.DDKD_FK "+
				"\n )as soKHmoi on soKHmoi.DDKD_FK = ctBan.DDKD_FK and soKHmoi.NPP_FK=ctBan.NPP_FK "+  
				"\n left join "+ 
				"\n ( "+
				"\n	select thang, nam, TU, den, thuong from TIEUCHITINHTHUONG a inner join TIEUCHITINHTHUONG_CHITIET b on a.PK_SEQ = b.TIEUCHITINHTHUONG_FK "+
				"\n	where TRANGTHAI = 1 and LOAI = 1 and tieuchi = 1 "+
				"\n ) as tctBan on tctBan.NAM = ctBan.Nam and tctBan.THANG = ctBan.Thang and "+
				"\n case when ctDs > 0 then dsBan.DoanhSo*100/ctDs else -1 end between tctBan.Tu and tctBan.Den "+
				"\n left join "+ 
				"\n (  "+
				"\n	select thang, nam, TU, den, thuong from TIEUCHITINHTHUONG a inner join TIEUCHITINHTHUONG_CHITIET b on a.PK_SEQ = b.TIEUCHITINHTHUONG_FK "+ 
				"\n	where TRANGTHAI = 1 and LOAI = 1 and tieuchi = 2  "+
				"\n ) as tctSodh on tctSodh.NAM = ctBan.Nam and tctSodh.THANG = ctBan.Thang and "+ 
				"\n case when sodonhang > 0 then dsBan.sodh*100/SoDonHang else -1 end between tctSodh.Tu and tctSodh.Den "+
				"\n left join "+ 
				"\n (  "+
				"\n	select thang, nam, TU, den, thuong from TIEUCHITINHTHUONG a inner join TIEUCHITINHTHUONG_CHITIET b on a.PK_SEQ = b.TIEUCHITINHTHUONG_FK "+ 
				"\n	where TRANGTHAI = 1 and LOAI = 1 and tieuchi = 3  "+
				"\n ) as tctGttb on tctGttb.NAM = ctBan.Nam and tctGttb.THANG = ctBan.Thang and "+ 
				"\n case when giatritbdonhang > 0 then dsBan.Gttbdh*100/giatritbdonhang else -1 end between tctGttb.Tu and tctGttb.Den "+
				"\n left join  "+
				"\n (  "+
				"\n	select thang, nam, TU, den, thuong from TIEUCHITINHTHUONG a inner join TIEUCHITINHTHUONG_CHITIET b on a.PK_SEQ = b.TIEUCHITINHTHUONG_FK "+ 
				"\n	where TRANGTHAI = 1 and LOAI = 1 and tieuchi = 4  "+
				"\n ) as tctSokhmoi on tctSokhmoi.NAM = ctBan.Nam and tctSokhmoi.THANG = ctBan.Thang and "+ 
				"\n case when SoKhachHangMoi > 0 then soKHmoi.sokhmoi*100/SoKhachHangMoi else -1 end between tctSokhmoi.Tu and tctSokhmoi.Den "+
				"\n left join  "+
				"\n (  "+
				"\n	select thang, nam, TU, den, thuong from TIEUCHITINHTHUONG a inner join TIEUCHITINHTHUONG_CHITIET b on a.PK_SEQ = b.TIEUCHITINHTHUONG_FK "+ 
				"\n	where TRANGTHAI = 1 and LOAI = 1 and tieuchi = 5  "+
				"\n ) as tctSokhds on tctSokhds.NAM = ctBan.Nam and tctSokhds.THANG = ctBan.Thang and "+ 
				"\n case when SoKhachHangMuaHang > 0 then dsBan.sokh*100/SoKhachHangMuaHang else -1 end between tctSokhds.Tu and tctSokhds.Den";

		System.out.println("[Salesreperformance]"+query);
		return query;
	}

	private void CreatePivotTable(OutputStream out,IStockintransit obj,String query) throws Exception
	{   
		try{
			//String chuoi=getServletContext().getInitParameter("path") + "\\ThucHienChiTieuTT.xlsm";
			//FileInputStream fstream = new FileInputStream(chuoi);
			File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\ThucHienChiTieuTT.xlsm");
			FileInputStream fstream = new FileInputStream(f) ;
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

			CreateStaticHeader(workbook,obj); 
			FillData(workbook,obj.getFieldShow(), query, obj); 
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
		cell.setValue("TÌNH HÌNH THỰC HIỆN CHỈ TIÊU ĐẠI DIỆN KINH DOANH");

		style = cell.getStyle();

		Font font2 = new Font();
		font2.setColor(Color.RED);// mau chu
		font2.setSize(14);// size chu
		font2.setBold(true);
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu
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

		int i = 105;
		cell = cells.getCell(GetExcelColumnName(i++)+"1"); cell.setValue("Vung");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("KhuVuc");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("TinhThanh");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1"); cell.setValue("MaNhaPhanPhoi");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("NhaPhanPhoi");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("MaGiamSat");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1"); cell.setValue("GiamSat");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("MaNhanVien");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("NhanVien");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ChiTieuDoanhSo");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ThucDatDoanhSo");
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("%DoanhSo");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ThuongDoanhSo");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ChiTieuSoDonHang");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ThucDatSoDonHang");
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("%SoDonHang");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ThuongSoDonHang");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ChiTieuGiaTriTrungBinh");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ThucDatGiaTriTrungBinh");
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("%GiaTriTrungBinh");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ThuongGiaTriTrungBinh");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ChiTieuSoKhachHangMoi");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ThucDatSoKhachHangMoi");
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("%SoKhachHangMoi");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ThuongSoKhachHangMoi");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ChiTieuSoKhachHangMuaHang");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ThucDatSoKhachHangMuaHang");
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("%SoKhachHangMuaHang");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ThuongSoKhachHangMuaHang");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);


		String sql = "select distinct nhomsp_fk as nhomsanpham_fk,dbo.ftBoDau(nsp.ten) as ten  from  ChiTieuNhanVien_DDKD_NHOMSP "+  
				" inner join ChiTieuNhanVien b on b.pk_Seq=ChiTieuNhanVien_fk  "+
				" inner join nhomsanpham nsp on nsp.pk_seq=nhomsp_fk "+
				" where b.thang="+obj.getMonth()+" and b.nam="+ obj.getYear() ;
		dbutils db=new dbutils();


		ResultSet rs=db.get(sql);

		if(rs!=null){

			try {
				while (rs.next())
				{
					cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ChiTieu-"+rs.getString("ten"));	
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ThucDat-"+rs.getString("ten"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("PhanTram-"+rs.getString("ten"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				}
				rs.close();
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}

		cell = cells.getCell("M1");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, ( i-1 ) + "");

	}

	private void FillData(Workbook workbook,String[] fieldShow, String query, IStockintransit obj)throws Exception 
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		dbutils db = new dbutils();		
		int indexCol = 2;
		int indexRow=2;
		String colID="";


		//Lay danh sach mang SALEOUT,CHO NAY NO RA HET MANG SALESUP,TRONG KHI CAI
		// NHÓM THƯỞNG SALESOUT ISNHOMTHUONGSALESOUT=1
		String sql="select distinct nhomsp_fk as nhomsanpham_fk,dbo.ftBoDau(nsp.ten) as ten  from  ChiTieuNhanVien_DDKD_NHOMSP "+  
				" inner join ChiTieuNhanVien b on b.pk_Seq=ChiTieuNhanVien_fk  "+
				" inner join nhomsanpham nsp on nsp.pk_seq=nhomsp_fk "+
				" where b.thang="+obj.getMonth()+" and b.nam="+ obj.getYear() ;

		System.out.println("[NhomSaleOut]"+sql );
		ResultSet	rs = db.get(sql);
		String[] thuongsalesout = new String[20];
		int i = 0;
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					thuongsalesout[i] = rs.getString("nhomsanpham_fk");
					i++;
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}


		rs=db.get(query);
		try 
		{				

			Cell cell = null;
			while(rs.next())
			{ 				
				indexCol = 105;
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getString("vTen"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);

				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getString("kvTen"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);

				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getString("ttTen"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);

				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getString("nppMa"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);

				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getString("nppTen"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);

				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getString("gsMa"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);

				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getString("gsTen"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);

				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getString("nvMa"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);

				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getString("nvTen"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);

				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getDouble("ctDS"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);

				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getDouble("tdDS"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);

				float phantramMTD = 0;
				if (rs.getFloat("ctDS") > 0)
				{
					phantramMTD = rs.getFloat("tdDS") / rs.getFloat("ctDS" );
				}
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(phantramMTD);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 10);
				
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getDouble("ThuongDS"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);

				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getDouble("SoDonHang"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
				
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getDouble("sodh"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
				
				phantramMTD = 0;
				if (rs.getFloat("SoDonHang") > 0)
				{
					phantramMTD = rs.getFloat("sodh") / rs.getFloat("SoDonHang" );
				}
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(phantramMTD);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 10);
				
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getDouble("ThuongDH"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
				
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getDouble("giatritbdonhang"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
				
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getDouble("gttbdh"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
				
				phantramMTD = 0;
				if (rs.getFloat("giatritbdonhang") > 0)
				{
					phantramMTD = rs.getFloat("gttbdh") / rs.getFloat("giatritbdonhang" );
				}
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(phantramMTD);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 10);
				
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getDouble("ThuongTBDH"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
				
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getDouble("SoKhachHangMoi"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
				
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getDouble("khmoi"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
				
				phantramMTD = 0;
				if (rs.getFloat("SoKhachHangMoi") > 0)
				{
					phantramMTD = rs.getFloat("khmoi") / rs.getFloat("SoKhachHangMoi" );
				}
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(phantramMTD);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 10);
				
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getDouble("ThuongKHMoi"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
				
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getDouble("SoKhachHangMuaHang"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
				
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getDouble("sokhds"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
				
				phantramMTD = 0;
				if (rs.getFloat("SoKhachHangMuaHang") > 0)
				{
					phantramMTD = rs.getFloat("sokhds") / rs.getFloat("SoKhachHangMuaHang" );
				}
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(phantramMTD);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 10);
				
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getDouble("ThuongKHds"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);

				for (i = 0; i < thuongsalesout.length; i++)
				{
					if (thuongsalesout[i] != null)
					{
						cell = cells.getCell(GetExcelColumnName(indexCol++) + Integer.toString(indexRow));
						cell.setValue(rs.getFloat("ctBan" + thuongsalesout[i]));
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);

						cell = cells.getCell(GetExcelColumnName(indexCol++) + Integer.toString(indexRow));
						cell.setValue(rs.getFloat("tdBan" + thuongsalesout[i]));
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
						phantramMTD = 0;

						if (rs.getFloat("ctBan" + thuongsalesout[i]) > 0)
						{
							phantramMTD = rs.getFloat("tdBan" + thuongsalesout[i])  / rs.getFloat("ctBan" + thuongsalesout[i]);
						}

						cell = cells.getCell(GetExcelColumnName(indexCol++) + Integer.toString(indexRow));
						cell.setValue(phantramMTD);

						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 10);
					}
				}


				indexRow++;
			}
			if(rs != null) rs.close();
			if(db!=null){
				db.shutDown();
			}
		}catch(java.sql.SQLException err){
			err.printStackTrace();
			System.out.println(err.toString());
			throw new Exception("Khong the tao duoc bao cao trong thoi gian nay. Error :"+err.toString());
		}


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
