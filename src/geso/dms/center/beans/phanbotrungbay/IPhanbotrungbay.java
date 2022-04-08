package geso.dms.center.beans.phanbotrungbay;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

public interface IPhanbotrungbay extends Serializable
{
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
	public ResultSet getNpp();
	public void setNpp(ResultSet nppRS);
	public Hashtable<String, Integer> getngansach();	
	public void setngansach(Hashtable<String, Integer> value);
	public Hashtable<String, Integer> getSTT();
	public void setSTT(Hashtable<String, Integer> value);
	public Hashtable<String, String> getusedPro();
	public void getId_npp(String manpp, Hashtable<String, Integer> npp_nsach, Hashtable<String, Integer> kh_st);
	public void setusedPro(Hashtable<String, String> usedPro);
	
	public void init();
	
	public boolean save(HttpServletRequest request);
	public void DBClose();
}
