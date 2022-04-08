package geso.dms.distributor.beans.hoadontaichinh;

import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IHoadontaichinhList extends Serializable, IPhan_Trang
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
	
	public String getKhTen();
	public void setKhTen(String KhTen);
	public ResultSet getKhRs();
	public void setKhRs(ResultSet KhRs);
	
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
	
	public String getNppId();
	public void setNppId(String nppId) ;
	
	public void init(String search);
	public void init_dieuchinh(String search);
	public void DBclose();
	
	public String getSoHoaDon();
	public void setSoHoaDon(String sohoadon);
	
	public String getMadonhang();
	public void setMadonhang(String madonhang);
	
	
	public String getHoadonId();
	public void setHoadonId(String hoadonId);
	
	public String getType();
	public void setType(String type);
	
}
