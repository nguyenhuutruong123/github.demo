package geso.dms.distributor.servlets.denghitratb;

import geso.dms.distributor.beans.denghitratb.IDenghitratb;
import geso.dms.distributor.beans.denghitratb.IDenghitratbList;
import geso.dms.distributor.beans.denghitratb.imp.Denghitratb;
import geso.dms.distributor.beans.denghitratb.imp.DenghitratbList;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DenghitratbUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
	
	public DenghitratbUpdateSvl()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IDenghitratb dnttbBean;
		HttpSession session = request.getSession();
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		
		out.println(userId);
		
		if (userId.length() == 0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		
		String id = util.getId(querystring);
		
		dnttbBean = new Denghitratb(id);
		dnttbBean.setUserId(userId);
		dnttbBean.init();
		
		session.setAttribute("dnttbBean", dnttbBean);
		String nextJSP = "/AHF/pages/Distributor/DeNghiTraTbUpdate.jsp";
		
		if (querystring.contains("display"))
			nextJSP = "/AHF/pages/Distributor/DeNghiTraTbDisplay.jsp";
		
		response.sendRedirect(nextJSP);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IDenghitratb dnttbBean;
		this.out = response.getWriter();
		Utility util = new Utility();
		
		String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
		if (id == null)
		{
			dnttbBean = new Denghitratb("");
		} else
		{
			dnttbBean = new Denghitratb(id);
		}
		
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		dnttbBean.setUserId(userId);
		
		String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
		if (nppId == null)
			nppId = "";
		dnttbBean.setNppId(nppId);
		
		String cttbId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("cttbTen")));
		if (cttbId == null)
			cttbId = "";
		dnttbBean.setCttbId(cttbId);
		
		String[] nvbhIds = request.getParameterValues("nvbhIds");
		dnttbBean.setNvbhIds(nvbhIds);
		
		String[] khIds = request.getParameterValues("khIds");
		String[] ddkdIds = request.getParameterValues("ddkdIds");
		String[] dangky = request.getParameterValues("dangky");
		String[] denghi = request.getParameterValues("denghi");
		
		boolean error = false;
		
		if (cttbId.trim().length() == 0)
		{
			dnttbBean.setMessage("Vui Long Chon Chuong Trinh Trung bay");
			error = true;
		}
		
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		
		if (action.equals("save"))
		{
			if (id == null)
			{
				if (!(dnttbBean.CreateDnttb(khIds, ddkdIds, dangky, denghi)))
				{
					dnttbBean.createRS();
					dnttbBean.createKhRs();
					session.setAttribute("dnttbBean", dnttbBean);
					String nextJSP = "/AHF/pages/Distributor/DeNghiTraTbNew.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					IDenghitratbList obj = new DenghitratbList();
					obj.setUserId(userId);
					obj.init("");
					session.setAttribute("obj", obj);
					
					String nextJSP = "/AHF/pages/Distributor/DeNghiTraTrungBay.jsp";
					response.sendRedirect(nextJSP);
				}
				
			} else
			{
				
				if (!(dnttbBean.UpdateDnttb(khIds, ddkdIds, dangky, denghi)))
				{
					dnttbBean.init();
					dnttbBean.setNvbhIds(nvbhIds);
					dnttbBean.createKhRs();
					session.setAttribute("dnttbBean", dnttbBean);
					String nextJSP = "/AHF/pages/Distributor/DeNghiTraTbUpdate.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					IDenghitratbList obj = new DenghitratbList();
					obj.setUserId(userId);
					obj.init("");
					session.setAttribute("obj", obj);
					
					String nextJSP = "/AHF/pages/Distributor/DeNghiTraTrungBay.jsp";
					response.sendRedirect(nextJSP);
				}
			}
		} else
		{
			String nextJSP;
			if (id == null)
			{
				dnttbBean.createRS();
				dnttbBean.createKhRs();
				session.setAttribute("dnttbBean", dnttbBean);
				nextJSP = "/AHF/pages/Distributor/DeNghiTraTbNew.jsp";
			} else
			{
				dnttbBean.init();
				dnttbBean.setNvbhIds(nvbhIds);
				dnttbBean.createKhRs();
				session.setAttribute("dnttbBean", dnttbBean);
				nextJSP = "/AHF/pages/Distributor/DeNghiTraTbUpdate.jsp";
			}
			response.sendRedirect(nextJSP);
		}
	}
	
}
