package geso.dms.center.servlets.trakhuyenmai;

import geso.dms.center.beans.ctkhuyenmai.imp.CtkhuyenmaiList;
import geso.dms.center.beans.trakhuyenmai.*;
import geso.dms.center.beans.trakhuyenmai.imp.*;
import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class TrakhuyenmaiSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	ITrakhuyenmaiList obj;
	PrintWriter out;    
    
    public TrakhuyenmaiSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    out = response.getWriter();
	    
	    //--- check user
	    String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		if (view == null) {
			view = "";
		}
		String param = "";
		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "TrakhuyenmaiSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
		 //--- check user 
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String trakmId = util.getId(querystring);

	  
	
	    obj = new TrakhuyenmaiList();
	    obj.setRequestObj(request);
	    
	    if (action.equals("delete"))
	    {	
	    	String msg = Delete(trakmId);
	    	if(msg.length() > 0)
	    	{
	    		obj.setmsg(msg);
	    	}		
	    }
	    obj.setUserId(userId);
	    obj.init("");
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Center/TraKhuyenMai.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out = response.getWriter();
	    
		HttpSession session = request.getSession();
	    String userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	    
	    //--- check user
	    Utility util = new Utility();
	    String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		if (view == null) {
			view = "";
		}
		String param = "";
		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "TrakhuyenmaiSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
		 //--- check user 
	    
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    out.println(action); 
	        
	    obj.setRequestObj(request);
	    if (action.equals("Tao moi"))
	    {
	    	ITrakhuyenmai trakmBean = (ITrakhuyenmai) new Trakhuyenmai("");
	    	trakmBean.setUserId(userId);
	    	trakmBean.createRS();
	    	session.setAttribute("trakmBean", trakmBean);
    		
    		String nextJSP = "/AHF/pages/Center/TraKhuyenMaiNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    else
	    	if(action.equals("view") || action.equals("next") || action.equals("prev")){
		    	
		    	obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
		    	obj.init("");
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	
		    	obj.setUserId(userId);
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		    	
		    	response.sendRedirect("/AHF/pages/Center/TraKhuyenMai.jsp");  
	    	}
	    		
	    else{	
	    	
	    	
	    	
	    	obj = new TrakhuyenmaiList();
	    		    	
	    	String search = getSearchQuery(request);
	    	
	    	//obj = new DaidienkinhdoanhList(search);
	    	obj.init(search);
	    	
			obj.setUserId(userId);
			
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/AHF/pages/Center/TraKhuyenMai.jsp");    	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request)
	{	
		String diengiai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai"));
    	if ( diengiai == null)
    		diengiai = "";
    	obj.setDiengiai(diengiai);
    	
    	String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
    	if (tungay == null)
    		tungay = "";    	
    	obj.setTungay(tungay);
    	
    	String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
    	if (denngay == null)
    		denngay = "";    	
    	obj.setDenngay(denngay);
    	
    	String query = "select ROW_NUMBER() OVER(ORDER BY a.pk_seq DESC) AS stt, a.PK_SEQ as trakmId, a.diengiai, isnull(a.TONGLUONG, 0) as tongluong, isnull(a.TONGTIEN, 0) as tongtien, isnull(a.CHIETKHAU, 0) as chietkhau, a.loai, a.hinhthuc, a.ngaytao, a.ngaysua, b.TEN as nguoitao, c.TEN as nguoisua  ";
		query = query + " from TRAKHUYENMAI a inner join NHANVIEN b on a.NGUOITAO = b.PK_SEQ inner join NHANVIEN c on a.NGUOISUA = c.PK_SEQ  where a.pk_seq > 0";
		
		
		geso.dms.distributor.util.Utility util = new geso.dms.distributor.util.Utility();
		obj.getDataSearch().clear();

    	if (diengiai.length()>0){
//			query = query + " and upper(dbo.ftBoDau(a.diengiai)) like upper('%" + util.replaceAEIOU(diengiai) + "%') or upper(a.pk_seq) like upper('%" + diengiai + "%')";			
    	
			query = query + "\n and upper(dbo.ftBoDau(a.diengiai)) like upper(?) or upper(a.pk_seq) like upper(?)";	
			obj.getDataSearch().add( "%" + util.replaceAEIOU(diengiai) + "%" );
			obj.getDataSearch().add( "%" + diengiai + "%" );
    	}
    	
    	if (tungay.length()>0){
//			query = query + " and a.ngaytao >= '" + convertDate(tungay) + " '";	
			
			query = query + "\n and a.ngaytao >= ? ";	
			obj.getDataSearch().add( "" + convertDate(tungay) + "" );
    	}
    	
    	if (denngay.length()>0){
//			query = query + " and a.ngaytao <= '" + convertDate(denngay) + " '";	
			
			query = query + "\n and a.ngaytao <= ? ";	
			obj.getDataSearch().add( "" + convertDate(tungay) + "" );
    	}
    	
    	//query = query + " order by a.NGAYTAO DESC, a.pk_seq DESC";
    	
    	
    	return query;
	}
	
	private String convertDate(String date) 
	{
		//chuyen dinh dang dd-MM-yyyy sang dinh dang yyyy-MM-dd
		if(!date.contains("-"))
			return "";
		String[] arr = date.split("-");
		if(arr[0].length() < arr[2].length())
			return arr[2] + "-" + arr[1] + "-" + arr[0];
		return date;
	}
	
	private String Delete(String trakmId) 
	{
		dbutils db = new dbutils();
		ResultSet rs = db.get("select count(*) as num from ctkm_trakm where trakhuyenmai_fk='" + trakmId + "'");
		try 
		{
			rs.next();
			if(!rs.getString("num").equals("0"))
			{
				rs.close();
				return "Tra khuyen mai nay da duoc su dung trong khai bao ct khuyen mai";
			}
			rs.close();
			
			ResultSet rs1 = db.get("select count(*) as num from donhang_ctkm_trakm where trakmId='" + trakmId + "'");
			if(rs1 != null)
			{
				rs1.next();
				if(!rs1.getString("num").equals("0"))
				{
					rs1.close();
					return "Tra khuyen mai nay da duoc su dung trong don hang ap khuyen mai";
				}
			}
			
			db.update("delete from trakhuyenmai_sanpham where trakhuyenmai_fk='" + trakmId + "'");
			db.update("delete from ctkm_trakm where trakhuyenmai_fk='" + trakmId + "'");
			db.update("delete from donhang_ctkm_trakm where trakhuyenmai_fk='" + trakmId + "'");
			//Xoa Bang Cha
			db.update("delete from trakhuyenmai where pk_seq = '" + trakmId + "'");
			db.update("commit");
			return "";
			
		} 
		catch (SQLException e) 
		{
			return "Khong the xoa tra khuyen mai nay";
		}
		
	}


}
