package geso.dms.center.beans.hangcuahang;

import geso.dms.center.beans.hangcuahang.IHangcuahang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IHangcuahangList extends Serializable 
{
	public List<Object> getDataSearch();
	public void setDataSearch(List<Object> dataSearch);
	
	public String getUserId();
	public void setUserId(String userId);
		
	public String getHangcuahang();	
	public void setHangcuahang(String hangcuahang);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	//public List<IHangcuahang> getHchList();	
	//public void setHchList(List<IHangcuahang> hchlist);
	public ResultSet getHchList();	
	public void setHchList(ResultSet hchlist);
	
	public void init(String search);
	public void closeDB();
	public void setMsg(String Msg);
	public String getMsg();
	public String getDateTime(); 
	
	public String getView();
	public void setView(String view);
}
