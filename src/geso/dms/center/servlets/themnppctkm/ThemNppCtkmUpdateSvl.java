package geso.dms.center.servlets.themnppctkm;

import geso.dms.center.beans.themnppctkm.IThemNppCtkm;
import geso.dms.center.beans.themnppctkm.IThemNppCtkmList;
import geso.dms.center.beans.themnppctkm.imp.ThemNppCtkm;
import geso.dms.center.beans.themnppctkm.imp.ThemNppCtkmList;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ThemNppCtkmUpdateSvl")
public class ThemNppCtkmUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	public ThemNppCtkmUpdateSvl()
	{
		super();
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IThemNppCtkm fileBean;

		this.out = response.getWriter();
		Utility util = new Utility();
		
		//--- check user
	    String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		if (view == null) {
			view = "";
		}
		String param = "";
		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "ThemNppCtkmSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
		 //--- check user 

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		out.println(userId);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

		String id = util.getId(querystring);

		fileBean = new ThemNppCtkm(id);


		fileBean.setId(id);
		fileBean.setUserId(userId);

		fileBean.init();
		session.setAttribute("fileBean", fileBean);

		String nextJSP = "/AHF/pages/Center/ThemNppCtkmUpdate.jsp";
		if(querystring.indexOf("display") >= 0)
		{
			nextJSP = "/AHF/pages/Center/ThemNppCtkmDisplay.jsp";
		}

		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IThemNppCtkm fileBean;

		Utility util = new Utility();
		
		//--- check user
	    String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		if (view == null) {
			view = "";
		}
		String param = "";
		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "ThemNppCtkmSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
		 //--- check user 
		
		String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
		if(id == null){
			fileBean = new ThemNppCtkm();
		}else{
			fileBean = new ThemNppCtkm(id);
		}

		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		fileBean.setUserId(userId);

		/*String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
		if (trangthai == null)
			trangthai = "";
		fileBean.setTrangthai(trangthai);*/
		
		
		String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
		fileBean.setNppId(nppId);

		String ghichu = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ghichu")));
		if (ghichu == null)
			ghichu = "";
		fileBean.setGhichu(ghichu);
		
		String[] scheme = request.getParameterValues("scheme");
		fileBean.setScheme(scheme);

		String[] diengiai = request.getParameterValues("diengiai");
		fileBean.setDiengiai(diengiai);

		String[] loaingansach = request.getParameterValues("loaingansach");
		fileBean.setLoaingansach(loaingansach);
		
		String[] ngansach = request.getParameterValues("ngansach");
		fileBean.setNganSach(ngansach);
			
		String[]  ctkmId = request.getParameterValues("ctkmId");
		fileBean.setCtkmId(ctkmId);
		
				
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		System.out.println("______action____"+action);
		if(action.equals("save"))
		{
			if(id == null || id.trim().length()==0)
			{
				if (!(fileBean.save()))
				{
					fileBean.createRs();
					session.setAttribute("fileBean", fileBean);
					session.setAttribute("userId", userId);
					String nextJSP = "/AHF/pages/Center/ThemNppCtkmNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IThemNppCtkmList obj = new ThemNppCtkmList();
					obj.setUserId(userId);
					obj.init("");
					session.setAttribute("obj", obj);
					String nextJSP = "/AHF/pages/Center/ThemNppCtkm.jsp";
					response.sendRedirect(nextJSP);
				}
			}
			else
			{
				if (!(fileBean.edit()))
				{
					fileBean.createRs();
					session.setAttribute("fileBean", fileBean);
					session.setAttribute("userId", userId);

					String nextJSP = "/AHF/pages/Center/ThemNppCtkmUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IThemNppCtkmList obj = new ThemNppCtkmList();
					obj.setUserId(userId);
					obj.init("");

					session.setAttribute("obj", obj);

					String nextJSP = "/AHF/pages/Center/ThemNppCtkm.jsp";
					response.sendRedirect(nextJSP);
				}
			}
		}
		else
		{
			fileBean.createRs();
			session.setAttribute("userId", userId);
			session.setAttribute("fileBean", fileBean);

			String nextJSP = "/AHF/pages/Center/ThemNppCtkmNew.jsp";

			if( id != null )
			{
				nextJSP = "/AHF/pages/Center/ThemNppCtkmUpdate.jsp";
			}

			response.sendRedirect(nextJSP);
		}
	}
}
