package geso.dms.center.servlets.nhomchitieu;

import geso.dms.center.beans.nhomchitieu.INhomchitieu;
import geso.dms.center.beans.nhomchitieu.INhomchitieuList;
import geso.dms.center.beans.nhomchitieu.imp.Nhomchitieu;
import geso.dms.center.beans.nhomchitieu.imp.NhomchitieuList;
import geso.dms.center.util.Utility;
import geso.dms.center.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class NhomchitieuUpdateSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
{
	static final long serialVersionUID = 1L;
	private PrintWriter out;

	public NhomchitieuUpdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		HttpSession session = request.getSession();
		this.out = response.getWriter();
		dbutils db = new dbutils();
		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		out.println(userId);

		if (userId.length() == 0)
			userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));

		String id = util.getId(querystring);

		String query = "select a.pk_seq, a.ten, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua,a.TuNgay,a.DenNgay,a.TinhThuong, a.loaidk, a.tongluong "
				+ "from nhomsanpham a, nhanvien b, nhanvien c where a.loainhom = '3' and a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ and a.pk_seq='" + id + "'";

		ResultSet rs = db.get(query);
		INhomchitieu nkmBean = null;
		;
		try
		{
			rs.next();
			String[] param = new String[10];
			param[0] = id;
			param[1] = rs.getString("ten");
			param[2] = rs.getString("diengiai");
			param[3] = rs.getString("trangthai");
			param[4] = rs.getString("ngaytao");
			param[5] = rs.getString("ngaysua");
			param[6] = rs.getString("nguoitao");
			param[7] = rs.getString("nguoisua");

			nkmBean = new Nhomchitieu(param);

			nkmBean.setTuNgay(rs.getString("TuNgay") == null ? "" : rs.getString("TuNgay"));
			nkmBean.setDenNgay(rs.getString("DenNgay") == null ? "" : rs.getString("DenNgay"));
			nkmBean.setTinhThuong(rs.getString("TinhThuong") == null ? "" : rs.getString("TinhThuong"));
			nkmBean.setLoaiDk(rs.getString("loaidk") == null ? "" : rs.getString("loaidk"));
			nkmBean.setTongluong(rs.getString("tongluong") == null ? "" : rs.getString("tongluong"));

			nkmBean.UpdateRS();
			if (rs != null)
			{
				rs.close();
			}
			db.shutDown();
		} catch (Exception e)
		{
			out.println(e.toString());
		}

		session.setAttribute("nkmBean", nkmBean);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Center/NhomChiTieuUpdate.jsp";

		if (querystring.indexOf("display") >= 0)
			nextJSP = "/AHF/pages/Center/NhomChiTieuDisplay.jsp";
		response.sendRedirect(nextJSP);

	}

	private void CreateStaticHeader(Workbook workbook)
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();

		Cell cell = cells.getCell("A1");
		cell.setValue("Danh sách sản phẩm");

		cell = cells.getCell("A3");

		// cell = cells.getCell("A4");
		// cell.setValue("User:  " + UserName);

		// tieu de
		cell = cells.getCell("A3");
		cell.setValue("Ten Nhom");

		cell = cells.getCell("A5");
		cell.setValue("Ma SP");
		cell = cells.getCell("B5");
		cell.setValue("Ten SP");

		worksheet.setName("Danh sách  sản phẩm");
	}

	private void CreateStaticData(Workbook workbook, String nspid)
	{
		String query = "select nsp.ten, nsp.diengiai,sp.ma,sp.ten as tensp from nhomsanpham_sanpham a inner join nhomsanpham nsp on nsp.pk_Seq=nsp_fk " + " inner join sanpham sp on sp.pk_seq=a.sp_fk " + " where nsp.pk_seq="
				+ nspid;

		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();

		dbutils db = new dbutils();
		ResultSet rs = db.get(query);
		System.out.println("Get san pham :" + query);
		int i = 6;
		if (rs != null)
		{
			try
			{
				cells.setColumnWidth(0, 25.0f);
				cells.setColumnWidth(1, 15.0f);
				cells.setColumnWidth(2, 15.0f);

				Cell cell = null;
				while (rs.next())
				{

					cell = cells.getCell("B3");
					cell.setValue(rs.getString("diengiai"));
					cell = cells.getCell("A" + Integer.toString(i));
					cell.setValue(rs.getString("ma"));
					cell = cells.getCell("B" + Integer.toString(i));
					cell.setValue(rs.getString("tensp"));

					i++;
				}
				rs.close();
				db.shutDown();
			} catch (Exception e)
			{
				e.printStackTrace();
				System.out.println("Loi Nek :" + e.toString());
			}
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		java.io.OutputStream out = response.getOutputStream();

		INhomchitieu nkmBean = new Nhomchitieu();

		Utility Ult = new Utility();
		String userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
		String ten = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ten"));
		nkmBean.setTen(ten);

		String id = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nkmId"));
		nkmBean.setId(id);

		String diengiai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai"));
		nkmBean.setDiengiai(diengiai);

		String dvkdId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId"));
		if (!(dvkdId == null))
			nkmBean.setDvkdId(dvkdId);

		String kenhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId"));
		if (kenhId == null)
			kenhId = "";
		nkmBean.setkenhId(kenhId);

		String nhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhId"));
		if (!(nhId == null))
			nkmBean.setNhId(nhId);

		String clId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("clId"));
		if (!(clId == null))
			nkmBean.setClId(clId);

		String ngaytao = getDateTime();
		nkmBean.setNgaytao(ngaytao);

		String ngaysua = ngaytao;
		nkmBean.setNgaysua(ngaysua);

		String nguoitao = userId;
		nkmBean.setNguoitao(userId);

		String nguoisua = nguoitao;
		nkmBean.setNguoisua(nguoisua);

		String trangthai;
		if (geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")) != null)
			trangthai = "1";
		else
			trangthai = "0";
		nkmBean.setTrangthai(trangthai);

		String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
		if (tungay == null)
			tungay = "";
		nkmBean.setTuNgay(tungay);

		String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
		if (denngay == null)
			denngay = "";
		nkmBean.setDenNgay(denngay);

		String tinhthuong = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tinhthuong"));
		if (tinhthuong == null)
			tinhthuong = "0";
		nkmBean.setTinhThuong(tinhthuong);
		
		String loaidk = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaidk"));
		if (loaidk == null)
			loaidk = "0";
		nkmBean.setLoaiDk(loaidk);
		
		String tongluong = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tongluong"));
		if (tongluong == null)
			tongluong = "0";
		nkmBean.setTongluong(tongluong);

		boolean error = false;
		if (ten.trim().length() > 0)
			nkmBean.setTen(ten);
		else
		{
			nkmBean.setMessage("Vui lòng nhập vào nhóm chỉ tiêu");
			error = true;
		}

		String[] sanpham = request.getParameterValues("sanpham");
		nkmBean.setSanpham(sanpham);

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		session.setAttribute("userId", geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		
		Hashtable<String, String> sanpham_soluong = new Hashtable<String, String>();
		String[] soluong = request.getParameterValues("soluong");
		String[] spid = request.getParameterValues("spid");
		if(soluong != null)
		{
			for(int i = 0; i < soluong.length; i++)
			{
				if(soluong[i].trim().length() > 0)
				{
					sanpham_soluong.put(spid[i], soluong[i].trim());
					System.out.println("sanpham_soluong "+spid[i]+", "+sanpham_soluong.get(spid[i]));
				}
			}
		}	

		nkmBean.setSanpham_Soluong(sanpham_soluong);

		if (action.equals("xuatexel"))
		{
			try
			{
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "attachment; filename=DanhSachSanPham.xls");
				Workbook workbook = new Workbook();
				CreateStaticHeader(workbook);
				CreateStaticData(workbook, id);
				// Saving the Excel file
				workbook.save(out);
			} catch (Exception ex)
			{
				nkmBean.setMessage("Khong Xuat Ra Excel Duoc");
			}

			session.setAttribute("nkmBean", nkmBean);
			session.setAttribute("userId", userId);
			String nextJSP = "/AHF/pages/Center/NhomChiTieuUpdate.jsp";
			response.sendRedirect(nextJSP);
		} else if (action.equals("filter") || error)
		{
			nkmBean.UpdateRS();
			session.setAttribute("nkmBean", nkmBean);
			session.setAttribute("userId", userId);
			if (id.length() > 0)
			{
				response.sendRedirect("/AHF/pages/Center/NhomChiTieuUpdate.jsp");
			} else
			{
				response.sendRedirect("/AHF/pages/Center/NhomChiTieuNew.jsp");
			}

		} else
		{

			session.setAttribute("userId", nguoitao);
			if (id.length() > 0)
			{
				if (!nkmBean.updateNkm())
				{
					nkmBean.UpdateRS();
					session.setAttribute("nkmBean", nkmBean);
					session.setAttribute("userId", userId);
					String nextJSP = "/AHF/pages/Center/NhomChiTieuUpdate.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					INhomchitieuList obj = new NhomchitieuList();
					obj.init();
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);
					String nextJSP = "/AHF/pages/Center/NhomChiTieu.jsp";
					response.sendRedirect(nextJSP);
				}
			} else
			{
				if (!nkmBean.saveNewNkm())
				{
					session.setAttribute("nkmBean", nkmBean);
					session.setAttribute("userId", userId);
					String nextJSP = "/AHF/pages/Center/NhomChiTieuNew.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					INhomchitieuList obj = new NhomchitieuList();
					obj.init();
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);
					String nextJSP = "/AHF/pages/Center/NhomChiTieu.jsp";
					response.sendRedirect(nextJSP);
				}
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
