package geso.dms.distributor.beans.donhangthuhoi;


import geso.dms.distributor.beans.donhangthuhoi.IDonhangthuhoi;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IDonhangthuhoiList extends Serializable
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
		
	public List<IDonhangthuhoi> getDhthList();
	public void setDhthList(List<IDonhangthuhoi> Dhthlist);

	public void init(String search);
	public void DBclose();
}
