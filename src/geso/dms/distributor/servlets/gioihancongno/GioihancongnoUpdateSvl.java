package geso.dms.distributor.servlets.gioihancongno;

import geso.dms.distributor.beans.gioihancongno.IGioihancongno;
import geso.dms.distributor.beans.gioihancongno.IGioihancongnoList;
import geso.dms.distributor.beans.gioihancongno.imp.Gioihancongno;
import geso.dms.distributor.beans.gioihancongno.imp.GioihancongnoList;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GioihancongnoUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	
    public GioihancongnoUpdateSvl()
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
		}else{
		
		IGioihancongno ghcnBean;
		PrintWriter out; 

		
		out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    	    
	    String id = util.getId(querystring);  	
	    
	    ghcnBean = new Gioihancongno(id);
	    ghcnBean.setUserId(userId);
	    
        ghcnBean.init();
        session.setAttribute("ghCNandKH", ghcnBean.getListTinDung(id));
        session.setAttribute("ghcnBean", ghcnBean);
        String nextJSP = "/AHF/pages/Distributor/GioiHanCongNoUpdate.jsp";
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
		}else{
		
		IGioihancongno ghcnBean;		
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		
		Utility util = new Utility();
		
	    String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));	
	    if(id == null){  	
	    	ghcnBean = new Gioihancongno("");
	    }else{
	    	ghcnBean = new Gioihancongno(id);
	    }
	    	    
		userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		ghcnBean.setUserId(userId);
	    
    	String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		if (diengiai == null)
			diengiai = "";				
    	ghcnBean.setDiengiai(diengiai);
    	
    	String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
		if (nppId == null)
			nppId = "";
		ghcnBean.setNppId(nppId);
    	
		String songayno = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("songayno")));
		if (songayno == null)
			songayno = "";
		ghcnBean.setSongayno(songayno);
		
		String sotienno = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sotienno")));
		if (sotienno == null)
			sotienno = "";
		ghcnBean.setSotienno(sotienno);
		
		String ddkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdTen")));
		if (ddkdId == null)
			ddkdId = "";
		ghcnBean.setDdkdId(ddkdId);
		
		String[] khIds = request.getParameterValues("khIds");
		ghcnBean.setKh_GhcnIds(khIds);
		
		String ngaysua = getDateTime();
    	ghcnBean.setNgaysua(ngaysua);
		
					
		if (diengiai.trim().length()== 0){
			ghcnBean.setMessage("Vui Long Nhap Dien Giai Muc Chiet Khau");
					}

		if (songayno.trim().length()== 0){
			ghcnBean.setMessage("Vui Long Nhap So Ngay Duoc No");
			
		}
		
		if (sotienno.trim().length()== 0){
			ghcnBean.setMessage("Vui Long Nhap So Tien Duoc Phep No");
			
		}
	
		List<String> list = new ArrayList<String>();
		list = (List<String>)session.getAttribute("ghCNandKH");
		
 		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    
		if(action.equals("save"))
		{
			if ( id == null)
			{
				if (!(ghcnBean.CreateGhcn(khIds))){	
					ghcnBean.createRS();
					session.setAttribute("ghcnBean", ghcnBean);			
					String nextJSP = "/AHF/pages/Distributor/GioiHanCongNoNew.jsp";
					response.sendRedirect(nextJSP);
				}else{
					IGioihancongnoList obj = new GioihancongnoList();
					obj.setUserId(userId);
					obj.init("");
					session.setAttribute("obj", obj);
						
					String nextJSP = "/AHF/pages/Distributor/GioiHanCongNo.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
				
			}else{
				if (!(ghcnBean.UpdateGhcn(khIds,list)))
				{
					ghcnBean.init();
					session.setAttribute("ghcnBean", ghcnBean);
					String nextJSP = "/AHF/pages/Distributor/GioiHanCongNoUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else{
					IGioihancongnoList obj = new GioihancongnoList();
					obj.setUserId(userId);
					obj.init("");
					session.setAttribute("obj", obj);
						
					String nextJSP = "/AHF/pages/Distributor/GioiHanCongNo.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
			}
		}else
		{
			ghcnBean.createRS();
			session.setAttribute("ghcnBean", ghcnBean);
			
			String nextJSP;
			if (id == null){			
				nextJSP = "/AHF/pages/Distributor/GioiHanCongNoNew.jsp";
			}else{
				nextJSP = "/AHF/pages/Distributor/GioiHanCongNoUpdate.jsp";   						
			}
			response.sendRedirect(nextJSP);		
		}}
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
