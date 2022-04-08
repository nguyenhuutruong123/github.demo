package geso.dms.center.servlets.nganhang;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;


import geso.dms.center.util.Utility;
import geso.dms.center.beans.nganhang.IErpNganHang;
import geso.dms.center.beans.nganhang.IErpNganHangList;
import geso.dms.center.beans.nganhang.imp.*;

@WebServlet("/ErpNganHangUpdateSvl")
public class ErpNganHangUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErpNganHangUpdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();

		Utility util = new Utility();
		out = response.getWriter();

		String querystring = request.getQueryString();
		String action = util.getAction(querystring);
		out.println(action);

		String nhId = util.getId(querystring);

		String userId = util.getUserId(querystring);

		if (action.equals("update"))
		{
			IErpNganHang nhBean = new ErpNganHang(nhId);
			nhBean.init();
			nhBean.setUserid(userId);
			String nextJSP = "pages/Center/ErpNganHangUpdate.jsp";
			session.setAttribute("nhBean", nhBean);
			response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
		String userTen = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userTen"));
		String sum = (String) session.getAttribute("sum");
		Utility cutil = (Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/index.jsp");
		} else
		{
			IErpNganHang nhBean = new ErpNganHang();

			String nextJSP = "";

			String id = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id"));
			String ma = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Ma"));
			String ten = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Ten"));
			String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("hoatdong"));
			if (trangthai == null)
				trangthai = "0";
			else
				trangthai = "1";
			nhBean.setTrangthai(trangthai);

			String ngaytao = getDateTime();
			String nguoitao = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
			String ngaysua = getDateTime();
			String nguoisua = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));

			if (id != null)
				nhBean.setID(id);
			if (ma != null)
				nhBean.setMA(ma);
			if (ten != null)
				nhBean.setTEN(ten);

			nhBean.setNGUOITAO(nguoitao);
			nhBean.setNGAYTAO(ngaytao);

			nhBean.setNGUOISUA(nguoisua);
			nhBean.setNGAYSUA(ngaysua);

			session.setAttribute("userId", userId);
			session.setAttribute("userTen", userTen);

			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));

			if (action.equals("save"))
			{
				if (id == null)
				{
					if (!nhBean.themMoiMa())
					{
						nextJSP = "pages/Center/ErpNganHangNew.jsp";
					}

					else
					{
						IErpNganHangList nhList = new ErpNganHangList();
						nhList.setUserid(userId);
						nhList.init();
						session.setAttribute("nhList", nhList);
						nextJSP = "pages/Center/ErpNganHang.jsp";
					}
				} else if (id != null)
				{
					if (!nhBean.UpdateMa())
					{
						nextJSP = "pages/Center/ErpNganHangUpdate.jsp";
					}

					else
					{
						IErpNganHangList nhList = new ErpNganHangList();
						nhList.setUserid(userId);
						nhList.init();
						session.setAttribute("nhList", nhList);
						nextJSP = "pages/Center/ErpNganHang.jsp";
					}
				}
			}
			session.setAttribute("nhBean", nhBean);
			response.sendRedirect(nextJSP);

		}
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public static void main(String[] arg)
	{
		String str="<a>Tinh te</a>";
		System.out.println(str.matches("<a\b[^>]*>(.*?)</a>"));
	}
	
	
	
	
}
