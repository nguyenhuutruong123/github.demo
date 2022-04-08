package geso.dms.center.servlets.chuyennhanvienbanhang;


import geso.dms.center.beans.chuyennhanvienbanhang.IChuyennhanvienbanhang;
import geso.dms.center.beans.chuyennhanvienbanhang.imp.Chuyennhanvienbanhang;
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


public class chuyennhanvienbanhangSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    public chuyennhanvienbanhangSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		IChuyennhanvienbanhang obj;
		PrintWriter out; 
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	  
	    
	   
	    obj = new Chuyennhanvienbanhang();
	    obj.Init();
	    
	    obj.setUserId(userId);
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Center/Chuyennhanvienbanhang.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		IChuyennhanvienbanhang obj = new Chuyennhanvienbanhang();
		PrintWriter out; 
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
	    Utility util=new Utility();
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
		
		
	    
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    System.out.println ("action  : "+action);
	    
	   String nhappcu=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhappcu"));
	   obj.setIdNppCu(nhappcu);
	   
	   String nhappmoi=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhappmoi"));
	   obj.setIdNppMoi(nhappmoi);
	    
	    String NgayKs= util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngayks")));
	    String Iscopy= util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("iscopy")));
	    if(Iscopy==null){
	    	Iscopy="0";
	    }
	    obj.setIscopy(Iscopy);
	    
	    String[] nvbhIdchon=request.getParameterValues("nvbhidchon");
	    	
	    String chuoiid="";
	    if(nvbhIdchon!=null){
	    	for(int i=0;i<nvbhIdchon.length;i++)
	    	{
	    		if(i==0){
	    			chuoiid=nvbhIdchon[i];
	    		}else {
	    		chuoiid=chuoiid +","+nvbhIdchon[i];
	    		}	
	    	}
	    }
	    System.out.println(chuoiid);
	    obj.setIdNvbhChon(chuoiid);
	    
		if(action.equals("loadnhanvienbanhang"))
		{ 
			
		    obj.Init();
		}
		if(action.equals("save")){
			
			if( obj.ThucHienChuyen() )
				obj.setMsg("Chuyển nhân viên bán hàng thành công !");
			else
				obj.setMsg("Không chuyển được nhân viên bán hàng !");
			 obj.Init();
			 
		}
		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Center/Chuyennhanvienbanhang.jsp";
		response.sendRedirect(nextJSP);
	}
	  
	

	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
