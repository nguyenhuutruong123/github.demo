package geso.dms.center.beans.hoadon;

public interface IHoaDon_SanPham {
	public String getId();
	public void setId(String id);
	public String getIdSanPham();
	public void setIdSanPham(String idsanpham);
	public String getTenSanPham();
	public void setTenSanPham(String tensanpham);
	public String getMaSanPham();
	public void setMaSanPham(String masanpham);
	public void setSoLuong(int soluong);
	public int getSoLuong();
	public  double getDonGia();
	public void setDonGia(double dongia);
	public void setVAT(double vat);
	public double getVAT();
	public void setChietKhau(double chietkhau);
	public double getChietKhau();
	//get total of product sell
	public double getThanhTien();
	public void setThanhTien(double thanhtien);
	public void setDonViTinh(String donvitinh);
	public String getDonViTinh();
	public void setSoLuongDat(int soluongdat);
	public int getSoLuongDat();
	public void setCTKMID(String ctkmid);
	public String getCTKMId();
}
