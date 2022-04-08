package geso.dms.center.servlets.danhmucquyen;
import geso.dms.center.beans.daidienkinhdoanh.IDaidienkinhdoanhList;
import geso.dms.center.beans.danhmucquyen.IDanhmucquyen;
import geso.dms.center.beans.danhmucquyen.IDanhmucquyenList;
import geso.dms.center.beans.danhmucquyen.imp.Danhmucquyen;
import geso.dms.center.beans.danhmucquyen.imp.Danhmucquyenlist;
import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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


public class DanhmucquyenSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
	
	public DanhmucquyenSvl()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		this.out = response.getWriter();
		
		HttpSession session = request.getSession();
		
		Utility util = new Utility();
		out = response.getWriter();
		
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		IDanhmucquyenList obj;
		obj = new Danhmucquyenlist();
		
		
//		String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
//		if (view == null) {
//			view = "";
//		}
		
//		String param = "";
//		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "DanhmucquyenSvl", "", Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}else{ 
			System.out.println("user :" + userId);
			obj.setUserId(userId);
			session.setAttribute("obj", obj);
			String nextJSP = "/AHF/pages/Center/DanhMucQuyen.jsp";
			response.sendRedirect(nextJSP);
		}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		IDanhmucquyenList obj = new Danhmucquyenlist();
		Utility util = new Utility();
		
		HttpSession session = request.getSession();
		String MaQuyen = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("maquyen")));
		if (MaQuyen == null)
			MaQuyen = "";
		obj.setMaQuyen(MaQuyen);
		String TenQuyen = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tenquyen")));
		if (TenQuyen == null)
			TenQuyen = "";
		obj.setTenQuyen(TenQuyen);
		String Tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
		if (Tungay == null)
			Tungay = "";
		obj.setTungay(Tungay);
		String Denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
		if (Denngay == null)
			Denngay = "";
		obj.setDenngay(Denngay);
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		
		
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "DanhmucquyenSvl", "", Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
		
		if(action.equals("Xoa"))
		{
			
			
			String IdXoa = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("IdXoa")));
			if (IdXoa == null)
				IdXoa = "";
			
		

		
			System.out.println("IdXoa = "+ IdXoa);
			if(IdXoa.trim().length() > 0)
				obj.delete(IdXoa);
			
			obj = new Danhmucquyenlist();
			session.setAttribute("obj", obj);
			String nextJSP = "/AHF/pages/Center/DanhMucQuyen.jsp";
			response.sendRedirect(nextJSP);
