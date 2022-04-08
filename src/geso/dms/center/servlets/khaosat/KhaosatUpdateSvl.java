package geso.dms.center.servlets.khaosat;

import geso.dms.center.beans.khaosat.IKhaosat;
import geso.dms.center.beans.khaosat.IKhaosatList;
import geso.dms.center.beans.khaosat.imp.Khaosat;
import geso.dms.center.beans.khaosat.imp.KhaosatList;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class KhaosatUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
       
    public KhaosatUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IKhaosat csxBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    	    
	    String id = util.getId(querystring);
	    csxBean = new Khaosat(id);
	    
	    csxBean.setId(id);
	    csxBean.setUserId(userId);
	    
	    csxBean.init();
	    String copy = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("copy"));
	    if(copy == null)
	    	copy = "";
	    String nextJSP = "";
	    if(copy.length() > 0 )
	    {
	    	 csxBean.setId("");
	    	 session.setAttribute("csxBean", csxBean);
	         
	          nextJSP = "/AHF/pages/Center/KhaoSatNew.jsp";
	    }
	    else
	    {
	  
	        session.setAttribute("csxBean", csxBean);
	        
	         nextJSP = "/AHF/pages/Center/KhaoSatUpdate.jsp";
	        if(querystring.indexOf("display") >= 0)
	        {
	        	nextJSP = "/AHF/pages/Center/KhaoSatDisplay.jsp";
	        }
	    }
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IKhaosat csxBean;

		Utility util = new Utility();
	    String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));	
	    if(id == null){  	
	    	csxBean = new Khaosat();
	    }else{
	    	csxBean = new Khaosat(id);
	    }
	    	    
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		csxBean.setUserId(userId);	       
		
		String tieude = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tieude")));
		if (tieude == null)
			tieude = "";
		csxBean.setTieude(tieude);
		
		String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		if (diengiai == null)
			diengiai = "";
		csxBean.setDiengiai(diengiai);
		
		String bophan = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("bophan")));
		if (bophan == null)
			bophan = "";
		csxBean.setBophan(bophan);
		
		String doituong = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("doituong")));
		if (doituong == null)
			doituong = "";
		csxBean.setDoituong(doituong);
		
		
		String socauhoi = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("socauhoi")));
		if (socauhoi == null)
			socauhoi = "";
		csxBean.setSocauhoi(socauhoi);
		
		String loaict = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaict")));
		if (loaict == null)
			loaict = "0";
		csxBean.setLoaict(loaict); 
		
		String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
		if (tungay == null)
			tungay = "";
		csxBean.setTungay(tungay);
		
		String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
		if (denngay == null)
			denngay = "";
		csxBean.setDenngay(denngay);
		
		/*String kvId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")));
    	if(kvId == null)
    		kvId = "";
    	csxBean.setKhuvucId(kvId);
    	
		String vungId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")));
		if(vungId == null)
			vungId = "";
		csxBean.setVungId(vungId);*/
		
		/*String[] nvIds = request.getParameterValues("nvIds");
	    if(nvIds != null)
	    {
	    	String nvId = "";
	    	for(int i = 0; i < nvIds.length; i++)
	    		nvId += nvIds[i] + ",";
	    	
	    	if(nvId.trim().length() > 0)
	    	{
	    		nvId = nvId.substring(0, nvId.length() - 1);
	    		csxBean.setDdkdId(nvId);
	    	}
	    }*/
		
		if(socauhoi.trim().length() > 0)
		{
			Hashtable<String, String> cauhoi_noidung = new Hashtable<String, String>();
			
			for(int i = 0; i < Integer.parseInt(socauhoi.trim()); i++)
			{
				String cauhoiId = "cau" + (i + 1);
				
				String loaicauhoi = request.getParameter(cauhoiId + "_loaicauhoi");
				if(loaicauhoi == null)
					loaicauhoi = "0";
				
				String cauhoi = request.getParameter(cauhoiId + "_cauhoi");
				if(cauhoi == null || cauhoi.trim().length() <= 0 )
					cauhoi = " ";
				
				String huongdantraloi = request.getParameter(cauhoiId + "_huongdantraloi");
				if(huongdantraloi == null || huongdantraloi.trim().length() <= 0)
					huongdantraloi = " ";
				
				String cautraloi = "";
				String[] motluachon = request.getParameterValues(cauhoiId + "_motluachon");
				String[] nhieuluachon = request.getParameterValues(cauhoiId + "_nhieuluachon");
				
				if(loaicauhoi.equals("1"))
				{
					if(motluachon != null)
					{
						//System.out.println("__Do dai dap an mot lua chon: " + motluachon.length);
						for(int j = 0; j < motluachon.length; j++)
						{
							if(motluachon[j].trim().length() <= 0)
								motluachon[j] = "NA";
							cautraloi += motluachon[j] + "__";
						}
						
						if(cautraloi.trim().length() > 0)
							cautraloi = cautraloi.substring(0, cautraloi.length() - 2);
					}
				}
				else
				{
					if(loaicauhoi.equals("2"))
					{
						if(nhieuluachon != null)
						{
							//System.out.println("__Do dai dap an nhieu lua chon: " + nhieuluachon.length);
							for(int j = 0; j < nhieuluachon.length; j++)
							{
								if(nhieuluachon[j].trim().length() <= 0)
									nhieuluachon[j] = "NA";
								cautraloi += nhieuluachon[j] + "__";
							}
							
							if(cautraloi.trim().length() > 0)
								cautraloi = cautraloi.substring(0, cautraloi.length() - 2);
						}
					}
					else
					{
						cautraloi = " ";
					}
				}
				
				System.out.println("---- " + cauhoiId + ": " + loaicauhoi + ",," + cauhoi + ",," + huongdantraloi + ",," + cautraloi);
				cauhoi_noidung.put(cauhoiId, loaicauhoi + ",," + cauhoi + ",," + huongdantraloi + ",," + cautraloi );
			}
			
			//System.out.println("___Cau 1: " + cauhoi_noidung.get("cau1"));
			//System.out.println("___Cau 2: " + cauhoi_noidung.get("cau2"));
			
			csxBean.setNoidungcauhoi(cauhoi_noidung);
		}
		
		
 		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
 		if(action.equals("save"))
 		{
 			if(id == null||id.length()==0)
 			{
	 			if (!(csxBean.createKhaosat()))
				{
	 				csxBean.createRs();
	 				session.setAttribute("csxBean", csxBean);  	
	 	    		session.setAttribute("userId", userId);
	 			
					String nextJSP = "/AHF/pages/Center/KhaoSatNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IKhaosatList obj = new KhaosatList();
					obj.setUserId(userId);
					obj.init("");
	
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/AHF/pages/Center/KhaoSat.jsp";
					response.sendRedirect(nextJSP);
				}
 			}
 			else
 			{
 				if (!(csxBean.updateKhaosat()))
				{
 					csxBean.createRs();
	 				session.setAttribute("csxBean", csxBean);  	
	 	    		session.setAttribute("userId", userId);
	 			
					String nextJSP = "/AHF/pages/Center/KhaoSatUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IKhaosatList obj = new KhaosatList();
					obj.setUserId(userId);
					obj.init("");
	
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/AHF/pages/Center/KhaoSat.jsp";
					response.sendRedirect(nextJSP);
				}
 			}
	    }
		else
		{
			csxBean.createRs();
			session.setAttribute("userId", userId);
			session.setAttribute("csxBean", csxBean);
			
			String nextJSP = "/AHF/pages/Center/KhaoSatNew.jsp";
			
			if( id != null )
			{
				 nextJSP = "/AHF/pages/Center/KhaoSatUpdate.jsp";
			}
			
			response.sendRedirect(nextJSP);
		}		
	}
	
	
}
