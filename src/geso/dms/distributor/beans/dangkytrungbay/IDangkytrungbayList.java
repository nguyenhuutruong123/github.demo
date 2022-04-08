package geso.dms.distributor.beans.dangkytrungbay;

import geso.dms.distributor.beans.dangkytrungbay.IDangkytrungbay;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IDangkytrungbayList extends Serializable
{
	public String getUserId();
	public void setUserId(String userId);

	public String getScheme();
	public void setScheme(String scheme);
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
		
	public ResultSet getDktbList();

	public void init(String search);
	public void DBclose();
}
