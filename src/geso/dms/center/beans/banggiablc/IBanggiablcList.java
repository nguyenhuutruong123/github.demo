package geso.dms.center.beans.banggiablc;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IBanggiablcList extends Serializable
{
	public ResultSet getKbh() ;
	public String getKbhId() ;
	public void setKbhId(String kbhId) ;
	public String getUserId();
	public void setUserId(String userId);	
	public String getTen();
	public void setTen(String tenbanggia);	
	public ResultSet getDvkd();
	public void setDvkd(ResultSet dvkd);
	public String getDvkdId();
	public void setDvkdId(String dvkdId);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
		
	public List<IBanggiablc> getBgblcList();
	public void setBgblcList(List<IBanggiablc> bgblclist);
	public void init(String search);
	public void DbClose();
	public String getMsg();
	public void setMsg(String msg);
	
	public List<Object> getDataSearch() ;
	public void setDataSearch(List<Object> dataSearch) ;
	public String getView() ;
	public void setView(String view) ;
	public String getLoaikhachhangId();
	public void setLoaikhachhangId(String loaikhachhangId) ;
	public ResultSet getLoaiKhachHang();
	public void setLoaiKhachHang(ResultSet loaiKhachHang) ;
}
