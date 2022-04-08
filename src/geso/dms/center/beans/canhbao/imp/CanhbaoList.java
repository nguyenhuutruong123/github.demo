package geso.dms.center.beans.canhbao.imp;

import geso.dms.center.beans.canhbao.ICanhbaoList;
import geso.dms.center.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CanhbaoList implements ICanhbaoList 
{
	String userId;
	String ma;
	String diengiai;
	String trangthai; 
	String msg;
	
	ResultSet CanhbaoRs;
	
	dbutils db;
	
	public CanhbaoList()
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
			sql = "select a.pk_seq, a.noidung, a.loaithongbao, a.ngaytao    " +
				  "from CanhBao a inner join CanhBao_NhanVien b on a.pk_seq = b.canhbao_fk " +
				  "where b.nhanvien_fk = '" + userId + "' " +
				  "order by a.pk_seq desc";
		}
		
		System.out.println("__Kho TT : " + sql);
		this.CanhbaoRs = db.get(sql);
	}

	public void DbClose() 
	{
		try 
		{
			if(this.CanhbaoRs != null)
				this.CanhbaoRs.close();
			this.db.shutDown();
		} 
		catch (SQLException e) {}
	}

	public ResultSet getCanhbaoRs() 
	{
		return this.CanhbaoRs;
	}

	public void setCanhbaoRs(ResultSet CanhbaoRs) 
	{
		this.CanhbaoRs = CanhbaoRs;
	}

	

}
