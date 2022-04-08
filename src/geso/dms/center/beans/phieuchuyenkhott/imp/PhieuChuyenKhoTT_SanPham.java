package geso.dms.center.beans.phieuchuyenkhott.imp;

import geso.dms.center.beans.phieuchuyenkhott.IPhieuChuyenKhoTT_SanPham;

public class PhieuChuyenKhoTT_SanPham implements IPhieuChuyenKhoTT_SanPham {
 
	String Id="";
	String IdSanPham="";
	String TenSanPham="";
	int SoLuong=0;
	double DonGia=0;
	int SoLuongChuyen=0;
	String MaSanPham="";
	
	@Override
	public void setSanPhamId(String SanPhamId) {
		// TODO Auto-generated method stub
		this.IdSanPham=SanPhamId;
	}

	@Override
	public String getSanPhamId() {
		// TODO Auto-generated method stub
		return this.IdSanPham;
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
	public void setSoLuong(int soluong) {
		// TODO Auto-generated method stub
		this.SoLuong=soluong;
	}

	@Override
	public Integer getSoLuong() {
		// TODO Auto-generated method stub
		return this.SoLuong;
	}

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
	public Double getDonGia() {
		// TODO Auto-generated method stub
		return this.DonGia;
	}

	@Override
	public void setDonGia(double dongia) {
		// TODO Auto-generated method stub
		this.DonGia=dongia;
	}

	@Override
	public void setSoLuongChuyen(int soluongchuyen) {
		// TODO Auto-generated method stub
		this.SoLuongChuyen=soluongchuyen;
	}

	@Override
	public Integer getSoLuongChuyen() {
		// TODO Auto-generated method stub
		return this.SoLuongChuyen;
	}

	@Override
	public void setMaSanPham(String masp) {
		// TODO Auto-generated method stub
		this.MaSanPham=masp;
	}

	@Override
	public String getMaSanpham() {
		// TODO Auto-generated method stub
		return this.MaSanPham;
	}
	
}
