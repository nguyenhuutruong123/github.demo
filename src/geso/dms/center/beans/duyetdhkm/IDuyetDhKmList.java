package geso.dms.center.beans.duyetdhkm;
import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IDuyetDhKmList extends  Serializable, IPhan_Trang 
{
	public String getUserId();
	public void setUserId(String userId);
		
	public String getVungmien();	
	public void setVungmien(String vungmien);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public ResultSet getrslist();
	public void setrslist(ResultSet rs);
	
	public void setMsg(String Msg);
	public String getMsg();
	public void init(String search);
	public void closeDB();
	
	public String getThang();
	public void setThang(String thang);
	
	
	public String getNam();
	public void setNam(String nam);
	
	public String getCtkmId();
	public void setCtkmId(String ctkmId);
	
	public String getNppId();
	public void setNppId(String nppId);
	
	public ResultSet getNppRs();
	public void setNppRs(ResultSet nppRs);
	
	
	public ResultSet getCtkmRs();
	public void setCtkmRs(ResultSet ctkmRs);
	

}

