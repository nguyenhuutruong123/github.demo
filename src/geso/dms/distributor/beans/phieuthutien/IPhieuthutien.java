package geso.dms.distributor.beans.phieuthutien;

import java.io.Serializable;
import java.sql.ResultSet;


import javax.servlet.http.HttpServletRequest;
public interface IPhieuthutien extends Serializable
{
	//Cac thuoc tinh 
	public String getUserId();

	public void setUserId(String userId); 
	
	public boolean getClaim();

	public void setClaim(boolean claim); 
	
	public String getId(); 
	
	public void setId(String id); 
	
	public String getNgay();

	public void setNgay(String ngay);

	public String getNgaytao();

	public void setNgaytao(String ngaytao);
	
	public String getNgaysua();

	public void setNgaysua(String ngaysua);
	
	public String getNguoitao();
	
	public void setNguoitao(String nguoitao);

	public String getNguoisua();

	public void setNguoisua(String nguoisua);
	
	public String getTungay();

	public void setTungay(String tungay);

	public String getDenngay();

	public void setDenngay(String denngay);
	
	public String getMessage(); 
	
	public void setMessage(String msg); 
	
	public String getNppId(); 

	public void setNppId(String nppId); 
	
	public String getNppTen(); 

	public void setNppTen(String nppTen); 
	
	public String getSitecode(); 

	public void setSitecode(String sitecode); 

	public ResultSet getNvgn(); 
	
	public void setNvgn(ResultSet nvgn);
	
	public ResultSet getKh(); 
	
	public void setKh(ResultSet kh); 
	
	public ResultSet getKhSelected();
	
	public void setKhSelected(ResultSet khSelected); 
	
	public String getNvgnId(); 
	
	public void setNvgnId(String nvgnId); 
	
	public String[] getKhIds(); 
	
	public void setKhIds(String[] khIds); 
	
	public String getKhId(); 
	
	public void setKhId(String khId); 
	
	public String getTongsotien(); 
	
	public void setTongsotien(String tongsotien); 
	
	public String getDiengiai(); 
	
	public void setDiengiai(String diengiai); 

	public ResultSet getPxk(); 
	
	public void setPxk(ResultSet pxk); 

	public String getPxkId(); 
	
	public void setPxkId(String pxkId); 
	
	public boolean CreatePtt(HttpServletRequest request);

	public boolean UpdatePtt(HttpServletRequest request);
	
	public boolean PostPtt();
	
	public void DeletePtt();
	
	public void createRS();
	
	public void initNew();
	
	public void initUpdate();
	
	public void DBclose();
}
