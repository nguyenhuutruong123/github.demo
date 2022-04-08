package geso.dms.center.beans.dondathang;

import java.sql.ResultSet;
import java.util.Hashtable;

public interface IErpDondathang
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
	
	public String getCongNo();
	public void setCongNo(String congno);
	
	public String getKhoNhapId();
	public void setKhoNhapId(String khonhaptt);
	public ResultSet getKhoNhapRs();
	public void setKhoNHapRs(ResultSet khonhapRs);
	
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
	
	public String getSchemeId();
	public void setSchemeId(String kbhId);
	public ResultSet getSchemeRs();
	public void setSchemeRs(ResultSet schemeRs);
	
	public Hashtable<String, String> getScheme_Soluong();
	public void setScheme_Soluong(Hashtable<String, String> scheme_soluong); 
	
	public ResultSet getSpTheoScheme(String scheme, String tongluong);
	public ResultSet getSpTheoScheme_UngHang(String scheme, String tongluong);
	public ResultSet getSpTheoScheme_TrungBay(String scheme, String tongluong);
	
	public String getLoaidonhang();
	public void setLoaidonhang(String loaidonhang);
	public String getChietkhau();
	public void setChietkhau(String chietkhau);
	public String getVat();
	public void setVat(String vat);
	
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
	
	public String[] getSpGianhapCK();
	public void setSpGianhapCK(String[] spGianhapck);
	
	public String[] getSpChietkhau();
	public void setSpChietkhau(String[] spChietkhau);
	public String[] getSpSchemeIds();
	public void setSpSchemeIds(String[] spSchemeIds);
	public String[] getSpScheme();
	public void setSpScheme(String[] spScheme);
	public String[] getSpSchemeDiengiai();
	public void setSpSchemeDiengiai(String[] spScheme);
	public String[] getSpTrongluong();
	public void setSpTrongluong(String[] spTrongluong);
	public String[] getSpThetich();
	public void setSpThetich(String[] spThetich);
	public String[] getSpQuyDoi();
	public void setSpQuyDoi(String[] spQuyDoi);
	public String[] getSpVat();
	public void setSpVat(String[] spVat);
	public String[] getSpTonkho();
	public void setSpTonkho(String[] spTonkho);
	
	public String[] getSpGiagoc();
	public void setSpGiagoc(String[] spgiagoc);
	
	public String[] getSpBgId();
	public void setSpBgId(String[] bgId);
	
	public String[] getSoluongtt();
	public void setSoluongtt(String[] spSoluongtt);
	
	public String[] getSpGhichu();
	public void setSpGhichu(String[] spGhichu);
	
	public String[] getSpNgaygiaohang();
	public void setSpNgaygiaohang(String[] spNgaygiaohang);
	
	public String[] getDhck_diengiai();
	public void setDhck_Diengiai(String[] obj);
	public String[] getDhck_giatri();
	public void setDhck_giatri(String[] obj);
	public String[] getDhck_loai();
	public void setDhck_loai(String[] obj);
	public String[] getDhck_chietkhau();
	public void setDhck_chietkhau(String[] obj);
	
	public ResultSet getCongnoRs();
	public void setCongnoRs(ResultSet congnoRs);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public boolean createNK();
	public boolean updateNK(String checkKM);
	
	public boolean createKMTichLuy();
	public boolean updateKMTichLuy();
	
	public boolean createKMUngHang();
	public boolean updateKMUngHang();
	
	public boolean createTrungBay();
	public boolean updateTrungBay();

	public void createRs();
	public void createSP();
	public void createScheme();
	public void init();
	
	public void DBclose();
	
	public boolean ApChietKhau();
	
	public String[] getSpTrakmId();
	public void setSpTrakmId(String[] spTrakmId); 
	
	public String getTiengiam();
	public void setTiengiam(String tiengiam);
	
	public String getPOKhuyenMai();
	public void setPOKhuyenMai(String pokhuyenmai);
	
	public ResultSet getSchemeSpecialRs();
	public void setSchemeSpecialRs(ResultSet schemespecialRs);

	public void setIsNppDat(String[] isNppDat);
	public String[] getIsNppDat();
	
	public String getIsTT();
	public void setIsTT(String isTT) ;
}


