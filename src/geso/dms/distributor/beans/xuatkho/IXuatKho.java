package geso.dms.distributor.beans.xuatkho;

import geso.dms.distributor.beans.xuatkho.ISanPham;

import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;

public interface IXuatKho
{

	public String getId();
	public void setId(String id);

	public String getNgaynhap();
	public void setNgaynhap(String ngaynhap);

	public String getUserId();
	public void setUserId(String userId);

	public String getVat();
	public void setVat(String vat);

	public String getNppTen();
	public void setNppTen(String nppTen);

	public String getNppId();
	public void setNppId(String id);

	public String getNccId();
	public void setNccId(String nccId);

	public ResultSet getDvkdRs();
	public void setDvkdRs(ResultSet dvkdRs);

	public String getKbhId();
	public void setKbhId(String kbhId);

	public ResultSet getKbhRs();
	public void setKbhRs(ResultSet kbhRs);

	public String getDvkdId();
	public void setDvkdId(String dvkdId);

	public ResultSet getNccRs();
	public void setNccRs(ResultSet ncc);

	public String getMsg();
	public void setMsg(String msg);

	public String getGhichu();
	public void setGhichu(String Ghichu);

	public List<ISanPham> getSpList();
	public void setSpList(List<ISanPham> spList);

	public ResultSet getKhoRs();
	public void setKhoRs(ResultSet khoRs);


	public String getTienBVAT();
	public void setTienBVAT(String tienBVAT);

	public String getTienAVAT();
	public void setTienAVAT(String tienAVAT);

	public Hashtable<String, String> getDvdlList();
	public void setDvdlList( Hashtable<String, String> dvdlList);
	
	
	public Hashtable<String, String> getKhoTtList();
	public void setKhotTtList( Hashtable<String, String> khoTtList);
	
	
	public Hashtable<String, String> getKhoNppList();
	public void setKhoNppList( Hashtable<String, String> khoNppList);
	
	
	public String getKhoNppId();
	public void setKhoNppId(String khoNppId);
	
	public String getKhoTtId();
	public void setKhoTtId(String khoTtId);
	
	
	public void createRs();

	public void init();

	public boolean save();

	public boolean edit();

	public void DBclose();
	
	
}
