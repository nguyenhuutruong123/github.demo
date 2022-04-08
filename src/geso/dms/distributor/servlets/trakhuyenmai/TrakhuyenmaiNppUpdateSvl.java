package geso.dms.distributor.servlets.trakhuyenmai;

import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.donhang.ISanpham;
import geso.dms.distributor.beans.donhang.imp.Sanpham;
import geso.dms.distributor.beans.quanlykhuyenmai.ITrakhuyenmaiNpp;
import geso.dms.distributor.beans.quanlykhuyenmai.ITrakhuyenmaiNppList;
import geso.dms.distributor.beans.quanlykhuyenmai.imp.TrakhuyenmaiNpp;
import geso.dms.distributor.beans.quanlykhuyenmai.imp.TrakhuyenmaiNppList;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TrakhuyenmaiNppUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
    public TrakhuyenmaiNppUpdateSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		ITrakhuyenmaiNpp tkmBean;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String ctkmId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ctkmId"));
	    String tkmId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tkmId"));
	    
	    tkmBean = new TrakhuyenmaiNpp(ctkmId, tkmId);
	    tkmBean.setUserId(userId);
	    tkmBean.init();
		session.setAttribute("tkmBean", tkmBean);
				
		String nextJSP = "/AHF/pages/Distributor/TraKhuyenMaiNppSanPham.jsp";
		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		ITrakhuyenmaiNpp tkmBean;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	     
	    	    
	    String userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    String ctkmId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ctkmId"));
	    String tkmId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tkmId"));
	    
	    tkmBean = new TrakhuyenmaiNpp(ctkmId, tkmId);
	    tkmBean.setUserId(userId);
	    tkmBean.init();
	    
	    String[] id = request.getParameterValues("spId");
	    String[] masp = request.getParameterValues("spMa");
		String[] tensp = request.getParameterValues("spTen");
		String[] dongia = request.getParameterValues("dongia");
		String[] thutu = request.getParameterValues("thutu");
		
		List<ISanpham> spList = new ArrayList<ISanpham>();
		for(int  i = 0; i < id.length; i++)
		{
			ISanpham sp = new Sanpham();
			sp.setId(id[i]);
			sp.setMasanpham(masp[i]);
			sp.setTensanpham(tensp[i]);
			sp.setDongia(dongia[i]);
			//System.out.println("Don gia thu " + i + " la: " + dongia[i] + "\n");
			sp.setDonvitinh(thutu[i]);
			
			spList.add(sp);
		}
		tkmBean.setSpList(spList);
	    
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if(action.equals("save"))
		{
			if(!tkmBean.save())
			{
				session.setAttribute("tkmBean", tkmBean);
				
				String nextJSP = "/AHF/pages/Distributor/TraKhuyenMaiNppSanPham.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				ITrakhuyenmaiNppList obj = new TrakhuyenmaiNppList();
			    obj.setUserId(userId);
			    obj.init("");
				session.setAttribute("obj", obj);
						
				String nextJSP = "/AHF/pages/Distributor/TraKhuyenMai.jsp";
				response.sendRedirect(nextJSP);
			}
		}
		else
		{
			if(action.equals("remove"))
			{
				if(!tkmBean.remove())
				{
					session.setAttribute("tkmBean", tkmBean);
					
					String nextJSP = "/AHF/pages/Distributor/TraKhuyenMaiNppSanPham.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					ITrakhuyenmaiNppList obj = new TrakhuyenmaiNppList();
				    obj.setUserId(userId);
				    obj.init("");
					session.setAttribute("obj", obj);
							
					String nextJSP = "/AHF/pages/Distributor/TraKhuyenMai.jsp";
					response.sendRedirect(nextJSP);
				}
			}
		}
		
		
	}

}
