package geso.dms.center.servlets.danhmucquyen;

import geso.dms.center.beans.danhmucquyen.IDanhmucquyen;
import geso.dms.center.beans.danhmucquyen.IDanhmucquyenList;
import geso.dms.center.beans.danhmucquyen.imp.Danhmucquyen;
import geso.dms.center.beans.danhmucquyen.imp.Danhmucquyenlist;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DanhmucquyennewSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
	public DanhmucquyennewSvl()
	{
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IDanhmucquyen obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		this.out = response.getWriter();
		HttpSession session = request.getSession();
		Utility util = new Utility();
		out = response.getWriter();
		
		String querystring = request.getQueryString();
		
		String id = util.getId(querystring);
		
		String userId = util.getUserId(querystring);
		
		System.out.println("[ID]"+id);
		
		
//		if ( Utility.CheckSessionUser( session,  request, response))
//		{
//			Utility.RedireactLogin(session, request, response);
//		}
//		if( Utility.CheckRuleUser( session,  request, response, "DanhmucquyenSvl", "", Utility.SUA ))
//		{
//			Utility.RedireactLogin(session, request, response);
//		}
		String action = util.getAction(querystring);
		obj = new Danhmucquyen(id);
		obj.init();
		session.setAttribute("obj", obj);
		String nextJSP ="";
		if(action.equals("update"))
		{
			nextJSP = "/AHF/pages/Center/DanhMucQuyenNew.jsp";
		}else{
			nextJSP = "/AHF/pages/Center/DanhMucQuyenDisplay.jsp";
		}
		response.sendRedirect(nextJSP);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		Utility util = new Utility();
		
		IDanhmucquyen obj;
		obj = new Danhmucquyen("");
		
		String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")==null?"":request.getParameter("id")));
		obj.setId(id);
		
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));obj.setUserId(userId);
		
		
		System.out.println();
		String Ten = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ten")));
		if (Ten == null)
			Ten = "";
		obj.setTen(Ten);
		String DienGiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		if (DienGiai == null)
			DienGiai = "";
		obj.setDiengiai(DienGiai);
		
		String TrangThai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
		if (TrangThai == null)
			TrangThai = "0";
		else
			TrangThai = "1";
		obj.setTrangthai(TrangThai);
		String loaiMenu = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaiMenu")));
		if (loaiMenu == null)
			loaiMenu = "0";
		obj.setLoaiMenu(loaiMenu);
		
		
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }

//		if ( Utility.CheckSessionUser( session,  request, response))
//		{
//			Utility.RedireactLogin(session, request, response);
//		}
//		if( Utility.CheckRuleUser( session,  request, response, "DanhmucquyenSvl", "", Utility.SUA ))
//		{
//			Utility.RedireactLogin(session, request, response);
//		}
	    
	    //Thay doi VIEW DANH SACH UNG DUNG
	    if(action.equals("loaiMenu"))
	    {
	    	obj.createRs();
	    	session.setAttribute("obj", obj);
			String nextJSP = "/AHF/pages/Center/DanhMucQuyenNew.jsp";
			response.sendRedirect(nextJSP);			
	    }
	    else
	    {

	    String[] ungdungId = request.getParameterValues("ungdungId");
			String[] cbHienThi = request.getParameterValues("cbHienThi");
			String[] cbThem =   request.getParameterValues("cbThem");
			String[] cbXoa = request.getParameterValues("cbXoa");
			String[] cbSua = request.getParameterValues("cbSua");
			String[] cbXem = request.getParameterValues("cbXem");
			String[] cbChot =request.getParameterValues("cbChot");
			String[] cbHuychot = request.getParameterValues("cbHuyChot");
			
			obj.setUngdungIds(this.Doisangchuoi(ungdungId));
			obj.setCbHienThi(this.Doisangchuoi(cbHienThi));
			obj.setCbThem(this.Doisangchuoi(cbThem));
			obj.setCbXoa(this.Doisangchuoi(cbXoa));
			obj.setCbSua(this.Doisangchuoi(cbSua));
			obj.setCbXem(this.Doisangchuoi(cbXem));
			obj.setCbChot(this.Doisangchuoi(cbChot));			
			obj.setCbHuyChot(this.Doisangchuoi(cbHuychot));
			
			if (Ten.length() <= 0 || DienGiai.length() <= 0)
			{
				obj.setMsg("Ban phai nhap du thong tin");
				
				session.setAttribute("obj", obj);
				String nextJSP = "/AHF/pages/Center/DanhMucQuyenNew.jsp";
				response.sendRedirect(nextJSP);
			} else if (id.length() > 0)
			{
				if (!obj.update(request))
				{
					obj.init();
					session.setAttribute("obj", obj);
					String nextJSP = "/AHF/pages/Center/DanhMucQuyenNew.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					
					IDanhmucquyenList objlist = new Danhmucquyenlist();
					objlist.setUserId(userId);
					session.setAttribute("obj", objlist);
					String nextJSP = "/AHF/pages/Center/DanhMucQuyen.jsp";
					response.sendRedirect(nextJSP);
				}
			} else
			{
				if (!obj.save(request))
				{
					obj.createRs();
					session.setAttribute("obj", obj);
					String nextJSP = "/AHF/pages/Center/DanhMucQuyenNew.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					
					IDanhmucquyenList objlist = new Danhmucquyenlist();
					obj.setUserId(userId);
					session.setAttribute("obj", objlist);
					String nextJSP = "/AHF/pages/Center/DanhMucQuyen.jsp";
					response.sendRedirect(nextJSP);
				}
			}
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
					str =  checknpp[i];
				} else
				{
					str = str + "," +checknpp[i];
				}
			}
		}
		return str;

	}
	
}
