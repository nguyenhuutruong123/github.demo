package geso.dms.center.servlets.tieuchidanhgia;

import geso.dms.center.beans.tieuchidanhgia.*;
import geso.dms.center.beans.tieuchidanhgia.imp.*;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Sheet;
import jxl.read.biff.BiffException;
import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
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
import com.oreilly.servlet.MultipartRequest;

public class TieuchidanhgiaUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "C:\\upload\\excel\\";

	public TieuchidanhgiaUpdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		ITieuchidanhgia khlBean;
		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

		String id = util.getId(querystring);

		khlBean = new Tieuchidanhgia(id);
		khlBean.setId(id);
		khlBean.setUserId(userId);

		khlBean.init();
		session.setAttribute("tcdgBean", khlBean);

		String nextJSP = "/AHF/pages/Center/TieuChiDanhGiaUpdate.jsp";
		if (querystring.indexOf("display") >= 0)
			nextJSP = "/AHF/pages/Center/TieuChiDanhGiaDisplay.jsp";

		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		ITieuchidanhgia khlBean;
		OutputStream out = response.getOutputStream();
		Utility util = new Utility();
		String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
		String contentType = request.getContentType();
		if (id == null)
		{
			khlBean = new Tieuchidanhgia("");
		} else
		{
			khlBean = new Tieuchidanhgia(id);
		}

		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		khlBean.setUserId(userId);

		String noidung = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		if (noidung == null)
			noidung = "";
		khlBean.setDiengiai(noidung);

		String thang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang")));
		if (thang == null)
			thang = "";
		khlBean.setThang(thang);

		String nam = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nam")));
		if (nam == null)
			nam = "";
		khlBean.setNam(nam);

		String gsbhIds = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gsbhIds")));
		if (gsbhIds == null)
			gsbhIds = "";
		khlBean.setGsbhIds(gsbhIds);

		List<ITieuchiDetail> tcDetailList = new ArrayList<ITieuchiDetail>();

		String[] idtieuchi = request.getParameterValues("idtieuchi");
		String[] matieuchi = request.getParameterValues("matieuchi");
		String[] diengiaitc = request.getParameterValues("diengiaitc");
		String[] trongso = request.getParameterValues("trongso");
		String[] chamlan1 = request.getParameterValues("chamlan1");
		String[] chamlan2 = request.getParameterValues("chamlan2");
		String[] chamlan3 = request.getParameterValues("chamlan3");

		if (matieuchi != null)
		{
			ITieuchiDetail detail = null;
			for (int i = 0; i < matieuchi.length; i++)
			{
				if (matieuchi[i].trim().length() > 0 && diengiaitc[i].trim().length() > 0 && trongso[i].trim().length() > 0)
				{
					detail = new TieuchiDetail();

					if (chamlan1[i] == null)
						chamlan1[i] = "";
					if (chamlan2[i] == null)
						chamlan2[i] = "";
					if (chamlan3[i] == null)
						chamlan3[i] = "";

					detail.setId(idtieuchi[i].trim());
					detail.setMa(matieuchi[i].trim());
					detail.setDiengiai(diengiaitc[i].trim());
					detail.setTrongso(trongso[i].trim());
					detail.setChamlan1(chamlan1[i].trim());
					detail.setChamlan2(chamlan2[i].trim());
					detail.setChamlan3(chamlan3[i].trim());

					tcDetailList.add(detail);
				}
			}

			khlBean.setTieuchiDetail(tcDetailList);
		}

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if (action == null)
			action = "";
		System.out.println("__Action la: " + action);
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			MultipartRequest multi = new MultipartRequest(request, UPLOAD_DIRECTORY, 20000000, "UTF-8");
			Enumeration files = multi.getFileNames();
			String filename = "";
			while (files.hasMoreElements())
			{
				String name = (String) files.nextElement();
				filename = multi.getFilesystemName(name);
				System.out.println("File  " + UPLOAD_DIRECTORY + filename);
			}
			userId = multi.getParameter("userId");
			khlBean.setUserId(userId);
			if (filename != null && filename.length() > 0)
				khlBean = this.readExcel(UPLOAD_DIRECTORY + filename, khlBean);
			khlBean.createRs();
			session.setAttribute("userId", userId);
			session.setAttribute("tcdgBean", khlBean);
			String nextJSP = "/AHF/pages/Center/TieuChiDanhGiaUpdate.jsp";
			response.sendRedirect(nextJSP);
			System.out.print("id" + khlBean.getId());
		} else if (action.equals("save"))
		{
			if (id == null)
			{
				if (!khlBean.createKhl())
				{
					System.out.println("....Khong the luu...");
					khlBean.setUserId(userId);
					khlBean.createRs();

					session.setAttribute("userId", userId);
					session.setAttribute("tcdgBean", khlBean);

					String nextJSP = "/AHF/pages/Center/TieuChiDanhGiaNew.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					ITieuchidanhgiaList obj = new TieuchidanhgiaList();
					obj.setUserId(userId);
					obj.init("");

					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);

					response.sendRedirect("/AHF/pages/Center/TieuChiDanhGia.jsp");
				}
			} else
			{
				if (!(khlBean.updateKhl()))
				{
					khlBean.setUserId(userId);
					khlBean.createRs();

					session.setAttribute("userId", userId);
					session.setAttribute("tcdgBean", khlBean);

					String nextJSP = "/AHF/pages/Center/TieuChiDanhGiaUpdate.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					ITieuchidanhgiaList obj = new TieuchidanhgiaList();
					obj.setUserId(userId);
					obj.init("");

					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);

					response.sendRedirect("/AHF/pages/Center/TieuChiDanhGia.jsp");
				}
			}
		} else if (action.equals("ToExcel"))
		{
			System.out.print("Lay file Excel");
			try
			{
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "attachment; filename=DanhGiaGSBH.xls");
				if (!CreatePivotTable(out, khlBean))
				{
					khlBean.setMsg("Không có dữ liệu trong thời gian này.");
					khlBean.createRs();
					session.setAttribute("userId", userId);
					String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));
					if (trangthai == null)
						trangthai = "0";
					khlBean.setTrangthai(trangthai);
					session.setAttribute("tcdgBean", khlBean);
					String nextJSP;
					if (id == null)
					{
						nextJSP = "/AHF/pages/Center/TieuChiDanhGiaNew.jsp";
					} else
					{
						if (trangthai.equals("0"))
							nextJSP = "/AHF/pages/Center/TieuChiDanhGiaUpdate.jsp";
						else
						{
							nextJSP = "/AHF/pages/Center/TieuChiDanhGiaDisplay.jsp";
						}
					}
					response.sendRedirect(nextJSP);
				}
			} catch (Exception ex)
			{
				khlBean.setMsg(ex.getMessage());
			}
		} else
		{
			khlBean.createRs();
			session.setAttribute("userId", userId);

			String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));
			if (trangthai == null)
				trangthai = "0";
			khlBean.setTrangthai(trangthai);

			session.setAttribute("tcdgBean", khlBean);
			String nextJSP;
			if (id == null)
			{
				nextJSP = "/AHF/pages/Center/TieuChiDanhGiaNew.jsp";
			} else
			{
				if (trangthai.equals("0"))
					nextJSP = "/AHF/pages/Center/TieuChiDanhGiaUpdate.jsp";
				else
				{
					nextJSP = "/AHF/pages/Center/TieuChiDanhGiaDisplay.jsp";
				}
			}
			response.sendRedirect(nextJSP);
		}
	}

	@SuppressWarnings("deprecation")
	private boolean CreatePivotTable(OutputStream out, ITieuchidanhgia obj) throws Exception
	{
		Workbook workbook = new Workbook();
		workbook.setFileFormatType(FileFormatType.EXCEL2003);
		CreateStaticHeader(workbook, obj);
		boolean isFill = false;
		isFill = CreateStaticData(workbook, obj);
		if (!isFill)
		{
			return false;
		} else
		{
			workbook.save(out);
			return true;
		}
	}

	private void CreateStaticHeader(Workbook workbook, ITieuchidanhgia obj) throws Exception
	{
		try
		{
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("TieuChiDanhGia");

			Cells cells = worksheet.getCells();
			Cell cell = cells.getCell("A1");

			Font font = new Font();
			font.setName("Times New Roman");
			font.setColor(Color.RED);// mau chu
			font.setSize(16);// size chu
			font.setBold(true);

			Style style = cell.getStyle();
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			style.setVAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);

			String Content = "Đánh giá giám sát bán hàng";
			cell = cells.getCell("A1");
			cells.merge(0, 0, 0, 5);
			cell.setValue(Content);

			Content = "Tiêu chí tháng  ";
			CreateCell(cells, cell, "A2", Content, Color.NAVY, true, 10);

			Content = obj.getThang();
			CreateCell(cells, cell, "B2", Content, Color.NAVY, true, 10);

			Content = "Năm ";
			CreateCell(cells, cell, "A3", Content, Color.NAVY, true, 10);

			Content = obj.getNam();
			CreateCell(cells, cell, "B3", Content, Color.NAVY, true, 10);

			Content = "Mã tiêu chí  ";
			CreateCell(cells, cell, "A4", Content, Color.NAVY, true, 10);

			Content = obj.getId();
			CreateCell(cells, cell, "B4", Content, Color.NAVY, true, 10);

			Content = "Diễn giải   ";
			CreateCell(cells, cell, "A5", Content, Color.NAVY, true, 10);

			Content = obj.getDiengiai();
			CreateCell(cells, cell, "B5", Content, Color.NAVY, true, 10);

			Content = "GSBH   ";
			cell = cells.getCell("A6");
			cells.merge(5, 0, 8, 2);
			cell.setValue(Content);

		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("Bao cao bi loi khi khoi tao " + ex.getMessage());
		}
	}

	private boolean CreateStaticData(Workbook workbook, ITieuchidanhgia obj) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		int row = 9;
		int col = 2;
		String query = "select  GiamSat_TieuChi.*,ChamDiemGs.* " +
			"from " +
			"(	select gsbh.pk_seq as GsbhId,gsbh.ten as TenGsbh,TieuChi.* " +
			"	from " +
			"	giamsatbanhang gsbh, " +
			"	( " +
			"		select tcdg.pk_seq as TieuChidanhgiaId,tcdg.Thang,tcdg.Nam,tcdg.diengiai as DienGiaiTcDg,tcdg_tc.pk_seq as TieuChiId,tcdg_tc.diengiai as DiengiaiTc,tcdg_tc.ma as MaTc,tcdg_tc.trongso TrongsoTc " +
			"		from  TieuChidanhgia tcdg " +
			"		inner join TieuChidanhgia_TieuChi tcdg_tc on tcdg_tc.TieuChidanhgia_fk=tcdg.pk_seq " +
			"		where tcdg.pk_seq='" +
			obj.getId() +
			"' " +
			"	)as TieuChi " +
			"	where  gsbh.trangthai=1 " +
			")as GiamSat_TieuChi " +
			"	left join TieuChidanhgia_TieuChi_gsbh ChamDiemGs " +
			"	 on ChamDiemGs.TieuChidanhgia_TieuChi_fk=GiamSat_TieuChi.TieuChiId and ChamDiemGs.gsbh_fk=GiamSat_TieuChi.GsbhId " +
			"order by GiamSat_TieuChi.gsbhid,GiamSat_TieuChi.TieuChiId";
		ResultSet rs = db.get(query);
		System.out.println("Query___" + query);
		if (rs != null)
		{
			try
			{
				for (int j = 0; j < 15; j++)
					cells.setColumnWidth(row, 12.0f);
				Cell cell = null;
				String GsbhIdPrev = "";
				String TieuChiIdPrev = "";
				while (rs.next())
				{
					String GsbhId = rs.getString("GsbhId");
					String TenGsbh = rs.getString("TenGsbh");
					String TieuChiId = rs.getString("TieuChiId");
					String DienGiaiTc = rs.getString("DienGiaiTc");
					String MaTc = rs.getString("MaTc");
					String TrongSoTc = rs.getString("TrongSoTc");
					String DiemCham = rs.getString("ChamLan1");

					if (TieuChiIdPrev.equals(TieuChiId))
					{
						System.out.println("Same TcId" + "[TieuChiId]" + TieuChiId + "[TieuChiIdPrev]" + TieuChiIdPrev);
					} else if (!TieuChiId.equals(TieuChiIdPrev))
					{
						System.out.println("Diff TcId" + "[TieuChiId]" + TieuChiId + "[TieuChiIdPrev]" + TieuChiIdPrev);
						TieuChiIdPrev = TieuChiId;
						col++;
						if (GsbhId.equals(GsbhIdPrev) || GsbhIdPrev.equals(""))
						{
							cell = cells.getCell(5, col);
							cell.setValue(TieuChiId);
							cell = cells.getCell(6, col);
							cell.setValue(MaTc);
							cell = cells.getCell(7, col);
							cell.setValue(TrongSoTc);
							cell = cells.getCell(8, col);
							cell.setValue(DienGiaiTc);

							Style style;
							for (int j = 5; j <= 8; j++)
							{
								setBorder(cell, cells, j, j, 0, col);
								cell = cells.getCell(j, col);
								style = cell.getStyle();
								style.setNumber(3);
								cell.setStyle(style);
							}
						}
					}

					if (GsbhId.equals(GsbhIdPrev))
					{
						System.out.println("[Gsid Same]" + "[GsbhId]" + GsbhId + "[GsbhIdPrev]" + GsbhIdPrev +
							"[TieuChiId]" + TieuChiId + "[Row]" + row + "[Col]" + col + "[DiemCham]" + DiemCham);
					} else if (!GsbhIdPrev.equals(GsbhId))
					{

						System.out.println("[Gsid Diff]" + "[GsbhId]" + GsbhId + "[GsbhIdPrev]" + GsbhIdPrev +
							"[TieuChiId]" + TieuChiId + "[Row]" + row + "[Col]" + col + "[DiemCham]" + DiemCham);
						GsbhIdPrev = GsbhId;
						row++;
						col = 3;
						cell = cells.getCell("A" + Integer.toString(row));
						cell.setValue(GsbhId);
						cell = cells.getCell("B" + Integer.toString(row));
						cell.setValue(TenGsbh);
					}
					setBorder(cell, cells, row - 1, row - 1, 0, col);
					cell = cells.getCell(row - 1, col);
					cell.setValue(DiemCham);

					Style style;
					style = cell.getStyle();
					style.setNumber(3);
					cell.setStyle(style);
				}
				if (rs != null)
					rs.close();
				if (db != null)
					db.shutDown();
				if (row == 8)
				{
					throw new Exception("Khong co bao cao trong thoi gian nay...");
				}
			} catch (Exception e)
			{
				e.printStackTrace();
				System.out.println("115.Error: " + e.getMessage());
				return false;
			}
		}
		return true;
	}

	private void CreateCell(Cells cells, Cell cell, String CellPos, String Content, Color Color, Boolean Bold, int Size)
	{
		cell = cells.getCell(CellPos);
		ReportAPI.getCellStyle(cell, Color, Bold, Size, Content);
	}

	private void setBorder(Cell cell, Cells cells, int startRow, int endRow, int startColumn, int endColumn)
	{
		Style style;
		style = cell.getStyle();
		style.setBorderLine(BorderType.TOP, BorderLineType.DASHED);
		style.setBorderLine(BorderType.BOTTOM, BorderLineType.DASHED);
		style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
		style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
		cells.setRangeStyle(startRow, endRow, startColumn, endColumn, style);
	}

	private ITieuchidanhgia readExcel(String filename, ITieuchidanhgia obj)
	{
		File inputWorkbook = new File(filename);
		jxl.Workbook w;
		try
		{
			w = jxl.Workbook.getWorkbook(inputWorkbook);
			jxl.Sheet sheet = w.getSheet(0);
			int sodong = sheet.getRows();
			int socot = sheet.getColumns();
			System.out.println("[SoDong]" + sodong + "[SoCot]" + socot);
			int iGsId = 0;
			int Row_TieuChiId = 5;
			int Index_Col;
			String Thang = getValue(sheet, 1, 1).trim();
			String Nam = getValue(sheet, 1, 2).trim();
			String TieuChiDanhGiaId = getValue(sheet, 1, 3).trim();
			String DienGiai = getValue(sheet, 1, 4).trim();
			obj.setId(TieuChiDanhGiaId);
			obj.setDiengiai(DienGiai);
			obj.setThang(Thang);
			obj.setNam(Nam);
			for (int row = 9; row < sodong; row++)
			{
				List<ITieuchiDetail> tcDetailList = new ArrayList<ITieuchiDetail>();
				String GiamSatId = getValue(sheet, iGsId, row).trim();
				obj.setGsbhIds(GiamSatId);
				String DiemCham = null;
				String TieuChiId = null;
				String TrongSo = null;
				for (Index_Col = 3; Index_Col < socot; Index_Col++)
				{
					DiemCham = getValue(sheet, Index_Col, row).trim().replace(",", "");
					if (DiemCham.equals("") || DiemCham.length() <= 0)
						DiemCham = "0";
					TieuChiId = getValue(sheet, Index_Col, Row_TieuChiId).trim();
					TrongSo = getValue(sheet, Index_Col, Row_TieuChiId + 2).trim().replace(",", "");
					if (TrongSo.equals("") || TrongSo.length() <= 0)
						TrongSo = "0";
				//	System.out.println("TrongSo" + TrongSo);
					ITieuchiDetail detail = null;
					if (TieuChiId.length() > 0)
					{
						detail = new TieuchiDetail();
						detail.setId(TieuChiId);
						detail.setTrongso(TrongSo);
						detail.setChamlan1(DiemCham);
						detail.setChamlan2(DiemCham);
						detail.setChamlan3(DiemCham);
						tcDetailList.add(detail);
					}
					obj.setTieuchiDetail(tcDetailList);
				}
				//System.out.println("[Id]" + TieuChiDanhGiaId + "[GsId]" + GiamSatId + "[DiemCham]" + DiemCham +"[TieuChiId]" + TieuChiId + "[TrongSo]" + TrongSo);
				if (!obj.updateKhl())
				{
					System.out.println(obj.getMsg());
				}
			}
		} catch (BiffException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return obj;
	}

	private String getValue(jxl.Sheet sheet, int column, int row)
	{
		jxl.Cell cell;
		cell = sheet.getCell(column, row);
		return cell.getContents();
	}

	public static void main(String[] arg)
	{
		TieuchidanhgiaUpdateSvl p = new TieuchidanhgiaUpdateSvl();
		ITieuchidanhgia obj = new Tieuchidanhgia("");
		obj.setUserId("100368");

		p.readExcel("C:\\TieuChi.xls", obj);
	}

}