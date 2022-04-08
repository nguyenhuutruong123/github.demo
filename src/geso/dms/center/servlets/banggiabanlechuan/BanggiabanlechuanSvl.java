package geso.dms.center.servlets.banggiabanlechuan;

import geso.dms.center.beans.banggiablc.*;
import geso.dms.center.beans.banggiablc.imp.*;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BanggiabanlechuanSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
{
	static final long serialVersionUID = 1L;

	public BanggiabanlechuanSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();

		Utility util = new Utility();
		out = response.getWriter();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		out.println(userId);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

		String action = util.getAction(querystring);
		out.println(action);

		String bgId = util.getId(querystring);
		
		
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
		if( Utility.CheckRuleUser( session,  request, response, "BanggiabanlechuanSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
		 //--- check user 
		

		String msg = "";
		if (action.equals("delete"))
		{
			msg =Delete(bgId);
			
		}else
		if (action.equals("chot"))
		{
			msg = Chot(bgId,userId);
		}
		IBanggiablcList obj;

		obj = new BanggiablcList();
		obj.setUserId(userId);
		obj.setMsg(msg);
		session.setAttribute("obj", obj);

		String nextJSP = "/AHF/pages/Center/BangGiaBanLeChuan.jsp";
		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		IBanggiablcList obj;
		obj = new BanggiablcList();
		HttpSession session = request.getSession();
		Utility util = new Utility();
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if (action == null)
		{
			action = "";
		}
		out.println(action);
		
		
		//--- check user
	    String view = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view")));
		if (view == null)
			view = "";
		//obj.setView(view);
		String param = "";
		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			//obj.DBClose();
			Utility.RedireactLogin(session, request, response);
		}
		if( Utility.CheckRuleUser( session,  request, response, "BanggiabanlechuanSvl", param, Utility.XEM ))
		{
			//obj.DBClose();
			Utility.RedireactLogin(session, request, response);
		}
		 //--- check user
		
		

		if (action.equals("new"))
		{

			IBanggiablc bgBean = (IBanggiablc) new Banggiablc();
			bgBean.setUserId(userId);

			session.setAttribute("bgblcBean", bgBean);

			String nextJSP = "/AHF/pages/Center/BangGiaBanLeChuanNew.jsp";
			response.sendRedirect(nextJSP);

		}
		if (action.equals("search"))
		{
			String search = getSearchQuery(request, obj);

			obj.init(search);

			obj.setUserId(userId);

			session.setAttribute("obj", obj);

			response.sendRedirect("/AHF/pages/Center/BangGiaBanLeChuan.jsp");

		}
	}

	private String getSearchQuery(HttpServletRequest request, IBanggiablcList obj)
	{
		// PrintWriter out = response.getWriter();

		// IBanggiablcList obj = new BanggiablcList();
		obj.getDataSearch().clear();
		Utility util = new Utility();

		String bgTen = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("bgTen")));
		if (bgTen == null)
			bgTen = "";
		obj.setTen(bgTen);

		String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
		if (dvkdId == null)
			dvkdId = "";
		obj.setDvkdId(dvkdId);
		
		String kbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhId")));
		if (kbhId == null)
			kbhId = "";
		obj.setKbhId(kbhId);

		String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
		if (trangthai == null)
			trangthai = "";
		if (trangthai.equals("2"))
			trangthai = "";
		obj.setTrangthai(trangthai);

		String lkhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("lkhId")));
		if (lkhId == null)
			lkhId = "";
		obj.setLoaikhachhangId(lkhId);
		System.out.println("loaikhachhang:" + lkhId);
		String query =  "\n select a.tungay,kbh.ten KBH, a.pk_seq as id, a.ten as ten, a.trangthai as trangthai, c.ten as nguoitao" +
						"\n	, a.ngaytao as ngaytao, d.ten as nguoisua, a.ngaysua as ngaysua, b.donvikinhdoanh as dvkd" +
						"\n		, b.pk_seq as dvkdId " +
						"\n from banggiabanlechuan a " +
						"\n inner join donvikinhdoanh b on  a.dvkd_fk=b.pk_seq" +
						"\n inner join nhanvien c on  a.nguoitao = c.pk_seq " +
						"\n inner join nhanvien d on  a.nguoisua = d.pk_seq " +
						"\n inner join kenhbanhang kbh on a.kbh_fk = kbh.pk_seq " +
						"\n where 1 = 1   ";

		if (bgTen.length() > 0)
		{
			query = query + " and upper(dbo.ftBoDau(a.ten)) like upper(?)";
			
			obj.getDataSearch().add( "%" + util.replaceAEIOU(bgTen) + "%"  );
		}

		if (dvkdId.length() > 0)
		{
			query = query + " and b.pk_seq = ?";
			obj.getDataSearch().add( dvkdId );

		}
		if (kbhId.length() > 0)
		{
			query = query + " and kbh.pk_seq = ?";
			obj.getDataSearch().add( kbhId );

		}

		if (trangthai.length() > 0)
		{
			query = query + " and a.trangthai = ?";
			obj.getDataSearch().add( trangthai );

		}
		if(lkhId.trim().length()>0){
			query += "  and a.pk_seq in (select bgblc_fk from BANGGIABANLECHUAN_LOAIKHACHHANG where LKH_FK = '"+lkhId+"' )";
		}
	
		System.out.println(" search : "+ query);
		
		return query;
	}

	private String Chot(String id,String userId)
	{
		dbutils db = new dbutils();
		try
		{
				db.getConnection().setAutoCommit(false);
				String sql_getnpp=	 "\n select PK_SEQ manpp from NHAPHANPHOI where PK_SEQ != 1 and TRANGTHAI = 1    ";
				System.out.println ( "Lay Ra Nhung Nha PP Theo DVDK :"+ sql_getnpp );
				ResultSet rs_getnpp=db.get(sql_getnpp);

				while(rs_getnpp.next())
				{
					String sql_insertbangia="\n insert into BANGGIABANLENPP(kbh_fk,ngaybatdau,ten,ngaytao,ngaysua,nguoitao,nguoisua,dvkd_fk,npp_fk,BANGGIABANLECHUAN_FK) "+
											"\n  select kbh_fk ,tungay ,ten,'"+Utility.getNgayHienTai()+"','"+Utility.getNgayHienTai()+"',"+userId+","+userId+",dvkd_fk,"+rs_getnpp.getString("manpp")+",pk_seq " +
											"\n from banggiabanlechuan where pk_Seq =  "+ id;
					System.out.println("4. BANGGIABANLENPP : "+sql_insertbangia);
					if (db.updateReturnInt(sql_insertbangia) <=0)
					{
						
						db.getConnection().rollback();			
						db.shutDown();
						return "Chot lối 1 : "+sql_insertbangia;		
					}
				}
				
				String query = 
					"\n INSERT INTO BANGGIABANLENPP_loaikhachhang(bgblnpp_fk,lkh_fk) "+
					"\n SELECT B.PK_SEQ BGBANLENPP_FK ,A.lkh_fk "+
					"\n  FROM BANGGIABANLECHUAN_loaikhachhang A "+ 
					"\n INNER JOIN "+  
					"\n ( SELECT PK_SEQ, BANGGIABANLECHUAN_FK FROM BANGGIABANLENPP ) B ON a.BGBLC_FK = b.BANGGIABANLECHUAN_FK AND a.BGBLC_FK = "+id+" ";
					if (db.updateReturnInt(query) <=0)
					{
						db.getConnection().rollback();			
						db.shutDown();
						return "Chốt BANGGIABANLENPP_loaikhachhang lỗi: "+query;		
					}
				
				String command=
				"\n INSERT INTO BGBANLENPP_SANPHAM(BGBANLENPP_FK,SANPHAM_FK,GIABANLENPP,GIABANLECHUAN,luonhien,checkban) "+
				"\n SELECT    B.PK_SEQ as BGBANLENPP_FK ,A.SANPHAM_FK,A.GIABANLECHUAN,A.GIABANLECHUAN,A.luonhien,a.checkban "+
				"\n  FROM BANGGIABLC_SANPHAM A "+ 
				"\n INNER JOIN "+  
				"\n ( "+
				"\n 	 SELECT PK_SEQ, BANGGIABANLECHUAN_FK FROM BANGGIABANLENPP "+
				"\n  ) B ON a.BGBLC_FK = b.BANGGIABANLECHUAN_FK AND a.BGBLC_FK = "+id+" ";
				System.out.println("[BGBANLENPP_SANPHAM] "+command);
				if (db.updateReturnInt(command) <=0)
				{
					db.getConnection().rollback();			
					db.shutDown();
					return "Chot lối 2 : "+command;		
				}
				
				command = " update BANGGIABANLECHUAN set trangthai = 1, nguoiduyet ="+userId+" where trangthai = 0 and pk_Seq =   " + id;
				if (db.updateReturnInt(command) <=0)
				{
					db.getConnection().rollback();			
					db.shutDown();
					return "Chot lối 3 : "+command;		
				}
				db.getConnection().commit();			
				db.shutDown();
				return "";	
			
		}catch (Exception e) {
			e.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			db.shutDown();
			return "Exception:" + e.getMessage();
		}
		
	}
	
	private String Delete(String id)
	{
		
		dbutils db = new dbutils();
		try
		{
			db.getConnection().setAutoCommit(false);
				String command=
					"delete from banggiablc_sanpham where bgblc_fk='" + id + "'";
				if (db.updateReturnInt(command) <=0)
				{
					db.getConnection().rollback();			
					db.shutDown();
					return "Chot lối 2 : "+command;		
				}
				
				command = "delete from banggiabanlechuan where trangthai = 0 and pk_seq = '" + id + "'";
				if (db.updateReturnInt(command) <=0)
				{
					db.getConnection().rollback();			
					db.shutDown();
					return "Chot lối 3 : "+command;		
				}
				db.getConnection().commit();			
				db.shutDown();
				return "";	
			
		}catch (Exception e) {
			e.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			db.shutDown();
			return "Exception:" + e.getMessage();
		}
		
		

	}

}
