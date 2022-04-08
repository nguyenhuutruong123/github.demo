package geso.dms.distributor.beans.reports;

import java.io.Serializable;
import java.sql.ResultSet;

public interface Ireportnpp extends Serializable {
	
	public void setUser (String user);
	public String getUser ();
	public void setUserTen(String tenuser);
	public String getTenUser();
	public void setTuNgay (String tungay);
	public String getTuNgay ();
	public void setLoi(String loi);
	public String getLoi();
	public void setDenNgay (String denngay);
	public String getDenNgay ();
	
	public void setSKU (String sku);
	public String getSKU ();
	public void setSKUr (ResultSet SKUr);
	public ResultSet getSKUr();
	public void init();
	public void setNppId(String npp_id);
	public String getNppId();
	public void setKenhId(String kenh_id);
	public String getKenhId();
	public void setNhanHang(String nhanhang);
	public String getNhanHang();
	public void setLoaiHang(String loaihang);
	public String getLoaiHang();
	public void setSanPham(String SanPhamId);
	public String getSanPhamId();
	public void CreateRsNpp();
	public void CreateRsNhanHang();
	public void CreateRsLoaiHang();
	public void CreateRsSanPham();
	public void CreateRsKenh();
	public void CreateRsVung();
	public void CreateRsKhuVuc();
	public void setVungId(String vung_id);
	public String getVungId();
	public void setKhuVucId(String kv_id);
	public String getKhuVucId();
	
	public ResultSet getRsNhanHang();
	public ResultSet getRsNhaPP();
	public ResultSet getRsLoaiHang();
	public ResultSet getRsKenh();
	public ResultSet getRsSanPham();
	public ResultSet getRsVung();
	public ResultSet getRsKhuVuc();
	public void DBclose();
	//s
}
