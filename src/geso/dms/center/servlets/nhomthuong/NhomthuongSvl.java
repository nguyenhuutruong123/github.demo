package geso.dms.center.servlets.nhomthuong;

import geso.dms.center.beans.nhomthuong.INhomthuong;
import geso.dms.center.beans.nhomthuong.INhomthuongList;
import geso.dms.center.beans.nhomthuong.imp.Nhomthuong;
import geso.dms.center.beans.nhomthuong.imp.NhomthuongList;
import geso.dms.center.db.sql.dbutils;
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

public class NhomthuongSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
{
	static final long serialVersionUID = 1L;
	PrintWriter out;
	HttpServletRequest request;
	HttpServletResponse response;
	INhomthuongList obj;
	dbutils db;

	public NhomthuongSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		this.request = request;
		this.response = response;
		this.db = new dbutils();

		response.setContentType("text/html");

		HttpSession session = request.getSession();

		Utility util = new Utility();
		out = response.getWriter();
		obj = new NhomthuongList();
		
		String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		if (view == null)
			view = "";
		
		String param = "";
		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "NhomthuongSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}

		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		out.println(userId);
		
		String querystring = request.getQueryString();
		
		String action = util.getAction(querystring);
		out.println(action);

		String nkmId = util.getId(querystring);

		if (action.equals("delete"))
		{
			Delete(nkmId);
		}

