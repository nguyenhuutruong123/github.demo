package geso.dms.center.servlets.reports;

import geso.dms.center.beans.bcchart.IBcchart;
import geso.dms.center.beans.bcchart.imp.Bcchart;
import geso.dms.center.beans.kenhbanhang.IKenhbanhangList;
import geso.dms.center.beans.kenhbanhang.imp.KenhbanhangList;

import geso.dms.center.beans.vung.IVungmienList;
import geso.dms.center.beans.vung.imp.VungmienList;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BCChartAllSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PrintWriter out;

	public BCChartAllSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		this.out = response.getWriter();

		HttpSession session = request.getSession();

		Utility util = new Utility();
		out = response.getWriter();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		out.println(userId);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		IBcchart obj = new Bcchart();
		obj.setUserId(userId);

		String view = request.getParameter("view");
		String msg = "";
		obj.setView(view);
		// BÁO CÁO TRONG THÁNG HIỆN TẠI
		// obj.initData(view);

		obj.setMsg(msg);
		session.setAttribute("obj", obj);
		
		String nextJSP = "/AHF/pages/Center/BCChartThucDatChiTieu.jsp";
		if (view.equals("tangtruongdoanhso")) {
			nextJSP = "/AHF/pages/Center/BC_Chart_Theo_Doi_Doanh_So.jsp";
		} else if (view.equals("muc_do_tang_truong_san_pham")) {
			obj.setThang(Utility.getNgayHienTai());
			nextJSP = "/AHF/pages/Center/Bcchartsanpham.jsp";
		} else if (view.equals("tang_truong_doanh_so_theo_ngay")) {
			obj.setThang(Utility.getNgayHienTai());
			nextJSP = "/AHF/pages/Center/Bcchartdoanhsosanpham.jsp";
			// Bcchartdoanhsosanpham.jsp
		}
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		this.out = response.getWriter();

		HttpSession session = request.getSession();

		out = response.getWriter();
		Utility util = new Utility();

		Bcchart obj;
		obj = new Bcchart();
		String view = request.getParameter("view");
		obj.setView(view);

		String userId = util.antiSQLInspection(request.getParameter("userId"));

		obj.setUserId(userId);

		// LẤY PHẠM VI LẬP BÁO CÁO
		String thang = request.getParameter("thang");
		if (thang == null)
			thang = "";
		obj.setThang(thang);

		String spId = request.getParameter("spId");
		if (spId == null)
			spId = "";
		obj.setSpId(spId);
	
		String spVungid = request.getParameter("spVung");
		if(spVungid==null) {
			spVungid="";
		}
		obj.setSpVungId(spVungid);
		
		String spKenhid = request.getParameter("spKenh");
		if(spKenhid == null) {
			spKenhid = "";
		}
		obj.setSpKenhId(spKenhid);

		if (view.equals("tang_truong_doanh_so_theo_ngay")) {
			String tmp = "";
			String[] spIdArr = request.getParameterValues("spId");
			if (spIdArr != null)
				for (int i = 0; i < spIdArr.length; i++) {
					tmp += tmp.trim().length() > 0 ? "," + spIdArr[i] : spIdArr[i];
				}
			spId = tmp;
			obj.setSpId(spId);
		}
		String nam = request.getParameter("nam");
		if (nam == null)
			nam = "";
		obj.setnam(nam);

		String theomuc = request.getParameter("theomuc");
		if (theomuc == null)
			theomuc = "0";
		obj.setTheomuc(theomuc);

		String action = request.getParameter("action") == null ? "" : request.getParameter("action");
		// LẤY BÁO CÁO
		if (action.equals("xem"))
			obj.initData(view);

		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);

		String nextJSP = "/AHF/pages/Center/BCChartThucDatChiTieu.jsp";
		if (view.equals("tangtruongdoanhso")) {
			nextJSP = "/AHF/pages/Center/BC_Chart_Theo_Doi_Doanh_So.jsp";
		} else if (view.equals("muc_do_tang_truong_san_pham")) {
			nextJSP = "/AHF/pages/Center/Bcchartsanpham.jsp";
		} else if (view.equals("tang_truong_doanh_so_theo_ngay")) {
			nextJSP = "/AHF/pages/Center/Bcchartdoanhsosanpham.jsp";
			// Bcchartdoanhsosanpham.jsp
		}
		response.sendRedirect(nextJSP);

	}

}
