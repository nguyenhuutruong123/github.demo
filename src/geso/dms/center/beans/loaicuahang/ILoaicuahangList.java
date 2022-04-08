package geso.dms.center.beans.loaicuahang;

import geso.dms.center.beans.loaicuahang.ILoaicuahang;
import java.io.Serializable;
import java.util.List;

public interface ILoaicuahangList extends Serializable 
{
	public List<Object> getDataSearch();
	public void setDataSearch(List<Object> dataSearch);
	
	public String getUserId();
	public void setUserId(String userId);
		
	public String getLoaicuahang();	
	public void setLoaicuahang(String loaicuahang);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public List<ILoaicuahang> getLchList();	
	public void setLchList(List<ILoaicuahang> lchlist);
	public void setMsg(String Msg);
	public String getMsg();
	public void init(String search);
	public void closeDB();
	
	public String getView();
	public void setView(String view);
}
