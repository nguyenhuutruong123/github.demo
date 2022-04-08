package geso.dms.distributor.servlets.banggiablnpp;

import geso.dms.distributor.beans.banggiablnpp.*;
import geso.dms.distributor.beans.banggiablnpp.imp.*;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BanggiablUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	PrintWriter out;

	public BanggiablUpdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		  Utility util = new Utility();
		 
			IBanggiablnpp bgblBean;
			this.out = response.getWriter();

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);

			out.println(userId);

			if (userId.length() == 0)
				userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

			String id = util.getId(querystring);

			bgblBean = new Banggiablnpp(id);
			bgblBean.setUserId(userId);
			bgblBean.init();

			session.setAttribute("bgblBean", bgblBean);
			String nextJSP = "/AHF/pages/Distributor/BgBanLeNppUpdate.jsp";
			if(querystring.contains("display"))
				nextJSP = "/AHF/pages/Distributor/BgBanLeNppDisplay.jsp";
			response.sendRedirect(nextJSP);
		}
	

	@SuppressWarnings("null")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
	    Utility util = new Utility();
		String param = "";


		{

			IBanggiablnpp bgblBean;
			List<ISanpham> spList;

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			// HttpSession session = request.getSession();
			Utility ult = new Utility();
			this.out = response.getWriter();

			String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
			if (id == null)
			{
				bgblBean = new Banggiablnpp("");
			} else
			{
				bgblBean = new Banggiablnpp(id);
			}

			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
			bgblBean.setUserId(userId);

			String bgblTen = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("bgblTen")));
			if (bgblTen == null)
				bgblTen = "";
			bgblBean.setTenbanggia(bgblTen);

			String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
			if (nppId == null)
				nppId = "";
			bgblBean.setNppId(nppId);

			String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdTen")));
			if (dvkdId == null)
				dvkdId = "";
			bgblBean.setDvkdId(dvkdId);
			String[] spId = request.getParameterValues("spId");
			String[] spMa = request.getParameterValues("spMa");
			String[] spTen = request.getParameterValues("spTen");
			String[] spGiablc = request.getParameterValues("spGiablc");
			String[] spGiabanleNpp = request.getParameterValues("spGiabanleNpp");
			String[] giathung = request.getParameterValues("giathung");
			String[] quycach = request.getParameterValues("quycach");
			// Tao List San Pham
			spList = new ArrayList<ISanpham>();

			ISanpham sanpham = null;
			String[] param1 = new String[5];
			int m = 0;
			if (spId != null)
			{
				while (m < spId.length)
				{
					if (util.antiSQLInspection(spGiablc[m]) == null)
						spGiablc[m] = "";
					if (util.antiSQLInspection(spGiabanleNpp[m]) == null)
						spGiabanleNpp[m] = "";
					param1[0] = util.antiSQLInspection(spId[m]);
					param1[1] = util.antiSQLInspection(spMa[m]);
					param1[2] = util.antiSQLInspection(spTen[m]);
					param1[3] = util.antiSQLInspection(spGiablc[m].replaceAll(",", ""));
					param1[4] = util.antiSQLInspection(spGiabanleNpp[m].replaceAll(",", ""));
					sanpham = new Sanpham(param1);
					sanpham.setGiathung("0");
					sanpham.setQuycach("0");
					spList.add(sanpham);
					m++;
				}
			}
			String ngaysua = getDateTime();
			bgblBean.setNgaysua(ngaysua);

			boolean error = false;

			if (bgblTen.trim().length() == 0)
			{
				bgblBean.setMessage("Vui Long Nhap Ten Bang Gia");
				error = true;
			}

			if (dvkdId.trim().length() == 0)
			{
				bgblBean.setMessage("Vui Long Chon Don Vi Kinh Doanh");
				error = true;
			}

			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			if (!error)
			{
				if (action.equals("save"))
				{
					if (id == null)
					{
						if (!(bgblBean.CreateBgbl(spId, spGiablc, spGiabanleNpp)))
						{
							bgblBean.createRS();
							if (spList.size() > 0)
								bgblBean.setSpList(spList);
							session.setAttribute("bgblBean", bgblBean);
							String nextJSP = "/AHF/pages/Distributor/BgBanLeNppNew.jsp";
							response.sendRedirect(nextJSP);
						} else
						{
							IBanggiablnppList obj = new BanggiablnppList();
							obj.setUserId(userId);
							obj.init("");
							session.setAttribute("obj", obj);
							// ult.initSanPhamSearch(request,obj.getNppId());
							String nextJSP = "/AHF/pages/Distributor/BangGiaBanLeNpp.jsp";
							response.sendRedirect(nextJSP);
						}

					} else
					{
						if (!(bgblBean.UpdateBgbl(spId, spGiablc, spGiabanleNpp,giathung,quycach)))
						{
							bgblBean.init();
							if (spList.size() > 0)
								bgblBean.setSpList(spList);

							session.setAttribute("bgblBean", bgblBean);
							String nextJSP = "/AHF/pages/Distributor/BgBanLeNppUpdate.jsp";
							response.sendRedirect(nextJSP);
						} else
						{
							IBanggiablnppList obj = new BanggiablnppList();
							obj.setUserId(userId);
							obj.init("");
							session.setAttribute("obj", obj);
							// ult.initSanPhamSearch(request,obj.getNppId());
							String nextJSP = "/AHF/pages/Distributor/BangGiaBanLeNpp.jsp";
							response.sendRedirect(nextJSP);
						}
					}
				} else
				{
					bgblBean.createRS();
					session.setAttribute("bgblBean", bgblBean);

					String nextJSP;
					if (id == null)
					{
						nextJSP = "/AHF/pages/Distributor/BgBanLeNppNew.jsp";
					} else
					{
						nextJSP = "/AHF/pages/Distributor/BgBanLeNppUpdate.jsp";
					}
					response.sendRedirect(nextJSP);
				}
			} else
			{
				bgblBean.createRS();
				session.setAttribute("bgblBean", bgblBean);

				String nextJSP;
				if (id == null)
				{
					nextJSP = "/AHF/pages/Distributor/BgBanLeNppNew.jsp";
				} else
				{
					nextJSP = "/AHF/pages/Distributor/BgBanLeNppUpdate.jsp";
				}
				response.sendRedirect(nextJSP);

			}
		}
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

}
