package geso.dms.distributor.servlets.donhangtrave;

import geso.dms.center.util.Webservice_UTN;
import geso.dms.distributor.beans.donhangtrave.IDonhangtrave;
import geso.dms.distributor.beans.donhangtrave.IDonhangtraveList;
import geso.dms.distributor.beans.donhangtrave.imp.Donhangtrave;
import geso.dms.distributor.beans.donhangtrave.imp.DonhangtraveList;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cete.dynamicpdf.text.dd;

public class DonhangtraveSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	IDonhangtraveList obj;
	PrintWriter out; 
	String userId;
	
    public DonhangtraveSvl() 
    {
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
		userId = util.getUserId(querystring);
		out.println(userId);

		if (userId.length()==0)
			userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));

		String action = util.getAction(querystring);
		out.println(action);
		
		String dhtvId = util.getId(querystring);
		obj = new DonhangtraveList();
		obj.setUserId(userId);
		System.out.println("action ____" + action);
		if (action.equals("delete"))
		{	   		  	    	
			obj.setMsg( Delete(dhtvId,userId));
			out.print(dhtvId);
		}
		else
		{
			if (action.equals("chotdh"))
			{
				obj.setMsg(ChotDonHang(dhtvId,userId));
			}
			String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
			if ( nppId == null)
				nppId = "";
			obj.setNppId(nppId);


			String ddkdId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdTen"));
			if ( ddkdId == null)
				ddkdId = "";
			obj.setDdkdId(ddkdId);


			String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));
			if (trangthai == null)
				trangthai = "";    	
			obj.setTrangthai(trangthai);

			String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
			if (tungay == null)
				tungay = "";    	
			obj.setTungay(tungay);

			String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
			if (denngay == null)
				denngay = "";    	
			obj.setDenngay(denngay);
		}
		System.out.println("+++++ vao ++++++");
		obj.init("");
		session.setAttribute("obj", obj);
		session.setAttribute("tbhId", "");
		session.setAttribute("khId", "");

		String nextJSP = "/AHF/pages/Distributor/DonHangTraVe.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		this.out = response.getWriter();
		Utility util = new Utility();
		HttpSession session = request.getSession();
		userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action1"));
		if (action == null){
			action = "";
		}

		obj = new DonhangtraveList();
		String userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")); 
		obj.setUserId(userId);

		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
		if ( nppId == null)
			nppId = "";
		obj.setNppId(nppId);

		String ddkdId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdTen"));
		if ( ddkdId == null)
			ddkdId = "";
		obj.setDdkdId(ddkdId);

		String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null)
			trangthai = "";    	
		obj.setTrangthai(trangthai);

		String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
		if (tungay == null)
			tungay = "";    	
		obj.setTungay(tungay);

		String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
		if (denngay == null)
			denngay = "";    	
		obj.setDenngay(denngay);

		session.setAttribute("dhtvBean", obj);

		if (action.equals("taomoi")){
			IDonhangtrave dhtvBean = (IDonhangtrave) new Donhangtrave("");
			dhtvBean.setUserId(userId);
			dhtvBean.createRS();
			session.setAttribute("nppId", dhtvBean.getNppId());
			session.setAttribute("dhtvBean", dhtvBean);
			String nextJSP = "/AHF/pages/Distributor/DonHangTraVeNew.jsp";
			response.sendRedirect(nextJSP);

		}
		else {
			String search = getSearchQuery(request);
			obj.setUserId(userId);
			obj.init(search);
			session.setAttribute("obj", obj);  	
			session.setAttribute("userId", userId);
			response.sendRedirect("/AHF/pages/Distributor/DonHangTraVe.jsp");	    
		}
	}
	
	private String getSearchQuery(HttpServletRequest request) 
	{
		Utility util = new Utility();
		String ddkdId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdTen"));
    	if ( ddkdId == null)
    		ddkdId = "";
    	obj.setDdkdId(ddkdId);
    	
    	String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
    	if ( nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
    	String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));
    	if (trangthai == null)
    		trangthai = "";    	
    	obj.setTrangthai(trangthai);
    	
    	String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
    	if (tungay == null)
    		tungay = "";    	
    	obj.setTungay(tungay);
    	
    	String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
    	if (denngay == null)
    		denngay = "";    	
    	obj.setDenngay(denngay);
    	
    	String query = "\n select isnull(a.ghichutt,'') as lydo,a.pk_seq as dhtvId, a.ngaynhap, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, " +
		"\n c.ten as nguoisua, d.ten as khTen,d.smartid, d.pk_seq as khId, e.pk_seq as ddkdId, e.ten as ddkdTen, " +
		"\n f.ten as nppTen, a.VAT, " +
		"\n case when DONHANG_FK Is not null then (select SUM(soluong) from DONHANG_SANPHAM where DONHANG_FK = a.DONHANG_FK) " + 
		"\n else (select SUM(soluong) from DONHANGTRAVE_SANPHAM where DONHANGTRAVE_FK = a.pk_seq) end AS TongLuong, " +
		"\n case when DONHANG_FK Is not null then (select SUM(TONGGIATRI) from DONHANG where DONHANG_FK = a.DONHANG_FK) " +
		"\n else (select SUM(SOLUONG*GIAMUA) from DONHANGTRAVE_SANPHAM where DONHANGTRAVE_FK = A.pk_seq) end AS TongTien " +
		"\n from donhangtrave a " +
		"\n inner join nhanvien b on a.nguoitao = b.pk_seq " +
		"\n inner join nhanvien c on a.nguoisua = c.pk_seq " +
		"\n inner join khachhang d on a.khachhang_fk = d.pk_seq " +
		"\n inner join daidienkinhdoanh e on a.ddkd_fk = e.pk_seq " +
		"\n inner join nhaphanphoi f on a.npp_fk = f.pk_seq " +
		"\n where 1 = 1 ";
    	
		if (nppId.length()>0)
    	{
			query = query + " and f.pk_seq = '" + nppId + "'";		
    	}
		
    	if (ddkdId.length()>0)
    	{
			query = query + " and e.pk_seq = '" + ddkdId + "'";		
    	}
    	
    	if (trangthai.length()>0){
			query = query + " and a.trangthai ='" + trangthai + "'";
			
    	}
    	
    	if (tungay.length()>0){
			query = query + " and a.ngaynhap > '" + tungay + "'";
			
    	}
    	
    	if (denngay.length()>0){
			query = query + " and a.ngaynhap < '" + denngay + "'";
			
    	}
    	query = query + " order by a.pk_seq";
    	System.out.println("_+_+_+__" + query);
    	return query;
    	
	}

	private String Delete(String id,String nppId)
	{
		dbutils db = new dbutils();
		try
		{
			db.getConnection().setAutoCommit(false);
			String query ="DELETE FROM DONHANGTRAVE_sANPHAM WHERE DONHANGTRAVE_FK='"+id+"'";
			if (db.updateReturnInt(query)<=0)
			{
				db.getConnection().rollback();
				return "Không thể xóa đơn hàng trả về "+query;
			}
			
			/*query ="DELETE FROM DONHANGTRAVE_sANPHAM_chitiet WHERE DONHANGTRAVE_FK='"+id+"'";
			if (db.updateReturnInt(query)<=0)
			{
				db.getConnection().rollback();
				return "Không thể xóa đơn hàng trả về "+query;
			} */
			query =
			"DELETE FROM DONHANGTRAVE WHERE PK_SEQ='"+id+"' AND TRANGTHAI=0 ";
			if (db.updateReturnInt(query)!=1)
			{
				db.getConnection().rollback();
				return "Không thể xóa đơn hàng trả về do trạng thái không hợp lệ ";
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return "";
		} catch (Exception e)
		{
			try
			{
				db.getConnection().rollback();
			} catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		return "";
		
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private String ChotDonHang(String id,String userId) 
	{
		dbutils db = new dbutils();
		try 
		{
			String msg ="";
			db.getConnection().setAutoCommit(false);	


			String query = "update donhangtrave set nguoiduyet = '" + userId + "', ngayduyet = '" + this.getDateTime() + "' where pk_seq = '" + id + "' and trangthai=0 ";
			if (db.updateReturnInt(query)!=1)
			{
				db.getConnection().rollback();
				db.shutDown();
				return "Trạng thái không hợp lê vui lòng thử lại" ;
			}

			String ngayksthem1="";
			query = "select Replace(convert(char(10), DATEADD(day, 1, cast(max(ngayks) as datetime)) , 102) , '.', '-' ) as ngay from khoasongay where npp_fk=(select npp_fk from donhangtrave where pk_Seq="+id+")";
			System.out.println(query);
			ResultSet rs1=db.get(query);
			if (rs1!=null)
			{
				if (rs1.next())
				{
					ngayksthem1=rs1.getString("ngay");
					System.out.println("Ngay Khoa So Them " +ngayksthem1);
				}else
				{
					db.getConnection().rollback();
					db.shutDown();
					return "I.Error: " + query;
				}
			}else
			{
				db.getConnection().rollback();
				return  "II.Error: " + query;
			}
			geso.dms.center.util.Utility util = new geso.dms.center.util.Utility(); 
			query = "select b.sanpham_fk, b.soluong, a.npp_fk, a.kho_fk, a.kbh_fk, a.ngaynhap from donhangtrave a inner join donhangtrave_sanpham b on a.pk_seq = b.donhangtrave_fk where a.pk_seq = '" + id + "' and trangthai = 0";
			ResultSet rs = db.get(query);
			String nppId="";
			while(rs.next())
			{
				String sanpham_fk = rs.getString("sanpham_fk");
				Double soluong = rs.getDouble("soluong");
				String kho_fk = rs.getString("kho_fk");
				String kbh_fk = rs.getString("kbh_fk");
				String npp_fk = rs.getString("npp_fk");
				nppId=npp_fk;
				String ngaynhap = rs.getString("ngaynhap");
				String ngaynhapkho = rs.getString("ngaynhap"); // tăng kho theo ngày nhập
				msg=util.Update_NPP_Kho_Sp(ngaynhap,id, "hang tra ve tong"+ id, db, kho_fk, sanpham_fk, npp_fk, kbh_fk,soluong,  0.0,soluong , 0.0);
				if (msg.length()>0)
				{
					db.getConnection().rollback();
					db.shutDown();
					msg = "Lỗi kho khi tạo đơn hàng: " + msg;
					return msg;
				}	
				String msg1=util.Update_NPP_Kho_Sp_Chitiet(ngaynhap, " hangtrave DHID: "+id ,  db, kho_fk, sanpham_fk, npp_fk, kbh_fk, ngaynhapkho ,soluong,0,soluong, 0, 0);
				if (msg1.length()> 0) 
				{
					msg=msg1;
					db.getConnection().rollback();
					db.shutDown();
					return msg;
				}
			}		



			query = "update donhangtrave set trangthai = '3', nguoiduyet = '" + userId + "', ngayduyet = '" + this.getDateTime() + "', NgayGioChot= CONVERT(char(10),GETDATE(),121) + ' ' + CONVERT(CHAR( 5),GETDATE(),114)  where pk_seq = '" + id + "' and trangthai = 0 ";
			if (db.updateReturnInt(query)!=1)
			{
				db.getConnection().rollback();
				db.shutDown();
				return "Error: " + query;
			}

		
			rs.close();
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			return "Chốt thành công";
		} 
		catch(Exception e) 
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			db.shutDown();
			return "Error: " + e.getMessage();
		}
		
	}

}

