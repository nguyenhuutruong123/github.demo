package geso.dms.distributor.beans.mucchietkhau;

import geso.dms.distributor.beans.mucchietkhau.IMucchietkhau;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IMucchietkhauList extends Serializable 
{
	public String getUserId();
	public void setUserId(String userId);
	public String getMucchietkhau();
	public void setMucchietkhau(String mucchietkhau);
	public String getKhachhang();
	public void setKhachhang(String khachhang);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
		
	public List<IMucchietkhau> getMckList();
	public void setMucchietkhauList(List<IMucchietkhau> mcklist);
	
	public void init(String search);
	public void DBclose();
	public void setMsg(String Msg);
	public String getMsg();
}
