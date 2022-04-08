package geso.dms.center.beans.nhacungcap;

import java.io.Serializable;

import geso.dms.center.beans.nhacungcap.INhacungcap;

import java.util.List;
import java.sql.ResultSet;

public interface INhacungcapList extends Serializable {
	public void init();
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getQuery();
	
	public void setQuery(String query);
	
	public ResultSet getNccList();
	
	public void setNccList(ResultSet ncclist);
	
	public String getNhacungcap();
	
	public void setNhacungcap(String nhacungcap);
	
	public String getTenviettat();
	
	public void setTenviettat(String tenviettat);
	
	public String getTungay();
	
	public void setTungay(String tungay);

	public String getDenngay();
	
	public void setDenngay(String denngay);
	
	public String getTrangthai();
	
	public void setTrangthai(String trangthai);
	public void setMsg(String Msg);
	public String getMsg();
	
	public String getView();
	public void setView(String view);
	public void DBClose();
	
	public List<Object> getDataSearch();
	public void setDataSearch(List<Object> dataSearch);
}