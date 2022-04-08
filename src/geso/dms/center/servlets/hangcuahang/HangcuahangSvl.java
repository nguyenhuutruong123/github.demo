package geso.dms.center.servlets.hangcuahang;

import geso.dms.center.beans.hangcuahang.IHangcuahangList;
import geso.dms.center.beans.hangcuahang.IHangcuahang;
import geso.dms.center.beans.hangcuahang.imp.Hangcuahang;
import geso.dms.center.beans.hangcuahang.imp.HangcuahangList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.ResultSet;

 public class HangcuahangSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
	
	
    public HangcuahangSvl()
    {
        super();
    }
    dbutils db = new dbutils();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IHangcuahangList obj;
		PrintWriter out;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
	    obj = new HangcuahangList();
	    Utility util = new Utility();
	    out = response.getWriter();
	    
	    String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
	    if (view == null) 
	    	view = "";

	    String param = "";
	    if(view.trim().length() > 0) param ="&view="+view;
	    if ( Utility.CheckSessionUser( session,  request, response)) {
	    	Utility.RedireactLogin(session, request, response);
	    }
	    if( Utility.CheckRuleUser( session,  request, response, "HangcuahangSvl", param, Utility.XEM )) {
	    	Utility.RedireactLogin(session, request, response);
	    }
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String hchId = util.getId(querystring);

	    if (action.equals("delete")){	   		  	    	
	    	if(view.trim().length() > 0) param = "&view=" + view;
	    	int[] quyen = Utility.Getquyen("HangcuahangSvl", param, userId);
	    	if(quyen[Utility.XOA] != 1) {
	    		return;
	    	}
	    	
	    	obj.setMsg(Delete(hchId));
	    	out.print(hchId);
	    }
	   	
	  
	    obj.setUserId(userId);
	    obj.init("");
		session.setAttribute("obj", obj);
				
		String nextJSP = "/AHF/pages/Center/HangCuaHang.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		IHangcuahangList obj;
		PrintWriter out;
	  	
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out = response.getWriter();
	    obj = new HangcuahangList();
	    
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    //String msg = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("msg"));
	    
	    String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
	    if (view == null) 
	    	view = "";
	    obj.setView(view);

	    String param = "";
	    if(view.trim().length() > 0) param ="&view="+view;
	    if ( Utility.CheckSessionUser( session,  request, response)) {
	    	obj.closeDB();
	    	Utility.RedireactLogin(session, request, response);
	    }
	    if( Utility.CheckRuleUser( session,  request, response, "HangcuahangSvl", param, Utility.XEM )) {
	    	obj.closeDB();
	    	Utility.RedireactLogin(session, request, response);
	    }
	    
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    out.println(action); 
	        
	    
	    if (action.equals("new")){
	    	// Empty Bean for distributor
	    	IHangcuahang hchBean = (IHangcuahang) new Hangcuahang("");
	    	hchBean.setUserId(userId);
	    	// Save Data into session
	    	session.setAttribute("hchBean", hchBean);
    		
    		String nextJSP = "/AHF/pages/Center/HangCuaHangNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    else if(action.equals("search")){
	    	
	    	String search = getSearchQuery(request,obj);	    		
	    	obj.init(search);
			obj.setUserId(userId);
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/AHF/pages/Center/HangCuaHang.jsp");	    	
	    	
	    }
	    
	    /*else if(action.equals("phanbo")){	    	
			try{				
				Hangcuahang mfar = new Hangcuahang("");
	    		db = new dbutils();
	    		
	    		String sql = "select khachhang_fk, min(ngaynhap) as ngaynhap "+
	    				     "from DONHANG "+ 
	    				     "where TRANGTHAI = '1' "+ 
	    				     "group by KHACHHANG_FK"; 
	    		System.out.println("1.LAY NGAY NHAP :"+sql);
	    		ResultSet rs = db.get(sql);
	    		 
	    		while(rs.next())
	    		{	long ngay = mfar.getSongay(mfar.getDateTime(), mfar.getMonth(mfar.getDateTime(), 3)); // 3 thang truoc do
	    			long ngay2 = mfar.getSongay(rs.getString("ngaynhap"), mfar.getDateTime()); // ngay nhap don hang
	    		System.out.println("SO NGAY 1 :"+ngay+" va SO NGAY 2 "+ngay2);    		
	    		
	    			sql ="select * from "+
	    					"( "+
							"select khachhang_fk,";
	    			if(ngay2 <= ngay)
	    	    		
					{
	    				sql += "SUM(TONGGIATRI)/"+ngay2+"";
					}
	    			else
	    			{
	    				sql += "SUM(TONGGIATRI)/"+ngay+"";
	    			}
	    				sql +=" as doanhso, 1 as 'type' "+
							"from DONHANG "+
							"where TRANGTHAI = '1' "+
							"group by KHACHHANG_FK "+
							") "+
							"khachhang left join "+
							"( "+
							"select PK_SEQ as hchId, tumuc, denmuc , 1 as 'type' from HANGCUAHANG where TRANGTHAI = '1' "+
							") "+
							"hang on khachhang.type = hang.type "+
							"where khachhang.doanhso > hang.tumuc and khachhang.doanhso <= hang.denmuc";
	    		}    			    		
	    		System.out.println("2.TINH DOANH SO : "+sql);
	    		ResultSet rs1 = db.get(sql);
	    		System.out.println("getrow :"+rs1.getRow());	    		
	    		if(rs1.getRow()!=0)
	    		{
	    		while(rs1.next())
	    		{
	    			String query = "update khachhang set hch_fk = '"+ rs1.getString("hchId") +"' where pk_seq = '"+ rs1.getString("khachhang_fk") +"'";
	    			
	    			System.out.println("3.UPDATE HANG CUA HANG: "+query);
	    			
	    			if(db.update(query))
	    			{	System.out.println("Toi day");
	    				int n = rs1.getRow();	    				
	    				obj.setMsg("Cập nhật dữ liệu thành công. Có "+n+" khách hàng được cập nhật!");
	    			}
	    			
	    			else	    				
	    			{	obj.setMsg("Cập nhật dữ liệu không thành công : "+query);
	    				geso.dms.center.util.Utility.rollback_throw_exception(db);
	    			}
	    		}}
	    		else {
	    			obj.setMsg("Không có khách hàng nào được cập nhật!");	    			
	    		}
	    	}catch(Exception ex){obj.setMsg("Loi "+ ex.toString());}
	    	
	    	response.sendRedirect("/AHF/pages/Center/HangCuaHang.jsp");
		}	 	*/
    
	}
	
	private String getSearchQuery(HttpServletRequest request,IHangcuahangList obj){		
		
		Utility util = new Utility();
		
			String hang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("HangCuaHang")));
	    	if ( hang == null)
	    		hang = "";
	    	obj.setHangcuahang(hang);
	    	
	    	String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("DienGiai")));
	    	if (diengiai == null)
	    		diengiai = "";    	
	    	obj.setDiengiai(diengiai);
	    	
	    	String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("TrangThai")));   	
	    	if (trangthai == null)
	    		trangthai = "";    	
		
	    	if (trangthai.equals("2"))
	    		trangthai = "";
	    	
	    	obj.setTrangthai(trangthai);
	    	
	    	String query;
	    	query = "select a.pk_seq as id, a.hang, a.diengiai, a.trangthai,a.tumuc,a.denmuc, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua,a.thangtb"; 
			query = query + " from hangcuahang a, nhanvien b, nhanvien c where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq ";
			
			//geso.dms.distributor.util.Utility util = new geso.dms.distributor.util.Utility();
	    	if (hang.length() > 0){
				query = query + " and upper(dbo.ftBoDau(a.hang)) like upper(?)";
				query = query + " and upper(a.hang) like upper(?)";
				
				obj.getDataSearch().add( "%" + util.replaceAEIOU(hang) + "%" );
				obj.getDataSearch().add( "%" + util.replaceAEIOU(hang) + "%" );
	    	}
	    	
	    	if (diengiai.length() > 0){
				query = query + " and upper(dbo.ftBoDau(a.diengiai)) like upper(?)";
				obj.getDataSearch().add( "%" + util.replaceAEIOU(diengiai) + "%" );
	    	}
	  
	    	if(trangthai.length() > 0){
	    		query = query + " and a.trangthai = ?";
	    		obj.getDataSearch().add(trangthai);
	    	}
	    	query = query + " order by a.hang";
	    	System.out.println(query);
	    	return query;
		}	
	
	private String Delete(String id)
	{
		IHangcuahangList obj = new HangcuahangList();		
		String msg="";
		dbutils db = new dbutils();
		ResultSet rs1 = db.get("select count(hch_fk) as num from khachhang where hch_fk='"+ id + "'");
		try{
			rs1.next();			
			if (rs1.getString("num").equals("0")){
				db.update("delete from hangcuahang where pk_seq = '" + id + "'");
				db.shutDown();	
				
			}
			else
			{
				msg="Đã có khách hàng trong Hạng khách hàng này, nên không xóa được";
			}
				
				
		}catch(Exception e){System.out.println("Loi "+e.toString());}
		
		return msg;
	}
	
	
	
	
	
}