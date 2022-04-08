package geso.dms.center.servlets.sitecode_conv;

import geso.dms.center.beans.nhaphanphoi.imp.NhaphanphoiList;
import geso.dms.center.beans.sitecode_conv.Isitecode_conv;
import geso.dms.center.beans.sitecode_conv.imp.sitcode_conv;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class sitecode_convSvl
 */
public class sitecode_convSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PrintWriter out;   
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public sitecode_convSvl() {
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
	    Isitecode_conv obj;
	    obj=new sitcode_conv();

	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String nppId = util.getId(querystring);
	    obj.setUserid(userId);
	    obj.Init("");
	    obj.settrangthai("");
	    obj.setMsg("");
	    obj.setten("");
		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Center/sitecode_conv.jsp";
		response.sendRedirect(nextJSP);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Utility util = new Utility();
		String userId=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
       HttpSession session=request.getSession();
       Isitecode_conv obj;
		obj = new sitcode_conv();;
    	 obj.setUserid(userId);
    	//int[]l = obj.getListPages();
    	String ten=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppTen")));
    	String trangthai=  	util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("TrangThai")));
    	obj.setten(ten);
    	obj.settrangthai(trangthai);
    	String sql="select conv.sitecode,conv.ten,isnull (conv.convsitecode,'') as convsitecode,conv.tennpptn as npptiennhiem,npp.pk_seq as idnpptn, "+ 
		" conv.trangthai,conv.ngaytao,conv.ngaysua,nt.ten as nguoitao,ns.ten as nguoisua,npp.ten as tenupdate from sitecode_conv conv " +
		" left join nhaphanphoi npp on npp.sitecode=conv.convsitecode"+ 
		" inner join nhanvien nt on nt.pk_seq= conv.nguoitao inner join nhanvien ns on conv.nguoisua=ns.pk_seq where 1=1 ";
	
        	String dk="";
      
    	if(ten!=""){
    		//dk=dk+" and upper(Ndbo.ftBoDau(conv.ten)) like upper(N'%"+ util.replaceAEIOU(ten)+"%') ";
    		dk=dk+" and upper(conv.ten) like upper(N'%"+ (ten)+"%') ";
    	}
    	if(trangthai!=""){
    		dk=dk+" and conv.trangthai = '"+trangthai+"' ";
    	}
    	sql=sql+ dk;
    	sql=sql+" order by trangthai";
    	
		obj.Init(sql);
		String nextJSP = "/AHF/pages/Center/sitecode_conv.jsp";
		 session.setAttribute("obj", obj);
		  response.sendRedirect(nextJSP);
	}

}
