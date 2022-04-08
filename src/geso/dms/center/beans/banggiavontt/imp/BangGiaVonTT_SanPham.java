package geso.dms.center.beans.banggiavontt.imp;

import geso.dms.center.beans.banggiavontt.IBangGiaVonTT_SanPham;

public class BangGiaVonTT_SanPham implements IBangGiaVonTT_SanPham {

	String Id;
	String SanPhamId;
	double GiaVon;
	String ChonBan;
	String MaSanPham;
	String TenSanPham;
	String DonViTinh;
	double GiaMoi;
	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		this.Id=id;
		
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return this.Id;
	}

	@Override
	public void setSanPhamID(String spid) {
		// TODO Auto-generated method stub
		this.SanPhamId=spid;
	}

	@Override
	public String getSanPhamID() {
		// TODO Auto-generated method stub
		return this.SanPhamId;
	}

	@Override
	public void setGiaVon(double giavon) {
		// TODO Auto-generated method stub
		this.GiaVon=giavon;
	}

	@Override
	public double getGiaVon() {
		// TODO Auto-generated method stub
		return this.GiaVon;
	}
/**
 * chon ban co gia tri =1 co nghia la san pham nay duoc ap dung gia von nay va co the ban ra thi truong
 */
	@Override
	public void setChonBan(String chonban) {
		// TODO Auto-generated method stub
		this.ChonBan=chonban;
	}

	@Override
	public String getChonBan() {
		// TODO Auto-generated method stub
		return this.ChonBan;
	}

	@Override
	public void setMaSanPham(String masanpham) {
		// TODO Auto-generated method stub
		this.MaSanPham=masanpham;
	}

	@Override
	public String getMaSanPham() {
		// TODO Auto-generated method stub
		return this.MaSanPham;
	}

	@Override
	public void setTenSanPham(String tensanpham) {
		// TODO Auto-generated method stub
		this.TenSanPham=tensanpham;
	}

	@Override
	public String getTenSanPham() {
		// TODO Auto-generated method stub
		return this.TenSanPham;
	}

	@Override
	public void setDonViTinh(String dvt) {
		// TODO Auto-generated method stub
		this.DonViTinh=dvt;
	}

	@Override
	public String getDonViTinh() {
		// TODO Auto-generated method stub
		return this.DonViTinh;
	}

	@Override
	public void setGiaMoi(double giamoi) {
		// TODO Auto-generated method stub
		this.GiaMoi=giamoi;
	}

	@Override
	public double getGiaMoi() {
		// TODO Auto-generated method stub
		return this.GiaMoi;
	}
	
}
