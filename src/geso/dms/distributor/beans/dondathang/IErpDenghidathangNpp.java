package geso.dms.distributor.beans.dondathang;

import java.sql.ResultSet;
import java.util.Hashtable;

import geso.dms.center.db.sql.dbutils;

public interface IErpDenghidathangNpp
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String Id);

	public String getNgayyeucau();
	public void setNgayyeucau(String ngayyeucau);
	public String getNgaydenghi();
	public void setNgaydenghi(String ngaydenghi);

	public String getGhichu();
	public void setGhichu(String ghichu);
	
	public String getKhoNhapId();
	public void setKhoNhapId(String khonhaptt);
	public ResultSet getKhoNhapRs();
	public void setKhoNHapRs(ResultSet khonhapRs);
	
	public String getNppId();
	public void setNppId(String nppId);
	public ResultSet getNppRs();
	public void setNppRs(ResultSet nppRs);
	
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
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
	
	public String getSchemeId();
	public void setSchemeId(String kbhId);
	public ResultSet getSchemeRs();
	public void setSchemeRs(ResultSet schemeRs);
	
	public String getLoaidonhang();
	public void setLoaidonhang(String loaidonhang);
	public String getChietkhau();
	public void setChietkhau(String chietkhau);
	public String getVat();
	public void setVat(String vat);
	
	
	public ResultSet getSanphamRs();
	public void setSanphamRs(ResultSet spRs);
	
	public void setSanpham_soluong(Hashtable<String, String> sp_soluong);
	public Hashtable<String, String> getSanpham_soluong();
	
	
	public ResultSet getCongnoRs();
	public void setCongnoRs(ResultSet congnoRs);
	
	public String getTansuatdathang();
	public void setTansauatdathang(String tsdh);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public boolean createNK();
	public boolean updateNK();
	

	public void createRs();
	public void init();
	
	public void DBclose();
	
	public dbutils getDb();
}
