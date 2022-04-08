package geso.dms.center.beans.chitieunhaphanphoi;

import geso.dms.center.beans.chitieunhaphanphoi.imp.ChiTieuNhaphanphoi;

import java.sql.ResultSet;
import java.util.List;

public interface IChiTieuNhaphanphoi {

	public List<Object> getDataSearch();
	public void setDataSearch(List<Object> dataSearch);
	
	public void setID(String id);
	public String getID();

	public void setNguoiTao(String nguoitao);
	public String getNguoiTao();
	public void setNguoiSua(String nguoisua);
	public String getNguoiSua();
	public void setThang(int thang);
	public int getThang();
	public void setNam(int nam);
	public int getNam();
	public void setMessage(String strmessage);
	public String getMessage();


	public void setNgayTao(String ngaytao);
	public String getNgayTao();

	public void setNgaySua(String nguoisua);
	public String getNgaySua();

	public void setUserId(String userid);
	public String getUserId();

	public void setDienGiai(String diengiai);
	public String getDienGiai();


	public boolean  Create();

	public boolean Update();

	public boolean Delete();

	public List<IChiTieuNhaphanphoi> getChiTieu();
	public void setListChiTieu(String sql);


	public void setTrangThai(String trangthai);
	public String getTrangThai();


	public boolean Chot();
	public void closeDB();

	public boolean UnChot();
	
	public String getDisplay();
	public void setDisplay(String loai) ;
	
	public List<ICTNhaphanphoi> getListCTNhaphanphoi() ;
	public void setListCTNhaphanphoi(List<ICTNhaphanphoi> listCTNhanVien);

	public void initBCNhaphanphoi();
	
	public String getSoNgayLamViec();
	public void setSoNgayLamViec(String soNgayLamViec);
	
	public String getView();
	public void setView(String view);
}

