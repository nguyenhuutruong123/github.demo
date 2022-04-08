package geso.dms.center.beans.mucchietkhau.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.dms.center.beans.mucchietkhau.IChietkhau;
import geso.dms.center.db.sql.dbutils;

public class Chietkhau implements IChietkhau
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String id;
	String sotien;
	String chietkhau;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;
	String diengiai;
	
	ResultSet nppRs;
	String nppIds;
	
	dbutils db;
	
	public Chietkhau(String[] param)
	{
		this.db = new dbutils();
		this.id = param[0];
		this.sotien = param[1];
		this.chietkhau = param[2];
		this.ngaytao = param[3];
		this.nguoitao = param[4];
		this.ngaysua = param[5];
		this.nguoisua = param[6];
		this.msg = "";
		this.nppIds = "";
		this.diengiai = "";
		this.db = new dbutils();
	}
	
	public Chietkhau(String id)
	{
		this.db = new dbutils();
		this.id = id;
		this.sotien = "";
		this.chietkhau = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.msg = "";
		this.nppIds = "";
		this.diengiai = "";
		this.db = new dbutils();
		if(id.length() > 0)
			this.init();
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

	public void setId(String id) 
	{
		this.id = id;
	}
	
	public String getSotien() 
	{
		return this.sotien;
	}

	public void setSotien(String sotien) 
	{
		this.sotien = sotien;
	}
	
	public String getChietkhau() 
	{
		return this.chietkhau;
	}

	public void setChietkhau(String chietkhau) 
	{
		this.chietkhau = chietkhau;
	}

	public String getNgaytao()
	{
		return this.ngaytao;
	}

	public void setNgaytao(String ngaytao) 
	{
		this.ngaytao = ngaytao;
	}

	public String getNguoitao() 
	{
		return this.nguoitao;
	}

	public void setNguoitao(String nguoitao) 
	{
		this.nguoitao = nguoitao;
	}

	public String getNgaysua() 
	{
		return this.ngaysua;
	}

	public void setNgaysua(String ngaysua) 
	{
		this.ngaysua = ngaysua;
	}

	public String getNguoisua() 
	{
		return this.nguoisua;
	}

	public void setNguoisua(String nguoisua) 
	{
		this.nguoisua = nguoisua;
	}

	public String getMessage() 
	{
		return this.msg;
	}

	public void setMessage(String msg) 
	{
		this.msg = msg;
	}

	public boolean CreateMck()
	{
		try
		{
			this.db.getConnection().setAutoCommit(false);
			this.ngaytao = getDateTime();
			this.nguoitao = this.userId;
			
			String command = "insert into chietkhau(sotien, mucchietkhau, ngaytao, ngaysua, nguoitao, nguoisua, diengiai) values('" + this.sotien + "','" + this.chietkhau + "','" + this.ngaytao + "','" + this.ngaytao + "','" + this.userId + "','" + this.userId + "', N'" + this.diengiai + "')"; 
			
			if (!db.update(command))
			{
				this.msg = "Khong the tao moi chiet khau: " + command;		
				this.db.getConnection().rollback();
				return false;
			}
			
			//lay dkkm current
			String ckId = "";
			String query = "select IDENT_CURRENT('chietkhau') as ckId";
			
			ResultSet rsCk = this.db.get(query);						
			rsCk.next();
			ckId = rsCk.getString("ckId");
			rsCk.close();
			
			if(this.nppIds.length() > 0)
			{
				String[] npp = this.nppIds.split(",");
				for(int i = 0; i < npp.length; i++)
				{
					query = "insert CHIETKHAU_NPP(chietkhau_fk, npp_fk) values('" + ckId + "','" + npp[i] + "')";
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi chietkhau_npp: " + query;		
						this.db.getConnection().rollback();
						return false;
					}
				}
			}
			
			this.db.getConnection().setAutoCommit(true);
			this.db.getConnection().commit();
		}
		catch (Exception e) {}
		
		return true;
	}

	public boolean UpdateMck() 
	{
		try
		{
			this.db.getConnection().setAutoCommit(false);
			this.ngaysua = getDateTime();
			this.nguoisua = this.userId;
			
			String command ="update chietkhau set sotien = '" + this.sotien + "', mucchietkhau = '" + this.chietkhau + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.userId + "', diengiai = N'" + this.diengiai + "' where pk_seq = '" + this.id + "'";
			
			if (!this.db.update(command))
			{
				this.msg = "Khong the cap nhat chiet khau: " + command;
				this.db.getConnection().rollback();
				return false;
			}
			
			db.update("delete from CHIETKHAU_NPP where chietkhau_fk = '" + this.id + "'");
			
			if(this.nppIds.length() > 0)
			{
				String[] npp = this.nppIds.split(",");
				for(int i = 0; i < npp.length; i++)
				{
					String query = "insert CHIETKHAU_NPP(chietkhau_fk, npp_fk) values('" + this.id + "','" + npp[i] + "')";
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi chietkhau_npp: " + query;		
						this.db.getConnection().rollback();
						return false;
					}
				}
			}
			
			this.db.getConnection().setAutoCommit(true);
			this.db.getConnection().commit();
		}
		catch (Exception e) {}

		return true; 
	}

	private void init()
	{	
		String query = "select a.pk_seq as id, isnull(a.diengiai, 'na') as diengiai, a.sotien, a.mucchietkhau, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua from chietkhau a, nhanvien b, nhanvien c ";
		query = query + " where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.pk_seq = '"+ this.id + "'"; 
        ResultSet rs =  this.db.get(query);
        try
        {
            rs.next();        	
        	this.id = rs.getString("id");
        	this.sotien = rs.getString("sotien");
        	this.chietkhau = rs.getString("mucchietkhau");
        	this.ngaytao = rs.getString("ngaytao");
        	this.nguoitao = rs.getString("nguoitao");
        	this.ngaysua = rs.getString("ngaysua");
        	this.nguoisua = rs.getString("nguoisua");
        	
        	this.diengiai = rs.getString("diengiai");
        	  	
       	}
        catch (java.sql.SQLException e){}
        
        this.createRs();
	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}	

	public void DBClose(){
		if (this.db != null) 
			this.db.shutDown();
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

	public void createRs() 
	{
		String query = "select a.pk_seq as nppId, a.ten as nppTen, 'Khu vuc ' + b.ten as kvTen ";
		query += "from nhaphanphoi a inner join khuvuc b on a.khuvuc_fk = b.pk_seq where a.trangthai = '1' order by b.pk_seq asc, a.ten asc";
		
		//String query = "select distinct a.pk_seq as nppId, a.ten as nppTen, 'Khu vuc ' + b.ten as kvTen from khuvuc b inner join nhaphanphoi a on a.KHUVUC_FK = b.PK_SEQ where a.trangthai = '1'";
		
		System.out.println("Lay Nha pp: " + query + "\n");
		
		this.nppRs = db.get(query);
		
		if(this.id.length() > 0)
		{
			query = "select NPP_FK from CHIETKHAU_NPP where CHIETKHAU_FK = '" + this.id + "'";
			ResultSet rs = db.get(query);
			
			try 
			{
				String kq = "";
				while(rs.next())
				{
					kq += rs.getString("npp_fk") + ",";
				}
				if(kq.length() > 0)
					kq = kq.substring(0, kq.length() - 1);
				this.nppIds = kq;
				
				System.out.println("id la: " + this.nppIds + "\n");
				
			} 
			catch (SQLException e) {}
		}
	}

	public String getDiengiai() 
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai)
	{
		this.diengiai = diengiai;
	}

}
