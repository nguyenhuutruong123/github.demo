package geso.dms.center.beans.kehoachbanhang;

import geso.dms.center.util.IPhanTrang;
import geso.dms.center.util.IPhan_Trang;
import geso.dms.distributor.beans.tuyenbanhang.ITuyenbanhang;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface IKehoachbanhangList extends Serializable, IPhan_Trang
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
	
	public String getTrangthai();
	public void setTrangthai(String Trangthai);
	
	
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public List<IKehoachbanhang> getTbhList();
	public void setTbhList(List<IKehoachbanhang> tbhlist);
	
	public void init(String search);
	public void DBclose();
	public void setMsg (String msg);
	public String getMsg();
}
