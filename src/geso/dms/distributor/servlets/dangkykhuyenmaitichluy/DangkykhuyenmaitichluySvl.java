package geso.dms.distributor.servlets.dangkykhuyenmaitichluy;

import geso.dms.distributor.beans.dangkykhuyenmaitichluy.IDangkykhuyenmaitichluy;
import geso.dms.distributor.beans.dangkykhuyenmaitichluy.IDangkykhuyenmaitichluyList;
import geso.dms.distributor.beans.dangkykhuyenmaitichluy.imp.Dangkykhuyenmaitichluy;
import geso.dms.distributor.beans.dangkykhuyenmaitichluy.imp.DangkykhuyenmaitichluyList;

import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DangkykhuyenmaitichluySvl extends HttpServlet {


	IDangkykhuyenmaitichluyList obj;
	PrintWriter out; 
	String userId;
	private static final long serialVersionUID = 1L;

	public DangkykhuyenmaitichluySvl() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session
				.getAttribute("util");
		if (!cutil.check(userId, userTen, sum)) {
			response.sendRedirect("/AHF/index.jsp");
		} else {


			PrintWriter out;

			out = response.getWriter();
			Utility util = new Utility();

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);

			out.println(userId);

			if (userId.length() == 0)
				userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

			String id = util.getId(querystring);
			obj = new DangkykhuyenmaitichluyList();
			obj.setUserId(userId);
			obj.init();
			session.setAttribute("obj", obj);
			String nextJSP = "/AHF/pages/Distributor/DangKyKhuyenMaiTichLuy.jsp";
			response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session
				.getAttribute("util");
		if (!cutil.check(userId, userTen, sum)) {
			response.sendRedirect("/AHF/index.jsp");
		} else {

			PrintWriter out;

			out = response.getWriter();
			Utility util = new Utility();
			obj = new DangkykhuyenmaitichluyList();

			obj.setUserId(userId);

			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
			obj.setUserId(userId);

			String diengiai = util.antiSQLInspection(request
					.getParameter("diengiai"));
			if (diengiai == null)
				diengiai = "";
			obj.setDiengiai(diengiai);

			String tungay = util.antiSQLInspection(request
					.getParameter("tungay"));
			if (tungay == null)
				tungay = "";
			obj.setTungay(tungay);
			String denngay = util.antiSQLInspection(request
					.getParameter("denngay"));
			if (denngay == null)
				denngay = "";
			obj.setDenngay(denngay);

			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));

			obj.init();
			System.out.println("Action :" + action);
			if (action.equals("new")) {
				IDangkykhuyenmaitichluy objj = new Dangkykhuyenmaitichluy();		
				objj.setUserId(userId);
				objj.init();
				session.setAttribute("obj", objj);
				String nextJSP = "/AHF/pages/Distributor/DangKyKhuyenMaiTichLuyNew.jsp";
				response.sendRedirect(nextJSP);

			} else {

				session.setAttribute("obj", obj);

				String nextJSP = "/AHF/pages/Distributor/DangKyKhuyenMaiTichLuy.jsp";
				response.sendRedirect(nextJSP);
			}

		}
	}

	private String getSearchQuery(HttpServletRequest request) {
		Utility util = new Utility();
		String ddkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdTen")));
		if (ddkdId == null)
			ddkdId = "";
		//obj.setDdkdId(ddkdId);

		String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
		if (nppId == null)
			nppId = "";
		obj.setNppId(nppId);

		String trangthai = util.antiSQLInspection(request
				.getParameter("trangthai"));
		if (trangthai == null)
			trangthai = "";
		//obj.setTrangthai(trangthai);

		String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
		if (tungay == null)
			tungay = "";
		obj.setTungay(tungay);

		String denngay = util
				.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
		if (denngay == null)
			denngay = "";
		obj.setDenngay(denngay);

		String query = "select a.pk_seq as dhtvId, a.ngaynhap, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua, d.ten as khTen, d.pk_seq as khId,d.smartid, e.pk_seq as ddkdId, e.ten as ddkdTen, f.ten as nppTen, a.VAT";
		query = query
				+ " from donhangtrave a inner join nhanvien b on a.nguoitao = b.pk_seq inner join nhanvien c on a.nguoisua = c.pk_seq inner join khachhang d on a.khachhang_fk = d.pk_seq inner join daidienkinhdoanh e on a.ddkd_fk = e.pk_seq inner join nhaphanphoi f on a.npp_fk = f.pk_seq";
		query = query + " where a.npp_fk = '" + nppId + "'";

		if (ddkdId.length() > 0) {
			query = query + " and e.pk_seq = '" + ddkdId + "'";
		}

		if (trangthai.length() > 0) {
			query = query + " and a.trangthai ='" + trangthai + "'";

		}

		if (tungay.length() > 0) {
			query = query + " and a.ngaynhap >= '" + tungay + "'";

		}

		if (denngay.length() > 0) {
			query = query + " and a.ngaynhap <= '" + denngay + "'";

		}
		query = query + " order by a.pk_seq";
		System.out.println("Truy van: " + query + "\n");
		return query;

	}

}
