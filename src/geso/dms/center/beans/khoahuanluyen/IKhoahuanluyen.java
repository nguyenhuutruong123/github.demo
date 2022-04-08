package geso.dms.center.beans.khoahuanluyen;

import java.sql.ResultSet;

public interface IKhoahuanluyen 
{
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String Id);
	
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getTenkhoa();
	public void setTenkhoa(String tenkhoa);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getMsg();
	public void setMsg(String msg);
	
	public ResultSet getNppRs();
	public void setNppRs(ResultSet nppRs);
	public String getNppIds();
	public void setNppIds(String nppIds);
	
	public ResultSet getGsbhRs();
	public void setGsbhRs(ResultSet gsbhRs);
	public String getGsbhIds();
	public void setGsbhIds(String gsbhIds);
	
	public ResultSet getNvbhRs();
	public void setNvbhRs(ResultSet nvbhlRs);
	public String getNvbhIds();
	public void setNvbhIds(String nvbhIds);
	
	public boolean createKhl();
	public boolean updateKhl(String gsbh_hoanthanh, String ddkd_hoanthanh);
	
	public void createRs();
	public void init();
	public void DbClose();
}
