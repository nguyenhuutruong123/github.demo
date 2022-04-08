package geso.dms.center.beans.daidienkinhdoanh;

import geso.dms.center.util.IPhanTrang;
import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface IDaidienkinhdoanhList extends Serializable, IPhan_Trang
{	
	HttpServletRequest getRequestObj();
	void setRequestObj(HttpServletRequest request);
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getSmartId();
	public void setSmartId(String smartid);
	
	public String getTen();
	public void setTen(String ten);
	public String getSodienthoai();
	public void setSodienthoai(String sodienthoai);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public ResultSet getDdkdList();
	public void setDdkdList(ResultSet ddkdlist);
	
	public ResultSet getKenhbanhang();
	public void setKenhbanhang(ResultSet kenhbanhang);
	public String getKbhId();
	public void setKbhId(String kbhId);
	public ResultSet getGsbanhang();
	public void setGsbanhang(ResultSet gsbanhang);
	public String getGsbhId();
	public void setGsbhId(String gsbhId);
	public ResultSet getNhaphanphoi();
	public void setNhaphanphoi(ResultSet nhaphanphoi);
	public String getNppId();
	public void setMsg(String Msg);
	public String getMsg();
	public void setNppId(String nppId);
	public String getThuviec();
	public void setThuviec(String thiec);
	public int getCurrentPage();
	public void setCurrentPage(int current);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	public void init(String search);
	public void DbClose();
	public String getView();
	public void setView(String view);
	public List<Object> getDataSearch() ;
	public void setDataSearch(List<Object> dataSearch);
}
