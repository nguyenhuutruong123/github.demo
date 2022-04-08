package geso.dms.distributor.beans.nhaphang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface INhaphang extends Serializable
{
	public String getKbhTen();
	public void setKbhTen(String kbhTen) ;
	public String getUserId();
	public void setUserId(String userId);

	public String getNppId();
	public void setNppId(String id);
	public String getGhichuTT() ;

	public String getGhichutaomoi() ;
	public String getThuevat() ;

	public void setThuevat(String thuevat);

	public void setGhichutaomoi(String ghichutaomoi) ;
	public void setGhichuTT(String ghichuTT);
	public String getId();
	public void setId(String id);

	public String getNgaynhap();
	public void setNgaynhap(String ngaynhap);

	public String getNgayyeucau();
	public void setNgayyeucau(String ngayyeucau);

	public String getGhichu();
	public void setGhichu(String ghichu);

	public String getDondathangId();
	public void setDondathangId(String kbhId);
	
	public ResultSet getDondathangRs();
	public void setDondathangRs(ResultSet ddhRs);
	
	public ResultSet getChungtuRs();
	public void setChungtuRs(ResultSet ctRs);

	public String getSohoadon();
	public void setSohoadon(String sohoadon);

	public String getMessage();

	public void setMessage(String msg);


	public void createRs();

	public void init();

	public void DBclose();


	public String[] getSpId();
	public void setSpId(String[] spId);
	public String[] getSpMa();
	public void setSpMa(String[] spMa);
	public String[] getSpTen();
	public void setSpTen(String[] spTen);
	public String[] getSpDonvi();
	public void setSpDonvi(String[] spDonvi);
	public String[] getSpXuat();
	public void setSpXuat(String[] spXuat);
	public String[] getSpSoluong();
	public void setSpSoluong(String[] spSoluong);
	public String[] getSpDongia();
	public void setSpDongia(String[] spDongia);

	public String[] getSpScheme();
	public void setSpScheme(String[] spScheme);
	public String[] getSpvat();
	public void setSpvat(String[] spvat);
	public String[] getSpchietkhau();
	public void setSpchietkhau(String[] spchietkhau);
	
	public String[] getBvat();
	public void setBvat(String[] bvat);
	public String[] getVat();
	public void setVat(String[] vat);
	public String[] getAvat();
	public void setAvat(String[] avat);

	public String[] getSpKhoId();
	public void setSpKhoId(String[] spKhoId);
	
	/*-- THÊM 2019.03.29 --*/
	public String[] getSpTonkho();
	public void setSpTonkho(String[] spTonkho);
	public String[] getSpGianhap();
	public void setSpGianhap(String[] spGianhap);
	public String[] getSpVat();
	public void setSpVat(String[] spVat);
	public String[] getSpChietkhau();
	public void setSpChietkhau(String[] spChietkhau);
	public String[] getSpGiagoc();
	public void setSpGiagoc(String[] spgiagoc);
	public String[] getSpBgId();
	public void setSpBgId(String[] bgId);
	
	public String getLoaidonhang();
	public void setLoaidonhang(String loaidonhang);
	public String getNppTen();
	public void setNppTen(String nppTen);
	
	
	public boolean create();
	public boolean update();
	public boolean chot();
	
	public String getDvkdId();
	public void setDvkdId(String dvkdId);
	public ResultSet getDvkdRs();
	public void setDvkdRs(ResultSet dvkdRs);
	
	public String getKbhId();
	public void setKbhId(String kbhId);
	public ResultSet getKbhRs();
	public void setKbhRs(ResultSet kbhRs);
	
	public ResultSet getNppRs();
	public void setNppRs(ResultSet nppRs);
	
	public String getKhoNhapId();
	public void setKhoNhapId(String khonhaptt);
	public ResultSet getKhoNhapRs();
	public void setKhoNHapRs(ResultSet khonhapRs);
	
	public String getLoai();
	public void setLoai(String loai);
}
