package geso.dms.distributor.beans.donhangnhapp;

import java.io.Serializable;

public interface ISanPhamDhNpp extends Serializable {
public void setDonHangNPP(String DongHangNpp);
public String getDonHangNPP();
public void setMaSanPham(String MaSanPham);
public String getMaSanPham();
public void setTenSanPham(String TenSanPham);
public String getTenSanPham();
public void setDVT(String DVT);
public String getDVT();
public void setSoLuong(int SoLuong);
public int getSoLuong();
public void setGiaMua(double DonGia);
public double getGiaMua();
public void setKho(String Kho);
public String getKho();
public void setChietKhau(double ChietKhau);
public double getChietKhau();
public void setThanhTien(double ThanhTien);
public double getThanhTien();
public void setThanhTienCoCK(double ThanhTienCoCK);
public double getThanhTienCoCK();
public boolean SavaSanPhamDhNpp();
public void setIdSanPham(String IdSanPham);
public String getIdSanPham();
public void setSoLuongNhan(int soluongnhan);// So luong ben nha cung cap mua chap nhan so voi so luong cua nha cung cap ban
public int getSoLuongNhan();
public void DBclose();

}
