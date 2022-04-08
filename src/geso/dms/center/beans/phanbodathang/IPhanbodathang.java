package geso.dms.center.beans.phanbodathang;

import java.sql.ResultSet;

public interface IPhanbodathang {
	public String getTungay();
	
	public void setTungay(String tungay);

	public String getDenngay();

	public void setDenngay(String denngay);
	
	public String getSpId();

	public void setSpId(String spId);
	
	public ResultSet getSp();

	public void setSp(ResultSet sp);
	
	public String getKenhId();
	
	public void setKenhId(String kenhId);
	
	public String getVungId();

	public void setVungId(String vungId);
	
	public ResultSet getKenh();

	public void setKenh(ResultSet kenh);
	
	public ResultSet getVung();

	public void setVung(ResultSet vung);	

	public String getKvId();

	public void setKvId(String kvId);

	public ResultSet getKv();

	public void setKv(ResultSet kv);		
	
	public String getMsg();

	public void setMsg(String msg);
	
	public ResultSet getPhanbo();

	public void setPhanbo(ResultSet pb);
	
	public void init();
	
	public void DBClose();
	

}
