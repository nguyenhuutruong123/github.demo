package geso.dms.center.beans.chitieu;

import java.sql.ResultSet;
import java.util.Hashtable;

public interface IChuLuc
{

	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
	public String getScheme();
	public void setScheme(String scheme);
	
	public String getThang();
	public void setThang(String thang);
	public String getNam();
	public void setNam(String nam);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public void setSanphamRs(ResultSet spRs);
	public ResultSet getSanphamRs();
	public String getSpIds();
	public void setSpIds(String spIds);
	
	public void setNppRs(ResultSet nppRs);
	public ResultSet getNppRs();
	public String getNppIds();
	public void setNppIds(String nppIds);
	
	public Hashtable<String, String> getNpp_chitieu();
	public void setNpp_chitieu(Hashtable<String, String> npp_chitieu);
	
	public Hashtable<String, String> getNpp_donvi_chitieu();
	public void setNpp_donvi_chitieu(Hashtable<String, String> npp_donvi_chitieu);
	
	public void setVungRs(ResultSet vungRs);
	public ResultSet getVungRs();
	public String getVungIds();
	public void setVungIds(String vungIds);
	
	public void setKhuvucRs(ResultSet kvRs);
	public ResultSet getKhuvucRs();
	public String getKhuvucIds();
	public void setKhuvucIds(String kvIds);

	
	public String[] getTumuc();
	public void setTumuc(String[] tumuc);
	public String[] getDenmuc();
	public void setDenmuc(String[] denmuc);	
	public String[] getThuong();
	public void setThuong(String[] thuong);
	public String[] getDonvi_tinh_ds();
	public void setDonvi_tinh_ds(String[] donvi_tinh_ds);
	public String[] getDonvi_tinh_thuong();
	public void setDonvi_tinh_thuong(String[] donvi_tinh_thuong);
	
	
	public void init();
	public void createRs();
	
	public boolean save();
	public boolean edit();

	
	public String getDoituong();
	public void setDoituong(String doituong);
	
	public String getQuy();
	public void setQuy(String quy);
	
	public String getApdung();
	public void setApdung(String apdung);
	
	public String getLoaichitieu();
	public void setLoaichitieu(String loaichitieu);
	
	public String getView();
	public void setView(String view);
	
	public String getTructhuocId();
	public void setTructhuocId(String tructhuocId);
	
	public String getTdvIds();
	public void setTdvIds(String tdvIds);
	
	public ResultSet getTdvRs();
	public void setTdvRs(ResultSet tdvRs);
	
	public Hashtable<String, String> getTdv_donvi_chitieu();
	public void setTdv_donvi_chitieu(Hashtable<String, String> tdv_donvi_chitieu);
	
	public Hashtable<String, String> getTdv_chitieu();
	public void setTdv_chitieu(Hashtable<String, String> tdv_chitieu);

	public Hashtable<String, String> getSs_chitieu();
	public void setSs_chitieu(Hashtable<String, String> ss_chitieu);
	
	public Hashtable<String, String> getAsm_chitieu();
	public void setAsm_chitieu(Hashtable<String, String> asm_chitieu);
	
	public Hashtable<String, String> getRsm_chitieu();
	public void setRsm_chitieu(Hashtable<String, String> rsm_chitieu);
	
	
	public String getAsmIds();
	public void setAsmIds(String asmIds);
	
	public String getSsIds();
	public void setSsIds(String ssIds);
	
	public String getRsmIds();
	public void setRsmIds(String rsmIds);
	
	public ResultSet getAsmRs();
	public void setAsmRs(ResultSet ASMRs);
	
	public ResultSet getRsmRs();
	public void setRsmRs(ResultSet RSMRs);
	
	public ResultSet getSsRs();
	public void setSsRs(ResultSet SSRs);
	
	public ResultSet getKyRs();
	public void setKyRs(ResultSet kyRs);
	
	public ResultSet getQuyRs();
	public void setQuyRs(ResultSet quyRs);
	
	public String getSql();
	public void setSql(String sql);
	
	public String getSqlNhom();
	public void setSqlNhom(String sqlNhom);
	
	public ResultSet getNhomRs();
	public void setNhomRs(ResultSet nhomRs);
	
	public String[] getNhomId();
	public void setNhomId(String[] nhomId);
	
	public String[] getNhomTen();
	public void setNhomTen(String[] nhomId);
	
	public Hashtable<String, String> getNhomTrongso();
	public void setNhomTrongso(Hashtable<String, String> trongso);
	
	public Hashtable<String, String> getNhomchon();
	public void setNhomchon(Hashtable<String, String> trongso);
	
	public String getThuongSR();
	public void setThuongSR(String thuongSR);
	
	public String getThuongSS();
	public void setThuongSS(String thuongSS);
	
	public String getThuongASM();
	public void setThuongASM(String thuongASM);
	
	public String getThuongRSM();
	public void setThuongRSM(String thuongRSM);
	
	public String getSonhomdat();
	public void setSonhomdat(String sonhomdat);
}
