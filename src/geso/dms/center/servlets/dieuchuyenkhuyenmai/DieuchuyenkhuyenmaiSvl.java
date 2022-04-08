package geso.dms.center.servlets.dieuchuyenkhuyenmai;

import geso.dms.center.beans.dieuchuyenkhuyenmai.IDieuchuyenkhuyenmai;
import geso.dms.center.beans.dieuchuyenkhuyenmai.imp.Dieuchuyenkhuyenmai;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DieuchuyenkhuyenmaiSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public DieuchuyenkhuyenmaiSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Utility util = new Utility();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IDieuchuyenkhuyenmai dckmBean = new Dieuchuyenkhuyenmai();
		
		String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		if (view == null) 
			view = "";

		String param = "";
		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response)) {
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "DieuchuyenkhuyenmaiSvl", param, Utility.XEM )) {
			Utility.RedireactLogin(session, request, response);
		}

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		System.out.println(userId);
		dckmBean.setUserId(userId);
		dckmBean.init();
		session.setAttribute("dckm", dckmBean);
		session.setAttribute("schemeId", "0");
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Center/DieuChuyenKhuyenMai.jsp";
		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();

		IDieuchuyenkhuyenmai dckmBean = new Dieuchuyenkhuyenmai();
		Utility util = new Utility();

		String schemeoldid = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("schemeoldid")));
		String schemeId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("schemeId")));
		dckmBean.setSchemeId(schemeId);

		String kvId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kvId")==null?"":request.getParameter("kvId")));
		dckmBean.setKvId(kvId);

		String userId = (String) session.getAttribute("userId");
		dckmBean.setUserId(userId);
		
		String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
		if(tungay == null) tungay = "";
		dckmBean.setTungay(tungay);

		String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
		if(denngay == null) denngay = "";
		dckmBean.setDenngay(denngay);
		

		String vungId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")));
		if(vungId == null) vungId = "";
		dckmBean.setVungId(vungId);
		
		String khuvucId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")));
		if(khuvucId == null) khuvucId = "";
		dckmBean.setKvId(khuvucId);
		
		String[] nppId = request.getParameterValues("nppId");
		String[] ngansach = request.getParameterValues("ngansach");
		String[] dasudung = request.getParameterValues("dasudung");
		String[] ngansachmoi = request.getParameterValues("ngansachmoi");
		String[] conlai = request.getParameterValues("conlai");
		String[] dieuchuyen = request.getParameterValues("dieuchuyen");
		
		if (nppId != null)
		{
			Hashtable<String, String> htbDieuchuyen = new Hashtable<String, String>();
			for (int i = 0; i < nppId.length; i++)
			{
				String value=ngansach[i].replaceAll(",", "")+"__"+dasudung[i].replaceAll(",", "")+"__"+conlai[i].replaceAll(",", "")
						+"__"+ngansachmoi[i].replaceAll(",", "")+"__"+dieuchuyen[i].replaceAll(",", "");
				if(!schemeId.trim().equals(schemeoldid))
					value=ngansach[i].replaceAll(",", "")+"__"+dasudung[i].replaceAll(",", "")+"__"+conlai[i].replaceAll(",", "")
					+"__0__ ";
				if(dieuchuyen[i].length()>0)
					htbDieuchuyen.put(nppId[i], value  );
			}
			dckmBean.setDieuchuyen(htbDieuchuyen);
		}
		dckmBean.init();

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));

		if (action.equals("save"))
		{
			if (dckmBean.save(request))
			{
				dckmBean.setMessage("Dieu chuyen khuyen mai da duoc luu thanh cong");
			}
		}

		session.setAttribute("dckm", dckmBean);
		session.setAttribute("schemeId", schemeId);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Center/DieuChuyenKhuyenMai.jsp";
		response.sendRedirect(nextJSP);
	}

}
