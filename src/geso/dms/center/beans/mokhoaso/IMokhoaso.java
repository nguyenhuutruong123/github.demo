package geso.dms.center.beans.mokhoaso;

import java.sql.ResultSet;
import java.util.ArrayList;

public interface IMokhoaso {
	public void setVungId(String vungId);
	
	public String getVungId();
	
	public void setKhuvucId(String kvId);
	
	public String getKhuvucId();
	
	public void setKenhId(String kenhId);
	
	public String getKenhId();

	public void setNppId(String nppId);
	
	public String getNppId();
	
	public void init();
	
	public String MoKhoaSoNgay();
	
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
	
	public String KhoaSoNgay(String soNgay);
	public String KhoaSoThang(String soNgay);
	
	public void setNgay(String ngay);
	public String getNgay();
	
	public String[] getNppChonIds() ;
	public void setNppChonIds(String[] nppChonIds) ;

}
