package geso.dms.center.servlets.dondathang;

import geso.dms.center.beans.dondathang.IErpDuyetddh;
import geso.dms.center.beans.dondathang.IErpDuyetddhList;
import geso.dms.center.beans.dondathang.imp.ErpDuyetddh;
import geso.dms.center.beans.dondathang.imp.ErpDuyetddhList;
import geso.dms.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpDuyetddhUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
    public ErpDuyetddhUpdateSvl() 
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
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}
		else
		{
			session.setMaxInactiveInterval(30000);
		
			Utility util = new Utility();
			
	    	String querystring = request.getQueryString();
		    userId = util.getUserId(querystring);
		    
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"))); 
		    
		    String id = util.getId(querystring);  	
		    IErpDuyetddh lsxBean = new ErpDuyetddh(id);
		    lsxBean.setUserId(userId); 
		    
		    String nextJSP = "";
		    
    		lsxBean.init();
    		
    		String hopdongId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("hopdongId"));
		    if(hopdongId == null)
		    	hopdongId = "";
    			
			nextJSP = "/AHF/pages/Center/ErpDonDatHangDuyetDisplay.jsp";
			if(lsxBean.getLoaidonhang().equals("4"))
				nextJSP = "/AHF/pages/Center/ErpDonDatHangKhacDuyetDisplay.jsp";
			else if(lsxBean.getLoaidonhang().equals("0") && hopdongId.trim().length() > 0 )
				nextJSP = "/AHF/pages/Center/ErpDonHangETCDuyetDisplay.jsp";
    		
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
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}
		else
		{
			request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
			
			session.setMaxInactiveInterval(30000);
			
			this.out = response.getWriter();
			IErpDuyetddh lsxBean;
			
			Utility util = new Utility();	
			String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
		    if(id == null)
		    {  	
		    	lsxBean = new ErpDuyetddh("");
		    }
		    else
		    {
		    	lsxBean = new ErpDuyetddh(id);
		    }
	
		    lsxBean.setUserId(userId);
		    
		    String ngayyeucau = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaychuyen")));
		    if(ngayyeucau == null)
		    	ngayyeucau = "";
		    lsxBean.setNgayyeucau(ngayyeucau);
		    
		    String ngaydenghi = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaydenghi")));
		    if(ngaydenghi == null )
		    	ngaydenghi = "";
		    lsxBean.setNgaydenghi(ngaydenghi);
		    	    
		    String ghichu = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ghichu")));
		    if(ghichu == null)
		    	ghichu = "";
		    lsxBean.setGhichu(ghichu);
		    
			String khonhapId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khonhapId")));
			if (khonhapId == null)
				khonhapId = "";				
			lsxBean.setKhoNhapId(khonhapId);
			
			String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
			if (dvkdId == null)
				dvkdId = "";				
			lsxBean.setDvkdId(dvkdId);
			
			String kbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhId")));
			if (kbhId == null)
				kbhId = "";				
			lsxBean.setKbhId(kbhId);
			
			String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
			if (nppId == null)
				nppId = "";				
			lsxBean.setNppId(nppId);
		    
			String loaidonhang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaidonhang")));
			if (loaidonhang == null)
				loaidonhang = "";				
			lsxBean.setLoaidonhang(loaidonhang);
			
			String action = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));
			if (action == null)
				action = "";				
			
			System.out.println("---VO DUYET DON HANG...");
			boolean kq = false;
			
			if(action.equals("save"))
				kq = lsxBean.saveDDH(request);
			else
				kq = lsxBean.duyetDDH(request);
			
			if(!kq)
			{
				lsxBean.init();
				session.setAttribute("lsxBean", lsxBean);
				String nextJSP = "/AHF/pages/Center/ErpDonDatHangDuyetDisplay.jsp";
				if(lsxBean.getLoaidonhang().equals("4"))
					nextJSP = "/AHF/pages/Center/ErpDonDatHangKhacDuyetDisplay.jsp";
				else if(lsxBean.getLoaidonhang().equals("2"))
					nextJSP = "/AHF/pages/Center/ErpDonHangKMTichLuyDuyetDisplay.jsp";
				else if(lsxBean.getLoaidonhang().equals("1"))
					nextJSP = "/AHF/pages/Center/ErpDonHangKMUngHangDuyetDisplay.jsp";
				else if(lsxBean.getLoaidonhang().equals("3"))
					nextJSP = "/AHF/pages/Center/ErpDonHangTrungBayDuyetDisplay.jsp";
				
				response.sendRedirect(nextJSP);
			}
			else
			{
				IErpDuyetddhList obj = new ErpDuyetddhList();
				obj.setLoaidonhang(loaidonhang);
				
			    obj.setUserId(userId);
			    obj.init("");
				session.setAttribute("obj", obj);							
				String nextJSP = "/AHF/pages/Center/ErpDonDatHangDuyet.jsp";
				response.sendRedirect(nextJSP);
			}
		}	
	}
	
	
}
