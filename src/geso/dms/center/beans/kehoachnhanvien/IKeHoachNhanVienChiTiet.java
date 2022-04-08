package geso.dtl.center.beans.kehoachnhanvien;

import java.io.Serializable;

public interface IKeHoachNhanVienChiTiet extends Serializable 
{
	public String getId();
	public void setId(String id);
	public String getKeHoachId();
	public void setKeHoachId(String khId);
	public String getNgay();
	public void setNgay(String ngay);
	
	public String getLoai();
	public void setLoai(String loai);
	public String getNppId();
	public void setNppId(String nppId);
	public String getTinhId();
	public void setTinhId(String tinhId);
	public String getQuanHuyenId();
	public void setQuanHuyenId(String quanId);
	public String getLat();
	public void setLat(String lat);
	public String getLon();
	public void setLon(String lon);
	public String getDiaChi();
	public void setDiaChi(String diachi);
	
	public String getGhiChu();
	public void setGhiChu(String ghichu);
	public String getGhiChu2();
	public void setGhiChu2(String ghichu2);
	
	public String getNppTBHId();
	public void setNppTBHId(String id);
	public String getDDKDId();
	public void setDDKDId(String ddkdid);
	public String getTBHId();
	public void setTBHId(String tbhId);
	public String getGhiChuTBH();
	public void setGhiChuTBH(String ghichu);
	
	public void setThoidiem(String value);
	public String getThoidiem();
	
	public void setTyle(String value);
	public String getTyle();
}
