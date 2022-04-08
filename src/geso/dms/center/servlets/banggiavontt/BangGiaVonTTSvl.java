package geso.dms.center.servlets.banggiavontt;

import geso.dms.center.beans.banggiavontt.IBangGiaVonTT;
import geso.dms.center.beans.banggiavontt.imp.BangGiaVonTT;
import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BangGiaVonTTSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BangGiaVonTTSvl() {
        super();
        // TODO Auto-generated constructor stub
    }
    private String gettenuser(String userId_){
    	Utility util=new Utility();
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
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");		    		 
	    HttpSession session = request.getSession();
	    String querystring = request.getQueryString();
	    Utility util=new Utility();
	    String userId = util.getUserId(querystring);
	    session.setAttribute("userId", userId);
	    //
	    session.setAttribute("userTen", gettenuser(userId));   
	    session.setAttribute("dvkdId","");
	    session.setAttribute("bgTen","" );
	    String sql_dvkd="select pk_seq,donvikinhdoanh as ten from donvikinhdoanh where trangthai!='0'";
     	dbutils db=new dbutils();
     	ResultSet rs_dvkd=db.get(sql_dvkd);
     	session.setAttribute("rs_dvkd", rs_dvkd); 
     	IBangGiaVonTT obj = new BangGiaVonTT();	     
	    obj.setListBangGia("");
    	//Data object is saved into session
    	session.setAttribute("obj", obj);		
    	// userId is saved into session
    	
    	String nextJSP = "/AHF/pages/Center/BangGiaVonTT.jsp";
    	response.sendRedirect(nextJSP);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session =request.getSession();
		Utility util = new Utility();
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		//tu ten dang nhap,lay ra nha phan phoi	
		session.setAttribute("userId", userId);
		String userTen =util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userTen")));
		session.setAttribute("userTen",userTen);		
		String dvkdId=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));		
		String action=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		String bgTen=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("bgTen")));
		 String sql_dvkd="select pk_seq,donvikinhdoanh as ten from donvikinhdoanh where trangthai!='0'";
	     	dbutils db=new dbutils();
	     	ResultSet rs_dvkd=db.get(sql_dvkd);
	     	session.setAttribute("rs_dvkd", rs_dvkd); 
		  IBangGiaVonTT obj=new BangGiaVonTT();
		  obj.setDvkdId(dvkdId);
		  
		  obj.setTen(bgTen);
		  
		  	if(action.equals("search")){
			//cau lenh truyen vao de tim kiem
		  	 String	 sql_getdata="select a.pk_seq,a.dvkd_fk,d.donvikinhdoanh  ,a.ngaytao,a.tenbanggia,a.ngaysua ,ns.ten as nguoisua,nt.ten as nguoitao "+
		 		" from banggiavontt a inner join nhanvien ns on ns.pk_seq=a.nguoisua inner join nhanvien nt on nt.pk_seq=a.nguoitao inner join donvikinhdoanh d on d.pk_seq =a.dvkd_fk where 1=1 ";
		    if(!dvkdId.equals("")){
		    	sql_getdata=sql_getdata+" and a.dvkd_fk= "+dvkdId;
		    	
		    }
		    if(!bgTen.equals("")){
		    	sql_getdata=sql_getdata+" and a.tenbanggia =N'"+bgTen+"'";
		    	
		    }
		    obj.setListBangGia(sql_getdata);
		    session.setAttribute("obj", obj);
		    String nextJSP = "/AHF/pages/Center/BangGiaVonTT.jsp";
			session.setAttribute("userId", userId);
			response.sendRedirect(nextJSP);
			
			}else{//truong hop them moi
				//loc ra nhung dvkd chua co bang gia
				 String sql_dvkd1="select pk_seq ,donvikinhdoanh as ten from donvikinhdoanh where trangthai!='0' and pk_seq not in  (select dvkd_fk from banggiavontt)";
			     	ResultSet rs_dvkd1=db.get(sql_dvkd1);
			     	session.setAttribute("rs_dvkd", rs_dvkd1); 
				 obj.setDvkdId("");
				  obj.setTen("");
				  session.setAttribute("check","0");
				  session.setAttribute("obj", obj);
				    String nextJSP = "/AHF/pages/Center/BangGiaVonTTNew.jsp";
					session.setAttribute("userId", userId);
					response.sendRedirect(nextJSP);
			}
			   
	}

}
