package geso.dms.center.beans.tratrungbay;

import geso.dms.center.beans.tratrungbay.ITratrungbay;
import java.io.Serializable;
import java.util.List;

public interface ITratrungbayList extends Serializable
{
	public String getUserId();
	public void setUserId(String userId);

	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public List<ITratrungbay> getTratbList();
	public void setTratbList(List<ITratrungbay> tratblist);

	public void init(String search);
	public void DBclose();
	public void setMsg(String msg);
	public String getMsg();
	
	public List<Object> getDataSearch();
	public void setDataSearch(List<Object> dataSearch);
}

