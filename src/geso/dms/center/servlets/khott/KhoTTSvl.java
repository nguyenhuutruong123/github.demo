package geso.dms.center.servlets.khott;
import geso.dms.center.beans.khott.IKhoTT;
import geso.dms.center.beans.khott.imp.KhoTT;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class KhoTTSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
     
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KhoTTSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Utility util=new Utility();
	       //HttpServletRequest request;
		
		request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    
		    PrintWriter out = response.getWriter();
		    HttpSession session = request.getSession();

		    String querystring = request.getQueryString();
		    String userId = util.getUserId(querystring);
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		    
		    String action = util.getAction(querystring);	    
		    String khoId = util.getId(querystring);
            
		    if (action.equals("delete")){	   		
			  //delete;
		    	 KhoTT obj = new KhoTT();
		    	 obj.setId(khoId);
		    	 obj.Delete();
		    	 obj.setListkho("");
		    	 session.setAttribute("obj", obj);
		    	 session.setAttribute("userId", userId);
		    	 String nextJSP = "/AHF/pages/Center/KhoTT.jsp";
			     response.sendRedirect(nextJSP);
		    }
		    else if(action.equals("update")){
		    	 KhoTT obj = new KhoTT(khoId);
		    	 session.setAttribute("obj", obj);
		    	 session.setAttribute("userId", userId);
		    	 String nextJSP = "/AHF/pages/Center/KhoTTUpdate.jsp";
			    	response.sendRedirect(nextJSP);
		    }
		    else if(action.equals("display")){
		    	
		    }
		    else{
		    KhoTT obj = new KhoTT();	   
		    obj.setListkho("");
	    	//Data object is saved into session
	    	session.setAttribute("obj", obj);
			
	    	// userId is saved into session
	    	session.setAttribute("userId", userId);
			session.setAttribute("tungay", "");
			session.setAttribute("denngay", "");
	    	String nextJSP = "/AHF/pages/Center/KhoTT.jsp";
	    	response.sendRedirect(nextJSP);
		    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Utility util=new Utility();
	       //HttpServletRequest request;
		
		request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
			HttpSession session = request.getSession();
		    //this.request = request;
		    KhoTT obj = new KhoTT();
		    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		    // Perform searching. Each Business Unit is saved into Donvikinhdoanh
		    if (geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")).equals("search")){
		    	String search = getSearchQuery(obj, request);
//		    	out.println(search);
		    	obj.setListkho(search);
	    		// Saving data into session
	    		session.setAttribute("obj", obj);
	    		session.setAttribute("userId", userId);
	    		response.sendRedirect("/AHF/pages/Center/KhoTT.jsp");
		    }
		    // Create a new Business Unit
		    if (geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")).equals("new")){
		    	// Empty Bean for distributor
		    	IKhoTT khoBean = new KhoTT();
		    	// Save Data into session
	    		session.setAttribute("obj", khoBean);
	    		session.setAttribute("userId", userId);
		    	String nextJSP = "/AHF/pages/Center/KhoTTNew.jsp";
	    		response.sendRedirect(nextJSP);
		    }
	}
	private String getSearchQuery(KhoTT obj, ServletRequest request){
//	    PrintWriter out = response.getWriter();
		Utility util = new Utility();
		String ten = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ten")));
    	if (ten == null)
    		ten = "";
    	obj.setTen(ten);
    	
/*    	String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai"));
//    	out.println(nccId);
    	
    	if (diengiai == null)
    		diengiai = "";    	
    	obj.setDiengiai(diengiai);*/
    	
    	String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
    	if (tungay == null)
    		tungay = "";    	
    	
  
    	
    	String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
    	if (denngay == null)
    		denngay = "";    	

    
    	
    	String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));   	
    	if (trangthai == null)
    		trangthai = "";    	
	
    	if (trangthai.equals("2"))
    		trangthai = "";

    	obj.setTrangthai(trangthai);
    	
		String query="SELECT     K.PK_SEQ, K.TEN, K.NGAYTAO, K.NGAYSUA, K.TRANGTHAI, K.DIENGIAI, NT.TEN AS NGUOITAO, NS.TEN AS NGUOISUA "+
		   " FROM         dbo.KHOTT AS K INNER JOIN  dbo.NHANVIEN AS NT ON K.NGUOITAO = NT.PK_SEQ INNER JOIN "+
        " dbo.NHANVIEN AS NS ON K.NGUOISUA = NS.PK_SEQ WHERE     (K.TRANGTHAI <> '2') ";
    	
    	if (ten.length()>0){
			query = query + " and upper(k.ten) like upper(N'%" + util.replaceAEIOU(ten) + "%')";
    	}
    		
    	if (tungay.length() > 0) {
    		query = query + " and ngaytao >= '" + tungay + "'";
    	}
    	
    	if (denngay.length() > 0) {
    		query = query + " and ngaytao =< '" + denngay + "'";
    	}
    	
    	if(trangthai.length() > 0){
    		query = query + " and k.trangthai = '" + trangthai + "'";
    	}
    	//System.out.print(query);
		return query;
	}
}