//			else
//				obj.setMsg("Lỗi dữ liệu");
		}
		else
		if (action.equals("search"))
		{
			session.setAttribute("obj", obj);
			String nextJSP = "/AHF/pages/Center/DanhMucQuyen.jsp";
			response.sendRedirect(nextJSP);
		} else if (action.equals("toExcel"))
		{
			List<Object> dataSearch = new ArrayList<Object>();
			String st = "";
			if (MaQuyen.length() > 0)
			{
				st = st + " and a.pk_seq = ? ";
				dataSearch.add("%" + MaQuyen +"%" );
			}
			if (TenQuyen.length() > 0)
			{
				st = st + " and dbo.ftBoDau(upper(a.ten)) like upper( ? )";
				dataSearch.add("%" +util.replaceAEIOU(TenQuyen) + "%" );
			}
			if (Tungay.length() > 0)
			{
				st = st + " and a.ngaytao >= ? ";
				dataSearch.add( "%"+ Tungay+ "%"  );
			}
			if (Denngay.length() > 0)
			{
				st = st + " and a.ngaytao <= ? ";
				dataSearch.add( "%"+ Denngay + "%");
			}
			String tg = "select a.pk_seq as maquyen,a.ten,b.ten as nguoitao,c.ten as nguoisua,a.ngaytao,a.ngaysua,a.hoatdong from danhmucquyen a,nhanvien b,nhanvien c where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq " + st;
			ToExcel(response, tg,dataSearch);
		} else if (action.equals("new"))
		{
			IDanhmucquyen dmq = new Danhmucquyen("");
			dmq.createRs();
			dmq.setUserId(userId);
			session.setAttribute("obj", dmq);
			String nextJSP = "/AHF/pages/Center/DanhMucQuyenNew.jsp";
			response.sendRedirect(nextJSP);
		} else
		{
			obj = new Danhmucquyenlist();
			session.setAttribute("obj", obj);
			String nextJSP = "/AHF/pages/Center/DanhMucQuyen.jsp";
			response.sendRedirect(nextJSP);
		}
		
	}
	
	private void ToExcel(HttpServletResponse response, String query,List<Object> dataSearch) throws IOException
	{
		OutputStream out = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=DanhMucQuyen.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());
			
			int k = 0;
			int j = 5;
			
			WritableSheet sheet = null;
			
			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 14);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);
			
			sheet = w.createSheet("DanhMucQuyen", k);
			sheet.addCell(new Label(0, 1, "Danh mục quyền : ", new WritableCellFormat(cellTitle)));
			
			sheet.addCell(new Label(0, 2, "Ngày tạo: "));
			sheet.addCell(new Label(1, 2, "" + getDateTime()));
			
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
			
			sheet.addCell(new Label(0, 4, "STT", cellFormat));
			sheet.addCell(new Label(1, 4, "MÃ QUYỀN", cellFormatSpecical));
			sheet.addCell(new Label(2, 4, "TÊN QUYỀN", cellFormat));
			sheet.addCell(new Label(3, 4, "NGƯỜI TẠO", cellFormat));
			sheet.addCell(new Label(4, 4, "NGÀY TẠO", cellFormat));
			sheet.addCell(new Label(5, 4, "NGƯỜI SỬA", cellFormat));
			sheet.addCell(new Label(6, 4, "NGÀY SỬA", cellFormat));
			sheet.addCell(new Label(7, 4, "TRẠNG THÁI", cellFormat));
			sheet.setRowView(100, 4);
			
			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 20);
			sheet.setColumnView(2, 30);
			sheet.setColumnView(3, 25);
			sheet.setColumnView(4, 20);
			sheet.setColumnView(5, 20);
			sheet.setColumnView(6, 15);
			sheet.setColumnView(7, 35);
			sheet.setColumnView(8, 15);
			sheet.setColumnView(9, 15);
			sheet.setColumnView(10, 15);
			sheet.setColumnView(11, 15);
			sheet.setColumnView(12, 15);
			sheet.setColumnView(13, 15);
			sheet.setColumnView(14, 60);
			dbutils db = new dbutils();
			
			ResultSet rs = db.getByPreparedStatement(query, dataSearch);
			
			WritableCellFormat cellFormat2 = new WritableCellFormat(new jxl.write.NumberFormat("#,###,###"));
			
			cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			WritableCellFormat cellFormat3 = new WritableCellFormat(new jxl.write.NumberFormat("#,###,###"));
			cellFormat3.setBackground(jxl.format.Colour.YELLOW);
			cellFormat3.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			WritableCellFormat cformat = null;
			Label label;
			Number number;
			int stt = 0;
			while (rs.next())
			{
				
				cformat = cellFormat2;
				stt++;
				number = new Number(0, j, stt, cformat);
				sheet.addCell(number);
				label = new Label(1, j, rs.getString("maquyen"), cformat);
				sheet.addCell(label);
				label = new Label(2, j, rs.getString("ten"), cformat);
				sheet.addCell(label);
				label = new Label(3, j, rs.getString("nguoitao"), cformat);
				sheet.addCell(label);
				label = new Label(4, j, rs.getString("NgayTao"), cformat);
				sheet.addCell(label);
				label = new Label(5, j, rs.getString("nguoisua"), cformat);
				sheet.addCell(label);
				label = new Label(6, j, rs.getString("ngaysua"), cformat);
				sheet.addCell(label);
				label = new Label(7, j, rs.getInt("hoatdong") == 0 ? "Ngưng hoạt động" : rs.getInt("hoatdong") == 1 ? "Hoạt động" : "Đã hủy", cformat);
				sheet.addCell(label);
				j++;
			}
			w.write();
			w.close();
			rs.close();
			db.shutDown();
		} catch (Exception e)
		{
			System.out.println("Error Cac Ban : " + e.getMessage());
			e.printStackTrace();
		} finally
		{
			if (out != null)
				out.close();
			
		}
	}
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	
}
