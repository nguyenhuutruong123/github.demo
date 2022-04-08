package geso.dms.center.beans.nhomctkhuyenmai.imp;

import java.sql.ResultSet;

import geso.dms.center.beans.nhomctkhuyenmai.INhomctkhuyenmaiList;
import geso.dms.center.db.sql.dbutils;

public class NhomctkhuyenmaiList implements INhomctkhuyenmaiList
{
	private String userId;
	private String diengiai;
	private String trangthai;		 
	private String tungay;
	private String denngay;
	private ResultSet nctkmRs;
	dbutils db;
	
	public NhomctkhuyenmaiList()
	{
		this.diengiai = "";
		this.trangthai = "";
		this.tungay = "";
		this.denngay = "";
		db = new dbutils();
	}
	
	public String getDiengiai() 
	{
		return this.diengiai;
	}
	
	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}
	
	public String getTrangthai() 
	{
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
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
	
	public ResultSet getNctkmRs() 
	{
		return this.nctkmRs;
	}
	
	public void setNctkmRs(ResultSet nctkm) 
	{
		this.nctkmRs = nctkm;
	}


	public void createNhomctkmBean(String sql) 
	{
		String query = "";
		if(sql.length() > 0)
			query = sql;
		else
		{
			query = "select a.pk_seq as nkmId, a.tennhom, a.diengiai, a.ngaytao, a.ngaysua, a.trangthai, b.ten as nguoitao, c.ten as nguoisua " +
					"from nhomctkhuyenmai a inner join nhanvien b on a.nguoitao = b.pk_seq inner join nhanvien c on a.nguoisua = c.pk_seq " +
					"order by a.ngaytao desc";
		}
		
		this.nctkmRs = db.get(query);
	}

	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	
	
}
