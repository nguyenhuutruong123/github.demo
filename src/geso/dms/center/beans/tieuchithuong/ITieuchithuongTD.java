package geso.dms.center.beans.tieuchithuong;

import java.sql.ResultSet;
import java.util.Hashtable;

public interface ITieuchithuongTD 
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
	
	public void setVungRs(ResultSet vungRs);
    public ResultSet getVungRs();
    public String getVungIds();
	public void setVungIds(String vungIds);
	
	public void setKhuvucRs(ResultSet kvRs);
    public ResultSet getKhuvucRs();
    public String getKhuvucIds();
	public void setKhuvucIds(String kvIds);
    
    
    //Cac Muc 1 thang
    public String[] getDiengiaiMuc();
    public void setDiengiaiMuc(String[] diengiai);
    public String[] getTumuc();
    public void setTumuc(String[] tumuc);
    public String[] getDenmuc();
    public void setDenmuc(String[] denmuc);
    public String[] getThuongSR();
    public void setThuongSR(String[] thuongSR);
    public String[] getThuongTDSR();
    public void setThuongTDSR(String[] thuongTDSR);
    public String[] getThuongSS();
    public void setThuongSS(String[] thuongSS);
    public String[] getThuongTDSS();
    public void setThuongTDSS(String[] thuongTDSS);
    public String[] getThuongASM();
    public void setThuongASM(String[] thuongASM);
    public String[] getThuongTDASM();
    public void setThuongTDASM(String[] thuongTDASM);
    
    //Cac Muc 3 thang
    public String[] getDiengiaiMuc3();
    public void setDiengiaiMuc3(String[] diengiai);
    public String[] getTumuc3();
    public void setTumuc3(String[] tumuc);
    public String[] getDenmuc3();
    public void setDenmuc3(String[] denmuc);
    public String[] getThuongSR3();
    public void setThuongSR3(String[] thuongSR);
    public String[] getThuongTDSR3();
    public void setThuongTDSR3(String[] thuongTDSR);
    public String[] getThuongSS3();
    public void setThuongSS3(String[] thuongSS);
    public String[] getThuongTDSS3();
    public void setThuongTDSS3(String[] thuongTDSS);
    public String[] getThuongASM3();
    public void setThuongASM3(String[] thuongASM);
    public String[] getThuongTDASM3();
    public void setThuongTDASM3(String[] thuongTDASM);
    
    public String getMucvuot();
    public void setMucvuot(String mucvuot);
    public String getChietkhauMucvuot();
    public void setChietkhauMucvuot(String ckMv);
    public String getDonviMucvuot();
    public void setDonviMucvuot(String dvMv);
    
	public void init();
	public void createRs();
	
	
	public boolean createTctSKU();
	public boolean updateTctSKU();
	
	
	//San pham tra
	public String getHinhthuctra();
    public void setHinhthuctra(String htTra);
    
    public String[] getMaspTra();
    public void setMaspTra(String[] maspTra);
    public String[] getTenspTra();
    public void setTenspTra(String[] tenspTra);
    public String[] getSoluongspTra();
    public void setSoluongspTra(String[] soluongspTra);
    public String[] getIdspTra();
    public void setIdspTra(String[] idspTra);
    public String[] getDongiaspTra();
    public void setDongiaspTra(String[] dongiaspTra);
	

	///////////////////////////
    
    public String getDoanhsotheoThung();
    public void setDoanhsotheoThung(String isThung);
    
    public String getHTTT();
    public void setHTTT(String httt);
    public String getPT_TRACK();
    public void setPT_TRACK(String ptTRACK);
    
    
    public Hashtable<String, String> getDieukien();
    public void setDieukien(Hashtable<String, String> dieukien);
    public Hashtable<String, String> getQuydoi();
    public void setQuydoi(Hashtable<String, String> quydoi);
    
    
}
