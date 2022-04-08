package geso.dms.center.servlets.donmuahang;

import geso.dms.center.beans.asm.IAsm;
import geso.dms.center.beans.asm.IAsmList;
import geso.dms.center.beans.asm.imp.Asm;
import geso.dms.center.beans.asm.imp.AsmList;
import geso.dms.center.beans.congthucdndh.ICongthucdndh;
import geso.dms.center.beans.congthucdndh.imp.Congthucdndh;
import geso.dms.center.util.Utility;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CongthuDNDHSvl
 */
@WebServlet("/CongthuDNDHSvl")
public class CongthuDNDHSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CongthuDNDHSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Utility util = new Utility();

		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");

	    HttpSession session = request.getSession();

	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);

		ICongthucdndh congthuc =new Congthucdndh();
		
	    System.out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    System.out.println(action);
	    
	  

	    congthuc.setUserId(userId);
	    
	    
	    congthuc.init_Update();
		// Save data into session
		session.setAttribute("obj", congthuc);
	
		String nextJSP = "/AHF/pages/Center/CongThucDeNghiDatHang.jsp";
		response.sendRedirect(nextJSP);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Utility util = new Utility();
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");

	    HttpSession session = request.getSession();
	   
		ICongthucdndh congthuc =new Congthucdndh();
	    String ngaytktoithieu = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaytktoithieu")));
	    if(ngaytktoithieu == null) ngaytktoithieu = "0";
	    congthuc.setNgayTonKhoToithieu(ngaytktoithieu);
	    String muctangtruong = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("muctangtruong")));
	    if(muctangtruong == null) muctangtruong = "0";
	    congthuc.setMucTangTruong(muctangtruong);

	    String mucthue = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("mucthue")));
	    if(mucthue == null) mucthue = "0";
	    congthuc.setMucThue(mucthue);
	    
	
	    	congthuc.Save();
	    	

	    	    session.setAttribute("obj", congthuc);
	    	
	    	    String nextJSP = "/AHF/pages/Center/CongThucDeNghiDatHang.jsp";
	    		response.sendRedirect(nextJSP);	    		
	    
	    
	}

}
