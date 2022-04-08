package geso.dms.center.servlets.donmuahang;

import geso.dms.center.beans.donmuahang.ISanPhamTraKM;
import geso.dms.center.beans.donmuahang.ITaodonhangKm;
import geso.dms.center.beans.donmuahang.imp.SanPhamTraKm;
import geso.dms.center.beans.donmuahang.imp.TaodonhangKm;

import geso.dms.center.util.Utility;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/TaoDonHangTraKhuyenMaiSvl")
public class TaoDonHangTraKhuyenMaiSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public TaoDonHangTraKhuyenMaiSvl()
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

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		System.out.println(userId);

		if (userId.length() == 0)
			userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));

		String action = util.getAction(querystring);
		System.out.println(action);
		ITaodonhangKm dhkm = new TaodonhangKm();
		dhkm.Init();
		session.setAttribute("dhkm", dhkm);

		String nextJSP = "/AHF/pages/Center/Taodonhangtrakhuyenmai.jsp";
		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Utility util = new Utility();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();
		ITaodonhangKm dhkm = new TaodonhangKm();
		String msg = "";
		String userId = (String) session.getAttribute("userId");
		String action = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));
		String thang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang")));
		String nam = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nam")));
		String ctkmchon = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("schemechon"));
		dhkm.setUserId(userId);
		dhkm.setCTKMChon(ctkmchon);
		String[] scheme = request.getParameterValues("scheme");
		String[] spid = request.getParameterValues("spid");
		String[] spma = request.getParameterValues("spma");
		String[] spten = request.getParameterValues("spten");
		String[] idsptt = request.getParameterValues("idsptt");
		String[] ctkmid = request.getParameterValues("ctkmid");
		String[] soluongtt = request.getParameterValues("soluongtt");
		String[] kbhid = request.getParameterValues("kbhid");

		String[] soluong = request.getParameterValues("soluong");

		String[] nppid = request.getParameterValues("nppid");
		String[] nppten = request.getParameterValues("nppten");

		List<ISanPhamTraKM> listsp1 = new ArrayList<ISanPhamTraKM>();

		if (scheme != null)
		{
			for (int i = 0; i < scheme.length; i++)
			{
				ISanPhamTraKM sp = new SanPhamTraKm();
				sp.setScheme(scheme[i]);
				sp.setctkm(ctkmid[i]);
				sp.setNPPId(nppid[i]);
				sp.setNPPTen(nppten[i]);
				sp.setSpId(spid[i]);
				sp.setspten(spten[i]);
				sp.setspma(spma[i]);
				sp.setKBHId(kbhid[i]);

				sp.setSpIdTT(idsptt[i]);
				if (idsptt[i].trim().equals(""))
				{
					sp.setSpIdTT(spid[i]);
				}
				float soluongtt1 = 1;
				try
				{
					soluongtt1 = Float.parseFloat(soluongtt[i].replaceAll(",", ""));
				} catch (Exception er)
				{
					msg = "Mã sản phẩm : " + spma[i] + " của chương trình khuyến mãi : " + scheme[i] + " không hợp lệ vui lòng nhập lại";
					er.printStackTrace();
				}
				float soluong1 = 0;
				try
				{
					soluong1 = Float.parseFloat(soluong[i].replace(",", ""));
				} catch (Exception er)
				{
					msg = "Mã sản phẩm : " + spma[i] + " của chương trình khuyến mãi : " + scheme[i] + " không hợp lệ vui lòng nhập lại";
					er.printStackTrace();
				}

				sp.setsoluongtt(soluongtt1);
				listsp1.add(sp);

			}
		}
		dhkm.setListSanpham(listsp1);
		if (thang == null)
		{
			thang = "0";
		}
		dhkm.setNam(nam);
		dhkm.setThang(thang);
		System.out.println("Thang Nek " + thang);
		try
		{
			if (Double.parseDouble(thang) > 0 && Double.parseDouble(nam) > 0 && msg.length() == 0)
			{
				if (action.equals("thuchien"))
				{
					if (dhkm.ThucHien())
					{
						msg = "Thực hiện thành công";
					}
				} else
				{
					dhkm.LoadSpKm();
					dhkm.setMsg(msg);
				}
			}
		} catch (Exception err)
		{
			msg = err.toString();
			err.printStackTrace();
		}
		if (!msg.equals(""))
		{
			dhkm.setMsg(msg);
		}
		dhkm.Init();
		session.setAttribute("dhkm", dhkm);
		String nextJSP = "/AHF/pages/Center/Taodonhangtrakhuyenmai.jsp";
		response.sendRedirect(nextJSP);
	}
}
