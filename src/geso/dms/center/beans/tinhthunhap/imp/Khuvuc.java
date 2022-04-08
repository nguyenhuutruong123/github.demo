package geso.dms.center.beans.tinhthunhap.imp;

import geso.dms.center.beans.tinhthunhap.IKhuvuc;

public class Khuvuc implements IKhuvuc 
{
	String id;
	String ten;
	
	public Khuvuc()
	{
		this.id = "";
		this.ten = "";
	}
	
	public Khuvuc(String id, String ten)
	{
		this.id = id;
		this.ten = ten;
	}

	public String getId() 
	{
		return this.id;
	}

	public void setId(String Id) 
	{
		this.id = Id;
	}

	public String getTen() 
	{
		return this.ten;
	}

	public void setTen(String ten) 
	{
		this.ten = ten;
	}
	
	
}
