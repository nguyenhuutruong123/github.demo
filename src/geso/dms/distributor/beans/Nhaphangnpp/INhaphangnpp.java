package geso.dms.distributor.beans.Nhaphangnpp;

import java.sql.ResultSet;

public interface INhaphangnpp
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String Id);
	
	public String getNppId();
	public void setNppId(String nppId);

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
	
	public String getKhonhanId();
	public void setKhonhanId(String khonhanId);
	public ResultSet getKhonhanRs();
	public void setKhonhanRs(ResultSet khonhanRs);
	
	public String getSochungtu();
	public void setSOchungtu(String sochungtu);
	
	public String[] getSpId();
	public void setSpId(String[] spId);
	public String[] getSpMa();
	public void setSpMa(String[] spMa);
	public String[] getSpTen();
	public void setSpTen(String[] spTen);
	public String[] getSpDonvi();
	public void setSpDonvi(String[] spDonvi);
	public String[] getSpSolo();
	public void setSpSolo(String[] spSolo);
	public String[] getSpTonKho();
	public void setSpTonKho(String[] spTonkho);
	public String[] getSpXuat();
	public void setSpXuat(String[] spXuat);
	public String[] getSpSoluong();
	public void setSpSoluong(String[] spSoluong);
	public String[] getSpDongia();
	public void setSpDongia(String[] spDongia);
	public String[] getSpLoai();
	public void setSpLoai(String[] spLoai);
	public String[] getSpScheme();
	public void setSpScheme(String[] spScheme);
	public String[] getSpvat();
	public void setSpvat(String[] spvat);
	public String[] getSpchietkhau();
	public void setSpchietkhau(String[] spchietkhau);
	
	public String[] getSpNgayHetHan();
	public void setSpNgayHetHan(String[] ngayHetHan);
	
	
	
	public String getMsg();
	public void setMsg(String msg);
	
	public boolean create();
	public boolean update();
	public boolean chot();
	
	public void createRs();
	public void init();
	
	public void DBclose();
	
	public String getLoaiDh();
	public void setLoaiDh(String loaidh);
}
