package geso.dms.center.beans.hinhthuckinhdoanh;

import java.sql.ResultSet;
import java.io.Serializable;

public interface IHinhthuckinhdoanhList extends Serializable{

	public String getMa();
	
	public void setMa(String ma);

	public String getDiengiai();
	
	public void setDiengiai (String diengiai);
	
	public String getTungay();
	
	public void setTungay(String tungay);

	public String getDenngay();
	
	public void setDenngay(String denngay);
	
	public String getTrangthai();

	public void setTrangthai(String trangthai);
	
	public String getQuery();
	
	public void setQuery(String query);
	
	public ResultSet getHtkdlist();
		
	public void setMsg(String Msg);
	
	public String getMsg();
	
	public void DBClose();
}
