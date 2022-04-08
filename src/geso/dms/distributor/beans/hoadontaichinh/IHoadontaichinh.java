package geso.dms.distributor.beans.hoadontaichinh;

import java.sql.ResultSet;
import java.util.Hashtable;

public interface IHoadontaichinh
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String Id);
	
	public String getKyhieuhoadon();
	public void setKyhieuhoadon(String kyhieuhoadon);
	
	public String getSohoadon();
	public void setSohoadon(String sohoadon);
	
	public String getNgayxuatHD();
	public void setNgayxuatHD(String ngayxuatHD);
	
	public String getNgayghinhanCN();
	public void setNgayghinhanCN(String ngayghinhanCN);

	public String getGhichu();
	public void setGhichu(String ghichu);
	
	public String getLoaidonhang();
	public void setLoaidonhang(String loaidonhang);
	
	public String getHinhthucTT();
	public void setHinhthucTT(String hinhthucTT);
	
	public String getNguoimua();
	public void setNguoimua(String nguoimua);
	
	public String getInNguoimua();
	public void setInNguoimua(String inNguoimua);
	
	public String getKhoNhapId();
	public void setKhoNhapId(String khonhaptt);
	public ResultSet getKhoNhapRs();
	public void setKhoNHapRs(ResultSet khonhapRs);
	
	public String getNppId();
	public void setNppId(String nppId);
	public ResultSet getNppRs();
	public void setNppRs(ResultSet nppRs);
	
	public String getKhId();
	public void setKhId(String khId);
	public ResultSet getKhRs();
	public void setKhRs(ResultSet khRs);
	
	public String getDondathangId();
	public void setDondathangId(String kbhId);
	public ResultSet getDondathangRs();
	public void setDondathangRs(ResultSet ddhRs);
	
	public String[] getSpId();
	public void setSpId(String[] spId);
	public String[] getSpMa();
	public void setSpMa(String[] spMa);
	public String[] getSpTen();
	public void setSpTen(String[] spTen);
	public String[] getSpDonvi();
	public void setSpDonvi(String[] spDonvi);
	public String[] getSpDongia();
	public void setSpDongia(String[] spDongia);
	public String[] getSpChietkhau();
	public void setSpChietkhau(String[] spChietkhau);
	public String[] getSpSoluong();
	public void setSpSoluong(String[] spSoluong);
	public String[] getSpLoai();
	public void setSpLoai(String[] spLoai);
	public String[] getSpScheme();
	public void setSpScheme(String[] spScheme);
	public String[] getSpVat();
	public void setSpVat(String[] spVat);
	public String[] getSpThue();
	public void setSpThue(String[] spThue);
	
	//Tra KM tich luy
	public String[] getTichLuy_Scheme();
	public void setTichLuy_Scheme(String[] tichluy_scheme);
	public String[] getTichLuy_Tongtien();
	public void setTichLuy_Tongtien(String[] tichluy_tongtien);
	public String[] getTichLuy_VAT();
	public void setTichLuy_TVAT(String[] tichluy_vat);
	
	public String[] getDhck_diengiai();
	public void setDhck_Diengiai(String[] obj);
	public String[] getDhck_giatri();
	public void setDhck_giatri(String[] obj);
	public String[] getDhck_loai();
	public void setDhck_loai(String[] obj);
	
	//% CHIET KHAU
	public void setChietkhau(String chietkhau);
	public String getChietkhau();
	
	public String getTongtienBVAT();
	public void setTongtienBVAT(String bvat);
	public String getTongCK();
	public void setTongCK(String tongCK);
	public String getTongVAT();
	public void setTongVAT(String vat);
	public String getTongtienAVAT();
	public void setTongtienAVAT(String avat);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public boolean create();
	public boolean update();
	public boolean chot(String hdId, boolean updateBEFORE);
	public boolean duyetDIEUCHINH();
	public void getTongTienPDF();
	
	//LUU SO LO, SOLUONG THAY DOI  --> LUC DUYET SE TU DONG DE XUAT LO VA CO THE THAY DOI LAI
	public Hashtable<String, String> getSanpham_Soluong();
	public void setSanpham_Soluong(Hashtable<String, String> sp_soluong); 
	
	public ResultSet getSoloTheoSp(String spIds, String donvi, String tongluong);
	
	public void createRs();
	public void init();
	public void init_Display();
	
	public void DBclose();
	public void loadContents();
	public String getMavuviec();

	public void setMavuviec(String mavuviec);

}
