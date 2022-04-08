package geso.dms.distributor.beans.khachhangtt;

import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IKhachhangTTList extends Serializable, IPhan_Trang
{
	public String getUserId();
	public void setUserId(String userId);
	public String getTen();
	public void setTen(String ten);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	public String getloaiKH();
	public void setloaiKH(String loaikh);
	public String getHopdong();
	public void setHopdong(String hopdong);
	
	//public List<IKhachhang> getKhList();
	//public void setKhList(List<IKhachhang> khlist);
	
	public ResultSet getKhList();
	public void setKhList(ResultSet khlist);
	
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
	
	public void init(String search);
	
	public int getCurrentPage();
	public void setCurrentPage(int current);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	public int getLastPage();
	public void DBclose();
	public void setMsg(String msg);
	public String getMsg();
	
	public String getDiachi();
	public void setDiachi(String diachi);
	
	public String getMaFAST();
	public void setMaFAST(String maFAST);
	
	public String getDiadiemId();
	public void setDiadiemId(String diadiemId);
	
	public ResultSet getDiadiemRs();
	public void setDiadiemRs(ResultSet diadiemRs);
	
	
	public String getXuatkhau();
	public void setXuatkhau(String xuatkhau);
	
	public String getDdkdId();
	public void setDdkdId(String ddkdId);
	
	public String getTbhId();
	public void setTbhId(String tbhId);
	
	
	public ResultSet getDdkdRs();
	public void setDdkdRs(ResultSet ddkdRs);
	
	public ResultSet getTbhRs();
	public void setTbhRs(ResultSet tbhRs);
	public void setQuery(String query);
	
	public ResultSet getNppRs();
	public void setNppRs(ResultSet nppRs);
	
	public ResultSet getVung() ;


	public void setVung(ResultSet vung);

	
	
	public ResultSet getMien();


	public void setMien(ResultSet mien) ;
	public String getVungid() ;


	public void setVungid(String vungid);

	public String getMienid() ;

	public ResultSet getLoaikhachhang() ;
	public void setLoaikhachhang(ResultSet loaikhachhang) ;
	public void setMienid(String mienid);
	public String getDayOfWeekId();
	public void setDayOfWeekId(String dayOfWeekId);
	public ResultSet getDayOfWeekRs(); 
}
