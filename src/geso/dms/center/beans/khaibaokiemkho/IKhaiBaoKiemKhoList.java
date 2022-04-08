package geso.dms.center.beans.khaibaokiemkho;

import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IKhaiBaoKiemKhoList extends  Serializable, IPhan_Trang
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public String getTungay();
	public void setTungay(String value);
	
	public String getDenngay();
	public void setDenngay(String value);
	
	public ResultSet getKiemkhoRs();
	
	public void init(String query);
	public void DbClose();
}
