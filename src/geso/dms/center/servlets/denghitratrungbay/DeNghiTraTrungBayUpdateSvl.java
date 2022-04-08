package geso.dms.center.servlets.denghitratrungbay;

import geso.dms.center.beans.denghitratrungbay.IDeNghiTraTrungBay;
import geso.dms.center.beans.denghitratrungbay.IDeNghiTraTrungBayList;
import geso.dms.center.beans.denghitratrungbay.imp.DeNghiTraTrungBay;
import geso.dms.center.beans.denghitratrungbay.imp.DeNghiTraTrungBayList;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/DeNghiTraTrungBayUpdateSvl")
public class DeNghiTraTrungBayUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
	public DeNghiTraTrungBayUpdateSvl()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/AHF/index.jsp");
		} else
		{
			session.setMaxInactiveInterval(30000);
			
			Utility util = new Utility();
			
			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);
			
			if (userId.length() == 0)
				userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
			
			String id = util.getId(querystring);
			IDeNghiTraTrungBay lsxBean = new DeNghiTraTrungBay(id);
			lsxBean.setUserId(userId);
			
			String nextJSP = "";
			
			lsxBean.init();
			
			String hopdongId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("hopdongId"));
			if (hopdongId == null)
				hopdongId = "";
			
			nextJSP = "/AHF/pages/Center/DeNghiTraTrungBayUpdate.jsp";
			if(request.getQueryString().indexOf("display") >= 0 ) 
      {
      	nextJSP = "/AHF/pages/Center/DeNghiTraTrungBayDisplay.jsp";
      }
			session.setAttribute("lsxBean", lsxBean);
			response.sendRedirect(nextJSP);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/AHF/index.jsp");
		} else
		{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			
			session.setMaxInactiveInterval(30000);
			
			this.out = response.getWriter();
			IDeNghiTraTrungBay lsxBean;
			
			Utility util = new Utility();
			String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
			if (id == null)
			{
				lsxBean = new DeNghiTraTrungBay("");
			} else
			{
				lsxBean = new DeNghiTraTrungBay(id);
			}
			
			lsxBean.setUserId(userId);
			
			String ngaydenghi = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaydenghi")));
			if (ngaydenghi == null)
				ngaydenghi = "";
			lsxBean.setNgaydenghi(ngaydenghi);
			
			String action = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));
			if (action == null)
				action = "";
			
			
			String[] khId = request.getParameterValues("khId");
			String[] khDuyet = request.getParameterValues("khDuyet");
			if (khId != null)
			{
				Hashtable<String, String> htb_duyet = new Hashtable<String, String>();
				for (int i = 0; i < khId.length; i++)
				{
					htb_duyet.put(khId[i], khDuyet[i].replaceAll(",", ""));
				}
				lsxBean.setKh_Duyet(htb_duyet);
			}
		
			
			System.out.println("---VO DUYET DON HANG...");
			boolean kq = false;
			
			 if (action.equals("save")) 
				 kq = lsxBean.Save(); 
			 else kq =
					 lsxBean.Chot();
			 
			if (!kq)
			{
				lsxBean.init();
				session.setAttribute("lsxBean", lsxBean);
				String nextJSP = "/AHF/pages/Center/DeNghiTraTrungBayUpdate.jsp";
				response.sendRedirect(nextJSP);
			} else
			{
				IDeNghiTraTrungBayList obj = new DeNghiTraTrungBayList();
				obj.setLoaidonhang("");
				obj.setUserId(userId);
				obj.init("");
				session.setAttribute("obj", obj);
				String nextJSP = "/AHF/pages/Center/DeNghiTraTrungBay.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}
}
