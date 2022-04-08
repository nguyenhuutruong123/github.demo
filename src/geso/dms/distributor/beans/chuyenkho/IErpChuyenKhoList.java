package geso.dms.distributor.beans.chuyenkho;

import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpChuyenKhoList extends Serializable, IPhan_Trang
{
	public String getUserId();
	public void setUserId(String userId);

	public String getTungayTao();
	public void setTungayTao(String tungay);
	public String getDenngayTao();
	public void setDenngayTao(String denngay);
	
	public String getSophieu();
	public void setSophieu(String sophieu);
	
	public ResultSet getNhapkhoRs();
	public void setNhapkhoRs(ResultSet nkRs);
	
	public ResultSet getKhRs();
	public void setKhRs(ResultSet khrs);
	
	public String getKhId();
	public void setKhId(String KhId);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public String getSochungtu();
	public void setSochungtu(String sochungtu);
	
	public int getCurrentPage();
	public void setCurrentPage(int current);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	
	public void init(String search);
	public void DBclose();
	
	public String getType();
	public void setType(String type);
	
}
