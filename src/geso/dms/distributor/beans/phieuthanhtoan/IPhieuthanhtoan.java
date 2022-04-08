package geso.dms.distributor.beans.phieuthanhtoan;

import java.sql.ResultSet;
import java.util.Hashtable;
public interface IPhieuthanhtoan
{
	public String getUserId();
	public void setUserId(String userId);
	
	public void setId(String id);
	public String getId();
	
	public String getMsg();
	public void setMsg(String msg);
	
	public String getNppId();
	public void setNppId(String nppId);
	
	public String getNppTen();
	public void setNppTen(String nppTen);
	
	public String getTungay();
	public void setTungay(String tungay);
	
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public void setddkdId(String ddkdId);
	public String getddkdId();
	
	public void setSotien(String sotien);
	public String getSotien();
	
	public void setDskh(ResultSet dskh);
	public ResultSet getDskh();
	
	public void setNvgn(String nvgn);
	public String getNvgn();
	
	public void setNvgns(ResultSet nvgns);
	public ResultSet getNvgns();
	 
	public void setTuyen(String tuyen);
	public String getTuyen();
	
	public void setDdkd(ResultSet ddkd);
	public ResultSet getDdkd();
	
	public void setHinhthuc(String hinhthuc);
	public String getHinhthuc();
	
	public void setTuyens(ResultSet tuyens);
	public ResultSet getTuyens();
	
	public void setDiengiai(String diengiai);
	public String getDiengiai();
	
	public void setNgaythanhtoan(String ngaythanhtoan);
	public String getNgaythanhtoan();
	
	public void setThanhtoan(String[] thanhtoan);
	public String[] getthanhtoan();
	
	public void setdhIds(String[] dhids);
	public String[] getdhIds();
	
	public boolean save();
	
	public boolean Update();
	
	public void setDshd(ResultSet dshd);
	public ResultSet getDshd();
	public void init();
	/**
	 * get list danh sach cac don hang can thanh toan
	 */
}

