package geso.dms.center.beans.khaosat.imp;

import geso.dms.center.beans.khaosat.IKhaosatList;
import geso.dms.center.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class KhaosatList implements IKhaosatList 
{
	String userId;
	String ma;
	String diengiai;
	String trangthai; 
	String msg;
	
	ResultSet KhaosatRs;
	
	dbutils db;
	
	public KhaosatList()
	{
		this.userId = "";

		this.ma = "";
		this.trangthai = "";
		this.diengiai = "";
		
		this.msg = "";
		
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

	public String getMa() 
	{
		return this.ma;
	}

	public void setMa(String ma) 
	{
		this.ma = ma;
	}
	
	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
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

	public void init(String query) 
	{
		String sql = "";
		
		if(query.length() > 0)
			sql = query;
		else
		{	
			sql = "select a.pk_seq, a.tieude, a.diengiai, a.bophan, a.socauhoi, a.trangthai, b.ten as nguoitao, a.ngaytao, c.ten as nguoisua, a.ngaysua    " +
				  "from KHAOSAT a inner join NhanVien b on a.nguoitao = b.pk_seq    " +
				  		"inner join nhanvien c on a.nguoisua = c.pk_seq  " +
				  "order by a.pk_seq desc";
		}
		
		System.out.println("__Khao sat : " + sql);
		this.KhaosatRs = db.get(sql);
	}

	public void DbClose() 
	{
		try 
		{
			if(this.KhaosatRs != null)
				this.KhaosatRs.close();
			this.db.shutDown();
		} 
		catch (SQLException e) {}
	}

	public ResultSet getKhaosatRs() 
	{
		return this.KhaosatRs;
	}

	public void setKhaosatRs(ResultSet KhaosatRs) 
	{
		this.KhaosatRs = KhaosatRs;
	}

	

}
