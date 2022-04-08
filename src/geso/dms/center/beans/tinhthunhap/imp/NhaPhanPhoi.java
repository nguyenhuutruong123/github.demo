package geso.dms.center.beans.tinhthunhap.imp;

import geso.dms.center.beans.tinhthunhap.INhaPhanPhoi;

public class NhaPhanPhoi implements INhaPhanPhoi
{
	String id;
	String ten;

	public NhaPhanPhoi()
	{
		this.id = "";
		this.ten = "";
	}

	public NhaPhanPhoi(String id, String ten)
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
