package geso.dms.center.beans.khuvuc;

import geso.dms.center.beans.khuvuc.IKhuvuc;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IKhuvucList extends Serializable
{
	public String getUserId();
	public void setUserId(String userId);
	
	public ResultSet getVungMien();
	public void setVungmien(ResultSet vungmien);
	public String getVmId();
	public void setVmId(String vmId);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public List<IKhuvuc> getKvList();
	public void setKvList(List<IKhuvuc> kvlist);
	
	public void init(String search);
	public void setMsg(String Msg);
	public String getMsg();
	
	public List<Object> getDataSearch() ;
	public void setDataSearch(List<Object> dataSearch);
}
