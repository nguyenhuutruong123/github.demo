package geso.dms.distributor.servlets.gioihancongno;

import geso.dms.distributor.beans.gioihancongno.IGioihancongno;
import geso.dms.distributor.beans.gioihancongno.IGioihancongnoList;
import geso.dms.distributor.beans.gioihancongno.imp.Gioihancongno;
import geso.dms.distributor.beans.gioihancongno.imp.GioihancongnoList;
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


public class GioihancongnoSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	
    public GioihancongnoSvl()
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
		IGioihancongnoList obj;
		PrintWriter out; 
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out  = response.getWriter();
   
	    obj = new GioihancongnoList();
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String ghcnId = util.getId(querystring);

	    if (action.equals("delete")){	   		  	    	
	    	Delete(ghcnId, obj);
	    	out.print(ghcnId);
	    }
	   	    
	   
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Distributor/GioiHanCongNo.jsp";
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
		
		IGioihancongnoList obj;
		PrintWriter out; 
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out = response.getWriter();
	    Utility util = new Utility();
	    userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    out.println(action); 
	          
	    if (action.equals("new"))
	    {
	    	// Empty Bean for distributor
	    	IGioihancongno ghcnBean = (IGioihancongno) new Gioihancongno("");
	    	ghcnBean.setUserId(userId);
	    	ghcnBean.createRS();
	    	
	    	// Save Data into session
	    	session.setAttribute("ghcnBean", ghcnBean);
    		
    		String nextJSP = "/AHF/pages/Distributor/GioiHanCongNoNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    
	    if (action.equals("search"))
	    {
	    	obj = new GioihancongnoList();
	    	String search = this.getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/AHF/pages/Distributor/GioiHanCongNo.jsp");	    		    	
	    }
		}
	}
	
	private String getSearchQuery(HttpServletRequest request, IGioihancongnoList obj) 
	{
		Utility util = new Utility();
		HttpSession session = request.getSession();		
		String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
    	if ( nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
		String songayno = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("songayno")));
    	if ( songayno == null)
    		songayno = "";
    	obj.setSongayno(songayno);
    	
    	String sotienno = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sotienno")));
    	if ( sotienno == null)
    		sotienno = "";
    	obj.setSotienno(sotienno);
    	
   	
    	String khTen = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khTen")));
    	if ( khTen == null)
    		khTen = "";
    	obj.setKhachhang(khTen);
    	    	
    	String query = "select * from vwGioihancongno where nppId='" + nppId + "'";
    		
    	if (songayno.length() > 0)
    	{
			query = query + " and songayno='" + songayno + "'";			
    	}
    	
    	if (sotienno.length() > 0)
    	{
			query = query + " and sotienno='" + sotienno + "'";			
    	}
    	
    	if (khTen.length() > 0)
    	{
    		query = query + " and ghcnId in (select ghcn_fk from khachhang where  upper(ten) like  upper('%" + khTen + "%'))";
    	}
    	  	
    	query = query + " order by songayno";
    	return query;
	}

	private void Delete(String ghcnId, IGioihancongnoList obj) 
	{
		dbutils db = new dbutils(); 
		String query = "Select count(pk_seq) as num from KhachHang where ghcn_fk='" + ghcnId + "'";
		
		ResultSet kh = db.get(query);
		try {
			kh.next();
			if(kh.getString("num").equals("0"))
			{
				db.update("delete from gioihancongno where pk_seq='" + ghcnId + "'");
			}
			else
				obj.setMsg("Muc tin dung nay da duoc giao cho khach hang roi");
		} catch(Exception e) {
			
		}
		
		/*if(kh != null)
		{
			try {
				while(kh.next())
				{
					String khId = kh.getString("pk_seq");
					String sql = "update Khachhang set ghcn_fk = '0' where pk_seq = '" + khId + "'";
					db.update(sql);
				}
			} catch(Exception e) {}
		}
		db.update("delete from gioihancongno where pk_seq='" + ghcnId + "'");
		*/
		db.shutDown();
	}

}
