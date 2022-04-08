package geso.dms.distributor.servlets.banggiabandoitac;

import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.banggiabandoitac.IBanggiabandoitac;
import geso.dms.distributor.beans.banggiabandoitac.IBanggiabandoitacList;
import geso.dms.distributor.beans.banggiabandoitac.imp.Banggiabandoitac;
import geso.dms.distributor.beans.banggiabandoitac.imp.BanggiabandoitacList;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BanggiabandoitacUpdateSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet 
{
   static final long serialVersionUID = 1L;
   
	public BanggiabandoitacUpdateSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		
		PrintWriter out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String id = util.getId(querystring);  	
	    out.println(id);
	    
	    IBanggiabandoitac bgBean = new Banggiabandoitac();
	    
        bgBean.setUserId(userId);
        bgBean.setId(id);
        bgBean.init();
        
        session.setAttribute("bgBean", bgBean);
        String nextJSP = "/AHF/pages/Distributor/BangGiaBanDoiTacUpdate.jsp";
        
        response.sendRedirect(nextJSP);

	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		IBanggiabandoitac bgBean = (IBanggiabandoitac) new Banggiabandoitac();
	    Utility util = new Utility();
	    
		String id =  util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
	    if(id == null) 	
	    	id = "";
	    bgBean.setId(id);
	    
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		bgBean.setUserId(userId);

    	String bgTen = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ten")));
		if (bgTen == null)
			bgTen = "";				
    	bgBean.setTen(bgTen);

		String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
		if(dvkdId == null)
			dvkdId = "";	
		bgBean.setDvkdId(dvkdId);
	    
		String kenhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhId")));
		if(kenhId == null)
			kenhId = "";
		bgBean.setKenhId(kenhId);
		
		String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
		if(nppId == null)
			nppId = "";
		bgBean.setNppId(nppId);
		
		String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
		if(tungay == null)
			tungay = "";
		bgBean.setTungay(tungay);
		
		String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
		if(denngay == null)
			denngay = "";
		bgBean.setDenngay(denngay);
		
		String nhomsp = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhomsp")));
		if(nhomsp == null)
			nhomsp = "";
		bgBean.setNhomsp(nhomsp);

		String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
    	if (trangthai == null)
    		trangthai = "0";
    	else
    		trangthai = "1";
    	bgBean.setTrangthai(trangthai);
    	
    	String[] doitacIds = request.getParameterValues("doitacIds");
    	if(doitacIds != null)
    	{
    		String doitacId = "";
    		for(int i = 0; i < doitacIds.length; i++)
    			doitacId += doitacIds[i] + ",";
    		
    		if(doitacId.trim().length() > 0)
    		{
    			doitacId = doitacId.substring(0, doitacId.length() - 1);
    			bgBean.setDoitacId(doitacId);
    		}
    	}
    	
    	String[] spIds = request.getParameterValues("spIds");
    	String[] ptchietkhau = request.getParameterValues("ptchietkhau");
    	String[] dongiaSAUCK = request.getParameterValues("dongiaSAUCK");
    	
    	if(spIds != null )
    	{
    		Hashtable<String, String> sanphamCK = new Hashtable<String, String>();
    		Hashtable<String, String> sanphamDG_SauCK = new Hashtable<String, String>();
    		
    		for(int i = 0; i < spIds.length; i++)
    		{
    			sanphamCK.put(spIds[i], ptchietkhau[i]);
    			sanphamDG_SauCK.put(spIds[i], dongiaSAUCK[i]);
    		}
    		bgBean.setSanphamCK(sanphamCK);
    		bgBean.setSanphamDG_SauCK(sanphamDG_SauCK);
    	}
		   	
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if(action.equals("save"))
		{
			if (id.length()==0)
			{
				if (!(bgBean.CreateBg()))
				{			
					bgBean.setUserId(userId);
					bgBean.createRS();
					session.setAttribute("bgBean", bgBean);
					String nextJSP = "/AHF/pages/Distributor/BangGiaBanDoiTacNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IBanggiabandoitacList obj = new BanggiabandoitacList();
					obj.setUserId(userId);
					obj.init("");
					session.setAttribute("obj", obj);
						
					String nextJSP = "/AHF/pages/Distributor/BangGiaBanDoiTac.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}	
			}
			else
			{
				if (!(bgBean.UpdateBg()))
				{								
					bgBean.setUserId(userId);
					bgBean.setId(id);
					bgBean.init();
					session.setAttribute("bgBean", bgBean);
					String nextJSP = "/AHF/pages/Distributor/BangGiaBanDoiTacUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IBanggiabandoitacList obj = new BanggiabandoitacList();
					obj.setUserId(userId);
					obj.init("");
					session.setAttribute("obj", obj);
					String nextJSP = "/AHF/pages/Distributor/BangGiaBanDoiTac.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
			}
		}
		else
		{	
			String nextJSP;
			if (id.length()==0)
			{			
				bgBean.setUserId(userId);
				bgBean.createRS();
				session.setAttribute("bgBean", bgBean);
				nextJSP = "/AHF/pages/Distributor/BangGiaBanDoiTacNew.jsp";
			}
			else
			{
				bgBean.setUserId(userId);
				bgBean.setId(id);
				bgBean.init();
				session.setAttribute("bgBean", bgBean);

				nextJSP = "/AHF/pages/Distributor/BangGiaBanDoiTacUpdate.jsp";   						
			}
			response.sendRedirect(nextJSP);
		}
	}
	

}