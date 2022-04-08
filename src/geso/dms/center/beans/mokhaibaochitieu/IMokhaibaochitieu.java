package geso.dms.center.beans.mokhaibaochitieu;

import java.sql.ResultSet;

public interface IMokhaibaochitieu {
	public String getUserId();
	
	public void setUserId(String userId);
	
	public String getDvkdId();
	
	public void setDvkdId(String dvkdId);

	public ResultSet getDvkd();
	
	public void setDvkd(ResultSet dvkd);
	
	public String getNppId();
	
	public void setNppId(String nppId);
	
	public String getYear();
	
	public void setYear(String nam);

	public String getMonth();
	
	public void setMonth(String thang);
	
	public String getMsg();
	
	public void setMsg(String msg);
	
	public ResultSet getNpp();
	
	public void setNpp(ResultSet npp);
	
	public void init();
	
	public void Execute();

	public void DBClose();

}