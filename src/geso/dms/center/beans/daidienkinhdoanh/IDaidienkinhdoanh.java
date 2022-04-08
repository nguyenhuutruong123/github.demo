package geso.dms.center.beans.daidienkinhdoanh;

import geso.dms.center.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Hashtable;

public interface IDaidienkinhdoanh extends Serializable
{
	public ResultSet getKbhRs();
	public ResultSet getLogRs() ;
	public String getUserId();

	public void setUserId(String userId);

	public void init();

	public String getId();

	public void setId(String id);
	
	public String getSmartId();
	public void setSmartId(String smartid);
	
	public String getCmnd();

	public void setCmnd(String cmnd);

	public String getNgaysinh();

	public void setNgaysinh(String ngaysinh);

	public String getQuequan();

	public void setQuequan(String quequan);
	
	public String getMaycap();

	public void setMaycap(String maycap);
	
	public String getMaythe();

	public void setMaythe(String maythe);
	
	public String getMaymat();

	public void setMaymat(String maymat);

	public String getTienmat();

	public void setTienmat(String tienmat);
	
	public String getTiencap();

	public void setTiencap(String tiencap);
	
	public String getTienthe();

	public void setTienthe(String tienthe);
	
	
	public String getNppasm();

	public void setNppasm(String nppasm);
	
	public String getNgaybatdau();

	public void setNgaybatdau(String ngaybatdau);

	public String getNgayketthuc();

	public void setNgayketthuc(String ngayketthuc);

	public String getThuviec();

	public void setThuviec(String thiec);

	public void setSoTaiKhoan(String sotaikhoan);

	public String getSoTaiKhoan();

	public void setChuTaiKhoan(String chutaikhoan);

	public String getChuTaiKhoan();

	public void setNganHang(String nganhang);

	public String getNganHang();

	public String getTen();

	public void setTen(String ten);

	public String getSodt();

	public void setSodt(String sodt);

	public String getDiachi();

	public void setDiachi(String diachi);

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

	public String getKenhbanhang();

	public void setKenhbanhang(String kenhbanhang);

	public String getGsbanhang();

	public void setGsbanhang(String gsbanhang);

	public String getNhaphanphoi();

	public void setNhaphanphoi(String nhaphanphoi);

	public String getMessage();

	public void setMessage(String msg);

	public ResultSet getGsbhList();

	public void setGsbhList(ResultSet gsbhlist);

	public ResultSet getTrungtamphanphoiList();
	
	public ResultSet getNppList();

	public String getNppId();

	public void setNppId(String nppId);
	
	public String getTtppId();
	
	public void setTtppId(String ttppId);

	public void setGsbhIds(String[] gsbhIds);

	public Hashtable getGsbhIds();

	public boolean CreateDdkd();

	public boolean UpdateDdkd();

	public void createRS();

	public void createGsbhList();

	public boolean getIsDelete();

	public void setIsDelete(boolean isDelete);

	public void DBClose();

	public ResultSet GetRsDDKDTienNhiem();

	public void setDDKDTn(String id);
	public String getDDKDTn();

	public void setPhantramChuyen(String phantram);
	public String getPhanTramChuyen();

	public void setHinhanh(String ha);
	public String getHinhanh();

	public void setMatkhau(String matkhau);
	public String getMatkhau();
	
	public String getEmail();
	public void setEmail(String email);
	
	
	public String getFilecap();
	public void setFilecap(String filecap);
	public String getFilethe();
	public void setFilethe(String filethe);
	public String getChonmt();
	public void setChonmt(String chonmt);
	public String getFilemat();
	public void setFilemat(String filemat);
	public String getImei();
	public void setImei(String imei);
	public String getView();
	public void setView(String view);
	public String getRoute_fk();
	public void setRoute_fk(String route_fk);
	public ResultSet getRouteRs();
	public void setRouteRs(ResultSet routeRs);
	
	public String getMaERP();
	public void setMaERP(String maerp);
	public dbutils getDb();
}