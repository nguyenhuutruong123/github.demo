package geso.dms.center.beans.mochot;

import java.sql.ResultSet;

public interface IMochot {
	public void setVungId(String vungId);
	
	public String getVungId();
	
	public void setKhuvucId(String kvId);
	
	public String getKhuvucId();
	
	public void setKenhId(String kenhId);
	
	public String getKenhId();

	public void setNppId(String nppId);
	
	public String getNppId();
	
	
	public void settungay(String tungay);
	public String gettungay();
	
	public void setdenngay(String denngay);
	public String getdenngay();
	
	
	public void init();
	
	public void setMsg(String msg);
	
	public String getMsg();
	
	public void setVung(ResultSet vung);
	
	public ResultSet getVung();

	public void setKhuvuc(ResultSet kv);
	
	public ResultSet getKhuvuc();
	
	public void setKenh(ResultSet kenh);
	
	public ResultSet getKenh();

	public void setNpp(ResultSet npp);
	
	public ResultSet getNpp();
	public void  DBClose();
	
	
	public String getUserId();
	public void setUserId(String userId);
	
	public ResultSet getNppRs();
	public void setNppRs(ResultSet nppRs);
	
	public String MochotDH();
	public String HuyDH();
	
	public void setNgay(String ngay);
	public String getNgay();
	
	
	public ResultSet getGsbhList();
	public void setGsbhList(ResultSet nppList);
	public ResultSet getNppList();
	public void setNppList(ResultSet nppList);
	public String getGsbhId();
	public void setGsbhId(String gsbhId);
	public ResultSet getDdkdList();
	public void setDdkdList(ResultSet ddkdList);	
	public String getDdkdId();
	public void setDdkdId(String ddkdId);
	
	public String getDonhangId();
	public void setDonhangId(String dhId); 
	public ResultSet getDonhangList();
	public void setDonhangList(ResultSet donhangList);
	
	public String getKhId();
	public void setKhId(String khId);
	public String getSmartId(); 
	public void setSmartId(String smartId);
	public String getKhTen();
	public void setKhTen(String khTen); 

}
