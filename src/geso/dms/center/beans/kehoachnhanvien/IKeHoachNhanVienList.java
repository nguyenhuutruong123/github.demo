package geso.dms.center.beans.kehoachnhanvien;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IKeHoachNhanVienList extends Serializable 
{
	public String getUserId();
	public void setUserId(String userId);
		
	
	public String getTenNhanVien();	
	public void setTenNhanVien(String kehoachnhanvien);
	
	public String getLoai();
	public void setLoai(String loai);
	public String getKbhId() ;
	public void setKbhId(String kbhId);
	public ResultSet getKbhRs() ;
	public void setKbhRs(ResultSet kbhRs);

	public String getThang();
	public void setThang(String thang);
	
	public String getNam();
	public void setNam(String nam);
	
	public String getNhanVienId();
	public void setNhanVienId(String nhanvienId);
	
	public ResultSet getKhnvRs();	
	public void setKhnvRs(ResultSet khnvlist);
	public void setMsg(String Msg);
	public String getMsg();
	public void init(String search);
	public String getSearchQuery();
	public void closeDB();
	
	public boolean delete(String id);
	public boolean duyet(String id);
	public boolean moduyet(String id);
	
	public ResultSet getVungRs();
	public ResultSet getKhuvucRs();
	public String getVungId();
	public void setVungId(String value);
	public String getKhuvucId();
	public void setKhuvucId(String value);
	public boolean Upload(String data);
	
}
