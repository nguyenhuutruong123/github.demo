package geso.dms.distributor.beans.banggiabandoitac;

import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IBanggiabandoitacList extends Serializable, IPhan_Trang
{
	public String getUserId();
	public void setUserId(String userId);

	public String getUserTen(); 
	public void setUserTen(String userTen); 
	
	public String getTen(); 
	public void setTen(String ten);
	
	public String getDvkdId();
	public void setDvkdId(String dvkdId);
	
	public String getKenhId();
	public void setKenhId(String kenhId);
	
	public String getTrangthai(); 
	public void setTrangthai(String trangthai); 

	public ResultSet getBgdoitacList();
	public void setBgdoitacList(ResultSet bgDoitac);

	public ResultSet getDvkd();
	public void setDvkd(ResultSet dvkd);
	
	public ResultSet getKenh();
	public void setKenh(ResultSet kenh);

	public ResultSet getBglist(); 
	public void setBglist(ResultSet bglist); 
	
	public void init(String search);
	public void closeDB();
	
	public String getTungay();
	public void setTungay(String tungay);
	
	public void setDenngay(String denngay);
	public String getDenngay();
	
	public String getMsg();
	public void setMsg(String msg);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public String getType();
	public void setType(String type);
	public String getNhomsp();

	public void setNhomsp(String nhomsp);

	public ResultSet getRsnhomsp();

	public void setRsnhomsp(ResultSet rsnhomsp);

	
}
