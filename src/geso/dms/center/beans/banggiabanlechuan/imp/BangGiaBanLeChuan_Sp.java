package geso.dms.center.beans.banggiabanlechuan.imp;

import geso.dms.center.beans.banggiabanlechuan.IBangGiaBanLeChuan_Sp;

public class BangGiaBanLeChuan_Sp implements IBangGiaBanLeChuan_Sp
{
	String hopdong;
	String id;
	String ma;
	String ten;
	String tenmoi;
	String giaban;
	String giabanNew;
	String donvi;
	String chonban;
	
	String soluong;
	String dongia;
	String ngaygiao;
	String ngaygiaoDen;
	String soluong_dagiao;
	String soluong_conlai;
	
	String dvdlId;
	
	String[] soluong_thaydoigia;
	String[] soluong_thaydoigia_den;
	String[] dongia_thaydoigia;
	String soluong_tang;
	String dongia_moi;
	
	public BangGiaBanLeChuan_Sp()
	{
		this.hopdong = "";
		this.id = "";
		this.ma = "";
		this.ten = "";
		this.tenmoi = "";
		this.giaban = "0";
		this.giabanNew = "0";
		this.donvi = "";
		this.chonban = "";
		
		this.soluong = "";
		this.dongia = "";
		this.ngaygiao = "";
		this.ngaygiaoDen = "";
		this.soluong_dagiao = "";
		this.soluong_conlai = "";
		this.soluong_tang = "";
		this.dongia_moi = "";
		
		this.dvdlId = "";
	}
	
	public String getHopdong()
	{
		
		return this.hopdong;
	}
	
	public void setHopdong(String hopdong)
	{
		
		this.hopdong = hopdong;
	}
	
	public String getIdsp()
	{
		
		return this.id;
	}
	
	public void setIdsp(String idsp)
	{
		
		this.id = idsp;
	}
	
	public String getMasp()
	{
		
		return this.ma;
	}
	
	public void setMasp(String masp)
	{
		
		this.ma = masp;
	}
	
	public String getTensp()
	{
		
		return this.ten;
	}
	
	public void setTensp(String tensp)
	{
		
		this.ten = tensp;
	}
	
	public String getTenNewsp()
	{
		
		return this.tenmoi;
	}
	
	public void setTenNew(String tenNew)
	{
		
		this.tenmoi = tenNew;
	}
	
	public String getGiaban()
	{
		
		return this.giaban;
	}
	
	public void setGiaban(String giaban)
	{
		
		this.giaban = giaban;
	}
	
	public String getDonvi()
	{
		
		return this.donvi;
	}
	
	public void setDonvi(String donvi)
	{
		
		this.donvi = donvi;
	}
	
	public String getChonban()
	{
		
		return this.chonban;
	}
	
	public void setChonban(String chonban)
	{
		
		this.chonban = chonban;
	}
	
	public String getGiabanNew()
	{
		
		return this.giabanNew;
	}
	
	public void setGiabanNew(String giabanNew)
	{
		
		this.giabanNew = giabanNew;
	}
	
	public String getSoluong()
	{
		
		return this.soluong;
	}
	
	public void setSoluong(String soluong)
	{
		
		this.soluong = soluong;
	}
	
	public String getDongia()
	{
		
		return this.dongia;
	}
	
	public void setDongia(String dongia)
	{
		
		this.dongia = dongia;
	}
	
	public String getNgaygiao()
	{
		
		return this.ngaygiao;
	}
	
	public void setNgaygiao(String ngaygiao)
	{
		
		this.ngaygiao = ngaygiao;
	}
	
	public String getSoluong_Dagiao()
	{
		
		return this.soluong_dagiao;
	}
	
	public void setSoluong_Dagiao(String soluong)
	{
		
		this.soluong_dagiao = soluong;
	}
	
	public String getSoluong_Conlai()
	{
		
		return this.soluong_conlai;
	}
	
	public void setSoluong_Conlai(String soluong)
	{
		
		this.soluong_conlai = soluong;
	}
	
	public String getDvdlId()
	{
		
		return this.dvdlId;
	}
	
	public void setDvdlId(String dvdlId)
	{
		
		this.dvdlId = dvdlId;
	}
	
	public String[] getSoluong_Thaydoigia()
	{
		
		return this.soluong_thaydoigia;
	}
	
	public void setSoluong_Thaydoigia(String[] soluong_thaydoigia)
	{
		
		this.soluong_thaydoigia = soluong_thaydoigia;
	}
	
	public String[] getDongia_Thaydoigia()
	{
		
		return this.dongia_thaydoigia;
	}
	
	public void setDongia_Thaydoigia(String[] dongia_thaydoigia)
	{
		
		this.dongia_thaydoigia = dongia_thaydoigia;
	}
	
	public String[] getSoluong_Thaydoigia_Den()
	{
		
		return this.soluong_thaydoigia_den;
	}
	
	public void setSoluong_Thaydoigia_Den(String[] soluong_thaydoigia)
	{
		
		this.soluong_thaydoigia_den = soluong_thaydoigia;
	}
	
	public String getNgaygiaoDen()
	{
		
		return this.ngaygiaoDen;
	}
	
	public void setNgaygiaoDen(String ngaygiaoDen)
	{
		
		this.ngaygiaoDen = ngaygiaoDen;
	}
	
	public String getSoluong_Tang()
	{
		
		return this.soluong_tang;
	}
	
	public void setSoluong_Tang(String soluong_tang)
	{
		
		this.soluong_tang = soluong_tang;
	}
	
	public String getDongia_Moi()
	{
		
		return this.dongia_moi;
	}
	
	public void setDongia_Moi(String dongia_moi)
	{
		
		this.dongia_moi = dongia_moi;
	}
}
