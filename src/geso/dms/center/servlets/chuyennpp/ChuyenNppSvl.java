package geso.dms.center.servlets.chuyennpp;

import geso.dms.center.beans.chuyennpp.imp.ChuyenNpp;
import geso.dms.center.beans.chuyennpp.IChuyenNpp;

import geso.dms.center.util.Utility;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ChuyenNppSvl")
public class ChuyenNppSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ChuyenNppSvl() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IChuyenNpp obj = new ChuyenNpp();

		Utility util = new Utility();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		obj.setuserId(userId);
		
		String task = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("task"));
		if(task == null) task = "";
		obj.setTask(task);
		
		obj.init();
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);

		String nextJSP = "/AHF/pages/Center/ChuyenNpp.jsp";
		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IChuyenNpp obj = new ChuyenNpp();
		Utility util = new Utility();

		String userId = (String) session.getAttribute("userId");
		if (userId == null)
			userId = "";
		obj.setuserId(userId);

		 obj.setvungId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")))!=null?
		 util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")):""));
		
		 obj.setkhuvucId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")))!=null?
		 util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")):""));

		String nppIdFrom = util.antiSQLInspection(request
				.getParameter("nppIdFrom"));
		if (nppIdFrom == null)
			nppIdFrom = "";
		obj.setNppIdFrom(nppIdFrom);

		String nppIdTo = util
				.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppIdTo")));
		if (nppIdTo == null)
			nppIdTo = "";
		obj.setNppIdTo(nppIdTo);
		
		
		String ngayks=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngayks")));
		if (ngayks == null)
			ngayks = "";
		obj.setNgayKs(ngayks);
		
		System.out.println("[nppIdFrom]" + nppIdFrom + "[nppIdTo]" + nppIdTo);
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if(nppIdFrom.equals(nppIdTo))
		{
			obj.setMsg("Trùng nhà phân phối ! Vui lòng chọn lại !");
			obj.init();
		}
		
		
		if (action.equals("TransferData") && !nppIdFrom.equals(nppIdTo)) {
			if(obj.TransferData())
			{
				obj.setMsg("Chuyển NPP thành công");
				
			}else
				obj.setMsg("Chuyển NPP thất bại");
		}
		obj.init();
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Center/ChuyenNpp.jsp";
		response.sendRedirect(nextJSP);
	}

}
