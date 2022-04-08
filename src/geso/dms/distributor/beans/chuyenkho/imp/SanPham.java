package geso.dms.distributor.beans.chuyenkho.imp;

import geso.dms.distributor.beans.chuyenkho.ISanPham;

public class SanPham implements ISanPham
{

	String id, ma, ten, soluong, dongia, thanhtien, soluongchuan, dvdlId, quycach,tonkho;

	

	public SanPham(String id, String ma, String ten, String dongia, String dvdlId)
	{
		super();
		this.id = id;
		this.ma = ma;
		this.ten = ten;
		this.dongia = dongia;
		this.dvdlId = dvdlId;
		this.soluong = "";
		this.soluongchuan = "";
		this.thanhtien = "";
	}

	public SanPham(String id, String ma, String ten, String soluong, String dongia, String thanhtien, String soluongchuan, String dvdlId)
	{
		super();
		this.id = id;
		this.ma = ma;
		this.ten = ten;
		this.soluong = soluong;
		this.dongia = dongia;
		this.thanhtien = thanhtien;
		this.soluongchuan = soluongchuan;
		this.dvdlId = dvdlId;
	}

	public SanPham(String id, String ma, String ten, String dvdlId, String soluong, String soluongchuan, String dongia)
	{
		super();
		this.id = id;
		this.ma = ma;
		this.ten = ten;
		this.dvdlId = dvdlId;
		this.soluong = soluong;
		this.soluongchuan = soluongchuan;
		this.dongia = dongia;
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

	public String getDongia()
	{
		return dongia;
	}

	public void setDongia(String dongia)
	{
		this.dongia = dongia;
	}

	public String getThanhtien()
	{
		return thanhtien;
	}

	public void setThanhtien(String thanhtien)
	{
		this.thanhtien = thanhtien;
	}

	public String getSoluongchuan()
	{
		return soluongchuan;
	}

	public void setSoluongchuan(String soluongchuan)
	{
		this.soluongchuan = soluongchuan;
	}

	public String getDvdlId()
	{
		return dvdlId;
	}

	public void setDvdlId(String dvdlId)
	{
		this.dvdlId = dvdlId;
	}

	public String getQuycach()
	{
		return quycach;
	}

	public void setQuycach(String quycach)
	{
		this.quycach = quycach;
	}
	
	public String getTonkho()
	{
		return tonkho;
	}

	public void setTonkho(String tonkho)
	{
		this.tonkho = tonkho;
	}
	
}
