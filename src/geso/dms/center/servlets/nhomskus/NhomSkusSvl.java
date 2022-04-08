package geso.dms.center.servlets.nhomskus;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geso.dms.center.beans.nhomskus.INhomSkus;
import geso.dms.center.beans.nhomskus.INhomSkusList;
import geso.dms.center.beans.nhomskus.imp.NhomSkus;
import geso.dms.center.beans.nhomskus.imp.NhomSkusList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

public class NhomSkusSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	static final long serialVersionUID = 1L;
	PrintWriter out;

	public NhomSkusSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		INhomSkusList obj;

		HttpSession session = request.getSession();

		Utility util = new Utility();
		out = response.getWriter();
		obj = new NhomSkusList();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		out.println(userId);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

		String action = util.getAction(querystring);
		out.println(action);

		String nskusId = util.getId(querystring);

		if (action.equals("delete")) {
			Delete(nskusId, userId);
		}

		List<INhomSkus> nskuslist = new ArrayList<INhomSkus>();

		getNskusBeanList(nskuslist, userId);

		// Save data into session
		obj.setSkusList(nskuslist);

		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);

		String nextJSP = "/AHF/pages/Center/NhomSkus.jsp";
		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		INhomSkusList obj = new NhomSkusList();

		PrintWriter out = response.getWriter();

		Utility util = new Utility();

		HttpSession session = request.getSession();
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if (action == null) {
			action = "";
		}
		out.println(action);

		if (action.equals("new")) {
			INhomSkus nskusBean = new NhomSkus();

			nskusBean.UpdateRS();
			// Save Data into session
			session.setAttribute("nskusBean", nskusBean);
			session.setAttribute("userId", userId);

			String nextJSP = "/AHF/pages/Center/NhomSkusNew.jsp";
			response.sendRedirect(nextJSP);

		} else if (action.equals("search")) {

			String search = getSearchQuery(request, obj, userId);

			List<INhomSkus> nskuslist = getNskusListS(search);

			// Saving data into session
			obj.setSkusList(nskuslist);
			obj.setSearch(true);
			session.setAttribute("obj", obj);

			session.setAttribute("userId", userId);

			response.sendRedirect("/AHF/pages/Center/NhomSkus.jsp");
		} else if (action.equals("1")) {
			List<INhomSkus> nskuslist = new ArrayList<INhomSkus>();

			getNskusBeanList(nskuslist, userId);

			// Save data into session
			obj.setSkusList(nskuslist);

			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);

			String nextJSP = "/AHF/pages/Center/NhomSkus.jsp";
			response.sendRedirect(nextJSP);
		}
	}

	private void Delete(String nskusId, String userid) {
		Utility util = new Utility();
		dbutils db = new dbutils();
		try {
			db.getConnection().setAutoCommit(false);
			String command;
			command = "delete from nhomskus_sanpham where nskus_fk ='" + nskusId + "' ";
			command += " and nskus_fk in " + util.quyen_nhomskus(userid);
			if (!db.update(command)) {
				System.out.println("Khong the xoa nhomskus_sanpham: " + command);
				db.getConnection().rollback();
				return;
			}
			
			command = "delete from NHANVIEN_NHOMSKUS where Nhomskus_fk ='" + nskusId + "' ";
			command += " and Nhomskus_fk in " + util.quyen_nhomskus(userid);
			if (!db.update(command)) {
				System.out.println("Khong the xoa nhanvien_nhomskus: " + command);
				db.getConnection().rollback();
				return;
			}
	
			command = "delete from nhomskus where pk_seq ='" + nskusId + "' ";
			if (!db.update(command)) {
				System.out.println("Khong the xoa nhomskus: " + command);
				db.getConnection().rollback();
				return;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e1) {
			System.out.println("Exception: " + e1.getMessage());
		}
	}

	private void getNskusBeanList(List<INhomSkus> nskuslist, String userid) {
		dbutils db = new dbutils();
		Utility util = new Utility();
		String query = "select a.pk_seq, a.ten, a.ma, a.trangthai,"
				+ " a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from nhomskus a, nhanvien b,"
				+ " nhanvien c where a.nguoitao = b.PK_SEQ and " + " a.nguoisua = c.PK_SEQ ";
		query += " and a.pk_seq in " + util.quyen_nhomskus(userid);
		query += " order by pk_seq ";
		System.out.println("1.Khoi tao Nskus: " + query);
		ResultSet rs = db.get(query);
		try {
			String[] param = new String[12];
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

	private List<INhomSkus> getNskusListS(String query) {
		dbutils db = new dbutils();

		ResultSet rs = db.get(query);
		List<INhomSkus> nskuslist = new ArrayList<INhomSkus>();

		INhomSkus nskusBean;
		String[] param = new String[8];
		try {
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
		return nskuslist;
	}

	private String getSearchQuery(ServletRequest request, INhomSkusList obj, String userid) {
		Utility util = new Utility();
		String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
		if (tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
		if (denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
		if (trangthai == null)
			trangthai = "";
		if (trangthai.equals("2"))
			trangthai = "";
		obj.setTrangthai(trangthai);
		String query = "select a.pk_seq, a.ten, a.ma, a.trangthai,"
				+ " a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from nhomskus a, nhanvien b,"
				+ " nhanvien c where a.nguoitao = b.PK_SEQ and " + " a.nguoisua = c.PK_SEQ ";
		query += " and a.pk_seq in " + util.quyen_nhomskus(userid);
		if (tungay.length() > 0) {
			query = query + " and a.ngaytao >= '" + tungay + "'";
		}
		if (denngay.length() > 0) {
			query = query + " and a.ngaytao <= '" + denngay + "'";
		}
		if (trangthai.length() > 0) {
			query = query + " and a.trangthai = '" + trangthai + "'";
		}
		return query;
	}

}
