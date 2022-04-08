package geso.dms.distributor.beans.ctkhuyenmai.imp;

import geso.dms.distributor.beans.ctkhuyenmai.ISanpham;

public class Sanpham implements ISanpham, Comparable<Object>
{
	private String masp;
	private String tensp;
	private int soluongcan;
	private float dongia;
	private float thanhtien;
	private int slAvaiable;
	private float trongso;
	
	private float slThungCan;
	private float slThungAvai;
	private boolean comprareTongluong;
	
	public Sanpham()
	{
		this.masp = "";
		this.tensp = "";
		this.soluongcan = 0;
		this.dongia = 0;
		this.thanhtien = 0;
		this.slAvaiable = 0;
		this.trongso = 0;
		
		this.slThungCan = 0;
		this.slThungAvai = 0;
		this.comprareTongluong = false; //compareTo theo tongtien
	}
	
	public Sanpham(String masp, String tensp, int soluongcan, float dongia, int slAvaiable, boolean comprareTongluong)
	{
		this.masp = masp;
		this.tensp = tensp;
		this.soluongcan = soluongcan;
		this.dongia = dongia;
		this.slAvaiable = slAvaiable;
		this.thanhtien = this.dongia * this.slAvaiable;
		
		this.trongso = 0;
		this.slThungCan = 0;
		this.slThungAvai = 0;
		this.comprareTongluong = false;
	}
	
	public void setMasp(String maSp)
	{
		this.masp = maSp;		
	}

	public String getMasp() 
	{	
		return this.masp;
	}
	
	public void setTensp(String tenSp)
	{
		this.tensp = tenSp;
	}

	public String getTensp() 
	{
		return this.tensp;
	}
	
	public void setSoluongcan(int soluongcan)
	{
		this.soluongcan = soluongcan;		
	}
	
	public int getSoluongcan() 
	{	
		return this.soluongcan;
	}
	
	public void setDongia(float dongia)
	{
		this.dongia = dongia;	
	}
	
	public float getDongia() 
	{		
		return this.dongia;
	}
	
	public void setThanhtien(float thanhtien)
	{
		this.thanhtien = thanhtien;		
	}
	
	public float getThanhtien() 
	{		
		return this.thanhtien;
	}
	
	public void setSoluongAvaiable(int slAvaiable) 
	{
		this.slAvaiable = slAvaiable;		
	}
	
	public int getSoluongAvaiable()
	{		
		return this.slAvaiable;
	}

	public int compareTo(Object obj) 
	{
		float thanhtien = ((Sanpham)obj).getThanhtien();
		int soluong = ((Sanpham)obj).getSoluongcan();
		
		float trongso = ((Sanpham)obj).getTrongso();
		
		if(this.getTrongso() < trongso)
			return 1;
		
		if(this.getTrongso() > trongso)
			return -1;
		
		if(this.getTrongso() == trongso)
		{
			if(this.comprareTongluong == false) 						//CompareTo tongtien
			{
				if(this.getThanhtien() < thanhtien)
					return -1;
				if(this.getThanhtien() > thanhtien)
					return 1;
			}
			else							//CompareTo tongluong
			{
				if(this.getSoluongcan() < soluong)
					return -1;
				if(this.getSoluongcan() > soluong)
					return 1;
			}
			
		}
		
		return 0;
	}

	public void setSoluongThungCan(float slThungAvaiable)
	{
		this.slThungCan = slThungAvaiable;
	}

	public float getSoluongThungCan() 
	{
		return this.slThungCan;
	}

	public void setSoluongThungAvaiable(float slThungAvaiable) 
	{
		this.slThungAvai = slThungAvaiable;
	}

	public float getSoluongThungAvaiable() 
	{
		return this.slThungAvai;
	}
	
	public void setTrongso(float trongso) 
	{
		this.trongso = trongso;
	}

	public float getTrongso() 
	{
		return this.trongso;
	}

}
