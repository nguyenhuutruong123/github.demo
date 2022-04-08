package geso.dms.center.beans.khaibaockdathang;

import geso.dms.center.beans.khaibaockdathang.IKhaibaoCKdathang;

import java.sql.ResultSet;
import java.util.List;

public interface IKhaibaoCKdathanglist {

	public String getUserId();
	public void setUserId(String userId);
		
	public String getScheme();	
	public void setScheme(String scheme);
	
	public String getLoaiCK();
	public void setLoaiCK(String loaick);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getTungay();
	public void setTungay(String tungay);
	
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getNppId();
	public void setNppId(String nppId);
	
	public ResultSet getNPPList();
	public void setNPPList(ResultSet npplist);
	
	public List<IKhaibaoCKdathang> getKhaibaoCKdathangList();	
	public void setKhaibaoCKdathangList(List<IKhaibaoCKdathang> cklist);
	
	public void setMsg(String Msg);
	public String getMsg();
	
	public void init(String search);
	public void DBClose();
}
