package geso.dms.distributor.servlets.dontrahang;

import geso.dms.center.db.sql.Idbutils;
import geso.dms.center.util.Utility;

import geso.dms.distributor.beans.dontrahang.IDontrahang;
import geso.dms.distributor.beans.dontrahang.IDontrahangList;
import geso.dms.distributor.beans.dontrahang.imp.Dontrahang;
import geso.dms.distributor.beans.dontrahang.imp.DontrahangList;
import geso.dms.distributor.db.sql.dbutils;
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

public class DontrahangSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	public DontrahangSvl()
	{
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
			userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
		
		String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		if(view == null) view = "";
		
		String action = util.getAction(querystring);
		
		String lsxId = util.getId(querystring);
		obj = new DontrahangList();
		obj.setUserId(userId);
		obj.setView(view);
		if (action.equals("delete"))
		{
			String msg = this.DeleteChuyenKho(lsxId, userId);
			obj.setMsg(msg);
			
		} else 
		if (action.equals("HOdelete"))
		{
			String msg = this.HODeleteChuyenKho(lsxId, userId);
			obj.setMsg(msg);
			
		} else 	
		if (action.equals("chot"))
		{
			String msg = this.Chot(lsxId, userId);
			obj.setMsg(msg);
		} else if (action.equals("duyet"))
		{
			String msg = this.Duyet(lsxId, userId);
			obj.setMsg(msg);
		}else if (action.equals("UnChot"))
		{
			String msg = this.UnChot(lsxId, userId);
			obj.setMsg(msg);
		}
		
		obj.init("");
		session.setAttribute("obj", obj);
		
		String nextJSP = "/AHF/pages/Distributor/DonTraHang.jsp";
		response.sendRedirect(nextJSP);
	}
	
	private String UnChot(String lsxId, String userId)
  {
		dbutils db = new dbutils();

		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			Utility util = new Utility();
			msg = geso.dms.center.util.Utility.Check_Huy_NghiepVu_KhoaSo("dontrahang", lsxId, "NGAYTRA", db);
			if( msg.length() > 0)
			{
				db.getConnection().rollback();
				return msg;
			}
			
			String query="";
			/*query = " update kho set kho.SOLUONG = kho.SOLUONG + CT.tongxuat, " +
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
			}*/
			
			
		    query=	" 	select convert(varchar(10),a.ngaytra) as ngaytra,a.kho_fk as kho_fk, a.npp_fk,a.KBH_FK , b.sanpham_fk, SUM(b.soluong) as tongxuat  " +
					" 	from DonTraHang a inner join DonTraHang_SP b on a.pk_seq = b.dontrahang_Fk " +
					" 	where dontrahang_Fk = '" + lsxId + "' " +
					" 	group by a.kho_fk, a.npp_fk, a.kbh_fk, b.sanpham_fk,a.ngaytra " ;
		    ResultSet ckRs = db.get(query);
		    while(ckRs.next())
		    {
		    	String kho_fk=ckRs.getString("kho_fk");
				String nppId=ckRs.getString("npp_fk");	
				String kbh_fk=ckRs.getString("kbh_fk");
				String sanpham_fk=ckRs.getString("sanpham_fk");
				String ngaytra  =ckRs.getString("ngaytra");
				Double tongxuat=ckRs.getDouble("tongxuat");


				msg=util.Update_NPP_Kho_Sp(ngaytra,lsxId, "MỞ CHỐT DONTRAHANG", db, kho_fk, sanpham_fk, nppId, kbh_fk, tongxuat,  tongxuat, 0.0, 0.0);
				if(msg.length()>0)
				{
					db.getConnection().rollback();
					return msg;
				}
		    }
			//
		    query=	" 	select ngaynhapkho,convert(varchar(10),a.ngaytra) as ngaytra,a.kho_fk as kho_fk, a.npp_fk,a.KBH_FK , b.sanpham_fk, SUM(b.soluong) as tongxuat  " +
					" 	from DonTraHang a inner join DONTRAHANG_SP_ChiTiet b on a.pk_seq = b.dontrahang_Fk " +
					" 	where dontrahang_Fk = '" + lsxId + "' " +
					" 	group by a.kho_fk, a.npp_fk, a.kbh_fk, b.sanpham_fk,a.ngaytra,ngaynhapkho " ;
		    ResultSet ckRs1 = db.get(query);
		    while(ckRs1.next())
		    {
		    	String kho_fk=ckRs1.getString("kho_fk");
				String nppId=ckRs1.getString("npp_fk");	
				String kbh_fk=ckRs1.getString("kbh_fk");
				String sanpham_fk=ckRs1.getString("sanpham_fk");
				String ngaytra  =ckRs1.getString("ngaytra");
				String ngaynhapkho  =ckRs1.getString("ngaynhapkho");
				Double tongxuat=ckRs1.getDouble("tongxuat");


				msg = util.Update_NPP_Kho_Sp_Chitiet(ngaytra,  "nha book trả hang npp ct: "+lsxId ,  db, kho_fk, sanpham_fk, nppId, kbh_fk, ngaynhapkho, tongxuat ,tongxuat, 0, 0, 0);
				if(msg.length()>0)
				{
					db.getConnection().rollback();
					return msg;
				}
		    }
			
		    query = "delete  DONTRAHANG_SP_ChiTiet  where pk_seq = '" + lsxId + "'";
			if(db.updateReturnInt(query)!=1)
			{
				msg = "Lỗi xóa DONTRAHANG_SP_ChiTiet !"+query;
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

	private String Duyet(String id, String userId)
	{
		dbutils db = new dbutils();
		try
		{
			String msg = "";
			db.getConnection().setAutoCommit(false);
			
			msg = Utility.Check_Huy_NghiepVu_KhoaSo("dontrahang", id, "NGAYTRA", db);
			if( msg.length() > 0)
			{
				db.getConnection().rollback();
				return msg;
			}
			
			String	query = " update DonTraHang set trangthai = 2,ngay_duyet_HO=dbo.GetLocalDate(DEFAULT), nguoi_duyet_HO ="+userId+" " +
							" where pk_seq = '" + id + "' and trangthai in (1)  ";
			if(db.updateReturnInt(query)!=1)
			{
				msg = "Phiếu trả hàng này đã chốt rồi !"+query;
				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}
			msg = TruKhoNPP( id, db);
			if(msg.trim().length() > 0)
			{

				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			return "HO duỵệt thành công!";
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			db.shutDown();
			return "Exception: " + e.getMessage();
		}	
	}
	private String Chot(String id, String userId)
	{
		dbutils db = new dbutils();
		try
		{
			String msg = "";
			db.getConnection().setAutoCommit(false);
			Utility util=new Utility();
			msg = geso.dms.center.util.Utility.Check_Huy_NghiepVu_KhoaSo("dontrahang", id, "NGAYTRA", db);
			if( msg.length() > 0)
			{
				db.getConnection().rollback();
				return msg;
			}
			
			String	query = "update DonTraHang set trangthai = '1',Modified_Date=dbo.GetLocalDate(DEFAULT) where pk_seq = '" + id + "' and trangthai in (0)  ";
			if(db.updateReturnInt(query)!=1)
			{
				msg = "Phiếu trả hàng này đã chốt rồi !"+query;
				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}
			msg = BookKhoNPP( id, db);
			if(msg.trim().length() > 0)
			{

				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}
			query="\n  select COUNT(*) AS SL from (	 SELECT sum(soluong) as soluong,SANPHAM_FK FROM DONTRAHANG_SP "+
					"\n  	 where DONTRAHANG_FK in ( "+id+")  "+
					"\n  	 group by SANPHAM_FK "+
					"\n  	 ) as tong "+
					"\n  	 left join (	 SELECT sum(soluong) as soluong,SANPHAM_FK FROM DONTRAHANG_SP_ChiTiet "+
					"\n  	 where DONTRAHANG_FK in (  "+id+")  "+
					"\n   group by SANPHAM_FK "+
					"\n   ) as ct on tong.SANPHAM_FK=ct.SANPHAM_FK "+
					"\n   and TONG.soluonG=CT.soluong "+
					"\n   WHERE ISNULL(TONG.SOLUONG,0)<>ISNULL(CT.SOLUONG,0)		 ";
			ResultSet checkRs=db.get(query);
			System.out.println(query);
			checkRs.next();
			if(checkRs.getInt("sl")>0){
				db.getConnection().rollback();
				db.shutDown();
				return "Lệch bảng tổng và CT";
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			return "NPP chốt thành công!";
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
	
		
		
	}
	public static String Nha_Book_NPP(String id,Idbutils db)
	{
		String query = "";
		try
		{
			Utility utiCen = new Utility();
			boolean datrukho = false;
			query = "\n select t.NPP_FK,t.KBH_FK,t.KHO_FK,ct.SANPHAM_FK, ct.ngaynhapkho, ct.SOLUONG   " +
					"\n from DONTRAHANG_SP_ChiTiet ct inner join DONTRAHANG t on ct.DONTRAHANG_FK = t.PK_SEQ " +
					"\n where ct.DONTRAHANG_FK = " + id ;

			ResultSet rsKHO = db.get(query);
			while(rsKHO.next())
			{
				String khoId = rsKHO.getString("KHO_FK");
				String spId = rsKHO.getString("SANPHAM_FK");
				String nppId = rsKHO.getString("NPP_FK");
				String kbhId = rsKHO.getString("KBH_FK");
				String ngaynhapkho = rsKHO.getString("ngaynhapkho");
				double soluongXUAT = rsKHO.getDouble("SOLUONG");
				String msg1 = utiCen.Update_NPP_Kho_Sp_Chitiet("",  " HO nhả book kho trả hang npp ct: "+id 
						,  db, khoId, spId, nppId, kbhId, ngaynhapkho,0, (-1)*soluongXUAT, soluongXUAT , 0, 0);
				if (msg1.length() > 0) 
				{
					return msg1;
				}
				
				msg1 = utiCen.Update_NPP_Kho_Sp("",id,  " HO nhả book trả hang npp tổng: "+id, db, khoId, spId, nppId, kbhId
						,0, (-1)*soluongXUAT, soluongXUAT, 0.0);
				if(msg1.length()>0)
				{
					return msg1;
				}	
				datrukho = true;
				
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
	public String TruKhoNPP(String id,Idbutils db)
	{
		String query = "";
		try
		{
			Utility utiCen = new Utility();
			boolean datrukho = false;
			query = "\n select t.NPP_FK,t.KBH_FK,t.KHO_FK,ct.SANPHAM_FK, ct.ngaynhapkho, ct.SOLUONG   " +
					"\n from DONTRAHANG_SP_ChiTiet ct inner join DONTRAHANG t on ct.DONTRAHANG_FK = t.PK_SEQ " +
					"\n where ct.DONTRAHANG_FK = " + id ;
			System.out.println(" tru kho duyet tra hang HO:"+ query);
			ResultSet rsKHO = db.get(query);
			while(rsKHO.next())
			{
				String khoId = rsKHO.getString("KHO_FK");
				String spId = rsKHO.getString("SANPHAM_FK");
				String nppId = rsKHO.getString("NPP_FK");
				String kbhId = rsKHO.getString("KBH_FK");
				String ngaynhapkho = rsKHO.getString("ngaynhapkho");
				double soluongXUAT = rsKHO.getDouble("SOLUONG");
				String msg1 = utiCen.Update_NPP_Kho_Sp_Chitiet("",  " HO tru kho trả hang npp ct: "+id 
						,  db, khoId, spId, nppId, kbhId, ngaynhapkho,(-1)*soluongXUAT, (-1)*soluongXUAT, 0 , 0, 0);
				if (msg1.length() > 0) 
				{
					return msg1;
				}
				
				msg1 = utiCen.Update_NPP_Kho_Sp("",id,  " HO trừ trả hang npp tổng: "+id, db, khoId, spId, nppId, kbhId
						,(-1)*soluongXUAT, (-1)*soluongXUAT, 0 , 0.0);
				if(msg1.length()>0)
				{
					return msg1;
				}	
				datrukho = true;
				
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
	public String BookKhoNPP(String id,Idbutils db)
	{
		String query = "";
		try
		{
			
			query = "delete DONTRAHANG_SP_Chitiet where DONTRAHANG_FK = "+id+"";
		         	if(!db.update(query))
					{
						return " Lỗi:"+ query;							
					}
					
			boolean datrukho = false;
			query = "\n select c.NPP_FK nppId,c.kho_fk as khoId, case when (select dungchungkenh from nhaphanphoi where pk_seq = c.npp_fk) = 1 then 100025 else c.kbh_fk end kbhId, b.pk_seq as spId, b.ten as spTEN, sum(a.soluong) as soluong " +
			"\n from DONTRAHANG_SP a inner join sanpham b on a.sanpham_fk = b.pk_seq inner join DONTRAHANG c on a.DONTRAHANG_FK = c.pk_seq  " +
			"\n where c.trangthai not in (2,3) and a.DONTRAHANG_FK in ( " + id + " ) " +
			"\n group by c.kho_fk, c.kbh_fk, b.pk_seq, b.ten,c.npp_fk,c.kbh_fk" ;

			ResultSet rsKHO = db.get(query);
			while(rsKHO.next())
			{
				String nppId  = rsKHO.getString("nppId");
				String khoId = rsKHO.getString("khoId");
				String kbhId = rsKHO.getString("kbhId");
				String spId = rsKHO.getString("spId");
				String spTEN = rsKHO.getString("spTEN");
				double soluong = rsKHO.getDouble("soluong");
				

				//TU DE XUAT LO --> KHO CHI TIET THI VAN TRU SO LUONG + AVAI
				query =  "\n SELECT  KHO.AVAILABLE, KHO.ngaynhapkho " + 
				"\n FROM NHAPP_KHO_CHITIET KHO " +
				"\n inner join DONTRAHANG dh on dh.pk_seq = " + id + 
				"\n inner join Nhaphanphoi npp on npp.pk_seq = kho.npp_fk " + 
				"\n INNER JOIN SANPHAM SP ON SP.PK_SEQ = KHO.SANPHAM_FK " + 
				"\n WHERE kho.AVAILABLE > 0 and KHO.KHO_FK = " + khoId + 
				"\n AND KHO.NPP_FK = " + nppId +
				"\n	and KHO.KBH_FK = case npp.DUNGCHUNGKENH  when 1 then 100025 else dh.kbh_fk end " + 
				"\n 	and SP.pk_seq = " + spId + " and ngaynhapkho <= dh.ngaytra " +
				"\n ORDER BY  ngaynhapkho asc  " ;
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
					
						query = "insert into DONTRAHANG_SP_Chitiet(DONTRAHANG_FK,SANPHAM_FK,SOLUONG,DONVI,DONGIA,ptVat,ngaynhapkho) " +
						" select  DONTRAHANG_FK,SANPHAM_FK,"+soluongXUAT+",DONVI,DONGIA,ptVat, '"+ngaynhapkho+"' from DONTRAHANG_SP where DONTRAHANG_FK = "+id+" and sanpham_fk =" + spId;
						if (db.updateReturnInt(query) != 1)
						{
							return " Lỗi:"+ query;							
						}
						
						geso.dms.center.util.Utility utiCen = new geso.dms.center.util.Utility();
						String msg1 = utiCen.Update_NPP_Kho_Sp_Chitiet("",  " book trả hang npp ct: "+id ,  db, khoId, spId, nppId, kbhId, ngaynhapkho, 0 ,soluongXUAT, (-1)*soluongXUAT, 0, 0);
						if (msg1.length() > 0) 
						{
							return msg1;
						}
						
						msg1 = utiCen.Update_NPP_Kho_Sp("",id,  " book trả hang npp tổng: "+id, db, khoId, spId, nppId, kbhId,0, soluongXUAT, (-1)*soluongXUAT, 0.0);
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

				//if (tongluongxuatCT != soluong)
				if (tongluongxuatCT != soluong )
				{
					String addStr = "";
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
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			
			db.getConnection().setAutoCommit(false);

		
			msg = geso.dms.center.util.Utility.Check_Huy_NghiepVu_KhoaSo("dontrahang", lsxId, "NGAYTRA", db);
			if( msg.length() > 0)
			{
				db.getConnection().rollback();
				return msg;
			}
			
			String query = "update DonTraHang set trangthai = '3', nguoisua = '" + userId + "',Modified_Date=dbo.GetLocalDate(DEFAULT) where pk_seq = '" + lsxId + "' and trangthai in(0,1) ";
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
	
	private String HODeleteChuyenKho(String lsxId, String userId)
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);

			
			msg = Utility.Check_Huy_NghiepVu_KhoaSo("dontrahang", lsxId, "NGAYTRA", db);
			if( msg.length() > 0)
			{
				db.getConnection().rollback();
				return msg;
			}
			
			String query =  "update DonTraHang set trangthai = '3',nguoi_duyet_HO = "+userId+",ngay_duyet_HO = dbo.GetLocalDate(DEFAULT)" +
							", nguoisua = '" + userId + "',Modified_Date=dbo.GetLocalDate(DEFAULT) where pk_seq = '" + lsxId + "' and trangthai in(1) ";
			if(db.updateReturnInt(query)!=1)
			{
				msg = "2.Khong the xoa: " + query;
				db.getConnection().rollback();
				return msg;
			}	
			
			msg = Nha_Book_NPP( lsxId, db);
			if(msg.trim().length() > 0)
			{

				db.getConnection().rollback();
				db.shutDown();
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
		String view = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		if(view == null) view = "";
	    Utility util = new Utility();
	    
	    HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"))); 
	    obj.setUserId(userId);
	    obj.setView(view);
	    obj.setNppId(util.getIdNhapp(userId));
	    
	    if(action.equals("Tao moi"))
	    {
	    	IDontrahang lsxBean = new Dontrahang();
	    	lsxBean.setView(view);
	    	lsxBean.setUserId(userId);
	    	lsxBean.createRs();
	    	lsxBean.setNgayyeucau(Utility.getNgayHienTai());
	    	
	    	session.setAttribute("ngaychungtu", lsxBean.getNgayyeucau());
	    	session.setAttribute("lsxBean", lsxBean);	    	
	    	session.setAttribute("kenhId", "");
	    	session.setAttribute("khoxuat", "" );
	    	session.setAttribute("khoxuat", "" );
	    	session.setAttribute("nppId", lsxBean.getNppId());
    		
	    	
    		String nextJSP = "/AHF/pages/Distributor/DonTraHangNew.jsp";
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
		    	
		    	String nextJSP = "/AHF/pages/Distributor/DonTraHang.jsp";
				response.sendRedirect(nextJSP);
		    }
	    	else
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.init(search);
				obj.setUserId(userId);
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		String nextJSP = "/AHF/pages/Distributor/DonTraHang.jsp";
	    		response.sendRedirect(nextJSP);
	    		
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IDontrahangList obj)
	{
		Utility util = new Utility();
		
		String query = 
						"	select isnull((select round(sum(soluong*dongia*(1+ptvat/100)),0)  from dontrahang_sp where dontrahang_fk=a.pk_seq),0) as tienavat,isnull(a.ghichu,'') as ghichu,a.pk_Seq,b.MA as nppMa,b.TEN as nppTen,a.NGAYTRA,c.TEN as nguoiTao,d.TEN as nguoiSua,e.TEN as tructhuoc,a.TRANGTHAI,a.SOTIENBVAT,a.Modified_Date,a.created_date "+
						"		from DONTRAHANG a inner join NHAPHANPHOI b on b.PK_SEQ=a.NPP_FK "+
						"		inner join NHANVIEN c on c.PK_SEQ=a.NGUOITAO  "+
						"		inner join NHANVIEN d on d.PK_SEQ=a.NGUOISUA "+
						"		inner join NhaCungCap e on e.PK_SEQ=a.ncc_fk " +
						" where 1=1  ";
		if(obj.getView().equals("TT")){
			query+= " and a.npp_fk in "+util.quyen_npp(obj.getUserId())+"   ";
		}else{
			query+= " and a.npp_fk='"+obj.getNppId()+"' ";
		}
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
		if(nppId == null||nppId.equals("null")){ 
			nppId = ""; 
			}
		obj.setNppId(nppId);
		
		String nguoitaoId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nguoitaoId"));
		if(nguoitaoId == null||nppId.equals("null")){ 
			nguoitaoId = ""; 
			}
		obj.setNguoitaoId(nguoitaoId);
		
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
			query += " and a.pk_seq like '%" + sochungtu + "%'";
		
		if(khId.length() > 0)
			query += " and  a.tructhuoc_fk = '" + khId + "'";
		
		if(nppId.trim().length() > 0)
			query += " and a.NPP_FK = '" + nppId + "'";
		if(nguoitaoId.trim().length() > 0)
			query += " and c.pk_seq = '" + nguoitaoId + "'";
		
		System.out.print("sql loc: "+query);
		return query;
		
	}
		
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
}