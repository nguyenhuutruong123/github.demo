package geso.dms.center.servlets.phieuthanhtoantt;

import geso.dms.center.beans.phieuthanhtoantt.IPhieuThanhToanTT;
import geso.dms.center.beans.phieuthanhtoantt.imp.PhieuThanhToanTT;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PhieuThanhToanTTSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	IPhieuThanhToanTT obj;
	PrintWriter out; 
	String userId;
	String msg = "";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PhieuThanhToanTTSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    //out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	    //out.println(action);
	    	    
	  
	    if (action.equals("delete"))
	    {	   		
	    	  String pttId = util.getId(querystring);
	  	    obj.setId(pttId);
	    	if(!obj.DeleteThanhToan())
	    	{
	    		out.print("Khong the xoa phieuthanhtoan: " + this.msg + " <br />Vui long lien he voi admin..");
	    		return;
	    	}
	    }
	   
	    else  if (action.equals("chotphieu"))
	    {	   		
	    	  String pttId = util.getId(querystring);
	  	    obj.setId(pttId);
	    	if(!obj.ChotThanhToan() )
	    	{
	    		out.print("Khong the chot phieuthanhtoan: " + this.msg + " <br />Vui long lien he voi admin..");
	    		return;
	    	}
	    }
	 
	    //System.out.println("useraaaa:"+userId);
	    obj = new PhieuThanhToanTT();
	    obj.setUserId(userId);
	    
	    obj.setListThanhToan("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Center/PhieuThanhToanTT.jsp";
		response.sendRedirect(nextJSP);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out = response.getWriter();
	    
		HttpSession session = request.getSession();
		
		Utility util = new Utility();
		
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	     obj = new PhieuThanhToanTT();
	    obj.setUserId(userId);
	    
	    String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
	    if(diengiai ==null)
	    	diengiai ="";
	    obj.setDiengiai(diengiai);
	    
	    String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
	    if(tungay == null)
	    	tungay ="";
	    obj.setTungay(tungay);
	    String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
	    if(denngay ==null)
	    	denngay ="";
	    obj.setDenngay(denngay);
	    
	    String sotien = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sotien")));
	   try{
	    obj.setSotien(Double.parseDouble(sotien));
	   }catch(Exception er){ 
		   obj.setSotien(0);
	   }
	   
	    String action  = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));
	   
	 
	    if(action.equals("new"))
	     {
	    	IPhieuThanhToanTT	obj = new PhieuThanhToanTT();
	    	obj.setUserId(userId);
	        session.setAttribute("obj",obj);
	        String nextJSP = "/AHF/pages/Center/PhieuThanhToanTTNew.jsp";
	        response.sendRedirect(nextJSP);
	     }
	    else {
	    	obj.setUserId(userId);
	    	
	    	String sql="";
	    	sql="select a.pk_seq,a.ngaythanhtoan,a.hinhthuctt,a.diengiai,a.trangthai,a.sotien, a.ngaytao,a.ngaysua,nt.ten as nguoitao,ns.ten as nguoisua from phieuthanhtoantt a "+
			" inner join nhanvien as nt on nt.pk_seq=a.nguoitao inner join nhanvien as ns on ns.pk_seq=a.nguoisua where a.trangthai!='2'";
	    	if(!diengiai.equals("")){
	    		sql=sql+"  and a.diengiai like '%"+diengiai+"%'"; 
	    	}
	    	double sotien1=0;
	    	try{
	    	 sotien1=Double.parseDouble(sotien);
	    	 
	    	}catch(Exception er){
	    	}
	    	if(sotien1>0){
	    		sql=sql+ " and a.sotien="+ sotien1;
	    	}
	    	if(!tungay.equals("")){
	    		sql=sql+ " and a.ngaythanhtoan >= '"+tungay+"'";
	    	}
	    	
	    	if(!denngay.equals("")){
	    		sql=sql+ " and a.ngaythanhtoan <= '"+denngay+"'";
	    	}
	    	obj.setListThanhToan(sql);
	    	//System.out.println("cau lenh tim kiem :  "+sql);
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
    		response.sendRedirect("/AHF/pages/Center/PhieuThanhToanTT.jsp");	
	    }
	}

}
