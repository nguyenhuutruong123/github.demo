package geso.dms.center.servlets.hoadon;

import geso.dms.center.beans.hoadon.IHoaDon;
import geso.dms.center.beans.hoadon.imp.HoaDon;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class HoaDonKMSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HoaDonKMSvl() {
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
		   request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    
		    HttpSession session = request.getSession();

		    String querystring = request.getQueryString();
		    
		    Utility util= new  Utility();
		    
		    String userId = util.getUserId(querystring);
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		    //
		    session.setAttribute("userTen", gettenuser(userId));
		  
		    dbutils db=new dbutils();
		    String sql_getnhapp="select pk_seq,ten from nhaphanphoi where trangthai!='2'";
			ResultSet rs_nhapp=db.get(sql_getnhapp);
			session.setAttribute("rs_nhapp", rs_nhapp);
			
			String sql_getnhacc="select pk_seq,ten from nhacungcap where trangthai!='2'";
			ResultSet rs_nhacc=db.get(sql_getnhacc);
			session.setAttribute("rs_nhacc", rs_nhacc);
			
			String sql_getkenhbanhang="select pk_seq,ten from kenhbanhang where trangthai!='2'";
			ResultSet rs_kenhbanhang=db.get(sql_getkenhbanhang);
			session.setAttribute("rs_kenhbanhang", rs_kenhbanhang);
			
			String sql_getdondathang="select pk_seq from dondathang where trangthai='1'";//lay nhung don dat hang da chot,va chua chuyen het hang sang kho khac
			ResultSet rs_dondathang=db.get(sql_getdondathang);
			session.setAttribute("rs_dondathang", rs_dondathang);
			String sql_getdvkd="select pk_seq,donvikinhdoanh as ten from donvikinhdoanh where trangthai='1'";//lay nhung don dat hang da chot,va chua chuyen het hang sang kho khac
			ResultSet rs_dvkd=db.get(sql_getdvkd);
			session.setAttribute("rs_dvkd", rs_dvkd);
			String nextjsp="/AHF/pages/Center/HoaDonKhuyenMaiNew.jsp";
			IHoaDon obj=new HoaDon();
			//mac dinh cho loai hoa don =0
			session.setAttribute("loaihoadon","0");
			session.setAttribute("obj", obj);
			response.sendRedirect(nextjsp); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
