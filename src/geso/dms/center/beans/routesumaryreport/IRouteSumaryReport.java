package geso.dms.center.beans.routesumaryreport;

import java.sql.ResultSet;
import java.util.Hashtable;

public interface IRouteSumaryReport {
	public void setuserId(String userId);
	public String getuserId();
	
	public void setuserTen(String userTen);
	public String getuserTen();
		
	public void setnppId(String nppId);
	public String getnppId();
	
	public void setNppTen(String nppTen);
	public String getNppTen();
	
	public void setKhuVuc(String erea);
	public String getKhuVuc();
	
	public void setHashStatus();
	public Hashtable<String, String> getHashStatus();
	
	public void setArea(ResultSet erea);
	public ResultSet getArea();
	
	public void setStatus(String status);
	public String getStatus();
	
	public void setDistributor(ResultSet distributor);
	public ResultSet getDistributor();
	

	
	public void init();
	public void DBClose();
	
}
