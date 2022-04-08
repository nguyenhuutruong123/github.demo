package geso.dms.distributor.beans.dontratrungbaynpp;

import java.sql.ResultSet;
import java.util.Hashtable;

public interface IDonTraTrungBayNpp
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String Id);

	public String getNgayyeucau();
	public void setNgayyeucau(String ngayyeucau);

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
	
	public ResultSet getddkdRs();
	public void setddkdRs(ResultSet ddkdRs);
	
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
	
	public ResultSet getSoloTheoSp(String spIds, String tongluong);
	
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
	

	
	public String[] getSpGhiChu();
	public void setSpGhiChu(String[] spGhiChu);
	
	
	public String getDoituong();
	public void setDoituong(String doituong);
	
	public String getSoHoaDon();
	public void setSoHoaDon(String sohoadon);
	
	public String getddkdId();
	public void setddkdId(String ddkdid);
	
	public String getKyHieu();
	public void setKyHieu(String kyhieu);
	
	public String getNgayHoaDon();
	public void setNgayHoaDon(String ngayhoadon);
	
	public ResultSet getDtRs();
	public void setDtRs(ResultSet nppRs);
	
	public String getDtId();
	public void setDtId(String dtId);
	
	public String getSo();

	public void setSo(String so);
	public String getTienvat() ;

	public void setTienvat(String tienvat) ;
	
	
	public String getTraNguyenDon();
	public void setTraNguyenDon(String traNguyenDon);
	public ResultSet getDonhangRs();
	public void setDonhangRs(ResultSet donhangRs);
	public String getDonhangId() ;
	public void setDonhangId(String donhangId);
	public ResultSet getInfoRs() ;
	public void setInfoRs(ResultSet infoRs);
	public String getTonggiatridonhang();
	public void setTonggiatridonhang(String tonggiatridonhang);
	public String getHdId() ;
	public void setHdId(String hdId);
	public ResultSet getHdRs();
	
	public String getHopdong_fk();
	public void setHopdong_fk(String hopdong_fk);
	public ResultSet getHopdongRs();
	public void setHopdongRs(ResultSet hopdongRs);
	public String getRefresh();
	public void setRefresh(String refresh);
	public String getSoluongsptronghopdong();
	public void setSoluongsptronghopdong(String soluongsptronghopdong);
}
