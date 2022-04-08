package geso.dms.distributor.servlets.dieuchinhtonkho;

import geso.dms.distributor.beans.dieuchinhtonkho.IDieuchinhtonkho;
import geso.dms.distributor.beans.dieuchinhtonkho.imp.Dieuchinhtonkho;
import geso.dms.distributor.beans.dieuchinhtonkho.IDieuchinhtonkhoList;
import geso.dms.distributor.beans.dieuchinhtonkho.imp.DieuchinhtonkhoList;
import geso.dms.distributor.util.Utility;
import geso.dms.distributor.db.sql.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DieuchinhtonkhoSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DieuchinhtonkhoSvl() {
		super();
	}   	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
			IDieuchinhtonkhoList obj;

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();

			Utility util = new Utility();
			out = response.getWriter();

			String querystring = request.getQueryString();
			String action = util.getAction(querystring);

			String dctkId = util.getId(querystring);

			userId = util.getUserId(querystring);


			if (userId.length()==0)
				userId = util.antiSQLInspection(request.getParameter("userId"));

			obj = new DieuchinhtonkhoList();

			String msg="";
			if (action.equals("delete"))
			{	   		  	    	
				  msg= Delete(dctkId);
			}else  if (action.equals("chot"))
			{
				  msg = this.Chot(dctkId, userId);
			}
			obj.setMsg(msg);
			obj.setUserId(userId);
			obj.init0();
			obj.createDctklist("");
			session.setAttribute("obj", obj);
			String nextJSP = "/AHF/pages/Distributor/DieuChinhTonKho.jsp";
			response.sendRedirect(nextJSP);
		}
	}  	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
			IDieuchinhtonkhoList obj;


			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			Utility util = new Utility();

			IDieuchinhtonkho dctkBean = (IDieuchinhtonkho) new Dieuchinhtonkho();
			userId = util.antiSQLInspection(request.getParameter("userId"));

			String action = request.getParameter("action");
			if (action == null){
				action = "";
			}

			out.print(action);
			if (action.equals("new")){	    	

				dctkBean.setUserId(userId);
				dctkBean.setNppId(util.antiSQLInspection(request.getParameter("nppId")));
				dctkBean.initNew();
				session.setAttribute("dctkBean", dctkBean);
				out.println(dctkBean.getMessage());

				String nextJSP = "/AHF/pages/Distributor/DieuChinhTonKhoNew.jsp";
				response.sendRedirect(nextJSP);    		

			}
			else  if (action.equals("search")){
				obj = new DieuchinhtonkhoList();
				obj.setUserId(util.antiSQLInspection(request.getParameter("userId")));
				String query = getSearchQuery(request, obj);		    
				obj.init0();
				obj.createDctklist(query);
				session.setAttribute("obj", obj);

				String nextJSP = "/AHF/pages/Distributor/DieuChinhTonKho.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				obj = new DieuchinhtonkhoList();
				obj.setUserId(userId);
				obj.init0();
				obj.createDctklist("");
				session.setAttribute("obj", obj);

				String nextJSP = "/AHF/pages/Distributor/DieuChinhTonKho.jsp";
				response.sendRedirect(nextJSP);
			}
		}


	}

	private String getSearchQuery(HttpServletRequest request, IDieuchinhtonkhoList obj)
	{
		Utility util = new Utility();
		String nppId = util.antiSQLInspection(request.getParameter("nppId"));		
		if (nppId == null)
			nppId = "";
		obj.setNppId(nppId);

		String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
		if (dvkdId == null)
			dvkdId = "";
		obj.setDvkdId(dvkdId);

		String kbhId = util.antiSQLInspection(request.getParameter("kbhId"));
		if (kbhId == null)
			kbhId = "";
		obj.setKbhId(kbhId);

		String khoId = util.antiSQLInspection(request.getParameter("khoId"));
		if (khoId == null)
			khoId = "";
		obj.setKhoId(khoId);

		String tungay = util.antiSQLInspection(request.getParameter("tungay"));
		if (tungay == null)
			tungay = "";    	
		obj.setTungay(tungay);

		String denngay = util.antiSQLInspection(request.getParameter("denngay"));
		if (denngay == null)
			denngay = "";    	
		obj.setDenngay(denngay);

		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null)
			trangthai = "";    	
		obj.setTrangthai(trangthai);

		String query=	"select distinct a.ngaydc, a.pk_seq as chungtu,d.donvikinhdoanh as dvkd, e.ten as kbh, "+
				" f.ten, a.tongtien, a.trangthai, b.ten as nguoitao, c.ten as nguoisua, isnull(g.ten,'0') as nguoiduyet ,a.NppDaChot"+ 
				" from dieuchinhtonkho a inner join  nhanvien b on b.pk_Seq=a.nguoitao inner join nhanvien c on a.nguoisua=c.pk_Seq "+ 
				" inner join donvikinhdoanh d on d.pk_Seq=a.dvkd_fk  "+
				" inner join  kenhbanhang e on e.pk_seq=a.kbh_fk inner join  kho f  on f.pk_Seq= a.kho_fk "+ 
				" left join  nhanvien g  on g.pk_seq=a.nguoiduyet "+
				" where  a.npp_fk='"+nppId+"'  ";
		if (dvkdId.length()>0){
			query = query + " and a.dvkd_fk = '" + dvkdId + "'";

		}

		if (kbhId.length()>0){
			query = query + " and a.kbh_fk = '" + kbhId + "'";

		}

		if (khoId.length()>0){
			query = query + " and a.kho_fk = '" + khoId + "'";

		}

		if (tungay.length()>0){
			query = query + " and a.ngaydc >= '" + tungay+ "'";

		}

		if (denngay.length()>0){
			query = query + " and a.ngaydc <= '" + denngay+ "'";

		}

		if (trangthai.length()>0){
			query = query + " and a.trangthai = '" + trangthai+ "'";

		}

		query = query + " order by a.trangthai, a.pk_seq";
		System.out.println("1.Query dieu chinh ton kho: " + query);
		return query;

	}	

	private String Chot(String id, String userId)
	{
		dbutils db = new dbutils();
		try
		{
			String msg = "";
			db.getConnection().setAutoCommit(false);
			String query = "update DieuChinhTonKho set trangthai = 0, ngaygiochot = CONVERT(char(10),GETDATE(),121) + ' ' + CONVERT(CHAR( 5),GETDATE(),114) where pk_seq = '" + id + "' and trangthai = 0   ";
			if(db.updateReturnInt(query)!=1)
			{
				msg = "Vui lòng reload lại đơn hàng do trạng thái không hợp lệ";

				geso.dms.center.util.Utility.rollback_and_shutdown(db);
				return msg;
			}
		
			String msgKS  = geso.dms.center.util.Utility.Check_Huy_NghiepVu_KhoaSo("dieuchinhtonkho", id, "ngaydc", db);
			if( msgKS.length() > 0)
			{
				msg = msgKS;
				geso.dms.center.util.Utility.rollback_and_shutdown(db);
				return msg;
			}
			
				query = "update DieuChinhTonKho set nppDaChot = '1',Modified_Date=dbo.GetLocalDate(DEFAULT) where pk_seq = '" + id + "' and trangthai =0   ";
			if(db.updateReturnInt(query)!=1)
			{
				msg = "Không thể chốt phiếu,vui lòng reload lại phiếu.";
				geso.dms.center.util.Utility.rollback_and_shutdown(db);
				return msg;
			}
			
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return "";
		}
		catch (Exception e) 
		{
			geso.dms.center.util.Utility.rollback_and_shutdown(db);
			return "Exception:" +e.getMessage();
		}
		
	}

	private String Delete(String dctkId)
	{		
		dbutils db = new dbutils();
		String msg="";
		String query = "";
		geso.dms.center.util.Utility util = new geso.dms.center.util.Utility();
		
		try
		{
			db.getConnection().setAutoCommit(false);
			// CAP NHAT LAI BOOK DOI VOI NHUNG DCTK AM
  
			
			query = "update dieuchinhtonkho set trangthai = '2' where pk_seq = '"+ dctkId +"' and TrangThai=0 and isnull(nppDaChot,0)  != '1' ";
			if(db.updateReturnInt(query)!=1)
			{
				 
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return "Không thể xóa phiếu,vui lòng reload lại phiếu, Lỗi dòng lệnh : "+query;
				
			}
			
			
			query=	"	SELECT dcsp.ngaynhapkho ,dc.ngaydc,DC.KHO_FK, DC.KBH_FK, DC.NPP_FK, SANPHAM_FK, (-1)*DCSP.DIEUCHINH AS DIEUCHINH FROM DIEUCHINHTONKHO DC " +
			"	INNER JOIN DIEUCHINHTONKHO_SP DCSP ON DCSP.DIEUCHINHTONKHO_FK = DC.PK_SEQ" +
			" WHERE DC.PK_SEQ = '"+ dctkId +"' AND DCSP.DIEUCHINH < 0 " ;
	System.out.println("____query: "+query);		
    ResultSet ckRs = db.get(query);
    while(ckRs.next())
    {
    	String kho_fk=ckRs.getString("kho_fk");
		String nppId=ckRs.getString("npp_fk");	
		String kbh_fk=ckRs.getString("kbh_fk");
		String sanpham_fk=ckRs.getString("sanpham_fk");
		String ngaydc  =ckRs.getString("ngaydc");
		String ngaynhapkho  =ckRs.getString("ngaynhapkho");
		
		Double dieuchinh = ckRs.getDouble("dieuchinh");

		System.out.println("__dieuchinh: "+dieuchinh);
		msg=util.Update_NPP_Kho(ngaydc,dctkId, "XÓA DIEUCHINHTONKHO", db, kho_fk, sanpham_fk, nppId, kbh_fk,ngaynhapkho, 0.0,  (-1)*dieuchinh, dieuchinh,0, 0.0);
		if(msg.length()>0)
		{
			db.getConnection().rollback();
			db.getConnection().setAutoCommit(true);
			return msg;
		}			
    }
			
			
			//
			
			
			
		
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);

		}catch(Exception e)
		{
			msg="Exception xảy ra khi cập nhật ";
			e.printStackTrace();
			try 
			{
				db.getConnection().rollback();
			} catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
		}
		finally
		{
			db.shutDown();
		}
		return msg ;
	}
}
