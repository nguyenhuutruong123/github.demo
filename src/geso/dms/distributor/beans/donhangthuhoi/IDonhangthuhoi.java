package geso.dms.distributor.beans.donhangthuhoi;

import geso.dms.distributor.beans.donhang.ISanpham;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IDonhangthuhoi extends Serializable
{
	//Cac thuoc tinh 
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
	public String getPhieuxuatkho();
	public void setPhieuxuatkho(String pxkId);
	
	public ResultSet getNhanvienGN();
	public void setNhanvienGN(ResultSet nhanviengn);
	public String getNvgnId();
	public void setNvgnId(String nvgnId);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getDonhang();
	public void setDonhang(String donhang);
	
	public String getNgaytao();
	public void setNgaytao(String ngaytao);
	public String getNguoitao();
	public void setNguoitao(String nguoitao);
	public String getNgaysua();
	public void setNgaysua(String ngaysua);	
	public String getNguoisua();
	public void setNguoisua(String nguoisua);
	public String getMessage();
	public void setMessage(String msg);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	//in file pdf
	public ResultSet getKhachhang();
	public void setKhachhang(ResultSet khachangRs);	
	public List<ISanpham> getSanphamList();
	public void setSanphamList(List<ISanpham> spList);
	public List<ISanpham> getSpkmList();
	public void setSpkmList(List<ISanpham> spkmList);
		
	public boolean CreateDhth();
	public void createRS();
	public void init();
	public void DBclose();
}
