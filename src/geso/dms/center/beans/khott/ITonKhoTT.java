package geso.dms.center.beans.khott;

import java.sql.ResultSet;
import java.util.List;

public interface ITonKhoTT {
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

public void setSoLuong(int soluong);
public int getSoluong();

public void setBooked(int booked);
public int getBooked();
public void setdvkdId(String dvkdId);
public String getdvkdId();
public void setdvkd(ResultSet dvkd);
public ResultSet getdvkd();

public void setAvailable(int available);
public int getAvailable();
public void setGiaGoc(double giagoc);
public double getGiaGoc();
 public void setListTonKhoTT(String sql);
public List<ITonKhoTT> getListTonKhoTT();
}
