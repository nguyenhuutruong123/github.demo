package geso.dms.distributor.beans.Nhaphangnpp;

import java.io.Serializable;
import java.sql.ResultSet;

public interface INhaphangnppList extends Serializable
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

	public String getTrangthai();

	public void setTrangthai(String trangthai);

	public String getMalist();

	public void setMalist(String malist);

	public String getDangnhap();

	public void setDangnhap(String dangnhap);

	public void createNhaphanglist(String querystr);

	public void setMsg(String Msg);

	public void setDonHangId(String iddonhang);

	public String getDonHangId();

	public String getMsg();

	public String getSoct();

	public void setSoct(String soct);

	public String getSodh();

	public void setSodh(String sodh);
	public void DBclose();
}
