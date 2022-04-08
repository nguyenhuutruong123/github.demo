package geso.dms.center.beans.cttrungbay;

import geso.dms.center.beans.cttrungbay.ICttrungbay;

import java.io.Serializable;
import java.util.List;

public interface ICttrungbayList extends Serializable
{
	public String getUserId();
	public void setUserId(String userId);

	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public List<ICttrungbay> getCttbList();
	public void setCttbList(List<ICttrungbay> cttblist);

	public void init(String search);
	public void DBclose();
	
	public String getMsg();
	public void setMsg(String msg);
}