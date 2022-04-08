package geso.dms.center.beans.khott;

import java.util.List;

public interface ICapNhatGiaKhoTT {
	public void setSanPhamId(String sanphamid);
	public String getSanPhamId();
	public void setTenSanPham(String tensanpham);
	public String getTenSanPham();
	public void setMaSanPham(String masanpham);
	public String getMaSanPham();

	public void setKhoTTId(String khottid);
	public String getKhoTTId();
	
	public void setTenKhoTT(String tenkhott);
	public String getTenKhoTT();

	public void setGiaGoc(double giagoc);
	public double getGiaGoc();
	
	public void setGiaMoi(double giamoi);
	public double getGiaMoi();
	public void setListCapNhatGiaBySql(String sql);
	public void setListTonKhoTT(List<ICapNhatGiaKhoTT> list);
	public List<ICapNhatGiaKhoTT> getListTonKhoTT();
	public boolean SaveBangGia();
	public String getMessage();
	public void setMessage(String message);
}
