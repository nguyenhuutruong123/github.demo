package geso.dms.center.beans.denghitratrungbay;

import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IDeNghiTraTrungBayList extends Serializable, IPhan_Trang
{
	public String getUserId();
	public void setUserId(String userId);

	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getctId();
	public void setctId(String ctId);

	public String getNppTen();
	public void setNppTen(String nppTen);
	public ResultSet getNppRs();
	public void setNppRs(ResultSet nppRs);
	
	public ResultSet getDondathangRs();
	public void setDondathangRs(ResultSet ddhRs);
	
	public ResultSet getChungtuRs();
	public void setChungtuRs(ResultSet chungtu);
	
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
	public void DBclose();
	
	
	public String getPhanloai();
	public void setPhanloai(String phanloai);
}
