package geso.dms.distributor.servlets.dontratrungbaynpp;

import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.dontratrungbaynpp.IDonTraTrungBayNpp;
import geso.dms.distributor.beans.dontratrungbaynpp.IDonTraTrungBayNppList;
import geso.dms.distributor.beans.dontratrungbaynpp.imp.DonTraTrungBayNpp;
import geso.dms.distributor.beans.dontratrungbaynpp.imp.DonTraTrungBayNppList;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DonTraTrungBayNppUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
	public DonTraTrungBayNppUpdateSvl()
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
			IDonTraTrungBayNpp lsxBean = new DonTraTrungBayNpp(id);
			lsxBean.setUserId(userId);
			
			String nextJSP = "";
			
			lsxBean.init();
			session.setAttribute("khoxuat", lsxBean.getKhoXuatId());
			session.setAttribute("kenhId", lsxBean.getKbhId());
			session.setAttribute("nppId", lsxBean.getNppId());
			
			if (!querystring.contains("display"))
				nextJSP = "/AHF/pages/Distributor/DonTraTrungBayNppUpdate.jsp";
			else
				nextJSP = "/AHF/pages/Distributor/DonTraTrungBayNppDisplay.jsp";
			
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
			IDonTraTrungBayNpp lsxBean;
			
			Utility util = new Utility();
			String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
			if (id == null)
			{
				lsxBean = new DonTraTrungBayNpp("");
			} else
			{
				
				lsxBean = new DonTraTrungBayNpp(id);
			}
			
			lsxBean.setUserId(userId);
			
			String ngayyeucau = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaychuyen")));
			if (ngayyeucau == null || ngayyeucau.trim().length() <= 0)
				ngayyeucau = getDateTime();
			lsxBean.setNgayyeucau(ngayyeucau);
						
			String hopdong_fk = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("hopdong_fk")));
			if (hopdong_fk == null || ngayyeucau.trim().length() <= 0)
			{	
				hopdong_fk = null;
			}

			lsxBean.setHopdong_fk(hopdong_fk);
			session.setAttribute("hopdong_fk", lsxBean.getHopdong_fk());
			
			String ghichu = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ghichu")));
			if (ghichu == null)
				ghichu = "";
			lsxBean.setGhichu(ghichu);
			
			String TraNguyenDon = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("TraNguyenDon")));
			if (TraNguyenDon == null)
				TraNguyenDon = "0";
			else
				TraNguyenDon ="1";
			lsxBean.setTraNguyenDon(TraNguyenDon);
			
			
			String donhangId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("donhangId")));
			if (donhangId == null)
				donhangId = "";
			lsxBean.setDonhangId(donhangId);
			
			String hdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("hdId")));
			if (hdId == null)
				hdId = "";
			lsxBean.setHdId(hdId);
			
			String so = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("so")));
			if (so == null)
				so = "";
			lsxBean.setSo(so);
			
			String ddkd = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdId")));
			if (ddkd == null)
				ddkd = "";
			lsxBean.setddkdId(ddkd);
			session.setAttribute("ddkd", lsxBean.getddkdId());
			
			String khoxuatId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khoxuatId")));
			if (khoxuatId == null)
				khoxuatId = "";
			lsxBean.setKhoXuatId(khoxuatId);
			session.setAttribute("khoxuat", lsxBean.getKhoXuatId());
			
			String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
			if (nppId == null)
				nppId = "";
			lsxBean.setNppId(nppId);
			
			String khId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khId")));
			if (khId == null)
				khId = "";
			lsxBean.setKhId(khId);
			
			String dtId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dtId") == null ? "" : request.getParameter("dtId")));
			lsxBean.setDtId(dtId);
			
			String kbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhId")));
			if (kbhId == null)
				kbhId = "";
			lsxBean.setKbhId(kbhId);
			session.setAttribute("kenhId", lsxBean.getKbhId());
			
			String doituong = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("doituong") == null ? "" : request.getParameter("doituong")));
			lsxBean.setDoituong(doituong);
			
			String sohoadon = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sohoadon") == null ? "" : request.getParameter("sohoadon")));
			lsxBean.setSoHoaDon(sohoadon);
			
			String kyhieu = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kyhieu") == null ? "" : request.getParameter("kyhieu")));
			lsxBean.setKyHieu(kyhieu);
			
			String ngayhoadon = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngayhoadon") == null ? "" : request.getParameter("ngayhoadon")));
			lsxBean.setNgayHoaDon(ngayhoadon);
			
			String sohs = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("so") == null ? "" : request.getParameter("so")));
			lsxBean.setSo(sohs);
			
			String[] spMa = request.getParameterValues("spMa");
			lsxBean.setSpMa(spMa);
			
			String[] spTen = request.getParameterValues("spTen");
			lsxBean.setSpTen(spTen);
			
			String[] dvt = request.getParameterValues("donvi");
			lsxBean.setSpDonvi(dvt);
			
			String[] soluong = request.getParameterValues("soluong");
			lsxBean.setSpSoluong(soluong);
			
			String[] tonkho = request.getParameterValues("tonkho");
			lsxBean.setSpTonkho(tonkho);
			
			String[] dongia = request.getParameterValues("dongia");
			lsxBean.setSpGianhap(dongia);
			
			String[] solo = request.getParameterValues("solo");
			lsxBean.setSpSolo(solo);
			
			String[] ngayhethan = request.getParameterValues("ngayhethan");
			lsxBean.setSpNgayHetHan(ngayhethan);
			
			String[] spGhiChu = request.getParameterValues("spGhiChu");
			lsxBean.setSpGhiChu(spGhiChu);
			
			String[] spVat = request.getParameterValues("spVat");
			lsxBean.setSpVat(spVat);
			
			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			
			if (action.equals("save"))
			{
				if (id == null)
				{
					if (!lsxBean.createNK())
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
						String nextJSP = "/AHF/pages/Distributor/DonTraTrungBayNppNew.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						IDonTraTrungBayNppList obj = new DonTraTrungBayNppList();
						obj.setUserId(userId);
						obj.init("");
						session.setAttribute("obj", obj);
						session.setAttribute("userId", userId);
						
						response.sendRedirect("/AHF/pages/Distributor/DonTraTrungBayNpp.jsp");
					}
				} else
				{
					if (!lsxBean.updateNK())
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
						String nextJSP = "/AHF/pages/Distributor/DonTraTrungBayNppUpdate.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						IDonTraTrungBayNppList obj = new DonTraTrungBayNppList();
						obj.setUserId(userId);
						obj.init("");
						session.setAttribute("obj", obj);
						String nextJSP = "/AHF/pages/Distributor/DonTraTrungBayNpp.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			} else
			{
				lsxBean.createRs();
				
				session.setAttribute("lsxBean", lsxBean);
				
				String nextJSP = "";
				
				nextJSP = "/AHF/pages/Distributor/DonTraTrungBayNppNew.jsp";
				if (id != null)
					nextJSP = "/AHF/pages/Distributor/DonTraTrungBayNppUpdate.jsp";
				
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
