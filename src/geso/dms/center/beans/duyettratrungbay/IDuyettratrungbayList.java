package geso.dms.center.beans.duyettratrungbay;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IDuyettratrungbayList extends Serializable
{
	public String getUserId();

	public void setUserId(String UserId);

	public ResultSet getCttbRs();
	public String getCttbId();
	public void setCttbId(String cttbId);

	public int getSolantt();
	public void setSolantt(int solantt);

	public ResultSet getNppRs();
	public String getNppId();
	public void setNppId(String nppId);
	
	public String getTrangthai();
	public void setTrangthai(String tt);

	public String getMessage();

	public void setMessage(String msg);

	public ResultSet getDuyettraRS();

	public ResultSet getVung();
	public String getVungId();
	public void setVungId(String vungId);

	public ResultSet getKvRs();
	public String getKvId();
	public void setKvId(String kvId);

	public void init();
	
	public void closeDB();
	
}
