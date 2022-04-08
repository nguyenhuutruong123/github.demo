package geso.dms.center.servlets.nhomnhanvien;



import geso.dms.center.beans.nhomnhanvien.INhomNhanVien;
import geso.dms.center.beans.nhomnhanvien.INhomNhanVienList;
import geso.dms.center.beans.nhomnhanvien.imp.NhomNhanVien;
import geso.dms.center.beans.nhomnhanvien.imp.NhomNhanVienList;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/NhomNhanVienUpdateSvl")
public class NhomNhanVienUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	public NhomNhanVienUpdateSvl()
	{
		super();
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		INhomNhanVien nhBean;

		Utility util = new Utility();
		HttpSession session = request.getSession();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		System.out.println(userId);
		if (userId.length() == 0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		String id = util.getId(querystring);
		nhBean = new NhomNhanVien(id);
		nhBean.init();
		nhBean.setUserId(userId);

		session.setAttribute("nhBean", nhBean);
		String nextJSP = "/AHF/pages/Center/NhomNhanVienUpdate.jsp";
		if(querystring.indexOf("display") > 0)
	    {
	    	nextJSP = "/AHF/pages/Center/NhomNhanVienDisplay.jsp";
	    }
		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();

		INhomNhanVien nhBean;
		this.out = response.getWriter();
		Utility util = new Utility();

		String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
		if (id == null)
		{
			nhBean = new NhomNhanVien("");
		} else
		{
			nhBean = new NhomNhanVien(id);
		}

		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		nhBean.setUserId(userId);

		String ten = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ten")));
		if (ten == null)
			ten = "";
		nhBean.setTen(ten);

		String ma = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ma")));
		if (ma == null)
			ma = "";
		nhBean.setMa(ma);
		
		String loaiNv = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaiNv")));
		if (loaiNv == null)
			loaiNv = "";
		nhBean.setLoaiNv(loaiNv);
		
		String loainvId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loainvId")));
		if (loainvId == null)
			loaiNv = "";
		nhBean.setLoainvId(loainvId);
		
		String kbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhId")));
		if (kbhId == null)
			kbhId = "";
		nhBean.setKbhId(kbhId);
		
		String spId="";
		
		String[] spIds = request.getParameterValues("spId");
		if (spIds != null) {
			int size = spIds.length;
			for (int i = 0; i < size; i++) {
				spId += spIds[i] + ",";
			}
			if (spId.length() > 0) {
				spId = spId.substring(0, spId.length() - 1);
			}
		}
		System.out.println("nhanviendachon: " + spId);
		nhBean.setSpId(spId);
		
		String nvselectId ="";
		String[] nvselectIds = request.getParameterValues("nvselectId");
		if (nvselectIds != null) {
			int size = nvselectIds.length;
			for (int i = 0; i < size; i++) {
				nvselectId += nvselectIds[i] + ",";
			}
			if (nvselectId.length() > 0) {
				nvselectId = nvselectId.substring(0, nvselectId.length() - 1);
			}
		}
		System.out.println("ddkd da chon: "+ nvselectId);
		nhBean.setNvselected(nvselectId);
		
		boolean error = false;

		if (ten.trim().length() == 0)
		{
			nhBean.setMsg("Vui lòng nhập tên nhóm");
			error = true;
		}
		if(spId.length() <= 0 && nvselectId.trim().length()<=0)
		{
			nhBean.setMsg("Vui lòng tick chọn nhân viên");
			error = true;
		}
		
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		String nextJSP = "";
		if (!error)
		{
			if (action.equals("save"))
			{
				INhomNhanVienList obj = new NhomNhanVienList();
				if (id == null || id.trim().length() == 0)
				{
					if (!nhBean.save())
					{
						session.setAttribute("nhBean", nhBean);
						nhBean.setUserId(userId);
						nextJSP = "/AHF/pages/Center/NhomNhanVienNew.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						obj.setUserId(userId);
						obj.init("");
						session.setAttribute("obj", obj);
						nextJSP = "/AHF/pages/Center/NhomNhanVien.jsp";
						response.sendRedirect(nextJSP);
					}
				} else
				{
					if (!nhBean.edit())
					{
						session.setAttribute("nhBean", nhBean);
						nhBean.setUserId(userId);
						nextJSP = "/AHF/pages/Center/NhomNhanVienUpdate.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						obj.setUserId(userId);
						obj.init("");
						session.setAttribute("obj", obj);
						nextJSP = "/AHF/pages/Center/NhomNhanVien.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			} else
			{
				nhBean.createRs();
				session.setAttribute("nhBean", nhBean);
				if (id == null)
				{
					nextJSP = "/AHF/pages/Center/NhomNhanVienNew.jsp";
				} else
				{
					nextJSP = "/AHF/pages/Center/NhomNhanVienUpdate.jsp";
				}
				response.sendRedirect(nextJSP);
			}

		} else
		{
			nhBean.createRs();
			session.setAttribute("nhBean", nhBean);
			if (id == null)
			{
				nextJSP = "/AHF/pages/Center/NhomNhanVienNew.jsp";
			} else
			{
				nextJSP = "/AHF/pages/Center/NhomNhanVienUpdate.jsp";
			}
			response.sendRedirect(nextJSP);
		}
	}
	
}
