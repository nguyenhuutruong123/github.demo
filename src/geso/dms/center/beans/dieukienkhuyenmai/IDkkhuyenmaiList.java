package geso.dms.center.beans.dieukienkhuyenmai;

import geso.dms.center.util.IPhan_Trang;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface IDkkhuyenmaiList extends Serializable, IPhan_Trang
{
	HttpServletRequest getRequestObj();

	void setRequestObj(HttpServletRequest request);

	public String getUserId();
	public void setUserId(String userId);

	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public ResultSet getDkkmList();
	public void setDkkmList(ResultSet dkkmlist);

	public void init(String search);
	public void setmsg(String msg);
	public String getmsg();
	
	public void DBclose();
	
	public List<Object> getDataSearch();
	public void setDataSearch(List<Object> dataSearch);
}

