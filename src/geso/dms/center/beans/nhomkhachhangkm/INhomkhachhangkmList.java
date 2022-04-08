package geso.dms.center.beans.nhomkhachhangkm;

import java.sql.ResultSet;

public interface INhomkhachhangkmList {
    
	public void setuserId(String userId);
	
	public String getuserId();
	
	public void setDiengiai(String Diengiai);
	
	public String getDiengiai();
	
	public void setTrangthai(String Trangthai);
	
	public String getTrangthai();
    
	public void setTungay(String Tungay);
	
	public String getTungay();
	
    public void setDenngay(String Denngay);
	
	public String getDenngay();
	
	public void setDskh(ResultSet Dskh);
	
	public ResultSet getDskh();
	
	public void init(String st);
	public void setmsg(String msg);
	public String getmsg();
}
