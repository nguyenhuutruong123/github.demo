package geso.dms.center.beans.chitieu.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import geso.dms.center.beans.chitieu.IChiTieuNhanVien;
import geso.dms.center.db.sql.dbutils;

public class ChiTieuNhanVien implements IChiTieuNhanVien
{
	String userId;
	String id;
	String scheme;
	String thang;
	String nam;
	String diengiai;

	ResultSet sanphamRs;
	String spIds;
	ResultSet nppRs;
	String nppIds;

	ResultSet vungRs;
	String vungIds;
	ResultSet kvRs;
	String kvIds;

	ResultSet NppCtRs;
	
	String[] tumuc;
	String[] denmuc;

	String msg;

	dbutils db;

	public ChiTieuNhanVien()
	{
		this.id = "";
		this.thang = "";
		this.nam = "";
		this.diengiai = "";
		this.msg = "";
		this.scheme = "";
		this.spIds = "";
		this.nppIds = "";
		this.vungIds = "";
		this.kvIds = "";
		this.doituong="";
		this.apdung="0";
		this.quy="";
		this.loaichitieu="0";
		this.view="";
		this.asmIds="";
		this.rsmIds="";
		this.tdvIds="";
		this.ssIds="";
		this.sql="";
		this.sqlNhom="";
		this.db = new dbutils();
	}

	public ChiTieuNhanVien(String id)
	{
		this.id = id;
		this.thang = "";
		this.nam = "";
		this.diengiai = "";
		this.msg = "";
		this.scheme = "";
		this.spIds = "";
		this.nppIds = "";
		this.vungIds = "";
		this.kvIds = "";
		this.doituong="";
		this.apdung="0";
		this.quy="";
		this.loaichitieu="0";
		this.view="";
		this.asmIds="";
		this.rsmIds="";
		this.tdvIds="";
		this.ssIds="";
		this.sql="";
		this.sqlNhom="";
		this.db = new dbutils();
	}

	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getThang()
	{
		return this.thang;
	}

	public void setThang(String thang)
	{
		this.thang = thang;
	}

	public String getNam()
	{
		return this.nam;
	}

