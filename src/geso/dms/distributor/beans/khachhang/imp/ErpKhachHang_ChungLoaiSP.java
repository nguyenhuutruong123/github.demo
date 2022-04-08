package geso.dms.distributor.beans.khachhang.imp;

import geso.dms.distributor.beans.khachhang.IErpKhachHang_ChungLoaiSP;;

public class ErpKhachHang_ChungLoaiSP implements IErpKhachHang_ChungLoaiSP
{
	String Id;
	String IdChungLoai;
	String TenChungLoai;	
	String MaChungLoai;
	String PTChietKhau;
	
	public ErpKhachHang_ChungLoaiSP()
	{
		this.Id = "";
		this.IdChungLoai = "";
		this.TenChungLoai = "";		
		this.MaChungLoai = "";
		this.PTChietKhau = "";
	}
	
	public String getId() {
		
		return this.Id;
	}

	
	public void setId(String id) {
		
		this.Id=id;
	}

	public String getPTChietKhau() {
		return this.PTChietKhau;
	}


	public void setPTChietKhau(String ptchietkhau) {
		this.PTChietKhau = ptchietkhau;
		
	}

	
	public String getIdChungLoai() {
		
		return this.IdChungLoai;
	}

	
	public void setIdChungLoai(String idchungLoai) {
		
		this.IdChungLoai = idchungLoai;
	}

	
	public String getTenChungLoai() {
		
		return this.TenChungLoai;
	}

	
	public void setTenChungLoai(String tenchungLoai) {
		
		this.TenChungLoai = tenchungLoai;
	}

	
	public String getMaChungLoai() {
		
		return this.MaChungLoai;
	}

	
	public void setMaChungLoai(String machungLoai) {
		
		this.MaChungLoai = machungLoai;
	}	
	
	
}
