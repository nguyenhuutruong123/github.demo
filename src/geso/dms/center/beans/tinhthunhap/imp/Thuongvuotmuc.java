package geso.dms.center.beans.tinhthunhap.imp;

import geso.dms.center.beans.tinhthunhap.IThuongvuotmuc;

public class Thuongvuotmuc implements IThuongvuotmuc 
{
	String ntIds;
	String tumuc;
	String denmuc;
	String thuong;
	
	public Thuongvuotmuc()
	{
		this.ntIds = "";
		this.tumuc = "";
		this.denmuc = "";
		this.thuong = "";
	}
	
	public String getNhomthuong() 
	{
		return this.ntIds;
	}

	public void setNhomthuong(String nhomthuong) 
	{
		this.ntIds = nhomthuong;
	}

	public String getTumuc() 
	{
		return this.tumuc;
	}

	public void setTumuc(String tumuc)
	{
		this.tumuc = tumuc;
	}

	public String getDenmuc() 
	{
		return this.denmuc;
	}

	public void setDenmuc(String denmuc) 
	{
		this.denmuc = denmuc;
	}

	public String getThuong() 
	{
		return this.thuong;
	}

	public void setThuong(String thuong) 
	{
		this.thuong = thuong;
	}

}
