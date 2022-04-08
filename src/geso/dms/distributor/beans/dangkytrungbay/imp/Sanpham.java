package geso.dms.distributor.beans.dangkytrungbay.imp;

import java.io.Serializable;

import geso.dms.distributor.beans.dangkytrungbay.ISanpham;

public class Sanpham implements ISanpham, Serializable
{
	String id;
	int soluong; 
	
	public Sanpham()
	{
		this.id = "";
		this.soluong = 0;
	}
	
	public Sanpham(String id, int slg)
	{
		this.id = id;
		this.soluong = slg;
	}
	
	public String getId() 
	{
		return this.id;
	}

	public void setId(String id) 
	{
		this.id = id;
	}

	public int getSoluong() 
	{
		return this.soluong;
	}

	public void setSoluong(int soluong) 
	{
		this.soluong = soluong;
	}

	@Override
	public void DBclose() {
		// TODO Auto-generated method stub
		
	}
	
}
