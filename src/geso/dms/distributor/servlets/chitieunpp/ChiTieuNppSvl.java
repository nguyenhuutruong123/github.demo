package geso.dms.distributor.servlets.chitieunpp;
import geso.dms.center.beans.chitieu.IChiTieuNhanVienList;
import geso.dms.center.beans.chitieu.imp.ChiTieuNhanVienList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.chitieunpp.IChiTieuNppList;
import geso.dms.distributor.beans.chitieunpp.imp.ChiTieuNhaPP;
import geso.dms.distributor.beans.chitieunpp.imp.ChiTieuNppList;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ChiTieuNppSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public ChiTieuNppSvl() {
		super();

	}
	PrintWriter out;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		
		
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
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

		IChiTieuNppList obj = new ChiTieuNppList();
		obj.setUserId(userId);

		String loaichitieu = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaichitieu"));
		if (loaichitieu == null)
			loaichitieu = "0";
		obj.setType(loaichitieu);

		String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		if (view == null)
			view = "";
		obj.setView(view);

		String action = util.getAction(querystring);
		String ctskuId = util.getId(querystring);

		// System.out.println("___Action: " + action + " -- Id la: " + ctskuId);
		if (action.trim().equals("duyet"))
		{
			dbutils db = new dbutils();
			db.update("update ChiTieuNhanVien set trangthai = '1' where pk_seq = '" + ctskuId + "'");
			db.shutDown();
		}
		obj.init("");
		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Distributor/ChiTieuNPP.jsp";
		
		if(loaichitieu.equals("2"))
		{
			nextJSP = "/AHF/pages/Distributor/ChiTieuNPP.jsp";
		}
		else if(loaichitieu.equals("3"))
		{
			nextJSP = "/AHF/pages/Distributor/ChiTieuNPP.jsp";
		}
		response.sendRedirect(nextJSP);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}
