package geso.dms.distributor.beans.dondathang;

import java.sql.ResultSet;
import java.util.Hashtable;

public interface IErpDondathangDoitac
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String Id);

	public int getNgay_Chenhlech();
	public void setNgay_Chenhlech(int ngay_Chenhlech);
	
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
	
	public String getKhId();
	public void setKhId(String khId);
	public ResultSet getKhRs();
	public void setKhRs(ResultSet khRs);
	
	public String getNppId();
	public void setNppId(String nppId);
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
	public String getDungchungKenh();
	public void setDungchungKenh(String dungchungKenh);
	
	public ResultSet getDvtRs();
	public void setDvtRs(ResultSet dvtRs);
	
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
	public String[] getSpChietkhau();
	public void setSpChietkhau(String[] spChietkhau);
	public String[] getSpVat();
	public void setSpVat(String[] spVat);
	public String[] getSpScheme();
	public void setSpScheme(String[] spScheme);
	
	public String[] getSpGiagoc();
	public void setSpGiagoc(String[] spGiagoc);
	
	
	public String[] getDhck_diengiai();
	public void setDhck_Diengiai(String[] obj);
	public String[] getDhck_giatri();
	public void setDhck_giatri(String[] obj);
	public String[] getDhck_loai();
	public void setDhck_loai(String[] obj);
	
	
	public ResultSet getCongnoRs();
	public void setCongnoRs(ResultSet congnoRs);
	
	
	public String getMsg();
	public void setMsg(String msg);
	
	public boolean createNK();
	public boolean updateNK();
	
	//LUU SO LO, SOLUONG THAY DOI  --> LUC DUYET SE TU DONG DE XUAT LO VA CO THE THAY DOI LAI
	public Hashtable<String, String> getSanpham_Soluong();
	public void setSanpham_Soluong(Hashtable<String, String> sp_soluong); 
	
	public ResultSet getSoloTheoSp(String spIds, String donvi, String tongluong);
	public boolean duyetDH();
	
	public String[] getSpSoluongton();
	public void setSpSoluongton(String[] spSoluongton);

	public void createRs();
	public void init();
	
	public void DBclose();
	
	public String getCongNo();
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	
	public String getIsKm();
	public void setIsKm(String iskm);
	public String getIsdhk();

	public void setIsdhk(String isdhk);
	public String getIsgia();

	public void setIsgia(String isgia);
	public String[] getSptonkhocn();

	public void setSptonkhocn(String[] sptonkhocn) ;

	public String Chot(String lsxId, String userId); 
	public String Duyet(); 
	public String getTiengiamtru();
	public void setTiengiamtru(String tiengiamtru);
	public String getDiengiaigiamtru();
	public void setDiengiaigiamtru(String diengiaigiamtru);
	public String DeleteChuyenKho(String lsxId);
	
}
