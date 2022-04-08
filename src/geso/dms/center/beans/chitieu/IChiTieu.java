package geso.dms.center.beans.chitieu;

import geso.dms.center.beans.chitieu.imp.ChiTieu;
import geso.dms.center.beans.chitieu.imp.ChiTieuNPP;

import java.sql.ResultSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface IChiTieu
{
	public void setID(double id);
	public double getID();
	
	public void setChitieu(double chitieu);
	public double getChitieu();

	public void setNguoiTao(String nguoitao);
	public String getNguoiTao();

	public void setNguoiSua(String nguoisua);
	public String getNguoiSua();

	public void setThang(String thang);
	public String getThang();

	public void setNam(String nam);
	public String getNam();

	public void setKhuVucID(String khuvucid);
	public String getKhuVucID();

	public void setTenKhuVuc(String tenkhuvuc);
	public String getTenKhuVuc();

	public void setMessage(String strmessage);
	public String getMessage();

	public void setSoNgayLamViec(String songaylamviec);
	public String getSoNgayLamViec();

	public void setLoaiChiTieu(String loaichitieu);
	public String getLoaiChiTieu();

	public void setNgayKetThuc(String ngayketthuc);
	public String getNgayKetThuc();

	public void setNgayTao(String ngaytao);
	public String getNgayTao();

	public void setNgaySua(String nguoisua);
	public String getNgaySua();

	public void setUserId(String userid);
	public String getUserId();

	public void setDienGiai(String diengiai);
	public String getDienGiai();

	public boolean SaveChiTieu();
	public boolean SaveChiTieu_Sec();

	public boolean EditChiTieu();
	public boolean EditChiTieu_Sec();

	public boolean DeleteChitieu();
	public boolean DeleteChitieu_Sec();

	public List<ChiTieu> getChiTieu();
	public void setListChiTieu(String sql, String loaict);

	public List<ChiTieuNPP> getListChiTieuNPP();
	public void setListChiTieuNPP(List<ChiTieuNPP> list);

	public void setDVKDID(String dvkdid);
	public String getDVKDId();

	public void setTenDVKD(String tendvkd);
	public String getTenDVKD();

	public String getKenhId();
	public void setKenhId(String kenhid);

	public String getTenKenh();
	public ResultSet getRsKenh();

	public ResultSet getRsDvdk();

	public void CreateRs();

	public void setTrangThai(String trangthai);
	public String getTrangThai();

	public boolean ChotChiTieu();

	public ResultSet getRsChitieunhanvien();

	public ResultSet getRsNppNhomSp();

	public String[] getNhomSp();

	public boolean ChotChiTieu_Sec();

	public void closeDB();

	public boolean UnChotChiTieu_Sec();

	public String getChuoiNhomSp();

	public void setChuoiNhomSp(String chuoinhomsp);

	public void setRsPri(String sql);
	public ResultSet rsChitieuPri();

	public ResultSet rs_chitieuprinpp();
	public ResultSet rs_chitieupriss();
	public ResultSet rs_chitieupriasm();
	public ResultSet rs_chitieuprirsm();

	public void DbClose();

	public boolean DeleteChitieuSkuin();

	public boolean ChotChitieuSkuin();

	public boolean UnChotChitieuSkuin();

	public String GetTumuc();
	public void SetTumuc(String tumuc);

	public String GetToimuc();
	public void SetToimuc(String toimuc);

	public String[] getNhomSpId();
	 
	public boolean SaveChiTieu_Sec(HttpServletRequest request);
	public boolean EditChiTieu(HttpServletRequest request);
	
	
	public String getApdung();
	public void setApdung(String apdung);
	
	public String getQuy();
	public void setQuy(String quy);
	
	public ResultSet getKyRs();
	public void setKyRs(ResultSet kyRs);
	
	public ResultSet getQuyRs();
	public void setQuyRs(ResultSet quyRs);
	
	public String getView();
	public void setView(String view);
	
}
