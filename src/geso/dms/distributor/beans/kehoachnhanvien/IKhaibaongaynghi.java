package geso.dms.distributor.beans.kehoachnhanvien;

import java.sql.ResultSet;
import java.util.Hashtable;

public interface IKhaibaongaynghi 
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getMsg();
	public String setMsg(String msg);
	
	public void init();
	public void createRs();
	
	public void DBclose();
	
	public void SetNgayNghi(String[] Ngay);
	public String[] GetNgayNghi();
	
	public void SetGhichu(String[] ghichu);
	public String[] GetGhichu();
	
	public ResultSet getDDKDRs();
	public ResultSet getNgaynghiRs();
	
	public String getDdkdId();
	public void setDdkdId(String value);
	
	public String getNppId();
	public void setNppId(String value);
	
	public String getThang();
	public void setThang(String thang);
	
	public String getNam();
	public void setNam(String nam);
	public boolean CreateNew();
	public boolean Update();
	public void setId(String id);
	public String getId();
	public void setView(String view);
	public String getView();
}
