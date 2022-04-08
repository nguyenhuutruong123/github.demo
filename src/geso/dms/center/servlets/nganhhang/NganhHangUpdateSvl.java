package geso.dms.center.servlets.nganhhang;
import geso.dms.center.beans.nganhhang.*;
import geso.dms.center.beans.nganhhang.imp.*;
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

public class NganhHangUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private PrintWriter out;  

    public NganhHangUpdateSvl() 
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
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String id = util.getId(querystring);  	
	    String action= util.getAction(querystring);
	    INganhHang nhBean = new NganhHang(id);	    
	    
        nhBean.setUserId(userId);
        session.setAttribute("nhBean", nhBean);
        String nextJSP = "/AHF/pages/Center/NganhHangUpdate.jsp";
        if(action.equals("display"))
        	nextJSP = "/AHF/pages/Center/NganhHangDisplay.jsp";
        
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("Minh Dung da vo day.");
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		INganhHang nhBean;
		this.out = response.getWriter();
		
		Utility util = new Utility();
		
	    String id =  util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
	    if(id == null){  	
	    	nhBean = new NganhHang("");
	    }else{
	    	nhBean = new NganhHang(id);
	    }
	    
	    
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		nhBean.setUserId(userId);
    	System.out.println("[NganhHangUpdateSvl.doPost] userId = " + nhBean.getUserId());
	    
    	String ten = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ten")));
		if (ten == null)
			ten = "";				
    	nhBean.setTen(ten.trim());
    	System.out.println("[NganhHangUpdateSvl.doPost] ten = " + nhBean.getTen());
    	
    	String thuexuatStr = "10";
    	if (thuexuatStr == null)
    		thuexuatStr = "0";
    	nhBean.setThuexuat(Double.parseDouble(thuexuatStr));
    	System.out.println("Thue = "+thuexuatStr);
    	System.out.println("Thue = "+thuexuatStr);
    	System.out.println("[NganhHangUpdateSvl.doPost] ten = " + nhBean.getTen());
    	
    	
    	String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		if (diengiai == null)
			diengiai = "";
		nhBean.setDiengiai(diengiai.trim());
    	System.out.println("[NganhHangUpdateSvl.doPost] diengiai = " + nhBean.getDiengiai());
		
		String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkd")));
    	if ( dvkdId == null) { dvkdId = ""; }
    	nhBean.setDvkdId(dvkdId.trim());
    	System.out.println("[NganhHangUpdateSvl.doPost] dvkdId = " + nhBean.getDvkdId());

		String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
    	if (trangthai == null || trangthai.trim().length()==0)
    		trangthai = "0";
    	nhBean.setTrangthai(trangthai.trim());
    	System.out.println("[NganhHangUpdateSvl.doPost] trangthai = " + nhBean.getTrangthai());
    	
		String ngaysua = getDateTime();
    	nhBean.setNgaysua(ngaysua);
    	System.out.println("[NganhHangUpdateSvl.doPost] ngaysua = " + nhBean.getNgaysua());
		
		String nguoisua = userId;
		nhBean.setNguoisua(nguoisua);
    	System.out.println("[NganhHangUpdateSvl.doPost] nguoisua = " + nhBean.getNguoisua());
 		
 		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    
		if(action.equals("save"))
		{
			if ( id == null || id.trim().length()==0) {
				if (!(nhBean.create())){				
					session.setAttribute("nhBean", nhBean);
					nhBean.setUserId(userId);
					
					String nextJSP = "/AHF/pages/Center/NganhHangNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					INganhHangList obj = new NganhHangList();
					obj.setUserId(userId);
					obj.init("");
					session.setAttribute("obj", obj);
						
					String nextJSP = "/AHF/pages/Center/NganhHang.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
				
			} else {
				if (!(nhBean.update())){			
					session.setAttribute("nhBean", nhBean);
					String nextJSP = "/AHF/pages/Center/NganhHangUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else {
					INganhHangList obj = new NganhHangList();
					obj.setUserId(userId);
					obj.init("");
					session.setAttribute("obj", obj);
					String nextJSP = "/AHF/pages/Center/NganhHang.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
			}
		} 
		else 
		{
			nhBean.setUserId(userId);
			session.setAttribute("nhBean", nhBean);
			
			String nextJSP;
			if (id == null){			
				nextJSP = "/AHF/pages/Center/NganhHangNew.jsp";
			}else{
				nextJSP = "/AHF/pages/Center/NganhHangUpdate.jsp";   						
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
