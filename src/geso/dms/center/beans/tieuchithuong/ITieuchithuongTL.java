package geso.dms.center.beans.tieuchithuong;

import geso.dms.center.db.sql.dbutils;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public interface ITieuchithuongTL 
{
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
	
	public String getActiveTab();
	public void setActiveTab(String active);
	
	public String getKT();
	public void setKT(String kt);
	
	public String getMucNPP();
	public void setMucNPP(String MucNpp);
	
	public String getScheme();
	public void setScheme(String scheme);
	
	public String getThang();
	public void setThang(String thang);
	public String getNam();
	public void setNam(String nam);
	
	public String getGhichu();
	public void setGhichu(String ghichu);
	
	
	public String getNgayDS_Tungay();
	public void setNgayDS_Tungay(String tungay);
	public String getNgayDS_Denngay();
	public void setNgayDS_Denngay(String denngay);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public void setSanphamRs(ResultSet spRs);
    public ResultSet getSanphamRs();
    public String getSpIds();
	public void setSpIds(String spIds);
	
	

	public void setVungRs(ResultSet vungRs);
    public ResultSet getVungRs();
    public String getVungIds();
	public void setVungIds(String vungIds);
	
	public void setKhuvucRs(ResultSet kvRs);
    public ResultSet getKhuvucRs();
    public String getKhuvucIds();
	public void setKhuvucIds(String kvIds);
	
	public void setKenhRs(ResultSet kenhRs);
    public ResultSet getKenhRs();
    public String getKenhIds();
	public void setKenhIds(String kenhIds);
    
	public void setKhoRs(ResultSet khoRs);
    public ResultSet getKhoRs();
    public String getKhoIds();
	public void setKhoIds(String khoIds);
	
	public void setNhomsanphamRs(ResultSet spRs);
    public ResultSet getNhomsanphamRs();
    public String getNhomsanphamIds();
	public void setNhomsanphamIds(String nhomspIds);
	
    //Cac Muc 1 thang
  
   
  
    
	public void init();
	public void createRs();
	
	public boolean createTctSKU();
	public boolean updateTctSKU();
	public boolean uploadPhanbo(String values);
	
	
    public String getDoanhsotheoThung();
    public void setDoanhsotheoThung(String isThung);
    
    public String getHTTT();
    public void setHTTT(String httt);
    public String getPT_TRACK();
    public void setPT_TRACK(String ptTRACK);
   
    
    public String getPhanloai();
    public void setPhanloai(String phanloai);
    
    
	
	public void loadSP_NHOM();
    
	public String getNgayTB_Tungay();
	public void setNgayTB_Tungay(String tungay);
	public String getNgayTB_Denngay();
	public void setNgayTB_Denngay(String denngay);
	
	public String getPhaidangky();
    public void setPhandangky(String phaidangky);    

	public String getTinhtheoSl();
    public void setTinhtheoSl(String value);
    
    public ArrayList<ITichLuyItem> getTichluyItemList();
	public void setTichluyItemList(ArrayList<ITichLuyItem> tichluyItemList);

	public void setSanphamMuaRs() ;
	public ResultSet getSanphamMuaRs() ;
	public ArrayList<String> getSpMuaList() ;
	public void setSpMuaList(ArrayList<String> spMuaList) ;
	
	public dbutils getDb() ;List<Object> dataUpload = new ArrayList<Object>();
	public List<Object> getDataUpload() ;
	public void setDataUpload(List<Object> dataUpload) ;
}
