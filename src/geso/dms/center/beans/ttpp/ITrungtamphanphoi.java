package geso.dms.center.beans.ttpp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Hashtable;

public interface ITrungtamphanphoi extends Serializable
{
	
	public void init();
	
	public String getId();
	public void setId(String id);
	
	public String getMa();	
	public void setMa(String ma);
	
	public String getTen();
	public void setTen(String ten);
	
	public String getUserId();
	public void setUserId(String userId);
	
	public void setTrangthai(String trangthai);
	public String getTrangthai();
	
	public String getMessage();	
	public void setMessage(String msg);
	
	public ResultSet getNccRs();
	public void setNccRs(ResultSet nccRs);
	
	public String[] getNccId();
	public void setNccId(String[] nccId);
	
	public Hashtable<Integer, String> getNccSelected();
	
	public boolean save();
	
	public boolean edit();

	public void DBClose();
	
	public void createRs();
	
	
}