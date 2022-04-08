package geso.dms.center.beans.vitricuahang;

import geso.dms.center.beans.vitricuahang.IVitricuahang;

import java.io.Serializable;
import java.util.List;

public interface IVitricuahangList extends Serializable 
{
	public List<Object> getDataSearch();
	public void setDataSearch(List<Object> dataSearch);
	
	public String getUserId();
	public void setUserId(String userId);
		
	public String getVitricuahang();	
	public void setVitricuahang(String vitricuahang);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public List<IVitricuahang> getHchList();	
	public void setHchList(List<IVitricuahang> hchlist);
	
	public void init(String search);
    public void setMsg(String Msg);
    public String getMsg();
    
    public String getView();
	public void setView(String view);
	
	public void closeDB();
}

