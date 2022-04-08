package geso.dms.center.beans.dondathang;

import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IErpDondathangList extends Serializable, IPhan_Trang
{
	public String getUserId();
	public void setUserId(String userId);

	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getNppTen();
	public void setNppTen(String nppTen);
	public ResultSet getNppRs();
	public void setNppRs(ResultSet nppRs);
	
	public String getKbhId();
	public void setKbhId(String kbhId);
	public ResultSet getKbhRs();
	public void setKbhRs(ResultSet kbhRs);
	
	public String getVungId();
	public void setVungId(String vungId);
	public ResultSet getVungRs();
	public void setVungRs(ResultSet vungRs);
	
	public String getKhuvucId();
	public void setKhuvucId(String kvId);
	public ResultSet getKhuvucRs();
	public void setKhuvucRs(ResultSet kvRs);
	
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
	
	public String getSoDonDathang();
	public void setSoDonDathang(String SoDonDathang);
	
	public void init(String search);
	public void DBclose();
	
	public List<Object> getDataSearch();
	public void setDataSearch(List<Object> dataSearch);
}
