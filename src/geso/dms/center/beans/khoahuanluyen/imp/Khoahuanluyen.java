package geso.dms.center.beans.khoahuanluyen.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.rmi.CORBA.Util;

import geso.dms.center.beans.khoahuanluyen.IKhoahuanluyen;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

public class Khoahuanluyen implements IKhoahuanluyen 
{
	String userId;
	String id;
	
	String tungay;
	String denngay;
	String trangthai; 
	String tenkhoa;
	String diengiai;
	String msg;
	
	ResultSet nppRs;
	String nppIds;
	ResultSet gsbhRs;
	String gsbhIds;
	ResultSet ddkdRs;
	String ddkdIds;
	
	dbutils db;
	
	public Khoahuanluyen()
	{
		this.userId = "";
		this.id = "";
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.tenkhoa = "";
		this.diengiai = "";
		this.msg = "";
		
		this.nppIds = "";
		this.gsbhIds = "";
		this.ddkdIds = "";
		
		this.db = new dbutils();
	}
	
	public Khoahuanluyen(String id)
	{
		this.userId = "";
		this.id = id;
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.tenkhoa = "";
		this.diengiai = "";
		this.msg = "";
		
		this.nppIds = "";
		this.gsbhIds = "";
		this.ddkdIds = "";
		
		this.db = new dbutils();
	}
	
	public String getId() 
	{
		return this.id;
	}

	public void setId(String Id) 
	{
		this.id = Id;
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;	
	}

	public String getTungay()
	{
		return this.tungay;
	}

	public void setTungay(String tungay) 
	{
		this.tungay = tungay;
	}

	public String getDenngay()
	{
		return this.denngay;
	}

