package geso.dms.distributor.beans.denghidathangnpp;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

public interface IDenghidathangnppList {
public String getUserId();
	
	public void setUserId(String userId);
	
	public String getNppId();
	public void setNppId(String nppId);
	
	public String getNppTen();
	public void setNppTen(String nppTen);

	public ResultSet getDndhList();
	public void setDndhList(ResultSet dhList);
	
	public String getSKU();
	public void setSKU(String sku);
	
	public String getTungay();
	public void setTungay(String tungay);
	
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);

	public String getOrdered();	
	public void setOrdered(String ordered);
	
	public String getMalist();
	public void setMalist(String malist);

	public String getMaspstr(); 
	public void setMaspstr(String maspstr); 
	
	public String getMessage();
	
	public void createDndhlist();
	public void DBclose();
}
