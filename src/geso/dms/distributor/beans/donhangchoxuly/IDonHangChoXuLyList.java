package geso.dms.distributor.beans.donhangchoxuly;

import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IDonHangChoXuLyList extends IPhan_Trang, Serializable
{	
	public String getUserId(); 
	public void setUserId(String userId);
	
	public ResultSet getDaidienkd();
	public void setDaidienkd(ResultSet daidienkd);	
	public String getDdkdId();
	public void setDdkdId(String ddkdId);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getTumuc();
	public void setTumuc(String tumuc);
	public String getDenmuc();
	public void setDenmuc(String denmuc);
	
	public String getSohoadon();
	public void setSohoadon(String sohoadon);
	public String getKhachhang();
	public void setKhachhang(String khachhang);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);

	public ResultSet getDonhangRs();
	public void setDonhangRs(ResultSet dhRs);
	
	public void init(String search);
	public void DBclose();
	
	public String getMsg();
	public void setMsg(String msg);
	
	public int getCurrentPage();
	public void setCurrentPage(int current);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	public int getLastPage();
	
	public String getId();
	public void setId(String id);
	
}

