package geso.dms.center.beans.canhbao;

import java.sql.ResultSet;

public interface ICanhbaoList 
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getMa();
	public void setMa(String ma);	
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public ResultSet getCanhbaoRs();
	public void setCanhbaoRs(ResultSet canhbaoRs);
	
	public void init(String query);
	public void DbClose();
	
}
