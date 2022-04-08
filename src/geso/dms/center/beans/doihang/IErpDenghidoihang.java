package geso.dms.center.beans.doihang;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

public interface IErpDenghidoihang
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
	
	public String getKhoTTDoiId();
	public void setKhoTTDoiId(String khottDoiId);
	public ResultSet getKhoTTDoiRs();
	public void setKhoTTDoiRs(ResultSet khottDoiRs);
	
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
	
	public String getLoaidonhang();
	public void setLoaidonhang(String loaidonhang);
	public String getChietkhau();
	public void setChietkhau(String chietkhau);
	public String getVat();
	public void setVat(String vat);
	
	public ResultSet getSanphamRs();
	public void setSanphamRs(ResultSet spRs);
	
	public ResultSet getSanphamDoiRs();
	public void setSanphamDoiRs(ResultSet spDoiRs);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public boolean createNK(HttpServletRequest request);
	public boolean updateNK(HttpServletRequest request);
	public boolean duyetDH(HttpServletRequest request);
	

	public void createRs();
	public void init();
	
	public void DBclose();
}
