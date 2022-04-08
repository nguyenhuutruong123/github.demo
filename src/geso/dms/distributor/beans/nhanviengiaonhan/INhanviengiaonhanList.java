package geso.dms.distributor.beans.nhanviengiaonhan;

import java.io.Serializable;

import geso.dms.distributor.beans.nhanviengiaonhan.INhanviengiaonhan;

import java.sql.ResultSet;
import java.util.List;

public interface INhanviengiaonhanList extends Serializable
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getTennv();
	public void setTennv(String tennv);	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getMsg() ;

	public void setMsg(String msg);
	public ResultSet getDdkd();
	public void setDdkd(ResultSet ddkd);
	public String getDdkdId();
	public void setDdkdId(String ddkdId);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	public List<Object> getDatasearch();
	public void setDatasearch(List<Object> datasearch);
	public List<INhanviengiaonhan> getNvgnList();
	public void setNvgnList(List<INhanviengiaonhan> nvgnList);
	
	public void init(String search);
	public void DBclose();
}