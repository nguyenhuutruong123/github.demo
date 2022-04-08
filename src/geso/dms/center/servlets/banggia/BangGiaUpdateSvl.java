package geso.dms.center.servlets.banggia;

import geso.dms.center.beans.banggia.IBangGia;
import geso.dms.center.beans.banggia.IBangGiaList;
import geso.dms.center.beans.banggia.IBangGia_Sp;
import geso.dms.center.beans.banggia.imp.BangGia;
import geso.dms.center.beans.banggia.imp.BangGiaList;
import geso.dms.center.beans.banggia.imp.BangGia_Sp;
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

@WebServlet("/BangGiaUpdateSvl")
public class BangGiaUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	public BangGiaUpdateSvl()
	{
		super();
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IBangGia csxBean;

		this.out = response.getWriter();
		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		String loai="";
		if (loai.length() == 0)
			loai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loai"));

		out.println(userId);

		if (userId.length() == 0)
			userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));

		String id = util.getId(querystring);

		csxBean = new BangGia(id);
		csxBean.setView( geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view") == null ? "" : request.getParameter("view"))    );
		csxBean.setId(id);
		csxBean.setUserId(userId);
		csxBean.setLoai(loai);
		csxBean.init();
		session.setAttribute("csxBean", csxBean);

		String nextJSP = "/AHF/pages/Center/BangGiaUpdate.jsp";
		if (querystring.indexOf("display") >= 0)
		{
			nextJSP = "/AHF/pages/Center/BangGiaDisplay.jsp";
		}

		response.sendRedirect(nextJSP);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IBangGia csxBean;
		
		Utility util = new Utility();
		String id = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id"));
		if (id == null)
		{
			csxBean = new BangGia();
		} else
		{
			csxBean = new BangGia(id);
		}
		csxBean.setView( geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view") == null ? "" : request.getParameter("view"))    );
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		csxBean.setUserId(userId);
		
		String ten = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ten")));
		if (ten == null)
			ten = "";
		csxBean.setTen(ten);
		
		String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
		if (dvkdId == null)
			dvkdId = "";
		csxBean.setDvkdId(dvkdId);
		
		String kbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhId")));
		System.out.println(kbhId);
		if (kbhId == null)
			kbhId = "";
		csxBean.setKbhId(kbhId);

		String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
		if (tungay == null)
			tungay = "";
		csxBean.setTuNgay(tungay);
		
		
		String chietkhau = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chietkhau")));
		if (chietkhau == null)
			chietkhau = "";
		csxBean.setChietKhau(chietkhau.trim().length()<=0?"0":chietkhau.replaceAll(",","") );
		
		String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
		if (trangthai == null)
			trangthai = "0";
		csxBean.setTrangthai(trangthai);
		 String loai="";
		    if (loai.length() == 0)
		    	loai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loai")));
		   csxBean.setLoai(loai);
		
		String[] nppId = request.getParameterValues("nppId");
		String[] nppIdCks = request.getParameterValues("nppIdck");
		String[] nppChietkhaus = request.getParameterValues("chietkhauNPP");
		String nppIds = this.Doisangchuoi(nppId);
		csxBean.setNppId(nppIds);
		
		csxBean.setNppIdCks(nppIdCks);
		csxBean.setNppChietKhaus(nppChietkhaus);
		
		String[] spId = request.getParameterValues("spId");
		String[] spMa = request.getParameterValues("spMa");
		String[] spTen = request.getParameterValues("spTen");
		String[] giaban = request.getParameterValues("giaban");
		String[] donvi = request.getParameterValues("donvi");
		String[] chonban = request.getParameterValues("chonban");
		String [] spchietkhau=request.getParameterValues("spchietkhauNPP");
		String spChonbanIds = "";
		if (chonban != null)
		{
			for (int i = 0; i < chonban.length; i++)
			{
				spChonbanIds += chonban[i] + ",";
			}
			
			if (spChonbanIds.trim().length() > 0)
			{
				spChonbanIds = spChonbanIds.substring(0, spChonbanIds.length() - 1);
			}
		}
		
		List<IBangGia_Sp> spList = new ArrayList<IBangGia_Sp>();
		
		if (spId != null)
		{
			for (int i = 0; i < spId.length; i++)
			{
				IBangGia_Sp sp = new BangGia_Sp();
				sp.setIdsp(spId[i]);
				sp.setMasp(spMa[i]);
				sp.setTensp(spTen[i]);
				sp.setGiaban(giaban[i].replaceAll(",", ""));
				sp.setDonvi(donvi[i]);
				sp.setSpchietkhau(spchietkhau[i]);
				if (spChonbanIds.indexOf(spId[i]) >= 0)
					sp.setChonban("1");
				spList.add(sp);
			}
		}
		csxBean.setSpList(spList);
		
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if (action.equals("save"))
		{
			if (id == null || id.trim().length() <= 0)
			{
				if(loai.equals("0"))
				{
					if (!(csxBean.createBanggia_B2B()))
					{
						csxBean.createRs();
						csxBean.setLoai(loai);
						session.setAttribute("csxBean", csxBean);
						session.setAttribute("userId", userId);
						
						String nextJSP = "/AHF/pages/Center/BangGiaNew.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						IBangGiaList obj = new BangGiaList();
						obj.setUserId(userId);
						obj.setLoai(loai);
						obj.init("");
						
						session.setAttribute("obj", obj);
						
						String nextJSP = "/AHF/pages/Center/BangGia.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				else
				{
					if (!(csxBean.createBanggia()))
					{
						csxBean.createRs();
						
						session.setAttribute("csxBean", csxBean);
						session.setAttribute("userId", userId);
						
						String nextJSP = "/AHF/pages/Center/BangGiaNew.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						IBangGiaList obj = new BangGiaList();
						obj.setUserId(userId);
						
						obj.init("");
						
						session.setAttribute("obj", obj);
						
						String nextJSP = "/AHF/pages/Center/BangGia.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				
			} else
			{
				if(loai.equals("0"))
				{
					if (!(csxBean.updateBanggia_B2B()))
					{
						csxBean.createRs();
						csxBean.setLoai(loai);
						session.setAttribute("csxBean", csxBean);
						session.setAttribute("userId", userId);
						
						String nextJSP = "/AHF/pages/Center/BangGiaUpdate.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						IBangGiaList obj = new BangGiaList();
						obj.setView( geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view") == null ? "" : request.getParameter("view"))    );
						obj.setUserId(userId);
						obj.setLoai(loai);
						obj.init("");
						
						session.setAttribute("obj", obj);
						
						String nextJSP = "/AHF/pages/Center/BangGia.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				else
				{
					if (!(csxBean.updateBanggia()))
					{
						csxBean.createRs();
						session.setAttribute("csxBean", csxBean);
						session.setAttribute("userId", userId);
						
						String nextJSP = "/AHF/pages/Center/BangGiaUpdate.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						IBangGiaList obj = new BangGiaList();
						obj.setView( geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view") == null ? "" : request.getParameter("view"))    );
						obj.setUserId(userId);
						obj.init("");
						
						session.setAttribute("obj", obj);
						
						String nextJSP = "/AHF/pages/Center/BangGia.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				
			}
		} else
		{
			if (action.equals("changeKhachHang"))
				csxBean.setSpList(new ArrayList<IBangGia_Sp>());
			
			csxBean.createRs();
			System.out.println("loai la : "+csxBean.getLoai());
			session.setAttribute("userId", userId);
			session.setAttribute("csxBean", csxBean);
			String nextJSP = "/AHF/pages/Center/BangGiaNew.jsp";
			if (id != null)
			{
				nextJSP = "/AHF/pages/Center/BangGiaUpdate.jsp";
			}
			response.sendRedirect(nextJSP);
		}
	}
	
	private String Doisangchuoi(String[] checknpp)
	{
		// TODO Auto-generated method stub
		String str = "";
		if (checknpp != null)
		{
			for (int i = 0; i < checknpp.length; i++)
			{
				if (i == 0)
				{
					str = checknpp[i];
				} else
				{
					str = str + "," + checknpp[i];
				}
			}
		}
		return str;
		
	}
	
}
