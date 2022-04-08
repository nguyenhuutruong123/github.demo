package geso.dms.distributor.servlets.tuyenbanhang;

import geso.dms.distributor.beans.tuyenbanhang.*;
import geso.dms.distributor.beans.tuyenbanhang.imp.*;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TuyenbanhangMoveSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
   
    public TuyenbanhangMoveSvl() {
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
		
		ITuyenbanhang tbhBean;
		PrintWriter out; 
		
		out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    
	    String id = util.getId(querystring);  	
	    tbhBean = new Tuyenbanhang(id);
	    tbhBean.setUserId(userId);
	    tbhBean.init();
	    tbhBean.createNlvList();
	    tbhBean.createDiengiaiList();
	    
        session.setAttribute("tbhBean", tbhBean);
        String nextJSP = "/AHF/pages/Distributor/TuyenBanHangMove.jsp";
        response.sendRedirect(nextJSP);
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
		
		ITuyenbanhang tbhBean;
		PrintWriter out;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");

		
		out = response.getWriter();
		
	    String id = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id"));
	    tbhBean = new Tuyenbanhang(id);
	    tbhBean.setId(id);
	    
	    
	    
		userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
		tbhBean.setUserId(userId);
	    
		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
		if (nppId == null)
			nppId = "";
		tbhBean.setNppId(nppId);
		
		String ddkdTen = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdTen"));
		if (ddkdTen == null)
			ddkdTen = "";				
    	tbhBean.setDdkd(ddkdTen);
    	
    	String diengiai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai"));
    	if (diengiai == null)
    		diengiai = "";
    	tbhBean.setDiengiai(diengiai);
    	
    	String nlv = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaylamviec"));
		if (nlv == null)
			nlv = "";
		tbhBean.setNgaylamviec(nlv);
		
    	String ddkdNewId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdNewTen"));
		if (ddkdNewId == null)
			ddkdNewId = "";				
    	tbhBean.setDdkdNewId(ddkdNewId);
    	
    	String diengiaiNew = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tbhNewTen"));
    	if (diengiaiNew == null)
    		diengiaiNew = "";
    	tbhBean.setDiengiaiNew(diengiaiNew);
    	
    	String nlvNew = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nlvNewTen"));
		if (nlvNew == null)
			nlvNew = "";
		tbhBean.setNlvNew(nlvNew);
		
		boolean error = false;
		
		if (ddkdNewId.trim().length()== 0){
			tbhBean.setMessage("Vui lòng chọn đại diện kinh doanh.");
			error = true;
		}
		
		if (diengiaiNew.trim().length()== 0){
			tbhBean.setMessage("Vui lòng nhập diễn giải.");
			error = true;
		}
		
		if (nlvNew.trim().length()== 0){
			tbhBean.setMessage("Vui lòng chọn ngày làm việc.");
			error = true;		
		}
		
 		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
 		
	    String idNew = "";
	    dbutils db = new dbutils();
	    //String sql ="select pk_seq from tuyenbanhang where diengiai='" + diengiaiNew + "' and ngaylamviec='" + nlvNew + "' and ddkd_fk='" + ddkdNewId + "'";
	    String sql ="select pk_seq from tuyenbanhang where ngaylamviec='" + nlvNew + "' and ddkd_fk='" + ddkdNewId + "'";
	    ResultSet rs = db.get(sql);
	    System.out.println("chuyen tuyen:"+ sql);
	    try {
			//while(rs.next())
	    	  rs.next();
				idNew = rs.getString("pk_seq");
		} 
	    catch(Exception e) {}
	      
		if(action.equals("save"))
		{
			String[] khIds = request.getParameterValues("khIds");
			String[] tansoIds = request.getParameterValues("tansoList");	
			String[] sott = request.getParameterValues("thutu");
			if (!(tbhBean.MoveTbh(id, idNew, khIds, tansoIds,sott)))
			{
				tbhBean.setUserId(userId);
				tbhBean.init();
				//tbhBean.createNlvList();
		    	//tbhBean.createDiengiaiList();
				session.setAttribute("tbhBean", tbhBean);
				String nextJSP = "/AHF/pages/Distributor/TuyenBanHangMove.jsp";
				response.sendRedirect(nextJSP);
			}
			else{
				ITuyenbanhangList obj = new TuyenbanhangList();
				obj.setUserId(userId);
				obj.init("");
				session.setAttribute("obj", obj);
					
				String nextJSP = "/AHF/pages/Distributor/TuyenBanHang.jsp";
				response.sendRedirect(nextJSP);			    			    													
			}
		}
		else{
		
			tbhBean.setUserId(userId);
			tbhBean.createRS();
			tbhBean.createNlvList();
			tbhBean.createDiengiaiList();
			
			session.setAttribute("tbhBean", tbhBean);
			
			String nextJSP = "/AHF/pages/Distributor/TuyenBanHangMove.jsp";   						
			response.sendRedirect(nextJSP);		
		}
	}
	}

}
