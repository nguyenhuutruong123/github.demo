package geso.dms.distributor.beans.mucchietkhau;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;

public interface IMucchietkhau extends Serializable
{
	//Cac thuoc tinh 
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
	public String getChietkhau();
	public void setChietkhau(String chietkhau);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getNgaytao();
	public void setNgaytao(String ngaytao);
	public String getNguoitao();
	public void setNguoitao(String nguoitao);
	public String getNgaysua();
	public void setNgaysua(String ngaysua);	
	public String getNguoisua();
	public void setNguoisua(String nguoisua);
	public String getMessage();
	public void setMessage(String msg);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public ResultSet getDdkdList();
	public void setDdkdList(ResultSet ddkdlist);
	public String getDdkdId();
	public void setDdkdId(String ddkdId);
	
	public ResultSet getKhachhangList();
	public void setKhachhangList(ResultSet khachhanglist);
	public Hashtable<Integer, String> getKh_MckIds();
	public void setKh_MckIds(String[] kh_mckId);

	public ResultSet getSelectedKhlist(); 
	public void setSelectedKhlist(ResultSet selectedKhlist); 

	public boolean CreateMck(String[] khIds);
	public boolean UpdateMck(String[] khIds,List<String> list);
	public void createRS();
	public void init();
	public void DBclose();
	public List<String> getListKHCK(String ckId);
}
