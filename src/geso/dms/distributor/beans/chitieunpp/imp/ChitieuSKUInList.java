package geso.dms.distributor.beans.chitieunpp.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.dms.center.db.sql.dbutils;
import geso.dms.distributor.beans.chitieunpp.IChitieuSKUInList;
import geso.dms.distributor.util.Utility;

public class ChitieuSKUInList implements IChitieuSKUInList 
{
	String userId;
	String nppId;
	String nppTen;
	String sitecode;
	
	String thang;
	String nam;
	String trangthai;
	String msg;
	String diengiai;
	
	ResultSet rsChitieu;
	dbutils db;
	
	public ChitieuSKUInList()
	{
		this.thang = "";
		this.nam = "";
		this.trangthai = "";
		this.msg = "";
		this.diengiai = "";
		
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
	
	private void getNppInfo()
	{		
		Utility util= new Utility();
		this.nppId = util.getIdNhapp(this.userId);
		this.nppTen = util.getTenNhaPP();
		this.sitecode = util.getSitecode();
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

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public ResultSet getChitieuSKUInRs() 
	{
		return this.rsChitieu;
	}

	public void setChitieuSKUInRs(ResultSet ctskuIn) 
	{
		this.rsChitieu = ctskuIn;
	}

	public void init(String query)
	{
		this.getNppInfo();
		
		String sql = "";
		if(query.length() > 0)
			sql = query;
		else
		{
			sql = "select a.thang,a.pk_seq, a.nam, a.diengiai, a.trangthai, a.nhapp_fk, a.ngaytao, a.ngaysua, b.ten as nguoitao, a.ngaytao, c.ten as nguoisua " +
					"from CHITIEUSKUIN a inner join NHANVIEN b on a.nguoitao = b.pk_seq inner join NHANVIEN c on a.nguoisua = c.pk_seq " +
					"where a.nhapp_fk = '" + this.nppId + "'";
		}
		System.out.println(sql);
		this.rsChitieu = db.get(sql);
	}

	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public String getDiengiai() 
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}
	
	public void initTT(String query)
	{
		String sql = "";
		if(query.length() > 0)
			sql = query;
		else
		{
			sql = "select a.pk_seq, a.thang, a.nam, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, a.ngaytao, c.ten as nguoisua " +
				  "from CHITIEUSKUIN_TT a inner join NHANVIEN b on a.nguoitao = b.pk_seq inner join NHANVIEN c on a.nguoisua = c.pk_seq " +
				  "order by a.nam desc, a.thang desc ";
		}
		
		System.out.println("Query khoi tao: " + sql);
		
		this.rsChitieu = db.get(sql);
	}

	public void DbClose() 
	{
		try 
		{
			if(this.db != null)
				this.db.shutDown();
			if(rsChitieu != null)
				rsChitieu.close();
		} 
		catch (Exception e) {}
	}

	
}
