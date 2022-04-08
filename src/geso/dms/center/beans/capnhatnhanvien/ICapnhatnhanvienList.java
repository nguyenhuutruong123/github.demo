package geso.dms.center.beans.capnhatnhanvien;

import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface ICapnhatnhanvienList  extends Serializable, IPhan_Trang
{

	public String getPk_seq();
	public void setPk_seq(String pk_seq);
	public void CreateLoaiNV();
	public ResultSet getLoainv();
	public String getUsername_email();
	public void setUsername_email(String username_email);
	public void setTen(String Ten);
	public String getTen();
	public void setNgaysinh(String Ngaysinh);
	public String getNgaysinh();
	public void setTungay(String Tungay);
	public String getTungay();
	public void setDenngay(String Denngay);
	public String getDenngay();
	public ResultSet getDSNV();
	public void init(String st);
	public void setuserId(String userId);
	public String getuserId();
	public void setTrangthai(String Trangthai);
	public String getTrangthai();
	public void setPhanloai(String Phanloai);
	public String getPhanloai();
	public boolean xoa(String Id);
	public void setmsg(String msg);
	public String getmsg();
	public void DbClose();
	public String getLoainhanvien_fk();
	public void setLoainhanvien_fk(String loainhanvien_fk);
	public ResultSet getLoainhanvienRs();
	public void setLoainhanvienRs(ResultSet loainhanvienRs);
	public List<Object> getDataSearch();
	public void setDataSearch(List<Object> dataSearch) ;
}
