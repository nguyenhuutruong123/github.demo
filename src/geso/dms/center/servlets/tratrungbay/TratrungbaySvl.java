package geso.dms.center.servlets.tratrungbay;

import geso.dms.center.beans.daidienkinhdoanh.IDaidienkinhdoanhList;
import geso.dms.center.beans.tratrungbay.*;
import geso.dms.center.beans.tratrungbay.imp.*;
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


public class TratrungbaySvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out; 
       
    public TratrungbaySvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		ITratrungbayList obj;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
		String param = "";
//		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "TratrungbaySvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
	    
	    String tratbId = util.getId(querystring);

//	    if (action.equals("delete")){	
//	    	Delete(tratbId);
//	    	out.print(tratbId);
//	    }
	
	    obj = new TratrungbayList();
	    obj.setUserId(userId);
	    obj.init("");
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Center/TraTrungBay.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		ITratrungbayList obj = new TratrungbayList();
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out = response.getWriter();
	    
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));
	    if (action == null){
	    	action = "";
	    }
	    out.println(action); 
	    
//	    if(view.trim().length() > 0) param ="&view="+view;
	    String param = "";
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			obj.DBclose();
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "TratrungbaySvl", param, Utility.XEM ))
		{
			obj.DBclose();
			Utility.RedireactLogin(session, request, response);
		}
	        
	    
	    if (action.equals("Tao moi"))
	    {
	    	ITratrungbay tratbBean = (ITratrungbay) new Tratrungbay("");
	    	tratbBean.setUserId(userId);
	    	tratbBean.createRS();
	    	session.setAttribute("tratbBean", tratbBean);
    		
    		String nextJSP = "/AHF/pages/Center/TraTrungBayNew.jsp";
    		response.sendRedirect(nextJSP);
    		return;
    		
	    }
	    
	    
	    if(action.equals("Xoa"))
		{
		
			String IdXoa = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("IdXoa")));
			if (IdXoa == null)
				IdXoa = "";
			System.out.println("IdXoa = "+ IdXoa);
			if(IdXoa.trim().length() > 0)
				Delete(IdXoa, obj);
			else
				obj.setMsg("Lỗi dữ liệu");
			obj.setUserId(userId);
		    obj.init("");
			session.setAttribute("obj", obj);
					
			String nextJSP = "/AHF/pages/Center/TraTrungBay.jsp";
			response.sendRedirect(nextJSP);
		}
	    else{	    
	    	String search = getSearchQuery(request,userId , obj);
	    	
	    	//obj = new DaidienkinhdoanhList(search);
	    	obj.init(search);
			obj.setUserId(userId);
			
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/AHF/pages/Center/TraTrungBay.jsp");    	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request,String userId, ITratrungbayList obj)
	{	
		
		//ITratrungbayList obj= new TratrungbayList();
		obj.getDataSearch().clear();
		Utility util = new Utility();
		
		String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
    	if ( diengiai == null)
    		diengiai = "";
    	obj.setDiengiai(diengiai);
    	
    	String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
    	System.out.println("tungay :"+ tungay);
    	if (tungay == null)
    		tungay = "";   
    	obj.setTungay(tungay);
    	
    	String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
    	System.out.println("denngay :"+ denngay);
    	if (denngay == null)
    		denngay = "";    	
    	obj.setDenngay(denngay);
    	Utility Ult = new Utility();
    	String query = "select a.PK_SEQ as trakmId, a.diengiai, isnull(a.TONGLUONG, 0) as tongluong, isnull(a.TONGTIEN, 0) as tongtien, a.loai, a.hinhthuc, a.ngaytao, a.ngaysua, b.TEN as nguoitao, c.TEN as nguoisua   ";
		query = query + "  from tratrungbay a inner join NHANVIEN b on a.NGUOITAO = b.PK_SEQ inner join NHANVIEN c on a.NGUOISUA = c.PK_SEQ where 1=1 "  ;
    	if (diengiai.length()>0){
			query = query + " and upper(dbo.ftBoDau(a.diengiai)) like ?";	
			obj.getDataSearch().add( "%" + util.replaceAEIOU(diengiai) + "%" );
    	}
    	
    	if (tungay.length()>0){
			query = query + " and a.ngaytao >= ?";	
			obj.getDataSearch().add(convertDate(tungay));

    	}
    	
    	if (denngay.length()>0){
			query = query + " and a.ngaytao <= ?";	
			obj.getDataSearch().add(convertDate(denngay));
    	}
    	
    	query = query + " order by a.NGAYTAO DESC, a.pk_seq DESC";
    	
    	dbutils.viewQuery(query, obj.getDataSearch());
    	return query;
	}
	
	private String convertDate(String date) 
	{
		//chuyen dinh dang dd-MM-yyyy sang dinh dang yyyy-MM-dd
		if(!date.contains("-") && !date.contains("/"))
			return "";
		
		if(date.contains("-"))
		{
		String[] arr = date.split("-");
		if(arr[0].length() < arr[2].length())
			return arr[2] + "-" + arr[1] + "-" + arr[0];
		}
		else if(date.contains("/"))
		{
		String[] arr = date.split("/");
		if(arr[0].length() < arr[2].length())
			return arr[2] + "-" + arr[1] + "-" + arr[0];
		}
		return date;
	}
	
	private void Delete(String tratbId, ITratrungbayList obj) 
	{
		
//		ITratrungbayList obj = new TratrungbayList();
		
		dbutils db = new dbutils();
    	try{
    		db.getConnection().setAutoCommit(false);
    		String sqlcheck = "select count(tratrungbay_fk) as dem from CTTB_TRATB where tratrungbay_fk = "+tratbId+" ";
    		System.out.println("check phat sinh du lieu: " + sqlcheck);
			ResultSet rscheck = db.get(sqlcheck);
			int dem =0;
			if(rscheck.next()){
				dem = rscheck.getInt("dem");
			}
			if(dem >0){
				obj.setMsg("Nhóm sản phẩm trả trưng bày đã phát sinh dữ liệu, không thể xóa, vui lòng kiểm tra lại!");
				db.getConnection().rollback();
				return;
			}
    	
		//Xoa Cac Bang Con Truoc
    	String str1 = "delete from tratrungbay_sanpham where TRATRUNGBAY_FK='" + tratbId + "'";
		if(!db.update(str1))
		{
			db.getConnection().rollback();
			return;
		}
		String str2 = "delete from cttb_tratb where tratrungbay_fk='" + tratbId + "'";
		if(!db.update(str2))
		{
			db.getConnection().rollback();
			return;
		}
		
		//Xoa Bang Cha
		String str3 = "delete from tratrungbay where pk_seq = '" + tratbId + "'";
		if(!db.update(str3))
		{
			db.getConnection().rollback();
			return;
		}
		//db.update("commit");
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		obj.setMsg("Xóa thành công!");
		db.shutDown();
    	}catch(Exception err){
    		err.printStackTrace();
    		try {
				db.getConnection().rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    		obj.setMsg("khong the xoa tra trung bay nay!");
    		return;
    	}
	}

}
