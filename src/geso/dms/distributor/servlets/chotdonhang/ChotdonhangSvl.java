package geso.dms.distributor.servlets.chotdonhang;

import geso.dms.distributor.beans.donhang.IChotdonhang;
import geso.dms.distributor.beans.donhang.imp.Chotdonhang;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChotdonhangSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ChotdonhangSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/AHF/index.jsp");
		} else
		{
			IChotdonhang obj;

			PrintWriter out;

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			out = response.getWriter();

			Utility util = new Utility();

			String querystring = request.getQueryString();

			userId = util.getUserId(querystring);
			out.println(userId);

			if (userId.length() == 0)
				userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

			obj = new Chotdonhang();

			obj.setRequestObj(request);
			obj.setUserId(userId);

			obj.init();

			session.setAttribute("obj", obj);
			String nextJSP = "/AHF/pages/Distributor/ChotDonHang.jsp";
			response.sendRedirect(nextJSP);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		IChotdonhang obj;
		String userId;
		PrintWriter out;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();

		out = response.getWriter();
		obj = new Chotdonhang();

		obj.setRequestObj(request);
		Utility util = new Utility();
		userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		obj.setUserId(userId);

		String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
		if (nppId == null)
			nppId = "";
		obj.setNppId(nppId);

		String nvbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nvbhTen")));
		if (nvbhId == null)
			nvbhId = "";
		obj.setNvbhId(nvbhId);

		String nvgnId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nvgnTen")));
		if (nvgnId == null)
			nvgnId = "";
		obj.setNvgnId(nvgnId);

		String ngaygiao = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaygiao")));
		if (ngaygiao == null)
			ngaygiao = "";
		obj.setNgaygiao(ngaygiao);

		String[] dhIds = request.getParameterValues("dhIds");
		obj.setDhIds(dhIds);

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));

		if (action.equals("save"))
		{
			if (!obj.chotDonhang())
			{
				obj.init();
				session.setAttribute("obj", obj);
				String nextJSP = "/AHF/pages/Distributor/ChotDonHang.jsp";
				response.sendRedirect(nextJSP);
			} 
			else
			{
				obj.init();
				obj.setMessege("Chot don hang thanh cong..");
				session.setAttribute("obj", obj);
				String nextJSP = "/AHF/pages/Distributor/ChotDonHang.jsp";
				response.sendRedirect(nextJSP);
			}
		} 
		else if (action.equals("TraTrungBay"))
		{
			obj.TraTrungBay(request);
			String msg = obj.getMessege();
			obj.init();
			obj.setMessege(msg);
			session.setAttribute("obj", obj);
			String nextJSP = "/AHF/pages/Distributor/ChotDonHang.jsp";
			response.sendRedirect(nextJSP);
		} 
		else if (action.equals("view") || action.equals("next") || action.equals("prev"))
		{
			obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
			obj.init();
			obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
			session.setAttribute("obj", obj);
			String nextJSP = "/AHF/pages/Distributor/ChotDonHang.jsp";
			response.sendRedirect(nextJSP);
		} 
		else
		{
			System.out.println("Refresh lai trang");
			obj.init();
			session.setAttribute("obj", obj);
			String nextJSP = "/AHF/pages/Distributor/ChotDonHang.jsp";
			response.sendRedirect(nextJSP);
		}

	}
}

