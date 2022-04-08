package geso.dms.center.beans.dontrahang.imp;

import geso.dms.center.beans.dontrahang.IDontrahang;
import geso.dms.center.db.sql.db_Sync;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.OracleConnUtils;
import geso.dms.center.util.Utility;

import java.io.Serializable;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import geso.dms.distributor.util.*;

import javax.servlet.http.HttpServletRequest;

public class Dontrahang implements IDontrahang, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4877175563344609017L;
	String userId;
	String id;

	String ngayyeucau;
	String ghichu;

	String msg;
	String trangthai;

	String khoXuatId;
	ResultSet khoXuatRs;

	String khoNhanId;
	ResultSet khoNhanRs;

	String khId;
	ResultSet khRs;
	ResultSet dvtRs;

	String kbhId;
	ResultSet kbhRs;

	String lenhdieudong, lddcua, lddveviec, ngaylenhdieudong, sohopdong,ngayhopdong, nguoivanchuyen, ptvanchuyen;

	ResultSet spRs;


	String[] spId;
	String[] spMa;
	String[] spTen;
	String[] spDonvi;
	String[] spSoluong;
	String[] spGianhap;
	String[] spSolo;
	String[] spTonkho;
	String[] spBooked;
	String[] spNgaysanxuat;
	String[] spNgayhethan;

	String nppId;
	String nppTen;
	String sitecode;
	String sochungtu;
	String dungchung;
	Hashtable<String, String> sanpham_soluong;

	dbutils db;
	Utility util;

	public Dontrahang()
	{
		this.id = "";
		this.ngayyeucau = getDateTime();
		this.ghichu = "";
		this.khoNhanId = "";
		this.khoXuatId = "";
		this.khId = "";
		this.kbhId = "";
		this.msg = "";
		this.trangthai = "0";

		this.lenhdieudong="";
		this.lddcua="";
		this.lddveviec="";
		this.ngaylenhdieudong="";
		this.sohopdong="";
		this.ngayhopdong="";
		this.nguoivanchuyen="";
		this.ptvanchuyen="";

		this.sanpham_soluong = new Hashtable<String, String>();

		this.sochungtu ="";
		this.dungchung = "";
		this.db = new dbutils();
		this.util = new Utility();
	}

	public Dontrahang(String id)
	{
		this.id = id;
		this.ngayyeucau = getDateTime();
		this.ghichu = "";
		this.khoNhanId = "";
		this.khoXuatId = "";
		this.khId = "";
		this.kbhId = "";
		this.msg = "";
		this.trangthai = "0";

		this.lenhdieudong="";
		this.lddcua="";
		this.lddveviec="";
		this.ngaylenhdieudong="";
		this.sohopdong="";
		this.ngayhopdong="";
		this.nguoivanchuyen="";
		this.ptvanchuyen="";

		this.sanpham_soluong = new Hashtable<String, String>();

		this.sochungtu ="";
		this.dungchung = "";
		this.db = new dbutils();
		this.util = new Utility();
	}

	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}

	public String getId() 
	{
		return this.id;
	}

	public void setId(String Id) 
	{
		this.id = Id;
	}

	public String getNgayyeucau() 
	{
		return this.ngayyeucau;
	}

	public void setNgayyeucau(String ngayyeucau) 
	{
		this.ngayyeucau = ngayyeucau;
	}

	public String getKhoNhapId()
	{
		return this.khoNhanId;
	}

	public void setKhoNhapId(String khonhaptt) 
	{
		this.khoNhanId = khonhaptt;
	}

	public ResultSet getKhoNhapRs() 
	{
		return this.khoNhanRs;
	}

	public void setKhoNHapRs(ResultSet khonhapRs) 
	{
		this.khoNhanRs = khonhapRs;
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}


	public boolean createNK() 
	{
		if(this.ngayyeucau.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ngày chuyển";
			return false;
		}

		if( this.khoXuatId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho xuất";
			return false;
		}

		if( this.khId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đối tượng nhận";
			return false;
		}

		if( this.kbhId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kênh bán hàng";
			return false;
		}


		boolean coSP = false;
		for(int i = 0; i < spMa.length; i++)
		{
			if(spMa[i].trim().length() > 0 && spSoluong[i].trim().length() > 0  )
			{
				if( spSoluong[i].trim().length() > 0 )
				{
					coSP = true;
				}
			}
		}

		if(!coSP)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm xuất";
			return false;
		}

		//CHECK TRUNG MA 
		for(int i = 0; i < spMa.length - 1; i++)
		{
			for(int j = i + 1; j < spMa.length; j++)
			{
				if(spMa[i].trim().length() > 0 && spMa[j].trim().length() > 0 )
				{
					if( spMa[i].trim().equals(spMa[j].trim()) )
					{
						this.msg = "Sản phẩm ( " + spTen[i] + " )  đã bị trùng.";
						return false;
					}
				}
			}
		}	

		try
		{

			db.getConnection().setAutoCommit(false);
			String query = 
					"	INSERT INTO [DONTRAHANG]([NGAYTRA],[NGUOITAO],[NGUOISUA],[TRANGTHAI],[NPP_FK],[NCC_FK],[VAT],[SOTIENAVAT],[SOTIENBVAT],[KBH_FK],[KHO_FK],SoChungTu,SoHoaDon,KyHieu,GhiChu)" +
							"select 	'"+this.ngayyeucau+"','"+this.userId+"','"+this.userId+"',0,'"+this.nppId+"','"+this.khId+"',0,0,0,'"+this.kbhId+"','"+this.khoXuatId+"','"+this.sochungtu+"','"+this.sochungtu+"','',N'"+this.ghichu+"' ";

			System.out.println("1.Insert CK: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới DONTRAHANG " + query;
				db.getConnection().rollback();
				return false;
			}

			query = "select SCOPE_IDENTITY() as hdId";
			ResultSet rs = db.get(query);
			rs.next();
			this.id = rs.getString("hdId");
			rs.close();

			for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0 && spSoluong[i].trim().length() > 0 )
				{
					query =    "select sp.ten, dv.donvi, case when sp.dvdl_fk != dv.pk_seq   " +
							"	then ISNULL( ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = sp.PK_SEQ and DVDL1_FK = sp.DVDL_FK and DVDL2_FK = dv.pk_seq ), -1 ) else 1 end as quycach   "  +
							"from SANPHAM sp, DONVIDOLUONG dv " +
							"where sp.MA = '" + spMa[i].trim() + "' and dv.donvi = N'" + spDonvi[i].trim() + "'   ";

					System.out.println("-----CHECK QUY CACH: " + query );
					rs = db.get(query);
					if(rs.next())
					{
						if(rs.getDouble("quycach") <= 0)
						{
							this.msg = "Sản phẩm ( " + rs.getString("ten") + " ) với đơn vị đặt ( " + rs.getString("donvi") + " ) chưa thiết lập quy cách trong DLN ";
							rs.close();
							db.getConnection().rollback();
							return false;
						}
					}
					rs.close();

					query = "insert into DONTRAHANG_SP(DONTRAHANG_FK,SANPHAM_FK,SOLUONG,DONVI,DONGIA,ptVat,scheme) " +
							"select '"+this.id+"', pk_seq, '" + spSoluong[i].replaceAll(",", "") + "',N'"+spDonvi[i]+"','"+spGianhap[i].replaceAll(",", "")+"','"+spVat[i]+"','' " +
							"from SANPHAM where MA = '" + spMa[i] + "' ";
					System.out.println("1.2.Insert YCCK - SP - CT: " + query);
					if(db.updateReturnInt(query)!=1)
					{
						this.msg = "Khong the tao moi DONTRAHANG_SP: " + query;
						db.getConnection().rollback();
						return false;
					}

					query = "Update NHAPP_KHO set booked = booked + '" + spSoluong[i].replaceAll(",", "") + "', AVAILABLE = AVAILABLE - '" + spSoluong[i].replaceAll(",", "") + "' " +
							"where KHO_FK = '" + this.khoXuatId + "' and NPP_FK = '" + this.nppId + "' and KBH_FK = '" + this.kbhId + "' and SANPHAM_FK = ( select pk_seq from SANPHAM where MA = '" + spMa[i] + "' )  ";
					System.out.println("---CAP NHAT BOOKED KHO TONG: " + query );
					if(db.updateReturnInt(query)!=1)
					{
						this.msg = "Khong the cap nhat NHAPP_KHO: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}

			query="update dontrahang_sp set sotienBVAT=soluong*DonGia,VAT=soluong*DonGia*ptvat/100,SOTIENAVAT=soluong*DonGia*(1+ptvat/100) WHERE dontrahang_fk='"+this.id+"'";
			if(db.updateReturnInt(query)!=1)
			{
				this.msg = "Khong the cap nhat NHAPP_KHO: " + query;
				db.getConnection().rollback();
				return false;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			e.printStackTrace();
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		return true;
	}

	public boolean updateNK(String ischot) 
	{
		try
		{
			db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
			db.update("SET LOCK_TIMEOUT 60000;");
			this.db.getConnection().setAutoCommit(false);
			
			String query = "";
			query =
					"		select  SoChungTu_ERP as SoChungTu, so as so_number  "+
							"	from DonTraHang  " +
							" where pk_Seq = '"+this.id+"'   ";

			System.out.println("[SoCT]"+query);

			ResultSet rs = db.get(query);
			String chungtu ="", so = "";
			if(rs.next())
			{
				chungtu = rs.getString("SoChungTu")==null ? "": rs.getString("SoChungTu");
				so = rs.getString("so_number")==null ? "": rs.getString("so_number");
			}
			if(rs != null)
			{				
				rs.close();	
			}
			
			if(chungtu.length()<=0)
			{
				this.msg = "Số chứng từ chưa phát sinh!Vui lòng liên hệ TT!";
				return false;
			}

			/*System.out.println("Số hd "+this.sochungtu);
			System.out.println("Số yc "+chungtu);
			
			if(!this.sochungtu.trim().equals(chungtu.trim()) )
			{
				this.msg = "Số chứng từ không hợp lệ";
				return false;
			}*/
			
			query = " update kho set kho.SOLUONG = kho.SOLUONG - CT.tongxuat, " +
					" 			   kho.booked = kho.booked - CT.tongxuat " +
					" from " +
					" ( " +
					" 	select b.kho_fk as kho_fk, a.npp_fk, b.kbh_fk, b.sanpham_fk, SUM(b.soluong) as tongxuat  " +
					" 	from DonTraHang a inner join DonTraHang_SP b on a.pk_seq = b.dontrahang_Fk " +
					" 	where dontrahang_Fk = '" + this.id + "'  and a.TrangThai=1  " +
					" 	group by b.kho_fk, a.npp_fk, b.kbh_fk, b.sanpham_fk " +
					" ) " +
					" CT inner join NHAPP_KHO kho on CT.kho_fk = kho.KHO_FK  " +
					" and CT.sanpham_fk = kho.SANPHAM_FK and CT.NPP_FK = kho.npp_fk and CT.KBH_FK = kho.kbh_fk ";
			System.out.println("---TANG KHO NGUOC LAI 2: " + query );
			if(db.updateReturnInt(query)<=0)
			{
				msg = "Không thể cập nhật NHAPP_KHO " + query;
				db.getConnection().rollback();
				return false;	
			}

			String ngayksthem1="";
			query="select Replace(convert(char(10), DATEADD(day, 1, cast(max(ngayks) as datetime)) , 102) , '.', '-' ) as ngay from khoasongay where npp_fk="+this.nppId;
			ResultSet rs1=this.db.get(query);
			if(rs1!=null)
			{
				if(rs1.next())
				{
					ngayksthem1=rs1.getString("ngay");
					System.out.println("Ngay Khoa So Them " +ngayksthem1);
				}else
				{
					this.msg="I. Vui Long Thu Lai,Loi :"+ query;
					geso.dms.center.util.Utility.rollback_throw_exception(this.db);
					return false;	
				}
			}else
			{
				this.msg="II. Vui Long Thu Lai,Loi :"+ query;
				geso.dms.center.util.Utility.rollback_throw_exception(this.db);
				return false;
			}
			
			query="update dontrahang set nguoisua="+this.userId+", trangthai='2',Modified_Date=dbo.GetLocalDate(DEFAULT) where pk_seq='" + this.id + "' and TrangThai=1";
			if(db.updateReturnInt(query)!=1)
			{
				msg = "Phiếu trả hàng này đã chốt rồi !"+query;
				geso.dms.center.util.Utility.rollback_throw_exception(this.db);
				return false;
			}
			
			OracleConnUtils SYNC = new OracleConnUtils(); 
			SYNC.getConnection().setAutoCommit(false);
			
			query = "update apps.tbl_phieuxuatkho set trangthai = 1 where sochungtu = '"+chungtu+"' and soso = '"+so+"'";
			if(!SYNC.update(query)){
				this.msg="Khong the Cap Nhat Nhap Hang ,Vui Long Thu Lai : "+ query;
				
				SYNC.update("rollback");
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				
				return false;
			}
			
			SYNC.getConnection().commit();
			SYNC.getConnection().setAutoCommit(true);
			msg=Chot( id,  userId,db);
			if(msg.length()>0)
			{
				geso.dms.center.util.Utility.rollback_throw_exception(this.db);
				return false;
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		
		}catch(Exception e)
		{
			this.msg="Vui Long Thu Lai,Loi :"+ e.toString();
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			e.printStackTrace();
			return false;
		}
		/*finally
		{
			if(db!=null)db.shutDown();
		}*/
		return true;
	}

	private String Chot(String id, String userId,dbutils db )
	{
		
		db_Sync SYNC = new db_Sync(); 
		try
		{
			String msg = "";
		
			SYNC.getConnection().setAutoCommit(false);
			
			String	query= " insert into  DMS_Header(LoaiDonHang,DonTraHang_FK,UserId)"+
					" select 	'ZRE5','"+id+"','"+userId+"' ";
			System.out.println("[DMS_Header]"+ query);
			if(db.updateReturnInt(query)!=1)
			{
				msg = "Không thể đẩy đơn hàng sang SAP!Vui lòng kiểm tra lại kết nối !";
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
							"	'VND' as LoaiTienTe,1 as TyGia,A.NGAYTRA  as NgayChungTu,  "+
							"	 A.NGAYTRA as NgayXacDinhGia,NULL AS HanThanhToan,'101' as LyDoTraHang,NULL as NgayHoaDonTraHang,  "+
							"		'5'+cast(a.PK_SEQ as varchar(20))  as HeaderID, CAST(a.pk_Seq as varchar(20)) +'__' + CAST(a.NPP_FK as varchar(20))  +'__'+ b.MA  + '__' +  "+ 
							"		b.TEN + '__'+ a.NGAYTRA  as FullDesc  "+
							"	from DONTRAHANG a  "+
							"  inner join  NHAPHANPHOI b on b.PK_SEQ=a.NPP_FK "+
							"  where a.TRANGTHAI=2  and a.pk_Seq='"+id+"' ";
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
					return msg;
				}
			}
			rs.close();

			query=
					"	select "+HeaderID+" as HeaderID ,'5'+ CAST(a.DONTRAHANG_FK AS VARCHAR(20)) as SoDonHang,   "+
							"	ROW_NUMBER() OVER(ORDER BY A.DONTRAHANG_FK DESC) AS 'stt' "+
							"	,b.MA as Material, d.DONVI as DonViBanHang,a.SOLUONG,NULL as NgayGiaoHang,NULL as IO_CTTangHang ,NULL as IO_CTCKTien ,NULL as ptChietKhau "+
							"	from DONTRAHANG_SP a inner join dbo.sanpham b on b.pk_Seq=a.sanpham_fk "+	
							"		inner join DONTRAHANG c on c.PK_SEQ=a.DONTRAHANG_FK "+
							"			inner join DONVIDOLUONG d on d.PK_SEQ=b.DVDL_FK "+
							"	where c.TRANGTHAI=2 and  c.pk_Seq='"+id+"' ";
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
				return msg;
			}

			SYNC.getConnection().commit();
			SYNC.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return "Exception: " + e.getMessage();
		}
		finally
		{
			if(SYNC!=null)SYNC.shutDown();
		}
		return "";
	}
	
	
	public void createRs() 
	{
		String query = "select PK_SEQ, TEN from KHO where trangthai = '1'"; 
		this.khoXuatRs = db.get(query);

		query = " select PK_SEQ,TEN from NhaPhanPhoi where pk_Seq='"+this.nppId+"' ";
		this.khRs = db.get(query);

		this.dvtRs = db.getScrol("select PK_SEQ, DONVI from DONVIDOLUONG where trangthai = '1' ");

		this.kbhRs = db.getScrol("select PK_SEQ, TEN from KENHBANHANG where trangthai = '1' ");
		query=
				"select b.MA as spMa,b.ten as spTen,c.DONVI as spDonVi,SOLO,NGAYHETHAN,SOLUONG,BOOKED,AVAILABLE ,d.THUEXUAT,  "+
						"isnull( ( select GIAMUANPP * ( 1 - isnull( ( select chietkhau from BANGGIAMUANPP_NPP   "+
						"											where banggiamuaNPP_FK = bg_sp.bgmuaNPP_FK and NPP_FK = '"+this.nppId+"' ), 0) / 100 )  "+  
						"			from BGMUANPP_SANPHAM bg_sp  "+
						"				where SANPHAM_FK = a.SANPHAM_FK  "+
						"					and BGMUANPP_FK in   "+
						"				(		select top(1) PK_SEQ   "+
						"						from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK   "+
						"						where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = '"+this.nppId+"' and bg.DVKD_FK = b.DVKD_FK  and bg.KENH_FK='"+this.kbhId+"'  "+
						"								order by bg.TUNGAY desc ) ), 0) as giamua  "+
						" from NHAPP_KHO_CHITIET a inner join SANPHAM b on b.PK_SEQ=a.SANPHAM_FK  "+
						" left join DONVIDOLUONG c on c.PK_SEQ=b.DVDL_FK  "+
						"inner join NGANHHANG d on d.PK_SEQ=b.NGANHHANG_FK  "+
						" where NPP_FK='"+this.nppId+"' and SOLUONG>0  "; 

	}

	private void initSANPHAM() 
	{
		String query =  
				"	select b.MA, b.TEN, DV.donvi,( a.SOLUONG) as soluong, a.dongia, 0 as chietkhau,  "+
				"		ISNULL(b.trongluong, 0) as trongluong, ISNULL(b.thetich, 0) as thetich,       " +
				"	ISNULL( ( select sum(available)  from NHAPP_KHO   where KHO_FK = a.kho_fk "+
				"	and sanpham_fk = b.pk_seq  and NPP_FK = (select NPP_FK from DonTraHang where PK_SEQ= '"+this.id+"') " + 
				"			and KBH_FK = a.kbh_fk   ), 0 ) as avai,a.ptVat   "+  
				"	 from   "+ 
				"	 (  "+
				"			 select a.kho_fk, a.kbh_fk, a.DONTRAHANG_FK, sum(a.SOLUONG) as SoLuong,SANPHAM_FK,isnull(ptVat,0) as ptVat, DONGIA  "+
				"			 from DONTRAHANG_SP a "+
				"			 where a.dontrahang_Fk = '"+this.id+"'   "+
				"			 group by kho_fk, kbh_Fk, DONTRAHANG_FK, SANPHAM_FK, a.DONGIA, ptvat  "+  
				"	 ) a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ       "+		
				"		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.dvdl_Fk  ";       

		System.out.println("---INIT SP: " + query);
		ResultSet spRs = db.get(query);

		NumberFormat formater = new DecimalFormat("##,###,###");
		{
			try 
			{
				String spMA = "";
				String spTEN = "";
				String spDONVI = "";
				String spSOLUONG = "";
				String spGIANHAP = "";
				String spTONKHO = "";
				String spVAT = "";

				while(spRs.next())
				{
					spMA += spRs.getString("MA") + "__";
					spTEN += spRs.getString("TEN") + "__";
					spDONVI += spRs.getString("DONVI") + "__";
					spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "__";
					spGIANHAP += formater.format(spRs.getDouble("DONGIA")) + "__";
					spTONKHO += formater.format(spRs.getDouble("avai")) + "__";
					spVAT += spRs.getString("ptVat") + "__";
				}
				spRs.close();

				if(spMA.trim().length() > 0)
				{
					spMA = spMA.substring(0, spMA.length() - 2);
					this.spMa = spMA.split("__");

					spTEN = spTEN.substring(0, spTEN.length() - 2);
					this.spTen = spTEN.split("__");

					spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
					this.spDonvi = spDONVI.split("__");

					spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
					this.spSoluong = spSOLUONG.split("__");

					spGIANHAP = spGIANHAP.substring(0, spGIANHAP.length() - 2);
					this.spGianhap = spGIANHAP.split("__");

					spTONKHO = spTONKHO.substring(0, spTONKHO.length() - 2);
					this.spTonkho = spTONKHO.split("__");

					spVAT = spVAT.substring(0, spVAT.length() - 2);
					this.spVat = spVAT.split("__");

				}
			} 
			catch (Exception e) 
			{
				System.out.println("115.Exception: " + e.getMessage());
				e.printStackTrace();
			}
		}

	}

	public void init() 
	{
		String query =  "select PK_SEQ,NGAYTRA,TRANGTHAI,NPP_FK,NCC_FK,DVKD_FK,KBH_FK,KHO_FK,GhiChu,SoHoaDon,KyHieu " +
				"from DONTRAHANg where pk_seq = '" + this.id + "'";
		System.out.println("____INIT NHAP KHO: " + query);
		ResultSet rs = db.get(query);
		{
			try 
			{
				if(rs.next())
				{
					this.ngayyeucau = rs.getString("NGAYTRA");
					this.ghichu = rs.getString("ghichu");
					this.khoXuatId = rs.getString("KHO_FK");
					this.khId = rs.getString("NPP_FK");
					this.kbhId = rs.getString("kbh_fk");
					this.trangthai = rs.getString("trangthai");
					this.sochungtu = rs.getString("SoHoaDon");
					this.nppId  =rs.getString("NPP_FK");

				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("---LOI INIT: " + e.getMessage());
				e.printStackTrace();
			}
		}

		System.out.println("---KHO XUAT: " + this.khoXuatId);
		this.createRs();

		this.initSANPHAM();

	}

	public void DBclose() {

		try{

			if(khoNhanRs!=null){
				khoNhanRs.close();
			}

			this.db.shutDown();

		}catch(Exception er)
		{
			er.printStackTrace();
		}
	}

	public String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getGhichu() {

		return this.ghichu;
	}

	public void setGhichu(String ghichu) {

		this.ghichu = ghichu;
	}


	public String[] getSpId() {

		return this.spId;
	}


	public void setSpId(String[] spId) {

		this.spId = spId;
	}


	public String[] getSpMa() {

		return this.spMa;
	}


	public void setSpMa(String[] spMa) {

		this.spMa = spMa;
	}


	public String[] getSpTen() {

		return this.spTen;
	}


	public void setSpTen(String[] spTen) {

		this.spTen = spTen;
	}


	public String[] getSpDonvi() {

		return this.spDonvi;
	}


	public void setSpDonvi(String[] spDonvi) {

		this.spDonvi = spDonvi;
	}


	public String[] getSpSoluong() {

		return this.spSoluong;
	}


	public void setSpSoluong(String[] spSoluong) {

		this.spSoluong = spSoluong;
	}


	public String[] getSpGianhap() {

		return this.spGianhap;
	}


	public void setSpGianhap(String[] spGianhap) {

		this.spGianhap = spGianhap;
	}


	public String[] getSpSolo() {

		return this.spSolo;
	}


	public void setSpSolo(String[] spSolo) {

		this.spSolo = spSolo;
	}


	public String[] getSpNgaysanxuat() {

		return this.spNgaysanxuat;
	}


	public void setSpNgaysanxuat(String[] spNgaysanxuat) {

		this.spNgaysanxuat = spNgaysanxuat;
	}


	public String[] getSpTonkho() {

		return this.spTonkho;
	}


	public void setSpTonkho(String[] spTonkho) {

		this.spTonkho = spTonkho;
	}


	public String[] getSpBooked() {

		return this.spBooked;
	}


	public void setSpBooked(String[] spBooked) {

		this.spBooked = spBooked;
	}


	public ResultSet getSanphamRs() {

		return this.spRs;
	}


	public void setSanphamRs(ResultSet spRs) {

		this.spRs = spRs;
	}


	public String getKhoXuatId() {

		return this.khoXuatId;
	}


	public void setKhoXuatId(String khoxuattt) {

		this.khoXuatId = khoxuattt;
	}


	public ResultSet getKhoXuatRs() {

		return this.khoXuatRs;
	}


	public void setKhoXuatRs(ResultSet khoxuatRs) {

		this.khoXuatRs = khoxuatRs;
	}


	public String getKhId() {

		return this.khId;
	}


	public void setKhId(String khId) {

		this.khId = khId;
	}


	public ResultSet getKhRs() {

		return this.khRs;
	}


	public void setKhRs(ResultSet khRs) {

		this.khRs = khRs;
	}


	public ResultSet getDvtRs() {

		return this.dvtRs;
	}


	public void setDvtRs(ResultSet dvtRs) {

		this.dvtRs = dvtRs;
	}


	public String getKbhId() {

		return this.kbhId;
	}


	public void setKbhId(String kbhId) {

		this.kbhId = kbhId;
	}


	public ResultSet getKbhRs() {

		return this.kbhRs;
	}


	public void setKbhRs(ResultSet kbhRs) {

		this.kbhRs = kbhRs;
	}

	public String getNppId() 
	{
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
	}

	public String getNppTen() 
	{
		return this.nppTen;
	}

	public void setNppTen(String nppTen) 
	{
		this.nppTen = nppTen;
	}

	public String getSitecode() 
	{
		return this.sitecode;
	}

	public void setSitecode(String sitecode) 
	{
		this.sitecode = sitecode;
	}

	public String getLenhdieudong() 
	{
		return this.lenhdieudong;
	}

	public void setLenhdieudong(String lenhdieudong) 
	{
		this.lenhdieudong =lenhdieudong;

	}

	public String getLDDcua() 
	{
		return this.lddcua;
	}

	public void setLDDcua(String LDDcua) 
	{
		this.lddcua= LDDcua;

	}

	public String getLDDveviec() 
	{
		return this.lddveviec;
	}

	public void setLDDveviec(String LDDveviec) 
	{
		this.lddveviec= LDDveviec;

	}

	public String getNgaydieudong() 
	{
		return this.ngaylenhdieudong;
	}

	public void setNgaydieudong(String ngaydieudong) 
	{
		this.ngaylenhdieudong =ngaydieudong;

	}

	public String getNguoivanchuyen() 
	{
		return this.nguoivanchuyen;
	}

	public void setNguoivanchuyen(String nguoivanchuyen) 
	{
		this.nguoivanchuyen = nguoivanchuyen;

	}

	public String getPtvanchuyen() 
	{
		return this.ptvanchuyen;
	}

	public void setPtvanchuyen(String ptvanchuyen) 
	{
		this.ptvanchuyen = ptvanchuyen;

	}

	public String getSohopdong() 
	{
		return this.sohopdong;
	}

	public void setSohopdong(String sohopdong) 
	{
		this.sohopdong = sohopdong;

	}

	public String getNgayhopdong() 
	{
		return this.ngayhopdong;
	}

	public void setNgayhopdong(String ngayhopdong) 
	{
		this.ngayhopdong = ngayhopdong;

	}



	public String getSoChungTu()
	{
		return sochungtu;
	}
	public void setSoChungTu(String sochungtu)
	{
		this.sochungtu=sochungtu;
	}

	String[] spNgayHetHan;

	public String[] getSpNgayHetHan()
	{
		return spNgayHetHan;
	}

	public void setSpNgayHetHan(String[] ngayHetHan)
	{
		this.spNgayHetHan = ngayHetHan;
	}


	public Hashtable<String, String> getSanpham_Soluong() {

		return this.sanpham_soluong;
	}


	public void setSanpham_Soluong(Hashtable<String, String> sp_soluong) {

		this.sanpham_soluong = sp_soluong;
	}

	Hashtable<String, Integer> sp_sl;
	public Hashtable<String, Integer> getSp_Soluong() 
	{
		return this.sp_sl;
	}

	public void setSSp_Soluong(Hashtable<String, Integer> sp_sl) 
	{
		this.sp_sl = sp_sl;
	}

	public ResultSet getSpRs() 
	{
		return this.spRs;
	}

	public void setSpRs(ResultSet spRs) 
	{
		this.spRs = spRs;
	}

	String[] spVat;

	public String[] getSpVat()
	{
		return spVat;
	}

	public void setSpVat(String[] spVat)
	{
		this.spVat = spVat;
	}


}
