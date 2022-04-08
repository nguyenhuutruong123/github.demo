package geso.dms.center.beans.phieuthanhtoantt.imp;

import geso.dms.center.beans.phieuthanhtoantt.IPhieuThanhToanTT_DH;

public class PhieuThanhToanTT_DH implements IPhieuThanhToanTT_DH{

	String Id;
	String NgayDatHang;
	String IdNhaPP;
	String TenNhaPP;
	String IdDonHang;
	double TienThanhToan;
	double DaThanhToan;
	double NoDauKy;
	double TienDonHang;
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return this.Id;
	}

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		this.Id=id;
	}

	@Override
	public double getTienThanhToan() {
		// TODO Auto-generated method stub
		return this.TienThanhToan;
	}

	@Override
	public void setTienThanhToan(double thanhtoan) {
		// TODO Auto-generated method stub
		this.TienThanhToan=thanhtoan;
	}

	@Override
	public void setIdNhaPP(String idnhapp) {
		// TODO Auto-generated method stub
		this.IdNhaPP=idnhapp;
	}

	@Override
	public String getIdNhaPP() {
		// TODO Auto-generated method stub
		return this.IdNhaPP;
	}

	@Override
	public void setIdDonHang(String iddonhang) {
		// TODO Auto-generated method stub
		this.IdDonHang=iddonhang;
	}

	@Override
	public String getIdDonHang() {
		// TODO Auto-generated method stub
		return this.IdDonHang;
	}

	@Override
	public void setTenNhaPP(String tennhapp) {
		// TODO Auto-generated method stub
		this.TenNhaPP=tennhapp;
	}

	@Override
	public String getTenNhaPP() {
		// TODO Auto-generated method stub
		return this.TenNhaPP;
	}

	@Override
	public void setTienDonHang(double tiendonhang) {
		// TODO Auto-generated method stub
		this.TienDonHang=tiendonhang;
	}

	@Override
	public double getTienDonHang() {
		// TODO Auto-generated method stub
		return this.TienDonHang;
	}

	@Override
	public void setNgayDat(String ngaydat) {
		// TODO Auto-generated method stub
		this.NgayDatHang=ngaydat;
	}

	@Override
	public String getNgayDat() {
		// TODO Auto-generated method stub
		return this.NgayDatHang;
	}

	@Override
	public void setNoDauKy(double nodauky) {
		// TODO Auto-generated method stub
		this.NoDauKy=nodauky;
	}

	@Override
	public double getNoDauKy() {
		// TODO Auto-generated method stub
		return this.NoDauKy;
	}

	@Override
	public void setDaThanhToan(double dathanhtoan) {
		// TODO Auto-generated method stub
		this.DaThanhToan=dathanhtoan;
	}

	@Override
	public double getDaThanhToan() {
		// TODO Auto-generated method stub
		return this.DaThanhToan;
	}

}
