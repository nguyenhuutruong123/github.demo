package geso.dms.distributor.servlets.nhanviengiaonhan;

import geso.dms.distributor.beans.nhanviengiaonhan.INhanviengiaonhan;
import geso.dms.distributor.beans.nhanviengiaonhan.INhanviengiaonhanList;
import geso.dms.distributor.beans.nhanviengiaonhan.imp.Nhanviengiaonhan;
import geso.dms.distributor.beans.nhanviengiaonhan.imp.NhanviengiaonhanList;
import geso.dms.distributor.util.Utility;

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

public class NhanviengnUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
    
    public NhanviengnUpdateSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		
		INhanviengiaonhan nvgnBean;
		PrintWriter out; 

		
		out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    	    
	    String id = util.getId(querystring);  	
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		else if( Utility.CheckRuleUser( session,  request, response, "KhachhangSvl","", Utility.SUA ))
		{
			Utility.RedireactLogin(session, request, response);
		} 
		else{
			nvgnBean = new Nhanviengiaonhan(id);
		    nvgnBean.setUserId(userId);
	        nvgnBean.init();
	        session.setAttribute("nvgnBean", nvgnBean);
	        String nextJSP = "/AHF/pages/Distributor/NhanVienGiaoNhanUpdate.jsp";
	        response.sendRedirect(nextJSP);
		}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		
		INhanviengiaonhan nvgnBean;
		PrintWriter out; 
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		
		out = response.getWriter();
		Utility util = new Utility();
		
	    String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));	
	    if(id == null){  	
	    	id="";
	    	nvgnBean = new Nhanviengiaonhan("");
	    }else{
	    	nvgnBean = new Nhanviengiaonhan(id);
	    }
	    	    
		userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		nvgnBean.setUserId(userId);
	    
    	String nvgnTen = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nvgnTen")));
		if (nvgnTen == null)
			nvgnTen = "";				
    	nvgnBean.setTennv(nvgnTen);
    	
    	String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
		if (nppId == null)
			nppId = "";
		nvgnBean.setNppId(nppId);
    	
		String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
		if (trangthai == null || trangthai.equals("2"))
			trangthai = "";
		nvgnBean.setTrangthai(trangthai);
		
		String diachi = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diachi")));
		if (diachi == null)
			diachi = "";
		nvgnBean.setDiachi(diachi);
		
		String dienthoai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dienthoai")));
		if (dienthoai == null)
			dienthoai = "";
		nvgnBean.setDienthoai(dienthoai);
		
		String[] ddkdIds = request.getParameterValues("ddkdIds");
		nvgnBean.setDdkdIds(ddkdIds);
		String[] ddkdId = request.getParameterValues("ddkdIdIds");
		String str = "";
		if(ddkdId != null)
		{
			for(int i = 0; i < ddkdId.length; i++)
				str += ddkdId[i] + ",";
			if(str.length() > 0)
				str = str.substring(0, str.length() - 1);
		}
		nvgnBean.setDdkdId(str);
		
		//String[] tbhIds = request.getParameterValues("tbhIds");
		//nvgnBean.setTbhIds(tbhIds);
		String[] tbhId = request.getParameterValues("tbhIds");
		String str2 = "";
		if(tbhId != null)
		{
			for(int i = 0; i < tbhId.length; i++)
				str2 += tbhId[i] + ",";
			if(str2.length() > 0)
				str2 = str2.substring(0, str2.length() - 1);
		}
		nvgnBean.setTbhId(str2);
		
		String[] khIds = request.getParameterValues("khIds");
		if(khIds != null)
		{
			String[] khSelected = new String[khIds.length];
			int i = 0;
			while(i < khIds.length)
			{
				khSelected[i] = khIds[i].substring(khIds[i].indexOf("-") + 1, khIds[i].length());
				i++;
			}
			nvgnBean.setKhIds(khSelected);
		}
		
		String ngaysua = getDateTime();
    	nvgnBean.setNgaysua(ngaysua);
		
		boolean error = false;
				
		if (nvgnTen.trim().length()== 0){
			nvgnBean.setMessage("Vui Long Nhap Ten Nhan Vien Giao Nhan");
			error = true;
		}

		if (diachi.trim().length()== 0){
			nvgnBean.setMessage("Vui Long Nhap Dia Chi Nhan Vien Giao Nhan");
			error = true;
		}
		
		if (dienthoai.trim().length()== 0){
			nvgnBean.setMessage("Vui Long Nhap So Dien Thoai Nhan Vien Giao Nhan");
			error = true;
		}
		
