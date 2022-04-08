package geso.dms.distributor.beans.banggiasieuthi.imp;

import geso.dms.distributor.beans.banggiasieuthi.ISanpham;

public class Sanpham implements ISanpham
{	
	private static final long serialVersionUID = -9217977546733610214L;
	String id;
	String masp;
	String tensp;
	String giabanlechuan;
	String giaban;
	
	public Sanpham()
	{
		this.id = "";
		this.masp = "";
		this.tensp = "";
		this.giabanlechuan = "";
		this.giaban = "";
	}
	
	public Sanpham(String[] param)
	{
		this.id = param[0];
		this.masp = param[1];
		this.tensp = param[2];
		this.giabanlechuan = param[3];
		this.giaban = param[4].replaceAll(",", "");
	}
	
	public String getId() 
	{	
		return this.id;
	}

	public void setId(String id) 
	{
		this.id = id;	
	}
	
	public String getMasanpham()
	{	
		return this.masp;
	}
	
	public void setMasanpham(String masp)
	{
		this.masp = masp;		
	}
	
	public String getTensanpham() 
	{	
		return this.tensp;
	}
	
	public void setTensanpham(String tensp) 
	{
		this.tensp = tensp;	
	}

	public String getGiabanlechuan() 
	{
		return this.giabanlechuan;
	}

	public void setGiabanlechuan(String gblchuan) 
	{
		this.giabanlechuan = gblchuan;
	}

	public String getGiaban() 
	{
		return this.giaban;
	}

	public void setGiaban(String giaban)
	{
		this.giaban = giaban;
	}

	

}