		if (action.equals("chot"))
		{
			System.out.println("1.Action la: Chot + Id: " + nkmId);
			Chot(nkmId);
		}
		ResultSet Dsnkm = db.get("select a.tungay,a.denngay ,a.pk_seq, a.ten, a.diengiai, a.trangthai, a.type, a.ngaytao, a.ngaysua, b.ten as nguoitao," + " c.ten as nguoisua from NHOMSANPHAMCHITIEU a, nhanvien b, nhanvien c"
				+ " where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ order by a.ngaytao desc  ");
		obj.setDsnkm(Dsnkm);
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);

		String nextJSP = "/AHF/pages/Center/NhomThuong.jsp";
		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		Utility Ult = new Utility();
		HttpSession session = request.getSession();
		
		String view = Ult.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		if (view == null)
			view = "";
		
		String param = "";
		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "NhomthuongSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}

		String userId = Ult.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

		this.obj = new NhomthuongList();
		this.db = new dbutils();

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if (action == null)
		{
			action = "";
		}
		out.println(action);
		String diengiai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		obj.setDiengiai(diengiai);
		String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
		if (tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
		if (denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null)
			trangthai = "";

		if (trangthai.equals("2"))
			trangthai = "";
		obj.setTrangthai(trangthai);
		String query = "select  a.tungay,a.denngay,a.pk_seq, a.ten, a.type, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from NHOMSANPHAMCHITIEU a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ and a.type = '4' ";
		geso.dms.distributor.util.Utility util = new geso.dms.distributor.util.Utility();
		if (diengiai.length() > 0)
		{
			query = query + " and upper(dbo.ftBoDau(a.diengiai)) like upper(N'%" + util.replaceAEIOU(diengiai) + "%')";

		}

		if (tungay.length() > 0)
		{
			query = query + " and a.ngaytao >= '" + tungay + "'";

		}

		if (denngay.length() > 0)
		{
			query = query + " and a.ngaytao <= '" + denngay + "'";

		}

		if (trangthai.length() > 0)
		{
			query = query + " and a.trangthai = '" + trangthai + "'";

		}
		// query = query
		// +" and a.pk_seq in (select nsp_fk from NHOMSANPHAMCHITIEU_SANPHAM where sp_fk in "+
		// Ult.quyen_sanpham(userId) +")";
		System.out.println(query);
		// Perform searching. Each Nhomthuong is saved into Nhomthuong
		if (action.equals("new"))
		{
			// Empty Bean for distributor
			INhomthuong nkmBean = (INhomthuong) new Nhomthuong();

			nkmBean.UpdateRS();
			// Save Data into session
			session.setAttribute("nkmBean", nkmBean);
			session.setAttribute("userId", userId);

			String nextJSP = "/AHF/pages/Center/NhomThuongNew.jsp";
			response.sendRedirect(nextJSP);

		} else if (action.equals("search"))
		{
			List<Object> dataSearch = new ArrayList<Object>();
			
			query = "select  a.tungay,a.denngay,a.pk_seq, a.ten, a.type, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from NHOMSANPHAMCHITIEU a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ and a.type = '4' ";
			if (diengiai.length() > 0)
			{
				query = query + " and upper(dbo.ftBoDau(a.diengiai)) like upper(?)";
				dataSearch.add("%" + util.replaceAEIOU(diengiai) + "%");
			}

			if (tungay.length() > 0)
			{
				query = query + " and a.ngaytao >= ?";
				dataSearch.add(tungay);
			}

			if (denngay.length() > 0)
			{
				query = query + " and a.ngaytao <= ?";
				dataSearch.add(denngay);
			}

			if (trangthai.length() > 0)
			{
				query = query + " and a.trangthai = ?";
				dataSearch.add(trangthai);
			}
			
			ResultSet Dsnkm = db.getByPreparedStatement(query, dataSearch);
			obj.setDsnkm(Dsnkm);
			session.setAttribute("obj", obj);

			session.setAttribute("userId", userId);

			response.sendRedirect("/AHF/pages/Center/NhomThuong.jsp");
		}
		else if (action.equals("excel"))
		{
			ToExcel(response, query);
		} 
		else if(action.equals("refresh"))
		{
			query = "select  a.tungay,a.denngay,a.pk_seq, a.ten, a.type, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from NHOMSANPHAMCHITIEU a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ and a.type = '4' ";
			ResultSet Dsnkm = db.get(query);
			obj = new NhomthuongList();
			obj.setDsnkm(Dsnkm);
			session.setAttribute("obj", obj);

			session.setAttribute("userId", userId);

			response.sendRedirect("/AHF/pages/Center/NhomThuong.jsp");
		}
		else
		{
			obj = new NhomthuongList();

			ResultSet Dsnkm = db
					.get("select a.pk_seq, a.type, a.ten, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from NHOMSANPHAMCHITIEU a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ and a.type='1' and a.pk_seq in (select nsp_fk from NHOMSANPHAMCHITIEU_SANPHAM where sp_fk in "
							+ Ult.quyen_sanpham(userId) + ")");
			obj.setDsnkm(Dsnkm);
			session.setAttribute("obj", obj);

			session.setAttribute("userId", userId);

			response.sendRedirect("/AHF/pages/Center/NhomThuong.jsp");
		}
	}

	private void Delete(String nkmId)
	{
		String query;
		String command;
		command = "delete from NHOMSANPHAMCHITIEU_sanpham where nsp_fk ='" + nkmId + "'";
		db.update(command);

		command = "delete from NHOMSANPHAMCHITIEU where pk_seq ='" + nkmId + "'";
		db.update(command);

	}

	private void Chot(String nkmId)
	{
		String command = "update NHOMSANPHAMCHITIEU set trangthai = '1' where pk_seq ='" + nkmId + "'";
		db.update(command);
	}

	private void ToExcel(HttpServletResponse response, String query) throws IOException
	{
		OutputStream out = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=NhomSKU.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			int k = 0;
			int j = 5;

			WritableSheet sheet = null;

			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 14);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);

			sheet = w.createSheet("NhomSKU", k);
			sheet.addCell(new Label(0, 1, "NHÓM SKU : ", new WritableCellFormat(cellTitle)));

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
			sheet.addCell(new Label(1, 4, "MÃ NHÓM", cellFormatSpecical));
			sheet.addCell(new Label(2, 4, "TÊN NHÓM", cellFormat));
			sheet.addCell(new Label(3, 4, "DIỄN GIẢI", cellFormat));
			sheet.addCell(new Label(4, 4, "LOẠI", cellFormat));
			sheet.addCell(new Label(5, 4, "TRẠNG THÁI ", cellFormat));
			sheet.addCell(new Label(6, 4, "NGÀY TẠO", cellFormat));
			sheet.addCell(new Label(7, 4, "NGƯỜI TẠO", cellFormat));
			sheet.addCell(new Label(8, 4, "NGÀY SỬA", cellFormat));
			sheet.addCell(new Label(9, 4, "NGƯỜI SỬA", cellFormat));

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

			ResultSet rs = db.get(query);

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
				String type = rs.getString("type");
				if (type == null)
					type = "";
				else if (type.equals("4"))
					type = "SKU In";
				else if (type.equals("6"))
					type = "SKU Out";

				cformat = cellFormat2;
				stt++;
				number = new Number(0, j, stt, cformat);
				sheet.addCell(number);
				label = new Label(1, j, rs.getString("pk_seq"), cformat);
				sheet.addCell(label);
				label = new Label(2, j, rs.getString("ten"), cformat);
				sheet.addCell(label);
				label = new Label(3, j, rs.getString("diengiai"), cformat);
				sheet.addCell(label);
				label = new Label(4, j, type, cformat);
				sheet.addCell(label);

				label = new Label(5, j, rs.getInt("trangthai") == 0 ? "Chờ xử lý" : rs.getInt("trangthai") == 1 ? "Đã chốt" : "Đã hủy", cformat);
				sheet.addCell(label);

				label = new Label(6, j, rs.getString("ngaytao"), cformat);
				sheet.addCell(label);
				label = new Label(7, j, rs.getString("NguoiTao"), cformat);
				sheet.addCell(label);
				label = new Label(8, j, rs.getString("NgaySua"), cformat);
				sheet.addCell(label);
				label = new Label(9, j, rs.getString("NguoiSua"), cformat);
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
