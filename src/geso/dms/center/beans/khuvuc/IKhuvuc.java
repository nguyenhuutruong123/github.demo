package geso.dms.center.beans.khuvuc;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IKhuvuc extends Serializable 
{
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
	public String getTen();
	public void setTen(String tenkhuvuc);
	public String getVungmien();
	public void setVungmien(String vungmien);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
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
	
	public ResultSet getVungMienlist();
	public void setVungmienlist(ResultSet vungmienlist);
	public String getVmId();
	public void setVmId(String vmId);
	public void setAsm(String asm);
	public String getAsm();
	public void setAsms(ResultSet asms);
	public ResultSet getAsms();
	public boolean CreateKv();
	public boolean UpdateKv();
	public void createVmRS();
	
	
	public String getTtId();
	public void setTtId(String ttId);
	
	public String getMa();
	public void setMa(String Ma);
	
	public ResultSet getTtRs();
	
	public String getQhId();
	public void setQhId(String ttId);
	
	public ResultSet getQHRs();
}
