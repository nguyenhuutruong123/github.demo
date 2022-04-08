package geso.dms.distributor.beans.phieuthuhoi;

import geso.dms.center.util.IPhan_Trang;
import geso.dms.distributor.beans.phieuthuhoi.IPhieuthuhoi;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IPhieuthuhoiList extends IPhan_Trang, Serializable
{
	public String getUserId();
	public void setUserId(String userId);

	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getTungay();
	public void setTungay(String tungay);
	
	public ResultSet getNhanvienGN();
	public void setNhanvienGN(ResultSet nhanviengn);	
	public String getNvgnId();
	public void setNvgnId(String nvgnId);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public String getMsg();
	public void setMsg(String msg);
		
	public List<IPhieuthuhoi> getPthList();
	public void setPthList(List<IPhieuthuhoi> pthlist);

	public void init(String search);
	public void DBclose();
}
