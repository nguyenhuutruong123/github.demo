package geso.dms.center.beans.donmuahang;

public interface IERP_DonDatHang_SP {
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
	public void setSHEME(String ctkmid);
	public String getSHEME();
	public void setSoluongton(int cheme);
	public int getsoluongton();
	
	public void setSoluongduyet(int soluongduyet);
	public int getsoluongduyet();
	
	public String getTrongluong();
	public void setTrongluong(String trongluong);
	public String getThetich();
	public void setThetich(String thetich);
	public String getQuyCach();
	public void setQuyCach(String Quycach);
	public String getKhoTT();
	public void setKhoTT(String KhoTT);
	public String getTenKhoTT();
	public void setTenKhoTT(String TenKhoTT);
}
