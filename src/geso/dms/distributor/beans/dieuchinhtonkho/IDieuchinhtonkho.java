package geso.dms.distributor.beans.dieuchinhtonkho;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;



public interface IDieuchinhtonkho extends Serializable
{
	public String getId();
	public void setId(String id);

	public String getNppId();	
	public void setNppId(String nppId);

	public String getNppTen();
	public void setNppTen(String nppTen);

	public String getUserId();
	public void setUserId(String userId);
	
	public String getUserTen();
	
	public void setUserTen(String userTen);
	
	public String getNgaydc();
	
	public void setNgaydc(String ngaydc);
	
	
	public String[] getFile();
	public void setFile(String file);
	
	public String getLydodc();
	
	public void setLydodc(String lydodc);
	
	
	public String getNccId();
	
	public void setNccId(String nccId);

	public ResultSet getNcc();
	
	public void setNcc(ResultSet ncc);

	public String getDvkdId();
	
	public void setDvkdId(String dvkdId);

	public ResultSet getDvkd();
	
	public void setDvkd(ResultSet dvkd);
	
	public String getKbhId();
	
	public void setKbhId(String kbhId);

	public ResultSet getKbh();
	
	public void setKbh(ResultSet kbh);
	
	public String getKhoId();
	
	public void setKhoId(String khoId);

	public ResultSet getKho();
	
	public void setKho(ResultSet kho);

	public String getGtdc();
	
	public void setGtdc(String gtdc);
	
	public String[] getSpId();
	
	public void setSpId(String[] spId);
	
	public String[] getMasp();
	
	public void setMasp(String[] masp);
	
	public String[] getTensp();
	
	public void setTenSp(String[] tensp);
	
	public String[] getTkht();
	
	public void setTkht(String[] tkht);

	public String[] getDonvi();
	
	public void setDonvi(String[] dv);
	
	public String[] getDc();
	
	public void setDc(String[] dc);
	
	public String[] getGiamua();
	
	public void setGiamua(String[] giamua);

	public String[] getTtien();
	
	public void setTtien(String[] ttien);
	
	public String getMessage();
	
	public void setMessage(String msg);

	public String getSize();
	
	public void setSize(String size);

	public String getMaspstr();
	
	public void setMaspstr(String maspstr);
	public void init0();
	public void initNew();
	public void initUpdate(String kt);
	public void initDisplay();
	public boolean CreateDctk(HttpServletRequest request);
	public boolean UpdateDctk(HttpServletRequest request);
	public void DBclose();
	
	
	public String[] getSoluongle();
	public void setSoluongle(String[] soluongle);
	
	public String getSodong() ;
	public void setSodong(String sodong);
	
	public String[] getNgaynhapkho();
	public void setNgaynhapkho(String[] spNGAYNHAPKHO);
}