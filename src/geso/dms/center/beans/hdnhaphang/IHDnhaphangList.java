package geso.dms.center.beans.hdnhaphang;

import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IHDnhaphangList extends IPhan_Trang, Serializable {
	public ResultSet getNhaphangList();
	
	public void setNhaphangList(ResultSet nhaphangList);

	public String getUserId();
	
	public void setUserId(String userId);
	
	public String getNppId();
	
	public void setNppId(String nppId);

	public String getNppTen();
	
	public void setNppTen(String nppTen);

	public String getSKU();
	
	public void setSKU(String sku);
	
	public String getTungay();
	
	public void setTungay(String tungay);
	
	public String getDenngay();
	
	public void setDenngay(String denngay);
	
	/*public String getNpp();
	
	public void setNpp(String npp);*/
	
	public ResultSet getNppRs();
	public void setNppRs(ResultSet npplist);
	
	public String getTrangthai();

	public void setTrangthai(String trangthai);

	public String getMalist();
	
	public void setMalist(String malist);

	public String getDangnhap();
	
	public void setDangnhap(String dangnhap);
	
	public void init();
   
	public void setMsg(String Msg);
	
	public String getQuery();
	
	public void setQuery(String query);

     
	public void setDonHangId(String iddonhang);
	public String  getDonHangId();
    public String getMsg();
	public void DBclose();
	
	public int getCurrentPage();
	public void setCurrentPage(int current);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	
	public int getLastPage();


}
