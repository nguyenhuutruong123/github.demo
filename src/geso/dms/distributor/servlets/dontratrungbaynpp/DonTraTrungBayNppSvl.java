package geso.dms.distributor.servlets.dontratrungbaynpp;

import geso.dms.distributor.db.sql.dbutils;
import geso.dms.center.db.sql.Idbutils;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.dontratrungbaynpp.IDonTraTrungBayNpp;
import geso.dms.distributor.beans.dontratrungbaynpp.IDonTraTrungBayNppList;
import geso.dms.distributor.beans.dontratrungbaynpp.imp.DonTraTrungBayNpp;
import geso.dms.distributor.beans.dontratrungbaynpp.imp.DonTraTrungBayNppList;

import java.io.IOException;
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

public class DonTraTrungBayNppSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public DonTraTrungBayNppSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IDonTraTrungBayNppList obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();	    

		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		if (userId.length()==0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

		String action = util.getAction(querystring);

		String lsxId = util.getId(querystring);
		obj = new DonTraTrungBayNppList();
		obj.setUserId(userId);

		if (action.equals("delete") )
		{	
			String msg = this.DeleteChuyenKho(lsxId, userId);
			obj.setMsg(msg);
		}
		else if(action.equals("chot"))
		{
			String msg = this.Chot(lsxId, userId);
			obj.setMsg(msg);
		}

		obj.init("");
		session.setAttribute("obj", obj);

		String nextJSP = "/AHF/pages/Distributor/DonTraTrungBayNpp.jsp";
		response.sendRedirect(nextJSP);
	}

	private String Chot(String id, String userId)
	{
		dbutils db = new dbutils();
		String msg = "";

		Utility util = new Utility();

		msg= util.Check_Huy_NghiepVu_KhoaSo("DonTraTrungBayNPP", id, "NgayTra", db);
		if(msg.length()>0)
			return msg;
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query  = " update DonTraTrungBayNPP set nguoisua = "+userId+" where trangthai = 0 and pk_seq = "+ id;	
			if(db.updateReturnInt(query)<= 0)
			{
				db.getConnection().rollback();
				return " Lỗi: "+ query;
			}

			msg =TruKhoDonHang(db, id, userId);
			if (msg.length() > 0) {
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return msg;
			}
			
			query  = " update DonTraTrungBayNPP set trangthai = 1 where trangthai = 0 and pk_seq = "+ id;	
			if(db.updateReturnInt(query)<= 0)
			{
				db.getConnection().rollback();
				return " Lỗi: "+ query;
			}

			db.getConnection().commit();
			db.shutDown();
		}
		catch (Exception e) 
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		finally
		{
			db.shutDown();
		}
		return "";
	}
	
	public String TruKhoDonHang(Idbutils db ,String dhId, String userId)
	{
		String query = "";
		try
		{
			
			boolean datrukho = false;
			query = "\n select 0 trakmID,0 CTKMID,1 loai, c.npp_fk ,c.kho_fk as khoId" +
			"\n		,  c.kbh_fk as kbhId" +
			"\n		, b.pk_seq as spId, b.ten as spTEN, sum(a.soluong) as soluong " +
			"\n from DonTraTrungBayNPP_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq" +
			"\n inner join DonTraTrungBayNPP c on a.DonTraTrungBayNPP_fk = c.pk_seq   " +
			"\n where c.trangthai != 2 and a.DonTraTrungBayNPP_fk in ( " + dhId + " )  " +
			"\n group by c.kho_fk, c.kbh_fk, b.pk_seq, b.ten,c.npp_fk,c.kbh_fk,c.npp_fk " ;
		
			ResultSet rsKHO = db.get(query);
			while(rsKHO.next())
			{
				String nppId = rsKHO.getString("npp_fk");
				String khoId = rsKHO.getString("khoId");
				String kbhId = rsKHO.getString("kbhId");
				String spId = rsKHO.getString("spId");
				String trakmID = rsKHO.getString("trakmID");
				String CTKMID = rsKHO.getString("CTKMID");
				String spTEN = rsKHO.getString("spTEN");
				double soluong = rsKHO.getDouble("soluong");
				int isnhapkhau = 1;
				String loai = rsKHO.getString("loai");

				//TU DE XUAT LO --> KHO CHI TIET THI VAN TRU SO LUONG + AVAI

				query =  	 "\n SELECT AVAILABLE, KHO.ngaynhapkho  " + 
				"\n FROM NHAPP_KHO_CHITIET  KHO " +
				"\n inner join DonTraTrungBayNPP dh on dh.pk_seq = "+dhId+"  " +
				"\n inner join Nhaphanphoi npp on npp.pk_seq = kho.npp_fk " + 
				"\n INNER JOIN SANPHAM SP ON SP.PK_SEQ=KHO.SANPHAM_FK  " + 
				"\n WHERE  KHO.KHO_FK = "+khoId+"  " +
				"\n AND KHO.NPP_FK = "+nppId+" " +
				"\n	and KHO.KBH_FK = case npp.DUNGCHUNGKENH  when 1 then 100025 else dh.kbh_fk end  " + 
				"\n 	and SP.pk_seq="+spId+"  and ngaynhapkho <=dh.ngaytra " +
				"\n ORDER BY   ngaynhapkho asc,AVAILABLE asc  " ;
					System.out.println(" kho chitiet = "+ query);
				ResultSet rs = db.get(query);

				String NgayHetHan = "";
				double tongluongxuatCT = 0;  //PHAI BAT BUOC TONG LUONG XUAT O KHO CHI TIET PHAI BANG TONG LUONG XUAT O KHO TONG

				double totalLUONG = 0;
				boolean exit = false;
				while(rs.next())
				{
					
					String ngaynhapkho = rs.getString("ngaynhapkho");
					double avai = rs.getDouble("AVAILABLE"); 
					totalLUONG += avai;

					double soluongXUAT = 0;

					if (totalLUONG <= soluong && totalLUONG > 0)
					{
						soluongXUAT = avai;
					}
					else
					{
						soluongXUAT = soluong - (totalLUONG - avai);
						exit = true;
					}
					if (soluongXUAT > 0)
					{
						

						query = " insert into  DonTraTrungBayNPP_sanpham_chitiet (DonTraTrungBayNPP_fk,sanpham_fk,ngaynhapkho,soluong)" +
							" select " + dhId + ", '" + spId + "','" + ngaynhapkho + "','" + soluongXUAT + "' ";
						if (db.updateReturnInt(query) != 1)
						{
							return " Lỗi:"+ query;
							
						}
						geso.dms.center.util.Utility utiCen = new geso.dms.center.util.Utility();
						String msg1=utiCen.Update_NPP_Kho_Sp_Chitiet("", "chốt trả trừng bày: "+dhId ,  db, khoId, spId, nppId, kbhId, ngaynhapkho, (-1)*soluongXUAT,0, (-1)*soluongXUAT, 0, 0);
						if (msg1.length()> 0) 
						{
							return msg1;
						}
						msg1=utiCen.Update_NPP_Kho_Sp("",dhId, "chot tong tra trung báy", db, khoId, spId, nppId, kbhId, (-1)*soluongXUAT, 0, (-1)*soluongXUAT, 0.0);
						if(msg1.length()>0)
						{
							return msg1;
						}		
						datrukho = true;	
						tongluongxuatCT += soluongXUAT;
						if (exit)  //DA XUAT DU
						{
							break;
						}
					}
				}

				if (tongluongxuatCT != soluong )
				{
					String addStr = "";
					if (loai.equals("1"))
						addStr =  " Sp hàng bán ("+spTEN+")";
					else
						addStr =  " Sp KM ("+spTEN+")";

					return addStr +": số lượng đề xuất trong lô chi tiết theo ngày không còn đủ, vui lòng kiểm tra xuất nhập tồn theo lô để biết thêm chi tiết";
					


				}


			}
			if(!datrukho)
				return "Không trừ được kho";
			return "";

		}
		catch (Exception e) {
			e.printStackTrace();
			return "Exception:"+ e.getMessage();
		}
	}
	
	private String DeleteChuyenKho(String lsxId, String userId)
	{ 
		Utility util = new Utility();
		dbutils db = new dbutils();
		String msg = "";
		try
		{


			db.getConnection().setAutoCommit(false);

			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
		}
		catch (Exception e) 
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			db.shutDown();
			e.printStackTrace();
			return "Exception: " + e.getMessage();
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

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if (action == null)
		{
			action = "";
		}

		IDonTraTrungBayNppList obj = new DonTraTrungBayNppList();

		Utility util = new Utility();

		HttpSession session = request.getSession();
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"))); 

		if(action.equals("Tao moi"))
		{
			IDonTraTrungBayNpp lsxBean = new DonTraTrungBayNpp();
			lsxBean.setUserId(userId);
			lsxBean.createRs();
			session.setAttribute("lsxBean", lsxBean);	    	
			session.setAttribute("kenhId", "");
			session.setAttribute("khoxuat", "" );
			session.setAttribute("nppId", lsxBean.getNppId());
			String nextJSP = "/AHF/pages/Distributor/DonTraTrungBayNppNew.jsp";
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

				String nextJSP = "/AHF/pages/Distributor/DonTraTrungBayNpp.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				obj.setUserId(userId);
				String search = getSearchQuery(request, obj);
				obj.init(search);

				session.setAttribute("obj", obj);  	
				session.setAttribute("userId", userId);

				String nextJSP = "/AHF/pages/Distributor/DonTraTrungBayNpp.jsp";
				response.sendRedirect(nextJSP);

			}
		}
	}

	private String getSearchQuery(HttpServletRequest request, IDonTraTrungBayNppList obj)
	{
		/*String query =  "select a.sonetId,a.PK_SEQ, a.trangthai, a.ngaychuyen, a.ghichu as lydo, NV.TEN as nguoitao, b.ten as khoxuat, c.ten as nppTEN, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua   " +
		"from ERP_CHUYENKHONPP a inner join KHO b on a.khoxuat_fk = b.pk_seq inner join NHAPHANPHOI c on a.npp_dat_fk = c.pk_seq  " +
		"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
		"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.pk_seq > 0 ";*/

		String query = "	select  isnull(a.donhang_fk,0)donhangId ,a.sonetId,a.PK_SEQ, a.trangthai, a.ngaytra, a.ghichu as lydo, NV.TEN as nguoitao, b.ten as khoxuat,isnull(a.so,'') as so,   "+
		"			npp.ten as nppTEN, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua,isnull(kh.maFAST,isnull(npp.mafast,'')) as khMA,ISNULL(kh.ten,isnull(npp.ten,'')) as khTEN,kbh.TEN  as kbhTEN,a.SoHoaDon,a.Seri  "+				
		"		from Erp_HangTraLaiNpp a inner join ERP_khoTT b on a.kho_fk = b.pk_seq       "+
		"			inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ      "+
		"			inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ  "+
		"			left join NhaPhanPhoi npp on npp.PK_SEQ=a.npptra_fk   "+
		"			left join KHACHHANG kh on kh.PK_SEQ = a.khachhang_fk "+
		"			inner join KENHBANHANG kbh on kbh.PK_SEQ=a.kbh_fk   ";
		
		String tungaySX = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
		if(tungaySX == null)
			tungaySX = "";
		obj.setTungayTao(tungaySX);

		String denngaySX = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
		if(denngaySX == null)
			denngaySX = "";
		obj.setDenngayTao(denngaySX);

		String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);

		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
		if(nppId == null)
			nppId = "";
		obj.setNppId(nppId);
		
		//Tim kiem theo makh, tenkh
		/*String makh = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("makh"));
		if (makh == null)
			makh="";
		obj.setMaKh(makh);
		System.out.println("MaKH: "+makh);
		
		if(makh.length() > 0)
			query += "and isnull(npp.mafast,kh.maFAST) like N'%" + makh + "%'";
		*/
		
		String tenkh = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tenkh"));
		if (tenkh == null)
			tenkh="";
		obj.setTenKh(tenkh);
		
		if(tenkh.length() > 0)
			query += "and isnull(kh.mafast, npp.mafast) = '" + tenkh + "'";
		System.out.println("Ten KH: "+tenkh);
		System.out.println("Query search: "+query);
		//.
	
		if(tungaySX.length() > 0)
			query += " and a.ngaytra >= '" + tungaySX + "'";

		if(denngaySX.length() > 0)
			query += " and a.ngaytra <= '" + denngaySX + "'";

		if(trangthai.length() > 0)
			query += " and a.TrangThai = '" + trangthai + "'";

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
