package geso.dms.center.servlets.mokhoaso;

import geso.dms.center.beans.mokhoaso.IMokhoaso;
import geso.dms.center.beans.mokhoaso.imp.Mokhoaso;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MokhoasoSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public MokhoasoSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		Utility util = new Utility();

		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String querystring = request.getQueryString();

		String userId = util.getUserId(querystring);
		out.println(userId);

		if (userId.length() == 0)
			userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));

		session.setAttribute("userId", userId);
		IMokhoaso mksBean = new Mokhoaso();
		mksBean.setVungId("");
		mksBean.setKhuvucId("");
		mksBean.setNppId("");
		mksBean.init();

		session.setAttribute("mksBean", mksBean);
		String nextJSP = "/AHF/pages/Center/Mokhoaso.jsp";
		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		Utility util = new Utility();

		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();

		String userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
		String vungId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"));
		String kenhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId"));
		String kvId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kvId"));
		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
		String ngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngay"));
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));

		String[] nppChon = request.getParameterValues("nppChon");
	
		
		if (vungId == null)
			vungId = "";
		if (kvId == null)
			kvId = "";
		if (nppId == null)
			nppId = "";
		if (kenhId == null)
			kenhId = "";
		
		IMokhoaso mksBean = new Mokhoaso();
		mksBean.setVungId(vungId);
		mksBean.setKhuvucId(kvId);
		mksBean.setKenhId(kenhId);
		mksBean.setNppId(nppId);
		mksBean.setNgay(ngay);
		mksBean.setUserId(userId);
		mksBean.setNppChonIds( nppChon );
		
		
		mksBean.init();

		if (action.equals("open"))
		{
			if (nppId.length() > 0)
			{
				mksBean.setMsg(mksBean.MoKhoaSoNgay());
			} else
			{
				mksBean.setMsg("Vui lòng chọn Nhà phân phối");
			}
		} else if (action.equals("close"))
		{
			if (nppId.length() > 0)
			{
				mksBean.setMsg(mksBean.KhoaSoNgay("3"));
			}
			else
				mksBean.setMsg("Không thể khóa sổ. Vui lòng chọn ngày khóa sổ");
		}else if (action.equals("KhoaSoThang"))
		{
			if(nppChon != null && nppChon.length > 0)
			{
				mksBean.setMsg(mksBean.KhoaSoThang("3"));
			}
			else
				mksBean.setMsg("Không thể khóa sổ tháng. Vui lòng chọn npp muốn khóa sổ");
		}
		else if (action.equals("khoasotudong"))
		{
			if(nppChon != null && nppChon.length > 0)
			{
				mksBean.setMsg(mo_dong_khoasotudong(nppChon,KHOA_SO_TU_DONG) );
			}
			else
				mksBean.setMsg("Không thể thao tác. Vui lòng chọn npp");
		}
		else if (action.equals("ngung_khoasotudong"))
		{
			if(nppChon != null && nppChon.length > 0)
			{
				mksBean.setMsg( mo_dong_khoasotudong(nppChon,NGUNG_KHOA_SO_TU_DONG) );
			}
			else
				mksBean.setMsg("Không thể thao tác. Vui lòng chọn npp");
		}
		mksBean.init();
		session.setAttribute("mksBean", mksBean);
		String nextJSP = "/AHF/pages/Center/Mokhoaso.jsp";
		response.sendRedirect(nextJSP);

	}
	static final int KHOA_SO_TU_DONG = 1;
	static final int NGUNG_KHOA_SO_TU_DONG = 0;
	public String mo_dong_khoasotudong(String[]nppChonIds,int thaothac)
	{
		dbutils db = new dbutils();
		try
		{
			String dsNPP = "";
			
			if(nppChonIds != null)
			{
				for(int i = 0 ; i < nppChonIds.length ; i ++)
					dsNPP += dsNPP.length() > 0 ? "," + nppChonIds[i] : nppChonIds[i];
			}
			if(dsNPP.length() <=0)
			{
				Utility.rollback_and_shutdown(db);
				return "Vui lòng chọn NPP ";
			}
			
			String query = " update nhaphanphoi set khoasotudong = "+thaothac+" where pk_seq in (" +dsNPP+") ";
			if(!db.update(query))
			{
				Utility.rollback_and_shutdown(db);
				return "Không thể cập nhật!";
			}
			
			Utility.commit_and_shutdown(db);
			return "Thành công!";
		}
		catch (Exception e) {
			Utility.rollback_and_shutdown(db);
			e.printStackTrace();
			return "Exception:" + e.getMessage();
		}
	}

}
