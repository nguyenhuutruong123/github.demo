package geso.dms.center.servlets.nhomcttrungbay;


import geso.dms.center.beans.nhomcttrungbay.INhomCTTrungBay;
import geso.dms.center.beans.nhomcttrungbay.INhomCTTrungBayList;
import geso.dms.center.beans.nhomcttrungbay.imp.NhomCTTrungBay;
import geso.dms.center.beans.nhomcttrungbay.imp.NhomCTTrungBayList;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/NhomCTTrungBayUpdateSvl")
public class NhomCTTrungBayUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	public NhomCTTrungBayUpdateSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		INhomCTTrungBay ncttbBean;

		Utility util = new Utility();
		HttpSession session = request.getSession();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
	
		if (userId.length() == 0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		String id = util.getId(querystring);
		ncttbBean = new NhomCTTrungBay(id);
		ncttbBean.init();
		ncttbBean.setUserId(userId);

		session.setAttribute("ncttbBean", ncttbBean);
		String nextJSP = "/AHF/pages/Center/NhomCTTrungBayUpdate.jsp";
		if(querystring.indexOf("display") > 0)
	    {
	    	nextJSP = "/AHF/pages/Center/NhomCTTrungBayDisplay.jsp";
	    }
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();

		INhomCTTrungBay ncttbBean;
		this.out = response.getWriter();
		Utility util = new Utility();

		String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
		if (id == null)
		{
			ncttbBean = new NhomCTTrungBay("");
		} else
		{
			ncttbBean = new NhomCTTrungBay(id);
		}

		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		ncttbBean.setUserId(userId);

		String ten = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ten")));
		if (ten == null)
			ten = "";
		ncttbBean.setTen(ten);

		String ma = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ma")));
		if (ma == null)
			ma = "";
		ncttbBean.setMa(ma);
		

		boolean error = false;

		if (ma.trim().length() == 0)
		{
			ncttbBean.setMsg("Vui lòng nhập mã nhóm");
			error = true;
		}

		if (ten.trim().length() == 0)
		{
			ncttbBean.setMsg("Vui lòng nhập tên nhóm");
			error = true;
		}
		
		
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		String nextJSP = "";
		if (!error)
		{
			if (action.equals("save"))
			{
				INhomCTTrungBayList obj = new NhomCTTrungBayList();
				if (id == null)
				{
					if (!ncttbBean.save())
					{
						session.setAttribute("ncttbBean", ncttbBean);
						ncttbBean.setUserId(userId);
						nextJSP = "/AHF/pages/Center/NhomCTTrungBayNew.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						obj.setUserId(userId);
						obj.init("");
						session.setAttribute("obj", obj);
						nextJSP = "/AHF/pages/Center/NhomCTTrungBay.jsp";
						response.sendRedirect(nextJSP);
					}
				} else
				{
					if (!ncttbBean.edit())
					{
						session.setAttribute("ncttbBean", ncttbBean);
						ncttbBean.setUserId(userId);
						nextJSP = "/AHF/pages/Center/NhomCTTrungBayUpdate.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						obj.setUserId(userId);
						obj.init("");
						session.setAttribute("obj", obj);
						nextJSP = "/AHF/pages/Center/NhomCTTrungBay.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			} else
			{
				session.setAttribute("ncttbBean", ncttbBean);
				if (id == null)
				{
					nextJSP = "/AHF/pages/Center/NhomCTTrungBayNew.jsp";
				} else
				{
					nextJSP = "/AHF/pages/Center/NhomCTTrungBayUpdate.jsp";
				}
				response.sendRedirect(nextJSP);
			}

		} else
		{
			session.setAttribute("ncttbBean", ncttbBean);
			if (id == null)
			{
				nextJSP = "/AHF/pages/Center/NhomCTTrungBayNew.jsp";
			} else
			{
				nextJSP = "/AHF/pages/Center/NhomCTTrungBayUpdate.jsp";
			}
			response.sendRedirect(nextJSP);
		}
	}
}
