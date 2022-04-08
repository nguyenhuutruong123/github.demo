package geso.dms.center.beans.nhomnhaphanphoi.imp;

import geso.dms.center.beans.nhomnhaphanphoi.INhomNhaPhanPhoi;
import geso.dms.center.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NhomNhaPhanPhoi implements INhomNhaPhanPhoi
{

	public NhomNhaPhanPhoi(String id)
	{
		super();
		this.id = id;
		this.nppId = "";
		this.userId = "";
		this.vungId = "";
		this.kvId = "";
		this.ma = "";
		this.ten = "";
		this.kenhId = "";
		this.loainhom = "";
		this.nhomId = "";
		this.msg = "";
		this.tinhId = "";
		this.quanhuyenId = "";
		this.db = new dbutils();
	}

	public NhomNhaPhanPhoi(String id, String nppId, String userId, String vungId, String kvId, String ma, String ten, String kenhId, String loainhom, String nhomId, String msg, String tinhId, String quanhuyenId, ResultSet nppRs, ResultSet vungRs,
			ResultSet kvRs, ResultSet kenhRs, ResultSet nhomRs, ResultSet tinhRs, ResultSet quanhuyenRs, dbutils db)
	{
		super();
		this.id = id;
		this.nppId = nppId;
		this.userId = userId;
		this.vungId = vungId;
		this.kvId = kvId;
		this.ma = ma;
		this.ten = ten;
		this.kenhId = kenhId;
		this.loainhom = loainhom;
		this.nhomId = nhomId;
		this.msg = msg;
		this.tinhId = tinhId;
		this.quanhuyenId = quanhuyenId;
		this.nppRs = nppRs;
		this.vungRs = vungRs;
		this.kvRs = kvRs;
		this.kenhRs = kenhRs;
		this.nhomRs = nhomRs;
		this.tinhRs = tinhRs;
		this.quanhuyenRs = quanhuyenRs;
		this.db = db;
	}
	public NhomNhaPhanPhoi()
	{
		this.id = "";
		this.ma = "";
		this.ten = "";
		this.nppId = "";
		this.userId = "";
		this.vungId = "";
		this.kvId = "";
		this.nhomId = "";
		this.loainhom = "2";
		this.msg = "";
		this.kenhId = "";
		this.tinhId = "";
		this.quanhuyenId = "";
		this.db = new dbutils();
	}

	public NhomNhaPhanPhoi(String id, String nppId, String userId, String vungId, String kvId, String ma, String ten, ResultSet nppRs, ResultSet vungRs, ResultSet kvRs)
	{

		this.id = id;
		this.nppId = nppId;
		this.userId = userId;
		this.vungId = vungId;
		this.kvId = kvId;
		this.ma = ma;
		this.ten = ten;
		this.nppRs = nppRs;
		this.vungRs = vungRs;
		this.kvRs = kvRs;
		db = new dbutils();
	}

	public NhomNhaPhanPhoi(String id, String nppId, String userId, String vungId, String kvId, String ma, String ten)
	{
		this.id = id;
		this.nppId = nppId;
		this.userId = userId;
		this.vungId = vungId;
		this.kvId = kvId;
		this.ma = ma;
		this.ten = ten;
		db = new dbutils();
	}
	String id, nppId, userId, vungId, kvId, ma, ten, kenhId, loainhom, nhomId, msg, tinhId, quanhuyenId;
	ResultSet nppRs, vungRs, kvRs, kenhRs, nhomRs, tinhRs, quanhuyenRs;

	public String getTinhId()
	{
		return tinhId;
	}

	public void setTinhId(String tinhId)
	{
		this.tinhId = tinhId;
	}

	public String getQuanhuyenId()
	{
		return quanhuyenId;
	}

	public void setQuanhuyenId(String quanhuyenId)
	{
		this.quanhuyenId = quanhuyenId;
	}

	public ResultSet getTinhRs()
	{
		return tinhRs;
	}

	public void setTinhRs(ResultSet tinhRs)
	{
		this.tinhRs = tinhRs;
	}

	public ResultSet getQuanhuyenRs()
	{
		return quanhuyenRs;
	}

	public void setQuanhuyenRs(ResultSet quanhuyenRs)
	{
		this.quanhuyenRs = quanhuyenRs;
	}

	public String getNhomId()
	{
		return nhomId;
	}

	public void setNhomId(String nhomId)
	{
		this.nhomId = nhomId;
	}

	public ResultSet getNhomRs()
	{
		return nhomRs;
	}

	public void setNhomRs(ResultSet nhomRs)
	{
		this.nhomRs = nhomRs;
	}

	public String getLoainhom()
	{
		return loainhom;
	}

	public void setLoainhom(String loainhom)
	{
		this.loainhom = loainhom;
	}

	dbutils db;

	public String getKenhId()
	{
		return kenhId;
	}

	public void setKenhId(String kenhId)
	{
		this.kenhId = kenhId;
	}

	public ResultSet getKenhRs()
	{
		return kenhRs;
	}

	public void setKenhRs(ResultSet kenhRs)
	{
		this.kenhRs = kenhRs;
	}

	

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getNppId()
	{
		return nppId;
	}

	public void setNppId(String nppId)
	{
		this.nppId = nppId;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getVungId()
	{
		return vungId;
	}

	public void setVungId(String vungId)
	{
		this.vungId = vungId;
	}

	public String getKvId()
	{
		return kvId;
	}

	public void setKvId(String kvId)
	{
		this.kvId = kvId;
	}

	public String getMa()
	{
		return ma;
	}

	public void setMa(String ma)
	{
		this.ma = ma;
	}

	public String getTen()
	{
		return ten;
	}

	public void setTen(String ten)
	{
		this.ten = ten;
	}

	@Override
	public String getMsg()
	{

		return this.msg;
	}

	@Override
	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	@Override
	public boolean save()
	{
		String query="insert into NhomNpp(Ma,Ten,NguoiTao,NgayTao,NguoiSua,NgaySua)" +
				"values (N'"+this.ma+"',N'"+this.ten+"','"+this.userId+"','"+getDateTime()+"','"+this.userId+"','"+getDateTime()+"')  ";
		try
		{
			ResultSet rsCheck = db.get("select COUNT(PK_SEQ) as sodong from nhomNpp where  upper(ma) = upper('" + this.ma + "')");
			boolean chk = true;
			if(rsCheck != null)
			{
				if(rsCheck.next())
				{
					if(rsCheck.getInt("sodong") > 0)
					{
						chk = false;
					}
					rsCheck.close();
				}
			}
			if(!chk)
			{
				this.msg = "Mã (" + this.ma + ") bạn muốn cập nhật đã tồn tại trong hệ thống, vui lòng nhập mã khác.";
				return false;
			}
			this.db.getConnection().setAutoCommit(false);
			if(!this.db.update(query))
			{
				this.msg="Lỗi hệ thống "+query;
				return false;
			}
			query = "select IDENT_CURRENT('nhomNpp') as nhomId";
			ResultSet rs=this.db.get(query);
			while(rs.next())
				this.id=rs.getString("nhomId");
			rs.close();
			
			query="insert into nhomnpp_npp(nhomnpp_fk,npp_fk)" +
					" select '"+this.id+"',pk_seq" +
					" from nhaphanphoi where pk_seq in ("+this.nppId+")  ";
			if(!this.db.update(query))
			{
				this.msg="Lỗi hệ thống "+query;
				return false;
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean edit()
	{
		
		String query="update NhomNpp  set Ma=N'"+this.ma+"', Ten=N'"+this.ten+"',NguoiSua='"+this.userId+"',NgaySua='"+getDateTime()+"' where pk_Seq='"+this.id+"'" ;
		try
		{
			ResultSet rsCheck = db.get("select COUNT(PK_SEQ) as sodong from nhomNpp where pk_seq != '" + this.id + "' and upper(ma) = upper('" + this.ma + "')");
			boolean chk = true;
			if(rsCheck != null)
			{
				if(rsCheck.next())
				{
					if(rsCheck.getInt("sodong") > 0)
					{
						chk = false;
					}
					rsCheck.close();
				}
			}
			if(!chk)
			{
				this.msg = "Mã (" + this.ma + ") bạn muốn cập nhật đã tồn tại trong hệ thống, vui lòng nhập mã khác.";
				return false;
			}
			this.db.getConnection().setAutoCommit(false);
			if(!this.db.update(query))
			{
				this.msg="Lỗi hệ thống "+query;
				return false;
			}
			query="delete from nhomnpp_npp where nhomnpp_fk='"+this.id+"'";
			if(!this.db.update(query))
			{
				this.msg="Lỗi hệ thống "+query;
				return false;
			}
			
			query="insert into nhomnpp_npp(nhomnpp_fk,npp_fk)" +
					" select '"+this.id+"',pk_seq" +
					" from nhaphanphoi where pk_seq in ("+this.nppId+")  ";
			if(!this.db.update(query))
			{
				this.msg="Lỗi hệ thống "+query;
				return false;
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public void closeDB()
	{

	}

	@Override
	public void init()
	{
		String query="select ma,ten from nhomnpp where pk_seq='"+this.id+"'";
		System.out.println("init nhomnpp: " + query);
		ResultSet rs=this.db.get(query);
		try
		{
			while(rs.next())
			{
				this.ma=rs.getString("ma");
				this.ten=rs.getString("ten");
				this.loainhom="2";
			}rs.close();
			query="select npp_fk from nhomnpp_npp where nhomnpp_fk='"+this.id+"'";
			System.out.println("init nhomnpp_npp: " + query);
			rs=this.db.get(query);
			while(rs.next())
			{
				this.nppId+=rs.getString("npp_fk")+","; // phan nay danh dau Chon
			}
			if(nppId.length()>0)
			{
				nppId=nppId.substring(0,nppId.length()-1);
			}
			createRs();
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}		
	}

	@Override
	public ResultSet getNppRs()
	{

		return this.nppRs;
	}

	@Override
	public void setNppRs(ResultSet NppRs)
	{
		this.nppRs = NppRs;
	}

	@Override
	public ResultSet getKvRs()
	{

		return this.kvRs;
	}

	@Override
	public void setKvRs(ResultSet KvRs)
	{
		this.kvRs = KvRs;

	}

	@Override
	public ResultSet getVungRs()
	{

		return this.vungRs;
	}

	@Override
	public void setVungRs(ResultSet VungRs)
	{
		this.vungRs = VungRs;
	}

	public void createRs()
	{

		String query = "select pk_seq,diengiai,ten from kenhbanhang";
		this.kenhRs = this.db.get(query);
		query = "select PK_SEQ,TEN ,DIENGIAI from VUNG";
		this.vungRs = this.db.get(query);
		query = "select PK_SEQ,TEN,DIENGIAI from KHUVUC where 1=1 ";
		if (this.vungId.length() > 0)
			query += " and khuvuc_fk='" + this.vungId + "'";
		this.kvRs = this.db.get(query);
		query = "select " + 
		"	isnull(kbh.TEN,N'Chưa xác định') as Kenh, " + 
		"	isnull(v.TEN,N'Chưa xác định') as Vung, " + 
		"	isnull(kv.TEN,N'Chưa xác định') as KhuVuc, " + 
		"	isnull(tt.TEN,N'Chưa xác định') as Tinh, "+ 
		"	isnull(qh.TEN,N'Chưa xác định') as QuanHuyen, " + 
		"	npp.PK_SEQ,npp.MA,npp.TEN,npp.DIACHI " + 
		"from NHAPHANPHOI npp " + 
		"left join KHUVUC kv on " + 
		"kv.PK_SEQ=npp.KHUVUC_FK " + 
		"left join VUNG v on v.PK_SEQ=kv.VUNG_FK "+ 
		"left join NHAPP_KBH nppK on nppK.NPP_FK=npp.PK_SEQ " + 
		"left join QUANHUYEN qh on qh.PK_SEQ=npp.QUANHUYEN_FK " + 
		"left join TINHTHANH tt on tt.PK_SEQ=qh.TINHTHANH_FK " + 
		"left join KENHBANHANG kbh on kbh.PK_SEQ=nppK.KBH_FK where 1=1 ";
		query+=" and npp.pk_seq not in (select npp_fk from nhomnpp_npp   ";
		if(id.length()>0)
			query+=" where  nhomnpp_fk!='"+this.id+"'  ";
		query+="  )  ";
		if (this.kvId.length() > 0)
			query += " and npp.khuvuc_fk='" + this.kvId + "'";
		if (this.kenhId.length() > 0)
			query += " and npp.pk_seq in( select npp_fk from NHAPP_KBH where kbh_fk='" + this.kenhId + "' )";
		
		System.out.println("___Init__"+query);		
		this.nppRs = this.db.get(query);
		query = "select pk_seq ,ten from quanhuyen";
		this.quanhuyenRs = this.db.get(query);
		query = "select pk_Seq ,ten from tinhthanh";
		this.tinhRs = this.db.get(query);

	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}
