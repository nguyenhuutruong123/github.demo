package geso.dms.center.servlets.khott;

import geso.dms.center.beans.khott.ITonKhoTT;
import geso.dms.center.beans.khott.imp.TonKhoTT;
import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class TonKhoTTSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TonKhoTTSvl() {
        super();
        // TODO Auto-generated constructor stub
    }
    private String gettenuser(String userId_){
		dbutils db=new dbutils();
		String sql_getnam="select ten from nhanvien where pk_seq="+ userId_;
		ResultSet rs_tenuser=db.get(sql_getnam);
		if(rs_tenuser!= null){
			try{
			  while(rs_tenuser.next()){
				  return rs_tenuser.getString("ten");
			  }
			}catch(Exception er){
				return "";
			}
		}
		return "";
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Utility util=new Utility();
		
		request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    HttpSession session = request.getSession();
		    String querystring = request.getQueryString();
		    String userId = util.getUserId(querystring);
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		    //
		    session.setAttribute("userTen", gettenuser(userId));
		   // String id=util.getId(querystring);
		    //String action = util.getAction(querystring);	    
		    String sql_khott="select pk_seq,ten from khott where trangthai!='2'";
		    dbutils db=new dbutils();
		    ResultSet rs_khott=db.get(sql_khott);
		    session.setAttribute("rs_khott", rs_khott); 
		    String sql_getsanpham="select pk_seq,ma,ten from sanpham where trangthai!='2'";
		    ResultSet rs_sanpham=db.get(sql_getsanpham);
		    session.setAttribute("rs_sanpham", rs_sanpham);
        
		    ITonKhoTT obj = new TonKhoTT();	
          
           
           
		    obj.setListTonKhoTT("");
	    	//Data object is saved into session
	    	session.setAttribute("obj", obj);		
	    	// userId is saved into session
	    	session.setAttribute("userId", userId);
			session.setAttribute("sanphamid","");
	    	String nextJSP = "/AHF/pages/Center/XemTonKhoTT.jsp";
	    	response.sendRedirect(nextJSP);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub		
		HttpSession session =request.getSession();
		
		Utility util=new Utility();
		
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		//tu ten dang nhap,lay ra nha phan phoi	
		session.setAttribute("userId", userId);
		String khott_=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khott")));
		session.setAttribute("khottid",khott_);
		 String sql_khott="select pk_seq,ten from khott where trangthai!='2'";
         dbutils db=new dbutils();
         
         ResultSet rs_khott=db.get(sql_khott);
         session.setAttribute("rs_khott", rs_khott);       	
		
         String sanphamid=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sanphamid")));
		
		session.setAttribute("sanphamid",sanphamid);
         
         String sql_getsanpham="select pk_seq,ma,ten from sanpham where trangthai!='2'";
         ResultSet rs_sanpham=db.get(sql_getsanpham);
         session.setAttribute("rs_sanpham", rs_sanpham);    	
		  ITonKhoTT obj=new TonKhoTT();
		  String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
          if(dvkdId == null)
       	   dvkdId ="";
          obj.setdvkdId(dvkdId);
			//cau lenh truyen vao de tim kiem
			String sql1="select a.kho,a.masp,a.stock,a.booked,a.available,s.ten,s.pk_seq from tonkhoicp "+
          " a inner join sanpham s on s.ma=a.masp where a.masp <> '0' ";
		   
		    if(!sanphamid.equals("0")){
		    	sql1=sql1+" and s.ma like '%"+sanphamid +"%'";
		    	}
		    if(!khott_.equals("0")){
		    	sql1=sql1+" and a.kho='"+khott_+"'";
		    }
		    if(dvkdId.length() >0)
		    {
		    	sql1 = sql1 + " and s.dvkd_fk ='"+ dvkdId +"'";
		    }
	        //System.out.println(" C a u Lenh 159: "+  sql1);
			obj.setListTonKhoTT(sql1+" order by a.kho");	

			session.setAttribute("obj", obj);
			String nextJSP = "/AHF/pages/Center/XemTonKhoTT.jsp";
			session.setAttribute("userId", userId);
			response.sendRedirect(nextJSP);
	
	}
}
