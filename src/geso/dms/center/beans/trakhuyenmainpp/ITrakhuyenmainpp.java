package geso.dms.center.beans.trakhuyenmainpp;

import geso.dms.center.beans.trakhuyenmai.ITrakhuyenmai;

import java.sql.ResultSet;
import java.util.List;

public interface ITrakhuyenmainpp {
	public String getUserId();
	public void setUserId(String userId);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getId();
	public void setId(String id);
	public void setnppId(String nppId);
	public String getnppId();
	public void setnppIds(ResultSet nppIds);
	public ResultSet getnppIds();
	public void setctkmId(String ctkmId);
	public String getctkmId();
	public void setctkmIds(ResultSet ctkmIds);
	public ResultSet getctkmIds();
	public void setType(String type);
	public String getType();
	public void setSanpham(ResultSet Sanpham);
	public ResultSet getSanpham();
	public void setTongtien(String tongtien);
	public String getTongtien();
	public boolean save();
	public boolean update();
	public void init();
	public void setChon(String []chon);
	public String[] getChon();
	public void setMsg(String msg);
	public String getMsg();
	
	public void setMact(String[] mact);
	public String[] getMact();
	public void setThangtoan(String[] thanhtoan);
	public String[] getThanhtoan();
	
	public void DBClose();
	
	
}
