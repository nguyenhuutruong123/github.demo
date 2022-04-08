package geso.dms.center.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

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


public class BMPerformance extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BMPerformance() {
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
		String nextJSP = "/AHF/pages/Center/BMPerformance.jsp";
		response.sendRedirect(nextJSP);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
	    IStockintransit obj = new Stockintransit();	
	    Utility util = new Utility();
	  
	    obj.setuserId((String)session.getAttribute("userId")==null?"":(String) session.getAttribute("userId"));
	    
	    obj.setuserTen((String)session.getAttribute("userTen")==null? "":(String) session.getAttribute("userTen"));
	       	 	
   	 	obj.setkenhId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId"))==null? "":util.antiSQLInspection(request.getParameter("kenhId"))));
   	 	
	   	obj.setdvkdId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId"))==null? "":util.antiSQLInspection(request.getParameter("dvkdId"))));
	   	 
	   	obj.setMonth(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("month"))==null? "":util.antiSQLInspection(request.getParameter("month"))));
	   	 
	   	obj.setYear(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("year"))==null? "":util.antiSQLInspection(request.getParameter("year"))));	   	 
	 	 
	   	obj.setvungId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"))==null? "":util.antiSQLInspection(request.getParameter("vungId"))));	   	 
	   	    	 	   	    	
		obj.setdvkdId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId"))==null? "":util.antiSQLInspection(request.getParameter("dvkdId"))));		 
		
		obj.setBMId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("bmId"))==null? "":util.antiSQLInspection(request.getParameter("bmId"))));
	 
		 
		String nextJSP = "/AHF/pages/Center/BMPerformance.jsp";		 
		try{
			String action=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));
			if(action.equals("Taomoi")){
				response.setContentType("application/xlsm");
		        response.setHeader("Content-Disposition", "attachment; filename=BMThucHienChiTieuTT.xlsm");
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
						chuoiselct="sum(isnull(ctBan.["+rs.getString("nhomsanpham_fk")+"],0)) AS ctBan"+rs.getString("nhomsanpham_fk")+", sum(ISNULL(tdBan.["+rs.getString("nhomsanpham_fk")+"],0)) AS tdBan"+rs.getString("nhomsanpham_fk");
					}else{
						chuoi=chuoi+","+rs.getString("nhomsanpham_fk");
						chuoiselct=chuoiselct+", sum(isnull(ctBan.["+rs.getString("nhomsanpham_fk")+"],0)) AS ctBan"+rs.getString("nhomsanpham_fk")+", sum(ISNULL(DS.["+rs.getString("nhomsanpham_fk")+"],0)) AS tdBan"+rs.getString("nhomsanpham_fk");
						chuoingoac=chuoingoac+",["+rs.getString("nhomsanpham_fk")+"]";
					}
					arraychuoi[i]=rs.getString("nhomsanpham_fk");
					i++;

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		sql = "select distinct nhomsp_fk as nhomsanpham_fk ,dbo.ftBoDau(nsp.ten) as ten "+
				" from CHITIEU a inner join CHITIEU_NHAPP_NHOMSP b on a.PK_SEQ = b.CHITIEU_FK "+
				" inner join NHOMSANPHAM nsp on b.NHOMSP_FK = nsp.PK_SEQ "+
				" where b.nhomsp_fk != 0 and a.thang="+obj.getMonth()+" and a.nam="+ obj.getYear() ;
		rs=db.get(sql);
		String chuoisi="";
		String[] arraychuoisi= new String[20];
		String chuoiselctsi=" '' ";
		String chuoingoacsi="[0]";//co dau []
		String chuoingoacsumsi="sum([0]) as [0]";
		if(rs!=null){
			int i=0;
			try {
				while (rs.next()){

					if(i==0){
						chuoingoacsumsi="sum(["+rs.getString("nhomsanpham_fk")+"]) as ["+rs.getString("nhomsanpham_fk")+"]";
						chuoingoacsi="["+rs.getString("nhomsanpham_fk")+"]";
						chuoisi=rs.getString("nhomsanpham_fk");
						chuoiselctsi="isnull(ctSIn.["+rs.getString("nhomsanpham_fk")+"], 0) as ctSI"+rs.getString("nhomsanpham_fk")+", "+
									"isnull(tdSIn.["+rs.getString("nhomsanpham_fk")+"], 0) as tdSI"+rs.getString("nhomsanpham_fk");
					}else{
						chuoisi=chuoisi+","+rs.getString("nhomsanpham_fk");
						chuoiselctsi=chuoiselctsi+", isnull(ctSIn.["+rs.getString("nhomsanpham_fk")+"], 0) as ctSI"+rs.getString("nhomsanpham_fk")+", "+
								 	"isnull(tdSIn.["+rs.getString("nhomsanpham_fk")+"], 0) as tdSI"+rs.getString("nhomsanpham_fk")+", ";
						chuoingoacsi=chuoingoac+", ["+rs.getString("nhomsanpham_fk")+"]";
						chuoingoacsumsi=chuoingoacsumsi+", sum(["+rs.getString("nhomsanpham_fk")+"]) as ["+rs.getString("nhomsanpham_fk")+"]";
					}
					arraychuoisi[i]=rs.getString("nhomsanpham_fk");
					i++;

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		query=
				"\n select bmTEN, bmMa, ctDs as ctDs, dsBan.DoanhSo as tdDS, ctSI.CHITIEU as ctSI, isnull(tdSI.td, 0) as tdSI,"+
				"\n SoDonHang as sodonhang, isnull(sodh, 0) as sodh,"+
				"\n giatritbdonhang as giatritbdonhang, isnull(Gttbdh, 0) as gttbdh, SoKhachHangMoi as sokhachhangmoi, isnull(soKHmoi.sokhmoi,0) as khmoi, "+
				"\n SoKhachHangMuaHang as sokhachhangmuahang, ISNULL(sokh,0) as sokhds, "+chuoiselct+", "+chuoiselctsi+"  "+ 
				"\n from  "+ 
				"\n	(  "+ 
				"\n		select bm.pk_seq as bmId, bm.SmartId as bmMa, bm.ten as bmTen, sum(b.ChiTieu) as ctDs, sum(b.SoDonHang) as sodonhang, "+
				"\n 			sum(b.SoKhachHangMoi) as sokhachhangmoi, sum(b.SoKhachHangMuaHang) as sokhachhangmuahang, sum(b.GiaTriTBDonHang) as giatritbdonhang, "+ 
				"\n				a.Nam, a.Thang,"+chuoingoac+"  "+ 
				"\n		from ChiTieuNhanVien a inner join ChiTieuNhanVien_BM  b on a.Pk_Seq=b.ChiTieuNhanVien_FK  "+ 
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
				"\n	inner join GiamSatBanHang gs on gs.pk_seq=b.GSBH_FK "+
				"\n inner join BM on bm.pk_seq = b.BM_FK	"+
				"\n	inner join NHAPHANPHOI d on d.PK_SEQ=b.NPP_FK  "+ 
				"\n	inner join KHUVUC kv on kv.PK_SEQ=d.KHUVUC_FK  "+ 
				"\n	inner join VUNG v on v.PK_SEQ=kv.VUNG_FK  "+ 
				"\n	inner join TINHTHANH tt on tt.PK_SEQ=d.TINHTHANH_FK  " +
				"\n where a.thang ='"+fromMonth+"' and a.Nam='"+fromYear+"' and a.trangthai = 1 "+
				"\n group by bm.PK_SEQ, bm.SmartId, bm.ten, a.Nam, a.Thang, "+chuoingoac+" "+
				"\n )as ctBan  "+ 
				"\n left join   "+ 
				"\n (  "+ 
				"\n	 select bm.pk_seq as bmId, SUM(b.SoLuong*b.dongiagoc) as DoanhSo, count(distinct b.DONHANG_FK) as sodh, "+
				"\n  SUM(b.SoLuong*b.dongiagoc)/count(distinct b.DONHANG_FK) as Gttbdh, count(distinct a.KHACHHANG_FK) as sokh  "+ 
				"\n	 from DONHANG a  "+ 
				"\n		inner join DONHANG_SANPHAM b on b.DONHANG_FK=a.PK_SEQ  "+ 
				"\n 	inner join NHAPHANPHOI npp on npp.PK_SEQ = a.NPP_FK "+
				"\n     inner join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK "+
				"\n 	inner join VUNG v on v.PK_SEQ = kv.VUNG_FK "+
				"\n 	inner join BM_CHINHANH bc on bc.vung_fk = v.pk_seq "+
				"\n 	inner join BM on bm.PK_SEQ = bc.bm_FK "+
				"\n	 where a.TRANGTHAI=1 and a.NgayNhap like  '"+fromDate+"%' "+ 
				"\n	 group by bm.pk_seq  "+ 
				"\n )as dsBan on dsBan.bmId = ctBan.bmId  "+ 
				"\n left join   "+ 
				"\n (  "+ 
				"\n    select t.bmId,"+chuoingoac+"  "+ 
				"\n    from  "+ 
				"\n	(  "+ 
				"\n	 select bm.pk_seq as bmId,SUM(b.SoLuong) as SoLuong,c.NSP_FK  "+ 
				"\n	 from DONHANG a  "+ 
				"\n		inner join DONHANG_SANPHAM b on b.DONHANG_FK=a.PK_SEQ  "+ 
				"\n		inner join NHOMSANPHAM_SANPHAM  c on c.SP_FK=b.SANPHAM_FK  "+ 
				"\n		inner join NHAPHANPHOI npp on npp.PK_SEQ = a.NPP_FK "+
				"\n		inner join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK "+
				"\n		inner join VUNG v on v.PK_SEQ = kv.VUNG_FK  "+
				"\n 	inner join BM_CHINHANH bc on bc.vung_fk = v.pk_seq "+
				"\n 	inner join BM on bm.PK_SEQ = bc.bm_FK "+
				"\n	 where a.TRANGTHAI=1 and a.NgayNhap like  '"+fromDate+"%'   "+ 
				"\n	 group by bm.pk_seq, c.NSP_FK  "+ 
				"\n	 )as t pivot  ( sum(SoLuong) for NSP_FK in ( "+chuoingoac+"  )) as t   "+ 
				"\n )as tdBan on tdBan.bmId = ctBan.bmId "+
				"\n left join "+
				"\n ( "+
				"\n	select count(distinct a.pk_seq) as sokhmoi, bm.pk_seq as bmId from KHACHHANG a inner join NHAPHANPHOI npp on npp.PK_SEQ = a.NPP_FK "+ 
				"\n	inner join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK "+
				"\n	inner join VUNG v on v.PK_SEQ = kv.VUNG_FK "+ 
				"\n inner join BM_CHINHANH bc on bc.vung_fk = v.pk_seq "+
				"\n inner join BM on bm.PK_SEQ = bc.bm_FK "+
				"\n	where a.ngaytao >= '"+fromDate+"%' and a.TRANGTHAI = 1 "+
				"\n	group by bm.pk_seq "+
				"\n )as soKHmoi on soKHmoi.bmId = ctBan.bmId "+
				"\n left join   "+
				"\n (	"+
				"\n	select bm.pk_seq as bmId, sum(aa.CHITIEU) as chitieu from CHITIEU a  "+
				"\n		inner join CHITIEU_NHAPP_NHOMSP aa on a.PK_SEQ = aa.CHITIEU_FK "+
				"\n		inner join NHAPHANPHOI d on d.PK_SEQ=aa.NPP_FK  	"+
				"\n		inner join KHUVUC kv on kv.PK_SEQ = d.KHUVUC_FK "+
				"\n		inner join VUNG v on v.PK_SEQ = kv.VUNG_FK "+ 
				"\n 	inner join BM_CHINHANH bc on bc.vung_fk = v.pk_seq "+
				"\n 	inner join BM on bm.PK_SEQ = bc.bm_FK "+
				"\n	where a.thang ='"+fromMonth+"' and a.Nam='"+fromYear+"' and aa.NHOMSP_FK = 0 	 "+
				"\n	group by bm.pk_seq"+
				"\n ) as ctSI on ctSI.bmId = ctBan.bmId "+
				"\n left join  "+
				"\n (	"+
				"\n	select bm.pk_seq as bmId, SUM(b.soluongttduyet * b.DonGiaGoc) as td "+
				"\n	from ERP_DONDATHANG a inner join ERP_DONDATHANG_SANPHAM b on b.dondathang_fk=a.PK_SEQ "+
				"\n	inner join NHAPHANPHOI d on d.PK_SEQ = a.NPP_FK  	"+
				"\n	inner join KHUVUC kv on kv.PK_SEQ = d.KHUVUC_FK "+
				"\n	inner join VUNG v on v.PK_SEQ = kv.VUNG_FK "+
				"\n inner join BM_CHINHANH bc on bc.vung_fk = v.pk_seq "+
				"\n inner join BM on bm.PK_SEQ = bc.bm_FK "+
				"\n	where 1=1  and a.NgayDonHang like '"+fromDate+"%' and a.TRANGTHAI in (2, 4) "+
				"\n	group by bm.pk_seq "+
				"\n ) as tdSI on tdSI.bmId = ctBan.bmId "+
				"\n left join "+
				"\n ( "+
				"\n	select bm.pk_seq as bmId, "+chuoingoacsumsi+" "+
				"\n	from CHITIEU a inner join "+
				"\n	(select CHITIEU_FK, NPP_FK, "+chuoingoacsi+" "+
				"\n		from "+
				"\n		( "+
				"\n			select CHITIEU_FK, NPP_FK, NHOMSP_FK, CHITIEU from CHITIEU_NHAPP_NHOMSP "+
				"\n		) p "+
				"\n	pivot (sum(chitieu) for nhomsp_fk in ( "+chuoingoacsi+" )) AS t) aa on aa.CHITIEU_FK = a.PK_SEQ "+
				"\n	inner join NHAPHANPHOI d on d.PK_SEQ = aa.NPP_FK  	"+
				"\n	inner join KHUVUC kv on kv.PK_SEQ = d.KHUVUC_FK "+
				"\n	inner join VUNG v on v.PK_SEQ = kv.VUNG_FK "+
				"\n inner join BM_CHINHANH bc on bc.vung_fk = v.pk_seq "+
				"\n inner join BM on bm.PK_SEQ = bc.bm_FK "+
				"\n	where a.thang ='"+fromMonth+"' and a.Nam='"+fromYear+"' 	"+
				"\n group by bm.PK_SEQ"+
				"\n ) as ctSIn on ctSIn.bmId = ctBan.bmId "+
				"\n left join "+
				"\n ("+
				"\n		select t.bmId, "+chuoingoacsi+"  "+
				"\n    	from  ( "+
				"\n		select bm.pk_seq as bmId, c.NSP_FK, SUM(b.soluong * b.DonGiaGoc) as td  "+
				"\n		from ERP_DONDATHANG a "+
				"\n		inner join ERP_DONDATHANG_SANPHAM b on b.dondathang_fk=a.PK_SEQ   "+
				"\n		inner join NHAPHANPHOI d on d.PK_SEQ = a.NPP_FK  	"+
				"\n		inner join KHUVUC kv on kv.PK_SEQ = d.KHUVUC_FK "+
				"\n		inner join VUNG v on v.PK_SEQ = kv.VUNG_FK "+
				"\n 	inner join BM_CHINHANH bc on bc.vung_fk = v.pk_seq "+
				"\n 	inner join BM on bm.PK_SEQ = bc.bm_FK "+
				"\n		inner join NHOMSANPHAM_SANPHAM  c on c.SP_FK=b.SANPHAM_FK   "+
				"\n		where 1=1  and a.NgayDonHang like '"+fromDate+"%' and a.TRANGTHAI in (2, 4) "+
				"\n		group by bm.pk_seq, c.NSP_FK "+
				"\n	) as t pivot  ( sum(td) for NSP_FK in ( "+chuoingoacsi+"  )) as t "+
				"\n ) as tdSIn on tdSIn.bmId = ctBan.bmId";

		System.out.println("[BMperformance]"+query);
		
		return query;
	}
	
	private void CreatePivotTable(OutputStream out,IStockintransit obj, String query) throws Exception
    {   
		try{
			
			String chuoi=getServletContext().getInitParameter("path") + "\\BM_ThucHienChiTieuTT.xlsm";
			FileInputStream fstream = new FileInputStream(chuoi);		
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			CreateStaticHeader(workbook,obj);
			
			// FillData_2 de tao du lieu cho nhung SS duoc luan chuyen trong thang
			//int row = FillData_2(workbook, obj);
			int row=2;
			// FillData lay du lieu cho nhung SS khong duoc luan chuyen
			FillData(workbook, obj, row, query); 
			
			workbook.save(out);
			fstream.close();
	     }catch(Exception ex){
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
		cell.setValue("TÌNH HÌNH THỰC HIỆN CHỈ BM");

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
		/*cell = cells.getCell(GetExcelColumnName(i++)+"1"); cell.setValue("Vung");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("KhuVuc");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("TinhThanh");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1"); cell.setValue("MaNhaPhanPhoi");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("NhaPhanPhoi");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);*/
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("MaASM");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1"); cell.setValue("ASM");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		/*cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("MaNhanVien");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("NhanVien");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);*/
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ChiTieuDoanhSo");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ThucDatDoanhSo");
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("%DoanhSo");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ThuongDoanhSo");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);

		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ChiTieuSI");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ThucDatSI");
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("%SI");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ThuongSI");
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
		

		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ChiTieuSI");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ThucDatSI");
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("%SI");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		cell = cells.getCell(GetExcelColumnName(i++)+"1");  cell.setValue("ThuongSI");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
		

		cell = cells.getCell("M1");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, ( i-1 ) + "");
	    
	}

	private void FillData(Workbook workbook,IStockintransit obj, int row, String query)throws Exception 
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
		
		sql="select distinct nhomsp_fk as nhomsanpham_fk ,dbo.ftBoDau(nsp.ten) as ten "+
				" from CHITIEU a inner join CHITIEU_NHAPP_NHOMSP b on a.PK_SEQ = b.CHITIEU_FK "+
				" inner join NHOMSANPHAM nsp on b.NHOMSP_FK = nsp.PK_SEQ "+
				" where b.nhomsp_fk != 0 and a.thang="+obj.getMonth()+" and a.nam="+ obj.getYear() ;
		System.out.println("[NhomSaleIn]"+sql );
		rs = db.get(sql);
		String[] thuongsalein = new String[20];
		i = 0;
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					thuongsalein[i] = rs.getString("nhomsanpham_fk");
					i++;
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		String fromYear = obj.getYear();
		String fromMonth = obj.getMonth();
		ResultSet rsthuong;

		rs=db.get(query);
		try 
		{				

			Cell cell = null;
			while(rs.next())
			{ 				
				indexCol = 105;
				/*cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
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
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);*/

				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getString("bmMa"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);

				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getString("bmTen"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);

				/*cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getString("nvMa"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);

				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getString("nvTen"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);*/


				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getDouble("ctDS"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);

				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getDouble("tdDS"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);

				float		phantramMTD = 0;
				if (rs.getFloat("ctDS") > 0)
				{
					phantramMTD = rs.getFloat("tdDS") / rs.getFloat("ctDS" );
				}
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(phantramMTD);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 10);
				phantramMTD = phantramMTD * 100;
				sql = "select tieuchi, TU, den, thuong from TIEUCHITINHTHUONG a inner join TIEUCHITINHTHUONG_CHITIET b on a.PK_SEQ = b.TIEUCHITINHTHUONG_FK "+ 
						  "\n	where TRANGTHAI = 1 and LOAI = 4 and tieuchi = 1 and nam = "+fromYear+" and thang = '"+fromMonth + "' and "+phantramMTD+" >= tu and "+phantramMTD+" <= den";
				System.out.println("rsthuong ds "+sql);
				rsthuong = db.get(sql);
				boolean next = rsthuong.next();
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				if(next) cell.setValue(rsthuong.getDouble("Thuong"));
				else cell.setValue(0);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
				
				//---------------------------------SALE--IN---------------------------------------------------------
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getDouble("ctSI"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);

				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(rs.getDouble("tdSI"));
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);

				phantramMTD = 0;
				if (rs.getFloat("ctSI") > 0)
				{
					phantramMTD = rs.getFloat("tdSI") / rs.getFloat("ctSI" );
				}
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				cell.setValue(phantramMTD);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 10);
				phantramMTD = phantramMTD * 100;
				sql = "select tieuchi, TU, den, thuong from TIEUCHITINHTHUONG a inner join TIEUCHITINHTHUONG_CHITIET b on a.PK_SEQ = b.TIEUCHITINHTHUONG_FK "+ 
						  "\n	where TRANGTHAI = 1 and LOAI = 5 and tieuchi = 6 and nam = "+fromYear+" and thang = '"+fromMonth + "' and "+phantramMTD+" >= tu and "+phantramMTD+" <= den";
				System.out.println("rsthuong ds "+sql);
				rsthuong = db.get(sql);
				next = rsthuong.next();
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				if(next) cell.setValue(rsthuong.getDouble("Thuong"));
				else cell.setValue(0);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
				
				//---------------------------------SO--DON--HANG---------------------------------------------------

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
				phantramMTD = phantramMTD * 100;
				sql = "select tieuchi, TU, den, thuong from TIEUCHITINHTHUONG a inner join TIEUCHITINHTHUONG_CHITIET b on a.PK_SEQ = b.TIEUCHITINHTHUONG_FK "+ 
						  "\n	where TRANGTHAI = 1 and LOAI = 4 and tieuchi = 2 and nam = "+fromYear+" and thang = '"+fromMonth + "' and "+phantramMTD+" >= tu and "+phantramMTD+" <= den";
				rsthuong = db.get(sql);
				next = rsthuong.next();
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				if(next) cell.setValue(rsthuong.getDouble("Thuong"));
				else cell.setValue(0);
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
				phantramMTD = phantramMTD * 100;
				sql = "select tieuchi, TU, den, thuong from TIEUCHITINHTHUONG a inner join TIEUCHITINHTHUONG_CHITIET b on a.PK_SEQ = b.TIEUCHITINHTHUONG_FK "+ 
						  "\n	where TRANGTHAI = 1 and LOAI = 4 and tieuchi = 3 and nam = "+fromYear+" and thang = '"+fromMonth + "' and "+phantramMTD+" >= tu and "+phantramMTD+" <= den";
				rsthuong = db.get(sql);
				next = rsthuong.next();
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				if(next) cell.setValue(rsthuong.getDouble("Thuong"));
				else cell.setValue(0);
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
				phantramMTD = phantramMTD * 100;
				sql = "select tieuchi, TU, den, thuong from TIEUCHITINHTHUONG a inner join TIEUCHITINHTHUONG_CHITIET b on a.PK_SEQ = b.TIEUCHITINHTHUONG_FK "+ 
						  "\n	where TRANGTHAI = 1 and LOAI = 4 and tieuchi = 4 and nam = "+fromYear+" and thang = '"+fromMonth + "' and "+phantramMTD+" >= tu and "+phantramMTD+" <= den";
				rsthuong = db.get(sql);
				next = rsthuong.next();
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				if(next) cell.setValue(rsthuong.getDouble("Thuong"));
				else cell.setValue(0);
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
				phantramMTD = phantramMTD * 100;
				sql = "select tieuchi, TU, den, thuong from TIEUCHITINHTHUONG a inner join TIEUCHITINHTHUONG_CHITIET b on a.PK_SEQ = b.TIEUCHITINHTHUONG_FK "+ 
						  "\n	where TRANGTHAI = 1 and LOAI = 4 and tieuchi = 5 and nam = "+fromYear+" and thang = '"+fromMonth + "' and "+phantramMTD+" >= tu and "+phantramMTD+" <= den";
				rsthuong = db.get(sql);
				next = rsthuong.next();
				cell = cells.getCell(GetExcelColumnName(indexCol++) + indexRow);
				if(next) cell.setValue(rsthuong.getDouble("Thuong"));
				else cell.setValue(0);
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

				//---------------------------SALE--IN--------------------------------------------------------
				for (i = 0; i < thuongsalein.length; i++)
				{
					if (thuongsalein[i] != null)
					{
						cell = cells.getCell(GetExcelColumnName(indexCol++) + Integer.toString(indexRow));
						cell.setValue(rs.getFloat("ctSIn" + thuongsalein[i]));
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);

						cell = cells.getCell(GetExcelColumnName(indexCol++) + Integer.toString(indexRow));
						cell.setValue(rs.getFloat("tdSIn" + thuongsalein[i]));
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
						phantramMTD = 0;

						if (rs.getFloat("ctSIn" + thuongsalein[i]) > 0)
						{
							phantramMTD = rs.getFloat("tdSIn" + thuongsalein[i])  / rs.getFloat("ctSIn" + thuongsalein[i]);
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
