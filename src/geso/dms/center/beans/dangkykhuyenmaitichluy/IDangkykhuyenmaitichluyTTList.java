package geso.dms.center.beans.dangkykhuyenmaitichluy;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IDangkykhuyenmaitichluyTTList extends Serializable {
	public String getUserId();

	public void setUserId(String userId);
	
	public String getNppId();
	
	public void setDiengiai(String diengiai);
	public String getDiengiai();
	public void setNppTen(String nppTen);
	public String getNppTen();
	public void setNppId(String nppId);

	public void setTungay(String tungay);
	public String getTungay();
	
	public void setTrangthai(String trangthai);
	public String getTrangthai();

	public void setDenngay(String denngay);
	public String getDenngay();

	public void setTen(String ten);
	public String getTen();
	
	public void setDsTichluy(ResultSet dstichluy);
	public ResultSet getDsTichluy();
	public void init();
	public void DBclose();

	public String getMsg() ;
	public void setMsg(String msg);
}
