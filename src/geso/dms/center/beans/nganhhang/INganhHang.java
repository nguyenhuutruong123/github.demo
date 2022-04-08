package geso.dms.center.beans.nganhhang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface INganhHang extends Serializable 
{
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
	public String getTen();
	public void setTen(String ten);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getNgaytao();
	public void setNgaytao(String ngaytao);
	public String getNguoitao();
	public void setNguoitao(String nguoitao);
	public String getNgaysua();
	public void setNgaysua(String ngaysua);
	public String getNguoisua();
	public void setNguoisua(String nguoisua);	
	public String getMessage();
	public void setMessage(String msg);
	
	public double getThuexuat();
	public void setThuexuat(double thuexuat);
	
	public String getDvkdId();
	public void setDvkdId(String value);
	public void setDvkdList(ResultSet dvkdList);
	public ResultSet getDvkdList(); 
	
	public String getDvkdTen();
	public void setDvkdTen(String value);
	
	public ResultSet createDvkdList();
	public boolean create();
	public boolean update();
	public void DBClose();

}