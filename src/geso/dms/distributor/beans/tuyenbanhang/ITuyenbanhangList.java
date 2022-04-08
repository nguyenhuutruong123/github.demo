package geso.dms.distributor.beans.tuyenbanhang;

import geso.dms.center.util.IPhanTrang;
import geso.dms.center.util.IPhan_Trang;
import geso.dms.distributor.beans.tuyenbanhang.ITuyenbanhang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface ITuyenbanhangList extends Serializable, IPhan_Trang
{
	HttpServletRequest getRequestObj();
	void setRequestObj(HttpServletRequest request);
	
	public String getUserId(); //neu dang nhap la nha phan phoi thi hieu ngam la nppID
	public void setUserId(String userId);
	
	public String getTuyenbh();
	public void setTuyenbh(String tuyenbh);
	
	public ResultSet getDdkd();
	public void setDdkd(ResultSet ddkd);
	public String getDdkdId();
	public void setDdkdId(String ddkdId);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public List<ITuyenbanhang> getTbhList();
	public void setTbhList(List<ITuyenbanhang> tbhlist);
	
	public List<Object> getDatasearch() ;
	public void setDatasearch(List<Object> datasearch) ;
	
	public void init(String search);
	public void DBclose();
	public void setMsg (String msg);
	public String getMsg();
	public String getView();
	public void setView(String view);
	public String getNpp_fk_ho();
	public void setNpp_fk_ho(String npp_fk_ho);
	public ResultSet getNppRs_ho();
	public void setNppRs_ho(ResultSet nppRs_ho);
}
