package geso.dms.distributor.beans.kehoachnhanvien;

import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Hashtable;

public interface IKhaibaongaynghiList extends IPhan_Trang, Serializable
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getMsg();
	public String setMsg(String msg);
	
	public String getView();
	public void setView(String view);
	
	public String getThang();
	public void setThang(String Thang);
	
	public String getTrangThai();
	public void setTrangThai(String TrangThai);
	
	public String getAsmId();
	public void setAsmId(String AsmId);
	
	public String getDdkdId();
	public void setDdkdId(String DdkdId);
	public ResultSet getDDkdRs();
	
	public void init();
	public void createRs();
	
	public String getLoaiNv();
	public String getPhanloaiNv();
	public void DBclose();
	public ResultSet getNgaynghiRs();
	public ResultSet getAsmRs();
}
