package geso.dms.distributor.beans.khachhang.imp;

import geso.dms.distributor.beans.khachhang.IErpKhachHang_SPCK;

public class ErpKhachHang_SPCK implements IErpKhachHang_SPCK 
{
	String Id;
	String IdSanPham;
	String TenSanPham;	
	String MaSanPham;
	String DonViTinh;
	String PTChietKhau;
	
	public ErpKhachHang_SPCK()
	{
		this.Id = "";
		this.IdSanPham = "";
		this.TenSanPham = "";		
		this.MaSanPham = "";
		this.DonViTinh = "";
		this.PTChietKhau = "";
	}
	
	public String getId() {
		
		return this.Id;
	}

	
	public void setId(String id) {
		
		this.Id=id;
	}

	
	public String getIdSanPham() {
		
		return this.IdSanPham;
	}

	
	public void setIdSanPham(String idsanpham) {
		
		this.IdSanPham=idsanpham;
	}

	
	public String getTenSanPham() {
		
		return this.TenSanPham;
	}

	
	public void setTenSanPham(String tensanpham) {
		
		this.TenSanPham=tensanpham;
	}

	
	
	
	public String getMaSanPham() {
		
		return this.MaSanPham;
	}

	
	public void setMaSanPham(String masanpham) {
		
		this.MaSanPham=masanpham;
	}

	
	public void setDonViTinh(String donvitinh) {
		
		this.DonViTinh=donvitinh;
	}

	
	public String getDonViTinh() {
		
		return this.DonViTinh;
	}

	public String getPTChietKhau() {
		return this.PTChietKhau;
	}


	public void setPTChietKhau(String ptchietkhau) {
		this.PTChietKhau = ptchietkhau;
		
	}

	
	
	
}
