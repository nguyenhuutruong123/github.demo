package geso.dms.center.servlets.reports;

import geso.dms.center.beans.lotrinh.ILoTrinh;
import geso.dms.center.beans.lotrinh.imp.LoTrinh;
import geso.dms.center.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

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

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@WebServlet("/BCViengThamOutlet")
public class BCViengThamOutlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	Utility util = new Utility();
	NumberFormat formatter = new DecimalFormat("#,###,###.###");
	public BCViengThamOutlet()
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
		session.setAttribute("userId", userId);
		session.setAttribute("tungay", "");
		session.setAttribute("denngay", "");
		session.setAttribute("loi", "");
		session.setAttribute("userTen", userTen);

		ILoTrinh obj = new LoTrinh();

		obj.setStatus("1");
		obj.setUserId(userId);
		obj.init();

		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Center/BCViengThamOutlet.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		// PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		
		ILoTrinh obj = new LoTrinh();
		
		obj.setUserId(userId);
		
		String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));

		String kenhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")));
		if (kenhId == null)
			kenhId = "";
		obj.setKenhId(kenhId);

		if (nppId == null)
			nppId = "";
		obj.setnppId(nppId);

		String ddkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdId")));
		if (ddkdId == null)
			ddkdId = "";
		obj.setddkdId(ddkdId);

		String tuyenId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tuyenId")));
		if (tuyenId == null)
			tuyenId = "";
		obj.settuyenId(tuyenId);
		
		String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
    	if(tungay == null) tungay = ""; else tungay = tungay.trim();
    	obj.setTungay(tungay);
    	
    	String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
    	if(denngay == null || denngay.trim().length() <= 0) 
    		denngay = getDateTime();
    	else 
    		denngay = denngay.trim();
    	obj.setDenngay(denngay);

    	String vungId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")));
    	
		if (vungId == null)
			vungId = "";
		obj.setvungId(vungId);
		
    	String khuvucId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")));
    	
		if (khuvucId == null)
			khuvucId = "";
		obj.setkhuvucId(khuvucId);
		
		session.setAttribute("loi", "");
		
		obj.init();
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));

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
				String nextJSP = "/AHF/pages/Center/BCViengThamOutlet.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				ThucHienViengThamOutlet(response, obj);
			}
		}
		else if(action.equals("chitiet"))
		{
			System.out.println("XuatDetail__");
			if(tungay.trim().length() <= 0 || denngay.trim().length() <= 0 )
			{
				if(tungay.trim().length() <= 0)
					session.setAttribute("loi", "Bạn phải chọn ngày bắt đầu xem báo cáo");
				else
					session.setAttribute("loi", "Bạn phải chọn ngày kết thúc xem báo cáo");
				session.setAttribute("obj", obj);
				String nextJSP = "/AHF/pages/Center/BCViengThamOutlet.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				System.out.println("VO Xuat FILE Detail__");
				XuatFileExcelChiTiet(response, obj);
			}
		}
		else if(action.equals("chitietso"))
		{
			if(tungay.trim().length() <= 0 || denngay.trim().length() <= 0 )
			{
				if(tungay.trim().length() <= 0)
					session.setAttribute("loi", "Bạn phải chọn ngày bắt đầu xem báo cáo");
				else
					session.setAttribute("loi", "Bạn phải chọn ngày kết thúc xem báo cáo");
				session.setAttribute("obj", obj);
				String nextJSP = "/AHF/pages/Center/BCViengThamOutlet.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				XuatFileExcelChiTietSO(response, obj);
			}
		}
		else
		{
			String status = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("status")));
			obj.setStatus(status);
			obj.createNPP();
			session.setAttribute("obj", obj);
			String nextJSP = "/AHF/pages/Center/BCViengThamOutlet.jsp";
			response.sendRedirect(nextJSP);
		}
	}

	private void ThucHienViengThamOutlet(HttpServletResponse response, ILoTrinh lotrinh) throws IOException
	{
		OutputStream out1 = null;
		try
		{
			String NppId = lotrinh.getnppId();
			String DdkdId = lotrinh.getddkdId();
			String tuyenId = lotrinh.gettuyenId();
			String tungay = lotrinh.getTungay();
			String denngay = lotrinh.getDenngay();
			
			String ngayId = lotrinh.gettuyenId();
			if(ngayId == null) ngayId = ""; else ngayId = ngayId.trim();
			int ngayIdi = 0;
			try {
				ngayIdi = Integer.parseInt(ngayId);
				if(ngayIdi < 1 || ngayIdi > 7) {
					ngayIdi = 0;
				}
			} catch(Exception e) { }
			
			Utility util = new Utility();
			
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=BCThucHienViengThamOutlet.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			dbutils db = new dbutils();
			String sql =
				" select 	ddkd.pk_seq as ddkdId, ddkd_sokh.NGAY ,v.TEN as vungTen,kv.ten as kvTen, \n" +
				" 	  npp.MA as nppMa,npp.TEN as nppTen,ddkd.SmartId as ddkdMa, \n" +
				" 	  ddkd.TEN as ddkdTen,ddkd_sokh.NGAYID,ddkd_sokh.SOKH as soPhaiVt,viengtham.visited_outlet as soDaVt ,vtDung.visited_outlet as soVtDung,vtSai.visited_outlet as soVtSai,DDKD_SOKH.SOKHMOI ,doanhso.sodh,doanhso.doanhso,doanhso.soSKU \n, " +
			 	"     (select top(1) thoidiem from DDKD_NPP where ddkd_fk = ddkd.PK_SEQ and CONVERT(varchar(10), thoidiem, 120) = DDKD_SOKH.NGAY order by thoidiem asc) as thoigianvtnppdautien, " +
			 	"     (select top(1) thoidiem from ddkd_khachhang where ddkd_fk = ddkd.PK_SEQ and CONVERT(varchar(10), thoidiem, 120) = DDKD_SOKH.NGAY order by thoidiem asc) as thoigianvtkhdautien, " +
			 	"     (select top(1) thoidiem from ddkd_khachhang where ddkd_fk = ddkd.PK_SEQ and CONVERT(varchar(10), thoidiem, 120) = DDKD_SOKH.NGAY order by thoidiem desc) as thoigianketthuc, " +
			 	"	  (select top(1) THOIDIEMDI from ddkd_khachhang where ddkd_fk = ddkd.PK_SEQ and CONVERT(varchar(10), thoidiem, 120) = DDKD_SOKH.NGAY and THOIDIEMDI is not null order by thoidiem desc) as thoigianroikhoicuoicung " +
				"  from ddkd_sokh left join  \n" +
				"  (   \n" +
				"  	select count(distinct dh.pk_seq) as sodh,sum(dhsp.soluong*dhsp.giamua/1.1*0.965) as doanhso,count(distinct dhsp.sanpham_fk ) as soSKU,dh.ngaynhap,dh.ddkd_fk  \n" +
				"  	from donhang  dh inner join donhang_sanpham dhsp  on dhsp.donhang_fk=dh.pk_seq   \n" +
				"  	where dh.trangthai!=2 " ;
					if(tungay.length()>0)
				sql+=" and dh.ngaynhap>='"+tungay+"' ";
				if(denngay.length()>0)
					sql+=" and dh.ngaynhap<='"+denngay+"' ";
				if(NppId.length()>0)
					sql+=" and dh.npp_fk<='"+NppId+"' ";
				
				if(DdkdId.length()>0)
					sql+=" and dh.ddkd_fk='"+DdkdId+"' ";
				sql+=
				"  	group by dh.ddkd_fk,dh.ngaynhap   " +
				"  ) as doanhso on doanhso.ddkd_fk=ddkd_sokh.ddkd_fk and ddkd_sokh.ngay=doanhso.ngaynhap   \n" +
				"  	left join    \n" +
				"  	(   \n" +
				"  		select count(distinct khachhang_fk) as visited_outlet,ddkd_fk, \n" +
				"  		CONVERT(varchar(10), thoidiem, 120)as ngay   \n" +
				"  		from ddkd_khachhang   where 1=1  " ;
				if(tungay.length()>0)
					sql+=" and CONVERT(varchar(10), thoidiem, 120)>='"+tungay+"' ";
					if(denngay.length()>0)
						sql+=" and  CONVERT(varchar(10), thoidiem, 120) <='"+denngay+"' ";
				
					if(DdkdId.length()>0)
						sql+=" and ddkd_fk='"+DdkdId+"' ";
		sql+=
				"  		group by ddkd_fk,CONVERT(varchar(10), thoidiem, 120) \n" +
				"  	)as viengtham on viengtham.ddkd_fk=ddkd_sokh.ddkd_fk and viengtham.ngay=ddkd_sokh.ngay  \n" +
				"  	left join   \n" +
				"  	( \n" +
				"  		select count(distinct kh_tbh.khachhang_fk) as visited_outlet,a.PK_SEQ as ddkdId, \n" +
				"  			CONVERT(varchar(10), thoidiem, 120)  as ngay   \n" +
				" 		from daidienkinhdoanh a inner join tuyenbanhang b on a.pk_seq = b.ddkd_fk   \n" +
				" 			left join ddkd_khachhang c on a.pk_seq = c.ddkd_fk and b.ngayid =datepart(dw, c.thoidiem)  \n" +
				" 			inner join khachhang_tuyenbh kh_tbh on b.pk_seq = kh_tbh.tbh_fk and kh_tbh.khachhang_fk = c.khachhang_fk \n" +
				"  where 1=1   " ;
				if(tungay.length()>0)
					sql+=" and CONVERT(varchar(10), thoidiem, 120)>='"+tungay+"' ";
				if(denngay.length()>0)
					sql+="  and CONVERT(varchar(10), thoidiem, 120) <='"+denngay+"' ";
			
				if(DdkdId.length()>0)
					sql+=" and c.ddkd_fk='"+DdkdId+"' ";
				sql+=	
				" 		group by a.PK_SEQ,CONVERT(varchar(10), thoidiem, 120) \n" +
				"  	)as vtDung on vtDung.ddkdId=viengtham.ddkd_fk and vtDung.ngay=viengtham.ngay \n" +
				"  	left join  \n" +
				"  	( \n" +
				"  			select count(distinct kh_tbh.khachhang_fk) as visited_outlet,a.PK_SEQ as ddkdId, \n" +
				"  			CONVERT(varchar(10), thoidiem, 120)  as ngay   \n" +
				" 		from daidienkinhdoanh a inner join tuyenbanhang b on a.pk_seq = b.ddkd_fk   \n" +
				" 			left join ddkd_khachhang c on a.pk_seq = c.ddkd_fk and b.ngayid !=datepart(dw, c.thoidiem)  \n" +
				" 			inner join khachhang_tuyenbh kh_tbh on b.pk_seq = kh_tbh.tbh_fk and kh_tbh.khachhang_fk = c.khachhang_fk    \n" +
				" 		where not exists  \n" +
				" 			(  \n" +
				" 				 select *  \n" +
				" 				from daidienkinhdoanh dd inner join tuyenbanhang tbh on dd.pk_seq = tbh.ddkd_fk   \n" +
				" 					left join ddkd_khachhang ddkh on ddkh.ddkd_fk =dd.PK_SEQ   and tbh.ngayid =datepart(dw, ddkh.thoidiem)  \n" +
				" 					inner join khachhang_tuyenbh kh_tbh on kh_tbh.KHACHHANG_FK=ddkh.khachhang_fk and kh_tbh.TBH_FK=tbh.PK_SEQ  \n" +
				" 				where c.thoidiem=ddkh.thoidiem and ddkh.khachhang_fk=c.khachhang_fk \n" +
				" 			) " ;
				
			if(tungay.length()>0)
				sql+=" and CONVERT(varchar(10), thoidiem, 120)>='"+tungay+"' ";
				if(denngay.length()>0)
					sql+=" and  CONVERT(varchar(10), thoidiem, 120) <='"+denngay+"' ";
			
				if(DdkdId.length()>0)
					sql+=" and a.pk_seq = '"+DdkdId+"' ";
			sql+=	
				" 		group by a.PK_SEQ,CONVERT(varchar(10), thoidiem, 120) \n" +
				"  	)as vtSai on vtSai.ddkdId=viengtham.ddkd_fk and vtSai.ngay=viengtham.ngay \n" +
				"  	inner join daidienkinhdoanh ddkd on ddkd.pk_seq=ddkd_sokh.ddkd_fk   \n" +
				"  	inner join nhaphanphoi npp on npp.pk_seq=ddkd.npp_fk   \n" +
				"  	inner join khuvuc kv on kv.pk_seq=npp.khuvuc_fk   \n" +
				"  	inner join vung v on v.pk_seq=kv.vung_fk   \n" +
				"  where 1=1  " ;
							 
			
			if(tungay.length()>0)
				sql+= " and ddkd_sokh.Ngay>='"+tungay+"'";
					
			if(tungay.length()>0)
				sql+= " and ddkd_sokh.Ngay<='"+denngay+"'";
			
			if(NppId.trim().length() > 0)
			{
				sql+=" and npp.pk_seq=" + NppId +"  ";
			}
			if (DdkdId.trim().length() > 0)
			{
				sql += " and ddkd.pk_seq=" + DdkdId;
			}
			if(lotrinh.getvungId().trim().length() > 0)
			{
				sql += " and v.pk_seq = '" + lotrinh.getvungId() + "' ";
			}
			if(lotrinh.getkhuvucId().trim().length() > 0)
			{
				sql += " and kv.pk_seq = '" + lotrinh.getkhuvucId() + "' ";
			}
			if(tuyenId.length()>0)
			{
				sql += " and ddkd_sokh.NgayID = '" + tuyenId + "' ";
			}
			
			sql+=" order by ddkd_sokh.Ngay,V.PK_SEQ,KV.PK_SEQ,NPP.PK_SEQ,DDKD.PK_SEQ   ";
			System.out.println("[sql]"+sql);
			
			ResultSet rs = db.get(sql);
			int k = 0;
			int j = 5;
		/*	String curDay="";
			String prevDay="";*/
			WritableSheet sheet=null;
			
			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 14);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);
			
			sheet = w.createSheet("BCThucHienViengThamOutlet", k);
			sheet.addCell(new Label(0, 1, "BÁO CÁO THỰC HIỆN VIẾNG THĂM OUTLET: ",new WritableCellFormat(cellTitle)));

			sheet.addCell(new Label(0, 2, "TỪ NGÀY: "));
			sheet.addCell(new Label(1, 2, "" + tungay));
			
			
			sheet.addCell(new Label(0, 3, "ĐẾN NGÀY: "));
			sheet.addCell(new Label(1, 3, "" + denngay));
			
			sheet.addCell(new Label(2, 4, "Đơn vị tiền tệ:VND"));				

			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
			cellFont.setColour(Colour.BLACK);

			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

			cellFormat.setBackground(jxl.format.Colour.LIME);
			cellFormat.setWrap(true);
			cellFormat.setAlignment(Alignment.CENTRE);
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			
			WritableCellFormat cellFormatSpecical = new WritableCellFormat(cellFont);

			cellFormatSpecical.setBackground(jxl.format.Colour.LIGHT_ORANGE);
			cellFormatSpecical.setWrap(true);
			cellFormatSpecical.setAlignment(Alignment.CENTRE);
			cellFormatSpecical.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormatSpecical.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormatSpecical.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormatSpecical.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormatSpecical.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			

			// cellFormat.setFont()
			sheet.addCell(new Label(0, 4, "NGÀY", cellFormat));
			sheet.addCell(new Label(1, 4, "VÙNG", cellFormat));
			sheet.addCell(new Label(2, 4, "KHU VỰC", cellFormat));
			sheet.addCell(new Label(3, 4, "TÊN NHÀ PHÂN PHỐI", cellFormat));
			sheet.addCell(new Label(4, 4, "MÃ NHÂN VIÊN", cellFormat));
			sheet.addCell(new Label(5, 4, "NHÂN VIÊN BÁN HÀNG", cellFormatSpecical));
			sheet.addCell(new Label(6, 4, "TUYẾN BÁN HÀNG", cellFormat));
			sheet.addCell(new Label(7, 4, "SỐ LƯỢNG OUTLET YÊU CẦU VIẾNG THĂM", cellFormat));
			sheet.addCell(new Label(8, 4, "SỐ OUTLET ĐÃ VIẾNG THĂM", cellFormat));
			sheet.addCell(new Label(9, 4, "SỐ OUTLET VIẾNG THĂM TRONG LỘ TRÌNH", cellFormat));
			sheet.addCell(new Label(10, 4, "SỐ OUTLET VIẾNG THĂM NGOÀI LỘ TRÌNH", cellFormat));
			sheet.addCell(new Label(11, 4, "SỐ OUTLET MỚI(KHÔNG CÓ TRONG MCP) ", cellFormat));
			sheet.addCell(new Label(12, 4, "SỐ ĐƠN HÀNG ", cellFormat));
			sheet.addCell(new Label(13, 4, "SỐ SKU BÁN ĐƯỢC ", cellFormat));
			sheet.addCell(new Label(14, 4, "DOANH SỐ (TRƯỚC THUẾ & KHÔNG TÍNH CHIẾT KHẤU & KHÔNG TÍNH KHUYẾN MÃI)", cellFormat));
			sheet.addCell(new Label(15, 4, "THỜI GIAN BẮT ĐẦU NGÀY LÀM VIỆC", cellFormat));
			sheet.addCell(new Label(16, 4, "THỜI GIAN KẾT THÚC NGÀY LÀM VIỆC", cellFormat));
			sheet.addCell(new Label(17, 4, "QUÃNG ĐƯỜNG ĐI ĐƯỢC (KM)", cellFormat));
			sheet.addCell(new Label(18, 4, "THỜI GIAN LÀM VIỆC", cellFormat));
			
			sheet.setRowView(100, 4);
			
			sheet.setColumnView(0, 15);
			sheet.setColumnView(1, 20);
			sheet.setColumnView(2, 30);
			sheet.setColumnView(3, 35);
			sheet.setColumnView(4, 20);
			sheet.setColumnView(5, 35);
			sheet.setColumnView(6, 15);
			sheet.setColumnView(7, 15);
			sheet.setColumnView(8, 15);
			sheet.setColumnView(9, 15);
			sheet.setColumnView(10, 15);
			sheet.setColumnView(11, 15);
			sheet.setColumnView(12, 15);
			sheet.setColumnView(13, 15);
			sheet.setColumnView(14, 60);
			sheet.setColumnView(15, 20);
			sheet.setColumnView(16, 20);
			sheet.setColumnView(17, 20);
			Label label;
			Number number;

		
			WritableCellFormat cellFormat2 = new WritableCellFormat(new  jxl.write.NumberFormat("#,###,###"));

			cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			WritableCellFormat cellFormat3 = new WritableCellFormat(new  jxl.write.NumberFormat("#,###,###"));
			cellFormat3.setBackground(jxl.format.Colour.YELLOW);
			cellFormat3.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			WritableCellFormat cformat =null;
			
			while (rs.next())
			{
				/*curDay=rs.getString("Ngay");
				
				if(!prevDay.equals(curDay))
				{
					sheet = w.createSheet(curDay, k);
					prevDay=curDay;
					k++;
					j=5;
				}*/
				
			
				
				String type = "0";
				
				int sophaiVt=   rs.getInt("sophaiVt");
				int sodaVt=   rs.getInt("sodaVt");
				int sovtDung= rs.getInt("sovtDung");
				int sovtSai= rs.getInt("soVtSai");
				int sokhMoi= rs.getInt("sokhMoi");
				int order=  rs.getInt("sodh");
				double doanhso =  rs.getDouble("doanhso");
				double soSku =  rs.getDouble("soSku");
				
				cformat = type.equals("1") ? cellFormat3 : cellFormat2;
				label = new Label(0, j, rs.getString("NGAY"), cformat);sheet.addCell(label);
				label = new Label(1, j, rs.getString("vungTen"), cformat);sheet.addCell(label);
				label = new Label(2, j, rs.getString("kvTen"), cformat);sheet.addCell(label);
				label = new Label(3, j, rs.getString("nppMa"), cformat);sheet.addCell(label);
				label = new Label(3, j, rs.getString("nppTen"), cformat);sheet.addCell(label);
				label = new Label(4, j, rs.getString("ddkdMa"), cformat);sheet.addCell(label);
				label = new Label(5, j, rs.getString("ddkdTen"), cformat);sheet.addCell(label);
				label = new Label(6, j, rs.getString("NgayId"), cformat);sheet.addCell(label);
				
				number= new Number(7,j , sophaiVt,cformat);sheet.addCell(number);
				number= new Number(8,j , sodaVt,cformat);sheet.addCell(number);
				number= new Number(9,j , sovtDung,cformat);sheet.addCell(number);
				number= new Number(10,j , sovtSai,cformat);sheet.addCell(number);
				number= new Number(11,j , sokhMoi,cformat);sheet.addCell(number);
				number= new Number(12,j , order,cformat);sheet.addCell(number);
				number= new Number(13,j , soSku,cformat);sheet.addCell(number);
				number= new Number(14,j , doanhso,cformat);sheet.addCell(number);

				
				String tg1 = rs.getString("thoigianvtnppdautien"); if(tg1 == null) tg1 = "";
				String tg2 = rs.getString("thoigianvtkhdautien"); if(tg2 == null) tg2 = "";
				tg1 = tg1.equals("") ? tg2 : tg1.compareTo(tg2) < 0 ? tg1 : tg2;
				label = new Label(15, j, tg1, cformat);sheet.addCell(label);
				String tgbatdau = tg1;
				
				tg1 = rs.getString("thoigianketthuc"); if(tg1 == null) tg1 = "";
				tg2 = rs.getString("thoigianroikhoicuoicung"); if(tg2 == null) tg2 = "";
				tg1 = tg1.compareTo(tg2) < 0 ? tg2 : tg1;
				label = new Label(16, j, tg1, cformat);sheet.addCell(label);
				String tgketthuc = tg1;
				
				String dateFormatted = "";
				if(tgbatdau.trim().length() > 0 && tgketthuc.trim().length() > 0) {
					Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S", Locale.ENGLISH).parse(tgbatdau);
					Date date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S", Locale.ENGLISH).parse(tgketthuc);
					long diffInMillies = date2.getTime() - date1.getTime();
					
					long time = diffInMillies / 1000;  
					String seconds = Integer.toString((int)(time % 60));  
					String minutes = Integer.toString((int)((time % 3600) / 60));  
					String hours = Integer.toString((int)(time / 3600));
					
					for (int i = 0; i < 2; i++) {  
						if (seconds.length() < 2) {  
							seconds = "0" + seconds;  
						}
						if (minutes.length() < 2) {  
							minutes = "0" + minutes;  
						}
						if (hours.length() < 2) {  
							hours = "0" + hours;  
						}
					}
					
					dateFormatted = hours + ":" + minutes + ":" + seconds;
				}
				
				String ddkdId = rs.getString("ddkdId");
				String query = "select lat, long from ddkd_khachhang where ddkd_fk = " + ddkdId + " and CONVERT(varchar(10), thoidiem, 120) = '" + rs.getString("NGAY") + "'";
				//System.out.println("Query = " + query);
				double quangduong = 0, lat1 = 0, lon1 = 0, lat0 = 0, lon0 = 0, index = 0;
				ResultSet _rs = db.get(query);
				try {
					while(_rs.next()) {
						if(index == 0) {
							lat1 = _rs.getDouble("lat");
							lon1 = _rs.getDouble("long");
							index++;
						}
						lat0 = lat1; lon0 = lon1;
						lat1 = _rs.getDouble("lat"); lon1 = _rs.getDouble("long");
						//System.out.println(lat0 + "	" + lon0 + "	" + lat1 + "	" + lon1 + "	" + getKhoangCachHaversine(lat0, lon0, lat1, lon1));
						
						quangduong += getKhoangCachHaversine(lat0, lon0, lat1, lon1);
						
					}
					_rs.close();
				} catch(Exception e) {
					//System.out.println("[BCViengThamOutlet] err message = " + e.getMessage());
				}
				//cformat.setAlignment(Alignment.RIGHT);
				label = new Label(17, j, String.valueOf(formatter.format(quangduong/1000)), cformat);sheet.addCell(label);
				//cformat.setAlignment(Alignment.LEFT);
				
				label = new Label(18, j, dateFormatted, cformat);sheet.addCell(label);
				
				j++;
			}
			w.write();
			w.close();
		} catch (Exception e)
		{
			System.out.println("Error Cac Ban : " + e.getMessage());
			e.printStackTrace();
		} finally
		{
			if (out1 != null)
				out1.close();
		}
	}

	private void XuatFileExcelChiTiet(HttpServletResponse response, ILoTrinh lotrinh) throws IOException
	{
		OutputStream out1 = null;
		try
		{
			
			Utility util = new Utility();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=BCThucHienViengThamOutletChiTiet.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());
			
			WritableSheet sheet = null;
			String NppId = lotrinh.getnppId();
			String DdkdId = lotrinh.getddkdId();
			String tuyenId = lotrinh.gettuyenId();
			String tungay = lotrinh.getTungay();
			String denngay = lotrinh.getDenngay();			
			
			dbutils db= new dbutils();
			
			String query="DROP TABLE TEMP_BC";
			db.update(query);
			
			query=
			"CREATE TABLE TEMP_BC "+
			"(  "+
			"	DDKD_FK NUMERIC(18,0), "+	
			"	NGAYID INT, "+
			"	NGAY VARCHAR(10), "+
			"	CONSTRAINT UNI_TEMP_BC UNIQUE(DDKD_FK,NGAY,NGAYID) "+
		    " 	) ";
			db.update(query);
			
			query=
			"declare @TuNgay varchar(10),@DenNgay varchar(10) "+
			" set @TuNgay='"+tungay+"' "+
			"  set @DenNgay='"+denngay+"' "+
		    " 	while(@TuNgay<=@DenNgay) "+
		 	" BEGIN  "+
			"	INSERT INTO TEMP_BC(DDKD_FK,NGAY,NGAYID) "+
			"	SELECT  DISTINCT DDKD_FK,@TuNgay,datepart(dw, @TuNgay) "+
			"	FROM ddkd_khachhang "+
			"	SET @TuNgay=(select convert(varchar(10),DATEADD(DAY,1,@TuNgay),20) ) "+	
			"  END ";
			db.update(query);
			
			
			
			
			String sql =  
			"select 	kbh.TEN as kbh,v.ten as Vung,kv.TEN as KhuVuc,npp.ma as nppMa,npp.ten as nppTen, kv.ten as khuvuc, ddkd.SmartId as ddkdMa,    \n" +
			" 	vt.vitri ,hch.hang as hangcuahang,lch.loai as loaicuahang,kh.phuong as phuong, viengtham.ngayvt as ngaythang,      \n" +
			" 	kh.chucuahieu,kh.tinhthanh_fk,kh.quanhuyen_fk,  kh.ten as tencuahieu, kh.smartid, kh.diachi, kh.dienthoai, viengtham.ngayid,     \n" +
			" 	isnull(kh.lat, '0') as khlat,isnull(kh.long, '0') as khlong,      \n" +
			" 	isnull ( 	 ( select sum(soluong*giamua) 	from donhang_sanpham dhsp inner join donhang dh on dh.pk_seq=dhsp.donhang_fk    \n" +
			" 	where dh.khachhang_fk=kh.pk_seq and dh.trangthai = 1 and dh.ngaynhap = viengtham.ngayvt    	),0 )  as doanhso  , viengtham.*       \n" +
			" from daidienkinhdoanh ddkd      \n" +
			" left join      \n" +
			" (   	    \n" +
			" /*****************Khong vieng tham *********************************/ " +
			" 	select null as thoidiemdi,a.pk_seq as ddkdid,a.ten as ddkdten,b.diengiai,b.ngayid,null as thoidiem,vt.NgayId as NgayIdVt,    \n" +
			" 	-1 as type,kh_tbh.khachhang_fk as khid, b.npp_fk as nppid, b.pk_seq as tbhid, vt.Ngay as  ngayvt, kh_tbh.sott as sott,    \n" +
			" 	kh_tbh.tanso as tanso, null as lat, null  as long, null as dolech      \n" +
			" 	from daidienkinhdoanh a inner join tuyenbanhang b on a.pk_seq = b.ddkd_fk     \n" +
			" 	inner join khachhang_tuyenbh kh_tbh on b.pk_seq = kh_tbh.tbh_fk    \n" +
			" 	inner  join  \n" +
			" 	(  \n" +
			" 		select  ddkd_fk, NgayId,Ngay \n" +
			" 		from  TEMP_BC vt \n" +
			" 		where  1=1  " ;
			
			if(tungay.length()>0)
				sql+=" and Ngay>='"+tungay+"'";
			if(denngay.length()>0)
				sql+=" and Ngay<='"+denngay+"'";
			if(DdkdId.length()>0)
				sql+=" and ddkd_fk='"+DdkdId+"'";
			if(tuyenId.length()>0)
				sql+=" and NgayID='"+tuyenId+"'";
			
			sql+=
			" 	) vt on   b.NgayId=vt.NgayId and vt.ddkd_fk=a.pk_seq \n" +
			" 	where not exists		 \n" +
			" 	(   \n" +
			" 		select * from  ddkd_khachhang c  \n" +
			" 		where ddkd_fk=a.pk_seq and kh_tbh.khachhang_fk=khachhang_fk and  datepart(dw, c.thoidiem) =b.ngayid    \n" +
			" 		and CONVERT(varchar(10), thoidiem, 120)=vt.Ngay \n" +
			" 	) 	     " ;
			if(DdkdId.length()>0)
				sql+=" and a.pk_seq='"+DdkdId+"'";
			if(tuyenId.length()>0)
				sql+=" and b.ngayId='"+tuyenId+"'";
			if(NppId.length()>0)
				sql+=" and a.npp_fk='"+NppId+"'";
			
			sql+=
			" /*****************Vieng tham *********************************/ " +
			" union all    \n" +
			" 	select c.thoidiemdi , a.pk_seq as ddkdid, a.ten as ddkdten, b.diengiai, b.ngayid,     \n" +
			" 		thoidiem, datepart(dw, c.thoidiem) as ngayidvt, 0 as type, c.khachhang_fk as khid, b.npp_fk as nppid,     \n" +
			" 		b.pk_seq as tbhid, CONVERT(varchar(10), thoidiem, 120) as  ngayvt, kh_tbh.sott as sott,    \n" +
			" 		kh_tbh.tanso as tanso, isnull(c.lat, '0') as lat, isnull(c.long, '0') as long, isnull(c.dolech, '') as dolech     \n" +
			" 	from daidienkinhdoanh a inner join tuyenbanhang b on a.pk_seq = b.ddkd_fk      \n" +
			" 		left join ddkd_khachhang c on a.pk_seq = c.ddkd_fk and b.ngayid = datepart(dw, c.thoidiem)    \n" +
			" 		inner join khachhang_tuyenbh kh_tbh on b.pk_seq = kh_tbh.tbh_fk and kh_tbh.khachhang_fk = c.khachhang_fk      \n" +
			" 		left join      \n" +
			" 		(     \n" +
			" 			select   max(thoidiem) as thoidiemmax,ddkd_fk,khachhang_fk, CONVERT(varchar(10), thoidiem, 120) as ngay    \n" +
			" 			from ddkd_khachhang      \n" +
			" 			group by ddkd_fk,khachhang_fk, CONVERT(varchar(10), thoidiem, 120)     \n" +
			" 		)      \n" +
			" 	maxtd on maxtd.thoidiemmax = c.thoidiem and maxtd.ddkd_fk = c.ddkd_fk and maxtd.khachhang_fk = c.khachhang_fk   	  	   \n" +
			"  where 1=1    " ;
			if(tungay.length()>0)
				sql+=" and CONVERT(varchar(10), c.thoidiem, 120)>='"+tungay+"'";
			if(denngay.length()>0)
				sql+=" and CONVERT(varchar(10), c.thoidiem, 120)<='"+denngay+"'";
			if(DdkdId.length()>0)
				sql+=" and a.pk_seq='"+DdkdId+"'";
			if(NppId.length()>0)
				sql+=" and a.npp_fk='"+NppId+"'";
			if(tuyenId.length()>0)
				sql+=" and b.ngayId='"+tuyenId+"'";
			sql+=
			" union all     " +
			" /*****************Vieng tham Sai Tuyen *********************************/ " +
			" 	select  c.thoidiemdi,a.pk_seq as ddkdid, a.ten as ddkdten, b.diengiai, b.ngayid, thoidiem,     \n" +
			" 		datepart(dw, c.thoidiem) as NgayVtTt, 1 as type, c.khachhang_fk, b.npp_fk, b.pk_seq, replace(convert(nvarchar(10), thoidiem , 102) , '.', '-' ),     \n" +
			" 		kh_tbh.sott as sott, kh_tbh.tanso as tanso, isnull(c.lat, '0') as lat, isnull(c.long, '0') as long, isnull(c.dolech, '') as dolech     \n" +
			" 	from daidienkinhdoanh a inner join tuyenbanhang b on a.pk_seq = b.ddkd_fk      \n" +
			" 		left join ddkd_khachhang c on a.pk_seq = c.ddkd_fk and b.ngayid != datepart(dw, c.thoidiem)      \n" +
			" 		inner join khachhang_tuyenbh kh_tbh on b.pk_seq = kh_tbh.tbh_fk and kh_tbh.khachhang_fk = c.khachhang_fk      \n" +
			"  \n" +
			" left join       \n" +
			" 		(       			 \n" +
			" 			select   max(thoidiem) as thoidiemmax,ddkd_fk,khachhang_fk, CONVERT(varchar(10), thoidiem, 120)   as ngay    \n" +
			" 			from ddkd_khachhang      \n" +
			" 			group by ddkd_fk,khachhang_fk, CONVERT(varchar(10), thoidiem, 120)        \n" +
			" 		)  maxtd on maxtd.thoidiemmax = c.thoidiem and maxtd.ddkd_fk = c.ddkd_fk and maxtd.khachhang_fk = c.khachhang_fk   " +
			" where    " +
			" 		 not exists \n" +
			" 		( " +
			" 			 select *  \n" +
			" 			from daidienkinhdoanh dd inner join tuyenbanhang tbh on dd.pk_seq = tbh.ddkd_fk    \n" +
			" 				left join ddkd_khachhang ddkh on ddkh.ddkd_fk =dd.PK_SEQ   and tbh.ngayid =datepart(dw, ddkh.thoidiem)   \n" +
			" 				inner join khachhang_tuyenbh kh_tbh on kh_tbh.KHACHHANG_FK=ddkh.khachhang_fk and kh_tbh.TBH_FK=tbh.PK_SEQ  \n" +
			" 			where c.thoidiem=ddkh.thoidiem and ddkh.khachhang_fk=c.khachhang_fk  \n" +
			" 		)	  	     ";
			if(tungay.length()>0)
				sql+=" and CONVERT(varchar(10), c.thoidiem, 120)>='"+tungay+"' ";
			if(denngay.length()>0)
				sql+=" and CONVERT(varchar(10), c.thoidiem, 120)<='"+denngay+"' ";
			if(DdkdId.length()>0)
				sql+=" and a.pk_seq='"+DdkdId+"' ";
			if(NppId.length()>0)
				sql+=" and a.npp_fk='"+NppId+"' ";
			if(tuyenId.length()>0)
				sql+=" and b.ngayId='"+tuyenId+"' ";
			sql+=
			" )  viengtham on viengtham.ddkdid = ddkd.pk_seq        \n" +
			" 	left join khachhang kh on kh.pk_seq = viengtham.khid      \n" +
			" 	left join hangcuahang hch on hch.pk_seq = kh.hch_fk      \n" +
			" 	left join loaicuahang lch on lch.pk_seq = kh.lch_fk       \n" +
			" 	left join vitricuahang vt on vt.pk_seq = kh.vtch_fk      \n" +
			" 	left join nhaphanphoi npp on npp.pk_seq = viengtham.nppid      \n" +
			" 	left join khuvuc kv on kv.pk_Seq=npp.khuvuc_fk    \n" +
			" 	left join vung v on v.pk_seq=kv.vung_fk    \n" +
			" 	left join nhapp_kbh on nhapp_kbh.npp_fk=npp.pk_seq    \n" +
			" left join kenhbanhang kbh on kbh.pk_seq = nhapp_kbh.kbh_fk    \n" +
			" where 1=1 " ;
			
		
			if(tungay.length()>0)
				sql+= " and viengtham.Ngayvt>='"+tungay+"'";
					
			if(tungay.length()>0)
				sql+= " and viengtham.Ngayvt<='"+denngay+"'";
			
			if(NppId.trim().length() > 0)
			{
				sql+=" and npp.pk_seq=" + NppId +"  ";
			}
			if (DdkdId.trim().length() > 0)
			{
				sql += " and ddkd.pk_seq=" + DdkdId;
			}
			if(lotrinh.getvungId().trim().length() > 0)
			{
				sql += " and v.pk_seq = '" + lotrinh.getvungId() + "' ";
			}
			if(lotrinh.getkhuvucId().trim().length() > 0)
			{
				sql += " and kv.pk_seq = '" + lotrinh.getkhuvucId() + "' ";
			}
			if(tuyenId.length()>0)
			{
				sql += " and ddkd_sokh.NgayID = '" + tuyenId + "' ";
			}
			
			sql+= " order by npp.pk_Seq,viengtham.Ngayvt,ddkd.pk_seq,viengtham.ngayId  ";
			System.out.println("LAY BC: " + sql);
			
			ResultSet rs = db.get(sql);
			WritableCellFormat cformat =null;
			int count = 5;
			int k=0;
		/*	String nppPrev="";
			String nppCur="";*/
			sheet = w.createSheet("BCThucHienViengThamOutLetCT", k);
			
			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 14);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);
			
			sheet.addCell(new Label(0, 0, "BÁO CÁO THỰC HIỆN VIẾNG THĂM OUTLET CHI TIẾT", new WritableCellFormat(cellTitle)));
			sheet.addCell(new Label(0, 1, "TỪ NGÀY: " + lotrinh.getTungay()));
			sheet.addCell(new Label(0, 2, "ĐẾN NGÀY: " + lotrinh.getDenngay() ));
			
			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
			cellFont.setColour(Colour.BLACK);

			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

			cellFormat.setBackground(jxl.format.Colour.GRAY_25);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);


			sheet.addCell(new Label(0, 4, "KÊNH", cellFormat));
			sheet.addCell(new Label(1, 4, "VÙNG", cellFormat));
			sheet.addCell(new Label(2, 4, "KHU VỰC", cellFormat));
			sheet.addCell(new Label(3, 4, "NHÀ PHÂN PHỐI", cellFormat));
			sheet.addCell(new Label(4, 4, "NHÂN VIÊN BÁN HÀNG", cellFormat));
			sheet.addCell(new Label(5, 4, "TUYẾN BÁN HÀNG", cellFormat));
			sheet.addCell(new Label(6, 4, "NGÀY", cellFormat));
			sheet.addCell(new Label(7, 4, "STT", cellFormat));
			sheet.addCell(new Label(8, 4, "KHÁCH HÀNG", cellFormat));
			sheet.addCell(new Label(9, 4, "ĐỊA CHỈ", cellFormat));
			sheet.addCell(new Label(10, 4, "VIẾNG THĂM", cellFormat));
			sheet.addCell(new Label(11, 4, "ĐỘ LỆCH", cellFormat));
			sheet.addCell(new Label(12, 4, "THỜI GIAN", cellFormat));
			sheet.addCell(new Label(13, 4, "DOANH SỐ", cellFormat));
			
			
			
			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 15);
			sheet.setColumnView(2, 30);
			sheet.setColumnView(3, 55);
			sheet.setColumnView(4, 25);
			sheet.setColumnView(5, 15);
			sheet.setColumnView(6, 10);
			sheet.setColumnView(7, 10);
			sheet.setColumnView(8, 30);
			sheet.setColumnView(9, 55);
			sheet.setColumnView(10,15 );
			sheet.setColumnView(11, 15);
			sheet.setColumnView(12, 15);
			
			
			if(rs != null)
			{
				Label label;
				Number number;
				
				WritableCellFormat cellFormat2 = new WritableCellFormat(new  jxl.write.NumberFormat("#,###,###"));

				cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
				
				WritableCellFormat cellFormat3 = new WritableCellFormat(new  jxl.write.NumberFormat("#,###,###"));
				cellFormat3.setBackground(jxl.format.Colour.YELLOW);
				cellFormat3.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat3.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat3.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat3.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
				
				
				while(rs.next())
				{
					String type = rs.getString("type")==null?"":rs.getString("type");
					String viengtham="";
					cformat =cellFormat2;
					
					if(type.equals("-1"))
					{
						viengtham="Không";
					}else if(type.equals("0"))
					{
						viengtham="Có";
					}
					else if(type.equals("1"))
					{
						viengtham="Có";
						cformat=cellFormat3;
					}
								
					
					label = new Label(0, count, rs.getString("kbh"), cformat);sheet.addCell(label);
					label = new Label(1, count, rs.getString("vung"), cformat);sheet.addCell(label);
					label = new Label(2, count, rs.getString("khuvuc"), cformat);sheet.addCell(label);
					label = new Label(3, count, rs.getString("nppTen"), cformat);sheet.addCell(label);
					label = new Label(4, count, rs.getString("ddkdTen"), cformat);sheet.addCell(label);
					label = new Label(5, count, rs.getString("ngayId"), cformat);sheet.addCell(label);
					label = new Label(6, count, rs.getString("ngayVT"), cformat);sheet.addCell(label);
					label = new Label(7, count, rs.getString("SOTT"), cformat);sheet.addCell(label);
					
					label = new Label(8, count, rs.getString("tencuahieu"), cformat);sheet.addCell(label);
					label = new Label(9, count, rs.getString("diachi"), cformat);sheet.addCell(label);
					
					label = new Label(10, count, viengtham, cformat);sheet.addCell(label);
					
					number= new Number(11,count , rs.getDouble("dolech"),cformat);sheet.addCell(number);			
					label = new Label(12, count,  rs.getString("thoidiem"), cformat);sheet.addCell(label);					
					number= new Number(13,count , rs.getDouble("doanhso"),cformat);sheet.addCell(number);
					count++;
					
				}
				rs.close();
				query="DROP TABLE TEMP_BC";
				db.update(query);
				w.write();
				w.close();
			}
		} 
		catch (Exception e)
		{
			System.out.println("Error Cac Ban : " + e.getMessage());
			e.printStackTrace();
		} 
		finally
		{
			if (out1 != null)
				out1.close();
		}
		
	}
	
	
	
	
	private void XuatFileExcelChiTietSO(HttpServletResponse response, ILoTrinh lotrinh) throws IOException
	{
		OutputStream out1 = response.getOutputStream();
		try
		{
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=ViengThamOutletDenDonHang.xlsm");
			
			String strfstream = getServletContext().getInitParameter("path")+"\\ViengThamOutletSO.xlsm";
			
			FileInputStream fstream = new FileInputStream(strfstream);
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

			_XuatFileExcelChiTietSOHeader(workbook, lotrinh);	     
			_XuatFileExcelChiTietSOData(workbook, lotrinh, response);
		    workbook.save(out1);
		    fstream.close();
		} 
		catch (Exception e)
		{
			System.out.println("Error Cac Ban : " + e.getMessage());
			e.printStackTrace();
		} 
		finally
		{
			if (out1 != null)
				out1.close();
		}
		
	}
	
	private void _XuatFileExcelChiTietSOHeader(Workbook workbook, ILoTrinh obj)throws Exception 
 	{
 		
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
	    
	    cell.setValue("BÁO CÁO THỰC HIỆN VIẾNG THĂM CHI TIẾT ĐƠN HÀNG");  getCellStyle(workbook,"A1",Color.RED,true,14);	  	
	    	  
	    cells.setRowHeight(2, 18.0f);
	    cell = cells.getCell("A3"); 
	    getCellStyle(workbook,"A3",Color.NAVY,true,10);	    
	    cell.setValue("Từ ngày: " + obj.getTungay());
	    
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("B3"); getCellStyle(workbook,"B3",Color.NAVY,true,9);	        
	    cell.setValue("Đến ngày: " + obj.getDenngay());    
	    
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A4");getCellStyle(workbook,"A4",Color.NAVY,true,9);
	    cell.setValue("Ngày báo cáo: " + this.getdate());
			  
	    cell = cells.getCell("EA1"); cell.setValue("Kenh");
		cell = cells.getCell("EB1"); cell.setValue("Vung");
		cell = cells.getCell("EC1"); cell.setValue("KhuVuc");
 	    cell = cells.getCell("ED1"); cell.setValue("NhaPhanPhoi");  	
 	    cell = cells.getCell("EE1"); cell.setValue("NhanVienBanHang");
	    cell = cells.getCell("EF1"); cell.setValue("TuyenBanHang");
	    cell = cells.getCell("EG1"); cell.setValue("Ngay");
	    cell = cells.getCell("EH1"); cell.setValue("STT");
	    cell = cells.getCell("EI1"); cell.setValue("KhachHang");
	    cell = cells.getCell("EJ1"); cell.setValue("DiaChi");
	    cell = cells.getCell("EK1"); cell.setValue("ViengTham");
	    cell = cells.getCell("EL1"); cell.setValue("MaDonHang");
	    cell = cells.getCell("EM1"); cell.setValue("MaSanPham");
	    cell = cells.getCell("EN1"); cell.setValue("TenSanPham");
	    cell = cells.getCell("EO1"); cell.setValue("SoLuong");
	    cell = cells.getCell("EP1"); cell.setValue("DonGia");
	    cell = cells.getCell("EQ1"); cell.setValue("ThanhTien");
	    cell = cells.getCell("ER1"); cell.setValue("NgayTao");
	    cell = cells.getCell("ES1"); cell.setValue("SoLuongThung");
	    cell = cells.getCell("ET1"); cell.setValue("DonVi");
	     
 	}
	
	private void _XuatFileExcelChiTietSOData(Workbook workbook, ILoTrinh lotrinh, HttpServletResponse response) throws Exception
 	{
		lotrinh.DBclose();
		
 		Worksheets worksheets = workbook.getWorksheets();
 	    Worksheet worksheet = worksheets.getSheet(0);
 	    Cells cells = worksheet.getCells();
 	    
 	   String NppId = lotrinh.getnppId();
		String DdkdId = lotrinh.getddkdId();
		String tuyenId = lotrinh.gettuyenId();
		String tungay = lotrinh.getTungay();
		String denngay = lotrinh.getDenngay();			
		
		dbutils db= new dbutils();
		
		String query = "DROP TABLE TEMP_BC";
		db.update(query);
		
		query = " CREATE TABLE TEMP_BC "+
				" (  "+
				"	DDKD_FK NUMERIC(18,0), "+	
				"	NGAYID INT, "+
				"	NGAY VARCHAR(10), "+
				"	CONSTRAINT UNI_TEMP_BC UNIQUE(DDKD_FK,NGAY,NGAYID) "+
			    "  ) ";
		db.update(query);
		
		query = " declare @TuNgay varchar(10),@DenNgay varchar(10) "+
				"  set @TuNgay='"+tungay+"' "+
				"  set @DenNgay='"+denngay+"' "+
			    " 	while(@TuNgay<=@DenNgay) "+
			 	" BEGIN  "+
				"	INSERT INTO TEMP_BC(DDKD_FK,NGAY,NGAYID) "+
				"	SELECT  DISTINCT DDKD_FK,@TuNgay,datepart(dw, @TuNgay) "+
				"	FROM ddkd_khachhang "+
				"	SET @TuNgay=(select convert(varchar(10),DATEADD(DAY,1,@TuNgay),20) ) "+	
				"  END ";
		db.update(query);
		
		
		//TITLE BÁO CÁO
		ResultSet rs;
		String khuvuc = "";
		if(lotrinh.getkhuvucId() != null && lotrinh.getkhuvucId().trim().length() > 0) {
			query = " select TEN from khuvuc where pk_seq = " + lotrinh.getkhuvucId().trim();
			rs = db.get(query);
			try { rs.next(); khuvuc = "_" + util.replaceAEIOU(rs.getString("ten")); rs.close(); } catch(Exception e) { }
		}
		
		String npp = "";
		if(lotrinh.getnppId() != null && lotrinh.getnppId().trim().length() > 0) {
			query = " select TEN from nhaphanphoi where pk_seq = " + lotrinh.getnppId().trim();
			rs = db.get(query);
			try { rs.next(); npp = "_" + util.replaceAEIOU(rs.getString("ten")); rs.close(); } catch(Exception e) { }
		}
		
		String ddkd = "";
		if(lotrinh.getddkdId() != null && lotrinh.getddkdId().trim().length() > 0) {
			query = " select TEN from daidienkinhdoanh where pk_seq = " + lotrinh.getddkdId().trim();
			rs = db.get(query);
			try { rs.next(); ddkd = "_" + util.replaceAEIOU(rs.getString("ten")); rs.close(); } catch(Exception e) { }
		}
		
		String tuyen = "";
		if(lotrinh.gettuyenId() != null && lotrinh.gettuyenId().trim().length() > 0) {
			query = " select ngaylamviec from tuyenbanhang where pk_seq = " + lotrinh.gettuyenId().trim();
			rs = db.get(query);
			try { rs.next(); tuyen = "_Thu" + util.replaceAEIOU(rs.getString("ngaylamviec")); rs.close(); } catch(Exception e) { }
		}
		
		response.setHeader("Content-Disposition", "attachment; filename=ViengThamOutletDenDonHang_"+lotrinh.getTungay()+"_"+lotrinh.getDenngay()+khuvuc+npp+ddkd+tuyen+".xlsm");
		
		//DATA BÁO CÁO
		
		String sql =  
			" SELECT BC.NGAY AS NGAYTHANG, BC.NGAYID, BC.DDKD_FK, DH.DH_FK AS MADONHANG, DH.NGAYDH, DH.NGAYTAO AS NGAYVT, DH.KHACHHANG_FK, DH.NPP_FK, DH.KBH_FK, DH.ISPDA, DH.TRANGTHAI " +
			" 	, TBH.PK_SEQ AS TBH_FK, ISNULL(TBH.DIENGIAI, '') AS TBHTEN, ISNULL(TBH.NGAYID, 0) AS NGAYIDDUNG, KHTBH.SOTT " +
			" 	, VT.LAT, VT.LONG, NPP.TEN AS NPPTEN, KV.TEN AS KHUVUC, V.TEN AS VUNG, KBH.TEN AS KBH " +
			" 	, DDKD.TEN AS DDKDTEN " +
			" 	, KH.TEN AS TENCUAHIEU, KH.DIACHI " +
			" 	, SP.MA AS MASP, SP.TEN AS TENSP, dhsp.SOLUONG as soluongsp, dhsp.GIAMUA as dongiasp" +
			"	, ISNULL(DVDL.DONVI,'') AS DONVI, ISNULL(QC.SOLUONG1, 0) AS SL1, ISNULL(QC.SOLUONG2, 0) AS SL2 " +
			" FROM TEMP_BC BC  " +
			" LEFT JOIN ( " +
			" 	SELECT PK_SEQ AS DH_FK, DDKD_FK, KHACHHANG_FK, NPP_FK, KBH_FK, DH.NGAYNHAP AS NGAYDH, DH.NGAYTAO AS NGAYTAO, ISNULL(ISPDA, 0) AS ISPDA , DH.TRANGTHAI " +
			" 	FROM DONHANG DH " +
			" 	WHERE DH.TRANGTHAI != 2 AND DH.NGAYNHAP >= '"+tungay+"' AND DH.NGAYNHAP <= '"+denngay+"' " + (DdkdId.length()>0 ? " and dh.ddkd_fk='"+DdkdId+"' " : " ") + (NppId.length()>0 ? " and dh.npp_fk='"+NppId+"' " : " ") +
			" ) DH ON BC.NGAY = DH.NGAYDH AND BC.DDKD_FK = DH.DDKD_FK " +
			" LEFT JOIN DONHANG_SANPHAM DHSP ON DHSP.DONHANG_FK = DH.DH_FK " +
			" LEFT JOIN SANPHAM SP ON DHSP.SANPHAM_FK = SP.PK_SEQ  " +
			" LEFT JOIN DONVIDOLUONG DVDL ON SP.DVDL_FK = DVDL.PK_SEQ  " +
			" LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK = SP.PK_SEQ AND SP.DVDL_FK = QC.DVDL1_FK AND QC.DVDL2_FK = 100018 " +
			" LEFT JOIN KHACHHANG_TUYENBH KHTBH ON KHTBH.KHACHHANG_FK = DH.KHACHHANG_FK " +
			" LEFT JOIN TUYENBANHANG TBH ON TBH.PK_SEQ = KHTBH.TBH_FK AND TBH.DDKD_FK = BC.DDKD_FK " +
			" LEFT JOIN DDKD_KHACHHANG VT ON VT.DDKD_FK = BC.DDKD_FK AND VT.KHACHHANG_FK = DH.KHACHHANG_FK AND CONVERT(VARCHAR(10), VT.THOIDIEM, 120) = BC.NGAY " +
			" LEFT JOIN DAIDIENKINHDOANH DDKD ON DDKD.PK_SEQ = BC.DDKD_FK " +
			" LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = DH.KHACHHANG_FK  " +
			" LEFT JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = DH.NPP_FK " +
			" LEFT JOIN KHUVUC KV ON KV.PK_SEQ = NPP.KHUVUC_FK " +
			" LEFT JOIN VUNG V ON V.PK_SEQ = KV.VUNG_FK " +
			" LEFT JOIN NHAPP_KBH ON NHAPP_KBH.NPP_FK=NPP.PK_SEQ " +
			" LEFT JOIN KENHBANHANG KBH ON KBH.PK_SEQ = NHAPP_KBH.KBH_FK " +
			" WHERE BC.NGAY >= '"+tungay+"' AND BC.NGAY <= '"+denngay+"'  ";
		
		if(NppId.trim().length() > 0)
		{
			sql+=" and npp.pk_seq=" + NppId +"  ";
		}
		if (DdkdId.trim().length() > 0)
		{
			sql += " and ddkd.pk_seq=" + DdkdId;
		}
		if(lotrinh.getvungId().trim().length() > 0)
		{
			sql += " and v.pk_seq = '" + lotrinh.getvungId() + "' ";
		}
		if(lotrinh.getkhuvucId().trim().length() > 0)
		{
			sql += " and kv.pk_seq = '" + lotrinh.getkhuvucId() + "' ";
		}
		if(tuyenId.length()>0)
		{
			sql += " and bc.NgayID = '" + tuyenId + "' ";
		}
		
		System.out.println("LAY BC: " + sql);
		
		rs = db.get(sql);
		
 	    int i = 2;

		Cell cell = null;
		if(rs != null) {
			while(rs.next())
			{
				String type = rs.getString("lat") == null?"0" :rs.getString("lat");
				String viengtham = type.equals("0") ? "Không" : "Có";
				
				cell = cells.getCell("EA" + Integer.toString(i)); cell.setValue(rs.getString("kbh"));
				cell = cells.getCell("EB" + Integer.toString(i)); cell.setValue(rs.getString("vung"));			
				cell = cells.getCell("EC" + Integer.toString(i)); cell.setValue(rs.getString("khuvuc"));
				cell = cells.getCell("ED" + Integer.toString(i)); cell.setValue(rs.getString("nppTen"));
				cell = cells.getCell("EE" + Integer.toString(i)); cell.setValue(rs.getString("ddkdTen"));
				cell = cells.getCell("EF" + Integer.toString(i)); cell.setValue(rs.getString("ngayId"));
				cell = cells.getCell("EG" + Integer.toString(i)); cell.setValue(rs.getString("NGAYTHANG"));
				cell = cells.getCell("EH" + Integer.toString(i)); cell.setValue(rs.getString("SOTT"));
				cell = cells.getCell("EI" + Integer.toString(i)); cell.setValue(rs.getString("tencuahieu"));
				cell = cells.getCell("EJ" + Integer.toString(i)); cell.setValue(rs.getString("diachi"));
				cell = cells.getCell("EK" + Integer.toString(i)); cell.setValue(viengtham);
				cell = cells.getCell("EL" + Integer.toString(i)); cell.setValue(rs.getString("madonhang"));
				cell = cells.getCell("EM" + Integer.toString(i)); cell.setValue(rs.getString("masp"));
				cell = cells.getCell("EN" + Integer.toString(i)); cell.setValue(rs.getString("tensp"));
				
				double sl = 0, dg = 0, slthung = 0;
				try { sl = Double.parseDouble(rs.getString("soluongsp")); } catch(Exception e) { }
				try { dg = Double.parseDouble(rs.getString("dongiasp")); } catch(Exception e) { }
				try {
					double sl1 = Double.parseDouble(rs.getString("sl1"));
					double sl2 = Double.parseDouble(rs.getString("sl2"));
					if(sl1 != 0) { 
						slthung = sl * sl2 / sl1;
					} 
				} catch(Exception e) { }
				cell = cells.getCell("EO" + Integer.toString(i)); cell.setValue(sl);
				cell = cells.getCell("EP" + Integer.toString(i)); cell.setValue(dg);
				cell = cells.getCell("EQ" + Integer.toString(i)); cell.setValue(sl*dg);

				cell = cells.getCell("ER" + Integer.toString(i)); cell.setValue(rs.getString("NGAYVT"));
				cell = cells.getCell("ES" + Integer.toString(i)); cell.setValue(slthung);
				cell = cells.getCell("ET" + Integer.toString(i)); cell.setValue(rs.getString("DONVI"));
				
				
				i++;
			}
			rs.close();
		}
		else
 		{
 			throw new Exception("Khong tao duoc bao cao trong thoi gian nay...");
 		}
 	}


	private String getdate() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy: hh:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private void getCellStyle(Workbook workbook, String a, Color mau, Boolean dam, int size)
	{
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
	    
		 
	    style.setHAlignment(TextAlignmentType.LEFT);
	    cell.setStyle(style);
	}


	private void setAn(Workbook workbook,int i)
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	    for(int j = 26; j <= i; j++)
	    { 
	    	cells.hideColumn(j);
	    }
		
	}
	
	
	
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public static double getKhoangCachHaversine(double lat1, double long1, double lat2, double long2)
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

        return R * c * 1000; //m
    }
	
	private static double toRad(double value)
    {
        return value * Math.PI / 180;
    }

	public static void main(String[] args)
	{
		System.out.println("QUANGDUONG: " + getKhoangCachHaversine(10.803245, 106.68158833333334, 10.803036666666666, 106.68127000000001));
		
		/*Calendar tu_ngay = new GregorianCalendar(2013, 1, 28);		
		
		tu_ngay.add(Calendar.DATE, 1);
		System.out.println("COMPARE: " + tu_ngay.get(Calendar.YEAR));
		System.out.println("COMPARE: " + tu_ngay.get(Calendar.MONTH));
		System.out.println("COMPARE: " + tu_ngay.get(Calendar.DAY_OF_MONTH));*/
	}
	

}
