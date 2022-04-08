package geso.dms.center.servlets.reports;

import geso.dms.center.beans.chart.IChart;
import geso.dms.center.beans.chart.imp.Chart;
import geso.dms.distributor.util.Utility;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BCChartTongNhanVien extends HttpServlet
{
	private static final long serialVersionUID = 1L;

    public BCChartTongNhanVien() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		IChart obj = new Chart();
		String querystring = request.getQueryString();
		
		Utility util = new Utility();
		obj.setUserId(util.getUserId(querystring));
		obj.setUserTen((String) session.getAttribute("userTen"));
		
		session.setAttribute("obj", obj);		
		session.setAttribute("userId", obj.getUserId());
		session.setAttribute("userTen", obj.getUserTen());
		
		String nextJSP = "/AHF/pages/Center/BCChartNhanVien.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		IChart obj = new Chart();
		String userId = (String) session.getAttribute("userId");
		obj.setUserId(userId);
		
		String userTen = (String) session.getAttribute("userTen");
		obj.setUserTen(userTen);
		
		String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		System.out.println("1.Trang thai la: " + trangthai);
		
		String muclay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("muclay"));
		if(muclay == null)
			muclay = "0";
		obj.setMuclay(muclay);

		obj.initChartNV();
		session.setAttribute("obj", obj);
		
		String nextJSP = "/AHF/pages/Center/BCChartNhanVien.jsp";
		response.sendRedirect(nextJSP);
	}
	
	

}