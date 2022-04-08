package geso.dms.distributor.beans.catalog;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IcatalogList extends Serializable {

	public String getTen();

	public void setTen(String ten);

	public String getChungloai();

	public void setChungloai(String chungloai);
	public String getGhichu();

	public void setGhichu(String ghichu);

	public String getDuongdan();

	public void setDuongdan(String duongdan);

	public ResultSet getCatalogList();

	public void setCatalogList(ResultSet catalogList);
	public void init(String search);
	public String getMsg();
	public void setMsg(String msg);
}