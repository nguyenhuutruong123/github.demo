package geso.dms.center.servlets.banggia;

import geso.dms.center.beans.banggia.IBangGia;
import geso.dms.center.beans.banggia.IBangGiaList;
import geso.dms.center.beans.banggia.imp.BangGia;
import geso.dms.center.beans.banggia.imp.BangGiaList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/BangGiaSvl")
public class BangGiaSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	public BangGiaSvl()
	{
		super();
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    String loai = "";
	    HttpSession session = request.getSession();	
	    
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
	   
	    if (geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loai"))!=null)
	    	loai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loai"));
	    else
	    	loai = "";
	    
	    
	    String action = util.getAction(querystring);
	    String khlId = util.getId(querystring);
	    String msg = "";

	    String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		if (view == null) {
			view = "";
		}
		String param = "";
		if(view.trim().length() > 0) param ="&view="+view;
		if ( Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		
		if( Utility.CheckRuleUser( session,  request, response, "BangGiaSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
	    
	    if(action.trim().equals("delete"))
	    {
	    	msg = Delete(khlId,userId);
	    }
	    
	    if(action.trim().equals("copy"))
	    {
	    	IBangGia csxBean = new BangGia(khlId);
	    	csxBean.setView(view);
			csxBean.init();
			csxBean.setUserId(userId);
			csxBean.setLoai(loai);
			csxBean.setTrangthai("0");
	    	csxBean.setId("");
			session.setAttribute("csxBean", csxBean);
			String nextJSP = "/AHF/pages/Center/BangGiaUpdate.jsp";
			response.sendRedirect(nextJSP);
			return;
	    	//msg = copy_banggia(khlId, userId);
	    }
	    
	    if(action.trim().equals("chot"))
	    {
	    	msg = chot_bangia(khlId, userId);	    	
	    }
	    
	    IBangGiaList obj = new BangGiaList();
	    obj.setView( geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view") == null ? "" : request.getParameter("view"))    );
	    obj.setUserId(userId);
	    obj.setLoai(loai);
	    System.out.println("loai get la "+obj.getLoai()+ "loai la "+loai);
	    obj.init("");
	    obj.setMsg(msg);
	    session.setAttribute("obj", obj);
	    
	    String nextJSP = "/AHF/pages/Center/BangGia.jsp";
		response.sendRedirect(nextJSP);
	}
	
	private String chot_bangia(String id,String userId)
	{
		String msg="";
		dbutils db = new dbutils();
		try{
			db.getConnection().setAutoCommit(false);
			String query="update BANGGIAMUANPP set trangthai=1 where pk_seq="+id;
			if (db.updateReturnInt(query)!=1)
			{
				db.getConnection().rollback();
				return "Không thể chốt bảng giá,vui lòng liên hệ Admin" + query;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
		}catch(Exception e)
		{
			return "lỗi vui lòng liên hệ admin "+e.getMessage();
		}
		return msg;
	}

	private String Delete(String id,String userId)
	{
		dbutils db = new dbutils();
		try
		{
			String msg = "";
			String query = "\n SELECT COUNT(*) AS SoDong "+    
			"\n FROM DONDATHANG DH "+ 
			"\n WHERE EXISTS "+
			"\n ( "+
			"\n     SELECT * FROM "+
			"\n     BANGGIAMUANPP A INNER JOIN BANGGIAMUANPP_NPP "+ 
			"\n     B ON A.PK_SEQ=B.BANGGIAMUANPP_FK  "+
			"\n     WHERE  B.NPP_FK=DH.NPP_FK "+
			"\n     AND A.KENH_FK=DH.KBH_FK AND A.DVKD_FK=DH.DVKD_FK "+
			"\n     AND A.TUNGAY<=DH.NGAYDAT AND DH.TRANGTHAI!=6 "+
			"\n     AND A.PK_SEQ='"+id+"' "+
			"\n ) " ;
			System.out.println("[DONDATHANG]" + query);
			int sodong = 0;
			ResultSet rs = db.get(query);
			while (rs.next())
			{
				sodong = rs.getInt("sodong");
			}
			rs.close();
			
			if (sodong > 0)
			{
				msg = "Bảng giá đã được sử dụng trong đơn đặt hàng, vui lòng kiểm tra lại 1.";
				return msg;
			}
			
			/*
			query = 
			"\n SELECT COUNT(*) AS SoDong "+    
			"\n FROM DONHANG DH "+
			"\n WHERE EXISTS "+
			"\n ( "+
			"\n     SELECT * FROM  "+
			"\n     BANGGIAMUANPP A INNER JOIN BANGGIAMUANPP_NPP "+ 
			"\n     B ON A.PK_SEQ=B.BANGGIAMUANPP_FK "+
			"\n     WHERE B.NPP_FK=DH.NPP_FK "+
			"\n     AND A.KENH_FK=DH.KBH_FK "+
			"\n     AND A.TUNGAY<=DH.NGAYNHAP AND DH.TRANGTHAI!=2 "+
			"\n     AND A.PK_SEQ='"+id+"' "+
			"\n ) ";			
			rs = db.get(query);
			while (rs.next())
			{
				sodong = rs.getInt("sodong");
			}
			
			if (rs != null)
				rs.close();
			
			if (sodong > 0)
			{
				return "Bảng giá đã được sử dụng trong đơn hàng ,vui lòng kiểm tra lại !";
			}
			*/
			

			query = "\n select count(*) sodong from ERP_DONDATHANG a inner join ERP_DONDATHANG_SANPHAM b on a.pk_seq = b.dondathang_fk " +
			"\n where b.BANGGIAMUANPP_FK = "+id;
			rs = db.get(query);
			while (rs.next()) {
				sodong = rs.getInt("sodong");
			}
			rs.close();
			
			if (sodong > 0)
			{
				msg = "Bảng giá đã được sử dụng trong đơn đặt hàng, vui lòng kiểm tra lại 2.";
				return msg;
			}			  
			
			db.getConnection().setAutoCommit(false);
		
			query =	"\n INSERT INTO BANGGIA_LOG(NGUOISUA,bgmuanpp_fk,SANPHAM_FK,GIA,thoigian) " +
			"\n SELECT '"+userId+"',BGMUANPP_FK,SANPHAM_FK,GIAMUANPP, getdate() " +
			"\n FROM BGMUANPP_SANPHAM "+
			"\n WHERE BGMUANPP_FK='"+id+"' ";
			System.out.println("Query ghi Log: "+query);
			if (!db.update(query))
			{
				db.getConnection().rollback();
				msg = "Lỗi xoá bảng giá 1.";
				return msg;
			}
			
			query = "\n delete from bgmuanpp_sanpham where bgmuanpp_fk='" + id + "'";
			if (!db.update(query))
			{
				db.getConnection().rollback();
				msg = "Lỗi xoá bảng giá 2.";
				return msg;
			}
			
			query = "delete from BANGGIAMUANPP_NPP where BANGGIAMUANPP_FK='" + id + "'";
			if (!db.update(query))
			{
				db.getConnection().rollback();
				msg = "Lỗi xoá bảng giá 3.";
				return msg;
			}
			
			query = "delete from banggiamuanpp where pk_seq = '" + id + "'";
			if (!db.update(query))
			{
				db.getConnection().rollback();
				msg = "Lỗi xoá bảng giá 4.";
				return msg;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e)
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return "Lỗi ngoại lệ xoá bảng giá: "+e.getMessage();
		}
		finally
		{
			db.shutDown();
		}
		
		return "";
	}

	
	private String copy_banggia(String id, String userid)
	{
		dbutils db = new dbutils();
		
		try
		{
			db.getConnection().setAutoCommit(false);
			String query="";
			query = "\n insert BANGGIAMUANPP (TEN,NGAYTAO,NGAYSUA,NGUOITAO,NGUOISUA,DVKD_FK,KENH_FK,TRANGTHAI,TUNGAY,DENNGAY,CREATED_DATE,CHIETKHAU)	" +
			"\n select TEN, convert (char(10),GETDATE(),120), convert (char(10),GETDATE(),120),NGUOITAO,NGUOISUA,DVKD_FK,KENH_FK,0,'2030-12-30',DENNGAY,GETDATE(),CHIETKHAU from BANGGIAMUANPP where PK_SEQ="+id;
			
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể Copy bảng giá, vui lòng liên hệ Admin 1.";
			}
			
			query = "select scope_identity() as ID ";
			ResultSet rs = db.get(query);
			String idcopy="";
			if (rs.next())
			{
				idcopy = rs.getString("ID");
			}
			
			if(idcopy.length()>0)
			{
				query = "\n insert BGMUANPP_SANPHAM (BGMUANPP_FK,SANPHAM_FK,GIAMUANPP,TRANGTHAI,GIAMUANPP_TUVC,GIAMUA_SAUCK,chietkhau)  "+
				"\n select "+idcopy+",SANPHAM_FK,GIAMUANPP,TRANGTHAI,GIAMUANPP_TUVC,GIAMUA_SAUCK,chietkhau "+
				"\n from  BGMUANPP_SANPHAM where BGMUANPP_FK="+id;

				if(!db.update(query))
				{
					System.out.println("error "+query);
					db.getConnection().rollback();
					return "Không thể Copy bảng giá, vui lòng liên hệ Admin 2.";
				}

				query = "\n insert BANGGIAMUANPP_NPP (BANGGIAMUANPP_FK,NPP_FK,ChietKhau )  "+
				"\n select "+idcopy+",NPP_FK,ChietKhau from BANGGIAMUANPP_NPP where BANGGIAMUANPP_FK = "+id;
				if (!db.update(query))
				{
					db.getConnection().rollback();
					return "Không thể Copy bảng giá, vui lòng liên hệ Admin 3.";
				}
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e)
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return "Lỗi ngoại lệ Copy bảng giá: " + e.getMessage();
		}
		
		finally
		{
			db.shutDown();
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
	    
	    String userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));  
	    String ctyId = (String)session.getAttribute("congtyId");	    
	    
	    IBangGiaList obj;    

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
	    if (action == null){
	    	action = "";
	    }
	    
	    String loai="";    
	    if (loai.length() == 0)
	    	loai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loai"));

	    //--- check user
	    String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		if (view == null)
			view = "";
		//obj.setView(view);
		
		String param = "";
		if (view.trim().length() > 0) param ="&view="+view;
		if (Utility.CheckSessionUser( session,  request, response))
		{
			Utility.RedireactLogin(session, request, response);
		}
		
		if( Utility.CheckRuleUser( session,  request, response, "BangGiaSvl", param, Utility.XEM ))
		{
			Utility.RedireactLogin(session, request, response);
		}
   
	    if(action.equals("new"))
	    {
    		IBangGia khl = new BangGia();
    		khl.setView( geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view") == null ? "" : request.getParameter("view"))    );
    		khl.setUserId(userId);
    		khl.setLoai(loai);
    		khl.createRs();
    		
	    	session.setAttribute("csxBean", khl);  	
    		session.setAttribute("userId", userId);
    		response.sendRedirect("/AHF/pages/Center/BangGiaNew.jsp");
	    }
	    else
	    {
	    	obj = new BangGiaList();
		    obj.setUserId(userId);
		    obj.setView( geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view") == null ? "" : request.getParameter("view"))    );
	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/AHF/pages/Center/BangGia.jsp");	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IBangGiaList obj) 
	{
		
		obj.getDataSearch().clear();
		Utility util= new Utility();
		
		String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
	  	if ( nppId == null)
	  		nppId = "";
	  	obj.setNppId(nppId);
		
		String tenbg = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("bgTen"));
		if(tenbg == null)
			tenbg = "";
		obj.setMa(tenbg);
		
		String dvkdId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId"));
		if(dvkdId == null)
			dvkdId = "";
		obj.setDvkdId(dvkdId);
		
		String kenhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId"));
		if(kenhId == null)
			kenhId = "";
		obj.setKenhId(kenhId);
		
		String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String vung = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungid"));
		if(vung == null)
			vung = "";
		obj.setVungId(vung);
		
		String diaban = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diabanid"));
		if(diaban == null)
			diaban = "";
		obj.setDiabanId(diaban);
		
		String diengiai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai"));
		if(diengiai == null)
			diengiai = "";
		obj.setDiengiai(diengiai);
		String fromVung = "";
		String whereVung = "";
		
		if(vung.length() > 0 )
		{
			fromVung =" ,BANGGIAMUANPP_NPP f, NHAPHANPHOI npp, khuvuc kv ";
			whereVung ="  and a.PK_SEQ = f.BANGGIAMUANPP_FK and f.NPP_FK=npp.PK_SEQ and npp.KHUVUC_FK= kv.PK_SEQ 		 ";
		}
		
		String fromDiaban = "";
		String whereDiaban = "";
		
		if(diaban.length() > 0 )
		{
			fromVung="";
			whereVung="";
			fromDiaban =" ,BANGGIAMUANPP_NPP f, NHAPHANPHOI npp, khuvuc kv  ";
			whereDiaban ="  and a.PK_SEQ = f.BANGGIAMUANPP_FK and f.NPP_FK=npp.PK_SEQ and npp.KHUVUC_FK = kv.PK_SEQ 		 ";
		}
		
		String sql = 
				  " select distinct a.pk_seq, a.ten, a.trangthai , d.ten as nguoitao, a.ngaytao as ngaytao,  " +
				  " 	e.ten as nguoisua, a.ngaysua as ngaysua, b.donvikinhdoanh as dvkd, c.ten as kenh, c.pk_seq as kenhId," +
				  " 	a.TrangThai,a.TuNgay " +
				  " from BANGGIAMUANPP a, donvikinhdoanh b, kenhbanhang c, nhanvien d, nhanvien e   " + fromVung + fromDiaban +
				  " where a.dvkd_fk = b.pk_seq and a.KENH_FK = c.pk_seq and a.nguoitao = d.pk_seq and a.nguoisua = e.pk_seq   " + whereVung + whereDiaban +
				  "		 " ;
		if(tenbg.length() > 0) {
			sql += " and  dbo.ftBoDau(a.ten) like ? ";
			obj.getDataSearch().add( "%" + util.replaceAEIOU(tenbg) + "%" );
		}
		
		if(dvkdId.length() > 0){
			sql += " and a.dvkd_fk = ? ";
			obj.getDataSearch().add( dvkdId );
		}
		
		if(kenhId.length() > 0){
			sql += " and a.KENH_FK = ? ";
			obj.getDataSearch().add( kenhId );
		}
		
		if(trangthai.length() > 0){
			sql += " and a.trangthai = ? ";
			obj.getDataSearch().add( trangthai );
		}
		
		if(vung.length() > 0){
			sql += " and kv.vung_fk = ? ";
			obj.getDataSearch().add( vung );
		}
		
		if(diaban.length() > 0)
		{
			sql += " and npp.tinhthanh_fk = ?";
			obj.getDataSearch().add( diaban );
		}
		
		if(diengiai.trim().length() > 0){
			sql += " and a.pk_seq in ( select BANGGIAMUANPP_FK from BANGGIAMUANPP_NPP where NPP_FK "+
					"in ( select PK_SEQ from NhaPhanPhoi where dbo.ftBoDau( Ma + ' ' + Ten ) like ?  ) ) ";
			obj.getDataSearch().add( "%" + util.replaceAEIOU(diengiai) +"%" );
		}	
			return sql;
	}
}
