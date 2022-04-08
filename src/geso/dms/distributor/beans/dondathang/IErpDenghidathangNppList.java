package geso.dms.distributor.beans.dondathang;

import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IErpDenghidathangNppList extends Serializable, IPhan_Trang
{
	public String getUserId();
	public void setUserId(String userId);

	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public ResultSet getDondathangRs();
	public void setDondathangRs(ResultSet ddhRs);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public String getLoaidonhang();
	public void setLoaidonhang(String loaidonhang);
	
	public int getCurrentPage();
	public void setCurrentPage(int current);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	
	public void init(String search);
	
	public List<Object> getDataSearch();
	public void setDataSearch(List<Object> dataSearch);
	public dbutils getDb();
	public void DBclose();
}
