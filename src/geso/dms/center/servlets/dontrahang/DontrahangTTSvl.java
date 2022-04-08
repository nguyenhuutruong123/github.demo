package geso.dms.center.servlets.dontrahang;

import geso.dms.center.util.Utility;
import geso.dms.center.beans.dontrahang.IDontrahangList;
import geso.dms.center.beans.dontrahang.imp.DontrahangList;
import geso.dms.center.beans.dontrahang.IDontrahang;
import geso.dms.center.beans.dontrahang.imp.Dontrahang;
import geso.dms.center.db.sql.db_Sync;
import geso.dms.center.db.sql.dbutils;

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


public class DontrahangTTSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DontrahangTTSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IDontrahangList obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();

		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

		String action = util.getAction(querystring);

		String lsxId = util.getId(querystring);
		obj = new DontrahangList();
		obj.setUserId(userId);

		if (action.equals("delete"))
		{
			String msg = this.DeleteChuyenKho(lsxId, userId);
			obj.setMsg(msg);

		} else if (action.equals("chot"))
		{
			String msg = this.Chot(lsxId, userId);
			obj.setMsg(msg);
		} else if (action.equals("UnChot"))
		{
			String msg = this.UnChot(lsxId, userId);
			obj.setMsg(msg);
		}

		obj.init("");
		session.setAttribute("obj", obj);

		String nextJSP = "/AHF/pages/Center/DonTraHang.jsp";
		response.sendRedirect(nextJSP);
	}

	private String UnChot(String lsxId, String userId)
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			String query="";
			query = " update kho set kho.SOLUONG = kho.SOLUONG + CT.tongxuat, " +
					" 			   kho.BOOKED = kho.BOOKED + CT.tongxuat " +
					" from " +
					" ( " +
					" 	select a.kho_fk as kho_fk, a.npp_fk,a.KBH_FK , b.sanpham_fk, SUM(b.soluong) as tongxuat  " +
					" 	from DonTraHang a inner join DonTraHang_SP b on a.pk_seq = b.dontrahang_Fk " +
					" 	where dontrahang_Fk = '" + lsxId + "' " +
					" 	group by a.kho_fk, a.npp_fk, a.kbh_fk, b.sanpham_fk " +
					" ) " +
					" CT inner join NHAPP_KHO kho on CT.kho_fk = kho.KHO_FK  " +
					" 	and CT.sanpham_fk = kho.SANPHAM_FK and CT.NPP_FK = kho.npp_fk and CT.KBH_FK = kho.kbh_fk ";
			System.out.println("---TANG KHO NGUOC LAI 2: " + query );
			if(db.updateReturnInt(query)<=0)
			{
				msg = "Không thể cập nhật NHAPP_KHO " + query;
				db.getConnection().rollback();
				return msg;
			}
			query = "update DonTraHang set trangthai = '0',NgaySua='"+getDateTime()+"',Modified_Date=dbo.GetLocalDate(DEFAULT) where pk_seq = '" + lsxId + "' and trangthai=2  ";
			if(db.updateReturnInt(query)!=1)
			{
				msg = "Phiếu trả hàng này đã chốt rồi !"+query;
				db.getConnection().rollback();
				return msg;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return "Exception: " + e.getMessage();
		}
		finally
		{
			if(db!=null)db.shutDown();
		}
		return "";
	}

	private String Chot(String id, String userId)
	{
		dbutils db = new dbutils();
		//db_Sync SYNC = new db_Sync(); 
		try
		{
			String msg = "";
			db.getConnection().setAutoCommit(false);
			String query = "update DonTraHang set trangthai = '2',Modified_Date=dbo.GetLocalDate(DEFAULT) where pk_seq = '" + id + "' and trangthai in (1)  ";
			if(db.updateReturnInt(query)!=1)
			{
				msg = "Phiếu trả hàng này đã chốt rồi !"+query;
				db.getConnection().rollback();
				return msg;
			}
			/*query= " insert into  DMS_Header(LoaiDonHang,DonTraHang_FK,UserId)"+
					" select 	'ZRE5','"+id+"','"+userId+"' ";
			System.out.println("[DMS_Header]"+ query);
			if(db.updateReturnInt(query)!=1)
			{
				msg = "Không thể đẩy đơn hàng sang SAP!Vui lòng kiểm tra lại kết nối !";
				db.getConnection().rollback();
				return msg;
			}

			String HeaderID=null;
			ResultSet rsDDH = db.get("select Scope_identity() as ID ");
			if(rsDDH.next())
			{
				HeaderID= rsDDH.getString("ID");
			}
			rsDDH.close();

			query=

					"		select 'ZRE5' as LoaiDonHang ,  "+
							"		'1000'  as ToChucBanHang,'10' as KenhBanHang,00 AS NganhHang,  "+
							"		b.MA as NguoiMuaHang,b.MA as NguoiNhanGiaoHang,b.MA as NguoiTraTien,'5'+cast(a.PK_SEQ as varchar(20)) as SoDonHang,  "+
							"			A.NGAYTRA  as NgayTaoDonHang,  "+
							"	'VND' as LoaiTienTea,1 as TyGia,REPLACE(A.NGAYTRA,'-', '') as NgayChungTu,  "+
							"	 A.NGAYTRA as NgayXacDinhGia,NULL AS HanThanhToan,a.GhiChu as LyDoTraHang,NULL as NgayHoaDonTraHang,  "+
							"		'5'+cast(a.PK_SEQ as varchar(20))  as HeaderID, CAST(a.pk_Seq as varchar(20)) +'__' + CAST(a.NPP_FK as varchar(20))  +'__'+ b.MA  + '__' +  "+ 
							"		b.TEN + '__'+ a.NGAYTRA  as FullDesc  "+
							"	from DONTRAHANG a  "+
							"  inner NHAPHANPHOI b on b.PK_SEQ=a.NPP_FK "+
							"  where a.TRANGTHAI=2  ";
			ResultSet	rs = db.get(query);

			System.out.println("[LoaiDonHang]"+ query );
			int  SoDong =0;
			while(rs.next())
			{

				String LoaiDonHang= rs.getString("LoaiDonHang");
				String ToChucBanHang= rs.getString("ToChucBanHang");
				String KenhBanHang= rs.getString("KenhBanHang");
				String NganhHang= rs.getString("NganhHang");
				String NguoiMuaHang= rs.getString("NguoiMuaHang");
				String NguoiNhanGiaoHang= rs.getString("NguoiNhanGiaoHang");
				String NguoiTraTien= rs.getString("NguoiTraTien");
				String SoDonHang= rs.getString("SoDonHang");
				Date NgayTaoDonHang= rs.getDate("NgayTaoDonHang");
				String LoaiTienTe= rs.getString("LoaiTienTe");
				String TyGia= rs.getString("TyGia");
				Date NgayChungTu= rs.getDate("NgayChungTu");
				Date NgayXacDinhGia= rs.getDate("NgayXacDinhGia");
				Date HanThanhToan= rs.getDate("HanThanhToan");
				String LyDoTraHang= rs.getString("LyDoTraHang");
				Date NgayHoaDonTraHang= rs.getDate("NgayHoaDonTraHang");
				String FullDesc= rs.getString("FullDesc");

				query=
						" select  '"+LoaiDonHang+"', '"+ToChucBanHang+"' ,'"+KenhBanHang+"','"+NganhHang+"' ,'"+NguoiMuaHang+"'," +
								"  '"+NguoiNhanGiaoHang+"',  "+
								"				'"+NguoiTraTien+"','"+SoDonHang+"', cast('"+NgayTaoDonHang+"' as DATE),'"+LoaiTienTe+"','"+TyGia+"',cast('"+NgayChungTu+"'  as DATE) ,  "+
								" 		cast('"+NgayXacDinhGia+"' as DATE)		,'"+HanThanhToan+"',N'"+LyDoTraHang+"',cast('"+NgayHoaDonTraHang+"' as DATE) ,'"+HeaderID+"','"+FullDesc+"'   ";
				System.out.println("[DMS_DonHang_Header]"+ query );
				query=
						"		insert into DMS_DonHang_Header(LoaiDonHang,ToChucBanHang,KenhBanHang,NganhHang,NguoiMuaHang,NguoiNhanGiaoHang,  "+
								"				NguoiTraTien,SoDonHang,NgayTaoDonHang,LoaiTienTe,TyGia,NgayChungTu,  "+
								" 				NgayXacDinhGia,HanThanhToan,LyDoTraHang,NgayHoaDonTraHang,HeaderID,FullDesc)  " +
								" select ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?								" ;
				SoDong=0;	
				SoDong=	SYNC.executeUpdate(query, LoaiDonHang,ToChucBanHang,KenhBanHang,NganhHang,NguoiMuaHang,NguoiNhanGiaoHang,  
						NguoiTraTien,SoDonHang,NgayTaoDonHang,LoaiTienTe,TyGia,NgayChungTu,  
						NgayXacDinhGia,HanThanhToan,LyDoTraHang,NgayHoaDonTraHang,HeaderID,FullDesc);
				if(SoDong!=1)
				{
					msg = "Không thể đẩy đơn hàng sang SAP!Vui lòng kiểm tra lại kết nối !"+query;
					SYNC.getConnection().rollback();
					db.getConnection().rollback();
					return msg;
				}
			}
			rs.close();

			query=
					"	select "+HeaderID+" as HeaderID ,'5'+ CAST(a.DONTRAHANG_FK AS VARCHAR(20)) as SoDonHang,   "+
							"	ROW_NUMBER() OVER(ORDER BY A.DONTRAHANG_FK DESC) AS 'stt' "+
							"	,b.MA as Material, d.DONVI as DonViBanHang,a.SOLUONG,NULL as NgayGiaoHang,NULL as IO_CTTangHang ,NULL as IO_CTCKTien ,NULL as ptChietKhau "+
							"	from DONTRAHANG_SP a inner join DMSDATA.dbo.sanpham b on b.pk_Seq=a.sanpham_fk "+	
							"		inner join DONTRAHANG c on c.PK_SEQ=a.DONTRAHANG_FK "+
							"			inner join DONVIDOLUONG d on d.PK_SEQ=b.DVDL_FK "+
							"	where c.TRANGTHAI=2 ";
			System.out.println("[DMS_DonHang_Header]"+ query);
			rs = db.get(query);
			int PoLine=0;
			while(rs.next())
			{
				PoLine++;
				HeaderID= rs.getString("HeaderID");
				String SoDonHang= rs.getString("SoDonHang");
				String Material= rs.getString("Material");
				String DonViBanHang= rs.getString("DonViBanHang");
				double SoLuong =rs.getDouble("SoLuong");
				String NgayGiaoHang =rs.getString("NgayGiaoHang");
				String IO_CTTangHang = rs.getString("IO_CTTangHang");
				String	IO_CTCKTien = rs.getString("IO_CTCKTien");
				double ptChietKhau =rs.getDouble("ptChietKhau");

				query=
						"insert into DMS_DonHang_Item(HeaderID,SoDonHang,PoLine,Material,DonViBanHang,SoLuong,NgayGiaoHang,IO_CTTangHang,IO_CTCKTien,ptChietKhau)" +
								" select  ? ,? , ?, ? ,?    ,?, ? ,? ,? ,? ";

				SoDong=0;	
				SoDong=	SYNC.executeUpdate(query, HeaderID,SoDonHang,PoLine,Material,DonViBanHang,SoLuong,NgayGiaoHang,IO_CTTangHang,IO_CTCKTien,ptChietKhau);
				System.out.println("[DMS_DonHang_Item]"+ query);
				if(SoDong!=1)
				{
					msg = "Lỗi chèn thông tin chi tiết đơn hàng !"+query;
					SYNC.getConnection().rollback();
					db.getConnection().rollback();
					return msg;
				}
			}
			rs.close();
			query = "update DonTraHang set SendingDate=dbo.GetLocalDate(DEFAULT),Header_FK='"+HeaderID+"',TrangThai=2 where pk_seq = '" + id + "' ";
			System.out.println("[ERP_Dondathang]"+ query);
			if(!db.update(query))
			{
				msg = "Lỗi cập nhật thông tin đơn hàng " + query;
				SYNC.getConnection().rollback();
				db.getConnection().rollback();
				return msg;
			}*/



			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);


			/*SYNC.getConnection().commit();
			SYNC.getConnection().setAutoCommit(true);*/
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		finally
		{
			if(db!=null)db.shutDown();
		}

		return "";
	}

	private String DeleteChuyenKho(String lsxId, String userId)
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			String	query="";
			query = " update kho set kho.AVAILABLE = kho.AVAILABLE + CT.tongxuat, " +
					" 			   kho.BOOKED = kho.BOOKED - CT.tongxuat " +
					" from " +
					" ( " +
					" 	select a.kho_fk as kho_fk, a.npp_fk,a.kbh_fk  , b.sanpham_fk, SUM(b.soluong) as tongxuat  " +
					" 	from DonTraHang a inner join DonTraHang_SP b on a.pk_seq = b.dontrahang_Fk " +
					" 	where dontrahang_Fk = '" + lsxId + "'  and a.TrangThai in (0, 1)  " +
					" 	group by a.kho_fk, a.npp_fk, a.kbh_fk, b.sanpham_fk " +
					" ) " +
					" CT inner join NHAPP_KHO kho on CT.kho_fk = kho.KHO_FK  " +
					" 	and CT.sanpham_fk = kho.SANPHAM_FK and CT.NPP_FK = kho.npp_fk and CT.KBH_FK = kho.kbh_fk ";
			System.out.println("---TANG KHO NGUOC LAI 2: " + query );
			if(db.updateReturnInt(query)<=0)
			{
				msg = "Không thể cập nhật NHAPP_KHO " + query;
				db.getConnection().rollback();
				return msg;
			}

			query = "update DonTraHang set trangthai = '3', nguoisua = '" + userId + "',Modified_Date=dbo.GetLocalDate(DEFAULT) where pk_seq = '" + lsxId + "' and trangthai in(0,1) ";
			if(db.updateReturnInt(query)!=1)
			{
				msg = "2.Khong the xoa: " + query;
				db.getConnection().rollback();
				return msg;
			}			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		finally
		{
			if(db!=null)db.shutDown();
		}

		return "";
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if (action == null)
		{
			action = "";
		}

		IDontrahangList obj = new DontrahangList();

		Utility util = new Utility();

		HttpSession session = request.getSession();
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"))); 
		obj.setUserId(userId);

		obj.setNppId(util.getIdNhapp(userId));

		if(action.equals("Tao moi"))
		{
			IDontrahang lsxBean = new Dontrahang();
			lsxBean.setUserId(userId);
			lsxBean.createRs();

			session.setAttribute("lsxBean", lsxBean);	    	
			session.setAttribute("kenhId", "");
			session.setAttribute("khoxuat", "" );
			session.setAttribute("nppId", lsxBean.getNppId());


			String nextJSP = "/AHF/pages/Center/DonTraHangNew.jsp";
			response.sendRedirect(nextJSP);
		}
		else
		{
			if(action.equals("view") || action.equals("next") || action.equals("prev"))
			{
				String search = getSearchQuery(request, obj);
				obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
				obj.setUserId(userId);
				obj.init(search);
				obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
				session.setAttribute("obj", obj);

				String nextJSP = "/AHF/pages/Center/DonTraHang.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				String search = getSearchQuery(request, obj);
				obj.init(search);
				obj.setUserId(userId);

				session.setAttribute("obj", obj);  	
				session.setAttribute("userId", userId);

				String nextJSP = "/AHF/pages/Center/DonTraHang.jsp";
				response.sendRedirect(nextJSP);

			}
		}
	}

	private String getSearchQuery(HttpServletRequest request, IDontrahangList obj)
	{
		String query = 
				"	select a.pk_Seq,b.MA as nppMa,b.TEN as nppTen,a.NGAYTRA,c.TEN as nguoiTao,d.TEN as nguoiSua,e.TEN as tructhuoc,a.TRANGTHAI,a.SOTIENBVAT,a.Modified_Date,a.created_date "+
						"		from DONTRAHANG a inner join NHAPHANPHOI b on b.PK_SEQ=a.NPP_FK "+
						"		inner join NHANVIEN c on c.PK_SEQ=a.NGUOITAO  "+
						"		inner join NHANVIEN d on d.PK_SEQ=a.NGUOISUA "+
						"		inner join NhaCungCap e on e.PK_SEQ=a.NCC_FK " +
						" where  1=1  ";

		String tungaySX = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
		if(tungaySX == null)
			tungaySX = "";
		obj.setTungayTao(tungaySX);

		String denngaySX = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
		if(denngaySX == null)
			denngaySX = "";
		obj.setDenngayTao(denngaySX);

		String sochungtu = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sochungtu"));
		if(sochungtu == null)
			sochungtu = "";
		obj.setSochungtu(sochungtu);

		String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);

		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
		if(nppId == null)
			nppId = "";
		obj.setNppId(nppId);

		String khId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khId"));
		if(khId == null)
			khId = "";
		obj.setKhId(khId);


		if(tungaySX.length() > 0)
			query += " and a.ngaytra >= '" + tungaySX + "'";

		if(denngaySX.length() > 0)
			query += " and a.ngaytra <= '" + denngaySX + "'";

		if(trangthai.length() > 0)
			query += " and a.TrangThai = '" + trangthai + "'";

		if(sochungtu.length() > 0)
			query += " and a.sohoadon like '%" + sochungtu + "%'";

		if(khId.length() > 0)
			query += " and  a.NPP_FK = '" + khId + "'";

		if(nppId.length() > 0)
			query += " and a.NPP_FK = '" + nppId + "'";

		System.out.print(query);
		return query;

	}

	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}
}
