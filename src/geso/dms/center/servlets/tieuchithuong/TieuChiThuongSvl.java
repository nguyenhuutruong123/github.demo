package geso.dms.center.servlets.tieuchithuong;
import geso.dms.center.beans.tieuchithuong.imp.Tieuchithuong;
import geso.dms.center.beans.tieuchithuong.imp.TieuchithuongList;
import geso.dms.center.beans.tieuchithuong.ITieuchithuong;
import geso.dms.center.beans.tieuchithuong.ITieuchithuongList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.TTCCLayout;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TieuChiThuongSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public TieuChiThuongSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();	    

		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		//--- check user
//		Utility util = new Utility();
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
		if( Utility.CheckRuleUser( session,  request, response, "TieuChiThuongSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
		 //--- check user 
		
		ITieuchithuongList obj = new TieuchithuongList();
		obj.setUserId(userId);


		String action = util.antiSQLInspection(util.getAction(querystring));

		String id = util.antiSQLInspection(util.getId(querystring));
		String loai = util.antiSQLInspection(util.getId(querystring));

		if(action.equals("chot")){
			id = util.antiSQLInspection(util.getId(querystring)).split(";")[0];
			loai = util.antiSQLInspection(util.getId(querystring)).split(";")[1];
			obj.setLoai(loai);

			Chot(id);
		}

		if(action.equals("mochot")){
			//id = util.antiSQLInspection(util.getId(querystring));
			id = util.antiSQLInspection(util.getId(querystring)).split(";")[0];
			//MoChot(id);
			if(moTC(obj, id))
			{
				obj.setMsg("Mở tiêu chí thành công.");
			}
			else{ obj.setMsg("Mở tiêu chí không thành công."); }
		}

		if(action.equals("xoa")){
			id = util.antiSQLInspection(util.getId(querystring)).split(";")[0];
			loai = util.antiSQLInspection(util.getId(querystring)).split(";")[1];
			obj.setLoai(loai);

			String msg =  Xoa(id);
			obj.setMsg(msg);
		}

		obj.init();

		session.setAttribute("obj", obj);

		session.setAttribute("userId", userId);

		response.sendRedirect("/AHF/pages/Center/TieuChiThuong.jsp");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();	    
		Utility util = new Utility();
		
		//--- check user
//		Utility util = new Utility();
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
		if( Utility.CheckRuleUser( session,  request, response, "TieuChiThuongSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
		 //--- check user 
		
		ITieuchithuongList obj = new TieuchithuongList();

		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
		String kbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhId")));
		String month = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("month")));
		String year = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("year")));
		String action = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));
		String loai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loai")));
		String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
		String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));

		obj.setDvkdId(dvkdId);
		obj.setKbhId(kbhId);
		obj.setUserId(userId);
		obj.setMonth(month);
		obj.setYear(year);
		obj.setLoai(loai);
		obj.setTungay(tungay);
		obj.setDenngay(denngay);

		System.out.println(action);

		if(action.equals("timkiem")){
			obj.init();

			session.setAttribute("obj", obj);

			session.setAttribute("userId", userId);

			response.sendRedirect("/AHF/pages/Center/TieuChiThuong.jsp");
			return;
		}	    
		else if(action.equals("taomoi")){
			ITieuchithuong tctBean = new Tieuchithuong();
			tctBean.setUserId(userId);
			tctBean.setAction(action);
			tctBean.setTctId("0");	
			tctBean.setLoai(loai);
			tctBean.SetLoaiTieuChi(loai);
			tctBean.init();

			session.setAttribute("tctBean", tctBean);

			session.setAttribute("userId", userId);

			response.sendRedirect("/AHF/pages/Center/TieuChiThuongUpdate.jsp");
			return;
		}
	}
	private void MoChot(String id){
		String query = "UPDATE TIEUCHITINHTHUONG SET TRANGTHAI ='0' WHERE PK_SEQ = '" + id + "'";
		System.out.println(query);

		dbutils db = new dbutils();
		db.update(query);
		db.shutDown();
	}
	
	private boolean moTC(ITieuchithuongList obj, String id) {
		dbutils db = new dbutils();
		boolean result = true;
		try {
			db.getConnection().setAutoCommit(false);
			String query = 
					" UPDATE TIEUCHITINHTHUONG SET TRANGTHAI = '0', NGAYSUA = CONVERT(VARCHAR(10), GETDATE(), 120), NGUOISUA = '"+ obj.getUserId() +"' " +
					" WHERE PK_SEQ = '"+ id +"' AND TRANGTHAI = '1'";
			System.out.println("query motc : "+ query);
			if(db.updateReturnInt(query) != 1)
			{
				result = false;
				db.getConnection().rollback();
			}
			
			db.getConnection().commit();
		} catch (Exception e) {
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally { try { db.getConnection().setAutoCommit(true); if(db!=null) { db.shutDown(); } } catch (SQLException e) { e.printStackTrace(); } }
		return result;
	}
	
	private void Chot(String id)
	{
		String query = "UPDATE TieuChiTinhThuong SET TRANGTHAI ='1' WHERE PK_SEQ = '" + id + "'";
		System.out.println(query);

		dbutils db = new dbutils();
		db.update(query);
		db.shutDown();
	}

	private String Xoa(String id)
	{
		dbutils db = null;
		String query;
		try{

			db=  new dbutils();
			
			db.getConnection().setAutoCommit(false);

			/*query = "SELECT thang,nam FROM tieuchitinhthuong  WHERE PK_SEQ = '" + id + "'";
			ResultSet rs = db.get(query);
			rs.next();
			String thang = rs.getString("thang");
			String nam = rs.getString("nam");
			rs.close();

			query = "SELECT COUNT(*) AS NUM FROM chitieunhanvien WHERE  thang = '"+thang+"' and nam ='"+nam+"' and trangthai !=2  ";
			rs = db.get(query);
			rs.next();

			if(rs.getInt("nam") > 0 )
			{
				db.getConnection().rollback();
				db.shutDown();
				return " Chỉ tiêu "+thang+"/"+nam+"  đã được khai báo, không thể xóa KPI ";
			}

*/
			query = "DELETE TIEUCHITHUONG_CHITIET_MUCTHUONG WHERE TCTCT_FK in ( select pk_seq from TIEUCHITHUONG_CHITIET where   TIEUCHITINHTHUONG_FK = '" + id + "' ) ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				db.shutDown();
				return " Không thể xóa chi tiết KPI ";
			}
			

			query = "DELETE TIEUCHITHUONG_CHITIET WHERE TIEUCHITINHTHUONG_FK = '" + id + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				db.shutDown();
				return " Không thể xóa chi tiết KPI ";
			}


			query = "DELETE TIEUCHITINHTHUONG WHERE PK_SEQ = '" + id + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				db.shutDown();
				return " Không thể xóa KPI ";
			}
			
			db.getConnection().commit();
			db.shutDown();
			return " Xóa KPI thành công! ";

		}catch(Exception e){
			if(db != null)
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				db.shutDown();
			}
			e.printStackTrace();
			return "Xả ra lỗi khi xóa KPI tháng";
			
		}
	}

}
