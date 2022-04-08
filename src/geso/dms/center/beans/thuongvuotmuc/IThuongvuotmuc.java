package geso.dms.center.beans.thuongvuotmuc;

import java.sql.ResultSet;
import java.util.List;

public interface IThuongvuotmuc 
{
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String Id);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getTenkhoa();
	public void setTenkhoa(String tenkhoa);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getMsg();
	public void setMsg(String msg);
	
	public String getThang();
	public void setThang(String thang);
	public String getNam();
	public void setNam(String nam);
	
	public boolean createKhl();
	public boolean updateKhl();
	
	public List<IThuongvuotmucDetail> getTieuchiDetail();
	public void setTieuchiDetail(List<IThuongvuotmucDetail> tcDetail);
	
	public ResultSet getDvkdRs();
	public void setDvkdRs(ResultSet gsbhRs);
	public ResultSet getKbhRs();
	public void setKbhRs(ResultSet kbhRs);
	public ResultSet getKhuvucRs();
	public void setKhuvucRs(ResultSet kvRs);
	
	public void createRs();
	public void init();
	public void DbClose();
}
