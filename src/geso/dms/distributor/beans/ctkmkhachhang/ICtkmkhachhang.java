package geso.dms.distributor.beans.ctkmkhachhang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Hashtable;

public interface ICtkmkhachhang extends Serializable
{
	public void setuserId(String userId);  
	public String getuserId();

	public void setTrangthai(String Trangthai);
	public String getTrangthai();
    
	public void setDiengiai(String Diengiai);
	public String getDiengiai();
	
	public ResultSet getDsnpp();
	public void setId(String Id);
	
	public String getId();
	
	public void setnppId(String nppId);
	public String getnppTen();
	public void setnppTen(String nppId);
    
	public void setScheme(String Scheme);
	public String getScheme();
	
	public void setDskh(ResultSet Dskh);
	
	public ResultSet getDskh();
	
	public String getnppId();
	public void setCtkmId(String ctkmId);
	public String getCtkmId();
	
	public ResultSet getDdkdList();
	public void setDdkdList(ResultSet ddkdlist);
	public void setDdkdIds(String[] ddkdIds);
	public Hashtable<Integer, String> getDdkdIds(); 
	
	public boolean save();
	public void setKhachhang(String[] khachhang);
	public String[] getKhachhang();
	public void setSchemes(ResultSet Schemes);
	public ResultSet getSchemes();
	public void init();
	public void DBclose();
	
	public void setTen(String ten);
	public String getTen();
	public void setTungay(String tungay);
	public String getTungay();
	public void setDenngay(String denngay);
	public String getDenngay();
	

}
