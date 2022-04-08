package geso.dms.center.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
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


public class DailyPromotionReportDist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public DailyPromotionReportDist() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userTen = (String) session.getAttribute("userTen");
		String querystring = request.getQueryString();
		
		Utility Ult = new Utility();
		String userId = Ult.getUserId(querystring);	
		IStockintransit obj = new Stockintransit();
		
		obj.setuserTen(userTen);
		obj.setuserId(userId);
		obj.init();
		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Center/PromotionReportnppDaily.jsp";
		response.sendRedirect(nextJSP);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OutputStream out = response.getOutputStream(); 
		Stockintransit obj = new Stockintransit();
		String nextJSP = "/AHF/pages/Center/PromotionReportnppDaily.jsp";
		HttpSession session = request.getSession();
		Utility util = new Utility();
		try {	
	    	if(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("unghang")))!= null)
	    		obj.setUnghang("1");
			else
				obj.setUnghang("0");
	    	
	    	
	    	if(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dhchot")))!= null)
	    		obj.setDHchot("1");
			else
				obj.setDHchot("0");
	    				
			System.out.println(obj.getUnghang());
			
			obj.setuserTen((String) session.getAttribute("userTen")!=null?
						(String) session.getAttribute("userTen"):"");			
			
			obj.setuserId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")))!=null? 
					util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")):""));
			
			obj.settungay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays")))!=null? 
					util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays"))):"" );
			
			obj.setdenngay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays")))!=null?
					util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays")):""));
			
			
			obj.setPrograms(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("programs")))!=null? 
					util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("programs")):""));
			
