package geso.dms.center.servlets.khuvuc;

import geso.dms.center.beans.khuvuc.imp.*;
import geso.dms.center.beans.khuvuc.*;
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

public class KhuvucUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private PrintWriter out;  

    public KhuvucUpdateSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		
		this.out = response.getWriter();
		Utility util = new Utility();
		String querystring = request.getQueryString();
		String action = util.getAction(querystring);
		
	    String userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    String id = "";
        String nextJSP = "/AHF/pages/Center/KhuVucUpdate.jsp";
        id = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("update"));
        if(action.equals("display")) {
        	nextJSP = "/AHF/pages/Center/KhuVucDisplay.jsp";
        	 id = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("display"));
        }
        
	    System.out.println("Mã: "+id);
	    System.out.println("Mã: "+id);
	    System.out.println("Mã: "+id);
	    out.println(userId);
	    IKhuvuc kvBean = new Khuvuc(id);
        kvBean.setUserId(userId);
        session.setAttribute("kvBean", kvBean);
        response.sendRedirect(nextJSP);
	}
	private String Doisangchuoi(String[] checknpp)
	{
		// TODO Auto-generated method stub
		String str = "";
		if (checknpp != null)
		{
			for (int i = 0; i < checknpp.length; i++)
			{
				if (i == 0)
				{
					str = checknpp[i];
				} else
				{
					str = str + "," + checknpp[i];
				}
			}
		}
		return str;

	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		IKhuvuc kvBean;
		this.out = response.getWriter();
		
		Utility util = new Utility();
		
	    String id =  util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
	    if(id == null){  	
	    	kvBean = new Khuvuc("");
	    }else{
	    	kvBean = new Khuvuc(id);
	    }
	    
	    
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		kvBean.setUserId(userId);
	    
		String khuvuc = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvuc")));
		if (khuvuc == null)
			khuvuc = "";				
    	kvBean.setTen(khuvuc);
    	
    	String vung = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungmien")));
		if (vung == null)
			vung = "";				
    	kvBean.setVmId(vung);
    	String asm = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("asm")));
    	if(asm == null)
    		asm="";
    	kvBean.setAsm(asm);
    	
    	String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		if (diengiai == null)
			diengiai = "";
		kvBean.setDiengiai(diengiai);
		String Ma = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Ma")));
		if (Ma == null)
			Ma = "";
		kvBean.setMa(Ma);
		String[] ttppId = request.getParameterValues("ttId");
		kvBean.setTtId(Doisangchuoi(ttppId));
		
		
		String[] QhId = request.getParameterValues("QhId");
		kvBean.setQhId(Doisangchuoi(QhId));
		
		
		


		String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
		System.out.println("trangthai:fefef "+trangthai);
    	if (trangthai == null||trangthai.length()==0)
    		trangthai = "0";
    	else
    		trangthai = "1";
    	kvBean.setTrangthai(trangthai);
    	
		String ngaysua = getDateTime();
    	kvBean.setNgaysua(ngaysua);
		
		String nguoisua = userId;
		kvBean.setNguoisua(nguoisua);
    	
		
		boolean error = false;
				
		if (khuvuc.trim().length()== 0){
			kvBean.setMessage("Vui lòng nhập tên khu vực!");
			error = true;
		}
		
		if (vung.trim().length()== 0){
			kvBean.setMessage("Vui lòng nhập vùng miền!");
			error = true;
		}

		if (diengiai.trim().length()== 0){
			kvBean.setMessage("Vui lòng nhập diễn giải!");
			error = true;
		}
		if (ttppId == null){
			kvBean.setMessage("Vui lòng chọn tỉnh thành!");
			error = true;
		}
 		if (ttppId != null) {
 			for (int i = 0; i < ttppId.length; i++) {
 				if (ttppId[i].equals("100049") || ttppId[i].equals("100000")) {
 					if (QhId == null) {
 		 				kvBean.setMessage("Vui lòng chọn quận huyện!");
 		 				error = true;
 		 			}
 				}
 			}
 		}
 		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (!error){
	    	if(action.equals("save"))
	    	{
	    		if ( id == null || id.length()==0){
	    			if (!(kvBean.CreateKv())){				
	    				session.setAttribute("kvBean", kvBean);
	    				kvBean.setUserId(userId);
	    				String nextJSP = "/AHF/pages/Center/KhuVucNew.jsp";
	    				response.sendRedirect(nextJSP);
	    			}else{
	    				IKhuvucList obj = new KhuvucList();
	    				obj.setUserId(userId);
	    				session.setAttribute("obj", obj);
						
	    				String nextJSP = "/AHF/pages/Center/KhuVuc.jsp";
	    				response.sendRedirect(nextJSP);			    			    									
	    			}
				
	    		}else{
	    			if (!(kvBean.UpdateKv())){			
	    				session.setAttribute("kvBean", kvBean);
	    				String nextJSP = "/AHF/pages/Center/KhuVucUpdate.jsp";
	    				response.sendRedirect(nextJSP);
	    			}
	    			else{
	    				IKhuvucList obj = new KhuvucList();
	    				obj.setUserId(userId);
	    				session.setAttribute("obj", obj);
						
	    				String nextJSP = "/AHF/pages/Center/KhuVuc.jsp";
	    				response.sendRedirect(nextJSP);			    			    									
	    			}
	    		}
	    	}else{
	    		kvBean.setUserId(userId);
	    		session.setAttribute("kvBean", kvBean);
			
	    		String nextJSP;
	    		if (id == null){			
	    			nextJSP = "/AHF/pages/Center/KhuVucNew.jsp";
	    		}else{
	    			nextJSP = "/AHF/pages/Center/KhuVucUpdate.jsp";   						
	    		}
	    		response.sendRedirect(nextJSP);
			
	    	}
	    }else{
    		kvBean.setUserId(userId);
    		kvBean.createVmRS();
    		session.setAttribute("kvBean", kvBean);
		
    		String nextJSP;
    		if (id == null){			
    			nextJSP = "/AHF/pages/Center/KhuVucNew.jsp";
    		}else{
    			nextJSP = "/AHF/pages/Center/KhuVucUpdate.jsp";   						
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
