package geso.dms.center.servlets.thongtinhoadon;


import geso.dms.center.beans.thongtinhoadon.IThongtinhoadon;
import geso.dms.center.beans.thongtinhoadon.imp.Thongtinhoadon;
import geso.dms.center.db.sql.db_Sync;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ThongtinhoadonDisplaySvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ThongtinhoadonDisplaySvl() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
//		PrintWriter out = response.getWriter();
		String nextJSP;
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	        
	    if (userId.length()==0)
	    	userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    
	    String id = util.getId(querystring);  	
	    String action = util.getAction(querystring);

		IThongtinhoadon dctkBean = (IThongtinhoadon) new Thongtinhoadon();
		dctkBean.setUserId(userId);
		/*String sql="select ten from nhanvien where pk_seq="+userId;
	    
	    db_Sync db=new db_Sync();
	    ResultSet rs=db.get(sql);
	    if(rs!=null){
	    	try {
				if(rs.next()){
					dctkBean.setUserTen(rs.getString("ten"));
				}
				rs.close();
			} catch(Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    }*/
		dctkBean.setId(id);
    	dctkBean.initDisplay();
    	session.setAttribute("dctkBean", dctkBean);
    	nextJSP = "/AHF/pages/Center/ThongTinHoaDonDisplay.jsp";
    	response.sendRedirect(nextJSP);    		

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
