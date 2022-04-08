package geso.dms.center.servlets.thaydoikhuyenmai;

import geso.dms.center.beans.thaydoikhuyenmai.ISanPham;
import geso.dms.center.beans.thaydoikhuyenmai.IThayDoiKhuyenMai;
import geso.dms.center.beans.thaydoikhuyenmai.IThayDoiKhuyenMaiList;
import geso.dms.center.beans.thaydoikhuyenmai.imp.SanPham;
import geso.dms.center.beans.thaydoikhuyenmai.imp.ThayDoiKhuyenMai;
import geso.dms.center.beans.thaydoikhuyenmai.imp.ThayDoiKhuyenMaiList;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ThayDoiKhuyenMaiUpdateSvl")
public class ThayDoiKhuyenMaiUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ThayDoiKhuyenMaiUpdateSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();

		IThayDoiKhuyenMai tdkmBean;
		PrintWriter out = response.getWriter();

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
		if( Utility.CheckRuleUser( session,  request, response, "ThayDoiKhuyenMaiSvl", param, Utility.XEM ))
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

		
		String action = util.getAction(querystring);
		out.println(action);
		tdkmBean = new ThayDoiKhuyenMai(id);
		tdkmBean.setUserId(userId);
		tdkmBean.init();

		session.setAttribute("tdkmBean", tdkmBean);
		if (action.equals("edit"))
		{
			String nextJSP = "/AHF/pages/Center/ThayDoiKhuyenMaiUpdate.jsp";
			response.sendRedirect(nextJSP);
		}
		else if(action.equals("display"))
		{
			String nextJSP = "/AHF/pages/Center/ThayDoiKhuyenMaiDisplay.jsp";
			response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();

		IThayDoiKhuyenMai tdkmBean;
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
		if( Utility.CheckRuleUser( session,  request, response, "ThayDoiKhuyenMaiSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
		 //--- check user 

		String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
		if (id == null)
		{
			tdkmBean = new ThayDoiKhuyenMai("");
		} else
		{
			tdkmBean = new ThayDoiKhuyenMai(id);
		}

		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		tdkmBean.setUserId(userId);

		String loai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loai")==null?"":request.getParameter("loai")));
		if (loai == null)
			loai = "";
		tdkmBean.setLoai(loai);

		String tongtien = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tongtien")));
		if (tongtien == null)
			tongtien = "";
		tdkmBean.setTongtien(tongtien);

		String chietkhau = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chietkhau")));
		if (chietkhau == null)
			chietkhau = "";
		tdkmBean.setChietkhau(chietkhau);

		String tongluong = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tongluong")));
		if (tongluong == null)
			tongluong = "";
		tdkmBean.setTongluong(tongluong);

		String hinhthuc = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("hinhthuc")));
		if (hinhthuc == null)
			hinhthuc = "";
		tdkmBean.setHinhthuc(hinhthuc);
		
		String trakmId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trakmId")));
		if (trakmId == null)
			trakmId = "";
		tdkmBean.setTrakmId(trakmId);
		
		session.setAttribute("trakmId", trakmId);
		
		String dkkmId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dkkmId")));
		if (dkkmId == null)
			dkkmId = "";
		tdkmBean.setDkkmId(dkkmId);
		
		
		String ctkmId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ctkmId")==null?"":request.getParameter("ctkmId")));
		tdkmBean.setCtkmId(ctkmId);
		
		session.setAttribute("dkkmId", dkkmId);
		
		String type =util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("type")));
		
		if(loai.equals("2"))
			type = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("hinhthuc")));
		
		if (type == null)
			type = "";
		
		tdkmBean.setType(type.trim());
		

		String[] masp = request.getParameterValues("masp");
		String[] tensp = request.getParameterValues("tensp");
		String[] soluong = request.getParameterValues("soluong");

		List<ISanPham> spList = new ArrayList<ISanPham>();
		{
			if (masp != null)
			{
				for (int i = 0; i < masp.length; i++)
				{
					if (masp[i] != "")
					{
						SanPham sp = new SanPham("", masp[i], tensp[i], soluong[i], "");
						spList.add(sp);
					}
				}
			}
		}
		tdkmBean.setSpList(spList);
		
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if (action.equals("save"))
		{
			if (id == null || id.trim().length()==0)
			{
				if (!tdkmBean.save())
				{
					tdkmBean.setUserId(userId);
					tdkmBean.createRs();
					session.setAttribute("userId", userId);
					session.setAttribute("tdkmBean", tdkmBean);
					String nextJSP = "/AHF/pages/Center/ThayDoiKhuyenMaiNew.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					IThayDoiKhuyenMaiList obj = new ThayDoiKhuyenMaiList();
					obj.setUserId(userId);
					obj.createNhaphanglist("");
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);
					response.sendRedirect("/AHF/pages/Center/ThayDoiKhuyenMai.jsp");
				}
			} else
			{
				if (!(tdkmBean.edit()))
				{
					tdkmBean.setUserId(userId);
					tdkmBean.createRs();

					session.setAttribute("userId", userId);
					session.setAttribute("tdkmBean", tdkmBean);

					String nextJSP = "/AHF/pages/Center/ThayDoiKhuyenMaiUpdate.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					IThayDoiKhuyenMaiList obj = new ThayDoiKhuyenMaiList();
					obj.setUserId(userId);
					obj.createNhaphanglist("");

					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);

					response.sendRedirect("/AHF/pages/Center/ThayDoiKhuyenMai.jsp");
				}
			}
		} else
		{
			tdkmBean.createRs();
			session.setAttribute("userId", userId);
			session.setAttribute("tdkmBean", tdkmBean);
			String nextJSP;
			if (id == null)
			{
				nextJSP = "/AHF/pages/Center/ThayDoiKhuyenMaiNew.jsp";
			} else
			{
				nextJSP = "/AHF/pages/Center/ThayDoiKhuyenMaiUpdate.jsp";
			}
			response.sendRedirect(nextJSP);
		}
	}
}
