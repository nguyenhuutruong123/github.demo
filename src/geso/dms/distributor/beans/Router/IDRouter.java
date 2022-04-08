package geso.dms.distributor.beans.Router;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IDRouter extends Serializable {
   
	public void setnppId(String nppId);
	public String getnppId();
	public void setddkdId(String ddkdId);
	public String getddkdId();
	public ResultSet getnpp();
	public ResultSet getddkd();
	public void settuyenId(String tuyenId);
	public String gettuyenId();
	public ResultSet getdanhsach();
	public ResultSet getTuyen();
	public void setuserId(String userId);
	public String getuserId();
	public void init();
	public void DBclose();
	
}
