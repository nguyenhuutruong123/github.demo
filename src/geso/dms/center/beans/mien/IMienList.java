package geso.dms.center.beans.mien;

import geso.dms.center.beans.mien.IMien;
import java.io.Serializable;
import java.util.List;

public interface IMienList extends Serializable 
{
	public String getUserId();
	public void setUserId(String userId);
		
	public String getMien();	
	public void setMien(String vungmien);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public List<IMien> getVmList();	
	public void setVmList(List<IMien> vmlist);
	public void setMsg(String Msg);
	public String getMsg();
	public void init(String search);
	public void closeDB();
}

