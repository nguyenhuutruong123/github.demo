package geso.dms.center.servlets.tieuchithuong;

import geso.dms.center.beans.tieuchithuong.ITieuchithuongTB;
import geso.dms.center.beans.tieuchithuong.ITieuchithuongTBList;
import geso.dms.center.beans.tieuchithuong.imp.TieuchithuongTB;
import geso.dms.center.beans.tieuchithuong.imp.TieuchithuongTBList;
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

public class TieuchithuongTBUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
	
    public TieuchithuongTBUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		ITieuchithuongTB tctskuBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    	    
	    String id = util.getId(querystring);
	   
	    tctskuBean = new TieuchithuongTB(id);
	    tctskuBean.setId(id);
	    tctskuBean.setUserId(userId);
	    
        tctskuBean.init();
        session.setAttribute("tctskuBean", tctskuBean);
        
        String nextJSP = "/AHF/pages/Center/TieuChiThuongTBUpdate.jsp";
        if(querystring.indexOf("display") >= 0)
        	nextJSP = "/AHF/pages/Center/TieuChiThuongTBDisplay.jsp";
        
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		ITieuchithuongTB tctskuBean;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		this.out = response.getWriter();
		Utility util = new Utility();
		
	   	String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
	    if(id == null){  	
	    	tctskuBean = new TieuchithuongTB("");
	    }else{
	    	tctskuBean = new TieuchithuongTB(id);
	    }
	    
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		tctskuBean.setUserId(userId);
		
		String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		if (diengiai == null)
			diengiai = "";
		tctskuBean.setDiengiai(diengiai);
		
		String scheme = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("scheme")));
		if (scheme == null)
			scheme = "";
		tctskuBean.setScheme(scheme);
		
		String thang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang")));
		if (thang == null)
			thang = "";
		tctskuBean.setThang(thang);
		
		String nam = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nam")));
		if (nam == null)
			nam = "";
		tctskuBean.setNam(nam);
				
		String[] schemeTB = request.getParameterValues("schemeTB");
    	tctskuBean.setSchemeTB(schemeTB);
    	
    	String[] schemeDG = request.getParameterValues("schemeDG");
    	tctskuBean.setSchemeDiengiai(schemeDG);
    	
    	String[] diengiaiMuc = request.getParameterValues("diengiaiMuc");
    	tctskuBean.setDiengiaiMuc(diengiaiMuc);
    	
		String[] tumuc = request.getParameterValues("tumuc");
		tctskuBean.setTumuc(tumuc);
		
		String[] denmuc = request.getParameterValues("denmuc");
		tctskuBean.setDenmuc(denmuc);
		
		String[] thuongSR = request.getParameterValues("thuongSR");
		tctskuBean.setThuongSR(thuongSR);
		
		String[] thuongTDSR = request.getParameterValues("thuongTDSR");
		tctskuBean.setThuongTDSR(thuongTDSR);
		
		String[] thuongSS = request.getParameterValues("thuongSS");
		tctskuBean.setThuongSS(thuongSS);
		
		String[] thuongTDSS = request.getParameterValues("thuongTDSS");
		tctskuBean.setThuongTDSS(thuongTDSS);
		
		String[] thuongASM = request.getParameterValues("thuongASM");
		tctskuBean.setThuongASM(thuongASM);
		
		String[] thuongTDASM = request.getParameterValues("thuongTDASM");
		tctskuBean.setThuongTDASM(thuongTDASM);
		
 		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
 		System.out.println("Action la: " + action);
 		
 		if(action.equals("save"))
 		{    
    		if (id == null )
    		{
    			if (!tctskuBean.createTctSKU())
    			{
    		    	tctskuBean.setUserId(userId);
    		    	
    		    	tctskuBean.createRs();
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("tctskuBean", tctskuBean);
    				
    				String nextJSP = "/AHF/pages/Center/TieuChiThuongTBNew.jsp";
    				response.sendRedirect(nextJSP);
    			}
    			else
    			{
    				ITieuchithuongTBList obj = new TieuchithuongTBList();
				    obj.setUserId(userId);
				    
				    obj.init("");
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/AHF/pages/Center/TieuChiThuongTB.jsp";
					response.sendRedirect(nextJSP);
    			}	
    		}
    		else
    		{
    			if (!(tctskuBean.updateTctSKU()))
    			{			
    		    	tctskuBean.setUserId(userId);
    		    	
    		    	tctskuBean.createRs();
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("tctskuBean", tctskuBean);
    				
    				String nextJSP = "/AHF/pages/Center/TieuChiThuongTBUpdate.jsp";
    				response.sendRedirect(nextJSP);
    			}
				else
				{
					ITieuchithuongTBList obj = new TieuchithuongTBList();
				    obj.setUserId(userId);
				    
				    obj.init("");
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/AHF/pages/Center/TieuChiThuongTB.jsp";
					response.sendRedirect(nextJSP);
				}
    		}
	    }
		else
		{		
			tctskuBean.createRs();
			session.setAttribute("userId", userId);
			session.setAttribute("tctskuBean", tctskuBean);
			
			String nextJSP;
			if (id == null){
				nextJSP = "/AHF/pages/Center/TieuChiThuongTBNew.jsp";
			}
			else{
				nextJSP = "/AHF/pages/Center/TieuChiThuongTBUpdate.jsp";   						
			}
			response.sendRedirect(nextJSP);
		}
	}
	
	public String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
