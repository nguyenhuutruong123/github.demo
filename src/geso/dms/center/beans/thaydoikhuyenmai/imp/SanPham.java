package geso.dms.center.beans.thaydoikhuyenmai.imp;

import geso.dms.center.beans.thaydoikhuyenmai.ISanPham;

public class SanPham implements ISanPham
{

	String id,ma,ten,soluong,batbuoc;

	
	public SanPham(String id, String ma, String ten, String soluong)
	{
		super();
		this.id = id;
		this.ma = ma;
		this.ten = ten;
		this.soluong = soluong;
	}
	public SanPham(String id, String ma, String ten, String soluong, String batbuoc)
	{
		super();
		this.id = id;
		this.ma = ma;
		this.ten = ten;
		this.soluong = soluong;
		this.batbuoc = batbuoc;
	}
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
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
	public String getSoluong()
	{
		return soluong;
	}

	public void setSoluong(String soluong)
	{
		this.soluong = soluong;
	}
	
	public String getBatbuoc()
	{
		return batbuoc;
	}
	public void setBatbuoc(String batbuoc)
	{
		this.batbuoc = batbuoc;
	}
	
}
