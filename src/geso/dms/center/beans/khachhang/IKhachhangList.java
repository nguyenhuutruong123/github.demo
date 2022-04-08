package geso.dms.center.beans.khachhang;

import geso.dms.center.util.IPhan_Trang;
import geso.dms.center.beans.khachhang.IKhachhang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IKhachhangList extends IPhan_Trang, Serializable 
{	
	public String getUserId();
	public void setUserId(String userId);
	public String getTen();
	public void setTen(String ten);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getSmartId();
	public void setSmartId(String SmartId);
	public String getTpId(); 
	public void setTpId(String tpId); 
	public String getQhId(); 
	public void setQhId(String qhId); 	
	
	public ResultSet getTp(); 
	public void setTp(ResultSet tp); 
	public ResultSet getQh(); 
	public void setQh(ResultSet qh); 	
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	
	public String getIsMT();
	public void setIsMT(String isMT);
	
	/*public String getTtTen();
	public void setttTen(String ttTen);
	
	public String getQhTen();
	public void setQhTen(String qhTen);*/
	public ResultSet getKhList();
	public void setKhList(ResultSet khlist);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
/*	public List<IKhachhang> getKhList();
	public void setKhList(List<IKhachhang> khlist);
	*/
	//Cac phuong thuc Get, Set tra ve ResultSet tuong ung
	public ResultSet getHangcuahang();
	public void setHangcuahang(ResultSet hangcuahang);	
	public ResultSet getKenhbanhang();
	public void setKenhbanhang(ResultSet kenhbanhang);
	public ResultSet getVitricuahang();
	public void setVitricuahang(ResultSet vitricuahang);
	public ResultSet getLoaicuahang();
	public void setLoaicuahang(ResultSet loaicuahang);
	public ResultSet getNhomcuahang();
	public void setNhomcuahang(ResultSet nhomcuahang);
	public String getNvbhId(); 
	public void setNvbhId(String NvbhId); 
	public ResultSet getNhaphanphoi();
	public void setNhaphanphoi(ResultSet nhaphanphoi);
	public ResultSet getNvbh();
	public void setvungId(String vungId);

	public String getvungId();
	
	public void setvung(ResultSet vung);

	public ResultSet getvung();
	//Cac phuong thuc Get, Set cua thuoc tinh duoc chon
	public String getHchId();
	public void setHchId(String hchId);
	public String getKbhId();
	public void setKbhId(String kbhId);
	public String getVtchId();
	public void setVtId(String vtchId);
	public String getLchId();
	public void setLchId(String lchId);
	public String getNchId();
	public void setNchId(String nchId);
	public String getMsg();
	public void setMsg(String Msg);
	public void init(String search);
	public void khChuaPhanTuyen(String dk);
	
	public int getCurrentPage();
	public void setCurrentPage(int current);
	
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	public int getLastPage();
	public void DBclose();
	
}