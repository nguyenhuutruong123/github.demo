package geso.dms.center.servlets.mochot;

import geso.dms.center.beans.mochot.IMochot;
import geso.dms.center.beans.mochot.imp.Mochot;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MochotdhSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public MochotdhSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		Utility util = new Utility();

		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String querystring = request.getQueryString();

		String userId = util.getUserId(querystring);
		out.println(userId);

		if (userId.length() == 0)
			userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));

		session.setAttribute("userId", userId);
		IMochot mksBean = new Mochot();
		mksBean.setVungId("");
		mksBean.setKhuvucId("");
		mksBean.setNppId("");
		mksBean.init();

		session.setAttribute("mksBean", mksBean);
		String nextJSP = "/AHF/pages/Center/Mochot.jsp";
		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		Utility util = new Utility();

		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		IMochot mksBean = new Mochot();
		String userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
		String vungId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"));
		String kenhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId"));
		String kvId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kvId"));
		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
		String ngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngay"));
		
		String dhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dhId"));
		if (dhId == null)
			dhId = "";
		System.out.println("dhId" +dhId);
		mksBean.setDonhangId(dhId);
		session.setAttribute("dhId", dhId);
		
    	
    	
    	String gsbhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gsbhTen"));
    	if (gsbhId == null)
    		gsbhId = "";    	
    	mksBean.setGsbhId(gsbhId);
    	
    	String ddkdId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdTen"));
    	if (ddkdId == null)
    		ddkdId = "";    	
    	mksBean.setDdkdId(ddkdId);
    	session.setAttribute("ddkdId", ddkdId);
    	
    	String khId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khId"));
    	if (khId == null)
    		khId = "";    	
    	mksBean.setKhId(khId);
    	session.setAttribute("khId", khId);
    	
    	String smartId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("smartId"));
    	if (smartId == null)
    		smartId = "";    	
    	mksBean.setSmartId(smartId);

    	String khTen = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khTen"));
    	if (khTen == null)
    		
    		khTen = "";    	
    	mksBean.setKhTen(khTen);
		
    	
    	String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays")));
		if (tungay == null)
			tungay = "";
		mksBean.settungay(tungay);

		String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays")));
		if (denngay == null)
			denngay = "";
		mksBean.setdenngay(denngay);
		
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));

		if (vungId == null)
			vungId = "";
		if (kvId == null)
			kvId = "";
		if (nppId == null)
			nppId = "";
		if (kenhId == null)
			kenhId = "";
		
		
		mksBean.setVungId(vungId);
		mksBean.setKhuvucId(kvId);
		mksBean.setKenhId(kenhId);
		mksBean.setNppId(nppId);
		mksBean.setNgay(ngay);
		mksBean.setUserId(userId);
		
		mksBean.init();

		if (action.equals("mochot"))
		{
			if (nppId.length() > 0)
			{
				mksBean.setMsg(mksBean.MochotDH());
			} else
			{
				mksBean.setMsg("Vui lòng chọn Nhà phân phối, từ ngày, đến ngày");
			}
		} else if (action.equals("huydon"))
		{
			if (nppId.length() > 0)
			{
				mksBean.setMsg(mksBean.HuyDH());
			} else
				mksBean.setMsg("Vui lòng chọn Nhà phân phối, từ ngày, đến ngày");
		}

		session.setAttribute("mksBean", mksBean);
		String nextJSP = "/AHF/pages/Center/Mochot.jsp";
		response.sendRedirect(nextJSP);

	}

}
