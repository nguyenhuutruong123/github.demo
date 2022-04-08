package geso.dms.center.beans.phieunhapkhott.imp;

import geso.dms.center.beans.phieunhapkhott.IPhieuNhapKhoTT_SanPham;

public class PhieuNhapKhoTT_SanPham implements IPhieuNhapKhoTT_SanPham {

	 String Id="";
	 String SanPhamId="";
	 String SanPhamTen="";
	 int SoLuong=0;
	@Override
	public void setSanPhamId(String sanphamid) {
		// TODO Auto-generated method stub
		this.SanPhamId=sanphamid;
	}

	@Override
	public String getSanPhamId() {
		// TODO Auto-generated method stub
		return this.SanPhamId;
	}

	@Override
	public void setTenSanPham(String tensanpham) {
		// TODO Auto-generated method stub
		this.SanPhamTen=tensanpham;
	}

	@Override
	public String getTenSanPham() {
		// TODO Auto-generated method stub
		return this.SanPhamTen;
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

}
