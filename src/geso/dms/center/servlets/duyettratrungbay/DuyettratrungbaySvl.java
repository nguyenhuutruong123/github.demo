package geso.dms.center.servlets.duyettratrungbay;

import geso.dms.center.beans.duyettratrungbay.IDuyettratrungbay;
import geso.dms.center.beans.duyettratrungbay.imp.Duyettratrungbay;
import geso.dms.center.beans.duyettratrungbay.IDuyettratrungbayList;
import geso.dms.center.beans.duyettratrungbay.imp.DuyettratrungbayList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class DuyettratrungbaySvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public DuyettratrungbaySvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		Utility util = new Utility();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IDuyettratrungbayList dttbBean = new DuyettratrungbayList();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		dttbBean.setUserId(userId);
		String action = util.getAction(querystring);
		if(action.equals("delete")){
			String id = util.getId(querystring);
			dttbBean.setMessage(Delete(id));
		}
		else if(action.equals("chot")){
			String id = util.getId(querystring);
			dttbBean.setMessage(Duyet(id));
		}
		dttbBean.init();
		session.setAttribute("dttbBean", dttbBean);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Center/DuyetTraTrungBay.jsp";
		response.sendRedirect(nextJSP);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		IDuyettratrungbayList dttbBean = new DuyettratrungbayList();
		String nextJSP = "/AHF/pages/Center/DuyetTraTrungBay.jsp";

		Utility util = new Utility();
		dttbBean.setUserId(userId);
		
		String cttbId = util.antiSQLInspection(request.getParameter("schemeId"));
		dttbBean.setCttbId(cttbId);
		
		String vungId = util.antiSQLInspection(request.getParameter("vungId"));
		dttbBean.setVungId(vungId);

		String kvId = util.antiSQLInspection(request.getParameter("kvId"));
		dttbBean.setKvId(kvId);

		String nppId = util.antiSQLInspection(request.getParameter("nppId"));
		dttbBean.setNppId(nppId);

		
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		dttbBean.setTrangthai(trangthai);
		
		if (request.getParameter("action").equals("new"))
		{
			IDuyettratrungbay bean = new Duyettratrungbay();
			bean.setUserId(userId);
			bean.init();
			session.setAttribute("dttbBean", bean);
			session.setAttribute("userId", userId);
			nextJSP = "/AHF/pages/Center/DuyetTraTrungBayNew.jsp";
			response.sendRedirect(nextJSP);
			return;
		}
		dttbBean.init();
		session.setAttribute("dttbBean", dttbBean);
		session.setAttribute("userId", userId);
		response.sendRedirect(nextJSP);
	}

	private String Delete(String id) {
		dbutils db = new dbutils();
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String update = "DELETE FROM denghitratb_khachhang WHERE DENGHITRATB_FK = " + id;
			if(!db.update(update)){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
			    return "Khong The Cap Nhat ,Vui Long Thu Lai. Loi (1)";
			    
			}
			update = "DELETE FROM DENGHITRATRUNGBAY WHERE trangthai =0 and PK_SEQ = " + id;

			if(db.updateReturnInt(update)!=1){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
			    return "Khong The Cap Nhat ,Vui Long Thu Lai. Loi (2)";
			    
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
		}
		catch(Exception er)
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			db.shutDown();
			return "Khong The Thuc Hien Insert ,Vui Long Thu Lai. Loi "+ er.toString();
		}
		return "";
	}
	private String Duyet(String id) {
		dbutils db = new dbutils();
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String CTTB_FK = "";
			String query = "select CTTRUNGBAY_FK from DENGHITRATRUNGBAY where PK_SEQ = '"+id+"'";
			ResultSet rs = db.get(query);
			if(rs != null && rs.next()){
				CTTB_FK = rs.getString("CTTRUNGBAY_FK");
			}
			else{
				db.getConnection().rollback();
			    db.shutDown();
			    return "Lỗi kiểm tra chương trình trưng bày";
			}
			
			query = "UPDATE DENGHITRATRUNGBAY SET TRANGTHAI = 1 WHERE  trangthai = 0 and PK_SEQ = " + id;

			if(db.updateReturnInt(query)!=1 ){
				db.getConnection().rollback();
			    db.shutDown();
			    return "Khong The Cap Nhat ,Vui Long Thu Lai. Loi (2)";
			    
			}
			
			query = "\n insert khachhang_thuongtrungbay(CTTB_FK,TraTrungBay_FK,KhachHang_fk,TongLuong,TongGiaTri,SoXuat,denghitratrungbay_fk,loai_tra,hinhthuc_tra)" + 
					 "\n select  dn.CTTRUNGBAY_FK,tra.pk_seq ttbId, dnct.KHACHHANG_FK" + 
					 "\n 	, case when tra.loai = 2 and tra.hinhthuc = 2 then tra.TONGLUONG * isnull(dnct.XUATDUYET,0) else null end" + 
					 "\n 	, case tra.loai when 1 then tra.TONGTIEN * isnull(dnct.XUATDUYET,0) else null end" + 
					 "\n 	, isnull(dnct.XUATDUYET,0) ,dnct.DENGHITRATB_FK, tra.loai, tra.hinhthuc" + 
					 "\n from DENGHITRATB_KHACHHANG dnct" + 
					 "\n inner join DENGHITRATRUNGBAY dn on dnct.DENGHITRATB_FK = dn.PK_SEQ" + 
					 "\n outer apply" + 
					 "\n (" + 
					 "\n 	select  ttb.*" + 
					 "\n 	from TRATRUNGBAY ttb" + 
					 "\n 	where ttb.PK_SEQ in (select top 1 x.TRATRUNGBAY_FK from  CTTB_TRATB x where x.CTTRUNGBAY_FK = dn.CTTRUNGBAY_FK )" + 
					 "\n ) tra" + 
					 "\n where DENGHITRATB_FK = "+id+" and isnull(dnct.XUATDUYET,0) > 0 ";
			 if(db.updateReturnInt(query)<= 0)
			 {
				 System.out.println(" query ="+ query);
				 db.getConnection().rollback();
				    db.shutDown();
				    return "Không tính được trả trưng bày cho KH";
				    
			 }
			 
			 query = "\n insert khachhang_thuongtrungbay_sanpham (CTTB_FK ,denghitratrungbay_fk,tra.tratrungbay_fk,khachhang_fk,sanpham_fk,SoLuong)" + 
					 "\n select CTTB_FK ,denghitratrungbay_fk,tra.tratrungbay_fk,khachhang_fk,sp.sanpham_fk" + 
					 "\n 	,	 case tra.hinhthuc_tra when 1 then sp.SOLUONG * tra.SoXuat else null end" + 
					 "\n from khachhang_thuongtrungbay tra " + 
					 "\n inner join TRATRUNGBAY_SANPHAM sp on sp.TRATRUNGBAY_FK = tra.TraTrungBay_FK" + 
					 "\n where  tra.denghitratrungbay_fk = "+id+" and tra.loai_tra = 2 ";
			 if(db.updateReturnInt(query)< 0)
			 {
				 System.out.println(" query ="+ query);
				    db.getConnection().rollback();
				    db.shutDown();
				    return "Không tính được trả trưng bày cho KH";
				    
			 }
			
			
			

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			return "Duyệt thành công";
		}
		catch(Exception er)
		{
			er.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			db.shutDown();
			return "Khong The Thuc Hien Insert ,Vui Long Thu Lai. Loi "+ er.toString();
		}
	}

	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

}
