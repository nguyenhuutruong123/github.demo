/*
 * author : KHOAND
 * Interface name : IChiTieuNPP;
 * Description : use nameing convention Pascal
 * Date create : 2011-10-20 
 * Modification Log : implements from IChiTieuNPP
 *--- date--- name------ description  
 *2011-10-21 khoand  --re build 
 */
package geso.dms.center.beans.chitieu.imp;

import geso.dms.center.beans.chitieu.IChiTieuNPP;

public class ChiTieuNPP implements IChiTieuNPP
{

	double Id;
	String NhaPPID;
	String TenNhaPP = "";
	double SoTien = 0;
	String SoDonHang = "";
	String SoSku = "";
	double TrungBinhThang = 0;
	double phantram = 0;
	String sanluongtrendh = "";
	public String getGiaoHang()
  {
  	return GiaoHang;
  }

	public void setGiaoHang(String giaoHang)
  {
  	GiaoHang = giaoHang;
  }

	public String getTonKho()
  {
  	return TonKho;
  }

	public void setTonKho(String tonKho)
  {
  	TonKho = tonKho;
  }

	String SoKhachHang_PhatSinh,SoKhachHang_MuaHang,GiaoHang,TonKho;
	
	public String getSoKhachHang_PhatSinh()
  {
  	return SoKhachHang_PhatSinh;
  }

	public void setSoKhachHang_PhatSinh(String soKhachHang_PhatSinh)
  {
  	SoKhachHang_PhatSinh = soKhachHang_PhatSinh;
  }

	public String getSoKhachHang_MuaHang()
  {
  	return SoKhachHang_MuaHang;
  }

	public void setSoKhachHang_MuaHang(String soKhachHang_MuaHang)
  {
  	SoKhachHang_MuaHang = soKhachHang_MuaHang;
  }

	public ChiTieuNPP()
	{
		this.Id = 0;
		this.NhaPPID = "";
		this.TenNhaPP = "";
		this.SoTien = 0;
		this.SoDonHang = "";
		this.SoSku = "";
		this.TrungBinhThang = 0;
		this.phantram = 0;
		this.sanluongtrendh = "";
		this.SoKhachHang_MuaHang="";
		this.SoKhachHang_PhatSinh="";
		this.GiaoHang="";
		this.TonKho="";
	}

	public void setId(double id)
	{

		this.Id = id;
	}

	public double getid()
	{

		return Id;
	}

	public void setNhaPPId(String nppid)
	{

		this.NhaPPID = nppid;
	}

	public String getNhaPPId()
	{

		return this.NhaPPID;
	}

	public void setSoTien(double sotien)
	{

		this.SoTien = sotien;
	}

	public double getSoTien()
	{

		return this.SoTien;
	}

	public void setTenNhaPP(String tennpp)
	{

		this.TenNhaPP = tennpp;
	}

	public String getTenNhaPP()
	{

		return this.TenNhaPP;
	}

	public void setSoDonHang(String sodonhang)
	{

		this.SoDonHang = sodonhang;
	}

	public String getSodonhang()
	{

		return this.SoDonHang;
	}

	public void setSoSku(String sosku)
	{

		this.SoSku = sosku;
	}

	public String getsoSku()
	{

		return this.SoSku;
	}

	public void setTrungBinhThang(double trungbinhthang)
	{

		this.TrungBinhThang = trungbinhthang;
	}

	public double getTrungBinhThang()
	{

		return this.TrungBinhThang;
	}

	public void setPhantram(double phantram_)
	{

		this.phantram = phantram_;
	}

	public double getPhantram()
	{

		return this.phantram;
	}

	public void setSanluongtrendh(String sanluongdh)
	{

		this.sanluongtrendh = sanluongdh;
	}

	public String getSanluongtrendh()
	{

		return this.sanluongtrendh;
	}

}
