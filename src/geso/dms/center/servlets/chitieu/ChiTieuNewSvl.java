package geso.dms.center.servlets.chitieu;

import geso.dms.center.beans.chitieu.IChiTieu;
import geso.dms.center.beans.chitieu.imp.ChiTieu;
import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.Label;

import com.oreilly.servlet.MultipartRequest;

public class ChiTieuNewSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ChiTieuNewSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String querystring = request.getQueryString();
		Utility util = new Utility();
		String userId = util.getUserId(querystring);
		if (userId.length() == 0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		String id = util.getId(querystring);
		String loaict = "";
		loaict = (String) session.getAttribute("loaichitieu");
		if (loaict == null)
		{
			loaict = "";
			try
			{
				String tmp;

				if (querystring != null)
				{
					if (querystring.contains("&"))
					{
						tmp = querystring.split("&")[2];
					} else
					{
						tmp = querystring;
					}
					loaict = tmp.split("=")[1];
				}
			} catch (Exception er)
			{

			}
		}
		IChiTieu obj = new ChiTieu(id, "1");
		obj.setUserId(userId);
		session.setAttribute("loaichitieu", loaict);
		obj.CreateRs();
		session.setAttribute("userId", userId);
		session.setAttribute("obj", obj);
		String action = util.getAction(querystring);
		session.setAttribute("check", "0");
		if (action.equals("update"))
		{
			String nextJSP = "/AHF/pages/Center/ChiTieuUpdate.jsp";// default
			response.sendRedirect(nextJSP);
		} else if (action.equals("display"))
		{
			String nextJSP = "/AHF/pages/Center/ChiTieuDisplay.jsp";// default
			response.sendRedirect(nextJSP);
		} else if (action.equals("excel"))
		{
			XuatFileExcel(response, id);
		} else if (action.equals("excelSR"))
		{
			XuatFileExcelSR(response, id);
		} else
		{

		}

	}

	private void XuatFileExcelSR(HttpServletResponse response, String id) throws IOException
	{

		OutputStream out1 = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=sampleName.xls");
			WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
			WritableSheet sheet = null;
			sheet = w.createSheet("Chỉ Tiêu", 0);//ten sheet

			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 15);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);

			WritableCellFormat celltieude = new WritableCellFormat(cellTitle);
			celltieude.setAlignment(Alignment.CENTRE);




			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 13);
			cellFont.setColour(Colour.BLACK);

			WritableFont cellFontWhite = new WritableFont(WritableFont.TIMES, 13);
			cellFontWhite.setColour(Colour.WHITE);

			WritableCellFormat cellFormat = new WritableCellFormat(cellFontWhite);

			cellFormat.setBackground(jxl.format.Colour.GRAY_80);
			cellFormat.setWrap(true);
			cellFormat.setAlignment(Alignment.CENTRE);
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.WHITE);

			WritableCellFormat cellFormatSpecical = new WritableCellFormat(cellFont);

			cellFormatSpecical.setBackground(jxl.format.Colour.GRAY_80);
			cellFormatSpecical.setWrap(true);
			cellFormatSpecical.setAlignment(Alignment.CENTRE);
			cellFormatSpecical.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormatSpecical.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormatSpecical.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormatSpecical.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.WHITE);
			cellFormatSpecical.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.WHITE);

			WritableCellFormat cellF = new WritableCellFormat(cellFont);
			cellF.setAlignment(Alignment.RIGHT);	

			WritableCellFormat cellFormatTD = new WritableCellFormat(cellFont);

			sheet.addCell(new Label(0, 1, "", cellFormatTD));								
			sheet.mergeCells(0, 1, 2, 1);


			dbutils db = new dbutils();
			String sql = "select thang,nam,dvkd.diengiai,kbh.ten from chitieu_sec  ct inner join donvikinhdoanh dvkd on dvkd.pk_seq=dvkd_fk " + " inner join kenhbanhang kbh on kbh.pk_seq=kenh_fk " + " where ct.pk_seq=" + id;

			ResultSet rs = db.get(sql);
			if (rs.next())
			{

				sheet.addCell(new Label(0, 0, "Chỉ tiêu " + rs.getString("thang") + "-" + rs.getString("nam")  ,cellFormatTD));
				sheet.addCell(new Label(0, 2, "DVKD:" + rs.getString("diengiai"),cellFormatTD));
				sheet.addCell(new Label(0, 3, "KENH:" + rs.getString("ten"),cellFormatTD));

				sheet.addCell(new Label(0, 5, "Mã",cellFormat));
				sheet.addCell(new Label(1, 5, "Tên",cellFormat));
				sheet.addCell(new Label(2, 5, "Chức Vụ",cellFormat));
				sheet.addCell(new Label(3, 5, "SELLS OUT",cellFormat));
				sheet.addCell(new Label(4, 5, " Độ Phủ",cellFormat));
				sheet.addCell(new Label(5, 5, " Số Đơn Hàng",cellFormat));
				sheet.addCell(new Label(6, 5, " Sản Lượng/Đơn Hàng",cellFormat));
				sheet.addCell(new Label(7, 5, "% Khách Hàng Mua Hàng",cellFormat));
				sheet.addCell(new Label(8, 5, "Số Khách Hàng Mới",cellFormat));
				sql = "select distinct nhomsp_fk,(select DIENGIAI from NHOMSANPHAM where PK_SEQ=nhomsp_fk) as TenNhom from CHITIEUSEC_NHAPP_NHOMSP where chitieusec_fk=" + id;
				rs = db.get(sql);
				String chuoingoac = "";
				String[] chuoi = new String[20];
				int i = 0;
				while (rs.next())
				{
					sheet.addCell(new Label(9 + i, 4, rs.getString("TenNhom"),cellFormat));
					sheet.addCell(new Label(9 + i, 5, rs.getString("nhomsp_fk"),cellFormat));
					chuoi[i] = rs.getString("nhomsp_fk");
					if (chuoingoac.equals(""))
					{
						chuoingoac = "[" + rs.getString("nhomsp_fk") + "]";

					} else
					{
						chuoingoac = chuoingoac + ",[" + rs.getString("nhomsp_fk") + "]";

					}
					i++;

				}
				rs.close();


				sheet.setRowView(100, 4);

				sheet.setColumnView(0, 10);
				sheet.setColumnView(1, 50);
				sheet.setColumnView(2, 15);
				sheet.setColumnView(3, 15);
				sheet.setColumnView(4, 15);
				sheet.setColumnView(5, 20);
				sheet.setColumnView(6, 20);
				sheet.setColumnView(7, 20);

				WritableCellFormat cellFormat2 = new WritableCellFormat(cellFont);

				cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

				WritableCellFormat cellFormat3 = new WritableCellFormat(cellFont);
				cellFormat3.setBackground(jxl.format.Colour.YELLOW);
				cellFormat3.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat3.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat3.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat3.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

				WritableCellFormat cformat = new WritableCellFormat(cellFont);

				WritableCellFormat cformat3 = new WritableCellFormat(cellFont);
				cformat3.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				cformat3.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				cformat3.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				cformat3.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

				WritableCellFormat cformat1 = new WritableCellFormat(cellFont);
				cformat1.setAlignment(Alignment.RIGHT);
				cformat1.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				cformat1.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				cformat1.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				cformat1.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

				NumberFormat dp3 = new NumberFormat("#,###,###,##");
				WritableCellFormat inFormat = new WritableCellFormat(dp3);
				inFormat.setFont(cellFont);

				inFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				inFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				inFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				inFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

				Label label;
				Number number;

				sql = " select * from  ( " + " select  ct.kenh_fk,ct.dvkd_fk,ct.thang,ct.nam,ctnpp.sodonhang ,ctnpp.sku,ctnpp.dophu, ctnpp.chitieu as sellsout , "
						+ " ctnhom.nhomsanpham_fk,ctnhom.chitieu,  ddkd.pk_seq as nppid ,ddkd.ten as nppten,Isnull(ctnpp.SanLuongTrenDh,0) as  SanLuongTrenDh,ctnpp.SoKhachHang_MuaHang,ctnpp.SoKhachHang_PhatSinh " + " from chitieunpp ct  inner join chitieunpp_ddkd ctnpp "
						+ " on ct.pk_seq=ctnpp.chitieunpp_fk  inner join CHITIEUNPP_DDKD_NHOMSP ctnhom on ctnpp.chitieunpp_fk=ctnhom.chitieunpp_fk "
						+ " and ctnpp.ddkd_fk=ctnhom.ddkd_fk  inner join daidienkinhdoanh ddkd on ddkd.pk_seq=ctnpp.ddkd_fk  "
						+ " inner join chitieu_sec a on a.dvkd_fk=ct.dvkd_fk and ct.thang=a.thang and ct.nam=a.nam and ct.kenh_fk=a.kenh_fk " + " where ct.trangthai<>2 and a.pk_seq= " + id
						+ " ) p  pivot  (  sum(chitieu)  for nhomsanpham_fk in (" + chuoingoac + ") " + " ) as chitieu ";

				System.out.println(sql);

				ResultSet rs1 = db.get(sql);

				int j = 6;
				if (rs1 != null)
					while (rs1.next())
					{

						label = new Label(0, j, rs1.getString("nppid"),cformat3);
						sheet.addCell(label);

						label = new Label(1, j, rs1.getString("nppten"),cformat3);
						sheet.addCell(label);
						label = new Label(2, j, "SR",cformat3);
						sheet.addCell(label);

						number = new Number(3, j, rs1.getDouble("sellsout"),inFormat);
						sheet.addCell(number);

						number = new Number(4, j, rs1.getDouble("dophu"),inFormat);
						sheet.addCell(number);

						number = new Number(5, j, rs1.getDouble("sodonhang"),inFormat);
						sheet.addCell(number);

						number = new Number(6, j, rs1.getDouble("SanLuongTrenDh"),inFormat);
						sheet.addCell(number);


						number = new Number(7, j, rs1.getDouble("SoKhachHang_MuaHang"),inFormat);
						sheet.addCell(number);

						number = new Number(8, j, rs1.getDouble("SoKhachHang_PhatSinh"),inFormat);
						sheet.addCell(number);

						for (int k = 0; k < i; k++)
						{
							number = new Number(9 + k, j, rs1.getDouble(chuoi[k]) ,inFormat );
							sheet.addCell(number);
						}

						j++;
					}

				w.write();
				w.close();
			}
		} catch (Exception e)
		{
			System.out.println("Error Cac Ban : " + e.toString());
		} finally
		{
			if (out1 != null)
				out1.close();
		}
	}

	private void XuatFileExcel(HttpServletResponse response, String id) throws IOException
	{
		OutputStream out1 = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=ChiTieuNPP.xls");
			WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
			WritableSheet sheet = null;
			sheet = w.createSheet("Chỉ Tiêu", 0);//ten sheet

			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 15);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);

			WritableCellFormat celltieude = new WritableCellFormat(cellTitle);
			celltieude.setAlignment(Alignment.CENTRE);


			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 13);
			cellFont.setColour(Colour.BLACK);

			WritableFont cellFontWhite = new WritableFont(WritableFont.TIMES, 13);
			cellFontWhite.setColour(Colour.WHITE);

			WritableCellFormat cellFormat = new WritableCellFormat(cellFontWhite);

			cellFormat.setBackground(jxl.format.Colour.GRAY_80);
			cellFormat.setWrap(true);
			cellFormat.setAlignment(Alignment.CENTRE);
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.WHITE);

			WritableCellFormat cellFormatSpecical = new WritableCellFormat(cellFont);

			cellFormatSpecical.setBackground(jxl.format.Colour.GRAY_80);
			cellFormatSpecical.setWrap(true);
			cellFormatSpecical.setAlignment(Alignment.CENTRE);
			cellFormatSpecical.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormatSpecical.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormatSpecical.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormatSpecical.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.WHITE);
			cellFormatSpecical.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.WHITE);

			WritableCellFormat cellF = new WritableCellFormat(cellFont);
			cellF.setAlignment(Alignment.RIGHT);	

			WritableCellFormat cellFormatTD = new WritableCellFormat(cellFont);

			sheet.addCell(new Label(0, 1, "", cellFormatTD));								
			sheet.mergeCells(0, 1, 2, 1);


			dbutils db = new dbutils();
			String sql = "select thang,nam,dvkd.diengiai,kbh.ten from chitieu_sec  ct inner join donvikinhdoanh dvkd on dvkd.pk_seq=dvkd_fk " + " inner join kenhbanhang kbh on kbh.pk_seq=kenh_fk " + " where ct.pk_seq=" + id;

			ResultSet rs = db.get(sql);
			if (rs.next())
			{

				sheet.addCell(new Label(0, 0, "Chỉ tiêu " + rs.getString("thang") + "-" + rs.getString("nam")  ,cellFormatTD));
				sheet.addCell(new Label(0, 2, "DVKD:" + rs.getString("diengiai"),cellFormatTD));
				sheet.addCell(new Label(0, 3, "KENH:" + rs.getString("ten"),cellFormatTD));

				sheet.addCell(new Label(0, 5, "Mã",cellFormat));
				sheet.addCell(new Label(1, 5, "Tên",cellFormat));
				sheet.addCell(new Label(2, 5, "Chức Vụ",cellFormat));
				sheet.addCell(new Label(3, 5, "SELLS OUT",cellFormat));
				sheet.addCell(new Label(4, 5, " Độ Phủ",cellFormat));
				sheet.addCell(new Label(5, 5, " Số Đơn Hàng",cellFormat));
				sheet.addCell(new Label(6, 5, " Sản Lượng/Đơn Hàng",cellFormat));
				sheet.addCell(new Label(7, 5, "% Khách Hàng Mua Hàng",cellFormat));
				sheet.addCell(new Label(8, 5, "Số Khách Hàng Mới",cellFormat));
				sheet.addCell(new Label(9, 5, "Tỷ Lệ Giao Hàng",cellFormat));
				sheet.addCell(new Label(10, 5, "Độ Chính Xác Tồn Kho",cellFormat));

				sql = "select distinct nhomsp_fk,(select TEN from NHOMSANPHAM where PK_SEQ=nhomsp_fk) as TenNhom from CHITIEUSEC_NHAPP_NHOMSP where chitieusec_fk=" + id;

				rs = db.get(sql);
				String chuoingoac = "";
				String[] chuoi = new String[20];
				int i = 0;
				while (rs.next())
				{
					sheet.addCell(new Label(11 + i, 4, rs.getString("TenNhom"),cellFormat));
					sheet.addCell(new Label(11 + i, 5, rs.getString("nhomsp_fk"),cellFormat));
					chuoi[i] = rs.getString("nhomsp_fk");

					if (chuoingoac.equals(""))
					{
						chuoingoac = "[" + rs.getString("nhomsp_fk") + "]";

					} else
					{
						chuoingoac = chuoingoac + ",[" + rs.getString("nhomsp_fk") + "]";

					}
					i++;

				}
				rs.close();

				sheet.setRowView(100, 4);

				sheet.setColumnView(0, 10);
				sheet.setColumnView(1, 50);
				sheet.setColumnView(2, 15);
				sheet.setColumnView(3, 15);
				sheet.setColumnView(4, 15);
				sheet.setColumnView(5, 20);
				sheet.setColumnView(6, 20);
				sheet.setColumnView(7, 20);

				WritableCellFormat cellFormat2 = new WritableCellFormat(cellFont);

				cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

				WritableCellFormat cellFormat3 = new WritableCellFormat(cellFont);
				cellFormat3.setBackground(jxl.format.Colour.YELLOW);
				cellFormat3.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat3.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat3.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat3.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

				WritableCellFormat cformat = new WritableCellFormat(cellFont);

				WritableCellFormat cformat3 = new WritableCellFormat(cellFont);
				cformat3.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				cformat3.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				cformat3.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				cformat3.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

				WritableCellFormat cformat1 = new WritableCellFormat(cellFont);
				cformat1.setAlignment(Alignment.RIGHT);
				cformat1.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				cformat1.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				cformat1.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				cformat1.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

				NumberFormat dp3 = new NumberFormat("#,###,###,##");
				WritableCellFormat inFormat = new WritableCellFormat(dp3);
				inFormat.setFont(cellFont);

				inFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				inFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				inFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				inFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

				Label label;
				Number number;


				sql = " select * from " + " ( " + " select  ct.kenh_fk,ct.dvkd_fk,ct.thang,ct.nam,ctnpp.sodonhang,isnull(ctnpp.sanluongtrendh,0) as sanluongtrendh ,ctnpp.sku,ctnpp.dophu, " + "ctnpp.chitieu as sellsout , "
						+ " ctnhom.nhomsp_fk,ctnhom.chitieu, " + " npp.pk_seq as nppid ,npp.ten as nppten,ctnpp.SoKhachHang_MuaHang,ctnpp.SoKhachHang_PhatSinh,ctnpp.GiaoHang,ctnpp.TonKho " + " from chitieu_Sec ct " + " inner join chitieu_nhapp_sec ctnpp on ct.pk_seq=ctnpp.chitieu_sec_fk "
						+ " inner join CHITIEUSEC_NHAPP_NHOMSP ctnhom on ctnpp.chitieu_sec_fk=ctnhom.chitieusec_fk and ctnpp.nhapp_fk=ctnhom.npp_fk " + " inner join nhaphanphoi npp on npp.pk_seq=ctnpp.nhapp_fk "
						+ " where ct.pk_seq= " + id + " ) p " + " pivot " + " ( " + " sum(chitieu) " + " for nhomsp_fk in (" + chuoingoac + ") " + " ) as chitieu";

				System.out.println(sql);

				ResultSet rs1 = db.get(sql);
				int j = 6;
				if (rs1 != null)
					while (rs1.next())
					{

						label = new Label(0, j, rs1.getString("nppid"),cformat3);
						sheet.addCell(label);

						label = new Label(1, j, rs1.getString("nppten"),cformat3);
						sheet.addCell(label);
						label = new Label(2, j, "NPP",cformat3);
						sheet.addCell(label);

						number = new Number(3, j, rs1.getDouble("sellsout"),inFormat);
						sheet.addCell(number);

						number = new Number(4, j, rs1.getDouble("dophu"),inFormat);
						sheet.addCell(number);

						number = new Number(5, j, rs1.getDouble("sodonhang"),inFormat);
						sheet.addCell(number);

						number = new Number(6, j, rs1.getDouble("SanLuongTrenDh"),inFormat);
						sheet.addCell(number);

						number = new Number(7, j, rs1.getDouble("SoKhachHang_MuaHang"),inFormat);
						sheet.addCell(number);

						number = new Number(8, j, rs1.getDouble("SoKhachHang_PhatSinh"),inFormat);
						sheet.addCell(number);


						number = new Number(9, j, rs1.getDouble("GiaoHang"),inFormat);
						sheet.addCell(number);

						number = new Number(10, j, rs1.getDouble("TonKho"),inFormat);
						sheet.addCell(number);

						for (int k = 0; k < i; k++)
						{
							number = new Number(11 + k, j, rs1.getDouble(chuoi[k]),inFormat);
							sheet.addCell(number);
						}

						j++;
					}

				w.write();
				w.close();
			}
		} catch (Exception e)
		{
			System.out.println("Error Cac Ban : " + e.toString());
		} finally
		{
			if (out1 != null)
				out1.close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		dbutils db = new dbutils();
		IChiTieu chitieu = new ChiTieu();

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String contentType = request.getContentType();

		Utility util = new Utility();

		String action = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action") == null ? "" : request.getParameter("action")));

		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			//MultipartRequest multi = new MultipartRequest(request, "C:\\java-tomcat\\data\\", 20000000, "UTF-8");
			MultipartRequest multi = new MultipartRequest(request, getServletContext().getRealPath("\\upload\\"), 20000000, "UTF-8"); 
			String userId = util.antiSQLInspection(multi.getParameter("userId"));
			chitieu.setUserId(userId);
			Enumeration files = multi.getFileNames();
			String filenameu = "";
			while (files.hasMoreElements())
			{
				String name = (String) files.nextElement();
				filenameu = multi.getFilesystemName(name);
				System.out.println("name  " + name);
			}
			String thang =util.antiSQLInspection(multi.getParameter("thang"));
			chitieu.setThang(thang);

			String nam =util.antiSQLInspection(multi.getParameter("nam"));
			chitieu.setNam(nam);


			String quy =util.antiSQLInspection(multi.getParameter("quy"));
			chitieu.setQuy(quy);

			String id = util.antiSQLInspection(multi.getParameter("id"));
			if(id!=null)
				chitieu.setID(Double.parseDouble(id));

		
			String dvkdid = util.antiSQLInspection(multi.getParameter("dvkdid"));
			chitieu.setDVKDID(dvkdid);
			String kbhid = util.antiSQLInspection(multi.getParameter("kbhid"));
			chitieu.setKenhId(kbhid);
			String songaylamviec = util.antiSQLInspection(multi.getParameter("songaylamviec"));
			chitieu.setSoNgayLamViec(songaylamviec);
			String ngayketthuc = util.antiSQLInspection(multi.getParameter("ngayketthuc"));
			chitieu.setNgayKetThuc(ngayketthuc);

			String diengiai = util.antiSQLInspection(multi.getParameter("diengiai"));
			chitieu.setDienGiai(diengiai);

			String loaichitieu = util.antiSQLInspection(multi.getParameter("loaichitieu"));
			chitieu.setLoaiChiTieu(loaichitieu);

			chitieu.setKhuVucID("");
			chitieu.setNguoiSua(userId);
			chitieu.setNguoiTao(userId);
			chitieu.setNgayTao(this.getDateTime());
			chitieu.setNgaySua(this.getDateTime());

			String sql = "delete chitieutmp";
			db.update(sql);

			//String filename = "C:\\java-tomcat\\data\\" + filenameu;
			String filename=    getServletContext().getRealPath("\\upload\\")+ "\\"+ filenameu;
			if (filename.length() > 0)
			{
				File file = new File(filename);
				System.out.println("filename  " + file);
				Workbook workbook;
				int indexRow = 5;
				int j = 11;
				try
				{
					System.out.println(file);
					workbook = Workbook.getWorkbook(file);
					Sheet sheet = workbook.getSheet(0);
					Cell[] cells = sheet.getRow(indexRow);

					String nhomspid = "";
					int dodai = 11;
					while (dodai < cells.length)
					{
						if (cells[dodai].getContents().trim().length() > 0)
						{
							dodai = dodai + 1;
						} else
						{
							break;
						}
					}
					System.out.println("Do Dai :" + dodai);

					while (j < dodai)
					{
						if (j ==11)
						{
							nhomspid = cells[j].getContents();
						} else
						{
							nhomspid = nhomspid + ";" + cells[j].getContents();
						}
						j++;
						System.out.println("nhomSpID : " + nhomspid);
					}
					chitieu.setChuoiNhomSp(nhomspid);
					String chuoi = "";
					for (j = 1; j < dodai - 10; j++)
					{
						chuoi = chuoi + "," + "manhom" + j;
					}
					System.out.println(chuoi);
					chitieu.setKhuVucID(chuoi);

					System.out.println("So Dong : " + sheet.getRows());
					for (int i = indexRow + 1; i < sheet.getRows(); i++)
					{
						cells = sheet.getRow(i);
						if (cells.length > 0)
						{
							if (cells[0].getContents().toString().length() > 0)
							{
								String values = "";
								for (j = 0; j < dodai; j++)
								{
									System.out.println("cell["+j+"] "+cells[j].getContents().toString());
									if (j != 1)
									{
										if (j == 0)
										{
											values = "'" + cells[j].getContents() + "'";
										} else if (j == 2)
										{
											values = values + ",'" + cells[j].getContents() + "'";
										} else
										{
											float sotmp = 0;
											try
											{
												sotmp = Float.parseFloat(cells[j].getContents().toString().replace(",", ""));
											} catch (Exception er)
											{
												er.printStackTrace();
											}
											values = values + ",'" + sotmp + "'";
										}
									}
								}
								sql = " insert into chitieutmp (ma,chucvu,SELLSOUT,dophu,donhang,sanluongtrendh,SoKhachHang_MuaHang,SoKhachHang_PhatSinh,TonKho,GiaoHang" + chuoi + ")values(" + values + ")";
								if (!db.update(sql))
								{
									System.out.println(" Khong In sert Duoc : " + sql);
								}
							}
						}

					}
					if (id==null)
					{

						if (chitieu.SaveChiTieu_Sec())
						{
							session.setAttribute("obj", chitieu);
							String nextJSP = "/AHF/pages/Center/ChiTieu.jsp";
							response.sendRedirect(nextJSP);
						} else
						{
							chitieu.CreateRs();
							String nextJSP = "/AHF/pages/Center/ChiTieuNew.jsp";// default
							session.setAttribute("obj", chitieu);
							response.sendRedirect(nextJSP);
						}
					} else
					{
						if (chitieu.EditChiTieu_Sec())
						{
							session.setAttribute("obj", chitieu);
							String nextJSP = "/AHF/pages/Center/ChiTieu.jsp";
							response.sendRedirect(nextJSP);

						} else
						{
							chitieu.CreateRs();
							String nextJSP = "/AHF/pages/Center/ChiTieuUpdate.jsp";// default
							session.setAttribute("obj", chitieu);
							response.sendRedirect(nextJSP);
						}
					}
				} catch (Exception er)
				{
					chitieu.setMessage("Exception:" + er.toString());
					er.printStackTrace();
				}
			}
		} else
		{
			String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
			chitieu.setUserId(userId);

			String thang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang")));
			chitieu.setThang(thang);

			String nam = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nam")));
			chitieu.setNam(nam);
			
			String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
			if(id!=null)
				chitieu.setID(Double.parseDouble(id));
			
			
			String dvkdid = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdid")));
			chitieu.setDVKDID(dvkdid);

			String kbhid = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhid")));
			chitieu.setKenhId(kbhid);

			String songaylamviec = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("songaylamviec")));
			chitieu.setSoNgayLamViec(songaylamviec);

			String ngayketthuc = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngayketthuc")));
			chitieu.setNgayKetThuc(ngayketthuc);

			String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
			chitieu.setDienGiai(diengiai);

			String loaichitieu = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaichitieu")));
			chitieu.setLoaiChiTieu(loaichitieu);

			userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId") == null ? "" : request.getParameter("userId"));
			chitieu.setUserId(userId);
			chitieu.setKhuVucID("");
			chitieu.setNguoiSua(userId);
			chitieu.setNguoiTao(userId);
			chitieu.setNgayTao(this.getDateTime());
			chitieu.setNgaySua(this.getDateTime());

			String loaict = "1";
			if (action.equals("capnhatForm"))
			{
				if (chitieu.SaveChiTieu_Sec(request))
				{
					chitieu.setListChiTieu("", "1");
					session.setAttribute("obj", chitieu);
					String nextJSP = "/AHF/pages/Center/ChiTieu.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					String nextJSP = "/AHF/pages/Center/ChiTieuUpdate.jsp";// default
					chitieu.CreateRs();
					chitieu = new ChiTieu(id, loaict);
					session.setAttribute("obj", chitieu);
					response.sendRedirect(nextJSP);
				}
			} 
			else
			{
				chitieu = new ChiTieu(id, loaict);			
				session.setAttribute("obj", chitieu);
				String nextJSP = "/AHF/pages/Center/ChiTieuDisplay.jsp";// default
				response.sendRedirect(nextJSP);
			}
		}
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
