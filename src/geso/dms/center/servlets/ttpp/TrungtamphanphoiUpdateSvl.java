package geso.dms.center.servlets.ttpp;

import geso.dms.center.beans.ttpp.ITrungtamphanphoi;
import geso.dms.center.beans.ttpp.ITrungtamphanphoiList;
import geso.dms.center.beans.ttpp.imp.Trungtamphanphoi;
import geso.dms.center.beans.ttpp.imp.TrungtamphanphoiList;
import geso.dms.center.util.Utility;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/TrungtamphanphoiUpdateSvl")
public class TrungtamphanphoiUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public TrungtamphanphoiUpdateSvl()
	{
		super();
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		
		ITrungtamphanphoi dvkdBean = new Trungtamphanphoi();
		Utility util = new Utility();
		String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
		if (id != null)
			dvkdBean.setId(id);
		
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		dvkdBean.setUserId(userId);
		
		String ma = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ma")));
		dvkdBean.setMa(ma);
		
		String ten = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ten")));
		dvkdBean.setTen(ten);
		
		String trangthai;
		if (util.antiSQLInspection(request.getParameter("trangthai")) != null && util.antiSQLInspection(request.getParameter("trangthai")).trim().length()>0)
			trangthai = "1";
		else
			trangthai = "0";
		System.out.println("TT: " + trangthai + " -- " + util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"))));
		dvkdBean.setTrangthai(trangthai);
		
		String[] nccId = request.getParameterValues("nccId");
		dvkdBean.setNccId(nccId);
		
		boolean error = false;
		if (ma.trim().length() == 0)
		{
			dvkdBean.setMessage("Vui long nhap vao Trung tam phan phoi");
			error = true;
		}
		dvkdBean.createRs();
		
		session = request.getSession();
		session.setAttribute("userId", util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"))));
		
		if (error)
		{
			session.setAttribute("dvkdBean", dvkdBean);
			session.setAttribute("userId", userId);
			String nextJSP = "/AHF/pages/Center/TrungTamPhanPhoiNew.jsp";
			response.sendRedirect(nextJSP);
		} else
		{
			if (id == null || id.trim().length() == 0)
			{
				if (!dvkdBean.save())
				{
					session.setAttribute("dvkdBean", dvkdBean);
					session.setAttribute("userId", userId);
					String nextJSP = "/AHF/pages/Center/TrungTamPhanPhoiNew.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					ITrungtamphanphoiList obj = new TrungtamphanphoiList();
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);
					
					String nextJSP = "/AHF/pages/Center/TrungTamPhanPhoi.jsp";
					response.sendRedirect(nextJSP);
					
				}
			} else
			{
				if (!dvkdBean.edit())
				{
					session.setAttribute("dvkdBean", dvkdBean);
					session.setAttribute("userId", userId);
					String nextJSP = "/AHF/pages/Center/TrungTamPhanPhoiNew.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					ITrungtamphanphoiList obj = new TrungtamphanphoiList();
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);
					
					String nextJSP = "/AHF/pages/Center/TrungTamPhanPhoi.jsp";
					response.sendRedirect(nextJSP);
					
				}
				
			}
		}
	}
}
