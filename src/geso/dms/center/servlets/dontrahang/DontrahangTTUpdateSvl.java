package geso.dms.center.servlets.dontrahang;

import geso.dms.center.beans.dontrahang.IDontrahang;
import geso.dms.center.beans.dontrahang.IDontrahangList;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import geso.dms.center.beans.dontrahang.imp.Dontrahang;
import geso.dms.center.beans.dontrahang.imp.DontrahangList;
import geso.dms.center.db.sql.db_Sync;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.PrintWriter;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.http.HttpSession;


public class DontrahangTTUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	public DontrahangTTUpdateSvl() 
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
			IDontrahang lsxBean = new Dontrahang(id);
			lsxBean.setUserId(userId);

			String nextJSP = "";

			lsxBean.init();

			if (!querystring.contains("display"))
				nextJSP = "/AHF/pages/Center/DonTraHangUpdate.jsp";
			else
				nextJSP = "/AHF/pages/Center/DonTraHangDisplay.jsp";

			session.setAttribute("lsxBean", lsxBean);

			session.setAttribute("kenhId", lsxBean.getKbhId());
			session.setAttribute("kbhId", lsxBean.getKbhId());
			session.setAttribute("khoId", lsxBean.getKhoXuatId());
			session.setAttribute("nppId",lsxBean.getNppId() );

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
			IDontrahang lsxBean;

			Utility util = new Utility();
			String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
			if (id == null)
			{
				lsxBean = new Dontrahang("");
			} else
			{

				lsxBean = new Dontrahang(id);
			}

			lsxBean.setUserId(userId);

			String ngayyeucau = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaychuyen")));
			if (ngayyeucau == null || ngayyeucau.trim().length() <= 0)
				ngayyeucau = getDateTime();
			lsxBean.setNgayyeucau(ngayyeucau);

			String ghichu = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ghichu")));
			if (ghichu == null)
				ghichu = "";
			lsxBean.setGhichu(ghichu);


			String sochungtu = util.antiSQLInspection( geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sochungtu")==null? "":request.getParameter("sochungtu")) );
			lsxBean.setSoChungTu(sochungtu);


			String khoxuatId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khoxuatId")));
			if (khoxuatId == null)
				khoxuatId = "";
			lsxBean.setKhoXuatId(khoxuatId);


			String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
			if (nppId == null)
				nppId = "";
			lsxBean.setNppId(nppId);

			String khId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khId")));
			if (khId == null)
				khId = "";
			lsxBean.setKhId(khId);

			String kbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhId")));
			if (kbhId == null)
				kbhId = "";
			lsxBean.setKbhId(kbhId);

			session.setAttribute("kenhId", lsxBean.getKbhId());
			session.setAttribute("kbhId", lsxBean.getKbhId());
			session.setAttribute("khoId", lsxBean.getKhoXuatId());
			session.setAttribute("nppId", geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));


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

			String[] spVat = request.getParameterValues("spVat");
			lsxBean.setSpVat(spVat);


			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));

			if(spMa != null && action.equals("save") )  //LUU LAI THONG TIN NGUOI DUNG NHAP
			{
				Hashtable<String, String> sanpham_soluong = new Hashtable<String, String>();
				for(int i = 0; i < spMa.length; i++ )
				{
					String temID = spMa[i];
					String[] spSOLO = request.getParameterValues(temID + "_spSOLO");

					String[] spNgayHetHan = request.getParameterValues(temID + "_spNGAYHETHAN");

					String[] soLUONGXUAT = request.getParameterValues(temID + "_spSOLUONG");

					if(soLUONGXUAT != null)
					{
						for(int j = 0; j < soLUONGXUAT.length; j++ )
						{
							if(soLUONGXUAT[j] != null && soLUONGXUAT[j].trim().length() > 0)
							{
								sanpham_soluong.put(spMa[i] + "__" + spSOLO[j]+ "__" + spNgayHetHan[j], soLUONGXUAT[j].replaceAll(",", "") );
							}
						}
					}
				}

				lsxBean.setSanpham_Soluong(sanpham_soluong);
			}
			
			String ischot = util.antiSQLInspection( geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ischot")==null? "0":request.getParameter("ischot")) );
			
			
			if (action.equals("save"))
			{
				if (id == null)
				{
					if (!lsxBean.createNK())
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
						String nextJSP = "/AHF/pages/Center/DonTraHangNew.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						IDontrahangList obj = new DontrahangList();
						obj.setUserId(userId);
						obj.init("");
						session.setAttribute("obj", obj);
						session.setAttribute("userId", userId);

						response.sendRedirect("/AHF/pages/Center/DonTraHang.jsp");
					}
				} else
				{
					
					if (!lsxBean.updateNK(ischot))
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
						String nextJSP = "/AHF/pages/Center/DonTraHangUpdate.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						IDontrahangList obj = new DontrahangList();
						obj.setUserId(userId);
						obj.init("");
						session.setAttribute("obj", obj);
						String nextJSP = "/AHF/pages/Center/DonTraHang.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			} else
			{
				lsxBean.createRs();
				session.setAttribute("lsxBean", lsxBean);
				String nextJSP = "";
				nextJSP = "/AHF/pages/Center/DonTraHangNew.jsp";
				if (id != null)
					nextJSP = "/AHF/pages/Center/DonTraHangUpdate.jsp";

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
