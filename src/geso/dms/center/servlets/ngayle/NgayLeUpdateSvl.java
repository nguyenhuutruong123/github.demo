package geso.dms.center.servlets.ngayle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.dms.center.util.Utility;
import geso.dms.center.beans.ngayle.INgayLe;
import geso.dms.center.beans.ngayle.INgayLeList;
import geso.dms.center.beans.ngayle.imp.NgayLe;
import geso.dms.center.beans.ngayle.imp.NgayLeList;
import geso.dms.center.beans.nhacungcap.INhacungcap;
import geso.dms.center.beans.nhacungcap.imp.Nhacungcap;
import geso.dms.center.beans.nhacungcap.INhacungcapList;
import geso.dms.center.beans.nhacungcap.imp.NhacungcapList;
import geso.dms.distributor.beans.donhang.IDonhang;
import geso.dms.distributor.beans.donhang.imp.Donhang;

public class NgayLeUpdateSvl extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {
	static final long serialVersionUID = 1L;

	public NgayLeUpdateSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Utility util = new Utility();
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session
				.getAttribute("util");
		if (!cutil.check(userId, userTen, sum)) {
			response.sendRedirect("/AHF/index.jsp");
		} else {
			session.setMaxInactiveInterval(30000);

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);

			if (userId.length() == 0)
				userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

			String id = util.getId(querystring);
			
			if ( Utility.CheckSessionUser( session,  request, response))
			{
				Utility.RedireactLogin(session, request, response);
			}
			
			INgayLe nccBean = new NgayLe();
			nccBean.setId(id);
			nccBean.setUserId(userId);
			nccBean.init();

			String nextJSP = "/AHF/pages/Center/NgayLeUpdate.jsp";
			session.setAttribute("nccBean", nccBean);
			response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		INgayLe ncc = new NgayLe();

		Utility util = new Utility();

		HttpSession session = request.getSession();
		
		
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		//String userId =  session.getAttribute("userId");
		ncc.setUserId(userId);
		
		String id =   util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
		if (id == null )
			id = "";
		ncc.setId(id);
		
		String ngayle = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngayle")));
		if (ngayle == null)
			ngayle = "";
		ncc.setNgayle(ngayle);
		
		String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		if (diengiai == null)
			diengiai = "";		
		ncc.setDiengiai(diengiai);
		
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
				
		ncc.setNgaytao(getDateTime());
		ncc.setNgaysua(getDateTime());
		
		
		boolean error = false;
		
		if(ngayle.trim().length() !=10)
		{
			error = true;
			ncc.setMessage( "Vui lòng nhập ngày lễ"  );
		}
		
		if (error) 
		{
			session.setAttribute("nccBean", ncc);
			String nextJSP  = "";
			
			if (id == null || id.length() <= 0)			
				nextJSP = "/AHF/pages/Center/NgayLeNew.jsp";			
			else 
				nextJSP = "/AHF/pages/Center/NgayLeUpdate.jsp";	
			response.sendRedirect(nextJSP);
		} 
		else 
		{ //
			boolean  thanhcong = false;
			if (id == null || id.length() <= 0) 
			{
				if (!ncc.saveNewNgayLe()) 
				{
					session.setAttribute("nccBean", ncc);
					String nextJSP = "/AHF/pages/Center/NgayLeNew.jsp";
					response.sendRedirect(nextJSP);
					return;
				}
				else
					thanhcong = true;
				
			
			} else {
				if (!ncc.UpdateNcc()) {
					session.setAttribute("nccBean", ncc);
					String nextJSP = "/AHF/pages/Center/NgayLeUpdate.jsp";
					response.sendRedirect(nextJSP);
					return;
				}
				else
					thanhcong = true;
			}
			if(thanhcong)
			{
				ncc.DBClose();				
				INgayLeList obj = new NgayLeList();
				obj.init();
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
				String nextJSP = "/AHF/pages/Center/NgayLe.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

}