//		if (ddkdIds == null){
//			nvgnBean.setMessage("Vui Long Chon Dai Dien Kinh Doanh");
//			error = true;
//		}
		
//		if (tbhIds == null){
//			nvgnBean.setMessage("Vui Long Chon Tuyen Ban Hang");
//			error = true;
//		}
		
//		if (khIds == null){
//			nvgnBean.setMessage("Vui Long Chon Khach Hang");
//			error = true;
//		}
	
 		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if(!error){
	    	if(action.equals("save"))
	    	{
	    		if ( id.length()==0){
	    			if (!(nvgnBean.CreateNvgn(khIds))){	
	    				nvgnBean.createRS();
	    				session.setAttribute("nvgnBean", nvgnBean);			
	    				String nextJSP = "/AHF/pages/Distributor/NhanVienGiaoNhanNew.jsp";
	    				response.sendRedirect(nextJSP);
	    			}else{
	    				INhanviengiaonhanList obj = new NhanviengiaonhanList();
	    				obj.setUserId(userId);
	    				obj.init("");
	    				session.setAttribute("obj", obj);
						
	    				String nextJSP = "/AHF/pages/Distributor/NhanVienGiaoNhan.jsp";
	    				response.sendRedirect(nextJSP);			    			    									
	    			}
				
	    		}else{
	    			if (!(nvgnBean.UpdateNvgn(khIds)))
	    			{
	    				nvgnBean.init();
	    				session.setAttribute("nvgnBean", nvgnBean);
	    				String nextJSP = "/AHF/pages/Distributor/NhanVienGiaoNhanUpdate.jsp";
	    				response.sendRedirect(nextJSP);
	    			}
	    			else{
	    				INhanviengiaonhanList obj = new NhanviengiaonhanList();
	    				obj.setUserId(userId);
	    				obj.init("");
	    				session.setAttribute("obj", obj);
						
	    				String nextJSP = "/AHF/pages/Distributor/NhanVienGiaoNhan.jsp";
	    				response.sendRedirect(nextJSP);			    			    									
	    			}
	    		}
	    	}
	    	else
	    	{
				if(id.length() > 0){
					nvgnBean.init();
				}else{
					nvgnBean.createRS();
				}

	    		session.setAttribute("nvgnBean", nvgnBean);
			
	    		String nextJSP;
	    		if (id.length() == 0){			
	    			nextJSP = "/AHF/pages/Distributor/NhanVienGiaoNhanNew.jsp";
	    		}else{
	    			nextJSP = "/AHF/pages/Distributor/NhanVienGiaoNhanUpdate.jsp";   						
	    		}
	    		response.sendRedirect(nextJSP);		
	    	}
	    }else{
			if(id.length() > 0){
				nvgnBean.init();
			}else{
				nvgnBean.createRS();
			}
			session.setAttribute("nvgnBean", nvgnBean);
		
			String nextJSP;
			if (id.length() == 0){				
				nextJSP = "/AHF/pages/Distributor/NhanVienGiaoNhanNew.jsp";
			}else{
				nextJSP = "/AHF/pages/Distributor/NhanVienGiaoNhanUpdate.jsp";   						
			}
			response.sendRedirect(nextJSP);		
			
		}
		}

	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
