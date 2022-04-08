package geso.dms.distributor.beans.xuatkho;

import java.sql.ResultSet;
import java.util.Hashtable;

public interface IErpYeucauxuatkhoNpp
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String Id);

	public String getNgayyeucau();
	public void setNgayyeucau(String ngayyeucau);

	public String getGhichu();
	public void setGhichu(String ghichu);
	
	public String getXuatcho();
	public void setXuatcho(String xuatcho);
	
	public String getKhoNhapId();
	public void setKhoNhapId(String khonhaptt);
	public ResultSet getKhoNhapRs();
	public void setKhoNHapRs(ResultSet khonhapRs);
	
	public String getKhId();
	public void setKhId(String khId);
	public ResultSet getKhRs();
	public void setKhRs(ResultSet khRs);
	
	public String getDondathangId();
	public void setDondathangId(String kbhId);
	public ResultSet getDondathangRs();
	public void setDondathangRs(ResultSet ddhRs);
	
	public Hashtable<String, String> getSanpham_Soluong();
	public void setSanpham_Soluong(Hashtable<String, String> sp_soluong); 
	
	public ResultSet getSoloTheoSp(String spIds, String tongluong);
	
	public String[] getSpId();
	public void setSpId(String[] spId);
	public String[] getSpMa();
	public void setSpMa(String[] spMa);
	public String[] getSpTen();
	public void setSpTen(String[] spTen);
	public String[] getSpDonvi();
	public void setSpDonvi(String[] spDonvi);
	public String[] getSpSoluongDat();
	public void setSpSoluongDat(String[] spSoluong);
	public String[] getSpTonKho();
	public void setSpTonKho(String[] spTonkho);
	public String[] getSpDaXuat();
	public void setSpDaXuat(String[] spDaXuat);
	public String[] getSpSoluong();
	public void setSpSoluong(String[] spSoluong);
	public String[] getSpLoai();
	public void setSpLoai(String[] spLoai);
	public String[] getSpScheme();
	public void setSpScheme(String[] spScheme);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public boolean create();
	public boolean update();
	
	public void createRs();
	public void init();
	
	public void DBclose();
}
