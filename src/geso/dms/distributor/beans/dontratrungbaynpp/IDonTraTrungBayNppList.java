package geso.dms.distributor.beans.dontratrungbaynpp;

import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IDonTraTrungBayNppList extends Serializable, IPhan_Trang
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
	
	
	public int getCurrentPage();
	public void setCurrentPage(int current);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	
	public void init(String search);
	public void DBclose();
	
	//Tim kiem theo makh, tenkh
	public String getMaKh();
	public void setMaKh(String makh);
	
	public String getTenKh();
	public void setTenKh(String tenkh);
	
	public ResultSet getKhRs();
	public void setKhRs(ResultSet khRs);
	
	public void CreateKhRs();
	//.
}
