package geso.dms.center.servlets.ttpp;

import geso.dms.center.beans.ttpp.ITrungtamphanphoi;
import geso.dms.center.beans.ttpp.ITrungtamphanphoiList;
import geso.dms.center.beans.ttpp.imp.Trungtamphanphoi;
import geso.dms.center.beans.ttpp.imp.TrungtamphanphoiList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TrungtamphanphoiSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public TrungtamphanphoiSvl()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		Utility util = new Utility();
		HttpSession session;
		ITrungtamphanphoiList obj;
		String userId;
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		session = request.getSession();
		PrintWriter out = response.getWriter();
		obj = new TrungtamphanphoiList();
		
		String querystring = request.getQueryString();
		userId = util.getUserId(querystring);
		
		if (userId.length() == 0)
			userId = Utility.antiSQLInspection(request.getParameter("userId"));
		
		String action = util.getAction(querystring);
		out.println(action);
		
		String dvkdId = util.getId(querystring);
		out.println(dvkdId);
		String msg = "";
		
		String param = "";
	    
		if (Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		
		if (Utility.CheckRuleUser( session,  request, response, "TrungtamphanphoiSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		} 
	 
		if (action.equals("delete"))
		{
			int[] quyen = Utility.Getquyen("TrungtamphanphoiSvl", "",userId);
			if(quyen[Utility.XOA]==1)
			{
				Delete(dvkdId);
			}else{
				msg = "User không được phân quyền xóa.";
			}			
		}
		
		if (action.equals("update"))
		{
			int[] quyen = Utility.Getquyen("TrungtamphanphoiSvl", "",userId);
			if(quyen[Utility.SUA]==1)
			{
				ITrungtamphanphoi dvkdBean = new Trungtamphanphoi();
				dvkdBean.setId(dvkdId);
				dvkdBean.init();
				session.setAttribute("dvkdBean", dvkdBean);
				session.setAttribute("userId", userId);
				String nextJSP = "/AHF/pages/Center/TrungTamPhanPhoiUpdate.jsp";
				response.sendRedirect(nextJSP);
			}
			else{
				msg = "User không được phân quyền sửa.";
			}
		} 
		else
		{
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			String nextJSP = "/AHF/pages/Center/TrungTamPhanPhoi.jsp";
			response.sendRedirect(nextJSP);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		Utility util = new Utility();
		HttpSession session;
		ITrungtamphanphoiList obj = new TrungtamphanphoiList();
		String userId;
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		session = request.getSession();
		userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		
		String param = "";
	    
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "TrungtamphanphoiSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
		
		if (geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")).equals("search"))
		{ 
			obj.setQuery(getSearchQuery(request, obj));
			obj.setUserId(userId);
			obj.getDvkdList();
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			response.sendRedirect("/AHF/pages/Center/TrungTamPhanPhoi.jsp");
		}
		String msg = "";
		// Create a new Business Unit
		if (geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")).equals("new"))
		{	
			int[] quyen = Utility.Getquyen("TrungtamphanphoiSvl", "",userId);
			if(quyen[Utility.THEM]==1)
			{
				ITrungtamphanphoi dvkdBean = new Trungtamphanphoi();
				dvkdBean.createRs();
				session.setAttribute("dvkdBean", dvkdBean);
				session.setAttribute("userId", userId);
				
				String nextJSP = "/AHF/pages/Center/TrungTamPhanPhoiNew.jsp";
				response.sendRedirect(nextJSP);
			}else{
				msg = "User không được phân quyền thêm";
			}
		}
		
	}
	
	private void Delete(String dvkdId)
	{ 
		try{
			dbutils db = new dbutils(); 
			String command = "delete from TTPP_NCC where ttpp_fk ='" + dvkdId + "'";
			db.update(command);
			command = "delete from TrungTamPhanPhoi where pk_seq ='" + dvkdId + "'";
			db.update(command); 
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private String getSearchQuery(ServletRequest request, ITrungtamphanphoiList obj)
	{
		Utility util = new Utility();
		obj.getDataSearch().clear();
		String dvkd = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkd"));
		if (dvkd == null)
			dvkd = "";
		obj.setDvkd(dvkd);
		
		String nccId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nccId"));
		if (nccId == null)
			nccId = "";
		obj.setNccId(nccId);
		
		String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
		if (tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
		if (denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null)
			trangthai = "";
		
		if (trangthai.equals("2"))
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String query = " select a.pk_seq, a.TEN as ttpp, d.ten as nhacungcap, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao,  c.ten as nguoisua, d.pk_seq as nccId, a.MA from TRUNGTAMPHANPHOI a, nhanvien b, nhanvien c, nhacungcap d, TTPP_NCC   e  where a.PK_SEQ = e.TTPP_FK and d.PK_SEQ = e.NCC_FK  and a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ   ";
		
		if (dvkd.length() > 0)
		{
			obj.setDvkd(dvkd);
			query = query + " and upper(a.TEN) like upper(?)";
			obj.getDataSearch().add("%" + util.replaceAEIOU(dvkd) + "%");
		}
		
		if (!nccId.equals("0"))
		{
			obj.setNccId(nccId);
			query = query + " and d.pk_seq = ?";
			obj.getDataSearch().add("" + nccId + "");
		}
		
		if (tungay.length() > 0)
		{
			obj.setTungay(tungay);
			query = query + " and a.ngaytao >=?";
			obj.getDataSearch().add("%" + tungay + "%");
		}
		
		if (denngay.length() > 0)
		{
			obj.setDenngay(denngay);
			query = query + " and a.ngaytao <=?";
			obj.getDataSearch().add("%" + denngay + "%");
		}
		
		if (trangthai.length() > 0)
		{
			obj.setTrangthai(trangthai);
			query = query + " and a.trangthai =?";
			obj.getDataSearch().add( "" + trangthai + "");
		}
		 
		return query;
	}
	
}
