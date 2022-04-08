package geso.dms.center.beans.tinhthunhap;

import java.sql.ResultSet;

public interface ITinhthunhapList 
{
	public String getUserId();
	public void setUserId(String userId);
	public String getUserTen();
	public void setUserten(String userTen);
	
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
	
	public ResultSet getKhlRs();
	public void setKhlRs(ResultSet khlRs);
	
	public void init(String query);
	public void DbClose();
	
	//init  BC
	public ResultSet getVungRs();
	public void setVungRs(ResultSet vungRs);
	public String getVungId();
	public void setVungId(String vungId);
	
	public ResultSet getKhuvucRs();
	public void setKhuvucRs(ResultSet kvRs);
	public String getKvId();
	public void setKvId(String kvId);
	
	public ResultSet getKbhRs();
	public void setKbhRs(ResultSet kbhRs);
	public String getKbhId();
	public void setKbhId(String kbhId);
	
	public ResultSet getNppRs();
	public void setNppRs(ResultSet nppRs);
	public String getNppId();
	public void setNppId(String nppId);
	
	public void initBC(String query);
	
	
	
}
