package geso.dms.center.beans.duyettrakhuyenmai;

import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;

public interface IDuyettrakhuyenmai 
{
	public dbutils getDb() ;
	public ResultSet getKhDuyetRs() ;
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
	
	public void setCtkmRs(ResultSet ctkmRs);
    public ResultSet getCtkmRs();
	public String getCtkmId();
	public void setCtkmId(String ctkmId);
	
	public String getNgayduyet();
	public void setNgayduyet(String ngayduyet);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public void DbClose();
	
	public String getMsg();
	public void setMsg(String msg);
	
	public String getThang();
	public void setThang(String value);
	
	public String getNam();
	public void setNam(String value);
	
	public void setKhachhangRs(ResultSet khRs);
    public ResultSet getKhachhangRs();

	public String[] getNppTen();
	public void setNppTen(String[] nppTen);
	
	public String[] getKhId();
	public void setKhId(String[] khId);
	
	public String[] getKhMa();
	public void setKhMa(String[] khMa);

	public String[] gettinhthanhTen();
	public void settinhthanhTen(String[] ttTen);
	
	public String[] getddkdTen();
	public void setddkdTen(String[] ddkdTen);
	
	public String[] getKhTen();
	public void setKhTen(String[] khTen);
	
	public String[] getDoanhso();
	public void setDoanhso(String[] doanhso);
	
	public String[] getDat();
	public void setDat(String[] dat);
	
	public String[] getSoxuat();
	public void setSoxuat(String[] soxuat);
	
	public String[] getTongtien();
	public void setTongtien(String[] tongtien);
	
	public String[] getSanpham();
	public void setSanpham(String[] sanpham);
	
	public String[] getThuongthem();
	public void setThuongthem(String[] value);
	
	public String[] getMucthuong();
	public void setMucthuong(String[] value);
	
	public String[] getMucDk();
	public void setMucDk(String[] value);
 
	public void init();
	public void createRs();
	
	public boolean createTctSKU();
	public boolean updateTctSKU();
	
	public boolean setDuyetMucThuong(String khachhang_fks,Hashtable<String, Integer> kh_muc);
	
	public String[] getTinhthanhid() ;
	public void setTinhthanhid(String[] tinhthanhid);
	
	public String getTungay_ds() ;
	public void setTungay_ds(String tungay_ds);
	public String getDenngay_ds() ;
	public void setDenngay_ds(String denngay_ds) ;	
	public ResultSet getDangkyTichluyRs() ;

	public List<String> getDangkyIds();
	public void setDangkyIds(String[]dangkyIds) ;
	public boolean createDuyet();
}
