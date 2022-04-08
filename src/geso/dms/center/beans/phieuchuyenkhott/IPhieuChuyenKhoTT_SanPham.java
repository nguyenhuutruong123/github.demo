package geso.dms.center.beans.phieuchuyenkhott;

public interface IPhieuChuyenKhoTT_SanPham {
	  public void setSanPhamId(String SanPhamId);
	  public String getSanPhamId();
	  public void setTenSanPham(String tensanpham);
	  public String getTenSanPham();
	  public void setSoLuong(int soluong);
	  public Integer getSoLuong();
	  public void setId(String Id);
	  public String getId();
	  public Double getDonGia();
	  public void setDonGia(double dongia);
	  /**
	   * So Luong chuyen la so luong chuyen cua 1 san pham,vd sp A co so luong trong don hang la 10, ta chuyen qua 5
	   * @param soluongnhan
	   */
	  public void setSoLuongChuyen(int soluongchuyen);
	  public Integer getSoLuongChuyen();
	  public void setMaSanPham(String masp);
	  public String getMaSanpham();
}
