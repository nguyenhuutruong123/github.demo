package geso.dms.center.beans.ttpp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface ITrungtamphanphoiList extends Serializable
{
	public String getQuery();
	public void setQuery(String query);
 
	public ResultSet getDvkdList();
	public ResultSet getNccList(boolean all);
	
	public String getDvkd();
	public void setDvkd(String dvkd);

	public String getNccId();
	public void setNccId(String nccId);
	
	public String getTungay();
	public void setTungay(String tungay);

	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public void setMsg(String Msg);
	public String getMsg();
	
	public void DBClose();
	public String getView();
	public void setView(String view);
	
	public List<Object> getDataSearch();
	public void setDataSearch(List<Object> dataSearch);
	
	public String getUserId();
	public void setUserId(String userId);
 
}
