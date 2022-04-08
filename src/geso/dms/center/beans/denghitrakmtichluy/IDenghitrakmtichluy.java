package geso.dms.center.beans.denghitrakmtichluy;

import java.sql.ResultSet;

public interface IDenghitrakmtichluy {
	
	public String getUserId();
	public void setUserId(String userId);
	public String getUserName();
	public void setUserName(String userName);
	
	public String getNppId();
	public void setId(String Id);
	public String getId();
	public void setNppTen(String nppTen);
	public String getNppTen();
	public void setNppId(String nppId);

	public void setTungay(String tungay);
	public String getTungay();

	public void setDenngay(String denngay);
	public String getDenngay();
	
	public void setctkmId(String ctkmId);
	public String getctkmId();
	
	public void setctkmIds(ResultSet ctkmIds);
	public ResultSet getctkmIds();
		
	public void setMessage(String Msg);
	public String getMessage();
	
	public void init();
	public void Denghitrakmtichluy();
	public void DBclose();
}