	public void setDenngay(String denngay)
	{
		this.denngay = denngay;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getTenkhoa() 
	{
		return this.tenkhoa;
	}

	public void setTenkhoa(String tenkhoa) 
	{
		this.tenkhoa = tenkhoa;
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

	public void DbClose() 
	{
		
		try 
		{
			if(this.nppRs != null)
				this.nppRs.close();
			if(this.gsbhRs != null)
				this.gsbhRs.close();
			if(this.ddkdRs != null)
				this.ddkdRs.close();
			this.db.shutDown();
		} 
		catch (SQLException e) {}
		
	}

	
	public ResultSet getNppRs()
	{
		return this.nppRs;
	}

	public void setNppRs(ResultSet nppRs) 
	{
		this.nppRs = nppRs;
	}

	public String getNppIds() 
	{
		return this.nppIds;
	}

	public void setNppIds(String nppIds) 
	{
		this.nppIds = nppIds;
	}

	public ResultSet getGsbhRs() 
	{
		return this.gsbhRs;
	}

	public void setGsbhRs(ResultSet gsbhRs)
	{
		this.gsbhRs = gsbhRs;
	}

	public String getGsbhIds() 
	{
		return this.gsbhIds;
	}

	public void setGsbhIds(String gsbhIds)
	{
		this.gsbhIds = gsbhIds;
	}

	public ResultSet getNvbhRs() 
	{
		return this.ddkdRs;
	}

	public void setNvbhRs(ResultSet nvbhlRs) 
	{
		this.ddkdRs = nvbhlRs;
	}

	public String getNvbhIds() 
	{
		return this.ddkdIds;
	}

	public void setNvbhIds(String nvbhIds) 
	{
		this.ddkdIds = nvbhIds;
	}

	
	public boolean createKhl()
	{
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "insert KhoaHuanLuyen(tungay, denngay, tieude, noidung, trangthai, nguoitao, nguoisua, ngaytao, ngaysua) " +
							"values('" + this.tungay + "', '" + this.denngay + "', N'" + this.tenkhoa + "', N'" + this.diengiai + "', '0', '" + this.userId + "', '" + this.userId + "', '" + getDateTime() + "', '" + getDateTime() + "')";
			
			if(!db.update(query))
			{
				this.msg = "Khong the tao moi KhoaHuanLuyen, " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			//lay dkkm current
			String khlCurrent = "";
			query = "select IDENT_CURRENT('KhoaHuanLuyen') as khlId";
			
			ResultSet rsDkkm = this.db.get(query);						
			rsDkkm.next();
			khlCurrent = rsDkkm.getString("khlId");
			rsDkkm.close();
			
			if(this.gsbhIds.length() > 0)
			{
				query = "insert KhoaHuanLuyen_GSBH(khoahuanluyen_fk, gsbh_fk) " +
						"select '" + khlCurrent + "' as khoahuanluyen_fk, pk_seq from GIAMSATBANHANG where PK_SEQ in (" +  this.gsbhIds + ") ";
				if(!db.update(query))
				{
					this.msg = "Khong the tao moi KhoaHuanLuyen, " + query;
					this.db.getConnection().rollback();
					return false;
				}
			}
			
			if(this.ddkdIds.length() > 0)
			{
				query = "insert KhoaHuanLuyen_DDKD(khoahuanluyen_fk, ddkd_fk) " +
						"select '" + khlCurrent + "' as khoahuanluyen_fk, pk_seq from DAIDIENKINHDOANH where PK_SEQ in (" +  this.ddkdIds + ") ";
				if(!db.update(query))
				{
					this.msg = "Khong the tao moi KhoaHuanLuyen, " + query;
					this.db.getConnection().rollback();
					return false;
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			this.msg = "Loi: " + e.getMessage();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}

	
	public boolean updateKhl(String gsbh_hoanthanh, String ddkd_hoanthanh) 
	{
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "update KhoaHuanLuyen set tungay = '" + this.tungay + "', denngay = '" + this.denngay + "', tieude = N'" + this.tenkhoa + "', " +
								"noidung = N'" + this.diengiai + "', nguoisua = '" + this.userId + "', ngaysua = '" + getDateTime() + "' where pk_seq = '" + this.id + "' ";
			
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat KhoaHuanLuyen, " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			query = "delete KhoaHuanLuyen_GSBH where khoahuanluyen_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat KhoaHuanLuyen, " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			if(this.gsbhIds.length() > 0)
			{
				query = "insert KhoaHuanLuyen_GSBH(khoahuanluyen_fk, gsbh_fk) " +
						"select '" + this.id + "' as khoahuanluyen_fk, pk_seq from GIAMSATBANHANG where PK_SEQ in (" +  this.gsbhIds + ") ";
				if(!db.update(query))
				{
					this.msg = "Khong the tao moi KhoaHuanLuyen, " + query;
					this.db.getConnection().rollback();
					return false;
				}
			}
			
			if(gsbh_hoanthanh.trim().length() > 0)
			{
				query = "update KhoaHuanLuyen_GSBH set trangthai = '1' where khoahuanluyen_fk = '" + this.id + "' and gsbh_fk in (" + gsbh_hoanthanh + ") ";
				if(!db.update(query))
				{
					this.msg = "Khong the tao moi KhoaHuanLuyen_GSBH, " + query;
					this.db.getConnection().rollback();
					return false;
				}
			}
			
			
			query = "delete KhoaHuanLuyen_DDKD where khoahuanluyen_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat KhoaHuanLuyen, " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			if(this.ddkdIds.length() > 0)
			{
				query = "insert KhoaHuanLuyen_DDKD(khoahuanluyen_fk, ddkd_fk) " +
						"select '" + this.id + "' as khoahuanluyen_fk, pk_seq from DAIDIENKINHDOANH where PK_SEQ in (" +  this.ddkdIds + ") ";
				if(!db.update(query))
				{
					this.msg = "Khong the tao moi KhoaHuanLuyen, " + query;
					this.db.getConnection().rollback();
					return false;
				}
			}
			
			if(ddkd_hoanthanh.trim().length() > 0)
			{
				query = "update KhoaHuanLuyen_DDKD set trangthai = '1' where khoahuanluyen_fk = '" + this.id + "' and ddkd_fk in (" + ddkd_hoanthanh + ") ";
				if(!db.update(query))
				{
					this.msg = "Khong the tao moi KhoaHuanLuyen_DDKD, " + query;
					this.db.getConnection().rollback();
					return false;
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			this.msg = "Loi: " + e.getMessage();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}

	
	public void createRs() 
	{
		System.out.println("NppIds: " + this.nppIds);
		
		Utility util=new Utility();
		
		if(this.nppIds.length() > 0)
		{
			this.nppRs = db.get("select pk_seq, ma, ten from nhaphanphoi where trangthai = '1' and pk_seq in (" + this.nppIds + ") and pk_seq in  "+ util.quyen_npp(this.userId));
		}
		else
		{
			this.nppRs = db.get("select pk_seq, ma, ten from nhaphanphoi where trangthai = '1' and pk_seq in " + util.quyen_npp(this.userId));
		}
		
		if(this.nppIds.length() > 0)
		{
			String query = "";
			if(this.id.trim().length() > 0)
			{
				query = "select b.pk_seq, b.ten, isnull(dienthoai, 'na') as dienthoai, isnull(email, 'na') as email, isnull(diachi, 'na') as diachi, a.trangthai " +
						"from KhoaHuanLuyen_GSBH a inner join GIAMSATBANHANG b on a.gsbh_fk = b.PK_SEQ " +
						"where a.khoahuanluyen_fk = '" + this.id + "' and b.pk_seq in (select  gsbh_fk from nhapp_giamsatbh where npp_fk in  "+util.quyen_npp(this.userId)+")"+
						" union all ";
			}
			
			query += "select pk_seq, ten, isnull(dienthoai, 'na') as dienthoai, isnull(email, 'na') as email, isnull(diachi, 'na') as diachi, '0' as trangthai " +
					"from GIAMSATBANHANG where PK_SEQ in (select GSBH_FK from NHAPP_GIAMSATBH where NPP_FK  in (" + this.nppIds + "))  and PK_SEQ in (select  gsbh_fk from nhapp_giamsatbh where npp_fk in  "+util.quyen_npp(this.userId)+" ) " ;
			
			
			
			if(this.id.trim().length() > 0)
				query += " and PK_SEQ not in ( select gsbh_fk from KhoaHuanLuyen_GSBH where khoahuanluyen_fk = '" + this.id + "' ) ";
			
			System.out.println("__Khoi tao GSBH: " + query);
			
			
			this.gsbhRs = db.get(query);
			
			String sql = "";
			if(this.id.trim().length() > 0)
			{
				sql = "select b.pk_seq, b.ten, b.dienthoai, b.diachi, a.trangthai, c.TEN as nppTen " +
						"from KhoaHuanLuyen_DDKD a inner join DAIDIENKINHDOANH b on a.ddkd_fk = b.PK_SEQ inner join nhaphanphoi c on b.npp_fk = c.pk_seq " +
						"where a.khoahuanluyen_fk = '" + this.id + "' and c.pk_seq in  " +util.quyen_npp(this.userId) +
						" union all ";
			}
			
			sql += "select a.pk_seq, a.ten, a.dienthoai, a.diachi, '0' as trangthai, b.ten as nppTen " +
					"from daidienkinhdoanh a inner join nhaphanphoi b on a.npp_fk = b.pk_seq where a.npp_fk in (" + this.nppIds + ") and a.npp_fk in "+util.quyen_npp(this.userId);
			
			if(this.id.trim().length() > 0)
				sql += " and a.pk_seq not in ( select ddkd_fk from KhoaHuanLuyen_DDKD where khoahuanluyen_fk = '" + this.id + "' ) ";
			
			System.out.println("__Khoi tao ddkd: " + sql);
			this.ddkdRs = db.get(sql);
		}
		
	}

	
	public void init() 
	{
		String query = "select tungay, denngay, tieude, noidung from KhoaHuanLuyen where pk_seq = '" + this.id + "'";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.tungay = rs.getString("tungay");
					this.denngay = rs.getString("denngay");
					this.tenkhoa = rs.getString("tieude");
					this.diengiai = rs.getString("noidung");	
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("115.Error: " + e.getMessage());
			}
		}
		
		this.initIds();
		this.createRs();
	}
	
	private void initIds()
	{
		try 
		{
			String query = "select gsbh_fk from KhoaHuanLuyen_GSBH where khoahuanluyen_fk = '" + this.id + "'";
			System.out.println("__Khoi tao GSBH: " + query);
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				String gsbh = "";
				while(rs.next())
				{
					gsbh += rs.getString("gsbh_fk") + ",";
				}
				rs.close();
				
				if(gsbh.length() > 0)
					this.gsbhIds = gsbh.substring(0, gsbh.length() - 1);
			}
			
			query = "select ddkd_fk from KhoaHuanLuyen_DDKD where khoahuanluyen_fk = '" + this.id + "'";
			System.out.println("__Khoi tao DDKD: " + query);
			rs = db.get(query);
			if(rs != null)
			{
				String ddkd = "";
				while(rs.next())
				{
					ddkd += rs.getString("ddkd_fk") + ",";
				}
				rs.close();
				
				if(ddkd.length() > 0)
					this.ddkdIds = ddkd.substring(0, ddkd.length() - 1);
			}
			
			//Lay Nha Phan Phoi
			String gsbh = "0";
			if(this.gsbhIds.length() > 0)
				gsbh = this.gsbhIds;
			
			String ddkd = "0";
			if(this.ddkdIds.length() > 0)
				ddkd = this.ddkdIds;
			
			query = "select pk_seq from NHAPHANPHOI where PK_SEQ in (select npp_fk from DAIDIENKINHDOANH where PK_SEQ in (" + ddkd + ") union select NPP_FK from NHAPP_GIAMSATBH where GSBH_FK in (" + gsbh + ")  ) ";
			System.out.println("1.Khoi tao NPP: " + query);
			
			rs = db.get(query);
			if(rs != null)
			{
				String npp = "";
				while(rs.next())
				{
					npp += rs.getString("pk_seq") + ",";
				}
				rs.close();
				
				if(npp.length() > 0)
					this.nppIds = npp.substring(0, npp.length() - 1);
			}
			
		} 
		catch (Exception e) 
		{
			System.out.println("115.Loi: " + e.getMessage());
		}
	}

	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	
}
