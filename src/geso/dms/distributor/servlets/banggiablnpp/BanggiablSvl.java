package geso.dms.distributor.servlets.banggiablnpp;

import geso.dms.distributor.beans.banggiablnpp.*;
import geso.dms.distributor.beans.banggiablnpp.imp.*;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BanggiablSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	String userId;   
   
    public BanggiablSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		/*HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
			IBanggiablnppList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	   
	    	    
	       

	    Utility util = new Utility();
	   
	    	    
	    String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    System.out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	    System.out.println("action = " +  action);
	    
	    String bgblId = util.getId(querystring);
	    obj = new BanggiablnppList();
	    obj.setUserId(userId);
	    
	    if (action.equals("delete")){	   		  	    	
	    	Delete(bgblId);
	    	 System.out.println(bgblId);
	    }
	    if (action.equals("chot")){	   		  	    	
	    	String msg = Chot(bgblId, userId);
	    	 System.out.println("chot: = "+ msg);
	    	obj.setMsg(msg);
	    }
	   	    
	    
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Distributor/BangGiaBanLeNpp.jsp";
		response.sendRedirect(nextJSP);
		}*/
		Utility util = new Utility();

	    String querystring = request.getQueryString();
	    String action = util.getAction(querystring);
    	if (action == null)
    		action = "";
    	
    

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		IBanggiablnppList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    if (view == null) {
			view = "";
		}	
		String param = "";

		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "BanggiablSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}

		    String bgblId = util.getId(querystring);
		    if (action.equals("delete")){	
		    	Delete(bgblId);
		    	 System.out.println(bgblId);
				
		    }

		    if (action.equals("chot")){	  
		    	
		    	String msg = Chot(bgblId, userId);
		    	 System.out.println("chot: = "+ msg);
				
		    }
		   	    
	    obj = new BanggiablnppList();

	    obj.setUserId(userId);
	    
	 
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Distributor/BangGiaBanLeNpp.jsp";
		response.sendRedirect(nextJSP);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
   	 System.out.println("hello");

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
	
			IBanggiablnppList obj  = new BanggiablnppList();
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	 
	    Utility util = new Utility();
		
		String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		if (view == null)
			view = "";
		obj.setView(view);
	    userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    String param = "";
		if(view.trim().length() > 0) param ="&view="+view;

		if ( Utility.CheckSessionUser( session,  request, response))
		{

			Utility.RedireactLogin(session, request, response);

		}
		if( Utility.CheckRuleUser( session,  request, response, "BanggiablSvl", param, Utility.XEM ))
		{

			Utility.RedireactLogin(session, request, response);

		}else{

	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    String querystring = request.getQueryString();

	    String bgblId = util.getId(querystring);
	    if (action.equals("delete")){	
	    	Delete(bgblId);
	    	 System.out.println(bgblId);
			
	    }
	    if (action.equals("chot")){	  
	    	
	    	String msg = Chot(bgblId, userId);
	    	 System.out.println("chot: = "+ msg);
			
	    }
	   	    
	    
	    if (action.equals("new"))
	    {
	    	// Empty Bean for distributor
	    	IBanggiablnpp bgblBean = (IBanggiablnpp) new Banggiablnpp("");
	    	bgblBean.setUserId(userId);
	    	bgblBean.createRS();
	    	// Save Data into session
	    	session.setAttribute("bgblBean", bgblBean);
    		
    		String nextJSP = "/AHF/pages/Distributor/BgBanLeNppNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    if (action.equals("search")){
	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/AHF/pages/Distributor/BangGiaBanLeNpp.jsp");	    		    	
	    }
		}}
	
	
	private String getSearchQuery(HttpServletRequest request, IBanggiablnppList obj)
	{
		Utility util = new Utility();
		String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
		if ( nppId == null)
			nppId = "";
		obj.setNppId(nppId);

		String bgblTen = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("bgblTen")));
		if ( bgblTen == null)
			bgblTen = "";
		obj.setTenbanggia(bgblTen);

		String nccId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nccTen")));
		if ( nccId == null)
			nccId = "";
		obj.setNccId(nccId);

		String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdTen")));
		if (dvkdId == null)
			dvkdId = "";    	
		obj.setDvkdId(dvkdId);

		String	query = "\n SELECT  bgbl.trangthai,bgbl.PK_SEQ, bgbl.TEN AS BGBLTEN,  "+
		"\n bgbl.NGAYTAO, bgbl.NGAYSUA, nv.TEN AS NGUOITAO, nv2.TEN AS NGUOISUA ,"+
		"\n npp.PK_SEQ AS NPPID, npp.TEN AS NPPTEN, dvkd.DONVIKINHDOANH AS DVKDTENVIETTAT,  "+
		"\n dvkd.DIENGIAI AS DVKDTEN,  dvkd.PK_SEQ AS DVKDID " +
		"\n FROM dbo.BANGGIABANLENPP AS bgbl  "+
		"\n INNER JOIN  dbo.NHANVIEN AS nv ON bgbl.NGUOITAO = nv.PK_SEQ " +
		"\n INNER JOIN  dbo.NHANVIEN AS nv2 ON bgbl.NGUOISUA = nv2.PK_SEQ " +
		"\n LEFT OUTER JOIN  dbo.NHAPHANPHOI AS npp ON bgbl.NPP_FK = npp.PK_SEQ " +
		"\n INNER JOIN  dbo.DONVIKINHDOANH AS dvkd ON bgbl.DVKD_FK = dvkd.PK_SEQ  	" +
		"\n where dvkd.PK_SEQ in (select b.dvkd_fk from nhapp_nhacc_donvikd a inner join  "+
		"\n nhacungcap_dvkd b on a.ncc_dvkd_fk = b.pk_seq inner join donvikinhdoanh c on b.dvkd_fk = c.pk_seq inner  "+
		"\n join nhacungcap ncc on ncc.pk_Seq=b.ncc_fk where a.npp_fk='"+nppId+"') and   npp.pk_Seq='"+nppId+"'";

		/*if (bgblTen.length()>0)
		{
			query = query + "\n and upper(dbo.ftBoDau(bgbl.TEN )) like upper(N'%" + util.replaceAEIOU(bgblTen) + "%')";			
		}

		if (nccId.length()>0){
			query = query + "\n and npp.PK_SEQ ='" + nccId + "'";			
		}

		if (dvkdId.length()>0){
			query = query + "\n and dvkd.PK_SEQ ='" + dvkdId + "'";			
		}

		query = query + "\n order by bgbl.pk_seq";
		return query;*/
		if (bgblTen.length()>0)
		{
			query = query + "\n and upper(dbo.ftBoDau(bgbl.TEN )) like upper (N'"+ util.replaceAEIOU(bgblTen) + "')";
			/*obj.getDataSearch().add("'" + util.replaceAEIOU(bgblTen) + "'");*/

		}

		if (nccId.length()>0){
			query = query + "\n and npp.PK_SEQ = ?";	
			obj.getDataSearch().add(nccId );

		}

		if (dvkdId.length()>0){
			query = query + "\n and dvkd.PK_SEQ = ?";			
			obj.getDataSearch().add(dvkdId );

		}

		//query = query + "\n order by bgbl.pk_seq";
		return query;
	}
	

	private void Delete(String bgblId) 
	{
		dbutils db = new dbutils();
		db.update("delete from bgbanlenpp_sanpham where bgbanlenpp_fk='" + bgblId + "'");
		//Xoa Bang Cha
		db.update("delete from banggiabanlenpp where pk_seq = '" + bgblId + "'");
		db.shutDown();
	}
	
	private String Chot(String id,String userId)
	{
		dbutils db = new dbutils();
		try
		{
				db.getConnection().setAutoCommit(false);
				
				String query = " update banggiabanlenpp set trangthai  =1, nguoisua = "+userId+" where trangthai = 0 and pk_seq = "+ id;
				if (db.updateReturnInt(query) <=0)
				{
					db.getConnection().rollback();			
					db.shutDown();
					return "Error : "+query;		
				}
				db.getConnection().commit();			
				db.shutDown();
				return "Success!";	
				
		}catch (Exception e) {
			e.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			db.shutDown();
			return "Exception:" + e.getMessage();
		}
		
	}

}
