package geso.dms.center.beans.gioihancongnott.imp;

import geso.dms.center.beans.gioihancongnott.IGioiHangCongNoTT_Npp;

public class GioiHangCongNo_Npp implements IGioiHangCongNoTT_Npp{

	String Id;
	String IdNhaPp;
	String TenNhaPp;
	String DiaChi;
	String KhuVuc;
	@Override
	public void setIDNhaPP(String idnhapp) {
		// TODO Auto-generated method stub
		this.IdNhaPp=idnhapp;
	}

	@Override
	public String getIdNhaPp() {
		// TODO Auto-generated method stub
		return this.IdNhaPp;
	}

	@Override
	public void setTenNhaPP(String tennhapp) {
		// TODO Auto-generated method stub
		this.TenNhaPp=tennhapp;
	}

	@Override
	public String getTenNhaPp() {
		// TODO Auto-generated method stub
		return this.TenNhaPp;
	}

	@Override
	public void setDiaChi(String diachi) {
		// TODO Auto-generated method stub
		this.DiaChi=diachi;
	}

	@Override
	public String getDiaChi() {
		// TODO Auto-generated method stub
		return this.DiaChi;
	}

	@Override
	public void setID(String id) {
		// TODO Auto-generated method stub
		this.Id=id;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return this.Id;
	}

	@Override
	public void setKhuVuc(String khuvuc) {
		// TODO Auto-generated method stub
		this.KhuVuc=khuvuc;
	}

	@Override
	public String getKhuVuc() {
		// TODO Auto-generated method stub
		return this.KhuVuc;
	}
	

}
