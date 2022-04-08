package geso.dms.center.servlets.nhomnhaphanphoi;

import geso.dms.center.beans.nhomnhaphanphoi.INhomNhaPhanPhoi;
import geso.dms.center.beans.nhomnhaphanphoi.INhomNhaPhanPhoiList;
import geso.dms.center.beans.nhomnhaphanphoi.imp.NhomNhaPhanPhoi;
import geso.dms.center.beans.nhomnhaphanphoi.imp.NhomNhaPhanPhoiList;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/NhomNhaPhanPhoiUpdateSvl")
public class NhomNhaPhanPhoiUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;

	public NhomNhaPhanPhoiUpdateSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		INhomNhaPhanPhoi nnppBean;

		Utility util = new Utility();
		HttpSession session = request.getSession();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		System.out.println(userId);
		if (userId.length() == 0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		String id = util.getId(querystring);
		nnppBean = new NhomNhaPhanPhoi(id);
		nnppBean.init();
		nnppBean.setUserId(userId);

		session.setAttribute("nnppBean", nnppBean);
		String nextJSP = "/AHF/pages/Center/NhomNhaPhanPhoiUpdate.jsp";
		if(querystring.indexOf("display") > 0)
	    {
	    	nextJSP = "/AHF/pages/Center/NhomNhaPhanPhoiDisplay.jsp";
	    }
		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();

		INhomNhaPhanPhoi nnppBean;
		this.out = response.getWriter();
		Utility util = new Utility();

		String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
		if (id == null)
		{
			nnppBean = new NhomNhaPhanPhoi("");
		} else
		{
			nnppBean = new NhomNhaPhanPhoi(id);
		}

		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		nnppBean.setUserId(userId);

		String ten = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ten")));
		if (ten == null)
			ten = "";
		nnppBean.setTen(ten);

		String ma = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ma")));
		if (ma == null)
			ma = "";
		nnppBean.setMa(ma);

		String loainhom = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loainhom")));
		if (loainhom == null)
			loainhom = "";
		nnppBean.setLoainhom(loainhom);

		String vungId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")));
		
		
		
		if (vungId == null)
			vungId = "";
		nnppBean.setVungId(vungId);

		String kvId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kvId")));
		if (kvId == null)
			kvId = "";
		nnppBean.setKvId(kvId);

		String kenhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")));
		if (kenhId == null)
			kenhId = "";
		nnppBean.setKenhId(kenhId);

		String quanhuyenId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("quanhuyenId")));
		if (quanhuyenId == null)
			quanhuyenId = "";
		nnppBean.setQuanhuyenId(quanhuyenId);

		String tinhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tinhId")));
		if (tinhId == null)
			tinhId = "";
		nnppBean.setTinhId(tinhId);

		String nppId = "";

		String[] nppIds = request.getParameterValues("nppId");
		if (nppIds != null)
		{
			int size = nppIds.length;
			for (int i = 0; i < size; i++)
			{
				nppId += nppIds[i] + ",";
			}
			if (nppId.length() > 0)
			{
				nppId = nppId.substring(0, nppId.length() - 1);
			}
		}

		nnppBean.setNppId(nppId);
		
		

		boolean error = false;

		if (ma.trim().length() == 0)
		{
			nnppBean.setMsg("Vui lòng nhập mã nhóm");
			error = true;
		}

		if (ten.trim().length() == 0)
		{
			nnppBean.setMsg("Vui lòng nhập tên nhóm");
			error = true;
		}

		if(nppId.length()<=0)
		{
			nnppBean.setMsg("Vui lòng chọn nhà phân phối");
			error = true;
		}
		
		
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		String nextJSP = "";
		if (!error)
		{
			if (action.equals("save"))
			{
				INhomNhaPhanPhoiList obj = new NhomNhaPhanPhoiList();
				if (id == null)
				{
					if (!nnppBean.save())
					{
						session.setAttribute("nnppBean", nnppBean);
						nnppBean.setUserId(userId);
						nextJSP = "/AHF/pages/Center/NhomNhaPhanPhoiNew.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						obj.setUserId(userId);
						obj.init("");
						session.setAttribute("obj", obj);
						nextJSP = "/AHF/pages/Center/NhomNhaPhanPhoi.jsp";
						response.sendRedirect(nextJSP);
					}
				} else
				{
					if (!nnppBean.edit())
					{
						session.setAttribute("nnppBean", nnppBean);
						nnppBean.setUserId(userId);
						nextJSP = "/AHF/pages/Center/NhomNhaPhanPhoiUpdate.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						obj.setUserId(userId);
						obj.init("");
						session.setAttribute("obj", obj);
						nextJSP = "/AHF/pages/Center/NhomNhaPhanPhoi.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			} else
			{
				nnppBean.createRs();
				session.setAttribute("nnppBean", nnppBean);
				if (id == null)
				{
					nextJSP = "/AHF/pages/Center/NhomNhaPhanPhoiNew.jsp";
				} else
				{
					nextJSP = "/AHF/pages/Center/NhomNhaPhanPhoiUpdate.jsp";
				}
				response.sendRedirect(nextJSP);
			}

		} else
		{
			nnppBean.createRs();
			session.setAttribute("nnppBean", nnppBean);
			if (id == null)
			{
				nextJSP = "/AHF/pages/Center/NhomNhaPhanPhoiNew.jsp";
			} else
			{
				nextJSP = "/AHF/pages/Center/NhomNhaPhanPhoiUpdate.jsp";
			}
			response.sendRedirect(nextJSP);
		}
	}
}
