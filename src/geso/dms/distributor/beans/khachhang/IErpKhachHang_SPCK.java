package geso.dms.distributor.beans.khachhang;

public interface IErpKhachHang_SPCK
{
	public String getId();
	public void setId(String id);
	
	public String getIdSanPham();
	public void setIdSanPham(String idsanpham);
	
	public String getTenSanPham();
	public void setTenSanPham(String tensanpham);	
	
	public String getMaSanPham();
	public void setMaSanPham(String masanpham);	
	
	public String getPTChietKhau();
	public void setPTChietKhau(String ptchietkhau);	
	
	public void setDonViTinh(String donvitinh);
	public String getDonViTinh();
	
	
}
