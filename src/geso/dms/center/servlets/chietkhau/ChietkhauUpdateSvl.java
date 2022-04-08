package geso.dms.center.servlets.chietkhau;

import geso.dms.center.beans.mucchietkhau.IChietkhau;
import geso.dms.center.beans.mucchietkhau.IChietkhauList;
import geso.dms.center.beans.mucchietkhau.imp.Chietkhau;
import geso.dms.center.beans.mucchietkhau.imp.ChietkhauList;
import geso.dms.center.util.Utility;

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

public class ChietkhauUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private PrintWriter out;  
       
    public ChietkhauUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    
	    String id = util.getId(querystring);  	

	    IChietkhau ckBean = new Chietkhau(id);	    
	    
        ckBean.setUserId(userId);
        session.setAttribute("ckBean", ckBean);
        String nextJSP = "/AHF/pages/Center/ChietKhauUpdate.jsp";
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		IChietkhau ckBean;
		this.out = response.getWriter();
		
	    String id =  geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id"));
	    if(id == null){  	
	    	ckBean = new Chietkhau("");
	    }else{
	    	ckBean = new Chietkhau(id);
	    }
	    
	    
		String userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
		ckBean.setUserId(userId);
	    
    	String sotien = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sotien"));
		if (sotien == null)
			sotien = "";
		sotien=sotien.replaceAll(",", "");
    	ckBean.setSotien(sotien);
    	
    	String chietkhau = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chietkhau"));
		if (chietkhau == null)
			chietkhau = "";
		ckBean.setChietkhau(chietkhau);
		
		String diengiai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		ckBean.setDiengiai(diengiai);
		
		String[] nppId = request.getParameterValues("nppTen");
		String str = "";
		if(nppId != null)
		{
			for(int i = 0; i < nppId.length; i++)
				str += nppId[i] + ",";
			if(str.length() > 0)
				str = str.substring(0, str.length() - 1);
		}
		ckBean.setNppIds(str);


		String ngaysua = getDateTime();
    	ckBean.setNgaysua(ngaysua);
		
		String nguoisua = userId;
		ckBean.setNguoisua(nguoisua);
    		
 		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    
		if(action.equals("save"))
		{
			if ( id == null){
				if (!(ckBean.CreateMck())){				
					ckBean.setUserId(userId);
					ckBean.createRs();
					session.setAttribute("ckBean", ckBean);
					String nextJSP = "/AHF/pages/Center/ChietKhauNew.jsp";
					response.sendRedirect(nextJSP);
				}else{
					IChietkhauList obj = new ChietkhauList();
					obj.setUserId(userId);
					obj.init("");
					session.setAttribute("obj", obj);
						
					String nextJSP = "/AHF/pages/Center/ChietKhau.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
				
			}else{
				if (!(ckBean.UpdateMck())){			
					ckBean.createRs();
					session.setAttribute("ckBean", ckBean);
					String nextJSP = "/AHF/pages/Center/ChietKhauUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else{
					IChietkhauList obj = new ChietkhauList();
					obj.setUserId(userId);
					obj.init("");
					session.setAttribute("obj", obj);
						
					String nextJSP = "/AHF/pages/Center/ChietKhau.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
			}
		}
		else
		{
			ckBean.setUserId(userId);
			session.setAttribute("ckBean", ckBean);
			
			String nextJSP;
			if (id == null){			
				nextJSP = "/AHF/pages/Center/ChietKhauNew.jsp";
			}else{
				nextJSP = "/AHF/pages/Center/ChietKhauUpdate.jsp";   						
			}
			response.sendRedirect(nextJSP);
			
		}
	}
	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}


}
