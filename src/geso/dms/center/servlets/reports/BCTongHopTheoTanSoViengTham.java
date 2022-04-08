package geso.dms.center.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

@WebServlet("/BCTongHopTheoTanSoViengTham")
public class BCTongHopTheoTanSoViengTham extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public BCTongHopTheoTanSoViengTham()
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
		String querystring = request.getQueryString();

		Utility util = new Utility();
		obj.setuserId(util.getUserId(querystring));
		obj.setuserTen((String) session.getAttribute("userTen"));
		obj.setnppId(util.getIdNhapp(obj.getuserId()));

		obj.init();
		obj.settype("0");
		session.setAttribute("obj", obj);
		session.setAttribute("userId", obj.getuserId());
		session.setAttribute("userTen", obj.getuserTen());
		String nextJSP = "/AHF/pages/Center/BCTongHopTheoTanSoViengTham.jsp";
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
		OutputStream out = response.getOutputStream();
		Utility util = new Utility();

		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");

		obj.settungay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays"))));
		obj.setdenngay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays"))));

		obj.setuserId(userId != null ? userId : "");
		obj.setuserTen(userTen != null ? userTen : "");
		obj.setkenhId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId") != null ? util.antiSQLInspection(request.getParameter("kenhId")): ""));

		obj.setvungId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId") != null ? util.antiSQLInspection(request.getParameter("vungId"))
				: ""));

		obj.setkhuvucId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")) != null ? util.antiSQLInspection(request
				.getParameter("khuvucId")) : "");

		obj.setgsbhId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gsbhs") != null ? util.antiSQLInspection(request.getParameter("gsbhs"))
				: ""));

		obj.setnppId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId") != null ? util.antiSQLInspection(request.getParameter("nppId")) : ""));

		obj.setdvkdId(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId") != null ? util.antiSQLInspection(request.getParameter("dvkdId"))
				: ""));

		obj.setDdkd(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdId") != null ? util.antiSQLInspection(request.getParameter("ddkdId"))
				: ""));
		String tuthang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tuthang").length() < 2 ? ("0" + request.getParameter("tuthang"))
				: geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tuthang")));

		String toithang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denthang").length() < 2 ? ("0" + request.getParameter("denthang"))
				: geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denthang")));
		obj.setFromMonth(tuthang);

		obj.setToMonth(toithang);
		obj.setToYear(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dennam")));
		obj.setFromYear(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tunam")));

		obj.settype(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("typeid")));
		System.out.println(obj.gettype());
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action") != null ? util.antiSQLInspection(request.getParameter("action"))
				: "");
		String nextJSP = "/AHF/pages/Center/BCTongHopTheoTanSoViengTham.jsp";

		if (action.equals("toExcel"))
		{
			try
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BCTongHopTheoTanSoViengTham.xlsm");
				if (!CreatePivotTable(out, obj))
				{

				}
			} catch (Exception ex)
			{
				obj.setMsg(ex.getMessage());
			}
		} else
		{

		}
		obj.init();
		session.setAttribute("obj", obj);
		response.sendRedirect(nextJSP);
	}

	private boolean CreatePivotTable(OutputStream out, IStockintransit obj) throws Exception
	{
		String chuoi = getServletContext().getInitParameter("path") + "\\BCTongHopTheoTanSoViengTham.xlsm";
		FileInputStream fstream = new FileInputStream(chuoi);

		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateStaticHeader(workbook, obj);

		boolean isFill = CreateStaticData(workbook, obj);

		if (!isFill)
		{
			fstream.close();
			return false;
		} else
		{
			workbook.save(out);
			fstream.close();
			return true;
		}
	}

	private void CreateStaticHeader(Workbook workbook, IStockintransit obj) throws Exception
	{
		try
		{

			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");

			Cells cells = worksheet.getCells();

			cells.setRowHeight(0, 20.0f);
			Cell cell = cells.getCell("A1");
			cell.setValue("BÁO CÁO TỔNG HỢP DOANH SỐ THEO TẦN SỐ");

			cells.setRowHeight(2, 18.0f);
			cell = cells.getCell("A3");
			getCellStyle(workbook, "A3", Color.NAVY, true, 10);
			if (obj.gettype().equals("2"))
			{
				cell.setValue("Từ tháng: " + obj.getFromYear() + "-" + obj.getFromMonth());
			} else
			{
				if (obj.gettype().equals("0"))
					cell.setValue("Từ ngày: " + obj.gettungay());
				else
					cell.setValue("Từ tuần: " + obj.gettungay());
			}

			cells.setRowHeight(3, 18.0f);
			cell = cells.getCell("B3");
			getCellStyle(workbook, "B3", Color.NAVY, true, 9);

			if (obj.gettype().equals("2"))
			{
				cell.setValue("Đến tháng: " + obj.getToYear() + "-" + obj.getToMonth());
			} else
			{
				if (obj.gettype().equals("0"))
					cell.setValue("Đến ngày: " + obj.getdenngay());
				else
					cell.setValue("Đến tuần: " + obj.getdenngay());
			}
			cells.setRowHeight(4, 18.0f);
			cell = cells.getCell("A4");
			getCellStyle(workbook, "A4", Color.NAVY, true, 9);
			cell.setValue("Ngày báo cáo: " + this.getDateTime());

			cells.setRowHeight(5, 18.0f);
			cell = cells.getCell("A5");
			getCellStyle(workbook, "A5", Color.NAVY, true, 9);
			cell.setValue("Được tạo bởi:  " + obj.getuserTen());

			cell = cells.getCell("AA1");
			cell.setValue("KenhBanHang");
			cell = cells.getCell("AB1");
			cell.setValue("DonViKinhDoanh");
			cell = cells.getCell("AC1");
			cell.setValue("ChiNhanh");
			cell = cells.getCell("AD1");
			cell.setValue("KhuVuc");
			cell = cells.getCell("AE1");
			cell.setValue("MaGiamSat");
			cell = cells.getCell("AF1");
			cell.setValue("GiamSat");
			cell = cells.getCell("AG1");
			cell.setValue("MaDaiDienKinhDoanh");
			cell = cells.getCell("AH1");
			cell.setValue("DaiDienKinhDoanh");
			cell = cells.getCell("AI1");
			cell.setValue("MaNhaPhanPhoi");
			cell = cells.getCell("AJ1");
			cell.setValue("NhaPhanPhoi");
			if (obj.gettype().equals("0"))
			{
				cell = cells.getCell("AK1");
				cell.setValue("Ngay");
			} else if (obj.gettype().equals("1"))
			{
				cell = cells.getCell("AK1");
				cell.setValue("Tuan");
			} else
			{
				cell = cells.getCell("AK1");
				cell.setValue("Thang");
			}
			cell = cells.getCell("AL1");
			cell.setValue("TanSo");
			cell = cells.getCell("AM1");
			cell.setValue("DoanhSo");
			cell = cells.getCell("AN1");
			cell.setValue("SanLuong");
		} catch (Exception ex)
		{
			throw new Exception("Bao cao bi loi khi khoi tao");
		}
	}

	private boolean CreateStaticData(Workbook workbook, IStockintransit obj) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();

		ResultSet rs;
		String[] param = new String[11];
		if (obj.gettype().equals("0"))
		{
			param[0] = obj.gettungay().trim().length() == 0 ? null : obj.gettungay();
			param[1] = obj.getdenngay().trim().length() == 0 ? null : obj.getdenngay();
		} else if (obj.gettype().equals("1"))
		{
			param[0] = obj.gettungay().trim().length() == 0 ? null : obj.gettungay();
			param[1] = obj.getdenngay().trim().length() == 0 ? null : obj.getdenngay();
		} else
		{
			String denngay = obj.getToYear() + "-" + obj.getToMonth();
			String tungay = obj.getFromYear() + "-" + obj.getFromMonth();
			param[0] = tungay.trim().length() == 0 ? null : tungay;
			param[1] = denngay.trim().length() == 0 ? null : denngay;
		}
		param[2] = obj.getDdkd().length() == 0 ? null : obj.getDdkd();
		param[3] = obj.getnppId().trim().length() == 0 ? null : obj.getnppId();
		param[4] = obj.getvungId() == "" ? null : obj.getvungId();
		param[5] = obj.getkhuvucId() == "" ? null : obj.getkhuvucId();
		param[6] = obj.getgsbhId() == "" ? null : obj.getgsbhId();
		param[7] = obj.getkenhId().equals("") ? null : obj.getkenhId();
		param[8] = obj.getuserId();
		param[9] = "1";
		param[10] = obj.getdvkdId().equals("") ? null : obj.getdvkdId();
		String pr = "";
		for(int i = 0 ; i < param.length; i ++)
			pr +=param[i]+",";
		System.out.println("Param: "+pr);
		if (obj.gettype().equals("0"))
		{
			rs = db.getRsByPro("getDoanhSoTheoTanSoViengTham", param);
		} else if (obj.gettype().equals("1"))
		{

			rs = db.getRsByPro("getDoanhSoTheoTanSoViengTham_ByWeek", param);
		} else
		{
			rs = db.getRsByPro("getDoanhSoTheoTanSoViengTham_ByMonth", param);
		}
		int i = 2;
		try
		{
			for (int ii = 0; ii < 13; ii++)
			{
				cells.setColumnWidth(ii, 15.0f);
			}
			if (rs != null)
			{
				Cell cell = null;
				while (rs.next())
				{

					String Channel = rs.getString("KenhBanHang");

					String BusinessUnit = rs.getString("DonViKinhDoanh");

					String Region = rs.getString("Vung");

					String Area = rs.getString("KhuVuc");

					String SalesResCode = rs.getString("gsbhMa");

					String SalesResName = rs.getString("gsbhTen");

					String SalesSupCode = rs.getString("ddkdMa");

					String SalesSupName = rs.getString("ddkdTen");

					String DistributorCode = rs.getString("nppMa");

					String Distributor = rs.getString("NPPTEN");

					String date = rs.getString("Date");

					String TanSo = rs.getString("TanSo");

					double DoanhSo = rs.getDouble("DOANHSO");

					double SanLuong = rs.getDouble("SanLuong");

					cell = cells.getCell("AA" + Integer.toString(i));
					cell.setValue(Channel);
					cell = cells.getCell("AB" + Integer.toString(i));
					cell.setValue(BusinessUnit);
					cell = cells.getCell("AC" + Integer.toString(i));
					cell.setValue(Region);
					cell = cells.getCell("AD" + Integer.toString(i));
					cell.setValue(Area);
					cell = cells.getCell("AE" + Integer.toString(i));
					cell.setValue(SalesResCode);
					cell = cells.getCell("AF" + Integer.toString(i));
					cell.setValue(SalesResName);
					cell = cells.getCell("AG" + Integer.toString(i));
					cell.setValue(SalesSupCode);
					cell = cells.getCell("AH" + Integer.toString(i));
					cell.setValue(SalesSupName);
					cell = cells.getCell("AI" + Integer.toString(i));
					cell.setValue(DistributorCode);
					cell = cells.getCell("AJ" + Integer.toString(i));
					cell.setValue(Distributor);
					cell = cells.getCell("AK" + Integer.toString(i));
					cell.setValue(date);
					cell = cells.getCell("AL" + Integer.toString(i));
					cell.setValue(TanSo);
					cell = cells.getCell("AM" + Integer.toString(i));
					cell.setValue(DoanhSo);
					cell = cells.getCell("AN" + Integer.toString(i));
					cell.setValue(SanLuong);
					i++;
				}

				if (rs != null)
				{
					rs.close();
				}

				if (db != null)
				{
					db.shutDown();
				}

				if (i == 2)
				{
					obj.setMsg("Khong co bao cao trong thoi gian nay");
					return false;
				}
				return true;
			} else
			{
				obj.setMsg("Khong co bao cao trong thoi gian nay");
				return false;
			}
		} catch (Exception ex)
		{
			System.out.println("Khong The Tao Duoc Bao Cao :" + ex.toString());
			obj.setMsg("Khong The Tao Duoc Bao Cao :" + ex.toString());
			return false;
		}
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
		cell.setStyle(style);
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
