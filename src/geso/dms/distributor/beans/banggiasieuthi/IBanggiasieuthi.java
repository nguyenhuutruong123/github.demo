package geso.dms.distributor.beans.banggiasieuthi;

import geso.dms.distributor.beans.banggiasieuthi.ISanpham;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;

public interface IBanggiasieuthi extends Serializable
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String id);
	
	public String getTenbanggia();
	public void setTenbanggia(String tenbanggia);
	
	public String getNhacungcap();
	public void setNhacungcap(String nhacungcap);
	
	public String getDonvikinhdoanh();
	public void setDonvikinhdoanh(String donvikinhdoanh);
	
	public String getNgaytao();
	public void setNgaytao(String Ngaytao);
	
	public String getNguoitao();
	public void setNguoitao(String nguoitao);
	
	public String getNgaysua();
	public void setNgaysua(String ngaysua);
	
	public String getNguoisua();
	public void setNguoisua(String nguoisua);	
	
	public String getHangCuaHang();
	public void setHangCuaHang(String hangcuahang);
	public String getLoaiCuahang();
	public void setLoaiCuahang(String loaicuahang);
	public String getVitricuahang();
	public void setViTriCuahang(String vtchid);
	public String getQuanhuyen();
	public void setquanhuyen(String quanhuyen);
	public String getTinhthanh();
	public void setTinhThanh(String tinhthanh);
	public ResultSet getNcc();
	public void setNcc(ResultSet ncc);
	public String  getNccId();
	public void setNccId(String nccId);
	
	public ResultSet getDvkd();
	public void setDvkd(ResultSet dvkd);
	public String  getDvkdId();
	public void setDvkdId(String dvkdId);
	
	public List<ISanpham> getSpList();
	public void setSpList(List<ISanpham> splist);
	public void setKhList(List<IKhachHang_Bgst> khlist);
	public List<IKhachHang_Bgst> getListKh();
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public String getddkdid();
	public void setddkdid(String ddkdid);
	
	public String getMessage();
	public void setMessage(String msg);
	
	public boolean CreateBgst(String[] spId, String[] spGiasieuthi);
	public boolean UpdateBgst(String[] spId, String[] spGiasieuthi);
	public void init();
	public void createRS();
	//dung de tim kiem khachhang trong phan dua khachhang vao bang gia
	public void CreateRsSearch();
	public ResultSet getrsloaicuahang();
	public ResultSet getrshangcuahang();
	public ResultSet getrsvitricuahang();
	public ResultSet getrsquanhuyen();
	public ResultSet getrstinhthanh();
	public ResultSet getrsddkd();
	public void DBclose();
	public ResultSet getrskhachhang_bgst();
	public ResultSet getrskhachhang_new();
	public String getkhachhangString();
	public void setkhachhangString( String ss);
	public boolean update_bg_kh(javax.servlet.http.HttpServletRequest request);
   public Hashtable<String,String> getDSKH_CO_BGST();
}
