package geso.dms.center.servlets.duyetdhkm;

import geso.dms.center.beans.duyetdhkm.imp.*;
import geso.dms.center.beans.duyetdhkm.*;
import geso.dms.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DuyetdhkmUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private PrintWriter out;

	public DuyetdhkmUpdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();

		this.out = response.getWriter();
		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		out.println(userId);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

		String id = util.getId(querystring);

		IDuyetDhKm bean = new DuyetDhKm();
		bean.setId(id);
		bean.init_view();
		bean.setUserId(userId);
		session.setAttribute("dhkm", bean);
		String nextJSP = "/AHF/pages/Center/DuyetdhKmNew.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();

		IDuyetDhKm bean;
		this.out = response.getWriter();
		Utility util = new Utility();

		String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));

		bean = new DuyetDhKm();
		
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		bean.setUserId(userId);
		String thang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang")));
		bean.setThang(thang);
		String nam = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nam")));
		bean.setNam(nam);
		String ngaysua = getDateTime();
		bean.setNgaysua(ngaysua);
		String nguoisua = userId;
		bean.setNguoisua(nguoisua);

		String[] ctkmchon = request.getParameterValues("ctkmchon");

		String str = "";
		if (ctkmchon != null)
		{
			for (int i = 0; i < ctkmchon.length; i++)
			{
				if (i == 0)
				{
					str = ctkmchon[i];
				} else
				{
					str = str + "," + ctkmchon[i];
				}
			}
		}
		bean.setCTKMChon(str);

		String[] nppId = request.getParameterValues("npp");
		str = "";
		String nppIds = "";
		String spIds = "";
		String ctkmIds = "";

		String table = "";
		if (nppId != null)
		{
			for (int i = 0; i < nppId.length; i++)
			{
				if (i == 0)
				{
					str = nppId[i];
					nppIds = nppId[i].split("_")[0];
					ctkmIds = nppId[i].split("_")[1];
					spIds = nppId[i].split("_")[2];
					table = " select " + nppIds + " as nppId , " + ctkmIds + " as ctkmId , " + spIds + " as spId ";
				} else
				{
					str = str + "," + nppId[i];
					nppIds = nppId[i].split("_")[0];
					ctkmIds = nppId[i].split("_")[1];
					spIds = nppId[i].split("_")[2];

					table += " union select  " + nppIds + " as nppId , " + ctkmIds + " as ctkmId , " + spIds + " as spId ";
				}
			}
		}
		
		bean.setSelected(str);
		bean.setTable(table);

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if (action.equals("createdh"))
		{
			if (!bean.createDhKm())
			{
				bean.setUserId(userId);
				bean.init();
				session.setAttribute("dhkm", bean);
				String nextJSP;
				nextJSP = "/AHF/pages/Center/DuyetdhKmNew.jsp";
				response.sendRedirect(nextJSP);
			} else
			{
				IDuyetDhKmList obj = new DuyetDhKmList();
				obj.init("");
				obj.setUserId(userId);
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
				response.sendRedirect("/AHF/pages/Center/DuyetdhKm.jsp");
			}

		} else
		{
			bean.setUserId(userId);
			bean.init();
			session.setAttribute("dhkm", bean);
			String nextJSP;
			nextJSP = "/AHF/pages/Center/DuyetdhKmNew.jsp";
			response.sendRedirect(nextJSP);

		}
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

}
