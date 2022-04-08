package geso.dms.center.beans.danhmucquyen;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

public interface IDanhmucquyen
{
	public void setTen(String Ten);
	public String getTen();
	
	public void setDiengiai(String DienGiai);
	public String getDiengiai();
	
	public ResultSet getUngdungRs();
	public void setUngdungRs(ResultSet ungdungRs);
	
	
	public void setId(String id);
	public String getId();
	
	public void init();
	
	public void setUserId(String UserId);
	public String getUserId();
	
	public void setTrangthai(String TrangThai);
	public String getTrangThai();
	
	
	public boolean save(HttpServletRequest request);
	public boolean update(HttpServletRequest request);
	
	public void setMsg(String Msg);
	public String getMsg();


	
	public void DBClose();
	

	public String getUngdungIds();
	public void setUngdungIds(String ungdungIds);
	
	public String getLoaiMenu();
	public void setLoaiMenu(String loaiMenu);
	
	public String getCbThem();
	public void setCbThem(String cbThem);
	
	public String getCbXoa();
	public void setCbXoa(String cbXoa);
	
	public String getCbSua();
	public void setCbSua(String cbSua);
	
	public String getCbXem();
	public void setCbXem(String cbXem);
	
	public String getCbChot();
	public void setCbChot(String cbChot);
	
	
	public String getCbHuyChot();
	public void setCbHuyChot(String cbHuyChot);
	
	public String getCbHienThi();
	public void setCbHienThi(String cbHienThi);
	
	public void createRs();
	
	
	
}
