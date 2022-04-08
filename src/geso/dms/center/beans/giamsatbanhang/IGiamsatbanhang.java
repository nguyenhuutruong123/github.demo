package geso.dms.center.beans.giamsatbanhang;

import geso.dms.center.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

public interface IGiamsatbanhang extends Serializable
{
	public ResultSet getLogRs() ;
	// Cac thuoc tinh
	public String getUserId();
	public void setUserId(String userId);
	public String getNppIdcu();
	public void setNppIdcu(String nppIdcu);
	public String getId();
	public void setId(String id);
	public String getQuerycu() ;

	public void setQuerycu(String querycu);
	public String getSmartId();
	public void setSmartId(String smartid);
	
	public String getTen();
	public void setTen(String ten);

	public String getDiachi();
	public void setDiachi(String diachi);

	public String getSodienthoai();
	public void setSodienthoai(String sodienthoai);

	public String getEmail();
	public void setEmail(String email);

	public String getNhacungcap();
	public void setNhacungcap(String nhacungcap);

	public String getKenhbanhang();
	public void setKenhbanhang(String kenhbanhang);

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
	
	public ResultSet getTrungtamphanphoiList();
	public void setTrungtamphanphoiList(ResultSet trungtamphanphoilist);

	public ResultSet getNhacungcapList();
	public void setNhacungcapList(ResultSet nhacungcaplist);

	public ResultSet getKenhbanhangList();
	public void setKenhbanhangList(ResultSet kenhbanhanglist);

	public String getTtppId();
	public void setTtppId(String TtppId);
	
	public String getNccId();
	public void setNccId(String nccId);

	/* Thêm vào */
	public void setChuTaiKhoan(String chutaikhoan);
	public String getChuTaiKhoan();

	public void setSoTaiKhoan(String sotaikhoan);
	public String getSoTaiKhoan();

	public void setNganHang(String nganhang);
	public String getNganHang();

	public String getNganHangId();
	public void setChiNhanhId(String ChiNhanhId);

	public String getChiNhanhId();
	public void setNganHangId(String ChiNhanhId);

	public ResultSet getRsNganHang();
	public ResultSet getRsChiNhanh();

	public void setRsNganHang(ResultSet rsNganHang);
	public void setRsChiNhanh(ResultSet rsChiNhanh);

	/* ##################### */
	public String getCmnd();
	public void setCmnd(String cmnd);

	public String getNgaysinh();
	public void setNgaysinh(String ngaysinh);

	public String getQuequan();
	public void setQuequan(String quequan);

	public String getThuviec();
	public void setThuviec(String thiec);

	public String getDvkdId();
	public void setDvkdId(String dvkdId);

	public ResultSet getDvkdList();
	public void setDvkdList(ResultSet dvkdlist);

	public String getKbhId();
	public void setKbhId(String kbhId);

	public String getVungId();
	public void setVungId(String vungId);
	
	public String getNgaybatdau();
	public void setNgaybatdau(String ngaybatdau);
	
	public String getNgayketthuc();
	public void setNgayketthuc(String ngayketthuc);

	public String getKvId();
	public void setKvId(String kvId);

	public ResultSet createVungRS();
	public ResultSet createKhuvucRS();

	public ResultSet createKVByGSBHRS();

	public void init();

	public boolean CreateGsbh(HttpServletRequest request);

	public boolean UpdateGsbh(HttpServletRequest request);

	public void createRS();

	
	public void setHinhanh(String ha);
	public String getHinhanh();
	

	public String getNppIds();
	public void setNppIds(String nppIds);
	
	public ResultSet getNppRs();
	public void setNppRs(ResultSet nppRs);
	
	public ResultSet getNppQuanLyRs();
	public void setNppQuanLyRs(ResultSet nppQlRs);
	
	public String getGsbhTnId();
	public void setGsbhTnId(String gsbhTnId);
	
	public ResultSet getGsbhTnRs();
	public void setGsbhTnRs(ResultSet gsbhTnRs);
	
	public void closeDB();
	
	public ResultSet getNppGsTnQuanLy();
	public void setNppGsTnQuanLy(ResultSet nppTnRs);
	

	//CO THE CHON Nhan vien tien nhiem
	public void setCothechonTN(String mathuviec);
	public String getCothechonTN();
	
	public String getTenDangNhap();
	public void setTenDangNhap(String dangnhap);

	public String getMatKhau();
	public void setMatKhau(String matkhau);
	public String getDiaban_fk();
	public void setDiaban_fk(String diaban_fk);
	public ResultSet getDiabanRs();
	public void setDiabanRs(ResultSet diabanRs);
	public dbutils getDb();
	public void setImgList(ArrayList<String> imgList);
	public ArrayList<String> getImgList();
	public ResultSet getAsmRs();
	public String getAsmId() ;
	public void setAsmId(String asmId) ;
}
