package geso.dms.center.servlets.duyettrakhuyenmai;

import geso.dms.center.beans.duyettrakhuyenmai.*;
import geso.dms.center.beans.duyettrakhuyenmai.imp.*;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DuyettrakhuyenmaiSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
   
    public DuyettrakhuyenmaiSvl() {
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
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    
	    IDuyettrakhuyenmaiList obj = new DuyettrakhuyenmaiList();
	    obj.setUserId(userId);
	    
	    String action = util.getAction(querystring);
	    String ctskuId = util.getId(querystring);
	    
	    //System.out.println("___Action: " + action + " -- Id la: " + ctskuId);
	    if(action.trim().equals("duyet"))
	    {
	    	obj.setMsg(Duyet( ctskuId, userId) );
	    }
	    
	    if(action.trim().equals("delete"))
	    {
	    	obj.setMsg(XoaChiTieu(ctskuId));
	    }

	    obj.init("");
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/AHF/pages/Center/DuyetTraKhuyenMai.jsp";
		response.sendRedirect(nextJSP);
	}
	
	private String Duyet(String ctskuId,String userId) 
	{
		dbutils db = new dbutils();
		String  query = "";
    	try 
    	{
			db.getConnection().setAutoCommit(false);
			
			
					
		
		
			

			query = " update Duyettrakhuyenmai set  trangthai = '1' where pk_seq = '" + ctskuId + "' and trangthai = 0 ";
	    	if(db.updateReturnInt(query)!=1)
	    	{
	    		Utility.rollback_and_shutdown(db);
				return "Trạng thái duyệt trả không hợp lệ";
	    	}
	    	
	    	
	    	query = " select count(*)sd from DUYETTRAKHUYENMAI_KHACHHANG  where duyetkm_fk = '" + ctskuId + "' and mucduyet is null ";
	    	ResultSet rs = db.get(query);
	    	rs.next();
	    	if(rs.getInt("sd") > 0)
	    	{
	    		Utility.rollback_and_shutdown(db);
				return "Vui lòng duyệt mức trả cho toàn bộ khách hàng trước khi chốt duyệt trả";
	    	}
	    	
	    	query = " update DUYETTRAKHUYENMAI_KHACHHANG set trangthai = 1 where  duyetkm_fk = '" + ctskuId + "' and mucduyet is not null   ";
	    	if(db.updateReturnInt(query)<=0 )
	    	{
	    		Utility.rollback_and_shutdown(db);
				return  " Lỗi, không có khách hàng nào tạo được duyệt trả!";
	    	}
	    	
	    	/*query = " delete DUYETTRAKHUYENMAI_KHACHHANG  where duyetkm_fk = '" + ctskuId + "' and isnull(mucduyet,-1) < 0   ";
	    	if(db.updateReturnInt(query)<0)
	    	{
	    		db.getConnection().rollback();
	    		db.shutDown();
				return "lỗi:"+ query ;
	    	}
	    	query = " update DUYETTRAKHUYENMAI_KHACHHANG set trangthai = 1 where duyetkm_fk = '" + ctskuId + "'   ";
	    	if(db.updateReturnInt(query)<0)
	    	{
	    		db.getConnection().rollback();
	    		db.shutDown();
				return "lỗi:"+ query ;
	    	}
	    	*/
	    	
	    	Utility.commit_and_shutdown(db);
	    	return "Chốt thành công!";
		} 
    	catch (SQLException e)
    	{
    		Utility.rollback_and_shutdown(db);
			return "lỗi:"+ e.getMessage() +"("+query +")" ;
    	}
    	
	}

	private String XoaChiTieu(String ctskuId) 
	{
		dbutils db = new dbutils();
    	
    	try 
    	{
    		String sql = "select A.PK_SEQ " +
    				"from DONHANG A INNER JOIN DUYETTRAKHUYENMAI_DONHANG B ON A.PK_SEQ = B.DONHANG_FK " +
    				"WHERE A.TRANGTHAI != 2 AND B.DuyetKm_FK = " + ctskuId;
    		ResultSet rs = db.get(sql);
    		String msg = "";
    		while(rs.next())
    			msg += rs.getString("PK_SEQ") + ", ";
    		if(msg.length() > 0){
    			db.shutDown();
    			return "Đơn đã hưởng khuyến mãi: " + msg;
    		}

			db.getConnection().setAutoCommit(false);
			
    		sql = "DELETE FROM DUYETTRAKHUYENMAI_DONHANG WHERE DuyetKm_FK = " + ctskuId + " " +
    				"AND DONHANG_FK IN (SELECT PK_SEQ FROM DONHANG WHERE TRANGTHAI = 2)";
    		if(!db.update(sql)){
    			db.getConnection().rollback();
    			db.shutDown();
    			return "Lỗi xóa duyệt trả (1)";
    		}
			
			System.out.println("delete DuyetTraKhuyenMai_KhachHang where duyetkm_fk = '" + ctskuId + "'");
	    	if(!db.update("delete DuyetTraKhuyenMai_KhachHang where duyetkm_fk = '" + ctskuId + "'"))
	    	{
	    		db.getConnection().rollback();
	    		db.shutDown();
	    		return "Lỗi xóa duyệt trả (2)";
	    	}
	    	
	    	System.out.println("delete DUYETTRAKHUYENMAI_Dangky where duyetkm_fk = '" + ctskuId + "'");
	    	if(!db.update("delete DUYETTRAKHUYENMAI_Dangky where duyetkm_fk = '" + ctskuId + "'"))
	    	{
	    		db.getConnection().rollback();
	    		db.shutDown();
	    		return "Lỗi xóa duyệt trả (3)";
	    	}
	    	System.out.println("delete DUYETTRAKHUYENMAI_KHACHHANG_SanPham where duyetkm_fk = '" + ctskuId + "'");
	    	if(!db.update("delete DUYETTRAKHUYENMAI_KHACHHANG_SanPham where duyetkm_fk = '" + ctskuId + "'"))
	    	{
	    		db.getConnection().rollback();
	    		db.shutDown();
	    		return "Lỗi xóa duyệt trả (4)";
	    	}
	    	
	    	
	    	
	    	if(!db.update("delete Duyettrakhuyenmai where pk_seq = '" + ctskuId + "'"))
			{
				db.getConnection().rollback();
				db.shutDown();
				return "Lỗi xóa duyệt trả (3)";
			}
	    	db.getConnection().commit();
	    	db.shutDown();
		} 
    	catch (SQLException e)
    	{
    		try 
    		{
				db.getConnection().rollback();
			} catch (SQLException e1) {}
    	}
    	return "";
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    
	    out = response.getWriter();
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));     
	    IDuyettrakhuyenmaiList obj;
	    
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    
	    if(action.equals("new"))
	    {
    		IDuyettrakhuyenmai tctsku = new Duyettrakhuyenmai();
    		tctsku.setUserId(userId);
    		tctsku.createRs();
    		
	    	session.setAttribute("tctskuBean", tctsku);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/AHF/pages/Center/DuyetTraKhuyenMaiNew.jsp");
	    }
	    else
	    {
	    	obj = new DuyettrakhuyenmaiList();
		    obj.setUserId(userId);

	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/AHF/pages/Center/DuyetTraKhuyenMai.jsp");	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IDuyettrakhuyenmaiList obj) 
	{
		String thang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thang"));
		if(thang == null)
			thang = "";
		obj.setThang(thang);
		
		String nam = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nam"));
		if(nam == null)
			nam = "";
		obj.setNam(nam);
		
		String diengiai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai"));
		if(diengiai == null)
			diengiai = "";
		obj.setDiengiai(diengiai);
		
		String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String sql = "select d.scheme, a.pk_seq, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua  " +
				"from DUYETTRAKHUYENMAI a inner join NHANVIEN b on a.NGUOITAO = b.pk_seq " +
				"	inner join NHANVIEN c on a.NGUOISUA = c.pk_seq" +
				"	inner join TieuChiThuongTL d on a.ctkm_fk = d.pk_seq ";
		
		if(thang.length() > 0)
			sql += " and month(a.ngaytao) = '" + thang + "' ";
		if(nam.length() > 0)
			sql += " and year(a.ngaytao ) = '" + nam + "' ";
		if(diengiai.length() > 0)
			sql += " and (a.diengiai like N'%" + diengiai + "%' or d.scheme like N'%" + diengiai + "%') ";
		if(trangthai.length() > 0)
			sql += " and a.trangthai = '" + trangthai + "' ";
		
		
		sql += "order by a.pk_seq desc";
		
		return sql;
	}

}
