package geso.dms.center.servlets.tieuchithuong;

import geso.dms.center.beans.tieuchithuong.ITieuchithuong;
import geso.dms.center.beans.tieuchithuong.ITieuchithuongList;
import geso.dms.center.beans.tieuchithuong.imp.Tieuchithuong;
import geso.dms.center.beans.tieuchithuong.imp.TieuchithuongList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TieuChiThuongUpdateSvl extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public TieuChiThuongUpdateSvl() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		Utility util = new Utility();

		// --- check user
		// Utility util = new Utility();
		String view = Utility.antiSQLInspection(geso.dms.center.util.Utility
				.antiSQLInspection(request.getParameter("view")));
		if (view == null) {
			view = "";
		}
		String param = "";
		if (view.trim().length() > 0)
			param = "&view=" + view;
		if (Utility.CheckSessionUser(session, request, response)) {
			Utility.RedireactLogin(session, request, response);
		}
		if (Utility.CheckRuleUser(session, request, response,
				"TieuChiThuongSvl", param, Utility.XEM)) {
			Utility.RedireactLogin(session, request, response);
		}
		// --- check user

		ITieuchithuong tctBean = new Tieuchithuong();
		String querystring = request.getQueryString();
		System.out.println("querystring : " + querystring);

		String userId = Utility.antiSQLInspection(util.getUserId(querystring));
		tctBean.setUserId(userId);

		String tctId = Utility.antiSQLInspection(util.getId(querystring));

		String action = Utility.antiSQLInspection(util.getAction(querystring));
		String loaithuong = Utility
				.antiSQLInspection(geso.dms.center.util.Utility
						.antiSQLInspection(request.getParameter("loaithuong")));
		tctBean.setLoaithuong(loaithuong);
		String hienthi = Utility.antiSQLInspection(geso.dms.center.util.Utility
				.antiSQLInspection(request.getParameter("hienthi")));
		tctBean.setHienthi(hienthi);
		System.out.println("action : " + action);

		if (action.equals("copy")) {
			tctBean.setAction(action);
		}

		if (action.equals("xoatieuchi")) {
			tctBean.setTctId(tctId.split(";")[9]);
			System.out.println("vo day ne : " + tctId.split(";")[9]);

			String loaiTC = tctId.split(";")[1];
			System.out.println("loaiTC : " + loaiTC);

			String tcfk = tctId.split(";")[0];
			System.out.println("tcfk : " + tcfk);

			// Diengiai();Kbh();Dvkd();Thang;Nam();TongThuong();TileDStoithieu()

			String diengiai = tctId.split(";")[2];
			if (diengiai == null)
				diengiai = "";

			String kbhId = tctId.split(";")[3];
			if (kbhId == null)
				kbhId = "";

			String dvkdId = tctId.split(";")[4];
			if (dvkdId == null)
				dvkdId = "";

			String nam = tctId.split(";")[6];
			if (nam == null)
				nam = "";

			String thang = tctId.split(";")[5];
			if (thang == null)
				thang = "";

			String loai = Utility
					.antiSQLInspection(geso.dms.center.util.Utility
							.antiSQLInspection(request.getParameter("loai")));
			if (loai == null)
				loai = "0";
			else
				loai = "1";
			System.out.println("Loai : " + loai);
			tctBean.setLoai(loai);

			String tiledstoithieu = tctId.split(";")[8];
			if (tiledstoithieu == null)
				tiledstoithieu = "";

			String tongthuong = tctId.split(";")[7];
			if (tongthuong == null)
				tongthuong = "";

			/*
			 * String tungay = tctId.split(";")[10]; if(tungay == null) tungay =
			 * "";
			 * 
			 * String denngay = tctId.split(";")[11]; if(denngay == null)
			 * denngay = "";
			 */

			if (loaiTC.length() > 0)
				tctBean.SetLoaiTieuChi(loaiTC);

			if (tcfk.length() > 0)
				tctBean.setTieuchiFK(tcfk);

			if (diengiai.length() > 0)
				tctBean.setDiengiai(diengiai);

			if (kbhId.length() > 0)
				tctBean.setKbh(kbhId);

			if (dvkdId.length() > 0)
				tctBean.setDvkd(dvkdId);

			if (nam.length() > 0)
				tctBean.setNam(nam);

			if (thang.length() > 0)
				tctBean.setThang(thang);

			if (tiledstoithieu.replaceAll(",", "").length() > 0)
				tctBean.setTileDStoithieu(tiledstoithieu.replaceAll(",", ""));

			if (tongthuong.replaceAll(",", "").length() > 0)
				tctBean.setTongThuong(tongthuong.replaceAll(",", ""));

			if (loai.length() > 0)
				tctBean.setLoai(loai);

			tctBean.xoaTC();
		} else {
			tctBean.setTctId(tctId);
		}

		tctBean.init();

		session.setAttribute("tctBean", tctBean);

		session.setAttribute("userId", userId);

		if (action.equals("hienthi")) {
			response.sendRedirect("/AHF/pages/Center/TieuChiThuongDisplay.jsp");
		} else {
			System.out.println("vo day update ");
			response.sendRedirect("/AHF/pages/Center/TieuChiThuongUpdate.jsp");
		}

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		Utility util = new Utility();

		// check user
		// Utility util = new Utility();
		String view = Utility.antiSQLInspection(geso.dms.center.util.Utility
				.antiSQLInspection(request.getParameter("view")));
		if (view == null) {
			view = "";
		}
		String param = "";
		if (view.trim().length() > 0)
			param = "&view=" + view;
		if (Utility.CheckSessionUser(session, request, response)) {
			Utility.RedireactLogin(session, request, response);
		}
		if (Utility.CheckRuleUser(session, request, response,
				"TieuChiThuongSvl", param, Utility.XEM)) {
			Utility.RedireactLogin(session, request, response);
		}
		// --- check user

		String userId = Utility.antiSQLInspection(geso.dms.center.util.Utility
				.antiSQLInspection(request.getParameter("userId")));
		String tctId = Utility.antiSQLInspection(geso.dms.center.util.Utility
				.antiSQLInspection(request.getParameter("id")));
		// String tc =
		// Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tc")));
		String tcfk = Utility.antiSQLInspection(geso.dms.center.util.Utility
				.antiSQLInspection(request.getParameter("tc")));
		String action = Utility.antiSQLInspection(geso.dms.center.util.Utility
				.antiSQLInspection(request.getParameter("action")));

		ITieuchithuong tctBean = new Tieuchithuong();
		String loaithuong = Utility
				.antiSQLInspection(geso.dms.center.util.Utility
						.antiSQLInspection(request.getParameter("loaithuong")));
		tctBean.setLoaithuong(loaithuong);
		tctBean.setUserId(userId);
		tctBean.setTctId(tctId);
		String hienthi = Utility.antiSQLInspection(geso.dms.center.util.Utility
				.antiSQLInspection(request.getParameter("hienthi")));
		tctBean.setHienthi(hienthi);
		String tc = "";
		try {
			dbutils db = new dbutils();
			String query = "select tieuchi from tieuchithuong_chitiet where pk_seq = '"
					+ tcfk + "'";
			System.out.println("Tieuchichitiet: " + query);
			ResultSet rs = db.get(query);
			rs.next();
			tc = rs.getString("tieuchi");
			rs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		tctBean.setTieuchi(tc);
		tctBean.setTieuchiFK(tcfk);

		String loaiTC = Utility.antiSQLInspection(geso.dms.center.util.Utility
				.antiSQLInspection(request.getParameter("loaiTC")));
		System.out.println("loaiTC : " + loaiTC);

		String diengiai = Utility
				.antiSQLInspection(geso.dms.center.util.Utility
						.antiSQLInspection(request.getParameter("diengiai")));
		if (diengiai == null)
			diengiai = "";

		String kbhId = Utility.antiSQLInspection(geso.dms.center.util.Utility
				.antiSQLInspection(request.getParameter("kbhId")));
		if (kbhId == null)
			kbhId = "";

		String dvkdId = Utility.antiSQLInspection(geso.dms.center.util.Utility
				.antiSQLInspection(request.getParameter("dvkdId")));
		if (dvkdId == null)
			dvkdId = "";

		String nam = Utility.antiSQLInspection(geso.dms.center.util.Utility
				.antiSQLInspection(request.getParameter("nam")));
		if (nam == null)
			nam = "";

		String thang = Utility.antiSQLInspection(geso.dms.center.util.Utility
				.antiSQLInspection(request.getParameter("thang")));
		if (thang == null)
			thang = "";

		String loai = Utility.antiSQLInspection(geso.dms.center.util.Utility
				.antiSQLInspection(request.getParameter("loai")));
		if (loai == null)
			loai = "0";
		else
			loai = "1";
		System.out.println("Loai : " + loai);
		tctBean.setLoai(loai);

		String tungay = "";
		String denngay = "";
		if (tc.equals("2") || tc.equals("5") || tc.equals("6") || tc.equals("4")) {
			tungay = Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
			denngay = Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));			
			if (tungay == null) tungay = "";			
			if (denngay == null) denngay = "";
		}

		String min = Utility.antiSQLInspection(geso.dms.center.util.Utility
				.antiSQLInspection(request.getParameter("min")));
		if (min == null)
			min = "";

		String max = Utility.antiSQLInspection(geso.dms.center.util.Utility
				.antiSQLInspection(request.getParameter("max")));
		if (max == null)
			max = "";

		String thuongnsp = Utility
				.antiSQLInspection(geso.dms.center.util.Utility
						.antiSQLInspection(request.getParameter("thuongnsp")));
		if (thuongnsp == null)
			thuongnsp = "";

		String dstoithieudh = Utility
				.antiSQLInspection(geso.dms.center.util.Utility
						.antiSQLInspection(request.getParameter("dstoithieudh")));
		if (dstoithieudh == null)
			dstoithieudh = "";

		String tiledstoithieu = Utility
				.antiSQLInspection(geso.dms.center.util.Utility
						.antiSQLInspection(request.getParameter("tldstoithieu")));
		if (tiledstoithieu == null)
			tiledstoithieu = "";

		String tongthuong = Utility
				.antiSQLInspection(geso.dms.center.util.Utility
						.antiSQLInspection(request.getParameter("tongthuong")));
		if (tongthuong == null)
			tongthuong = "";

		String loaisales = Utility
				.antiSQLInspection(geso.dms.center.util.Utility
						.antiSQLInspection(request.getParameter("loaisales")));
		if (loaisales == null)
			loaisales = "0";
		System.out.println("loaisales : " + loaisales);

		String nhomsp = Utility.antiSQLInspection(geso.dms.center.util.Utility
				.antiSQLInspection(request.getParameter("nhomsp")));
		if (nhomsp == null)
			nhomsp = "";
		System.out.println("nhom sp : " + nhomsp);

		String[] tcct = request.getParameterValues("tcct");
		String[] stt = request.getParameterValues("stt");
		String[] tu = request.getParameterValues("tu");
		String[] den = request.getParameterValues("den");
		String[] thuong = request.getParameterValues("thuong");
		tctBean.setData(tcct, stt, tu, den, thuong);

		String diengiaitc = Utility
				.antiSQLInspection(geso.dms.center.util.Utility
						.antiSQLInspection(request.getParameter("diengiaitc")));
		if (diengiaitc == null)
			diengiaitc = "";
		System.out.println("diengiaitc : " + diengiaitc);
		tctBean.setTCDiengiai(diengiaitc);

		String nhomtc = Utility.antiSQLInspection(geso.dms.center.util.Utility
				.antiSQLInspection(request.getParameter("nhomtc")));
		if (nhomtc == null)
			nhomtc = "";
		System.out.println("nhomtc : " + nhomtc);
		tctBean.setTCNhomId(nhomtc);

		if (action.equals("luulai")) {

			String[] tctctYeuCauId = request
					.getParameterValues("tctctYeuCauId");
			tctBean.setTctctYeuCauIdList(tctctYeuCauId);
			String[] giatri = request.getParameterValues("giatri");
			tctBean.setGiatriTctctList(giatri);

			String[] spDieuKienId = request.getParameterValues("spDieuKienId");
			tctBean.setSpDieuKienList(spDieuKienId);
			String[] soluongSpDieuKien = request
					.getParameterValues("soluongSpDieuKien");
			tctBean.setSoluongSpDieuKienList(soluongSpDieuKien);

			tctBean.SetLoaiTieuChi(loaiTC);
			tctBean.setDiengiai(diengiai);
			tctBean.setKbh(kbhId);
			tctBean.setDvkd(dvkdId);
			tctBean.setNam(nam);
			tctBean.setThang(thang);
			tctBean.setToithieu(min.replaceAll(",", ""));
			tctBean.setToida(max.replaceAll(",", ""));
			tctBean.setThuongnsp(thuongnsp.replaceAll(",", ""));
			tctBean.setDstoithieuDH(dstoithieudh.replaceAll(",", ""));

			tctBean.setTileDStoithieu(tiledstoithieu.replaceAll(",", ""));
			tctBean.setTongThuong(tongthuong.replaceAll(",", ""));

			tctBean.setNhomsp(nhomsp);

			tctBean.setLoaiDS(loaisales);

			tctBean.setTungay(tungay);
			tctBean.setDenngay(denngay);

			tctBean.setData(tcct, stt, tu, den, thuong);

			tctBean.Save();
		}

		tctBean.init();

		if (loaiTC.length() > 0)
			tctBean.SetLoaiTieuChi(loaiTC);

		if (diengiai.length() > 0)
			tctBean.setDiengiai(diengiai);
		if (kbhId.length() > 0)
			tctBean.setKbh(kbhId);
		if (dvkdId.length() > 0)
			tctBean.setDvkd(dvkdId);
		if (nam.length() > 0)
			tctBean.setNam(nam);
		if (thang.length() > 0)
			tctBean.setThang(thang);

		if (min.replaceAll(",", "").length() > 0)
			tctBean.setToithieu(min.replaceAll(",", ""));
		if (max.replaceAll(",", "").length() > 0)
			tctBean.setToida(max.replaceAll(",", ""));
		if (thuongnsp.replaceAll(",", "").length() > 0)
			tctBean.setThuongnsp(thuongnsp.replaceAll(",", ""));

		if (dstoithieudh.replaceAll(",", "").length() > 0)
			tctBean.setDstoithieuDH(dstoithieudh.replaceAll(",", ""));

		if (tiledstoithieu.replaceAll(",", "").length() > 0)
			tctBean.setTileDStoithieu(tiledstoithieu.replaceAll(",", ""));

		if (tongthuong.replaceAll(",", "").length() > 0)
			tctBean.setTongThuong(tongthuong.replaceAll(",", ""));

		if (nhomsp.length() > 0)
			tctBean.setNhomsp(nhomsp);

		if (loai.length() > 0)
			tctBean.setLoai(loai);

		if (loaisales.length() > 0)
			tctBean.setLoaiDS(loaisales);

		if (tungay.length() > 0)
			tctBean.setTungay(tungay);

		if (denngay.length() > 0)
			tctBean.setDenngay(denngay);

		session.setAttribute("tctBean", tctBean);

		session.setAttribute("userId", userId);

		if (hienthi != null && hienthi.length() > 0) {
			response.sendRedirect("/AHF/pages/Center/TieuChiThuongDisplay.jsp");
		} else {
			response.sendRedirect("/AHF/pages/Center/TieuChiThuongUpdate.jsp");
		}

	}
}
