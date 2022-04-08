package geso.dms.center.beans.diaban;

import geso.dms.center.beans.diaban.IDiaban;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IDiabanList extends Serializable
{
	public String getUserId();
	public void setUserId(String userId);
	
	public ResultSet getKhuvucRs();

	public String getKhuvucId();
	public void setKhuvucId(String vmId);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public List<IDiaban> getDiabanList();
	public void setDiabanList(List<IDiaban> dblist);
	
	public void init(String search);
	public void setMsg(String Msg);
	public String getMsg();
	
	public void closeDB();
}
