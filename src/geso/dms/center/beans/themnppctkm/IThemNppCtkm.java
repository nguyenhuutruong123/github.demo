package geso.dms.center.beans.themnppctkm;

import java.sql.ResultSet;


public interface IThemNppCtkm
{
	
	public String getUserId();
	public void setUserId(String userId);

	public String getId();
	public void setId(String Id);

	public String getTrangthai();
	public void setTrangthai(String trangthai);

	public String getGhichu();
	public void setGhichu(String ghichu);

	public String getMsg();
	public void setMsg(String msg);

	public void init();
	public void createRs();

	public boolean save();
	
	public boolean edit();
	public void DbClose();
	
	public String[] getCtkmId();
	public void setCtkmId(String[] nhanhangId);
	
	public String[] getScheme();
	public void setScheme(String[] scheme);
	
	public String[] getDiengiai();
	public void setDiengiai(String[] diengiai);
	
	
	
	public String[] getLoaingansach();
	public void setLoaingansach(String[] scheme);
	
	public String[] getNganSach();
	public void setNganSach(String[] ngansach);
	
	public String getNppId();
	public void setNppId(String nppId);
	
	public ResultSet getNppRs();
	public void setNppRs(ResultSet nppRs);
	
}
