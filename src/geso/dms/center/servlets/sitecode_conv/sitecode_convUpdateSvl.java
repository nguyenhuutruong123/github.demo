package geso.dms.center.servlets.sitecode_conv;

import geso.dms.center.beans.nhaphanphoi.INhaphanphoi;
import geso.dms.center.beans.nhaphanphoi.imp.Nhaphanphoi;
import geso.dms.center.beans.sitecode_conv.Isitecode_conv;
import geso.dms.center.beans.sitecode_conv.imp.sitcode_conv;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class sitecode_convUpdateSvl
 */
public class sitecode_convUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
    /**
     * @see HttpServlet#HttpServlet()
     */
    public sitecode_convUpdateSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//this.out = response.getWriter();
		Isitecode_conv obj;
		Utility util = new Utility();
		HttpSession session=request.getSession();
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    System.out.println(userId);
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));	    
	    String sitecode = util.getId(querystring);  
	   String action=util.getAction(querystring);
	   if(action.equals("update")){
	    obj = new sitcode_conv(sitecode);
	    obj.setRsKhuvuc();
        obj.setUserid(userId);
        obj.setRsNppTienNhiem();
       session.setAttribute("obj", obj);
        String nextJSP = "/AHF/pages/Center/sitecode_convupdate.jsp";
        response.sendRedirect(nextJSP);
	   }else if(action.equals("chot")){
		   obj = new sitcode_conv(sitecode);
	        obj.setUserid(userId);
	        obj.setngaysua(getDateTime());
	        
	        	if(!obj.chot()){
	        		obj.setMsg("Khong The Chot,Vui Long Thu Lai,Neu Khong Duoc Vui Long Lien He Voi Admin,De Duoc Giup Do ");
	        	}else{
	        		obj.setMsg("");
	        	} 
	        	obj.Init("");
	     	    obj.settrangthai("");
	     	    obj.setten("");
	     		session.setAttribute("obj", obj);
	     		String nextJSP = "/AHF/pages/Center/sitecode_conv.jsp";
	        response.sendRedirect(nextJSP);
	   }
	   
       
	}
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();
		Isitecode_conv obj;
		obj=new sitcode_conv();
		Utility util = new Utility();
		String sitecode=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sitecode")));
		obj.setsitecode(sitecode);
		
		obj = new sitcode_conv(sitecode);
		String userId=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
        
		obj.setUserid(userId);
        obj.setngaytao(this.getDateTime());
        
        obj.setnguoitao(userId);
		String npptn=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("npptn")));
		
		
		String ngayks=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngayks")));
		obj.NgayKhoaSo(ngayks);
		
		obj.setIdNppTienNhiem(npptn);
		
		String tennpp=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("TenNCC_long")));
		obj.setten(tennpp);
		
		String khuvucid=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucid")));
		
		if(khuvucid ==null){
			khuvucid="";
		}
		obj.setkhuvucId(khuvucid);
		System.out.println("khuvuc :" + khuvucid);
		String action=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		String nextJSP = "/AHF/pages/Center/sitecode_conv.jsp";
		//System.out.println("action :" + action);
		if(action.equals("save")){
			//truong hop save chi thuc hien luu them convsitecode vao bang sitecode_conv
			if(!obj.save()){
				obj.setRsKhuvuc();	
				  obj.setRsNppTienNhiem();
		        nextJSP = "/AHF/pages/Center/sitecode_convupdate.jsp";
			}else{
				obj=new sitcode_conv();
				 obj.Init("");
				 nextJSP = "/AHF/pages/Center/sitecode_conv.jsp";
			}
		}else if(action.equals("chot")) {
			if(!obj.chot()){
				obj.setRsKhuvuc();	
				  obj.setRsNppTienNhiem();
			         nextJSP = "/AHF/pages/Center/sitecode_convupdate.jsp";
				}else{
					obj=new sitcode_conv();
					 obj.Init("");
					 nextJSP = "/AHF/pages/Center/sitecode_conv.jsp";
				}
			
		}else if(action.equals("submit")){
			obj.setRsKhuvuc();	
			 obj.setRsNppTienNhiem();
	         nextJSP = "/AHF/pages/Center/sitecode_convupdate.jsp";
			
		}else if(action.equals("nppmoi")){
			if(!obj.TaoNPPMoi()){
					obj.setRsKhuvuc();	
					obj.setRsNppTienNhiem();
					nextJSP = "/AHF/pages/Center/sitecode_convupdate.jsp";
				}else{
					obj=new sitcode_conv();
					obj.Init("");
					nextJSP = "/AHF/pages/Center/sitecode_conv.jsp";
				}
		}
		 session.setAttribute("obj", obj);
		  response.sendRedirect(nextJSP);
	}

}
