package geso.dms.distributor.servlets.chuyenkho;

import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.chuyenkho.ITrahang;
import geso.dms.distributor.beans.chuyenkho.ITrahangList;
import geso.dms.distributor.beans.chuyenkho.imp.Trahang;
import geso.dms.distributor.beans.chuyenkho.imp.TrahangList;
import geso.dms.distributor.db.sql.dbutils;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TrahangUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	ITrahang pxkBean;
	PrintWriter out; 
	dbutils db;
	
    public TrahangUpdateSvl() 
    {
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
	    
	    if (userId.length() == 0)
	    	userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);  	

	    pxkBean = new Trahang(id);
	    pxkBean.setUserId(userId);
	    pxkBean.init();
        
        String nextJSP = "";
        if(querystring.indexOf("display") > 0)
        {
        	nextJSP = "/AHF/pages/Distributor/TraHangDisplay.jsp";
        }
        else
        {
        	nextJSP = "/AHF/pages/Distributor/TraHangUpdate.jsp";
        }
        
        session.setAttribute("pxkBean", pxkBean);
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		this.out = response.getWriter();
		
	    String id = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id"));	
	    if(id == null){  	
	    	pxkBean = new Trahang("");
	    }else{
	    	pxkBean = new Trahang(id);
	    }
	    	    
		String userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
		pxkBean.setUserId(userId);
	        	
    	String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
		if (nppId == null)
			nppId = "";
		pxkBean.setNppId(nppId);
    	
		String ngaychuyen = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaychuyen"));
		if (ngaychuyen == null || ngaychuyen.length() < 10)
			ngaychuyen = getDateTime();
		pxkBean.setNgaychuyen(ngaychuyen);
		
		String nvbhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nvbhId"));
		if (nvbhId == null)
			nvbhId = "";
		pxkBean.setNvbhId(nvbhId);
		
		String khoId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khoId"));
		if (khoId == null)
			khoId = "";
		pxkBean.setKhoId(khoId);
		
		String[] spIds = request.getParameterValues("spIds");
		String[] soluong = request.getParameterValues("soluong");
		
		if(soluong != null)
		{
			Hashtable<String, Integer> sp_sl = new Hashtable<String, Integer>();
			for(int i = 0; i < soluong.length; i++)
			{
				if(soluong[i].trim().length() > 0)
				{
					String[] soluongDETAIL = request.getParameterValues(spIds[i] + "_spSOLUONG");
					String[] solo = request.getParameterValues(spIds[i] + "_spSOLO");
					
					for(int j = 0; j < solo.length; j++)
					{
						if(soluongDETAIL[j].trim().length() > 0)
						{
							sp_sl.put(spIds[i] + solo[j], Integer.parseInt( soluongDETAIL[j].trim().replaceAll(",", "") ));
						}
					}
				}
			}
			pxkBean.setSSp_Soluong(sp_sl);
		}
		
 		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    
		if(action.equals("save"))
		{
			db = new dbutils();
			if (id == null)
			{
				if (!(pxkBean.CreateCk(request)))
				{	
					pxkBean.createRS();
					session.setAttribute("pxkBean", pxkBean);			
					String nextJSP = "/AHF/pages/Distributor/TraHangNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else{
					ITrahangList obj = new TrahangList();
					obj.setUserId(userId);
					
					obj.init("");
					session.setAttribute("obj", obj);
						
					String nextJSP = "/AHF/pages/Distributor/TraHang.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}				
			}	
			else
			{
				if (!(pxkBean.UpdateCk(request)))
				{	
					System.out.println("___Khong the cap nhat: " + pxkBean.getMessage());
					pxkBean.createRS();
					session.setAttribute("pxkBean", pxkBean);			
					String nextJSP = "/AHF/pages/Distributor/TraHangUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else{
					ITrahangList obj = new TrahangList();
					obj.setUserId(userId);
					
					obj.init("");
					session.setAttribute("obj", obj);
						
					String nextJSP = "/AHF/pages/Distributor/TraHang.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}	
			}
		}
		else
		{
			pxkBean.createRS();
			session.setAttribute("pxkBean", pxkBean);
			
			String nextJSP;
			if (id == null)
			{			
				nextJSP = "/AHF/pages/Distributor/TraHangNew.jsp";
			}
			else
			{
				nextJSP = "/AHF/pages/Distributor/TraHangUpdate.jsp";   						
			}
			response.sendRedirect(nextJSP);							
		}
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
}
