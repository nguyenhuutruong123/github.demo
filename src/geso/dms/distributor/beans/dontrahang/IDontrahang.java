package geso.dms.distributor.beans.dontrahang;


import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

public interface IDontrahang extends Serializable 
{
	public String getView() ;
	public void setView(String view) ;
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String Id);
	
	public String getNgayyeucau();
	public void setNgayyeucau(String ngayyeucau);
	
	public String[] getFile();
	public void setFile(String file);
	
	public String getGhichu();
	public void setGhichu(String ghichu);
	
	public String getLenhdieudong();
	public void setLenhdieudong(String lenhdieudong);
	
	public String getLDDcua();
	public void setLDDcua(String LDDcua);
	
	public String getLDDveviec();
	public void setLDDveviec(String LDDveviec);
	
	public String getNgaydieudong();
	public void setNgaydieudong(String ngaydieudong);	
	
	public String getNguoivanchuyen();
	public void setNguoivanchuyen(String nguoivanchuyen);
	
	public String getPtvanchuyen();
	public void setPtvanchuyen(String ptvanchuyen);
	
	public String getSohopdong();
	public void setSohopdong(String sohopdong);
	
	public String getNgayhopdong();
	public void setNgayhopdong(String ngayhopdong);
	
	public String getKhoXuatId();
	public void setKhoXuatId(String khoxuattt);
	public ResultSet getKhoXuatRs();
	public void setKhoXuatRs(ResultSet khoxuatRs);
	
	public String getKhoNhapId();
	public void setKhoNhapId(String khonhaptt);
	public ResultSet getKhoNhapRs();
	public void setKhoNHapRs(ResultSet khonhapRs);
	
	public String getKhId();
	public void setKhId(String khId);
	public ResultSet getKhRs();
	public void setKhRs(ResultSet khRs);
	
	public String getKbhId();
	public void setKbhId(String kbhId);
	public ResultSet getKbhRs();
	public void setKbhRs(ResultSet kbhRs);
	
	public ResultSet getDvtRs();
	public void setDvtRs(ResultSet dvtRs);
	
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
	public String[] getSpSolo();
	public void setSpSolo(String[] spSolo);
	public String[] getSpTonkho();
	public void setSpTonkho(String[] spHansudung);
	public String[] getSpBooked();
	public void setSpBooked(String[] spBooked);
	public String[] getSpNgaysanxuat();
	public void setSpNgaysanxuat(String[] spNgaysanxuat);

	public String[] getSpNgayHetHan();
	public void setSpNgayHetHan(String[] ngayHetHan);
	
	public String[] getSpVat();
	public void setSpVat(String[] spVat);
	
	
	//LUU SO LO, SOLUONG THAY DOI
	public Hashtable<String, String> getSanpham_Soluong();
	public void setSanpham_Soluong(Hashtable<String, String> sp_soluong); 
	

	
	public ResultSet getSanphamRs();
	public void setSanphamRs(ResultSet spRs);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public boolean createNK();
	public boolean updateNK();

	public String getDateTime();
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public void createRs();
	public void init();
	
	public void DBclose();
	
	public String getSoChungTu();
	public void setSoChungTu(String sochungtu);
	
	public Hashtable<String, Integer> getSp_Soluong();
	public void setSSp_Soluong(Hashtable<String, Integer> sp_sl);
	
	public ResultSet getSpRs();
	public void setSpRs(ResultSet spRs);	
}
