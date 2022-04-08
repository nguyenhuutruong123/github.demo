package geso.dms.distributor.beans.quanlykhuyenmai;

import geso.dms.center.util.IPhanTrang;
import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

public interface IChuongtrinhkhuyenmai extends Serializable, IPhan_Trang {
	
	HttpServletRequest getRequestObj();
	void setRequestObj(HttpServletRequest request);
	public String getUserId();

	public void setUserId(String userId); 
	
	public String getNppId();

	public void setNppId(String nppId);
	public String getNppTen(); 

	public void setNppTen(String nppTen); 
	
	public String getSitecode(); 

	public void setSitecode(String sitecode); 

	public String getMessage();
	public void setMessage(String msg);
	public ResultSet getSchemeRS(); 
	public void setSchemeRS(ResultSet schemeRS); 
	public Hashtable<String, String> getBudget();

	public void setBudget(Hashtable<String, String> budget);

	public Hashtable<String, String> getusedPro();
	public void setusedPro(Hashtable<String, String> usedPro);

	public String getTungay(); 

	public void setTungay(String tungay); 

	public String getDenngay(); 

	public void setDenngay(String denngay); 
	
	public String getDiengiai();

	public void setDiengiai(String diengiai); 

	public String getTrangthai(); 

	public void setTrangthai(String trangthai); 
	
	public void init();	

	public void DBclose();

}
