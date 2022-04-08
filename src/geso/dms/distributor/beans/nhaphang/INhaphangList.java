package geso.dms.distributor.beans.nhaphang;

import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface INhaphangList extends Serializable, IPhan_Trang 
{
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
	public void HangVe(String id);
	
	public String getTrangthai();

	public void setTrangthai(String trangthai);

	public String getMalist();
	
	public void setMalist(String malist);

	public String getDangnhap();
	
	public void setDangnhap(String dangnhap);
	
	public void createNhaphanglist(String querystr);
   
	public void setMsg(String Msg);
     
	public void setDonHangId(String iddonhang);
	public String  getDonHangId();
    public String getMsg();
	public void DBclose();
	
	public List<Object> getDataSearch();


}
