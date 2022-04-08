package geso.dms.center.servlets.nhomskus;

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

import geso.dms.center.beans.nhomskus.INhomSkus;
import geso.dms.center.beans.nhomskus.INhomSkusList;
import geso.dms.center.beans.nhomskus.imp.NhomSkus;
import geso.dms.center.beans.nhomskus.imp.NhomSkusList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

public class NhomSkusUpdateSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	static final long serialVersionUID = 1L;
	private PrintWriter out;

	public NhomSkusUpdateSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();
		this.out = response.getWriter();
		dbutils db = new dbutils();
		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		out.println(userId);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

		String id = util.getId(querystring);

		String query = "select a.pk_seq, a.ten, a.ma, a.trangthai,  a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from nhomskus a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ and a.pk_seq='"
				+ id + "' ";
		query += " and a.pk_seq in " + util.quyen_nhomskus(userId);

		ResultSet rs = db.get(query);
		try {
			rs.next();
			String[] param = new String[12];
			param[0] = id;
			param[1] = rs.getString("ten");
			param[2] = rs.getString("ma");
			param[3] = rs.getString("trangthai");
			param[4] = rs.getString("ngaytao");
			param[5] = rs.getString("ngaysua");
			param[6] = rs.getString("nguoitao");
			param[7] = rs.getString("nguoisua");

			INhomSkus nskusBean = new NhomSkus(param);

			nskusBean.UpdateRS();
			session.setAttribute("nskusBean", nskusBean);
			session.setAttribute("userId", userId);
			String nextJSP = "/AHF/pages/Center/NhomSkusNew.jsp";
			response.sendRedirect(nextJSP);
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			out.println(e.toString());
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		INhomSkus nskusBean = new NhomSkus();

		// Get data from NhacungcapUpdate.jsp

		Utility util = new Utility();

		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

		String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nskusId")));
		nskusBean.setId(id);

		String ma = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ma")));
		if (ma == null)
			ma = "";
		nskusBean.setMa(ma);

		String ten = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ten")));
		if (ten == null)
			ten = "";
		nskusBean.setTen(ten);

		String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
		nskusBean.setTrangthai(trangthai);

		String ngaytao = getDateTime();
		nskusBean.setNgaytao(ngaytao);

		String ngaysua = ngaytao;
		nskusBean.setNgaysua(ngaysua);

		String nguoitao = userId;
		nskusBean.setNguoitao(userId);

		String nguoisua = nguoitao;
		nskusBean.setNguoisua(nguoisua);

		String[] sanpham = request.getParameterValues("sanpham");
		nskusBean.setSanpham(sanpham);

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		session.setAttribute("userId", util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"))));
		out.println(action);

			String nextJSP = "";
			if (id == null || id == "") {
				if (!nskusBean.saveNewNSkus()) {
					nskusBean.UpdateRS();
					session.setAttribute("nskusBean", nskusBean);
					session.setAttribute("userId", userId);
					nextJSP = "/AHF/pages/Center/NhomSkusNew.jsp";
					response.sendRedirect(nextJSP);
				} else {
					INhomSkusList obj = new NhomSkusList();
					List<INhomSkus> nskuslist = new ArrayList<INhomSkus>();

					getNskusBeanList(nskuslist, userId	);

					// Save data into session
					obj.setSkusList(nskuslist);

					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);

					nextJSP = "/AHF/pages/Center/NhomSkus.jsp";
					response.sendRedirect(nextJSP);
				}
			} else {
				if (!nskusBean.updateNSkus()) {
					nskusBean.UpdateRS();
					session.setAttribute("nskusBean", nskusBean);
					session.setAttribute("userId", userId);
					nextJSP = "/AHF/pages/Center/NhomSkusNew.jsp";
					response.sendRedirect(nextJSP);
				} else {
					INhomSkusList obj = new NhomSkusList();
					List<INhomSkus> nskuslist = new ArrayList<INhomSkus>();

					getNskusBeanList(nskuslist, userId);

					// Save data into session
					obj.setSkusList(nskuslist);

					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);

					nextJSP = "/AHF/pages/Center/NhomSkus.jsp";
					response.sendRedirect(nextJSP);
				}
			}
	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	private void getNskusBeanList(List<INhomSkus> nskuslist, String userid) {
		dbutils db = new dbutils();
		Utility util = new Utility();
		String query = "select a.pk_seq, a.ten, a.ma, a.trangthai,"
				+ " a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from nhomskus a, nhanvien b,"
				+ " nhanvien c where a.nguoitao = b.PK_SEQ and " + " a.nguoisua = c.PK_SEQ ";
		query += " and a.pk_seq in " + util.quyen_nhomskus(userid);
		query += " order by pk_seq ";
		ResultSet rs = db.get(query);
		try {
			String[] param = new String[8];
			INhomSkus nskusBean;
			while (rs.next()) {
				param[0] = rs.getString("pk_seq");
				param[1] = rs.getString("ten");
				param[2] = rs.getString("ma");
				param[3] = rs.getString("trangthai");
				param[4] = rs.getString("ngaytao");
				param[5] = rs.getString("ngaysua");
				param[6] = rs.getString("nguoitao");
				param[7] = rs.getString("nguoisua");
				nskusBean = new NhomSkus(param);
				nskuslist.add(nskusBean);
			}
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
		}
	}

}
