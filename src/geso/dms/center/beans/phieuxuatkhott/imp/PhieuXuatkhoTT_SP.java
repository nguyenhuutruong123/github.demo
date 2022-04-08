package geso.dms.center.beans.phieuxuatkhott.imp;

import geso.dms.center.beans.phieuxuatkhott.IPhieuXuatKhoTT_SP;

public class PhieuXuatkhoTT_SP implements IPhieuXuatKhoTT_SP{

	String ID="";
	String IdSanPham="";
	String TenSanPham="";
	int SoLuong=0;
	int Le=0;
	String DonViTinh="";
	int QuyCach=0;
	String DVDL2;
	int SoLuongQuydoi;
	String MaSanPham="";
	@Override
	public void setId(String Id) {
		// TODO Auto-generated method stub
	this.ID=Id;	
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return this.ID;
	}

	@Override
	public void setIDSanPham(String idsanpham) {
		// TODO Auto-generated method stub
		this.IdSanPham=idsanpham;
	}

	@Override
	public String getIdSanPham() {
		// TODO Auto-generated method stub
		return this.IdSanPham;
	}

	@Override
	public void setSoLuong(int soluong) {
		// TODO Auto-generated method stub
		this.SoLuong=soluong;
	}

	@Override
	public int getSoLuong() {
		// TODO Auto-generated method stub
		return this.SoLuong;
	}

	@Override
	public void setDonViTinh(String donvitinh) {
		// TODO Auto-generated method stub
		this.DonViTinh=donvitinh;
	}

	@Override
	public String getDonViTinh() {
		// TODO Auto-generated method stub
		return this.DonViTinh;
	}

	@Override
	public void setQuyCach(int quycach) {
		// TODO Auto-generated method stub
		this.QuyCach=quycach;
	}

	@Override
	public int getQuyCach() {
		// TODO Auto-generated method stub
		return this.QuyCach;
	}

	@Override
	public void setDVDL2(String dvld2) {
		// TODO Auto-generated method stub
		this.DVDL2=dvld2;
	}

	@Override
	public String getDVDL2() {
		// TODO Auto-generated method stub
		return this.DVDL2;
	}

	@Override
	public void setLe(int le) {
		// TODO Auto-generated method stub
		this.Le=le;
	}

	@Override
	public int getLe() {
		// TODO Auto-generated method stub
		return this.Le;
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
	public void setSoLuongQuyDoi(int soluongquydoi) {
		// TODO Auto-generated method stub
		this.SoLuongQuydoi=soluongquydoi;
	}

	@Override
	public int getSoLuongQuyDoi() {
		// TODO Auto-generated method stub
		return this.SoLuongQuydoi;
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

}
