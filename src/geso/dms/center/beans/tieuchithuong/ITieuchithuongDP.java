package geso.dms.center.beans.tieuchithuong;

import java.sql.ResultSet;

public interface ITieuchithuongDP 
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
	
	public String getIsthung();
	public void setIsthung(String isthung);
	public String getMsg();
	public void setMsg(String msg);
	
	public void setNhomspRs(ResultSet nspRs);
    public ResultSet getNhomspRs();
    

    public String getNhomdkId();
	public void setNhomdkId(String nhomdkId);
	
    
    //Cac Muc
    public String[] getDiengiaiMuc();
    public void setDiengiaiMuc(String[] diengiai);
    public String[] getTumuc();
    public void setTumuc(String[] tumuc);
    public String[] getDenmuc();
    public void setDenmuc(String[] denmuc);
    public String[] getDoanhso();
    public void setDoanhso(String[] doanhso);
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
    
    //SAN PHAM
    public String getDiengiaiNhom();
	public void setDiengiaiNhom(String diengiai);
	public String getSoluongNhom();
	public void setSoluongNhom(String soluongNhom);
	public String getSotienNhom();
	public void setSotienNhom(String sotienNhom);
    public String getIsThung();
	public void setIsThung(String isThung);
	
    public String[] getSpMa();
    public void setSpMa(String[] spMa);
    public String[] getSpTen();
    public void setSpTen(String[] spTen);
    
    
	public void init();
	public void createRs();
	
	
	public boolean createTctSKU();
	public boolean updateTctSKU();
	
	///////////////////////////
}
