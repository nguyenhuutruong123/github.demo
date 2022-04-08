package geso.dms.center.beans.khaosat;

import java.sql.ResultSet;

public interface IThongkekhaosatList 
{
	public String getUserId();
	public void setUserId(String userId);
	
	public ResultSet getKhaosatRs();
	public void setKhaosatRs(ResultSet khaosatRs);
	public String getKhaosatId();
	public void setKhaosatId(String khaosatId);
	
	public String getTennguoitraloi();
	public void setTennguoitraloi(String tennguoitraloi);	
	public String getSodienthoai();
	public void setSodienthoai(String sodienthoai);	
	
	public String getMsg();
	public void setMsg(String msg);
	
	public ResultSet getKetquaKsRs();
	public void setKetquaKsRs(ResultSet ketquaKsRs);
	
	public void init(String query);
	public void createRs();
	
	public void DbClose();
	public String getTungay();
	public void setTungay(String tungay) ;
	public String getDenngay();
	public void setDenngay(String denngay);
	public String getDoituong();
	
	public void setDoituong(String doituong);
}
