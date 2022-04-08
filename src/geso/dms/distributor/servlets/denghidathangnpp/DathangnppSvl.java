package geso.dms.distributor.servlets.denghidathangnpp;

import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.denghidathangnpp.IDathangnpp;
import geso.dms.distributor.beans.denghidathangnpp.IDenghidathangnppList;
import geso.dms.distributor.beans.denghidathangnpp.imp.Dathangnpp;
import geso.dms.distributor.beans.denghidathangnpp.imp.DenghidathangnppList;
import geso.dms.distributor.db.sql.dbutils;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class DathangnppSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
   
    public DathangnppSvl() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		 IDathangnpp obj;
		request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
//		    PrintWriter out = response.getWriter();
		    			
			Utility util = (Utility) session.getAttribute("util");
			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);
			
				String action = util.getAction(querystring);
		   
				String id = util.getId(querystring);
		    
				if (action.equals("delete")){	   		  	    	
				//	Delete(dndhId);
				}
				 System.out.println("iam here id:" + id);
				obj = new Dathangnpp(id);
		    
				obj.setUserId(userId);
				//obj.setId(id);
				
				obj.init0();
				obj.init1();
				obj.init2();
				//obj.createDndhlist();
				session.setAttribute("obj", obj);
				String nextJSP = "/AHF/pages/Distributor/DatHangNpp.jsp";
				response.sendRedirect(nextJSP);
		}
			
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		 IDathangnpp obj;  
		request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
			
  	
			sum = (String) session.getAttribute("sum");
			Utility util = (Utility) session.getAttribute("util");
			String Id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
			obj = new Dathangnpp(Id);
			
			String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
			obj.setNppId(nppId);
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
			obj.setUserId(userId);
		//	String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
			String soluong[] = request.getParameterValues("soluong");
			obj.setSoluong(soluong);
			String tonkho[] = request.getParameterValues("tonkho");
			obj.setTonkho(tonkho);
			String masp[] = request.getParameterValues("masp");
			obj.setMasp(masp);
			
			String donvi[] = request.getParameterValues("donvi");
			obj.setDonvi(donvi);
			
			String dongia[] = request.getParameterValues("dongia");
			obj.setDongia(dongia);
			
			
			String gsbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gsbhId")));
			obj.setGsbhId(gsbhId);
			
			
			String tongchuavat = util.ValidateParam(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tongchuavat"))));
		    if(tongchuavat.length()>0)
		    	  obj.setTongtienbVAT(tongchuavat);
			String vat = util.ValidateParam(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vat"))));
			if(vat.length()>0)
		    obj.setVat(vat);
		    String tongcovat = util.ValidateParam(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tongcovat"))));
		    if(tongcovat.length()>0)
			obj.setTongtienaVAT(tongcovat);
				
		    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		    
		    if(action.equals("chot"))
		    {
		    	dbutils db = new dbutils();
		    	String sotien ="0";
		    	String st ="select * from dondathang where denghidathang_fk = '"+ Id +"'";
		    	ResultSet tb = db.get(st);
		    	try {
					tb.next();
					sotien = tb.getString("SOTIENAVAT");
				} catch(Exception e1) {
					
					e1.printStackTrace();
				}
		    	
		    	String sql ="update denghidathang set trangthai ='1',DADATHANG ='"+ sotien +"' where pk_seq ='"+ Id +"'";
		    	db.update(sql);
		    	
		    	System.out.println("chot:" +sql);
		    	db.update(sql);
		    	sql ="update dondathang set trangthai ='1' where denghidathang_fk ='"+ Id +"'";
		    	System.out.println("chot:" +sql);
		    	db.update(sql);
		    	
		    	sql ="select b.pk_seq,b.ma,a.soluong from dondathang_sp a,sanpham b where a.sanpham_fk = b.pk_seq and a.dondathang_fk in (select pk_seq from dondathang where denghidathang_fk='"+ Id +"')";
		    	ResultSet rs = db.get(sql);
		    	try {
		    		if(rs!=null)
					while(rs.next())
					{
						sql = "update tonkhoicp set AVAILABLE = AVAILABLE - '"+ rs.getString("soluong")+"',BOOKED = BOOKED + '"+ rs.getString("soluong")+"' where masp = '"+ rs.getString("ma")+"'";
						db.update(sql);
					}
				} catch(Exception e) {
					
					e.printStackTrace();
				}
		    	
		    	
				
			
		    	
		    	IDenghidathangnppList	objl = new DenghidathangnppList();
			    
				objl.setUserId(userId);
				objl.createDndhlist();
				session.setAttribute("obj", objl);
					
				String nextJSP = "/AHF/pages/Distributor/DeNghiDatHangNpp.jsp";
				response.sendRedirect(nextJSP);
			
		    }
		    else
		    {
		    if(obj.kiemtra_thieuhang())
		    {
		    	if(!obj.savedathang())
		    	{
		    		obj.init0();
					obj.init1();
			        //obj.init2();
					session.setAttribute("obj", obj);
					String nextJSP = "/AHF/pages/Distributor/DatHangNpp.jsp";
					response.sendRedirect(nextJSP);
					
		    	}
		    	else
		    	{
		    		IDenghidathangnppList	objl = new DenghidathangnppList();
				    
					objl.setUserId(userId);
					objl.createDndhlist();
					session.setAttribute("obj", objl);
						
					String nextJSP = "/AHF/pages/Distributor/DeNghiDatHangNpp.jsp";
					response.sendRedirect(nextJSP);
				

		    	}
		    }
		    else
		    {obj.init0();
		    obj.init1();
			//obj.init2();
	        
			session.setAttribute("obj", obj);
			String nextJSP = "/AHF/pages/Distributor/DatHangNpp.jsp";
			response.sendRedirect(nextJSP);
		    }
		  }
	}}

}
