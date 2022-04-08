package geso.dms.center.beans.congnonpp;

import java.sql.ResultSet;

public interface ICongnonppList {
	public void setuserId(String userId);	
	public String getuserId();
	
	public void setuserTen(String userTen);	
	public String getuserTen();
	
	public String getId();	
	public void setId(String id);	

	public String getTungay();
	public void setTungay(String tungay);
	
	public String getDenngay();
	public void setDenngay(String denngay);	
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);	
		
	public ResultSet getCnList();	
	public void setCnList(ResultSet cnlist);	
	public void init(String st);
	
	public void setmsg(String msg);
	public String getmsg();
	
	public void DBclose();
}
