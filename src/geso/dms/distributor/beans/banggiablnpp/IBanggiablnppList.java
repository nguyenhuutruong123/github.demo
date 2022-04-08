package geso.dms.distributor.beans.banggiablnpp;

import geso.dms.distributor.beans.banggiablnpp.IBanggiablnpp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IBanggiablnppList extends Serializable
{	
	public List<Object> getDataSearch();
	public void setDataSearch(List<Object> dataSearch) ;
	public String getMsg();
	public void setMsg(String msg);
	public String getUserId();
	public void setUserId(String userId);
	public String getView() ;
	public ResultSet getBgblList() ;
	public void setBgblList(ResultSet bgblList) ;
	public void setView(String view) ;
	public String getTenbanggia();
	public void setTenbanggia(String tenbanggia);
		
	public ResultSet getNcc();
	public void setNcc(ResultSet ncc);
	public String  getNccId();
	public void setNccId(String nccId);
	
	public ResultSet getDvkd();
	public void setDvkd(ResultSet dvkd);
	public String  getDvkdId();
	public void setDvkdId(String dvkdId);
	

	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public void init(String search);
	public void DBclose();
}