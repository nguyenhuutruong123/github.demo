package geso.dms.center.beans.duyettrasanpham;

import java.sql.ResultSet;

public interface IDuyettrasanphamList 
{
	public String getUserId();
	public void setUserId(String userId);
	
	/*
	public ResultSet getVungRs();
	public void setVungRs(ResultSet vungRs);	
	public String getVungId();
	public void setVungId(String vungId);
	
	public ResultSet getKhuvucRs();
	public void setKhuvucRs(ResultSet kvRs);	
	public String getKhuvucId();
	public void setKhuvucId(String kvId);
	*/
	
	public ResultSet getNppRs();
	public void setNppRs(ResultSet nppRs);	
	public String getNppId();
	public void setNppId(String nppId);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public ResultSet getDspList();
	public void setDspList(ResultSet dsplist);
	
	public void init(String search);
	public void DBclose();
}
