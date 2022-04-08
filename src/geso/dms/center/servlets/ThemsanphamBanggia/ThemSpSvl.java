package geso.dms.center.servlets.ThemsanphamBanggia;

import geso.dms.center.beans.banggiablc.*;
import geso.dms.center.beans.banggiablc.imp.*;
import geso.dms.center.beans.themsanphambanggia.IThemSp;
import geso.dms.center.beans.themsanphambanggia.imp.ThemSp;
import geso.dms.center.util.Utility;



import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Request;


public class ThemSpSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
{
	static final long serialVersionUID = 1L;

	public ThemSpSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		OutputStream out = response.getOutputStream();
		
		HttpSession session = request.getSession();

		Utility util = new Utility();
		

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		//out.println(userId);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		String action = util.getAction(querystring);
		//out.println(action);

		String bgId = util.getId(querystring);
		IThemSp obj;
		obj = new ThemSp();
		obj.setUserid(userId);
		obj.init();
		session.setAttribute("obj", obj);

		String nextJSP = "/AHF/pages/Center/Themsp.jsp";
		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		IThemSp obj;
		obj = new ThemSp();
		HttpSession session = request.getSession();
		Utility util = new Utility();
		String userId = util.antiSQLInspection(request.getParameter("userId"));

		String action = request.getParameter("action");
		if (action == null)
		{
			action = "";
		}
		out.println(action);

		String loai=request.getParameter("loai");
		obj.setLoai(loai);
		
		String banggiamuaid=request.getParameter("banggiamuaid");
		if(banggiamuaid==null)
			banggiamuaid="";
		obj.setIdbanggiamua(banggiamuaid);
		
		String banggiabanid=request.getParameter("banggiabanid");
		if(banggiabanid==null)
			banggiabanid="";
		obj.setIdbanggiaban(banggiabanid);
		
		System.out.println("gia mua "+banggiamuaid +"---- gia ban "+banggiabanid);
		
		String giasanpham=request.getParameter("giasanpham");
		if(giasanpham==null)
			giasanpham="";
		obj.setGia(giasanpham);
		
		
		String chietkhau=request.getParameter("chietkhau");
		if(chietkhau==null)
			chietkhau="0";
		obj.setChietkhau(chietkhau);
		
		String spid=request.getParameter("spid");
		if(spid==null)
			spid="";
		obj.setSpid(spid);
		
		if (action.equals("save"))
		{

			
			obj.setUserid(userId);

			if(obj.save())
			{
				obj.setMsg("chèn giá thành công !!!");
			}
			obj.init();
			session.setAttribute("obj", obj);

			String nextJSP = "/AHF/pages/Center/Themsp.jsp";
			response.sendRedirect(nextJSP);
			return;

		}
		else
		{
			obj.setUserid(userId);
			obj.init();
			session.setAttribute("obj", obj);
			String nextJSP = "/AHF/pages/Center/Themsp.jsp";
			response.sendRedirect(nextJSP);
			return;
		}
		
	}


	
	
	
}
