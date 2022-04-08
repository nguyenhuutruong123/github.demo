package geso.dms.distributor.beans.dondathang;

import java.sql.ResultSet;

import geso.dms.center.db.sql.dbutils;

public interface IErpDondathangNpp
{
	public String[] getSpSoluongNppDat() ;
	
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
	
	public String getCongno();
	public void setCongno(String congno);
	
	public String[] getSpId();
	public void setSpId(String[] spId);
	public String[] getSpMa();
	public void setSpMa(String[] spMa);
	public String[] getSpTen();
	public void setSpTen(String[] spTen);
	public String[] getSpDonvi();
	public void setSpDonvi(String[] spDonvi);
	public String[] getSpTonkho();
	public void setSpTonkho(String[] spTonkho);
	public String[] getSpSoluong();
	public void setSpSoluong(String[] spSoluong);
	public String[] getSpGianhap();
	public void setSpGianhap(String[] spGianhap);
	public String[] getSpChietkhau();
	public void setSpChietkhau(String[] spChietkhau);
	public String[] getSpScheme();
	public void setSpScheme(String[] spScheme);
	public String[] getSpVat();
	public void setSpVat(String[] spVat);
	public String[] getSoluongtt();
	public void setSoluongtt(String[] spSoluongtt);
	public String[] getSpGhichu();
	public void setSpGhichu(String[] spGhichu);
	
	public String[] getSpGiagoc();
	public void setSpGiagoc(String[] spgiagoc);
	
	public String[] getSpBgId();
	public void setSpBgId(String[] bgId);
	public String[] getSpTrakmId();
	public void setSpTrakmId(String[] spTrakmId); 
	
	
	public String[] getDhck_diengiai();
	public void setDhck_Diengiai(String[] obj);
	public String[] getDhck_giatri();
	public void setDhck_giatri(String[] obj);
	public String[] getDhck_loai();
	public void setDhck_loai(String[] obj);
	
	public String[] getSpNgaygiaohang();
	public void setSpNgaygiaohang(String[] spNgaygiaohang);
	
	
	public ResultSet getCongnoRs();
	public void setCongnoRs(ResultSet congnoRs);
	
	
	public String getMsg();
	public void setMsg(String msg);
	
	public boolean createNK();
	public boolean updateNK();
	

	public void createRs();
	public void init();
	
	public void DBclose();
	
	public boolean isAplaikhuyenmai(); //bien dung bat nguoi dung phai ap lai khuyen mai neu vao` sua don hang da co km
	public void setAplaikhuyenmai(boolean aplaikm);
	
	public String getTiengiam();
	public void setTiengiam(String tiengiam);
	
	public double getPtChietKhau();
	public void setPtChietKhau(double ptChietKhau);
	
	public dbutils getDb();
}
