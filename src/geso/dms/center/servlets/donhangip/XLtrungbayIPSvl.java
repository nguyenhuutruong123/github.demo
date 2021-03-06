package geso.dms.center.servlets.donhangip;

import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.donhang.IDonhang;
import geso.dms.distributor.beans.donhang.imp.Donhang;
import geso.dms.distributor.beans.donhang.imp.XLTrungbay;
import geso.dms.distributor.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class XLtrungbayIPSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
           
    public XLtrungbayIPSvl() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		
		String nextJSP; 
		XLTrungbay xltb;
		IDonhang dhBean;
		dbutils db;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    
	    Utility util = new Utility();
	    userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));	
	    String dhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dhId")));
	    System.out.println("dhId : "+dhId);
	   
	    String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));	    
	    String khId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khId")));
	    String kenhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")));
	    String khoId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khoId")));
	    System.out.println("khoId : "+khoId);
	    String ngaydh = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaydh")));	    
	    
	    String chon = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chon")));
	    System.out.println("Chon : "+chon);
	    
	    xltb = new XLTrungbay(dhId, khId, nppId, ngaydh, khoId);
	    xltb.setIdDonhang(dhId);
	    xltb.setNgaydh(ngaydh);
	    xltb.setIdkho(khoId);
	    xltb.setIdkh(khId);
	    xltb.setIdkenh(kenhId);
	    xltb.setUserId(userId);
	    xltb.setIdnpp(nppId);
 		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	  
 		dhBean = (IDonhang) new Donhang(dhId);
 		
	    	if(action.equals("save"))
	    	{		    
	    		int cotrungbay = xltb.luutrungbay(chon);
				String thongbao = xltb.getCotrungbay();
								
				//if( cotrungbay != 0 )
				//{					
					dhBean.setKhId(khId);
					dhBean.setUserId(userId);
					dhBean.setKhoId(khoId);
					dhBean.setNppId(nppId);
					dhBean.init();
					
					System.out.println("KH : "+khId+" - KHO : "+khoId+" - NPP : "+nppId+" - DH : "+dhId);
					
					if(cotrungbay == 1)
					{
						dhBean.setMessage("????n h??ng: " + dhBean.getId() + " th???a ch????ng tr??nh tr??ng b??y: " + thongbao);
					}
					else
					{
						dhBean.setMessage("L???i khi c???p nh???t ch????ng tr??nh tr??ng b??y: " + thongbao);
					}
					dhBean.setAplaitrugnbay(false);
					session.setAttribute("dhBean", dhBean);
					nextJSP = "/AHF/pages/Distributor/DonHangUpdate.jsp";
					response.sendRedirect(nextJSP);
					return;
				//}
				/*else if(cotrungbay == 0)
				{
					dhBean.setKhId(khId);
					dhBean.setUserId(userId);
					dhBean.setKhoId(khoId);
					dhBean.setNppId(nppId);
					dhBean.init();
					
					System.out.println("Kh??ng th???a tr??ng b??y !");									
					dhBean.setMessage("????n h??ng: " + dhBean.getId() + " kh??ng th???a ch????ng tr??nh tr??ng b??y: " + thongbao);					
					dhBean.setAplaitrugnbay(false);
					session.setAttribute("dhBean", dhBean);
					nextJSP = "/AHF/pages/Distributor/DonHangUpdate.jsp";
					response.sendRedirect(nextJSP);
					return;
				}*/
					    		
	    	}	    	  
	    			    		
		}
	}	

}
