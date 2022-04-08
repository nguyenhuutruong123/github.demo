package geso.dms.center.servlets.khaosat;

import geso.dms.center.beans.khaosat.IThongkekhaosat;
import geso.dms.center.beans.khaosat.imp.Thongkekhaosat;
import geso.dms.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ThongkekhaosatUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
       
    public ThongkekhaosatUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IThongkekhaosat csxBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    	    
	    String id = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khaosatId"));
	    String tennguoiKs = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("hoten"));
	    String dienthoaiKs = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dienthoai"));
	   
	    csxBean = new Thongkekhaosat(id);
	    csxBean.setTennguoiks(tennguoiKs);
	    csxBean.setSodienthoai(dienthoaiKs);
	    
	    csxBean.setId(id);
	    csxBean.setUserId(userId);
	    
	    csxBean.init();
        session.setAttribute("csxBean", csxBean);
        
        String nextJSP = "/AHF/pages/Center/ThongKeKhaoSatDisplay.jsp";
       
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
			
	}
	
	
}
