package geso.dms.center.servlets.chitieu;

import geso.dms.center.beans.chitieu.IChiTieu;
import geso.dms.center.beans.chitieu.imp.ChiTieu;
import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ChitieuPriSvl
 */
@WebServlet("/ChitieuPriSvl")
public class ChitieuPriSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChitieuPriSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    private String gettenuser(String userId_){
		dbutils db=new dbutils();
		String sql_getnam="select ten from nhanvien where pk_seq="+ userId_;
		ResultSet rs_tenuser=db.get(sql_getnam);
		String ten="";
		if(rs_tenuser!= null){
			try{
			  while(rs_tenuser.next()){
				ten= rs_tenuser.getString("ten");
			  }
			  rs_tenuser.close();
			}catch(Exception er){
				
			}
		
		}
		db.shutDown();
		return ten;
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Utility util = new Utility();

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		IChiTieu obj = new ChiTieu();
		
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);  
		obj.setUserId(userId);
		
		if (userId.length()==0)
		userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		
		
		
		session.setAttribute("userId", userId); 
		session.setAttribute("userTen", gettenuser(userId));
		String action = util.getAction(querystring);
		//out.println(action);   
		String idlist = util.getId(querystring); 
		try{
		obj.setID(Double.parseDouble(idlist));
		}catch(Exception er){};
		
		String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		if (view == null)
			view = "";
		obj.setView(view);
		
		if(action.equals("delete")){
			
			obj.DeleteChitieuSkuin();
		
		}else if(action.equals("chot")){
			
		  	obj.ChotChitieuSkuin();
		}
		else if(action.equals("unchot")){
			obj.UnChotChitieuSkuin();
		}
		
		obj.setRsPri("");
		session.setAttribute("obj", obj);
		String nextJSP = "/AHF/pages/Center/ChitieuPri.jsp";//default
		response.sendRedirect(nextJSP);
	}



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Utility util = new Utility();
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    String username=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userTen")));
	    
	    String thangtk = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang")));
	    String namtk = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nam")));
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	   
	    ChiTieu chitieu=new ChiTieu();
	    chitieu.setThang(thangtk);
	    chitieu.setNam(namtk);
	    chitieu.setUserId(userId);
	    HttpSession session=request.getSession();;
	    session.setAttribute("nam",Integer.parseInt(namtk));
	    session.setAttribute("thang",Integer.parseInt(thangtk));
	    //set dvkd truyen qua form
	  Utility Ult = new Utility(); 
	    if(action.equals("search"))
	    {
	    	String  sql="";
	    	
				  sql=" SELECT  KBH.TEN AS KENH,c.trangthai,C.PK_SEQ, C.THANG, C.NAM, C.CHITIEU,C.DIENGIAI, C.NGAYKETTHUC,C.DVKD_FK,DONVIKINHDOANH,C.SONGAYLAMVIEC, C.NGAYTAO, C.NGAYSUA,NT.TEN AS NGUOITAO, "+ 
		            " NS.TEN AS NGUOISUA " +
		            "  FROM dbo.CHITIEU AS C inner join donvikinhdoanh as d on d.pk_seq=c.DVKD_FK " +
		            " INNER JOIN  dbo.NHANVIEN AS NT ON C.NGUOITAO = NT.PK_SEQ " +
		            " INNER JOIN dbo.NHANVIEN AS NS ON C.NGUOISUA = NS.PK_SEQ " +
		            " INNER JOIN KENHBANHANG AS KBH ON C.KENH_FK= KBH.PK_SEQ " +
		            " WHERE 1=1 ";
				  
		          /*  " where c.trangthai!='2' "+
		            " and  c.pk_seq in (select chitieu_fk from CHITIEU_NHAPP where nhapp_fk in " + Ult.quyen_npp(userId) +")"+
					  " and kenh_fk in "+ Ult.quyen_kenh(userId);*/
	    	String where ="";
	    	if(!(thangtk.equals("0")) ){
	    		where=  " and C.THANG="+ thangtk + " ";	
	    	}
	    	if(!namtk.equals("0")){
	    		where=where + "and C.NAM="+namtk +" ";
	    	}
	    	   if(where !=""){//if have search condition
	    	       sql= sql+ where; 
	    	      }
	    	    	
	    	
	    	System.out.println("CAU LENH TIM KIEM :" + sql);
	    	chitieu.setView("TT");
	    	chitieu.setRsPri(sql);
	    	session.setAttribute("obj", chitieu);
	    	String nextJSP = "/AHF/pages/Center/ChitieuPri.jsp";//default
	    	response.sendRedirect(nextJSP);
	    }
	    else if(action.equals("new"))
	    {

	    	IChiTieu obj = new ChiTieu();
	    	
	    	obj.setUserId(userId);
	    
	    	obj.CreateRs();
	     	
	        session.setAttribute("userId",userId);
	        session.setAttribute("userTen", username);
	    	session.setAttribute("obj", obj);
	    	String	nextJSP= "/AHF/pages/Center/ChitieuPriNew.jsp";
	    	response.sendRedirect(nextJSP);
	    	
	    }
	}

}
