package geso.dms.center.beans.chuyennhanvienbanhang;

import java.sql.ResultSet;

public interface IChuyennhanvienbanhang {
	
	public void setIdNppCu(String IdNppCu);
	public String getIdNppCu();
	
	public void setIdNppMoi(String IdNppoi);
	public String getIdNppMoi();
	
	public void setIdNvbhChon(String idchon);
	public String getIdNvbhChon();
	
	public void setIscopy(String Iscopy);
	public String getIscopy();
	
	public ResultSet getRsDdkd();
	public void setRsDdkd(ResultSet rs);
	
	public ResultSet getRsNppCu();
	public void setRsNppCu(ResultSet rs);
	
	public ResultSet getRsNppMoi();
	public void setRsNppMoi(ResultSet rs);
	
	public void Init();
	
	public void DbClose();
	
	public void setUserId(String UserId);
	public String getUserId();
	
	public void setMsg(String msg);
	public String getMsg();
	public boolean ThucHienChuyen();
	

	
}
