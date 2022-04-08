package geso.dms.distributor.servlets.nhaphang;

import geso.dms.distributor.beans.nhaphang.INhaphang;
import geso.dms.distributor.beans.nhaphang.INhaphangList;
import geso.dms.distributor.beans.nhaphang.imp.Nhaphang;
import geso.dms.distributor.beans.nhaphang.imp.NhaphangList;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

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

public class NhaphangUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public NhaphangUpdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		
		// kiểm tra quyền xem menu
	    String param="";
		if( Utility.CheckRuleUser( session,  request, response, "NhaphangSvl", param, Utility.XEM ) || Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
			return;
		}
		
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session
				.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/AHF/index.jsp");
		} else
		{
			PrintWriter out = response.getWriter();
			String nextJSP = "";
			Utility util = new Utility();

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);

			out.println(userId);

			if (userId.length() == 0)
				userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

			String id = util.getId(querystring);
			out.println(id);
			
			//dbutils db = new dbutils();
			/*// Kiểm tra id có hợp lệ không
		    if(id != null && id.trim().length() > 0 
		    		&& !geso.dms.center.util.Utility.KiemTra_PK_SEQ_HopLe(id, "nhaphang", db)){
		    	db.shutDown();
	    		return;
	    	}*/

			INhaphang nhaphang = (INhaphang) new Nhaphang();
			nhaphang.setUserId(userId);
			//String ms = "Vui lòng chọn ngày nhận hàng khớp với ngày nhận hàng thực tế. Cảm ơn!!! ";
			
			//nhaphang.setMessage(ms);
			nhaphang.setId(id);
			nhaphang.init();
			String update = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("update"));

			if (update != null)
			{	
				int[] quyen = geso.dms.center.util.Utility.Getquyen("NhaphangSvl", "",nhaphang.getUserId());
				if(quyen[geso.dms.center.util.Utility.SUA]==1){
					nextJSP = "/AHF/pages/Distributor/NhapHangUpdate.jsp";
				}
				else{
					return;
				}
			} else
			{
				nextJSP = "/AHF/pages/Distributor/NhapHangBeVo.jsp";
			}
			session.setAttribute("nhaphang", nhaphang);
			response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen"); 
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String khonhapId =null;
		String dvkdId = null;
		String kbhId = null;
		
		// kiểm tra quyền xem menu
	    String param="";
		if( Utility.CheckRuleUser( session,  request, response, "NhaphangSvl", param, Utility.XEM ) || Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
			return;
		}
		
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}
		else
		{

			session.setMaxInactiveInterval(30000);

			INhaphang nhaphang;

			Utility util = new Utility();	
			String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
			
			dbutils db = new dbutils();
			// Kiểm tra id có hợp lệ không
		    if(id != null && id.trim().length() > 0 
		    		&& !geso.dms.center.util.Utility.KiemTra_PK_SEQ_HopLe(id, "nhaphang", db)){
		    	db.shutDown();
	    		return;
	    	}
			
			if(id == null)
			{  	
				nhaphang = new Nhaphang();
			}
			else
			{
				nhaphang = new Nhaphang();
				nhaphang.setUserId(userId);
				nhaphang.init();
				nhaphang.setId(id);
			}
			nhaphang.setUserId(userId);
			String ngayyeucau = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaychuyen")));
			if(ngayyeucau == null || ngayyeucau.trim().length() <= 0)
				ngayyeucau = getDateTime();
			nhaphang.setNgayyeucau(ngayyeucau);

			String ngaynhan = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaynhan")));
			if(ngaynhan == null )
				ngaynhan = "";
			nhaphang.setNgaynhap(ngaynhan);

			String sohoadon = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sohoadon")));
			if(sohoadon == null )
				sohoadon = "";
			nhaphang.setSohoadon(sohoadon);

			String ghichu = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ghichu")));
			if(ghichu == null)
				ghichu = "";
			nhaphang.setGhichu(ghichu);
			
			khonhapId = util.antiSQLInspection(request.getParameter("khonhapId"));
			if (khonhapId == null)
				khonhapId = "";				
			nhaphang.setKhoNhapId(khonhapId);
			System.out.println("khonhapid : "+ khonhapId);
			session.setAttribute("khoId", nhaphang.getKhoNhapId());
			
			dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
			if (dvkdId == null)
				dvkdId = "";				
			nhaphang.setDvkdId(dvkdId);
			String thuevat = util.antiSQLInspection(request.getParameter("thuevat"));
			nhaphang.setThuevat(thuevat); 
			kbhId = util.antiSQLInspection(request.getParameter("kbhId"));
			if (kbhId == null)
				kbhId = "";				
			nhaphang.setKbhId(kbhId);

			String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
			if (nppId == null)
				nppId = "";				
			nhaphang.setNppId(nppId);
			

			String[] spId = request.getParameterValues("spMa");
			nhaphang.setSpId(spId);

			String[] spTen = request.getParameterValues("spTen");
			nhaphang.setSpTen(spTen);

			String[] spxuat = request.getParameterValues("spxuat");
			nhaphang.setSpXuat(spxuat);
			
			String[] soluong = request.getParameterValues("soluong");
			nhaphang.setSpSoluong(soluong);

			/*String[] spScheme = request.getParameterValues("scheme");
			nhaphang.setSpScheme(spScheme);*/
			
			String[] spDongia = request.getParameterValues("dongia");
			nhaphang.setSpDongia(spDongia);
			
			String[] spVat = request.getParameterValues("spvat");
			nhaphang.setSpVat(spVat);
			
			String[] spDonvi = util.antiSQLInspection_Array(request.getParameterValues("donvi"));
			nhaphang.setSpDonvi(spDonvi);
			
			String[] spKhoId = request.getParameterValues("spKhoId");
			nhaphang.setSpKhoId(spKhoId);

			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			int[] quyen = geso.dms.center.util.Utility.Getquyen("NhaphangSvl", "",nhaphang.getUserId());
			if(action.equals("save"))
			{	
				if(id == null)
				{
					boolean kq = false;
					//nhaphang.create()
					
					/*if(quyen[Utility.THEM]==1){
						kq = nhaphang.create();
					}*/
					kq = nhaphang.create();
					if(!kq)
					{
						nhaphang.createRs();
						session.setAttribute("nhaphang", nhaphang);
						String nextJSP = "/AHF/pages/Distributor/NhapHangNew.jsp";

						response.sendRedirect(nextJSP);
					}
					else
					{
						INhaphangList obj = new NhaphangList();

						obj.setUserId(userId);
						obj.createNhaphanglist("");
						session.setAttribute("obj", obj);							
						String nextJSP = "/AHF/pages/Distributor/NhapHang.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				else
				{
					boolean kq = false;
					//nhaphang.create()
					
					if(quyen[Utility.SUA]==1){
						System.out.println("vo update ");
						kq = nhaphang.update();
					}
					if(!kq)
					{
						nhaphang.createRs();
						session.setAttribute("nhaphang", nhaphang);
						String nextJSP = "/AHF/pages/Distributor/NhapHangUpdate.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{
						INhaphangList obj = new NhaphangList();

						obj.setUserId(userId);
						obj.createNhaphanglist("");
						session.setAttribute("obj", obj);							
						String nextJSP = "/AHF/pages/Distributor/NhapHang.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
				if(action.equals("chot"))
				{
					boolean kq = false;
					//nhaphang.create()
					
					if(quyen[Utility.CHOT]==1){
						kq = nhaphang.chot();
					}
					
					if(!kq)
					{
						nhaphang.createRs();
						session.setAttribute("nhaphang", nhaphang);
						String nextJSP = "/AHF/pages/Distributor/NhapHangUpdate.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{
						String msg = nhaphang.getMessage();
						INhaphangList obj = new NhaphangList();
						obj.setMsg(msg);
						obj.setUserId(userId);
						obj.createNhaphanglist("");
						session.setAttribute("obj", obj);							
						String nextJSP = "/AHF/pages/Distributor/NhapHang.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				else
				{
					nhaphang.setUserId(userId);
					nhaphang.createRs();
					session.setAttribute("dvkdId", nhaphang.getDvkdId());
					session.setAttribute("kbhId", nhaphang.getKbhId());
					session.setAttribute("nppId", nhaphang.getNppId());
					session.setAttribute("khoId", nhaphang.getKhoNhapId());
					session.setAttribute("nhaphang", nhaphang);
					session.setAttribute("ngaychuyen", nhaphang.getNgayyeucau());

					String nextJSP = "/AHF/pages/Distributor/NhapHangNew.jsp";
					if(id != null)
						nextJSP = "/AHF/pages/Distributor/NhapHangUpdate.jsp";

					response.sendRedirect(nextJSP);
				}
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
