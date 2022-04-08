package geso.dms.distributor.servlets.catalog;

import geso.dms.center.beans.nhomsanpham.INhomsanpham;
import geso.dms.center.beans.nhomsanpham.INhomsanphamList;
import geso.dms.center.beans.nhomsanpham.imp.Nhomsanpham;
import geso.dms.center.beans.nhomsanpham.imp.NhomsanphamList;
import geso.dms.center.beans.thongtinsanpham.IThongtinsanphamList;
import geso.dms.center.beans.thongtinsanpham.imp.ThongtinsanphamList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.catalog.Icatalog;
import geso.dms.distributor.beans.catalog.IcatalogList;
import geso.dms.distributor.beans.catalog.imp.catalog;
import geso.dms.distributor.beans.catalog.imp.catalogList;

import java.io.IOException;
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

import com.oreilly.servlet.MultipartRequest;

public class catalogNewSvl extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {
	static final long serialVersionUID = 1L;

	public catalogNewSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();

		Icatalog ctl = new catalog();
		INhomsanpham nspBean = new Nhomsanpham();

		// Collecting data from NhomsanphamNew.jsp

		Utility util = new Utility();

		String userId = util.antiSQLInspection(geso.dms.center.util.Utility
				.antiSQLInspection(request.getParameter("userId")));
		System.out.println("userId  " + userId);
		String ten = util.antiSQLInspection(geso.dms.center.util.Utility
				.antiSQLInspection(request.getParameter("ten")));
		nspBean.setTen(ten);

		String nspId = util.antiSQLInspection(geso.dms.center.util.Utility
				.antiSQLInspection(request.getParameter("nspId")));
		System.out.println("nspId  " + nspId);