	public void setNam(String nam)
	{
		this.nam = nam;
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getDiengiai()
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai)
	{
		this.diengiai = diengiai;
	}

	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}


	public String Check() 
	{
		
		if(this.thang.trim().length()<=0)
		{
			return "Vui lòng chọn tháng ";
		}
		
		if(this.nam.trim().length()<=0)
		{
			return "Vui lòng chọn năm ";
		}
		
		if(this.sql.trim().length()<=0)
		{
			return "Vui lòng chọn file Upload ";
		}
		
		String msg= "";
		String	query=" select nvId,nvTen from ("+this.sql+") as data where nvLoai='SR' and nvId not in (select smartId from daidienkinhdoanh ) ";
		System.out.println("[daidienkinhdoanh]"+query);
		ResultSet	rs = db.get(query);
		try
		{
			while(rs.next())
			{
				msg += rs.getString("nvTen") +" \n ";
			}
			if(msg.length()>0)
			{
				this.msg =" Nhân viên bán hàng "+msg+ " chưa được khai báo trong hệ thống ";
			}
			if(rs!=null)rs.close();

			query=" select nppMa,nppTen from ("+this.sql+") as data where nvLoai='SR' and nppMa not in (select Ma from NhaPhanPhoi ) ";
			System.out.println("[NhaPhanPhoi]"+query);
			rs = db.get(query);
			while(rs.next())
			{
				msg += rs.getString("nppTen") +" \n ";
			}
			if(msg.length()>0)
			{
				this.msg ="Nhà phân phối "+msg+ " chưa được khai báo trong hệ thống ";
			}
			if(rs!=null)rs.close();

			query=" select gsbhMa,gsbhTen from ("+this.sql+") as data where nvLoai='SR' and gsbhMa not in (select smartId from GiamSatBanHang ) ";
			System.out.println("[GiamSatBanHang]"+query);
			rs = db.get(query);
			while(rs.next())
			{
				msg += rs.getString("gsbhTen") +" \n ";
			}
			if(msg.length()>0)
			{
				this.msg ="Giám sát bán hàng "+msg+ " chưa được khai báo trong hệ thống ";
			}
			if(rs!=null)rs.close();
			
			if(this.sqlNhom.trim().length()>0)
			{
				query="select nhomId from ("+this.sqlNhom+") as data where nvLoai='SR' and nhomId not in (select pk_Seq from NHOMSANPHAM ) ";
				System.out.println("[NHOMSANPHAM]"+query);
				rs = db.get(query);
				while(rs.next())
				{
					msg += rs.getString("nhomId") +" \n ";
				}
				if(msg.length()>0)
				{
					this.msg =" Nhóm sản phẩm "+msg+ " chưa được khai báo trong hệ thống ";
				}
				if(rs!=null)rs.close();
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
				return "Exception "+e.getMessage();
		}
		return msg;
	}


	public boolean save()
	{
		try
		{
			if (this.nam.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn kết thúc";
				return false;
			}

			if (this.scheme.trim().length() <= 0)
			{
				this.msg = "Vui lòng scheme";
				return false;
			}			

			if(Check().length()>0)
			{
				this.msg=Check();
				return false;
			}

			db.getConnection().setAutoCommit(false);

	/*		String	query =
					" INSERT INTO [ChiTieuNhanVien]([Thang],[Nam],[Quy],[Ma],[Ten],[NguoiTao],[NguoiSua],[NgayTao],[NgaySua],[TrangThai],[ApDung],[LoaiChiTieu])" +
							" select "+(this.thang.length()<=0?"NULL":this.thang)+","+(this.nam.length()<=0?"NULL":this.nam)+","+(this.quy.length()<=0?"NULL":this.quy)+",N'"+this.scheme+"',N'"+this.diengiai+"','"+this.userId+"','"+this.userId+"','"+this.getDateTime()+"','"+this.getDateTime()+"',0,'"+this.apdung+"','"+this.loaichitieu+"' ";
			System.out.println("1.Insert: " + query);
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi ChiTieu: " + query;
				db.getConnection().rollback();
				return false;
			}

			query = "select scope_identity() as pxkId";
			ResultSet	rs = db.get(query);
			rs.next();
			this.id = rs.getString("pxkId");
			rs.close();*/
			
			String query = "INSERT INTO [CHITIEUNHANVIEN]([Thang],[Nam],[Quy],[Ma],[Ten],[NguoiTao],[NguoiSua],[NgayTao],[NgaySua],[TrangThai],[ApDung],[LoaiChiTieu]) select ?,?,?,?,?,?,?,?,?,?,?,?";

			Object[] data = {(this.thang.length()<=0?"NULL":this.thang),(this.nam.length()<=0?"NULL":this.nam),(this.quy.length()<=0?"NULL":this.quy),this.scheme,this.diengiai,this.userId,this.userId,this.getDateTime(),this.getDateTime(),0,this.apdung,this.loaichitieu};

			dbutils.viewQuery(query,data);
			if (this.db.updateQueryByPreparedStatement(query,data) != 1) {
				this.msg = "1. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
				this.db.getConnection().rollback();
				return false;
			}
			this.id = db.getPk_seq();

			query =
					" insert into ChiTieuNhanVien_DDKD(ChiTieuNhanVien_FK,DDKD_FK,NPP_FK,GSBH_FK,ChiTieu,SoDonHang,GiaTriTBDonHang,SoKhachHangMoi,SoKhachHangMuaHang) " +
							" select '"+this.id+"',nv.pk_seq,npp.pk_Seq,gs.pk_seq,data.ChiTieu,data.SoDonHang,data.GiaTriTBDonHang,data.SoKhachHangMoi,data.SoKhachHangMuaHang from ("+sql+") as data  " +
							"  inner join daidienkinhdoanh nv on nv.SmartId=data.nvId  "+
							"  inner join NhaPhanPhoi npp on npp.Ma=data.nppMa  "+
							"  inner join GiamSatBanHang gs on gs.SmartId=data.gsbhMa  "+
							"		where data.nvLoai='SR' and gs.trangthai=1 and npp.trangthai=1 ";

			System.out.println("[ChiTieuNhanVien_DDKD]"+query);
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi ChiTieu: " + query;
				db.getConnection().rollback();
				return false;
			}

			if(this.sqlNhom.length()>0)
			{
				query =" insert into ChiTieuNhanVien_DDKD_NHOMSP(ChiTieuNhanVien_FK,DDKD_FK,NPP_FK,GSBH_FK,NHOMSP_FK,ChiTieu) " +
						" select '"+this.id+"',nv.pk_seq,npp.pk_seq as nppId,gs.pk_seq as gsbhId,data.nhomId, data.ChiTieu from ("+sqlNhom+") as data  " +
						"  inner join daidienkinhdoanh nv on nv.SmartId=data.nvId  "+
						"  inner join NhaPhanPhoi npp on npp.Ma=data.nppMa  "+
						"  inner join GiamSatBanHang gs on gs.SmartId=data.gsbhMa  "+
						"		where data.nvLoai='SR' and gs.trangthai=1 and npp.trangthai=1  ";
				System.out.println("[ChiTieuNhanVien_DDKD_NHOMSP]"+query);
				if (!db.update(query))
				{
					this.msg = "Khong the tao moi ChiTieu: " + query;
					db.getConnection().rollback();
					return false;
				}

				query =" insert into ChiTieuNhanVien_ASM_NHOMSP(ChiTieuNhanVien_FK,DDKD_FK,NPP_FK,GSBH_FK,ASM_FK,KHUVUC_fK,NHOMSP_FK,ChiTieu) " +
						" select '"+this.id+"',nv.pk_seq,nv.npp_fk,aa.gsbh_fk,bb.asm_fk,bb.khuvuc_fk,data.nhomId,data.ChiTieu from ("+sqlNhom+") as data  " +
						"  inner join daidienkinhdoanh nv on nv.SmartId=data.nvId " +
						"  INNER JOIN DDKD_GSBH aa on aa.ddkd_Fk=nv.pk_seq     " +
						" inner join NHAPHANPHOI npp on npp.PK_SEQ=nv.NPP_FK" +
						" inner join    ASM_KHUVUC bb on bb.khuvuc_fk=npp.khuvuc_fk    " +
						" inner join ASM asm on asm.pk_Seq=bb.asm_fk     "+
						"		where data.nvLoai='SR' and asm.trangthai=1 ";
				System.out.println("[ChiTieuNhanVien_ASM_NHOMSP]"+query);
				if (!db.update(query))
				{
					this.msg = "Khong the tao moi ChiTieu: " + query;
					db.getConnection().rollback();
					return false;
				}

				query =" insert into ChiTieuNhanVien_BM_NHOMSP(ChiTieuNhanVien_FK,DDKD_FK,NPP_FK,GSBH_FK,BM_FK,VUNG_FK,NHOMSP_FK,ChiTieu) " +
						" select '"+this.id+"',nv.pk_seq,nv.npp_fk,aa.gsbh_fk,bb.bm_fk,bb.vung_fk,data.nhomId,data.ChiTieu from ("+sqlNhom+") as data  " +
						"  inner join daidienkinhdoanh nv on nv.SmartId=data.nvId " +
						"  INNER JOIN DDKD_GSBH aa on aa.ddkd_Fk=nv.pk_seq     " +
						" inner join NHAPHANPHOI npp on npp.PK_SEQ=nv.NPP_FK " +
						" inner join khuvuc kv on kv.pk_Seq=npp.khuvuc_Fk" +
						"  inner join vung v on v.pk_Seq=kv.vung_fk    	" +
						" inner join    BM_CHINHANH bb on bb.vung_fk=kv.vung_fk     " +
						"  inner join BM on bm.pk_Seq=bb.bm_fk    "+
						"		where data.nvLoai='SR' and bm.TrangThai=1 and npp.TrangThai=1 and bm.TrangThai=1 ";
				System.out.println("[ChiTieuNhanVien_BM_NHOMSP]"+query);
				if (!db.update(query))
				{
					this.msg = "Khong the tao moi ChiTieu: " + query;
					db.getConnection().rollback();
					return false;
				}

				query =
						"  insert into  ChiTieuNhanVien_SanPham(ChiTieuNhanVien_FK,SANPHAM_FK,NHOMSP_FK) " + 
								"	select "+this.id+",SP_FK,NSP_FK from NHOMSANPHAM_SANPHAM "+
								"	where NSP_FK in "+ 
								"	( "+
								"		select distinct nhomId "+
								"		from "+
								"		( "+this.sqlNhom+"		) as nhom "+"	)  ";

				System.out.println("[ChiTieuNhanVien_SanPham]"+query);
				if (!db.update(query))
				{
					this.msg = "Khong the tao moi ChiTieu: " + query;
					db.getConnection().rollback();
					return false;
				}

			}



			//Trong file upload đã có gíám sát rồi nên không sum nvbh lên cho gs nữa.
			/*query =" insert into ChiTieuNhanVien_GSBH(ChiTieuNhanVien_FK,DDKD_FK,NPP_FK,GSBH_FK,ChiTieu,SoDonHang,GiaTriTBDonHang,SoKhachHangMoi,SoKhachHangMuaHang) " +
					" select '"+this.id+"',nv.pk_seq as DDKD_FK,nv.npp_fk,aa.gsbh_fk,data.ChiTieu,data.SoDonHang,data.GiaTriTBDonHang,data.SoKhachHangMoi,data.SoKhachHangMuaHang from ("+sql+") as data  " +
					"  inner join daidienkinhdoanh nv on nv.PK_SEQ=data.nvId " +
					"  INNER JOIN DDKD_GSBH aa on aa.ddkd_Fk=nv.pk_seq     "+
					"		where data.nvLoai='SR' ";
			System.out.println("[ChiTieuNhanVien_GSBH]"+query);
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi ChiTieu: " + query;
				db.getConnection().rollback();
				return false;
			}

			query =" insert into ChiTieuNhanVien_GSBH_NHOMSP(ChiTieuNhanVien_FK,DDKD_FK,NPP_FK,GSBH_FK,NHOMSP_FK,ChiTieu) " +
					" select '"+this.id+"',nv.pk_seq as DDKD_FK,nv.npp_fk,aa.gsbh_fk,data.nhomId,data.ChiTieu from ("+sqlNhom+") as data  " +
					"  inner join daidienkinhdoanh nv on nv.PK_SEQ=data.nvId  "+
					"  INNER JOIN DDKD_GSBH aa on aa.ddkd_Fk=nv.pk_seq     "+
					"		where data.nvLoai='SR' ";
			System.out.println("[ChiTieuNhanVien_GSBH_FK_NHOMSP]"+query);
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi ChiTieu: " + query;
				db.getConnection().rollback();
				return false;
			}
			 */

			query =" insert into ChiTieuNhanVien_ASM(ChiTieuNhanVien_FK,DDKD_FK,NPP_FK,GSBH_FK,ASM_FK,KHUVUC_fK,ChiTieu,SoDonHang,GiaTriTBDonHang,SoKhachHangMoi,SoKhachHangMuaHang) " +
					" select '"+this.id+"',nv.pk_seq,nv.npp_fk,aa.gsbh_fk,bb.asm_fk,bb.khuvuc_fk,data.ChiTieu,data.SoDonHang,data.GiaTriTBDonHang,data.SoKhachHangMoi,data.SoKhachHangMuaHang from ("+sql+") as data  " +
					"  inner join daidienkinhdoanh nv on nv.SmartId=data.nvId " +
					"  INNER JOIN DDKD_GSBH aa on aa.ddkd_Fk=nv.pk_seq     " +
					" inner join NHAPHANPHOI npp on npp.PK_SEQ=nv.NPP_FK" +
					" inner join    ASM_KHUVUC bb on bb.khuvuc_fk=npp.khuvuc_fk      "+
					" inner join ASM asm on asm.pk_Seq=bb.asm_fk     "+
					"		where data.nvLoai='SR' and asm.TrangThai=1 ";
			System.out.println("[ChiTieuNhanVien_ASM]"+query);
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi ChiTieu: " + query;
				db.getConnection().rollback();
				return false;
			}
			query =" insert into ChiTieuNhanVien_BM(ChiTieuNhanVien_FK,DDKD_FK,NPP_FK,GSBH_FK,BM_FK,VUNG_FK,ChiTieu,SoDonHang,GiaTriTBDonHang,SoKhachHangMoi,SoKhachHangMuaHang) " +
					" select '"+this.id+"',nv.pk_seq,nv.npp_fk,aa.gsbh_fk,bb.bm_fk,bb.vung_fk,data.ChiTieu,data.SoDonHang,data.GiaTriTBDonHang,data.SoKhachHangMoi,data.SoKhachHangMuaHang from ("+sql+") as data  " +
					"  inner join daidienkinhdoanh nv on nv.SmartId=data.nvId " +
					"  INNER JOIN DDKD_GSBH aa on aa.ddkd_Fk=nv.pk_seq     " +
					" inner join NHAPHANPHOI npp on npp.PK_SEQ=nv.NPP_FK " +
					" inner join khuvuc kv on kv.pk_Seq=npp.khuvuc_Fk" +
					"  inner join vung v on v.pk_Seq=kv.vung_fk    	" +
					" inner join    BM_CHINHANH bb on bb.vung_fk=kv.vung_fk      "+
					" inner join BM asm on asm.pk_Seq=bb.BM_FK      "+
					"		where data.nvLoai='SR' and asm.TrangThai=1  ";
			System.out.println("[ChiTieuNhanVien_BM]"+query);
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi ChiTieu: " + query;
				db.getConnection().rollback();
				return false;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e)
		{
			e.printStackTrace();
			try
			{
				db.getConnection().rollback();
				System.out.println("__EXCEPTION UPDATE: " + e.getMessage());
			} catch (SQLException e1)
			{
			}
			return false;
		}

		return true;
	}

	public boolean edit()
	{
		try
		{
			if (this.nam.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn kết thúc";
				return false;
			}

			if (this.scheme.trim().length() <= 0)
			{
				this.msg = "Vui lòng scheme";
				return false;
			}			

			if(Check().length()>0)
			{
				this.msg=Check();
				return false;
			}

			db.getConnection().setAutoCommit(false);

	/*		String	query =
					" update [ChiTieuNhanVien] set [Thang]="+(this.thang.trim().length()<=0?"NULL":this.thang)+",[Nam]='"+(this.nam.trim().length()<=0?"NULL":this.nam)+"',[Quy]="+(this.quy.trim().length()<=0?"NULL":this.quy)+",[Ma]=N'"+this.scheme+"',[Ten]=N'"+this.diengiai+"',[NguoiSua]='"+this.userId+"',[NgaySua]='"+this.getDateTime()+"',[ApDung]='"+this.apdung+"' " +
							" where pk_seq='"+this.id+"'  " ;
			System.out.println("1.update: " + query);
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi ChiTieu: " + query;
				db.getConnection().rollback();
				return false;
			}*/

			String	query =
					" update [ChiTieuNhanVien] set [Thang]=?,[Nam]=?,"
							+ "[Quy]=?,[Ma]=?,[Ten]=?,[NguoiSua]=?,"
									+ "[NgaySua]=?,[ApDung]=? " +
							" where pk_seq='"+this.id+"'  " ;
			
			Object[] data = {(this.thang.trim().length()<=0?"NULL":this.thang),(this.nam.trim().length()<=0?"NULL":this.nam),
					(this.quy.trim().length()<=0?"NULL":this.quy),this.scheme,this.diengiai,this.userId,this.getDateTime(),this.apdung};
			
			dbutils.viewQuery(query,data);
			if (this.db.updateQueryByPreparedStatement(query,data) != 1) {
				this.msg = "1. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
				this.db.getConnection().rollback();
				return false;
			}

			query="delete from ChiTieuNhanVien_DDKD where ChiTieuNhanVien_FK='"+this.id+"'";
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi ChiTieu: " + query;
				db.getConnection().rollback();
				return false;
			}

			query="delete from ChiTieuNhanVien_DDKD_NHOMSP where ChiTieuNhanVien_FK='"+this.id+"'";
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi ChiTieu: " + query;
				db.getConnection().rollback();
				return false;
			}

			query="delete from ChiTieuNhanVien_GSBH where ChiTieuNhanVien_FK='"+this.id+"'";
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi ChiTieu: " + query;
				db.getConnection().rollback();
				return false;
			}

			query="delete from ChiTieuNhanVien_GSBH_NHOMSP where ChiTieuNhanVien_FK='"+this.id+"'";
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi ChiTieu: " + query;
				db.getConnection().rollback();
				return false;
			}

			query="delete from ChiTieuNhanVien_ASM where ChiTieuNhanVien_FK='"+this.id+"'";
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi ChiTieu: " + query;
				db.getConnection().rollback();
				return false;
			}

			query="delete from ChiTieuNhanVien_ASM_NHOMSP where ChiTieuNhanVien_FK='"+this.id+"'";
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi ChiTieu: " + query;
				db.getConnection().rollback();
				return false;
			}

			query="delete from ChiTieuNhanVien_BM where ChiTieuNhanVien_FK='"+this.id+"'";
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi ChiTieu: " + query;
				db.getConnection().rollback();
				return false;
			}

			query="delete from ChiTieuNhanVien_BM_NHOMSP where ChiTieuNhanVien_FK='"+this.id+"'";
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi ChiTieu: " + query;
				db.getConnection().rollback();
				return false;
			}



			query =
					" insert into ChiTieuNhanVien_DDKD(ChiTieuNhanVien_FK,DDKD_FK,NPP_FK,GSBH_FK,ChiTieu,SoDonHang,GiaTriTBDonHang,SoKhachHangMoi,SoKhachHangMuaHang) " +
							" select '"+this.id+"',nv.pk_seq,npp.pk_Seq,gs.pk_seq,data.ChiTieu,data.SoDonHang,data.GiaTriTBDonHang,data.SoKhachHangMoi,data.SoKhachHangMuaHang from ("+sql+") as data  " +
							"  inner join daidienkinhdoanh nv on nv.SmartId=data.nvId  "+
							"  inner join NhaPhanPhoi npp on npp.Ma=data.nppMa  "+
							"  inner join GiamSatBanHang gs on gs.SmartId=data.gsbhMa  "+
							"		where data.nvLoai='SR' ";

			System.out.println("[ChiTieuNhanVien_DDKD]"+query);
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi ChiTieu: " + query;
				db.getConnection().rollback();
				return false;
			}

			if(this.sqlNhom.length()>0)
			{
				query =" insert into ChiTieuNhanVien_DDKD_NHOMSP(ChiTieuNhanVien_FK,DDKD_FK,NPP_FK,GSBH_FK,NHOMSP_FK,ChiTieu) " +
						" select '"+this.id+"',nv.pk_seq,npp.pk_seq as nppId,gs.pk_seq as gsbhId,data.nhomId, data.ChiTieu from ("+sqlNhom+") as data  " +
						"  inner join daidienkinhdoanh nv on nv.SmartId=data.nvId  "+
						"  inner join NhaPhanPhoi npp on npp.Ma=data.nppMa  "+
						"  inner join GiamSatBanHang gs on gs.SmartId=data.gsbhMa  "+
						"		where data.nvLoai='SR' ";
				System.out.println("[ChiTieuNhanVien_DDKD_NHOMSP]"+query);
				if (!db.update(query))
				{
					this.msg = "Khong the tao moi ChiTieu: " + query;
					db.getConnection().rollback();
					return false;
				}

				query =" insert into ChiTieuNhanVien_ASM_NHOMSP(ChiTieuNhanVien_FK,DDKD_FK,NPP_FK,GSBH_FK,ASM_FK,KHUVUC_fK,NHOMSP_FK,ChiTieu) " +
						" select '"+this.id+"',nv.pk_seq,nv.npp_fk,aa.gsbh_fk,bb.asm_fk,bb.khuvuc_fk,data.nhomId,data.ChiTieu from ("+sqlNhom+") as data  " +
						"  inner join daidienkinhdoanh nv on nv.SmartId=data.nvId " +
						"  INNER JOIN DDKD_GSBH aa on aa.ddkd_Fk=nv.pk_seq     " +
						" inner join NHAPHANPHOI npp on npp.PK_SEQ=nv.NPP_FK" +
						" inner join    ASM_KHUVUC bb on bb.khuvuc_fk=npp.khuvuc_fk      "+
						"		where data.nvLoai='SR' ";
				System.out.println("[ChiTieuNhanVien_ASM_NHOMSP]"+query);
				if (!db.update(query))
				{
					this.msg = "Khong the tao moi ChiTieu: " + query;
					db.getConnection().rollback();
					return false;
				}

				query =" insert into ChiTieuNhanVien_BM_NHOMSP(ChiTieuNhanVien_FK,DDKD_FK,NPP_FK,GSBH_FK,BM_FK,VUNG_FK,NHOMSP_FK,ChiTieu) " +
						" select '"+this.id+"',nv.pk_seq,nv.npp_fk,aa.gsbh_fk,bb.bm_fk,bb.vung_fk,data.nhomId,data.ChiTieu from ("+sqlNhom+") as data  " +
						"  inner join daidienkinhdoanh nv on nv.SmartId=data.nvId " +
						"  INNER JOIN DDKD_GSBH aa on aa.ddkd_Fk=nv.pk_seq     " +
						" inner join NHAPHANPHOI npp on npp.PK_SEQ=nv.NPP_FK " +
						" inner join khuvuc kv on kv.pk_Seq=npp.khuvuc_Fk" +
						"  inner join vung v on v.pk_Seq=kv.vung_fk    	" +
						" inner join    BM_CHINHANH bb on bb.vung_fk=kv.vung_fk      "+
						"		where data.nvLoai='SR' ";
				System.out.println("[ChiTieuNhanVien_BM_NHOMSP]"+query);
				if (!db.update(query))
				{
					this.msg = "Khong the tao moi ChiTieu: " + query;
					db.getConnection().rollback();
					return false;
				}

				query =
						"  insert into  ChiTieuNhanVien_SanPham(ChiTieuNhanVien_FK,SANPHAM_FK,NHOMSP_FK) " + 
								"	select "+this.id+",SP_FK,NSP_FK from NHOMSANPHAM_SANPHAM "+
								"	where NSP_FK in "+ 
								"	( "+
								"		select distinct nhomId "+
								"		from "+
								"		( "+this.sqlNhom+"		) as nhom "+"	)  ";

				System.out.println("[ChiTieuNhanVien_SanPham]"+query);
				if (!db.update(query))
				{
					this.msg = "Khong the tao moi ChiTieu: " + query;
					db.getConnection().rollback();
					return false;
				}

			}



			//Trong file upload đã có gíám sát rồi nên không sum nvbh lên cho gs nữa.
			/*query =" insert into ChiTieuNhanVien_GSBH(ChiTieuNhanVien_FK,DDKD_FK,NPP_FK,GSBH_FK,ChiTieu,SoDonHang,GiaTriTBDonHang,SoKhachHangMoi,SoKhachHangMuaHang) " +
					" select '"+this.id+"',nv.pk_seq as DDKD_FK,nv.npp_fk,aa.gsbh_fk,data.ChiTieu,data.SoDonHang,data.GiaTriTBDonHang,data.SoKhachHangMoi,data.SoKhachHangMuaHang from ("+sql+") as data  " +
					"  inner join daidienkinhdoanh nv on nv.PK_SEQ=data.nvId " +
					"  INNER JOIN DDKD_GSBH aa on aa.ddkd_Fk=nv.pk_seq     "+
					"		where data.nvLoai='SR' ";
			System.out.println("[ChiTieuNhanVien_GSBH]"+query);
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi ChiTieu: " + query;
				db.getConnection().rollback();
				return false;
			}

			query =" insert into ChiTieuNhanVien_GSBH_NHOMSP(ChiTieuNhanVien_FK,DDKD_FK,NPP_FK,GSBH_FK,NHOMSP_FK,ChiTieu) " +
					" select '"+this.id+"',nv.pk_seq as DDKD_FK,nv.npp_fk,aa.gsbh_fk,data.nhomId,data.ChiTieu from ("+sqlNhom+") as data  " +
					"  inner join daidienkinhdoanh nv on nv.PK_SEQ=data.nvId  "+
					"  INNER JOIN DDKD_GSBH aa on aa.ddkd_Fk=nv.pk_seq     "+
					"		where data.nvLoai='SR' ";
			System.out.println("[ChiTieuNhanVien_GSBH_FK_NHOMSP]"+query);
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi ChiTieu: " + query;
				db.getConnection().rollback();
				return false;
			}
			 */

			query =" insert into ChiTieuNhanVien_ASM(ChiTieuNhanVien_FK,DDKD_FK,NPP_FK,GSBH_FK,ASM_FK,KHUVUC_fK,ChiTieu,SoDonHang,GiaTriTBDonHang,SoKhachHangMoi,SoKhachHangMuaHang) " +
					" select '"+this.id+"',nv.pk_seq,nv.npp_fk,aa.gsbh_fk,bb.asm_fk,bb.khuvuc_fk,data.ChiTieu,data.SoDonHang,data.GiaTriTBDonHang,data.SoKhachHangMoi,data.SoKhachHangMuaHang from ("+sql+") as data  " +
					"  inner join daidienkinhdoanh nv on nv.SmartId=data.nvId " +
					"  INNER JOIN DDKD_GSBH aa on aa.ddkd_Fk=nv.pk_seq     " +
					" inner join NHAPHANPHOI npp on npp.PK_SEQ=nv.NPP_FK" +
					" inner join    ASM_KHUVUC bb on bb.khuvuc_fk=npp.khuvuc_fk      "+
					"		where data.nvLoai='SR' ";
			System.out.println("[ChiTieuNhanVien_ASM]"+query);
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi ChiTieu: " + query;
				db.getConnection().rollback();
				return false;
			}
			query =" insert into ChiTieuNhanVien_BM(ChiTieuNhanVien_FK,DDKD_FK,NPP_FK,GSBH_FK,BM_FK,VUNG_FK,ChiTieu,SoDonHang,GiaTriTBDonHang,SoKhachHangMoi,SoKhachHangMuaHang) " +
					" select '"+this.id+"',nv.pk_seq,nv.npp_fk,aa.gsbh_fk,bb.bm_fk,bb.vung_fk,data.ChiTieu,data.SoDonHang,data.GiaTriTBDonHang,data.SoKhachHangMoi,data.SoKhachHangMuaHang from ("+sql+") as data  " +
					"  inner join daidienkinhdoanh nv on nv.SmartId=data.nvId " +
					"  INNER JOIN DDKD_GSBH aa on aa.ddkd_Fk=nv.pk_seq     " +
					" inner join NHAPHANPHOI npp on npp.PK_SEQ=nv.NPP_FK " +
					" inner join khuvuc kv on kv.pk_Seq=npp.khuvuc_Fk" +
					"  inner join vung v on v.pk_Seq=kv.vung_fk    	" +
					" inner join    BM_CHINHANH bb on bb.vung_fk=kv.vung_fk      "+
					"		where data.nvLoai='SR' ";
			System.out.println("[ChiTieuNhanVien_BM]"+query);
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi ChiTieu: " + query;
				db.getConnection().rollback();
				return false;
			}


			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e)
		{
			e.printStackTrace();
			try
			{
				db.getConnection().rollback();
				System.out.println("__EXCEPTION UPDATE: " + e.getMessage());
			} catch (SQLException e1)
			{
			}
			return false;
		}

		return true;
	}

	public void init()
	{

		String colID="";
		String query = 
				"select ApDung,ma as ma, Thang, nam,Quy, ten,loaichitieu,trangthai  " + 
						"		, '[' + REPLACE(   	  "+
						"				   ( 	select  distinct b.nhomsp_fk   as [data()]   "+
						"				   from NHOMSANPHAM a inner join ChiTieuNhanVien_DDKD_NHOMSP b on b.nhomsp_fk=a.PK_SEQ  "+  
						"				   where b.ChiTieuNhanVien_fk='"+this.id+"'  		    "+
						"				    FOR XML PATH('')  		    ),' ','],[') + ']' as colID  "+ 
						"from ChiTieuNhanVien where pk_seq = '" + this.id + "'";
		System.out.println("__Khoi tao tieu chi thuong: " + query);
		ResultSet rs = db.get(query);
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					this.scheme = rs.getString("ma");
					this.thang = rs.getString("Thang")==null?"":rs.getString("Thang");
					this.nam = rs.getString("nam");
					this.diengiai = rs.getString("ten");
					this.quy=rs.getString("quy")==null?"":rs.getString("quy");
					colID=rs.getString("colID")==null?"[0]":rs.getString("colID");
					this.apdung=rs.getString("apdung")==null?"":rs.getString("apdung");
					this.loaichitieu=rs.getString("loaichitieu")==null?"":rs.getString("loaichitieu");
				}
				rs.close();
				query=
						"	select  distinct b.nhomsp_fk,a.TEN from NHOMSANPHAM a inner join ChiTieuNhanVien_DDKD_NHOMSP b on b.nhomsp_fk=a.PK_SEQ  where b.ChiTieuNhanVien_fk='"+this.id+"' ";
				this.nhomRs=  this.db.getScrol(query);
				rs =db.get(query);
				String spNhomId__="";
				String spNhomTEN__="";

				int i=0;
				try
				{
					while(rs.next())
					{
						if(i==0)
						{
							spNhomId__= rs.getString("nhomsp_fk");
							spNhomTEN__ = rs.getString("TEN");
						}else 
						{
							spNhomId__ +="__#__"+ rs.getString("nhomsp_fk");
							spNhomTEN__ +="__#__" + rs.getString("TEN");
						}
						i++;
					}
					if(spNhomId__.length()>0)
					{
						nhomId=spNhomId__.split("__#__");
						nhomTen=spNhomTEN__.split("__#__");
					}
					if(rs!=null)rs.close();
				} catch (SQLException e)
				{
					e.printStackTrace();
				}


				query=
						"	select c.smartId as nvId ,c.PK_SEQ as nvbhID,c.TEN as nvTen,c.DIACHI as nvDiaChi,c.SMARTID as nvMa ,a.ChiTieu,a.SoDonHang,a.GiaTriTBDonHang,a.SoKhachHangMoi,a.SoKhachHangMuaHang,"+colID+"   "+
								"				from ChiTieuNhanVien_DDKD a left join  "+ 
								"				(  "+
								"					select ChiTieuNhanVien_FK,DDKD_fk,"+colID+"  "+
								"					from "+ 
								"					(  "+
								"						select ChiTieuNhanVien_FK,DDKD_fk,CHITIEU,nhomsp_fk  "+
								"						from ChiTieuNhanVien_DDKD_NHOMSP   "+
								"					) as nhom pivot  ( sum(CHITIEU) for nhomsp_fk in ( "+colID+"  )) as t "+
								"				)as b on a.ChiTieuNhanVien_FK=b.ChiTieuNhanVien_FK and a.ddkd_fk=b.ddkd_fk  "+
								"				inner join DAIDIENKINHDOANH c on c.PK_SEQ=a.DDKD_fk  " +
								"  where    a.ChiTieuNhanVien_FK='"+this.id+"' order by c.ten ";

				this.tdvRs = db.get(query);


				query=
						"	select c.MA as nvId,c.TEN as nvTen,c.DIACHI as nvDiaChi,(a.ChiTieu) as  ChiTieu,(a.SoDonHang) as SoDonHang,(a.GiaTriTBDonHang) as GiaTriTBDonHang ,(a.SoKhachHangMoi) as SoKhachHangMoi,(a.SoKhachHangMuaHang) as SoKhachHangMuaHang ,"+colID+"   "+
								"	from  " +
								"		(" +
								"			select ChiTieuNhanVien_FK,a.NPP_FK,  sum(a.ChiTieu) as  ChiTieu,sum(a.SoDonHang) as SoDonHang,sum(a.GiaTriTBDonHang) as GiaTriTBDonHang ,sum(a.SoKhachHangMoi) as SoKhachHangMoi,sum(a.SoKhachHangMuaHang) as SoKhachHangMuaHang " +
								"				from  ChiTieuNhanVien_DDKD a inner join DAIDIENKINHDOANH b on a.ddkd_fk = b.pk_seq  " +
								"				where a.ChiTieuNhanVien_FK='"+this.id+"' " +
								"			 group by  ChiTieuNhanVien_FK,a.NPP_FK  		" +
								"			) as a   left join  "+ 
								"				(  "+
								"					select ChiTieuNhanVien_FK,NPP_FK,"+colID+"    "+
								"					from "+ 
								"					(  "+
								"						select ChiTieuNhanVien_FK,NPP_FK,CHITIEU,nhomsp_fk  "+
								"						from ChiTieuNhanVien_DDKD_NHOMSP   "+
								"					) as nhom pivot  ( sum(CHITIEU) for nhomsp_fk in ( "+colID+"   )) as t "+
								"				)as b on a.ChiTieuNhanVien_FK=b.ChiTieuNhanVien_FK and a.NPP_FK=b.NPP_FK  "+
								"				inner join NHAPHANPHOI c on c.PK_SEQ=a.NPP_FK  " +
								"  where    a.ChiTieuNhanVien_FK='"+this.id+"' order by c.ten    " ;


				System.out.println("[ChiTieuNhanVien_NPP]"+query);
				this.NppCtRs = db.get(query);
				
				query=
						"	select c.smartId as nvId ,c.PK_SEQ as nvbhID,c.TEN as nvTen,c.DIACHI as nvDiaChi,c.SMARTID as nvMa ,(a.ChiTieu) as  ChiTieu,(a.SoDonHang) as SoDonHang,(a.GiaTriTBDonHang) as GiaTriTBDonHang ,(a.SoKhachHangMoi) as SoKhachHangMoi,(a.SoKhachHangMuaHang) as SoKhachHangMuaHang ,"+colID+"   "+
								"	from  " +
								"		(" +
								"			select ChiTieuNhanVien_FK,GSBH_FK,  sum(a.ChiTieu) as  ChiTieu,sum(a.SoDonHang) as SoDonHang,sum(a.GiaTriTBDonHang) as GiaTriTBDonHang ,sum(a.SoKhachHangMoi) as SoKhachHangMoi,sum(a.SoKhachHangMuaHang) as SoKhachHangMuaHang " +
								"				from  ChiTieuNhanVien_DDKD a inner join DAIDIENKINHDOANH b on a.ddkd_fk = b.pk_seq  " +
								"				where a.ChiTieuNhanVien_FK='"+this.id+"' " +
								"			 group by  ChiTieuNhanVien_FK,GSBH_FK  		" +
								"			) as a   left join  "+ 
								"				(  "+
								"					select ChiTieuNhanVien_FK,GSBH_FK,"+colID+"    "+
								"					from "+ 
								"					(  "+
								"						select ChiTieuNhanVien_FK,GSBH_FK,CHITIEU,nhomsp_fk  "+
								"						from ChiTieuNhanVien_DDKD_NHOMSP   "+
								"					) as nhom pivot  ( sum(CHITIEU) for nhomsp_fk in ( "+colID+"   )) as t "+
								"				)as b on a.ChiTieuNhanVien_FK=b.ChiTieuNhanVien_FK and a.GSBH_FK=b.GSBH_FK  "+
								"				inner join GIAMSATBANHANG c on c.PK_SEQ=a.GSBH_FK " +
								"  where    a.ChiTieuNhanVien_FK='"+this.id+"' order by c.ten    " ;


				System.out.println("[ChiTieuNhanVien_GSBH_FK_NHOMSP]"+query);
				this.SsRs = db.get(query);


				query=
						" select c.smartId as nvId ,c.PK_SEQ as nvbhID,c.TEN as nvTen,c.DIACHI as nvDiaChi,c.SMARTID as nvMa ,a.ChiTieu,a.SoDonHang,a.GiaTriTBDonHang,a.SoKhachHangMoi,a.SoKhachHangMuaHang,"+colID+"   "+
								"	from	(" +
								"			select ChiTieuNhanVien_FK,ASM_FK,  sum(a.ChiTieu) as  ChiTieu,sum(a.SoDonHang) as SoDonHang,sum(a.GiaTriTBDonHang) as GiaTriTBDonHang ,sum(a.SoKhachHangMoi) as SoKhachHangMoi,sum(a.SoKhachHangMuaHang) as SoKhachHangMuaHang " +
								"				from  ChiTieuNhanVien_ASM a inner join DAIDIENKINHDOANH b on a.ddkd_fk = b.pk_seq  " +
								"				where a.ChiTieuNhanVien_FK='"+this.id+"' " +
								"			 group by  ChiTieuNhanVien_FK,ASM_FK  		" +
								"			) as a   left join  "+  
								"				(  "+
								"					select ChiTieuNhanVien_FK,ASM_FK,"+colID+" "+
								"					from "+ 
								"					(  "+
								"						select ChiTieuNhanVien_FK,ASM_FK,CHITIEU,nhomsp_fk  "+
								"						from ChiTieuNhanVien_ASM_NHOMSP   "+
								"					) as nhom pivot  ( sum(CHITIEU) for nhomsp_fk in ("+colID+"   )) as t "+
								"				)as b on a.ChiTieuNhanVien_FK=b.ChiTieuNhanVien_FK and a.ASM_FK=b.ASM_FK  "+
								"				inner join ASM c on c.PK_SEQ=a.ASM_FK  " +
								"  where    a.ChiTieuNhanVien_FK='"+this.id+"' order by c.ten  ";
				System.out.println("[ChiTieuNhanVien_ASM_NHOMSP]"+query);
				this.AsmRs = db.get(query);


				query=								
						"	select c.smartId as nvId ,c.PK_SEQ as nvbhID,c.TEN as nvTen,c.DIACHI as nvDiaChi,c.SMARTID as nvMa ,a.ChiTieu,a.SoDonHang,a.GiaTriTBDonHang,a.SoKhachHangMoi,a.SoKhachHangMuaHang,"+colID+"  "+
								"	from	(" +
								"			select ChiTieuNhanVien_FK,BM_FK,  sum(a.ChiTieu) as  ChiTieu,sum(a.SoDonHang) as SoDonHang,sum(a.GiaTriTBDonHang) as GiaTriTBDonHang ,sum(a.SoKhachHangMoi) as SoKhachHangMoi,sum(a.SoKhachHangMuaHang) as SoKhachHangMuaHang " +
								"				from  ChiTieuNhanVien_BM a inner join DAIDIENKINHDOANH b on a.ddkd_fk = b.pk_seq  " +
								"				where a.ChiTieuNhanVien_FK='"+this.id+"' " +
								"			 group by  ChiTieuNhanVien_FK,BM_FK  		" +
								"			) as a   left join  "+  
								"				(  "+
								"					select ChiTieuNhanVien_FK,BM_FK,"+colID+"   "+
								"					from "+ 
								"					(  "+
								"						select ChiTieuNhanVien_FK,BM_FK,CHITIEU,nhomsp_fk  "+
								"						from ChiTieuNhanVien_BM_NHOMSP   "+
								"					) as nhom pivot  ( sum(CHITIEU) for nhomsp_fk in ("+colID+"  )) as t "+
								"				)as b on a.ChiTieuNhanVien_FK=b.ChiTieuNhanVien_FK and a.BM_FK=b.BM_FK  "+
								"				inner join BM c on c.PK_SEQ=a.BM_FK  " +
								"  where    a.ChiTieuNhanVien_FK='"+this.id+"'    order by c.ten ";
				System.out.println("[ChiTieuNhanVien_BM_NHOMSP]"+query);
				this.RsmRs = db.get(query);
			} catch (Exception e)
			{
				e.printStackTrace();
				System.out.println("115.Error Meg: " + e.getMessage());
			}
		}
		this.createRs();
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public String getScheme()
	{
		return this.scheme;
	}

	public void setScheme(String scheme)
	{
		this.scheme = scheme;
	}


	public String[] getTumuc()
	{

		return this.tumuc;
	}

	public void setTumuc(String[] tumuc)
	{

		this.tumuc = tumuc;
	}

	public String[] getDenmuc()
	{

		return this.denmuc;
	}

	public void setDenmuc(String[] denmuc)
	{

		this.denmuc = denmuc;
	}

	public void createRs()
	{
		String query = "select PK_SEQ, MA, TEN, TRANGTHAI, NHANHANG_FK, CHUNGLOAI_FK  from SanPham where trangthai = '1' ";

		if (this.id.trim().length() > 0)
		{
			query += " union select PK_SEQ, MA, TEN, TRANGTHAI, NHANHANG_FK, CHUNGLOAI_FK from SanPham where pk_seq in ( select sanpham_fk from ChiTieuNhanVien_SANPHAM where ChiTieuNhanVien_fk = '" + this.id + "' ) ";
		}

		query += " order by TRANGTHAI desc, NHANHANG_FK asc, CHUNGLOAI_FK asc ";
		this.sanphamRs = db.getScrol(query);

		this.vungRs = db.get("select pk_seq, ten from VUNG where trangthai = '1'");

		query = "select pk_seq, ten from KHUVUC where trangthai = '1'";
		if (this.vungIds.trim().length() > 0)
			query += " and vung_fk in ( " + this.vungIds + " ) ";
		this.kvRs = db.get(query);

		query="select TLNTC_FK,dbo.trim(TenKy) as  TenKy,Thang,Nam,TuNgay,DenNgay from Ky";
		this.kyRs=this.db.get(query);

		query="select TLNTC_FK,Ten,Quy,Nam,TuNgay,DenNgay from Quy";
		this.quyRs=this.db.get(query);

		query= " select PK_SEQ,TEN from NHOMSANPHAM where loainhom=0 and TYPE=1 ";
		this.nhomRs=this.db.get(query);

	}

	public void setSanphamRs(ResultSet spRs)
	{

		this.sanphamRs = spRs;
	}

	public ResultSet getSanphamRs()
	{

		return this.sanphamRs;
	}

	public String getSpIds()
	{

		return this.spIds;
	}

	public void setSpIds(String spIds)
	{

		this.spIds = spIds;
	}

	public void setNppRs(ResultSet nppRs)
	{

		this.nppRs = nppRs;
	}

	public ResultSet getNppRs()
	{

		return this.nppRs;
	}

	public String getNppIds()
	{

		return this.nppIds;
	}

	public void setNppIds(String nppIds)
	{

		this.nppIds = nppIds;
	}
	public void setVungRs(ResultSet vungRs)
	{

		this.vungRs = vungRs;
	}

	public ResultSet getVungRs()
	{

		return this.vungRs;
	}

	public String getVungIds()
	{

		return this.vungIds;
	}

	public void setVungIds(String vungIds)
	{

		this.vungIds = vungIds;
	}

	public void setKhuvucRs(ResultSet kvRs)
	{

		this.kvRs = kvRs;
	}

	public ResultSet getKhuvucRs()
	{

		return this.kvRs;
	}

	public String getKhuvucIds()
	{

		return this.kvIds;
	}

	public void setKhuvucIds(String kvIds)
	{

		this.kvIds = kvIds;
	}

	String doituong,quy,apdung;

	public String getQuy()
	{
		return quy;
	}

	public void setQuy(String quy)
	{
		this.quy = quy;
	}

	public String getApdung()
	{
		return apdung;
	}

	public void setApdung(String apdung)
	{
		this.apdung = apdung;
	}

	public String getDoituong()
	{
		return doituong;
	}

	public void setDoituong(String doituong)
	{
		this.doituong = doituong;
	}
	String[] donvi_tinh_ds,donvi_tinh_thuong,thuong;

	public String[] getThuong()
	{
		return thuong;
	}

	public void setThuong(String[] thuong)
	{
		this.thuong = thuong;
	}

	public String[] getDonvi_tinh_ds()
	{
		return donvi_tinh_ds;
	}

	public void setDonvi_tinh_ds(String[] donvi_tinh_ds)
	{
		this.donvi_tinh_ds = donvi_tinh_ds;
	}

	public String[] getDonvi_tinh_thuong()
	{
		return donvi_tinh_thuong;
	}

	public void setDonvi_tinh_thuong(String[] donvi_tinh_thuong)
	{
		this.donvi_tinh_thuong = donvi_tinh_thuong;
	} 

	Hashtable<String, String> npp_chitieu= new Hashtable<String, String>();
	Hashtable<String, String> npp_donvi_chitieu= new Hashtable<String, String>();
	public Hashtable<String, String> getNpp_donvi_chitieu()
	{
		return npp_donvi_chitieu;
	}

	public void setNpp_donvi_chitieu(Hashtable<String, String> npp_donvi_chitieu)
	{
		this.npp_donvi_chitieu = npp_donvi_chitieu;
	}

	public Hashtable<String, String> getNpp_chitieu()
	{
		return npp_chitieu;
	}

	public void setNpp_chitieu(Hashtable<String, String> npp_chitieu)
	{
		this.npp_chitieu = npp_chitieu;
	}

	String loaichitieu;

	public String getLoaichitieu()
	{
		return loaichitieu;
	}

	public void setLoaichitieu(String loaichitieu)
	{
		this.loaichitieu = loaichitieu;
	}

	String view;

	public String getView()
	{
		return view;
	}

	public void setView(String view)
	{
		this.view = view;
	}

	String tructhuocId;
	@Override
	public String getTructhuocId()
	{
		return tructhuocId;
	}

	@Override
	public void setTructhuocId(String tructhuocId)
	{
		this.tructhuocId=tructhuocId;
	}

	String tdvIds;
	ResultSet tdvRs;

	public String getTdvIds()
	{
		return tdvIds;
	}

	public void setTdvIds(String tdvIds)
	{
		this.tdvIds = tdvIds;
	}

	public ResultSet getTdvRs()
	{
		return tdvRs;
	}

	public void setTdvRs(ResultSet tdvRs)
	{
		this.tdvRs = tdvRs;
	}

	Hashtable<String, String> tdv_chitieu= new Hashtable<String, String>();
	public Hashtable<String, String> getTdv_chitieu()
	{
		return tdv_chitieu;
	}

	public void setTdv_chitieu(Hashtable<String, String> tdv_chitieu)
	{
		this.tdv_chitieu = tdv_chitieu;
	}

	Hashtable<String, String> tdv_donvi_chitieu= new Hashtable<String, String>();

	public Hashtable<String, String> getTdv_donvi_chitieu()
	{
		return tdv_donvi_chitieu;
	}

	public void setTdv_donvi_chitieu(Hashtable<String, String> tdv_donvi_chitieu)
	{
		this.tdv_donvi_chitieu = tdv_donvi_chitieu;
	}




	ResultSet AsmRs,RsmRs,SsRs;

	public ResultSet getAsmRs()
	{
		return AsmRs;
	}

	public void setAsmRs(ResultSet asmRs)
	{
		AsmRs = asmRs;
	}

	public ResultSet getRsmRs()
	{
		return RsmRs;
	}

	public void setRsmRs(ResultSet rsmRs)
	{
		RsmRs = rsmRs;
	}

	public ResultSet getSsRs()
	{
		return SsRs;
	}

	public void setSsRs(ResultSet ssRs)
	{
		SsRs = ssRs;
	}

	Hashtable<String, String> Rsm_chitieu= new Hashtable<String, String>();
	public Hashtable<String, String> getRsm_chitieu()
	{
		return Rsm_chitieu;
	}

	public void setRsm_chitieu(Hashtable<String, String> Rsm_chitieu)
	{
		this.Rsm_chitieu = Rsm_chitieu;
	}

	Hashtable<String, String> asm_chitieu= new Hashtable<String, String>();
	public Hashtable<String, String> getAsm_chitieu()
	{
		return asm_chitieu;
	}
	public void setAsm_chitieu(Hashtable<String, String> asm_chitieu)
	{
		this.asm_chitieu = asm_chitieu;
	}

	Hashtable<String, String> ss_chitieu= new Hashtable<String, String>();
	public Hashtable<String, String> getSs_chitieu()
	{
		return ss_chitieu;
	}

	public void setSs_chitieu(Hashtable<String, String> ss_chitieu)
	{
		this.ss_chitieu = ss_chitieu;
	}

	String rsmIds,asmIds,ssIds;

	public String getRsmIds()
	{
		return rsmIds;
	}

	public void setRsmIds(String rsmIds)
	{
		this.rsmIds = rsmIds;
	}

	public String getAsmIds()
	{
		return asmIds;
	}

	public void setAsmIds(String asmIds)
	{
		this.asmIds = asmIds;
	}

	public String getSsIds()
	{
		return ssIds;
	}

	public void setSsIds(String ssIds)
	{
		this.ssIds = ssIds;
	}


	ResultSet kyRs,quyRs;

	public ResultSet getKyRs()
	{
		return kyRs;
	}

	public void setKyRs(ResultSet kyRs)
	{
		this.kyRs = kyRs;
	}

	public ResultSet getQuyRs()
	{
		return quyRs;
	}

	public void setQuyRs(ResultSet quyRs)
	{
		this.quyRs = quyRs;
	}

	public String getSql()
	{
		return sql;
	}

	public void setSql(String sql)
	{
		this.sql = sql;
	}

	public String getSqlNhom()
	{
		return sqlNhom;
	}

	public void setSqlNhom(String sqlNhom)
	{
		this.sqlNhom = sqlNhom;
	}

	String sql,sqlNhom;

	ResultSet nhomRs;
	String[] nhomId,nhomTen;

	public ResultSet getNhomRs()
	{
		return nhomRs;
	}

	public void setNhomRs(ResultSet nhomRs)
	{
		this.nhomRs = nhomRs;
	}

	public String[] getNhomId()
	{
		return nhomId;
	}

	public void setNhomId(String[] nhomId)
	{
		this.nhomId = nhomId;
	}

	public String[] getNhomTen()
	{
		return nhomTen;
	}

	public void setNhomTen(String[] nhomTen)
	{
		this.nhomTen = nhomTen;
	}

	@Override
	public void setNppCtRs(ResultSet nppctRs) {
		this.NppCtRs = nppctRs;
	}

	@Override
	public ResultSet getNppCtRs() {
		return this.NppCtRs;
	}



}
