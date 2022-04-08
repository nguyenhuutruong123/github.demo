package geso.dms.distributor.beans.hoadontaichinhNPP;

import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpHoadontaichinhNPPList extends Serializable, IPhan_Trang
{
	public String getUserId();
	public void setUserId(String userId);

	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getnppId();
	public void setnppId(String nppId);
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
	
	public String getSohoadon();
	public void setSohoadon(String sohoadon);
	
	public String getSodonhang();
	public void setSodonhang(String sodonhang);
	
	public int getCurrentPage();
	public void setCurrentPage(int current);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	
	public void init(String search);
	public void DBclose();
	public boolean getIsSearch();
	public void setIsSearch(boolean search);
	public double getTongTruoc();
	public double getTongCK();
	public double getTongSau();
	public void getSumBySearch(String sumqr);
}
