package geso.dms.center.beans.phanbokhuyenmai;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Hashtable;

public interface IPhanbokhuyenmai extends Serializable
{
	public String getUserId();
	public void setUserId(String userId); 
	
	public String getSchemeId();
	public void setSchemeId(String schemeId);	
	
	public String getScheme();
	public void setScheme(String scheme);
	
	public String getMessage();
	public void setMessage(String msg);
	
	public ResultSet getSchemeRS(); 
	public void setSchemeRS(ResultSet schemeRS);
	
	public ResultSet getVung();
	public void setVung(ResultSet vung);
	
	public String getVungId();
	public void setVungId(String vungId);
	
	public ResultSet getKv();
	public void setKv(ResultSet khuvuc);
	
	public String getKvId();
	public void setKvId(String kvId);
	
	public Hashtable<String, String> getusedPro();
	public void setusedPro(Hashtable<String, String> usedPro);

	public ResultSet getSchemeKoGioiHanRs(); 
	public void setSchemeKoGioiHanRS(ResultSet schemeKoghRS); 
	
	public String getTungay();
	public void setTungay(String tungay);
	
	public String getDenngay();
	public void setDenngay(String denngay);
	
	//ctkm co gioi han ngan sach hay khong?
	public String getFlag();
	public void setFlag(String flag);
	
	
	public ResultSet getPhanboRs();
	public void setPhanboRs(ResultSet phanboRs);
	
	public String getNppId();
	public void setNppId(String nppId);
	
	public String getNppIdSearch();
	public void setNppIdSearch(String nppIdSearch);
	
	public ResultSet getNppRs();
	
	public void init();		
	
	public void DBClose();
	
	public void createSchemeRS();
	
	
	public String getPhanbo();
	public void setPhanbo(String phanbo);

	public void createPhanBoRs();
	
	
	public String getLoaingansach();
	public void setLoaingansach(String loaingansach);
	
	
	public String getActiveTab();
	public void setActiveTab(String activeTab);
	
}
