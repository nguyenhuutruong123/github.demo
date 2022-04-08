package geso.dms.center.beans.tieuchithuong;

import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface ITieuchithuongList    extends  Serializable, IPhan_Trang
{
	
public void setDvkdId(String dvkdId);
	
	public String getdvkdId();

	public void setDvkd(ResultSet dvkd);
	
	public ResultSet getdvkd();
	
	public void setKbhId(String kbhId);
	
	public String getkbhId();

	public void setKbh(ResultSet kbh);
	
	public ResultSet getKbh();

	public void setMsg(String msg);
	
	public String getMsg();

	public void setUserId(String userId);
	
	public String getUserId();
	
	public void setLoai(String loai);
	
	public String getLoai();
	
	public void setMonth(String month);
	
	public String getMonth();
	
	public void setYear(String year);
	
	public String getYear();
	
	
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	
	public void setTct(ResultSet tct);
	
	public ResultSet getTct();
	
	public void closeDB();
	
	public void init();
	
	public List<Object> getDataSearch();
	public void setDataSearch(List<Object> dataSearch);
}
