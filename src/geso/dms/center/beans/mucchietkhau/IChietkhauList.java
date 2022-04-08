package geso.dms.center.beans.mucchietkhau;

import geso.dms.center.beans.mucchietkhau.IChietkhau;

import java.util.List;

public interface IChietkhauList 
{
	public String getUserId();
	public void setUserId(String userId);
		
	public String getSotien();	
	public void setSotien(String sotien);
	
	public String getChietkhau();
	public void setChietkhau(String chietkhau);
	
	public List<IChietkhau> getChietkhauList();	
	public void setChietkhauList(List<IChietkhau> cklist);
	
	public void setMsg(String Msg);
	public String getMsg();
	
	public void init(String search);
	public void DBClose();
}
