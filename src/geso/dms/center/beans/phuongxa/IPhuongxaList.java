package geso.dms.center.beans.phuongxa;

import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IPhuongxaList extends IPhan_Trang, Serializable
{
	public String getUserId();
	public void setUserId(String userId);
	
	public ResultSet getTinhthanhRs();
	public void setTinhthanhRs(ResultSet ttRs);
	public String getTinhthanhId();
	public void setTinhthanhId(String ttId);
	
	public ResultSet getQuanhuyenRs();
	public void setQuanhuyenRs(ResultSet qhRs);
	public String getQuanhuyenId();
	public void setQuanhuyenId(String qhId);
	
	public ResultSet getPhuongxaRs();
	public void setPhuongxaRs(ResultSet pxRs);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public void init(String search);
	public void setMsg(String Msg);
	public String getMsg();
	
	public void createRS();
	
	public int getCurrentPage();
	public void setCurrentPage(int current);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	public int getLastPage();
	
	public List<Object> getDataSearch() ;
	public void setDataSearch(List<Object> dataSearch);
}