		String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility
				.antiSQLInspection(request.getParameter("diengiai")));
		nspBean.setDiengiai(diengiai);
		System.out.println("___Dien giai la: " + diengiai);

		String thanhvien = util.antiSQLInspection(geso.dms.center.util.Utility
				.antiSQLInspection(request.getParameter("thanhvien")));
		nspBean.setThanhvien(thanhvien);

		String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility
				.antiSQLInspection(request.getParameter("dvkdId")));
		if (dvkdId != null)
			nspBean.setDvkdId(dvkdId);

		String nhId = util.antiSQLInspection(geso.dms.center.util.Utility
				.antiSQLInspection(request.getParameter("nhId")));
		if (nhId != null)
			nspBean.setNhId(nhId);

		String clId = util.antiSQLInspection(geso.dms.center.util.Utility
				.antiSQLInspection(request.getParameter("clId")));
		if (clId != null)
			nspBean.setClId(clId);

		String loainhom = util.antiSQLInspection(geso.dms.center.util.Utility
				.antiSQLInspection(request.getParameter("lnhom")));
		if (loainhom != null)
			nspBean.setLoainhom(loainhom);

		String ngaytao = getDateTime();
		nspBean.setNgaytao(ngaytao);

		String ngaysua = ngaytao;
		nspBean.setNgaysua(ngaysua);

		String nguoitao = userId;
		nspBean.setNguoitao(userId);

		String nguoisua = nguoitao;
		nspBean.setNguoisua(nguoisua);

		String trangthai;
		if (util.antiSQLInspection(geso.dms.center.util.Utility
				.antiSQLInspection(request.getParameter("trangthai"))) != null)
			trangthai = "1";
		else
			trangthai = "0";
		nspBean.setTrangthai(trangthai);

		boolean error = false;
		if (ten != null && ten.trim().length() > 0)
			nspBean.setTen(ten);
		else {
			nspBean.setMessage("Vui long nhap vao nhom san pham");
			error = true;
		}
		System.out.println("error là  "+error);
		System.out.println("tên là  "+ten);
		String[] nhom = request.getParameterValues("nhom");
		nspBean.setNhomsp(nhom);

		String[] sanpham = request.getParameterValues("sanpham");
		nspBean.setSanpham(sanpham);

		String action = util.antiSQLInspection(geso.dms.center.util.Utility
				.antiSQLInspection(request.getParameter("action")));
		// session.setAttribute("userId",
		// util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"))));

		System.out.println("action là   " + action);

		if (action.equals("save") && !error) {
			String ma = util.antiSQLInspection(geso.dms.center.util.Utility
					.antiSQLInspection(request.getParameter("Ma")));
			if (ma != null)
				ctl.setMa(ma);
			String nhanhang = util
					.antiSQLInspection(geso.dms.center.util.Utility
							.antiSQLInspection(request.getParameter("nhanhang")));
			if (nhanhang != null)
				ctl.setNhanhang(nhanhang);
			String tenc = util.antiSQLInspection(geso.dms.center.util.Utility
					.antiSQLInspection(request.getParameter("ten")));
			if (tenc != null)
				ctl.setTen(tenc);
			String chungloai = util
					.antiSQLInspection(geso.dms.center.util.Utility
							.antiSQLInspection(request
									.getParameter("chungloai")));
			if (chungloai != null)
				ctl.setChungloai(chungloai);
			String ghichu = util.antiSQLInspection(geso.dms.center.util.Utility
					.antiSQLInspection(request.getParameter("ghichu")));
			if (ghichu != null)
				ctl.setGhichu(ghichu);
			String file = util.antiSQLInspection(geso.dms.center.util.Utility
					.antiSQLInspection(request.getParameter("filename")));
			if (file != null)
				ctl.setDuongdan(file);
			;

			if (!(ctl.saveNsp())) {

				String nextJSP = "/AHF/pages/Center/catalogNew.jsp";
				response.sendRedirect(nextJSP);
			} else {
				IcatalogList obj = new catalogList();
				obj.init("");
				session.setAttribute("obj", obj);
				ctl.setMa(ma);
				ctl.setNhanhang(nhanhang);
				ctl.setTen(tenc);
				ctl.setChungloai(chungloai);
				ctl.setGhichu(ghichu);
				ctl.setDuongdan(file);
				session.setAttribute("userId", userId);

				response.sendRedirect("/AHF/pages/Center/catalog.jsp");
			}

		}

		/*
		 * if (action.equals("filter") || error){ nspBean.UpdateRS(); // Save
		 * Data into session session.setAttribute("nspBean", nspBean);
		 * session.setAttribute("userId", userId);
		 * 
		 * String nextJSP = "/AHF/pages/Center/NhomSanPhamNew.jsp";
		 * response.sendRedirect(nextJSP); }else{ // userId is saved into
		 * session session.setAttribute("userId", nguoitao); //if there is any
		 * error when saving a Brand? if (!nspBean.saveNewNsp()){
		 * session.setAttribute("nspBean", nspBean);
		 * session.setAttribute("userId", userId); String nextJSP =
		 * "/AHF/pages/Center/NhomSanPhamNew.jsp";
		 * response.sendRedirect(nextJSP); } else{ INhomsanphamList obj = new
		 * NhomsanphamList(); List<INhomsanpham> nsplist = new
		 * ArrayList<INhomsanpham>();
		 * 
		 * getNspBeanList("0", nsplist);
		 * 
		 * // Save data into session obj.setNspList(nsplist);
		 * 
		 * session.setAttribute("obj", obj); session.setAttribute("userId",
		 * userId);
		 * 
		 * String nextJSP = "/AHF/pages/Center/catalog.jsp";
		 * response.sendRedirect(nextJSP);
		 * 
		 * 
		 * } }
		 */

	}

	private void getNspBeanList(String parent, List<INhomsanpham> nsplist) {

		dbutils db = new dbutils();

		String query = "select a.nsp_parent_fk as parent,a.loainhom, a.pk_seq, a.ten, a.diengiai, a.loaithanhvien, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from nhomsanpham a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ and a.type='0' and a.nsp_parent_fk = '"
				+ parent + "' order by pk_seq";
		ResultSet rs = db.get(query);
		try {
			String[] param = new String[11];
			INhomsanpham nspBean;
			while (rs.next()) {
				param[0] = rs.getString("pk_seq");
				param[1] = rs.getString("ten");
				param[2] = rs.getString("diengiai");
				param[3] = rs.getString("loaithanhvien");
				param[4] = rs.getString("trangthai");
				param[5] = rs.getString("ngaytao");
				param[6] = rs.getString("ngaysua");
				param[7] = rs.getString("nguoitao");
				param[8] = rs.getString("nguoisua");
				param[9] = rs.getString("parent");
				param[10] = rs.getString("loainhom");
				nspBean = new Nhomsanpham(param, false);
				nsplist.add(nspBean);
				getNspBeanList(param[0], nsplist);
			}
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
		}
	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

}