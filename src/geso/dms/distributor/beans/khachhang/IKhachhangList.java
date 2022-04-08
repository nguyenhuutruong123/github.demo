package geso.dms.distributor.beans.khachhang;

import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IKhachhangList extends IPhan_Trang, Serializable {
	public String getLoaikhachhang();

	public void setLoaikhachhang(String loaikhachhang);
	
	public String getRoute();

	public void setRoute(String route);

	public String getNpp1Ten();

	public String getMathamchieu() ;

	public void setMathamchieu(String mathamchieu) ;
	public void setNpp1Ten(String npp1Ten);

	public String getNpp1Id();

	public void setNpp1Id(String npp1Id);

	public ResultSet getNhapp();

	public void setNhapp(ResultSet nhapp);

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

	public ResultSet getKhList();

	public void setKhList(ResultSet khlist);
	
	public List<Object> getDatasearch();

	public void setDatasearch(List<Object> datasearch);

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

	public void khChuaPhanTuyen(String dk);

	public int getCurrentPage();

	public void setCurrentPage(int current);

	public int[] getListPages();

	public void setListPages(int[] listPages);

	public int getLastPage();

	public void DBclose();

	public void setMsg(String msg);

	public String getMsg();

	public String getView();

	public void setView(String view);
	
	public String getNpp_search();
	
	public void setNpp_search(String npp_search);
	
	public String getVungId();

	public void setVungId(String vungId);

	public String getKvId();

	public void setKvId(String kvId);

	public String getGsbhId();

	public void setGsbhId(String gsbhId);

	public String getDdkdId();

	public void setDdkdId(String ddkdId);

	public String getIsDuyet();

	public void setIsDuyet(String isDuyet);

	public String getIsToado();

	public void setIsToado(String isToado);

	public String getTtId();

	public void setTtId(String ttId);

	public String getCoderoute();

	public void setCoderoute(String coderoute);

	public ResultSet getKhlist();

	public void setKhlist(ResultSet khlist);

	public ResultSet getTinhthanh();

	public void setTinhthanh(ResultSet tinhthanh);

	public ResultSet getVung();

	public void setVung(ResultSet vung);

	public ResultSet getKhuvuc();

	public void setKhuvuc(ResultSet khuvuc);

	public ResultSet getDdkd();

	public void setDdkd(ResultSet ddkd);

	public ResultSet getGsbh();

	public void setGsbh(ResultSet gsbh);

	public ResultSet getCoderouteRs();

	public void setCoderouteRs(ResultSet coderouteRs);

	public void setVtchId(String vtchId);
	
	public String getTinhthanhId();
	public void setTinhthanhId(String tinhthanhId);
	
	public String getSodienthoai();
	public void setSodienthoai(String sodt);
}
