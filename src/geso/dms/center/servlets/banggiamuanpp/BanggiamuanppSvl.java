package geso.dms.center.servlets.banggiamuanpp;

import geso.dms.center.beans.banggiamuanpp.imp.BanggiamuanppList;
import geso.dms.center.beans.banggiamuanpp.imp.Banggiamuanpp;
import geso.dms.center.beans.banggiamuanpp.imp.Banggiamuanpp_npp;
import geso.dms.center.beans.banggiamuanpp.IBanggiamuanppList;
import geso.dms.center.beans.banggiamuanpp.IBanggiamuanpp;
import geso.dms.center.beans.banggiamuanpp.IBanggiamuanpp_npp;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BanggiamuanppSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
{
	static final long serialVersionUID = 1L;

	public BanggiamuanppSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();

		Utility util = new Utility();
		out = response.getWriter();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		System.out.println("User  : " + userId);

		if (userId.length() == 0)
			userId = (String) session.getAttribute("userId");

		String action = util.getAction(querystring);
		out.println(action);

		String bgId = util.getId(querystring);
		System.out.println("__action__" + action);
		IBanggiamuanppList obj;

		if (action.equals("delete"))
		{
			Delete(bgId);
			out.print(bgId);
		}
		if (action.equals("assign"))
		{
			IBanggiamuanpp_npp assign = new Banggiamuanpp_npp();
			assign.setId(bgId);
			assign.setUserId(userId);
			assign.init();
			session.setAttribute("assign", assign);
			String nextJSP = "/AHF/pages/Center/BangGiaMuaNppAssign.jsp";
			response.sendRedirect(nextJSP);

		} else if (action.equals("display"))
		{
			IBanggiamuanpp bgBean = new Banggiamuanpp();
			bgBean.setUserId(userId);
			bgBean.setId(bgId);
			bgBean.init();
			session.setAttribute("bgmuanppBean", bgBean);
			String nextJSP = "/AHF/pages/Center/BangGiaMuaNppDisplay.jsp";
			response.sendRedirect(nextJSP);
		} else
		{
			obj = new BanggiamuanppList();
			obj.setUserId(userId);
			session.setAttribute("obj", obj);

			String nextJSP = "/AHF/pages/Center/BangGiaMuaNpp.jsp";
			response.sendRedirect(nextJSP);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		IBanggiamuanppList obj;
		obj = new BanggiamuanppList();
		HttpSession session = request.getSession();
		Utility util = new Utility();
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if (action == null)
		{
			action = "";
		}
		out.println(action);

		if (action.equals("new"))
		{

			IBanggiamuanpp bgBean = (IBanggiamuanpp) new Banggiamuanpp();
			bgBean.setUserId(userId);

			session.setAttribute("bgmuanppBean", bgBean);

			String nextJSP = "/AHF/pages/Center/BangGiaMuaNppNew.jsp";
			response.sendRedirect(nextJSP);

		}
		if (action.equals("search"))
		{
			String search = getSearchQuery(request, obj);

			obj.init(search);

			obj.setUserId(userId);

			session.setAttribute("obj", obj);

			response.sendRedirect("/AHF/pages/Center/BangGiaMuaNpp.jsp");

		}
		if (action.equals("assign"))
		{

			IBanggiamuanpp_npp assign = new Banggiamuanpp_npp();

			assign.setId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id"))));

			assign.setKvId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kvId"))));

			assign.setUserId(userId);

			assign.init();

			session.setAttribute("assign", assign);

			String nextJSP = "/AHF/pages/Center/BangGiaMuaNppAssign.jsp";

			response.sendRedirect(nextJSP);

		}

		if (action.equals("save"))
		{

			IBanggiamuanpp_npp assign = new Banggiamuanpp_npp();

			assign.setId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id"))));

			assign.setUserId(userId);

			assign.init();

			assign.UpdateBgmuanpp(request);

			// IBanggiamuanppList obj = new BanggiamuanppList();

			obj.setUserId(userId);

			session.setAttribute("obj", obj);

			String nextJSP = "/AHF/pages/Center/BangGiaMuaNpp.jsp";

			response.sendRedirect(nextJSP);

		}

	}

	private String getSearchQuery(HttpServletRequest request, IBanggiamuanppList obj)
	{
		// PrintWriter out = response.getWriter();
		/* IBanggiamuanppList obj = new BanggiamuanppList(); */
		Utility util = new Utility();
		String bgTen = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("bgTen")));
		if (bgTen == null)
			bgTen = "";
		obj.setTen(bgTen);

		String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
		if (dvkdId == null)
			dvkdId = "";
		obj.setDvkdId(dvkdId);

		String kenhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")));
		if (kenhId == null)
			kenhId = "";
		obj.setKenhId(kenhId);

		String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
		if (trangthai == null)
			trangthai = "";

		if (trangthai.equals("2"))
			trangthai = "";

		obj.setTrangthai(trangthai);

		String query = "select a.pk_seq as id, a.ten as ten, a.trangthai as trangthai, d.ten as nguoitao, a.ngaytao as ngaytao, e.ten as nguoisua, a.ngaysua as ngaysua, b.donvikinhdoanh as dvkd, c.diengiai as kenh, c.pk_seq as kenhId from banggiamuanpp a, donvikinhdoanh b, kenhbanhang c, nhanvien d, nhanvien e where a.dvkd_fk=b.pk_seq and a.kenh_fk = c.pk_seq and a.nguoitao = d.pk_seq and a.nguoisua = e.pk_seq ";

		if (bgTen.length() > 0)
		{
			// geso.dms.distributor.util.Utility util = new
			// geso.dms.distributor.util.Utility();
			query = query + " and upper(dbo.ftBoDau(a.ten)) like upper(N'%" + util.replaceAEIOU(bgTen) + "%')";

		}

		if (dvkdId.length() > 0)
		{
			query = query + " and b.pk_seq = '" + dvkdId + "'";

		}

		if (kenhId.length() > 0)
		{
			query = query + " and c.pk_seq = '" + kenhId + "'";

		}

		if (trangthai.length() > 0)
		{
			query = query + " and a.trangthai = '" + trangthai + "'";

		}
		query = query + "  order by ten";
		return query;
	}

	private void Delete(String id)
	{
		dbutils db = new dbutils();

		db.update("delete from bgmuanpp_sanpham where bgmuanpp_fk='" + id + "'");
		db.update("delete from banggiamuanpp where pk_seq = '" + id + "'");

	}

}