//			obj.setFieldShow(request.getParameterValues("fieldsHien")!=null?
//					request.getParameterValues("fieldsHien"):null);
			
			String sql = "";
			if(obj.getPrograms().length()>0){
				sql +=" AND ctkm.SCHEME = '" + obj.getPrograms() +"'";
			}
			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			if(action.equals("create")){
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BaoCaoXuatKhuyenMaiTheoNgay.xlsm");
				
				boolean isTrue = CreatePivotTable(out, obj,sql);
				
				if(!isTrue){
					PrintWriter writer = new PrintWriter(out);
					writer.write("Xin loi. Khong co bao cao trong thoi gian nay..");
					writer.close();
				}
			}		
			
		} catch (Exception ex) {
			obj.setMsg(ex.getMessage());
			response.sendRedirect(nextJSP);
		}
		obj.init();
		session.setAttribute("obj", obj);
	}
	private boolean CreatePivotTable(OutputStream out, IStockintransit obj, String sql) throws Exception
    {  
		String fstreamstr = getServletContext().getInitParameter("path") + "\\XuatKhuyenMaiNPP.xlsm";
		FileInputStream fstream = new FileInputStream(fstreamstr);
		//FileInputStream fstream = new FileInputStream("C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\Best\\pages\\Templates\\XuatKhuyenMaiTheoNgay.xlsm");

//		FileInputStream fstream = new FileInputStream("D:\\DMS\\Best\\WebContent\\pages\\Templates\\XuatKhuyenMaiTheoNgay.xlsm");
		
		Workbook workbook = new Workbook();
		
		workbook.open(fstream);
		
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		
		CreateStaticHeader(workbook, obj.gettungay(), obj.getdenngay(), obj.getuserTen());
		
		boolean isTrue = CreateStaticData(workbook, obj,sql);
		
		if(!isTrue){
			return false;
		}else{
			workbook.save(out);
		}	
		
		fstream.close();
		return true;
   }
	
	private void CreateStaticHeader(Workbook workbook, 
				String tungay, String denngay, String UserName)throws Exception 
	{
		try{
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
		    
		    cell.setValue("BÁO CÁO XUẤT KHUYẾN MÃI ĐÃ SỬ DỤNG");  getCellStyle(workbook,"A1",Color.RED,true,14);	  	
		    
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("A3");
		    
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ ngày : " + tungay + "" );
		    
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("B3"); 
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến ngày : " + denngay + "" );
		    
		    cells.setRowHeight(4, 18.0f);
		    cell = cells.getCell("A4");getCellStyle(workbook,"A4",Color.NAVY,true,9);
		    cell.setValue("Ngày báo cáo: " + this.getDateTime());
		    
		    cells.setRowHeight(5, 18.0f);
		    cell = cells.getCell("A5");getCellStyle(workbook,"A5",Color.NAVY,true,9);
		    cell.setValue("Được tạo bởi Nhà phân phối:  " + UserName);
		   
		    cell = cells.getCell("AA1");		cell.setValue("KenhBanHang");
			cell = cells.getCell("AB1");		cell.setValue("ChiNhanh");
			cell = cells.getCell("AC1");		cell.setValue("KhuVuc");
			cell = cells.getCell("AD1");		cell.setValue("MaNhaPhanPhoi");
			cell = cells.getCell("AE1");		cell.setValue("NhaPhanPhoi");
			cell = cells.getCell("AF1");		cell.setValue("MaChuongTrinh");
			cell = cells.getCell("AG1");		cell.setValue("DienGiai");
			cell = cells.getCell("AH1");		cell.setValue("MaSanPhamTra");			
			cell = cells.getCell("AI1");		cell.setValue("TenSanPhamTra");
			cell = cells.getCell("AJ1");		cell.setValue("MaSanPhamMua");
			cell = cells.getCell("AK1");		cell.setValue("TenSanPhamMua");
			cell = cells.getCell("AL1");		cell.setValue("NhanHang");
			cell = cells.getCell("AM1");		cell.setValue("ChungLoai");
			cell = cells.getCell("AN1");		cell.setValue("Tinh/Thanh");
			cell = cells.getCell("AO1");		cell.setValue("Quan/Huyen");
			cell = cells.getCell("AP1");		cell.setValue("LoaiChuongTrinh");
			cell = cells.getCell("AQ1");		cell.setValue("DaiDienKinhDoanh");
			cell = cells.getCell("AR1");		cell.setValue("MaKhachHang");
			cell = cells.getCell("AS1");		cell.setValue("TenKhachHang");
			cell = cells.getCell("AT1");		cell.setValue("Ngay");
			cell = cells.getCell("AU1");		cell.setValue("SoLuongTra");
			cell = cells.getCell("AV1");		cell.setValue("SoTien");
		//	cell = cells.getCell("AW1");		cell.setValue("SoLuong(Thung)");
			cell = cells.getCell("AW1");		cell.setValue("DoanhSo");
			cell = cells.getCell("AX1");		cell.setValue("DiaChiKH");
			cell = cells.getCell("AY1");		cell.setValue("SoLuongMua");
		}catch(Exception ex){
			throw new Exception("Khong tao duoc header cho bao cao");
		}
	}

	private boolean CreateStaticData(Workbook workbook, IStockintransit obj, String query)throws Exception {
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		dbutils db = new dbutils();
		Utility utl = new Utility();
		String manpp = "";
		manpp = utl.getIdNhapp(obj.getuserId());
		

		/*String sql = "\n	SELECT KBH.PK_SEQ AS CHANNELID, KBH.TEN AS CHANNEL,VUNG.PK_SEQ AS REGIONID,VUNG.TEN AS REGION,           "     +
				"\n		KHUVUC.PK_SEQ AS AREAID,KHUVUC.TEN AS AREA,            "     +
				"\n		TINH.PK_SEQ AS PROVINCEID,TINH.TEN AS PROVINCE, QUAN.PK_SEQ AS TOWNID,           "     +
				"\n		QUAN.TEN AS TOWN,ISNULL(GSBH.PK_SEQ,0) AS SALESSUPID,            "     +
				"\n		ISNULL(GSBH.TEN, 'Khong xac dinh') AS SALESSUP, NPP.MA AS DISTRIBUTORID,           "     +
				"\n		NPP.TEN AS DISTRIBUTOR, DDKD.PK_SEQ AS SALEREPID, isnull(DDKD.TEN, 'Chua khai bao') AS SALESREP,            "     +
				"\n		CAST(KH.SMARTID as nvarchar(8)) as khId, KH.ten AS CUSTOMER,KH.DIACHI AS diachi ,DH.NGAYNHAP AS DATE,CTKM.SCHEME AS PROGRAM_CODE,           "     +
				"\n		ISNULL(ctkm.diengiai + '__' + ctkm.tungay + '-' + ctkm.denngay, 'Khong xac dinh') AS PROGRAM,            "     +
				"\n		CASE WHEN CTKM.LOAICT = 1 THEN 'Binh Thuong' WHEN CTKM.LOAICT = 2 THEN 'On Top'            "     +
				"\n		WHEN CTKM.LOAICT = 3 THEN 'Tich Luy' else 'Chua Xac Dinh' END AS PROGRAM_TYPE,            "     +
				"\n		NH.PK_SEQ AS BRANDID, NH.TEN AS BRAND,CL.PK_SEQ AS CATEGORYID,CL.TEN AS CATEGORY,  DieuKienKmTraKm.*          "     +
				"\n	FROM          "     +
				"\n	(	         "     +
				""     +
				"\n	Select TraKm.*,DieuKienKm.MaSanPhamMua,DieuKienKm.idSanPhamMua,DieuKienKm.TenSanPhamMua,DieuKienKm.doanhso,DieuKienKm.SoLuongMua,DieuKienKm.SanLuongMua from          "     +
				"\n		(          "     +
				"\n			SELECT TRAKM.DONHANGID,TRAKM.CTKMID,TRAKM.SOLUONG as SoLuongTra,'' AS SoLuongTraQuyDoi,TRAKM.SOLUONG*SP.TRONGLUONG AS SanLuongTra,TRAKM.TONGGIATRI as SoTienTraKm,TRAKM.SPMA as MaSanPhamTra,SP.PK_SEQ as IdSpTra,SP.TEN as TenSanPhamTra          "     +
				"\n			FROM DONHANG_CTKM_TRAKM TRAKM           "     +
				"\n			INNER JOIN CTKHUYENMAI CTKM ON TRAKM.CTKMID = CTKM.PK_SEQ         "     +
				"\n			LEFT JOIN SANPHAM SP ON TRAKM.SPMA = SP.MA           "     +
			
				"\n		)as TraKm Inner JOIN          "     +
				"\n		(          "     +
				"\n			select a.pk_seq as CtKMId, c.donhang_fk as DhId, c.SANPHAM_FK as idSanPhamMua,SP.TEN AS TenSanPhamMua,SP.MA AS MaSanPhamMua,sum( c.soluong * c.giamua - isnull(c.chietkhau, '0') ) as DoanhSo, sum(c.soluong) as SoLuongMua,SUM(c.soluong*sp.TrongLuong)as SanLuongMua          "     +
				"\n			from ctkhuyenmai a inner join donhang_ctkm_trakm b on a.pk_seq = b.ctkmId            "     +
				"\n				inner join donhang_sanpham c on b.donhangId = c.donhang_fk           "     +
				"\n				inner join ctkm_dkkm d on a.pk_seq = d.ctkhuyenmai_fk           "     +
				"\n				inner join dieukienkm_sanpham e on d.dkkhuyenmai_fk = e.dieukienkhuyenmai_fk and c.sanpham_fk = e.sanpham_fk           "     +
				"\n				LEFT JOIN SANPHAM SP ON c.SANPHAM_FK = SP.PK_SEQ  "     +
				"\n				         "     +
				"\n			group by a.pk_seq, c.donhang_fk ,c.SANPHAM_FK ,SP.TEN ,SP.MA          "     +
				"\n		)DieuKienKm on TraKm.DONHANGID=DieuKienKm.DhId and TraKm.CTKMID=DieuKienKm.CtKMId          "     +
				"\n	) AS DieuKienKmTraKm          "     +
				"\n	inner join CTKHUYENMAI CTKM ON CTKM.PK_SEQ=DieuKienKmTraKm.CTKMID          "     +
				"\n	inner join DONHANG dh on DieuKienKmTraKm.DONHANGID=dh.PK_SEQ        "     +
				"\n	LEFT JOIN SANPHAM SP ON DieuKienKmTraKm.IdSpTra = SP.PK_SEQ          "     +
				"\n	LEFT JOIN NHANHANG NH ON NH.PK_SEQ = SP.NHANHANG_FK           "     +
				"\n	LEFT JOIN CHUNGLOAI CL ON CL.PK_SEQ = SP.CHUNGLOAI_FK           "     +
			
				"\n	INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = DH.NPP_FK    and npp.pk_seq = "     +manpp+
				"\n	left JOIN TINHTHANH TINH ON TINH.PK_SEQ = NPP.TinhThanh_FK           "     +
				"\n	left JOIN QUANHUYEN QUAN ON QUAN.PK_SEQ = TINH.PK_SEQ            "     +
				"\n	INNER JOIN KHACHHANG KH ON KH.PK_SEQ = DH.KHACHHANG_FK           "     +
				"\n	INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ = KH.KBH_FK           "     +
				"\n	INNER JOIN DAIDIENKINHDOANH DDKD ON DDKD.PK_SEQ = DH.DDKD_FK           "     +
				"\n	LEFT JOIN GIAMSATBANHANG GSBH ON GSBH.PK_SEQ = DH.GSBH_FK           "     +
				"\n	INNER JOIN KHUVUC KHUVUC ON KHUVUC.PK_SEQ = NPP.KHUVUC_FK           "     +
				"\n	INNER JOIN VUNG VUNG ON VUNG.PK_SEQ = KHUVUC.VUNG_FK             "     +
				"\n WHERE ISNULL(DieuKienKmTraKm.SoLuongTra, '0') >= 0  "     +
				"\n AND dh.trangthai <> 2 and DH.PK_SEQ NOT IN  "     +
				"\n ( "     +
				"\n	SELECT DONHANG_FK FROM DONHANGTRAVE  "     +
				"\n	WHERE DONHANG_FK IS NOT NULL AND TRANGTHAI = '3'  "     +
				"\n ) and dh.ngaynhap >= '"+obj.gettungay()+"' and dh.ngaynhap <= '"+obj.getdenngay()+"' ";*/
		
		String sql = 				
				"\n	SELECT KBH.PK_SEQ AS CHANNELID, KBH.TEN AS CHANNEL,VUNG.PK_SEQ AS REGIONID,VUNG.TEN AS REGION,           "     +
				"\n		KHUVUC.PK_SEQ AS AREAID,KHUVUC.TEN AS AREA,            "     +
				"\n		isnull(TINH.PK_SEQ,0) AS PROVINCEID,isnull(TINH.TEN,'NA') AS PROVINCE, isnull(QUAN.PK_SEQ ,0)AS TOWNID,           "     +
				"\n		isnull(QUAN.TEN,'NA') AS TOWN,ISNULL(GSBH.PK_SEQ,0) AS SALESSUPID,            "     +
				"\n		ISNULL(GSBH.TEN, '') AS SALESSUP, NPP.MA AS DISTRIBUTORID,           "     +
				"\n		NPP.TEN AS DISTRIBUTOR, DDKD.PK_SEQ AS SALEREPID, isnull(DDKD.TEN, 'Chua khai bao') AS SALESREP,            "     +
				"\n		CAST(KH.SMARTID as nvarchar(8)) as khId, KH.ten AS CUSTOMER,KH.DIACHI AS diachi ,DH.NGAYNHAP AS DATE,CTKM.SCHEME AS PROGRAM_CODE,           "     +
				"\n		ISNULL(ctkm.diengiai + '__' + ctkm.tungay + '-' + ctkm.denngay, '') AS PROGRAM,            "     +
				"\n		CASE WHEN CTKM.LOAICT = 1 THEN 'Binh Thuong' WHEN CTKM.LOAICT = 2 THEN 'On Top'            "     +
				"\n		WHEN CTKM.LOAICT = 3 THEN 'Tich Luy' else 'Chua Xac Dinh' END AS PROGRAM_TYPE,            "     +
				"\n		NH.PK_SEQ AS BRANDID, NH.TEN AS BRAND,CL.PK_SEQ AS CATEGORYID,CL.TEN AS CATEGORY,  DieuKienKmTraKm.* , CTKM.NPPTUCHAY,  " +
				"\n     case when ctkm.kho_fk = 100001 then N'Trả trước' else N'Trả sau' end as Hinhthuc, '' loai  "     +
				"\n	FROM          "     +
				"\n	(	         "     +
				"\n	select data.loai,data.DONHANGID,data.CTKMID,data.SoLuongTra,data.SoLuongMua,data.SanLuongTra, '' sanluongmua,sum(data.SoTienTraKm) as SoTienTraKm, data.doanhso,data.MaSanPhamTra, data.MaSanPhamMua,data.IdSpTra, data.IdSanphammua,data.TenSanPhamTra, data.TenSanPhammua from ( "     +
				"\n			Select 'TRA' as loai,TraKm.*          "     +
				"\n			from                   "     + 
				"\n			(                 "     +
						"\n				SELECT TRAKM.DONHANGID,TRAKM.CTKMID,TRAKM.SOLUONG as SoLuongTra, '0' as SoLuongMua,'0' AS SoLuongTraQuyDoi,TRAKM.SOLUONG*SP.TRONGLUONG AS SanLuongTra,TRAKM.TONGGIATRI as SoTienTraKm, '0' as doanhso,TRAKM.SPMA as MaSanPhamTra,'0' MaSanPhamMua,'0' IdSanphammua,SP.PK_SEQ as IdSpTra,'' TenSanPhammua,SP.TEN as TenSanPhamTra     "     +               
					"\n				FROM DONHANG_CTKM_TRAKM TRAKM     "     +            
					"\n				inner join DONHANG DH ON DH.PK_SEQ = TRAKM.DONHANGID       "     +    
					"\n				INNER JOIN CTKHUYENMAI CTKM ON TRAKM.CTKMID = CTKM.PK_SEQ     "     +       
					"\n				LEFT JOIN SANPHAM SP ON TRAKM.SPMA = SP.MA      "     +       
					"\n				WHERE 1=1         "     ;
					if(obj.gettungay().length() > 0 && obj.getdenngay().length() > 0){
						sql += "\n and dh.ngaynhap >= '"+obj.gettungay()+"' and dh.ngaynhap <= '"+obj.getdenngay()+"' ";
				}
					sql +=	"\n			)as TraKm           "     +
			/*	"\n		  union all           "     +
			  "\n			select N'DKMUA' as loai,a.PK_SEQ,b.CTKMID,'' as soluongtra,case when dk.SOLUONG>0 then dk.SOLUONG*b.SOXUAT else   d.SOLUONG end soluongmua ,'' soluongquydoi,'' sanluongtra,'' sotientrakm,d.SOLUONG *d.giamua as doanhso,'0' MaSanPhamTra,sp.MA as MaSanPhamMua,sp.PK_SEQ as IdSanphammua, '0' IdSpTra,sp.ten as TenSanPhammua,'' TenSanPhamTra "     +
				"\n			from DONHANG a     "     +      
				"\n			inner join DONHANG_CTKM_TRAKM b on a.PK_SEQ=b.DONHANGID          "     +
				"\n			inner join CTKM_DKKM c on c.CTKHUYENMAI_FK=b.CTKMID          "     +
				"\n			inner join DONHANG_SANPHAM d on d.DONHANG_FK=a.PK_SEQ        "     +  
				"\n			inner join sanpham sp on sp.PK_SEQ=d.SANPHAM_FK          "     +
				"\n			inner join  BANGGIABANLECHUAN_NPP gb on a.NPP_FK = gb.NPP_FK     "     +     
				"\n			inner join BANGGIABLC_SANPHAM cbh on cbh.BGBLC_FK = gb.BANGGIABANLECHUAN_FK and cbh.SANPHAM_FK = d.SANPHAM_FK      "     +     
				"\n			inner join DIEUKIENKM_SANPHAM dk on dk.DIEUKIENKHUYENMAI_FK=c.DKKHUYENMAI_FK and dk.SANPHAM_FK=d.SANPHAM_FK        "     +  
				"\n			where  b.SPMA is null        "     ; 
				if(obj.gettungay().length() > 0 && obj.getdenngay().length() > 0){
					query += "\n and a.ngaynhap >= '"+obj.gettungay()+"' and a.ngaynhap <= '"+obj.getdenngay()+"' ";
				}
			query +=
			*/	"\n		 union all "     +
			 "\n		 select N'DKMUA' as loai,dh.PK_SEQ,a.pk_seq as CtKMId,'0' as soluongtra,b.soluongmua ,'0' soluongquydoi,'0' sanluongtra,'0' sotientrakm,( b.soluongmua * cbh.GIABANLECHUAN ) as doanhso,'0' MaSanPhamTra,sp.MA as MaSanPhamMua,sp.PK_SEQ as IdSpmua, '0' IdSpTra,sp.ten as TenSanPhammua,'' TenSanPhamTra "     +
			 "\n			from ctkhuyenmai a inner join donhang_ctkm_dkkm b on a.pk_seq = b.ctkm_fk         " +
				"\n			inner join donhang_sanpham c on b.donhang_fk = c.donhang_fk          "     +  
				"\n			inner JOIN SANPHAM SP ON b.SANPHAM_FK = SP.ma   "     +
				"\n			inner join DONHANG dh on dh.PK_SEQ = b.DONHANG_fk  "     +
				"\n			inner join  BANGGIABANLECHUAN_NPP gb on dh.NPP_FK = gb.NPP_FK  "     +
				"\n			inner join BANGGIABLC_SANPHAM cbh on cbh.BGBLC_FK = gb.BANGGIABANLECHUAN_FK and cbh.SANPHAM_FK = c.SANPHAM_FK and cbh.sanpham_fk = sp.pk_seq "     +
				"\n			inner join ctkm_dkkm d on a.pk_seq = d.ctkhuyenmai_fk            "     +
				"\n			where 1=1 and exists (select 1 from donhang_ctkm_trakm where ctkmid = b.ctkm_fk and c.donhang_fk = donhangid ) " ;
				if(obj.gettungay().length() > 0 && obj.getdenngay().length() > 0){
					sql += "\n and dh.ngaynhap >= '"+obj.gettungay()+"' and dh.ngaynhap <= '"+obj.getdenngay()+"' ";
				}
				sql +=
				"\n	)as data "     +
				"\n	group by data.loai,data.DONHANGID,data.CTKMID,data.SoLuongTra,data.SoLuongMua,data.SanLuongTra, data.doanhso,data.MaSanPhamTra, data.MaSanPhamMua,data.IdSpTra, data.IdSanphammua,data.TenSanPhamTra, data.TenSanPhammua "     +
		"\n	 ) AS DieuKienKmTraKm          "     + 
		"\n	inner join CTKHUYENMAI CTKM ON CTKM.PK_SEQ=DieuKienKmTraKm.CTKMID          "     +
		"\n	inner join DONHANG dh on DieuKienKmTraKm.DONHANGID=dh.PK_SEQ          "     +
		"\n	LEFT JOIN SANPHAM SP ON DieuKienKmTraKm.IdSpTra = SP.PK_SEQ          "     +
		"\n	LEFT JOIN NHANHANG NH ON NH.PK_SEQ = SP.NHANHANG_FK           "     +
		"\n	LEFT JOIN CHUNGLOAI CL ON CL.PK_SEQ = SP.CHUNGLOAI_FK           "     +
	
		"\n	INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = DH.NPP_FK    and npp.pk_seq = "     +manpp+
		"\n	left JOIN TINHTHANH TINH ON TINH.PK_SEQ = NPP.TinhThanh_FK           "     +
		"\n	left JOIN QUANHUYEN QUAN ON QUAN.PK_SEQ = TINH.PK_SEQ            "     +
		"\n	INNER JOIN KHACHHANG KH ON KH.PK_SEQ = DH.KHACHHANG_FK           "     +
		"\n	INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ = npp.KBH_FK           "     +
		"\n	INNER JOIN DAIDIENKINHDOANH DDKD ON DDKD.PK_SEQ = DH.DDKD_FK           "     +
		"\n	LEFT JOIN GIAMSATBANHANG GSBH ON GSBH.PK_SEQ = DH.GSBH_FK           "     +
		"\n	INNER JOIN KHUVUC KHUVUC ON KHUVUC.PK_SEQ = NPP.KHUVUC_FK           "     +
		"\n	INNER JOIN VUNG VUNG ON VUNG.PK_SEQ = KHUVUC.VUNG_FK             "     +
		"\n WHERE ISNULL(DieuKienKmTraKm.SoLuongTra, '0') >= 0  "     +
		"\n AND DH.PK_SEQ NOT IN  "     +
		"\n ( "     +
		"\n	SELECT DONHANG_FK FROM DONHANGTRAVE  "     +
		"\n	WHERE DONHANG_FK IS NOT NULL AND TRANGTHAI = '3'  "     +
		"\n ) ";
		if(obj.getUnghang().equals("0")){
			sql = sql + "\n and ctkm.kho_fk='100000' ";
		}
		if(obj.getDHchot().equals("1")){
			sql = sql + "\n and dh.trangthai = 1 ";
		}
		query = query + " order by DieuKienKmTraKm.DONHANGID,DieuKienKmTraKm.CtKMId, DieuKienKmTraKm.MaSanPhamTra  ";		
		System.out.println("Xuat Khuyen Mai NPP: " + sql);
		ResultSet rs = db.get(sql);
		int i = 2;
		String masptra = "";
		String Prev="";
		if (rs != null) {
			try {
				Cell cell = null;
				while (rs.next())// lap den cuoi bang du lieu
				{
					String Cur=rs.getString("DONHANGID")+"_"+rs.getString("CtKMId")+rs.getString("MaSanPhamTra");
					String Chanel = rs.getString("Channel");
					String Region = rs.getString("Region");
					String area = rs.getString("Area");

					String Distributor = rs.getString("Distributor");
					String PromotionProgram = rs.getString("PROGRAM");

					String Brand = "" ;
					
					if(rs.getString("Brand") == null)
						Brand = "Khong xac dinh";
					else
						Brand = rs.getString("Brand");
					
					String Category = "";

					if(rs.getString("Category") == null)
						Category = "Khong xac dinh";
					else
						Category = rs.getString("Category");
					
					String Province = rs.getString("Province");
					String Town = rs.getString("Town");
					String DisCode = rs.getString("DISTRIBUTORID");
					String ProgramCode = rs.getString("PROGRAM_CODE");
					String ProgramType = rs.getString("PROGRAM_TYPE");
					
					String MaSanPhamTra = "";
					
					if(rs.getString("MaSanPhamTra") == null)
						MaSanPhamTra = "Khong xac dinh";
					else
						MaSanPhamTra = rs.getString("MaSanPhamTra");

					String TenSanPhamTra = "";
					if(rs.getString("TenSanPhamTra") == null)
						TenSanPhamTra = "Khong xac dinh";
					else
						TenSanPhamTra = rs.getString("TenSanPhamTra");
					
					
					String MaSanPhamMua = "";
					if(rs.getString("MaSanPhamMua") == null)
						MaSanPhamMua = "Khong xac dinh";
					else
						MaSanPhamMua = rs.getString("MaSanPhamMua");

					String TenSanPhamMua = "";
					if(rs.getString("TenSanPhamMua") == null)
						TenSanPhamMua = "Khong xac dinh";
					else
						TenSanPhamMua = rs.getString("TenSanPhamMua");
					
					
					String SaleRep = rs.getString("SALESREP");
					//String CustomerKey = rs.getString("khId");
					String Customer = rs.getString("Customer");
					int CustomerKey = Integer.parseInt(rs.getString("khId"));
					String Offdate = rs.getString("DATE");

					long doanhso = Math.round(rs.getDouble("DOANHSO"));
					cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(Chanel);
					cell = cells.getCell("AB" + Integer.toString(i)); cell.setValue(Region);
					cell = cells.getCell("AC" + Integer.toString(i)); cell.setValue(area);
					cell = cells.getCell("AD" + Integer.toString(i)); cell.setValue(DisCode);
					cell = cells.getCell("AE" + Integer.toString(i)); cell.setValue(Distributor); 
					cell = cells.getCell("AF" + Integer.toString(i)); cell.setValue(ProgramCode);
					cell = cells.getCell("AG" + Integer.toString(i)); cell.setValue(PromotionProgram);
					cell = cells.getCell("AH" + Integer.toString(i)); cell.setValue(MaSanPhamTra);
					cell = cells.getCell("AI" + Integer.toString(i)); cell.setValue(TenSanPhamTra);
					cell = cells.getCell("AJ" + Integer.toString(i)); cell.setValue(MaSanPhamMua);
					cell = cells.getCell("AK" + Integer.toString(i)); cell.setValue(TenSanPhamMua);
					cell = cells.getCell("AL" + Integer.toString(i)); cell.setValue(Brand);
					cell = cells.getCell("AM" + Integer.toString(i)); cell.setValue(Category);
					cell = cells.getCell("AN" + Integer.toString(i)); cell.setValue(Province);
					cell = cells.getCell("AO" + Integer.toString(i)); cell.setValue(Town);					
					cell = cells.getCell("AP" + Integer.toString(i)); cell.setValue(ProgramType);
					cell = cells.getCell("AQ" + Integer.toString(i)); cell.setValue(SaleRep);
					cell = cells.getCell("AR" + Integer.toString(i)); cell.setValue(CustomerKey);
					cell = cells.getCell("AS" + Integer.toString(i)); cell.setValue(Customer);
					cell = cells.getCell("AT" + Integer.toString(i)); cell.setValue(Offdate);
					
					if(!Cur.equals(Prev)){
						Prev=Cur;
						masptra=rs.getString("MaSanPhamTra");
						System.out.println("Diff[MaSp]"+rs.getString("MaSanPhamTra")+"[Ma]"+masptra);
						cell = cells.getCell("AU" + Integer.toString(i));		cell.setValue(rs.getDouble("SoLuongTra"));
						cell = cells.getCell("BA" + Integer.toString(i)); 		cell.setValue(rs.getDouble("sanluongTra"));
					}else{
						System.out.println("Same [MaSp]"+rs.getString("MaSanPhamTra")+"[Ma]"+masptra);
						cell = cells.getCell("AU" + Integer.toString(i));		cell.setValue(0);
						cell = cells.getCell("BA" + Integer.toString(i)); 		cell.setValue(0);
					}
					
					/*cell = cells.getCell("AU" + Integer.toString(i)); cell.setValue(rs.getDouble("SoLuongTra"));*/
					cell = cells.getCell("AV" + Integer.toString(i)); cell.setValue(rs.getDouble("SoTienTraKm"));
					
					cell = cells.getCell("AW" + Integer.toString(i)); cell.setValue(doanhso);
					cell = cells.getCell("AX" + Integer.toString(i)); cell.setValue(rs.getString("diachi"));
					cell = cells.getCell("AY" + Integer.toString(i)); cell.setValue(rs.getDouble("SoLuongMua"));																															
					
					
					i++;

				}
   				

				if (rs != null)
					rs.close();
				if (db != null)
					db.shutDown();
				if(i==2){
					throw new Exception("Xin loi. Khong co bao cao trong thoi gian nay..");
				}
				

			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		} else {
			return false;
		}
		this.setAn(workbook, 49);
		return true;
	}

	private void getCellStyle(Workbook workbook, String a, Color mau, Boolean dam, int size) {
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();
		Style style;
		Cell cell = cells.getCell(a);
		style = cell.getStyle();
		Font font1 = new Font();
		font1.setColor(mau);
		font1.setBold(dam);
		font1.setSize(size);
		style.setFont(font1);
		cell.setStyle(style);
	}

	private void setAn(Workbook workbook, int i) {
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();
		for (int j = 26; j <= i; j++) {
			cells.hideColumn(j);
		}

	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
