package geso.dms.center.beans.phieuxuatkhodms;

import java.sql.ResultSet;
import java.util.Hashtable;

public interface IPhieuxuatkhoDms
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String Id);

	public String getSochungtu();
	public void setSochungtu(String sochungtu);
	
	public String getSopo();
	public void setSopo(String sopo);
	
	public String getSoso();
	public void setSoso(String soso);
	
	public String getNgaychungtu();
	public void setNgaychungtu(String ngaychungtu);

	public String getGhichu();
	public void setGhichu(String ghichu);
	
	public String getNppId();
	public void setNppId(String nppId);
	public ResultSet getNppRs();
	public void setNppRs(ResultSet nppRs);
	
	public String getDvkdId();
	public void setDvkdId(String dvkdId);
	public ResultSet getDvkdRs();
	public void setDvkdRs(ResultSet dvkdRs);
	
	public String getKbhId();
	public void setKbhId(String kbhId);
	public ResultSet getKbhRs();
	public void setKbhRs(ResultSet kbhRs);
	
	public ResultSet getDvtRs();
	public void setDvtRs(ResultSet dvtRs);
	
	public ResultSet getSchemeRs();
	public void setSchemeRs(ResultSet schemeRs);
	
	public String[] getSpId();
	public void setSpId(String[] spId);
	public String[] getSpMa();
	public void setSpMa(String[] spMa);
	public String[] getSpTen();
	public void setSpTen(String[] spTen);
	public String[] getSpDonvi();
	public void setSpDonvi(String[] spDonvi);
	public String[] getSpSoluong();
	public void setSpSoluong(String[] spSoluong);
	public String[] getSpGianhap();
	public void setSpGianhap(String[] spGianhap);
	
	public String[] getSpScheme();
	public void setSpScheme(String[] spScheme);
	public String[] getSpVat();
	public void setSpVat(String[] spVat);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public boolean createNK();
	public boolean updateNK();
	
	public void createRs();
	public void init();
	
	public void DBclose();
}


