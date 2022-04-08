package geso.dms.center.beans.nhaphanphoi;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

public interface INhaphanphoi extends Serializable
{
	// Cac thuoc tinh
	public String getUserId();
	public void setUserId(String userId);

	public String getSotk();
	public void setSotk(String sotk);
	public void setError(String error);
	public String getError();

	public String getGiayphepKD();
	public void setGiayphepKD(String gpkd);

	public String getFax();
	public void setFax(String fax);

	public String getId();
	public void setId(String id);

	public String getTen();
	public void setTen(String ten);
	
	public String getNguoidaidien();
	public void setNguoidaidien(String ndd);

	public String getGsbhnpp();
	public void setGsbhnpp(String gsbhnpp);

	public String getDiachi();
	public void setDiachi(String diachi);

	public String getTpId();
	public void setTpId(String tpId);

	public String getQhId();
	public void setQhId(String qhId);

	public String getSodienthoai();
	public void setSodienthoai(String sodienthoai);

	public String getDvkd();
	public void setDvkd(String dvkd);

	public String getKv();
	public void setKv(String kv);

	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getTrangthaidms();
	public void setTrangthaidms(String trangthaidms);

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

	public ResultSet getTp();
	public void setTp(ResultSet tp);

	public ResultSet getQh();
	public void setQh(ResultSet qh);

	public ResultSet getKhuvuc();
	public void setKhuvuc(ResultSet khuvuc);

	public ResultSet getNhappTiennhiem();
	public void setNhappTiennhiem(ResultSet nhapptiennhiem);

	public String getKvId();
	public void setKvId(String kvId);

	public String getNpptnId();
	public void setNpptnId(String npptnId);

	public ResultSet getDvkd_NccList();
	public void setDvkd_NccList(ResultSet dvkd_ncclist);
	public int CountResultSet(ResultSet Rs); // đếm dòng trong Rs
	
	public ResultSet getDvkd_NccSelected(); // cap dvkd_ncc duoc chon trong
											// trang Update
	public void setDvkd_NccSelected(ResultSet dvkd_nccselected);

	public Hashtable<Integer, String> getDvkd_NccIds();
	public void setDvkd_NccIds(String[] dvkd_nccIds);

	public ResultSet getGsbh_KbhList();
	public void setGsbh_KbhList(ResultSet gsbh_kbhlist);

	public String getNgayDh_IdSelected();
	public ResultSet getGsbh_KbhSelected();
	public String getGsbh_KbhIdSelected();
	public void setGsbh_KbhSelected(ResultSet gsbh_kbhselected);
	
	public String getDvkd_NccIdSelected();
	
	public ResultSet getNgaydhList();
	public void setNgaydhList(ResultSet ngaydhlist);

	public ResultSet getNgaydhSelected();
	public void setNgaydhSelected(ResultSet ngaydhselected);

	public Hashtable<Integer, String> getNgaydhIds();
	public void setNgaydhIds(String[] ngaydhIds);

	public Hashtable<Integer, String> getGsbh_KbhIds();
	public void setGsbh_KbhIds(String[] gsbh_kbhIds);

	public void setMaSAP(String ma);
	public String getMaSAP();

	public void setPass(String pass);
	public String getPass();

	public String getPriSec();
	public void setPriSec(String prisec);

	public boolean CreateNpp(HttpServletRequest request);

	public boolean UpdateNpp(HttpServletRequest request);

	public void createRS();

	public void DBclose();

	public void setDiaChiXuatHoaDon(String diachixhd);
	public String getDiaChiXuatHoaDon();

	public void setMaSoThue(String masothue);
	public String getmaSoThue();

	public void setKhoTT(String khott);
	public String getKhoTT();
	
	public ResultSet getrs_khott();

	public String getTungay();
	public void setTungay(String tungay);

	public String getDenngay();
	public void setDenngay(String denngay);

	public String getDiaChiKho();
	public void setDiaChiKho(String dckho);

	public String getDiaBanHd();
	public void setDiaBanHd(String DiaBanHd);

	public String getNganHangId();
	public void setNganHangId(String NganHangId);

	public String getChiNhanhId();
	public void setChiNhanhId(String ChiNhanhId);

	
	public String getChuTaiKhoan();
	public void setChuTaiKhoan(String ChuTaiKhoan);


	public String getTentat();
	public void setTentat(String tentat);
	
	public ResultSet getGsQlRs();
	public void setGsQlRs(ResultSet gsqlRs);
	
	public String getIsChiNhanh();
	public void setIsChiNhanh(String isChinhanh);
	
	
	public String getLoaiNpp();
	public void setLoaiNpp(String loainpp);
	
	public String getTructhuocId();
	public void setTructhuocId(String tructhuocId);
	
	public ResultSet getTructhuocRs();
	public void setTructhuocRs(ResultSet tructhuocRs);
	
	public ResultSet getLoaiNppRs();
	public boolean checkma(String ma);
	public void setLoaiNppRs(ResultSet loainppRs);
	
	public String getSongayno();
	public void setSongayno(String songayno);
	
	public String getSotienno();
	public void setSotienno(String songayno);
	
	public String getTtppId();
	public void setTtppId(String ttppId);
	
	public String getKenhId();
	public void setKenhId(String kenhId);
	
	public ResultSet getMuahangtuRs();
	public ResultSet getTtppRs();
	public void setTtppRs(ResultSet ttppRs);
	
	public ResultSet getKenhRs();
	public void setKenhRs(ResultSet kenhRs);
	// thêm mới
	public void setArrayNgaydhSelected();
	public void setArrayGsbhSelected();
	public void setArrayDvkdSelected();
	
	public void setChuNhaPhanPhoi(String cnpp);
	public void setTonAnToan(String tat);
	public void setMuaHangTu(String mht);
	public void setLichDatHang(String ldh);
	public void setQuyTrinhBanHang(String qtbh);
	public String getChuNhaPhanPhoi();
	public String getTonAnToan();
	public String getMuaHangTu();
	public String getLichDatHang();
	public String getQuyTrinhBanHang();
	
	public String getMaNpp();
	public void setMaNpp(String manpp);
	
	public String getDiachi2();
	public void setDiachi2(String diachi2);
	
	public String getNppcap1();
	public void setNppcap1(String nppc1);
	
	public String getPtTangTruong();
	public void setPtTangTruong(String pttangtruong);
	
	public String getTansuatdh();
	public void setTansuatdh(String Tansuatdh);
	
	public ResultSet getNppC1Rs();
	public String getDiabanId();
	public void setDiabanId(String diabanId);
	public ResultSet getDiabanRs();
	public void setDiabanRs(ResultSet diabanRs);
	public double getChietKhau();
	public void setChietKhau(double chietKhau) ;
}
