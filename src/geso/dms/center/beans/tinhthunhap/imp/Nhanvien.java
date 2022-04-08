package geso.dms.center.beans.tinhthunhap.imp;

import geso.dms.center.beans.tinhthunhap.INhanvien;

public class Nhanvien implements INhanvien 
{
	String nppTen;
	String id;
	String ten;
	String luongcb;
	String chucvu;
	
	public Nhanvien()
	{
		this.nppTen = "";
		this.id = "";
		this.ten = "";
		this.luongcb = "";
		this.chucvu = "";
	}

	public String getId() 
	{
		return this.id;
	}

	public void setId(String id) 
	{
		this.id = id;
	}

	public String getTen() 
	{
		return this.ten;
	}

	public void setTen(String ten)
	{
		this.ten = ten;
	}

	public String getLuongCB() 
	{
		return this.luongcb;
	}

	public void setLuongCB(String luongcb) 
	{
		this.luongcb = luongcb;
	}

	public String getChucVu()
	{
		return this.chucvu;
	}

	public void setChucVu(String chucvu)
	{
		this.chucvu = chucvu;
	}

	public String getNppTen() 
	{
		return this.nppTen;
	}

	public void setNppTen(String nppTen)
	{
		this.nppTen = nppTen;
	}
	
	
}
