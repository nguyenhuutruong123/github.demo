package geso.dms.distributor.beans.chitieunpp;

import java.sql.ResultSet;

public interface IChitieuSKUInList
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public String getThang();
	public void setThang(String thang);
	public String getNam();
	public void setNam(String nam);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getMsg();
	public void setMsg(String msg);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public ResultSet getChitieuSKUInRs();
	public void setChitieuSKUInRs(ResultSet ctskuIn);
	
	public void init(String query);
	public void initTT(String query);
	
	public void DbClose();
	
}
