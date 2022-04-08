package geso.dms.center.beans.vung;

import geso.dms.center.beans.vung.IVungmien;

import java.io.Serializable;
import java.util.List;

public interface IVungmienList extends Serializable 
{
	public String getUserId();
	public void setUserId(String userId);
		
	public String getVungmien();	
	public void setVungmien(String vungmien);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public List<IVungmien> getVmList();	
	public void setVmList(List<IVungmien> vmlist);
	public void setMsg(String Msg);
	public String getMsg();
	public void init(String search);
	public void closeDB();
	
	public List<Object> getDataSearch() ;
	public void setDataSearch(List<Object> dataSearch) ;
}

