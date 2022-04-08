package geso.dms.center.beans.mokhaibaotrungbay;

import java.sql.ResultSet;

public interface IMokhaibaotrungbay {
	public String getUserId();
	
	public void setUserId(String userId);
	
	public String getSchemeId();
	
	public void setSchemeId(String schemeId);
	
	public String getNppId();
	
	public void setNppId(String nppId);
	
	public String getMsg();
	
	public void setMsg(String msg);	

	public ResultSet getScheme();
	
	public void setScheme(ResultSet scheme);

	public ResultSet getNpp();
	
	public void setNpp(ResultSet npp);
	
	public void init();
	
	public void Execute();
}
