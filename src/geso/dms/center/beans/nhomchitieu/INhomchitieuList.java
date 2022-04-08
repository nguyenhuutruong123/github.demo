package geso.dms.center.beans.nhomchitieu;

import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface INhomchitieuList  extends  Serializable, IPhan_Trang
{
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getTungay();
	public void setTungay(String tungay);

	public String getDenngay();
	public void setDenngay(String denngay);
	
	public void setDsnkm(ResultSet Dsnkm);
	public ResultSet getDsnkm();	
	
	public void init();
	
	public String getUserId();
	public void setUserId(String userId);
	

}